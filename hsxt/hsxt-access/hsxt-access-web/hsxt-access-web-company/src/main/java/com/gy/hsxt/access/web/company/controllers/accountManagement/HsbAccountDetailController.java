/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.company.controllers.accountManagement;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.gy.hsi.common.utils.StringUtils;
import com.gy.hsxt.access.web.common.bean.HttpRespEnvelope;
import com.gy.hsxt.access.web.common.controller.BaseController;
import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.access.web.common.utils.RequestUtil;
import com.gy.hsxt.access.web.company.services.accountManagement.IHsbAccountDetailService;
import com.gy.hsxt.ao.bean.BuyHsb;
import com.gy.hsxt.ao.bean.HsbToCash;
import com.gy.hsxt.ao.bean.PvToHsb;
import com.gy.hsxt.bs.bean.annualfee.AnnualFeeOrder;
import com.gy.hsxt.bs.bean.order.OrderCustom;
import com.gy.hsxt.bs.bean.order.PointDividend;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.rp.bean.ReportsPointDeduction;
import com.gy.hsxt.uc.as.bean.device.AsDevice;
import com.gy.hsxt.uc.as.bean.operator.AsOperator;

/***************************************************************************
 * <PRE>
 * Description 		: 互生币账户明细查看处理类
 * 
 * Project Name   	: hsxt-access-web-company 
 * 
 * Package Name     : com.gy.hsxt.access.web.company.controllers.accountManagement  
 * 
 * File Name        : HsbAccountDetailController 
 * 
 * Author           : chenli
 * 
 * Create Date      : 2016-01-26 上午10:22:16
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
	 * 获取年费订单详情
	 * 
	 * @param transNo
	 *            交易流水号
	 * @param request
	 *            当前请求数据
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/queryAnnualFeeOrder" }, method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope queryAnnualFeeOrder(String transNo,
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

			// 获取年费订单详情
			AnnualFeeOrder annualFeeOrder = service
					.queryAnnualFeeOrder(transNo);

			hre = new HttpRespEnvelope(annualFeeOrder);

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
	public HttpRespEnvelope getOrder(String transNo, HttpServletRequest request) {
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
	 * 查询互生币转货币详情
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
	 * 消费积分扣除统计查询
	 * 
	 * @return 消费积分扣除统计查询信息
	 * @throws HsException
	 */
	@ResponseBody
	@RequestMapping(value = { "/reportsPointDeduction" }, method = {
			RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope searchReportsPointDeduction(
			HttpServletRequest request, HttpServletResponse response) {
		HttpRespEnvelope hre = null;

		try {
			// Token验证
			super.verifySecureToken(request);
			// 分页查询
			request.setAttribute("serviceName", service);
			request.setAttribute("methodName", "searchReportsPointDeduction");
			hre = super.doList(request, response);

		} catch (HsException e) {
			hre = new HttpRespEnvelope(e);
		}
		return hre;
	}

	/**
	 * 消费积分扣除统计汇总查询
	 * 
	 * @return 消费积分扣除统计汇总查询信息
	 * @throws HsException
	 */
	@ResponseBody
	@RequestMapping(value = { "/reportsPointDeductionSum" }, method = {
			RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope reportsPointDeductionSum(
			HttpServletRequest request, HttpServletResponse response) {
		HttpRespEnvelope hre = null;

		try {
			// Token验证
			super.verifySecureToken(request);
			ReportsPointDeduction pointDedution = createReportsPointDeduction(request);
			ReportsPointDeduction reportsPointDeduction = service.searchReportsPointDeductionSum(pointDedution);
			hre = new HttpRespEnvelope(reportsPointDeduction);

		} catch (HsException e) {
			hre = new HttpRespEnvelope(e);
		}
		return hre;
	}

	/**
	 * 消费积分扣除终端统计查询
	 * 
	 * @return 消费积分扣除终端统计查询信息
	 * @throws HsException
	 */
	@ResponseBody
	@RequestMapping(value = { "/reportsPointDeductionByChannel" }, method = {
			RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope searchReportsPointDeductionByChannel(
			HttpServletRequest request, HttpServletResponse response) {
		HttpRespEnvelope hre = null;

		try {
			// Token验证
			super.verifySecureToken(request);
			// 分页查询
			request.setAttribute("serviceName", service);
			request.setAttribute("methodName",
					"searchReportsPointDeductionByChannel");
			hre = super.doList(request, response);

		} catch (HsException e) {
			hre = new HttpRespEnvelope(e);
		}
		return hre;
	}

	/**
	 * 消费积分扣除统计汇总查询
	 * 
	 * @return 消费积分扣除统计查询信息
	 * @throws HsException
	 */
	@ResponseBody
	@RequestMapping(value = { "/reportsPointDeductionByChannelSum" }, method = {
			RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope reportsPointDeductionByChannelSum(
			HttpServletRequest request, HttpServletResponse response) {
		HttpRespEnvelope hre = null;

		try {
			// Token验证
			super.verifySecureToken(request);
			ReportsPointDeduction pointDedution = createReportsPointDeduction(request);
			ReportsPointDeduction reportsPointDeduction = service.searchReportsPointDeductionByChannelSum(pointDedution);
			hre = new HttpRespEnvelope(reportsPointDeduction);
		} catch (HsException e) {
			hre = new HttpRespEnvelope(e);
		}
		return hre;
	}

	/**
	 * 消费积分扣除操作员统计查询
	 * 
	 * @return 消费积分扣除操作员统计查询信息
	 * @throws HsException
	 */
	@ResponseBody
	@RequestMapping(value = { "/reportsPointDeductionByOper" }, method = {
			RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope reportsPointDeductionByOper(
			HttpServletRequest request, HttpServletResponse response) {
		HttpRespEnvelope hre = null;

		try {
			// Token验证
			super.verifySecureToken(request);
			// 分页查询
			request.setAttribute("serviceName", service);
			request.setAttribute("methodName",
					"searchReportsPointDeductionByOper");
			hre = super.doList(request, response);

		} catch (HsException e) {
			hre = new HttpRespEnvelope(e);
		}
		return hre;
	}

	/**
	 * 消费积分扣除操作员统计汇总查询
	 * 
	 * @return 消费积分扣除统计查询信息
	 * @throws HsException
	 */
	@ResponseBody
	@RequestMapping(value = { "/reportsPointDeductionByOperSum" }, method = {
			RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope reportsPointDeductionByOperSum(
			HttpServletRequest request, HttpServletResponse response) {
		HttpRespEnvelope hre = null;

		try {
			// Token验证
			super.verifySecureToken(request);
			ReportsPointDeduction pointDedution = createReportsPointDeduction(request);
			ReportsPointDeduction reportsPointDeduction = service
					.searchReportsPointDeductionByOperSum(pointDedution);
			hre = new HttpRespEnvelope(reportsPointDeduction);
		} catch (HsException e) {
			hre = new HttpRespEnvelope(e);
		}
		return hre;
	}
	
	/**
     * 获取终端编号列表查询
     * 
     * @return 获取终端编号列表
     * @throws HsException
     */
    @ResponseBody
    @RequestMapping(value = { "/getPosDeviceNoList" }, method = {
            RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope getPosDeviceNoList(HttpServletRequest request, String pointNo) {
        HttpRespEnvelope hre = null;
        try {
            // Token验证
            super.verifySecureToken(request);
            PageData<AsDevice> pd = service.getPosDeviceNoList(pointNo);
            hre = new HttpRespEnvelope(pd.getResult());
        } catch (HsException e) {
            hre = new HttpRespEnvelope(e);
        }
        return hre;
    }
    /**
     * 获取操作员编号列表查询
     * 
     * @return 获取操作员编号列表
     * @throws HsException
     */
    @ResponseBody
    @RequestMapping(value = { "/getOperaterNoList" }, method = {
            RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope getOperaterNoList(HttpServletRequest request, String entCustId) {
        HttpRespEnvelope hre = null;
        try {
            // Token验证
            super.verifySecureToken(request);
            List<AsOperator> list = service.getOperaterNoList(entCustId);
            hre = new HttpRespEnvelope(list);
        } catch (HsException e) {
            hre = new HttpRespEnvelope(e);
        }
        return hre;
    }

	/**
	 * 创建账户明细查询类
	 * 
	 * @param filter
	 * @return
	 */
	ReportsPointDeduction createReportsPointDeduction(HttpServletRequest request) {
		ReportsPointDeduction pointDedution = new ReportsPointDeduction();
		// 客户号
		String entCustId = (String) request.getParameter("entCustId");
		if (!StringUtils.isEmpty(entCustId) && !"undefined".equals(entCustId)) {
			pointDedution.setCustId(entCustId);
		}
		// 操作员账号
		String operCustName = (String) request
				.getParameter("search_operCustName");
		if (!StringUtils.isEmpty(operCustName)
				&& !"undefined".equals(operCustName)) {
			pointDedution.setOperNo(operCustName);
		}
		// 设备编号
		String equipmentNo = (String) request
				.getParameter("search_equipmentNo");
		if (!StringUtils.isEmpty(equipmentNo)
				&& !"undefined".equals(equipmentNo)) {
			pointDedution.setEquipmentNo(equipmentNo);
		}
		// 终端类型
		String channelType = (String) request
				.getParameter("search_channelType");
		if (!StringUtils.isEmpty(channelType)
				&& !"undefined".equals(channelType)) {
			pointDedution.setChannelType(Integer.parseInt(channelType));
		}
		// 开始时间
		String beginDate = (String) request.getParameter("search_beginDate");
		if (!StringUtils.isEmpty(beginDate) && !"undefined".equals(beginDate)) {
			pointDedution.setBeginDate(beginDate);
		}
		// 结束时间
		String endDate = (String) request.getParameter("search_endDate");
		if (!StringUtils.isEmpty(endDate) && !"undefined".equals(endDate)) {
			pointDedution.setEndDate(endDate);
		}
		return pointDedution;
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
