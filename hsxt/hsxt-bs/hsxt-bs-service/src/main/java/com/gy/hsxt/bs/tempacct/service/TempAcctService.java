/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO.,
 * LTD. All rights reserved.
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.bs.tempacct.service;

import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.bs.bean.order.Order;
import com.gy.hsxt.bs.bean.tempacct.*;
import com.gy.hsxt.bs.disconf.BsConfig;
import com.gy.hsxt.bs.tempacct.interfaces.IDebitService;
import com.gy.hsxt.bs.tempacct.interfaces.ITempAcctLinkService;
import com.gy.hsxt.bs.tempacct.interfaces.ITempAcctRefundService;
import com.gy.hsxt.bs.tempacct.interfaces.ITempAcctService;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 临帐管理 实现类
 *
 * @Package : com.gy.hsxt.bs.tempacct.service
 * @ClassName : TempAcctService
 * @Description :
 * @Author : liuhq
 * @Date : 2015-10-19 上午11:14:31
 * @Version V3.0
 */
@Service
public class TempAcctService implements ITempAcctService {
    /**
     * 业务服务私有配置参数
     **/
    @Resource
    private BsConfig bsConfig;

    /**
     * 临账记录业务接口
     */
    @Resource
    private IDebitService debitService;

    /**
     * 临账退款业务接口
     */
    @Resource
    private ITempAcctRefundService tempAcctRefundService;

    /**
     * 临账关联业务接口
     */
    @Resource
    private ITempAcctLinkService tempAcctLinkService;

    // ===================== 临帐记录 开始====================

    /**
     * 创建 临帐记录
     *
     * @param debit 实体类 非null
     *              <p/>
     *              无异常便成功，Exception失败
     * @throws HsException
     */
    @Override
    public void createDebit(Debit debit) throws HsException {
        // 写入系统日志
        SystemLog.debug(bsConfig.getSysName(), "function:createDebit", "创建临帐记录--实体[debit]:" + debit);
        //创建临账记录
        debitService.saveBean(debit);
    }

    /**
     * 修改更新临账记录
     *
     * @param debit 临账实体
     * @throws HsException
     */
    @Override
    public void modifyDebit(Debit debit) throws HsException {
        // 写入系统日志
        SystemLog.debug(bsConfig.getSysName(), "function:modifyDebit", "修改更新临账记录--实体[debit]:" + debit);
        //修改临账
        debitService.modifyBean(debit);
    }

    /**
     * 分页查询 临帐记录列表
     *
     * @param page       分页对象 非null
     * @param debitQuery 查询实体对象
     * @return 返回分页的数据列表
     * @throws HsException
     */
    @Override
    public PageData<Debit> queryDebitListPage(Page page, DebitQuery debitQuery) throws HsException {
        // 写入系统日志
        SystemLog.debug(bsConfig.getSysName(), "function:queryDebitListPage", "临帐:分页查询临帐记录列表--查询实体[debitQuery]:" + debitQuery);
        //分页查询临账
        List<Debit> debits = debitService.queryListForPage(page, debitQuery);
        return PageData.bulid(page.getCount(), debits);
    }

    /**
     * 根据条件查询临账记录列表
     *
     * @param debitQuery 查询实体
     * @return {@link List}
     * @throws HsException
     */
    @Override
    public List<Debit> queryDebitListByQuery(DebitQuery debitQuery) throws HsException {
        // 写入系统日志
        SystemLog.debug(bsConfig.getSysName(), "function:queryDebitListByQuery", "临帐:根据条件查询临账记录列表--查询实体[debitQuery]:" + debitQuery);
        return debitService.queryListByQuery(debitQuery);
    }

    /**
     * 分页查询临账录入复核/退款复核列表
     *
     * @param page       分页对象 非null
     * @param debitQuery 查询实体对象
     * @return 返回分页的数据列表
     * @throws HsException
     */
    @Override
    public PageData<Debit> queryDebitTaskListPage(Page page, DebitQuery debitQuery) throws HsException {
        // 写入系统日志
        SystemLog.debug(bsConfig.getSysName(), "function:queryDebitTaskListPage", " 分页查询临账录入复核/退款复核列表查询实体[debitQuery]:" + debitQuery);
        //分页查询临账
        List<Debit> debits = debitService.queryTaskListForPage(page, debitQuery);
        return PageData.bulid(page.getCount(), debits);
    }

    /**
     * 查询某一条临帐记录的详情
     *
     * @param debitId 临账记录编号 非空
     * @return 返回实体类
     * @throws HsException
     */
    @Override
    public Debit queryDebitDetail(String debitId) throws HsException {
        // 写入系统日志
        SystemLog.debug(bsConfig.getSysName(), "function:queryDebitDetail", "查询某一条临帐记录的详情参数[debitId]:" + debitId);
        //查询某一条临帐记录的详情
        return debitService.queryBeanById(debitId);
    }

    /**
     * 复核临账录入
     *
     * @param debit 临账记录 非null
     * @throws HsException 无异常便成功
     */
    @Override
    public void approveDebit(Debit debit) throws HsException {
        // 写入系统日志
        SystemLog.debug(bsConfig.getSysName(), "function:approveDebit", "临帐:审核复核--临帐记录实体[debit]:" + debit);
        //修改临账状态
        debitService.modifyDebitStatus(debit,true);
    }

    /**
     * 分页查询临帐记录按帐户名称分组统计总金额
     *
     * @param page       分页对象
     * @param debitQuery 帐户名称 非空按条件查询
     * @return 返回分页的数据列表
     * @throws HsException
     */
    @Override
    public PageData<DebitSum> queryDebitSumListPage(Page page, DebitQuery debitQuery) throws HsException {
        // 写入系统日志
        SystemLog.debug(bsConfig.getSysName(), "function:queryDebitSumListPage", "临帐:分页查询临帐记录按帐户名称分组统计总金额--查询实体[debitQuery]:" + debitQuery);
        //分页查询临账统计结果
        List<DebitSum> debitSums = debitService.queryDebitSumListForPage(page, debitQuery);
        return PageData.bulid(page.getCount(), debitSums);
    }

    /**
     * 临账统计详情
     *
     * @param receiveAccountName 收款账户名称
     * @param debitQuery 查询实体
     * @return list
     * @throws HsException
     */
    @Override
    public List<Debit> queryDebitSumDetail(String receiveAccountName,DebitQuery debitQuery) throws HsException {
        // 写入系统日志
        SystemLog.debug(bsConfig.getSysName(), "function:queryDebitSumDetail", "查询临账统计详情-收款账户名称[receiveAccountName]:" + receiveAccountName);
        return debitService.queryDebitSumDetail(receiveAccountName,debitQuery);
    }

    /**
     * 转不动款 临帐记录
     *
     * @param debit        临账记录 非null
     * @param dblOptCustId 双签操作员客户号 非空
     *                     <p/>
     *                     无异常便成功，Exception失败
     * @throws HsException
     */
    @Override
    public void turnNotMovePayment(Debit debit, String dblOptCustId) throws HsException {
        // 写入系统日志
        SystemLog.debug(bsConfig.getSysName(), "function:turnNotMovePayment", "不动款临账互转参数[debit]:" + debit + "双签操作员客户号[dblOptCustId]" + dblOptCustId);
        //不动款与临账互转
        debitService.turnDebit(debit, dblOptCustId);
    }

    /**
     * 统计不动款的总金额
     *
     * @return 返回不动款的总金额
     * @throws HsException
     */
    @Override
    public String notMovePaymentSum() throws HsException {
        return debitService.frozenDebitSum();
    }

    // ===================== 临帐关联申请 开始====================

    /**
     * 创建临帐关联申请
     *
     * @param tempAcctLink 临帐关联申请 实体类 非null
     * @throws HsException
     */
    @Override
    public void createTempAcctLink(TempAcctLink tempAcctLink) throws HsException {
        // 写入系统日志
        SystemLog.debug(bsConfig.getSysName(), "function:createTempAcctLink", "创建临帐关联申请参数[tempAcctLink]:" + tempAcctLink);
        //创建临账关联申请
        tempAcctLinkService.saveBean(tempAcctLink);
    }

    /**
     * 分页查询临帐关联相关的业务订单
     *
     * @param page           分页对象 非null
     * @param tempOrderQuery 条件查询实体
     * @return 返回分页的数据列表
     * @throws HsException
     */
    @Override
    public PageData<Order> queryTempOrderListPage(Page page, TempOrderQuery tempOrderQuery) throws HsException {
        // 写入系统日志
        SystemLog.debug(bsConfig.getSysName(), "function:queryTempOrderListPage", "分页查询临帐关联未复核的业务订单参数[tempOrderQuery]:" + tempOrderQuery);
        //分页查询临帐关联未复核的业务订单
        List<Order> orders = tempAcctLinkService.queryTempOrderListPage(page, tempOrderQuery);

        return PageData.bulid(page.getCount(), orders);
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
    public PageData<Order> queryTempTaskListPage(Page page, TempOrderQuery tempOrderQuery) throws HsException {
        // 写入系统日志
        SystemLog.debug(bsConfig.getSysName(), "function:queryTempTaskListPage", "分页查询临帐关联未复核的业务订单参数[tempOrderQuery]:" + tempOrderQuery);
        //分页查询临帐关联未复核的业务订单
        List<Order> orders = tempAcctLinkService.queryTempTaskListPage(page, tempOrderQuery);

        return PageData.bulid(page.getCount(), orders);
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
        //查询临账记录相关联的业务订单
        return tempAcctLinkService.queryTempOrderByDebitId(debitId);
    }

    /**
     * 根据订单编号查询对应的未审核临账关联申请
     *
     * @param orderNo 业务订单号
     * @return obj
     * @throws HsException
     */
    @Override
    public TempAcctLink queryTempAcctLinkByOrderNo(String orderNo) throws HsException {
        // 写入系统日志
        SystemLog.debug(bsConfig.getSysName(), "function:queryTempAcctLinkByOrderNo", "根据订单编号查询对应的未审核临账关联申请参数[orderNo]:" + orderNo);
        //根据订单编号查询对应的临账关联申请
        return tempAcctLinkService.queryBeanByOrderNo(orderNo);
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
        // 写入系统日志
        SystemLog.debug(bsConfig.getSysName(), "function:queryTempSuccessOrderByOrderNo", "查询临账成功支付的订单详情参数[orderNo]:" + orderNo);
        //根据订单编号查询对应的临账关联申请
        return tempAcctLinkService.queryTempSuccessOrderByOrderNo(orderNo);
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
    public TempAcctLink queryTempAcctLinkDetail(String orderNo, String debitId) throws HsException {
        // 写入系统日志
        SystemLog.debug(bsConfig.getSysName(), "function:queryTempAcctLinkDetail", "查询临账关联申请信息详情参数[orderNo]:" + orderNo + ",[debitId]:" + debitId);
        return tempAcctLinkService.queryBeanDetail(orderNo,debitId);
    }

    /**
     * 查询单个临账的所有关联申请信息
     *
     * @param debitId 临账ID
     * @return {@link List < TempAcctLink >}
     * @throws HsException
     */
    @Override
    public List<TempAcctLink> queryTempAcctLinkListByDebitId(String debitId) throws HsException {
        // 写入系统日志
        SystemLog.debug(bsConfig.getSysName(), "function:queryTempAcctLinkListByDebitId", "查询单个临账的所有关联申请信息参数[debitId]:" + debitId);
        return tempAcctLinkService.queryBeanListByDebitId(debitId);
    }

    /**
     * 审批临帐关联申请
     *
     * @param tempAcctLink 临帐关联申请 实体类 非null
     * @throws HsException
     */
    @Override
    public void apprTempAcctLink(TempAcctLink tempAcctLink) throws HsException {
        // 写入系统日志
        SystemLog.debug(bsConfig.getSysName(), "function:apprTempAcctLink", "审批临帐关联申请参数[tempAcctLink]:" + tempAcctLink);
        //审批临帐关联申请
        tempAcctLinkService.modifyBean(tempAcctLink);

    }

    // ===================== 临账退款申请单 开始====================

    /**
     * 创建 临账退款申请单
     *
     * @param tempAcctRefund 实体类 非null
     *                       <p/>
     *                       无异常便成功，Exception失败
     * @throws HsException
     */
    @Override
    public void createTempAcctRefund(TempAcctRefund tempAcctRefund) throws HsException {
        // 写入系统日志
        SystemLog.debug(bsConfig.getSysName(), "function:createTempAcctRefund", "临账退款申请单[tempAcctRefund]:" + tempAcctRefund);
        //创建临账退款申请单
        tempAcctRefundService.saveBean(tempAcctRefund);
    }

    /**
     * 复核临账退款申请单
     *
     * @param tempAcctRefund 退款申请编号 非null
     * @throws HsException
     */
    @Override
    public void apprTempAcctRefund(TempAcctRefund tempAcctRefund) throws HsException {
        // 写入系统日志
        SystemLog.debug(bsConfig.getSysName(), "function:apprTempAcctRefund", "复核临账退款申请单[tempAcctRefund]:" + tempAcctRefund);
        //复核临账退款申请单
        tempAcctRefundService.modifyBean(tempAcctRefund);
    }
}
