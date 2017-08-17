/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.pg.bankadapter.chinapay;

import java.util.List;

/***************************************************************************
 * <PRE>
 *  Project Name    : bankadapter
 * 
 *  Package Name    : com.gy.hsxt.pg.bankadapter.chinapay
 * 
 *  File Name       : IChinapayBalanceCallback.java
 * 
 *  Creation Date   : 2015-01-22
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 中国银联对账的回调接口
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/

public interface IChinapayBalanceCallback {

	/**
	 * 通知订单状态
	 * 
	 * @param pageDatas
	 * @param currPage
	 */
	public void notifyPageData(List<ChinapayOrderResultDTO> pageDatas,
			int currPage) throws Exception;

	/**
	 * 通知已经完成解析, 告知总行数、总页数
	 * 
	 * @param totalRows
	 * @param totalPages
	 */
	public void notifyFinished(int totalRows, int totalPages) throws Exception;

	/**
	 * 判断是否为当前回调关心的数据
	 * 
	 * @param orderItem
	 * @return
	 */
	public boolean isMyFocus(ChinapayOrderResultDTO orderItem);

	/**
	 * 每页数据行数
	 * 
	 * @return
	 */
	public int getRowsPerPage();
}
