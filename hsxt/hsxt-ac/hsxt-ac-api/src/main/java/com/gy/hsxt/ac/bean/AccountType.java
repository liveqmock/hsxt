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
import java.sql.Timestamp;

/**
 * 账户类型对象
 * @author 作者 : weixz
 * @ClassName: 类名: AccountType
 * @Description: TODO
 * @createDate 创建时间: 2015-8-24 下午4:47:29
 * @version 1.0
 */
public class AccountType implements Serializable{
	
	private static final long serialVersionUID = 7397787573807703569L;

	/** 账户类型编码 **/
	private String     accType;
	
	/** 账户类型名称 **/
	private String     accName;
	
	/** 币种 **/
	private String     currencyCode;
	
	/** 账户类型描述  **/
	private String     accTypeDescription;
	
	/** 创建时间 **/
	private String     createdDate;
	
	/** 更新时间**/
	private String     updatedDate;

	/** 开始时间**/
    private String     beginDate;
    
    /** 结束时间**/
    private String     endDate;
    
    /** 开始时间**/
    private Timestamp     beginDateTim;
    
    /** 结束时间**/
    private Timestamp     endDateTim;
	
	/**
	 * 获取账户类型编码
	 * 
	 * @return accType 账户类型编码
	 */
	public String getAccType() {
		return accType;
	}

	/**
	 * 设置账户类型编码
	 * 
	 * @param accType 账户类型编码
	 */
	public void setAccType(String accType) {
		this.accType = accType;
	}

	/**
	 * 获取账户类型名称 
	 * 
	 * @return accName 账户类型名称 
	 */
	public String getAccName() {
		return accName;
	}

	/**
	 * 设置账户类型名称 
	 * 
	 * @param accName 账户类型名称 
	 */
	public void setAccName(String accName) {
		this.accName = accName;
	}

	/**
	 * 获取账户类型编码
	 * 
	 * @return currencyCode 账户类型编码
	 */
	public String getCurrencyCode() {
		return currencyCode;
	}

	/**
	 * 设置账户类型编码
	 * 
	 * @param currencyCode 账户类型编码
	 */
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	/**
	 * 获取账户类型描述
	 * 
	 * @return accTypeDescription 账户类型描述
	 */
	public String getAccTypeDescription() {
		return accTypeDescription;
	}

	/**
	 * 设置账户类型描述
	 * 
	 * @param accTypeDescription 账户类型描述
	 */
	public void setAccTypeDescription(String accTypeDescription) {
		this.accTypeDescription = accTypeDescription;
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
     * @return the 开始时间
     */
    public String getBeginDate() {
        return beginDate;
    }

    /**
     * @param 开始时间 the beginDate to set
     */
    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    /**
     * @return the 结束时间
     */
    public String getEndDate() {
        return endDate;
    }

    /**
     * @param 结束时间 the endDate to set
     */
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    /**
     * @return the 开始时间
     */
    public Timestamp getBeginDateTim() {
        return beginDateTim;
    }

    /**
     * @param 开始时间 the beginDateTim to set
     */
    public void setBeginDateTim(Timestamp beginDateTim) {
        this.beginDateTim = beginDateTim;
    }

    /**
     * @return the 结束时间
     */
    public Timestamp getEndDateTim() {
        return endDateTim;
    }

    /**
     * @param 结束时间 the endDateTim to set
     */
    public void setEndDateTim(Timestamp endDateTim) {
        this.endDateTim = endDateTim;
    }

	
}
