package com.gy.hsxt.access.web.aps.services.invoice;

import java.util.Map;
import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.bs.bean.invoice.Invoice;
import com.gy.hsxt.bs.bean.invoice.InvoiceCust;
import com.gy.hsxt.bs.bean.invoice.InvoicePlat;
import com.gy.hsxt.bs.bean.invoice.InvoicePool;
import com.gy.hsxt.bs.common.enumtype.invoice.InvoiceMaker;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;

/**
 * 
 * @projectName : hsxt-access-web-aps
 * @package : com.gy.hsxt.access.web.aps.services.invoice
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
	 * 分页查询发票池数据列表
	 * 
	 * @param page
	 *            分页对象
	 * @param invoicePoolQuery
	 *            查询对象
	 * @return 发票池的分页数据
	 * @throws HsException
	 */
	PageData<InvoicePool> queryInvoicePoolListForPage(Map filterMap,
			Map sortMap, Page page) throws HsException;

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
	 *            发票
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
	 * 平台审批发票
	 * 
	 * @param invoicePlat
	 *            平台发票
	 * @return boolean
	 * @throws HsException
	 */
	boolean platApproveInvoice(InvoicePlat invoicePlat) throws HsException;

	/**
	 * 平台开发票
	 * 
	 * @param invoicePlat
	 *            平台发票
	 * @return boolean
	 * @throws HsException
	 */
	boolean platOpenInvoice(InvoicePlat invoicePlat) throws HsException;

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
	boolean signInvoice(Invoice invoice, InvoiceMaker invoiceMaker)
			throws HsException;
	
	/**
	 * 平台发票修改配送方式
	 * 
	 * @param invoicePlat
	 *            平台发票
	 * @return boolean
	 * @throws HsException
	 */
	 public boolean changePostWay(InvoicePlat invoicePlat) throws HsException;

}
