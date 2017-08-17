package com.gy.hsxt.access.web.scs.services.businessService.imp;

import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gy.hsxt.access.web.common.service.BaseServiceImpl;
import com.gy.hsxt.access.web.scs.services.businessService.IReceiveInvoiceService;
import com.gy.hsxt.bs.api.invoice.IBSInvoiceService;
import com.gy.hsxt.bs.bean.invoice.Invoice;
import com.gy.hsxt.bs.bean.invoice.InvoiceCust;
import com.gy.hsxt.bs.bean.invoice.InvoicePlat;
import com.gy.hsxt.bs.bean.invoice.InvoicePoolQuery;
import com.gy.hsxt.bs.bean.invoice.InvoiceQuery;
import com.gy.hsxt.bs.bean.invoice.InvoiceSum;
import com.gy.hsxt.bs.common.enumtype.invoice.InvoiceMaker;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;

/**
 * 
 * @projectName : hsxt-access-web-company
 * @package : com.gy.hsxt.access.web.company.services.systemBusiness.imp
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
			// 开票时间--起始时间
			String startDate = (String) filterMap.get("startDate");
			if (!StringUtils.isEmpty(startDate) && !"null".equals(startDate)) {
				invoiceQuery.setStartDate((String) filterMap.get("startDate"));
			}

			// 开票时间--结束时间
			String endDate = (String) filterMap.get("endDate");
			if (!StringUtils.isEmpty(endDate) && !"null".equals(endDate)) {
				invoiceQuery.setEndDate((String) filterMap.get("endDate"));
			}
			// 企业ID
			String entCustId = (String) filterMap.get("entCustId");
			if (!StringUtils.isEmpty(entCustId) && !"null".equals(entCustId)) {
				invoiceQuery.setCustId((String) filterMap.get("entCustId"));
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
	 * 客户开发票
	 * 
	 * @param invoiceCust
	 *            客户发票
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
	 * 查询发票统计总数
	 * 
	 * @param entcustId
	 *            企业客户ID
	 * @param invoiceMaker
	 *            开票方
	 * @return sum
	 * @throws HsException
	 */
	@Override
	public InvoiceSum queryInvoiceSum(String entcustId, int invoiceMaker)
			throws HsException {
		InvoicePoolQuery invoicePoolQuery = new InvoicePoolQuery();
		invoicePoolQuery.setCustId(entcustId);
		invoicePoolQuery.setInvoiceMaker(invoiceMaker);
		return BSInvoiceService.queryInvoiceSum(invoicePoolQuery);
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
	 * 发票修改配送方式
	 * 
	 * @param invoicePlat
	 *            平台发票
	 * @return boolean
	 * @throws HsException
	 */
	@Override
	public boolean changePostWay(InvoiceCust invoiceCust) throws HsException {
		return BSInvoiceService.changePostWay(invoiceCust);
	}
}
