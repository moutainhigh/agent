package com.ryx.credit.pojo.admin.order;

import com.ryx.credit.common.util.Page;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OSupplementExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Page page;

    protected Integer limitStart;

    protected Integer limitEnd;

    public OSupplementExample() {
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

        public Criteria andSrcIdIsNull() {
            addCriterion("SRC_ID is null");
            return (Criteria) this;
        }

        public Criteria andSrcIdIsNotNull() {
            addCriterion("SRC_ID is not null");
            return (Criteria) this;
        }

        public Criteria andSrcIdEqualTo(String value) {
            addCriterion("SRC_ID =", value, "srcId");
            return (Criteria) this;
        }

        public Criteria andSrcIdNotEqualTo(String value) {
            addCriterion("SRC_ID <>", value, "srcId");
            return (Criteria) this;
        }

        public Criteria andSrcIdGreaterThan(String value) {
            addCriterion("SRC_ID >", value, "srcId");
            return (Criteria) this;
        }

        public Criteria andSrcIdGreaterThanOrEqualTo(String value) {
            addCriterion("SRC_ID >=", value, "srcId");
            return (Criteria) this;
        }

        public Criteria andSrcIdLessThan(String value) {
            addCriterion("SRC_ID <", value, "srcId");
            return (Criteria) this;
        }

        public Criteria andSrcIdLessThanOrEqualTo(String value) {
            addCriterion("SRC_ID <=", value, "srcId");
            return (Criteria) this;
        }

        public Criteria andSrcIdLike(String value) {
            addCriterion("SRC_ID like", value, "srcId");
            return (Criteria) this;
        }

        public Criteria andSrcIdNotLike(String value) {
            addCriterion("SRC_ID not like", value, "srcId");
            return (Criteria) this;
        }

        public Criteria andSrcIdIn(List<String> values) {
            addCriterion("SRC_ID in", values, "srcId");
            return (Criteria) this;
        }

        public Criteria andSrcIdNotIn(List<String> values) {
            addCriterion("SRC_ID not in", values, "srcId");
            return (Criteria) this;
        }

        public Criteria andSrcIdBetween(String value1, String value2) {
            addCriterion("SRC_ID between", value1, value2, "srcId");
            return (Criteria) this;
        }

        public Criteria andSrcIdNotBetween(String value1, String value2) {
            addCriterion("SRC_ID not between", value1, value2, "srcId");
            return (Criteria) this;
        }

        public Criteria andPkTypeIsNull() {
            addCriterion("PK_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andPkTypeIsNotNull() {
            addCriterion("PK_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andPkTypeEqualTo(String value) {
            addCriterion("PK_TYPE =", value, "pkType");
            return (Criteria) this;
        }

        public Criteria andPkTypeNotEqualTo(String value) {
            addCriterion("PK_TYPE <>", value, "pkType");
            return (Criteria) this;
        }

        public Criteria andPkTypeGreaterThan(String value) {
            addCriterion("PK_TYPE >", value, "pkType");
            return (Criteria) this;
        }

        public Criteria andPkTypeGreaterThanOrEqualTo(String value) {
            addCriterion("PK_TYPE >=", value, "pkType");
            return (Criteria) this;
        }

        public Criteria andPkTypeLessThan(String value) {
            addCriterion("PK_TYPE <", value, "pkType");
            return (Criteria) this;
        }

        public Criteria andPkTypeLessThanOrEqualTo(String value) {
            addCriterion("PK_TYPE <=", value, "pkType");
            return (Criteria) this;
        }

        public Criteria andPkTypeLike(String value) {
            addCriterion("PK_TYPE like", value, "pkType");
            return (Criteria) this;
        }

        public Criteria andPkTypeNotLike(String value) {
            addCriterion("PK_TYPE not like", value, "pkType");
            return (Criteria) this;
        }

        public Criteria andPkTypeIn(List<String> values) {
            addCriterion("PK_TYPE in", values, "pkType");
            return (Criteria) this;
        }

        public Criteria andPkTypeNotIn(List<String> values) {
            addCriterion("PK_TYPE not in", values, "pkType");
            return (Criteria) this;
        }

        public Criteria andPkTypeBetween(String value1, String value2) {
            addCriterion("PK_TYPE between", value1, value2, "pkType");
            return (Criteria) this;
        }

        public Criteria andPkTypeNotBetween(String value1, String value2) {
            addCriterion("PK_TYPE not between", value1, value2, "pkType");
            return (Criteria) this;
        }

        public Criteria andPayMethodIsNull() {
            addCriterion("PAY_METHOD is null");
            return (Criteria) this;
        }

        public Criteria andPayMethodIsNotNull() {
            addCriterion("PAY_METHOD is not null");
            return (Criteria) this;
        }

        public Criteria andPayMethodEqualTo(String value) {
            addCriterion("PAY_METHOD =", value, "payMethod");
            return (Criteria) this;
        }

        public Criteria andPayMethodNotEqualTo(String value) {
            addCriterion("PAY_METHOD <>", value, "payMethod");
            return (Criteria) this;
        }

        public Criteria andPayMethodGreaterThan(String value) {
            addCriterion("PAY_METHOD >", value, "payMethod");
            return (Criteria) this;
        }

        public Criteria andPayMethodGreaterThanOrEqualTo(String value) {
            addCriterion("PAY_METHOD >=", value, "payMethod");
            return (Criteria) this;
        }

        public Criteria andPayMethodLessThan(String value) {
            addCriterion("PAY_METHOD <", value, "payMethod");
            return (Criteria) this;
        }

        public Criteria andPayMethodLessThanOrEqualTo(String value) {
            addCriterion("PAY_METHOD <=", value, "payMethod");
            return (Criteria) this;
        }

        public Criteria andPayMethodLike(String value) {
            addCriterion("PAY_METHOD like", value, "payMethod");
            return (Criteria) this;
        }

        public Criteria andPayMethodNotLike(String value) {
            addCriterion("PAY_METHOD not like", value, "payMethod");
            return (Criteria) this;
        }

        public Criteria andPayMethodIn(List<String> values) {
            addCriterion("PAY_METHOD in", values, "payMethod");
            return (Criteria) this;
        }

        public Criteria andPayMethodNotIn(List<String> values) {
            addCriterion("PAY_METHOD not in", values, "payMethod");
            return (Criteria) this;
        }

        public Criteria andPayMethodBetween(String value1, String value2) {
            addCriterion("PAY_METHOD between", value1, value2, "payMethod");
            return (Criteria) this;
        }

        public Criteria andPayMethodNotBetween(String value1, String value2) {
            addCriterion("PAY_METHOD not between", value1, value2, "payMethod");
            return (Criteria) this;
        }

        public Criteria andPayAmountIsNull() {
            addCriterion("PAY_AMOUNT is null");
            return (Criteria) this;
        }

        public Criteria andPayAmountIsNotNull() {
            addCriterion("PAY_AMOUNT is not null");
            return (Criteria) this;
        }

        public Criteria andPayAmountEqualTo(BigDecimal value) {
            addCriterion("PAY_AMOUNT =", value, "payAmount");
            return (Criteria) this;
        }

        public Criteria andPayAmountNotEqualTo(BigDecimal value) {
            addCriterion("PAY_AMOUNT <>", value, "payAmount");
            return (Criteria) this;
        }

        public Criteria andPayAmountGreaterThan(BigDecimal value) {
            addCriterion("PAY_AMOUNT >", value, "payAmount");
            return (Criteria) this;
        }

        public Criteria andPayAmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("PAY_AMOUNT >=", value, "payAmount");
            return (Criteria) this;
        }

        public Criteria andPayAmountLessThan(BigDecimal value) {
            addCriterion("PAY_AMOUNT <", value, "payAmount");
            return (Criteria) this;
        }

        public Criteria andPayAmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("PAY_AMOUNT <=", value, "payAmount");
            return (Criteria) this;
        }

        public Criteria andPayAmountIn(List<BigDecimal> values) {
            addCriterion("PAY_AMOUNT in", values, "payAmount");
            return (Criteria) this;
        }

        public Criteria andPayAmountNotIn(List<BigDecimal> values) {
            addCriterion("PAY_AMOUNT not in", values, "payAmount");
            return (Criteria) this;
        }

        public Criteria andPayAmountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("PAY_AMOUNT between", value1, value2, "payAmount");
            return (Criteria) this;
        }

        public Criteria andPayAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("PAY_AMOUNT not between", value1, value2, "payAmount");
            return (Criteria) this;
        }

        public Criteria andRealPayAmountIsNull() {
            addCriterion("REAL_PAY_AMOUNT is null");
            return (Criteria) this;
        }

        public Criteria andRealPayAmountIsNotNull() {
            addCriterion("REAL_PAY_AMOUNT is not null");
            return (Criteria) this;
        }

        public Criteria andRealPayAmountEqualTo(BigDecimal value) {
            addCriterion("REAL_PAY_AMOUNT =", value, "realPayAmount");
            return (Criteria) this;
        }

        public Criteria andRealPayAmountNotEqualTo(BigDecimal value) {
            addCriterion("REAL_PAY_AMOUNT <>", value, "realPayAmount");
            return (Criteria) this;
        }

        public Criteria andRealPayAmountGreaterThan(BigDecimal value) {
            addCriterion("REAL_PAY_AMOUNT >", value, "realPayAmount");
            return (Criteria) this;
        }

        public Criteria andRealPayAmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("REAL_PAY_AMOUNT >=", value, "realPayAmount");
            return (Criteria) this;
        }

        public Criteria andRealPayAmountLessThan(BigDecimal value) {
            addCriterion("REAL_PAY_AMOUNT <", value, "realPayAmount");
            return (Criteria) this;
        }

        public Criteria andRealPayAmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("REAL_PAY_AMOUNT <=", value, "realPayAmount");
            return (Criteria) this;
        }

        public Criteria andRealPayAmountIn(List<BigDecimal> values) {
            addCriterion("REAL_PAY_AMOUNT in", values, "realPayAmount");
            return (Criteria) this;
        }

        public Criteria andRealPayAmountNotIn(List<BigDecimal> values) {
            addCriterion("REAL_PAY_AMOUNT not in", values, "realPayAmount");
            return (Criteria) this;
        }

        public Criteria andRealPayAmountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("REAL_PAY_AMOUNT between", value1, value2, "realPayAmount");
            return (Criteria) this;
        }

        public Criteria andRealPayAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("REAL_PAY_AMOUNT not between", value1, value2, "realPayAmount");
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

        public Criteria andReviewStatusIsNull() {
            addCriterion("REVIEW_STATUS is null");
            return (Criteria) this;
        }

        public Criteria andReviewStatusIsNotNull() {
            addCriterion("REVIEW_STATUS is not null");
            return (Criteria) this;
        }

        public Criteria andReviewStatusEqualTo(BigDecimal value) {
            addCriterion("REVIEW_STATUS =", value, "reviewStatus");
            return (Criteria) this;
        }

        public Criteria andReviewStatusNotEqualTo(BigDecimal value) {
            addCriterion("REVIEW_STATUS <>", value, "reviewStatus");
            return (Criteria) this;
        }

        public Criteria andReviewStatusGreaterThan(BigDecimal value) {
            addCriterion("REVIEW_STATUS >", value, "reviewStatus");
            return (Criteria) this;
        }

        public Criteria andReviewStatusGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("REVIEW_STATUS >=", value, "reviewStatus");
            return (Criteria) this;
        }

        public Criteria andReviewStatusLessThan(BigDecimal value) {
            addCriterion("REVIEW_STATUS <", value, "reviewStatus");
            return (Criteria) this;
        }

        public Criteria andReviewStatusLessThanOrEqualTo(BigDecimal value) {
            addCriterion("REVIEW_STATUS <=", value, "reviewStatus");
            return (Criteria) this;
        }

        public Criteria andReviewStatusIn(List<BigDecimal> values) {
            addCriterion("REVIEW_STATUS in", values, "reviewStatus");
            return (Criteria) this;
        }

        public Criteria andReviewStatusNotIn(List<BigDecimal> values) {
            addCriterion("REVIEW_STATUS not in", values, "reviewStatus");
            return (Criteria) this;
        }

        public Criteria andReviewStatusBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("REVIEW_STATUS between", value1, value2, "reviewStatus");
            return (Criteria) this;
        }

        public Criteria andReviewStatusNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("REVIEW_STATUS not between", value1, value2, "reviewStatus");
            return (Criteria) this;
        }

        public Criteria andSchstatusIsNull() {
            addCriterion("SCHSTATUS is null");
            return (Criteria) this;
        }

        public Criteria andSchstatusIsNotNull() {
            addCriterion("SCHSTATUS is not null");
            return (Criteria) this;
        }

        public Criteria andSchstatusEqualTo(BigDecimal value) {
            addCriterion("SCHSTATUS =", value, "schstatus");
            return (Criteria) this;
        }

        public Criteria andSchstatusNotEqualTo(BigDecimal value) {
            addCriterion("SCHSTATUS <>", value, "schstatus");
            return (Criteria) this;
        }

        public Criteria andSchstatusGreaterThan(BigDecimal value) {
            addCriterion("SCHSTATUS >", value, "schstatus");
            return (Criteria) this;
        }

        public Criteria andSchstatusGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("SCHSTATUS >=", value, "schstatus");
            return (Criteria) this;
        }

        public Criteria andSchstatusLessThan(BigDecimal value) {
            addCriterion("SCHSTATUS <", value, "schstatus");
            return (Criteria) this;
        }

        public Criteria andSchstatusLessThanOrEqualTo(BigDecimal value) {
            addCriterion("SCHSTATUS <=", value, "schstatus");
            return (Criteria) this;
        }

        public Criteria andSchstatusIn(List<BigDecimal> values) {
            addCriterion("SCHSTATUS in", values, "schstatus");
            return (Criteria) this;
        }

        public Criteria andSchstatusNotIn(List<BigDecimal> values) {
            addCriterion("SCHSTATUS not in", values, "schstatus");
            return (Criteria) this;
        }

        public Criteria andSchstatusBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("SCHSTATUS between", value1, value2, "schstatus");
            return (Criteria) this;
        }

        public Criteria andSchstatusNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("SCHSTATUS not between", value1, value2, "schstatus");
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

        public Criteria andRemitTimeIsNull() {
            addCriterion("REMIT_TIME is null");
            return (Criteria) this;
        }

        public Criteria andRemitTimeIsNotNull() {
            addCriterion("REMIT_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andRemitTimeEqualTo(Date value) {
            addCriterion("REMIT_TIME =", value, "remitTime");
            return (Criteria) this;
        }

        public Criteria andRemitTimeNotEqualTo(Date value) {
            addCriterion("REMIT_TIME <>", value, "remitTime");
            return (Criteria) this;
        }

        public Criteria andRemitTimeGreaterThan(Date value) {
            addCriterion("REMIT_TIME >", value, "remitTime");
            return (Criteria) this;
        }

        public Criteria andRemitTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("REMIT_TIME >=", value, "remitTime");
            return (Criteria) this;
        }

        public Criteria andRemitTimeLessThan(Date value) {
            addCriterion("REMIT_TIME <", value, "remitTime");
            return (Criteria) this;
        }

        public Criteria andRemitTimeLessThanOrEqualTo(Date value) {
            addCriterion("REMIT_TIME <=", value, "remitTime");
            return (Criteria) this;
        }

        public Criteria andRemitTimeIn(List<Date> values) {
            addCriterion("REMIT_TIME in", values, "remitTime");
            return (Criteria) this;
        }

        public Criteria andRemitTimeNotIn(List<Date> values) {
            addCriterion("REMIT_TIME not in", values, "remitTime");
            return (Criteria) this;
        }

        public Criteria andRemitTimeBetween(Date value1, Date value2) {
            addCriterion("REMIT_TIME between", value1, value2, "remitTime");
            return (Criteria) this;
        }

        public Criteria andRemitTimeNotBetween(Date value1, Date value2) {
            addCriterion("REMIT_TIME not between", value1, value2, "remitTime");
            return (Criteria) this;
        }

        public Criteria andRemitPeopleIsNull() {
            addCriterion("REMIT_PEOPLE is null");
            return (Criteria) this;
        }

        public Criteria andRemitPeopleIsNotNull() {
            addCriterion("REMIT_PEOPLE is not null");
            return (Criteria) this;
        }

        public Criteria andRemitPeopleEqualTo(String value) {
            addCriterion("REMIT_PEOPLE =", value, "remitPeople");
            return (Criteria) this;
        }

        public Criteria andRemitPeopleNotEqualTo(String value) {
            addCriterion("REMIT_PEOPLE <>", value, "remitPeople");
            return (Criteria) this;
        }

        public Criteria andRemitPeopleGreaterThan(String value) {
            addCriterion("REMIT_PEOPLE >", value, "remitPeople");
            return (Criteria) this;
        }

        public Criteria andRemitPeopleGreaterThanOrEqualTo(String value) {
            addCriterion("REMIT_PEOPLE >=", value, "remitPeople");
            return (Criteria) this;
        }

        public Criteria andRemitPeopleLessThan(String value) {
            addCriterion("REMIT_PEOPLE <", value, "remitPeople");
            return (Criteria) this;
        }

        public Criteria andRemitPeopleLessThanOrEqualTo(String value) {
            addCriterion("REMIT_PEOPLE <=", value, "remitPeople");
            return (Criteria) this;
        }

        public Criteria andRemitPeopleLike(String value) {
            addCriterion("REMIT_PEOPLE like", value, "remitPeople");
            return (Criteria) this;
        }

        public Criteria andRemitPeopleNotLike(String value) {
            addCriterion("REMIT_PEOPLE not like", value, "remitPeople");
            return (Criteria) this;
        }

        public Criteria andRemitPeopleIn(List<String> values) {
            addCriterion("REMIT_PEOPLE in", values, "remitPeople");
            return (Criteria) this;
        }

        public Criteria andRemitPeopleNotIn(List<String> values) {
            addCriterion("REMIT_PEOPLE not in", values, "remitPeople");
            return (Criteria) this;
        }

        public Criteria andRemitPeopleBetween(String value1, String value2) {
            addCriterion("REMIT_PEOPLE between", value1, value2, "remitPeople");
            return (Criteria) this;
        }

        public Criteria andRemitPeopleNotBetween(String value1, String value2) {
            addCriterion("REMIT_PEOPLE not between", value1, value2, "remitPeople");
            return (Criteria) this;
        }

        public Criteria andCheckTimeIsNull() {
            addCriterion("CHECK_TIME is null");
            return (Criteria) this;
        }

        public Criteria andCheckTimeIsNotNull() {
            addCriterion("CHECK_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andCheckTimeEqualTo(Date value) {
            addCriterion("CHECK_TIME =", value, "checkTime");
            return (Criteria) this;
        }

        public Criteria andCheckTimeNotEqualTo(Date value) {
            addCriterion("CHECK_TIME <>", value, "checkTime");
            return (Criteria) this;
        }

        public Criteria andCheckTimeGreaterThan(Date value) {
            addCriterion("CHECK_TIME >", value, "checkTime");
            return (Criteria) this;
        }

        public Criteria andCheckTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("CHECK_TIME >=", value, "checkTime");
            return (Criteria) this;
        }

        public Criteria andCheckTimeLessThan(Date value) {
            addCriterion("CHECK_TIME <", value, "checkTime");
            return (Criteria) this;
        }

        public Criteria andCheckTimeLessThanOrEqualTo(Date value) {
            addCriterion("CHECK_TIME <=", value, "checkTime");
            return (Criteria) this;
        }

        public Criteria andCheckTimeIn(List<Date> values) {
            addCriterion("CHECK_TIME in", values, "checkTime");
            return (Criteria) this;
        }

        public Criteria andCheckTimeNotIn(List<Date> values) {
            addCriterion("CHECK_TIME not in", values, "checkTime");
            return (Criteria) this;
        }

        public Criteria andCheckTimeBetween(Date value1, Date value2) {
            addCriterion("CHECK_TIME between", value1, value2, "checkTime");
            return (Criteria) this;
        }

        public Criteria andCheckTimeNotBetween(Date value1, Date value2) {
            addCriterion("CHECK_TIME not between", value1, value2, "checkTime");
            return (Criteria) this;
        }

        public Criteria andLogicalVersionIsNull() {
            addCriterion("LOGICAL_VERSION is null");
            return (Criteria) this;
        }

        public Criteria andLogicalVersionIsNotNull() {
            addCriterion("LOGICAL_VERSION is not null");
            return (Criteria) this;
        }

        public Criteria andLogicalVersionEqualTo(String value) {
            addCriterion("LOGICAL_VERSION =", value, "logicalVersion");
            return (Criteria) this;
        }

        public Criteria andLogicalVersionNotEqualTo(String value) {
            addCriterion("LOGICAL_VERSION <>", value, "logicalVersion");
            return (Criteria) this;
        }

        public Criteria andLogicalVersionGreaterThan(String value) {
            addCriterion("LOGICAL_VERSION >", value, "logicalVersion");
            return (Criteria) this;
        }

        public Criteria andLogicalVersionGreaterThanOrEqualTo(String value) {
            addCriterion("LOGICAL_VERSION >=", value, "logicalVersion");
            return (Criteria) this;
        }

        public Criteria andLogicalVersionLessThan(String value) {
            addCriterion("LOGICAL_VERSION <", value, "logicalVersion");
            return (Criteria) this;
        }

        public Criteria andLogicalVersionLessThanOrEqualTo(String value) {
            addCriterion("LOGICAL_VERSION <=", value, "logicalVersion");
            return (Criteria) this;
        }

        public Criteria andLogicalVersionLike(String value) {
            addCriterion("LOGICAL_VERSION like", value, "logicalVersion");
            return (Criteria) this;
        }

        public Criteria andLogicalVersionNotLike(String value) {
            addCriterion("LOGICAL_VERSION not like", value, "logicalVersion");
            return (Criteria) this;
        }

        public Criteria andLogicalVersionIn(List<String> values) {
            addCriterion("LOGICAL_VERSION in", values, "logicalVersion");
            return (Criteria) this;
        }

        public Criteria andLogicalVersionNotIn(List<String> values) {
            addCriterion("LOGICAL_VERSION not in", values, "logicalVersion");
            return (Criteria) this;
        }

        public Criteria andLogicalVersionBetween(String value1, String value2) {
            addCriterion("LOGICAL_VERSION between", value1, value2, "logicalVersion");
            return (Criteria) this;
        }

        public Criteria andLogicalVersionNotBetween(String value1, String value2) {
            addCriterion("LOGICAL_VERSION not between", value1, value2, "logicalVersion");
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