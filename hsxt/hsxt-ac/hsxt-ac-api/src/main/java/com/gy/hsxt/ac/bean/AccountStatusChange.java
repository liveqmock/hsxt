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
 * 账户状态变更对象
 * @author 作者 : weixz
 * @ClassName: 类名:AccountStatusChange
 * @Description: TODO
 * @createDate 创建时间: 2015-8-25 下午4:25:22
 * @version 1.0 
 */
public class AccountStatusChange implements Serializable{
	
	private static final long serialVersionUID = -5167783395614079038L;

	/** 客户全局编号 **/
	private String     custID;
	
	/** 互生号 **/
	private String     hsResNo;
	
	/** 账户类型编码 **/
	private String     accType;
	
	/** 变更前账户状态 **/
	private Integer    accStatusOld;
	
	/** 变更后账户状态 **/
	private Integer    accStatusNew;
	
	/** 变更描述 **/
	private String     remarks;
	
	/** 变更人由谁变更，值为用户的主键ID **/	
	private String     updatedby;
	
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
	 * @return the 变更前账户状态
	 */
	public Integer getAccStatusOld() {
		return accStatusOld;
	}

	/**
	 * @param 变更前账户状态 the accStatusOld to set
	 */
	public void setAccStatusOld(Integer accStatusOld) {
		this.accStatusOld = accStatusOld;
	}

	/**
	 * @return the 变更后账户状态
	 */
	public Integer getAccStatusNew() {
		return accStatusNew;
	}

	/**
	 * @param 变更后账户状态 the accStatusNew to set
	 */
	public void setAccStatusNew(Integer accStatusNew) {
		this.accStatusNew = accStatusNew;
	}

	/**
	 * @return the 变更描述
	 */
	public String getRemarks() {
		return remarks;
	}

	/**
	 * @param 变更描述 the remarks to set
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	/**
	 * @return the 变更人由谁变更，值为用户的主键ID
	 */
	public String getUpdatedby() {
		return updatedby;
	}

	/**
	 * @param 变更人由谁变更，值为用户的主键ID the updatedby to set
	 */
	public void setUpdatedby(String updatedby) {
		this.updatedby = updatedby;
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
    };

	
	
}
