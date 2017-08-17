/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.uc.as.bean.common;

import java.io.Serializable;

import com.alibaba.fastjson.JSONObject;
import com.gy.hsxt.uc.as.bean.enumerate.ChannelTypeEnum;

/**
 * 操作员登录信息
 * 
 * @Package: com.gy.hsxt.uc.as.bean.common
 * @ClassName: AsOperatorLoginInfo
 * @Description: TODO
 * 
 * @author: lixuan
 * @date: 2015-10-19 下午12:18:26
 * @version V1.0
 */
public class AsOperatorLoginInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5527537953297720722L;
	/**
	 * 资源号
	 */
	String resNo;
	/**
	 * 用户名
	 */
	String userName;
	/**
	 * 登录密码
	 */
	String loginPwd;
	/**
	 * 密码版本
	 */
	String pwdVer;
	/**
	 * 登录IP
	 */
	String loginIp;
	/**
	 * 随机token
	 */
	String randomToken;
	/**
	 * 频道类型
	 */
	ChannelTypeEnum channelType;

	/**
	 * 企业客户类型1 持卡人;2 成员企业;3 托管企业;4 服务公司;5 管理公司;6 地区平台;7 总平台; 8 其它地区平台 11：操作员
	 * 21：POS机；22：积分刷卡器；23：消费刷卡器；24：平板；25：云台 51 非持卡人; 52 非互生格式化企业
	 */
	String custType;

	/**
	 * 随机token
	 * 
	 * @return the randomToken
	 */
	public String getRandomToken() {
		return randomToken;
	}

	/**
	 * 随机token
	 * 
	 * @param randomToken
	 *            the randomToken to set
	 */
	public void setRandomToken(String randomToken) {
		this.randomToken = randomToken;
	}

	/**
	 * 操作员登录用户名
	 * 
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * 操作员登录用户名
	 * 
	 * @param userName
	 *            the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * 企业互生号
	 * 
	 * @return
	 */
	public String getResNo() {
		return resNo;
	}

	/**
	 * 企业互生号
	 * 
	 * @param resNo
	 */
	public void setResNo(String resNo) {
		this.resNo = resNo;
	}

	/**
	 * 登录密码
	 * 
	 * @return
	 */
	public String getLoginPwd() {
		return loginPwd;
	}

	/**
	 * 登录密码
	 * 
	 * @param loginPwd
	 */
	public void setLoginPwd(String loginPwd) {
		this.loginPwd = loginPwd;
	}

	/**
	 * 密码版本号
	 * 
	 * @return
	 */
	public String getPwdVer() {
		return pwdVer;
	}

	/**
	 * 密码版本号
	 * 
	 * @param pwdVer
	 */
	public void setPwdVer(String pwdVer) {
		this.pwdVer = pwdVer;
	}

	/**
	 * 登录IP
	 * 
	 * @return
	 */
	public String getLoginIp() {
		return loginIp;
	}

	/**
	 * 登录IP
	 * 
	 * @param loginIp
	 */
	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	/**
	 * 渠道类型
	 * 
	 * @return
	 */
	public ChannelTypeEnum getChannelType() {
		return channelType;
	}

	/**
	 * 渠道类型
	 * 
	 * @param channelType
	 */
	public void setChannelType(ChannelTypeEnum channelType) {
		this.channelType = channelType;
	}

	public String toString() {
		return JSONObject.toJSONString(this);
	}

	public String getCustType() {
		return custType;
	}

	public void setCustType(String custType) {
		this.custType = custType;
	}
}
