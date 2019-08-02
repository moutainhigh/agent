package com.ryx.credit.service.impl.agent;

import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.enumc.*;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.ResultVO;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.dao.agent.*;
import com.ryx.credit.pojo.admin.agent.*;
import com.ryx.credit.pojo.admin.vo.AgentBusInfoVo;
import com.ryx.credit.pojo.admin.vo.AgentColinfoVo;
import com.ryx.credit.pojo.admin.vo.AgentVo;
import com.ryx.credit.pojo.admin.vo.CapitalVo;
import com.ryx.credit.service.ActivityService;
import com.ryx.credit.service.IUserService;
import com.ryx.credit.service.agent.*;
import com.ryx.credit.service.agent.netInPort.AgentNetInNotityService;
import com.ryx.credit.service.dict.DictOptionsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by cx on 2018/6/6.
 */
@Service("dataChangeActivityService")
public class DataChangeActivityServiceImpl implements DataChangeActivityService {

    private static Logger logger = LoggerFactory.getLogger(AgentEnterServiceImpl.class);

    @Autowired
    private AgentColinfoService agentColinfoService;
    @Autowired
    private ActivityService activityService;
    @Autowired
    private DateChangeRequestMapper dateChangeRequestMapper;
    @Autowired
    private BusActRelMapper busActRelMapper;
    @Autowired
    private DictOptionsService dictOptionsService;
    @Autowired
    private AgentEnterService agentEnterService;
    @Autowired
    private AimportService aimportService;
    //    @Autowired
//    private AgentNotifyService agentNotifyService;
    @Autowired
    private AColinfoPaymentService colinfoPaymentService;
    @Autowired
    private PlatFormMapper platFormMapper;
    @Autowired
    private AgentBusInfoMapper agentBusInfoMapper;
    @Autowired
    private AgentMapper agentMapper;
    @Autowired
    private AgentColinfoMapper agentColinfoMapper;
    @Autowired
    private AgentQueryService agentQueryService;
    @Autowired
    private IUserService iUserService;
    @Autowired
    private CapitalService capitalService;
    @Autowired
    private CapitalMapper capitalMapper;
    @Autowired
    private CapitalFlowService capitalFlowService;
    @Autowired
    private DataChangeActivityService dataChangeActivityService;
    @Autowired
    private AgentNetInNotityService agentNetInNotityService;
    @Autowired
    private AgentDataHistoryService agentDataHistoryService;


    @Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    @Override
    public ResultVO startDataChangeActivity(String dataChangeId,String userId) throws Exception{
        logger.info("========用户{}启动数据修改申请{}",userId,dataChangeId);
        DateChangeRequest dateChangeRequest = dateChangeRequestMapper.selectByPrimaryKey(dataChangeId);

        DateChangeRequestExample dateChangeRequestExample = new DateChangeRequestExample();
        DateChangeRequestExample.Criteria criteria = dateChangeRequestExample.createCriteria();
        criteria.andDataIdEqualTo(dateChangeRequest.getDataId());
        criteria.andDataTypeEqualTo(BusActRelBusType.DC_Colinfo.name());
        criteria.andAppyStatusEqualTo(AgStatus.Approving.getValue());
        List<DateChangeRequest> dateChangeRequests = dateChangeRequestMapper.selectByExample(dateChangeRequestExample);
        if(null==dateChangeRequests){
            logger.info("数据修改申请:{}", "查询审批状态失败");
            throw new MessageException("查询审批状态失败！");
        }
        if(dateChangeRequests.size()>0){
            logger.info("数据修改申请:{}", "审批中请勿重复提交");
            throw new MessageException("审批中请勿重复提交！");
        }
        AgentVo agentVo = JSONObject.parseObject(dateChangeRequest.getDataContent(), AgentVo.class);
        if(null==agentVo){
            logger.info("数据修改申请:{}", "转换异常");
            throw new MessageException("修改申请提交审批失败！");
        }
        List<AgentColinfoVo> colinfoVoList = agentVo.getColinfoVoList();
        if(colinfoVoList!=null) {
            for (AgentColinfoVo agentColinfoVo : colinfoVoList) {
                List<AColinfoPayment> aColinfoPayments = colinfoPaymentService.queryConlifoPaymentList(dateChangeRequest.getDataId(), agentColinfoVo.getCloBankAccount());
                if (null == aColinfoPayments) {
                    logger.info("数据修改申请:{}", "收款账号查询失败");
                    throw new MessageException("收款账号查询失败");
                }
                if (aColinfoPayments.size() > 2) {
                    logger.info("数据修改申请:{}", "收款账号已验证超过2次请更换银行卡");
                    throw new MessageException("收款账号已验证超过2次请更换银行卡！");
                }
            }
        }
        BusActRelExample example = new BusActRelExample();
        example.or().andBusIdEqualTo(dateChangeRequest.getId()).andActivStatusEqualTo(AgStatus.Approving.name()).andStatusEqualTo(Status.STATUS_1.status);
        List<BusActRel> list = busActRelMapper.selectByExample(example);
        if(list.size()>0){
            logger.info("========用户{}启动数据修改申请{}{}",dataChangeId,userId,"申请进行中，禁止重复提交");
            return ResultVO.fail("申请进行中，禁止重复提交");
        }

        //不同的业务类型找到不同的启动流程
        String workId;
        if(dateChangeRequest.getDataType().equals(BusActRelBusType.DC_Agent.name())){
            workId = dictOptionsService.getApproveVersion("business");
        }else if(dateChangeRequest.getDataType().equals(BusActRelBusType.DC_Colinfo.name())){
            workId = dictOptionsService.getApproveVersion("finance");
        }else{
            throw new MessageException("请求类型错误");
        }

        dateChangeRequest.setAppyStatus(AgStatus.Approving.status);
        if(1!=dateChangeRequestMapper.updateByPrimaryKeySelective(dateChangeRequest)){
            logger.info("代理商审批，启动审批异常，更新记录状态{}:{}",dateChangeRequest.getId(),userId);
            throw  new MessageException("更新记录状态异常");
        }
        if(StringUtils.isEmpty(workId)) {
            logger.info("========用户{}启动数据修改申请{}{}",dataChangeId,userId,"审批流启动失败字典中未配置部署流程");
            throw new MessageException("审批流启动失败字典中未配置部署流程!");
        }
        Map startPar = agentEnterService.startPar(userId);
        if(null==startPar){
            logger.info("========用户{}启动数据修改申请{}{}启动部门参数为空",dataChangeId,userId,"审批流启动失败字典中未配置部署流程");
            throw new MessageException("启动部门参数为空!");
        }
        startPar.put("rs","pass");
        String proce = activityService.createDeloyFlow(null,workId,null,null,startPar);
        if(proce==null){
            logger.info("========用户{}启动数据修改申请{}{}",dataChangeId,userId,"数据修改审批，审批流启动失败");
            logger.info("数据修改审批，审批流启动失败{}:{}",dataChangeId,userId);
            throw new MessageException("审批流启动失败!");
        }
        //代理商业务视频关系
        BusActRel record = new BusActRel();
        record.setBusId(dateChangeRequest.getId());
        record.setActivId(proce);
        record.setcTime(Calendar.getInstance().getTime());
        record.setcUser(userId);
        record.setStatus(Status.STATUS_1.status);
        record.setBusType(dateChangeRequest.getDataType());//流程关系类型是数据申请类型
        record.setActivStatus(AgStatus.Approving.name());
        record.setAgentId(dateChangeRequest.getDataId());
        record.setDataShiro(BusActRelBusType.DC_Agent.key);
        Agent agent = agentMapper.selectByPrimaryKey(dateChangeRequest.getDataId());
        if(agent!=null)
            record.setAgentName(agent.getAgName());
        if(dateChangeRequest.getDataType().equals(BusActRelBusType.DC_Agent.name())){
            AgentVo agVo = JSONObject.parseObject(dateChangeRequest.getDataContent(), AgentVo.class);
            if(agVo.getBusInfoVoList().size()==0){
                throw  new MessageException("缺少业务信息");
            }
            AgentBusInfoVo agentBusInfoVo = agVo.getBusInfoVoList().get(0);
            PlatForm platForm = platFormMapper.selectByPlatFormNum(agentBusInfoVo.getBusPlatform());
            record.setNetInBusType("ACTIVITY_"+platForm.getPlatformNum());
            record.setAgDocDistrict(agentBusInfoVo.getAgDocDistrict());
            record.setAgDocPro(agentBusInfoVo.getAgDocPro());
        }else{
            record.setAgDocDistrict(agent.getAgDocDistrict());
            record.setAgDocPro(agent.getAgDocPro());
        }
        if(1!=busActRelMapper.insertSelective(record)){
            logger.info("代理商审批，启动审批异常，添加审批关系失败{}:{}",dateChangeRequest.getId(),proce);
            throw  new MessageException("添加审批关系失败");
        }
        return ResultVO.success(null);
    }


    /**
     * 收款账户修改 审批完成处理
     * @param proIns
     * @param agStatus
     * @return
     */
    @Override
    public ResultVO compressColInfoDataChangeActivity(String proIns, String agStatus)throws Exception {
        try {
            BusActRelExample example = new BusActRelExample();
            example.or().andStatusEqualTo(Status.STATUS_1.status).andActivIdEqualTo(proIns).andActivStatusEqualTo(AgStatus.Approving.name());
            List<BusActRel> resl =  busActRelMapper.selectByExample(example);
            if(resl.size()==0){
                logger.info("========审批流完成{}状态{}未找到数据与审批流关系",proIns,agStatus);
                return ResultVO.fail("未找到数据与审批流关系");
            }
            BusActRel rel = resl.get(0);
            logger.info("========审批流完成{}业务{}状态{}",proIns,rel.getBusType(),agStatus);
            DateChangeRequest dr = dateChangeRequestMapper.selectByPrimaryKey(rel.getBusId());
            if(dr==null){
                logger.info("========审批流完成{}业务{}状态{} 未找到数据",proIns,rel.getBusType(),agStatus);
                throw new ProcessException("更新数据申请失败");
            }
            try {
                if(AgStatus.Approved.name().equals(agStatus)){
                    //收款账户修改
                    if(DataChangeApyType.DC_Colinfo.name().equals(dr.getDataType())) {
                        //更新入库
                        AgentVo vo = JSONObject.parseObject(dr.getDataContent(), AgentVo.class);
                        vo.getAgent().setcUser(rel.getcUser());     //直接新增收款账户时 此字段不可为空
                        ResultVO res = agentColinfoService.updateAgentColinfoVo(vo.getColinfoVoList(), vo.getAgent(),rel.getcUser(),null);
                        logger.info("========审批流完成{}业务{}状态{},结果{}", proIns, rel.getBusType(), agStatus, res.getResInfo());
                        //更新数据状态为审批成功
                        if(res.isSuccess()){
                            dr.setAppyStatus(AgStatus.Approved.status);
                            dr.setcUpdate(Calendar.getInstance().getTime());
                            logger.info("========审批流完成{}业务{}状态{},结果{}",proIns,rel.getBusType(),agStatus,"更新数据申请成功");
                            if(1!=dateChangeRequestMapper.updateByPrimaryKeySelective(dr)){
                                throw new ProcessException("更新数据申请失败");
                            }

                            //首刷平台
                            PlatFormExample platFormExample = new PlatFormExample();
//                            List<String> list = new ArrayList<>();
//                            list.add(PlatformType.MPOS.code);
//                            list.add(PlatformType.RHPOS.code);
                            platFormExample.or()
                            .andStatusEqualTo(Status.STATUS_1.status);
//                            .andPlatformTypeIn(list);
                            List<PlatForm>  platForms = platFormMapper.selectByExample(platFormExample);
                            List<String> pltcode = new ArrayList<>();
                            pltcode.add("aaaa");
                            for (PlatForm platForm : platForms) {
                                pltcode.add(platForm.getPlatformNum());
                            }
                            //查询首刷业务
                            AgentBusInfoExample agentBusInfoExample = new AgentBusInfoExample();
                            agentBusInfoExample.or()
                                    .andStatusEqualTo(Status.STATUS_1.status)
                                    .andBusPlatformIn(pltcode)
                                    .andCloReviewStatusEqualTo(AgStatus.Approved.status)
                                    .andAgentIdEqualTo(vo.getAgent().getId());
                            List<AgentBusInfo> agentBusInfoList = agentBusInfoMapper.selectByExample(agentBusInfoExample);

                            //是否需要同步至业务系统、进行一分钱验证
                            AgentVo preVo = JSONObject.parseObject(dr.getDataPreContent(), AgentVo.class);
                            //收款账户信息
                            List<AgentColinfoVo> voColinfoVoList = vo.getColinfoVoList();
                            List<AgentColinfoVo> preVoColinfoVoList = preVo.getColinfoVoList();
                            Agent voAgent = vo.getAgent();
                            logger.info("===============================更新代理商基础信息开始");
                            Agent db_agent = agentMapper.selectByPrimaryKey(voAgent.getId());
                            db_agent.setAgName(voAgent.getAgName());
                            db_agent.setAgNature(voAgent.getAgNature());
                            db_agent.setAgCapital(voAgent.getAgCapital());
                            db_agent.setAgBusLic(voAgent.getAgBusLic());
                            db_agent.setAgBusLicb(voAgent.getAgBusLicb());
                            db_agent.setAgBusLice(voAgent.getAgBusLice());
                            db_agent.setAgLegal(voAgent.getAgLegal());
                            db_agent.setAgLegalCertype(voAgent.getAgLegalCertype());
                            db_agent.setAgLegalCernum(voAgent.getAgLegalCernum());
                            db_agent.setAgLegalMobile(voAgent.getAgLegalMobile());
                            db_agent.setAgHead(voAgent.getAgHead());
                            db_agent.setAgHeadMobile(voAgent.getAgHeadMobile());
                            db_agent.setAgRegAdd(voAgent.getAgRegAdd());
                            db_agent.setAgBusScope(voAgent.getAgBusScope());
                            db_agent.setCloTaxPoint(voAgent.getCloTaxPoint());
                            db_agent.setAgDocPro(voAgent.getAgDocPro());
                            db_agent.setAgDocDistrict(voAgent.getAgDocDistrict());
                            db_agent.setAgRemark(voAgent.getAgRemark());
                            db_agent.setStatus(voAgent.getStatus());
                            db_agent.setAgRegArea(voAgent.getAgRegArea());
                            db_agent.setBusRiskEmail(voAgent.getBusRiskEmail());
                            db_agent.setBusContactEmail(voAgent.getBusContactEmail());
                            if (1 != agentMapper.updateByPrimaryKeySelective(db_agent)) {
                                throw new ProcessException("代理商信息更新失败");
                            }else{
                                //保存数据历史
                                if(!agentDataHistoryService.saveDataHistory(db_agent,db_agent.getId(), DataHistoryType.BASICS.code,rel.getcUser(),voAgent.getVersion()).isOK()){
                                    throw new ProcessException("代理商信息更新失败！请重试");
                                }
                            }
                            logger.info("===============================更新代理商基础信息成功");


                            Agent preVoAgent = preVo.getAgent();

                            if (voColinfoVoList.size()>0){
                                if (voColinfoVoList.size() != preVoColinfoVoList.size()){       //新增收款账户
                                    //一分钱验证、同步至业务系统

                                    logger.info("========================一分钱验证状态修改开始");
                                    for (AgentColinfoVo agentColinfo:voColinfoVoList){
                                        agentColinfo.setPayStatus(ColinfoPayStatus.A.getValue());
                                    }
                                    agentColinfoService.updateAgentColinfoVo(voColinfoVoList, vo.getAgent(),rel.getcUser(),null);
                                    logger.info("========================一分钱验证状态修改完成");

                                    /*logger.info("========================同步至业务系统开始");
                                    for (AgentBusInfo agentBusInfo : agentBusInfoList) {
                                        agentNetInNotityService.asynNotifyPlatform(agentBusInfo.getId(),NotifyType.NetInEdit.getValue());
                                    }
                                    logger.info("========================同步至业务系统完成");*/

                                    //建立收款账户和平台码的关系
                                    AgentColinfo agentColinfoVo=voColinfoVoList.get(0);


                                    for (AgentBusInfo agentBusInfo : agentBusInfoList) {        //为业务平台建立练习
                                        AgentColinfoRel agentColinfoRel = new AgentColinfoRel();
                                        agentColinfoRel.setcUse(rel.getcUser());
                                        agentColinfoRel.setAgentid(voAgent.getId());
                                        agentColinfoRel.setAgentColinfoid(agentColinfoVo.getId());
                                        agentColinfoRel.setBusPlatform(agentBusInfo.getBusPlatform());
                                        agentColinfoRel.setAgentbusid(agentBusInfo.getId());

                                        agentColinfoService.saveAgentColinfoRel(agentColinfoRel, rel.getcUser());
                                    }

                                }else{
                                    boolean synTemp=true;
                                    boolean checkTemp=true;
                                    for (AgentColinfoVo newColinfo:voColinfoVoList){
                                        for (AgentColinfoVo oldColinfo:preVoColinfoVoList){
                                            if (newColinfo.getId().equals(oldColinfo.getId())){
                                                checkTemp = checkNewAccount(newColinfo,oldColinfo);

                                                synTemp=isMustSyn(newColinfo,oldColinfo,voAgent,preVoAgent);
                                            }
                                        }
                                        if (!checkTemp){
                                            //一分钱验证、同步至业务系统
                                            logger.info("========================一分钱验证状态修改开始");
                                            for (AgentColinfoVo agentColinfo:voColinfoVoList){
                                                agentColinfo.setPayStatus(ColinfoPayStatus.A.getValue());
                                            }
                                            agentColinfoService.updateAgentColinfoVo(voColinfoVoList, vo.getAgent(),rel.getcUser(),null);
                                            logger.info("========================一分钱验证状态修改完成");

                                            break;
                                            /*logger.info("========================同步至业务系统开始");
                                            for (AgentBusInfo agentBusInfo : agentBusInfoList) {
                                                agentNetInNotityService.asynNotifyPlatform(agentBusInfo.getId(),NotifyType.NetInEdit.getValue());
                                            }
                                            logger.info("========================同步至业务系统完成");*/
                                        }/*else if (!synTemp){//同步至业务系统
                                            for (AgentBusInfo agentBusInfo : agentBusInfoList) {
                                                agentNetInNotityService.asynNotifyPlatform(agentBusInfo.getId(),NotifyType.NetInEdit.getValue());
                                            }
                                            break;
                                        }*/
                                    }
                                }
                            }
                        }
                        //代理商新修改
                    }else if(DataChangeApyType.DC_Agent.name().equals(dr.getDataType())){

                        //更新入库
                        AgentVo vo = JSONObject.parseObject(dr.getDataContent(), AgentVo.class);
                        AgentVo preVo = JSONObject.parseObject(dr.getDataPreContent(), AgentVo.class);
                        List<CapitalVo> capitalVoList = vo.getCapitalVoList();
                        if(null!=capitalVoList && capitalVoList.size()>0){
                            for (CapitalVo capitalVo : capitalVoList) {
                                capitalVo.setcAgentId(vo.getAgent().getId());
                                capitalVo.setcUser(rel.getcUser());
                                capitalVo.setSrcId(dr.getId());
                                capitalVo.setSrcRemark("代理商信息修改");
                            }
                        }
                        //更新财务出款机构
                        List<AgentBusInfoVo> orgTypeList = vo.getOrgTypeList();
                        for (AgentBusInfoVo agentBusInfoVo : orgTypeList) {
                            AgentBusInfo agentBusInfo = agentBusInfoMapper.selectByPrimaryKey(agentBusInfoVo.getId());
                            agentBusInfo.setFinaceRemitOrgan(agentBusInfoVo.getFinaceRemitOrgan());
                            int i = agentBusInfoMapper.updateByPrimaryKeySelective(agentBusInfo);
                            if ( i != 1) {
                                throw new ProcessException("更新财务出款机构失败");
                            }
                        }

                        ResultVO res = agentEnterService.updateAgentVo(vo,rel.getcUser(),true,null);
                        for (AgentBusInfoVo agentBusInfoVo : vo.getEditDebitList()) {
                            AgentBusInfo agentBusInfo = agentBusInfoMapper.selectByPrimaryKey(agentBusInfoVo.getId());
                            agentBusInfoVo.setId(agentBusInfoVo.getId());
                            agentBusInfoVo.setVersion(agentBusInfo.getVersion());
                            agentBusInfoVo.setcUtime(new Date());
                            int i = agentBusInfoMapper.updateByPrimaryKeySelective(agentBusInfoVo);
                            if(i!=1){
                                throw new ProcessException("更新借记费率等信息失败");
                            }
                        }
                        logger.info("========审批流完成{}业务{}状态{},结果{}", proIns, rel.getBusType(), agStatus, res.getResInfo());
                        //更新数据状态为审批成功
                        if(res.isSuccess()){
                            dr.setAppyStatus(AgStatus.Approved.status);
                            dr.setcUpdate(Calendar.getInstance().getTime());
                            logger.info("========审批流完成{}业务{}状态{},结果{}",proIns,rel.getBusType(),agStatus,"更新数据申请成功");
                            if(1!=dateChangeRequestMapper.updateByPrimaryKeySelective(dr)){
                                throw new ProcessException("更新数据申请失败");
                            }
                            if(null!=vo.getCapitalVoList() && vo.getCapitalVoList().size()>0){
                                for (Capital capital : vo.getCapitalVoList()) {
                                    capital.setCloReviewStatus(AgStatus.Approved.getValue());
                                    int i = capitalMapper.updateByPrimaryKeySelective(capital);
                                    if(1!=i){
                                        throw new ProcessException("更新缴纳款审批通过失败");
                                    }
                                }
                            }

                        }

                        //入网程序调用
//                        try {
                        if(vo.getBusInfoVoList()!=null){

                            for (AgentBusInfoVo agentBusInfoVo : vo.getBusInfoVoList()) {
//                                    if(StringUtils.isNotBlank(agentBusInfoVo.getId())) {
//                                        ImportAgent importAgent = new ImportAgent();
//                                        importAgent.setDataid(agentBusInfoVo.getId());
//                                        importAgent.setDatatype(AgImportType.DATACHANGEAPP.name());
//                                        importAgent.setBatchcode(proIns);
//                                        importAgent.setcUser(rel.getcUser());
//                                        if (1 != aimportService.insertAgentImportData(importAgent)) {
//                                            logger.info("代理商修改审批通过-添加开户任务失败");
//                                        } else {
//                                            logger.info("代理商修改审批通过-添加开户任务成功!{},{}", AgImportType.DATACHANGEAPP.getValue(), vo.getAgent().getId());
//                                        }
//                                    }
                                agentNetInNotityService.asynNotifyPlatform(agentBusInfoVo.getId(),NotifyType.NetInEdit.getValue());
                            }
                        }
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                            throw new ProcessException("更新数据申请失败");
//                        } finally {
//                            agentNotifyService.asynNotifyPlatform();
//                        }

                    }
                    //拒绝更新数据状态
                }else if(AgStatus.Refuse.name().equals(agStatus)){
                    dr.setAppyStatus(AgStatus.Refuse.status);
                    dr.setcUpdate(Calendar.getInstance().getTime());
                    logger.info("========审批流完成{}业务{}状态{},结果{}",proIns,rel.getBusType(),agStatus,"更新数据申请失败");
                    if(1!=dateChangeRequestMapper.updateByPrimaryKeySelective(dr)){
                        throw new ProcessException("更新数据申请失败");
                    }
                }
                rel.setActivStatus(agStatus);
                if(1!=busActRelMapper.updateByPrimaryKeySelective(rel)){
                    throw new ProcessException("更新数据申请失败");
                }
            } catch (ProcessException e) {
                e.printStackTrace();
                throw e;
            }

            try {
                agentQueryService.loadCach();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return ResultVO.success(dr);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }


    /**
     * 一分钱验证判断
     */
    public boolean checkNewAccount(AgentColinfoVo newColinfo,AgentColinfoVo oldColinfo){
        return (newColinfo.getCloRealname().equals(oldColinfo.getCloRealname())) &&    //收款账户名
                    (newColinfo.getCloBankAccount().equals(oldColinfo.getCloBankAccount())) &&  //收款账号
                        (newColinfo.getCloBankCode().equals(oldColinfo.getCloBankCode())) &&    //收款开户总行
                            (newColinfo.getBankRegion().equals(oldColinfo.getBankRegion())) &&  //开户行地区
                                (newColinfo.getCloBankBranch().equals(oldColinfo.getCloBankBranch())) &&    //收款开户支行
                                    (newColinfo.getAllLineNum().equals(oldColinfo.getAllLineNum())) &&  //总行联行号
                                        (newColinfo.getBranchLineNum().equals(oldColinfo.getBranchLineNum())) &&    //支行联行号
                                          (newColinfo.getAgLegalCernum().equals(oldColinfo.getAgLegalCernum()));  //户主证件号
    }

    /**
     * 通知业务系统判断
     *
     *
     */
    public boolean isMustSyn(AgentColinfoVo newColinfo,AgentColinfoVo oldColinfo,Agent agent,Agent preagent){
        return checkNewAccount(newColinfo,oldColinfo) && (newColinfo.getCloType().equals(oldColinfo.getCloType())) &&  //收款账户类型
                    (newColinfo.getCloTaxPoint().equals(oldColinfo.getCloTaxPoint())) &&    //税点
                        (newColinfo.getCloInvoice().equals(oldColinfo.getCloInvoice())) &&  //是否开具分润发票
                          (newColinfo.getStatus().equals(oldColinfo.getStatus())) &&  //是否有
                            agent.getAgName().equals(preagent.getAgName()) && //代理商名称
                                agent.getAgBusLic().equals(preagent.getAgBusLic()) &&   //代理商营业执照
                                    agent.getAgLegalCernum().equals(preagent.getAgLegalCernum()) && //法人证件号
                                        agent.getAgLegal().equals(preagent.getAgLegal()) && //法人姓名
                                            agent.getAgRegArea().equals(preagent.getAgRegArea()) && // 注册地区
                                                agent.getAgRegAdd().equals(preagent.getAgRegAdd()); //注册地址
    }


    /**
     * 处理任务
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW,isolation = Isolation.DEFAULT,rollbackFor = Exception.class)
    @Override
    public AgentResult approvalTask(AgentVo agentVo, String userId) throws Exception{

        try {
            List<Map<String, Object>> orgCodeRes = iUserService.orgCode(Long.valueOf(userId));
            if(orgCodeRes==null && orgCodeRes.size()!=1){
                throw new ProcessException("部门参数为空");
            }
            Map<String, Object> stringObjectMap = orgCodeRes.get(0);
            String orgCode = String.valueOf(stringObjectMap.get("ORGANIZATIONCODE"));
            DateChangeRequest dateChangeRequest = dateChangeRequestMapper.selectByPrimaryKey(agentVo.getAgentBusId());
            if(null==dateChangeRequest){
                throw new ProcessException("数据错误");
            }
            //财务审批
            if(orgCode.equals("finance")){
/*//                财务填写实际到账金额
                for (CapitalVo  capitalVo:agentVo.getCapitalVoList()){
                    if (capitalVo.getcPayType().equals(PayType.YHHK.code)){
                        if (null==capitalVo.getcInAmount() || capitalVo.getcInAmount().equals("")){
                            logger.info("请填写实际到账金额");
                            throw new ProcessException("请填写实际到账金额");
                        }
                        if (StringUtils.isNotBlank(capitalVo.getTime())){
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                            Date cPaytime = sdf.parse(capitalVo.getTime());
                            capitalVo.setcPaytime(cPaytime);
                        }
                        if(null!=capitalVo.getcInAmount()){
                            capitalVo.setcFqInAmount(capitalVo.getcInAmount());
                        }
                    }
                }*/
                //数据修改
                if(dateChangeRequest.getDataType().equals(DataChangeApyType.DC_Agent.name())){
                    AgentVo vo = JSONObject.parseObject(dateChangeRequest.getDataContent(), AgentVo.class);
                    if(StringUtils.isBlank(agentVo.getDebt()) || StringUtils.isBlank(agentVo.getOweTicket())){
                        throw new ProcessException("请填写欠票欠款信息,没有请填0");
                    }
                    vo.setDebt(agentVo.getDebt());
                    vo.setOweTicket(agentVo.getOweTicket());
                    if(orgCode.equals("finance")){
                        vo.setCapitalVoList(agentVo.getCapitalVoList());
                        //处理财务审批（财务出款机构）
                        vo.setOrgTypeList(agentVo.getOrgTypeList());
                        for (AgentBusInfoVo orgTypeList : agentVo.getOrgTypeList()) {
                            if (StringUtils.isBlank(orgTypeList.getFinaceRemitOrgan())) {
                                throw new ProcessException("请选择财务出款机构");
                            }
                            vo.setFinaceRemitOrgan(orgTypeList.getFinaceRemitOrgan());
                        }
                    }
                    String voJson = JSONObject.toJSONString(vo);
                    dateChangeRequest.setDataContent(voJson);
                    int i = dataChangeActivityService.updateByPrimaryKeySelective(dateChangeRequest);
                    if(i!=1){
                        throw new ProcessException("处理任务：更新失败");
                    }
                }
            }else if(orgCode.equals("business")){
                //数据修改
                if(dateChangeRequest.getDataType().equals(DataChangeApyType.DC_Agent.name())){
                    AgentVo vo = JSONObject.parseObject(dateChangeRequest.getDataContent(), AgentVo.class);
                    vo.setEditDebitList(agentVo.getEditDebitList());
                    String voJson = JSONObject.toJSONString(vo);
                    dateChangeRequest.setDataContent(voJson);
                    int i = dataChangeActivityService.updateByPrimaryKeySelective(dateChangeRequest);
                    if(i!=1){
                        throw new ProcessException("处理任务：更新失败");
                    }
                }
            }

            AgentResult result = agentEnterService.completeTaskEnterActivity(agentVo,userId);
            if(!result.isOK()){
                logger.error(result.getMsg());
                throw new ProcessException("工作流处理任务异常");
            }
        } catch (ProcessException e) {
            e.printStackTrace();
            throw new MessageException("catch工作流处理任务异常:",e.getMsg());
        }
        return AgentResult.ok();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW,isolation = Isolation.DEFAULT,rollbackFor = Exception.class)
    @Override
    public int updateByPrimaryKeySelective(DateChangeRequest dateChangeRequest){
        int i = dateChangeRequestMapper.updateByPrimaryKeySelective(dateChangeRequest);
        return i;
    }
}
