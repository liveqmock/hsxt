/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.scs.services.businessHandling.imp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.access.web.common.service.BaseServiceImpl;
import com.gy.hsxt.access.web.scs.services.businessHandling.IAnnualFeeManageService;
import com.gy.hsxt.bs.api.annualfee.IBSAnnualFeeService;
import com.gy.hsxt.bs.api.order.IBSOrderService;
import com.gy.hsxt.bs.bean.annualfee.AnnualFeeInfo;
import com.gy.hsxt.bs.bean.annualfee.AnnualFeeOrder;
import com.gy.hsxt.bs.bean.order.OrderCustom;
import com.gy.hsxt.bs.bean.order.OrderQueryParam;
import com.gy.hsxt.bs.common.enumtype.order.OrderType;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.rp.api.IRPPaymentManagementService;
import com.gy.hsxt.rp.bean.PaymentManagementOrder;
import com.gy.hsxt.uc.as.api.common.IUCAsPwdService;

/**
 * 缴纳系统年费
 * 
 * @Package: com.gy.hsxt.access.web.company.services.systemBusiness.imp
 * @ClassName: AnnualFeeManageServiceImpl
 * @Description: TODO
 * 
 * @author: zhangcy
 * @date: 2015-10-30 上午10:26:07
 * @version V3.0.0
 */
@SuppressWarnings("rawtypes")
@Service
public class AnnualFeeManageServiceImpl extends BaseServiceImpl implements
		IAnnualFeeManageService {

	@Autowired
	private IBSAnnualFeeService ibSAnnualFeeService;
	@Resource
	private IRPPaymentManagementService irpPaymentManagementService;

	@Autowired
	private IUCAsPwdService ucAsPwdService;
	@Autowired
	private IBSOrderService ibSOrderService;

	@Override
	public PageData<OrderCustom> findScrollResult(Map filterMap, Map sortMap,
			Page page) throws HsException {
		OrderQueryParam param = new OrderQueryParam();
		/** 客户号 **/
		param.setHsCustId((String) filterMap.get("entCustId"));
		/** 结束时间 **/
		param.setEndDate((String) filterMap.get("endDate"));
		/** 开始时间 **/
		param.setStartDate((String) filterMap.get("beginDate"));

		/** 订单类型 **/
		param.setOrderType(OrderType.ANNUAL_FEE.getCode());
		// 订单状态
		Integer orderStatus = filterMap.get("orderStatus") == null
				|| "".equals(filterMap.get("orderStatus").toString()) ? null
				: Integer.parseInt(filterMap.get("orderStatus").toString());
		param.setStatus(orderStatus);
		try {
			return ibSOrderService.getOrderList(param, page);
		} catch (Exception ex) {
			SystemLog.error(this.getClass().getName(), "getOrderList",
					"调用BS查询系统订单失败", ex);
		}
		return null;
	}

	@Override
	public Map<String, Object> findAnnualFeeShould(String custId)
			throws HsException, ParseException {

		SimpleDateFormat localTime = new SimpleDateFormat("yyyy-MM-dd");
		AnnualFeeInfo feelInfo = ibSAnnualFeeService.queryAnnualFeeInfo(custId);

		Map<String, Object> result = new HashMap<String, Object>();

		/** 系统欠（要缴）年费 **/
		result.put("payFee", feelInfo == null ? "" : feelInfo.getArrearAmount());

		/** 目前年费有效期 **/
		result.put("availableTime",
				feelInfo == null ? "" : feelInfo.getEndDate());

		/** 是否欠年费 1:是 0：否 **/
		result.put("isOwe", feelInfo == null ? 0 : feelInfo.getIsArrear());

		result.put("payTime",
				feelInfo == null ? "" : feelInfo.getAreaStartDate() + " ~ "
						+ feelInfo.getAreaEndDate());

		/** 系统使用年费 **/
		result.put("price", feelInfo == null ? "0" : feelInfo.getPrice());

		result.put("warnDate",
				feelInfo == null ? "" : feelInfo.getWarningDate());

		/** 是否在缴费期内 缴费时间段从年费有效期前2个月开始 当不在缴费期且为不欠费时 不显示缴费页面 ***/
		if (feelInfo == null) {
			result.put("isInTime", "0");
		} else {

			String payTime = feelInfo.getWarningDate();
			Date warn = localTime.parse(payTime);

			Date nowTime = new Date();
			if (nowTime.getTime() >= warn.getTime()) {
				result.put("isInTime", "1");
			} else {
				result.put("isInTime", "0");
			}
		}

		return result;
	}

	@Override
	public String addAnnualFee(AnnualFeeOrder annualFee) throws HsException {

		return ibSAnnualFeeService.payAnnualFeeOrder(annualFee);
	}

	@Override
	public void checkTradePwd(String custId, String pwd, String entType,
			String randomToken) throws HsException {

		ucAsPwdService.checkTradePwd(custId, pwd, entType, randomToken);
	}

	@Override
	public AnnualFeeOrder submitAnnualFeeOrder(AnnualFeeOrder annualFeeOrder)
			throws HsException {
		return ibSAnnualFeeService.submitAnnualFeeOrder(annualFeeOrder);
	}

	@Override
	public AnnualFeeOrder queryAnnualFeeOrder(String orderNo)
			throws HsException {

		return ibSAnnualFeeService.queryAnnualFeeOrder(orderNo);
	}
}
