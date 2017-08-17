package com.gy.hsxt.uc.as.bean.consumer;

import java.io.Serializable;
import java.sql.Timestamp;

import com.alibaba.fastjson.JSONObject;

/**
 * 
 * 
 * @Package: com.gy.hsxt.uc.as.bean.consumer  
 * @ClassName: AsCardHolder 
 * @Description: TODO
 *
 * @author: tianxh
 * @date: 2015-11-9 下午9:28:13 
 * @version V1.0
 */
public class AsNewMobileInfo implements Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = -7508277974280643880L;

	/** 客户号 */
    private String perCustId;
    
    /** 新的手机号码 */
    private String newMobile;
    
    /** 短信验证码 */
    private String smsCode;
    
    /** AES秘钥 */
    private String secretKey;
    
    /** AES登录密码 */
    private String loginPwd;

	public String getPerCustId() {
		return perCustId;
	}

	public void setPerCustId(String perCustId) {
		this.perCustId = perCustId;
	}

	public String getNewMobile() {
		return newMobile;
	}

	public void setNewMobile(String newMobile) {
		this.newMobile = newMobile;
	}

	public String getSmsCode() {
		return smsCode;
	}

	public void setSmsCode(String smsCode) {
		this.smsCode = smsCode;
	}

	public String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	public String getLoginPwd() {
		return loginPwd;
	}

	public void setLoginPwd(String loginPwd) {
		this.loginPwd = loginPwd;
	}
  
}