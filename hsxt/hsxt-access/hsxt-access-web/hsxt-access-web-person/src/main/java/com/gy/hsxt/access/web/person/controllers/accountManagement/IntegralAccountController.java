/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.person.controllers.accountManagement;

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
import com.gy.hsxt.access.web.person.services.accountManagement.IBalanceService;
import com.gy.hsxt.access.web.person.services.accountManagement.IIntegralAccountService;
import com.gy.hsxt.access.web.person.services.accountManagement.ITransInnerService;
import com.gy.hsxt.access.web.person.services.common.IPubParamService;
import com.gy.hsxt.access.web.person.services.common.PersonConfigService;
import com.gy.hsxt.access.web.person.services.safetyset.IPwdSetService;
import com.gy.hsxt.ao.bean.PvToHsb;
import com.gy.hsxt.bp.api.IBusinessSysBoSettingService;
import com.gy.hsxt.bp.bean.BusinessCustParamItemsRedis;
import com.gy.hsxt.bp.bean.BusinessSysBoSetting;
import com.gy.hsxt.bp.client.api.BusinessParamSearch;
import com.gy.hsxt.bs.bean.order.PointInvest;
import com.gy.hsxt.common.constant.AccountType;
import com.gy.hsxt.common.constant.BusinessParam;
import com.gy.hsxt.common.constant.Channel;
import com.gy.hsxt.common.constant.CustType;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.DoubleUtil;
import com.gy.hsxt.common.utils.StringUtils;
import com.gy.hsxt.lcs.bean.LocalInfo;
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
public class IntegralAccountController extends BaseController {
	@Resource
	private IIntegralAccountService integralAccountService; // 用户积分服务类

	@Resource
	private IBalanceService balanceService; // 账户余额查询服务类

    @Resource
    private PersonConfigService personConfigSevice; // 全局配置文件

	@Autowired
	private ITransInnerService ransInnerService; // 内部转账

	@Autowired
	private IPubParamService pubParamService;// 平台服务公共信息服务类

	@Autowired
	public BusinessParamSearch businessParamSearch;
	/**
	 * 交易密码服务类
	 */
	@Resource
	private IPwdSetService pwdSetService;

	/**
	 * 根据 流水号查详情
	 * 
	 * @param request
	 * @param response
	 * @param transNo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/queryDetailsByTransNo" }, method = {
			RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope queryDetailsByTransNo(String transNo,
			String transType,HttpServletRequest request,
			HttpServletResponse response) {
		HttpRespEnvelope hre = null;
		try {
			// Token验证
			super.verifySecureToken(request);
			Map<String, Object> detail = balanceService.queryAccOptDetailed(
					transNo, transType);
			hre = new HttpRespEnvelope(detail);

		} catch (HsException e) {
			hre = new HttpRespEnvelope(e);
		}
		return hre;
	}

	/**
	 * 查询余额信息
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
	@RequestMapping(value = { "/find_integral_balance" }, method = {
			RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope findIntegralBalance(/*
												 * @RequestBody(required=false)
												 * ParameterValue paramValue
												 *//*
													 * ,
													 * @RequestParam(required=false
													 * ) String custId
													 * ,@RequestParam
													 * (required=false) String
													 * token
													 */String custId,
			String token, HttpServletRequest request) {

		// 变量声明
		Double itnAccBalance = null;
		Map<String, Object> map = null;
		AccountBalance accBalance = null;
		AccountEntrySum accEntrySum = null;

		HttpRespEnvelope hre = null;
		// 执行查询方法
		try {
			// Token验证
			super.checkSecureToken(request);

			map = new HashMap<String, Object>();

			// 查询积分账户余额
			accBalance = balanceService.findAccNormal(custId,
					AccountType.ACC_TYPE_POINT10110.getCode());
			// 获取保底积分数
			String pliNumber = personConfigSevice.getPersonLeastIntegration();
			// 非空验证
			if (accBalance != null) {

				// 积分账户余额
				itnAccBalance = DoubleUtil.parseDouble(accBalance
						.getAccBalance());
				// 保存账户余额
				map.put("pointBlance", itnAccBalance);

				// 可用积分数 =积分数-保底积分数
				itnAccBalance = DoubleUtil.sub(itnAccBalance,DoubleUtil.parseDouble(pliNumber));

				//如果小于零则显示0的积分余额
				itnAccBalance = itnAccBalance<0?0.00D:itnAccBalance;
				// 保存账户余额
				map.put("usablePointNum", itnAccBalance);
			}

			// 查询今日积分数
			accEntrySum = balanceService.findPerIntegralByToday(custId,
					AccountType.ACC_TYPE_POINT10110.getCode());

			// 没有今日积分门人显示的值
			String sumAmount = "0.00";
			if (accEntrySum != null) {
				sumAmount = accEntrySum.getSumAmount();
			}

			// 保底积分数
			map.put("securityPointNumber", pliNumber);
			// 今日积分数
			map.put("todaysNewPoint", sumAmount);// 保存今日积分数

			// 积分账户的可用积分数可转出，转出积分数为不小于的整数！
			map.put("integrationMin",
					this.personConfigSevice.getIntegrationConvertibleMin());

			hre = new HttpRespEnvelope(map);

		} catch (HsException e) {
			hre = new HttpRespEnvelope(e);
		}

		return hre;
	}

	/**
	 * 初始化积分转互生币
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
	@RequestMapping(value = { "/init_integral_transfer_Hsb" }, method = {
			RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope initIntegralTransferHsb(String custId,
			String pointNo, HttpServletRequest request) {

		// 变量声明
		LocalInfo lcalInfo = null; // 本地平台信息
		Double itnAccBalance = null; // 积分账户余额
		Map<String, Object> map = null; // 返回数据临时结合
		AccountBalance accBalance = null; // 账户余额对象

		// Token验证
		HttpRespEnvelope hre = super.checkSecureToken(request);
		if (hre != null) {
			return hre;
		}

		// 执行查询方法
		try {
			// Token验证
			super.checkSecureToken(request);

			map = new HashMap<String, Object>();

			// 查询积分账户余额
			accBalance = balanceService.findAccNormal(custId,
					AccountType.ACC_TYPE_POINT10110.getCode());

			// 获取保底积分数
			String pliNumber = personConfigSevice.getPersonLeastIntegration();

			// 非空验证
			if (accBalance != null) {
				// 积分账户余额
				itnAccBalance = DoubleUtil.parseDouble(accBalance
						.getAccBalance());

				// 可用积分数
				itnAccBalance = DoubleUtil.sub(itnAccBalance,Double.parseDouble(pliNumber));
				
				//如果小于零则显示0的积分余额
				itnAccBalance = itnAccBalance<0?0.00D:itnAccBalance;
				
				// 保存账户余额
				map.put("pointBlance", itnAccBalance);
			}

			// 积分账户的可用积分数可转出，转出积分数为不小于的整数！
			map.put("integrationMin",
					this.personConfigSevice.getIntegrationConvertibleMin());

			// 获取本平台的信息
			lcalInfo = this.pubParamService.findSystemInfo();
			// 货币转换比率
			map.put("exchangeRate", lcalInfo.getExchangeRate());
			// 币种
			map.put("currencyCode", lcalInfo.getCurrencyCode());
			Map<String,String> infoMap = getRestrictMap(BusinessParam.PV_TO_HSB, custId);
			if(null != infoMap && infoMap.size() > 0){
				map.putAll(infoMap);
			}
			hre = new HttpRespEnvelope(map);

		} catch (HsException e) {
			hre = new HttpRespEnvelope(e);
		}
		return hre;
	}

	private Map<String,String> getRestrictMap(BusinessParam businessParam,String custId){
		Map<String,String> infoMap = new HashMap<>();
		Map<String, BusinessCustParamItemsRedis>  custParamMap = businessParamSearch.searchCustParamItemsByGroup(custId);
		if(null == custParamMap){
			return null;
		}
		BusinessCustParamItemsRedis items = custParamMap.get(businessParam.getCode());
		if(null == items){
			return null;
		}
		String restrictValue = StringUtils.nullToEmpty(items.getSysItemsValue());
		String restrictRemark = StringUtils.nullToEmpty(items.getSettingRemark());
		String restrictName = StringUtils.nullToEmpty(items.getSysItemsKey());
		infoMap.put("restrictValue", restrictValue);
		infoMap.put("restrictRemark", restrictRemark);
		infoMap.put("restrictName", restrictName);
		return infoMap;
	}
	/**
	 * 积分转互生币提交方法
	 * 
	 * @param pvToHsb
	 *            积分转互生币实体
	 * @param tradePwd
	 *            交易密码
	 * @param randomToken
	 *            随机token
	 * @param request
	 *            当前请求
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/integral_transfer_Hsb" }, method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope integralTransferHsb(
			@ModelAttribute PvToHsb pvToHsb, String tradePwd,
			String randomToken, HttpServletRequest request) {
		// Token验证
		HttpRespEnvelope hre = null;
		try {
			// Token验证
			super.checkSecureToken(request);

			// 非空数据验证
			RequestUtil.verifyParamsIsNotEmpty(new Object[] { tradePwd,
					RespCode.PW_TRADEPWD_INVALID.getCode(),
					RespCode.PW_TRADEPWD_INVALID.getDesc() }, // 交易密码
					new Object[] { randomToken,
							RespCode.AS_SECURE_TOKEN_INVALID.getCode(),
							RespCode.AS_SECURE_TOKEN_INVALID.getDesc() }, // 随机token
					new Object[] { pvToHsb.getHsResNo(),
							RespCode.AS_POINTNO_INVALID.getCode(),
							RespCode.AS_POINTNO_INVALID.getDesc() } // 互生号不能为空

					);
			// 特殊情况加的 以后要去除
			if (pvToHsb.getCustName().equals(null)
					|| pvToHsb.getCustName().equals("undefined")) {
				pvToHsb.setCustName("test");
			}
			// 正整数验证
			RequestUtil.verifyPositiveInteger(pvToHsb.getAmount(),
					RespCode.PW_INTEGRAL_NUMBER_INVALID);

			// 转出的积分大于最小积分数量验证
			String itcm = this.personConfigSevice
					.getIntegrationConvertibleMin();
			if (DoubleUtil.parseDouble(pvToHsb.getAmount()) < DoubleUtil
					.parseDouble(itcm)) {
				hre = new HttpRespEnvelope(
						RespCode.PW_INTEGRATIONCONVERTIBLEMIN_INVALID.getCode(),
						RespCode.PW_INTEGRATIONCONVERTIBLEMIN_INVALID.getDesc());
				return hre;
			}
			// 呼叫中心的特殊处理方法
			tradePwd = RequestUtil.encodeBase64String(request, tradePwd);
			// 交易密码验证
			pwdSetService.checkTradePwd(pvToHsb.getCustId(), tradePwd,
					UserTypeEnum.CARDER.getType(), randomToken);
			// 内部转账构造相关属性
			pvToHsb.setCustType(CustType.PERSON.getCode()); // 持卡人
			pvToHsb.setOptCustId(pvToHsb.getCustId()); // 操作员编号
			pvToHsb.setChannel(Channel.WEB.getCode()); // 终端渠道

			// 新增内部转帐记录
			ransInnerService.savePvToHsb(pvToHsb);

			hre = new HttpRespEnvelope();

		} catch (HsException e) {
			e.printStackTrace();
			hre = new HttpRespEnvelope(e);
		}
		return hre;
	}

	/**
	 * 初始化积分投资页面
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
	@RequestMapping(value = { "/init_integral_investment" }, method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope initIntegralInvestment(String custId,
			String pointNo, HttpServletRequest request) {

		// 变量声明
		Double itnAccBalance = null;
		Map<String, Object> map = null;
		AccountBalance accBalance = null;
		// Token验证
		HttpRespEnvelope hre = super.checkSecureToken(request);
		if (hre != null) {
			return hre;
		}

		// 执行查询方法
		try {
			map = new HashMap<String, Object>();

			// 查询积分账户余额
			accBalance = balanceService.findAccNormal(custId,
					AccountType.ACC_TYPE_POINT10110.getCode());
			// 获取保底积分数
			String pliNumber = personConfigSevice.getPersonLeastIntegration();
			// 非空验证
			if (accBalance != null) {

				// 积分账户余额
				itnAccBalance = DoubleUtil.parseDouble(accBalance
						.getAccBalance());

				// 可用积分数 =积分数-保底积分数
				itnAccBalance = DoubleUtil.sub(itnAccBalance,DoubleUtil.parseDouble(pliNumber));
				
				//如果小于零则显示0的积分余额
				itnAccBalance = itnAccBalance<0?0.00D:itnAccBalance;

				// 保存账户余额
				map.put("pointBlance", itnAccBalance);
			}

			// 积分投资整数倍！
			map.put("integrationInvIntMult",
					this.personConfigSevice.getIntegrationInvIntMult());
			Map<String,String> infoMap = getRestrictMap(BusinessParam.PV_INVEST, custId);
			if(null != infoMap && infoMap.size() > 0){
				map.putAll(infoMap);
			}
			hre = new HttpRespEnvelope(map);

		} catch (HsException e) {
			hre = new HttpRespEnvelope(e);
		}

		return hre;
	}

	/**
	 * 积分转互生币提交方法
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
	public HttpRespEnvelope integralInvestment(
			@ModelAttribute PointInvest pointInvest, String tradePwd,
			String randomToken, HttpServletRequest request) {

		HttpRespEnvelope hre = null;

		// 执行查询方法
		try {
			// Token验证
			super.checkSecureToken(request);

			// 非空数据验证
			RequestUtil.verifyParamsIsNotEmpty(new Object[] { tradePwd,
					RespCode.PW_TRADEPWD_INVALID.getCode(),
					RespCode.PW_TRADEPWD_INVALID.getDesc() }, // 交易密码
					new Object[] { randomToken,
							RespCode.AS_SECURE_TOKEN_INVALID.getCode(),
							RespCode.AS_SECURE_TOKEN_INVALID.getDesc() }, // 随机token
					new Object[] { pointInvest.getHsResNo(),
							RespCode.AS_CUSTID_INVALID.getCode(),
							RespCode.AS_CUSTID_INVALID.getDesc() } // 客户互生号
					);
			// 特殊情况加的 以后要去除
			if (pointInvest.getCustName().equals(null)
					|| pointInvest.getCustName().equals("undefined")) {
				pointInvest.setCustName("test");
			}
			// 正整数验证
			RequestUtil.verifyPositiveInteger(pointInvest.getInvestAmount(),
					RespCode.PW_INTEGRAL_NUMBER_INVALID);

			// 呼叫中心的特殊处理方法
			tradePwd = RequestUtil.encodeBase64String(request, tradePwd);

			// 交易密码验证
			pwdSetService.checkTradePwd(pointInvest.getCustId(), tradePwd,
					UserTypeEnum.CARDER.getType(), randomToken);

			// 积分账户的可用积分数可转出，转出积分数为不小于的整数！
			String itim = this.personConfigSevice.getIntegrationInvIntMult();
			if (DoubleUtil.parseDouble(pointInvest.getInvestAmount()) < DoubleUtil
					.parseDouble(pointInvest.getInvestAmount())) {
				hre = new HttpRespEnvelope(
						RespCode.PW_INTEGRATIONINVINTMULT_INVALID.getCode(),
						RespCode.PW_INTEGRATIONINVINTMULT_INVALID.getDesc());
				return hre;
			}

			// 内部转账构造相关属性
			pointInvest.setCustType(CustType.PERSON.getCode()); // 持卡人
			pointInvest.setChannel(Channel.WEB.getCode()); // 终端渠道

			// 执行内部转账
			ransInnerService.savePointInvest(pointInvest);

			hre = new HttpRespEnvelope();

		} catch (HsException e) {
			e.printStackTrace();
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
	protected HttpRespEnvelope beforeList(HttpServletRequest request,
			Map filterMap, Map sortMap) {
		// 变量声明
		HttpRespEnvelope hre = null;

		hre = RequestUtil.checkParamIsNotEmpty(
				new Object[] { filterMap.get("beginDate"), "30001" }, // 开始时间非空验证
				new Object[] { filterMap.get("endDate"), "30002" } // 结束时间非空验证
				// ,new Object[] { filterMap.get("subject"), "3000" }
				); // 业务类别

		// 返回验证信息
		return hre;
	}

	/**
	 * 积分账户明细查询
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/detailed_page" }, method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope searchAccEntryPage(HttpServletRequest request,
			HttpServletResponse response) {
		HttpRespEnvelope hre = null;

		try {
			// Token验证
			super.verifySecureToken(request);

			// 分页查询
			request.setAttribute("serviceName", balanceService);
			request.setAttribute("methodName", "searchAccEntryPage");
			hre = super.doList(request, response);

		} catch (HsException e) {
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
		return integralAccountService;
	}
}
