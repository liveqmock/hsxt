package com.gy.hsi.ds.login.controller.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gy.hsi.ds.common.thirds.dsp.common.exception.FieldException;
import com.gy.hsi.ds.login.beans.bo.User;
import com.gy.hsi.ds.login.service.ISignMgr;
import com.gy.hsi.ds.param.controller.form.PasswordForm;
import com.gy.hsi.ds.param.controller.form.SigninForm;

/**
 * 权限验证
 *
 * @author liaoqiqi
 * @version 2014-7-2
 */
@Component
public class AuthValidator {

    @Autowired
    private ISignMgr signMgr;

    /**
     * 验证登录
     */
    public void validateLogin(SigninForm signinForm) {

        // 校验用户是否存在
        User user = signMgr.getUserByName(signinForm.getName());
        
        if (user == null) {
            //throw new FieldException(SigninForm.Name, "user.not.exist", null);
        	throw new FieldException("错误", "user.not.exist", null);
        }

        // 校验密码
        if (!signMgr.validate(user.getPassword(), signinForm.getPassword())) {
            //throw new FieldException(SigninForm.PASSWORD, "password.not.right", null);
        	throw new FieldException("错误", "password.not.right", null);
        }
    }
    
    /**
     * 验证原密码
     */
    public void validatePass(PasswordForm passwordForm,User user ) {

        // 校验密码
        if (!signMgr.validate(user.getPassword(), passwordForm.getOldpass())) {
			// throw new FieldException(PasswordForm.OLDPASS, "oldpass.not.right", null);
        	throw new FieldException("错误", "oldpass.not.right", null);
        }
    }
}
