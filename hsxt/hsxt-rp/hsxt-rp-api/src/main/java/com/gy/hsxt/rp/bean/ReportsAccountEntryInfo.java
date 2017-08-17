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

package com.gy.hsxt.rp.bean;

import java.io.Serializable;
import java.sql.Timestamp;


/** 
 * 分录信息接口对象
 * @Package: com.gy.hsxt.ac.bean  
 * @ClassName: AccountEntryInfo 
 * @Description: TODO
 * @author: weixz 
 * @date: 2015-9-22 上午9:26:48 
 * @version V1.0 
 

 */
public class ReportsAccountEntryInfo implements Serializable{
	

	private static final long serialVersionUID = -5805329249740375806L;

	/**	客户全局编号 */
	private   String       custID;
	
	/** 客户类型 */
	private   Integer      custType;
	
	/** 客户名称 */
	private   String		custName;
	
	/** 证件号 */
	private   String       custIdNo;
	
	/** 互生号 */
	private   String		hsResNo;
	
	/**	账户类型编码 */
	private   String       accType;
	
	/**	交易流水号	*/
	private   String	    transNo;
	
	/**	交易类型	*/
	private	   String		transType;
	
	/** 多个账户类型编码 */
	private String[]		accTypes;
	
	/**	开始时间 */
	private   String       beginDate;
	
	/**	结束时间 */
	private   String       endDate;
	
	/** 开始时间 */
    private   Timestamp    beginDateTim;
    
    /** 结束时间 */
    private   Timestamp    endDateTim;
	
	/** 业务类型 
	 * (0、全部，1：收入，2：支出)
	 * */
	private   Integer      businessType;
	
	 /**
     * 手机号
     */
    private   String       mobile;

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
	 * @return the 客户名称
	 */
	public String getCustName() {
		return custName;
	}

	/**
	 * @param 客户名称 the custName to set
	 */
	public void setCustName(String custName) {
		this.custName = custName;
	}

	/**
	 * @return the 证件号
	 */
	public String getCustIdNo() {
		return custIdNo;
	}

	/**
	 * @param 证件号 the custIdNo to set
	 */
	public void setCustIdNo(String custIdNo) {
		this.custIdNo = custIdNo;
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
	 * @return the 多个账户类型编码
	 */
	public String[] getAccTypes() {
		return accTypes;
	}

	/**
	 * @param 多个账户类型编码 the accTypes to set
	 */
	public void setAccTypes(String[] accTypes) {
		this.accTypes = accTypes;
	}

	/**
	 * @return the 创建时间
	 */
	public String getBeginDate() {
		return beginDate;
	}

	/**
	 * @param 创建时间 the beginDate to set
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
	 * @return the 业务类型
	 */
	public Integer getBusinessType() {
		return businessType;
	}

	/**
	 * @param 业务类型 the businessType to set
	 */
	public void setBusinessType(Integer businessType) {
		this.businessType = businessType;
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

    /**
     * @return the 手机号
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * @param 手机号 the mobile to set
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
	
	
	
}















