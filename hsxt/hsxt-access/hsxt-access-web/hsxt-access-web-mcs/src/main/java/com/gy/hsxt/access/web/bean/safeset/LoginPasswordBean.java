/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.bean.safeset;

import java.io.Serializable;

import com.gy.hsxt.access.web.bean.AbstractMCSBase;
import com.gy.hsxt.access.web.common.utils.RequestUtil;
import com.gy.hsxt.common.constant.RespCode;

/***
 * 登录密码维护实体
 * 
 * @Package: com.gy.hsxt.access.web.bean.safeSet
 * @ClassName: PassWordBean
 * @Description: TODO
 * 
 * @author: wangjg
 * @date: 2015-10-21 下午4:09:54
 * @version V1.0
 */
public class LoginPasswordBean extends AbstractMCSBase implements Serializable {
    private static final long serialVersionUID = -9068172725252922430L;

    /**
     * 旧密码
     */
    private String oldPassword;

    /**
     * 新密码
     */
    private String newPassword;

    /**
     * 确认新密码
     */
    private String confirmPassword;

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    /**
     * 验证密码长度(纯数字)
     * 
     * @param length
     * @return
     */
    public boolean validatePasswordLength(int length) {
        String reg = "^\\d{" + length + "}$";
        return this.newPassword.matches(reg);
    }

    /**
     * 验证两次新密码相等
     * 
     * @return
     */
    public boolean validateNewPasswordEquals() {
        return this.newPassword.equals(this.confirmPassword);
    }

    /**
     * 验证非空数据
     * 
     * @return
     */
    public void validateEmptyData() {
        RequestUtil.verifyParamsIsNotEmpty(
                new Object[] { this.oldPassword, RespCode.GL_PARAM_ERROR },
                new Object[] { this.newPassword, RespCode.GL_PARAM_ERROR }, 
                new Object[] { this.confirmPassword, RespCode.GL_PARAM_ERROR });
    }
}
