/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.gks.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gy.hsi.redis.client.api.RedisUtil;
import com.gy.hsxt.access.web.gks.bean.HttpRespEnvelope;
import com.gy.hsxt.access.web.gks.bean.LoginParam;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.HsResNoUtils;
import com.gy.hsxt.uc.as.api.auth.IUCAsRoleService;
import com.gy.hsxt.uc.as.api.common.IUCAsLogoutService;
import com.gy.hsxt.uc.as.api.common.IUCAsTokenService;
import com.gy.hsxt.uc.as.api.common.IUCLoginService;
import com.gy.hsxt.uc.as.bean.common.AsOperatorLoginInfo;
import com.gy.hsxt.uc.as.bean.enumerate.ChannelTypeEnum;
import com.gy.hsxt.uc.as.bean.result.AsOperatorLoginResult;

/**
 * 登录控制器，操作员的登录、登出等操作
 * 
 * @Package: com.gy.hsxt.access.web.common.controller
 * @ClassName: LoginController
 * @Description: TODO
 * 
 * @author: lixuan
 * @date: 2015-11-9 下午6:43:18
 * @version V1.0
 */
@Controller
@RequestMapping(value = "/login")
public class LoginController {
	@Autowired
	IUCLoginService loginService;
	@Autowired
	IUCAsTokenService tokenService;
	@Autowired
	IUCAsLogoutService logoutService;
	@Autowired
	@Qualifier("changeRedisUtil")
	RedisUtil<Object> changeRedisUtil;
	@Autowired
	IUCAsRoleService roleService;
	
	/**
	 * 登出
	 * 
	 * @param custId
	 *            客户号
	 * @param channelType
	 *            渠道类型
	 * @param randomToken
	 *            随机token
	 * @param userType
	 *            用户类型
	 * @return 登出信息
	 */
	@ResponseBody
	@RequestMapping(value = "operatorLogout", method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope operatorLogout(LoginParam param) {
		HttpRespEnvelope hre = new HttpRespEnvelope();
		ChannelTypeEnum chaType = ChannelTypeEnum.get(Integer
				.valueOf(param.getChannelType()));
		if (chaType == null) {
			hre.setRetCode(RespCode.AS_PARAM_INVALID.getCode());
			hre.setResultDesc("渠道类型值错误");
			hre.setSuccess(false);
			return hre;
		}

		try {
			logoutService.operatorLogout(chaType, null, param.getCustId());
		} catch (HsException e) {
			e.printStackTrace();
			hre.setRetCode(e.getErrorCode());
			hre.setResultDesc("退出登录失败");
			hre.setSuccess(false);
			return hre;
		}
		return hre;
	}

	/**
	 * 登录
	 * 
	 * @param param
	 *            登录参数
	 * @param randomToken
	 *            随机token
	 * @param channelType
	 *            渠道类型，如果为空默认为1，WEB方式登录
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "login", method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope login(LoginParam param) {
		HttpRespEnvelope hre = new HttpRespEnvelope();
		AsOperatorLoginInfo info = new AsOperatorLoginInfo();
		if(param.getChannelType() == null || param.getChannelType().equals("")){
			hre.setRetCode(RespCode.AS_OPT_FAILED.getCode());
			hre.setResultDesc("渠道类型为空");
			return hre;
		}
		// 拼装数据
		ChannelTypeEnum type = ChannelTypeEnum
				.get(Integer.valueOf(param.getChannelType()));
		if (type == null) {
			hre.setRetCode(RespCode.AS_OPT_FAILED.getCode());
			hre.setResultDesc("渠道类型不正确");
			return hre;
		}
		info.setChannelType(type);
		info.setLoginIp(param.getLoginIp());
		info.setLoginPwd(param.getLoginPwd());
		info.setPwdVer(param.getVersionNumber());
		info.setRandomToken(param.getRandomToken());
		info.setResNo(param.getResNo());
		info.setUserName(param.getUserName());
		
		Integer custType = HsResNoUtils.getCustTypeByHsResNo(param.getResNo());
		if(custType==null){//不能根据互生号获取企业类型
			hre.setRetCode(RespCode.UNKNOWN.getCode());
			hre.setResultDesc(param.getResNo()+"企业互生号异常，不能判断企业类型");
			hre.setSuccess(false);
			return hre;
		}
		
		info.setCustType(custType.toString());
		try {
			AsOperatorLoginResult rs = loginService.operatorLogin(info);
			hre.setData(rs);
		} catch (HsException hse) {
		    hse.printStackTrace();
			hre.setRetCode(hse.getErrorCode());
			hre.setResultDesc(hse.getMessage());
			hre.setSuccess(false);
			return hre;
		} catch (Exception e) {
		    e.printStackTrace();
			hre.setRetCode(RespCode.UNKNOWN.getCode());
			hre.setResultDesc(e.getMessage() + "，请求参数：" + param);
			hre.setSuccess(false);
			return hre;
		}
		return hre;
	}
}
