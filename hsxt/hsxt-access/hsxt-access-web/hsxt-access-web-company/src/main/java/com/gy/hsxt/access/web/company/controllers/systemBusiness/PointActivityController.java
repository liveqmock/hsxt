/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.company.controllers.systemBusiness;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gy.hsxt.ac.bean.AccountBalance;
import com.gy.hsxt.access.web.common.bean.HttpRespEnvelope;
import com.gy.hsxt.access.web.common.constant.ASRespCode;
import com.gy.hsxt.access.web.common.controller.BaseController;
import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.access.web.common.utils.RequestUtil;
import com.gy.hsxt.access.web.company.services.accountManagement.IBalanceService;
import com.gy.hsxt.access.web.company.services.common.PubParamService;
import com.gy.hsxt.access.web.company.services.companyInfo.ICompanyInfoService;
import com.gy.hsxt.access.web.company.services.systemBusiness.IPointActivityService;
import com.gy.hsxt.bs.bean.entstatus.PointActivity;
import com.gy.hsxt.bs.common.enumtype.entstatus.PointAppType;
import com.gy.hsxt.common.constant.AccountType;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.DoubleUtil;
import com.gy.hsxt.lcs.bean.LocalInfo;
import com.gy.hsxt.uc.as.bean.ent.AsEntExtendInfo;

/**
 * 积分活动申请（停止/参与）
 * 
 * @Package: com.gy.hsxt.access.web.company.controllers.systemBusiness
 * @ClassName: PointActivityController
 * @Description: TODO
 * 
 * @author: zhangcy
 * @date: 2015-10-22 下午6:39:16
 * @version V3.0.0
 */
@SuppressWarnings("rawtypes")
@Controller
@RequestMapping("pointactivity")
public class PointActivityController extends BaseController {

	@Resource
	private IPointActivityService ipointActivityService;

	@Resource
	private IBalanceService balanceService; // 账户信息服务类

	@Resource
	private PubParamService pubParamService;

	@Resource
	private ICompanyInfoService companyService;// 企业信息服务类

	/**
	 * 查询企业积分预付款余额、货币转换比率
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/query_point_num" }, method = {
			RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope queryPointNum(HttpServletRequest request) {
		// Token验证
		HttpRespEnvelope httpRespEnvelope = super.checkSecureToken(request);
		if (httpRespEnvelope == null) {
			String custId = request.getParameter("entCustId");
			try {
				Map<String, Object> result = new HashMap<String, Object>();
				// 查询企业积分预付款余额
				AccountBalance balance = balanceService.findAccNormal(custId,
						AccountType.ACC_TYPE_POINT30210.getCode());
				String pointfee = "0";
				String accBalance = "0";
				if (balance != null) {
					pointfee = balance.getAccBalance();
					accBalance = balance.getAccBalance();
				}
				result.put("pointfee", pointfee);
				// 获取本地信息
				LocalInfo local = pubParamService.findSystemInfo();
				// 结算币种
				result.put("currence", local.getCurrencyNameCn());
				// 货币转换比率
				String exchangeRate = local.getExchangeRate() == null ? "1.0000"
						: local.getExchangeRate();
				result.put("transrat", exchangeRate);
				// 转入互生币数量
				result.put(
						"hsbnum",
						DoubleUtil.mul(Long.parseLong(accBalance),
								Long.parseLong(exchangeRate)));
				httpRespEnvelope = new HttpRespEnvelope(result);
			} catch (HsException e) {
				httpRespEnvelope = new HttpRespEnvelope(e);
			}
		}
		return httpRespEnvelope;
	}

	/**
	 * 停止积分活动申请
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/apply_pointactivity_apply" }, method = {
			RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope pointActivityApply(HttpServletRequest request,
			@ModelAttribute PointActivity activity) {
		// Token验证
		HttpRespEnvelope httpRespEnvelope = super.checkSecureToken(request);
		if (httpRespEnvelope == null) {
			try {
				// 非空验证
				RequestUtil.verifyParamsIsNotEmpty(new Object[] {
						activity.getApplyReason(),
						ASRespCode.EW_SYSBUSINESS_APPLY_REASON.getCode() } // 申请原因不准为空
						);
				if (activity.getApplyType() == 0) {
					activity.setApplyType(PointAppType.STOP_PONIT_ACITIVITY
							.getCode());
				} else {
					activity.setApplyType(PointAppType.JOIN_PONIT_ACTIVITY
							.getCode());
				}
				// 企业客户号 lyh2016.2.27号修改
				String entCustId = request.getParameter("entCustId");
				AsEntExtendInfo info = this.companyService
						.findEntAllInfo(entCustId);
				activity.setEntAddress(info.getEntRegAddr());
				activity.setLinkmanMobile(info.getContactPhone());
				activity.setLinkman(info.getContactPerson());

				ipointActivityService.pointActivityApply(activity);
				
				httpRespEnvelope = new HttpRespEnvelope();
			} catch (Exception e) {
				httpRespEnvelope = new HttpRespEnvelope(e);
			}
		}
		return httpRespEnvelope;
	}

	@Override
	protected IBaseService getEntityService() {
		return ipointActivityService;
	}

}
