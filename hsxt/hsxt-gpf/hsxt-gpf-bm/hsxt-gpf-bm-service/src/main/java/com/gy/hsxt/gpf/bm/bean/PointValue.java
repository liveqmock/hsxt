package com.gy.hsxt.gpf.bm.bean;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;

/**
 * 互生积分分配详情
 *
 * @Package : com.gy.hsxt.gpf.bm.bean
 * @ClassName : PointValue
 * @Description : 互生积分分配详情
 * @Author : chenm
 * @Date : 2016/1/8 9:55
 * @Version V3.0.0.0
 */
public class PointValue implements Serializable {

    private static final long serialVersionUID = -1510010853157975549L;

    //资源号
    private String resNo;
    //客户号
    private String custId;
    //分配时间
    private String date;
    //分配积分点
    private Integer pv=0;

    //主积分增值点信息
    private Integer leftCount1=0;
    private Integer rightCount1=0;
    private String left1;
    private String right1;
    private Integer leftP1=0;
    private Integer rightP1=0;
    private Integer mlmPoint1=0;

    //左积分增值点信息

    private Integer leftCount2=0;
    private Integer rightCount2=0;
    private String left2;
    private String right2;
    private Integer leftP2=0;
    private Integer rightP2=0;
    private Integer mlmPoint2=0;

    //右积分增值点信息
    private Integer leftCount3=0;
    private Integer rightCount3=0;
    private String left3;
    private String right3;
    private Integer leftP3=0;
    private Integer rightP3=0;
    private Integer mlmPoint3=0;

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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getPv() {
        return pv;
    }

    public void setPv(Integer pv) {
        this.pv = pv;
    }

    public Integer getLeftCount1() {
        return leftCount1;
    }

    public void setLeftCount1(Integer leftCount1) {
        this.leftCount1 = leftCount1;
    }

    public Integer getRightCount1() {
        return rightCount1;
    }

    public void setRightCount1(Integer rightCount1) {
        this.rightCount1 = rightCount1;
    }

    public String getLeft1() {
        return left1;
    }

    public void setLeft1(String left1) {
        this.left1 = left1;
    }

    public String getRight1() {
        return right1;
    }

    public void setRight1(String right1) {
        this.right1 = right1;
    }

    public Integer getLeftP1() {
        return leftP1;
    }

    public void setLeftP1(Integer leftP1) {
        this.leftP1 = leftP1;
    }

    public Integer getRightP1() {
        return rightP1;
    }

    public void setRightP1(Integer rightP1) {
        this.rightP1 = rightP1;
    }

    public Integer getMlmPoint1() {
        return mlmPoint1;
    }

    public void setMlmPoint1(Integer mlmPoint1) {
        this.mlmPoint1 = mlmPoint1;
    }

    public Integer getLeftCount2() {
        return leftCount2;
    }

    public void setLeftCount2(Integer leftCount2) {
        this.leftCount2 = leftCount2;
    }

    public Integer getRightCount2() {
        return rightCount2;
    }

    public void setRightCount2(Integer rightCount2) {
        this.rightCount2 = rightCount2;
    }

    public String getLeft2() {
        return left2;
    }

    public void setLeft2(String left2) {
        this.left2 = left2;
    }

    public String getRight2() {
        return right2;
    }

    public void setRight2(String right2) {
        this.right2 = right2;
    }

    public Integer getLeftP2() {
        return leftP2;
    }

    public void setLeftP2(Integer leftP2) {
        this.leftP2 = leftP2;
    }

    public Integer getRightP2() {
        return rightP2;
    }

    public void setRightP2(Integer rightP2) {
        this.rightP2 = rightP2;
    }

    public Integer getMlmPoint2() {
        return mlmPoint2;
    }

    public void setMlmPoint2(Integer mlmPoint2) {
        this.mlmPoint2 = mlmPoint2;
    }

    public Integer getLeftCount3() {
        return leftCount3;
    }

    public void setLeftCount3(Integer leftCount3) {
        this.leftCount3 = leftCount3;
    }

    public Integer getRightCount3() {
        return rightCount3;
    }

    public void setRightCount3(Integer rightCount3) {
        this.rightCount3 = rightCount3;
    }

    public String getLeft3() {
        return left3;
    }

    public void setLeft3(String left3) {
        this.left3 = left3;
    }

    public String getRight3() {
        return right3;
    }

    public void setRight3(String right3) {
        this.right3 = right3;
    }

    public Integer getLeftP3() {
        return leftP3;
    }

    public void setLeftP3(Integer leftP3) {
        this.leftP3 = leftP3;
    }

    public Integer getRightP3() {
        return rightP3;
    }

    public void setRightP3(Integer rightP3) {
        this.rightP3 = rightP3;
    }

    public Integer getMlmPoint3() {
        return mlmPoint3;
    }

    public void setMlmPoint3(Integer mlmPoint3) {
        this.mlmPoint3 = mlmPoint3;
    }

    /**
     * 设置主节点的值
     * @param incNode
     * @param point
     */
    public void setMain(IncNode incNode, int point){
            this.setResNo(incNode.getResNo());
            this.setCustId(incNode.getCustId());
            this.setLeftCount1(incNode.getLCount());
            this.setRightCount1(incNode.getRCount());
            this.setLeft1(incNode.getLeft());
            this.setRight1(incNode.getRight());
            this.setLeftP1(incNode.getLP());
            this.setRightP1(incNode.getRP());
            this.setMlmPoint1(point);
    }

    /**
     * 设置左节点的值
     * @param incNode
     * @param point
     */
    public void setLeft(IncNode incNode, int point){
        this.setLeftCount2(incNode.getLCount());
        this.setRightCount2(incNode.getRCount());
        this.setLeft2(incNode.getLeft());
        this.setRight2(incNode.getRight());
        this.setLeftP2(incNode.getLP());
        this.setRightP2(incNode.getRP());
        this.setMlmPoint2(point);
    }

    /**
     * 设置右节点的值
     * @param incNode
     * @param point
     */
    public void setRight(IncNode incNode, int point){
        this.setLeftCount3(incNode.getLCount());
        this.setRightCount3(incNode.getRCount());
        this.setLeft3(incNode.getLeft());
        this.setRight3(incNode.getRight());
        this.setLeftP3(incNode.getLP());
        this.setRightP3(incNode.getRP());
        this.setMlmPoint3(point);
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
