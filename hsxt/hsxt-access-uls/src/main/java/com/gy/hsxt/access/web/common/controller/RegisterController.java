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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gy.hsxt.access.web.common.bean.HttpRespEnvelope;
import com.gy.hsxt.access.web.common.bean.RegisterParam;
import com.gy.hsxt.common.constant.CustType;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.uc.as.api.common.IUCAsMobileService;
import com.gy.hsxt.uc.as.api.consumer.IUCAsNoCardHolderService;
import com.gy.hsxt.uc.as.api.enumerate.ErrorCodeEnum;
import com.gy.hsxt.uc.as.bean.consumer.AsRegNoCardHolder;

/**
 * 提供注册功能的接口，目前提供非持卡人注册
 * 
 * @Package: com.gy.hsxt.access.web.common.controller
 * @ClassName: RegisterController
 * @Description: TODO
 * 
 * @author: lixuan
 * @date: 2015-11-10 上午11:38:07
 * @version V1.0
 */
@Controller
@RequestMapping(value = "/register")
public class RegisterController  {

	@Autowired
	IUCAsNoCardHolderService noCardHolderService;
	@Autowired
	IUCAsMobileService mobileService;

	/**
	 * 非持卡人注册
	 * 
	 * @param mobile
	 *            手机号
	 * @param loginPwd
	 *            登录密码，使用aes加密MD5
	 * @param smsCode
	 *            短信验证码
	 * @param nickname
	 *            昵称
	 * @param email
	 *            email
	 * @param randomToken
	 *            随机token
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="nocarder", method = RequestMethod.POST)
	public HttpRespEnvelope nocarderRegister(@RequestBody RegisterParam param, @RequestParam String randomToken) {
		HttpRespEnvelope hre = new HttpRespEnvelope();
		AsRegNoCardHolder rnch = new AsRegNoCardHolder();
		rnch.setEmail(param.getEmail());
		rnch.setMobile(param.getMobile());
		rnch.setLoginPwd(param.getLoginPwd());
		rnch.setNickname(param.getNickname());
		rnch.setSmsCode(param.getSmsCode());
		try {
			noCardHolderService.regNoCardConsumer(rnch, randomToken);
		} catch (HsException e) {
			e.printStackTrace();
			hre.setRetCode(e.getErrorCode());
			hre.setResultDesc(e.getMessage());
			hre.setSuccess(false);
			return hre;
		}
		return hre;
	}

	@ResponseBody
	@RequestMapping(value="sendRegisterCode", method = RequestMethod.POST)
	public HttpRespEnvelope sendRegisterCode(@RequestBody RegisterParam param) {
		HttpRespEnvelope hre = new HttpRespEnvelope();
		try {
			// 验证是否已注册
			boolean isRegister = noCardHolderService.isRegister(param.getMobile());
			if(isRegister){
				throw new HsException(ErrorCodeEnum.USER_EXIST.getValue(), ErrorCodeEnum.USER_EXIST.getDesc());
			}
			mobileService.sendSmsCodeForValidMobile("", param.getMobile(), CustType.NOT_HS_PERSON.getCode());
		} catch (HsException e) {
			e.printStackTrace();
			hre.setRetCode(e.getErrorCode());
			hre.setResultDesc(e.getMessage());
			hre.setSuccess(false);
			return hre;
		}
		return hre;
	}
	
	@ResponseBody
	@RequestMapping(value="isRegister", method = RequestMethod.POST)
	public HttpRespEnvelope isRegister(@RequestBody RegisterParam param) {
		HttpRespEnvelope hre = new HttpRespEnvelope();
		try {
			// 验证是否已注册
			boolean isRegister = noCardHolderService.isRegister(param.getMobile());
			if(isRegister){
				hre.setData("1");
			}
			else{
				hre.setData("0");
			}
		} catch (HsException e) {
			e.printStackTrace();
			hre.setRetCode(e.getErrorCode());
			hre.setResultDesc(e.getMessage());
			hre.setSuccess(false);
			return hre;
		}
		return hre;
	}
}
