/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/

package com.gy.hsxt.bp.bean;

import java.io.Serializable;
import java.sql.Timestamp;

/** 
 * 协议费用参数管理
 * @Package: com.gy.hsxt.bp.bean  
 * @ClassName: AgreeFeeParamManager 
 * @Description: TODO
 *
 * @author: weixz 
 * @date: 2015-11-24 下午3:10:06 
 * @version V1.0 
 

 */
public class BusinessAgreeFee implements Serializable{

    private static final long serialVersionUID = -6773224582261041083L;

    /** 协议费用ID **/
    private String     agreeFeeId;
    
    /** 协议费用编号 **/
    private String     agreeFeeCode;
    
    /** 协议费用名称 **/
    private String     agreeFeeName;
    
    /** 协议代码 **/
    private String     agreeCode;
    
    /** 协议名称 **/
    private String     agreeName;
    
    /** 缴费期限(不超过30天)**/
    private int        payPeriod;
    
    /** 计费类型：
        0：无
        TIMES:次
        DAILY：天
        WEEKLY：周
        MONTHLY：月
        ANNUAL：年 
    **/
    private String     billingType;
    
    /** 扣费类型：
        0：无
        TIMES:次
        DAILY：天
        WEEKLY：周
        MONTHLY：月
        ANNUAL：年
     **/
    private String     chargingType;
    
    /** 资费类型：
        FIXED：固定金额资费
        RATIO：资费比例
     **/
    private String     feeType;
    
    /** 货币代码   币种 
     *  000：互生币
     *  001：积分 
     *  156：人民币
     **/
    private String     currencyCode;
    
    /** 最小金额  **/
    private String     fromAmount;
    
    /** 最大金额  **/
    private String     toAmount;
    
    /** 费用金额  **/
    private String     feeAmount;
    
    /** 资费比例  **/
    private String     feeRatio;
    
    /** 激活状态: Y：是；N：否  **/
    private String     isActive;
    
    /** 创建者 **/
    private String     createdby;
    
    /** 创建时间 **/
    private String     createdDate;
       
    /** 更新者 **/
    private String     updatedby;
    
    /** 更新时间**/
    private String     updatedDate;
    
    /** 创建时间 **/
    private Timestamp  createdDateTim;
    
    /** 更新时间**/
    private Timestamp  updatedDateTim;

    
    /**
     * @return the 协议费用ID
     */
    public String getAgreeFeeId() {
        return agreeFeeId;
    }

    /**
     * @param 协议费用ID the agreeFeeId to set
     */
    public void setAgreeFeeId(String agreeFeeId) {
        this.agreeFeeId = agreeFeeId;
    }

    /**
     * @return the 协议费用编号
     */
    public String getAgreeFeeCode() {
        return agreeFeeCode;
    }

    /**
     * @param 协议费用编号 the agreeFeeCode to set
     */
    public void setAgreeFeeCode(String agreeFeeCode) {
        this.agreeFeeCode = agreeFeeCode;
    }

    /**
     * @return the 协议费用名称
     */
    public String getAgreeFeeName() {
        return agreeFeeName;
    }

    /**
     * @param 协议费用名称 the agreeFeeName to set
     */
    public void setAgreeFeeName(String agreeFeeName) {
        this.agreeFeeName = agreeFeeName;
    }

    /**
     * @return the 协议代码
     */
    public String getAgreeCode() {
        return agreeCode;
    }

    /**
     * @param 协议代码 the agreeCode to set
     */
    public void setAgreeCode(String agreeCode) {
        this.agreeCode = agreeCode;
    }

    /**
     * @return the 缴费期限(不超过30天)
     */
    public int getPayPeriod() {
        return payPeriod;
    }

    /**
     * @param 缴费期限(不超过30天) the payPeriod to set
     */
    public void setPayPeriod(int payPeriod) {
        this.payPeriod = payPeriod;
    }

    /**
     * @return the 计费类型：0：无TIMES:次DAILY：天WEEKLY：周MONTHLY：月ANNUAL：年
     */
    public String getBillingType() {
        return billingType;
    }

    /**
     * @param 计费类型：0：无TIMES:次DAILY：天WEEKLY：周MONTHLY：月ANNUAL：年 the billingType to set
     */
    public void setBillingType(String billingType) {
        this.billingType = billingType;
    }

    /**
     * @return the 扣费类型：0：无TIMES:次DAILY：天WEEKLY：周MONTHLY：月ANNUAL：年
     */
    public String getChargingType() {
        return chargingType;
    }

    /**
     * @param 扣费类型：0：无TIMES:次DAILY：天WEEKLY：周MONTHLY：月ANNUAL：年 the chargingType to set
     */
    public void setChargingType(String chargingType) {
        this.chargingType = chargingType;
    }

    /**
     * @return the 资费类型：FIXED：固定金额资费RATIO：资费比例
     */
    public String getFeeType() {
        return feeType;
    }

    /**
     * @param 资费类型：FIXED：固定金额资费RATIO：资费比例 the feeType to set
     */
    public void setFeeType(String feeType) {
        this.feeType = feeType;
    }

    /**
     * @return the 货币代码币种000：互生币001：积分156：人民币
     */
    public String getCurrencyCode() {
        return currencyCode;
    }

    /**
     * @param 货币代码币种000：互生币001：积分156：人民币 the currencyCode to set
     */
    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    /**
     * @return the 最小金额
     */
    public String getFromAmount() {
        return fromAmount;
    }

    /**
     * @param 最小金额 the fromAmount to set
     */
    public void setFromAmount(String fromAmount) {
        this.fromAmount = fromAmount;
    }

    /**
     * @return the 最大金额
     */
    public String getToAmount() {
        return toAmount;
    }

    /**
     * @param 最大金额 the toAmount to set
     */
    public void setToAmount(String toAmount) {
        this.toAmount = toAmount;
    }

    /**
     * @return the 费用金额
     */
    public String getFeeAmount() {
        return feeAmount;
    }

    /**
     * @param 费用金额 the feeAmount to set
     */
    public void setFeeAmount(String feeAmount) {
        this.feeAmount = feeAmount;
    }

    /**
     * @return the 资费比例
     */
    public String getFeeRatio() {
        return feeRatio;
    }

    /**
     * @param 资费比例 the feeRatio to set
     */
    public void setFeeRatio(String feeRatio) {
        this.feeRatio = feeRatio;
    }

    /**
     * @return the 激活状态:Y：是；N：否
     */
    public String getIsActive() {
        return isActive;
    }

    /**
     * @param 激活状态:Y：是；N：否 the isActive to set
     */
    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    /**
     * @return the 创建者
     */
    public String getCreatedby() {
        return createdby;
    }

    /**
     * @param 创建者 the createdby to set
     */
    public void setCreatedby(String createdby) {
        this.createdby = createdby;
    }

    /**
     * @return the 创建时间
     */
    public String getCreatedDate() {
        return createdDate;
    }

    /**
     * @param 创建时间 the createdDate to set
     */
    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    /**
     * @return the 更新者
     */
    public String getUpdatedby() {
        return updatedby;
    }

    /**
     * @param 更新者 the updatedby to set
     */
    public void setUpdatedby(String updatedby) {
        this.updatedby = updatedby;
    }

    /**
     * @return the 更新时间
     */
    public String getUpdatedDate() {
        return updatedDate;
    }

    /**
     * @param 更新时间 the updatedDate to set
     */
    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

    /**
     * @return the 创建时间
     */
    public Timestamp getCreatedDateTim() {
        return createdDateTim;
    }

    /**
     * @param 创建时间 the createdDateTim to set
     */
    public void setCreatedDateTim(Timestamp createdDateTim) {
        this.createdDateTim = createdDateTim;
    }

    /**
     * @return the 更新时间
     */
    public Timestamp getUpdatedDateTim() {
        return updatedDateTim;
    }

    /**
     * @param 更新时间 the updatedDateTim to set
     */
    public void setUpdatedDateTim(Timestamp updatedDateTim) {
        this.updatedDateTim = updatedDateTim;
    }

    /**
     * @return the 协议名称
     */
    public String getAgreeName() {
        return agreeName;
    }

    /**
     * @param 协议名称 the agreeName to set
     */
    public void setAgreeName(String agreeName) {
        this.agreeName = agreeName;
    }

    
}
