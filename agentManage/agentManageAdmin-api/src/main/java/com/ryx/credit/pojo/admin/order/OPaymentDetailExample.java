package com.ryx.credit.pojo.admin.order;

import com.ryx.credit.common.util.Page;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OPaymentDetailExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Page page;

    protected Integer limitStart;

    protected Integer limitEnd;

    public OPaymentDetailExample() {
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

        public Criteria andPaymentIdIsNull() {
            addCriterion("PAYMENT_ID is null");
            return (Criteria) this;
        }

        public Criteria andPaymentIdIsNotNull() {
            addCriterion("PAYMENT_ID is not null");
            return (Criteria) this;
        }

        public Criteria andPaymentIdEqualTo(String value) {
            addCriterion("PAYMENT_ID =", value, "paymentId");
            return (Criteria) this;
        }

        public Criteria andPaymentIdNotEqualTo(String value) {
            addCriterion("PAYMENT_ID <>", value, "paymentId");
            return (Criteria) this;
        }

        public Criteria andPaymentIdGreaterThan(String value) {
            addCriterion("PAYMENT_ID >", value, "paymentId");
            return (Criteria) this;
        }

        public Criteria andPaymentIdGreaterThanOrEqualTo(String value) {
            addCriterion("PAYMENT_ID >=", value, "paymentId");
            return (Criteria) this;
        }

        public Criteria andPaymentIdLessThan(String value) {
            addCriterion("PAYMENT_ID <", value, "paymentId");
            return (Criteria) this;
        }

        public Criteria andPaymentIdLessThanOrEqualTo(String value) {
            addCriterion("PAYMENT_ID <=", value, "paymentId");
            return (Criteria) this;
        }

        public Criteria andPaymentIdLike(String value) {
            addCriterion("PAYMENT_ID like", value, "paymentId");
            return (Criteria) this;
        }

        public Criteria andPaymentIdNotLike(String value) {
            addCriterion("PAYMENT_ID not like", value, "paymentId");
            return (Criteria) this;
        }

        public Criteria andPaymentIdIn(List<String> values) {
            addCriterion("PAYMENT_ID in", values, "paymentId");
            return (Criteria) this;
        }

        public Criteria andPaymentIdNotIn(List<String> values) {
            addCriterion("PAYMENT_ID not in", values, "paymentId");
            return (Criteria) this;
        }

        public Criteria andPaymentIdBetween(String value1, String value2) {
            addCriterion("PAYMENT_ID between", value1, value2, "paymentId");
            return (Criteria) this;
        }

        public Criteria andPaymentIdNotBetween(String value1, String value2) {
            addCriterion("PAYMENT_ID not between", value1, value2, "paymentId");
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

        public Criteria andCOrderIdIsNull() {
            addCriterion("C_ORDER_ID is null");
            return (Criteria) this;
        }

        public Criteria andCOrderIdIsNotNull() {
            addCriterion("C_ORDER_ID is not null");
            return (Criteria) this;
        }

        public Criteria andCOrderIdEqualTo(String value) {
            addCriterion("C_ORDER_ID =", value, "cOrderId");
            return (Criteria) this;
        }

        public Criteria andCOrderIdNotEqualTo(String value) {
            addCriterion("C_ORDER_ID <>", value, "cOrderId");
            return (Criteria) this;
        }

        public Criteria andCOrderIdGreaterThan(String value) {
            addCriterion("C_ORDER_ID >", value, "cOrderId");
            return (Criteria) this;
        }

        public Criteria andCOrderIdGreaterThanOrEqualTo(String value) {
            addCriterion("C_ORDER_ID >=", value, "cOrderId");
            return (Criteria) this;
        }

        public Criteria andCOrderIdLessThan(String value) {
            addCriterion("C_ORDER_ID <", value, "cOrderId");
            return (Criteria) this;
        }

        public Criteria andCOrderIdLessThanOrEqualTo(String value) {
            addCriterion("C_ORDER_ID <=", value, "cOrderId");
            return (Criteria) this;
        }

        public Criteria andCOrderIdLike(String value) {
            addCriterion("C_ORDER_ID like", value, "cOrderId");
            return (Criteria) this;
        }

        public Criteria andCOrderIdNotLike(String value) {
            addCriterion("C_ORDER_ID not like", value, "cOrderId");
            return (Criteria) this;
        }

        public Criteria andCOrderIdIn(List<String> values) {
            addCriterion("C_ORDER_ID in", values, "cOrderId");
            return (Criteria) this;
        }

        public Criteria andCOrderIdNotIn(List<String> values) {
            addCriterion("C_ORDER_ID not in", values, "cOrderId");
            return (Criteria) this;
        }

        public Criteria andCOrderIdBetween(String value1, String value2) {
            addCriterion("C_ORDER_ID between", value1, value2, "cOrderId");
            return (Criteria) this;
        }

        public Criteria andCOrderIdNotBetween(String value1, String value2) {
            addCriterion("C_ORDER_ID not between", value1, value2, "cOrderId");
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

        public Criteria andPayTimeIsNull() {
            addCriterion("PAY_TIME is null");
            return (Criteria) this;
        }

        public Criteria andPayTimeIsNotNull() {
            addCriterion("PAY_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andPayTimeEqualTo(Date value) {
            addCriterion("PAY_TIME =", value, "payTime");
            return (Criteria) this;
        }

        public Criteria andPayTimeNotEqualTo(Date value) {
            addCriterion("PAY_TIME <>", value, "payTime");
            return (Criteria) this;
        }

        public Criteria andPayTimeGreaterThan(Date value) {
            addCriterion("PAY_TIME >", value, "payTime");
            return (Criteria) this;
        }

        public Criteria andPayTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("PAY_TIME >=", value, "payTime");
            return (Criteria) this;
        }

        public Criteria andPayTimeLessThan(Date value) {
            addCriterion("PAY_TIME <", value, "payTime");
            return (Criteria) this;
        }

        public Criteria andPayTimeLessThanOrEqualTo(Date value) {
            addCriterion("PAY_TIME <=", value, "payTime");
            return (Criteria) this;
        }

        public Criteria andPayTimeIn(List<Date> values) {
            addCriterion("PAY_TIME in", values, "payTime");
            return (Criteria) this;
        }

        public Criteria andPayTimeNotIn(List<Date> values) {
            addCriterion("PAY_TIME not in", values, "payTime");
            return (Criteria) this;
        }

        public Criteria andPayTimeBetween(Date value1, Date value2) {
            addCriterion("PAY_TIME between", value1, value2, "payTime");
            return (Criteria) this;
        }

        public Criteria andPayTimeNotBetween(Date value1, Date value2) {
            addCriterion("PAY_TIME not between", value1, value2, "payTime");
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

        public Criteria andSrcTypeIsNull() {
            addCriterion("SRC_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andSrcTypeIsNotNull() {
            addCriterion("SRC_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andSrcTypeEqualTo(String value) {
            addCriterion("SRC_TYPE =", value, "srcType");
            return (Criteria) this;
        }

        public Criteria andSrcTypeNotEqualTo(String value) {
            addCriterion("SRC_TYPE <>", value, "srcType");
            return (Criteria) this;
        }

        public Criteria andSrcTypeGreaterThan(String value) {
            addCriterion("SRC_TYPE >", value, "srcType");
            return (Criteria) this;
        }

        public Criteria andSrcTypeGreaterThanOrEqualTo(String value) {
            addCriterion("SRC_TYPE >=", value, "srcType");
            return (Criteria) this;
        }

        public Criteria andSrcTypeLessThan(String value) {
            addCriterion("SRC_TYPE <", value, "srcType");
            return (Criteria) this;
        }

        public Criteria andSrcTypeLessThanOrEqualTo(String value) {
            addCriterion("SRC_TYPE <=", value, "srcType");
            return (Criteria) this;
        }

        public Criteria andSrcTypeLike(String value) {
            addCriterion("SRC_TYPE like", value, "srcType");
            return (Criteria) this;
        }

        public Criteria andSrcTypeNotLike(String value) {
            addCriterion("SRC_TYPE not like", value, "srcType");
            return (Criteria) this;
        }

        public Criteria andSrcTypeIn(List<String> values) {
            addCriterion("SRC_TYPE in", values, "srcType");
            return (Criteria) this;
        }

        public Criteria andSrcTypeNotIn(List<String> values) {
            addCriterion("SRC_TYPE not in", values, "srcType");
            return (Criteria) this;
        }

        public Criteria andSrcTypeBetween(String value1, String value2) {
            addCriterion("SRC_TYPE between", value1, value2, "srcType");
            return (Criteria) this;
        }

        public Criteria andSrcTypeNotBetween(String value1, String value2) {
            addCriterion("SRC_TYPE not between", value1, value2, "srcType");
            return (Criteria) this;
        }

        public Criteria andPlanPayTimeIsNull() {
            addCriterion("PLAN_PAY_TIME is null");
            return (Criteria) this;
        }

        public Criteria andPlanPayTimeIsNotNull() {
            addCriterion("PLAN_PAY_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andPlanPayTimeEqualTo(Date value) {
            addCriterion("PLAN_PAY_TIME =", value, "planPayTime");
            return (Criteria) this;
        }

        public Criteria andPlanPayTimeNotEqualTo(Date value) {
            addCriterion("PLAN_PAY_TIME <>", value, "planPayTime");
            return (Criteria) this;
        }

        public Criteria andPlanPayTimeGreaterThan(Date value) {
            addCriterion("PLAN_PAY_TIME >", value, "planPayTime");
            return (Criteria) this;
        }

        public Criteria andPlanPayTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("PLAN_PAY_TIME >=", value, "planPayTime");
            return (Criteria) this;
        }

        public Criteria andPlanPayTimeLessThan(Date value) {
            addCriterion("PLAN_PAY_TIME <", value, "planPayTime");
            return (Criteria) this;
        }

        public Criteria andPlanPayTimeLessThanOrEqualTo(Date value) {
            addCriterion("PLAN_PAY_TIME <=", value, "planPayTime");
            return (Criteria) this;
        }

        public Criteria andPlanPayTimeIn(List<Date> values) {
            addCriterion("PLAN_PAY_TIME in", values, "planPayTime");
            return (Criteria) this;
        }

        public Criteria andPlanPayTimeNotIn(List<Date> values) {
            addCriterion("PLAN_PAY_TIME not in", values, "planPayTime");
            return (Criteria) this;
        }

        public Criteria andPlanPayTimeBetween(Date value1, Date value2) {
            addCriterion("PLAN_PAY_TIME between", value1, value2, "planPayTime");
            return (Criteria) this;
        }

        public Criteria andPlanPayTimeNotBetween(Date value1, Date value2) {
            addCriterion("PLAN_PAY_TIME not between", value1, value2, "planPayTime");
            return (Criteria) this;
        }

        public Criteria andPlanNumIsNull() {
            addCriterion("PLAN_NUM is null");
            return (Criteria) this;
        }

        public Criteria andPlanNumIsNotNull() {
            addCriterion("PLAN_NUM is not null");
            return (Criteria) this;
        }

        public Criteria andPlanNumEqualTo(BigDecimal value) {
            addCriterion("PLAN_NUM =", value, "planNum");
            return (Criteria) this;
        }

        public Criteria andPlanNumNotEqualTo(BigDecimal value) {
            addCriterion("PLAN_NUM <>", value, "planNum");
            return (Criteria) this;
        }

        public Criteria andPlanNumGreaterThan(BigDecimal value) {
            addCriterion("PLAN_NUM >", value, "planNum");
            return (Criteria) this;
        }

        public Criteria andPlanNumGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("PLAN_NUM >=", value, "planNum");
            return (Criteria) this;
        }

        public Criteria andPlanNumLessThan(BigDecimal value) {
            addCriterion("PLAN_NUM <", value, "planNum");
            return (Criteria) this;
        }

        public Criteria andPlanNumLessThanOrEqualTo(BigDecimal value) {
            addCriterion("PLAN_NUM <=", value, "planNum");
            return (Criteria) this;
        }

        public Criteria andPlanNumIn(List<BigDecimal> values) {
            addCriterion("PLAN_NUM in", values, "planNum");
            return (Criteria) this;
        }

        public Criteria andPlanNumNotIn(List<BigDecimal> values) {
            addCriterion("PLAN_NUM not in", values, "planNum");
            return (Criteria) this;
        }

        public Criteria andPlanNumBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("PLAN_NUM between", value1, value2, "planNum");
            return (Criteria) this;
        }

        public Criteria andPlanNumNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("PLAN_NUM not between", value1, value2, "planNum");
            return (Criteria) this;
        }

        public Criteria andAccountUserIsNull() {
            addCriterion("ACCOUNT_USER is null");
            return (Criteria) this;
        }

        public Criteria andAccountUserIsNotNull() {
            addCriterion("ACCOUNT_USER is not null");
            return (Criteria) this;
        }

        public Criteria andAccountUserEqualTo(String value) {
            addCriterion("ACCOUNT_USER =", value, "accountUser");
            return (Criteria) this;
        }

        public Criteria andAccountUserNotEqualTo(String value) {
            addCriterion("ACCOUNT_USER <>", value, "accountUser");
            return (Criteria) this;
        }

        public Criteria andAccountUserGreaterThan(String value) {
            addCriterion("ACCOUNT_USER >", value, "accountUser");
            return (Criteria) this;
        }

        public Criteria andAccountUserGreaterThanOrEqualTo(String value) {
            addCriterion("ACCOUNT_USER >=", value, "accountUser");
            return (Criteria) this;
        }

        public Criteria andAccountUserLessThan(String value) {
            addCriterion("ACCOUNT_USER <", value, "accountUser");
            return (Criteria) this;
        }

        public Criteria andAccountUserLessThanOrEqualTo(String value) {
            addCriterion("ACCOUNT_USER <=", value, "accountUser");
            return (Criteria) this;
        }

        public Criteria andAccountUserLike(String value) {
            addCriterion("ACCOUNT_USER like", value, "accountUser");
            return (Criteria) this;
        }

        public Criteria andAccountUserNotLike(String value) {
            addCriterion("ACCOUNT_USER not like", value, "accountUser");
            return (Criteria) this;
        }

        public Criteria andAccountUserIn(List<String> values) {
            addCriterion("ACCOUNT_USER in", values, "accountUser");
            return (Criteria) this;
        }

        public Criteria andAccountUserNotIn(List<String> values) {
            addCriterion("ACCOUNT_USER not in", values, "accountUser");
            return (Criteria) this;
        }

        public Criteria andAccountUserBetween(String value1, String value2) {
            addCriterion("ACCOUNT_USER between", value1, value2, "accountUser");
            return (Criteria) this;
        }

        public Criteria andAccountUserNotBetween(String value1, String value2) {
            addCriterion("ACCOUNT_USER not between", value1, value2, "accountUser");
            return (Criteria) this;
        }

        public Criteria andAccountpayTypeIsNull() {
            addCriterion("ACCOUNTPAY_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andAccountpayTypeIsNotNull() {
            addCriterion("ACCOUNTPAY_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andAccountpayTypeEqualTo(String value) {
            addCriterion("ACCOUNTPAY_TYPE =", value, "accountpayType");
            return (Criteria) this;
        }

        public Criteria andAccountpayTypeNotEqualTo(String value) {
            addCriterion("ACCOUNTPAY_TYPE <>", value, "accountpayType");
            return (Criteria) this;
        }

        public Criteria andAccountpayTypeGreaterThan(String value) {
            addCriterion("ACCOUNTPAY_TYPE >", value, "accountpayType");
            return (Criteria) this;
        }

        public Criteria andAccountpayTypeGreaterThanOrEqualTo(String value) {
            addCriterion("ACCOUNTPAY_TYPE >=", value, "accountpayType");
            return (Criteria) this;
        }

        public Criteria andAccountpayTypeLessThan(String value) {
            addCriterion("ACCOUNTPAY_TYPE <", value, "accountpayType");
            return (Criteria) this;
        }

        public Criteria andAccountpayTypeLessThanOrEqualTo(String value) {
            addCriterion("ACCOUNTPAY_TYPE <=", value, "accountpayType");
            return (Criteria) this;
        }

        public Criteria andAccountpayTypeLike(String value) {
            addCriterion("ACCOUNTPAY_TYPE like", value, "accountpayType");
            return (Criteria) this;
        }

        public Criteria andAccountpayTypeNotLike(String value) {
            addCriterion("ACCOUNTPAY_TYPE not like", value, "accountpayType");
            return (Criteria) this;
        }

        public Criteria andAccountpayTypeIn(List<String> values) {
            addCriterion("ACCOUNTPAY_TYPE in", values, "accountpayType");
            return (Criteria) this;
        }

        public Criteria andAccountpayTypeNotIn(List<String> values) {
            addCriterion("ACCOUNTPAY_TYPE not in", values, "accountpayType");
            return (Criteria) this;
        }

        public Criteria andAccountpayTypeBetween(String value1, String value2) {
            addCriterion("ACCOUNTPAY_TYPE between", value1, value2, "accountpayType");
            return (Criteria) this;
        }

        public Criteria andAccountpayTypeNotBetween(String value1, String value2) {
            addCriterion("ACCOUNTPAY_TYPE not between", value1, value2, "accountpayType");
            return (Criteria) this;
        }

        public Criteria andPayAccountIsNull() {
            addCriterion("PAY_ACCOUNT is null");
            return (Criteria) this;
        }

        public Criteria andPayAccountIsNotNull() {
            addCriterion("PAY_ACCOUNT is not null");
            return (Criteria) this;
        }

        public Criteria andPayAccountEqualTo(String value) {
            addCriterion("PAY_ACCOUNT =", value, "payAccount");
            return (Criteria) this;
        }

        public Criteria andPayAccountNotEqualTo(String value) {
            addCriterion("PAY_ACCOUNT <>", value, "payAccount");
            return (Criteria) this;
        }

        public Criteria andPayAccountGreaterThan(String value) {
            addCriterion("PAY_ACCOUNT >", value, "payAccount");
            return (Criteria) this;
        }

        public Criteria andPayAccountGreaterThanOrEqualTo(String value) {
            addCriterion("PAY_ACCOUNT >=", value, "payAccount");
            return (Criteria) this;
        }

        public Criteria andPayAccountLessThan(String value) {
            addCriterion("PAY_ACCOUNT <", value, "payAccount");
            return (Criteria) this;
        }

        public Criteria andPayAccountLessThanOrEqualTo(String value) {
            addCriterion("PAY_ACCOUNT <=", value, "payAccount");
            return (Criteria) this;
        }

        public Criteria andPayAccountLike(String value) {
            addCriterion("PAY_ACCOUNT like", value, "payAccount");
            return (Criteria) this;
        }

        public Criteria andPayAccountNotLike(String value) {
            addCriterion("PAY_ACCOUNT not like", value, "payAccount");
            return (Criteria) this;
        }

        public Criteria andPayAccountIn(List<String> values) {
            addCriterion("PAY_ACCOUNT in", values, "payAccount");
            return (Criteria) this;
        }

        public Criteria andPayAccountNotIn(List<String> values) {
            addCriterion("PAY_ACCOUNT not in", values, "payAccount");
            return (Criteria) this;
        }

        public Criteria andPayAccountBetween(String value1, String value2) {
            addCriterion("PAY_ACCOUNT between", value1, value2, "payAccount");
            return (Criteria) this;
        }

        public Criteria andPayAccountNotBetween(String value1, String value2) {
            addCriterion("PAY_ACCOUNT not between", value1, value2, "payAccount");
            return (Criteria) this;
        }

        public Criteria andPaymentStatusIsNull() {
            addCriterion("PAYMENT_STATUS is null");
            return (Criteria) this;
        }

        public Criteria andPaymentStatusIsNotNull() {
            addCriterion("PAYMENT_STATUS is not null");
            return (Criteria) this;
        }

        public Criteria andPaymentStatusEqualTo(BigDecimal value) {
            addCriterion("PAYMENT_STATUS =", value, "paymentStatus");
            return (Criteria) this;
        }

        public Criteria andPaymentStatusNotEqualTo(BigDecimal value) {
            addCriterion("PAYMENT_STATUS <>", value, "paymentStatus");
            return (Criteria) this;
        }

        public Criteria andPaymentStatusGreaterThan(BigDecimal value) {
            addCriterion("PAYMENT_STATUS >", value, "paymentStatus");
            return (Criteria) this;
        }

        public Criteria andPaymentStatusGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("PAYMENT_STATUS >=", value, "paymentStatus");
            return (Criteria) this;
        }

        public Criteria andPaymentStatusLessThan(BigDecimal value) {
            addCriterion("PAYMENT_STATUS <", value, "paymentStatus");
            return (Criteria) this;
        }

        public Criteria andPaymentStatusLessThanOrEqualTo(BigDecimal value) {
            addCriterion("PAYMENT_STATUS <=", value, "paymentStatus");
            return (Criteria) this;
        }

        public Criteria andPaymentStatusIn(List<BigDecimal> values) {
            addCriterion("PAYMENT_STATUS in", values, "paymentStatus");
            return (Criteria) this;
        }

        public Criteria andPaymentStatusNotIn(List<BigDecimal> values) {
            addCriterion("PAYMENT_STATUS not in", values, "paymentStatus");
            return (Criteria) this;
        }

        public Criteria andPaymentStatusBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("PAYMENT_STATUS between", value1, value2, "paymentStatus");
            return (Criteria) this;
        }

        public Criteria andPaymentStatusNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("PAYMENT_STATUS not between", value1, value2, "paymentStatus");
            return (Criteria) this;
        }

        public Criteria andDeferredRidIsNull() {
            addCriterion("DEFERRED_RID is null");
            return (Criteria) this;
        }

        public Criteria andDeferredRidIsNotNull() {
            addCriterion("DEFERRED_RID is not null");
            return (Criteria) this;
        }

        public Criteria andDeferredRidEqualTo(String value) {
            addCriterion("DEFERRED_RID =", value, "deferredRid");
            return (Criteria) this;
        }

        public Criteria andDeferredRidNotEqualTo(String value) {
            addCriterion("DEFERRED_RID <>", value, "deferredRid");
            return (Criteria) this;
        }

        public Criteria andDeferredRidGreaterThan(String value) {
            addCriterion("DEFERRED_RID >", value, "deferredRid");
            return (Criteria) this;
        }

        public Criteria andDeferredRidGreaterThanOrEqualTo(String value) {
            addCriterion("DEFERRED_RID >=", value, "deferredRid");
            return (Criteria) this;
        }

        public Criteria andDeferredRidLessThan(String value) {
            addCriterion("DEFERRED_RID <", value, "deferredRid");
            return (Criteria) this;
        }

        public Criteria andDeferredRidLessThanOrEqualTo(String value) {
            addCriterion("DEFERRED_RID <=", value, "deferredRid");
            return (Criteria) this;
        }

        public Criteria andDeferredRidLike(String value) {
            addCriterion("DEFERRED_RID like", value, "deferredRid");
            return (Criteria) this;
        }

        public Criteria andDeferredRidNotLike(String value) {
            addCriterion("DEFERRED_RID not like", value, "deferredRid");
            return (Criteria) this;
        }

        public Criteria andDeferredRidIn(List<String> values) {
            addCriterion("DEFERRED_RID in", values, "deferredRid");
            return (Criteria) this;
        }

        public Criteria andDeferredRidNotIn(List<String> values) {
            addCriterion("DEFERRED_RID not in", values, "deferredRid");
            return (Criteria) this;
        }

        public Criteria andDeferredRidBetween(String value1, String value2) {
            addCriterion("DEFERRED_RID between", value1, value2, "deferredRid");
            return (Criteria) this;
        }

        public Criteria andDeferredRidNotBetween(String value1, String value2) {
            addCriterion("DEFERRED_RID not between", value1, value2, "deferredRid");
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

        public Criteria andCollectCompanyIsNull() {
            addCriterion("COLLECT_COMPANY is null");
            return (Criteria) this;
        }

        public Criteria andCollectCompanyIsNotNull() {
            addCriterion("COLLECT_COMPANY is not null");
            return (Criteria) this;
        }

        public Criteria andCollectCompanyEqualTo(String value) {
            addCriterion("COLLECT_COMPANY =", value, "collectCompany");
            return (Criteria) this;
        }

        public Criteria andCollectCompanyNotEqualTo(String value) {
            addCriterion("COLLECT_COMPANY <>", value, "collectCompany");
            return (Criteria) this;
        }

        public Criteria andCollectCompanyGreaterThan(String value) {
            addCriterion("COLLECT_COMPANY >", value, "collectCompany");
            return (Criteria) this;
        }

        public Criteria andCollectCompanyGreaterThanOrEqualTo(String value) {
            addCriterion("COLLECT_COMPANY >=", value, "collectCompany");
            return (Criteria) this;
        }

        public Criteria andCollectCompanyLessThan(String value) {
            addCriterion("COLLECT_COMPANY <", value, "collectCompany");
            return (Criteria) this;
        }

        public Criteria andCollectCompanyLessThanOrEqualTo(String value) {
            addCriterion("COLLECT_COMPANY <=", value, "collectCompany");
            return (Criteria) this;
        }

        public Criteria andCollectCompanyLike(String value) {
            addCriterion("COLLECT_COMPANY like", value, "collectCompany");
            return (Criteria) this;
        }

        public Criteria andCollectCompanyNotLike(String value) {
            addCriterion("COLLECT_COMPANY not like", value, "collectCompany");
            return (Criteria) this;
        }

        public Criteria andCollectCompanyIn(List<String> values) {
            addCriterion("COLLECT_COMPANY in", values, "collectCompany");
            return (Criteria) this;
        }

        public Criteria andCollectCompanyNotIn(List<String> values) {
            addCriterion("COLLECT_COMPANY not in", values, "collectCompany");
            return (Criteria) this;
        }

        public Criteria andCollectCompanyBetween(String value1, String value2) {
            addCriterion("COLLECT_COMPANY between", value1, value2, "collectCompany");
            return (Criteria) this;
        }

        public Criteria andCollectCompanyNotBetween(String value1, String value2) {
            addCriterion("COLLECT_COMPANY not between", value1, value2, "collectCompany");
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

        public Criteria andCDateIsNull() {
            addCriterion("C_DATE is null");
            return (Criteria) this;
        }

        public Criteria andCDateIsNotNull() {
            addCriterion("C_DATE is not null");
            return (Criteria) this;
        }

        public Criteria andCDateEqualTo(Date value) {
            addCriterion("C_DATE =", value, "cDate");
            return (Criteria) this;
        }

        public Criteria andCDateNotEqualTo(Date value) {
            addCriterion("C_DATE <>", value, "cDate");
            return (Criteria) this;
        }

        public Criteria andCDateGreaterThan(Date value) {
            addCriterion("C_DATE >", value, "cDate");
            return (Criteria) this;
        }

        public Criteria andCDateGreaterThanOrEqualTo(Date value) {
            addCriterion("C_DATE >=", value, "cDate");
            return (Criteria) this;
        }

        public Criteria andCDateLessThan(Date value) {
            addCriterion("C_DATE <", value, "cDate");
            return (Criteria) this;
        }

        public Criteria andCDateLessThanOrEqualTo(Date value) {
            addCriterion("C_DATE <=", value, "cDate");
            return (Criteria) this;
        }

        public Criteria andCDateIn(List<Date> values) {
            addCriterion("C_DATE in", values, "cDate");
            return (Criteria) this;
        }

        public Criteria andCDateNotIn(List<Date> values) {
            addCriterion("C_DATE not in", values, "cDate");
            return (Criteria) this;
        }

        public Criteria andCDateBetween(Date value1, Date value2) {
            addCriterion("C_DATE between", value1, value2, "cDate");
            return (Criteria) this;
        }

        public Criteria andCDateNotBetween(Date value1, Date value2) {
            addCriterion("C_DATE not between", value1, value2, "cDate");
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