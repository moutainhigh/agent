package com.ryx.credit.pojo.admin.agent;

import com.ryx.credit.common.util.Page;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BusActRelExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Page page;

    protected Integer limitStart;

    protected Integer limitEnd;

    public BusActRelExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
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

    public void setPage(Page page) {
        this.page=page;
    }

    public Page getPage() {
        return page;
    }

    public void setLimitStart(Integer limitStart) {
        this.limitStart=limitStart;
    }

    public Integer getLimitStart() {
        return limitStart;
    }

    public void setLimitEnd(Integer limitEnd) {
        this.limitEnd=limitEnd;
    }

    public Integer getLimitEnd() {
        return limitEnd;
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

        public Criteria andActivIdIsNull() {
            addCriterion("ACTIV_ID is null");
            return (Criteria) this;
        }

        public Criteria andActivIdIsNotNull() {
            addCriterion("ACTIV_ID is not null");
            return (Criteria) this;
        }

        public Criteria andActivIdEqualTo(String value) {
            addCriterion("ACTIV_ID =", value, "activId");
            return (Criteria) this;
        }

        public Criteria andActivIdNotEqualTo(String value) {
            addCriterion("ACTIV_ID <>", value, "activId");
            return (Criteria) this;
        }

        public Criteria andActivIdGreaterThan(String value) {
            addCriterion("ACTIV_ID >", value, "activId");
            return (Criteria) this;
        }

        public Criteria andActivIdGreaterThanOrEqualTo(String value) {
            addCriterion("ACTIV_ID >=", value, "activId");
            return (Criteria) this;
        }

        public Criteria andActivIdLessThan(String value) {
            addCriterion("ACTIV_ID <", value, "activId");
            return (Criteria) this;
        }

        public Criteria andActivIdLessThanOrEqualTo(String value) {
            addCriterion("ACTIV_ID <=", value, "activId");
            return (Criteria) this;
        }

        public Criteria andActivIdLike(String value) {
            addCriterion("ACTIV_ID like", value, "activId");
            return (Criteria) this;
        }

        public Criteria andActivIdNotLike(String value) {
            addCriterion("ACTIV_ID not like", value, "activId");
            return (Criteria) this;
        }

        public Criteria andActivIdIn(List<String> values) {
            addCriterion("ACTIV_ID in", values, "activId");
            return (Criteria) this;
        }

        public Criteria andActivIdNotIn(List<String> values) {
            addCriterion("ACTIV_ID not in", values, "activId");
            return (Criteria) this;
        }

        public Criteria andActivIdBetween(String value1, String value2) {
            addCriterion("ACTIV_ID between", value1, value2, "activId");
            return (Criteria) this;
        }

        public Criteria andActivIdNotBetween(String value1, String value2) {
            addCriterion("ACTIV_ID not between", value1, value2, "activId");
            return (Criteria) this;
        }

        public Criteria andBusIdIsNull() {
            addCriterion("BUS_ID is null");
            return (Criteria) this;
        }

        public Criteria andBusIdIsNotNull() {
            addCriterion("BUS_ID is not null");
            return (Criteria) this;
        }

        public Criteria andBusIdEqualTo(String value) {
            addCriterion("BUS_ID =", value, "busId");
            return (Criteria) this;
        }

        public Criteria andBusIdNotEqualTo(String value) {
            addCriterion("BUS_ID <>", value, "busId");
            return (Criteria) this;
        }

        public Criteria andBusIdGreaterThan(String value) {
            addCriterion("BUS_ID >", value, "busId");
            return (Criteria) this;
        }

        public Criteria andBusIdGreaterThanOrEqualTo(String value) {
            addCriterion("BUS_ID >=", value, "busId");
            return (Criteria) this;
        }

        public Criteria andBusIdLessThan(String value) {
            addCriterion("BUS_ID <", value, "busId");
            return (Criteria) this;
        }

        public Criteria andBusIdLessThanOrEqualTo(String value) {
            addCriterion("BUS_ID <=", value, "busId");
            return (Criteria) this;
        }

        public Criteria andBusIdLike(String value) {
            addCriterion("BUS_ID like", value, "busId");
            return (Criteria) this;
        }

        public Criteria andBusIdNotLike(String value) {
            addCriterion("BUS_ID not like", value, "busId");
            return (Criteria) this;
        }

        public Criteria andBusIdIn(List<String> values) {
            addCriterion("BUS_ID in", values, "busId");
            return (Criteria) this;
        }

        public Criteria andBusIdNotIn(List<String> values) {
            addCriterion("BUS_ID not in", values, "busId");
            return (Criteria) this;
        }

        public Criteria andBusIdBetween(String value1, String value2) {
            addCriterion("BUS_ID between", value1, value2, "busId");
            return (Criteria) this;
        }

        public Criteria andBusIdNotBetween(String value1, String value2) {
            addCriterion("BUS_ID not between", value1, value2, "busId");
            return (Criteria) this;
        }

        public Criteria andBusTypeIsNull() {
            addCriterion("BUS_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andBusTypeIsNotNull() {
            addCriterion("BUS_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andBusTypeEqualTo(String value) {
            addCriterion("BUS_TYPE =", value, "busType");
            return (Criteria) this;
        }

        public Criteria andBusTypeNotEqualTo(String value) {
            addCriterion("BUS_TYPE <>", value, "busType");
            return (Criteria) this;
        }

        public Criteria andBusTypeGreaterThan(String value) {
            addCriterion("BUS_TYPE >", value, "busType");
            return (Criteria) this;
        }

        public Criteria andBusTypeGreaterThanOrEqualTo(String value) {
            addCriterion("BUS_TYPE >=", value, "busType");
            return (Criteria) this;
        }

        public Criteria andBusTypeLessThan(String value) {
            addCriterion("BUS_TYPE <", value, "busType");
            return (Criteria) this;
        }

        public Criteria andBusTypeLessThanOrEqualTo(String value) {
            addCriterion("BUS_TYPE <=", value, "busType");
            return (Criteria) this;
        }

        public Criteria andBusTypeLike(String value) {
            addCriterion("BUS_TYPE like", value, "busType");
            return (Criteria) this;
        }

        public Criteria andBusTypeNotLike(String value) {
            addCriterion("BUS_TYPE not like", value, "busType");
            return (Criteria) this;
        }

        public Criteria andBusTypeIn(List<String> values) {
            addCriterion("BUS_TYPE in", values, "busType");
            return (Criteria) this;
        }

        public Criteria andBusTypeNotIn(List<String> values) {
            addCriterion("BUS_TYPE not in", values, "busType");
            return (Criteria) this;
        }

        public Criteria andBusTypeBetween(String value1, String value2) {
            addCriterion("BUS_TYPE between", value1, value2, "busType");
            return (Criteria) this;
        }

        public Criteria andBusTypeNotBetween(String value1, String value2) {
            addCriterion("BUS_TYPE not between", value1, value2, "busType");
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

        public Criteria andActivStatusIsNull() {
            addCriterion("ACTIV_STATUS is null");
            return (Criteria) this;
        }

        public Criteria andActivStatusIsNotNull() {
            addCriterion("ACTIV_STATUS is not null");
            return (Criteria) this;
        }

        public Criteria andActivStatusEqualTo(String value) {
            addCriterion("ACTIV_STATUS =", value, "activStatus");
            return (Criteria) this;
        }

        public Criteria andActivStatusNotEqualTo(String value) {
            addCriterion("ACTIV_STATUS <>", value, "activStatus");
            return (Criteria) this;
        }

        public Criteria andActivStatusGreaterThan(String value) {
            addCriterion("ACTIV_STATUS >", value, "activStatus");
            return (Criteria) this;
        }

        public Criteria andActivStatusGreaterThanOrEqualTo(String value) {
            addCriterion("ACTIV_STATUS >=", value, "activStatus");
            return (Criteria) this;
        }

        public Criteria andActivStatusLessThan(String value) {
            addCriterion("ACTIV_STATUS <", value, "activStatus");
            return (Criteria) this;
        }

        public Criteria andActivStatusLessThanOrEqualTo(String value) {
            addCriterion("ACTIV_STATUS <=", value, "activStatus");
            return (Criteria) this;
        }

        public Criteria andActivStatusLike(String value) {
            addCriterion("ACTIV_STATUS like", value, "activStatus");
            return (Criteria) this;
        }

        public Criteria andActivStatusNotLike(String value) {
            addCriterion("ACTIV_STATUS not like", value, "activStatus");
            return (Criteria) this;
        }

        public Criteria andActivStatusIn(List<String> values) {
            addCriterion("ACTIV_STATUS in", values, "activStatus");
            return (Criteria) this;
        }

        public Criteria andActivStatusNotIn(List<String> values) {
            addCriterion("ACTIV_STATUS not in", values, "activStatus");
            return (Criteria) this;
        }

        public Criteria andActivStatusBetween(String value1, String value2) {
            addCriterion("ACTIV_STATUS between", value1, value2, "activStatus");
            return (Criteria) this;
        }

        public Criteria andActivStatusNotBetween(String value1, String value2) {
            addCriterion("ACTIV_STATUS not between", value1, value2, "activStatus");
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

        public Criteria andNetInBusTypeIsNull() {
            addCriterion("NET_IN_BUS_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andNetInBusTypeIsNotNull() {
            addCriterion("NET_IN_BUS_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andNetInBusTypeEqualTo(String value) {
            addCriterion("NET_IN_BUS_TYPE =", value, "netInBusType");
            return (Criteria) this;
        }

        public Criteria andNetInBusTypeNotEqualTo(String value) {
            addCriterion("NET_IN_BUS_TYPE <>", value, "netInBusType");
            return (Criteria) this;
        }

        public Criteria andNetInBusTypeGreaterThan(String value) {
            addCriterion("NET_IN_BUS_TYPE >", value, "netInBusType");
            return (Criteria) this;
        }

        public Criteria andNetInBusTypeGreaterThanOrEqualTo(String value) {
            addCriterion("NET_IN_BUS_TYPE >=", value, "netInBusType");
            return (Criteria) this;
        }

        public Criteria andNetInBusTypeLessThan(String value) {
            addCriterion("NET_IN_BUS_TYPE <", value, "netInBusType");
            return (Criteria) this;
        }

        public Criteria andNetInBusTypeLessThanOrEqualTo(String value) {
            addCriterion("NET_IN_BUS_TYPE <=", value, "netInBusType");
            return (Criteria) this;
        }

        public Criteria andNetInBusTypeLike(String value) {
            addCriterion("NET_IN_BUS_TYPE like", value, "netInBusType");
            return (Criteria) this;
        }

        public Criteria andNetInBusTypeNotLike(String value) {
            addCriterion("NET_IN_BUS_TYPE not like", value, "netInBusType");
            return (Criteria) this;
        }

        public Criteria andNetInBusTypeIn(List<String> values) {
            addCriterion("NET_IN_BUS_TYPE in", values, "netInBusType");
            return (Criteria) this;
        }

        public Criteria andNetInBusTypeNotIn(List<String> values) {
            addCriterion("NET_IN_BUS_TYPE not in", values, "netInBusType");
            return (Criteria) this;
        }

        public Criteria andNetInBusTypeBetween(String value1, String value2) {
            addCriterion("NET_IN_BUS_TYPE between", value1, value2, "netInBusType");
            return (Criteria) this;
        }

        public Criteria andNetInBusTypeNotBetween(String value1, String value2) {
            addCriterion("NET_IN_BUS_TYPE not between", value1, value2, "netInBusType");
            return (Criteria) this;
        }

        public Criteria andDataShiroIsNull() {
            addCriterion("DATA_SHIRO is null");
            return (Criteria) this;
        }

        public Criteria andDataShiroIsNotNull() {
            addCriterion("DATA_SHIRO is not null");
            return (Criteria) this;
        }

        public Criteria andDataShiroEqualTo(String value) {
            addCriterion("DATA_SHIRO =", value, "dataShiro");
            return (Criteria) this;
        }

        public Criteria andDataShiroNotEqualTo(String value) {
            addCriterion("DATA_SHIRO <>", value, "dataShiro");
            return (Criteria) this;
        }

        public Criteria andDataShiroGreaterThan(String value) {
            addCriterion("DATA_SHIRO >", value, "dataShiro");
            return (Criteria) this;
        }

        public Criteria andDataShiroGreaterThanOrEqualTo(String value) {
            addCriterion("DATA_SHIRO >=", value, "dataShiro");
            return (Criteria) this;
        }

        public Criteria andDataShiroLessThan(String value) {
            addCriterion("DATA_SHIRO <", value, "dataShiro");
            return (Criteria) this;
        }

        public Criteria andDataShiroLessThanOrEqualTo(String value) {
            addCriterion("DATA_SHIRO <=", value, "dataShiro");
            return (Criteria) this;
        }

        public Criteria andDataShiroLike(String value) {
            addCriterion("DATA_SHIRO like", value, "dataShiro");
            return (Criteria) this;
        }

        public Criteria andDataShiroNotLike(String value) {
            addCriterion("DATA_SHIRO not like", value, "dataShiro");
            return (Criteria) this;
        }

        public Criteria andDataShiroIn(List<String> values) {
            addCriterion("DATA_SHIRO in", values, "dataShiro");
            return (Criteria) this;
        }

        public Criteria andDataShiroNotIn(List<String> values) {
            addCriterion("DATA_SHIRO not in", values, "dataShiro");
            return (Criteria) this;
        }

        public Criteria andDataShiroBetween(String value1, String value2) {
            addCriterion("DATA_SHIRO between", value1, value2, "dataShiro");
            return (Criteria) this;
        }

        public Criteria andDataShiroNotBetween(String value1, String value2) {
            addCriterion("DATA_SHIRO not between", value1, value2, "dataShiro");
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

        public Criteria andExplainIsNull() {
            addCriterion("EXPLAIN is null");
            return (Criteria) this;
        }

        public Criteria andExplainIsNotNull() {
            addCriterion("EXPLAIN is not null");
            return (Criteria) this;
        }

        public Criteria andExplainEqualTo(String value) {
            addCriterion("EXPLAIN =", value, "explain");
            return (Criteria) this;
        }

        public Criteria andExplainNotEqualTo(String value) {
            addCriterion("EXPLAIN <>", value, "explain");
            return (Criteria) this;
        }

        public Criteria andExplainGreaterThan(String value) {
            addCriterion("EXPLAIN >", value, "explain");
            return (Criteria) this;
        }

        public Criteria andExplainGreaterThanOrEqualTo(String value) {
            addCriterion("EXPLAIN >=", value, "explain");
            return (Criteria) this;
        }

        public Criteria andExplainLessThan(String value) {
            addCriterion("EXPLAIN <", value, "explain");
            return (Criteria) this;
        }

        public Criteria andExplainLessThanOrEqualTo(String value) {
            addCriterion("EXPLAIN <=", value, "explain");
            return (Criteria) this;
        }

        public Criteria andExplainLike(String value) {
            addCriterion("EXPLAIN like", value, "explain");
            return (Criteria) this;
        }

        public Criteria andExplainNotLike(String value) {
            addCriterion("EXPLAIN not like", value, "explain");
            return (Criteria) this;
        }

        public Criteria andExplainIn(List<String> values) {
            addCriterion("EXPLAIN in", values, "explain");
            return (Criteria) this;
        }

        public Criteria andExplainNotIn(List<String> values) {
            addCriterion("EXPLAIN not in", values, "explain");
            return (Criteria) this;
        }

        public Criteria andExplainBetween(String value1, String value2) {
            addCriterion("EXPLAIN between", value1, value2, "explain");
            return (Criteria) this;
        }

        public Criteria andExplainNotBetween(String value1, String value2) {
            addCriterion("EXPLAIN not between", value1, value2, "explain");
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
    }
}