package com.ryx.credit.dao.agent;


import com.ryx.credit.pojo.admin.agent.BusActRel;
import com.ryx.credit.pojo.admin.agent.BusActRelExample;
import com.ryx.credit.pojo.admin.agent.BusActRelKey;
import org.apache.ibatis.annotations.Param;
import org.omg.CORBA.OBJ_ADAPTER;

import java.util.List;
import java.util.Map;

public interface BusActRelMapper {
    int countByExample(BusActRelExample example);

    int deleteByExample(BusActRelExample example);

    int insert(BusActRel record);

    int insertSelective(BusActRel record);

    List<BusActRel> selectByExample(BusActRelExample example);

    BusActRel selectByPrimaryKey(BusActRelKey key);

    int updateByPrimaryKeySelective(BusActRel record);

    int updateByPrimaryKey(BusActRel record);

    BusActRel findById(String activId);

    int updateActivIdByActivId(@Param("activId") String activId, @Param("newActivId") String newActivId);

    BusActRel findByBusId(String busId);

    BusActRel findByActivId(String activId);

    Map<String, Object> queryActRuTaskByMap(Map<String, Object> paramMap);
}