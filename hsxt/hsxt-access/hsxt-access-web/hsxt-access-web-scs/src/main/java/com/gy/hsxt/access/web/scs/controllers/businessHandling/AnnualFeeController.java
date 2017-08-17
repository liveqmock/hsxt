/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.scs.controllers.businessHandling;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gy.hsxt.ac.bean.AccountBalance;
import com.gy.hsxt.access.web.common.bean.HttpRespEnvelope;
import com.gy.hsxt.access.web.common.controller.BaseController;
import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.access.web.common.utils.HttpClientUtils;
import com.gy.hsxt.access.web.common.utils.RequestUtil;
import com.gy.hsxt.access.web.scs.services.accountManagement.IBalanceService;
import com.gy.hsxt.access.web.scs.services.businessHandling.IAnnualFeeManageService;
import com.gy.hsxt.access.web.scs.services.common.IPubParamService;
import com.gy.hsxt.access.web.scs.services.common.SCSConfigService;
import com.gy.hsxt.bs.bean.annualfee.AnnualFeeOrder;
import com.gy.hsxt.common.constant.AccountType;
import com.gy.hsxt.common.constant.PayChannel;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.lcs.bean.LocalInfo;
import com.gy.hsxt.lcs.client.LcsClient;
import com.gy.hsxt.uc.as.api.enumerate.UserTypeEnum;

/**
 * 缴纳系统使用年费
 * 
 * @Package: com.gy.hsxt.access.web.company.controllers.systemBusiness
 * @ClassName: AnnualFeeController
 * @Description: TODO
 * 
 * @author: zhangcy
 * @date: 2015-10-14 下午7:41:48
 * @version V3.0.0
 */
@SuppressWarnings("rawtypes")
@Controller
@RequestMapping("sysannualfee")
public class AnnualFeeController extends BaseController {

	@Resource
	private IAnnualFeeManageService iannualFeeManageService;

	@Resource
	private IPubParamService pubParamService;

	@Resource
	private IBalanceService ibalanceService;

	@Autowired
	private SCSConfigService scsConfigService; // 全局配置文件

	@Autowired
	private LcsClient lcsClient; // 全局配置服务接口

	/**
	 * 查询系统年费缴纳信息
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/query_entannualfee_info" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope queryAnnualfeeInfo(HttpServletRequest request)
	{
		// Token验证
		HttpRespEnvelope httpRespEnvelope = super.checkSecureToken(request);
		if (httpRespEnvelope == null)
		{
			String custId = request.getParameter("entCustId");
			try
			{
				Map<String, Object> result = iannualFeeManageService.findAnnualFeeShould(custId);
				LocalInfo localinfo = pubParamService.findSystemInfo();

				// 查询互生币账户
				AccountBalance balance = ibalanceService.findAccNormal(custId,
						AccountType.ACC_TYPE_POINT20110.getCode());
				// 查询货币账户
				AccountBalance hbBalance = ibalanceService.findAccNormal(custId,
						AccountType.ACC_TYPE_POINT20110.getCode());

				/** 企业账户互生币可用数量 **/
				result.put("availablePointNum", null != balance ? balance.getAccBalance() : 0);

				// 企业货币账户可用数量
				result.put("availableHbNum", null != hbBalance ? hbBalance.getAccBalance() : 0);

				/** 货币转换比率 **/
				result.put("transRate", localinfo.getExchangeRate());

				/** 当地结算货币 **/
				result.put("localCurrency", localinfo.getCurrencyNameCn());

				httpRespEnvelope = new HttpRespEnvelope(result);
			} catch (Exception e)
			{
				httpRespEnvelope = new HttpRespEnvelope(e);
			}
		}
		return httpRespEnvelope;
	}

	/**
	 * 缴纳年费
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/pay_annualfee" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope payAnnualfee(HttpServletRequest request, @ModelAttribute AnnualFeeOrder annualFeeOrder)
	{
		// Token验证
		HttpRespEnvelope httpRespEnvelope = super.checkSecureToken(request);
		if (httpRespEnvelope == null)
		{
			try
			{
				// 交易密码
				String tradePwd = request.getParameter("tradePwd");
				// 随机token
				String randomToken = request.getParameter("randomToken");

				// 地区平台信息
				LocalInfo info = lcsClient.getLocalInfo();

				// 验证非空
				RequestUtil.verifyParamsIsNotEmpty(
						new Object[] { annualFeeOrder.getOrder().getOrderOperator(),
								RespCode.MW_OPRATOR_OPTCUSTID.getCode(), RespCode.MW_OPRATOR_OPTCUSTID.getDesc() },
						new Object[] { annualFeeOrder.getOrder().getOrderNo(),
								RespCode.SW_ENT_ANNUALFEE_ORDERNO.getCode(),
								RespCode.SW_ENT_ANNUALFEE_ORDERNO.getDesc() },
						new Object[] { annualFeeOrder.getOrder().getPayChannel(),
								RespCode.EW_TOOL_PAY_CHANNEL_INVALID.getCode(),
								RespCode.EW_TOOL_PAY_CHANNEL_INVALID.getDesc() }, new Object[] {
								annualFeeOrder.getOrder().getCustId(), RespCode.AS_CUSTID_INVALID.getCode(),
								RespCode.AS_CUSTID_INVALID.getDesc() }

				);

				// 互生币支付 验证交易密码 随机token不能为空
				if (annualFeeOrder.getOrder().getPayChannel().intValue() == PayChannel.HS_COIN_PAY.getCode().intValue())
				{
					RequestUtil.verifyParamsIsNotEmpty(new Object[] { tradePwd, RespCode.AS_TRADEPWD_INVALID.getCode(),
							RespCode.AS_TRADEPWD_INVALID.getDesc() }, new Object[] { randomToken,
							RespCode.AS_SECURE_TOKEN_NULL.getCode(), RespCode.AS_SECURE_TOKEN_NULL.getDesc() });
				}
				// 快捷支付支付 验证交易密码 随机token 快捷支付预约号 短信验证码不能为空
				if (annualFeeOrder.getOrder().getPayChannel().intValue() == PayChannel.QUICK_PAY.getCode().intValue())
				{
					RequestUtil.verifyParamsIsNotEmpty(new Object[] { annualFeeOrder.getSmsCode(),
							RespCode.EW_SMSCODE_INVALID.getCode(), RespCode.EW_SMSCODE_INVALID.getDesc() },
							new Object[] { annualFeeOrder.getBindingNo(), RespCode.EW_BINDINGNO_INVALID.getCode(),
									RespCode.EW_BINDINGNO_INVALID.getDesc() });
				}

				if (annualFeeOrder.getOrder().getPayChannel() == PayChannel.HS_COIN_PAY.getCode().intValue())
				{
					// 呼叫中心的特殊处理方法
					tradePwd = RequestUtil.encodeBase64String(request, tradePwd);
					// 验证交易密码
					iannualFeeManageService.checkTradePwd(annualFeeOrder.getOrder().getCustId(), tradePwd,
							UserTypeEnum.ENT.getType(), randomToken);
				}
				// 支付
				annualFeeOrder.setCallbackUrl(info.getWebPayJumpUrl());
				String url = iannualFeeManageService.addAnnualFee(annualFeeOrder);
				if (annualFeeOrder.getOrder().getPayChannel().intValue() == PayChannel.QUICK_PAY.getCode().intValue())
				{
					url = HttpClientUtils.get(url);
				}
				
				httpRespEnvelope = new HttpRespEnvelope(url);
			} catch (Exception e)
			{
				httpRespEnvelope = new HttpRespEnvelope(e);
			}
		}
		return httpRespEnvelope;
	}

	/**
	 * 提交年费订单
	 * 
	 * @param request
	 * @param annualFee
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/create_annualfee_order" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope createAnnualfeeOrder(HttpServletRequest request,
			@ModelAttribute AnnualFeeOrder annualFeeOrder)
	{
		// Token验证
		HttpRespEnvelope httpRespEnvelope = super.checkSecureToken(request);
		if (httpRespEnvelope == null)
		{
			try
			{

				AnnualFeeOrder order = iannualFeeManageService.submitAnnualFeeOrder(annualFeeOrder);
				httpRespEnvelope = new HttpRespEnvelope(order);
			} catch (Exception e)
			{
				httpRespEnvelope = new HttpRespEnvelope(e);
			}
		}
		return httpRespEnvelope;
	}

	/**
	 * 查询订单详情
	 * 
	 * @param request
	 * @param annualFeeOrder
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/query_annualfee_order" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope queryAnnualfeeOrder(HttpServletRequest request,
			String orderNo)
	{
		// Token验证
		HttpRespEnvelope httpRespEnvelope = super.checkSecureToken(request);
		if (httpRespEnvelope == null)
		{
			try
			{
				AnnualFeeOrder order = iannualFeeManageService.queryAnnualFeeOrder(orderNo);
				httpRespEnvelope = new HttpRespEnvelope(order);
			} catch (Exception e)
			{
				httpRespEnvelope = new HttpRespEnvelope(e);
			}
		}
		return httpRespEnvelope;
	}

	@ResponseBody
	@RequestMapping(value = { "/init_localinfo" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope initLocalInfo(HttpServletRequest request, HttpServletResponse response)
	{
		// Token验证
		HttpRespEnvelope httpRespEnvelope = super.checkSecureToken(request);
		if (httpRespEnvelope == null)
		{
			try
			{
				LocalInfo localInfo = pubParamService.findSystemInfo();
				httpRespEnvelope = new HttpRespEnvelope(localInfo);
			} catch (Exception e)
			{
				httpRespEnvelope = new HttpRespEnvelope(e);
			}
		}
		return httpRespEnvelope;
	}

	@Override
	protected IBaseService getEntityService()
	{
		return iannualFeeManageService;
	}

}
