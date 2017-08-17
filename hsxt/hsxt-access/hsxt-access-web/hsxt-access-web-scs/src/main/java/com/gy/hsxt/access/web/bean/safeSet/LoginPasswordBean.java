/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.bean.safeSet;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

import com.gy.hsxt.access.web.bean.AbstractSCSBase;
import com.gy.hsxt.access.web.common.utils.RequestUtil;
import com.gy.hsxt.common.constant.RespCode;

/***
 * 登录密码维护实体
 * 
 * @Package: com.gy.hsxt.access.web.bean.safeSet
 * @ClassName: LoginPasswordBean
 * @Description: TODO
 * 
 * @author: wangjg
 * @date: 2015-11-2 下午7:57:21
 * @version V1.0
 */
public class LoginPasswordBean extends AbstractSCSBase implements Serializable {

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
     * 数据验证
     * 
     * @return
     */
    public void validateEmptyData(HttpServletRequest request) {
        RequestUtil.verifyParamsIsNotEmpty(
                new Object[] { this.oldPassword, RespCode.AS_PARAM_INVALID },
                new Object[] { this.newPassword, RespCode.AS_PARAM_INVALID },
                new Object[] { this.confirmPassword, RespCode.AS_PARAM_INVALID }
        );
        //呼叫中心的特殊处理方法
        this.pwdEncodeBase64String(request);
    }
    
    /**
     * 呼叫中心的特殊处理方法===登录密码解密
     * @param request
     */
    public void pwdEncodeBase64String(HttpServletRequest request){
        this.oldPassword= RequestUtil.encodeBase64String(request,this.oldPassword);
        this.newPassword= RequestUtil.encodeBase64String(request,this.newPassword);
        this.confirmPassword= RequestUtil.encodeBase64String(request,this.confirmPassword);
    }
}
