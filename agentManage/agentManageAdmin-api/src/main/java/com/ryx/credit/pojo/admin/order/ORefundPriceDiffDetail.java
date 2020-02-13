package com.ryx.credit.pojo.admin.order;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class ORefundPriceDiffDetail  implements Serializable {
    private String id;

    private String subOrderId;

    private String agentId;

    private String activityFrontId;

    private String activityRealId;

    private String refundPriceDiffId;

    private String proId;

    private String proName;

    private BigDecimal changeCount;

    private String activityName;

    private String activityWay;

    private String activityRule;

    private BigDecimal frontPrice;

    private BigDecimal price;

    private Date sTime;

    private String beginSn;

    private String endSn;

    private String vender;

    private String proModel;

    private Date cTime;

    private Date uTime;

    private String cUser;

    private String uUser;

    private BigDecimal status;

    private BigDecimal version;

    private Map<String, Object> refundPriceDiffDetailMap;

    private String orderId;

    private BigDecimal sendStatus;

    private String sendMsg;

    private BigDecimal orderType;

    private String frontProId;

    private String frontProName;

    private String platformType;

    private String oldOrgId;

    private String oldOrgName;

    private String newOrgId;

    private String newOrgName;

    private String deliveryTime;

    private Date appTime;

    private String oldSupdOrgId;

    private String oldSupdOrgName;

    private String newSupdOrgId;

    private String newSupdOrgName;

    private String delayDay;

    private String deliveryTimeType;

    private OActivity activityFront;

    private List<OActivity> oActivities;

    private List<Map> proMaps;

    private String newMachineId;

    private String oldMachineId;

    //原平台S码
    private String oldPosPlatCode;
    //目标平台S码
    private String newPosPlatCode;
    //原平台上级S码
    private String oldSupdPosPlatCode;
    //目标平台上级S码
    private String newSupdPosPlatCode;
    //当前机构品牌
    private String oldBrandCode;
    //目标机构品牌
    private String newBrandCode;

    public String getOldBrandCode() {
        return oldBrandCode;
    }

    public void setOldBrandCode(String oldBrandCode) {
        this.oldBrandCode = oldBrandCode;
    }

    public String getNewBrandCode() {
        return newBrandCode;
    }

    public void setNewBrandCode(String newBrandCode) {
        this.newBrandCode = newBrandCode;
    }

    public String getOldPosPlatCode() {
        return oldPosPlatCode;
    }

    public void setOldPosPlatCode(String oldPosPlatCode) {
        this.oldPosPlatCode = oldPosPlatCode;
    }

    public String getNewPosPlatCode() {
        return newPosPlatCode;
    }

    public void setNewPosPlatCode(String newPosPlatCode) {
        this.newPosPlatCode = newPosPlatCode;
    }

    public String getOldSupdPosPlatCode() {
        return oldSupdPosPlatCode;
    }

    public void setOldSupdPosPlatCode(String oldSupdPosPlatCode) {
        this.oldSupdPosPlatCode = oldSupdPosPlatCode;
    }

    public String getNewSupdPosPlatCode() {
        return newSupdPosPlatCode;
    }

    public void setNewSupdPosPlatCode(String newSupdPosPlatCode) {
        this.newSupdPosPlatCode = newSupdPosPlatCode;
    }

    public String getOldSupdOrgId() {
        return oldSupdOrgId;
    }

    public void setOldSupdOrgId(String oldSupdOrgId) {
        this.oldSupdOrgId = oldSupdOrgId;
    }

    public String getOldSupdOrgName() {
        return oldSupdOrgName;
    }

    public void setOldSupdOrgName(String oldSupdOrgName) {
        this.oldSupdOrgName = oldSupdOrgName;
    }

    public String getNewSupdOrgId() {
        return newSupdOrgId;
    }

    public void setNewSupdOrgId(String newSupdOrgId) {
        this.newSupdOrgId = newSupdOrgId;
    }

    public String getNewSupdOrgName() {
        return newSupdOrgName;
    }

    public void setNewSupdOrgName(String newSupdOrgName) {
        this.newSupdOrgName = newSupdOrgName;
    }

    public String getDelayDay() {
        return delayDay;
    }

    public void setDelayDay(String delayDay) {
        this.delayDay = delayDay;
    }

    public String getDeliveryTimeType() {
        return deliveryTimeType;
    }

    public void setDeliveryTimeType(String deliveryTimeType) {
        this.deliveryTimeType = deliveryTimeType;
    }

    public String getNewMachineId() {
        return newMachineId;
    }

    public void setNewMachineId(String newMachineId) {
        this.newMachineId = newMachineId;
    }

    public String getOldMachineId() {
        return oldMachineId;
    }

    public void setOldMachineId(String oldMachineId) {
        this.oldMachineId = oldMachineId;
    }

    public String getPlatformType() {
        return platformType;
    }

    public void setPlatformType(String platformType) {
        this.platformType = platformType;
    }

    public String getOldOrgId() {
        return oldOrgId;
    }

    public void setOldOrgId(String oldOrgId) {
        this.oldOrgId = oldOrgId;
    }

    public String getNewOrgId() {
        return newOrgId;
    }

    public void setNewOrgId(String newOrgId) {
        this.newOrgId = newOrgId;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public Date getAppTime() {
        return appTime;
    }

    public void setAppTime(Date appTime) {
        this.appTime = appTime;
    }

    public String getFrontProId() {
        return frontProId;
    }

    public void setFrontProId(String frontProId) {
        this.frontProId = frontProId;
    }

    public String getFrontProName() {
        return frontProName;
    }

    public void setFrontProName(String frontProName) {
        this.frontProName = frontProName;
    }

    public List<Map> getProMaps() {
        return proMaps;
    }

    public void setProMaps(List<Map> proMaps) {
        this.proMaps = proMaps;
    }

    public List<OActivity> getoActivities() {
        return oActivities;
    }

    public void setoActivities(List<OActivity> oActivities) {
        this.oActivities = oActivities;
    }

    public OActivity getActivityFront() {
        return activityFront;
    }

    public void setActivityFront(OActivity activityFront) {
        this.activityFront = activityFront;
    }

    public BigDecimal getOrderType() {
        return orderType;
    }

    public void setOrderType(BigDecimal orderType) {
        this.orderType = orderType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getSubOrderId() {
        return subOrderId;
    }

    public void setSubOrderId(String subOrderId) {
        this.subOrderId = subOrderId == null ? null : subOrderId.trim();
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId == null ? null : agentId.trim();
    }

    public String getActivityFrontId() {
        return activityFrontId;
    }

    public void setActivityFrontId(String activityFrontId) {
        this.activityFrontId = activityFrontId == null ? null : activityFrontId.trim();
    }

    public String getActivityRealId() {
        return activityRealId;
    }

    public void setActivityRealId(String activityRealId) {
        this.activityRealId = activityRealId == null ? null : activityRealId.trim();
    }

    public String getRefundPriceDiffId() {
        return refundPriceDiffId;
    }

    public void setRefundPriceDiffId(String refundPriceDiffId) {
        this.refundPriceDiffId = refundPriceDiffId == null ? null : refundPriceDiffId.trim();
    }

    public String getProId() {
        return proId;
    }

    public void setProId(String proId) {
        this.proId = proId == null ? null : proId.trim();
    }

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName == null ? null : proName.trim();
    }

    public BigDecimal getChangeCount() {
        return changeCount;
    }

    public void setChangeCount(BigDecimal changeCount) {
        this.changeCount = changeCount;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName == null ? null : activityName.trim();
    }

    public String getActivityWay() {
        return activityWay;
    }

    public void setActivityWay(String activityWay) {
        this.activityWay = activityWay == null ? null : activityWay.trim();
    }

    public String getActivityRule() {
        return activityRule;
    }

    public void setActivityRule(String activityRule) {
        this.activityRule = activityRule == null ? null : activityRule.trim();
    }

    public BigDecimal getFrontPrice() {
        return frontPrice;
    }

    public void setFrontPrice(BigDecimal frontPrice) {
        this.frontPrice = frontPrice;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Date getsTime() {
        return sTime;
    }

    public void setsTime(Date sTime) {
        this.sTime = sTime;
    }

    public String getBeginSn() {
        return beginSn;
    }

    public void setBeginSn(String beginSn) {
        this.beginSn = beginSn == null ? null : beginSn.trim();
    }

    public String getEndSn() {
        return endSn;
    }

    public void setEndSn(String endSn) {
        this.endSn = endSn == null ? null : endSn.trim();
    }

    public String getVender() {
        return vender;
    }

    public void setVender(String vender) {
        this.vender = vender == null ? null : vender.trim();
    }

    public String getProModel() {
        return proModel;
    }

    public void setProModel(String proModel) {
        this.proModel = proModel == null ? null : proModel.trim();
    }

    public Date getcTime() {
        return cTime;
    }

    public void setcTime(Date cTime) {
        this.cTime = cTime;
    }

    public Date getuTime() {
        return uTime;
    }

    public void setuTime(Date uTime) {
        this.uTime = uTime;
    }

    public String getcUser() {
        return cUser;
    }

    public void setcUser(String cUser) {
        this.cUser = cUser == null ? null : cUser.trim();
    }

    public String getuUser() {
        return uUser;
    }

    public void setuUser(String uUser) {
        this.uUser = uUser == null ? null : uUser.trim();
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

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    public BigDecimal getSendStatus() {
        return sendStatus;
    }

    public void setSendStatus(BigDecimal sendStatus) {
        this.sendStatus = sendStatus;
    }

    public String getSendMsg() {
        return sendMsg;
    }

    public void setSendMsg(String sendMsg) {
        this.sendMsg = sendMsg == null ? null : sendMsg.trim();
    }

    public Map<String, Object> getRefundPriceDiffDetailMap() {
        return refundPriceDiffDetailMap;
    }

    public void setRefundPriceDiffDetailMap(Map<String, Object> refundPriceDiffDetailMap) {
        this.refundPriceDiffDetailMap = refundPriceDiffDetailMap;
    }

    public String getOldOrgName() {
        return oldOrgName;
    }

    public void setOldOrgName(String oldOrgName) {
        this.oldOrgName = oldOrgName;
    }

    public String getNewOrgName() {
        return newOrgName;
    }

    public void setNewOrgName(String newOrgName) {
        this.newOrgName = newOrgName;
    }
}