/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.uc.as.bean.consumer;

import java.io.Serializable;

/**
 * 消费者资源信息查询条件
 * 
 * @Package: com.gy.hsxt.uc.as.bean.consumer
 * @ClassName: AsQueryConsumerCondition
 * @Description: TODO
 * 
 * @author: huanggy
 * @date: 2016-1-11 下午4:08:31
 * @version V1.0
 */
public class AsQueryConsumerCondition implements Serializable {
	private static final long serialVersionUID = 2703997766533281224L;
	/** 企业互生号 */
	private String entResNo;
	/** 消费互生号 */
	private String perResNo;
	/** 消费者姓名 */
	private String realName;
	/** 基本状态1：启用、2：曾经登录、3：激活、4：活跃、5：休眠、6：沉淀、7：注销 */
	private Integer baseStatus;
	/** 实名状态1：未实名注册、2：已实名注册（有名字和身份证）、3:已实名认证 */
	private Integer realnameStatus;
	/** 开始查询时间（实名状态完成时间） */
	private String beginDate;
	/** 结束查询时间（实名状态完成时间） */
	private String endDate;
	/** 开始互生卡号 */
	private String beginCardNo;
	/** 结束互生卡号 */
	private String endCardNo;

	/**
	 * @return the 企业互生号
	 */
	public String getEntResNo() {
		return entResNo;
	}

	/**
	 * @param 企业互生号
	 *            the entResNo to set
	 */
	public void setEntResNo(String entResNo) {
		this.entResNo = entResNo;
	}

	/**
	 * @return the 消费互生号
	 */
	public String getPerResNo() {
		return perResNo;
	}

	/**
	 * @param 消费互生号
	 *            the perResNo to set
	 */
	public void setPerResNo(String perResNo) {
		this.perResNo = perResNo;
	}

	/**
	 * @return the 消费者姓名
	 */
	public String getRealName() {
		return realName;
	}

	/**
	 * @param 消费者姓名
	 *            the realName to set
	 */
	public void setRealName(String realName) {
		this.realName = realName;
	}

	/**
	 * @return the 基本状态1：启用、2：曾经登录、3：激活、4：活跃、5：休眠、6：沉淀、7：注销
	 */
	public Integer getBaseStatus() {
		return baseStatus;
	}

	/**
	 * @param 基本状态1
	 *            ：启用、2：曾经登录、3：激活、4：活跃、5：休眠、6：沉淀、7：注销 the baseStatus to set
	 */
	public void setBaseStatus(Integer baseStatus) {
		this.baseStatus = baseStatus;
	}

	/**
	 * @return the 实名状态1：未实名注册、2：已实名注册（有名字和身份证）、3:已实名认证
	 */
	public Integer getRealnameStatus() {
		return realnameStatus;
	}

	/**
	 * @param 实名状态1
	 *            ：未实名注册、2：已实名注册（有名字和身份证）、3:已实名认证 the realnameStatus to set
	 */
	public void setRealnameStatus(Integer realnameStatus) {
		this.realnameStatus = realnameStatus;
	}

	/**
	 * @return the 开始查询时间（实名状态完成时间）
	 */
	public String getBeginDate() {
		return beginDate;
	}

	/**
	 * @param 开始查询时间
	 *            （实名状态完成时间） the beginDate to set
	 */
	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	/**
	 * @return the 结束查询时间（实名状态完成时间）
	 */
	public String getEndDate() {
		return endDate;
	}

	/**
	 * @param 结束查询时间
	 *            （实名状态完成时间） the endDate to set
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return the 开始互生卡号
	 */
	public String getBeginCardNo() {
		return beginCardNo;
	}

	/**
	 * @param 开始互生卡号
	 *            the beginCardNo to set
	 */
	public void setBeginCardNo(String beginCardNo) {
		this.beginCardNo = beginCardNo;
	}

	/**
	 * @return the 结束互生卡号
	 */
	public String getEndCardNo() {
		return endCardNo;
	}

	/**
	 * @param 结束互生卡号
	 *            the endCardNo to set
	 */
	public void setEndCardNo(String endCardNo) {
		this.endCardNo = endCardNo;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
