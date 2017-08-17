/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.os.ucm.controller;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsi.redis.client.api.RedisUtil;
import com.gy.hsxt.access.web.common.bean.HttpRespEnvelope;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.DateUtil;
import com.gy.hsxt.common.utils.ValidateCodeUtils;
import com.gy.hsxt.uc.as.api.common.IUCAsTokenService;
import com.gy.hsxt.uc.as.api.common.IUCLoginService;
import com.gy.hsxt.uc.as.api.enumerate.ErrorCodeEnum;
import com.gy.hsxt.uc.as.api.util.UcCacheKey;
import com.gy.hsxt.uc.as.bean.common.AsSysOperLoginInfo;
import com.gy.hsxt.uc.as.bean.enumerate.ChannelTypeEnum;
import com.gy.hsxt.uc.as.bean.result.AsSysOperatorLoginResult;

@Controller
@RequestMapping("login")
public class LoginController {
	/**
	 * 登录操作接口
	 */
	@Autowired
	IUCLoginService loginService;
	/**
	 * token操作接口
	 */
	@Autowired
	IUCAsTokenService tokenService;
	/**
	 * 缓存帮助类
	 */
	@Resource
	RedisUtil<Object> changeRedisUtil;

	/**
	 * 获取随机token
	 * 
	 * @param custId
	 *            客户号
	 * @return 随机token
	 */
	@ResponseBody
	@RequestMapping(value = "getRandomToken", method = RequestMethod.POST)
	public HttpRespEnvelope getRandomToken(
			@RequestParam(required = false) String custId) {
		HttpRespEnvelope hre = new HttpRespEnvelope();
		if (custId != null) {
			hre.setData(tokenService.getRandomToken(custId));
			hre.setSuccess(true);

		} else {
			hre.setData(tokenService.getRandomToken(""));
			hre.setSuccess(true);
		}
		return hre;
	}

	/**
	 * 获取验证码
	 * 
	 * @return 随机token
	 */
	@ResponseBody
	@RequestMapping(value = "getCheckCode")
	public void getCheckCode(@RequestParam(required = true) String randomToken,
			HttpServletResponse response) {
		System.out.println("开始获取验证码" + DateUtil.getCurrentDateTime());
		// 禁止图像缓存。
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("image/jpeg");

		try {
			// 将图像输出到Servlet输出流中。
			ServletOutputStream sos = response.getOutputStream();
			String code = ValidateCodeUtils.getDefaultRandcode(4, sos);
			// request.getSession().setAttribute("chkCode", obj[0]);
			changeRedisUtil.setString("OS.checkCode_" + randomToken, code);
			System.out
					.println(DateUtil.getCurrentDateTime() + "生成的验证码：" + code);

			// ImageIO.write((BufferedImage) obj[1], "jpeg", sos);
			sos.close();
			System.out.println("验证码生成成功，并返回" + DateUtil.getCurrentDateTime());
		} catch (Exception ex) {
			ex.printStackTrace();
			SystemLog.error(this.getClass().getName(), "getCheckCode",
					"生成验证码异常", ex);
		}
	}

	/**
	 * 登录
	 * 
	 * @param mobile
	 *            手机号
	 * @param loginPwd
	 *            登录密码
	 * @param channelType
	 *            渠道类型
	 * @param versionNumber
	 *            版本号
	 * @param randomToken
	 *            随机token
	 * @param loginIp
	 *            登录IP
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "login", method = RequestMethod.POST)
	public HttpRespEnvelope login(
			@RequestParam(required = true) String username,
			@RequestParam(required = true) String password,
			@RequestParam(required = false) String randomToken,
			@RequestParam(required = true) String chkCode,
			HttpServletRequest request) {
		HttpRespEnvelope hre = new HttpRespEnvelope();

		AsSysOperLoginInfo loginInfo = new AsSysOperLoginInfo();
		loginInfo.setChannelType(ChannelTypeEnum.WEB);
		loginInfo.setLoginPwd(password);
		loginInfo.setUserName(username);
		loginInfo.setRandomToken(randomToken);

		// 验证randomtoken
		if (!checkRandomToken("", randomToken)) {
			hre.setRetCode(ErrorCodeEnum.RANDOM_TOKEN_NOT_MATCH.getValue());
			hre.setSuccess(false);
			hre.setData(tokenService.getRandomToken(""));
			return hre;
		}

		// 验证验证码
		if (!checkCode(randomToken, chkCode)) {
			hre.setRetCode(ErrorCodeEnum.LOGIN_CODE_WRONG.getValue());
			hre.setSuccess(false);
			hre.setData(tokenService.getRandomToken(""));
			return hre;
		}
		try {
			// 验证登录
			AsSysOperatorLoginResult rs = loginService
					.sysOperatorLogin(loginInfo);
			hre.setData(rs);
		} catch (HsException hse) {
			hse.printStackTrace();
			hre.setRetCode(hse.getErrorCode());
			hre.setResultDesc(hse.getMessage() + "，用户名：" + username + ",密码："
					+ password + ",随机token:" + randomToken);
			hre.setSuccess(false);
			// 重新生成随机token
			hre.setData(tokenService.getRandomToken(""));
			return hre;
		} catch (Exception e) {
			System.out.println("--未知异常，非持卡人登录请求参数：" + "，用户名：" + username
					+ ",密码：" + password + ",随机token:" + randomToken);
			e.printStackTrace();
			hre.setRetCode(RespCode.UNKNOWN.getCode());
			hre.setResultDesc(e.getMessage() + "，用户名：" + username + ",密码："
					+ password + ",随机token:" + randomToken);
			hre.setSuccess(false);
			// 重新生成随机token
			hre.setData(tokenService.getRandomToken(""));
			return hre;
		}
		return hre;
	}

	/**
	 * 判断随机token是否正确
	 * 
	 * @param custId
	 * @param token
	 * @return
	 */
	private boolean checkRandomToken(String custId, String token) {
		String key = UcCacheKey.genRandomTokenKey(custId, token);
		return changeRedisUtil.isExists(key, true);
	}

	/**
	 * 判断验证码是否正确
	 * 
	 * @param code
	 * @return
	 */
	private boolean checkCode(String randomToken, String code) {
		String originalCode = changeRedisUtil.getString("OS.checkCode_"
				+ randomToken);
		System.out.println(randomToken + "缓存中获取的随机token:" + originalCode);
		if (StringUtils.isBlank(originalCode)) {
			return false;
		}
		return originalCode.equals(code);
	}

	/**
	 * 是否登录
	 * 
	 * @param custId
	 *            客户号
	 * @param loginToken
	 *            登录token
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "isLogin", method = RequestMethod.POST)
	public HttpRespEnvelope isLogin(
			@RequestParam(required = false) String custId,
			@RequestParam(required = false) String loginToken) {
		HttpRespEnvelope hre = new HttpRespEnvelope();

		if (loginToken == null || loginToken.equals("")) {
			hre.setData(0);
			return hre;
		}
		if (custId == null || custId.equals("")) {
			hre.setData(0);
			return hre;
		}

		String loginKey = UcCacheKey.genLoginKey(ChannelTypeEnum.WEB, custId);
		String token = null;
		try {
			token = changeRedisUtil.getString(loginKey);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (loginToken.equals(token)) {
			hre.setData(1);
			return hre;
		}

		hre.setData(0);
		return hre;
	}
}
