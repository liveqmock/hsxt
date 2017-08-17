/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.rp.bean;

import java.io.Serializable;

public class ReportsBankrollChange implements Serializable {
	
	private static final long serialVersionUID = 8258503883699649412L;

	/**
	 * 资金存量变动主键
	 */
	private String bankrollChangeId;
	
	/**
	 * 客户全局编号
	 */
	private String custId;
	
	/**
	 * 互生号
	 */
	private String hsResNo;
	
	/**
	 * 客户类型编码
	 */
	private int custType;
	
	/**
	 * 账户类型编码
	 */
	private String accType;
	
	/**
	 * 账户类型名称
	 */
	private String accName;
	
	/**
	 * 变动金额
	 */
	private String changeAmount;
	
	/**
	 * 变动后账户余额
	 */
	private String changeBalance;
	
	/**
	 * 变动时间
	 */
	private String changeDate;
	
	/** 创建时间 **/
	private String  createdDateTim;
	
	/** 更新时间**/
	private String  updatedDateTim;

	/**
	 * @return the 资金存量变动主键
	 */
	public String getBankrollChangeId() {
		return bankrollChangeId;
	}

	/**
	 * @param 资金存量变动主键 the bankrollChangeId to set
	 */
	public void setBankrollChangeId(String bankrollChangeId) {
		this.bankrollChangeId = bankrollChangeId;
	}

	/**
	 * @return the 客户全局编号
	 */
	public String getCustId() {
		return custId;
	}

	/**
	 * @param 客户全局编号 the custId to set
	 */
	public void setCustId(String custId) {
		this.custId = custId;
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
	 * @return the 客户类型编码
	 */
	public int getCustType() {
		return custType;
	}

	/**
	 * @param 客户类型编码 the custType to set
	 */
	public void setCustType(int custType) {
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
	 * @return the 账户类型名称
	 */
	public String getAccName() {
		return accName;
	}

	/**
	 * @param 账户类型名称 the accName to set
	 */
	public void setAccName(String accName) {
		this.accName = accName;
	}

	/**
	 * @return the 变动金额
	 */
	public String getChangeAmount() {
		return changeAmount;
	}

	/**
	 * @param 变动金额 the changeAmount to set
	 */
	public void setChangeAmount(String changeAmount) {
		this.changeAmount = changeAmount;
	}

	/**
	 * @return the 变动后账户余额
	 */
	public String getChangeBalance() {
		return changeBalance;
	}

	/**
	 * @param 变动后账户余额 the changeBalance to set
	 */
	public void setChangeBalance(String changeBalance) {
		this.changeBalance = changeBalance;
	}

	/**
	 * @return the 变动时间
	 */
	public String getChangeDate() {
		return changeDate;
	}

	/**
	 * @param 变动时间 the changeDate to set
	 */
	public void setChangeDate(String changeDate) {
		this.changeDate = changeDate;
	}

	/**
	 * @return the 创建时间
	 */
	public String getCreatedDateTim() {
		return createdDateTim;
	}

	/**
	 * @param 创建时间 the createdDateTim to set
	 */
	public void setCreatedDateTim(String createdDateTim) {
		this.createdDateTim = createdDateTim;
	}

	/**
	 * @return the 更新时间
	 */
	public String getUpdatedDateTim() {
		return updatedDateTim;
	}

	/**
	 * @param 更新时间 the updatedDateTim to set
	 */
	public void setUpdatedDateTim(String updatedDateTim) {
		this.updatedDateTim = updatedDateTim;
	}
	
}
