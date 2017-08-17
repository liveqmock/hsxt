/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */

package com.gy.hsxt.access.web.person.controllers.common;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gy.hsxt.access.web.common.bean.HttpRespEnvelope;
import com.gy.hsxt.access.web.common.controller.BaseController;
import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.access.web.common.utils.HttpClientUtils;
import com.gy.hsxt.access.web.person.services.common.IPubParamService;
import com.gy.hsxt.ao.api.IAOExchangeHsbService;
import com.gy.hsxt.bp.client.api.BusinessParamSearch;
import com.gy.hsxt.bs.api.tool.IBSToolMarkService;
import com.gy.hsxt.bs.common.enumtype.order.OrderType;
import com.gy.hsxt.common.bean.OpenQuickPayBean;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.StringUtils;
import com.gy.hsxt.lcs.bean.LocalInfo;
import com.gy.hsxt.lcs.bean.PayBank;
import com.gy.hsxt.lcs.client.LcsClient;
import com.gy.hsxt.uc.as.api.common.IUCAsBankAcctInfoService;
import com.gy.hsxt.uc.as.api.common.IUCAsPwdService;
import com.gy.hsxt.uc.as.api.enumerate.UserTypeEnum;
import com.gy.hsxt.uc.as.bean.common.AsBankAcctInfo;
import com.gy.hsxt.uc.as.bean.common.AsQkBank;

/**
 * 
 * @Package: com.gy.hsxt.access.web.person.common
 * @ClassName: BankDetailSearchController
 * @Description: TODO
 * 
 * @author: weixz
 * @date: 2016-1-12 下午4:29:18
 * @version V1.0
 */

@Controller
@RequestMapping("bankDetailSearch")
public class BankDetailSearchController extends BaseController {

	@Autowired
	private IUCAsBankAcctInfoService iUCAsBankAcctInfoService;// 银行账户管理

	@Autowired
	private IUCAsPwdService iUCAsPwdService; // 密码管理

	@Autowired
	private BusinessParamSearch businessParamSearch;// 业务参数服务接口

	@Autowired
	private IAOExchangeHsbService iAOExchangeHsbService;// 兑换互生币

	@Autowired
	private IPubParamService pubParamService;// 平台服务公共信息服务类

	@Autowired
	private LcsClient lcsClient; // 全局配置服务接口

	@Autowired
	private IBSToolMarkService iBSToolMarkService;// 工单配置管理

	/**
	 * 查询客户绑定的银行卡
	 * 
	 * @param custId
	 *            客户号
	 * @param custType
	 *            客户类型
	 * @param request
	 *            当前请求数据
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/search_BankInfo_List" }, method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope searchBankInfoList(String custId, String custType, HttpServletRequest request)
	{

		// 变量声明
		HttpRespEnvelope hre = null;
		Map<String, Object> map = null;

		try
		{
			// Token验证
			super.checkSecureToken(request);

			map = new HashMap<String, Object>();

			// 查询绑定的银行卡
			List<AsBankAcctInfo> list = iUCAsBankAcctInfoService.listBanksByCustId(custId, custType);

			hre = new HttpRespEnvelope(list);
			System.out.println("list.size()====" + list.size());
		} catch (HsException e)
		{
			e.printStackTrace();
			hre = new HttpRespEnvelope(e);
		}
		return hre;
	}

	/**
	 * 查询客户绑定的快捷支付卡
	 * 
	 * @param custId
	 *            客户号
	 * @param custType
	 *            客户类型
	 * @param request
	 *            当前请求数据
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/search_Quick_BankInfo_List" }, method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope searchQuickBankInfoList(String custId, String custType, HttpServletRequest request)
	{

		// 变量声明
		HttpRespEnvelope hre = null;
		Map<String, Object> map = null;

		try
		{
			// Token验证
			super.checkSecureToken(request);

			map = new HashMap<String, Object>();

			// 查询绑定的银行卡
			List<AsQkBank> list = iUCAsBankAcctInfoService.listQkBanksByCustId(custId, custType);

			hre = new HttpRespEnvelope(list);
			System.out.println("list.size()====" + list.size());
		} catch (HsException e)
		{
			e.printStackTrace();
			hre = new HttpRespEnvelope(e);
		}

		return hre;
	}

	/**
	 * 校验支付密码后进行快捷支付
	 * 
	 * @param custId
	 *            客户号
	 * @param custType
	 *            客户类型
	 * @param request
	 *            当前请求数据
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/validate_PayPwd" }, method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope validatePayPwd(String custId, String transNo, String bindingNo, String smsCode,
			HttpServletRequest request)
	{

		// 变量声明
		HttpRespEnvelope hre = null;

		try
		{
			// Token验证
			super.checkSecureToken(request);

			// 校验支付 去掉快捷支付的交易密码验证
			// iUCAsPwdService.checkTradePwd(custId, tradePwd, custType,
			// randomToken);

			// 支付密码校验通过后进行支付并返回一个URL
			String url = iAOExchangeHsbService.getQuickPayUrl(transNo, bindingNo, smsCode);
			url = HttpClientUtils.get(url);
			hre = new HttpRespEnvelope(url);
		} catch (HsException e)
		{
			e.printStackTrace();
			hre = new HttpRespEnvelope(e);
			hre.setRetCode(e.getErrorCode());
		}

		return hre;
	}

	/**
	 * 校验支付密码后进行货币支付
	 * 
	 * @param custId
	 *            客户号
	 * @param custType
	 *            客户类型
	 * @param request
	 *            当前请求数据
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/validate_HbPay" }, method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope validateHbPay(String custId, String tradePwd, String custType,String hs_isCard, String randomToken,
			String transNo, HttpServletRequest request)
	{

		// 变量声明
		HttpRespEnvelope hre = null;

		try
		{
			// Token验证
			super.checkSecureToken(request);
			
			//默认是持卡人code
			String type = UserTypeEnum.CARDER.getType();
			
			//非持卡人code
			if(StringUtils.isNotBlank(hs_isCard) && "1".equals(hs_isCard))
			{
				type = UserTypeEnum.NO_CARDER.getType();
			}
			// 校验支付密码
			iUCAsPwdService.checkTradePwd(custId, tradePwd, type, randomToken);

			// 支付密码校验通过后进行支付
			iAOExchangeHsbService.paymentByCurrency(transNo);

			hre = new HttpRespEnvelope();

		} catch (HsException e)
		{
			e.printStackTrace();
			hre = new HttpRespEnvelope(e);
			hre.setRetCode(e.getErrorCode());
		}

		return hre;
	}

	/**
	 * 校验支付密码后进行互生币支付
	 * 
	 * @param custId
	 *            客户号
	 * @param custType
	 *            客户类型
	 * @param request
	 *            当前请求数据
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/remark_Open_Card" }, method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope remarkOpenCard(String custId, String tradePwd, String custType, String randomToken,
			String transNo, HttpServletRequest request)
	{

		// 变量声明
		HttpRespEnvelope hre = null;

		try
		{
			// Token验证
			super.checkSecureToken(request);

			// 校验支付密码
			iUCAsPwdService.checkTradePwd(custId, tradePwd, custType, randomToken);

			// 支付密码校验通过后进行支付
			iBSToolMarkService.remarkOpenCard(transNo, OrderType.REMAKE_MY_CARD.getCode(), custId);

			hre = new HttpRespEnvelope();

		} catch (HsException e)
		{
			e.printStackTrace();
			hre = new HttpRespEnvelope(e);
			hre.setRetCode(e.getErrorCode());
		}

		return hre;
	}

	/**
	 * 开通快捷支付，并进行付款
	 * 
	 * @param custId
	 *            客户号
	 * @param bankCardNo
	 *            银行账户
	 * @param bankCardType
	 *            银行卡类型
	 * @param bankId
	 *            银行简码
	 * @param transNo
	 *            订单交易流水号
	 * @param jumpUrl
	 *            跳转的URL
	 * @param request
	 *            当前请求数据
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/open_QuickPay" }, method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope getOpenQuickPay(String custId, String bankCardNo, int bankCardType, String bankId,
			String transNo, String jumpUrl, String privObligate, HttpServletRequest request)
	{

		// 变量声明
		HttpRespEnvelope hre = null;

		try
		{
			// Token验证
			super.checkSecureToken(request);
			LocalInfo info = lcsClient.getLocalInfo();
			OpenQuickPayBean openQuickPayBean = new OpenQuickPayBean();
			openQuickPayBean.setOrderNo(transNo);
			openQuickPayBean.setBankCardNo(bankCardNo);
			openQuickPayBean.setBankCardType(bankCardType);
			openQuickPayBean.setBankId(bankId);
			openQuickPayBean.setJumpUrl(info.getWebPayJumpUrl());
			openQuickPayBean.setPrivObligate(privObligate);
			openQuickPayBean.setCustId(custId);

			// 开通快捷支付，并进行付款
			String url = iAOExchangeHsbService.getOpenQuickPayUrl(openQuickPayBean);

			hre = new HttpRespEnvelope(url);

		} catch (HsException e)
		{
			e.printStackTrace();
			hre = new HttpRespEnvelope(e);
			hre.setRetCode(e.getErrorCode());
		}

		return hre;
	}

	/**
	 * 网银支付
	 * 
	 * @param custId
	 *            客户号
	 * @param transNo
	 *            订单交易流水号
	 * @param jumpUrl
	 *            跳转的URL
	 * @param privObligate
	 *            私有数据
	 * @param request
	 *            当前请求数据
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/net_Pay" }, method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope getNetPayUrl(String custId, String privObligate, String transNo, String jumpUrl,
			HttpServletRequest request)
	{

		// 变量声明
		HttpRespEnvelope hre = null;

		try
		{
			// Token验证
			super.checkSecureToken(request);

			LocalInfo info = lcsClient.getLocalInfo();
			// 网银支付
			String url = iAOExchangeHsbService.getNetPayUrl(transNo, info.getWebPayJumpUrl(), privObligate);

			hre = new HttpRespEnvelope(url);

		} catch (HsException e)
		{
			e.printStackTrace();
			hre = new HttpRespEnvelope(e);
			hre.setRetCode(e.getErrorCode());
		}

		return hre;
	}

	/**
	 * 获取支持快捷支付的银行列表
	 * 
	 * @param custId
	 *            客户号
	 * @param cardType
	 *            卡类型
	 * @param request
	 *            当前请求数据
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/query_PayBankAll" }, method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope getPayBankAll(String custId, String cardType, HttpServletRequest request)
	{

		// 变量声明
		HttpRespEnvelope hre = null;
		Map<String, List<PayBank>> map = null;
		List<PayBank> debitCardList = null;
		List<PayBank> creditCardList = null;
		try
		{
			// Token验证
			// super.checkSecureToken(request);

			// 获取快捷支付验证码
			List<PayBank> list = lcsClient.queryPayBankAll();

			if (!list.isEmpty())
			{

				debitCardList = new ArrayList<PayBank>();
				creditCardList = new ArrayList<PayBank>();
				map = new HashMap<String, List<PayBank>>();

				for (PayBank bank : list)
				{
					// 支持储蓄卡的银行列表
					if (bank.isSupportDebit())
					{
						debitCardList.add(bank);
					}
					// 支持的信用卡银行列表
					if (bank.isSupportCredit())
					{
						creditCardList.add(bank);
					}
				}
				map.put("CreditCard", creditCardList);
				map.put("DebitCard", debitCardList);
			}
			hre = new HttpRespEnvelope(map);
		} catch (HsException e)
		{
			e.printStackTrace();
			hre = new HttpRespEnvelope(e);
			hre.setRetCode(e.getErrorCode());
		}

		return hre;
	}

	/**
	 * 获取快捷支付验证码
	 * 
	 * @param custId
	 *            客户号
	 * @param transNo
	 *            交易流水号
	 * @param bindingNo
	 *            签约号
	 * @param privObligate
	 *            私用数据
	 * @param request
	 *            当前请求数据
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/quickPay_VerifyCode" }, method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope getQuickPayVerifyCode(String custId, String transNo, String bindingNo, String privObligate,
			HttpServletRequest request)
	{

		// 变量声明
		HttpRespEnvelope hre = null;

		try
		{
			// Token验证
			super.checkSecureToken(request);

			// 获取快捷支付验证码
			iAOExchangeHsbService.getQuickPaySmsCode(transNo, bindingNo, privObligate);

			hre = new HttpRespEnvelope();
		} catch (HsException e)
		{

			e.printStackTrace();
			hre = new HttpRespEnvelope(e);
			hre.setRetCode(22006);
		}

		return hre;
	}

	/**
	 * 支付结果跳转
	 * 
	 * @param transStatus
	 *            支付状态
	 * @param failReason
	 *            失败原因
	 * @param request
	 *            当前请求数据
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = { "/netJumpUrl" }, method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public void jumpUrl(String transStatus, String failReason, HttpServletRequest request, HttpServletResponse response)
			throws IOException
	{
		PrintWriter writer = response.getWriter();

		String msg = null;
		if ("100".equals(transStatus))
		{

			msg = "pay success";
		} else
		{
			// 当做不成功处理
			msg = "fail" + "\n" + failReason;
		}
		writer.write("<html><head>");
		writer.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">");
		writer.write("<title>支付结果页面</title>");
		writer.write("<script type=\"text/javascript\"> ");
		writer.write("setTimeout(function(){ window.opener=null;window.open(\"\",\"_self\");");
		writer.write("window.close();}, 3000);");
		writer.write("</script></head><body>");
		writer.write("<script type=\"text/javascript\">alert(\"" + msg + "\");</script>");
		writer.write("</body></html>");

		writer.close();
	}

	/**
	 * @return
	 * @see com.gy.hsxt.access.web.common.controller.BaseController#getEntityService()
	 */
	@Override
	protected IBaseService getEntityService()
	{
		return null;
	}
}
