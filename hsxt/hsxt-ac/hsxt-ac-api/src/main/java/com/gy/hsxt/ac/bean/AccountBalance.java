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
 * 账户余额对象
 * @author 作者 : weixz
 * @ClassName: 类名:AccountBalance
 * @Description: TODO
 * @createDate 创建时间: 2015-8-25 下午2:35:52
 * @version 1.0
 */
public class AccountBalance implements Serializable{

	private static final long serialVersionUID = 7560727889135712302L;

	/** 客户全局编号 **/
	private String     custID;
	
	/** 账户类型编码 **/
	private String     accType;
	
	/** 互生号  **/
	private String     hsResNo;
	
	/** 客户类型 **/
	private Integer    custType;
	
	/** 账户余额 **/
	private String     accBalance;
	
	/** 账户状态 **/
	private Integer    accStatus;
	
	/** 创建时间 **/
	private String     createdDate;
	
	/** 更新时间**/
	private String     updatedDate;
	
	/** 创建时间 **/
	private Timestamp  createdDateTim;
	
	/** 更新时间**/
	private Timestamp  updatedDateTim;
	
	/** 客户类型分类（0：消费者类型，1：企业类型，2：平台类型）**/
    private Integer    custTypeCategory;
    
    /**
     * 保底值
     */
    private String		guaranteeIntegral;

    /** 账户是否可以为负数标识**/
    private int     accFlag;
    
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
	 * @return the 账户余额
	 */
	public String getAccBalance() {
		if(accBalance != null)
		{
			String a = "";
			if(accBalance.length()>1)
			{
				a = accBalance.substring(0, 1);
			}
			String b = "";
			if(accBalance.length()>2)
			{
				b = accBalance.substring(0, 2);
			}
			if(".".equals(a)){
				accBalance = 0 + accBalance;
			}
			if("-.".equals(b))
			{
				accBalance = a + 0 + accBalance.substring(1);
			}
		}
		return accBalance;
	}

	/**
	 * @param 账户余额 the accBalance to set
	 */
	public void setAccBalance(String accBalance) {
		this.accBalance = accBalance;
	}


	/**
	 * @return the createdDate
	 */
	public String getCreatedDate() {
		return createdDate;
	}

	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * @return the updatedDate
	 */
	public String getUpdatedDate() {
		return updatedDate;
	}

	/**
	 * @param updatedDate the updatedDate to set
	 */
	public void setUpdatedDate(String updatedDate) {
		this.updatedDate = updatedDate;
	}


	/**
	 * @return the 账户状态
	 */
	public Integer getAccStatus() {
		return accStatus;
	}

	/**
	 * @param 账户状态 the accStatus to set
	 */
	public void setAccStatus(Integer accStatus) {
		this.accStatus = accStatus;
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
     * @return the 客户类型分类（0：消费者类型，1：企业类型，2：平台类型）
     */
    public Integer getCustTypeCategory() {
        return custTypeCategory;
    }

    /**
     * @param 客户类型分类（0：消费者类型，1：企业类型，2：平台类型） the custTypeCategory to set
     */
    public void setCustTypeCategory(Integer custTypeCategory) {
        this.custTypeCategory = custTypeCategory;
    }

	/**
	 * @return the 保底值
	 */
	public String getGuaranteeIntegral() {
		return guaranteeIntegral;
	}

	/**
	 * @param 保底值 the guaranteeIntegral to set
	 */
	public void setGuaranteeIntegral(String guaranteeIntegral) {
		this.guaranteeIntegral = guaranteeIntegral;
	}

    /**
     * @return the 账户是否可以为负数标识
     */
    public int getAccFlag() {
        return accFlag;
    }

    /**
     * @param 账户是否可以为负数标识 the accFlag to set
     */
    public void setAccFlag(int accFlag) {
        this.accFlag = accFlag;
    }

	
}
