/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.person.controllers.accountManagement;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.gy.hsxt.access.web.common.bean.HttpRespEnvelope;
import com.gy.hsxt.access.web.common.controller.BaseController;
import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.access.web.common.utils.RequestUtil;
import com.gy.hsxt.access.web.person.common.TransType;
import com.gy.hsxt.access.web.person.services.accountManagement.IHsbAccountDetailService;
import com.gy.hsxt.ao.bean.BuyHsb;
import com.gy.hsxt.ao.bean.HsbToCash;
import com.gy.hsxt.ao.bean.PvToHsb;
import com.gy.hsxt.bs.bean.order.OrderCustom;
import com.gy.hsxt.bs.bean.order.PointDividend;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.ws.bean.GrantDetail;

/***************************************************************************
 * <PRE>
 * Description 		: 互生币账户明细查看处理类
 * 
 * Project Name   	: hsxt-access-web-person 
 * 
 * Package Name     : com.gy.hsxt.access.web.person.controllers.accountManagement  
 * 
 * File Name        : HsbAccountDetailController 
 * 
 * Author           : chenli
 * 
 * Create Date      : 2016-01-27 上午10:22:16
 * 
 * Update User      : 
 * 
 * Update Date      : 
 * 
 * UpdateRemark 	: 说明本次修改内容
 * 
 * Version          : v1.0
 * 
 * </PRE>
 ***************************************************************************/
@Controller
@RequestMapping("hsbAccountDetail")
public class HsbAccountDetailController extends BaseController {
	@Resource
	private IHsbAccountDetailService service;

	/**
	 * 查询积分投资分红详情
	 * 
	 * @param transNo
	 *            交易流水号
	 * @param request
	 *            当前请求数据
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/getPointDividendInfo" }, method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope getPointDividendInfo(String transNo,
			HttpServletRequest request) {
		HttpRespEnvelope hre = null;
		try {
			// Token验证
			super.checkSecureToken(request);

			// 非空数据验证
			RequestUtil.verifyParamsIsNotEmpty(new Object[] { transNo,
					RespCode.AS_ENT_CUSTID_INVALID.getCode(),
					RespCode.AS_ENT_CUSTID_INVALID.getDesc() } // 交易流水号
					);

			// 查询积分投资分红详情
			PointDividend pointDividend = service.getPointDividendInfo(transNo);

			hre = new HttpRespEnvelope(pointDividend);

		} catch (HsException e) {
			e.printStackTrace();
			hre = new HttpRespEnvelope(e);
		}

		return hre;
	}
	
	/**
	 * 查询线下兑换互生币详情
	 * 
	 * @param transNo
	 *            交易流水号
	 * @param request
	 *            当前请求数据
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/getOrder" }, method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope getOrder(String transNo,
			HttpServletRequest request) {
		HttpRespEnvelope hre = null;
		try {
			// Token验证
			super.checkSecureToken(request);

			// 非空数据验证
			RequestUtil.verifyParamsIsNotEmpty(new Object[] { transNo,
					RespCode.AS_ENT_CUSTID_INVALID.getCode(),
					RespCode.AS_ENT_CUSTID_INVALID.getDesc() } // 交易流水号
					);

			// 查询线下兑换互生币详情
			OrderCustom orderCustom = service.getOrder(transNo);

			hre = new HttpRespEnvelope(orderCustom);

		} catch (HsException e) {
			e.printStackTrace();
			hre = new HttpRespEnvelope(e);
		}

		return hre;
	}
	
	/**
	 *  查询互生币转货币详情
	 * 
	 * @param transNo
	 *            交易流水号
	 * @param request
	 *            当前请求数据
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/getHsbToCashInfo" }, method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope getHsbToCashInfo(String transNo,
			HttpServletRequest request) {
		HttpRespEnvelope hre = null;
		try {
			// Token验证
			super.checkSecureToken(request);

			// 非空数据验证
			RequestUtil.verifyParamsIsNotEmpty(new Object[] { transNo,
					RespCode.AS_ENT_CUSTID_INVALID.getCode(),
					RespCode.AS_ENT_CUSTID_INVALID.getDesc() } // 交易流水号
					);

			// 查询互生币转货币详情
			HsbToCash hsbToCash = service.getHsbToCashInfo(transNo);

			hre = new HttpRespEnvelope(hsbToCash);

		} catch (HsException e) {
			e.printStackTrace();
			hre = new HttpRespEnvelope(e);
		}

		return hre;
	}
	
	/**
	 * 查询兑换互生币详情
	 * 
	 * @param transNo
	 *            交易流水号
	 * @param request
	 *            当前请求数据
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/getBuyHsbInfo" }, method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope getBuyHsbInfo(String transNo,
			HttpServletRequest request) {
		HttpRespEnvelope hre = null;
		try {
			// Token验证
			super.checkSecureToken(request);

			// 非空数据验证
			RequestUtil.verifyParamsIsNotEmpty(new Object[] { transNo,
					RespCode.AS_ENT_CUSTID_INVALID.getCode(),
					RespCode.AS_ENT_CUSTID_INVALID.getDesc() } // 交易流水号
					);

			// 查询兑换互生币详情
			BuyHsb buyHsb = service.getBuyHsbInfo(transNo);

			hre = new HttpRespEnvelope(buyHsb);

		} catch (HsException e) {
			e.printStackTrace();
			hre = new HttpRespEnvelope(e);
		}

		return hre;
	}
	/**
	 * 查询积分转互生币详情
	 * 
	 * @param transNo
	 *            交易流水号
	 * @param request
	 *            当前请求数据
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/getPvToHsbInfo" }, method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope getPvToHsbInfo(String transNo,
			HttpServletRequest request) {
		HttpRespEnvelope hre = null;
		try {
			// Token验证
			super.checkSecureToken(request);

			// 非空数据验证
			RequestUtil.verifyParamsIsNotEmpty(new Object[] { transNo,
					RespCode.AS_ENT_CUSTID_INVALID.getCode(),
					RespCode.AS_ENT_CUSTID_INVALID.getDesc() } // 交易流水号
					);

			// 查询积分转互生币详情
			PvToHsb pvToHsb = service.getPvToHsbInfo(transNo);

			hre = new HttpRespEnvelope(pvToHsb);

		} catch (HsException e) {
			e.printStackTrace();
			hre = new HttpRespEnvelope(e);
		}

		return hre;
	}
	
	/**
	 * 查询积分福利详情
	 * 
	 * @param transNo
	 *            交易流水号
	 * @param request
	 *            当前请求数据
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/welfareApplyDetail" }, method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope welfareApplyDetail(String transNo,
			HttpServletRequest request) {
		HttpRespEnvelope hre = null;
		try {
			// Token验证
			super.checkSecureToken(request);

			// 非空数据验证
			RequestUtil.verifyParamsIsNotEmpty(new Object[] { transNo,
					RespCode.AS_ENT_CUSTID_INVALID.getCode(),
					RespCode.AS_ENT_CUSTID_INVALID.getDesc() } // 交易流水号
					);

			// 查询积分福利详情
			GrantDetail grantDetail = service.queryWelfareApplyDetail(transNo);

			hre = new HttpRespEnvelope(grantDetail);

		} catch (HsException e) {
			e.printStackTrace();
			hre = new HttpRespEnvelope(e);
		}

		return hre;
	}

	/**
	 * 查找PS的交易类型
	 * 
	 * @param transNo
	 *            交易流水号
	 * @param request
	 *            当前请求数据
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/getHsbTransType" }, method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope getHsbTransType(String transType,
			HttpServletRequest request) {
		HttpRespEnvelope hre = null;
		try {
			// Token验证
			super.checkSecureToken(request);

			// 非空数据验证
			RequestUtil.verifyParamsIsNotEmpty(new Object[] { transType,
					RespCode.AS_ENT_CUSTID_INVALID.getCode(),
					RespCode.AS_ENT_CUSTID_INVALID.getDesc() } // 交易流水号
					);

			// 查询互生币交易类型(互生币账户)
			String transTypeName= TransType.getHsbTransType(transType);

			hre = new HttpRespEnvelope(transTypeName);

		} catch (HsException e) {
			e.printStackTrace();
			hre = new HttpRespEnvelope(e);
		}

		return hre;
	}
	/**
	 * @return
	 * @see com.gy.hsxt.access.web.common.controller.BaseController#getEntityService()
	 */
	@Override
	protected IBaseService getEntityService() {
		return service;
	}
}
