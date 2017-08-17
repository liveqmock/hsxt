/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.common.controller;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.gy.hsi.ds.param.HsPropertiesConfigurer;
import com.gy.hsi.redis.client.api.RedisUtil;
import com.gy.hsxt.access.web.common.bean.HttpRespEnvelope;
import com.gy.hsxt.access.web.common.bean.LoginParam;
import com.gy.hsxt.common.constant.AcrossPlatBizCode;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.DateUtil;
import com.gy.hsxt.common.utils.HsResNoUtils;
import com.gy.hsxt.common.utils.StringUtils;
import com.gy.hsxt.common.utils.ValidateCodeUtils;
import com.gy.hsxt.uc.as.api.common.IUCAsTokenService;
import com.gy.hsxt.uc.as.api.common.IUCLoginService;
import com.gy.hsxt.uc.as.api.util.UcCacheKey;
import com.gy.hsxt.uc.as.bean.common.AsConsumerLoginInfo;
import com.gy.hsxt.uc.as.bean.common.AsOperatorLoginInfo;
import com.gy.hsxt.uc.as.bean.enumerate.ChannelTypeEnum;
import com.gy.hsxt.uc.as.bean.result.AsCardHolderLoginResult;
import com.gy.hsxt.uc.as.bean.result.AsNoCardHolderLoginResult;
import com.gy.hsxt.uc.as.bean.result.AsOperatorLoginResult;
import com.gy.hsxt.uf.UFRegionPacketSupport;
import com.gy.hsxt.uf.bean.packet.RegionPacketBody;
import com.gy.hsxt.uf.bean.packet.RegionPacketHeader;

/**
 * 登录控制器，负责所有用户的登录、登出等操作
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
	@Autowired
	@Qualifier("changeRedisUtil")
	RedisUtil<Object> changeRedisUtil;

	/**
	 * 本地综合前置
	 */
	// @Autowired
	UFRegionPacketSupport ufRegionPacketService;
	// 业务代码
	final String bizCode = AcrossPlatBizCode.TO_UC_LOGIN.name();

	// 子系统代码
	// final String systemId = "1000";
	// 持卡人登录类型
	final String CARDER_LOGIN_TYPE = "1";
	// 操作员登录类型
	final String OPERATOR_LOGIN_TYPE = "2";

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
			@RequestBody(required = false) LoginParam param,
			@RequestParam(required = false) String custId) {
		HttpRespEnvelope hre = new HttpRespEnvelope();
		if (param != null) {
			String token = tokenService.getRandomToken(param.getCustId());
			hre.setData(token);
		} else if (custId != null) {
			hre.setData(tokenService.getRandomToken(custId));
			hre.setSuccess(false);

		} else {
			hre.setData(tokenService.getRandomToken(""));
			hre.setSuccess(false);
		}
		return hre;
	}

	/**
	 * 需要校验验证码的非持卡人登录
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
	@RequestMapping(value = "noCardholderChkCodeLogin", method = RequestMethod.POST)
	public HttpRespEnvelope noCardholderChkCodeLogin(
			@RequestBody LoginParam param,
			@RequestParam(required = false) String randomToken,
			@RequestParam(required = false) String channelType,
			HttpServletRequest request) {
		return noCardholderLogin(param, randomToken, channelType, request);
	}

	/**
	 * 非持卡人登录
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
	@RequestMapping(value = "noCardholderLogin", method = RequestMethod.POST)
	public HttpRespEnvelope noCardholderLogin(@RequestBody LoginParam param,
			@RequestParam(required = false) String randomToken,
			@RequestParam(required = false) String channelType,
			HttpServletRequest request) {
		param.setChannelType(channelType);

		//System.out.println("开始登录:" + param);
		HttpRespEnvelope hre = new HttpRespEnvelope();
		AsConsumerLoginInfo loginInfo = new AsConsumerLoginInfo();

		ChannelTypeEnum type = ChannelTypeEnum
				.get(Integer.valueOf(channelType));
		if (type == null) {
			hre.setRetCode(RespCode.AS_PARAM_INVALID.getCode());
			hre.setResultDesc("渠道类型值错误");
			hre.setSuccess(false);
			return hre;
		}
		loginInfo.setChannelType(type);
		loginInfo.setRandomToken(randomToken);
		loginInfo.setLoginPwd(param.getLoginPwd());
		loginInfo.setMobile(param.getMobile());
		loginInfo.setRandomToken(randomToken);
		if (StringUtils.isEmpty(param.getLoginIp())) {
			param.setLoginIp(getIpAddr(request));
		}
		loginInfo.setLoginIp(param.getLoginIp());
		try {
			AsNoCardHolderLoginResult rs = loginService
					.noCardHolderLogin(loginInfo);
			hre.setData(rs);
		} catch (HsException hse) {
			hre = handleModifyPasswordException(hse,param);
		} catch (Exception e) {
			System.out.println("--未知异常，非持卡人登录请求参数：" + param);
			e.printStackTrace();
			hre.setRetCode(RespCode.UNKNOWN.getCode());
			hre.setResultDesc(e.getMessage());
			hre.setSuccess(false);
		}
		return hre;
	}

	private String getIpAddr(HttpServletRequest request) {
		String ipAddress = null;
		ipAddress = request.getHeader("x-forwarded-for");
		if (ipAddress == null || ipAddress.length() == 0
				|| "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("Proxy-Client-IP");
		}
		if (ipAddress == null || ipAddress.length() == 0
				|| "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("WL-Proxy-Client-IP");
		}
		if(ipAddress == null || ipAddress.length() == 0
				|| "unknown".equalsIgnoreCase(ipAddress) ){
			ipAddress = request.getHeader("X-Real-IP");
		}
		if (ipAddress == null || ipAddress.length() == 0
				|| "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getRemoteAddr();
		}
		
		if (ipAddress.equals("127.0.0.1")) {
			// 根据网卡取本机配置的IP
			InetAddress inet = null;
			try {
				inet = InetAddress.getLocalHost();
			} catch (UnknownHostException e) {
				e.printStackTrace();
				//logger.error("获取用户IP地址失败！异常信息:", e);
				return "";
			}
			ipAddress = inet.getHostAddress();
		}
		// 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
		if (ipAddress != null && ipAddress.length() > 15) {
			// "***.***.***.***".length()
			// = 15
			if (ipAddress.indexOf(",") > 0) {
				ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
			}
		}
		//System.out.println("获取的真实IP：" + ipAddress);
		return ipAddress;
	}
	
	/**
	 * 需要校验验证码的持卡人登录
	 * 
	 * @param resNo
	 *            持卡人资源号
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
	@RequestMapping(value = "cardholderChkCodeLogin", method = RequestMethod.POST)
	public HttpRespEnvelope cardholderChkCodeLogin(
			@RequestBody LoginParam param, @RequestParam String randomToken,
			@RequestParam(required = false) String channelType,
			HttpServletRequest request) {
		return cardholderLogin(param, randomToken, channelType, request);
	}

	/**
	 * 持卡人登录
	 * 
	 * @param resNo
	 *            持卡人资源号
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
	@RequestMapping(value = "cardholderLogin", method = RequestMethod.POST)
	public HttpRespEnvelope cardholderLogin(@RequestBody LoginParam param,
			@RequestParam String randomToken,
			@RequestParam(required = false) String channelType,
			HttpServletRequest request) {
		param.setChannelType(channelType);
		//System.out.println("开始登录:" + param);
		// 验证数据正确性
		HttpRespEnvelope hre = new HttpRespEnvelope();
		AsConsumerLoginInfo loginInfo = new AsConsumerLoginInfo();

		ChannelTypeEnum type = ChannelTypeEnum
				.get(Integer.valueOf(channelType));
		if (type == null) {
			hre.setRetCode(RespCode.AS_PARAM_INVALID.getCode());
			hre.setResultDesc("渠道类型值错误");
			hre.setSuccess(false);
			return hre;
		}
		// 组装请求对象
		loginInfo.setChannelType(type);
		if (StringUtils.isEmpty(param.getLoginIp())) {
			loginInfo.setLoginIp(getIpAddr(request));
		}
		else{
			loginInfo.setLoginIp(param.getLoginIp());
		}
		loginInfo.setLoginPwd(param.getLoginPwd());
		loginInfo.setResNo(param.getResNo());
		loginInfo.setRandomToken(randomToken);
		
		AsCardHolderLoginResult rs = null;
		try {
			// 判断是否是本地登录
			// 如果是异地登录
			if (!isLocal(param.getResNo())) {
				RegionPacketHeader header = RegionPacketHeader.build();
				header.setDestResNo(param.getResNo());
				header.setDestBizCode(bizCode);
				header.setSendDateTime(DateUtil.getCurrentDate());
				header.addObligateArgs("type", CARDER_LOGIN_TYPE);
				RegionPacketBody body = RegionPacketBody.build(loginInfo);
				// 调用本地综合前置接口，返回异地的登录信息
				rs = (AsCardHolderLoginResult) ufRegionPacketService
						.sendSyncRegionPacket(header, body);
			}
			// 如果是本地登录
			else {
				rs = loginService.cardHolderLogin(loginInfo);
			}
			hre.setData(rs);
		} catch (HsException hse) {
			hre = handleModifyPasswordException(hse,param);
		} catch (Exception e) {
			System.out.println("--未知异常，持卡人登录请求参数：" + param);
			e.printStackTrace();
			hre.setRetCode(RespCode.UNKNOWN.getCode());
			hre.setResultDesc(e.getMessage());
			hre.setSuccess(false);
		}
		return hre;
	}

	/**
	 * 操作员使用验证码
	 * @param param
	 * @param randomToken
	 * @param channelType
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "operatorChkCodeLogin", method = RequestMethod.POST)
	public HttpRespEnvelope operatorChkCodeLogin(@RequestBody LoginParam param,
			@RequestParam String randomToken,
			@RequestParam(required = false) String channelType,
			HttpServletRequest request) {
		return operatorLogin(param, randomToken, channelType, request);
	}

	/**
	 * 操作员登录
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
	@RequestMapping(value = "operatorLogin", method = RequestMethod.POST)
	public HttpRespEnvelope operatorLogin(@RequestBody LoginParam param,
			@RequestParam String randomToken,
			@RequestParam(required = false) String channelType,
			HttpServletRequest request) {
		param.setChannelType(channelType);
		//System.out.println("开始登录:" + param.toString());
		HttpRespEnvelope hre = new HttpRespEnvelope();
		AsOperatorLoginInfo info = new AsOperatorLoginInfo();
		ChannelTypeEnum type = ChannelTypeEnum
				.get(Integer.valueOf(channelType));
		if (type == null) {
			hre.setRetCode(RespCode.AS_OPT_FAILED.getCode());
			hre.setResultDesc("渠道类型值错误");
			hre.setSuccess(false);
			return hre;
		}
		String userName=param.getUserName();
		if(!StringUtils.isBlank(userName)){ //企业互生号+操作号登录
			// 检查处理 企业类型
			Integer custType = 0;
			if(StringUtils.isBlank(param.getCustType())){//页面不传企业类型
				custType = HsResNoUtils.getCustTypeByHsResNo(param.getResNo());
				if(custType==null){//不能根据互生号获取企业类型
					hre.setRetCode(RespCode.UNKNOWN.getCode());
					hre.setResultDesc(param.getResNo()+"企业互生号异常，不能判断企业类型");
					hre.setSuccess(false);
					return hre;
				}
				if(custType>4){//管理公司，地区平台企业互生号登录必须传企业类型
					hre.setRetCode(RespCode.UNKNOWN.getCode());
					hre.setResultDesc("本接口只能处理2成员企业3托管企业及4服务公司登录");
					hre.setSuccess(false);
					return hre;
				}
				info.setCustType(custType.toString());
			}else{//使用页面传入 企业类型
				info.setCustType(param.getCustType());
			}
			
		}else{//使用页面传入 企业类型
			info.setCustType(param.getCustType());
			
		}
		
		// 组装请求数据
		info.setChannelType(type);
		if (StringUtils.isEmpty(param.getLoginIp())) {
			param.setLoginIp(getIpAddr(request));
		}
		info.setLoginIp(param.getLoginIp());
		info.setLoginPwd(param.getLoginPwd());
		info.setPwdVer(param.getVersionNumber());
		info.setRandomToken(randomToken);
		info.setResNo(param.getResNo());
		info.setUserName(param.getUserName());
		
		
		// 判断是否是本地登录
		AsOperatorLoginResult rs = null;
		
		try {
			if (!isLocal(param.getResNo())) {//异地登录
				RegionPacketHeader header = RegionPacketHeader.build();
				header.setDestResNo(param.getResNo());
				header.setDestBizCode(bizCode);
				header.setSendDateTime(DateUtil.getCurrentDate());
				header.addObligateArgs("type", OPERATOR_LOGIN_TYPE);
				RegionPacketBody body = RegionPacketBody.build(info);

				// 调用本地综合前置接口，返回异地的登录信息
				rs = (AsOperatorLoginResult) ufRegionPacketService
						.sendSyncRegionPacket(header, body);
			}
			
			else {// 是本地登录
				if(StringUtils.isBlank(param.getUserName())){
					//个人绑定互生卡登录
					rs = loginService.operatorLoginByCard(info);
				}else{
					 //企业互生号+操作号登录
					rs = loginService.operatorLogin(info);	
				}
				
			}
			hre.setData(rs);
		} catch (HsException hse) {
			hre = handleModifyPasswordException(hse,param);
		} catch (Exception e) {
			System.out.println("--未知异常，非持卡人登录请求参数：" + param);
			e.printStackTrace();
			hre.setRetCode(RespCode.UNKNOWN.getCode());
			hre.setResultDesc(e.getMessage());
			hre.setSuccess(false);
		}
		return hre;
	}
	
	 private HttpRespEnvelope handleModifyPasswordException(HsException e,LoginParam param){
		 	e.printStackTrace();
	    	HttpRespEnvelope hre = null;
	    	int errorCode = e.getErrorCode();
	    	String errorMsg = e.getMessage();
	    	StringBuffer msg = new StringBuffer();
	    	msg.append("处理修改登录密码Exception时报错,传入参数信息  e["+JSON.toJSONString(e)+"] \r\n");
	    	StringBuffer message = new StringBuffer();
	    	message.append("登录验证失败，您今天还剩");
	    	if(160359 == errorCode || 160108 == errorCode || 160444 == errorCode){
	     		try {
	        			if(StringUtils.isNotBlank(errorMsg) && errorMsg.contains(",")){
	            			String[] msgInfo = errorMsg.split(",");
	            			if(msgInfo.length > 1){
	            				int loginFailTimes = Integer.parseInt(msgInfo[0]);
	            				int maxFailTimes = Integer.parseInt(msgInfo[1]);
	            				int tryTimes = maxFailTimes - loginFailTimes;
	            				message.append(tryTimes).append("次尝试机会。");
	            			}
	            		}
	            		
	    			} catch (Exception e2) {
	    				System.out.println("error:  "+msg.toString());
	    				e2.printStackTrace();
	    			}
	    		hre = new HttpRespEnvelope(e.getErrorCode(),message.toString());
	    	}else if(160467 == errorCode){
	    		hre = new HttpRespEnvelope(e.getErrorCode(),e.getMessage());
	    	}else{
	    		hre = new HttpRespEnvelope(e);
	    		hre.setRetCode(e.getErrorCode());
	    		hre.setResultDesc(e.getMessage() + "，请求参数[" + JSON.toJSONString(param)+"]");
	    	}
	    	hre.setSuccess(false);
	    	return hre;
	    }

	
	
	
	/**
	 * 获取验证码
	 * 
	 * @return 随机token
	 */
	@ResponseBody
	@RequestMapping(value = "getCheckCode", method = RequestMethod.GET)
	public void getCheckCode(HttpServletRequest request,
			HttpServletResponse response) {
		//System.out.println("开始获取验证码" + DateUtil.getCurrentDateTime());
		String randomToken = request.getParameter("randomToken");
		// 获取验证码
//		Object[] obj = VaildCodeUtils.createValidCodeImage(new VaildCodeParam(
//				4, 1));
		
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
			//String   redisKey="UC.checkCode_" + randomToken; //
			String redisKey=UcCacheKey.genCheckCodeKey(randomToken);
			changeRedisUtil.add(redisKey,  code);
			//验证码增加过期时间，默认3小时
			changeRedisUtil.setExpireInMinutes(redisKey, getCheckCodeValiTime());
			//System.out.println(DateUtil.getCurrentDateTime() + "生成的验证码：" + code );
			
			//ImageIO.write((BufferedImage) obj[1], "jpeg", sos);
			sos.close();
			
			//增加日志分析验证法错误异常
			HttpSession session=request.getSession(false);
			if(session!=null){
				String sessionToken=(String)session.getAttribute("randomToken");
				if(sessionToken!=null){//上次验证码输入错误，增加信息记录以便分析原因
					session.setAttribute("randomToken",randomToken);
					session.setAttribute("checkCode", code);
				}
			}
			//System.out.println("验证码生成成功，并返回" + DateUtil.getCurrentDateTime()  );
		} catch (Exception ex) {
			ex.printStackTrace();
			
		}
	}

	/**
	 * 操作员登录
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
	@RequestMapping(value = "operatorLoginUseTwoPwd", method = RequestMethod.POST)
	public HttpRespEnvelope operatorLoginUseTwoPwd(@RequestBody LoginParam param,
			@RequestParam String randomToken,
			@RequestParam(required = false) String channelType,
			HttpServletRequest request) {
		param.setChannelType(channelType);
		HttpRespEnvelope hre = new HttpRespEnvelope();
		AsOperatorLoginInfo info = new AsOperatorLoginInfo();
		ChannelTypeEnum type = ChannelTypeEnum
				.get(Integer.valueOf(channelType));
		if (type == null) {
			hre.setRetCode(RespCode.AS_OPT_FAILED.getCode());
			hre.setResultDesc("渠道类型值错误");
			hre.setSuccess(false);
			return hre;
		}
		if(StringUtils.isBlank(param.getSecondPwd())){
			hre.setRetCode(RespCode.PARAM_ERROR.getCode());
			hre.setResultDesc("第2个密码不能为空");
			hre.setSuccess(false);
			return hre;
		}
		if(StringUtils.isBlank(param.getUserName())){
			hre.setRetCode(RespCode.PARAM_ERROR.getCode());
			hre.setResultDesc("用户名不能为空");
			hre.setSuccess(false);
			return hre;
		}
		if(StringUtils.isBlank(param.getLoginPwd())){
			hre.setRetCode(RespCode.PARAM_ERROR.getCode());
			hre.setResultDesc("第1个密码不能为空");
			hre.setSuccess(false);
			return hre;
		}
		if(StringUtils.isBlank(param.getResNo())){
			hre.setRetCode(RespCode.PARAM_ERROR.getCode());
			hre.setResultDesc("企业资源号不能为空");
			hre.setSuccess(false);
			return hre;
		}
		String userName=param.getUserName();
		if(!StringUtils.isBlank(userName)){ //企业互生号+操作号登录
			// 检查处理 企业类型
			Integer custType = 0;
			if(StringUtils.isBlank(param.getCustType())){//页面不传企业类型
				custType = HsResNoUtils.getCustTypeByHsResNo(param.getResNo());
				if(custType==null){//不能根据互生号获取企业类型
					hre.setRetCode(RespCode.UNKNOWN.getCode());
					hre.setResultDesc(param.getResNo()+"企业互生号异常，不能判断企业类型");
					hre.setSuccess(false);
					return hre;
				}
				if(custType>4){//管理公司，地区平台企业互生号登录必须传企业类型
					hre.setRetCode(RespCode.UNKNOWN.getCode());
					hre.setResultDesc("本接口只能处理2成员企业3托管企业及4服务公司登录");
					hre.setSuccess(false);
					return hre;
				}
				info.setCustType(custType.toString());
			}else{//使用页面传入 企业类型
				info.setCustType(param.getCustType());
			}
			
		}else{//使用页面传入 企业类型
			info.setCustType(param.getCustType());
		}
		
		// 组装请求数据
		info.setChannelType(type);
		if (StringUtils.isEmpty(param.getLoginIp())) {
			param.setLoginIp(getIpAddr(request));
		}
		info.setLoginIp(param.getLoginIp());
		info.setLoginPwd(param.getLoginPwd());
		info.setPwdVer(param.getVersionNumber());
		info.setRandomToken(randomToken);
		info.setResNo(param.getResNo());
		info.setUserName(param.getUserName());
		
		// 判断是否是本地登录
		AsOperatorLoginResult rs = null;
		
		try {
			if (!isLocal(param.getResNo())) {//异地登录
				RegionPacketHeader header = RegionPacketHeader.build();
				header.setDestResNo(param.getResNo());
				header.setDestBizCode(bizCode);
				header.setSendDateTime(DateUtil.getCurrentDate());
				header.addObligateArgs("type", OPERATOR_LOGIN_TYPE);
				RegionPacketBody body = RegionPacketBody.build(info);

				// 调用本地综合前置接口，返回异地的登录信息
				rs = (AsOperatorLoginResult) ufRegionPacketService
						.sendSyncRegionPacket(header, body);
			}
			else {// 是本地登录
				rs = loginService.operatorLogin2(info, param.getSecondPwd());
			}
			hre.setData(rs);
		} catch (HsException hse) {
			hre = handleModifyPasswordException(hse,param);
		} catch (Exception e) {
			System.out.println("--未知异常，非持卡人登录请求参数：" + param);
			e.printStackTrace();
			hre.setRetCode(RespCode.UNKNOWN.getCode());
			hre.setResultDesc(e.getMessage());
			hre.setSuccess(false);
		}
		return hre;
	}
	
	/**
	 * 验证互生号是否是异地
	 * 
	 * @param resNo
	 * @return
	 */
	private boolean isLocal(String resNo) {
		try {
			// return baseDataService.isAcrossPlat(resNo);
			return true;
		} catch (Exception e) {
			return true;
		}
	}
	
	public long getCheckCodeValiTime(){
		String minute=HsPropertiesConfigurer.getProperty("checkCodeValiTime");
		if(StringUtils.isBlank(minute)){
			return 60*3; //3小时
		}else{
			return Integer.parseInt(minute);
		}
	}



}
