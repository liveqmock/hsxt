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
import com.gy.hsxt.bs.bean.order.Order;
import com.gy.hsxt.bs.bean.tempacct.Debit;
import com.gy.hsxt.bs.bean.tempacct.TempAcctLink;
import com.gy.hsxt.bs.bean.tool.OrderBean;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;

/**
 * 
 * @projectName : hsxt-access-web-aps
 * @package : com.gy.hsxt.access.web.aps.services.debit
 * @className : IDebitLinkService.java
 * @description : 临账关联服务接口类
 * @author : chenli
 * @createDate : 2015-12-18
 * @updateUser :
 * @updateDate :
 * @updateRemark :
 * @version : v0.0.1
 */
@SuppressWarnings("rawtypes")
public interface IDebitLinkService extends IBaseService {
	/**
	 * 创建 临帐关联申请
	 * 
	 * @param tempAcctLink
	 *            临帐关联申请 实体类 非null
	 * @throws HsException
	 */
	void createTempAcctLink(TempAcctLink tempAcctLink) throws HsException;

	/**
	 * 分页查询临帐关联未复核的业务订单
	 * 
	 * @param page
	 *            分页对象 非null
	 * @param tempOrderQuery
	 *            条件查询实体
	 * @return 返回分页的数据列表
	 * @throws HsException
	 */
	public PageData<Order> queryTempOrderListPage(Map filterMap, Map sortMap,
			Page page) throws HsException;

	/**
	 * 根据订单编号查询对应的关联申请临账
	 * 
	 * @param filterMap
	 * @param sortMap
	 * @param page
	 * @return
	 */
	PageData<Debit> queryLinkDebitByOrderNo(Map filterMap, Map sortMap,
			Page page);

	/**
	 * 根据订单编号查询对应的关联申请临账
	 * 
	 * @param orderNo
	 *            业务订单号
	 * @return obj
	 * @throws HsException
	 */
	public TempAcctLink queryLinkDebitByOrderNo(String orderNo)
			throws HsException;

	/**
	 * 复核临帐关联申请
	 * 
	 * @param tempAcctLink
	 *            临帐关联申请 实体类 非null
	 * @throws HsException
	 */
	void apprTempAcctLink(TempAcctLink tempAcctLink) throws HsException;

	/**
	 * 根据临账编号查询对应关联的订单
	 * 
	 * @param filterMap
	 * @param sortMap
	 * @param page
	 * @return
	 * @throws HsException
	 */
	public PageData<Order> queryTempOrderByDebitId(Map filterMap, Map sortMap,
			Page page) throws HsException;
	/**
     * 根据订单号码查询订单详情
     *
     * @param  orderNo 订单号码
     * 
     *                    无异常便成功，Exception失败
     * @throws HsException 
     */
	public OrderBean findOrderByNo(String orderNo) throws HsException;
	/**
	 * 查询单个临账的所有关联申请信息
	 */
	public List<TempAcctLink> queryTempAcctLinkListByDebitId(String debitId) throws HsException;
	
	/**
	 * 查询单个临账关联信息(待关联、已关联、审批信息)
	 * @param debitId
	 * @return
	 * @throws HsException
	 */
	public Map<String, Object> queryDebitLinkInfo(String debitId) throws HsException;;
}
