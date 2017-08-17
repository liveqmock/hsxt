package com.gy.hsi.fs.server.common.framework.mybatis.mapvo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TFsFileOperationLogExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TFsFileOperationLogExample() {
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

        public Criteria andIdEqualTo(Long value) {
            addCriterion("ID =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("ID <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("ID >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("ID >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("ID <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("ID <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("ID in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("ID not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("ID between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("ID not between", value1, value2, "id");
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

        public Criteria andFunctionIdIsNull() {
            addCriterion("FUNCTION_ID is null");
            return (Criteria) this;
        }

        public Criteria andFunctionIdIsNotNull() {
            addCriterion("FUNCTION_ID is not null");
            return (Criteria) this;
        }

        public Criteria andFunctionIdEqualTo(String value) {
            addCriterion("FUNCTION_ID =", value, "functionId");
            return (Criteria) this;
        }

        public Criteria andFunctionIdNotEqualTo(String value) {
            addCriterion("FUNCTION_ID <>", value, "functionId");
            return (Criteria) this;
        }

        public Criteria andFunctionIdGreaterThan(String value) {
            addCriterion("FUNCTION_ID >", value, "functionId");
            return (Criteria) this;
        }

        public Criteria andFunctionIdGreaterThanOrEqualTo(String value) {
            addCriterion("FUNCTION_ID >=", value, "functionId");
            return (Criteria) this;
        }

        public Criteria andFunctionIdLessThan(String value) {
            addCriterion("FUNCTION_ID <", value, "functionId");
            return (Criteria) this;
        }

        public Criteria andFunctionIdLessThanOrEqualTo(String value) {
            addCriterion("FUNCTION_ID <=", value, "functionId");
            return (Criteria) this;
        }

        public Criteria andFunctionIdLike(String value) {
            addCriterion("FUNCTION_ID like", value, "functionId");
            return (Criteria) this;
        }

        public Criteria andFunctionIdNotLike(String value) {
            addCriterion("FUNCTION_ID not like", value, "functionId");
            return (Criteria) this;
        }

        public Criteria andFunctionIdIn(List<String> values) {
            addCriterion("FUNCTION_ID in", values, "functionId");
            return (Criteria) this;
        }

        public Criteria andFunctionIdNotIn(List<String> values) {
            addCriterion("FUNCTION_ID not in", values, "functionId");
            return (Criteria) this;
        }

        public Criteria andFunctionIdBetween(String value1, String value2) {
            addCriterion("FUNCTION_ID between", value1, value2, "functionId");
            return (Criteria) this;
        }

        public Criteria andFunctionIdNotBetween(String value1, String value2) {
            addCriterion("FUNCTION_ID not between", value1, value2, "functionId");
            return (Criteria) this;
        }

        public Criteria andFunctionParamIsNull() {
            addCriterion("FUNCTION_PARAM is null");
            return (Criteria) this;
        }

        public Criteria andFunctionParamIsNotNull() {
            addCriterion("FUNCTION_PARAM is not null");
            return (Criteria) this;
        }

        public Criteria andFunctionParamEqualTo(String value) {
            addCriterion("FUNCTION_PARAM =", value, "functionParam");
            return (Criteria) this;
        }

        public Criteria andFunctionParamNotEqualTo(String value) {
            addCriterion("FUNCTION_PARAM <>", value, "functionParam");
            return (Criteria) this;
        }

        public Criteria andFunctionParamGreaterThan(String value) {
            addCriterion("FUNCTION_PARAM >", value, "functionParam");
            return (Criteria) this;
        }

        public Criteria andFunctionParamGreaterThanOrEqualTo(String value) {
            addCriterion("FUNCTION_PARAM >=", value, "functionParam");
            return (Criteria) this;
        }

        public Criteria andFunctionParamLessThan(String value) {
            addCriterion("FUNCTION_PARAM <", value, "functionParam");
            return (Criteria) this;
        }

        public Criteria andFunctionParamLessThanOrEqualTo(String value) {
            addCriterion("FUNCTION_PARAM <=", value, "functionParam");
            return (Criteria) this;
        }

        public Criteria andFunctionParamLike(String value) {
            addCriterion("FUNCTION_PARAM like", value, "functionParam");
            return (Criteria) this;
        }

        public Criteria andFunctionParamNotLike(String value) {
            addCriterion("FUNCTION_PARAM not like", value, "functionParam");
            return (Criteria) this;
        }

        public Criteria andFunctionParamIn(List<String> values) {
            addCriterion("FUNCTION_PARAM in", values, "functionParam");
            return (Criteria) this;
        }

        public Criteria andFunctionParamNotIn(List<String> values) {
            addCriterion("FUNCTION_PARAM not in", values, "functionParam");
            return (Criteria) this;
        }

        public Criteria andFunctionParamBetween(String value1, String value2) {
            addCriterion("FUNCTION_PARAM between", value1, value2, "functionParam");
            return (Criteria) this;
        }

        public Criteria andFunctionParamNotBetween(String value1, String value2) {
            addCriterion("FUNCTION_PARAM not between", value1, value2, "functionParam");
            return (Criteria) this;
        }

        public Criteria andOptStatusIsNull() {
            addCriterion("OPT_STATUS is null");
            return (Criteria) this;
        }

        public Criteria andOptStatusIsNotNull() {
            addCriterion("OPT_STATUS is not null");
            return (Criteria) this;
        }

        public Criteria andOptStatusEqualTo(Integer value) {
            addCriterion("OPT_STATUS =", value, "optStatus");
            return (Criteria) this;
        }

        public Criteria andOptStatusNotEqualTo(Integer value) {
            addCriterion("OPT_STATUS <>", value, "optStatus");
            return (Criteria) this;
        }

        public Criteria andOptStatusGreaterThan(Integer value) {
            addCriterion("OPT_STATUS >", value, "optStatus");
            return (Criteria) this;
        }

        public Criteria andOptStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("OPT_STATUS >=", value, "optStatus");
            return (Criteria) this;
        }

        public Criteria andOptStatusLessThan(Integer value) {
            addCriterion("OPT_STATUS <", value, "optStatus");
            return (Criteria) this;
        }

        public Criteria andOptStatusLessThanOrEqualTo(Integer value) {
            addCriterion("OPT_STATUS <=", value, "optStatus");
            return (Criteria) this;
        }

        public Criteria andOptStatusIn(List<Integer> values) {
            addCriterion("OPT_STATUS in", values, "optStatus");
            return (Criteria) this;
        }

        public Criteria andOptStatusNotIn(List<Integer> values) {
            addCriterion("OPT_STATUS not in", values, "optStatus");
            return (Criteria) this;
        }

        public Criteria andOptStatusBetween(Integer value1, Integer value2) {
            addCriterion("OPT_STATUS between", value1, value2, "optStatus");
            return (Criteria) this;
        }

        public Criteria andOptStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("OPT_STATUS not between", value1, value2, "optStatus");
            return (Criteria) this;
        }

        public Criteria andOptErrDescIsNull() {
            addCriterion("OPT_ERR_DESC is null");
            return (Criteria) this;
        }

        public Criteria andOptErrDescIsNotNull() {
            addCriterion("OPT_ERR_DESC is not null");
            return (Criteria) this;
        }

        public Criteria andOptErrDescEqualTo(String value) {
            addCriterion("OPT_ERR_DESC =", value, "optErrDesc");
            return (Criteria) this;
        }

        public Criteria andOptErrDescNotEqualTo(String value) {
            addCriterion("OPT_ERR_DESC <>", value, "optErrDesc");
            return (Criteria) this;
        }

        public Criteria andOptErrDescGreaterThan(String value) {
            addCriterion("OPT_ERR_DESC >", value, "optErrDesc");
            return (Criteria) this;
        }

        public Criteria andOptErrDescGreaterThanOrEqualTo(String value) {
            addCriterion("OPT_ERR_DESC >=", value, "optErrDesc");
            return (Criteria) this;
        }

        public Criteria andOptErrDescLessThan(String value) {
            addCriterion("OPT_ERR_DESC <", value, "optErrDesc");
            return (Criteria) this;
        }

        public Criteria andOptErrDescLessThanOrEqualTo(String value) {
            addCriterion("OPT_ERR_DESC <=", value, "optErrDesc");
            return (Criteria) this;
        }

        public Criteria andOptErrDescLike(String value) {
            addCriterion("OPT_ERR_DESC like", value, "optErrDesc");
            return (Criteria) this;
        }

        public Criteria andOptErrDescNotLike(String value) {
            addCriterion("OPT_ERR_DESC not like", value, "optErrDesc");
            return (Criteria) this;
        }

        public Criteria andOptErrDescIn(List<String> values) {
            addCriterion("OPT_ERR_DESC in", values, "optErrDesc");
            return (Criteria) this;
        }

        public Criteria andOptErrDescNotIn(List<String> values) {
            addCriterion("OPT_ERR_DESC not in", values, "optErrDesc");
            return (Criteria) this;
        }

        public Criteria andOptErrDescBetween(String value1, String value2) {
            addCriterion("OPT_ERR_DESC between", value1, value2, "optErrDesc");
            return (Criteria) this;
        }

        public Criteria andOptErrDescNotBetween(String value1, String value2) {
            addCriterion("OPT_ERR_DESC not between", value1, value2, "optErrDesc");
            return (Criteria) this;
        }

        public Criteria andOptIpAddrIsNull() {
            addCriterion("OPT_IP_ADDR is null");
            return (Criteria) this;
        }

        public Criteria andOptIpAddrIsNotNull() {
            addCriterion("OPT_IP_ADDR is not null");
            return (Criteria) this;
        }

        public Criteria andOptIpAddrEqualTo(String value) {
            addCriterion("OPT_IP_ADDR =", value, "optIpAddr");
            return (Criteria) this;
        }

        public Criteria andOptIpAddrNotEqualTo(String value) {
            addCriterion("OPT_IP_ADDR <>", value, "optIpAddr");
            return (Criteria) this;
        }

        public Criteria andOptIpAddrGreaterThan(String value) {
            addCriterion("OPT_IP_ADDR >", value, "optIpAddr");
            return (Criteria) this;
        }

        public Criteria andOptIpAddrGreaterThanOrEqualTo(String value) {
            addCriterion("OPT_IP_ADDR >=", value, "optIpAddr");
            return (Criteria) this;
        }

        public Criteria andOptIpAddrLessThan(String value) {
            addCriterion("OPT_IP_ADDR <", value, "optIpAddr");
            return (Criteria) this;
        }

        public Criteria andOptIpAddrLessThanOrEqualTo(String value) {
            addCriterion("OPT_IP_ADDR <=", value, "optIpAddr");
            return (Criteria) this;
        }

        public Criteria andOptIpAddrLike(String value) {
            addCriterion("OPT_IP_ADDR like", value, "optIpAddr");
            return (Criteria) this;
        }

        public Criteria andOptIpAddrNotLike(String value) {
            addCriterion("OPT_IP_ADDR not like", value, "optIpAddr");
            return (Criteria) this;
        }

        public Criteria andOptIpAddrIn(List<String> values) {
            addCriterion("OPT_IP_ADDR in", values, "optIpAddr");
            return (Criteria) this;
        }

        public Criteria andOptIpAddrNotIn(List<String> values) {
            addCriterion("OPT_IP_ADDR not in", values, "optIpAddr");
            return (Criteria) this;
        }

        public Criteria andOptIpAddrBetween(String value1, String value2) {
            addCriterion("OPT_IP_ADDR between", value1, value2, "optIpAddr");
            return (Criteria) this;
        }

        public Criteria andOptIpAddrNotBetween(String value1, String value2) {
            addCriterion("OPT_IP_ADDR not between", value1, value2, "optIpAddr");
            return (Criteria) this;
        }

        public Criteria andOptUserIdIsNull() {
            addCriterion("OPT_USER_ID is null");
            return (Criteria) this;
        }

        public Criteria andOptUserIdIsNotNull() {
            addCriterion("OPT_USER_ID is not null");
            return (Criteria) this;
        }

        public Criteria andOptUserIdEqualTo(String value) {
            addCriterion("OPT_USER_ID =", value, "optUserId");
            return (Criteria) this;
        }

        public Criteria andOptUserIdNotEqualTo(String value) {
            addCriterion("OPT_USER_ID <>", value, "optUserId");
            return (Criteria) this;
        }

        public Criteria andOptUserIdGreaterThan(String value) {
            addCriterion("OPT_USER_ID >", value, "optUserId");
            return (Criteria) this;
        }

        public Criteria andOptUserIdGreaterThanOrEqualTo(String value) {
            addCriterion("OPT_USER_ID >=", value, "optUserId");
            return (Criteria) this;
        }

        public Criteria andOptUserIdLessThan(String value) {
            addCriterion("OPT_USER_ID <", value, "optUserId");
            return (Criteria) this;
        }

        public Criteria andOptUserIdLessThanOrEqualTo(String value) {
            addCriterion("OPT_USER_ID <=", value, "optUserId");
            return (Criteria) this;
        }

        public Criteria andOptUserIdLike(String value) {
            addCriterion("OPT_USER_ID like", value, "optUserId");
            return (Criteria) this;
        }

        public Criteria andOptUserIdNotLike(String value) {
            addCriterion("OPT_USER_ID not like", value, "optUserId");
            return (Criteria) this;
        }

        public Criteria andOptUserIdIn(List<String> values) {
            addCriterion("OPT_USER_ID in", values, "optUserId");
            return (Criteria) this;
        }

        public Criteria andOptUserIdNotIn(List<String> values) {
            addCriterion("OPT_USER_ID not in", values, "optUserId");
            return (Criteria) this;
        }

        public Criteria andOptUserIdBetween(String value1, String value2) {
            addCriterion("OPT_USER_ID between", value1, value2, "optUserId");
            return (Criteria) this;
        }

        public Criteria andOptUserIdNotBetween(String value1, String value2) {
            addCriterion("OPT_USER_ID not between", value1, value2, "optUserId");
            return (Criteria) this;
        }

        public Criteria andSecureTokenIsNull() {
            addCriterion("SECURE_TOKEN is null");
            return (Criteria) this;
        }

        public Criteria andSecureTokenIsNotNull() {
            addCriterion("SECURE_TOKEN is not null");
            return (Criteria) this;
        }

        public Criteria andSecureTokenEqualTo(String value) {
            addCriterion("SECURE_TOKEN =", value, "secureToken");
            return (Criteria) this;
        }

        public Criteria andSecureTokenNotEqualTo(String value) {
            addCriterion("SECURE_TOKEN <>", value, "secureToken");
            return (Criteria) this;
        }

        public Criteria andSecureTokenGreaterThan(String value) {
            addCriterion("SECURE_TOKEN >", value, "secureToken");
            return (Criteria) this;
        }

        public Criteria andSecureTokenGreaterThanOrEqualTo(String value) {
            addCriterion("SECURE_TOKEN >=", value, "secureToken");
            return (Criteria) this;
        }

        public Criteria andSecureTokenLessThan(String value) {
            addCriterion("SECURE_TOKEN <", value, "secureToken");
            return (Criteria) this;
        }

        public Criteria andSecureTokenLessThanOrEqualTo(String value) {
            addCriterion("SECURE_TOKEN <=", value, "secureToken");
            return (Criteria) this;
        }

        public Criteria andSecureTokenLike(String value) {
            addCriterion("SECURE_TOKEN like", value, "secureToken");
            return (Criteria) this;
        }

        public Criteria andSecureTokenNotLike(String value) {
            addCriterion("SECURE_TOKEN not like", value, "secureToken");
            return (Criteria) this;
        }

        public Criteria andSecureTokenIn(List<String> values) {
            addCriterion("SECURE_TOKEN in", values, "secureToken");
            return (Criteria) this;
        }

        public Criteria andSecureTokenNotIn(List<String> values) {
            addCriterion("SECURE_TOKEN not in", values, "secureToken");
            return (Criteria) this;
        }

        public Criteria andSecureTokenBetween(String value1, String value2) {
            addCriterion("SECURE_TOKEN between", value1, value2, "secureToken");
            return (Criteria) this;
        }

        public Criteria andSecureTokenNotBetween(String value1, String value2) {
            addCriterion("SECURE_TOKEN not between", value1, value2, "secureToken");
            return (Criteria) this;
        }

        public Criteria andChannelIsNull() {
            addCriterion("CHANNEL is null");
            return (Criteria) this;
        }

        public Criteria andChannelIsNotNull() {
            addCriterion("CHANNEL is not null");
            return (Criteria) this;
        }

        public Criteria andChannelEqualTo(String value) {
            addCriterion("CHANNEL =", value, "channel");
            return (Criteria) this;
        }

        public Criteria andChannelNotEqualTo(String value) {
            addCriterion("CHANNEL <>", value, "channel");
            return (Criteria) this;
        }

        public Criteria andChannelGreaterThan(String value) {
            addCriterion("CHANNEL >", value, "channel");
            return (Criteria) this;
        }

        public Criteria andChannelGreaterThanOrEqualTo(String value) {
            addCriterion("CHANNEL >=", value, "channel");
            return (Criteria) this;
        }

        public Criteria andChannelLessThan(String value) {
            addCriterion("CHANNEL <", value, "channel");
            return (Criteria) this;
        }

        public Criteria andChannelLessThanOrEqualTo(String value) {
            addCriterion("CHANNEL <=", value, "channel");
            return (Criteria) this;
        }

        public Criteria andChannelLike(String value) {
            addCriterion("CHANNEL like", value, "channel");
            return (Criteria) this;
        }

        public Criteria andChannelNotLike(String value) {
            addCriterion("CHANNEL not like", value, "channel");
            return (Criteria) this;
        }

        public Criteria andChannelIn(List<String> values) {
            addCriterion("CHANNEL in", values, "channel");
            return (Criteria) this;
        }

        public Criteria andChannelNotIn(List<String> values) {
            addCriterion("CHANNEL not in", values, "channel");
            return (Criteria) this;
        }

        public Criteria andChannelBetween(String value1, String value2) {
            addCriterion("CHANNEL between", value1, value2, "channel");
            return (Criteria) this;
        }

        public Criteria andChannelNotBetween(String value1, String value2) {
            addCriterion("CHANNEL not between", value1, value2, "channel");
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