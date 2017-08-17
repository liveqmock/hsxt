package com.gy.hsi.fs.mapper.vo.dbbs01;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.gy.hsi.fs.mapper.vo.AbstractPage;

public class TBsToolProductExample extends AbstractPage {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TBsToolProductExample() {
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

        public Criteria andProductIdIsNull() {
            addCriterion("PRODUCT_ID is null");
            return (Criteria) this;
        }

        public Criteria andProductIdIsNotNull() {
            addCriterion("PRODUCT_ID is not null");
            return (Criteria) this;
        }

        public Criteria andProductIdEqualTo(String value) {
            addCriterion("PRODUCT_ID =", value, "productId");
            return (Criteria) this;
        }

        public Criteria andProductIdNotEqualTo(String value) {
            addCriterion("PRODUCT_ID <>", value, "productId");
            return (Criteria) this;
        }

        public Criteria andProductIdGreaterThan(String value) {
            addCriterion("PRODUCT_ID >", value, "productId");
            return (Criteria) this;
        }

        public Criteria andProductIdGreaterThanOrEqualTo(String value) {
            addCriterion("PRODUCT_ID >=", value, "productId");
            return (Criteria) this;
        }

        public Criteria andProductIdLessThan(String value) {
            addCriterion("PRODUCT_ID <", value, "productId");
            return (Criteria) this;
        }

        public Criteria andProductIdLessThanOrEqualTo(String value) {
            addCriterion("PRODUCT_ID <=", value, "productId");
            return (Criteria) this;
        }

        public Criteria andProductIdLike(String value) {
            addCriterion("PRODUCT_ID like", value, "productId");
            return (Criteria) this;
        }

        public Criteria andProductIdNotLike(String value) {
            addCriterion("PRODUCT_ID not like", value, "productId");
            return (Criteria) this;
        }

        public Criteria andProductIdIn(List<String> values) {
            addCriterion("PRODUCT_ID in", values, "productId");
            return (Criteria) this;
        }

        public Criteria andProductIdNotIn(List<String> values) {
            addCriterion("PRODUCT_ID not in", values, "productId");
            return (Criteria) this;
        }

        public Criteria andProductIdBetween(String value1, String value2) {
            addCriterion("PRODUCT_ID between", value1, value2, "productId");
            return (Criteria) this;
        }

        public Criteria andProductIdNotBetween(String value1, String value2) {
            addCriterion("PRODUCT_ID not between", value1, value2, "productId");
            return (Criteria) this;
        }

        public Criteria andCategoryCodeIsNull() {
            addCriterion("CATEGORY_CODE is null");
            return (Criteria) this;
        }

        public Criteria andCategoryCodeIsNotNull() {
            addCriterion("CATEGORY_CODE is not null");
            return (Criteria) this;
        }

        public Criteria andCategoryCodeEqualTo(String value) {
            addCriterion("CATEGORY_CODE =", value, "categoryCode");
            return (Criteria) this;
        }

        public Criteria andCategoryCodeNotEqualTo(String value) {
            addCriterion("CATEGORY_CODE <>", value, "categoryCode");
            return (Criteria) this;
        }

        public Criteria andCategoryCodeGreaterThan(String value) {
            addCriterion("CATEGORY_CODE >", value, "categoryCode");
            return (Criteria) this;
        }

        public Criteria andCategoryCodeGreaterThanOrEqualTo(String value) {
            addCriterion("CATEGORY_CODE >=", value, "categoryCode");
            return (Criteria) this;
        }

        public Criteria andCategoryCodeLessThan(String value) {
            addCriterion("CATEGORY_CODE <", value, "categoryCode");
            return (Criteria) this;
        }

        public Criteria andCategoryCodeLessThanOrEqualTo(String value) {
            addCriterion("CATEGORY_CODE <=", value, "categoryCode");
            return (Criteria) this;
        }

        public Criteria andCategoryCodeLike(String value) {
            addCriterion("CATEGORY_CODE like", value, "categoryCode");
            return (Criteria) this;
        }

        public Criteria andCategoryCodeNotLike(String value) {
            addCriterion("CATEGORY_CODE not like", value, "categoryCode");
            return (Criteria) this;
        }

        public Criteria andCategoryCodeIn(List<String> values) {
            addCriterion("CATEGORY_CODE in", values, "categoryCode");
            return (Criteria) this;
        }

        public Criteria andCategoryCodeNotIn(List<String> values) {
            addCriterion("CATEGORY_CODE not in", values, "categoryCode");
            return (Criteria) this;
        }

        public Criteria andCategoryCodeBetween(String value1, String value2) {
            addCriterion("CATEGORY_CODE between", value1, value2, "categoryCode");
            return (Criteria) this;
        }

        public Criteria andCategoryCodeNotBetween(String value1, String value2) {
            addCriterion("CATEGORY_CODE not between", value1, value2, "categoryCode");
            return (Criteria) this;
        }

        public Criteria andProductNameIsNull() {
            addCriterion("PRODUCT_NAME is null");
            return (Criteria) this;
        }

        public Criteria andProductNameIsNotNull() {
            addCriterion("PRODUCT_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andProductNameEqualTo(String value) {
            addCriterion("PRODUCT_NAME =", value, "productName");
            return (Criteria) this;
        }

        public Criteria andProductNameNotEqualTo(String value) {
            addCriterion("PRODUCT_NAME <>", value, "productName");
            return (Criteria) this;
        }

        public Criteria andProductNameGreaterThan(String value) {
            addCriterion("PRODUCT_NAME >", value, "productName");
            return (Criteria) this;
        }

        public Criteria andProductNameGreaterThanOrEqualTo(String value) {
            addCriterion("PRODUCT_NAME >=", value, "productName");
            return (Criteria) this;
        }

        public Criteria andProductNameLessThan(String value) {
            addCriterion("PRODUCT_NAME <", value, "productName");
            return (Criteria) this;
        }

        public Criteria andProductNameLessThanOrEqualTo(String value) {
            addCriterion("PRODUCT_NAME <=", value, "productName");
            return (Criteria) this;
        }

        public Criteria andProductNameLike(String value) {
            addCriterion("PRODUCT_NAME like", value, "productName");
            return (Criteria) this;
        }

        public Criteria andProductNameNotLike(String value) {
            addCriterion("PRODUCT_NAME not like", value, "productName");
            return (Criteria) this;
        }

        public Criteria andProductNameIn(List<String> values) {
            addCriterion("PRODUCT_NAME in", values, "productName");
            return (Criteria) this;
        }

        public Criteria andProductNameNotIn(List<String> values) {
            addCriterion("PRODUCT_NAME not in", values, "productName");
            return (Criteria) this;
        }

        public Criteria andProductNameBetween(String value1, String value2) {
            addCriterion("PRODUCT_NAME between", value1, value2, "productName");
            return (Criteria) this;
        }

        public Criteria andProductNameNotBetween(String value1, String value2) {
            addCriterion("PRODUCT_NAME not between", value1, value2, "productName");
            return (Criteria) this;
        }

        public Criteria andMicroPicIsNull() {
            addCriterion("MICRO_PIC is null");
            return (Criteria) this;
        }

        public Criteria andMicroPicIsNotNull() {
            addCriterion("MICRO_PIC is not null");
            return (Criteria) this;
        }

        public Criteria andMicroPicEqualTo(String value) {
            addCriterion("MICRO_PIC =", value, "microPic");
            return (Criteria) this;
        }

        public Criteria andMicroPicNotEqualTo(String value) {
            addCriterion("MICRO_PIC <>", value, "microPic");
            return (Criteria) this;
        }

        public Criteria andMicroPicGreaterThan(String value) {
            addCriterion("MICRO_PIC >", value, "microPic");
            return (Criteria) this;
        }

        public Criteria andMicroPicGreaterThanOrEqualTo(String value) {
            addCriterion("MICRO_PIC >=", value, "microPic");
            return (Criteria) this;
        }

        public Criteria andMicroPicLessThan(String value) {
            addCriterion("MICRO_PIC <", value, "microPic");
            return (Criteria) this;
        }

        public Criteria andMicroPicLessThanOrEqualTo(String value) {
            addCriterion("MICRO_PIC <=", value, "microPic");
            return (Criteria) this;
        }

        public Criteria andMicroPicLike(String value) {
            addCriterion("MICRO_PIC like", value, "microPic");
            return (Criteria) this;
        }

        public Criteria andMicroPicNotLike(String value) {
            addCriterion("MICRO_PIC not like", value, "microPic");
            return (Criteria) this;
        }

        public Criteria andMicroPicIn(List<String> values) {
            addCriterion("MICRO_PIC in", values, "microPic");
            return (Criteria) this;
        }

        public Criteria andMicroPicNotIn(List<String> values) {
            addCriterion("MICRO_PIC not in", values, "microPic");
            return (Criteria) this;
        }

        public Criteria andMicroPicBetween(String value1, String value2) {
            addCriterion("MICRO_PIC between", value1, value2, "microPic");
            return (Criteria) this;
        }

        public Criteria andMicroPicNotBetween(String value1, String value2) {
            addCriterion("MICRO_PIC not between", value1, value2, "microPic");
            return (Criteria) this;
        }

        public Criteria andPriceIsNull() {
            addCriterion("PRICE is null");
            return (Criteria) this;
        }

        public Criteria andPriceIsNotNull() {
            addCriterion("PRICE is not null");
            return (Criteria) this;
        }

        public Criteria andPriceEqualTo(BigDecimal value) {
            addCriterion("PRICE =", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceNotEqualTo(BigDecimal value) {
            addCriterion("PRICE <>", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceGreaterThan(BigDecimal value) {
            addCriterion("PRICE >", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("PRICE >=", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceLessThan(BigDecimal value) {
            addCriterion("PRICE <", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("PRICE <=", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceIn(List<BigDecimal> values) {
            addCriterion("PRICE in", values, "price");
            return (Criteria) this;
        }

        public Criteria andPriceNotIn(List<BigDecimal> values) {
            addCriterion("PRICE not in", values, "price");
            return (Criteria) this;
        }

        public Criteria andPriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("PRICE between", value1, value2, "price");
            return (Criteria) this;
        }

        public Criteria andPriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("PRICE not between", value1, value2, "price");
            return (Criteria) this;
        }

        public Criteria andUnitIsNull() {
            addCriterion("UNIT is null");
            return (Criteria) this;
        }

        public Criteria andUnitIsNotNull() {
            addCriterion("UNIT is not null");
            return (Criteria) this;
        }

        public Criteria andUnitEqualTo(String value) {
            addCriterion("UNIT =", value, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitNotEqualTo(String value) {
            addCriterion("UNIT <>", value, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitGreaterThan(String value) {
            addCriterion("UNIT >", value, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitGreaterThanOrEqualTo(String value) {
            addCriterion("UNIT >=", value, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitLessThan(String value) {
            addCriterion("UNIT <", value, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitLessThanOrEqualTo(String value) {
            addCriterion("UNIT <=", value, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitLike(String value) {
            addCriterion("UNIT like", value, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitNotLike(String value) {
            addCriterion("UNIT not like", value, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitIn(List<String> values) {
            addCriterion("UNIT in", values, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitNotIn(List<String> values) {
            addCriterion("UNIT not in", values, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitBetween(String value1, String value2) {
            addCriterion("UNIT between", value1, value2, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitNotBetween(String value1, String value2) {
            addCriterion("UNIT not between", value1, value2, "unit");
            return (Criteria) this;
        }

        public Criteria andDescriptionIsNull() {
            addCriterion("DESCRIPTION is null");
            return (Criteria) this;
        }

        public Criteria andDescriptionIsNotNull() {
            addCriterion("DESCRIPTION is not null");
            return (Criteria) this;
        }

        public Criteria andDescriptionEqualTo(String value) {
            addCriterion("DESCRIPTION =", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotEqualTo(String value) {
            addCriterion("DESCRIPTION <>", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionGreaterThan(String value) {
            addCriterion("DESCRIPTION >", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionGreaterThanOrEqualTo(String value) {
            addCriterion("DESCRIPTION >=", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLessThan(String value) {
            addCriterion("DESCRIPTION <", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLessThanOrEqualTo(String value) {
            addCriterion("DESCRIPTION <=", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLike(String value) {
            addCriterion("DESCRIPTION like", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotLike(String value) {
            addCriterion("DESCRIPTION not like", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionIn(List<String> values) {
            addCriterion("DESCRIPTION in", values, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotIn(List<String> values) {
            addCriterion("DESCRIPTION not in", values, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionBetween(String value1, String value2) {
            addCriterion("DESCRIPTION between", value1, value2, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotBetween(String value1, String value2) {
            addCriterion("DESCRIPTION not between", value1, value2, "description");
            return (Criteria) this;
        }

        public Criteria andWarningValueIsNull() {
            addCriterion("WARNING_VALUE is null");
            return (Criteria) this;
        }

        public Criteria andWarningValueIsNotNull() {
            addCriterion("WARNING_VALUE is not null");
            return (Criteria) this;
        }

        public Criteria andWarningValueEqualTo(Integer value) {
            addCriterion("WARNING_VALUE =", value, "warningValue");
            return (Criteria) this;
        }

        public Criteria andWarningValueNotEqualTo(Integer value) {
            addCriterion("WARNING_VALUE <>", value, "warningValue");
            return (Criteria) this;
        }

        public Criteria andWarningValueGreaterThan(Integer value) {
            addCriterion("WARNING_VALUE >", value, "warningValue");
            return (Criteria) this;
        }

        public Criteria andWarningValueGreaterThanOrEqualTo(Integer value) {
            addCriterion("WARNING_VALUE >=", value, "warningValue");
            return (Criteria) this;
        }

        public Criteria andWarningValueLessThan(Integer value) {
            addCriterion("WARNING_VALUE <", value, "warningValue");
            return (Criteria) this;
        }

        public Criteria andWarningValueLessThanOrEqualTo(Integer value) {
            addCriterion("WARNING_VALUE <=", value, "warningValue");
            return (Criteria) this;
        }

        public Criteria andWarningValueIn(List<Integer> values) {
            addCriterion("WARNING_VALUE in", values, "warningValue");
            return (Criteria) this;
        }

        public Criteria andWarningValueNotIn(List<Integer> values) {
            addCriterion("WARNING_VALUE not in", values, "warningValue");
            return (Criteria) this;
        }

        public Criteria andWarningValueBetween(Integer value1, Integer value2) {
            addCriterion("WARNING_VALUE between", value1, value2, "warningValue");
            return (Criteria) this;
        }

        public Criteria andWarningValueNotBetween(Integer value1, Integer value2) {
            addCriterion("WARNING_VALUE not between", value1, value2, "warningValue");
            return (Criteria) this;
        }

        public Criteria andEnableStatusIsNull() {
            addCriterion("ENABLE_STATUS is null");
            return (Criteria) this;
        }

        public Criteria andEnableStatusIsNotNull() {
            addCriterion("ENABLE_STATUS is not null");
            return (Criteria) this;
        }

        public Criteria andEnableStatusEqualTo(Short value) {
            addCriterion("ENABLE_STATUS =", value, "enableStatus");
            return (Criteria) this;
        }

        public Criteria andEnableStatusNotEqualTo(Short value) {
            addCriterion("ENABLE_STATUS <>", value, "enableStatus");
            return (Criteria) this;
        }

        public Criteria andEnableStatusGreaterThan(Short value) {
            addCriterion("ENABLE_STATUS >", value, "enableStatus");
            return (Criteria) this;
        }

        public Criteria andEnableStatusGreaterThanOrEqualTo(Short value) {
            addCriterion("ENABLE_STATUS >=", value, "enableStatus");
            return (Criteria) this;
        }

        public Criteria andEnableStatusLessThan(Short value) {
            addCriterion("ENABLE_STATUS <", value, "enableStatus");
            return (Criteria) this;
        }

        public Criteria andEnableStatusLessThanOrEqualTo(Short value) {
            addCriterion("ENABLE_STATUS <=", value, "enableStatus");
            return (Criteria) this;
        }

        public Criteria andEnableStatusIn(List<Short> values) {
            addCriterion("ENABLE_STATUS in", values, "enableStatus");
            return (Criteria) this;
        }

        public Criteria andEnableStatusNotIn(List<Short> values) {
            addCriterion("ENABLE_STATUS not in", values, "enableStatus");
            return (Criteria) this;
        }

        public Criteria andEnableStatusBetween(Short value1, Short value2) {
            addCriterion("ENABLE_STATUS between", value1, value2, "enableStatus");
            return (Criteria) this;
        }

        public Criteria andEnableStatusNotBetween(Short value1, Short value2) {
            addCriterion("ENABLE_STATUS not between", value1, value2, "enableStatus");
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

        public Criteria andStatusEqualTo(Short value) {
            addCriterion("STATUS =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(Short value) {
            addCriterion("STATUS <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(Short value) {
            addCriterion("STATUS >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(Short value) {
            addCriterion("STATUS >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(Short value) {
            addCriterion("STATUS <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(Short value) {
            addCriterion("STATUS <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<Short> values) {
            addCriterion("STATUS in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<Short> values) {
            addCriterion("STATUS not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(Short value1, Short value2) {
            addCriterion("STATUS between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(Short value1, Short value2) {
            addCriterion("STATUS not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andLastStatusTimeIsNull() {
            addCriterion("LAST_STATUS_TIME is null");
            return (Criteria) this;
        }

        public Criteria andLastStatusTimeIsNotNull() {
            addCriterion("LAST_STATUS_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andLastStatusTimeEqualTo(Date value) {
            addCriterion("LAST_STATUS_TIME =", value, "lastStatusTime");
            return (Criteria) this;
        }

        public Criteria andLastStatusTimeNotEqualTo(Date value) {
            addCriterion("LAST_STATUS_TIME <>", value, "lastStatusTime");
            return (Criteria) this;
        }

        public Criteria andLastStatusTimeGreaterThan(Date value) {
            addCriterion("LAST_STATUS_TIME >", value, "lastStatusTime");
            return (Criteria) this;
        }

        public Criteria andLastStatusTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("LAST_STATUS_TIME >=", value, "lastStatusTime");
            return (Criteria) this;
        }

        public Criteria andLastStatusTimeLessThan(Date value) {
            addCriterion("LAST_STATUS_TIME <", value, "lastStatusTime");
            return (Criteria) this;
        }

        public Criteria andLastStatusTimeLessThanOrEqualTo(Date value) {
            addCriterion("LAST_STATUS_TIME <=", value, "lastStatusTime");
            return (Criteria) this;
        }

        public Criteria andLastStatusTimeIn(List<Date> values) {
            addCriterion("LAST_STATUS_TIME in", values, "lastStatusTime");
            return (Criteria) this;
        }

        public Criteria andLastStatusTimeNotIn(List<Date> values) {
            addCriterion("LAST_STATUS_TIME not in", values, "lastStatusTime");
            return (Criteria) this;
        }

        public Criteria andLastStatusTimeBetween(Date value1, Date value2) {
            addCriterion("LAST_STATUS_TIME between", value1, value2, "lastStatusTime");
            return (Criteria) this;
        }

        public Criteria andLastStatusTimeNotBetween(Date value1, Date value2) {
            addCriterion("LAST_STATUS_TIME not between", value1, value2, "lastStatusTime");
            return (Criteria) this;
        }

        public Criteria andLastApplyIdIsNull() {
            addCriterion("LAST_APPLY_ID is null");
            return (Criteria) this;
        }

        public Criteria andLastApplyIdIsNotNull() {
            addCriterion("LAST_APPLY_ID is not null");
            return (Criteria) this;
        }

        public Criteria andLastApplyIdEqualTo(String value) {
            addCriterion("LAST_APPLY_ID =", value, "lastApplyId");
            return (Criteria) this;
        }

        public Criteria andLastApplyIdNotEqualTo(String value) {
            addCriterion("LAST_APPLY_ID <>", value, "lastApplyId");
            return (Criteria) this;
        }

        public Criteria andLastApplyIdGreaterThan(String value) {
            addCriterion("LAST_APPLY_ID >", value, "lastApplyId");
            return (Criteria) this;
        }

        public Criteria andLastApplyIdGreaterThanOrEqualTo(String value) {
            addCriterion("LAST_APPLY_ID >=", value, "lastApplyId");
            return (Criteria) this;
        }

        public Criteria andLastApplyIdLessThan(String value) {
            addCriterion("LAST_APPLY_ID <", value, "lastApplyId");
            return (Criteria) this;
        }

        public Criteria andLastApplyIdLessThanOrEqualTo(String value) {
            addCriterion("LAST_APPLY_ID <=", value, "lastApplyId");
            return (Criteria) this;
        }

        public Criteria andLastApplyIdLike(String value) {
            addCriterion("LAST_APPLY_ID like", value, "lastApplyId");
            return (Criteria) this;
        }

        public Criteria andLastApplyIdNotLike(String value) {
            addCriterion("LAST_APPLY_ID not like", value, "lastApplyId");
            return (Criteria) this;
        }

        public Criteria andLastApplyIdIn(List<String> values) {
            addCriterion("LAST_APPLY_ID in", values, "lastApplyId");
            return (Criteria) this;
        }

        public Criteria andLastApplyIdNotIn(List<String> values) {
            addCriterion("LAST_APPLY_ID not in", values, "lastApplyId");
            return (Criteria) this;
        }

        public Criteria andLastApplyIdBetween(String value1, String value2) {
            addCriterion("LAST_APPLY_ID between", value1, value2, "lastApplyId");
            return (Criteria) this;
        }

        public Criteria andLastApplyIdNotBetween(String value1, String value2) {
            addCriterion("LAST_APPLY_ID not between", value1, value2, "lastApplyId");
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