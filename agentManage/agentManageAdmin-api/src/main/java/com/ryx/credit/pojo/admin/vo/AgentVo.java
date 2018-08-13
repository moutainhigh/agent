package com.ryx.credit.pojo.admin.vo;

import com.ryx.credit.pojo.admin.agent.Agent;
import com.ryx.credit.pojo.admin.agent.AgentColinfoRel;
import com.ryx.credit.pojo.admin.order.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Created by cx on 2018/5/28.
 */
public class AgentVo implements Serializable {

    private Agent agent;
    private List<CapitalVo> capitalVoList;
    private List<AgentContractVo> contractVoList;
    private List<AgentColinfoVo> colinfoVoList;
    private List<AgentBusInfoVo> busInfoVoList;
    private List<String> agentTableFile;
    private String agentId;
    private String submitType;
    private String agDocProString;
    private String agDocDistrictString;
    private List<AgentColinfoRel> agentColinfoRelList;
    private String approvalOpinion;
    private String approvalResult;
    private String taskId;
    private String flag;
    private String agentBusId;
    private BigDecimal realPayAmount;
    private String supplementId;

    //订单审批下个审批部门参数
    private String orderAprDept;
    private List<ORefundPriceDiffDetail> refundPriceDiffDetailList;
    private List<String> refundPriceDiffFile;
    private List<String> refundPriceDiffFinanceFile;
    private ORefundPriceDiff oRefundPriceDiff;
    private ORefundPriceDiffVo oRefundPriceDiffVo;
    private List<ODeductCapital> deductCapitalList;
    //付款单
    private Map<String, String> oPayment;

    //退货ID
    private String returnId;
    //退货排单计划
    private String plans;
    //工作流节点ID
    private String sid;
    //退货打款截图
    private String[] attachments;
    private List<Map<String,Object>> reqListMap;
    private ReceiptPlan receiptPlan;

    public Agent getAgent() {
        return agent;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }

    public List<CapitalVo> getCapitalVoList() {
        return capitalVoList;
    }

    public void setCapitalVoList(List<CapitalVo> capitalVoList) {
        this.capitalVoList = capitalVoList;
    }

    public List<AgentContractVo> getContractVoList() {
        return contractVoList;
    }

    public void setContractVoList(List<AgentContractVo> contractVoList) {
        this.contractVoList = contractVoList;
    }

    public List<AgentColinfoVo> getColinfoVoList() {
        return colinfoVoList;
    }

    public void setColinfoVoList(List<AgentColinfoVo> colinfoVoList) {
        this.colinfoVoList = colinfoVoList;
    }

    public List<AgentBusInfoVo> getBusInfoVoList() {
        return busInfoVoList;
    }

    public void setBusInfoVoList(List<AgentBusInfoVo> busInfoVoList) {
        this.busInfoVoList = busInfoVoList;
    }

    public List<String> getAgentTableFile() {
        return agentTableFile;
    }

    public void setAgentTableFile(List<String> agentTableFile) {
        this.agentTableFile = agentTableFile;
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    public String getSubmitType() {
        return submitType;
    }

    public void setSubmitType(String submitType) {
        this.submitType = submitType;
    }


    public String getAgDocProString() {
        return agDocProString;
    }

    public void setAgDocProString(String agDocProString) {
        this.agDocProString = agDocProString;
    }

    public String getAgDocDistrictString() {
        return agDocDistrictString;
    }

    public void setAgDocDistrictString(String agDocDistrictString) {
        this.agDocDistrictString = agDocDistrictString;
    }

    public List<AgentColinfoRel> getAgentColinfoRelList() {
        return agentColinfoRelList;
    }

    public void setAgentColinfoRelList(List<AgentColinfoRel> agentColinfoRelList) {
        this.agentColinfoRelList = agentColinfoRelList;
    }

    public String getApprovalOpinion() {
        return approvalOpinion;
    }

    public void setApprovalOpinion(String approvalOpinion) {
        this.approvalOpinion = approvalOpinion;
    }

    public String getApprovalResult() {
        return approvalResult;
    }

    public void setApprovalResult(String approvalResult) {
        this.approvalResult = approvalResult;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getOrderAprDept() {
        return orderAprDept;
    }

    public void setOrderAprDept(String orderAprDept) {
        this.orderAprDept = orderAprDept;
    }

    public Map<String, String> getoPayment() {
        return oPayment;
    }

    public void setoPayment(Map<String, String> oPayment) {
        this.oPayment = oPayment;
    }

    public List<ORefundPriceDiffDetail> getRefundPriceDiffDetailList() {
        return refundPriceDiffDetailList;
    }

    public void setRefundPriceDiffDetailList(List<ORefundPriceDiffDetail> refundPriceDiffDetailList) {
        this.refundPriceDiffDetailList = refundPriceDiffDetailList;
    }

    public List<String> getRefundPriceDiffFile() {
        return refundPriceDiffFile;
    }

    public void setRefundPriceDiffFile(List<String> refundPriceDiffFile) {
        this.refundPriceDiffFile = refundPriceDiffFile;
    }

    public ORefundPriceDiff getoRefundPriceDiff() {
        return oRefundPriceDiff;
    }

    public void setoRefundPriceDiff(ORefundPriceDiff oRefundPriceDiff) {
        this.oRefundPriceDiff = oRefundPriceDiff;
    }

    public List<ODeductCapital> getDeductCapitalList() {
        return deductCapitalList;
    }

    public void setDeductCapitalList(List<ODeductCapital> deductCapitalList) {
        this.deductCapitalList = deductCapitalList;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public ORefundPriceDiffVo getoRefundPriceDiffVo() {
        return oRefundPriceDiffVo;
    }

    public void setoRefundPriceDiffVo(ORefundPriceDiffVo oRefundPriceDiffVo) {
        this.oRefundPriceDiffVo = oRefundPriceDiffVo;
    }

    public String getAgentBusId() {
        return agentBusId;
    }

    public void setAgentBusId(String agentBusId) {
        this.agentBusId = agentBusId;
    }

    public String getPlans() {
        return plans;
    }

    public void setPlans(String plans) {
        this.plans = plans;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String[] getAttachments() {
        return attachments;
    }

    public void setAttachments(String[] attachments) {
        this.attachments = attachments;
    }

    public String getReturnId() {
        return returnId;
    }

    public void setReturnId(String returnId) {
        this.returnId = returnId;
    }

    public List<String> getRefundPriceDiffFinanceFile() {
        return refundPriceDiffFinanceFile;
    }

    public void setRefundPriceDiffFinanceFile(List<String> refundPriceDiffFinanceFile) {
        this.refundPriceDiffFinanceFile = refundPriceDiffFinanceFile;
    }
    public String getSupplementId() {
        return supplementId;
    }

    public void setSupplementId(String supplementId) {
        this.supplementId = supplementId;
    }

    public BigDecimal getRealPayAmount() {
        return realPayAmount;
    }

    public void setRealPayAmount(BigDecimal realPayAmount) {
        this.realPayAmount = realPayAmount;
    }

    public List<Map<String, Object>> getReqListMap() {
        return reqListMap;
    }

    public void setReqListMap(List<Map<String, Object>> reqListMap) {
        this.reqListMap = reqListMap;
    }

    public ReceiptPlan getReceiptPlan() {
        return receiptPlan;
    }

    public void setReceiptPlan(ReceiptPlan receiptPlan) {
        this.receiptPlan = receiptPlan;
    }
}
