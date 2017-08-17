/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.access.web.common.bean;

import java.io.Serializable;

public class EmailParam implements Serializable{
    private static final long serialVersionUID = 1137683752009801663L;
    
    /**邮箱 */
    private String email;
    
    /**AES 秘钥 */
    private String secretKey;
    
    /**用户账号   非持卡人填手机号码，持卡人填互生号 ，企业管理员填操作员账号*/
    private String userName; 
    
   
    
    /**用户类型  1:非持卡人  2:持卡人 3：企业管理员 4：系统用户 */
    private String userType;
    
    /**新的登录密码 */
    private String newLoginPwd;

    /**客户号 */
    private String custId;
    
    /**验证邮件通过的凭证 */
    private String randomToken; 
    
    private String entResNo;
    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


	public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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


    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

	public String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	public String getRandomToken() {
		return randomToken;
	}

	public void setRandomToken(String randomToken) {
		this.randomToken = randomToken;
	}

	public String getEntResNo() {
		return entResNo;
	}

	public void setEntResNo(String entResNo) {
		this.entResNo = entResNo;
	}

}
