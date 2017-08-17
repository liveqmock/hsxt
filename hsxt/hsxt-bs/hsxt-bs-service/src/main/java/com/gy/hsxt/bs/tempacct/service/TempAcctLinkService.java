/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.bs.tempacct.service;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.gy.hsi.lc.client.log4j.BizLog;
import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.bs.bean.base.Query;
import com.gy.hsxt.bs.bean.order.Order;
import com.gy.hsxt.bs.bean.tempacct.*;
import com.gy.hsxt.bs.common.PageContext;
import com.gy.hsxt.bs.common.enumtype.BSRespCode;
import com.gy.hsxt.bs.common.enumtype.order.OrderStatus;
import com.gy.hsxt.bs.common.enumtype.order.PayStatus;
import com.gy.hsxt.bs.common.enumtype.tempacct.DebitStatus;
import com.gy.hsxt.bs.common.enumtype.tempacct.LinkStatus;
import com.gy.hsxt.bs.common.interfaces.ICommonService;
import com.gy.hsxt.bs.common.interfaces.IPaymentNotifyService;
import com.gy.hsxt.bs.disconf.BsConfig;
import com.gy.hsxt.bs.order.interfaces.IOrderService;
import com.gy.hsxt.bs.task.bean.Task;
import com.gy.hsxt.bs.task.interfaces.ITaskService;
import com.gy.hsxt.bs.tempacct.interfaces.IDebitService;
import com.gy.hsxt.bs.tempacct.interfaces.ITempAcctLinkService;
import com.gy.hsxt.bs.tempacct.mapper.TempAcctLinkDebitMapper;
import com.gy.hsxt.bs.tempacct.mapper.TempAcctLinkMapper;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.constant.*;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.guid.GuidAgent;
import com.gy.hsxt.common.utils.BigDecimalUtils;
import com.gy.hsxt.common.utils.DateUtil;
import com.gy.hsxt.common.utils.HsAssert;
import com.gy.hsxt.gp.bean.PaymentOrderState;
import com.gy.hsxt.gp.constant.GPConstant;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

/**
 * 临账关联业务实现
 *
 * @Package :com.gy.hsxt.bs.tempacct.service
 * @ClassName : TempAcctLinkService
 * @Description : 临账关联业务实现
 * @Author : chenm
 * @Date : 2015/12/21 16:01
 * @Version V3.0.0.0
 */
@Service
public class TempAcctLinkService implements ITempAcctLinkService {

    /**
     * 业务服务私有配置参数
     **/
    @Resource
    private BsConfig bsConfig;

    /**
     * 临账关联Mapper接口
     */
    @Resource
    private TempAcctLinkMapper tempAcctLinkMapper;

    /**
     * 关联的临账实体Mapper接口
     */
    @Resource
    private TempAcctLinkDebitMapper tempAcctLinkDebitMapper;

    /**
     * 业务订单业务接口
     */
    @Resource
    private IOrderService orderService;

    /**
     * 临账业务接口
     */
    @Resource
    private IDebitService debitService;

    /**
     * 支付回调通知接口
     */
    @Resource
    private IPaymentNotifyService paymentNotifyService;

    /**
     * 工单业务接口
     */
    @Resource
    private ITaskService taskService;

    /**
     * 平台数据接口
     */
    @Resource
    private ICommonService commonService;

    /**
     * 保存实体
     *
     * @param tempAcctLink 实体
     * @return string
     * @throws HsException
     */
    @Override
    @Transactional
    public String saveBean(TempAcctLink tempAcctLink) throws HsException {
        // 写入系统日志
        SystemLog.debug(bsConfig.getSysName(), "function:saveBean", "创建临帐关联申请参数[tempAcctLink]:" + tempAcctLink);
        // 临帐关联申请实体类为null抛出异常
        HsAssert.notNull(tempAcctLink, RespCode.PARAM_ERROR, "临帐关联申请实体[tempAcctLink]为null");
        //参数校验
        HsAssert.hasText(tempAcctLink.getOrderNo(), RespCode.PARAM_ERROR, "业务订单号[orderNo]为空");
        TempAcctLink link = queryBeanByOrderNo(tempAcctLink.getOrderNo());
        HsAssert.isNull(link, BSRespCode.BS_TEMP_ACCT_LINK_EXIST, "[" + tempAcctLink.getOrderNo() + "]业务订单已有待复核临账关联存在");
        HsAssert.hasText(tempAcctLink.getCashAmount(), RespCode.PARAM_ERROR, "折合货币金额[cashAmount]为空");
        HsAssert.hasText(tempAcctLink.getTotalLinkAmount(), RespCode.PARAM_ERROR, "关联总金额[totalLinkAmount]为空");
        HsAssert.hasText(tempAcctLink.getReqOperator(), RespCode.PARAM_ERROR, "申请操作者客户号[reqOperator]为空");
        HsAssert.hasText(tempAcctLink.getReqOperatorName(), RespCode.PARAM_ERROR, "申请操作者名称[reqOperatorName]为空");
        HsAssert.hasText(tempAcctLink.getReqRemark(), RespCode.PARAM_ERROR, "申请备注[reqRemark]为空");

        // 校验关联的临账记录
        HsAssert.isTrue(CollectionUtils.isNotEmpty(tempAcctLink.getTempAcctLinkDebits()), RespCode.PARAM_ERROR, "关联临帐列表[tempAcctLinkDebits]为空");

        Order order = orderService.getOrderByNo(tempAcctLink.getOrderNo());

        HsAssert.notNull(order,BSRespCode.BS_ORDER_NOT_EXIST,"业务订单[orderNo:"+tempAcctLink.getOrderNo()+"]不存在");

        HsAssert.isTrue(order.getPayStatus() ==PayStatus.WAIT_PAY.getCode(),BSRespCode.BS_ORDER_NOT_WAIT_PAY,"业务订单[orderNo:"+tempAcctLink.getOrderNo()+"]不是待付款状态");
        order.setOrderStatus(OrderStatus.WAIT_PAY.getCode());
        order.setPayChannel(PayChannel.TRANSFER_REMITTANCE.getCode());

        //-------------------------修改业务订单的状态----------------------
        //业务订单号- 支付中状态- 支付中状态 - 转账汇款的付款方式
        orderService.updateOrderAllStatus(order);

        //-------------------------更新临账记录的状态----------------------
        //将临账的状态改为关联待审核状态
        for (TempAcctLinkDebit linkDebit : tempAcctLink.getTempAcctLinkDebits()) {
            HsAssert.hasText(linkDebit.getDebitId(), RespCode.PARAM_ERROR, "临账记录编号[debitId]为空");
            Debit debit = new Debit();
            debit.setDebitId(linkDebit.getDebitId());//临账记录编号
            debit.setDebitStatus(DebitStatus.LINK_PENDING.ordinal());//设置临账状态为 关联待复核
            debit.setUpdatedBy(tempAcctLink.getReqOperator());//申请操作人客户号
            debit.setUpdatedName(tempAcctLink.getReqOperatorName());//申请操作人名称
            debitService.modifyDebitStatus(debit,false);
        }

        // -------------------------临帐关联申请--------------------------
        try {

            //设置关联申请状态为待复核
            tempAcctLink.setApplyId(GuidAgent.getStringGuid(BizGroup.BS + bsConfig.getAppNode()));//申请编号
            tempAcctLink.setStatus(LinkStatus.PENDING.ordinal());
            int count = tempAcctLinkMapper.insertBean(tempAcctLink);

            // 创建操作日志
            BizLog.biz(bsConfig.getSysName(), "function:saveBean", "params==>" + tempAcctLink, "创建临帐关联申请成功");

            // -------------------------临帐关联临帐--------------------------
            // 遍历列表
            BigDecimal linkAllAmount = new BigDecimal(0);
            for (TempAcctLinkDebit linkDebit : tempAcctLink.getTempAcctLinkDebits()) {
                HsAssert.hasText(linkDebit.getDebitId(), RespCode.PARAM_ERROR, "临账记录编号[debitId]为空");
                HsAssert.hasText(linkDebit.getLinkAmount(), RespCode.PARAM_ERROR, "[TempAcctLinkDebit]临账使用的关联金额[linkAmount]为空");
                linkDebit.setApplyId(tempAcctLink.getApplyId());//设置申请ID
                linkDebit.setOrderNo(tempAcctLink.getOrderNo());//设置订单编号
                linkAllAmount = linkAllAmount.add(BigDecimalUtils.floor(linkDebit.getLinkAmount()));
                // 执行创建
                tempAcctLinkDebitMapper.insertBean(linkDebit);
            }
            HsAssert.isTrue(linkAllAmount.compareTo(BigDecimalUtils.ceiling(tempAcctLink.getCashAmount())) == 0, BSRespCode.BS_TEMP_ACCT_LINK_AMOUNT_NOT_EQUAL, "关联总金额与折合货币金额不相等");
            // 创建操作日志
            BizLog.biz(bsConfig.getSysName(), "function:saveBean", "params==>" + tempAcctLink.getTempAcctLinkDebits(), "创建关联临帐成功");

            //保存申请后，派送工单
            if (count == 1) {
                String platCustId = commonService.getAreaPlatCustId();
                HsAssert.hasText(platCustId, BSRespCode.BS_QUERY_AREA_PLAT_CUST_ID_ERROR, "查询地区平台客户号失败");
                //由于页面查询的是业务单的列表 , 则工单关联的应该是业务订单号
                Task task = new Task(tempAcctLink.getOrderNo(), TaskType.TASK_TYPE338.getCode(), platCustId,order.getHsResNo(),order.getCustName());
                taskService.saveTask(task);
            }

        } catch (HsException hse) {
            // 写入系统日志
            SystemLog.error(bsConfig.getSysName(), "function:saveBean", hse.getMessage(), hse);
            throw hse;
        } catch (Exception e) {
            // 写入系统日志
            SystemLog.error(bsConfig.getSysName(), "function:saveBean", "创建临帐关联失败，参数[tempAcctLink]:" + tempAcctLink, e);
            throw new HsException(BSRespCode.BS_TEMP_ACCT_LINK_DB_ERROR, "创建临帐关联失败,原因:" + e.getMessage());
        }
        return tempAcctLink.getOrderNo();
    }

    /**
     * 更新实体
     *
     * @param tempAcctLink 实体
     * @return int
     * @throws HsException
     */
    @Override
    @Transactional
    public int modifyBean(TempAcctLink tempAcctLink) throws HsException {
        // 写入系统日志
        SystemLog.debug(bsConfig.getSysName(), "function:modifyBean", "审批临帐关联申请参数[tempAcctLink]:" + tempAcctLink);
        // 临帐关联申请实体类为null抛出异常
        HsAssert.notNull(tempAcctLink, RespCode.PARAM_ERROR, "临帐关联申请实体[tempAcctLink]为null");
        TempAcctLink link = queryBeanByOrderNo(tempAcctLink.getOrderNo());
        HsAssert.notNull(link, RespCode.PARAM_ERROR, "[" + tempAcctLink.getOrderNo() + "]业务订单的待复核临账关联不存在");
        HsAssert.hasText(tempAcctLink.getOrderNo(), RespCode.PARAM_ERROR, "业务订单编号[orderNo]为空");
        // 审核状态 1：复核通过 2：复核驳回
        HsAssert.isTrue(LinkStatus.checkStatus(tempAcctLink.getStatus()), RespCode.PARAM_ERROR, "复核状态[status]参数错误");
        HsAssert.hasText(tempAcctLink.getApprOperator(), RespCode.PARAM_ERROR, "复核操作员客户号[apprOperator]为空");
        HsAssert.hasText(tempAcctLink.getApprOperatorName(), RespCode.PARAM_ERROR, "复核操作员名称[apprOperatorName]为空");
        HsAssert.hasText(tempAcctLink.getCashAmount(), RespCode.PARAM_ERROR, "折合货币金额[cashAmount]为空");
        HsAssert.isTrue(CollectionUtils.isNotEmpty(tempAcctLink.getTempAcctLinkDebits()), RespCode.PARAM_ERROR, "关联临帐关系列表[tempAcctLinkDebits]为空");

        Order order = orderService.getOrderByNo(tempAcctLink.getOrderNo());
        HsAssert.notNull(order, BSRespCode.BS_TEMP_ACCT_LINK_ORDER_NULL, "临账关联的业务订单[order]不存在");
        //审批操作完成时，更新工单状态
        String taskId = taskService.getSrcTask(tempAcctLink.getOrderNo(),tempAcctLink.getApprOperator());
        HsAssert.hasText(taskId,BSRespCode.BS_TASK_STATUS_NOT_DEALLING,"任务状态不是办理中");
        try {
            //更新关联状态
            tempAcctLink.setApplyId(link.getApplyId());
            tempAcctLinkMapper.updateBean(tempAcctLink);

            // ----------------------- 修改业务订单----------------------
            //审核通过的情况下
            if (LinkStatus.PASS.ordinal() == tempAcctLink.getStatus()) {
                HsAssert.isTrue(PayStatus.WAIT_PAY.getCode()==order.getPayStatus(),BSRespCode.BS_ORDER_NOT_WAIT_PAY,"业务订单[orderNo:"+tempAcctLink.getOrderNo()+"]不是待付款状态");
                // ------------------------ 修改临帐记录----------------------
                // 遍历列表
                for (TempAcctLinkDebit linkDebit : tempAcctLink.getTempAcctLinkDebits()) {
                    HsAssert.hasText(linkDebit.getDebitId(), RespCode.PARAM_ERROR, "临账记录编号[debitId]为空");
                    HsAssert.hasText(linkDebit.getLinkAmount(), RespCode.PARAM_ERROR, "[TempAcctLinkDebit]临账使用的关联金额[linkAmount]为空");
                    //更新临账记录
                    {
                        Debit debit = debitService.queryBeanById(linkDebit.getDebitId());
                        //校验输入的关联金额
                        int compare = BigDecimalUtils.compareTo(debit.getUnlinkAmount(), linkDebit.getLinkAmount());
                        HsAssert.isTrue(compare >= 0, RespCode.PARAM_ERROR, "[TempAcctLinkDebit]临账关联使用的关联金额[linkAmount]超出临账记录[" + linkDebit.getDebitId() + "]的未关联金额");
                        //设置临账的状态
                        if (compare > 0) {
                            debit.setDebitStatus(DebitStatus.LINKABLE.ordinal());
                        } else if (compare == 0) {
                            debit.setDebitStatus(DebitStatus.OVER.ordinal());
                        }
                        //计算未关联的金额
                        String unlinkAmount = BigDecimalUtils.floorSub(debit.getUnlinkAmount(), linkDebit.getLinkAmount()).toPlainString();
                        debit.setUnlinkAmount(BigDecimalUtils.floor(unlinkAmount, 2).toPlainString());
                        //计算已关联的金额
                        String linkAmount = BigDecimalUtils.ceilingAdd(debit.getLinkAmount(), linkDebit.getLinkAmount()).toPlainString();
                        debit.setLinkAmount(BigDecimalUtils.ceiling(linkAmount, 2).toPlainString());
                        //设置操作人
                        debit.setUpdatedBy(tempAcctLink.getApprOperator());
                        debit.setUpdatedName(tempAcctLink.getApprOperatorName());
                        //更新临账记录
                        debitService.modifyDebitStatus(debit,false);
                    }
                }
                // 创建操作日志
                BizLog.biz(bsConfig.getSysName(), "function:modifyBean", "params==>" + tempAcctLink, "复核临帐关联申请通过");
            } else { //驳回的情况
                for (TempAcctLinkDebit linkDebit : tempAcctLink.getTempAcctLinkDebits()) {
                    HsAssert.hasText(linkDebit.getDebitId(), RespCode.PARAM_ERROR, "临账记录编号[debitId]为空");
                    Debit debit = new Debit();
                    debit.setDebitStatus(DebitStatus.LINKABLE.ordinal());//还原为未关联状态
                    debit.setDebitId(linkDebit.getDebitId());//设置临账记录编号
                    debit.setUpdatedBy(tempAcctLink.getApprOperator());//设置复核操作员客户号
                    debit.setUpdatedName(tempAcctLink.getApprOperatorName());//设置复核操作员名称
                    //更新临账记录
                    debitService.modifyDebitStatus(debit,false);
                }
                // 创建操作日志
                BizLog.biz(bsConfig.getSysName(), "function:modifyBean", "params==>" + tempAcctLink, "复核临帐关联申请驳回");
            }
            //支付成功回调
            if (LinkStatus.PASS.ordinal() == tempAcctLink.getStatus()) {
                PaymentOrderState paymentOrderState = new PaymentOrderState(
                        order.getOrderNo(), //业务订单编号
                        DateUtil.getCurrentDate(),//处理时间
                        order.getOrderCashAmount(),//订单货币金额
                        order.getCurrencyCode(),//货币币种
                        PayChannel.TRANSFER_REMITTANCE.getCode(),//支付方式
                        GPConstant.PaymentStateCode.PAY_SUCCESS ,//支付状态
                        "临账支付成功");//备注

                paymentNotifyService.notifyPaymentOrderState(paymentOrderState);
            }
            taskService.updateTaskStatus(taskId, TaskStatus.COMPLETED.getCode());
            return 1;
        } catch (HsException hse) {
            // 写入系统日志
            SystemLog.error(bsConfig.getSysName(), "function:modifyBean", hse.getMessage(), hse);
            throw hse;
        } catch (Exception e) {
            // 写入系统日志
            SystemLog.error(bsConfig.getSysName(), "function:modifyBean", "审批临帐关联申请失败,参数[tempAcctLink]:" + tempAcctLink, e);
            throw new HsException(BSRespCode.BS_TEMP_ACCT_LINK_DB_ERROR, "审批临帐关联申请失败,原因:" + e.getMessage());
        }
    }

    /**
     * 根据ID查询实体
     *
     * @param id key
     * @return t
     * @throws HsException
     */
    @Override
    public TempAcctLink queryBeanById(String id) throws HsException {
        //系统日志
        SystemLog.debug(bsConfig.getSysName(), "function:queryBeanById", "根据ID查询实体体参数[applyId]:" + id);
        //参数校验
        HsAssert.hasText(id, RespCode.PARAM_ERROR, "临账关联申请编号[applyId]为空");
        try {
            TempAcctLink tempAcctLink = tempAcctLinkMapper.selectBeanById(id);
            if (tempAcctLink != null) {
                //查询业务订单
                Order order = orderService.getOrderByNo(tempAcctLink.getOrderNo());
                List<TempAcctLinkDebit> linkDebits = tempAcctLinkDebitMapper.selectBeanListById(id);
                tempAcctLink.setTempAcctLinkDebits(linkDebits);
                tempAcctLink.setOrder(order);
            }
            return tempAcctLink;
        } catch (HsException hse) {
            // 写入系统日志
            SystemLog.error(bsConfig.getSysName(), "function:queryBeanById", hse.getMessage(), hse);
            throw hse;
        } catch (Exception e) {
            // 写入系统日志
            SystemLog.error(bsConfig.getSysName(), "function:queryBeanById", "根据订单编号查询对应的临账关联实体失败，参数[applyId]:" + id, e);
            throw new HsException(BSRespCode.BS_TEMP_ACCT_LINK_DB_ERROR, "根据订单编号查询对应的临账关联实体失败,原因:" + e.getMessage());
        }
    }

    /**
     * 根据订单编号查询对应的未审核临账关联申请
     *
     * @param orderNo 订单编号
     * @return bean
     * @throws HsException
     */
    @Override
    public TempAcctLink queryBeanByOrderNo(String orderNo) throws HsException {
        //系统日志
        SystemLog.debug(bsConfig.getSysName(), "function:queryBeanByOrderNo", "根据订单编号查询对应的未审核临账关联申请参数[orderNo]:" + orderNo);
        //参数校验
        HsAssert.hasText(orderNo, RespCode.PARAM_ERROR, "业务订单编号[orderNo]为空");
        try {
            TempAcctLinkQuery tempAcctLinkQuery = new TempAcctLinkQuery();
            tempAcctLinkQuery.setOrderNo(orderNo);
            tempAcctLinkQuery.setStatus(LinkStatus.PENDING.ordinal());
            TempAcctLink tempAcctLink = tempAcctLinkMapper.selectBeanByQuery(tempAcctLinkQuery);
            if (tempAcctLink != null) {
                //查询业务订单
                Order order = orderService.getOrderByNo(tempAcctLink.getOrderNo());
                if (order != null) {
                    List<TempAcctLinkDebit> linkDebits = tempAcctLinkDebitMapper.selectBeanListById(tempAcctLink.getApplyId());
                    tempAcctLink.setTempAcctLinkDebits(linkDebits);
                }
                tempAcctLink.setOrder(order);
            }
            return tempAcctLink;
        } catch (HsException hse) {
            // 写入系统日志
            SystemLog.error(bsConfig.getSysName(), "function:queryBeanByOrderNo", hse.getMessage(), hse);
            throw hse;
        } catch (Exception e) {
            // 写入系统日志
            SystemLog.error(bsConfig.getSysName(), "function:queryBeanByOrderNo", "根据订单编号查询对应的未审核临账关联申请失败，参数[orderNo]:" + orderNo, e);
            throw new HsException(BSRespCode.BS_TEMP_ACCT_LINK_DB_ERROR, "根据订单编号查询对应的未审核临账关联申请失败,原因:" + e.getMessage());
        }
    }

    /**
     * 查询临账成功支付的订单详情
     *
     * @param orderNo 业务订单编号
     * @return {@code Order}
     * @throws HsException
     */
    @Override
    public Order queryTempSuccessOrderByOrderNo(String orderNo) throws HsException {
        //系统日志
        SystemLog.debug(bsConfig.getSysName(), "function:queryTempSuccessOrderByOrderNo", "查询临账成功支付的订单详情参数[orderNo]:" + orderNo);
        //参数校验
        HsAssert.hasText(orderNo, RespCode.PARAM_ERROR, "业务订单编号[orderNo]为空");
        try {
            TempAcctLinkQuery tempAcctLinkQuery = new TempAcctLinkQuery();
            tempAcctLinkQuery.setOrderNo(orderNo);
            tempAcctLinkQuery.setStatus(LinkStatus.PASS.ordinal());
            TempAcctLink tempAcctLink = tempAcctLinkMapper.selectBeanByQuery(tempAcctLinkQuery);
            Order order = null;
            if (tempAcctLink != null) {
                //查询业务订单
                order = orderService.getOrderByNo(tempAcctLink.getOrderNo());
                if (order != null) {
                    List<TempAcctLinkDebit> linkDebits = tempAcctLinkDebitMapper.selectBeanListById(tempAcctLink.getApplyId());
                    if (CollectionUtils.isNotEmpty(linkDebits)) {
                        for (TempAcctLinkDebit linkDebit : linkDebits) {
                            linkDebit.setRemark(tempAcctLink.getApprRemark());
                        }
                    }
                    order.setLinkDebits(linkDebits);
                }
            }
            return order;
        } catch (HsException hse) {
            // 写入系统日志
            SystemLog.error(bsConfig.getSysName(), "function:queryTempSuccessOrderByOrderNo", hse.getMessage(), hse);
            throw hse;
        } catch (Exception e) {
            // 写入系统日志
            SystemLog.error(bsConfig.getSysName(), "function:queryTempSuccessOrderByOrderNo", "查询临账成功支付的订单详情失败，参数[orderNo]:" + orderNo, e);
            throw new HsException(BSRespCode.BS_TEMP_ACCT_LINK_DB_ERROR, "查询临账成功支付的订单详情失败,原因:" + e.getMessage());
        }
    }

    /**
     * 查询临账关联申请信息详情
     *
     * @param orderNo 业务订单号
     * @param debitId 临账ID
     * @return {@code TempAcctLink}
     * @throws HsException
     */
    @Override
    public TempAcctLink queryBeanDetail(String orderNo, String debitId) throws HsException {
        // 写入系统日志
        SystemLog.debug(bsConfig.getSysName(), "function:queryBeanDetail", "查询临账关联申请信息详情参数[orderNo]:" + orderNo + ",[debitId]:" + debitId);
        //参数校验
        HsAssert.hasText(orderNo, RespCode.PARAM_ERROR, "业务订单编号[orderNo]为空");
        HsAssert.hasText(debitId, RespCode.PARAM_ERROR, "临账ID[debitId]为空");
        try {
            return tempAcctLinkMapper.selectBeanDetail(orderNo,debitId);
        } catch (Exception e) {
            // 写入系统日志
            SystemLog.error(bsConfig.getSysName(), "function:queryBeanDetail", "查询临账关联申请信息详情失败，参数[orderNo]:" + orderNo+ ",[debitId]:" + debitId, e);
            throw new HsException(BSRespCode.BS_TEMP_ACCT_LINK_DB_ERROR, "查询临账关联申请信息详情失败,原因:" + e.getMessage());
        }
    }

    /**
     * 查询单个临账的所有关联申请信息
     *
     * @param debitId 临账ID
     * @return {@link List < TempAcctLink >}
     * @throws HsException
     */
    @Override
    public List<TempAcctLink> queryBeanListByDebitId(String debitId) throws HsException {
        // 写入系统日志
        SystemLog.debug(bsConfig.getSysName(), "function:queryBeanListByDebitId", "查询单个临账的所有关联申请信息参数[debitId]:" + debitId);
        //参数校验
        HsAssert.hasText(debitId, RespCode.PARAM_ERROR, "临账ID[debitId]为空");
        try {
            List<TempAcctLink> tempAcctLinks =  tempAcctLinkMapper.selectBeanListByDebitId(debitId);
            if (CollectionUtils.isNotEmpty(tempAcctLinks)) {
                for (TempAcctLink tempAcctLink : tempAcctLinks) {
                    Order order = orderService.getOrderByNo(tempAcctLink.getOrderNo());
                    tempAcctLink.setOrder(order);
                }
            }
            return tempAcctLinks;
        } catch (Exception e) {
            // 写入系统日志
            SystemLog.error(bsConfig.getSysName(), "function:queryBeanListByDebitId", "查询单个临账的所有关联申请信息失败，参数[debitId]:" + debitId, e);
            throw new HsException(BSRespCode.BS_TEMP_ACCT_LINK_DB_ERROR, "查询单个临账的所有关联申请信息失败,原因:" + e.getMessage());
        }
    }

    /**
     * 查询实体列表
     *
     * @param query 查询实体
     * @return list
     * @throws HsException
     */
    @Override
    public List<TempAcctLink> queryListByQuery(Query query) throws HsException {
        return null;
    }

    /**
     * 分页查询列表
     *
     * @param page  分页对象
     * @param query 查询实体
     * @return list
     * @throws HsException
     */
    @Override
    public List<TempAcctLink> queryListForPage(Page page, Query query) throws HsException {
        return null;
    }

    /**
     * 分页查询临帐关联相关的业务订单
     *
     * @param page           分页对象
     * @param tempOrderQuery 查询对象
     * @return list
     * @throws HsException
     */
    @Override
    public List<Order> queryTempOrderListPage(Page page, TempOrderQuery tempOrderQuery) throws HsException {
        // 写入系统日志
        SystemLog.debug(bsConfig.getSysName(), "function:queryTempOrderListPage", "分页查询临帐关联相关的业务订单参数[tempOrderQuery]:" + tempOrderQuery);

        // 分页对象为null抛出异常
        HsAssert.notNull(page, RespCode.PARAM_ERROR, "分页对象[page]为null");

        HsAssert.notNull(tempOrderQuery, RespCode.PARAM_ERROR, "查询实体[tempOrderQuery]为null");

        //查询实体校验
        tempOrderQuery.checkDateFormat();
        tempOrderQuery.checkOrderType();
        tempOrderQuery.setOrderStatus(OrderStatus.WAIT_PAY.getCode());//待付款

        try {
            // 设置分页信息
            PageContext.setPage(page);

            // 获取当前页数据列表
            return tempAcctLinkMapper.selectTempOrderListPage(tempOrderQuery);
        } catch (Exception e) {
            // 写入系统日志
            SystemLog.error(bsConfig.getSysName(), "function:queryTempOrderListPage", "分页查询临帐关联相关的业务订单失败,参数[tempOrderQuery]:" + tempOrderQuery, e);
            throw new HsException(BSRespCode.BS_TEMP_ACCT_LINK_DB_ERROR, "分页查询临帐关联相关的业务订单失败,原因:" + e.getMessage());
        }
    }

    /**
     * 分页查询临帐关联未复核的业务订单
     *
     * @param page           分页对象 非null
     * @param tempOrderQuery 条件查询实体
     * @return 返回分页的数据列表
     * @throws HsException
     */
    @Override
    public List<Order> queryTempTaskListPage(Page page, TempOrderQuery tempOrderQuery) throws HsException {
        // 写入系统日志
        SystemLog.debug(bsConfig.getSysName(), "function:queryTempTaskListPage", "分页查询临帐关联未复核的业务订单参数[tempOrderQuery]:" + tempOrderQuery);

        // 分页对象为null抛出异常
        HsAssert.notNull(page, RespCode.PARAM_ERROR, "分页对象[page]为null");

        HsAssert.notNull(tempOrderQuery, RespCode.PARAM_ERROR, "查询实体[tempOrderQuery]为null");

        HsAssert.hasText(tempOrderQuery.getOptCustId(), RespCode.PARAM_ERROR, "操作员客户号[optCustId]为空");

        //查询实体校验
        tempOrderQuery.checkDateFormat();
        tempOrderQuery.checkOrderType();
        //支付状态为处理中
        tempOrderQuery.setPayStatus(PayStatus.WAIT_PAY.getCode());
        //待支付订单状态
        tempOrderQuery.setOrderStatus(OrderStatus.WAIT_PAY.getCode());
        //临账支付渠道
        tempOrderQuery.setPayChannel(PayChannel.TRANSFER_REMITTANCE.getCode());
        //工单状态为处理中
        tempOrderQuery.setTaskStatus(TaskStatus.DEALLING.getCode());
        //临账支付关联复核 TASK_TYPE338("338")
        tempOrderQuery.setTaskType(TaskType.TASK_TYPE338.getCode());

        try {
            // 设置分页信息
            PageContext.setPage(page);

            // 获取当前页数据列表
            return tempAcctLinkMapper.selectTempTaskListPage(tempOrderQuery);
        } catch (Exception e) {
            // 写入系统日志
            SystemLog.error(bsConfig.getSysName(), "function:queryTempTaskListPage", "分页查询临帐关联未复核的业务订单失败,参数[tempOrderQuery]:" + tempOrderQuery, e);
            throw new HsException(BSRespCode.BS_TEMP_ACCT_LINK_DB_ERROR, "分页查询临帐关联未复核的业务订单失败,原因:" + e.getMessage());
        }
    }

    /**
     * 查询临账记录相关联的业务订单
     *
     * @param debitId 临账编号
     * @return list
     * @throws HsException
     */
    @Override
    public List<Order> queryTempOrderByDebitId(String debitId) throws HsException {
        // 写入系统日志
        SystemLog.debug(bsConfig.getSysName(), "function:queryTempOrderByDebitId", "查询临账记录相关联的业务订单参数[debitId]:" + debitId);
        //校验参数
        HsAssert.hasText(debitId, RespCode.PARAM_ERROR, "临账编号[debitId]为空");

        try {
            return tempAcctLinkMapper.selectTempOrderByDebitId(debitId);
        } catch (Exception e) {
            // 写入系统日志
            SystemLog.error(bsConfig.getSysName(), "function:queryTempOrderByDebitId", "查询临账记录相关联的业务订单失败,参数[debitId]:" + debitId, e);
            throw new HsException(BSRespCode.BS_TEMP_ACCT_LINK_DB_ERROR, "查询临账记录相关联的业务订单失败,原因:" + e.getMessage());
        }
    }

}
