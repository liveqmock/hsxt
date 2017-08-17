/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.company.controllers.systemBusiness;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gy.hsxt.access.web.common.bean.HttpRespEnvelope;
import com.gy.hsxt.access.web.common.constant.ASRespCode;
import com.gy.hsxt.access.web.common.controller.BaseController;
import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.access.web.common.utils.CommonUtils;
import com.gy.hsxt.access.web.common.utils.RequestUtil;
import com.gy.hsxt.access.web.common.utils.ValidateUtil;
import com.gy.hsxt.access.web.company.services.systemBusiness.IQuickPayManageService;
import com.gy.hsxt.common.bean.OpenQuickPayBean;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.HsAssert;

/**
 * 快捷支付控制器
 * 
 * @Package: com.gy.hsxt.access.web.company.controllers.systemBusiness
 * @ClassName: QuickPayManageController
 * @Description: TODO
 * @author: likui
 * @date: 2016年1月12日 下午2:07:19
 * @company: gyist
 * @version V3.0.0
 */
@Controller
@RequestMapping("quickPay")
@SuppressWarnings("rawtypes")
public class QuickPayManageController extends BaseController<QuickPayManageController> {

	/**
	 * 快捷支付Service
	 */
	@Autowired
	private IQuickPayManageService quickPayManageService;

	/**
	 * 获取绑定快捷支付的银行列表
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年1月12日 下午6:17:59
	 * @param request
	 * @return
	 * @return : HttpRespEnvelope
	 * @version V3.0.0
	 */
	@ResponseBody
	@RequestMapping(value = { "/get_band_quick_bank" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope getBandQuickBank(HttpServletRequest request)
	{
		// Token验证
		HttpRespEnvelope httpRespEnvelope = super.checkSecureToken(request);
		if (httpRespEnvelope == null)
		{
			String entCustId = request.getParameter("entCustId");
			try
			{
				RequestUtil.verifyParamsIsNotEmpty(new Object[] { entCustId, ASRespCode.AS_CUSTID_INVALID.getCode(),
						ASRespCode.AS_CUSTID_INVALID.getDesc() });
				httpRespEnvelope = new HttpRespEnvelope(quickPayManageService.queryBandQuickBank(entCustId));
			} catch (HsException e)
			{
				httpRespEnvelope = new HttpRespEnvelope(e);
			}
		}
		return httpRespEnvelope;
	}

	/**
	 * 获取快捷支付支持的银行列表
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年1月12日 下午6:20:41
	 * @param request
	 * @return
	 * @return : HttpRespEnvelope
	 * @version V3.0.0
	 */
	@ResponseBody
	@RequestMapping(value = { "/get_quick_pay_bank" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope getQuickPayBank(HttpServletRequest request)
	{
		// Token验证
		HttpRespEnvelope httpRespEnvelope = super.checkSecureToken(request);
		if (httpRespEnvelope == null)
		{
			String cardType = request.getParameter("cardType");
			try
			{
				httpRespEnvelope = new HttpRespEnvelope(quickPayManageService.queryQuickPayBank(cardType));
			} catch (HsException e)
			{
				httpRespEnvelope = new HttpRespEnvelope(e);
			}
		}
		return httpRespEnvelope;
	}

	/**
	 * 开通快捷支付
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年1月12日 下午6:15:24
	 * @param request
	 * @return
	 * @return : HttpRespEnvelope
	 * @version V3.0.0
	 */
	@ResponseBody
	@RequestMapping(value = { "/open_quick_pay" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope openQuickPay(HttpServletRequest request)
	{
		// Token验证
		HttpRespEnvelope httpRespEnvelope = super.checkSecureToken(request);
		if (httpRespEnvelope == null)
		{
			String orderNo = request.getParameter("orderNo");
			String entCustId = request.getParameter("entCustId");
			String bankCardNo = request.getParameter("bankCardNo");
			String bankCardType = request.getParameter("bankCardType");
			String bankId = request.getParameter("bankId");
			String callType = request.getParameter("callType");
			try
			{
				RequestUtil.verifyParamsIsNotEmpty(
						new Object[] { orderNo, ASRespCode.EW_TOOL_ORDERNO_INVALID.getCode(),
								ASRespCode.EW_TOOL_ORDERNO_INVALID.getDesc() },
						new Object[] { entCustId, ASRespCode.AS_CUSTID_INVALID.getCode(),
								ASRespCode.AS_CUSTID_INVALID.getDesc() },
						new Object[] { bankCardNo, ASRespCode.AS_BANKCARDNO_INVALID.getCode(),
								ASRespCode.AS_BANKCARDNO_INVALID.getDesc() },
						new Object[] { bankCardType, ASRespCode.AS_BANKCARDTYPE_INVALID.getCode(),
								ASRespCode.AS_BANKCARDTYPE_INVALID.getDesc() }, new Object[] { bankId,
								ASRespCode.AS_BANKID_INVALID.getCode(), ASRespCode.AS_BANKID_INVALID.getDesc() });

				HsAssert.isTrue(ValidateUtil.validateBankCardId(bankCardNo), ASRespCode.AS_BANKCARDNO_ERROR);
				String ret = quickPayManageService.openQuickPay(new OpenQuickPayBean(null, orderNo, null, null, null,
						entCustId, bankCardNo, CommonUtils.toInteger(bankCardType), bankId), callType);
				httpRespEnvelope = new HttpRespEnvelope(ret);
			} catch (HsException e)
			{
				httpRespEnvelope = new HttpRespEnvelope(e);
			}
		}
		return httpRespEnvelope;
	}

	/**
	 * 发送快捷支付短信
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年1月12日 下午6:26:42
	 * @param request
	 * @return
	 * @return : HttpRespEnvelope
	 * @version V3.0.0
	 */
	@ResponseBody
	@RequestMapping(value = { "/send_quick_pay_sms" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope sendQuickPaySms(HttpServletRequest request)
	{
		// Token验证
		HttpRespEnvelope httpRespEnvelope = super.checkSecureToken(request);
		if (httpRespEnvelope == null)
		{
			String callType = request.getParameter("callType");
			String orderNo = request.getParameter("orderNo");
			String bindingNo = request.getParameter("bindingNo");
			String privObligate = request.getParameter("privObligate");

			try
			{
				RequestUtil.verifyParamsIsNotEmpty(new Object[] { orderNo,
						ASRespCode.EW_TOOL_ORDERNO_INVALID.getCode(), ASRespCode.EW_TOOL_ORDERNO_INVALID.getDesc() },
						new Object[] { bindingNo, ASRespCode.EW_BINDINGNO_INVALID.getCode(),
								ASRespCode.EW_BINDINGNO_INVALID.getDesc() });

				quickPayManageService.sendQuickPaySmsCode(orderNo, bindingNo, privObligate, callType);
				httpRespEnvelope = new HttpRespEnvelope();
			} catch (HsException e)
			{
				httpRespEnvelope = new HttpRespEnvelope(e);
			}
		}
		return httpRespEnvelope;
	}

	@Override
	protected IBaseService getEntityService()
	{
		return quickPayManageService;
	}
}
