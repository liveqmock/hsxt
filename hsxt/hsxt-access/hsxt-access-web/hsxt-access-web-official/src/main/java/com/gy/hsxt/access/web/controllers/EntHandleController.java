/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.controllers;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.access.web.bean.HttpResp;
import com.gy.hsxt.access.web.service.IEntHandleService;
import com.gy.hsxt.common.constant.RespCode;

/**
 * 企业办理控制器
 * 
 * @Package: com.gy.hsxt.access.web.controllers
 * @ClassName: EntHandleController
 * @Description: TODO
 * @author: likui
 * @date: 2015年12月9日 下午4:11:02
 * @company: gyist
 * @version V3.0.0
 */
@Controller
@RequestMapping("/gy/official")
public class EntHandleController {

	/** 企业办理Service **/
	@Autowired
	private IEntHandleService entHandleService;

	/**
	 * 查询所有服务公司
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2015年12月10日 上午9:20:51
	 * @param request
	 * @param response
	 * @return
	 * @return : HttpRespEnvelope
	 * @version V3.0.0
	 */
	@ResponseBody
	@RequestMapping(value =
	{ "/entHandle/query_all_ent_info_s" }, method =
	{ RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpResp queryAllEntInfoS(@RequestParam("cityName") String cityName, HttpServletRequest request,
			HttpServletResponse response)
	{
		return entHandleService.selectAllEntInfoS(cityName);
	}

	/**
	 * 提交意向客户申请
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2015年12月10日 上午9:32:05
	 * @param intent
	 * @param request
	 * @param response
	 * @return
	 * @return : HttpRespEnvelope
	 * @version V3.0.0
	 */
	@ResponseBody
	@RequestMapping(value =
			{ "/entHandle/add_intention_cust_apply" }, method =
			{ RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpResp addIntentionCustApply(String intent, HttpServletRequest request, HttpServletResponse response)
	{
		return entHandleService.submitIntentionCustApply(intent);
	}

	/**
	 * 根据名称查询企业信息
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2015年12月9日 下午4:20:19
	 * @param entCustName
	 * @param request
	 * @param response
	 * @return
	 * @return : HttpRespEnvelope
	 * @version V3.0.0
	 */
	@ResponseBody
	@RequestMapping(value =
	{ "/entHandle/query_ent_info_by_name" }, method =
	{ RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpResp queryEntInfoByName(@RequestParam("entCustName") String entCustName,
			@RequestParam("vaildCode") String vaildCode, HttpServletRequest request, HttpServletResponse response)
	{
		try
		{
			// 去掉后台验证
			// String code = (String)
			// request.getSession().getAttribute(VaildCodeType.APP_QUERY.getCode());
			// if (vaildCode.equalsIgnoreCase(code))
			// {
			// return
			// entHandleService.selectEntInfoByName(URLDecoder.decode(entCustName,
			// "UTF-8"));
			// }
			return entHandleService.selectEntInfoByName(URLDecoder.decode(entCustName, "UTF-8"));
		} catch (UnsupportedEncodingException ex)
		{
			SystemLog.error(this.getClass().getName(), "function:submitIntentionCustApply", "参数解码失败" + entCustName, ex);
		}
		return new HttpResp(RespCode.FAIL.getCode());
	}

	/**
	 * 根据授权码查询企业信息
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2015年12月9日 下午4:22:01
	 * @param authCode
	 * @param retData
	 * @param request
	 * @param response
	 * @return
	 * @return : HttpRespEnvelope
	 * @version V3.0.0
	 */
	@ResponseBody
	@RequestMapping(value =
	{ "/entHandle/query_ent_info_by_code" }, method =
	{ RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpResp queryEntInfoByCode(@RequestParam("authCode") String authCode,
			@RequestParam("retData") boolean retData, HttpServletRequest request, HttpServletResponse response)
	{
		return entHandleService.selectEntInfoByCode(authCode, retData);
	}

	/**
	 * 验证授权码
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2015年12月29日 上午11:55:24
	 * @param authCode
	 * @param vaildCode
	 * @param request
	 * @param response
	 * @return
	 * @return : HttpResp
	 * @version V3.0.0
	 */
	@ResponseBody
	@RequestMapping(value =
	{ "/entHandle/vaild_auth_code" }, method =
	{ RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpResp vaildAuthCode(@RequestParam("authCode") String authCode,
			@RequestParam("vaildCode") String vaildCode, HttpServletRequest request, HttpServletResponse response)
	{
		// 去掉后台验证码
		// String code = (String)
		// request.getSession().getAttribute(VaildCodeType.AAP_HANDLE.getCode());
		// if (vaildCode.equalsIgnoreCase(code))
		// {
		// return entHandleService.selectEntInfoByCode(authCode, false);
		// }
		// return new HttpResp(RespCode.VAILD_CODE_ERROR.getCode());
		return entHandleService.selectEntInfoByCode(authCode, false);
	}

	/**
	 * 查询公告期的企业
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2015年12月9日 下午4:22:53
	 * @param request
	 * @param response
	 * @return
	 * @return : HttpRespEnvelope
	 * @version V3.0.0
	 */
	@ResponseBody
	@RequestMapping(value =
	{ "/entHandle/query_notice_ent" }, method =
	{ RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpResp queryNoticeEnt(HttpServletRequest request, HttpServletResponse response)
	{
		return entHandleService.selectNoticeEnt();
	}

	/**
	 * 获取网银支付地址
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2015年12月9日 下午4:28:44
	 * @param orderNo
	 * @param request
	 * @param response
	 * @return
	 * @return : HttpRespEnvelope
	 * @version V3.0.0
	 */
	@ResponseBody
	@RequestMapping(value =
	{ "/entHandle/get_ebank_pay_url" }, method =
	{ RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpResp getEBankPayUrl(@RequestParam("orderNo") String orderNo, HttpServletRequest request,
			HttpServletResponse response)
	{
		return entHandleService.getEBankPayUrl(orderNo);
	}

	/**
	 * 提交互生卡发放申请
	 *
	 * @Description:
	 * @author: likui
	 * @created: 2016年06月23日 下午6:00:05
	 * @param cardProvice
	 * @param request
	 * @param response
	 * @return
	 * @return : HttpRespEnvelope
	 * @version V3.0.0
	 */
	@ResponseBody
	@RequestMapping(value =
			{ "/entHandle/add_card_provide_apply" }, method =
			{ RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpResp addCardProvideApply(String cardProvice, HttpServletRequest request, HttpServletResponse response)
	{
		return entHandleService.submitCardProvideApply(cardProvice);
	}
}
