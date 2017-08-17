/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.bs.order.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.gy.hsxt.bs.bean.order.Order;
import com.gy.hsxt.bs.bean.order.OrderCustom;
import com.gy.hsxt.bs.bean.order.OrderQueryParam;
import com.gy.hsxt.common.exception.HsException;

/**
 * 订单Mapper DAO映射
 * 
 * @Package: com.hsxt.bs.order.mapper
 * @ClassName: OrderMapper
 * @Description: TODO
 * 
 * @author: kongsl
 * @date: 2015-9-30 下午2:04:29
 * @version V1.0
 */
@Repository("orderMapper")
public interface OrderMapper {

    /**
     * 插入新订单
     * 
     * @param order
     *            订单数据
     * @return 成功记录数
     */
    public int insertOrder(Order order);

    /**
     * 更新订单支付状态
     * 
     * @param orderNo
     *            被更新的订单号
     * @param payStatus
     *            支付状态
     */
    public int updateOrderPayStat(@Param(value = "orderNo") String orderNo,
            @Param(value = "payStatus") Integer payStatus);

    /**
     * 更新订单状态
     * 
     * @param orderNo
     *            被更新的订单号
     * @param orderStatus
     *            订单状态
     * @return 成功记录数
     */
    public int updateOrderStatus(@Param(value = "orderNo") String orderNo,
            @Param(value = "orderStatus") Integer orderStatus);

    /**
     * 根据订单号查询订单详情
     * 
     * @param orderNo
     *            订单号
     * @return 返回订单包装数据，包涵订单收货信息
     * @throws HsException
     */
    public OrderCustom findOrderByOrderNo(String orderNo);

    /**
     * 根据订单号查询订单详情
     * 
     * @param orderNo
     *            订单号
     * @return 订单详情
     * @throws HsException
     */
    public Order findOrderByNo(String orderNo);

    /**
     * 查询未支付订单详情
     * 
     * @param orderNo
     *            订单号
     * @return 订单详情
     * @throws HsException
     */
    public Order findUnPayOrder(String orderNo);

    /**
     * 查询工具订单
     * 
     * @Description:
     * @author: likui
     * @created: 2015年11月4日 下午4:02:30
     * @param orderNo
     * @param orderType
     * @return
     * @return : Order
     * @version V3.0.0
     */
    public Order findToolOrderByNo(@Param("orderNo") String orderNo, @Param("orderType") String[] orderType);

    /**
     * 根据客户号和订单类型查询订单
     * 
     * @Description:
     * @author: likui
     * @created: 2015年12月2日 上午11:07:54
     * @param hsCustId
     * @param orderType
     * @return
     * @return : List<Order>
     * @version V3.0.0
     */
    public List<Order> selectOrderHsCustId(@Param("hsCustId") String hsCustId, @Param("orderType") String[] orderType);

    /**
     * 条件查询订单列表
     * 
     * @param orderQueryParam
     *            查询条件
     * @return 订单列表
     */
    public List<OrderCustom> findOrderListPage(OrderQueryParam orderQueryParam);

    /**
     * 条件查询订单列表
     * 
     * @param orderQueryParam
     *            查询条件
     * @return 订单列表
     */
    public List<OrderCustom> findOrders(OrderQueryParam orderQueryParam);

    /**
     * 修改支付方式
     * 
     * @Desc: TODO
     * @author: likui
     * @created: 2015年10月15日 下午8:58:31
     * @param :
     * @param orderNo
     * @param :
     * @param payChannel
     * @return : void
     * @version V3.0.0
     */
    public int updateOrderPayChannel(@Param("orderNo") String orderNo, @Param("payChannel") Integer payChannel);

    /**
     * 修改工具订单支付成功
     * 
     * @Description:
     * @author: likui
     * @created: 2016年4月20日 下午3:35:39
     * @param orderNo
     * @param bankTransNo
     * @return
     * @return : int
     * @version V3.0.0
     */
    public int updateOrderPaySuccess(@Param("orderNo") String orderNo, @Param("bankTransNo") String bankTransNo);

    /**
     * 修改非确认工具订单支付成功
     * 
     * @Description:
     * @author: likui
     * @created: 2016年4月20日 下午3:41:19
     * @param orderNo
     * @param bankTransNo
     * @param orderStatus
     * @return
     * @return : int
     * @version V3.0.0
     */
    public int updateNoConfirmOrderPaySuccess(@Param("orderNo") String orderNo,
            @Param("bankTransNo") String bankTransNo, @Param("orderStatus") Integer orderStatus);

    /**
     * 更新资源费订单
     * 
     * @param orderNo
     *            订单编号
     * @return 成功记录数
     */
    public int updateResFeeOrder(@Param("orderNo") String orderNo);

    /**
     * 更新订单所有状态
     * 
     * @param order
     *            订单信息
     * @return 成功记录数
     */
    public int updateOrderAllStatus(Order order);

    /**
     * 更新订单免单金额
     * 
     * @param orderNo
     *            订单编号
     * @param orderDerateAmount
     *            订单减免金额
     * @return 成功记录数
     */
    public int updateOrderDerateAmount(@Param("orderNo") String orderNo,
            @Param("orderOriginalAmount") String orderOriginalAmount);

    /**
     * 查询支付系统对账数据
     * 
     * @param queryParam
     *            查询实体
     * @return list
     */
    public List<Order> selectListForGPByQuery(OrderQueryParam queryParam);

    /**
     * 更新订单支付中时间
     * 
     * @param payingTime
     *            支付中时间
     * @param orderNo
     *            订单编号
     * @return 成功记录数
     */
    public int updatePayingTime(@Param("payingTime") String payingTime, @Param("orderNo") String orderNo);

    /**
     * 查询客户未付款成功的订单
     * 
     * @Description:
     * @author: likui
     * @created: 2016年4月1日 上午10:37:17
     * @param hsCustId
     * @param orderType
     * @return
     * @return : Order
     * @version V3.0.0
     */
    public Order selectCustNoPayFinishOrder(@Param("hsCustId") String hsCustId, @Param("orderType") String orderType);
}
