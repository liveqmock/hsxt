package com.gy.hsi.fs.mapper.vo.dbuc01;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.gy.hsi.fs.mapper.vo.AbstractPage;

public class TNetworkInfoExample extends AbstractPage {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TNetworkInfoExample() {
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

        public Criteria andNicknameIsNull() {
            addCriterion("NICKNAME is null");
            return (Criteria) this;
        }

        public Criteria andNicknameIsNotNull() {
            addCriterion("NICKNAME is not null");
            return (Criteria) this;
        }

        public Criteria andNicknameEqualTo(String value) {
            addCriterion("NICKNAME =", value, "nickname");
            return (Criteria) this;
        }

        public Criteria andNicknameNotEqualTo(String value) {
            addCriterion("NICKNAME <>", value, "nickname");
            return (Criteria) this;
        }

        public Criteria andNicknameGreaterThan(String value) {
            addCriterion("NICKNAME >", value, "nickname");
            return (Criteria) this;
        }

        public Criteria andNicknameGreaterThanOrEqualTo(String value) {
            addCriterion("NICKNAME >=", value, "nickname");
            return (Criteria) this;
        }

        public Criteria andNicknameLessThan(String value) {
            addCriterion("NICKNAME <", value, "nickname");
            return (Criteria) this;
        }

        public Criteria andNicknameLessThanOrEqualTo(String value) {
            addCriterion("NICKNAME <=", value, "nickname");
            return (Criteria) this;
        }

        public Criteria andNicknameLike(String value) {
            addCriterion("NICKNAME like", value, "nickname");
            return (Criteria) this;
        }

        public Criteria andNicknameNotLike(String value) {
            addCriterion("NICKNAME not like", value, "nickname");
            return (Criteria) this;
        }

        public Criteria andNicknameIn(List<String> values) {
            addCriterion("NICKNAME in", values, "nickname");
            return (Criteria) this;
        }

        public Criteria andNicknameNotIn(List<String> values) {
            addCriterion("NICKNAME not in", values, "nickname");
            return (Criteria) this;
        }

        public Criteria andNicknameBetween(String value1, String value2) {
            addCriterion("NICKNAME between", value1, value2, "nickname");
            return (Criteria) this;
        }

        public Criteria andNicknameNotBetween(String value1, String value2) {
            addCriterion("NICKNAME not between", value1, value2, "nickname");
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

        public Criteria andHeadShotIsNull() {
            addCriterion("HEAD_SHOT is null");
            return (Criteria) this;
        }

        public Criteria andHeadShotIsNotNull() {
            addCriterion("HEAD_SHOT is not null");
            return (Criteria) this;
        }

        public Criteria andHeadShotEqualTo(String value) {
            addCriterion("HEAD_SHOT =", value, "headShot");
            return (Criteria) this;
        }

        public Criteria andHeadShotNotEqualTo(String value) {
            addCriterion("HEAD_SHOT <>", value, "headShot");
            return (Criteria) this;
        }

        public Criteria andHeadShotGreaterThan(String value) {
            addCriterion("HEAD_SHOT >", value, "headShot");
            return (Criteria) this;
        }

        public Criteria andHeadShotGreaterThanOrEqualTo(String value) {
            addCriterion("HEAD_SHOT >=", value, "headShot");
            return (Criteria) this;
        }

        public Criteria andHeadShotLessThan(String value) {
            addCriterion("HEAD_SHOT <", value, "headShot");
            return (Criteria) this;
        }

        public Criteria andHeadShotLessThanOrEqualTo(String value) {
            addCriterion("HEAD_SHOT <=", value, "headShot");
            return (Criteria) this;
        }

        public Criteria andHeadShotLike(String value) {
            addCriterion("HEAD_SHOT like", value, "headShot");
            return (Criteria) this;
        }

        public Criteria andHeadShotNotLike(String value) {
            addCriterion("HEAD_SHOT not like", value, "headShot");
            return (Criteria) this;
        }

        public Criteria andHeadShotIn(List<String> values) {
            addCriterion("HEAD_SHOT in", values, "headShot");
            return (Criteria) this;
        }

        public Criteria andHeadShotNotIn(List<String> values) {
            addCriterion("HEAD_SHOT not in", values, "headShot");
            return (Criteria) this;
        }

        public Criteria andHeadShotBetween(String value1, String value2) {
            addCriterion("HEAD_SHOT between", value1, value2, "headShot");
            return (Criteria) this;
        }

        public Criteria andHeadShotNotBetween(String value1, String value2) {
            addCriterion("HEAD_SHOT not between", value1, value2, "headShot");
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

        public Criteria andAgeIsNull() {
            addCriterion("AGE is null");
            return (Criteria) this;
        }

        public Criteria andAgeIsNotNull() {
            addCriterion("AGE is not null");
            return (Criteria) this;
        }

        public Criteria andAgeEqualTo(Short value) {
            addCriterion("AGE =", value, "age");
            return (Criteria) this;
        }

        public Criteria andAgeNotEqualTo(Short value) {
            addCriterion("AGE <>", value, "age");
            return (Criteria) this;
        }

        public Criteria andAgeGreaterThan(Short value) {
            addCriterion("AGE >", value, "age");
            return (Criteria) this;
        }

        public Criteria andAgeGreaterThanOrEqualTo(Short value) {
            addCriterion("AGE >=", value, "age");
            return (Criteria) this;
        }

        public Criteria andAgeLessThan(Short value) {
            addCriterion("AGE <", value, "age");
            return (Criteria) this;
        }

        public Criteria andAgeLessThanOrEqualTo(Short value) {
            addCriterion("AGE <=", value, "age");
            return (Criteria) this;
        }

        public Criteria andAgeIn(List<Short> values) {
            addCriterion("AGE in", values, "age");
            return (Criteria) this;
        }

        public Criteria andAgeNotIn(List<Short> values) {
            addCriterion("AGE not in", values, "age");
            return (Criteria) this;
        }

        public Criteria andAgeBetween(Short value1, Short value2) {
            addCriterion("AGE between", value1, value2, "age");
            return (Criteria) this;
        }

        public Criteria andAgeNotBetween(Short value1, Short value2) {
            addCriterion("AGE not between", value1, value2, "age");
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

        public Criteria andAreaIsNull() {
            addCriterion("AREA is null");
            return (Criteria) this;
        }

        public Criteria andAreaIsNotNull() {
            addCriterion("AREA is not null");
            return (Criteria) this;
        }

        public Criteria andAreaEqualTo(String value) {
            addCriterion("AREA =", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaNotEqualTo(String value) {
            addCriterion("AREA <>", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaGreaterThan(String value) {
            addCriterion("AREA >", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaGreaterThanOrEqualTo(String value) {
            addCriterion("AREA >=", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaLessThan(String value) {
            addCriterion("AREA <", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaLessThanOrEqualTo(String value) {
            addCriterion("AREA <=", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaLike(String value) {
            addCriterion("AREA like", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaNotLike(String value) {
            addCriterion("AREA not like", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaIn(List<String> values) {
            addCriterion("AREA in", values, "area");
            return (Criteria) this;
        }

        public Criteria andAreaNotIn(List<String> values) {
            addCriterion("AREA not in", values, "area");
            return (Criteria) this;
        }

        public Criteria andAreaBetween(String value1, String value2) {
            addCriterion("AREA between", value1, value2, "area");
            return (Criteria) this;
        }

        public Criteria andAreaNotBetween(String value1, String value2) {
            addCriterion("AREA not between", value1, value2, "area");
            return (Criteria) this;
        }

        public Criteria andAddressIsNull() {
            addCriterion("ADDRESS is null");
            return (Criteria) this;
        }

        public Criteria andAddressIsNotNull() {
            addCriterion("ADDRESS is not null");
            return (Criteria) this;
        }

        public Criteria andAddressEqualTo(String value) {
            addCriterion("ADDRESS =", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotEqualTo(String value) {
            addCriterion("ADDRESS <>", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressGreaterThan(String value) {
            addCriterion("ADDRESS >", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressGreaterThanOrEqualTo(String value) {
            addCriterion("ADDRESS >=", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressLessThan(String value) {
            addCriterion("ADDRESS <", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressLessThanOrEqualTo(String value) {
            addCriterion("ADDRESS <=", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressLike(String value) {
            addCriterion("ADDRESS like", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotLike(String value) {
            addCriterion("ADDRESS not like", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressIn(List<String> values) {
            addCriterion("ADDRESS in", values, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotIn(List<String> values) {
            addCriterion("ADDRESS not in", values, "address");
            return (Criteria) this;
        }

        public Criteria andAddressBetween(String value1, String value2) {
            addCriterion("ADDRESS between", value1, value2, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotBetween(String value1, String value2) {
            addCriterion("ADDRESS not between", value1, value2, "address");
            return (Criteria) this;
        }

        public Criteria andIndividualSignIsNull() {
            addCriterion("INDIVIDUAL_SIGN is null");
            return (Criteria) this;
        }

        public Criteria andIndividualSignIsNotNull() {
            addCriterion("INDIVIDUAL_SIGN is not null");
            return (Criteria) this;
        }

        public Criteria andIndividualSignEqualTo(String value) {
            addCriterion("INDIVIDUAL_SIGN =", value, "individualSign");
            return (Criteria) this;
        }

        public Criteria andIndividualSignNotEqualTo(String value) {
            addCriterion("INDIVIDUAL_SIGN <>", value, "individualSign");
            return (Criteria) this;
        }

        public Criteria andIndividualSignGreaterThan(String value) {
            addCriterion("INDIVIDUAL_SIGN >", value, "individualSign");
            return (Criteria) this;
        }

        public Criteria andIndividualSignGreaterThanOrEqualTo(String value) {
            addCriterion("INDIVIDUAL_SIGN >=", value, "individualSign");
            return (Criteria) this;
        }

        public Criteria andIndividualSignLessThan(String value) {
            addCriterion("INDIVIDUAL_SIGN <", value, "individualSign");
            return (Criteria) this;
        }

        public Criteria andIndividualSignLessThanOrEqualTo(String value) {
            addCriterion("INDIVIDUAL_SIGN <=", value, "individualSign");
            return (Criteria) this;
        }

        public Criteria andIndividualSignLike(String value) {
            addCriterion("INDIVIDUAL_SIGN like", value, "individualSign");
            return (Criteria) this;
        }

        public Criteria andIndividualSignNotLike(String value) {
            addCriterion("INDIVIDUAL_SIGN not like", value, "individualSign");
            return (Criteria) this;
        }

        public Criteria andIndividualSignIn(List<String> values) {
            addCriterion("INDIVIDUAL_SIGN in", values, "individualSign");
            return (Criteria) this;
        }

        public Criteria andIndividualSignNotIn(List<String> values) {
            addCriterion("INDIVIDUAL_SIGN not in", values, "individualSign");
            return (Criteria) this;
        }

        public Criteria andIndividualSignBetween(String value1, String value2) {
            addCriterion("INDIVIDUAL_SIGN between", value1, value2, "individualSign");
            return (Criteria) this;
        }

        public Criteria andIndividualSignNotBetween(String value1, String value2) {
            addCriterion("INDIVIDUAL_SIGN not between", value1, value2, "individualSign");
            return (Criteria) this;
        }

        public Criteria andJobIsNull() {
            addCriterion("JOB is null");
            return (Criteria) this;
        }

        public Criteria andJobIsNotNull() {
            addCriterion("JOB is not null");
            return (Criteria) this;
        }

        public Criteria andJobEqualTo(String value) {
            addCriterion("JOB =", value, "job");
            return (Criteria) this;
        }

        public Criteria andJobNotEqualTo(String value) {
            addCriterion("JOB <>", value, "job");
            return (Criteria) this;
        }

        public Criteria andJobGreaterThan(String value) {
            addCriterion("JOB >", value, "job");
            return (Criteria) this;
        }

        public Criteria andJobGreaterThanOrEqualTo(String value) {
            addCriterion("JOB >=", value, "job");
            return (Criteria) this;
        }

        public Criteria andJobLessThan(String value) {
            addCriterion("JOB <", value, "job");
            return (Criteria) this;
        }

        public Criteria andJobLessThanOrEqualTo(String value) {
            addCriterion("JOB <=", value, "job");
            return (Criteria) this;
        }

        public Criteria andJobLike(String value) {
            addCriterion("JOB like", value, "job");
            return (Criteria) this;
        }

        public Criteria andJobNotLike(String value) {
            addCriterion("JOB not like", value, "job");
            return (Criteria) this;
        }

        public Criteria andJobIn(List<String> values) {
            addCriterion("JOB in", values, "job");
            return (Criteria) this;
        }

        public Criteria andJobNotIn(List<String> values) {
            addCriterion("JOB not in", values, "job");
            return (Criteria) this;
        }

        public Criteria andJobBetween(String value1, String value2) {
            addCriterion("JOB between", value1, value2, "job");
            return (Criteria) this;
        }

        public Criteria andJobNotBetween(String value1, String value2) {
            addCriterion("JOB not between", value1, value2, "job");
            return (Criteria) this;
        }

        public Criteria andHobbyIsNull() {
            addCriterion("HOBBY is null");
            return (Criteria) this;
        }

        public Criteria andHobbyIsNotNull() {
            addCriterion("HOBBY is not null");
            return (Criteria) this;
        }

        public Criteria andHobbyEqualTo(String value) {
            addCriterion("HOBBY =", value, "hobby");
            return (Criteria) this;
        }

        public Criteria andHobbyNotEqualTo(String value) {
            addCriterion("HOBBY <>", value, "hobby");
            return (Criteria) this;
        }

        public Criteria andHobbyGreaterThan(String value) {
            addCriterion("HOBBY >", value, "hobby");
            return (Criteria) this;
        }

        public Criteria andHobbyGreaterThanOrEqualTo(String value) {
            addCriterion("HOBBY >=", value, "hobby");
            return (Criteria) this;
        }

        public Criteria andHobbyLessThan(String value) {
            addCriterion("HOBBY <", value, "hobby");
            return (Criteria) this;
        }

        public Criteria andHobbyLessThanOrEqualTo(String value) {
            addCriterion("HOBBY <=", value, "hobby");
            return (Criteria) this;
        }

        public Criteria andHobbyLike(String value) {
            addCriterion("HOBBY like", value, "hobby");
            return (Criteria) this;
        }

        public Criteria andHobbyNotLike(String value) {
            addCriterion("HOBBY not like", value, "hobby");
            return (Criteria) this;
        }

        public Criteria andHobbyIn(List<String> values) {
            addCriterion("HOBBY in", values, "hobby");
            return (Criteria) this;
        }

        public Criteria andHobbyNotIn(List<String> values) {
            addCriterion("HOBBY not in", values, "hobby");
            return (Criteria) this;
        }

        public Criteria andHobbyBetween(String value1, String value2) {
            addCriterion("HOBBY between", value1, value2, "hobby");
            return (Criteria) this;
        }

        public Criteria andHobbyNotBetween(String value1, String value2) {
            addCriterion("HOBBY not between", value1, value2, "hobby");
            return (Criteria) this;
        }

        public Criteria andBloodIsNull() {
            addCriterion("BLOOD is null");
            return (Criteria) this;
        }

        public Criteria andBloodIsNotNull() {
            addCriterion("BLOOD is not null");
            return (Criteria) this;
        }

        public Criteria andBloodEqualTo(Short value) {
            addCriterion("BLOOD =", value, "blood");
            return (Criteria) this;
        }

        public Criteria andBloodNotEqualTo(Short value) {
            addCriterion("BLOOD <>", value, "blood");
            return (Criteria) this;
        }

        public Criteria andBloodGreaterThan(Short value) {
            addCriterion("BLOOD >", value, "blood");
            return (Criteria) this;
        }

        public Criteria andBloodGreaterThanOrEqualTo(Short value) {
            addCriterion("BLOOD >=", value, "blood");
            return (Criteria) this;
        }

        public Criteria andBloodLessThan(Short value) {
            addCriterion("BLOOD <", value, "blood");
            return (Criteria) this;
        }

        public Criteria andBloodLessThanOrEqualTo(Short value) {
            addCriterion("BLOOD <=", value, "blood");
            return (Criteria) this;
        }

        public Criteria andBloodIn(List<Short> values) {
            addCriterion("BLOOD in", values, "blood");
            return (Criteria) this;
        }

        public Criteria andBloodNotIn(List<Short> values) {
            addCriterion("BLOOD not in", values, "blood");
            return (Criteria) this;
        }

        public Criteria andBloodBetween(Short value1, Short value2) {
            addCriterion("BLOOD between", value1, value2, "blood");
            return (Criteria) this;
        }

        public Criteria andBloodNotBetween(Short value1, Short value2) {
            addCriterion("BLOOD not between", value1, value2, "blood");
            return (Criteria) this;
        }

        public Criteria andPostcodeIsNull() {
            addCriterion("POSTCODE is null");
            return (Criteria) this;
        }

        public Criteria andPostcodeIsNotNull() {
            addCriterion("POSTCODE is not null");
            return (Criteria) this;
        }

        public Criteria andPostcodeEqualTo(String value) {
            addCriterion("POSTCODE =", value, "postcode");
            return (Criteria) this;
        }

        public Criteria andPostcodeNotEqualTo(String value) {
            addCriterion("POSTCODE <>", value, "postcode");
            return (Criteria) this;
        }

        public Criteria andPostcodeGreaterThan(String value) {
            addCriterion("POSTCODE >", value, "postcode");
            return (Criteria) this;
        }

        public Criteria andPostcodeGreaterThanOrEqualTo(String value) {
            addCriterion("POSTCODE >=", value, "postcode");
            return (Criteria) this;
        }

        public Criteria andPostcodeLessThan(String value) {
            addCriterion("POSTCODE <", value, "postcode");
            return (Criteria) this;
        }

        public Criteria andPostcodeLessThanOrEqualTo(String value) {
            addCriterion("POSTCODE <=", value, "postcode");
            return (Criteria) this;
        }

        public Criteria andPostcodeLike(String value) {
            addCriterion("POSTCODE like", value, "postcode");
            return (Criteria) this;
        }

        public Criteria andPostcodeNotLike(String value) {
            addCriterion("POSTCODE not like", value, "postcode");
            return (Criteria) this;
        }

        public Criteria andPostcodeIn(List<String> values) {
            addCriterion("POSTCODE in", values, "postcode");
            return (Criteria) this;
        }

        public Criteria andPostcodeNotIn(List<String> values) {
            addCriterion("POSTCODE not in", values, "postcode");
            return (Criteria) this;
        }

        public Criteria andPostcodeBetween(String value1, String value2) {
            addCriterion("POSTCODE between", value1, value2, "postcode");
            return (Criteria) this;
        }

        public Criteria andPostcodeNotBetween(String value1, String value2) {
            addCriterion("POSTCODE not between", value1, value2, "postcode");
            return (Criteria) this;
        }

        public Criteria andEmailIsNull() {
            addCriterion("EMAIL is null");
            return (Criteria) this;
        }

        public Criteria andEmailIsNotNull() {
            addCriterion("EMAIL is not null");
            return (Criteria) this;
        }

        public Criteria andEmailEqualTo(String value) {
            addCriterion("EMAIL =", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotEqualTo(String value) {
            addCriterion("EMAIL <>", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailGreaterThan(String value) {
            addCriterion("EMAIL >", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailGreaterThanOrEqualTo(String value) {
            addCriterion("EMAIL >=", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailLessThan(String value) {
            addCriterion("EMAIL <", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailLessThanOrEqualTo(String value) {
            addCriterion("EMAIL <=", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailLike(String value) {
            addCriterion("EMAIL like", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotLike(String value) {
            addCriterion("EMAIL not like", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailIn(List<String> values) {
            addCriterion("EMAIL in", values, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotIn(List<String> values) {
            addCriterion("EMAIL not in", values, "email");
            return (Criteria) this;
        }

        public Criteria andEmailBetween(String value1, String value2) {
            addCriterion("EMAIL between", value1, value2, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotBetween(String value1, String value2) {
            addCriterion("EMAIL not between", value1, value2, "email");
            return (Criteria) this;
        }

        public Criteria andGraduateSchoolIsNull() {
            addCriterion("GRADUATE_SCHOOL is null");
            return (Criteria) this;
        }

        public Criteria andGraduateSchoolIsNotNull() {
            addCriterion("GRADUATE_SCHOOL is not null");
            return (Criteria) this;
        }

        public Criteria andGraduateSchoolEqualTo(String value) {
            addCriterion("GRADUATE_SCHOOL =", value, "graduateSchool");
            return (Criteria) this;
        }

        public Criteria andGraduateSchoolNotEqualTo(String value) {
            addCriterion("GRADUATE_SCHOOL <>", value, "graduateSchool");
            return (Criteria) this;
        }

        public Criteria andGraduateSchoolGreaterThan(String value) {
            addCriterion("GRADUATE_SCHOOL >", value, "graduateSchool");
            return (Criteria) this;
        }

        public Criteria andGraduateSchoolGreaterThanOrEqualTo(String value) {
            addCriterion("GRADUATE_SCHOOL >=", value, "graduateSchool");
            return (Criteria) this;
        }

        public Criteria andGraduateSchoolLessThan(String value) {
            addCriterion("GRADUATE_SCHOOL <", value, "graduateSchool");
            return (Criteria) this;
        }

        public Criteria andGraduateSchoolLessThanOrEqualTo(String value) {
            addCriterion("GRADUATE_SCHOOL <=", value, "graduateSchool");
            return (Criteria) this;
        }

        public Criteria andGraduateSchoolLike(String value) {
            addCriterion("GRADUATE_SCHOOL like", value, "graduateSchool");
            return (Criteria) this;
        }

        public Criteria andGraduateSchoolNotLike(String value) {
            addCriterion("GRADUATE_SCHOOL not like", value, "graduateSchool");
            return (Criteria) this;
        }

        public Criteria andGraduateSchoolIn(List<String> values) {
            addCriterion("GRADUATE_SCHOOL in", values, "graduateSchool");
            return (Criteria) this;
        }

        public Criteria andGraduateSchoolNotIn(List<String> values) {
            addCriterion("GRADUATE_SCHOOL not in", values, "graduateSchool");
            return (Criteria) this;
        }

        public Criteria andGraduateSchoolBetween(String value1, String value2) {
            addCriterion("GRADUATE_SCHOOL between", value1, value2, "graduateSchool");
            return (Criteria) this;
        }

        public Criteria andGraduateSchoolNotBetween(String value1, String value2) {
            addCriterion("GRADUATE_SCHOOL not between", value1, value2, "graduateSchool");
            return (Criteria) this;
        }

        public Criteria andWeixinIsNull() {
            addCriterion("WEIXIN is null");
            return (Criteria) this;
        }

        public Criteria andWeixinIsNotNull() {
            addCriterion("WEIXIN is not null");
            return (Criteria) this;
        }

        public Criteria andWeixinEqualTo(String value) {
            addCriterion("WEIXIN =", value, "weixin");
            return (Criteria) this;
        }

        public Criteria andWeixinNotEqualTo(String value) {
            addCriterion("WEIXIN <>", value, "weixin");
            return (Criteria) this;
        }

        public Criteria andWeixinGreaterThan(String value) {
            addCriterion("WEIXIN >", value, "weixin");
            return (Criteria) this;
        }

        public Criteria andWeixinGreaterThanOrEqualTo(String value) {
            addCriterion("WEIXIN >=", value, "weixin");
            return (Criteria) this;
        }

        public Criteria andWeixinLessThan(String value) {
            addCriterion("WEIXIN <", value, "weixin");
            return (Criteria) this;
        }

        public Criteria andWeixinLessThanOrEqualTo(String value) {
            addCriterion("WEIXIN <=", value, "weixin");
            return (Criteria) this;
        }

        public Criteria andWeixinLike(String value) {
            addCriterion("WEIXIN like", value, "weixin");
            return (Criteria) this;
        }

        public Criteria andWeixinNotLike(String value) {
            addCriterion("WEIXIN not like", value, "weixin");
            return (Criteria) this;
        }

        public Criteria andWeixinIn(List<String> values) {
            addCriterion("WEIXIN in", values, "weixin");
            return (Criteria) this;
        }

        public Criteria andWeixinNotIn(List<String> values) {
            addCriterion("WEIXIN not in", values, "weixin");
            return (Criteria) this;
        }

        public Criteria andWeixinBetween(String value1, String value2) {
            addCriterion("WEIXIN between", value1, value2, "weixin");
            return (Criteria) this;
        }

        public Criteria andWeixinNotBetween(String value1, String value2) {
            addCriterion("WEIXIN not between", value1, value2, "weixin");
            return (Criteria) this;
        }

        public Criteria andQqNoIsNull() {
            addCriterion("QQ_NO is null");
            return (Criteria) this;
        }

        public Criteria andQqNoIsNotNull() {
            addCriterion("QQ_NO is not null");
            return (Criteria) this;
        }

        public Criteria andQqNoEqualTo(String value) {
            addCriterion("QQ_NO =", value, "qqNo");
            return (Criteria) this;
        }

        public Criteria andQqNoNotEqualTo(String value) {
            addCriterion("QQ_NO <>", value, "qqNo");
            return (Criteria) this;
        }

        public Criteria andQqNoGreaterThan(String value) {
            addCriterion("QQ_NO >", value, "qqNo");
            return (Criteria) this;
        }

        public Criteria andQqNoGreaterThanOrEqualTo(String value) {
            addCriterion("QQ_NO >=", value, "qqNo");
            return (Criteria) this;
        }

        public Criteria andQqNoLessThan(String value) {
            addCriterion("QQ_NO <", value, "qqNo");
            return (Criteria) this;
        }

        public Criteria andQqNoLessThanOrEqualTo(String value) {
            addCriterion("QQ_NO <=", value, "qqNo");
            return (Criteria) this;
        }

        public Criteria andQqNoLike(String value) {
            addCriterion("QQ_NO like", value, "qqNo");
            return (Criteria) this;
        }

        public Criteria andQqNoNotLike(String value) {
            addCriterion("QQ_NO not like", value, "qqNo");
            return (Criteria) this;
        }

        public Criteria andQqNoIn(List<String> values) {
            addCriterion("QQ_NO in", values, "qqNo");
            return (Criteria) this;
        }

        public Criteria andQqNoNotIn(List<String> values) {
            addCriterion("QQ_NO not in", values, "qqNo");
            return (Criteria) this;
        }

        public Criteria andQqNoBetween(String value1, String value2) {
            addCriterion("QQ_NO between", value1, value2, "qqNo");
            return (Criteria) this;
        }

        public Criteria andQqNoNotBetween(String value1, String value2) {
            addCriterion("QQ_NO not between", value1, value2, "qqNo");
            return (Criteria) this;
        }

        public Criteria andTelNoIsNull() {
            addCriterion("TEL_NO is null");
            return (Criteria) this;
        }

        public Criteria andTelNoIsNotNull() {
            addCriterion("TEL_NO is not null");
            return (Criteria) this;
        }

        public Criteria andTelNoEqualTo(String value) {
            addCriterion("TEL_NO =", value, "telNo");
            return (Criteria) this;
        }

        public Criteria andTelNoNotEqualTo(String value) {
            addCriterion("TEL_NO <>", value, "telNo");
            return (Criteria) this;
        }

        public Criteria andTelNoGreaterThan(String value) {
            addCriterion("TEL_NO >", value, "telNo");
            return (Criteria) this;
        }

        public Criteria andTelNoGreaterThanOrEqualTo(String value) {
            addCriterion("TEL_NO >=", value, "telNo");
            return (Criteria) this;
        }

        public Criteria andTelNoLessThan(String value) {
            addCriterion("TEL_NO <", value, "telNo");
            return (Criteria) this;
        }

        public Criteria andTelNoLessThanOrEqualTo(String value) {
            addCriterion("TEL_NO <=", value, "telNo");
            return (Criteria) this;
        }

        public Criteria andTelNoLike(String value) {
            addCriterion("TEL_NO like", value, "telNo");
            return (Criteria) this;
        }

        public Criteria andTelNoNotLike(String value) {
            addCriterion("TEL_NO not like", value, "telNo");
            return (Criteria) this;
        }

        public Criteria andTelNoIn(List<String> values) {
            addCriterion("TEL_NO in", values, "telNo");
            return (Criteria) this;
        }

        public Criteria andTelNoNotIn(List<String> values) {
            addCriterion("TEL_NO not in", values, "telNo");
            return (Criteria) this;
        }

        public Criteria andTelNoBetween(String value1, String value2) {
            addCriterion("TEL_NO between", value1, value2, "telNo");
            return (Criteria) this;
        }

        public Criteria andTelNoNotBetween(String value1, String value2) {
            addCriterion("TEL_NO not between", value1, value2, "telNo");
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

        public Criteria andBirthdayIsNull() {
            addCriterion("BIRTHDAY is null");
            return (Criteria) this;
        }

        public Criteria andBirthdayIsNotNull() {
            addCriterion("BIRTHDAY is not null");
            return (Criteria) this;
        }

        public Criteria andBirthdayEqualTo(String value) {
            addCriterion("BIRTHDAY =", value, "birthday");
            return (Criteria) this;
        }

        public Criteria andBirthdayNotEqualTo(String value) {
            addCriterion("BIRTHDAY <>", value, "birthday");
            return (Criteria) this;
        }

        public Criteria andBirthdayGreaterThan(String value) {
            addCriterion("BIRTHDAY >", value, "birthday");
            return (Criteria) this;
        }

        public Criteria andBirthdayGreaterThanOrEqualTo(String value) {
            addCriterion("BIRTHDAY >=", value, "birthday");
            return (Criteria) this;
        }

        public Criteria andBirthdayLessThan(String value) {
            addCriterion("BIRTHDAY <", value, "birthday");
            return (Criteria) this;
        }

        public Criteria andBirthdayLessThanOrEqualTo(String value) {
            addCriterion("BIRTHDAY <=", value, "birthday");
            return (Criteria) this;
        }

        public Criteria andBirthdayLike(String value) {
            addCriterion("BIRTHDAY like", value, "birthday");
            return (Criteria) this;
        }

        public Criteria andBirthdayNotLike(String value) {
            addCriterion("BIRTHDAY not like", value, "birthday");
            return (Criteria) this;
        }

        public Criteria andBirthdayIn(List<String> values) {
            addCriterion("BIRTHDAY in", values, "birthday");
            return (Criteria) this;
        }

        public Criteria andBirthdayNotIn(List<String> values) {
            addCriterion("BIRTHDAY not in", values, "birthday");
            return (Criteria) this;
        }

        public Criteria andBirthdayBetween(String value1, String value2) {
            addCriterion("BIRTHDAY between", value1, value2, "birthday");
            return (Criteria) this;
        }

        public Criteria andBirthdayNotBetween(String value1, String value2) {
            addCriterion("BIRTHDAY not between", value1, value2, "birthday");
            return (Criteria) this;
        }

        public Criteria andHomeAddrIsNull() {
            addCriterion("HOME_ADDR is null");
            return (Criteria) this;
        }

        public Criteria andHomeAddrIsNotNull() {
            addCriterion("HOME_ADDR is not null");
            return (Criteria) this;
        }

        public Criteria andHomeAddrEqualTo(String value) {
            addCriterion("HOME_ADDR =", value, "homeAddr");
            return (Criteria) this;
        }

        public Criteria andHomeAddrNotEqualTo(String value) {
            addCriterion("HOME_ADDR <>", value, "homeAddr");
            return (Criteria) this;
        }

        public Criteria andHomeAddrGreaterThan(String value) {
            addCriterion("HOME_ADDR >", value, "homeAddr");
            return (Criteria) this;
        }

        public Criteria andHomeAddrGreaterThanOrEqualTo(String value) {
            addCriterion("HOME_ADDR >=", value, "homeAddr");
            return (Criteria) this;
        }

        public Criteria andHomeAddrLessThan(String value) {
            addCriterion("HOME_ADDR <", value, "homeAddr");
            return (Criteria) this;
        }

        public Criteria andHomeAddrLessThanOrEqualTo(String value) {
            addCriterion("HOME_ADDR <=", value, "homeAddr");
            return (Criteria) this;
        }

        public Criteria andHomeAddrLike(String value) {
            addCriterion("HOME_ADDR like", value, "homeAddr");
            return (Criteria) this;
        }

        public Criteria andHomeAddrNotLike(String value) {
            addCriterion("HOME_ADDR not like", value, "homeAddr");
            return (Criteria) this;
        }

        public Criteria andHomeAddrIn(List<String> values) {
            addCriterion("HOME_ADDR in", values, "homeAddr");
            return (Criteria) this;
        }

        public Criteria andHomeAddrNotIn(List<String> values) {
            addCriterion("HOME_ADDR not in", values, "homeAddr");
            return (Criteria) this;
        }

        public Criteria andHomeAddrBetween(String value1, String value2) {
            addCriterion("HOME_ADDR between", value1, value2, "homeAddr");
            return (Criteria) this;
        }

        public Criteria andHomeAddrNotBetween(String value1, String value2) {
            addCriterion("HOME_ADDR not between", value1, value2, "homeAddr");
            return (Criteria) this;
        }

        public Criteria andHomePhoneIsNull() {
            addCriterion("HOME_PHONE is null");
            return (Criteria) this;
        }

        public Criteria andHomePhoneIsNotNull() {
            addCriterion("HOME_PHONE is not null");
            return (Criteria) this;
        }

        public Criteria andHomePhoneEqualTo(String value) {
            addCriterion("HOME_PHONE =", value, "homePhone");
            return (Criteria) this;
        }

        public Criteria andHomePhoneNotEqualTo(String value) {
            addCriterion("HOME_PHONE <>", value, "homePhone");
            return (Criteria) this;
        }

        public Criteria andHomePhoneGreaterThan(String value) {
            addCriterion("HOME_PHONE >", value, "homePhone");
            return (Criteria) this;
        }

        public Criteria andHomePhoneGreaterThanOrEqualTo(String value) {
            addCriterion("HOME_PHONE >=", value, "homePhone");
            return (Criteria) this;
        }

        public Criteria andHomePhoneLessThan(String value) {
            addCriterion("HOME_PHONE <", value, "homePhone");
            return (Criteria) this;
        }

        public Criteria andHomePhoneLessThanOrEqualTo(String value) {
            addCriterion("HOME_PHONE <=", value, "homePhone");
            return (Criteria) this;
        }

        public Criteria andHomePhoneLike(String value) {
            addCriterion("HOME_PHONE like", value, "homePhone");
            return (Criteria) this;
        }

        public Criteria andHomePhoneNotLike(String value) {
            addCriterion("HOME_PHONE not like", value, "homePhone");
            return (Criteria) this;
        }

        public Criteria andHomePhoneIn(List<String> values) {
            addCriterion("HOME_PHONE in", values, "homePhone");
            return (Criteria) this;
        }

        public Criteria andHomePhoneNotIn(List<String> values) {
            addCriterion("HOME_PHONE not in", values, "homePhone");
            return (Criteria) this;
        }

        public Criteria andHomePhoneBetween(String value1, String value2) {
            addCriterion("HOME_PHONE between", value1, value2, "homePhone");
            return (Criteria) this;
        }

        public Criteria andHomePhoneNotBetween(String value1, String value2) {
            addCriterion("HOME_PHONE not between", value1, value2, "homePhone");
            return (Criteria) this;
        }

        public Criteria andOfficePhoneIsNull() {
            addCriterion("OFFICE_PHONE is null");
            return (Criteria) this;
        }

        public Criteria andOfficePhoneIsNotNull() {
            addCriterion("OFFICE_PHONE is not null");
            return (Criteria) this;
        }

        public Criteria andOfficePhoneEqualTo(String value) {
            addCriterion("OFFICE_PHONE =", value, "officePhone");
            return (Criteria) this;
        }

        public Criteria andOfficePhoneNotEqualTo(String value) {
            addCriterion("OFFICE_PHONE <>", value, "officePhone");
            return (Criteria) this;
        }

        public Criteria andOfficePhoneGreaterThan(String value) {
            addCriterion("OFFICE_PHONE >", value, "officePhone");
            return (Criteria) this;
        }

        public Criteria andOfficePhoneGreaterThanOrEqualTo(String value) {
            addCriterion("OFFICE_PHONE >=", value, "officePhone");
            return (Criteria) this;
        }

        public Criteria andOfficePhoneLessThan(String value) {
            addCriterion("OFFICE_PHONE <", value, "officePhone");
            return (Criteria) this;
        }

        public Criteria andOfficePhoneLessThanOrEqualTo(String value) {
            addCriterion("OFFICE_PHONE <=", value, "officePhone");
            return (Criteria) this;
        }

        public Criteria andOfficePhoneLike(String value) {
            addCriterion("OFFICE_PHONE like", value, "officePhone");
            return (Criteria) this;
        }

        public Criteria andOfficePhoneNotLike(String value) {
            addCriterion("OFFICE_PHONE not like", value, "officePhone");
            return (Criteria) this;
        }

        public Criteria andOfficePhoneIn(List<String> values) {
            addCriterion("OFFICE_PHONE in", values, "officePhone");
            return (Criteria) this;
        }

        public Criteria andOfficePhoneNotIn(List<String> values) {
            addCriterion("OFFICE_PHONE not in", values, "officePhone");
            return (Criteria) this;
        }

        public Criteria andOfficePhoneBetween(String value1, String value2) {
            addCriterion("OFFICE_PHONE between", value1, value2, "officePhone");
            return (Criteria) this;
        }

        public Criteria andOfficePhoneNotBetween(String value1, String value2) {
            addCriterion("OFFICE_PHONE not between", value1, value2, "officePhone");
            return (Criteria) this;
        }

        public Criteria andOfficeFaxIsNull() {
            addCriterion("OFFICE_FAX is null");
            return (Criteria) this;
        }

        public Criteria andOfficeFaxIsNotNull() {
            addCriterion("OFFICE_FAX is not null");
            return (Criteria) this;
        }

        public Criteria andOfficeFaxEqualTo(String value) {
            addCriterion("OFFICE_FAX =", value, "officeFax");
            return (Criteria) this;
        }

        public Criteria andOfficeFaxNotEqualTo(String value) {
            addCriterion("OFFICE_FAX <>", value, "officeFax");
            return (Criteria) this;
        }

        public Criteria andOfficeFaxGreaterThan(String value) {
            addCriterion("OFFICE_FAX >", value, "officeFax");
            return (Criteria) this;
        }

        public Criteria andOfficeFaxGreaterThanOrEqualTo(String value) {
            addCriterion("OFFICE_FAX >=", value, "officeFax");
            return (Criteria) this;
        }

        public Criteria andOfficeFaxLessThan(String value) {
            addCriterion("OFFICE_FAX <", value, "officeFax");
            return (Criteria) this;
        }

        public Criteria andOfficeFaxLessThanOrEqualTo(String value) {
            addCriterion("OFFICE_FAX <=", value, "officeFax");
            return (Criteria) this;
        }

        public Criteria andOfficeFaxLike(String value) {
            addCriterion("OFFICE_FAX like", value, "officeFax");
            return (Criteria) this;
        }

        public Criteria andOfficeFaxNotLike(String value) {
            addCriterion("OFFICE_FAX not like", value, "officeFax");
            return (Criteria) this;
        }

        public Criteria andOfficeFaxIn(List<String> values) {
            addCriterion("OFFICE_FAX in", values, "officeFax");
            return (Criteria) this;
        }

        public Criteria andOfficeFaxNotIn(List<String> values) {
            addCriterion("OFFICE_FAX not in", values, "officeFax");
            return (Criteria) this;
        }

        public Criteria andOfficeFaxBetween(String value1, String value2) {
            addCriterion("OFFICE_FAX between", value1, value2, "officeFax");
            return (Criteria) this;
        }

        public Criteria andOfficeFaxNotBetween(String value1, String value2) {
            addCriterion("OFFICE_FAX not between", value1, value2, "officeFax");
            return (Criteria) this;
        }

        public Criteria andIsShowIsNull() {
            addCriterion("IS_SHOW is null");
            return (Criteria) this;
        }

        public Criteria andIsShowIsNotNull() {
            addCriterion("IS_SHOW is not null");
            return (Criteria) this;
        }

        public Criteria andIsShowEqualTo(Short value) {
            addCriterion("IS_SHOW =", value, "isShow");
            return (Criteria) this;
        }

        public Criteria andIsShowNotEqualTo(Short value) {
            addCriterion("IS_SHOW <>", value, "isShow");
            return (Criteria) this;
        }

        public Criteria andIsShowGreaterThan(Short value) {
            addCriterion("IS_SHOW >", value, "isShow");
            return (Criteria) this;
        }

        public Criteria andIsShowGreaterThanOrEqualTo(Short value) {
            addCriterion("IS_SHOW >=", value, "isShow");
            return (Criteria) this;
        }

        public Criteria andIsShowLessThan(Short value) {
            addCriterion("IS_SHOW <", value, "isShow");
            return (Criteria) this;
        }

        public Criteria andIsShowLessThanOrEqualTo(Short value) {
            addCriterion("IS_SHOW <=", value, "isShow");
            return (Criteria) this;
        }

        public Criteria andIsShowIn(List<Short> values) {
            addCriterion("IS_SHOW in", values, "isShow");
            return (Criteria) this;
        }

        public Criteria andIsShowNotIn(List<Short> values) {
            addCriterion("IS_SHOW not in", values, "isShow");
            return (Criteria) this;
        }

        public Criteria andIsShowBetween(Short value1, Short value2) {
            addCriterion("IS_SHOW between", value1, value2, "isShow");
            return (Criteria) this;
        }

        public Criteria andIsShowNotBetween(Short value1, Short value2) {
            addCriterion("IS_SHOW not between", value1, value2, "isShow");
            return (Criteria) this;
        }

        public Criteria andModifyAcountIsNull() {
            addCriterion("MODIFY_ACOUNT is null");
            return (Criteria) this;
        }

        public Criteria andModifyAcountIsNotNull() {
            addCriterion("MODIFY_ACOUNT is not null");
            return (Criteria) this;
        }

        public Criteria andModifyAcountEqualTo(Integer value) {
            addCriterion("MODIFY_ACOUNT =", value, "modifyAcount");
            return (Criteria) this;
        }

        public Criteria andModifyAcountNotEqualTo(Integer value) {
            addCriterion("MODIFY_ACOUNT <>", value, "modifyAcount");
            return (Criteria) this;
        }

        public Criteria andModifyAcountGreaterThan(Integer value) {
            addCriterion("MODIFY_ACOUNT >", value, "modifyAcount");
            return (Criteria) this;
        }

        public Criteria andModifyAcountGreaterThanOrEqualTo(Integer value) {
            addCriterion("MODIFY_ACOUNT >=", value, "modifyAcount");
            return (Criteria) this;
        }

        public Criteria andModifyAcountLessThan(Integer value) {
            addCriterion("MODIFY_ACOUNT <", value, "modifyAcount");
            return (Criteria) this;
        }

        public Criteria andModifyAcountLessThanOrEqualTo(Integer value) {
            addCriterion("MODIFY_ACOUNT <=", value, "modifyAcount");
            return (Criteria) this;
        }

        public Criteria andModifyAcountIn(List<Integer> values) {
            addCriterion("MODIFY_ACOUNT in", values, "modifyAcount");
            return (Criteria) this;
        }

        public Criteria andModifyAcountNotIn(List<Integer> values) {
            addCriterion("MODIFY_ACOUNT not in", values, "modifyAcount");
            return (Criteria) this;
        }

        public Criteria andModifyAcountBetween(Integer value1, Integer value2) {
            addCriterion("MODIFY_ACOUNT between", value1, value2, "modifyAcount");
            return (Criteria) this;
        }

        public Criteria andModifyAcountNotBetween(Integer value1, Integer value2) {
            addCriterion("MODIFY_ACOUNT not between", value1, value2, "modifyAcount");
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

        public Criteria andCreateDateIsNull() {
            addCriterion("CREATE_DATE is null");
            return (Criteria) this;
        }

        public Criteria andCreateDateIsNotNull() {
            addCriterion("CREATE_DATE is not null");
            return (Criteria) this;
        }

        public Criteria andCreateDateEqualTo(Date value) {
            addCriterion("CREATE_DATE =", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateNotEqualTo(Date value) {
            addCriterion("CREATE_DATE <>", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateGreaterThan(Date value) {
            addCriterion("CREATE_DATE >", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateGreaterThanOrEqualTo(Date value) {
            addCriterion("CREATE_DATE >=", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateLessThan(Date value) {
            addCriterion("CREATE_DATE <", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateLessThanOrEqualTo(Date value) {
            addCriterion("CREATE_DATE <=", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateIn(List<Date> values) {
            addCriterion("CREATE_DATE in", values, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateNotIn(List<Date> values) {
            addCriterion("CREATE_DATE not in", values, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateBetween(Date value1, Date value2) {
            addCriterion("CREATE_DATE between", value1, value2, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateNotBetween(Date value1, Date value2) {
            addCriterion("CREATE_DATE not between", value1, value2, "createDate");
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

        public Criteria andUpdateDateIsNull() {
            addCriterion("UPDATE_DATE is null");
            return (Criteria) this;
        }

        public Criteria andUpdateDateIsNotNull() {
            addCriterion("UPDATE_DATE is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateDateEqualTo(Date value) {
            addCriterion("UPDATE_DATE =", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateNotEqualTo(Date value) {
            addCriterion("UPDATE_DATE <>", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateGreaterThan(Date value) {
            addCriterion("UPDATE_DATE >", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateGreaterThanOrEqualTo(Date value) {
            addCriterion("UPDATE_DATE >=", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateLessThan(Date value) {
            addCriterion("UPDATE_DATE <", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateLessThanOrEqualTo(Date value) {
            addCriterion("UPDATE_DATE <=", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateIn(List<Date> values) {
            addCriterion("UPDATE_DATE in", values, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateNotIn(List<Date> values) {
            addCriterion("UPDATE_DATE not in", values, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateBetween(Date value1, Date value2) {
            addCriterion("UPDATE_DATE between", value1, value2, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateNotBetween(Date value1, Date value2) {
            addCriterion("UPDATE_DATE not between", value1, value2, "updateDate");
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