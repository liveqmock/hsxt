package com.gy.hsi.fs.mapper.vo.dbbs01;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.gy.hsi.fs.mapper.vo.AbstractPage;

public class TBsEntChangeInfoAppExample extends AbstractPage {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TBsEntChangeInfoAppExample() {
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

        public Criteria andImptappPicIsNull() {
            addCriterion("IMPTAPP_PIC is null");
            return (Criteria) this;
        }

        public Criteria andImptappPicIsNotNull() {
            addCriterion("IMPTAPP_PIC is not null");
            return (Criteria) this;
        }

        public Criteria andImptappPicEqualTo(String value) {
            addCriterion("IMPTAPP_PIC =", value, "imptappPic");
            return (Criteria) this;
        }

        public Criteria andImptappPicNotEqualTo(String value) {
            addCriterion("IMPTAPP_PIC <>", value, "imptappPic");
            return (Criteria) this;
        }

        public Criteria andImptappPicGreaterThan(String value) {
            addCriterion("IMPTAPP_PIC >", value, "imptappPic");
            return (Criteria) this;
        }

        public Criteria andImptappPicGreaterThanOrEqualTo(String value) {
            addCriterion("IMPTAPP_PIC >=", value, "imptappPic");
            return (Criteria) this;
        }

        public Criteria andImptappPicLessThan(String value) {
            addCriterion("IMPTAPP_PIC <", value, "imptappPic");
            return (Criteria) this;
        }

        public Criteria andImptappPicLessThanOrEqualTo(String value) {
            addCriterion("IMPTAPP_PIC <=", value, "imptappPic");
            return (Criteria) this;
        }

        public Criteria andImptappPicLike(String value) {
            addCriterion("IMPTAPP_PIC like", value, "imptappPic");
            return (Criteria) this;
        }

        public Criteria andImptappPicNotLike(String value) {
            addCriterion("IMPTAPP_PIC not like", value, "imptappPic");
            return (Criteria) this;
        }

        public Criteria andImptappPicIn(List<String> values) {
            addCriterion("IMPTAPP_PIC in", values, "imptappPic");
            return (Criteria) this;
        }

        public Criteria andImptappPicNotIn(List<String> values) {
            addCriterion("IMPTAPP_PIC not in", values, "imptappPic");
            return (Criteria) this;
        }

        public Criteria andImptappPicBetween(String value1, String value2) {
            addCriterion("IMPTAPP_PIC between", value1, value2, "imptappPic");
            return (Criteria) this;
        }

        public Criteria andImptappPicNotBetween(String value1, String value2) {
            addCriterion("IMPTAPP_PIC not between", value1, value2, "imptappPic");
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

        public Criteria andApprRemarkIsNull() {
            addCriterion("APPR_REMARK is null");
            return (Criteria) this;
        }

        public Criteria andApprRemarkIsNotNull() {
            addCriterion("APPR_REMARK is not null");
            return (Criteria) this;
        }

        public Criteria andApprRemarkEqualTo(String value) {
            addCriterion("APPR_REMARK =", value, "apprRemark");
            return (Criteria) this;
        }

        public Criteria andApprRemarkNotEqualTo(String value) {
            addCriterion("APPR_REMARK <>", value, "apprRemark");
            return (Criteria) this;
        }

        public Criteria andApprRemarkGreaterThan(String value) {
            addCriterion("APPR_REMARK >", value, "apprRemark");
            return (Criteria) this;
        }

        public Criteria andApprRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("APPR_REMARK >=", value, "apprRemark");
            return (Criteria) this;
        }

        public Criteria andApprRemarkLessThan(String value) {
            addCriterion("APPR_REMARK <", value, "apprRemark");
            return (Criteria) this;
        }

        public Criteria andApprRemarkLessThanOrEqualTo(String value) {
            addCriterion("APPR_REMARK <=", value, "apprRemark");
            return (Criteria) this;
        }

        public Criteria andApprRemarkLike(String value) {
            addCriterion("APPR_REMARK like", value, "apprRemark");
            return (Criteria) this;
        }

        public Criteria andApprRemarkNotLike(String value) {
            addCriterion("APPR_REMARK not like", value, "apprRemark");
            return (Criteria) this;
        }

        public Criteria andApprRemarkIn(List<String> values) {
            addCriterion("APPR_REMARK in", values, "apprRemark");
            return (Criteria) this;
        }

        public Criteria andApprRemarkNotIn(List<String> values) {
            addCriterion("APPR_REMARK not in", values, "apprRemark");
            return (Criteria) this;
        }

        public Criteria andApprRemarkBetween(String value1, String value2) {
            addCriterion("APPR_REMARK between", value1, value2, "apprRemark");
            return (Criteria) this;
        }

        public Criteria andApprRemarkNotBetween(String value1, String value2) {
            addCriterion("APPR_REMARK not between", value1, value2, "apprRemark");
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