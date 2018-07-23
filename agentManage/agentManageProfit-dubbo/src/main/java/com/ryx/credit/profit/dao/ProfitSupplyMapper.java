package com.ryx.credit.profit.dao;

import com.ryx.credit.profit.pojo.ProfitSupply;
import com.ryx.credit.profit.pojo.ProfitSupplyExample;
import java.util.List;

public interface ProfitSupplyMapper {
    int countByExample(ProfitSupplyExample example);

    int deleteByExample(ProfitSupplyExample example);

    int insert(ProfitSupply record);

    int insertSelective(ProfitSupply record);

    List<ProfitSupply> selectByExample(ProfitSupplyExample example);

    ProfitSupply selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ProfitSupply record);

    int updateByPrimaryKey(ProfitSupply record);
}