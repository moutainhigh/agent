package com.ryx.internet.pojo;

import com.ryx.credit.pojo.admin.agent.Attachment;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class OInternetRenew implements Serializable{
    private String id;

    private String renewWay;

    private BigDecimal renewCardCount;

    private BigDecimal sumOffsetAmt;

    private BigDecimal suppAmt;

    private BigDecimal reviewStatus;

    private Date reviewPassTime;

    private String applyRemark;

    private Date cTime;

    private Date uTime;

    private String cUser;

    private String uUser;

    private BigDecimal status;

    private BigDecimal version;

    private String agentId;

    private String agentName;

    private String files;

    private String iccidNumIds;

    private String renewWayName;

    private String busNum;

    private String busPlatform;

    private String agDocDistrict;

    private String agDocPro;

    private String busContactPerson;

    private List<Attachment> attachmentList;

    public String getBusNum() {
        return busNum;
    }

    public void setBusNum(String busNum) {
        this.busNum = busNum;
    }

    public String getBusPlatform() {
        return busPlatform;
    }

    public void setBusPlatform(String busPlatform) {
        this.busPlatform = busPlatform;
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

    public String getRenewWayName() {
        return renewWayName;
    }

    public void setRenewWayName(String renewWayName) {
        this.renewWayName = renewWayName;
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public List<Attachment> getAttachmentList() {
        return attachmentList;
    }

    public void setAttachmentList(List<Attachment> attachmentList) {
        this.attachmentList = attachmentList;
    }

    public String getIccidNumIds() {
        return iccidNumIds;
    }

    public void setIccidNumIds(String iccidNumIds) {
        this.iccidNumIds = iccidNumIds;
    }

    public String getFiles() {
        return files;
    }

    public void setFiles(String files) {
        this.files = files;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getRenewWay() {
        return renewWay;
    }

    public void setRenewWay(String renewWay) {
        this.renewWay = renewWay == null ? null : renewWay.trim();
    }

    public BigDecimal getRenewCardCount() {
        return renewCardCount;
    }

    public void setRenewCardCount(BigDecimal renewCardCount) {
        this.renewCardCount = renewCardCount;
    }

    public BigDecimal getSumOffsetAmt() {
        return sumOffsetAmt;
    }

    public void setSumOffsetAmt(BigDecimal sumOffsetAmt) {
        this.sumOffsetAmt = sumOffsetAmt;
    }

    public BigDecimal getSuppAmt() {
        return suppAmt;
    }

    public void setSuppAmt(BigDecimal suppAmt) {
        this.suppAmt = suppAmt;
    }

    public BigDecimal getReviewStatus() {
        return reviewStatus;
    }

    public void setReviewStatus(BigDecimal reviewStatus) {
        this.reviewStatus = reviewStatus;
    }

    public Date getReviewPassTime() {
        return reviewPassTime;
    }

    public void setReviewPassTime(Date reviewPassTime) {
        this.reviewPassTime = reviewPassTime;
    }

    public String getApplyRemark() {
        return applyRemark;
    }

    public void setApplyRemark(String applyRemark) {
        this.applyRemark = applyRemark == null ? null : applyRemark.trim();
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

    @Override
    public String toString() {
        return "OInternetRenew{" +
                "id='" + id + '\'' +
                ", renewWay='" + renewWay + '\'' +
                ", renewCardCount=" + renewCardCount +
                ", sumOffsetAmt=" + sumOffsetAmt +
                ", suppAmt=" + suppAmt +
                ", reviewStatus=" + reviewStatus +
                ", reviewPassTime=" + reviewPassTime +
                ", applyRemark='" + applyRemark + '\'' +
                ", cTime=" + cTime +
                ", uTime=" + uTime +
                ", cUser='" + cUser + '\'' +
                ", uUser='" + uUser + '\'' +
                ", status=" + status +
                ", version=" + version +
                ", agentId='" + agentId + '\'' +
                ", agentName='" + agentName + '\'' +
                ", files='" + files + '\'' +
                ", iccidNumIds='" + iccidNumIds + '\'' +
                ", renewWayName='" + renewWayName + '\'' +
                ", busNum='" + busNum + '\'' +
                ", busPlatform='" + busPlatform + '\'' +
                ", agDocDistrict='" + agDocDistrict + '\'' +
                ", agDocPro='" + agDocPro + '\'' +
                ", busContactPerson='" + busContactPerson + '\'' +
                ", attachmentList=" + attachmentList +
                '}';
    }
}