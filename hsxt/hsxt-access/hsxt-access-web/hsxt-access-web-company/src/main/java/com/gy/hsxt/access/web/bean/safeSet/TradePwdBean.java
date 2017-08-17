/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.bean.safeSet;

import java.io.Serializable;

import com.gy.hsxt.access.web.bean.AbstractCompanyBase;
import com.gy.hsxt.access.web.common.utils.RequestUtil;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.uc.as.api.enumerate.UserTypeEnum;
import com.gy.hsxt.uc.as.bean.common.AsResetTradePwdAuthCode;
import com.gy.hsxt.uc.as.bean.common.AsUpdateTradePwd;

/***
 * 交易密码维护实体
 * 
 * @Package: com.gy.hsxt.access.web.bean.safeSet
 * @ClassName: TransactionPasswordBean
 * @Description: TODO
 * 
 * @author: wangjg
 * @date: 2015-10-27 上午10:59:20
 * @version V1.0
 */
public class TradePwdBean extends AbstractCompanyBase implements Serializable {
    private static final long serialVersionUID = 4026746652976866838L;

    /**
     * 授权码(用于重置交易密码)
     */
    private String authCode;

    /**
     * 旧交易密码
     */
    private String oldTradingPassword;

    /**
     * 新交易密码
     */
    private String newTradingPassword;

    /**
     * 确认交易密码
     */
    private String confirmTradingPassword;

    /**
     * 验证密码长度(纯数字)
     * 
     * @param length
     * @return
     */
    public boolean validatePasswordLength(int length) {
        String reg = "^\\d{" + length + "}$";
        return this.newTradingPassword.matches(reg);
    }

    /**
     * 验证两次新密码相等
     * 
     * @return
     */
    public boolean validateNewPasswordEquals() {
        return this.newTradingPassword.equals(this.confirmTradingPassword);
    }

    /**
     * 新增功能,交易密码验证
     * 
     * @return
     */
    public void addValidate() {
        RequestUtil.verifyParamsIsNotEmpty(
                new Object[] { this.newTradingPassword, RespCode.GL_PARAM_ERROR },
                new Object[] { this.confirmTradingPassword, RespCode.GL_PARAM_ERROR }
                );
    }

    /***
     * 修改功能,交易密码验证
     * 
     * @return
     */
    public void updateValidate() {
        RequestUtil.verifyParamsIsNotEmpty(new Object[] { this.oldTradingPassword, RespCode.GL_PARAM_ERROR });

        // 修改验证部分数据跟新增交易密码一致, 故此调用
        addValidate();
    }

    /***
     * 重置交易密码验证
     * 
     * @return
     */
    public void resetValidate() {
        RequestUtil.verifyParamsIsNotEmpty(new Object[] { this.authCode, RespCode.GL_PARAM_ERROR });
        // 修改验证部分数据跟新增交易密码一致, 故此调用
        addValidate();
    }

    /**
     * 创建交易密码修改类
     * 
     * @return
     */
    public AsUpdateTradePwd crateUpdateTradePwd() {
        AsUpdateTradePwd autp = new AsUpdateTradePwd();
        autp.setCustId(super.getEntCustId());
        autp.setOldTradePwd(this.oldTradingPassword);
        autp.setNewTradePwd(this.newTradingPassword);
        autp.setUserType(UserTypeEnum.ENT.getType());
        autp.setSecretKey(super.getRandomToken());
        autp.setOperCustId(super.getCustId());
        return autp;
    }

    /**
     * 创建企业重置交易密码类
     * 
     * @return
     */
    public AsResetTradePwdAuthCode createARTPAC() {
        AsResetTradePwdAuthCode artpac = new AsResetTradePwdAuthCode();
        artpac.setCustId(super.getEntCustId());
        artpac.setNewTraderPwd(this.newTradingPassword);
        artpac.setAuthCode(this.authCode);
        artpac.setSecretKey(super.getRandomToken());
        artpac.setOperCustId(super.getCustId());
        return artpac;
    }

    public String getOldTradingPassword() {
        return oldTradingPassword;
    }

    public void setOldTradingPassword(String oldTradingPassword) {
        this.oldTradingPassword = oldTradingPassword;
    }

    public String getNewTradingPassword() {
        return newTradingPassword;
    }

    public void setNewTradingPassword(String newTradingPassword) {
        this.newTradingPassword = newTradingPassword;
    }

    public String getConfirmTradingPassword() {
        return confirmTradingPassword;
    }

    public void setConfirmTradingPassword(String confirmTradingPassword) {
        this.confirmTradingPassword = confirmTradingPassword;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

}
