package com.ryx.credit.pojo.admin.agent;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ryx.credit.common.util.DateJsonDeserializer;
import com.ryx.credit.common.util.DateJsonSerializer;
import com.ryx.credit.pojo.admin.vo.AgentExtends;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class Agent extends AgentExtends implements Serializable {
    private String id;

    private String agUniqNum;

    private String agName;

    private BigDecimal agNature;

    private BigDecimal agCapital;

    private String agBusLic;

    @JSONField(format="yyyy-MM-dd")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @JsonDeserialize(using = DateJsonDeserializer.class)
    @JsonSerialize(using = DateJsonSerializer.class)
    private Date agBusLicb;

    @JSONField(format="yyyy-MM-dd")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @JsonDeserialize(using = DateJsonDeserializer.class)
    @JsonSerialize(using = DateJsonSerializer.class)
    private Date agBusLice;

    private String agLegal;

    private BigDecimal agLegalCertype;

    private String agLegalCernum;

    private String agLegalMobile;

    private String agHead;

    private String agHeadMobile;

    private String agRegAdd;

    private String agBusScope;

    private BigDecimal cloTaxPoint;

    private String agStatus;

    private String agDocPro;

    private String agDocDistrict;

    private String agRemark;

    private Date cIncomTime;

    private Date cTime;

    private Date cUtime;

    private BigDecimal cIncomStatus;

    private String cUser;

    private String agZbh;

    private BigDecimal status;

    private BigDecimal version;

    private BigDecimal caStatus;

    private String agRegArea;

    private boolean isImport = false;

    private BigDecimal freestatus;

    private String busRiskEmail;

    private String busContactEmail;

    private BigDecimal reportStatus;

    private Date reportTime;

    private String agRunAdd;

    private Date agLegalCerdate;

    public String getAgRunAdd() {
        return agRunAdd;
    }

    public void setAgRunAdd(String agRunAdd) {
        this.agRunAdd = agRunAdd;
    }

    public Date getAgLegalCerdate() {
        return agLegalCerdate;
    }

    public void setAgLegalCerdate(Date agLegalCerdate) {
        this.agLegalCerdate = agLegalCerdate;
    }

    public String getBusRiskEmail() {
        return busRiskEmail;
    }

    public void setBusRiskEmail(String busRiskEmail) {
        this.busRiskEmail = busRiskEmail;
    }

    public String getBusContactEmail() {
        return busContactEmail;
    }

    public void setBusContactEmail(String busContactEmail) {
        this.busContactEmail = busContactEmail;
    }

    public BigDecimal getReportStatus() {
        return reportStatus;
    }

    public void setReportStatus(BigDecimal reportStatus) {
        this.reportStatus = reportStatus;
    }

    public Date getReportTime() {
        return reportTime;
    }

    public void setReportTime(Date reportTime) {
        this.reportTime = reportTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getAgUniqNum() {
        return agUniqNum;
    }

    public void setAgUniqNum(String agUniqNum) {
        this.agUniqNum = agUniqNum == null ? null : agUniqNum.trim();
    }

    public String getAgName() {
        return agName;
    }

    public void setAgName(String agName) {
        this.agName = agName == null ? null : agName.trim();
    }

    public BigDecimal getAgNature() {
        return agNature;
    }

    public void setAgNature(BigDecimal agNature) {
        this.agNature = agNature;
    }

    public BigDecimal getAgCapital() {
        return agCapital;
    }

    public void setAgCapital(BigDecimal agCapital) {
        this.agCapital = agCapital;
    }

    public String getAgBusLic() {
        return agBusLic;
    }

    public void setAgBusLic(String agBusLic) {
        this.agBusLic = agBusLic == null ? null : agBusLic.trim();
    }

    public Date getAgBusLicb() {
        return agBusLicb;
    }

    public void setAgBusLicb(Date agBusLicb) {
        this.agBusLicb = agBusLicb;
    }

    public Date getAgBusLice() {
        return agBusLice;
    }

    public void setAgBusLice(Date agBusLice) {
        this.agBusLice = agBusLice;
    }

    public String getAgLegal() {
        return agLegal;
    }

    public void setAgLegal(String agLegal) {
        this.agLegal = agLegal == null ? null : agLegal.trim();
    }

    public BigDecimal getAgLegalCertype() {
        return agLegalCertype;
    }

    public void setAgLegalCertype(BigDecimal agLegalCertype) {
        this.agLegalCertype = agLegalCertype;
    }

    public String getAgLegalCernum() {
        return agLegalCernum;
    }

    public void setAgLegalCernum(String agLegalCernum) {
        this.agLegalCernum = agLegalCernum == null ? null : agLegalCernum.trim();
    }

    public String getAgLegalMobile() {
        return agLegalMobile;
    }

    public void setAgLegalMobile(String agLegalMobile) {
        this.agLegalMobile = agLegalMobile == null ? null : agLegalMobile.trim();
    }

    public String getAgHead() {
        return agHead;
    }

    public void setAgHead(String agHead) {
        this.agHead = agHead == null ? null : agHead.trim();
    }

    public String getAgHeadMobile() {
        return agHeadMobile;
    }

    public void setAgHeadMobile(String agHeadMobile) {
        this.agHeadMobile = agHeadMobile == null ? null : agHeadMobile.trim();
    }

    public String getAgRegAdd() {
        return agRegAdd;
    }

    public void setAgRegAdd(String agRegAdd) {
        this.agRegAdd = agRegAdd == null ? null : agRegAdd.trim();
    }

    public String getAgBusScope() {
        return agBusScope;
    }

    public void setAgBusScope(String agBusScope) {
        this.agBusScope = agBusScope == null ? null : agBusScope.trim();
    }

    public BigDecimal getCloTaxPoint() {
        return cloTaxPoint;
    }

    public void setCloTaxPoint(BigDecimal cloTaxPoint) {
        this.cloTaxPoint = cloTaxPoint;
    }

    public String getAgStatus() {
        return agStatus;
    }

    public void setAgStatus(String agStatus) {
        this.agStatus = agStatus == null ? null : agStatus.trim();
    }

    public String getAgDocPro() {
        return agDocPro;
    }

    public void setAgDocPro(String agDocPro) {
        this.agDocPro = agDocPro == null ? null : agDocPro.trim();
    }

    public String getAgDocDistrict() {
        return agDocDistrict;
    }

    public void setAgDocDistrict(String agDocDistrict) {
        this.agDocDistrict = agDocDistrict == null ? null : agDocDistrict.trim();
    }

    public String getAgRemark() {
        return agRemark;
    }

    public void setAgRemark(String agRemark) {
        this.agRemark = agRemark == null ? null : agRemark.trim();
    }

    public Date getcIncomTime() {
        return cIncomTime;
    }

    public void setcIncomTime(Date cIncomTime) {
        this.cIncomTime = cIncomTime;
    }

    public Date getcTime() {
        return cTime;
    }

    public void setcTime(Date cTime) {
        this.cTime = cTime;
    }

    public Date getcUtime() {
        return cUtime;
    }

    public void setcUtime(Date cUtime) {
        this.cUtime = cUtime;
    }

    public BigDecimal getcIncomStatus() {
        return cIncomStatus;
    }

    public void setcIncomStatus(BigDecimal cIncomStatus) {
        this.cIncomStatus = cIncomStatus;
    }

    public String getcUser() {
        return cUser;
    }

    public void setcUser(String cUser) {
        this.cUser = cUser == null ? null : cUser.trim();
    }

    public String getAgZbh() {
        return agZbh;
    }

    public void setAgZbh(String agZbh) {
        this.agZbh = agZbh == null ? null : agZbh.trim();
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

    public BigDecimal getCaStatus() {
        return caStatus;
    }

    public void setCaStatus(BigDecimal caStatus) {
        this.caStatus = caStatus;
    }

    public String getAgRegArea() {
        return agRegArea;
    }

    public void setAgRegArea(String agRegArea) {
        this.agRegArea = agRegArea;
    }

    public boolean isImport() {
        return isImport;
    }

    public void setImport(boolean anImport) {
        isImport = anImport;
    }

    public BigDecimal getFreestatus() {
        return freestatus;
    }

    public void setFreestatus(BigDecimal freestatus) {
        this.freestatus = freestatus;
    }
}