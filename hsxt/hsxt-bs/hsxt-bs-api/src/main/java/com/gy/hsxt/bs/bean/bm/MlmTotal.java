/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.bs.bean.bm;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;
import java.util.List;

/**
 * 增值数据汇总实体
 *
 * @Package :com.gy.hsxt.bs.bean.bm
 * @ClassName : MlmTotal
 * @Description : TODO
 * @Author : chenm
 * @Date : 2015/11/6 17:15
 * @Version V3.0.0.0
 */
public class MlmTotal implements Serializable {

    private static final long serialVersionUID = 4836489064712166870L;

    /**
     * 主键/业务流水号
     */
    private String totalId;

    /**
     * 企业互生号
     */
    private String resNo;

    /**
     * 主节点客户号
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
     * 积分总数
     */
    private String points;

    /**
     * 总笔数
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

    /**
     * 增值详情
     */
    private List<MlmDetail> details;

    public String getTotalId() {
        return totalId;
    }

    public void setTotalId(String totalId) {
        this.totalId = totalId;
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

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
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

    public List<MlmDetail> getDetails() {
        return details;
    }

    public void setDetails(List<MlmDetail> details) {
        this.details = details;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
