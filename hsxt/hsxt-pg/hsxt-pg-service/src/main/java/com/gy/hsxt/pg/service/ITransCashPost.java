/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.pg.service;

import java.util.List;

import com.gy.hsxt.pg.mapper.vo.TPgBankTransOrder;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-pg-service
 * 
 *  Package Name    : com.gy.hsxt.pg.service
 * 
 *  File Name       : ITransCashPost.java
 * 
 *  Creation Date   : 2015-10-22
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 线程池中进行转账处理
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public interface ITransCashPost {

	/**
	 * 组装数据，通过线程池异步调用单笔银行接口
	 * 
	 * @param pinganOrder
	 * @return
	 */
	public boolean postTranCash(TPgBankTransOrder pinganOrder);

	/**
	 * 组装数据，通过线程池异步调用批量转账银行接口
	 * 
	 * @param bankBatchNo
	 * @param pinganOrderList
	 * @return
	 */
	public boolean postBatchTransCash(String bankBatchNo,
			List<TPgBankTransOrder> pinganOrderList);

	/**
	 * 发起向银行查询单笔转账结果, 并进行通知GP
	 * 
	 * @param bankOrderNo
	 * @return
	 */
	public boolean qryAndNotifySingleTransfer(String bankOrderNo);

	/**
	 * 发起向银行查询批量转账结果, 并进行通知GP
	 * 
	 * @param bankBatchNo
	 * @return
	 */
	public boolean qryAndNotifyBatchTransfer(String bankBatchNo);

	/**
	 * 从平安银行查询单笔转账结果
	 * 
	 * @param bankOrderNo
	 * @return
	 */
	public TPgBankTransOrder qrySingleTransferFromPA(String bankOrderNo);

	/**
	 * 从平安银行查询批量转账结果
	 * 
	 * @param bankBatchNo
	 * @return
	 */
	public List<TPgBankTransOrder> qryBatchTransferFromPA(String bankBatchNo);
}
