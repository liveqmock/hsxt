package com.gy.hsi.ds.login.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gy.hsi.ds.common.constant.LoginConstant;
import com.gy.hsi.ds.common.thirds.dsp.common.annotation.NoAuth;
import com.gy.hsi.ds.common.thirds.dsp.common.constant.ErrorCode;
import com.gy.hsi.ds.common.thirds.dsp.common.constant.WebConstants;
import com.gy.hsi.ds.common.thirds.dsp.common.controller.BaseController;
import com.gy.hsi.ds.common.thirds.dsp.common.vo.JsonObject;
import com.gy.hsi.ds.common.thirds.dsp.common.vo.JsonObjectBase;
import com.gy.hsi.ds.common.utils.SignUtils;
import com.gy.hsi.ds.login.beans.bo.User;
import com.gy.hsi.ds.login.beans.vo.VisitorVo;
import com.gy.hsi.ds.login.controller.validator.AuthValidator;
import com.gy.hsi.ds.login.service.ILoginService;
import com.gy.hsi.ds.login.service.ISignMgr;
import com.gy.hsi.ds.login.service.IUserMgr;
import com.gy.hsi.ds.param.controller.form.PasswordForm;
import com.gy.hsi.ds.param.controller.form.SigninForm;

/**
 * @author liaoqiqi
 * @version 2014-1-20
 */
@Controller
@RequestMapping(WebConstants.API_PREFIX + "/account")
public class UserController extends BaseController {
    @Autowired
    private IUserMgr userMgr;

    @Autowired
    private AuthValidator authValidator;

    @Autowired
    private ISignMgr signMgr;

    @Autowired
    private ILoginService redisLogin;

    /**
     * GET 获取
     *
     * @return
     */
    @NoAuth
    @RequestMapping(value = "/session")
    @ResponseBody
    public JsonObjectBase get() {

        VisitorVo visitorVo = userMgr.getCurVisitor();
        
        if (visitorVo != null) {
            return buildSuccess("visitor", visitorVo);
        } else {
            // 没有登录啊
            return buildGlobalError("syserror.inner", ErrorCode.GLOBAL_ERROR);
        }
    }
    
    /**
     * 校验session
     *
     * @return
     */
    @NoAuth
    @RequestMapping(value = "/checksession")
    @ResponseBody
    public JsonObjectBase checksession() {

        VisitorVo visitorVo = userMgr.getCurVisitor();
        
        if (visitorVo != null) {
            return buildSuccess("visitor", visitorVo);
        } else {
            // 没有登录啊
            return buildGlobalError("syserror.inner", ErrorCode.GLOBAL_ERROR);
        }
    }

    /**
     * 登录
     *
     * @param signin
     * @param request
     *
     * @return
     */
    @NoAuth
    @RequestMapping(value = "/signin")
    @ResponseBody
    public JsonObject signin(@Valid SigninForm signin, HttpServletRequest request) {
        // 验证
        authValidator.validateLogin(signin);

        // 数据库登录
        User user = signMgr.signin(signin.getName());

        // 过期时间
        int expireTime = LoginConstant.SESSION_EXPIRE_TIME;
        
        if (signin.getRemember().equals(LoginConstant.REMEMBER_ME)) {
            expireTime = LoginConstant.SESSION_EXPIRE_TIME2;
        }

        // redis login
        redisLogin.login(request, user, expireTime);

        VisitorVo visitorVo = userMgr.getCurVisitor();
        
        return buildSuccessSignin("visitor", visitorVo);
    }

    /**
     * 登出
     *
     * @param request
     * @return
     */
    @NoAuth
    @RequestMapping(value = "/signout")
    @ResponseBody
    public JsonObjectBase signout(HttpServletRequest request) {

        redisLogin.logout(request);
        
        request.getSession().invalidate();

        return buildSuccess("ok", "ok");
    }
    
    /**
     * 修改密码
     * @param request
     * @return
     */
    @NoAuth
    @RequestMapping(value = "/password")
    @ResponseBody
	public JsonObjectBase updatePass(@Valid PasswordForm passwordForm) {

		VisitorVo visitorVo = userMgr.getCurVisitor();

		// 数据库登录
		User user = signMgr.signin(visitorVo.getName());

		// 验证
		authValidator.validatePass(passwordForm, user);

		user.setPassword(SignUtils.createPassword(passwordForm.getNewpass()));

		userMgr.updateUser(user);

		return buildSuccess("修改密码成功");
	}    
    
    private JsonObject buildSuccessSignin(String key, Object value) {
    	JsonObject json = new JsonObject();
        json.addData(key, value);
        
        return json;
    }
    
}
