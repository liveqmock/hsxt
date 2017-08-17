/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.company.controllers.accountManagement;

import java.util.HashMap;
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
import com.gy.hsxt.ac.bean.AccountEntrySum;
import com.gy.hsxt.access.web.common.bean.HttpRespEnvelope;
import com.gy.hsxt.access.web.common.controller.BaseController;
import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.access.web.common.utils.RequestUtil;
import com.gy.hsxt.access.web.company.services.accountManagement.IBalanceService;
import com.gy.hsxt.access.web.company.services.accountManagement.IIntegralAccountService;
import com.gy.hsxt.access.web.company.services.accountManagement.ITransInnerService;
import com.gy.hsxt.access.web.company.services.common.CompanyConfigService;
import com.gy.hsxt.access.web.company.services.common.ICommService;
import com.gy.hsxt.access.web.company.services.common.IPubParamService;
import com.gy.hsxt.access.web.company.services.safeSet.IPwdSetService;
import com.gy.hsxt.ao.bean.PvToHsb;
import com.gy.hsxt.bs.bean.order.PointInvest;
import com.gy.hsxt.common.constant.AccountType;
import com.gy.hsxt.common.constant.BusinessParam;
import com.gy.hsxt.common.constant.Channel;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.BigDecimalUtils;
import com.gy.hsxt.common.utils.DoubleUtil;
import com.gy.hsxt.common.utils.HsResNoUtils;
import com.gy.hsxt.lcs.bean.LocalInfo;
import com.gy.hsxt.ps.bean.AllocDetailSum;
import com.gy.hsxt.uc.as.api.ent.IUCAsEntService;
import com.gy.hsxt.uc.as.api.enumerate.UserTypeEnum;

/***************************************************************************
 * <PRE>
 * Description 		: 积分账户操作Controller
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
@Controller
@RequestMapping("integralAccount")
public class IntegralAccountController extends BaseController<IIntegralAccountService> {

	@Resource
	private IBalanceService balanceService; // 账户余额查询服务类
	
	@Resource
	private IIntegralAccountService integralAccountService;

	@Autowired
	private CompanyConfigService companyConfigService; // 全局配置文件

	@Autowired
	private ITransInnerService ransInnerService; // 内部转账

	@Autowired
	private IPubParamService pubParamService;// 平台服务公共信息服务类

	@Resource
	private IPwdSetService pwdSetService;  //交易密码服务类
	
	@Autowired
    private IUCAsEntService iuCAsEntService; //查询企业状态dubbo接口
	
	@Resource
	private ICommService commService;  //公共接口类

	/**
	 * 查询余额信息
	 * 
	 * @param custId
	 *            操作员客户号
	 * @param entCustId
	 *            企业客户号
	 * @param request
	 *            当前请求数据
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/find_integral_balance" }, method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope findIntegralBalance(String custId, String entCustId, String token,
			HttpServletRequest request)
	{

		// 变量声明
		String itnAccBalance = null;
		Map<String, Object> map = null;
		AccountBalance accBalance = null;
		AccountEntrySum accEntrySum = null;

		HttpRespEnvelope hre = null;

		// 执行查询方法
		try
		{
			// Token验证
			super.checkSecureToken(request);

			// 非空数据验证
			RequestUtil.verifyParamsIsNotEmpty(new Object[] { entCustId, RespCode.AS_ENT_CUSTID_INVALID.getCode(),
					RespCode.AS_ENT_CUSTID_INVALID.getDesc() } // 企业客户号
					);

			map = new HashMap<String, Object>();

			// 查询积分账户余额
			accBalance = balanceService.findAccNormal(entCustId, AccountType.ACC_TYPE_POINT10110.getCode());
			// 获取保底积分数
			String pliNumber = companyConfigService.getPersonLeastIntegration();
			// 保底积分数
			map.put("securityPointNumber", pliNumber);
			// 非空验证
			if (accBalance != null)
			{

				// 积分账户余额
				itnAccBalance = accBalance.getAccBalance();
				// 保存账户余额
				map.put("pointBlance", itnAccBalance);
				
				//积分余额大于保底积分则相减否则显示0
				if(DoubleUtil.parseDouble(itnAccBalance)> DoubleUtil.parseDouble(pliNumber)){
					// 可用积分数 =积分数-保底积分数
					itnAccBalance = BigDecimalUtils.ceilingSub(itnAccBalance, pliNumber).toString();
				}else{
					itnAccBalance = "0";
				}

				// 可用积分数
				map.put("usablePointNum", itnAccBalance);
			}

			// 查询昨日积分数
			accEntrySum = balanceService.findPerIntegralByYesterday(entCustId,
					AccountType.ACC_TYPE_POINT10110.getCode()); // balanceService.findPerIntegralByToday(entCustId,
																// AccountType.ACC_TYPE_POINT10110.getCode());

			// 没有今日积分门人显示的值
			String sumAmount = "0.00";
			if (accEntrySum != null)
			{
				sumAmount = accEntrySum.getSumAmount();
			}

			// 昨日积分数
			map.put("yesterdayNewPoint", sumAmount);// 保存昨日积分数

			// 积分账户的可用积分数可转出，转出积分数为不小于的整数！
			map.put("integrationMin", this.companyConfigService.getIntegrationConvertibleMin());

			hre = new HttpRespEnvelope(map);

		} catch (HsException e)
		{
			hre = new HttpRespEnvelope(e);
		}

		return hre;
	}

	/**
	 * 初始化积分转互生币
	 * 
	 * @param custId
	 *            客户号
	 * @param entCustId
	 *            企业互生号
	 * @param request
	 *            当前请求数据
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/init_integral_transfer_Hsb" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope initIntegralTransferHsb(String custId, String entCustId, String pointNo,
			HttpServletRequest request)
	{

		// 变量声明
		LocalInfo lcalInfo = null; // 本地平台信息
		String itnAccBalance = null; // 积分账户余额
		Map<String, Object> map = null; // 返回数据临时结合
		AccountBalance accBalance = null; // 账户余额对象

		// Token验证
		HttpRespEnvelope hre = super.checkSecureToken(request);
		if (hre != null)
		{
			return hre;
		}

		// 执行查询方法
		try
		{
			// Token验证
			super.checkSecureToken(request);

			// 非空数据验证
			RequestUtil.verifyParamsIsNotEmpty(new Object[] { entCustId, RespCode.AS_ENT_CUSTID_INVALID.getCode(),
					RespCode.AS_ENT_CUSTID_INVALID.getDesc() } // 企业客户号
					);

			map = new HashMap<String, Object>();

			// 查询积分账户余额
			accBalance = balanceService.findAccNormal(entCustId, AccountType.ACC_TYPE_POINT10110.getCode());

			// 非空验证
			if (accBalance != null)
			{
				// 积分账户余额
				itnAccBalance = accBalance.getAccBalance();
				
				String pliNumber= balanceService.baseIntegral();
				//积分余额大于保底积分则相减否则显示0
				if(DoubleUtil.parseDouble(itnAccBalance)> DoubleUtil.parseDouble(pliNumber)){
					// 可用积分数 =积分数-保底积分数
					itnAccBalance = BigDecimalUtils.ceilingSub(itnAccBalance, pliNumber).toString();
				}else{
					itnAccBalance = "0";
				}
				// 保存账户余额
				map.put("pointBlance", itnAccBalance);
			}

			// 积分账户的可用积分数可转出，转出积分数为不小于的整数！
			map.put("integrationMin", this.companyConfigService.getIntegrationConvertibleMin());

			// 获取本平台的信息
			lcalInfo = this.pubParamService.findSystemInfo();
			// 货币转换比率
			map.put("exchangeRate", lcalInfo.getExchangeRate());
			// 币种
			map.put("currencyCode", lcalInfo.getCurrencyNameCn());
			
			//获取业务限制数据
			Map<String, String> restrictMap = commService.getBusinessRestrict(entCustId, BusinessParam.PV_TO_HSB);
            map.put("restrict", restrictMap);

			hre = new HttpRespEnvelope(map);

		} catch (HsException e)
		{
			hre = new HttpRespEnvelope(e);
		}
		return hre;
	}

	/**
	 * 积分转互生币提交方法
	 * 
	 * @param pvToHsb
	 *            积分转互生币实体
	 * @param tradePwd
	 *            交易密码(AES加密后的密文)
	 * @param entCustId
	 *            企业互生号
	 * @param randomToken
	 *            随机token
	 * @param request
	 *            当前请求
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/integral_transfer_Hsb" }, method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope integralTransferHsb(@ModelAttribute PvToHsb pvToHsb, String resNo, String entCustId,
			String tradePwd, String randomToken, HttpServletRequest request)
	{
		// Token验证
		HttpRespEnvelope hre = null;
		try
		{
			// Token验证
			super.checkSecureToken(request);
			// 非空数据验证
			RequestUtil.verifyParamsIsNotEmpty(
					new Object[] { tradePwd, RespCode.PW_TRADEPWD_INVALID.getCode(),
							RespCode.PW_TRADEPWD_INVALID.getDesc() }, // 交易密码
					new Object[] { randomToken, RespCode.AS_SECURE_TOKEN_INVALID.getCode(),
							RespCode.AS_SECURE_TOKEN_INVALID.getDesc() }, // 随机token
					new Object[] { pvToHsb.getHsResNo(), RespCode.AS_POINTNO_INVALID.getCode(),
							RespCode.AS_POINTNO_INVALID.getDesc() }, // 客户互生号
					new Object[] { entCustId, RespCode.AS_ENT_CUSTID_INVALID.getCode(),
							RespCode.AS_ENT_CUSTID_INVALID.getDesc() }, // 企业客户号
					new Object[] { pvToHsb.getCustName(), RespCode.PW_CUSTNAME_INVALID.getCode(),
							RespCode.PW_CUSTNAME_INVALID.getDesc() } // 客户名称
					);

			// 正整数验证
			RequestUtil.verifyPositiveInteger(pvToHsb.getAmount(), RespCode.PW_INTEGRAL_NUMBER_INVALID);

			// 呼叫中心的特殊处理方法
			tradePwd = RequestUtil.encodeBase64String(request, tradePwd);
			
			int channelCode = Channel.WEB.getCode();
			// 呼叫中心渠道类型特殊处理
			if(RequestUtil.isIVR(request))
			{
				channelCode = Channel.IVR.getCode();
			}
			// 交易密码验证
			pwdSetService.checkTradePwd(entCustId, tradePwd, UserTypeEnum.ENT.getType(), randomToken);

			// 转出的积分大于最小积分数量验证
			String itcm = this.companyConfigService.getIntegrationConvertibleMin();
			if (DoubleUtil.parseDouble(pvToHsb.getAmount()) < DoubleUtil.parseDouble(itcm))
			{
				hre = new HttpRespEnvelope(RespCode.PW_INTEGRATIONCONVERTIBLEMIN_INVALID.getCode(),
						RespCode.PW_INTEGRATIONCONVERTIBLEMIN_INVALID.getDesc());
				return hre;
			}

			// 内部转账构造相关属性

			pvToHsb.setOptCustId(pvToHsb.getCustId()); // 操作员编号
			pvToHsb.setCustId(entCustId); // 企业客户号
			pvToHsb.setCustType(HsResNoUtils.getCustTypeByHsResNo(pvToHsb.getHsResNo())); // 持卡人
			pvToHsb.setChannel(channelCode); // 终端渠道

			// 新增内部转帐记录
			ransInnerService.savePvToHsb(pvToHsb);

			hre = new HttpRespEnvelope();

		} catch (HsException e)
		{
			e.printStackTrace();
			hre = new HttpRespEnvelope(e);
		}
		return hre;
	}

	/**
	 * 初始化积分投资页面
	 * 
	 * @param custId
	 *            操作员客户号
	 * @param entCustId
	 *            企业客户号
	 * @param request
	 *            当前请求数据
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/init_integral_investment" }, method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope initIntegralInvestment(String custId, String entCustId, String pointNo,
			HttpServletRequest request)
	{

		// 变量声明
		String itnAccBalance = null;
		Map<String, Object> map = null;
		AccountBalance accBalance = null;
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

			// 非空数据验证
			RequestUtil.verifyParamsIsNotEmpty(new Object[] { entCustId, RespCode.AS_ENT_CUSTID_INVALID.getCode(),
					RespCode.AS_ENT_CUSTID_INVALID.getDesc() } // 企业客户号
					);

			// 查询积分账户余额
			accBalance = balanceService.findAccNormal(entCustId, AccountType.ACC_TYPE_POINT10110.getCode());

			// 非空验证
			if (accBalance != null)
			{

				// 积分账户余额
				itnAccBalance = accBalance.getAccBalance();

				// 获取保底积分数
				String pliNumber= balanceService.baseIntegral();
				
				//积分余额大于保底积分则相减否则显示0
				if(DoubleUtil.parseDouble(itnAccBalance)> DoubleUtil.parseDouble(pliNumber)){
					// 可用积分数 =积分数-保底积分数
					itnAccBalance = BigDecimalUtils.ceilingSub(itnAccBalance, pliNumber).toString();
				}else{
					itnAccBalance = "0";
				}
				// 保存账户余额
				map.put("pointBlance", itnAccBalance);
			}

			// 积分投资整数倍！
			map.put("integrationInvIntMult", this.companyConfigService.getIntegrationInvIntMult());
			
            //获取业务限制数据
            Map<String, String> restrictMap = commService.getBusinessRestrict(entCustId, BusinessParam.PV_INVEST);
            map.put("restrict", restrictMap);

			hre = new HttpRespEnvelope(map);

		} catch (HsException e)
		{
			hre = new HttpRespEnvelope(e);
		}

		return hre;
	}

	/**
	 * 积分投资提交方法
	 * 
	 * @param transInner
	 *            转帐记录实体
	 * @param tradePwd
	 *            交易密码
	 * @param integralNum
	 *            转出积分数
	 * @param hsbNum
	 *            转入互生币
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/integral_investment" }, method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope integralInvestment(@ModelAttribute PointInvest pointInvest, String entCustId,
			String tradePwd, String randomToken, HttpServletRequest request)
	{

		HttpRespEnvelope hre = null;

		// 执行查询方法
		try
		{
			// Token验证
			super.checkSecureToken(request);

			// 非空数据验证
			RequestUtil.verifyParamsIsNotEmpty(
					new Object[] { tradePwd, RespCode.PW_TRADEPWD_INVALID.getCode(),
							RespCode.PW_TRADEPWD_INVALID.getDesc() }, // 交易密码
					new Object[] { randomToken, RespCode.AS_SECURE_TOKEN_INVALID.getCode(),
							RespCode.AS_SECURE_TOKEN_INVALID.getDesc() }, // 随机token
					new Object[] { entCustId, RespCode.AS_ENT_CUSTID_INVALID.getCode(),
							RespCode.AS_ENT_CUSTID_INVALID.getDesc() }, // 企业客户号
					new Object[] { pointInvest.getHsResNo(), RespCode.AS_CUSTID_INVALID.getCode(),
							RespCode.AS_CUSTID_INVALID.getDesc() }, // 客户互生号
					new Object[] { pointInvest.getCustName(), RespCode.PW_CUSTNAME_INVALID.getCode(),
							RespCode.PW_CUSTNAME_INVALID.getDesc() } // 客户名称
					);

			// 正整数验证
			RequestUtil.verifyPositiveInteger(pointInvest.getInvestAmount(), RespCode.PW_INTEGRAL_NUMBER_INVALID);

			// 呼叫中心的特殊处理方法
			tradePwd = RequestUtil.encodeBase64String(request, tradePwd);
			
			int channelCode = Channel.WEB.getCode();
			// 呼叫中心渠道类型特殊处理
			if(RequestUtil.isIVR(request))
			{
				channelCode = Channel.IVR.getCode();
			}

			// 交易密码验证
			pwdSetService.checkTradePwd(entCustId, tradePwd, UserTypeEnum.ENT.getType(), randomToken);

			// 积分账户的可用积分数可转出，转出积分数为不小于的整数！
			String itim = this.companyConfigService.getIntegrationInvIntMult();
			if (DoubleUtil.parseDouble(pointInvest.getInvestAmount()) < DoubleUtil.parseDouble(itim))
			{
				hre = new HttpRespEnvelope(RespCode.PW_INTEGRATIONINVINTMULT_INVALID.getCode(),
						RespCode.PW_INTEGRATIONINVINTMULT_INVALID.getDesc());
				return hre;
			}

			// 内部转账构造相关属性
			pointInvest.setOptCustId(pointInvest.getCustId()); // 操作员客户号
			pointInvest.setCustId(entCustId); // 企业客户号
			pointInvest.setCustType(HsResNoUtils.getCustTypeByHsResNo(pointInvest.getHsResNo())); // 持卡人
			pointInvest.setChannel(channelCode); // 终端渠道

			// 执行内部转账
			ransInnerService.savePointInvest(pointInvest);

			hre = new HttpRespEnvelope();

		} catch (HsException e)
		{
			e.printStackTrace();
			hre = new HttpRespEnvelope(e);
		}
		return hre;
	}

	/**
	 * 分页查询积分明细
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年2月2日 下午8:23:03
	 * @param request
	 * @param response
	 * @return
	 * @return : HttpRespEnvelope
	 * @version V3.0.0
	 */
	@ResponseBody
	@RequestMapping(value = { "/detailed_page" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope searchAccEntryPage(HttpServletRequest request, HttpServletResponse response)
	{
		HttpRespEnvelope hre = null;

		try
		{
			// Token验证
			super.verifySecureToken(request);

			// 分页查询
			request.setAttribute("serviceName", balanceService);
			request.setAttribute("methodName", "searchAccEntryPage");
			hre = super.doList(request, response);

		} catch (HsException e)
		{
			hre = new HttpRespEnvelope(e);
		}

		return hre;
	}

	/**
	 * 所有详情
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年1月26日 下午7:40:12
	 * @param request
	 * @param response
	 * @return
	 * @return : HttpRespEnvelope
	 * @version V3.0.0
	 */
	@ResponseBody
	@RequestMapping(value = { "/get_acc_opt_detailed" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope getAccOptDetailed(HttpServletRequest request, HttpServletResponse response)
	{
		HttpRespEnvelope hre = null;

		try
		{
			// Token验证
			super.verifySecureToken(request);
			String transNo = request.getParameter("transNo");
			String transType = request.getParameter("transType");
			hre = new HttpRespEnvelope(balanceService.queryAccOptDetailed(transNo, transType));
		} catch (HsException e)
		{
			hre = new HttpRespEnvelope(e);
		}
		return hre;
	}

	/**
	 * 消费积分分配明细查询
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/get_point_allot_detailed" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope pointDetail(String pointNo, String batchNo, HttpServletRequest request)
	{
		HttpRespEnvelope hre = null;

		try
		{
			// Token验证
			super.verifySecureToken(request);
			AllocDetailSum pdr = balanceService.queryPointAllotDetailed(batchNo, pointNo);
			hre = new HttpRespEnvelope(pdr);
		} catch (HsException e)
		{
			hre = new HttpRespEnvelope(e);
		}

		return hre;
	}

	/**
	 * 消费积分分配明细查询
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/get_point_allot_detailed_list" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope queryPointAllotDetailedList(HttpServletRequest request, HttpServletResponse response)
	{
		HttpRespEnvelope hre = null;

		try
		{
			// Token验证
			super.verifySecureToken(request);

			// 设置服务名和方法名，调用父类查询分页方法
			request.setAttribute("serviceName", balanceService);
			request.setAttribute("methodName", "queryPointAllotDetailedList");
			return super.doList(request, response);

		} catch (HsException e)
		{
			hre = new HttpRespEnvelope(e);
			return hre;
		}

	}
	/**
	 * 查询网上商城销售收入详情流水
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/getMallDetail" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope getMallDetail(HttpServletRequest request, HttpServletResponse response)
	{
		HttpRespEnvelope hre = null;

		try
		{
			// Token验证
			super.verifySecureToken(request);

			// 设置服务名和方法名，调用父类查询分页方法
			request.setAttribute("serviceName", balanceService);
			request.setAttribute("methodName", "proceedsOnLineEntryList");
			return super.doList(request, response);

		} catch (HsException e)
		{
			hre = new HttpRespEnvelope(e);
			return hre;
		}

	}
	/**
	 * 查询线下销售收入详情流水
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/getSaleDetail" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope getSaleDetail(HttpServletRequest request, HttpServletResponse response)
	{
		HttpRespEnvelope hre = null;

		try
		{
			// Token验证
			super.verifySecureToken(request);

			// 设置服务名和方法名，调用父类查询分页方法
			request.setAttribute("serviceName", balanceService);
			request.setAttribute("methodName", "proceedsEntryList");
			return super.doList(request, response);

		} catch (HsException e)
		{
			hre = new HttpRespEnvelope(e);
			return hre;
		}

	}
	
	/**
	 * 分页查询互生积分分配列
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/queryMlmListPage" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope queryMlmListPage(HttpServletRequest request, HttpServletResponse response)
	{
		HttpRespEnvelope hre = null;

		try
		{
			// Token验证
			super.verifySecureToken(request);

			// 设置服务名和方法名，调用父类查询分页方法
			request.setAttribute("serviceName", integralAccountService);
			request.setAttribute("methodName", "queryMlmListPage");
			return super.doList(request, response);

		} catch (HsException e)
		{
			hre = new HttpRespEnvelope(e);
			return hre;
		}

	}
	/**
	 * 分页查询再增值积分汇总列表
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/queryBmlmListPage" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope queryBmlmListPage(HttpServletRequest request, HttpServletResponse response)
	{
		HttpRespEnvelope hre = null;

		try
		{
			// Token验证
			super.verifySecureToken(request);

			// 设置服务名和方法名，调用父类查询分页方法
			request.setAttribute("serviceName", integralAccountService);
			request.setAttribute("methodName", "queryBmlmListPage");
			return super.doList(request, response);

		} catch (HsException e)
		{
			hre = new HttpRespEnvelope(e);
			return hre;
		}

	}
	
	/**
     * 分页查询消费积分分配汇总列表
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = { "/queryPointDetailSumPage" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope queryPointDetailSumPage(HttpServletRequest request, HttpServletResponse response)
    {
        HttpRespEnvelope hre = null;
        try
        {
            // Token验证
            super.verifySecureToken(request);
            // 设置服务名和方法名，调用父类查询分页方法
            request.setAttribute("serviceName", integralAccountService);
            request.setAttribute("methodName", "queryPointDetailSumPage");
            return super.doList(request, response);

        } catch (HsException e)
        {
            hre = new HttpRespEnvelope(e);
            return hre;
        }

    }
	
	/**
	 * @return
	 * @see com.gy.hsxt.access.web.common.controller.BaseController#getEntityService()
	 */
	@Override
	protected IBaseService<IIntegralAccountService> getEntityService()
	{
		return integralAccountService;
	}
}
