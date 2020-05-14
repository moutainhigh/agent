package com.ryx.credit.pojo.admin.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
/**
 * @Auther: lrr
 * @Date: 2018/9/11 17:38
 * @Description:订单发货的导出实体类
 */
public class OrderoutVo implements Serializable{
    private String num;//单号
    private String payAmo;//总金额
    private String apytime;//订单日期
    private String agentId;//代理商唯一编码
    private String agUniqNum;//自编唯一码
    private String agName;//代理商名称
    private String platformName;//平台
    private String platformType;//平台
    private String proNum;//数量
    private String payMethod;//结算方式
    private String downPaymentUser;//付款人
    private String comName;//收款地方
    private BigDecimal actualReceipt;//付款金额
    private String xxAmount;//首付款金额
    private Date actualReceiptDate;//收款日期
    private String actualTime;
    private String oinuretime;//审批时间
    private String deductionType;//抵扣类型
    private BigDecimal deductionAmount;//抵扣金额
    private String amount ;//保证金抵货款金额
    private BigDecimal money;//分期金额
    private BigDecimal planNum;//分期笔数

    private String nuclearUser;//核款人
    private Date nuclearTime;
    private String nuclearTimeString;

    private String proType;//机具类型
    private String activityName;//活动名称
    private BigDecimal ddAmt;//订单金额
    private BigDecimal ykfrAmt;//应扣分润金额
    private String downPaymentDate;//分润扣款开始月份
    private BigDecimal downPaymentCount;//分期扣分润期数
    private BigDecimal downPayment;////首付款合计
    private String mqykAmt;//每期应扣分润金额
    private BigDecimal skfrAmt;//实扣分润金额
    private BigDecimal fqdkAmt;//分期打款金额
    private BigDecimal frdkCount;//分期打款期数
    private BigDecimal sjdkAmt;//实际还款金额
    private BigDecimal syqkAmt;//剩余欠款

    private String proRelPrice;//单价
    private String model;//机具型号
    private String reviewStatus;//审批状态
    private String orgName;//顶级机构

    private String payUser;//打款人
    private String realRecTime;//实际到账日期
    private String checkDate;//核款日期
    private String remark;//备注

    private String busNum;//业务平台编码
    private String mqydkAmt;//每期应打款金额
    private String profitMouth;//存量分润
    private String profitForm;//分润形式

    private String toPickNum;//待配货
    private String isPlanNum;//已排单
    private String toPlanNum;//待排单

    private String orderUser;//订单提交人
    private String orderDate;//订单提交时间
    private String agDocDistrict;//对接大区
    private String agDocPro;//对接省区
    private String proCode;//商品编号
    private String proName;//商品名称


    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    public String getPlatformType() {
        return platformType;
    }

    public void setPlatformType(String platformType) {
        this.platformType = platformType;
    }

    public String getOrderUser() {
        return orderUser;
    }

    public void setOrderUser(String orderUser) {
        this.orderUser = orderUser;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getAgDocDistrict() {
        return agDocDistrict;
    }

    public void setAgDocDistrict(String agDocDistrict) {
        this.agDocDistrict = agDocDistrict;
    }

    public String getAgDocPro() {
        return agDocPro;
    }

    public void setAgDocPro(String agDocPro) {
        this.agDocPro = agDocPro;
    }

    public String getProCode() {
        return proCode;
    }

    public void setProCode(String proCode) {
        this.proCode = proCode;
    }

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }

    public String getToPickNum() {
        return toPickNum;
    }

    public void setToPickNum(String toPickNum) {
        this.toPickNum = toPickNum;
    }

    public String getIsPlanNum() {
        return isPlanNum;
    }

    public void setIsPlanNum(String isPlanNum) {
        this.isPlanNum = isPlanNum;
    }

    public String getToPlanNum() {
        return toPlanNum;
    }

    public void setToPlanNum(String toPlanNum) {
        this.toPlanNum = toPlanNum;
    }

    public String getBusNum() {
        return busNum;
    }

    public void setBusNum(String busNum) {
        this.busNum = busNum;
    }

    public String getMqydkAmt() {
        return mqydkAmt;
    }

    public void setMqydkAmt(String mqydkAmt) {
        this.mqydkAmt = mqydkAmt;
    }

    public String getProfitMouth() {
        return profitMouth;
    }

    public void setProfitMouth(String profitMouth) {
        this.profitMouth = profitMouth;
    }

    public String getProfitForm() {
        return profitForm;
    }

    public void setProfitForm(String profitForm) {
        this.profitForm = profitForm;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getPayUser() {
        return payUser;
    }

    public void setPayUser(String payUser) {
        this.payUser = payUser;
    }

    public String getRealRecTime() {
        return realRecTime;
    }

    public void setRealRecTime(String realRecTime) {
        this.realRecTime = realRecTime;
    }

    public String getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(String checkDate) {
        this.checkDate = checkDate;
    }

    public String getProType() {
        return proType;
    }

    public void setProType(String proType) {
        this.proType = proType;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public BigDecimal getDdAmt() {
        return ddAmt;
    }

    public void setDdAmt(BigDecimal ddAmt) {
        this.ddAmt = ddAmt;
    }

    public BigDecimal getYkfrAmt() {
        return ykfrAmt;
    }

    public void setYkfrAmt(BigDecimal ykfrAmt) {
        this.ykfrAmt = ykfrAmt;
    }

    public String getDownPaymentDate() {
        return downPaymentDate;
    }

    public void setDownPaymentDate(String downPaymentDate) {
        this.downPaymentDate = downPaymentDate;
    }

    public BigDecimal getDownPaymentCount() {
        return downPaymentCount;
    }

    public void setDownPaymentCount(BigDecimal downPaymentCount) {
        this.downPaymentCount = downPaymentCount;
    }

    public BigDecimal getDownPayment() {
        return downPayment;
    }

    public void setDownPayment(BigDecimal downPayment) {
        this.downPayment = downPayment;
    }

    public String getMqykAmt() {
        return mqykAmt;
    }

    public void setMqykAmt(String mqykAmt) {
        this.mqykAmt = mqykAmt;
    }

    public BigDecimal getSkfrAmt() {
        return skfrAmt;
    }

    public void setSkfrAmt(BigDecimal skfrAmt) {
        this.skfrAmt = skfrAmt;
    }

    public BigDecimal getFqdkAmt() {
        return fqdkAmt;
    }

    public void setFqdkAmt(BigDecimal fqdkAmt) {
        this.fqdkAmt = fqdkAmt;
    }

    public BigDecimal getFrdkCount() {
        return frdkCount;
    }

    public void setFrdkCount(BigDecimal frdkCount) {
        this.frdkCount = frdkCount;
    }

    public BigDecimal getSjdkAmt() {
        return sjdkAmt;
    }

    public void setSjdkAmt(BigDecimal sjdkAmt) {
        this.sjdkAmt = sjdkAmt;
    }

    public BigDecimal getSyqkAmt() {
        return syqkAmt;
    }

    public void setSyqkAmt(BigDecimal syqkAmt) {
        this.syqkAmt = syqkAmt;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public String getDeductionType() {
        return deductionType;
    }

    public void setDeductionType(String deductionType) {
        this.deductionType = deductionType;
    }

    public BigDecimal getDeductionAmount() {
        return deductionAmount;
    }

    public void setDeductionAmount(BigDecimal deductionAmount) {
        this.deductionAmount = deductionAmount;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }


    public BigDecimal getPlanNum() {
        return planNum;
    }

    public void setPlanNum(BigDecimal planNum) {
        this.planNum = planNum;
    }

    public String getOinuretime() {
        return oinuretime;
    }

    public void setOinuretime(String oinuretime) {
        this.oinuretime = oinuretime;
    }

    public String getActualTime() {
        return actualTime;
    }

    public void setActualTime(String actualTime) {
        this.actualTime = actualTime;
    }

    public String getPayAmo() {
        return payAmo;
    }

    public void setPayAmo(String payAmo) {
        this.payAmo = payAmo;
    }


    public String getAgUniqNum() {
        return agUniqNum;
    }

    public void setAgUniqNum(String agUniqNum) {
        this.agUniqNum = agUniqNum;
    }

    public String getAgName() {
        return agName;
    }

    public void setAgName(String agName) {
        this.agName = agName;
    }

    public String getPlatformName() {
        return platformName;
    }

    public void setPlatformName(String platformName) {
        this.platformName = platformName;
    }

    public String getProNum() {
        return proNum;
    }

    public void setProNum(String proNum) {
        this.proNum = proNum;
    }

    public String getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(String payMethod) {
        this.payMethod = payMethod;
    }

    public String getDownPaymentUser() {
        return downPaymentUser;
    }

    public void setDownPaymentUser(String downPaymentUser) {
        this.downPaymentUser = downPaymentUser;
    }

    public String getComName() {
        return comName;
    }

    public void setComName(String comName) {
        this.comName = comName;
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

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getApytime() {
        return apytime;
    }

    public void setApytime(String apytime) {
        this.apytime = apytime;
    }

    public String getNuclearUser() {
        return nuclearUser;
    }

    public void setNuclearUser(String nuclearUser) {
        this.nuclearUser = nuclearUser;
    }

    public Date getNuclearTime() {
        return nuclearTime;
    }

    public void setNuclearTime(Date nuclearTime) {
        this.nuclearTime = nuclearTime;
    }

    public String getNuclearTimeString() {
        return nuclearTimeString;
    }

    public void setNuclearTimeString(String nuclearTimeString) {
        this.nuclearTimeString = nuclearTimeString;
    }

    public String getXxAmount() {
        return xxAmount;
    }

    public void setXxAmount(String xxAmount) {
        this.xxAmount = xxAmount;
    }

    public String getProRelPrice() {
        return proRelPrice;
    }

    public void setProRelPrice(String proRelPrice) {
        this.proRelPrice = proRelPrice;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getReviewStatus() {
        return reviewStatus;
    }

    public void setReviewStatus(String reviewStatus) {
        this.reviewStatus = reviewStatus;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }
}