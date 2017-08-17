/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.uc.as.bean.operator;

import java.io.Serializable;

/**
 * 操作员分页查询条件
 * 
 * @Package: com.gy.hsxt.uc.as.bean.operator
 * @ClassName: AsQueryOperatorCondition
 * @Description: TODO
 * 
 * @author: huanggy
 * @date: 2016-1-12 下午4:46:41
 * @version V1.0
 */
public class AsQueryOperatorCondition implements Serializable {
	private static final long serialVersionUID = 4359028934063650351L;
	/** 企业客户号 必传 */
	private String entCustId;
	/** 用户名（员工账号） 选传 */
	private String userName;
	/** 真实姓名 选传 */
	private String realName;

	/**
	 * @return the 企业客户号必传
	 */
	public String getEntCustId() {
		return entCustId;
	}

	/**
	 * @param 企业客户号必传
	 *            the entCustId to set
	 */
	public void setEntCustId(String entCustId) {
		this.entCustId = entCustId;
	}

	/**
	 * @return the 用户名（员工账号）选传
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param 用户名
	 *            （员工账号）选传 the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the 真实姓名选传
	 */
	public String getRealName() {
		return realName;
	}

	/**
	 * @param 真实姓名选传
	 *            the realName to set
	 */
	public void setRealName(String realName) {
		this.realName = realName;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
