package com.ryx.credit.service.impl.order;

import com.ryx.credit.common.enumc.*;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.dao.order.*;
import com.ryx.credit.pojo.admin.agent.Dict;
import com.ryx.credit.pojo.admin.order.*;
import com.ryx.credit.service.dict.DictOptionsService;
import com.ryx.credit.service.dict.IdService;
import com.ryx.credit.service.order.ReceiptPlanService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 排单计划
 * Created by RYX on 2018/7/21.
 */
@Service("receiptPlanService")
public class ReceiptPlanServiceImpl implements ReceiptPlanService {

    private static Logger logger = LoggerFactory.getLogger(ReceiptPlanServiceImpl.class);
    @Autowired
    private ReceiptPlanMapper receiptPlanMapper;
    @Autowired
    private IdService idService;
    @Autowired
    private DictOptionsService dictOptionsService;
    @Autowired
    private OReceiptProMapper oReceiptProMapper;


    @Override
    public AgentResult saveReceiptPlan(ReceiptPlan receiptPlan) {
        AgentResult result = new AgentResult(500, "系统异常", "");
        receiptPlan.setId(idService.genId(TabId.o_receipt_plan));
        Date nowDate = new Date();
        receiptPlan.setcDate(nowDate);
        receiptPlan.setStatus(Status.STATUS_1.status);
        receiptPlan.setVersion(Status.STATUS_1.status);
        int insert = receiptPlanMapper.insert(receiptPlan);
        if (insert == 1) {
            return AgentResult.ok();
        }
        return result;
    }

    /**
     * 获取排单列表
     * @param param
     * @param pageInfo
     * @param isPlan   true 排单  flase 退货排单
     * @return
     */
    @Override
    public PageInfo getReceiptPlanList(Map<String, Object> param, PageInfo pageInfo, Boolean isPlan) {
//        param.put("planOrderStatus", PlannerStatus.YesPlanner.getValue());
        if(param.get("begin")== null && param.get("end")==null && isPlan){
            param.put("isDeliver","yes");//只有导出排单才加此条件，过滤退货
        }
        Long count = receiptPlanMapper.getReceipPlanCount(param);
        List<Map<String, Object>> list = receiptPlanMapper.getReceipPlanList(param);
        for (Map<String, Object> maps : list) {
            Dict dictByValue = dictOptionsService.findDictByValue(DictGroup.ORDER.name(), DictGroup.MODEL_TYPE.name(), String.valueOf(maps.get("PRO_TYPE")));
            if(dictByValue!=null)
            maps.put("PRO_TYPE",dictByValue.getdItemname());
            Dict proCom = dictOptionsService.findDictByValue(DictGroup.ORDER.name(), DictGroup.MANUFACTURER.name(), String.valueOf(maps.get("PRO_COM")));
            if(proCom!=null)
            maps.put("PRO_COM",proCom.getdItemname());
        }
        pageInfo.setTotal(count.intValue());
        pageInfo.setRows(list);
        return pageInfo;
    }

    @Override
    public AgentResult revocationPlanner(String planNum, String orderId, String user) throws Exception {
        AgentResult result = new AgentResult(500, "系统异常", "");
        try {
            if (null == user) {
                return AgentResult.fail("操作用户不能为空");
            }
            ReceiptPlanExample receiptPlanExample = new ReceiptPlanExample();
            receiptPlanExample.or()
                    .andStatusEqualTo(Status.STATUS_1.status)
                    .andPlanOrderStatusEqualTo(new BigDecimal(PlannerStatus.YesPlanner.getValue()))
                    .andPlanNumEqualTo(planNum)
                    .andOrderIdEqualTo(orderId);
            List<ReceiptPlan> receiptPlanList = receiptPlanMapper.selectByExample(receiptPlanExample);
            ReceiptPlan receiptPlan = receiptPlanList.get(0);
            //收货单商品
            OReceiptPro oReceiptPro = oReceiptProMapper.selectByPrimaryKey(receiptPlan.getProId());
            BigDecimal sendNum = BigDecimal.ZERO;
            sendNum = oReceiptPro.getSendNum().subtract(receiptPlan.getPlanProNum());
            oReceiptPro.setSendNum(sendNum);
            oReceiptPro.setReceiptProStatus(OReceiptStatus.WAITING_LIST.code);
            oReceiptPro.setuUser(user);
            oReceiptPro.setuTime(new Date());
            int updateByOReceiptPro = oReceiptProMapper.updateByPrimaryKeySelective(oReceiptPro);
            if (updateByOReceiptPro != 1) {
                logger.info("撤回排单异常-更新收货单商品失败:{}{}", planNum, orderId);
                throw new MessageException("撤回排单异常!");
            }
            //排单数据
            receiptPlan.setStatus(Status.STATUS_0.status);
            receiptPlan.setPlanProNum(BigDecimal.ZERO);
            int updateByReceiptPlan = receiptPlanMapper.updateByPrimaryKeySelective(receiptPlan);
            if (updateByReceiptPlan != 1) {
                logger.info("撤回排单异常-更新排单数据失败:{}{}", planNum, orderId);
                throw new MessageException("撤回排单异常!");
            }
            result.setStatus(200);
            result.setMsg("处理成功");
        } catch (Exception e) {
            e.printStackTrace();
            throw new MessageException("撤回排单异常!");
        }
        return result;
    }

    @Override
    public AgentResult startShipping(String planNum, String orderId, String user) throws Exception {
        AgentResult result = new AgentResult(500, "系统异常", "");
        try {
            if (null == user) {
                return AgentResult.fail("操作用户不能为空");
            }
            ReceiptPlanExample receiptPlanExample = new ReceiptPlanExample();
            receiptPlanExample.or()
                    .andStatusEqualTo(Status.STATUS_1.status)
                    .andPlanOrderStatusEqualTo(new BigDecimal(PlannerStatus.YesPlanner.getValue()))
                    .andPlanNumEqualTo(planNum)
                    .andOrderIdEqualTo(orderId);
            List<ReceiptPlan> receiptPlanList = receiptPlanMapper.selectByExample(receiptPlanExample);
            ReceiptPlan receiptPlan = receiptPlanList.get(0);
            //更新排单状态为发货中
            receiptPlan.setPlanOrderStatus(new BigDecimal(PlannerStatus.InTheDeliver.getValue()));
            int updateByReceiptPlan = receiptPlanMapper.updateByPrimaryKeySelective(receiptPlan);
            if (updateByReceiptPlan != 1) {
                logger.info("开始发货异常-更新排单数据失败:{}{}", planNum, orderId);
                throw new MessageException("开始发货异常!");
            }
            result.setStatus(200);
            result.setMsg("处理成功");
        } catch (Exception e) {
            e.printStackTrace();
            throw new MessageException("开始发货异常!");
        }
        return result;
    }

}
