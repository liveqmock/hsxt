package com.gy.hsi.fs.mapper.vo.dbbs01;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.gy.hsi.fs.mapper.vo.AbstractPage;

public class TBsCardStyleExample extends AbstractPage {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TBsCardStyleExample() {
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

        public Criteria andCardStyleIdIsNull() {
            addCriterion("CARD_STYLE_ID is null");
            return (Criteria) this;
        }

        public Criteria andCardStyleIdIsNotNull() {
            addCriterion("CARD_STYLE_ID is not null");
            return (Criteria) this;
        }

        public Criteria andCardStyleIdEqualTo(String value) {
            addCriterion("CARD_STYLE_ID =", value, "cardStyleId");
            return (Criteria) this;
        }

        public Criteria andCardStyleIdNotEqualTo(String value) {
            addCriterion("CARD_STYLE_ID <>", value, "cardStyleId");
            return (Criteria) this;
        }

        public Criteria andCardStyleIdGreaterThan(String value) {
            addCriterion("CARD_STYLE_ID >", value, "cardStyleId");
            return (Criteria) this;
        }

        public Criteria andCardStyleIdGreaterThanOrEqualTo(String value) {
            addCriterion("CARD_STYLE_ID >=", value, "cardStyleId");
            return (Criteria) this;
        }

        public Criteria andCardStyleIdLessThan(String value) {
            addCriterion("CARD_STYLE_ID <", value, "cardStyleId");
            return (Criteria) this;
        }

        public Criteria andCardStyleIdLessThanOrEqualTo(String value) {
            addCriterion("CARD_STYLE_ID <=", value, "cardStyleId");
            return (Criteria) this;
        }

        public Criteria andCardStyleIdLike(String value) {
            addCriterion("CARD_STYLE_ID like", value, "cardStyleId");
            return (Criteria) this;
        }

        public Criteria andCardStyleIdNotLike(String value) {
            addCriterion("CARD_STYLE_ID not like", value, "cardStyleId");
            return (Criteria) this;
        }

        public Criteria andCardStyleIdIn(List<String> values) {
            addCriterion("CARD_STYLE_ID in", values, "cardStyleId");
            return (Criteria) this;
        }

        public Criteria andCardStyleIdNotIn(List<String> values) {
            addCriterion("CARD_STYLE_ID not in", values, "cardStyleId");
            return (Criteria) this;
        }

        public Criteria andCardStyleIdBetween(String value1, String value2) {
            addCriterion("CARD_STYLE_ID between", value1, value2, "cardStyleId");
            return (Criteria) this;
        }

        public Criteria andCardStyleIdNotBetween(String value1, String value2) {
            addCriterion("CARD_STYLE_ID not between", value1, value2, "cardStyleId");
            return (Criteria) this;
        }

        public Criteria andCardStyleNameIsNull() {
            addCriterion("CARD_STYLE_NAME is null");
            return (Criteria) this;
        }

        public Criteria andCardStyleNameIsNotNull() {
            addCriterion("CARD_STYLE_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andCardStyleNameEqualTo(String value) {
            addCriterion("CARD_STYLE_NAME =", value, "cardStyleName");
            return (Criteria) this;
        }

        public Criteria andCardStyleNameNotEqualTo(String value) {
            addCriterion("CARD_STYLE_NAME <>", value, "cardStyleName");
            return (Criteria) this;
        }

        public Criteria andCardStyleNameGreaterThan(String value) {
            addCriterion("CARD_STYLE_NAME >", value, "cardStyleName");
            return (Criteria) this;
        }

        public Criteria andCardStyleNameGreaterThanOrEqualTo(String value) {
            addCriterion("CARD_STYLE_NAME >=", value, "cardStyleName");
            return (Criteria) this;
        }

        public Criteria andCardStyleNameLessThan(String value) {
            addCriterion("CARD_STYLE_NAME <", value, "cardStyleName");
            return (Criteria) this;
        }

        public Criteria andCardStyleNameLessThanOrEqualTo(String value) {
            addCriterion("CARD_STYLE_NAME <=", value, "cardStyleName");
            return (Criteria) this;
        }

        public Criteria andCardStyleNameLike(String value) {
            addCriterion("CARD_STYLE_NAME like", value, "cardStyleName");
            return (Criteria) this;
        }

        public Criteria andCardStyleNameNotLike(String value) {
            addCriterion("CARD_STYLE_NAME not like", value, "cardStyleName");
            return (Criteria) this;
        }

        public Criteria andCardStyleNameIn(List<String> values) {
            addCriterion("CARD_STYLE_NAME in", values, "cardStyleName");
            return (Criteria) this;
        }

        public Criteria andCardStyleNameNotIn(List<String> values) {
            addCriterion("CARD_STYLE_NAME not in", values, "cardStyleName");
            return (Criteria) this;
        }

        public Criteria andCardStyleNameBetween(String value1, String value2) {
            addCriterion("CARD_STYLE_NAME between", value1, value2, "cardStyleName");
            return (Criteria) this;
        }

        public Criteria andCardStyleNameNotBetween(String value1, String value2) {
            addCriterion("CARD_STYLE_NAME not between", value1, value2, "cardStyleName");
            return (Criteria) this;
        }

        public Criteria andMicroPicIsNull() {
            addCriterion("MICRO_PIC is null");
            return (Criteria) this;
        }

        public Criteria andMicroPicIsNotNull() {
            addCriterion("MICRO_PIC is not null");
            return (Criteria) this;
        }

        public Criteria andMicroPicEqualTo(String value) {
            addCriterion("MICRO_PIC =", value, "microPic");
            return (Criteria) this;
        }

        public Criteria andMicroPicNotEqualTo(String value) {
            addCriterion("MICRO_PIC <>", value, "microPic");
            return (Criteria) this;
        }

        public Criteria andMicroPicGreaterThan(String value) {
            addCriterion("MICRO_PIC >", value, "microPic");
            return (Criteria) this;
        }

        public Criteria andMicroPicGreaterThanOrEqualTo(String value) {
            addCriterion("MICRO_PIC >=", value, "microPic");
            return (Criteria) this;
        }

        public Criteria andMicroPicLessThan(String value) {
            addCriterion("MICRO_PIC <", value, "microPic");
            return (Criteria) this;
        }

        public Criteria andMicroPicLessThanOrEqualTo(String value) {
            addCriterion("MICRO_PIC <=", value, "microPic");
            return (Criteria) this;
        }

        public Criteria andMicroPicLike(String value) {
            addCriterion("MICRO_PIC like", value, "microPic");
            return (Criteria) this;
        }

        public Criteria andMicroPicNotLike(String value) {
            addCriterion("MICRO_PIC not like", value, "microPic");
            return (Criteria) this;
        }

        public Criteria andMicroPicIn(List<String> values) {
            addCriterion("MICRO_PIC in", values, "microPic");
            return (Criteria) this;
        }

        public Criteria andMicroPicNotIn(List<String> values) {
            addCriterion("MICRO_PIC not in", values, "microPic");
            return (Criteria) this;
        }

        public Criteria andMicroPicBetween(String value1, String value2) {
            addCriterion("MICRO_PIC between", value1, value2, "microPic");
            return (Criteria) this;
        }

        public Criteria andMicroPicNotBetween(String value1, String value2) {
            addCriterion("MICRO_PIC not between", value1, value2, "microPic");
            return (Criteria) this;
        }

        public Criteria andSourceFileIsNull() {
            addCriterion("SOURCE_FILE is null");
            return (Criteria) this;
        }

        public Criteria andSourceFileIsNotNull() {
            addCriterion("SOURCE_FILE is not null");
            return (Criteria) this;
        }

        public Criteria andSourceFileEqualTo(String value) {
            addCriterion("SOURCE_FILE =", value, "sourceFile");
            return (Criteria) this;
        }

        public Criteria andSourceFileNotEqualTo(String value) {
            addCriterion("SOURCE_FILE <>", value, "sourceFile");
            return (Criteria) this;
        }

        public Criteria andSourceFileGreaterThan(String value) {
            addCriterion("SOURCE_FILE >", value, "sourceFile");
            return (Criteria) this;
        }

        public Criteria andSourceFileGreaterThanOrEqualTo(String value) {
            addCriterion("SOURCE_FILE >=", value, "sourceFile");
            return (Criteria) this;
        }

        public Criteria andSourceFileLessThan(String value) {
            addCriterion("SOURCE_FILE <", value, "sourceFile");
            return (Criteria) this;
        }

        public Criteria andSourceFileLessThanOrEqualTo(String value) {
            addCriterion("SOURCE_FILE <=", value, "sourceFile");
            return (Criteria) this;
        }

        public Criteria andSourceFileLike(String value) {
            addCriterion("SOURCE_FILE like", value, "sourceFile");
            return (Criteria) this;
        }

        public Criteria andSourceFileNotLike(String value) {
            addCriterion("SOURCE_FILE not like", value, "sourceFile");
            return (Criteria) this;
        }

        public Criteria andSourceFileIn(List<String> values) {
            addCriterion("SOURCE_FILE in", values, "sourceFile");
            return (Criteria) this;
        }

        public Criteria andSourceFileNotIn(List<String> values) {
            addCriterion("SOURCE_FILE not in", values, "sourceFile");
            return (Criteria) this;
        }

        public Criteria andSourceFileBetween(String value1, String value2) {
            addCriterion("SOURCE_FILE between", value1, value2, "sourceFile");
            return (Criteria) this;
        }

        public Criteria andSourceFileNotBetween(String value1, String value2) {
            addCriterion("SOURCE_FILE not between", value1, value2, "sourceFile");
            return (Criteria) this;
        }

        public Criteria andMaterialMicroPicIsNull() {
            addCriterion("MATERIAL_MICRO_PIC is null");
            return (Criteria) this;
        }

        public Criteria andMaterialMicroPicIsNotNull() {
            addCriterion("MATERIAL_MICRO_PIC is not null");
            return (Criteria) this;
        }

        public Criteria andMaterialMicroPicEqualTo(String value) {
            addCriterion("MATERIAL_MICRO_PIC =", value, "materialMicroPic");
            return (Criteria) this;
        }

        public Criteria andMaterialMicroPicNotEqualTo(String value) {
            addCriterion("MATERIAL_MICRO_PIC <>", value, "materialMicroPic");
            return (Criteria) this;
        }

        public Criteria andMaterialMicroPicGreaterThan(String value) {
            addCriterion("MATERIAL_MICRO_PIC >", value, "materialMicroPic");
            return (Criteria) this;
        }

        public Criteria andMaterialMicroPicGreaterThanOrEqualTo(String value) {
            addCriterion("MATERIAL_MICRO_PIC >=", value, "materialMicroPic");
            return (Criteria) this;
        }

        public Criteria andMaterialMicroPicLessThan(String value) {
            addCriterion("MATERIAL_MICRO_PIC <", value, "materialMicroPic");
            return (Criteria) this;
        }

        public Criteria andMaterialMicroPicLessThanOrEqualTo(String value) {
            addCriterion("MATERIAL_MICRO_PIC <=", value, "materialMicroPic");
            return (Criteria) this;
        }

        public Criteria andMaterialMicroPicLike(String value) {
            addCriterion("MATERIAL_MICRO_PIC like", value, "materialMicroPic");
            return (Criteria) this;
        }

        public Criteria andMaterialMicroPicNotLike(String value) {
            addCriterion("MATERIAL_MICRO_PIC not like", value, "materialMicroPic");
            return (Criteria) this;
        }

        public Criteria andMaterialMicroPicIn(List<String> values) {
            addCriterion("MATERIAL_MICRO_PIC in", values, "materialMicroPic");
            return (Criteria) this;
        }

        public Criteria andMaterialMicroPicNotIn(List<String> values) {
            addCriterion("MATERIAL_MICRO_PIC not in", values, "materialMicroPic");
            return (Criteria) this;
        }

        public Criteria andMaterialMicroPicBetween(String value1, String value2) {
            addCriterion("MATERIAL_MICRO_PIC between", value1, value2, "materialMicroPic");
            return (Criteria) this;
        }

        public Criteria andMaterialMicroPicNotBetween(String value1, String value2) {
            addCriterion("MATERIAL_MICRO_PIC not between", value1, value2, "materialMicroPic");
            return (Criteria) this;
        }

        public Criteria andMaterialSourceFileIsNull() {
            addCriterion("MATERIAL_SOURCE_FILE is null");
            return (Criteria) this;
        }

        public Criteria andMaterialSourceFileIsNotNull() {
            addCriterion("MATERIAL_SOURCE_FILE is not null");
            return (Criteria) this;
        }

        public Criteria andMaterialSourceFileEqualTo(String value) {
            addCriterion("MATERIAL_SOURCE_FILE =", value, "materialSourceFile");
            return (Criteria) this;
        }

        public Criteria andMaterialSourceFileNotEqualTo(String value) {
            addCriterion("MATERIAL_SOURCE_FILE <>", value, "materialSourceFile");
            return (Criteria) this;
        }

        public Criteria andMaterialSourceFileGreaterThan(String value) {
            addCriterion("MATERIAL_SOURCE_FILE >", value, "materialSourceFile");
            return (Criteria) this;
        }

        public Criteria andMaterialSourceFileGreaterThanOrEqualTo(String value) {
            addCriterion("MATERIAL_SOURCE_FILE >=", value, "materialSourceFile");
            return (Criteria) this;
        }

        public Criteria andMaterialSourceFileLessThan(String value) {
            addCriterion("MATERIAL_SOURCE_FILE <", value, "materialSourceFile");
            return (Criteria) this;
        }

        public Criteria andMaterialSourceFileLessThanOrEqualTo(String value) {
            addCriterion("MATERIAL_SOURCE_FILE <=", value, "materialSourceFile");
            return (Criteria) this;
        }

        public Criteria andMaterialSourceFileLike(String value) {
            addCriterion("MATERIAL_SOURCE_FILE like", value, "materialSourceFile");
            return (Criteria) this;
        }

        public Criteria andMaterialSourceFileNotLike(String value) {
            addCriterion("MATERIAL_SOURCE_FILE not like", value, "materialSourceFile");
            return (Criteria) this;
        }

        public Criteria andMaterialSourceFileIn(List<String> values) {
            addCriterion("MATERIAL_SOURCE_FILE in", values, "materialSourceFile");
            return (Criteria) this;
        }

        public Criteria andMaterialSourceFileNotIn(List<String> values) {
            addCriterion("MATERIAL_SOURCE_FILE not in", values, "materialSourceFile");
            return (Criteria) this;
        }

        public Criteria andMaterialSourceFileBetween(String value1, String value2) {
            addCriterion("MATERIAL_SOURCE_FILE between", value1, value2, "materialSourceFile");
            return (Criteria) this;
        }

        public Criteria andMaterialSourceFileNotBetween(String value1, String value2) {
            addCriterion("MATERIAL_SOURCE_FILE not between", value1, value2, "materialSourceFile");
            return (Criteria) this;
        }

        public Criteria andIsSpecialIsNull() {
            addCriterion("IS_SPECIAL is null");
            return (Criteria) this;
        }

        public Criteria andIsSpecialIsNotNull() {
            addCriterion("IS_SPECIAL is not null");
            return (Criteria) this;
        }

        public Criteria andIsSpecialEqualTo(Short value) {
            addCriterion("IS_SPECIAL =", value, "isSpecial");
            return (Criteria) this;
        }

        public Criteria andIsSpecialNotEqualTo(Short value) {
            addCriterion("IS_SPECIAL <>", value, "isSpecial");
            return (Criteria) this;
        }

        public Criteria andIsSpecialGreaterThan(Short value) {
            addCriterion("IS_SPECIAL >", value, "isSpecial");
            return (Criteria) this;
        }

        public Criteria andIsSpecialGreaterThanOrEqualTo(Short value) {
            addCriterion("IS_SPECIAL >=", value, "isSpecial");
            return (Criteria) this;
        }

        public Criteria andIsSpecialLessThan(Short value) {
            addCriterion("IS_SPECIAL <", value, "isSpecial");
            return (Criteria) this;
        }

        public Criteria andIsSpecialLessThanOrEqualTo(Short value) {
            addCriterion("IS_SPECIAL <=", value, "isSpecial");
            return (Criteria) this;
        }

        public Criteria andIsSpecialIn(List<Short> values) {
            addCriterion("IS_SPECIAL in", values, "isSpecial");
            return (Criteria) this;
        }

        public Criteria andIsSpecialNotIn(List<Short> values) {
            addCriterion("IS_SPECIAL not in", values, "isSpecial");
            return (Criteria) this;
        }

        public Criteria andIsSpecialBetween(Short value1, Short value2) {
            addCriterion("IS_SPECIAL between", value1, value2, "isSpecial");
            return (Criteria) this;
        }

        public Criteria andIsSpecialNotBetween(Short value1, Short value2) {
            addCriterion("IS_SPECIAL not between", value1, value2, "isSpecial");
            return (Criteria) this;
        }

        public Criteria andIsDefaultIsNull() {
            addCriterion("IS_DEFAULT is null");
            return (Criteria) this;
        }

        public Criteria andIsDefaultIsNotNull() {
            addCriterion("IS_DEFAULT is not null");
            return (Criteria) this;
        }

        public Criteria andIsDefaultEqualTo(Short value) {
            addCriterion("IS_DEFAULT =", value, "isDefault");
            return (Criteria) this;
        }

        public Criteria andIsDefaultNotEqualTo(Short value) {
            addCriterion("IS_DEFAULT <>", value, "isDefault");
            return (Criteria) this;
        }

        public Criteria andIsDefaultGreaterThan(Short value) {
            addCriterion("IS_DEFAULT >", value, "isDefault");
            return (Criteria) this;
        }

        public Criteria andIsDefaultGreaterThanOrEqualTo(Short value) {
            addCriterion("IS_DEFAULT >=", value, "isDefault");
            return (Criteria) this;
        }

        public Criteria andIsDefaultLessThan(Short value) {
            addCriterion("IS_DEFAULT <", value, "isDefault");
            return (Criteria) this;
        }

        public Criteria andIsDefaultLessThanOrEqualTo(Short value) {
            addCriterion("IS_DEFAULT <=", value, "isDefault");
            return (Criteria) this;
        }

        public Criteria andIsDefaultIn(List<Short> values) {
            addCriterion("IS_DEFAULT in", values, "isDefault");
            return (Criteria) this;
        }

        public Criteria andIsDefaultNotIn(List<Short> values) {
            addCriterion("IS_DEFAULT not in", values, "isDefault");
            return (Criteria) this;
        }

        public Criteria andIsDefaultBetween(Short value1, Short value2) {
            addCriterion("IS_DEFAULT between", value1, value2, "isDefault");
            return (Criteria) this;
        }

        public Criteria andIsDefaultNotBetween(Short value1, Short value2) {
            addCriterion("IS_DEFAULT not between", value1, value2, "isDefault");
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

        public Criteria andOrderNoIsNull() {
            addCriterion("ORDER_NO is null");
            return (Criteria) this;
        }

        public Criteria andOrderNoIsNotNull() {
            addCriterion("ORDER_NO is not null");
            return (Criteria) this;
        }

        public Criteria andOrderNoEqualTo(String value) {
            addCriterion("ORDER_NO =", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoNotEqualTo(String value) {
            addCriterion("ORDER_NO <>", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoGreaterThan(String value) {
            addCriterion("ORDER_NO >", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoGreaterThanOrEqualTo(String value) {
            addCriterion("ORDER_NO >=", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoLessThan(String value) {
            addCriterion("ORDER_NO <", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoLessThanOrEqualTo(String value) {
            addCriterion("ORDER_NO <=", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoLike(String value) {
            addCriterion("ORDER_NO like", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoNotLike(String value) {
            addCriterion("ORDER_NO not like", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoIn(List<String> values) {
            addCriterion("ORDER_NO in", values, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoNotIn(List<String> values) {
            addCriterion("ORDER_NO not in", values, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoBetween(String value1, String value2) {
            addCriterion("ORDER_NO between", value1, value2, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoNotBetween(String value1, String value2) {
            addCriterion("ORDER_NO not between", value1, value2, "orderNo");
            return (Criteria) this;
        }

        public Criteria andConfirmFileIsNull() {
            addCriterion("CONFIRM_FILE is null");
            return (Criteria) this;
        }

        public Criteria andConfirmFileIsNotNull() {
            addCriterion("CONFIRM_FILE is not null");
            return (Criteria) this;
        }

        public Criteria andConfirmFileEqualTo(String value) {
            addCriterion("CONFIRM_FILE =", value, "confirmFile");
            return (Criteria) this;
        }

        public Criteria andConfirmFileNotEqualTo(String value) {
            addCriterion("CONFIRM_FILE <>", value, "confirmFile");
            return (Criteria) this;
        }

        public Criteria andConfirmFileGreaterThan(String value) {
            addCriterion("CONFIRM_FILE >", value, "confirmFile");
            return (Criteria) this;
        }

        public Criteria andConfirmFileGreaterThanOrEqualTo(String value) {
            addCriterion("CONFIRM_FILE >=", value, "confirmFile");
            return (Criteria) this;
        }

        public Criteria andConfirmFileLessThan(String value) {
            addCriterion("CONFIRM_FILE <", value, "confirmFile");
            return (Criteria) this;
        }

        public Criteria andConfirmFileLessThanOrEqualTo(String value) {
            addCriterion("CONFIRM_FILE <=", value, "confirmFile");
            return (Criteria) this;
        }

        public Criteria andConfirmFileLike(String value) {
            addCriterion("CONFIRM_FILE like", value, "confirmFile");
            return (Criteria) this;
        }

        public Criteria andConfirmFileNotLike(String value) {
            addCriterion("CONFIRM_FILE not like", value, "confirmFile");
            return (Criteria) this;
        }

        public Criteria andConfirmFileIn(List<String> values) {
            addCriterion("CONFIRM_FILE in", values, "confirmFile");
            return (Criteria) this;
        }

        public Criteria andConfirmFileNotIn(List<String> values) {
            addCriterion("CONFIRM_FILE not in", values, "confirmFile");
            return (Criteria) this;
        }

        public Criteria andConfirmFileBetween(String value1, String value2) {
            addCriterion("CONFIRM_FILE between", value1, value2, "confirmFile");
            return (Criteria) this;
        }

        public Criteria andConfirmFileNotBetween(String value1, String value2) {
            addCriterion("CONFIRM_FILE not between", value1, value2, "confirmFile");
            return (Criteria) this;
        }

        public Criteria andIsLockIsNull() {
            addCriterion("IS_LOCK is null");
            return (Criteria) this;
        }

        public Criteria andIsLockIsNotNull() {
            addCriterion("IS_LOCK is not null");
            return (Criteria) this;
        }

        public Criteria andIsLockEqualTo(Short value) {
            addCriterion("IS_LOCK =", value, "isLock");
            return (Criteria) this;
        }

        public Criteria andIsLockNotEqualTo(Short value) {
            addCriterion("IS_LOCK <>", value, "isLock");
            return (Criteria) this;
        }

        public Criteria andIsLockGreaterThan(Short value) {
            addCriterion("IS_LOCK >", value, "isLock");
            return (Criteria) this;
        }

        public Criteria andIsLockGreaterThanOrEqualTo(Short value) {
            addCriterion("IS_LOCK >=", value, "isLock");
            return (Criteria) this;
        }

        public Criteria andIsLockLessThan(Short value) {
            addCriterion("IS_LOCK <", value, "isLock");
            return (Criteria) this;
        }

        public Criteria andIsLockLessThanOrEqualTo(Short value) {
            addCriterion("IS_LOCK <=", value, "isLock");
            return (Criteria) this;
        }

        public Criteria andIsLockIn(List<Short> values) {
            addCriterion("IS_LOCK in", values, "isLock");
            return (Criteria) this;
        }

        public Criteria andIsLockNotIn(List<Short> values) {
            addCriterion("IS_LOCK not in", values, "isLock");
            return (Criteria) this;
        }

        public Criteria andIsLockBetween(Short value1, Short value2) {
            addCriterion("IS_LOCK between", value1, value2, "isLock");
            return (Criteria) this;
        }

        public Criteria andIsLockNotBetween(Short value1, Short value2) {
            addCriterion("IS_LOCK not between", value1, value2, "isLock");
            return (Criteria) this;
        }

        public Criteria andIsConfirmIsNull() {
            addCriterion("IS_CONFIRM is null");
            return (Criteria) this;
        }

        public Criteria andIsConfirmIsNotNull() {
            addCriterion("IS_CONFIRM is not null");
            return (Criteria) this;
        }

        public Criteria andIsConfirmEqualTo(Short value) {
            addCriterion("IS_CONFIRM =", value, "isConfirm");
            return (Criteria) this;
        }

        public Criteria andIsConfirmNotEqualTo(Short value) {
            addCriterion("IS_CONFIRM <>", value, "isConfirm");
            return (Criteria) this;
        }

        public Criteria andIsConfirmGreaterThan(Short value) {
            addCriterion("IS_CONFIRM >", value, "isConfirm");
            return (Criteria) this;
        }

        public Criteria andIsConfirmGreaterThanOrEqualTo(Short value) {
            addCriterion("IS_CONFIRM >=", value, "isConfirm");
            return (Criteria) this;
        }

        public Criteria andIsConfirmLessThan(Short value) {
            addCriterion("IS_CONFIRM <", value, "isConfirm");
            return (Criteria) this;
        }

        public Criteria andIsConfirmLessThanOrEqualTo(Short value) {
            addCriterion("IS_CONFIRM <=", value, "isConfirm");
            return (Criteria) this;
        }

        public Criteria andIsConfirmIn(List<Short> values) {
            addCriterion("IS_CONFIRM in", values, "isConfirm");
            return (Criteria) this;
        }

        public Criteria andIsConfirmNotIn(List<Short> values) {
            addCriterion("IS_CONFIRM not in", values, "isConfirm");
            return (Criteria) this;
        }

        public Criteria andIsConfirmBetween(Short value1, Short value2) {
            addCriterion("IS_CONFIRM between", value1, value2, "isConfirm");
            return (Criteria) this;
        }

        public Criteria andIsConfirmNotBetween(Short value1, Short value2) {
            addCriterion("IS_CONFIRM not between", value1, value2, "isConfirm");
            return (Criteria) this;
        }

        public Criteria andEnableStatusIsNull() {
            addCriterion("ENABLE_STATUS is null");
            return (Criteria) this;
        }

        public Criteria andEnableStatusIsNotNull() {
            addCriterion("ENABLE_STATUS is not null");
            return (Criteria) this;
        }

        public Criteria andEnableStatusEqualTo(Short value) {
            addCriterion("ENABLE_STATUS =", value, "enableStatus");
            return (Criteria) this;
        }

        public Criteria andEnableStatusNotEqualTo(Short value) {
            addCriterion("ENABLE_STATUS <>", value, "enableStatus");
            return (Criteria) this;
        }

        public Criteria andEnableStatusGreaterThan(Short value) {
            addCriterion("ENABLE_STATUS >", value, "enableStatus");
            return (Criteria) this;
        }

        public Criteria andEnableStatusGreaterThanOrEqualTo(Short value) {
            addCriterion("ENABLE_STATUS >=", value, "enableStatus");
            return (Criteria) this;
        }

        public Criteria andEnableStatusLessThan(Short value) {
            addCriterion("ENABLE_STATUS <", value, "enableStatus");
            return (Criteria) this;
        }

        public Criteria andEnableStatusLessThanOrEqualTo(Short value) {
            addCriterion("ENABLE_STATUS <=", value, "enableStatus");
            return (Criteria) this;
        }

        public Criteria andEnableStatusIn(List<Short> values) {
            addCriterion("ENABLE_STATUS in", values, "enableStatus");
            return (Criteria) this;
        }

        public Criteria andEnableStatusNotIn(List<Short> values) {
            addCriterion("ENABLE_STATUS not in", values, "enableStatus");
            return (Criteria) this;
        }

        public Criteria andEnableStatusBetween(Short value1, Short value2) {
            addCriterion("ENABLE_STATUS between", value1, value2, "enableStatus");
            return (Criteria) this;
        }

        public Criteria andEnableStatusNotBetween(Short value1, Short value2) {
            addCriterion("ENABLE_STATUS not between", value1, value2, "enableStatus");
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

        public Criteria andReqOperatorIsNull() {
            addCriterion("REQ_OPERATOR is null");
            return (Criteria) this;
        }

        public Criteria andReqOperatorIsNotNull() {
            addCriterion("REQ_OPERATOR is not null");
            return (Criteria) this;
        }

        public Criteria andReqOperatorEqualTo(String value) {
            addCriterion("REQ_OPERATOR =", value, "reqOperator");
            return (Criteria) this;
        }

        public Criteria andReqOperatorNotEqualTo(String value) {
            addCriterion("REQ_OPERATOR <>", value, "reqOperator");
            return (Criteria) this;
        }

        public Criteria andReqOperatorGreaterThan(String value) {
            addCriterion("REQ_OPERATOR >", value, "reqOperator");
            return (Criteria) this;
        }

        public Criteria andReqOperatorGreaterThanOrEqualTo(String value) {
            addCriterion("REQ_OPERATOR >=", value, "reqOperator");
            return (Criteria) this;
        }

        public Criteria andReqOperatorLessThan(String value) {
            addCriterion("REQ_OPERATOR <", value, "reqOperator");
            return (Criteria) this;
        }

        public Criteria andReqOperatorLessThanOrEqualTo(String value) {
            addCriterion("REQ_OPERATOR <=", value, "reqOperator");
            return (Criteria) this;
        }

        public Criteria andReqOperatorLike(String value) {
            addCriterion("REQ_OPERATOR like", value, "reqOperator");
            return (Criteria) this;
        }

        public Criteria andReqOperatorNotLike(String value) {
            addCriterion("REQ_OPERATOR not like", value, "reqOperator");
            return (Criteria) this;
        }

        public Criteria andReqOperatorIn(List<String> values) {
            addCriterion("REQ_OPERATOR in", values, "reqOperator");
            return (Criteria) this;
        }

        public Criteria andReqOperatorNotIn(List<String> values) {
            addCriterion("REQ_OPERATOR not in", values, "reqOperator");
            return (Criteria) this;
        }

        public Criteria andReqOperatorBetween(String value1, String value2) {
            addCriterion("REQ_OPERATOR between", value1, value2, "reqOperator");
            return (Criteria) this;
        }

        public Criteria andReqOperatorNotBetween(String value1, String value2) {
            addCriterion("REQ_OPERATOR not between", value1, value2, "reqOperator");
            return (Criteria) this;
        }

        public Criteria andReqTimeIsNull() {
            addCriterion("REQ_TIME is null");
            return (Criteria) this;
        }

        public Criteria andReqTimeIsNotNull() {
            addCriterion("REQ_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andReqTimeEqualTo(Date value) {
            addCriterion("REQ_TIME =", value, "reqTime");
            return (Criteria) this;
        }

        public Criteria andReqTimeNotEqualTo(Date value) {
            addCriterion("REQ_TIME <>", value, "reqTime");
            return (Criteria) this;
        }

        public Criteria andReqTimeGreaterThan(Date value) {
            addCriterion("REQ_TIME >", value, "reqTime");
            return (Criteria) this;
        }

        public Criteria andReqTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("REQ_TIME >=", value, "reqTime");
            return (Criteria) this;
        }

        public Criteria andReqTimeLessThan(Date value) {
            addCriterion("REQ_TIME <", value, "reqTime");
            return (Criteria) this;
        }

        public Criteria andReqTimeLessThanOrEqualTo(Date value) {
            addCriterion("REQ_TIME <=", value, "reqTime");
            return (Criteria) this;
        }

        public Criteria andReqTimeIn(List<Date> values) {
            addCriterion("REQ_TIME in", values, "reqTime");
            return (Criteria) this;
        }

        public Criteria andReqTimeNotIn(List<Date> values) {
            addCriterion("REQ_TIME not in", values, "reqTime");
            return (Criteria) this;
        }

        public Criteria andReqTimeBetween(Date value1, Date value2) {
            addCriterion("REQ_TIME between", value1, value2, "reqTime");
            return (Criteria) this;
        }

        public Criteria andReqTimeNotBetween(Date value1, Date value2) {
            addCriterion("REQ_TIME not between", value1, value2, "reqTime");
            return (Criteria) this;
        }

        public Criteria andReqRemarkIsNull() {
            addCriterion("REQ_REMARK is null");
            return (Criteria) this;
        }

        public Criteria andReqRemarkIsNotNull() {
            addCriterion("REQ_REMARK is not null");
            return (Criteria) this;
        }

        public Criteria andReqRemarkEqualTo(String value) {
            addCriterion("REQ_REMARK =", value, "reqRemark");
            return (Criteria) this;
        }

        public Criteria andReqRemarkNotEqualTo(String value) {
            addCriterion("REQ_REMARK <>", value, "reqRemark");
            return (Criteria) this;
        }

        public Criteria andReqRemarkGreaterThan(String value) {
            addCriterion("REQ_REMARK >", value, "reqRemark");
            return (Criteria) this;
        }

        public Criteria andReqRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("REQ_REMARK >=", value, "reqRemark");
            return (Criteria) this;
        }

        public Criteria andReqRemarkLessThan(String value) {
            addCriterion("REQ_REMARK <", value, "reqRemark");
            return (Criteria) this;
        }

        public Criteria andReqRemarkLessThanOrEqualTo(String value) {
            addCriterion("REQ_REMARK <=", value, "reqRemark");
            return (Criteria) this;
        }

        public Criteria andReqRemarkLike(String value) {
            addCriterion("REQ_REMARK like", value, "reqRemark");
            return (Criteria) this;
        }

        public Criteria andReqRemarkNotLike(String value) {
            addCriterion("REQ_REMARK not like", value, "reqRemark");
            return (Criteria) this;
        }

        public Criteria andReqRemarkIn(List<String> values) {
            addCriterion("REQ_REMARK in", values, "reqRemark");
            return (Criteria) this;
        }

        public Criteria andReqRemarkNotIn(List<String> values) {
            addCriterion("REQ_REMARK not in", values, "reqRemark");
            return (Criteria) this;
        }

        public Criteria andReqRemarkBetween(String value1, String value2) {
            addCriterion("REQ_REMARK between", value1, value2, "reqRemark");
            return (Criteria) this;
        }

        public Criteria andReqRemarkNotBetween(String value1, String value2) {
            addCriterion("REQ_REMARK not between", value1, value2, "reqRemark");
            return (Criteria) this;
        }

        public Criteria andApprOperatorIsNull() {
            addCriterion("APPR_OPERATOR is null");
            return (Criteria) this;
        }

        public Criteria andApprOperatorIsNotNull() {
            addCriterion("APPR_OPERATOR is not null");
            return (Criteria) this;
        }

        public Criteria andApprOperatorEqualTo(String value) {
            addCriterion("APPR_OPERATOR =", value, "apprOperator");
            return (Criteria) this;
        }

        public Criteria andApprOperatorNotEqualTo(String value) {
            addCriterion("APPR_OPERATOR <>", value, "apprOperator");
            return (Criteria) this;
        }

        public Criteria andApprOperatorGreaterThan(String value) {
            addCriterion("APPR_OPERATOR >", value, "apprOperator");
            return (Criteria) this;
        }

        public Criteria andApprOperatorGreaterThanOrEqualTo(String value) {
            addCriterion("APPR_OPERATOR >=", value, "apprOperator");
            return (Criteria) this;
        }

        public Criteria andApprOperatorLessThan(String value) {
            addCriterion("APPR_OPERATOR <", value, "apprOperator");
            return (Criteria) this;
        }

        public Criteria andApprOperatorLessThanOrEqualTo(String value) {
            addCriterion("APPR_OPERATOR <=", value, "apprOperator");
            return (Criteria) this;
        }

        public Criteria andApprOperatorLike(String value) {
            addCriterion("APPR_OPERATOR like", value, "apprOperator");
            return (Criteria) this;
        }

        public Criteria andApprOperatorNotLike(String value) {
            addCriterion("APPR_OPERATOR not like", value, "apprOperator");
            return (Criteria) this;
        }

        public Criteria andApprOperatorIn(List<String> values) {
            addCriterion("APPR_OPERATOR in", values, "apprOperator");
            return (Criteria) this;
        }

        public Criteria andApprOperatorNotIn(List<String> values) {
            addCriterion("APPR_OPERATOR not in", values, "apprOperator");
            return (Criteria) this;
        }

        public Criteria andApprOperatorBetween(String value1, String value2) {
            addCriterion("APPR_OPERATOR between", value1, value2, "apprOperator");
            return (Criteria) this;
        }

        public Criteria andApprOperatorNotBetween(String value1, String value2) {
            addCriterion("APPR_OPERATOR not between", value1, value2, "apprOperator");
            return (Criteria) this;
        }

        public Criteria andApprTimeIsNull() {
            addCriterion("APPR_TIME is null");
            return (Criteria) this;
        }

        public Criteria andApprTimeIsNotNull() {
            addCriterion("APPR_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andApprTimeEqualTo(Date value) {
            addCriterion("APPR_TIME =", value, "apprTime");
            return (Criteria) this;
        }

        public Criteria andApprTimeNotEqualTo(Date value) {
            addCriterion("APPR_TIME <>", value, "apprTime");
            return (Criteria) this;
        }

        public Criteria andApprTimeGreaterThan(Date value) {
            addCriterion("APPR_TIME >", value, "apprTime");
            return (Criteria) this;
        }

        public Criteria andApprTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("APPR_TIME >=", value, "apprTime");
            return (Criteria) this;
        }

        public Criteria andApprTimeLessThan(Date value) {
            addCriterion("APPR_TIME <", value, "apprTime");
            return (Criteria) this;
        }

        public Criteria andApprTimeLessThanOrEqualTo(Date value) {
            addCriterion("APPR_TIME <=", value, "apprTime");
            return (Criteria) this;
        }

        public Criteria andApprTimeIn(List<Date> values) {
            addCriterion("APPR_TIME in", values, "apprTime");
            return (Criteria) this;
        }

        public Criteria andApprTimeNotIn(List<Date> values) {
            addCriterion("APPR_TIME not in", values, "apprTime");
            return (Criteria) this;
        }

        public Criteria andApprTimeBetween(Date value1, Date value2) {
            addCriterion("APPR_TIME between", value1, value2, "apprTime");
            return (Criteria) this;
        }

        public Criteria andApprTimeNotBetween(Date value1, Date value2) {
            addCriterion("APPR_TIME not between", value1, value2, "apprTime");
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