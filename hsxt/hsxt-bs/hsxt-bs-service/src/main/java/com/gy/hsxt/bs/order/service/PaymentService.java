/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.order.service;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.bs.api.order.IBSPaymentService;
import com.gy.hsxt.bs.bean.order.Order;
import com.gy.hsxt.bs.bean.order.PayInfo;
import com.gy.hsxt.bs.common.enumtype.BSRespCode;
import com.gy.hsxt.bs.common.enumtype.order.OrderType;
import com.gy.hsxt.bs.common.interfaces.ICommonService;
import com.gy.hsxt.bs.disconf.BsConfig;
import com.gy.hsxt.bs.order.interfaces.IOrderService;
import com.gy.hsxt.bs.order.interfaces.IPaymentService;
import com.gy.hsxt.bs.order.mapper.OrderMapper;
import com.gy.hsxt.common.constant.PayChannel;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.DateUtil;
import com.gy.hsxt.common.utils.HsAssert;
import com.gy.hsxt.gp.api.IGPPaymentService;
import com.gy.hsxt.gp.bean.pinganpay.PinganNetPayBean;
import com.gy.hsxt.gp.bean.pinganpay.PinganQuickPayBean;
import com.gy.hsxt.gp.bean.pinganpay.PinganQuickPayBindingBean;
import com.gy.hsxt.gp.bean.pinganpay.PinganQuickPaySmsCodeBean;
import com.gy.hsxt.lcs.bean.LocalInfo;

/**
 * 支付服务实现类
 * 
 * @Package: com.gy.hsxt.bs.order.service
 * @ClassName: PaymentService
 * @Description: TODO
 * 
 * @author: kongsl
 * @date: 2016-7-1 下午6:10:00
 * @version V1.0
 */
@Service
public class PaymentService implements IBSPaymentService, IPaymentService {
    /**
     * 帐户操作私有配置参数
     **/
    @Resource
    BsConfig bsConfig;

    /**
     * 公共服务，获取全局公共数据
     */
    @Resource
    ICommonService commonService;

    /**
     * 支付系统：网银支付
     */
    @Resource
    IGPPaymentService gpPaymentService;

    /**
     * 订单内部接口
     */
    @Autowired
    IOrderService orderService;

    /**
     * 订单mapper
     */
    @Autowired
    OrderMapper orderMapper;

    /**
     * 获取支付URL
     * 
     * @param payInfo
     *            支付信息
     * @return 支付URL
     * @throws HsException
     */
    @Override
    public String getPayUrl(PayInfo payInfo) throws HsException {
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), "获取支付URL,params[" + payInfo + "]");
        // 实体参数为空
        HsAssert.notNull(payInfo, BSRespCode.BS_PARAMS_NULL, "获取支付URL：实体参数为空");
        HsAssert.hasText(payInfo.getTransNo(), BSRespCode.BS_PARAMS_NULL, "获取支付URL：交易流水号为空");
        HsAssert.notNull(payInfo.getPayChannel(), BSRespCode.BS_PARAMS_NULL, "获取支付URL：支付方式为空");
        String payUrl = "";
        try
        {
            Order order = payInfo.getOrder();
            if (null == order)
            {
                order = orderService.findUnPayOrder(payInfo.getTransNo());
            }

            // 设置定单支付中时间
            order.setPayingTime(DateUtil.getCurrentDate2String());

            // 银行卡支付
            if (PayChannel.CARD_PAY.getCode().equals(payInfo.getPayChannel()))
            {
                // 获取银行卡支付URL
                payUrl = getCardPayUrl(order, payInfo);
            }
            // 快捷支付
            if (PayChannel.QUICK_PAY.getCode().equals(payInfo.getPayChannel()))
            {
                // 获取快捷支付URL
                payUrl = getQuickPayUrl(payInfo.getTransNo(), payInfo.getBindingNo(), payInfo.getSmsCode());
            }

            // 更新订单支付中时间
            orderMapper.updatePayingTime(order.getPayingTime(), order.getOrderNo());
        }
        catch (HsException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), BSRespCode.BS_GET_PAY_URL_ERROR.getCode() + ":获取支付URL异常,params[" + payInfo + "]", e);
            throw new HsException(BSRespCode.BS_GET_PAY_URL_ERROR.getCode(), "获取支付URL异常,params[" + payInfo + "]" + e);
        }
        return payUrl;
    }

    /**
     * 获取银行卡支付URL
     * 
     * @param buyHsb
     *            兑换互生币信息
     * @param jumpUrl
     *            回跳地址
     * @return 银行卡支付URL
     * @throws HsException
     */
    private String getCardPayUrl(Order order, PayInfo payInfo) throws HsException {
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), "获取银行卡支付URL,params[buyHsb:" + order + ",payInfo:" + payInfo + "]");
        HsAssert.hasText(payInfo.getGoodsName(), BSRespCode.BS_PARAMS_NULL, "获取支付URL：交易购买商品名称为空");
        HsAssert.hasText(payInfo.getCallbackUrl(), BSRespCode.BS_PARAMS_NULL, "获取支付URL：回跳地址为空");
        // 支付URL
        String payUrl = "";
        PinganNetPayBean netPayBean = null;
        try
        {
            // 获取本地信息
            LocalInfo localInfo = commonService.getAreaPlatInfo();
            // 银联支付实体类
            netPayBean = new PinganNetPayBean();
            netPayBean.setMerId(bsConfig.getMerchantNo());// 商户号
            netPayBean.setOrderNo(order.getOrderNo());// 业务订单号
            netPayBean.setOrderDate(DateUtil.StringToDate(order.getPayingTime(), DateUtil.DEFAULT_DATE_TIME_FORMAT));// 业务订单日期
            netPayBean.setTransAmount(order.getOrderCashAmount());// 交易金额
            netPayBean.setTransDesc(payInfo.getTransDesc());// 交易描述
            netPayBean.setCurrencyCode(localInfo.getCurrencyCode());// 币种
            netPayBean.setJumpUrl(payInfo.getCallbackUrl());// 回跳地址
            netPayBean.setPrivObligate(payInfo.getPrivObligate());// 私有数据
            netPayBean.setGoodsName(payInfo.getGoodsName());// 交易购买商品名称
            netPayBean.setPaymentUIType(2);// 支付页面显示类型

            // 调用支付系统：获取银行卡支付URL接口
            payUrl = gpPaymentService.getPinganNetPayUrl(netPayBean, bsConfig.getSysName());
        }
        catch (HsException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), BSRespCode.BS_GET_PAY_URL_ERROR + ":调用支付系统获取银行卡支付URL异常：" + gpPaymentService.getClass().getName() + "." + "getPinganNetPayUrl(" + JSON.toJSONString(netPayBean) + "," + bsConfig.getSysName() + ")\n", e);
            throw new HsException(BSRespCode.BS_GET_PAY_URL_ERROR, "调用支付系统获取银行卡支付URL异常：" + gpPaymentService.getClass().getName() + "." + "getPinganNetPayUrl(" + JSON.toJSONString(netPayBean) + "," + bsConfig.getSysName() + ")\n" + e);
        }
        return payUrl;
    }

    /**
     * 获取快捷支付URL
     * 
     * @param transNo
     *            交易流水号
     * @param bindingNo
     *            签约号
     * @param smsCode
     *            短信验证码
     * @return 快捷支付URL
     * @throws HsException
     */
    private String getQuickPayUrl(String transNo, String bindingNo, String smsCode) throws HsException {
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), "获取快捷支付URL,params[transNo:" + transNo + ",bindingNo:" + bindingNo + ",smsCode:" + smsCode + "]");
        String payUrl = "";
        HsAssert.hasText(bindingNo, BSRespCode.BS_PARAMS_NULL, "获取快捷支付URL：签约号为空");
        HsAssert.hasText(smsCode, BSRespCode.BS_PARAMS_NULL, "获取快捷支付URL：短信验证码为空");
        // 银联支付实体
        PinganQuickPayBean quickPayBean = new PinganQuickPayBean();
        try
        {
            // 从配置文件中取互生商户号
            quickPayBean.setMerId(bsConfig.getMerchantNo());
            quickPayBean.setOrderNo(transNo);// 业务订单号
            quickPayBean.setBindingNo(bindingNo);// 签约号
            quickPayBean.setSmsCode(smsCode);// 短信验证码

            // 调用支付系统：获取快捷支付URL接口
            payUrl = gpPaymentService.getPinganQuickPayUrl(quickPayBean, bsConfig.getSysName());
        }
        catch (HsException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), BSRespCode.BS_GET_PAY_URL_ERROR + ":调用支付系统获取快捷支付URL异常：" + gpPaymentService.getClass().getName() + "." + "getQuickPayUrl(" + JSON.toJSONString(quickPayBean) + ")\n", e);
            throw new HsException(BSRespCode.BS_GET_PAY_URL_ERROR, "调用支付系统获取快捷支付URL异常：" + gpPaymentService.getClass().getName() + "." + "getQuickPayUrl(" + JSON.toJSONString(quickPayBean) + "," + bsConfig.getSysName() + ")\n" + e);
        }
        return payUrl;
    }

    /**
     * 获取快捷支付绑定URL
     * 
     * @param custId
     *            客户号
     * @return 首次快捷支付URL
     * @throws HsException
     * @see com.gy.hsxt.ao.api.IAOPaymentService#getQuickPayBindUrl(java.lang.String)
     */
    @Override
    public String getQuickPayBindUrl(String custId) throws HsException {
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), "开通快捷支付并获取URL,params[custId:" + custId + "]");
        HsAssert.hasText(custId, BSRespCode.BS_PARAMS_NULL, "获取快捷支付绑定URL：客户号为空");

        // 银联快捷支付
        PinganQuickPayBindingBean bindQuickPayBean = null;
        String quickPayBindUrl = "";
        try
        {
            bindQuickPayBean = new PinganQuickPayBindingBean();
            // 从配置文件中取互生商户号
            bindQuickPayBean.setMerId(bsConfig.getMerchantNo());
            // 客户号
            bindQuickPayBean.setCustId(custId);
            // 调用支付系统：获取首次快捷支付URL接口
            quickPayBindUrl = gpPaymentService.getPinganQuickPayBindingUrl(bindQuickPayBean, bsConfig.getSysName());
        }
        catch (HsException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), BSRespCode.BS_GET_PAY_URL_ERROR + ":调用支付系统获取快捷支付绑定URL异常：" + gpPaymentService.getClass().getName() + "." + "getPinganQuickPayBindingUrl(" + JSON.toJSONString(bindQuickPayBean) + "," + bsConfig.getSysName() + ")\n", e);
            throw new HsException(BSRespCode.BS_GET_PAY_URL_ERROR, "调用支付系统获取快捷支付绑定URL异常：" + gpPaymentService.getClass().getName() + "." + "getPinganQuickPayBindingUrl(" + JSON.toJSONString(bindQuickPayBean) + "," + bsConfig.getSysName() + ")\n" + e);
        }
        return quickPayBindUrl;
    }

    /**
     * 获取快捷支付短信验证码
     * 
     * @param payInfo
     *            支付信息
     * @throws HsException
     * @see com.gy.hsxt.ao.api.IAOPaymentService#getQuickPaySmsCode(com.gy.hsxt.ao.bean.PayInfo)
     */
    public void getQuickPaySmsCode(PayInfo payInfo) throws HsException {
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), "获取快捷支付短信验证码,params[payInfo:" + payInfo + "]");
        HsAssert.notNull(payInfo, BSRespCode.BS_PARAMS_NULL, "获取快捷支付短信验证码：支付信息参数对象为空");
        // 银联快捷支付实体
        PinganQuickPaySmsCodeBean quickPaySmsCodeBean = null;
        try
        {
            // 查询待支付订单
            Order order = orderService.findUnPayOrder(payInfo.getTransNo());

            quickPaySmsCodeBean = new PinganQuickPaySmsCodeBean();
            quickPaySmsCodeBean.setMerId(bsConfig.getMerchantNo());// 商户号
            quickPaySmsCodeBean.setOrderNo(order.getOrderNo());// 业务订单号
            quickPaySmsCodeBean.setOrderDate(DateUtil.StringToDate(order.getOrderTime(), DateUtil.DEFAULT_DATE_TIME_FORMAT));// 业务订单日期
            quickPaySmsCodeBean.setGoodsName(OrderType.getGoodNameByOrderType(order.getOrderType()));// 商品名称
            quickPaySmsCodeBean.setBindingNo(payInfo.getBindingNo());// 签约号
            quickPaySmsCodeBean.setCustId(order.getCustId());// 客户号
            quickPaySmsCodeBean.setTransAmount(StringUtils.isBlank(order.getOrderCashAmount()) ? "0" : order.getOrderCashAmount());// 交易金额
            quickPaySmsCodeBean.setTransDesc(order.getOrderRemark());// 交易描述
            quickPaySmsCodeBean.setCurrencyCode(commonService.getAreaPlatInfo().getCurrencyCode());// 币种
            quickPaySmsCodeBean.setPrivObligate(payInfo.getPrivObligate());// 私有数据

            // 调用支付系统：获取首次快捷支付URL接口
            gpPaymentService.getPinganQuickPaySmsCode(quickPaySmsCodeBean, bsConfig.getSysName());
        }
        catch (HsException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), BSRespCode.BS_GET_QUICK_PAY_SMS_CODE_ERROR + ":调用支付系统：获取快捷支付短信验证码异常：" + gpPaymentService.getClass().getName() + "." + "getQuickPaySmsCode(" + JSON.toJSONString(quickPaySmsCodeBean) + "," + bsConfig.getSysName() + ")\n", e);
            throw new HsException(BSRespCode.BS_GET_QUICK_PAY_SMS_CODE_ERROR, "调用支付系统：获取快捷支付短信验证码异常：" + gpPaymentService.getClass().getName() + "." + "getQuickPaySmsCode(" + JSON.toJSONString(quickPaySmsCodeBean) + "," + bsConfig.getSysName() + ")\n" + e);
        }
    }
}
