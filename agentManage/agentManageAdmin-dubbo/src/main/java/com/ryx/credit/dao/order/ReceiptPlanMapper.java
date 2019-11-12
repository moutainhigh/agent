package com.ryx.credit.dao.order;

import com.ryx.credit.pojo.admin.order.ReceiptPlan;
import com.ryx.credit.pojo.admin.order.ReceiptPlanExample;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface ReceiptPlanMapper {
    long countByExample(ReceiptPlanExample example);

    int deleteByExample(ReceiptPlanExample example);

    int insert(ReceiptPlan record);

    int insertSelective(ReceiptPlan record);

    List<ReceiptPlan> selectByExample(ReceiptPlanExample example);

    ReceiptPlan selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ReceiptPlan record);

    int updateByPrimaryKey(ReceiptPlan record);

    List<Map<String,Object>> getReceipPlanList(Map <String, Object> param);

    Long getReceipPlanCount(Map <String, Object> param);

    List<Map<String,Object>> queryOrderReceiptPlanInfo(@Param("params") Map<String,String> params);

    List<Map<String, Object>> queryReceiveOrderLogistics(@Param("params") Map<String, String> params);

    List<Map<String,Object>> queryReveiveAgentData(Map <String, Object> param);

    BigDecimal planCountTotal(@Param("orderId") String orderId);
}