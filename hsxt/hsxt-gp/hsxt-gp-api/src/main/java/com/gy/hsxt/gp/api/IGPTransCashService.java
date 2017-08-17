/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.gp.api;

import java.util.List;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.gp.bean.TransCash;
import com.gy.hsxt.gp.bean.TransCashOrderState;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-gp-api
 * 
 *  Package Name    : com.gy.hsxt.gp.api
 * 
 *  File Name       : IGPPaymentService.java
 * 
 *  Creation Date   : 2015-9-28
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 平安银行转账业务：单笔转账、批量转账、转账结果查询
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public interface IGPTransCashService {
	/**
	 * 单笔转账
	 * 
	 * @param merId
	 *            :商户号
	 * @param transCash
	 *            ：
	 * @return 提交成功 or 失败，如果失败会抛出异常
	 * @throws HsException
	 */
	public boolean transCash(String merId, TransCash transCash,
			String srcSubsysId) throws HsException;

	/**
	 * 
	 * 单笔转账结果查询
	 * 
	 * @param merId
	 *            :商户号
	 * @param orderNo
	 *            ：订单号
	 * @return
	 * @throws HsException
	 */
	public TransCashOrderState getTransCashOrderState(String merId,
			String orderNo) throws HsException;

	/**
	 * 批量转账
	 * 
	 * @param merId
	 *            :商户号
	 * @param batchNo
	 *            :转账批次号
	 * @param transCashList
	 *            :平安银行转账bean集合
	 * @return
	 * @throws HsException
	 */
	public boolean batchTransCash(String merId, String batchNo,
			List<TransCash> transCashList, String srcSubsysId)
			throws HsException;

	/**
	 * 根据批次号批量转账结果查询
	 * 
	 * @param merId
	 *            :商户号
	 * @param batchNo
	 *            :转账批次号
	 * @return
	 * @throws HsException
	 */
	public List<TransCashOrderState> getBatchTransCashOrderState(String merId,
			String batchNo) throws HsException;

	/**
	 * 根据订单号批量转账结果查询
	 * 
	 * @param merId
	 *            :商户号
	 * @param orderNoList
	 *            :业务订单号列表，最大只能传递50个业务订单号，主要是基于性能角度考虑
	 * @return
	 * @throws HsException
	 */
	public List<TransCashOrderState> getBatchTransCashOrderStates(String merId,
			List<String> orderNoList) throws HsException;

	/**
	 * 查询提现手续费接口
	 * 
	 * @param transAmount
	 *            :交易金额
	 * @param inAccBankNode
	 *            :收款人开户银行代码
	 * @param inAccCityCode
	 *            :收款账户开户城市代码
	 * @param sysFlag
	 *            :转账加急标志
	 * @return 转账手续费(精度为6，传递值的精度小于6的自动补足)
	 * @throws HsException
	 */
	public String getBankTransFee(String transAmount, String inAccBankNode,
			String inAccCityCode, String sysFlag) throws HsException;
}
