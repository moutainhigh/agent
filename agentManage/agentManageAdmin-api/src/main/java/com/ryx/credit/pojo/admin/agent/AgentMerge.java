package com.ryx.credit.pojo.admin.agent;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class AgentMerge implements Serializable{
    private String id;

    private String mainAgentId;

    private String mainAgentName;

    private String subAgentId;

    private String subAgentName;

    private BigDecimal subAgentDebt;

    private BigDecimal subAgentOweTicket;

    private BigDecimal cloReviewStatus;

    private Date cTime;

    private Date uTime;

    private String cUser;

    private String uUser;

    private BigDecimal status;

    private BigDecimal version;

    private BigDecimal mergeType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getMainAgentId() {
        return mainAgentId;
    }

    public void setMainAgentId(String mainAgentId) {
        this.mainAgentId = mainAgentId == null ? null : mainAgentId.trim();
    }

    public String getMainAgentName() {
        return mainAgentName;
    }

    public void setMainAgentName(String mainAgentName) {
        this.mainAgentName = mainAgentName == null ? null : mainAgentName.trim();
    }

    public String getSubAgentId() {
        return subAgentId;
    }

    public void setSubAgentId(String subAgentId) {
        this.subAgentId = subAgentId == null ? null : subAgentId.trim();
    }

    public String getSubAgentName() {
        return subAgentName;
    }

    public void setSubAgentName(String subAgentName) {
        this.subAgentName = subAgentName == null ? null : subAgentName.trim();
    }

    public BigDecimal getSubAgentDebt() {
        return subAgentDebt;
    }

    public void setSubAgentDebt(BigDecimal subAgentDebt) {
        this.subAgentDebt = subAgentDebt;
    }

    public BigDecimal getSubAgentOweTicket() {
        return subAgentOweTicket;
    }

    public void setSubAgentOweTicket(BigDecimal subAgentOweTicket) {
        this.subAgentOweTicket = subAgentOweTicket;
    }

    public BigDecimal getCloReviewStatus() {
        return cloReviewStatus;
    }

    public void setCloReviewStatus(BigDecimal cloReviewStatus) {
        this.cloReviewStatus = cloReviewStatus;
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

    public BigDecimal getMergeType() {
        return mergeType;
    }

    public void setMergeType(BigDecimal mergeType) {
        this.mergeType = mergeType;
    }
}