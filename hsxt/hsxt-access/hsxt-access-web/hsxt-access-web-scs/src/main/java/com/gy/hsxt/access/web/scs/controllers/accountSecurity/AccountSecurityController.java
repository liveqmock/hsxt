/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.scs.controllers.accountSecurity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gy.hsi.ds.common.utils.StringUtils;
import com.gy.hsi.redis.client.api.RedisUtil;
import com.gy.hsxt.access.web.bean.VerificationCodeType;
import com.gy.hsxt.access.web.common.bean.HttpRespEnvelope;
import com.gy.hsxt.access.web.common.controller.BaseController;
import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.bp.api.IBusinessCustParamService;
import com.gy.hsxt.bp.bean.BusinessCustParamItemsRedis;
import com.gy.hsxt.bp.client.api.BusinessParamSearch;
import com.gy.hsxt.common.constant.BusinessParam;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.uc.as.api.common.IUCAsPwdService;
import com.gy.hsxt.uc.as.api.enumerate.UserTypeEnum;

/***************************************************************************
 * <PRE>
 * Description 		: 账户安全
 * 
 * Project Name   	: hsxt-access-web-scs 
 * 
 * Package Name     : com.gy.hsxt.access.web.person.controllers.AccountSecurityController  
 * 
 * File Name        : IntegralAccountController 
 * 
 * Author           : weixz
 * 
 * Create Date      : 2016-01-26 下午15:22:16
 * 
 * Update User      : weixz
 * 
 * Update Date      : 2016-01-26 下午15:22:16
 * 
 * UpdateRemark 	: 说明本次修改内容
 * 
 * Version          : v1.0
 * 
 * </PRE>
 ***************************************************************************/
@Controller
@RequestMapping("accountSecurity")
public class AccountSecurityController extends BaseController {

	@Autowired
	private BusinessParamSearch businessParamSearch;// 业务参数服务接口

	@Autowired
	private IUCAsPwdService iUCAsPwdService; // 密码管理

	@Autowired
	private IBusinessCustParamService iBusinessCustParamService;

	@Resource
	private RedisUtil<String> changeRedisUtil;

	/**
	 * 支付限额设置
	 * 
	 * @param custId
	 *            客户号
	 * @param pointNo
	 *            互生卡号
	 * @param entType
	 *            服务公司类型
	 * @param request
	 *            当前请求数据
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/pay_Limit_Setting" }, method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope payLimitSetting(String custId, String pointNo, String entResType, HttpServletRequest request)
	{

		// 变量声明
		Map<String, Object> map = null; // 返回数据临时结合

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
			map = new HashMap<String, Object>();

			// 互生币支付code
			String code = BusinessParam.HSB_PAYMENT.getCode();
			// 服务公司互生币支付单笔限额code
			String codeMin = BusinessParam.COMPANY_PAYMENT_MAX.getCode();
			// 服务公司互生币支付当日限额code
			String codeDayMax = BusinessParam.COMPANY_PAYMENT_DAY_MAX.getCode();
			// 服务公司互生币支付单日交易次数code
			String codeTimesMax = BusinessParam.COMPANY_PAYMENT_DAY_NUMBER.getCode();

			BusinessCustParamItemsRedis business = businessParamSearch.searchCustParamItemsByIdKey(entCustId, code,
					codeMin);
			// 服务公司互生币支付单笔限额
			String amountmin = business.getSysItemsValue() == null ? "0" : business.getSysItemsValue();

			business = businessParamSearch.searchCustParamItemsByIdKey(entCustId, code, codeDayMax);
			// 服务公司互生币支付当日限额code
			String maxAmountMax = business.getSysItemsValue() == null ? "0" : business.getSysItemsValue();

			business = businessParamSearch.searchCustParamItemsByIdKey(entCustId, code, codeTimesMax);
			// 服务公司互生币支付单日交易次数
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
	 *            服务公司类型
	 * @param request
	 *            当前请求数据
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/pay_Limit_Upate" }, method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope payLimitUpdate(String entCustId, String tradePwd, String pointNo, String randomToken,
			HttpServletRequest request)
	{

		// Token验证
		HttpRespEnvelope hre = super.checkSecureToken(request);
		String custName = request.getParameter("custName");
		String aounmtMinByTime = request.getParameter("aounmtMinByTime");
		String amountMaxByDay = request.getParameter("amountMaxByDay");
		// String timesMaxByDay = request.getParameter("timesMaxByDay");
		String code = request.getParameter("code");
		String custId = request.getParameter("custId");

		// 校验支付密码
		if (hre != null)
		{
			return hre;
		}

		// 执行查询方法
		try
		{
			/*
			 * // 校验验证码 String validateCode =
			 * (String)request.getSession().getAttribute("code"); if(code ==
			 * null || "".equals(code) || !code.equals(validateCode)){ hre = new
			 * HttpRespEnvelope(); hre.setRetCode(15102); return hre; }
			 */
			verificationCode(custId, code.trim());

			// 校验支付密码
			iUCAsPwdService.checkTradePwd(entCustId, tradePwd, UserTypeEnum.ENT.getType(), randomToken);

			BusinessCustParamItemsRedis custParamItemsRedis = new BusinessCustParamItemsRedis();
			custParamItemsRedis.setHsResNo(pointNo);
			custParamItemsRedis.setCustName(custName);
			custParamItemsRedis.setSysGroupCode(BusinessParam.HSB_PAYMENT.getCode());
			custParamItemsRedis.setSysItemsKey(BusinessParam.COMPANY_PAYMENT_MAX.getCode());
			custParamItemsRedis.setSysItemsName("服务公司互生币支付单笔限额");
			custParamItemsRedis.setSysItemsValue(aounmtMinByTime);
			BusinessCustParamItemsRedis custParamItemsRedis1 = new BusinessCustParamItemsRedis();
			custParamItemsRedis1.setHsResNo(pointNo);
			custParamItemsRedis1.setCustName(custName);
			custParamItemsRedis1.setSysGroupCode(BusinessParam.HSB_PAYMENT.getCode());
			custParamItemsRedis1.setSysItemsKey(BusinessParam.COMPANY_PAYMENT_DAY_MAX.getCode());
			custParamItemsRedis1.setSysItemsName("服务公司互生币支付当日限额");
			custParamItemsRedis1.setSysItemsValue(amountMaxByDay);
			/*
			 * BusinessCustParamItemsRedis custParamItemsRedis2 = new
			 * BusinessCustParamItemsRedis();
			 * custParamItemsRedis2.setHsResNo(pointNo);
			 * custParamItemsRedis2.setCustName(custName);
			 * custParamItemsRedis2.setSysGroupCode
			 * (BusinessParam.HSB_PAYMENT.getCode());
			 * custParamItemsRedis2.setSysItemsKey
			 * (BusinessParam.COMPANY_PAYMENT_DAY_NUMBER.getCode());
			 * custParamItemsRedis2.setSysItemsName("服务公司互生币支付单日交易次数");
			 * custParamItemsRedis2.setSysItemsValue(timesMaxByDay);
			 */

			List<BusinessCustParamItemsRedis> list = new ArrayList<BusinessCustParamItemsRedis>();
			list.add(custParamItemsRedis);
			list.add(custParamItemsRedis1);
			// list.add(custParamItemsRedis2);

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
	 * @return
	 * @see com.gy.hsxt.access.web.common.controller.BaseController#getEntityService()
	 */
	@Override
	protected IBaseService getEntityService()
	{
		return null;
	}
}
