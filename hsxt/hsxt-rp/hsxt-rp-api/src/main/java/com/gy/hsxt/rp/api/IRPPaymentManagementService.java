/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.rp.api;

import java.util.List;

import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.rp.bean.PaymentManagementOrder;

/**
 * 地区平台收款管理对外接口定义
 * 
 * @Package: com.gy.hsxt.rp.api
 * @ClassName: IRPPaymentManagementService
 * @Description: TODO
 * 
 * @author: kongsl
 * @date: 2016-3-1 下午3:47:27
 * @version V3.0.0
 */
public interface IRPPaymentManagementService {

    /**
     * 查询业务订单列表
     * 
     * @param paymentManagementOrder
     *            收款管理订单实体类
     * @param page
     *            分页信息
     * @return 收款管理订单列表
     * @throws HsException
     */
    public PageData<PaymentManagementOrder> getPaymentOrderList(PaymentManagementOrder paymentManagementOrder, Page page)
            throws HsException;

    /**
     * 数据对帐
     * 
     * @param orderNos
     *            订单号列表
     * @return 对帐成功列表
     * @throws HsException
     */
    public List<PaymentManagementOrder> dataReconciliation(List<String> orderNos) throws HsException;

    /**
     * 查询收款订单详情
     * 
     * @param orderNo
     *            订单编号
     * @return 收款订单详情
     * @throws HsException
     */
    public PaymentManagementOrder getPaymentOrderInfo(String orderNo) throws HsException;

    /**
     * 导出收款管理订单
     * 
     * @param paymentManagementOrder
     *            收款管理订单实体类
     * @return 文件路径
     * @throws HsException
     */
    public String exportPaymentOrderData(PaymentManagementOrder paymentManagementOrder) throws HsException;

}
