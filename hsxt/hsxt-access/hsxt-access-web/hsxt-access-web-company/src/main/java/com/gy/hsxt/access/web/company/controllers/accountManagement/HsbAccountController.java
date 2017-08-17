/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.company.controllers.accountManagement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gy.hsi.redis.client.api.RedisUtil;
import com.gy.hsxt.ac.bean.AccountBalance;
import com.gy.hsxt.access.web.bean.CompanyBase;
import com.gy.hsxt.access.web.bean.accountManage.NetPay;
import com.gy.hsxt.access.web.bean.common.VerificationCodeType;
import com.gy.hsxt.access.web.common.bean.HttpRespEnvelope;
import com.gy.hsxt.access.web.common.constant.ASRespCode;
import com.gy.hsxt.access.web.common.controller.BaseController;
import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.access.web.common.utils.HttpClientUtils;
import com.gy.hsxt.access.web.common.utils.RequestUtil;
import com.gy.hsxt.access.web.company.services.accountManagement.IBalanceService;
import com.gy.hsxt.access.web.company.services.accountManagement.IIntegralAccountService;
import com.gy.hsxt.access.web.company.services.accountManagement.IOrderService;
import com.gy.hsxt.access.web.company.services.accountManagement.ITransInnerService;
import com.gy.hsxt.access.web.company.services.common.CompanyConfigService;
import com.gy.hsxt.access.web.company.services.common.ICommService;
import com.gy.hsxt.access.web.company.services.common.IPubParamService;
import com.gy.hsxt.access.web.company.services.safeSet.IPwdSetService;
import com.gy.hsxt.access.web.company.services.systemBusiness.IMemberEnterpriseService;
import com.gy.hsxt.ao.api.IAOExchangeHsbService;
import com.gy.hsxt.ao.bean.BuyHsb;
import com.gy.hsxt.ao.bean.HsbToCash;
import com.gy.hsxt.bp.api.IBusinessCustParamService;
import com.gy.hsxt.bp.bean.BusinessCustParamItemsRedis;
import com.gy.hsxt.bp.bean.BusinessSysParamItemsRedis;
import com.gy.hsxt.bp.client.api.BusinessParamSearch;
import com.gy.hsxt.bs.bean.order.Order;
import com.gy.hsxt.bs.common.enumtype.order.OrderChannel;
import com.gy.hsxt.common.bean.OpenQuickPayBean;
import com.gy.hsxt.common.constant.AccountType;
import com.gy.hsxt.common.constant.BusinessParam;
import com.gy.hsxt.common.constant.Channel;
import com.gy.hsxt.common.constant.CustType;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.DoubleUtil;
import com.gy.hsxt.common.utils.StringUtils;
import com.gy.hsxt.lcs.bean.LocalInfo;
import com.gy.hsxt.lcs.bean.PayBank;
import com.gy.hsxt.lcs.client.LcsClient;
import com.gy.hsxt.uc.as.api.common.IUCAsBankAcctInfoService;
import com.gy.hsxt.uc.as.api.common.IUCAsPwdService;
import com.gy.hsxt.uc.as.api.ent.IUCAsEntService;
import com.gy.hsxt.uc.as.api.enumerate.UserTypeEnum;
import com.gy.hsxt.uc.as.bean.common.AsBankAcctInfo;
import com.gy.hsxt.uc.as.bean.common.AsQkBank;
import com.gy.hsxt.uc.as.bean.ent.AsEntStatusInfo;

/***************************************************************************
 * <PRE>
 * Description 		: 互生币账户处理类
 * 
 * Project Name   	: hsxt-access-web-person 
 * 
 * Package Name     : com.gy.hsxt.access.web.person.controllers.accountManagement  
 * 
 * File Name        : IntegralAccountController 
 * 
 * Author           : LiZhi Peter
 * 
 * Create Date      : 2015-10-26 下午5:22:16
 * 
 * Update User      : LiZhi Peter
 * 
 * Update Date      : 2015-10-26 下午5:22:16
 * 
 * UpdateRemark 	: 说明本次修改内容
 * 
 * Version          : v1.0
 * 
 * </PRE>
 ***************************************************************************/
@SuppressWarnings("rawtypes")
@Controller
@RequestMapping("hsbAccount")
public class HsbAccountController extends BaseController {
	@Resource
	private IIntegralAccountService integralAccountService; // 用户积分服务类

	@Resource
	private IBalanceService balanceService; // 账户余额查询服务类

	@Autowired
	private CompanyConfigService scsConfigService; // 全局配置文件

	@Autowired
	private ITransInnerService ransInnerService; // 内部转账

	// @Resource
	// private ICardholderService cardholderService;// 持卡人服务类

	@Resource
	private IOrderService orderService;// 发布订单服务

	@Autowired
	private IPubParamService pubParamService;// 平台服务公共信息服务类

	@Autowired
	private BusinessParamSearch businessParamSearch;// 业务参数服务接口

	@Autowired
	private IAOExchangeHsbService iAOExchangeHsbService;// 兑换互生币

	@Autowired
	private IUCAsPwdService iUCAsPwdService; // 密码管理

	@Autowired
	private IUCAsBankAcctInfoService iUCAsBankAcctInfoService;// 银行账户管理

	@Autowired
	private LcsClient lcsClient; // 全局配置服务接口

	@Autowired
	private IBusinessCustParamService iBusinessCustParamService;

	@Resource
	private RedisUtil<String> changeRedisUtil;

	@Autowired
	private IUCAsEntService iuCAsEntService; // 查询企业状态dubbo接口

	@Resource
	private IPwdSetService pwdSetService;  //交易密码服务类
	
    @Resource
    private ICommService commService;  //公共接口类

    @Resource
    private IMemberEnterpriseService imemberEnterpriseService;
    
	/**
	 * 查询余额信息
	 * 
	 * @param custId
	 *            客户号
	 * @param entCustId
	 *            企业客户号
	 * @param request
	 *            当前请求数据
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value =
	{ "/find_hsb_blance" }, method =
	{ RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope findHsbBlance(String custId, String entCustId, String pointNo, HttpServletRequest request)
	{

		// 变量声明
		HttpRespEnvelope hre = null;
		Map<String, Object> map = null;
		AccountBalance accBalance = null;

		try
		{
			// Token验证
			super.checkSecureToken(request);

			map = new HashMap<String, Object>();
			// 非空数据验证
			RequestUtil.verifyParamsIsNotEmpty(new Object[]
			{ entCustId, RespCode.AS_ENT_CUSTID_INVALID.getCode(), RespCode.AS_ENT_CUSTID_INVALID.getDesc() } // 企业客户号
			);
			
			//默认金额为零
			String defaultValue ="0.00";
			
			// 获取流通币
			accBalance = balanceService.findAccNormal(entCustId, AccountType.ACC_TYPE_POINT20110.getCode());
			if (accBalance != null)
			{
				map.put("circulationHsb", accBalance.getAccBalance());
			}else{
				map.put("circulationHsb", defaultValue);
			}

			// 慈善救助基金
			accBalance = balanceService.findAccNormal(entCustId, AccountType.ACC_TYPE_POINT20130.getCode());
			if (accBalance != null)
			{
				map.put("directionalHsb", accBalance.getAccBalance());
			}else{
				map.put("directionalHsb", defaultValue);
			}

			hre = new HttpRespEnvelope(map);

		} catch (HsException e)
		{
			e.printStackTrace();
			hre = new HttpRespEnvelope(e);
		}

		return hre;
	}

	/**
	 * 初始化互生币转货币
	 * 
	 * @param custId
	 *            客户号
	 * @param pointNo
	 *            互生卡号
	 * @param request
	 *            当前请求数据
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value =
	{ "/init_hsb_transfer_currency" }, method =
	{ RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope initHsbTransferCurrency(String custId, String entCustId, String pointNo,
			HttpServletRequest request)
	{

		// 变量声明
		LocalInfo lcalInfo = null; // 本地平台信息
		HttpRespEnvelope hre = null;
		Map<String, Object> map = null; // 返回数据临时结合
		AccountBalance accBalance = null; // 账户余额对象

		try
		{
			// Token验证
			super.checkSecureToken(request);

			map = new HashMap<String, Object>();

			// 非空数据验证
			RequestUtil.verifyParamsIsNotEmpty(new Object[]
			{ entCustId, RespCode.AS_ENT_CUSTID_INVALID.getCode(), RespCode.AS_ENT_CUSTID_INVALID.getDesc() } // 企业客户号
			);

			// 查询流通币
			accBalance = balanceService.findAccNormal(entCustId, AccountType.ACC_TYPE_POINT20110.getCode());

			if (accBalance != null)
			{
				map.put("circulationHsb", accBalance.getAccBalance());
			}

			// 获取本平台的信息
			lcalInfo = this.pubParamService.findSystemInfo();

			map.put("exchangeRate", lcalInfo.getExchangeRate()); // 货币转换比率
			map.put("currencyCode", lcalInfo.getCurrencyNameCn()); // 币种
			map.put("currencyFee", scsConfigService.getHsbConvertibleFee()); // 货币转换费
			map.put("hsbMin", this.scsConfigService.getHsbCirculationConvertibleMin()); // 转出最小互生币数
			map.put("hsbMinimum", scsConfigService.getHsbLeastIntegration());// 保底互生币
			
			//获取业务限制数据
            Map<String, String> restrictMap = commService.getBusinessRestrict(entCustId, BusinessParam.HSB_TO_CASH);
            map.put("restrict", restrictMap);

			hre = new HttpRespEnvelope(map);

		} catch (HsException e)
		{
			hre = new HttpRespEnvelope(e);
		}
		return hre;
	}

	/**
	 * 互生币转货币
	 * 
	 * @param hsbToCash
	 *            互生币转货币实体
	 * @param tradePwd
	 *            交易密码
	 * @param randomToken
	 *            随机token
	 * @param request
	 *            当前请求
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value =
	{ "/hsb_transfer_currency" }, method =
	{ RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope hsbTransferCurrency(@ModelAttribute HsbToCash hsbToCash, String entCustId,
			Integer entResType, String tradePwd, String randomToken, HttpServletRequest request)
	{

		// Token验证
		HttpRespEnvelope hre = null;
		try
		{
			// Token验证
			super.checkSecureToken(request);

			// 非空数据验证
			RequestUtil.verifyParamsIsNotEmpty(new Object[]
			{ tradePwd, RespCode.PW_TRADEPWD_INVALID.getCode(), RespCode.PW_TRADEPWD_INVALID.getDesc() }, // 交易密码
					new Object[]
					{ randomToken, RespCode.AS_SECURE_TOKEN_INVALID.getCode(), RespCode.AS_SECURE_TOKEN_INVALID.getDesc() }, // 随机token
					new Object[]
					{ hsbToCash.getHsResNo(), RespCode.AS_CUSTID_INVALID.getCode(), RespCode.AS_CUSTID_INVALID.getDesc() }, // 客户互生号
					new Object[]
					{ entCustId, RespCode.AS_ENT_CUSTID_INVALID.getCode(), RespCode.AS_ENT_CUSTID_INVALID.getDesc() }, // 企业客户号
					new Object[]
					{ entResType, RespCode.PW_BIZREG_ENTTYPE_INVALID.getCode(), RespCode.PW_BIZREG_ENTTYPE_INVALID.getDesc() }, // 企业客户号
					new Object[]
					{ hsbToCash.getCustName(), RespCode.PW_CUSTNAME_INVALID.getCode(), RespCode.PW_CUSTNAME_INVALID.getDesc() } // 客户名称
			);

			//验证企业状态
			verificationEntStatus(entCustId);
			
			// 呼叫中心的特殊处理方法
			tradePwd = RequestUtil.encodeBase64String(request, tradePwd);

			int channelCode = Channel.WEB.getCode();
			// 呼叫中心渠道类型特殊处理
			if(RequestUtil.isIVR(request))
			{
				channelCode = Channel.IVR.getCode();
			}
			
			pwdSetService.checkTradePwd(entCustId, tradePwd, UserTypeEnum.ENT.getType(), randomToken);

			// 正整数验证
			RequestUtil.verifyPositiveInteger(hsbToCash.getFromHsbAmt(), RespCode.PW_HSB_NUMBER_INVALID);

			// 转出最小互生币数
			String itcm = this.scsConfigService.getHsbCirculationConvertibleMin();
			if (DoubleUtil.parseDouble(hsbToCash.getFromHsbAmt()) < DoubleUtil.parseDouble(itcm))
			{
				hre = new HttpRespEnvelope(RespCode.PW_MIN_NUMBER_INVALID.getCode(),
						RespCode.PW_MIN_NUMBER_INVALID.getDesc());
				return hre;
			}

			// 内部转账构造相关属性
			hsbToCash.setOptCustId(hsbToCash.getCustId()); // 操作员编号
			hsbToCash.setCustId(entCustId); // 企业客户号
			hsbToCash.setCustType(entResType); // 企业类型
			hsbToCash.setChannel(channelCode); // 终端渠道

			// 新增内部转帐记录
			ransInnerService.saveHsbToCash(hsbToCash);

			hre = new HttpRespEnvelope();

		} catch (HsException e)
		{
			e.printStackTrace();
			hre = new HttpRespEnvelope(e);
		}
		return hre;
	}

	/**
	 * 初始化兑换互生币
	 * 
	 * @param custId
	 *            客户号
	 * @param pointNo
	 *            互生卡号
	 * @param entType
	 *            企业类型
	 * @param request
	 *            当前请求数据
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value =
	{ "/init_transfer_hsb" }, method =
	{ RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope initTransferHsb(String pointNo, String entResType, CompanyBase companyBase,
			HttpServletRequest request)
	{

		// 变量声明
		LocalInfo lcalInfo = null; // 本地平台信息
		Map<String, Object> map = null; // 返回数据临时结合

		// Token验证
		HttpRespEnvelope hre = super.checkSecureToken(request);
		if (hre != null)
		{
			return hre;
		}

		// 执行查询方法
		try
		{
			map = new HashMap<String, Object>();

			// 兑换互生币（组）
			String sysGroupCode = BusinessParam.EXCHANGE_HSB.getCode();
			Map<String, BusinessSysParamItemsRedis> businessMap = businessParamSearch
					.searchSysParamItemsByGroup(sysGroupCode);

			int ent_Type = Integer.parseInt(entResType);
			// 成员企业/托管公司/服务公司 单笔最小值Code
			String codeMin = "";
			// 成员企业/托管公司/服务公司 单笔最大值Code
			String codeMax = "";
			// 成员企业/托管公司/服务公司 单笔最小值
			String aounmtMinByTime;
			// 成员企业/托管公司/服务公司 单笔最大值
			String amountMaxByTime;
			// 成员企业/托管公司/服务公司 单日限额
			String amountMax = "";
			// 成员企业
			if (CustType.MEMBER_ENT.getCode() == ent_Type)
			{
				codeMin = BusinessParam.B_SINGLE_BUY_HSB_MIN.getCode();
				codeMax = BusinessParam.B_SINGLE_BUY_HSB_MAX.getCode();
				amountMax = BusinessParam.B_SINGLE_DAY_BUY_HSB_MAX.getCode();
			}
			// 托管公司
			else if (CustType.TRUSTEESHIP_ENT.getCode() == ent_Type)
			{
				codeMin = BusinessParam.T_SINGLE_BUY_HSB_MIN.getCode();
				codeMax = BusinessParam.T_SINGLE_BUY_HSB_MAX.getCode();
				amountMax = BusinessParam.T_SINGLE_DAY_BUY_HSB_MAX.getCode();
			}
			// 服务公司
			else if (CustType.SERVICE_CORP.getCode() == ent_Type)
			{
				codeMin = BusinessParam.S_SINGLE_BUY_HSB_MIN.getCode();
				codeMax = BusinessParam.S_SINGLE_BUY_HSB_MAX.getCode();
				amountMax = BusinessParam.S_SINGLE_DAY_BUY_HSB_MAX.getCode();
			}

			aounmtMinByTime = businessMap.get(codeMin) == null ? "0" : businessMap.get(codeMin).getSysItemsValue();
			amountMaxByTime = businessMap.get(codeMax) == null ? "0" : businessMap.get(codeMax).getSysItemsValue();
			amountMax = businessMap.get(amountMax) == null ? "0" : businessMap.get(amountMax).getSysItemsValue();

			map.put("aounmtMinByTime", aounmtMinByTime);
			map.put("amountMaxByTime", amountMaxByTime);

			lcalInfo = pubParamService.findSystemInfo();
			// 货币转转换比率
			map.put("exchangeRate", lcalInfo.getExchangeRate());
			// 币种
			map.put("currencyCode", lcalInfo.getCurrencyCode());
			// 币种名称
			map.put("currencyNameCn", lcalInfo.getCurrencyNameCn());
			
			//获取业务限制数据
            Map<String, String> restrictMap = commService.getBusinessRestrict(companyBase.getEntCustId(), BusinessParam.BUY_HSB);
            map.put("restrict", restrictMap);
            
			hre = new HttpRespEnvelope(map);

		} catch (HsException e)
		{
			e.printStackTrace();
			hre = new HttpRespEnvelope(e);
		}
		return hre;
	}

	/**
	 * 兑换互生币提交数据
	 * 
	 * @param order
	 *            订单对象
	 * @param tradePwd
	 *            交易密码
	 * @param randomToken
	 *            随机token（防止CSRF攻击）
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value =
	{ "/transfer_hsb" }, method =
	{ RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope transferHsb(@ModelAttribute Order order, String custId, String entCustId,
			String custEntName, String entResType, String randomToken, HttpServletRequest request)
	{

		// 变量声明
		BuyHsb buyHsb = null;

		int ent_Type = Integer.parseInt(entResType);
		// Token验证
		HttpRespEnvelope hre = super.checkSecureToken(request);
		if (hre != null)
		{
			return hre;
		}

		// 执行查询方法
		try
		{
			// 非空验证
			RequestUtil.verifyParamsIsNotEmpty(new Object[]
			{ custEntName, RespCode.PW_CUSTNAME_INVALID.getDesc() }, // 客户名称
					new Object[]
					{ order.getHsResNo(), RespCode.PW_HSRESNO_INVALID.getCode(), RespCode.PW_HSRESNO_INVALID.getDesc() }, // 互生卡号不准为空
					new Object[]
					{ entCustId, RespCode.PW_CUSTID_INVALID.getCode(), RespCode.PW_CUSTID_INVALID.getDesc() }, // 客户号不能为空
					new Object[]
					{ custId, RespCode.PW_OPTCUSTID_INVALID.getCode(), RespCode.PW_OPTCUSTID_INVALID.getDesc() }, // 操作员号不能为空
					new Object[]
					{ ent_Type, RespCode.PW_CUSTTYPE_INVALID.getCode(), RespCode.PW_CUSTTYPE_INVALID.getDesc() }, // 客户类型
					new Object[]
					{ order.getOrderHsbAmount(), RespCode.PW_TRANSFER_NUMBER_INVALID.getCode(), RespCode.PW_TRANSFER_NUMBER_INVALID.getDesc() }// 兑换的互生币数量
			);

			//验证企业状态
			verificationEntStatus(entCustId);
			
			// 封装添加的兑换互生币订单信息
			buyHsb = new BuyHsb();
			buyHsb.setCustId(entCustId);
			buyHsb.setCustName(custEntName);
			buyHsb.setHsResNo(order.getHsResNo());
			buyHsb.setCustType(ent_Type);
			buyHsb.setBuyHsbAmt(order.getOrderHsbAmount());
			buyHsb.setOptCustId(custId);
			buyHsb.setChannel(OrderChannel.WEB.getCode());
			buyHsb.setRemark("兑换互生币");

			String transNo = iAOExchangeHsbService.saveBuyHsb(buyHsb);

			hre = new HttpRespEnvelope(transNo);
		} catch (HsException e)
		{
			hre = new HttpRespEnvelope(e);
		}
		return hre;
	}

	/**
	 * 分页查询的条件约束
	 * 
	 * @param request
	 *            当前请求对象
	 * @param filterMap
	 *            查询条件Map
	 * @param sortMap
	 * @return
	 * @see com.gy.hsxt.access.web.common.controller.BaseController#beforeList(javax.servlet.http.HttpServletRequest,
	 *      java.util.Map, java.util.Map)
	 */
	@Override
	protected HttpRespEnvelope beforeList(HttpServletRequest request, Map filterMap, Map sortMap)
	{
		// 变量声明
		HttpRespEnvelope hre = null;

		hre = RequestUtil.checkParamIsNotEmpty(new Object[]
		{ filterMap.get("beginDate"), "30001" }, // 开始时间非空验证
				new Object[]
				{ filterMap.get("endDate"), "30002" }, // 结束时间非空验证
				new Object[]
				{ filterMap.get("subject"), "3000" }); // 业务类别

		// 返回验证信息
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
	@RequestMapping(value =
	{ "/validate_PayPwd" }, method =
	{ RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope validatePayPwd(String entCustId, String transNo, String bindingNo, String smsCode,
			HttpServletRequest request)
	{

		// 变量声明
		HttpRespEnvelope hre = null;

		try
		{
			// Token验证
			super.checkSecureToken(request);

			// 校验支付
			// iUCAsPwdService.checkTradePwd(entCustId, tradePwd,
			// UserTypeEnum.ENT.getType(), randomToken);

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
	 * @param entCustId
	 *            客户号
	 * @param entResType
	 *            客户类型
	 * @param request
	 *            当前请求数据
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value =
	{ "/validate_HbPay" }, method =
	{ RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope validateHbPay(String entCustId, String tradePwd, String entResType, String randomToken,
			String transNo, HttpServletRequest request)
	{

		// 变量声明
		HttpRespEnvelope hre = null;

		try
		{
			// Token验证
			super.checkSecureToken(request);

			// 校验支付密码
			iUCAsPwdService.checkTradePwd(entCustId, tradePwd, UserTypeEnum.ENT.getType(), randomToken);

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
	@RequestMapping(value =
	{ "/open_QuickPay" }, method =
	{ RequestMethod.POST }, produces = "application/json;charset=UTF-8")
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
	@RequestMapping(value =
	{ "/net_Pay" }, method =
	{ RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope getNetPayUrl(String privObligate, String transNo, String jumpUrl,
			HttpServletRequest request)
	{

		// 变量声明
		HttpRespEnvelope hre = null;

		try
		{
			// Token验证
			super.checkSecureToken(request);

			// 地区平台信息
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
	@RequestMapping(value =
	{ "/search_BankInfo_List" }, method =
	{ RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope searchBankInfoList(String entCustId, String custType, HttpServletRequest request)
	{

		// 变量声明
		HttpRespEnvelope hre = null;
		try
		{
			// Token验证
			super.checkSecureToken(request);

			// 查询绑定的银行卡
			List<AsBankAcctInfo> list = iUCAsBankAcctInfoService.listBanksByCustId(entCustId,
					UserTypeEnum.ENT.getType());

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
	@RequestMapping(value =
	{ "/search_Quick_BankInfo_List" }, method =
	{ RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope searchQuickBankInfoList(String entCustId, String custType, HttpServletRequest request)
	{

		// 变量声明
		HttpRespEnvelope hre = null;
		try
		{
			// Token验证
			super.checkSecureToken(request);

			// 查询绑定的银行卡
			List<AsQkBank> list = iUCAsBankAcctInfoService.listQkBanksByCustId(entCustId, UserTypeEnum.ENT.getType());

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
	 * 获取支持快捷支付的银行列表
	 * 
	 * @param custId
	 *            客户号
	 * @param request
	 *            当前请求数据
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value =
	{ "/query_PayBankAll" }, method =
	{ RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope getPayBankAll(String entCustId, HttpServletRequest request)
	{

		// 变量声明
		HttpRespEnvelope hre = null;
		Map<String, List<PayBank>> map = null;
		List<PayBank> debitCardList = null;
		List<PayBank> creditCardList = null;
		try
		{
			// Token验证
			super.checkSecureToken(request);

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
	@RequestMapping(value =
	{ "/quickPay_VerifyCode" }, method =
	{ RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope getQuickPayVerifyCode(String transNo, String bindingNo, String privObligate,
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
			hre.setRetCode(e.getErrorCode());
		}

		return hre;
	}

	/**
	 * 支付限额设置
	 * 
	 * @param custId
	 *            客户号
	 * @param pointNo
	 *            互生卡号
	 * @param entType
	 *            企业类型
	 * @param request
	 *            当前请求数据
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value =
	{ "/pay_Limit_Setting" }, method =
	{ RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope payLimitSetting(String custId, String pointNo, String entResType,
			HttpServletRequest request)
	{

		

		// Token验证
		HttpRespEnvelope hre = super.checkSecureToken(request);
		String entCustId = request.getParameter("entCustId");

		if (hre != null)
		{
			return hre;
		}

		// 执行查询方法
		try
		{
			// 变量声明
			Map<String, Object> map = new HashMap<String, Object>();// 返回数据临时结合

			// 互生币支付code
			String code = BusinessParam.HSB_PAYMENT.getCode();
			// 企业互生币支付单笔限额code
			String codeMin = BusinessParam.ENT_PAYMENT_MAX.getCode();
			// 企业互生币支付当日限额code
			String codeDayMax = BusinessParam.ENT_PAYMENT_DAY_MAX.getCode();
			// 企业互生币支付单日交易次数code
			String codeTimesMax = BusinessParam.ENT_PAYMENT_DAY_NUMBER.getCode();

			BusinessCustParamItemsRedis business = businessParamSearch.searchCustParamItemsByIdKey(entCustId, code,
					codeMin);
			// 企业互生币支付单笔限额
			String amountmin = business.getSysItemsValue() == null ? "0" : business.getSysItemsValue();

			business = businessParamSearch.searchCustParamItemsByIdKey(entCustId, code, codeDayMax);
			// 企业互生币支付当日限额code
			String maxAmountMax = business.getSysItemsValue() == null ? "0" : business.getSysItemsValue();

			business = businessParamSearch.searchCustParamItemsByIdKey(entCustId, code, codeTimesMax);
			// 企业互生币支付单日交易次数
			String dayTimesMax = business.getSysItemsValue() == null ? "0" : business.getSysItemsValue();

			map.put("aounmtMinByTime", amountmin);
			map.put("amountMaxByDay", maxAmountMax);
			map.put("timesMaxByDay", dayTimesMax);

			hre = new HttpRespEnvelope(map);

		} catch (HsException e)
		{
			e.printStackTrace();
			hre = new HttpRespEnvelope(e);
		}
		return hre;
	}

	/**
	 * 支付限额修改
	 * 
	 * @param custId
	 *            客户号
	 * @param pointNo
	 *            互生卡号
	 * @param entType
	 *            企业类型
	 * @param request
	 *            当前请求数据
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value =
	{ "/pay_Limit_Upate" }, method =
	{ RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope payLimitUpdate(String entCustId, String tradePwd, String pointNo, String randomToken,
			HttpServletRequest request)
	{

		// Token验证
		HttpRespEnvelope hre = super.checkSecureToken(request);
		String custName = request.getParameter("custName");
		String custId = request.getParameter("custId");
		String aounmtMinByTime = request.getParameter("aounmtMinByTime");
		String amountMaxByDay = request.getParameter("amountMaxByDay");
		String code = request.getParameter("code");

		// 校验支付密码
		if (hre != null)
		{
			return hre;
		}

		// 执行查询方法
		try
		{
			verificationCode(custId, code.trim());

			// 校验支付密码
			iUCAsPwdService.checkTradePwd(entCustId, tradePwd, UserTypeEnum.ENT.getType(), randomToken);

			BusinessCustParamItemsRedis custParamItemsRedis = new BusinessCustParamItemsRedis();
			custParamItemsRedis.setHsResNo(pointNo);
			custParamItemsRedis.setCustName(custName);
			custParamItemsRedis.setSysGroupCode(BusinessParam.HSB_PAYMENT.getCode());
			custParamItemsRedis.setSysItemsKey(BusinessParam.ENT_PAYMENT_MAX.getCode());
			custParamItemsRedis.setSysItemsName("企业互生币支付单笔限额");
			custParamItemsRedis.setSysItemsValue(aounmtMinByTime);
			BusinessCustParamItemsRedis custParamItemsRedis1 = new BusinessCustParamItemsRedis();
			custParamItemsRedis1.setHsResNo(pointNo);
			custParamItemsRedis1.setCustName(custName);
			custParamItemsRedis1.setSysGroupCode(BusinessParam.HSB_PAYMENT.getCode());
			custParamItemsRedis1.setSysItemsKey(BusinessParam.ENT_PAYMENT_DAY_MAX.getCode());
			custParamItemsRedis1.setSysItemsName("企业互生币支付当日限额");
			custParamItemsRedis1.setSysItemsValue(amountMaxByDay);

			List<BusinessCustParamItemsRedis> list = new ArrayList<BusinessCustParamItemsRedis>();
			list.add(custParamItemsRedis);
			list.add(custParamItemsRedis1);

			iBusinessCustParamService.addOrUpdateCustParamItemsList(entCustId, list);
			hre = new HttpRespEnvelope();

		} catch (HsException e)
		{
			e.printStackTrace();
			hre = new HttpRespEnvelope(e);
		}
		return hre;
	}

	/**
	 * 验证码验证
	 */
	public void verificationCode(String custId, String smsCode)
	{
		// 获取验证码
		String vCode = changeRedisUtil.get(custId + "_" + VerificationCodeType.payLimitSetting, String.class);
		// 判断为空
		if (StringUtils.isEmpty(vCode))
		{
			throw new HsException(RespCode.PW_VERIFICATION_CODE_NULL.getCode(),
					RespCode.PW_VERIFICATION_CODE_NULL.getDesc());
		}
		// 判断相等
		if (!smsCode.toUpperCase().equals(vCode.toUpperCase()))
		{
			throw new HsException(RespCode.PW_VERIFICATION_CODE_ERROR.getCode(),
					RespCode.PW_VERIFICATION_CODE_ERROR.getDesc());
		}
	}

	 /**
     * 验证企业状态
     * 
     * @param custId 企业客户编号
     * @throws HsException
     */
    public void verificationEntStatus(String entCustId) throws HsException
    {
        AsEntStatusInfo status = imemberEnterpriseService.searchEntStatusInfo(entCustId);
        if (status != null)
        {
            switch (status.getBaseStatus())
            {
            case 5:
                throw new HsException(ASRespCode.EW_STATUS5_IS_NOT_REPLACE_BUY_HSB.getCode(),
                        ASRespCode.EW_STATUS5_IS_NOT_REPLACE_BUY_HSB.getDesc());
            case 8:
                throw new HsException(ASRespCode.EW_STATUS8_IS_NOT_REPLACE_BUY_HSB.getCode(),
                        ASRespCode.EW_STATUS8_IS_NOT_REPLACE_BUY_HSB.getDesc());
            }
        }
    }
    
    /**
     * 获取兑换互生币支付url
     * @param netPay
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = { "/convert_pay" }, method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope convertPay(NetPay netPay, HttpServletRequest request) {
        HttpRespEnvelope hre = null;

        try
        {
            //验证非空
            netPay.vaildNotNull();
            
            // 获取互生币兑换支付URL
            String url = ransInnerService.convertHSBPay(netPay);
            hre = new HttpRespEnvelope(url);

        }
        catch (HsException e)
        {
            hre = new HttpRespEnvelope(e);
            hre.setRetCode(e.getErrorCode());
        }

        return hre;
    }
    
	/**
	 * @return
	 * @see com.gy.hsxt.access.web.common.controller.BaseController#getEntityService()
	 */
	@Override
	protected IBaseService getEntityService()
	{
		return integralAccountService;
	}
}
