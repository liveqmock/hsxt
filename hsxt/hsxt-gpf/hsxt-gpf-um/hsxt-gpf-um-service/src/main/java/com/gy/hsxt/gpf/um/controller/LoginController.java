/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.gpf.um.controller;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.HsAssert;
import com.gy.hsxt.gpf.um.bean.LoginInfo;
import com.gy.hsxt.gpf.um.bean.Operator;
import com.gy.hsxt.gpf.um.bean.RespInfo;
import com.gy.hsxt.gpf.um.constants.SessionAttribute;
import com.gy.hsxt.gpf.um.enums.UMRespCode;
import com.gy.hsxt.gpf.um.service.ILoginService;
import com.gy.hsxt.gpf.um.utils.TokenUtils;
import com.gy.hsxt.gpf.um.utils.ValidCodeUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.WebUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录控制中心
 *
 * @Package : com.gy.hsxt.gpf.um.controller
 * @ClassName : LoginController
 * @Description : 登录控制中心
 * @Author : chenm
 * @Date : 2016/1/28 19:54
 * @Version V3.0.0.0
 */
@Controller
@RequestMapping("/login")
public class LoginController extends BaseController {


    /**
     * 登录业务接口
     */
    @Resource
    private ILoginService loginService;

    /**
     * 登录
     *
     * @param operator 操作员
     * @return {@link Boolean}
     * @throws HsException
     */
    @ResponseBody
    @RequestMapping("/on")
    public RespInfo<LoginInfo> on(HttpServletRequest request, HttpServletResponse response, Operator operator) throws HsException {
        logger.info("========登录/签入的操作员[operator]:{}========", operator);
        HsAssert.notNull(operator, UMRespCode.UM_PARAM_NULL_ERROR, "登录/签入的操作员[operator]为null");
        HsAssert.hasText(operator.getValidCode(), UMRespCode.UM_PARAM_EMPTY_ERROR, "验证码不能为空");
        //获取session中验证码
        Object validCode = WebUtils.getSessionAttribute(request, SessionAttribute.LOGIN_VALID_CODE);
        //session中没有，说明已经过期
//        HsAssert.notNull(validCode, UMRespCode.UM_LOGIN_VALID_CODE_EXPIRE, "验证码已经过期");
        //比较验证码
        boolean success = validCode!=null&&StringUtils.equals(operator.getValidCode(), String.valueOf(validCode));

//        HsAssert.isTrue(success, UMRespCode.UM_LOGIN_VALID_CODE_ERROR, "验证码错误");
        //移除session中验证码
        WebUtils.setSessionAttribute(request, SessionAttribute.LOGIN_VALID_CODE, null);
        //验证成功之后,返回登录信息
        LoginInfo loginInfo = loginService.signIn(operator,!success);
        //将token放入cookie/session
        TokenUtils.putToken(request, response, loginInfo.getToken());

        return RespInfo.bulid(loginInfo);
    }


    /**
     * 描绘验证码
     *
     * @param request 请求
     * @return byte
     * @throws HsException
     */
    @ResponseBody
    @RequestMapping(value = "/paintImage", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] paintImage(HttpServletRequest request) throws HsException {
        //形成验证码
        ValidCodeUtils.ValidatorCode code = ValidCodeUtils.getCode();
        //设置session属性值
        WebUtils.setSessionAttribute(request, SessionAttribute.LOGIN_VALID_CODE, code.getCode());
        //保存验证码
        loginService.storeValidCode(code.getCode());
        return code.getImage();
    }

    /**
     * 退出/签出
     *
     * @return {@link Boolean}
     * @throws HsException
     */
    @ResponseBody
    @RequestMapping("/out")
    public RespInfo<String> out(HttpServletRequest request, HttpServletResponse response) throws HsException {
        //从请求中获取令牌token
        String token = TokenUtils.takeToken(request);

        loginService.signOut(token);

        //移除token对应的cookie和session中的值
        TokenUtils.removeToken(request, response);

        return RespInfo.bulid(token);
    }

    /**
     * 令牌校验
     *
     * @return {@link Boolean}
     * @throws HsException
     */
    @ResponseBody
    @RequestMapping("/check")
    public RespInfo<LoginInfo> check(HttpServletRequest request) throws HsException {
        LoginInfo loginInfo = null;
        try {
            //从请求中获取令牌token
            String token = TokenUtils.takeToken(request);
            //验证完成后返回登录信息
            loginInfo = loginService.proofToken(token);
        } catch (HsException e) {
            //nothing
        }

        return RespInfo.bulid(loginInfo);
    }
}
