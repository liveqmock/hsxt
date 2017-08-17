/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.bs.api.order;

import com.gy.hsxt.bs.bean.order.Order;
import com.gy.hsxt.bs.bean.order.OrderCustom;
import com.gy.hsxt.bs.bean.order.OrderPaymentResult;
import com.gy.hsxt.bs.bean.order.OrderQueryParam;
import com.gy.hsxt.common.bean.OpenQuickPayBean;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;

/**
 * 业务订单接口
 * 
 * @Package: com.gy.hsxt.bs.api.order
 * @ClassName: IBSOrderService
 * @Description: TODO
 * 
 * @author: kongsl
 * @date: 2015-8-31 下午3:14:06
 * @version V1.0
 */
public interface IBSOrderService {

    /**
     * 保存订单
     * 
     * @param order
     *            订单对象
     * @throws HsException
     */
    public void saveOrder(Order order) throws HsException;

    /**
     * 获取订单详情
     * 
     * @param orderNo
     *            非空订单id
     * @return 返回与条件匹配的订单对象，异常则失败
     * @throws HsException
     */
    public OrderCustom getOrder(String orderNo) throws HsException;

    /**
     * 获取订单列表
     * 
     * @param orderQueryParam
     *            包装查询条件
     * @param page
     *            分页信息
     * @return 分页后的业务订单列表
     * @throws HsException
     */
    public PageData<OrderCustom> getOrderList(OrderQueryParam orderQueryParam, Page page) throws HsException;

    /**
     * 查询我的订单列表
     * 
     * @param orderQueryParam
     *            包装查询条件
     * @param page
     *            分页信息
     * @return 分页后的业务订单列表
     * @throws HsException
     */
    public PageData<OrderCustom> getMyOrderList(OrderQueryParam orderQueryParam, Page page) throws HsException;

    /**
     * 获取网银支付地址
     * 
     * @param payChannel
     *            支付方式，枚举类：PayChannel
     * @param orderNo
     *            订单号
     * @param bindingNo
     *            签约号：快捷支付时需要
     * @param smsCode
     *            短信验证码：快捷支付时需要
     * @return 支付URL
     * @throws HsException
     */
    public String getPayUrl(Integer payChannel, String orderNo, String bindingNo, String smsCode) throws HsException;

    /**
     * 开通快捷支付并获取支付URL
     * 
     * @param openQuickPayBean
     *            开通快捷支付实体类
     * @return 首次快捷支付URL
     * @throws HsException
     */
    public String getOpenQuickPayUrl(OpenQuickPayBean openQuickPayBean) throws HsException;

    /**
     * 获取快捷支付短信验证码
     * 
     * @param orderNo
     *            订单编号
     * @param bindingNo
     *            签约号
     * @param privObligate
     *            私有数据
     * @throws HsException
     */
    public void getQuickPaySmsCode(String orderNo, String bindingNo, String privObligate) throws HsException;

    /**
     * 关闭订单
     * 
     * @param orderNo
     *            订单编号
     * @throws HsException
     */
    public void closeOrder(String orderNo) throws HsException;

    /**
     * 同步更新支付状态
     * 
     * @param paymentResult
     *            支付结果实体
     * @throws HsException
     */
    public void synPayStatus(OrderPaymentResult paymentResult) throws HsException;

}
