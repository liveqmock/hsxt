package com.gy.hsi.fs.mapper.vo.dbuc01;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.gy.hsi.fs.mapper.vo.AbstractPage;

public class TEntExtendExample extends AbstractPage {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TEntExtendExample() {
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

        public Criteria andApplyEntResNoIsNull() {
            addCriterion("APPLY_ENT_RES_NO is null");
            return (Criteria) this;
        }

        public Criteria andApplyEntResNoIsNotNull() {
            addCriterion("APPLY_ENT_RES_NO is not null");
            return (Criteria) this;
        }

        public Criteria andApplyEntResNoEqualTo(String value) {
            addCriterion("APPLY_ENT_RES_NO =", value, "applyEntResNo");
            return (Criteria) this;
        }

        public Criteria andApplyEntResNoNotEqualTo(String value) {
            addCriterion("APPLY_ENT_RES_NO <>", value, "applyEntResNo");
            return (Criteria) this;
        }

        public Criteria andApplyEntResNoGreaterThan(String value) {
            addCriterion("APPLY_ENT_RES_NO >", value, "applyEntResNo");
            return (Criteria) this;
        }

        public Criteria andApplyEntResNoGreaterThanOrEqualTo(String value) {
            addCriterion("APPLY_ENT_RES_NO >=", value, "applyEntResNo");
            return (Criteria) this;
        }

        public Criteria andApplyEntResNoLessThan(String value) {
            addCriterion("APPLY_ENT_RES_NO <", value, "applyEntResNo");
            return (Criteria) this;
        }

        public Criteria andApplyEntResNoLessThanOrEqualTo(String value) {
            addCriterion("APPLY_ENT_RES_NO <=", value, "applyEntResNo");
            return (Criteria) this;
        }

        public Criteria andApplyEntResNoLike(String value) {
            addCriterion("APPLY_ENT_RES_NO like", value, "applyEntResNo");
            return (Criteria) this;
        }

        public Criteria andApplyEntResNoNotLike(String value) {
            addCriterion("APPLY_ENT_RES_NO not like", value, "applyEntResNo");
            return (Criteria) this;
        }

        public Criteria andApplyEntResNoIn(List<String> values) {
            addCriterion("APPLY_ENT_RES_NO in", values, "applyEntResNo");
            return (Criteria) this;
        }

        public Criteria andApplyEntResNoNotIn(List<String> values) {
            addCriterion("APPLY_ENT_RES_NO not in", values, "applyEntResNo");
            return (Criteria) this;
        }

        public Criteria andApplyEntResNoBetween(String value1, String value2) {
            addCriterion("APPLY_ENT_RES_NO between", value1, value2, "applyEntResNo");
            return (Criteria) this;
        }

        public Criteria andApplyEntResNoNotBetween(String value1, String value2) {
            addCriterion("APPLY_ENT_RES_NO not between", value1, value2, "applyEntResNo");
            return (Criteria) this;
        }

        public Criteria andSuperEntResNoIsNull() {
            addCriterion("SUPER_ENT_RES_NO is null");
            return (Criteria) this;
        }

        public Criteria andSuperEntResNoIsNotNull() {
            addCriterion("SUPER_ENT_RES_NO is not null");
            return (Criteria) this;
        }

        public Criteria andSuperEntResNoEqualTo(String value) {
            addCriterion("SUPER_ENT_RES_NO =", value, "superEntResNo");
            return (Criteria) this;
        }

        public Criteria andSuperEntResNoNotEqualTo(String value) {
            addCriterion("SUPER_ENT_RES_NO <>", value, "superEntResNo");
            return (Criteria) this;
        }

        public Criteria andSuperEntResNoGreaterThan(String value) {
            addCriterion("SUPER_ENT_RES_NO >", value, "superEntResNo");
            return (Criteria) this;
        }

        public Criteria andSuperEntResNoGreaterThanOrEqualTo(String value) {
            addCriterion("SUPER_ENT_RES_NO >=", value, "superEntResNo");
            return (Criteria) this;
        }

        public Criteria andSuperEntResNoLessThan(String value) {
            addCriterion("SUPER_ENT_RES_NO <", value, "superEntResNo");
            return (Criteria) this;
        }

        public Criteria andSuperEntResNoLessThanOrEqualTo(String value) {
            addCriterion("SUPER_ENT_RES_NO <=", value, "superEntResNo");
            return (Criteria) this;
        }

        public Criteria andSuperEntResNoLike(String value) {
            addCriterion("SUPER_ENT_RES_NO like", value, "superEntResNo");
            return (Criteria) this;
        }

        public Criteria andSuperEntResNoNotLike(String value) {
            addCriterion("SUPER_ENT_RES_NO not like", value, "superEntResNo");
            return (Criteria) this;
        }

        public Criteria andSuperEntResNoIn(List<String> values) {
            addCriterion("SUPER_ENT_RES_NO in", values, "superEntResNo");
            return (Criteria) this;
        }

        public Criteria andSuperEntResNoNotIn(List<String> values) {
            addCriterion("SUPER_ENT_RES_NO not in", values, "superEntResNo");
            return (Criteria) this;
        }

        public Criteria andSuperEntResNoBetween(String value1, String value2) {
            addCriterion("SUPER_ENT_RES_NO between", value1, value2, "superEntResNo");
            return (Criteria) this;
        }

        public Criteria andSuperEntResNoNotBetween(String value1, String value2) {
            addCriterion("SUPER_ENT_RES_NO not between", value1, value2, "superEntResNo");
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

        public Criteria andBusiLicenseNoIsNull() {
            addCriterion("BUSI_LICENSE_NO is null");
            return (Criteria) this;
        }

        public Criteria andBusiLicenseNoIsNotNull() {
            addCriterion("BUSI_LICENSE_NO is not null");
            return (Criteria) this;
        }

        public Criteria andBusiLicenseNoEqualTo(String value) {
            addCriterion("BUSI_LICENSE_NO =", value, "busiLicenseNo");
            return (Criteria) this;
        }

        public Criteria andBusiLicenseNoNotEqualTo(String value) {
            addCriterion("BUSI_LICENSE_NO <>", value, "busiLicenseNo");
            return (Criteria) this;
        }

        public Criteria andBusiLicenseNoGreaterThan(String value) {
            addCriterion("BUSI_LICENSE_NO >", value, "busiLicenseNo");
            return (Criteria) this;
        }

        public Criteria andBusiLicenseNoGreaterThanOrEqualTo(String value) {
            addCriterion("BUSI_LICENSE_NO >=", value, "busiLicenseNo");
            return (Criteria) this;
        }

        public Criteria andBusiLicenseNoLessThan(String value) {
            addCriterion("BUSI_LICENSE_NO <", value, "busiLicenseNo");
            return (Criteria) this;
        }

        public Criteria andBusiLicenseNoLessThanOrEqualTo(String value) {
            addCriterion("BUSI_LICENSE_NO <=", value, "busiLicenseNo");
            return (Criteria) this;
        }

        public Criteria andBusiLicenseNoLike(String value) {
            addCriterion("BUSI_LICENSE_NO like", value, "busiLicenseNo");
            return (Criteria) this;
        }

        public Criteria andBusiLicenseNoNotLike(String value) {
            addCriterion("BUSI_LICENSE_NO not like", value, "busiLicenseNo");
            return (Criteria) this;
        }

        public Criteria andBusiLicenseNoIn(List<String> values) {
            addCriterion("BUSI_LICENSE_NO in", values, "busiLicenseNo");
            return (Criteria) this;
        }

        public Criteria andBusiLicenseNoNotIn(List<String> values) {
            addCriterion("BUSI_LICENSE_NO not in", values, "busiLicenseNo");
            return (Criteria) this;
        }

        public Criteria andBusiLicenseNoBetween(String value1, String value2) {
            addCriterion("BUSI_LICENSE_NO between", value1, value2, "busiLicenseNo");
            return (Criteria) this;
        }

        public Criteria andBusiLicenseNoNotBetween(String value1, String value2) {
            addCriterion("BUSI_LICENSE_NO not between", value1, value2, "busiLicenseNo");
            return (Criteria) this;
        }

        public Criteria andBusiLicenseImgIsNull() {
            addCriterion("BUSI_LICENSE_IMG is null");
            return (Criteria) this;
        }

        public Criteria andBusiLicenseImgIsNotNull() {
            addCriterion("BUSI_LICENSE_IMG is not null");
            return (Criteria) this;
        }

        public Criteria andBusiLicenseImgEqualTo(String value) {
            addCriterion("BUSI_LICENSE_IMG =", value, "busiLicenseImg");
            return (Criteria) this;
        }

        public Criteria andBusiLicenseImgNotEqualTo(String value) {
            addCriterion("BUSI_LICENSE_IMG <>", value, "busiLicenseImg");
            return (Criteria) this;
        }

        public Criteria andBusiLicenseImgGreaterThan(String value) {
            addCriterion("BUSI_LICENSE_IMG >", value, "busiLicenseImg");
            return (Criteria) this;
        }

        public Criteria andBusiLicenseImgGreaterThanOrEqualTo(String value) {
            addCriterion("BUSI_LICENSE_IMG >=", value, "busiLicenseImg");
            return (Criteria) this;
        }

        public Criteria andBusiLicenseImgLessThan(String value) {
            addCriterion("BUSI_LICENSE_IMG <", value, "busiLicenseImg");
            return (Criteria) this;
        }

        public Criteria andBusiLicenseImgLessThanOrEqualTo(String value) {
            addCriterion("BUSI_LICENSE_IMG <=", value, "busiLicenseImg");
            return (Criteria) this;
        }

        public Criteria andBusiLicenseImgLike(String value) {
            addCriterion("BUSI_LICENSE_IMG like", value, "busiLicenseImg");
            return (Criteria) this;
        }

        public Criteria andBusiLicenseImgNotLike(String value) {
            addCriterion("BUSI_LICENSE_IMG not like", value, "busiLicenseImg");
            return (Criteria) this;
        }

        public Criteria andBusiLicenseImgIn(List<String> values) {
            addCriterion("BUSI_LICENSE_IMG in", values, "busiLicenseImg");
            return (Criteria) this;
        }

        public Criteria andBusiLicenseImgNotIn(List<String> values) {
            addCriterion("BUSI_LICENSE_IMG not in", values, "busiLicenseImg");
            return (Criteria) this;
        }

        public Criteria andBusiLicenseImgBetween(String value1, String value2) {
            addCriterion("BUSI_LICENSE_IMG between", value1, value2, "busiLicenseImg");
            return (Criteria) this;
        }

        public Criteria andBusiLicenseImgNotBetween(String value1, String value2) {
            addCriterion("BUSI_LICENSE_IMG not between", value1, value2, "busiLicenseImg");
            return (Criteria) this;
        }

        public Criteria andOrgCodeNoIsNull() {
            addCriterion("ORG_CODE_NO is null");
            return (Criteria) this;
        }

        public Criteria andOrgCodeNoIsNotNull() {
            addCriterion("ORG_CODE_NO is not null");
            return (Criteria) this;
        }

        public Criteria andOrgCodeNoEqualTo(String value) {
            addCriterion("ORG_CODE_NO =", value, "orgCodeNo");
            return (Criteria) this;
        }

        public Criteria andOrgCodeNoNotEqualTo(String value) {
            addCriterion("ORG_CODE_NO <>", value, "orgCodeNo");
            return (Criteria) this;
        }

        public Criteria andOrgCodeNoGreaterThan(String value) {
            addCriterion("ORG_CODE_NO >", value, "orgCodeNo");
            return (Criteria) this;
        }

        public Criteria andOrgCodeNoGreaterThanOrEqualTo(String value) {
            addCriterion("ORG_CODE_NO >=", value, "orgCodeNo");
            return (Criteria) this;
        }

        public Criteria andOrgCodeNoLessThan(String value) {
            addCriterion("ORG_CODE_NO <", value, "orgCodeNo");
            return (Criteria) this;
        }

        public Criteria andOrgCodeNoLessThanOrEqualTo(String value) {
            addCriterion("ORG_CODE_NO <=", value, "orgCodeNo");
            return (Criteria) this;
        }

        public Criteria andOrgCodeNoLike(String value) {
            addCriterion("ORG_CODE_NO like", value, "orgCodeNo");
            return (Criteria) this;
        }

        public Criteria andOrgCodeNoNotLike(String value) {
            addCriterion("ORG_CODE_NO not like", value, "orgCodeNo");
            return (Criteria) this;
        }

        public Criteria andOrgCodeNoIn(List<String> values) {
            addCriterion("ORG_CODE_NO in", values, "orgCodeNo");
            return (Criteria) this;
        }

        public Criteria andOrgCodeNoNotIn(List<String> values) {
            addCriterion("ORG_CODE_NO not in", values, "orgCodeNo");
            return (Criteria) this;
        }

        public Criteria andOrgCodeNoBetween(String value1, String value2) {
            addCriterion("ORG_CODE_NO between", value1, value2, "orgCodeNo");
            return (Criteria) this;
        }

        public Criteria andOrgCodeNoNotBetween(String value1, String value2) {
            addCriterion("ORG_CODE_NO not between", value1, value2, "orgCodeNo");
            return (Criteria) this;
        }

        public Criteria andOrgCodeImgIsNull() {
            addCriterion("ORG_CODE_IMG is null");
            return (Criteria) this;
        }

        public Criteria andOrgCodeImgIsNotNull() {
            addCriterion("ORG_CODE_IMG is not null");
            return (Criteria) this;
        }

        public Criteria andOrgCodeImgEqualTo(String value) {
            addCriterion("ORG_CODE_IMG =", value, "orgCodeImg");
            return (Criteria) this;
        }

        public Criteria andOrgCodeImgNotEqualTo(String value) {
            addCriterion("ORG_CODE_IMG <>", value, "orgCodeImg");
            return (Criteria) this;
        }

        public Criteria andOrgCodeImgGreaterThan(String value) {
            addCriterion("ORG_CODE_IMG >", value, "orgCodeImg");
            return (Criteria) this;
        }

        public Criteria andOrgCodeImgGreaterThanOrEqualTo(String value) {
            addCriterion("ORG_CODE_IMG >=", value, "orgCodeImg");
            return (Criteria) this;
        }

        public Criteria andOrgCodeImgLessThan(String value) {
            addCriterion("ORG_CODE_IMG <", value, "orgCodeImg");
            return (Criteria) this;
        }

        public Criteria andOrgCodeImgLessThanOrEqualTo(String value) {
            addCriterion("ORG_CODE_IMG <=", value, "orgCodeImg");
            return (Criteria) this;
        }

        public Criteria andOrgCodeImgLike(String value) {
            addCriterion("ORG_CODE_IMG like", value, "orgCodeImg");
            return (Criteria) this;
        }

        public Criteria andOrgCodeImgNotLike(String value) {
            addCriterion("ORG_CODE_IMG not like", value, "orgCodeImg");
            return (Criteria) this;
        }

        public Criteria andOrgCodeImgIn(List<String> values) {
            addCriterion("ORG_CODE_IMG in", values, "orgCodeImg");
            return (Criteria) this;
        }

        public Criteria andOrgCodeImgNotIn(List<String> values) {
            addCriterion("ORG_CODE_IMG not in", values, "orgCodeImg");
            return (Criteria) this;
        }

        public Criteria andOrgCodeImgBetween(String value1, String value2) {
            addCriterion("ORG_CODE_IMG between", value1, value2, "orgCodeImg");
            return (Criteria) this;
        }

        public Criteria andOrgCodeImgNotBetween(String value1, String value2) {
            addCriterion("ORG_CODE_IMG not between", value1, value2, "orgCodeImg");
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

        public Criteria andTaxRegImgIsNull() {
            addCriterion("TAX_REG_IMG is null");
            return (Criteria) this;
        }

        public Criteria andTaxRegImgIsNotNull() {
            addCriterion("TAX_REG_IMG is not null");
            return (Criteria) this;
        }

        public Criteria andTaxRegImgEqualTo(String value) {
            addCriterion("TAX_REG_IMG =", value, "taxRegImg");
            return (Criteria) this;
        }

        public Criteria andTaxRegImgNotEqualTo(String value) {
            addCriterion("TAX_REG_IMG <>", value, "taxRegImg");
            return (Criteria) this;
        }

        public Criteria andTaxRegImgGreaterThan(String value) {
            addCriterion("TAX_REG_IMG >", value, "taxRegImg");
            return (Criteria) this;
        }

        public Criteria andTaxRegImgGreaterThanOrEqualTo(String value) {
            addCriterion("TAX_REG_IMG >=", value, "taxRegImg");
            return (Criteria) this;
        }

        public Criteria andTaxRegImgLessThan(String value) {
            addCriterion("TAX_REG_IMG <", value, "taxRegImg");
            return (Criteria) this;
        }

        public Criteria andTaxRegImgLessThanOrEqualTo(String value) {
            addCriterion("TAX_REG_IMG <=", value, "taxRegImg");
            return (Criteria) this;
        }

        public Criteria andTaxRegImgLike(String value) {
            addCriterion("TAX_REG_IMG like", value, "taxRegImg");
            return (Criteria) this;
        }

        public Criteria andTaxRegImgNotLike(String value) {
            addCriterion("TAX_REG_IMG not like", value, "taxRegImg");
            return (Criteria) this;
        }

        public Criteria andTaxRegImgIn(List<String> values) {
            addCriterion("TAX_REG_IMG in", values, "taxRegImg");
            return (Criteria) this;
        }

        public Criteria andTaxRegImgNotIn(List<String> values) {
            addCriterion("TAX_REG_IMG not in", values, "taxRegImg");
            return (Criteria) this;
        }

        public Criteria andTaxRegImgBetween(String value1, String value2) {
            addCriterion("TAX_REG_IMG between", value1, value2, "taxRegImg");
            return (Criteria) this;
        }

        public Criteria andTaxRegImgNotBetween(String value1, String value2) {
            addCriterion("TAX_REG_IMG not between", value1, value2, "taxRegImg");
            return (Criteria) this;
        }

        public Criteria andBuildDateIsNull() {
            addCriterion("BUILD_DATE is null");
            return (Criteria) this;
        }

        public Criteria andBuildDateIsNotNull() {
            addCriterion("BUILD_DATE is not null");
            return (Criteria) this;
        }

        public Criteria andBuildDateEqualTo(Date value) {
            addCriterion("BUILD_DATE =", value, "buildDate");
            return (Criteria) this;
        }

        public Criteria andBuildDateNotEqualTo(Date value) {
            addCriterion("BUILD_DATE <>", value, "buildDate");
            return (Criteria) this;
        }

        public Criteria andBuildDateGreaterThan(Date value) {
            addCriterion("BUILD_DATE >", value, "buildDate");
            return (Criteria) this;
        }

        public Criteria andBuildDateGreaterThanOrEqualTo(Date value) {
            addCriterion("BUILD_DATE >=", value, "buildDate");
            return (Criteria) this;
        }

        public Criteria andBuildDateLessThan(Date value) {
            addCriterion("BUILD_DATE <", value, "buildDate");
            return (Criteria) this;
        }

        public Criteria andBuildDateLessThanOrEqualTo(Date value) {
            addCriterion("BUILD_DATE <=", value, "buildDate");
            return (Criteria) this;
        }

        public Criteria andBuildDateIn(List<Date> values) {
            addCriterion("BUILD_DATE in", values, "buildDate");
            return (Criteria) this;
        }

        public Criteria andBuildDateNotIn(List<Date> values) {
            addCriterion("BUILD_DATE not in", values, "buildDate");
            return (Criteria) this;
        }

        public Criteria andBuildDateBetween(Date value1, Date value2) {
            addCriterion("BUILD_DATE between", value1, value2, "buildDate");
            return (Criteria) this;
        }

        public Criteria andBuildDateNotBetween(Date value1, Date value2) {
            addCriterion("BUILD_DATE not between", value1, value2, "buildDate");
            return (Criteria) this;
        }

        public Criteria andEndDateIsNull() {
            addCriterion("END_DATE is null");
            return (Criteria) this;
        }

        public Criteria andEndDateIsNotNull() {
            addCriterion("END_DATE is not null");
            return (Criteria) this;
        }

        public Criteria andEndDateEqualTo(String value) {
            addCriterion("END_DATE =", value, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateNotEqualTo(String value) {
            addCriterion("END_DATE <>", value, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateGreaterThan(String value) {
            addCriterion("END_DATE >", value, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateGreaterThanOrEqualTo(String value) {
            addCriterion("END_DATE >=", value, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateLessThan(String value) {
            addCriterion("END_DATE <", value, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateLessThanOrEqualTo(String value) {
            addCriterion("END_DATE <=", value, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateLike(String value) {
            addCriterion("END_DATE like", value, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateNotLike(String value) {
            addCriterion("END_DATE not like", value, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateIn(List<String> values) {
            addCriterion("END_DATE in", values, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateNotIn(List<String> values) {
            addCriterion("END_DATE not in", values, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateBetween(String value1, String value2) {
            addCriterion("END_DATE between", value1, value2, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateNotBetween(String value1, String value2) {
            addCriterion("END_DATE not between", value1, value2, "endDate");
            return (Criteria) this;
        }

        public Criteria andNatureIsNull() {
            addCriterion("NATURE is null");
            return (Criteria) this;
        }

        public Criteria andNatureIsNotNull() {
            addCriterion("NATURE is not null");
            return (Criteria) this;
        }

        public Criteria andNatureEqualTo(String value) {
            addCriterion("NATURE =", value, "nature");
            return (Criteria) this;
        }

        public Criteria andNatureNotEqualTo(String value) {
            addCriterion("NATURE <>", value, "nature");
            return (Criteria) this;
        }

        public Criteria andNatureGreaterThan(String value) {
            addCriterion("NATURE >", value, "nature");
            return (Criteria) this;
        }

        public Criteria andNatureGreaterThanOrEqualTo(String value) {
            addCriterion("NATURE >=", value, "nature");
            return (Criteria) this;
        }

        public Criteria andNatureLessThan(String value) {
            addCriterion("NATURE <", value, "nature");
            return (Criteria) this;
        }

        public Criteria andNatureLessThanOrEqualTo(String value) {
            addCriterion("NATURE <=", value, "nature");
            return (Criteria) this;
        }

        public Criteria andNatureLike(String value) {
            addCriterion("NATURE like", value, "nature");
            return (Criteria) this;
        }

        public Criteria andNatureNotLike(String value) {
            addCriterion("NATURE not like", value, "nature");
            return (Criteria) this;
        }

        public Criteria andNatureIn(List<String> values) {
            addCriterion("NATURE in", values, "nature");
            return (Criteria) this;
        }

        public Criteria andNatureNotIn(List<String> values) {
            addCriterion("NATURE not in", values, "nature");
            return (Criteria) this;
        }

        public Criteria andNatureBetween(String value1, String value2) {
            addCriterion("NATURE between", value1, value2, "nature");
            return (Criteria) this;
        }

        public Criteria andNatureNotBetween(String value1, String value2) {
            addCriterion("NATURE not between", value1, value2, "nature");
            return (Criteria) this;
        }

        public Criteria andLegalPersonIsNull() {
            addCriterion("LEGAL_PERSON is null");
            return (Criteria) this;
        }

        public Criteria andLegalPersonIsNotNull() {
            addCriterion("LEGAL_PERSON is not null");
            return (Criteria) this;
        }

        public Criteria andLegalPersonEqualTo(String value) {
            addCriterion("LEGAL_PERSON =", value, "legalPerson");
            return (Criteria) this;
        }

        public Criteria andLegalPersonNotEqualTo(String value) {
            addCriterion("LEGAL_PERSON <>", value, "legalPerson");
            return (Criteria) this;
        }

        public Criteria andLegalPersonGreaterThan(String value) {
            addCriterion("LEGAL_PERSON >", value, "legalPerson");
            return (Criteria) this;
        }

        public Criteria andLegalPersonGreaterThanOrEqualTo(String value) {
            addCriterion("LEGAL_PERSON >=", value, "legalPerson");
            return (Criteria) this;
        }

        public Criteria andLegalPersonLessThan(String value) {
            addCriterion("LEGAL_PERSON <", value, "legalPerson");
            return (Criteria) this;
        }

        public Criteria andLegalPersonLessThanOrEqualTo(String value) {
            addCriterion("LEGAL_PERSON <=", value, "legalPerson");
            return (Criteria) this;
        }

        public Criteria andLegalPersonLike(String value) {
            addCriterion("LEGAL_PERSON like", value, "legalPerson");
            return (Criteria) this;
        }

        public Criteria andLegalPersonNotLike(String value) {
            addCriterion("LEGAL_PERSON not like", value, "legalPerson");
            return (Criteria) this;
        }

        public Criteria andLegalPersonIn(List<String> values) {
            addCriterion("LEGAL_PERSON in", values, "legalPerson");
            return (Criteria) this;
        }

        public Criteria andLegalPersonNotIn(List<String> values) {
            addCriterion("LEGAL_PERSON not in", values, "legalPerson");
            return (Criteria) this;
        }

        public Criteria andLegalPersonBetween(String value1, String value2) {
            addCriterion("LEGAL_PERSON between", value1, value2, "legalPerson");
            return (Criteria) this;
        }

        public Criteria andLegalPersonNotBetween(String value1, String value2) {
            addCriterion("LEGAL_PERSON not between", value1, value2, "legalPerson");
            return (Criteria) this;
        }

        public Criteria andLegalPersonIdIsNull() {
            addCriterion("LEGAL_PERSON_ID is null");
            return (Criteria) this;
        }

        public Criteria andLegalPersonIdIsNotNull() {
            addCriterion("LEGAL_PERSON_ID is not null");
            return (Criteria) this;
        }

        public Criteria andLegalPersonIdEqualTo(String value) {
            addCriterion("LEGAL_PERSON_ID =", value, "legalPersonId");
            return (Criteria) this;
        }

        public Criteria andLegalPersonIdNotEqualTo(String value) {
            addCriterion("LEGAL_PERSON_ID <>", value, "legalPersonId");
            return (Criteria) this;
        }

        public Criteria andLegalPersonIdGreaterThan(String value) {
            addCriterion("LEGAL_PERSON_ID >", value, "legalPersonId");
            return (Criteria) this;
        }

        public Criteria andLegalPersonIdGreaterThanOrEqualTo(String value) {
            addCriterion("LEGAL_PERSON_ID >=", value, "legalPersonId");
            return (Criteria) this;
        }

        public Criteria andLegalPersonIdLessThan(String value) {
            addCriterion("LEGAL_PERSON_ID <", value, "legalPersonId");
            return (Criteria) this;
        }

        public Criteria andLegalPersonIdLessThanOrEqualTo(String value) {
            addCriterion("LEGAL_PERSON_ID <=", value, "legalPersonId");
            return (Criteria) this;
        }

        public Criteria andLegalPersonIdLike(String value) {
            addCriterion("LEGAL_PERSON_ID like", value, "legalPersonId");
            return (Criteria) this;
        }

        public Criteria andLegalPersonIdNotLike(String value) {
            addCriterion("LEGAL_PERSON_ID not like", value, "legalPersonId");
            return (Criteria) this;
        }

        public Criteria andLegalPersonIdIn(List<String> values) {
            addCriterion("LEGAL_PERSON_ID in", values, "legalPersonId");
            return (Criteria) this;
        }

        public Criteria andLegalPersonIdNotIn(List<String> values) {
            addCriterion("LEGAL_PERSON_ID not in", values, "legalPersonId");
            return (Criteria) this;
        }

        public Criteria andLegalPersonIdBetween(String value1, String value2) {
            addCriterion("LEGAL_PERSON_ID between", value1, value2, "legalPersonId");
            return (Criteria) this;
        }

        public Criteria andLegalPersonIdNotBetween(String value1, String value2) {
            addCriterion("LEGAL_PERSON_ID not between", value1, value2, "legalPersonId");
            return (Criteria) this;
        }

        public Criteria andCredentialTypeIsNull() {
            addCriterion("CREDENTIAL_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andCredentialTypeIsNotNull() {
            addCriterion("CREDENTIAL_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andCredentialTypeEqualTo(Short value) {
            addCriterion("CREDENTIAL_TYPE =", value, "credentialType");
            return (Criteria) this;
        }

        public Criteria andCredentialTypeNotEqualTo(Short value) {
            addCriterion("CREDENTIAL_TYPE <>", value, "credentialType");
            return (Criteria) this;
        }

        public Criteria andCredentialTypeGreaterThan(Short value) {
            addCriterion("CREDENTIAL_TYPE >", value, "credentialType");
            return (Criteria) this;
        }

        public Criteria andCredentialTypeGreaterThanOrEqualTo(Short value) {
            addCriterion("CREDENTIAL_TYPE >=", value, "credentialType");
            return (Criteria) this;
        }

        public Criteria andCredentialTypeLessThan(Short value) {
            addCriterion("CREDENTIAL_TYPE <", value, "credentialType");
            return (Criteria) this;
        }

        public Criteria andCredentialTypeLessThanOrEqualTo(Short value) {
            addCriterion("CREDENTIAL_TYPE <=", value, "credentialType");
            return (Criteria) this;
        }

        public Criteria andCredentialTypeIn(List<Short> values) {
            addCriterion("CREDENTIAL_TYPE in", values, "credentialType");
            return (Criteria) this;
        }

        public Criteria andCredentialTypeNotIn(List<Short> values) {
            addCriterion("CREDENTIAL_TYPE not in", values, "credentialType");
            return (Criteria) this;
        }

        public Criteria andCredentialTypeBetween(Short value1, Short value2) {
            addCriterion("CREDENTIAL_TYPE between", value1, value2, "credentialType");
            return (Criteria) this;
        }

        public Criteria andCredentialTypeNotBetween(Short value1, Short value2) {
            addCriterion("CREDENTIAL_TYPE not between", value1, value2, "credentialType");
            return (Criteria) this;
        }

        public Criteria andLegalPersonPicFrontIsNull() {
            addCriterion("LEGAL_PERSON_PIC_FRONT is null");
            return (Criteria) this;
        }

        public Criteria andLegalPersonPicFrontIsNotNull() {
            addCriterion("LEGAL_PERSON_PIC_FRONT is not null");
            return (Criteria) this;
        }

        public Criteria andLegalPersonPicFrontEqualTo(String value) {
            addCriterion("LEGAL_PERSON_PIC_FRONT =", value, "legalPersonPicFront");
            return (Criteria) this;
        }

        public Criteria andLegalPersonPicFrontNotEqualTo(String value) {
            addCriterion("LEGAL_PERSON_PIC_FRONT <>", value, "legalPersonPicFront");
            return (Criteria) this;
        }

        public Criteria andLegalPersonPicFrontGreaterThan(String value) {
            addCriterion("LEGAL_PERSON_PIC_FRONT >", value, "legalPersonPicFront");
            return (Criteria) this;
        }

        public Criteria andLegalPersonPicFrontGreaterThanOrEqualTo(String value) {
            addCriterion("LEGAL_PERSON_PIC_FRONT >=", value, "legalPersonPicFront");
            return (Criteria) this;
        }

        public Criteria andLegalPersonPicFrontLessThan(String value) {
            addCriterion("LEGAL_PERSON_PIC_FRONT <", value, "legalPersonPicFront");
            return (Criteria) this;
        }

        public Criteria andLegalPersonPicFrontLessThanOrEqualTo(String value) {
            addCriterion("LEGAL_PERSON_PIC_FRONT <=", value, "legalPersonPicFront");
            return (Criteria) this;
        }

        public Criteria andLegalPersonPicFrontLike(String value) {
            addCriterion("LEGAL_PERSON_PIC_FRONT like", value, "legalPersonPicFront");
            return (Criteria) this;
        }

        public Criteria andLegalPersonPicFrontNotLike(String value) {
            addCriterion("LEGAL_PERSON_PIC_FRONT not like", value, "legalPersonPicFront");
            return (Criteria) this;
        }

        public Criteria andLegalPersonPicFrontIn(List<String> values) {
            addCriterion("LEGAL_PERSON_PIC_FRONT in", values, "legalPersonPicFront");
            return (Criteria) this;
        }

        public Criteria andLegalPersonPicFrontNotIn(List<String> values) {
            addCriterion("LEGAL_PERSON_PIC_FRONT not in", values, "legalPersonPicFront");
            return (Criteria) this;
        }

        public Criteria andLegalPersonPicFrontBetween(String value1, String value2) {
            addCriterion("LEGAL_PERSON_PIC_FRONT between", value1, value2, "legalPersonPicFront");
            return (Criteria) this;
        }

        public Criteria andLegalPersonPicFrontNotBetween(String value1, String value2) {
            addCriterion("LEGAL_PERSON_PIC_FRONT not between", value1, value2, "legalPersonPicFront");
            return (Criteria) this;
        }

        public Criteria andLegalPersonPicBackIsNull() {
            addCriterion("LEGAL_PERSON_PIC_BACK is null");
            return (Criteria) this;
        }

        public Criteria andLegalPersonPicBackIsNotNull() {
            addCriterion("LEGAL_PERSON_PIC_BACK is not null");
            return (Criteria) this;
        }

        public Criteria andLegalPersonPicBackEqualTo(String value) {
            addCriterion("LEGAL_PERSON_PIC_BACK =", value, "legalPersonPicBack");
            return (Criteria) this;
        }

        public Criteria andLegalPersonPicBackNotEqualTo(String value) {
            addCriterion("LEGAL_PERSON_PIC_BACK <>", value, "legalPersonPicBack");
            return (Criteria) this;
        }

        public Criteria andLegalPersonPicBackGreaterThan(String value) {
            addCriterion("LEGAL_PERSON_PIC_BACK >", value, "legalPersonPicBack");
            return (Criteria) this;
        }

        public Criteria andLegalPersonPicBackGreaterThanOrEqualTo(String value) {
            addCriterion("LEGAL_PERSON_PIC_BACK >=", value, "legalPersonPicBack");
            return (Criteria) this;
        }

        public Criteria andLegalPersonPicBackLessThan(String value) {
            addCriterion("LEGAL_PERSON_PIC_BACK <", value, "legalPersonPicBack");
            return (Criteria) this;
        }

        public Criteria andLegalPersonPicBackLessThanOrEqualTo(String value) {
            addCriterion("LEGAL_PERSON_PIC_BACK <=", value, "legalPersonPicBack");
            return (Criteria) this;
        }

        public Criteria andLegalPersonPicBackLike(String value) {
            addCriterion("LEGAL_PERSON_PIC_BACK like", value, "legalPersonPicBack");
            return (Criteria) this;
        }

        public Criteria andLegalPersonPicBackNotLike(String value) {
            addCriterion("LEGAL_PERSON_PIC_BACK not like", value, "legalPersonPicBack");
            return (Criteria) this;
        }

        public Criteria andLegalPersonPicBackIn(List<String> values) {
            addCriterion("LEGAL_PERSON_PIC_BACK in", values, "legalPersonPicBack");
            return (Criteria) this;
        }

        public Criteria andLegalPersonPicBackNotIn(List<String> values) {
            addCriterion("LEGAL_PERSON_PIC_BACK not in", values, "legalPersonPicBack");
            return (Criteria) this;
        }

        public Criteria andLegalPersonPicBackBetween(String value1, String value2) {
            addCriterion("LEGAL_PERSON_PIC_BACK between", value1, value2, "legalPersonPicBack");
            return (Criteria) this;
        }

        public Criteria andLegalPersonPicBackNotBetween(String value1, String value2) {
            addCriterion("LEGAL_PERSON_PIC_BACK not between", value1, value2, "legalPersonPicBack");
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

        public Criteria andBusiAreaIsNull() {
            addCriterion("BUSI_AREA is null");
            return (Criteria) this;
        }

        public Criteria andBusiAreaIsNotNull() {
            addCriterion("BUSI_AREA is not null");
            return (Criteria) this;
        }

        public Criteria andBusiAreaEqualTo(Long value) {
            addCriterion("BUSI_AREA =", value, "busiArea");
            return (Criteria) this;
        }

        public Criteria andBusiAreaNotEqualTo(Long value) {
            addCriterion("BUSI_AREA <>", value, "busiArea");
            return (Criteria) this;
        }

        public Criteria andBusiAreaGreaterThan(Long value) {
            addCriterion("BUSI_AREA >", value, "busiArea");
            return (Criteria) this;
        }

        public Criteria andBusiAreaGreaterThanOrEqualTo(Long value) {
            addCriterion("BUSI_AREA >=", value, "busiArea");
            return (Criteria) this;
        }

        public Criteria andBusiAreaLessThan(Long value) {
            addCriterion("BUSI_AREA <", value, "busiArea");
            return (Criteria) this;
        }

        public Criteria andBusiAreaLessThanOrEqualTo(Long value) {
            addCriterion("BUSI_AREA <=", value, "busiArea");
            return (Criteria) this;
        }

        public Criteria andBusiAreaIn(List<Long> values) {
            addCriterion("BUSI_AREA in", values, "busiArea");
            return (Criteria) this;
        }

        public Criteria andBusiAreaNotIn(List<Long> values) {
            addCriterion("BUSI_AREA not in", values, "busiArea");
            return (Criteria) this;
        }

        public Criteria andBusiAreaBetween(Long value1, Long value2) {
            addCriterion("BUSI_AREA between", value1, value2, "busiArea");
            return (Criteria) this;
        }

        public Criteria andBusiAreaNotBetween(Long value1, Long value2) {
            addCriterion("BUSI_AREA not between", value1, value2, "busiArea");
            return (Criteria) this;
        }

        public Criteria andEntEmpNumIsNull() {
            addCriterion("ENT_EMP_NUM is null");
            return (Criteria) this;
        }

        public Criteria andEntEmpNumIsNotNull() {
            addCriterion("ENT_EMP_NUM is not null");
            return (Criteria) this;
        }

        public Criteria andEntEmpNumEqualTo(Long value) {
            addCriterion("ENT_EMP_NUM =", value, "entEmpNum");
            return (Criteria) this;
        }

        public Criteria andEntEmpNumNotEqualTo(Long value) {
            addCriterion("ENT_EMP_NUM <>", value, "entEmpNum");
            return (Criteria) this;
        }

        public Criteria andEntEmpNumGreaterThan(Long value) {
            addCriterion("ENT_EMP_NUM >", value, "entEmpNum");
            return (Criteria) this;
        }

        public Criteria andEntEmpNumGreaterThanOrEqualTo(Long value) {
            addCriterion("ENT_EMP_NUM >=", value, "entEmpNum");
            return (Criteria) this;
        }

        public Criteria andEntEmpNumLessThan(Long value) {
            addCriterion("ENT_EMP_NUM <", value, "entEmpNum");
            return (Criteria) this;
        }

        public Criteria andEntEmpNumLessThanOrEqualTo(Long value) {
            addCriterion("ENT_EMP_NUM <=", value, "entEmpNum");
            return (Criteria) this;
        }

        public Criteria andEntEmpNumIn(List<Long> values) {
            addCriterion("ENT_EMP_NUM in", values, "entEmpNum");
            return (Criteria) this;
        }

        public Criteria andEntEmpNumNotIn(List<Long> values) {
            addCriterion("ENT_EMP_NUM not in", values, "entEmpNum");
            return (Criteria) this;
        }

        public Criteria andEntEmpNumBetween(Long value1, Long value2) {
            addCriterion("ENT_EMP_NUM between", value1, value2, "entEmpNum");
            return (Criteria) this;
        }

        public Criteria andEntEmpNumNotBetween(Long value1, Long value2) {
            addCriterion("ENT_EMP_NUM not between", value1, value2, "entEmpNum");
            return (Criteria) this;
        }

        public Criteria andCertificateDepositIsNull() {
            addCriterion("CERTIFICATE_DEPOSIT is null");
            return (Criteria) this;
        }

        public Criteria andCertificateDepositIsNotNull() {
            addCriterion("CERTIFICATE_DEPOSIT is not null");
            return (Criteria) this;
        }

        public Criteria andCertificateDepositEqualTo(String value) {
            addCriterion("CERTIFICATE_DEPOSIT =", value, "certificateDeposit");
            return (Criteria) this;
        }

        public Criteria andCertificateDepositNotEqualTo(String value) {
            addCriterion("CERTIFICATE_DEPOSIT <>", value, "certificateDeposit");
            return (Criteria) this;
        }

        public Criteria andCertificateDepositGreaterThan(String value) {
            addCriterion("CERTIFICATE_DEPOSIT >", value, "certificateDeposit");
            return (Criteria) this;
        }

        public Criteria andCertificateDepositGreaterThanOrEqualTo(String value) {
            addCriterion("CERTIFICATE_DEPOSIT >=", value, "certificateDeposit");
            return (Criteria) this;
        }

        public Criteria andCertificateDepositLessThan(String value) {
            addCriterion("CERTIFICATE_DEPOSIT <", value, "certificateDeposit");
            return (Criteria) this;
        }

        public Criteria andCertificateDepositLessThanOrEqualTo(String value) {
            addCriterion("CERTIFICATE_DEPOSIT <=", value, "certificateDeposit");
            return (Criteria) this;
        }

        public Criteria andCertificateDepositLike(String value) {
            addCriterion("CERTIFICATE_DEPOSIT like", value, "certificateDeposit");
            return (Criteria) this;
        }

        public Criteria andCertificateDepositNotLike(String value) {
            addCriterion("CERTIFICATE_DEPOSIT not like", value, "certificateDeposit");
            return (Criteria) this;
        }

        public Criteria andCertificateDepositIn(List<String> values) {
            addCriterion("CERTIFICATE_DEPOSIT in", values, "certificateDeposit");
            return (Criteria) this;
        }

        public Criteria andCertificateDepositNotIn(List<String> values) {
            addCriterion("CERTIFICATE_DEPOSIT not in", values, "certificateDeposit");
            return (Criteria) this;
        }

        public Criteria andCertificateDepositBetween(String value1, String value2) {
            addCriterion("CERTIFICATE_DEPOSIT between", value1, value2, "certificateDeposit");
            return (Criteria) this;
        }

        public Criteria andCertificateDepositNotBetween(String value1, String value2) {
            addCriterion("CERTIFICATE_DEPOSIT not between", value1, value2, "certificateDeposit");
            return (Criteria) this;
        }

        public Criteria andBusinessScopeIsNull() {
            addCriterion("BUSINESS_SCOPE is null");
            return (Criteria) this;
        }

        public Criteria andBusinessScopeIsNotNull() {
            addCriterion("BUSINESS_SCOPE is not null");
            return (Criteria) this;
        }

        public Criteria andBusinessScopeEqualTo(String value) {
            addCriterion("BUSINESS_SCOPE =", value, "businessScope");
            return (Criteria) this;
        }

        public Criteria andBusinessScopeNotEqualTo(String value) {
            addCriterion("BUSINESS_SCOPE <>", value, "businessScope");
            return (Criteria) this;
        }

        public Criteria andBusinessScopeGreaterThan(String value) {
            addCriterion("BUSINESS_SCOPE >", value, "businessScope");
            return (Criteria) this;
        }

        public Criteria andBusinessScopeGreaterThanOrEqualTo(String value) {
            addCriterion("BUSINESS_SCOPE >=", value, "businessScope");
            return (Criteria) this;
        }

        public Criteria andBusinessScopeLessThan(String value) {
            addCriterion("BUSINESS_SCOPE <", value, "businessScope");
            return (Criteria) this;
        }

        public Criteria andBusinessScopeLessThanOrEqualTo(String value) {
            addCriterion("BUSINESS_SCOPE <=", value, "businessScope");
            return (Criteria) this;
        }

        public Criteria andBusinessScopeLike(String value) {
            addCriterion("BUSINESS_SCOPE like", value, "businessScope");
            return (Criteria) this;
        }

        public Criteria andBusinessScopeNotLike(String value) {
            addCriterion("BUSINESS_SCOPE not like", value, "businessScope");
            return (Criteria) this;
        }

        public Criteria andBusinessScopeIn(List<String> values) {
            addCriterion("BUSINESS_SCOPE in", values, "businessScope");
            return (Criteria) this;
        }

        public Criteria andBusinessScopeNotIn(List<String> values) {
            addCriterion("BUSINESS_SCOPE not in", values, "businessScope");
            return (Criteria) this;
        }

        public Criteria andBusinessScopeBetween(String value1, String value2) {
            addCriterion("BUSINESS_SCOPE between", value1, value2, "businessScope");
            return (Criteria) this;
        }

        public Criteria andBusinessScopeNotBetween(String value1, String value2) {
            addCriterion("BUSINESS_SCOPE not between", value1, value2, "businessScope");
            return (Criteria) this;
        }

        public Criteria andIntroductionIsNull() {
            addCriterion("INTRODUCTION is null");
            return (Criteria) this;
        }

        public Criteria andIntroductionIsNotNull() {
            addCriterion("INTRODUCTION is not null");
            return (Criteria) this;
        }

        public Criteria andIntroductionEqualTo(String value) {
            addCriterion("INTRODUCTION =", value, "introduction");
            return (Criteria) this;
        }

        public Criteria andIntroductionNotEqualTo(String value) {
            addCriterion("INTRODUCTION <>", value, "introduction");
            return (Criteria) this;
        }

        public Criteria andIntroductionGreaterThan(String value) {
            addCriterion("INTRODUCTION >", value, "introduction");
            return (Criteria) this;
        }

        public Criteria andIntroductionGreaterThanOrEqualTo(String value) {
            addCriterion("INTRODUCTION >=", value, "introduction");
            return (Criteria) this;
        }

        public Criteria andIntroductionLessThan(String value) {
            addCriterion("INTRODUCTION <", value, "introduction");
            return (Criteria) this;
        }

        public Criteria andIntroductionLessThanOrEqualTo(String value) {
            addCriterion("INTRODUCTION <=", value, "introduction");
            return (Criteria) this;
        }

        public Criteria andIntroductionLike(String value) {
            addCriterion("INTRODUCTION like", value, "introduction");
            return (Criteria) this;
        }

        public Criteria andIntroductionNotLike(String value) {
            addCriterion("INTRODUCTION not like", value, "introduction");
            return (Criteria) this;
        }

        public Criteria andIntroductionIn(List<String> values) {
            addCriterion("INTRODUCTION in", values, "introduction");
            return (Criteria) this;
        }

        public Criteria andIntroductionNotIn(List<String> values) {
            addCriterion("INTRODUCTION not in", values, "introduction");
            return (Criteria) this;
        }

        public Criteria andIntroductionBetween(String value1, String value2) {
            addCriterion("INTRODUCTION between", value1, value2, "introduction");
            return (Criteria) this;
        }

        public Criteria andIntroductionNotBetween(String value1, String value2) {
            addCriterion("INTRODUCTION not between", value1, value2, "introduction");
            return (Criteria) this;
        }

        public Criteria andContactPersonIsNull() {
            addCriterion("CONTACT_PERSON is null");
            return (Criteria) this;
        }

        public Criteria andContactPersonIsNotNull() {
            addCriterion("CONTACT_PERSON is not null");
            return (Criteria) this;
        }

        public Criteria andContactPersonEqualTo(String value) {
            addCriterion("CONTACT_PERSON =", value, "contactPerson");
            return (Criteria) this;
        }

        public Criteria andContactPersonNotEqualTo(String value) {
            addCriterion("CONTACT_PERSON <>", value, "contactPerson");
            return (Criteria) this;
        }

        public Criteria andContactPersonGreaterThan(String value) {
            addCriterion("CONTACT_PERSON >", value, "contactPerson");
            return (Criteria) this;
        }

        public Criteria andContactPersonGreaterThanOrEqualTo(String value) {
            addCriterion("CONTACT_PERSON >=", value, "contactPerson");
            return (Criteria) this;
        }

        public Criteria andContactPersonLessThan(String value) {
            addCriterion("CONTACT_PERSON <", value, "contactPerson");
            return (Criteria) this;
        }

        public Criteria andContactPersonLessThanOrEqualTo(String value) {
            addCriterion("CONTACT_PERSON <=", value, "contactPerson");
            return (Criteria) this;
        }

        public Criteria andContactPersonLike(String value) {
            addCriterion("CONTACT_PERSON like", value, "contactPerson");
            return (Criteria) this;
        }

        public Criteria andContactPersonNotLike(String value) {
            addCriterion("CONTACT_PERSON not like", value, "contactPerson");
            return (Criteria) this;
        }

        public Criteria andContactPersonIn(List<String> values) {
            addCriterion("CONTACT_PERSON in", values, "contactPerson");
            return (Criteria) this;
        }

        public Criteria andContactPersonNotIn(List<String> values) {
            addCriterion("CONTACT_PERSON not in", values, "contactPerson");
            return (Criteria) this;
        }

        public Criteria andContactPersonBetween(String value1, String value2) {
            addCriterion("CONTACT_PERSON between", value1, value2, "contactPerson");
            return (Criteria) this;
        }

        public Criteria andContactPersonNotBetween(String value1, String value2) {
            addCriterion("CONTACT_PERSON not between", value1, value2, "contactPerson");
            return (Criteria) this;
        }

        public Criteria andContactDutyIsNull() {
            addCriterion("CONTACT_DUTY is null");
            return (Criteria) this;
        }

        public Criteria andContactDutyIsNotNull() {
            addCriterion("CONTACT_DUTY is not null");
            return (Criteria) this;
        }

        public Criteria andContactDutyEqualTo(String value) {
            addCriterion("CONTACT_DUTY =", value, "contactDuty");
            return (Criteria) this;
        }

        public Criteria andContactDutyNotEqualTo(String value) {
            addCriterion("CONTACT_DUTY <>", value, "contactDuty");
            return (Criteria) this;
        }

        public Criteria andContactDutyGreaterThan(String value) {
            addCriterion("CONTACT_DUTY >", value, "contactDuty");
            return (Criteria) this;
        }

        public Criteria andContactDutyGreaterThanOrEqualTo(String value) {
            addCriterion("CONTACT_DUTY >=", value, "contactDuty");
            return (Criteria) this;
        }

        public Criteria andContactDutyLessThan(String value) {
            addCriterion("CONTACT_DUTY <", value, "contactDuty");
            return (Criteria) this;
        }

        public Criteria andContactDutyLessThanOrEqualTo(String value) {
            addCriterion("CONTACT_DUTY <=", value, "contactDuty");
            return (Criteria) this;
        }

        public Criteria andContactDutyLike(String value) {
            addCriterion("CONTACT_DUTY like", value, "contactDuty");
            return (Criteria) this;
        }

        public Criteria andContactDutyNotLike(String value) {
            addCriterion("CONTACT_DUTY not like", value, "contactDuty");
            return (Criteria) this;
        }

        public Criteria andContactDutyIn(List<String> values) {
            addCriterion("CONTACT_DUTY in", values, "contactDuty");
            return (Criteria) this;
        }

        public Criteria andContactDutyNotIn(List<String> values) {
            addCriterion("CONTACT_DUTY not in", values, "contactDuty");
            return (Criteria) this;
        }

        public Criteria andContactDutyBetween(String value1, String value2) {
            addCriterion("CONTACT_DUTY between", value1, value2, "contactDuty");
            return (Criteria) this;
        }

        public Criteria andContactDutyNotBetween(String value1, String value2) {
            addCriterion("CONTACT_DUTY not between", value1, value2, "contactDuty");
            return (Criteria) this;
        }

        public Criteria andContactPhoneIsNull() {
            addCriterion("CONTACT_PHONE is null");
            return (Criteria) this;
        }

        public Criteria andContactPhoneIsNotNull() {
            addCriterion("CONTACT_PHONE is not null");
            return (Criteria) this;
        }

        public Criteria andContactPhoneEqualTo(String value) {
            addCriterion("CONTACT_PHONE =", value, "contactPhone");
            return (Criteria) this;
        }

        public Criteria andContactPhoneNotEqualTo(String value) {
            addCriterion("CONTACT_PHONE <>", value, "contactPhone");
            return (Criteria) this;
        }

        public Criteria andContactPhoneGreaterThan(String value) {
            addCriterion("CONTACT_PHONE >", value, "contactPhone");
            return (Criteria) this;
        }

        public Criteria andContactPhoneGreaterThanOrEqualTo(String value) {
            addCriterion("CONTACT_PHONE >=", value, "contactPhone");
            return (Criteria) this;
        }

        public Criteria andContactPhoneLessThan(String value) {
            addCriterion("CONTACT_PHONE <", value, "contactPhone");
            return (Criteria) this;
        }

        public Criteria andContactPhoneLessThanOrEqualTo(String value) {
            addCriterion("CONTACT_PHONE <=", value, "contactPhone");
            return (Criteria) this;
        }

        public Criteria andContactPhoneLike(String value) {
            addCriterion("CONTACT_PHONE like", value, "contactPhone");
            return (Criteria) this;
        }

        public Criteria andContactPhoneNotLike(String value) {
            addCriterion("CONTACT_PHONE not like", value, "contactPhone");
            return (Criteria) this;
        }

        public Criteria andContactPhoneIn(List<String> values) {
            addCriterion("CONTACT_PHONE in", values, "contactPhone");
            return (Criteria) this;
        }

        public Criteria andContactPhoneNotIn(List<String> values) {
            addCriterion("CONTACT_PHONE not in", values, "contactPhone");
            return (Criteria) this;
        }

        public Criteria andContactPhoneBetween(String value1, String value2) {
            addCriterion("CONTACT_PHONE between", value1, value2, "contactPhone");
            return (Criteria) this;
        }

        public Criteria andContactPhoneNotBetween(String value1, String value2) {
            addCriterion("CONTACT_PHONE not between", value1, value2, "contactPhone");
            return (Criteria) this;
        }

        public Criteria andContactAddrIsNull() {
            addCriterion("CONTACT_ADDR is null");
            return (Criteria) this;
        }

        public Criteria andContactAddrIsNotNull() {
            addCriterion("CONTACT_ADDR is not null");
            return (Criteria) this;
        }

        public Criteria andContactAddrEqualTo(String value) {
            addCriterion("CONTACT_ADDR =", value, "contactAddr");
            return (Criteria) this;
        }

        public Criteria andContactAddrNotEqualTo(String value) {
            addCriterion("CONTACT_ADDR <>", value, "contactAddr");
            return (Criteria) this;
        }

        public Criteria andContactAddrGreaterThan(String value) {
            addCriterion("CONTACT_ADDR >", value, "contactAddr");
            return (Criteria) this;
        }

        public Criteria andContactAddrGreaterThanOrEqualTo(String value) {
            addCriterion("CONTACT_ADDR >=", value, "contactAddr");
            return (Criteria) this;
        }

        public Criteria andContactAddrLessThan(String value) {
            addCriterion("CONTACT_ADDR <", value, "contactAddr");
            return (Criteria) this;
        }

        public Criteria andContactAddrLessThanOrEqualTo(String value) {
            addCriterion("CONTACT_ADDR <=", value, "contactAddr");
            return (Criteria) this;
        }

        public Criteria andContactAddrLike(String value) {
            addCriterion("CONTACT_ADDR like", value, "contactAddr");
            return (Criteria) this;
        }

        public Criteria andContactAddrNotLike(String value) {
            addCriterion("CONTACT_ADDR not like", value, "contactAddr");
            return (Criteria) this;
        }

        public Criteria andContactAddrIn(List<String> values) {
            addCriterion("CONTACT_ADDR in", values, "contactAddr");
            return (Criteria) this;
        }

        public Criteria andContactAddrNotIn(List<String> values) {
            addCriterion("CONTACT_ADDR not in", values, "contactAddr");
            return (Criteria) this;
        }

        public Criteria andContactAddrBetween(String value1, String value2) {
            addCriterion("CONTACT_ADDR between", value1, value2, "contactAddr");
            return (Criteria) this;
        }

        public Criteria andContactAddrNotBetween(String value1, String value2) {
            addCriterion("CONTACT_ADDR not between", value1, value2, "contactAddr");
            return (Criteria) this;
        }

        public Criteria andContactEmailIsNull() {
            addCriterion("CONTACT_EMAIL is null");
            return (Criteria) this;
        }

        public Criteria andContactEmailIsNotNull() {
            addCriterion("CONTACT_EMAIL is not null");
            return (Criteria) this;
        }

        public Criteria andContactEmailEqualTo(String value) {
            addCriterion("CONTACT_EMAIL =", value, "contactEmail");
            return (Criteria) this;
        }

        public Criteria andContactEmailNotEqualTo(String value) {
            addCriterion("CONTACT_EMAIL <>", value, "contactEmail");
            return (Criteria) this;
        }

        public Criteria andContactEmailGreaterThan(String value) {
            addCriterion("CONTACT_EMAIL >", value, "contactEmail");
            return (Criteria) this;
        }

        public Criteria andContactEmailGreaterThanOrEqualTo(String value) {
            addCriterion("CONTACT_EMAIL >=", value, "contactEmail");
            return (Criteria) this;
        }

        public Criteria andContactEmailLessThan(String value) {
            addCriterion("CONTACT_EMAIL <", value, "contactEmail");
            return (Criteria) this;
        }

        public Criteria andContactEmailLessThanOrEqualTo(String value) {
            addCriterion("CONTACT_EMAIL <=", value, "contactEmail");
            return (Criteria) this;
        }

        public Criteria andContactEmailLike(String value) {
            addCriterion("CONTACT_EMAIL like", value, "contactEmail");
            return (Criteria) this;
        }

        public Criteria andContactEmailNotLike(String value) {
            addCriterion("CONTACT_EMAIL not like", value, "contactEmail");
            return (Criteria) this;
        }

        public Criteria andContactEmailIn(List<String> values) {
            addCriterion("CONTACT_EMAIL in", values, "contactEmail");
            return (Criteria) this;
        }

        public Criteria andContactEmailNotIn(List<String> values) {
            addCriterion("CONTACT_EMAIL not in", values, "contactEmail");
            return (Criteria) this;
        }

        public Criteria andContactEmailBetween(String value1, String value2) {
            addCriterion("CONTACT_EMAIL between", value1, value2, "contactEmail");
            return (Criteria) this;
        }

        public Criteria andContactEmailNotBetween(String value1, String value2) {
            addCriterion("CONTACT_EMAIL not between", value1, value2, "contactEmail");
            return (Criteria) this;
        }

        public Criteria andOfficeQqIsNull() {
            addCriterion("OFFICE_QQ is null");
            return (Criteria) this;
        }

        public Criteria andOfficeQqIsNotNull() {
            addCriterion("OFFICE_QQ is not null");
            return (Criteria) this;
        }

        public Criteria andOfficeQqEqualTo(String value) {
            addCriterion("OFFICE_QQ =", value, "officeQq");
            return (Criteria) this;
        }

        public Criteria andOfficeQqNotEqualTo(String value) {
            addCriterion("OFFICE_QQ <>", value, "officeQq");
            return (Criteria) this;
        }

        public Criteria andOfficeQqGreaterThan(String value) {
            addCriterion("OFFICE_QQ >", value, "officeQq");
            return (Criteria) this;
        }

        public Criteria andOfficeQqGreaterThanOrEqualTo(String value) {
            addCriterion("OFFICE_QQ >=", value, "officeQq");
            return (Criteria) this;
        }

        public Criteria andOfficeQqLessThan(String value) {
            addCriterion("OFFICE_QQ <", value, "officeQq");
            return (Criteria) this;
        }

        public Criteria andOfficeQqLessThanOrEqualTo(String value) {
            addCriterion("OFFICE_QQ <=", value, "officeQq");
            return (Criteria) this;
        }

        public Criteria andOfficeQqLike(String value) {
            addCriterion("OFFICE_QQ like", value, "officeQq");
            return (Criteria) this;
        }

        public Criteria andOfficeQqNotLike(String value) {
            addCriterion("OFFICE_QQ not like", value, "officeQq");
            return (Criteria) this;
        }

        public Criteria andOfficeQqIn(List<String> values) {
            addCriterion("OFFICE_QQ in", values, "officeQq");
            return (Criteria) this;
        }

        public Criteria andOfficeQqNotIn(List<String> values) {
            addCriterion("OFFICE_QQ not in", values, "officeQq");
            return (Criteria) this;
        }

        public Criteria andOfficeQqBetween(String value1, String value2) {
            addCriterion("OFFICE_QQ between", value1, value2, "officeQq");
            return (Criteria) this;
        }

        public Criteria andOfficeQqNotBetween(String value1, String value2) {
            addCriterion("OFFICE_QQ not between", value1, value2, "officeQq");
            return (Criteria) this;
        }

        public Criteria andOfficeTelIsNull() {
            addCriterion("OFFICE_TEL is null");
            return (Criteria) this;
        }

        public Criteria andOfficeTelIsNotNull() {
            addCriterion("OFFICE_TEL is not null");
            return (Criteria) this;
        }

        public Criteria andOfficeTelEqualTo(String value) {
            addCriterion("OFFICE_TEL =", value, "officeTel");
            return (Criteria) this;
        }

        public Criteria andOfficeTelNotEqualTo(String value) {
            addCriterion("OFFICE_TEL <>", value, "officeTel");
            return (Criteria) this;
        }

        public Criteria andOfficeTelGreaterThan(String value) {
            addCriterion("OFFICE_TEL >", value, "officeTel");
            return (Criteria) this;
        }

        public Criteria andOfficeTelGreaterThanOrEqualTo(String value) {
            addCriterion("OFFICE_TEL >=", value, "officeTel");
            return (Criteria) this;
        }

        public Criteria andOfficeTelLessThan(String value) {
            addCriterion("OFFICE_TEL <", value, "officeTel");
            return (Criteria) this;
        }

        public Criteria andOfficeTelLessThanOrEqualTo(String value) {
            addCriterion("OFFICE_TEL <=", value, "officeTel");
            return (Criteria) this;
        }

        public Criteria andOfficeTelLike(String value) {
            addCriterion("OFFICE_TEL like", value, "officeTel");
            return (Criteria) this;
        }

        public Criteria andOfficeTelNotLike(String value) {
            addCriterion("OFFICE_TEL not like", value, "officeTel");
            return (Criteria) this;
        }

        public Criteria andOfficeTelIn(List<String> values) {
            addCriterion("OFFICE_TEL in", values, "officeTel");
            return (Criteria) this;
        }

        public Criteria andOfficeTelNotIn(List<String> values) {
            addCriterion("OFFICE_TEL not in", values, "officeTel");
            return (Criteria) this;
        }

        public Criteria andOfficeTelBetween(String value1, String value2) {
            addCriterion("OFFICE_TEL between", value1, value2, "officeTel");
            return (Criteria) this;
        }

        public Criteria andOfficeTelNotBetween(String value1, String value2) {
            addCriterion("OFFICE_TEL not between", value1, value2, "officeTel");
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

        public Criteria andLogoImgIsNull() {
            addCriterion("LOGO_IMG is null");
            return (Criteria) this;
        }

        public Criteria andLogoImgIsNotNull() {
            addCriterion("LOGO_IMG is not null");
            return (Criteria) this;
        }

        public Criteria andLogoImgEqualTo(String value) {
            addCriterion("LOGO_IMG =", value, "logoImg");
            return (Criteria) this;
        }

        public Criteria andLogoImgNotEqualTo(String value) {
            addCriterion("LOGO_IMG <>", value, "logoImg");
            return (Criteria) this;
        }

        public Criteria andLogoImgGreaterThan(String value) {
            addCriterion("LOGO_IMG >", value, "logoImg");
            return (Criteria) this;
        }

        public Criteria andLogoImgGreaterThanOrEqualTo(String value) {
            addCriterion("LOGO_IMG >=", value, "logoImg");
            return (Criteria) this;
        }

        public Criteria andLogoImgLessThan(String value) {
            addCriterion("LOGO_IMG <", value, "logoImg");
            return (Criteria) this;
        }

        public Criteria andLogoImgLessThanOrEqualTo(String value) {
            addCriterion("LOGO_IMG <=", value, "logoImg");
            return (Criteria) this;
        }

        public Criteria andLogoImgLike(String value) {
            addCriterion("LOGO_IMG like", value, "logoImg");
            return (Criteria) this;
        }

        public Criteria andLogoImgNotLike(String value) {
            addCriterion("LOGO_IMG not like", value, "logoImg");
            return (Criteria) this;
        }

        public Criteria andLogoImgIn(List<String> values) {
            addCriterion("LOGO_IMG in", values, "logoImg");
            return (Criteria) this;
        }

        public Criteria andLogoImgNotIn(List<String> values) {
            addCriterion("LOGO_IMG not in", values, "logoImg");
            return (Criteria) this;
        }

        public Criteria andLogoImgBetween(String value1, String value2) {
            addCriterion("LOGO_IMG between", value1, value2, "logoImg");
            return (Criteria) this;
        }

        public Criteria andLogoImgNotBetween(String value1, String value2) {
            addCriterion("LOGO_IMG not between", value1, value2, "logoImg");
            return (Criteria) this;
        }

        public Criteria andIndustryIsNull() {
            addCriterion("INDUSTRY is null");
            return (Criteria) this;
        }

        public Criteria andIndustryIsNotNull() {
            addCriterion("INDUSTRY is not null");
            return (Criteria) this;
        }

        public Criteria andIndustryEqualTo(Integer value) {
            addCriterion("INDUSTRY =", value, "industry");
            return (Criteria) this;
        }

        public Criteria andIndustryNotEqualTo(Integer value) {
            addCriterion("INDUSTRY <>", value, "industry");
            return (Criteria) this;
        }

        public Criteria andIndustryGreaterThan(Integer value) {
            addCriterion("INDUSTRY >", value, "industry");
            return (Criteria) this;
        }

        public Criteria andIndustryGreaterThanOrEqualTo(Integer value) {
            addCriterion("INDUSTRY >=", value, "industry");
            return (Criteria) this;
        }

        public Criteria andIndustryLessThan(Integer value) {
            addCriterion("INDUSTRY <", value, "industry");
            return (Criteria) this;
        }

        public Criteria andIndustryLessThanOrEqualTo(Integer value) {
            addCriterion("INDUSTRY <=", value, "industry");
            return (Criteria) this;
        }

        public Criteria andIndustryIn(List<Integer> values) {
            addCriterion("INDUSTRY in", values, "industry");
            return (Criteria) this;
        }

        public Criteria andIndustryNotIn(List<Integer> values) {
            addCriterion("INDUSTRY not in", values, "industry");
            return (Criteria) this;
        }

        public Criteria andIndustryBetween(Integer value1, Integer value2) {
            addCriterion("INDUSTRY between", value1, value2, "industry");
            return (Criteria) this;
        }

        public Criteria andIndustryNotBetween(Integer value1, Integer value2) {
            addCriterion("INDUSTRY not between", value1, value2, "industry");
            return (Criteria) this;
        }

        public Criteria andPostCodeIsNull() {
            addCriterion("POST_CODE is null");
            return (Criteria) this;
        }

        public Criteria andPostCodeIsNotNull() {
            addCriterion("POST_CODE is not null");
            return (Criteria) this;
        }

        public Criteria andPostCodeEqualTo(String value) {
            addCriterion("POST_CODE =", value, "postCode");
            return (Criteria) this;
        }

        public Criteria andPostCodeNotEqualTo(String value) {
            addCriterion("POST_CODE <>", value, "postCode");
            return (Criteria) this;
        }

        public Criteria andPostCodeGreaterThan(String value) {
            addCriterion("POST_CODE >", value, "postCode");
            return (Criteria) this;
        }

        public Criteria andPostCodeGreaterThanOrEqualTo(String value) {
            addCriterion("POST_CODE >=", value, "postCode");
            return (Criteria) this;
        }

        public Criteria andPostCodeLessThan(String value) {
            addCriterion("POST_CODE <", value, "postCode");
            return (Criteria) this;
        }

        public Criteria andPostCodeLessThanOrEqualTo(String value) {
            addCriterion("POST_CODE <=", value, "postCode");
            return (Criteria) this;
        }

        public Criteria andPostCodeLike(String value) {
            addCriterion("POST_CODE like", value, "postCode");
            return (Criteria) this;
        }

        public Criteria andPostCodeNotLike(String value) {
            addCriterion("POST_CODE not like", value, "postCode");
            return (Criteria) this;
        }

        public Criteria andPostCodeIn(List<String> values) {
            addCriterion("POST_CODE in", values, "postCode");
            return (Criteria) this;
        }

        public Criteria andPostCodeNotIn(List<String> values) {
            addCriterion("POST_CODE not in", values, "postCode");
            return (Criteria) this;
        }

        public Criteria andPostCodeBetween(String value1, String value2) {
            addCriterion("POST_CODE between", value1, value2, "postCode");
            return (Criteria) this;
        }

        public Criteria andPostCodeNotBetween(String value1, String value2) {
            addCriterion("POST_CODE not between", value1, value2, "postCode");
            return (Criteria) this;
        }

        public Criteria andWebSiteIsNull() {
            addCriterion("WEB_SITE is null");
            return (Criteria) this;
        }

        public Criteria andWebSiteIsNotNull() {
            addCriterion("WEB_SITE is not null");
            return (Criteria) this;
        }

        public Criteria andWebSiteEqualTo(String value) {
            addCriterion("WEB_SITE =", value, "webSite");
            return (Criteria) this;
        }

        public Criteria andWebSiteNotEqualTo(String value) {
            addCriterion("WEB_SITE <>", value, "webSite");
            return (Criteria) this;
        }

        public Criteria andWebSiteGreaterThan(String value) {
            addCriterion("WEB_SITE >", value, "webSite");
            return (Criteria) this;
        }

        public Criteria andWebSiteGreaterThanOrEqualTo(String value) {
            addCriterion("WEB_SITE >=", value, "webSite");
            return (Criteria) this;
        }

        public Criteria andWebSiteLessThan(String value) {
            addCriterion("WEB_SITE <", value, "webSite");
            return (Criteria) this;
        }

        public Criteria andWebSiteLessThanOrEqualTo(String value) {
            addCriterion("WEB_SITE <=", value, "webSite");
            return (Criteria) this;
        }

        public Criteria andWebSiteLike(String value) {
            addCriterion("WEB_SITE like", value, "webSite");
            return (Criteria) this;
        }

        public Criteria andWebSiteNotLike(String value) {
            addCriterion("WEB_SITE not like", value, "webSite");
            return (Criteria) this;
        }

        public Criteria andWebSiteIn(List<String> values) {
            addCriterion("WEB_SITE in", values, "webSite");
            return (Criteria) this;
        }

        public Criteria andWebSiteNotIn(List<String> values) {
            addCriterion("WEB_SITE not in", values, "webSite");
            return (Criteria) this;
        }

        public Criteria andWebSiteBetween(String value1, String value2) {
            addCriterion("WEB_SITE between", value1, value2, "webSite");
            return (Criteria) this;
        }

        public Criteria andWebSiteNotBetween(String value1, String value2) {
            addCriterion("WEB_SITE not between", value1, value2, "webSite");
            return (Criteria) this;
        }

        public Criteria andEnsureInfoIsNull() {
            addCriterion("ENSURE_INFO is null");
            return (Criteria) this;
        }

        public Criteria andEnsureInfoIsNotNull() {
            addCriterion("ENSURE_INFO is not null");
            return (Criteria) this;
        }

        public Criteria andEnsureInfoEqualTo(String value) {
            addCriterion("ENSURE_INFO =", value, "ensureInfo");
            return (Criteria) this;
        }

        public Criteria andEnsureInfoNotEqualTo(String value) {
            addCriterion("ENSURE_INFO <>", value, "ensureInfo");
            return (Criteria) this;
        }

        public Criteria andEnsureInfoGreaterThan(String value) {
            addCriterion("ENSURE_INFO >", value, "ensureInfo");
            return (Criteria) this;
        }

        public Criteria andEnsureInfoGreaterThanOrEqualTo(String value) {
            addCriterion("ENSURE_INFO >=", value, "ensureInfo");
            return (Criteria) this;
        }

        public Criteria andEnsureInfoLessThan(String value) {
            addCriterion("ENSURE_INFO <", value, "ensureInfo");
            return (Criteria) this;
        }

        public Criteria andEnsureInfoLessThanOrEqualTo(String value) {
            addCriterion("ENSURE_INFO <=", value, "ensureInfo");
            return (Criteria) this;
        }

        public Criteria andEnsureInfoLike(String value) {
            addCriterion("ENSURE_INFO like", value, "ensureInfo");
            return (Criteria) this;
        }

        public Criteria andEnsureInfoNotLike(String value) {
            addCriterion("ENSURE_INFO not like", value, "ensureInfo");
            return (Criteria) this;
        }

        public Criteria andEnsureInfoIn(List<String> values) {
            addCriterion("ENSURE_INFO in", values, "ensureInfo");
            return (Criteria) this;
        }

        public Criteria andEnsureInfoNotIn(List<String> values) {
            addCriterion("ENSURE_INFO not in", values, "ensureInfo");
            return (Criteria) this;
        }

        public Criteria andEnsureInfoBetween(String value1, String value2) {
            addCriterion("ENSURE_INFO between", value1, value2, "ensureInfo");
            return (Criteria) this;
        }

        public Criteria andEnsureInfoNotBetween(String value1, String value2) {
            addCriterion("ENSURE_INFO not between", value1, value2, "ensureInfo");
            return (Criteria) this;
        }

        public Criteria andLongitudeIsNull() {
            addCriterion("LONGITUDE is null");
            return (Criteria) this;
        }

        public Criteria andLongitudeIsNotNull() {
            addCriterion("LONGITUDE is not null");
            return (Criteria) this;
        }

        public Criteria andLongitudeEqualTo(String value) {
            addCriterion("LONGITUDE =", value, "longitude");
            return (Criteria) this;
        }

        public Criteria andLongitudeNotEqualTo(String value) {
            addCriterion("LONGITUDE <>", value, "longitude");
            return (Criteria) this;
        }

        public Criteria andLongitudeGreaterThan(String value) {
            addCriterion("LONGITUDE >", value, "longitude");
            return (Criteria) this;
        }

        public Criteria andLongitudeGreaterThanOrEqualTo(String value) {
            addCriterion("LONGITUDE >=", value, "longitude");
            return (Criteria) this;
        }

        public Criteria andLongitudeLessThan(String value) {
            addCriterion("LONGITUDE <", value, "longitude");
            return (Criteria) this;
        }

        public Criteria andLongitudeLessThanOrEqualTo(String value) {
            addCriterion("LONGITUDE <=", value, "longitude");
            return (Criteria) this;
        }

        public Criteria andLongitudeLike(String value) {
            addCriterion("LONGITUDE like", value, "longitude");
            return (Criteria) this;
        }

        public Criteria andLongitudeNotLike(String value) {
            addCriterion("LONGITUDE not like", value, "longitude");
            return (Criteria) this;
        }

        public Criteria andLongitudeIn(List<String> values) {
            addCriterion("LONGITUDE in", values, "longitude");
            return (Criteria) this;
        }

        public Criteria andLongitudeNotIn(List<String> values) {
            addCriterion("LONGITUDE not in", values, "longitude");
            return (Criteria) this;
        }

        public Criteria andLongitudeBetween(String value1, String value2) {
            addCriterion("LONGITUDE between", value1, value2, "longitude");
            return (Criteria) this;
        }

        public Criteria andLongitudeNotBetween(String value1, String value2) {
            addCriterion("LONGITUDE not between", value1, value2, "longitude");
            return (Criteria) this;
        }

        public Criteria andLatitudeIsNull() {
            addCriterion("LATITUDE is null");
            return (Criteria) this;
        }

        public Criteria andLatitudeIsNotNull() {
            addCriterion("LATITUDE is not null");
            return (Criteria) this;
        }

        public Criteria andLatitudeEqualTo(String value) {
            addCriterion("LATITUDE =", value, "latitude");
            return (Criteria) this;
        }

        public Criteria andLatitudeNotEqualTo(String value) {
            addCriterion("LATITUDE <>", value, "latitude");
            return (Criteria) this;
        }

        public Criteria andLatitudeGreaterThan(String value) {
            addCriterion("LATITUDE >", value, "latitude");
            return (Criteria) this;
        }

        public Criteria andLatitudeGreaterThanOrEqualTo(String value) {
            addCriterion("LATITUDE >=", value, "latitude");
            return (Criteria) this;
        }

        public Criteria andLatitudeLessThan(String value) {
            addCriterion("LATITUDE <", value, "latitude");
            return (Criteria) this;
        }

        public Criteria andLatitudeLessThanOrEqualTo(String value) {
            addCriterion("LATITUDE <=", value, "latitude");
            return (Criteria) this;
        }

        public Criteria andLatitudeLike(String value) {
            addCriterion("LATITUDE like", value, "latitude");
            return (Criteria) this;
        }

        public Criteria andLatitudeNotLike(String value) {
            addCriterion("LATITUDE not like", value, "latitude");
            return (Criteria) this;
        }

        public Criteria andLatitudeIn(List<String> values) {
            addCriterion("LATITUDE in", values, "latitude");
            return (Criteria) this;
        }

        public Criteria andLatitudeNotIn(List<String> values) {
            addCriterion("LATITUDE not in", values, "latitude");
            return (Criteria) this;
        }

        public Criteria andLatitudeBetween(String value1, String value2) {
            addCriterion("LATITUDE between", value1, value2, "latitude");
            return (Criteria) this;
        }

        public Criteria andLatitudeNotBetween(String value1, String value2) {
            addCriterion("LATITUDE not between", value1, value2, "latitude");
            return (Criteria) this;
        }

        public Criteria andTaxRateIsNull() {
            addCriterion("TAX_RATE is null");
            return (Criteria) this;
        }

        public Criteria andTaxRateIsNotNull() {
            addCriterion("TAX_RATE is not null");
            return (Criteria) this;
        }

        public Criteria andTaxRateEqualTo(BigDecimal value) {
            addCriterion("TAX_RATE =", value, "taxRate");
            return (Criteria) this;
        }

        public Criteria andTaxRateNotEqualTo(BigDecimal value) {
            addCriterion("TAX_RATE <>", value, "taxRate");
            return (Criteria) this;
        }

        public Criteria andTaxRateGreaterThan(BigDecimal value) {
            addCriterion("TAX_RATE >", value, "taxRate");
            return (Criteria) this;
        }

        public Criteria andTaxRateGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("TAX_RATE >=", value, "taxRate");
            return (Criteria) this;
        }

        public Criteria andTaxRateLessThan(BigDecimal value) {
            addCriterion("TAX_RATE <", value, "taxRate");
            return (Criteria) this;
        }

        public Criteria andTaxRateLessThanOrEqualTo(BigDecimal value) {
            addCriterion("TAX_RATE <=", value, "taxRate");
            return (Criteria) this;
        }

        public Criteria andTaxRateIn(List<BigDecimal> values) {
            addCriterion("TAX_RATE in", values, "taxRate");
            return (Criteria) this;
        }

        public Criteria andTaxRateNotIn(List<BigDecimal> values) {
            addCriterion("TAX_RATE not in", values, "taxRate");
            return (Criteria) this;
        }

        public Criteria andTaxRateBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("TAX_RATE between", value1, value2, "taxRate");
            return (Criteria) this;
        }

        public Criteria andTaxRateNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("TAX_RATE not between", value1, value2, "taxRate");
            return (Criteria) this;
        }

        public Criteria andContactProxyIsNull() {
            addCriterion("CONTACT_PROXY is null");
            return (Criteria) this;
        }

        public Criteria andContactProxyIsNotNull() {
            addCriterion("CONTACT_PROXY is not null");
            return (Criteria) this;
        }

        public Criteria andContactProxyEqualTo(String value) {
            addCriterion("CONTACT_PROXY =", value, "contactProxy");
            return (Criteria) this;
        }

        public Criteria andContactProxyNotEqualTo(String value) {
            addCriterion("CONTACT_PROXY <>", value, "contactProxy");
            return (Criteria) this;
        }

        public Criteria andContactProxyGreaterThan(String value) {
            addCriterion("CONTACT_PROXY >", value, "contactProxy");
            return (Criteria) this;
        }

        public Criteria andContactProxyGreaterThanOrEqualTo(String value) {
            addCriterion("CONTACT_PROXY >=", value, "contactProxy");
            return (Criteria) this;
        }

        public Criteria andContactProxyLessThan(String value) {
            addCriterion("CONTACT_PROXY <", value, "contactProxy");
            return (Criteria) this;
        }

        public Criteria andContactProxyLessThanOrEqualTo(String value) {
            addCriterion("CONTACT_PROXY <=", value, "contactProxy");
            return (Criteria) this;
        }

        public Criteria andContactProxyLike(String value) {
            addCriterion("CONTACT_PROXY like", value, "contactProxy");
            return (Criteria) this;
        }

        public Criteria andContactProxyNotLike(String value) {
            addCriterion("CONTACT_PROXY not like", value, "contactProxy");
            return (Criteria) this;
        }

        public Criteria andContactProxyIn(List<String> values) {
            addCriterion("CONTACT_PROXY in", values, "contactProxy");
            return (Criteria) this;
        }

        public Criteria andContactProxyNotIn(List<String> values) {
            addCriterion("CONTACT_PROXY not in", values, "contactProxy");
            return (Criteria) this;
        }

        public Criteria andContactProxyBetween(String value1, String value2) {
            addCriterion("CONTACT_PROXY between", value1, value2, "contactProxy");
            return (Criteria) this;
        }

        public Criteria andContactProxyNotBetween(String value1, String value2) {
            addCriterion("CONTACT_PROXY not between", value1, value2, "contactProxy");
            return (Criteria) this;
        }

        public Criteria andHelpAgreementIsNull() {
            addCriterion("HELP_AGREEMENT is null");
            return (Criteria) this;
        }

        public Criteria andHelpAgreementIsNotNull() {
            addCriterion("HELP_AGREEMENT is not null");
            return (Criteria) this;
        }

        public Criteria andHelpAgreementEqualTo(String value) {
            addCriterion("HELP_AGREEMENT =", value, "helpAgreement");
            return (Criteria) this;
        }

        public Criteria andHelpAgreementNotEqualTo(String value) {
            addCriterion("HELP_AGREEMENT <>", value, "helpAgreement");
            return (Criteria) this;
        }

        public Criteria andHelpAgreementGreaterThan(String value) {
            addCriterion("HELP_AGREEMENT >", value, "helpAgreement");
            return (Criteria) this;
        }

        public Criteria andHelpAgreementGreaterThanOrEqualTo(String value) {
            addCriterion("HELP_AGREEMENT >=", value, "helpAgreement");
            return (Criteria) this;
        }

        public Criteria andHelpAgreementLessThan(String value) {
            addCriterion("HELP_AGREEMENT <", value, "helpAgreement");
            return (Criteria) this;
        }

        public Criteria andHelpAgreementLessThanOrEqualTo(String value) {
            addCriterion("HELP_AGREEMENT <=", value, "helpAgreement");
            return (Criteria) this;
        }

        public Criteria andHelpAgreementLike(String value) {
            addCriterion("HELP_AGREEMENT like", value, "helpAgreement");
            return (Criteria) this;
        }

        public Criteria andHelpAgreementNotLike(String value) {
            addCriterion("HELP_AGREEMENT not like", value, "helpAgreement");
            return (Criteria) this;
        }

        public Criteria andHelpAgreementIn(List<String> values) {
            addCriterion("HELP_AGREEMENT in", values, "helpAgreement");
            return (Criteria) this;
        }

        public Criteria andHelpAgreementNotIn(List<String> values) {
            addCriterion("HELP_AGREEMENT not in", values, "helpAgreement");
            return (Criteria) this;
        }

        public Criteria andHelpAgreementBetween(String value1, String value2) {
            addCriterion("HELP_AGREEMENT between", value1, value2, "helpAgreement");
            return (Criteria) this;
        }

        public Criteria andHelpAgreementNotBetween(String value1, String value2) {
            addCriterion("HELP_AGREEMENT not between", value1, value2, "helpAgreement");
            return (Criteria) this;
        }

        public Criteria andArtResTypestIsNull() {
            addCriterion("ART_RES_TYPEST is null");
            return (Criteria) this;
        }

        public Criteria andArtResTypestIsNotNull() {
            addCriterion("ART_RES_TYPEST is not null");
            return (Criteria) this;
        }

        public Criteria andArtResTypestEqualTo(Short value) {
            addCriterion("ART_RES_TYPEST =", value, "artResTypest");
            return (Criteria) this;
        }

        public Criteria andArtResTypestNotEqualTo(Short value) {
            addCriterion("ART_RES_TYPEST <>", value, "artResTypest");
            return (Criteria) this;
        }

        public Criteria andArtResTypestGreaterThan(Short value) {
            addCriterion("ART_RES_TYPEST >", value, "artResTypest");
            return (Criteria) this;
        }

        public Criteria andArtResTypestGreaterThanOrEqualTo(Short value) {
            addCriterion("ART_RES_TYPEST >=", value, "artResTypest");
            return (Criteria) this;
        }

        public Criteria andArtResTypestLessThan(Short value) {
            addCriterion("ART_RES_TYPEST <", value, "artResTypest");
            return (Criteria) this;
        }

        public Criteria andArtResTypestLessThanOrEqualTo(Short value) {
            addCriterion("ART_RES_TYPEST <=", value, "artResTypest");
            return (Criteria) this;
        }

        public Criteria andArtResTypestIn(List<Short> values) {
            addCriterion("ART_RES_TYPEST in", values, "artResTypest");
            return (Criteria) this;
        }

        public Criteria andArtResTypestNotIn(List<Short> values) {
            addCriterion("ART_RES_TYPEST not in", values, "artResTypest");
            return (Criteria) this;
        }

        public Criteria andArtResTypestBetween(Short value1, Short value2) {
            addCriterion("ART_RES_TYPEST between", value1, value2, "artResTypest");
            return (Criteria) this;
        }

        public Criteria andArtResTypestNotBetween(Short value1, Short value2) {
            addCriterion("ART_RES_TYPEST not between", value1, value2, "artResTypest");
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

        public Criteria andEntRegCodeIsNull() {
            addCriterion("ENT_REG_CODE is null");
            return (Criteria) this;
        }

        public Criteria andEntRegCodeIsNotNull() {
            addCriterion("ENT_REG_CODE is not null");
            return (Criteria) this;
        }

        public Criteria andEntRegCodeEqualTo(String value) {
            addCriterion("ENT_REG_CODE =", value, "entRegCode");
            return (Criteria) this;
        }

        public Criteria andEntRegCodeNotEqualTo(String value) {
            addCriterion("ENT_REG_CODE <>", value, "entRegCode");
            return (Criteria) this;
        }

        public Criteria andEntRegCodeGreaterThan(String value) {
            addCriterion("ENT_REG_CODE >", value, "entRegCode");
            return (Criteria) this;
        }

        public Criteria andEntRegCodeGreaterThanOrEqualTo(String value) {
            addCriterion("ENT_REG_CODE >=", value, "entRegCode");
            return (Criteria) this;
        }

        public Criteria andEntRegCodeLessThan(String value) {
            addCriterion("ENT_REG_CODE <", value, "entRegCode");
            return (Criteria) this;
        }

        public Criteria andEntRegCodeLessThanOrEqualTo(String value) {
            addCriterion("ENT_REG_CODE <=", value, "entRegCode");
            return (Criteria) this;
        }

        public Criteria andEntRegCodeLike(String value) {
            addCriterion("ENT_REG_CODE like", value, "entRegCode");
            return (Criteria) this;
        }

        public Criteria andEntRegCodeNotLike(String value) {
            addCriterion("ENT_REG_CODE not like", value, "entRegCode");
            return (Criteria) this;
        }

        public Criteria andEntRegCodeIn(List<String> values) {
            addCriterion("ENT_REG_CODE in", values, "entRegCode");
            return (Criteria) this;
        }

        public Criteria andEntRegCodeNotIn(List<String> values) {
            addCriterion("ENT_REG_CODE not in", values, "entRegCode");
            return (Criteria) this;
        }

        public Criteria andEntRegCodeBetween(String value1, String value2) {
            addCriterion("ENT_REG_CODE between", value1, value2, "entRegCode");
            return (Criteria) this;
        }

        public Criteria andEntRegCodeNotBetween(String value1, String value2) {
            addCriterion("ENT_REG_CODE not between", value1, value2, "entRegCode");
            return (Criteria) this;
        }

        public Criteria andEntRegImgIsNull() {
            addCriterion("ENT_REG_IMG is null");
            return (Criteria) this;
        }

        public Criteria andEntRegImgIsNotNull() {
            addCriterion("ENT_REG_IMG is not null");
            return (Criteria) this;
        }

        public Criteria andEntRegImgEqualTo(String value) {
            addCriterion("ENT_REG_IMG =", value, "entRegImg");
            return (Criteria) this;
        }

        public Criteria andEntRegImgNotEqualTo(String value) {
            addCriterion("ENT_REG_IMG <>", value, "entRegImg");
            return (Criteria) this;
        }

        public Criteria andEntRegImgGreaterThan(String value) {
            addCriterion("ENT_REG_IMG >", value, "entRegImg");
            return (Criteria) this;
        }

        public Criteria andEntRegImgGreaterThanOrEqualTo(String value) {
            addCriterion("ENT_REG_IMG >=", value, "entRegImg");
            return (Criteria) this;
        }

        public Criteria andEntRegImgLessThan(String value) {
            addCriterion("ENT_REG_IMG <", value, "entRegImg");
            return (Criteria) this;
        }

        public Criteria andEntRegImgLessThanOrEqualTo(String value) {
            addCriterion("ENT_REG_IMG <=", value, "entRegImg");
            return (Criteria) this;
        }

        public Criteria andEntRegImgLike(String value) {
            addCriterion("ENT_REG_IMG like", value, "entRegImg");
            return (Criteria) this;
        }

        public Criteria andEntRegImgNotLike(String value) {
            addCriterion("ENT_REG_IMG not like", value, "entRegImg");
            return (Criteria) this;
        }

        public Criteria andEntRegImgIn(List<String> values) {
            addCriterion("ENT_REG_IMG in", values, "entRegImg");
            return (Criteria) this;
        }

        public Criteria andEntRegImgNotIn(List<String> values) {
            addCriterion("ENT_REG_IMG not in", values, "entRegImg");
            return (Criteria) this;
        }

        public Criteria andEntRegImgBetween(String value1, String value2) {
            addCriterion("ENT_REG_IMG between", value1, value2, "entRegImg");
            return (Criteria) this;
        }

        public Criteria andEntRegImgNotBetween(String value1, String value2) {
            addCriterion("ENT_REG_IMG not between", value1, value2, "entRegImg");
            return (Criteria) this;
        }

        public Criteria andBusiRegCodeIsNull() {
            addCriterion("BUSI_REG_CODE is null");
            return (Criteria) this;
        }

        public Criteria andBusiRegCodeIsNotNull() {
            addCriterion("BUSI_REG_CODE is not null");
            return (Criteria) this;
        }

        public Criteria andBusiRegCodeEqualTo(String value) {
            addCriterion("BUSI_REG_CODE =", value, "busiRegCode");
            return (Criteria) this;
        }

        public Criteria andBusiRegCodeNotEqualTo(String value) {
            addCriterion("BUSI_REG_CODE <>", value, "busiRegCode");
            return (Criteria) this;
        }

        public Criteria andBusiRegCodeGreaterThan(String value) {
            addCriterion("BUSI_REG_CODE >", value, "busiRegCode");
            return (Criteria) this;
        }

        public Criteria andBusiRegCodeGreaterThanOrEqualTo(String value) {
            addCriterion("BUSI_REG_CODE >=", value, "busiRegCode");
            return (Criteria) this;
        }

        public Criteria andBusiRegCodeLessThan(String value) {
            addCriterion("BUSI_REG_CODE <", value, "busiRegCode");
            return (Criteria) this;
        }

        public Criteria andBusiRegCodeLessThanOrEqualTo(String value) {
            addCriterion("BUSI_REG_CODE <=", value, "busiRegCode");
            return (Criteria) this;
        }

        public Criteria andBusiRegCodeLike(String value) {
            addCriterion("BUSI_REG_CODE like", value, "busiRegCode");
            return (Criteria) this;
        }

        public Criteria andBusiRegCodeNotLike(String value) {
            addCriterion("BUSI_REG_CODE not like", value, "busiRegCode");
            return (Criteria) this;
        }

        public Criteria andBusiRegCodeIn(List<String> values) {
            addCriterion("BUSI_REG_CODE in", values, "busiRegCode");
            return (Criteria) this;
        }

        public Criteria andBusiRegCodeNotIn(List<String> values) {
            addCriterion("BUSI_REG_CODE not in", values, "busiRegCode");
            return (Criteria) this;
        }

        public Criteria andBusiRegCodeBetween(String value1, String value2) {
            addCriterion("BUSI_REG_CODE between", value1, value2, "busiRegCode");
            return (Criteria) this;
        }

        public Criteria andBusiRegCodeNotBetween(String value1, String value2) {
            addCriterion("BUSI_REG_CODE not between", value1, value2, "busiRegCode");
            return (Criteria) this;
        }

        public Criteria andBusiRegImgIsNull() {
            addCriterion("BUSI_REG_IMG is null");
            return (Criteria) this;
        }

        public Criteria andBusiRegImgIsNotNull() {
            addCriterion("BUSI_REG_IMG is not null");
            return (Criteria) this;
        }

        public Criteria andBusiRegImgEqualTo(String value) {
            addCriterion("BUSI_REG_IMG =", value, "busiRegImg");
            return (Criteria) this;
        }

        public Criteria andBusiRegImgNotEqualTo(String value) {
            addCriterion("BUSI_REG_IMG <>", value, "busiRegImg");
            return (Criteria) this;
        }

        public Criteria andBusiRegImgGreaterThan(String value) {
            addCriterion("BUSI_REG_IMG >", value, "busiRegImg");
            return (Criteria) this;
        }

        public Criteria andBusiRegImgGreaterThanOrEqualTo(String value) {
            addCriterion("BUSI_REG_IMG >=", value, "busiRegImg");
            return (Criteria) this;
        }

        public Criteria andBusiRegImgLessThan(String value) {
            addCriterion("BUSI_REG_IMG <", value, "busiRegImg");
            return (Criteria) this;
        }

        public Criteria andBusiRegImgLessThanOrEqualTo(String value) {
            addCriterion("BUSI_REG_IMG <=", value, "busiRegImg");
            return (Criteria) this;
        }

        public Criteria andBusiRegImgLike(String value) {
            addCriterion("BUSI_REG_IMG like", value, "busiRegImg");
            return (Criteria) this;
        }

        public Criteria andBusiRegImgNotLike(String value) {
            addCriterion("BUSI_REG_IMG not like", value, "busiRegImg");
            return (Criteria) this;
        }

        public Criteria andBusiRegImgIn(List<String> values) {
            addCriterion("BUSI_REG_IMG in", values, "busiRegImg");
            return (Criteria) this;
        }

        public Criteria andBusiRegImgNotIn(List<String> values) {
            addCriterion("BUSI_REG_IMG not in", values, "busiRegImg");
            return (Criteria) this;
        }

        public Criteria andBusiRegImgBetween(String value1, String value2) {
            addCriterion("BUSI_REG_IMG between", value1, value2, "busiRegImg");
            return (Criteria) this;
        }

        public Criteria andBusiRegImgNotBetween(String value1, String value2) {
            addCriterion("BUSI_REG_IMG not between", value1, value2, "busiRegImg");
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