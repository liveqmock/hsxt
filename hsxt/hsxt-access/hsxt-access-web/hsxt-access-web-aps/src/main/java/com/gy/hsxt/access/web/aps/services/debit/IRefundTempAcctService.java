/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.access.web.aps.services.debit;

import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.bs.bean.tempacct.TempAcctRefund;
import com.gy.hsxt.common.exception.HsException;

/**
 * 
 * @projectName : hsxt-access-web-aps
 * @package : com.gy.hsxt.access.web.aps.services.debit
 * @className : IRefundTempAcctService.java
 * @description : 临账退款申请单服务接口类
 * @author : chenli
 * @createDate : 2015-12-18
 * @updateUser :
 * @updateDate :
 * @updateRemark :
 * @version : v0.0.1
 */
@SuppressWarnings("rawtypes")
public interface IRefundTempAcctService extends IBaseService {
	/**
	 * 创建 临账退款申请单
	 * 
	 * @param tempAcctRefund
	 *            实体类 非null
	 *            <p/>
	 *            无异常便成功，Exception失败
	 * @throws HsException
	 */
	void createTempAcctRefund(TempAcctRefund tempAcctRefund) throws HsException;

	/**
	 * 审批 临账退款申请单
	 * 
	 * @param tempAcctRefund
	 *            退款申请单
	 *            <p/>
	 *            无异常便成功，Exception失败
	 * @throws HsException
	 */
	void apprTempAcctRefund(TempAcctRefund tempAcctRefund) throws HsException;

}
