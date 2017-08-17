/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.ps.points.service;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.annotation.Resource;

import com.gy.hsxt.bp.client.api.BusinessParamSearch;
import com.gy.hsxt.ps.externalapi.HsecExternalApi;
import com.gy.hsxt.ps.validator.PsValidator;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gy.hsi.common.spring.transaction.TransactionHandler;
import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.ac.api.IAccountEntryService;
import com.gy.hsxt.ac.bean.AccountEntry;
import com.gy.hsxt.ac.bean.AccountWriteBack;
import com.gy.hsxt.common.constant.PayChannel;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.constant.TransType;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.BeanCopierUtils;
import com.gy.hsxt.common.utils.DateUtil;
import com.gy.hsxt.common.utils.HsAssert;
import com.gy.hsxt.gp.api.IGPNotifyService;
import com.gy.hsxt.gp.api.IGPPaymentService;
import com.gy.hsxt.gp.bean.MobilePayBean;
import com.gy.hsxt.gp.bean.PaymentOrderState;
import com.gy.hsxt.gp.bean.QuickPayBean;
import com.gy.hsxt.gp.bean.QuickPaySmsCodeBean;
import com.gy.hsxt.gp.bean.TransCashOrderState;
import com.gy.hsxt.lcs.bean.LocalInfo;
import com.gy.hsxt.lcs.client.LcsClient;
import com.gy.hsxt.ps.api.IPsPointService;
import com.gy.hsxt.ps.api.IPsQueryService;
import com.gy.hsxt.ps.bean.Back;
import com.gy.hsxt.ps.bean.BackResult;
import com.gy.hsxt.ps.bean.Cancel;
import com.gy.hsxt.ps.bean.CancelResult;
import com.gy.hsxt.ps.bean.Correct;
import com.gy.hsxt.ps.bean.Point;
import com.gy.hsxt.ps.bean.PointResult;
import com.gy.hsxt.ps.bean.QueryPosSingle;
import com.gy.hsxt.ps.bean.QueryResult;
import com.gy.hsxt.ps.bean.ReturnResult;
import com.gy.hsxt.ps.common.Compute;
import com.gy.hsxt.ps.common.Constants;
import com.gy.hsxt.ps.common.PsException;
import com.gy.hsxt.ps.common.TransTypeUtil;
import com.gy.hsxt.ps.common.ValidateModelUtil;
import com.gy.hsxt.ps.distribution.bean.Alloc;
import com.gy.hsxt.ps.distribution.bean.EntryAllot;
import com.gy.hsxt.ps.distribution.bean.PointAllot;
import com.gy.hsxt.ps.distribution.handle.BatAllocHandle;
import com.gy.hsxt.ps.distribution.handle.EntryHandle;
import com.gy.hsxt.ps.distribution.handle.PointBackHandle;
import com.gy.hsxt.ps.distribution.mapper.HsbAllocMapper;
import com.gy.hsxt.ps.distribution.mapper.PointAllocMapper;
import com.gy.hsxt.ps.distribution.service.PointAllocService;
import com.gy.hsxt.ps.points.bean.BackDetail;
import com.gy.hsxt.ps.points.bean.CancellationDetail;
import com.gy.hsxt.ps.points.bean.CorrectDetail;
import com.gy.hsxt.ps.points.bean.PointDetail;
import com.gy.hsxt.ps.points.handle.AccountHandle;
import com.gy.hsxt.ps.points.handle.PointHandle;
import com.gy.hsxt.ps.points.mapper.PointMapper;
import com.gy.hsxt.ps.validator.GeneralValidator;

/**
 * @version V3.0
 * @应用场景: 消费者线下POS机刷卡消费积分
 * @调用方: POS接入
 * @Package: com.gy.hsxt.ps.points.service
 * @ClassName: PointService
 * @Description: POS积分服务实现
 * @author: chenhongzhi
 * @date: 2015-12-8 上午9:20:21
 */
@Service
public class PointService implements IPsPointService, IGPNotifyService {

    // 验证卡片服务
    @Autowired
    private CheckCardService checkCardService;


    // 业务参数系统配置服务
    @Autowired
    private BusinessParamSearch businessParamSearch;

    // 限额验证服务
    @Autowired
    private AccountQuotaService AccountQuotaService;

    // 消费积分映射器
    @Autowired
    private PointMapper pointMapper;

    // 积分分配服务
    @Autowired
    private PointAllocService pointAllocService;

    // 积分分配映射器
    @Autowired
    private PointAllocMapper pointAllocMapper;

    // 调用互生币分配的映射器
    @Autowired
    private HsbAllocMapper hsbAllocMapper;

    // 账务系统服务
    @Autowired
    private IAccountEntryService accountEntryService;

    // 获取本平台详细信息
    @Autowired
    private LcsClient lcsClient;

    // 消费积分查询服务接口
    @Autowired
    private IPsQueryService psQueryService;

    /**
     * 支付系统：网银支付
     */
    @Resource
    private IGPPaymentService gpPaymentService;

    // start--added by liuzh on 2016-05-14
    @Autowired
    private DataSourceTransactionManager transactionMgr;

    @Autowired
    private HsecExternalApi hsecexternalapi;

    // end--added by liuzh on 2016-05-14

    /**
     * 消费积分 应用场景 消费者刷卡支付后调用此服务方法给积分同时扣消费者货款和扣企业的积分款
     *
     * @param point 积分入参对象
     * @return PointResult 返回值
     */
    @Override
    // start--commented by liuzh on 2016-05-14 修改事务处理
    // @Transactional
    // end--commented by liuzh on 2016-05-14 修改事务处理
    public PointResult point(final Point point) throws HsException {
        // add--added by liuzh on 2016-05-17
        // SystemLog.debug(this.getClass().getName(), "point","point:" +
        // JSON.toJSONString(point));
        // end--added by liuzh on 2016-05-17

        // 校验参数是否合法,不合法则抛出异常
        ValidateModelUtil.validateModel(point);

        PsValidator.rebateValidator(point, businessParamSearch);

        GeneralValidator.notNull(point.getTermRunCode(), RespCode.PS_PARAM_ERROR, "终端流水号不能为空");

        // 调用共享方法
        this.pointPublic(point);

        QueryPosSingle queryPosSingle = new QueryPosSingle();
        // 原批次号
        queryPosSingle.setBatchNo(point.getSourceBatchNo());
        // 企业客户号
        queryPosSingle.setEntCustId(point.getEntCustId());
        // 企业互生号
        queryPosSingle.setEntResNo(point.getEntResNo());
        // 设备编号
        queryPosSingle.setEquipmentNo(point.getEquipmentNo());
        // 单据码生成时间
        queryPosSingle.setSourcePosDate(point.getSourcePosDate());
        // 终端流水号
        queryPosSingle.setTermRunCode(point.getTermRunCode());

        // 创建积分返回值实体类
        PointResult pointResult = new PointResult();

        // 创建积分明细实体类
        PointDetail pointDetail = new PointDetail();

        // 创建消费积分处理类
        PointHandle pointHandle = new PointHandle();

        QueryResult queryResult = null;

        // start--added by liuzh on 2016-05-14
        Map<String, Object> addOrUpdateMap = new HashMap<>();
        List<AccountEntry> accountList;
        AccountWriteBack accountWriteBack;
        // end--added by liuzh on 2016-05-14

        if (StringUtils.isNotEmpty(point.getSourcePosDate())) {
            // POS单笔查询
            queryResult = psQueryService.singlePosQuery(queryPosSingle);
        }

        // start--modified by liuzh on 2016-05-18 修改为事务提交成功后,再执行
        boolean boo = false;
        if (TransTypeUtil.transWay(point.getTransType()).equals(Constants.POINT_HSB)) {
            // 互生币支付限额验证
            boo = AccountQuotaService.checkCustQuota(point.getPerCustId(), point.getTransAmount());
            /*
             * if (boo) { AccountQuotaService.quotaHandle(point.getPerCustId(),
             * point.getTransAmount()); }
             */
        }
        // end--commented by liuzh on 2016-05-18

        if (queryResult != null) {
            String transNo = queryResult.getTransNo();

            // 原交易流水号
            queryResult.setSourceTransNo(point.getSourceTransNo());
            // 交易类型
            queryResult.setTransType(point.getTransType());

            BeanCopierUtils.copy(point, pointDetail);

            // 把合法参数拷贝到对应的积分明细实体中
            BeanCopierUtils.copy(queryResult, pointResult);
            // 把合法参数拷贝到对应的积分明细实体中
            BeanCopierUtils.copy(queryResult, pointDetail);

            // 调用积分明细处理方法, 设置积分明细实体属性值
            pointHandle.pointDispose(pointDetail, point);

            pointDetail.setTransNo(transNo);

            // 支付状态设置
            pointDetail.setPayStatus(Constants.PAY_STATUS1);

            // 更新消费积分表支付状态
            // start--modified by liuzh on 2016-05-14
            // pointMapper.updatePointPayStatus(pointDetail);
            addOrUpdateMap.put("PointMapper.updatePointPayStatus", pointDetail);
            // end--modified by liuzh on 2016-05-14

            // 调用积分分配方法,传递积分明细对象,返回分录list
            List<Alloc> pointList = pointAllocService.allocPoint(pointDetail);

            // 调用消费积分分录数据处理方法,传入分配list,积分明细实体
            pointHandle.pointAccountData(pointList, pointDetail);

            // 改造后的分录数据
            EntryAllot entryAllot = EntryHandle.pointEntryData(pointList, Constants.HSB_POINT);

            // start--modified by liuzh on 2016-05-14
            // pointAllocService.addEntryData(entryAllot);
            addOrUpdateMap.put("PointAllocService.addEntryData", entryAllot);
            // end--modified by liuzh on 2016-05-14

            // 账务记账数据处理,传入分录分配list
            // start--modified by liuzh on 2016-05-14
            // List<AccountEntry> accountList =
            // AccountHandle.accountPointEntry(pointList);
            accountList = AccountHandle.accountPointEntry(pointList);
            // end--modified by liuzh on 2016-05-14

            // 组装账务冲正对象参数
            // start--modified by liuzh on 2016-05-14
            // AccountWriteBack accountWriteBack =
            // pointHandle.writeBackPoint(pointDetail.getTransType(),
            // pointDetail.getTransNo());
            accountWriteBack = pointHandle.writeBackPoint(pointDetail.getTransType(), pointDetail.getTransNo());
            // end--modified by liuzh on 2016-05-14

            // 调用账务接口
            // start--commented by liuzh on 2016-05-14
            // this.callAc(accountList, accountWriteBack, Constants.HSB_POINT,
            // false);
            // end--commented by liuzh on 2016-05-14

        } else {

            // 把合法参数拷贝到对应的积分明细实体中
            BeanCopierUtils.copy(point, pointDetail);

            // 调用积分明细处理方法, 设置积分明细实体属性值
            pointHandle.pointDispose(pointDetail, point);

            pointDetail.setIsOnlineRegister(Constants.IS_ONLINE_REGISTER1);

            // 支付状态设置
            pointDetail.setPayStatus(Constants.PAY_STATUS1);

            // 调用积分分配方法,传递积分明细对象,返回分录list
            List<Alloc> pointList = pointAllocService.allocPoint(pointDetail);

            // 调用消费积分分录数据处理方法,传入分配list,积分明细实体
            pointHandle.pointAccountData(pointList, pointDetail);

            // 积分明细实体绑定的数据插入到数据库积分明细表
            // start--modified by liuzh on 2016-05-14 把数据库更新操作移到另一个函数处理
            // pointMapper.point(pointDetail);
            addOrUpdateMap.put("PointMapper.point", pointDetail);
            // end----modified by liuzh on 2016-05-14

            // 改造后的分录数据
            EntryAllot entryAllot = EntryHandle.pointEntryData(pointList, Constants.HSB_POINT);

            // start--modified by liuzh on 2016-05-14
            // pointAllocService.addEntryData(entryAllot);
            addOrUpdateMap.put("PointAllocService.addEntryData", entryAllot);
            // --modified by liuzh on 2016-05-14

            // 账务记账数据处理,传入分录分配list
            // start--modified by liuzh on 2016-05-14
            // List<AccountEntry> accountList =
            // AccountHandle.accountPointEntry(pointList);
            accountList = AccountHandle.accountPointEntry(pointList);
            // end--modified by liuzh on 2016-05-14

            // 组装账务冲正对象参数
            // start--modified by liuzh on 2016-05-14
            // AccountWriteBack accountWriteBack =
            // pointHandle.writeBackPoint(point.getTransType(),
            // pointDetail.getTransNo());
            accountWriteBack = pointHandle.writeBackPoint(point.getTransType(), pointDetail.getTransNo());
            // end--modified by liuzh on 2016-05-14

            // 调用账务接口
            // start--commented by liuzh on 2016-05-14
            // this.callAc(accountList, accountWriteBack, Constants.HSB_POINT,
            // false);
            // end--commented by liuzh on 2016-05-14

        }

        //使用抵扣券
        hsecexternalapi.useCoupon(point.getPerResNo(), point.getEntResNo(), pointDetail.getTransNo(), point.getDeductionVoucher());

        // start--added by liuzh on 2016-05-14
        try {
            if (accountList != null && accountList.size() != 0) {
                // 调用账务系统记账接口实现记账
                this.callAc(accountList, accountWriteBack, Constants.HSB_POINT, false);
                commitPointTransactional(addOrUpdateMap);

                // 提交成功后,再设置单日互生币已支付金额
                if (boo) {
                    AccountQuotaService.quotaHandle(point.getPerCustId(), point.getTransAmount());
                }

                SystemLog.debug(this.getClass().getName(), "point", "operate successfully");
            }
        } catch (HsException he) {
            //撤销已使用的抵扣券
            hsecexternalapi.useCouponCancel(point.getEntResNo(), pointDetail.getTransNo(), point.getDeductionVoucher());
            // 抛出互生异常
            PsException.psThrowException(new Throwable().getStackTrace()[0], he.getErrorCode(), he.getMessage(), he);

        } catch (Exception e) {

            // 组装账务冲正对象参数
            accountWriteBack = pointHandle.writeBackPoint(pointDetail.getTransType(), pointDetail.getTransNo());

            // 调用账务系统冲正
            accountEntryService.correctSingleAccount(accountWriteBack);

            //撤销已使用的抵扣券
            hsecexternalapi.useCouponCancel(point.getEntResNo(), pointDetail.getTransNo(), point.getDeductionVoucher());

            // 抛出 异常
            PsException.psThrowException(new Throwable().getStackTrace()[0], RespCode.PS_DB_SQL_ERROR.getCode(), e
                    .getMessage(), e);
        }
        // end--added by liuzh on 2016-05-14

        // 返回数据处理,数据组装
        pointHandle.pointResultDispose(pointResult, pointDetail);

        // 返回
        return pointResult;
    }

    /**
     * 消费积分 跨地区交易
     *
     * @param point 积分入参对象
     * @return PointResult 返回值
     */
    @Override
    // start--commented by liuzh on 2016-05-14 修改事务处理
    // @Transactional
    // end--commented by liuzh on 2016-05-14 修改事务处理
    public PointResult point(final Point point, final JSONObject cardParams) throws HsException {

        // add--added by liuzh on 2016-05-17
        /*
         * SystemLog.debug(this.getClass().getName(), "point", "point:" +
         * JSON.toJSONString(point) + ",cardParams:" +
         * JSON.toJSONString(cardParams));
         */
        // end--added by liuzh on 2016-05-17

        // 校验参数是否合法,不合法则抛出异常
        ValidateModelUtil.validateModel(point);

        PsValidator.rebateValidator(point, businessParamSearch);

        // 调用共享方法
        this.pointPublic(point);

        // 判断是否异地持卡人本地消费
        if (TransTypeUtil.crossPlatform(point.getTransType()).equals(Constants.POINT_ALLOPATRY_CARD_E)) {
            checkCardService.checkCard(cardParams);
        }

        GeneralValidator.notNull(point.getTermRunCode(), RespCode.PS_PARAM_ERROR, "终端流水号不能为空");

        // start--modified by liuzh on 2016-05-18 修改为事务提交成功后,再执行
        boolean boo = false;
        if (TransTypeUtil.transWay(point.getTransType()).equals(Constants.POINT_HSB)) {
            // 互生币支付限额验证
            boo = AccountQuotaService.checkCustQuota(point.getPerCustId(), point.getTransAmount());
            /*
             * if (boo) { AccountQuotaService.quotaHandle(point.getPerCustId(),
             * point.getTransAmount()); }
             */
        }
        // end--commented by liuzh on 2016-05-18

        // 创建积分返回值实体类
        PointResult pointResult = new PointResult();

        // 创建积分明细实体类
        PointDetail pointDetail = new PointDetail();

        // 把合法参数拷贝到对应的积分明细实体中
        BeanCopierUtils.copy(point, pointDetail);

        // 创建消费积分处理类
        PointHandle pointHandle = new PointHandle();

        // start--added by liuzh on 2016-05-14
        Map<String, Object> addOrUpdateMap = new HashMap<>();
        // end--added by liuzh on 2016-05-14

        // 调用积分明细处理方法, 设置积分明细实体属性值
        pointHandle.pointDispose(pointDetail, point);

        pointDetail.setIsOnlineRegister(Constants.IS_ONLINE_REGISTER1);

        // 调用积分分配方法,传递积分明细对象,返回分录list
        List<Alloc> pointList = pointAllocService.allocPoint(pointDetail);

        // 调用消费积分分录数据处理方法,传入分配list,积分明细实体
        pointHandle.pointAccountData(pointList, pointDetail);

        // 积分明细实体绑定的数据插入到数据库积分明细表
        // start--modified by liuzh on 2016-05-14
        // pointMapper.point(pointDetail);
        addOrUpdateMap.put("PointMapper.point", pointDetail);
        // end--modified by liuzh on 2016-05-14

        // 改造后的分录数据
        EntryAllot entryAllot = EntryHandle.pointEntryData(pointList, Constants.HSB_POINT);

        // start--modified by liuzh on 2016-05-14
        // pointAllocService.addEntryData(entryAllot);
        addOrUpdateMap.put("PointAllocService.addEntryData", entryAllot);
        // end--modified by liuzh on 2016-05-14

        // 账务记账数据处理,传入分录分配list
        List<AccountEntry> accountList = AccountHandle.accountPointEntry(pointList);

        // 组装账务冲正对象参数
        AccountWriteBack accountWriteBack = pointHandle.writeBackPoint(point.getTransType(), pointDetail.getTransNo());

        // 调用账务接口
        // start--commented by liuzh on 2016-05-14
        // this.callAc(accountList, accountWriteBack, Constants.HSB_POINT,
        // false);
        // end----commented by liuzh on 2016-05-14

        //使用抵扣券
        hsecexternalapi.useCoupon(point.getPerResNo(), point.getEntResNo(), pointDetail.getTransNo(), point.getDeductionVoucher());

        // start--added by liuzh on 2016-05-14
        try {

            if (accountList != null && accountList.size() != 0) {

                // 调用账务系统记账接口实现记账
                this.callAc(accountList, accountWriteBack, Constants.HSB_POINT, false);
                commitPointTransactional(addOrUpdateMap);

                // 提交成功后,再设置单日互生币已支付金额
                if (boo) {
                    AccountQuotaService.quotaHandle(point.getPerCustId(), point.getTransAmount());
                }

                SystemLog.debug(this.getClass().getName(), "point", "operate successfully");
            }
        } catch (HsException he) {
            //撤销已使用的抵扣券
            hsecexternalapi.useCouponCancel(point.getEntResNo(), pointDetail.getTransNo(), point.getDeductionVoucher());
            // 抛出互生异常
            PsException.psThrowException(new Throwable().getStackTrace()[0], he.getErrorCode(), he.getMessage(), he);

        } catch (Exception e) {

            // 组装账务冲正对象参数
            accountWriteBack = pointHandle.writeBackPoint(pointDetail.getTransType(), pointDetail.getTransNo());

            // 调用账务系统冲正
            accountEntryService.correctSingleAccount(accountWriteBack);

            //撤销已使用的抵扣券
            hsecexternalapi.useCouponCancel(point.getEntResNo(), pointDetail.getTransNo(), point.getDeductionVoucher());

            // 抛出 异常
            PsException.psThrowException(new Throwable().getStackTrace()[0], RespCode.PS_DB_SQL_ERROR.getCode(), e
                    .getMessage(), e);
        }
        // end--added by liuzh on 2016-05-14

        // 返回数据处理,数据组装
        pointHandle.pointResultDispose(pointResult, pointDetail);

        // 返回
        return pointResult;
    }

    /**
     * 定金结单
     *
     * @param point 积分入参对象
     * @return PointResult 返回值
     */
    @Override
    @Transactional
    public PointResult earnestSettle(final Point point) throws HsException {

        // add--added by liuzh on 2016-05-17
        SystemLog.debug(this.getClass().getName(), "earnestSettle", "point:" + JSON.toJSONString(point));
        // end--added by liuzh on 2016-05-17

        Cancel cancel = new Cancel();
        PointResult pointResult = null;
        CancelResult cancelResult;
        // 初始化撤单
        BeanCopierUtils.copy(point, cancel);
        try {
            cancel.setSourceTransNo(point.getOldSourceTransNo());
            // 调用撤单
            cancelResult = this.cancelPointInside(cancel, true);
            // 调用消费积分
            pointResult = this.point(point);
            pointResult.setSourceEarnestAmount(String.valueOf(cancelResult.getTransAmount()));
        } catch (HsException e) {
            // 抛出 异常
            PsException.psThrowException(new Throwable().getStackTrace()[0], RespCode.PS_EARNEST_SETTLE_FAIL.getCode(),
                    e.getMessage(), e);
        }
        // 返回
        return pointResult;
    }

    /**
     * 退货(全部退、部分退) 应用场景 消费者完成下单确认收货后要求退货,调用此服务方法
     *
     * @param back 退货入参对象
     * @return BackResult 退货返回结果
     */
    @Override
    // start--modified by liuzh on 2016-05-14 修改事务处理
    // @Transactional
    // end--modified by liuzh on 2016-05-14 修改事务处理
    public BackResult backPoint(final Back back) throws HsException {

        // add--added by liuzh on 2016-05-17
        // SystemLog.debug(this.getClass().getName(), "backPoint","back:" +
        // JSON.toJSONString(back));
        // end--added by liuzh on 2016-05-17

        // 校验参数是否合法,不合法则抛出异常
        ValidateModelUtil.validateModel(back);

        // 创建返回结果对象
        BackResult backResult = new BackResult();

        // start--added by liuzh on 2016-05-14
        Map<String, Object> addOrUpdateMap = new HashMap<>();
        AccountWriteBack accountWriteBack = new AccountWriteBack();
        // end--added by liuzh on 2016-05-14

        // 查询原单(积分单)
        PointDetail oldOrder = null;
        try {
            oldOrder = pointMapper.queryOldOrder(back.getOldSourceTransNo(), back.getEntResNo(), back.getPerResNo());
        } catch (Exception e) {
            // 抛出 异常
            PsException.psThrowException(new Throwable().getStackTrace()[0], RespCode.PS_DB_SQL_ERROR.getCode(), e
                    .getMessage(), e);
        }

        GeneralValidator.notNull(oldOrder, RespCode.PS_ORDER_NOT_FOUND, "原订单不存在,无法退货");

        if (oldOrder.getTransStatus() == 2 || oldOrder.getTransStatus() == 6) {
            // 判断原积分单非正常状态, 则抛出互生异常
            PsException.psHsThrowException(new Throwable().getStackTrace()[0], RespCode.PS_HAS_THE_BACKATION.getCode(),
                    "已退货,无法退货");
        } else if (oldOrder.getTransStatus() == 1) {
            // 判断原积分单非正常状态, 则抛出互生异常
            PsException.psHsThrowException(new Throwable().getStackTrace()[0], RespCode.PS_HAS_THE_BACKATION.getCode(),
                    "原订单已撤单");
        } else if (oldOrder.getTransStatus() == 7) {
            PsException.psHsThrowException(new Throwable().getStackTrace()[0], RespCode.PS_EARNEST_SETTLE_BACKATION.getCode(),
                    "定金交易流水号不支持退货操作");
        }
        
        /*
           * else if (transWay3.equals(Constants.POINT_CASH)) { // 判断现金支付不能退货
           * PsException.psThrowException(new Throwable().getStackTrace()[0],
           * RespCode.PS_ERROR_IN_TRANSTYPE.getCode(), "消费积分请走撤单通道"); }
           */
        else {
            // 创建账务记账参数对象
            List<AccountEntry> accountList = null;

            // 创建退货明细实体
            BackDetail backDetail = new BackDetail();

            // 把查询的原积分单信息拷贝到退货明细实体
            BeanCopierUtils.copy(oldOrder, backDetail);

            // 把退货入参信息拷贝到退货明细实体
            BeanCopierUtils.copy(back, backDetail);

            // 创建积分处理类
            PointHandle pointHandle = new PointHandle();

            // 调用退货处理方法,传入退货明细信息,原积分订单信息
            pointHandle.backDispose(backDetail, oldOrder, back);

            // 对原积分单中的订单金额做处理
            BigDecimal pointTransAmount = oldOrder.getTransAmount();
            BigDecimal transAmount = Compute.roundFloor(pointTransAmount, Constants.SURPLUS_TWO);

            // 对退货入参的退货金额做处理(保留2位小数点，有余数截断)
            BigDecimal backTransAmount = new BigDecimal(back.getTransAmount());
            BigDecimal backAmount = Compute.roundFloor(backTransAmount, Constants.SURPLUS_TWO);

            // 部分退货修改第四位
            StringBuilder sb = new StringBuilder(oldOrder.getTransType());
            String transType = String.valueOf(sb.replace(3, 4, Constants.POINT_BUSS_TYPE2));
            backDetail.setTransType(transType);

            if (backAmount.compareTo(transAmount) == 1) {
                // 判断原积分单非正常状态, 则抛出互生异常
                PsException.psHsThrowException(new Throwable().getStackTrace()[0], RespCode.PS_BACK_AMOUNT_ERROR
                        .getCode(), "退货金额不能大于原交易金额,无法退货");

            } else if (transAmount.compareTo(backAmount) == 1
                    || oldOrder.getTransStatus() == Integer.parseInt(Constants.POINT_BUSS_TYPE6)) {
                // 判断金额是否一致,如果退货金额不一致,则是部分退货,否则为全部退货
                // 原订单金额减去退货金额
                BigDecimal subAmount = Compute.sub(transAmount, backAmount);

                // 金额格式处理(保留6位小数点，有余数截断)
                BigDecimal amount = Compute.roundFloor(subAmount, Constants.SURPLUS_TWO);
                if (Compute.mulCeiling(backAmount, oldOrder.getPointRate(), Constants.SURPLUS_TWO).compareTo(BigDecimal.valueOf(Constants.MIN_POINT)) == -1) {
                    PsException.psHsThrowException(new Throwable().getStackTrace()[0], RespCode.PS_POINT_NO_MIN
                            .getCode(), "退货金额不能小于0.1，无法退货");

                }
                // 退货明细实体设置原订单金额
                backDetail.setOldTransAmount(amount);
                backDetail.setTransAmount(new BigDecimal(back.getTransAmount()));

                // 查找分录
                EntryAllot OldEntryAllot = pointAllocService.backQueryOffLineEntry(oldOrder);

                // 调用积分分配服务,计算部分退货, 存储在分录list
                List<Alloc> allocList = pointAllocService.allocBackPoint(backDetail, oldOrder);

                pointHandle.relEntryNoHandle(allocList, OldEntryAllot);

                // 退货分录数据处理,把退货信息、原单信息设置到分录list中
                pointHandle.backEntry(allocList, backDetail, oldOrder);

                // 互生币支付退货，当退货金额*积分比例小于0.1积分时，应该扣除持卡人0.05积分，企业0.1积分。
                pointHandle.pointMInZero(backDetail);

                backDetail.setTransType(String.valueOf(sb.replace(3, 4, Constants.POINT_BUSS_TYPE6)));

                // 退货信息存储到退货明细表
                // start--modified by liuzh on 2016-05-14
                // pointMapper.backPoint(backDetail);
                addOrUpdateMap.put("PointMapper.backPoint", backDetail);
                // end--modified by liuzh on 2016-05-14

                // 改造后的分录数据结构
                EntryAllot entryAllot = EntryHandle.pointEntryData(allocList, Constants.HSB_POINT_BACK);

                entryAllot.setTransType(String.valueOf(sb.replace(3, 4, Constants.POINT_BUSS_TYPE6)));

                // 设置原分路流水号
                entryAllot.setRelEntryNo(OldEntryAllot.getEntryNo());

                entryAllot.setEntAddPointAmount(backDetail.getEntPoint());

                entryAllot.setPerSubPointAmount(backDetail.getPerPoint());
                // 写分路表
                // start--modified by liuzh on 2016-05-14
                // pointAllocService.addEntryData(entryAllot);
                addOrUpdateMap.put("PointAllocService.addEntryData", entryAllot);
                // end--modified by liuzh on 2016-05-14

                if (oldOrder.getTransAmount().compareTo(new BigDecimal(back.getTransAmount())) == 0) {
                    // 全部退完是设置原订单状态为全部退货
                    oldOrder.setStatus(Integer.parseInt(Constants.POINT_BUSS_TYPE2));

                } else {
                    // 设置原订单状态为部分退货
                    oldOrder.setStatus(Integer.parseInt(Constants.POINT_BUSS_TYPE6));
                }
                oldOrder.setTransAmount(amount);

                // 更新原订单状态
                // start--modified by liuzh on 2016-05-14
                // pointMapper.updatePoint(oldOrder);
                addOrUpdateMap.put("PointMapper.updatePoint", oldOrder);
                // end--modified by liuzh on 2016-05-14

                // 根据账务分录记账对消费积分分录数据进行处理
                accountList = AccountHandle.accountBackEntry(allocList);

                // 组装账务冲正对象参数
                // start--modified by liuzh on 2016-05-14
                // AccountWriteBack accountWriteBack =
                // pointHandle.writeBackPoint(back.getTransType(),
                // backDetail.getTransNo());
                accountWriteBack = pointHandle.writeBackPoint(back.getTransType(), backDetail.getTransNo());
                // end--modified by liuzh on 2016-05-14

                // 调用账务
                // start--commented by liuzh on 2016-05-14
                // this.callAc(accountList, accountWriteBack,
                // Constants.HSB_POINT_BACK, false);
                // end--commented by liuzh on 2016-05-14

                if (oldOrder.getIsSettle() == Constants.IS_SETTLE0) {
                    // 取退款金额
                    oldOrder.setTransAmount(backDetail.getTransAmount());
                    // 计算出退货的积分
                    oldOrder.setEntPoint(backDetail.getEntPoint());
                    // 改造后的积分分配
                    oldOrder.setBusinessType(Constants.BUSINESS_TYPE2);
                    oldOrder.setTransType(TransType.HSB_BUSINESS_POINT_BACK.getCode());
                    oldOrder.setBatchNo(DateUtil.DateToString(DateUtil.today()));
                    BatAllocHandle bat = new BatAllocHandle();
                    bat.insertUpPoint(oldOrder);
                    bat.getPointAllotData();
                    // start--modified by liuzh on 2016-05-14
                    // pointAllocService.addPointAllotData(bat.getPointList());
                    addOrUpdateMap.put("PointAllocService.addPointAllotData", bat.getPointList());
                    // end--modified by liuzh on 2016-05-14
                }

            } else {
                // 查找分录
                EntryAllot OldEntryAllot = pointAllocService.backQueryOffLineEntry(oldOrder);

                // 调用积分分配服务,计算部分退货, 存储在分录list
                List<Alloc> allocList = pointAllocService.allocBackPoint(backDetail, oldOrder);

                pointHandle.relEntryNoHandle(allocList, OldEntryAllot);

                // 退货分录数据处理,把退货信息、原单信息设置到分录list中
                pointHandle.backEntry(allocList, backDetail, oldOrder);

                // 退货信息存储到退货明细表
                // start--modified by liuzh on 2016-05-14
                // pointMapper.backPoint(backDetail);
                addOrUpdateMap.put("PointMapper.backPoint", backDetail);
                // end--modified by liuzh on 2016-05-14

                // 改造后的分录数据结构
                EntryAllot entryAllot = EntryHandle.pointEntryData(allocList, Constants.HSB_POINT_BACK);
                // 设置原分路流水号
                entryAllot.setRelEntryNo(OldEntryAllot.getEntryNo());
                // 写分路表
                // start--modified by liuzh on 2016-05-14
                // pointAllocService.addEntryData(entryAllot);
                addOrUpdateMap.put("PointAllocService.addEntryData", entryAllot);
                // end--modified by liuzh on 2016-05-14

                // 修改原单为已退货
                // start--modified by liuzh on 2016-05-14
                /*
                 * pointMapper.updateStatus(Integer.parseInt(Constants.
                 * POINT_BUSS_TYPE2), oldOrder.getTransNo(),
                 * Constants.T_PS_NDETAIL);
                 */
                addOrUpdateMap.put("PointMapper.updateStatus", new Object[]{
                        Integer.parseInt(Constants.POINT_BUSS_TYPE2), oldOrder.getTransNo(), Constants.T_PS_NDETAIL});
                // end--modified by liuzh on 2016-05-14

                // 根据账务分录记账对消费积分分录数据进行处理
                accountList = AccountHandle.accountBackEntry(allocList);
                // 组装账务冲正对象参数
                // start--modified by liuzh on 2016-05-14
                // AccountWriteBack accountWriteBack =
                // pointHandle.writeBackPoint(back.getTransType(),
                // backDetail.getTransNo());
                accountWriteBack = pointHandle.writeBackPoint(back.getTransType(), backDetail.getTransNo());
                // end--modified by liuzh on 2016-05-14

                // 调用账务
                // start--commented by liuzh on 2016-05-14
                // this.callAc(accountList, accountWriteBack,
                // Constants.HSB_POINT_BACK, false);
                // end--commented by liuzh on 2016-05-14

                if (oldOrder.getIsSettle() == Constants.IS_SETTLE0) {
                    // 积分退回
                    PointAllot pointAllot = pointAllocService.getPointAllot(oldOrder.getTransNo());
                    if (!Objects.equals(pointAllot, null)) {
                        pointAllot.setBatchNo(DateUtil.DateToString(DateUtil.today()));
                        List<PointAllot> list = new ArrayList<>();
                        list.add(PointBackHandle.setPointAllot(pointAllot));
                        // pointAllocService.addPointAllotData(list);
                        // pointAllocMapper.insertPointAllotDaily(list);
                        addOrUpdateMap.put("PointAllocService.addPointAllotData", list);

                    }
                }
            }


            // start--added by liuzh on 2016-05-14
            try {
                if (accountList != null && accountList.size() != 0) {
                    // 调用账务系统记账接口实现记账
                    this.callAc(accountList, accountWriteBack, Constants.HSB_POINT_BACK, false);
                    commitBackPointTransactional(addOrUpdateMap);

                    // start--added by liuzh on 2016-05-21 撤单减当日已支付金额
                    AccountQuotaService.cancelPointQuotaHandle(oldOrder.getPerCustId(), String.valueOf(back
                            .getTransAmount()));
                    // end--added by liuzh on 2016-05-21

                    SystemLog.debug(this.getClass().getName(), "backPoint", "operate successfully");
                }
            } catch (HsException he) {
                // 抛出互生异常
                PsException
                        .psThrowException(new Throwable().getStackTrace()[0], he.getErrorCode(), he.getMessage(), he);

            } catch (Exception e) {
                // 组装账务冲正对象参数
                accountWriteBack = pointHandle.writeBackPoint(backDetail.getTransType(), backDetail.getTransNo());

                // 调用账务系统冲正
                accountEntryService.correctSingleAccount(accountWriteBack);

                // 抛出 异常
                PsException.psThrowException(new Throwable().getStackTrace()[0], RespCode.PS_DB_SQL_ERROR.getCode(), e
                        .getMessage(), e);
            }
            // end--added by liuzh on 2016-05-14

            // 返回数据处理,数据组装
            pointHandle.backResultDispose(backResult, backDetail);
        }
        // 返回
        return backResult;
    }

    /**
     * 撤单 应用场景 消费者支付完订单后在当日可选择撤单
     *
     * @param cancel 撤单入参对象
     * @return CancelResult 撤单退货结果对象
     */
    @Override
    // start--commented by liuzh on 2016-05-17
    // @Transactional
    // end--commented by liuzh on 2016-05-17
    public CancelResult cancelPoint(final Cancel cancel) throws HsException {

        // add--added by liuzh on 2016-05-17
        // SystemLog.debug(this.getClass().getName(), "cancelPoint","cancel:" +
        // JSON.toJSONString(cancel));
        // end--added by liuzh on 2016-05-17

        return this.cancelPointInside(cancel, false);
    }

    /**
     * 撤单 应用场景 消费者支付完订单后在当日可选择撤单
     *
     * @param cancel 撤单入参对象
     * @return CancelResult 撤单退货结果对象
     */
    // start--commented by liuzh on 2016-05-17 修改事务处理
    // @Transactional
    // end--commented by liuzh on 2016-05-17 修改事务处理
    private CancelResult cancelPointInside(Cancel cancel, Boolean isHsExceptionCorrect) throws HsException {

        // add--added by liuzh on 2016-05-17
        /*
         * SystemLog.debug(this.getClass().getName(), "cancelPointInside",
         * "isHsExceptionCorrect:" + isHsExceptionCorrect + ",cancel:" +
         * JSON.toJSONString(cancel));
         */
        // end--added by liuzh on 2016-05-17

        // 校验参数是否合法,不合法则抛出异常
        ValidateModelUtil.validateModel(cancel);

        // 创建撤单返回值对象
        CancelResult cancelResult = new CancelResult();

        // start--added by liuzh on 2016-05-17
        Map<String, Object> addOrUpdateMap = new HashMap<>();
        // List<AccountEntry> accountList = new ArrayList<AccountEntry>();
        // AccountWriteBack accountWriteBack = new AccountWriteBack();
        // end--added by liuzh on 2016-05-17

        // 根据原订单号查询需要撤单的原积分单信息
        PointDetail oldOrder = null;
        try {
            oldOrder = pointMapper.queryOldOrder(cancel.getOldSourceTransNo(), cancel.getEntResNo(), cancel
                    .getPerResNo());
        } catch (Exception e) {
            // 抛出 异常
            PsException.psThrowException(new Throwable().getStackTrace()[0], RespCode.PS_DB_SQL_ERROR.getCode(), e
                    .getMessage(), e);
        }
        // 判断原单是否为空, 为空则抛出互生异常
        GeneralValidator.notNull(oldOrder, RespCode.PS_ORDER_NOT_FOUND, "原订单不存在,无法撤单");

        if (oldOrder.getIsSettle() == Constants.IS_SETTLE0) {
            // 判断原积分单非正常状态, 则抛出互生异常
            PsException.psHsThrowException(new Throwable().getStackTrace()[0], RespCode.PS_HAS_ISSETTLE_CANCELLATION
                    .getCode(), "您的订单已结算,无法撤单");
        }
        // 取原交易类型，第四位修改为撤单
        StringBuilder sb = new StringBuilder(oldOrder.getTransType());

        String transType = "";
        // start--modified by liuzh on 2016-05-17 transType的第4位, 只把退货时修改为撤单
        // 取第四位订单状态
        String transWay4 = TransTypeUtil.transStatus(oldOrder.getTransType());
        if (Constants.POINT_BUSS_TYPE7.equals(transWay4)) {
            transType = String.valueOf(sb.replace(3, 4, Constants.POINT_BUSS_TYPE9));
        } else {
            transType = String.valueOf(sb.replace(3, 4, Constants.POINT_BUSS_TYPE1));
        }

        cancel.setTransType(transType);

        // 原单取第三位交易类型
        /*
         * String oldTransWay3 =
         * TransTypeUtil.transWay(oldOrder.getTransType()); String
         * cancelTransWay3 = TransTypeUtil.transWay(cancel.getTransType());
         */
        if (oldOrder.getTransStatus() != 0 && oldOrder.getTransStatus() != 7) {
            // 判断原积分单非正常状态, 则抛出互生异常
            PsException.psHsThrowException(new Throwable().getStackTrace()[0], RespCode.PS_HAS_THE_CANCELLATION
                    .getCode(), "原订单已撤单或已退货,无法撤单");

        } /*
           * else if (!oldTransWay3.equals(cancelTransWay3)) { //判断撤单选择的交易类型是否正确
           * PsException.psThrowException(new Throwable().getStackTrace()[0],
           * RespCode.PS_ERROR_IN_TRANSTYPE.getCode(), "请选择正确的交易方式"); }
           */ else {
            // 创建撤单明细实体对象
            CancellationDetail cancellationDetail = new CancellationDetail();

            // 把原积分单信息拷贝到撤单明细实体对象中
            BeanCopierUtils.copy(oldOrder, cancellationDetail);

            // 撤单入参信息拷贝到撤单明细实体对象中
            BeanCopierUtils.copy(cancel, cancellationDetail);

            // 创建积分处理
            PointHandle pointHandle = new PointHandle();

            // 调用撤单处理,传入撤单明细实体对象、原积分单对象
            pointHandle.cancelDispose(cancellationDetail, oldOrder, cancel);

            // 根据原积分单查找原分录,存储到分录list
            Pair<List<Alloc>, EntryAllot> allocList = pointAllocService.canceQueryOffLineEntry(oldOrder);

            // 原分录数据处理,传入撤单明细实体对象、原积分单对象,返回分录信息 allocList
            pointHandle.cancelEntry(allocList.getLeft(), cancellationDetail, oldOrder);

            // 撤单明细信息存入数据库撤单表
            // start--modified by liuzh on 2016-05-17
            // pointMapper.cancelPoint(cancellationDetail);
            addOrUpdateMap.put("PointMapper.cancelPoint", cancellationDetail);
            // end--modified by liuzh on 2016-05-17

            // 改造后的分录数据结构
            EntryAllot entryAllot = EntryHandle.pointEntryData(allocList.getKey(), Constants.HSB_POINT_CANCEL);
            // 设置原分路流水号
            entryAllot.setRelEntryNo(allocList.getRight().getEntryNo());
            // start--modified by liuzh on 2016-05-17
            // pointAllocService.addEntryData(entryAllot);
            addOrUpdateMap.put("PointAllocService.addEntryData", entryAllot);
            // end--modified by liuzh on 2016-05-17

            // 修改原积分单为已撤单
            // start--modified by liuzh on 2016-05-17
            /*
             * pointMapper.updateStatus(Integer.parseInt(Constants.POINT_BUSS_TYPE1
             * ), oldOrder.getTransNo(), Constants.T_PS_NDETAIL);
             */
            addOrUpdateMap.put("PointMapper.updateStatus", new Object[]{Integer.parseInt(Constants.POINT_BUSS_TYPE1),
                    oldOrder.getTransNo(), Constants.T_PS_NDETAIL});
            // end--modified by liuzh on 2016-05-17

            // 根据账务分录记账对消费积分分录数据进行处理
            List<AccountEntry> accountList = AccountHandle.accountCancelEntry(allocList.getLeft());

            // 组装账务冲正对象参数
            AccountWriteBack accountWriteBack = pointHandle.writeBackPoint(cancel.getTransType(), cancellationDetail
                    .getTransNo());

            // 调用账务接口
            // start--commented by liuzh on 2016-05-17
            // this.callAc(accountList, accountWriteBack,
            // Constants.HSB_POINT_CANCEL, isHsExceptionCorrect);
            // end--commented by liuzh on 2016-05-17

            // start--added by liuzh on 2016-05-17
            try {
                if (accountList != null && accountList.size() != 0) {
                    // 调用账务系统记账接口实现记账
                    this.callAc(accountList, accountWriteBack, Constants.HSB_POINT_CANCEL, isHsExceptionCorrect);
                    commitCancelPointInsideTransactional(addOrUpdateMap);

                    // start--added by liuzh on 2016-05-21 撤单减当日已支付金额
                    AccountQuotaService.cancelPointQuotaHandle(oldOrder.getPerCustId(), String.valueOf(oldOrder
                            .getTransAmount()));
                    // end--added by liuzh on 2016-05-21

                    SystemLog.debug(this.getClass().getName(), "cancelPoint", "operate successfully");
                }
            } catch (HsException he) {
                // 抛出互生异常
                PsException
                        .psThrowException(new Throwable().getStackTrace()[0], he.getErrorCode(), he.getMessage(), he);

            } catch (Exception e) {
                // 组装账务冲正对象参数
                accountWriteBack = pointHandle.writeBackPoint(cancellationDetail.getTransType(), cancellationDetail
                        .getTransNo());

                // 调用账务系统冲正
                accountEntryService.correctSingleAccount(accountWriteBack);

                // 抛出 异常
                PsException.psThrowException(new Throwable().getStackTrace()[0], RespCode.PS_DB_SQL_ERROR.getCode(), e
                        .getMessage(), e);
            }
            // end--added by liuzh on 2016-05-17

            // 返回数据处理,数据组装
            pointHandle.cancelResultDispose(cancelResult, cancellationDetail);
        }
        /*
         * if (oldOrder.getIsSettle() == Constants.IS_SETTLE0 &&
         * !oldOrder.getBatchNo().equals(DateUtil.DateToString(new Date(),
         * DateUtil.DEFAULT_DATE_FORMAT))) { // 改造后的积分分配
         * oldOrder.setBusinessType(Constants.BUSINESS_TYPE1);
         * oldOrder.setTransType(TransType.HSB_BUSINESS_POINT_CANCEL.getCode());
         * BatAllocHandle bat = new BatAllocHandle(); bat.insertPoint(oldOrder);
         * bat.getPointAllotData();
         * pointAllocService.addPointAllotData(bat.getPointList()); }
         */

        // 返回
        return cancelResult;
    }

    /**
     * 预付定金冲正 修改记录: 修改冲正的事务处理 by liuzh on 2016-05-17
     *
     * @param correct 冲正参数
     * @return returnPoint
     */
    @Override
    // start--commmented by liuzh on 2016-05-17 修改事务处理 returnPoint
    // @Transactional
    // end--commmented by liuzh on 2016-05-17 修改事务处理 returnPoint
    public ReturnResult returnPoint(final Correct correct) throws HsException {

        // add--added by liuzh on 2016-05-17
        // SystemLog.debug(this.getClass().getName(), "returnPoint","correct:" +
        // JSON.toJSONString(correct));
        // end--added by liuzh on 2016-05-17

        // 校验参数是否合法,不合法则抛出异常
        ValidateModelUtil.validateModel(correct);

        // 创建冲正返回值对象
        ReturnResult returnResult = new ReturnResult();

        // 创建冲正明细实体对象
        CorrectDetail correctDetail = new CorrectDetail();

        // 封装冲正账务对象
        List<AccountWriteBack> accountWriteBackList = new ArrayList<>();

        // 创建积分明细实体对象
        PointDetail oldOrder;

        AccountWriteBack accountWriteBack;

        // start--added by liuzh on 2016-05-14
        Map<String, Object> addOrUpdateMap = new HashMap<>();
        List<PointDetail> oldOrderList = new ArrayList<PointDetail>();
        // end--added by liuzh on 2016-05-14

        // 创建数组,存储积分表名、撤单表名、退货表名

        String[] arr = {Constants.T_PS_BDETAIL, Constants.T_PS_CDETAIL, Constants.T_PS_NDETAIL};

        // 取第四位交易类型
        String transWay4 = TransTypeUtil.transStatus(correct.getTransType());
        // 只做撤单
        if (transWay4.equals(Constants.POINT_BUSS_TYPE1) || transWay4.equals(Constants.POINT_BUSS_TYPE9)) {
            arr = new String[]{Constants.T_PS_CDETAIL};
        }
        // 正常单
        if (transWay4.equals(Constants.POINT_BUSS_TYPE0) || transWay4.equals(Constants.POINT_BUSS_TYPE7)) {
            arr = new String[]{Constants.T_PS_NDETAIL};
        }

        // 定金结单
        if (transWay4.equals(Constants.POINT_BUSS_TYPE8)) {
            arr = new String[]{Constants.T_PS_NDETAIL, Constants.T_PS_CDETAIL};
        }

        // 退货
        if (transWay4.equals(Constants.POINT_BUSS_TYPE2)) {
            arr = new String[]{Constants.T_PS_BDETAIL};
        }

        // 动态循环查找需要冲正的单
        for (String tableName : arr) {
            // 扫描查找积分、撤单、退货
            oldOrder = pointMapper.queryOldOrderAll(correct, tableName);
            if (oldOrder == null) {
                continue;
            }

            // start--added by liuzh 2016-05-17
            oldOrderList.add(oldOrder);
            // end--added by liuzh 2016-05-17

            // 把查找到的订单信息拷贝到冲正明细实体对象中
            BeanCopierUtils.copy(oldOrder, correctDetail);

            // 把冲正入参信息拷贝到冲正明细实体对象中
            BeanCopierUtils.copy(correct, correctDetail);

            // 创建积分处理
            PointHandle pointHandle = new PointHandle();

            // 调用冲正明细处理,传入冲正入参信息、冲正明细信息
            pointHandle.correctDetailDispose(correctDetail, correct);

            // 根据原积分单查找原分录,返回处理后的分录correctList
            Pair<List<Alloc>, EntryAllot> correctList = pointAllocService.allocReturnPoint(correctDetail, oldOrder);

            // 调用冲正分录数据处理,传入冲正明细信息、原积分单信息,返回处理后的分录信息correctList
            pointHandle.correctEntry(correctList.getLeft(), correctDetail, oldOrder);

            // 冲正信息存储到数据库冲正表
            // start--modified by liuzh on 2016-05-17
            // pointMapper.returnPoint(correctDetail);
            addOrUpdateMap.put(tableName + ".PointMapper.returnPoint", correctDetail);
            // end--modified by liuzh on 2016-05-17

            // 分录信息存储到数据库分录表
            // 改造后的分录数据结构
            EntryAllot entryAllot = EntryHandle.pointEntryData(correctList.getLeft(), Constants.HSB_POINT_CORRECT);
            // 设置原分路流水号
            entryAllot.setRelEntryNo(correctList.getRight().getEntryNo());
            // start--modified by liuzh on 2016-05-17
            // pointAllocService.addEntryData(entryAllot);
            addOrUpdateMap.put(tableName + ".PointAllocService.addEntryData", entryAllot);
            // end--modified by liuzh on 2016-05-17

            // 修改分路原单冲正状态
            // start--modified by liuzh on 2016-05-17
            // pointAllocService.updatePosHsbWriteBack(entryAllot.getWriteBack(),
            // entryAllot.getRelEntryNo());
            addOrUpdateMap.put(tableName + ".PointAllocService.updatePosHsbWriteBack", new Object[]{
                    entryAllot.getWriteBack(), entryAllot.getRelEntryNo()});
            // end--modified by liuzh on 2016-05-17

            // 判断是积分冲正、退货冲正、撤单冲正?
            if (tableName.equals(Constants.T_PS_NDETAIL)) {
                // 判断如果是积分冲正,则修改积分单为已冲正
                // start--modified by liuzh on 2016-05-17
                /*
                 * 原代码逻辑 pointMapper.updateStatus(Integer.parseInt(Constants.
                 * ALREADY_CORRECT11), oldOrder.getTransNo(),
                 * Constants.T_PS_NDETAIL);
                 */
                addOrUpdateMap.put(tableName + ".PointMapper.updateStatus", new Object[]{
                        Integer.parseInt(Constants.ALREADY_CORRECT11), oldOrder.getTransNo(), Constants.T_PS_NDETAIL});
                // end--modified by liuzh on 2016-05-17
            } else {
                // 判断如果是退货或者撤单冲正,则修改退货单或者撤单为已冲正
                // start--modified by liuzh on 2016-05-17
                /*
                 * 原代码逻辑 pointMapper.updateStatus(Integer.parseInt(Constants.
                 * ALREADY_CORRECT11), oldOrder.getTransNo(), tableName);
                 */
                addOrUpdateMap.put(tableName + ".PointMapper.updateStatus", new Object[]{
                        Integer.parseInt(Constants.ALREADY_CORRECT11), oldOrder.getTransNo(), tableName});
                // end--modified by liuzh on 2016-05-17

                // 修改原积分单为正常
                // start--modified by liuzh on 2016-05-17
                /*
                 * 原代码逻辑
                 * pointMapper.updatePointStatus(Integer.parseInt(Constants
                 * .POINT_BUSS_TYPE0), oldOrder.getTransNo(), tableName);
                 */
                addOrUpdateMap.put(tableName + ".PointMapper.updatePointStatus", new Object[]{
                        Integer.parseInt(Constants.POINT_BUSS_TYPE0), oldOrder.getTransNo(), tableName});
                // end--modified by liuzh on 2016-05-17
            }

            // 根据账务分录记账对消费积分分录数据进行处理
            accountWriteBack = AccountHandle.correctAccount(correctList.getLeft(), correct);
            accountWriteBackList.add(accountWriteBack);
            // 返回数据处理,数据组装
            // start--commented by liuzh on 2016-05-17
            // pointHandle.correctResultDispose(returnResult, oldOrder);
            // end--commented by liuzh on 2016-05-17
        }

        // start--added by liuzh on 2016-05-17
        // 返回数据处理,数据组装
        PointHandle pointHandle = new PointHandle();
        for (PointDetail oldOrderRow : oldOrderList) {
            pointHandle.correctResultDispose(returnResult, oldOrderRow);
        }
        // end--added by liuzh on 2016-05-17

        try {
            if (!CollectionUtils.isEmpty(accountWriteBackList)) {
                // 调用账务系统记账接口实现记账
                accountEntryService.correctAccountList(accountWriteBackList);
                // start--added by liuzh on 2016-05-17
                String[] tableNames = arr;
                commitReturnPointTransactional(tableNames, addOrUpdateMap);

                SystemLog.debug(this.getClass().getName(), "returnPoint", "operate successfully");
                // end--added by liuzh on 2016-05-17
            }
        } catch (HsException he) {
            // 抛出互生异常
            PsException.psThrowException(new Throwable().getStackTrace()[0], he.getErrorCode(), he.getMessage(), he);

        } catch (Exception e) {
            if (!CollectionUtils.isEmpty(accountWriteBackList)) {
                // 调用账务系统冲正记账
                // 调用账务系统记账接口实现记账
                accountEntryService.correctAccountList(accountWriteBackList);
            }

            // 抛出 dubbo 异常
            PsException.psThrowException(new Throwable().getStackTrace()[0], RespCode.PS_AC_ERROR.getCode(), e
                    .getMessage(), e);
        }

        // 返回
        return returnResult;
    }

    /*
     * commented by liuzh on 2016-05-17 原代码逻辑 before 2016-05-17
     * 
     * @Override
     * 
     * @Transactional public ReturnResult returnPoint(final Correct correct)
     * throws HsException { // 校验参数是否合法,不合法则抛出异常
     * ValidateModelUtil.validateModel(correct);
     * 
     * // 创建冲正返回值对象 ReturnResult returnResult = new ReturnResult();
     * 
     * // 创建冲正明细实体对象 CorrectDetail correctDetail = new CorrectDetail();
     * 
     * //封装冲正账务对象 List<AccountWriteBack> accountWriteBackList = new
     * ArrayList<>();
     * 
     * // 创建积分明细实体对象 PointDetail oldOrder;
     * 
     * AccountWriteBack accountWriteBack;
     * 
     * // 创建数组,存储积分表名、撤单表名、退货表名
     * 
     * String[] arr = {Constants.T_PS_BDETAIL, Constants.T_PS_CDETAIL,
     * Constants.T_PS_NDETAIL};
     * 
     * //取第四位交易类型 String transWay4 =
     * TransTypeUtil.transStatus(correct.getTransType()); //只做撤单 if
     * (transWay4.equals(Constants.POINT_BUSS_TYPE1) ||
     * transWay4.equals(Constants.POINT_BUSS_TYPE9)) { arr = new
     * String[]{Constants.T_PS_CDETAIL}; } //正常单 if
     * (transWay4.equals(Constants.POINT_BUSS_TYPE0) ||
     * transWay4.equals(Constants.POINT_BUSS_TYPE7)) { arr = new
     * String[]{Constants.T_PS_NDETAIL}; }
     * 
     * //定金结单 if (transWay4.equals(Constants.POINT_BUSS_TYPE8)) { arr = new
     * String[]{Constants.T_PS_NDETAIL, Constants.T_PS_CDETAIL}; }
     * 
     * //退货 if (transWay4.equals(Constants.POINT_BUSS_TYPE2)) { arr = new
     * String[]{Constants.T_PS_BDETAIL}; }
     * 
     * // 动态循环查找需要冲正的单 for (String tableName : arr) { // 扫描查找积分、撤单、退货 oldOrder =
     * pointMapper.queryOldOrderAll(correct, tableName); if (oldOrder == null) {
     * continue; } // 把查找到的订单信息拷贝到冲正明细实体对象中 BeanCopierUtils.copy(oldOrder,
     * correctDetail);
     * 
     * // 把冲正入参信息拷贝到冲正明细实体对象中 BeanCopierUtils.copy(correct, correctDetail);
     * 
     * // 创建积分处理 PointHandle pointHandle = new PointHandle();
     * 
     * // 调用冲正明细处理,传入冲正入参信息、冲正明细信息
     * pointHandle.correctDetailDispose(correctDetail, correct);
     * 
     * // 根据原积分单查找原分录,返回处理后的分录correctList Pair<List<Alloc>, EntryAllot>
     * correctList = pointAllocService.allocReturnPoint(correctDetail,
     * oldOrder);
     * 
     * // 调用冲正分录数据处理,传入冲正明细信息、原积分单信息,返回处理后的分录信息correctList
     * pointHandle.correctEntry(correctList.getLeft(), correctDetail, oldOrder);
     * 
     * // 冲正信息存储到数据库冲正表 pointMapper.returnPoint(correctDetail);
     * 
     * // 分录信息存储到数据库分录表 // 改造后的分录数据结构 EntryAllot entryAllot =
     * EntryHandle.pointEntryData(correctList.getLeft(),
     * Constants.HSB_POINT_CORRECT); //设置原分路流水号
     * entryAllot.setRelEntryNo(correctList.getRight().getEntryNo());
     * pointAllocService.addEntryData(entryAllot); //修改分路原单冲正状态
     * pointAllocService.updatePosHsbWriteBack(entryAllot.getWriteBack(),
     * entryAllot.getRelEntryNo()); // 判断是积分冲正、退货冲正、撤单冲正? if
     * (tableName.equals(Constants.T_PS_NDETAIL)) { // 判断如果是积分冲正,则修改积分单为已冲正
     * pointMapper.updateStatus(Integer.parseInt(Constants.ALREADY_CORRECT11),
     * oldOrder.getTransNo(), Constants.T_PS_NDETAIL); } else { //
     * 判断如果是退货或者撤单冲正,则修改退货单或者撤单为已冲正
     * pointMapper.updateStatus(Integer.parseInt(Constants.ALREADY_CORRECT11),
     * oldOrder.getTransNo(), tableName);
     * 
     * // 修改原积分单为正常
     * pointMapper.updatePointStatus(Integer.parseInt(Constants.POINT_BUSS_TYPE0
     * ), oldOrder.getTransNo(), tableName); }
     * 
     * // 根据账务分录记账对消费积分分录数据进行处理 accountWriteBack =
     * AccountHandle.correctAccount(correctList.getLeft(), correct);
     * accountWriteBackList.add(accountWriteBack); // 返回数据处理,数据组装
     * pointHandle.correctResultDispose(returnResult, oldOrder); } try { if
     * (!CollectionUtils.isEmpty(accountWriteBackList)) { // 调用账务系统记账接口实现记账
     * accountEntryService.correctAccountList(accountWriteBackList); } } catch
     * (HsException he) { // 抛出互生异常 PsException.psThrowException(new
     * Throwable().getStackTrace()[0], he.getErrorCode(), he.getMessage());
     * 
     * } catch (Exception e) { if
     * (!CollectionUtils.isEmpty(accountWriteBackList)) { // 调用账务系统冲正记账 //
     * 调用账务系统记账接口实现记账
     * accountEntryService.correctAccountList(accountWriteBackList); }
     * 
     * // 抛出 dubbo 异常 PsException.psThrowException(new
     * Throwable().getStackTrace()[0], RespCode.PS_AC_ERROR.getCode(),
     * e.getMessage()); }
     * 
     * 
     * // 返回 return returnResult; }
     */

    /**
     * 网上积分登记
     *
     * @param point 积分参数
     * @throws HsException
     * @see com.gy.hsxt.ps.api.IPsPointService#pointRegister(com.gy.hsxt.ps.bean.Point)
     */
    @Override
    // start--commented by liuzh on 2016-05-17
    // @Transactional
    // end--commented by liuzh on 2016-05-17
    public PointResult pointRegister(final Point point) throws HsException {

        // add--added by liuzh on 2016-05-17
        // SystemLog.debug(this.getClass().getName(), "pointRegister","point:" +
        // JSON.toJSONString(point));
        // end--added by liuzh on 2016-05-17

        // 校验参数是否合法,不合法则抛出异常
        ValidateModelUtil.validateModel(point);

        PsValidator.rebateValidator(point, businessParamSearch);

        // 调用共享方法
        this.pointPublic(point);

        // 创建积分返回值实体类
        PointResult pointResult = new PointResult();

        // 创建积分明细实体类
        PointDetail pointDetail = new PointDetail();

        // start--added by liuzh on 2016-05-17
        Map<String, Object> addOrUpdateMap = new HashMap<>();
        // end--added by liuzh on 2016-05-14

        // 把合法参数拷贝到对应的积分明细实体中
        BeanCopierUtils.copy(point, pointDetail);

        // 创建消费积分处理类
        PointHandle pointHandle = new PointHandle();

        // 调用积分明细处理方法, 设置积分明细实体属性值
        pointHandle.pointDispose(pointDetail, point);

        // 设置为网上积分登记
        pointDetail.setIsOnlineRegister(Constants.IS_ONLINE_REGISTER0);

        // 调用积分分配方法,传递积分明细对象,返回分录list
        List<Alloc> pointList = pointAllocService.allocPoint(pointDetail);

        // 调用消费积分分录数据处理方法,传入分配list,积分明细实体
        pointHandle.pointAccountData(pointList, pointDetail);

        // 积分明细实体绑定的数据插入到数据库积分明细表
        // start--modified by liuzh on 2016-05-17
        // pointMapper.point(pointDetail);
        addOrUpdateMap.put("PointMapper.point", pointDetail);
        // end--modified by liuzh on 2016-05-17

        // 改造后的分录数据
        EntryAllot entryAllot = EntryHandle.pointEntryData(pointList, Constants.HSB_POINT);
        // start--modified by liuzh on 2016-05-17
        // pointAllocService.addEntryData(entryAllot);
        addOrUpdateMap.put("PointAllocService.addEntryData", entryAllot);
        // end--modified by liuzh on 2016-05-17

        // 账务记账数据处理,传入分录分配list
        List<AccountEntry> accountList = AccountHandle.accountPointEntry(pointList);

        // 组装账务冲正对象参数
        AccountWriteBack accountWriteBack = pointHandle.writeBackPoint(point.getTransType(), pointDetail.getTransNo());

        // 调用账务接口
        // start--commented by liuzh on 2016-05-17
        // this.callAc(accountList, accountWriteBack, Constants.HSB_POINT,
        // false);
        // end--commented by liuzh on 2016-05-17

        //使用抵扣券
        hsecexternalapi.useCoupon(point.getPerResNo(), point.getEntResNo(), pointDetail.getTransNo(), point.getDeductionVoucher());

        // start--added by liuzh on 2016-05-17
        try {
            if (accountList != null && accountList.size() != 0) {
                // 调用账务系统记账接口实现记账
                this.callAc(accountList, accountWriteBack, Constants.HSB_POINT, false);

                commitPointRegisterTransactional(addOrUpdateMap);

                SystemLog.debug(this.getClass().getName(), "pointRegister", "operate successfully");
            }
        } catch (HsException he) {
            //撤销已使用的抵扣券
            hsecexternalapi.useCouponCancel(point.getEntResNo(), pointDetail.getTransNo(), point.getDeductionVoucher());
            // 抛出互生异常
            PsException.psThrowException(new Throwable().getStackTrace()[0], he.getErrorCode(), he.getMessage(), he);

        } catch (Exception e) {

            // 组装账务冲正对象参数
            accountWriteBack = pointHandle.writeBackPoint(pointDetail.getTransType(), pointDetail.getTransNo());

            // 调用账务系统冲正
            accountEntryService.correctSingleAccount(accountWriteBack);

            //撤销已使用的抵扣券
            hsecexternalapi.useCouponCancel(point.getEntResNo(), pointDetail.getTransNo(), point.getDeductionVoucher());

            // 抛出 异常
            PsException.psThrowException(new Throwable().getStackTrace()[0], RespCode.PS_DB_SQL_ERROR.getCode(), e
                    .getMessage(), e);
        }
        // end--added by liuzh on 2016-05-17

        // 返回数据处理,数据组装
        pointHandle.pointResultDispose(pointResult, pointDetail);

        // 返回
        return pointResult;
    }

    /**
     * point抽取方法A
     *
     * @param point 积分参数
     */
    private void pointPublic(Point point) throws HsException {
        // 查找货币转换率
        LocalInfo localInfo = lcsClient.getLocalInfo();

        GeneralValidator.verifyObjectIsNotEmpty(localInfo, "获取本平台详细信息为空!");

        GeneralValidator.verifyIsNotEmpty(localInfo.getExchangeRate(), "货币转换比率为空!");
        // 货币转换率
        point.setCurrencyRate(localInfo.getExchangeRate());

        // 交易类型
        String transType = point.getTransType();

        // 积分比率
        String pointRate = point.getPointRate();
        // 取第三位，判断是否是互生币支付，如果不是，货币转换
        if (GeneralValidator.verifyTransType(point.getTransType(), Constants.POINT_HSB, 3)) {
            GeneralValidator.verifyIsNotEmpty(point.getTransAmount(), "交易金额为空!");
        } else {
            point.setTransAmount(String.valueOf(Compute.mulFloor(new BigDecimal(point.getSourceTransAmount()),
                    new BigDecimal(localInfo.getExchangeRate()), Constants.SURPLUS_TWO)));
        }

        if ((!Constants.POINT_BUSS_TYPE7.equals(TransTypeUtil.transStatus(transType)))) {

            GeneralValidator.verifyIsNotEmpty(pointRate, "积分比例不能为空!");
            // 判断积分是否小于积分最小值
            BigDecimal transAmount;
            if (StringUtils.isNotBlank(StringUtils.trimToEmpty(point.getOrderAmount()))) {
                transAmount = new BigDecimal(StringUtils.trimToEmpty(point.getOrderAmount()));
            } else {
                transAmount = new BigDecimal(StringUtils.trimToEmpty(point.getTransAmount()));
            }
            GeneralValidator.verifyMixPoint(transAmount,
                    new BigDecimal(StringUtils.trimToEmpty(pointRate)));

        }// 没有传的时候给个默认值0
        else {
            point.setPointRate(String.valueOf(0));
        }
    }

    /**
     * modified by liuzh on 2016-05-18
     *
     * @param accountList          给账务的参数
     * @param accountWriteBack     组装账务冲正对象参数
     * @param transType            交易类型
     * @param isHsExceptionCorrect 抛出异常时，是否冲正
     */
    private void callAc(final List<AccountEntry> accountList, final AccountWriteBack accountWriteBack,
                        final int transType, final Boolean isHsExceptionCorrect) throws HsException, Exception {

        SystemLog.debug(this.getClass().getName(), "callAc", "accountList.size():" + accountList.size()
                + ",accountWriteBack:" + JSON.toJSONString(accountWriteBack) + ",transType:" + transType
                + ",isHsExceptionCorrect:" + isHsExceptionCorrect);

        try {
            switch (transType) {
                case Constants.HSB_POINT:
                    // 调用账务系统记账接口实现记账
                    accountEntryService.deductAccount(accountList);
                    break;
                case Constants.HSB_POINT_BACK:
                    // 调用账务系统记账接口实现记账
                    accountEntryService.chargebackAccount(accountList);
                    break;
                case Constants.HSB_POINT_CANCEL:
                    // 调用账务系统记账接口实现记账
                    accountEntryService.chargebackAccount(accountList);
                    break;
                default:
                    PsException.psHsThrowException(new Throwable().getStackTrace()[0], RespCode.PS_TRANSTYPE_ERROR
                            .getCode(), "交易类型错误");
            }
        } catch (HsException hex) {
            throw hex;
        } catch (Exception e) {
            throw e;
        }
    }

    /*
     * 原代码逻辑 commented by liuzh on 2016-05-18 private void callAc(final
     * List<AccountEntry> accountList, final AccountWriteBack accountWriteBack,
     * final int transType, final Boolean isHsExceptionCorrect) throws
     * HsException {
     * 
     * //add--added by liuzh on 2016-05-17
     * SystemLog.debug(this.getClass().getName(), "callAc", "accountList:" +
     * JSON.toJSONString(accountList) + ",accountWriteBack:" +
     * JSON.toJSONString(accountList) + ",transType:" + transType +
     * ",isHsExceptionCorrect:" + isHsExceptionCorrect); //end--added by liuzh
     * on 2016-05-17
     * 
     * try { switch (transType) { case Constants.HSB_POINT: // 调用账务系统记账接口实现记账
     * accountEntryService.deductAccount(accountList); break; case
     * Constants.HSB_POINT_BACK: // 调用账务系统记账接口实现记账
     * accountEntryService.chargebackAccount(accountList); break; case
     * Constants.HSB_POINT_CANCEL: // 调用账务系统记账接口实现记账
     * accountEntryService.chargebackAccount(accountList); break; default:
     * PsException.psThrowException(new Throwable().getStackTrace()[0],
     * RespCode.PS_TRANSTYPE_ERROR.getCode(), "交易类型错误"); }
     * 
     * } catch (HsException he) { if (isHsExceptionCorrect) { // 调用账务系统冲正
     * accountEntryService.correctSingleAccount(accountWriteBack); } else { //
     * 抛出互生异常 PsException.psThrowException(new Throwable().getStackTrace()[0],
     * he.getErrorCode(), he.getMessage()); } } catch (Exception e) { //
     * 调用账务系统冲正 accountEntryService.correctSingleAccount(accountWriteBack); //
     * 抛出 异常 PsException.psThrowException(new Throwable().getStackTrace()[0],
     * RespCode.PS_AC_ERROR.getCode(), e.getMessage()); } }
     */

    /**
     * 消费积分二维码网银支付生成交易单接口
     *
     * @param point 积分入参对象
     * @return PointResult 返回值
     */
    @Override
    public PointResult pointBanking(Point point) throws HsException {
        // 校验参数是否合法,不合法则抛出异常
        ValidateModelUtil.validateModel(point);

        GeneralValidator.notNull(point.getTermRunCode(), RespCode.PS_PARAM_ERROR, "终端流水号不能为空");

        QueryPosSingle queryPosSingle = new QueryPosSingle();
        // 原批次号
        queryPosSingle.setBatchNo(point.getSourceBatchNo());
        // 企业客户号
        queryPosSingle.setEntCustId(point.getEntCustId());
        // 企业互生号
        queryPosSingle.setEntResNo(point.getEntResNo());
        // 设备编号
        queryPosSingle.setEquipmentNo(point.getEquipmentNo());
        // 单据码生成时间
        queryPosSingle.setSourcePosDate(point.getSourcePosDate());
        // 终端流水号
        queryPosSingle.setTermRunCode(point.getTermRunCode());

        // 创建积分返回值实体类
        PointResult pointResult = new PointResult();

        // 创建积分明细实体类
        PointDetail pointDetail = new PointDetail();

        // POS单笔查询
        QueryResult queryResult = psQueryService.singlePosQuery(queryPosSingle);

        if (queryResult != null) {
            // 把合法参数拷贝到对应的积分明细实体中
            BeanCopierUtils.copy(queryResult, pointResult);
            // 把合法参数拷贝到对应的积分明细实体中
            BeanCopierUtils.copy(queryResult, pointDetail);
            if (Objects.equals(PayChannel.MOBILE_PAY.getCode(), point.getPayChannel())) {

                pointDetail.setSourceTransAmount(new BigDecimal(queryResult.getSourceTransAmount()));
                // 获取手机支付TN码
                String tnTransNo = getMobilePayTn(pointDetail, null);
                pointResult.setTnTransNo(tnTransNo);
            }
            return pointResult;
        }

        // 调用共享方法
        this.pointPublic(point);

        // 把合法参数拷贝到对应的积分明细实体中
        BeanCopierUtils.copy(point, pointDetail);

        // 创建消费积分处理类
        PointHandle pointHandle = new PointHandle();

        // 调用积分明细处理方法, 设置积分明细实体属性值
        pointHandle.pointDispose(pointDetail, point);

        pointDetail.setIsOnlineRegister(Constants.IS_ONLINE_REGISTER1);

        // 支付状态设置
        pointDetail.setPayStatus(Constants.PAY_STATUS0);

        pointAllocService.allocPoint(pointDetail);

        // 积分明细实体绑定的数据插入到数据库积分明细表
        try {
            pointMapper.point(pointDetail);
        } catch (SQLException e) {
            // 抛出 异常
            PsException.psThrowException(new Throwable().getStackTrace()[0], RespCode.PS_DB_SQL_ERROR.getCode(), e
                    .getMessage(), e);
        }

        // 返回数据处理,数据组装
        pointHandle.pointResultDispose(pointResult, pointDetail);

        if (Objects.equals(PayChannel.MOBILE_PAY.getCode(), point.getPayChannel())) {
            // 获取手机支付TN码
            String tnTransNo = getMobilePayTn(pointDetail, null);
            pointResult.setTnTransNo(tnTransNo);
        }

        // 返回
        return pointResult;
    }

    /**
     * 消费积分二维码网银支付完成交易单接口
     *
     * @param transNo 消费积分流水号
     * @return PointResult 返回值
     */
    // start--commented by liuzh on 2016-05-17
    // @Transactional
    // end--commented by liuzh on 2016-05-17
    public boolean pointFinishBanking(String transNo) throws HsException {

        // add--added by liuzh on 2016-05-17
        // SystemLog.debug(this.getClass().getName(),
        // "pointFinishBanking","transNo:" + transNo);
        // end--added by liuzh on 2016-05-17

        GeneralValidator.notNull(transNo, RespCode.PS_PARAM_ERROR, "消费积分流水号不能为空");

        // 根据原订单号查询需要的原积分单信息
        PointDetail pointDetail = null;
        try {
            pointDetail = pointMapper.queryPoint(transNo);
        } catch (Exception e) {
            // 抛出 异常
            PsException.psThrowException(new Throwable().getStackTrace()[0], RespCode.PS_DB_SQL_ERROR.getCode(), e
                    .getMessage(), e);
        }
        // 判断原单是否为空, 为空则抛出互生异常
        GeneralValidator.notNull(pointDetail, RespCode.PS_ORDER_NOT_FOUND, "原订单不存在,");
        // 原单取第三位交易类型

        // 创建积分返回值实体类
        PointResult pointResult = new PointResult();

        // start--added by liuzh on 2016-05-17
        Map<String, Object> addOrUpdateMap = new HashMap<>();
        // end--added by liuzh on 2016-05-17

        // 创建消费积分处理类
        PointHandle pointHandle = new PointHandle();

        pointDetail.setIsOnlineRegister(Constants.IS_ONLINE_REGISTER1);

        // 支付状态设置
        pointDetail.setPayStatus(Constants.PAY_STATUS1);

        // 更新消费积分表支付状态
        // start--modified by liuzh on 2016-05-17
        // pointMapper.updatePointPayStatus(pointDetail);
        addOrUpdateMap.put("PointMapper.updatePointPayStatus", pointDetail);
        // end--modified by liuzh on 2016-05-17

        // 调用积分分配方法,传递积分明细对象,返回分录list
        List<Alloc> pointList = pointAllocService.allocPoint(pointDetail);

        // 调用消费积分分录数据处理方法,传入分配list,积分明细实体
        pointHandle.pointAccountData(pointList, pointDetail);

        // 改造后的分录数据
        EntryAllot entryAllot = EntryHandle.pointEntryData(pointList, Constants.HSB_POINT);
        // start--modified by liuzh on 2016-05-17
        // pointAllocService.addEntryData(entryAllot);
        addOrUpdateMap.put("PointAllocService.addEntryData", entryAllot);
        // end--modified by liuzh on 2016-05-17

        // 账务记账数据处理,传入分录分配list
        List<AccountEntry> accountList = AccountHandle.accountPointEntry(pointList);

        // 组装账务冲正对象参数
        AccountWriteBack accountWriteBack = pointHandle.writeBackPoint(pointDetail.getTransType(), pointDetail
                .getTransNo());

        // 调用账务接口
        // start--commented by liuzh on 2016-05-17
        // this.callAc(accountList, accountWriteBack, Constants.HSB_POINT,
        // false);
        // end--commented by liuzh on 2016-05-17

        // start--added by liuzh on 2016-05-17
        try {
            if (accountList != null && accountList.size() != 0) {
                //使用抵扣券
                hsecexternalapi.useCoupon(pointDetail.getPerResNo(), pointDetail.getEntResNo(), pointDetail.getTransNo(), pointDetail.getDeductionVoucher());
                // 调用账务系统记账接口实现记账
                this.callAc(accountList, accountWriteBack, Constants.HSB_POINT, false);
                commitPointFinishBankingTransactional(addOrUpdateMap);

                SystemLog.debug(this.getClass().getName(), "pointFinishBanking", "operate successfully");
            }
        } catch (HsException he) {
            //撤销已使用的抵扣券
            hsecexternalapi.useCouponCancel(pointDetail.getEntResNo(), pointDetail.getTransNo(), pointDetail.getDeductionVoucher());
            // 抛出互生异常
            PsException.psThrowException(new Throwable().getStackTrace()[0], he.getErrorCode(), he.getMessage(), he);

        } catch (Exception e) {
            //撤销已使用的抵扣券
            hsecexternalapi.useCouponCancel(pointDetail.getEntResNo(), pointDetail.getTransNo(), pointDetail.getDeductionVoucher());
            // 组装账务冲正对象参数
            accountWriteBack = pointHandle.writeBackPoint(pointDetail.getTransType(), pointDetail.getTransNo());

            // 调用账务系统冲正
            accountEntryService.correctSingleAccount(accountWriteBack);

            // 抛出 异常
            PsException.psThrowException(new Throwable().getStackTrace()[0], RespCode.PS_DB_SQL_ERROR.getCode(), e
                    .getMessage(), e);
        }
        // end--added by liuzh on 2016-05-17

        // 返回数据处理,数据组装
        pointHandle.pointResultDispose(pointResult, pointDetail);

        // 返回
        return true;
    }

    /**
     * 获取快捷支付短信验证码
     *
     * @param pointResult
     * @throws HsException
     */
    @Override
    public void pointQuickPaySmsCode(PointResult pointResult) throws HsException {
        GeneralValidator.notNull(pointResult, RespCode.PS_PARAM_ERROR, "参数未空");
        GeneralValidator.notNull(pointResult.getTransNo(), RespCode.PS_PARAM_ERROR, "交易流水号为空");
        GeneralValidator.notNull(pointResult.getBindingNo(), RespCode.PS_PARAM_ERROR, "签约号为空");
        // 根据原订单号查询需要的原积分单信息
        PointDetail pointDetail = null;
        try {
            pointDetail = pointMapper.queryPoint(pointResult.getTransNo());
        } catch (Exception e) {
            // 抛出 异常
            PsException.psThrowException(new Throwable().getStackTrace()[0], RespCode.PS_DB_SQL_ERROR.getCode(), e
                    .getMessage(), e);
        }
        // 判断原单是否为空, 为空则抛出互生异常
        GeneralValidator.notNull(pointDetail, RespCode.PS_ORDER_NOT_FOUND, "原订单不存在");

        getQuickPaySmsCode(pointDetail, pointResult.getBindingNo(), null);
    }

    /**
     * 验证手机短信验证码
     *
     * @param pointResult
     * @return
     * @throws HsException
     */
    @Override
    public String pointQuickPayUrl(PointResult pointResult) throws HsException {
        GeneralValidator.notNull(pointResult, RespCode.PS_PARAM_ERROR, "参数未空");
        GeneralValidator.notNull(pointResult.getTransNo(), RespCode.PS_PARAM_ERROR, "交易流水号为空");
        GeneralValidator.notNull(pointResult.getBindingNo(), RespCode.PS_PARAM_ERROR, "签约号为空");
        GeneralValidator.notNull(pointResult.getSmsCode(), RespCode.PS_PARAM_ERROR, "手机短信验证码为空");
        // 根据原订单号查询需要的原积分单信息
        PointDetail pointDetail = null;
        try {
            pointDetail = pointMapper.queryPoint(pointResult.getTransNo());
            // 支付状态设置
            pointDetail.setPayStatus(Constants.PAY_STATUS3);
            // 更新消费积分表支付状态
            pointMapper.updatePointPayStatus(pointDetail);
        } catch (Exception e) {
            // 抛出 异常
            PsException.psThrowException(new Throwable().getStackTrace()[0], RespCode.PS_DB_SQL_ERROR.getCode(), e
                    .getMessage(), e);
        }
        return getQuickPayUrl(pointResult.getTransNo(), pointResult.getBindingNo(), pointResult.getSmsCode());
    }

    /**
     * 获取手机支付TN码
     *
     * @param pointDetail  兑换互生币信息
     * @param privObligate 私有数据
     * @return TN交易流水号
     * @throws HsException
     */
    public String getMobilePayTn(PointDetail pointDetail, String privObligate) throws HsException {
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                "获取手机支付TN码,params[" + pointDetail + ",privObligate:" + privObligate + "]");
        String tnTransNo = "";
        LocalInfo localInfo = lcsClient.getLocalInfo();
        // 手机支付实体
        MobilePayBean mobilePayBean = null;
        try {
            mobilePayBean = new MobilePayBean();
            // 从配置文件中取互生商户号
            mobilePayBean.setMerId("1100000007");
            mobilePayBean.setOrderNo(pointDetail.getTransNo());// 业务订单号
            SimpleDateFormat sdf = new SimpleDateFormat(DateUtil.DATE_FORMAT);
            mobilePayBean.setOrderDate(sdf.parse(sdf.format(DateUtil.today())));// 业务订单日期
            mobilePayBean.setTransAmount(pointDetail.getSourceTransAmount() == null ? "0" : pointDetail
                    .getSourceTransAmount().toString());// 交易金额
            mobilePayBean.setTransDesc("二维码手机支付");// 交易描述
            mobilePayBean.setCurrencyCode(localInfo.getCurrencyCode());// 币种
            mobilePayBean.setPrivObligate(privObligate);// 私有数据
            // 调用支付系统：获取手机支付TN码接口
            tnTransNo = gpPaymentService.getMobilePayTn(mobilePayBean, "PS");
        } catch (HsException e) {
            throw e;
        } catch (Exception e) {
            SystemLog.error(this.getClass().getName(), "method:"
                            + Thread.currentThread().getStackTrace()[1].getMethodName(),
                    RespCode.PS_INVOKE_GP_GET_MOBILE_PAY_TN_CODE_ERROR + ":调用支付系统获取手机支付TN码异常："
                            + gpPaymentService.getClass().getName() + "." + "getMobilePayTn("
                            + JSON.toJSONString(mobilePayBean) + ", PS)\n", e);
            throw new HsException(RespCode.PS_INVOKE_GP_GET_MOBILE_PAY_TN_CODE_ERROR, "调用支付系统获取手机支付TN码异常："
                    + gpPaymentService.getClass().getName() + "." + "getMobilePayTn("
                    + JSON.toJSONString(mobilePayBean) + ", PS)\n" + e);
        }
        return tnTransNo;
    }

    /**
     * 获取快捷支付短信验证码
     *
     * @param pointDetail  兑换互生币信息
     * @param bindingNo    商户号
     * @param bindingNo    签约号
     * @param privObligate 私有数据
     * @throws HsException
     * @see com.gy.hsxt.ao.interfaces.IDubboInvokService#getQuickPaySmsCode(com.gy.hsxt.ao.bean.BuyHsb,
     * java.lang.String, java.lang.String, java.lang.String)
     */
    public void getQuickPaySmsCode(PointDetail pointDetail, String bindingNo, String privObligate) throws HsException {
        SystemLog
                .debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                        "获取快捷支付短信验证码,params[" + pointDetail + ",bindingNo:" + bindingNo + ",privObligate:"
                                + privObligate + "]");
        LocalInfo localInfo = lcsClient.getLocalInfo();
        // 银联快捷支付实体
        QuickPaySmsCodeBean quickPaySmsCodeBean = null;
        try {
            quickPaySmsCodeBean = new QuickPaySmsCodeBean();
            quickPaySmsCodeBean.setMerId("1100000007");// 商户号
            quickPaySmsCodeBean.setOrderNo(pointDetail.getTransNo());// 业务订单号
            SimpleDateFormat sdf = new SimpleDateFormat(DateUtil.DATE_FORMAT);
            quickPaySmsCodeBean.setOrderDate(sdf.parse(sdf.format(DateUtil.today())));// 业务订单日期
            quickPaySmsCodeBean.setBindingNo(bindingNo);// 签约号
            quickPaySmsCodeBean.setTransAmount(pointDetail.getSourceTransAmount() == null ? "0" : pointDetail
                    .getSourceTransAmount().toString());// 交易金额
            quickPaySmsCodeBean.setTransDesc("二维码快捷支付");// 交易描述
            quickPaySmsCodeBean.setCurrencyCode(localInfo.getCurrencyCode());// 币种
            quickPaySmsCodeBean.setPrivObligate(privObligate);// 私有数据
            // 调用支付系统：获取首次快捷支付短信接口
            gpPaymentService.getQuickPaySmsCode(quickPaySmsCodeBean, "PS");
        } catch (HsException e) {
            throw e;
        } catch (Exception e) {
            SystemLog.error(this.getClass().getName(), "method:"
                            + Thread.currentThread().getStackTrace()[1].getMethodName(),
                    RespCode.PS_INVOKE_GP_GET_QUICK_PAY_SMS_CODE_ERROR + ":调用支付系统：获取快捷支付短信验证码异常："
                            + gpPaymentService.getClass().getName() + "." + "getQuickPaySmsCode("
                            + JSON.toJSONString(quickPaySmsCodeBean) + ", PS)\n", e);
            throw new HsException(RespCode.PS_INVOKE_GP_GET_QUICK_PAY_SMS_CODE_ERROR, "调用支付系统：获取快捷支付短信验证码异常："
                    + gpPaymentService.getClass().getName() + "." + "getQuickPaySmsCode("
                    + JSON.toJSONString(quickPaySmsCodeBean) + ", PS)\n" + e);
        }
    }

    /**
     * 支付结果异步通知
     *
     * @param paymentState
     * @return
     * @throws HsException
     * @see com.gy.hsxt.gp.api.IGPNotifyService#notifyPaymentOrderState(com.gy.hsxt.gp.bean.PaymentOrderState)
     */
    @Override
    // start--commented by liuzh on 2016-05-17 修改事务处理
    // @Transactional
    // end--commented by liuzh on 2016-05-17
    public boolean notifyPaymentOrderState(PaymentOrderState paymentState) {
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                "支付结果异步通知,params[" + paymentState + "]");
        // 是否通知成功
        boolean isOk = false;
        try {
            // 参数为空
            HsAssert.notNull(paymentState, RespCode.PS_PARAM_ERROR, "支付结果异步通知：实体参数为空");
            // 获取业务订单号
            String orderNo = paymentState.getOrderNo();
            // 业务订单号为空
            HsAssert.hasText(orderNo, RespCode.PS_PARAM_ERROR, "支付结果异步通知：支付系统异步通知结果中的业务订单号为空");
            // 根据原订单号查询需要的原积分单信息
            PointDetail pointDetail = pointMapper.queryPoint(orderNo);
            // 校验业务订单：业务订单不存在
            HsAssert.notNull(pointDetail, RespCode.PS_PARAM_ERROR, "支付结果异步通知：业务订单号对应的兑换互生币交易单不存在");
            if (pointDetail.getPayStatus() == 1) {
                return true;
            }
            // 返回处理结果
            isOk = pointFinishBanking(orderNo);
        } catch (HsException e) {
            // 因为支付系统不接受异常，所以只进行日志记录
            SystemLog.error(this.getClass().getName(), "method:"
                    + Thread.currentThread().getStackTrace()[1].getMethodName(), e.getErrorCode() + ":"
                    + e.getMessage(), e);
        } catch (Exception e) {
            // 因为支付系统不接受异常，所以只进行日志记录
            SystemLog.error(this.getClass().getName(), "method:"
                            + Thread.currentThread().getStackTrace()[1].getMethodName(),
                    "PS处理支付结果通知异常：" + this.getClass().getName() + "." + "pointFinishBanking("
                            + JSON.toJSONString(paymentState) + ")\n", e);
        }
        return isOk;
    }

    @Override
    public boolean notifyTransCashOrderState(TransCashOrderState arg0) {
        // TODO Auto-generated method stub
        return true;
    }

    /**
     * 获取快捷支付URL
     *
     * @param transNo   交易流水号
     * @param bindingNo 签约号
     * @param smsCode   短信验证码
     * @return 快捷支付URL
     * @throws HsException
     * @see com.gy.hsxt.ao.interfaces.IDubboInvokService#getQuickPayUrl(java.lang.String,
     * java.lang.String, java.lang.String)
     */
    public String getQuickPayUrl(String transNo, String bindingNo, String smsCode) throws HsException {
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                "获取快捷支付URL,params[transNo:" + transNo + ",bindingNo:" + bindingNo + ",smsCode:" + smsCode + "]");
        String payUrl = "";
        // 银联支付实体
        QuickPayBean quickPayBean = new QuickPayBean();
        try {
            // 从配置文件中取互生商户号
            quickPayBean.setMerId("1100000007");
            quickPayBean.setOrderNo(transNo);// 业务订单号
            quickPayBean.setBindingNo(bindingNo);// 签约号
            quickPayBean.setSmsCode(smsCode);// 短信验证码

            // 调用支付系统：获取快捷支付URL接口
            payUrl = gpPaymentService.getQuickPayUrl(quickPayBean, "PS");
        } catch (HsException e) {
            throw e;
        } catch (Exception e) {
            SystemLog.error(this.getClass().getName(), "method:"
                            + Thread.currentThread().getStackTrace()[1].getMethodName(),
                    RespCode.PS_INVOKE_GP_GET_PAY_URL_ERROR + ":调用支付系统获取快捷支付URL异常："
                            + gpPaymentService.getClass().getName() + "." + "getQuickPayUrl("
                            + JSON.toJSONString(quickPayBean) + ",PS)\n", e);
            throw new HsException(RespCode.PS_INVOKE_GP_GET_PAY_URL_ERROR, "调用支付系统获取快捷支付URL异常："
                    + gpPaymentService.getClass().getName() + "." + "getQuickPayUrl(" + JSON.toJSONString(quickPayBean)
                    + ",PS)\n" + e);
        }
        return payUrl;
    }

    /**
     * added by liuzh on 2016-05-14 提交数据库更新:积分
     *
     * @param addOrUpdateMap
     */
    private void commitPointTransactional(final Map<String, Object> addOrUpdateMap) throws Exception {
        /*
         * SystemLog.debug(this.getClass().getName(),
         * "commitPointTransactional", "addOrUpdateMap:" +
         * JSON.toJSONString(addOrUpdateMap));
         */
        if (addOrUpdateMap != null && addOrUpdateMap.size() > 0) {
            new TransactionHandler<Boolean>(transactionMgr) {

                @Override
                protected Boolean doInTransaction() throws Exception {
                    try {
                        PointDetail updatePointPayStatusPointDetail = (PointDetail) addOrUpdateMap
                                .get("PointMapper.updatePointPayStatus");
                        EntryAllot entryAllot = (EntryAllot) addOrUpdateMap.get("PointAllocService.addEntryData");
                        PointDetail pointDetail = (PointDetail) addOrUpdateMap.get("PointMapper.point");

                        if (updatePointPayStatusPointDetail != null) {
                            pointMapper.updatePointPayStatus(updatePointPayStatusPointDetail);
                        }

                        if (entryAllot != null) {
                            // pointAllocService.addEntryData(entryAllot);
                            pointAllocMapper.insertEntry(entryAllot);
                        }

                        if (pointDetail != null) {
                            pointMapper.point(pointDetail);
                        }
                    } catch (Exception e) {
                        throw e;
                    }
                    return true;
                }// end doInTransaction

                protected void doWhenException(Exception e) {
                    System.out.println("point:" + e.getMessage());
                    SystemLog.debug(this.getClass().getName(), "point", e.getMessage());
                }

                ;

            }.getResult();
            // end new TransactionHandler
        }
    }

    /**
     * added by liuzh on 2016-05-14 提交数据库更新:积分 退货
     *
     * @param addOrUpdateMap
     */
    private void commitBackPointTransactional(final Map<String, Object> addOrUpdateMap) throws Exception {
        /*
         * SystemLog.debug(this.getClass().getName(),
         * "commitBackPointTransactional", "addOrUpdateMap:" +
         * JSON.toJSONString(addOrUpdateMap));
         */
        if (addOrUpdateMap != null && addOrUpdateMap.size() > 0) {
            new TransactionHandler<Boolean>(transactionMgr) {

                @Override
                protected Boolean doInTransaction() throws Exception {

                    BackDetail backDetail = (BackDetail) addOrUpdateMap.get("PointMapper.backPoint");
                    EntryAllot entryAllot = (EntryAllot) addOrUpdateMap.get("PointAllocService.addEntryData");
                    PointDetail oldOrder = (PointDetail) addOrUpdateMap.get("PointMapper.updatePoint");
                    Object[] updateStatusParams = (Object[]) addOrUpdateMap.get("PointMapper.updateStatus");
                    List<PointAllot> pointAllotList = (List<PointAllot>) addOrUpdateMap
                            .get("PointAllocService.addPointAllotData");

                    if (backDetail != null) {
                        pointMapper.backPoint(backDetail);
                    }

                    if (entryAllot != null) {
                        // pointAllocService.addEntryData(entryAllot);
                        pointAllocMapper.insertEntry(entryAllot);

                    }

                    if (oldOrder != null) {
                        pointMapper.updatePoint(oldOrder);
                    }

                    if (updateStatusParams != null) {
                        int transStatus = (int) updateStatusParams[0];
                        String transNo = (String) updateStatusParams[1];
                        String tableName = (String) updateStatusParams[2];

                        pointMapper.updateStatus(transStatus, transNo, tableName);
                    }

                    if (pointAllotList != null) {
                        // pointAllocService.addPointAllotData(pointAllotList);
                        pointAllocMapper.insertPointAllotDaily(pointAllotList);
                    }

                    return true;
                }// end doInTransaction

            }.getResult();
            // end new TransactionHandler
        }

    }

    /**
     * added by liuzh on 2016-05-17 提交数据库更新:积分 撤单
     *
     * @param addOrUpdateMap
     */
    private void commitCancelPointInsideTransactional(final Map<String, Object> addOrUpdateMap) throws Exception {
        /*
         * SystemLog.debug(this.getClass().getName(),
         * "commitCancelPointInsideTransactional", "addOrUpdateMap:" +
         * JSON.toJSONString(addOrUpdateMap));
         */
        if (addOrUpdateMap != null && addOrUpdateMap.size() > 0) {
            new TransactionHandler<Boolean>(transactionMgr) {

                @Override
                protected Boolean doInTransaction() throws Exception {
                    try {
                        CancellationDetail cancellationDetail = (CancellationDetail) addOrUpdateMap
                                .get("PointMapper.cancelPoint");
                        EntryAllot entryAllot = (EntryAllot) addOrUpdateMap.get("PointAllocService.addEntryData");
                        Object[] updateStatusParams = (Object[]) addOrUpdateMap.get("PointMapper.updateStatus");

                        if (cancellationDetail != null) {
                            pointMapper.cancelPoint(cancellationDetail);
                        }

                        if (entryAllot != null) {
                            // pointAllocService.addEntryData(entryAllot);
                            pointAllocMapper.insertEntry(entryAllot);
                        }

                        if (updateStatusParams != null) {
                            int transStatus = (int) updateStatusParams[0];
                            String transNo = (String) updateStatusParams[1];
                            String tableName = (String) updateStatusParams[2];

                            pointMapper.updateStatus(transStatus, transNo, tableName);
                        }
                    } catch (Exception e) {
                        throw e;
                    }
                    return true;
                }// end doInTransaction

            }.getResult();
            // end new TransactionHandler
        }

    }

    /**
     * added by liuzh on 2016-05-17 提交数据库更新:冲正
     *
     * @param addOrUpdateMap
     */
    private void commitReturnPointTransactional(final String[] tableNames, final Map<String, Object> addOrUpdateMap)
            throws Exception {
        /*
         * SystemLog.debug(this.getClass().getName(),
         * "commitReturnPointTransactional", "addOrUpdateMap:" +
         * JSON.toJSONString(addOrUpdateMap));
         */
        if (addOrUpdateMap != null && addOrUpdateMap.size() > 0) {
            new TransactionHandler<Boolean>(transactionMgr) {

                @Override
                protected Boolean doInTransaction() throws Exception {
                    for (String tableName : tableNames) {
                        CorrectDetail correctDetail = (CorrectDetail) addOrUpdateMap.get(tableName
                                + ".PointMapper.returnPoint");
                        EntryAllot entryAllot = (EntryAllot) addOrUpdateMap.get(tableName
                                + ".PointAllocService.addEntryData");
                        Object[] updatePosHsbWriteBackParams = (Object[]) addOrUpdateMap.get(tableName
                                + ".PointAllocService.updatePosHsbWriteBack");
                        Object[] updateStatusParams = (Object[]) addOrUpdateMap.get(tableName
                                + ".PointMapper.updateStatus");
                        Object[] updatePointStatusParams = (Object[]) addOrUpdateMap.get(tableName
                                + ".PointMapper.updatePointStatus");

                        if (correctDetail != null) {
                            pointMapper.returnPoint(correctDetail);
                        }

                        if (entryAllot != null) {
                            // pointAllocService.addEntryData(entryAllot);
                            pointAllocMapper.insertEntry(entryAllot);
                        }

                        if (updatePosHsbWriteBackParams != null) {
                            String writeBack = (String) updatePosHsbWriteBackParams[0];
                            String entryNo = (String) updatePosHsbWriteBackParams[1];
                            // pointAllocService.updatePosHsbWriteBack(writeBack,entryNo);
                            hsbAllocMapper.updatePosHsbWriteBack(writeBack, entryNo);
                        }

                        if (updateStatusParams != null) {
                            int transStatus = (int) updateStatusParams[0];
                            String transNo = (String) updateStatusParams[1];
                            String tblName = (String) updateStatusParams[2];
                            pointMapper.updateStatus(transStatus, transNo, tblName);
                        }

                        if (updatePointStatusParams != null) {
                            int transStatus = (int) updatePointStatusParams[0];
                            String transNo = (String) updatePointStatusParams[1];
                            String tblName = (String) updatePointStatusParams[2];
                            pointMapper.updatePointStatus(transStatus, transNo, tblName);
                        }
                    }
                    return true;
                }// end doInTransaction

            }.getResult();
            // end new TransactionHandler
        }

    }

    /**
     * added by liuzh on 2016-05-17 提交数据库更新:网上积分登记
     *
     * @param addOrUpdateMap
     */
    private void commitPointRegisterTransactional(final Map<String, Object> addOrUpdateMap) throws Exception {
        /*
         * SystemLog.debug(this.getClass().getName(),
         * "commitPointRegisterTransactional", "addOrUpdateMap:" +
         * JSON.toJSONString(addOrUpdateMap));
         */
        if (addOrUpdateMap != null && addOrUpdateMap.size() > 0) {
            new TransactionHandler<Boolean>(transactionMgr) {

                @Override
                protected Boolean doInTransaction() throws Exception {
                    PointDetail pointDetail = (PointDetail) addOrUpdateMap.get("PointMapper.point");
                    EntryAllot entryAllot = (EntryAllot) addOrUpdateMap.get("PointAllocService.addEntryData");

                    if (pointDetail != null) {
                        pointMapper.point(pointDetail);
                    }

                    if (entryAllot != null) {
                        // pointAllocService.addEntryData(entryAllot);
                        pointAllocMapper.insertEntry(entryAllot);
                    }
                    return true;
                }// end doInTransaction

            }.getResult();
            // end new TransactionHandler
        }

    }

    /**
     * added by liuzh on 2016-05-17 提交数据库更新:消费积分二维码网银支付完成交易单接口
     *
     * @param addOrUpdateMap
     */
    private void commitPointFinishBankingTransactional(final Map<String, Object> addOrUpdateMap) throws Exception {
        /*
         * SystemLog.debug(this.getClass().getName(),
         * "commitPointFinishBankingTransactional", "addOrUpdateMap:" +
         * JSON.toJSONString(addOrUpdateMap));
         */
        if (addOrUpdateMap != null && addOrUpdateMap.size() > 0) {
            new TransactionHandler<Boolean>(transactionMgr) {

                @Override
                protected Boolean doInTransaction() throws Exception {
                    PointDetail pointDetail = (PointDetail) addOrUpdateMap.get("PointMapper.updatePointPayStatus");
                    EntryAllot entryAllot = (EntryAllot) addOrUpdateMap.get("PointAllocService.addEntryData");

                    if (pointDetail != null) {
                        int ret = pointMapper.updatePointPayStatus(pointDetail);
                        if (ret == 0) {
                            // 未更新
                            // isThrowException = true;
                            throw new Exception("需要冲正");
                        }
                    }

                    if (entryAllot != null) {
                        pointAllocService.addEntryData(entryAllot);
                    }

                    return true;
                }// end doInTransaction

            }.getResult();
            // end new TransactionHandler
        }

    }

}
