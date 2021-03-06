package com.ryx.credit.pojo.admin.order;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ryx.credit.common.util.DateJsonDeserializer;
import com.ryx.credit.common.util.DateJsonDeserializerMonth;
import com.ryx.credit.common.util.DateJsonSerializer;
import com.ryx.credit.common.util.DateJsonSerializerMonth;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class OPayment implements Serializable{
    private String id;

    private String userId;

    private String orderId;

    private String agentId;

    private String payMethod;

    private Date cTime;

    private BigDecimal payAmount;

    private BigDecimal realAmount;

    private BigDecimal outstandingAmount;

    @JSONField(format="yyyy-MM-dd")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @JsonDeserialize(using = DateJsonDeserializer.class)
    @JsonSerialize(using = DateJsonSerializer.class)
    private Date payCompletTime;

    private BigDecimal payStatus;

    private Date planSucTime;

    private String payRule;

    private String guaranteeAgent;

    private BigDecimal settlementPrice;

    private String shareTemplet;

    private BigDecimal isCloInvoice;

    private String deductionType;

    private BigDecimal deductionAmount;

    private BigDecimal downPayment;

    private BigDecimal downPaymentCount;

    @JSONField(
            format = "yyyy-MM"
    )
    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "yyyy-MM",
            timezone = "GMT+8"
    )
    @JsonDeserialize(
            using = DateJsonDeserializerMonth.class
    )
    @JsonSerialize(
            using = DateJsonSerializerMonth.class
    )
    private Date downPaymentDate;

    private String collectCompany;

    private String remark;

    private BigDecimal status;

    private BigDecimal version;

    private BigDecimal actualReceipt;

    @JSONField(
            format = "yyyy-MM-dd"
    )
    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "yyyy-MM-dd",
            timezone = "GMT+8"
    )
    @JsonDeserialize(
            using = DateJsonDeserializer.class
    )
    @JsonSerialize(
            using = DateJsonSerializer.class
    )
    private Date actualReceiptDate;

    private BigDecimal profitTaxAmt;

    private String downPaymentUser;

    private String settlementPriceStr;

    private Date nuclearTime;

    private String nuclearUser;

    private String extendscode;

    private String profitForm;

    private String profitMouth;

    private String customStaging;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId == null ? null : agentId.trim();
    }

    public String getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(String payMethod) {
        this.payMethod = payMethod == null ? null : payMethod.trim();
    }

    public Date getcTime() {
        return cTime;
    }

    public void setcTime(Date cTime) {
        this.cTime = cTime;
    }

    public BigDecimal getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(BigDecimal payAmount) {
        this.payAmount = payAmount;
    }

    public BigDecimal getRealAmount() {
        return realAmount;
    }

    public void setRealAmount(BigDecimal realAmount) {
        this.realAmount = realAmount;
    }

    public BigDecimal getOutstandingAmount() {
        return outstandingAmount;
    }

    public void setOutstandingAmount(BigDecimal outstandingAmount) {
        this.outstandingAmount = outstandingAmount;
    }

    public Date getPayCompletTime() {
        return payCompletTime;
    }

    public void setPayCompletTime(Date payCompletTime) {
        this.payCompletTime = payCompletTime;
    }

    public BigDecimal getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(BigDecimal payStatus) {
        this.payStatus = payStatus;
    }

    public Date getPlanSucTime() {
        return planSucTime;
    }

    public void setPlanSucTime(Date planSucTime) {
        this.planSucTime = planSucTime;
    }

    public String getPayRule() {
        return payRule;
    }

    public void setPayRule(String payRule) {
        this.payRule = payRule == null ? null : payRule.trim();
    }

    public String getGuaranteeAgent() {
        return guaranteeAgent;
    }

    public void setGuaranteeAgent(String guaranteeAgent) {
        this.guaranteeAgent = guaranteeAgent == null ? null : guaranteeAgent.trim();
    }

    public BigDecimal getSettlementPrice() {
        return settlementPrice;
    }

    public void setSettlementPrice(BigDecimal settlementPrice) {
        this.settlementPrice = settlementPrice;
    }

    public String getShareTemplet() {
        return shareTemplet;
    }

    public void setShareTemplet(String shareTemplet) {
        this.shareTemplet = shareTemplet == null ? null : shareTemplet.trim();
    }

    public BigDecimal getIsCloInvoice() {
        return isCloInvoice;
    }

    public void setIsCloInvoice(BigDecimal isCloInvoice) {
        this.isCloInvoice = isCloInvoice;
    }

    public String getDeductionType() {
        return deductionType;
    }

    public void setDeductionType(String deductionType) {
        this.deductionType = deductionType == null ? null : deductionType.trim();
    }

    public BigDecimal getDeductionAmount() {
        return deductionAmount;
    }

    public void setDeductionAmount(BigDecimal deductionAmount) {
        this.deductionAmount = deductionAmount;
    }

    public BigDecimal getDownPayment() {
        return downPayment;
    }

    public void setDownPayment(BigDecimal downPayment) {
        this.downPayment = downPayment;
    }

    public BigDecimal getDownPaymentCount() {
        return downPaymentCount;
    }

    public void setDownPaymentCount(BigDecimal downPaymentCount) {
        this.downPaymentCount = downPaymentCount;
    }

    public Date getDownPaymentDate() {
        return downPaymentDate;
    }

    public void setDownPaymentDate(Date downPaymentDate) {
        this.downPaymentDate = downPaymentDate;
    }

    public String getCollectCompany() {
        return collectCompany;
    }

    public void setCollectCompany(String collectCompany) {
        this.collectCompany = collectCompany == null ? null : collectCompany.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public BigDecimal getStatus() {
        return status;
    }

    public void setStatus(BigDecimal status) {
        this.status = status;
    }

    public BigDecimal getVersion() {
        return version;
    }

    public void setVersion(BigDecimal version) {
        this.version = version;
    }

    public BigDecimal getActualReceipt() {
        return actualReceipt;
    }

    public void setActualReceipt(BigDecimal actualReceipt) {
        this.actualReceipt = actualReceipt;
    }

    public Date getActualReceiptDate() {
        return actualReceiptDate;
    }

    public void setActualReceiptDate(Date actualReceiptDate) {
        this.actualReceiptDate = actualReceiptDate;
    }

    public BigDecimal getProfitTaxAmt() {
        return profitTaxAmt;
    }

    public void setProfitTaxAmt(BigDecimal profitTaxAmt) {
        this.profitTaxAmt = profitTaxAmt;
    }

    public String getDownPaymentUser() {
        return downPaymentUser;
    }

    public void setDownPaymentUser(String downPaymentUser) {
        this.downPaymentUser = downPaymentUser == null ? null : downPaymentUser.trim();
    }

    public String getSettlementPriceStr() {
        return settlementPriceStr;
    }

    public void setSettlementPriceStr(String settlementPriceStr) {
        this.settlementPriceStr = settlementPriceStr == null ? null : settlementPriceStr.trim();
    }

    public Date getNuclearTime() {
        return nuclearTime;
    }

    public void setNuclearTime(Date nuclearTime) {
        this.nuclearTime = nuclearTime;
    }

    public String getNuclearUser() {
        return nuclearUser;
    }

    public void setNuclearUser(String nuclearUser) {
        this.nuclearUser = nuclearUser == null ? null : nuclearUser.trim();
    }

    public String getExtendscode() {
        return extendscode;
    }

    public void setExtendscode(String extendscode) {
        this.extendscode = extendscode == null ? null : extendscode.trim();
    }

    public String getProfitForm() {
        return profitForm;
    }

    public void setProfitForm(String profitForm) {
        this.profitForm = profitForm == null ? null : profitForm.trim();
    }

    public String getProfitMouth() {
        return profitMouth;
    }

    public void setProfitMouth(String profitMouth) {
        this.profitMouth = profitMouth == null ? null : profitMouth.trim();
    }

    public String getCustomStaging() {
        return customStaging;
    }

    public void setCustomStaging(String customStaging) {
        this.customStaging = customStaging == null ? null : customStaging.trim();
    }
}