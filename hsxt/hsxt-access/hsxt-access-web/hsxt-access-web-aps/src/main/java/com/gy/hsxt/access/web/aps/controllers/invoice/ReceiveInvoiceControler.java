package com.gy.hsxt.access.web.aps.controllers.invoice;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.gy.hsxt.access.web.aps.services.invoice.ICompanyInforService;
import com.gy.hsxt.access.web.aps.services.invoice.IReceiveInvoiceService;
import com.gy.hsxt.access.web.bean.ApsBase;
import com.gy.hsxt.access.web.common.bean.HttpRespEnvelope;
import com.gy.hsxt.access.web.common.constant.ASRespCode;
import com.gy.hsxt.access.web.common.controller.BaseController;
import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.access.web.common.utils.RequestUtil;
import com.gy.hsxt.bs.bean.invoice.Invoice;
import com.gy.hsxt.bs.bean.invoice.InvoiceCust;
import com.gy.hsxt.bs.bean.invoice.InvoiceList;
import com.gy.hsxt.bs.bean.invoice.InvoicePlat;
import com.gy.hsxt.bs.common.enumtype.invoice.InvoiceMaker;
import com.gy.hsxt.bs.common.enumtype.invoice.InvoiceStatus;
import com.gy.hsxt.bs.common.enumtype.invoice.InvoiceType;
import com.gy.hsxt.bs.common.enumtype.invoice.PostWay;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.uc.as.bean.ent.AsEntMainInfo;

/**
 * 
 * @projectName : hsxt-access-web-aps
 * @package : com.gy.hsxt.access.web.aps.controllers.invoice
 * @className : ReceiveInvoiceControler.java
 * @description : 发票管理
 * @author : chenli
 * @createDate : 2015-12-18
 * @updateUser :
 * @updateDate :
 * @updateRemark :
 * @version : v0.0.1
 */
@Controller
@RequestMapping("receiveInvoice")
public class ReceiveInvoiceControler extends
		BaseController<IReceiveInvoiceService> {
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
	@ResponseBody
    @RequestMapping(value = { "/findById" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope findById(String invoiceId, String invoiceMaker, HttpServletRequest request) {
        HttpRespEnvelope hre = null;
        
        try
        {
            // 验证安全令牌
            verifySecureToken(request);
            
            // 非空验证
            RequestUtil.verifyParamsIsNotEmpty(new Object[] { invoiceId, ASRespCode.APS_INVOICE_INVOICE_ID_EMPTY  });
            
            InvoiceMaker maker = InvoiceMaker.PLAT;
            
            if (invoiceMaker != null && invoiceMaker.equals("1")) {
                maker = InvoiceMaker.CUST;
            }
            Invoice invoice = service.queryInvoiceDetail(invoiceId, maker);
            hre = new HttpRespEnvelope(invoice);
        }
        catch (HsException e)
        {
            hre = new HttpRespEnvelope(e);
        }

        return hre;
    }

    /**
     * 签收/拒签发票
     * 
     * @param invoicePlat
     *            :平台发票Bean
     * @param invoiceMaker
     *            ：开票方
     * @param request
     * @return
     */
	@ResponseBody
    @RequestMapping(value = { "/sign" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope sign(InvoicePlat invoicePlat, String invoiceMaker, String custName, String refuseRemark,
            HttpServletRequest request) {
	    
        HttpRespEnvelope hre = null;    //请求结果
        InvoiceMaker maker = null;      //开票实体
        
        try
        {
            // 验证安全令牌
            verifySecureToken(request);
            
            // 非空验证
            RequestUtil.verifyParamsIsNotEmpty(
                    new Object[] { invoicePlat.getInvoiceId(), ASRespCode.APS_INVOICE_INVOICE_ID_EMPTY}, 
                    new Object[] { invoicePlat.getStatus(), ASRespCode.APS_INVOICE_STATUS_EMPTY },
                    new Object[] { invoiceMaker, ASRespCode.APS_INVOICE_INVOICE_MAKER_EMPTY });
            
            invoicePlat.setUpdatedBy(custName);

            if (invoiceMaker != null && invoiceMaker.equals("1"))
            {
                maker = InvoiceMaker.CUST;
                InvoiceCust invoiceCust = new InvoiceCust();
                invoiceCust.setInvoiceId(invoicePlat.getInvoiceId());
                invoiceCust.setStatus(invoicePlat.getStatus());
                invoiceCust.setUpdatedBy(custName);
                invoiceCust.setRefuseRemark(refuseRemark);
                invoiceCust.setReceiveWay(invoicePlat.getReceiveWay());
                service.signInvoice(invoiceCust, maker);
            }
            else
            {
                maker = InvoiceMaker.PLAT;
                service.signInvoice(invoicePlat, maker);
            }

            hre = new HttpRespEnvelope();
        }
        catch (HsException e)
        {
            hre = new HttpRespEnvelope(e);
        }

        return hre;
    }

    /**
     * 平台发票修改配送方式
     * 
     * @param invoicePlat
     *            :平台发票Bean
     * @param request
     * @return
     */
	@ResponseBody
    @RequestMapping(value = { "/changePostWay" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope changePostWay(InvoicePlat invoicePlat, ApsBase apsBase, HttpServletRequest request) {
	    
        HttpRespEnvelope hre = null;

        try
        {
            // 验证安全令牌
            verifySecureToken(request);
            
            // 非空验证
            RequestUtil.verifyParamsIsNotEmpty(new Object[] { invoicePlat.getInvoiceId(), ASRespCode.APS_INVOICE_INVOICE_ID_EMPTY });
            
            invoicePlat.setUpdatedBy(apsBase.getOperName());    //操作员真实名称
            Integer postWay = invoicePlat.getPostWay();         // 配送方式
            
            if (postWay == null)
            {
                invoicePlat.setPostWay(PostWay.OTHER.ordinal());
            }
            else if (postWay == PostWay.EXPRESS.ordinal())
            {
                RequestUtil.verifyParamsIsNotEmpty(new Object[] { invoicePlat.getExpressName(),
                        ASRespCode.APS_INVOICE_EXPRESS_NAME_EMPTY.getCode(),
                        ASRespCode.APS_INVOICE_EXPRESS_NAME_EMPTY.getDesc() }, new Object[] {
                        invoicePlat.getTrackingNo(), ASRespCode.APS_INVOICE_TRANKING_NO_EMPTY.getCode(),
                        ASRespCode.APS_INVOICE_TRANKING_NO_EMPTY.getDesc() });
            }
            service.changePostWay(invoicePlat);
            hre = new HttpRespEnvelope();
        }
        catch (HsException e)
        {
            hre = new HttpRespEnvelope(e);
        }

        return hre;
    }

    /**
     * 平台开发票
     * 
     * @param invoicePlat
     *            :平台发票Bean
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = { "/create" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope create(InvoicePlat invoicePlat, String custName, String customId, ApsBase apsBase, HttpServletRequest request) {
        HttpRespEnvelope hre = null;

        try
        {
            // 验证安全令牌
            verifySecureToken(request);
            
            String invoiceAmountArray =  request.getParameter("invoiceAmounts");    //发票金额(格式212,232)
            String invoiceCodesArray =  request.getParameter("invoiceCodes");       //发票代码(格式dm2222,dm33333)
            String invoiceNosArray = request.getParameter("invoiceNos");            //发票号码(格式hm2222,hm33333)
            String ztsArray = request.getParameter("zts");
            
            // 非空验证
            RequestUtil.verifyParamsIsNotEmpty(
                    new Object[] { customId, ASRespCode.APS_TAXRATECHANGE_CUST_ID_EMPTY }, 
                    new Object[] { invoicePlat.getResNo(),  ASRespCode.APS_INVOICE_RES_NO_EMPTY },
                   /*    new Object[] { invoicePlat.getAddress(), ASRespCode.APS_INVOICE_ADDRESS_EMPTY }, 
                    new Object[] { invoicePlat.getBankBranchName(), ASRespCode.APS_INVOICE_BANK_BRANCH_NAME_EMPTY }, 
                    new Object[] { invoicePlat.getBankNo(), ASRespCode.APS_INVOICE_BANK_NO_EMPTY }, 
                    new Object[] { invoicePlat.getTelephone(), ASRespCode.APS_INVOICE_TELEPHONE_EMPTY }, 
                    new Object[] { invoicePlat.getTaxpayerNo(), ASRespCode.APS_INVOICE_TAX_NO_EMPTY }, */
                    new Object[] { invoicePlat.getOpenContent(), ASRespCode.APS_INVOICE_OPEN_CONTENT_EMPTY }, 
                    new Object[] { invoicePlat.getBizType(), ASRespCode.APS_INVOICE_BIZ_TYPE_EMPTY }, 
                    new Object[] { invoiceAmountArray, ASRespCode.APS_INVOICE_LIST_NULL_EMPTY }, 
                    new Object[] { invoiceCodesArray, ASRespCode.APS_INVOICE_LIST_NULL_EMPTY }, 
                    new Object[] { invoiceNosArray, ASRespCode.APS_INVOICE_LIST_NULL_EMPTY }, 
                    new Object[] { ztsArray, ASRespCode.APS_INVOICE_LIST_NULL_EMPTY});

           
            invoicePlat.setOpenOperator(apsBase.getOperName());         // 经办人
//            invoicePlat.setInvoiceType(InvoiceType.NORMAL.ordinal());   // 发票类型
            invoicePlat.setCustId(customId);                            // 企业客户号
            invoicePlat.setCustName(invoicePlat.getInvoiceTitle());     // 企业名称
            
            //分割字符串数组
            String[] invoiceAmounts = invoiceAmountArray.split(",");
            String[] invoiceCodes = invoiceCodesArray.split(",");
            String[] invoiceNos = invoiceNosArray.split(",");
            String[] zts = ztsArray.split(",");
            
            //发票总金额
            double allAmount = 0.0;
            List<InvoiceList> invoiceList = new ArrayList<InvoiceList>();
            
            //发票信息累计
            for (int i = 0; i < invoiceAmounts.length; i++)
            {
                String zt = zts[i];
                if (zt != null && !"0".equals(zt))
                {
                    InvoiceList invoices = new InvoiceList();
                    invoices.setInvoiceAmount(invoiceAmounts[i]);
                    invoices.setInvoiceCode(invoiceCodes[i]);
                    invoices.setInvoiceNo(invoiceNos[i]);
                    invoiceList.add(invoices);
                    allAmount += Double.parseDouble(invoiceAmounts[i]);
                }
            }
            if (invoiceList == null || invoiceList.size() < 1) {
                throw new HsException(ASRespCode.APS_INVOICE_LIST_NULL_EMPTY);
            }
            
            invoicePlat.setAllAmount("" + allAmount);
            invoicePlat.setInvoiceLists(invoiceList);
            service.platOpenInvoice(invoicePlat);
            hre = new HttpRespEnvelope();
        }
        catch (HsException e)
        {
            hre = new HttpRespEnvelope(e);
        }

        return hre;
    }

	/**
	 * 平台审批开发票
	 * 
	 * @param invoicePlat
	 *            :平台发票Bean
	 * @param request
	 * @return
	 */
	@RequestMapping(value = { "/platApproveInvoice" }, method = {
			RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public HttpRespEnvelope platApproveInvoice(InvoicePlat invoicePlat,String custName,String custId,
			HttpServletRequest request) {
		HttpRespEnvelope hre = null;

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
			RequestUtil
					.verifyParamsIsNotEmpty(
							new Object[] {
									invoiceAmountArray,
									ASRespCode.APS_INVOICE_LIST_NULL_EMPTY
											.getCode(),
									ASRespCode.APS_INVOICE_LIST_NULL_EMPTY
											.getDesc() },
							new Object[] {
									invoiceCodesArray,
									ASRespCode.APS_INVOICE_LIST_NULL_EMPTY
											.getCode(),
									ASRespCode.APS_INVOICE_LIST_NULL_EMPTY
											.getDesc() },
							new Object[] {
									invoiceNosArray,
									ASRespCode.APS_INVOICE_LIST_NULL_EMPTY
											.getCode(),
									ASRespCode.APS_INVOICE_LIST_NULL_EMPTY
											.getDesc() },
							new Object[] {
									ztsArray,
									ASRespCode.APS_INVOICE_LIST_NULL_EMPTY
											.getCode(),
									ASRespCode.APS_INVOICE_LIST_NULL_EMPTY
											.getDesc() });

			// 经办人
			invoicePlat.setOpenOperator(custName);
			
			List<InvoiceList> invoiceList = new ArrayList<InvoiceList>();

			String[] invoiceAmounts = ((String) request
					.getParameter("invoiceAmounts")).split(",");
			String[] invoiceCodes = ((String) request
					.getParameter("invoiceCodes")).split(",");
			String[] invoiceNos = ((String) request.getParameter("invoiceNos"))
					.split(",");
			String[] zts = ((String) request.getParameter("zts")).split(",");
			double allAmount = 0.0;
			for (int i = 0; i < invoiceAmounts.length; i++) {
				String zt = zts[i];
				if (zt != null && !"0".equals(zt)) {
					InvoiceList invoices = new InvoiceList();
					invoices.setInvoiceAmount(invoiceAmounts[i]);
					invoices.setInvoiceCode(invoiceCodes[i]);
					invoices.setInvoiceNo(invoiceNos[i]);
					invoiceList.add(invoices);
					allAmount += Double.parseDouble(invoiceAmounts[i]);
				}
			}
			//判断金额
			double oldAllAmout = Double.parseDouble(invoicePlat.getAllAmount());
			if (oldAllAmout > allAmount) {
				throw new HsException(
						ASRespCode.APS_INVOICE_AMOUNT_NOT_EQUAL_EMPTY);
			} else if (oldAllAmout < allAmount) {
				throw new HsException(ASRespCode.APS_INVOICE_AMOUNT_LARGE_EMPTY);
			}
			if (invoiceList == null || invoiceList.size() == 0) {
				throw new HsException(ASRespCode.APS_INVOICE_LIST_NULL_EMPTY);
			}
			invoicePlat.setAllAmount("" + allAmount);
			invoicePlat.setInvoiceLists(invoiceList);
			invoicePlat.setUpdatedBy(custId);
			service.platApproveInvoice(invoicePlat);
			hre = new HttpRespEnvelope();
		} catch (HsException e) {
			hre = new HttpRespEnvelope(e);
		}

		return hre;
	}

    /**
     * 平台审批发票驳回
     * 
     * @param invoicePlat
     *            :平台发票bean
     * @param request
     * @return
     */
	@ResponseBody
    @RequestMapping(value = { "/rejection" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope rejection(String invoiceId, String allAmount, String refuseRemark, String custId,
            HttpServletRequest request) {
        HttpRespEnvelope hre = null;

        try
        {
            // 验证安全令牌
            verifySecureToken(request);

            // 非空验证
            RequestUtil.verifyParamsIsNotEmpty(
                    new Object[] { invoiceId, ASRespCode.APS_INVOICE_INVOICE_ID_EMPTY },
                    new Object[] { allAmount, ASRespCode.APS_INVOICE_ALL_AMOUNT_EMPTY }, 
                    new Object[] { refuseRemark, ASRespCode.APS_INVOICE_APPLEY_REASON_EMPTY });

            InvoicePlat invoice = new InvoicePlat();
            invoice.setInvoiceId(invoiceId);                        // 发票编号
            invoice.setAllAmount(allAmount);                        // 发票申请金额
            invoice.setRefuseRemark(refuseRemark);                  // 驳回意见
            invoice.setUpdatedBy(custId);                           // 操作者
            invoice.setStatus(InvoiceStatus.REJECTED.ordinal());    // 发票状态

            service.platApproveInvoice(invoice);
            hre = new HttpRespEnvelope();
        }
        catch (HsException e)
        {
            hre = new HttpRespEnvelope(e);
        }

        return hre;
    }

	/**
	 * 发票池数据列表
	 * 
	 * @param request
	 *            :
	 * @param response
	 *            ：
	 * @return
	 */
	@RequestMapping(value = { "/invoicePoolList" }, method = {
			RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public HttpRespEnvelope invoicePoolList(HttpServletRequest request,
			HttpServletResponse response) {
		HttpRespEnvelope hre = null;

		try {
			// 验证安全令牌
			verifySecureToken(request);

			request.setAttribute("serviceName", service);
			request.setAttribute("methodName", "queryInvoicePoolListForPage");
			return super.doList(request, response);
		} catch (HsException e) {
			hre = new HttpRespEnvelope(e);
		}

		return hre;
	}

    /**
     * 客户开发票
     * 
     * @param resNo
     *            :企业互生号
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = { "/custOpenInvoice" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope custOpenInvoice(String billingEnt, InvoiceCust invoiceCust, ApsBase apsBase, HttpServletRequest request) {
        HttpRespEnvelope hre = null;

        try
        {
            // 验证安全令牌
            verifySecureToken(request);
            
            //发票信息
            String invoiceAmountArray = request.getParameter("invoiceAmounts");
            String invoiceCodesArray = request.getParameter("invoiceCodes");
            String invoiceNosArray = request.getParameter("invoiceNos");
            String ztsArray = request.getParameter("zts");
            
            // 非空验证
            RequestUtil.verifyParamsIsNotEmpty(
                    new Object[] { billingEnt, ASRespCode.APS_INVOICE_RES_NO_EMPTY },
                    new Object[] { invoiceAmountArray, ASRespCode.APS_INVOICE_LIST_NULL_EMPTY }, 
                    new Object[] { invoiceCodesArray, ASRespCode.APS_INVOICE_LIST_NULL_EMPTY }, 
                    new Object[] { invoiceNosArray, ASRespCode.APS_INVOICE_LIST_NULL_EMPTY }, 
                    new Object[] { ztsArray, ASRespCode.APS_INVOICE_LIST_NULL_EMPTY });
            
            //获取企业详情
            AsEntMainInfo mainInfor = service2.findMainByResNo(billingEnt);
            invoiceCust.setResNo(billingEnt);                       //互生号
            invoiceCust.setCustId(mainInfor.getEntCustId());        //企业客户号
            invoiceCust.setCustName(mainInfor.getEntName());        //企业名称
            //invoiceCust.setOpenOperator(apsBase.getOperName());     // 经办人
            invoiceCust.setInvoiceTitle(apsBase.getCustEntName());  //发票抬头      
            
            List<InvoiceList> invoiceList = new ArrayList<InvoiceList>();

            String[] invoiceAmounts = invoiceAmountArray.split(",");
            String[] invoiceCodes = invoiceCodesArray.split(",");
            String[] invoiceNos = invoiceNosArray.split(",");
            String[] zts = ztsArray.split(",");
            
            double allAmount = 0.0;
            for (int i = 0; i < invoiceAmounts.length; i++)
            {
                String zt = zts[i];
                if (zt != null && !"0".equals(zt))
                {
                    InvoiceList invoices = new InvoiceList();
                    invoices.setInvoiceAmount(invoiceAmounts[i]);
                    invoices.setInvoiceCode(invoiceCodes[i]);
                    invoices.setInvoiceNo(invoiceNos[i]);
                    invoiceList.add(invoices);
                    allAmount += Double.parseDouble(invoiceAmounts[i]);
                }
            }
            if (invoiceList == null || invoiceList.size() == 0) {
                throw new HsException(ASRespCode.APS_INVOICE_LIST_NULL_EMPTY);
            }
            
            invoiceCust.setAllAmount("" + allAmount);
            invoiceCust.setInvoiceLists(invoiceList);
            service.custOpenInvoice(invoiceCust);
            hre = new HttpRespEnvelope();
        }
        catch (HsException e)
        {
            hre = new HttpRespEnvelope(e);
        }

        return hre;
    }

	@Override
	protected IBaseService getEntityService() {
		return service;
	}
}
