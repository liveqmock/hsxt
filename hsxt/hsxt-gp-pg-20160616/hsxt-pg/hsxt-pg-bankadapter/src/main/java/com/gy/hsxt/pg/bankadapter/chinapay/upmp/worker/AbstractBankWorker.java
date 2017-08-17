/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.pg.bankadapter.chinapay.upmp.worker;

import java.util.Date;

import org.apache.log4j.Logger;

import com.gy.hsxt.pg.bankadapter.chinapay.upmp.ChinapayUpmpFacade;
import com.gy.hsxt.pg.bankadapter.common.utils.DateUtils;
import com.gy.hsxt.pg.bankadapter.common.utils.StringHelper;

/***************************************************************************
 * <PRE>
 *  Project Name    : bankadapter
 * 
 *  Package Name    : com.gy.hsxt.pg.bankadapter.chinapay.upmp.worker
 * 
 *  File Name       : AbstractBankWorker.java
 * 
 *  Creation Date   : 2014-11-21
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : AbstractBankWorker
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public abstract class AbstractBankWorker {
	protected final Logger logger = Logger.getLogger(this.getClass());

	protected ChinapayUpmpFacade chinapayUpmpFacade;

	/** 商户号 **/
	protected String mechantNo;

	/** 密钥 **/
	protected String privateKey;

	public AbstractBankWorker() {
	}

	/**
	 * 初始化数据
	 * 
	 * @param facade
	 */
	public void initFacade(ChinapayUpmpFacade facade) {
		this.chinapayUpmpFacade = facade;

		this.mechantNo = facade.getMechantNo();
		this.privateKey = facade.getPrivateKey();
	}
	
	/**
	 * 矫正日期, 将时间替换为0, 最终日期格式：yyyyMMddHHmmss
	 * 
	 * @param orderDate
	 * @return
	 */
	protected String resetDateTime2Zero(Date orderDate) {
		String strOrderDate = null;

		if (!StringHelper.isEmpty(orderDate)) {
			strOrderDate = DateUtils.format2yyyyMMddDate(orderDate);

			return StringHelper.rightPad(strOrderDate, 14, '0');
		}

		return "";
	}
}
