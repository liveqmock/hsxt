/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.bs.order.interfaces;

import java.util.List;

import com.gy.hsxt.bs.bean.order.Order;
import com.gy.hsxt.bs.bean.order.OrderQueryParam;
import com.gy.hsxt.bs.common.bean.PayUrl;
import com.gy.hsxt.common.exception.HsException;

/**
 * 通用业务订单接口定义
 * 
 * @Package: com.gy.hsxt.bs.order.interfaces
 * @ClassName: IOrderService
 * @Description: TODO
 * 
 * @author: kongsl
 * @date: 2015-10-21 下午6:41:50
 * @version V3.0.0
 */
public interface IOrderService {

    /**
     * 保存通用订单
     * 
     * @param order
     *            订单信息
     * @return 订单号
     * @throws HsException
     */
    public String saveCommonOrder(Order order) throws HsException;

    /**
     * 更新资源费订单
     * 
     * @param orderNo
     *            订单编号
     * @throws HsException
     */
    public void updateResFeeOrder(String orderNo) throws HsException;

    /**
     * 根据订单号查询一条订单记录
     * 
     * @param orderNo
     *            订单号
     * @return 订单
     * @throws HsException
     */
    public Order getOrderByNo(String orderNo) throws HsException;

    /**
     * 获取网银支付地址
     * 
     * @param payUrl
     *            非空订单支付方式，枚举类：PayChannel 订单数据 支付成功后跳转页面 签约号：快捷支付时需要
     *            短信验证码：快捷支付时需要
     * @return 成功返回true，false或异常为失败
     * @throws HsException
     */
    public String getPayUrl(PayUrl payUrl) throws HsException;

    /**
     * 更新订单状态
     * 
     * @param orderNo
     *            订单号
     * @param orderStatus
     *            订单状态码
     * @throws HsException
     */
    public void updateOrderStatus(String orderNo, Integer orderStatus) throws HsException;

    /**
     * 更新订单所有状态
     * 
     * @param order
     *            订单信息
     * @throws HsException
     */
    public void updateOrderAllStatus(Order order) throws HsException;

    /**
     * 查询支付系统对账数据
     * 
     * @param queryParam
     *            查询实体
     * @return list
     * @throws HsException
     */
    List<Order> queryListForGPByQuery(OrderQueryParam queryParam) throws HsException;

    /**
     * 获取支付订单
     * 
     * @param orderNo
     *            订单编号
     * @return 订单实体
     * @throws HsException
     */
    public Order findUnPayOrder(String orderNo) throws HsException;
}
