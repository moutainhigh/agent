package com.ryx.credit.service.impl.order;

import com.ryx.credit.common.enumc.*;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.dao.order.*;
import com.ryx.credit.pojo.admin.agent.Dict;
import com.ryx.credit.pojo.admin.order.*;
import com.ryx.credit.pojo.admin.vo.ReceiptOrderVo;
import com.ryx.credit.service.IResourceService;
import com.ryx.credit.service.IUserService;
import com.ryx.credit.service.dict.DictOptionsService;
import com.ryx.credit.service.dict.IdService;
import com.ryx.credit.service.order.PlannerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 排单
 * Created by RYX on 2018/7/20.
 */
@Service("plannerService")
public class PlannerServiceImpl implements PlannerService {

    private static Logger log = LoggerFactory.getLogger(PlannerServiceImpl.class);
    @Autowired
    private OReceiptOrderMapper receiptOrderMapper;
    @Autowired
    private IdService idService;
    @Autowired
    private ReceiptPlanMapper receiptPlanMapper;
    @Autowired
    private OReceiptProMapper receiptProMapper;
    @Autowired
    private OSubOrderMapper oSubOrderMapper;
    @Autowired
    private OSubOrderActivityMapper oSubOrderActivityMapper;
    @Autowired
    private OActivityMapper oActivityMapper;
    @Autowired
    private IUserService iUserService;
    @Autowired
    private IResourceService iResourceService;
    @Autowired
    private DictOptionsService dictOptionsService;


    @Override
    public PageInfo queryPlannerList(OReceiptOrder receiptOrder, OReceiptPro receiptPro, Page page,Map map) {
        Map<String, Object> reqMap = new HashMap<>();
//        reqMap.put("receiptStatus", OReceiptStatus.WAITING_LIST.code);
        reqMap.put("receiptProStatus", OReceiptStatus.WAITING_LIST.code);

        if (StringUtils.isNotBlank(receiptOrder.getOrderId())) {
            reqMap.put("orderId", receiptOrder.getOrderId());
        }
        if (StringUtils.isNotBlank(receiptOrder.getReceiptNum())) {
            reqMap.put("receiptNum", receiptOrder.getReceiptNum());
        }
        if (StringUtils.isNotBlank(receiptOrder.getAddrRealname())) {
            reqMap.put("addrRealname", receiptOrder.getAddrRealname());
        }
        //同活动去掉
        //        if (null!=map.get("ACT_CODE") && StringUtils.isNotBlank(map.get("ACT_CODE")+"")){
        //            reqMap.put("actCode", map.get("ACT_CODE"));
        //        }
                //同商品去掉
        //        if (null!=map.get("PRO_ID") && StringUtils.isNotBlank(map.get("PRO_ID")+"")){
        //            reqMap.put("proId", map.get("PRO_ID"));
        //        }
        //增加增加厂家和机型
        if (null!=map.get("ag") && StringUtils.isNotBlank(map.get("ag")+"")){
            reqMap.put("ag", map.get("ag"));
        }
        if (null!=map.get("VENDER") && StringUtils.isNotBlank(map.get("VENDER")+"")){
            reqMap.put("vender", map.get("VENDER"));
        }
        if (null!=map.get("PRO_MODEL") && StringUtils.isNotBlank(map.get("PRO_MODEL")+"")){
            reqMap.put("proModel", map.get("PRO_MODEL"));
        }
        //增加机具类型
        if (null!=map.get("PRO_TYPE") && StringUtils.isNotBlank(map.get("PRO_TYPE")+"")){
            reqMap.put("proType", map.get("PRO_TYPE"));
        }
        if (null!=map.get("par_agentName") && StringUtils.isNotBlank(map.get("par_agentName")+"")){
            reqMap.put("agentName", "%"+map.get("par_agentName")+"%");
        }
        if (null!=map.get("par_proName") && StringUtils.isNotBlank(map.get("par_proName")+"")){
            reqMap.put("proName", "%"+map.get("par_proName")+"%");
        }
        if (null!=map.get("oInuretime") && StringUtils.isNotBlank(map.get("oInuretime")+"")){
            reqMap.put("oInuretime", map.get("oInuretime"));
        }
        if (null!=map.get("par_avtivityName") && StringUtils.isNotBlank(map.get("par_avtivityName")+"")){
            reqMap.put("avtivityName", "%"+map.get("par_avtivityName")+"%");
        }
        reqMap.put("agStatus", AgStatus.Approved.name());
        reqMap.put("cIncomStatus", AgentInStatus.NO.status);
        reqMap.put("cloReviewStatus", AgStatus.Approved.status);
        List<Dict> dictList = dictOptionsService.dictList(DictGroup.ORDER.name(), DictGroup.APPROVAL_OPINION.name());
        if (dictList.size()>0 && dictList!=null) {
            reqMap.put("approvalOpinion", dictList);
        }
        List<Map<String, Object>> plannerList = receiptOrderMapper.queryPlannerAll(reqMap, page);
        //退货子订单编号
        if(plannerList.size()>0 && null!=map.get("O_RETURN_ORDER_DETAIL_ID")){
            for (Map<String, Object> stringObjectMap : plannerList) {
                stringObjectMap.put("O_RETURN_ORDER_DETAIL_ID",map.get("O_RETURN_ORDER_DETAIL_ID"));
                //回填退货数量
                stringObjectMap.put("planProNum",map.get("RETURN_COUNT"));
            }
        }
        PageInfo pageInfo = new PageInfo();
        pageInfo.setRows(plannerList);
        pageInfo.setTotal(receiptOrderMapper.queryPlannerCount(reqMap));
        return pageInfo;
    }


    /**
     * 分配排单
     *
     * @param receiptPlan
     * @param receiptProId
     * @return
     * @throws Exception
     */
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
    @Override
    public AgentResult savePlanner(ReceiptPlan receiptPlan, String receiptProId,String activityId) throws Exception {
        AgentResult result = new AgentResult(500, "系统异常", "");
            OReceiptPro oReceiptPro = receiptProMapper.selectByPrimaryKey(receiptProId);
            if (oReceiptPro == null) {
                throw new MessageException("收货单商品未找到!");
            }
            if(receiptPlan.getPlanProNum().compareTo(oReceiptPro.getProNum().subtract(oReceiptPro.getSendNum()))>0){
                throw new MessageException("此条配货信息已变更  请点击查询按钮以获取数据!!");
            }
            String planId = idService.genId(TabId.o_receipt_plan);
            receiptPlan.setId(planId);
            receiptPlan.setPlanNum(planId);
            Date nowDate = new Date();
            receiptPlan.setcDate(nowDate);
            receiptPlan.setStatus(Status.STATUS_1.status);
            receiptPlan.setVersion(Status.STATUS_1.status);
            receiptPlan.setPlanOrderStatus(new BigDecimal(PlannerStatus.YesPlanner.getValue()));
            receiptPlan.setActivityId(activityId);
            //采购单商品
            OSubOrderExample example = new OSubOrderExample();
            example.or().andOrderIdEqualTo(oReceiptPro.getOrderid())
                    .andProIdEqualTo(oReceiptPro.getProId())
                    .andStatusEqualTo(Status.STATUS_1.status);
            List<OSubOrder>  oSubOrders = oSubOrderMapper.selectByExample(example);
            if(oSubOrders.size()==0){
                throw new MessageException("订购商品未找到!");
            }
            OSubOrder  oSubOrderItem = oSubOrders.get(0);

            //子订单 根据活动id 找到活动代码，根据活动代码 厂家和型号
            OSubOrderActivityExample oSubOrderActivity = new OSubOrderActivityExample();
            oSubOrderActivity.or().andStatusEqualTo(Status.STATUS_1.status).andSubOrderIdEqualTo(oSubOrderItem.getId());
            List<OSubOrderActivity> oSubOrderActivities =oSubOrderActivityMapper.selectByExample(oSubOrderActivity);
            if(oSubOrderActivities.size()==0){
                throw new MessageException("订购商品活动未找到!");
            }
            //新订单活动
            OSubOrderActivity OSubOrderActivityItem = oSubOrderActivities.get(0);
            //排单活动
            OActivity sure_activity = oActivityMapper.selectByPrimaryKey(activityId);
            //cxinfo  保存排单 确定具体活动 价格计算采用活动中的价格 xx

            //确定活动
            //            OActivity real_activity = sure_activity;
            //            OSubOrderActivityItem.setActivityId(real_activity.getId());
            //            OSubOrderActivityItem.setActivityName(real_activity.getActivityName());
            //            OSubOrderActivityItem.setActivityWay(real_activity.getActivityWay());
            //            OSubOrderActivityItem.setActivityRule(real_activity.getActivityRule());
            //            OSubOrderActivityItem.setVender(real_activity.getVender());
            //            OSubOrderActivityItem.setProModel(real_activity.getProModel());
            //            OSubOrderActivityItem.setBusProCode(real_activity.getBusProCode());
            //            OSubOrderActivityItem.setBusProName(real_activity.getBusProName());
            //            OSubOrderActivityItem.setTermBatchcode(real_activity.getTermBatchcode());
            //            OSubOrderActivityItem.setTermBatchname(real_activity.getTermBatchname());
            //            OSubOrderActivityItem.setTermtype(real_activity.getTermtype());
            //            OSubOrderActivityItem.setTermtypename(real_activity.getTermtypename());
            //            OSubOrderActivityItem.setOriginalPrice(real_activity.getOriginalPrice());
            //            OSubOrderActivityItem.setPrice(real_activity.getPrice());
            //            OSubOrderActivityItem.setPosType(real_activity.getPosType());
            //            OSubOrderActivityItem.setPosSpePrice(real_activity.getPosSpePrice());
            //            OSubOrderActivityItem.setStandTime(real_activity.getStandTime());
            //            OSubOrderActivityItem.setBackType(real_activity.getBackType());
            //            OSubOrderActivityItem.setStandAmt(real_activity.getStandAmt());
            //            if(1!=oSubOrderActivityMapper.updateByPrimaryKeySelective(OSubOrderActivityItem)){
            //                throw new MessageException("更新活动失败!");
            //            }


            receiptPlan.setProType(oSubOrderItem.getProType());
            int receiptInsert = receiptPlanMapper.insert(receiptPlan);
            if (receiptInsert != 1 ) {
                log.info("保存排单异常");
                throw new MessageException("保存排单异常!");
            }
            OReceiptPro receiptPro = new OReceiptPro();
            receiptPro.setId(receiptProId);
            receiptPro.setSendNum(oReceiptPro.getSendNum().add(receiptPlan.getPlanProNum()));
            if (receiptPro.getSendNum().equals(oReceiptPro.getProNum())) {
                receiptPro.setReceiptProStatus(OReceiptStatus.DISPATCHED_ORDER.code);
            }
            int receiptProUpdate = receiptProMapper.updateByPrimaryKeySelective(receiptPro);
            if ( receiptProUpdate != 1) {
                log.info("保存排单异常");
                throw new MessageException("保存排单异常!");
            }
            //没有待排单的商品更新收货单状态
            OReceiptProExample oReceiptProExample = new OReceiptProExample();
            OReceiptProExample.Criteria criteria = oReceiptProExample.createCriteria();
            criteria.andReceiptIdEqualTo(receiptPlan.getReceiptId());
            criteria.andReceiptProStatusEqualTo(OReceiptStatus.WAITING_LIST.code);
            List<OReceiptPro> oReceiptPros = receiptProMapper.selectByExample(oReceiptProExample);
            if (oReceiptPros.size() == 0) {
                OReceiptOrder receiptOrder = new OReceiptOrder();
                receiptOrder.setId(receiptPlan.getReceiptId());
                receiptOrder.setReceiptStatus(OReceiptStatus.DISPATCHED_ORDER.code);
                int receiptOrdUpdate = receiptOrderMapper.updateByPrimaryKeySelective(receiptOrder);
                if (receiptOrdUpdate != 1) {
                    throw new MessageException("保存排单异常!");
                }
            }
            result.setStatus(200);
            result.setMsg("处理成功");
        return result;
    }


    /**
     * @Author: Zhang Lei
     * @Description: 查询订单/退货单的排单详细情况
     * @Date: 15:58 2018/7/27
     */
    public List<Map<String, Object>> queryOrderReceiptPlanInfo(Map<String, String> params) throws ProcessException {
        List<Map<String, Object>> list = receiptPlanMapper.queryOrderReceiptPlanInfo(params);
        return list;
    }



    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
    @Override
    public AgentResult batchPlanner(List<ReceiptPlan> receiptPlanList,String userId) throws Exception {
        AgentResult result = new AgentResult(500, "系统异常", "");
        int i = 0;
        for (ReceiptPlan receiptPlan : receiptPlanList) {
            i++;
            try {
                receiptPlan.setUserId(userId);
                receiptPlan.setcUser(userId);
                result = savePlanner(receiptPlan, receiptPlan.getProId(),receiptPlan.getActivityId());
            } catch (MessageException e) {
                result.setMsg("第"+i+"条"+e.getMsg());
                throw new MessageException("第"+i+"条"+e.getMsg()+",请核对发货数量");
            }
        }
        return result;
    }


    @Override
    public List<ReceiptOrderVo> exportPlanner(Map map) {
        if(null != map.get("userId")) {
            Long userId = (Long) map.get("userId");
            List<Map<String, Object>> orgCodeRes = iUserService.orgCode(userId);
            if (orgCodeRes == null && orgCodeRes.size() != 1) {
                return null;
            }
            Map<String, Object> stringObjectMap = orgCodeRes.get(0);
            String organizationCode = String.valueOf(stringObjectMap.get("ORGANIZATIONCODE"));
            map.put("organizationCode", organizationCode);
            List<Map> platfromPerm = iResourceService.userHasPlatfromPerm(userId);
            map.put("platfromPerm", platfromPerm);
        }
        if (null!=map.get("orderId") && StringUtils.isNotBlank(map.get("orderId")+"")){
            map.put("orderId", map.get("orderId"));
        }
        if (null!=map.get("oInuretime") && StringUtils.isNotBlank(map.get("oInuretime")+"")){
            map.put("oInuretime", map.get("oInuretime"));
        }
        if (null!=map.get("receiptNum") && StringUtils.isNotBlank(map.get("receiptNum")+"")){
            map.put("receiptNum", map.get("receiptNum"));
        }
        if (null!=map.get("addrRealname") && StringUtils.isNotBlank(map.get("addrRealname")+"")){
            map.put("addrRealname", map.get("addrRealname"));
        }
        if (null!=map.get("agentName") && StringUtils.isNotBlank(map.get("agentName")+"")) {
            map.put("agentName", "%"+map.get("agentName")+"%");
        }
        if (null!=map.get("proName") && StringUtils.isNotBlank(map.get("proName")+"")) {
            map.put("proName", "%"+map.get("proName")+"%");
        }
        if (null!=map.get("avtivityName") && StringUtils.isNotBlank(map.get("avtivityName")+"")) {
            map.put("avtivityName", "%"+map.get("avtivityName")+"%");
        }

        List<Dict> dictList = dictOptionsService.dictList(DictGroup.ORDER.name(), DictGroup.APPROVAL_OPINION.name());
        if (dictList.size()>0 && dictList!=null) {
            map.put("approvalOpinion", dictList);
        }
        List<ReceiptOrderVo> receiptOrderVoList = receiptOrderMapper.exportPlanner(map);
        if (receiptOrderVoList.size()>0 && receiptOrderVoList!=null) {
            for (ReceiptOrderVo receiptOrderVo : receiptOrderVoList) {
                Dict dict = dictOptionsService.findDictByValue(DictGroup.ORDER.name(), DictGroup.MANUFACTURER.name(), receiptOrderVo.getProCom());
                if (dict != null) {
                    receiptOrderVo.setProCom(
                            dict.getdItemname()+"/"+receiptOrderVo.getModel()+"/"+receiptOrderVo.getBusProName()+"/"+receiptOrderVo.getBackType()+"/"+receiptOrderVo.getStandTime()+"/"+receiptOrderVo.getStandAmt()
                    );
                } else {
                    receiptOrderVo.setProCom(
                            "系统未配置该厂商信息"+"/"+receiptOrderVo.getModel()+"/"+receiptOrderVo.getBusProName()+"/"+receiptOrderVo.getBackType()+"/"+receiptOrderVo.getStandTime()+"/"+receiptOrderVo.getStandAmt()
                    );
                }
                receiptOrderVo.setPlanProNum(null);
            }
        }

        return receiptOrderVoList;
    }

}
