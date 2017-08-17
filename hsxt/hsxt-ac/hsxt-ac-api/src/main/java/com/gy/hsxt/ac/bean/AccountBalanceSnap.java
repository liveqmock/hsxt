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
 *  账户余额快照
 *  @Author          : weixz
 *  @Project Name    : hsxt-ac-api
 *  @Package Name    : com.gy.hsxt.ac.bean
 *  @File Name       : Tacaccountbalancesnap.java
 *  @Description     : TODO
 *  @Creation Date   : 2015-8-25
 *  @version V1.0
 * 
 */
public class AccountBalanceSnap implements Serializable{
 
	private static final long serialVersionUID = 9178496296576667536L;

	/**	余额快照主键 */
	private String        accSnapID;
	
	/**	客户全局编号 */
	private String        custID;
	
	/**	互生号 */
	private String        hsResNo;
	
	/**	客户类型 */
	private Integer       custType;
	
	/**账户类型编码 */
	private String        accType;
	
	/**变更前账户余额 */
	private String    	  accBalanceOld;
	
	/**变更后账户余额 */
	private String    	  accBalanceNew;
	 
	/**	创建时间 */
	private String     	  createdDate;
	
	/**	创建时间 */
	private Timestamp     createdDateTim;
	
	/**	更新时间 */
	private String        updatedDate;
	
	/**	更新时间 */
	private Timestamp     updatedDateTim;
	
	/**快照记录时间 */
	private  String       snapDate;
	
	/**快照记录时间 */
	private  Timestamp    snapDateTim;
	
	/**变更类型（1：单笔；2：批量）*/
	private Integer       changeType;
	
	/**关联ID */
	private String        changeRecordID;
	
	/** 变动金额 **/
	private String		   amount;
	
	/** 客户类型分类（0：消费者类型，1：企业类型，2：平台类型）**/
    private Integer    custTypeCategory;

	/**
	 * @return the 余额快照主键
	 */
	public String getAccSnapID() {
		return accSnapID;
	}

	/**
	 * @param 余额快照主键 the accSnapID to set
	 */
	public void setAccSnapID(String accSnapID) {
		this.accSnapID = accSnapID;
	}

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
	 * @return the 变更前账户余额
	 */
	public String getAccBalanceOld() {
		if(accBalanceOld != null)
		{
			String a = "";
			if(accBalanceOld.length()>1)
			{
				a = accBalanceOld.substring(0, 1);
			}
			String b = "";
			if(accBalanceOld.length()>2)
			{
				b = accBalanceOld.substring(0, 2);
			}
			if(".".equals(a)){
				accBalanceOld = 0 + accBalanceOld;
			}
			if("-.".equals(b))
			{
				accBalanceOld = a + 0 + accBalanceOld.substring(1);
			}
		}
		return accBalanceOld;
	}

	/**
	 * @param 变更前账户余额 the accBalanceOld to set
	 */
	public void setAccBalanceOld(String accBalanceOld) {
		this.accBalanceOld = accBalanceOld;
	}

	/**
	 * @return the 变更后账户余额
	 */
	public String getAccBalanceNew() {
		if(accBalanceNew != null)
		{
			String a = "";
			if(accBalanceNew.length()>1)
			{
				a = accBalanceNew.substring(0, 1);
			}
			String b = "";
			if(accBalanceNew.length()>2)
			{
				b = accBalanceNew.substring(0, 2);
			}
			if(".".equals(a)){
				accBalanceNew = 0 + accBalanceNew;
			}
			if("-.".equals(b))
			{
				accBalanceNew = a + 0 + accBalanceNew.substring(1);
			}
		}
		return accBalanceNew;
	}

	/**
	 * @param 变更后账户余额 the accBalanceNew to set
	 */
	public void setAccBalanceNew(String accBalanceNew) {
		this.accBalanceNew = accBalanceNew;
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
	 * @return the 快照记录时间
	 */
	public String getSnapDate() {
		return snapDate;
	}

	/**
	 * @param 快照记录时间 the snapDate to set
	 */
	public void setSnapDate(String snapDate) {
		this.snapDate = snapDate;
	}

	/**
	 * @return the 变更类型（1：单笔；2：批量）
	 */
	public Integer getChangeType() {
		return changeType;
	}

	/**
	 * @param 变更类型（1：单笔；2：批量） the changeType to set
	 */
	public void setChangeType(Integer changeType) {
		this.changeType = changeType;
	}

	/**
	 * @return the 关联ID
	 */
	public String getChangeRecordID() {
		return changeRecordID;
	}

	/**
	 * @param 关联ID the changeRecordID to set
	 */
	public void setChangeRecordID(String changeRecordID) {
		this.changeRecordID = changeRecordID;
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
	 * 获取更新时间
	 * @return updatedDateTim 更新时间
	 */
	public Timestamp getUpdatedDateTim() {
		return updatedDateTim;
	}

	/**
	 * 设置更新时间
	 * @param updatedDateTim 更新时间
	 */
	public void setUpdatedDateTim(Timestamp updatedDateTim) {
		this.updatedDateTim = updatedDateTim;
	}

	/**
	 * 获取快照记录时间
	 * @return snapDateTim 快照记录时间
	 */
	public Timestamp getSnapDateTim() {
		return snapDateTim;
	}

	/**
	 * 设置快照记录时间
	 * @param snapDateTim 快照记录时间
	 */
	public void setSnapDateTim(Timestamp snapDateTim) {
		this.snapDateTim = snapDateTim;
	}

	/**
	 * @return the 变动金额
	 */
	public String getAmount() {
		return amount;
	}

	/**
	 * @param 变动金额 the amount to set
	 */
	public void setAmount(String amount) {
		this.amount = amount;
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
	
}
