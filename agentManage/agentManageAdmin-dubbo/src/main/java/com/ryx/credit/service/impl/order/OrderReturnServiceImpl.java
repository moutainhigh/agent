package com.ryx.credit.service.impl.order;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.enumc.*;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.*;
import com.ryx.credit.dao.agent.AttachmentRelMapper;
import com.ryx.credit.dao.agent.BusActRelMapper;
import com.ryx.credit.dao.order.ODeductCapitalMapper;
import com.ryx.credit.dao.order.OReturnOrderDetailMapper;
import com.ryx.credit.dao.order.OReturnOrderMapper;
import com.ryx.credit.dao.order.OReturnOrderRelMapper;
import com.ryx.credit.pojo.admin.agent.AttachmentRel;
import com.ryx.credit.pojo.admin.agent.BusActRel;
import com.ryx.credit.pojo.admin.agent.BusActRelExample;
import com.ryx.credit.pojo.admin.agent.Dict;
import com.ryx.credit.pojo.admin.order.*;
import com.ryx.credit.pojo.admin.vo.AgentVo;
import com.ryx.credit.service.ActivityService;
import com.ryx.credit.service.agent.AgentEnterService;
import com.ryx.credit.service.dict.DictOptionsService;
import com.ryx.credit.service.dict.IdService;
import com.ryx.credit.service.order.IOrderReturnService;
import com.ryx.credit.service.order.OLogisticsService;
import com.ryx.credit.service.order.PlannerService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.pojo.admin.order.OReturnOrder;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * @Author: Zhang Lei
 * @Description: 退货
 * @Date: 14:23 2018/7/25
 */
@Service("orderReturnService")
public class OrderReturnServiceImpl implements IOrderReturnService {

    private static Logger log = LoggerFactory.getLogger(OrderReturnServiceImpl.class);

    @Autowired
    OReturnOrderMapper returnOrderMapper;
    @Resource
    IdService idService;
    @Autowired
    OReturnOrderDetailMapper returnOrderDetailMapper;
    @Autowired
    OReturnOrderRelMapper returnOrderRelMapper;
    @Autowired
    ODeductCapitalMapper deductCapitalMapper;
    @Resource
    PlannerService plannerService;
    @Autowired
    private BusActRelMapper busActRelMapper;
    @Autowired
    private AgentEnterService agentEnterService;
    @Autowired
    private DictOptionsService dictOptionsService;
    @Autowired
    private ActivityService activityService;
    @Autowired
    private AttachmentRelMapper attachmentRelMapper;
    @Resource
    OLogisticsService oLogisticsService;


    /**
     * @Author: Zhang Lei
     * @Description: 退货列表查询
     * @Date: 14:24 2018/7/25
     */
    @Override
    public PageInfo orderList(OReturnOrder returnOrder, PageInfo page) {
        OReturnOrderExample example = new OReturnOrderExample();
        OReturnOrderExample.Criteria c = example.or();
        if (returnOrder != null) {
            if (StringUtils.isNotEmpty(returnOrder.getId())) {
                c.andIdEqualTo(returnOrder.getId());
            }
            if (StringUtils.isNotEmpty(returnOrder.getAgentId())) {
                c.andAgentIdEqualTo(returnOrder.getAgentId());
            }
        }
        example.setPage(new Page(page.getFrom(), page.getPagesize()));
        long count = returnOrderMapper.countByExample(example);
        example.setOrderByClause(" u_time desc ");
        List<OReturnOrder> list = returnOrderMapper.selectByExample(example);
        page.setRows(list);
        page.setTotal(Integer.parseInt(count + ""));
        return page;
    }


    /**
     * @Author: Zhang Lei
     * @Description: 查询退货详情
     * @Date: 15:27 2018/7/27
     */
    public Map<String, Object> view(String returnId) throws ProcessException {
        Map<String, Object> map = new HashMap<>();

        //查询退货单
        OReturnOrder returnOrder = returnOrderMapper.selectByPrimaryKey(returnId);
        if (returnOrder == null) {
            throw new ProcessException("退货单不存在");
        }

        //查询退货明细
        OReturnOrderDetailExample example = new OReturnOrderDetailExample();
        example.or().andReturnIdEqualTo(returnId);
        List<OReturnOrderDetail> returnDetails = returnOrderDetailMapper.selectByExample(example);

        //查询扣款款项
        ODeductCapitalExample deductCapitalExample = new ODeductCapitalExample();
        deductCapitalExample.or().andSourceIdEqualTo(returnId);
        List<ODeductCapital> deductCapitals = deductCapitalMapper.selectByExample(deductCapitalExample);

        //查询已排单列表
        Map<String, String> params = new HashMap<String, String>();
        params.put("returnId", returnId);
        List<Map<String, Object>> receiptPlans = plannerService.queryOrderReceiptPlanInfo(params);

        map.put("returnOrder", returnOrder);
        map.put("returnDetails", returnDetails);
        map.put("deductCapitals", deductCapitals);
        map.put("receiptPlans", receiptPlans);

        return map;
    }

    /**
     * @Author: Zhang Lei
     * @Description: 保存扣款款项
     * @Date: 20:25 2018/7/27
     */
    @Override
    @Transactional
    public Map<String, Object> saveCut(String returnId, String amt, String ctype) {

        Map<String, Object> map = new HashMap<>();

        OReturnOrder returnOrder = returnOrderMapper.selectByPrimaryKey(returnId);

        ODeductCapital deductCapital = new ODeductCapital();
        deductCapital.setId(idService.genId(TabId.o_deduct_capital));
        deductCapital.setcAmount(new BigDecimal(amt));
        deductCapital.setcType(ctype);
        deductCapital.setcAgentId(returnOrder.getAgentId());
        deductCapital.setSourceId(returnId);
        deductCapital.setcTime(new Date());
        deductCapitalMapper.insertSelective(deductCapital);

        returnOrder.setCutAmo(returnOrder.getCutAmo().add(new BigDecimal(amt)));
        returnOrder.setReturnAmo(returnOrder.getReturnAmo().subtract(new BigDecimal(amt)));
        returnOrder.setuTime(new Date());
        returnOrderMapper.updateByPrimaryKeySelective(returnOrder);

        map.put("goodsReturnAmo", returnOrder.getGoodsReturnAmo());
        map.put("returnAmo", returnOrder.getReturnAmo());
        map.put("cutAmo", returnOrder.getCutAmo());

        return map;
    }


    /**
     * @Author: Zhang Lei
     * @Description: 业务审批
     * @Date: 10:20 2018/7/30
     */
    @Override
    @Transactional
    public Map<String, Object> bizAudit(String returnId, String plans, String remark, String userid, String auditResult) {

        if (auditResult.equals("no")) {

            return null;
        }

        JSONArray jsonArray = JSONObject.parseArray(plans);
        for (Object obj : jsonArray) {
            JSONObject jsonObject = (JSONObject) obj;
            ReceiptPlan receiptPlan = new ReceiptPlan();
            receiptPlan.setProId(jsonObject.getString("receiptProId"));
            receiptPlan.setcUser(userid);
            receiptPlan.setUserId(userid);
            receiptPlan.setOrderId(jsonObject.getString("orderId"));
            receiptPlan.setReceiptId(jsonObject.getString("receiptId"));
            receiptPlan.setProCom(jsonObject.getString("proCom"));
            receiptPlan.setModel(jsonObject.getString("model"));
            receiptPlan.setPlanProNum(jsonObject.getBigDecimal("planProNum"));
            String receiptProId = jsonObject.getString("receiptProId");
            try {
                plannerService.savePlanner(receiptPlan, receiptProId);
            } catch (Exception e) {
                throw new ProcessException("保存排单信息失败");
            }
        }

        return null;
    }

    /**
     * @Author: Zhang Lei
     * @Description: 财务审核
     * @Date: 9:08 2018/7/31
     */
    @Override
    public Map<String, Object> cwAudit(String returnId, String remark, String userid, String auditResult, String[] attachments) throws ProcessException {

        //审核拒绝
        if (auditResult.equals("no")) {
            return null;
        }

        //审核通过
        //保存附件
        for (String attach : attachments) {
            AttachmentRel attachmentRel = new AttachmentRel();
            attachmentRel.setId(idService.genId(TabId.a_attachment_rel));
            attachmentRel.setSrcId(returnId);
            attachmentRel.setAttId(attach);
            attachmentRel.setBusType(AttachmentRelType.Return.name());
            attachmentRel.setcTime(new Date());
            attachmentRel.setcUser(userid);
            attachmentRel.setStatus(Status.STATUS_1.status);
        }

        return null;
    }

    /**
     * @Author: Zhang Lei
     * @Description: 退貨申請
     * @Date: 20:43 2018/7/26
     */
    @Override
    @Transactional
    public Map<String, Object> apply(String agentId, OReturnOrder returnOrder, String productsJson, String userid) throws ProcessException {

        List<Map> list = null;
        try {
            list = JSONObject.parseArray(productsJson, Map.class);
        } catch (Exception e) {
            log.error("解析退货商品失败", e);
            throw new ProcessException("解析退货商品失败");
        }

        String returnId = null;

        try {
            //生成退货单
            returnId = idService.genId(TabId.o_return_order);
            returnOrder.setId(returnId);
            returnOrder.setRetSchedule(new BigDecimal(RetSchedule.SPZ.code));
            returnOrder.setcTime(new Date());
            returnOrder.setcUser(userid);
            returnOrder.setAgentId(agentId);
            returnOrder.setCutAmo(BigDecimal.ZERO);
            returnOrder.setRelReturnAmo(BigDecimal.ZERO);
            returnOrderMapper.insertSelective(returnOrder);
        } catch (Exception e) {
            log.error("生成退货单失败", e);
            throw new ProcessException("生成退货单失败");
        }

        //退货单和订单关系
        Set<String> relSet = new HashSet<>();

        for (Map<String, Object> map : list) {

            String orderId = (String) map.get("orderId");
            String startSn = (String) map.get("startSn");
            String endSn = (String) map.get("endSn");

            //生成退货明细
            OReturnOrderDetail returnOrderDetail = null;
            try {
                returnOrderDetail = new OReturnOrderDetail();
                returnOrderDetail.setId(idService.genId(TabId.o_return_order_detail));
                returnOrderDetail.setReturnId(returnId);
                returnOrderDetail.setAgentId(agentId);
                returnOrderDetail.setOrderId(orderId);
                returnOrderDetail.setSubOrderId((String) map.get("receiptId"));
                returnOrderDetail.setProId((String) map.get("proId"));
                returnOrderDetail.setProName((String) map.get("proName"));
                returnOrderDetail.setProType((String) map.get("proType"));
                returnOrderDetail.setProCom((String) map.get("proCom"));
                returnOrderDetail.setProPrice(MapUtil.getBigDecimal(map, "proPrice"));
                returnOrderDetail.setModel((String) map.get("proModel"));
                returnOrderDetail.setBeginSn((String) map.get("startSn"));
                returnOrderDetail.setEndSn((String) map.get("endSn"));
                returnOrderDetail.setOrderPrice(MapUtil.getBigDecimal(map, "proPrice"));
                returnOrderDetail.setReturnCount(MapUtil.getBigDecimal(map, "count"));
                returnOrderDetail.setReturnAmt(MapUtil.getBigDecimal(map, "totalPrice"));
                returnOrderDetail.setReturnTime(new Date());
                returnOrderDetail.setcTime(new Date());
                returnOrderDetail.setcUser(agentId);
                returnOrderDetailMapper.insertSelective(returnOrderDetail);
            } catch (Exception e) {
                log.error("生成退货明细失败", e);
                throw new ProcessException("生成退货明细失败,SN[" + (String) map.get("startSn") + "--" + (String) map.get("endSn") + "]");
            }

            //更新物流明细表SN状态
            try {
                oLogisticsService.updateSnStatus(orderId,startSn,endSn,SnStatus.THZ.code);
            } catch (Exception e) {
                log.error("更新SN状态失败", e);
                throw new ProcessException("更新SN状态失败,SN[" + (String) map.get("startSn") + "--" + (String) map.get("endSn") + "]");
            }


            String setstr = returnOrderDetail.getOrderId() + "_" + returnOrderDetail.getSubOrderId() + "_" + returnOrderDetail.getReturnId();
            relSet.add(setstr);
        }

        //生成退货和订单关系
        for (String realId : relSet) {
            try {
                String[] ids = realId.split("_");
                OReturnOrderRel returnOrderRel = new OReturnOrderRel();
                returnOrderRel.setId(idService.genId(TabId.o_return_order_rel));
                returnOrderRel.setOrderId(ids[0]);
                returnOrderRel.setSubOrderId(ids[1]);
                returnOrderRel.setReturnOrderId(ids[2]);
                returnOrderRel.setcTime(new Date());
                returnOrderRel.setcUser(agentId);
                returnOrderRelMapper.insertSelective(returnOrderRel);
            } catch (Exception e) {
                log.error("生成退货关系失败", e);
                throw new ProcessException("生成退货关系失败");
            }
        }


        //启动退货审批流程==========================================================

        //检查是否有审批中的代理商新
        BusActRelExample example = new BusActRelExample();
        example.or().andBusIdEqualTo(returnId)
                .andBusTypeEqualTo(BusActRelBusType.refund.name())
                .andActivStatusEqualTo(AgStatus.Approving.name())
                .andStatusEqualTo(Status.STATUS_1.status);
        if (busActRelMapper.selectByExample(example).size() > 0) {
            log.info("退货提交审批失败,禁止重复提交审批{}:{}", returnId, agentId);
            throw new ProcessException("退货提交审批失败，禁止重复提交审批");
        }

        //更新退货单状态
        returnOrder.setRetSchedule(new BigDecimal(RetSchedule.SPZ.code));
        returnOrder.setuTime(new Date());
        if (1 != returnOrderMapper.updateByPrimaryKeySelective(returnOrder)) {
            log.info("退货提交审批失败,禁止重复提交审批{}:{}", returnId, agentId);
            throw new ProcessException("退货提交审批失败，更新退货单失败");
        }
        Map startPar = agentEnterService.startPar(userid);
        if (null == startPar) {
            log.info("========用户{}{}启动部门参数为空", returnId, agentId);
            throw new ProcessException("启动部门参数为空!");
        }

        //不同的业务类型找到不同的启动流程
        List<Dict> actlist = dictOptionsService.dictList(DictGroup.ORDER.name(), DictGroup.ACT_ORDER_RETURN.name());
        String workId = null;
        for (Dict dict : actlist) {
            workId = dict.getdItemvalue();
        }
        //启动审批
        String proce = activityService.createDeloyFlow(null, workId, null, null, startPar);
        if (proce == null) {
            log.info("退货提交审批，审批流启动失败{}:{}", returnId, agentId);
            throw new ProcessException("退货审批流启动失败!");
        }

        //添加审批关系
        BusActRel record = new BusActRel();
        record.setBusId(returnId);
        record.setActivId(proce);
        record.setcTime(Calendar.getInstance().getTime());
        record.setcUser(userid);
        record.setStatus(Status.STATUS_1.status);
        record.setBusType(BusActRelBusType.refund.name());
        record.setActivStatus(AgStatus.Approving.name());
        if (1 != busActRelMapper.insertSelective(record)) {
            log.info("退货提交审批，启动审批异常，添加审批关系失败{}:{}", returnId, proce);
            throw new ProcessException("退货审批流启动失败:添加审批关系失败");
        }

        return null;
    }


    /**
     * 审批订单任务
     *
     * @param agentVo
     * @param userId
     * @return
     * @throws Exception
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
    @Override
    public AgentResult approvalTask(AgentVo agentVo, String userId) throws ProcessException {
        try {
            //处理审批数据
            log.info("订单提交审批，完成任务{}:{}：{}", agentVo.getTaskId(), userId, JSONObject.toJSONString(agentVo));

            //业务处理
            String sid = agentVo.getSid();

            //业务审批时添加排单
            if (sid.equals("sid-C911F512-9E63-44CC-9E6E-763484FA0E5B")) {
                AgentResult agentResult = savePlans(agentVo, userId);
                if (!agentResult.isOK()) {
                    return AgentResult.fail(agentResult.getMsg());
                }
            }

            //财务审批时上传打款凭证
            if (sid.equals("sid-4528CEA4-998C-4854-827B-1842BBA5DB4B")) {
                AgentResult agentResult = saveAttachments(agentVo, userId);
                if (!agentResult.isOK()) {
                    return AgentResult.fail(agentResult.getMsg());
                }
            }

            //完成任务
            AgentResult result = new AgentResult(500, "系统异常", "");
            Map<String, Object> reqMap = new HashMap<>();
            reqMap.put("rs", agentVo.getApprovalResult());
            reqMap.put("approvalOpinion", agentVo.getApprovalOpinion());
            reqMap.put("approvalPerson", userId);
            reqMap.put("createTime", DateUtils.dateToStringss(new Date()));
            reqMap.put("taskId", agentVo.getTaskId());
            //下一个节点参数
            if (org.apache.commons.lang.StringUtils.isNotEmpty(agentVo.getOrderAprDept()))
                reqMap.put("dept", agentVo.getOrderAprDept());

            //传递部门信息
            Map startPar = agentEnterService.startPar(userId);
            if (null != startPar) {
                reqMap.put("party", startPar.get("party"));
            }
            //完成任务
            Map resultMap = activityService.completeTask(agentVo.getTaskId(), reqMap);
            if (resultMap == null) {
                throw new ProcessException("catch工作流处理任务异常!");
            }
            Boolean rs = (Boolean) resultMap.get("rs");
            String msg = String.valueOf(resultMap.get("msg"));
            if (!rs) {
                throw new ProcessException("catch工作流处理任务异常!");
            }
            return AgentResult.ok(null);
        } catch (ProcessException e) {
            e.printStackTrace();
            throw new ProcessException("catch工作流处理任务异常!");
        }
    }


    /**
     * @Author: Zhang Lei
     * @Description: 保存排单
     * @Date: 21:31 2018/8/2
     */
    public AgentResult savePlans(AgentVo agentVo, String userid) {
        try {
            JSONArray jsonArray = JSONObject.parseArray(agentVo.getPlans());
            for (Object obj : jsonArray) {
                JSONObject jsonObject = (JSONObject) obj;
                ReceiptPlan receiptPlan = new ReceiptPlan();
                receiptPlan.setProId(jsonObject.getString("receiptProId"));
                receiptPlan.setcUser(userid);
                receiptPlan.setUserId(userid);
                receiptPlan.setOrderId(jsonObject.getString("orderId"));
                receiptPlan.setReceiptId(jsonObject.getString("receiptId"));
                receiptPlan.setProCom(jsonObject.getString("proCom"));
                receiptPlan.setModel(jsonObject.getString("model"));
                receiptPlan.setPlanProNum(jsonObject.getBigDecimal("planProNum"));
                receiptPlan.setReturnOrderDetailId(agentVo.getReturnId());
                String receiptProId = jsonObject.getString("receiptProId");
                plannerService.savePlanner(receiptPlan, receiptProId);
            }
        } catch (Exception e) {
            log.error("保存退货排单失败", e);
            return AgentResult.fail("保存退货排单失败");
        }

        return AgentResult.ok();
    }


    /**
     * @Author: Zhang Lei
     * @Description: 保存打款截图
     * @Date: 21:31 2018/8/2
     */
    public AgentResult saveAttachments(AgentVo agentVo, String userid) {
        try {
            String[] attachments = agentVo.getAttachments();
            String returnId = agentVo.getReturnId();
            for (String attach : attachments) {
                AttachmentRel attachmentRel = new AttachmentRel();
                attachmentRel.setId(idService.genId(TabId.a_attachment_rel));
                attachmentRel.setSrcId(returnId);
                attachmentRel.setAttId(attach);
                attachmentRel.setBusType(AttachmentRelType.Return.name());
                attachmentRel.setcTime(new Date());
                attachmentRel.setcUser(userid);
                attachmentRel.setStatus(Status.STATUS_1.status);
                attachmentRelMapper.insertSelective(attachmentRel);
            }
        } catch (Exception e) {
            log.error("保存退货打款凭证失败", e);
            return AgentResult.fail("保存退货打款凭证失败");
        }

        return AgentResult.ok();
    }


    /**
     * @Author: Zhang Lei
     * @Description: 执行扣款计划后更新退货单和退货明细
     * @Date: 19:13 2018/8/3
     */
    public void doPlan(String returnId, BigDecimal takeAmt,String userid) {
        try {

            OReturnOrder oReturnOrder = returnOrderMapper.selectByPrimaryKey(returnId);
            BigDecimal returnAmo = oReturnOrder.getReturnAmo();
            returnAmo = returnAmo.subtract(takeAmt);
            oReturnOrder.setReturnAmo(returnAmo);

            if(returnAmo.compareTo(BigDecimal.ZERO)>0){
                oReturnOrder.setRetSchedule(new BigDecimal(RetSchedule.TKZ.code));
            }else {
                oReturnOrder.setRetSchedule(new BigDecimal(RetSchedule.WC.code));
            }

            oReturnOrder.setRetTime(new Date());
            oReturnOrder.setuUser(userid);

            returnOrderMapper.updateByPrimaryKeySelective(oReturnOrder);

        } catch (Exception e) {
            log.error("执行扣款时更新退货单失败", e);
            throw e;
        }
    }

}
