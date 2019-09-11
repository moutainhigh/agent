package com.ryx.credit.service.impl.agent;

import com.ryx.credit.common.enumc.*;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.redis.RedisService;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.AppConfig;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.dao.agent.*;
import com.ryx.credit.dao.order.OrganizationMapper;
import com.ryx.credit.pojo.admin.agent.*;
import com.ryx.credit.pojo.admin.order.Organization;
import com.ryx.credit.pojo.admin.vo.AgentBusInfoVo;
import com.ryx.credit.pojo.admin.vo.AgentVo;
import com.ryx.credit.pojo.admin.vo.CapitalVo;
import com.ryx.credit.service.ActivityService;
import com.ryx.credit.service.IUserService;
import com.ryx.credit.service.agent.*;
import com.ryx.credit.service.dict.DictOptionsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

/**
 * @version V1.0
 * @Description:
 * @author: Liudh
 * @date: 2018/5/30 16:51
 */
@Service("taskApprovalService")
public class TaskApprovalServiceImpl implements TaskApprovalService {

    private Logger logger = LoggerFactory.getLogger(TaskApprovalServiceImpl.class);

     @Autowired
     private AgentBusInfoMapper agentBusInfoMapper;
     @Autowired
     private AgentColinfoMapper agentColinfoMapper;
     @Autowired
     private AgentEnterService agentEnterService;
     @Autowired
     private AgentColinfoService agentColinfoService;
     @Autowired
     private ActivityService activityService;
     @Autowired
     private BusActRelMapper busActRelMapper;
     @Autowired
     private TaskApprovalService taskApprovalService;
     @Autowired
     private CapitalMapper capitalMapper;
    @Autowired
    private IUserService iUserService;
    @Autowired
    private PlatFormMapper platFormMapper;
    @Autowired
    private RedisService redisService;
    @Autowired
    private DictOptionsService dictOptionsService;
    @Autowired
    private AgentMapper agentMapper;
    @Autowired
    private OrganizationMapper organizationMapper;
    @Autowired
    private PlatFormService platFormService;
    @Autowired
    private AgentBusinfoService agentBusinfoService;


     @Override
     public List<Map<String,Object>> queryBusInfoAndRemit(AgentBusInfo agentBusInfo){

         if(StringUtils.isBlank(agentBusInfo.getAgentId())){
            return null;
         }
         return agentBusInfoMapper.queryBusInfoAndRemit(agentBusInfo.getAgentId());
     }


    @Override
    public List<Map<String, Object>> queryBusInfoAndRemitByBusId(String busId) {
        if(StringUtils.isBlank(busId)){
            return new ArrayList<>();
        }
        return agentBusInfoMapper.queryBusInfoAndRemitByBusId(busId);
    }

    /**
     * 处理任务
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW,isolation = Isolation.DEFAULT,rollbackFor = Exception.class)
    @Override
    public AgentResult approvalTask(AgentVo agentVo,String userId) throws Exception{

        try {
            taskApprovalService.updateApproval(agentVo, userId);
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
    public AgentResult updateApproval(AgentVo agentVo,String userId) throws Exception{

        if(agentVo.getApprovalResult().equals(ApprovalType.PASS.getValue())) {
            //判断打款方式--->银行汇款  是否填写了实际到账金额
            List<Map<String, Object>> orgCodeRes = iUserService.orgCode(Long.valueOf(userId));
            if(orgCodeRes==null && orgCodeRes.size()!=1){
                throw new ProcessException("部门参数为空");
            }
            Map<String, Object> stringObjectMap = orgCodeRes.get(0);
            String orgCode = String.valueOf(stringObjectMap.get("ORGANIZATIONCODE"));
            //市场审批
            if(orgCode.equals("market")){
                //业务平台除pro类型 都赋值空
                String ryx_pro = AppConfig.getProperty("ryx_pro");
                String ryx_pro1 = AppConfig.getProperty("ryx_pro1");

                //处理财务审批（财务出款机构）
                for (AgentBusInfoVo agentBusInfoVo : agentVo.getMarketToporgTableIdForm()) {
                    if(StringUtils.isNotBlank(agentBusInfoVo.getBusPlatform())){
                        if (!agentBusInfoVo.getBusPlatform().equals(ryx_pro) && !agentBusInfoVo.getBusPlatform().equals(ryx_pro1)){
                            agentBusInfoVo.setBusPlatform(" ");
                        }

                    }
                    //必须选择业务顶级机构
                    if(StringUtils.isBlank(agentBusInfoVo.getOrganNum())){
                        throw new ProcessException("请选择业务顶级机构");
                    }
                    //上级机构和本级机构判断
                    AgentBusInfo agentBusInfo = agentBusInfoMapper.selectByPrimaryKey(agentBusInfoVo.getId());
                    if (StringUtils.isNotBlank(agentBusInfoVo.getBusPlatform())){
                        agentBusInfo.setBusPlatform(agentBusInfoVo.getBusPlatform());
                    }
                    //上级机构判断
                    if(agentBusInfo!=null){
                        //上级存在
                        if (StringUtils.isNotBlank(agentBusInfoVo.getBusParent())){
                                //获取上级代理商类型
                                AgentBusInfo busInfo = agentBusinfoService.getById(agentBusInfoVo.getBusParent());
                                if (agentBusInfoVo.getBusType().equals(BusType.ZQ.key) || agentBusInfoVo.getBusType().equals(BusType.ZQBZF.key) || agentBusInfoVo.getBusType().equals(BusType.ZQZF.key)) {
                                    if (busInfo.getBusType().equals(BusType.ZQ.key) || busInfo.getBusType().equals(BusType.ZQZF.key) || busInfo.getBusType().equals(BusType.ZQBZF.key)) {
                                        throw new ProcessException("不能选择同级别的代理商为上级，请重新选择");
                                    }
                                }
                                if (agentBusInfoVo.getBusType().equals(BusType.YDX.key)) {
                                    if (busInfo.getBusType().equals(BusType.ZQ.key) || busInfo.getBusType().equals(BusType.YDX.key)
                                            || busInfo.getBusType().equals(BusType.ZQZF.key) || busInfo.getBusType().equals(BusType.ZQBZF.key)) {
                                        throw new ProcessException("不能选择同级别的代理商为上级，请重新选择");
                                    }
                                }
                                if (agentBusInfoVo.getBusType().equals(BusType.JGYD.key)) {
                                    if (!busInfo.getBusType().equals(BusType.JG.key)) {
                                        throw new ProcessException("不能选择同级别的代理商为上级，请重新选择");
                                    }
                                }

                            //上级不为空  说明选择了上级---校验业务平台
                            AgentBusInfo parent = agentBusInfoMapper.selectByPrimaryKey(agentBusInfoVo.getBusParent());
                            PlatForm platForm = platFormService.selectByPlatformNum(parent.getBusPlatform());
                            if (null!=parent){
                                if (StringUtils.isNotBlank(agentBusInfoVo.getBusPlatform())){
                                    if (!agentBusInfoVo.getBusPlatform().equals(parent.getBusPlatform())){
                                        throw new ProcessException("审批失败:业务平台类型和上级代理商业务平台类型不同，上级代理商业务平台类型为:"+platForm.getPlatformName());
                                    }
                                }else{
                                    //业务平台为空  说明不是pro类型的(前端没有传值进来) 需查询业务平台类型
                                    if (!agentBusInfo.getBusPlatform().equals(parent.getBusPlatform())){
                                        throw new ProcessException("审批失败:业务平台类型和上级代理商业务平台类型不同，上级代理商业务平台类型为:"+platForm.getPlatformName());
                                    }
                                }
                                //校验顶级机构
                                if(StringUtils.isNotBlank(parent.getOrganNum())){
                                    //上级机构不为空判断与本级是否一致
                                    if(!parent.getOrganNum().equals(agentBusInfoVo.getOrganNum())){
                                        //提示上级机构是什么
                                        Organization organization = organizationMapper.selectByPrimaryKey(parent.getOrganNum());
                                        if(organization==null){
                                            throw new ProcessException("审批失败:顶级机构和上级的顶级机构不同，上级顶级机构未找到");
                                        }else{
                                            throw new ProcessException("审批失败:顶级机构和上级的顶级机构不同，上级顶级机构为:"+organization.getOrgName());
                                        }
                                    }
                                }else{
                                    throw new ProcessException("审批失败:上级的顶级机构为空，请联系业务进行补全");
                                }

                            }



                            agentBusInfo.setBusParent(agentBusInfoVo.getBusParent());
                        }
                        if(StringUtils.isNotBlank(agentBusInfo.getBusParent())){
                            AgentBusInfo parent = agentBusInfoMapper.selectByPrimaryKey(agentBusInfo.getBusParent());
                            //上级必须有机构，如果没有机构需要提示补全
                            if(parent!=null){
                                //上级机构为空，提示必须填写，上级机构不为空判断与本级是否一致
                                if(StringUtils.isNotBlank(parent.getOrganNum())){
                                    //上级机构不为空判断与本级是否一致
                                    if(!parent.getOrganNum().equals(agentBusInfoVo.getOrganNum())){
                                        //提示上级机构是什么
                                        Organization organization = organizationMapper.selectByPrimaryKey(parent.getOrganNum());
                                        if(organization==null){
                                            throw new ProcessException("审批失败:顶级机构和上级的顶级机构不同，上级顶级机构未找到");
                                        }else{
                                            throw new ProcessException("审批失败:顶级机构和上级的顶级机构不同，上级顶级机构为:"+organization.getOrgName());
                                        }
                                    }
                                }else{
                                    throw new ProcessException("审批失败:上级的顶级机构为空，请联系业务进行补全");
                                }
                            }
                        }
                        agentBusInfo.setOrganNum(agentBusInfoVo.getOrganNum());

                        if(agentBusInfoMapper.updateByPrimaryKeySelective(agentBusInfo)!=1){
                            throw new ProcessException("审批失败:业务顶级机构更新异常");
                        }
                        //修改业务审批关系表
                        if (StringUtils.isNotBlank(agentVo.getSid())){
                            BusActRel byActivId = busActRelMapper.findByActivId(agentVo.getSid());
                            byActivId.setNetInBusType("ACTIVITY_"+agentBusInfoVo.getBusPlatform());
                            if(busActRelMapper.updateByPrimaryKeySelective(byActivId)!=1){
                                throw new ProcessException("业务审批关系表更新异常");
                            }
                        }
                    }
                }
            }
            //财务审批
            if(orgCode.equals("finance")){
                for (CapitalVo capitalVo : agentVo.getCapitalVoList()) {
                    if (capitalVo.getcPayType().equals(PayType.YHHK.code)) {
                        if (null == capitalVo.getcInAmount()) {
                            logger.info("请填写实际到账金额");
                            throw new ProcessException("请填写实际到账金额");
                        }
                        //银行汇款 可用余额 财务填写提交金额
                        capitalVo.setcFqInAmount(capitalVo.getcInAmount());
                    }else if (capitalVo.getcPayType().equals(PayType.FRDK.code)) {
                        capitalVo.setcInAmount(capitalVo.getcAmount());
                        //分润抵扣可用余额为0
                        capitalVo.setcFqInAmount(BigDecimal.ZERO);
                    }else{
                        //其他不可知状态 可用余额
                        capitalVo.setcFqInAmount(capitalVo.getcInAmount());
                    }
                    //分润分期 银行汇款
                    Capital capital = capitalMapper.selectByPrimaryKey(capitalVo.getId());
                    capitalVo.setcUtime(new Date());
                    capitalVo.setVersion(capital.getVersion());
                    //财务填写
                    // capitalVo.setcInAmount(capitalVo.getcInAmount());
                    capitalVo.setId(capitalVo.getId());
                    int i = capitalMapper.updateByPrimaryKeySelective(capitalVo);
                    if (i != 1) {
                        logger.info("实际到账金额填写失败");
                        throw new ProcessException("实际到账金额填写失败");
                    }
                }

                //处理财务审批（财务出款机构）
                for (AgentBusInfoVo agentBusInfoVo : agentVo.getOrgTypeList()) {
                    if (StringUtils.isBlank(agentBusInfoVo.getFinaceRemitOrgan())) {
                        throw new ProcessException("请选择财务出款机构");
                    }
                    AgentBusInfo agentBusInfo = agentBusInfoMapper.selectByPrimaryKey(agentBusInfoVo.getId());
                    agentBusInfo.setFinaceRemitOrgan(agentBusInfoVo.getFinaceRemitOrgan());
                    int i = agentBusInfoMapper.updateByPrimaryKeySelective(agentBusInfo);
                    if (i != 1) {
                        throw new ProcessException("更新财务出款机构异常");
                    }
                }
            }
            //处理财务修改
            for (AgentColinfoRel agentColinfoRel : agentVo.getAgentColinfoRelList()) {
                AgentResult result = agentColinfoService.saveAgentColinfoRel(agentColinfoRel, userId);
                if(!result.isOK()){
                    throw new ProcessException("保存收款关系异常");
                }
            }
            Set<String> dkgs = new HashSet<String>();
            for (AgentBusInfoVo agentBusInfoVo : agentVo.getBusInfoVoList()) {
                AgentBusInfo agentBusInfo = agentBusInfoMapper.selectByPrimaryKey(agentBusInfoVo.getId());
                //上级不为空判断是否与上级打款公司一致 fixme 一个代理商下的业务平台的打款公司必须是一样
//                if(StringUtils.isNotBlank(agentBusInfo.getBusParent())){
//                    AgentBusInfo parentBusInfo = agentBusInfoMapper.selectByPrimaryKey(agentBusInfo.getBusParent());
//                    if(!agentBusInfoVo.getCloPayCompany().equals(parentBusInfo.getCloPayCompany())){
//                        throw new ProcessException(Platform.getContentByValue(agentBusInfo.getBusPlatform())+"上级打款公司不一致");
//                    }
//                }
                if(StringUtils.isNotBlank(agentBusInfo.getCloPayCompany())) {
                    dkgs.add(agentBusInfo.getCloPayCompany());
                }
                if(dkgs.size()>1){
                    throw new ProcessException("代理商的业务平台的打款公司必须是一样");
                }
                agentBusInfoVo.setId(agentBusInfoVo.getId());
                agentBusInfoVo.setVersion(agentBusInfo.getVersion());
                agentBusInfoVo.setcUtime(new Date());
                int i = agentBusInfoMapper.updateByPrimaryKeySelective(agentBusInfoVo);
                if(i!=1){
                    throw new ProcessException("更新打款公司或业务所属上级异常");
                }
            }


            //业务部门审批
            if(orgCode.equals("business")) {
                //业务部输入借记费率
                for (AgentBusInfoVo agentBusInfoVo : agentVo.getEditDebitList()) {
                    AgentBusInfo agentBusInfo = agentBusInfoMapper.selectByPrimaryKey(agentBusInfoVo.getId());
                    agentBusInfoVo.setId(agentBusInfoVo.getId());
                    agentBusInfoVo.setVersion(agentBusInfo.getVersion());
                    agentBusInfoVo.setcUtime(new Date());
                    int i = agentBusInfoMapper.updateByPrimaryKeySelective(agentBusInfoVo);
                    if (i != 1) {
                        throw new ProcessException("更新借记费率等信息失败");
                    }
                }
                //业务部输入瑞大宝终端数量下线
                if (agentVo.getTerminalsLowerList() != null && agentVo.getTerminalsLowerList().size() > 0) {
                    for (AgentBusInfoVo agentBusInfoVo : agentVo.getTerminalsLowerList()) {
                        AgentBusInfo agentBusInfo = agentBusInfoMapper.selectByPrimaryKey(agentBusInfoVo.getId());
                        PlatForm platForm = platFormMapper.selectByPlatFormNum(agentBusInfo.getBusPlatform());
                        if (PlatformType.RDBPOS.code.equals(platForm.getPlatformType())) {
                            if (StringUtils.isBlank(agentBusInfoVo.getTerminalsLower())) {
                                throw new ProcessException("请填写终端数量下限");
                            }
                            if (new BigDecimal(agentBusInfoVo.getTerminalsLower()).compareTo(new BigDecimal(5000)) > 0) {
                                throw new ProcessException("终端数量下限最高为5000");
                            }
                            agentBusInfo.setcUtime(new Date());
                            agentBusInfo.setTerminalsLower(agentBusInfoVo.getTerminalsLower());
                            int i = agentBusInfoMapper.updateByPrimaryKeySelective(agentBusInfo);
                            if (i != 1) {
                                throw new ProcessException("更新终端数量下限失败");
                            }
                        }
                    }
                }
            }
        }
        return AgentResult.ok();
    }
    /**
     * 查询工作流程
     * @param busId
     * @param busType
     * @return
     */
    @Override
    public Map findBusActByBusId(String busId,String busType,String activStatus){
        BusActRel busActRel = queryBusActRel(busId, busType,activStatus);
        if(busActRel==null){
            return null;
        }
        Map resultMap = activityService.getImageByExecuId(busActRel.getActivId());
        return resultMap;
    }

    @Override
    public BusActRel queryBusActRel(String busId,String busType,String activStatus){
        BusActRelExample example = new BusActRelExample();
        BusActRelExample.Criteria criteria = example.createCriteria();
        criteria.andBusIdEqualTo(busId);
        criteria.andBusTypeEqualTo(busType);
        criteria.andStatusEqualTo(Status.STATUS_1.status);
        if (StringUtils.isNotBlank(activStatus))
        criteria.andActivStatusEqualTo(activStatus);
        List<BusActRel> busActRels = busActRelMapper.selectByExample(example);
        if(busActRels.size()!=1){
            return null;
        }
        BusActRel busActRel = busActRels.get(0);
        return busActRel;
    }

    @Override
    public List<Map<String, Object>> queryById(AgentBusInfo agentBusInfo) {
        if(StringUtils.isBlank(agentBusInfo.getAgentId())){
            return null;
        }
        return agentBusInfoMapper.queryById(agentBusInfo.getAgentId());
    }


    @Override
    public int addABusActRel(BusActRel busActRel)throws Exception {
        if(StringUtils.isBlank(busActRel.getBusId()))throw new ProcessException("BusId不能为空");
        if(StringUtils.isBlank(busActRel.getActivId()))throw new ProcessException("ActivId不能为空");
        if(StringUtils.isBlank(busActRel.getcUser()))throw new ProcessException("cUser不能为空");
        if(StringUtils.isBlank(busActRel.getBusType()))throw new ProcessException("BusType不能为空");
        //代理商业务视频关系
        BusActRel record = new BusActRel();
        record.setBusId(busActRel.getBusId());
        record.setActivId(busActRel.getActivId());
        record.setcTime(Calendar.getInstance().getTime());
        record.setcUser(busActRel.getcUser());
        record.setStatus(Status.STATUS_1.status);
        record.setBusType(busActRel.getBusType());
        record.setAgentId(busActRel.getAgentId());
        record.setAgentName(busActRel.getAgentName());
        record.setActivStatus(AgStatus.Approving.name());
        record.setDataShiro(busActRel.getDataShiro());
        if (1 != busActRelMapper.insertSelective(record)) {
            logger.info("启动审批异常，添加审批关系失败{}:{}:{}", busActRel.getActivId(), busActRel.getBusId(),busActRel.getBusType());
        }
        return 1;
    }

    @Override
    public int updateABusActRel(BusActRel busActRel) {
        if(StringUtils.isBlank(busActRel.getBusId()))throw new ProcessException("BusId不能为空");
        if(StringUtils.isBlank(busActRel.getActivId()))throw new ProcessException("ActivId不能为空");
        if(StringUtils.isBlank(busActRel.getcUser()))throw new ProcessException("cUser不能为空");
        if(StringUtils.isBlank(busActRel.getBusType()))throw new ProcessException("BusType不能为空");
        BusActRelKey key = new BusActRelKey();
        key.setActivId(busActRel.getActivId());
        key.setBusId(busActRel.getBusId());
        BusActRel rel = busActRelMapper.selectByPrimaryKey(key);
        if(rel==null){
           return 0;
        }
        rel.setActivStatus(busActRel.getActivStatus());
        if(1==busActRelMapper.updateByPrimaryKeySelective(rel)){
            return 1;
        }
        return 0;
    }

    @Override
    public BusActRel queryBusActRel(BusActRel busActRel) {
        BusActRelExample example = new BusActRelExample();
        BusActRelExample.Criteria c = example.or().andStatusEqualTo(Status.STATUS_1.status);
        if(StringUtils.isNotBlank(busActRel.getBusId())) {
            c.andBusIdEqualTo(busActRel.getBusId());
        }
        if(StringUtils.isNotBlank(busActRel.getActivId())){
            c.andActivIdEqualTo(busActRel.getActivId());
        }
        if(StringUtils.isNotBlank(busActRel.getcUser())){
            c.andCUserEqualTo(busActRel.getcUser());
        }
        if(StringUtils.isNotBlank(busActRel.getActivStatus())){
            c.andActivStatusEqualTo(busActRel.getActivStatus());
        }
        if(StringUtils.isNotBlank(busActRel.getBusType())){
            c.andBusTypeEqualTo(busActRel.getBusType());
        }
        List<BusActRel>  list = busActRelMapper.selectByExample(example);
        return list.size()==1?list.get(0):null;
    }


    @Override
    public void updateShrioBusActRel() {
        BusActRelExample busActRelExample = new BusActRelExample();
        List<BusActRel> busActRels = busActRelMapper.selectByExample(busActRelExample);
        for (BusActRel busActRel : busActRels) {
            busActRel.setDataShiro(BusActRelBusType.getNameByKey(busActRel.getBusType()));
            busActRelMapper.updateByPrimaryKeySelective(busActRel);
        }
    }

    /**
     * 更新流程id修复程序
     */
    @Override
    public void updateActivId(){
        String activIds = redisService.hGet("actRepairConfig", "activIds");
        if(StringUtils.isBlank(activIds)){
            return;
        }
        String approveName = redisService.hGet("actRepairConfig", "approveName");
        if(StringUtils.isBlank(approveName)){
            return;
        }
        String[] activIdArr = activIds.split(",");
        for (String activId : activIdArr) {
            BusActRel busActRel = busActRelMapper.findById(activId);
            String workId = dictOptionsService.getApproveVersion(approveName);
            Map startPar = agentEnterService.startPar(busActRel.getcUser());
            if(null==startPar)continue;
            startPar.put("rs","pass");
            String proce = activityService.createDeloyFlow(null,workId,null,null,startPar);
            busActRelMapper.updateActivIdByActivId(busActRel.getActivId(),proce);
        }
    }
}
