package com.gy.hsxt.gpf.bm.bean;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;

/**
 * 增值节点
 *
 * @Package :com.gy.hsxt.gpf.bm.bean
 * @ClassName : IncNode
 * @Description : 增值节点
 * @Author : chenm
 * @Date : 2015/10/12 19:29
 * @Version V3.0.0.0
 */
public class IncNode implements Serializable {

    private static final long serialVersionUID = 676289460838729075L;
    /**
     * 资源号
     */
    private String resNo;
    /**
     * 客户号
     **/
    private String custId;
    /**
     * 父节点
     */
    private String parent;
    /**
     * 左增值节点
     */
    private String left;

    /**
     * 右增值节点
     */
    private String right;
    /**
     * 左增值节点的子节点数量（不包含自身）
     */
    private Integer lCount;
    /**
     * 右增值节点的子节点数量（不包含自身）
     */
    private Integer rCount;
    /**
     * 左增值点
     */
    private Integer lP;
    /**
     * 右增值点
     */
    private Integer rP;

    /**
     * 节点层级
     */
    private Integer level;

    /**
     * 启用消费者资源类型
     */
    private Integer type;

    /**
     * 积分参考值
     */
    private String refValue;

    /**
     * 计算基数
     */
    private String baseValue;

    /**
     * 是否有效 Y --有效 N--无效
     */
    private String isActive;

    public IncNode() {

    }

    public IncNode(String custId, String resNo, String left, String right) {
        this.left = left;
        this.right = right;
        this.resNo = resNo;
        this.custId = custId;
    }

    public IncNode(String custId, String resNo, String parent, String left, String right) {
        this(custId, resNo, left, right);
        this.parent = parent;
    }

    public IncNode(String custId, String resNo, String parent, String left, String right,
                   Integer lCount, Integer rCount) {
        this(custId, resNo, parent, left, right);
        this.lCount = lCount;
        this.rCount = rCount;
    }

    public IncNode(String custId, String resNo, String parent, String left, String right,
                   Integer lCount, Integer rCount, Integer level, Integer type) {
        this(custId, resNo, parent, left, right, lCount, rCount);
        this.level = level;
        this.type = type;
    }

    public IncNode(String custId, String resNo, String parent, String left, String right,
                   Integer lCount, Integer rCount, Integer lP, Integer rP, Integer level, Integer type) {
        this(custId, resNo, parent, left, right, lCount, rCount, level, type);
        this.lP = lP;
        this.rP = rP;
    }

    public IncNode(String custId, String resNo, String parent, Integer lCount, Integer rCount,
                   Integer lP, Integer rP, Integer level, Integer type) {
        this.custId = custId;
        this.resNo = resNo;
        this.parent = parent;
        this.lCount = lCount;
        this.rCount = rCount;
        this.lP = lP;
        this.rP = rP;
        this.level = level;
        this.type = type;
    }

    public String getResNo() {
        return resNo;
    }

    public void setResNo(String resNo) {
        this.resNo = resNo;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getLeft() {
        return left;
    }

    public void setLeft(String left) {
        this.left = left;
    }

    public String getRight() {
        return right;
    }

    public void setRight(String right) {
        this.right = right;
    }

    public Integer getLCount() {
        return lCount;
    }

    public void setLCount(Integer lCount) {
        this.lCount = lCount;
    }

    public Integer getRCount() {
        return rCount;
    }

    public void setRCount(Integer rCount) {
        this.rCount = rCount;
    }

    public Integer getLP() {
        return lP;
    }

    public void setLP(Integer lP) {
        this.lP = lP;
    }

    public Integer getRP() {
        return rP;
    }

    public void setRP(Integer rP) {
        this.rP = rP;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getRefValue() {
        return refValue;
    }

    public void setRefValue(String refValue) {
        this.refValue = refValue;
    }

    public String getBaseValue() {
        return baseValue;
    }

    public void setBaseValue(String baseValue) {
        this.baseValue = baseValue;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
