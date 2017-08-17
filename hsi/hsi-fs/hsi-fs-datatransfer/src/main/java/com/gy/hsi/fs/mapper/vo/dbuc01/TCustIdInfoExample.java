package com.gy.hsi.fs.mapper.vo.dbuc01;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.gy.hsi.fs.mapper.vo.AbstractPage;

public class TCustIdInfoExample extends AbstractPage  {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TCustIdInfoExample() {
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

        public Criteria andIdTypeIsNull() {
            addCriterion("ID_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andIdTypeIsNotNull() {
            addCriterion("ID_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andIdTypeEqualTo(Short value) {
            addCriterion("ID_TYPE =", value, "idType");
            return (Criteria) this;
        }

        public Criteria andIdTypeNotEqualTo(Short value) {
            addCriterion("ID_TYPE <>", value, "idType");
            return (Criteria) this;
        }

        public Criteria andIdTypeGreaterThan(Short value) {
            addCriterion("ID_TYPE >", value, "idType");
            return (Criteria) this;
        }

        public Criteria andIdTypeGreaterThanOrEqualTo(Short value) {
            addCriterion("ID_TYPE >=", value, "idType");
            return (Criteria) this;
        }

        public Criteria andIdTypeLessThan(Short value) {
            addCriterion("ID_TYPE <", value, "idType");
            return (Criteria) this;
        }

        public Criteria andIdTypeLessThanOrEqualTo(Short value) {
            addCriterion("ID_TYPE <=", value, "idType");
            return (Criteria) this;
        }

        public Criteria andIdTypeIn(List<Short> values) {
            addCriterion("ID_TYPE in", values, "idType");
            return (Criteria) this;
        }

        public Criteria andIdTypeNotIn(List<Short> values) {
            addCriterion("ID_TYPE not in", values, "idType");
            return (Criteria) this;
        }

        public Criteria andIdTypeBetween(Short value1, Short value2) {
            addCriterion("ID_TYPE between", value1, value2, "idType");
            return (Criteria) this;
        }

        public Criteria andIdTypeNotBetween(Short value1, Short value2) {
            addCriterion("ID_TYPE not between", value1, value2, "idType");
            return (Criteria) this;
        }

        public Criteria andIdNoIsNull() {
            addCriterion("ID_NO is null");
            return (Criteria) this;
        }

        public Criteria andIdNoIsNotNull() {
            addCriterion("ID_NO is not null");
            return (Criteria) this;
        }

        public Criteria andIdNoEqualTo(String value) {
            addCriterion("ID_NO =", value, "idNo");
            return (Criteria) this;
        }

        public Criteria andIdNoNotEqualTo(String value) {
            addCriterion("ID_NO <>", value, "idNo");
            return (Criteria) this;
        }

        public Criteria andIdNoGreaterThan(String value) {
            addCriterion("ID_NO >", value, "idNo");
            return (Criteria) this;
        }

        public Criteria andIdNoGreaterThanOrEqualTo(String value) {
            addCriterion("ID_NO >=", value, "idNo");
            return (Criteria) this;
        }

        public Criteria andIdNoLessThan(String value) {
            addCriterion("ID_NO <", value, "idNo");
            return (Criteria) this;
        }

        public Criteria andIdNoLessThanOrEqualTo(String value) {
            addCriterion("ID_NO <=", value, "idNo");
            return (Criteria) this;
        }

        public Criteria andIdNoLike(String value) {
            addCriterion("ID_NO like", value, "idNo");
            return (Criteria) this;
        }

        public Criteria andIdNoNotLike(String value) {
            addCriterion("ID_NO not like", value, "idNo");
            return (Criteria) this;
        }

        public Criteria andIdNoIn(List<String> values) {
            addCriterion("ID_NO in", values, "idNo");
            return (Criteria) this;
        }

        public Criteria andIdNoNotIn(List<String> values) {
            addCriterion("ID_NO not in", values, "idNo");
            return (Criteria) this;
        }

        public Criteria andIdNoBetween(String value1, String value2) {
            addCriterion("ID_NO between", value1, value2, "idNo");
            return (Criteria) this;
        }

        public Criteria andIdNoNotBetween(String value1, String value2) {
            addCriterion("ID_NO not between", value1, value2, "idNo");
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

        public Criteria andPerNameIsNull() {
            addCriterion("PER_NAME is null");
            return (Criteria) this;
        }

        public Criteria andPerNameIsNotNull() {
            addCriterion("PER_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andPerNameEqualTo(String value) {
            addCriterion("PER_NAME =", value, "perName");
            return (Criteria) this;
        }

        public Criteria andPerNameNotEqualTo(String value) {
            addCriterion("PER_NAME <>", value, "perName");
            return (Criteria) this;
        }

        public Criteria andPerNameGreaterThan(String value) {
            addCriterion("PER_NAME >", value, "perName");
            return (Criteria) this;
        }

        public Criteria andPerNameGreaterThanOrEqualTo(String value) {
            addCriterion("PER_NAME >=", value, "perName");
            return (Criteria) this;
        }

        public Criteria andPerNameLessThan(String value) {
            addCriterion("PER_NAME <", value, "perName");
            return (Criteria) this;
        }

        public Criteria andPerNameLessThanOrEqualTo(String value) {
            addCriterion("PER_NAME <=", value, "perName");
            return (Criteria) this;
        }

        public Criteria andPerNameLike(String value) {
            addCriterion("PER_NAME like", value, "perName");
            return (Criteria) this;
        }

        public Criteria andPerNameNotLike(String value) {
            addCriterion("PER_NAME not like", value, "perName");
            return (Criteria) this;
        }

        public Criteria andPerNameIn(List<String> values) {
            addCriterion("PER_NAME in", values, "perName");
            return (Criteria) this;
        }

        public Criteria andPerNameNotIn(List<String> values) {
            addCriterion("PER_NAME not in", values, "perName");
            return (Criteria) this;
        }

        public Criteria andPerNameBetween(String value1, String value2) {
            addCriterion("PER_NAME between", value1, value2, "perName");
            return (Criteria) this;
        }

        public Criteria andPerNameNotBetween(String value1, String value2) {
            addCriterion("PER_NAME not between", value1, value2, "perName");
            return (Criteria) this;
        }

        public Criteria andIdValidDateIsNull() {
            addCriterion("ID_VALID_DATE is null");
            return (Criteria) this;
        }

        public Criteria andIdValidDateIsNotNull() {
            addCriterion("ID_VALID_DATE is not null");
            return (Criteria) this;
        }

        public Criteria andIdValidDateEqualTo(String value) {
            addCriterion("ID_VALID_DATE =", value, "idValidDate");
            return (Criteria) this;
        }

        public Criteria andIdValidDateNotEqualTo(String value) {
            addCriterion("ID_VALID_DATE <>", value, "idValidDate");
            return (Criteria) this;
        }

        public Criteria andIdValidDateGreaterThan(String value) {
            addCriterion("ID_VALID_DATE >", value, "idValidDate");
            return (Criteria) this;
        }

        public Criteria andIdValidDateGreaterThanOrEqualTo(String value) {
            addCriterion("ID_VALID_DATE >=", value, "idValidDate");
            return (Criteria) this;
        }

        public Criteria andIdValidDateLessThan(String value) {
            addCriterion("ID_VALID_DATE <", value, "idValidDate");
            return (Criteria) this;
        }

        public Criteria andIdValidDateLessThanOrEqualTo(String value) {
            addCriterion("ID_VALID_DATE <=", value, "idValidDate");
            return (Criteria) this;
        }

        public Criteria andIdValidDateLike(String value) {
            addCriterion("ID_VALID_DATE like", value, "idValidDate");
            return (Criteria) this;
        }

        public Criteria andIdValidDateNotLike(String value) {
            addCriterion("ID_VALID_DATE not like", value, "idValidDate");
            return (Criteria) this;
        }

        public Criteria andIdValidDateIn(List<String> values) {
            addCriterion("ID_VALID_DATE in", values, "idValidDate");
            return (Criteria) this;
        }

        public Criteria andIdValidDateNotIn(List<String> values) {
            addCriterion("ID_VALID_DATE not in", values, "idValidDate");
            return (Criteria) this;
        }

        public Criteria andIdValidDateBetween(String value1, String value2) {
            addCriterion("ID_VALID_DATE between", value1, value2, "idValidDate");
            return (Criteria) this;
        }

        public Criteria andIdValidDateNotBetween(String value1, String value2) {
            addCriterion("ID_VALID_DATE not between", value1, value2, "idValidDate");
            return (Criteria) this;
        }

        public Criteria andIdIssueOrgIsNull() {
            addCriterion("ID_ISSUE_ORG is null");
            return (Criteria) this;
        }

        public Criteria andIdIssueOrgIsNotNull() {
            addCriterion("ID_ISSUE_ORG is not null");
            return (Criteria) this;
        }

        public Criteria andIdIssueOrgEqualTo(String value) {
            addCriterion("ID_ISSUE_ORG =", value, "idIssueOrg");
            return (Criteria) this;
        }

        public Criteria andIdIssueOrgNotEqualTo(String value) {
            addCriterion("ID_ISSUE_ORG <>", value, "idIssueOrg");
            return (Criteria) this;
        }

        public Criteria andIdIssueOrgGreaterThan(String value) {
            addCriterion("ID_ISSUE_ORG >", value, "idIssueOrg");
            return (Criteria) this;
        }

        public Criteria andIdIssueOrgGreaterThanOrEqualTo(String value) {
            addCriterion("ID_ISSUE_ORG >=", value, "idIssueOrg");
            return (Criteria) this;
        }

        public Criteria andIdIssueOrgLessThan(String value) {
            addCriterion("ID_ISSUE_ORG <", value, "idIssueOrg");
            return (Criteria) this;
        }

        public Criteria andIdIssueOrgLessThanOrEqualTo(String value) {
            addCriterion("ID_ISSUE_ORG <=", value, "idIssueOrg");
            return (Criteria) this;
        }

        public Criteria andIdIssueOrgLike(String value) {
            addCriterion("ID_ISSUE_ORG like", value, "idIssueOrg");
            return (Criteria) this;
        }

        public Criteria andIdIssueOrgNotLike(String value) {
            addCriterion("ID_ISSUE_ORG not like", value, "idIssueOrg");
            return (Criteria) this;
        }

        public Criteria andIdIssueOrgIn(List<String> values) {
            addCriterion("ID_ISSUE_ORG in", values, "idIssueOrg");
            return (Criteria) this;
        }

        public Criteria andIdIssueOrgNotIn(List<String> values) {
            addCriterion("ID_ISSUE_ORG not in", values, "idIssueOrg");
            return (Criteria) this;
        }

        public Criteria andIdIssueOrgBetween(String value1, String value2) {
            addCriterion("ID_ISSUE_ORG between", value1, value2, "idIssueOrg");
            return (Criteria) this;
        }

        public Criteria andIdIssueOrgNotBetween(String value1, String value2) {
            addCriterion("ID_ISSUE_ORG not between", value1, value2, "idIssueOrg");
            return (Criteria) this;
        }

        public Criteria andResidentAddrIsNull() {
            addCriterion("RESIDENT_ADDR is null");
            return (Criteria) this;
        }

        public Criteria andResidentAddrIsNotNull() {
            addCriterion("RESIDENT_ADDR is not null");
            return (Criteria) this;
        }

        public Criteria andResidentAddrEqualTo(String value) {
            addCriterion("RESIDENT_ADDR =", value, "residentAddr");
            return (Criteria) this;
        }

        public Criteria andResidentAddrNotEqualTo(String value) {
            addCriterion("RESIDENT_ADDR <>", value, "residentAddr");
            return (Criteria) this;
        }

        public Criteria andResidentAddrGreaterThan(String value) {
            addCriterion("RESIDENT_ADDR >", value, "residentAddr");
            return (Criteria) this;
        }

        public Criteria andResidentAddrGreaterThanOrEqualTo(String value) {
            addCriterion("RESIDENT_ADDR >=", value, "residentAddr");
            return (Criteria) this;
        }

        public Criteria andResidentAddrLessThan(String value) {
            addCriterion("RESIDENT_ADDR <", value, "residentAddr");
            return (Criteria) this;
        }

        public Criteria andResidentAddrLessThanOrEqualTo(String value) {
            addCriterion("RESIDENT_ADDR <=", value, "residentAddr");
            return (Criteria) this;
        }

        public Criteria andResidentAddrLike(String value) {
            addCriterion("RESIDENT_ADDR like", value, "residentAddr");
            return (Criteria) this;
        }

        public Criteria andResidentAddrNotLike(String value) {
            addCriterion("RESIDENT_ADDR not like", value, "residentAddr");
            return (Criteria) this;
        }

        public Criteria andResidentAddrIn(List<String> values) {
            addCriterion("RESIDENT_ADDR in", values, "residentAddr");
            return (Criteria) this;
        }

        public Criteria andResidentAddrNotIn(List<String> values) {
            addCriterion("RESIDENT_ADDR not in", values, "residentAddr");
            return (Criteria) this;
        }

        public Criteria andResidentAddrBetween(String value1, String value2) {
            addCriterion("RESIDENT_ADDR between", value1, value2, "residentAddr");
            return (Criteria) this;
        }

        public Criteria andResidentAddrNotBetween(String value1, String value2) {
            addCriterion("RESIDENT_ADDR not between", value1, value2, "residentAddr");
            return (Criteria) this;
        }

        public Criteria andCertificateFrontIsNull() {
            addCriterion("CERTIFICATE_FRONT is null");
            return (Criteria) this;
        }

        public Criteria andCertificateFrontIsNotNull() {
            addCriterion("CERTIFICATE_FRONT is not null");
            return (Criteria) this;
        }

        public Criteria andCertificateFrontEqualTo(String value) {
            addCriterion("CERTIFICATE_FRONT =", value, "certificateFront");
            return (Criteria) this;
        }

        public Criteria andCertificateFrontNotEqualTo(String value) {
            addCriterion("CERTIFICATE_FRONT <>", value, "certificateFront");
            return (Criteria) this;
        }

        public Criteria andCertificateFrontGreaterThan(String value) {
            addCriterion("CERTIFICATE_FRONT >", value, "certificateFront");
            return (Criteria) this;
        }

        public Criteria andCertificateFrontGreaterThanOrEqualTo(String value) {
            addCriterion("CERTIFICATE_FRONT >=", value, "certificateFront");
            return (Criteria) this;
        }

        public Criteria andCertificateFrontLessThan(String value) {
            addCriterion("CERTIFICATE_FRONT <", value, "certificateFront");
            return (Criteria) this;
        }

        public Criteria andCertificateFrontLessThanOrEqualTo(String value) {
            addCriterion("CERTIFICATE_FRONT <=", value, "certificateFront");
            return (Criteria) this;
        }

        public Criteria andCertificateFrontLike(String value) {
            addCriterion("CERTIFICATE_FRONT like", value, "certificateFront");
            return (Criteria) this;
        }

        public Criteria andCertificateFrontNotLike(String value) {
            addCriterion("CERTIFICATE_FRONT not like", value, "certificateFront");
            return (Criteria) this;
        }

        public Criteria andCertificateFrontIn(List<String> values) {
            addCriterion("CERTIFICATE_FRONT in", values, "certificateFront");
            return (Criteria) this;
        }

        public Criteria andCertificateFrontNotIn(List<String> values) {
            addCriterion("CERTIFICATE_FRONT not in", values, "certificateFront");
            return (Criteria) this;
        }

        public Criteria andCertificateFrontBetween(String value1, String value2) {
            addCriterion("CERTIFICATE_FRONT between", value1, value2, "certificateFront");
            return (Criteria) this;
        }

        public Criteria andCertificateFrontNotBetween(String value1, String value2) {
            addCriterion("CERTIFICATE_FRONT not between", value1, value2, "certificateFront");
            return (Criteria) this;
        }

        public Criteria andCertificateBackIsNull() {
            addCriterion("CERTIFICATE_BACK is null");
            return (Criteria) this;
        }

        public Criteria andCertificateBackIsNotNull() {
            addCriterion("CERTIFICATE_BACK is not null");
            return (Criteria) this;
        }

        public Criteria andCertificateBackEqualTo(String value) {
            addCriterion("CERTIFICATE_BACK =", value, "certificateBack");
            return (Criteria) this;
        }

        public Criteria andCertificateBackNotEqualTo(String value) {
            addCriterion("CERTIFICATE_BACK <>", value, "certificateBack");
            return (Criteria) this;
        }

        public Criteria andCertificateBackGreaterThan(String value) {
            addCriterion("CERTIFICATE_BACK >", value, "certificateBack");
            return (Criteria) this;
        }

        public Criteria andCertificateBackGreaterThanOrEqualTo(String value) {
            addCriterion("CERTIFICATE_BACK >=", value, "certificateBack");
            return (Criteria) this;
        }

        public Criteria andCertificateBackLessThan(String value) {
            addCriterion("CERTIFICATE_BACK <", value, "certificateBack");
            return (Criteria) this;
        }

        public Criteria andCertificateBackLessThanOrEqualTo(String value) {
            addCriterion("CERTIFICATE_BACK <=", value, "certificateBack");
            return (Criteria) this;
        }

        public Criteria andCertificateBackLike(String value) {
            addCriterion("CERTIFICATE_BACK like", value, "certificateBack");
            return (Criteria) this;
        }

        public Criteria andCertificateBackNotLike(String value) {
            addCriterion("CERTIFICATE_BACK not like", value, "certificateBack");
            return (Criteria) this;
        }

        public Criteria andCertificateBackIn(List<String> values) {
            addCriterion("CERTIFICATE_BACK in", values, "certificateBack");
            return (Criteria) this;
        }

        public Criteria andCertificateBackNotIn(List<String> values) {
            addCriterion("CERTIFICATE_BACK not in", values, "certificateBack");
            return (Criteria) this;
        }

        public Criteria andCertificateBackBetween(String value1, String value2) {
            addCriterion("CERTIFICATE_BACK between", value1, value2, "certificateBack");
            return (Criteria) this;
        }

        public Criteria andCertificateBackNotBetween(String value1, String value2) {
            addCriterion("CERTIFICATE_BACK not between", value1, value2, "certificateBack");
            return (Criteria) this;
        }

        public Criteria andCertificateHandholdIsNull() {
            addCriterion("CERTIFICATE_HANDHOLD is null");
            return (Criteria) this;
        }

        public Criteria andCertificateHandholdIsNotNull() {
            addCriterion("CERTIFICATE_HANDHOLD is not null");
            return (Criteria) this;
        }

        public Criteria andCertificateHandholdEqualTo(String value) {
            addCriterion("CERTIFICATE_HANDHOLD =", value, "certificateHandhold");
            return (Criteria) this;
        }

        public Criteria andCertificateHandholdNotEqualTo(String value) {
            addCriterion("CERTIFICATE_HANDHOLD <>", value, "certificateHandhold");
            return (Criteria) this;
        }

        public Criteria andCertificateHandholdGreaterThan(String value) {
            addCriterion("CERTIFICATE_HANDHOLD >", value, "certificateHandhold");
            return (Criteria) this;
        }

        public Criteria andCertificateHandholdGreaterThanOrEqualTo(String value) {
            addCriterion("CERTIFICATE_HANDHOLD >=", value, "certificateHandhold");
            return (Criteria) this;
        }

        public Criteria andCertificateHandholdLessThan(String value) {
            addCriterion("CERTIFICATE_HANDHOLD <", value, "certificateHandhold");
            return (Criteria) this;
        }

        public Criteria andCertificateHandholdLessThanOrEqualTo(String value) {
            addCriterion("CERTIFICATE_HANDHOLD <=", value, "certificateHandhold");
            return (Criteria) this;
        }

        public Criteria andCertificateHandholdLike(String value) {
            addCriterion("CERTIFICATE_HANDHOLD like", value, "certificateHandhold");
            return (Criteria) this;
        }

        public Criteria andCertificateHandholdNotLike(String value) {
            addCriterion("CERTIFICATE_HANDHOLD not like", value, "certificateHandhold");
            return (Criteria) this;
        }

        public Criteria andCertificateHandholdIn(List<String> values) {
            addCriterion("CERTIFICATE_HANDHOLD in", values, "certificateHandhold");
            return (Criteria) this;
        }

        public Criteria andCertificateHandholdNotIn(List<String> values) {
            addCriterion("CERTIFICATE_HANDHOLD not in", values, "certificateHandhold");
            return (Criteria) this;
        }

        public Criteria andCertificateHandholdBetween(String value1, String value2) {
            addCriterion("CERTIFICATE_HANDHOLD between", value1, value2, "certificateHandhold");
            return (Criteria) this;
        }

        public Criteria andCertificateHandholdNotBetween(String value1, String value2) {
            addCriterion("CERTIFICATE_HANDHOLD not between", value1, value2, "certificateHandhold");
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

        public Criteria andBirthPlaceIsNull() {
            addCriterion("BIRTH_PLACE is null");
            return (Criteria) this;
        }

        public Criteria andBirthPlaceIsNotNull() {
            addCriterion("BIRTH_PLACE is not null");
            return (Criteria) this;
        }

        public Criteria andBirthPlaceEqualTo(String value) {
            addCriterion("BIRTH_PLACE =", value, "birthPlace");
            return (Criteria) this;
        }

        public Criteria andBirthPlaceNotEqualTo(String value) {
            addCriterion("BIRTH_PLACE <>", value, "birthPlace");
            return (Criteria) this;
        }

        public Criteria andBirthPlaceGreaterThan(String value) {
            addCriterion("BIRTH_PLACE >", value, "birthPlace");
            return (Criteria) this;
        }

        public Criteria andBirthPlaceGreaterThanOrEqualTo(String value) {
            addCriterion("BIRTH_PLACE >=", value, "birthPlace");
            return (Criteria) this;
        }

        public Criteria andBirthPlaceLessThan(String value) {
            addCriterion("BIRTH_PLACE <", value, "birthPlace");
            return (Criteria) this;
        }

        public Criteria andBirthPlaceLessThanOrEqualTo(String value) {
            addCriterion("BIRTH_PLACE <=", value, "birthPlace");
            return (Criteria) this;
        }

        public Criteria andBirthPlaceLike(String value) {
            addCriterion("BIRTH_PLACE like", value, "birthPlace");
            return (Criteria) this;
        }

        public Criteria andBirthPlaceNotLike(String value) {
            addCriterion("BIRTH_PLACE not like", value, "birthPlace");
            return (Criteria) this;
        }

        public Criteria andBirthPlaceIn(List<String> values) {
            addCriterion("BIRTH_PLACE in", values, "birthPlace");
            return (Criteria) this;
        }

        public Criteria andBirthPlaceNotIn(List<String> values) {
            addCriterion("BIRTH_PLACE not in", values, "birthPlace");
            return (Criteria) this;
        }

        public Criteria andBirthPlaceBetween(String value1, String value2) {
            addCriterion("BIRTH_PLACE between", value1, value2, "birthPlace");
            return (Criteria) this;
        }

        public Criteria andBirthPlaceNotBetween(String value1, String value2) {
            addCriterion("BIRTH_PLACE not between", value1, value2, "birthPlace");
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

        public Criteria andEntBuildDateEqualTo(String value) {
            addCriterion("ENT_BUILD_DATE =", value, "entBuildDate");
            return (Criteria) this;
        }

        public Criteria andEntBuildDateNotEqualTo(String value) {
            addCriterion("ENT_BUILD_DATE <>", value, "entBuildDate");
            return (Criteria) this;
        }

        public Criteria andEntBuildDateGreaterThan(String value) {
            addCriterion("ENT_BUILD_DATE >", value, "entBuildDate");
            return (Criteria) this;
        }

        public Criteria andEntBuildDateGreaterThanOrEqualTo(String value) {
            addCriterion("ENT_BUILD_DATE >=", value, "entBuildDate");
            return (Criteria) this;
        }

        public Criteria andEntBuildDateLessThan(String value) {
            addCriterion("ENT_BUILD_DATE <", value, "entBuildDate");
            return (Criteria) this;
        }

        public Criteria andEntBuildDateLessThanOrEqualTo(String value) {
            addCriterion("ENT_BUILD_DATE <=", value, "entBuildDate");
            return (Criteria) this;
        }

        public Criteria andEntBuildDateLike(String value) {
            addCriterion("ENT_BUILD_DATE like", value, "entBuildDate");
            return (Criteria) this;
        }

        public Criteria andEntBuildDateNotLike(String value) {
            addCriterion("ENT_BUILD_DATE not like", value, "entBuildDate");
            return (Criteria) this;
        }

        public Criteria andEntBuildDateIn(List<String> values) {
            addCriterion("ENT_BUILD_DATE in", values, "entBuildDate");
            return (Criteria) this;
        }

        public Criteria andEntBuildDateNotIn(List<String> values) {
            addCriterion("ENT_BUILD_DATE not in", values, "entBuildDate");
            return (Criteria) this;
        }

        public Criteria andEntBuildDateBetween(String value1, String value2) {
            addCriterion("ENT_BUILD_DATE between", value1, value2, "entBuildDate");
            return (Criteria) this;
        }

        public Criteria andEntBuildDateNotBetween(String value1, String value2) {
            addCriterion("ENT_BUILD_DATE not between", value1, value2, "entBuildDate");
            return (Criteria) this;
        }

        public Criteria andAuthRemarkIsNull() {
            addCriterion("AUTH_REMARK is null");
            return (Criteria) this;
        }

        public Criteria andAuthRemarkIsNotNull() {
            addCriterion("AUTH_REMARK is not null");
            return (Criteria) this;
        }

        public Criteria andAuthRemarkEqualTo(String value) {
            addCriterion("AUTH_REMARK =", value, "authRemark");
            return (Criteria) this;
        }

        public Criteria andAuthRemarkNotEqualTo(String value) {
            addCriterion("AUTH_REMARK <>", value, "authRemark");
            return (Criteria) this;
        }

        public Criteria andAuthRemarkGreaterThan(String value) {
            addCriterion("AUTH_REMARK >", value, "authRemark");
            return (Criteria) this;
        }

        public Criteria andAuthRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("AUTH_REMARK >=", value, "authRemark");
            return (Criteria) this;
        }

        public Criteria andAuthRemarkLessThan(String value) {
            addCriterion("AUTH_REMARK <", value, "authRemark");
            return (Criteria) this;
        }

        public Criteria andAuthRemarkLessThanOrEqualTo(String value) {
            addCriterion("AUTH_REMARK <=", value, "authRemark");
            return (Criteria) this;
        }

        public Criteria andAuthRemarkLike(String value) {
            addCriterion("AUTH_REMARK like", value, "authRemark");
            return (Criteria) this;
        }

        public Criteria andAuthRemarkNotLike(String value) {
            addCriterion("AUTH_REMARK not like", value, "authRemark");
            return (Criteria) this;
        }

        public Criteria andAuthRemarkIn(List<String> values) {
            addCriterion("AUTH_REMARK in", values, "authRemark");
            return (Criteria) this;
        }

        public Criteria andAuthRemarkNotIn(List<String> values) {
            addCriterion("AUTH_REMARK not in", values, "authRemark");
            return (Criteria) this;
        }

        public Criteria andAuthRemarkBetween(String value1, String value2) {
            addCriterion("AUTH_REMARK between", value1, value2, "authRemark");
            return (Criteria) this;
        }

        public Criteria andAuthRemarkNotBetween(String value1, String value2) {
            addCriterion("AUTH_REMARK not between", value1, value2, "authRemark");
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