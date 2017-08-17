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
 * @ClassName : BmlmDetail
 * @Description : 再增值积分详情
 * @Author : chenm
 * @Date : 2015/11/3 20:17
 * @Version V3.0.0.0
 */
public class BmlmDetail implements Serializable {

    private static final long serialVersionUID = 781142180798679975L;

    /**
     * 主键/业务流水号
     */
    private String bmlmId;

    /**
     * 企业互生号
     */
    private String resNo;
    /**
     * 企业客户号
     */
    private String custId;

    /**
     * 汇总开始时间
     */
    private String calcStartTime;

    /**
     * 汇总结束时间
     */
    private String calcEndTime;
    /**
     * 积分参考值
     */
    private String pointREF;
    /**
     * 分配基数
     */
    private String baseVal;

    /**
     * 百位分配比
     */
    private String percent;

    /**
     * 再增值积分
     */
    private String bmlmPoint;

    /**
     * 汇总笔数 (再增值可能是多笔汇总的和)
     */
    private int totalRow;

    /**
     * 计算税率
     */
    private String taxRate;

    /**
     * 税金
     */
    private String tax;


    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    private String createdDate;

    public String getBmlmId() {
        return bmlmId;
    }

    public void setBmlmId(String bmlmId) {
        this.bmlmId = bmlmId;
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

    public String getCalcStartTime() {
        return calcStartTime;
    }

    public void setCalcStartTime(String calcStartTime) {
        this.calcStartTime = calcStartTime;
    }

    public String getCalcEndTime() {
        return calcEndTime;
    }

    public void setCalcEndTime(String calcEndTime) {
        this.calcEndTime = calcEndTime;
    }

    public String getPointREF() {
        return pointREF;
    }

    public void setPointREF(String pointREF) {
        this.pointREF = pointREF;
    }

    public String getBaseVal() {
        return baseVal;
    }

    public void setBaseVal(String baseVal) {
        this.baseVal = baseVal;
    }

    public String getPercent() {
        return percent;
    }

    public void setPercent(String percent) {
        this.percent = percent;
    }

    public String getBmlmPoint() {
        return bmlmPoint;
    }

    public void setBmlmPoint(String bmlmPoint) {
        this.bmlmPoint = bmlmPoint;
    }

    public int getTotalRow() {
        return totalRow;
    }

    public void setTotalRow(int totalRow) {
        this.totalRow = totalRow;
    }

    public String getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(String taxRate) {
        this.taxRate = taxRate;
    }

    public String getTax() {
        return tax;
    }

    public void setTax(String tax) {
        this.tax = tax;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
