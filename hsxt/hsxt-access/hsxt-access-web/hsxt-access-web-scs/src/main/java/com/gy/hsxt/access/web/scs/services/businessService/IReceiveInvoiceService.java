package com.gy.hsxt.access.web.scs.services.businessService;

import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.bs.bean.invoice.Invoice;
import com.gy.hsxt.bs.bean.invoice.InvoiceCust;
import com.gy.hsxt.bs.bean.invoice.InvoicePlat;
import com.gy.hsxt.bs.bean.invoice.InvoiceSum;
import com.gy.hsxt.bs.common.enumtype.invoice.InvoiceMaker;
import com.gy.hsxt.common.exception.HsException;

/**
 * 
 * @projectName : hsxt-access-web-scs
 * @package : com.gy.hsxt.access.web.scs.services.businessService
 * @className : IReceiveInvoiceService.java
 * @description : 企业发票管理对外接口类
 * @author : chenli
 * @createDate : 2015-12-18
 * @updateUser :
 * @updateDate :
 * @updateRemark :
 * @version : v0.0.1
 */
public interface IReceiveInvoiceService extends IBaseService {
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
	Invoice queryInvoiceDetail(String invoiceId, InvoiceMaker invoiceMaker)
			throws HsException;

	/**
	 * 客户开发票
	 * 
	 * @param invoiceCust
	 *            客户发票
	 * @return boolean
	 * @throws HsException
	 */
	boolean custOpenInvoice(InvoiceCust invoiceCust) throws HsException;

	/**
	 * 客户向平台申请发票
	 * 
	 * @param invoicePlat
	 *            平台发票
	 * @return boolean
	 * @throws HsException
	 */
	boolean custApplyInvoice(InvoicePlat invoicePlat) throws HsException;

	/**
	 * 查询发票统计总数
	 * 
	 * @param custId
	 *            企业客户ID
	 * @param invoiceMaker
	 *            开票方
	 * @return sum
	 * @throws HsException
	 */
	public InvoiceSum queryInvoiceSum(String custId, int invoiceMaker)
			throws HsException;
	/**
	 * 发票修改配送方式
	 * 
	 * @param invoicePlat
	 *            平台发票
	 * @return boolean
	 * @throws HsException
	 */
	public boolean changePostWay(InvoiceCust invoiceCust) throws HsException;
}
