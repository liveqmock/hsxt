package com.gy.hsi.fs.mapper.vo.dbbs01;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.gy.hsi.fs.mapper.vo.AbstractPage;

public class TBsPerChangeInfoAppExample extends AbstractPage {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TBsPerChangeInfoAppExample() {
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

        public Criteria andPerCustNameIsNull() {
            addCriterion("PER_CUST_NAME is null");
            return (Criteria) this;
        }

        public Criteria andPerCustNameIsNotNull() {
            addCriterion("PER_CUST_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andPerCustNameEqualTo(String value) {
            addCriterion("PER_CUST_NAME =", value, "perCustName");
            return (Criteria) this;
        }

        public Criteria andPerCustNameNotEqualTo(String value) {
            addCriterion("PER_CUST_NAME <>", value, "perCustName");
            return (Criteria) this;
        }

        public Criteria andPerCustNameGreaterThan(String value) {
            addCriterion("PER_CUST_NAME >", value, "perCustName");
            return (Criteria) this;
        }

        public Criteria andPerCustNameGreaterThanOrEqualTo(String value) {
            addCriterion("PER_CUST_NAME >=", value, "perCustName");
            return (Criteria) this;
        }

        public Criteria andPerCustNameLessThan(String value) {
            addCriterion("PER_CUST_NAME <", value, "perCustName");
            return (Criteria) this;
        }

        public Criteria andPerCustNameLessThanOrEqualTo(String value) {
            addCriterion("PER_CUST_NAME <=", value, "perCustName");
            return (Criteria) this;
        }

        public Criteria andPerCustNameLike(String value) {
            addCriterion("PER_CUST_NAME like", value, "perCustName");
            return (Criteria) this;
        }

        public Criteria andPerCustNameNotLike(String value) {
            addCriterion("PER_CUST_NAME not like", value, "perCustName");
            return (Criteria) this;
        }

        public Criteria andPerCustNameIn(List<String> values) {
            addCriterion("PER_CUST_NAME in", values, "perCustName");
            return (Criteria) this;
        }

        public Criteria andPerCustNameNotIn(List<String> values) {
            addCriterion("PER_CUST_NAME not in", values, "perCustName");
            return (Criteria) this;
        }

        public Criteria andPerCustNameBetween(String value1, String value2) {
            addCriterion("PER_CUST_NAME between", value1, value2, "perCustName");
            return (Criteria) this;
        }

        public Criteria andPerCustNameNotBetween(String value1, String value2) {
            addCriterion("PER_CUST_NAME not between", value1, value2, "perCustName");
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

        public Criteria andResidenceAddrPicIsNull() {
            addCriterion("RESIDENCE_ADDR_PIC is null");
            return (Criteria) this;
        }

        public Criteria andResidenceAddrPicIsNotNull() {
            addCriterion("RESIDENCE_ADDR_PIC is not null");
            return (Criteria) this;
        }

        public Criteria andResidenceAddrPicEqualTo(String value) {
            addCriterion("RESIDENCE_ADDR_PIC =", value, "residenceAddrPic");
            return (Criteria) this;
        }

        public Criteria andResidenceAddrPicNotEqualTo(String value) {
            addCriterion("RESIDENCE_ADDR_PIC <>", value, "residenceAddrPic");
            return (Criteria) this;
        }

        public Criteria andResidenceAddrPicGreaterThan(String value) {
            addCriterion("RESIDENCE_ADDR_PIC >", value, "residenceAddrPic");
            return (Criteria) this;
        }

        public Criteria andResidenceAddrPicGreaterThanOrEqualTo(String value) {
            addCriterion("RESIDENCE_ADDR_PIC >=", value, "residenceAddrPic");
            return (Criteria) this;
        }

        public Criteria andResidenceAddrPicLessThan(String value) {
            addCriterion("RESIDENCE_ADDR_PIC <", value, "residenceAddrPic");
            return (Criteria) this;
        }

        public Criteria andResidenceAddrPicLessThanOrEqualTo(String value) {
            addCriterion("RESIDENCE_ADDR_PIC <=", value, "residenceAddrPic");
            return (Criteria) this;
        }

        public Criteria andResidenceAddrPicLike(String value) {
            addCriterion("RESIDENCE_ADDR_PIC like", value, "residenceAddrPic");
            return (Criteria) this;
        }

        public Criteria andResidenceAddrPicNotLike(String value) {
            addCriterion("RESIDENCE_ADDR_PIC not like", value, "residenceAddrPic");
            return (Criteria) this;
        }

        public Criteria andResidenceAddrPicIn(List<String> values) {
            addCriterion("RESIDENCE_ADDR_PIC in", values, "residenceAddrPic");
            return (Criteria) this;
        }

        public Criteria andResidenceAddrPicNotIn(List<String> values) {
            addCriterion("RESIDENCE_ADDR_PIC not in", values, "residenceAddrPic");
            return (Criteria) this;
        }

        public Criteria andResidenceAddrPicBetween(String value1, String value2) {
            addCriterion("RESIDENCE_ADDR_PIC between", value1, value2, "residenceAddrPic");
            return (Criteria) this;
        }

        public Criteria andResidenceAddrPicNotBetween(String value1, String value2) {
            addCriterion("RESIDENCE_ADDR_PIC not between", value1, value2, "residenceAddrPic");
            return (Criteria) this;
        }

        public Criteria andChangeItemIsNull() {
            addCriterion("CHANGE_ITEM is null");
            return (Criteria) this;
        }

        public Criteria andChangeItemIsNotNull() {
            addCriterion("CHANGE_ITEM is not null");
            return (Criteria) this;
        }

        public Criteria andChangeItemEqualTo(String value) {
            addCriterion("CHANGE_ITEM =", value, "changeItem");
            return (Criteria) this;
        }

        public Criteria andChangeItemNotEqualTo(String value) {
            addCriterion("CHANGE_ITEM <>", value, "changeItem");
            return (Criteria) this;
        }

        public Criteria andChangeItemGreaterThan(String value) {
            addCriterion("CHANGE_ITEM >", value, "changeItem");
            return (Criteria) this;
        }

        public Criteria andChangeItemGreaterThanOrEqualTo(String value) {
            addCriterion("CHANGE_ITEM >=", value, "changeItem");
            return (Criteria) this;
        }

        public Criteria andChangeItemLessThan(String value) {
            addCriterion("CHANGE_ITEM <", value, "changeItem");
            return (Criteria) this;
        }

        public Criteria andChangeItemLessThanOrEqualTo(String value) {
            addCriterion("CHANGE_ITEM <=", value, "changeItem");
            return (Criteria) this;
        }

        public Criteria andChangeItemLike(String value) {
            addCriterion("CHANGE_ITEM like", value, "changeItem");
            return (Criteria) this;
        }

        public Criteria andChangeItemNotLike(String value) {
            addCriterion("CHANGE_ITEM not like", value, "changeItem");
            return (Criteria) this;
        }

        public Criteria andChangeItemIn(List<String> values) {
            addCriterion("CHANGE_ITEM in", values, "changeItem");
            return (Criteria) this;
        }

        public Criteria andChangeItemNotIn(List<String> values) {
            addCriterion("CHANGE_ITEM not in", values, "changeItem");
            return (Criteria) this;
        }

        public Criteria andChangeItemBetween(String value1, String value2) {
            addCriterion("CHANGE_ITEM between", value1, value2, "changeItem");
            return (Criteria) this;
        }

        public Criteria andChangeItemNotBetween(String value1, String value2) {
            addCriterion("CHANGE_ITEM not between", value1, value2, "changeItem");
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