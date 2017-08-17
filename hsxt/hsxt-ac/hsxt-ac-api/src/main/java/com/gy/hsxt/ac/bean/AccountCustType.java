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
 * 客户和账户类型关系对象
 * @author 作者 : weixz
 * @ClassName: 类名:CustAccType
 * @Description: TODO
 * @createDate 创建时间: 2015-8-25 下午2:26:52
 * @version 1.0 
 */
public class AccountCustType implements Serializable{

	private static final long serialVersionUID = 5545985054452568538L;

	/** 客户类型 **/
	private Integer    custType;
	
	/** 账户类型编码 **/
	private String     accType;
	
	/**　账户余额最小值**/
	private String     balanceMin;
	
	/**　账户余额最大值**/
	private String     balanceMax;

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
	public String getAccType() {
		return accType;
	}

	/**
	 * @param 账户类型编码 the accType to set
	 */
	public void setAccType(String accType) {
		this.accType = accType;
	}
	
	/**
	 * @return the 　账户余额最小值
	 */
	public String getBalanceMin() {
		return balanceMin;
	}

	/**
	 * @param 　账户余额最小值 the balanceMin to set
	 */
	public void setBalanceMin(String balanceMin) {
		this.balanceMin = balanceMin;
	}

	/**
	 * @return the 　账户余额最大值
	 */
	public String getBalanceMax() {
		return balanceMax;
	}

	/**
	 * @param 　账户余额最大值 the balanceMax to set
	 */
	public void setBalanceMax(String balanceMax) {
		this.balanceMax = balanceMax;
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


