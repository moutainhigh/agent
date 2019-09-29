package com.ryx.credit.service.impl.order;

import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.enumc.*;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.redis.RedisService;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.DateUtil;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.dao.COrganizationMapper;
import com.ryx.credit.dao.agent.*;
import com.ryx.credit.dao.order.*;
import com.ryx.credit.machine.service.ImsTermWarehouseDetailService;
import com.ryx.credit.machine.service.TermMachineService;
import com.ryx.credit.machine.vo.ChangeActMachineVo;
import com.ryx.credit.pojo.admin.COrganization;
import com.ryx.credit.pojo.admin.agent.*;
import com.ryx.credit.pojo.admin.order.*;
import com.ryx.credit.pojo.admin.vo.AgentVo;
import com.ryx.credit.pojo.admin.vo.OCashReceivablesVo;
import com.ryx.credit.pojo.admin.vo.ORefundPriceDiffVo;
import com.ryx.credit.service.ActivityService;
import com.ryx.credit.service.IUserService;
import com.ryx.credit.service.agent.AgentEnterService;
import com.ryx.credit.service.agent.AgentService;
import com.ryx.credit.service.agent.PlatFormService;
import com.ryx.credit.service.dict.DictOptionsService;
import com.ryx.credit.service.dict.IdService;
import com.ryx.credit.service.order.CompensateService;
import com.ryx.credit.service.order.OCashReceivablesService;
import com.ryx.credit.service.order.OrderActivityService;
import com.ryx.credit.service.order.ProductService;
import com.ryx.internet.pojo.OInternetCardImport;
import net.sf.jxls.transformer.Row;
import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 补差价处理
 * Created by liudh on 2018/7/24.
 */
@Service("compensateService")
public class CompensateServiceImpl implements CompensateService {

    private static Logger log = LoggerFactory.getLogger(CompensateServiceImpl.class);
    @Autowired
    private OActivityMapper activityMapper;
    @Autowired
    private IdService idService;
    @Autowired
    private ORefundPriceDiffMapper refundPriceDiffMapper;
    @Autowired
    private ORefundPriceDiffDetailMapper refundPriceDiffDetailMapper;
    @Autowired
    private AttachmentRelMapper attachmentRelMapper;
    @Autowired
    private AgentEnterService agentEnterService;
    @Autowired
    private DictOptionsService dictOptionsService;
    @Autowired
    private ActivityService activityService;
    @Autowired
    private BusActRelMapper busActRelMapper;
    @Autowired
    private ODeductCapitalMapper deductCapitalMapper;
    @Autowired
    private AttachmentMapper attachmentMapper;
    @Autowired
    private CompensateService compensateService;
    @Autowired
    private OLogisticsDetailMapper logisticsDetailMapper;
    @Autowired
    private OrderActivityService orderActivityService;
    @Autowired
    private OLogisticsMapper logisticsMapper;
    @Autowired
    private IUserService userService;
    @Autowired
    private AgentService agentService;
    @Autowired
    private TermMachineService termMachineService;
    @Autowired
    private AgentBusInfoMapper agentBusInfoMapper;
    @Autowired
    private OOrderMapper oOrderMapper;
    @Autowired
    private PlatFormService platFormService;
    @Autowired
    private ImsTermWarehouseDetailService imsTermWarehouseDetailService;
    @Autowired
    private OSubOrderMapper subOrderMapper;
    @Autowired
    private OSubOrderActivityMapper subOrderActivityMapper;
    @Autowired
    private IUserService iUserService;
    @Autowired
    private AgentMapper agentMapper;
    @Autowired
    private OCashReceivablesService cashReceivablesService;
    @Autowired
    private RedisService redisService;
    @Autowired
    private ProductService productService;
    @Autowired
    private COrganizationMapper organizationMapper;
    @Autowired
    private PlatFormMapper platFormMapper;


    @Override
    public ORefundPriceDiff selectByPrimaryKey(String id){
        if(StringUtils.isBlank(id)){
            return null;
        }
        ORefundPriceDiff oRefundPriceDiff = refundPriceDiffMapper.selectByPrimaryKey(id);
        return oRefundPriceDiff;
    }


    @Override
    public PageInfo compensateList(ORefundPriceDiffVo refundPriceDiff, Page page, String dataRole,long userId){

        Map<String, Object> reqMap = new HashMap<>();
        if(null!=refundPriceDiff.getReviewStatus()){
            reqMap.put("reviewStatus",refundPriceDiff.getReviewStatus());
        }
        if(StringUtils.isNotBlank(refundPriceDiff.getApplyBeginTime())){
            reqMap.put("applyBeginTime",refundPriceDiff.getApplyBeginTime());
        }
        if(StringUtils.isNotBlank(refundPriceDiff.getApplyEndTime())){
            reqMap.put("applyEndTime",refundPriceDiff.getApplyEndTime());
        }
        if(StringUtils.isNotBlank(refundPriceDiff.getAgentId())){
            reqMap.put("agentId",refundPriceDiff.getAgentId());
        }
        if(StringUtils.isNotBlank(refundPriceDiff.getAgentName())){
            reqMap.put("agentName",refundPriceDiff.getAgentName());
        }
        if(StringUtils.isBlank(dataRole)){
            reqMap.put("cUser",userId);
        }else{
            if(dataRole.equals("below")){
                List<Map<String, Object>> orgCodeRes = iUserService.orgCode(userId);
                if(orgCodeRes==null && orgCodeRes.size()!=1){
                    return null;
                }
                Map<String, Object> stringObjectMap = orgCodeRes.get(0);
                reqMap.put("orgId",String.valueOf(stringObjectMap.get("ORGID")));
                reqMap.put("cUser",userId);
            }
        }
        List<Map<String,Object>> refundPriceDiffs = refundPriceDiffMapper.selectByAgent(reqMap,page);
        PageInfo pageInfo = new PageInfo();
        pageInfo.setRows(refundPriceDiffs);
        pageInfo.setTotal(refundPriceDiffMapper.selectByAgentCount(reqMap));
        return pageInfo;
    }

    @Override
    public List<Map<String,Object>> getOrderMsgByExcel(List<Object> excelList,Long userId,String agentId)throws ProcessException{
        String snBegin = "";
        String snEnd = "";
        String count = "";
        String platformType = "";
        try {
            snBegin =  String.valueOf(excelList.get(0)).trim();
            snEnd =  String.valueOf(excelList.get(1)).trim();
            count =  String.valueOf(excelList.get(2)).trim();
            platformType =  String.valueOf(excelList.get(3)).trim();
        } catch (Exception e) {
            throw new ProcessException("导入解析文件失败");
        }
        String platformTypeCode = PlatformType.getValueByContent(platformType);
        if(StringUtils.isBlank(platformTypeCode)){
            throw new ProcessException("平台信息填写有误");
        }
        Map<String, Object> reqParam = new HashMap<>();
        reqParam.put("snBegin",snBegin);
        reqParam.put("snEnd",snEnd);
        reqParam.put("status",OLogisticsDetailStatus.STATUS_FH.code);
        ArrayList<Object> recordStatusList = new ArrayList<>();
        recordStatusList.add(OLogisticsDetailStatus.RECORD_STATUS_VAL.code);
        reqParam.put("recordStatusList",recordStatusList);
        List<Map<String,Object>> compensateLList = logisticsDetailMapper.queryCompensateLList(reqParam);
        if(null==compensateLList){
            throw new ProcessException("导入解析文件失败");
        }
        if(compensateLList.size()==0){
            throw new ProcessException("sn号在审批中或已退货");
        }
        BigDecimal proNumSum = new BigDecimal(0);
        Set<String> platformTypeSet = new HashSet<>();
        for (Map<String, Object> stringObjectMap : compensateLList) {
            proNumSum = proNumSum.add(new BigDecimal(stringObjectMap.get("PRO_NUM").toString()));
            if(String.valueOf(stringObjectMap.get("MIN_SN")).equals(snBegin)){
                stringObjectMap.put("PLATFORM_TYPE",platformTypeCode);
            }
            platformTypeSet.add(platformTypeCode);
        }
        if(platformTypeSet.size()==0){
            throw new ProcessException("请选择平台类型");
        }
        if(platformTypeSet.size()!=1){
            throw new ProcessException("不同平台类型请分开提交");
        }
        if(!String.valueOf(proNumSum).equals(count)){
            throw new ProcessException("sn号数量不匹配");
        }
        //判断是否是自己省区下的订单
        List<Map<String, Object>>  org = userService.orgCode(userId);
        if(org.size()==0){throw new ProcessException("部门信息未找到");}
        String orgId = String.valueOf(org.get(0).get("ORGID"));
        String orgCode = String.valueOf(org.get(0).get("ORGANIZATIONCODE"));
        if(StringUtils.isBlank(orgId)){
            throw new ProcessException("省区部门参数为空");
        }
        String logisticsId = String.valueOf(compensateLList.get(0).get("LOGISTICS_ID"));
        OLogistics oLogistics = logisticsMapper.selectByPrimaryKey(logisticsId);
        for (Map<String, Object> stringObjectMap : compensateLList) {
            Agent agent = agentService.getAgentById(String.valueOf(stringObjectMap.get("AGENT_ID")));
            if(null==agent){
                log.info("代理商信息不存在");
                throw new ProcessException("代理商信息不存在");
            }
            if(!agent.getId().equals(agentId)){
                COrganization cOrganization = organizationMapper.selectByPrimaryKey(Integer.valueOf(agent.getAgDocPro()));
                if(!Pattern.matches(orgCode+".*",cOrganization.getCode())){
                    log.info("不能提交其他省区的退补差价");
                    throw new ProcessException("不能提交其他省区的退补差价");
                }
            }
            AgentBusInfoExample agentBusInfoExample = new AgentBusInfoExample();
            AgentBusInfoExample.Criteria criteria = agentBusInfoExample.createCriteria();
            criteria.andStatusEqualTo(Status.STATUS_1.status);
            criteria.andBusStatusNotEqualTo(BusinessStatus.pause.status);
            criteria.andIdEqualTo(String.valueOf(stringObjectMap.get("BUS_ID")));
            List<AgentBusInfo> agentBusInfos = agentBusInfoMapper.selectByExample(agentBusInfoExample);
            if(agentBusInfos.size()==0){
                throw new ProcessException("SN不在平台下");
            }

            String gTime = String.valueOf(stringObjectMap.get("G_TIME"));
            if(StringUtils.isNotBlank(gTime) && gTime!="null"){
                BigDecimal gTimeB = new BigDecimal(gTime);
                gTimeB = gTimeB.multiply(new BigDecimal(24)).multiply(new BigDecimal(60)).multiply(new BigDecimal(60)).multiply(new BigDecimal(1000));
                long activityCtime = oLogistics.getcTime().getTime();
                long nowTime = new Date().getTime();
                if((new BigDecimal(nowTime-activityCtime)).compareTo(gTimeB)==1){
                    log.info("商品活动超出保价时间");
                    throw new ProcessException("商品活动超出保价时间");
                }
            }
        }
        return compensateLList;
    }


    /**
     * 计算变更差价
     * @param oldActivityId
     * @param activityId
     * @param proNum
     * @return
     */
    @Override
    public BigDecimal calculatePriceDiff(String beginSn,String endSn,String oldActivityId,String activityId,BigDecimal proNum, ORefundPriceDiff oRefundPriceDiff){
        BigDecimal resultPrice = new BigDecimal(0);

        Map<String, Object> reqParam = new HashMap<>();
        reqParam.put("snBegin",beginSn);
        reqParam.put("snEnd",endSn);
        reqParam.put("status",OLogisticsDetailStatus.STATUS_FH.code);
        ArrayList<Object> recordStatusList = new ArrayList<>();
        if(null!=oRefundPriceDiff && null!=oRefundPriceDiff.getId()){
            oRefundPriceDiff = refundPriceDiffMapper.selectByPrimaryKey(oRefundPriceDiff.getId());
            if(oRefundPriceDiff.getReviewStatus().compareTo(AgStatus.Approving.getValue())==0){
                recordStatusList.add(OLogisticsDetailStatus.RECORD_STATUS_LOC.code);
            }else{
                recordStatusList.add(OLogisticsDetailStatus.RECORD_STATUS_VAL.code);
            }
        }else{
            recordStatusList.add(OLogisticsDetailStatus.RECORD_STATUS_VAL.code);
        }
        reqParam.put("recordStatusList",recordStatusList);
        List<Map<String, Object>> oLogisticsDetails = logisticsDetailMapper.queryCompensateLList(reqParam);
        if(null==oLogisticsDetails){
            log.info("calculatePriceDiff数据有误异常返回1");
            return null;
        }
        if(oLogisticsDetails.size()!=1){
            log.info("calculatePriceDiff数据有误异常返回2");
            return null;
        }
        Map<String, Object> logisticsDetail = oLogisticsDetails.get(0);
        BigDecimal oldPrice = new BigDecimal(logisticsDetail.get("SETTLEMENT_PRICE").toString()).multiply(proNum);
        BigDecimal newPrice = calculateTotalPrice(activityId, proNum);
        resultPrice = oldPrice.subtract(newPrice);
        return resultPrice;
    }

    /**
     * 计算总金额
     * @param activityId  活动id
     * @param count
     * @return
     */
    @Override
    public BigDecimal calculateTotalPrice(String activityId,BigDecimal count){

        if(StringUtils.isBlank(activityId)){
            return null;
        }
        OActivity oActivity = activityMapper.selectByPrimaryKey(activityId);
        //判断是否满足  暂未实现

        BigDecimal sumPrice = count.multiply(oActivity.getPrice());
        return sumPrice;
    }

    /**
     * save
     * @param oRefundPriceDiff
     * @param refundPriceDiffDetailList
     * @param cUser
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW,isolation = Isolation.DEFAULT,rollbackFor = Exception.class)
    @Override
    public AgentResult compensateAmtSave(ORefundPriceDiff oRefundPriceDiff, List<ORefundPriceDiffDetail> refundPriceDiffDetailList,
                                         List<String> refundPriceDiffFile, String cUser,List<OCashReceivablesVo> oCashReceivablesVoList,AgentVo agentVo)throws ProcessException{

        try {
            if(PriceDiffType.DETAIN_AMT.code.equals(oRefundPriceDiff.getApplyCompType())){
                if(refundPriceDiffFile.size()==0){
                    return AgentResult.fail("代理商打款必须上传打款凭证");
                }
                if(oCashReceivablesVoList==null || oCashReceivablesVoList.size()==0){
                    return AgentResult.fail("代理商打款必须填写打款记录");
                }
            }
            String priceDiffId = idService.genId(TabId.o_Refund_price_diff);
            oRefundPriceDiff.setId(priceDiffId);
            Date nowDate = new Date();
            oRefundPriceDiff.setAgentId(refundPriceDiffDetailList.get(0).getAgentId());
            oRefundPriceDiff.setRelCompType(oRefundPriceDiff.getApplyCompType());
            oRefundPriceDiff.setRelCompAmt(oRefundPriceDiff.getApplyCompAmt());
            oRefundPriceDiff.setMachOweAmt(new BigDecimal(0));
            oRefundPriceDiff.setDeductAmt(new BigDecimal(0));
            oRefundPriceDiff.setGatherAmt(new BigDecimal(0));
            oRefundPriceDiff.setcTime(nowDate);
            oRefundPriceDiff.setuTime(nowDate);
            oRefundPriceDiff.setsTime(nowDate);
            oRefundPriceDiff.setcUser(cUser);
            oRefundPriceDiff.setuUser(cUser);
            oRefundPriceDiff.setReviewStatus(AgStatus.Create.status);
            oRefundPriceDiff.setStatus(Status.STATUS_1.status);
            oRefundPriceDiff.setVersion(Status.STATUS_0.status);
            oRefundPriceDiff.setOrderType(OrderType.NEW.getValue());
            BigDecimal belowPayAmt = new BigDecimal(0);
            BigDecimal shareDeductAmt = new BigDecimal(0);
            for (OCashReceivablesVo oCashReceivablesVo : oCashReceivablesVoList) {
                if(oCashReceivablesVo.getPayType().equals(PayType.YHHK.code)){
                    belowPayAmt = belowPayAmt.add(oCashReceivablesVo.getAmount());
                }else if(oCashReceivablesVo.getPayType().equals(PayType.FRDK.code)){
                    shareDeductAmt = shareDeductAmt.add(oCashReceivablesVo.getAmount());
                }
            }
            oRefundPriceDiff.setBelowPayAmt(belowPayAmt);
            oRefundPriceDiff.setShareDeductAmt(shareDeductAmt);
            int refundDiffInsert = refundPriceDiffMapper.insert(oRefundPriceDiff);
            if(refundDiffInsert!=1){
                log.info("插入补退差价申请表异常");
                throw new ProcessException("保存失败");
            }

            //添加新的附件
            if (refundPriceDiffFile != null) {
                refundPriceDiffFile.forEach(fileId->{
                    AttachmentRel record = new AttachmentRel();
                    record.setAttId(fileId);
                    record.setSrcId(priceDiffId);
                    record.setcUser(cUser);
                    record.setcTime(Calendar.getInstance().getTime());
                    record.setStatus(Status.STATUS_1.status);
                    record.setBusType(AttachmentRelType.ActivityEdit.name());
                    record.setId(idService.genId(TabId.a_attachment_rel));
                    int i = attachmentRelMapper.insertSelective(record);
                    if (1 != i) {
                        log.info("活动变更附件关系失败");
                        throw new ProcessException("保存失败");
                    }
                });
            }
            //打款记录
            AgentResult agentResult = cashReceivablesService.addOCashReceivables(oCashReceivablesVoList,cUser,oRefundPriceDiff.getAgentId(),CashPayType.REFUNDPRICEDIFF,oRefundPriceDiff.getId());
            if(!agentResult.isOK()){
                log.info("退补差价保存打款记录失败1");
                throw new ProcessException("保存打款记录失败");
            }

            refundPriceDiffDetailList.forEach(refundPriceDiffDetail->{

                Map<String, Object> logisticsDetail = null;
                if(StringUtils.isNotBlank(refundPriceDiffDetail.getActivityFrontId()) && !refundPriceDiffDetail.getActivityFrontId().equals("undefined")){
                    Map<String, Object> reqParam = new HashMap<>();
                    reqParam.put("snBegin",refundPriceDiffDetail.getBeginSn());
                    reqParam.put("snEnd",refundPriceDiffDetail.getEndSn());
                    reqParam.put("status",OLogisticsDetailStatus.STATUS_FH.code);
                    ArrayList<Object> recordStatusList = new ArrayList<>();
                    recordStatusList.add(OLogisticsDetailStatus.RECORD_STATUS_VAL.code);
                    reqParam.put("recordStatusList",recordStatusList);
                    reqParam.put("activityId",refundPriceDiffDetail.getActivityFrontId());
                    List<Map<String, Object>> oLogisticsDetails = logisticsDetailMapper.queryCompensateLList(reqParam);
                    if(null==oLogisticsDetails){
                        log.info("calculatePriceDiff数据有误异常返回1");
                        throw new ProcessException("保存失败");
                    }
                    if(oLogisticsDetails.size()!=1){
                        log.info("calculatePriceDiff数据有误异常返回2");
                        throw new ProcessException("保存失败");
                    }
                    logisticsDetail = oLogisticsDetails.get(0);

                    OActivity oldActivity = activityMapper.selectByPrimaryKey(refundPriceDiffDetail.getActivityFrontId());
                    if(oldActivity==null){
                        throw new ProcessException("旧活动不存在");
                    }
                    OActivity newActivity = activityMapper.selectByPrimaryKey(refundPriceDiffDetail.getActivityRealId());
                    if(newActivity==null){
                        throw new ProcessException("新活动不存在");
                    }
                    //检查目标活动和代理商平台码是否一致
                    if(oldActivity.getPlatform().equals(newActivity.getPlatform())){
                        //活动没有跨平台，平台号也不允许跨平台
                        if(!refundPriceDiffDetail.getNewOrgId().equals(refundPriceDiffDetail.getOldOrgId())) {
                            throw new ProcessException("原平台编号与目标平台编号不一致");
                        }
                    }
                    //检查目标活动和代理商平台码是否一致
                    if(refundPriceDiffDetail.getNewOrgId().equals(refundPriceDiffDetail.getOldOrgId())){
                        //平台号没有跨平台，活动也不允许跨平台
                        if(!oldActivity.getPlatform().equals(newActivity.getPlatform())) {
                            throw new ProcessException("目标平台编号与调整活动平台不匹配");
                        }
                    }
                }
                if(StringUtils.isBlank(refundPriceDiffDetail.getActivityRealId())){
                    throw new ProcessException("请选择活动");
                }
                if(StringUtils.isBlank(refundPriceDiffDetail.getOldOrgId())){
                    throw new ProcessException("请填写目标机构编码");
                }
                if(StringUtils.isBlank(refundPriceDiffDetail.getNewOrgId())){
                    throw new ProcessException("请填写原机构编码");
                }
                //变更后活动实体
                OActivity oActivity = activityMapper.selectByPrimaryKey(refundPriceDiffDetail.getActivityRealId());
                if(null==oActivity){
                    log.info("查询oActivity异常");
                    throw new ProcessException("保存失败");
                }
                //TODO 校验是否有审批中的活动变更
                ORefundPriceDiffDetailExample example = new ORefundPriceDiffDetailExample();
                example.or()
                        .andBeginSnBetween(refundPriceDiffDetail.getBeginSn(),refundPriceDiffDetail.getEndSn())
                        .andStatusEqualTo(Status.STATUS_1.status);
                example.or() .andEndSnBetween(refundPriceDiffDetail.getBeginSn(),refundPriceDiffDetail.getEndSn())
                        .andStatusEqualTo(Status.STATUS_1.status);
                List<ORefundPriceDiffDetail> listDetail = refundPriceDiffDetailMapper.selectByExample(example);
                if(listDetail.size()>0){
                    for (ORefundPriceDiffDetail detail : listDetail) {
                        ORefundPriceDiff diff = refundPriceDiffMapper.selectByPrimaryKey(detail.getRefundPriceDiffId());
                        if(AgStatus.Approving.status.compareTo(diff.getReviewStatus())==0){
                            throw new ProcessException(detail.getBeginSn()+"-"+detail.getEndSn()+"活动调整正在审批中");
                        }
                    }
                }

                refundPriceDiffDetail.setId(idService.genId(TabId.o_Refund_price_diff_d));
                refundPriceDiffDetail.setRefundPriceDiffId(priceDiffId);
                refundPriceDiffDetail.setFrontPrice(logisticsDetail!=null?new BigDecimal(logisticsDetail.get("SETTLEMENT_PRICE").toString()):new BigDecimal(0));
                refundPriceDiffDetail.setActivityName(oActivity.getActivityName());
                refundPriceDiffDetail.setActivityWay(oActivity.getActivityWay());
                refundPriceDiffDetail.setActivityRule(oActivity.getActivityRule());
                refundPriceDiffDetail.setPrice(oActivity.getPrice());
                refundPriceDiffDetail.setVender(oActivity.getVender());
                refundPriceDiffDetail.setProModel(oActivity.getProModel());
                refundPriceDiffDetail.setcTime(nowDate);
                refundPriceDiffDetail.setuTime(nowDate);
                refundPriceDiffDetail.setsTime(nowDate);
                refundPriceDiffDetail.setcUser(cUser);
                refundPriceDiffDetail.setuUser(cUser);
                refundPriceDiffDetail.setStatus(Status.STATUS_1.status);
                refundPriceDiffDetail.setVersion(Status.STATUS_0.status);
                refundPriceDiffDetail.setOrderType(OrderType.NEW.getValue());
                refundPriceDiffDetail.setSendStatus(LogisticsSendStatus.none_send.code);
                OActivity frontActivity = activityMapper.selectByPrimaryKey(refundPriceDiffDetail.getActivityRealId());
                if(frontActivity==null){
                    throw new ProcessException("查询新活动失败");
                }
                refundPriceDiffDetail.setFrontProId(frontActivity.getProductId());
                OProduct oProduct = productService.findById(frontActivity.getProductId());
                if(oProduct==null){
                    throw new ProcessException("查询新活动商品失败");
                }
                refundPriceDiffDetail.setFrontProName(oProduct.getProName());
                int priceDiffDetailInsert = refundPriceDiffDetailMapper.insert(refundPriceDiffDetail);
                if(priceDiffDetailInsert!=1){
                    log.info("插入补退差价详情表异常");
                    throw new ProcessException("保存失败");
                }

                OSubOrderExample subOrderExample = new OSubOrderExample();
                OSubOrderExample.Criteria subOrderCriteria = subOrderExample.createCriteria();
                subOrderCriteria.andStatusEqualTo(Status.STATUS_1.status);
                subOrderCriteria.andOrderIdEqualTo(refundPriceDiffDetail.getOrderId());
                subOrderCriteria.andProIdEqualTo(refundPriceDiffDetail.getProId());
                List<OSubOrder> oSubOrders = subOrderMapper.selectByExample(subOrderExample);
                OSubOrder oSubOrder = oSubOrders.get(0);
                if(oSubOrder==null){
                    throw new ProcessException("采购单不唯一");
                }

                OSubOrderActivityExample oSubOrderActivityExample = new OSubOrderActivityExample();
                OSubOrderActivityExample.Criteria criteria = oSubOrderActivityExample.createCriteria();
                criteria.andStatusEqualTo(Status.STATUS_1.status);
                criteria.andSubOrderIdEqualTo(oSubOrder.getId());
                List<OSubOrderActivity> oSubOrderActivities = subOrderActivityMapper.selectByExample(oSubOrderActivityExample);
                OSubOrderActivity oSubOrderActivity = oSubOrderActivities.get(0);
                if(oSubOrderActivity==null){
                    throw new ProcessException("活动不唯一");
                }
                refundPriceDiffDetail.setNewMachineId(frontActivity.getBusProCode());
                refundPriceDiffDetail.setOldMachineId(oSubOrderActivity.getBusProCode());
//                refundPriceDiffDetail.set
            });

            AgentResult synOrVerifyResult = termMachineService.synOrVerifyCompensate(refundPriceDiffDetailList, "check");
            if(!synOrVerifyResult.isOK()){
                throw new ProcessException(synOrVerifyResult.getMsg());
            }

            if(agentVo.getFlag().equals("2")){
                startCompensateActiviy(priceDiffId,cUser);
            }
            return AgentResult.ok(priceDiffId);
        }catch (ProcessException e) {
            e.printStackTrace();
            log.info("退补差价保存失败");
            throw new ProcessException(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            log.info("退补差价保存失败");
            throw new ProcessException("退补差价保存失败");
        }
    }


    @Transactional(rollbackFor = Exception.class, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    @Override
    public AgentResult startCompensateActiviy(String id, String cuser) throws Exception {

        if (StringUtils.isBlank(id)) {
            log.info("退补差价提交审批,订单ID为空{}:{}", id, cuser);
            return AgentResult.fail("退补差价提交审批，订单ID为空");
        }
        if (StringUtils.isBlank(cuser)) {
            log.info("退补差价提交审批,操作用户为空{}:{}", id, cuser);
            return AgentResult.fail("退补差价审批中，操作用户为空");
        }
        //更新审批中
        ORefundPriceDiff oRefundPriceDiff = refundPriceDiffMapper.selectByPrimaryKey(id);
        if (oRefundPriceDiff.getReviewStatus().equals(AgStatus.Approving.name())) {
            log.info("退补差价提交审批,禁止重复提交审批{}:{}", id, cuser);
            return AgentResult.fail("退补差价提交审批，禁止重复提交审批");
        }

        if (!oRefundPriceDiff.getStatus().equals(Status.STATUS_1.status)) {
            log.info("退补差价提交审批,代理商信息已失效{}:{}", id, cuser);
            return AgentResult.fail("退补差价信息已失效");
        }

        ORefundPriceDiff updateRefundPriceDiff = new ORefundPriceDiff();
        updateRefundPriceDiff.setVersion(oRefundPriceDiff.getVersion());
        updateRefundPriceDiff.setId(id);
        updateRefundPriceDiff.setReviewStatus(AgStatus.Approving.status);
        int i = refundPriceDiffMapper.updateByPrimaryKeySelective(updateRefundPriceDiff);
        if (1 != i) {
            log.info("退补差价提交审批，更新订单基本信息失败{}:{}", id, cuser);
            throw new MessageException("退补差价提交审批，更新退补差价基本信息失败");
        }

        Map startPar = agentEnterService.startPar(cuser);
        if (null == startPar) {
            log.info("========用户{}{}启动部门参数为空", id, cuser);
            throw new MessageException("启动部门参数为空!");
        }
        String party = String.valueOf(startPar.get("party"));
        String workId;
        //根据不同的部门信息启动不同的流程
        if(agentService.isAgent(cuser).isOK()){
            workId = dictOptionsService.getApproveVersion("agentCompensation");
        }else{
            workId = dictOptionsService.getApproveVersion("compensation");
        }
        if(startPar.get("party").toString().equals("beijing")) {
            startPar.put("rs", ApprovalType.PASS.getValue());
        }
        //启动审批
        String proce = activityService.createDeloyFlow(null, workId, null, null, startPar);
        if (proce == null) {
            log.info("退补差价提交审批，审批流启动失败{}:{}", id, cuser);
            throw new MessageException("审批流启动失败!");
        }
        //代理商业务审批关系
        BusActRel record = new BusActRel();
        record.setBusId(id);
        record.setActivId(proce);
        record.setcTime(Calendar.getInstance().getTime());
        record.setcUser(cuser);
        record.setStatus(Status.STATUS_1.status);
        record.setBusType(BusActRelBusType.COMPENSATE.name());
        record.setActivStatus(AgStatus.Approving.name());
        record.setAgentId(oRefundPriceDiff.getAgentId());
        record.setDataShiro(BusActRelBusType.COMPENSATE.key);
        List<Map<String, Object>> maps = iUserService.orgCode(Long.valueOf(cuser));
        if(maps!=null && maps.size()>0){
            Map<String, Object> stringObjectMap = maps.get(0);
            record.setAgDocPro(stringObjectMap.get("ORGID")+"");
            if(null!=stringObjectMap.get("isRegion") && (Boolean)stringObjectMap.get("isRegion"))
            record.setAgDocDistrict(stringObjectMap.get("ORGPID")+"");
            if(null!=stringObjectMap.get("ppidorgcodeisRegion") && (Boolean)stringObjectMap.get("ppidorgcodeisRegion"))
                record.setAgDocDistrict(stringObjectMap.get("ORGPPID")+"");
        }else{
            throw new MessageException("未获取到部门编号!");
        }
        if(StringUtils.isBlank(record.getAgDocDistrict())){
            throw new MessageException("未获取到部门编号!");
        }
        Agent agent = agentMapper.selectByPrimaryKey(oRefundPriceDiff.getAgentId());
        if(null!=agent)
        record.setAgentName(agent.getAgName());
        if (1 != busActRelMapper.insertSelective(record)) {
            log.info("订单提交审批，启动审批异常，添加审批关系失败{}:{}", id, proce);
            throw new MessageException("审批流启动失败:添加审批关系失败");
        }

        ORefundPriceDiffDetailExample oRefundPriceDiffDetailExample = new ORefundPriceDiffDetailExample();
        ORefundPriceDiffDetailExample.Criteria criteria = oRefundPriceDiffDetailExample.createCriteria();
        criteria.andRefundPriceDiffIdEqualTo(id);
        List<ORefundPriceDiffDetail> oRefundPriceDiffDetails = refundPriceDiffDetailMapper.selectByExample(oRefundPriceDiffDetailExample);
        if (null == oRefundPriceDiffDetails) {
            throw new MessageException("审批流启动失败:查询明细异常");
        }
        for (ORefundPriceDiffDetail row : oRefundPriceDiffDetails) {
            Map<String, Object> updateMap = new HashMap<>();
            updateMap.put("setRecordStatus", OLogisticsDetailStatus.RECORD_STATUS_LOC.code);
            updateMap.put("refundPriceDiffDetailId", row.getId());
            //where条件
            updateMap.put("snBegin", row.getBeginSn());
            updateMap.put("snEnd", row.getEndSn());
            updateMap.put("status", OLogisticsDetailStatus.STATUS_FH.code);
            updateMap.put("recordStatus", OLogisticsDetailStatus.RECORD_STATUS_VAL.code);
            int j = logisticsDetailMapper.updateCompensate(updateMap);
            if (!row.getChangeCount().equals(new BigDecimal(j))) {
                log.info("插入补退差价锁定物流明细表异常");
                throw new MessageException("已锁定请核对补差价信息!");
            }
        }
        AgentResult agentResult = cashReceivablesService.startProcing(CashPayType.REFUNDPRICEDIFF,oRefundPriceDiff.getId(),cuser);
        if(!agentResult.isOK()){
            log.info("插入补退差价更新打款信息失败");
            throw new MessageException("补退差价更新打款信息失败");
        }
        return AgentResult.ok();
    }


    /**
     * 处理任务
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW,isolation = Isolation.DEFAULT,rollbackFor = Exception.class)
    @Override
    public AgentResult approvalTask(AgentVo agentVo, String userId) throws ProcessException{
        try {
            if(agentVo.getApprovalResult().equals(ApprovalType.PASS.getValue())){
                BigDecimal deductAmt = new BigDecimal(0);
                if(agentVo.getDeductCapitalList()!=null && agentVo.getDeductCapitalList().size()!=0){
                    if(agentVo.getDeductCapitalList()!=null)
                    for (ODeductCapital oDeductCapital : agentVo.getDeductCapitalList()) {
                        oDeductCapital.setId(idService.genId(TabId.o_deduct_capital));
                        Date nowDate = new Date();
                        oDeductCapital.setcTime(nowDate);
                        oDeductCapital.setcUtime(nowDate);
                        oDeductCapital.setStatus(Status.STATUS_1.status);
                        oDeductCapital.setVersion(Status.STATUS_0.status);
                        deductAmt = deductAmt.add(oDeductCapital.getcAmount());
                        int insert = deductCapitalMapper.insert(oDeductCapital);
                        if(insert!=1){
                            throw new ProcessException("工作流处理任务DeductCapita异常");
                        }
                    }
                }
                //添加新的附件
                if (agentVo.getRefundPriceDiffFinanceFile()!= null) {
                    agentVo.getRefundPriceDiffFinanceFile().forEach(fileId->{
                        AttachmentRel record = new AttachmentRel();
                        record.setAttId(fileId);
                        record.setSrcId(agentVo.getAgentBusId());
                        record.setcUser(userId);
                        record.setcTime(Calendar.getInstance().getTime());
                        record.setStatus(Status.STATUS_1.status);
                        record.setBusType(AttachmentRelType.ActivityFinanceEdit.name());
                        record.setId(idService.genId(TabId.a_attachment_rel));
                        int i = attachmentRelMapper.insertSelective(record);
                        if (1 != i) {
                            log.info("活动变更财务审批附件关系失败");
                            throw new ProcessException("系统异常");
                        }
                    });
                }
                //1表示机具欠款已抵扣
                ORefundPriceDiff oRefundPriceDiff= refundPriceDiffMapper.selectByPrimaryKey(agentVo.getAgentBusId());
                if(agentVo.getFlag().equals("1")){
                    BigDecimal subtract = oRefundPriceDiff.getRelCompAmt().subtract(agentVo.getoRefundPriceDiffVo().getMachOweAmt());
                    String subtractStr = String.valueOf(subtract);
                    if(subtractStr.contains("-")){
                        agentVo.getoRefundPriceDiffVo().setMachOweAmt(oRefundPriceDiff.getRelCompAmt());
                    }
                    agentVo.getoRefundPriceDiffVo().setRelCompAmt(subtractStr.contains("-")?new BigDecimal(0):subtract);
                    deductAmt = oRefundPriceDiff.getDeductAmt().add(agentVo.getoRefundPriceDiffVo().getMachOweAmt());
                }

                AgentResult agentResult = compensateService.updateTask(agentVo, deductAmt,userId,agentVo.getoCashReceivablesVoList());
                if(!agentResult.isOK()){
                    throw new ProcessException("更新退补差价主表异常");
                }
            }
            AgentResult result = agentEnterService.completeTaskEnterActivity(agentVo,userId);
            if(!result.isOK()){
                log.error(result.getMsg());
                throw new ProcessException("工作流处理任务异常");
            }
        } catch (ProcessException e) {
            e.printStackTrace();
            throw new ProcessException(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            throw new ProcessException("catch工作流处理任务异常!");
        }
        return AgentResult.ok();
    }

    /**
     * 退补差价处理
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW,isolation = Isolation.DEFAULT,rollbackFor = Exception.class)
    @Override
    public AgentResult updateTask(AgentVo agentVo,BigDecimal deductAmt,String userId,List<OCashReceivablesVo> cashReceivablesVoList)throws Exception{
        ORefundPriceDiff oRefundPriceDiff= refundPriceDiffMapper.selectByPrimaryKey(agentVo.getAgentBusId());
        ORefundPriceDiffVo updatePriceDiff = new ORefundPriceDiffVo();
        updatePriceDiff.setId(oRefundPriceDiff.getId());
        List<Map<String, Object>> orgCodeRes = iUserService.orgCode(Long.valueOf(userId));
        if (null == orgCodeRes) {
            throw new ProcessException("部门参数为空！");
        }
        Map<String, Object> map = orgCodeRes.get(0);
        String orgCode = String.valueOf(map.get("ORGANIZATIONCODE"));
        if(orgCode.equals("business")){
            //            if(oRefundPriceDiff.getOrderType().compareTo(OrderType.OLD.getValue())==0){
            //                List<ORefundPriceDiffDetail> refundPriceDiffDetailList = agentVo.getRefundPriceDiffDetailList();
            //                for (ORefundPriceDiffDetail oRefundPriceDiffDetail : refundPriceDiffDetailList) {
            //                    ORefundPriceDiffDetail upPriceDiffDetail = refundPriceDiffDetailMapper.selectByPrimaryKey(oRefundPriceDiffDetail.getId());
            //                    if(StringUtils.isNotBlank(oRefundPriceDiffDetail.getSubOrderId())){
            //                        upPriceDiffDetail.setSubOrderId(oRefundPriceDiffDetail.getSubOrderId());
            //                        OSubOrder oSubOrder = subOrderMapper.selectByPrimaryKey(oRefundPriceDiffDetail.getSubOrderId());
            //                        if(null==oSubOrder){
            //                            throw new ProcessException("商品不存在");
            //                        }
            //                        upPriceDiffDetail.setProId(oSubOrder.getProId());
            //                        upPriceDiffDetail.setProName(oSubOrder.getProName());
            //                    }
            //                    if(StringUtils.isNotBlank(oRefundPriceDiffDetail.getOrderId())){
            //                        upPriceDiffDetail.setOrderId(oRefundPriceDiffDetail.getOrderId());
            //                    }
            //                    int i = refundPriceDiffDetailMapper.updateByPrimaryKeySelective(upPriceDiffDetail);
            //                    if(i!=1){
            //                        throw new ProcessException("更新明细失败");
            //                    }
            //                }
            //            }
            if(StringUtils.isBlank(agentVo.getDeliveryTime())){
                throw new ProcessException("请填写发货时间");
            }
            ORefundPriceDiffDetailExample oRefundPriceDiffDetailExample = new ORefundPriceDiffDetailExample();
            ORefundPriceDiffDetailExample.Criteria criteria = oRefundPriceDiffDetailExample.createCriteria();
            criteria.andStatusEqualTo(Status.STATUS_1.status);
            criteria.andRefundPriceDiffIdEqualTo(oRefundPriceDiff.getId());
            List<ORefundPriceDiffDetail> oRefundPriceDiffDetails = refundPriceDiffDetailMapper.selectByExample(oRefundPriceDiffDetailExample);
            for (ORefundPriceDiffDetail oRefundPriceDiffDetail : oRefundPriceDiffDetails) {
                oRefundPriceDiffDetail.setDeliveryTime(agentVo.getDeliveryTime());
                int i = refundPriceDiffDetailMapper.updateByPrimaryKeySelective(oRefundPriceDiffDetail);
                if(i==0){
                    throw new ProcessException("更新发货时间失败");
                }
            }
        }
        //业务审批
        if(agentVo.getDeductCapitalList()!=null && agentVo.getDeductCapitalList().size()!=0) {
            updatePriceDiff.setDeductAmt(deductAmt);
            BigDecimal relCompAmt = oRefundPriceDiff.getApplyCompAmt().subtract(deductAmt);
            String relCompAmtStr = String.valueOf(relCompAmt);
            updatePriceDiff.setRelCompType(relCompAmtStr.contains("-") ? PriceDiffType.DETAIN_AMT.getValue() : PriceDiffType.REPAIR_AMT.getValue());
            updatePriceDiff.setRelCompAmt(relCompAmtStr.contains("-") ? new BigDecimal(relCompAmtStr.substring(1)) : new BigDecimal(relCompAmtStr));
        }
        if(null!=agentVo.getoRefundPriceDiffVo())
        //财务审批
        if(StringUtils.isNotBlank(agentVo.getoRefundPriceDiffVo().getGatherTimeStr())){
            updatePriceDiff.setGatherTime(DateUtil.getDateFromStr(agentVo.getoRefundPriceDiffVo().getGatherTimeStr(),DateUtil.DATE_FORMAT_1));
            updatePriceDiff.setGatherAmt(agentVo.getoRefundPriceDiffVo().getGatherAmt());
            AgentResult cashAgentResult = cashReceivablesService.approveTashBusiness(CashPayType.REFUNDPRICEDIFF,oRefundPriceDiff.getId(),userId,new Date(),cashReceivablesVoList);
            if(!cashAgentResult.isOK()){
                throw new ProcessException("更新收款信息失败");
            }
        }
        if(agentVo.getFlag().equals("1")){
            updatePriceDiff.setRelCompAmt(agentVo.getoRefundPriceDiffVo().getRelCompAmt());
            updatePriceDiff.setMachOweAmt(agentVo.getoRefundPriceDiffVo().getMachOweAmt());
            updatePriceDiff.setDeductAmt(deductAmt);
        }
        updatePriceDiff.setuTime(new Date());
        updatePriceDiff.setuUser(userId);
        int i = refundPriceDiffMapper.updateByPrimaryKeySelective(updatePriceDiff);
        if(i!=1){
            return AgentResult.fail("更新异常");
        }
        return AgentResult.ok();
    }

    /**
     * 完成处理
     * @param proIns
     * @param agStatus
     * @return
     */
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    @Override
    public AgentResult compressCompensateActivity(String proIns, BigDecimal agStatus)throws Exception{

        BusActRelExample example = new BusActRelExample();
        example.or().andActivIdEqualTo(proIns).andActivStatusEqualTo(AgStatus.Approving.name());
        List<BusActRel> list = busActRelMapper.selectByExample(example);
        if (list.size() != 1) {
            log.info("审批任务结束{}{}，未找到审批中的审批和数据关系", proIns, agStatus);
            return null;
        }
        BusActRel rel = list.get(0);
        ORefundPriceDiff oRefundPriceDiff = refundPriceDiffMapper.selectByPrimaryKey(rel.getBusId());
        if (null==oRefundPriceDiff) {
            log.info("审批任务结束{}{}，ORefundPriceDiff为null", proIns, agStatus);
            return null;
        }
        oRefundPriceDiff.setReviewStatus(agStatus);
        Date nowDate = new Date();
        oRefundPriceDiff.setuTime(nowDate);
        if(agStatus.compareTo(AgStatus.Approved.getValue())==0){
            oRefundPriceDiff.setAppTime(nowDate);
        }
        int i = refundPriceDiffMapper.updateByPrimaryKeySelective(oRefundPriceDiff);
        if(i!=1){
            throw new ProcessException("更新退补差价数据申请失败");
        }

        AgentResult agentResult = AgentResult.fail();
        if(agStatus.compareTo(AgStatus.Refuse.getValue())==0){
            agentResult = cashReceivablesService.refuseProcing(CashPayType.REFUNDPRICEDIFF,oRefundPriceDiff.getId(),oRefundPriceDiff.getcUser());
        }
        if(agStatus.compareTo(AgStatus.Approved.getValue())==0){
            agentResult = cashReceivablesService.finishProcing(CashPayType.REFUNDPRICEDIFF,oRefundPriceDiff.getId(),oRefundPriceDiff.getcUser());
        }
        if(!agentResult.isOK()){
            throw new ProcessException("更新打款记录失败");
        }

        ORefundPriceDiffDetailExample oRefundPriceDiffDetailExample = new ORefundPriceDiffDetailExample();
        ORefundPriceDiffDetailExample.Criteria criteria = oRefundPriceDiffDetailExample.createCriteria();
        criteria.andRefundPriceDiffIdEqualTo(rel.getBusId());
        criteria.andStatusEqualTo(Status.STATUS_1.status);
        List<ORefundPriceDiffDetail> oRefundPriceDiffDetails = refundPriceDiffDetailMapper.selectByExample(oRefundPriceDiffDetailExample);

        if(agStatus.compareTo(AgStatus.Refuse.getValue())==0){
            oRefundPriceDiffDetails.forEach(row->{
                OLogisticsDetailExample oLogisticsDetailExample = new OLogisticsDetailExample();
                OLogisticsDetailExample.Criteria criteria1 = oLogisticsDetailExample.createCriteria();
                criteria1.andSnNumBetween(row.getBeginSn(),row.getEndSn());
                criteria1.andStatusEqualTo(OLogisticsDetailStatus.STATUS_FH.code);
                criteria1.andRecordStatusEqualTo(OLogisticsDetailStatus.RECORD_STATUS_LOC.code);
                List<OLogisticsDetail> oLogisticsDetails = logisticsDetailMapper.selectByExample(oLogisticsDetailExample);
                if(null==oLogisticsDetails){
                    throw new ProcessException("退补差价数据完成失败");
                }
                oLogisticsDetails.forEach(oLogisticsDetail->{
                    oLogisticsDetail.setRecordStatus(OLogisticsDetailStatus.RECORD_STATUS_VAL.code);
                    int update = logisticsDetailMapper.updateByPrimaryKeySelective(oLogisticsDetail);
                    if(1!=update){
                        throw new ProcessException("退补差价数据更新完成失败");
                    }
                });
            });
        }else if(agStatus.compareTo(AgStatus.Approved.getValue())==0){
            oRefundPriceDiffDetails.forEach(row->{
                try {
                    OLogisticsDetailExample oLogisticsDetailExample = new OLogisticsDetailExample();
                    OLogisticsDetailExample.Criteria criteria1 = oLogisticsDetailExample.createCriteria();
                    criteria1.andSnNumBetween(row.getBeginSn(),row.getEndSn());
                    criteria1.andStatusEqualTo(OLogisticsDetailStatus.STATUS_FH.code);
                    criteria1.andRecordStatusEqualTo(OLogisticsDetailStatus.RECORD_STATUS_LOC.code);
                    List<OLogisticsDetail> oLogisticsDetails = logisticsDetailMapper.selectByExample(oLogisticsDetailExample);
                    if(null==oLogisticsDetails){
                        throw new ProcessException("退补差价数据完成失败");
                    }
                    OActivity activity = orderActivityService.findById(row.getActivityRealId());
                    OActivity activityOld = orderActivityService.findById(row.getActivityFrontId());

                    oLogisticsDetails.forEach(oLogisticsDetail->{
                        try {
                            //更新
                            oLogisticsDetail.setRecordStatus(OLogisticsDetailStatus.RECORD_STATUS_HIS.code);
                            int update = logisticsDetailMapper.updateByPrimaryKeySelective(oLogisticsDetail);
                            if(1!=update){
                                throw new ProcessException("退补差价数据更新完成失败");
                            }
                            //插入新的物流信息
                            oLogisticsDetail.setId(idService.genId(TabId.o_logistics_detail));
                            oLogisticsDetail.setOptId(row.getId());
                            oLogisticsDetail.setOptType(OLogisticsDetailOptType.BCJ.code);
                            oLogisticsDetail.setActivityId(row.getActivityRealId());
                            oLogisticsDetail.setActivityName(row.getActivityName());

                            oLogisticsDetail.setgTime(activity.getgTime());
                            oLogisticsDetail.setSettlementPrice(row.getPrice());
                            oLogisticsDetail.setcTime(new Date());
                            oLogisticsDetail.setuTime(new Date());
                            oLogisticsDetail.setRecordStatus(OLogisticsDetailStatus.RECORD_STATUS_VAL.code);

                            oLogisticsDetail.setBusProCode(activity.getBusProCode());
                            oLogisticsDetail.setBusProName(activity.getBusProName());
                            oLogisticsDetail.setTermBatchcode(activity.getTermBatchcode());
                            oLogisticsDetail.setTermBatchname(activity.getTermBatchname());
                            oLogisticsDetail.setTermtype(activity.getTermtype());
                            oLogisticsDetail.setTermtypename(activity.getTermtypename());
                            oLogisticsDetail.setVersion(Status.STATUS_0.status);
                            oLogisticsDetail.setPosType(activity.getPosType());
                            oLogisticsDetail.setPosSpePrice(activity.getPosSpePrice());
                            oLogisticsDetail.setStandTime(activity.getStandTime());
                            OOrder oOrder = oOrderMapper.selectByPrimaryKey(oLogisticsDetail.getOrderId());
                            oLogisticsDetail.setBusId(oOrder.getBusId());
                            int insert = logisticsDetailMapper.insert(oLogisticsDetail);
                            if(1!=insert){
                                throw new ProcessException("退补差价数据新增完成失败");
                            }
                        } catch (Exception e) {
                            throw new ProcessException("退补差价处理完成失败");
                        }

                    });
                    row.setAppTime(new Date());
                    row.setSendStatus(LogisticsSendStatus.send_ing.code);
                    int j = refundPriceDiffDetailMapper.updateByPrimaryKeySelective(row);
                    if(j!=1){
                        throw new ProcessException("退补差价更新失败");
                    }
                    OSubOrderExample subOrderExample = new OSubOrderExample();
                    OSubOrderExample.Criteria subOrderCriteria = subOrderExample.createCriteria();
                    subOrderCriteria.andStatusEqualTo(Status.STATUS_1.status);
                    subOrderCriteria.andOrderIdEqualTo(row.getOrderId());
                    subOrderCriteria.andProIdEqualTo(row.getProId());
                    List<OSubOrder> oSubOrders = subOrderMapper.selectByExample(subOrderExample);
                    OSubOrder oSubOrder = oSubOrders.get(0);
                    if(oSubOrder==null){
                        throw new ProcessException("采购单不唯一");
                    }

                    OSubOrderActivityExample oSubOrderActivityExample = new OSubOrderActivityExample();
                    OSubOrderActivityExample.Criteria oSubOrderCriteria = oSubOrderActivityExample.createCriteria();
                    oSubOrderCriteria.andStatusEqualTo(Status.STATUS_1.status);
                    oSubOrderCriteria.andSubOrderIdEqualTo(oSubOrder.getId());
                    List<OSubOrderActivity> oSubOrderActivities = subOrderActivityMapper.selectByExample(oSubOrderActivityExample);
                    OSubOrderActivity oSubOrderActivity = oSubOrderActivities.get(0);
                    if(oSubOrderActivity==null){
                        throw new ProcessException("活动不唯一");
                    }
                    row.setNewMachineId(activity.getBusProCode());
                    row.setOldMachineId(oSubOrderActivity.getBusProCode());

                }catch (ProcessException e) {
                    e.printStackTrace();
                    throw new ProcessException(e.getMessage());
                }catch (Exception e) {
                    e.printStackTrace();
                    throw new ProcessException("处理失败");
                }
            });

            AgentResult synOrVerifyResult = termMachineService.synOrVerifyCompensate(oRefundPriceDiffDetails, "adjust");
            if(!synOrVerifyResult.isOK()){
                throw new ProcessException(synOrVerifyResult.getMsg());
            }
        }
        return AgentResult.ok();
    }

    /**
     * 补差价发送到业务系统
     * @param row
     * @param oLogisticsDetails
     * @param activity
     * @param activityOld
     * @throws Exception
     */
    public void sendBusinessSystem(ORefundPriceDiffDetail row,List<OLogisticsDetail> oLogisticsDetails,OActivity activity,OActivity activityOld)throws ProcessException{

        //待调整集合 cxinfo 机具的调整  调货明细
        OOrder oo = oOrderMapper.selectByPrimaryKey(row.getOrderId());
        AgentBusInfo agentBusInfo = agentBusInfoMapper.selectByPrimaryKey(oo.getBusId());
        PlatformType platformType = platFormService.byPlatformCode(agentBusInfo.getBusPlatform());

        ChangeActMachineVo cav = new ChangeActMachineVo();
        cav.setBusNum(agentBusInfo.getBusNum());
        cav.setNewAct(activity.getBusProCode());
        cav.setOldAct(activityOld.getBusProCode());
        cav.setOptUser(row.getcUser());

        //起始sn
        OLogisticsDetailExample exampleOLogisticsDetailExamplestart = new OLogisticsDetailExample();
        exampleOLogisticsDetailExamplestart.or().andSnNumEqualTo(row.getBeginSn())
                .andRecordStatusEqualTo(OLogisticsDetailStatus.RECORD_STATUS_VAL.code)
                .andStatusEqualTo(OLogisticsDetailStatus.STATUS_FH.code);
        exampleOLogisticsDetailExamplestart.setOrderByClause(" c_time desc");
        List<OLogisticsDetail> logisticsDetailsstart = logisticsDetailMapper.selectByExample(exampleOLogisticsDetailExamplestart);
        if(logisticsDetailsstart.size()!=1){
            log.info("退货补差价sn码查询失败{}发货有效数量不唯一",row.getBeginSn());
            throw new ProcessException("退货补差价sn码查询失败"+row.getBeginSn()+"发货有效数量不唯一");
        }
        OLogisticsDetail detailstart = logisticsDetailsstart.get(0);

        //结束sn
        OLogisticsDetailExample exampleOLogisticsDetailExampleend = new OLogisticsDetailExample();
        exampleOLogisticsDetailExampleend.or().andSnNumEqualTo(row.getEndSn())
                .andRecordStatusEqualTo(OLogisticsDetailStatus.RECORD_STATUS_VAL.code)
                .andStatusEqualTo(OLogisticsDetailStatus.STATUS_FH.code);
        exampleOLogisticsDetailExampleend.setOrderByClause(" c_time desc");
        List<OLogisticsDetail> logisticsDetailsend = logisticsDetailMapper.selectByExample(exampleOLogisticsDetailExampleend);
        if(logisticsDetailsstart.size()!=1){
            log.info("退货补差价sn码查询失败{}发货有效数量不唯一",row.getEndSn());
            throw new ProcessException("退货补差价sn码查询失败"+row.getEndSn()+"发货有效数量不唯一");
        }
        OLogisticsDetail detailend = logisticsDetailsend.get(0);

        cav.setSnStart(detailstart.getSnNum()+(detailstart.getTerminalidCheck()==null?"":detailstart.getTerminalidCheck()));
        cav.setSnEnd(detailend.getSnNum()+(detailend.getTerminalidCheck()==null?"":detailend.getTerminalidCheck()));

        cav.setPlatformType(platformType.code);
        cav.setoRefundPriceDiffDetailId(row.getId());
        cav.setLogisticsDetailList(oLogisticsDetails);
        cav.setSnNum(oLogisticsDetails.size()+"");

        //cxinfo 调用活动变更接口进行活动的变更
        try {
            AgentResult chAgentResult = termMachineService.changeActMachine(cav);
            row.setSendMsg(chAgentResult.getMsg());
            if(chAgentResult.isOK()){
                row.setSendStatus(Status.STATUS_1.status);
            }else{
                row.setSendStatus(Status.STATUS_2.status);
            }
            refundPriceDiffDetailMapper.updateByPrimaryKeySelective(row);
        }catch (ProcessException e) {
            e.printStackTrace();
            throw new ProcessException(e.getMessage());
        }catch (Exception e) {
            e.printStackTrace();
            throw new ProcessException(e.getLocalizedMessage());
        }
    }

    /**
     * 手动处理
     * @param id
     * @throws Exception
     */
    @Override
    public void manualDispose(String id)throws ProcessException{

        ORefundPriceDiffDetail oRefundPriceDiffDetail = refundPriceDiffDetailMapper.selectByPrimaryKey(id);
        if(null==oRefundPriceDiffDetail){
            throw new ProcessException("退补差价明细不存在");
        }
        if(oRefundPriceDiffDetail.getSendStatus().compareTo(Status.STATUS_1.status)==0){
            throw new ProcessException("已联动成功请勿重复发送");
        }
        OLogisticsDetailExample oLogisticsDetailExample = new OLogisticsDetailExample();
        OLogisticsDetailExample.Criteria criteria1 = oLogisticsDetailExample.createCriteria();
        criteria1.andSnNumBetween(oRefundPriceDiffDetail.getBeginSn(),oRefundPriceDiffDetail.getEndSn());
        criteria1.andStatusEqualTo(OLogisticsDetailStatus.STATUS_FH.code);
        List<BigDecimal> recordStatusList = new ArrayList<>();
        recordStatusList.add(OLogisticsDetailStatus.RECORD_STATUS_LOC.code);
        recordStatusList.add(OLogisticsDetailStatus.RECORD_STATUS_VAL.code);
        criteria1.andRecordStatusIn(recordStatusList);
        List<OLogisticsDetail> oLogisticsDetails = logisticsDetailMapper.selectByExample(oLogisticsDetailExample);
        if(null==oLogisticsDetails){
            throw new ProcessException("退补差价明细不存在");
        }
        if(null==oLogisticsDetails){
            throw new ProcessException("退补差价明细不存在");
        }
        OActivity activity = orderActivityService.findById(oRefundPriceDiffDetail.getActivityRealId());
        if(null==activity){
            throw new ProcessException("新活动不存在");
        }
        OActivity activityOld = orderActivityService.findById(oRefundPriceDiffDetail.getActivityFrontId());
        if(null==activityOld){
            throw new ProcessException("旧活动不存在");
        }
        sendBusinessSystem(oRefundPriceDiffDetail,oLogisticsDetails,activity,activityOld);
    }


    /**
     * 查看退补差价明细
     * @param id
     * @return
     */
    @Override
    public ORefundPriceDiff queryRefDiffDetail(String id){
        if(StringUtils.isBlank(id)){
            return null;
        }
        ORefundPriceDiff oRefundPriceDiff = refundPriceDiffMapper.selectByPrimaryKey(id);
        if(null==oRefundPriceDiff){
            return null;
        }
        //查询关联附件
        List<Attachment> attachments = attachmentMapper.accessoryQuery(oRefundPriceDiff.getId(), AttachmentRelType.ActivityEdit.name());
        oRefundPriceDiff.setAttachmentList(attachments);
        //查询财务打款关联附件
        List<Attachment> attachmentFianceList = attachmentMapper.accessoryQuery(oRefundPriceDiff.getId(), AttachmentRelType.ActivityFinanceEdit.name());
        oRefundPriceDiff.setAttachmentFianceList(attachmentFianceList);
        //查询扣除款项
        ODeductCapitalExample oDeductCapitalExample = new ODeductCapitalExample();
        ODeductCapitalExample.Criteria criteria2 = oDeductCapitalExample.createCriteria();
        criteria2.andSourceIdEqualTo(oRefundPriceDiff.getId());
        List<ODeductCapital> oDeductCapitals = deductCapitalMapper.selectByExample(oDeductCapitalExample);
        oRefundPriceDiff.setDeductCapitalList(oDeductCapitals);

        oRefundPriceDiff.setApplyCompName(PriceDiffType.getContentByValue(oRefundPriceDiff.getApplyCompType()));
        oRefundPriceDiff.setRelCompName(PriceDiffType.getContentByValue(oRefundPriceDiff.getRelCompType()));
        ORefundPriceDiffDetailExample oRefundPriceDiffDetailExample = new ORefundPriceDiffDetailExample();
        ORefundPriceDiffDetailExample.Criteria criteria = oRefundPriceDiffDetailExample.createCriteria();
        criteria.andRefundPriceDiffIdEqualTo(oRefundPriceDiff.getId());
        List<ORefundPriceDiffDetail> oRefundPriceDiffDetails = refundPriceDiffDetailMapper.selectByExample(oRefundPriceDiffDetailExample);
        //新订单数据查询
        if(oRefundPriceDiff.getOrderType().compareTo(OrderType.NEW.getValue())==0){
            for (ORefundPriceDiffDetail oRefundPriceDiffDetail : oRefundPriceDiffDetails) {

                Dict dict = dictOptionsService.findDictByValue(DictGroup.ORDER.name(), DictGroup.ACTIVITY_DIS_TYPE.name(),oRefundPriceDiffDetail.getActivityWay());
                oRefundPriceDiffDetail.setActivityWay(dict.getdItemname());

                List<Map<String, Object>> oLogisticsDetails = null;
                Map<String, Object> reqParam = new HashMap<>();
                reqParam.put("snBegin",oRefundPriceDiffDetail.getBeginSn());
                reqParam.put("snEnd",oRefundPriceDiffDetail.getEndSn());
                reqParam.put("status",OLogisticsDetailStatus.STATUS_FH.code);

                ArrayList<Object> recordStatusList = new ArrayList<>();
                //新建
                if(oRefundPriceDiff.getReviewStatus().equals(AgStatus.Create.getValue())){
                    recordStatusList.add(OLogisticsDetailStatus.RECORD_STATUS_VAL.code);
                }else if(oRefundPriceDiff.getReviewStatus().equals(AgStatus.Approving.getValue())){
                    recordStatusList.add(OLogisticsDetailStatus.RECORD_STATUS_LOC.code);
                }else if(oRefundPriceDiff.getReviewStatus().equals(AgStatus.Approved.getValue()) || oRefundPriceDiff.getReviewStatus().equals(AgStatus.Refuse.getValue())){
                    recordStatusList.add(OLogisticsDetailStatus.RECORD_STATUS_HIS.code);
                }
                reqParam.put("recordStatusList",recordStatusList);
                if(!oRefundPriceDiff.getReviewStatus().equals(AgStatus.Create.getValue())){
                    reqParam.put("optId",oRefundPriceDiffDetail.getId());
                }
                oLogisticsDetails = logisticsDetailMapper.queryCompensateLList(reqParam);
                if(null==oLogisticsDetails){
                    log.info("calculatePriceDiff查询Sn失败请检查Sn有效性1");
                    throw new ProcessException("查询Sn失败请检查Sn有效性");
                }
                if(oLogisticsDetails.size()!=1){
                    log.info("calculatePriceDiff查询Sn失败请检查Sn有效性2");
                    throw new ProcessException("查询Sn失败请检查Sn有效性");
                }
                Map<String, Object> oLogisticsDetailMap = oLogisticsDetails.get(0);
                //TODO 查询业务平台信息进行展示
                if(StringUtils.isNotBlank(oRefundPriceDiffDetail.getNewOrgId()) && StringUtils.isNotBlank(oRefundPriceDiffDetail.getPlatformType())) {
                    PlatFormExample pe = new PlatFormExample();
                    pe.or().andPlatformTypeEqualTo(oRefundPriceDiffDetail.getPlatformType()).andStatusEqualTo(Status.STATUS_1.status).andPlatformStatusEqualTo(Status.STATUS_1.status);
                    List<PlatForm>  platFormList = platFormMapper.selectByExample(pe);
                    AgentBusInfoExample example = new AgentBusInfoExample();
                    example.or().andBusStatusEqualTo(Status.STATUS_1.status)
                            .andBusNumEqualTo(oRefundPriceDiffDetail.getNewOrgId())
                            .andBusPlatformIn(platFormList.stream().map(item->{return item.getPlatformNum();}).collect(Collectors.toList()));
                    List<AgentBusInfo>  newOrgInfo = agentBusInfoMapper.selectByExample(example);
                    if(newOrgInfo.size()>0) {
                        oLogisticsDetailMap.put("NewAgentBusInfo", newOrgInfo.get(0));
                    }
                }
                if(StringUtils.isNotBlank(oRefundPriceDiffDetail.getOldOrgId()) && StringUtils.isNotBlank(oRefundPriceDiffDetail.getPlatformType())) {
                    PlatFormExample pe = new PlatFormExample();
                    pe.or().andPlatformTypeEqualTo(oRefundPriceDiffDetail.getPlatformType()).andStatusEqualTo(Status.STATUS_1.status).andPlatformStatusEqualTo(Status.STATUS_1.status);
                    List<PlatForm>  platFormList = platFormMapper.selectByExample(pe);
                    AgentBusInfoExample example = new AgentBusInfoExample();
                    example.or().andBusStatusEqualTo(Status.STATUS_1.status)
                            .andBusNumEqualTo(oRefundPriceDiffDetail.getNewOrgId())
                            .andBusPlatformIn(platFormList.stream().map(item->{return item.getPlatformNum();}).collect(Collectors.toList()));
                    List<AgentBusInfo>  newOrgInfo = agentBusInfoMapper.selectByExample(example);
                    if(newOrgInfo.size()>0) {
                        oLogisticsDetailMap.put("OldAgentBusInfo", newOrgInfo.get(0));
                    }
                }
                oRefundPriceDiffDetail.setRefundPriceDiffDetailMap(oLogisticsDetailMap);
            }
        //历史订单数据查询
        }else{
            for (ORefundPriceDiffDetail oRefundPriceDiffDetail : oRefundPriceDiffDetails) {
                OActivity oActivity = activityMapper.selectByPrimaryKey(oRefundPriceDiffDetail.getActivityFrontId());
                oRefundPriceDiffDetail.setActivityFront(oActivity);

                OActivityExample oActivityExample = new OActivityExample();
                OActivityExample.Criteria activityCriteria = oActivityExample.createCriteria();
                activityCriteria.andStatusEqualTo(Status.STATUS_1.status);
                activityCriteria.andActCodeNotEqualTo(oActivity.getActCode());
                activityCriteria.andVenderEqualTo(oActivity.getVender());
                activityCriteria.andProModelEqualTo(oActivity.getProModel());
                activityCriteria.andPlatformEqualTo(oActivity.getPlatform());
                activityCriteria.andProTypeEqualTo(oActivity.getProType());
                Date date = new Date();
                activityCriteria.andBeginTimeLessThanOrEqualTo(date);
                activityCriteria.andEndTimeGreaterThanOrEqualTo(date);
                List<OActivity> oActivities = activityMapper.selectByExample(oActivityExample);
                oRefundPriceDiffDetail.setoActivities(oActivities);

                OProduct oProduct = productService.findById(oRefundPriceDiffDetail.getProId());
                OProduct product = new OProduct();
                product.setProType(oProduct.getProType());
                List<Map> proMaps = productService.queryGroupByProCode(product);
                for (Map proMap : proMaps) {
                    if(String.valueOf(proMap.get("proName")).equals("流量卡")){
                        proMaps.remove(proMap);
                        break;
                    }
                }
                oRefundPriceDiffDetail.setProMaps(proMaps);

                Map<String, Object> oLogisticsDetailMap = new HashedMap();
                //TODO 查询业务平台信息进行展示
                if(StringUtils.isNotBlank(oRefundPriceDiffDetail.getNewOrgId()) && StringUtils.isNotBlank(oRefundPriceDiffDetail.getPlatformType())) {
                    PlatFormExample pe = new PlatFormExample();
                    pe.or().andPlatformTypeEqualTo(oRefundPriceDiffDetail.getPlatformType()).andStatusEqualTo(Status.STATUS_1.status).andPlatformStatusEqualTo(Status.STATUS_1.status);
                    List<PlatForm>  platFormList = platFormMapper.selectByExample(pe);
                    AgentBusInfoExample example = new AgentBusInfoExample();
                    example.or().andBusStatusEqualTo(Status.STATUS_1.status)
                            .andBusNumEqualTo(oRefundPriceDiffDetail.getNewOrgId())
                            .andBusPlatformIn(platFormList.stream().map(item->{return item.getPlatformNum();}).collect(Collectors.toList()));
                    List<AgentBusInfo>  newOrgInfo = agentBusInfoMapper.selectByExample(example);
                    if(newOrgInfo.size()>0) {
                        oLogisticsDetailMap.put("NewAgentBusInfo", newOrgInfo.get(0));
                    }
                }
                if(StringUtils.isNotBlank(oRefundPriceDiffDetail.getOldOrgId()) && StringUtils.isNotBlank(oRefundPriceDiffDetail.getPlatformType())) {
                    PlatFormExample pe = new PlatFormExample();
                    pe.or().andPlatformTypeEqualTo(oRefundPriceDiffDetail.getPlatformType()).andStatusEqualTo(Status.STATUS_1.status).andPlatformStatusEqualTo(Status.STATUS_1.status);
                    List<PlatForm>  platFormList = platFormMapper.selectByExample(pe);
                    AgentBusInfoExample example = new AgentBusInfoExample();
                    example.or().andBusStatusEqualTo(Status.STATUS_1.status)
                            .andBusNumEqualTo(oRefundPriceDiffDetail.getNewOrgId())
                            .andBusPlatformIn(platFormList.stream().map(item->{return item.getPlatformNum();}).collect(Collectors.toList()));
                    List<AgentBusInfo>  newOrgInfo = agentBusInfoMapper.selectByExample(example);
                    if(newOrgInfo.size()>0) {
                        oLogisticsDetailMap.put("OldAgentBusInfo", newOrgInfo.get(0));
                    }
                }
                oRefundPriceDiffDetail.setRefundPriceDiffDetailMap(oLogisticsDetailMap);
            }
        }
        oRefundPriceDiff.setRefundPriceDiffDetailList(oRefundPriceDiffDetails);
        return oRefundPriceDiff;
    }


    @Transactional(propagation = Propagation.REQUIRES_NEW,isolation = Isolation.DEFAULT,rollbackFor = Exception.class)
    @Override
    public AgentResult compensateAmtEdit(ORefundPriceDiff oRefundPriceDiff, List<ORefundPriceDiffDetail> refundPriceDiffDetailList,
                                         List<String> refundPriceDiffFile, String cUser, List<OCashReceivablesVo> cashReceivablesVoList) {

        try {
            if(null==refundPriceDiffDetailList){
                throw new ProcessException("退补差价明细数据为空");
            }
            if(null==oRefundPriceDiff){
                throw new ProcessException("退补差价金额数据为空");
            }
            refundPriceDiffDetailList.forEach(row->{
                //查询最新活动
                OActivity oActivity = activityMapper.selectByPrimaryKey(row.getActivityRealId());
                ORefundPriceDiffDetail oRefundPriceDiffDetail = refundPriceDiffDetailMapper.selectByPrimaryKey(row.getId());
                oRefundPriceDiffDetail.setActivityRealId(row.getActivityRealId());
                oRefundPriceDiffDetail.setActivityName(oActivity.getActivityName());
                oRefundPriceDiffDetail.setActivityWay(oActivity.getActivityWay());
                oRefundPriceDiffDetail.setActivityRule(oActivity.getActivityRule());
                oRefundPriceDiffDetail.setPrice(oActivity.getPrice());
                OActivity frontActivity = activityMapper.selectByPrimaryKey(row.getActivityRealId());
                if(frontActivity==null){
                    throw new ProcessException("查询新活动失败");
                }
                row.setFrontProId(frontActivity.getProductId());
                OProduct oProduct = productService.findById(frontActivity.getProductId());
                if(oProduct==null){
                    throw new ProcessException("查询新活动商品失败");
                }
                row.setFrontProName(oProduct.getProName());
                int i = refundPriceDiffDetailMapper.updateByPrimaryKeySelective(oRefundPriceDiffDetail);
                if(i!=1){
                    throw new ProcessException("修改退补差价数据失败");
                }
            });
            if(null==oRefundPriceDiff.getId()){
                throw new ProcessException("退补差价数据id为空");
            }
            BigDecimal belowPayAmt = new BigDecimal(0);
            BigDecimal shareDeductAmt = new BigDecimal(0);
            for (OCashReceivablesVo oCashReceivablesVo : cashReceivablesVoList) {
                if(oCashReceivablesVo.getPayType().equals(PayType.YHHK.code)){
                    belowPayAmt = belowPayAmt.add(oCashReceivablesVo.getAmount());
                }else if(oCashReceivablesVo.getPayType().equals(PayType.FRDK.code)){
                    shareDeductAmt = shareDeductAmt.add(oCashReceivablesVo.getAmount());
                }
            }
            oRefundPriceDiff.setBelowPayAmt(belowPayAmt);
            oRefundPriceDiff.setShareDeductAmt(shareDeductAmt);
            int k = refundPriceDiffMapper.updateByPrimaryKeySelective(oRefundPriceDiff);
            if(k!=1){
                throw new ProcessException("更新退补差价数据失败");
            }
            //附件修改
            if(null!=refundPriceDiffFile){
                AttachmentRelExample attachmentRelExample = new AttachmentRelExample();
                AttachmentRelExample.Criteria criteria = attachmentRelExample.createCriteria();
                criteria.andSrcIdEqualTo(oRefundPriceDiff.getId());
                criteria.andBusTypeEqualTo(AttachmentRelType.ActivityEdit.name());
                List<AttachmentRel> attachmentRels = attachmentRelMapper.selectByExample(attachmentRelExample);
                attachmentRels.forEach(row->{
                    row.setStatus(Status.STATUS_0.status);
                    int i = attachmentRelMapper.updateByPrimaryKeySelective(row);
                    if (1 != i) {
                        log.info("删除活动变更附件关系失败");
                        throw new ProcessException("删除附件失败");
                    }
                });

                refundPriceDiffFile.forEach(row->{
                    AttachmentRel record = new AttachmentRel();
                    record.setAttId(row);
                    record.setSrcId(oRefundPriceDiff.getId());
                    record.setcUser(cUser);
                    record.setcTime(Calendar.getInstance().getTime());
                    record.setStatus(Status.STATUS_1.status);
                    record.setBusType(AttachmentRelType.ActivityEdit.name());
                    record.setId(idService.genId(TabId.a_attachment_rel));
                    int i = attachmentRelMapper.insertSelective(record);
                    if (1 != i) {
                        log.info("活动变更附件关系失败");
                        throw new ProcessException("系统异常");
                    }
                });
            }
            ORefundPriceDiff refundPriceDiff = refundPriceDiffMapper.selectByPrimaryKey(oRefundPriceDiff.getId());
            cashReceivablesService.addOCashReceivables(cashReceivablesVoList,cUser,refundPriceDiff.getAgentId(),CashPayType.REFUNDPRICEDIFF,oRefundPriceDiff.getId());
        } catch (Exception e) {
            e.printStackTrace();
            log.info("退补差价修改失败");
            throw new ProcessException("退补差价修改失败");
        }
        return AgentResult.ok();
    }


    @Transactional(rollbackFor = Exception.class, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    @Override
    public AgentResult compensateAmtDel(String busId, String cUser) throws Exception {
        if (StringUtils.isBlank(busId)) {
            throw new MessageException("数据ID为空！");
        }
        ORefundPriceDiff oRefundPriceDiff = refundPriceDiffMapper.selectByPrimaryKey(busId);
        oRefundPriceDiff.setStatus(Status.STATUS_0.status);
        int i = refundPriceDiffMapper.updateByPrimaryKeySelective(oRefundPriceDiff);
        if(i!=1){
            throw new MessageException("删除失败！");
        }
        ORefundPriceDiffDetailExample oRefundPriceDiffDetailExample = new ORefundPriceDiffDetailExample();
        ORefundPriceDiffDetailExample.Criteria criteria = oRefundPriceDiffDetailExample.createCriteria();
        criteria.andStatusEqualTo(Status.STATUS_1.status);
        criteria.andRefundPriceDiffIdEqualTo(oRefundPriceDiff.getId());
        List<ORefundPriceDiffDetail> oRefundPriceDiffDetails = refundPriceDiffDetailMapper.selectByExample(oRefundPriceDiffDetailExample);
        for (ORefundPriceDiffDetail oRefundPriceDiffDetail : oRefundPriceDiffDetails) {
            oRefundPriceDiffDetail.setStatus(Status.STATUS_0.status);
            int j = refundPriceDiffDetailMapper.updateByPrimaryKeySelective(oRefundPriceDiffDetail);
            if(j!=1){
                throw new MessageException("删除明细失败！");
            }
        }
        return AgentResult.ok();
    }

    @Override
    public PageInfo compensateDetailList(ORefundPriceDiffDetail refundPriceDiffDetail, Page page, String dataRole,long userId){

        ORefundPriceDiffDetailExample refundPriceDiffDetailExample = new ORefundPriceDiffDetailExample();
        ORefundPriceDiffDetailExample.Criteria criteria = refundPriceDiffDetailExample.createCriteria();
        criteria.andStatusEqualTo(Status.STATUS_1.status);

        refundPriceDiffDetailExample.setPage(page);
        List<ORefundPriceDiffDetail> oRefundPriceDiffDetails = refundPriceDiffDetailMapper.selectByExample(refundPriceDiffDetailExample);
        PageInfo pageInfo = new PageInfo();
        pageInfo.setRows(oRefundPriceDiffDetails);
        pageInfo.setTotal((int)refundPriceDiffDetailMapper.countByExample(refundPriceDiffDetailExample));
        return pageInfo;
    }




//    @Override
    public List<ORefundPriceDiffDetail> queryPriceDiffDetail(){

        ORefundPriceDiffDetailExample oRefundPriceDiffDetailExample = new ORefundPriceDiffDetailExample();
        ORefundPriceDiffDetailExample.Criteria criteria = oRefundPriceDiffDetailExample.createCriteria();
        criteria.andStatusEqualTo(Status.STATUS_1.status);


        List<ORefundPriceDiffDetail> oRefundPriceDiffDetails = refundPriceDiffDetailMapper.selectByExample(oRefundPriceDiffDetailExample);

        return oRefundPriceDiffDetails;
    }


    @Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
//    @Override
    public void disposePriceDiffDetail(ORefundPriceDiffDetail refundPriceDiffDetail)throws MessageException{


    }


    @Override
    public List<String> querySendingOrefundPriceDiffDetail() {
        ORefundPriceDiffDetailExample oRefundPriceDiffDetailExample = new ORefundPriceDiffDetailExample();
        ORefundPriceDiffDetailExample.Criteria criteria = oRefundPriceDiffDetailExample.createCriteria();
        criteria.andSendStatusEqualTo(LogisticsSendStatus.send_ing.code)
                .andStatusEqualTo(Status.STATUS_1.status)
                .andAppTimeIsNotNull();
        oRefundPriceDiffDetailExample.setPage(new Page(1,200));
        List<ORefundPriceDiffDetail> oRefundPriceDiffDetails = refundPriceDiffDetailMapper.selectByExample(oRefundPriceDiffDetailExample);
        return oRefundPriceDiffDetails.stream().map(item ->{return item.getId();}).collect(Collectors.toList());
    }

    @Transactional(rollbackFor = Exception.class,isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED)
    @Override
    public AgentResult dealQeruySendingReault(String id)throws Exception {
        log.info("活动调整异步查询:{}",id);
        ORefundPriceDiffDetail detail =  refundPriceDiffDetailMapper.selectByPrimaryKey(id);
        try {
            AgentResult agentResult = termMachineService.queryCompensateResult(detail.getId(),detail.getPlatformType());
            if(agentResult.isOK()){
                //成功
                if("00".equals(agentResult.getData())){
                    detail.setSendStatus(LogisticsSendStatus.send_success.code);
                    detail.setSendMsg(agentResult.getMsg());
                    if(1!=refundPriceDiffDetailMapper.updateByPrimaryKeySelective(detail)){
                        log.info("活动调整异步查询 更新数据失败:{}",id);
                        throw new MessageException("更新数据失败");
                    }
                //调整中
                }else if("01".equals(agentResult.getData())){
                        log.info("活动调整异步查询 调整中:{}",id);
                //调整失败
                }else if("02".equals(agentResult.getData())){
                    detail.setSendStatus(LogisticsSendStatus.send_fail.code);
                    detail.setSendMsg(agentResult.getMsg());
                    if(1!=refundPriceDiffDetailMapper.updateByPrimaryKeySelective(detail)){
                        log.info("活动调整异步查询 更新数据失败:{}",id);
                        throw new MessageException("更新数据失败");
                    }
                //未知结果
                }else if("03".equals(agentResult.getData())){
                    log.info("活动调整异步查询 未知结果:{}",id);
                //未调整
                }else if("04".equals(agentResult.getData())){
                    detail.setSendStatus(LogisticsSendStatus.dt_send.code);
                    detail.setSendMsg(agentResult.getMsg());
                    if(1!=refundPriceDiffDetailMapper.updateByPrimaryKeySelective(detail)){
                        log.info("活动调整异步查询 更新数据失败:{}",id);
                        throw new MessageException("更新数据失败");
                    }
                }else{
                    log.info("活动调整异步查询 未知的返回结果:{}",id);
                }
            }else{
                log.info("活动调整异步查询结果调用接口失败:{} {}",id,agentResult.getMsg());
                detail.setSendStatus(LogisticsSendStatus.send_fail.code);
                detail.setSendMsg(agentResult.getMsg());
                if(1!=refundPriceDiffDetailMapper.updateByPrimaryKeySelective(detail)){
                    log.info("活动调整异步查询 更新数据失败:{}",id);
                    throw new MessageException("更新数据失败");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return AgentResult.ok();
    }
}
