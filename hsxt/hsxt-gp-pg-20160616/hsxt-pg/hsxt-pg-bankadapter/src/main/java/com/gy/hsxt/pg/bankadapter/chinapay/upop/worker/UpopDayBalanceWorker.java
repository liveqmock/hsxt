/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.pg.bankadapter.chinapay.upop.worker;

import com.gy.hsxt.pg.bankadapter.chinapay.IChinapayBalanceCallback;
import com.gy.hsxt.pg.bankadapter.chinapay.b2c.worker.B2cDayBalanceWorker;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-pg-bankadapter
 * 
 *  Package Name    : com.gy.hsxt.pg.bankadapter.chinapay.upop.worker
 * 
 *  File Name       : DayBalanceWorker.java
 * 
 *  Creation Date   : 2015年9月25日
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 快捷支付每日对账, 因为对账字段与B2C完全一致, 所以直接继承B2C的工作者类
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class UpopDayBalanceWorker extends B2cDayBalanceWorker {
	
	public UpopDayBalanceWorker() {
	}

	/**
	 * 对账文件解析
	 * 
	 * @param localSavePath
	 *            文件保存路径
	 * @param callback
	 *            处理过程回调
	 * @return
	 * @throws Exception
	 */
	@Override
	public boolean doParsingBalanceFile(String localSavePath,
			IChinapayBalanceCallback callback) throws Exception {
		return super.doParsingBalanceFile(localSavePath, callback);
	}
}
