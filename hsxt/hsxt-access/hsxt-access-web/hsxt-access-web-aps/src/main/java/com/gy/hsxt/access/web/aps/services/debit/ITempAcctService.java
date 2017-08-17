/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.access.web.aps.services.debit;

import java.util.List;
import java.util.Map;
import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.bs.bean.tempacct.Debit;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;

/**
 * 
 * @projectName : hsxt-access-web-aps
 * @package : com.gy.hsxt.access.web.aps.services.debit
 * @className : ITempAcctService.java
 * @description : 临管理服务接口类
 * @author : chenli
 * @createDate : 2015-12-18
 * @updateUser :
 * @updateDate :
 * @updateRemark :
 * @version : v0.0.1
 */
public interface ITempAcctService extends IBaseService {

	/**
	 * 创建 临帐记录
	 * 
	 * @param debit
	 *            实体类 非null
	 *            <p/>
	 *            无异常便成功，Exception失败
	 * @throws HsException
	 */
	void createDebit(Debit debit) throws HsException;

	/**
	 * 修改临帐记录
	 * 
	 * @param debit
	 *            实体类 非null
	 *            <p/>
	 *            无异常便成功，Exception失败
	 * @throws HsException
	 */
	public void updateDebit(Debit debit) throws HsException;

	/**
	 * 获取 某一条临帐记录
	 * 
	 * @param debitId
	 *            临账记录编号 非空
	 * @return 返回实体类
	 * @throws HsException
	 */
	Debit findbyId(String debitId) throws HsException;

	/**
	 * 审核复核 临帐记录
	 * 
	 * @param debit
	 *            临账记录 非null
	 * @throws HsException
	 *             无异常便成功
	 */
	void approveDebit(Debit debit) throws HsException;

	/**
	 * 转不动款 临帐记录
	 * 
	 * @param debit
	 *            临账记录 非null
	 * @param dblOptCustId
	 *            双签操作员客户号 非空
	 *            <p/>
	 *            无异常便成功，Exception失败
	 * @throws HsException
	 */
	void turnNotMovePayment(Debit debit, String dblOptCustId)
			throws HsException;

	/**
	 * 统计不动款的总金额
	 * 
	 * @return 返回不动款的总金额
	 * @throws HsException
	 */
	String notMovePaymentSum() throws HsException;

	/**
	 * 分页查询临账录入复核/退款复核列表
	 * 
	 * @param filterMap
	 *            查询条件map
	 * 
	 * @param sortMap
	 *            排序map
	 * 
	 * @param page
	 *            分页 无异常便成功，Exception失败
	 * @throws HsException
	 */
	public PageData<Debit> queryDebitTaskListPage(Map filterMap, Map sortMap,
			Page paramPage) throws HsException;

	/**
	 * 根据拟使用企业互生号查询待关联临账记录
	 * 
	 * @param useEntCustName
	 *            拟使用企业互生号
	 * @throws HsException
	 */
	public PageData<Debit> queryDebitListByQuery(Map filterMap, Map sortMap,
			Page paramPage) throws HsException;
}
