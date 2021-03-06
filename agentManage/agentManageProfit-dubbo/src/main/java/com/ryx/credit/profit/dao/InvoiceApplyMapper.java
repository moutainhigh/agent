package com.ryx.credit.profit.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.ryx.credit.profit.pojo.InvoiceApply;
import com.ryx.credit.profit.pojo.InvoiceApplyExample;
import org.apache.ibatis.annotations.Param;

public interface InvoiceApplyMapper {
    long countByExample(InvoiceApplyExample example);

    int deleteByExample(InvoiceApplyExample example);

    int insert(InvoiceApply record);

    int insertSelective(InvoiceApply record);

    List<InvoiceApply> selectByExample(InvoiceApplyExample example);

    InvoiceApply selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") InvoiceApply record, @Param("example") InvoiceApplyExample example);

    int updateByExample(@Param("record") InvoiceApply record, @Param("example") InvoiceApplyExample example);

    int updateByPrimaryKeySelective(InvoiceApply record);

    int updateByPrimaryKey(InvoiceApply record);

    void  deleteById(String id);

    List<String> getPayCompanyById(String id);

    BigDecimal getSumInvoice(@Param("agentId") String agentId,@Param("invoiceCompany")String invoiceCompany,@Param("month")String month);

    BigDecimal getOwnInvoice(@Param("agentId") String agentId,@Param("invoiceCompany")String invoiceCompany,@Param("month")String month);

    BigDecimal getAgentTaxByAgentId(String agentId);

    List<Map<String,Object>> exports(InvoiceApplyExample example);

    Map<String,Object> getImportBatch(@Param("finUser") String finUser,@Param("dateStr") String dateStr);

    String getMaxImportBatch(String dateStr);

    List<Map<String,String>> getAgentIdByInvoiceCompany(@Param("invoiceCompany")String invoiceCompany,@Param("month") String month);

    List<Map<String,String>> getImportUserList();

    List<Map> selectByExampleOwn(InvoiceApplyExample example);

    long countByExampleOwn(InvoiceApplyExample example);

    List<InvoiceApply> selectListForDeal(@Param("invoiceNumber") String invoiceNumber,@Param("invoiceCode") String invoiceCode);
}