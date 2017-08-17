package com.gy.hsxt.access.web.aps.services.invoice.imp;

import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gy.hsxt.access.web.aps.services.invoice.IReceiveInvoiceService;
import com.gy.hsxt.access.web.common.service.BaseServiceImpl;
import com.gy.hsxt.bs.api.invoice.IBSInvoiceService;
import com.gy.hsxt.bs.bean.invoice.Invoice;
import com.gy.hsxt.bs.bean.invoice.InvoiceCust;
import com.gy.hsxt.bs.bean.invoice.InvoicePlat;
import com.gy.hsxt.bs.bean.invoice.InvoicePool;
import com.gy.hsxt.bs.bean.invoice.InvoicePoolQuery;
import com.gy.hsxt.bs.bean.invoice.InvoiceQuery;
import com.gy.hsxt.bs.common.enumtype.invoice.BizType;
import com.gy.hsxt.bs.common.enumtype.invoice.InvoiceMaker;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;

/**
 * 
 * @projectName : hsxt-access-web-aps
 * @package : com.gy.hsxt.access.web.aps.services.invoice.imp
 * @className : ReceiveInvoiceService.java
 * @description : 发票管理
 * @author : chenli
 * @createDate : 2015-12-18
 * @updateUser :
 * @updateDate :
 * @updateRemark :
 * @version : v0.0.1
 */
@Service("receiveInvoiceService")
public class ReceiveInvoiceService extends
		BaseServiceImpl<ReceiveInvoiceService> implements
		IReceiveInvoiceService {
	@Autowired
	private IBSInvoiceService BSInvoiceService;

	/**
	 * 分页查询发票列表
	 * 
	 * @param filterMap
	 *            :查询条件map
	 * @param filterMap
	 *            :排序条件map
	 * @param page
	 *            :分页信息
	 * @return 发票的分页数据
	 */
	@Override
	public PageData<Invoice> findScrollResult(Map filterMap, Map sortMap,
			Page page) throws HsException {
		InvoiceQuery invoiceQuery = new InvoiceQuery();
		if (filterMap != null) {
		    
		    //根据查询开票日期
		    invoiceQuery.setPlatQuery(true);
		    
			// 开票时间--起始时间
			String startDate = (String) filterMap.get("startDate");
			if (!StringUtils.isEmpty(startDate) && !"null".equals(startDate)) {
				invoiceQuery.setStartDate(startDate);
			}

			// 开票时间--结束时间
			String endDate = (String) filterMap.get("endDate");
			if (!StringUtils.isEmpty(endDate) && !"null".equals(endDate)) {
				invoiceQuery.setEndDate(endDate);
			}
			// 企业名称
			String companyName = (String) filterMap.get("companyName");
			if (!StringUtils.isEmpty(companyName) && !"null".equals(companyName)) {
				invoiceQuery.setCustName(companyName);
			}
			// 企业互生号
			String resNo = (String) filterMap.get("resNo");
			if (!StringUtils.isEmpty(resNo)) {
				invoiceQuery.setResNo(resNo);
			}
			// 状态
			String status = (String) filterMap.get("status");
			if (!StringUtils.isEmpty(status)) {
				invoiceQuery.setStatus(Integer.parseInt(status));
			}
			// 业务类型
			String bizType = (String) filterMap.get("bizType");
			if (!StringUtils.isEmpty(bizType)) {
				invoiceQuery.setBizType(bizType);
			}
			// 开票方
			String invoiceMaker = (String) filterMap.get("invoiceMaker");
			if (!StringUtils.isEmpty(invoiceMaker)) {
				invoiceQuery.setInvoiceMaker(Integer.parseInt(invoiceMaker));
			}
			String queryType = (String) filterMap.get("queryType");
			if (!StringUtils.isEmpty(queryType)) {
				invoiceQuery.setQueryType(Integer.parseInt(queryType));
			}
		}
		return BSInvoiceService.queryInvoiceListForPage(page, invoiceQuery);
	}

	/**
	 * 分页查询发票池数据列表
	 * 
	 * @param filterMap
	 *            :查询条件map
	 * @param filterMap
	 *            :排序条件map
	 * @param page
	 *            :分页信息
	 * @return 发票池的分页数据
	 */
	@Override
	public PageData<InvoicePool> queryInvoicePoolListForPage(Map filterMap,
			Map sortMap, Page page) throws HsException {
		InvoicePoolQuery invoicePoolQuery = new InvoicePoolQuery();
		if (filterMap != null) {
			// 企业名称
			String companyName = (String) filterMap.get("companyName");
			if (!StringUtils.isEmpty(companyName)
					&& !"null".equals(companyName)) {
				invoicePoolQuery.setCustName((String) filterMap
						.get("companyName"));
			}
			// 企业互生号
			String resNo = (String) filterMap.get("resNo");
			if (!StringUtils.isEmpty(resNo)) {
				invoicePoolQuery.setResNo(resNo);
			}
			// 状态
			String custType = (String) filterMap.get("custType");
			if (!StringUtils.isEmpty(custType)) {
				invoicePoolQuery.setCustType(Integer.parseInt(custType));
			}
			// 业务类型
			String bizType = (String) filterMap.get("bizType");
			if (!StringUtils.isEmpty(bizType)) {
				invoicePoolQuery.setBizType(bizType);
			}
			// 开票方
			String invoiceMaker = (String) filterMap.get("invoiceMaker");
			if (!StringUtils.isEmpty(invoiceMaker)) {
				invoicePoolQuery
						.setInvoiceMaker(Integer.parseInt(invoiceMaker));
			}
		}
		return BSInvoiceService.queryInvoicePoolListForPage(page,
				invoicePoolQuery);
	}

	/**
	 * 查询发票详情
	 * 
	 * @param invoiceId
	 *            发票ID
	 * @param invoiceMaker
	 *            开票方
	 * @return invoice
	 * @throws HsException
	 */
	@Override
	public Invoice queryInvoiceDetail(String invoiceId,
			InvoiceMaker invoiceMaker) throws HsException {
		return BSInvoiceService.queryInvoiceDetail(invoiceId, invoiceMaker);
	}

	/**
	 * 客户开发票
	 * 
	 * @param invoiceCust
	 *            发票
	 * @return boolean
	 * @throws HsException
	 */
	@Override
	public boolean custOpenInvoice(InvoiceCust invoiceCust) throws HsException {
		return BSInvoiceService.custOpenInvoice(invoiceCust);

	}

	/**
	 * 客户向平台申请发票
	 * 
	 * @param invoicePlat
	 *            平台发票
	 * @return boolean
	 * @throws HsException
	 */
	@Override
	public boolean custApplyInvoice(InvoicePlat invoicePlat) throws HsException {
		return BSInvoiceService.custApplyInvoice(invoicePlat);
	}

	/**
	 * 平台审批发票
	 * 
	 * @param invoicePlat
	 *            平台发票
	 * @return boolean
	 * @throws HsException
	 */
	@Override
	public boolean platApproveInvoice(InvoicePlat invoicePlat)
			throws HsException {
		return BSInvoiceService.platApproveInvoice(invoicePlat);
	}

	/**
	 * 平台开发票
	 * 
	 * @param invoicePlat
	 *            平台发票
	 * @return boolean
	 * @throws HsException
	 */
	@Override
	public boolean platOpenInvoice(InvoicePlat invoicePlat) throws HsException {
		return BSInvoiceService.platOpenInvoice(invoicePlat);
	}

	/**
	 * 发票签收/拒签
	 * 
	 * @param invoice
	 *            发票
	 * @param invoiceMaker
	 *            开票方
	 * @return boolean
	 * @throws HsException
	 */
	@Override
	public boolean signInvoice(Invoice invoice, InvoiceMaker invoiceMaker)
			throws HsException {
		return BSInvoiceService.signInvoice(invoice, invoiceMaker);
	}

	/**
	 * 平台发票修改配送方式
	 * 
	 * @param invoicePlat
	 *            平台发票
	 * @return boolean
	 * @throws HsException
	 */
	@Override
	public boolean changePostWay(InvoicePlat invoicePlat) throws HsException {
		return BSInvoiceService.changePostWay(invoicePlat);
	}
}
