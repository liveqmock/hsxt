package com.gy.hsxt.access.web.person.bean;
		
/***************************************************************************
 * <PRE>
 * Description 		: Token 安全令牌
 *
 * Project Name   	: hsxt-common
 *
 * Package Name     : com.gy.hsxt.common.beans
 *
 * File Name        : SecurityToken
 * 
 * Author           : LiZhi Peter
 *
 * Create Date      : 2015-9-1 下午4:09:01  
 *
 * Update User      : LiZhi Peter
 *
 * Update Date      : 2015-9-1 下午4:09:01
 *
 * UpdateRemark 	: 说明本次修改内容
 *
 * Version          : v0.0.1
 *
 * </PRE>
 ***************************************************************************/
public class SecurityToken {
	/** 客户号 */
	private String custId ;
	
	/** token*/
	private String token ;

	/**
	 * 构造方法
	 * @param custId
	 * @param token
	 */
	public SecurityToken(String custId,String token){
		this.custId = custId;
		this.token = token;
	}
	/**
	 * 获取客户号
	 * @return custId 客户号
	 */
	public String getCustId() {
		return custId;
	}

	/**
	 * 设置客户号
	 * @param custId 客户号
	 */
	public void setCustId(String custId) {
		this.custId = custId;
	}

	/**
	 * 获取token
	 * @return token token
	 */
	public String getToken() {
		return token;
	}

	/**
	 * 设置token
	 * @param token token
	 */
	public void setToken(String token) {
		this.token = token;
	}
	
	
}

	