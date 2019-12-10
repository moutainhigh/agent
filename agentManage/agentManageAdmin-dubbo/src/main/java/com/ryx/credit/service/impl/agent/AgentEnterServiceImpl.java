package com.ryx.credit.service.impl.agent;

import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.activity.entity.ActRuTask;
import com.ryx.credit.common.enumc.*;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.*;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.dao.agent.*;
import com.ryx.credit.dao.order.OrganizationMapper;
import com.ryx.credit.pojo.admin.agent.*;
import com.ryx.credit.pojo.admin.order.Organization;
import com.ryx.credit.pojo.admin.vo.*;
import com.ryx.credit.service.ActRuTaskService;
import com.ryx.credit.service.ActivityService;
import com.ryx.credit.service.IResourceService;
import com.ryx.credit.service.IUserService;
import com.ryx.credit.service.agent.*;
import com.ryx.credit.service.agent.netInPort.AgentNetInHttpService;
import com.ryx.credit.service.agent.netInPort.AgentNetInNotityService;
import com.ryx.credit.service.dict.DictOptionsService;
import com.ryx.credit.service.pay.LivenessDetectionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import sun.management.resources.agent;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

/**
 * Created by cx on 2018/5/28.
 */
@Service("agentEnterService")
public class AgentEnterServiceImpl implements AgentEnterService {

    private static Logger logger = LoggerFactory.getLogger(AgentEnterServiceImpl.class);
    private final String JURIS_DICTION = AppConfig.getProperty("region_jurisdiction");

    @Autowired
    private AgentService agentService;
    @Autowired
    private AgentContractService agentContractService;
    @Autowired
    private AccountPaidItemService accountPaidItemService;
    @Autowired
    private AgentBusinfoService agentBusinfoService;
    @Autowired
    private AgentColinfoService agentColinfoService;
    @Autowired
    private ActivityService activityService;
    @Autowired
    private BusActRelMapper busActRelMapper;
    @Autowired
    private AgentAssProtocolService agentAssProtocolService;
    @Autowired
    private AimportService aimportService;
    @Autowired
    private IUserService iUserService;
    @Autowired
    private DictOptionsService dictOptionsService;
    @Autowired
    private AgentMapper agentMapper;
    @Autowired
    private AgentQueryService agentQueryService;
    @Autowired
    private AssProtoColMapper assProtoColMapper;
    @Autowired
    private PlatFormService platFormService;
    @Autowired
    private PlatFormMapper platFormMapper;
    @Autowired
    private AgentBusInfoMapper agentBusInfoMapper;
    @Autowired
    private CapitalFlowService capitalFlowService;
    @Autowired
    private OrganizationMapper organizationMapper;
    @Autowired
    private AgentNetInNotityService agentNetInNotityService;
    @Autowired
    private BusinessPlatformService businessPlatformService;
    @Autowired
    private LivenessDetectionService livenessDetectionService;
    @Autowired
    private IResourceService iResourceService;
    @Autowired
    private ActRuTaskService actRuTaskService;
    @Autowired
    private AgentFreezeService agentFreezeService;


    /**
     * 代理商入网信息保存
     * @param agentVo
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
    @Override
    public ResultVO saveAgentInfo(AgentVo agentVo) throws ProcessException{

        try {
            if (agentVo.getAgent() != null && StringUtils.isNotBlank(agentVo.getAgent().getAgBusLic())) {
                AgentResult agentResult = agentService.checkAgBusLicIsEst(agentVo.getAgent().getAgName(), agentVo.getAgent().getAgBusLic());
                if (agentResult.isOK()) {
                    return ResultVO.fail(agentVo.getAgent().getAgBusLic() + "已存在,编号为：" + agentResult.getData());
                }
            }
            // 保存代理商基础信息
            Agent agent = agentService.insertAgent(agentVo.getAgent(), agentVo.getAgentTableFile(), agentVo.getAgent().getcUser(), "1");
            agentVo.setAgent(agent);
            // 保存缴纳款项信息
            for (CapitalVo item : agentVo.getCapitalVoList()) {
                item.setcAgentId(agent.getId());
                item.setcUser(agent.getcUser());
                AgentResult res = accountPaidItemService.insertAccountPaid(item, item.getCapitalTableFile(), agentVo.getAgent().getcUser(), false, "1");
                if (!res.isOK()) {
                    throw new ProcessException("添加交款项异常");
                }
            }
            // 保存合同信息
            for (AgentContractVo item : agentVo.getContractVoList()) {
                item.setcUser(agent.getcUser());
                item.setAgentId(agent.getId());
                item.setCloReviewStatus(AgStatus.Create.status);
                AgentContract agentContract = agentContractService.insertAgentContract(item, item.getContractTableFile(), agent.getcUser(), "1");
                //添加分管协议
                if (StringUtils.isNotBlank(item.getAgentAssProtocol())) {
                    AssProtoColRel rel = new AssProtoColRel();
                    rel.setAgentBusinfoId(agentContract.getId());
                    rel.setAssProtocolId(item.getAgentAssProtocol());
                    AssProtoCol assProtoCol = assProtoColMapper.selectByPrimaryKey(item.getAgentAssProtocol());
                    if (org.apache.commons.lang.StringUtils.isNotBlank(item.getProtocolRuleValue())) {
                        String ruleReplace = assProtoCol.getProtocolRule().replace("{}", item.getProtocolRuleValue());
                        rel.setProtocolRule(ruleReplace);
                    } else {
                        rel.setProtocolRule(assProtoCol.getProtocolRule());
                    }
                    rel.setProtocolRuleValue(item.getProtocolRuleValue());
                    if (1 != agentAssProtocolService.addProtocolRel(rel, agent.getcUser())) {
                        throw new ProcessException("业务分管协议添加失败");
                    }
                }

            }
            //收款账户
            for (AgentColinfoVo item : agentVo.getColinfoVoList()) {
                item.setAgentId(agent.getId());
                item.setcUser(agent.getcUser());
                item.setCloReviewStatus(AgStatus.Create.status);
                String agentName = agent.getAgName();
                String trueName = item.getCloRealname();
                //对公时 判断收款账户名是否与代理商名称一致 不一致则抛异常提示信息
                if (item.getCloType().compareTo(new BigDecimal(1)) == 0) {
                    if (agentName.equals(trueName)) {
                        item.setAgLegalCernum(agent.getAgLegalCernum());
                    }
                }
                agentColinfoService.agentColinfoInsert(item, item.getColinfoTableFile(), "1");
            }
            // 保存业务信息
            for (AgentBusInfoVo item : agentVo.getBusInfoVoList()) {
                item.setcUser(agent.getcUser());
                item.setAgentId(agent.getId());
                item.setCloReviewStatus(AgStatus.Create.status);
                if(StringUtils.isBlank(item.getBusScope())){
                    throw new ProcessException("业务范围不能为空");
                }
                if(StringUtils.isBlank(item.getBusUseOrgan())){
                    throw new ProcessException("使用范围不能为空");
                }
                AgentBusInfo db_AgentBusInfo = agentBusinfoService.agentBusInfoInsert(item);
            }
        } catch (ProcessException e) {
            e.printStackTrace();
            throw e;
        }catch (Exception e) {
            e.printStackTrace();
            throw new ProcessException("商户入网保存失败:"+e.getMessage());
        }
        return ResultVO.success("保存成功");
    }

    /**
     * 商户入网
     *
     * @param agentVo
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
    @Override
    public ResultVO agentEnterIn(AgentVo agentVo) throws ProcessException {
        try {
//            verifyOrgAndBZYD(agentVo.getBusInfoVoList());
//            verifyOther(agentVo.getBusInfoVoList());
            //根据营业执照号进行检查是否存在
            if(agentVo.getAgent()!=null && StringUtils.isNotBlank(agentVo.getAgent().getAgBusLic())) {
                AgentResult agentResult =  agentService.checkAgBusLicIsEst(agentVo.getAgent().getAgName(),agentVo.getAgent().getAgBusLic());
                if(agentResult.isOK()){
                    return ResultVO.fail(agentVo.getAgent().getAgBusLic()+"已存在,编号为："+agentResult.getData());
                }
            }
            Agent agent = agentService.insertAgent(agentVo.getAgent(), agentVo.getAgentTableFile(),agentVo.getAgent().getcUser(),null);
            agentVo.setAgent(agent);
            //代理商业务
            for (AgentBusInfo item : agentVo.getBusInfoVoList()) {
                //校验实时分润不能升级
                List platformList = platFormMapper.selectPlatformNumByPlatformType();
                boolean checkBusPlatform = platformList.contains(item.getBusPlatform()) && (null != item.getBusNum() && !"".equals(item.getBusNum()));
                if (checkBusPlatform) throw new ProcessException("实时分润品牌暂不支持升级！");
            }
            for (AgentContractVo item : agentVo.getContractVoList()) {
                item.setcUser(agent.getcUser());
                item.setAgentId(agent.getId());
                item.setCloReviewStatus(AgStatus.Create.status);
                AgentContract agentContract = agentContractService.insertAgentContract(item, item.getContractTableFile(), agent.getcUser(),null);
                //添加分管协议
                if (StringUtils.isNotBlank(item.getAgentAssProtocol())) {
                    AssProtoColRel rel = new AssProtoColRel();
                    rel.setAgentBusinfoId(agentContract.getId());
                    rel.setAssProtocolId(item.getAgentAssProtocol());
                    AssProtoCol assProtoCol = assProtoColMapper.selectByPrimaryKey(item.getAgentAssProtocol());
                    if(org.apache.commons.lang.StringUtils.isNotBlank(item.getProtocolRuleValue())){
                        String ruleReplace = assProtoCol.getProtocolRule().replace("{}", item.getProtocolRuleValue());
                        rel.setProtocolRule(ruleReplace);
                    }else{
                        rel.setProtocolRule(assProtoCol.getProtocolRule());
                    }
                    rel.setProtocolRuleValue(item.getProtocolRuleValue());
                    if (1 != agentAssProtocolService.addProtocolRel(rel, agent.getcUser())) {
                        throw new ProcessException("业务分管协议添加失败");
                    }
                }

            }
            for (CapitalVo item : agentVo.getCapitalVoList()) {
                if(item.getcPayType().equals(PayType.YHHK.getValue())){
                    if(item.getCapitalTableFile()==null){
                        throw new ProcessException("银行汇款方式必须上传打款凭据");
                    }
                    if(item.getCapitalTableFile().size()==0){
                        throw new ProcessException("银行汇款方式必须上传打款凭据");
                    }
                }
                item.setcAgentId(agent.getId());
                item.setcUser(agent.getcUser());
                AgentResult res = accountPaidItemService.insertAccountPaid(item, item.getCapitalTableFile(), agentVo.getAgent().getcUser(),false,null);
                if (!res.isOK()) {
                    throw new ProcessException("添加交款项异常");
                }

            }
            for (AgentColinfoVo item : agentVo.getColinfoVoList()) {
                item.setAgentId(agent.getId());
                item.setcUser(agent.getcUser());
                item.setCloReviewStatus(AgStatus.Create.status);
                //收款账户对私时做校验
                String agentName = agent.getAgName();
                String agLegalName = agent.getAgLegal();
                String trueName = item.getCloRealname();
                String certNo = item.getAgLegalCernum();
                if (item.getCloType().compareTo(new BigDecimal(2)) == 0) {
                    //对私时 收款账户名与法人姓名一致时 把法人身份证号拷贝到户主身份证号并进行认证
                    if (agLegalName.equals(trueName)) {
                        //法人身份证未空，必须输入结算卡户主身份证
                        if(StringUtils.isBlank(agent.getAgLegalCernum())){
                            if(StringUtils.isBlank(certNo)){
                                throw new ProcessException("法人身份证未获取到，请输入结算卡户主身份证号");
                            }
                        }
                        if(StringUtils.isBlank(agent.getAgLegalCernum()) && StringUtils.isBlank(certNo))
                            throw new ProcessException("请输入结算卡户主身份证");
                        if(StringUtils.isNotBlank(certNo)) {
                            item.setAgLegalCernum(certNo);
                        }
                        if(StringUtils.isBlank(item.getAgLegalCernum())){
                            item.setAgLegalCernum(agent.getAgLegalCernum());
                        }
                    }
                    //三要素认证
                    if (StringUtils.isNotBlank(item.getAgLegalCernum())) {
                        //三要素认证
                        AgentResult result = livenessDetectionService.threeElementsCertificationDetection(trueName, item.getAgLegalCernum(), agent.getcUser(),item.getCloBankAccount());
                        if (!result.isOK()) {
                            throw new ProcessException(result.getMsg());
                        }
                    } else {
                        throw new ProcessException("请输入收款账户名相对应的户主证件号");
                    }

                }
                //对公时 判断收款账户名是否与代理商名称一致 不一致则抛异常提示信息
                if (item.getCloType().compareTo(new BigDecimal(1)) == 0) {
                    if (agentName.equals(trueName)) {
                        item.setAgLegalCernum(agent.getAgLegalCernum());
                    } else if (!agentName.equals(trueName)) {
                        throw new ProcessException("收款账户名与代理商名称不一致");
                    }
                }
                agentColinfoService.agentColinfoInsert(item, item.getColinfoTableFile(),null);
            }
            //判断平台是否重复
            List hav = new ArrayList();
            List<Organization> organList = null;
            Map<String, Object> reqMap = new HashMap<>();
            reqMap.put("agentVo",agentVo);
            for (AgentBusInfoVo item : agentVo.getBusInfoVoList()) {
                //如果业务平台编号不位空，说明是一个升级业务的操作，进行升级条件检查
                if(StringUtils.isNotBlank(item.getBusNum())) {
                    if (!OrgType.zQ(item.getBusType())) {
                        throw new ProcessException("升级类型必须是直签");
                    }
                    if (StringUtils.isBlank(item.getBusParent())){
                        throw new ProcessException("升级直签上级不能为空");
                    }
                    reqMap.put("busInfo",item);
                    AgentResult agentResult = agentNetInNotityService.agencyLevelCheck(reqMap);
                    if(!agentResult.isOK()){
                        throw new ProcessException(agentResult.getMsg());
                    }
                }
                if(OrgType.zQ(item.getBusType())){
                    if(StringUtils.isBlank(item.getBusParent()))
                        throw new ProcessException("直签上级不能为空");
                }

                if (hav.contains(item.getBusPlatform())) {
                    throw new ProcessException("开通(" + item.getBusPlatform() + ")业务平台重复");
                } else {
                    hav.add(item.getBusPlatform());
                }

                if(StringUtils.isBlank(item.getBusScope())){
                    throw new ProcessException("业务范围不能为空");
                }
                if(StringUtils.isBlank(item.getBusUseOrgan())){
                    throw new ProcessException("使用范围不能为空");
                }

                if (null!=item.getBusPlatform()){
                    PlatformType platformType = platFormService.byPlatformCode(item.getBusPlatform());
                    if (null!=platformType){
                        if(PlatformType.whetherPOS(platformType.code)){
                            if (StringUtils.isNotBlank(item.getBusNum())){
                                if (StringUtils.isBlank(item.getBusLoginNum())){
                                    logger.info("请填写平台登录账号");
                                    throw new ProcessException("请填写平台登录账号");
                                }
                            }
                        }
                    }
                }
                //检查业务平台数据
                PlatformType platformType = platFormService.byPlatformCode(item.getBusPlatform());
                if(PlatformType.RDBPOS.code.equals(platformType.getValue())){
                    //检查手机号是否填写
                    if(StringUtils.isBlank(item.getBusLoginNum())){
                        throw new ProcessException("瑞大宝平台登录账号不能为空");
                    }
                    Boolean exist = businessPlatformService.selectByBusLoginNumExist(item.getBusLoginNum(), agent.getId());
                    if(!exist){
                        throw new ProcessException("瑞大宝平台登录账号已入网,请勿重复入网");
                    }
                    item.setBusLoginNum(item.getBusLoginNum().trim());
                    if(!RegexUtil.checkInt(item.getBusLoginNum())){
                        throw new ProcessException("瑞大宝平台登录账号必须为数字");
                    }
                }
                if(PlatformType.RHPOS.code.equals(platformType.getValue())){
                    //检查手机号是否填写
                    if(StringUtils.isBlank(item.getBusLoginNum())){
                        throw new ProcessException("瑞花宝平台登录账号不能为空");
                    }
                    item.setBusLoginNum(item.getBusLoginNum().trim());
                    if(!RegexUtil.checkInt(item.getBusLoginNum())){
                        throw new ProcessException("瑞花宝平台登录账号必须是数字");
                    }
                }
            }
            Set<String> resultSet = new HashSet<>();
            for (AgentBusInfoVo item : agentVo.getBusInfoVoList()) {
                PlatForm platForm = platFormMapper.selectByPlatFormNum(item.getBusPlatform());
                resultSet.add(platForm.getPlatformType());
                item.setcUser(agent.getcUser());
                item.setAgentId(agent.getId());
                item.setCloReviewStatus(AgStatus.Create.status);
                AgentBusInfo db_AgentBusInfo = agentBusinfoService.agentBusInfoInsert(item);
                if (StringUtils.isNotBlank(item.getAgentAssProtocol())) {
                    AssProtoColRel rel = new AssProtoColRel();
                    rel.setAgentBusinfoId(db_AgentBusInfo.getId());
                    rel.setAssProtocolId(item.getAgentAssProtocol());
                    AssProtoCol assProtoCol = assProtoColMapper.selectByPrimaryKey(item.getAgentAssProtocol());
                    if(org.apache.commons.lang.StringUtils.isNotBlank(item.getProtocolRuleValue())){
                        String ruleReplace = assProtoCol.getProtocolRule().replace("{}", item.getProtocolRuleValue());
                        rel.setProtocolRule(ruleReplace);
                    }else{
                        rel.setProtocolRule(assProtoCol.getProtocolRule());
                    }
                    rel.setProtocolRuleValue(item.getProtocolRuleValue());
                    if (1 != agentAssProtocolService.addProtocolRel(rel, agent.getcUser())) {
                        throw new ProcessException("业务分管协议添加失败");
                    }
                }
            }
            if(resultSet.size()>1){
                throw new ProcessException("不同类型平台不能同时提交");
            }
            return ResultVO.success(agentVo);
        } catch (ProcessException e) {
            e.printStackTrace();
            throw e;
        }catch (Exception e) {
            e.printStackTrace();
            throw new ProcessException("商户入网保存失败:"+e.getMessage());
        }
    }

    /**
     * 代理商入网开通的业务平台类型为机构与标准一代时，新增业务平台业务类型也必须为机构或标准一代。
     * @param busInfoVoList
     * @throws Exception
     */
    @Override
    public void verifyOrgAndBZYD(List<AgentBusInfoVo> agentBusInfoVoList, List<AgentBusInfoVo> busInfoVoList) throws Exception {
        Boolean busInfo = false;
        List<String> stringList = new ArrayList<String>();
        for (AgentBusInfoVo agentBusInfoVo : agentBusInfoVoList) {
            if (agentBusInfoVo.getBusType().equals(BusType.JG.key) || agentBusInfoVo.getBusType().equals(BusType.BZYD.key)) {
                BigDecimal cloReviewStatus = agentBusInfoVo.getCloReviewStatus();
                stringList.add(String.valueOf(cloReviewStatus));
            }
            if (agentBusInfoVo.getBusType().equals(BusType.JG.key) || agentBusInfoVo.getBusType().equals(BusType.BZYD.key)) {
                busInfo = true;
            }
        }
        if (busInfo) {
            for (AgentBusInfoVo busInfoVo : busInfoVoList) {
                if (!busInfoVo.getBusType().equals(BusType.JG.key) && !busInfoVo.getBusType().equals(BusType.BZYD.key)) {
//                    throw new ProcessException("当前代理商已有标准一代/机构类型的业务平台，不可再次选择直签类型业务平台");
                    if (stringList.size()>0 && stringList!=null) {
                        for (String str : stringList) {
                            if (!str.equals(String.valueOf(AgStatus.Refuse.status))) {
                                throw new ProcessException("当前代理商已有标准一代/机构类型的业务平台，不可再次选择直签类型业务平台");
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public ResultVO selectPlatType(String platformNum) {
        String platFormType="";
        if (StringUtils.isNotBlank(platformNum)){
            platFormType  = platFormMapper.selectPlatType(platformNum);
        }else {
            throw new ProcessException("请选择品牌");
        }
        return ResultVO.success(platFormType);
    }

    /**
     * （业务市场财务去掉必须是同一个上级限制，此方法废弃 20190313）
     * 代理商新签入网业务平台类型为机构一代、二代直签直发、直签不直发、一代X时，所有业务平台上级必须为同一个。
     * @param busInfoVoList
     * @throws Exception
     */
//    @Override
//    public void verifyOther(List<AgentBusInfoVo> busInfoVoList)throws Exception {
//        Set<String> busParentSet = new HashSet<>();
//        for (AgentBusInfoVo agentBusInfoVo : busInfoVoList) {
//            String busParent = "";
//            if(StringUtils.isNotBlank(agentBusInfoVo.getBusParent())){
//                AgentBusInfo agentBusInfo = agentBusInfoMapper.selectByPrimaryKey(agentBusInfoVo.getBusParent());
//                busParent = agentBusInfo.getAgentId();
//            }else{
//                busParent = "";
//            }
//            busParentSet.add(busParent);
//        }
//        for (AgentBusInfoVo agentBusInfoVo : busInfoVoList) {
//            if(agentBusInfoVo.getBusType().equals(BusType.ZQ.key) || agentBusInfoVo.getBusType().equals(BusType.JGYD.key)
//            || agentBusInfoVo.getBusType().equals(BusType.YDX.key) || agentBusInfoVo.getBusType().equals(BusType.ZQBZF.key)){
//                if(busParentSet.size()!=1){
//                    throw new ProcessException("上级不是同一个");
//                }
//            }
//        }
//    }

    /**
     * 启动代理商审批
     *
     * @param agentId
     * @param cuser
     * @return
     * @throws ProcessException
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
    @Override
    public ResultVO startAgentEnterActivity(String agentId, String cuser) throws ProcessException {
        if (StringUtils.isBlank(agentId)) {
            logger.info("代理商审批,代理商ID为空{}:{}", agentId, cuser);
            return ResultVO.fail("代理商审批中，代理商ID为空");
        }
        if (StringUtils.isBlank(cuser)) {
            logger.info("代理商审批,操作用户为空{}:{}", agentId, cuser);
            return ResultVO.fail("代理商审批中，操作用户为空");
        }

        //检查是否有审批中的代理商新
        BusActRelExample example = new BusActRelExample();
        example.or().andBusIdEqualTo(agentId).andActivStatusEqualTo(AgStatus.Approving.name()).andStatusEqualTo(Status.STATUS_1.status);
        if (busActRelMapper.selectByExample(example).size() > 0) {
            logger.info("代理商审批,禁止重复提交审批{}:{}", agentId, cuser);
            return ResultVO.fail("代理商审批中，禁止重复提交审批");
        }

        Agent agent = agentService.getAgentById(agentId);
        if (agent.getAgStatus().equals(AgStatus.Approving.name())) {
            logger.info("代理商审批,禁止重复提交审批{}:{}", agentId, cuser);
            return ResultVO.fail("代理商审批中，禁止重复提交审批");
        }
        if (!agent.getStatus().equals(Status.STATUS_1.status)) {
            logger.info("代理商审批中,代理商信息已失效{}:{}", agentId, cuser);
            return ResultVO.fail("代理商信息已失效");
        }

        //更新代理商审批中
        agent.setAgStatus(AgStatus.Approving.name());
        if (1 != agentService.updateAgent(agent)) {
            logger.info("代理商审批，更新代理商基本信息失败{}:{}", agentId, cuser);
            throw new ProcessException("启动审批异常，更新代理商基本信息失败");
        }

        //获取代理商有效的业务
        List<AgentBusInfo> aginfo = agentBusinfoService.agentBusInfoList(agent.getId());
        for (AgentBusInfo agentBusInfo : aginfo) {
            agentBusInfo.setcUtime(Calendar.getInstance().getTime());
            agentBusInfo.setCloReviewStatus(AgStatus.Approving.status);
            if (agentBusinfoService.updateAgentBusInfo(agentBusInfo) != 1) {
                logger.info("代理商审批，更新业务本信息失败{}:{}", agentId, cuser);
                throw new ProcessException("启动审批异常，更新业务本信息失败");
            }
        }
        //代理商有效新建的合同
        List<AgentContract> ag = agentContractService.queryAgentContract(agentId, null, AgStatus.Create.status);
        for (AgentContract contract : ag) {
            contract.setCloReviewStatus(AgStatus.Approving.status);
            if (1 != agentContractService.update(contract)) {
                logger.info("代理商审批，合同状态更新失败{}:{}", agentId, cuser);
                throw new ProcessException("合同状态更新失败");
            }
        }

        //代理商有效的新建的收款账户
        List<AgentColinfo> clolist = agentColinfoService.queryAgentColinfoService(agentId, null, AgStatus.Create.status);
        for (AgentColinfo agentColinfo : clolist) {
            agentColinfo.setCloReviewStatus(AgStatus.Approving.status);
            if (1 != agentColinfoService.update(agentColinfo)) {
                logger.info("代理商审批，合同状态更新失败{}:{}", agentId, cuser);
                throw new ProcessException("合同状态更新失败");
            }
        }

        List<Capital> capitals = accountPaidItemService.queryCap(agentId, null, null, AgStatus.Create.status);
        for (Capital capital : capitals) {
            capital.setCloReviewStatus(AgStatus.Approving.status);
            if (1 != accountPaidItemService.update(capital)) {
                logger.info("代理商审批，合同状态更新失败{}:{}", agentId, cuser);
                throw new ProcessException("合同状态更新失败");
            }
        }
        Map startPar = startPar(cuser);
        if (null == startPar) {
            logger.info("========用户{}{}启动部门参数为空", agentId, cuser);
            throw new ProcessException("启动部门参数为空!");
        }
        if(startPar.get("party").toString().equals("beijing")) {
            startPar.put("rs", ApprovalType.PASS.getValue());
        }
        //启动审批
        String proce = activityService.createDeloyFlow(null, dictOptionsService.getApproveVersion("net"), null, null, startPar);
        if (proce == null) {
            logger.info("代理商审批，审批流启动失败{}:{}", agentId, cuser);
            throw new ProcessException("审批流启动失败!");
        }

        //代理商业务视频关系
        BusActRel record = new BusActRel();
        record.setBusId(agentId);
        record.setActivId(proce);
        record.setcTime(Calendar.getInstance().getTime());
        record.setcUser(cuser);
        record.setStatus(Status.STATUS_1.status);
        record.setBusType(BusActRelBusType.Agent.name());
        record.setActivStatus(AgStatus.Approving.name());
        record.setAgentId(agent.getId());
        record.setAgentName(agent.getAgName());
        record.setDataShiro(BusActRelBusType.Agent.key);

        AgentBusInfo agentBusInfo = aginfo.get(0);
//            PlatForm platForm = platFormMapper.selectByPlatFormNum(agentBusInfo.getBusPlatform());
        record.setNetInBusType("ACTIVITY_"+agentBusInfo.getBusPlatform());
        record.setAgDocPro(agentBusInfo.getAgDocPro());
        record.setAgDocDistrict(agentBusInfo.getAgDocDistrict());
        if (StringUtils.isNotBlank(agentBusInfo.getBusNum())){
            record.setExplain(agentBusInfo.getBusNum());
        }
        if (1 != busActRelMapper.insertSelective(record)) {
            logger.info("代理商审批，启动审批异常，添加审批关系失败{}:{}", agentId, proce);
        }
        return ResultVO.success(null);
    }


    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
    @Override
    public ResultVO startAgentBusiEnterActivity(String busid, String cuser) throws MessageException {
        AgentBusInfo abus = agentBusinfoService.getById(busid);
        if (abus == null) {
            logger.info("代理商信息审批中,业务信息未找到{}:{}", busid, cuser);
            throw new MessageException("业务信息未找到");
        }

        //检查业务平台数据
        PlatForm platForm = platFormMapper.selectByPlatFormNum(abus.getBusPlatform());
        if(platForm==null){
            throw new MessageException("业务平台不存在");
        }

        PlatformType platformType = platFormService.byPlatformCode(abus.getBusPlatform());
        if(PlatformType.RDBPOS.code.equals(platformType.getValue())){
            //检查手机号是否填写
            if(StringUtils.isBlank(abus.getBusLoginNum())){
                throw new MessageException("瑞大宝平台登录账号不能为空");
            }
            if(!RegexUtil.checkInt(abus.getBusLoginNum())){
                throw new MessageException("瑞大宝平台登录账号必须为数字");
            }
            if(abus.getBusLoginNum().length()!=11){
                throw new MessageException("手机位数不正确");
            }
        }
        if(PlatformType.RHPOS.code.equals(platformType.getValue())){
            //检查手机号是否填写
            if(StringUtils.isBlank(abus.getBusLoginNum())){
                throw new MessageException("瑞花宝平台登录账号不能为空");
            }
            if(!RegexUtil.checkInt(abus.getBusLoginNum())){
                throw new MessageException("瑞花宝平台登录账号必须是数字");
            }
            if(abus.getBusLoginNum().length()!=11){
                throw new MessageException("手机位数不正确");
            }
        }
        //检查是否有审批中的代理商新
        Agent agent = agentService.getAgentById(abus.getAgentId());
        if (agent.getAgStatus().equals(AgStatus.Approving.name())) {
            logger.info("代理商信息审批中,禁止启动业务审批{}:{}", busid, cuser);
            throw new MessageException("代理商信息审批中,禁止启动业务审批");
        }
        if (!agent.getAgStatus().equals(AgStatus.Approved.name())) {
            logger.info("代理商信息未审批完成,禁止启动业务审批{}:{}", busid, cuser);
            throw new MessageException("代理商信息未审批完成,禁止启动业务审批");
        }
        if (!agent.getStatus().equals(Status.STATUS_1.status)) {
            logger.info("代理商信息已失效{}:{}", busid, cuser);
            throw new MessageException("代理商信息已失效");
        }

        BusActRelExample example = new BusActRelExample();
        example.or().andBusIdEqualTo(abus.getId()).andBusTypeEqualTo(BusActRelBusType.Business.name()).andActivStatusEqualTo(AgStatus.Approving.name()).andStatusEqualTo(Status.STATUS_1.status);
        if (busActRelMapper.selectByExample(example).size() > 0) {
            logger.info("代理商审批中，禁止重复提交审批{}:{}", busid, cuser);
            throw new MessageException("代理商审批中，禁止重复提交审批");
        }

        //获取代理商有效的业务
        abus.setcUtime(Calendar.getInstance().getTime());
        abus.setCloReviewStatus(AgStatus.Approving.status);
        if (agentBusinfoService.updateAgentBusInfo(abus) != 1) {
            logger.info("代理商业务启动审批异常，更新业务本信息失败{}:{}", busid, cuser);
            throw new MessageException("代理商业务启动审批异常，更新业务本信息失败");
        }



        //代理商有效的新建的收款账户
        List<AgentColinfo> clolist = agentColinfoService.queryAgentColinfoService(abus.getAgentId(), null, AgStatus.Create.status);
        for (AgentColinfo agentColinfo : clolist) {
            agentColinfo.setCloReviewStatus(AgStatus.Approving.status);
            if (1 != agentColinfoService.update(agentColinfo)) {
                logger.info("代理商业务启动审批异常，收款账户状态更新失败{}:{}", busid, cuser);
                throw new MessageException("收款账户状态更新失败");
            }
        }

        Map startPar = startPar(cuser);
        if (null == startPar) {
            logger.info("========用户{}{}启动部门参数为空", busid, cuser);
            throw new MessageException("启动部门参数为空!");
        }
        if(startPar.get("party").toString().equals("beijing")) {
            startPar.put("rs", ApprovalType.PASS.getValue());
        }
        //启动审批
        String proce = activityService.createDeloyFlow(null, dictOptionsService.getApproveVersion("net"), null, null, startPar);
        if (proce == null) {
            logger.info("代理商业务启动审批异常，审批流启动失败{}:{}", busid, cuser);
            throw new MessageException("审批流启动失败!");
        }
        //代理商有效新建的合同
        List<AgentContract> ag = agentContractService.queryAgentContract(abus.getAgentId(), null, AgStatus.Create.status);
        for (AgentContract contract : ag) {
            contract.setActivId(proce);
            contract.setCloReviewStatus(AgStatus.Approving.status);
            if (1 != agentContractService.update(contract)) {
                logger.info("代理商业务启动审批异常，合同状态更新失败{}:{}", busid, cuser);
                throw new MessageException("合同状态更新失败");
            }
        }
        List<Capital> capitals = accountPaidItemService.queryCap(abus.getAgentId(), null, null, AgStatus.Create.status);
        for (Capital capital : capitals) {
            capital.setActivId(proce);
            capital.setCloReviewStatus(AgStatus.Approving.status);
            if (1 != accountPaidItemService.update(capital)) {
                logger.info("代理商审批，缴款状态更新失败{}:{}", abus.getAgentId(), cuser);
                throw new MessageException("缴款状态更新失败");
            }
        }
        //代理商业务视频关系
        BusActRel record = new BusActRel();
        record.setBusId(abus.getId());
        record.setActivId(proce);
        record.setcTime(Calendar.getInstance().getTime());
        record.setcUser(cuser);
        record.setStatus(Status.STATUS_1.status);
        record.setBusType(BusActRelBusType.Business.name());
        record.setActivStatus(AgStatus.Approving.name());
        record.setAgentId(agent.getId());
        record.setAgentName(agent.getAgName());
        record.setDataShiro(BusActRelBusType.Business.key);
        record.setAgDocPro(abus.getAgDocPro());
        record.setAgDocDistrict(abus.getAgDocDistrict());
        record.setNetInBusType("ACTIVITY_"+platForm.getPlatformNum());

        if (StringUtils.isNotBlank(abus.getBusNum())){
            record.setExplain(abus.getBusNum());
        }
        if (1 != busActRelMapper.insertSelective(record)) {
            logger.info("代理商业务启动审批异常，添加审批关系失败{}:{}", record.getBusId(), proce);
            throw new MessageException("添加审批关系失败!");
        }
        return ResultVO.success(null);
    }


    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
    @Override
    public AgentResult completeTaskEnterActivity(AgentVo agentVo, String userId) throws ProcessException {

        AgentResult result = new AgentResult(500, "系统异常", "");
        Map<String, Object> reqMap = new HashMap<>();
        reqMap.put("rs", agentVo.getApprovalResult());
        reqMap.put("approvalOpinion", agentVo.getApprovalOpinion());
        reqMap.put("approvalPerson", userId);
        reqMap.put("createTime", DateUtils.dateToStringss(new Date()));
        reqMap.put("taskId", agentVo.getTaskId());
        reqMap.put("dept", agentVo.getDept());
        if(StringUtils.isNotBlank(agentVo.getMainDocDistrict()) && StringUtils.isNotBlank(agentVo.getSubDocDistrict())){
            reqMap.put(agentVo.getMainDocDistrict(),agentVo.getMainDocDistrict());
            reqMap.put(agentVo.getSubDocDistrict(),agentVo.getSubDocDistrict());
            if(!reqMap.containsValue("beijing")){
                reqMap.put("beijing","");
            }
            if(!reqMap.containsValue("south")){
                reqMap.put("south","");
            }
            if(!reqMap.containsValue("north")){
                reqMap.put("north","");
            }
        }

        if(StringUtils.isNotBlank(agentVo.getOperationType())){
            reqMap.put("operationType", agentVo.getOperationType());
        }

        if(agentVo.getAmt() != null){
            reqMap.put("amt", agentVo.getAmt());
        }
        if(StringUtils.isNotBlank(agentVo.getRenewWay())){
            reqMap.put("renewWay", agentVo.getRenewWay());
        }

        //传递部门信息
        Map startPar = startPar(userId);
        if (null != startPar) {
            ActRuTask actRuTask = actRuTaskService.selectByPrimaryKey(agentVo.getTaskId());
            if(actRuTask==null){
                return result;
            }
            String[] procDefId = String.valueOf(actRuTask.getProcDefId()).split(":");
            if(procDefId==null){
                return result;
            }
            String taskView = procDefId[0];
            String[] taskViewVersion = taskView.split("_");
            if(taskViewVersion.length>=2){
                BigDecimal version = new BigDecimal(taskViewVersion[1]);
                if(version.compareTo(new BigDecimal("3.0"))>=0){
                    reqMap.put("party", startPar.get("party"));
                }else{
                    reqMap.put("party", "north");
                }
            }else{
                reqMap.put("party", "north");
            }
        }

        Map resultMap = activityService.completeTask(agentVo.getTaskId(), reqMap);
        Boolean rs = (Boolean) resultMap.get("rs");
        String msg = String.valueOf(resultMap.get("msg"));
        if (resultMap == null) {
            return result;
        }
        if (!rs) {
            result.setMsg(msg);
            return result;
        }
        return AgentResult.ok(resultMap);
    }



    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public ResultVO completeProcessing(String processingId, String processingStatus) throws ProcessException {
        BusActRelExample example = new BusActRelExample();
        example.or().andActivIdEqualTo(processingId).andActivStatusEqualTo(AgStatus.Approving.name());
        List<BusActRel> list = busActRelMapper.selectByExample(example);
        if (list.size() != 1) {
            logger.info("审批任务结束{}{}，未找到审批中的审批和数据关系", processingId, processingStatus);
            return ResultVO.fail("审批任务结束" + processingId + ":" + processingStatus + "未找到审批中的审批和数据关系");
        }
        BusActRel rel = list.get(0);
        if (rel.getBusType().equals(BusActRelBusType.Business.name())) {
            //审批通过
            if (AgStatus.Approved.name().equals(processingStatus)) {
                return processingBusAproveApproved(rel, processingId, rel.getBusId());
            }
            //审批拒绝
            if (AgStatus.Refuse.name().equals(processingStatus)) {
                return processingBusAproveRefuse(rel, processingId, rel.getBusId());
            }
        }
        if (rel.getBusType().equals(BusActRelBusType.Agent.name())) {
            //审批通过
            if (AgStatus.Approved.name().equals(processingStatus)) {
                return processingAgentApproved(rel, processingId, rel.getBusId());
            }
            //审批拒绝
            if (AgStatus.Refuse.name().equals(processingStatus)) {
                return processingAgentRefuse(rel, processingId, rel.getBusId());
            }
        }
        return ResultVO.success("");
    }


    /**
     * 业务审批同意
     *
     * @param processingId
     * @param busId
     * @return
     */
    private ResultVO processingBusAproveApproved(BusActRel rel, String processingId, String busId) {
        rel.setActivStatus(AgStatus.Approved.name());
        if (1 != busActRelMapper.updateByPrimaryKeySelective(rel)) {
            logger.info("代理商审批通过，更新BusActRel失败{}:{}", processingId, rel.getBusId());
            throw new ProcessException("更新ActRel失败");
        }

        AgentBusInfo bus = agentBusinfoService.getById(busId);
        bus.setcUtime(Calendar.getInstance().getTime());
        bus.setCloReviewStatus(AgStatus.Approved.status);
        bus.setApproveTime(Calendar.getInstance().getTime());//审批通过时间
        if(StringUtils.isNotBlank(bus.getBusNum())){
            bus.setBusStatus(BusinessStatus.pause.status);
        }
        if (agentBusinfoService.updateAgentBusInfo(bus) != 1) {
            logger.info("代理商审批通过，更新业务本信息失败{}:{}", processingId, bus.getId());
            throw new ProcessException("代理商审批通过，更新业务本信息失败");
        }
        //代理商有效新建的合同
        List<AgentContract> ag = agentContractService.queryAgentContract(bus.getAgentId(), null, AgStatus.Approving.status);
        for (AgentContract contract : ag) {
            contract.setCloReviewStatus(AgStatus.Approved.status);
            if (1 != agentContractService.update(contract)) {
                logger.info("代理商审批通过，合同状态更新失败{}:{}", processingId, contract.getId());
                throw new ProcessException("合同状态更新失败");
            }
        }

        //代理商有效的新建的收款账户
        List<AgentColinfo> clolist = agentColinfoService.queryAgentColinfoService(bus.getAgentId(), null, AgStatus.Approving.status);
        for (AgentColinfo agentColinfo : clolist) {
            agentColinfo.setCloReviewStatus(AgStatus.Approved.status);
            if (1 != agentColinfoService.update(agentColinfo)) {
                logger.info("代理商审批通过，收款状态更新失败{}:{}", processingId, agentColinfo.getId());
                throw new ProcessException("收款状态更新失败");
            }
        }
        //缴款
        List<Capital> capitals = accountPaidItemService.queryCap(bus.getAgentId(), null, null, AgStatus.Approving.status);
        for (Capital capital : capitals) {
            capital.setCloReviewStatus(AgStatus.Approved.status);
            if (1 != accountPaidItemService.update(capital)) {
                logger.info("代理商审批通过，合同状态更新失败{}:{}", processingId, bus.getId());
                throw new ProcessException("合同状态更新失败");
            }
            if(PayType.FRDK.code.equals(capital.getcPayType())) {
                try {
                    //生成保证金等分期数据
                    AgentResult capitalFq = accountPaidItemService.capitalFq(capital);
                    if (!capitalFq.isOK()) {
                        logger.info("代理商审批，保证金{}:{}", processingId, capital.getId());
                        throw new ProcessException("生成保证金分期失败");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new ProcessException(e.getMessage());
                }
            }
            //插入资金流水
            try {
                capitalFlowService.insertCapitalFlow(capital,BigDecimal.ZERO,busId,"代理商新增业务");
            } catch (Exception e) {
                e.printStackTrace();
                throw new ProcessException("新增资金流水失败");
            }
        }
//        入网程序调用
//        try {
//            ImportAgent importAgent = new ImportAgent();
//            importAgent.setDataid(busId);
//            importAgent.setDatatype(AgImportType.BUSAPP.name());
//            importAgent.setBatchcode(processingId);
//            importAgent.setcUser(rel.getcUser());
//            if (1 != aimportService.insertAgentImportData(importAgent)) {
//                logger.info("代理商审批通过-添加开户任务失败");
//            } else {
//                logger.info("代理商审批通过-添加开户任务成功!{},{}", AgImportType.BUSAPP.getValue(), busId);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
        agentNetInNotityService.asynNotifyPlatform(busId,NotifyType.NetInAddBus.getValue());
//        }
        return ResultVO.success(null);
    }

    /**
     * 业务审批拒绝
     *
     * @param processingId
     * @param busId
     * @return
     */
    private ResultVO processingBusAproveRefuse(BusActRel rel, String processingId, String busId) {
        rel.setActivStatus(AgStatus.Refuse.name());
        if (1 != busActRelMapper.updateByPrimaryKeySelective(rel)) {
            logger.info("代理商审批拒绝，更新BusActRel失败{}:{}", processingId, rel.getBusId());
        }

        AgentBusInfo bus = agentBusinfoService.getById(busId);
        bus.setcUtime(Calendar.getInstance().getTime());
        bus.setCloReviewStatus(AgStatus.Refuse.status);
        bus.setApproveTime(Calendar.getInstance().getTime());//审批通过时间
        bus.setBusStatus(BusinessStatus.pause.status);
        if (agentBusinfoService.updateAgentBusInfo(bus) != 1) {
            logger.info("代理商审批拒绝，更新业务本信息失败{}:{}", processingId, bus.getId());
            throw new ProcessException("代理商审批通过，更新业务本信息失败");
        }

        //代理商有效新建的合同
        List<AgentContract> ag = agentContractService.queryAgentContract(bus.getAgentId(), null, AgStatus.Approving.status);
        for (AgentContract contract : ag) {
            contract.setCloReviewStatus(AgStatus.Refuse.status);
            if (1 != agentContractService.update(contract)) {
                logger.info("代理商审批拒绝，合同状态更新失败{}:{}", processingId, contract.getId());
                throw new ProcessException("合同状态更新失败");
            }
        }

        //代理商有效的新建的收款账户
        List<AgentColinfo> clolist = agentColinfoService.queryAgentColinfoService(bus.getAgentId(), null, AgStatus.Approving.status);
        for (AgentColinfo agentColinfo : clolist) {
            agentColinfo.setCloReviewStatus(AgStatus.Refuse.status);
            if (1 != agentColinfoService.update(agentColinfo)) {
                logger.info("代理商审批拒绝，收款状态更新失败{}:{}", processingId, agentColinfo.getId());
                throw new ProcessException("收款状态更新失败");
            }
        }

        List<Capital> capitals = accountPaidItemService.queryCap(bus.getAgentId(), null, null, AgStatus.Approving.status);
        for (Capital capital : capitals) {
            capital.setCloReviewStatus(AgStatus.Refuse.status);
            capital.setStatus(Status.STATUS_0.status);
            if (1 != accountPaidItemService.update(capital)) {
                logger.info("代理商审批，合同状态更新失败{}:{}", processingId, bus.getId());
                throw new ProcessException("合同状态更新失败");
            }
        }

        return ResultVO.success(null);
    }

    /**
     * 代理商业务审批同意
     *
     * @param processingId
     * @param busId
     * @return
     */
    private ResultVO processingAgentApproved(BusActRel rel, String processingId, String busId) {
        rel.setActivStatus(AgStatus.Approved.name());
        if (1 != busActRelMapper.updateByPrimaryKeySelective(rel)) {
            logger.info("代理商审批通过，更新BusActRel失败{}:{}", processingId, rel.getBusId());
            throw new ProcessException("更新ActRel失败");
        }

        Agent agent = agentService.getAgentById(busId);
        agent.setAgStatus(AgStatus.Approved.name());
        agent.setAgUniqNum(agent.getId());
        agent.setcIncomTime(Calendar.getInstance().getTime());
        if (1 != agentService.updateAgent(agent)) {
            logger.info("代理商审批通过，代理商信息失败{}:{}", processingId, agent.getId());
            throw new ProcessException("代理商信息失败");
        }
        //入网合同冻结
        try {
            AgentFreezePort agentFreezePort = new AgentFreezePort();
            agentFreezePort.setAgentId(agent.getId());
            agentFreezePort.setFreezeCause(FreeCause.HTDJ.getValue());
            agentFreezePort.setOperationPerson(agent.getcUser());
            agentFreezePort.setFreezeNum(agent.getId());
            AgentResult agentResult = agentFreezeService.agentFreeze(agentFreezePort);
            if(!agentResult.isOK()){
                throw new ProcessException(agentResult.getMsg());
            }
        } catch (MessageException e) {
            e.printStackTrace();
            throw new ProcessException(e.getMsg());
        }
        //获取代理商有效的业务
        List<AgentBusInfo> aginfo = agentBusinfoService.agentBusInfoList(agent.getId(), null, AgStatus.Approving.status);
        for (AgentBusInfo agentBusInfo : aginfo) {
            if(StringUtils.isNotBlank(agentBusInfo.getBusNum())){
                agentBusInfo.setBusStatus(BusinessStatus.pause.status);
            }
            agentBusInfo.setcUtime(Calendar.getInstance().getTime());
            agentBusInfo.setCloReviewStatus(AgStatus.Approved.status);
            agentBusInfo.setApproveTime(Calendar.getInstance().getTime());//审批通过时间
            if (agentBusinfoService.updateAgentBusInfo(agentBusInfo) != 1) {
                logger.info("代理商审批通过，更新业务本信息失败{}:{}", processingId, agentBusInfo.getId());
                throw new ProcessException("代理商审批通过，更新业务本信息失败");
            }
        }

        //代理商有效新建的合同
        List<AgentContract> ag = agentContractService.queryAgentContract(agent.getId(), null, AgStatus.Approving.status);
        for (AgentContract contract : ag) {
            contract.setCloReviewStatus(AgStatus.Approved.status);
            if (1 != agentContractService.update(contract)) {
                logger.info("代理商审批通过，合同状态更新失败{}:{}", processingId, contract.getId());
                throw new ProcessException("合同状态更新失败");
            }
        }

        //代理商有效的新建的收款账户
        List<AgentColinfo> clolist = agentColinfoService.queryAgentColinfoService(agent.getId(), null, AgStatus.Approving.status);
        for (AgentColinfo agentColinfo : clolist) {
            agentColinfo.setCloReviewStatus(AgStatus.Approved.status);
            if (1 != agentColinfoService.update(agentColinfo)) {
                logger.info("代理商审批通过，收款状态更新失败{}:{}", processingId, agentColinfo.getId());
                throw new ProcessException("收款状态更新失败");
            }
        }
        List<Capital> capitals = accountPaidItemService.queryCap(agent.getId(), null, null, AgStatus.Approving.status);
        for (Capital capital : capitals) {
            capital.setCloReviewStatus(AgStatus.Approved.status);
            if (1 != accountPaidItemService.update(capital)) {
                logger.info("代理商审批，合同状态更新失败{}:{}",processingId, capital.getId());
                throw new ProcessException("合同状态更新失败");
            }
            if(PayType.FRDK.code.equals(capital.getcPayType())) {
                try {
                    //生成保证金等分期数据
                    AgentResult capitalFq = accountPaidItemService.capitalFq(capital);
                    if (!capitalFq.isOK()) {
                        logger.info("代理商审批，保证金{}:{}", processingId, capital.getId());
                        throw new ProcessException("生成保证金等分期数据更新失败");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new ProcessException(e.getMessage());
                }
            }
            try {
                capitalFlowService.insertCapitalFlow(capital,BigDecimal.ZERO,busId,"代理商入网");
            } catch (Exception e) {
                e.printStackTrace();
                throw new ProcessException("新增资金流水失败");
            }
        }
        //入网程序调用
//        try {
//            ImportAgent importAgent = new ImportAgent();
//            importAgent.setDataid(busId);
//            importAgent.setDatatype(AgImportType.NETINAPP.name());
//            importAgent.setBatchcode(processingId);
//            importAgent.setcUser(rel.getcUser());
//            if (1 != aimportService.insertAgentImportData(importAgent)) {
//                logger.info("代理商审批通过-添加开户任务失败");
//            } else {
//                logger.info("代理商审批通过-添加开户任务成功!{},{}", AgImportType.NETINAPP.getValue(), busId);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
        //todo 生成后台用户
        agentService.createBackUserbyAgent(agent.getId());
        agentNetInNotityService.asynNotifyPlatform(busId,NotifyType.NetInAdd.getValue());
//        }

        return ResultVO.success(null);
    }

    /**
     * 代理商业务审批拒绝
     *
     * @param processingId
     * @param busId
     * @return
     */
    private ResultVO processingAgentRefuse(BusActRel rel, String processingId, String busId) {
        rel.setActivStatus(AgStatus.Refuse.name());
        if (1 != busActRelMapper.updateByPrimaryKeySelective(rel)) {
            logger.info("代理商审批拒绝，更新BusActRel失败{}:{}", processingId, rel.getBusId());
        }

        Agent agent = agentService.getAgentById(busId);
        agent.setAgStatus(AgStatus.Refuse.name());
        if (1 != agentService.updateAgent(agent)) {
            logger.info("代理商审批拒绝，代理商信息失败{}:{}", processingId, agent.getId());
        }

        //获取代理商有效的业务
        List<AgentBusInfo> aginfo = agentBusinfoService.agentBusInfoList(agent.getId(), null, AgStatus.Approving.status);
        for (AgentBusInfo agentBusInfo : aginfo) {
            agentBusInfo.setcUtime(Calendar.getInstance().getTime());
            agentBusInfo.setCloReviewStatus(AgStatus.Refuse.status);
            agentBusInfo.setApproveTime(Calendar.getInstance().getTime());//审批通过时间
            agentBusInfo.setBusStatus(BusinessStatus.pause.status);
            if (agentBusinfoService.updateAgentBusInfo(agentBusInfo) != 1) {
                logger.info("代理商审批拒绝，更新业务本信息失败{}:{}", processingId, agentBusInfo.getId());
                throw new ProcessException("代理商审批通过，更新业务本信息失败");
            }
        }

        //代理商有效新建的合同
        List<AgentContract> ag = agentContractService.queryAgentContract(agent.getId(), null, AgStatus.Approving.status);
        for (AgentContract contract : ag) {
            contract.setCloReviewStatus(AgStatus.Refuse.status);
            if (1 != agentContractService.update(contract)) {
                logger.info("代理商审批拒绝，合同状态更新失败{}:{}", processingId, contract.getId());
                throw new ProcessException("合同状态更新失败");
            }
        }

        //代理商有效的新建的收款账户
        List<AgentColinfo> clolist = agentColinfoService.queryAgentColinfoService(agent.getId(), null, AgStatus.Approving.status);
        for (AgentColinfo agentColinfo : clolist) {
            agentColinfo.setCloReviewStatus(AgStatus.Refuse.status);
            if (1 != agentColinfoService.update(agentColinfo)) {
                logger.info("代理商审批拒绝，收款状态更新失败{}:{}", processingId, agentColinfo.getId());
                throw new ProcessException("收款状态更新失败");
            }
        }
        List<Capital> capitals = accountPaidItemService.queryCap(agent.getId(), null, null, AgStatus.Approving.status);
        for (Capital capital : capitals) {
            capital.setCloReviewStatus(AgStatus.Refuse.status);
            if (1 != accountPaidItemService.update(capital)) {
                logger.info("代理商审批，合同状态更新失败{}:{}",processingId, capital.getId());
                throw new ProcessException("合同状态更新失败");
            }
        }
        return ResultVO.success(null);
    }


    /**
     * 信息修改
     * @param agent
     * @param userId
     * @param isPass  是否审批通过  审批通过传true ,未提交审批修改传 false
     * @return
     * @throws Exception
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
    @Override
    public ResultVO updateAgentVo(AgentVo agent, String userId,Boolean isPass,String saveStatus) throws MessageException {

        AgentVo agentVo = agent;
        try {
//            verifyOrgAndBZYD(agent.getBusInfoVoList());
            logger.info("用户{}{}修改代理商信息{}", userId, agent.getAgent().getId(), JSONObject.toJSONString(agent));
            //根据工商编号验证是否已经存在，禁止重复提交代理商
            if(agent.getAgent()!=null && StringUtils.isNotBlank(agent.getAgent().getAgBusLic())) {
                AgentResult agentResult =  agentService.checkAgBusLicIsEst(agent.getAgent().getAgName(),agent.getAgent().getAgBusLic());
                if(agentResult.isOK()){
                    if(agent.getAgent()!=null && StringUtils.isNotBlank(agent.getAgent().getId()) && !agent.getAgent().getId().equals(agentResult.getData())) {
                        return ResultVO.fail(agent.getAgent().getAgBusLic()+"已存在，编号为："+agentResult.getData());
                    }
                }
            }
            Agent ag = null;
            if (StringUtils.isNotBlank(agent.getAgent().getAgName())) {
                ag = agentService.updateAgentVo(agent.getAgent(), agent.getAgentTableFile(),userId,saveStatus);
            }
            logger.info("用户{}{}修改代理商信息结果{}", userId, agent.getAgent().getId(), "成功");

            if (agent.getCapitalVoList() != null && agent.getCapitalVoList().size() > 0) {
                logger.info("用户{}{}修改代理商收款信息{}", userId, agent.getAgent().getId(), JSONObject.toJSONString(agent.getCapitalVoList()));
                ResultVO updateAccountPaidUpdateRes = accountPaidItemService.updateListCapitalVo(agent.getCapitalVoList(), agent.getAgent(),userId,isPass);
                logger.info("用户{}{}修改代理商收款信息结果{}", userId, agent.getAgent().getId(), updateAccountPaidUpdateRes.getResInfo());
            }
            if (agent.getContractVoList() != null && agent.getContractVoList().size() > 0) {
                logger.info("用户{}{}修改代理商合同信息{}", userId, agent.getAgent().getId(), JSONObject.toJSONString(agent.getContractVoList()));
                ResultVO updateAgentContractVoRes = agentContractService.updateAgentContractVo(agent.getContractVoList(), agent.getAgent(),userId);
                logger.info("用户{}{}修改代理商合同信息结果{}", userId, agent.getAgent().getId(), updateAgentContractVoRes.getResInfo());
            }
            if (agent.getColinfoVoList() != null && agent.getColinfoVoList().size() > 0) {
                logger.info("用户{}{}修改代理商收款信息{}", userId, agent.getAgent().getId(), JSONObject.toJSONString(agent.getColinfoVoList()));
                ResultVO updateAgentColinfoVoRes = agentColinfoService.updateAgentColinfoVo(agent.getColinfoVoList(), agent.getAgent(),userId,saveStatus);
                logger.info("用户{}{}修改代理商收款信息结果{}", userId, agent.getAgent().getId(), updateAgentColinfoVoRes.getResInfo());
            }
            if (agent.getBusInfoVoList() != null && agent.getBusInfoVoList().size() > 0) {
                logger.info("用户{}{}修改代理商业务信息{}", userId, agent.getAgent().getId(), JSONObject.toJSONString(agent.getBusInfoVoList()));
                ResultVO updateAgentBusInfoVoRes = agentBusinfoService.updateAgentBusInfoVo(agentVo, agent.getBusInfoVoList(), agent.getAgent(),userId,isPass,saveStatus);
                logger.info("用户{}{}修改代理商业务信息结果{}", userId, agent.getAgent().getId(), updateAgentBusInfoVoRes.getResInfo());
            }

            return ResultVO.success(ag);
        } catch (ProcessException e) {
            logger.error("修改代理商错误", e.getMsg());
            throw new MessageException(e.getMsg());
        } catch (MessageException e) {
            e.printStackTrace();
            logger.error("修改代理商错误", e);
            throw new MessageException(e.getMsg());
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("修改代理商错误", e);
            throw new MessageException("修改代理商错误");
        }
    }

    @Override
    public Map startPar(String cuserId) {
        if (StringUtils.isBlank(cuserId)) {
            logger.info("startPar用户ID为空");
            return null;
        }
        List<Map<String, Object>> orgCodeRes = iUserService.orgCode(Long.valueOf(cuserId));
        List<Dict> disc = dictOptionsService.dictList(DictGroup.AGENT.name(), DictGroup.ACTIVITY_RESPAR.name());
        Map res = null;
        for (Map<String, Object> orgCodeRe : orgCodeRes) {
            for (Dict dict : disc) {
                if (Pattern.matches(dict.getdItemvalue(), orgCodeRe.get("ORGANIZATIONCODE") + "")) {
                    res = FastMap.fastMap("party", dict.getdItemname());
                    return res;
                }
            }
        }
        return res;
    }


    @Override
    public List<AgentoutVo> exportAgent(Map map,Long userId) throws ParseException {
        //加载缓存
        if (null != map) {
            String time = String.valueOf(map.get("time"));
            if (org.apache.commons.lang.StringUtils.isNotBlank(time)&&!time.equals("null")) {
                String reltime = time.substring(0, 10);
                map.put("time", reltime);
            }
        }
        List<Map> platfromPerm = iResourceService.userHasPlatfromPerm(userId);
        map.put("platfromPerm",platfromPerm);
        List<AgentoutVo> agentoutVos = agentMapper.excelAgent(map);
        if (null==agentoutVos && agentoutVos.size()<1)
            return null;
        List<Dict> BUS_TYPE = dictOptionsService.dictList(DictGroup.AGENT.name(), DictGroup.BUS_TYPE.name());
        List<Dict> BUS_SCOPE = dictOptionsService.dictList(DictGroup.AGENT.name(), DictGroup.BUS_SCOPE.name());
        List<Dict> COLINFO_TYPE = dictOptionsService.dictList(DictGroup.AGENT.name(), DictGroup.COLINFO_TYPE.name());
        List<Dict> REPORT_STATUS = dictOptionsService.dictList(DictGroup.AGENT.name(), DictGroup.REPORT_STATUS.name());

        if (null != agentoutVos && agentoutVos.size() > 0)
            for (AgentoutVo agentoutVo : agentoutVos) {
                if (StringUtils.isNotBlank(agentoutVo.getBusType()) && !agentoutVo.getBusType().equals("null")) {
                    for (Dict dict : BUS_TYPE) {
                        if (null!=dict  &&  agentoutVo.getBusType().equals(dict.getdItemvalue())){
                            agentoutVo.setBusType(dict.getdItemname());
                            break;
                        }
                    }
                }

                if (StringUtils.isNotBlank(agentoutVo.getBusScope()) && !agentoutVo.getBusScope().equals("null")) {
                    for (Dict dict : BUS_SCOPE) {
                        if (null!=dict  &&  agentoutVo.getBusScope().equals(dict.getdItemvalue())){
                            agentoutVo.setBusScope(dict.getdItemname());
                            break;
                        }
                    }
                }

                if (null != agentoutVo.getCloInvoice()) {
                    if (agentoutVo.getCloInvoice().compareTo(new BigDecimal(1)) == 0) {
                        agentoutVo.setYesOrNo("是");
                    } else if (agentoutVo.getCloInvoice().compareTo(new BigDecimal(0)) == 0)
                        agentoutVo.setYesOrNo("否");
                }

                if (null != agentoutVo.getCloType()) {
                    for (Dict dict : COLINFO_TYPE) {
                        if (null!=dict  &&  agentoutVo.getCloType().equals(dict.getdItemvalue())){
                            agentoutVo.setCloString(dict.getdItemname());
                            break;
                        }
                    }
                }

                if (null!=agentoutVo.getReportStatus()){
                    for (Dict dict : REPORT_STATUS) {
                        if (null!=dict  &&  agentoutVo.getReportStatus().toString().equals(dict.getdItemvalue())){
                            agentoutVo.setReportString(dict.getdItemname());
                            break;
                        }
                    }
                }

                if (StringUtils.isNotBlank(agentoutVo.getFreeStatus()) && !agentoutVo.getFreeStatus().equals("null")) {
                    String agStatusByValue = FreeStatus.getContentByValue(new BigDecimal(agentoutVo.getFreeStatus()));
                    if (null != agStatusByValue) {
                        agentoutVo.setFreeStatus(agStatusByValue);
                    }
                }

                if (null!=agentoutVo.getReportTime()){
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    agentoutVo.setTime(simpleDateFormat.format(agentoutVo.getReportTime()));
                }

                if (null != agentoutVo.getCloTaxPoint()) {
                    agentoutVo.setPoint(String.valueOf(agentoutVo.getCloTaxPoint()) + "%");
                }

                //业务区域
                if (StringUtils.isNotBlank(agentoutVo.getBusRegion()) && !agentoutVo.getBusRegion().equals("null")){
                    String busRegion = agentoutVo.getBusRegion();
                    if (StringUtils.isNotBlank(busRegion) &&!busRegion.equals("null")){
                        String[] arr = busRegion.split(",");
                        String name = agentQueryService.dPosRegionNameFromDposIds(arr);
                        if (StringUtils.isNotBlank(name) && !name.equals("null"))
                            agentoutVo.setBusRegion(name);
                    }
                }
                //银行地址
                if (StringUtils.isNotBlank(agentoutVo.getBankRegion()) && !agentoutVo.getBankRegion().equals("null")){
                    String bankRegion = agentoutVo.getBankRegion();
                    if (StringUtils.isNotBlank(bankRegion)){
                        String[] bank = bankRegion.split(",");
                        String bankName = agentQueryService.dRegionNameFromIds(bank);
                        if (StringUtils.isNotBlank(bankName))
                            agentoutVo.setBankRegion(bankName);
                    }
                }

            }
        return agentoutVos;
    }

    private void getBusParent(AgentoutVo agentoutVo) {
        List<AgentBusInfo> agentBusInfos = agentBusinfoService.queryParenFourLevel(new ArrayList<AgentBusInfo>(), agentoutVo.getBusPlatform(), agentoutVo.getAgentId());
        if (null != agentBusInfos && agentBusInfos.size() > 0) {
            if (agentBusInfos.size() == 1) {
                AgentBusInfo agentBusInfo = agentBusInfos.get(0);
                if (null != agentBusInfo) {
                    agentoutVo.setBusParentId(agentBusInfo.getAgentId());
                    Agent agentById = agentService.getAgentById(agentBusInfo.getAgentId());
                    if (null != agentById.getAgName())
                        agentoutVo.setBusParent(agentBusInfo.getAgentId() + agentById.getAgName());
                }
            } else if (agentBusInfos.size() == 2) {
                AgentBusInfo agentBusInfo = agentBusInfos.get(0);
                if (null != agentBusInfo) {
                    agentoutVo.setBusParentId(agentBusInfo.getAgentId());
                    Agent agentById = agentService.getAgentById(agentBusInfo.getAgentId());
                    if (null != agentById.getAgName())
                        agentoutVo.setBusParent(agentBusInfo.getAgentId() + agentById.getAgName());
                }
                AgentBusInfo twoParent = agentBusInfos.get(1);
                if (null != twoParent) {
                    agentoutVo.setBusParentId(twoParent.getAgentId());
                    Agent agentById = agentService.getAgentById(twoParent.getAgentId());
                    if (null != agentById.getAgName())
                        agentoutVo.setTwoParentId(twoParent.getAgentId() + agentById.getAgName());
                }

            } else if (agentBusInfos.size() == 3) {
                AgentBusInfo agentBusInfo = agentBusInfos.get(0);
                if (null != agentBusInfo) {
                    agentoutVo.setBusParentId(agentBusInfo.getAgentId());
                    Agent agentById = agentService.getAgentById(agentBusInfo.getAgentId());
                    if (null != agentById.getAgName())
                        agentoutVo.setBusParent(agentBusInfo.getAgentId() + agentById.getAgName());
                }
                AgentBusInfo twoParent = agentBusInfos.get(1);
                if (null != twoParent) {
                    agentoutVo.setBusParentId(twoParent.getAgentId());
                    Agent agentById = agentService.getAgentById(twoParent.getAgentId());
                    if (null != agentById.getAgName())
                        agentoutVo.setTwoParentId(twoParent.getAgentId() + agentById.getAgName());
                }
                AgentBusInfo threeParent = agentBusInfos.get(2);
                if (null != threeParent) {
                    agentoutVo.setBusParentId(threeParent.getAgentId());
                    Agent agentById = agentService.getAgentById(threeParent.getAgentId());
                    if (null != agentById.getAgName())
                        agentoutVo.setThreeParentId(threeParent.getAgentId() + agentById.getAgName());
                }
            }
        }
    }

}
