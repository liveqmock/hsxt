/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.uc.as.bean.ent;

import java.io.Serializable;


/**
 * 查询企业条件对象
 * 
 * @Package: com.gy.hsxt.uc.as.bean.ent
 * @ClassName: AsQueryBelongEntCondition
 * @Description: TODO
 * 
 * @author: huanggy
 * @date: 2016-1-9 下午4:57:23
 * @version V1.0
 */
public class AsQueryEntCondition implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6216288724748479471L;
	/** 企业互生号 */
	String entResNo;
	/** 企业名称*/
	String custName;
	/** 联系人名称 */
	String contactor;
	/** 客户类型*/
	Integer custType;
	/** 开启系统结束日期 */
	String endDate;
	/** 开启系统日期*/
	String beginDate;
	/**
	 * @return the 企业互生号
	 */
	public String getEntResNo() {
		return entResNo;
	}
	/**
	 * @param 企业互生号 the entResNo to set
	 */
	public void setEntResNo(String entResNo) {
		this.entResNo = entResNo;
	}
	/**
	 * @return the 企业名称
	 */
	public String getCustName() {
		return custName;
	}
	/**
	 * @param 企业名称 the custName to set
	 */
	public void setCustName(String custName) {
		this.custName = custName;
	}
	/**
	 * @return the 联系人名称
	 */
	public String getContactor() {
		return contactor;
	}
	/**
	 * @param 联系人名称 the contactor to set
	 */
	public void setContactor(String contactor) {
		this.contactor = contactor;
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
	 * @return the 开启系统结束日期
	 */
	public String getEndDate() {
		return endDate;
	}
	/**
	 * @param 开启系统结束日期 the endDate to set
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	/**
	 * @return the 开启系统日期
	 */
	public String getBeginDate() {
		return beginDate;
	}
	/**
	 * @param 开启系统日期 the beginDate to set
	 */
	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}
	
	
}
