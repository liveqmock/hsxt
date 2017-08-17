package com.gy.hsi.fs.mapper.vo.dbbs01;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.gy.hsi.fs.mapper.vo.AbstractPage;

public class TBsTaxrateChangeExample extends AbstractPage {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TBsTaxrateChangeExample() {
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

        public Criteria andApplyIdIsNull() {
            addCriterion("APPLY_ID is null");
            return (Criteria) this;
        }

        public Criteria andApplyIdIsNotNull() {
            addCriterion("APPLY_ID is not null");
            return (Criteria) this;
        }

        public Criteria andApplyIdEqualTo(String value) {
            addCriterion("APPLY_ID =", value, "applyId");
            return (Criteria) this;
        }

        public Criteria andApplyIdNotEqualTo(String value) {
            addCriterion("APPLY_ID <>", value, "applyId");
            return (Criteria) this;
        }

        public Criteria andApplyIdGreaterThan(String value) {
            addCriterion("APPLY_ID >", value, "applyId");
            return (Criteria) this;
        }

        public Criteria andApplyIdGreaterThanOrEqualTo(String value) {
            addCriterion("APPLY_ID >=", value, "applyId");
            return (Criteria) this;
        }

        public Criteria andApplyIdLessThan(String value) {
            addCriterion("APPLY_ID <", value, "applyId");
            return (Criteria) this;
        }

        public Criteria andApplyIdLessThanOrEqualTo(String value) {
            addCriterion("APPLY_ID <=", value, "applyId");
            return (Criteria) this;
        }

        public Criteria andApplyIdLike(String value) {
            addCriterion("APPLY_ID like", value, "applyId");
            return (Criteria) this;
        }

        public Criteria andApplyIdNotLike(String value) {
            addCriterion("APPLY_ID not like", value, "applyId");
            return (Criteria) this;
        }

        public Criteria andApplyIdIn(List<String> values) {
            addCriterion("APPLY_ID in", values, "applyId");
            return (Criteria) this;
        }

        public Criteria andApplyIdNotIn(List<String> values) {
            addCriterion("APPLY_ID not in", values, "applyId");
            return (Criteria) this;
        }

        public Criteria andApplyIdBetween(String value1, String value2) {
            addCriterion("APPLY_ID between", value1, value2, "applyId");
            return (Criteria) this;
        }

        public Criteria andApplyIdNotBetween(String value1, String value2) {
            addCriterion("APPLY_ID not between", value1, value2, "applyId");
            return (Criteria) this;
        }

        public Criteria andResNoIsNull() {
            addCriterion("RES_NO is null");
            return (Criteria) this;
        }

        public Criteria andResNoIsNotNull() {
            addCriterion("RES_NO is not null");
            return (Criteria) this;
        }

        public Criteria andResNoEqualTo(String value) {
            addCriterion("RES_NO =", value, "resNo");
            return (Criteria) this;
        }

        public Criteria andResNoNotEqualTo(String value) {
            addCriterion("RES_NO <>", value, "resNo");
            return (Criteria) this;
        }

        public Criteria andResNoGreaterThan(String value) {
            addCriterion("RES_NO >", value, "resNo");
            return (Criteria) this;
        }

        public Criteria andResNoGreaterThanOrEqualTo(String value) {
            addCriterion("RES_NO >=", value, "resNo");
            return (Criteria) this;
        }

        public Criteria andResNoLessThan(String value) {
            addCriterion("RES_NO <", value, "resNo");
            return (Criteria) this;
        }

        public Criteria andResNoLessThanOrEqualTo(String value) {
            addCriterion("RES_NO <=", value, "resNo");
            return (Criteria) this;
        }

        public Criteria andResNoLike(String value) {
            addCriterion("RES_NO like", value, "resNo");
            return (Criteria) this;
        }

        public Criteria andResNoNotLike(String value) {
            addCriterion("RES_NO not like", value, "resNo");
            return (Criteria) this;
        }

        public Criteria andResNoIn(List<String> values) {
            addCriterion("RES_NO in", values, "resNo");
            return (Criteria) this;
        }

        public Criteria andResNoNotIn(List<String> values) {
            addCriterion("RES_NO not in", values, "resNo");
            return (Criteria) this;
        }

        public Criteria andResNoBetween(String value1, String value2) {
            addCriterion("RES_NO between", value1, value2, "resNo");
            return (Criteria) this;
        }

        public Criteria andResNoNotBetween(String value1, String value2) {
            addCriterion("RES_NO not between", value1, value2, "resNo");
            return (Criteria) this;
        }

        public Criteria andCustIdIsNull() {
            addCriterion("CUST_ID is null");
            return (Criteria) this;
        }

        public Criteria andCustIdIsNotNull() {
            addCriterion("CUST_ID is not null");
            return (Criteria) this;
        }

        public Criteria andCustIdEqualTo(String value) {
            addCriterion("CUST_ID =", value, "custId");
            return (Criteria) this;
        }

        public Criteria andCustIdNotEqualTo(String value) {
            addCriterion("CUST_ID <>", value, "custId");
            return (Criteria) this;
        }

        public Criteria andCustIdGreaterThan(String value) {
            addCriterion("CUST_ID >", value, "custId");
            return (Criteria) this;
        }

        public Criteria andCustIdGreaterThanOrEqualTo(String value) {
            addCriterion("CUST_ID >=", value, "custId");
            return (Criteria) this;
        }

        public Criteria andCustIdLessThan(String value) {
            addCriterion("CUST_ID <", value, "custId");
            return (Criteria) this;
        }

        public Criteria andCustIdLessThanOrEqualTo(String value) {
            addCriterion("CUST_ID <=", value, "custId");
            return (Criteria) this;
        }

        public Criteria andCustIdLike(String value) {
            addCriterion("CUST_ID like", value, "custId");
            return (Criteria) this;
        }

        public Criteria andCustIdNotLike(String value) {
            addCriterion("CUST_ID not like", value, "custId");
            return (Criteria) this;
        }

        public Criteria andCustIdIn(List<String> values) {
            addCriterion("CUST_ID in", values, "custId");
            return (Criteria) this;
        }

        public Criteria andCustIdNotIn(List<String> values) {
            addCriterion("CUST_ID not in", values, "custId");
            return (Criteria) this;
        }

        public Criteria andCustIdBetween(String value1, String value2) {
            addCriterion("CUST_ID between", value1, value2, "custId");
            return (Criteria) this;
        }

        public Criteria andCustIdNotBetween(String value1, String value2) {
            addCriterion("CUST_ID not between", value1, value2, "custId");
            return (Criteria) this;
        }

        public Criteria andCustNameIsNull() {
            addCriterion("CUST_NAME is null");
            return (Criteria) this;
        }

        public Criteria andCustNameIsNotNull() {
            addCriterion("CUST_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andCustNameEqualTo(String value) {
            addCriterion("CUST_NAME =", value, "custName");
            return (Criteria) this;
        }

        public Criteria andCustNameNotEqualTo(String value) {
            addCriterion("CUST_NAME <>", value, "custName");
            return (Criteria) this;
        }

        public Criteria andCustNameGreaterThan(String value) {
            addCriterion("CUST_NAME >", value, "custName");
            return (Criteria) this;
        }

        public Criteria andCustNameGreaterThanOrEqualTo(String value) {
            addCriterion("CUST_NAME >=", value, "custName");
            return (Criteria) this;
        }

        public Criteria andCustNameLessThan(String value) {
            addCriterion("CUST_NAME <", value, "custName");
            return (Criteria) this;
        }

        public Criteria andCustNameLessThanOrEqualTo(String value) {
            addCriterion("CUST_NAME <=", value, "custName");
            return (Criteria) this;
        }

        public Criteria andCustNameLike(String value) {
            addCriterion("CUST_NAME like", value, "custName");
            return (Criteria) this;
        }

        public Criteria andCustNameNotLike(String value) {
            addCriterion("CUST_NAME not like", value, "custName");
            return (Criteria) this;
        }

        public Criteria andCustNameIn(List<String> values) {
            addCriterion("CUST_NAME in", values, "custName");
            return (Criteria) this;
        }

        public Criteria andCustNameNotIn(List<String> values) {
            addCriterion("CUST_NAME not in", values, "custName");
            return (Criteria) this;
        }

        public Criteria andCustNameBetween(String value1, String value2) {
            addCriterion("CUST_NAME between", value1, value2, "custName");
            return (Criteria) this;
        }

        public Criteria andCustNameNotBetween(String value1, String value2) {
            addCriterion("CUST_NAME not between", value1, value2, "custName");
            return (Criteria) this;
        }

        public Criteria andLinkmanIsNull() {
            addCriterion("LINKMAN is null");
            return (Criteria) this;
        }

        public Criteria andLinkmanIsNotNull() {
            addCriterion("LINKMAN is not null");
            return (Criteria) this;
        }

        public Criteria andLinkmanEqualTo(String value) {
            addCriterion("LINKMAN =", value, "linkman");
            return (Criteria) this;
        }

        public Criteria andLinkmanNotEqualTo(String value) {
            addCriterion("LINKMAN <>", value, "linkman");
            return (Criteria) this;
        }

        public Criteria andLinkmanGreaterThan(String value) {
            addCriterion("LINKMAN >", value, "linkman");
            return (Criteria) this;
        }

        public Criteria andLinkmanGreaterThanOrEqualTo(String value) {
            addCriterion("LINKMAN >=", value, "linkman");
            return (Criteria) this;
        }

        public Criteria andLinkmanLessThan(String value) {
            addCriterion("LINKMAN <", value, "linkman");
            return (Criteria) this;
        }

        public Criteria andLinkmanLessThanOrEqualTo(String value) {
            addCriterion("LINKMAN <=", value, "linkman");
            return (Criteria) this;
        }

        public Criteria andLinkmanLike(String value) {
            addCriterion("LINKMAN like", value, "linkman");
            return (Criteria) this;
        }

        public Criteria andLinkmanNotLike(String value) {
            addCriterion("LINKMAN not like", value, "linkman");
            return (Criteria) this;
        }

        public Criteria andLinkmanIn(List<String> values) {
            addCriterion("LINKMAN in", values, "linkman");
            return (Criteria) this;
        }

        public Criteria andLinkmanNotIn(List<String> values) {
            addCriterion("LINKMAN not in", values, "linkman");
            return (Criteria) this;
        }

        public Criteria andLinkmanBetween(String value1, String value2) {
            addCriterion("LINKMAN between", value1, value2, "linkman");
            return (Criteria) this;
        }

        public Criteria andLinkmanNotBetween(String value1, String value2) {
            addCriterion("LINKMAN not between", value1, value2, "linkman");
            return (Criteria) this;
        }

        public Criteria andTelephoneIsNull() {
            addCriterion("TELEPHONE is null");
            return (Criteria) this;
        }

        public Criteria andTelephoneIsNotNull() {
            addCriterion("TELEPHONE is not null");
            return (Criteria) this;
        }

        public Criteria andTelephoneEqualTo(String value) {
            addCriterion("TELEPHONE =", value, "telephone");
            return (Criteria) this;
        }

        public Criteria andTelephoneNotEqualTo(String value) {
            addCriterion("TELEPHONE <>", value, "telephone");
            return (Criteria) this;
        }

        public Criteria andTelephoneGreaterThan(String value) {
            addCriterion("TELEPHONE >", value, "telephone");
            return (Criteria) this;
        }

        public Criteria andTelephoneGreaterThanOrEqualTo(String value) {
            addCriterion("TELEPHONE >=", value, "telephone");
            return (Criteria) this;
        }

        public Criteria andTelephoneLessThan(String value) {
            addCriterion("TELEPHONE <", value, "telephone");
            return (Criteria) this;
        }

        public Criteria andTelephoneLessThanOrEqualTo(String value) {
            addCriterion("TELEPHONE <=", value, "telephone");
            return (Criteria) this;
        }

        public Criteria andTelephoneLike(String value) {
            addCriterion("TELEPHONE like", value, "telephone");
            return (Criteria) this;
        }

        public Criteria andTelephoneNotLike(String value) {
            addCriterion("TELEPHONE not like", value, "telephone");
            return (Criteria) this;
        }

        public Criteria andTelephoneIn(List<String> values) {
            addCriterion("TELEPHONE in", values, "telephone");
            return (Criteria) this;
        }

        public Criteria andTelephoneNotIn(List<String> values) {
            addCriterion("TELEPHONE not in", values, "telephone");
            return (Criteria) this;
        }

        public Criteria andTelephoneBetween(String value1, String value2) {
            addCriterion("TELEPHONE between", value1, value2, "telephone");
            return (Criteria) this;
        }

        public Criteria andTelephoneNotBetween(String value1, String value2) {
            addCriterion("TELEPHONE not between", value1, value2, "telephone");
            return (Criteria) this;
        }

        public Criteria andCustTypeIsNull() {
            addCriterion("CUST_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andCustTypeIsNotNull() {
            addCriterion("CUST_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andCustTypeEqualTo(Short value) {
            addCriterion("CUST_TYPE =", value, "custType");
            return (Criteria) this;
        }

        public Criteria andCustTypeNotEqualTo(Short value) {
            addCriterion("CUST_TYPE <>", value, "custType");
            return (Criteria) this;
        }

        public Criteria andCustTypeGreaterThan(Short value) {
            addCriterion("CUST_TYPE >", value, "custType");
            return (Criteria) this;
        }

        public Criteria andCustTypeGreaterThanOrEqualTo(Short value) {
            addCriterion("CUST_TYPE >=", value, "custType");
            return (Criteria) this;
        }

        public Criteria andCustTypeLessThan(Short value) {
            addCriterion("CUST_TYPE <", value, "custType");
            return (Criteria) this;
        }

        public Criteria andCustTypeLessThanOrEqualTo(Short value) {
            addCriterion("CUST_TYPE <=", value, "custType");
            return (Criteria) this;
        }

        public Criteria andCustTypeIn(List<Short> values) {
            addCriterion("CUST_TYPE in", values, "custType");
            return (Criteria) this;
        }

        public Criteria andCustTypeNotIn(List<Short> values) {
            addCriterion("CUST_TYPE not in", values, "custType");
            return (Criteria) this;
        }

        public Criteria andCustTypeBetween(Short value1, Short value2) {
            addCriterion("CUST_TYPE between", value1, value2, "custType");
            return (Criteria) this;
        }

        public Criteria andCustTypeNotBetween(Short value1, Short value2) {
            addCriterion("CUST_TYPE not between", value1, value2, "custType");
            return (Criteria) this;
        }

        public Criteria andTaxpayerTypeIsNull() {
            addCriterion("TAXPAYER_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andTaxpayerTypeIsNotNull() {
            addCriterion("TAXPAYER_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andTaxpayerTypeEqualTo(Short value) {
            addCriterion("TAXPAYER_TYPE =", value, "taxpayerType");
            return (Criteria) this;
        }

        public Criteria andTaxpayerTypeNotEqualTo(Short value) {
            addCriterion("TAXPAYER_TYPE <>", value, "taxpayerType");
            return (Criteria) this;
        }

        public Criteria andTaxpayerTypeGreaterThan(Short value) {
            addCriterion("TAXPAYER_TYPE >", value, "taxpayerType");
            return (Criteria) this;
        }

        public Criteria andTaxpayerTypeGreaterThanOrEqualTo(Short value) {
            addCriterion("TAXPAYER_TYPE >=", value, "taxpayerType");
            return (Criteria) this;
        }

        public Criteria andTaxpayerTypeLessThan(Short value) {
            addCriterion("TAXPAYER_TYPE <", value, "taxpayerType");
            return (Criteria) this;
        }

        public Criteria andTaxpayerTypeLessThanOrEqualTo(Short value) {
            addCriterion("TAXPAYER_TYPE <=", value, "taxpayerType");
            return (Criteria) this;
        }

        public Criteria andTaxpayerTypeIn(List<Short> values) {
            addCriterion("TAXPAYER_TYPE in", values, "taxpayerType");
            return (Criteria) this;
        }

        public Criteria andTaxpayerTypeNotIn(List<Short> values) {
            addCriterion("TAXPAYER_TYPE not in", values, "taxpayerType");
            return (Criteria) this;
        }

        public Criteria andTaxpayerTypeBetween(Short value1, Short value2) {
            addCriterion("TAXPAYER_TYPE between", value1, value2, "taxpayerType");
            return (Criteria) this;
        }

        public Criteria andTaxpayerTypeNotBetween(Short value1, Short value2) {
            addCriterion("TAXPAYER_TYPE not between", value1, value2, "taxpayerType");
            return (Criteria) this;
        }

        public Criteria andCurrTaxrateIsNull() {
            addCriterion("CURR_TAXRATE is null");
            return (Criteria) this;
        }

        public Criteria andCurrTaxrateIsNotNull() {
            addCriterion("CURR_TAXRATE is not null");
            return (Criteria) this;
        }

        public Criteria andCurrTaxrateEqualTo(BigDecimal value) {
            addCriterion("CURR_TAXRATE =", value, "currTaxrate");
            return (Criteria) this;
        }

        public Criteria andCurrTaxrateNotEqualTo(BigDecimal value) {
            addCriterion("CURR_TAXRATE <>", value, "currTaxrate");
            return (Criteria) this;
        }

        public Criteria andCurrTaxrateGreaterThan(BigDecimal value) {
            addCriterion("CURR_TAXRATE >", value, "currTaxrate");
            return (Criteria) this;
        }

        public Criteria andCurrTaxrateGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("CURR_TAXRATE >=", value, "currTaxrate");
            return (Criteria) this;
        }

        public Criteria andCurrTaxrateLessThan(BigDecimal value) {
            addCriterion("CURR_TAXRATE <", value, "currTaxrate");
            return (Criteria) this;
        }

        public Criteria andCurrTaxrateLessThanOrEqualTo(BigDecimal value) {
            addCriterion("CURR_TAXRATE <=", value, "currTaxrate");
            return (Criteria) this;
        }

        public Criteria andCurrTaxrateIn(List<BigDecimal> values) {
            addCriterion("CURR_TAXRATE in", values, "currTaxrate");
            return (Criteria) this;
        }

        public Criteria andCurrTaxrateNotIn(List<BigDecimal> values) {
            addCriterion("CURR_TAXRATE not in", values, "currTaxrate");
            return (Criteria) this;
        }

        public Criteria andCurrTaxrateBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("CURR_TAXRATE between", value1, value2, "currTaxrate");
            return (Criteria) this;
        }

        public Criteria andCurrTaxrateNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("CURR_TAXRATE not between", value1, value2, "currTaxrate");
            return (Criteria) this;
        }

        public Criteria andApplyTaxrateIsNull() {
            addCriterion("APPLY_TAXRATE is null");
            return (Criteria) this;
        }

        public Criteria andApplyTaxrateIsNotNull() {
            addCriterion("APPLY_TAXRATE is not null");
            return (Criteria) this;
        }

        public Criteria andApplyTaxrateEqualTo(BigDecimal value) {
            addCriterion("APPLY_TAXRATE =", value, "applyTaxrate");
            return (Criteria) this;
        }

        public Criteria andApplyTaxrateNotEqualTo(BigDecimal value) {
            addCriterion("APPLY_TAXRATE <>", value, "applyTaxrate");
            return (Criteria) this;
        }

        public Criteria andApplyTaxrateGreaterThan(BigDecimal value) {
            addCriterion("APPLY_TAXRATE >", value, "applyTaxrate");
            return (Criteria) this;
        }

        public Criteria andApplyTaxrateGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("APPLY_TAXRATE >=", value, "applyTaxrate");
            return (Criteria) this;
        }

        public Criteria andApplyTaxrateLessThan(BigDecimal value) {
            addCriterion("APPLY_TAXRATE <", value, "applyTaxrate");
            return (Criteria) this;
        }

        public Criteria andApplyTaxrateLessThanOrEqualTo(BigDecimal value) {
            addCriterion("APPLY_TAXRATE <=", value, "applyTaxrate");
            return (Criteria) this;
        }

        public Criteria andApplyTaxrateIn(List<BigDecimal> values) {
            addCriterion("APPLY_TAXRATE in", values, "applyTaxrate");
            return (Criteria) this;
        }

        public Criteria andApplyTaxrateNotIn(List<BigDecimal> values) {
            addCriterion("APPLY_TAXRATE not in", values, "applyTaxrate");
            return (Criteria) this;
        }

        public Criteria andApplyTaxrateBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("APPLY_TAXRATE between", value1, value2, "applyTaxrate");
            return (Criteria) this;
        }

        public Criteria andApplyTaxrateNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("APPLY_TAXRATE not between", value1, value2, "applyTaxrate");
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

        public Criteria andProveMaterialFileIsNull() {
            addCriterion("PROVE_MATERIAL_FILE is null");
            return (Criteria) this;
        }

        public Criteria andProveMaterialFileIsNotNull() {
            addCriterion("PROVE_MATERIAL_FILE is not null");
            return (Criteria) this;
        }

        public Criteria andProveMaterialFileEqualTo(String value) {
            addCriterion("PROVE_MATERIAL_FILE =", value, "proveMaterialFile");
            return (Criteria) this;
        }

        public Criteria andProveMaterialFileNotEqualTo(String value) {
            addCriterion("PROVE_MATERIAL_FILE <>", value, "proveMaterialFile");
            return (Criteria) this;
        }

        public Criteria andProveMaterialFileGreaterThan(String value) {
            addCriterion("PROVE_MATERIAL_FILE >", value, "proveMaterialFile");
            return (Criteria) this;
        }

        public Criteria andProveMaterialFileGreaterThanOrEqualTo(String value) {
            addCriterion("PROVE_MATERIAL_FILE >=", value, "proveMaterialFile");
            return (Criteria) this;
        }

        public Criteria andProveMaterialFileLessThan(String value) {
            addCriterion("PROVE_MATERIAL_FILE <", value, "proveMaterialFile");
            return (Criteria) this;
        }

        public Criteria andProveMaterialFileLessThanOrEqualTo(String value) {
            addCriterion("PROVE_MATERIAL_FILE <=", value, "proveMaterialFile");
            return (Criteria) this;
        }

        public Criteria andProveMaterialFileLike(String value) {
            addCriterion("PROVE_MATERIAL_FILE like", value, "proveMaterialFile");
            return (Criteria) this;
        }

        public Criteria andProveMaterialFileNotLike(String value) {
            addCriterion("PROVE_MATERIAL_FILE not like", value, "proveMaterialFile");
            return (Criteria) this;
        }

        public Criteria andProveMaterialFileIn(List<String> values) {
            addCriterion("PROVE_MATERIAL_FILE in", values, "proveMaterialFile");
            return (Criteria) this;
        }

        public Criteria andProveMaterialFileNotIn(List<String> values) {
            addCriterion("PROVE_MATERIAL_FILE not in", values, "proveMaterialFile");
            return (Criteria) this;
        }

        public Criteria andProveMaterialFileBetween(String value1, String value2) {
            addCriterion("PROVE_MATERIAL_FILE between", value1, value2, "proveMaterialFile");
            return (Criteria) this;
        }

        public Criteria andProveMaterialFileNotBetween(String value1, String value2) {
            addCriterion("PROVE_MATERIAL_FILE not between", value1, value2, "proveMaterialFile");
            return (Criteria) this;
        }

        public Criteria andApplyReasonIsNull() {
            addCriterion("APPLY_REASON is null");
            return (Criteria) this;
        }

        public Criteria andApplyReasonIsNotNull() {
            addCriterion("APPLY_REASON is not null");
            return (Criteria) this;
        }

        public Criteria andApplyReasonEqualTo(String value) {
            addCriterion("APPLY_REASON =", value, "applyReason");
            return (Criteria) this;
        }

        public Criteria andApplyReasonNotEqualTo(String value) {
            addCriterion("APPLY_REASON <>", value, "applyReason");
            return (Criteria) this;
        }

        public Criteria andApplyReasonGreaterThan(String value) {
            addCriterion("APPLY_REASON >", value, "applyReason");
            return (Criteria) this;
        }

        public Criteria andApplyReasonGreaterThanOrEqualTo(String value) {
            addCriterion("APPLY_REASON >=", value, "applyReason");
            return (Criteria) this;
        }

        public Criteria andApplyReasonLessThan(String value) {
            addCriterion("APPLY_REASON <", value, "applyReason");
            return (Criteria) this;
        }

        public Criteria andApplyReasonLessThanOrEqualTo(String value) {
            addCriterion("APPLY_REASON <=", value, "applyReason");
            return (Criteria) this;
        }

        public Criteria andApplyReasonLike(String value) {
            addCriterion("APPLY_REASON like", value, "applyReason");
            return (Criteria) this;
        }

        public Criteria andApplyReasonNotLike(String value) {
            addCriterion("APPLY_REASON not like", value, "applyReason");
            return (Criteria) this;
        }

        public Criteria andApplyReasonIn(List<String> values) {
            addCriterion("APPLY_REASON in", values, "applyReason");
            return (Criteria) this;
        }

        public Criteria andApplyReasonNotIn(List<String> values) {
            addCriterion("APPLY_REASON not in", values, "applyReason");
            return (Criteria) this;
        }

        public Criteria andApplyReasonBetween(String value1, String value2) {
            addCriterion("APPLY_REASON between", value1, value2, "applyReason");
            return (Criteria) this;
        }

        public Criteria andApplyReasonNotBetween(String value1, String value2) {
            addCriterion("APPLY_REASON not between", value1, value2, "applyReason");
            return (Criteria) this;
        }

        public Criteria andEnableDateIsNull() {
            addCriterion("ENABLE_DATE is null");
            return (Criteria) this;
        }

        public Criteria andEnableDateIsNotNull() {
            addCriterion("ENABLE_DATE is not null");
            return (Criteria) this;
        }

        public Criteria andEnableDateEqualTo(Date value) {
            addCriterion("ENABLE_DATE =", value, "enableDate");
            return (Criteria) this;
        }

        public Criteria andEnableDateNotEqualTo(Date value) {
            addCriterion("ENABLE_DATE <>", value, "enableDate");
            return (Criteria) this;
        }

        public Criteria andEnableDateGreaterThan(Date value) {
            addCriterion("ENABLE_DATE >", value, "enableDate");
            return (Criteria) this;
        }

        public Criteria andEnableDateGreaterThanOrEqualTo(Date value) {
            addCriterion("ENABLE_DATE >=", value, "enableDate");
            return (Criteria) this;
        }

        public Criteria andEnableDateLessThan(Date value) {
            addCriterion("ENABLE_DATE <", value, "enableDate");
            return (Criteria) this;
        }

        public Criteria andEnableDateLessThanOrEqualTo(Date value) {
            addCriterion("ENABLE_DATE <=", value, "enableDate");
            return (Criteria) this;
        }

        public Criteria andEnableDateIn(List<Date> values) {
            addCriterion("ENABLE_DATE in", values, "enableDate");
            return (Criteria) this;
        }

        public Criteria andEnableDateNotIn(List<Date> values) {
            addCriterion("ENABLE_DATE not in", values, "enableDate");
            return (Criteria) this;
        }

        public Criteria andEnableDateBetween(Date value1, Date value2) {
            addCriterion("ENABLE_DATE between", value1, value2, "enableDate");
            return (Criteria) this;
        }

        public Criteria andEnableDateNotBetween(Date value1, Date value2) {
            addCriterion("ENABLE_DATE not between", value1, value2, "enableDate");
            return (Criteria) this;
        }

        public Criteria andDisableDateIsNull() {
            addCriterion("DISABLE_DATE is null");
            return (Criteria) this;
        }

        public Criteria andDisableDateIsNotNull() {
            addCriterion("DISABLE_DATE is not null");
            return (Criteria) this;
        }

        public Criteria andDisableDateEqualTo(Date value) {
            addCriterion("DISABLE_DATE =", value, "disableDate");
            return (Criteria) this;
        }

        public Criteria andDisableDateNotEqualTo(Date value) {
            addCriterion("DISABLE_DATE <>", value, "disableDate");
            return (Criteria) this;
        }

        public Criteria andDisableDateGreaterThan(Date value) {
            addCriterion("DISABLE_DATE >", value, "disableDate");
            return (Criteria) this;
        }

        public Criteria andDisableDateGreaterThanOrEqualTo(Date value) {
            addCriterion("DISABLE_DATE >=", value, "disableDate");
            return (Criteria) this;
        }

        public Criteria andDisableDateLessThan(Date value) {
            addCriterion("DISABLE_DATE <", value, "disableDate");
            return (Criteria) this;
        }

        public Criteria andDisableDateLessThanOrEqualTo(Date value) {
            addCriterion("DISABLE_DATE <=", value, "disableDate");
            return (Criteria) this;
        }

        public Criteria andDisableDateIn(List<Date> values) {
            addCriterion("DISABLE_DATE in", values, "disableDate");
            return (Criteria) this;
        }

        public Criteria andDisableDateNotIn(List<Date> values) {
            addCriterion("DISABLE_DATE not in", values, "disableDate");
            return (Criteria) this;
        }

        public Criteria andDisableDateBetween(Date value1, Date value2) {
            addCriterion("DISABLE_DATE between", value1, value2, "disableDate");
            return (Criteria) this;
        }

        public Criteria andDisableDateNotBetween(Date value1, Date value2) {
            addCriterion("DISABLE_DATE not between", value1, value2, "disableDate");
            return (Criteria) this;
        }

        public Criteria andIsactiveIsNull() {
            addCriterion("ISACTIVE is null");
            return (Criteria) this;
        }

        public Criteria andIsactiveIsNotNull() {
            addCriterion("ISACTIVE is not null");
            return (Criteria) this;
        }

        public Criteria andIsactiveEqualTo(String value) {
            addCriterion("ISACTIVE =", value, "isactive");
            return (Criteria) this;
        }

        public Criteria andIsactiveNotEqualTo(String value) {
            addCriterion("ISACTIVE <>", value, "isactive");
            return (Criteria) this;
        }

        public Criteria andIsactiveGreaterThan(String value) {
            addCriterion("ISACTIVE >", value, "isactive");
            return (Criteria) this;
        }

        public Criteria andIsactiveGreaterThanOrEqualTo(String value) {
            addCriterion("ISACTIVE >=", value, "isactive");
            return (Criteria) this;
        }

        public Criteria andIsactiveLessThan(String value) {
            addCriterion("ISACTIVE <", value, "isactive");
            return (Criteria) this;
        }

        public Criteria andIsactiveLessThanOrEqualTo(String value) {
            addCriterion("ISACTIVE <=", value, "isactive");
            return (Criteria) this;
        }

        public Criteria andIsactiveLike(String value) {
            addCriterion("ISACTIVE like", value, "isactive");
            return (Criteria) this;
        }

        public Criteria andIsactiveNotLike(String value) {
            addCriterion("ISACTIVE not like", value, "isactive");
            return (Criteria) this;
        }

        public Criteria andIsactiveIn(List<String> values) {
            addCriterion("ISACTIVE in", values, "isactive");
            return (Criteria) this;
        }

        public Criteria andIsactiveNotIn(List<String> values) {
            addCriterion("ISACTIVE not in", values, "isactive");
            return (Criteria) this;
        }

        public Criteria andIsactiveBetween(String value1, String value2) {
            addCriterion("ISACTIVE between", value1, value2, "isactive");
            return (Criteria) this;
        }

        public Criteria andIsactiveNotBetween(String value1, String value2) {
            addCriterion("ISACTIVE not between", value1, value2, "isactive");
            return (Criteria) this;
        }

        public Criteria andCreatedDateIsNull() {
            addCriterion("CREATED_DATE is null");
            return (Criteria) this;
        }

        public Criteria andCreatedDateIsNotNull() {
            addCriterion("CREATED_DATE is not null");
            return (Criteria) this;
        }

        public Criteria andCreatedDateEqualTo(Date value) {
            addCriterion("CREATED_DATE =", value, "createdDate");
            return (Criteria) this;
        }

        public Criteria andCreatedDateNotEqualTo(Date value) {
            addCriterion("CREATED_DATE <>", value, "createdDate");
            return (Criteria) this;
        }

        public Criteria andCreatedDateGreaterThan(Date value) {
            addCriterion("CREATED_DATE >", value, "createdDate");
            return (Criteria) this;
        }

        public Criteria andCreatedDateGreaterThanOrEqualTo(Date value) {
            addCriterion("CREATED_DATE >=", value, "createdDate");
            return (Criteria) this;
        }

        public Criteria andCreatedDateLessThan(Date value) {
            addCriterion("CREATED_DATE <", value, "createdDate");
            return (Criteria) this;
        }

        public Criteria andCreatedDateLessThanOrEqualTo(Date value) {
            addCriterion("CREATED_DATE <=", value, "createdDate");
            return (Criteria) this;
        }

        public Criteria andCreatedDateIn(List<Date> values) {
            addCriterion("CREATED_DATE in", values, "createdDate");
            return (Criteria) this;
        }

        public Criteria andCreatedDateNotIn(List<Date> values) {
            addCriterion("CREATED_DATE not in", values, "createdDate");
            return (Criteria) this;
        }

        public Criteria andCreatedDateBetween(Date value1, Date value2) {
            addCriterion("CREATED_DATE between", value1, value2, "createdDate");
            return (Criteria) this;
        }

        public Criteria andCreatedDateNotBetween(Date value1, Date value2) {
            addCriterion("CREATED_DATE not between", value1, value2, "createdDate");
            return (Criteria) this;
        }

        public Criteria andCreatedbyIsNull() {
            addCriterion("CREATEDBY is null");
            return (Criteria) this;
        }

        public Criteria andCreatedbyIsNotNull() {
            addCriterion("CREATEDBY is not null");
            return (Criteria) this;
        }

        public Criteria andCreatedbyEqualTo(String value) {
            addCriterion("CREATEDBY =", value, "createdby");
            return (Criteria) this;
        }

        public Criteria andCreatedbyNotEqualTo(String value) {
            addCriterion("CREATEDBY <>", value, "createdby");
            return (Criteria) this;
        }

        public Criteria andCreatedbyGreaterThan(String value) {
            addCriterion("CREATEDBY >", value, "createdby");
            return (Criteria) this;
        }

        public Criteria andCreatedbyGreaterThanOrEqualTo(String value) {
            addCriterion("CREATEDBY >=", value, "createdby");
            return (Criteria) this;
        }

        public Criteria andCreatedbyLessThan(String value) {
            addCriterion("CREATEDBY <", value, "createdby");
            return (Criteria) this;
        }

        public Criteria andCreatedbyLessThanOrEqualTo(String value) {
            addCriterion("CREATEDBY <=", value, "createdby");
            return (Criteria) this;
        }

        public Criteria andCreatedbyLike(String value) {
            addCriterion("CREATEDBY like", value, "createdby");
            return (Criteria) this;
        }

        public Criteria andCreatedbyNotLike(String value) {
            addCriterion("CREATEDBY not like", value, "createdby");
            return (Criteria) this;
        }

        public Criteria andCreatedbyIn(List<String> values) {
            addCriterion("CREATEDBY in", values, "createdby");
            return (Criteria) this;
        }

        public Criteria andCreatedbyNotIn(List<String> values) {
            addCriterion("CREATEDBY not in", values, "createdby");
            return (Criteria) this;
        }

        public Criteria andCreatedbyBetween(String value1, String value2) {
            addCriterion("CREATEDBY between", value1, value2, "createdby");
            return (Criteria) this;
        }

        public Criteria andCreatedbyNotBetween(String value1, String value2) {
            addCriterion("CREATEDBY not between", value1, value2, "createdby");
            return (Criteria) this;
        }

        public Criteria andUpdatedDateIsNull() {
            addCriterion("UPDATED_DATE is null");
            return (Criteria) this;
        }

        public Criteria andUpdatedDateIsNotNull() {
            addCriterion("UPDATED_DATE is not null");
            return (Criteria) this;
        }

        public Criteria andUpdatedDateEqualTo(Date value) {
            addCriterion("UPDATED_DATE =", value, "updatedDate");
            return (Criteria) this;
        }

        public Criteria andUpdatedDateNotEqualTo(Date value) {
            addCriterion("UPDATED_DATE <>", value, "updatedDate");
            return (Criteria) this;
        }

        public Criteria andUpdatedDateGreaterThan(Date value) {
            addCriterion("UPDATED_DATE >", value, "updatedDate");
            return (Criteria) this;
        }

        public Criteria andUpdatedDateGreaterThanOrEqualTo(Date value) {
            addCriterion("UPDATED_DATE >=", value, "updatedDate");
            return (Criteria) this;
        }

        public Criteria andUpdatedDateLessThan(Date value) {
            addCriterion("UPDATED_DATE <", value, "updatedDate");
            return (Criteria) this;
        }

        public Criteria andUpdatedDateLessThanOrEqualTo(Date value) {
            addCriterion("UPDATED_DATE <=", value, "updatedDate");
            return (Criteria) this;
        }

        public Criteria andUpdatedDateIn(List<Date> values) {
            addCriterion("UPDATED_DATE in", values, "updatedDate");
            return (Criteria) this;
        }

        public Criteria andUpdatedDateNotIn(List<Date> values) {
            addCriterion("UPDATED_DATE not in", values, "updatedDate");
            return (Criteria) this;
        }

        public Criteria andUpdatedDateBetween(Date value1, Date value2) {
            addCriterion("UPDATED_DATE between", value1, value2, "updatedDate");
            return (Criteria) this;
        }

        public Criteria andUpdatedDateNotBetween(Date value1, Date value2) {
            addCriterion("UPDATED_DATE not between", value1, value2, "updatedDate");
            return (Criteria) this;
        }

        public Criteria andUpdatedbyIsNull() {
            addCriterion("UPDATEDBY is null");
            return (Criteria) this;
        }

        public Criteria andUpdatedbyIsNotNull() {
            addCriterion("UPDATEDBY is not null");
            return (Criteria) this;
        }

        public Criteria andUpdatedbyEqualTo(String value) {
            addCriterion("UPDATEDBY =", value, "updatedby");
            return (Criteria) this;
        }

        public Criteria andUpdatedbyNotEqualTo(String value) {
            addCriterion("UPDATEDBY <>", value, "updatedby");
            return (Criteria) this;
        }

        public Criteria andUpdatedbyGreaterThan(String value) {
            addCriterion("UPDATEDBY >", value, "updatedby");
            return (Criteria) this;
        }

        public Criteria andUpdatedbyGreaterThanOrEqualTo(String value) {
            addCriterion("UPDATEDBY >=", value, "updatedby");
            return (Criteria) this;
        }

        public Criteria andUpdatedbyLessThan(String value) {
            addCriterion("UPDATEDBY <", value, "updatedby");
            return (Criteria) this;
        }

        public Criteria andUpdatedbyLessThanOrEqualTo(String value) {
            addCriterion("UPDATEDBY <=", value, "updatedby");
            return (Criteria) this;
        }

        public Criteria andUpdatedbyLike(String value) {
            addCriterion("UPDATEDBY like", value, "updatedby");
            return (Criteria) this;
        }

        public Criteria andUpdatedbyNotLike(String value) {
            addCriterion("UPDATEDBY not like", value, "updatedby");
            return (Criteria) this;
        }

        public Criteria andUpdatedbyIn(List<String> values) {
            addCriterion("UPDATEDBY in", values, "updatedby");
            return (Criteria) this;
        }

        public Criteria andUpdatedbyNotIn(List<String> values) {
            addCriterion("UPDATEDBY not in", values, "updatedby");
            return (Criteria) this;
        }

        public Criteria andUpdatedbyBetween(String value1, String value2) {
            addCriterion("UPDATEDBY between", value1, value2, "updatedby");
            return (Criteria) this;
        }

        public Criteria andUpdatedbyNotBetween(String value1, String value2) {
            addCriterion("UPDATEDBY not between", value1, value2, "updatedby");
            return (Criteria) this;
        }

        public Criteria andUpdatedNameIsNull() {
            addCriterion("UPDATED_NAME is null");
            return (Criteria) this;
        }

        public Criteria andUpdatedNameIsNotNull() {
            addCriterion("UPDATED_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andUpdatedNameEqualTo(String value) {
            addCriterion("UPDATED_NAME =", value, "updatedName");
            return (Criteria) this;
        }

        public Criteria andUpdatedNameNotEqualTo(String value) {
            addCriterion("UPDATED_NAME <>", value, "updatedName");
            return (Criteria) this;
        }

        public Criteria andUpdatedNameGreaterThan(String value) {
            addCriterion("UPDATED_NAME >", value, "updatedName");
            return (Criteria) this;
        }

        public Criteria andUpdatedNameGreaterThanOrEqualTo(String value) {
            addCriterion("UPDATED_NAME >=", value, "updatedName");
            return (Criteria) this;
        }

        public Criteria andUpdatedNameLessThan(String value) {
            addCriterion("UPDATED_NAME <", value, "updatedName");
            return (Criteria) this;
        }

        public Criteria andUpdatedNameLessThanOrEqualTo(String value) {
            addCriterion("UPDATED_NAME <=", value, "updatedName");
            return (Criteria) this;
        }

        public Criteria andUpdatedNameLike(String value) {
            addCriterion("UPDATED_NAME like", value, "updatedName");
            return (Criteria) this;
        }

        public Criteria andUpdatedNameNotLike(String value) {
            addCriterion("UPDATED_NAME not like", value, "updatedName");
            return (Criteria) this;
        }

        public Criteria andUpdatedNameIn(List<String> values) {
            addCriterion("UPDATED_NAME in", values, "updatedName");
            return (Criteria) this;
        }

        public Criteria andUpdatedNameNotIn(List<String> values) {
            addCriterion("UPDATED_NAME not in", values, "updatedName");
            return (Criteria) this;
        }

        public Criteria andUpdatedNameBetween(String value1, String value2) {
            addCriterion("UPDATED_NAME between", value1, value2, "updatedName");
            return (Criteria) this;
        }

        public Criteria andUpdatedNameNotBetween(String value1, String value2) {
            addCriterion("UPDATED_NAME not between", value1, value2, "updatedName");
            return (Criteria) this;
        }

        public Criteria andCreatedNameIsNull() {
            addCriterion("CREATED_NAME is null");
            return (Criteria) this;
        }

        public Criteria andCreatedNameIsNotNull() {
            addCriterion("CREATED_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andCreatedNameEqualTo(String value) {
            addCriterion("CREATED_NAME =", value, "createdName");
            return (Criteria) this;
        }

        public Criteria andCreatedNameNotEqualTo(String value) {
            addCriterion("CREATED_NAME <>", value, "createdName");
            return (Criteria) this;
        }

        public Criteria andCreatedNameGreaterThan(String value) {
            addCriterion("CREATED_NAME >", value, "createdName");
            return (Criteria) this;
        }

        public Criteria andCreatedNameGreaterThanOrEqualTo(String value) {
            addCriterion("CREATED_NAME >=", value, "createdName");
            return (Criteria) this;
        }

        public Criteria andCreatedNameLessThan(String value) {
            addCriterion("CREATED_NAME <", value, "createdName");
            return (Criteria) this;
        }

        public Criteria andCreatedNameLessThanOrEqualTo(String value) {
            addCriterion("CREATED_NAME <=", value, "createdName");
            return (Criteria) this;
        }

        public Criteria andCreatedNameLike(String value) {
            addCriterion("CREATED_NAME like", value, "createdName");
            return (Criteria) this;
        }

        public Criteria andCreatedNameNotLike(String value) {
            addCriterion("CREATED_NAME not like", value, "createdName");
            return (Criteria) this;
        }

        public Criteria andCreatedNameIn(List<String> values) {
            addCriterion("CREATED_NAME in", values, "createdName");
            return (Criteria) this;
        }

        public Criteria andCreatedNameNotIn(List<String> values) {
            addCriterion("CREATED_NAME not in", values, "createdName");
            return (Criteria) this;
        }

        public Criteria andCreatedNameBetween(String value1, String value2) {
            addCriterion("CREATED_NAME between", value1, value2, "createdName");
            return (Criteria) this;
        }

        public Criteria andCreatedNameNotBetween(String value1, String value2) {
            addCriterion("CREATED_NAME not between", value1, value2, "createdName");
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