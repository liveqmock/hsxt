package com.gy.hsxt.uf.mapper.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TUfPacketLogExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TUfPacketLogExample() {
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

        public Criteria andLogIdIsNull() {
            addCriterion("LOG_ID is null");
            return (Criteria) this;
        }

        public Criteria andLogIdIsNotNull() {
            addCriterion("LOG_ID is not null");
            return (Criteria) this;
        }

        public Criteria andLogIdEqualTo(Long value) {
            addCriterion("LOG_ID =", value, "logId");
            return (Criteria) this;
        }

        public Criteria andLogIdNotEqualTo(Long value) {
            addCriterion("LOG_ID <>", value, "logId");
            return (Criteria) this;
        }

        public Criteria andLogIdGreaterThan(Long value) {
            addCriterion("LOG_ID >", value, "logId");
            return (Criteria) this;
        }

        public Criteria andLogIdGreaterThanOrEqualTo(Long value) {
            addCriterion("LOG_ID >=", value, "logId");
            return (Criteria) this;
        }

        public Criteria andLogIdLessThan(Long value) {
            addCriterion("LOG_ID <", value, "logId");
            return (Criteria) this;
        }

        public Criteria andLogIdLessThanOrEqualTo(Long value) {
            addCriterion("LOG_ID <=", value, "logId");
            return (Criteria) this;
        }

        public Criteria andLogIdIn(List<Long> values) {
            addCriterion("LOG_ID in", values, "logId");
            return (Criteria) this;
        }

        public Criteria andLogIdNotIn(List<Long> values) {
            addCriterion("LOG_ID not in", values, "logId");
            return (Criteria) this;
        }

        public Criteria andLogIdBetween(Long value1, Long value2) {
            addCriterion("LOG_ID between", value1, value2, "logId");
            return (Criteria) this;
        }

        public Criteria andLogIdNotBetween(Long value1, Long value2) {
            addCriterion("LOG_ID not between", value1, value2, "logId");
            return (Criteria) this;
        }

        public Criteria andPacketIdIsNull() {
            addCriterion("PACKET_ID is null");
            return (Criteria) this;
        }

        public Criteria andPacketIdIsNotNull() {
            addCriterion("PACKET_ID is not null");
            return (Criteria) this;
        }

        public Criteria andPacketIdEqualTo(String value) {
            addCriterion("PACKET_ID =", value, "packetId");
            return (Criteria) this;
        }

        public Criteria andPacketIdNotEqualTo(String value) {
            addCriterion("PACKET_ID <>", value, "packetId");
            return (Criteria) this;
        }

        public Criteria andPacketIdGreaterThan(String value) {
            addCriterion("PACKET_ID >", value, "packetId");
            return (Criteria) this;
        }

        public Criteria andPacketIdGreaterThanOrEqualTo(String value) {
            addCriterion("PACKET_ID >=", value, "packetId");
            return (Criteria) this;
        }

        public Criteria andPacketIdLessThan(String value) {
            addCriterion("PACKET_ID <", value, "packetId");
            return (Criteria) this;
        }

        public Criteria andPacketIdLessThanOrEqualTo(String value) {
            addCriterion("PACKET_ID <=", value, "packetId");
            return (Criteria) this;
        }

        public Criteria andPacketIdLike(String value) {
            addCriterion("PACKET_ID like", value, "packetId");
            return (Criteria) this;
        }

        public Criteria andPacketIdNotLike(String value) {
            addCriterion("PACKET_ID not like", value, "packetId");
            return (Criteria) this;
        }

        public Criteria andPacketIdIn(List<String> values) {
            addCriterion("PACKET_ID in", values, "packetId");
            return (Criteria) this;
        }

        public Criteria andPacketIdNotIn(List<String> values) {
            addCriterion("PACKET_ID not in", values, "packetId");
            return (Criteria) this;
        }

        public Criteria andPacketIdBetween(String value1, String value2) {
            addCriterion("PACKET_ID between", value1, value2, "packetId");
            return (Criteria) this;
        }

        public Criteria andPacketIdNotBetween(String value1, String value2) {
            addCriterion("PACKET_ID not between", value1, value2, "packetId");
            return (Criteria) this;
        }

        public Criteria andSrcPlatformIdIsNull() {
            addCriterion("SRC_PLATFORM_ID is null");
            return (Criteria) this;
        }

        public Criteria andSrcPlatformIdIsNotNull() {
            addCriterion("SRC_PLATFORM_ID is not null");
            return (Criteria) this;
        }

        public Criteria andSrcPlatformIdEqualTo(String value) {
            addCriterion("SRC_PLATFORM_ID =", value, "srcPlatformId");
            return (Criteria) this;
        }

        public Criteria andSrcPlatformIdNotEqualTo(String value) {
            addCriterion("SRC_PLATFORM_ID <>", value, "srcPlatformId");
            return (Criteria) this;
        }

        public Criteria andSrcPlatformIdGreaterThan(String value) {
            addCriterion("SRC_PLATFORM_ID >", value, "srcPlatformId");
            return (Criteria) this;
        }

        public Criteria andSrcPlatformIdGreaterThanOrEqualTo(String value) {
            addCriterion("SRC_PLATFORM_ID >=", value, "srcPlatformId");
            return (Criteria) this;
        }

        public Criteria andSrcPlatformIdLessThan(String value) {
            addCriterion("SRC_PLATFORM_ID <", value, "srcPlatformId");
            return (Criteria) this;
        }

        public Criteria andSrcPlatformIdLessThanOrEqualTo(String value) {
            addCriterion("SRC_PLATFORM_ID <=", value, "srcPlatformId");
            return (Criteria) this;
        }

        public Criteria andSrcPlatformIdLike(String value) {
            addCriterion("SRC_PLATFORM_ID like", value, "srcPlatformId");
            return (Criteria) this;
        }

        public Criteria andSrcPlatformIdNotLike(String value) {
            addCriterion("SRC_PLATFORM_ID not like", value, "srcPlatformId");
            return (Criteria) this;
        }

        public Criteria andSrcPlatformIdIn(List<String> values) {
            addCriterion("SRC_PLATFORM_ID in", values, "srcPlatformId");
            return (Criteria) this;
        }

        public Criteria andSrcPlatformIdNotIn(List<String> values) {
            addCriterion("SRC_PLATFORM_ID not in", values, "srcPlatformId");
            return (Criteria) this;
        }

        public Criteria andSrcPlatformIdBetween(String value1, String value2) {
            addCriterion("SRC_PLATFORM_ID between", value1, value2, "srcPlatformId");
            return (Criteria) this;
        }

        public Criteria andSrcPlatformIdNotBetween(String value1, String value2) {
            addCriterion("SRC_PLATFORM_ID not between", value1, value2, "srcPlatformId");
            return (Criteria) this;
        }

        public Criteria andSrcSubsysIdIsNull() {
            addCriterion("SRC_SUBSYS_ID is null");
            return (Criteria) this;
        }

        public Criteria andSrcSubsysIdIsNotNull() {
            addCriterion("SRC_SUBSYS_ID is not null");
            return (Criteria) this;
        }

        public Criteria andSrcSubsysIdEqualTo(String value) {
            addCriterion("SRC_SUBSYS_ID =", value, "srcSubsysId");
            return (Criteria) this;
        }

        public Criteria andSrcSubsysIdNotEqualTo(String value) {
            addCriterion("SRC_SUBSYS_ID <>", value, "srcSubsysId");
            return (Criteria) this;
        }

        public Criteria andSrcSubsysIdGreaterThan(String value) {
            addCriterion("SRC_SUBSYS_ID >", value, "srcSubsysId");
            return (Criteria) this;
        }

        public Criteria andSrcSubsysIdGreaterThanOrEqualTo(String value) {
            addCriterion("SRC_SUBSYS_ID >=", value, "srcSubsysId");
            return (Criteria) this;
        }

        public Criteria andSrcSubsysIdLessThan(String value) {
            addCriterion("SRC_SUBSYS_ID <", value, "srcSubsysId");
            return (Criteria) this;
        }

        public Criteria andSrcSubsysIdLessThanOrEqualTo(String value) {
            addCriterion("SRC_SUBSYS_ID <=", value, "srcSubsysId");
            return (Criteria) this;
        }

        public Criteria andSrcSubsysIdLike(String value) {
            addCriterion("SRC_SUBSYS_ID like", value, "srcSubsysId");
            return (Criteria) this;
        }

        public Criteria andSrcSubsysIdNotLike(String value) {
            addCriterion("SRC_SUBSYS_ID not like", value, "srcSubsysId");
            return (Criteria) this;
        }

        public Criteria andSrcSubsysIdIn(List<String> values) {
            addCriterion("SRC_SUBSYS_ID in", values, "srcSubsysId");
            return (Criteria) this;
        }

        public Criteria andSrcSubsysIdNotIn(List<String> values) {
            addCriterion("SRC_SUBSYS_ID not in", values, "srcSubsysId");
            return (Criteria) this;
        }

        public Criteria andSrcSubsysIdBetween(String value1, String value2) {
            addCriterion("SRC_SUBSYS_ID between", value1, value2, "srcSubsysId");
            return (Criteria) this;
        }

        public Criteria andSrcSubsysIdNotBetween(String value1, String value2) {
            addCriterion("SRC_SUBSYS_ID not between", value1, value2, "srcSubsysId");
            return (Criteria) this;
        }

        public Criteria andDestPlatformIdIsNull() {
            addCriterion("DEST_PLATFORM_ID is null");
            return (Criteria) this;
        }

        public Criteria andDestPlatformIdIsNotNull() {
            addCriterion("DEST_PLATFORM_ID is not null");
            return (Criteria) this;
        }

        public Criteria andDestPlatformIdEqualTo(String value) {
            addCriterion("DEST_PLATFORM_ID =", value, "destPlatformId");
            return (Criteria) this;
        }

        public Criteria andDestPlatformIdNotEqualTo(String value) {
            addCriterion("DEST_PLATFORM_ID <>", value, "destPlatformId");
            return (Criteria) this;
        }

        public Criteria andDestPlatformIdGreaterThan(String value) {
            addCriterion("DEST_PLATFORM_ID >", value, "destPlatformId");
            return (Criteria) this;
        }

        public Criteria andDestPlatformIdGreaterThanOrEqualTo(String value) {
            addCriterion("DEST_PLATFORM_ID >=", value, "destPlatformId");
            return (Criteria) this;
        }

        public Criteria andDestPlatformIdLessThan(String value) {
            addCriterion("DEST_PLATFORM_ID <", value, "destPlatformId");
            return (Criteria) this;
        }

        public Criteria andDestPlatformIdLessThanOrEqualTo(String value) {
            addCriterion("DEST_PLATFORM_ID <=", value, "destPlatformId");
            return (Criteria) this;
        }

        public Criteria andDestPlatformIdLike(String value) {
            addCriterion("DEST_PLATFORM_ID like", value, "destPlatformId");
            return (Criteria) this;
        }

        public Criteria andDestPlatformIdNotLike(String value) {
            addCriterion("DEST_PLATFORM_ID not like", value, "destPlatformId");
            return (Criteria) this;
        }

        public Criteria andDestPlatformIdIn(List<String> values) {
            addCriterion("DEST_PLATFORM_ID in", values, "destPlatformId");
            return (Criteria) this;
        }

        public Criteria andDestPlatformIdNotIn(List<String> values) {
            addCriterion("DEST_PLATFORM_ID not in", values, "destPlatformId");
            return (Criteria) this;
        }

        public Criteria andDestPlatformIdBetween(String value1, String value2) {
            addCriterion("DEST_PLATFORM_ID between", value1, value2, "destPlatformId");
            return (Criteria) this;
        }

        public Criteria andDestPlatformIdNotBetween(String value1, String value2) {
            addCriterion("DEST_PLATFORM_ID not between", value1, value2, "destPlatformId");
            return (Criteria) this;
        }

        public Criteria andDestSubsysIdIsNull() {
            addCriterion("DEST_SUBSYS_ID is null");
            return (Criteria) this;
        }

        public Criteria andDestSubsysIdIsNotNull() {
            addCriterion("DEST_SUBSYS_ID is not null");
            return (Criteria) this;
        }

        public Criteria andDestSubsysIdEqualTo(String value) {
            addCriterion("DEST_SUBSYS_ID =", value, "destSubsysId");
            return (Criteria) this;
        }

        public Criteria andDestSubsysIdNotEqualTo(String value) {
            addCriterion("DEST_SUBSYS_ID <>", value, "destSubsysId");
            return (Criteria) this;
        }

        public Criteria andDestSubsysIdGreaterThan(String value) {
            addCriterion("DEST_SUBSYS_ID >", value, "destSubsysId");
            return (Criteria) this;
        }

        public Criteria andDestSubsysIdGreaterThanOrEqualTo(String value) {
            addCriterion("DEST_SUBSYS_ID >=", value, "destSubsysId");
            return (Criteria) this;
        }

        public Criteria andDestSubsysIdLessThan(String value) {
            addCriterion("DEST_SUBSYS_ID <", value, "destSubsysId");
            return (Criteria) this;
        }

        public Criteria andDestSubsysIdLessThanOrEqualTo(String value) {
            addCriterion("DEST_SUBSYS_ID <=", value, "destSubsysId");
            return (Criteria) this;
        }

        public Criteria andDestSubsysIdLike(String value) {
            addCriterion("DEST_SUBSYS_ID like", value, "destSubsysId");
            return (Criteria) this;
        }

        public Criteria andDestSubsysIdNotLike(String value) {
            addCriterion("DEST_SUBSYS_ID not like", value, "destSubsysId");
            return (Criteria) this;
        }

        public Criteria andDestSubsysIdIn(List<String> values) {
            addCriterion("DEST_SUBSYS_ID in", values, "destSubsysId");
            return (Criteria) this;
        }

        public Criteria andDestSubsysIdNotIn(List<String> values) {
            addCriterion("DEST_SUBSYS_ID not in", values, "destSubsysId");
            return (Criteria) this;
        }

        public Criteria andDestSubsysIdBetween(String value1, String value2) {
            addCriterion("DEST_SUBSYS_ID between", value1, value2, "destSubsysId");
            return (Criteria) this;
        }

        public Criteria andDestSubsysIdNotBetween(String value1, String value2) {
            addCriterion("DEST_SUBSYS_ID not between", value1, value2, "destSubsysId");
            return (Criteria) this;
        }

        public Criteria andDestBizCodeIsNull() {
            addCriterion("DEST_BIZ_CODE is null");
            return (Criteria) this;
        }

        public Criteria andDestBizCodeIsNotNull() {
            addCriterion("DEST_BIZ_CODE is not null");
            return (Criteria) this;
        }

        public Criteria andDestBizCodeEqualTo(String value) {
            addCriterion("DEST_BIZ_CODE =", value, "destBizCode");
            return (Criteria) this;
        }

        public Criteria andDestBizCodeNotEqualTo(String value) {
            addCriterion("DEST_BIZ_CODE <>", value, "destBizCode");
            return (Criteria) this;
        }

        public Criteria andDestBizCodeGreaterThan(String value) {
            addCriterion("DEST_BIZ_CODE >", value, "destBizCode");
            return (Criteria) this;
        }

        public Criteria andDestBizCodeGreaterThanOrEqualTo(String value) {
            addCriterion("DEST_BIZ_CODE >=", value, "destBizCode");
            return (Criteria) this;
        }

        public Criteria andDestBizCodeLessThan(String value) {
            addCriterion("DEST_BIZ_CODE <", value, "destBizCode");
            return (Criteria) this;
        }

        public Criteria andDestBizCodeLessThanOrEqualTo(String value) {
            addCriterion("DEST_BIZ_CODE <=", value, "destBizCode");
            return (Criteria) this;
        }

        public Criteria andDestBizCodeLike(String value) {
            addCriterion("DEST_BIZ_CODE like", value, "destBizCode");
            return (Criteria) this;
        }

        public Criteria andDestBizCodeNotLike(String value) {
            addCriterion("DEST_BIZ_CODE not like", value, "destBizCode");
            return (Criteria) this;
        }

        public Criteria andDestBizCodeIn(List<String> values) {
            addCriterion("DEST_BIZ_CODE in", values, "destBizCode");
            return (Criteria) this;
        }

        public Criteria andDestBizCodeNotIn(List<String> values) {
            addCriterion("DEST_BIZ_CODE not in", values, "destBizCode");
            return (Criteria) this;
        }

        public Criteria andDestBizCodeBetween(String value1, String value2) {
            addCriterion("DEST_BIZ_CODE between", value1, value2, "destBizCode");
            return (Criteria) this;
        }

        public Criteria andDestBizCodeNotBetween(String value1, String value2) {
            addCriterion("DEST_BIZ_CODE not between", value1, value2, "destBizCode");
            return (Criteria) this;
        }

        public Criteria andPacketOptTypeIsNull() {
            addCriterion("PACKET_OPT_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andPacketOptTypeIsNotNull() {
            addCriterion("PACKET_OPT_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andPacketOptTypeEqualTo(Integer value) {
            addCriterion("PACKET_OPT_TYPE =", value, "packetOptType");
            return (Criteria) this;
        }

        public Criteria andPacketOptTypeNotEqualTo(Integer value) {
            addCriterion("PACKET_OPT_TYPE <>", value, "packetOptType");
            return (Criteria) this;
        }

        public Criteria andPacketOptTypeGreaterThan(Integer value) {
            addCriterion("PACKET_OPT_TYPE >", value, "packetOptType");
            return (Criteria) this;
        }

        public Criteria andPacketOptTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("PACKET_OPT_TYPE >=", value, "packetOptType");
            return (Criteria) this;
        }

        public Criteria andPacketOptTypeLessThan(Integer value) {
            addCriterion("PACKET_OPT_TYPE <", value, "packetOptType");
            return (Criteria) this;
        }

        public Criteria andPacketOptTypeLessThanOrEqualTo(Integer value) {
            addCriterion("PACKET_OPT_TYPE <=", value, "packetOptType");
            return (Criteria) this;
        }

        public Criteria andPacketOptTypeIn(List<Integer> values) {
            addCriterion("PACKET_OPT_TYPE in", values, "packetOptType");
            return (Criteria) this;
        }

        public Criteria andPacketOptTypeNotIn(List<Integer> values) {
            addCriterion("PACKET_OPT_TYPE not in", values, "packetOptType");
            return (Criteria) this;
        }

        public Criteria andPacketOptTypeBetween(Integer value1, Integer value2) {
            addCriterion("PACKET_OPT_TYPE between", value1, value2, "packetOptType");
            return (Criteria) this;
        }

        public Criteria andPacketOptTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("PACKET_OPT_TYPE not between", value1, value2, "packetOptType");
            return (Criteria) this;
        }

        public Criteria andPacketOptStatusIsNull() {
            addCriterion("PACKET_OPT_STATUS is null");
            return (Criteria) this;
        }

        public Criteria andPacketOptStatusIsNotNull() {
            addCriterion("PACKET_OPT_STATUS is not null");
            return (Criteria) this;
        }

        public Criteria andPacketOptStatusEqualTo(Integer value) {
            addCriterion("PACKET_OPT_STATUS =", value, "packetOptStatus");
            return (Criteria) this;
        }

        public Criteria andPacketOptStatusNotEqualTo(Integer value) {
            addCriterion("PACKET_OPT_STATUS <>", value, "packetOptStatus");
            return (Criteria) this;
        }

        public Criteria andPacketOptStatusGreaterThan(Integer value) {
            addCriterion("PACKET_OPT_STATUS >", value, "packetOptStatus");
            return (Criteria) this;
        }

        public Criteria andPacketOptStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("PACKET_OPT_STATUS >=", value, "packetOptStatus");
            return (Criteria) this;
        }

        public Criteria andPacketOptStatusLessThan(Integer value) {
            addCriterion("PACKET_OPT_STATUS <", value, "packetOptStatus");
            return (Criteria) this;
        }

        public Criteria andPacketOptStatusLessThanOrEqualTo(Integer value) {
            addCriterion("PACKET_OPT_STATUS <=", value, "packetOptStatus");
            return (Criteria) this;
        }

        public Criteria andPacketOptStatusIn(List<Integer> values) {
            addCriterion("PACKET_OPT_STATUS in", values, "packetOptStatus");
            return (Criteria) this;
        }

        public Criteria andPacketOptStatusNotIn(List<Integer> values) {
            addCriterion("PACKET_OPT_STATUS not in", values, "packetOptStatus");
            return (Criteria) this;
        }

        public Criteria andPacketOptStatusBetween(Integer value1, Integer value2) {
            addCriterion("PACKET_OPT_STATUS between", value1, value2, "packetOptStatus");
            return (Criteria) this;
        }

        public Criteria andPacketOptStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("PACKET_OPT_STATUS not between", value1, value2, "packetOptStatus");
            return (Criteria) this;
        }

        public Criteria andReqPacketSizeIsNull() {
            addCriterion("REQ_PACKET_SIZE is null");
            return (Criteria) this;
        }

        public Criteria andReqPacketSizeIsNotNull() {
            addCriterion("REQ_PACKET_SIZE is not null");
            return (Criteria) this;
        }

        public Criteria andReqPacketSizeEqualTo(Long value) {
            addCriterion("REQ_PACKET_SIZE =", value, "reqPacketSize");
            return (Criteria) this;
        }

        public Criteria andReqPacketSizeNotEqualTo(Long value) {
            addCriterion("REQ_PACKET_SIZE <>", value, "reqPacketSize");
            return (Criteria) this;
        }

        public Criteria andReqPacketSizeGreaterThan(Long value) {
            addCriterion("REQ_PACKET_SIZE >", value, "reqPacketSize");
            return (Criteria) this;
        }

        public Criteria andReqPacketSizeGreaterThanOrEqualTo(Long value) {
            addCriterion("REQ_PACKET_SIZE >=", value, "reqPacketSize");
            return (Criteria) this;
        }

        public Criteria andReqPacketSizeLessThan(Long value) {
            addCriterion("REQ_PACKET_SIZE <", value, "reqPacketSize");
            return (Criteria) this;
        }

        public Criteria andReqPacketSizeLessThanOrEqualTo(Long value) {
            addCriterion("REQ_PACKET_SIZE <=", value, "reqPacketSize");
            return (Criteria) this;
        }

        public Criteria andReqPacketSizeIn(List<Long> values) {
            addCriterion("REQ_PACKET_SIZE in", values, "reqPacketSize");
            return (Criteria) this;
        }

        public Criteria andReqPacketSizeNotIn(List<Long> values) {
            addCriterion("REQ_PACKET_SIZE not in", values, "reqPacketSize");
            return (Criteria) this;
        }

        public Criteria andReqPacketSizeBetween(Long value1, Long value2) {
            addCriterion("REQ_PACKET_SIZE between", value1, value2, "reqPacketSize");
            return (Criteria) this;
        }

        public Criteria andReqPacketSizeNotBetween(Long value1, Long value2) {
            addCriterion("REQ_PACKET_SIZE not between", value1, value2, "reqPacketSize");
            return (Criteria) this;
        }

        public Criteria andRespPacketSizeIsNull() {
            addCriterion("RESP_PACKET_SIZE is null");
            return (Criteria) this;
        }

        public Criteria andRespPacketSizeIsNotNull() {
            addCriterion("RESP_PACKET_SIZE is not null");
            return (Criteria) this;
        }

        public Criteria andRespPacketSizeEqualTo(Long value) {
            addCriterion("RESP_PACKET_SIZE =", value, "respPacketSize");
            return (Criteria) this;
        }

        public Criteria andRespPacketSizeNotEqualTo(Long value) {
            addCriterion("RESP_PACKET_SIZE <>", value, "respPacketSize");
            return (Criteria) this;
        }

        public Criteria andRespPacketSizeGreaterThan(Long value) {
            addCriterion("RESP_PACKET_SIZE >", value, "respPacketSize");
            return (Criteria) this;
        }

        public Criteria andRespPacketSizeGreaterThanOrEqualTo(Long value) {
            addCriterion("RESP_PACKET_SIZE >=", value, "respPacketSize");
            return (Criteria) this;
        }

        public Criteria andRespPacketSizeLessThan(Long value) {
            addCriterion("RESP_PACKET_SIZE <", value, "respPacketSize");
            return (Criteria) this;
        }

        public Criteria andRespPacketSizeLessThanOrEqualTo(Long value) {
            addCriterion("RESP_PACKET_SIZE <=", value, "respPacketSize");
            return (Criteria) this;
        }

        public Criteria andRespPacketSizeIn(List<Long> values) {
            addCriterion("RESP_PACKET_SIZE in", values, "respPacketSize");
            return (Criteria) this;
        }

        public Criteria andRespPacketSizeNotIn(List<Long> values) {
            addCriterion("RESP_PACKET_SIZE not in", values, "respPacketSize");
            return (Criteria) this;
        }

        public Criteria andRespPacketSizeBetween(Long value1, Long value2) {
            addCriterion("RESP_PACKET_SIZE between", value1, value2, "respPacketSize");
            return (Criteria) this;
        }

        public Criteria andRespPacketSizeNotBetween(Long value1, Long value2) {
            addCriterion("RESP_PACKET_SIZE not between", value1, value2, "respPacketSize");
            return (Criteria) this;
        }

        public Criteria andTotalRespTimeIsNull() {
            addCriterion("TOTAL_RESP_TIME is null");
            return (Criteria) this;
        }

        public Criteria andTotalRespTimeIsNotNull() {
            addCriterion("TOTAL_RESP_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andTotalRespTimeEqualTo(Float value) {
            addCriterion("TOTAL_RESP_TIME =", value, "totalRespTime");
            return (Criteria) this;
        }

        public Criteria andTotalRespTimeNotEqualTo(Float value) {
            addCriterion("TOTAL_RESP_TIME <>", value, "totalRespTime");
            return (Criteria) this;
        }

        public Criteria andTotalRespTimeGreaterThan(Float value) {
            addCriterion("TOTAL_RESP_TIME >", value, "totalRespTime");
            return (Criteria) this;
        }

        public Criteria andTotalRespTimeGreaterThanOrEqualTo(Float value) {
            addCriterion("TOTAL_RESP_TIME >=", value, "totalRespTime");
            return (Criteria) this;
        }

        public Criteria andTotalRespTimeLessThan(Float value) {
            addCriterion("TOTAL_RESP_TIME <", value, "totalRespTime");
            return (Criteria) this;
        }

        public Criteria andTotalRespTimeLessThanOrEqualTo(Float value) {
            addCriterion("TOTAL_RESP_TIME <=", value, "totalRespTime");
            return (Criteria) this;
        }

        public Criteria andTotalRespTimeIn(List<Float> values) {
            addCriterion("TOTAL_RESP_TIME in", values, "totalRespTime");
            return (Criteria) this;
        }

        public Criteria andTotalRespTimeNotIn(List<Float> values) {
            addCriterion("TOTAL_RESP_TIME not in", values, "totalRespTime");
            return (Criteria) this;
        }

        public Criteria andTotalRespTimeBetween(Float value1, Float value2) {
            addCriterion("TOTAL_RESP_TIME between", value1, value2, "totalRespTime");
            return (Criteria) this;
        }

        public Criteria andTotalRespTimeNotBetween(Float value1, Float value2) {
            addCriterion("TOTAL_RESP_TIME not between", value1, value2, "totalRespTime");
            return (Criteria) this;
        }

        public Criteria andLogErrDescIsNull() {
            addCriterion("LOG_ERR_DESC is null");
            return (Criteria) this;
        }

        public Criteria andLogErrDescIsNotNull() {
            addCriterion("LOG_ERR_DESC is not null");
            return (Criteria) this;
        }

        public Criteria andLogErrDescEqualTo(String value) {
            addCriterion("LOG_ERR_DESC =", value, "logErrDesc");
            return (Criteria) this;
        }

        public Criteria andLogErrDescNotEqualTo(String value) {
            addCriterion("LOG_ERR_DESC <>", value, "logErrDesc");
            return (Criteria) this;
        }

        public Criteria andLogErrDescGreaterThan(String value) {
            addCriterion("LOG_ERR_DESC >", value, "logErrDesc");
            return (Criteria) this;
        }

        public Criteria andLogErrDescGreaterThanOrEqualTo(String value) {
            addCriterion("LOG_ERR_DESC >=", value, "logErrDesc");
            return (Criteria) this;
        }

        public Criteria andLogErrDescLessThan(String value) {
            addCriterion("LOG_ERR_DESC <", value, "logErrDesc");
            return (Criteria) this;
        }

        public Criteria andLogErrDescLessThanOrEqualTo(String value) {
            addCriterion("LOG_ERR_DESC <=", value, "logErrDesc");
            return (Criteria) this;
        }

        public Criteria andLogErrDescLike(String value) {
            addCriterion("LOG_ERR_DESC like", value, "logErrDesc");
            return (Criteria) this;
        }

        public Criteria andLogErrDescNotLike(String value) {
            addCriterion("LOG_ERR_DESC not like", value, "logErrDesc");
            return (Criteria) this;
        }

        public Criteria andLogErrDescIn(List<String> values) {
            addCriterion("LOG_ERR_DESC in", values, "logErrDesc");
            return (Criteria) this;
        }

        public Criteria andLogErrDescNotIn(List<String> values) {
            addCriterion("LOG_ERR_DESC not in", values, "logErrDesc");
            return (Criteria) this;
        }

        public Criteria andLogErrDescBetween(String value1, String value2) {
            addCriterion("LOG_ERR_DESC between", value1, value2, "logErrDesc");
            return (Criteria) this;
        }

        public Criteria andLogErrDescNotBetween(String value1, String value2) {
            addCriterion("LOG_ERR_DESC not between", value1, value2, "logErrDesc");
            return (Criteria) this;
        }

        public Criteria andLogStackTraceIdIsNull() {
            addCriterion("LOG_STACK_TRACE_ID is null");
            return (Criteria) this;
        }

        public Criteria andLogStackTraceIdIsNotNull() {
            addCriterion("LOG_STACK_TRACE_ID is not null");
            return (Criteria) this;
        }

        public Criteria andLogStackTraceIdEqualTo(String value) {
            addCriterion("LOG_STACK_TRACE_ID =", value, "logStackTraceId");
            return (Criteria) this;
        }

        public Criteria andLogStackTraceIdNotEqualTo(String value) {
            addCriterion("LOG_STACK_TRACE_ID <>", value, "logStackTraceId");
            return (Criteria) this;
        }

        public Criteria andLogStackTraceIdGreaterThan(String value) {
            addCriterion("LOG_STACK_TRACE_ID >", value, "logStackTraceId");
            return (Criteria) this;
        }

        public Criteria andLogStackTraceIdGreaterThanOrEqualTo(String value) {
            addCriterion("LOG_STACK_TRACE_ID >=", value, "logStackTraceId");
            return (Criteria) this;
        }

        public Criteria andLogStackTraceIdLessThan(String value) {
            addCriterion("LOG_STACK_TRACE_ID <", value, "logStackTraceId");
            return (Criteria) this;
        }

        public Criteria andLogStackTraceIdLessThanOrEqualTo(String value) {
            addCriterion("LOG_STACK_TRACE_ID <=", value, "logStackTraceId");
            return (Criteria) this;
        }

        public Criteria andLogStackTraceIdLike(String value) {
            addCriterion("LOG_STACK_TRACE_ID like", value, "logStackTraceId");
            return (Criteria) this;
        }

        public Criteria andLogStackTraceIdNotLike(String value) {
            addCriterion("LOG_STACK_TRACE_ID not like", value, "logStackTraceId");
            return (Criteria) this;
        }

        public Criteria andLogStackTraceIdIn(List<String> values) {
            addCriterion("LOG_STACK_TRACE_ID in", values, "logStackTraceId");
            return (Criteria) this;
        }

        public Criteria andLogStackTraceIdNotIn(List<String> values) {
            addCriterion("LOG_STACK_TRACE_ID not in", values, "logStackTraceId");
            return (Criteria) this;
        }

        public Criteria andLogStackTraceIdBetween(String value1, String value2) {
            addCriterion("LOG_STACK_TRACE_ID between", value1, value2, "logStackTraceId");
            return (Criteria) this;
        }

        public Criteria andLogStackTraceIdNotBetween(String value1, String value2) {
            addCriterion("LOG_STACK_TRACE_ID not between", value1, value2, "logStackTraceId");
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