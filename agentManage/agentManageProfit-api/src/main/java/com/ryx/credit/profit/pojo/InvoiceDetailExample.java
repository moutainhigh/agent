package com.ryx.credit.profit.pojo;

import com.ryx.credit.common.util.Page;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class InvoiceDetailExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Page page;

    protected Integer limitStart;

    protected Integer limitEnd;

    public InvoiceDetailExample() {
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

        public Criteria andProfitMonthIsNull() {
            addCriterion("PROFIT_MONTH is null");
            return (Criteria) this;
        }

        public Criteria andProfitMonthIsNotNull() {
            addCriterion("PROFIT_MONTH is not null");
            return (Criteria) this;
        }

        public Criteria andProfitMonthEqualTo(String value) {
            addCriterion("PROFIT_MONTH =", value, "profitMonth");
            return (Criteria) this;
        }

        public Criteria andProfitMonthNotEqualTo(String value) {
            addCriterion("PROFIT_MONTH <>", value, "profitMonth");
            return (Criteria) this;
        }

        public Criteria andProfitMonthGreaterThan(String value) {
            addCriterion("PROFIT_MONTH >", value, "profitMonth");
            return (Criteria) this;
        }

        public Criteria andProfitMonthGreaterThanOrEqualTo(String value) {
            addCriterion("PROFIT_MONTH >=", value, "profitMonth");
            return (Criteria) this;
        }

        public Criteria andProfitMonthLessThan(String value) {
            addCriterion("PROFIT_MONTH <", value, "profitMonth");
            return (Criteria) this;
        }

        public Criteria andProfitMonthLessThanOrEqualTo(String value) {
            addCriterion("PROFIT_MONTH <=", value, "profitMonth");
            return (Criteria) this;
        }

        public Criteria andProfitMonthLike(String value) {
            addCriterion("PROFIT_MONTH like", value, "profitMonth");
            return (Criteria) this;
        }

        public Criteria andProfitMonthNotLike(String value) {
            addCriterion("PROFIT_MONTH not like", value, "profitMonth");
            return (Criteria) this;
        }

        public Criteria andProfitMonthIn(List<String> values) {
            addCriterion("PROFIT_MONTH in", values, "profitMonth");
            return (Criteria) this;
        }

        public Criteria andProfitMonthNotIn(List<String> values) {
            addCriterion("PROFIT_MONTH not in", values, "profitMonth");
            return (Criteria) this;
        }

        public Criteria andProfitMonthBetween(String value1, String value2) {
            addCriterion("PROFIT_MONTH between", value1, value2, "profitMonth");
            return (Criteria) this;
        }

        public Criteria andProfitMonthNotBetween(String value1, String value2) {
            addCriterion("PROFIT_MONTH not between", value1, value2, "profitMonth");
            return (Criteria) this;
        }

        public Criteria andAgentPidIsNull() {
            addCriterion("AGENT_PID is null");
            return (Criteria) this;
        }

        public Criteria andAgentPidIsNotNull() {
            addCriterion("AGENT_PID is not null");
            return (Criteria) this;
        }

        public Criteria andAgentPidEqualTo(String value) {
            addCriterion("AGENT_PID =", value, "agentPid");
            return (Criteria) this;
        }

        public Criteria andAgentPidNotEqualTo(String value) {
            addCriterion("AGENT_PID <>", value, "agentPid");
            return (Criteria) this;
        }

        public Criteria andAgentPidGreaterThan(String value) {
            addCriterion("AGENT_PID >", value, "agentPid");
            return (Criteria) this;
        }

        public Criteria andAgentPidGreaterThanOrEqualTo(String value) {
            addCriterion("AGENT_PID >=", value, "agentPid");
            return (Criteria) this;
        }

        public Criteria andAgentPidLessThan(String value) {
            addCriterion("AGENT_PID <", value, "agentPid");
            return (Criteria) this;
        }

        public Criteria andAgentPidLessThanOrEqualTo(String value) {
            addCriterion("AGENT_PID <=", value, "agentPid");
            return (Criteria) this;
        }

        public Criteria andAgentPidLike(String value) {
            addCriterion("AGENT_PID like", value, "agentPid");
            return (Criteria) this;
        }

        public Criteria andAgentPidNotLike(String value) {
            addCriterion("AGENT_PID not like", value, "agentPid");
            return (Criteria) this;
        }

        public Criteria andAgentPidIn(List<String> values) {
            addCriterion("AGENT_PID in", values, "agentPid");
            return (Criteria) this;
        }

        public Criteria andAgentPidNotIn(List<String> values) {
            addCriterion("AGENT_PID not in", values, "agentPid");
            return (Criteria) this;
        }

        public Criteria andAgentPidBetween(String value1, String value2) {
            addCriterion("AGENT_PID between", value1, value2, "agentPid");
            return (Criteria) this;
        }

        public Criteria andAgentPidNotBetween(String value1, String value2) {
            addCriterion("AGENT_PID not between", value1, value2, "agentPid");
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

        public Criteria andFristAgentIdIsNull() {
            addCriterion("FRIST_AGENT_ID is null");
            return (Criteria) this;
        }

        public Criteria andFristAgentIdIsNotNull() {
            addCriterion("FRIST_AGENT_ID is not null");
            return (Criteria) this;
        }

        public Criteria andFristAgentIdEqualTo(String value) {
            addCriterion("FRIST_AGENT_ID =", value, "fristAgentId");
            return (Criteria) this;
        }

        public Criteria andFristAgentIdNotEqualTo(String value) {
            addCriterion("FRIST_AGENT_ID <>", value, "fristAgentId");
            return (Criteria) this;
        }

        public Criteria andFristAgentIdGreaterThan(String value) {
            addCriterion("FRIST_AGENT_ID >", value, "fristAgentId");
            return (Criteria) this;
        }

        public Criteria andFristAgentIdGreaterThanOrEqualTo(String value) {
            addCriterion("FRIST_AGENT_ID >=", value, "fristAgentId");
            return (Criteria) this;
        }

        public Criteria andFristAgentIdLessThan(String value) {
            addCriterion("FRIST_AGENT_ID <", value, "fristAgentId");
            return (Criteria) this;
        }

        public Criteria andFristAgentIdLessThanOrEqualTo(String value) {
            addCriterion("FRIST_AGENT_ID <=", value, "fristAgentId");
            return (Criteria) this;
        }

        public Criteria andFristAgentIdLike(String value) {
            addCriterion("FRIST_AGENT_ID like", value, "fristAgentId");
            return (Criteria) this;
        }

        public Criteria andFristAgentIdNotLike(String value) {
            addCriterion("FRIST_AGENT_ID not like", value, "fristAgentId");
            return (Criteria) this;
        }

        public Criteria andFristAgentIdIn(List<String> values) {
            addCriterion("FRIST_AGENT_ID in", values, "fristAgentId");
            return (Criteria) this;
        }

        public Criteria andFristAgentIdNotIn(List<String> values) {
            addCriterion("FRIST_AGENT_ID not in", values, "fristAgentId");
            return (Criteria) this;
        }

        public Criteria andFristAgentIdBetween(String value1, String value2) {
            addCriterion("FRIST_AGENT_ID between", value1, value2, "fristAgentId");
            return (Criteria) this;
        }

        public Criteria andFristAgentIdNotBetween(String value1, String value2) {
            addCriterion("FRIST_AGENT_ID not between", value1, value2, "fristAgentId");
            return (Criteria) this;
        }

        public Criteria andFristAgentNameIsNull() {
            addCriterion("FRIST_AGENT_NAME is null");
            return (Criteria) this;
        }

        public Criteria andFristAgentNameIsNotNull() {
            addCriterion("FRIST_AGENT_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andFristAgentNameEqualTo(String value) {
            addCriterion("FRIST_AGENT_NAME =", value, "fristAgentName");
            return (Criteria) this;
        }

        public Criteria andFristAgentNameNotEqualTo(String value) {
            addCriterion("FRIST_AGENT_NAME <>", value, "fristAgentName");
            return (Criteria) this;
        }

        public Criteria andFristAgentNameGreaterThan(String value) {
            addCriterion("FRIST_AGENT_NAME >", value, "fristAgentName");
            return (Criteria) this;
        }

        public Criteria andFristAgentNameGreaterThanOrEqualTo(String value) {
            addCriterion("FRIST_AGENT_NAME >=", value, "fristAgentName");
            return (Criteria) this;
        }

        public Criteria andFristAgentNameLessThan(String value) {
            addCriterion("FRIST_AGENT_NAME <", value, "fristAgentName");
            return (Criteria) this;
        }

        public Criteria andFristAgentNameLessThanOrEqualTo(String value) {
            addCriterion("FRIST_AGENT_NAME <=", value, "fristAgentName");
            return (Criteria) this;
        }

        public Criteria andFristAgentNameLike(String value) {
            addCriterion("FRIST_AGENT_NAME like", value, "fristAgentName");
            return (Criteria) this;
        }

        public Criteria andFristAgentNameNotLike(String value) {
            addCriterion("FRIST_AGENT_NAME not like", value, "fristAgentName");
            return (Criteria) this;
        }

        public Criteria andFristAgentNameIn(List<String> values) {
            addCriterion("FRIST_AGENT_NAME in", values, "fristAgentName");
            return (Criteria) this;
        }

        public Criteria andFristAgentNameNotIn(List<String> values) {
            addCriterion("FRIST_AGENT_NAME not in", values, "fristAgentName");
            return (Criteria) this;
        }

        public Criteria andFristAgentNameBetween(String value1, String value2) {
            addCriterion("FRIST_AGENT_NAME between", value1, value2, "fristAgentName");
            return (Criteria) this;
        }

        public Criteria andFristAgentNameNotBetween(String value1, String value2) {
            addCriterion("FRIST_AGENT_NAME not between", value1, value2, "fristAgentName");
            return (Criteria) this;
        }

        public Criteria andParentAgentIdIsNull() {
            addCriterion("PARENT_AGENT_ID is null");
            return (Criteria) this;
        }

        public Criteria andParentAgentIdIsNotNull() {
            addCriterion("PARENT_AGENT_ID is not null");
            return (Criteria) this;
        }

        public Criteria andParentAgentIdEqualTo(String value) {
            addCriterion("PARENT_AGENT_ID =", value, "parentAgentId");
            return (Criteria) this;
        }

        public Criteria andParentAgentIdNotEqualTo(String value) {
            addCriterion("PARENT_AGENT_ID <>", value, "parentAgentId");
            return (Criteria) this;
        }

        public Criteria andParentAgentIdGreaterThan(String value) {
            addCriterion("PARENT_AGENT_ID >", value, "parentAgentId");
            return (Criteria) this;
        }

        public Criteria andParentAgentIdGreaterThanOrEqualTo(String value) {
            addCriterion("PARENT_AGENT_ID >=", value, "parentAgentId");
            return (Criteria) this;
        }

        public Criteria andParentAgentIdLessThan(String value) {
            addCriterion("PARENT_AGENT_ID <", value, "parentAgentId");
            return (Criteria) this;
        }

        public Criteria andParentAgentIdLessThanOrEqualTo(String value) {
            addCriterion("PARENT_AGENT_ID <=", value, "parentAgentId");
            return (Criteria) this;
        }

        public Criteria andParentAgentIdLike(String value) {
            addCriterion("PARENT_AGENT_ID like", value, "parentAgentId");
            return (Criteria) this;
        }

        public Criteria andParentAgentIdNotLike(String value) {
            addCriterion("PARENT_AGENT_ID not like", value, "parentAgentId");
            return (Criteria) this;
        }

        public Criteria andParentAgentIdIn(List<String> values) {
            addCriterion("PARENT_AGENT_ID in", values, "parentAgentId");
            return (Criteria) this;
        }

        public Criteria andParentAgentIdNotIn(List<String> values) {
            addCriterion("PARENT_AGENT_ID not in", values, "parentAgentId");
            return (Criteria) this;
        }

        public Criteria andParentAgentIdBetween(String value1, String value2) {
            addCriterion("PARENT_AGENT_ID between", value1, value2, "parentAgentId");
            return (Criteria) this;
        }

        public Criteria andParentAgentIdNotBetween(String value1, String value2) {
            addCriterion("PARENT_AGENT_ID not between", value1, value2, "parentAgentId");
            return (Criteria) this;
        }

        public Criteria andParentAgentNameIsNull() {
            addCriterion("PARENT_AGENT_NAME is null");
            return (Criteria) this;
        }

        public Criteria andParentAgentNameIsNotNull() {
            addCriterion("PARENT_AGENT_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andParentAgentNameEqualTo(String value) {
            addCriterion("PARENT_AGENT_NAME =", value, "parentAgentName");
            return (Criteria) this;
        }

        public Criteria andParentAgentNameNotEqualTo(String value) {
            addCriterion("PARENT_AGENT_NAME <>", value, "parentAgentName");
            return (Criteria) this;
        }

        public Criteria andParentAgentNameGreaterThan(String value) {
            addCriterion("PARENT_AGENT_NAME >", value, "parentAgentName");
            return (Criteria) this;
        }

        public Criteria andParentAgentNameGreaterThanOrEqualTo(String value) {
            addCriterion("PARENT_AGENT_NAME >=", value, "parentAgentName");
            return (Criteria) this;
        }

        public Criteria andParentAgentNameLessThan(String value) {
            addCriterion("PARENT_AGENT_NAME <", value, "parentAgentName");
            return (Criteria) this;
        }

        public Criteria andParentAgentNameLessThanOrEqualTo(String value) {
            addCriterion("PARENT_AGENT_NAME <=", value, "parentAgentName");
            return (Criteria) this;
        }

        public Criteria andParentAgentNameLike(String value) {
            addCriterion("PARENT_AGENT_NAME like", value, "parentAgentName");
            return (Criteria) this;
        }

        public Criteria andParentAgentNameNotLike(String value) {
            addCriterion("PARENT_AGENT_NAME not like", value, "parentAgentName");
            return (Criteria) this;
        }

        public Criteria andParentAgentNameIn(List<String> values) {
            addCriterion("PARENT_AGENT_NAME in", values, "parentAgentName");
            return (Criteria) this;
        }

        public Criteria andParentAgentNameNotIn(List<String> values) {
            addCriterion("PARENT_AGENT_NAME not in", values, "parentAgentName");
            return (Criteria) this;
        }

        public Criteria andParentAgentNameBetween(String value1, String value2) {
            addCriterion("PARENT_AGENT_NAME between", value1, value2, "parentAgentName");
            return (Criteria) this;
        }

        public Criteria andParentAgentNameNotBetween(String value1, String value2) {
            addCriterion("PARENT_AGENT_NAME not between", value1, value2, "parentAgentName");
            return (Criteria) this;
        }

        public Criteria andPreLeftAmtIsNull() {
            addCriterion("PRE_LEFT_AMT is null");
            return (Criteria) this;
        }

        public Criteria andPreLeftAmtIsNotNull() {
            addCriterion("PRE_LEFT_AMT is not null");
            return (Criteria) this;
        }

        public Criteria andPreLeftAmtEqualTo(BigDecimal value) {
            addCriterion("PRE_LEFT_AMT =", value, "preLeftAmt");
            return (Criteria) this;
        }

        public Criteria andPreLeftAmtNotEqualTo(BigDecimal value) {
            addCriterion("PRE_LEFT_AMT <>", value, "preLeftAmt");
            return (Criteria) this;
        }

        public Criteria andPreLeftAmtGreaterThan(BigDecimal value) {
            addCriterion("PRE_LEFT_AMT >", value, "preLeftAmt");
            return (Criteria) this;
        }

        public Criteria andPreLeftAmtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("PRE_LEFT_AMT >=", value, "preLeftAmt");
            return (Criteria) this;
        }

        public Criteria andPreLeftAmtLessThan(BigDecimal value) {
            addCriterion("PRE_LEFT_AMT <", value, "preLeftAmt");
            return (Criteria) this;
        }

        public Criteria andPreLeftAmtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("PRE_LEFT_AMT <=", value, "preLeftAmt");
            return (Criteria) this;
        }

        public Criteria andPreLeftAmtIn(List<BigDecimal> values) {
            addCriterion("PRE_LEFT_AMT in", values, "preLeftAmt");
            return (Criteria) this;
        }

        public Criteria andPreLeftAmtNotIn(List<BigDecimal> values) {
            addCriterion("PRE_LEFT_AMT not in", values, "preLeftAmt");
            return (Criteria) this;
        }

        public Criteria andPreLeftAmtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("PRE_LEFT_AMT between", value1, value2, "preLeftAmt");
            return (Criteria) this;
        }

        public Criteria andPreLeftAmtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("PRE_LEFT_AMT not between", value1, value2, "preLeftAmt");
            return (Criteria) this;
        }

        public Criteria andDayProfitAmtIsNull() {
            addCriterion("DAY_PROFIT_AMT is null");
            return (Criteria) this;
        }

        public Criteria andDayProfitAmtIsNotNull() {
            addCriterion("DAY_PROFIT_AMT is not null");
            return (Criteria) this;
        }

        public Criteria andDayProfitAmtEqualTo(BigDecimal value) {
            addCriterion("DAY_PROFIT_AMT =", value, "dayProfitAmt");
            return (Criteria) this;
        }

        public Criteria andDayProfitAmtNotEqualTo(BigDecimal value) {
            addCriterion("DAY_PROFIT_AMT <>", value, "dayProfitAmt");
            return (Criteria) this;
        }

        public Criteria andDayProfitAmtGreaterThan(BigDecimal value) {
            addCriterion("DAY_PROFIT_AMT >", value, "dayProfitAmt");
            return (Criteria) this;
        }

        public Criteria andDayProfitAmtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("DAY_PROFIT_AMT >=", value, "dayProfitAmt");
            return (Criteria) this;
        }

        public Criteria andDayProfitAmtLessThan(BigDecimal value) {
            addCriterion("DAY_PROFIT_AMT <", value, "dayProfitAmt");
            return (Criteria) this;
        }

        public Criteria andDayProfitAmtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("DAY_PROFIT_AMT <=", value, "dayProfitAmt");
            return (Criteria) this;
        }

        public Criteria andDayProfitAmtIn(List<BigDecimal> values) {
            addCriterion("DAY_PROFIT_AMT in", values, "dayProfitAmt");
            return (Criteria) this;
        }

        public Criteria andDayProfitAmtNotIn(List<BigDecimal> values) {
            addCriterion("DAY_PROFIT_AMT not in", values, "dayProfitAmt");
            return (Criteria) this;
        }

        public Criteria andDayProfitAmtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("DAY_PROFIT_AMT between", value1, value2, "dayProfitAmt");
            return (Criteria) this;
        }

        public Criteria andDayProfitAmtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("DAY_PROFIT_AMT not between", value1, value2, "dayProfitAmt");
            return (Criteria) this;
        }

        public Criteria andDayBackAmtIsNull() {
            addCriterion("DAY_BACK_AMT is null");
            return (Criteria) this;
        }

        public Criteria andDayBackAmtIsNotNull() {
            addCriterion("DAY_BACK_AMT is not null");
            return (Criteria) this;
        }

        public Criteria andDayBackAmtEqualTo(BigDecimal value) {
            addCriterion("DAY_BACK_AMT =", value, "dayBackAmt");
            return (Criteria) this;
        }

        public Criteria andDayBackAmtNotEqualTo(BigDecimal value) {
            addCriterion("DAY_BACK_AMT <>", value, "dayBackAmt");
            return (Criteria) this;
        }

        public Criteria andDayBackAmtGreaterThan(BigDecimal value) {
            addCriterion("DAY_BACK_AMT >", value, "dayBackAmt");
            return (Criteria) this;
        }

        public Criteria andDayBackAmtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("DAY_BACK_AMT >=", value, "dayBackAmt");
            return (Criteria) this;
        }

        public Criteria andDayBackAmtLessThan(BigDecimal value) {
            addCriterion("DAY_BACK_AMT <", value, "dayBackAmt");
            return (Criteria) this;
        }

        public Criteria andDayBackAmtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("DAY_BACK_AMT <=", value, "dayBackAmt");
            return (Criteria) this;
        }

        public Criteria andDayBackAmtIn(List<BigDecimal> values) {
            addCriterion("DAY_BACK_AMT in", values, "dayBackAmt");
            return (Criteria) this;
        }

        public Criteria andDayBackAmtNotIn(List<BigDecimal> values) {
            addCriterion("DAY_BACK_AMT not in", values, "dayBackAmt");
            return (Criteria) this;
        }

        public Criteria andDayBackAmtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("DAY_BACK_AMT between", value1, value2, "dayBackAmt");
            return (Criteria) this;
        }

        public Criteria andDayBackAmtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("DAY_BACK_AMT not between", value1, value2, "dayBackAmt");
            return (Criteria) this;
        }

        public Criteria andPreProfitMonthAmtIsNull() {
            addCriterion("PRE_PROFIT_MONTH_AMT is null");
            return (Criteria) this;
        }

        public Criteria andPreProfitMonthAmtIsNotNull() {
            addCriterion("PRE_PROFIT_MONTH_AMT is not null");
            return (Criteria) this;
        }

        public Criteria andPreProfitMonthAmtEqualTo(BigDecimal value) {
            addCriterion("PRE_PROFIT_MONTH_AMT =", value, "preProfitMonthAmt");
            return (Criteria) this;
        }

        public Criteria andPreProfitMonthAmtNotEqualTo(BigDecimal value) {
            addCriterion("PRE_PROFIT_MONTH_AMT <>", value, "preProfitMonthAmt");
            return (Criteria) this;
        }

        public Criteria andPreProfitMonthAmtGreaterThan(BigDecimal value) {
            addCriterion("PRE_PROFIT_MONTH_AMT >", value, "preProfitMonthAmt");
            return (Criteria) this;
        }

        public Criteria andPreProfitMonthAmtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("PRE_PROFIT_MONTH_AMT >=", value, "preProfitMonthAmt");
            return (Criteria) this;
        }

        public Criteria andPreProfitMonthAmtLessThan(BigDecimal value) {
            addCriterion("PRE_PROFIT_MONTH_AMT <", value, "preProfitMonthAmt");
            return (Criteria) this;
        }

        public Criteria andPreProfitMonthAmtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("PRE_PROFIT_MONTH_AMT <=", value, "preProfitMonthAmt");
            return (Criteria) this;
        }

        public Criteria andPreProfitMonthAmtIn(List<BigDecimal> values) {
            addCriterion("PRE_PROFIT_MONTH_AMT in", values, "preProfitMonthAmt");
            return (Criteria) this;
        }

        public Criteria andPreProfitMonthAmtNotIn(List<BigDecimal> values) {
            addCriterion("PRE_PROFIT_MONTH_AMT not in", values, "preProfitMonthAmt");
            return (Criteria) this;
        }

        public Criteria andPreProfitMonthAmtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("PRE_PROFIT_MONTH_AMT between", value1, value2, "preProfitMonthAmt");
            return (Criteria) this;
        }

        public Criteria andPreProfitMonthAmtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("PRE_PROFIT_MONTH_AMT not between", value1, value2, "preProfitMonthAmt");
            return (Criteria) this;
        }

        public Criteria andPreProfitMonthBlAmtIsNull() {
            addCriterion("PRE_PROFIT_MONTH_BL_AMT is null");
            return (Criteria) this;
        }

        public Criteria andPreProfitMonthBlAmtIsNotNull() {
            addCriterion("PRE_PROFIT_MONTH_BL_AMT is not null");
            return (Criteria) this;
        }

        public Criteria andPreProfitMonthBlAmtEqualTo(BigDecimal value) {
            addCriterion("PRE_PROFIT_MONTH_BL_AMT =", value, "preProfitMonthBlAmt");
            return (Criteria) this;
        }

        public Criteria andPreProfitMonthBlAmtNotEqualTo(BigDecimal value) {
            addCriterion("PRE_PROFIT_MONTH_BL_AMT <>", value, "preProfitMonthBlAmt");
            return (Criteria) this;
        }

        public Criteria andPreProfitMonthBlAmtGreaterThan(BigDecimal value) {
            addCriterion("PRE_PROFIT_MONTH_BL_AMT >", value, "preProfitMonthBlAmt");
            return (Criteria) this;
        }

        public Criteria andPreProfitMonthBlAmtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("PRE_PROFIT_MONTH_BL_AMT >=", value, "preProfitMonthBlAmt");
            return (Criteria) this;
        }

        public Criteria andPreProfitMonthBlAmtLessThan(BigDecimal value) {
            addCriterion("PRE_PROFIT_MONTH_BL_AMT <", value, "preProfitMonthBlAmt");
            return (Criteria) this;
        }

        public Criteria andPreProfitMonthBlAmtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("PRE_PROFIT_MONTH_BL_AMT <=", value, "preProfitMonthBlAmt");
            return (Criteria) this;
        }

        public Criteria andPreProfitMonthBlAmtIn(List<BigDecimal> values) {
            addCriterion("PRE_PROFIT_MONTH_BL_AMT in", values, "preProfitMonthBlAmt");
            return (Criteria) this;
        }

        public Criteria andPreProfitMonthBlAmtNotIn(List<BigDecimal> values) {
            addCriterion("PRE_PROFIT_MONTH_BL_AMT not in", values, "preProfitMonthBlAmt");
            return (Criteria) this;
        }

        public Criteria andPreProfitMonthBlAmtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("PRE_PROFIT_MONTH_BL_AMT between", value1, value2, "preProfitMonthBlAmt");
            return (Criteria) this;
        }

        public Criteria andPreProfitMonthBlAmtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("PRE_PROFIT_MONTH_BL_AMT not between", value1, value2, "preProfitMonthBlAmt");
            return (Criteria) this;
        }

        public Criteria andAddInvoiceAmtIsNull() {
            addCriterion("ADD_INVOICE_AMT is null");
            return (Criteria) this;
        }

        public Criteria andAddInvoiceAmtIsNotNull() {
            addCriterion("ADD_INVOICE_AMT is not null");
            return (Criteria) this;
        }

        public Criteria andAddInvoiceAmtEqualTo(BigDecimal value) {
            addCriterion("ADD_INVOICE_AMT =", value, "addInvoiceAmt");
            return (Criteria) this;
        }

        public Criteria andAddInvoiceAmtNotEqualTo(BigDecimal value) {
            addCriterion("ADD_INVOICE_AMT <>", value, "addInvoiceAmt");
            return (Criteria) this;
        }

        public Criteria andAddInvoiceAmtGreaterThan(BigDecimal value) {
            addCriterion("ADD_INVOICE_AMT >", value, "addInvoiceAmt");
            return (Criteria) this;
        }

        public Criteria andAddInvoiceAmtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("ADD_INVOICE_AMT >=", value, "addInvoiceAmt");
            return (Criteria) this;
        }

        public Criteria andAddInvoiceAmtLessThan(BigDecimal value) {
            addCriterion("ADD_INVOICE_AMT <", value, "addInvoiceAmt");
            return (Criteria) this;
        }

        public Criteria andAddInvoiceAmtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("ADD_INVOICE_AMT <=", value, "addInvoiceAmt");
            return (Criteria) this;
        }

        public Criteria andAddInvoiceAmtIn(List<BigDecimal> values) {
            addCriterion("ADD_INVOICE_AMT in", values, "addInvoiceAmt");
            return (Criteria) this;
        }

        public Criteria andAddInvoiceAmtNotIn(List<BigDecimal> values) {
            addCriterion("ADD_INVOICE_AMT not in", values, "addInvoiceAmt");
            return (Criteria) this;
        }

        public Criteria andAddInvoiceAmtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ADD_INVOICE_AMT between", value1, value2, "addInvoiceAmt");
            return (Criteria) this;
        }

        public Criteria andAddInvoiceAmtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ADD_INVOICE_AMT not between", value1, value2, "addInvoiceAmt");
            return (Criteria) this;
        }

        public Criteria andAdjustAmtIsNull() {
            addCriterion("ADJUST_AMT is null");
            return (Criteria) this;
        }

        public Criteria andAdjustAmtIsNotNull() {
            addCriterion("ADJUST_AMT is not null");
            return (Criteria) this;
        }

        public Criteria andAdjustAmtEqualTo(BigDecimal value) {
            addCriterion("ADJUST_AMT =", value, "adjustAmt");
            return (Criteria) this;
        }

        public Criteria andAdjustAmtNotEqualTo(BigDecimal value) {
            addCriterion("ADJUST_AMT <>", value, "adjustAmt");
            return (Criteria) this;
        }

        public Criteria andAdjustAmtGreaterThan(BigDecimal value) {
            addCriterion("ADJUST_AMT >", value, "adjustAmt");
            return (Criteria) this;
        }

        public Criteria andAdjustAmtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("ADJUST_AMT >=", value, "adjustAmt");
            return (Criteria) this;
        }

        public Criteria andAdjustAmtLessThan(BigDecimal value) {
            addCriterion("ADJUST_AMT <", value, "adjustAmt");
            return (Criteria) this;
        }

        public Criteria andAdjustAmtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("ADJUST_AMT <=", value, "adjustAmt");
            return (Criteria) this;
        }

        public Criteria andAdjustAmtIn(List<BigDecimal> values) {
            addCriterion("ADJUST_AMT in", values, "adjustAmt");
            return (Criteria) this;
        }

        public Criteria andAdjustAmtNotIn(List<BigDecimal> values) {
            addCriterion("ADJUST_AMT not in", values, "adjustAmt");
            return (Criteria) this;
        }

        public Criteria andAdjustAmtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ADJUST_AMT between", value1, value2, "adjustAmt");
            return (Criteria) this;
        }

        public Criteria andAdjustAmtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ADJUST_AMT not between", value1, value2, "adjustAmt");
            return (Criteria) this;
        }

        public Criteria andAdjustAccountIsNull() {
            addCriterion("ADJUST_ACCOUNT is null");
            return (Criteria) this;
        }

        public Criteria andAdjustAccountIsNotNull() {
            addCriterion("ADJUST_ACCOUNT is not null");
            return (Criteria) this;
        }

        public Criteria andAdjustAccountEqualTo(String value) {
            addCriterion("ADJUST_ACCOUNT =", value, "adjustAccount");
            return (Criteria) this;
        }

        public Criteria andAdjustAccountNotEqualTo(String value) {
            addCriterion("ADJUST_ACCOUNT <>", value, "adjustAccount");
            return (Criteria) this;
        }

        public Criteria andAdjustAccountGreaterThan(String value) {
            addCriterion("ADJUST_ACCOUNT >", value, "adjustAccount");
            return (Criteria) this;
        }

        public Criteria andAdjustAccountGreaterThanOrEqualTo(String value) {
            addCriterion("ADJUST_ACCOUNT >=", value, "adjustAccount");
            return (Criteria) this;
        }

        public Criteria andAdjustAccountLessThan(String value) {
            addCriterion("ADJUST_ACCOUNT <", value, "adjustAccount");
            return (Criteria) this;
        }

        public Criteria andAdjustAccountLessThanOrEqualTo(String value) {
            addCriterion("ADJUST_ACCOUNT <=", value, "adjustAccount");
            return (Criteria) this;
        }

        public Criteria andAdjustAccountLike(String value) {
            addCriterion("ADJUST_ACCOUNT like", value, "adjustAccount");
            return (Criteria) this;
        }

        public Criteria andAdjustAccountNotLike(String value) {
            addCriterion("ADJUST_ACCOUNT not like", value, "adjustAccount");
            return (Criteria) this;
        }

        public Criteria andAdjustAccountIn(List<String> values) {
            addCriterion("ADJUST_ACCOUNT in", values, "adjustAccount");
            return (Criteria) this;
        }

        public Criteria andAdjustAccountNotIn(List<String> values) {
            addCriterion("ADJUST_ACCOUNT not in", values, "adjustAccount");
            return (Criteria) this;
        }

        public Criteria andAdjustAccountBetween(String value1, String value2) {
            addCriterion("ADJUST_ACCOUNT between", value1, value2, "adjustAccount");
            return (Criteria) this;
        }

        public Criteria andAdjustAccountNotBetween(String value1, String value2) {
            addCriterion("ADJUST_ACCOUNT not between", value1, value2, "adjustAccount");
            return (Criteria) this;
        }

        public Criteria andAdjustResonIsNull() {
            addCriterion("ADJUST_RESON is null");
            return (Criteria) this;
        }

        public Criteria andAdjustResonIsNotNull() {
            addCriterion("ADJUST_RESON is not null");
            return (Criteria) this;
        }

        public Criteria andAdjustResonEqualTo(String value) {
            addCriterion("ADJUST_RESON =", value, "adjustReson");
            return (Criteria) this;
        }

        public Criteria andAdjustResonNotEqualTo(String value) {
            addCriterion("ADJUST_RESON <>", value, "adjustReson");
            return (Criteria) this;
        }

        public Criteria andAdjustResonGreaterThan(String value) {
            addCriterion("ADJUST_RESON >", value, "adjustReson");
            return (Criteria) this;
        }

        public Criteria andAdjustResonGreaterThanOrEqualTo(String value) {
            addCriterion("ADJUST_RESON >=", value, "adjustReson");
            return (Criteria) this;
        }

        public Criteria andAdjustResonLessThan(String value) {
            addCriterion("ADJUST_RESON <", value, "adjustReson");
            return (Criteria) this;
        }

        public Criteria andAdjustResonLessThanOrEqualTo(String value) {
            addCriterion("ADJUST_RESON <=", value, "adjustReson");
            return (Criteria) this;
        }

        public Criteria andAdjustResonLike(String value) {
            addCriterion("ADJUST_RESON like", value, "adjustReson");
            return (Criteria) this;
        }

        public Criteria andAdjustResonNotLike(String value) {
            addCriterion("ADJUST_RESON not like", value, "adjustReson");
            return (Criteria) this;
        }

        public Criteria andAdjustResonIn(List<String> values) {
            addCriterion("ADJUST_RESON in", values, "adjustReson");
            return (Criteria) this;
        }

        public Criteria andAdjustResonNotIn(List<String> values) {
            addCriterion("ADJUST_RESON not in", values, "adjustReson");
            return (Criteria) this;
        }

        public Criteria andAdjustResonBetween(String value1, String value2) {
            addCriterion("ADJUST_RESON between", value1, value2, "adjustReson");
            return (Criteria) this;
        }

        public Criteria andAdjustResonNotBetween(String value1, String value2) {
            addCriterion("ADJUST_RESON not between", value1, value2, "adjustReson");
            return (Criteria) this;
        }

        public Criteria andAdjustTimeIsNull() {
            addCriterion("ADJUST_TIME is null");
            return (Criteria) this;
        }

        public Criteria andAdjustTimeIsNotNull() {
            addCriterion("ADJUST_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andAdjustTimeEqualTo(String value) {
            addCriterion("ADJUST_TIME =", value, "adjustTime");
            return (Criteria) this;
        }

        public Criteria andAdjustTimeNotEqualTo(String value) {
            addCriterion("ADJUST_TIME <>", value, "adjustTime");
            return (Criteria) this;
        }

        public Criteria andAdjustTimeGreaterThan(String value) {
            addCriterion("ADJUST_TIME >", value, "adjustTime");
            return (Criteria) this;
        }

        public Criteria andAdjustTimeGreaterThanOrEqualTo(String value) {
            addCriterion("ADJUST_TIME >=", value, "adjustTime");
            return (Criteria) this;
        }

        public Criteria andAdjustTimeLessThan(String value) {
            addCriterion("ADJUST_TIME <", value, "adjustTime");
            return (Criteria) this;
        }

        public Criteria andAdjustTimeLessThanOrEqualTo(String value) {
            addCriterion("ADJUST_TIME <=", value, "adjustTime");
            return (Criteria) this;
        }

        public Criteria andAdjustTimeLike(String value) {
            addCriterion("ADJUST_TIME like", value, "adjustTime");
            return (Criteria) this;
        }

        public Criteria andAdjustTimeNotLike(String value) {
            addCriterion("ADJUST_TIME not like", value, "adjustTime");
            return (Criteria) this;
        }

        public Criteria andAdjustTimeIn(List<String> values) {
            addCriterion("ADJUST_TIME in", values, "adjustTime");
            return (Criteria) this;
        }

        public Criteria andAdjustTimeNotIn(List<String> values) {
            addCriterion("ADJUST_TIME not in", values, "adjustTime");
            return (Criteria) this;
        }

        public Criteria andAdjustTimeBetween(String value1, String value2) {
            addCriterion("ADJUST_TIME between", value1, value2, "adjustTime");
            return (Criteria) this;
        }

        public Criteria andAdjustTimeNotBetween(String value1, String value2) {
            addCriterion("ADJUST_TIME not between", value1, value2, "adjustTime");
            return (Criteria) this;
        }

        public Criteria andOwnInvoiceIsNull() {
            addCriterion("OWN_INVOICE is null");
            return (Criteria) this;
        }

        public Criteria andOwnInvoiceIsNotNull() {
            addCriterion("OWN_INVOICE is not null");
            return (Criteria) this;
        }

        public Criteria andOwnInvoiceEqualTo(BigDecimal value) {
            addCriterion("OWN_INVOICE =", value, "ownInvoice");
            return (Criteria) this;
        }

        public Criteria andOwnInvoiceNotEqualTo(BigDecimal value) {
            addCriterion("OWN_INVOICE <>", value, "ownInvoice");
            return (Criteria) this;
        }

        public Criteria andOwnInvoiceGreaterThan(BigDecimal value) {
            addCriterion("OWN_INVOICE >", value, "ownInvoice");
            return (Criteria) this;
        }

        public Criteria andOwnInvoiceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("OWN_INVOICE >=", value, "ownInvoice");
            return (Criteria) this;
        }

        public Criteria andOwnInvoiceLessThan(BigDecimal value) {
            addCriterion("OWN_INVOICE <", value, "ownInvoice");
            return (Criteria) this;
        }

        public Criteria andOwnInvoiceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("OWN_INVOICE <=", value, "ownInvoice");
            return (Criteria) this;
        }

        public Criteria andOwnInvoiceIn(List<BigDecimal> values) {
            addCriterion("OWN_INVOICE in", values, "ownInvoice");
            return (Criteria) this;
        }

        public Criteria andOwnInvoiceNotIn(List<BigDecimal> values) {
            addCriterion("OWN_INVOICE not in", values, "ownInvoice");
            return (Criteria) this;
        }

        public Criteria andOwnInvoiceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("OWN_INVOICE between", value1, value2, "ownInvoice");
            return (Criteria) this;
        }

        public Criteria andOwnInvoiceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("OWN_INVOICE not between", value1, value2, "ownInvoice");
            return (Criteria) this;
        }

        public Criteria andTaxRateIsNull() {
            addCriterion("TAX_RATE is null");
            return (Criteria) this;
        }

        public Criteria andTaxRateIsNotNull() {
            addCriterion("TAX_RATE is not null");
            return (Criteria) this;
        }

        public Criteria andTaxRateEqualTo(BigDecimal value) {
            addCriterion("TAX_RATE =", value, "taxRate");
            return (Criteria) this;
        }

        public Criteria andTaxRateNotEqualTo(BigDecimal value) {
            addCriterion("TAX_RATE <>", value, "taxRate");
            return (Criteria) this;
        }

        public Criteria andTaxRateGreaterThan(BigDecimal value) {
            addCriterion("TAX_RATE >", value, "taxRate");
            return (Criteria) this;
        }

        public Criteria andTaxRateGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("TAX_RATE >=", value, "taxRate");
            return (Criteria) this;
        }

        public Criteria andTaxRateLessThan(BigDecimal value) {
            addCriterion("TAX_RATE <", value, "taxRate");
            return (Criteria) this;
        }

        public Criteria andTaxRateLessThanOrEqualTo(BigDecimal value) {
            addCriterion("TAX_RATE <=", value, "taxRate");
            return (Criteria) this;
        }

        public Criteria andTaxRateIn(List<BigDecimal> values) {
            addCriterion("TAX_RATE in", values, "taxRate");
            return (Criteria) this;
        }

        public Criteria andTaxRateNotIn(List<BigDecimal> values) {
            addCriterion("TAX_RATE not in", values, "taxRate");
            return (Criteria) this;
        }

        public Criteria andTaxRateBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("TAX_RATE between", value1, value2, "taxRate");
            return (Criteria) this;
        }

        public Criteria andTaxRateNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("TAX_RATE not between", value1, value2, "taxRate");
            return (Criteria) this;
        }

        public Criteria andAddTaxAmtIsNull() {
            addCriterion("ADD_TAX_AMT is null");
            return (Criteria) this;
        }

        public Criteria andAddTaxAmtIsNotNull() {
            addCriterion("ADD_TAX_AMT is not null");
            return (Criteria) this;
        }

        public Criteria andAddTaxAmtEqualTo(BigDecimal value) {
            addCriterion("ADD_TAX_AMT =", value, "addTaxAmt");
            return (Criteria) this;
        }

        public Criteria andAddTaxAmtNotEqualTo(BigDecimal value) {
            addCriterion("ADD_TAX_AMT <>", value, "addTaxAmt");
            return (Criteria) this;
        }

        public Criteria andAddTaxAmtGreaterThan(BigDecimal value) {
            addCriterion("ADD_TAX_AMT >", value, "addTaxAmt");
            return (Criteria) this;
        }

        public Criteria andAddTaxAmtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("ADD_TAX_AMT >=", value, "addTaxAmt");
            return (Criteria) this;
        }

        public Criteria andAddTaxAmtLessThan(BigDecimal value) {
            addCriterion("ADD_TAX_AMT <", value, "addTaxAmt");
            return (Criteria) this;
        }

        public Criteria andAddTaxAmtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("ADD_TAX_AMT <=", value, "addTaxAmt");
            return (Criteria) this;
        }

        public Criteria andAddTaxAmtIn(List<BigDecimal> values) {
            addCriterion("ADD_TAX_AMT in", values, "addTaxAmt");
            return (Criteria) this;
        }

        public Criteria andAddTaxAmtNotIn(List<BigDecimal> values) {
            addCriterion("ADD_TAX_AMT not in", values, "addTaxAmt");
            return (Criteria) this;
        }

        public Criteria andAddTaxAmtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ADD_TAX_AMT between", value1, value2, "addTaxAmt");
            return (Criteria) this;
        }

        public Criteria andAddTaxAmtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ADD_TAX_AMT not between", value1, value2, "addTaxAmt");
            return (Criteria) this;
        }

        public Criteria andRealTaxAmtIsNull() {
            addCriterion("REAL_TAX_AMT is null");
            return (Criteria) this;
        }

        public Criteria andRealTaxAmtIsNotNull() {
            addCriterion("REAL_TAX_AMT is not null");
            return (Criteria) this;
        }

        public Criteria andRealTaxAmtEqualTo(BigDecimal value) {
            addCriterion("REAL_TAX_AMT =", value, "realTaxAmt");
            return (Criteria) this;
        }

        public Criteria andRealTaxAmtNotEqualTo(BigDecimal value) {
            addCriterion("REAL_TAX_AMT <>", value, "realTaxAmt");
            return (Criteria) this;
        }

        public Criteria andRealTaxAmtGreaterThan(BigDecimal value) {
            addCriterion("REAL_TAX_AMT >", value, "realTaxAmt");
            return (Criteria) this;
        }

        public Criteria andRealTaxAmtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("REAL_TAX_AMT >=", value, "realTaxAmt");
            return (Criteria) this;
        }

        public Criteria andRealTaxAmtLessThan(BigDecimal value) {
            addCriterion("REAL_TAX_AMT <", value, "realTaxAmt");
            return (Criteria) this;
        }

        public Criteria andRealTaxAmtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("REAL_TAX_AMT <=", value, "realTaxAmt");
            return (Criteria) this;
        }

        public Criteria andRealTaxAmtIn(List<BigDecimal> values) {
            addCriterion("REAL_TAX_AMT in", values, "realTaxAmt");
            return (Criteria) this;
        }

        public Criteria andRealTaxAmtNotIn(List<BigDecimal> values) {
            addCriterion("REAL_TAX_AMT not in", values, "realTaxAmt");
            return (Criteria) this;
        }

        public Criteria andRealTaxAmtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("REAL_TAX_AMT between", value1, value2, "realTaxAmt");
            return (Criteria) this;
        }

        public Criteria andRealTaxAmtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("REAL_TAX_AMT not between", value1, value2, "realTaxAmt");
            return (Criteria) this;
        }

        public Criteria andNotDeductionTaxAmtIsNull() {
            addCriterion("NOT_DEDUCTION_TAX_AMT is null");
            return (Criteria) this;
        }

        public Criteria andNotDeductionTaxAmtIsNotNull() {
            addCriterion("NOT_DEDUCTION_TAX_AMT is not null");
            return (Criteria) this;
        }

        public Criteria andNotDeductionTaxAmtEqualTo(BigDecimal value) {
            addCriterion("NOT_DEDUCTION_TAX_AMT =", value, "notDeductionTaxAmt");
            return (Criteria) this;
        }

        public Criteria andNotDeductionTaxAmtNotEqualTo(BigDecimal value) {
            addCriterion("NOT_DEDUCTION_TAX_AMT <>", value, "notDeductionTaxAmt");
            return (Criteria) this;
        }

        public Criteria andNotDeductionTaxAmtGreaterThan(BigDecimal value) {
            addCriterion("NOT_DEDUCTION_TAX_AMT >", value, "notDeductionTaxAmt");
            return (Criteria) this;
        }

        public Criteria andNotDeductionTaxAmtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("NOT_DEDUCTION_TAX_AMT >=", value, "notDeductionTaxAmt");
            return (Criteria) this;
        }

        public Criteria andNotDeductionTaxAmtLessThan(BigDecimal value) {
            addCriterion("NOT_DEDUCTION_TAX_AMT <", value, "notDeductionTaxAmt");
            return (Criteria) this;
        }

        public Criteria andNotDeductionTaxAmtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("NOT_DEDUCTION_TAX_AMT <=", value, "notDeductionTaxAmt");
            return (Criteria) this;
        }

        public Criteria andNotDeductionTaxAmtIn(List<BigDecimal> values) {
            addCriterion("NOT_DEDUCTION_TAX_AMT in", values, "notDeductionTaxAmt");
            return (Criteria) this;
        }

        public Criteria andNotDeductionTaxAmtNotIn(List<BigDecimal> values) {
            addCriterion("NOT_DEDUCTION_TAX_AMT not in", values, "notDeductionTaxAmt");
            return (Criteria) this;
        }

        public Criteria andNotDeductionTaxAmtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("NOT_DEDUCTION_TAX_AMT between", value1, value2, "notDeductionTaxAmt");
            return (Criteria) this;
        }

        public Criteria andNotDeductionTaxAmtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("NOT_DEDUCTION_TAX_AMT not between", value1, value2, "notDeductionTaxAmt");
            return (Criteria) this;
        }

        public Criteria andPreNotDeductionAmt1IsNull() {
            addCriterion("PRE_NOT_DEDUCTION_AMT1 is null");
            return (Criteria) this;
        }

        public Criteria andPreNotDeductionAmt1IsNotNull() {
            addCriterion("PRE_NOT_DEDUCTION_AMT1 is not null");
            return (Criteria) this;
        }

        public Criteria andPreNotDeductionAmt1EqualTo(BigDecimal value) {
            addCriterion("PRE_NOT_DEDUCTION_AMT1 =", value, "preNotDeductionAmt1");
            return (Criteria) this;
        }

        public Criteria andPreNotDeductionAmt1NotEqualTo(BigDecimal value) {
            addCriterion("PRE_NOT_DEDUCTION_AMT1 <>", value, "preNotDeductionAmt1");
            return (Criteria) this;
        }

        public Criteria andPreNotDeductionAmt1GreaterThan(BigDecimal value) {
            addCriterion("PRE_NOT_DEDUCTION_AMT1 >", value, "preNotDeductionAmt1");
            return (Criteria) this;
        }

        public Criteria andPreNotDeductionAmt1GreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("PRE_NOT_DEDUCTION_AMT1 >=", value, "preNotDeductionAmt1");
            return (Criteria) this;
        }

        public Criteria andPreNotDeductionAmt1LessThan(BigDecimal value) {
            addCriterion("PRE_NOT_DEDUCTION_AMT1 <", value, "preNotDeductionAmt1");
            return (Criteria) this;
        }

        public Criteria andPreNotDeductionAmt1LessThanOrEqualTo(BigDecimal value) {
            addCriterion("PRE_NOT_DEDUCTION_AMT1 <=", value, "preNotDeductionAmt1");
            return (Criteria) this;
        }

        public Criteria andPreNotDeductionAmt1In(List<BigDecimal> values) {
            addCriterion("PRE_NOT_DEDUCTION_AMT1 in", values, "preNotDeductionAmt1");
            return (Criteria) this;
        }

        public Criteria andPreNotDeductionAmt1NotIn(List<BigDecimal> values) {
            addCriterion("PRE_NOT_DEDUCTION_AMT1 not in", values, "preNotDeductionAmt1");
            return (Criteria) this;
        }

        public Criteria andPreNotDeductionAmt1Between(BigDecimal value1, BigDecimal value2) {
            addCriterion("PRE_NOT_DEDUCTION_AMT1 between", value1, value2, "preNotDeductionAmt1");
            return (Criteria) this;
        }

        public Criteria andPreNotDeductionAmt1NotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("PRE_NOT_DEDUCTION_AMT1 not between", value1, value2, "preNotDeductionAmt1");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("CREATE_TIME is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("CREATE_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(String value) {
            addCriterion("CREATE_TIME =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(String value) {
            addCriterion("CREATE_TIME <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(String value) {
            addCriterion("CREATE_TIME >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(String value) {
            addCriterion("CREATE_TIME >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(String value) {
            addCriterion("CREATE_TIME <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(String value) {
            addCriterion("CREATE_TIME <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLike(String value) {
            addCriterion("CREATE_TIME like", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotLike(String value) {
            addCriterion("CREATE_TIME not like", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<String> values) {
            addCriterion("CREATE_TIME in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<String> values) {
            addCriterion("CREATE_TIME not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(String value1, String value2) {
            addCriterion("CREATE_TIME between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(String value1, String value2) {
            addCriterion("CREATE_TIME not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNull() {
            addCriterion("UPDATE_TIME is null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNotNull() {
            addCriterion("UPDATE_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeEqualTo(String value) {
            addCriterion("UPDATE_TIME =", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotEqualTo(String value) {
            addCriterion("UPDATE_TIME <>", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThan(String value) {
            addCriterion("UPDATE_TIME >", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThanOrEqualTo(String value) {
            addCriterion("UPDATE_TIME >=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThan(String value) {
            addCriterion("UPDATE_TIME <", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThanOrEqualTo(String value) {
            addCriterion("UPDATE_TIME <=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLike(String value) {
            addCriterion("UPDATE_TIME like", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotLike(String value) {
            addCriterion("UPDATE_TIME not like", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIn(List<String> values) {
            addCriterion("UPDATE_TIME in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotIn(List<String> values) {
            addCriterion("UPDATE_TIME not in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeBetween(String value1, String value2) {
            addCriterion("UPDATE_TIME between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotBetween(String value1, String value2) {
            addCriterion("UPDATE_TIME not between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andDrAddInvoiceAmtIsNull() {
            addCriterion("DR_ADD_INVOICE_AMT is null");
            return (Criteria) this;
        }

        public Criteria andDrAddInvoiceAmtIsNotNull() {
            addCriterion("DR_ADD_INVOICE_AMT is not null");
            return (Criteria) this;
        }

        public Criteria andDrAddInvoiceAmtEqualTo(BigDecimal value) {
            addCriterion("DR_ADD_INVOICE_AMT =", value, "drAddInvoiceAmt");
            return (Criteria) this;
        }

        public Criteria andDrAddInvoiceAmtNotEqualTo(BigDecimal value) {
            addCriterion("DR_ADD_INVOICE_AMT <>", value, "drAddInvoiceAmt");
            return (Criteria) this;
        }

        public Criteria andDrAddInvoiceAmtGreaterThan(BigDecimal value) {
            addCriterion("DR_ADD_INVOICE_AMT >", value, "drAddInvoiceAmt");
            return (Criteria) this;
        }

        public Criteria andDrAddInvoiceAmtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("DR_ADD_INVOICE_AMT >=", value, "drAddInvoiceAmt");
            return (Criteria) this;
        }

        public Criteria andDrAddInvoiceAmtLessThan(BigDecimal value) {
            addCriterion("DR_ADD_INVOICE_AMT <", value, "drAddInvoiceAmt");
            return (Criteria) this;
        }

        public Criteria andDrAddInvoiceAmtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("DR_ADD_INVOICE_AMT <=", value, "drAddInvoiceAmt");
            return (Criteria) this;
        }

        public Criteria andDrAddInvoiceAmtIn(List<BigDecimal> values) {
            addCriterion("DR_ADD_INVOICE_AMT in", values, "drAddInvoiceAmt");
            return (Criteria) this;
        }

        public Criteria andDrAddInvoiceAmtNotIn(List<BigDecimal> values) {
            addCriterion("DR_ADD_INVOICE_AMT not in", values, "drAddInvoiceAmt");
            return (Criteria) this;
        }

        public Criteria andDrAddInvoiceAmtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("DR_ADD_INVOICE_AMT between", value1, value2, "drAddInvoiceAmt");
            return (Criteria) this;
        }

        public Criteria andDrAddInvoiceAmtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("DR_ADD_INVOICE_AMT not between", value1, value2, "drAddInvoiceAmt");
            return (Criteria) this;
        }

        public Criteria andShAddInvoceAmtIsNull() {
            addCriterion("SH_ADD_INVOCE_AMT is null");
            return (Criteria) this;
        }

        public Criteria andShAddInvoceAmtIsNotNull() {
            addCriterion("SH_ADD_INVOCE_AMT is not null");
            return (Criteria) this;
        }

        public Criteria andShAddInvoceAmtEqualTo(BigDecimal value) {
            addCriterion("SH_ADD_INVOCE_AMT =", value, "shAddInvoceAmt");
            return (Criteria) this;
        }

        public Criteria andShAddInvoceAmtNotEqualTo(BigDecimal value) {
            addCriterion("SH_ADD_INVOCE_AMT <>", value, "shAddInvoceAmt");
            return (Criteria) this;
        }

        public Criteria andShAddInvoceAmtGreaterThan(BigDecimal value) {
            addCriterion("SH_ADD_INVOCE_AMT >", value, "shAddInvoceAmt");
            return (Criteria) this;
        }

        public Criteria andShAddInvoceAmtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("SH_ADD_INVOCE_AMT >=", value, "shAddInvoceAmt");
            return (Criteria) this;
        }

        public Criteria andShAddInvoceAmtLessThan(BigDecimal value) {
            addCriterion("SH_ADD_INVOCE_AMT <", value, "shAddInvoceAmt");
            return (Criteria) this;
        }

        public Criteria andShAddInvoceAmtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("SH_ADD_INVOCE_AMT <=", value, "shAddInvoceAmt");
            return (Criteria) this;
        }

        public Criteria andShAddInvoceAmtIn(List<BigDecimal> values) {
            addCriterion("SH_ADD_INVOCE_AMT in", values, "shAddInvoceAmt");
            return (Criteria) this;
        }

        public Criteria andShAddInvoceAmtNotIn(List<BigDecimal> values) {
            addCriterion("SH_ADD_INVOCE_AMT not in", values, "shAddInvoceAmt");
            return (Criteria) this;
        }

        public Criteria andShAddInvoceAmtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("SH_ADD_INVOCE_AMT between", value1, value2, "shAddInvoceAmt");
            return (Criteria) this;
        }

        public Criteria andShAddInvoceAmtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("SH_ADD_INVOCE_AMT not between", value1, value2, "shAddInvoceAmt");
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