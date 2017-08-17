/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.access.web.aps.services.debit.imp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gy.hsi.common.utils.StringUtils;
import com.gy.hsxt.access.web.aps.services.debit.IDebitLinkService;
import com.gy.hsxt.access.web.common.service.BaseServiceImpl;
import com.gy.hsxt.bs.api.order.IBSOrderService;
import com.gy.hsxt.bs.api.tempacct.IBSTempAcctService;
import com.gy.hsxt.bs.api.tool.IBSToolOrderService;
import com.gy.hsxt.bs.bean.order.Order;
import com.gy.hsxt.bs.bean.tempacct.Debit;
import com.gy.hsxt.bs.bean.tempacct.TempAcctLink;
import com.gy.hsxt.bs.bean.tempacct.TempAcctLinkDebit;
import com.gy.hsxt.bs.bean.tempacct.TempOrderQuery;
import com.gy.hsxt.bs.bean.tool.OrderBean;
import com.gy.hsxt.bs.common.enumtype.order.OrderStatus;
import com.gy.hsxt.bs.common.enumtype.tempacct.LinkStatus;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;

/**
 * 
 * @projectName : hsxt-access-web-aps
 * @package : com.gy.hsxt.access.web.aps.services.debit.imp
 * @className : DebitLinkService.java
 * @description : 临账关联服务类
 * @author : chenli
 * @createDate : 2015-12-18
 * @updateUser :
 * @updateDate :
 * @updateRemark :
 * @version : v0.0.1
 */
@Service("debitLink")
public class DebitLinkService extends BaseServiceImpl<DebitLinkService>
		implements IDebitLinkService {

	@Autowired
	private IBSTempAcctService BSTempDebitService;// 临帐管理 接口类

	@Autowired
	private IBSOrderService BSOrderService;
	@Autowired
	private IBSToolOrderService toolOrderService;

	/**
	 * 查询所有未付款的订单记录
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
	@Override
	public PageData<Order> findScrollResult(Map filterMap, Map sortMap,
			Page page) throws HsException {
		TempOrderQuery tempOrderQuery = new TempOrderQuery();

		// 企业名称
		String companyName = (String) filterMap.get("companyName");
		if (!StringUtils.isEmpty(companyName)
				&& !companyName.equals("undefined")) {
			tempOrderQuery.setCustName(companyName);
		}

		// 订单开始日期
		String startDate = (String) filterMap.get("startDate");
		if (!StringUtils.isEmpty(startDate) && !startDate.equals("undefined")) {
			tempOrderQuery.setStartDate(startDate);
		}
		// 订单结束日期
		String endDate = (String) filterMap.get("endDate");
		if (!StringUtils.isEmpty(endDate) && !endDate.equals("undefined")) {
			tempOrderQuery.setEndDate(endDate);
		}
		// 订单类型
		String orderType = (String) filterMap.get("orderType");
		if (!StringUtils.isEmpty(orderType) && !orderType.equals("undefined")) {
			tempOrderQuery.setOrderType(orderType);
		}
		// 互生号
		String hsResNo = (String) filterMap.get("hsResNo");
		if (!StringUtils.isEmpty(hsResNo) && !hsResNo.equals("undefined")) {
			tempOrderQuery.setHsResNo(hsResNo);
		}
		tempOrderQuery.setOrderStatus(OrderStatus.WAIT_PAY.getCode());
		return BSTempDebitService.queryTempOrderListPage(page, tempOrderQuery);
	}

	/**
	 * 创建临帐关联申请
	 * 
	 * @param tempAcctLink
	 *            临帐关联Bean 无异常便成功，Exception失败
	 * @throws HsException
	 */
	@Override
	public void createTempAcctLink(TempAcctLink tempAcctLink)
			throws HsException {
		BSTempDebitService.createTempAcctLink(tempAcctLink);
	}

	/**
	 * 分页查询临帐关联未复核的业务订单
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
	@Override
	public PageData<Order> queryTempOrderListPage(Map filterMap, Map sortMap,
			Page page) throws HsException {
		TempOrderQuery orderQueryParam = new TempOrderQuery();
		// 企业名称
		String companyName = (String) filterMap.get("companyName");
		if (!StringUtils.isEmpty(companyName)
				&& !companyName.equals("undefined")) {
			orderQueryParam.setCustName(companyName);
		}

		// 订单开始日期
		String startDate = (String) filterMap.get("startDate");
		if (!StringUtils.isEmpty(startDate) && !startDate.equals("undefined")) {
			orderQueryParam.setStartDate(startDate);
		}
		// 订单结束日期
		String endDate = (String) filterMap.get("endDate");
		if (!StringUtils.isEmpty(endDate) && !endDate.equals("undefined")) {
			orderQueryParam.setEndDate(endDate);
		}
		// 订单类型
		String orderType = (String) filterMap.get("orderType");
		if (!StringUtils.isEmpty(orderType) && !orderType.equals("undefined")) {
			orderQueryParam.setOrderType(orderType);
		}
		// 互生号
		String hsResNo = (String) filterMap.get("hsResNo");
		if (!StringUtils.isEmpty(hsResNo) && !hsResNo.equals("undefined")) {
			orderQueryParam.setHsResNo(hsResNo);
		}
		// 操作员客户号
		String custId = (String) filterMap.get("custId");
		if (!StringUtils.isEmpty(custId) && !custId.equals("undefined")) {
			orderQueryParam.setOptCustId(custId);
		}
		return BSTempDebitService.queryTempTaskListPage(page, orderQueryParam);
	}

	/**
	 * 根据订单编号查询对应的临账关联申请
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
	public PageData<Debit> queryLinkDebitByOrderNo(Map filterMap, Map sortMap,
			Page page) throws HsException {
		TempAcctLink debitLink = BSTempDebitService
				.queryTempAcctLinkByOrderNo((String) filterMap.get("orderNo"));
		List<Debit> debits = new ArrayList<Debit>();
		for (TempAcctLinkDebit tempAcctLinkDebit : debitLink
				.getTempAcctLinkDebits()) {
			Debit debit=tempAcctLinkDebit.getDebit();
			debit.setLinkAmount(tempAcctLinkDebit.getLinkAmount());
			debit.setAuditRecord(debitLink.getReqRemark());
			debits.add(tempAcctLinkDebit.getDebit());
		}
		return new PageData<Debit>(debits.size(), debits);

	}

	/**
	 * 根据临账编号查询对应关联申请订单
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
	public PageData<Order> queryTempOrderByDebitId(Map filterMap, Map sortMap,
			Page page) throws HsException {
		List<Order> orders = BSTempDebitService
				.queryTempOrderByDebitId((String) filterMap.get("debitId"));
		return new PageData<Order>(orders.size(), orders);

	}

	/**
	 * 根据订单编号查询对应的临账关联申请
	 * 
	 * @param orderNo
	 *            订单号
	 * 
	 *            无异常便成功，Exception失败
	 * @throws HsException
	 */
	public TempAcctLink queryLinkDebitByOrderNo(String orderNo)
			throws HsException {

		return BSTempDebitService.queryTempAcctLinkByOrderNo(orderNo);

	}

	/**
	 * 复核临帐关联申请
	 * 
	 * @param tempAcctLink
	 *            临帐关联Bean
	 * 
	 *            无异常便成功，Exception失败
	 * @throws HsException
	 */
	@Override
	public void apprTempAcctLink(TempAcctLink tempAcctLink) throws HsException {
		BSTempDebitService.apprTempAcctLink(tempAcctLink);
	}

	/**
	 * 根据订单号码查询订单详情
	 * 
	 * @param orderNo
	 *            订单号码
	 * 
	 *            无异常便成功，Exception失败
	 * @throws HsException
	 */
	@Override
	public OrderBean findOrderByNo(String orderNo) throws HsException {
		return toolOrderService.queryOrderDetailByNo(orderNo);
	}

	/**
	 * 查询单个临账的所有关联申请信息
	 */
	public List<TempAcctLink> queryTempAcctLinkListByDebitId(String debitId)
			throws HsException {
		return BSTempDebitService.queryTempAcctLinkListByDebitId(debitId);
	}

	/**
	 * 查询单个临账关联信息(待关联、已关联、审批信息)
	 * @param debitId
	 * @return
	 * @throws HsException 
	 * @see com.gy.hsxt.access.web.aps.services.debit.IDebitLinkService#queryDebitLinkInfo(java.lang.String)
	 */
    @Override
    public Map<String, Object> queryDebitLinkInfo(String debitId) throws HsException {
        Map<String, Object> retMap = new HashMap<String, Object>();
        
        List<Order> cognateOrder = new ArrayList<Order>();              //已关联订单
        List<Order> pendingOrder = new ArrayList<Order>();              //待审批关联订单
        List<TempAcctLink> apprInfo = new ArrayList<TempAcctLink>();    //审批信息
        
        //查询单个临账的所有关联申请信息
        List<TempAcctLink> talList = BSTempDebitService.queryTempAcctLinkListByDebitId(debitId);
        
        //循环过滤
        for (TempAcctLink tal : talList)
        {
            if(tal.getStatus() == LinkStatus.PENDING.ordinal()){        //待关联(待关联订单)            
                pendingOrder.add(tal.getOrder());
            }else if (tal.getStatus() == LinkStatus.PASS.ordinal()){    //复核通过(已关联订单)  
                cognateOrder.add(tal.getOrder());
                apprInfo.add(tal);
            }
            
            if (tal.getStatus() == LinkStatus.REJECT.ordinal()){  //复核驳回
                apprInfo.add(tal);
            }
        }
        
        retMap.put("cognateOrder", cognateOrder);
        retMap.put("pendingOrder", pendingOrder);
        retMap.put("apprInfo", apprInfo);
        return retMap;
    }
}
