/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.access.web.common.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gy.hsxt.access.web.common.bean.HttpRespEnvelope;
import com.gy.hsxt.access.web.common.bean.LoginParam;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.uc.as.api.common.IUCAsLogoutService;
import com.gy.hsxt.uc.as.api.enumerate.UserTypeEnum;
import com.gy.hsxt.uc.as.bean.enumerate.ChannelTypeEnum;

/**
 * 登出
 * 
 * @Package: com.gy.hsxt.access.web.common.controller  
 * @ClassName: LogoutController 
 * @Description: TODO
 *
 * @author: lixuan 
 * @date: 2015-12-18 上午11:56:48 
 * @version V1.0
 */
@Controller
@RequestMapping(value = "/logout")
public class LogoutController {

	/**
	 * 登出操作接口
	 */
	@Autowired
	IUCAsLogoutService logoutService;
	
	/**
	 * 操作员登出
	 * 
	 * @param custId
	 *            客户号
	 * @param channelType
	 *            渠道类型
	 * @param randomToken
	 *            随机token
	 * @return 登出信息
	 */
	@ResponseBody
	@RequestMapping(value = "operatorLogout", method = RequestMethod.POST)
	public HttpRespEnvelope operatorLogout(@RequestBody LoginParam param,
			@RequestParam(required = false) String channelType) {
		HttpRespEnvelope hre = new HttpRespEnvelope();

		if (param.getCustId() == null || param.getCustId().equals("")) {
			hre.setRetCode(RespCode.AS_PARAM_INVALID.getCode());
			hre.setResultDesc("操作员客户号不存在");
			hre.setSuccess(false);
			return hre;
		}
		ChannelTypeEnum chaType = ChannelTypeEnum.get(Integer
				.valueOf(channelType));
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
	@RequestMapping(value = "consumerLogout", method = RequestMethod.POST)
	public HttpRespEnvelope consumerLogout(@RequestBody LoginParam param,
			@RequestParam(required = false) String channelType) {
		HttpRespEnvelope hre = new HttpRespEnvelope();

		ChannelTypeEnum chaType = ChannelTypeEnum.get(Integer
				.valueOf(channelType));
		if (chaType == null) {
			hre.setRetCode(RespCode.AS_PARAM_INVALID.getCode());
			hre.setResultDesc("渠道类型值错误");
			hre.setSuccess(false);
			return hre;
		}
		if(StringUtils.isEmpty(param.getCustId())){
			hre.setRetCode(RespCode.AS_PARAM_INVALID.getCode());
			hre.setResultDesc("用户未登录，客户号为空");
			hre.setSuccess(false);
			return hre;
		}

//		UserTypeEnum userTypeEnum = UserTypeEnum.getUserTypeEnum(param
//				.getUserType());
//		if (userTypeEnum == null) {
//			hre.setRetCode(RespCode.AS_PARAM_INVALID.getCode());
//			hre.setResultDesc("用户类型值错误");
//			hre.setSuccess(false);
//			return hre;
//		}

		try {
			logoutService.logoutByCustId(param.getCustId(), chaType);
		} catch (HsException e) {
			e.printStackTrace();
			hre.setRetCode(e.getErrorCode());
			hre.setResultDesc("退出登录失败");
			hre.setSuccess(false);
			return hre;
		}
		return hre;
	}

}
