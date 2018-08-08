package com.ryx.credit.profit.dao;

import com.ryx.credit.profit.pojo.ProfitDeduction;
import com.ryx.credit.profit.pojo.ProfitDeductionExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ProfitDeductionMapper {
    int countByExample(ProfitDeductionExample example);

    int deleteByExample(ProfitDeductionExample example);

    int insert(ProfitDeduction record);

    int insertSelective(ProfitDeduction record);

    List<ProfitDeduction> selectByExample(ProfitDeductionExample example);

    ProfitDeduction selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ProfitDeduction record);

    int updateByPrimaryKey(ProfitDeduction record);

    List<Map<String, Object>> getDeductDetail(String deductDate);

    List<Map<String,Object>> getNotDeductDetail(@Param("beforeDeductDate") String beforeDeductDate, @Param("deductDate") String deductDate, @Param("type") String type);
}