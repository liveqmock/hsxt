package com.gy.hsi.fs.server.common.framework.mybatis.mapvo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TFsFileMetaDataDelExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TFsFileMetaDataDelExample() {
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

        public Criteria andFileStorageIdIsNull() {
            addCriterion("FILE_STORAGE_ID is null");
            return (Criteria) this;
        }

        public Criteria andFileStorageIdIsNotNull() {
            addCriterion("FILE_STORAGE_ID is not null");
            return (Criteria) this;
        }

        public Criteria andFileStorageIdEqualTo(String value) {
            addCriterion("FILE_STORAGE_ID =", value, "fileStorageId");
            return (Criteria) this;
        }

        public Criteria andFileStorageIdNotEqualTo(String value) {
            addCriterion("FILE_STORAGE_ID <>", value, "fileStorageId");
            return (Criteria) this;
        }

        public Criteria andFileStorageIdGreaterThan(String value) {
            addCriterion("FILE_STORAGE_ID >", value, "fileStorageId");
            return (Criteria) this;
        }

        public Criteria andFileStorageIdGreaterThanOrEqualTo(String value) {
            addCriterion("FILE_STORAGE_ID >=", value, "fileStorageId");
            return (Criteria) this;
        }

        public Criteria andFileStorageIdLessThan(String value) {
            addCriterion("FILE_STORAGE_ID <", value, "fileStorageId");
            return (Criteria) this;
        }

        public Criteria andFileStorageIdLessThanOrEqualTo(String value) {
            addCriterion("FILE_STORAGE_ID <=", value, "fileStorageId");
            return (Criteria) this;
        }

        public Criteria andFileStorageIdLike(String value) {
            addCriterion("FILE_STORAGE_ID like", value, "fileStorageId");
            return (Criteria) this;
        }

        public Criteria andFileStorageIdNotLike(String value) {
            addCriterion("FILE_STORAGE_ID not like", value, "fileStorageId");
            return (Criteria) this;
        }

        public Criteria andFileStorageIdIn(List<String> values) {
            addCriterion("FILE_STORAGE_ID in", values, "fileStorageId");
            return (Criteria) this;
        }

        public Criteria andFileStorageIdNotIn(List<String> values) {
            addCriterion("FILE_STORAGE_ID not in", values, "fileStorageId");
            return (Criteria) this;
        }

        public Criteria andFileStorageIdBetween(String value1, String value2) {
            addCriterion("FILE_STORAGE_ID between", value1, value2, "fileStorageId");
            return (Criteria) this;
        }

        public Criteria andFileStorageIdNotBetween(String value1, String value2) {
            addCriterion("FILE_STORAGE_ID not between", value1, value2, "fileStorageId");
            return (Criteria) this;
        }

        public Criteria andFilePermissionIsNull() {
            addCriterion("FILE_PERMISSION is null");
            return (Criteria) this;
        }

        public Criteria andFilePermissionIsNotNull() {
            addCriterion("FILE_PERMISSION is not null");
            return (Criteria) this;
        }

        public Criteria andFilePermissionEqualTo(String value) {
            addCriterion("FILE_PERMISSION =", value, "filePermission");
            return (Criteria) this;
        }

        public Criteria andFilePermissionNotEqualTo(String value) {
            addCriterion("FILE_PERMISSION <>", value, "filePermission");
            return (Criteria) this;
        }

        public Criteria andFilePermissionGreaterThan(String value) {
            addCriterion("FILE_PERMISSION >", value, "filePermission");
            return (Criteria) this;
        }

        public Criteria andFilePermissionGreaterThanOrEqualTo(String value) {
            addCriterion("FILE_PERMISSION >=", value, "filePermission");
            return (Criteria) this;
        }

        public Criteria andFilePermissionLessThan(String value) {
            addCriterion("FILE_PERMISSION <", value, "filePermission");
            return (Criteria) this;
        }

        public Criteria andFilePermissionLessThanOrEqualTo(String value) {
            addCriterion("FILE_PERMISSION <=", value, "filePermission");
            return (Criteria) this;
        }

        public Criteria andFilePermissionLike(String value) {
            addCriterion("FILE_PERMISSION like", value, "filePermission");
            return (Criteria) this;
        }

        public Criteria andFilePermissionNotLike(String value) {
            addCriterion("FILE_PERMISSION not like", value, "filePermission");
            return (Criteria) this;
        }

        public Criteria andFilePermissionIn(List<String> values) {
            addCriterion("FILE_PERMISSION in", values, "filePermission");
            return (Criteria) this;
        }

        public Criteria andFilePermissionNotIn(List<String> values) {
            addCriterion("FILE_PERMISSION not in", values, "filePermission");
            return (Criteria) this;
        }

        public Criteria andFilePermissionBetween(String value1, String value2) {
            addCriterion("FILE_PERMISSION between", value1, value2, "filePermission");
            return (Criteria) this;
        }

        public Criteria andFilePermissionNotBetween(String value1, String value2) {
            addCriterion("FILE_PERMISSION not between", value1, value2, "filePermission");
            return (Criteria) this;
        }

        public Criteria andOwnerUserIdIsNull() {
            addCriterion("OWNER_USER_ID is null");
            return (Criteria) this;
        }

        public Criteria andOwnerUserIdIsNotNull() {
            addCriterion("OWNER_USER_ID is not null");
            return (Criteria) this;
        }

        public Criteria andOwnerUserIdEqualTo(String value) {
            addCriterion("OWNER_USER_ID =", value, "ownerUserId");
            return (Criteria) this;
        }

        public Criteria andOwnerUserIdNotEqualTo(String value) {
            addCriterion("OWNER_USER_ID <>", value, "ownerUserId");
            return (Criteria) this;
        }

        public Criteria andOwnerUserIdGreaterThan(String value) {
            addCriterion("OWNER_USER_ID >", value, "ownerUserId");
            return (Criteria) this;
        }

        public Criteria andOwnerUserIdGreaterThanOrEqualTo(String value) {
            addCriterion("OWNER_USER_ID >=", value, "ownerUserId");
            return (Criteria) this;
        }

        public Criteria andOwnerUserIdLessThan(String value) {
            addCriterion("OWNER_USER_ID <", value, "ownerUserId");
            return (Criteria) this;
        }

        public Criteria andOwnerUserIdLessThanOrEqualTo(String value) {
            addCriterion("OWNER_USER_ID <=", value, "ownerUserId");
            return (Criteria) this;
        }

        public Criteria andOwnerUserIdLike(String value) {
            addCriterion("OWNER_USER_ID like", value, "ownerUserId");
            return (Criteria) this;
        }

        public Criteria andOwnerUserIdNotLike(String value) {
            addCriterion("OWNER_USER_ID not like", value, "ownerUserId");
            return (Criteria) this;
        }

        public Criteria andOwnerUserIdIn(List<String> values) {
            addCriterion("OWNER_USER_ID in", values, "ownerUserId");
            return (Criteria) this;
        }

        public Criteria andOwnerUserIdNotIn(List<String> values) {
            addCriterion("OWNER_USER_ID not in", values, "ownerUserId");
            return (Criteria) this;
        }

        public Criteria andOwnerUserIdBetween(String value1, String value2) {
            addCriterion("OWNER_USER_ID between", value1, value2, "ownerUserId");
            return (Criteria) this;
        }

        public Criteria andOwnerUserIdNotBetween(String value1, String value2) {
            addCriterion("OWNER_USER_ID not between", value1, value2, "ownerUserId");
            return (Criteria) this;
        }

        public Criteria andIsAnonymousIsNull() {
            addCriterion("IS_ANONYMOUS is null");
            return (Criteria) this;
        }

        public Criteria andIsAnonymousIsNotNull() {
            addCriterion("IS_ANONYMOUS is not null");
            return (Criteria) this;
        }

        public Criteria andIsAnonymousEqualTo(Boolean value) {
            addCriterion("IS_ANONYMOUS =", value, "isAnonymous");
            return (Criteria) this;
        }

        public Criteria andIsAnonymousNotEqualTo(Boolean value) {
            addCriterion("IS_ANONYMOUS <>", value, "isAnonymous");
            return (Criteria) this;
        }

        public Criteria andIsAnonymousGreaterThan(Boolean value) {
            addCriterion("IS_ANONYMOUS >", value, "isAnonymous");
            return (Criteria) this;
        }

        public Criteria andIsAnonymousGreaterThanOrEqualTo(Boolean value) {
            addCriterion("IS_ANONYMOUS >=", value, "isAnonymous");
            return (Criteria) this;
        }

        public Criteria andIsAnonymousLessThan(Boolean value) {
            addCriterion("IS_ANONYMOUS <", value, "isAnonymous");
            return (Criteria) this;
        }

        public Criteria andIsAnonymousLessThanOrEqualTo(Boolean value) {
            addCriterion("IS_ANONYMOUS <=", value, "isAnonymous");
            return (Criteria) this;
        }

        public Criteria andIsAnonymousIn(List<Boolean> values) {
            addCriterion("IS_ANONYMOUS in", values, "isAnonymous");
            return (Criteria) this;
        }

        public Criteria andIsAnonymousNotIn(List<Boolean> values) {
            addCriterion("IS_ANONYMOUS not in", values, "isAnonymous");
            return (Criteria) this;
        }

        public Criteria andIsAnonymousBetween(Boolean value1, Boolean value2) {
            addCriterion("IS_ANONYMOUS between", value1, value2, "isAnonymous");
            return (Criteria) this;
        }

        public Criteria andIsAnonymousNotBetween(Boolean value1, Boolean value2) {
            addCriterion("IS_ANONYMOUS not between", value1, value2, "isAnonymous");
            return (Criteria) this;
        }

        public Criteria andFileNameIsNull() {
            addCriterion("FILE_NAME is null");
            return (Criteria) this;
        }

        public Criteria andFileNameIsNotNull() {
            addCriterion("FILE_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andFileNameEqualTo(String value) {
            addCriterion("FILE_NAME =", value, "fileName");
            return (Criteria) this;
        }

        public Criteria andFileNameNotEqualTo(String value) {
            addCriterion("FILE_NAME <>", value, "fileName");
            return (Criteria) this;
        }

        public Criteria andFileNameGreaterThan(String value) {
            addCriterion("FILE_NAME >", value, "fileName");
            return (Criteria) this;
        }

        public Criteria andFileNameGreaterThanOrEqualTo(String value) {
            addCriterion("FILE_NAME >=", value, "fileName");
            return (Criteria) this;
        }

        public Criteria andFileNameLessThan(String value) {
            addCriterion("FILE_NAME <", value, "fileName");
            return (Criteria) this;
        }

        public Criteria andFileNameLessThanOrEqualTo(String value) {
            addCriterion("FILE_NAME <=", value, "fileName");
            return (Criteria) this;
        }

        public Criteria andFileNameLike(String value) {
            addCriterion("FILE_NAME like", value, "fileName");
            return (Criteria) this;
        }

        public Criteria andFileNameNotLike(String value) {
            addCriterion("FILE_NAME not like", value, "fileName");
            return (Criteria) this;
        }

        public Criteria andFileNameIn(List<String> values) {
            addCriterion("FILE_NAME in", values, "fileName");
            return (Criteria) this;
        }

        public Criteria andFileNameNotIn(List<String> values) {
            addCriterion("FILE_NAME not in", values, "fileName");
            return (Criteria) this;
        }

        public Criteria andFileNameBetween(String value1, String value2) {
            addCriterion("FILE_NAME between", value1, value2, "fileName");
            return (Criteria) this;
        }

        public Criteria andFileNameNotBetween(String value1, String value2) {
            addCriterion("FILE_NAME not between", value1, value2, "fileName");
            return (Criteria) this;
        }

        public Criteria andFileSuffixIsNull() {
            addCriterion("FILE_SUFFIX is null");
            return (Criteria) this;
        }

        public Criteria andFileSuffixIsNotNull() {
            addCriterion("FILE_SUFFIX is not null");
            return (Criteria) this;
        }

        public Criteria andFileSuffixEqualTo(String value) {
            addCriterion("FILE_SUFFIX =", value, "fileSuffix");
            return (Criteria) this;
        }

        public Criteria andFileSuffixNotEqualTo(String value) {
            addCriterion("FILE_SUFFIX <>", value, "fileSuffix");
            return (Criteria) this;
        }

        public Criteria andFileSuffixGreaterThan(String value) {
            addCriterion("FILE_SUFFIX >", value, "fileSuffix");
            return (Criteria) this;
        }

        public Criteria andFileSuffixGreaterThanOrEqualTo(String value) {
            addCriterion("FILE_SUFFIX >=", value, "fileSuffix");
            return (Criteria) this;
        }

        public Criteria andFileSuffixLessThan(String value) {
            addCriterion("FILE_SUFFIX <", value, "fileSuffix");
            return (Criteria) this;
        }

        public Criteria andFileSuffixLessThanOrEqualTo(String value) {
            addCriterion("FILE_SUFFIX <=", value, "fileSuffix");
            return (Criteria) this;
        }

        public Criteria andFileSuffixLike(String value) {
            addCriterion("FILE_SUFFIX like", value, "fileSuffix");
            return (Criteria) this;
        }

        public Criteria andFileSuffixNotLike(String value) {
            addCriterion("FILE_SUFFIX not like", value, "fileSuffix");
            return (Criteria) this;
        }

        public Criteria andFileSuffixIn(List<String> values) {
            addCriterion("FILE_SUFFIX in", values, "fileSuffix");
            return (Criteria) this;
        }

        public Criteria andFileSuffixNotIn(List<String> values) {
            addCriterion("FILE_SUFFIX not in", values, "fileSuffix");
            return (Criteria) this;
        }

        public Criteria andFileSuffixBetween(String value1, String value2) {
            addCriterion("FILE_SUFFIX between", value1, value2, "fileSuffix");
            return (Criteria) this;
        }

        public Criteria andFileSuffixNotBetween(String value1, String value2) {
            addCriterion("FILE_SUFFIX not between", value1, value2, "fileSuffix");
            return (Criteria) this;
        }

        public Criteria andFileSizeBytesIsNull() {
            addCriterion("FILE_SIZE_BYTES is null");
            return (Criteria) this;
        }

        public Criteria andFileSizeBytesIsNotNull() {
            addCriterion("FILE_SIZE_BYTES is not null");
            return (Criteria) this;
        }

        public Criteria andFileSizeBytesEqualTo(Long value) {
            addCriterion("FILE_SIZE_BYTES =", value, "fileSizeBytes");
            return (Criteria) this;
        }

        public Criteria andFileSizeBytesNotEqualTo(Long value) {
            addCriterion("FILE_SIZE_BYTES <>", value, "fileSizeBytes");
            return (Criteria) this;
        }

        public Criteria andFileSizeBytesGreaterThan(Long value) {
            addCriterion("FILE_SIZE_BYTES >", value, "fileSizeBytes");
            return (Criteria) this;
        }

        public Criteria andFileSizeBytesGreaterThanOrEqualTo(Long value) {
            addCriterion("FILE_SIZE_BYTES >=", value, "fileSizeBytes");
            return (Criteria) this;
        }

        public Criteria andFileSizeBytesLessThan(Long value) {
            addCriterion("FILE_SIZE_BYTES <", value, "fileSizeBytes");
            return (Criteria) this;
        }

        public Criteria andFileSizeBytesLessThanOrEqualTo(Long value) {
            addCriterion("FILE_SIZE_BYTES <=", value, "fileSizeBytes");
            return (Criteria) this;
        }

        public Criteria andFileSizeBytesIn(List<Long> values) {
            addCriterion("FILE_SIZE_BYTES in", values, "fileSizeBytes");
            return (Criteria) this;
        }

        public Criteria andFileSizeBytesNotIn(List<Long> values) {
            addCriterion("FILE_SIZE_BYTES not in", values, "fileSizeBytes");
            return (Criteria) this;
        }

        public Criteria andFileSizeBytesBetween(Long value1, Long value2) {
            addCriterion("FILE_SIZE_BYTES between", value1, value2, "fileSizeBytes");
            return (Criteria) this;
        }

        public Criteria andFileSizeBytesNotBetween(Long value1, Long value2) {
            addCriterion("FILE_SIZE_BYTES not between", value1, value2, "fileSizeBytes");
            return (Criteria) this;
        }

        public Criteria andFileStatusIsNull() {
            addCriterion("FILE_STATUS is null");
            return (Criteria) this;
        }

        public Criteria andFileStatusIsNotNull() {
            addCriterion("FILE_STATUS is not null");
            return (Criteria) this;
        }

        public Criteria andFileStatusEqualTo(Byte value) {
            addCriterion("FILE_STATUS =", value, "fileStatus");
            return (Criteria) this;
        }

        public Criteria andFileStatusNotEqualTo(Byte value) {
            addCriterion("FILE_STATUS <>", value, "fileStatus");
            return (Criteria) this;
        }

        public Criteria andFileStatusGreaterThan(Byte value) {
            addCriterion("FILE_STATUS >", value, "fileStatus");
            return (Criteria) this;
        }

        public Criteria andFileStatusGreaterThanOrEqualTo(Byte value) {
            addCriterion("FILE_STATUS >=", value, "fileStatus");
            return (Criteria) this;
        }

        public Criteria andFileStatusLessThan(Byte value) {
            addCriterion("FILE_STATUS <", value, "fileStatus");
            return (Criteria) this;
        }

        public Criteria andFileStatusLessThanOrEqualTo(Byte value) {
            addCriterion("FILE_STATUS <=", value, "fileStatus");
            return (Criteria) this;
        }

        public Criteria andFileStatusIn(List<Byte> values) {
            addCriterion("FILE_STATUS in", values, "fileStatus");
            return (Criteria) this;
        }

        public Criteria andFileStatusNotIn(List<Byte> values) {
            addCriterion("FILE_STATUS not in", values, "fileStatus");
            return (Criteria) this;
        }

        public Criteria andFileStatusBetween(Byte value1, Byte value2) {
            addCriterion("FILE_STATUS between", value1, value2, "fileStatus");
            return (Criteria) this;
        }

        public Criteria andFileStatusNotBetween(Byte value1, Byte value2) {
            addCriterion("FILE_STATUS not between", value1, value2, "fileStatus");
            return (Criteria) this;
        }

        public Criteria andFailedCountsIsNull() {
            addCriterion("FAILED_COUNTS is null");
            return (Criteria) this;
        }

        public Criteria andFailedCountsIsNotNull() {
            addCriterion("FAILED_COUNTS is not null");
            return (Criteria) this;
        }

        public Criteria andFailedCountsEqualTo(Integer value) {
            addCriterion("FAILED_COUNTS =", value, "failedCounts");
            return (Criteria) this;
        }

        public Criteria andFailedCountsNotEqualTo(Integer value) {
            addCriterion("FAILED_COUNTS <>", value, "failedCounts");
            return (Criteria) this;
        }

        public Criteria andFailedCountsGreaterThan(Integer value) {
            addCriterion("FAILED_COUNTS >", value, "failedCounts");
            return (Criteria) this;
        }

        public Criteria andFailedCountsGreaterThanOrEqualTo(Integer value) {
            addCriterion("FAILED_COUNTS >=", value, "failedCounts");
            return (Criteria) this;
        }

        public Criteria andFailedCountsLessThan(Integer value) {
            addCriterion("FAILED_COUNTS <", value, "failedCounts");
            return (Criteria) this;
        }

        public Criteria andFailedCountsLessThanOrEqualTo(Integer value) {
            addCriterion("FAILED_COUNTS <=", value, "failedCounts");
            return (Criteria) this;
        }

        public Criteria andFailedCountsIn(List<Integer> values) {
            addCriterion("FAILED_COUNTS in", values, "failedCounts");
            return (Criteria) this;
        }

        public Criteria andFailedCountsNotIn(List<Integer> values) {
            addCriterion("FAILED_COUNTS not in", values, "failedCounts");
            return (Criteria) this;
        }

        public Criteria andFailedCountsBetween(Integer value1, Integer value2) {
            addCriterion("FAILED_COUNTS between", value1, value2, "failedCounts");
            return (Criteria) this;
        }

        public Criteria andFailedCountsNotBetween(Integer value1, Integer value2) {
            addCriterion("FAILED_COUNTS not between", value1, value2, "failedCounts");
            return (Criteria) this;
        }

        public Criteria andLastFailedDateIsNull() {
            addCriterion("LAST_FAILED_DATE is null");
            return (Criteria) this;
        }

        public Criteria andLastFailedDateIsNotNull() {
            addCriterion("LAST_FAILED_DATE is not null");
            return (Criteria) this;
        }

        public Criteria andLastFailedDateEqualTo(Date value) {
            addCriterion("LAST_FAILED_DATE =", value, "lastFailedDate");
            return (Criteria) this;
        }

        public Criteria andLastFailedDateNotEqualTo(Date value) {
            addCriterion("LAST_FAILED_DATE <>", value, "lastFailedDate");
            return (Criteria) this;
        }

        public Criteria andLastFailedDateGreaterThan(Date value) {
            addCriterion("LAST_FAILED_DATE >", value, "lastFailedDate");
            return (Criteria) this;
        }

        public Criteria andLastFailedDateGreaterThanOrEqualTo(Date value) {
            addCriterion("LAST_FAILED_DATE >=", value, "lastFailedDate");
            return (Criteria) this;
        }

        public Criteria andLastFailedDateLessThan(Date value) {
            addCriterion("LAST_FAILED_DATE <", value, "lastFailedDate");
            return (Criteria) this;
        }

        public Criteria andLastFailedDateLessThanOrEqualTo(Date value) {
            addCriterion("LAST_FAILED_DATE <=", value, "lastFailedDate");
            return (Criteria) this;
        }

        public Criteria andLastFailedDateIn(List<Date> values) {
            addCriterion("LAST_FAILED_DATE in", values, "lastFailedDate");
            return (Criteria) this;
        }

        public Criteria andLastFailedDateNotIn(List<Date> values) {
            addCriterion("LAST_FAILED_DATE not in", values, "lastFailedDate");
            return (Criteria) this;
        }

        public Criteria andLastFailedDateBetween(Date value1, Date value2) {
            addCriterion("LAST_FAILED_DATE between", value1, value2, "lastFailedDate");
            return (Criteria) this;
        }

        public Criteria andLastFailedDateNotBetween(Date value1, Date value2) {
            addCriterion("LAST_FAILED_DATE not between", value1, value2, "lastFailedDate");
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