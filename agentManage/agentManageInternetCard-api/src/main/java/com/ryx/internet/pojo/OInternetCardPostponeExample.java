package com.ryx.internet.pojo;

import com.ryx.credit.common.util.Page;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class OInternetCardPostponeExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Page page;

    protected Integer limitStart;

    protected Integer limitEnd;

    protected Map<String,Object> reqMap;

    public OInternetCardPostponeExample() {
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

        public Criteria andIccidIsNull() {
            addCriterion("ICCID is null");
            return (Criteria) this;
        }

        public Criteria andIccidIsNotNull() {
            addCriterion("ICCID is not null");
            return (Criteria) this;
        }

        public Criteria andIccidEqualTo(String value) {
            addCriterion("ICCID =", value, "iccid");
            return (Criteria) this;
        }

        public Criteria andIccidNotEqualTo(String value) {
            addCriterion("ICCID <>", value, "iccid");
            return (Criteria) this;
        }

        public Criteria andIccidGreaterThan(String value) {
            addCriterion("ICCID >", value, "iccid");
            return (Criteria) this;
        }

        public Criteria andIccidGreaterThanOrEqualTo(String value) {
            addCriterion("ICCID >=", value, "iccid");
            return (Criteria) this;
        }

        public Criteria andIccidLessThan(String value) {
            addCriterion("ICCID <", value, "iccid");
            return (Criteria) this;
        }

        public Criteria andIccidLessThanOrEqualTo(String value) {
            addCriterion("ICCID <=", value, "iccid");
            return (Criteria) this;
        }

        public Criteria andIccidLike(String value) {
            addCriterion("ICCID like", value, "iccid");
            return (Criteria) this;
        }

        public Criteria andIccidNotLike(String value) {
            addCriterion("ICCID not like", value, "iccid");
            return (Criteria) this;
        }

        public Criteria andIccidIn(List<String> values) {
            addCriterion("ICCID in", values, "iccid");
            return (Criteria) this;
        }

        public Criteria andIccidNotIn(List<String> values) {
            addCriterion("ICCID not in", values, "iccid");
            return (Criteria) this;
        }

        public Criteria andIccidBetween(String value1, String value2) {
            addCriterion("ICCID between", value1, value2, "iccid");
            return (Criteria) this;
        }

        public Criteria andIccidNotBetween(String value1, String value2) {
            addCriterion("ICCID not between", value1, value2, "iccid");
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

        public Criteria andOrderIdIsNull() {
            addCriterion("ORDER_ID is null");
            return (Criteria) this;
        }

        public Criteria andOrderIdIsNotNull() {
            addCriterion("ORDER_ID is not null");
            return (Criteria) this;
        }

        public Criteria andOrderIdEqualTo(String value) {
            addCriterion("ORDER_ID =", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdNotEqualTo(String value) {
            addCriterion("ORDER_ID <>", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdGreaterThan(String value) {
            addCriterion("ORDER_ID >", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdGreaterThanOrEqualTo(String value) {
            addCriterion("ORDER_ID >=", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdLessThan(String value) {
            addCriterion("ORDER_ID <", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdLessThanOrEqualTo(String value) {
            addCriterion("ORDER_ID <=", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdLike(String value) {
            addCriterion("ORDER_ID like", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdNotLike(String value) {
            addCriterion("ORDER_ID not like", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdIn(List<String> values) {
            addCriterion("ORDER_ID in", values, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdNotIn(List<String> values) {
            addCriterion("ORDER_ID not in", values, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdBetween(String value1, String value2) {
            addCriterion("ORDER_ID between", value1, value2, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdNotBetween(String value1, String value2) {
            addCriterion("ORDER_ID not between", value1, value2, "orderId");
            return (Criteria) this;
        }

        public Criteria andOpenAccountTimeIsNull() {
            addCriterion("OPEN_ACCOUNT_TIME is null");
            return (Criteria) this;
        }

        public Criteria andOpenAccountTimeIsNotNull() {
            addCriterion("OPEN_ACCOUNT_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andOpenAccountTimeEqualTo(Date value) {
            addCriterion("OPEN_ACCOUNT_TIME =", value, "openAccountTime");
            return (Criteria) this;
        }

        public Criteria andOpenAccountTimeNotEqualTo(Date value) {
            addCriterion("OPEN_ACCOUNT_TIME <>", value, "openAccountTime");
            return (Criteria) this;
        }

        public Criteria andOpenAccountTimeGreaterThan(Date value) {
            addCriterion("OPEN_ACCOUNT_TIME >", value, "openAccountTime");
            return (Criteria) this;
        }

        public Criteria andOpenAccountTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("OPEN_ACCOUNT_TIME >=", value, "openAccountTime");
            return (Criteria) this;
        }

        public Criteria andOpenAccountTimeLessThan(Date value) {
            addCriterion("OPEN_ACCOUNT_TIME <", value, "openAccountTime");
            return (Criteria) this;
        }

        public Criteria andOpenAccountTimeLessThanOrEqualTo(Date value) {
            addCriterion("OPEN_ACCOUNT_TIME <=", value, "openAccountTime");
            return (Criteria) this;
        }

        public Criteria andOpenAccountTimeIn(List<Date> values) {
            addCriterion("OPEN_ACCOUNT_TIME in", values, "openAccountTime");
            return (Criteria) this;
        }

        public Criteria andOpenAccountTimeNotIn(List<Date> values) {
            addCriterion("OPEN_ACCOUNT_TIME not in", values, "openAccountTime");
            return (Criteria) this;
        }

        public Criteria andOpenAccountTimeBetween(Date value1, Date value2) {
            addCriterion("OPEN_ACCOUNT_TIME between", value1, value2, "openAccountTime");
            return (Criteria) this;
        }

        public Criteria andOpenAccountTimeNotBetween(Date value1, Date value2) {
            addCriterion("OPEN_ACCOUNT_TIME not between", value1, value2, "openAccountTime");
            return (Criteria) this;
        }

        public Criteria andPostponeTimeIsNull() {
            addCriterion("POSTPONE_TIME is null");
            return (Criteria) this;
        }

        public Criteria andPostponeTimeIsNotNull() {
            addCriterion("POSTPONE_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andPostponeTimeEqualTo(BigDecimal value) {
            addCriterion("POSTPONE_TIME =", value, "postponeTime");
            return (Criteria) this;
        }

        public Criteria andPostponeTimeNotEqualTo(BigDecimal value) {
            addCriterion("POSTPONE_TIME <>", value, "postponeTime");
            return (Criteria) this;
        }

        public Criteria andPostponeTimeGreaterThan(BigDecimal value) {
            addCriterion("POSTPONE_TIME >", value, "postponeTime");
            return (Criteria) this;
        }

        public Criteria andPostponeTimeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("POSTPONE_TIME >=", value, "postponeTime");
            return (Criteria) this;
        }

        public Criteria andPostponeTimeLessThan(BigDecimal value) {
            addCriterion("POSTPONE_TIME <", value, "postponeTime");
            return (Criteria) this;
        }

        public Criteria andPostponeTimeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("POSTPONE_TIME <=", value, "postponeTime");
            return (Criteria) this;
        }

        public Criteria andPostponeTimeIn(List<BigDecimal> values) {
            addCriterion("POSTPONE_TIME in", values, "postponeTime");
            return (Criteria) this;
        }

        public Criteria andPostponeTimeNotIn(List<BigDecimal> values) {
            addCriterion("POSTPONE_TIME not in", values, "postponeTime");
            return (Criteria) this;
        }

        public Criteria andPostponeTimeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("POSTPONE_TIME between", value1, value2, "postponeTime");
            return (Criteria) this;
        }

        public Criteria andPostponeTimeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("POSTPONE_TIME not between", value1, value2, "postponeTime");
            return (Criteria) this;
        }

        public Criteria andExpireTimeIsNull() {
            addCriterion("EXPIRE_TIME is null");
            return (Criteria) this;
        }

        public Criteria andExpireTimeIsNotNull() {
            addCriterion("EXPIRE_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andExpireTimeEqualTo(Date value) {
            addCriterion("EXPIRE_TIME =", value, "expireTime");
            return (Criteria) this;
        }

        public Criteria andExpireTimeNotEqualTo(Date value) {
            addCriterion("EXPIRE_TIME <>", value, "expireTime");
            return (Criteria) this;
        }

        public Criteria andExpireTimeGreaterThan(Date value) {
            addCriterion("EXPIRE_TIME >", value, "expireTime");
            return (Criteria) this;
        }

        public Criteria andExpireTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("EXPIRE_TIME >=", value, "expireTime");
            return (Criteria) this;
        }

        public Criteria andExpireTimeLessThan(Date value) {
            addCriterion("EXPIRE_TIME <", value, "expireTime");
            return (Criteria) this;
        }

        public Criteria andExpireTimeLessThanOrEqualTo(Date value) {
            addCriterion("EXPIRE_TIME <=", value, "expireTime");
            return (Criteria) this;
        }

        public Criteria andExpireTimeIn(List<Date> values) {
            addCriterion("EXPIRE_TIME in", values, "expireTime");
            return (Criteria) this;
        }

        public Criteria andExpireTimeNotIn(List<Date> values) {
            addCriterion("EXPIRE_TIME not in", values, "expireTime");
            return (Criteria) this;
        }

        public Criteria andExpireTimeBetween(Date value1, Date value2) {
            addCriterion("EXPIRE_TIME between", value1, value2, "expireTime");
            return (Criteria) this;
        }

        public Criteria andExpireTimeNotBetween(Date value1, Date value2) {
            addCriterion("EXPIRE_TIME not between", value1, value2, "expireTime");
            return (Criteria) this;
        }

        public Criteria andPostponeExpireTimeIsNull() {
            addCriterion("POSTPONE_EXPIRE_TIME is null");
            return (Criteria) this;
        }

        public Criteria andPostponeExpireTimeIsNotNull() {
            addCriterion("POSTPONE_EXPIRE_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andPostponeExpireTimeEqualTo(Date value) {
            addCriterion("POSTPONE_EXPIRE_TIME =", value, "postponeExpireTime");
            return (Criteria) this;
        }

        public Criteria andPostponeExpireTimeNotEqualTo(Date value) {
            addCriterion("POSTPONE_EXPIRE_TIME <>", value, "postponeExpireTime");
            return (Criteria) this;
        }

        public Criteria andPostponeExpireTimeGreaterThan(Date value) {
            addCriterion("POSTPONE_EXPIRE_TIME >", value, "postponeExpireTime");
            return (Criteria) this;
        }

        public Criteria andPostponeExpireTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("POSTPONE_EXPIRE_TIME >=", value, "postponeExpireTime");
            return (Criteria) this;
        }

        public Criteria andPostponeExpireTimeLessThan(Date value) {
            addCriterion("POSTPONE_EXPIRE_TIME <", value, "postponeExpireTime");
            return (Criteria) this;
        }

        public Criteria andPostponeExpireTimeLessThanOrEqualTo(Date value) {
            addCriterion("POSTPONE_EXPIRE_TIME <=", value, "postponeExpireTime");
            return (Criteria) this;
        }

        public Criteria andPostponeExpireTimeIn(List<Date> values) {
            addCriterion("POSTPONE_EXPIRE_TIME in", values, "postponeExpireTime");
            return (Criteria) this;
        }

        public Criteria andPostponeExpireTimeNotIn(List<Date> values) {
            addCriterion("POSTPONE_EXPIRE_TIME not in", values, "postponeExpireTime");
            return (Criteria) this;
        }

        public Criteria andPostponeExpireTimeBetween(Date value1, Date value2) {
            addCriterion("POSTPONE_EXPIRE_TIME between", value1, value2, "postponeExpireTime");
            return (Criteria) this;
        }

        public Criteria andPostponeExpireTimeNotBetween(Date value1, Date value2) {
            addCriterion("POSTPONE_EXPIRE_TIME not between", value1, value2, "postponeExpireTime");
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

        public Criteria andCTimeIsNull() {
            addCriterion("C_TIME is null");
            return (Criteria) this;
        }

        public Criteria andCTimeIsNotNull() {
            addCriterion("C_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andCTimeEqualTo(Date value) {
            addCriterion("C_TIME =", value, "cTime");
            return (Criteria) this;
        }

        public Criteria andCTimeNotEqualTo(Date value) {
            addCriterion("C_TIME <>", value, "cTime");
            return (Criteria) this;
        }

        public Criteria andCTimeGreaterThan(Date value) {
            addCriterion("C_TIME >", value, "cTime");
            return (Criteria) this;
        }

        public Criteria andCTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("C_TIME >=", value, "cTime");
            return (Criteria) this;
        }

        public Criteria andCTimeLessThan(Date value) {
            addCriterion("C_TIME <", value, "cTime");
            return (Criteria) this;
        }

        public Criteria andCTimeLessThanOrEqualTo(Date value) {
            addCriterion("C_TIME <=", value, "cTime");
            return (Criteria) this;
        }

        public Criteria andCTimeIn(List<Date> values) {
            addCriterion("C_TIME in", values, "cTime");
            return (Criteria) this;
        }

        public Criteria andCTimeNotIn(List<Date> values) {
            addCriterion("C_TIME not in", values, "cTime");
            return (Criteria) this;
        }

        public Criteria andCTimeBetween(Date value1, Date value2) {
            addCriterion("C_TIME between", value1, value2, "cTime");
            return (Criteria) this;
        }

        public Criteria andCTimeNotBetween(Date value1, Date value2) {
            addCriterion("C_TIME not between", value1, value2, "cTime");
            return (Criteria) this;
        }

        public Criteria andUTimeIsNull() {
            addCriterion("U_TIME is null");
            return (Criteria) this;
        }

        public Criteria andUTimeIsNotNull() {
            addCriterion("U_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andUTimeEqualTo(Date value) {
            addCriterion("U_TIME =", value, "uTime");
            return (Criteria) this;
        }

        public Criteria andUTimeNotEqualTo(Date value) {
            addCriterion("U_TIME <>", value, "uTime");
            return (Criteria) this;
        }

        public Criteria andUTimeGreaterThan(Date value) {
            addCriterion("U_TIME >", value, "uTime");
            return (Criteria) this;
        }

        public Criteria andUTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("U_TIME >=", value, "uTime");
            return (Criteria) this;
        }

        public Criteria andUTimeLessThan(Date value) {
            addCriterion("U_TIME <", value, "uTime");
            return (Criteria) this;
        }

        public Criteria andUTimeLessThanOrEqualTo(Date value) {
            addCriterion("U_TIME <=", value, "uTime");
            return (Criteria) this;
        }

        public Criteria andUTimeIn(List<Date> values) {
            addCriterion("U_TIME in", values, "uTime");
            return (Criteria) this;
        }

        public Criteria andUTimeNotIn(List<Date> values) {
            addCriterion("U_TIME not in", values, "uTime");
            return (Criteria) this;
        }

        public Criteria andUTimeBetween(Date value1, Date value2) {
            addCriterion("U_TIME between", value1, value2, "uTime");
            return (Criteria) this;
        }

        public Criteria andUTimeNotBetween(Date value1, Date value2) {
            addCriterion("U_TIME not between", value1, value2, "uTime");
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

        public Criteria andBatchNumIsNull() {
            addCriterion("BATCH_NUM is null");
            return (Criteria) this;
        }

        public Criteria andBatchNumIsNotNull() {
            addCriterion("BATCH_NUM is not null");
            return (Criteria) this;
        }

        public Criteria andBatchNumEqualTo(String value) {
            addCriterion("BATCH_NUM =", value, "batchNum");
            return (Criteria) this;
        }

        public Criteria andBatchNumNotEqualTo(String value) {
            addCriterion("BATCH_NUM <>", value, "batchNum");
            return (Criteria) this;
        }

        public Criteria andBatchNumGreaterThan(String value) {
            addCriterion("BATCH_NUM >", value, "batchNum");
            return (Criteria) this;
        }

        public Criteria andBatchNumGreaterThanOrEqualTo(String value) {
            addCriterion("BATCH_NUM >=", value, "batchNum");
            return (Criteria) this;
        }

        public Criteria andBatchNumLessThan(String value) {
            addCriterion("BATCH_NUM <", value, "batchNum");
            return (Criteria) this;
        }

        public Criteria andBatchNumLessThanOrEqualTo(String value) {
            addCriterion("BATCH_NUM <=", value, "batchNum");
            return (Criteria) this;
        }

        public Criteria andBatchNumLike(String value) {
            addCriterion("BATCH_NUM like", value, "batchNum");
            return (Criteria) this;
        }

        public Criteria andBatchNumNotLike(String value) {
            addCriterion("BATCH_NUM not like", value, "batchNum");
            return (Criteria) this;
        }

        public Criteria andBatchNumIn(List<String> values) {
            addCriterion("BATCH_NUM in", values, "batchNum");
            return (Criteria) this;
        }

        public Criteria andBatchNumNotIn(List<String> values) {
            addCriterion("BATCH_NUM not in", values, "batchNum");
            return (Criteria) this;
        }

        public Criteria andBatchNumBetween(String value1, String value2) {
            addCriterion("BATCH_NUM between", value1, value2, "batchNum");
            return (Criteria) this;
        }

        public Criteria andBatchNumNotBetween(String value1, String value2) {
            addCriterion("BATCH_NUM not between", value1, value2, "batchNum");
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

        public Criteria andPostponeCauseIsNull() {
            addCriterion("POSTPONE_CAUSE is null");
            return (Criteria) this;
        }

        public Criteria andPostponeCauseIsNotNull() {
            addCriterion("POSTPONE_CAUSE is not null");
            return (Criteria) this;
        }

        public Criteria andPostponeCauseEqualTo(String value) {
            addCriterion("POSTPONE_CAUSE =", value, "postponeCause");
            return (Criteria) this;
        }

        public Criteria andPostponeCauseNotEqualTo(String value) {
            addCriterion("POSTPONE_CAUSE <>", value, "postponeCause");
            return (Criteria) this;
        }

        public Criteria andPostponeCauseGreaterThan(String value) {
            addCriterion("POSTPONE_CAUSE >", value, "postponeCause");
            return (Criteria) this;
        }

        public Criteria andPostponeCauseGreaterThanOrEqualTo(String value) {
            addCriterion("POSTPONE_CAUSE >=", value, "postponeCause");
            return (Criteria) this;
        }

        public Criteria andPostponeCauseLessThan(String value) {
            addCriterion("POSTPONE_CAUSE <", value, "postponeCause");
            return (Criteria) this;
        }

        public Criteria andPostponeCauseLessThanOrEqualTo(String value) {
            addCriterion("POSTPONE_CAUSE <=", value, "postponeCause");
            return (Criteria) this;
        }

        public Criteria andPostponeCauseLike(String value) {
            addCriterion("POSTPONE_CAUSE like", value, "postponeCause");
            return (Criteria) this;
        }

        public Criteria andPostponeCauseNotLike(String value) {
            addCriterion("POSTPONE_CAUSE not like", value, "postponeCause");
            return (Criteria) this;
        }

        public Criteria andPostponeCauseIn(List<String> values) {
            addCriterion("POSTPONE_CAUSE in", values, "postponeCause");
            return (Criteria) this;
        }

        public Criteria andPostponeCauseNotIn(List<String> values) {
            addCriterion("POSTPONE_CAUSE not in", values, "postponeCause");
            return (Criteria) this;
        }

        public Criteria andPostponeCauseBetween(String value1, String value2) {
            addCriterion("POSTPONE_CAUSE between", value1, value2, "postponeCause");
            return (Criteria) this;
        }

        public Criteria andPostponeCauseNotBetween(String value1, String value2) {
            addCriterion("POSTPONE_CAUSE not between", value1, value2, "postponeCause");
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