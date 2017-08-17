package com.gy.hsi.fs.mapper.vo.dbbs01;

import java.util.ArrayList;
import java.util.List;

import com.gy.hsi.fs.mapper.vo.AbstractPage;

public class TBsEntChangeInfoDataExample extends AbstractPage {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TBsEntChangeInfoDataExample() {
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

        public Criteria andChangeInfoCodeIsNull() {
            addCriterion("CHANGE_INFO_CODE is null");
            return (Criteria) this;
        }

        public Criteria andChangeInfoCodeIsNotNull() {
            addCriterion("CHANGE_INFO_CODE is not null");
            return (Criteria) this;
        }

        public Criteria andChangeInfoCodeEqualTo(String value) {
            addCriterion("CHANGE_INFO_CODE =", value, "changeInfoCode");
            return (Criteria) this;
        }

        public Criteria andChangeInfoCodeNotEqualTo(String value) {
            addCriterion("CHANGE_INFO_CODE <>", value, "changeInfoCode");
            return (Criteria) this;
        }

        public Criteria andChangeInfoCodeGreaterThan(String value) {
            addCriterion("CHANGE_INFO_CODE >", value, "changeInfoCode");
            return (Criteria) this;
        }

        public Criteria andChangeInfoCodeGreaterThanOrEqualTo(String value) {
            addCriterion("CHANGE_INFO_CODE >=", value, "changeInfoCode");
            return (Criteria) this;
        }

        public Criteria andChangeInfoCodeLessThan(String value) {
            addCriterion("CHANGE_INFO_CODE <", value, "changeInfoCode");
            return (Criteria) this;
        }

        public Criteria andChangeInfoCodeLessThanOrEqualTo(String value) {
            addCriterion("CHANGE_INFO_CODE <=", value, "changeInfoCode");
            return (Criteria) this;
        }

        public Criteria andChangeInfoCodeLike(String value) {
            addCriterion("CHANGE_INFO_CODE like", value, "changeInfoCode");
            return (Criteria) this;
        }

        public Criteria andChangeInfoCodeNotLike(String value) {
            addCriterion("CHANGE_INFO_CODE not like", value, "changeInfoCode");
            return (Criteria) this;
        }

        public Criteria andChangeInfoCodeIn(List<String> values) {
            addCriterion("CHANGE_INFO_CODE in", values, "changeInfoCode");
            return (Criteria) this;
        }

        public Criteria andChangeInfoCodeNotIn(List<String> values) {
            addCriterion("CHANGE_INFO_CODE not in", values, "changeInfoCode");
            return (Criteria) this;
        }

        public Criteria andChangeInfoCodeBetween(String value1, String value2) {
            addCriterion("CHANGE_INFO_CODE between", value1, value2, "changeInfoCode");
            return (Criteria) this;
        }

        public Criteria andChangeInfoCodeNotBetween(String value1, String value2) {
            addCriterion("CHANGE_INFO_CODE not between", value1, value2, "changeInfoCode");
            return (Criteria) this;
        }

        public Criteria andBeforeValueIsNull() {
            addCriterion("BEFORE_VALUE is null");
            return (Criteria) this;
        }

        public Criteria andBeforeValueIsNotNull() {
            addCriterion("BEFORE_VALUE is not null");
            return (Criteria) this;
        }

        public Criteria andBeforeValueEqualTo(String value) {
            addCriterion("BEFORE_VALUE =", value, "beforeValue");
            return (Criteria) this;
        }

        public Criteria andBeforeValueNotEqualTo(String value) {
            addCriterion("BEFORE_VALUE <>", value, "beforeValue");
            return (Criteria) this;
        }

        public Criteria andBeforeValueGreaterThan(String value) {
            addCriterion("BEFORE_VALUE >", value, "beforeValue");
            return (Criteria) this;
        }

        public Criteria andBeforeValueGreaterThanOrEqualTo(String value) {
            addCriterion("BEFORE_VALUE >=", value, "beforeValue");
            return (Criteria) this;
        }

        public Criteria andBeforeValueLessThan(String value) {
            addCriterion("BEFORE_VALUE <", value, "beforeValue");
            return (Criteria) this;
        }

        public Criteria andBeforeValueLessThanOrEqualTo(String value) {
            addCriterion("BEFORE_VALUE <=", value, "beforeValue");
            return (Criteria) this;
        }

        public Criteria andBeforeValueLike(String value) {
            addCriterion("BEFORE_VALUE like", value, "beforeValue");
            return (Criteria) this;
        }

        public Criteria andBeforeValueNotLike(String value) {
            addCriterion("BEFORE_VALUE not like", value, "beforeValue");
            return (Criteria) this;
        }

        public Criteria andBeforeValueIn(List<String> values) {
            addCriterion("BEFORE_VALUE in", values, "beforeValue");
            return (Criteria) this;
        }

        public Criteria andBeforeValueNotIn(List<String> values) {
            addCriterion("BEFORE_VALUE not in", values, "beforeValue");
            return (Criteria) this;
        }

        public Criteria andBeforeValueBetween(String value1, String value2) {
            addCriterion("BEFORE_VALUE between", value1, value2, "beforeValue");
            return (Criteria) this;
        }

        public Criteria andBeforeValueNotBetween(String value1, String value2) {
            addCriterion("BEFORE_VALUE not between", value1, value2, "beforeValue");
            return (Criteria) this;
        }

        public Criteria andAfterValueIsNull() {
            addCriterion("AFTER_VALUE is null");
            return (Criteria) this;
        }

        public Criteria andAfterValueIsNotNull() {
            addCriterion("AFTER_VALUE is not null");
            return (Criteria) this;
        }

        public Criteria andAfterValueEqualTo(String value) {
            addCriterion("AFTER_VALUE =", value, "afterValue");
            return (Criteria) this;
        }

        public Criteria andAfterValueNotEqualTo(String value) {
            addCriterion("AFTER_VALUE <>", value, "afterValue");
            return (Criteria) this;
        }

        public Criteria andAfterValueGreaterThan(String value) {
            addCriterion("AFTER_VALUE >", value, "afterValue");
            return (Criteria) this;
        }

        public Criteria andAfterValueGreaterThanOrEqualTo(String value) {
            addCriterion("AFTER_VALUE >=", value, "afterValue");
            return (Criteria) this;
        }

        public Criteria andAfterValueLessThan(String value) {
            addCriterion("AFTER_VALUE <", value, "afterValue");
            return (Criteria) this;
        }

        public Criteria andAfterValueLessThanOrEqualTo(String value) {
            addCriterion("AFTER_VALUE <=", value, "afterValue");
            return (Criteria) this;
        }

        public Criteria andAfterValueLike(String value) {
            addCriterion("AFTER_VALUE like", value, "afterValue");
            return (Criteria) this;
        }

        public Criteria andAfterValueNotLike(String value) {
            addCriterion("AFTER_VALUE not like", value, "afterValue");
            return (Criteria) this;
        }

        public Criteria andAfterValueIn(List<String> values) {
            addCriterion("AFTER_VALUE in", values, "afterValue");
            return (Criteria) this;
        }

        public Criteria andAfterValueNotIn(List<String> values) {
            addCriterion("AFTER_VALUE not in", values, "afterValue");
            return (Criteria) this;
        }

        public Criteria andAfterValueBetween(String value1, String value2) {
            addCriterion("AFTER_VALUE between", value1, value2, "afterValue");
            return (Criteria) this;
        }

        public Criteria andAfterValueNotBetween(String value1, String value2) {
            addCriterion("AFTER_VALUE not between", value1, value2, "afterValue");
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