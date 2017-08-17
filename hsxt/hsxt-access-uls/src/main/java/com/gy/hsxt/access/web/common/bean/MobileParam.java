/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.access.web.common.bean;

import java.io.Serializable;

public class MobileParam implements Serializable{
    private static final long serialVersionUID = 5108367074615778743L;
    /** 手机号码 */
    private String mobile;
    
    /**短信验证码 */
    private String smsCode;
    
    /**用户账号   非持卡人填手机号码，持卡人填互生号 ，企业管理员填操作员账号*/
    private String userName; 
    
    /**企业互生号 仅企业管理员填 */
    private String entResNo; 
    
    /**用户类型  1:非持卡人  2:持卡人 3：企业管理员 4：系统用户 */
    private String userType;
    
    /**新的登录密码 */
    private String newLoginPwd;
    
    private String email;
    
	private String secretKey;
	
	private Integer custType;
	
	
	

    
    
    public Integer getCustType() {
		return custType;
	}

	public void setCustType(Integer custType) {
		this.custType = custType;
	}

	/**
     * 昵称
     */
    String nickname;
    
    /**
     * 短信模板类别 
     * 1：手机短信验证码发送（包含非持卡人注册、持卡人验证手机、企业验证手机）
     * 2：登录密码重置短信发送（包含非持卡人重置登录密码、持卡人重置登录密码、企业管理员重置登录密码）
     * 3：交易密码重置授权码短信发送模板(申请成功) （包含企业重置交易密码时授权码发送） 
     * 4：交易密码重置授权码短信发送模板（申请驳回）（包含企业重置交易面时授权码发送）
     * */
    private String busiType;
    
    /**客户类型 */
    private String custId;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getSmsCode() {
        return smsCode;
    }

    public void setSmsCode(String smsCode) {
        this.smsCode = smsCode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEntResNo() {
        return entResNo;
    }

    public void setEntResNo(String entResNo) {
        this.entResNo = entResNo;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }




    public String getNewLoginPwd() {
		return newLoginPwd;
	}

	public void setNewLoginPwd(String newLoginPwd) {
		this.newLoginPwd = newLoginPwd;
	}

	public String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getBusiType() {
        return busiType;
    }

    public void setBusiType(String busiType) {
        this.busiType = busiType;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    
}
