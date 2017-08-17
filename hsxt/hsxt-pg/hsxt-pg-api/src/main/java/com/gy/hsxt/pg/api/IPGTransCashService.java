/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.pg.api;

import java.util.List;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.pg.bean.PGTransCash;
import com.gy.hsxt.pg.bean.PGTransCashOrderState;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-gp-service
 * 
 *  Package Name    : com.gy.hsxt.gp.service.out
 * 
 *  File Name       : IPGTransCashService.java
 * 
 *  Creation Date   : 2015-10-10
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 提现接口
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public interface IPGTransCashService {
	/**
	 * 单笔提现接口
	 * 
	 * @param merType
	 * @param transCash
	 * @return
	 * @throws HsException
	 */
	public boolean transCash(int merType, PGTransCash transCash)
			throws HsException;

	/**
	 * 单笔提现结果查询接口
	 * 
	 * @param merType
	 * @param bankOrderNo
	 * @return
	 * @throws HsException
	 */
	public PGTransCashOrderState getTransCashOrderState(int merType,
			String bankOrderNo) throws HsException;

	/**
	 * 批量提现接口
	 * 
	 * @param merType
	 * @param bankBatchNo
	 *            :银行转账批次号（由支付系统生成并传递给银行）
	 * @param transCashList
	 * @return
	 * @throws HsException
	 */
	public boolean batchTransCash(int merType, String bankBatchNo,
			List<PGTransCash> transCashList) throws HsException;

	/**
	 * 批量提现结果查询接口
	 * 
	 * @param merType
	 * @param bankBatchNo
	 * @return
	 * @throws HsException
	 */
	public List<PGTransCashOrderState> getBatchTransCashOrderState(int merType,
			String bankBatchNo) throws HsException;

	/**
	 * 批量提现结果查询接口
	 * 
	 * @param merType
	 * @param bankOrderNoList
	 * @return
	 * @throws HsException
	 */
	public List<PGTransCashOrderState> getBatchTransCashOrderState(int merType,
			List<String> bankOrderNoList) throws HsException;
}
