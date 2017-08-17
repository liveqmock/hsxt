/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.access.web.aps.services.debit.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gy.hsxt.access.web.aps.services.debit.IRefundTempAcctService;
import com.gy.hsxt.access.web.common.service.BaseServiceImpl;
import com.gy.hsxt.bs.api.tempacct.IBSTempAcctService;
import com.gy.hsxt.bs.bean.tempacct.TempAcctRefund;
import com.gy.hsxt.common.exception.HsException;

/**
 * 
 * @projectName : hsxt-access-web-aps
 * @package : com.gy.hsxt.access.web.aps.services.debit.imp
 * @className : RefundTempAcctService.java
 * @description : 临账退款服务类
 * @author : chenli
 * @createDate : 2015-12-18
 * @updateUser :
 * @updateDate :
 * @updateRemark :
 * @version : v0.0.1
 */
@Service("refundTempAcctService")
public class RefundTempAcctService extends
		BaseServiceImpl<RefundTempAcctService> implements
		IRefundTempAcctService {
	@Autowired
	private IBSTempAcctService BStempAcctService;// 临帐管理 接口类

	/**
	 * 创建临账退款申请
	 * 
	 * @param tempAcctRefund
	 *            临账退款Bean
	 * 
	 *             无异常便成功，Exception失败
	 * @throws HsException
	 */
	@Override
	public void createTempAcctRefund(TempAcctRefund tempAcctRefund)
			throws HsException {
		BStempAcctService.createTempAcctRefund(tempAcctRefund);
	}

	/**
	 * 复核临账退款申请
	 * 
	 * @param tempAcctRefund
	 *            临账退款Bean
	 * 
	 *             无异常便成功，Exception失败
	 * @throws HsException
	 */
	@Override
	public void apprTempAcctRefund(TempAcctRefund tempAcctRefund)
			throws HsException {
		BStempAcctService.apprTempAcctRefund(tempAcctRefund);
	}
}
