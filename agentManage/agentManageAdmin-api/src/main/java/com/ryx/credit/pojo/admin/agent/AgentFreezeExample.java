package com.ryx.credit.pojo.admin.agent;

import com.ryx.credit.common.util.Page;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AgentFreezeExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Page page;

    protected Integer limitStart;

    protected Integer limitEnd;

    public AgentFreezeExample() {
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

        public Criteria andFreezeStatusIsNull() {
            addCriterion("FREEZE_STATUS is null");
            return (Criteria) this;
        }

        public Criteria andFreezeStatusIsNotNull() {
            addCriterion("FREEZE_STATUS is not null");
            return (Criteria) this;
        }

        public Criteria andFreezeStatusEqualTo(String value) {
            addCriterion("FREEZE_STATUS =", value, "freezeStatus");
            return (Criteria) this;
        }

        public Criteria andFreezeStatusNotEqualTo(String value) {
            addCriterion("FREEZE_STATUS <>", value, "freezeStatus");
            return (Criteria) this;
        }

        public Criteria andFreezeStatusGreaterThan(String value) {
            addCriterion("FREEZE_STATUS >", value, "freezeStatus");
            return (Criteria) this;
        }

        public Criteria andFreezeStatusGreaterThanOrEqualTo(String value) {
            addCriterion("FREEZE_STATUS >=", value, "freezeStatus");
            return (Criteria) this;
        }

        public Criteria andFreezeStatusLessThan(String value) {
            addCriterion("FREEZE_STATUS <", value, "freezeStatus");
            return (Criteria) this;
        }

        public Criteria andFreezeStatusLessThanOrEqualTo(String value) {
            addCriterion("FREEZE_STATUS <=", value, "freezeStatus");
            return (Criteria) this;
        }

        public Criteria andFreezeStatusLike(String value) {
            addCriterion("FREEZE_STATUS like", value, "freezeStatus");
            return (Criteria) this;
        }

        public Criteria andFreezeStatusNotLike(String value) {
            addCriterion("FREEZE_STATUS not like", value, "freezeStatus");
            return (Criteria) this;
        }

        public Criteria andFreezeStatusIn(List<String> values) {
            addCriterion("FREEZE_STATUS in", values, "freezeStatus");
            return (Criteria) this;
        }

        public Criteria andFreezeStatusNotIn(List<String> values) {
            addCriterion("FREEZE_STATUS not in", values, "freezeStatus");
            return (Criteria) this;
        }

        public Criteria andFreezeStatusBetween(String value1, String value2) {
            addCriterion("FREEZE_STATUS between", value1, value2, "freezeStatus");
            return (Criteria) this;
        }

        public Criteria andFreezeStatusNotBetween(String value1, String value2) {
            addCriterion("FREEZE_STATUS not between", value1, value2, "freezeStatus");
            return (Criteria) this;
        }

        public Criteria andFreezePersonIsNull() {
            addCriterion("FREEZE_PERSON is null");
            return (Criteria) this;
        }

        public Criteria andFreezePersonIsNotNull() {
            addCriterion("FREEZE_PERSON is not null");
            return (Criteria) this;
        }

        public Criteria andFreezePersonEqualTo(String value) {
            addCriterion("FREEZE_PERSON =", value, "freezePerson");
            return (Criteria) this;
        }

        public Criteria andFreezePersonNotEqualTo(String value) {
            addCriterion("FREEZE_PERSON <>", value, "freezePerson");
            return (Criteria) this;
        }

        public Criteria andFreezePersonGreaterThan(String value) {
            addCriterion("FREEZE_PERSON >", value, "freezePerson");
            return (Criteria) this;
        }

        public Criteria andFreezePersonGreaterThanOrEqualTo(String value) {
            addCriterion("FREEZE_PERSON >=", value, "freezePerson");
            return (Criteria) this;
        }

        public Criteria andFreezePersonLessThan(String value) {
            addCriterion("FREEZE_PERSON <", value, "freezePerson");
            return (Criteria) this;
        }

        public Criteria andFreezePersonLessThanOrEqualTo(String value) {
            addCriterion("FREEZE_PERSON <=", value, "freezePerson");
            return (Criteria) this;
        }

        public Criteria andFreezePersonLike(String value) {
            addCriterion("FREEZE_PERSON like", value, "freezePerson");
            return (Criteria) this;
        }

        public Criteria andFreezePersonNotLike(String value) {
            addCriterion("FREEZE_PERSON not like", value, "freezePerson");
            return (Criteria) this;
        }

        public Criteria andFreezePersonIn(List<String> values) {
            addCriterion("FREEZE_PERSON in", values, "freezePerson");
            return (Criteria) this;
        }

        public Criteria andFreezePersonNotIn(List<String> values) {
            addCriterion("FREEZE_PERSON not in", values, "freezePerson");
            return (Criteria) this;
        }

        public Criteria andFreezePersonBetween(String value1, String value2) {
            addCriterion("FREEZE_PERSON between", value1, value2, "freezePerson");
            return (Criteria) this;
        }

        public Criteria andFreezePersonNotBetween(String value1, String value2) {
            addCriterion("FREEZE_PERSON not between", value1, value2, "freezePerson");
            return (Criteria) this;
        }

        public Criteria andFreezeDateIsNull() {
            addCriterion("FREEZE_DATE is null");
            return (Criteria) this;
        }

        public Criteria andFreezeDateIsNotNull() {
            addCriterion("FREEZE_DATE is not null");
            return (Criteria) this;
        }

        public Criteria andFreezeDateEqualTo(Date value) {
            addCriterion("FREEZE_DATE =", value, "freezeDate");
            return (Criteria) this;
        }

        public Criteria andFreezeDateNotEqualTo(Date value) {
            addCriterion("FREEZE_DATE <>", value, "freezeDate");
            return (Criteria) this;
        }

        public Criteria andFreezeDateGreaterThan(Date value) {
            addCriterion("FREEZE_DATE >", value, "freezeDate");
            return (Criteria) this;
        }

        public Criteria andFreezeDateGreaterThanOrEqualTo(Date value) {
            addCriterion("FREEZE_DATE >=", value, "freezeDate");
            return (Criteria) this;
        }

        public Criteria andFreezeDateLessThan(Date value) {
            addCriterion("FREEZE_DATE <", value, "freezeDate");
            return (Criteria) this;
        }

        public Criteria andFreezeDateLessThanOrEqualTo(Date value) {
            addCriterion("FREEZE_DATE <=", value, "freezeDate");
            return (Criteria) this;
        }

        public Criteria andFreezeDateIn(List<Date> values) {
            addCriterion("FREEZE_DATE in", values, "freezeDate");
            return (Criteria) this;
        }

        public Criteria andFreezeDateNotIn(List<Date> values) {
            addCriterion("FREEZE_DATE not in", values, "freezeDate");
            return (Criteria) this;
        }

        public Criteria andFreezeDateBetween(Date value1, Date value2) {
            addCriterion("FREEZE_DATE between", value1, value2, "freezeDate");
            return (Criteria) this;
        }

        public Criteria andFreezeDateNotBetween(Date value1, Date value2) {
            addCriterion("FREEZE_DATE not between", value1, value2, "freezeDate");
            return (Criteria) this;
        }

        public Criteria andFreezeCauseIsNull() {
            addCriterion("FREEZE_CAUSE is null");
            return (Criteria) this;
        }

        public Criteria andFreezeCauseIsNotNull() {
            addCriterion("FREEZE_CAUSE is not null");
            return (Criteria) this;
        }

        public Criteria andFreezeCauseEqualTo(String value) {
            addCriterion("FREEZE_CAUSE =", value, "freezeCause");
            return (Criteria) this;
        }

        public Criteria andFreezeCauseNotEqualTo(String value) {
            addCriterion("FREEZE_CAUSE <>", value, "freezeCause");
            return (Criteria) this;
        }

        public Criteria andFreezeCauseGreaterThan(String value) {
            addCriterion("FREEZE_CAUSE >", value, "freezeCause");
            return (Criteria) this;
        }

        public Criteria andFreezeCauseGreaterThanOrEqualTo(String value) {
            addCriterion("FREEZE_CAUSE >=", value, "freezeCause");
            return (Criteria) this;
        }

        public Criteria andFreezeCauseLessThan(String value) {
            addCriterion("FREEZE_CAUSE <", value, "freezeCause");
            return (Criteria) this;
        }

        public Criteria andFreezeCauseLessThanOrEqualTo(String value) {
            addCriterion("FREEZE_CAUSE <=", value, "freezeCause");
            return (Criteria) this;
        }

        public Criteria andFreezeCauseLike(String value) {
            addCriterion("FREEZE_CAUSE like", value, "freezeCause");
            return (Criteria) this;
        }

        public Criteria andFreezeCauseNotLike(String value) {
            addCriterion("FREEZE_CAUSE not like", value, "freezeCause");
            return (Criteria) this;
        }

        public Criteria andFreezeCauseIn(List<String> values) {
            addCriterion("FREEZE_CAUSE in", values, "freezeCause");
            return (Criteria) this;
        }

        public Criteria andFreezeCauseNotIn(List<String> values) {
            addCriterion("FREEZE_CAUSE not in", values, "freezeCause");
            return (Criteria) this;
        }

        public Criteria andFreezeCauseBetween(String value1, String value2) {
            addCriterion("FREEZE_CAUSE between", value1, value2, "freezeCause");
            return (Criteria) this;
        }

        public Criteria andFreezeCauseNotBetween(String value1, String value2) {
            addCriterion("FREEZE_CAUSE not between", value1, value2, "freezeCause");
            return (Criteria) this;
        }

        public Criteria andUnfreezePersonIsNull() {
            addCriterion("UNFREEZE_PERSON is null");
            return (Criteria) this;
        }

        public Criteria andUnfreezePersonIsNotNull() {
            addCriterion("UNFREEZE_PERSON is not null");
            return (Criteria) this;
        }

        public Criteria andUnfreezePersonEqualTo(String value) {
            addCriterion("UNFREEZE_PERSON =", value, "unfreezePerson");
            return (Criteria) this;
        }

        public Criteria andUnfreezePersonNotEqualTo(String value) {
            addCriterion("UNFREEZE_PERSON <>", value, "unfreezePerson");
            return (Criteria) this;
        }

        public Criteria andUnfreezePersonGreaterThan(String value) {
            addCriterion("UNFREEZE_PERSON >", value, "unfreezePerson");
            return (Criteria) this;
        }

        public Criteria andUnfreezePersonGreaterThanOrEqualTo(String value) {
            addCriterion("UNFREEZE_PERSON >=", value, "unfreezePerson");
            return (Criteria) this;
        }

        public Criteria andUnfreezePersonLessThan(String value) {
            addCriterion("UNFREEZE_PERSON <", value, "unfreezePerson");
            return (Criteria) this;
        }

        public Criteria andUnfreezePersonLessThanOrEqualTo(String value) {
            addCriterion("UNFREEZE_PERSON <=", value, "unfreezePerson");
            return (Criteria) this;
        }

        public Criteria andUnfreezePersonLike(String value) {
            addCriterion("UNFREEZE_PERSON like", value, "unfreezePerson");
            return (Criteria) this;
        }

        public Criteria andUnfreezePersonNotLike(String value) {
            addCriterion("UNFREEZE_PERSON not like", value, "unfreezePerson");
            return (Criteria) this;
        }

        public Criteria andUnfreezePersonIn(List<String> values) {
            addCriterion("UNFREEZE_PERSON in", values, "unfreezePerson");
            return (Criteria) this;
        }

        public Criteria andUnfreezePersonNotIn(List<String> values) {
            addCriterion("UNFREEZE_PERSON not in", values, "unfreezePerson");
            return (Criteria) this;
        }

        public Criteria andUnfreezePersonBetween(String value1, String value2) {
            addCriterion("UNFREEZE_PERSON between", value1, value2, "unfreezePerson");
            return (Criteria) this;
        }

        public Criteria andUnfreezePersonNotBetween(String value1, String value2) {
            addCriterion("UNFREEZE_PERSON not between", value1, value2, "unfreezePerson");
            return (Criteria) this;
        }

        public Criteria andUnfreezeDateIsNull() {
            addCriterion("UNFREEZE_DATE is null");
            return (Criteria) this;
        }

        public Criteria andUnfreezeDateIsNotNull() {
            addCriterion("UNFREEZE_DATE is not null");
            return (Criteria) this;
        }

        public Criteria andUnfreezeDateEqualTo(Date value) {
            addCriterion("UNFREEZE_DATE =", value, "unfreezeDate");
            return (Criteria) this;
        }

        public Criteria andUnfreezeDateNotEqualTo(Date value) {
            addCriterion("UNFREEZE_DATE <>", value, "unfreezeDate");
            return (Criteria) this;
        }

        public Criteria andUnfreezeDateGreaterThan(Date value) {
            addCriterion("UNFREEZE_DATE >", value, "unfreezeDate");
            return (Criteria) this;
        }

        public Criteria andUnfreezeDateGreaterThanOrEqualTo(Date value) {
            addCriterion("UNFREEZE_DATE >=", value, "unfreezeDate");
            return (Criteria) this;
        }

        public Criteria andUnfreezeDateLessThan(Date value) {
            addCriterion("UNFREEZE_DATE <", value, "unfreezeDate");
            return (Criteria) this;
        }

        public Criteria andUnfreezeDateLessThanOrEqualTo(Date value) {
            addCriterion("UNFREEZE_DATE <=", value, "unfreezeDate");
            return (Criteria) this;
        }

        public Criteria andUnfreezeDateIn(List<Date> values) {
            addCriterion("UNFREEZE_DATE in", values, "unfreezeDate");
            return (Criteria) this;
        }

        public Criteria andUnfreezeDateNotIn(List<Date> values) {
            addCriterion("UNFREEZE_DATE not in", values, "unfreezeDate");
            return (Criteria) this;
        }

        public Criteria andUnfreezeDateBetween(Date value1, Date value2) {
            addCriterion("UNFREEZE_DATE between", value1, value2, "unfreezeDate");
            return (Criteria) this;
        }

        public Criteria andUnfreezeDateNotBetween(Date value1, Date value2) {
            addCriterion("UNFREEZE_DATE not between", value1, value2, "unfreezeDate");
            return (Criteria) this;
        }

        public Criteria andUnfreezeCauseIsNull() {
            addCriterion("UNFREEZE_CAUSE is null");
            return (Criteria) this;
        }

        public Criteria andUnfreezeCauseIsNotNull() {
            addCriterion("UNFREEZE_CAUSE is not null");
            return (Criteria) this;
        }

        public Criteria andUnfreezeCauseEqualTo(String value) {
            addCriterion("UNFREEZE_CAUSE =", value, "unfreezeCause");
            return (Criteria) this;
        }

        public Criteria andUnfreezeCauseNotEqualTo(String value) {
            addCriterion("UNFREEZE_CAUSE <>", value, "unfreezeCause");
            return (Criteria) this;
        }

        public Criteria andUnfreezeCauseGreaterThan(String value) {
            addCriterion("UNFREEZE_CAUSE >", value, "unfreezeCause");
            return (Criteria) this;
        }

        public Criteria andUnfreezeCauseGreaterThanOrEqualTo(String value) {
            addCriterion("UNFREEZE_CAUSE >=", value, "unfreezeCause");
            return (Criteria) this;
        }

        public Criteria andUnfreezeCauseLessThan(String value) {
            addCriterion("UNFREEZE_CAUSE <", value, "unfreezeCause");
            return (Criteria) this;
        }

        public Criteria andUnfreezeCauseLessThanOrEqualTo(String value) {
            addCriterion("UNFREEZE_CAUSE <=", value, "unfreezeCause");
            return (Criteria) this;
        }

        public Criteria andUnfreezeCauseLike(String value) {
            addCriterion("UNFREEZE_CAUSE like", value, "unfreezeCause");
            return (Criteria) this;
        }

        public Criteria andUnfreezeCauseNotLike(String value) {
            addCriterion("UNFREEZE_CAUSE not like", value, "unfreezeCause");
            return (Criteria) this;
        }

        public Criteria andUnfreezeCauseIn(List<String> values) {
            addCriterion("UNFREEZE_CAUSE in", values, "unfreezeCause");
            return (Criteria) this;
        }

        public Criteria andUnfreezeCauseNotIn(List<String> values) {
            addCriterion("UNFREEZE_CAUSE not in", values, "unfreezeCause");
            return (Criteria) this;
        }

        public Criteria andUnfreezeCauseBetween(String value1, String value2) {
            addCriterion("UNFREEZE_CAUSE between", value1, value2, "unfreezeCause");
            return (Criteria) this;
        }

        public Criteria andUnfreezeCauseNotBetween(String value1, String value2) {
            addCriterion("UNFREEZE_CAUSE not between", value1, value2, "unfreezeCause");
            return (Criteria) this;
        }

        public Criteria andFreezeNumIsNull() {
            addCriterion("FREEZE_NUM is null");
            return (Criteria) this;
        }

        public Criteria andFreezeNumIsNotNull() {
            addCriterion("FREEZE_NUM is not null");
            return (Criteria) this;
        }

        public Criteria andFreezeNumEqualTo(String value) {
            addCriterion("FREEZE_NUM =", value, "freezeNum");
            return (Criteria) this;
        }

        public Criteria andFreezeNumNotEqualTo(String value) {
            addCriterion("FREEZE_NUM <>", value, "freezeNum");
            return (Criteria) this;
        }

        public Criteria andFreezeNumGreaterThan(String value) {
            addCriterion("FREEZE_NUM >", value, "freezeNum");
            return (Criteria) this;
        }

        public Criteria andFreezeNumGreaterThanOrEqualTo(String value) {
            addCriterion("FREEZE_NUM >=", value, "freezeNum");
            return (Criteria) this;
        }

        public Criteria andFreezeNumLessThan(String value) {
            addCriterion("FREEZE_NUM <", value, "freezeNum");
            return (Criteria) this;
        }

        public Criteria andFreezeNumLessThanOrEqualTo(String value) {
            addCriterion("FREEZE_NUM <=", value, "freezeNum");
            return (Criteria) this;
        }

        public Criteria andFreezeNumLike(String value) {
            addCriterion("FREEZE_NUM like", value, "freezeNum");
            return (Criteria) this;
        }

        public Criteria andFreezeNumNotLike(String value) {
            addCriterion("FREEZE_NUM not like", value, "freezeNum");
            return (Criteria) this;
        }

        public Criteria andFreezeNumIn(List<String> values) {
            addCriterion("FREEZE_NUM in", values, "freezeNum");
            return (Criteria) this;
        }

        public Criteria andFreezeNumNotIn(List<String> values) {
            addCriterion("FREEZE_NUM not in", values, "freezeNum");
            return (Criteria) this;
        }

        public Criteria andFreezeNumBetween(String value1, String value2) {
            addCriterion("FREEZE_NUM between", value1, value2, "freezeNum");
            return (Criteria) this;
        }

        public Criteria andFreezeNumNotBetween(String value1, String value2) {
            addCriterion("FREEZE_NUM not between", value1, value2, "freezeNum");
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

        public Criteria andFreezeTypeIsNull() {
            addCriterion("FREEZE_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andFreezeTypeIsNotNull() {
            addCriterion("FREEZE_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andFreezeTypeEqualTo(BigDecimal value) {
            addCriterion("FREEZE_TYPE =", value, "freezeType");
            return (Criteria) this;
        }

        public Criteria andFreezeTypeNotEqualTo(BigDecimal value) {
            addCriterion("FREEZE_TYPE <>", value, "freezeType");
            return (Criteria) this;
        }

        public Criteria andFreezeTypeGreaterThan(BigDecimal value) {
            addCriterion("FREEZE_TYPE >", value, "freezeType");
            return (Criteria) this;
        }

        public Criteria andFreezeTypeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("FREEZE_TYPE >=", value, "freezeType");
            return (Criteria) this;
        }

        public Criteria andFreezeTypeLessThan(BigDecimal value) {
            addCriterion("FREEZE_TYPE <", value, "freezeType");
            return (Criteria) this;
        }

        public Criteria andFreezeTypeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("FREEZE_TYPE <=", value, "freezeType");
            return (Criteria) this;
        }

        public Criteria andFreezeTypeIn(List<BigDecimal> values) {
            addCriterion("FREEZE_TYPE in", values, "freezeType");
            return (Criteria) this;
        }

        public Criteria andFreezeTypeNotIn(List<BigDecimal> values) {
            addCriterion("FREEZE_TYPE not in", values, "freezeType");
            return (Criteria) this;
        }

        public Criteria andFreezeTypeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("FREEZE_TYPE between", value1, value2, "freezeType");
            return (Criteria) this;
        }

        public Criteria andFreezeTypeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("FREEZE_TYPE not between", value1, value2, "freezeType");
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

        public Criteria andBusFreezeIsNull() {
            addCriterion("BUS_FREEZE is null");
            return (Criteria) this;
        }

        public Criteria andBusFreezeIsNotNull() {
            addCriterion("BUS_FREEZE is not null");
            return (Criteria) this;
        }

        public Criteria andBusFreezeEqualTo(BigDecimal value) {
            addCriterion("BUS_FREEZE =", value, "busFreeze");
            return (Criteria) this;
        }

        public Criteria andBusFreezeNotEqualTo(BigDecimal value) {
            addCriterion("BUS_FREEZE <>", value, "busFreeze");
            return (Criteria) this;
        }

        public Criteria andBusFreezeGreaterThan(BigDecimal value) {
            addCriterion("BUS_FREEZE >", value, "busFreeze");
            return (Criteria) this;
        }

        public Criteria andBusFreezeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("BUS_FREEZE >=", value, "busFreeze");
            return (Criteria) this;
        }

        public Criteria andBusFreezeLessThan(BigDecimal value) {
            addCriterion("BUS_FREEZE <", value, "busFreeze");
            return (Criteria) this;
        }

        public Criteria andBusFreezeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("BUS_FREEZE <=", value, "busFreeze");
            return (Criteria) this;
        }

        public Criteria andBusFreezeIn(List<BigDecimal> values) {
            addCriterion("BUS_FREEZE in", values, "busFreeze");
            return (Criteria) this;
        }

        public Criteria andBusFreezeNotIn(List<BigDecimal> values) {
            addCriterion("BUS_FREEZE not in", values, "busFreeze");
            return (Criteria) this;
        }

        public Criteria andBusFreezeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("BUS_FREEZE between", value1, value2, "busFreeze");
            return (Criteria) this;
        }

        public Criteria andBusFreezeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("BUS_FREEZE not between", value1, value2, "busFreeze");
            return (Criteria) this;
        }

        public Criteria andProfitFreezeIsNull() {
            addCriterion("PROFIT_FREEZE is null");
            return (Criteria) this;
        }

        public Criteria andProfitFreezeIsNotNull() {
            addCriterion("PROFIT_FREEZE is not null");
            return (Criteria) this;
        }

        public Criteria andProfitFreezeEqualTo(BigDecimal value) {
            addCriterion("PROFIT_FREEZE =", value, "profitFreeze");
            return (Criteria) this;
        }

        public Criteria andProfitFreezeNotEqualTo(BigDecimal value) {
            addCriterion("PROFIT_FREEZE <>", value, "profitFreeze");
            return (Criteria) this;
        }

        public Criteria andProfitFreezeGreaterThan(BigDecimal value) {
            addCriterion("PROFIT_FREEZE >", value, "profitFreeze");
            return (Criteria) this;
        }

        public Criteria andProfitFreezeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("PROFIT_FREEZE >=", value, "profitFreeze");
            return (Criteria) this;
        }

        public Criteria andProfitFreezeLessThan(BigDecimal value) {
            addCriterion("PROFIT_FREEZE <", value, "profitFreeze");
            return (Criteria) this;
        }

        public Criteria andProfitFreezeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("PROFIT_FREEZE <=", value, "profitFreeze");
            return (Criteria) this;
        }

        public Criteria andProfitFreezeIn(List<BigDecimal> values) {
            addCriterion("PROFIT_FREEZE in", values, "profitFreeze");
            return (Criteria) this;
        }

        public Criteria andProfitFreezeNotIn(List<BigDecimal> values) {
            addCriterion("PROFIT_FREEZE not in", values, "profitFreeze");
            return (Criteria) this;
        }

        public Criteria andProfitFreezeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("PROFIT_FREEZE between", value1, value2, "profitFreeze");
            return (Criteria) this;
        }

        public Criteria andProfitFreezeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("PROFIT_FREEZE not between", value1, value2, "profitFreeze");
            return (Criteria) this;
        }

        public Criteria andReflowFreezeIsNull() {
            addCriterion("REFLOW_FREEZE is null");
            return (Criteria) this;
        }

        public Criteria andReflowFreezeIsNotNull() {
            addCriterion("REFLOW_FREEZE is not null");
            return (Criteria) this;
        }

        public Criteria andReflowFreezeEqualTo(BigDecimal value) {
            addCriterion("REFLOW_FREEZE =", value, "reflowFreeze");
            return (Criteria) this;
        }

        public Criteria andReflowFreezeNotEqualTo(BigDecimal value) {
            addCriterion("REFLOW_FREEZE <>", value, "reflowFreeze");
            return (Criteria) this;
        }

        public Criteria andReflowFreezeGreaterThan(BigDecimal value) {
            addCriterion("REFLOW_FREEZE >", value, "reflowFreeze");
            return (Criteria) this;
        }

        public Criteria andReflowFreezeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("REFLOW_FREEZE >=", value, "reflowFreeze");
            return (Criteria) this;
        }

        public Criteria andReflowFreezeLessThan(BigDecimal value) {
            addCriterion("REFLOW_FREEZE <", value, "reflowFreeze");
            return (Criteria) this;
        }

        public Criteria andReflowFreezeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("REFLOW_FREEZE <=", value, "reflowFreeze");
            return (Criteria) this;
        }

        public Criteria andReflowFreezeIn(List<BigDecimal> values) {
            addCriterion("REFLOW_FREEZE in", values, "reflowFreeze");
            return (Criteria) this;
        }

        public Criteria andReflowFreezeNotIn(List<BigDecimal> values) {
            addCriterion("REFLOW_FREEZE not in", values, "reflowFreeze");
            return (Criteria) this;
        }

        public Criteria andReflowFreezeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("REFLOW_FREEZE between", value1, value2, "reflowFreeze");
            return (Criteria) this;
        }

        public Criteria andReflowFreezeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("REFLOW_FREEZE not between", value1, value2, "reflowFreeze");
            return (Criteria) this;
        }

        public Criteria andMonthlyFreezeIsNull() {
            addCriterion("MONTHLY_FREEZE is null");
            return (Criteria) this;
        }

        public Criteria andMonthlyFreezeIsNotNull() {
            addCriterion("MONTHLY_FREEZE is not null");
            return (Criteria) this;
        }

        public Criteria andMonthlyFreezeEqualTo(BigDecimal value) {
            addCriterion("MONTHLY_FREEZE =", value, "monthlyFreeze");
            return (Criteria) this;
        }

        public Criteria andMonthlyFreezeNotEqualTo(BigDecimal value) {
            addCriterion("MONTHLY_FREEZE <>", value, "monthlyFreeze");
            return (Criteria) this;
        }

        public Criteria andMonthlyFreezeGreaterThan(BigDecimal value) {
            addCriterion("MONTHLY_FREEZE >", value, "monthlyFreeze");
            return (Criteria) this;
        }

        public Criteria andMonthlyFreezeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("MONTHLY_FREEZE >=", value, "monthlyFreeze");
            return (Criteria) this;
        }

        public Criteria andMonthlyFreezeLessThan(BigDecimal value) {
            addCriterion("MONTHLY_FREEZE <", value, "monthlyFreeze");
            return (Criteria) this;
        }

        public Criteria andMonthlyFreezeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("MONTHLY_FREEZE <=", value, "monthlyFreeze");
            return (Criteria) this;
        }

        public Criteria andMonthlyFreezeIn(List<BigDecimal> values) {
            addCriterion("MONTHLY_FREEZE in", values, "monthlyFreeze");
            return (Criteria) this;
        }

        public Criteria andMonthlyFreezeNotIn(List<BigDecimal> values) {
            addCriterion("MONTHLY_FREEZE not in", values, "monthlyFreeze");
            return (Criteria) this;
        }

        public Criteria andMonthlyFreezeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("MONTHLY_FREEZE between", value1, value2, "monthlyFreeze");
            return (Criteria) this;
        }

        public Criteria andMonthlyFreezeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("MONTHLY_FREEZE not between", value1, value2, "monthlyFreeze");
            return (Criteria) this;
        }

        public Criteria andDailyFreezeIsNull() {
            addCriterion("DAILY_FREEZE is null");
            return (Criteria) this;
        }

        public Criteria andDailyFreezeIsNotNull() {
            addCriterion("DAILY_FREEZE is not null");
            return (Criteria) this;
        }

        public Criteria andDailyFreezeEqualTo(BigDecimal value) {
            addCriterion("DAILY_FREEZE =", value, "dailyFreeze");
            return (Criteria) this;
        }

        public Criteria andDailyFreezeNotEqualTo(BigDecimal value) {
            addCriterion("DAILY_FREEZE <>", value, "dailyFreeze");
            return (Criteria) this;
        }

        public Criteria andDailyFreezeGreaterThan(BigDecimal value) {
            addCriterion("DAILY_FREEZE >", value, "dailyFreeze");
            return (Criteria) this;
        }

        public Criteria andDailyFreezeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("DAILY_FREEZE >=", value, "dailyFreeze");
            return (Criteria) this;
        }

        public Criteria andDailyFreezeLessThan(BigDecimal value) {
            addCriterion("DAILY_FREEZE <", value, "dailyFreeze");
            return (Criteria) this;
        }

        public Criteria andDailyFreezeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("DAILY_FREEZE <=", value, "dailyFreeze");
            return (Criteria) this;
        }

        public Criteria andDailyFreezeIn(List<BigDecimal> values) {
            addCriterion("DAILY_FREEZE in", values, "dailyFreeze");
            return (Criteria) this;
        }

        public Criteria andDailyFreezeNotIn(List<BigDecimal> values) {
            addCriterion("DAILY_FREEZE not in", values, "dailyFreeze");
            return (Criteria) this;
        }

        public Criteria andDailyFreezeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("DAILY_FREEZE between", value1, value2, "dailyFreeze");
            return (Criteria) this;
        }

        public Criteria andDailyFreezeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("DAILY_FREEZE not between", value1, value2, "dailyFreeze");
            return (Criteria) this;
        }

        public Criteria andStopProfitFreezeIsNull() {
            addCriterion("STOP_PROFIT_FREEZE is null");
            return (Criteria) this;
        }

        public Criteria andStopProfitFreezeIsNotNull() {
            addCriterion("STOP_PROFIT_FREEZE is not null");
            return (Criteria) this;
        }

        public Criteria andStopProfitFreezeEqualTo(BigDecimal value) {
            addCriterion("STOP_PROFIT_FREEZE =", value, "stopProfitFreeze");
            return (Criteria) this;
        }

        public Criteria andStopProfitFreezeNotEqualTo(BigDecimal value) {
            addCriterion("STOP_PROFIT_FREEZE <>", value, "stopProfitFreeze");
            return (Criteria) this;
        }

        public Criteria andStopProfitFreezeGreaterThan(BigDecimal value) {
            addCriterion("STOP_PROFIT_FREEZE >", value, "stopProfitFreeze");
            return (Criteria) this;
        }

        public Criteria andStopProfitFreezeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("STOP_PROFIT_FREEZE >=", value, "stopProfitFreeze");
            return (Criteria) this;
        }

        public Criteria andStopProfitFreezeLessThan(BigDecimal value) {
            addCriterion("STOP_PROFIT_FREEZE <", value, "stopProfitFreeze");
            return (Criteria) this;
        }

        public Criteria andStopProfitFreezeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("STOP_PROFIT_FREEZE <=", value, "stopProfitFreeze");
            return (Criteria) this;
        }

        public Criteria andStopProfitFreezeIn(List<BigDecimal> values) {
            addCriterion("STOP_PROFIT_FREEZE in", values, "stopProfitFreeze");
            return (Criteria) this;
        }

        public Criteria andStopProfitFreezeNotIn(List<BigDecimal> values) {
            addCriterion("STOP_PROFIT_FREEZE not in", values, "stopProfitFreeze");
            return (Criteria) this;
        }

        public Criteria andStopProfitFreezeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("STOP_PROFIT_FREEZE between", value1, value2, "stopProfitFreeze");
            return (Criteria) this;
        }

        public Criteria andStopProfitFreezeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("STOP_PROFIT_FREEZE not between", value1, value2, "stopProfitFreeze");
            return (Criteria) this;
        }

        public Criteria andCashFreezeIsNull() {
            addCriterion("CASH_FREEZE is null");
            return (Criteria) this;
        }

        public Criteria andCashFreezeIsNotNull() {
            addCriterion("CASH_FREEZE is not null");
            return (Criteria) this;
        }

        public Criteria andCashFreezeEqualTo(BigDecimal value) {
            addCriterion("CASH_FREEZE =", value, "cashFreeze");
            return (Criteria) this;
        }

        public Criteria andCashFreezeNotEqualTo(BigDecimal value) {
            addCriterion("CASH_FREEZE <>", value, "cashFreeze");
            return (Criteria) this;
        }

        public Criteria andCashFreezeGreaterThan(BigDecimal value) {
            addCriterion("CASH_FREEZE >", value, "cashFreeze");
            return (Criteria) this;
        }

        public Criteria andCashFreezeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("CASH_FREEZE >=", value, "cashFreeze");
            return (Criteria) this;
        }

        public Criteria andCashFreezeLessThan(BigDecimal value) {
            addCriterion("CASH_FREEZE <", value, "cashFreeze");
            return (Criteria) this;
        }

        public Criteria andCashFreezeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("CASH_FREEZE <=", value, "cashFreeze");
            return (Criteria) this;
        }

        public Criteria andCashFreezeIn(List<BigDecimal> values) {
            addCriterion("CASH_FREEZE in", values, "cashFreeze");
            return (Criteria) this;
        }

        public Criteria andCashFreezeNotIn(List<BigDecimal> values) {
            addCriterion("CASH_FREEZE not in", values, "cashFreeze");
            return (Criteria) this;
        }

        public Criteria andCashFreezeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("CASH_FREEZE between", value1, value2, "cashFreeze");
            return (Criteria) this;
        }

        public Criteria andCashFreezeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("CASH_FREEZE not between", value1, value2, "cashFreeze");
            return (Criteria) this;
        }

        public Criteria andStopCountIsNull() {
            addCriterion("STOP_COUNT is null");
            return (Criteria) this;
        }

        public Criteria andStopCountIsNotNull() {
            addCriterion("STOP_COUNT is not null");
            return (Criteria) this;
        }

        public Criteria andStopCountEqualTo(BigDecimal value) {
            addCriterion("STOP_COUNT =", value, "stopCount");
            return (Criteria) this;
        }

        public Criteria andStopCountNotEqualTo(BigDecimal value) {
            addCriterion("STOP_COUNT <>", value, "stopCount");
            return (Criteria) this;
        }

        public Criteria andStopCountGreaterThan(BigDecimal value) {
            addCriterion("STOP_COUNT >", value, "stopCount");
            return (Criteria) this;
        }

        public Criteria andStopCountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("STOP_COUNT >=", value, "stopCount");
            return (Criteria) this;
        }

        public Criteria andStopCountLessThan(BigDecimal value) {
            addCriterion("STOP_COUNT <", value, "stopCount");
            return (Criteria) this;
        }

        public Criteria andStopCountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("STOP_COUNT <=", value, "stopCount");
            return (Criteria) this;
        }

        public Criteria andStopCountIn(List<BigDecimal> values) {
            addCriterion("STOP_COUNT in", values, "stopCount");
            return (Criteria) this;
        }

        public Criteria andStopCountNotIn(List<BigDecimal> values) {
            addCriterion("STOP_COUNT not in", values, "stopCount");
            return (Criteria) this;
        }

        public Criteria andStopCountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("STOP_COUNT between", value1, value2, "stopCount");
            return (Criteria) this;
        }

        public Criteria andStopCountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("STOP_COUNT not between", value1, value2, "stopCount");
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

        public Criteria andNewBusFreezeIsNull() {
            addCriterion("NEW_BUS_FREEZE is null");
            return (Criteria) this;
        }

        public Criteria andNewBusFreezeIsNotNull() {
            addCriterion("NEW_BUS_FREEZE is not null");
            return (Criteria) this;
        }

        public Criteria andNewBusFreezeEqualTo(BigDecimal value) {
            addCriterion("NEW_BUS_FREEZE =", value, "newBusFreeze");
            return (Criteria) this;
        }

        public Criteria andNewBusFreezeNotEqualTo(BigDecimal value) {
            addCriterion("NEW_BUS_FREEZE <>", value, "newBusFreeze");
            return (Criteria) this;
        }

        public Criteria andNewBusFreezeGreaterThan(BigDecimal value) {
            addCriterion("NEW_BUS_FREEZE >", value, "newBusFreeze");
            return (Criteria) this;
        }

        public Criteria andNewBusFreezeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("NEW_BUS_FREEZE >=", value, "newBusFreeze");
            return (Criteria) this;
        }

        public Criteria andNewBusFreezeLessThan(BigDecimal value) {
            addCriterion("NEW_BUS_FREEZE <", value, "newBusFreeze");
            return (Criteria) this;
        }

        public Criteria andNewBusFreezeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("NEW_BUS_FREEZE <=", value, "newBusFreeze");
            return (Criteria) this;
        }

        public Criteria andNewBusFreezeIn(List<BigDecimal> values) {
            addCriterion("NEW_BUS_FREEZE in", values, "newBusFreeze");
            return (Criteria) this;
        }

        public Criteria andNewBusFreezeNotIn(List<BigDecimal> values) {
            addCriterion("NEW_BUS_FREEZE not in", values, "newBusFreeze");
            return (Criteria) this;
        }

        public Criteria andNewBusFreezeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("NEW_BUS_FREEZE between", value1, value2, "newBusFreeze");
            return (Criteria) this;
        }

        public Criteria andNewBusFreezeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("NEW_BUS_FREEZE not between", value1, value2, "newBusFreeze");
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