/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */

package com.gy.hsxt.rp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gy.hsxt.rp.bean.PaymentManagementOrder;

/**
 * 收管管理业务订单mapper
 * 
 * @Package: com.gy.hsxt.rp.mapper
 * @ClassName: PaymentManagementOrderMapper
 * @Description:
 * 
 * @author: kongsl
 * @date: 2016-3-1 下午6:39:03
 * @version V3.0.0
 */
public interface PaymentManagementOrderMapper {

    /**
     * 查询收款管理订单列表
     * 
     * @param paymentManagementOrder
     *            收款管理订单对象查询条件
     * @return List<PaymentManagementOrder> 收款管理订单数据集合
     */
    public List<PaymentManagementOrder> findPaymentOrderListPage(PaymentManagementOrder paymentManagementOrder);

    /**
     * 查询导出收款管理订单列表
     * 
     * @param paymentManagementOrder
     *            收款管理订单对象查询条件
     * @return List<PaymentManagementOrder> 收款管理订单数据集合
     */
    public List<PaymentManagementOrder> findExportPaymentOrderList(PaymentManagementOrder paymentManagementOrder);

    /**
     * 查询订单详情
     * 
     * @param orderNo
     *            订单编号
     * @return 订单对象
     */
    public PaymentManagementOrder findOrderInfo(@Param("orderNo") String orderNo);

}
