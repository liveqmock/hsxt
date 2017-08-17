package com.gy.hsxt.access.web.aps.services.receivingManage;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsxt.access.web.common.service.BaseServiceImpl;
import com.gy.hsxt.access.web.common.utils.CommonUtils;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.rp.api.IRPPaymentManagementService;
import com.gy.hsxt.rp.bean.PaymentManagementOrder;

/**
 * 
 * @projectName   : hsxt-access-web-aps
 * @package       : com.gy.hsxt.access.web.aps.services.receivingManage
 * @className     : PaymentOrderService.java
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
public class PaymentOrderService extends BaseServiceImpl implements IPaymentOrderService {
	
    @Autowired
    private IRPPaymentManagementService rpService;//服务类
    
    /**
     * 
     * 方法名称：收款管理数据对帐
     * 方法描述：网银收款对账-收款管理数据对帐
     * @param orderNos 订单编号列表
     * @throws HsException
     */
	public List<PaymentManagementOrder> dataReconciliation(List<String> orderNos) throws HsException {
		return this.rpService.dataReconciliation(orderNos);
	}
	
	/**
     * 
     * 方法名称：收款管理订单详情
     * 方法描述：网银收款对账-收款管理订单详情
     * @param orderNo 订单编号
     * @throws HsException
     */
	public PaymentManagementOrder findPaymentOrderInfo(String orderNo) throws HsException {
		return this.rpService.getPaymentOrderInfo(orderNo);
	}
	
	/**
     * 方法名称：收款管理订单列表
     * 方法描述：网银收款对账-收款管理订单列表
     * @param filterMap
     * @param sortMap
     * @param page
     * @return
     * @throws HsException
     */
    public PageData<?> findScrollResult(Map filterMap, Map sortMap, Page page) throws HsException {
    	PaymentManagementOrder params = new PaymentManagementOrder();
    	params.setStartDate((String) filterMap.get("startDate"));
    	params.setEndDate((String) filterMap.get("endDate"));
    	params.setOrderType((String) filterMap.get("orderType"));
    	params.setHsResNo((String) filterMap.get("hsResNo"));
    	params.setCustName((String) filterMap.get("custNameCondition"));
    	params.setPayChannel(CommonUtils.toInteger(filterMap.get("payChannel")));
    	params.setPayStatus(CommonUtils.toInteger(filterMap.get("payStatus")));
    	params.setUnPayStatus((String) filterMap.get("unPayStatus"));
    	params.setOrderChannel(CommonUtils.toInteger(filterMap.get("orderChannel")));
    	//支持多个支付方式   1,2,3
    	params.setCustomWhere((String) filterMap.get("payChannels"));
    	return this.rpService.getPaymentOrderList(params, page);
    }
    
}