/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.uc.as.bean.common;

import java.io.Serializable;

import com.alibaba.fastjson.JSONObject;

/**
 * 
 * 
 * @Package: com.gy.hsxt.uc.as.bean.common  
 * @ClassName: AsPosEntParam 
 * @Description: TODO
 *
 * @author: lixuan 
 * @date: 2015-12-29 下午2:37:40 
 * @version V1.0
 */
public class AsPosEnt implements Serializable {
	private static final long serialVersionUID = -5500114472026321460L;
	String version ;
	/**
	 * 企业名称
	 */
	String entName;

	/**
	 * 企业类型
	 */
	String entType;
	
	
	/**
	 * 企业资源号
	 */
	String entResNo;	
	
	/**
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}
	/**
	 * @param version the version to set
	 */
	public void setVersion(String version) {
		this.version = version;
	}

	/**
	 * @return the 企业资源号
	 */
	public String getEntResNo() {
		return entResNo;
	}

	/**
	 * @param 企业资源号 the entResNo to set
	 */
	public void setEntResNo(String entResNo) {
		this.entResNo = entResNo;
	}

	/**
	 * @return the 企业名称
	 */
	public String getEntName() {
		return entName;
	}

	/**
	 * @param 企业名称
	 *            the entName to set
	 */
	public void setEntName(String entName) {
		this.entName = entName;
	}

	/**
	 * @return the 企业类型
	 */
	public String getEntType() {
		return entType;
	}

	/**
	 * @param 企业类型
	 *            the entType to set
	 */
	public void setEntType(String entType) {
		this.entType = entType;
	}

	public String toString(){
		return JSONObject.toJSONString(this);
	}
}
