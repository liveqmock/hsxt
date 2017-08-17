package com.gy.hsi.fs.mapper.vo.dbbs01;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.gy.hsi.fs.mapper.vo.AbstractPage;

public class TBsPerRealnameAuthExample extends AbstractPage {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TBsPerRealnameAuthExample() {
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

        public Criteria andPerResNoIsNull() {
            addCriterion("PER_RES_NO is null");
            return (Criteria) this;
        }

        public Criteria andPerResNoIsNotNull() {
            addCriterion("PER_RES_NO is not null");
            return (Criteria) this;
        }

        public Criteria andPerResNoEqualTo(String value) {
            addCriterion("PER_RES_NO =", value, "perResNo");
            return (Criteria) this;
        }

        public Criteria andPerResNoNotEqualTo(String value) {
            addCriterion("PER_RES_NO <>", value, "perResNo");
            return (Criteria) this;
        }

        public Criteria andPerResNoGreaterThan(String value) {
            addCriterion("PER_RES_NO >", value, "perResNo");
            return (Criteria) this;
        }

        public Criteria andPerResNoGreaterThanOrEqualTo(String value) {
            addCriterion("PER_RES_NO >=", value, "perResNo");
            return (Criteria) this;
        }

        public Criteria andPerResNoLessThan(String value) {
            addCriterion("PER_RES_NO <", value, "perResNo");
            return (Criteria) this;
        }

        public Criteria andPerResNoLessThanOrEqualTo(String value) {
            addCriterion("PER_RES_NO <=", value, "perResNo");
            return (Criteria) this;
        }

        public Criteria andPerResNoLike(String value) {
            addCriterion("PER_RES_NO like", value, "perResNo");
            return (Criteria) this;
        }

        public Criteria andPerResNoNotLike(String value) {
            addCriterion("PER_RES_NO not like", value, "perResNo");
            return (Criteria) this;
        }

        public Criteria andPerResNoIn(List<String> values) {
            addCriterion("PER_RES_NO in", values, "perResNo");
            return (Criteria) this;
        }

        public Criteria andPerResNoNotIn(List<String> values) {
            addCriterion("PER_RES_NO not in", values, "perResNo");
            return (Criteria) this;
        }

        public Criteria andPerResNoBetween(String value1, String value2) {
            addCriterion("PER_RES_NO between", value1, value2, "perResNo");
            return (Criteria) this;
        }

        public Criteria andPerResNoNotBetween(String value1, String value2) {
            addCriterion("PER_RES_NO not between", value1, value2, "perResNo");
            return (Criteria) this;
        }

        public Criteria andPerCustIdIsNull() {
            addCriterion("PER_CUST_ID is null");
            return (Criteria) this;
        }

        public Criteria andPerCustIdIsNotNull() {
            addCriterion("PER_CUST_ID is not null");
            return (Criteria) this;
        }

        public Criteria andPerCustIdEqualTo(String value) {
            addCriterion("PER_CUST_ID =", value, "perCustId");
            return (Criteria) this;
        }

        public Criteria andPerCustIdNotEqualTo(String value) {
            addCriterion("PER_CUST_ID <>", value, "perCustId");
            return (Criteria) this;
        }

        public Criteria andPerCustIdGreaterThan(String value) {
            addCriterion("PER_CUST_ID >", value, "perCustId");
            return (Criteria) this;
        }

        public Criteria andPerCustIdGreaterThanOrEqualTo(String value) {
            addCriterion("PER_CUST_ID >=", value, "perCustId");
            return (Criteria) this;
        }

        public Criteria andPerCustIdLessThan(String value) {
            addCriterion("PER_CUST_ID <", value, "perCustId");
            return (Criteria) this;
        }

        public Criteria andPerCustIdLessThanOrEqualTo(String value) {
            addCriterion("PER_CUST_ID <=", value, "perCustId");
            return (Criteria) this;
        }

        public Criteria andPerCustIdLike(String value) {
            addCriterion("PER_CUST_ID like", value, "perCustId");
            return (Criteria) this;
        }

        public Criteria andPerCustIdNotLike(String value) {
            addCriterion("PER_CUST_ID not like", value, "perCustId");
            return (Criteria) this;
        }

        public Criteria andPerCustIdIn(List<String> values) {
            addCriterion("PER_CUST_ID in", values, "perCustId");
            return (Criteria) this;
        }

        public Criteria andPerCustIdNotIn(List<String> values) {
            addCriterion("PER_CUST_ID not in", values, "perCustId");
            return (Criteria) this;
        }

        public Criteria andPerCustIdBetween(String value1, String value2) {
            addCriterion("PER_CUST_ID between", value1, value2, "perCustId");
            return (Criteria) this;
        }

        public Criteria andPerCustIdNotBetween(String value1, String value2) {
            addCriterion("PER_CUST_ID not between", value1, value2, "perCustId");
            return (Criteria) this;
        }

        public Criteria andNameIsNull() {
            addCriterion("NAME is null");
            return (Criteria) this;
        }

        public Criteria andNameIsNotNull() {
            addCriterion("NAME is not null");
            return (Criteria) this;
        }

        public Criteria andNameEqualTo(String value) {
            addCriterion("NAME =", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotEqualTo(String value) {
            addCriterion("NAME <>", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThan(String value) {
            addCriterion("NAME >", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThanOrEqualTo(String value) {
            addCriterion("NAME >=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThan(String value) {
            addCriterion("NAME <", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThanOrEqualTo(String value) {
            addCriterion("NAME <=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLike(String value) {
            addCriterion("NAME like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotLike(String value) {
            addCriterion("NAME not like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameIn(List<String> values) {
            addCriterion("NAME in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotIn(List<String> values) {
            addCriterion("NAME not in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameBetween(String value1, String value2) {
            addCriterion("NAME between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotBetween(String value1, String value2) {
            addCriterion("NAME not between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andSexIsNull() {
            addCriterion("SEX is null");
            return (Criteria) this;
        }

        public Criteria andSexIsNotNull() {
            addCriterion("SEX is not null");
            return (Criteria) this;
        }

        public Criteria andSexEqualTo(Short value) {
            addCriterion("SEX =", value, "sex");
            return (Criteria) this;
        }

        public Criteria andSexNotEqualTo(Short value) {
            addCriterion("SEX <>", value, "sex");
            return (Criteria) this;
        }

        public Criteria andSexGreaterThan(Short value) {
            addCriterion("SEX >", value, "sex");
            return (Criteria) this;
        }

        public Criteria andSexGreaterThanOrEqualTo(Short value) {
            addCriterion("SEX >=", value, "sex");
            return (Criteria) this;
        }

        public Criteria andSexLessThan(Short value) {
            addCriterion("SEX <", value, "sex");
            return (Criteria) this;
        }

        public Criteria andSexLessThanOrEqualTo(Short value) {
            addCriterion("SEX <=", value, "sex");
            return (Criteria) this;
        }

        public Criteria andSexIn(List<Short> values) {
            addCriterion("SEX in", values, "sex");
            return (Criteria) this;
        }

        public Criteria andSexNotIn(List<Short> values) {
            addCriterion("SEX not in", values, "sex");
            return (Criteria) this;
        }

        public Criteria andSexBetween(Short value1, Short value2) {
            addCriterion("SEX between", value1, value2, "sex");
            return (Criteria) this;
        }

        public Criteria andSexNotBetween(Short value1, Short value2) {
            addCriterion("SEX not between", value1, value2, "sex");
            return (Criteria) this;
        }

        public Criteria andMobileIsNull() {
            addCriterion("MOBILE is null");
            return (Criteria) this;
        }

        public Criteria andMobileIsNotNull() {
            addCriterion("MOBILE is not null");
            return (Criteria) this;
        }

        public Criteria andMobileEqualTo(String value) {
            addCriterion("MOBILE =", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileNotEqualTo(String value) {
            addCriterion("MOBILE <>", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileGreaterThan(String value) {
            addCriterion("MOBILE >", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileGreaterThanOrEqualTo(String value) {
            addCriterion("MOBILE >=", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileLessThan(String value) {
            addCriterion("MOBILE <", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileLessThanOrEqualTo(String value) {
            addCriterion("MOBILE <=", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileLike(String value) {
            addCriterion("MOBILE like", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileNotLike(String value) {
            addCriterion("MOBILE not like", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileIn(List<String> values) {
            addCriterion("MOBILE in", values, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileNotIn(List<String> values) {
            addCriterion("MOBILE not in", values, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileBetween(String value1, String value2) {
            addCriterion("MOBILE between", value1, value2, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileNotBetween(String value1, String value2) {
            addCriterion("MOBILE not between", value1, value2, "mobile");
            return (Criteria) this;
        }

        public Criteria andCountryNoIsNull() {
            addCriterion("COUNTRY_NO is null");
            return (Criteria) this;
        }

        public Criteria andCountryNoIsNotNull() {
            addCriterion("COUNTRY_NO is not null");
            return (Criteria) this;
        }

        public Criteria andCountryNoEqualTo(String value) {
            addCriterion("COUNTRY_NO =", value, "countryNo");
            return (Criteria) this;
        }

        public Criteria andCountryNoNotEqualTo(String value) {
            addCriterion("COUNTRY_NO <>", value, "countryNo");
            return (Criteria) this;
        }

        public Criteria andCountryNoGreaterThan(String value) {
            addCriterion("COUNTRY_NO >", value, "countryNo");
            return (Criteria) this;
        }

        public Criteria andCountryNoGreaterThanOrEqualTo(String value) {
            addCriterion("COUNTRY_NO >=", value, "countryNo");
            return (Criteria) this;
        }

        public Criteria andCountryNoLessThan(String value) {
            addCriterion("COUNTRY_NO <", value, "countryNo");
            return (Criteria) this;
        }

        public Criteria andCountryNoLessThanOrEqualTo(String value) {
            addCriterion("COUNTRY_NO <=", value, "countryNo");
            return (Criteria) this;
        }

        public Criteria andCountryNoLike(String value) {
            addCriterion("COUNTRY_NO like", value, "countryNo");
            return (Criteria) this;
        }

        public Criteria andCountryNoNotLike(String value) {
            addCriterion("COUNTRY_NO not like", value, "countryNo");
            return (Criteria) this;
        }

        public Criteria andCountryNoIn(List<String> values) {
            addCriterion("COUNTRY_NO in", values, "countryNo");
            return (Criteria) this;
        }

        public Criteria andCountryNoNotIn(List<String> values) {
            addCriterion("COUNTRY_NO not in", values, "countryNo");
            return (Criteria) this;
        }

        public Criteria andCountryNoBetween(String value1, String value2) {
            addCriterion("COUNTRY_NO between", value1, value2, "countryNo");
            return (Criteria) this;
        }

        public Criteria andCountryNoNotBetween(String value1, String value2) {
            addCriterion("COUNTRY_NO not between", value1, value2, "countryNo");
            return (Criteria) this;
        }

        public Criteria andCountryNameIsNull() {
            addCriterion("COUNTRY_NAME is null");
            return (Criteria) this;
        }

        public Criteria andCountryNameIsNotNull() {
            addCriterion("COUNTRY_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andCountryNameEqualTo(String value) {
            addCriterion("COUNTRY_NAME =", value, "countryName");
            return (Criteria) this;
        }

        public Criteria andCountryNameNotEqualTo(String value) {
            addCriterion("COUNTRY_NAME <>", value, "countryName");
            return (Criteria) this;
        }

        public Criteria andCountryNameGreaterThan(String value) {
            addCriterion("COUNTRY_NAME >", value, "countryName");
            return (Criteria) this;
        }

        public Criteria andCountryNameGreaterThanOrEqualTo(String value) {
            addCriterion("COUNTRY_NAME >=", value, "countryName");
            return (Criteria) this;
        }

        public Criteria andCountryNameLessThan(String value) {
            addCriterion("COUNTRY_NAME <", value, "countryName");
            return (Criteria) this;
        }

        public Criteria andCountryNameLessThanOrEqualTo(String value) {
            addCriterion("COUNTRY_NAME <=", value, "countryName");
            return (Criteria) this;
        }

        public Criteria andCountryNameLike(String value) {
            addCriterion("COUNTRY_NAME like", value, "countryName");
            return (Criteria) this;
        }

        public Criteria andCountryNameNotLike(String value) {
            addCriterion("COUNTRY_NAME not like", value, "countryName");
            return (Criteria) this;
        }

        public Criteria andCountryNameIn(List<String> values) {
            addCriterion("COUNTRY_NAME in", values, "countryName");
            return (Criteria) this;
        }

        public Criteria andCountryNameNotIn(List<String> values) {
            addCriterion("COUNTRY_NAME not in", values, "countryName");
            return (Criteria) this;
        }

        public Criteria andCountryNameBetween(String value1, String value2) {
            addCriterion("COUNTRY_NAME between", value1, value2, "countryName");
            return (Criteria) this;
        }

        public Criteria andCountryNameNotBetween(String value1, String value2) {
            addCriterion("COUNTRY_NAME not between", value1, value2, "countryName");
            return (Criteria) this;
        }

        public Criteria andNationIsNull() {
            addCriterion("NATION is null");
            return (Criteria) this;
        }

        public Criteria andNationIsNotNull() {
            addCriterion("NATION is not null");
            return (Criteria) this;
        }

        public Criteria andNationEqualTo(String value) {
            addCriterion("NATION =", value, "nation");
            return (Criteria) this;
        }

        public Criteria andNationNotEqualTo(String value) {
            addCriterion("NATION <>", value, "nation");
            return (Criteria) this;
        }

        public Criteria andNationGreaterThan(String value) {
            addCriterion("NATION >", value, "nation");
            return (Criteria) this;
        }

        public Criteria andNationGreaterThanOrEqualTo(String value) {
            addCriterion("NATION >=", value, "nation");
            return (Criteria) this;
        }

        public Criteria andNationLessThan(String value) {
            addCriterion("NATION <", value, "nation");
            return (Criteria) this;
        }

        public Criteria andNationLessThanOrEqualTo(String value) {
            addCriterion("NATION <=", value, "nation");
            return (Criteria) this;
        }

        public Criteria andNationLike(String value) {
            addCriterion("NATION like", value, "nation");
            return (Criteria) this;
        }

        public Criteria andNationNotLike(String value) {
            addCriterion("NATION not like", value, "nation");
            return (Criteria) this;
        }

        public Criteria andNationIn(List<String> values) {
            addCriterion("NATION in", values, "nation");
            return (Criteria) this;
        }

        public Criteria andNationNotIn(List<String> values) {
            addCriterion("NATION not in", values, "nation");
            return (Criteria) this;
        }

        public Criteria andNationBetween(String value1, String value2) {
            addCriterion("NATION between", value1, value2, "nation");
            return (Criteria) this;
        }

        public Criteria andNationNotBetween(String value1, String value2) {
            addCriterion("NATION not between", value1, value2, "nation");
            return (Criteria) this;
        }

        public Criteria andBirthAddrIsNull() {
            addCriterion("BIRTH_ADDR is null");
            return (Criteria) this;
        }

        public Criteria andBirthAddrIsNotNull() {
            addCriterion("BIRTH_ADDR is not null");
            return (Criteria) this;
        }

        public Criteria andBirthAddrEqualTo(String value) {
            addCriterion("BIRTH_ADDR =", value, "birthAddr");
            return (Criteria) this;
        }

        public Criteria andBirthAddrNotEqualTo(String value) {
            addCriterion("BIRTH_ADDR <>", value, "birthAddr");
            return (Criteria) this;
        }

        public Criteria andBirthAddrGreaterThan(String value) {
            addCriterion("BIRTH_ADDR >", value, "birthAddr");
            return (Criteria) this;
        }

        public Criteria andBirthAddrGreaterThanOrEqualTo(String value) {
            addCriterion("BIRTH_ADDR >=", value, "birthAddr");
            return (Criteria) this;
        }

        public Criteria andBirthAddrLessThan(String value) {
            addCriterion("BIRTH_ADDR <", value, "birthAddr");
            return (Criteria) this;
        }

        public Criteria andBirthAddrLessThanOrEqualTo(String value) {
            addCriterion("BIRTH_ADDR <=", value, "birthAddr");
            return (Criteria) this;
        }

        public Criteria andBirthAddrLike(String value) {
            addCriterion("BIRTH_ADDR like", value, "birthAddr");
            return (Criteria) this;
        }

        public Criteria andBirthAddrNotLike(String value) {
            addCriterion("BIRTH_ADDR not like", value, "birthAddr");
            return (Criteria) this;
        }

        public Criteria andBirthAddrIn(List<String> values) {
            addCriterion("BIRTH_ADDR in", values, "birthAddr");
            return (Criteria) this;
        }

        public Criteria andBirthAddrNotIn(List<String> values) {
            addCriterion("BIRTH_ADDR not in", values, "birthAddr");
            return (Criteria) this;
        }

        public Criteria andBirthAddrBetween(String value1, String value2) {
            addCriterion("BIRTH_ADDR between", value1, value2, "birthAddr");
            return (Criteria) this;
        }

        public Criteria andBirthAddrNotBetween(String value1, String value2) {
            addCriterion("BIRTH_ADDR not between", value1, value2, "birthAddr");
            return (Criteria) this;
        }

        public Criteria andLicenceIssuingIsNull() {
            addCriterion("LICENCE_ISSUING is null");
            return (Criteria) this;
        }

        public Criteria andLicenceIssuingIsNotNull() {
            addCriterion("LICENCE_ISSUING is not null");
            return (Criteria) this;
        }

        public Criteria andLicenceIssuingEqualTo(String value) {
            addCriterion("LICENCE_ISSUING =", value, "licenceIssuing");
            return (Criteria) this;
        }

        public Criteria andLicenceIssuingNotEqualTo(String value) {
            addCriterion("LICENCE_ISSUING <>", value, "licenceIssuing");
            return (Criteria) this;
        }

        public Criteria andLicenceIssuingGreaterThan(String value) {
            addCriterion("LICENCE_ISSUING >", value, "licenceIssuing");
            return (Criteria) this;
        }

        public Criteria andLicenceIssuingGreaterThanOrEqualTo(String value) {
            addCriterion("LICENCE_ISSUING >=", value, "licenceIssuing");
            return (Criteria) this;
        }

        public Criteria andLicenceIssuingLessThan(String value) {
            addCriterion("LICENCE_ISSUING <", value, "licenceIssuing");
            return (Criteria) this;
        }

        public Criteria andLicenceIssuingLessThanOrEqualTo(String value) {
            addCriterion("LICENCE_ISSUING <=", value, "licenceIssuing");
            return (Criteria) this;
        }

        public Criteria andLicenceIssuingLike(String value) {
            addCriterion("LICENCE_ISSUING like", value, "licenceIssuing");
            return (Criteria) this;
        }

        public Criteria andLicenceIssuingNotLike(String value) {
            addCriterion("LICENCE_ISSUING not like", value, "licenceIssuing");
            return (Criteria) this;
        }

        public Criteria andLicenceIssuingIn(List<String> values) {
            addCriterion("LICENCE_ISSUING in", values, "licenceIssuing");
            return (Criteria) this;
        }

        public Criteria andLicenceIssuingNotIn(List<String> values) {
            addCriterion("LICENCE_ISSUING not in", values, "licenceIssuing");
            return (Criteria) this;
        }

        public Criteria andLicenceIssuingBetween(String value1, String value2) {
            addCriterion("LICENCE_ISSUING between", value1, value2, "licenceIssuing");
            return (Criteria) this;
        }

        public Criteria andLicenceIssuingNotBetween(String value1, String value2) {
            addCriterion("LICENCE_ISSUING not between", value1, value2, "licenceIssuing");
            return (Criteria) this;
        }

        public Criteria andProfessionIsNull() {
            addCriterion("PROFESSION is null");
            return (Criteria) this;
        }

        public Criteria andProfessionIsNotNull() {
            addCriterion("PROFESSION is not null");
            return (Criteria) this;
        }

        public Criteria andProfessionEqualTo(String value) {
            addCriterion("PROFESSION =", value, "profession");
            return (Criteria) this;
        }

        public Criteria andProfessionNotEqualTo(String value) {
            addCriterion("PROFESSION <>", value, "profession");
            return (Criteria) this;
        }

        public Criteria andProfessionGreaterThan(String value) {
            addCriterion("PROFESSION >", value, "profession");
            return (Criteria) this;
        }

        public Criteria andProfessionGreaterThanOrEqualTo(String value) {
            addCriterion("PROFESSION >=", value, "profession");
            return (Criteria) this;
        }

        public Criteria andProfessionLessThan(String value) {
            addCriterion("PROFESSION <", value, "profession");
            return (Criteria) this;
        }

        public Criteria andProfessionLessThanOrEqualTo(String value) {
            addCriterion("PROFESSION <=", value, "profession");
            return (Criteria) this;
        }

        public Criteria andProfessionLike(String value) {
            addCriterion("PROFESSION like", value, "profession");
            return (Criteria) this;
        }

        public Criteria andProfessionNotLike(String value) {
            addCriterion("PROFESSION not like", value, "profession");
            return (Criteria) this;
        }

        public Criteria andProfessionIn(List<String> values) {
            addCriterion("PROFESSION in", values, "profession");
            return (Criteria) this;
        }

        public Criteria andProfessionNotIn(List<String> values) {
            addCriterion("PROFESSION not in", values, "profession");
            return (Criteria) this;
        }

        public Criteria andProfessionBetween(String value1, String value2) {
            addCriterion("PROFESSION between", value1, value2, "profession");
            return (Criteria) this;
        }

        public Criteria andProfessionNotBetween(String value1, String value2) {
            addCriterion("PROFESSION not between", value1, value2, "profession");
            return (Criteria) this;
        }

        public Criteria andCertypeIsNull() {
            addCriterion("CERTYPE is null");
            return (Criteria) this;
        }

        public Criteria andCertypeIsNotNull() {
            addCriterion("CERTYPE is not null");
            return (Criteria) this;
        }

        public Criteria andCertypeEqualTo(Short value) {
            addCriterion("CERTYPE =", value, "certype");
            return (Criteria) this;
        }

        public Criteria andCertypeNotEqualTo(Short value) {
            addCriterion("CERTYPE <>", value, "certype");
            return (Criteria) this;
        }

        public Criteria andCertypeGreaterThan(Short value) {
            addCriterion("CERTYPE >", value, "certype");
            return (Criteria) this;
        }

        public Criteria andCertypeGreaterThanOrEqualTo(Short value) {
            addCriterion("CERTYPE >=", value, "certype");
            return (Criteria) this;
        }

        public Criteria andCertypeLessThan(Short value) {
            addCriterion("CERTYPE <", value, "certype");
            return (Criteria) this;
        }

        public Criteria andCertypeLessThanOrEqualTo(Short value) {
            addCriterion("CERTYPE <=", value, "certype");
            return (Criteria) this;
        }

        public Criteria andCertypeIn(List<Short> values) {
            addCriterion("CERTYPE in", values, "certype");
            return (Criteria) this;
        }

        public Criteria andCertypeNotIn(List<Short> values) {
            addCriterion("CERTYPE not in", values, "certype");
            return (Criteria) this;
        }

        public Criteria andCertypeBetween(Short value1, Short value2) {
            addCriterion("CERTYPE between", value1, value2, "certype");
            return (Criteria) this;
        }

        public Criteria andCertypeNotBetween(Short value1, Short value2) {
            addCriterion("CERTYPE not between", value1, value2, "certype");
            return (Criteria) this;
        }

        public Criteria andCredentialsNoIsNull() {
            addCriterion("CREDENTIALS_NO is null");
            return (Criteria) this;
        }

        public Criteria andCredentialsNoIsNotNull() {
            addCriterion("CREDENTIALS_NO is not null");
            return (Criteria) this;
        }

        public Criteria andCredentialsNoEqualTo(String value) {
            addCriterion("CREDENTIALS_NO =", value, "credentialsNo");
            return (Criteria) this;
        }

        public Criteria andCredentialsNoNotEqualTo(String value) {
            addCriterion("CREDENTIALS_NO <>", value, "credentialsNo");
            return (Criteria) this;
        }

        public Criteria andCredentialsNoGreaterThan(String value) {
            addCriterion("CREDENTIALS_NO >", value, "credentialsNo");
            return (Criteria) this;
        }

        public Criteria andCredentialsNoGreaterThanOrEqualTo(String value) {
            addCriterion("CREDENTIALS_NO >=", value, "credentialsNo");
            return (Criteria) this;
        }

        public Criteria andCredentialsNoLessThan(String value) {
            addCriterion("CREDENTIALS_NO <", value, "credentialsNo");
            return (Criteria) this;
        }

        public Criteria andCredentialsNoLessThanOrEqualTo(String value) {
            addCriterion("CREDENTIALS_NO <=", value, "credentialsNo");
            return (Criteria) this;
        }

        public Criteria andCredentialsNoLike(String value) {
            addCriterion("CREDENTIALS_NO like", value, "credentialsNo");
            return (Criteria) this;
        }

        public Criteria andCredentialsNoNotLike(String value) {
            addCriterion("CREDENTIALS_NO not like", value, "credentialsNo");
            return (Criteria) this;
        }

        public Criteria andCredentialsNoIn(List<String> values) {
            addCriterion("CREDENTIALS_NO in", values, "credentialsNo");
            return (Criteria) this;
        }

        public Criteria andCredentialsNoNotIn(List<String> values) {
            addCriterion("CREDENTIALS_NO not in", values, "credentialsNo");
            return (Criteria) this;
        }

        public Criteria andCredentialsNoBetween(String value1, String value2) {
            addCriterion("CREDENTIALS_NO between", value1, value2, "credentialsNo");
            return (Criteria) this;
        }

        public Criteria andCredentialsNoNotBetween(String value1, String value2) {
            addCriterion("CREDENTIALS_NO not between", value1, value2, "credentialsNo");
            return (Criteria) this;
        }

        public Criteria andCerpicaIsNull() {
            addCriterion("CERPICA is null");
            return (Criteria) this;
        }

        public Criteria andCerpicaIsNotNull() {
            addCriterion("CERPICA is not null");
            return (Criteria) this;
        }

        public Criteria andCerpicaEqualTo(String value) {
            addCriterion("CERPICA =", value, "cerpica");
            return (Criteria) this;
        }

        public Criteria andCerpicaNotEqualTo(String value) {
            addCriterion("CERPICA <>", value, "cerpica");
            return (Criteria) this;
        }

        public Criteria andCerpicaGreaterThan(String value) {
            addCriterion("CERPICA >", value, "cerpica");
            return (Criteria) this;
        }

        public Criteria andCerpicaGreaterThanOrEqualTo(String value) {
            addCriterion("CERPICA >=", value, "cerpica");
            return (Criteria) this;
        }

        public Criteria andCerpicaLessThan(String value) {
            addCriterion("CERPICA <", value, "cerpica");
            return (Criteria) this;
        }

        public Criteria andCerpicaLessThanOrEqualTo(String value) {
            addCriterion("CERPICA <=", value, "cerpica");
            return (Criteria) this;
        }

        public Criteria andCerpicaLike(String value) {
            addCriterion("CERPICA like", value, "cerpica");
            return (Criteria) this;
        }

        public Criteria andCerpicaNotLike(String value) {
            addCriterion("CERPICA not like", value, "cerpica");
            return (Criteria) this;
        }

        public Criteria andCerpicaIn(List<String> values) {
            addCriterion("CERPICA in", values, "cerpica");
            return (Criteria) this;
        }

        public Criteria andCerpicaNotIn(List<String> values) {
            addCriterion("CERPICA not in", values, "cerpica");
            return (Criteria) this;
        }

        public Criteria andCerpicaBetween(String value1, String value2) {
            addCriterion("CERPICA between", value1, value2, "cerpica");
            return (Criteria) this;
        }

        public Criteria andCerpicaNotBetween(String value1, String value2) {
            addCriterion("CERPICA not between", value1, value2, "cerpica");
            return (Criteria) this;
        }

        public Criteria andCerpicbIsNull() {
            addCriterion("CERPICB is null");
            return (Criteria) this;
        }

        public Criteria andCerpicbIsNotNull() {
            addCriterion("CERPICB is not null");
            return (Criteria) this;
        }

        public Criteria andCerpicbEqualTo(String value) {
            addCriterion("CERPICB =", value, "cerpicb");
            return (Criteria) this;
        }

        public Criteria andCerpicbNotEqualTo(String value) {
            addCriterion("CERPICB <>", value, "cerpicb");
            return (Criteria) this;
        }

        public Criteria andCerpicbGreaterThan(String value) {
            addCriterion("CERPICB >", value, "cerpicb");
            return (Criteria) this;
        }

        public Criteria andCerpicbGreaterThanOrEqualTo(String value) {
            addCriterion("CERPICB >=", value, "cerpicb");
            return (Criteria) this;
        }

        public Criteria andCerpicbLessThan(String value) {
            addCriterion("CERPICB <", value, "cerpicb");
            return (Criteria) this;
        }

        public Criteria andCerpicbLessThanOrEqualTo(String value) {
            addCriterion("CERPICB <=", value, "cerpicb");
            return (Criteria) this;
        }

        public Criteria andCerpicbLike(String value) {
            addCriterion("CERPICB like", value, "cerpicb");
            return (Criteria) this;
        }

        public Criteria andCerpicbNotLike(String value) {
            addCriterion("CERPICB not like", value, "cerpicb");
            return (Criteria) this;
        }

        public Criteria andCerpicbIn(List<String> values) {
            addCriterion("CERPICB in", values, "cerpicb");
            return (Criteria) this;
        }

        public Criteria andCerpicbNotIn(List<String> values) {
            addCriterion("CERPICB not in", values, "cerpicb");
            return (Criteria) this;
        }

        public Criteria andCerpicbBetween(String value1, String value2) {
            addCriterion("CERPICB between", value1, value2, "cerpicb");
            return (Criteria) this;
        }

        public Criteria andCerpicbNotBetween(String value1, String value2) {
            addCriterion("CERPICB not between", value1, value2, "cerpicb");
            return (Criteria) this;
        }

        public Criteria andCerpichIsNull() {
            addCriterion("CERPICH is null");
            return (Criteria) this;
        }

        public Criteria andCerpichIsNotNull() {
            addCriterion("CERPICH is not null");
            return (Criteria) this;
        }

        public Criteria andCerpichEqualTo(String value) {
            addCriterion("CERPICH =", value, "cerpich");
            return (Criteria) this;
        }

        public Criteria andCerpichNotEqualTo(String value) {
            addCriterion("CERPICH <>", value, "cerpich");
            return (Criteria) this;
        }

        public Criteria andCerpichGreaterThan(String value) {
            addCriterion("CERPICH >", value, "cerpich");
            return (Criteria) this;
        }

        public Criteria andCerpichGreaterThanOrEqualTo(String value) {
            addCriterion("CERPICH >=", value, "cerpich");
            return (Criteria) this;
        }

        public Criteria andCerpichLessThan(String value) {
            addCriterion("CERPICH <", value, "cerpich");
            return (Criteria) this;
        }

        public Criteria andCerpichLessThanOrEqualTo(String value) {
            addCriterion("CERPICH <=", value, "cerpich");
            return (Criteria) this;
        }

        public Criteria andCerpichLike(String value) {
            addCriterion("CERPICH like", value, "cerpich");
            return (Criteria) this;
        }

        public Criteria andCerpichNotLike(String value) {
            addCriterion("CERPICH not like", value, "cerpich");
            return (Criteria) this;
        }

        public Criteria andCerpichIn(List<String> values) {
            addCriterion("CERPICH in", values, "cerpich");
            return (Criteria) this;
        }

        public Criteria andCerpichNotIn(List<String> values) {
            addCriterion("CERPICH not in", values, "cerpich");
            return (Criteria) this;
        }

        public Criteria andCerpichBetween(String value1, String value2) {
            addCriterion("CERPICH between", value1, value2, "cerpich");
            return (Criteria) this;
        }

        public Criteria andCerpichNotBetween(String value1, String value2) {
            addCriterion("CERPICH not between", value1, value2, "cerpich");
            return (Criteria) this;
        }

        public Criteria andValidDateIsNull() {
            addCriterion("VALID_DATE is null");
            return (Criteria) this;
        }

        public Criteria andValidDateIsNotNull() {
            addCriterion("VALID_DATE is not null");
            return (Criteria) this;
        }

        public Criteria andValidDateEqualTo(String value) {
            addCriterion("VALID_DATE =", value, "validDate");
            return (Criteria) this;
        }

        public Criteria andValidDateNotEqualTo(String value) {
            addCriterion("VALID_DATE <>", value, "validDate");
            return (Criteria) this;
        }

        public Criteria andValidDateGreaterThan(String value) {
            addCriterion("VALID_DATE >", value, "validDate");
            return (Criteria) this;
        }

        public Criteria andValidDateGreaterThanOrEqualTo(String value) {
            addCriterion("VALID_DATE >=", value, "validDate");
            return (Criteria) this;
        }

        public Criteria andValidDateLessThan(String value) {
            addCriterion("VALID_DATE <", value, "validDate");
            return (Criteria) this;
        }

        public Criteria andValidDateLessThanOrEqualTo(String value) {
            addCriterion("VALID_DATE <=", value, "validDate");
            return (Criteria) this;
        }

        public Criteria andValidDateLike(String value) {
            addCriterion("VALID_DATE like", value, "validDate");
            return (Criteria) this;
        }

        public Criteria andValidDateNotLike(String value) {
            addCriterion("VALID_DATE not like", value, "validDate");
            return (Criteria) this;
        }

        public Criteria andValidDateIn(List<String> values) {
            addCriterion("VALID_DATE in", values, "validDate");
            return (Criteria) this;
        }

        public Criteria andValidDateNotIn(List<String> values) {
            addCriterion("VALID_DATE not in", values, "validDate");
            return (Criteria) this;
        }

        public Criteria andValidDateBetween(String value1, String value2) {
            addCriterion("VALID_DATE between", value1, value2, "validDate");
            return (Criteria) this;
        }

        public Criteria andValidDateNotBetween(String value1, String value2) {
            addCriterion("VALID_DATE not between", value1, value2, "validDate");
            return (Criteria) this;
        }

        public Criteria andAppealTypeIsNull() {
            addCriterion("APPEAL_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andAppealTypeIsNotNull() {
            addCriterion("APPEAL_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andAppealTypeEqualTo(String value) {
            addCriterion("APPEAL_TYPE =", value, "appealType");
            return (Criteria) this;
        }

        public Criteria andAppealTypeNotEqualTo(String value) {
            addCriterion("APPEAL_TYPE <>", value, "appealType");
            return (Criteria) this;
        }

        public Criteria andAppealTypeGreaterThan(String value) {
            addCriterion("APPEAL_TYPE >", value, "appealType");
            return (Criteria) this;
        }

        public Criteria andAppealTypeGreaterThanOrEqualTo(String value) {
            addCriterion("APPEAL_TYPE >=", value, "appealType");
            return (Criteria) this;
        }

        public Criteria andAppealTypeLessThan(String value) {
            addCriterion("APPEAL_TYPE <", value, "appealType");
            return (Criteria) this;
        }

        public Criteria andAppealTypeLessThanOrEqualTo(String value) {
            addCriterion("APPEAL_TYPE <=", value, "appealType");
            return (Criteria) this;
        }

        public Criteria andAppealTypeLike(String value) {
            addCriterion("APPEAL_TYPE like", value, "appealType");
            return (Criteria) this;
        }

        public Criteria andAppealTypeNotLike(String value) {
            addCriterion("APPEAL_TYPE not like", value, "appealType");
            return (Criteria) this;
        }

        public Criteria andAppealTypeIn(List<String> values) {
            addCriterion("APPEAL_TYPE in", values, "appealType");
            return (Criteria) this;
        }

        public Criteria andAppealTypeNotIn(List<String> values) {
            addCriterion("APPEAL_TYPE not in", values, "appealType");
            return (Criteria) this;
        }

        public Criteria andAppealTypeBetween(String value1, String value2) {
            addCriterion("APPEAL_TYPE between", value1, value2, "appealType");
            return (Criteria) this;
        }

        public Criteria andAppealTypeNotBetween(String value1, String value2) {
            addCriterion("APPEAL_TYPE not between", value1, value2, "appealType");
            return (Criteria) this;
        }

        public Criteria andAppealReasonIsNull() {
            addCriterion("APPEAL_REASON is null");
            return (Criteria) this;
        }

        public Criteria andAppealReasonIsNotNull() {
            addCriterion("APPEAL_REASON is not null");
            return (Criteria) this;
        }

        public Criteria andAppealReasonEqualTo(String value) {
            addCriterion("APPEAL_REASON =", value, "appealReason");
            return (Criteria) this;
        }

        public Criteria andAppealReasonNotEqualTo(String value) {
            addCriterion("APPEAL_REASON <>", value, "appealReason");
            return (Criteria) this;
        }

        public Criteria andAppealReasonGreaterThan(String value) {
            addCriterion("APPEAL_REASON >", value, "appealReason");
            return (Criteria) this;
        }

        public Criteria andAppealReasonGreaterThanOrEqualTo(String value) {
            addCriterion("APPEAL_REASON >=", value, "appealReason");
            return (Criteria) this;
        }

        public Criteria andAppealReasonLessThan(String value) {
            addCriterion("APPEAL_REASON <", value, "appealReason");
            return (Criteria) this;
        }

        public Criteria andAppealReasonLessThanOrEqualTo(String value) {
            addCriterion("APPEAL_REASON <=", value, "appealReason");
            return (Criteria) this;
        }

        public Criteria andAppealReasonLike(String value) {
            addCriterion("APPEAL_REASON like", value, "appealReason");
            return (Criteria) this;
        }

        public Criteria andAppealReasonNotLike(String value) {
            addCriterion("APPEAL_REASON not like", value, "appealReason");
            return (Criteria) this;
        }

        public Criteria andAppealReasonIn(List<String> values) {
            addCriterion("APPEAL_REASON in", values, "appealReason");
            return (Criteria) this;
        }

        public Criteria andAppealReasonNotIn(List<String> values) {
            addCriterion("APPEAL_REASON not in", values, "appealReason");
            return (Criteria) this;
        }

        public Criteria andAppealReasonBetween(String value1, String value2) {
            addCriterion("APPEAL_REASON between", value1, value2, "appealReason");
            return (Criteria) this;
        }

        public Criteria andAppealReasonNotBetween(String value1, String value2) {
            addCriterion("APPEAL_REASON not between", value1, value2, "appealReason");
            return (Criteria) this;
        }

        public Criteria andPostScriptIsNull() {
            addCriterion("POST_SCRIPT is null");
            return (Criteria) this;
        }

        public Criteria andPostScriptIsNotNull() {
            addCriterion("POST_SCRIPT is not null");
            return (Criteria) this;
        }

        public Criteria andPostScriptEqualTo(String value) {
            addCriterion("POST_SCRIPT =", value, "postScript");
            return (Criteria) this;
        }

        public Criteria andPostScriptNotEqualTo(String value) {
            addCriterion("POST_SCRIPT <>", value, "postScript");
            return (Criteria) this;
        }

        public Criteria andPostScriptGreaterThan(String value) {
            addCriterion("POST_SCRIPT >", value, "postScript");
            return (Criteria) this;
        }

        public Criteria andPostScriptGreaterThanOrEqualTo(String value) {
            addCriterion("POST_SCRIPT >=", value, "postScript");
            return (Criteria) this;
        }

        public Criteria andPostScriptLessThan(String value) {
            addCriterion("POST_SCRIPT <", value, "postScript");
            return (Criteria) this;
        }

        public Criteria andPostScriptLessThanOrEqualTo(String value) {
            addCriterion("POST_SCRIPT <=", value, "postScript");
            return (Criteria) this;
        }

        public Criteria andPostScriptLike(String value) {
            addCriterion("POST_SCRIPT like", value, "postScript");
            return (Criteria) this;
        }

        public Criteria andPostScriptNotLike(String value) {
            addCriterion("POST_SCRIPT not like", value, "postScript");
            return (Criteria) this;
        }

        public Criteria andPostScriptIn(List<String> values) {
            addCriterion("POST_SCRIPT in", values, "postScript");
            return (Criteria) this;
        }

        public Criteria andPostScriptNotIn(List<String> values) {
            addCriterion("POST_SCRIPT not in", values, "postScript");
            return (Criteria) this;
        }

        public Criteria andPostScriptBetween(String value1, String value2) {
            addCriterion("POST_SCRIPT between", value1, value2, "postScript");
            return (Criteria) this;
        }

        public Criteria andPostScriptNotBetween(String value1, String value2) {
            addCriterion("POST_SCRIPT not between", value1, value2, "postScript");
            return (Criteria) this;
        }

        public Criteria andIssuePlaceIsNull() {
            addCriterion("ISSUE_PLACE is null");
            return (Criteria) this;
        }

        public Criteria andIssuePlaceIsNotNull() {
            addCriterion("ISSUE_PLACE is not null");
            return (Criteria) this;
        }

        public Criteria andIssuePlaceEqualTo(String value) {
            addCriterion("ISSUE_PLACE =", value, "issuePlace");
            return (Criteria) this;
        }

        public Criteria andIssuePlaceNotEqualTo(String value) {
            addCriterion("ISSUE_PLACE <>", value, "issuePlace");
            return (Criteria) this;
        }

        public Criteria andIssuePlaceGreaterThan(String value) {
            addCriterion("ISSUE_PLACE >", value, "issuePlace");
            return (Criteria) this;
        }

        public Criteria andIssuePlaceGreaterThanOrEqualTo(String value) {
            addCriterion("ISSUE_PLACE >=", value, "issuePlace");
            return (Criteria) this;
        }

        public Criteria andIssuePlaceLessThan(String value) {
            addCriterion("ISSUE_PLACE <", value, "issuePlace");
            return (Criteria) this;
        }

        public Criteria andIssuePlaceLessThanOrEqualTo(String value) {
            addCriterion("ISSUE_PLACE <=", value, "issuePlace");
            return (Criteria) this;
        }

        public Criteria andIssuePlaceLike(String value) {
            addCriterion("ISSUE_PLACE like", value, "issuePlace");
            return (Criteria) this;
        }

        public Criteria andIssuePlaceNotLike(String value) {
            addCriterion("ISSUE_PLACE not like", value, "issuePlace");
            return (Criteria) this;
        }

        public Criteria andIssuePlaceIn(List<String> values) {
            addCriterion("ISSUE_PLACE in", values, "issuePlace");
            return (Criteria) this;
        }

        public Criteria andIssuePlaceNotIn(List<String> values) {
            addCriterion("ISSUE_PLACE not in", values, "issuePlace");
            return (Criteria) this;
        }

        public Criteria andIssuePlaceBetween(String value1, String value2) {
            addCriterion("ISSUE_PLACE between", value1, value2, "issuePlace");
            return (Criteria) this;
        }

        public Criteria andIssuePlaceNotBetween(String value1, String value2) {
            addCriterion("ISSUE_PLACE not between", value1, value2, "issuePlace");
            return (Criteria) this;
        }

        public Criteria andEntNameIsNull() {
            addCriterion("ENT_NAME is null");
            return (Criteria) this;
        }

        public Criteria andEntNameIsNotNull() {
            addCriterion("ENT_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andEntNameEqualTo(String value) {
            addCriterion("ENT_NAME =", value, "entName");
            return (Criteria) this;
        }

        public Criteria andEntNameNotEqualTo(String value) {
            addCriterion("ENT_NAME <>", value, "entName");
            return (Criteria) this;
        }

        public Criteria andEntNameGreaterThan(String value) {
            addCriterion("ENT_NAME >", value, "entName");
            return (Criteria) this;
        }

        public Criteria andEntNameGreaterThanOrEqualTo(String value) {
            addCriterion("ENT_NAME >=", value, "entName");
            return (Criteria) this;
        }

        public Criteria andEntNameLessThan(String value) {
            addCriterion("ENT_NAME <", value, "entName");
            return (Criteria) this;
        }

        public Criteria andEntNameLessThanOrEqualTo(String value) {
            addCriterion("ENT_NAME <=", value, "entName");
            return (Criteria) this;
        }

        public Criteria andEntNameLike(String value) {
            addCriterion("ENT_NAME like", value, "entName");
            return (Criteria) this;
        }

        public Criteria andEntNameNotLike(String value) {
            addCriterion("ENT_NAME not like", value, "entName");
            return (Criteria) this;
        }

        public Criteria andEntNameIn(List<String> values) {
            addCriterion("ENT_NAME in", values, "entName");
            return (Criteria) this;
        }

        public Criteria andEntNameNotIn(List<String> values) {
            addCriterion("ENT_NAME not in", values, "entName");
            return (Criteria) this;
        }

        public Criteria andEntNameBetween(String value1, String value2) {
            addCriterion("ENT_NAME between", value1, value2, "entName");
            return (Criteria) this;
        }

        public Criteria andEntNameNotBetween(String value1, String value2) {
            addCriterion("ENT_NAME not between", value1, value2, "entName");
            return (Criteria) this;
        }

        public Criteria andEntRegAddrIsNull() {
            addCriterion("ENT_REG_ADDR is null");
            return (Criteria) this;
        }

        public Criteria andEntRegAddrIsNotNull() {
            addCriterion("ENT_REG_ADDR is not null");
            return (Criteria) this;
        }

        public Criteria andEntRegAddrEqualTo(String value) {
            addCriterion("ENT_REG_ADDR =", value, "entRegAddr");
            return (Criteria) this;
        }

        public Criteria andEntRegAddrNotEqualTo(String value) {
            addCriterion("ENT_REG_ADDR <>", value, "entRegAddr");
            return (Criteria) this;
        }

        public Criteria andEntRegAddrGreaterThan(String value) {
            addCriterion("ENT_REG_ADDR >", value, "entRegAddr");
            return (Criteria) this;
        }

        public Criteria andEntRegAddrGreaterThanOrEqualTo(String value) {
            addCriterion("ENT_REG_ADDR >=", value, "entRegAddr");
            return (Criteria) this;
        }

        public Criteria andEntRegAddrLessThan(String value) {
            addCriterion("ENT_REG_ADDR <", value, "entRegAddr");
            return (Criteria) this;
        }

        public Criteria andEntRegAddrLessThanOrEqualTo(String value) {
            addCriterion("ENT_REG_ADDR <=", value, "entRegAddr");
            return (Criteria) this;
        }

        public Criteria andEntRegAddrLike(String value) {
            addCriterion("ENT_REG_ADDR like", value, "entRegAddr");
            return (Criteria) this;
        }

        public Criteria andEntRegAddrNotLike(String value) {
            addCriterion("ENT_REG_ADDR not like", value, "entRegAddr");
            return (Criteria) this;
        }

        public Criteria andEntRegAddrIn(List<String> values) {
            addCriterion("ENT_REG_ADDR in", values, "entRegAddr");
            return (Criteria) this;
        }

        public Criteria andEntRegAddrNotIn(List<String> values) {
            addCriterion("ENT_REG_ADDR not in", values, "entRegAddr");
            return (Criteria) this;
        }

        public Criteria andEntRegAddrBetween(String value1, String value2) {
            addCriterion("ENT_REG_ADDR between", value1, value2, "entRegAddr");
            return (Criteria) this;
        }

        public Criteria andEntRegAddrNotBetween(String value1, String value2) {
            addCriterion("ENT_REG_ADDR not between", value1, value2, "entRegAddr");
            return (Criteria) this;
        }

        public Criteria andEntTypeIsNull() {
            addCriterion("ENT_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andEntTypeIsNotNull() {
            addCriterion("ENT_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andEntTypeEqualTo(String value) {
            addCriterion("ENT_TYPE =", value, "entType");
            return (Criteria) this;
        }

        public Criteria andEntTypeNotEqualTo(String value) {
            addCriterion("ENT_TYPE <>", value, "entType");
            return (Criteria) this;
        }

        public Criteria andEntTypeGreaterThan(String value) {
            addCriterion("ENT_TYPE >", value, "entType");
            return (Criteria) this;
        }

        public Criteria andEntTypeGreaterThanOrEqualTo(String value) {
            addCriterion("ENT_TYPE >=", value, "entType");
            return (Criteria) this;
        }

        public Criteria andEntTypeLessThan(String value) {
            addCriterion("ENT_TYPE <", value, "entType");
            return (Criteria) this;
        }

        public Criteria andEntTypeLessThanOrEqualTo(String value) {
            addCriterion("ENT_TYPE <=", value, "entType");
            return (Criteria) this;
        }

        public Criteria andEntTypeLike(String value) {
            addCriterion("ENT_TYPE like", value, "entType");
            return (Criteria) this;
        }

        public Criteria andEntTypeNotLike(String value) {
            addCriterion("ENT_TYPE not like", value, "entType");
            return (Criteria) this;
        }

        public Criteria andEntTypeIn(List<String> values) {
            addCriterion("ENT_TYPE in", values, "entType");
            return (Criteria) this;
        }

        public Criteria andEntTypeNotIn(List<String> values) {
            addCriterion("ENT_TYPE not in", values, "entType");
            return (Criteria) this;
        }

        public Criteria andEntTypeBetween(String value1, String value2) {
            addCriterion("ENT_TYPE between", value1, value2, "entType");
            return (Criteria) this;
        }

        public Criteria andEntTypeNotBetween(String value1, String value2) {
            addCriterion("ENT_TYPE not between", value1, value2, "entType");
            return (Criteria) this;
        }

        public Criteria andEntBuildDateIsNull() {
            addCriterion("ENT_BUILD_DATE is null");
            return (Criteria) this;
        }

        public Criteria andEntBuildDateIsNotNull() {
            addCriterion("ENT_BUILD_DATE is not null");
            return (Criteria) this;
        }

        public Criteria andEntBuildDateEqualTo(Date value) {
            addCriterion("ENT_BUILD_DATE =", value, "entBuildDate");
            return (Criteria) this;
        }

        public Criteria andEntBuildDateNotEqualTo(Date value) {
            addCriterion("ENT_BUILD_DATE <>", value, "entBuildDate");
            return (Criteria) this;
        }

        public Criteria andEntBuildDateGreaterThan(Date value) {
            addCriterion("ENT_BUILD_DATE >", value, "entBuildDate");
            return (Criteria) this;
        }

        public Criteria andEntBuildDateGreaterThanOrEqualTo(Date value) {
            addCriterion("ENT_BUILD_DATE >=", value, "entBuildDate");
            return (Criteria) this;
        }

        public Criteria andEntBuildDateLessThan(Date value) {
            addCriterion("ENT_BUILD_DATE <", value, "entBuildDate");
            return (Criteria) this;
        }

        public Criteria andEntBuildDateLessThanOrEqualTo(Date value) {
            addCriterion("ENT_BUILD_DATE <=", value, "entBuildDate");
            return (Criteria) this;
        }

        public Criteria andEntBuildDateIn(List<Date> values) {
            addCriterion("ENT_BUILD_DATE in", values, "entBuildDate");
            return (Criteria) this;
        }

        public Criteria andEntBuildDateNotIn(List<Date> values) {
            addCriterion("ENT_BUILD_DATE not in", values, "entBuildDate");
            return (Criteria) this;
        }

        public Criteria andEntBuildDateBetween(Date value1, Date value2) {
            addCriterion("ENT_BUILD_DATE between", value1, value2, "entBuildDate");
            return (Criteria) this;
        }

        public Criteria andEntBuildDateNotBetween(Date value1, Date value2) {
            addCriterion("ENT_BUILD_DATE not between", value1, value2, "entBuildDate");
            return (Criteria) this;
        }

        public Criteria andApplyDateIsNull() {
            addCriterion("APPLY_DATE is null");
            return (Criteria) this;
        }

        public Criteria andApplyDateIsNotNull() {
            addCriterion("APPLY_DATE is not null");
            return (Criteria) this;
        }

        public Criteria andApplyDateEqualTo(Date value) {
            addCriterion("APPLY_DATE =", value, "applyDate");
            return (Criteria) this;
        }

        public Criteria andApplyDateNotEqualTo(Date value) {
            addCriterion("APPLY_DATE <>", value, "applyDate");
            return (Criteria) this;
        }

        public Criteria andApplyDateGreaterThan(Date value) {
            addCriterion("APPLY_DATE >", value, "applyDate");
            return (Criteria) this;
        }

        public Criteria andApplyDateGreaterThanOrEqualTo(Date value) {
            addCriterion("APPLY_DATE >=", value, "applyDate");
            return (Criteria) this;
        }

        public Criteria andApplyDateLessThan(Date value) {
            addCriterion("APPLY_DATE <", value, "applyDate");
            return (Criteria) this;
        }

        public Criteria andApplyDateLessThanOrEqualTo(Date value) {
            addCriterion("APPLY_DATE <=", value, "applyDate");
            return (Criteria) this;
        }

        public Criteria andApplyDateIn(List<Date> values) {
            addCriterion("APPLY_DATE in", values, "applyDate");
            return (Criteria) this;
        }

        public Criteria andApplyDateNotIn(List<Date> values) {
            addCriterion("APPLY_DATE not in", values, "applyDate");
            return (Criteria) this;
        }

        public Criteria andApplyDateBetween(Date value1, Date value2) {
            addCriterion("APPLY_DATE between", value1, value2, "applyDate");
            return (Criteria) this;
        }

        public Criteria andApplyDateNotBetween(Date value1, Date value2) {
            addCriterion("APPLY_DATE not between", value1, value2, "applyDate");
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

        public Criteria andApprContentIsNull() {
            addCriterion("APPR_CONTENT is null");
            return (Criteria) this;
        }

        public Criteria andApprContentIsNotNull() {
            addCriterion("APPR_CONTENT is not null");
            return (Criteria) this;
        }

        public Criteria andApprContentEqualTo(String value) {
            addCriterion("APPR_CONTENT =", value, "apprContent");
            return (Criteria) this;
        }

        public Criteria andApprContentNotEqualTo(String value) {
            addCriterion("APPR_CONTENT <>", value, "apprContent");
            return (Criteria) this;
        }

        public Criteria andApprContentGreaterThan(String value) {
            addCriterion("APPR_CONTENT >", value, "apprContent");
            return (Criteria) this;
        }

        public Criteria andApprContentGreaterThanOrEqualTo(String value) {
            addCriterion("APPR_CONTENT >=", value, "apprContent");
            return (Criteria) this;
        }

        public Criteria andApprContentLessThan(String value) {
            addCriterion("APPR_CONTENT <", value, "apprContent");
            return (Criteria) this;
        }

        public Criteria andApprContentLessThanOrEqualTo(String value) {
            addCriterion("APPR_CONTENT <=", value, "apprContent");
            return (Criteria) this;
        }

        public Criteria andApprContentLike(String value) {
            addCriterion("APPR_CONTENT like", value, "apprContent");
            return (Criteria) this;
        }

        public Criteria andApprContentNotLike(String value) {
            addCriterion("APPR_CONTENT not like", value, "apprContent");
            return (Criteria) this;
        }

        public Criteria andApprContentIn(List<String> values) {
            addCriterion("APPR_CONTENT in", values, "apprContent");
            return (Criteria) this;
        }

        public Criteria andApprContentNotIn(List<String> values) {
            addCriterion("APPR_CONTENT not in", values, "apprContent");
            return (Criteria) this;
        }

        public Criteria andApprContentBetween(String value1, String value2) {
            addCriterion("APPR_CONTENT between", value1, value2, "apprContent");
            return (Criteria) this;
        }

        public Criteria andApprContentNotBetween(String value1, String value2) {
            addCriterion("APPR_CONTENT not between", value1, value2, "apprContent");
            return (Criteria) this;
        }

        public Criteria andApprDateIsNull() {
            addCriterion("APPR_DATE is null");
            return (Criteria) this;
        }

        public Criteria andApprDateIsNotNull() {
            addCriterion("APPR_DATE is not null");
            return (Criteria) this;
        }

        public Criteria andApprDateEqualTo(Date value) {
            addCriterion("APPR_DATE =", value, "apprDate");
            return (Criteria) this;
        }

        public Criteria andApprDateNotEqualTo(Date value) {
            addCriterion("APPR_DATE <>", value, "apprDate");
            return (Criteria) this;
        }

        public Criteria andApprDateGreaterThan(Date value) {
            addCriterion("APPR_DATE >", value, "apprDate");
            return (Criteria) this;
        }

        public Criteria andApprDateGreaterThanOrEqualTo(Date value) {
            addCriterion("APPR_DATE >=", value, "apprDate");
            return (Criteria) this;
        }

        public Criteria andApprDateLessThan(Date value) {
            addCriterion("APPR_DATE <", value, "apprDate");
            return (Criteria) this;
        }

        public Criteria andApprDateLessThanOrEqualTo(Date value) {
            addCriterion("APPR_DATE <=", value, "apprDate");
            return (Criteria) this;
        }

        public Criteria andApprDateIn(List<Date> values) {
            addCriterion("APPR_DATE in", values, "apprDate");
            return (Criteria) this;
        }

        public Criteria andApprDateNotIn(List<Date> values) {
            addCriterion("APPR_DATE not in", values, "apprDate");
            return (Criteria) this;
        }

        public Criteria andApprDateBetween(Date value1, Date value2) {
            addCriterion("APPR_DATE between", value1, value2, "apprDate");
            return (Criteria) this;
        }

        public Criteria andApprDateNotBetween(Date value1, Date value2) {
            addCriterion("APPR_DATE not between", value1, value2, "apprDate");
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

        public Criteria andUpdatebyIsNull() {
            addCriterion("UPDATEBY is null");
            return (Criteria) this;
        }

        public Criteria andUpdatebyIsNotNull() {
            addCriterion("UPDATEBY is not null");
            return (Criteria) this;
        }

        public Criteria andUpdatebyEqualTo(String value) {
            addCriterion("UPDATEBY =", value, "updateby");
            return (Criteria) this;
        }

        public Criteria andUpdatebyNotEqualTo(String value) {
            addCriterion("UPDATEBY <>", value, "updateby");
            return (Criteria) this;
        }

        public Criteria andUpdatebyGreaterThan(String value) {
            addCriterion("UPDATEBY >", value, "updateby");
            return (Criteria) this;
        }

        public Criteria andUpdatebyGreaterThanOrEqualTo(String value) {
            addCriterion("UPDATEBY >=", value, "updateby");
            return (Criteria) this;
        }

        public Criteria andUpdatebyLessThan(String value) {
            addCriterion("UPDATEBY <", value, "updateby");
            return (Criteria) this;
        }

        public Criteria andUpdatebyLessThanOrEqualTo(String value) {
            addCriterion("UPDATEBY <=", value, "updateby");
            return (Criteria) this;
        }

        public Criteria andUpdatebyLike(String value) {
            addCriterion("UPDATEBY like", value, "updateby");
            return (Criteria) this;
        }

        public Criteria andUpdatebyNotLike(String value) {
            addCriterion("UPDATEBY not like", value, "updateby");
            return (Criteria) this;
        }

        public Criteria andUpdatebyIn(List<String> values) {
            addCriterion("UPDATEBY in", values, "updateby");
            return (Criteria) this;
        }

        public Criteria andUpdatebyNotIn(List<String> values) {
            addCriterion("UPDATEBY not in", values, "updateby");
            return (Criteria) this;
        }

        public Criteria andUpdatebyBetween(String value1, String value2) {
            addCriterion("UPDATEBY between", value1, value2, "updateby");
            return (Criteria) this;
        }

        public Criteria andUpdatebyNotBetween(String value1, String value2) {
            addCriterion("UPDATEBY not between", value1, value2, "updateby");
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