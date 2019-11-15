package com.ryx.internet.pojo;

import com.ryx.credit.common.util.Page;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class InternetRenewOffsetDetailExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Page page;

    protected Integer limitStart;

    protected Integer limitEnd;

    protected Map<String,Object> reqMap;

    public InternetRenewOffsetDetailExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public Map<String, Object> getReqMap() {
        return reqMap;
    }

    public void setReqMap(Map<String, Object> reqMap) {
        this.reqMap = reqMap;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page=page;
    }

    public Integer getLimitStart() {
        return limitStart;
    }

    public void setLimitStart(Integer limitStart) {
        this.limitStart=limitStart;
    }

    public Integer getLimitEnd() {
        return limitEnd;
    }

    public void setLimitEnd(Integer limitEnd) {
        this.limitEnd=limitEnd;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("ID is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("ID is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(String value) {
            addCriterion("ID =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(String value) {
            addCriterion("ID <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(String value) {
            addCriterion("ID >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(String value) {
            addCriterion("ID >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(String value) {
            addCriterion("ID <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(String value) {
            addCriterion("ID <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLike(String value) {
            addCriterion("ID like", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotLike(String value) {
            addCriterion("ID not like", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<String> values) {
            addCriterion("ID in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<String> values) {
            addCriterion("ID not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(String value1, String value2) {
            addCriterion("ID between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(String value1, String value2) {
            addCriterion("ID not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andFlowIdIsNull() {
            addCriterion("FLOW_ID is null");
            return (Criteria) this;
        }

        public Criteria andFlowIdIsNotNull() {
            addCriterion("FLOW_ID is not null");
            return (Criteria) this;
        }

        public Criteria andFlowIdEqualTo(String value) {
            addCriterion("FLOW_ID =", value, "flowId");
            return (Criteria) this;
        }

        public Criteria andFlowIdNotEqualTo(String value) {
            addCriterion("FLOW_ID <>", value, "flowId");
            return (Criteria) this;
        }

        public Criteria andFlowIdGreaterThan(String value) {
            addCriterion("FLOW_ID >", value, "flowId");
            return (Criteria) this;
        }

        public Criteria andFlowIdGreaterThanOrEqualTo(String value) {
            addCriterion("FLOW_ID >=", value, "flowId");
            return (Criteria) this;
        }

        public Criteria andFlowIdLessThan(String value) {
            addCriterion("FLOW_ID <", value, "flowId");
            return (Criteria) this;
        }

        public Criteria andFlowIdLessThanOrEqualTo(String value) {
            addCriterion("FLOW_ID <=", value, "flowId");
            return (Criteria) this;
        }

        public Criteria andFlowIdLike(String value) {
            addCriterion("FLOW_ID like", value, "flowId");
            return (Criteria) this;
        }

        public Criteria andFlowIdNotLike(String value) {
            addCriterion("FLOW_ID not like", value, "flowId");
            return (Criteria) this;
        }

        public Criteria andFlowIdIn(List<String> values) {
            addCriterion("FLOW_ID in", values, "flowId");
            return (Criteria) this;
        }

        public Criteria andFlowIdNotIn(List<String> values) {
            addCriterion("FLOW_ID not in", values, "flowId");
            return (Criteria) this;
        }

        public Criteria andFlowIdBetween(String value1, String value2) {
            addCriterion("FLOW_ID between", value1, value2, "flowId");
            return (Criteria) this;
        }

        public Criteria andFlowIdNotBetween(String value1, String value2) {
            addCriterion("FLOW_ID not between", value1, value2, "flowId");
            return (Criteria) this;
        }

        public Criteria andRenewIdIsNull() {
            addCriterion("RENEW_ID is null");
            return (Criteria) this;
        }

        public Criteria andRenewIdIsNotNull() {
            addCriterion("RENEW_ID is not null");
            return (Criteria) this;
        }

        public Criteria andRenewIdEqualTo(String value) {
            addCriterion("RENEW_ID =", value, "renewId");
            return (Criteria) this;
        }

        public Criteria andRenewIdNotEqualTo(String value) {
            addCriterion("RENEW_ID <>", value, "renewId");
            return (Criteria) this;
        }

        public Criteria andRenewIdGreaterThan(String value) {
            addCriterion("RENEW_ID >", value, "renewId");
            return (Criteria) this;
        }

        public Criteria andRenewIdGreaterThanOrEqualTo(String value) {
            addCriterion("RENEW_ID >=", value, "renewId");
            return (Criteria) this;
        }

        public Criteria andRenewIdLessThan(String value) {
            addCriterion("RENEW_ID <", value, "renewId");
            return (Criteria) this;
        }

        public Criteria andRenewIdLessThanOrEqualTo(String value) {
            addCriterion("RENEW_ID <=", value, "renewId");
            return (Criteria) this;
        }

        public Criteria andRenewIdLike(String value) {
            addCriterion("RENEW_ID like", value, "renewId");
            return (Criteria) this;
        }

        public Criteria andRenewIdNotLike(String value) {
            addCriterion("RENEW_ID not like", value, "renewId");
            return (Criteria) this;
        }

        public Criteria andRenewIdIn(List<String> values) {
            addCriterion("RENEW_ID in", values, "renewId");
            return (Criteria) this;
        }

        public Criteria andRenewIdNotIn(List<String> values) {
            addCriterion("RENEW_ID not in", values, "renewId");
            return (Criteria) this;
        }

        public Criteria andRenewIdBetween(String value1, String value2) {
            addCriterion("RENEW_ID between", value1, value2, "renewId");
            return (Criteria) this;
        }

        public Criteria andRenewIdNotBetween(String value1, String value2) {
            addCriterion("RENEW_ID not between", value1, value2, "renewId");
            return (Criteria) this;
        }

        public Criteria andRenewDetailIdIsNull() {
            addCriterion("RENEW_DETAIL_ID is null");
            return (Criteria) this;
        }

        public Criteria andRenewDetailIdIsNotNull() {
            addCriterion("RENEW_DETAIL_ID is not null");
            return (Criteria) this;
        }

        public Criteria andRenewDetailIdEqualTo(String value) {
            addCriterion("RENEW_DETAIL_ID =", value, "renewDetailId");
            return (Criteria) this;
        }

        public Criteria andRenewDetailIdNotEqualTo(String value) {
            addCriterion("RENEW_DETAIL_ID <>", value, "renewDetailId");
            return (Criteria) this;
        }

        public Criteria andRenewDetailIdGreaterThan(String value) {
            addCriterion("RENEW_DETAIL_ID >", value, "renewDetailId");
            return (Criteria) this;
        }

        public Criteria andRenewDetailIdGreaterThanOrEqualTo(String value) {
            addCriterion("RENEW_DETAIL_ID >=", value, "renewDetailId");
            return (Criteria) this;
        }

        public Criteria andRenewDetailIdLessThan(String value) {
            addCriterion("RENEW_DETAIL_ID <", value, "renewDetailId");
            return (Criteria) this;
        }

        public Criteria andRenewDetailIdLessThanOrEqualTo(String value) {
            addCriterion("RENEW_DETAIL_ID <=", value, "renewDetailId");
            return (Criteria) this;
        }

        public Criteria andRenewDetailIdLike(String value) {
            addCriterion("RENEW_DETAIL_ID like", value, "renewDetailId");
            return (Criteria) this;
        }

        public Criteria andRenewDetailIdNotLike(String value) {
            addCriterion("RENEW_DETAIL_ID not like", value, "renewDetailId");
            return (Criteria) this;
        }

        public Criteria andRenewDetailIdIn(List<String> values) {
            addCriterion("RENEW_DETAIL_ID in", values, "renewDetailId");
            return (Criteria) this;
        }

        public Criteria andRenewDetailIdNotIn(List<String> values) {
            addCriterion("RENEW_DETAIL_ID not in", values, "renewDetailId");
            return (Criteria) this;
        }

        public Criteria andRenewDetailIdBetween(String value1, String value2) {
            addCriterion("RENEW_DETAIL_ID between", value1, value2, "renewDetailId");
            return (Criteria) this;
        }

        public Criteria andRenewDetailIdNotBetween(String value1, String value2) {
            addCriterion("RENEW_DETAIL_ID not between", value1, value2, "renewDetailId");
            return (Criteria) this;
        }

        public Criteria andIccidNumIsNull() {
            addCriterion("ICCID_NUM is null");
            return (Criteria) this;
        }

        public Criteria andIccidNumIsNotNull() {
            addCriterion("ICCID_NUM is not null");
            return (Criteria) this;
        }

        public Criteria andIccidNumEqualTo(String value) {
            addCriterion("ICCID_NUM =", value, "iccidNum");
            return (Criteria) this;
        }

        public Criteria andIccidNumNotEqualTo(String value) {
            addCriterion("ICCID_NUM <>", value, "iccidNum");
            return (Criteria) this;
        }

        public Criteria andIccidNumGreaterThan(String value) {
            addCriterion("ICCID_NUM >", value, "iccidNum");
            return (Criteria) this;
        }

        public Criteria andIccidNumGreaterThanOrEqualTo(String value) {
            addCriterion("ICCID_NUM >=", value, "iccidNum");
            return (Criteria) this;
        }

        public Criteria andIccidNumLessThan(String value) {
            addCriterion("ICCID_NUM <", value, "iccidNum");
            return (Criteria) this;
        }

        public Criteria andIccidNumLessThanOrEqualTo(String value) {
            addCriterion("ICCID_NUM <=", value, "iccidNum");
            return (Criteria) this;
        }

        public Criteria andIccidNumLike(String value) {
            addCriterion("ICCID_NUM like", value, "iccidNum");
            return (Criteria) this;
        }

        public Criteria andIccidNumNotLike(String value) {
            addCriterion("ICCID_NUM not like", value, "iccidNum");
            return (Criteria) this;
        }

        public Criteria andIccidNumIn(List<String> values) {
            addCriterion("ICCID_NUM in", values, "iccidNum");
            return (Criteria) this;
        }

        public Criteria andIccidNumNotIn(List<String> values) {
            addCriterion("ICCID_NUM not in", values, "iccidNum");
            return (Criteria) this;
        }

        public Criteria andIccidNumBetween(String value1, String value2) {
            addCriterion("ICCID_NUM between", value1, value2, "iccidNum");
            return (Criteria) this;
        }

        public Criteria andIccidNumNotBetween(String value1, String value2) {
            addCriterion("ICCID_NUM not between", value1, value2, "iccidNum");
            return (Criteria) this;
        }

        public Criteria andAgentIdIsNull() {
            addCriterion("AGENT_ID is null");
            return (Criteria) this;
        }

        public Criteria andAgentIdIsNotNull() {
            addCriterion("AGENT_ID is not null");
            return (Criteria) this;
        }

        public Criteria andAgentIdEqualTo(String value) {
            addCriterion("AGENT_ID =", value, "agentId");
            return (Criteria) this;
        }

        public Criteria andAgentIdNotEqualTo(String value) {
            addCriterion("AGENT_ID <>", value, "agentId");
            return (Criteria) this;
        }

        public Criteria andAgentIdGreaterThan(String value) {
            addCriterion("AGENT_ID >", value, "agentId");
            return (Criteria) this;
        }

        public Criteria andAgentIdGreaterThanOrEqualTo(String value) {
            addCriterion("AGENT_ID >=", value, "agentId");
            return (Criteria) this;
        }

        public Criteria andAgentIdLessThan(String value) {
            addCriterion("AGENT_ID <", value, "agentId");
            return (Criteria) this;
        }

        public Criteria andAgentIdLessThanOrEqualTo(String value) {
            addCriterion("AGENT_ID <=", value, "agentId");
            return (Criteria) this;
        }

        public Criteria andAgentIdLike(String value) {
            addCriterion("AGENT_ID like", value, "agentId");
            return (Criteria) this;
        }

        public Criteria andAgentIdNotLike(String value) {
            addCriterion("AGENT_ID not like", value, "agentId");
            return (Criteria) this;
        }

        public Criteria andAgentIdIn(List<String> values) {
            addCriterion("AGENT_ID in", values, "agentId");
            return (Criteria) this;
        }

        public Criteria andAgentIdNotIn(List<String> values) {
            addCriterion("AGENT_ID not in", values, "agentId");
            return (Criteria) this;
        }

        public Criteria andAgentIdBetween(String value1, String value2) {
            addCriterion("AGENT_ID between", value1, value2, "agentId");
            return (Criteria) this;
        }

        public Criteria andAgentIdNotBetween(String value1, String value2) {
            addCriterion("AGENT_ID not between", value1, value2, "agentId");
            return (Criteria) this;
        }

        public Criteria andAgentNameIsNull() {
            addCriterion("AGENT_NAME is null");
            return (Criteria) this;
        }

        public Criteria andAgentNameIsNotNull() {
            addCriterion("AGENT_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andAgentNameEqualTo(String value) {
            addCriterion("AGENT_NAME =", value, "agentName");
            return (Criteria) this;
        }

        public Criteria andAgentNameNotEqualTo(String value) {
            addCriterion("AGENT_NAME <>", value, "agentName");
            return (Criteria) this;
        }

        public Criteria andAgentNameGreaterThan(String value) {
            addCriterion("AGENT_NAME >", value, "agentName");
            return (Criteria) this;
        }

        public Criteria andAgentNameGreaterThanOrEqualTo(String value) {
            addCriterion("AGENT_NAME >=", value, "agentName");
            return (Criteria) this;
        }

        public Criteria andAgentNameLessThan(String value) {
            addCriterion("AGENT_NAME <", value, "agentName");
            return (Criteria) this;
        }

        public Criteria andAgentNameLessThanOrEqualTo(String value) {
            addCriterion("AGENT_NAME <=", value, "agentName");
            return (Criteria) this;
        }

        public Criteria andAgentNameLike(String value) {
            addCriterion("AGENT_NAME like", value, "agentName");
            return (Criteria) this;
        }

        public Criteria andAgentNameNotLike(String value) {
            addCriterion("AGENT_NAME not like", value, "agentName");
            return (Criteria) this;
        }

        public Criteria andAgentNameIn(List<String> values) {
            addCriterion("AGENT_NAME in", values, "agentName");
            return (Criteria) this;
        }

        public Criteria andAgentNameNotIn(List<String> values) {
            addCriterion("AGENT_NAME not in", values, "agentName");
            return (Criteria) this;
        }

        public Criteria andAgentNameBetween(String value1, String value2) {
            addCriterion("AGENT_NAME between", value1, value2, "agentName");
            return (Criteria) this;
        }

        public Criteria andAgentNameNotBetween(String value1, String value2) {
            addCriterion("AGENT_NAME not between", value1, value2, "agentName");
            return (Criteria) this;
        }

        public Criteria andMerIdIsNull() {
            addCriterion("MER_ID is null");
            return (Criteria) this;
        }

        public Criteria andMerIdIsNotNull() {
            addCriterion("MER_ID is not null");
            return (Criteria) this;
        }

        public Criteria andMerIdEqualTo(String value) {
            addCriterion("MER_ID =", value, "merId");
            return (Criteria) this;
        }

        public Criteria andMerIdNotEqualTo(String value) {
            addCriterion("MER_ID <>", value, "merId");
            return (Criteria) this;
        }

        public Criteria andMerIdGreaterThan(String value) {
            addCriterion("MER_ID >", value, "merId");
            return (Criteria) this;
        }

        public Criteria andMerIdGreaterThanOrEqualTo(String value) {
            addCriterion("MER_ID >=", value, "merId");
            return (Criteria) this;
        }

        public Criteria andMerIdLessThan(String value) {
            addCriterion("MER_ID <", value, "merId");
            return (Criteria) this;
        }

        public Criteria andMerIdLessThanOrEqualTo(String value) {
            addCriterion("MER_ID <=", value, "merId");
            return (Criteria) this;
        }

        public Criteria andMerIdLike(String value) {
            addCriterion("MER_ID like", value, "merId");
            return (Criteria) this;
        }

        public Criteria andMerIdNotLike(String value) {
            addCriterion("MER_ID not like", value, "merId");
            return (Criteria) this;
        }

        public Criteria andMerIdIn(List<String> values) {
            addCriterion("MER_ID in", values, "merId");
            return (Criteria) this;
        }

        public Criteria andMerIdNotIn(List<String> values) {
            addCriterion("MER_ID not in", values, "merId");
            return (Criteria) this;
        }

        public Criteria andMerIdBetween(String value1, String value2) {
            addCriterion("MER_ID between", value1, value2, "merId");
            return (Criteria) this;
        }

        public Criteria andMerIdNotBetween(String value1, String value2) {
            addCriterion("MER_ID not between", value1, value2, "merId");
            return (Criteria) this;
        }

        public Criteria andMerNameIsNull() {
            addCriterion("MER_NAME is null");
            return (Criteria) this;
        }

        public Criteria andMerNameIsNotNull() {
            addCriterion("MER_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andMerNameEqualTo(String value) {
            addCriterion("MER_NAME =", value, "merName");
            return (Criteria) this;
        }

        public Criteria andMerNameNotEqualTo(String value) {
            addCriterion("MER_NAME <>", value, "merName");
            return (Criteria) this;
        }

        public Criteria andMerNameGreaterThan(String value) {
            addCriterion("MER_NAME >", value, "merName");
            return (Criteria) this;
        }

        public Criteria andMerNameGreaterThanOrEqualTo(String value) {
            addCriterion("MER_NAME >=", value, "merName");
            return (Criteria) this;
        }

        public Criteria andMerNameLessThan(String value) {
            addCriterion("MER_NAME <", value, "merName");
            return (Criteria) this;
        }

        public Criteria andMerNameLessThanOrEqualTo(String value) {
            addCriterion("MER_NAME <=", value, "merName");
            return (Criteria) this;
        }

        public Criteria andMerNameLike(String value) {
            addCriterion("MER_NAME like", value, "merName");
            return (Criteria) this;
        }

        public Criteria andMerNameNotLike(String value) {
            addCriterion("MER_NAME not like", value, "merName");
            return (Criteria) this;
        }

        public Criteria andMerNameIn(List<String> values) {
            addCriterion("MER_NAME in", values, "merName");
            return (Criteria) this;
        }

        public Criteria andMerNameNotIn(List<String> values) {
            addCriterion("MER_NAME not in", values, "merName");
            return (Criteria) this;
        }

        public Criteria andMerNameBetween(String value1, String value2) {
            addCriterion("MER_NAME between", value1, value2, "merName");
            return (Criteria) this;
        }

        public Criteria andMerNameNotBetween(String value1, String value2) {
            addCriterion("MER_NAME not between", value1, value2, "merName");
            return (Criteria) this;
        }

        public Criteria andOffsetAmtIsNull() {
            addCriterion("OFFSET_AMT is null");
            return (Criteria) this;
        }

        public Criteria andOffsetAmtIsNotNull() {
            addCriterion("OFFSET_AMT is not null");
            return (Criteria) this;
        }

        public Criteria andOffsetAmtEqualTo(BigDecimal value) {
            addCriterion("OFFSET_AMT =", value, "offsetAmt");
            return (Criteria) this;
        }

        public Criteria andOffsetAmtNotEqualTo(BigDecimal value) {
            addCriterion("OFFSET_AMT <>", value, "offsetAmt");
            return (Criteria) this;
        }

        public Criteria andOffsetAmtGreaterThan(BigDecimal value) {
            addCriterion("OFFSET_AMT >", value, "offsetAmt");
            return (Criteria) this;
        }

        public Criteria andOffsetAmtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("OFFSET_AMT >=", value, "offsetAmt");
            return (Criteria) this;
        }

        public Criteria andOffsetAmtLessThan(BigDecimal value) {
            addCriterion("OFFSET_AMT <", value, "offsetAmt");
            return (Criteria) this;
        }

        public Criteria andOffsetAmtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("OFFSET_AMT <=", value, "offsetAmt");
            return (Criteria) this;
        }

        public Criteria andOffsetAmtIn(List<BigDecimal> values) {
            addCriterion("OFFSET_AMT in", values, "offsetAmt");
            return (Criteria) this;
        }

        public Criteria andOffsetAmtNotIn(List<BigDecimal> values) {
            addCriterion("OFFSET_AMT not in", values, "offsetAmt");
            return (Criteria) this;
        }

        public Criteria andOffsetAmtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("OFFSET_AMT between", value1, value2, "offsetAmt");
            return (Criteria) this;
        }

        public Criteria andOffsetAmtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("OFFSET_AMT not between", value1, value2, "offsetAmt");
            return (Criteria) this;
        }

        public Criteria andAlreadyOffsetAmtIsNull() {
            addCriterion("ALREADY_OFFSET_AMT is null");
            return (Criteria) this;
        }

        public Criteria andAlreadyOffsetAmtIsNotNull() {
            addCriterion("ALREADY_OFFSET_AMT is not null");
            return (Criteria) this;
        }

        public Criteria andAlreadyOffsetAmtEqualTo(BigDecimal value) {
            addCriterion("ALREADY_OFFSET_AMT =", value, "alreadyOffsetAmt");
            return (Criteria) this;
        }

        public Criteria andAlreadyOffsetAmtNotEqualTo(BigDecimal value) {
            addCriterion("ALREADY_OFFSET_AMT <>", value, "alreadyOffsetAmt");
            return (Criteria) this;
        }

        public Criteria andAlreadyOffsetAmtGreaterThan(BigDecimal value) {
            addCriterion("ALREADY_OFFSET_AMT >", value, "alreadyOffsetAmt");
            return (Criteria) this;
        }

        public Criteria andAlreadyOffsetAmtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("ALREADY_OFFSET_AMT >=", value, "alreadyOffsetAmt");
            return (Criteria) this;
        }

        public Criteria andAlreadyOffsetAmtLessThan(BigDecimal value) {
            addCriterion("ALREADY_OFFSET_AMT <", value, "alreadyOffsetAmt");
            return (Criteria) this;
        }

        public Criteria andAlreadyOffsetAmtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("ALREADY_OFFSET_AMT <=", value, "alreadyOffsetAmt");
            return (Criteria) this;
        }

        public Criteria andAlreadyOffsetAmtIn(List<BigDecimal> values) {
            addCriterion("ALREADY_OFFSET_AMT in", values, "alreadyOffsetAmt");
            return (Criteria) this;
        }

        public Criteria andAlreadyOffsetAmtNotIn(List<BigDecimal> values) {
            addCriterion("ALREADY_OFFSET_AMT not in", values, "alreadyOffsetAmt");
            return (Criteria) this;
        }

        public Criteria andAlreadyOffsetAmtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ALREADY_OFFSET_AMT between", value1, value2, "alreadyOffsetAmt");
            return (Criteria) this;
        }

        public Criteria andAlreadyOffsetAmtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ALREADY_OFFSET_AMT not between", value1, value2, "alreadyOffsetAmt");
            return (Criteria) this;
        }

        public Criteria andTodayOffsetAmtIsNull() {
            addCriterion("TODAY_OFFSET_AMT is null");
            return (Criteria) this;
        }

        public Criteria andTodayOffsetAmtIsNotNull() {
            addCriterion("TODAY_OFFSET_AMT is not null");
            return (Criteria) this;
        }

        public Criteria andTodayOffsetAmtEqualTo(BigDecimal value) {
            addCriterion("TODAY_OFFSET_AMT =", value, "todayOffsetAmt");
            return (Criteria) this;
        }

        public Criteria andTodayOffsetAmtNotEqualTo(BigDecimal value) {
            addCriterion("TODAY_OFFSET_AMT <>", value, "todayOffsetAmt");
            return (Criteria) this;
        }

        public Criteria andTodayOffsetAmtGreaterThan(BigDecimal value) {
            addCriterion("TODAY_OFFSET_AMT >", value, "todayOffsetAmt");
            return (Criteria) this;
        }

        public Criteria andTodayOffsetAmtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("TODAY_OFFSET_AMT >=", value, "todayOffsetAmt");
            return (Criteria) this;
        }

        public Criteria andTodayOffsetAmtLessThan(BigDecimal value) {
            addCriterion("TODAY_OFFSET_AMT <", value, "todayOffsetAmt");
            return (Criteria) this;
        }

        public Criteria andTodayOffsetAmtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("TODAY_OFFSET_AMT <=", value, "todayOffsetAmt");
            return (Criteria) this;
        }

        public Criteria andTodayOffsetAmtIn(List<BigDecimal> values) {
            addCriterion("TODAY_OFFSET_AMT in", values, "todayOffsetAmt");
            return (Criteria) this;
        }

        public Criteria andTodayOffsetAmtNotIn(List<BigDecimal> values) {
            addCriterion("TODAY_OFFSET_AMT not in", values, "todayOffsetAmt");
            return (Criteria) this;
        }

        public Criteria andTodayOffsetAmtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("TODAY_OFFSET_AMT between", value1, value2, "todayOffsetAmt");
            return (Criteria) this;
        }

        public Criteria andTodayOffsetAmtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("TODAY_OFFSET_AMT not between", value1, value2, "todayOffsetAmt");
            return (Criteria) this;
        }

        public Criteria andCTimeIsNull() {
            addCriterion("C_TIME is null");
            return (Criteria) this;
        }

        public Criteria andCTimeIsNotNull() {
            addCriterion("C_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andCTimeEqualTo(String value) {
            addCriterion("C_TIME =", value, "cTime");
            return (Criteria) this;
        }

        public Criteria andCTimeNotEqualTo(String value) {
            addCriterion("C_TIME <>", value, "cTime");
            return (Criteria) this;
        }

        public Criteria andCTimeGreaterThan(String value) {
            addCriterion("C_TIME >", value, "cTime");
            return (Criteria) this;
        }

        public Criteria andCTimeGreaterThanOrEqualTo(String value) {
            addCriterion("C_TIME >=", value, "cTime");
            return (Criteria) this;
        }

        public Criteria andCTimeLessThan(String value) {
            addCriterion("C_TIME <", value, "cTime");
            return (Criteria) this;
        }

        public Criteria andCTimeLessThanOrEqualTo(String value) {
            addCriterion("C_TIME <=", value, "cTime");
            return (Criteria) this;
        }

        public Criteria andCTimeLike(String value) {
            addCriterion("C_TIME like", value, "cTime");
            return (Criteria) this;
        }

        public Criteria andCTimeNotLike(String value) {
            addCriterion("C_TIME not like", value, "cTime");
            return (Criteria) this;
        }

        public Criteria andCTimeIn(List<String> values) {
            addCriterion("C_TIME in", values, "cTime");
            return (Criteria) this;
        }

        public Criteria andCTimeNotIn(List<String> values) {
            addCriterion("C_TIME not in", values, "cTime");
            return (Criteria) this;
        }

        public Criteria andCTimeBetween(String value1, String value2) {
            addCriterion("C_TIME between", value1, value2, "cTime");
            return (Criteria) this;
        }

        public Criteria andCTimeNotBetween(String value1, String value2) {
            addCriterion("C_TIME not between", value1, value2, "cTime");
            return (Criteria) this;
        }

        public Criteria andProcessDateIsNull() {
            addCriterion("PROCESS_DATE is null");
            return (Criteria) this;
        }

        public Criteria andProcessDateIsNotNull() {
            addCriterion("PROCESS_DATE is not null");
            return (Criteria) this;
        }

        public Criteria andProcessDateEqualTo(String value) {
            addCriterion("PROCESS_DATE =", value, "processDate");
            return (Criteria) this;
        }

        public Criteria andProcessDateNotEqualTo(String value) {
            addCriterion("PROCESS_DATE <>", value, "processDate");
            return (Criteria) this;
        }

        public Criteria andProcessDateGreaterThan(String value) {
            addCriterion("PROCESS_DATE >", value, "processDate");
            return (Criteria) this;
        }

        public Criteria andProcessDateGreaterThanOrEqualTo(String value) {
            addCriterion("PROCESS_DATE >=", value, "processDate");
            return (Criteria) this;
        }

        public Criteria andProcessDateLessThan(String value) {
            addCriterion("PROCESS_DATE <", value, "processDate");
            return (Criteria) this;
        }

        public Criteria andProcessDateLessThanOrEqualTo(String value) {
            addCriterion("PROCESS_DATE <=", value, "processDate");
            return (Criteria) this;
        }

        public Criteria andProcessDateLike(String value) {
            addCriterion("PROCESS_DATE like", value, "processDate");
            return (Criteria) this;
        }

        public Criteria andProcessDateNotLike(String value) {
            addCriterion("PROCESS_DATE not like", value, "processDate");
            return (Criteria) this;
        }

        public Criteria andProcessDateIn(List<String> values) {
            addCriterion("PROCESS_DATE in", values, "processDate");
            return (Criteria) this;
        }

        public Criteria andProcessDateNotIn(List<String> values) {
            addCriterion("PROCESS_DATE not in", values, "processDate");
            return (Criteria) this;
        }

        public Criteria andProcessDateBetween(String value1, String value2) {
            addCriterion("PROCESS_DATE between", value1, value2, "processDate");
            return (Criteria) this;
        }

        public Criteria andProcessDateNotBetween(String value1, String value2) {
            addCriterion("PROCESS_DATE not between", value1, value2, "processDate");
            return (Criteria) this;
        }

        public Criteria andProcessTimeIsNull() {
            addCriterion("PROCESS_TIME is null");
            return (Criteria) this;
        }

        public Criteria andProcessTimeIsNotNull() {
            addCriterion("PROCESS_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andProcessTimeEqualTo(String value) {
            addCriterion("PROCESS_TIME =", value, "processTime");
            return (Criteria) this;
        }

        public Criteria andProcessTimeNotEqualTo(String value) {
            addCriterion("PROCESS_TIME <>", value, "processTime");
            return (Criteria) this;
        }

        public Criteria andProcessTimeGreaterThan(String value) {
            addCriterion("PROCESS_TIME >", value, "processTime");
            return (Criteria) this;
        }

        public Criteria andProcessTimeGreaterThanOrEqualTo(String value) {
            addCriterion("PROCESS_TIME >=", value, "processTime");
            return (Criteria) this;
        }

        public Criteria andProcessTimeLessThan(String value) {
            addCriterion("PROCESS_TIME <", value, "processTime");
            return (Criteria) this;
        }

        public Criteria andProcessTimeLessThanOrEqualTo(String value) {
            addCriterion("PROCESS_TIME <=", value, "processTime");
            return (Criteria) this;
        }

        public Criteria andProcessTimeLike(String value) {
            addCriterion("PROCESS_TIME like", value, "processTime");
            return (Criteria) this;
        }

        public Criteria andProcessTimeNotLike(String value) {
            addCriterion("PROCESS_TIME not like", value, "processTime");
            return (Criteria) this;
        }

        public Criteria andProcessTimeIn(List<String> values) {
            addCriterion("PROCESS_TIME in", values, "processTime");
            return (Criteria) this;
        }

        public Criteria andProcessTimeNotIn(List<String> values) {
            addCriterion("PROCESS_TIME not in", values, "processTime");
            return (Criteria) this;
        }

        public Criteria andProcessTimeBetween(String value1, String value2) {
            addCriterion("PROCESS_TIME between", value1, value2, "processTime");
            return (Criteria) this;
        }

        public Criteria andProcessTimeNotBetween(String value1, String value2) {
            addCriterion("PROCESS_TIME not between", value1, value2, "processTime");
            return (Criteria) this;
        }

        public Criteria andCUserIsNull() {
            addCriterion("C_USER is null");
            return (Criteria) this;
        }

        public Criteria andCUserIsNotNull() {
            addCriterion("C_USER is not null");
            return (Criteria) this;
        }

        public Criteria andCUserEqualTo(String value) {
            addCriterion("C_USER =", value, "cUser");
            return (Criteria) this;
        }

        public Criteria andCUserNotEqualTo(String value) {
            addCriterion("C_USER <>", value, "cUser");
            return (Criteria) this;
        }

        public Criteria andCUserGreaterThan(String value) {
            addCriterion("C_USER >", value, "cUser");
            return (Criteria) this;
        }

        public Criteria andCUserGreaterThanOrEqualTo(String value) {
            addCriterion("C_USER >=", value, "cUser");
            return (Criteria) this;
        }

        public Criteria andCUserLessThan(String value) {
            addCriterion("C_USER <", value, "cUser");
            return (Criteria) this;
        }

        public Criteria andCUserLessThanOrEqualTo(String value) {
            addCriterion("C_USER <=", value, "cUser");
            return (Criteria) this;
        }

        public Criteria andCUserLike(String value) {
            addCriterion("C_USER like", value, "cUser");
            return (Criteria) this;
        }

        public Criteria andCUserNotLike(String value) {
            addCriterion("C_USER not like", value, "cUser");
            return (Criteria) this;
        }

        public Criteria andCUserIn(List<String> values) {
            addCriterion("C_USER in", values, "cUser");
            return (Criteria) this;
        }

        public Criteria andCUserNotIn(List<String> values) {
            addCriterion("C_USER not in", values, "cUser");
            return (Criteria) this;
        }

        public Criteria andCUserBetween(String value1, String value2) {
            addCriterion("C_USER between", value1, value2, "cUser");
            return (Criteria) this;
        }

        public Criteria andCUserNotBetween(String value1, String value2) {
            addCriterion("C_USER not between", value1, value2, "cUser");
            return (Criteria) this;
        }

        public Criteria andUUserIsNull() {
            addCriterion("U_USER is null");
            return (Criteria) this;
        }

        public Criteria andUUserIsNotNull() {
            addCriterion("U_USER is not null");
            return (Criteria) this;
        }

        public Criteria andUUserEqualTo(String value) {
            addCriterion("U_USER =", value, "uUser");
            return (Criteria) this;
        }

        public Criteria andUUserNotEqualTo(String value) {
            addCriterion("U_USER <>", value, "uUser");
            return (Criteria) this;
        }

        public Criteria andUUserGreaterThan(String value) {
            addCriterion("U_USER >", value, "uUser");
            return (Criteria) this;
        }

        public Criteria andUUserGreaterThanOrEqualTo(String value) {
            addCriterion("U_USER >=", value, "uUser");
            return (Criteria) this;
        }

        public Criteria andUUserLessThan(String value) {
            addCriterion("U_USER <", value, "uUser");
            return (Criteria) this;
        }

        public Criteria andUUserLessThanOrEqualTo(String value) {
            addCriterion("U_USER <=", value, "uUser");
            return (Criteria) this;
        }

        public Criteria andUUserLike(String value) {
            addCriterion("U_USER like", value, "uUser");
            return (Criteria) this;
        }

        public Criteria andUUserNotLike(String value) {
            addCriterion("U_USER not like", value, "uUser");
            return (Criteria) this;
        }

        public Criteria andUUserIn(List<String> values) {
            addCriterion("U_USER in", values, "uUser");
            return (Criteria) this;
        }

        public Criteria andUUserNotIn(List<String> values) {
            addCriterion("U_USER not in", values, "uUser");
            return (Criteria) this;
        }

        public Criteria andUUserBetween(String value1, String value2) {
            addCriterion("U_USER between", value1, value2, "uUser");
            return (Criteria) this;
        }

        public Criteria andUUserNotBetween(String value1, String value2) {
            addCriterion("U_USER not between", value1, value2, "uUser");
            return (Criteria) this;
        }

        public Criteria andCleanStatusIsNull() {
            addCriterion("CLEAN_STATUS is null");
            return (Criteria) this;
        }

        public Criteria andCleanStatusIsNotNull() {
            addCriterion("CLEAN_STATUS is not null");
            return (Criteria) this;
        }

        public Criteria andCleanStatusEqualTo(String value) {
            addCriterion("CLEAN_STATUS =", value, "cleanStatus");
            return (Criteria) this;
        }

        public Criteria andCleanStatusNotEqualTo(String value) {
            addCriterion("CLEAN_STATUS <>", value, "cleanStatus");
            return (Criteria) this;
        }

        public Criteria andCleanStatusGreaterThan(String value) {
            addCriterion("CLEAN_STATUS >", value, "cleanStatus");
            return (Criteria) this;
        }

        public Criteria andCleanStatusGreaterThanOrEqualTo(String value) {
            addCriterion("CLEAN_STATUS >=", value, "cleanStatus");
            return (Criteria) this;
        }

        public Criteria andCleanStatusLessThan(String value) {
            addCriterion("CLEAN_STATUS <", value, "cleanStatus");
            return (Criteria) this;
        }

        public Criteria andCleanStatusLessThanOrEqualTo(String value) {
            addCriterion("CLEAN_STATUS <=", value, "cleanStatus");
            return (Criteria) this;
        }

        public Criteria andCleanStatusLike(String value) {
            addCriterion("CLEAN_STATUS like", value, "cleanStatus");
            return (Criteria) this;
        }

        public Criteria andCleanStatusNotLike(String value) {
            addCriterion("CLEAN_STATUS not like", value, "cleanStatus");
            return (Criteria) this;
        }

        public Criteria andCleanStatusIn(List<String> values) {
            addCriterion("CLEAN_STATUS in", values, "cleanStatus");
            return (Criteria) this;
        }

        public Criteria andCleanStatusNotIn(List<String> values) {
            addCriterion("CLEAN_STATUS not in", values, "cleanStatus");
            return (Criteria) this;
        }

        public Criteria andCleanStatusBetween(String value1, String value2) {
            addCriterion("CLEAN_STATUS between", value1, value2, "cleanStatus");
            return (Criteria) this;
        }

        public Criteria andCleanStatusNotBetween(String value1, String value2) {
            addCriterion("CLEAN_STATUS not between", value1, value2, "cleanStatus");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("STATUS is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("STATUS is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(BigDecimal value) {
            addCriterion("STATUS =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(BigDecimal value) {
            addCriterion("STATUS <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(BigDecimal value) {
            addCriterion("STATUS >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("STATUS >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(BigDecimal value) {
            addCriterion("STATUS <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(BigDecimal value) {
            addCriterion("STATUS <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<BigDecimal> values) {
            addCriterion("STATUS in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<BigDecimal> values) {
            addCriterion("STATUS not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("STATUS between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("STATUS not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andVersionIsNull() {
            addCriterion("VERSION is null");
            return (Criteria) this;
        }

        public Criteria andVersionIsNotNull() {
            addCriterion("VERSION is not null");
            return (Criteria) this;
        }

        public Criteria andVersionEqualTo(BigDecimal value) {
            addCriterion("VERSION =", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionNotEqualTo(BigDecimal value) {
            addCriterion("VERSION <>", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionGreaterThan(BigDecimal value) {
            addCriterion("VERSION >", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("VERSION >=", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionLessThan(BigDecimal value) {
            addCriterion("VERSION <", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionLessThanOrEqualTo(BigDecimal value) {
            addCriterion("VERSION <=", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionIn(List<BigDecimal> values) {
            addCriterion("VERSION in", values, "version");
            return (Criteria) this;
        }

        public Criteria andVersionNotIn(List<BigDecimal> values) {
            addCriterion("VERSION not in", values, "version");
            return (Criteria) this;
        }

        public Criteria andVersionBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("VERSION between", value1, value2, "version");
            return (Criteria) this;
        }

        public Criteria andVersionNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("VERSION not between", value1, value2, "version");
            return (Criteria) this;
        }

        public Criteria andBusNumIsNull() {
            addCriterion("BUS_NUM is null");
            return (Criteria) this;
        }

        public Criteria andBusNumIsNotNull() {
            addCriterion("BUS_NUM is not null");
            return (Criteria) this;
        }

        public Criteria andBusNumEqualTo(String value) {
            addCriterion("BUS_NUM =", value, "busNum");
            return (Criteria) this;
        }

        public Criteria andBusNumNotEqualTo(String value) {
            addCriterion("BUS_NUM <>", value, "busNum");
            return (Criteria) this;
        }

        public Criteria andBusNumGreaterThan(String value) {
            addCriterion("BUS_NUM >", value, "busNum");
            return (Criteria) this;
        }

        public Criteria andBusNumGreaterThanOrEqualTo(String value) {
            addCriterion("BUS_NUM >=", value, "busNum");
            return (Criteria) this;
        }

        public Criteria andBusNumLessThan(String value) {
            addCriterion("BUS_NUM <", value, "busNum");
            return (Criteria) this;
        }

        public Criteria andBusNumLessThanOrEqualTo(String value) {
            addCriterion("BUS_NUM <=", value, "busNum");
            return (Criteria) this;
        }

        public Criteria andBusNumLike(String value) {
            addCriterion("BUS_NUM like", value, "busNum");
            return (Criteria) this;
        }

        public Criteria andBusNumNotLike(String value) {
            addCriterion("BUS_NUM not like", value, "busNum");
            return (Criteria) this;
        }

        public Criteria andBusNumIn(List<String> values) {
            addCriterion("BUS_NUM in", values, "busNum");
            return (Criteria) this;
        }

        public Criteria andBusNumNotIn(List<String> values) {
            addCriterion("BUS_NUM not in", values, "busNum");
            return (Criteria) this;
        }

        public Criteria andBusNumBetween(String value1, String value2) {
            addCriterion("BUS_NUM between", value1, value2, "busNum");
            return (Criteria) this;
        }

        public Criteria andBusNumNotBetween(String value1, String value2) {
            addCriterion("BUS_NUM not between", value1, value2, "busNum");
            return (Criteria) this;
        }

        public Criteria andBusPlatformIsNull() {
            addCriterion("BUS_PLATFORM is null");
            return (Criteria) this;
        }

        public Criteria andBusPlatformIsNotNull() {
            addCriterion("BUS_PLATFORM is not null");
            return (Criteria) this;
        }

        public Criteria andBusPlatformEqualTo(String value) {
            addCriterion("BUS_PLATFORM =", value, "busPlatform");
            return (Criteria) this;
        }

        public Criteria andBusPlatformNotEqualTo(String value) {
            addCriterion("BUS_PLATFORM <>", value, "busPlatform");
            return (Criteria) this;
        }

        public Criteria andBusPlatformGreaterThan(String value) {
            addCriterion("BUS_PLATFORM >", value, "busPlatform");
            return (Criteria) this;
        }

        public Criteria andBusPlatformGreaterThanOrEqualTo(String value) {
            addCriterion("BUS_PLATFORM >=", value, "busPlatform");
            return (Criteria) this;
        }

        public Criteria andBusPlatformLessThan(String value) {
            addCriterion("BUS_PLATFORM <", value, "busPlatform");
            return (Criteria) this;
        }

        public Criteria andBusPlatformLessThanOrEqualTo(String value) {
            addCriterion("BUS_PLATFORM <=", value, "busPlatform");
            return (Criteria) this;
        }

        public Criteria andBusPlatformLike(String value) {
            addCriterion("BUS_PLATFORM like", value, "busPlatform");
            return (Criteria) this;
        }

        public Criteria andBusPlatformNotLike(String value) {
            addCriterion("BUS_PLATFORM not like", value, "busPlatform");
            return (Criteria) this;
        }

        public Criteria andBusPlatformIn(List<String> values) {
            addCriterion("BUS_PLATFORM in", values, "busPlatform");
            return (Criteria) this;
        }

        public Criteria andBusPlatformNotIn(List<String> values) {
            addCriterion("BUS_PLATFORM not in", values, "busPlatform");
            return (Criteria) this;
        }

        public Criteria andBusPlatformBetween(String value1, String value2) {
            addCriterion("BUS_PLATFORM between", value1, value2, "busPlatform");
            return (Criteria) this;
        }

        public Criteria andBusPlatformNotBetween(String value1, String value2) {
            addCriterion("BUS_PLATFORM not between", value1, value2, "busPlatform");
            return (Criteria) this;
        }

        public Criteria andAgDocDistrictIsNull() {
            addCriterion("AG_DOC_DISTRICT is null");
            return (Criteria) this;
        }

        public Criteria andAgDocDistrictIsNotNull() {
            addCriterion("AG_DOC_DISTRICT is not null");
            return (Criteria) this;
        }

        public Criteria andAgDocDistrictEqualTo(String value) {
            addCriterion("AG_DOC_DISTRICT =", value, "agDocDistrict");
            return (Criteria) this;
        }

        public Criteria andAgDocDistrictNotEqualTo(String value) {
            addCriterion("AG_DOC_DISTRICT <>", value, "agDocDistrict");
            return (Criteria) this;
        }

        public Criteria andAgDocDistrictGreaterThan(String value) {
            addCriterion("AG_DOC_DISTRICT >", value, "agDocDistrict");
            return (Criteria) this;
        }

        public Criteria andAgDocDistrictGreaterThanOrEqualTo(String value) {
            addCriterion("AG_DOC_DISTRICT >=", value, "agDocDistrict");
            return (Criteria) this;
        }

        public Criteria andAgDocDistrictLessThan(String value) {
            addCriterion("AG_DOC_DISTRICT <", value, "agDocDistrict");
            return (Criteria) this;
        }

        public Criteria andAgDocDistrictLessThanOrEqualTo(String value) {
            addCriterion("AG_DOC_DISTRICT <=", value, "agDocDistrict");
            return (Criteria) this;
        }

        public Criteria andAgDocDistrictLike(String value) {
            addCriterion("AG_DOC_DISTRICT like", value, "agDocDistrict");
            return (Criteria) this;
        }

        public Criteria andAgDocDistrictNotLike(String value) {
            addCriterion("AG_DOC_DISTRICT not like", value, "agDocDistrict");
            return (Criteria) this;
        }

        public Criteria andAgDocDistrictIn(List<String> values) {
            addCriterion("AG_DOC_DISTRICT in", values, "agDocDistrict");
            return (Criteria) this;
        }

        public Criteria andAgDocDistrictNotIn(List<String> values) {
            addCriterion("AG_DOC_DISTRICT not in", values, "agDocDistrict");
            return (Criteria) this;
        }

        public Criteria andAgDocDistrictBetween(String value1, String value2) {
            addCriterion("AG_DOC_DISTRICT between", value1, value2, "agDocDistrict");
            return (Criteria) this;
        }

        public Criteria andAgDocDistrictNotBetween(String value1, String value2) {
            addCriterion("AG_DOC_DISTRICT not between", value1, value2, "agDocDistrict");
            return (Criteria) this;
        }

        public Criteria andAgDocProIsNull() {
            addCriterion("AG_DOC_PRO is null");
            return (Criteria) this;
        }

        public Criteria andAgDocProIsNotNull() {
            addCriterion("AG_DOC_PRO is not null");
            return (Criteria) this;
        }

        public Criteria andAgDocProEqualTo(String value) {
            addCriterion("AG_DOC_PRO =", value, "agDocPro");
            return (Criteria) this;
        }

        public Criteria andAgDocProNotEqualTo(String value) {
            addCriterion("AG_DOC_PRO <>", value, "agDocPro");
            return (Criteria) this;
        }

        public Criteria andAgDocProGreaterThan(String value) {
            addCriterion("AG_DOC_PRO >", value, "agDocPro");
            return (Criteria) this;
        }

        public Criteria andAgDocProGreaterThanOrEqualTo(String value) {
            addCriterion("AG_DOC_PRO >=", value, "agDocPro");
            return (Criteria) this;
        }

        public Criteria andAgDocProLessThan(String value) {
            addCriterion("AG_DOC_PRO <", value, "agDocPro");
            return (Criteria) this;
        }

        public Criteria andAgDocProLessThanOrEqualTo(String value) {
            addCriterion("AG_DOC_PRO <=", value, "agDocPro");
            return (Criteria) this;
        }

        public Criteria andAgDocProLike(String value) {
            addCriterion("AG_DOC_PRO like", value, "agDocPro");
            return (Criteria) this;
        }

        public Criteria andAgDocProNotLike(String value) {
            addCriterion("AG_DOC_PRO not like", value, "agDocPro");
            return (Criteria) this;
        }

        public Criteria andAgDocProIn(List<String> values) {
            addCriterion("AG_DOC_PRO in", values, "agDocPro");
            return (Criteria) this;
        }

        public Criteria andAgDocProNotIn(List<String> values) {
            addCriterion("AG_DOC_PRO not in", values, "agDocPro");
            return (Criteria) this;
        }

        public Criteria andAgDocProBetween(String value1, String value2) {
            addCriterion("AG_DOC_PRO between", value1, value2, "agDocPro");
            return (Criteria) this;
        }

        public Criteria andAgDocProNotBetween(String value1, String value2) {
            addCriterion("AG_DOC_PRO not between", value1, value2, "agDocPro");
            return (Criteria) this;
        }

        public Criteria andBusContactPersonIsNull() {
            addCriterion("BUS_CONTACT_PERSON is null");
            return (Criteria) this;
        }

        public Criteria andBusContactPersonIsNotNull() {
            addCriterion("BUS_CONTACT_PERSON is not null");
            return (Criteria) this;
        }

        public Criteria andBusContactPersonEqualTo(String value) {
            addCriterion("BUS_CONTACT_PERSON =", value, "busContactPerson");
            return (Criteria) this;
        }

        public Criteria andBusContactPersonNotEqualTo(String value) {
            addCriterion("BUS_CONTACT_PERSON <>", value, "busContactPerson");
            return (Criteria) this;
        }

        public Criteria andBusContactPersonGreaterThan(String value) {
            addCriterion("BUS_CONTACT_PERSON >", value, "busContactPerson");
            return (Criteria) this;
        }

        public Criteria andBusContactPersonGreaterThanOrEqualTo(String value) {
            addCriterion("BUS_CONTACT_PERSON >=", value, "busContactPerson");
            return (Criteria) this;
        }

        public Criteria andBusContactPersonLessThan(String value) {
            addCriterion("BUS_CONTACT_PERSON <", value, "busContactPerson");
            return (Criteria) this;
        }

        public Criteria andBusContactPersonLessThanOrEqualTo(String value) {
            addCriterion("BUS_CONTACT_PERSON <=", value, "busContactPerson");
            return (Criteria) this;
        }

        public Criteria andBusContactPersonLike(String value) {
            addCriterion("BUS_CONTACT_PERSON like", value, "busContactPerson");
            return (Criteria) this;
        }

        public Criteria andBusContactPersonNotLike(String value) {
            addCriterion("BUS_CONTACT_PERSON not like", value, "busContactPerson");
            return (Criteria) this;
        }

        public Criteria andBusContactPersonIn(List<String> values) {
            addCriterion("BUS_CONTACT_PERSON in", values, "busContactPerson");
            return (Criteria) this;
        }

        public Criteria andBusContactPersonNotIn(List<String> values) {
            addCriterion("BUS_CONTACT_PERSON not in", values, "busContactPerson");
            return (Criteria) this;
        }

        public Criteria andBusContactPersonBetween(String value1, String value2) {
            addCriterion("BUS_CONTACT_PERSON between", value1, value2, "busContactPerson");
            return (Criteria) this;
        }

        public Criteria andBusContactPersonNotBetween(String value1, String value2) {
            addCriterion("BUS_CONTACT_PERSON not between", value1, value2, "busContactPerson");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }
    }
}