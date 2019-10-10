package com.ryx.credit.pojo.admin.vo;

import java.io.Serializable;
import java.util.Date;

/**

/**
 * @Auther: RYX
 * @Date: 2018/8/30 11:14
 * @Description:业务部的导出数据
 */
public class BusinessOutVo extends AgentExtends implements Serializable {
    private String id;

    private String agUniqNum;

    private String agName;

    private String agHead;

    private String agHeadMobile;

    private String agRegAdd;

    private String busId;
    private String busNum;

    private String busPlatform;
    private String platformName;//平台名称

    private String busType;

    private String busParent;

    private String busRiskParent;

    private String busActivationParent;

    private String busRegion;

    private String busIndeAss;

    private String busRiskEmail;

    private String busScope;

    private Date approveTime;

    private String busContactPerson;

    private String agDocPro;

    private String time;

    private String busLoginNum;

    private String busStatus;

    private String busParentBusNum;

    private String busactivationparentbusNum;

    public String getBusParentBusNum() {
        return busParentBusNum;
    }

    public void setBusParentBusNum(String busParentBusNum) {
        this.busParentBusNum = busParentBusNum;
    }

    public String getBusactivationparentbusNum() {
        return busactivationparentbusNum;
    }

    public void setBusactivationparentbusNum(String busactivationparentbusNum) {
        this.busactivationparentbusNum = busactivationparentbusNum;
    }

    public String getBusId() {
        return busId;
    }

    public void setBusId(String busId) {
        this.busId = busId;
    }

    public String getBusStatus() {
        return busStatus;
    }

    public void setBusStatus(String busStatus) {
        this.busStatus = busStatus;
    }

    public String getBusLoginNum() {
        return busLoginNum;
    }

    public void setBusLoginNum(String busLoginNum) {
        this.busLoginNum = busLoginNum;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Date getApproveTime() {
        return approveTime;
    }

    public void setApproveTime(Date approveTime) {
        this.approveTime = approveTime;
    }

    public String getBusContactPerson() {
        return busContactPerson;
    }

    public void setBusContactPerson(String busContactPerson) {
        this.busContactPerson = busContactPerson;
    }

    public String getAgDocPro() {
        return agDocPro;
    }

    public void setAgDocPro(String agDocPro) {
        this.agDocPro = agDocPro;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getAgHead() {
        return agHead;
    }

    public void setAgHead(String agHead) {
        this.agHead = agHead;
    }

    public String getAgHeadMobile() {
        return agHeadMobile;
    }

    public void setAgHeadMobile(String agHeadMobile) {
        this.agHeadMobile = agHeadMobile;
    }

    public String getAgRegAdd() {
        return agRegAdd;
    }

    public void setAgRegAdd(String agRegAdd) {
        this.agRegAdd = agRegAdd;
    }

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

    public String getBusType() {
        return busType;
    }

    public void setBusType(String busType) {
        this.busType = busType;
    }

    public String getBusParent() {
        return busParent;
    }

    public void setBusParent(String busParent) {
        this.busParent = busParent;
    }

    public String getBusRiskParent() {
        return busRiskParent;
    }

    public void setBusRiskParent(String busRiskParent) {
        this.busRiskParent = busRiskParent;
    }

    public String getBusActivationParent() {
        return busActivationParent;
    }

    public void setBusActivationParent(String busActivationParent) {
        this.busActivationParent = busActivationParent;
    }

    public String getBusRegion() {
        return busRegion;
    }

    public void setBusRegion(String busRegion) {
        this.busRegion = busRegion;
    }

    public String getBusIndeAss() {
        return busIndeAss;
    }

    public void setBusIndeAss(String busIndeAss) {
        this.busIndeAss = busIndeAss;
    }

    public String getBusRiskEmail() {
        return busRiskEmail;
    }

    public void setBusRiskEmail(String busRiskEmail) {
        this.busRiskEmail = busRiskEmail;
    }

    public String getBusScope() {
        return busScope;
    }

    public void setBusScope(String busScope) {
        this.busScope = busScope;
    }

    public String getPlatformName() {
        return platformName;
    }

    public void setPlatformName(String platformName) {
        this.platformName = platformName;
    }
}