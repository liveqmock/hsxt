package com.gy.hsi.fs.mapper.vo.dbbs01;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.gy.hsi.fs.mapper.vo.AbstractPage;

public class TBsShippingMethodExample extends AbstractPage {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TBsShippingMethodExample() {
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

        public Criteria andSmIdIsNull() {
            addCriterion("SM_ID is null");
            return (Criteria) this;
        }

        public Criteria andSmIdIsNotNull() {
            addCriterion("SM_ID is not null");
            return (Criteria) this;
        }

        public Criteria andSmIdEqualTo(String value) {
            addCriterion("SM_ID =", value, "smId");
            return (Criteria) this;
        }

        public Criteria andSmIdNotEqualTo(String value) {
            addCriterion("SM_ID <>", value, "smId");
            return (Criteria) this;
        }

        public Criteria andSmIdGreaterThan(String value) {
            addCriterion("SM_ID >", value, "smId");
            return (Criteria) this;
        }

        public Criteria andSmIdGreaterThanOrEqualTo(String value) {
            addCriterion("SM_ID >=", value, "smId");
            return (Criteria) this;
        }

        public Criteria andSmIdLessThan(String value) {
            addCriterion("SM_ID <", value, "smId");
            return (Criteria) this;
        }

        public Criteria andSmIdLessThanOrEqualTo(String value) {
            addCriterion("SM_ID <=", value, "smId");
            return (Criteria) this;
        }

        public Criteria andSmIdLike(String value) {
            addCriterion("SM_ID like", value, "smId");
            return (Criteria) this;
        }

        public Criteria andSmIdNotLike(String value) {
            addCriterion("SM_ID not like", value, "smId");
            return (Criteria) this;
        }

        public Criteria andSmIdIn(List<String> values) {
            addCriterion("SM_ID in", values, "smId");
            return (Criteria) this;
        }

        public Criteria andSmIdNotIn(List<String> values) {
            addCriterion("SM_ID not in", values, "smId");
            return (Criteria) this;
        }

        public Criteria andSmIdBetween(String value1, String value2) {
            addCriterion("SM_ID between", value1, value2, "smId");
            return (Criteria) this;
        }

        public Criteria andSmIdNotBetween(String value1, String value2) {
            addCriterion("SM_ID not between", value1, value2, "smId");
            return (Criteria) this;
        }

        public Criteria andSmNameIsNull() {
            addCriterion("SM_NAME is null");
            return (Criteria) this;
        }

        public Criteria andSmNameIsNotNull() {
            addCriterion("SM_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andSmNameEqualTo(String value) {
            addCriterion("SM_NAME =", value, "smName");
            return (Criteria) this;
        }

        public Criteria andSmNameNotEqualTo(String value) {
            addCriterion("SM_NAME <>", value, "smName");
            return (Criteria) this;
        }

        public Criteria andSmNameGreaterThan(String value) {
            addCriterion("SM_NAME >", value, "smName");
            return (Criteria) this;
        }

        public Criteria andSmNameGreaterThanOrEqualTo(String value) {
            addCriterion("SM_NAME >=", value, "smName");
            return (Criteria) this;
        }

        public Criteria andSmNameLessThan(String value) {
            addCriterion("SM_NAME <", value, "smName");
            return (Criteria) this;
        }

        public Criteria andSmNameLessThanOrEqualTo(String value) {
            addCriterion("SM_NAME <=", value, "smName");
            return (Criteria) this;
        }

        public Criteria andSmNameLike(String value) {
            addCriterion("SM_NAME like", value, "smName");
            return (Criteria) this;
        }

        public Criteria andSmNameNotLike(String value) {
            addCriterion("SM_NAME not like", value, "smName");
            return (Criteria) this;
        }

        public Criteria andSmNameIn(List<String> values) {
            addCriterion("SM_NAME in", values, "smName");
            return (Criteria) this;
        }

        public Criteria andSmNameNotIn(List<String> values) {
            addCriterion("SM_NAME not in", values, "smName");
            return (Criteria) this;
        }

        public Criteria andSmNameBetween(String value1, String value2) {
            addCriterion("SM_NAME between", value1, value2, "smName");
            return (Criteria) this;
        }

        public Criteria andSmNameNotBetween(String value1, String value2) {
            addCriterion("SM_NAME not between", value1, value2, "smName");
            return (Criteria) this;
        }

        public Criteria andIcoIsNull() {
            addCriterion("ICO is null");
            return (Criteria) this;
        }

        public Criteria andIcoIsNotNull() {
            addCriterion("ICO is not null");
            return (Criteria) this;
        }

        public Criteria andIcoEqualTo(String value) {
            addCriterion("ICO =", value, "ico");
            return (Criteria) this;
        }

        public Criteria andIcoNotEqualTo(String value) {
            addCriterion("ICO <>", value, "ico");
            return (Criteria) this;
        }

        public Criteria andIcoGreaterThan(String value) {
            addCriterion("ICO >", value, "ico");
            return (Criteria) this;
        }

        public Criteria andIcoGreaterThanOrEqualTo(String value) {
            addCriterion("ICO >=", value, "ico");
            return (Criteria) this;
        }

        public Criteria andIcoLessThan(String value) {
            addCriterion("ICO <", value, "ico");
            return (Criteria) this;
        }

        public Criteria andIcoLessThanOrEqualTo(String value) {
            addCriterion("ICO <=", value, "ico");
            return (Criteria) this;
        }

        public Criteria andIcoLike(String value) {
            addCriterion("ICO like", value, "ico");
            return (Criteria) this;
        }

        public Criteria andIcoNotLike(String value) {
            addCriterion("ICO not like", value, "ico");
            return (Criteria) this;
        }

        public Criteria andIcoIn(List<String> values) {
            addCriterion("ICO in", values, "ico");
            return (Criteria) this;
        }

        public Criteria andIcoNotIn(List<String> values) {
            addCriterion("ICO not in", values, "ico");
            return (Criteria) this;
        }

        public Criteria andIcoBetween(String value1, String value2) {
            addCriterion("ICO between", value1, value2, "ico");
            return (Criteria) this;
        }

        public Criteria andIcoNotBetween(String value1, String value2) {
            addCriterion("ICO not between", value1, value2, "ico");
            return (Criteria) this;
        }

        public Criteria andSortIsNull() {
            addCriterion("SORT is null");
            return (Criteria) this;
        }

        public Criteria andSortIsNotNull() {
            addCriterion("SORT is not null");
            return (Criteria) this;
        }

        public Criteria andSortEqualTo(Long value) {
            addCriterion("SORT =", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortNotEqualTo(Long value) {
            addCriterion("SORT <>", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortGreaterThan(Long value) {
            addCriterion("SORT >", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortGreaterThanOrEqualTo(Long value) {
            addCriterion("SORT >=", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortLessThan(Long value) {
            addCriterion("SORT <", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortLessThanOrEqualTo(Long value) {
            addCriterion("SORT <=", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortIn(List<Long> values) {
            addCriterion("SORT in", values, "sort");
            return (Criteria) this;
        }

        public Criteria andSortNotIn(List<Long> values) {
            addCriterion("SORT not in", values, "sort");
            return (Criteria) this;
        }

        public Criteria andSortBetween(Long value1, Long value2) {
            addCriterion("SORT between", value1, value2, "sort");
            return (Criteria) this;
        }

        public Criteria andSortNotBetween(Long value1, Long value2) {
            addCriterion("SORT not between", value1, value2, "sort");
            return (Criteria) this;
        }

        public Criteria andSmDescIsNull() {
            addCriterion("SM_DESC is null");
            return (Criteria) this;
        }

        public Criteria andSmDescIsNotNull() {
            addCriterion("SM_DESC is not null");
            return (Criteria) this;
        }

        public Criteria andSmDescEqualTo(String value) {
            addCriterion("SM_DESC =", value, "smDesc");
            return (Criteria) this;
        }

        public Criteria andSmDescNotEqualTo(String value) {
            addCriterion("SM_DESC <>", value, "smDesc");
            return (Criteria) this;
        }

        public Criteria andSmDescGreaterThan(String value) {
            addCriterion("SM_DESC >", value, "smDesc");
            return (Criteria) this;
        }

        public Criteria andSmDescGreaterThanOrEqualTo(String value) {
            addCriterion("SM_DESC >=", value, "smDesc");
            return (Criteria) this;
        }

        public Criteria andSmDescLessThan(String value) {
            addCriterion("SM_DESC <", value, "smDesc");
            return (Criteria) this;
        }

        public Criteria andSmDescLessThanOrEqualTo(String value) {
            addCriterion("SM_DESC <=", value, "smDesc");
            return (Criteria) this;
        }

        public Criteria andSmDescLike(String value) {
            addCriterion("SM_DESC like", value, "smDesc");
            return (Criteria) this;
        }

        public Criteria andSmDescNotLike(String value) {
            addCriterion("SM_DESC not like", value, "smDesc");
            return (Criteria) this;
        }

        public Criteria andSmDescIn(List<String> values) {
            addCriterion("SM_DESC in", values, "smDesc");
            return (Criteria) this;
        }

        public Criteria andSmDescNotIn(List<String> values) {
            addCriterion("SM_DESC not in", values, "smDesc");
            return (Criteria) this;
        }

        public Criteria andSmDescBetween(String value1, String value2) {
            addCriterion("SM_DESC between", value1, value2, "smDesc");
            return (Criteria) this;
        }

        public Criteria andSmDescNotBetween(String value1, String value2) {
            addCriterion("SM_DESC not between", value1, value2, "smDesc");
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