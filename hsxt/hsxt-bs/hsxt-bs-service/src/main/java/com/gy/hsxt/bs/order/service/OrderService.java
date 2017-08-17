/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.bs.order.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.bs.api.order.IBSOrderService;
import com.gy.hsxt.bs.apply.interfaces.IDeclareService;
import com.gy.hsxt.bs.bean.order.Order;
import com.gy.hsxt.bs.bean.order.OrderCustom;
import com.gy.hsxt.bs.bean.order.OrderPaymentResult;
import com.gy.hsxt.bs.bean.order.OrderQueryParam;
import com.gy.hsxt.bs.bean.tempacct.TempAcctLink;
import com.gy.hsxt.bs.bean.tempacct.TempAcctLinkQuery;
import com.gy.hsxt.bs.common.PageContext;
import com.gy.hsxt.bs.common.bean.PayUrl;
import com.gy.hsxt.bs.common.enumtype.BSRespCode;
import com.gy.hsxt.bs.common.enumtype.order.OrderStatus;
import com.gy.hsxt.bs.common.enumtype.order.OrderType;
import com.gy.hsxt.bs.common.interfaces.ICommonService;
import com.gy.hsxt.bs.common.interfaces.IPaymentNotifyService;
import com.gy.hsxt.bs.disconf.BsConfig;
import com.gy.hsxt.bs.order.interfaces.IOrderService;
import com.gy.hsxt.bs.order.mapper.AccountDetailMapper;
import com.gy.hsxt.bs.order.mapper.OrderMapper;
import com.gy.hsxt.bs.tempacct.mapper.TempAcctLinkMapper;
import com.gy.hsxt.common.bean.OpenQuickPayBean;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.constant.BizGroup;
import com.gy.hsxt.common.constant.PayChannel;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.guid.GuidAgent;
import com.gy.hsxt.common.utils.DateUtil;
import com.gy.hsxt.common.utils.HsAssert;
import com.gy.hsxt.gp.bean.PaymentOrderState;
import com.gy.hsxt.lcs.bean.LocalInfo;

/**
 * 订单Dubbo Service实现类
 * 
 * @Package: com.hsxt.bs.order.service
 * @ClassName: OrderService
 * @Description: TODO
 * 
 * @author: kongsl
 * @date: 2015-9-30 下午2:57:30
 * @version V1.0
 */
@Service(value = "orderService")
public class OrderService implements IBSOrderService, IOrderService {

    // 记帐分解mapper接口
    @Autowired
    AccountDetailMapper accountDetailMapper;

    // 记帐分解服务
    @Autowired
    AccountDetailService accountDetailService;

    /** 业务服务私有配置参数 **/
    @Autowired
    private BsConfig bsConfig;

    // 订单mapper接口
    @Autowired
    OrderMapper orderMapper;

    // 远程调用服务类
    @Autowired
    InvokeRemoteService invokeRemoteService;

    // 全局配置接口
    @Autowired
    IDeclareService declareService;

    /** BS公共Service **/
    @Autowired
    ICommonService commonService;

    /**
     * 网银支付回调接口
     */
    @Autowired
    IPaymentNotifyService paymentNotifyService;

    /**
     * 临账Mapper接口
     */
    @Resource
    private TempAcctLinkMapper tempAcctLinkMapper;

    /**
     * 保存订单
     * 
     * @param order
     *            订单对象
     * @throws HsException
     * @see com.gy.hsxt.bs.api.order.IBSOrderService#saveOrder(com.gy.hsxt.bs.bean.order.Order)
     */
    @Override
    public void saveOrder(Order order) throws HsException {
        // 记录业务日志
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), "保存订单,params[" + order + "]");
        // 实体参数为null时抛出异常
        HsAssert.notNull(order, BSRespCode.BS_PARAMS_NULL, "保存订单失败：实体参数为空");
        // 生成订单GUID
        order.setOrderNo(GuidAgent.getStringGuid(BizGroup.BS + bsConfig.getAppNode()));
        // 执行插入订单，返回插入成功记录数
        orderMapper.insertOrder(order);
    }

    /**
     * 通用订单保存实现
     * 
     * @param order
     *            订单信息
     * @return 订单号
     * @throws HsException
     * @see com.gy.hsxt.bs.order.interfaces.IOrderService#saveCommonOrder(com.gy.hsxt.bs.bean.order.Order)
     */
    @Override
    public String saveCommonOrder(Order order) throws HsException {
        // 记录业务日志
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), "通用订单保存,params[" + order + "]");
        // 实体参数为null时抛出异常
        HsAssert.notNull(order, BSRespCode.BS_PARAMS_NULL, "保存订单失败：实体参数为空");
        // 生成订单GUID
        order.setOrderNo(GuidAgent.getStringGuid(BizGroup.BS + bsConfig.getAppNode()));
        try
        {
            // 执行插入订单，返回插入成功记录数
            orderMapper.insertOrder(order);
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), "method:" + Thread.currentThread().getStackTrace()[1].getMethodName(), BSRespCode.BS_SAVE_COMMON_ORDER_ERROR.getCode() + "保存订单异常,params[" + order + "]", e);
            throw new HsException(BSRespCode.BS_SAVE_COMMON_ORDER_ERROR.getCode(), "保存订单异常,params[" + order + "]" + e);
        }

        // 默认返回当前记录订单号
        return order.getOrderNo();
    }

    /**
     * 同步更新支付状态
     * 
     * @param paymentResult
     *            支付结果实体
     * @throws HsException
     * @see com.gy.hsxt.bs.api.order.IBSOrderService#synPayStatus(com.gy.hsxt.bs.bean.order.OrderPaymentResult)
     */
    @Override
    public void synPayStatus(OrderPaymentResult paymentResult) throws HsException {
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), "同步更新订单支付状态,params[" + paymentResult + "]");
        if (paymentResult != null)
        {
            try
            {
                PaymentOrderState pos = new PaymentOrderState();
                BeanUtils.copyProperties(paymentResult, pos);
                paymentNotifyService.notifyPaymentOrderState(pos);
            }
            catch (HsException e)
            {
                throw e;
            }
            catch (Exception e)
            {
                SystemLog.error(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), BSRespCode.BS_UPDATE_ORDER_STATUS_ERROR.getCode() + "同步更新订单支付状态异常", e);
                throw new HsException(BSRespCode.BS_UPDATE_ORDER_STATUS_ERROR.getCode(), "同步更新订单支付状态异常" + e);
            }
        }
    }

    /**
     * 根据订单号获取订单详情
     * 
     * @param orderNo
     *            订单号
     * @return 订单详情
     * @throws HsException
     * @see com.gy.hsxt.bs.api.order.IBSOrderService#getOrder(java.lang.String)
     */
    @Override
    public OrderCustom getOrder(String orderNo) throws HsException {
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), "查询订单详情,params[orderNo:" + orderNo + "]");
        // 验证订单号不能为空
        HsAssert.hasText(orderNo, BSRespCode.BS_PARAMS_NULL, "查询订单详情：订单号参数为空");
        try
        {
            // 调用查询方法
            return orderMapper.findOrderByOrderNo(orderNo);
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), "method:" + Thread.currentThread().getStackTrace()[1].getMethodName(), BSRespCode.BS_FIND_ORDER_DETAIL_ERROR.getCode() + ":查询订单详情异常,params[orderNo:" + orderNo + "]", e);
            throw new HsException(BSRespCode.BS_FIND_ORDER_DETAIL_ERROR.getCode(), "查询订单详情异常,params[orderNo:" + orderNo + "]" + e);
        }
    }

    /**
     * 获取分页订单列表
     * 
     * @param orderQueryParam
     *            查询条件
     * @param page
     *            分页信息
     * @return 订单列表
     * @throws HsException
     * @see com.gy.hsxt.bs.api.order.IBSOrderService#getOrderList(com.gy.hsxt.bs.bean.order.OrderQueryParam,
     *      com.gy.hsxt.common.bean.Page)
     */
    @Override
    public PageData<OrderCustom> getOrderList(OrderQueryParam orderQueryParam, Page page) throws HsException {
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), "分页查询订单列表,params[" + orderQueryParam + "]");
        // 分页数据
        PageData<OrderCustom> orderListPage = null;
        // 分页参数为空
        HsAssert.notNull(page, BSRespCode.BS_PAGE_PARAM_NULL, "查询订单列表：分页条件参数为空");
        // 设置分页信息
        PageContext.setPage(page);
        // 设置开始日期00:00:00结束日期59:59:59
        orderQueryParam.setQueryDate();
        try
        {
            // 执行查询
            List<OrderCustom> orderList = orderMapper.findOrderListPage(orderQueryParam);
            // 验证查询结果
            HsAssert.notNull(orderList, BSRespCode.BS_NOT_QUERY_DATA, "查询订单列表：未查询到数据");
            // 为公用分页查询设置查询结果集
            orderListPage = new PageData<OrderCustom>(page.getCount(), orderList);
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), BSRespCode.BS_QUERY_ORDER_LIST_ERROR.getCode() + ":查询订单列表异常,params[" + orderQueryParam + "]", e);
            throw new HsException(BSRespCode.BS_QUERY_ORDER_LIST_ERROR.getCode(), "查询订单列表异常,params[" + orderQueryParam + "]" + e);
        }
        return orderListPage;
    }

    /**
     * 获取网银支付地址
     * 
     * @param payUrl
     *            非空订单支付方式，枚举类：PayChannel 订单数据 支付成功后跳转页面 签约号：快捷支付时需要
     *            短信验证码：快捷支付时需要
     * @return 成功返回true，false或异常为失败
     * @throws HsException
     * @see com.gy.hsxt.bs.order.interfaces.IOrderService#getPayUrl(com.gy.hsxt.bs.common.bean.PayUrl)
     */
    @Override
    public String getPayUrl(PayUrl payUrl) throws HsException {
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), "获取支付URL,params[" + payUrl + "]");
        // 支付方式为空
        HsAssert.notNull(payUrl, BSRespCode.BS_PARAMS_NULL, "获取支付URL：实体参数为空");
        // 订单数据为空
        HsAssert.notNull(payUrl.getOrder(), BSRespCode.BS_PARAMS_NULL, "获取支付URL：订单数据为空");
        try
        {
            String url = "";
            // 设置定单支付中时间
            payUrl.getOrder().setPayingTime(DateUtil.getCurrentDate2String());
            // 网银支付
            if (payUrl.getPayChannel() == PayChannel.E_BANK_PAY.getCode())
            {
                // 获取网银支付URL
                url = invokeRemoteService.getNetPayUrl(payUrl.getOrder(), payUrl.getCallbackUrl());
            }
            else
            // 快捷支付
            if (payUrl.getPayChannel() == PayChannel.QUICK_PAY.getCode())
            {
                HsAssert.hasText(payUrl.getBindingNo(), BSRespCode.BS_PARAMS_NULL, "签约号为空");
                HsAssert.hasText(payUrl.getSmsCode(), BSRespCode.BS_PARAMS_NULL, "短信验证码为空");
                // 获取快捷支付URL
                url = invokeRemoteService.getQuickPayUrl(payUrl.getOrder().getOrderNo(), payUrl.getBindingNo(), payUrl.getSmsCode());
            }
            else
            // 手机支付
            if (payUrl.getPayChannel() == PayChannel.MOBILE_PAY.getCode())
            {
                // 获取手机支付TN码
                url = invokeRemoteService.getMobilePayTn(payUrl.getOrder(), "");
            }
            else
            // 银行卡支付
            if (payUrl.getPayChannel() == PayChannel.CARD_PAY.getCode())
            {
                HsAssert.hasText(payUrl.getGoodsName(), BSRespCode.BS_PARAMS_NULL, "获取支付URL：交易购买商品名称为空");
                // 获取银行卡支付URL
                url = invokeRemoteService.getCardPayUrl(payUrl);
            }

            // 更新订单支付中时间
            orderMapper.updatePayingTime(payUrl.getOrder().getPayingTime(), payUrl.getOrder().getOrderNo());
            return url;
        }
        catch (HsException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), "method:" + Thread.currentThread().getStackTrace()[1].getMethodName(), BSRespCode.BS_GET_PAY_URL_ERROR.getCode() + ":获取支付URL异常,params[" + payUrl + "]", e);
            throw new HsException(BSRespCode.BS_GET_PAY_URL_ERROR.getCode(), "获取支付URL异常,params[" + payUrl + "]" + e);
        }
    }

    /**
     * 获取网银支付地址
     * 
     * @param payChannel
     *            非空订单支付方式，枚举类：PayChannel
     * @param orderNo
     *            订单号
     * @param bindingNo
     *            签约号：快捷支付时需要
     * @param smsCode
     *            短信验证码：快捷支付时需要
     * @return 支付URL
     * @throws HsException
     * @see com.gy.hsxt.bs.api.order.IBSOrderService#getPayUrl(java.lang.Integer,
     *      java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public String getPayUrl(Integer payChannel, String orderNo, String bindingNo, String smsCode) throws HsException {
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), "获取支付URL,params[payChannel:" + payChannel + ",orderNo:" + orderNo + ",bindingNo:" + bindingNo + ",smsCode:" + smsCode + "]");
        String payUrl = "";
        // 支付方式为空
        HsAssert.notNull(payChannel, BSRespCode.BS_PARAMS_NULL, "获取支付URL：支付方式参数为空");
        // 订单号参数为空
        HsAssert.hasText(orderNo, BSRespCode.BS_PARAMS_NULL, "获取支付URL：订单号参数为空");
        try
        {
            // 地区平台信息
            LocalInfo info = commonService.getAreaPlatInfo();

            // 查询订单数据
            Order order = orderMapper.findUnPayOrder(orderNo);
            // 设置定义支付中时间
            order.setPayingTime(DateUtil.getCurrentDate2String());
            // 未查询到原始订单数据
            HsAssert.notNull(order, BSRespCode.BS_NOT_QUERY_DATA, "获取支付URL：未查询到原始订单数据");
            // 网银支付
            if (PayChannel.E_BANK_PAY.getCode().intValue() == payChannel)
            {
                // 获取网银支付URL
                payUrl = invokeRemoteService.getNetPayUrl(order, info.getWebPayJumpUrl());
            }
            else
            // 快捷支付
            if (PayChannel.QUICK_PAY.getCode().intValue() == payChannel)
            {
                // 获取快捷支付URL
                payUrl = invokeRemoteService.getQuickPayUrl(order.getOrderNo(), bindingNo, smsCode);
            }
            else
            // 手机支付
            if (PayChannel.MOBILE_PAY.getCode().intValue() == payChannel)
            {
                // 获取手机支付TN码
                invokeRemoteService.getMobilePayTn(order, "");
            }
            else
            // 银行卡支付
            if (PayChannel.CARD_PAY.getCode().intValue() == payChannel)
            {
                // 获取银行卡支付URL
                payUrl = invokeRemoteService.getCardPayUrl(PayUrl.bulid(order, payChannel, "", bindingNo, smsCode, OrderType.getGoodNameByOrderType(order.getOrderType())));
            }
            // 更新订单支付方式
            orderMapper.updateOrderPayChannel(orderNo, payChannel);
            // 更新订单支付中时间
            orderMapper.updatePayingTime(order.getPayingTime(), orderNo);
        }
        catch (HsException e)
        {
            SystemLog.error(this.getClass().getName(), "method:" + Thread.currentThread().getStackTrace()[1].getMethodName(), e.getMessage(), e);
            throw e;
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), "method:" + Thread.currentThread().getStackTrace()[1].getMethodName(), BSRespCode.BS_GET_PAY_URL_ERROR.getCode() + ":获取支付URL异常,params[payChannel:" + payChannel + ",orderNo:" + orderNo + ",bindingNo:" + bindingNo + ",smsCode:" + smsCode + "]", e);
            throw new HsException(BSRespCode.BS_GET_PAY_URL_ERROR.getCode(), "获取支付URL异常,params[payChannel:" + payChannel + ",orderNo:" + orderNo + ",bindingNo:" + bindingNo + ",smsCode:" + smsCode + "]" + e);
        }
        return payUrl;
    }

    /**
     * 开通快捷支付并获取支付URL
     * 
     * @param openQuickPayBean
     *            开通快捷支付实体类
     * @return 首次快捷支付URL
     * @throws HsException
     * @see com.gy.hsxt.bs.api.order.IBSOrderService#getOpenQuickPayUrl(com.gy.hsxt.bs.bean.order.OpenQuickPayBean)
     */
    @Override
    public String getOpenQuickPayUrl(OpenQuickPayBean openQuickPayBean) throws HsException {
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), "开通快捷支付并获取支付URL,params[" + openQuickPayBean + "]");
        String payUrl = "";
        // 参数对象为空
        HsAssert.notNull(openQuickPayBean, BSRespCode.BS_PARAMS_NULL, "开通快捷支付并获取支付URL：输入参数对象为空");
        try
        {
            // 查询未支付且未过期的记录
            Order order = findUnPayOrder(openQuickPayBean.getOrderNo());
            // 设置定单支付中时间
            order.setPayingTime(DateUtil.getCurrentDate2String());

            // 更新支付方式
            orderMapper.updateOrderPayChannel(openQuickPayBean.getOrderNo(), PayChannel.QUICK_PAY.getCode());

            /**
             * 初始化bean{
             */
            openQuickPayBean.setOrderNo(order.getOrderNo());// 交易流水号
            openQuickPayBean.setOrderDate(DateUtil.StringToDate(order.getPayingTime(), DateUtil.DEFAULT_DATE_TIME_FORMAT));// 订单日期使用支付中的时间
            openQuickPayBean.setCustId(order.getCustId());// 客户号
            openQuickPayBean.setTransAmount(order.getOrderCashAmount());// 交易金额
            /**
             * }
             */

            // 获取网银支付URL
            payUrl = invokeRemoteService.getOpenQuickPayUrl(openQuickPayBean);

            // 更新订单支付中时间
            orderMapper.updatePayingTime(order.getPayingTime(), order.getOrderNo());
        }
        catch (HsException e)
        {
            SystemLog.error(this.getClass().getName(), "method:" + Thread.currentThread().getStackTrace()[1].getMethodName(), e.getErrorCode() + ":" + e.getMessage(), e);
            throw e;
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), "method:" + Thread.currentThread().getStackTrace()[1].getMethodName(), BSRespCode.BS_GET_PAY_URL_ERROR.getCode() + "获取支付URL并开通快捷支付异常", e);
            throw new HsException(BSRespCode.BS_GET_PAY_URL_ERROR.getCode(), " 获取支付URL并开通快捷支付异常");
        }
        return payUrl;
    }

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
     * @see com.gy.hsxt.bs.api.order.IBSOrderService#getQuickPaySmsCode(java.lang.String,
     *      java.lang.String, java.lang.String)
     */
    @Override
    public void getQuickPaySmsCode(String transNo, String bindingNo, String privObligate) throws HsException {
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), "获取快捷支付短信验证码,params[transNo:" + transNo + ",bindingNo:" + bindingNo + ",privObligate:" + privObligate + "]");
        // 交易流水号为空
        HsAssert.hasText(transNo, BSRespCode.BS_PARAMS_NULL, "获取快捷支付短信验证码：订单编号为空");
        // 签约号为空
        HsAssert.hasText(bindingNo, BSRespCode.BS_PARAMS_NULL, "获取快捷支付短信验证码：签约号为空");
        try
        {
            // 查询未支付且未过期的记录
            Order order = findUnPayOrder(transNo);
            // 获取快捷支付短信验证码
            invokeRemoteService.getQuickPaySmsCode(order, bindingNo, privObligate);
        }
        catch (HsException e)
        {
            SystemLog.error(this.getClass().getName(), "method:" + Thread.currentThread().getStackTrace()[1].getMethodName(), e.getErrorCode() + ":" + e.getMessage(), e);
            throw e;
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), "method:" + Thread.currentThread().getStackTrace()[1].getMethodName(), BSRespCode.BS_GET_QUICK_PAY_SMS_CODE_ERROR.getCode() + "获取快捷支付短信验证码异常,params[transNo:" + transNo + ",bindingNo:" + bindingNo + ",privObligate:" + privObligate + "]", e);
            throw new HsException(BSRespCode.BS_GET_QUICK_PAY_SMS_CODE_ERROR.getCode(), "获取快捷支付短信验证码异常,params[transNo:" + transNo + ",bindingNo:" + bindingNo + ",privObligate:" + privObligate + "]" + e);
        }
    }

    /**
     * 查询客户自己的订单列表
     * 
     * @param queryParam
     *            查询条件
     * @param page
     *            分页条件
     * @return
     * @throws HsException
     * @see com.gy.hsxt.bs.api.order.IBSOrderService#getMyOrderList(com.gy.hsxt.bs.bean.order.OrderQueryParam,
     *      com.gy.hsxt.common.bean.Page)
     */
    @Override
    public PageData<OrderCustom> getMyOrderList(OrderQueryParam queryParam, Page page) throws HsException {
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), "分页客户自己的订单列表,params[" + queryParam + "]");
        // 分页数据
        PageData<OrderCustom> orderListPage = null;
        // 分页参数为空
        HsAssert.notNull(page, BSRespCode.BS_PAGE_PARAM_NULL, "客户自己的订单列表：分页条件参数为空");
        // 订单客户号为空
        HsAssert.notNull(queryParam.getHsCustId(), BSRespCode.BS_PAGE_PARAM_NULL, "客户自己的订单列表：订单客户号参数为空");
        // 设置分页信息
        PageContext.setPage(page);
        // 设置开始日期00:00:00结束日期59:59:59
        queryParam.setQueryDate();
        try
        {
            // 执行查询
            List<OrderCustom> orderList = orderMapper.findOrderListPage(queryParam);
            // 验证查询结果
            HsAssert.notNull(orderList, BSRespCode.BS_NOT_QUERY_DATA, "客户自己的订单列表：未查询到数据");
            // 为公用分页查询设置查询结果集
            orderListPage = new PageData<OrderCustom>(page.getCount(), orderList);
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), "method:" + Thread.currentThread().getStackTrace()[1].getMethodName(), BSRespCode.BS_QUERY_ORDER_LIST_ERROR.getCode() + ":客户自己的订单列表,params[" + queryParam + "," + page + "]", e);
            throw new HsException(BSRespCode.BS_QUERY_ORDER_LIST_ERROR.getCode(), "客户自己的订单列表,params[" + queryParam + "," + page + "]" + e);
        }
        return orderListPage;
    }

    /**
     * 实现根据订单号查询订单记录内部接口方法
     * 
     * @param orderNo
     *            订单号
     * @return 订单
     * @throws HsException
     * @see com.gy.hsxt.bs.order.interfaces.IOrderService#getOrderByNo(java.lang.String)
     */
    @Override
    public Order getOrderByNo(String orderNo) throws HsException {
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), "查询订单详情,params[orderNo:" + orderNo + "]");
        // 验证订单号不能为空
        HsAssert.hasText(orderNo, BSRespCode.BS_PARAMS_NULL, "查询订单详情：订单编号为空");
        try
        {
            // 调用查询方法
            return orderMapper.findOrderByOrderNo(orderNo);
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), "method:" + Thread.currentThread().getStackTrace()[1].getMethodName(), BSRespCode.BS_FIND_ORDER_DETAIL_ERROR.getCode() + ":查询订单详情异常,params[orderNo:" + orderNo + "]", e);
            throw new HsException(BSRespCode.BS_FIND_ORDER_DETAIL_ERROR.getCode(), "查询订单详情异常,params[orderNo:" + orderNo + "]" + e);
        }
    }

    /**
     * 更新订单状态
     * 
     * @param orderNo
     *            订单号
     * @param orderStatus
     *            订单状态码
     * @throws HsException
     * @see com.gy.hsxt.bs.order.interfaces.IOrderService#updateOrderStatus(java.lang.String,
     *      java.lang.Integer)
     */
    @Override
    public void updateOrderStatus(String orderNo, Integer orderStatus) throws HsException {
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), "更新订单状态,params[orderNo:" + orderNo + ",orderStatus:" + orderStatus + "]");
        // 订单编号为空
        HsAssert.hasText(orderNo, BSRespCode.BS_PARAMS_NULL, "更新订单状态：订单编号为空");
        // 订单状态码为空
        HsAssert.notNull(orderStatus, BSRespCode.BS_PARAMS_NULL, "更新订单状态：订单状态为空");
        try
        {
            // 执行更新订单状态
            orderMapper.updateOrderStatus(orderNo, orderStatus);
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), "method:" + Thread.currentThread().getStackTrace()[1].getMethodName(), BSRespCode.BS_UPDATE_ORDER_STATUS_ERROR.getCode() + ":更新订单状态异常,params[orderNo:" + orderNo + ",orderStatus:" + orderStatus + "]", e);
            throw new HsException(BSRespCode.BS_UPDATE_ORDER_STATUS_ERROR.getCode(), "更新订单状态异常,params[orderNo:" + orderNo + ",orderStatus:" + orderStatus + "]" + e);
        }
    }

    /**
     * 实现更新资源费订单内部接口方法
     * 
     * @param orderNo
     *            订单编号
     * @throws HsException
     * @see com.gy.hsxt.bs.order.interfaces.IOrderService#updateResFeeOrder(java.lang.String)
     */
    @Override
    public void updateResFeeOrder(String orderNo) throws HsException {
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), "更新资源费订单,params[orderNo:" + orderNo + "]");
        // 订单编号为空
        HsAssert.hasText(orderNo, BSRespCode.BS_PARAMS_NULL, "更新资源费订单：订单编号为空");
        try
        {
            // 更新订单
            orderMapper.updateResFeeOrder(orderNo);
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), "method:" + Thread.currentThread().getStackTrace()[1].getMethodName(), BSRespCode.BS_UPDATE_RES_FEE_ORDER_ERROR.getCode() + ":更新资源费订单异常,params[orderNo:" + orderNo + "]", e);
            throw new HsException(BSRespCode.BS_UPDATE_RES_FEE_ORDER_ERROR.getCode(), "更新资源费订单异常,params[orderNo:" + orderNo + "]" + e);
        }
    }

    /**
     * 更新订单所有状态
     * 
     * @param order
     *            订单信息
     * @throws HsException
     * @see com.gy.hsxt.bs.order.interfaces.IOrderService#updateOrderAllStatus(com.gy.hsxt.bs.bean.order.Order)
     */
    @Override
    @Transactional
    public void updateOrderAllStatus(Order order) throws HsException {
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), "更新订单状态,params[" + order + "]");
        // 订单编号为空
        HsAssert.hasText(order.getOrderNo(), RespCode.PARAM_ERROR, "更新订单状态：订单编号为空");
        // 订单状态码为空
        HsAssert.notNull(order.getOrderStatus(), RespCode.PARAM_ERROR, "更新订单状态：订单状态为空,orderNo=" + order.getOrderNo());
        try
        {
            // 执行更新
            orderMapper.updateOrderAllStatus(order);
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), "method:" + Thread.currentThread().getStackTrace()[1].getMethodName(), BSRespCode.BS_UPDATE_ORDER_STATUS_ERROR.getCode() + ":更新订单状态异常,params[" + order + "]", e);
            throw new HsException(BSRespCode.BS_UPDATE_ORDER_STATUS_ERROR.getCode(), "更新订单状态异常,params[" + order + "]" + e);
        }
    }

    /**
     * 关闭订单
     * 
     * @param orderNo
     *            订单编号
     * @throws HsException
     * @see com.gy.hsxt.bs.api.order.IBSOrderService#closeOrder(java.lang.String)
     */
    @Override
    @Transactional
    public void closeOrder(String orderNo) throws HsException {
        // 记录业务日志
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), "关闭订单,params[orderNo:" + orderNo + "]");
        // 订单编号为空
        HsAssert.hasText(orderNo, BSRespCode.BS_PARAMS_NULL, "关闭订单：订单编号为空");
        try
        {
            /**
             * 订单是否被临帐关联且正在审批中
             */
            // 查询订单
            Order order = orderMapper.findOrderByNo(orderNo);
            if (order != null)
            {
                TempAcctLinkQuery tempAcctLinkQuery = new TempAcctLinkQuery();
                tempAcctLinkQuery.setOrderNo(orderNo);
                tempAcctLinkQuery.setStatus(0);
                // 查询临帐信息
                TempAcctLink tempAcctLink = tempAcctLinkMapper.selectBeanByQuery(tempAcctLinkQuery);
                // 正在审批中的订单不可关闭
                HsAssert.isNull(tempAcctLink, BSRespCode.BS_CLOSE_ORDER_IS_APPR, "关闭订单：当前订单正在审批");
            }
            // 更新订单状态为关闭
            orderMapper.updateOrderStatus(orderNo, OrderStatus.IS_CLOSED.getCode());
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), "method:" + Thread.currentThread().getStackTrace()[1].getMethodName(), BSRespCode.BS_CLOSE_ORDER_ERROR.getCode() + "关闭订单异常,params[orderNo:" + orderNo + "]", e);
            throw new HsException(BSRespCode.BS_CLOSE_ORDER_ERROR.getCode(), "关闭订单异常,params[orderNo:" + orderNo + "]" + e);
        }
    }

    /**
     * 查询支付系统对账数据
     * 
     * @param queryParam
     *            查询实体
     * @return list
     * @throws HsException
     */
    @Override
    public List<Order> queryListForGPByQuery(OrderQueryParam queryParam) throws HsException {
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), "查询支付系统对账数据,params[queryParam:" + queryParam + "]");
        return orderMapper.selectListForGPByQuery(queryParam);
    }

    /**
     * 查询未支付的订单
     * 
     * @param orderNo
     *            订单编号
     * @return 订单信息
     */
    public Order findUnPayOrder(String orderNo) {
        Order order = null;
        try
        {
            order = orderMapper.findUnPayOrder(orderNo);
            HsAssert.notNull(order, BSRespCode.BS_NOT_QUERY_DATA, "未查询到待付款订单,orderNo=" + order.getOrderNo());
            int date = DateUtil.compare_date(DateUtil.getCurrentDateTime(), DateUtil.getMaxDateOfDayStr(order.getPayOvertime()));
            HsAssert.isTrue(date != 1, BSRespCode.BS_ORDER_TIME_OUT, "订单已超时,orderNo=" + order.getOrderNo());
        }
        catch (HsException e)
        {
            SystemLog.error(this.getClass().getName(), "method:" + Thread.currentThread().getStackTrace()[1].getMethodName(), e.getErrorCode() + ":" + e.getMessage(), e);
            throw e;
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), "method:" + Thread.currentThread().getStackTrace()[1].getMethodName(), BSRespCode.BS_QUERY_ERROR.getCode() + "获取快捷支付短信验证码查询订单异常", e);
            throw new HsException(BSRespCode.BS_QUERY_ERROR.getCode(), "获取快捷支付短信验证码查询订单异常");
        }
        return order;
    }
}
