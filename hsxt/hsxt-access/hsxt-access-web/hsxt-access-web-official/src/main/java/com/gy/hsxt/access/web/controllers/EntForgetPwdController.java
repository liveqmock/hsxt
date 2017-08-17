/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.controllers;

import java.net.URLDecoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gy.hsi.lc.client.log4j.BizLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.access.web.bean.HttpResp;
import com.gy.hsxt.access.web.common.constant.ASRespCode;
import com.gy.hsxt.access.web.utils.ChannleType;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.Base64Utils;
import com.gy.hsxt.common.utils.HsResNoUtils;
import com.gy.hsxt.common.utils.StringUtils;
import com.gy.hsxt.uc.as.api.common.IUCAsEmailService;
import com.gy.hsxt.uc.as.api.common.IUCAsMobileService;
import com.gy.hsxt.uc.as.api.common.IUCAsPwdQuestionService;
import com.gy.hsxt.uc.as.api.common.IUCAsPwdService;
import com.gy.hsxt.uc.as.api.enumerate.UserTypeEnum;
import com.gy.hsxt.uc.as.api.operator.IUCAsOperatorService;
import com.gy.hsxt.uc.as.bean.common.AsPwdQuestion;
import com.gy.hsxt.uc.as.bean.common.AsResetLoginPwd;

/**
 * 企业忘记密码控制器
 * 
 * @Package: com.gy.hsxt.access.web.controllers
 * @ClassName: EntForgetPwdController
 * @Description: TODO
 * @author: likui
 * @date: 2016年1月4日 下午8:19:30
 * @company: gyist
 * @version V3.0.0
 */
@Controller
@RequestMapping("/gy/official")
public class EntForgetPwdController {

	/**
	 * 用户中心操作员Service
	 */
	@Autowired
	private IUCAsOperatorService asOperatorService;

	/**
	 * 用户中心手机Service
	 */
	@Autowired
	private IUCAsMobileService asMobileService;

	/**
	 * 用户中心邮箱Service
	 */
	@Autowired
	private IUCAsEmailService asEmailService;

	/**
	 * 用户中心密保Service
	 */
	@Autowired
	private IUCAsPwdQuestionService asPwdQuestionService;

	/**
	 * 用户中心密码Service
	 */
	@Autowired
	private IUCAsPwdService asPwdService;

	/**
	 * 查询企业操作员客户号
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年1月5日 下午6:11:15
	 * @param entResNo
	 *            企业互生号
	 * @param userName
	 *            用户名
	 * @param request
	 * @param response
	 * @return
	 * @return : HttpResp
	 * @version V3.0.0
	 */
	@ResponseBody
	@RequestMapping(value =
	{ "/entpwd/query_ent_opt_cust_id" }, method =
	{ RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpResp queryEntOptCustId(@RequestParam("entResNo") String entResNo,
			@RequestParam("userName") String userName, HttpServletRequest request, HttpServletResponse response)
	{
		BizLog.biz(this.getClass().getName(), "function:queryEntOptCustId", "params==>" + entResNo, "查询企业操作员客户号");
		// 企业操作员客户号
		String custId = "";
		if (!isOfficalForget(entResNo))
		{
			return new HttpResp(ASRespCode.AS_HSRESNO_ERROR.getCode());
		}
		try
		{
			custId = asOperatorService.findOperCustId(entResNo, userName);
			if (StringUtils.isNotBlank(custId))
			{
				return new HttpResp(RespCode.SUCCESS.getCode(), custId);
			}
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "function:queryEntOptCustId", "调用UC查询企业操作员客户号失败", ex);
		}
		return new HttpResp(RespCode.FAIL.getCode());
	}

	/**
	 * 是否是官网门户忘记密码
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年2月22日 下午3:40:58
	 * @param entResNo
	 * @return
	 * @return : boolean
	 * @version V3.0.0
	 */
	private boolean isOfficalForget(String entResNo)
	{
		if (HsResNoUtils.isServiceResNo(entResNo))
		{
			return true;
		}
		if (HsResNoUtils.isTrustResNo(entResNo))
		{
			return true;
		}
		if (HsResNoUtils.isMemberResNo(entResNo))
		{
			return true;
		}
		return false;
	}

	/**
	 * 管理公司查询操作员客户号
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年2月22日 下午3:42:14
	 * @param entResNo
	 * @param userName
	 * @param request
	 * @param response
	 * @return
	 * @return : HttpResp
	 * @version V3.0.0
	 */
	@ResponseBody
	@RequestMapping(value =
	{ "/entpwd/m_query_ent_opt_cust_id" }, method =
	{ RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpResp mQueryEntOptCustId(@RequestParam("entResNo") String entResNo,
			@RequestParam("userName") String userName, HttpServletRequest request, HttpServletResponse response)
	{
		BizLog.biz(this.getClass().getName(), "function:mQueryEntOptCustId", "params==>" + entResNo, "管理公司查询操作员客户号");
		// 管理公司操作员客户号
		String custId = "";
		if (!HsResNoUtils.isManageResNo(entResNo))
		{
			return new HttpResp(ASRespCode.AS_HSRESNO_ERROR.getCode());
		}
		try
		{
			custId = asOperatorService.findOperCustId(entResNo, userName);
			if (StringUtils.isNotBlank(custId))
			{
				return new HttpResp(RespCode.SUCCESS.getCode(), custId);
			}
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "function:queryEntOptCustId", "调用UC查询企业操作员客户号失败", ex);
		}
		return new HttpResp(RespCode.FAIL.getCode());
	}

	/**
	 * 发送手机短信验证码
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年1月4日 下午8:57:14
	 * @param optCustId
	 * @param mobile
	 * @param request
	 * @param response
	 * @return
	 * @return : HttpResp
	 * @version V3.0.0
	 */
	@ResponseBody
	@RequestMapping(value =
	{ "/entpwd/forget_pwd_send_sms" }, method =
	{ RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpResp forgetPwdSendSms(@RequestParam("optCustId") String optCustId,
			@RequestParam("mobile") String mobile, HttpServletRequest request, HttpServletResponse response)
	{
		BizLog.biz(this.getClass().getName(), "function:forgetPwdSendSms", "params==>optCustId:" + optCustId + ",mobile:"
				+ mobile, "发送手机短信验证码");
		try
		{
			String custId = new String(Base64Utils.decode(optCustId));
			asMobileService.sendSmsCodeForResetPwd(custId, UserTypeEnum.OPERATOR.getType(), mobile,
					HsResNoUtils.getCustTypeByHsResNo(custId.substring(0, 11)));
			return new HttpResp(RespCode.SUCCESS.getCode());
		} catch (HsException ex)
		{
			// 160140 手机号码不正确
			// 160101 客户号不存在
			// 160150 用户类型非法
			return new HttpResp(ex.getErrorCode());
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "function:forgetPwdSendSms", "调用UC发送手机短信验证码失败", ex);
		}
		return new HttpResp(RespCode.FAIL.getCode());
	}

	/**
	 * 验证短信验证码
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年1月4日 下午9:03:25
	 * @param mobile
	 * @param vaildCode
	 * @param request
	 * @param response
	 * @return
	 * @return : HttpResp
	 * @version V3.0.0
	 */
	@ResponseBody
	@RequestMapping(value =
	{ "/entpwd/vaild_send_sms_vaild_code" }, method =
	{ RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpResp vaildSendSmsVaildCode(@RequestParam("mobile") String mobile,
			@RequestParam("vaildCode") String vaildCode, HttpServletRequest request, HttpServletResponse response)
	{
		BizLog.biz(this.getClass().getName(), "function:vaildSendSmsVaildCode", "params==>vaildCode:" + vaildCode
				+ ",mobile:" + mobile, "验证短信验证码");
		try
		{
			String resp = asMobileService.checkSmsCode(mobile, vaildCode);
			return new HttpResp(RespCode.SUCCESS.getCode(), resp);
		} catch (HsException ex)
		{
			// 160134 短信验证码已过期
			// 160133 短信验证码不正确
			return new HttpResp(ex.getErrorCode());
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "function:vaildSendSmsVaildCode", "调用UC验证短信验证码失败", ex);
		}
		return new HttpResp(RespCode.FAIL.getCode());
	}

	/**
	 * 发送邮件
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年1月4日 下午9:06:24
	 * @param optCustId
	 * @param email
	 * @param request
	 * @param response
	 * @return
	 * @return : HttpResp
	 * @version V3.0.0
	 */
	@ResponseBody
	@RequestMapping(value =
	{ "/entpwd/forget_pwd_send_email" }, method =
	{ RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpResp forgetPwdSendEmail(@RequestParam("optCustId") String optCustId,
			@RequestParam("email") String email, HttpServletRequest request, HttpServletResponse response)
	{
		BizLog.biz(this.getClass().getName(), "function:forgetPwdSendEmail", "params==>optCustId:" + optCustId
				+ ",email:" + email, "发送邮件");
		try
		{
			String custId = new String(Base64Utils.decode(optCustId));
			asEmailService.sendMailForRestPwd(custId, UserTypeEnum.OPERATOR.getType(), email,
					HsResNoUtils.getCustTypeByHsResNo(custId.substring(0, 11)));
			return new HttpResp(RespCode.SUCCESS.getCode());
		} catch (HsException ex)
		{
			// 160353 邮箱不正确
			return new HttpResp(ex.getErrorCode());
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "function:forgetPwdSendEmail", "调用UC发送邮件失败", ex);
		}
		return new HttpResp(RespCode.FAIL.getCode());
	}

	/**
	 * 验证邮件地址是否有效
	 * 
	 * @param email
	 * @param vaildCode
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value =
	{ "/entpwd/vaild_send_email_address" }, method =
	{ RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpResp vaildSendEmailAddress(@RequestParam("email") String email,
			@RequestParam("vaildCode") String vaildCode, HttpServletRequest request, HttpServletResponse response)
	{
		BizLog.biz(this.getClass().getName(), "function:vaildSendEmailAddress", "params==>vaildCode:" + vaildCode
				+ ",email:" + email, "验证邮件地址是否有效");
		try
		{
			String resp = asEmailService.checkMail(new String(Base64Utils.decode(email)), new String(Base64Utils.decode(vaildCode)));
			return new HttpResp(RespCode.SUCCESS.getCode(), resp);
		} catch (HsException ex)
		{
			// 160136 非法操作,随机验证码不正确
			// 160137 邮件链接已过期
			return new HttpResp(ex.getErrorCode());
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "function:vaildSendEmailAddress", "调用UC验证邮件地址是否有效失败", ex);
		}
		return new HttpResp(RespCode.FAIL.getCode());
	}

	/**
	 * 查询所有的密保问题
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年1月5日 上午9:06:03
	 * @param request
	 * @param response
	 * @return
	 * @return : HttpResp
	 * @version V3.0.0
	 */
	@ResponseBody
	@RequestMapping(value =
	{ "/entpwd/query_pwd_question_all" }, method =
	{ RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpResp queryPwdQuestionAll(HttpServletRequest request, HttpServletResponse response)
	{
		BizLog.biz(this.getClass().getName(), "function:queryPwdQuestionAll", "", "查询所有的密保问题");
		try
		{
			// 查询所有密保问题
			List<AsPwdQuestion> beans = asPwdQuestionService.listDefaultPwdQuestions();
			if (StringUtils.isNotBlank(beans))
			{
				return new HttpResp(RespCode.SUCCESS.getCode(), beans);
			}
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "function:queryPwdQuestionAll", "调用UC查询所有的密保问题失败", ex);
		}
		return new HttpResp(RespCode.FAIL.getCode());
	}

	/**
	 * 验证密保问题
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年1月21日 下午3:32:09
	 * @param optCustId
	 * @param question
	 * @param answer
	 * @param request
	 * @param response
	 * @return
	 * @return : HttpResp
	 * @version V3.0.0
	 */
	@ResponseBody
	@RequestMapping(value =
	{ "/entpwd/vaild_pwd_question" }, method =
	{ RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpResp vaildPwdQuestion(@RequestParam("optCustId") String optCustId,
			@RequestParam("question") String question, @RequestParam("answer") String answer,
			HttpServletRequest request, HttpServletResponse response)
	{
		BizLog.biz(this.getClass().getName(), "function:vaildPwdQuestion", "params==>optCustId:" + optCustId
				+ ",question:" + question + ",answer:" + answer, "验证密保问题");
		try
		{ // 密保问题答案实体
			AsPwdQuestion bean = new AsPwdQuestion();
			bean.setQuestion(URLDecoder.decode(question, "UTF-8"));
			bean.setAnswer(URLDecoder.decode(answer, "UTF-8")); // 验证密保问题答案
			String resp = asPwdQuestionService.checkPwdQuestion(new String(Base64Utils.decode(optCustId)),
					UserTypeEnum.ENT.getType(), bean);
			return new HttpResp(RespCode.SUCCESS.getCode(), resp);
		} catch (HsException ex)
		{
			// 160119 密保答案不正确
			// 160120 密保问题不正确
			return new HttpResp(ex.getErrorCode());
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "function:vaildPwdQuestion", "调用UC验证密保问题失败", ex);
		}
		return new HttpResp(RespCode.FAIL.getCode());
	}

	/**
	 * 重置操作员登录密码
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年1月20日 下午6:10:56
	 * @param optCustId
	 * @param newPwd
	 * @param token
	 * @param channleType
	 * @param request
	 * @param response
	 * @return
	 * @return : HttpResp
	 * @version V3.0.0
	 */
	@ResponseBody
	@RequestMapping(value =
	{ "/entpwd/reset_opt_login_pwd" }, method =
	{ RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpResp resetOptLoginPwd(@RequestParam("optCustId") String optCustId,
			@RequestParam("newPwd") String newPwd, @RequestParam("token") String token,
			@RequestParam("channleType") String channleType, @RequestParam("randomCode") String randomCode,
			@RequestParam("mobile") String mobile, @RequestParam("email") String email, HttpServletRequest request,
			HttpServletResponse response)
	{
		BizLog.biz(this.getClass().getName(), "function:resetOptLoginPwd", "params==>optCustId:"+optCustId+",token:" + token + ",randomCode:"
				+ randomCode + ",mobile:" + mobile + ",email:" + email, "重置操作员登录密码");
		try
		{
			// 设置重置密码的参数
			AsResetLoginPwd bean = new AsResetLoginPwd();
			bean.setCustId(new String(Base64Utils.decode(optCustId)));
			bean.setNewLoginPwd(newPwd);
			bean.setSecretKey(new String(Base64Utils.decode(token)));
			bean.setRandom(new String(Base64Utils.decode(randomCode)));
			if (StringUtils.isNotBlank(mobile))
			{
				bean.setMobile(new String(Base64Utils.decode(mobile)));
			}
			if (StringUtils.isNotBlank(email))
			{
				bean.setEmail(new String(Base64Utils.decode(email)));
			}
			channleType = new String(Base64Utils.decode(channleType));
			switch (ChannleType.getCode(channleType))
			{
			case PHONE:
				bean.setUserType(UserTypeEnum.OPERATOR.getType());
				asPwdService.resetLoginPwdByMobile(bean);
				break;
			case EMAIL:
				bean.setUserType(UserTypeEnum.OPERATOR.getType());
				asPwdService.resetLoginPwdByEmail(bean);
				break;
			case QUESTION:
				bean.setUserType(UserTypeEnum.ENT.getType());
				asPwdService.resetLoginPwdByCrypt(bean);
				break;
			default:
				break;
			}
			return new HttpResp(RespCode.SUCCESS.getCode());
		} catch (HsException ex)
		{
			// 160404 重置登录密码的短信验证token已过期
			// 160405 重置登录密码的短信验证token不正确
			// 160406 重置登录密码的邮件验证token已过期
			// 160407 重置登录密码的邮件验证token不正确
			// 160408 重置登录密码的密保验证token已过期
			// 160409 重置登录密码的密保验证token不正确
			return new HttpResp(ex.getErrorCode());
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "function:vaildPwdQuestion", "调用UC重置操作员登录密码失败", ex);
		}
		return new HttpResp(RespCode.FAIL.getCode());
	}
}
