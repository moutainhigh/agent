package com.ryx.credit.pojo.admin.agent;

import com.ryx.credit.common.util.Page;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AgentPlatFormSynExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Page page;

    protected Integer limitStart;

    protected Integer limitEnd;

    public AgentPlatFormSynExample() {
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

        public Criteria andPlatformCodeIsNull() {
            addCriterion("PLATFORM_CODE is null");
            return (Criteria) this;
        }

        public Criteria andPlatformCodeIsNotNull() {
            addCriterion("PLATFORM_CODE is not null");
            return (Criteria) this;
        }

        public Criteria andPlatformCodeEqualTo(String value) {
            addCriterion("PLATFORM_CODE =", value, "platformCode");
            return (Criteria) this;
        }

        public Criteria andPlatformCodeNotEqualTo(String value) {
            addCriterion("PLATFORM_CODE <>", value, "platformCode");
            return (Criteria) this;
        }

        public Criteria andPlatformCodeGreaterThan(String value) {
            addCriterion("PLATFORM_CODE >", value, "platformCode");
            return (Criteria) this;
        }

        public Criteria andPlatformCodeGreaterThanOrEqualTo(String value) {
            addCriterion("PLATFORM_CODE >=", value, "platformCode");
            return (Criteria) this;
        }

        public Criteria andPlatformCodeLessThan(String value) {
            addCriterion("PLATFORM_CODE <", value, "platformCode");
            return (Criteria) this;
        }

        public Criteria andPlatformCodeLessThanOrEqualTo(String value) {
            addCriterion("PLATFORM_CODE <=", value, "platformCode");
            return (Criteria) this;
        }

        public Criteria andPlatformCodeLike(String value) {
            addCriterion("PLATFORM_CODE like", value, "platformCode");
            return (Criteria) this;
        }

        public Criteria andPlatformCodeNotLike(String value) {
            addCriterion("PLATFORM_CODE not like", value, "platformCode");
            return (Criteria) this;
        }

        public Criteria andPlatformCodeIn(List<String> values) {
            addCriterion("PLATFORM_CODE in", values, "platformCode");
            return (Criteria) this;
        }

        public Criteria andPlatformCodeNotIn(List<String> values) {
            addCriterion("PLATFORM_CODE not in", values, "platformCode");
            return (Criteria) this;
        }

        public Criteria andPlatformCodeBetween(String value1, String value2) {
            addCriterion("PLATFORM_CODE between", value1, value2, "platformCode");
            return (Criteria) this;
        }

        public Criteria andPlatformCodeNotBetween(String value1, String value2) {
            addCriterion("PLATFORM_CODE not between", value1, value2, "platformCode");
            return (Criteria) this;
        }

        public Criteria andSendjsonIsNull() {
            addCriterion("SENDJSON is null");
            return (Criteria) this;
        }

        public Criteria andSendjsonIsNotNull() {
            addCriterion("SENDJSON is not null");
            return (Criteria) this;
        }

        public Criteria andSendjsonEqualTo(String value) {
            addCriterion("SENDJSON =", value, "sendjson");
            return (Criteria) this;
        }

        public Criteria andSendjsonNotEqualTo(String value) {
            addCriterion("SENDJSON <>", value, "sendjson");
            return (Criteria) this;
        }

        public Criteria andSendjsonGreaterThan(String value) {
            addCriterion("SENDJSON >", value, "sendjson");
            return (Criteria) this;
        }

        public Criteria andSendjsonGreaterThanOrEqualTo(String value) {
            addCriterion("SENDJSON >=", value, "sendjson");
            return (Criteria) this;
        }

        public Criteria andSendjsonLessThan(String value) {
            addCriterion("SENDJSON <", value, "sendjson");
            return (Criteria) this;
        }

        public Criteria andSendjsonLessThanOrEqualTo(String value) {
            addCriterion("SENDJSON <=", value, "sendjson");
            return (Criteria) this;
        }

        public Criteria andSendjsonLike(String value) {
            addCriterion("SENDJSON like", value, "sendjson");
            return (Criteria) this;
        }

        public Criteria andSendjsonNotLike(String value) {
            addCriterion("SENDJSON not like", value, "sendjson");
            return (Criteria) this;
        }

        public Criteria andSendjsonIn(List<String> values) {
            addCriterion("SENDJSON in", values, "sendjson");
            return (Criteria) this;
        }

        public Criteria andSendjsonNotIn(List<String> values) {
            addCriterion("SENDJSON not in", values, "sendjson");
            return (Criteria) this;
        }

        public Criteria andSendjsonBetween(String value1, String value2) {
            addCriterion("SENDJSON between", value1, value2, "sendjson");
            return (Criteria) this;
        }

        public Criteria andSendjsonNotBetween(String value1, String value2) {
            addCriterion("SENDJSON not between", value1, value2, "sendjson");
            return (Criteria) this;
        }

        public Criteria andNotifyjsonIsNull() {
            addCriterion("NOTIFYJSON is null");
            return (Criteria) this;
        }

        public Criteria andNotifyjsonIsNotNull() {
            addCriterion("NOTIFYJSON is not null");
            return (Criteria) this;
        }

        public Criteria andNotifyjsonEqualTo(String value) {
            addCriterion("NOTIFYJSON =", value, "notifyjson");
            return (Criteria) this;
        }

        public Criteria andNotifyjsonNotEqualTo(String value) {
            addCriterion("NOTIFYJSON <>", value, "notifyjson");
            return (Criteria) this;
        }

        public Criteria andNotifyjsonGreaterThan(String value) {
            addCriterion("NOTIFYJSON >", value, "notifyjson");
            return (Criteria) this;
        }

        public Criteria andNotifyjsonGreaterThanOrEqualTo(String value) {
            addCriterion("NOTIFYJSON >=", value, "notifyjson");
            return (Criteria) this;
        }

        public Criteria andNotifyjsonLessThan(String value) {
            addCriterion("NOTIFYJSON <", value, "notifyjson");
            return (Criteria) this;
        }

        public Criteria andNotifyjsonLessThanOrEqualTo(String value) {
            addCriterion("NOTIFYJSON <=", value, "notifyjson");
            return (Criteria) this;
        }

        public Criteria andNotifyjsonLike(String value) {
            addCriterion("NOTIFYJSON like", value, "notifyjson");
            return (Criteria) this;
        }

        public Criteria andNotifyjsonNotLike(String value) {
            addCriterion("NOTIFYJSON not like", value, "notifyjson");
            return (Criteria) this;
        }

        public Criteria andNotifyjsonIn(List<String> values) {
            addCriterion("NOTIFYJSON in", values, "notifyjson");
            return (Criteria) this;
        }

        public Criteria andNotifyjsonNotIn(List<String> values) {
            addCriterion("NOTIFYJSON not in", values, "notifyjson");
            return (Criteria) this;
        }

        public Criteria andNotifyjsonBetween(String value1, String value2) {
            addCriterion("NOTIFYJSON between", value1, value2, "notifyjson");
            return (Criteria) this;
        }

        public Criteria andNotifyjsonNotBetween(String value1, String value2) {
            addCriterion("NOTIFYJSON not between", value1, value2, "notifyjson");
            return (Criteria) this;
        }

        public Criteria andNotifystatusIsNull() {
            addCriterion("NOTIFYSTATUS is null");
            return (Criteria) this;
        }

        public Criteria andNotifystatusIsNotNull() {
            addCriterion("NOTIFYSTATUS is not null");
            return (Criteria) this;
        }

        public Criteria andNotifystatusEqualTo(BigDecimal value) {
            addCriterion("NOTIFYSTATUS =", value, "notifystatus");
            return (Criteria) this;
        }

        public Criteria andNotifystatusNotEqualTo(BigDecimal value) {
            addCriterion("NOTIFYSTATUS <>", value, "notifystatus");
            return (Criteria) this;
        }

        public Criteria andNotifystatusGreaterThan(BigDecimal value) {
            addCriterion("NOTIFYSTATUS >", value, "notifystatus");
            return (Criteria) this;
        }

        public Criteria andNotifystatusGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("NOTIFYSTATUS >=", value, "notifystatus");
            return (Criteria) this;
        }

        public Criteria andNotifystatusLessThan(BigDecimal value) {
            addCriterion("NOTIFYSTATUS <", value, "notifystatus");
            return (Criteria) this;
        }

        public Criteria andNotifystatusLessThanOrEqualTo(BigDecimal value) {
            addCriterion("NOTIFYSTATUS <=", value, "notifystatus");
            return (Criteria) this;
        }

        public Criteria andNotifystatusIn(List<BigDecimal> values) {
            addCriterion("NOTIFYSTATUS in", values, "notifystatus");
            return (Criteria) this;
        }

        public Criteria andNotifystatusNotIn(List<BigDecimal> values) {
            addCriterion("NOTIFYSTATUS not in", values, "notifystatus");
            return (Criteria) this;
        }

        public Criteria andNotifystatusBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("NOTIFYSTATUS between", value1, value2, "notifystatus");
            return (Criteria) this;
        }

        public Criteria andNotifystatusNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("NOTIFYSTATUS not between", value1, value2, "notifystatus");
            return (Criteria) this;
        }

        public Criteria andNotifytimeIsNull() {
            addCriterion("NOTIFYTIME is null");
            return (Criteria) this;
        }

        public Criteria andNotifytimeIsNotNull() {
            addCriterion("NOTIFYTIME is not null");
            return (Criteria) this;
        }

        public Criteria andNotifytimeEqualTo(Date value) {
            addCriterion("NOTIFYTIME =", value, "notifytime");
            return (Criteria) this;
        }

        public Criteria andNotifytimeNotEqualTo(Date value) {
            addCriterion("NOTIFYTIME <>", value, "notifytime");
            return (Criteria) this;
        }

        public Criteria andNotifytimeGreaterThan(Date value) {
            addCriterion("NOTIFYTIME >", value, "notifytime");
            return (Criteria) this;
        }

        public Criteria andNotifytimeGreaterThanOrEqualTo(Date value) {
            addCriterion("NOTIFYTIME >=", value, "notifytime");
            return (Criteria) this;
        }

        public Criteria andNotifytimeLessThan(Date value) {
            addCriterion("NOTIFYTIME <", value, "notifytime");
            return (Criteria) this;
        }

        public Criteria andNotifytimeLessThanOrEqualTo(Date value) {
            addCriterion("NOTIFYTIME <=", value, "notifytime");
            return (Criteria) this;
        }

        public Criteria andNotifytimeIn(List<Date> values) {
            addCriterion("NOTIFYTIME in", values, "notifytime");
            return (Criteria) this;
        }

        public Criteria andNotifytimeNotIn(List<Date> values) {
            addCriterion("NOTIFYTIME not in", values, "notifytime");
            return (Criteria) this;
        }

        public Criteria andNotifytimeBetween(Date value1, Date value2) {
            addCriterion("NOTIFYTIME between", value1, value2, "notifytime");
            return (Criteria) this;
        }

        public Criteria andNotifytimeNotBetween(Date value1, Date value2) {
            addCriterion("NOTIFYTIME not between", value1, value2, "notifytime");
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