package com.ryx.credit.pojo.admin.agent;

import com.ryx.credit.common.util.Page;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AgentColinfoExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Page page;

    protected Integer limitStart;

    protected Integer limitEnd;

    public AgentColinfoExample() {
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

        public Criteria andCloTypeIsNull() {
            addCriterion("CLO_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andCloTypeIsNotNull() {
            addCriterion("CLO_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andCloTypeEqualTo(BigDecimal value) {
            addCriterion("CLO_TYPE =", value, "cloType");
            return (Criteria) this;
        }

        public Criteria andCloTypeNotEqualTo(BigDecimal value) {
            addCriterion("CLO_TYPE <>", value, "cloType");
            return (Criteria) this;
        }

        public Criteria andCloTypeGreaterThan(BigDecimal value) {
            addCriterion("CLO_TYPE >", value, "cloType");
            return (Criteria) this;
        }

        public Criteria andCloTypeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("CLO_TYPE >=", value, "cloType");
            return (Criteria) this;
        }

        public Criteria andCloTypeLessThan(BigDecimal value) {
            addCriterion("CLO_TYPE <", value, "cloType");
            return (Criteria) this;
        }

        public Criteria andCloTypeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("CLO_TYPE <=", value, "cloType");
            return (Criteria) this;
        }

        public Criteria andCloTypeIn(List<BigDecimal> values) {
            addCriterion("CLO_TYPE in", values, "cloType");
            return (Criteria) this;
        }

        public Criteria andCloTypeNotIn(List<BigDecimal> values) {
            addCriterion("CLO_TYPE not in", values, "cloType");
            return (Criteria) this;
        }

        public Criteria andCloTypeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("CLO_TYPE between", value1, value2, "cloType");
            return (Criteria) this;
        }

        public Criteria andCloTypeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("CLO_TYPE not between", value1, value2, "cloType");
            return (Criteria) this;
        }

        public Criteria andCloRealnameIsNull() {
            addCriterion("CLO_REALNAME is null");
            return (Criteria) this;
        }

        public Criteria andCloRealnameIsNotNull() {
            addCriterion("CLO_REALNAME is not null");
            return (Criteria) this;
        }

        public Criteria andCloRealnameEqualTo(String value) {
            addCriterion("CLO_REALNAME =", value, "cloRealname");
            return (Criteria) this;
        }

        public Criteria andCloRealnameNotEqualTo(String value) {
            addCriterion("CLO_REALNAME <>", value, "cloRealname");
            return (Criteria) this;
        }

        public Criteria andCloRealnameGreaterThan(String value) {
            addCriterion("CLO_REALNAME >", value, "cloRealname");
            return (Criteria) this;
        }

        public Criteria andCloRealnameGreaterThanOrEqualTo(String value) {
            addCriterion("CLO_REALNAME >=", value, "cloRealname");
            return (Criteria) this;
        }

        public Criteria andCloRealnameLessThan(String value) {
            addCriterion("CLO_REALNAME <", value, "cloRealname");
            return (Criteria) this;
        }

        public Criteria andCloRealnameLessThanOrEqualTo(String value) {
            addCriterion("CLO_REALNAME <=", value, "cloRealname");
            return (Criteria) this;
        }

        public Criteria andCloRealnameLike(String value) {
            addCriterion("CLO_REALNAME like", value, "cloRealname");
            return (Criteria) this;
        }

        public Criteria andCloRealnameNotLike(String value) {
            addCriterion("CLO_REALNAME not like", value, "cloRealname");
            return (Criteria) this;
        }

        public Criteria andCloRealnameIn(List<String> values) {
            addCriterion("CLO_REALNAME in", values, "cloRealname");
            return (Criteria) this;
        }

        public Criteria andCloRealnameNotIn(List<String> values) {
            addCriterion("CLO_REALNAME not in", values, "cloRealname");
            return (Criteria) this;
        }

        public Criteria andCloRealnameBetween(String value1, String value2) {
            addCriterion("CLO_REALNAME between", value1, value2, "cloRealname");
            return (Criteria) this;
        }

        public Criteria andCloRealnameNotBetween(String value1, String value2) {
            addCriterion("CLO_REALNAME not between", value1, value2, "cloRealname");
            return (Criteria) this;
        }

        public Criteria andCloBankIsNull() {
            addCriterion("CLO_BANK is null");
            return (Criteria) this;
        }

        public Criteria andCloBankIsNotNull() {
            addCriterion("CLO_BANK is not null");
            return (Criteria) this;
        }

        public Criteria andCloBankEqualTo(String value) {
            addCriterion("CLO_BANK =", value, "cloBank");
            return (Criteria) this;
        }

        public Criteria andCloBankNotEqualTo(String value) {
            addCriterion("CLO_BANK <>", value, "cloBank");
            return (Criteria) this;
        }

        public Criteria andCloBankGreaterThan(String value) {
            addCriterion("CLO_BANK >", value, "cloBank");
            return (Criteria) this;
        }

        public Criteria andCloBankGreaterThanOrEqualTo(String value) {
            addCriterion("CLO_BANK >=", value, "cloBank");
            return (Criteria) this;
        }

        public Criteria andCloBankLessThan(String value) {
            addCriterion("CLO_BANK <", value, "cloBank");
            return (Criteria) this;
        }

        public Criteria andCloBankLessThanOrEqualTo(String value) {
            addCriterion("CLO_BANK <=", value, "cloBank");
            return (Criteria) this;
        }

        public Criteria andCloBankLike(String value) {
            addCriterion("CLO_BANK like", value, "cloBank");
            return (Criteria) this;
        }

        public Criteria andCloBankNotLike(String value) {
            addCriterion("CLO_BANK not like", value, "cloBank");
            return (Criteria) this;
        }

        public Criteria andCloBankIn(List<String> values) {
            addCriterion("CLO_BANK in", values, "cloBank");
            return (Criteria) this;
        }

        public Criteria andCloBankNotIn(List<String> values) {
            addCriterion("CLO_BANK not in", values, "cloBank");
            return (Criteria) this;
        }

        public Criteria andCloBankBetween(String value1, String value2) {
            addCriterion("CLO_BANK between", value1, value2, "cloBank");
            return (Criteria) this;
        }

        public Criteria andCloBankNotBetween(String value1, String value2) {
            addCriterion("CLO_BANK not between", value1, value2, "cloBank");
            return (Criteria) this;
        }

        public Criteria andCloBankBranchIsNull() {
            addCriterion("CLO_BANK_BRANCH is null");
            return (Criteria) this;
        }

        public Criteria andCloBankBranchIsNotNull() {
            addCriterion("CLO_BANK_BRANCH is not null");
            return (Criteria) this;
        }

        public Criteria andCloBankBranchEqualTo(String value) {
            addCriterion("CLO_BANK_BRANCH =", value, "cloBankBranch");
            return (Criteria) this;
        }

        public Criteria andCloBankBranchNotEqualTo(String value) {
            addCriterion("CLO_BANK_BRANCH <>", value, "cloBankBranch");
            return (Criteria) this;
        }

        public Criteria andCloBankBranchGreaterThan(String value) {
            addCriterion("CLO_BANK_BRANCH >", value, "cloBankBranch");
            return (Criteria) this;
        }

        public Criteria andCloBankBranchGreaterThanOrEqualTo(String value) {
            addCriterion("CLO_BANK_BRANCH >=", value, "cloBankBranch");
            return (Criteria) this;
        }

        public Criteria andCloBankBranchLessThan(String value) {
            addCriterion("CLO_BANK_BRANCH <", value, "cloBankBranch");
            return (Criteria) this;
        }

        public Criteria andCloBankBranchLessThanOrEqualTo(String value) {
            addCriterion("CLO_BANK_BRANCH <=", value, "cloBankBranch");
            return (Criteria) this;
        }

        public Criteria andCloBankBranchLike(String value) {
            addCriterion("CLO_BANK_BRANCH like", value, "cloBankBranch");
            return (Criteria) this;
        }

        public Criteria andCloBankBranchNotLike(String value) {
            addCriterion("CLO_BANK_BRANCH not like", value, "cloBankBranch");
            return (Criteria) this;
        }

        public Criteria andCloBankBranchIn(List<String> values) {
            addCriterion("CLO_BANK_BRANCH in", values, "cloBankBranch");
            return (Criteria) this;
        }

        public Criteria andCloBankBranchNotIn(List<String> values) {
            addCriterion("CLO_BANK_BRANCH not in", values, "cloBankBranch");
            return (Criteria) this;
        }

        public Criteria andCloBankBranchBetween(String value1, String value2) {
            addCriterion("CLO_BANK_BRANCH between", value1, value2, "cloBankBranch");
            return (Criteria) this;
        }

        public Criteria andCloBankBranchNotBetween(String value1, String value2) {
            addCriterion("CLO_BANK_BRANCH not between", value1, value2, "cloBankBranch");
            return (Criteria) this;
        }

        public Criteria andCloBankAccountIsNull() {
            addCriterion("CLO_BANK_ACCOUNT is null");
            return (Criteria) this;
        }

        public Criteria andCloBankAccountIsNotNull() {
            addCriterion("CLO_BANK_ACCOUNT is not null");
            return (Criteria) this;
        }

        public Criteria andCloBankAccountEqualTo(String value) {
            addCriterion("CLO_BANK_ACCOUNT =", value, "cloBankAccount");
            return (Criteria) this;
        }

        public Criteria andCloBankAccountNotEqualTo(String value) {
            addCriterion("CLO_BANK_ACCOUNT <>", value, "cloBankAccount");
            return (Criteria) this;
        }

        public Criteria andCloBankAccountGreaterThan(String value) {
            addCriterion("CLO_BANK_ACCOUNT >", value, "cloBankAccount");
            return (Criteria) this;
        }

        public Criteria andCloBankAccountGreaterThanOrEqualTo(String value) {
            addCriterion("CLO_BANK_ACCOUNT >=", value, "cloBankAccount");
            return (Criteria) this;
        }

        public Criteria andCloBankAccountLessThan(String value) {
            addCriterion("CLO_BANK_ACCOUNT <", value, "cloBankAccount");
            return (Criteria) this;
        }

        public Criteria andCloBankAccountLessThanOrEqualTo(String value) {
            addCriterion("CLO_BANK_ACCOUNT <=", value, "cloBankAccount");
            return (Criteria) this;
        }

        public Criteria andCloBankAccountLike(String value) {
            addCriterion("CLO_BANK_ACCOUNT like", value, "cloBankAccount");
            return (Criteria) this;
        }

        public Criteria andCloBankAccountNotLike(String value) {
            addCriterion("CLO_BANK_ACCOUNT not like", value, "cloBankAccount");
            return (Criteria) this;
        }

        public Criteria andCloBankAccountIn(List<String> values) {
            addCriterion("CLO_BANK_ACCOUNT in", values, "cloBankAccount");
            return (Criteria) this;
        }

        public Criteria andCloBankAccountNotIn(List<String> values) {
            addCriterion("CLO_BANK_ACCOUNT not in", values, "cloBankAccount");
            return (Criteria) this;
        }

        public Criteria andCloBankAccountBetween(String value1, String value2) {
            addCriterion("CLO_BANK_ACCOUNT between", value1, value2, "cloBankAccount");
            return (Criteria) this;
        }

        public Criteria andCloBankAccountNotBetween(String value1, String value2) {
            addCriterion("CLO_BANK_ACCOUNT not between", value1, value2, "cloBankAccount");
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

        public Criteria andCUtimeIsNull() {
            addCriterion("C_UTIME is null");
            return (Criteria) this;
        }

        public Criteria andCUtimeIsNotNull() {
            addCriterion("C_UTIME is not null");
            return (Criteria) this;
        }

        public Criteria andCUtimeEqualTo(Date value) {
            addCriterion("C_UTIME =", value, "cUtime");
            return (Criteria) this;
        }

        public Criteria andCUtimeNotEqualTo(Date value) {
            addCriterion("C_UTIME <>", value, "cUtime");
            return (Criteria) this;
        }

        public Criteria andCUtimeGreaterThan(Date value) {
            addCriterion("C_UTIME >", value, "cUtime");
            return (Criteria) this;
        }

        public Criteria andCUtimeGreaterThanOrEqualTo(Date value) {
            addCriterion("C_UTIME >=", value, "cUtime");
            return (Criteria) this;
        }

        public Criteria andCUtimeLessThan(Date value) {
            addCriterion("C_UTIME <", value, "cUtime");
            return (Criteria) this;
        }

        public Criteria andCUtimeLessThanOrEqualTo(Date value) {
            addCriterion("C_UTIME <=", value, "cUtime");
            return (Criteria) this;
        }

        public Criteria andCUtimeIn(List<Date> values) {
            addCriterion("C_UTIME in", values, "cUtime");
            return (Criteria) this;
        }

        public Criteria andCUtimeNotIn(List<Date> values) {
            addCriterion("C_UTIME not in", values, "cUtime");
            return (Criteria) this;
        }

        public Criteria andCUtimeBetween(Date value1, Date value2) {
            addCriterion("C_UTIME between", value1, value2, "cUtime");
            return (Criteria) this;
        }

        public Criteria andCUtimeNotBetween(Date value1, Date value2) {
            addCriterion("C_UTIME not between", value1, value2, "cUtime");
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

        public Criteria andCloReviewStatusIsNull() {
            addCriterion("CLO_REVIEW_STATUS is null");
            return (Criteria) this;
        }

        public Criteria andCloReviewStatusIsNotNull() {
            addCriterion("CLO_REVIEW_STATUS is not null");
            return (Criteria) this;
        }

        public Criteria andCloReviewStatusEqualTo(BigDecimal value) {
            addCriterion("CLO_REVIEW_STATUS =", value, "cloReviewStatus");
            return (Criteria) this;
        }

        public Criteria andCloReviewStatusNotEqualTo(BigDecimal value) {
            addCriterion("CLO_REVIEW_STATUS <>", value, "cloReviewStatus");
            return (Criteria) this;
        }

        public Criteria andCloReviewStatusGreaterThan(BigDecimal value) {
            addCriterion("CLO_REVIEW_STATUS >", value, "cloReviewStatus");
            return (Criteria) this;
        }

        public Criteria andCloReviewStatusGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("CLO_REVIEW_STATUS >=", value, "cloReviewStatus");
            return (Criteria) this;
        }

        public Criteria andCloReviewStatusLessThan(BigDecimal value) {
            addCriterion("CLO_REVIEW_STATUS <", value, "cloReviewStatus");
            return (Criteria) this;
        }

        public Criteria andCloReviewStatusLessThanOrEqualTo(BigDecimal value) {
            addCriterion("CLO_REVIEW_STATUS <=", value, "cloReviewStatus");
            return (Criteria) this;
        }

        public Criteria andCloReviewStatusIn(List<BigDecimal> values) {
            addCriterion("CLO_REVIEW_STATUS in", values, "cloReviewStatus");
            return (Criteria) this;
        }

        public Criteria andCloReviewStatusNotIn(List<BigDecimal> values) {
            addCriterion("CLO_REVIEW_STATUS not in", values, "cloReviewStatus");
            return (Criteria) this;
        }

        public Criteria andCloReviewStatusBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("CLO_REVIEW_STATUS between", value1, value2, "cloReviewStatus");
            return (Criteria) this;
        }

        public Criteria andCloReviewStatusNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("CLO_REVIEW_STATUS not between", value1, value2, "cloReviewStatus");
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

        public Criteria andVarsionIsNull() {
            addCriterion("VARSION is null");
            return (Criteria) this;
        }

        public Criteria andVarsionIsNotNull() {
            addCriterion("VARSION is not null");
            return (Criteria) this;
        }

        public Criteria andVarsionEqualTo(BigDecimal value) {
            addCriterion("VARSION =", value, "varsion");
            return (Criteria) this;
        }

        public Criteria andVarsionNotEqualTo(BigDecimal value) {
            addCriterion("VARSION <>", value, "varsion");
            return (Criteria) this;
        }

        public Criteria andVarsionGreaterThan(BigDecimal value) {
            addCriterion("VARSION >", value, "varsion");
            return (Criteria) this;
        }

        public Criteria andVarsionGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("VARSION >=", value, "varsion");
            return (Criteria) this;
        }

        public Criteria andVarsionLessThan(BigDecimal value) {
            addCriterion("VARSION <", value, "varsion");
            return (Criteria) this;
        }

        public Criteria andVarsionLessThanOrEqualTo(BigDecimal value) {
            addCriterion("VARSION <=", value, "varsion");
            return (Criteria) this;
        }

        public Criteria andVarsionIn(List<BigDecimal> values) {
            addCriterion("VARSION in", values, "varsion");
            return (Criteria) this;
        }

        public Criteria andVarsionNotIn(List<BigDecimal> values) {
            addCriterion("VARSION not in", values, "varsion");
            return (Criteria) this;
        }

        public Criteria andVarsionBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("VARSION between", value1, value2, "varsion");
            return (Criteria) this;
        }

        public Criteria andVarsionNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("VARSION not between", value1, value2, "varsion");
            return (Criteria) this;
        }

        public Criteria andBranchLineNumIsNull() {
            addCriterion("BRANCH_LINE_NUM is null");
            return (Criteria) this;
        }

        public Criteria andBranchLineNumIsNotNull() {
            addCriterion("BRANCH_LINE_NUM is not null");
            return (Criteria) this;
        }

        public Criteria andBranchLineNumEqualTo(String value) {
            addCriterion("BRANCH_LINE_NUM =", value, "branchLineNum");
            return (Criteria) this;
        }

        public Criteria andBranchLineNumNotEqualTo(String value) {
            addCriterion("BRANCH_LINE_NUM <>", value, "branchLineNum");
            return (Criteria) this;
        }

        public Criteria andBranchLineNumGreaterThan(String value) {
            addCriterion("BRANCH_LINE_NUM >", value, "branchLineNum");
            return (Criteria) this;
        }

        public Criteria andBranchLineNumGreaterThanOrEqualTo(String value) {
            addCriterion("BRANCH_LINE_NUM >=", value, "branchLineNum");
            return (Criteria) this;
        }

        public Criteria andBranchLineNumLessThan(String value) {
            addCriterion("BRANCH_LINE_NUM <", value, "branchLineNum");
            return (Criteria) this;
        }

        public Criteria andBranchLineNumLessThanOrEqualTo(String value) {
            addCriterion("BRANCH_LINE_NUM <=", value, "branchLineNum");
            return (Criteria) this;
        }

        public Criteria andBranchLineNumLike(String value) {
            addCriterion("BRANCH_LINE_NUM like", value, "branchLineNum");
            return (Criteria) this;
        }

        public Criteria andBranchLineNumNotLike(String value) {
            addCriterion("BRANCH_LINE_NUM not like", value, "branchLineNum");
            return (Criteria) this;
        }

        public Criteria andBranchLineNumIn(List<String> values) {
            addCriterion("BRANCH_LINE_NUM in", values, "branchLineNum");
            return (Criteria) this;
        }

        public Criteria andBranchLineNumNotIn(List<String> values) {
            addCriterion("BRANCH_LINE_NUM not in", values, "branchLineNum");
            return (Criteria) this;
        }

        public Criteria andBranchLineNumBetween(String value1, String value2) {
            addCriterion("BRANCH_LINE_NUM between", value1, value2, "branchLineNum");
            return (Criteria) this;
        }

        public Criteria andBranchLineNumNotBetween(String value1, String value2) {
            addCriterion("BRANCH_LINE_NUM not between", value1, value2, "branchLineNum");
            return (Criteria) this;
        }

        public Criteria andAllLineNumIsNull() {
            addCriterion("ALL_LINE_NUM is null");
            return (Criteria) this;
        }

        public Criteria andAllLineNumIsNotNull() {
            addCriterion("ALL_LINE_NUM is not null");
            return (Criteria) this;
        }

        public Criteria andAllLineNumEqualTo(String value) {
            addCriterion("ALL_LINE_NUM =", value, "allLineNum");
            return (Criteria) this;
        }

        public Criteria andAllLineNumNotEqualTo(String value) {
            addCriterion("ALL_LINE_NUM <>", value, "allLineNum");
            return (Criteria) this;
        }

        public Criteria andAllLineNumGreaterThan(String value) {
            addCriterion("ALL_LINE_NUM >", value, "allLineNum");
            return (Criteria) this;
        }

        public Criteria andAllLineNumGreaterThanOrEqualTo(String value) {
            addCriterion("ALL_LINE_NUM >=", value, "allLineNum");
            return (Criteria) this;
        }

        public Criteria andAllLineNumLessThan(String value) {
            addCriterion("ALL_LINE_NUM <", value, "allLineNum");
            return (Criteria) this;
        }

        public Criteria andAllLineNumLessThanOrEqualTo(String value) {
            addCriterion("ALL_LINE_NUM <=", value, "allLineNum");
            return (Criteria) this;
        }

        public Criteria andAllLineNumLike(String value) {
            addCriterion("ALL_LINE_NUM like", value, "allLineNum");
            return (Criteria) this;
        }

        public Criteria andAllLineNumNotLike(String value) {
            addCriterion("ALL_LINE_NUM not like", value, "allLineNum");
            return (Criteria) this;
        }

        public Criteria andAllLineNumIn(List<String> values) {
            addCriterion("ALL_LINE_NUM in", values, "allLineNum");
            return (Criteria) this;
        }

        public Criteria andAllLineNumNotIn(List<String> values) {
            addCriterion("ALL_LINE_NUM not in", values, "allLineNum");
            return (Criteria) this;
        }

        public Criteria andAllLineNumBetween(String value1, String value2) {
            addCriterion("ALL_LINE_NUM between", value1, value2, "allLineNum");
            return (Criteria) this;
        }

        public Criteria andAllLineNumNotBetween(String value1, String value2) {
            addCriterion("ALL_LINE_NUM not between", value1, value2, "allLineNum");
            return (Criteria) this;
        }

        public Criteria andBankRegionIsNull() {
            addCriterion("BANK_REGION is null");
            return (Criteria) this;
        }

        public Criteria andBankRegionIsNotNull() {
            addCriterion("BANK_REGION is not null");
            return (Criteria) this;
        }

        public Criteria andBankRegionEqualTo(String value) {
            addCriterion("BANK_REGION =", value, "bankRegion");
            return (Criteria) this;
        }

        public Criteria andBankRegionNotEqualTo(String value) {
            addCriterion("BANK_REGION <>", value, "bankRegion");
            return (Criteria) this;
        }

        public Criteria andBankRegionGreaterThan(String value) {
            addCriterion("BANK_REGION >", value, "bankRegion");
            return (Criteria) this;
        }

        public Criteria andBankRegionGreaterThanOrEqualTo(String value) {
            addCriterion("BANK_REGION >=", value, "bankRegion");
            return (Criteria) this;
        }

        public Criteria andBankRegionLessThan(String value) {
            addCriterion("BANK_REGION <", value, "bankRegion");
            return (Criteria) this;
        }

        public Criteria andBankRegionLessThanOrEqualTo(String value) {
            addCriterion("BANK_REGION <=", value, "bankRegion");
            return (Criteria) this;
        }

        public Criteria andBankRegionLike(String value) {
            addCriterion("BANK_REGION like", value, "bankRegion");
            return (Criteria) this;
        }

        public Criteria andBankRegionNotLike(String value) {
            addCriterion("BANK_REGION not like", value, "bankRegion");
            return (Criteria) this;
        }

        public Criteria andBankRegionIn(List<String> values) {
            addCriterion("BANK_REGION in", values, "bankRegion");
            return (Criteria) this;
        }

        public Criteria andBankRegionNotIn(List<String> values) {
            addCriterion("BANK_REGION not in", values, "bankRegion");
            return (Criteria) this;
        }

        public Criteria andBankRegionBetween(String value1, String value2) {
            addCriterion("BANK_REGION between", value1, value2, "bankRegion");
            return (Criteria) this;
        }

        public Criteria andBankRegionNotBetween(String value1, String value2) {
            addCriterion("BANK_REGION not between", value1, value2, "bankRegion");
            return (Criteria) this;
        }

        public Criteria andCloTaxPointIsNull() {
            addCriterion("CLO_TAX_POINT is null");
            return (Criteria) this;
        }

        public Criteria andCloTaxPointIsNotNull() {
            addCriterion("CLO_TAX_POINT is not null");
            return (Criteria) this;
        }

        public Criteria andCloTaxPointEqualTo(BigDecimal value) {
            addCriterion("CLO_TAX_POINT =", value, "cloTaxPoint");
            return (Criteria) this;
        }

        public Criteria andCloTaxPointNotEqualTo(BigDecimal value) {
            addCriterion("CLO_TAX_POINT <>", value, "cloTaxPoint");
            return (Criteria) this;
        }

        public Criteria andCloTaxPointGreaterThan(BigDecimal value) {
            addCriterion("CLO_TAX_POINT >", value, "cloTaxPoint");
            return (Criteria) this;
        }

        public Criteria andCloTaxPointGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("CLO_TAX_POINT >=", value, "cloTaxPoint");
            return (Criteria) this;
        }

        public Criteria andCloTaxPointLessThan(BigDecimal value) {
            addCriterion("CLO_TAX_POINT <", value, "cloTaxPoint");
            return (Criteria) this;
        }

        public Criteria andCloTaxPointLessThanOrEqualTo(BigDecimal value) {
            addCriterion("CLO_TAX_POINT <=", value, "cloTaxPoint");
            return (Criteria) this;
        }

        public Criteria andCloTaxPointIn(List<BigDecimal> values) {
            addCriterion("CLO_TAX_POINT in", values, "cloTaxPoint");
            return (Criteria) this;
        }

        public Criteria andCloTaxPointNotIn(List<BigDecimal> values) {
            addCriterion("CLO_TAX_POINT not in", values, "cloTaxPoint");
            return (Criteria) this;
        }

        public Criteria andCloTaxPointBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("CLO_TAX_POINT between", value1, value2, "cloTaxPoint");
            return (Criteria) this;
        }

        public Criteria andCloTaxPointNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("CLO_TAX_POINT not between", value1, value2, "cloTaxPoint");
            return (Criteria) this;
        }

        public Criteria andCloInvoiceIsNull() {
            addCriterion("CLO_INVOICE is null");
            return (Criteria) this;
        }

        public Criteria andCloInvoiceIsNotNull() {
            addCriterion("CLO_INVOICE is not null");
            return (Criteria) this;
        }

        public Criteria andCloInvoiceEqualTo(BigDecimal value) {
            addCriterion("CLO_INVOICE =", value, "cloInvoice");
            return (Criteria) this;
        }

        public Criteria andCloInvoiceNotEqualTo(BigDecimal value) {
            addCriterion("CLO_INVOICE <>", value, "cloInvoice");
            return (Criteria) this;
        }

        public Criteria andCloInvoiceGreaterThan(BigDecimal value) {
            addCriterion("CLO_INVOICE >", value, "cloInvoice");
            return (Criteria) this;
        }

        public Criteria andCloInvoiceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("CLO_INVOICE >=", value, "cloInvoice");
            return (Criteria) this;
        }

        public Criteria andCloInvoiceLessThan(BigDecimal value) {
            addCriterion("CLO_INVOICE <", value, "cloInvoice");
            return (Criteria) this;
        }

        public Criteria andCloInvoiceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("CLO_INVOICE <=", value, "cloInvoice");
            return (Criteria) this;
        }

        public Criteria andCloInvoiceIn(List<BigDecimal> values) {
            addCriterion("CLO_INVOICE in", values, "cloInvoice");
            return (Criteria) this;
        }

        public Criteria andCloInvoiceNotIn(List<BigDecimal> values) {
            addCriterion("CLO_INVOICE not in", values, "cloInvoice");
            return (Criteria) this;
        }

        public Criteria andCloInvoiceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("CLO_INVOICE between", value1, value2, "cloInvoice");
            return (Criteria) this;
        }

        public Criteria andCloInvoiceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("CLO_INVOICE not between", value1, value2, "cloInvoice");
            return (Criteria) this;
        }

        public Criteria andCloBankCodeIsNull() {
            addCriterion("CLO_BANK_CODE is null");
            return (Criteria) this;
        }

        public Criteria andCloBankCodeIsNotNull() {
            addCriterion("CLO_BANK_CODE is not null");
            return (Criteria) this;
        }

        public Criteria andCloBankCodeEqualTo(String value) {
            addCriterion("CLO_BANK_CODE =", value, "cloBankCode");
            return (Criteria) this;
        }

        public Criteria andCloBankCodeNotEqualTo(String value) {
            addCriterion("CLO_BANK_CODE <>", value, "cloBankCode");
            return (Criteria) this;
        }

        public Criteria andCloBankCodeGreaterThan(String value) {
            addCriterion("CLO_BANK_CODE >", value, "cloBankCode");
            return (Criteria) this;
        }

        public Criteria andCloBankCodeGreaterThanOrEqualTo(String value) {
            addCriterion("CLO_BANK_CODE >=", value, "cloBankCode");
            return (Criteria) this;
        }

        public Criteria andCloBankCodeLessThan(String value) {
            addCriterion("CLO_BANK_CODE <", value, "cloBankCode");
            return (Criteria) this;
        }

        public Criteria andCloBankCodeLessThanOrEqualTo(String value) {
            addCriterion("CLO_BANK_CODE <=", value, "cloBankCode");
            return (Criteria) this;
        }

        public Criteria andCloBankCodeLike(String value) {
            addCriterion("CLO_BANK_CODE like", value, "cloBankCode");
            return (Criteria) this;
        }

        public Criteria andCloBankCodeNotLike(String value) {
            addCriterion("CLO_BANK_CODE not like", value, "cloBankCode");
            return (Criteria) this;
        }

        public Criteria andCloBankCodeIn(List<String> values) {
            addCriterion("CLO_BANK_CODE in", values, "cloBankCode");
            return (Criteria) this;
        }

        public Criteria andCloBankCodeNotIn(List<String> values) {
            addCriterion("CLO_BANK_CODE not in", values, "cloBankCode");
            return (Criteria) this;
        }

        public Criteria andCloBankCodeBetween(String value1, String value2) {
            addCriterion("CLO_BANK_CODE between", value1, value2, "cloBankCode");
            return (Criteria) this;
        }

        public Criteria andCloBankCodeNotBetween(String value1, String value2) {
            addCriterion("CLO_BANK_CODE not between", value1, value2, "cloBankCode");
            return (Criteria) this;
        }

        public Criteria andPayStatusIsNull() {
            addCriterion("PAY_STATUS is null");
            return (Criteria) this;
        }

        public Criteria andPayStatusIsNotNull() {
            addCriterion("PAY_STATUS is not null");
            return (Criteria) this;
        }

        public Criteria andPayStatusEqualTo(BigDecimal value) {
            addCriterion("PAY_STATUS =", value, "payStatus");
            return (Criteria) this;
        }

        public Criteria andPayStatusNotEqualTo(BigDecimal value) {
            addCriterion("PAY_STATUS <>", value, "payStatus");
            return (Criteria) this;
        }

        public Criteria andPayStatusGreaterThan(BigDecimal value) {
            addCriterion("PAY_STATUS >", value, "payStatus");
            return (Criteria) this;
        }

        public Criteria andPayStatusGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("PAY_STATUS >=", value, "payStatus");
            return (Criteria) this;
        }

        public Criteria andPayStatusLessThan(BigDecimal value) {
            addCriterion("PAY_STATUS <", value, "payStatus");
            return (Criteria) this;
        }

        public Criteria andPayStatusLessThanOrEqualTo(BigDecimal value) {
            addCriterion("PAY_STATUS <=", value, "payStatus");
            return (Criteria) this;
        }

        public Criteria andPayStatusIn(List<BigDecimal> values) {
            addCriterion("PAY_STATUS in", values, "payStatus");
            return (Criteria) this;
        }

        public Criteria andPayStatusNotIn(List<BigDecimal> values) {
            addCriterion("PAY_STATUS not in", values, "payStatus");
            return (Criteria) this;
        }

        public Criteria andPayStatusBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("PAY_STATUS between", value1, value2, "payStatus");
            return (Criteria) this;
        }

        public Criteria andPayStatusNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("PAY_STATUS not between", value1, value2, "payStatus");
            return (Criteria) this;
        }

        public Criteria andAgLegalCernumIsNull() {
            addCriterion("AG_LEGAL_CERNUM is null");
            return (Criteria) this;
        }

        public Criteria andAgLegalCernumIsNotNull() {
            addCriterion("AG_LEGAL_CERNUM is not null");
            return (Criteria) this;
        }

        public Criteria andAgLegalCernumEqualTo(String value) {
            addCriterion("AG_LEGAL_CERNUM =", value, "agLegalCernum");
            return (Criteria) this;
        }

        public Criteria andAgLegalCernumNotEqualTo(String value) {
            addCriterion("AG_LEGAL_CERNUM <>", value, "agLegalCernum");
            return (Criteria) this;
        }

        public Criteria andAgLegalCernumGreaterThan(String value) {
            addCriterion("AG_LEGAL_CERNUM >", value, "agLegalCernum");
            return (Criteria) this;
        }

        public Criteria andAgLegalCernumGreaterThanOrEqualTo(String value) {
            addCriterion("AG_LEGAL_CERNUM >=", value, "agLegalCernum");
            return (Criteria) this;
        }

        public Criteria andAgLegalCernumLessThan(String value) {
            addCriterion("AG_LEGAL_CERNUM <", value, "agLegalCernum");
            return (Criteria) this;
        }

        public Criteria andAgLegalCernumLessThanOrEqualTo(String value) {
            addCriterion("AG_LEGAL_CERNUM <=", value, "agLegalCernum");
            return (Criteria) this;
        }

        public Criteria andAgLegalCernumLike(String value) {
            addCriterion("AG_LEGAL_CERNUM like", value, "agLegalCernum");
            return (Criteria) this;
        }

        public Criteria andAgLegalCernumNotLike(String value) {
            addCriterion("AG_LEGAL_CERNUM not like", value, "agLegalCernum");
            return (Criteria) this;
        }

        public Criteria andAgLegalCernumIn(List<String> values) {
            addCriterion("AG_LEGAL_CERNUM in", values, "agLegalCernum");
            return (Criteria) this;
        }

        public Criteria andAgLegalCernumNotIn(List<String> values) {
            addCriterion("AG_LEGAL_CERNUM not in", values, "agLegalCernum");
            return (Criteria) this;
        }

        public Criteria andAgLegalCernumBetween(String value1, String value2) {
            addCriterion("AG_LEGAL_CERNUM between", value1, value2, "agLegalCernum");
            return (Criteria) this;
        }

        public Criteria andAgLegalCernumNotBetween(String value1, String value2) {
            addCriterion("AG_LEGAL_CERNUM not between", value1, value2, "agLegalCernum");
            return (Criteria) this;
        }
        public Criteria andUnusualExplainIsNull() {
            addCriterion("UNUSUAL_EXPLAIN is null");
            return (Criteria) this;
        }

        public Criteria andUnusualExplainIsNotNull() {
            addCriterion("UNUSUAL_EXPLAIN is not null");
            return (Criteria) this;
        }

        public Criteria andUnusualExplainEqualTo(String value) {
            addCriterion("UNUSUAL_EXPLAIN =", value, "unusualExplain");
            return (Criteria) this;
        }

        public Criteria andUnusualExplainNotEqualTo(String value) {
            addCriterion("UNUSUAL_EXPLAIN <>", value, "unusualExplain");
            return (Criteria) this;
        }

        public Criteria andUnusualExplainGreaterThan(String value) {
            addCriterion("UNUSUAL_EXPLAIN >", value, "unusualExplain");
            return (Criteria) this;
        }

        public Criteria andUnusualExplainGreaterThanOrEqualTo(String value) {
            addCriterion("UNUSUAL_EXPLAIN >=", value, "unusualExplain");
            return (Criteria) this;
        }

        public Criteria andUnusualExplainLessThan(String value) {
            addCriterion("UNUSUAL_EXPLAIN <", value, "unusualExplain");
            return (Criteria) this;
        }

        public Criteria andUnusualExplainLessThanOrEqualTo(String value) {
            addCriterion("UNUSUAL_EXPLAIN <=", value, "unusualExplain");
            return (Criteria) this;
        }

        public Criteria andUnusualExplainLike(String value) {
            addCriterion("UNUSUAL_EXPLAIN like", value, "unusualExplain");
            return (Criteria) this;
        }

        public Criteria andUnusualExplainNotLike(String value) {
            addCriterion("UNUSUAL_EXPLAIN not like", value, "unusualExplain");
            return (Criteria) this;
        }

        public Criteria andUnusualExplainIn(List<String> values) {
            addCriterion("UNUSUAL_EXPLAIN in", values, "unusualExplain");
            return (Criteria) this;
        }

        public Criteria andUnusualExplainNotIn(List<String> values) {
            addCriterion("UNUSUAL_EXPLAIN not in", values, "unusualExplain");
            return (Criteria) this;
        }

        public Criteria andUnusualExplainBetween(String value1, String value2) {
            addCriterion("UNUSUAL_EXPLAIN between", value1, value2, "unusualExplain");
            return (Criteria) this;
        }

        public Criteria andUnusualExplainNotBetween(String value1, String value2) {
            addCriterion("UNUSUAL_EXPLAIN not between", value1, value2, "unusualExplain");
            return (Criteria) this;
        }

        public Criteria andAmendStatusIsNull() {
            addCriterion("AMEND_STATUS is null");
            return (Criteria) this;
        }

        public Criteria andAmendStatusIsNotNull() {
            addCriterion("AMEND_STATUS is not null");
            return (Criteria) this;
        }

        public Criteria andAmendStatusEqualTo(BigDecimal value) {
            addCriterion("AMEND_STATUS =", value, "amendStatus");
            return (Criteria) this;
        }

        public Criteria andAmendStatusNotEqualTo(BigDecimal value) {
            addCriterion("AMEND_STATUS <>", value, "amendStatus");
            return (Criteria) this;
        }

        public Criteria andAmendStatusGreaterThan(BigDecimal value) {
            addCriterion("AMEND_STATUS >", value, "amendStatus");
            return (Criteria) this;
        }

        public Criteria andAmendStatusGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("AMEND_STATUS >=", value, "amendStatus");
            return (Criteria) this;
        }

        public Criteria andAmendStatusLessThan(BigDecimal value) {
            addCriterion("AMEND_STATUS <", value, "amendStatus");
            return (Criteria) this;
        }

        public Criteria andAmendStatusLessThanOrEqualTo(BigDecimal value) {
            addCriterion("AMEND_STATUS <=", value, "amendStatus");
            return (Criteria) this;
        }

        public Criteria andAmendStatusIn(List<BigDecimal> values) {
            addCriterion("AMEND_STATUS in", values, "amendStatus");
            return (Criteria) this;
        }

        public Criteria andAmendStatusNotIn(List<BigDecimal> values) {
            addCriterion("AMEND_STATUS not in", values, "amendStatus");
            return (Criteria) this;
        }

        public Criteria andAmendStatusBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("AMEND_STATUS between", value1, value2, "amendStatus");
            return (Criteria) this;
        }

        public Criteria andAmendStatusNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("AMEND_STATUS not between", value1, value2, "amendStatus");
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