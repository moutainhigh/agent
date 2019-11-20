package com.ryx.credit.profit.pojo;

import java.io.Serializable;
import java.math.BigDecimal;

public class PosRewardDetail implements Serializable {
    private String id;

    private String profitPosDate;

    private String posAgentId;

    private String posAgentName;

    private String posMechanismType;

    private String posMechanismId;

    private String posCompareCount;

    private String posCurrentCount;

    private String posCompareLoanCount;

    private String posCurrentLoanCount;

    private String posAmt;

    private String posStandard;

    private String posOwnReward;

    private String posDownReward;

    private String posReawrdProfit;

    private String posRemark;

    private String posCheckDeductAmt;

    private String orgid;

    private BigDecimal settlemonth1812totaltrans;

    private BigDecimal settlemonthtotaltrans;

    private String isstandard;

    private BigDecimal totalorder;

    private BigDecimal yearafter19totaltrans;

    private String contrastmonth;

    private BigDecimal newtransamount;

    private BigDecimal yearafter19credittrans;

    private BigDecimal settleMonthCreditTrans;

    private BigDecimal specialRewardStandard;

    private BigDecimal specialReward;

    private String assessStatus;

    private BigDecimal executeReward;

    private BigDecimal deductedReward;

    private BigDecimal executeRewardStandard;

    private String childAgentIdList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getProfitPosDate() {
        return profitPosDate;
    }

    public void setProfitPosDate(String profitPosDate) {
        this.profitPosDate = profitPosDate == null ? null : profitPosDate.trim();
    }

    public String getPosAgentId() {
        return posAgentId;
    }

    public void setPosAgentId(String posAgentId) {
        this.posAgentId = posAgentId == null ? null : posAgentId.trim();
    }

    public String getPosAgentName() {
        return posAgentName;
    }

    public void setPosAgentName(String posAgentName) {
        this.posAgentName = posAgentName == null ? null : posAgentName.trim();
    }

    public String getPosMechanismType() {
        return posMechanismType;
    }

    public void setPosMechanismType(String posMechanismType) {
        this.posMechanismType = posMechanismType == null ? null : posMechanismType.trim();
    }

    public String getPosMechanismId() {
        return posMechanismId;
    }

    public void setPosMechanismId(String posMechanismId) {
        this.posMechanismId = posMechanismId == null ? null : posMechanismId.trim();
    }

    public String getPosCompareCount() {
        return posCompareCount;
    }

    public void setPosCompareCount(String posCompareCount) {
        this.posCompareCount = posCompareCount == null ? null : posCompareCount.trim();
    }

    public String getPosCurrentCount() {
        return posCurrentCount;
    }

    public void setPosCurrentCount(String posCurrentCount) {
        this.posCurrentCount = posCurrentCount == null ? null : posCurrentCount.trim();
    }

    public String getPosCompareLoanCount() {
        return posCompareLoanCount;
    }

    public void setPosCompareLoanCount(String posCompareLoanCount) {
        this.posCompareLoanCount = posCompareLoanCount == null ? null : posCompareLoanCount.trim();
    }

    public String getPosCurrentLoanCount() {
        return posCurrentLoanCount;
    }

    public void setPosCurrentLoanCount(String posCurrentLoanCount) {
        this.posCurrentLoanCount = posCurrentLoanCount == null ? null : posCurrentLoanCount.trim();
    }

    public String getPosAmt() {
        return posAmt;
    }

    public void setPosAmt(String posAmt) {
        this.posAmt = posAmt == null ? null : posAmt.trim();
    }

    public String getPosStandard() {
        return posStandard;
    }

    public void setPosStandard(String posStandard) {
        this.posStandard = posStandard == null ? null : posStandard.trim();
    }

    public String getPosOwnReward() {
        return posOwnReward;
    }

    public void setPosOwnReward(String posOwnReward) {
        this.posOwnReward = posOwnReward == null ? null : posOwnReward.trim();
    }

    public String getPosDownReward() {
        return posDownReward;
    }

    public void setPosDownReward(String posDownReward) {
        this.posDownReward = posDownReward == null ? null : posDownReward.trim();
    }

    public String getPosReawrdProfit() {
        return posReawrdProfit;
    }

    public void setPosReawrdProfit(String posReawrdProfit) {
        this.posReawrdProfit = posReawrdProfit == null ? null : posReawrdProfit.trim();
    }

    public String getPosRemark() {
        return posRemark;
    }

    public void setPosRemark(String posRemark) {
        this.posRemark = posRemark == null ? null : posRemark.trim();
    }

    public String getPosCheckDeductAmt() {
        return posCheckDeductAmt;
    }

    public void setPosCheckDeductAmt(String posCheckDeductAmt) {
        this.posCheckDeductAmt = posCheckDeductAmt == null ? null : posCheckDeductAmt.trim();
    }

    public String getOrgid() {
        return orgid;
    }

    public void setOrgid(String orgid) {
        this.orgid = orgid == null ? null : orgid.trim();
    }

    public BigDecimal getSettlemonth1812totaltrans() {
        return settlemonth1812totaltrans;
    }

    public void setSettlemonth1812totaltrans(BigDecimal settlemonth1812totaltrans) {
        this.settlemonth1812totaltrans = settlemonth1812totaltrans;
    }

    public BigDecimal getSettlemonthtotaltrans() {
        return settlemonthtotaltrans;
    }

    public void setSettlemonthtotaltrans(BigDecimal settlemonthtotaltrans) {
        this.settlemonthtotaltrans = settlemonthtotaltrans;
    }

    public String getIsstandard() {
        return isstandard;
    }

    public void setIsstandard(String isstandard) {
        this.isstandard = isstandard == null ? null : isstandard.trim();
    }

    public BigDecimal getTotalorder() {
        return totalorder;
    }

    public void setTotalorder(BigDecimal totalorder) {
        this.totalorder = totalorder;
    }

    public BigDecimal getYearafter19totaltrans() {
        return yearafter19totaltrans;
    }

    public void setYearafter19totaltrans(BigDecimal yearafter19totaltrans) {
        this.yearafter19totaltrans = yearafter19totaltrans;
    }

    public String getContrastmonth() {
        return contrastmonth;
    }

    public void setContrastmonth(String contrastmonth) {
        this.contrastmonth = contrastmonth == null ? null : contrastmonth.trim();
    }

    public BigDecimal getNewtransamount() {
        return newtransamount;
    }

    public void setNewtransamount(BigDecimal newtransamount) {
        this.newtransamount = newtransamount;
    }

    public BigDecimal getYearafter19credittrans() {
        return yearafter19credittrans;
    }

    public void setYearafter19credittrans(BigDecimal yearafter19credittrans) {
        this.yearafter19credittrans = yearafter19credittrans;
    }

    public BigDecimal getSettleMonthCreditTrans() {
        return settleMonthCreditTrans;
    }

    public void setSettleMonthCreditTrans(BigDecimal settleMonthCreditTrans) {
        this.settleMonthCreditTrans = settleMonthCreditTrans;
    }

    public BigDecimal getSpecialRewardStandard() {
        return specialRewardStandard;
    }

    public void setSpecialRewardStandard(BigDecimal specialRewardStandard) {
        this.specialRewardStandard = specialRewardStandard;
    }

    public BigDecimal getSpecialReward() {
        return specialReward;
    }

    public void setSpecialReward(BigDecimal specialReward) {
        this.specialReward = specialReward;
    }

    public String getAssessStatus() {
        return assessStatus;
    }

    public void setAssessStatus(String assessStatus) {
        this.assessStatus = assessStatus == null ? null : assessStatus.trim();
    }

    public BigDecimal getExecuteReward() {
        return executeReward;
    }

    public void setExecuteReward(BigDecimal executeReward) {
        this.executeReward = executeReward;
    }

    public BigDecimal getDeductedReward() {
        return deductedReward;
    }

    public void setDeductedReward(BigDecimal deductedReward) {
        this.deductedReward = deductedReward;
    }

    public BigDecimal getExecuteRewardStandard() {
        return executeRewardStandard;
    }

    public void setExecuteRewardStandard(BigDecimal executeRewardStandard) {
        this.executeRewardStandard = executeRewardStandard;
    }

    public String getChildAgentIdList() {
        return childAgentIdList;
    }

    public void setChildAgentIdList(String childAgentIdList) {
        this.childAgentIdList = childAgentIdList == null ? null : childAgentIdList.trim();
    }
}