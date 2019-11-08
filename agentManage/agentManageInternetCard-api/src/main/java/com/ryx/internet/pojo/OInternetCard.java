package com.ryx.internet.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class OInternetCard implements Serializable{
    private String iccidNum;

    private String cardImportId;

    private String batchNum;

    private String snNum;

    private Date deliverTime;

    private String consignee;

    private String orderId;

    private String agentId;

    private String agentName;

    private String internetCardNum;

    private String issuer;

    private Date openAccountTime;

    private Date expireTime;

    private BigDecimal internetCardStatus;

    private String merId;

    private String latelyPayTime;

    private String merName;

    private String manufacturer;

    private BigDecimal renew;

    private Date cTime;

    private Date uTime;

    private String cUser;

    private String uUser;

    private BigDecimal status;

    private BigDecimal version;

    private BigDecimal stop;

    private String renewStatus;

    private String stopReason;

    private BigDecimal sumPostponeTime;

    private String snCount;

    private String beginSn;

    private String endSn;

    private String iccidNumId;

    private String openAccountTimeBeginStr;

    private String openAccountTimeEndStr;

    private String expireTimeBeginStr;

    private String expireTimeEndStr;

    private String renewButton;  //是否展示续费按钮   1 展示  0 不展示

    private String iccidNumBegin;

    private String iccidNumEnd;

    private String postponeTime;

    private String busNum;

    private String busPlatform;

    private String agDocDistrict;//数据库无

    private String agDocPro;//数据库无

    private String busContactPerson;//数据库无

    private String platformName; //数据库无

    public String getPlatformName() {
        return platformName;
    }

    public void setPlatformName(String platformName) {
        this.platformName = platformName;
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

    public String getBusContactPerson() {
        return busContactPerson;
    }

    public void setBusContactPerson(String busContactPerson) {
        this.busContactPerson = busContactPerson;
    }

    public String getBusPlatform() {
        return busPlatform;
    }

    public void setBusPlatform(String busPlatform) {
        this.busPlatform = busPlatform;
    }

    public String getBusNum() {
        return busNum;
    }

    public void setBusNum(String busNum) {
        this.busNum = busNum;
    }

    public String getPostponeTime() {
        return postponeTime;
    }

    public void setPostponeTime(String postponeTime) {
        this.postponeTime = postponeTime;
    }

    public String getIccidNumBegin() {
        return iccidNumBegin;
    }

    public void setIccidNumBegin(String iccidNumBegin) {
        this.iccidNumBegin = iccidNumBegin;
    }

    public String getIccidNumEnd() {
        return iccidNumEnd;
    }

    public void setIccidNumEnd(String iccidNumEnd) {
        this.iccidNumEnd = iccidNumEnd;
    }

    public BigDecimal getSumPostponeTime() {
        return sumPostponeTime;
    }

    public void setSumPostponeTime(BigDecimal sumPostponeTime) {
        this.sumPostponeTime = sumPostponeTime;
    }

    public String getRenewButton() {
        return renewButton;
    }

    public void setRenewButton(String renewButton) {
        this.renewButton = renewButton;
    }

    public String getOpenAccountTimeBeginStr() {
        return openAccountTimeBeginStr;
    }

    public void setOpenAccountTimeBeginStr(String openAccountTimeBeginStr) {
        this.openAccountTimeBeginStr = openAccountTimeBeginStr;
    }

    public String getOpenAccountTimeEndStr() {
        return openAccountTimeEndStr;
    }

    public void setOpenAccountTimeEndStr(String openAccountTimeEndStr) {
        this.openAccountTimeEndStr = openAccountTimeEndStr;
    }

    public String getExpireTimeBeginStr() {
        return expireTimeBeginStr;
    }

    public void setExpireTimeBeginStr(String expireTimeBeginStr) {
        this.expireTimeBeginStr = expireTimeBeginStr;
    }

    public String getExpireTimeEndStr() {
        return expireTimeEndStr;
    }

    public void setExpireTimeEndStr(String expireTimeEndStr) {
        this.expireTimeEndStr = expireTimeEndStr;
    }

    public String getIccidNumId() {
        return iccidNumId;
    }

    public void setIccidNumId(String iccidNumId) {
        this.iccidNumId = iccidNumId;
    }

    public BigDecimal getStop() {
        return stop;
    }

    public void setStop(BigDecimal stop) {
        this.stop = stop;
    }

    public String getRenewStatus() {
        return renewStatus;
    }

    public void setRenewStatus(String renewStatus) {
        this.renewStatus = renewStatus;
    }

    public String getStopReason() {
        return stopReason;
    }

    public void setStopReason(String stopReason) {
        this.stopReason = stopReason;
    }

    public String getBeginSn() {
        return beginSn;
    }

    public void setBeginSn(String beginSn) {
        this.beginSn = beginSn;
    }

    public String getEndSn() {
        return endSn;
    }

    public void setEndSn(String endSn) {
        this.endSn = endSn;
    }

    public String getSnCount() {
        return snCount;
    }

    public void setSnCount(String snCount) {
        this.snCount = snCount;
    }

    public String getIccidNum() {
        return iccidNum;
    }

    public void setIccidNum(String iccidNum) {
        this.iccidNum = iccidNum == null ? null : iccidNum.trim();
    }

    public String getCardImportId() {
        return cardImportId;
    }

    public void setCardImportId(String cardImportId) {
        this.cardImportId = cardImportId == null ? null : cardImportId.trim();
    }

    public String getBatchNum() {
        return batchNum;
    }

    public void setBatchNum(String batchNum) {
        this.batchNum = batchNum == null ? null : batchNum.trim();
    }

    public String getSnNum() {
        return snNum;
    }

    public void setSnNum(String snNum) {
        this.snNum = snNum == null ? null : snNum.trim();
    }

    public Date getDeliverTime() {
        return deliverTime;
    }

    public void setDeliverTime(Date deliverTime) {
        this.deliverTime = deliverTime;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee == null ? null : consignee.trim();
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

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName == null ? null : agentName.trim();
    }

    public String getInternetCardNum() {
        return internetCardNum;
    }

    public void setInternetCardNum(String internetCardNum) {
        this.internetCardNum = internetCardNum == null ? null : internetCardNum.trim();
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer == null ? null : issuer.trim();
    }

    public Date getOpenAccountTime() {
        return openAccountTime;
    }

    public void setOpenAccountTime(Date openAccountTime) {
        this.openAccountTime = openAccountTime;
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }

    public BigDecimal getInternetCardStatus() {
        return internetCardStatus;
    }

    public void setInternetCardStatus(BigDecimal internetCardStatus) {
        this.internetCardStatus = internetCardStatus;
    }

    public String getMerId() {
        return merId;
    }

    public void setMerId(String merId) {
        this.merId = merId == null ? null : merId.trim();
    }

    public String getLatelyPayTime() {
        return latelyPayTime;
    }

    public void setLatelyPayTime(String latelyPayTime) {
        this.latelyPayTime = latelyPayTime;
    }

    public String getMerName() {
        return merName;
    }

    public void setMerName(String merName) {
        this.merName = merName == null ? null : merName.trim();
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer == null ? null : manufacturer.trim();
    }

    public BigDecimal getRenew() {
        return renew;
    }

    public void setRenew(BigDecimal renew) {
        this.renew = renew;
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
}