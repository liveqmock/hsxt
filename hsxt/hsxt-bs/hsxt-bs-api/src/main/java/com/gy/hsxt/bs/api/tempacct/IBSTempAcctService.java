/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO.,
 * LTD. All rights reserved.
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.bs.api.tempacct;

import com.gy.hsxt.bs.bean.order.Order;
import com.gy.hsxt.bs.bean.tempacct.*;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;

import java.util.List;

/**
 * 临帐管理 接口类
 *
 * @Package : com.gy.hsxt.bs.api.tempacct
 * @ClassName : ITempAcctService
 * @Description :
 * @Author : liuhq
 * @Date : 2015-9-8 上午10:17:36
 * @Version V3.0
 */
public interface IBSTempAcctService {
    // ===================== 临帐记录 开始====================

    /**
     * 创建 临帐记录
     *
     * @param debit 实体类 非null
     * @throws HsException
     */
    void createDebit(Debit debit) throws HsException;

    /**
     * 修改更新临账记录
     *
     * @param debit 临账实体
     * @throws HsException
     */
    void modifyDebit(Debit debit) throws HsException;

    /**
     * 分页查询 临帐记录列表
     *
     * @param page       分页对象 非null
     * @param debitQuery 查询实体对象
     * @return 返回分页的数据列表
     * @throws HsException
     */
    PageData<Debit> queryDebitListPage(Page page, DebitQuery debitQuery) throws HsException;

    /**
     * 根据条件查询临账记录列表
     *
     * @param debitQuery 查询实体
     * @return {@link List}
     * @throws HsException
     */
    List<Debit> queryDebitListByQuery(DebitQuery debitQuery) throws HsException;

    /**
     * 分页查询临账录入复核/退款复核列表
     *
     * @param page       分页对象 非null
     * @param debitQuery 查询实体对象
     * @return 返回分页的数据列表
     * @throws HsException
     */
    PageData<Debit> queryDebitTaskListPage(Page page, DebitQuery debitQuery) throws HsException;

    /**
     * 查询某一条临帐记录的详情
     *
     * @param debitId 临账记录编号 非空
     * @return 返回实体类
     * @throws HsException
     */
    Debit queryDebitDetail(String debitId) throws HsException;

    /**
     * 审核复核 临帐记录
     *
     * @param debit 临账记录 非null
     * @throws HsException 无异常便成功
     */
    void approveDebit(Debit debit) throws HsException;

    /**
     * 临帐记录按帐户名称分组统计总金额分页查询
     *
     * @param page       分页对象
     * @param debitQuery 查询实体对象
     * @return 返回分页的数据列表
     * @throws HsException
     */
    PageData<DebitSum> queryDebitSumListPage(Page page, DebitQuery debitQuery) throws HsException;

    /**
     * 导出/查询临账统计详情
     *
     * @param receiveAccountName 收款账户名称
     * @param debitQuery 查询实体
     * @return list
     * @throws HsException
     */
    List<Debit> queryDebitSumDetail(String receiveAccountName,DebitQuery debitQuery) throws HsException;

    /**
     * 转不动款 临帐记录
     *
     * @param debit        临账记录 非null
     * @param dblOptCustId 双签操作员客户号 非空
     * @throws HsException
     */
    void turnNotMovePayment(Debit debit, String dblOptCustId) throws HsException;

    /**
     * 统计不动款的总金额
     *
     * @return 返回不动款的总金额
     * @throws HsException
     */
    String notMovePaymentSum() throws HsException;

    // ===================== 临帐关联申请 开始====================

    /**
     * 创建 临帐关联申请
     *
     * @param tempAcctLink 临帐关联申请 实体类 非null
     * @throws HsException
     */
    void createTempAcctLink(TempAcctLink tempAcctLink) throws HsException;

    /**
     * 分页查询临帐关联相关的业务订单
     *
     * @param page           分页对象 非null
     * @param tempOrderQuery 条件查询实体
     * @return 返回分页的数据列表
     * @throws HsException
     */
    PageData<Order> queryTempOrderListPage(Page page, TempOrderQuery tempOrderQuery) throws HsException;

    /**
     * 分页查询临帐关联未复核的业务订单
     *
     * @param page           分页对象 非null
     * @param tempOrderQuery 条件查询实体
     * @return 返回分页的数据列表
     * @throws HsException
     */
    PageData<Order> queryTempTaskListPage(Page page, TempOrderQuery tempOrderQuery) throws HsException;


    /**
     * 查询临账记录相关联的业务订单
     *
     * @param debitId 临账编号
     * @return list
     * @throws HsException
     */
    List<Order> queryTempOrderByDebitId(String debitId) throws HsException;

    /**
     * 根据订单编号查询对应的未审核临账关联申请
     *
     * @param orderNo 业务订单号
     * @return obj
     * @throws HsException
     */
    TempAcctLink queryTempAcctLinkByOrderNo(String orderNo) throws HsException;

    /**
     * 查询临账关联申请信息详情
     *
     * @param orderNo 业务订单号
     * @param debitId 临账ID
     * @return {@code TempAcctLink}
     * @throws HsException
     */
    TempAcctLink queryTempAcctLinkDetail(String orderNo, String debitId) throws HsException;

    /**
     * 查询单个临账的所有关联申请信息
     *
     * @param debitId 临账ID
     * @return {@link List<TempAcctLink>}
     * @throws HsException
     */
    List<TempAcctLink> queryTempAcctLinkListByDebitId(String debitId) throws HsException;

    /**
     * 复核临帐关联申请
     *
     * @param tempAcctLink 临帐关联申请 实体类 非null
     * @throws HsException
     */
    void apprTempAcctLink(TempAcctLink tempAcctLink) throws HsException;

    /**
     * 查询临账成功支付的订单详情
     *
     * @param orderNo 业务订单编号
     * @return {@code Order}
     * @throws HsException
     */
    Order queryTempSuccessOrderByOrderNo(String orderNo) throws HsException;

    // ===================== 临账退款申请单 开始====================

    /**
     * 创建 临账退款申请单
     *
     * @param tempAcctRefund 实体类 非null
     * @throws HsException
     */
    void createTempAcctRefund(TempAcctRefund tempAcctRefund) throws HsException;

    /**
     * 审批 临账退款申请单
     *
     * @param tempAcctRefund 退款申请单
     * @throws HsException
     */
    void apprTempAcctRefund(TempAcctRefund tempAcctRefund) throws HsException;

}
