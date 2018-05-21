package com.ryx.credit.pojo.admin.agent;

import com.ryx.credit.common.util.Page;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AssProtoColRelExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Page page;

    protected Integer limitStart;

    protected Integer limitEnd;

    public AssProtoColRelExample() {
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

        public Criteria andAssProtocolIdIsNull() {
            addCriterion("ASS_PROTOCOL_ID is null");
            return (Criteria) this;
        }

        public Criteria andAssProtocolIdIsNotNull() {
            addCriterion("ASS_PROTOCOL_ID is not null");
            return (Criteria) this;
        }

        public Criteria andAssProtocolIdEqualTo(String value) {
            addCriterion("ASS_PROTOCOL_ID =", value, "assProtocolId");
            return (Criteria) this;
        }

        public Criteria andAssProtocolIdNotEqualTo(String value) {
            addCriterion("ASS_PROTOCOL_ID <>", value, "assProtocolId");
            return (Criteria) this;
        }

        public Criteria andAssProtocolIdGreaterThan(String value) {
            addCriterion("ASS_PROTOCOL_ID >", value, "assProtocolId");
            return (Criteria) this;
        }

        public Criteria andAssProtocolIdGreaterThanOrEqualTo(String value) {
            addCriterion("ASS_PROTOCOL_ID >=", value, "assProtocolId");
            return (Criteria) this;
        }

        public Criteria andAssProtocolIdLessThan(String value) {
            addCriterion("ASS_PROTOCOL_ID <", value, "assProtocolId");
            return (Criteria) this;
        }

        public Criteria andAssProtocolIdLessThanOrEqualTo(String value) {
            addCriterion("ASS_PROTOCOL_ID <=", value, "assProtocolId");
            return (Criteria) this;
        }

        public Criteria andAssProtocolIdLike(String value) {
            addCriterion("ASS_PROTOCOL_ID like", value, "assProtocolId");
            return (Criteria) this;
        }

        public Criteria andAssProtocolIdNotLike(String value) {
            addCriterion("ASS_PROTOCOL_ID not like", value, "assProtocolId");
            return (Criteria) this;
        }

        public Criteria andAssProtocolIdIn(List<String> values) {
            addCriterion("ASS_PROTOCOL_ID in", values, "assProtocolId");
            return (Criteria) this;
        }

        public Criteria andAssProtocolIdNotIn(List<String> values) {
            addCriterion("ASS_PROTOCOL_ID not in", values, "assProtocolId");
            return (Criteria) this;
        }

        public Criteria andAssProtocolIdBetween(String value1, String value2) {
            addCriterion("ASS_PROTOCOL_ID between", value1, value2, "assProtocolId");
            return (Criteria) this;
        }

        public Criteria andAssProtocolIdNotBetween(String value1, String value2) {
            addCriterion("ASS_PROTOCOL_ID not between", value1, value2, "assProtocolId");
            return (Criteria) this;
        }

        public Criteria andAgentBusinfoIdIsNull() {
            addCriterion("AGENT_BUSINFO_ID is null");
            return (Criteria) this;
        }

        public Criteria andAgentBusinfoIdIsNotNull() {
            addCriterion("AGENT_BUSINFO_ID is not null");
            return (Criteria) this;
        }

        public Criteria andAgentBusinfoIdEqualTo(String value) {
            addCriterion("AGENT_BUSINFO_ID =", value, "agentBusinfoId");
            return (Criteria) this;
        }

        public Criteria andAgentBusinfoIdNotEqualTo(String value) {
            addCriterion("AGENT_BUSINFO_ID <>", value, "agentBusinfoId");
            return (Criteria) this;
        }

        public Criteria andAgentBusinfoIdGreaterThan(String value) {
            addCriterion("AGENT_BUSINFO_ID >", value, "agentBusinfoId");
            return (Criteria) this;
        }

        public Criteria andAgentBusinfoIdGreaterThanOrEqualTo(String value) {
            addCriterion("AGENT_BUSINFO_ID >=", value, "agentBusinfoId");
            return (Criteria) this;
        }

        public Criteria andAgentBusinfoIdLessThan(String value) {
            addCriterion("AGENT_BUSINFO_ID <", value, "agentBusinfoId");
            return (Criteria) this;
        }

        public Criteria andAgentBusinfoIdLessThanOrEqualTo(String value) {
            addCriterion("AGENT_BUSINFO_ID <=", value, "agentBusinfoId");
            return (Criteria) this;
        }

        public Criteria andAgentBusinfoIdLike(String value) {
            addCriterion("AGENT_BUSINFO_ID like", value, "agentBusinfoId");
            return (Criteria) this;
        }

        public Criteria andAgentBusinfoIdNotLike(String value) {
            addCriterion("AGENT_BUSINFO_ID not like", value, "agentBusinfoId");
            return (Criteria) this;
        }

        public Criteria andAgentBusinfoIdIn(List<String> values) {
            addCriterion("AGENT_BUSINFO_ID in", values, "agentBusinfoId");
            return (Criteria) this;
        }

        public Criteria andAgentBusinfoIdNotIn(List<String> values) {
            addCriterion("AGENT_BUSINFO_ID not in", values, "agentBusinfoId");
            return (Criteria) this;
        }

        public Criteria andAgentBusinfoIdBetween(String value1, String value2) {
            addCriterion("AGENT_BUSINFO_ID between", value1, value2, "agentBusinfoId");
            return (Criteria) this;
        }

        public Criteria andAgentBusinfoIdNotBetween(String value1, String value2) {
            addCriterion("AGENT_BUSINFO_ID not between", value1, value2, "agentBusinfoId");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNull() {
            addCriterion("REMARK is null");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNotNull() {
            addCriterion("REMARK is not null");
            return (Criteria) this;
        }

        public Criteria andRemarkEqualTo(String value) {
            addCriterion("REMARK =", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotEqualTo(String value) {
            addCriterion("REMARK <>", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThan(String value) {
            addCriterion("REMARK >", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("REMARK >=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThan(String value) {
            addCriterion("REMARK <", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThanOrEqualTo(String value) {
            addCriterion("REMARK <=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLike(String value) {
            addCriterion("REMARK like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotLike(String value) {
            addCriterion("REMARK not like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkIn(List<String> values) {
            addCriterion("REMARK in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotIn(List<String> values) {
            addCriterion("REMARK not in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkBetween(String value1, String value2) {
            addCriterion("REMARK between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotBetween(String value1, String value2) {
            addCriterion("REMARK not between", value1, value2, "remark");
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