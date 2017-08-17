package com.gy.hsi.fs.mapper.vo.dbbs01;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.gy.hsi.fs.mapper.vo.AbstractPage;

public class TBsDebitExample extends AbstractPage {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TBsDebitExample() {
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

        public Criteria andDebitIdIsNull() {
            addCriterion("DEBIT_ID is null");
            return (Criteria) this;
        }

        public Criteria andDebitIdIsNotNull() {
            addCriterion("DEBIT_ID is not null");
            return (Criteria) this;
        }

        public Criteria andDebitIdEqualTo(String value) {
            addCriterion("DEBIT_ID =", value, "debitId");
            return (Criteria) this;
        }

        public Criteria andDebitIdNotEqualTo(String value) {
            addCriterion("DEBIT_ID <>", value, "debitId");
            return (Criteria) this;
        }

        public Criteria andDebitIdGreaterThan(String value) {
            addCriterion("DEBIT_ID >", value, "debitId");
            return (Criteria) this;
        }

        public Criteria andDebitIdGreaterThanOrEqualTo(String value) {
            addCriterion("DEBIT_ID >=", value, "debitId");
            return (Criteria) this;
        }

        public Criteria andDebitIdLessThan(String value) {
            addCriterion("DEBIT_ID <", value, "debitId");
            return (Criteria) this;
        }

        public Criteria andDebitIdLessThanOrEqualTo(String value) {
            addCriterion("DEBIT_ID <=", value, "debitId");
            return (Criteria) this;
        }

        public Criteria andDebitIdLike(String value) {
            addCriterion("DEBIT_ID like", value, "debitId");
            return (Criteria) this;
        }

        public Criteria andDebitIdNotLike(String value) {
            addCriterion("DEBIT_ID not like", value, "debitId");
            return (Criteria) this;
        }

        public Criteria andDebitIdIn(List<String> values) {
            addCriterion("DEBIT_ID in", values, "debitId");
            return (Criteria) this;
        }

        public Criteria andDebitIdNotIn(List<String> values) {
            addCriterion("DEBIT_ID not in", values, "debitId");
            return (Criteria) this;
        }

        public Criteria andDebitIdBetween(String value1, String value2) {
            addCriterion("DEBIT_ID between", value1, value2, "debitId");
            return (Criteria) this;
        }

        public Criteria andDebitIdNotBetween(String value1, String value2) {
            addCriterion("DEBIT_ID not between", value1, value2, "debitId");
            return (Criteria) this;
        }

        public Criteria andPayTimeIsNull() {
            addCriterion("PAY_TIME is null");
            return (Criteria) this;
        }

        public Criteria andPayTimeIsNotNull() {
            addCriterion("PAY_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andPayTimeEqualTo(Date value) {
            addCriterion("PAY_TIME =", value, "payTime");
            return (Criteria) this;
        }

        public Criteria andPayTimeNotEqualTo(Date value) {
            addCriterion("PAY_TIME <>", value, "payTime");
            return (Criteria) this;
        }

        public Criteria andPayTimeGreaterThan(Date value) {
            addCriterion("PAY_TIME >", value, "payTime");
            return (Criteria) this;
        }

        public Criteria andPayTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("PAY_TIME >=", value, "payTime");
            return (Criteria) this;
        }

        public Criteria andPayTimeLessThan(Date value) {
            addCriterion("PAY_TIME <", value, "payTime");
            return (Criteria) this;
        }

        public Criteria andPayTimeLessThanOrEqualTo(Date value) {
            addCriterion("PAY_TIME <=", value, "payTime");
            return (Criteria) this;
        }

        public Criteria andPayTimeIn(List<Date> values) {
            addCriterion("PAY_TIME in", values, "payTime");
            return (Criteria) this;
        }

        public Criteria andPayTimeNotIn(List<Date> values) {
            addCriterion("PAY_TIME not in", values, "payTime");
            return (Criteria) this;
        }

        public Criteria andPayTimeBetween(Date value1, Date value2) {
            addCriterion("PAY_TIME between", value1, value2, "payTime");
            return (Criteria) this;
        }

        public Criteria andPayTimeNotBetween(Date value1, Date value2) {
            addCriterion("PAY_TIME not between", value1, value2, "payTime");
            return (Criteria) this;
        }

        public Criteria andPayAmountIsNull() {
            addCriterion("PAY_AMOUNT is null");
            return (Criteria) this;
        }

        public Criteria andPayAmountIsNotNull() {
            addCriterion("PAY_AMOUNT is not null");
            return (Criteria) this;
        }

        public Criteria andPayAmountEqualTo(BigDecimal value) {
            addCriterion("PAY_AMOUNT =", value, "payAmount");
            return (Criteria) this;
        }

        public Criteria andPayAmountNotEqualTo(BigDecimal value) {
            addCriterion("PAY_AMOUNT <>", value, "payAmount");
            return (Criteria) this;
        }

        public Criteria andPayAmountGreaterThan(BigDecimal value) {
            addCriterion("PAY_AMOUNT >", value, "payAmount");
            return (Criteria) this;
        }

        public Criteria andPayAmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("PAY_AMOUNT >=", value, "payAmount");
            return (Criteria) this;
        }

        public Criteria andPayAmountLessThan(BigDecimal value) {
            addCriterion("PAY_AMOUNT <", value, "payAmount");
            return (Criteria) this;
        }

        public Criteria andPayAmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("PAY_AMOUNT <=", value, "payAmount");
            return (Criteria) this;
        }

        public Criteria andPayAmountIn(List<BigDecimal> values) {
            addCriterion("PAY_AMOUNT in", values, "payAmount");
            return (Criteria) this;
        }

        public Criteria andPayAmountNotIn(List<BigDecimal> values) {
            addCriterion("PAY_AMOUNT not in", values, "payAmount");
            return (Criteria) this;
        }

        public Criteria andPayAmountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("PAY_AMOUNT between", value1, value2, "payAmount");
            return (Criteria) this;
        }

        public Criteria andPayAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("PAY_AMOUNT not between", value1, value2, "payAmount");
            return (Criteria) this;
        }

        public Criteria andPayTypeIsNull() {
            addCriterion("PAY_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andPayTypeIsNotNull() {
            addCriterion("PAY_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andPayTypeEqualTo(Short value) {
            addCriterion("PAY_TYPE =", value, "payType");
            return (Criteria) this;
        }

        public Criteria andPayTypeNotEqualTo(Short value) {
            addCriterion("PAY_TYPE <>", value, "payType");
            return (Criteria) this;
        }

        public Criteria andPayTypeGreaterThan(Short value) {
            addCriterion("PAY_TYPE >", value, "payType");
            return (Criteria) this;
        }

        public Criteria andPayTypeGreaterThanOrEqualTo(Short value) {
            addCriterion("PAY_TYPE >=", value, "payType");
            return (Criteria) this;
        }

        public Criteria andPayTypeLessThan(Short value) {
            addCriterion("PAY_TYPE <", value, "payType");
            return (Criteria) this;
        }

        public Criteria andPayTypeLessThanOrEqualTo(Short value) {
            addCriterion("PAY_TYPE <=", value, "payType");
            return (Criteria) this;
        }

        public Criteria andPayTypeIn(List<Short> values) {
            addCriterion("PAY_TYPE in", values, "payType");
            return (Criteria) this;
        }

        public Criteria andPayTypeNotIn(List<Short> values) {
            addCriterion("PAY_TYPE not in", values, "payType");
            return (Criteria) this;
        }

        public Criteria andPayTypeBetween(Short value1, Short value2) {
            addCriterion("PAY_TYPE between", value1, value2, "payType");
            return (Criteria) this;
        }

        public Criteria andPayTypeNotBetween(Short value1, Short value2) {
            addCriterion("PAY_TYPE not between", value1, value2, "payType");
            return (Criteria) this;
        }

        public Criteria andPayEntCustNameIsNull() {
            addCriterion("PAY_ENT_CUST_NAME is null");
            return (Criteria) this;
        }

        public Criteria andPayEntCustNameIsNotNull() {
            addCriterion("PAY_ENT_CUST_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andPayEntCustNameEqualTo(String value) {
            addCriterion("PAY_ENT_CUST_NAME =", value, "payEntCustName");
            return (Criteria) this;
        }

        public Criteria andPayEntCustNameNotEqualTo(String value) {
            addCriterion("PAY_ENT_CUST_NAME <>", value, "payEntCustName");
            return (Criteria) this;
        }

        public Criteria andPayEntCustNameGreaterThan(String value) {
            addCriterion("PAY_ENT_CUST_NAME >", value, "payEntCustName");
            return (Criteria) this;
        }

        public Criteria andPayEntCustNameGreaterThanOrEqualTo(String value) {
            addCriterion("PAY_ENT_CUST_NAME >=", value, "payEntCustName");
            return (Criteria) this;
        }

        public Criteria andPayEntCustNameLessThan(String value) {
            addCriterion("PAY_ENT_CUST_NAME <", value, "payEntCustName");
            return (Criteria) this;
        }

        public Criteria andPayEntCustNameLessThanOrEqualTo(String value) {
            addCriterion("PAY_ENT_CUST_NAME <=", value, "payEntCustName");
            return (Criteria) this;
        }

        public Criteria andPayEntCustNameLike(String value) {
            addCriterion("PAY_ENT_CUST_NAME like", value, "payEntCustName");
            return (Criteria) this;
        }

        public Criteria andPayEntCustNameNotLike(String value) {
            addCriterion("PAY_ENT_CUST_NAME not like", value, "payEntCustName");
            return (Criteria) this;
        }

        public Criteria andPayEntCustNameIn(List<String> values) {
            addCriterion("PAY_ENT_CUST_NAME in", values, "payEntCustName");
            return (Criteria) this;
        }

        public Criteria andPayEntCustNameNotIn(List<String> values) {
            addCriterion("PAY_ENT_CUST_NAME not in", values, "payEntCustName");
            return (Criteria) this;
        }

        public Criteria andPayEntCustNameBetween(String value1, String value2) {
            addCriterion("PAY_ENT_CUST_NAME between", value1, value2, "payEntCustName");
            return (Criteria) this;
        }

        public Criteria andPayEntCustNameNotBetween(String value1, String value2) {
            addCriterion("PAY_ENT_CUST_NAME not between", value1, value2, "payEntCustName");
            return (Criteria) this;
        }

        public Criteria andPayBankNameIsNull() {
            addCriterion("PAY_BANK_NAME is null");
            return (Criteria) this;
        }

        public Criteria andPayBankNameIsNotNull() {
            addCriterion("PAY_BANK_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andPayBankNameEqualTo(String value) {
            addCriterion("PAY_BANK_NAME =", value, "payBankName");
            return (Criteria) this;
        }

        public Criteria andPayBankNameNotEqualTo(String value) {
            addCriterion("PAY_BANK_NAME <>", value, "payBankName");
            return (Criteria) this;
        }

        public Criteria andPayBankNameGreaterThan(String value) {
            addCriterion("PAY_BANK_NAME >", value, "payBankName");
            return (Criteria) this;
        }

        public Criteria andPayBankNameGreaterThanOrEqualTo(String value) {
            addCriterion("PAY_BANK_NAME >=", value, "payBankName");
            return (Criteria) this;
        }

        public Criteria andPayBankNameLessThan(String value) {
            addCriterion("PAY_BANK_NAME <", value, "payBankName");
            return (Criteria) this;
        }

        public Criteria andPayBankNameLessThanOrEqualTo(String value) {
            addCriterion("PAY_BANK_NAME <=", value, "payBankName");
            return (Criteria) this;
        }

        public Criteria andPayBankNameLike(String value) {
            addCriterion("PAY_BANK_NAME like", value, "payBankName");
            return (Criteria) this;
        }

        public Criteria andPayBankNameNotLike(String value) {
            addCriterion("PAY_BANK_NAME not like", value, "payBankName");
            return (Criteria) this;
        }

        public Criteria andPayBankNameIn(List<String> values) {
            addCriterion("PAY_BANK_NAME in", values, "payBankName");
            return (Criteria) this;
        }

        public Criteria andPayBankNameNotIn(List<String> values) {
            addCriterion("PAY_BANK_NAME not in", values, "payBankName");
            return (Criteria) this;
        }

        public Criteria andPayBankNameBetween(String value1, String value2) {
            addCriterion("PAY_BANK_NAME between", value1, value2, "payBankName");
            return (Criteria) this;
        }

        public Criteria andPayBankNameNotBetween(String value1, String value2) {
            addCriterion("PAY_BANK_NAME not between", value1, value2, "payBankName");
            return (Criteria) this;
        }

        public Criteria andPayBankNoIsNull() {
            addCriterion("PAY_BANK_NO is null");
            return (Criteria) this;
        }

        public Criteria andPayBankNoIsNotNull() {
            addCriterion("PAY_BANK_NO is not null");
            return (Criteria) this;
        }

        public Criteria andPayBankNoEqualTo(String value) {
            addCriterion("PAY_BANK_NO =", value, "payBankNo");
            return (Criteria) this;
        }

        public Criteria andPayBankNoNotEqualTo(String value) {
            addCriterion("PAY_BANK_NO <>", value, "payBankNo");
            return (Criteria) this;
        }

        public Criteria andPayBankNoGreaterThan(String value) {
            addCriterion("PAY_BANK_NO >", value, "payBankNo");
            return (Criteria) this;
        }

        public Criteria andPayBankNoGreaterThanOrEqualTo(String value) {
            addCriterion("PAY_BANK_NO >=", value, "payBankNo");
            return (Criteria) this;
        }

        public Criteria andPayBankNoLessThan(String value) {
            addCriterion("PAY_BANK_NO <", value, "payBankNo");
            return (Criteria) this;
        }

        public Criteria andPayBankNoLessThanOrEqualTo(String value) {
            addCriterion("PAY_BANK_NO <=", value, "payBankNo");
            return (Criteria) this;
        }

        public Criteria andPayBankNoLike(String value) {
            addCriterion("PAY_BANK_NO like", value, "payBankNo");
            return (Criteria) this;
        }

        public Criteria andPayBankNoNotLike(String value) {
            addCriterion("PAY_BANK_NO not like", value, "payBankNo");
            return (Criteria) this;
        }

        public Criteria andPayBankNoIn(List<String> values) {
            addCriterion("PAY_BANK_NO in", values, "payBankNo");
            return (Criteria) this;
        }

        public Criteria andPayBankNoNotIn(List<String> values) {
            addCriterion("PAY_BANK_NO not in", values, "payBankNo");
            return (Criteria) this;
        }

        public Criteria andPayBankNoBetween(String value1, String value2) {
            addCriterion("PAY_BANK_NO between", value1, value2, "payBankNo");
            return (Criteria) this;
        }

        public Criteria andPayBankNoNotBetween(String value1, String value2) {
            addCriterion("PAY_BANK_NO not between", value1, value2, "payBankNo");
            return (Criteria) this;
        }

        public Criteria andPayUseIsNull() {
            addCriterion("PAY_USE is null");
            return (Criteria) this;
        }

        public Criteria andPayUseIsNotNull() {
            addCriterion("PAY_USE is not null");
            return (Criteria) this;
        }

        public Criteria andPayUseEqualTo(String value) {
            addCriterion("PAY_USE =", value, "payUse");
            return (Criteria) this;
        }

        public Criteria andPayUseNotEqualTo(String value) {
            addCriterion("PAY_USE <>", value, "payUse");
            return (Criteria) this;
        }

        public Criteria andPayUseGreaterThan(String value) {
            addCriterion("PAY_USE >", value, "payUse");
            return (Criteria) this;
        }

        public Criteria andPayUseGreaterThanOrEqualTo(String value) {
            addCriterion("PAY_USE >=", value, "payUse");
            return (Criteria) this;
        }

        public Criteria andPayUseLessThan(String value) {
            addCriterion("PAY_USE <", value, "payUse");
            return (Criteria) this;
        }

        public Criteria andPayUseLessThanOrEqualTo(String value) {
            addCriterion("PAY_USE <=", value, "payUse");
            return (Criteria) this;
        }

        public Criteria andPayUseLike(String value) {
            addCriterion("PAY_USE like", value, "payUse");
            return (Criteria) this;
        }

        public Criteria andPayUseNotLike(String value) {
            addCriterion("PAY_USE not like", value, "payUse");
            return (Criteria) this;
        }

        public Criteria andPayUseIn(List<String> values) {
            addCriterion("PAY_USE in", values, "payUse");
            return (Criteria) this;
        }

        public Criteria andPayUseNotIn(List<String> values) {
            addCriterion("PAY_USE not in", values, "payUse");
            return (Criteria) this;
        }

        public Criteria andPayUseBetween(String value1, String value2) {
            addCriterion("PAY_USE between", value1, value2, "payUse");
            return (Criteria) this;
        }

        public Criteria andPayUseNotBetween(String value1, String value2) {
            addCriterion("PAY_USE not between", value1, value2, "payUse");
            return (Criteria) this;
        }

        public Criteria andUseEntCustNameIsNull() {
            addCriterion("USE_ENT_CUST_NAME is null");
            return (Criteria) this;
        }

        public Criteria andUseEntCustNameIsNotNull() {
            addCriterion("USE_ENT_CUST_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andUseEntCustNameEqualTo(String value) {
            addCriterion("USE_ENT_CUST_NAME =", value, "useEntCustName");
            return (Criteria) this;
        }

        public Criteria andUseEntCustNameNotEqualTo(String value) {
            addCriterion("USE_ENT_CUST_NAME <>", value, "useEntCustName");
            return (Criteria) this;
        }

        public Criteria andUseEntCustNameGreaterThan(String value) {
            addCriterion("USE_ENT_CUST_NAME >", value, "useEntCustName");
            return (Criteria) this;
        }

        public Criteria andUseEntCustNameGreaterThanOrEqualTo(String value) {
            addCriterion("USE_ENT_CUST_NAME >=", value, "useEntCustName");
            return (Criteria) this;
        }

        public Criteria andUseEntCustNameLessThan(String value) {
            addCriterion("USE_ENT_CUST_NAME <", value, "useEntCustName");
            return (Criteria) this;
        }

        public Criteria andUseEntCustNameLessThanOrEqualTo(String value) {
            addCriterion("USE_ENT_CUST_NAME <=", value, "useEntCustName");
            return (Criteria) this;
        }

        public Criteria andUseEntCustNameLike(String value) {
            addCriterion("USE_ENT_CUST_NAME like", value, "useEntCustName");
            return (Criteria) this;
        }

        public Criteria andUseEntCustNameNotLike(String value) {
            addCriterion("USE_ENT_CUST_NAME not like", value, "useEntCustName");
            return (Criteria) this;
        }

        public Criteria andUseEntCustNameIn(List<String> values) {
            addCriterion("USE_ENT_CUST_NAME in", values, "useEntCustName");
            return (Criteria) this;
        }

        public Criteria andUseEntCustNameNotIn(List<String> values) {
            addCriterion("USE_ENT_CUST_NAME not in", values, "useEntCustName");
            return (Criteria) this;
        }

        public Criteria andUseEntCustNameBetween(String value1, String value2) {
            addCriterion("USE_ENT_CUST_NAME between", value1, value2, "useEntCustName");
            return (Criteria) this;
        }

        public Criteria andUseEntCustNameNotBetween(String value1, String value2) {
            addCriterion("USE_ENT_CUST_NAME not between", value1, value2, "useEntCustName");
            return (Criteria) this;
        }

        public Criteria andDebitStatusIsNull() {
            addCriterion("DEBIT_STATUS is null");
            return (Criteria) this;
        }

        public Criteria andDebitStatusIsNotNull() {
            addCriterion("DEBIT_STATUS is not null");
            return (Criteria) this;
        }

        public Criteria andDebitStatusEqualTo(Short value) {
            addCriterion("DEBIT_STATUS =", value, "debitStatus");
            return (Criteria) this;
        }

        public Criteria andDebitStatusNotEqualTo(Short value) {
            addCriterion("DEBIT_STATUS <>", value, "debitStatus");
            return (Criteria) this;
        }

        public Criteria andDebitStatusGreaterThan(Short value) {
            addCriterion("DEBIT_STATUS >", value, "debitStatus");
            return (Criteria) this;
        }

        public Criteria andDebitStatusGreaterThanOrEqualTo(Short value) {
            addCriterion("DEBIT_STATUS >=", value, "debitStatus");
            return (Criteria) this;
        }

        public Criteria andDebitStatusLessThan(Short value) {
            addCriterion("DEBIT_STATUS <", value, "debitStatus");
            return (Criteria) this;
        }

        public Criteria andDebitStatusLessThanOrEqualTo(Short value) {
            addCriterion("DEBIT_STATUS <=", value, "debitStatus");
            return (Criteria) this;
        }

        public Criteria andDebitStatusIn(List<Short> values) {
            addCriterion("DEBIT_STATUS in", values, "debitStatus");
            return (Criteria) this;
        }

        public Criteria andDebitStatusNotIn(List<Short> values) {
            addCriterion("DEBIT_STATUS not in", values, "debitStatus");
            return (Criteria) this;
        }

        public Criteria andDebitStatusBetween(Short value1, Short value2) {
            addCriterion("DEBIT_STATUS between", value1, value2, "debitStatus");
            return (Criteria) this;
        }

        public Criteria andDebitStatusNotBetween(Short value1, Short value2) {
            addCriterion("DEBIT_STATUS not between", value1, value2, "debitStatus");
            return (Criteria) this;
        }

        public Criteria andFilenameIsNull() {
            addCriterion("FILENAME is null");
            return (Criteria) this;
        }

        public Criteria andFilenameIsNotNull() {
            addCriterion("FILENAME is not null");
            return (Criteria) this;
        }

        public Criteria andFilenameEqualTo(String value) {
            addCriterion("FILENAME =", value, "filename");
            return (Criteria) this;
        }

        public Criteria andFilenameNotEqualTo(String value) {
            addCriterion("FILENAME <>", value, "filename");
            return (Criteria) this;
        }

        public Criteria andFilenameGreaterThan(String value) {
            addCriterion("FILENAME >", value, "filename");
            return (Criteria) this;
        }

        public Criteria andFilenameGreaterThanOrEqualTo(String value) {
            addCriterion("FILENAME >=", value, "filename");
            return (Criteria) this;
        }

        public Criteria andFilenameLessThan(String value) {
            addCriterion("FILENAME <", value, "filename");
            return (Criteria) this;
        }

        public Criteria andFilenameLessThanOrEqualTo(String value) {
            addCriterion("FILENAME <=", value, "filename");
            return (Criteria) this;
        }

        public Criteria andFilenameLike(String value) {
            addCriterion("FILENAME like", value, "filename");
            return (Criteria) this;
        }

        public Criteria andFilenameNotLike(String value) {
            addCriterion("FILENAME not like", value, "filename");
            return (Criteria) this;
        }

        public Criteria andFilenameIn(List<String> values) {
            addCriterion("FILENAME in", values, "filename");
            return (Criteria) this;
        }

        public Criteria andFilenameNotIn(List<String> values) {
            addCriterion("FILENAME not in", values, "filename");
            return (Criteria) this;
        }

        public Criteria andFilenameBetween(String value1, String value2) {
            addCriterion("FILENAME between", value1, value2, "filename");
            return (Criteria) this;
        }

        public Criteria andFilenameNotBetween(String value1, String value2) {
            addCriterion("FILENAME not between", value1, value2, "filename");
            return (Criteria) this;
        }

        public Criteria andFileIdIsNull() {
            addCriterion("FILE_ID is null");
            return (Criteria) this;
        }

        public Criteria andFileIdIsNotNull() {
            addCriterion("FILE_ID is not null");
            return (Criteria) this;
        }

        public Criteria andFileIdEqualTo(String value) {
            addCriterion("FILE_ID =", value, "fileId");
            return (Criteria) this;
        }

        public Criteria andFileIdNotEqualTo(String value) {
            addCriterion("FILE_ID <>", value, "fileId");
            return (Criteria) this;
        }

        public Criteria andFileIdGreaterThan(String value) {
            addCriterion("FILE_ID >", value, "fileId");
            return (Criteria) this;
        }

        public Criteria andFileIdGreaterThanOrEqualTo(String value) {
            addCriterion("FILE_ID >=", value, "fileId");
            return (Criteria) this;
        }

        public Criteria andFileIdLessThan(String value) {
            addCriterion("FILE_ID <", value, "fileId");
            return (Criteria) this;
        }

        public Criteria andFileIdLessThanOrEqualTo(String value) {
            addCriterion("FILE_ID <=", value, "fileId");
            return (Criteria) this;
        }

        public Criteria andFileIdLike(String value) {
            addCriterion("FILE_ID like", value, "fileId");
            return (Criteria) this;
        }

        public Criteria andFileIdNotLike(String value) {
            addCriterion("FILE_ID not like", value, "fileId");
            return (Criteria) this;
        }

        public Criteria andFileIdIn(List<String> values) {
            addCriterion("FILE_ID in", values, "fileId");
            return (Criteria) this;
        }

        public Criteria andFileIdNotIn(List<String> values) {
            addCriterion("FILE_ID not in", values, "fileId");
            return (Criteria) this;
        }

        public Criteria andFileIdBetween(String value1, String value2) {
            addCriterion("FILE_ID between", value1, value2, "fileId");
            return (Criteria) this;
        }

        public Criteria andFileIdNotBetween(String value1, String value2) {
            addCriterion("FILE_ID not between", value1, value2, "fileId");
            return (Criteria) this;
        }

        public Criteria andDescriptionIsNull() {
            addCriterion("DESCRIPTION is null");
            return (Criteria) this;
        }

        public Criteria andDescriptionIsNotNull() {
            addCriterion("DESCRIPTION is not null");
            return (Criteria) this;
        }

        public Criteria andDescriptionEqualTo(String value) {
            addCriterion("DESCRIPTION =", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotEqualTo(String value) {
            addCriterion("DESCRIPTION <>", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionGreaterThan(String value) {
            addCriterion("DESCRIPTION >", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionGreaterThanOrEqualTo(String value) {
            addCriterion("DESCRIPTION >=", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLessThan(String value) {
            addCriterion("DESCRIPTION <", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLessThanOrEqualTo(String value) {
            addCriterion("DESCRIPTION <=", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLike(String value) {
            addCriterion("DESCRIPTION like", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotLike(String value) {
            addCriterion("DESCRIPTION not like", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionIn(List<String> values) {
            addCriterion("DESCRIPTION in", values, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotIn(List<String> values) {
            addCriterion("DESCRIPTION not in", values, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionBetween(String value1, String value2) {
            addCriterion("DESCRIPTION between", value1, value2, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotBetween(String value1, String value2) {
            addCriterion("DESCRIPTION not between", value1, value2, "description");
            return (Criteria) this;
        }

        public Criteria andAuditRecordIsNull() {
            addCriterion("AUDIT_RECORD is null");
            return (Criteria) this;
        }

        public Criteria andAuditRecordIsNotNull() {
            addCriterion("AUDIT_RECORD is not null");
            return (Criteria) this;
        }

        public Criteria andAuditRecordEqualTo(String value) {
            addCriterion("AUDIT_RECORD =", value, "auditRecord");
            return (Criteria) this;
        }

        public Criteria andAuditRecordNotEqualTo(String value) {
            addCriterion("AUDIT_RECORD <>", value, "auditRecord");
            return (Criteria) this;
        }

        public Criteria andAuditRecordGreaterThan(String value) {
            addCriterion("AUDIT_RECORD >", value, "auditRecord");
            return (Criteria) this;
        }

        public Criteria andAuditRecordGreaterThanOrEqualTo(String value) {
            addCriterion("AUDIT_RECORD >=", value, "auditRecord");
            return (Criteria) this;
        }

        public Criteria andAuditRecordLessThan(String value) {
            addCriterion("AUDIT_RECORD <", value, "auditRecord");
            return (Criteria) this;
        }

        public Criteria andAuditRecordLessThanOrEqualTo(String value) {
            addCriterion("AUDIT_RECORD <=", value, "auditRecord");
            return (Criteria) this;
        }

        public Criteria andAuditRecordLike(String value) {
            addCriterion("AUDIT_RECORD like", value, "auditRecord");
            return (Criteria) this;
        }

        public Criteria andAuditRecordNotLike(String value) {
            addCriterion("AUDIT_RECORD not like", value, "auditRecord");
            return (Criteria) this;
        }

        public Criteria andAuditRecordIn(List<String> values) {
            addCriterion("AUDIT_RECORD in", values, "auditRecord");
            return (Criteria) this;
        }

        public Criteria andAuditRecordNotIn(List<String> values) {
            addCriterion("AUDIT_RECORD not in", values, "auditRecord");
            return (Criteria) this;
        }

        public Criteria andAuditRecordBetween(String value1, String value2) {
            addCriterion("AUDIT_RECORD between", value1, value2, "auditRecord");
            return (Criteria) this;
        }

        public Criteria andAuditRecordNotBetween(String value1, String value2) {
            addCriterion("AUDIT_RECORD not between", value1, value2, "auditRecord");
            return (Criteria) this;
        }

        public Criteria andAuditCustIdIsNull() {
            addCriterion("AUDIT_CUST_ID is null");
            return (Criteria) this;
        }

        public Criteria andAuditCustIdIsNotNull() {
            addCriterion("AUDIT_CUST_ID is not null");
            return (Criteria) this;
        }

        public Criteria andAuditCustIdEqualTo(String value) {
            addCriterion("AUDIT_CUST_ID =", value, "auditCustId");
            return (Criteria) this;
        }

        public Criteria andAuditCustIdNotEqualTo(String value) {
            addCriterion("AUDIT_CUST_ID <>", value, "auditCustId");
            return (Criteria) this;
        }

        public Criteria andAuditCustIdGreaterThan(String value) {
            addCriterion("AUDIT_CUST_ID >", value, "auditCustId");
            return (Criteria) this;
        }

        public Criteria andAuditCustIdGreaterThanOrEqualTo(String value) {
            addCriterion("AUDIT_CUST_ID >=", value, "auditCustId");
            return (Criteria) this;
        }

        public Criteria andAuditCustIdLessThan(String value) {
            addCriterion("AUDIT_CUST_ID <", value, "auditCustId");
            return (Criteria) this;
        }

        public Criteria andAuditCustIdLessThanOrEqualTo(String value) {
            addCriterion("AUDIT_CUST_ID <=", value, "auditCustId");
            return (Criteria) this;
        }

        public Criteria andAuditCustIdLike(String value) {
            addCriterion("AUDIT_CUST_ID like", value, "auditCustId");
            return (Criteria) this;
        }

        public Criteria andAuditCustIdNotLike(String value) {
            addCriterion("AUDIT_CUST_ID not like", value, "auditCustId");
            return (Criteria) this;
        }

        public Criteria andAuditCustIdIn(List<String> values) {
            addCriterion("AUDIT_CUST_ID in", values, "auditCustId");
            return (Criteria) this;
        }

        public Criteria andAuditCustIdNotIn(List<String> values) {
            addCriterion("AUDIT_CUST_ID not in", values, "auditCustId");
            return (Criteria) this;
        }

        public Criteria andAuditCustIdBetween(String value1, String value2) {
            addCriterion("AUDIT_CUST_ID between", value1, value2, "auditCustId");
            return (Criteria) this;
        }

        public Criteria andAuditCustIdNotBetween(String value1, String value2) {
            addCriterion("AUDIT_CUST_ID not between", value1, value2, "auditCustId");
            return (Criteria) this;
        }

        public Criteria andAuditCustNameIsNull() {
            addCriterion("AUDIT_CUST_NAME is null");
            return (Criteria) this;
        }

        public Criteria andAuditCustNameIsNotNull() {
            addCriterion("AUDIT_CUST_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andAuditCustNameEqualTo(String value) {
            addCriterion("AUDIT_CUST_NAME =", value, "auditCustName");
            return (Criteria) this;
        }

        public Criteria andAuditCustNameNotEqualTo(String value) {
            addCriterion("AUDIT_CUST_NAME <>", value, "auditCustName");
            return (Criteria) this;
        }

        public Criteria andAuditCustNameGreaterThan(String value) {
            addCriterion("AUDIT_CUST_NAME >", value, "auditCustName");
            return (Criteria) this;
        }

        public Criteria andAuditCustNameGreaterThanOrEqualTo(String value) {
            addCriterion("AUDIT_CUST_NAME >=", value, "auditCustName");
            return (Criteria) this;
        }

        public Criteria andAuditCustNameLessThan(String value) {
            addCriterion("AUDIT_CUST_NAME <", value, "auditCustName");
            return (Criteria) this;
        }

        public Criteria andAuditCustNameLessThanOrEqualTo(String value) {
            addCriterion("AUDIT_CUST_NAME <=", value, "auditCustName");
            return (Criteria) this;
        }

        public Criteria andAuditCustNameLike(String value) {
            addCriterion("AUDIT_CUST_NAME like", value, "auditCustName");
            return (Criteria) this;
        }

        public Criteria andAuditCustNameNotLike(String value) {
            addCriterion("AUDIT_CUST_NAME not like", value, "auditCustName");
            return (Criteria) this;
        }

        public Criteria andAuditCustNameIn(List<String> values) {
            addCriterion("AUDIT_CUST_NAME in", values, "auditCustName");
            return (Criteria) this;
        }

        public Criteria andAuditCustNameNotIn(List<String> values) {
            addCriterion("AUDIT_CUST_NAME not in", values, "auditCustName");
            return (Criteria) this;
        }

        public Criteria andAuditCustNameBetween(String value1, String value2) {
            addCriterion("AUDIT_CUST_NAME between", value1, value2, "auditCustName");
            return (Criteria) this;
        }

        public Criteria andAuditCustNameNotBetween(String value1, String value2) {
            addCriterion("AUDIT_CUST_NAME not between", value1, value2, "auditCustName");
            return (Criteria) this;
        }

        public Criteria andAuditTimeIsNull() {
            addCriterion("AUDIT_TIME is null");
            return (Criteria) this;
        }

        public Criteria andAuditTimeIsNotNull() {
            addCriterion("AUDIT_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andAuditTimeEqualTo(Date value) {
            addCriterion("AUDIT_TIME =", value, "auditTime");
            return (Criteria) this;
        }

        public Criteria andAuditTimeNotEqualTo(Date value) {
            addCriterion("AUDIT_TIME <>", value, "auditTime");
            return (Criteria) this;
        }

        public Criteria andAuditTimeGreaterThan(Date value) {
            addCriterion("AUDIT_TIME >", value, "auditTime");
            return (Criteria) this;
        }

        public Criteria andAuditTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("AUDIT_TIME >=", value, "auditTime");
            return (Criteria) this;
        }

        public Criteria andAuditTimeLessThan(Date value) {
            addCriterion("AUDIT_TIME <", value, "auditTime");
            return (Criteria) this;
        }

        public Criteria andAuditTimeLessThanOrEqualTo(Date value) {
            addCriterion("AUDIT_TIME <=", value, "auditTime");
            return (Criteria) this;
        }

        public Criteria andAuditTimeIn(List<Date> values) {
            addCriterion("AUDIT_TIME in", values, "auditTime");
            return (Criteria) this;
        }

        public Criteria andAuditTimeNotIn(List<Date> values) {
            addCriterion("AUDIT_TIME not in", values, "auditTime");
            return (Criteria) this;
        }

        public Criteria andAuditTimeBetween(Date value1, Date value2) {
            addCriterion("AUDIT_TIME between", value1, value2, "auditTime");
            return (Criteria) this;
        }

        public Criteria andAuditTimeNotBetween(Date value1, Date value2) {
            addCriterion("AUDIT_TIME not between", value1, value2, "auditTime");
            return (Criteria) this;
        }

        public Criteria andLinkAmountIsNull() {
            addCriterion("LINK_AMOUNT is null");
            return (Criteria) this;
        }

        public Criteria andLinkAmountIsNotNull() {
            addCriterion("LINK_AMOUNT is not null");
            return (Criteria) this;
        }

        public Criteria andLinkAmountEqualTo(BigDecimal value) {
            addCriterion("LINK_AMOUNT =", value, "linkAmount");
            return (Criteria) this;
        }

        public Criteria andLinkAmountNotEqualTo(BigDecimal value) {
            addCriterion("LINK_AMOUNT <>", value, "linkAmount");
            return (Criteria) this;
        }

        public Criteria andLinkAmountGreaterThan(BigDecimal value) {
            addCriterion("LINK_AMOUNT >", value, "linkAmount");
            return (Criteria) this;
        }

        public Criteria andLinkAmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("LINK_AMOUNT >=", value, "linkAmount");
            return (Criteria) this;
        }

        public Criteria andLinkAmountLessThan(BigDecimal value) {
            addCriterion("LINK_AMOUNT <", value, "linkAmount");
            return (Criteria) this;
        }

        public Criteria andLinkAmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("LINK_AMOUNT <=", value, "linkAmount");
            return (Criteria) this;
        }

        public Criteria andLinkAmountIn(List<BigDecimal> values) {
            addCriterion("LINK_AMOUNT in", values, "linkAmount");
            return (Criteria) this;
        }

        public Criteria andLinkAmountNotIn(List<BigDecimal> values) {
            addCriterion("LINK_AMOUNT not in", values, "linkAmount");
            return (Criteria) this;
        }

        public Criteria andLinkAmountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("LINK_AMOUNT between", value1, value2, "linkAmount");
            return (Criteria) this;
        }

        public Criteria andLinkAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("LINK_AMOUNT not between", value1, value2, "linkAmount");
            return (Criteria) this;
        }

        public Criteria andUnlinkAmountIsNull() {
            addCriterion("UNLINK_AMOUNT is null");
            return (Criteria) this;
        }

        public Criteria andUnlinkAmountIsNotNull() {
            addCriterion("UNLINK_AMOUNT is not null");
            return (Criteria) this;
        }

        public Criteria andUnlinkAmountEqualTo(BigDecimal value) {
            addCriterion("UNLINK_AMOUNT =", value, "unlinkAmount");
            return (Criteria) this;
        }

        public Criteria andUnlinkAmountNotEqualTo(BigDecimal value) {
            addCriterion("UNLINK_AMOUNT <>", value, "unlinkAmount");
            return (Criteria) this;
        }

        public Criteria andUnlinkAmountGreaterThan(BigDecimal value) {
            addCriterion("UNLINK_AMOUNT >", value, "unlinkAmount");
            return (Criteria) this;
        }

        public Criteria andUnlinkAmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("UNLINK_AMOUNT >=", value, "unlinkAmount");
            return (Criteria) this;
        }

        public Criteria andUnlinkAmountLessThan(BigDecimal value) {
            addCriterion("UNLINK_AMOUNT <", value, "unlinkAmount");
            return (Criteria) this;
        }

        public Criteria andUnlinkAmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("UNLINK_AMOUNT <=", value, "unlinkAmount");
            return (Criteria) this;
        }

        public Criteria andUnlinkAmountIn(List<BigDecimal> values) {
            addCriterion("UNLINK_AMOUNT in", values, "unlinkAmount");
            return (Criteria) this;
        }

        public Criteria andUnlinkAmountNotIn(List<BigDecimal> values) {
            addCriterion("UNLINK_AMOUNT not in", values, "unlinkAmount");
            return (Criteria) this;
        }

        public Criteria andUnlinkAmountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("UNLINK_AMOUNT between", value1, value2, "unlinkAmount");
            return (Criteria) this;
        }

        public Criteria andUnlinkAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("UNLINK_AMOUNT not between", value1, value2, "unlinkAmount");
            return (Criteria) this;
        }

        public Criteria andRefundAmountIsNull() {
            addCriterion("REFUND_AMOUNT is null");
            return (Criteria) this;
        }

        public Criteria andRefundAmountIsNotNull() {
            addCriterion("REFUND_AMOUNT is not null");
            return (Criteria) this;
        }

        public Criteria andRefundAmountEqualTo(BigDecimal value) {
            addCriterion("REFUND_AMOUNT =", value, "refundAmount");
            return (Criteria) this;
        }

        public Criteria andRefundAmountNotEqualTo(BigDecimal value) {
            addCriterion("REFUND_AMOUNT <>", value, "refundAmount");
            return (Criteria) this;
        }

        public Criteria andRefundAmountGreaterThan(BigDecimal value) {
            addCriterion("REFUND_AMOUNT >", value, "refundAmount");
            return (Criteria) this;
        }

        public Criteria andRefundAmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("REFUND_AMOUNT >=", value, "refundAmount");
            return (Criteria) this;
        }

        public Criteria andRefundAmountLessThan(BigDecimal value) {
            addCriterion("REFUND_AMOUNT <", value, "refundAmount");
            return (Criteria) this;
        }

        public Criteria andRefundAmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("REFUND_AMOUNT <=", value, "refundAmount");
            return (Criteria) this;
        }

        public Criteria andRefundAmountIn(List<BigDecimal> values) {
            addCriterion("REFUND_AMOUNT in", values, "refundAmount");
            return (Criteria) this;
        }

        public Criteria andRefundAmountNotIn(List<BigDecimal> values) {
            addCriterion("REFUND_AMOUNT not in", values, "refundAmount");
            return (Criteria) this;
        }

        public Criteria andRefundAmountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("REFUND_AMOUNT between", value1, value2, "refundAmount");
            return (Criteria) this;
        }

        public Criteria andRefundAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("REFUND_AMOUNT not between", value1, value2, "refundAmount");
            return (Criteria) this;
        }

        public Criteria andAccountInfoIdIsNull() {
            addCriterion("ACCOUNT_INFO_ID is null");
            return (Criteria) this;
        }

        public Criteria andAccountInfoIdIsNotNull() {
            addCriterion("ACCOUNT_INFO_ID is not null");
            return (Criteria) this;
        }

        public Criteria andAccountInfoIdEqualTo(String value) {
            addCriterion("ACCOUNT_INFO_ID =", value, "accountInfoId");
            return (Criteria) this;
        }

        public Criteria andAccountInfoIdNotEqualTo(String value) {
            addCriterion("ACCOUNT_INFO_ID <>", value, "accountInfoId");
            return (Criteria) this;
        }

        public Criteria andAccountInfoIdGreaterThan(String value) {
            addCriterion("ACCOUNT_INFO_ID >", value, "accountInfoId");
            return (Criteria) this;
        }

        public Criteria andAccountInfoIdGreaterThanOrEqualTo(String value) {
            addCriterion("ACCOUNT_INFO_ID >=", value, "accountInfoId");
            return (Criteria) this;
        }

        public Criteria andAccountInfoIdLessThan(String value) {
            addCriterion("ACCOUNT_INFO_ID <", value, "accountInfoId");
            return (Criteria) this;
        }

        public Criteria andAccountInfoIdLessThanOrEqualTo(String value) {
            addCriterion("ACCOUNT_INFO_ID <=", value, "accountInfoId");
            return (Criteria) this;
        }

        public Criteria andAccountInfoIdLike(String value) {
            addCriterion("ACCOUNT_INFO_ID like", value, "accountInfoId");
            return (Criteria) this;
        }

        public Criteria andAccountInfoIdNotLike(String value) {
            addCriterion("ACCOUNT_INFO_ID not like", value, "accountInfoId");
            return (Criteria) this;
        }

        public Criteria andAccountInfoIdIn(List<String> values) {
            addCriterion("ACCOUNT_INFO_ID in", values, "accountInfoId");
            return (Criteria) this;
        }

        public Criteria andAccountInfoIdNotIn(List<String> values) {
            addCriterion("ACCOUNT_INFO_ID not in", values, "accountInfoId");
            return (Criteria) this;
        }

        public Criteria andAccountInfoIdBetween(String value1, String value2) {
            addCriterion("ACCOUNT_INFO_ID between", value1, value2, "accountInfoId");
            return (Criteria) this;
        }

        public Criteria andAccountInfoIdNotBetween(String value1, String value2) {
            addCriterion("ACCOUNT_INFO_ID not between", value1, value2, "accountInfoId");
            return (Criteria) this;
        }

        public Criteria andBankBranchNameIsNull() {
            addCriterion("BANK_BRANCH_NAME is null");
            return (Criteria) this;
        }

        public Criteria andBankBranchNameIsNotNull() {
            addCriterion("BANK_BRANCH_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andBankBranchNameEqualTo(String value) {
            addCriterion("BANK_BRANCH_NAME =", value, "bankBranchName");
            return (Criteria) this;
        }

        public Criteria andBankBranchNameNotEqualTo(String value) {
            addCriterion("BANK_BRANCH_NAME <>", value, "bankBranchName");
            return (Criteria) this;
        }

        public Criteria andBankBranchNameGreaterThan(String value) {
            addCriterion("BANK_BRANCH_NAME >", value, "bankBranchName");
            return (Criteria) this;
        }

        public Criteria andBankBranchNameGreaterThanOrEqualTo(String value) {
            addCriterion("BANK_BRANCH_NAME >=", value, "bankBranchName");
            return (Criteria) this;
        }

        public Criteria andBankBranchNameLessThan(String value) {
            addCriterion("BANK_BRANCH_NAME <", value, "bankBranchName");
            return (Criteria) this;
        }

        public Criteria andBankBranchNameLessThanOrEqualTo(String value) {
            addCriterion("BANK_BRANCH_NAME <=", value, "bankBranchName");
            return (Criteria) this;
        }

        public Criteria andBankBranchNameLike(String value) {
            addCriterion("BANK_BRANCH_NAME like", value, "bankBranchName");
            return (Criteria) this;
        }

        public Criteria andBankBranchNameNotLike(String value) {
            addCriterion("BANK_BRANCH_NAME not like", value, "bankBranchName");
            return (Criteria) this;
        }

        public Criteria andBankBranchNameIn(List<String> values) {
            addCriterion("BANK_BRANCH_NAME in", values, "bankBranchName");
            return (Criteria) this;
        }

        public Criteria andBankBranchNameNotIn(List<String> values) {
            addCriterion("BANK_BRANCH_NAME not in", values, "bankBranchName");
            return (Criteria) this;
        }

        public Criteria andBankBranchNameBetween(String value1, String value2) {
            addCriterion("BANK_BRANCH_NAME between", value1, value2, "bankBranchName");
            return (Criteria) this;
        }

        public Criteria andBankBranchNameNotBetween(String value1, String value2) {
            addCriterion("BANK_BRANCH_NAME not between", value1, value2, "bankBranchName");
            return (Criteria) this;
        }

        public Criteria andReceiveAccountNameIsNull() {
            addCriterion("RECEIVE_ACCOUNT_NAME is null");
            return (Criteria) this;
        }

        public Criteria andReceiveAccountNameIsNotNull() {
            addCriterion("RECEIVE_ACCOUNT_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andReceiveAccountNameEqualTo(String value) {
            addCriterion("RECEIVE_ACCOUNT_NAME =", value, "receiveAccountName");
            return (Criteria) this;
        }

        public Criteria andReceiveAccountNameNotEqualTo(String value) {
            addCriterion("RECEIVE_ACCOUNT_NAME <>", value, "receiveAccountName");
            return (Criteria) this;
        }

        public Criteria andReceiveAccountNameGreaterThan(String value) {
            addCriterion("RECEIVE_ACCOUNT_NAME >", value, "receiveAccountName");
            return (Criteria) this;
        }

        public Criteria andReceiveAccountNameGreaterThanOrEqualTo(String value) {
            addCriterion("RECEIVE_ACCOUNT_NAME >=", value, "receiveAccountName");
            return (Criteria) this;
        }

        public Criteria andReceiveAccountNameLessThan(String value) {
            addCriterion("RECEIVE_ACCOUNT_NAME <", value, "receiveAccountName");
            return (Criteria) this;
        }

        public Criteria andReceiveAccountNameLessThanOrEqualTo(String value) {
            addCriterion("RECEIVE_ACCOUNT_NAME <=", value, "receiveAccountName");
            return (Criteria) this;
        }

        public Criteria andReceiveAccountNameLike(String value) {
            addCriterion("RECEIVE_ACCOUNT_NAME like", value, "receiveAccountName");
            return (Criteria) this;
        }

        public Criteria andReceiveAccountNameNotLike(String value) {
            addCriterion("RECEIVE_ACCOUNT_NAME not like", value, "receiveAccountName");
            return (Criteria) this;
        }

        public Criteria andReceiveAccountNameIn(List<String> values) {
            addCriterion("RECEIVE_ACCOUNT_NAME in", values, "receiveAccountName");
            return (Criteria) this;
        }

        public Criteria andReceiveAccountNameNotIn(List<String> values) {
            addCriterion("RECEIVE_ACCOUNT_NAME not in", values, "receiveAccountName");
            return (Criteria) this;
        }

        public Criteria andReceiveAccountNameBetween(String value1, String value2) {
            addCriterion("RECEIVE_ACCOUNT_NAME between", value1, value2, "receiveAccountName");
            return (Criteria) this;
        }

        public Criteria andReceiveAccountNameNotBetween(String value1, String value2) {
            addCriterion("RECEIVE_ACCOUNT_NAME not between", value1, value2, "receiveAccountName");
            return (Criteria) this;
        }

        public Criteria andReceiveAccountNoIsNull() {
            addCriterion("RECEIVE_ACCOUNT_NO is null");
            return (Criteria) this;
        }

        public Criteria andReceiveAccountNoIsNotNull() {
            addCriterion("RECEIVE_ACCOUNT_NO is not null");
            return (Criteria) this;
        }

        public Criteria andReceiveAccountNoEqualTo(String value) {
            addCriterion("RECEIVE_ACCOUNT_NO =", value, "receiveAccountNo");
            return (Criteria) this;
        }

        public Criteria andReceiveAccountNoNotEqualTo(String value) {
            addCriterion("RECEIVE_ACCOUNT_NO <>", value, "receiveAccountNo");
            return (Criteria) this;
        }

        public Criteria andReceiveAccountNoGreaterThan(String value) {
            addCriterion("RECEIVE_ACCOUNT_NO >", value, "receiveAccountNo");
            return (Criteria) this;
        }

        public Criteria andReceiveAccountNoGreaterThanOrEqualTo(String value) {
            addCriterion("RECEIVE_ACCOUNT_NO >=", value, "receiveAccountNo");
            return (Criteria) this;
        }

        public Criteria andReceiveAccountNoLessThan(String value) {
            addCriterion("RECEIVE_ACCOUNT_NO <", value, "receiveAccountNo");
            return (Criteria) this;
        }

        public Criteria andReceiveAccountNoLessThanOrEqualTo(String value) {
            addCriterion("RECEIVE_ACCOUNT_NO <=", value, "receiveAccountNo");
            return (Criteria) this;
        }

        public Criteria andReceiveAccountNoLike(String value) {
            addCriterion("RECEIVE_ACCOUNT_NO like", value, "receiveAccountNo");
            return (Criteria) this;
        }

        public Criteria andReceiveAccountNoNotLike(String value) {
            addCriterion("RECEIVE_ACCOUNT_NO not like", value, "receiveAccountNo");
            return (Criteria) this;
        }

        public Criteria andReceiveAccountNoIn(List<String> values) {
            addCriterion("RECEIVE_ACCOUNT_NO in", values, "receiveAccountNo");
            return (Criteria) this;
        }

        public Criteria andReceiveAccountNoNotIn(List<String> values) {
            addCriterion("RECEIVE_ACCOUNT_NO not in", values, "receiveAccountNo");
            return (Criteria) this;
        }

        public Criteria andReceiveAccountNoBetween(String value1, String value2) {
            addCriterion("RECEIVE_ACCOUNT_NO between", value1, value2, "receiveAccountNo");
            return (Criteria) this;
        }

        public Criteria andReceiveAccountNoNotBetween(String value1, String value2) {
            addCriterion("RECEIVE_ACCOUNT_NO not between", value1, value2, "receiveAccountNo");
            return (Criteria) this;
        }

        public Criteria andAccountReceivetimeIsNull() {
            addCriterion("ACCOUNT_RECEIVETIME is null");
            return (Criteria) this;
        }

        public Criteria andAccountReceivetimeIsNotNull() {
            addCriterion("ACCOUNT_RECEIVETIME is not null");
            return (Criteria) this;
        }

        public Criteria andAccountReceivetimeEqualTo(Date value) {
            addCriterion("ACCOUNT_RECEIVETIME =", value, "accountReceivetime");
            return (Criteria) this;
        }

        public Criteria andAccountReceivetimeNotEqualTo(Date value) {
            addCriterion("ACCOUNT_RECEIVETIME <>", value, "accountReceivetime");
            return (Criteria) this;
        }

        public Criteria andAccountReceivetimeGreaterThan(Date value) {
            addCriterion("ACCOUNT_RECEIVETIME >", value, "accountReceivetime");
            return (Criteria) this;
        }

        public Criteria andAccountReceivetimeGreaterThanOrEqualTo(Date value) {
            addCriterion("ACCOUNT_RECEIVETIME >=", value, "accountReceivetime");
            return (Criteria) this;
        }

        public Criteria andAccountReceivetimeLessThan(Date value) {
            addCriterion("ACCOUNT_RECEIVETIME <", value, "accountReceivetime");
            return (Criteria) this;
        }

        public Criteria andAccountReceivetimeLessThanOrEqualTo(Date value) {
            addCriterion("ACCOUNT_RECEIVETIME <=", value, "accountReceivetime");
            return (Criteria) this;
        }

        public Criteria andAccountReceivetimeIn(List<Date> values) {
            addCriterion("ACCOUNT_RECEIVETIME in", values, "accountReceivetime");
            return (Criteria) this;
        }

        public Criteria andAccountReceivetimeNotIn(List<Date> values) {
            addCriterion("ACCOUNT_RECEIVETIME not in", values, "accountReceivetime");
            return (Criteria) this;
        }

        public Criteria andAccountReceivetimeBetween(Date value1, Date value2) {
            addCriterion("ACCOUNT_RECEIVETIME between", value1, value2, "accountReceivetime");
            return (Criteria) this;
        }

        public Criteria andAccountReceivetimeNotBetween(Date value1, Date value2) {
            addCriterion("ACCOUNT_RECEIVETIME not between", value1, value2, "accountReceivetime");
            return (Criteria) this;
        }

        public Criteria andIsPlatformFillingIsNull() {
            addCriterion("IS_PLATFORM_FILLING is null");
            return (Criteria) this;
        }

        public Criteria andIsPlatformFillingIsNotNull() {
            addCriterion("IS_PLATFORM_FILLING is not null");
            return (Criteria) this;
        }

        public Criteria andIsPlatformFillingEqualTo(Short value) {
            addCriterion("IS_PLATFORM_FILLING =", value, "isPlatformFilling");
            return (Criteria) this;
        }

        public Criteria andIsPlatformFillingNotEqualTo(Short value) {
            addCriterion("IS_PLATFORM_FILLING <>", value, "isPlatformFilling");
            return (Criteria) this;
        }

        public Criteria andIsPlatformFillingGreaterThan(Short value) {
            addCriterion("IS_PLATFORM_FILLING >", value, "isPlatformFilling");
            return (Criteria) this;
        }

        public Criteria andIsPlatformFillingGreaterThanOrEqualTo(Short value) {
            addCriterion("IS_PLATFORM_FILLING >=", value, "isPlatformFilling");
            return (Criteria) this;
        }

        public Criteria andIsPlatformFillingLessThan(Short value) {
            addCriterion("IS_PLATFORM_FILLING <", value, "isPlatformFilling");
            return (Criteria) this;
        }

        public Criteria andIsPlatformFillingLessThanOrEqualTo(Short value) {
            addCriterion("IS_PLATFORM_FILLING <=", value, "isPlatformFilling");
            return (Criteria) this;
        }

        public Criteria andIsPlatformFillingIn(List<Short> values) {
            addCriterion("IS_PLATFORM_FILLING in", values, "isPlatformFilling");
            return (Criteria) this;
        }

        public Criteria andIsPlatformFillingNotIn(List<Short> values) {
            addCriterion("IS_PLATFORM_FILLING not in", values, "isPlatformFilling");
            return (Criteria) this;
        }

        public Criteria andIsPlatformFillingBetween(Short value1, Short value2) {
            addCriterion("IS_PLATFORM_FILLING between", value1, value2, "isPlatformFilling");
            return (Criteria) this;
        }

        public Criteria andIsPlatformFillingNotBetween(Short value1, Short value2) {
            addCriterion("IS_PLATFORM_FILLING not between", value1, value2, "isPlatformFilling");
            return (Criteria) this;
        }

        public Criteria andRefundRecordIsNull() {
            addCriterion("REFUND_RECORD is null");
            return (Criteria) this;
        }

        public Criteria andRefundRecordIsNotNull() {
            addCriterion("REFUND_RECORD is not null");
            return (Criteria) this;
        }

        public Criteria andRefundRecordEqualTo(String value) {
            addCriterion("REFUND_RECORD =", value, "refundRecord");
            return (Criteria) this;
        }

        public Criteria andRefundRecordNotEqualTo(String value) {
            addCriterion("REFUND_RECORD <>", value, "refundRecord");
            return (Criteria) this;
        }

        public Criteria andRefundRecordGreaterThan(String value) {
            addCriterion("REFUND_RECORD >", value, "refundRecord");
            return (Criteria) this;
        }

        public Criteria andRefundRecordGreaterThanOrEqualTo(String value) {
            addCriterion("REFUND_RECORD >=", value, "refundRecord");
            return (Criteria) this;
        }

        public Criteria andRefundRecordLessThan(String value) {
            addCriterion("REFUND_RECORD <", value, "refundRecord");
            return (Criteria) this;
        }

        public Criteria andRefundRecordLessThanOrEqualTo(String value) {
            addCriterion("REFUND_RECORD <=", value, "refundRecord");
            return (Criteria) this;
        }

        public Criteria andRefundRecordLike(String value) {
            addCriterion("REFUND_RECORD like", value, "refundRecord");
            return (Criteria) this;
        }

        public Criteria andRefundRecordNotLike(String value) {
            addCriterion("REFUND_RECORD not like", value, "refundRecord");
            return (Criteria) this;
        }

        public Criteria andRefundRecordIn(List<String> values) {
            addCriterion("REFUND_RECORD in", values, "refundRecord");
            return (Criteria) this;
        }

        public Criteria andRefundRecordNotIn(List<String> values) {
            addCriterion("REFUND_RECORD not in", values, "refundRecord");
            return (Criteria) this;
        }

        public Criteria andRefundRecordBetween(String value1, String value2) {
            addCriterion("REFUND_RECORD between", value1, value2, "refundRecord");
            return (Criteria) this;
        }

        public Criteria andRefundRecordNotBetween(String value1, String value2) {
            addCriterion("REFUND_RECORD not between", value1, value2, "refundRecord");
            return (Criteria) this;
        }

        public Criteria andRefundAuditRecordIsNull() {
            addCriterion("REFUND_AUDIT_RECORD is null");
            return (Criteria) this;
        }

        public Criteria andRefundAuditRecordIsNotNull() {
            addCriterion("REFUND_AUDIT_RECORD is not null");
            return (Criteria) this;
        }

        public Criteria andRefundAuditRecordEqualTo(String value) {
            addCriterion("REFUND_AUDIT_RECORD =", value, "refundAuditRecord");
            return (Criteria) this;
        }

        public Criteria andRefundAuditRecordNotEqualTo(String value) {
            addCriterion("REFUND_AUDIT_RECORD <>", value, "refundAuditRecord");
            return (Criteria) this;
        }

        public Criteria andRefundAuditRecordGreaterThan(String value) {
            addCriterion("REFUND_AUDIT_RECORD >", value, "refundAuditRecord");
            return (Criteria) this;
        }

        public Criteria andRefundAuditRecordGreaterThanOrEqualTo(String value) {
            addCriterion("REFUND_AUDIT_RECORD >=", value, "refundAuditRecord");
            return (Criteria) this;
        }

        public Criteria andRefundAuditRecordLessThan(String value) {
            addCriterion("REFUND_AUDIT_RECORD <", value, "refundAuditRecord");
            return (Criteria) this;
        }

        public Criteria andRefundAuditRecordLessThanOrEqualTo(String value) {
            addCriterion("REFUND_AUDIT_RECORD <=", value, "refundAuditRecord");
            return (Criteria) this;
        }

        public Criteria andRefundAuditRecordLike(String value) {
            addCriterion("REFUND_AUDIT_RECORD like", value, "refundAuditRecord");
            return (Criteria) this;
        }

        public Criteria andRefundAuditRecordNotLike(String value) {
            addCriterion("REFUND_AUDIT_RECORD not like", value, "refundAuditRecord");
            return (Criteria) this;
        }

        public Criteria andRefundAuditRecordIn(List<String> values) {
            addCriterion("REFUND_AUDIT_RECORD in", values, "refundAuditRecord");
            return (Criteria) this;
        }

        public Criteria andRefundAuditRecordNotIn(List<String> values) {
            addCriterion("REFUND_AUDIT_RECORD not in", values, "refundAuditRecord");
            return (Criteria) this;
        }

        public Criteria andRefundAuditRecordBetween(String value1, String value2) {
            addCriterion("REFUND_AUDIT_RECORD between", value1, value2, "refundAuditRecord");
            return (Criteria) this;
        }

        public Criteria andRefundAuditRecordNotBetween(String value1, String value2) {
            addCriterion("REFUND_AUDIT_RECORD not between", value1, value2, "refundAuditRecord");
            return (Criteria) this;
        }

        public Criteria andBalanceRecordIsNull() {
            addCriterion("BALANCE_RECORD is null");
            return (Criteria) this;
        }

        public Criteria andBalanceRecordIsNotNull() {
            addCriterion("BALANCE_RECORD is not null");
            return (Criteria) this;
        }

        public Criteria andBalanceRecordEqualTo(String value) {
            addCriterion("BALANCE_RECORD =", value, "balanceRecord");
            return (Criteria) this;
        }

        public Criteria andBalanceRecordNotEqualTo(String value) {
            addCriterion("BALANCE_RECORD <>", value, "balanceRecord");
            return (Criteria) this;
        }

        public Criteria andBalanceRecordGreaterThan(String value) {
            addCriterion("BALANCE_RECORD >", value, "balanceRecord");
            return (Criteria) this;
        }

        public Criteria andBalanceRecordGreaterThanOrEqualTo(String value) {
            addCriterion("BALANCE_RECORD >=", value, "balanceRecord");
            return (Criteria) this;
        }

        public Criteria andBalanceRecordLessThan(String value) {
            addCriterion("BALANCE_RECORD <", value, "balanceRecord");
            return (Criteria) this;
        }

        public Criteria andBalanceRecordLessThanOrEqualTo(String value) {
            addCriterion("BALANCE_RECORD <=", value, "balanceRecord");
            return (Criteria) this;
        }

        public Criteria andBalanceRecordLike(String value) {
            addCriterion("BALANCE_RECORD like", value, "balanceRecord");
            return (Criteria) this;
        }

        public Criteria andBalanceRecordNotLike(String value) {
            addCriterion("BALANCE_RECORD not like", value, "balanceRecord");
            return (Criteria) this;
        }

        public Criteria andBalanceRecordIn(List<String> values) {
            addCriterion("BALANCE_RECORD in", values, "balanceRecord");
            return (Criteria) this;
        }

        public Criteria andBalanceRecordNotIn(List<String> values) {
            addCriterion("BALANCE_RECORD not in", values, "balanceRecord");
            return (Criteria) this;
        }

        public Criteria andBalanceRecordBetween(String value1, String value2) {
            addCriterion("BALANCE_RECORD between", value1, value2, "balanceRecord");
            return (Criteria) this;
        }

        public Criteria andBalanceRecordNotBetween(String value1, String value2) {
            addCriterion("BALANCE_RECORD not between", value1, value2, "balanceRecord");
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

        public Criteria andCreatedNameIsNull() {
            addCriterion("CREATED_NAME is null");
            return (Criteria) this;
        }

        public Criteria andCreatedNameIsNotNull() {
            addCriterion("CREATED_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andCreatedNameEqualTo(String value) {
            addCriterion("CREATED_NAME =", value, "createdName");
            return (Criteria) this;
        }

        public Criteria andCreatedNameNotEqualTo(String value) {
            addCriterion("CREATED_NAME <>", value, "createdName");
            return (Criteria) this;
        }

        public Criteria andCreatedNameGreaterThan(String value) {
            addCriterion("CREATED_NAME >", value, "createdName");
            return (Criteria) this;
        }

        public Criteria andCreatedNameGreaterThanOrEqualTo(String value) {
            addCriterion("CREATED_NAME >=", value, "createdName");
            return (Criteria) this;
        }

        public Criteria andCreatedNameLessThan(String value) {
            addCriterion("CREATED_NAME <", value, "createdName");
            return (Criteria) this;
        }

        public Criteria andCreatedNameLessThanOrEqualTo(String value) {
            addCriterion("CREATED_NAME <=", value, "createdName");
            return (Criteria) this;
        }

        public Criteria andCreatedNameLike(String value) {
            addCriterion("CREATED_NAME like", value, "createdName");
            return (Criteria) this;
        }

        public Criteria andCreatedNameNotLike(String value) {
            addCriterion("CREATED_NAME not like", value, "createdName");
            return (Criteria) this;
        }

        public Criteria andCreatedNameIn(List<String> values) {
            addCriterion("CREATED_NAME in", values, "createdName");
            return (Criteria) this;
        }

        public Criteria andCreatedNameNotIn(List<String> values) {
            addCriterion("CREATED_NAME not in", values, "createdName");
            return (Criteria) this;
        }

        public Criteria andCreatedNameBetween(String value1, String value2) {
            addCriterion("CREATED_NAME between", value1, value2, "createdName");
            return (Criteria) this;
        }

        public Criteria andCreatedNameNotBetween(String value1, String value2) {
            addCriterion("CREATED_NAME not between", value1, value2, "createdName");
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

        public Criteria andUpdatedNameIsNull() {
            addCriterion("UPDATED_NAME is null");
            return (Criteria) this;
        }

        public Criteria andUpdatedNameIsNotNull() {
            addCriterion("UPDATED_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andUpdatedNameEqualTo(String value) {
            addCriterion("UPDATED_NAME =", value, "updatedName");
            return (Criteria) this;
        }

        public Criteria andUpdatedNameNotEqualTo(String value) {
            addCriterion("UPDATED_NAME <>", value, "updatedName");
            return (Criteria) this;
        }

        public Criteria andUpdatedNameGreaterThan(String value) {
            addCriterion("UPDATED_NAME >", value, "updatedName");
            return (Criteria) this;
        }

        public Criteria andUpdatedNameGreaterThanOrEqualTo(String value) {
            addCriterion("UPDATED_NAME >=", value, "updatedName");
            return (Criteria) this;
        }

        public Criteria andUpdatedNameLessThan(String value) {
            addCriterion("UPDATED_NAME <", value, "updatedName");
            return (Criteria) this;
        }

        public Criteria andUpdatedNameLessThanOrEqualTo(String value) {
            addCriterion("UPDATED_NAME <=", value, "updatedName");
            return (Criteria) this;
        }

        public Criteria andUpdatedNameLike(String value) {
            addCriterion("UPDATED_NAME like", value, "updatedName");
            return (Criteria) this;
        }

        public Criteria andUpdatedNameNotLike(String value) {
            addCriterion("UPDATED_NAME not like", value, "updatedName");
            return (Criteria) this;
        }

        public Criteria andUpdatedNameIn(List<String> values) {
            addCriterion("UPDATED_NAME in", values, "updatedName");
            return (Criteria) this;
        }

        public Criteria andUpdatedNameNotIn(List<String> values) {
            addCriterion("UPDATED_NAME not in", values, "updatedName");
            return (Criteria) this;
        }

        public Criteria andUpdatedNameBetween(String value1, String value2) {
            addCriterion("UPDATED_NAME between", value1, value2, "updatedName");
            return (Criteria) this;
        }

        public Criteria andUpdatedNameNotBetween(String value1, String value2) {
            addCriterion("UPDATED_NAME not between", value1, value2, "updatedName");
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