package com.gy.hsxt.access.web.aps.services.receivingManage;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.rp.bean.PaymentManagementOrder;

/**
 * 
 * @projectName   : hsxt-access-web-aps
 * @package       : com.gy.hsxt.access.web.aps.services.receivingManage
 * @className     : IPaymentOrderService.java
 * @description   : 网银收款对账
 * @author        : maocy
 * @createDate    : 2016-03-03
 * @updateUser    : 
 * @updateDate    : 
 * @updateRemark  : 
 * @version       : v0.0.1
 */
@SuppressWarnings("rawtypes")
@Service
public interface IPaymentOrderService extends IBaseService {
	
    /**
     * 
     * 方法名称：收款管理数据对帐
     * 方法描述：网银收款对账-收款管理数据对帐
     * @param orderNos 订单编号列表
     * @throws HsException
     */
	public List<PaymentManagementOrder> dataReconciliation(List<String> orderNos) throws HsException;
	
	/**
     * 
     * 方法名称：收款管理订单详情
     * 方法描述：网银收款对账-收款管理订单详情
     * @param orderNo 订单编号
     * @throws HsException
     */
	public PaymentManagementOrder findPaymentOrderInfo(String orderNo) throws HsException;
	
	/**
     * 方法名称：收款管理订单列表
     * 方法描述：网银收款对账-收款管理订单列表
     * @param filterMap
     * @param sortMap
     * @param page
     * @return
     * @throws HsException
     */
	public PageData<?> findScrollResult(Map filterMap, Map sortMap, Page page) throws HsException;
    
}
