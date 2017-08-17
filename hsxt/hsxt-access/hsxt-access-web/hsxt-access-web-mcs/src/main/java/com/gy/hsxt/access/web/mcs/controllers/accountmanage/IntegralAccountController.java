/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.mcs.controllers.accountmanage;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gy.hsxt.ac.bean.AccountBalance;
import com.gy.hsxt.ac.bean.AccountEntrySum;
import com.gy.hsxt.access.web.bean.MCSBase;
import com.gy.hsxt.access.web.common.bean.HttpRespEnvelope;
import com.gy.hsxt.access.web.common.controller.BaseController;
import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.access.web.common.utils.RequestUtil;
import com.gy.hsxt.access.web.mcs.services.accountmanage.IBalanceService;
import com.gy.hsxt.access.web.mcs.services.common.IPubParamService;
import com.gy.hsxt.access.web.mcs.services.common.MCSConfigService;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.constant.AccountType;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.DoubleUtil;
import com.gy.hsxt.lcs.bean.LocalInfo;
import com.gy.hsxt.ps.bean.AllocDetail;
import com.gy.hsxt.ps.bean.AllocDetailSum;

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
	private IBalanceService balanceService; // 账户余额查询服务类

	@Resource
	private MCSConfigService mcsConfigService; // 全局配置

	@Autowired
	private IPubParamService pubParamService;// 平台服务公共信息服务类

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
	public HttpRespEnvelope findIntegralBalance(MCSBase mcsBase,
			HttpServletRequest request) {

		// 变量声明
		Double itnAccBalance = null;
		Map<String, Object> map = null;
		AccountBalance accBalance = null;
		AccountEntrySum accEntrySum = null;
		HttpRespEnvelope hre = null;
		// 执行查询方法
		try {
			map = new HashMap<String, Object>();
			// Token验证
			super.checkSecureToken(request);
			// 非空数据验证
			RequestUtil.verifyParamsIsNotEmpty(new Object[] {
					mcsBase.getEntCustId(),
					RespCode.AS_ENT_CUSTID_INVALID.getCode(),
					RespCode.AS_ENT_CUSTID_INVALID.getDesc() } // 企业客户号
					);
			// 获取平台信息
			LocalInfo localInfo = pubParamService.findSystemInfo();
			// 查询积分账户余额
			accBalance = balanceService.findAccNormal(mcsBase.getEntCustId(),
					AccountType.ACC_TYPE_POINT10110.getCode());
			// 非空验证
			if (accBalance != null) {
				// 积分账户余额
				itnAccBalance = DoubleUtil.parseDouble(accBalance
						.getAccBalance());
				// 保存账户余额
				map.put("pointBlance", itnAccBalance);
				// 获取保底积分数
				Double pliNumber = Double.parseDouble(balanceService
						.baseIntegral());
				// 可用积分数 =积分数-保底积分数
				itnAccBalance = DoubleUtil.sub(itnAccBalance, pliNumber);
				
				//如果小于零则显示0的积分余额
				itnAccBalance = itnAccBalance<0?0.00D:itnAccBalance;
				
				// 保底积分数
				map.put("securityPointNumber", pliNumber);
				// 保存账户余额
				map.put("usablePointNum", itnAccBalance);
			}

			// 国家代码
			map.put("countryNo", localInfo.getCountryNo());
			// 查询昨日积分数
			accEntrySum = balanceService.findEntIntegralByYesterday(
					mcsBase.getEntCustId(),
					AccountType.ACC_TYPE_POINT10110.getCode());

			// 无昨日积显示的值
			String sumAmount = "0.00";
			if (accEntrySum != null) {
				sumAmount = accEntrySum.getSumAmount();
			}
			// 昨日积分数
			map.put("yesterdayNewPoint", sumAmount);

			hre = new HttpRespEnvelope(map);
		} catch (HsException e) {
			hre = new HttpRespEnvelope(e);
		}

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
	 * 消费积分分配明细查询
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/pointAllocDetail" }, method = {
			RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope pointDetail(String entResNo, String batchNo,
			HttpServletRequest request) {
		HttpRespEnvelope hre = null;
	try {
			// Token验证
			super.verifySecureToken(request);

			AllocDetailSum pdr = balanceService.pointDetailSum(batchNo,
					entResNo);
			hre = new HttpRespEnvelope(pdr);
		} catch (HsException e) {
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
	@RequestMapping(value = { "/pointAllocDetailList" }, method = {
			RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope queryPointAllotDetailedList(
			HttpServletRequest request, HttpServletResponse response) {
		HttpRespEnvelope hre = null;

		try {
			// Token验证
			super.verifySecureToken(request);

			// 设置服务名和方法名，调用父类查询分页方法
			request.setAttribute("serviceName", balanceService);
			request.setAttribute("methodName", "pointDetailList");
			return super.doList(request, response);

		} catch (HsException e) {
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
            request.setAttribute("serviceName", balanceService);
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
	protected IBaseService getEntityService() {
		return null;
	}
}
