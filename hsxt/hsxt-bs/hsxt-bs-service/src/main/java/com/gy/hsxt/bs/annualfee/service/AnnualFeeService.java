/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO.,
 * LTD. All rights reserved.
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.bs.annualfee.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.gy.hsi.lc.client.log4j.BizLog;
import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.bs.annualfee.interfaces.IAnnualFeeDetailService;
import com.gy.hsxt.bs.annualfee.interfaces.IAnnualFeeInfoService;
import com.gy.hsxt.bs.annualfee.interfaces.IAnnualFeeOrderService;
import com.gy.hsxt.bs.annualfee.interfaces.IAnnualFeeService;
import com.gy.hsxt.bs.annualfee.utils.AnnualAreaUtils;
import com.gy.hsxt.bs.annualfee.utils.AnnualValidateUtils;
import com.gy.hsxt.bs.bean.annualfee.AnnualFeeDetail;
import com.gy.hsxt.bs.bean.annualfee.AnnualFeeInfo;
import com.gy.hsxt.bs.bean.annualfee.AnnualFeeOrder;
import com.gy.hsxt.bs.bean.annualfee.AnnualFeeOrderQuery;
import com.gy.hsxt.bs.bean.order.Order;
import com.gy.hsxt.bs.common.bean.PayUrl;
import com.gy.hsxt.bs.common.bean.PaymentNotify;
import com.gy.hsxt.bs.common.enumtype.BSRespCode;
import com.gy.hsxt.bs.common.enumtype.order.OrderStatus;
import com.gy.hsxt.bs.common.enumtype.order.OrderType;
import com.gy.hsxt.bs.common.enumtype.order.PayStatus;
import com.gy.hsxt.bs.common.interfaces.ICommonService;
import com.gy.hsxt.bs.disconf.BsConfig;
import com.gy.hsxt.bs.order.interfaces.IAccountRuleService;
import com.gy.hsxt.bs.order.interfaces.IOrderService;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.constant.CurrencyCode;
import com.gy.hsxt.common.constant.PayChannel;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.BigDecimalUtils;
import com.gy.hsxt.common.utils.HsAssert;

/**
 * 年费 实现类
 * 
 * @author : liuhq
 * @version V3.0
 * @Package : com.hsxt.bs.annualfee.service
 * @ClassName : AnnualFeeService
 * @Description :年费整个模块对外接口在此实现
 * @Date : 2015-9-28 下午2:18:58
 */
@Service
public class AnnualFeeService implements IAnnualFeeService {

    /**
     * 业务服务私有配置参数
     **/
    @Resource
    private BsConfig bsConfig;

    /**
     * 注入 年费信息接口
     */
    @Resource
    private IAnnualFeeInfoService annualFeeInfoService;

    /**
     * 注入 计费单接口
     */
    @Resource
    private IAnnualFeeDetailService annualFeeDetailService;

    /**
     * 注入 年费业务单接口
     */
    @Resource
    private IAnnualFeeOrderService annualFeeOrderService;

    /**
     * 账户规则接口
     */
    @Resource
    private IAccountRuleService accountRuleService;

    /**
     * 业务订单接口
     */
    @Resource
    private IOrderService orderService;

    /**
     * 公共参数接口
     */
    @Resource
    private ICommonService commonService;

    // ======================== 企业年费信息 开始 =========================//

    /**
     * 获取 某一个企业年费信息
     * 
     * @param entCustId
     *            企业客户号 必须 非null
     * @return 返回某一个企业年费信息， Exception失败
     * @throws HsException
     */
    @Override
    public AnnualFeeInfo queryAnnualFeeInfo(String entCustId) throws HsException {
        // 返回实体对象
        return annualFeeInfoService.queryBeanById(entCustId);
    }

    // =================== 企业年费计费单 开始 =====================//

    /**
     * 提交年费业务订单
     * 
     * @param annualFeeOrder
     *            年费业务订单
     * @return 业务订单号
     * @throws HsException
     */
    @Override
    @Transactional
    public AnnualFeeOrder submitAnnualFeeOrder(AnnualFeeOrder annualFeeOrder) throws HsException {
        // 写入系统日志
        SystemLog.debug(bsConfig.getSysName(), "function:submitAnnualFeeOrder", "提交年费业务订单--实体类参为annualFeeOrder:" + annualFeeOrder);
        // 校验所传参数
        Order order = AnnualValidateUtils.saveValidate(annualFeeOrder);

        // 1.1.1查询该企业年费信息
        AnnualFeeInfo annualFeeInfo = annualFeeInfoService.queryBeanById(order.getCustId());
        // 1.1.2当天日期应当大于等于缴费提示日期
        // HsAssert.isTrue(AnnualAreaUtils.compareToWarnDate(annualFeeInfo.getWarningDate(),
        // 0) >= 0, BSRespCode.BS_ANNUAL_FEE_NOT_TO_PAY_TIME,
        // "提交年费业务订单--未到缴费时间");
        // 校验金额
        HsAssert.isTrue(BigDecimalUtils.compareTo(order.getOrderHsbAmount(), annualFeeInfo.getArrearAmount()) == 0, RespCode.PARAM_ERROR, " 互生币金额[orderHsbAmount]错误");
        // ---------------------------上面为参数校验
        // ------------------------------------
        // 形成年费计费单列表===根据截止日期计算而来
        List<AnnualFeeDetail> details = AnnualAreaUtils.parseAnnualAreas(annualFeeInfo, true);

        HsAssert.isTrue(CollectionUtils.isNotEmpty(details), BSRespCode.BS_ANNUAL_FEE_NOT_TO_PAY_TIME, "提交年费业务订单--未到缴费时间");
        // 先校验年费业务订单
        // 判断该企业是否存在未付款的年费订单
        AnnualFeeOrder annualFeeOrderInDB = annualFeeOrderService.queryBeanForWaitPayByCustId(order.getCustId());
        // 计算订单金额
        // 当前平台的货币转换率
        String exchangeRate = commonService.getAreaPlatInfo().getExchangeRate();
        // 设置订单货币转换率
        order.setExchangeRate(exchangeRate);
        // 设置订单货币币种
        order.setCurrencyCode(CurrencyCode.HSB.getCode());
        // 原始订单金额
        order.setOrderOriginalAmount(order.getOrderHsbAmount());
        // 减免金额
        order.setOrderDerateAmount("0");
        // 订单货币金额
        String cash = BigDecimalUtils.ceilingDiv(order.getOrderHsbAmount(), exchangeRate).toPlainString();
        order.setOrderCashAmount(BigDecimalUtils.ceiling(cash, 2).toPlainString());
        // 企业名称
        order.setCustName(annualFeeInfo.getEntCustName());
        // 企业互生号
        order.setHsResNo(annualFeeInfo.getEntResNo());

        order.setQuantity(details.size());// 数量

        if (annualFeeOrderInDB.getOrder() == null)
        {
            // 创建订单记录
            annualFeeOrderService.saveBean(annualFeeOrder);

            // 创建操作日志
            BizLog.biz(bsConfig.getSysName(), "function:createAnnualFeeOrder", "params==>" + order, "年费：提交年费业务订单--创建订单记录成功");
        }
        else
        {
            order.setOrderNo(annualFeeOrderInDB.getOrder().getOrderNo());// 订单号

            // 修改订单金额和时间
            annualFeeOrderService.modifyBeanForAmount(annualFeeOrder);

            // 创建操作日志
            BizLog.biz(bsConfig.getSysName(), "function:createAnnualFeeOrder", "params==>" + order, "年费：提交年费业务订单--修改订单记录成功");
        }

        // 接着处理年费计费单
        for (AnnualFeeDetail detail : details)
        {
            detail.setEntCustId(order.getCustId());
            // 查询企业是否提交过下一个缴费区间的计费单信息
            AnnualFeeDetail detailInDB = annualFeeDetailService.queryBeanByAnnualArea(detail);
            if (detailInDB == null)
            {
                // -------------------------创建年费计费单-------------------------//
                detail.build(order);
                // 执行创建
                annualFeeDetailService.saveBean(detail);

                // 创建操作日志
                BizLog.biz(bsConfig.getSysName(), "function:createEntAnnualFeeDetail", "params==>" + detail, "创建企业年费计费单成功");
            }
        }
        // 设置缴费区间
        String startDate = AnnualAreaUtils.obtainAnnualAreaStartDate(annualFeeInfo);
        String endDate = AnnualAreaUtils.obtainAnnualAreaEndDate(annualFeeInfo, true);
        annualFeeInfo.setAreaStartDate(startDate);
        annualFeeInfo.setAreaEndDate(endDate);
        return annualFeeOrder;
    }

    /**
     * 人工提前缴纳年费（互生币直接处理完）
     * 
     * @param annualFeeOrder
     *            企业年费订单
     * @throws HsException
     */

    @Override
    @Transactional
    public String payAnnualFeeOrder(AnnualFeeOrder annualFeeOrder) throws HsException {
        // 写入系统日志-
        SystemLog.debug(bsConfig.getSysName(), "function:payAnnualFee", "人工提前缴纳年费--实体类参为annualFeeOrder:" + annualFeeOrder);
        // 实体类参为为null抛出异常
        HsAssert.notNull(annualFeeOrder, RespCode.PARAM_ERROR, "年费业务订单[annualFeeOrder]为null");
        // 业务订单
        Order order = annualFeeOrder.getOrder();
        // 业务订单不能为空
        HsAssert.notNull(order, RespCode.PARAM_ERROR, "参数业务订单[order]为null");
        // 校验客户号
        HsAssert.hasText(order.getCustId(), RespCode.PARAM_ERROR, "企业客户号[custId]为空");
        HsAssert.hasText(order.getOrderNo(), RespCode.PARAM_ERROR, "业务订单号[orderNo]为空");
        HsAssert.hasText(order.getOrderOperator(), RespCode.PARAM_ERROR, "订单操作者[orderOperator]为空");
        HsAssert.isTrue(PayChannel.checkChannel(order.getPayChannel()), RespCode.PARAM_ERROR, "支付渠道[payChannel]错误");
        Order orderInDB = annualFeeOrderService.queryBeanById(order.getOrderNo()).getOrder();
        HsAssert.notNull(orderInDB, BSRespCode.BS_ANNUAL_FEE_ORDER_NOT_EXIST, "对应的年费业务订单[" + order.getOrderNo() + "]不存在");
        HsAssert.isTrue(orderInDB.getPayStatus() != PayStatus.PAY_FINISH.getCode(), BSRespCode.BS_ORDER_IS_PAY, "业务订单[orderNo:" + order.getOrderNo() + "]已经支付");

        /******************************* 互生币支付 *********************************/

        // 若为互生币支付方式
        if (PayChannel.HS_COIN_PAY.getCode() == order.getPayChannel().intValue())
        {
            // 检查互生币支付是否符合限额限次规则
            accountRuleService.checkHsbToPayRule(orderInDB.getCustId(), orderInDB.getCustType(), orderInDB.getOrderHsbAmount());

            // 2.1.2 执行 更新企业年费信息（这个是缴费完成后调用）
            String orgEndDate = annualFeeInfoService.modifyBeanForPaid(order.getCustId(), order.getOrderNo());

            // 2.1.3 执行 更新计费单
            annualFeeDetailService.modifyBeanForPaid(order.getOrderNo());

            // 2.1.4 更新业务订单 记账分解-实时记账
            orderInDB.setPayStatus(PayStatus.PAY_FINISH.getCode());// 支付状态
            orderInDB.setOrderStatus(OrderStatus.IS_COMPLETE.getCode());// 订单状态
            orderInDB.setPayChannel(order.getPayChannel());// 支付方式
            annualFeeOrderService.modifyBeanForPaid(AnnualFeeOrder.bulid(orderInDB), orgEndDate, true);

            // 互生币支付后续处理，比如更新累计互生币支付金额、支付次数等
            accountRuleService.afterHsbToPay(orderInDB.getCustId(), orderInDB.getCustType(), orderInDB.getOrderHsbAmount());
        }

        /******************************* 网银支付 *********************************/

        // 2.2 若为网银支付/快捷支付/手机支付
        if (PayChannel.isGainPayAddress(order.getPayChannel()))
        {
            // 2.2.1 调用支付系统 获取url返回给接入
            return orderService.getPayUrl(PayUrl.bulid(orderInDB, order.getPayChannel(), annualFeeOrder.getCallbackUrl(), annualFeeOrder.getBindingNo(), annualFeeOrder.getSmsCode(), OrderType.getGoodNameByOrderType(OrderType.ANNUAL_FEE.getCode())));
        }

        return null;
    }

    /**
     * 查询年费订单详情
     * 
     * @param orderNo
     *            订单编号 非空
     * @return AnnualFeeOrder 年费订单实体
     * @throws HsException
     *             异常
     */
    @Override
    public AnnualFeeOrder queryAnnualFeeOrder(String orderNo) throws HsException {
        // 写入系统日志
        SystemLog.debug(bsConfig.getSysName(), "function:queryAnnualFeeOrder", "年费：获取年费订单详情--订单编号参数：" + orderNo);
        // 订单编号参数为空时
        HsAssert.hasText(orderNo, RespCode.PARAM_ERROR, "年费：获取年费订单详情--订单编号参数为空");

        return annualFeeOrderService.queryBeanById(orderNo);
    }

    /**
     * 分页查询年费业务单
     * 
     * @param page
     *            分页实体
     * @param annualFeeOrderQuery
     *            查询实体
     * @return 查询数据
     * @throws HsException
     */
    @Override
    public PageData<AnnualFeeOrder> queryAnnualFeeOrderForPage(Page page, AnnualFeeOrderQuery annualFeeOrderQuery) throws HsException {

        SystemLog.debug(bsConfig.getSysName(), "function:queryAnnualFeeOrderForPage", "分页查询年费业务单参数[annualFeeOrderQuery]：" + annualFeeOrderQuery);

        HsAssert.notNull(page, RespCode.PARAM_ERROR, "分页对象[page]为空");
        // 校验查询实体
        HsAssert.notNull(annualFeeOrderQuery, RespCode.PARAM_ERROR, "查询对象[annualFeeOrderQuery]为空");
        // 校验日期
        annualFeeOrderQuery.checkDateFormat();
        annualFeeOrderQuery.setOrderType(OrderType.ANNUAL_FEE.getCode());

        // 查询年费业务单
        List<AnnualFeeOrder> annualFeeOrders = annualFeeOrderService.queryListForPage(page, annualFeeOrderQuery);

        return PageData.bulid(page.getCount(), annualFeeOrders);
    }

    /**
     * 年费支付回调
     * 
     * @param paymentNotify
     *            支付通知
     * @return false or true
     * @throws HsException
     */
    @Override
    @Transactional
    public boolean callbackForPayment(PaymentNotify paymentNotify) throws HsException {
        // 1.查询业务订单
        Order order = paymentNotify.getOrder();
        String orgEndDate = "";
        // 2.1 支付成功的情况
        if (PayStatus.PAY_FINISH == paymentNotify.getPayStatus())
        {

            // 2.1.2 执行 更新企业年费信息（这个是缴费完成后调用）
            orgEndDate = annualFeeInfoService.modifyBeanForPaid(order.getCustId(), order.getOrderNo());

            // 2.1.3 执行 更新计费单
            annualFeeDetailService.modifyBeanForPaid(order.getOrderNo());

            // 2.1.4 更新业务订单
            order.setPayStatus(PayStatus.PAY_FINISH.getCode());// 支付状态 支付成功
            order.setOrderStatus(OrderStatus.IS_COMPLETE.getCode());// 订单状态

        }
        else
        { // 处理失败的情况
            // 设置业务订单相关状态
            order.setPayStatus(PayStatus.WAIT_PAY.getCode());// 支付状态 支付失败
            order.setOrderStatus(OrderStatus.WAIT_PAY.getCode());// 订单状态
        }
        order.setPayChannel(paymentNotify.getPayChannel().getCode());// 支付方式
        annualFeeOrderService.modifyBeanForPaid(AnnualFeeOrder.bulid(order), orgEndDate, true);
        return true;
    }
}
