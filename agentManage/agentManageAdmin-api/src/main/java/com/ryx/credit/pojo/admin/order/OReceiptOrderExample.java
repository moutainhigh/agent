package com.ryx.credit.pojo.admin.order;

import com.ryx.credit.common.util.Page;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OReceiptOrderExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Page page;

    protected Integer limitStart;

    protected Integer limitEnd;

    public OReceiptOrderExample() {
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

        public Criteria andReceiptNumIsNull() {
            addCriterion("RECEIPT_NUM is null");
            return (Criteria) this;
        }

        public Criteria andReceiptNumIsNotNull() {
            addCriterion("RECEIPT_NUM is not null");
            return (Criteria) this;
        }

        public Criteria andReceiptNumEqualTo(String value) {
            addCriterion("RECEIPT_NUM =", value, "receiptNum");
            return (Criteria) this;
        }

        public Criteria andReceiptNumNotEqualTo(String value) {
            addCriterion("RECEIPT_NUM <>", value, "receiptNum");
            return (Criteria) this;
        }

        public Criteria andReceiptNumGreaterThan(String value) {
            addCriterion("RECEIPT_NUM >", value, "receiptNum");
            return (Criteria) this;
        }

        public Criteria andReceiptNumGreaterThanOrEqualTo(String value) {
            addCriterion("RECEIPT_NUM >=", value, "receiptNum");
            return (Criteria) this;
        }

        public Criteria andReceiptNumLessThan(String value) {
            addCriterion("RECEIPT_NUM <", value, "receiptNum");
            return (Criteria) this;
        }

        public Criteria andReceiptNumLessThanOrEqualTo(String value) {
            addCriterion("RECEIPT_NUM <=", value, "receiptNum");
            return (Criteria) this;
        }

        public Criteria andReceiptNumLike(String value) {
            addCriterion("RECEIPT_NUM like", value, "receiptNum");
            return (Criteria) this;
        }

        public Criteria andReceiptNumNotLike(String value) {
            addCriterion("RECEIPT_NUM not like", value, "receiptNum");
            return (Criteria) this;
        }

        public Criteria andReceiptNumIn(List<String> values) {
            addCriterion("RECEIPT_NUM in", values, "receiptNum");
            return (Criteria) this;
        }

        public Criteria andReceiptNumNotIn(List<String> values) {
            addCriterion("RECEIPT_NUM not in", values, "receiptNum");
            return (Criteria) this;
        }

        public Criteria andReceiptNumBetween(String value1, String value2) {
            addCriterion("RECEIPT_NUM between", value1, value2, "receiptNum");
            return (Criteria) this;
        }

        public Criteria andReceiptNumNotBetween(String value1, String value2) {
            addCriterion("RECEIPT_NUM not between", value1, value2, "receiptNum");
            return (Criteria) this;
        }

        public Criteria andAddressIdIsNull() {
            addCriterion("ADDRESS_ID is null");
            return (Criteria) this;
        }

        public Criteria andAddressIdIsNotNull() {
            addCriterion("ADDRESS_ID is not null");
            return (Criteria) this;
        }

        public Criteria andAddressIdEqualTo(String value) {
            addCriterion("ADDRESS_ID =", value, "addressId");
            return (Criteria) this;
        }

        public Criteria andAddressIdNotEqualTo(String value) {
            addCriterion("ADDRESS_ID <>", value, "addressId");
            return (Criteria) this;
        }

        public Criteria andAddressIdGreaterThan(String value) {
            addCriterion("ADDRESS_ID >", value, "addressId");
            return (Criteria) this;
        }

        public Criteria andAddressIdGreaterThanOrEqualTo(String value) {
            addCriterion("ADDRESS_ID >=", value, "addressId");
            return (Criteria) this;
        }

        public Criteria andAddressIdLessThan(String value) {
            addCriterion("ADDRESS_ID <", value, "addressId");
            return (Criteria) this;
        }

        public Criteria andAddressIdLessThanOrEqualTo(String value) {
            addCriterion("ADDRESS_ID <=", value, "addressId");
            return (Criteria) this;
        }

        public Criteria andAddressIdLike(String value) {
            addCriterion("ADDRESS_ID like", value, "addressId");
            return (Criteria) this;
        }

        public Criteria andAddressIdNotLike(String value) {
            addCriterion("ADDRESS_ID not like", value, "addressId");
            return (Criteria) this;
        }

        public Criteria andAddressIdIn(List<String> values) {
            addCriterion("ADDRESS_ID in", values, "addressId");
            return (Criteria) this;
        }

        public Criteria andAddressIdNotIn(List<String> values) {
            addCriterion("ADDRESS_ID not in", values, "addressId");
            return (Criteria) this;
        }

        public Criteria andAddressIdBetween(String value1, String value2) {
            addCriterion("ADDRESS_ID between", value1, value2, "addressId");
            return (Criteria) this;
        }

        public Criteria andAddressIdNotBetween(String value1, String value2) {
            addCriterion("ADDRESS_ID not between", value1, value2, "addressId");
            return (Criteria) this;
        }

        public Criteria andAddrRealnameIsNull() {
            addCriterion("ADDR_REALNAME is null");
            return (Criteria) this;
        }

        public Criteria andAddrRealnameIsNotNull() {
            addCriterion("ADDR_REALNAME is not null");
            return (Criteria) this;
        }

        public Criteria andAddrRealnameEqualTo(String value) {
            addCriterion("ADDR_REALNAME =", value, "addrRealname");
            return (Criteria) this;
        }

        public Criteria andAddrRealnameNotEqualTo(String value) {
            addCriterion("ADDR_REALNAME <>", value, "addrRealname");
            return (Criteria) this;
        }

        public Criteria andAddrRealnameGreaterThan(String value) {
            addCriterion("ADDR_REALNAME >", value, "addrRealname");
            return (Criteria) this;
        }

        public Criteria andAddrRealnameGreaterThanOrEqualTo(String value) {
            addCriterion("ADDR_REALNAME >=", value, "addrRealname");
            return (Criteria) this;
        }

        public Criteria andAddrRealnameLessThan(String value) {
            addCriterion("ADDR_REALNAME <", value, "addrRealname");
            return (Criteria) this;
        }

        public Criteria andAddrRealnameLessThanOrEqualTo(String value) {
            addCriterion("ADDR_REALNAME <=", value, "addrRealname");
            return (Criteria) this;
        }

        public Criteria andAddrRealnameLike(String value) {
            addCriterion("ADDR_REALNAME like", value, "addrRealname");
            return (Criteria) this;
        }

        public Criteria andAddrRealnameNotLike(String value) {
            addCriterion("ADDR_REALNAME not like", value, "addrRealname");
            return (Criteria) this;
        }

        public Criteria andAddrRealnameIn(List<String> values) {
            addCriterion("ADDR_REALNAME in", values, "addrRealname");
            return (Criteria) this;
        }

        public Criteria andAddrRealnameNotIn(List<String> values) {
            addCriterion("ADDR_REALNAME not in", values, "addrRealname");
            return (Criteria) this;
        }

        public Criteria andAddrRealnameBetween(String value1, String value2) {
            addCriterion("ADDR_REALNAME between", value1, value2, "addrRealname");
            return (Criteria) this;
        }

        public Criteria andAddrRealnameNotBetween(String value1, String value2) {
            addCriterion("ADDR_REALNAME not between", value1, value2, "addrRealname");
            return (Criteria) this;
        }

        public Criteria andAddrMobileIsNull() {
            addCriterion("ADDR_MOBILE is null");
            return (Criteria) this;
        }

        public Criteria andAddrMobileIsNotNull() {
            addCriterion("ADDR_MOBILE is not null");
            return (Criteria) this;
        }

        public Criteria andAddrMobileEqualTo(String value) {
            addCriterion("ADDR_MOBILE =", value, "addrMobile");
            return (Criteria) this;
        }

        public Criteria andAddrMobileNotEqualTo(String value) {
            addCriterion("ADDR_MOBILE <>", value, "addrMobile");
            return (Criteria) this;
        }

        public Criteria andAddrMobileGreaterThan(String value) {
            addCriterion("ADDR_MOBILE >", value, "addrMobile");
            return (Criteria) this;
        }

        public Criteria andAddrMobileGreaterThanOrEqualTo(String value) {
            addCriterion("ADDR_MOBILE >=", value, "addrMobile");
            return (Criteria) this;
        }

        public Criteria andAddrMobileLessThan(String value) {
            addCriterion("ADDR_MOBILE <", value, "addrMobile");
            return (Criteria) this;
        }

        public Criteria andAddrMobileLessThanOrEqualTo(String value) {
            addCriterion("ADDR_MOBILE <=", value, "addrMobile");
            return (Criteria) this;
        }

        public Criteria andAddrMobileLike(String value) {
            addCriterion("ADDR_MOBILE like", value, "addrMobile");
            return (Criteria) this;
        }

        public Criteria andAddrMobileNotLike(String value) {
            addCriterion("ADDR_MOBILE not like", value, "addrMobile");
            return (Criteria) this;
        }

        public Criteria andAddrMobileIn(List<String> values) {
            addCriterion("ADDR_MOBILE in", values, "addrMobile");
            return (Criteria) this;
        }

        public Criteria andAddrMobileNotIn(List<String> values) {
            addCriterion("ADDR_MOBILE not in", values, "addrMobile");
            return (Criteria) this;
        }

        public Criteria andAddrMobileBetween(String value1, String value2) {
            addCriterion("ADDR_MOBILE between", value1, value2, "addrMobile");
            return (Criteria) this;
        }

        public Criteria andAddrMobileNotBetween(String value1, String value2) {
            addCriterion("ADDR_MOBILE not between", value1, value2, "addrMobile");
            return (Criteria) this;
        }

        public Criteria andAddrProvinceIsNull() {
            addCriterion("ADDR_PROVINCE is null");
            return (Criteria) this;
        }

        public Criteria andAddrProvinceIsNotNull() {
            addCriterion("ADDR_PROVINCE is not null");
            return (Criteria) this;
        }

        public Criteria andAddrProvinceEqualTo(String value) {
            addCriterion("ADDR_PROVINCE =", value, "addrProvince");
            return (Criteria) this;
        }

        public Criteria andAddrProvinceNotEqualTo(String value) {
            addCriterion("ADDR_PROVINCE <>", value, "addrProvince");
            return (Criteria) this;
        }

        public Criteria andAddrProvinceGreaterThan(String value) {
            addCriterion("ADDR_PROVINCE >", value, "addrProvince");
            return (Criteria) this;
        }

        public Criteria andAddrProvinceGreaterThanOrEqualTo(String value) {
            addCriterion("ADDR_PROVINCE >=", value, "addrProvince");
            return (Criteria) this;
        }

        public Criteria andAddrProvinceLessThan(String value) {
            addCriterion("ADDR_PROVINCE <", value, "addrProvince");
            return (Criteria) this;
        }

        public Criteria andAddrProvinceLessThanOrEqualTo(String value) {
            addCriterion("ADDR_PROVINCE <=", value, "addrProvince");
            return (Criteria) this;
        }

        public Criteria andAddrProvinceLike(String value) {
            addCriterion("ADDR_PROVINCE like", value, "addrProvince");
            return (Criteria) this;
        }

        public Criteria andAddrProvinceNotLike(String value) {
            addCriterion("ADDR_PROVINCE not like", value, "addrProvince");
            return (Criteria) this;
        }

        public Criteria andAddrProvinceIn(List<String> values) {
            addCriterion("ADDR_PROVINCE in", values, "addrProvince");
            return (Criteria) this;
        }

        public Criteria andAddrProvinceNotIn(List<String> values) {
            addCriterion("ADDR_PROVINCE not in", values, "addrProvince");
            return (Criteria) this;
        }

        public Criteria andAddrProvinceBetween(String value1, String value2) {
            addCriterion("ADDR_PROVINCE between", value1, value2, "addrProvince");
            return (Criteria) this;
        }

        public Criteria andAddrProvinceNotBetween(String value1, String value2) {
            addCriterion("ADDR_PROVINCE not between", value1, value2, "addrProvince");
            return (Criteria) this;
        }

        public Criteria andAddrCityIsNull() {
            addCriterion("ADDR_CITY is null");
            return (Criteria) this;
        }

        public Criteria andAddrCityIsNotNull() {
            addCriterion("ADDR_CITY is not null");
            return (Criteria) this;
        }

        public Criteria andAddrCityEqualTo(String value) {
            addCriterion("ADDR_CITY =", value, "addrCity");
            return (Criteria) this;
        }

        public Criteria andAddrCityNotEqualTo(String value) {
            addCriterion("ADDR_CITY <>", value, "addrCity");
            return (Criteria) this;
        }

        public Criteria andAddrCityGreaterThan(String value) {
            addCriterion("ADDR_CITY >", value, "addrCity");
            return (Criteria) this;
        }

        public Criteria andAddrCityGreaterThanOrEqualTo(String value) {
            addCriterion("ADDR_CITY >=", value, "addrCity");
            return (Criteria) this;
        }

        public Criteria andAddrCityLessThan(String value) {
            addCriterion("ADDR_CITY <", value, "addrCity");
            return (Criteria) this;
        }

        public Criteria andAddrCityLessThanOrEqualTo(String value) {
            addCriterion("ADDR_CITY <=", value, "addrCity");
            return (Criteria) this;
        }

        public Criteria andAddrCityLike(String value) {
            addCriterion("ADDR_CITY like", value, "addrCity");
            return (Criteria) this;
        }

        public Criteria andAddrCityNotLike(String value) {
            addCriterion("ADDR_CITY not like", value, "addrCity");
            return (Criteria) this;
        }

        public Criteria andAddrCityIn(List<String> values) {
            addCriterion("ADDR_CITY in", values, "addrCity");
            return (Criteria) this;
        }

        public Criteria andAddrCityNotIn(List<String> values) {
            addCriterion("ADDR_CITY not in", values, "addrCity");
            return (Criteria) this;
        }

        public Criteria andAddrCityBetween(String value1, String value2) {
            addCriterion("ADDR_CITY between", value1, value2, "addrCity");
            return (Criteria) this;
        }

        public Criteria andAddrCityNotBetween(String value1, String value2) {
            addCriterion("ADDR_CITY not between", value1, value2, "addrCity");
            return (Criteria) this;
        }

        public Criteria andAddrDistrictIsNull() {
            addCriterion("ADDR_DISTRICT is null");
            return (Criteria) this;
        }

        public Criteria andAddrDistrictIsNotNull() {
            addCriterion("ADDR_DISTRICT is not null");
            return (Criteria) this;
        }

        public Criteria andAddrDistrictEqualTo(String value) {
            addCriterion("ADDR_DISTRICT =", value, "addrDistrict");
            return (Criteria) this;
        }

        public Criteria andAddrDistrictNotEqualTo(String value) {
            addCriterion("ADDR_DISTRICT <>", value, "addrDistrict");
            return (Criteria) this;
        }

        public Criteria andAddrDistrictGreaterThan(String value) {
            addCriterion("ADDR_DISTRICT >", value, "addrDistrict");
            return (Criteria) this;
        }

        public Criteria andAddrDistrictGreaterThanOrEqualTo(String value) {
            addCriterion("ADDR_DISTRICT >=", value, "addrDistrict");
            return (Criteria) this;
        }

        public Criteria andAddrDistrictLessThan(String value) {
            addCriterion("ADDR_DISTRICT <", value, "addrDistrict");
            return (Criteria) this;
        }

        public Criteria andAddrDistrictLessThanOrEqualTo(String value) {
            addCriterion("ADDR_DISTRICT <=", value, "addrDistrict");
            return (Criteria) this;
        }

        public Criteria andAddrDistrictLike(String value) {
            addCriterion("ADDR_DISTRICT like", value, "addrDistrict");
            return (Criteria) this;
        }

        public Criteria andAddrDistrictNotLike(String value) {
            addCriterion("ADDR_DISTRICT not like", value, "addrDistrict");
            return (Criteria) this;
        }

        public Criteria andAddrDistrictIn(List<String> values) {
            addCriterion("ADDR_DISTRICT in", values, "addrDistrict");
            return (Criteria) this;
        }

        public Criteria andAddrDistrictNotIn(List<String> values) {
            addCriterion("ADDR_DISTRICT not in", values, "addrDistrict");
            return (Criteria) this;
        }

        public Criteria andAddrDistrictBetween(String value1, String value2) {
            addCriterion("ADDR_DISTRICT between", value1, value2, "addrDistrict");
            return (Criteria) this;
        }

        public Criteria andAddrDistrictNotBetween(String value1, String value2) {
            addCriterion("ADDR_DISTRICT not between", value1, value2, "addrDistrict");
            return (Criteria) this;
        }

        public Criteria andAddrDetailIsNull() {
            addCriterion("ADDR_DETAIL is null");
            return (Criteria) this;
        }

        public Criteria andAddrDetailIsNotNull() {
            addCriterion("ADDR_DETAIL is not null");
            return (Criteria) this;
        }

        public Criteria andAddrDetailEqualTo(String value) {
            addCriterion("ADDR_DETAIL =", value, "addrDetail");
            return (Criteria) this;
        }

        public Criteria andAddrDetailNotEqualTo(String value) {
            addCriterion("ADDR_DETAIL <>", value, "addrDetail");
            return (Criteria) this;
        }

        public Criteria andAddrDetailGreaterThan(String value) {
            addCriterion("ADDR_DETAIL >", value, "addrDetail");
            return (Criteria) this;
        }

        public Criteria andAddrDetailGreaterThanOrEqualTo(String value) {
            addCriterion("ADDR_DETAIL >=", value, "addrDetail");
            return (Criteria) this;
        }

        public Criteria andAddrDetailLessThan(String value) {
            addCriterion("ADDR_DETAIL <", value, "addrDetail");
            return (Criteria) this;
        }

        public Criteria andAddrDetailLessThanOrEqualTo(String value) {
            addCriterion("ADDR_DETAIL <=", value, "addrDetail");
            return (Criteria) this;
        }

        public Criteria andAddrDetailLike(String value) {
            addCriterion("ADDR_DETAIL like", value, "addrDetail");
            return (Criteria) this;
        }

        public Criteria andAddrDetailNotLike(String value) {
            addCriterion("ADDR_DETAIL not like", value, "addrDetail");
            return (Criteria) this;
        }

        public Criteria andAddrDetailIn(List<String> values) {
            addCriterion("ADDR_DETAIL in", values, "addrDetail");
            return (Criteria) this;
        }

        public Criteria andAddrDetailNotIn(List<String> values) {
            addCriterion("ADDR_DETAIL not in", values, "addrDetail");
            return (Criteria) this;
        }

        public Criteria andAddrDetailBetween(String value1, String value2) {
            addCriterion("ADDR_DETAIL between", value1, value2, "addrDetail");
            return (Criteria) this;
        }

        public Criteria andAddrDetailNotBetween(String value1, String value2) {
            addCriterion("ADDR_DETAIL not between", value1, value2, "addrDetail");
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

        public Criteria andZipCodeIsNull() {
            addCriterion("ZIP_CODE is null");
            return (Criteria) this;
        }

        public Criteria andZipCodeIsNotNull() {
            addCriterion("ZIP_CODE is not null");
            return (Criteria) this;
        }

        public Criteria andZipCodeEqualTo(String value) {
            addCriterion("ZIP_CODE =", value, "zipCode");
            return (Criteria) this;
        }

        public Criteria andZipCodeNotEqualTo(String value) {
            addCriterion("ZIP_CODE <>", value, "zipCode");
            return (Criteria) this;
        }

        public Criteria andZipCodeGreaterThan(String value) {
            addCriterion("ZIP_CODE >", value, "zipCode");
            return (Criteria) this;
        }

        public Criteria andZipCodeGreaterThanOrEqualTo(String value) {
            addCriterion("ZIP_CODE >=", value, "zipCode");
            return (Criteria) this;
        }

        public Criteria andZipCodeLessThan(String value) {
            addCriterion("ZIP_CODE <", value, "zipCode");
            return (Criteria) this;
        }

        public Criteria andZipCodeLessThanOrEqualTo(String value) {
            addCriterion("ZIP_CODE <=", value, "zipCode");
            return (Criteria) this;
        }

        public Criteria andZipCodeLike(String value) {
            addCriterion("ZIP_CODE like", value, "zipCode");
            return (Criteria) this;
        }

        public Criteria andZipCodeNotLike(String value) {
            addCriterion("ZIP_CODE not like", value, "zipCode");
            return (Criteria) this;
        }

        public Criteria andZipCodeIn(List<String> values) {
            addCriterion("ZIP_CODE in", values, "zipCode");
            return (Criteria) this;
        }

        public Criteria andZipCodeNotIn(List<String> values) {
            addCriterion("ZIP_CODE not in", values, "zipCode");
            return (Criteria) this;
        }

        public Criteria andZipCodeBetween(String value1, String value2) {
            addCriterion("ZIP_CODE between", value1, value2, "zipCode");
            return (Criteria) this;
        }

        public Criteria andZipCodeNotBetween(String value1, String value2) {
            addCriterion("ZIP_CODE not between", value1, value2, "zipCode");
            return (Criteria) this;
        }

        public Criteria andProNumIsNull() {
            addCriterion("PRO_NUM is null");
            return (Criteria) this;
        }

        public Criteria andProNumIsNotNull() {
            addCriterion("PRO_NUM is not null");
            return (Criteria) this;
        }

        public Criteria andProNumEqualTo(BigDecimal value) {
            addCriterion("PRO_NUM =", value, "proNum");
            return (Criteria) this;
        }

        public Criteria andProNumNotEqualTo(BigDecimal value) {
            addCriterion("PRO_NUM <>", value, "proNum");
            return (Criteria) this;
        }

        public Criteria andProNumGreaterThan(BigDecimal value) {
            addCriterion("PRO_NUM >", value, "proNum");
            return (Criteria) this;
        }

        public Criteria andProNumGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("PRO_NUM >=", value, "proNum");
            return (Criteria) this;
        }

        public Criteria andProNumLessThan(BigDecimal value) {
            addCriterion("PRO_NUM <", value, "proNum");
            return (Criteria) this;
        }

        public Criteria andProNumLessThanOrEqualTo(BigDecimal value) {
            addCriterion("PRO_NUM <=", value, "proNum");
            return (Criteria) this;
        }

        public Criteria andProNumIn(List<BigDecimal> values) {
            addCriterion("PRO_NUM in", values, "proNum");
            return (Criteria) this;
        }

        public Criteria andProNumNotIn(List<BigDecimal> values) {
            addCriterion("PRO_NUM not in", values, "proNum");
            return (Criteria) this;
        }

        public Criteria andProNumBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("PRO_NUM between", value1, value2, "proNum");
            return (Criteria) this;
        }

        public Criteria andProNumNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("PRO_NUM not between", value1, value2, "proNum");
            return (Criteria) this;
        }

        public Criteria andExpressRemarkIsNull() {
            addCriterion("EXPRESS_REMARK is null");
            return (Criteria) this;
        }

        public Criteria andExpressRemarkIsNotNull() {
            addCriterion("EXPRESS_REMARK is not null");
            return (Criteria) this;
        }

        public Criteria andExpressRemarkEqualTo(String value) {
            addCriterion("EXPRESS_REMARK =", value, "expressRemark");
            return (Criteria) this;
        }

        public Criteria andExpressRemarkNotEqualTo(String value) {
            addCriterion("EXPRESS_REMARK <>", value, "expressRemark");
            return (Criteria) this;
        }

        public Criteria andExpressRemarkGreaterThan(String value) {
            addCriterion("EXPRESS_REMARK >", value, "expressRemark");
            return (Criteria) this;
        }

        public Criteria andExpressRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("EXPRESS_REMARK >=", value, "expressRemark");
            return (Criteria) this;
        }

        public Criteria andExpressRemarkLessThan(String value) {
            addCriterion("EXPRESS_REMARK <", value, "expressRemark");
            return (Criteria) this;
        }

        public Criteria andExpressRemarkLessThanOrEqualTo(String value) {
            addCriterion("EXPRESS_REMARK <=", value, "expressRemark");
            return (Criteria) this;
        }

        public Criteria andExpressRemarkLike(String value) {
            addCriterion("EXPRESS_REMARK like", value, "expressRemark");
            return (Criteria) this;
        }

        public Criteria andExpressRemarkNotLike(String value) {
            addCriterion("EXPRESS_REMARK not like", value, "expressRemark");
            return (Criteria) this;
        }

        public Criteria andExpressRemarkIn(List<String> values) {
            addCriterion("EXPRESS_REMARK in", values, "expressRemark");
            return (Criteria) this;
        }

        public Criteria andExpressRemarkNotIn(List<String> values) {
            addCriterion("EXPRESS_REMARK not in", values, "expressRemark");
            return (Criteria) this;
        }

        public Criteria andExpressRemarkBetween(String value1, String value2) {
            addCriterion("EXPRESS_REMARK between", value1, value2, "expressRemark");
            return (Criteria) this;
        }

        public Criteria andExpressRemarkNotBetween(String value1, String value2) {
            addCriterion("EXPRESS_REMARK not between", value1, value2, "expressRemark");
            return (Criteria) this;
        }

        public Criteria andExpressSucDateIsNull() {
            addCriterion("EXPRESS_SUC_DATE is null");
            return (Criteria) this;
        }

        public Criteria andExpressSucDateIsNotNull() {
            addCriterion("EXPRESS_SUC_DATE is not null");
            return (Criteria) this;
        }

        public Criteria andExpressSucDateEqualTo(Date value) {
            addCriterion("EXPRESS_SUC_DATE =", value, "expressSucDate");
            return (Criteria) this;
        }

        public Criteria andExpressSucDateNotEqualTo(Date value) {
            addCriterion("EXPRESS_SUC_DATE <>", value, "expressSucDate");
            return (Criteria) this;
        }

        public Criteria andExpressSucDateGreaterThan(Date value) {
            addCriterion("EXPRESS_SUC_DATE >", value, "expressSucDate");
            return (Criteria) this;
        }

        public Criteria andExpressSucDateGreaterThanOrEqualTo(Date value) {
            addCriterion("EXPRESS_SUC_DATE >=", value, "expressSucDate");
            return (Criteria) this;
        }

        public Criteria andExpressSucDateLessThan(Date value) {
            addCriterion("EXPRESS_SUC_DATE <", value, "expressSucDate");
            return (Criteria) this;
        }

        public Criteria andExpressSucDateLessThanOrEqualTo(Date value) {
            addCriterion("EXPRESS_SUC_DATE <=", value, "expressSucDate");
            return (Criteria) this;
        }

        public Criteria andExpressSucDateIn(List<Date> values) {
            addCriterion("EXPRESS_SUC_DATE in", values, "expressSucDate");
            return (Criteria) this;
        }

        public Criteria andExpressSucDateNotIn(List<Date> values) {
            addCriterion("EXPRESS_SUC_DATE not in", values, "expressSucDate");
            return (Criteria) this;
        }

        public Criteria andExpressSucDateBetween(Date value1, Date value2) {
            addCriterion("EXPRESS_SUC_DATE between", value1, value2, "expressSucDate");
            return (Criteria) this;
        }

        public Criteria andExpressSucDateNotBetween(Date value1, Date value2) {
            addCriterion("EXPRESS_SUC_DATE not between", value1, value2, "expressSucDate");
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

        public Criteria andReceiptStatusIsNull() {
            addCriterion("RECEIPT_STATUS is null");
            return (Criteria) this;
        }

        public Criteria andReceiptStatusIsNotNull() {
            addCriterion("RECEIPT_STATUS is not null");
            return (Criteria) this;
        }

        public Criteria andReceiptStatusEqualTo(BigDecimal value) {
            addCriterion("RECEIPT_STATUS =", value, "receiptStatus");
            return (Criteria) this;
        }

        public Criteria andReceiptStatusNotEqualTo(BigDecimal value) {
            addCriterion("RECEIPT_STATUS <>", value, "receiptStatus");
            return (Criteria) this;
        }

        public Criteria andReceiptStatusGreaterThan(BigDecimal value) {
            addCriterion("RECEIPT_STATUS >", value, "receiptStatus");
            return (Criteria) this;
        }

        public Criteria andReceiptStatusGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("RECEIPT_STATUS >=", value, "receiptStatus");
            return (Criteria) this;
        }

        public Criteria andReceiptStatusLessThan(BigDecimal value) {
            addCriterion("RECEIPT_STATUS <", value, "receiptStatus");
            return (Criteria) this;
        }

        public Criteria andReceiptStatusLessThanOrEqualTo(BigDecimal value) {
            addCriterion("RECEIPT_STATUS <=", value, "receiptStatus");
            return (Criteria) this;
        }

        public Criteria andReceiptStatusIn(List<BigDecimal> values) {
            addCriterion("RECEIPT_STATUS in", values, "receiptStatus");
            return (Criteria) this;
        }

        public Criteria andReceiptStatusNotIn(List<BigDecimal> values) {
            addCriterion("RECEIPT_STATUS not in", values, "receiptStatus");
            return (Criteria) this;
        }

        public Criteria andReceiptStatusBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("RECEIPT_STATUS between", value1, value2, "receiptStatus");
            return (Criteria) this;
        }

        public Criteria andReceiptStatusNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("RECEIPT_STATUS not between", value1, value2, "receiptStatus");
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