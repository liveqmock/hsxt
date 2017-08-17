/*
\ * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.common.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gy.hsxt.access.web.common.bean.EmailParam;
import com.gy.hsxt.access.web.common.bean.HttpRespEnvelope;
import com.gy.hsxt.access.web.common.bean.MobileParam;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.StringUtils;
import com.gy.hsxt.uc.as.api.common.IUCAsPwdService;
import com.gy.hsxt.uc.as.api.enumerate.ErrorCodeEnum;
import com.gy.hsxt.uc.as.api.enumerate.UserTypeEnum;
import com.gy.hsxt.uc.as.bean.common.AsResetLoginPwd;

/**
 * 重置密码
 * 
 * @Package: com.gy.hsxt.access.web.common.controller
 * @ClassName: ResetPwdController
 * @Description: TODO
 * 
 * @author: lixuan
 * @date: 2015-12-18 下午4:19:06
 * @version V1.0
 */
@Controller
@RequestMapping(value = "/resetPwd")
public class ResetPwdController {
	@Autowired
	IUCAsPwdService asPwdService;

//	/**
//	 * 重置登录密码
//	 * 
//	 * @param param
//	 * @return
//	 */
//	@ResponseBody
//	@RequestMapping(value = "resetLoginPwdByMobile", method = RequestMethod.POST)
//	public HttpRespEnvelope resetLoginPwdByMobile(
//			@RequestBody MobileParam param, @RequestParam String randomToken) {
//		HttpRespEnvelope hre = validMobileParams(param, randomToken);
//		try {
//			AsResetLoginPwd mobileParams = new AsResetLoginPwd();
//			mobileParams.setEntResNo(param.getEntResNo().trim());
//			mobileParams.setMobile(param.getMobile().trim());
//			mobileParams.setNewLoginPwd(param.getNewLoginPwd().trim());
//			mobileParams.setSecretKey(param.getSecretKey().trim());
//			mobileParams.setSmsCode(param.getSmsCode().trim());
//			mobileParams.setUserName(param.getUserName().trim());
//			mobileParams.setUserType(param.getUserType().trim());
//			asPwdService.resetLoginPwdByMobile(mobileParams);
//		} catch (HsException e) {
//			hre.setRetCode(e.getErrorCode());
//			hre.setResultDesc(e.getMessage());
//			hre.setSuccess(false);
//			return hre;
//		}
//		return hre;
//	}
	
	private HttpRespEnvelope validMobileParams(MobileParam param, String randomToken){
		HttpRespEnvelope hre = new HttpRespEnvelope();
		if (null == param) {
			hre.setRetCode(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue());
			hre.setResultDesc("param is null");
			hre.setSuccess(false);
			return hre;
		}
		if (StringUtils.isBlank(param.getMobile())) {
			hre.setRetCode(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue());
			hre.setResultDesc("手机号码不能为空");
			hre.setSuccess(false);
			return hre;
		}
		if (StringUtils.isBlank(param.getNewLoginPwd())) {
			hre.setRetCode(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue());
			hre.setResultDesc("新登录密码不能为空");
			hre.setSuccess(false);
			return hre;
		}
		if (StringUtils.isBlank(param.getSecretKey())) {
			hre.setRetCode(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue());
			hre.setResultDesc("AES秘钥不能为空");
			hre.setSuccess(false);
			return hre;
		}
		if (StringUtils.isBlank(param.getSmsCode())) {
			hre.setRetCode(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue());
			hre.setResultDesc("短信验证码不能为空");
			hre.setSuccess(false);
			return hre;
		}
		if (StringUtils.isBlank(param.getUserName())) {
			hre.setRetCode(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue());
			hre.setResultDesc("用户名不能为空");
			hre.setSuccess(false);
			return hre;
		}
		if (StringUtils.isBlank(randomToken)) {
			hre.setRetCode(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue());
			hre.setResultDesc("随机token不能为空");
			hre.setSuccess(false);
			return hre;
		}
		if (StringUtils.isBlank(param.getUserType())) {
			hre.setRetCode(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue());
			hre.setResultDesc("用户类型不能为空");
			hre.setSuccess(false);
			return hre;
		}if (UserTypeEnum.OPERATOR.getType().equals(param.getUserType().trim()) && StringUtils.isBlank(param.getEntResNo())) {
			hre.setRetCode(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue());
			hre.setResultDesc("企业互生号不能为空");
			hre.setSuccess(false);
			return hre;
		}
		return hre;
	}

//	/**
//	 * 重置登录密码
//	 * 
//	 * @param param
//	 * @return
//	 */
//	@ResponseBody
//	@RequestMapping(value = "resetLoginPwdByEmail", method = RequestMethod.POST)
//	public HttpRespEnvelope resetLoginPwdByEmail(
//			@RequestBody EmailParam emailParam, @RequestParam String randomToken) {
//		HttpRespEnvelope hre = new HttpRespEnvelope();
//		validEmailParams(emailParam, randomToken);
//		try {
//			AsResetLoginPwdEmail params = new AsResetLoginPwdEmail();
//			params.setCustId(emailParam.getCustId().trim());
//			params.setEmail(emailParam.getEmail().trim());
//			params.setNewLoginPwd(emailParam.getNewLoginPwd().trim());
//			params.setRandomToken(randomToken.trim());
//			params.setSecretKey(emailParam.getSecretKey().trim());
//			params.setUserType(emailParam.getUserType().trim());
//			asPwdService.resetLoginPwdByEmail(params);
//		} catch (HsException e) {
//			hre.setRetCode(e.getErrorCode());
//			hre.setResultDesc(e.getMessage());
//			hre.setSuccess(false);
//			return hre;
//		}
//		return hre;
//	}
	
	
	/**
	 * 检查入参
	 * @param param
	 */
	private HttpRespEnvelope validEmailParams(EmailParam param,String randomToken){
		HttpRespEnvelope hre = new HttpRespEnvelope();
		if (null == param) {
			hre.setRetCode(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue());
			hre.setResultDesc("param is null");
			hre.setSuccess(false);
			return hre;
		}
		if (StringUtils.isBlank(param.getNewLoginPwd())) {
			hre.setRetCode(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue());
			hre.setResultDesc("新登录密码不能为空");
			hre.setSuccess(false);
			return hre;
		}

		if (StringUtils.isBlank(param.getCustId())) {
			hre.setRetCode(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue());
			hre.setResultDesc("客户号不能为空");
			hre.setSuccess(false);
			return hre;
		}
		if (StringUtils.isBlank(param.getUserType())) {
			hre.setRetCode(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue());
			hre.setResultDesc("用户类型不能为空");
			hre.setSuccess(false);
			return hre;
		}

		if (StringUtils.isBlank(param.getRandomToken())) {
			hre.setRetCode(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue());
			hre.setResultDesc("随机token不能为空");
			hre.setSuccess(false);
			return hre;
		}
		if (StringUtils.isBlank(param.getSecretKey())) {
			hre.setRetCode(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue());
			hre.setResultDesc("随机token不能为空");
			hre.setSuccess(false);
			return hre;
		}
		if (StringUtils.isBlank(param.getEmail())) {
			hre.setRetCode(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue());
			hre.setResultDesc("邮箱不能为空");
			hre.setSuccess(false);
			return hre;
		}
		if (StringUtils.isBlank(randomToken)) {
			hre.setRetCode(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue());
			hre.setResultDesc("随机token不能为空");
			hre.setSuccess(false);
			return hre;
		}
		return hre;
	}
}
