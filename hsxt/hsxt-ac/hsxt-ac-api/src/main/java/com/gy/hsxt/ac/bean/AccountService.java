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

package com.gy.hsxt.ac.bean;

import java.io.Serializable;

/** 
 * 服务记账对象
 * @Package: com.gy.hsxt.ac.bean  
 * @ClassName: AccountService 
 * @Description: TODO
 *
 * @author: weixz 
 * @date: 2015-11-2 下午5:11:23 
 * @version V1.0 
 

 */
public class AccountService implements Serializable{

    
    private static final long serialVersionUID = -6656369002815252575L;

    /** 客户全局编号 */
    private   String       custID;
    
    /** 互生号 */
    private   String       hsResNo;
    
    /** 交易系统 */
    private   String       transSys;
    
    /** 客户类型 */
    private   Integer      custType;
       
    /** 账户类型编码
     *  两个账户拼接的账户集字符串，以分隔符"|"分开
     *  第一个为扣减的账户
     *  第二个为增加的账户
     */
    private   String       accTypes;
       
    /** 交易类型 */
    private   String       transType;

    /** 交易流水号   */
    private   String       transNo;
    
    /**
     * 原始交易流水号
     */
    private   String       sourceTransNo;

    /** 金额 */
    private   String       amount;
    
    /**
     * 账户余额正负（1：正；2：负）
     */
    private   String		positiveNegative;
    
    

    /**
     * @return the 客户全局编号
     */
    public String getCustID() {
        return custID;
    }

    /**
     * @param 客户全局编号 the custID to set
     */
    public void setCustID(String custID) {
        this.custID = custID;
    }

    /**
     * @return the 互生号
     */
    public String getHsResNo() {
        return hsResNo;
    }

    /**
     * @param 互生号 the hsResNo to set
     */
    public void setHsResNo(String hsResNo) {
        this.hsResNo = hsResNo;
    }

    /**
     * @return the 客户类型
     */
    public Integer getCustType() {
        return custType;
    }

    /**
     * @param 客户类型 the custType to set
     */
    public void setCustType(Integer custType) {
        this.custType = custType;
    }

    /**
     * @return the 账户类型编码
     */
    public String getAccTypes() {
        return accTypes;
    }

    /**
     * @param 账户类型编码 the accTypes to set
     */
    public void setAccTypes(String accTypes) {
        this.accTypes = accTypes;
    }

    /**
     * @return the 交易类型
     */
    public String getTransType() {
        return transType;
    }

    /**
     * @param 交易类型 the transType to set
     */
    public void setTransType(String transType) {
        this.transType = transType;
    }

    /**
     * @return the 交易流水号
     */
    public String getTransNo() {
        return transNo;
    }

    /**
     * @param 交易流水号 the transNo to set
     */
    public void setTransNo(String transNo) {
        this.transNo = transNo;
    }

    /**
     * @return the 金额
     */
    public String getAmount() {
        return amount;
    }

    /**
     * @param 金额 the amount to set
     */
    public void setAmount(String amount) {
        this.amount = amount;
    }

    /**
     * @return the 交易系统
     */
    public String getTransSys() {
        return transSys;
    }

    /**
     * @param 交易系统 the transSys to set
     */
    public void setTransSys(String transSys) {
        this.transSys = transSys;
    }

	/**
	 * @return the 账户余额正负（1：正；2：负）
	 */
	public String getPositiveNegative() {
		return positiveNegative;
	}

	/**
	 * @param 账户余额正负（1：正；2：负） the positiveNegative to set
	 */
	public void setPositiveNegative(String positiveNegative) {
		this.positiveNegative = positiveNegative;
	}

	/**
	 * @return the 原始交易流水号
	 */
	public String getSourceTransNo() {
		return sourceTransNo;
	}

	/**
	 * @param 原始交易流水号 the sourceTransNo to set
	 */
	public void setSourceTransNo(String sourceTransNo) {
		this.sourceTransNo = sourceTransNo;
	}
    
}
