package com.gy.hsxt.access.web.company.controllers.systemBusiness;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gy.hsxt.access.web.bean.CompanyBase;
import com.gy.hsxt.access.web.common.bean.HttpRespEnvelope;
import com.gy.hsxt.access.web.common.constant.ASRespCode;
import com.gy.hsxt.access.web.common.controller.BaseController;
import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.access.web.common.utils.RequestUtil;
import com.gy.hsxt.access.web.company.services.systemBusiness.ICompanyInforService;
import com.gy.hsxt.access.web.company.services.systemBusiness.IReceiveInvoiceService;
import com.gy.hsxt.bs.bean.invoice.Invoice;
import com.gy.hsxt.bs.bean.invoice.InvoiceCust;
import com.gy.hsxt.bs.bean.invoice.InvoiceList;
import com.gy.hsxt.bs.bean.invoice.InvoicePlat;
import com.gy.hsxt.bs.bean.invoice.InvoiceSum;
import com.gy.hsxt.bs.common.enumtype.invoice.InvoiceMaker;
import com.gy.hsxt.bs.common.enumtype.invoice.InvoiceType;
import com.gy.hsxt.bs.common.enumtype.invoice.PostWay;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.uc.as.bean.common.AsBankAcctInfo;
import com.gy.hsxt.uc.as.bean.ent.AsEntExtendInfo;
import com.gy.hsxt.uc.as.bean.ent.AsEntMainInfo;

/**
 * 
 * @projectName : hsxt-access-web-company
 * @package : com.gy.hsxt.access.web.company.controllers.systemBusiness
 * @className : InvoiceController.java
 * @description : 发票管理
 * @author : chenli
 * @createDate : 2015-12-18
 * @updateUser :
 * @updateDate :
 * @updateRemark :
 * @version : v0.0.1
 */
@Controller
@RequestMapping("invoice")
public class InvoiceController extends BaseController<IReceiveInvoiceService> {
	@Resource
	private IReceiveInvoiceService service;
	@Resource
	private ICompanyInforService service2;

	/**
	 * 获取一条发票信息
	 * 
	 * @param invoiceId
	 *            :发票编号ID
	 * @param invoiceMaker
	 *            ：开票方
	 * @param request
	 * @return
	 */
	@RequestMapping(value = { "/findById" }, method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public HttpRespEnvelope findById(String invoiceId, String invoiceMaker,
			HttpServletRequest request) {
		HttpRespEnvelope hre = null;

		try {
			// 验证安全令牌
			verifySecureToken(request);
			// 非空验证
			RequestUtil.verifyParamsIsNotEmpty(new Object[] { invoiceId,
					RespCode.APS_INVOICE_INVOICE_ID_EMPTY.getCode(),
					RespCode.APS_INVOICE_INVOICE_ID_EMPTY.getDesc() });
			InvoiceMaker maker = InvoiceMaker.PLAT;
			if (invoiceMaker != null && invoiceMaker.equals("1")) {
				maker = InvoiceMaker.CUST;
			}
			Invoice invoice = service.queryInvoiceDetail(invoiceId, maker);
			hre = new HttpRespEnvelope(invoice);
		} catch (HsException e) {
			hre = new HttpRespEnvelope(e);
		}

		return hre;
	}

	/**
	 * 查询发票统计总数
	 * 
	 * @param entCustId
	 *            :企业客户号
	 * @param request
	 * @return
	 */
	@RequestMapping(value = { "/queryInvoiceSum" }, method = {
			RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public HttpRespEnvelope queryInvoiceSum(String entCustId,
			HttpServletRequest request) {
		HttpRespEnvelope hre = null;

		try {
			// 验证安全令牌
			verifySecureToken(request);
			int invoiceMaker = Integer.parseInt(request
					.getParameter("invoiceMaker"));
			// 非空验证
			RequestUtil.verifyParamsIsNotEmpty(new Object[] { entCustId,
					RespCode.APS_TAXRATECHANGE_CUST_ID_EMPTY.getCode(),
					RespCode.APS_TAXRATECHANGE_CUST_ID_EMPTY.getDesc() });

			InvoiceSum invoiceSum = service.queryInvoiceSum(entCustId,
					invoiceMaker);
			hre = new HttpRespEnvelope(invoiceSum);
		} catch (HsException e) {
			hre = new HttpRespEnvelope(e);
		}

		return hre;
	}

	/**
	 * 客户开发票
	 * 
	 * @param invoiceCust
	 *            :客户发票bean
	 * @param entCustId
	 *            :企业客户号
	 * @param custName
	 *            :操作员账号
	 * @param entResNo
	 *            :企业互生号
	 * @param entName
	 *            :企业名称
	 * @param request
	 * @return
	 */
	@RequestMapping(value = { "/custOpenInvoice" }, method = {
			RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public HttpRespEnvelope custOpenInvoice(InvoiceCust invoiceCust,
			String entCustId, String custName, String pointNo,
			String custEntName, HttpServletRequest request) {
		HttpRespEnvelope hre = null;
		String operName = request.getParameter("operName");//操作员名称
		try {
			// 验证安全令牌
			verifySecureToken(request);
			String invoiceAmountArray = (String) request
					.getParameter("invoiceAmounts");
			String invoiceCodesArray = (String) request
					.getParameter("invoiceCodes");
			String invoiceNosArray = (String) request
					.getParameter("invoiceNos");
			String ztsArray = (String) request.getParameter("zts");
			// 非空验证
			RequestUtil.verifyParamsIsNotEmpty(new Object[] { entCustId,
					RespCode.APS_TAXRATECHANGE_CUST_ID_EMPTY.getCode(),
					RespCode.APS_TAXRATECHANGE_CUST_ID_EMPTY.getDesc() },
					new Object[] { invoiceAmountArray,
							RespCode.APS_INVOICE_LIST_NULL_EMPTY.getCode(),
							RespCode.APS_INVOICE_LIST_NULL_EMPTY.getDesc() },
					new Object[] { invoiceCodesArray,
							RespCode.APS_INVOICE_LIST_NULL_EMPTY.getCode(),
							RespCode.APS_INVOICE_LIST_NULL_EMPTY.getDesc() },
					new Object[] { invoiceNosArray,
							RespCode.APS_INVOICE_LIST_NULL_EMPTY.getCode(),
							RespCode.APS_INVOICE_LIST_NULL_EMPTY.getDesc() },
					new Object[] { ztsArray,
							RespCode.APS_INVOICE_LIST_NULL_EMPTY.getCode(),
							RespCode.APS_INVOICE_LIST_NULL_EMPTY.getDesc() }
				);

			List<InvoiceList> invoiceList = new ArrayList<InvoiceList>();

			String[] invoiceAmounts = ((String) request
					.getParameter("invoiceAmounts")).split(",");
			String[] invoiceCodes = ((String) request
					.getParameter("invoiceCodes")).split(",");
			String[] invoiceNos = ((String) request.getParameter("invoiceNos"))
					.split(",");
			String[] zts = ((String) request.getParameter("zts")).split(",");
			for (int i = 0; i < invoiceAmounts.length; i++) {
				String zt = zts[i];
				if (zt != null && !"0".equals(zt)) {
					InvoiceList invoices = new InvoiceList();
					invoices.setInvoiceAmount(invoiceAmounts[i]);
					invoices.setInvoiceCode(invoiceCodes[i]);
					invoices.setInvoiceNo(invoiceNos[i]);
					invoiceList.add(invoices);
				}
			}
			if (invoiceList == null || invoiceList.size() == 0) {
				throw new HsException(RespCode.APS_INVOICE_LIST_NULL_EMPTY);
			}
			invoiceCust.setInvoiceLists(invoiceList);
			invoiceCust.setCustId(entCustId);
			invoiceCust.setResNo(pointNo);
			invoiceCust.setCustName(custEntName);
			Integer invoiceType=invoiceCust.getInvoiceType();
			if(invoiceType==null){
				invoiceCust.setInvoiceType(InvoiceType.SPECIAL.ordinal());
			}
			// 经办人
			invoiceCust.setOpenOperator(operName);
			service.custOpenInvoice(invoiceCust);
			hre = new HttpRespEnvelope();
		} catch (HsException e) {
			hre = new HttpRespEnvelope(e);
		}

		return hre;
	}

	/**
	 * 客户申请发票
	 * 
	 * @param invoicePlat
	 *            :平台发票bean
	 * @param entCustId
	 *            :企业客户号
	 * @param custName
	 *            :操作员名
	 * @param entResNo
	 *            :企业互生号
	 * @param custEntName
	 *            :企业名称
	 * @param request
	 * @return
	 */
	@RequestMapping(value = { "/custApplyInvoice" }, method = {
			RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public HttpRespEnvelope custApplyInvoice(InvoicePlat invoicePlat,
			String entCustId, String custEntName, String pointNo,String custName,
			HttpServletRequest request, CompanyBase companyBase) {
		HttpRespEnvelope hre = null;

		try {
			// 验证安全令牌
			verifySecureToken(request);
			// 非空验证
			RequestUtil
					.verifyParamsIsNotEmpty(
							new Object[] {
									entCustId,
									RespCode.APS_TAXRATECHANGE_CUST_ID_EMPTY
											.getCode(),
									RespCode.APS_TAXRATECHANGE_CUST_ID_EMPTY
											.getDesc() },
							new Object[] {
									pointNo,
									RespCode.APS_INVOICE_RES_NO_EMPTY.getCode(),
									RespCode.APS_INVOICE_RES_NO_EMPTY.getDesc() },
							new Object[] {
									custEntName,
									RespCode.APS_INVOICE_CUST_NAME_EMPTY
											.getCode(),
									RespCode.APS_INVOICE_CUST_NAME_EMPTY
											.getDesc() },
							new Object[] {
									invoicePlat.getOpenContent(),
									RespCode.APS_INVOICE_OPEN_CONTENT_EMPTY
											.getCode(),
									RespCode.APS_INVOICE_OPEN_CONTENT_EMPTY
											.getDesc() }
					);
			
			Integer invoiceType=invoicePlat.getInvoiceType();
			if(invoiceType==null){
				invoicePlat.setInvoiceType(InvoiceType.SPECIAL.ordinal());
			}
			invoicePlat.setApplyOperator(companyBase.getOperName());
			invoicePlat.setCustId(entCustId);
			invoicePlat.setResNo(pointNo);
			invoicePlat.setCustName(custEntName);
			service.custApplyInvoice(invoicePlat);
			hre = new HttpRespEnvelope();
		} catch (HsException e) {
			hre = new HttpRespEnvelope(e);
		}

		return hre;
	}
	/**
	 * 根据 resNo 查询客户信息和默认银行账户
	 * 
	 * @param companyResNo
	 *            :企业entResNo
	 * @param request
	 * @return
	 */
	@RequestMapping(value = { "/findMainInfoDefaultBankByResNo" }, method = {
			RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public HttpRespEnvelope findMainInfoDefaultBankByCustId(
			String companyResNo, String pointNo, HttpServletRequest request) {
		HttpRespEnvelope hre = null;

		try {
			// 验证安全令牌
			verifySecureToken(request);
			if (companyResNo == null) {
				companyResNo = pointNo;
			}

			AsEntMainInfo asEntMainInfo = service2.findMainByResNo(companyResNo);
			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("mainInfo", asEntMainInfo);
			if (asEntMainInfo != null) {
				AsBankAcctInfo asBankAcctInfo = service2
						.searchDefaultBankAcc(asEntMainInfo.getEntCustId());
				resultMap.put("bankInfo", asBankAcctInfo);
				resultMap.put("baseInfo", service2.findBaseInfoCustId(asEntMainInfo.getEntCustId()));
			}
			hre = new HttpRespEnvelope(resultMap);
		} catch (HsException e) {
			hre = new HttpRespEnvelope(e);
		}

		return hre;
	}
	
	/**
     * 根据 custId 查询默认银行账户
     * 
     * @param companyResNo
     *            :企业entResNo
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = { "/findDefaultBankByResNo" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope findDefaultBankByResNo(String companyResNo, String entResNo, HttpServletRequest request) {
        try{
            // 验证安全令牌
            verifySecureToken(request);
            //获取企业互生号
            if (companyResNo == null) {
            	companyResNo = entResNo;
            }
            // 企业信息
            AsEntMainInfo asEntMainInfo = service2.findMainByResNo(companyResNo);
            if (asEntMainInfo != null){
            	return new HttpRespEnvelope(service2.searchDefaultBankAcc(asEntMainInfo.getEntCustId()));
            }
            return new HttpRespEnvelope();
        } catch (HsException e){
        	return new HttpRespEnvelope(e);
        }
    }
    
	/**
	 * 发票修改配送方式
	 * 
	 * @param invoicePlat
	 *            :平台发票Bean
	 * @param request
	 * @return
	 */
	@RequestMapping(value = { "/changePostWay" }, method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public HttpRespEnvelope changePostWay(InvoiceCust invoiceCust,String custName,
			HttpServletRequest request) {
		HttpRespEnvelope hre = null;

		try {
			// 验证安全令牌
			verifySecureToken(request);
			// 非空验证
			RequestUtil.verifyParamsIsNotEmpty(new Object[] {
					invoiceCust.getInvoiceId(),
					ASRespCode.APS_INVOICE_INVOICE_ID_EMPTY.getCode(),
					ASRespCode.APS_INVOICE_INVOICE_ID_EMPTY.getDesc() });
			invoiceCust.setUpdatedBy(custName);
			// 配送方式
			Integer postWay = invoiceCust.getPostWay();
			if (postWay == null) {
				invoiceCust.setPostWay(PostWay.OTHER.ordinal());
			} else if (postWay == PostWay.EXPRESS.ordinal()) {
				RequestUtil.verifyParamsIsNotEmpty(
						new Object[] {
								invoiceCust.getExpressName(),
								ASRespCode.APS_INVOICE_EXPRESS_NAME_EMPTY
										.getCode(),
								ASRespCode.APS_INVOICE_EXPRESS_NAME_EMPTY
										.getDesc() },
						new Object[] {
								invoiceCust.getTrackingNo(),
								ASRespCode.APS_INVOICE_TRANKING_NO_EMPTY
										.getCode(),
								ASRespCode.APS_INVOICE_TRANKING_NO_EMPTY
										.getDesc() });
			}
			service.changePostWay(invoiceCust);
			hre = new HttpRespEnvelope();
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
