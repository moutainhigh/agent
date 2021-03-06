package com.ryx.credit.dao.order;

import com.ryx.credit.pojo.admin.order.OLogistics;
import com.ryx.credit.pojo.admin.order.OLogisticsExample;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

    public interface OLogisticsMapper {
    long countByExample(OLogisticsExample example);

    int deleteByExample(OLogisticsExample example);

    int insert(OLogistics record);

    int insertSelective(OLogistics record);

    List<OLogistics> selectByExample(OLogisticsExample example);

    int updateByPrimaryKeySelective(OLogistics record);

    int updateByPrimaryKey(OLogistics record);

    OLogistics selectByPrimaryKey(String id);

    List<Map<String, Object>> getOLogisticsList(Map<String, Object> param);

    Long getOLogisticsCount(Map<String, Object> param);

    Map<String, Object> getOrderAndLogisticsBySn(@Param("SN") String SN, @Param("agentId") String agentId);

    List<Map<String, Object>> getOrderAndLogisticsBySns(@Param("startSn") String startSn, @Param("endSn") String endSn, @Param("agentId") String agentId);

    List<Map<String, Object>> queryLogisticsList(Map<String, Object> param);

    int updateSnStatus(@Param("orderId") String orderId, @Param("startSn") String startSn, @Param("endSn") String endSn, @Param("status") BigDecimal status, @Param("recordStatus") BigDecimal recordStatus,@Param("returnId") String returnId);

    /**
     * 根据物流类型，物流下发状态查询物流编号
     * @param param
     * @return
     */
    List<String> queryLogicInfoIdByStatus(Map<String, Object> param);

    /**
     * 查询退货物流明细
     * @return
     */
    List<String> queryLogicInfoIdByStatusAndApproved(Map<String, Object> param);

    /**
     * 查询退货物流发货数量
     * @return
     */
    int selectSendNumByReturnId(String returnId);
}