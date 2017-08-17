/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */

package com.gy.hsxt.uc.as.bean.consumer;

import java.io.Serializable;

import com.alibaba.fastjson.JSONObject;

/**
 * 
 * @Package: com.gy.hsxt.uc.as.bean.consumer
 * @ClassName: AsNoCardHolder
 * @Description: 非持卡人注册
 * 
 * @author: tianxh
 * @date: 2015-10-19 下午4:51:39
 * @version V1.0
 */
public class AsRegNoCardHolder implements Serializable {

	private static final long serialVersionUID = -818462273903876119L;

	/**
	 * 非持卡人手机号码
	 */
	private String mobile;

	/** 
	 * 非持卡人登录密码
	 */
	private String loginPwd;

	/**
	 * 短信验证码
	 */
	private String smsCode;

	/**
	 * 昵称
	 */
	private String nickname;

	/**
	 * 邮箱
	 */
	private String email;

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getLoginPwd() {
		return loginPwd;
	}

	public void setLoginPwd(String loginPwd) {
		this.loginPwd = loginPwd;
	}

	public String getSmsCode() {
		return smsCode;
	}

	public void setSmsCode(String smsCode) {
		this.smsCode = smsCode;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	public String toString(){
        return JSONObject.toJSONString(this);
    }
}
