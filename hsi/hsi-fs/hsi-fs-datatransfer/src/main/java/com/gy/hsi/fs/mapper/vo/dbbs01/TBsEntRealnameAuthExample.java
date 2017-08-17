package com.gy.hsi.fs.mapper.vo.dbbs01;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.gy.hsi.fs.mapper.vo.AbstractPage;

public class TBsEntRealnameAuthExample extends AbstractPage {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TBsEntRealnameAuthExample() {
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

        public Criteria andEntResNoIsNull() {
            addCriterion("ENT_RES_NO is null");
            return (Criteria) this;
        }

        public Criteria andEntResNoIsNotNull() {
            addCriterion("ENT_RES_NO is not null");
            return (Criteria) this;
        }

        public Criteria andEntResNoEqualTo(String value) {
            addCriterion("ENT_RES_NO =", value, "entResNo");
            return (Criteria) this;
        }

        public Criteria andEntResNoNotEqualTo(String value) {
            addCriterion("ENT_RES_NO <>", value, "entResNo");
            return (Criteria) this;
        }

        public Criteria andEntResNoGreaterThan(String value) {
            addCriterion("ENT_RES_NO >", value, "entResNo");
            return (Criteria) this;
        }

        public Criteria andEntResNoGreaterThanOrEqualTo(String value) {
            addCriterion("ENT_RES_NO >=", value, "entResNo");
            return (Criteria) this;
        }

        public Criteria andEntResNoLessThan(String value) {
            addCriterion("ENT_RES_NO <", value, "entResNo");
            return (Criteria) this;
        }

        public Criteria andEntResNoLessThanOrEqualTo(String value) {
            addCriterion("ENT_RES_NO <=", value, "entResNo");
            return (Criteria) this;
        }

        public Criteria andEntResNoLike(String value) {
            addCriterion("ENT_RES_NO like", value, "entResNo");
            return (Criteria) this;
        }

        public Criteria andEntResNoNotLike(String value) {
            addCriterion("ENT_RES_NO not like", value, "entResNo");
            return (Criteria) this;
        }

        public Criteria andEntResNoIn(List<String> values) {
            addCriterion("ENT_RES_NO in", values, "entResNo");
            return (Criteria) this;
        }

        public Criteria andEntResNoNotIn(List<String> values) {
            addCriterion("ENT_RES_NO not in", values, "entResNo");
            return (Criteria) this;
        }

        public Criteria andEntResNoBetween(String value1, String value2) {
            addCriterion("ENT_RES_NO between", value1, value2, "entResNo");
            return (Criteria) this;
        }

        public Criteria andEntResNoNotBetween(String value1, String value2) {
            addCriterion("ENT_RES_NO not between", value1, value2, "entResNo");
            return (Criteria) this;
        }

        public Criteria andEntCustIdIsNull() {
            addCriterion("ENT_CUST_ID is null");
            return (Criteria) this;
        }

        public Criteria andEntCustIdIsNotNull() {
            addCriterion("ENT_CUST_ID is not null");
            return (Criteria) this;
        }

        public Criteria andEntCustIdEqualTo(String value) {
            addCriterion("ENT_CUST_ID =", value, "entCustId");
            return (Criteria) this;
        }

        public Criteria andEntCustIdNotEqualTo(String value) {
            addCriterion("ENT_CUST_ID <>", value, "entCustId");
            return (Criteria) this;
        }

        public Criteria andEntCustIdGreaterThan(String value) {
            addCriterion("ENT_CUST_ID >", value, "entCustId");
            return (Criteria) this;
        }

        public Criteria andEntCustIdGreaterThanOrEqualTo(String value) {
            addCriterion("ENT_CUST_ID >=", value, "entCustId");
            return (Criteria) this;
        }

        public Criteria andEntCustIdLessThan(String value) {
            addCriterion("ENT_CUST_ID <", value, "entCustId");
            return (Criteria) this;
        }

        public Criteria andEntCustIdLessThanOrEqualTo(String value) {
            addCriterion("ENT_CUST_ID <=", value, "entCustId");
            return (Criteria) this;
        }

        public Criteria andEntCustIdLike(String value) {
            addCriterion("ENT_CUST_ID like", value, "entCustId");
            return (Criteria) this;
        }

        public Criteria andEntCustIdNotLike(String value) {
            addCriterion("ENT_CUST_ID not like", value, "entCustId");
            return (Criteria) this;
        }

        public Criteria andEntCustIdIn(List<String> values) {
            addCriterion("ENT_CUST_ID in", values, "entCustId");
            return (Criteria) this;
        }

        public Criteria andEntCustIdNotIn(List<String> values) {
            addCriterion("ENT_CUST_ID not in", values, "entCustId");
            return (Criteria) this;
        }

        public Criteria andEntCustIdBetween(String value1, String value2) {
            addCriterion("ENT_CUST_ID between", value1, value2, "entCustId");
            return (Criteria) this;
        }

        public Criteria andEntCustIdNotBetween(String value1, String value2) {
            addCriterion("ENT_CUST_ID not between", value1, value2, "entCustId");
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

        public Criteria andEntCustNameIsNull() {
            addCriterion("ENT_CUST_NAME is null");
            return (Criteria) this;
        }

        public Criteria andEntCustNameIsNotNull() {
            addCriterion("ENT_CUST_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andEntCustNameEqualTo(String value) {
            addCriterion("ENT_CUST_NAME =", value, "entCustName");
            return (Criteria) this;
        }

        public Criteria andEntCustNameNotEqualTo(String value) {
            addCriterion("ENT_CUST_NAME <>", value, "entCustName");
            return (Criteria) this;
        }

        public Criteria andEntCustNameGreaterThan(String value) {
            addCriterion("ENT_CUST_NAME >", value, "entCustName");
            return (Criteria) this;
        }

        public Criteria andEntCustNameGreaterThanOrEqualTo(String value) {
            addCriterion("ENT_CUST_NAME >=", value, "entCustName");
            return (Criteria) this;
        }

        public Criteria andEntCustNameLessThan(String value) {
            addCriterion("ENT_CUST_NAME <", value, "entCustName");
            return (Criteria) this;
        }

        public Criteria andEntCustNameLessThanOrEqualTo(String value) {
            addCriterion("ENT_CUST_NAME <=", value, "entCustName");
            return (Criteria) this;
        }

        public Criteria andEntCustNameLike(String value) {
            addCriterion("ENT_CUST_NAME like", value, "entCustName");
            return (Criteria) this;
        }

        public Criteria andEntCustNameNotLike(String value) {
            addCriterion("ENT_CUST_NAME not like", value, "entCustName");
            return (Criteria) this;
        }

        public Criteria andEntCustNameIn(List<String> values) {
            addCriterion("ENT_CUST_NAME in", values, "entCustName");
            return (Criteria) this;
        }

        public Criteria andEntCustNameNotIn(List<String> values) {
            addCriterion("ENT_CUST_NAME not in", values, "entCustName");
            return (Criteria) this;
        }

        public Criteria andEntCustNameBetween(String value1, String value2) {
            addCriterion("ENT_CUST_NAME between", value1, value2, "entCustName");
            return (Criteria) this;
        }

        public Criteria andEntCustNameNotBetween(String value1, String value2) {
            addCriterion("ENT_CUST_NAME not between", value1, value2, "entCustName");
            return (Criteria) this;
        }

        public Criteria andEntCustNameEnIsNull() {
            addCriterion("ENT_CUST_NAME_EN is null");
            return (Criteria) this;
        }

        public Criteria andEntCustNameEnIsNotNull() {
            addCriterion("ENT_CUST_NAME_EN is not null");
            return (Criteria) this;
        }

        public Criteria andEntCustNameEnEqualTo(String value) {
            addCriterion("ENT_CUST_NAME_EN =", value, "entCustNameEn");
            return (Criteria) this;
        }

        public Criteria andEntCustNameEnNotEqualTo(String value) {
            addCriterion("ENT_CUST_NAME_EN <>", value, "entCustNameEn");
            return (Criteria) this;
        }

        public Criteria andEntCustNameEnGreaterThan(String value) {
            addCriterion("ENT_CUST_NAME_EN >", value, "entCustNameEn");
            return (Criteria) this;
        }

        public Criteria andEntCustNameEnGreaterThanOrEqualTo(String value) {
            addCriterion("ENT_CUST_NAME_EN >=", value, "entCustNameEn");
            return (Criteria) this;
        }

        public Criteria andEntCustNameEnLessThan(String value) {
            addCriterion("ENT_CUST_NAME_EN <", value, "entCustNameEn");
            return (Criteria) this;
        }

        public Criteria andEntCustNameEnLessThanOrEqualTo(String value) {
            addCriterion("ENT_CUST_NAME_EN <=", value, "entCustNameEn");
            return (Criteria) this;
        }

        public Criteria andEntCustNameEnLike(String value) {
            addCriterion("ENT_CUST_NAME_EN like", value, "entCustNameEn");
            return (Criteria) this;
        }

        public Criteria andEntCustNameEnNotLike(String value) {
            addCriterion("ENT_CUST_NAME_EN not like", value, "entCustNameEn");
            return (Criteria) this;
        }

        public Criteria andEntCustNameEnIn(List<String> values) {
            addCriterion("ENT_CUST_NAME_EN in", values, "entCustNameEn");
            return (Criteria) this;
        }

        public Criteria andEntCustNameEnNotIn(List<String> values) {
            addCriterion("ENT_CUST_NAME_EN not in", values, "entCustNameEn");
            return (Criteria) this;
        }

        public Criteria andEntCustNameEnBetween(String value1, String value2) {
            addCriterion("ENT_CUST_NAME_EN between", value1, value2, "entCustNameEn");
            return (Criteria) this;
        }

        public Criteria andEntCustNameEnNotBetween(String value1, String value2) {
            addCriterion("ENT_CUST_NAME_EN not between", value1, value2, "entCustNameEn");
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

        public Criteria andProvinceNoIsNull() {
            addCriterion("PROVINCE_NO is null");
            return (Criteria) this;
        }

        public Criteria andProvinceNoIsNotNull() {
            addCriterion("PROVINCE_NO is not null");
            return (Criteria) this;
        }

        public Criteria andProvinceNoEqualTo(String value) {
            addCriterion("PROVINCE_NO =", value, "provinceNo");
            return (Criteria) this;
        }

        public Criteria andProvinceNoNotEqualTo(String value) {
            addCriterion("PROVINCE_NO <>", value, "provinceNo");
            return (Criteria) this;
        }

        public Criteria andProvinceNoGreaterThan(String value) {
            addCriterion("PROVINCE_NO >", value, "provinceNo");
            return (Criteria) this;
        }

        public Criteria andProvinceNoGreaterThanOrEqualTo(String value) {
            addCriterion("PROVINCE_NO >=", value, "provinceNo");
            return (Criteria) this;
        }

        public Criteria andProvinceNoLessThan(String value) {
            addCriterion("PROVINCE_NO <", value, "provinceNo");
            return (Criteria) this;
        }

        public Criteria andProvinceNoLessThanOrEqualTo(String value) {
            addCriterion("PROVINCE_NO <=", value, "provinceNo");
            return (Criteria) this;
        }

        public Criteria andProvinceNoLike(String value) {
            addCriterion("PROVINCE_NO like", value, "provinceNo");
            return (Criteria) this;
        }

        public Criteria andProvinceNoNotLike(String value) {
            addCriterion("PROVINCE_NO not like", value, "provinceNo");
            return (Criteria) this;
        }

        public Criteria andProvinceNoIn(List<String> values) {
            addCriterion("PROVINCE_NO in", values, "provinceNo");
            return (Criteria) this;
        }

        public Criteria andProvinceNoNotIn(List<String> values) {
            addCriterion("PROVINCE_NO not in", values, "provinceNo");
            return (Criteria) this;
        }

        public Criteria andProvinceNoBetween(String value1, String value2) {
            addCriterion("PROVINCE_NO between", value1, value2, "provinceNo");
            return (Criteria) this;
        }

        public Criteria andProvinceNoNotBetween(String value1, String value2) {
            addCriterion("PROVINCE_NO not between", value1, value2, "provinceNo");
            return (Criteria) this;
        }

        public Criteria andCityNoIsNull() {
            addCriterion("CITY_NO is null");
            return (Criteria) this;
        }

        public Criteria andCityNoIsNotNull() {
            addCriterion("CITY_NO is not null");
            return (Criteria) this;
        }

        public Criteria andCityNoEqualTo(String value) {
            addCriterion("CITY_NO =", value, "cityNo");
            return (Criteria) this;
        }

        public Criteria andCityNoNotEqualTo(String value) {
            addCriterion("CITY_NO <>", value, "cityNo");
            return (Criteria) this;
        }

        public Criteria andCityNoGreaterThan(String value) {
            addCriterion("CITY_NO >", value, "cityNo");
            return (Criteria) this;
        }

        public Criteria andCityNoGreaterThanOrEqualTo(String value) {
            addCriterion("CITY_NO >=", value, "cityNo");
            return (Criteria) this;
        }

        public Criteria andCityNoLessThan(String value) {
            addCriterion("CITY_NO <", value, "cityNo");
            return (Criteria) this;
        }

        public Criteria andCityNoLessThanOrEqualTo(String value) {
            addCriterion("CITY_NO <=", value, "cityNo");
            return (Criteria) this;
        }

        public Criteria andCityNoLike(String value) {
            addCriterion("CITY_NO like", value, "cityNo");
            return (Criteria) this;
        }

        public Criteria andCityNoNotLike(String value) {
            addCriterion("CITY_NO not like", value, "cityNo");
            return (Criteria) this;
        }

        public Criteria andCityNoIn(List<String> values) {
            addCriterion("CITY_NO in", values, "cityNo");
            return (Criteria) this;
        }

        public Criteria andCityNoNotIn(List<String> values) {
            addCriterion("CITY_NO not in", values, "cityNo");
            return (Criteria) this;
        }

        public Criteria andCityNoBetween(String value1, String value2) {
            addCriterion("CITY_NO between", value1, value2, "cityNo");
            return (Criteria) this;
        }

        public Criteria andCityNoNotBetween(String value1, String value2) {
            addCriterion("CITY_NO not between", value1, value2, "cityNo");
            return (Criteria) this;
        }

        public Criteria andEntAddrIsNull() {
            addCriterion("ENT_ADDR is null");
            return (Criteria) this;
        }

        public Criteria andEntAddrIsNotNull() {
            addCriterion("ENT_ADDR is not null");
            return (Criteria) this;
        }

        public Criteria andEntAddrEqualTo(String value) {
            addCriterion("ENT_ADDR =", value, "entAddr");
            return (Criteria) this;
        }

        public Criteria andEntAddrNotEqualTo(String value) {
            addCriterion("ENT_ADDR <>", value, "entAddr");
            return (Criteria) this;
        }

        public Criteria andEntAddrGreaterThan(String value) {
            addCriterion("ENT_ADDR >", value, "entAddr");
            return (Criteria) this;
        }

        public Criteria andEntAddrGreaterThanOrEqualTo(String value) {
            addCriterion("ENT_ADDR >=", value, "entAddr");
            return (Criteria) this;
        }

        public Criteria andEntAddrLessThan(String value) {
            addCriterion("ENT_ADDR <", value, "entAddr");
            return (Criteria) this;
        }

        public Criteria andEntAddrLessThanOrEqualTo(String value) {
            addCriterion("ENT_ADDR <=", value, "entAddr");
            return (Criteria) this;
        }

        public Criteria andEntAddrLike(String value) {
            addCriterion("ENT_ADDR like", value, "entAddr");
            return (Criteria) this;
        }

        public Criteria andEntAddrNotLike(String value) {
            addCriterion("ENT_ADDR not like", value, "entAddr");
            return (Criteria) this;
        }

        public Criteria andEntAddrIn(List<String> values) {
            addCriterion("ENT_ADDR in", values, "entAddr");
            return (Criteria) this;
        }

        public Criteria andEntAddrNotIn(List<String> values) {
            addCriterion("ENT_ADDR not in", values, "entAddr");
            return (Criteria) this;
        }

        public Criteria andEntAddrBetween(String value1, String value2) {
            addCriterion("ENT_ADDR between", value1, value2, "entAddr");
            return (Criteria) this;
        }

        public Criteria andEntAddrNotBetween(String value1, String value2) {
            addCriterion("ENT_ADDR not between", value1, value2, "entAddr");
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

        public Criteria andLegalNameIsNull() {
            addCriterion("LEGAL_NAME is null");
            return (Criteria) this;
        }

        public Criteria andLegalNameIsNotNull() {
            addCriterion("LEGAL_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andLegalNameEqualTo(String value) {
            addCriterion("LEGAL_NAME =", value, "legalName");
            return (Criteria) this;
        }

        public Criteria andLegalNameNotEqualTo(String value) {
            addCriterion("LEGAL_NAME <>", value, "legalName");
            return (Criteria) this;
        }

        public Criteria andLegalNameGreaterThan(String value) {
            addCriterion("LEGAL_NAME >", value, "legalName");
            return (Criteria) this;
        }

        public Criteria andLegalNameGreaterThanOrEqualTo(String value) {
            addCriterion("LEGAL_NAME >=", value, "legalName");
            return (Criteria) this;
        }

        public Criteria andLegalNameLessThan(String value) {
            addCriterion("LEGAL_NAME <", value, "legalName");
            return (Criteria) this;
        }

        public Criteria andLegalNameLessThanOrEqualTo(String value) {
            addCriterion("LEGAL_NAME <=", value, "legalName");
            return (Criteria) this;
        }

        public Criteria andLegalNameLike(String value) {
            addCriterion("LEGAL_NAME like", value, "legalName");
            return (Criteria) this;
        }

        public Criteria andLegalNameNotLike(String value) {
            addCriterion("LEGAL_NAME not like", value, "legalName");
            return (Criteria) this;
        }

        public Criteria andLegalNameIn(List<String> values) {
            addCriterion("LEGAL_NAME in", values, "legalName");
            return (Criteria) this;
        }

        public Criteria andLegalNameNotIn(List<String> values) {
            addCriterion("LEGAL_NAME not in", values, "legalName");
            return (Criteria) this;
        }

        public Criteria andLegalNameBetween(String value1, String value2) {
            addCriterion("LEGAL_NAME between", value1, value2, "legalName");
            return (Criteria) this;
        }

        public Criteria andLegalNameNotBetween(String value1, String value2) {
            addCriterion("LEGAL_NAME not between", value1, value2, "legalName");
            return (Criteria) this;
        }

        public Criteria andLegalCreTypeIsNull() {
            addCriterion("LEGAL_CRE_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andLegalCreTypeIsNotNull() {
            addCriterion("LEGAL_CRE_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andLegalCreTypeEqualTo(Short value) {
            addCriterion("LEGAL_CRE_TYPE =", value, "legalCreType");
            return (Criteria) this;
        }

        public Criteria andLegalCreTypeNotEqualTo(Short value) {
            addCriterion("LEGAL_CRE_TYPE <>", value, "legalCreType");
            return (Criteria) this;
        }

        public Criteria andLegalCreTypeGreaterThan(Short value) {
            addCriterion("LEGAL_CRE_TYPE >", value, "legalCreType");
            return (Criteria) this;
        }

        public Criteria andLegalCreTypeGreaterThanOrEqualTo(Short value) {
            addCriterion("LEGAL_CRE_TYPE >=", value, "legalCreType");
            return (Criteria) this;
        }

        public Criteria andLegalCreTypeLessThan(Short value) {
            addCriterion("LEGAL_CRE_TYPE <", value, "legalCreType");
            return (Criteria) this;
        }

        public Criteria andLegalCreTypeLessThanOrEqualTo(Short value) {
            addCriterion("LEGAL_CRE_TYPE <=", value, "legalCreType");
            return (Criteria) this;
        }

        public Criteria andLegalCreTypeIn(List<Short> values) {
            addCriterion("LEGAL_CRE_TYPE in", values, "legalCreType");
            return (Criteria) this;
        }

        public Criteria andLegalCreTypeNotIn(List<Short> values) {
            addCriterion("LEGAL_CRE_TYPE not in", values, "legalCreType");
            return (Criteria) this;
        }

        public Criteria andLegalCreTypeBetween(Short value1, Short value2) {
            addCriterion("LEGAL_CRE_TYPE between", value1, value2, "legalCreType");
            return (Criteria) this;
        }

        public Criteria andLegalCreTypeNotBetween(Short value1, Short value2) {
            addCriterion("LEGAL_CRE_TYPE not between", value1, value2, "legalCreType");
            return (Criteria) this;
        }

        public Criteria andLegalCreNoIsNull() {
            addCriterion("LEGAL_CRE_NO is null");
            return (Criteria) this;
        }

        public Criteria andLegalCreNoIsNotNull() {
            addCriterion("LEGAL_CRE_NO is not null");
            return (Criteria) this;
        }

        public Criteria andLegalCreNoEqualTo(String value) {
            addCriterion("LEGAL_CRE_NO =", value, "legalCreNo");
            return (Criteria) this;
        }

        public Criteria andLegalCreNoNotEqualTo(String value) {
            addCriterion("LEGAL_CRE_NO <>", value, "legalCreNo");
            return (Criteria) this;
        }

        public Criteria andLegalCreNoGreaterThan(String value) {
            addCriterion("LEGAL_CRE_NO >", value, "legalCreNo");
            return (Criteria) this;
        }

        public Criteria andLegalCreNoGreaterThanOrEqualTo(String value) {
            addCriterion("LEGAL_CRE_NO >=", value, "legalCreNo");
            return (Criteria) this;
        }

        public Criteria andLegalCreNoLessThan(String value) {
            addCriterion("LEGAL_CRE_NO <", value, "legalCreNo");
            return (Criteria) this;
        }

        public Criteria andLegalCreNoLessThanOrEqualTo(String value) {
            addCriterion("LEGAL_CRE_NO <=", value, "legalCreNo");
            return (Criteria) this;
        }

        public Criteria andLegalCreNoLike(String value) {
            addCriterion("LEGAL_CRE_NO like", value, "legalCreNo");
            return (Criteria) this;
        }

        public Criteria andLegalCreNoNotLike(String value) {
            addCriterion("LEGAL_CRE_NO not like", value, "legalCreNo");
            return (Criteria) this;
        }

        public Criteria andLegalCreNoIn(List<String> values) {
            addCriterion("LEGAL_CRE_NO in", values, "legalCreNo");
            return (Criteria) this;
        }

        public Criteria andLegalCreNoNotIn(List<String> values) {
            addCriterion("LEGAL_CRE_NO not in", values, "legalCreNo");
            return (Criteria) this;
        }

        public Criteria andLegalCreNoBetween(String value1, String value2) {
            addCriterion("LEGAL_CRE_NO between", value1, value2, "legalCreNo");
            return (Criteria) this;
        }

        public Criteria andLegalCreNoNotBetween(String value1, String value2) {
            addCriterion("LEGAL_CRE_NO not between", value1, value2, "legalCreNo");
            return (Criteria) this;
        }

        public Criteria andLicenseNoIsNull() {
            addCriterion("LICENSE_NO is null");
            return (Criteria) this;
        }

        public Criteria andLicenseNoIsNotNull() {
            addCriterion("LICENSE_NO is not null");
            return (Criteria) this;
        }

        public Criteria andLicenseNoEqualTo(String value) {
            addCriterion("LICENSE_NO =", value, "licenseNo");
            return (Criteria) this;
        }

        public Criteria andLicenseNoNotEqualTo(String value) {
            addCriterion("LICENSE_NO <>", value, "licenseNo");
            return (Criteria) this;
        }

        public Criteria andLicenseNoGreaterThan(String value) {
            addCriterion("LICENSE_NO >", value, "licenseNo");
            return (Criteria) this;
        }

        public Criteria andLicenseNoGreaterThanOrEqualTo(String value) {
            addCriterion("LICENSE_NO >=", value, "licenseNo");
            return (Criteria) this;
        }

        public Criteria andLicenseNoLessThan(String value) {
            addCriterion("LICENSE_NO <", value, "licenseNo");
            return (Criteria) this;
        }

        public Criteria andLicenseNoLessThanOrEqualTo(String value) {
            addCriterion("LICENSE_NO <=", value, "licenseNo");
            return (Criteria) this;
        }

        public Criteria andLicenseNoLike(String value) {
            addCriterion("LICENSE_NO like", value, "licenseNo");
            return (Criteria) this;
        }

        public Criteria andLicenseNoNotLike(String value) {
            addCriterion("LICENSE_NO not like", value, "licenseNo");
            return (Criteria) this;
        }

        public Criteria andLicenseNoIn(List<String> values) {
            addCriterion("LICENSE_NO in", values, "licenseNo");
            return (Criteria) this;
        }

        public Criteria andLicenseNoNotIn(List<String> values) {
            addCriterion("LICENSE_NO not in", values, "licenseNo");
            return (Criteria) this;
        }

        public Criteria andLicenseNoBetween(String value1, String value2) {
            addCriterion("LICENSE_NO between", value1, value2, "licenseNo");
            return (Criteria) this;
        }

        public Criteria andLicenseNoNotBetween(String value1, String value2) {
            addCriterion("LICENSE_NO not between", value1, value2, "licenseNo");
            return (Criteria) this;
        }

        public Criteria andOrgNoIsNull() {
            addCriterion("ORG_NO is null");
            return (Criteria) this;
        }

        public Criteria andOrgNoIsNotNull() {
            addCriterion("ORG_NO is not null");
            return (Criteria) this;
        }

        public Criteria andOrgNoEqualTo(String value) {
            addCriterion("ORG_NO =", value, "orgNo");
            return (Criteria) this;
        }

        public Criteria andOrgNoNotEqualTo(String value) {
            addCriterion("ORG_NO <>", value, "orgNo");
            return (Criteria) this;
        }

        public Criteria andOrgNoGreaterThan(String value) {
            addCriterion("ORG_NO >", value, "orgNo");
            return (Criteria) this;
        }

        public Criteria andOrgNoGreaterThanOrEqualTo(String value) {
            addCriterion("ORG_NO >=", value, "orgNo");
            return (Criteria) this;
        }

        public Criteria andOrgNoLessThan(String value) {
            addCriterion("ORG_NO <", value, "orgNo");
            return (Criteria) this;
        }

        public Criteria andOrgNoLessThanOrEqualTo(String value) {
            addCriterion("ORG_NO <=", value, "orgNo");
            return (Criteria) this;
        }

        public Criteria andOrgNoLike(String value) {
            addCriterion("ORG_NO like", value, "orgNo");
            return (Criteria) this;
        }

        public Criteria andOrgNoNotLike(String value) {
            addCriterion("ORG_NO not like", value, "orgNo");
            return (Criteria) this;
        }

        public Criteria andOrgNoIn(List<String> values) {
            addCriterion("ORG_NO in", values, "orgNo");
            return (Criteria) this;
        }

        public Criteria andOrgNoNotIn(List<String> values) {
            addCriterion("ORG_NO not in", values, "orgNo");
            return (Criteria) this;
        }

        public Criteria andOrgNoBetween(String value1, String value2) {
            addCriterion("ORG_NO between", value1, value2, "orgNo");
            return (Criteria) this;
        }

        public Criteria andOrgNoNotBetween(String value1, String value2) {
            addCriterion("ORG_NO not between", value1, value2, "orgNo");
            return (Criteria) this;
        }

        public Criteria andTaxNoIsNull() {
            addCriterion("TAX_NO is null");
            return (Criteria) this;
        }

        public Criteria andTaxNoIsNotNull() {
            addCriterion("TAX_NO is not null");
            return (Criteria) this;
        }

        public Criteria andTaxNoEqualTo(String value) {
            addCriterion("TAX_NO =", value, "taxNo");
            return (Criteria) this;
        }

        public Criteria andTaxNoNotEqualTo(String value) {
            addCriterion("TAX_NO <>", value, "taxNo");
            return (Criteria) this;
        }

        public Criteria andTaxNoGreaterThan(String value) {
            addCriterion("TAX_NO >", value, "taxNo");
            return (Criteria) this;
        }

        public Criteria andTaxNoGreaterThanOrEqualTo(String value) {
            addCriterion("TAX_NO >=", value, "taxNo");
            return (Criteria) this;
        }

        public Criteria andTaxNoLessThan(String value) {
            addCriterion("TAX_NO <", value, "taxNo");
            return (Criteria) this;
        }

        public Criteria andTaxNoLessThanOrEqualTo(String value) {
            addCriterion("TAX_NO <=", value, "taxNo");
            return (Criteria) this;
        }

        public Criteria andTaxNoLike(String value) {
            addCriterion("TAX_NO like", value, "taxNo");
            return (Criteria) this;
        }

        public Criteria andTaxNoNotLike(String value) {
            addCriterion("TAX_NO not like", value, "taxNo");
            return (Criteria) this;
        }

        public Criteria andTaxNoIn(List<String> values) {
            addCriterion("TAX_NO in", values, "taxNo");
            return (Criteria) this;
        }

        public Criteria andTaxNoNotIn(List<String> values) {
            addCriterion("TAX_NO not in", values, "taxNo");
            return (Criteria) this;
        }

        public Criteria andTaxNoBetween(String value1, String value2) {
            addCriterion("TAX_NO between", value1, value2, "taxNo");
            return (Criteria) this;
        }

        public Criteria andTaxNoNotBetween(String value1, String value2) {
            addCriterion("TAX_NO not between", value1, value2, "taxNo");
            return (Criteria) this;
        }

        public Criteria andLrcFacePicIsNull() {
            addCriterion("LRC_FACE_PIC is null");
            return (Criteria) this;
        }

        public Criteria andLrcFacePicIsNotNull() {
            addCriterion("LRC_FACE_PIC is not null");
            return (Criteria) this;
        }

        public Criteria andLrcFacePicEqualTo(String value) {
            addCriterion("LRC_FACE_PIC =", value, "lrcFacePic");
            return (Criteria) this;
        }

        public Criteria andLrcFacePicNotEqualTo(String value) {
            addCriterion("LRC_FACE_PIC <>", value, "lrcFacePic");
            return (Criteria) this;
        }

        public Criteria andLrcFacePicGreaterThan(String value) {
            addCriterion("LRC_FACE_PIC >", value, "lrcFacePic");
            return (Criteria) this;
        }

        public Criteria andLrcFacePicGreaterThanOrEqualTo(String value) {
            addCriterion("LRC_FACE_PIC >=", value, "lrcFacePic");
            return (Criteria) this;
        }

        public Criteria andLrcFacePicLessThan(String value) {
            addCriterion("LRC_FACE_PIC <", value, "lrcFacePic");
            return (Criteria) this;
        }

        public Criteria andLrcFacePicLessThanOrEqualTo(String value) {
            addCriterion("LRC_FACE_PIC <=", value, "lrcFacePic");
            return (Criteria) this;
        }

        public Criteria andLrcFacePicLike(String value) {
            addCriterion("LRC_FACE_PIC like", value, "lrcFacePic");
            return (Criteria) this;
        }

        public Criteria andLrcFacePicNotLike(String value) {
            addCriterion("LRC_FACE_PIC not like", value, "lrcFacePic");
            return (Criteria) this;
        }

        public Criteria andLrcFacePicIn(List<String> values) {
            addCriterion("LRC_FACE_PIC in", values, "lrcFacePic");
            return (Criteria) this;
        }

        public Criteria andLrcFacePicNotIn(List<String> values) {
            addCriterion("LRC_FACE_PIC not in", values, "lrcFacePic");
            return (Criteria) this;
        }

        public Criteria andLrcFacePicBetween(String value1, String value2) {
            addCriterion("LRC_FACE_PIC between", value1, value2, "lrcFacePic");
            return (Criteria) this;
        }

        public Criteria andLrcFacePicNotBetween(String value1, String value2) {
            addCriterion("LRC_FACE_PIC not between", value1, value2, "lrcFacePic");
            return (Criteria) this;
        }

        public Criteria andLrcBackPicIsNull() {
            addCriterion("LRC_BACK_PIC is null");
            return (Criteria) this;
        }

        public Criteria andLrcBackPicIsNotNull() {
            addCriterion("LRC_BACK_PIC is not null");
            return (Criteria) this;
        }

        public Criteria andLrcBackPicEqualTo(String value) {
            addCriterion("LRC_BACK_PIC =", value, "lrcBackPic");
            return (Criteria) this;
        }

        public Criteria andLrcBackPicNotEqualTo(String value) {
            addCriterion("LRC_BACK_PIC <>", value, "lrcBackPic");
            return (Criteria) this;
        }

        public Criteria andLrcBackPicGreaterThan(String value) {
            addCriterion("LRC_BACK_PIC >", value, "lrcBackPic");
            return (Criteria) this;
        }

        public Criteria andLrcBackPicGreaterThanOrEqualTo(String value) {
            addCriterion("LRC_BACK_PIC >=", value, "lrcBackPic");
            return (Criteria) this;
        }

        public Criteria andLrcBackPicLessThan(String value) {
            addCriterion("LRC_BACK_PIC <", value, "lrcBackPic");
            return (Criteria) this;
        }

        public Criteria andLrcBackPicLessThanOrEqualTo(String value) {
            addCriterion("LRC_BACK_PIC <=", value, "lrcBackPic");
            return (Criteria) this;
        }

        public Criteria andLrcBackPicLike(String value) {
            addCriterion("LRC_BACK_PIC like", value, "lrcBackPic");
            return (Criteria) this;
        }

        public Criteria andLrcBackPicNotLike(String value) {
            addCriterion("LRC_BACK_PIC not like", value, "lrcBackPic");
            return (Criteria) this;
        }

        public Criteria andLrcBackPicIn(List<String> values) {
            addCriterion("LRC_BACK_PIC in", values, "lrcBackPic");
            return (Criteria) this;
        }

        public Criteria andLrcBackPicNotIn(List<String> values) {
            addCriterion("LRC_BACK_PIC not in", values, "lrcBackPic");
            return (Criteria) this;
        }

        public Criteria andLrcBackPicBetween(String value1, String value2) {
            addCriterion("LRC_BACK_PIC between", value1, value2, "lrcBackPic");
            return (Criteria) this;
        }

        public Criteria andLrcBackPicNotBetween(String value1, String value2) {
            addCriterion("LRC_BACK_PIC not between", value1, value2, "lrcBackPic");
            return (Criteria) this;
        }

        public Criteria andLicensePicIsNull() {
            addCriterion("LICENSE_PIC is null");
            return (Criteria) this;
        }

        public Criteria andLicensePicIsNotNull() {
            addCriterion("LICENSE_PIC is not null");
            return (Criteria) this;
        }

        public Criteria andLicensePicEqualTo(String value) {
            addCriterion("LICENSE_PIC =", value, "licensePic");
            return (Criteria) this;
        }

        public Criteria andLicensePicNotEqualTo(String value) {
            addCriterion("LICENSE_PIC <>", value, "licensePic");
            return (Criteria) this;
        }

        public Criteria andLicensePicGreaterThan(String value) {
            addCriterion("LICENSE_PIC >", value, "licensePic");
            return (Criteria) this;
        }

        public Criteria andLicensePicGreaterThanOrEqualTo(String value) {
            addCriterion("LICENSE_PIC >=", value, "licensePic");
            return (Criteria) this;
        }

        public Criteria andLicensePicLessThan(String value) {
            addCriterion("LICENSE_PIC <", value, "licensePic");
            return (Criteria) this;
        }

        public Criteria andLicensePicLessThanOrEqualTo(String value) {
            addCriterion("LICENSE_PIC <=", value, "licensePic");
            return (Criteria) this;
        }

        public Criteria andLicensePicLike(String value) {
            addCriterion("LICENSE_PIC like", value, "licensePic");
            return (Criteria) this;
        }

        public Criteria andLicensePicNotLike(String value) {
            addCriterion("LICENSE_PIC not like", value, "licensePic");
            return (Criteria) this;
        }

        public Criteria andLicensePicIn(List<String> values) {
            addCriterion("LICENSE_PIC in", values, "licensePic");
            return (Criteria) this;
        }

        public Criteria andLicensePicNotIn(List<String> values) {
            addCriterion("LICENSE_PIC not in", values, "licensePic");
            return (Criteria) this;
        }

        public Criteria andLicensePicBetween(String value1, String value2) {
            addCriterion("LICENSE_PIC between", value1, value2, "licensePic");
            return (Criteria) this;
        }

        public Criteria andLicensePicNotBetween(String value1, String value2) {
            addCriterion("LICENSE_PIC not between", value1, value2, "licensePic");
            return (Criteria) this;
        }

        public Criteria andOrgPicIsNull() {
            addCriterion("ORG_PIC is null");
            return (Criteria) this;
        }

        public Criteria andOrgPicIsNotNull() {
            addCriterion("ORG_PIC is not null");
            return (Criteria) this;
        }

        public Criteria andOrgPicEqualTo(String value) {
            addCriterion("ORG_PIC =", value, "orgPic");
            return (Criteria) this;
        }

        public Criteria andOrgPicNotEqualTo(String value) {
            addCriterion("ORG_PIC <>", value, "orgPic");
            return (Criteria) this;
        }

        public Criteria andOrgPicGreaterThan(String value) {
            addCriterion("ORG_PIC >", value, "orgPic");
            return (Criteria) this;
        }

        public Criteria andOrgPicGreaterThanOrEqualTo(String value) {
            addCriterion("ORG_PIC >=", value, "orgPic");
            return (Criteria) this;
        }

        public Criteria andOrgPicLessThan(String value) {
            addCriterion("ORG_PIC <", value, "orgPic");
            return (Criteria) this;
        }

        public Criteria andOrgPicLessThanOrEqualTo(String value) {
            addCriterion("ORG_PIC <=", value, "orgPic");
            return (Criteria) this;
        }

        public Criteria andOrgPicLike(String value) {
            addCriterion("ORG_PIC like", value, "orgPic");
            return (Criteria) this;
        }

        public Criteria andOrgPicNotLike(String value) {
            addCriterion("ORG_PIC not like", value, "orgPic");
            return (Criteria) this;
        }

        public Criteria andOrgPicIn(List<String> values) {
            addCriterion("ORG_PIC in", values, "orgPic");
            return (Criteria) this;
        }

        public Criteria andOrgPicNotIn(List<String> values) {
            addCriterion("ORG_PIC not in", values, "orgPic");
            return (Criteria) this;
        }

        public Criteria andOrgPicBetween(String value1, String value2) {
            addCriterion("ORG_PIC between", value1, value2, "orgPic");
            return (Criteria) this;
        }

        public Criteria andOrgPicNotBetween(String value1, String value2) {
            addCriterion("ORG_PIC not between", value1, value2, "orgPic");
            return (Criteria) this;
        }

        public Criteria andTaxPicIsNull() {
            addCriterion("TAX_PIC is null");
            return (Criteria) this;
        }

        public Criteria andTaxPicIsNotNull() {
            addCriterion("TAX_PIC is not null");
            return (Criteria) this;
        }

        public Criteria andTaxPicEqualTo(String value) {
            addCriterion("TAX_PIC =", value, "taxPic");
            return (Criteria) this;
        }

        public Criteria andTaxPicNotEqualTo(String value) {
            addCriterion("TAX_PIC <>", value, "taxPic");
            return (Criteria) this;
        }

        public Criteria andTaxPicGreaterThan(String value) {
            addCriterion("TAX_PIC >", value, "taxPic");
            return (Criteria) this;
        }

        public Criteria andTaxPicGreaterThanOrEqualTo(String value) {
            addCriterion("TAX_PIC >=", value, "taxPic");
            return (Criteria) this;
        }

        public Criteria andTaxPicLessThan(String value) {
            addCriterion("TAX_PIC <", value, "taxPic");
            return (Criteria) this;
        }

        public Criteria andTaxPicLessThanOrEqualTo(String value) {
            addCriterion("TAX_PIC <=", value, "taxPic");
            return (Criteria) this;
        }

        public Criteria andTaxPicLike(String value) {
            addCriterion("TAX_PIC like", value, "taxPic");
            return (Criteria) this;
        }

        public Criteria andTaxPicNotLike(String value) {
            addCriterion("TAX_PIC not like", value, "taxPic");
            return (Criteria) this;
        }

        public Criteria andTaxPicIn(List<String> values) {
            addCriterion("TAX_PIC in", values, "taxPic");
            return (Criteria) this;
        }

        public Criteria andTaxPicNotIn(List<String> values) {
            addCriterion("TAX_PIC not in", values, "taxPic");
            return (Criteria) this;
        }

        public Criteria andTaxPicBetween(String value1, String value2) {
            addCriterion("TAX_PIC between", value1, value2, "taxPic");
            return (Criteria) this;
        }

        public Criteria andTaxPicNotBetween(String value1, String value2) {
            addCriterion("TAX_PIC not between", value1, value2, "taxPic");
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