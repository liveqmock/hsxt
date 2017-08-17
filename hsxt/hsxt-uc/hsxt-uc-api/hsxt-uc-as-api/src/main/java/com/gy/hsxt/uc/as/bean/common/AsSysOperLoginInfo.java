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
 * 系统操作员登录信息
 * 
 * @Package: com.gy.hsxt.uc.as.bean.common
 * @ClassName: AsConsumerrLoginInfo
 * @Description: TODO
 * 
 * @author: lixuan
 * @date: 2015-10-19 下午12:08:10
 * @version V1.0
 */
public class AsSysOperLoginInfo implements Serializable {

    private static final long serialVersionUID = 1583105696099995418L;
    /** 系统操作员账号 */
    private String userName;
    
    /** 登录密码  */
    private String loginPwd;
	/** 第二个登录密码 */
	 
    private String secondLoginPwd;
	/** 密码版本号 */
	 
    private String pwdVer;
	/** 登录IP */
	
    private String loginIp;
	/** 秘钥（随机token） */
	
    private String randomToken;
	/** 渠道类型 */
	 
    private ChannelTypeEnum channelType;
	
	/**
	 * @return the 第二个登录密码
	 */
	public String getSecondLoginPwd() {
		return secondLoginPwd;
	}

	/**
	 * @param 第二个登录密码 the secondLoginPwd to set
	 */
	public void setSecondLoginPwd(String secondLoginPwd) {
		this.secondLoginPwd = secondLoginPwd;
	}

	/**
	 * 随机token
	 * @return the randomToken
	 */
	public String getRandomToken() {
		return randomToken;
	}

	/**
	 * 随机token
	 * @param randomToken the randomToken to set
	 */
	public void setRandomToken(String randomToken) {
		this.randomToken = randomToken;
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

	/**
	 * 用户名
	 * @return
	 */
    public String getUserName() {
        return userName;
    }

    /**
     * 用户名
     * @param userName
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String toString(){
		return JSONObject.toJSONString(this);
	}
}
