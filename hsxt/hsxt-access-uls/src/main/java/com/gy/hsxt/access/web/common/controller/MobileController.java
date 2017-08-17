/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.common.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gy.hsxt.access.web.common.bean.HttpRespEnvelope;
import com.gy.hsxt.access.web.common.bean.MobileParam;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.StringUtils;
import com.gy.hsxt.uc.as.api.common.IUCAsMobileService;
import com.gy.hsxt.uc.as.api.common.IUCAsPwdService;
import com.gy.hsxt.uc.as.api.consumer.IUCAsNoCardHolderService;
import com.gy.hsxt.uc.as.api.enumerate.ErrorCodeEnum;
import com.gy.hsxt.uc.as.api.enumerate.UserTypeEnum;

@Controller
@RequestMapping(value = "/mobile")
public class MobileController {
	@Autowired
	IUCAsMobileService mobileService;
	@Autowired
	IUCAsPwdService asPwdService;
	@Autowired
	IUCAsNoCardHolderService noCardHolderService;

	/**
	 * 发送短信验证码(包含企业验证手机、持卡人验证手机)
	 * 
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "sendSmsCodeForValidMobile", method = RequestMethod.POST)
	public HttpRespEnvelope sendSmsCodeForValidMobile(
			@RequestBody MobileParam param) {
		HttpRespEnvelope hre = new HttpRespEnvelope();
		if (StringUtils.isBlank(param.getMobile())) {
			hre.setRetCode(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue());
			hre.setResultDesc("手机号为空");
			hre.setSuccess(false);
			return hre;
		}
		String custId = StringUtils.nullToEmpty(param.getCustId());
		try {
			mobileService.sendSmsCodeForValidMobile(custId, param.getMobile(), param.getCustType());
		} catch (HsException e) {
			hre.setRetCode(e.getErrorCode());
			hre.setResultDesc(e.getMessage());
			hre.setSuccess(false);
			return hre;
		}
		return hre;
	}

	/**
	 * 发送企业授权码
	 * 
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "sendAuthCode", method = RequestMethod.POST)
	public HttpRespEnvelope sendAuthCode(@RequestBody MobileParam param) {
		HttpRespEnvelope hre = new HttpRespEnvelope();
		if (StringUtils.isBlank(param.getEntResNo())) {
			hre.setRetCode(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue());
			hre.setResultDesc("企业资源号为空");
			hre.setSuccess(false);
			return hre;
		}
		try {
			mobileService.sendAuthCodeSuccess(param.getEntResNo(), param.getCustType());
		} catch (HsException e) {
			hre.setRetCode(e.getErrorCode());
			hre.setResultDesc(e.getMessage());
			hre.setSuccess(false);
			return hre;
		}
		return hre;
	}

	/**
	 * 发送短信验证码(包含企业管理员重置登录密码、持卡人重置登录密码、非持卡人重置登录密码)
	 * 
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "sendSmsCodeForResetPwd", method = RequestMethod.POST)
	public HttpRespEnvelope sendSmsCodeForResetPwd(
			@RequestBody MobileParam param) {
		HttpRespEnvelope hre = new HttpRespEnvelope();
		if (param.getUserType() == null || param.getUserType().equals("")) {
			hre.setRetCode(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue());
			hre.setResultDesc("用户类型为空");
			hre.setSuccess(false);
			return hre;
		}
		if (param.getUserName() == null || param.getUserName().equals("")) {
			hre.setRetCode(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue());
			hre.setResultDesc("用户名称为空");
			hre.setSuccess(false);
			return hre;
		}
		String custId = param.getCustId();
		if (!(UserTypeEnum.CARDER.getType().equals(param.getUserType()))
				&& (!(UserTypeEnum.OPERATOR.getType().equals(param
						.getUserType())))
				&& (!(UserTypeEnum.NO_CARDER.getType().equals(param
						.getUserType())))) {
			hre.setRetCode(ErrorCodeEnum.USERTYPE_IS_ILLEGAL.getValue());
			hre.setResultDesc(ErrorCodeEnum.USERTYPE_IS_ILLEGAL.getDesc());
			hre.setSuccess(false);
			return hre;
		}

		try {
			mobileService.sendSmsCodeForResetPwd(custId, param.getUserType(), param.getMobile(), param.getCustType());
		} catch (HsException e) {
			hre.setRetCode(e.getErrorCode());
			hre.setResultDesc(e.getMessage());
			hre.setSuccess(false);
			return hre;
		}
		hre.setData(custId);
		return hre;
	}
	
	/**
	 * 校验短信验证码
	 * 
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "checkSmsCode", method = RequestMethod.POST)
	public HttpRespEnvelope checkSmsCode(@RequestBody MobileParam param) {
		HttpRespEnvelope hre = new HttpRespEnvelope();
		if (StringUtils.isBlank(param.getMobile())) {
			hre.setRetCode(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue());
			hre.setResultDesc("手机号码为空");
			hre.setSuccess(false);
			return hre;
		}
		if (StringUtils.isBlank(param.getSmsCode())) {
			hre.setRetCode(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue());
			hre.setResultDesc("短信验证码为空");
			hre.setSuccess(false);
			return hre;
		}
		try {
			mobileService.checkSmsCode(param.getMobile().trim(), param
					.getSmsCode().trim());
		} catch (HsException e) {
			hre.setRetCode(e.getErrorCode());
			hre.setResultDesc(e.getMessage());
			hre.setSuccess(false);
			return hre;
		}
		return hre;
	}

	

}
