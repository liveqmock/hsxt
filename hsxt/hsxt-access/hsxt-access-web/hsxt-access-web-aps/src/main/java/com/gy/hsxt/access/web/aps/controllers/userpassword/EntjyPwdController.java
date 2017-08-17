/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.aps.controllers.userpassword;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gy.hsxt.access.web.aps.services.common.IPubParamService;
import com.gy.hsxt.access.web.aps.services.userpassword.EntjypwdService;
import com.gy.hsxt.access.web.common.bean.HttpRespEnvelope;
import com.gy.hsxt.access.web.common.controller.BaseController;
import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.access.web.common.utils.RequestUtil;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.uc.as.bean.common.AsBankAcctInfo;
import com.gy.hsxt.uc.as.bean.ent.AsEntAllInfo;

/**
 * 
 * 
 * @Package: com.gy.hsxt.access.web.aps.controllers.userpassword
 * @ClassName: EntjyPwdController
 * @Description: TODO
 * 
 * @author: lyh
 * @date: 2016-1-25 下午12:24:03
 * @version V3.0
 */
@Controller
@RequestMapping("entjyPwd")
public class EntjyPwdController extends BaseController<EntjypwdService> {

	@Resource
	private EntjypwdService entjypwdService;

	@Resource
	private IPubParamService pubParamService;

	@SuppressWarnings("rawtypes")
	protected IBaseService getEntityService() {
		return entjypwdService;
	}

	/**
	 * 查询分页
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/listEntJypwdinfopage" }, method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope listEntJyPwdInfoPage(HttpServletRequest request,
			HttpServletResponse response) {
		HttpRespEnvelope hre = null;
		try {
			// Token验证
			super.checkSecureToken(request);
			// 分页查询
			request.setAttribute("serviceName", entjypwdService);
			request.setAttribute("methodName", "findScrollResult");
			hre = super.doList(request, response);
		} catch (HsException e) {
			hre = new HttpRespEnvelope(e);
		}
		return hre;
	}

	/**
	 * 根据 企业互生号 查询企业的所有信息
	 * 
	 * @param customId
	 *            :企业客户ID
	 * @param request
	 * @return
	 */
	@RequestMapping(value = { "/findAllByResNo" }, method = {
			RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public HttpRespEnvelope findAllByResNo(String resNo,
			HttpServletRequest request) {
		HttpRespEnvelope hre = null;

		try {
			// 验证安全令牌
			verifySecureToken(request);

			// 非空验证
			RequestUtil.verifyParamsIsNotEmpty(new Object[] { resNo,
					RespCode.APS_INVOICE_RES_NO_EMPTY.getCode(),
					RespCode.APS_INVOICE_RES_NO_EMPTY.getDesc() });

			// 查询企业客户号
			String custId = entjypwdService.findEntCustIdByEntResNo(resNo);

			// 查询企业的所有信息
			AsEntAllInfo asEntAllInfo = entjypwdService.findAllByCustId(custId);

			hre = new HttpRespEnvelope(asEntAllInfo);
		} catch (HsException e) {
			hre = new HttpRespEnvelope(e);
		}

		return hre;
	}

	/**
	 * 根据 custId 查询企业的基本信息
	 * 
	 * @param customId
	 *            :企业客户ID
	 * @param request
	 * @return
	 */
	/*
	 * @RequestMapping(value = { "/findBaseByCustId" }, method = {
	 * RequestMethod.GET, RequestMethod.POST }, produces =
	 * "application/json;charset=UTF-8")
	 * 
	 * @ResponseBody public HttpRespEnvelope findBaseByCustId(String customId,
	 * HttpServletRequest request) { HttpRespEnvelope hre = null;
	 * 
	 * try { // 验证安全令牌 verifySecureToken(request); // 非空验证
	 * RequestUtil.verifyParamsIsNotEmpty(new Object[] { customId,
	 * RespCode.APS_TAXRATECHANGE_CUST_ID_EMPTY.getCode(),
	 * RespCode.APS_TAXRATECHANGE_CUST_ID_EMPTY.getDesc() });
	 * 
	 * // 查询企业的基本信息 AsEntBaseInfo asEntBaseInfo = entjypwdService
	 * .findBaseByCustId(customId);
	 * 
	 * hre = new HttpRespEnvelope(asEntBaseInfo); } catch (HsException e) { hre
	 * = new HttpRespEnvelope(e); }
	 * 
	 * return hre; }
	 */

	/**
	 * 根据 custId 查询企业的重要信息
	 * 
	 * @param customId
	 *            :企业客户ID
	 * @param request
	 * @return
	 */
	/*
	 * @RequestMapping(value = { "/findMainByCustId" }, method = {
	 * RequestMethod.GET, RequestMethod.POST }, produces =
	 * "application/json;charset=UTF-8")
	 * 
	 * @ResponseBody public HttpRespEnvelope findMainByCustId(String customId,
	 * HttpServletRequest request) { HttpRespEnvelope hre = null;
	 * 
	 * try { // 验证安全令牌 verifySecureToken(request); // 非空验证
	 * RequestUtil.verifyParamsIsNotEmpty(new Object[] { customId,
	 * RespCode.APS_TAXRATECHANGE_CUST_ID_EMPTY.getCode(),
	 * RespCode.APS_TAXRATECHANGE_CUST_ID_EMPTY.getDesc() });
	 * 
	 * // 查询企业的重要信息 AsEntMainInfo asEntMainInfo = entjypwdService
	 * .findMainByCustId(customId);
	 * 
	 * hre = new HttpRespEnvelope(asEntMainInfo); } catch (HsException e) { hre
	 * = new HttpRespEnvelope(e); }
	 * 
	 * return hre; }
	 */
	/**
	 * 根据 企业互生号 查询默认银行账户
	 * 
	 * @param customId
	 *            :企业客户ID
	 * @param request
	 * @return
	 */
	@RequestMapping(value = { "/findDefaultBankByEntResNo" }, method = {
			RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public HttpRespEnvelope findDefaultBankByEntResNo(String resNo,
			HttpServletRequest request) {
		HttpRespEnvelope hre = null;

		try {
			// 验证安全令牌
			verifySecureToken(request);
			// 非空验证
			RequestUtil.verifyParamsIsNotEmpty(new Object[] { resNo,
					RespCode.APS_TAXRATECHANGE_CUST_ID_EMPTY.getCode(),
					RespCode.APS_TAXRATECHANGE_CUST_ID_EMPTY.getDesc() });

			// 查询企业客户号
			String custId = entjypwdService.findEntCustIdByEntResNo(resNo);
			// 查询默认银行账户
			List<AsBankAcctInfo> asBankAcctInfo_list = entjypwdService
					.searchListBankAcc(custId);
			hre = new HttpRespEnvelope(asBankAcctInfo_list);
		} catch (HsException e) {
			hre = new HttpRespEnvelope(e);
		}

		return hre;
	}

	/**
	 * 通过企业互生号查询企业客户号
	 * 
	 * @param resNo
	 *            :企业互生号
	 * @param request
	 * @return
	 */
	/*
	 * @RequestMapping(value = { "/findEntCustIdByEntResNo" }, method = {
	 * RequestMethod.GET, RequestMethod.POST }, produces =
	 * "application/json;charset=UTF-8")
	 * 
	 * @ResponseBody public HttpRespEnvelope findEntCustIdByEntResNo(String
	 * resNo, HttpServletRequest request) { HttpRespEnvelope hre = null;
	 * 
	 * try { // 验证安全令牌 verifySecureToken(request); // 非空验证
	 * RequestUtil.verifyParamsIsNotEmpty(new Object[] { resNo,
	 * RespCode.APS_INVOICE_RES_NO_EMPTY.getCode(),
	 * RespCode.APS_INVOICE_RES_NO_EMPTY.getDesc() });
	 * 
	 * // 查询企业客户号 String custId =
	 * entjypwdService.findEntCustIdByEntResNo(resNo); hre = new
	 * HttpRespEnvelope(custId); } catch (HsException e) { hre = new
	 * HttpRespEnvelope(e); }
	 * 
	 * return hre; }
	 */

	/**
	 * 查询平台互生号
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = { "/getPlatResNo" }, method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public HttpRespEnvelope getPlatResNo(HttpServletRequest request) {
		HttpRespEnvelope hre = null;

		try {
			// 验证安全令牌
			verifySecureToken(request);

			// 查询平台互生号
			String platResNo = pubParamService.findSystemInfo().getPlatResNo();

			hre = new HttpRespEnvelope(platResNo);
		} catch (HsException e) {
			hre = new HttpRespEnvelope(e);
		}

		return hre;
	}

}
