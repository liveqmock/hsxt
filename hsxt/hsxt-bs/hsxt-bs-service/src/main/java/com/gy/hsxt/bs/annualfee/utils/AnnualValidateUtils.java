/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.bs.annualfee.utils;

import com.gy.hsxt.bs.bean.annualfee.AnnualFeeOrder;
import com.gy.hsxt.bs.bean.order.Order;
import com.gy.hsxt.bs.common.enumtype.order.OrderStatus;
import com.gy.hsxt.bs.common.enumtype.order.PayStatus;
import com.gy.hsxt.common.constant.PayChannel;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.BigDecimalUtils;
import com.gy.hsxt.common.utils.HsAssert;

/**
 * 年费参数校验工具类
 *
 * @Package :com.gy.hsxt.bs.annualfee.utils
 * @ClassName : AnnualValidateUtils
 * @Description : 年费参数校验工具类
 * @Author : chenm
 * @Date : 2015/12/11 15:41
 * @Version V3.0.0.0
 */
public abstract class AnnualValidateUtils {

    /**
     * 保存参数校验
     *
     * @param annualFeeOrder 年费业务订单
     * @throws HsException
     */
    public static Order saveValidate(AnnualFeeOrder annualFeeOrder) throws HsException {
        // 实体类参为为null抛出异常
        HsAssert.notNull(annualFeeOrder, RespCode.PARAM_ERROR, "年费业务订单[annualFeeOrder]为null");
        //业务订单
        Order order = annualFeeOrder.getOrder();
        //业务订单不能为空
        HsAssert.notNull(order, RespCode.PARAM_ERROR, "业务订单[order]为null");
        //业务订单参数校验
        HsAssert.hasText(order.getCustId(), RespCode.PARAM_ERROR, "客户号[custId]为空");
        HsAssert.hasText(order.getOrderHsbAmount(), RespCode.PARAM_ERROR, "订单互生币金额[orderHsbAmount]为空");
        HsAssert.isTrue(BigDecimalUtils.ceiling(order.getOrderHsbAmount()).doubleValue() > 0, RespCode.PARAM_ERROR, "订单互生币金额[orderHsbAmount]必须大于0");
        HsAssert.notNull(order.getOrderChannel(), RespCode.PARAM_ERROR, "订单渠道[orderChannel]为空");
        HsAssert.hasText(order.getOrderOperator(), RespCode.PARAM_ERROR, "订单操作员[orderOperator]为空");

        return order;
    }

    /**
     * 修改金额参数校验
     *
     * @param annualFeeOrder 年费业务订单
     * @throws HsException
     */
    public static Order modifyAmountValidate(AnnualFeeOrder annualFeeOrder) throws HsException {
        // 实体类参为为null抛出异常
        HsAssert.notNull(annualFeeOrder, RespCode.PARAM_ERROR, "年费业务订单[annualFeeOrder]为null");
        //业务订单
        Order order = annualFeeOrder.getOrder();
        //业务订单不能为空
        HsAssert.notNull(order, RespCode.PARAM_ERROR, "业务订单[order]为null");
        //业务订单参数校验
        HsAssert.hasText(order.getOrderNo(), RespCode.PARAM_ERROR, "业务订单编号[orderNo]为空");
        HsAssert.hasText(order.getOrderOriginalAmount(), RespCode.PARAM_ERROR, "订单原始金额[orderOriginalAmount]为空");
        HsAssert.hasText(order.getOrderDerateAmount(), RespCode.PARAM_ERROR, "订单减免金额[orderDerateAmount]为空");
        HsAssert.hasText(order.getOrderCashAmount(), RespCode.PARAM_ERROR, "订单货币金额[orderCashAmount]为空");
        HsAssert.hasText(order.getOrderHsbAmount(), RespCode.PARAM_ERROR, "订单互生币金额[orderHsbAmount]为空");
        HsAssert.hasText(order.getCurrencyCode(), RespCode.PARAM_ERROR, "货币币种[currencyCode]为空");
        HsAssert.hasText(order.getOrderOperator(), RespCode.PARAM_ERROR, "订单操作员[orderOperator]为空");
        HsAssert.notNull(order.getQuantity(), RespCode.PARAM_ERROR, "订单数量[quantity]为空");

        return order;
    }

    /**
     * 修改金额参数校验
     *
     * @param annualFeeOrder 年费业务订单
     * @throws HsException
     */
    public static Order modifyPaidValidate(AnnualFeeOrder annualFeeOrder) throws HsException {
        // 实体类参为为null抛出异常
        HsAssert.notNull(annualFeeOrder, RespCode.PARAM_ERROR, "年费业务订单[annualFeeOrder]为null");
        //业务订单
        Order order = annualFeeOrder.getOrder();
        //业务订单不能为空
        HsAssert.notNull(order, RespCode.PARAM_ERROR, "业务订单[order]为null");
        //业务订单参数校验
        HsAssert.isTrue(PayChannel.checkChannel(order.getPayChannel()), RespCode.PARAM_ERROR, "支付渠道[payChannel]类型错误");
        HsAssert.isTrue(PayStatus.checkStatus(order.getPayStatus()), RespCode.PARAM_ERROR, "支付状态[payStatus]类型错误");
        HsAssert.isTrue(OrderStatus.checkStatus(order.getOrderStatus()), RespCode.PARAM_ERROR, "订单状态[orderStatus]类型错误");

        return order;
    }
}
