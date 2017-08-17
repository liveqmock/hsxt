/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.person.bean.safetySet;

import java.io.Serializable;

import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.RandomCodeUtils;
import com.gy.hsxt.common.utils.StringUtils;
import com.gy.hsxt.uc.as.bean.common.AsMainInfo;

/***
 * 交易密码重置验证实体类
 * 
 * @Package: com.gy.hsxt.access.web.person.bean.safetySet
 * @ClassName: DealPwdResetCheck
 * @Description: TODO
 * 
 * @author: wangjg
 * @date: 2015-11-7 下午6:27:10
 * @version V1.0
 */
public class DealPwdResetCheck implements Serializable {

    private static final long serialVersionUID = -1685587035880514417L;

    /**
     * 客户号
     */
    private String custId;

    /**
     * 密码加密token
     */
    private String pwdToken;

    /**
     * 互生卡号
     */
    private String pointNo;

    /**
     * 登录密码
     */
    private String loginPwd;

    /**
     * 用户姓名
     */
    private String name;

    /**
     * 证件号码
     */
    private String idNumber;

    /**
     * 验证码
     */
    private String smsCode;

    /**
     * 随机tonken
     */
    private String randomToken;

    /**
     * 验证空数据
     * 
     * @return
     */
    public void checkEmptyData() {
        // 客户名
        if (StringUtils.isBlank(this.name))
        {
            throw new HsException(RespCode.PW_CUSTNAME_INVALID);
        }
        else if (StringUtils.isBlank(this.idNumber))// 证件号
        {
            throw new HsException(RespCode.PW_ENT_FILING_SHAREHOLDER_IDNO_INVALID);
        }
        else if (StringUtils.isBlank(this.smsCode)) // 验证码不能为空
        {
            throw new HsException(RespCode.PW_VERIFICATION_CODE_INVALID);
        }
        else if (StringUtils.isBlank(this.loginPwd))// 登录密码非空验证
        {
            throw new HsException(RespCode.PW_LOGINPWD_INVALID);
        }
        else if (StringUtils.isBlank(this.pwdToken))// 随机token非空验证
        {
            throw new HsException(RespCode.AS_RANDOM_TOKEN_INVALID);
        }
    }

    /**
     * 创建交易密码申请类
     * @param dprc
     * @return
     */
    public AsMainInfo createMainInfo() {
        AsMainInfo ami = new AsMainInfo();
        ami.setPerCustId(this.custId);
        ami.setRealName(this.name);
        ami.setCerNo(this.idNumber);
        ami.setCerType("1"); // 证件类型：身份证
        ami.setLoginPwd(this.loginPwd);
        ami.setSecretKey(this.pwdToken);
        return ami;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public String getPointNo() {
        return pointNo;
    }

    public void setPointNo(String pointNo) {
        this.pointNo = pointNo;
    }

    public String getLoginPwd() {
        return loginPwd;
    }

    public void setLoginPwd(String loginPwd) {
        this.loginPwd = loginPwd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getSmsCode() {
        return smsCode;
    }

    public void setSmsCode(String smsCode) {
        this.smsCode = smsCode;
    }

    public static void main(String[] args) {
        System.out.println("验证码：" + RandomCodeUtils.getNumberCode());
    }

    public String getRandomToken() {
        return randomToken;
    }

    public void setRandomToken(String randomToken) {
        this.randomToken = randomToken;
    }

    /**
     * @return the pwdToken
     */
    public String getPwdToken() {
        return pwdToken;
    }

    /**
     * @param pwdToken the pwdToken to set
     */
    public void setPwdToken(String pwdToken) {
        this.pwdToken = pwdToken;
    }

}
