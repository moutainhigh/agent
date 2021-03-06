package com.ryx.credit.dao.order;

import com.ryx.credit.common.util.Page;
import com.ryx.credit.pojo.admin.order.OReturnOrder;
import com.ryx.credit.pojo.admin.order.OReturnOrderExample;
import com.ryx.credit.pojo.admin.vo.ReturnOrderVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface OReturnOrderMapper {
    long countByExample(OReturnOrderExample example);

    int deleteByExample(OReturnOrderExample example);

    int insert(OReturnOrder record);

    int insertSelective(OReturnOrder record);

    List<OReturnOrder> selectByExample(OReturnOrderExample example);

    OReturnOrder selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(OReturnOrder record);

    int updateByPrimaryKey(OReturnOrder record);

    Long getOrderReturnCount(Map<String, Object> param);

    List<Map<String,Object>> getOrderReturnList(Map<String, Object> param);

    Map selectByReturnDeId(String returnDetailsId);

    List<ReturnOrderVo> exportRetForDetail(Map map);

    List<Map<String, Object>> queryReceiveOrderActivity(@Param("params") Map<String, String> params);

    List<Map<String, Object>> queryOrderReturnProvinceList(@Param("map") Map<String, Object> map, @Param("page") Page page);

    int queryOrderReturnProvinceCount(@Param("map") Map<String, Object> map);
}