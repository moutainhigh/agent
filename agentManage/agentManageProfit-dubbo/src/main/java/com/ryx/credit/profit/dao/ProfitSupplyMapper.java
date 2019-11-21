package com.ryx.credit.profit.dao;

import com.ryx.credit.profit.pojo.ProfitSupply;
import com.ryx.credit.profit.pojo.ProfitSupplyExample;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface ProfitSupplyMapper {
    int countByExample(ProfitSupplyExample example);

    int deleteByExample(ProfitSupplyExample example);

    int resetData(Map Param);

    int insert(ProfitSupply record);

    int insertSelective(ProfitSupply record);

    List<ProfitSupply> selectByExample(ProfitSupplyExample example);

    ProfitSupply selectByPrimaryKey(String id);

    ProfitSupply selectByAgentMonth(ProfitSupply record);

    int updateByPrimaryKeySelective(ProfitSupply record);

    int updateByPrimaryKey(ProfitSupply record);

    Long getProfitSupplyCount(Map<String, Object> param);

    BigDecimal getTotalByMonthAndPid(ProfitSupply record);

    BigDecimal getBuckleByMonthAndPid(ProfitSupply record);

    List<Map<String,Object>> getProfitSupplyList(Map <String, Object> param);

    Map<String,Object> profitCount(Map <String, Object> param);

    /**获取其他补款*/
    List<ProfitSupply> getProfitSuppList(ProfitSupply record);

    /**根据agentId,月份，parentAgentId,获取其他补款总计*/
    BigDecimal getTotalByPidMonthAndAndAG(Map<String,String> param);


    void clearRollingDifferenceSupplyData(String profitDate);
}