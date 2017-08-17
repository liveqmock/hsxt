/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.bs.bean.bm;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;

/**
 * 再增值积分详情
 *
 * @Package :com.gy.hsxt.bs.bean.bm
 * @ClassName : MlmDetail
 * @Description : TODO
 * @Author : chenm
 * @Date : 2015/11/6 16:45
 * @Version V3.0.0.0
 */
public class MlmDetail implements Serializable {

    private static final long serialVersionUID = -7917483365920706079L;
    /**
     * 主键
     */
    private String detailId;

    /**
     * 汇总ID
     */
    private String totalId;

    /**
     * 增值分配时间
     */
    private String date;

    /**
     * 企业互生号
     */
    private String resNo;

    /**
     * 主节点客户号
     */
    private String custId;

    /**
     * 分配积分总数
     */
    private int pv;

    /**
     * 主节点上的左边子节点数
     */
    private int leftCount1;
    /**
     * 主节点上的右边子节点数
     */
    private int rightCount1;
    /**
     * 主节点上的左节点客户号
     */
    private String left1;
    /**
     * 主节点上的右节点客户号
     */
    private String right1;
    /**
     * 主节点上的左增值点
     */
    private int leftP1;
    /**
     * 主节点上的右增值点
     */
    private int rightP1;
    /**
     * 主节点上的分配数
     */
    private int mlmPoint1;

    /**
     * 左节点上的左边子节点数
     */
    private int leftCount2;
    /**
     * 左节点上的右边子节点数
     */
    private int rightCount2;
    /**
     * 左节点上的左节点客户号
     */
    private String left2;
    /**
     * 左节点上的右节点客户号
     */
    private String right2;
    /**
     * 左节点上的左增值点
     */
    private int leftP2;
    /**
     * 左节点上的右增值点
     */
    private int rightP2;
    /**
     * 左节点上的分配数
     */
    private int mlmPoint2;

    /**
     * 右节点上的左节点数
     */
    private int leftCount3;
    /**
     * 右节点上的右节点数
     */
    private int rightCount3;
    /**
     * 右节点上的左节点客户号
     */
    private String left3;
    /**
     * 右节点上的右节点客户号
     */
    private String right3;
    /**
     * 右节点上的左增值点
     */
    private int leftP3;
    /**
     * 右节点上的右增值点
     */
    private int rightP3;
    /**
     * 右节点上的分配数
     */
    private int mlmPoint3;

    public String getDetailId() {
        return detailId;
    }

    public void setDetailId(String detailId) {
        this.detailId = detailId;
    }

    public String getTotalId() {
        return totalId;
    }

    public void setTotalId(String totalId) {
        this.totalId = totalId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public int getPv() {
        return pv;
    }

    public void setPv(int pv) {
        this.pv = pv;
    }

    public int getLeftCount1() {
        return leftCount1;
    }

    public void setLeftCount1(int leftCount1) {
        this.leftCount1 = leftCount1;
    }

    public int getRightCount1() {
        return rightCount1;
    }

    public void setRightCount1(int rightCount1) {
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

    public int getLeftP1() {
        return leftP1;
    }

    public void setLeftP1(int leftP1) {
        this.leftP1 = leftP1;
    }

    public int getRightP1() {
        return rightP1;
    }

    public void setRightP1(int rightP1) {
        this.rightP1 = rightP1;
    }

    public int getMlmPoint1() {
        return mlmPoint1;
    }

    public void setMlmPoint1(int mlmPoint1) {
        this.mlmPoint1 = mlmPoint1;
    }

    public int getLeftCount2() {
        return leftCount2;
    }

    public void setLeftCount2(int leftCount2) {
        this.leftCount2 = leftCount2;
    }

    public int getRightCount2() {
        return rightCount2;
    }

    public void setRightCount2(int rightCount2) {
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

    public int getLeftP2() {
        return leftP2;
    }

    public void setLeftP2(int leftP2) {
        this.leftP2 = leftP2;
    }

    public int getRightP2() {
        return rightP2;
    }

    public void setRightP2(int rightP2) {
        this.rightP2 = rightP2;
    }

    public int getMlmPoint2() {
        return mlmPoint2;
    }

    public void setMlmPoint2(int mlmPoint2) {
        this.mlmPoint2 = mlmPoint2;
    }

    public int getLeftCount3() {
        return leftCount3;
    }

    public void setLeftCount3(int leftCount3) {
        this.leftCount3 = leftCount3;
    }

    public int getRightCount3() {
        return rightCount3;
    }

    public void setRightCount3(int rightCount3) {
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

    public int getLeftP3() {
        return leftP3;
    }

    public void setLeftP3(int leftP3) {
        this.leftP3 = leftP3;
    }

    public int getRightP3() {
        return rightP3;
    }

    public void setRightP3(int rightP3) {
        this.rightP3 = rightP3;
    }

    public int getMlmPoint3() {
        return mlmPoint3;
    }

    public void setMlmPoint3(int mlmPoint3) {
        this.mlmPoint3 = mlmPoint3;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
