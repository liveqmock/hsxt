package com.gy.hsxt.access.web.aps.controllers.invoice;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gy.hsxt.access.web.aps.controllers.invoice.bean.TaxrateChangeCompanyInfor;
import com.gy.hsxt.access.web.aps.services.invoice.ICompanyInforService;
import com.gy.hsxt.access.web.aps.services.invoice.ITaxrateChangeService;
import com.gy.hsxt.access.web.common.bean.HttpRespEnvelope;
import com.gy.hsxt.access.web.common.controller.BaseController;
import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.access.web.common.utils.RequestUtil;
import com.gy.hsxt.bs.bean.tax.TaxrateChange;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.uc.as.bean.ent.AsEntMainInfo;
/**
 * 
 * @projectName : hsxt-access-web-aps
 * @package : com.gy.hsxt.access.web.aps.controllers.invoice
 * @className : TaxrateChangeControler.java
 * @description : 税率调整申请审批
 * @author : chenli
 * @createDate : 2016-01-04
 * @updateUser :
 * @updateDate :
 * @updateRemark :
 * @version : v0.0.1
 */
@Controller
@RequestMapping("taxrateChange")
public class TaxrateChangeControler extends BaseController {
	@Resource
	private ITaxrateChangeService service;

	@Resource
	private ICompanyInforService service2;
	/**
	 * 获取一条税率调整信息
	 * 
	 * @param applyId
	 *            :税率调整申请ID
	 * @param request
	 * @return
	 */
	/** 获取一条税率调整信息 */
	@RequestMapping(value = { "/findById" }, method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public HttpRespEnvelope findById(String applyId, HttpServletRequest request) {
		HttpRespEnvelope hre = null;

		try {
			// 验证安全令牌
			verifySecureToken(request);
			// 非空验证
			RequestUtil.verifyParamsIsNotEmpty(new Object[] { applyId,
					RespCode.APS_TAXRATECHANGE_APPLY_ID_EMPTY.getCode(),
					RespCode.APS_TAXRATECHANGE_APPLY_ID_EMPTY.getDesc() });

			TaxrateChange taxrateChange = service.findById(applyId);

			String custId = taxrateChange.getCustId();

			RequestUtil.verifyParamsIsNotEmpty(new Object[] { custId,
					RespCode.APS_TAXRATECHANGE_CUST_ID_EMPTY.getCode(),
					RespCode.APS_TAXRATECHANGE_CUST_ID_EMPTY.getDesc() });

			TaxrateChangeCompanyInfor tc = new TaxrateChangeCompanyInfor();
			BeanUtils.copyProperties(taxrateChange, tc);

			AsEntMainInfo asEntMainInfo = service2.findMainByCustId(custId);

			tc.setContactAddr(asEntMainInfo.getEntRegAddr());// 企业地址
			tc.setTaxNo(asEntMainInfo.getTaxNo());
			tc.setCreName(asEntMainInfo.getCreName());

			hre = new HttpRespEnvelope(tc);
		} catch (HsException e) {
			hre = new HttpRespEnvelope(e);
		}

		return hre;
	}
	/**
	 * 审批/复核税率修改申请 
	 * 
	 * @param taxrateChange
	 *            :税率调整申请Bean
	 * @param request
	 * @return
	 */
	/** 审批/复核税率修改申请 */
	@RequestMapping(value = { "/approve" }, method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public HttpRespEnvelope update(TaxrateChange taxrateChange,String custName,
			String customId, HttpServletRequest request) {
		HttpRespEnvelope hre = null;

		try {
			// 验证安全令牌
			verifySecureToken(request);
			// 非空验证
			RequestUtil
					.verifyParamsIsNotEmpty(
							new Object[] {
									taxrateChange.getApplyId(),
									RespCode.APS_TAXRATECHANGE_APPLY_ID_EMPTY
											.getCode(),
									RespCode.APS_TAXRATECHANGE_APPLY_ID_EMPTY
											.getDesc() },
							new Object[] {
									taxrateChange.getStatus(),
									RespCode.APS_TAXRATECHANGE_STATUS_EMPTY
											.getCode(),
									RespCode.APS_TAXRATECHANGE_STATUS_EMPTY
											.getDesc() },
							new Object[] {
									taxrateChange.getApplyReason(),
									RespCode.APS_TAXRATECHANGE_APPLEY_REASON_EMPTY
											.getCode(),
									RespCode.APS_TAXRATECHANGE_APPLEY_REASON_EMPTY
											.getDesc() },
							new Object[] {
									taxrateChange.getCustId(),
									RespCode.APS_TAXRATECHANGE_UPDATE_BY_EMPTY
											.getCode(),
									RespCode.APS_TAXRATECHANGE_UPDATE_BY_EMPTY
											.getDesc() },
							new Object[] {
									customId,
									RespCode.APS_TAXRATECHANGE_CUST_ID_EMPTY
											.getCode(),
									RespCode.APS_TAXRATECHANGE_CUST_ID_EMPTY
											.getDesc() });
			// 设置操作员
			taxrateChange.setUpdatedBy(taxrateChange.getCustId());
			taxrateChange.setCustId(customId);
			taxrateChange.setUpdatedName(custName);
			service.approve(taxrateChange);

			hre = new HttpRespEnvelope();
		} catch (HsException e) {
			hre = new HttpRespEnvelope(e);
		}

		return hre;
	}
	/**
	 * 分页查询企业税率调整审批/复核列表
	 * 
	 * @param request
	 *            :
	 * @param response
	 *            ：
	 * @return
	 */
	@RequestMapping(value = { "/approveTaxrateChangeList" }, method = {
			RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public HttpRespEnvelope invoicePoolList(HttpServletRequest request,
			HttpServletResponse response) {
		HttpRespEnvelope hre = null;

		try {
			// 验证安全令牌
			verifySecureToken(request);

			request.setAttribute("serviceName", service);
			request.setAttribute("methodName", "approveTaxrateChangeList");
			return super.doList(request, response);
		} catch (HsException e) {
			hre = new HttpRespEnvelope(e);
		}

		return hre;
	}
	@Override
	protected IBaseService getEntityService() {
		return service;
	}

}
