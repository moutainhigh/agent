package com.ryx.credit.profit.dao;

import com.ryx.credit.profit.pojo.ProfitDirect;
import com.ryx.credit.profit.pojo.ProfitDirectExample;
import java.util.List;
import java.util.Map;

public interface ProfitDirectMapper {
    long countByExample(ProfitDirectExample example);

    int deleteByExample(ProfitDirectExample example);

    int insert(ProfitDirect record);

    int insertSelective(ProfitDirect record);

    List<ProfitDirect> selectByExample(ProfitDirectExample example);

    ProfitDirect selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ProfitDirect record);

    int updateByPrimaryKey(ProfitDirect record);

    List<Map<String,Object>> getProfitDirectList(Map <String, Object> param);

    Long getProfitDirectCount(Map <String, Object> param);
}