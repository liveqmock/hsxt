package com.gy.hsi.fs.server.common.framework.mybatis.mapvo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TFsPermissionRuleExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TFsPermissionRuleExample() {
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

        public Criteria andIdIsNull() {
            addCriterion("ID is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("ID is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("ID =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("ID <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("ID >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("ID >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("ID <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("ID <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("ID in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("ID not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("ID between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("ID not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andRuleMatrixXIsNull() {
            addCriterion("RULE_MATRIX_X is null");
            return (Criteria) this;
        }

        public Criteria andRuleMatrixXIsNotNull() {
            addCriterion("RULE_MATRIX_X is not null");
            return (Criteria) this;
        }

        public Criteria andRuleMatrixXEqualTo(String value) {
            addCriterion("RULE_MATRIX_X =", value, "ruleMatrixX");
            return (Criteria) this;
        }

        public Criteria andRuleMatrixXNotEqualTo(String value) {
            addCriterion("RULE_MATRIX_X <>", value, "ruleMatrixX");
            return (Criteria) this;
        }

        public Criteria andRuleMatrixXGreaterThan(String value) {
            addCriterion("RULE_MATRIX_X >", value, "ruleMatrixX");
            return (Criteria) this;
        }

        public Criteria andRuleMatrixXGreaterThanOrEqualTo(String value) {
            addCriterion("RULE_MATRIX_X >=", value, "ruleMatrixX");
            return (Criteria) this;
        }

        public Criteria andRuleMatrixXLessThan(String value) {
            addCriterion("RULE_MATRIX_X <", value, "ruleMatrixX");
            return (Criteria) this;
        }

        public Criteria andRuleMatrixXLessThanOrEqualTo(String value) {
            addCriterion("RULE_MATRIX_X <=", value, "ruleMatrixX");
            return (Criteria) this;
        }

        public Criteria andRuleMatrixXLike(String value) {
            addCriterion("RULE_MATRIX_X like", value, "ruleMatrixX");
            return (Criteria) this;
        }

        public Criteria andRuleMatrixXNotLike(String value) {
            addCriterion("RULE_MATRIX_X not like", value, "ruleMatrixX");
            return (Criteria) this;
        }

        public Criteria andRuleMatrixXIn(List<String> values) {
            addCriterion("RULE_MATRIX_X in", values, "ruleMatrixX");
            return (Criteria) this;
        }

        public Criteria andRuleMatrixXNotIn(List<String> values) {
            addCriterion("RULE_MATRIX_X not in", values, "ruleMatrixX");
            return (Criteria) this;
        }

        public Criteria andRuleMatrixXBetween(String value1, String value2) {
            addCriterion("RULE_MATRIX_X between", value1, value2, "ruleMatrixX");
            return (Criteria) this;
        }

        public Criteria andRuleMatrixXNotBetween(String value1, String value2) {
            addCriterion("RULE_MATRIX_X not between", value1, value2, "ruleMatrixX");
            return (Criteria) this;
        }

        public Criteria andRuleMatrixYIsNull() {
            addCriterion("RULE_MATRIX_Y is null");
            return (Criteria) this;
        }

        public Criteria andRuleMatrixYIsNotNull() {
            addCriterion("RULE_MATRIX_Y is not null");
            return (Criteria) this;
        }

        public Criteria andRuleMatrixYEqualTo(String value) {
            addCriterion("RULE_MATRIX_Y =", value, "ruleMatrixY");
            return (Criteria) this;
        }

        public Criteria andRuleMatrixYNotEqualTo(String value) {
            addCriterion("RULE_MATRIX_Y <>", value, "ruleMatrixY");
            return (Criteria) this;
        }

        public Criteria andRuleMatrixYGreaterThan(String value) {
            addCriterion("RULE_MATRIX_Y >", value, "ruleMatrixY");
            return (Criteria) this;
        }

        public Criteria andRuleMatrixYGreaterThanOrEqualTo(String value) {
            addCriterion("RULE_MATRIX_Y >=", value, "ruleMatrixY");
            return (Criteria) this;
        }

        public Criteria andRuleMatrixYLessThan(String value) {
            addCriterion("RULE_MATRIX_Y <", value, "ruleMatrixY");
            return (Criteria) this;
        }

        public Criteria andRuleMatrixYLessThanOrEqualTo(String value) {
            addCriterion("RULE_MATRIX_Y <=", value, "ruleMatrixY");
            return (Criteria) this;
        }

        public Criteria andRuleMatrixYLike(String value) {
            addCriterion("RULE_MATRIX_Y like", value, "ruleMatrixY");
            return (Criteria) this;
        }

        public Criteria andRuleMatrixYNotLike(String value) {
            addCriterion("RULE_MATRIX_Y not like", value, "ruleMatrixY");
            return (Criteria) this;
        }

        public Criteria andRuleMatrixYIn(List<String> values) {
            addCriterion("RULE_MATRIX_Y in", values, "ruleMatrixY");
            return (Criteria) this;
        }

        public Criteria andRuleMatrixYNotIn(List<String> values) {
            addCriterion("RULE_MATRIX_Y not in", values, "ruleMatrixY");
            return (Criteria) this;
        }

        public Criteria andRuleMatrixYBetween(String value1, String value2) {
            addCriterion("RULE_MATRIX_Y between", value1, value2, "ruleMatrixY");
            return (Criteria) this;
        }

        public Criteria andRuleMatrixYNotBetween(String value1, String value2) {
            addCriterion("RULE_MATRIX_Y not between", value1, value2, "ruleMatrixY");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNull() {
            addCriterion("REMARK is null");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNotNull() {
            addCriterion("REMARK is not null");
            return (Criteria) this;
        }

        public Criteria andRemarkEqualTo(String value) {
            addCriterion("REMARK =", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotEqualTo(String value) {
            addCriterion("REMARK <>", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThan(String value) {
            addCriterion("REMARK >", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("REMARK >=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThan(String value) {
            addCriterion("REMARK <", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThanOrEqualTo(String value) {
            addCriterion("REMARK <=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLike(String value) {
            addCriterion("REMARK like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotLike(String value) {
            addCriterion("REMARK not like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkIn(List<String> values) {
            addCriterion("REMARK in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotIn(List<String> values) {
            addCriterion("REMARK not in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkBetween(String value1, String value2) {
            addCriterion("REMARK between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotBetween(String value1, String value2) {
            addCriterion("REMARK not between", value1, value2, "remark");
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