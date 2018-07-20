package com.ryx.credit.dao.order;

import com.ryx.credit.activity.entity.OReceiptPro;
import com.ryx.credit.activity.entity.OReceiptProExample;

import java.util.List;

public interface OReceiptProMapper {
    int countByExample(OReceiptProExample example);

    int deleteByExample(OReceiptProExample example);

    int insert(OReceiptPro record);

    int insertSelective(OReceiptPro record);

    List<OReceiptPro> selectByExample(OReceiptProExample example);

    OReceiptPro selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(OReceiptPro record);

    int updateByPrimaryKey(OReceiptPro record);
}