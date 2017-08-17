package com.gy.hsi.fs.mapper.vo.dbbs01;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.gy.hsi.fs.mapper.vo.AbstractPage;

public class TBsCompanyStopExample extends AbstractPage {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TBsCompanyStopExample() {
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

        public Criteria andApplyTypeIsNull() {
            addCriterion("APPLY_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andApplyTypeIsNotNull() {
            addCriterion("APPLY_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andApplyTypeEqualTo(Short value) {
            addCriterion("APPLY_TYPE =", value, "applyType");
            return (Criteria) this;
        }

        public Criteria andApplyTypeNotEqualTo(Short value) {
            addCriterion("APPLY_TYPE <>", value, "applyType");
            return (Criteria) this;
        }

        public Criteria andApplyTypeGreaterThan(Short value) {
            addCriterion("APPLY_TYPE >", value, "applyType");
            return (Criteria) this;
        }

        public Criteria andApplyTypeGreaterThanOrEqualTo(Short value) {
            addCriterion("APPLY_TYPE >=", value, "applyType");
            return (Criteria) this;
        }

        public Criteria andApplyTypeLessThan(Short value) {
            addCriterion("APPLY_TYPE <", value, "applyType");
            return (Criteria) this;
        }

        public Criteria andApplyTypeLessThanOrEqualTo(Short value) {
            addCriterion("APPLY_TYPE <=", value, "applyType");
            return (Criteria) this;
        }

        public Criteria andApplyTypeIn(List<Short> values) {
            addCriterion("APPLY_TYPE in", values, "applyType");
            return (Criteria) this;
        }

        public Criteria andApplyTypeNotIn(List<Short> values) {
            addCriterion("APPLY_TYPE not in", values, "applyType");
            return (Criteria) this;
        }

        public Criteria andApplyTypeBetween(Short value1, Short value2) {
            addCriterion("APPLY_TYPE between", value1, value2, "applyType");
            return (Criteria) this;
        }

        public Criteria andApplyTypeNotBetween(Short value1, Short value2) {
            addCriterion("APPLY_TYPE not between", value1, value2, "applyType");
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

        public Criteria andContactNameIsNull() {
            addCriterion("CONTACT_NAME is null");
            return (Criteria) this;
        }

        public Criteria andContactNameIsNotNull() {
            addCriterion("CONTACT_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andContactNameEqualTo(String value) {
            addCriterion("CONTACT_NAME =", value, "contactName");
            return (Criteria) this;
        }

        public Criteria andContactNameNotEqualTo(String value) {
            addCriterion("CONTACT_NAME <>", value, "contactName");
            return (Criteria) this;
        }

        public Criteria andContactNameGreaterThan(String value) {
            addCriterion("CONTACT_NAME >", value, "contactName");
            return (Criteria) this;
        }

        public Criteria andContactNameGreaterThanOrEqualTo(String value) {
            addCriterion("CONTACT_NAME >=", value, "contactName");
            return (Criteria) this;
        }

        public Criteria andContactNameLessThan(String value) {
            addCriterion("CONTACT_NAME <", value, "contactName");
            return (Criteria) this;
        }

        public Criteria andContactNameLessThanOrEqualTo(String value) {
            addCriterion("CONTACT_NAME <=", value, "contactName");
            return (Criteria) this;
        }

        public Criteria andContactNameLike(String value) {
            addCriterion("CONTACT_NAME like", value, "contactName");
            return (Criteria) this;
        }

        public Criteria andContactNameNotLike(String value) {
            addCriterion("CONTACT_NAME not like", value, "contactName");
            return (Criteria) this;
        }

        public Criteria andContactNameIn(List<String> values) {
            addCriterion("CONTACT_NAME in", values, "contactName");
            return (Criteria) this;
        }

        public Criteria andContactNameNotIn(List<String> values) {
            addCriterion("CONTACT_NAME not in", values, "contactName");
            return (Criteria) this;
        }

        public Criteria andContactNameBetween(String value1, String value2) {
            addCriterion("CONTACT_NAME between", value1, value2, "contactName");
            return (Criteria) this;
        }

        public Criteria andContactNameNotBetween(String value1, String value2) {
            addCriterion("CONTACT_NAME not between", value1, value2, "contactName");
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

        public Criteria andApplyResonIsNull() {
            addCriterion("APPLY_RESON is null");
            return (Criteria) this;
        }

        public Criteria andApplyResonIsNotNull() {
            addCriterion("APPLY_RESON is not null");
            return (Criteria) this;
        }

        public Criteria andApplyResonEqualTo(String value) {
            addCriterion("APPLY_RESON =", value, "applyReson");
            return (Criteria) this;
        }

        public Criteria andApplyResonNotEqualTo(String value) {
            addCriterion("APPLY_RESON <>", value, "applyReson");
            return (Criteria) this;
        }

        public Criteria andApplyResonGreaterThan(String value) {
            addCriterion("APPLY_RESON >", value, "applyReson");
            return (Criteria) this;
        }

        public Criteria andApplyResonGreaterThanOrEqualTo(String value) {
            addCriterion("APPLY_RESON >=", value, "applyReson");
            return (Criteria) this;
        }

        public Criteria andApplyResonLessThan(String value) {
            addCriterion("APPLY_RESON <", value, "applyReson");
            return (Criteria) this;
        }

        public Criteria andApplyResonLessThanOrEqualTo(String value) {
            addCriterion("APPLY_RESON <=", value, "applyReson");
            return (Criteria) this;
        }

        public Criteria andApplyResonLike(String value) {
            addCriterion("APPLY_RESON like", value, "applyReson");
            return (Criteria) this;
        }

        public Criteria andApplyResonNotLike(String value) {
            addCriterion("APPLY_RESON not like", value, "applyReson");
            return (Criteria) this;
        }

        public Criteria andApplyResonIn(List<String> values) {
            addCriterion("APPLY_RESON in", values, "applyReson");
            return (Criteria) this;
        }

        public Criteria andApplyResonNotIn(List<String> values) {
            addCriterion("APPLY_RESON not in", values, "applyReson");
            return (Criteria) this;
        }

        public Criteria andApplyResonBetween(String value1, String value2) {
            addCriterion("APPLY_RESON between", value1, value2, "applyReson");
            return (Criteria) this;
        }

        public Criteria andApplyResonNotBetween(String value1, String value2) {
            addCriterion("APPLY_RESON not between", value1, value2, "applyReson");
            return (Criteria) this;
        }

        public Criteria andOldStatusIsNull() {
            addCriterion("OLD_STATUS is null");
            return (Criteria) this;
        }

        public Criteria andOldStatusIsNotNull() {
            addCriterion("OLD_STATUS is not null");
            return (Criteria) this;
        }

        public Criteria andOldStatusEqualTo(Short value) {
            addCriterion("OLD_STATUS =", value, "oldStatus");
            return (Criteria) this;
        }

        public Criteria andOldStatusNotEqualTo(Short value) {
            addCriterion("OLD_STATUS <>", value, "oldStatus");
            return (Criteria) this;
        }

        public Criteria andOldStatusGreaterThan(Short value) {
            addCriterion("OLD_STATUS >", value, "oldStatus");
            return (Criteria) this;
        }

        public Criteria andOldStatusGreaterThanOrEqualTo(Short value) {
            addCriterion("OLD_STATUS >=", value, "oldStatus");
            return (Criteria) this;
        }

        public Criteria andOldStatusLessThan(Short value) {
            addCriterion("OLD_STATUS <", value, "oldStatus");
            return (Criteria) this;
        }

        public Criteria andOldStatusLessThanOrEqualTo(Short value) {
            addCriterion("OLD_STATUS <=", value, "oldStatus");
            return (Criteria) this;
        }

        public Criteria andOldStatusIn(List<Short> values) {
            addCriterion("OLD_STATUS in", values, "oldStatus");
            return (Criteria) this;
        }

        public Criteria andOldStatusNotIn(List<Short> values) {
            addCriterion("OLD_STATUS not in", values, "oldStatus");
            return (Criteria) this;
        }

        public Criteria andOldStatusBetween(Short value1, Short value2) {
            addCriterion("OLD_STATUS between", value1, value2, "oldStatus");
            return (Criteria) this;
        }

        public Criteria andOldStatusNotBetween(Short value1, Short value2) {
            addCriterion("OLD_STATUS not between", value1, value2, "oldStatus");
            return (Criteria) this;
        }

        public Criteria andBizApplyFileIsNull() {
            addCriterion("BIZ_APPLY_FILE is null");
            return (Criteria) this;
        }

        public Criteria andBizApplyFileIsNotNull() {
            addCriterion("BIZ_APPLY_FILE is not null");
            return (Criteria) this;
        }

        public Criteria andBizApplyFileEqualTo(String value) {
            addCriterion("BIZ_APPLY_FILE =", value, "bizApplyFile");
            return (Criteria) this;
        }

        public Criteria andBizApplyFileNotEqualTo(String value) {
            addCriterion("BIZ_APPLY_FILE <>", value, "bizApplyFile");
            return (Criteria) this;
        }

        public Criteria andBizApplyFileGreaterThan(String value) {
            addCriterion("BIZ_APPLY_FILE >", value, "bizApplyFile");
            return (Criteria) this;
        }

        public Criteria andBizApplyFileGreaterThanOrEqualTo(String value) {
            addCriterion("BIZ_APPLY_FILE >=", value, "bizApplyFile");
            return (Criteria) this;
        }

        public Criteria andBizApplyFileLessThan(String value) {
            addCriterion("BIZ_APPLY_FILE <", value, "bizApplyFile");
            return (Criteria) this;
        }

        public Criteria andBizApplyFileLessThanOrEqualTo(String value) {
            addCriterion("BIZ_APPLY_FILE <=", value, "bizApplyFile");
            return (Criteria) this;
        }

        public Criteria andBizApplyFileLike(String value) {
            addCriterion("BIZ_APPLY_FILE like", value, "bizApplyFile");
            return (Criteria) this;
        }

        public Criteria andBizApplyFileNotLike(String value) {
            addCriterion("BIZ_APPLY_FILE not like", value, "bizApplyFile");
            return (Criteria) this;
        }

        public Criteria andBizApplyFileIn(List<String> values) {
            addCriterion("BIZ_APPLY_FILE in", values, "bizApplyFile");
            return (Criteria) this;
        }

        public Criteria andBizApplyFileNotIn(List<String> values) {
            addCriterion("BIZ_APPLY_FILE not in", values, "bizApplyFile");
            return (Criteria) this;
        }

        public Criteria andBizApplyFileBetween(String value1, String value2) {
            addCriterion("BIZ_APPLY_FILE between", value1, value2, "bizApplyFile");
            return (Criteria) this;
        }

        public Criteria andBizApplyFileNotBetween(String value1, String value2) {
            addCriterion("BIZ_APPLY_FILE not between", value1, value2, "bizApplyFile");
            return (Criteria) this;
        }

        public Criteria andBankacctidIsNull() {
            addCriterion("BANKACCTID is null");
            return (Criteria) this;
        }

        public Criteria andBankacctidIsNotNull() {
            addCriterion("BANKACCTID is not null");
            return (Criteria) this;
        }

        public Criteria andBankacctidEqualTo(String value) {
            addCriterion("BANKACCTID =", value, "bankacctid");
            return (Criteria) this;
        }

        public Criteria andBankacctidNotEqualTo(String value) {
            addCriterion("BANKACCTID <>", value, "bankacctid");
            return (Criteria) this;
        }

        public Criteria andBankacctidGreaterThan(String value) {
            addCriterion("BANKACCTID >", value, "bankacctid");
            return (Criteria) this;
        }

        public Criteria andBankacctidGreaterThanOrEqualTo(String value) {
            addCriterion("BANKACCTID >=", value, "bankacctid");
            return (Criteria) this;
        }

        public Criteria andBankacctidLessThan(String value) {
            addCriterion("BANKACCTID <", value, "bankacctid");
            return (Criteria) this;
        }

        public Criteria andBankacctidLessThanOrEqualTo(String value) {
            addCriterion("BANKACCTID <=", value, "bankacctid");
            return (Criteria) this;
        }

        public Criteria andBankacctidLike(String value) {
            addCriterion("BANKACCTID like", value, "bankacctid");
            return (Criteria) this;
        }

        public Criteria andBankacctidNotLike(String value) {
            addCriterion("BANKACCTID not like", value, "bankacctid");
            return (Criteria) this;
        }

        public Criteria andBankacctidIn(List<String> values) {
            addCriterion("BANKACCTID in", values, "bankacctid");
            return (Criteria) this;
        }

        public Criteria andBankacctidNotIn(List<String> values) {
            addCriterion("BANKACCTID not in", values, "bankacctid");
            return (Criteria) this;
        }

        public Criteria andBankacctidBetween(String value1, String value2) {
            addCriterion("BANKACCTID between", value1, value2, "bankacctid");
            return (Criteria) this;
        }

        public Criteria andBankacctidNotBetween(String value1, String value2) {
            addCriterion("BANKACCTID not between", value1, value2, "bankacctid");
            return (Criteria) this;
        }

        public Criteria andSEntCustIdIsNull() {
            addCriterion("S_ENT_CUST_ID is null");
            return (Criteria) this;
        }

        public Criteria andSEntCustIdIsNotNull() {
            addCriterion("S_ENT_CUST_ID is not null");
            return (Criteria) this;
        }

        public Criteria andSEntCustIdEqualTo(String value) {
            addCriterion("S_ENT_CUST_ID =", value, "sEntCustId");
            return (Criteria) this;
        }

        public Criteria andSEntCustIdNotEqualTo(String value) {
            addCriterion("S_ENT_CUST_ID <>", value, "sEntCustId");
            return (Criteria) this;
        }

        public Criteria andSEntCustIdGreaterThan(String value) {
            addCriterion("S_ENT_CUST_ID >", value, "sEntCustId");
            return (Criteria) this;
        }

        public Criteria andSEntCustIdGreaterThanOrEqualTo(String value) {
            addCriterion("S_ENT_CUST_ID >=", value, "sEntCustId");
            return (Criteria) this;
        }

        public Criteria andSEntCustIdLessThan(String value) {
            addCriterion("S_ENT_CUST_ID <", value, "sEntCustId");
            return (Criteria) this;
        }

        public Criteria andSEntCustIdLessThanOrEqualTo(String value) {
            addCriterion("S_ENT_CUST_ID <=", value, "sEntCustId");
            return (Criteria) this;
        }

        public Criteria andSEntCustIdLike(String value) {
            addCriterion("S_ENT_CUST_ID like", value, "sEntCustId");
            return (Criteria) this;
        }

        public Criteria andSEntCustIdNotLike(String value) {
            addCriterion("S_ENT_CUST_ID not like", value, "sEntCustId");
            return (Criteria) this;
        }

        public Criteria andSEntCustIdIn(List<String> values) {
            addCriterion("S_ENT_CUST_ID in", values, "sEntCustId");
            return (Criteria) this;
        }

        public Criteria andSEntCustIdNotIn(List<String> values) {
            addCriterion("S_ENT_CUST_ID not in", values, "sEntCustId");
            return (Criteria) this;
        }

        public Criteria andSEntCustIdBetween(String value1, String value2) {
            addCriterion("S_ENT_CUST_ID between", value1, value2, "sEntCustId");
            return (Criteria) this;
        }

        public Criteria andSEntCustIdNotBetween(String value1, String value2) {
            addCriterion("S_ENT_CUST_ID not between", value1, value2, "sEntCustId");
            return (Criteria) this;
        }

        public Criteria andSEntResNoIsNull() {
            addCriterion("S_ENT_RES_NO is null");
            return (Criteria) this;
        }

        public Criteria andSEntResNoIsNotNull() {
            addCriterion("S_ENT_RES_NO is not null");
            return (Criteria) this;
        }

        public Criteria andSEntResNoEqualTo(String value) {
            addCriterion("S_ENT_RES_NO =", value, "sEntResNo");
            return (Criteria) this;
        }

        public Criteria andSEntResNoNotEqualTo(String value) {
            addCriterion("S_ENT_RES_NO <>", value, "sEntResNo");
            return (Criteria) this;
        }

        public Criteria andSEntResNoGreaterThan(String value) {
            addCriterion("S_ENT_RES_NO >", value, "sEntResNo");
            return (Criteria) this;
        }

        public Criteria andSEntResNoGreaterThanOrEqualTo(String value) {
            addCriterion("S_ENT_RES_NO >=", value, "sEntResNo");
            return (Criteria) this;
        }

        public Criteria andSEntResNoLessThan(String value) {
            addCriterion("S_ENT_RES_NO <", value, "sEntResNo");
            return (Criteria) this;
        }

        public Criteria andSEntResNoLessThanOrEqualTo(String value) {
            addCriterion("S_ENT_RES_NO <=", value, "sEntResNo");
            return (Criteria) this;
        }

        public Criteria andSEntResNoLike(String value) {
            addCriterion("S_ENT_RES_NO like", value, "sEntResNo");
            return (Criteria) this;
        }

        public Criteria andSEntResNoNotLike(String value) {
            addCriterion("S_ENT_RES_NO not like", value, "sEntResNo");
            return (Criteria) this;
        }

        public Criteria andSEntResNoIn(List<String> values) {
            addCriterion("S_ENT_RES_NO in", values, "sEntResNo");
            return (Criteria) this;
        }

        public Criteria andSEntResNoNotIn(List<String> values) {
            addCriterion("S_ENT_RES_NO not in", values, "sEntResNo");
            return (Criteria) this;
        }

        public Criteria andSEntResNoBetween(String value1, String value2) {
            addCriterion("S_ENT_RES_NO between", value1, value2, "sEntResNo");
            return (Criteria) this;
        }

        public Criteria andSEntResNoNotBetween(String value1, String value2) {
            addCriterion("S_ENT_RES_NO not between", value1, value2, "sEntResNo");
            return (Criteria) this;
        }

        public Criteria andSEntCustNameIsNull() {
            addCriterion("S_ENT_CUST_NAME is null");
            return (Criteria) this;
        }

        public Criteria andSEntCustNameIsNotNull() {
            addCriterion("S_ENT_CUST_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andSEntCustNameEqualTo(String value) {
            addCriterion("S_ENT_CUST_NAME =", value, "sEntCustName");
            return (Criteria) this;
        }

        public Criteria andSEntCustNameNotEqualTo(String value) {
            addCriterion("S_ENT_CUST_NAME <>", value, "sEntCustName");
            return (Criteria) this;
        }

        public Criteria andSEntCustNameGreaterThan(String value) {
            addCriterion("S_ENT_CUST_NAME >", value, "sEntCustName");
            return (Criteria) this;
        }

        public Criteria andSEntCustNameGreaterThanOrEqualTo(String value) {
            addCriterion("S_ENT_CUST_NAME >=", value, "sEntCustName");
            return (Criteria) this;
        }

        public Criteria andSEntCustNameLessThan(String value) {
            addCriterion("S_ENT_CUST_NAME <", value, "sEntCustName");
            return (Criteria) this;
        }

        public Criteria andSEntCustNameLessThanOrEqualTo(String value) {
            addCriterion("S_ENT_CUST_NAME <=", value, "sEntCustName");
            return (Criteria) this;
        }

        public Criteria andSEntCustNameLike(String value) {
            addCriterion("S_ENT_CUST_NAME like", value, "sEntCustName");
            return (Criteria) this;
        }

        public Criteria andSEntCustNameNotLike(String value) {
            addCriterion("S_ENT_CUST_NAME not like", value, "sEntCustName");
            return (Criteria) this;
        }

        public Criteria andSEntCustNameIn(List<String> values) {
            addCriterion("S_ENT_CUST_NAME in", values, "sEntCustName");
            return (Criteria) this;
        }

        public Criteria andSEntCustNameNotIn(List<String> values) {
            addCriterion("S_ENT_CUST_NAME not in", values, "sEntCustName");
            return (Criteria) this;
        }

        public Criteria andSEntCustNameBetween(String value1, String value2) {
            addCriterion("S_ENT_CUST_NAME between", value1, value2, "sEntCustName");
            return (Criteria) this;
        }

        public Criteria andSEntCustNameNotBetween(String value1, String value2) {
            addCriterion("S_ENT_CUST_NAME not between", value1, value2, "sEntCustName");
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

        public Criteria andProgressIsNull() {
            addCriterion("PROGRESS is null");
            return (Criteria) this;
        }

        public Criteria andProgressIsNotNull() {
            addCriterion("PROGRESS is not null");
            return (Criteria) this;
        }

        public Criteria andProgressEqualTo(Short value) {
            addCriterion("PROGRESS =", value, "progress");
            return (Criteria) this;
        }

        public Criteria andProgressNotEqualTo(Short value) {
            addCriterion("PROGRESS <>", value, "progress");
            return (Criteria) this;
        }

        public Criteria andProgressGreaterThan(Short value) {
            addCriterion("PROGRESS >", value, "progress");
            return (Criteria) this;
        }

        public Criteria andProgressGreaterThanOrEqualTo(Short value) {
            addCriterion("PROGRESS >=", value, "progress");
            return (Criteria) this;
        }

        public Criteria andProgressLessThan(Short value) {
            addCriterion("PROGRESS <", value, "progress");
            return (Criteria) this;
        }

        public Criteria andProgressLessThanOrEqualTo(Short value) {
            addCriterion("PROGRESS <=", value, "progress");
            return (Criteria) this;
        }

        public Criteria andProgressIn(List<Short> values) {
            addCriterion("PROGRESS in", values, "progress");
            return (Criteria) this;
        }

        public Criteria andProgressNotIn(List<Short> values) {
            addCriterion("PROGRESS not in", values, "progress");
            return (Criteria) this;
        }

        public Criteria andProgressBetween(Short value1, Short value2) {
            addCriterion("PROGRESS between", value1, value2, "progress");
            return (Criteria) this;
        }

        public Criteria andProgressNotBetween(Short value1, Short value2) {
            addCriterion("PROGRESS not between", value1, value2, "progress");
            return (Criteria) this;
        }

        public Criteria andReportFileIsNull() {
            addCriterion("REPORT_FILE is null");
            return (Criteria) this;
        }

        public Criteria andReportFileIsNotNull() {
            addCriterion("REPORT_FILE is not null");
            return (Criteria) this;
        }

        public Criteria andReportFileEqualTo(String value) {
            addCriterion("REPORT_FILE =", value, "reportFile");
            return (Criteria) this;
        }

        public Criteria andReportFileNotEqualTo(String value) {
            addCriterion("REPORT_FILE <>", value, "reportFile");
            return (Criteria) this;
        }

        public Criteria andReportFileGreaterThan(String value) {
            addCriterion("REPORT_FILE >", value, "reportFile");
            return (Criteria) this;
        }

        public Criteria andReportFileGreaterThanOrEqualTo(String value) {
            addCriterion("REPORT_FILE >=", value, "reportFile");
            return (Criteria) this;
        }

        public Criteria andReportFileLessThan(String value) {
            addCriterion("REPORT_FILE <", value, "reportFile");
            return (Criteria) this;
        }

        public Criteria andReportFileLessThanOrEqualTo(String value) {
            addCriterion("REPORT_FILE <=", value, "reportFile");
            return (Criteria) this;
        }

        public Criteria andReportFileLike(String value) {
            addCriterion("REPORT_FILE like", value, "reportFile");
            return (Criteria) this;
        }

        public Criteria andReportFileNotLike(String value) {
            addCriterion("REPORT_FILE not like", value, "reportFile");
            return (Criteria) this;
        }

        public Criteria andReportFileIn(List<String> values) {
            addCriterion("REPORT_FILE in", values, "reportFile");
            return (Criteria) this;
        }

        public Criteria andReportFileNotIn(List<String> values) {
            addCriterion("REPORT_FILE not in", values, "reportFile");
            return (Criteria) this;
        }

        public Criteria andReportFileBetween(String value1, String value2) {
            addCriterion("REPORT_FILE between", value1, value2, "reportFile");
            return (Criteria) this;
        }

        public Criteria andReportFileNotBetween(String value1, String value2) {
            addCriterion("REPORT_FILE not between", value1, value2, "reportFile");
            return (Criteria) this;
        }

        public Criteria andStatementFileIsNull() {
            addCriterion("STATEMENT_FILE is null");
            return (Criteria) this;
        }

        public Criteria andStatementFileIsNotNull() {
            addCriterion("STATEMENT_FILE is not null");
            return (Criteria) this;
        }

        public Criteria andStatementFileEqualTo(String value) {
            addCriterion("STATEMENT_FILE =", value, "statementFile");
            return (Criteria) this;
        }

        public Criteria andStatementFileNotEqualTo(String value) {
            addCriterion("STATEMENT_FILE <>", value, "statementFile");
            return (Criteria) this;
        }

        public Criteria andStatementFileGreaterThan(String value) {
            addCriterion("STATEMENT_FILE >", value, "statementFile");
            return (Criteria) this;
        }

        public Criteria andStatementFileGreaterThanOrEqualTo(String value) {
            addCriterion("STATEMENT_FILE >=", value, "statementFile");
            return (Criteria) this;
        }

        public Criteria andStatementFileLessThan(String value) {
            addCriterion("STATEMENT_FILE <", value, "statementFile");
            return (Criteria) this;
        }

        public Criteria andStatementFileLessThanOrEqualTo(String value) {
            addCriterion("STATEMENT_FILE <=", value, "statementFile");
            return (Criteria) this;
        }

        public Criteria andStatementFileLike(String value) {
            addCriterion("STATEMENT_FILE like", value, "statementFile");
            return (Criteria) this;
        }

        public Criteria andStatementFileNotLike(String value) {
            addCriterion("STATEMENT_FILE not like", value, "statementFile");
            return (Criteria) this;
        }

        public Criteria andStatementFileIn(List<String> values) {
            addCriterion("STATEMENT_FILE in", values, "statementFile");
            return (Criteria) this;
        }

        public Criteria andStatementFileNotIn(List<String> values) {
            addCriterion("STATEMENT_FILE not in", values, "statementFile");
            return (Criteria) this;
        }

        public Criteria andStatementFileBetween(String value1, String value2) {
            addCriterion("STATEMENT_FILE between", value1, value2, "statementFile");
            return (Criteria) this;
        }

        public Criteria andStatementFileNotBetween(String value1, String value2) {
            addCriterion("STATEMENT_FILE not between", value1, value2, "statementFile");
            return (Criteria) this;
        }

        public Criteria andOtherFileIsNull() {
            addCriterion("OTHER_FILE is null");
            return (Criteria) this;
        }

        public Criteria andOtherFileIsNotNull() {
            addCriterion("OTHER_FILE is not null");
            return (Criteria) this;
        }

        public Criteria andOtherFileEqualTo(String value) {
            addCriterion("OTHER_FILE =", value, "otherFile");
            return (Criteria) this;
        }

        public Criteria andOtherFileNotEqualTo(String value) {
            addCriterion("OTHER_FILE <>", value, "otherFile");
            return (Criteria) this;
        }

        public Criteria andOtherFileGreaterThan(String value) {
            addCriterion("OTHER_FILE >", value, "otherFile");
            return (Criteria) this;
        }

        public Criteria andOtherFileGreaterThanOrEqualTo(String value) {
            addCriterion("OTHER_FILE >=", value, "otherFile");
            return (Criteria) this;
        }

        public Criteria andOtherFileLessThan(String value) {
            addCriterion("OTHER_FILE <", value, "otherFile");
            return (Criteria) this;
        }

        public Criteria andOtherFileLessThanOrEqualTo(String value) {
            addCriterion("OTHER_FILE <=", value, "otherFile");
            return (Criteria) this;
        }

        public Criteria andOtherFileLike(String value) {
            addCriterion("OTHER_FILE like", value, "otherFile");
            return (Criteria) this;
        }

        public Criteria andOtherFileNotLike(String value) {
            addCriterion("OTHER_FILE not like", value, "otherFile");
            return (Criteria) this;
        }

        public Criteria andOtherFileIn(List<String> values) {
            addCriterion("OTHER_FILE in", values, "otherFile");
            return (Criteria) this;
        }

        public Criteria andOtherFileNotIn(List<String> values) {
            addCriterion("OTHER_FILE not in", values, "otherFile");
            return (Criteria) this;
        }

        public Criteria andOtherFileBetween(String value1, String value2) {
            addCriterion("OTHER_FILE between", value1, value2, "otherFile");
            return (Criteria) this;
        }

        public Criteria andOtherFileNotBetween(String value1, String value2) {
            addCriterion("OTHER_FILE not between", value1, value2, "otherFile");
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