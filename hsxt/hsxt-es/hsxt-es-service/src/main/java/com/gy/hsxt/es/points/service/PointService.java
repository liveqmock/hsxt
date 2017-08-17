package com.gy.hsxt.es.points.service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.gy.hsi.common.spring.transaction.TransactionHandler;
import com.gy.hsxt.ac.api.IAccountEntryService;
import com.gy.hsxt.ac.bean.AccountEntry;
import com.gy.hsxt.ac.bean.AccountWriteBack;
import com.gy.hsxt.bp.client.api.BusinessParamSearch;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.constant.TransType;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.BeanCopierUtils;
import com.gy.hsxt.common.utils.DateUtil;
import com.gy.hsxt.es.api.IEsPointService;
import com.gy.hsxt.es.bean.Back;
import com.gy.hsxt.es.bean.BackResult;
import com.gy.hsxt.es.bean.BonusPoints;
import com.gy.hsxt.es.bean.BonusPointsResult;
import com.gy.hsxt.es.bean.Cancel;
import com.gy.hsxt.es.bean.CancelResult;
import com.gy.hsxt.es.bean.Correct;
import com.gy.hsxt.es.bean.Point;
import com.gy.hsxt.es.bean.PointResult;
import com.gy.hsxt.es.bean.QuetyPaying;
import com.gy.hsxt.es.common.Compute;
import com.gy.hsxt.es.common.Constants;
import com.gy.hsxt.es.common.EsAssert;
import com.gy.hsxt.es.common.EsException;
import com.gy.hsxt.es.common.EsRespCode;
import com.gy.hsxt.es.common.EsTools;
import com.gy.hsxt.es.common.TransTypeUtil;
import com.gy.hsxt.es.common.ValidateModelUtil;
import com.gy.hsxt.es.distribution.bean.Alloc;
import com.gy.hsxt.es.distribution.bean.EntryAllot;
import com.gy.hsxt.es.distribution.bean.PointAllot;
import com.gy.hsxt.es.distribution.handle.BatAllocHandle;
import com.gy.hsxt.es.distribution.handle.EntryHandle;
import com.gy.hsxt.es.distribution.handle.PointBackHandle;
import com.gy.hsxt.es.distribution.mapper.PointAllocMapper;
import com.gy.hsxt.es.distribution.service.PointAllocService;
import com.gy.hsxt.es.points.bean.BackDetail;
import com.gy.hsxt.es.points.bean.CancellationDetail;
import com.gy.hsxt.es.points.bean.CorrectDetail;
import com.gy.hsxt.es.points.bean.PointDetail;
import com.gy.hsxt.es.points.handle.AccountHandle;
import com.gy.hsxt.es.points.handle.PointHandle;
import com.gy.hsxt.es.points.mapper.PointMapper;

/**
 * @author chenhz
 * @version v3.0
 * @description 消费积分接口实现类
 * @createDate 2015-7-27 上午10:15:22
 * @Company 深圳市归一科技研发有限公司
 */
@Service
public class PointService implements IEsPointService {
    // 限额验证服务
    @Autowired
    private AccountQuotaService AccountQuotaService;

    // 消费积分映射器
    @Autowired
    private PointMapper pointMapper;

    // 积分分配服务
    @Autowired
    private PointAllocService pointAllocService;

    // 账务记账服务
    @Autowired
    private IAccountEntryService accountEntryService;

    // 业务参数系统配置服务
    @Autowired
    private BusinessParamSearch businessParamSearch;

    @Autowired
    private PointAllocMapper pointAllocMapper;

    @Autowired
    private DataSourceTransactionManager transactionMgr;

    // @Autowired
    // private IConfirmReceiptService confirmReceiptService;

    /**
     * 混合接口，积分、撤单、退货、预留结单
     * 
     * @param listBonusPoints
     *            入参
     * @throws HsException
     */
    @Override
    public List<BonusPointsResult> bonusPoints(List<BonusPoints> listBonusPoints) throws HsException {
        EsAssert.notEmpty(listBonusPoints, "对象为空！！");
        // 返回List
        List<BonusPointsResult> bonusPointsResults = new ArrayList<>();

        List<Map<String, Object>> addOrUpdateList = new ArrayList<Map<String, Object>>();

        List<List<AccountEntry>> deductAccountList = new ArrayList<>();

        List<List<AccountEntry>> chargebackAccountList = new ArrayList<>();

        List<AccountWriteBack> accountWriteBack = new ArrayList<>();

        List<List<AccountWriteBack>> accountWriteBackList = new ArrayList<>();

        try
        {
            // 查询是否已处理条件封装
            List<QuetyPaying> quetyPayingList = new ArrayList<QuetyPaying>();
            for (int i = 0; i < listBonusPoints.size(); i++)
            {
                BonusPoints point = listBonusPoints.get(i);
                QuetyPaying quetyPaying = new QuetyPaying();
                // 订单号
                quetyPaying.setOrderNo(point.getOrderNo());
                // 原始交易号
                quetyPaying.setSourceTransNo(point.getSourceTransNo());
                // 交易类型
                quetyPaying.setTransType(point.getTransType());
                quetyPayingList.add(quetyPaying);
            }
            quetyPayingList = queryPaying(quetyPayingList);
            if (!quetyPayingList.isEmpty())
            {
                for (int i = 0; i < quetyPayingList.size(); i++)
                {
                    QuetyPaying quetyPaying = quetyPayingList.get(0);
                    BonusPointsResult bonusPointsResult = new BonusPointsResult();
                    bonusPointsResult.setTransPoint(quetyPaying.getPerPoint());
                    bonusPointsResult.setTransNo(quetyPaying.getTransNo());
                    bonusPointsResult.setAccountantDate(DateUtil.DateToString(quetyPaying.getSourceTransDate(),
                            DateUtil.DEFAULT_DATE_TIME_FORMAT));
                    bonusPointsResults.add(bonusPointsResult);
                }
                return bonusPointsResults;
            }
            for (int i = 0; i < listBonusPoints.size(); i++)
            {
                BonusPoints point = listBonusPoints.get(i);
                EsAssert.repeatSubmit(point.toString());

            }
            // 消费积分预留
            List<Point> pointListReservedPoint = new ArrayList<>();
            // 消费积分结单
            List<Point> pointListStatementPoint = new ArrayList<>();

            for (BonusPoints bonusPoints : listBonusPoints)
            {
                EsAssert.notNull(bonusPoints, RespCode.PS_PARAM_ERROR, "bonusPoints不能为空");
                /** 业务类型 :1是预留, 2是预留结单,3是撤单，4是退货，5是消费积分，货到付款一次性支付完成 */
                EsAssert.notNull(bonusPoints, RespCode.PS_PARAM_ERROR, "不能为空！");
                Integer businessType = bonusPoints.getTradeType();
                EsAssert.notNull(businessType, RespCode.PS_PARAM_ERROR, "业务类型不能为空！");
                if ((businessType == 0) || (businessType > 5))
                {
                    EsException.esHsThrowException(new Throwable().getStackTrace()[0], EsRespCode.PS_PARAM_ERROR
                            .getCode(), "业务类型填写的不对！");
                }
                if (businessType == 5)
                {
                    Point pointPoint = new Point();
                    BonusPointsResult bonusPointsResult0 = new BonusPointsResult();
                    BeanCopierUtils.copy(bonusPoints, pointPoint);
                    PointResult pointResult;

                    Map<String, Object> pointMap = this.pointMap(pointPoint);

                    pointResult = (PointResult) pointMap.get("pointPointResult");

                    addOrUpdateList.add((Map<String, Object>) pointMap.get("pointAddOrUpdate"));

                    deductAccountList.add((List<AccountEntry>) pointMap.get("pointAccountList"));

                    accountWriteBack.add((AccountWriteBack) pointMap.get("pointAccountWriteBack"));

                    if (pointResult != null)
                    {
                        bonusPointsResult0.setSourceTransNo(pointPoint.getSourceTransNo());
                        bonusPointsResult0.setAccountantDate(pointResult.getAccountantDate());
                        bonusPointsResult0.setOrderNo(pointResult.getTransNo());
                        bonusPointsResult0.setTradeType(5);
                        bonusPointsResult0.setTransAmount(pointPoint.getTransAmount());
                        bonusPointsResult0.setRetCode(RespCode.SUCCESS.getCode());
                        bonusPointsResult0.setTransPoint(pointPoint.getTransAmount());
                        bonusPointsResult0.setRetMsg("是消费积分，货到付款一次性支付完成交易成功！");
                    }
                    bonusPointsResults.add(bonusPointsResult0);
                }
                if (businessType == 3)
                {
                    BonusPointsResult bonusPointsResultCancel = new BonusPointsResult();
                    Cancel cancel = new Cancel();
                    BeanCopierUtils.copy(bonusPoints, cancel);
                    CancelResult cancelResult;

                    Map<String, Object> cancelPointMap = this.cancelPointMap(cancel);

                    cancelResult = (CancelResult) cancelPointMap.get("cancelPointCancelResult");

                    addOrUpdateList.add((Map<String, Object>) cancelPointMap.get("cancelPointAddOrUpdate"));

                    chargebackAccountList.add((List<AccountEntry>) cancelPointMap.get("cancelPointAccountList"));

                    accountWriteBack.add((AccountWriteBack) cancelPointMap.get("cancelPointAccountWriteBack"));

                    if (cancelResult != null)
                    {
                        bonusPointsResultCancel.setSourceTransNo(cancel.getSourceTransNo());
                        bonusPointsResultCancel.setAccountantDate(cancelResult.getAccountantDate());
                        bonusPointsResultCancel.setOrderNo(cancelResult.getTransNo());
                        bonusPointsResultCancel.setTradeType(3);
                        bonusPointsResultCancel.setTransAmount(cancelResult.getTransAmount());
                        bonusPointsResultCancel.setRetCode(RespCode.SUCCESS.getCode());
                        // bonusPointsResult1.setTransPoint(cancelResult.getTransAmount());
                        bonusPointsResultCancel.setRetMsg("撤单成功！");
                    }
                    bonusPointsResults.add(bonusPointsResultCancel);
                }
                if (businessType == 4)
                {
                    BonusPointsResult bonusPointsResultBack = new BonusPointsResult();
                    Back back = new Back();
                    BeanCopierUtils.copy(bonusPoints, back);
                    BackResult backResult;

                    Map<String, Object> backPointMap = this.backPointMap(back);

                    backResult = (BackResult) backPointMap.get("backPointBackResult");

                    addOrUpdateList.add((Map<String, Object>) backPointMap.get("backPointAddOrUpdate"));

                    chargebackAccountList.add((List<AccountEntry>) backPointMap.get("backPointAccountList"));

                    accountWriteBack.add((AccountWriteBack) backPointMap.get("backPointAccountWriteBack"));

                    if (backResult != null)
                    {
                        bonusPointsResultBack.setSourceTransNo(back.getSourceTransNo());
                        bonusPointsResultBack.setAccountantDate(backResult.getAccountantDate());
                        bonusPointsResultBack.setOrderNo(backResult.getTransNo());
                        bonusPointsResultBack.setTradeType(4);
                        bonusPointsResultBack.setTransAmount(back.getTransAmount());
                        bonusPointsResultBack.setRetCode(RespCode.SUCCESS.getCode());
                        bonusPointsResultBack.setTransPoint(back.getBackPoint());
                        bonusPointsResultBack.setRetMsg("退货成功！");
                    }
                    bonusPointsResults.add(bonusPointsResultBack);
                }
                if (businessType == 1)
                {
                    Point pointReservedPoint = new Point();
                    BeanCopierUtils.copy(bonusPoints, pointReservedPoint);
                    pointListReservedPoint.add(pointReservedPoint);
                }
                if (businessType == 2)
                {
                    Point pointStatementPoint = new Point();
                    BeanCopierUtils.copy(bonusPoints, pointStatementPoint);
                    pointListStatementPoint.add(pointStatementPoint);
                }
            }
            if (!CollectionUtils.isEmpty(pointListReservedPoint))
            {
                BonusPointsResult bonusPointsResultReservedPoint = new BonusPointsResult();
                List<PointResult> prList;
                Map<String, Object> reservedPointMap = this.reservedPointMap(pointListReservedPoint);

                prList = (List<PointResult>) reservedPointMap.get("reservedPointPrList");

                addOrUpdateList.add((Map<String, Object>) reservedPointMap.get("reservedPointAddOrUpdate"));

                deductAccountList.add((List<AccountEntry>) reservedPointMap.get("reservedPointAccountList"));

                accountWriteBackList.add((List<AccountWriteBack>) reservedPointMap
                        .get("reservedPointAccountWriteBackList"));

                if (!CollectionUtils.isEmpty(prList))
                {
                    for (PointResult pointResult : prList)
                    {
                        if (pointResult != null)
                        {
                            bonusPointsResultReservedPoint.setSourceTransNo(pointResult.getSourceTransNo());
                            bonusPointsResultReservedPoint.setAccountantDate(pointResult.getAccountantDate());
                            bonusPointsResultReservedPoint.setOrderNo(pointResult.getTransNo());
                            bonusPointsResultReservedPoint.setTradeType(1);
                            // bonusPointsResult3.setTransAmount(pointResult.getTransAmount());
                            bonusPointsResultReservedPoint.setRetCode(RespCode.SUCCESS.getCode());
                            // bonusPointsResult3.setTransPoint(pointResult.getTransAmount());
                            bonusPointsResultReservedPoint.setRetMsg("消费积分预留成功！");
                            bonusPointsResults.add(bonusPointsResultReservedPoint);
                        }
                    }
                }
            }
            if (!CollectionUtils.isEmpty(pointListStatementPoint))
            {
                BonusPointsResult bonusPointsResultStatementPoint = new BonusPointsResult();

                List<PointResult> prList;

                Map<String, Object> statementPointMap = this.statementPointMap(pointListStatementPoint);

                prList = (List<PointResult>) statementPointMap.get("statementPointPrList");

                addOrUpdateList.add((Map<String, Object>) statementPointMap.get("statementPointAddOrUpdate"));

                deductAccountList.add((List<AccountEntry>) statementPointMap.get("statementPointAccountList"));

                accountWriteBackList.add((List<AccountWriteBack>) statementPointMap
                        .get("statementPointAccountWriteBackList"));

                if (!CollectionUtils.isEmpty(prList))
                {
                    for (PointResult pointResult : prList)
                    {
                        if (pointResult != null)
                        {
                            bonusPointsResultStatementPoint.setSourceTransNo(pointResult.getSourceTransNo());
                            bonusPointsResultStatementPoint.setAccountantDate(pointResult.getAccountantDate());
                            bonusPointsResultStatementPoint.setOrderNo(pointResult.getTransNo());
                            bonusPointsResultStatementPoint.setTradeType(2);
                            // bonusPointsResult3.setTransAmount(pointResult.getTransAmount());
                            bonusPointsResultStatementPoint.setRetCode(RespCode.SUCCESS.getCode());
                            // bonusPointsResult3.setTransPoint(pointResult.getTransAmount());
                            bonusPointsResultStatementPoint.setRetMsg("消费积分结单成功！");
                            bonusPointsResults.add(bonusPointsResultStatementPoint);
                        }
                    }
                }
            }

            // 混合接口统一处理
            this.bonusPointsProcessing(addOrUpdateList, deductAccountList, chargebackAccountList, accountWriteBack,
                    accountWriteBackList);

            for (int i = 0; i < listBonusPoints.size(); i++)
            {
                BonusPoints point = listBonusPoints.get(i);
                EsAssert.deleteKeyt(point.toString());

            }
        }
        catch (HsException e)
        {
            EsException.esThrowException(new Throwable().getStackTrace()[0], e.getErrorCode(), e.getMessage(), e);
        }
        catch (Exception e)
        {
            EsException.esThrowException(new Throwable().getStackTrace()[0], RespCode.PS_DB_SQL_ERROR.getCode(), e
                    .getMessage(), e);
        }
        // 返回
        return bonusPointsResults;
    }

    /**
     * 混合接口统一处理
     * 
     * @param addOrUpdateList
     * @param accountList
     * @param accountWriteBack
     * @param accountWriteBackList
     * @throws HsException
     */
    public void bonusPointsProcessing(List<Map<String, Object>> addOrUpdateList,
            List<List<AccountEntry>> deductAccountList, List<List<AccountEntry>> chargebackAccountList,
            List<AccountWriteBack> accountWriteBack, List<List<AccountWriteBack>> accountWriteBackList)
            throws HsException {

        try
        {
            if (deductAccountList != null && deductAccountList.size() > 0)
            {
                for (int i = 0; i < deductAccountList.size(); i++)
                {
                    List<AccountEntry> accountList = deductAccountList.get(i);
                    if (accountList != null && accountList.size() > 0)
                    {
                        // 调用账务系统记账接口实现记账
                        accountEntryService.deductAccount(accountList);
                    }
                }
            }

            if (chargebackAccountList != null && chargebackAccountList.size() > 0)
            {
                for (int i = 0; i < chargebackAccountList.size(); i++)
                {
                    List<AccountEntry> accountList = chargebackAccountList.get(i);
                    if (accountList != null && accountList.size() > 0)
                    {
                        // 调用账务系统记账接口实现记账
                        accountEntryService.chargebackAccount(accountList);
                    }
                }
            }

            if (addOrUpdateList != null && addOrUpdateList.size() > 0)
            {
                this.addOrUpdateListTransactional(addOrUpdateList);
            }

        }
        catch (HsException e)
        {
            if (accountWriteBack != null && accountWriteBack.size() > 0)
            {
                for (int i = 0; i < accountWriteBack.size(); i++)
                {
                    AccountWriteBack accountWriteBack1 = accountWriteBack.get(i);
                    // 调用账务系统冲正
                    accountEntryService.correctSingleAccount(accountWriteBack1);
                }
            }

            if (accountWriteBackList != null && accountWriteBackList.size() > 0)
            {
                for (int i = 0; i < accountWriteBackList.size(); i++)
                {
                    List<AccountWriteBack> accountWriteBack1 = accountWriteBackList.get(i);
                    // 调用账务系统冲正
                    accountEntryService.correctAccountList(accountWriteBack1);
                }
            }
            throw e;
        }
        catch (Exception e)
        {
            if (accountWriteBack != null && accountWriteBack.size() > 0)
            {
                for (int i = 0; i < accountWriteBack.size(); i++)
                {
                    AccountWriteBack accountWriteBack1 = accountWriteBack.get(i);
                    // 调用账务系统冲正
                    accountEntryService.correctSingleAccount(accountWriteBack1);
                }
            }

            if (accountWriteBackList != null && accountWriteBackList.size() > 0)
            {
                for (int i = 0; i < accountWriteBackList.size(); i++)
                {
                    List<AccountWriteBack> accountWriteBack1 = accountWriteBackList.get(i);
                    // 调用账务系统冲正
                    accountEntryService.correctAccountList(accountWriteBack1);
                }
            }
            // 抛出 异常
            EsException.esThrowException(new Throwable().getStackTrace()[0], EsRespCode.PS_DB_SQL_ERROR.getCode(), e
                    .getMessage(), e);
        }

    }

    /**
     * 消费积分预扣(可批量)
     * 
     * @param pointList
     *            预扣实体
     * @return PointResult
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<PointResult> reservedPoint(List<Point> pointList) throws HsException {

        List<PointResult> prList = new ArrayList<>();

        // 查询是否已处理
        List<QuetyPaying> quetyPayingList = new ArrayList<>();
        for (Point point : pointList)
        {
            QuetyPaying quetyPaying = new QuetyPaying();
            // 订单号
            quetyPaying.setOrderNo(point.getOrderNo());
            // 原始交易号
            quetyPaying.setSourceTransNo(point.getSourceTransNo());
            // 交易类型
            quetyPaying.setTransType(point.getTransType());
            quetyPayingList.add(quetyPaying);
        }
        quetyPayingList = queryPaying(quetyPayingList);
        if (!quetyPayingList.isEmpty())
        {
            for (int i = 0; i < quetyPayingList.size(); i++)
            {
                QuetyPaying quetyPaying = quetyPayingList.get(0);
                PointResult pointResult = new PointResult();
                pointResult.setPerPoint(quetyPaying.getPerPoint());
                pointResult.setTransNo(quetyPaying.getTransNo());
                pointResult.setAccountantDate(DateUtil.DateToString(quetyPaying.getSourceTransDate(),
                        DateUtil.DEFAULT_DATE_TIME_FORMAT));
                prList.add(pointResult);
            }
            return prList;
        }

        // 处理类返回封装Map
        Map<String, Object> returnMap = reservedPointMap(pointList);

        if (returnMap != null && returnMap.size() > 0)
        {
            // 账务记账类集合
            List<AccountEntry> accountList = (List<AccountEntry>) returnMap.get("reservedPointAccountList");

            // 新增修改数据库操作Map
            Map<String, Object> addOrUpdate = (Map<String, Object>) returnMap.get("reservedPointAddOrUpdate");
            
            Boolean boo = (Boolean)returnMap.get("boo");
            
            Point point = (Point)returnMap.get("booPoint");

            // 到账务冲正集合
            List<AccountWriteBack> accountWriteBackList = (List<AccountWriteBack>) returnMap
                    .get("reservedPointAccountWriteBackList");

            // 处理返回集合
            prList = (List<PointResult>) returnMap.get("reservedPointPrList");

            try
            {
                if (accountList != null && accountList.size() != 0)
                {
                    // 调用账务系统记账接口实现记账
                    accountEntryService.deductAccount(accountList);
                }
                // 批量新增或更新
                addOrUpdateTransactional(addOrUpdate);
                if (boo)
                {
                    AccountQuotaService.quotaHandle(point.getPerCustId(), point.getTransAmount());
                }
            }
            catch (HsException he)
            {
                // 抛出互生异常
                EsException.esThrowException(new Throwable().getStackTrace()[0], he.getErrorCode(), he.getMessage(), he);

            }
            catch (Exception e)
            {
                // 调用账务系统冲正
                accountEntryService.correctAccountList(accountWriteBackList);
                // 抛出 异常
                EsException.esThrowException(new Throwable().getStackTrace()[0], EsRespCode.PS_DB_SQL_ERROR.getCode(),
                        e.getMessage(), e);
            }
            return prList;
        }
        return null;
    }

    /**
     * 消费积分预扣(可批量)
     * 
     * @param pointList
     *            预扣实体
     * @return PointResult
     */
    public Map<String, Object> reservedPointMap(List<Point> pointList) throws HsException {
        List<PointResult> prList = new ArrayList<>();

        // 处理类返回封装Map
        Map<String, Object> reservedPointMap = new HashMap<>();

        // 新增修改数据库操作Map
        Map<String, Object> addOrUpdate = new HashMap<>();
        try
        {
            List<Alloc> allAllocList = new ArrayList<>();

            List<EntryAllot> entryAllotList = new ArrayList<>();
            List<AccountWriteBack> accountWriteBackList = new ArrayList<>();
            if (CollectionUtils.isEmpty(pointList))
            {
                EsException.esHsThrowException(new Throwable().getStackTrace()[0], EsRespCode.PS_PARAM_ERROR.getCode(),
                        "对象参数错误");
            }
            else
            {
                // 积分详单实体类集合
                List<PointDetail> pdList = new ArrayList<>();

                Boolean boo = false;
                
                // 积分分录实体集合
                List<Alloc> allocList;
                for (Point point : pointList)
                {
                    // 通过交易类型判断订单状态为预留
                    if (TransTypeUtil.transStatus(point.getTransType()).equals(Constants.POINT_BUSS_TYPE3))
                    {
                        // 企业积分款不能为空
                        EsAssert.notNull(point.getEntPoint(), RespCode.PS_PARAM_ERROR, "企业积分应付款不能为空");
                    }

                    // 校验参数是否合法,不合法则抛出异常
                    ValidateModelUtil.validateModel(point);

                    EsAssert.repeatSubmit(point.toString());
                    
                    // 通过交易类型判断交易方式（1、互生币 2、网银 3、现金）
                    if (TransTypeUtil.transWay(point.getTransType()).equals(Constants.POINT_HSB))
                    {
                        // 互生币支付限额验证
                        boo = AccountQuotaService.checkCustQuota(point.getPerCustId(), point.getTransAmount());
                        if(boo)
                        {
                            reservedPointMap.put("booPoint", point);
                        }
                    }

                    // 积分返回结果
                    PointResult pointResult = new PointResult();
                    // 积分详单实体类
                    PointDetail pointDetail = new PointDetail();

                    // 把合法对应的参数拷贝到积分明细实体中
                    BeanCopierUtils.copy(point, pointDetail);

                    // 创建消费积分信息处理类
                    PointHandle pointHandle = new PointHandle();

                    // 调用积分明细处理方法, 设置积分明细实体属性值
                    pointHandle.pointDispose(pointDetail, point);

                    // 调用积分分配方法,传递积分明细对象,返回分录list
                    allocList = pointAllocService.allocPoint(pointDetail);

                    // 调用消费积分分录数据处理方法,传入分配list,积分明细实体
                    pointHandle.pointEntry(allocList, pointDetail);
                    pdList.add(pointDetail);

                    // 接入的返回值处理
                    pointHandle.pointResultDispose(pointResult, pointDetail);
                    prList.add(pointResult);
                    allAllocList.addAll(allocList);
                    // 改造后的分录数据结构
                    EntryAllot entryAllot = EntryHandle.mergeEntryData(allocList, Constants.HSB_POINT);
                    entryAllot.setPerCustId(point.getPerCustId());
                    entryAllot.setEntCustId(point.getEntCustId());
                    entryAllot.setEntSubServiceFee(BigDecimal.ZERO);
                    entryAllot.setEntAddServiceFee(BigDecimal.ZERO);
                    // pointAllocMapper.insertEntry(entryAllot);
                    entryAllotList.add(entryAllot);

                    // 组装账务冲正对象参数
                    AccountWriteBack accountWriteBack = pointHandle.writeBackPoint(pointDetail);

                    accountWriteBackList.add(accountWriteBack);
                }
                // 实时批量插入分配后的积分数据
                addOrUpdate.put("insertEntrys", entryAllotList);

                // 批量消费者积分(预留、网上积分登记、预付定金)
                addOrUpdate.put("points", pdList);

                // 账务记账数据处理,传入分录分配list
                List<AccountEntry> accountList = AccountHandle.accountPointEntry(allAllocList);

                reservedPointMap.put("reservedPointAddOrUpdate", addOrUpdate);

                reservedPointMap.put("reservedPointAccountList", accountList);

                reservedPointMap.put("reservedPointAccountWriteBackList", accountWriteBackList);

                reservedPointMap.put("reservedPointPrList", prList);
                
                reservedPointMap.put("boo", boo);

                for (Point point : pointList)
                {
                    EsAssert.deleteKeyt(point.toString());
                }
            }
        }
        catch (HsException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            EsException.esThrowException(new Throwable().getStackTrace()[0], RespCode.PS_DB_SQL_ERROR.getCode(), e
                    .getMessage(), e);
        }
        return reservedPointMap;
    }

    /**
     * 消费积分结单(可批量)
     * 
     * @param pointList
     *            积分实体类
     * @return PointResults
     */
    @Override
    public List<PointResult> statementPoint(List<Point> pointList) throws HsException {
        // 创建返回值对象
        List<PointResult> prList = new ArrayList<>();
        // 新增修改数据库操作Map
        Map<String, Object> addOrUpdate = new HashMap<>();

        try
        {
            // 校验参数对象
            if (CollectionUtils.isEmpty(pointList))
            {
                EsException.esHsThrowException(new Throwable().getStackTrace()[0], EsRespCode.PS_PARAM_ERROR.getCode(),
                        "对象参数错误");
            }
            else
            {
                // 创建积分明细信息存储对象
                List<PointDetail> pdList = new ArrayList<>();

                // 查询是否已结单条件封装
                List<QuetyPaying> quetyPayingList = new ArrayList<QuetyPaying>();
                for (Point point : pointList)
                {
                    QuetyPaying quetyPaying = new QuetyPaying();
                    // 订单号
                    quetyPaying.setOrderNo(point.getOrderNo());
                    // 原始交易号
                    quetyPaying.setSourceTransNo(point.getSourceTransNo());
                    // 交易类型
                    quetyPaying.setTransType(point.getTransType());
                    quetyPayingList.add(quetyPaying);
                }
                quetyPayingList = queryPaying(quetyPayingList);
                if (!quetyPayingList.isEmpty())
                {
                    for (int i = 0; i < quetyPayingList.size(); i++)
                    {
                        QuetyPaying quetyPaying = quetyPayingList.get(0);
                        PointResult pointResult = new PointResult();
                        pointResult.setPerPoint(quetyPaying.getPerPoint());
                        pointResult.setTransNo(quetyPaying.getTransNo());
                        pointResult.setAccountantDate(DateUtil.DateToString(quetyPaying.getSourceTransDate(),
                                DateUtil.DEFAULT_DATE_TIME_FORMAT));
                        prList.add(pointResult);
                    }
                    return prList;
                }

                // 创建分录信息存储对象
                List<Alloc> allocList = new ArrayList<>();

                List<EntryAllot> entryAllotList = new ArrayList<EntryAllot>();

                List<AccountWriteBack> accountWriteBackList = new ArrayList<>();

                for (Point point : pointList)
                {
                    // 校验参数是否合法,不合法则抛出异常
                    ValidateModelUtil.validateModel(point);

                    if (EsTools.isEmpty(point.getOldTransNo()))
                    {
                        EsException.esHsThrowException(new Throwable().getStackTrace()[0], EsRespCode.PS_PARAM_ERROR
                                .getCode(), "原预留消费积分单流水号不能为空");
                    }
                    EsAssert.repeatSubmit(point.toString());

                    PointResult pointResult = new PointResult();
                    PointDetail pointDetail = new PointDetail();

                    PointDetail oldOrder = pointMapper.queryReserveOrder(point);

                    // 查询分录表
                    EntryAllot oldEntryAllot = pointAllocService.queryOnlineEntryNo(oldOrder);

                    if (EsTools.isEmpty(oldOrder))
                    {
                        // 判断原积分订单为空
                        EsException.esHsThrowException(new Throwable().getStackTrace()[0], EsRespCode.PS_ORDER_NOT_FOUND
                                .getCode(), "找不到原订单");

                        // } else if (oldOrder.getTransStatus() != Integer
                        // .parseInt(Constants.POINT_BUSS_TYPE3)) {
                        // // 判断原积分单不是预留单, 则抛出互生异常
                        // EsException.esThrowException(new
                        // Throwable().getStackTrace()[0],
                        // EsRespCode.PS_HAS_THE_CANCELLATION.getCode(),
                        // "原订单不是预留单,无法预留结单");
                    }
                    else
                    {
                        // 把合法对应的参数拷贝到积分明细实体中
                        BeanCopierUtils.copy(point, pointDetail);

                        // 创建消费积分处理类
                        PointHandle pointHandle = new PointHandle();

                        // 调用积分明细处理方法, 设置积分明细实体属性值
                        pointHandle.pointDispose(pointDetail, point);

                        // 订单完成时间
                        pointDetail.setOrderFinishDate(DateUtil.StringToDateHMS(point.getSourceTransDate()));

                        // 批次号
                        pointDetail.setBatchNo(point.getSourceTransDate().substring(0, 10));

                        // 订单金额
                        pointDetail.setOrderAmount(point.getOrderAmount());

                        // 调用积分分配方法,传递积分明细对象,返回分录list
                        List<Alloc> allocPointList = pointAllocService.allocPoint(pointDetail);

                        // 调用消费积分分录数据处理方法,传入分配list,积分明细实体
                        pointHandle.pointEntry(allocPointList, pointDetail);
                        pdList.add(pointDetail);
                        allocList.addAll(allocPointList);

                        // 改造后的分录数据结构
                        EntryAllot newEntryAllot = EntryHandle.mergeEntryData(allocList, Constants.HSB_POINT);
                        // 设置原分路流水号
                        newEntryAllot.setRelEntryNo(oldEntryAllot.getEntryNo());
                        newEntryAllot.setEntryNo(oldEntryAllot.getEntryNo());
                        newEntryAllot.setBatchNo(pointDetail.getBatchNo());
                        // 计算出商业服务
                        EntryAllot entryAllot = EntryHandle.cashBusinessServerFee(point.getTransType(), new BigDecimal(
                                point.getTransAmount()), newEntryAllot.getEntSubPointAmount(), point.getIsDeduction());
                        if (entryAllot != null)
                        {
                            newEntryAllot.setSettleAmount(entryAllot.getSettleAmount());
                            newEntryAllot.setEntSubServiceFee(entryAllot.getEntSubServiceFee());
                        }
                        // pointAllocMapper.updateEntryDetail(newEntryAllot);
                        entryAllotList.add(newEntryAllot);
                        pointHandle.relEntryNoHandleBack(allocList, oldEntryAllot);

                        pointHandle.relEntryNoHandle(allocList, oldEntryAllot);
                        // 接入的返回值处理
                        pointHandle.pointResultDispose(pointResult, pointDetail);
                        prList.add(pointResult);
                        // 组装账务冲正对象参数
                        AccountWriteBack accountWriteBack = pointHandle.writeBackPoint(pointDetail);

                        accountWriteBackList.add(accountWriteBack);
                    }
                }

                // 批量线上交易分录信息结算修改
                // pointAllocMapper.updateEntryDetails(entryAllotList);

                addOrUpdate.put("updateEntryDetails", entryAllotList);

                // 积分明细信息入库
                // pointMapper.batUpdateStatus(pdList);

                addOrUpdate.put("batUpdateStatus", pdList);

                // 账务记账数据处理,传入分录分配list
                List<AccountEntry> accountList = AccountHandle.accountPointEntry(allocList);

                try
                {
                    if (accountList != null && accountList.size() != 0)
                    {
                        // 调用账务系统记账接口实现记账
                        accountEntryService.deductAccount(accountList);
                    }
                    // 批量新增或更新
                    addOrUpdateTransactional(addOrUpdate);
                }
                catch (HsException he)
                {
                    // 抛出互生异常
                    EsException
                            .esThrowException(new Throwable().getStackTrace()[0], he.getErrorCode(), he.getMessage(), he);

                }
                catch (Exception e)
                {
                    // 调用账务系统冲正
                    accountEntryService.correctAccountList(accountWriteBackList);
                    // 抛出 异常
                    EsException.esThrowException(new Throwable().getStackTrace()[0], EsRespCode.PS_DB_SQL_ERROR
                            .getCode(), e.getMessage(), e);
                }

            }
            for (Point point : pointList)
            {
                EsAssert.deleteKeyt(point.toString());
            }
        }
        catch (HsException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            EsException.esThrowException(new Throwable().getStackTrace()[0], RespCode.PS_DB_SQL_ERROR.getCode(), e
                    .getMessage(), e);
        }
        return prList;
    }

    /**
     * 消费积分结单(可批量)
     * 
     * @param pointList
     *            积分实体类
     * @return PointResults
     */
    public Map<String, Object> statementPointMap(List<Point> pointList) throws HsException {
        // 创建业务返回值对象
        List<PointResult> prList = new ArrayList<>();

        // 处理返回集合
        Map<String, Object> statementPointMap = new HashMap<>();

        // 新增修改数据库操作Map
        Map<String, Object> addOrUpdate = new HashMap<>();

        try
        {
            // 校验参数对象
            if (CollectionUtils.isEmpty(pointList))
            {
                EsException.esHsThrowException(new Throwable().getStackTrace()[0], EsRespCode.PS_PARAM_ERROR.getCode(),
                        "对象参数错误");
            }
            else
            {
                // 创建积分明细信息存储对象
                List<PointDetail> pdList = new ArrayList<>();

                // 创建分录信息存储对象
                List<Alloc> allocList = new ArrayList<>();

                List<EntryAllot> entryAllotList = new ArrayList<EntryAllot>();

                List<AccountWriteBack> accountWriteBackList = new ArrayList<>();

                for (Point point : pointList)
                {
                    // 校验参数是否合法,不合法则抛出异常
                    ValidateModelUtil.validateModel(point);

                    if (EsTools.isEmpty(point.getOldTransNo()))
                    {
                        EsException.esHsThrowException(new Throwable().getStackTrace()[0], EsRespCode.PS_PARAM_ERROR
                                .getCode(), "原预留消费积分单流水号不能为空");
                    }
                    PointResult pointResult = new PointResult();
                    PointDetail pointDetail = new PointDetail();

                    PointDetail oldOrder = pointMapper.queryReserveOrder(point);

                    // 查询分录表
                    EntryAllot oldEntryAllot = pointAllocService.queryOnlineEntryNo(oldOrder);

                    if (EsTools.isEmpty(oldOrder))
                    {
                        // 判断原积分订单为空
                        EsException.esHsThrowException(new Throwable().getStackTrace()[0], EsRespCode.PS_ORDER_NOT_FOUND
                                .getCode(), "找不到原订单");
                    }
                    else
                    {
                        // 把合法对应的参数拷贝到积分明细实体中
                        BeanCopierUtils.copy(point, pointDetail);

                        // 创建消费积分处理类
                        PointHandle pointHandle = new PointHandle();

                        // 调用积分明细处理方法, 设置积分明细实体属性值
                        pointHandle.pointDispose(pointDetail, point);

                        // 订单完成时间
                        pointDetail.setOrderFinishDate(DateUtil.StringToDateHMS(point.getSourceTransDate()));

                        // 批次号
                        pointDetail.setBatchNo(point.getSourceTransDate().substring(0, 10));

                        // 订单金额
                        pointDetail.setOrderAmount(point.getOrderAmount());

                        // 调用积分分配方法,传递积分明细对象,返回分录list
                        List<Alloc> allocPointList = pointAllocService.allocPoint(pointDetail);

                        // 调用消费积分分录数据处理方法,传入分配list,积分明细实体
                        pointHandle.pointEntry(allocPointList, pointDetail);
                        pdList.add(pointDetail);
                        allocList.addAll(allocPointList);

                        // 改造后的分录数据结构
                        EntryAllot newEntryAllot = EntryHandle.mergeEntryData(allocList, Constants.HSB_POINT);
                        // 设置原分路流水号
                        newEntryAllot.setRelEntryNo(oldEntryAllot.getEntryNo());
                        newEntryAllot.setEntryNo(oldEntryAllot.getEntryNo());
                        // 计算出商业服务
                        EntryAllot entryAllot = EntryHandle.cashBusinessServerFee(point.getTransType(), new BigDecimal(
                                point.getTransAmount()), newEntryAllot.getEntSubPointAmount(), point.getIsDeduction());
                        if (entryAllot != null)
                        {
                            newEntryAllot.setSettleAmount(entryAllot.getSettleAmount());
                            newEntryAllot.setEntSubServiceFee(entryAllot.getEntSubServiceFee());
                        }
                        // pointAllocMapper.updateEntryDetail(newEntryAllot);
                        entryAllotList.add(newEntryAllot);
                        pointHandle.relEntryNoHandleBack(allocList, oldEntryAllot);

                        pointHandle.relEntryNoHandle(allocList, oldEntryAllot);
                        // 接入的返回值处理
                        pointHandle.pointResultDispose(pointResult, pointDetail);
                        prList.add(pointResult);
                        // 组装账务冲正对象参数
                        AccountWriteBack accountWriteBack = pointHandle.writeBackPoint(pointDetail);

                        accountWriteBackList.add(accountWriteBack);
                    }
                }

                addOrUpdate.put("updateEntryDetails", entryAllotList);

                addOrUpdate.put("batUpdateStatus", pdList);

                // 账务记账数据处理,传入分录分配list
                List<AccountEntry> accountList = AccountHandle.accountPointEntry(allocList);

                statementPointMap.put("statementPointAddOrUpdate", addOrUpdate);

                statementPointMap.put("statementPointAccountList", accountList);

                statementPointMap.put("statementPointAccountWriteBackList", accountWriteBackList);

                statementPointMap.put("statementPointPrList", prList);
            }
        }
        catch (HsException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            EsException.esThrowException(new Throwable().getStackTrace()[0], RespCode.PS_DB_SQL_ERROR.getCode(), e
                    .getMessage(), e);
        }
        return statementPointMap;
    }

    /**
     * 一次性交易完成(如：货到付款)
     * 
     * @param point
     *            积分实体类
     * @throws HsException
     * @see com.gy.hsxt.es.api.IEsPointService#point(com.gy.hsxt.es.bean.Point)
     */
    @Override
    public PointResult point(Point point) throws HsException {
        // 校验参数是否合法,不合法则抛出异常
        ValidateModelUtil.validateModel(point);
        // 创建积分返回值实体类
        PointResult pointResult = new PointResult();

        Map<String, Object> addOrUpdate = new HashMap<>();

        try
        {
            // 查询是否已退款条件封装
            List<QuetyPaying> quetyPayingList = new ArrayList<QuetyPaying>();
            QuetyPaying quetyPaying = new QuetyPaying();
            // 订单号
            quetyPaying.setOrderNo(point.getOrderNo());
            // 原始交易号
            quetyPaying.setSourceTransNo(point.getSourceTransNo());
            // 交易类型
            quetyPaying.setTransType(point.getTransType());
            quetyPayingList.add(quetyPaying);
            quetyPayingList = queryPaying(quetyPayingList);
            if (!quetyPayingList.isEmpty())
            {
                quetyPaying = quetyPayingList.get(0);
                pointResult.setPerPoint(quetyPaying.getPerPoint());
                pointResult.setTransNo(quetyPaying.getTransNo());
                pointResult.setAccountantDate(DateUtil.DateToString(quetyPaying.getSourceTransDate(),
                        DateUtil.DEFAULT_DATE_TIME_FORMAT));

                return pointResult;
            }
            EsAssert.repeatSubmit(point.toString());

            // 互生币支付限额验证
            // start--commented by liuzh on 2016-05-18 修改为事务提交成功后,再执行
            /*
             * if
             * (TransTypeUtil.transWay(point.getTransType()).equals(Constants.
             * POINT_HSB)) { // 互生币支付限额验证 boolean boo =
             * AccountQuotaService.checkCustQuota(point.getPerCustId(),
             * point.getTransAmount()); if (boo) {
             * AccountQuotaService.quotaHandle(point.getPerCustId(),
             * point.getTransAmount()); } }
             */
            // end--commented by liuzh on 2016-05-18

            // start--added by liuzh on 2016-05-18 提交成功后,再设置单日互生币已支付金额
            boolean boo = false;
            if (TransTypeUtil.transWay(point.getTransType()).equals(Constants.POINT_HSB))
            {
                // 互生币支付限额验证
                boo = AccountQuotaService.checkCustQuota(point.getPerCustId(), point.getTransAmount());
            }

            // 创建积分明细实体类
            PointDetail pointDetail = new PointDetail();

            // 把合法参数拷贝到对应的积分明细实体中
            BeanCopierUtils.copy(point, pointDetail);

            // 创建消费积分处理类
            PointHandle pointHandle = new PointHandle();

            // 调用积分明细处理方法, 设置积分明细实体属性值
            pointHandle.pointDispose(pointDetail, point);

            // 订单完成时间
            pointDetail.setOrderFinishDate(DateUtil.StringToDateHMS(point.getSourceTransDate()));

            // 调用积分分配方法,传递积分明细对象,返回分录list
            List<Alloc> pointList = pointAllocService.allocPoint(pointDetail);

            pointDetail.setPerCustId(point.getPerCustId());

            // 调用消费积分分录数据处理方法,传入分配list,积分明细实体
            pointHandle.pointEntry(pointList, pointDetail);

            // 订单完成时间
            pointDetail.setOrderFinishDate(DateUtil.StringToDateHMS(point.getSourceTransDate()));

            // 批次号
            pointDetail.setBatchNo(point.getSourceTransDate().substring(0, 10));

            // 订单金额
            pointDetail.setOrderAmount(point.getOrderAmount());

            // 积分明细实体绑定的数据插入到数据库积分明细表
            // pointMapper.point(pointDetail);

            addOrUpdate.put("point", pointDetail);

            // 处理好的分录数据插入到数据库分录表
            // pointAllocService.getAllocDetail(pointList);

            // 改造后的分录数据结构
            EntryAllot entryAllot = EntryHandle.mergeEntryData(pointList, Constants.HSB_POINT);
            // 计算出商业服务
            EntryAllot entryAllotb = EntryHandle.cashBusinessServerFee(point.getTransType(), new BigDecimal(point
                    .getTransAmount()), entryAllot.getEntSubPointAmount(), point.getIsDeduction());
            if (entryAllotb != null)
            {
                entryAllot.setSettleAmount(entryAllotb.getSettleAmount());
                entryAllot.setEntSubServiceFee(entryAllotb.getEntSubServiceFee());
            }
            entryAllot.setPerCustId(point.getPerCustId());
            entryAllot.setEntCustId(point.getEntCustId());
            // pointAllocService.getEntryDetail(entryAllot);
            // pointAllocMapper.insertEntry(entryAllot);

            addOrUpdate.put("insertEntry", entryAllot);

            // 账务记账数据处理,传入分录分配list
            List<AccountEntry> accountList = AccountHandle.accountPointEntry(pointList);

            try
            {
                if (accountList != null && accountList.size() != 0)
                {
                    // 调用账务系统记账接口实现记账
                    accountEntryService.deductAccount(accountList);
                    // end--added by liuzh on 2016-05-18
                }
                addOrUpdateTransactional(addOrUpdate);
                if (boo)
                {
                    AccountQuotaService.quotaHandle(point.getPerCustId(), point.getTransAmount());
                }
            }
            catch (HsException he)
            {
                // 抛出互生异常
                EsException.esThrowException(new Throwable().getStackTrace()[0], he.getErrorCode(), he.getMessage(), he);

            }
            catch (Exception e)
            {
                // 组装账务冲正对象参数
                AccountWriteBack accountWriteBack = pointHandle.writeBackPoint(pointDetail);

                // 调用账务系统冲正
                accountEntryService.correctSingleAccount(accountWriteBack);

                // 抛出 异常
                EsException.esThrowException(new Throwable().getStackTrace()[0], EsRespCode.PS_DB_SQL_ERROR.getCode(),
                        e.getMessage(), e);
            }
            // 返回数据处理,数据组装
            pointHandle.pointResultDispose(pointResult, pointDetail);
            EsAssert.deleteKeyt(point.toString());
        }
        catch (HsException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            EsException.esThrowException(new Throwable().getStackTrace()[0], RespCode.PS_DB_SQL_ERROR.getCode(), e
                    .getMessage(), e);
        }
        // 返回
        return pointResult;
    }

    /**
     * 一次性交易完成(如：货到付款)
     * 
     * @param point
     *            积分实体类
     * @throws HsException
     * @see com.gy.hsxt.es.api.IEsPointService#point(com.gy.hsxt.es.bean.Point)
     */
    public Map<String, Object> pointMap(Point point) throws HsException {
        ValidateModelUtil.validateModel(point);
        // 创建积分返回值实体类
        PointResult pointResult = new PointResult();

        // 处理返回集合
        Map<String, Object> pointMap = new HashMap<>();

        Map<String, Object> addOrUpdate = new HashMap<>();

        try
        {
            // 互生币支付限额验证
            if (TransTypeUtil.transWay(point.getTransType()).equals(Constants.POINT_HSB))
            {
                // 互生币支付限额验证
                boolean boo = AccountQuotaService.checkCustQuota(point.getPerCustId(), point.getTransAmount());
            }

            // 创建积分明细实体类
            PointDetail pointDetail = new PointDetail();

            // 把合法参数拷贝到对应的积分明细实体中
            BeanCopierUtils.copy(point, pointDetail);

            // 创建消费积分处理类
            PointHandle pointHandle = new PointHandle();

            // 调用积分明细处理方法, 设置积分明细实体属性值
            pointHandle.pointDispose(pointDetail, point);

            // 订单完成时间
            pointDetail.setOrderFinishDate(DateUtil.StringToDateHMS(point.getSourceTransDate()));

            // 调用积分分配方法,传递积分明细对象,返回分录list
            List<Alloc> pointList = pointAllocService.allocPoint(pointDetail);

            pointDetail.setPerCustId(point.getPerCustId());

            // 调用消费积分分录数据处理方法,传入分配list,积分明细实体
            pointHandle.pointEntry(pointList, pointDetail);

            // 订单完成时间
            pointDetail.setOrderFinishDate(DateUtil.StringToDateHMS(point.getSourceTransDate()));

            // 批次号
            pointDetail.setBatchNo(point.getSourceTransDate().substring(0, 10));

            // 订单金额
            pointDetail.setOrderAmount(point.getOrderAmount());

            addOrUpdate.put("point", pointDetail);

            // 改造后的分录数据结构
            EntryAllot entryAllot = EntryHandle.mergeEntryData(pointList, Constants.HSB_POINT);
            // 计算出商业服务
            EntryAllot entryAllotb = EntryHandle.cashBusinessServerFee(point.getTransType(), new BigDecimal(point
                    .getTransAmount()), entryAllot.getEntSubPointAmount(), point.getIsDeduction());
            if (entryAllotb != null)
            {
                entryAllot.setSettleAmount(entryAllotb.getSettleAmount());
                entryAllot.setEntSubServiceFee(entryAllotb.getEntSubServiceFee());
            }
            entryAllot.setPerCustId(point.getPerCustId());
            entryAllot.setEntCustId(point.getEntCustId());

            addOrUpdate.put("insertEntry", entryAllot);

            // 组装账务冲正对象参数
            AccountWriteBack accountWriteBack = pointHandle.writeBackPoint(pointDetail);

            // 账务记账数据处理,传入分录分配list
            List<AccountEntry> accountList = AccountHandle.accountPointEntry(pointList);

            // 返回数据处理,数据组装
            pointHandle.pointResultDispose(pointResult, pointDetail);

            pointMap.put("pointAddOrUpdate", addOrUpdate);

            pointMap.put("pointAccountList", accountList);

            pointMap.put("pointAccountWriteBack", accountWriteBack);

            pointMap.put("pointPointResult", pointResult);

        }
        catch (HsException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            EsException.esThrowException(new Throwable().getStackTrace()[0], RespCode.PS_DB_SQL_ERROR.getCode(), e
                    .getMessage(), e);
        }
        // 返回
        return pointMap;
    }

    /**
     * 退货(全部退、部分退)
     * 
     * @param back
     *            退货实体
     * @return BackResult
     */
    @Override
    public BackResult backPoint(Back back) throws HsException {
        ValidateModelUtil.validateModel(back); // 校验参数
        BackResult backResult = new BackResult();

        Map<String, Object> addOrUpdate = new HashMap<>();

        try
        {
            // 查询是否已退款条件封装
            List<QuetyPaying> quetyPayingList = new ArrayList<QuetyPaying>();
            QuetyPaying quetyPaying = new QuetyPaying();
            // 订单号
            quetyPaying.setOrderNo(back.getOrderNo());
            // 原始交易号
            quetyPaying.setSourceTransNo(back.getSourceTransNo());
            // 交易类型
            quetyPaying.setTransType(back.getTransType());
            quetyPayingList.add(quetyPaying);
            quetyPayingList = queryPaying(quetyPayingList);
            if (!quetyPayingList.isEmpty())
            {
                quetyPaying = quetyPayingList.get(0);
                backResult.setPerPoint(quetyPaying.getPerPoint());
                backResult.setTransNo(quetyPaying.getTransNo());
                backResult.setAccountantDate(DateUtil.DateToString(quetyPaying.getSourceTransDate(),
                        DateUtil.DEFAULT_DATE_TIME_FORMAT));

                return backResult;
            }
            EsAssert.repeatSubmit(back.toString());

            PointDetail oldOrder = pointMapper.queryOldOrder(back.getOldTransNo());
            if (EsTools.isEmpty(oldOrder))
            {
                EsException.esHsThrowException(new Throwable().getStackTrace()[0], EsRespCode.PS_ORDER_NOT_FOUND
                        .getCode(), "原订单不存在,无法退货");
                // } else if (oldOrder.getTransStatus() != 0 &&
                // oldOrder.getTransStatus() != 4) {
                // EsException.esThrowException(new
                // Throwable().getStackTrace()[0],
                // EsRespCode.PS_HAS_THE_CANCELLATION.getCode(),
                // "原订单不是正常状态、预留结单状态,无法退货");
            }
            else
            {
                List<AccountEntry> accountList;
                BackDetail backDetail = new BackDetail();
                BeanCopierUtils.copy(oldOrder, backDetail);
                BeanCopierUtils.copy(back, backDetail);
                backDetail.setSourceTransDate(Timestamp.valueOf(back.getSourceTransDate()));
                PointHandle pointHandle = new PointHandle();
                pointHandle.backDispose(backDetail, oldOrder);
                BigDecimal pointTransAmount = oldOrder.getTransAmount();
                BigDecimal backTransAmount = new BigDecimal(back.getTransAmount());
                BigDecimal transAmount = Compute.roundFloor(pointTransAmount, Constants.SURPLUS_TWO);
                BigDecimal backAmount = Compute.roundFloor(backTransAmount, Constants.SURPLUS_TWO);

                // 查找分录
                EntryAllot oldEntryAllot = pointAllocService.queryOnlineEntryNo(oldOrder);

                if (transAmount.compareTo(backAmount) == 1) // 部分退货
                {
                    BigDecimal subAmount = Compute.sub(transAmount, backAmount);
                    BigDecimal amount = Compute.roundFloor(subAmount, Constants.SURPLUS_SIX);

                    // 退货明细实体设置原订单金额
                    backDetail.setOldTransAmount(amount);
                    backDetail.setTransAmount(new BigDecimal(back.getTransAmount()));
                    backDetail.setPerPoint(new BigDecimal(back.getBackPoint()));
                    backDetail.setEntPoint(Compute.roundFloor(new BigDecimal(back.getBackPoint()).multiply(BigDecimal
                            .valueOf(2)), Constants.SURPLUS_TWO));
                    List<Alloc> allocList = pointAllocService.allocBackPoint(backDetail, oldOrder); // 计算部分退货
                    pointHandle.relEntryNoHandle(allocList, oldEntryAllot);

                    pointHandle.backEntry(allocList, backDetail, oldOrder);

                    // pointMapper.backPoint(backDetail);

                    addOrUpdate.put("backPoint", backDetail);

                    // pointAllocService.getAllocDetail(allocList);
                    oldOrder.setStatus(Integer.parseInt(Constants.POINT_BUSS_TYPE6));

                    // pointMapper.updatePoint(oldOrder);
                    oldOrder.setTransAmount(amount);

                    addOrUpdate.put("updatePoint", oldOrder);

                    // 改造后的分录数据结构
                    EntryAllot entryAllot = EntryHandle.mergeEntryData(allocList, Constants.HSB_POINT_BACK);
                    entryAllot.setPerCustId(oldOrder.getPerCustId());
                    // 部分退货修改第四位
                    StringBuilder sb = new StringBuilder(entryAllot.getTransType());

                    entryAllot.setTransType(String.valueOf(sb.replace(3, 4, Constants.POINT_BUSS_TYPE6)));
                    entryAllot.setPerCustId(oldOrder.getPerCustId());
                    entryAllot.setEntCustId(oldOrder.getEntCustId());
                    EntryAllot bEntryAllot = EntryHandle.backBusinessServerFee(back.getTransType(), new BigDecimal(
                            back.getTransAmount()), entryAllot.getEntAddPointAmount(), oldOrder.getIsSettle(), oldOrder
                            .getIsDeduction());
                    if (bEntryAllot != null)
                    {
                        entryAllot.setEntAddServiceFee(bEntryAllot.getEntAddServiceFee());
                        entryAllot.setSettleAmount(bEntryAllot.getSettleAmount());
                    }

                    addOrUpdate.put("insertEntry", entryAllot);

                    accountList = AccountHandle.accountBackEntry(allocList);
                    // this.retreatAccountTally(accountList);

                    if (oldOrder.getIsSettle() == Constants.IS_SETTLE0)
                    {
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
                        addOrUpdate.put("insertPointAllotDaily", bat.getPointList());
                    }
                }
                else
                // 全部退货
                {
                    // 调用积分分配服务,计算部分退货, 存储在分录list
                    List<Alloc> allocList = pointAllocService.allocBackPoint(backDetail, oldOrder);
                    pointHandle.relEntryNoHandle(allocList, oldEntryAllot);

                    pointHandle.backEntry(allocList, backDetail, oldOrder);

                    // pointMapper.backPoint(backDetail);

                    addOrUpdate.put("backPoint", backDetail);

                    Map<String, Object> updateStatusMap = new HashMap<String, Object>();

                    updateStatusMap.put("POINT_BUSS_TYPE", Integer.parseInt(Constants.POINT_BUSS_TYPE2));

                    updateStatusMap.put("transNo", oldOrder.getTransNo());

                    updateStatusMap.put("tableName", Constants.T_PS_NDETAIL);

                    addOrUpdate.put("updateStatus", updateStatusMap);

                    // pointAllocService.getAllocDetail(allocList); // 退货分录入库
                    // pointMapper.updateStatus(Integer.parseInt(Constants.POINT_BUSS_TYPE2),
                    // oldOrder.getTransNo(),
                    // Constants.T_PS_NDETAIL); // 修改原单为已退货

                    // 改造后的分录数据结构
                    EntryAllot entryAllot = EntryHandle.mergeEntryData(allocList, Constants.HSB_POINT_BACK);
                    entryAllot.setPerCustId(oldOrder.getPerCustId());
                    entryAllot.setEntCustId(oldOrder.getEntCustId());
                    EntryAllot bEntryAllot = EntryHandle.cashBCBusinessServerFee(back.getTransType(), new BigDecimal(
                            back.getTransAmount()), entryAllot.getEntAddPointAmount(), oldOrder.getIsSettle(), oldOrder
                            .getIsDeduction());
                    if (bEntryAllot != null)
                    {
                        entryAllot.setEntAddServiceFee(bEntryAllot.getEntAddServiceFee());
                        entryAllot.setSettleAmount(bEntryAllot.getSettleAmount());
                    }
                    // pointAllocService.getEntryDetail(entryAllot);
                    // pointAllocMapper.insertEntry(entryAllot);

                    addOrUpdate.put("insertEntry", entryAllot);

                    accountList = AccountHandle.accountBackEntry(allocList);

                    // 调用账务
                    // this.retreatAccountTally(accountList);

                    if (oldOrder.getIsSettle() == Constants.IS_SETTLE0)
                    {
                        // 积分退回
                        PointAllot pointAllot = pointAllocService.getPointAllot(oldOrder.getTransNo());
                        if (!EsTools.isEmpty(pointAllot))
                        {
                            List<PointAllot> list = new ArrayList<>();
                            list.add(PointBackHandle.setPointAllot(pointAllot));
                            // pointAllocService.addPointAllotData(list);
                            // pointAllocMapper.insertPointAllotDaily(list);
                            addOrUpdate.put("insertPointAllotDaily", list);

                        }

                    }
                }

                try
                {
                    if (accountList != null && accountList.size() != 0)
                    {
                        // 调用账务系统记账接口实现记账
                        accountEntryService.chargebackAccount(accountList);
                    }
                    addOrUpdateTransactional(addOrUpdate);
                }
                catch (HsException he)
                {
                    // 抛出互生异常
                    EsException
                            .esThrowException(new Throwable().getStackTrace()[0], he.getErrorCode(), he.getMessage(), he);

                }
                catch (Exception e)
                {
                    // 组装账务冲正对象参数
                    AccountWriteBack accountWriteBack = pointHandle.writeBackPoint(backDetail);

                    // 调用账务系统冲正
                    accountEntryService.correctSingleAccount(accountWriteBack);

                    // 抛出 异常
                    EsException.esThrowException(new Throwable().getStackTrace()[0], EsRespCode.PS_DB_SQL_ERROR
                            .getCode(), e.getMessage(), e);
                }

                pointHandle.backResultDispose(backResult, backDetail);
            }
            EsAssert.deleteKeyt(back.toString());
        }
        catch (HsException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            EsException.esThrowException(new Throwable().getStackTrace()[0], RespCode.PS_DB_SQL_ERROR.getCode(), e
                    .getMessage(), e);
        }
        return backResult;
    }

    /**
     * 退货(全部退、部分退)
     * 
     * @param back
     *            退货实体
     * @return BackResult
     */
    public Map<String, Object> backPointMap(Back back) throws HsException {
        ValidateModelUtil.validateModel(back); // 校验参数

        BackResult backResult = new BackResult();

        Map<String, Object> backPointMap = new HashMap<>();

        Map<String, Object> addOrUpdate = new HashMap<>();

        try
        {

            PointDetail oldOrder = pointMapper.queryOldOrder(back.getOldTransNo());

            // 取第四位交易类型
            String transWay4 = TransTypeUtil.transStatus(back.getTransType());

            if (EsTools.isEmpty(oldOrder))
            {
                EsException.esHsThrowException(new Throwable().getStackTrace()[0], EsRespCode.PS_ORDER_NOT_FOUND
                        .getCode(), "原订单不存在,无法退货");
            }
            else
            {
                List<AccountEntry> accountList;
                BackDetail backDetail = new BackDetail();
                BeanCopierUtils.copy(oldOrder, backDetail);
                BeanCopierUtils.copy(back, backDetail);
                backDetail.setSourceTransDate(Timestamp.valueOf(back.getSourceTransDate()));
                PointHandle pointHandle = new PointHandle();
                pointHandle.backDispose(backDetail, oldOrder);
                BigDecimal pointTransAmount = oldOrder.getTransAmount();
                BigDecimal backTransAmount = new BigDecimal(back.getTransAmount());
                BigDecimal transAmount = Compute.roundFloor(pointTransAmount, Constants.SURPLUS_TWO);
                BigDecimal backAmount = Compute.roundFloor(backTransAmount, Constants.SURPLUS_TWO);

                // 查找分录
                EntryAllot oldEntryAllot = pointAllocService.queryOnlineEntryNo(oldOrder);

                if (transAmount.compareTo(backAmount) == 1) // 部分退货
                {
                    BigDecimal subAmount = Compute.sub(transAmount, backAmount);
                    BigDecimal amount = Compute.roundFloor(subAmount, Constants.SURPLUS_SIX);

                    // 退货明细实体设置原订单金额
                    backDetail.setOldTransAmount(amount);
                    backDetail.setTransAmount(new BigDecimal(back.getTransAmount()));
                    backDetail.setPerPoint(new BigDecimal(back.getBackPoint()));
                    backDetail.setEntPoint(Compute.roundFloor(new BigDecimal(back.getBackPoint()).multiply(BigDecimal
                            .valueOf(2)), Constants.SURPLUS_TWO));
                    List<Alloc> allocList = pointAllocService.allocBackPoint(backDetail, oldOrder); // 计算部分退货
                    pointHandle.relEntryNoHandle(allocList, oldEntryAllot);

                    pointHandle.backEntry(allocList, backDetail, oldOrder);

                    // 订金退款无积分扣除
                    if (Constants.TRANS_BACK5.equals(transWay4))
                    {
                        backDetail.setPerPoint(BigDecimal.ZERO);
                        backDetail.setEntPoint(BigDecimal.ZERO);
                    }

                    addOrUpdate.put("backPoint", backDetail);

                    oldOrder.setStatus(Integer.parseInt(Constants.POINT_BUSS_TYPE6));

                    oldOrder.setTransAmount(amount);

                    addOrUpdate.put("updatePoint", oldOrder);

                    // 改造后的分录数据结构
                    EntryAllot entryAllot = EntryHandle.mergeEntryData(allocList, Constants.HSB_POINT_BACK);
                    entryAllot.setPerCustId(oldOrder.getPerCustId());
                    // 部分退货修改第四位
                    StringBuilder sb = new StringBuilder(entryAllot.getTransType());

                    entryAllot.setTransType(String.valueOf(sb.replace(3, 4, Constants.POINT_BUSS_TYPE6)));
                    entryAllot.setPerCustId(oldOrder.getPerCustId());
                    entryAllot.setEntCustId(oldOrder.getEntCustId());
                    EntryAllot bEntryAllot = EntryHandle.backBusinessServerFee(back.getTransType(), new BigDecimal(
                            back.getTransAmount()), entryAllot.getEntAddPointAmount(), oldOrder.getIsSettle(), oldOrder
                            .getIsDeduction());
                    if (bEntryAllot != null)
                    {
                        entryAllot.setEntAddServiceFee(bEntryAllot.getEntAddServiceFee());
                        entryAllot.setSettleAmount(bEntryAllot.getSettleAmount());
                    }

                    entryAllot.setPerSubPointAmount(BigDecimal.ZERO);
                    entryAllot.setEntAddPointAmount(BigDecimal.ZERO);

                    addOrUpdate.put("insertEntry", entryAllot);

                    accountList = AccountHandle.accountBackEntry(allocList);
                    
                    if (oldOrder.getIsSettle() == Constants.IS_SETTLE0)
                    {
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
                        addOrUpdate.put("insertPointAllotDaily", bat.getPointList());
                    }
                }
                else
                // 全部退货
                {
                    // 调用积分分配服务,计算部分退货, 存储在分录list
                    List<Alloc> allocList = pointAllocService.allocBackPoint(backDetail, oldOrder);
                    pointHandle.relEntryNoHandle(allocList, oldEntryAllot);

                    pointHandle.backEntry(allocList, backDetail, oldOrder);

                    // 订金退款无积分扣除
                    if (Constants.TRANS_BACK5.equals(transWay4))
                    {
                        backDetail.setPerPoint(BigDecimal.ZERO);
                        backDetail.setEntPoint(BigDecimal.ZERO);
                    }

                    addOrUpdate.put("backPoint", backDetail);

                    Map<String, Object> updateStatusMap = new HashMap<String, Object>();

                    updateStatusMap.put("POINT_BUSS_TYPE", Integer.parseInt(Constants.POINT_BUSS_TYPE2));

                    updateStatusMap.put("transNo", oldOrder.getTransNo());

                    updateStatusMap.put("tableName", Constants.T_PS_NDETAIL);

                    addOrUpdate.put("updateStatus", updateStatusMap);

                    // 改造后的分录数据结构
                    EntryAllot entryAllot = EntryHandle.mergeEntryData(allocList, Constants.HSB_POINT_BACK);
                    entryAllot.setPerCustId(oldOrder.getPerCustId());
                    entryAllot.setEntCustId(oldOrder.getEntCustId());
                    EntryAllot bEntryAllot = EntryHandle.cashBCBusinessServerFee(back.getTransType(), new BigDecimal(
                            back.getTransAmount()), entryAllot.getEntAddPointAmount(), oldOrder.getIsSettle(), oldOrder
                            .getIsDeduction());
                    if (bEntryAllot != null)
                    {
                        entryAllot.setEntAddServiceFee(bEntryAllot.getEntAddServiceFee());
                        entryAllot.setSettleAmount(bEntryAllot.getSettleAmount());
                    }

                    entryAllot.setPerSubPointAmount(BigDecimal.ZERO);
                    entryAllot.setEntAddPointAmount(BigDecimal.ZERO);

                    addOrUpdate.put("insertEntry", entryAllot);

                    accountList = AccountHandle.accountBackEntry(allocList);

                    if (oldOrder.getIsSettle() == Constants.IS_SETTLE0)
                    {
                        // 积分退回
                        PointAllot pointAllot = pointAllocService.getPointAllot(oldOrder.getTransNo());
                        if (!EsTools.isEmpty(pointAllot))
                        {
                            List<PointAllot> list = new ArrayList<>();
                            list.add(PointBackHandle.setPointAllot(pointAllot));
                            addOrUpdate.put("insertPointAllotDaily", list);

                        }

                    }
                }
                // 组装账务冲正对象参数
                AccountWriteBack accountWriteBack = pointHandle.writeBackPoint(backDetail);

                pointHandle.backResultDispose(backResult, backDetail);

                backPointMap.put("backPointAddOrUpdate", addOrUpdate);

                backPointMap.put("backPointAccountList", accountList);

                backPointMap.put("backPointAccountWriteBack", accountWriteBack);

                backPointMap.put("backPointBackResult", backResult);

            }
        }
        catch (HsException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            EsException.esThrowException(new Throwable().getStackTrace()[0], RespCode.PS_DB_SQL_ERROR.getCode(), e
                    .getMessage(), e);
        }
        return backPointMap;
    }

    /**
     * 撤单
     * 
     * @param cancel
     *            撤单参数
     * @return CancelResult
     */
    @Override
    public CancelResult cancelPoint(Cancel cancel) throws HsException {
        CancelResult cancelResult = new CancelResult();

        Map<String, Object> addOrUpdate = new HashMap<>();

        try
        {
            ValidateModelUtil.validateModel(cancel); // 校验参数

            // 查询是否已退款条件封装
            List<QuetyPaying> quetyPayingList = new ArrayList<QuetyPaying>();
            QuetyPaying quetyPaying = new QuetyPaying();
            // 订单号
            quetyPaying.setOrderNo(cancel.getOrderNo());
            // 原始交易号
            quetyPaying.setSourceTransNo(cancel.getSourceTransNo());
            // 交易类型
            quetyPaying.setTransType(cancel.getTransType());
            quetyPayingList.add(quetyPaying);
            quetyPayingList = queryPaying(quetyPayingList);
            if (!quetyPayingList.isEmpty())
            {
                quetyPaying = quetyPayingList.get(0);
                cancelResult.setPerPoint(quetyPaying.getPerPoint());
                cancelResult.setTransNo(quetyPaying.getTransNo());
                cancelResult.setAccountantDate(DateUtil.DateToString(quetyPaying.getSourceTransDate(),
                        DateUtil.DEFAULT_DATE_TIME_FORMAT));

                return cancelResult;
            }

            EsAssert.repeatSubmit(cancel.toString());

            // 根据原积分单查找原分录,存储到分录list
            Pair<List<Alloc>, EntryAllot> oldAllocList = pointAllocService
                    .canceQueryOnLineEntry(cancel.getOldTransNo());
            // 校验原单是否为空
            EsAssert.notNull(oldAllocList, RespCode.PS_ORDER_NOT_FOUND, "原订单不存在,无法撤单");

            List<Alloc> allocList = oldAllocList.getLeft();

            EntryAllot oldEntryAllot = oldAllocList.getRight();

            // 校验原单是否为空
            EsAssert.notNull(allocList, RespCode.PS_ORDER_NOT_FOUND, "分路不存在,无法撤单");
            // 校验原单是否为空
            EsAssert.notNull(oldEntryAllot, RespCode.PS_ORDER_NOT_FOUND, "分路不存在,无法撤单");
            if (oldEntryAllot.getIsOldSettle() == Constants.IS_SETTLE0)
            {
                // 判断原积分单非正常状态, 则抛出互生异常
                EsException.esHsThrowException(new Throwable().getStackTrace()[0], RespCode.PS_HAS_ISSETTLE_CANCELLATION
                        .getCode(), "您的订单已结算,无法撤单");
            }
            if (oldEntryAllot.getTransStatus() != 0 && oldEntryAllot.getTransStatus() != 3
                    && oldEntryAllot.getTransStatus() != 4)
            {
                EsException.esHsThrowException(new Throwable().getStackTrace()[0], RespCode.PS_HAS_THE_CANCELLATION
                        .getCode(), "原订单已退货或已撤单,无法撤单");
            }
            else
            {
                CancellationDetail cancellationDetail = new CancellationDetail();

                BeanCopierUtils.copy(oldEntryAllot, cancellationDetail);

                BeanCopierUtils.copy(cancel, cancellationDetail);

                PointHandle pointHandle = new PointHandle();

                pointHandle.cancelDispose(cancellationDetail, oldEntryAllot, cancel);

                pointHandle.cancelEntry(allocList, cancellationDetail, oldEntryAllot); // 原分录数据处理

                // pointMapper.cancelPoint(cancellationDetail); // 撤单入库

                addOrUpdate.put("cancelPoint", cancellationDetail);

                Map<String, Object> updateStatusMap = new HashMap<String, Object>();

                updateStatusMap.put("POINT_BUSS_TYPE", Integer.parseInt(Constants.POINT_BUSS_TYPE1));

                updateStatusMap.put("transNo", oldEntryAllot.getRelTransNo());

                updateStatusMap.put("tableName", Constants.T_PS_NDETAIL);

                addOrUpdate.put("updateStatus", updateStatusMap);

                // pointMapper.updateStatus(Integer.parseInt(Constants.POINT_BUSS_TYPE1),
                // oldEntryAllot.getRelTransNo(),
                // Constants.T_PS_NDETAIL); // 修改原单为已撤单

                // 改造后的分录数据结构
                EntryAllot entryAllot = EntryHandle.mergeEntryData(allocList, Constants.HSB_POINT_CANCEL);
                entryAllot.setPerCustId(oldEntryAllot.getPerCustId());
                entryAllot.setEntCustId(oldEntryAllot.getEntCustId());

                // pointAllocService.getEntryDetail(entryAllot);
                EntryAllot bEntryAllot = EntryHandle.cashBCBusinessServerFee(cancel.getTransType(), oldAllocList
                        .getRight().getTransAmount(), entryAllot.getEntAddPointAmount(), oldAllocList.getRight()
                        .getIsSettle(), oldEntryAllot.getIsDeduction());
                if (bEntryAllot != null)
                {
                    entryAllot.setEntAddServiceFee(bEntryAllot.getEntAddServiceFee());
                    entryAllot.setSettleAmount(bEntryAllot.getSettleAmount());
                }

                // pointAllocService.getEntryDetail(entryAllot);
                // pointAllocMapper.insertEntry(entryAllot);

                addOrUpdate.put("insertEntry", entryAllot);

                pointHandle.relEntryNoHandleBack(allocList, oldEntryAllot);

                List<AccountEntry> accountList = AccountHandle.accountCancelEntry(allocList);

                try
                {
                    if (accountList != null && accountList.size() != 0)
                    {
                        // 调用账务系统记账接口实现记账
                        accountEntryService.chargebackAccount(accountList);
                    }
                    addOrUpdateTransactional(addOrUpdate);
                }
                catch (HsException he)
                {
                    // 抛出互生异常
                    EsException
                            .esThrowException(new Throwable().getStackTrace()[0], he.getErrorCode(), he.getMessage(), he);

                }
                catch (Exception e)
                {
                    // 组装账务冲正对象参数
                    AccountWriteBack accountWriteBack = pointHandle.writeBackPoint(cancellationDetail);

                    // 调用账务系统冲正
                    accountEntryService.correctSingleAccount(accountWriteBack);

                    // 抛出 异常
                    EsException.esThrowException(new Throwable().getStackTrace()[0], EsRespCode.PS_DB_SQL_ERROR
                            .getCode(), e.getMessage(), e);
                }

                pointHandle.cancelResultDispose(cancelResult, cancellationDetail); // 返回参数处理
            }
            EsAssert.deleteKeyt(cancel.toString());
        }
        catch (HsException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            EsException.esThrowException(new Throwable().getStackTrace()[0], RespCode.PS_DB_SQL_ERROR.getCode(), e
                    .getMessage(), e);
        }
        return cancelResult;
    }

    /**
     * 撤单
     * 
     * @param cancel
     *            撤单参数
     * @return CancelResult
     */
    public Map<String, Object> cancelPointMap(Cancel cancel) throws HsException {
        CancelResult cancelResult = new CancelResult();

        Map<String, Object> cancelPointMap = new HashMap<>();

        Map<String, Object> addOrUpdate = new HashMap<>();

        try
        {
            ValidateModelUtil.validateModel(cancel); // 校验参数

            // 根据原积分单查找原分录,存储到分录list
            Pair<List<Alloc>, EntryAllot> oldAllocList = pointAllocService
                    .canceQueryOnLineEntry(cancel.getOldTransNo());
            // 校验原单是否为空
            EsAssert.notNull(oldAllocList, RespCode.PS_ORDER_NOT_FOUND, "原订单不存在,无法撤单");

            List<Alloc> allocList = oldAllocList.getLeft();

            EntryAllot oldEntryAllot = oldAllocList.getRight();

            // 校验原单是否为空
            EsAssert.notNull(allocList, RespCode.PS_ORDER_NOT_FOUND, "分路不存在,无法撤单");
            // 校验原单是否为空
            EsAssert.notNull(oldEntryAllot, RespCode.PS_ORDER_NOT_FOUND, "分路不存在,无法撤单");
            if (oldEntryAllot.getTransStatus() != 0 && oldEntryAllot.getTransStatus() != 3
                    && oldEntryAllot.getTransStatus() != 4)
            {
                EsException.esHsThrowException(new Throwable().getStackTrace()[0], RespCode.PS_HAS_THE_CANCELLATION
                        .getCode(), "原订单已退货或已撤单,无法撤单");
            }
            else
            {
                CancellationDetail cancellationDetail = new CancellationDetail();

                BeanCopierUtils.copy(oldEntryAllot, cancellationDetail);

                BeanCopierUtils.copy(cancel, cancellationDetail);

                PointHandle pointHandle = new PointHandle();

                pointHandle.cancelDispose(cancellationDetail, oldEntryAllot, cancel);

                pointHandle.cancelEntry(allocList, cancellationDetail, oldEntryAllot); // 原分录数据处理

                addOrUpdate.put("cancelPoint", cancellationDetail);

                Map<String, Object> updateStatusMap = new HashMap<String, Object>();

                updateStatusMap.put("POINT_BUSS_TYPE", Integer.parseInt(Constants.POINT_BUSS_TYPE1));

                updateStatusMap.put("transNo", oldEntryAllot.getRelTransNo());

                updateStatusMap.put("tableName", Constants.T_PS_NDETAIL);

                addOrUpdate.put("updateStatus", updateStatusMap);

                // 改造后的分录数据结构
                EntryAllot entryAllot = EntryHandle.mergeEntryData(allocList, Constants.HSB_POINT_CANCEL);
                entryAllot.setPerCustId(oldEntryAllot.getPerCustId());
                entryAllot.setEntCustId(oldEntryAllot.getEntCustId());

                EntryAllot bEntryAllot = EntryHandle.cashBCBusinessServerFee(cancel.getTransType(), oldAllocList
                        .getRight().getTransAmount(), entryAllot.getEntAddPointAmount(), oldAllocList.getRight()
                        .getIsSettle(), oldEntryAllot.getIsDeduction());
                if (bEntryAllot != null)
                {
                    entryAllot.setEntAddServiceFee(bEntryAllot.getEntAddServiceFee());
                    entryAllot.setSettleAmount(bEntryAllot.getSettleAmount());
                }

                // 组装账务冲正对象参数
                AccountWriteBack accountWriteBack = pointHandle.writeBackPoint(cancellationDetail);

                addOrUpdate.put("insertEntry", entryAllot);

                pointHandle.relEntryNoHandleBack(allocList, oldEntryAllot);

                List<AccountEntry> accountList = AccountHandle.accountCancelEntry(allocList);

                pointHandle.cancelResultDispose(cancelResult, cancellationDetail); // 返回参数处理

                cancelPointMap.put("cancelPointAddOrUpdate", addOrUpdate);

                cancelPointMap.put("cancelPointAccountList", accountList);

                cancelPointMap.put("cancelPointAccountWriteBack", accountWriteBack);

                cancelPointMap.put("cancelPointCancelResult", cancelResult);

            }
        }
        catch (HsException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            EsException.esThrowException(new Throwable().getStackTrace()[0], RespCode.PS_DB_SQL_ERROR.getCode(), e
                    .getMessage(), e);
        }
        return cancelPointMap;
    }

    /**
     * 冲正
     * 
     * @param correct
     *            冲正参数
     */
    @Override
    @Transactional
    public void returnPoint(Correct correct) throws HsException {
        ValidateModelUtil.validateModel(correct); // 校验参数

        try
        {

            CorrectDetail correctDetail = new CorrectDetail();
            PointDetail oldOrder = new PointDetail();
            String tableName = "";
            String[] arr = { Constants.T_PS_BDETAIL, Constants.T_PS_CDETAIL, Constants.T_PS_NDETAIL };
            for (String anArr : arr)
            {
                tableName = anArr;
                oldOrder = pointMapper.queryOldOrderAll(correct, tableName); // 扫描查找积分、撤单、退货
                if (oldOrder != null)
                {
                    break;
                }
            }
            if (EsTools.isEmpty(oldOrder))
            {
                EsException.esHsThrowException(new Throwable().getStackTrace()[0], EsRespCode.PS_ORDER_NOT_FOUND
                        .getCode(), "已冲正,原订单不存在");
            }
            else
            {
                BeanCopierUtils.copy(oldOrder, correctDetail);
                BeanCopierUtils.copy(correct, correctDetail);
                PointHandle pointHandle = new PointHandle();
                pointHandle.correctDetailDispose(correctDetail, correct);
                List<Alloc> correctList = pointAllocService.allocReturnPoint(correctDetail, oldOrder); // 查找原分录
                pointHandle.correctEntry(correctList, correctDetail, oldOrder);
                pointMapper.returnPoint(correctDetail); // 冲正入库
                // pointAllocService.getAllocDetail(correctList); // 分录入库
                pointAllocMapper.insertAlloc(correctList);
                if (tableName.equals(Constants.T_PS_NDETAIL)) // 积分冲正
                {
                    pointMapper.updateStatus(Integer.parseInt(Constants.ALREADY_CORRECT11), oldOrder.getTransNo(),
                            Constants.T_PS_NDETAIL);
                }
                else
                // 退货或撤单冲正
                {
                    pointMapper.updateStatus(Integer.parseInt(Constants.ALREADY_CORRECT11), oldOrder.getTransNo(),
                            tableName); // 退货或撤单为已冲正
                    pointMapper.updatePointStatus(Integer.parseInt(Constants.POINT_BUSS_TYPE0), oldOrder.getTransNo(),
                            tableName); // 积分单为正常
                }
                AccountWriteBack accountWriteBack = AccountHandle.correctAccount(correctList); // 分录数据处理
                this.correctAccountTally(accountWriteBack); // 调用账务记账
            }
        }
        catch (HsException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            EsException.esThrowException(new Throwable().getStackTrace()[0], RespCode.PS_DB_SQL_ERROR.getCode(), e
                    .getMessage(), e);
        }
    }

    /**
     * 分录记账(积分)
     * 
     * @param list
     *            同步给账务对象
     */
    private void accountEntryTally(List<AccountEntry> list) throws HsException {
        try
        {
            if (list != null && list.size() != 0)
            {
                accountEntryService.deductAccount(list);
            }
        }
        catch (HsException he)
        {
            EsException.esThrowException(new Throwable().getStackTrace()[0], he.getErrorCode(), he.getMessage(), he);
        }
    }

    /**
     * 撤退分录记账(撤单、退货)
     * 
     * @param list
     *            给账务对象
     */
    private void retreatAccountTally(List<AccountEntry> list) throws HsException {
        try
        {
            if (list != null && list.size() != 0)
            {
                accountEntryService.chargebackAccount(list);
            }
        }
        catch (HsException he)
        {
            EsException.esThrowException(new Throwable().getStackTrace()[0], he.getErrorCode(), he.getMessage(), he);
        }
    }

    /**
     * 冲正分录记账
     * 
     * @param accountWriteBack
     *            冲正参数
     */
    private void correctAccountTally(AccountWriteBack accountWriteBack) throws HsException {
        try
        {
            accountEntryService.correctSingleAccount(accountWriteBack);
        }
        catch (HsException he)
        {
            EsException.esThrowException(new Throwable().getStackTrace()[0], he.getErrorCode(), he.getMessage(), he);
        }
    }

    /***
     * 退货退积分
     */
    @Override
    public BackResult returnBackPoint(Back back) throws HsException {
        ValidateModelUtil.validateModel(back); // 校验参数
        BackResult backResult = new BackResult();

        Map<String, Object> addOrUpdate = new HashMap<>();

        try
        {

            // 查询是否已退款条件封装
            List<QuetyPaying> quetyPayingList = new ArrayList<QuetyPaying>();
            QuetyPaying quetyPaying = new QuetyPaying();
            // 订单号
            quetyPaying.setOrderNo(back.getOrderNo());
            // 原始交易号
            quetyPaying.setSourceTransNo(back.getSourceTransNo());
            // 交易类型
            quetyPaying.setTransType(back.getTransType());
            quetyPayingList.add(quetyPaying);
            quetyPayingList = queryPaying(quetyPayingList);
            if (!quetyPayingList.isEmpty())
            {
                quetyPaying = quetyPayingList.get(0);
                backResult.setPerPoint(quetyPaying.getPerPoint());
                backResult.setTransNo(quetyPaying.getTransNo());
                backResult.setAccountantDate(DateUtil.DateToString(quetyPaying.getSourceTransDate(),
                        DateUtil.DEFAULT_DATE_TIME_FORMAT));

                return backResult;
            }
            EsAssert.repeatSubmit(back.toString());

            PointDetail oldOrder = pointMapper.queryOldOrder(back.getOldTransNo());
            if (EsTools.isEmpty(oldOrder))
            {
                EsException.esHsThrowException(new Throwable().getStackTrace()[0], EsRespCode.PS_ORDER_NOT_FOUND
                        .getCode(), "原订单不存在,无法退货");
            }
            else if (oldOrder.getTransStatus() != 0 && oldOrder.getTransStatus() != 4)
            {
                EsException.esHsThrowException(new Throwable().getStackTrace()[0], EsRespCode.PS_HAS_THE_CANCELLATION
                        .getCode(), "原订单不是正常状态、预留结单状态,无法退货退积分");
            }
            else
            {
                List<AccountEntry> accountList = new ArrayList<AccountEntry>();
                BackDetail backDetail = new BackDetail();
                BeanCopierUtils.copy(oldOrder, backDetail);
                BeanCopierUtils.copy(back, backDetail);
                // 退货预扣积分交易时间
                backDetail.setSourceTransDate(Timestamp.valueOf(back.getSourceTransDate()));
                PointHandle pointHandle = new PointHandle();
                pointHandle.backDispose(backDetail, oldOrder);
                BigDecimal pointTransAmount = oldOrder.getTransAmount();
                BigDecimal backTransAmount = new BigDecimal(back.getTransAmount());
                BigDecimal transAmount = Compute.roundFloor(pointTransAmount, Constants.SURPLUS_TWO);
                BigDecimal backAmount = Compute.roundFloor(backTransAmount, Constants.SURPLUS_TWO);
                // 查找分录
                EntryAllot oldEntryAllot = pointAllocService.queryOnlineEntryNo(oldOrder);
                if (transAmount.compareTo(backAmount) == 1
                        || oldOrder.getTransStatus() == Integer.parseInt(Constants.POINT_BUSS_TYPE6))// 部分退货
                {
                    BigDecimal subAmount = Compute.sub(transAmount, backAmount);
                    BigDecimal amount = Compute.roundFloor(subAmount, Constants.SURPLUS_SIX);

                    // 退货明细实体设置原订单金额
                    backDetail.setOldTransAmount(amount);
                    backDetail.setTransAmount(new BigDecimal(back.getTransAmount()));

                    backDetail.setPerPoint(new BigDecimal(back.getBackPoint()));
                    backDetail.setEntPoint(Compute.roundFloor(new BigDecimal(back.getBackPoint()).multiply(BigDecimal
                            .valueOf(2)), Constants.SURPLUS_TWO));

                    List<Alloc> allocList = pointAllocService.allocBackPoint(backDetail, oldOrder); // 计算部分退货
                    pointHandle.relEntryNoHandle(allocList, oldEntryAllot);
                    pointHandle.backEntry(allocList, backDetail, oldOrder);
                    // pointMapper.backPoint(backDetail);

                    addOrUpdate.put("backPoint", backDetail);

                    // pointAllocService.getAllocDetail(allocList);
                    oldOrder.setStatus(Integer.parseInt(Constants.POINT_BUSS_TYPE5));
                    // pointMapper.updatePoint(oldOrder);

                    addOrUpdate.put("updatePoint", oldOrder);

                    // 改造后的分录数据结构
                    EntryAllot entryAllot = EntryHandle.mergeEntryData(allocList, Constants.HSB_POINT_BACK);
                    // 部分退货修改第四位
                    StringBuilder sb = new StringBuilder(entryAllot.getTransType());

                    entryAllot.setTransType(String.valueOf(sb.replace(3, 4, Constants.POINT_BUSS_TYPE6)));
                    entryAllot.setEntAddPointAmount(backDetail.getEntPoint());
                    entryAllot.setPerSubPointAmount(backDetail.getPerPoint());
                    // 设置原分路流水号
                    entryAllot.setRelEntryNo(oldEntryAllot.getEntryNo());
                    entryAllot.setPerCustId(oldEntryAllot.getPerCustId());
                    entryAllot.setEntCustId(oldEntryAllot.getEntCustId());
                    // pointAllocService.getEntryDetail(entryAllot);
                    // pointAllocMapper.insertEntry(entryAllot);

                    EntryAllot bEntryAllot = EntryHandle.cashBCBusinessServerFee(back.getTransType(), new BigDecimal(
                            back.getTransAmount()), entryAllot.getEntAddPointAmount(), oldOrder.getIsSettle(), oldOrder
                            .getIsDeduction());
                    if (bEntryAllot != null)
                    {
                        entryAllot.setEntAddServiceFee(bEntryAllot.getEntAddServiceFee());
                        entryAllot.setSettleAmount(bEntryAllot.getSettleAmount());
                    }
                    
                    entryAllot.setEntAddServiceFee(BigDecimal.ZERO);
                    entryAllot.setEntSubServiceFee(BigDecimal.ZERO);
                    addOrUpdate.put("insertEntry", entryAllot);

                    // 退货只退积分
                    pointHandle.backPointHandle(allocList);
                    accountList = AccountHandle.accountBackEntry(allocList);
                    // this.retreatAccountTally(accountList);
                }
                else
                // 全部退货
                {
                    // 调用积分分配服务,计算部分退货, 存储在分录list
                    List<Alloc> allocList = pointAllocService.allocBackPoint(backDetail, oldOrder);

                    pointHandle.relEntryNoHandle(allocList, oldEntryAllot);
                    pointHandle.backEntry(allocList, backDetail, oldOrder);

                    // 写退货表
                    // pointMapper.backPoint(backDetail);

                    addOrUpdate.put("backPoint", backDetail);

                    Map<String, Object> updateStatusMap = new HashMap<String, Object>();

                    updateStatusMap.put("POINT_BUSS_TYPE", Integer.parseInt(Constants.POINT_BUSS_TYPE5));

                    updateStatusMap.put("transNo", oldOrder.getTransNo());

                    updateStatusMap.put("tableName", Constants.T_PS_NDETAIL);

                    addOrUpdate.put("updateStatus", updateStatusMap);

                    // pointMapper.updateStatus(Integer.parseInt(Constants.POINT_BUSS_TYPE5),
                    // oldOrder.getTransNo(),
                    // Constants.T_PS_NDETAIL); // 修改原单为已退货

                    // 改造后的分录数据结构
                    EntryAllot entryAllot = EntryHandle.mergeEntryData(allocList, Constants.HSB_POINT_BACK);
                    // 设置原分路流水号
                    entryAllot.setRelEntryNo(oldEntryAllot.getEntryNo());
                    entryAllot.setPerCustId(oldEntryAllot.getPerCustId());
                    entryAllot.setEntCustId(oldEntryAllot.getEntCustId());

                    EntryAllot bEntryAllot = EntryHandle.cashBCBusinessServerFee(back.getTransType(), new BigDecimal(
                            back.getTransAmount()), entryAllot.getEntAddPointAmount(), oldOrder.getIsSettle(), oldOrder
                            .getIsDeduction());
                    if (bEntryAllot != null)
                    {
                        entryAllot.setEntAddServiceFee(bEntryAllot.getEntAddServiceFee());
                        entryAllot.setSettleAmount(bEntryAllot.getSettleAmount());
                    }
                    // pointAlloc
                    // pointAllocService.getEntryDetail(entryAllot);
                    // pointAllocMapper.insertEntry(entryAllot);
                    entryAllot.setEntAddServiceFee(BigDecimal.ZERO);
                    entryAllot.setEntSubServiceFee(BigDecimal.ZERO);
                    addOrUpdate.put("insertEntry", entryAllot);

                    // 退货只退积分
                    pointHandle.backPointHandle(allocList);

                    if (allocList != null && allocList.size() != 0)
                    {
                        accountList = AccountHandle.accountBackEntry(allocList);
                        EsTools.jsonString(accountList);
                        // this.retreatAccountTally(accountList);
                    }
                }

                try
                {
                    if (accountList != null && accountList.size() != 0)
                    {
                        // 调用账务系统记账接口实现记账
                        accountEntryService.chargebackAccount(accountList);
                    }
                    addOrUpdateTransactional(addOrUpdate);
                }
                catch (HsException he)
                {
                    // 抛出互生异常
                    EsException
                            .esThrowException(new Throwable().getStackTrace()[0], he.getErrorCode(), he.getMessage(), he);

                }
                catch (Exception e)
                {
                    // 组装账务冲正对象参数
                    AccountWriteBack accountWriteBack = pointHandle.writeBackPoint(backDetail);

                    // 调用账务系统冲正
                    accountEntryService.correctSingleAccount(accountWriteBack);

                    // 抛出 异常
                    EsException.esThrowException(new Throwable().getStackTrace()[0], EsRespCode.PS_DB_SQL_ERROR
                            .getCode(), e.getMessage(), e);
                }

                pointHandle.backResultDispose(backResult, backDetail);
            }
            EsAssert.deleteKeyt(back.toString());
        }
        catch (HsException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            EsException.esThrowException(new Throwable().getStackTrace()[0], RespCode.PS_DB_SQL_ERROR.getCode(), e
                    .getMessage(), e);
        }
        return backResult;
    }

    /***
     * 退货退款
     */
    @Override
    public BackResult returnBackAmount(Back back) throws HsException {
        ValidateModelUtil.validateModel(back); // 校验参数

        BackResult backResult = new BackResult();

        Map<String, Object> addOrUpdate = new HashMap<>();

        try
        {
//            // 查询是否已退款条件封装
//            List<QuetyPaying> quetyPayingList = new ArrayList<QuetyPaying>();
//            QuetyPaying quetyPaying = new QuetyPaying();
//            // 订单号
//            quetyPaying.setOrderNo(back.getOrderNo());
//            // 原始交易号
//            quetyPaying.setSourceTransNo(back.getSourceTransNo());
//            // 交易类型
//            quetyPaying.setTransType(back.getTransType());
//            quetyPayingList.add(quetyPaying);
//            quetyPayingList = queryPaying(quetyPayingList);
//            if (!quetyPayingList.isEmpty())
//            {
//                quetyPaying = quetyPayingList.get(0);
//                backResult.setPerPoint(quetyPaying.getPerPoint());
//                backResult.setTransNo(quetyPaying.getTransNo());
//                backResult.setAccountantDate(DateUtil.DateToString(quetyPaying.getSourceTransDate(),
//                        DateUtil.DEFAULT_DATE_TIME_FORMAT));
//
//                return backResult;
//            }
            EsAssert.repeatSubmit(back.toString());

            PointDetail oldOrder = pointMapper.queryOldOrder(back.getOldTransNo());
            if (EsTools.isEmpty(oldOrder))
            {
                EsException.esHsThrowException(new Throwable().getStackTrace()[0], EsRespCode.PS_ORDER_NOT_FOUND
                        .getCode(), "原订单不存在,无法退货");
            }
            else if (oldOrder.getTransStatus() != 0 && oldOrder.getTransStatus() != 4 && oldOrder.getTransStatus() != 5)
            {
                EsException.esHsThrowException(new Throwable().getStackTrace()[0], EsRespCode.PS_HAS_THE_CANCELLATION
                        .getCode(), "原订单不是正常状态或预留结单状态或退货中,无法退货退款");
            }
            else
            {
                List<AccountEntry> accountList = new ArrayList<AccountEntry>();
                BackDetail backDetail = new BackDetail();
                BeanCopierUtils.copy(oldOrder, backDetail);
                BeanCopierUtils.copy(back, backDetail);
                // 退货退款交易时间
                backDetail.setSourceTransDate(Timestamp.valueOf(back.getSourceTransDate()));
                PointHandle pointHandle = new PointHandle();
                pointHandle.backDispose(backDetail, oldOrder);
                BigDecimal pointTransAmount = oldOrder.getTransAmount();
                BigDecimal backTransAmount = new BigDecimal(back.getTransAmount());
                // 退货明细实体设置原订单金额
                backDetail.setPerPoint(new BigDecimal(back.getBackPoint()));
                BigDecimal transAmount = Compute.roundFloor(pointTransAmount, Constants.SURPLUS_TWO);
                BigDecimal backAmount = Compute.roundFloor(backTransAmount, Constants.SURPLUS_TWO);
                // 查询分录表
                EntryAllot oldEntryAllot = pointAllocService.queryOnlineOldTransNo(oldOrder);
                if (transAmount.compareTo(backAmount) == 1
                        || oldOrder.getTransStatus() == Integer.parseInt(Constants.POINT_BUSS_TYPE6))// 部分退货
                {
                    BigDecimal subAmount = Compute.sub(transAmount, backAmount);
                    BigDecimal amount = Compute.roundFloor(subAmount, Constants.SURPLUS_SIX);

                    // 退货明细实体设置原订单金额
                    backDetail.setOldTransAmount(amount);
                    backDetail.setTransAmount(new BigDecimal(back.getTransAmount()));

                    backDetail.setPerPoint(new BigDecimal(back.getBackPoint()));
                    backDetail.setEntPoint(Compute.roundFloor(new BigDecimal(back.getBackPoint()).multiply(BigDecimal
                            .valueOf(2)), Constants.SURPLUS_TWO));

                    List<Alloc> oldAllocList = pointAllocService.allocBackPoint(backDetail, oldOrder); // 计算部分退货

                    for (Alloc alloc : oldAllocList)
                    {
                        alloc.setIsSettle(oldOrder.getIsSettle());
                        alloc.setIsDeduction(oldOrder.getIsDeduction());
                    }

                    List<Alloc> allocList = new ArrayList<>();

                    allocList.addAll(oldAllocList);

                    pointHandle.relEntryNoHandle(oldAllocList, oldEntryAllot);

                    pointHandle.backEntry(oldAllocList, backDetail, oldOrder);

                    // pointMapper.backPoint(backDetail);
                    oldOrder.setStatus(Integer.parseInt(Constants.POINT_BUSS_TYPE6));
                    oldOrder.setTransAmount(amount);
                    String batchNo = oldOrder.getBatchNo();
                    if(oldOrder.getIsSettle() == Constants.IS_SETTLE1)
                    {
                        oldOrder.setBatchNo(DateUtil.DateToString(DateUtil.today(), DateUtil.DEFAULT_DATE_FORMAT));
                    }
                    // pointMapper.updatePoint(oldOrder);
                    addOrUpdate.put("updatePoint", oldOrder);

                    // 改造后的分录数据结构
                    EntryAllot newEntryAllot = EntryHandle.mergeEntryData(allocList, Constants.HSB_POINT_BACK);
                    // 设置原分路流水号
                    newEntryAllot.setEntryNo(oldEntryAllot.getEntryNo());
                    newEntryAllot.setBatchNo(DateUtil.DateToString(DateUtil.today(), DateUtil.DEFAULT_DATE_FORMAT));
                    EntryAllot bEntryAllot = EntryHandle.backBusinessServerFee(back.getTransType(), new BigDecimal(
                            back.getTransAmount()), newEntryAllot.getEntAddPointAmount(), oldOrder.getIsSettle(),
                            oldOrder.getIsDeduction());
                    if (bEntryAllot != null)
                    {
                        newEntryAllot.setEntAddServiceFee(bEntryAllot.getEntAddServiceFee());
                        newEntryAllot.setSettleAmount(bEntryAllot.getSettleAmount());
                    }
                    // pointAllocService.updateEntryByEntryNo(newEntryAllot);
                    // pointAllocMapper.updateEntryByEntryNo(newEntryAllot);

                    addOrUpdate.put("updateEntryByEntryNo", newEntryAllot);

                    pointHandle.relEntryNoHandleBack(allocList, oldEntryAllot);
                    // 退货只退款
                    pointHandle.backHsbHandle(oldAllocList);
                    if (!CollectionUtils.isEmpty(oldAllocList))
                    {
                        accountList = AccountHandle.accountBackEntry(oldAllocList);
                        // this.retreatAccountTally(accountList);
                    }

                    if (oldOrder.getIsSettle() == Constants.IS_SETTLE0)
                    {
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
                        addOrUpdate.put("insertPointAllotDaily", bat.getPointList());
                        oldOrder.setBatchNo(batchNo);
                    }
                }
                else
                // 全部退货
                {
                    // 调用积分分配服务,计算部分退货, 存储在分录list
                    List<Alloc> allocList = pointAllocService.allocBackPoint(backDetail, oldOrder);

                    pointHandle.backEntry(allocList, backDetail, oldOrder);

                    // 是否要记两条，待确认
                    // pointMapper.backPoint(backDetail);

                    Map<String, Object> updateStatusMap = new HashMap<String, Object>();
                    
                    if(oldOrder.getIsSettle() == Constants.IS_SETTLE1)
                    {
                        updateStatusMap.put("batchNo", DateUtil.DateToString(DateUtil.today(), DateUtil.DEFAULT_DATE_FORMAT));
                    }

                    updateStatusMap.put("POINT_BUSS_TYPE", Integer.parseInt(Constants.POINT_BUSS_TYPE2));

                    updateStatusMap.put("transNo", oldOrder.getTransNo());

                    updateStatusMap.put("tableName", Constants.T_PS_NDETAIL);

                    addOrUpdate.put("updateStatus", updateStatusMap);

                    // pointMapper.updateStatus(Integer.parseInt(Constants.POINT_BUSS_TYPE2),
                    // oldOrder.getTransNo(),
                    // Constants.T_PS_NDETAIL); // 修改原单为已退货

                    // 改造后的分录数据结构
                    EntryAllot newEntryAllot = EntryHandle.mergeEntryData(allocList, Constants.HSB_POINT_BACK);
                    // 设置原分路流水号
                    newEntryAllot.setRelEntryNo(oldEntryAllot.getEntryNo());
                    newEntryAllot.setEntryNo(oldEntryAllot.getEntryNo());
                    EntryAllot bEntryAllot = EntryHandle.cashBCBusinessServerFee(back.getTransType(), new BigDecimal(
                            back.getTransAmount()), newEntryAllot.getEntAddPointAmount(), oldOrder.getIsSettle(),
                            oldOrder.getIsDeduction());
                    if (bEntryAllot != null)
                    {
                        newEntryAllot.setEntAddServiceFee(bEntryAllot.getEntAddServiceFee());
                        newEntryAllot.setSettleAmount(bEntryAllot.getSettleAmount());
                    }
                    // pointAllocService.updateEntryByEntryNo(newEntryAllot);
                    // pointAllocMapper.updateEntryByEntryNo(newEntryAllot);

                    addOrUpdate.put("updateEntryByEntryNo", newEntryAllot);

                    pointHandle.relEntryNoHandleBack(allocList, oldEntryAllot);
                    // 退货只退款
                    pointHandle.backHsbHandle(allocList);
                    if (allocList != null && allocList.size() != 0)
                    {
                        accountList = AccountHandle.accountBackEntry(allocList);
                        // this.retreatAccountTally(accountList);
                    }

                    if (oldOrder.getIsSettle() == Constants.IS_SETTLE0)
                    {
                        // 积分退回
                        PointAllot pointAllot = pointAllocService.getPointAllot(oldOrder.getTransNo());
                        if (!EsTools.isEmpty(pointAllot))
                        {
                            List<PointAllot> list = new ArrayList<>();
                            list.add(PointBackHandle.setPointAllot(pointAllot));
                            // pointAllocService.addPointAllotData(list);
                            // pointAllocMapper.insertPointAllotDaily(list);
                            addOrUpdate.put("insertPointAllotDaily", list);

                        }

                    }

                }

                try
                {
                    if (accountList != null && accountList.size() != 0)
                    {
                        // 调用账务系统记账接口实现记账
                        accountEntryService.chargebackAccount(accountList);
                    }
                    addOrUpdateTransactional(addOrUpdate);
                }
                catch (HsException he)
                {
                    // 抛出互生异常
                    EsException
                            .esThrowException(new Throwable().getStackTrace()[0], he.getErrorCode(), he.getMessage(), he);

                }
                catch (Exception e)
                {
                    // 组装账务冲正对象参数
                    AccountWriteBack accountWriteBack = pointHandle.writeBackPoint(backDetail);

                    // 调用账务系统冲正
                    accountEntryService.correctSingleAccount(accountWriteBack);

                    // 抛出 异常
                    EsException.esThrowException(new Throwable().getStackTrace()[0], EsRespCode.PS_DB_SQL_ERROR
                            .getCode(), e.getMessage(), e);
                }

                pointHandle.backResultDispose(backResult, backDetail);
            }
            EsAssert.deleteKeyt(back.toString());
        }
        catch (HsException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            EsException.esThrowException(new Throwable().getStackTrace()[0], RespCode.PS_DB_SQL_ERROR.getCode(), e
                    .getMessage(), e);
        }
        return backResult;
    }

    /**
     * 查询是否已支付
     * 
     * @param list
     * @return
     * @throws HsException
     */
    public List<QuetyPaying> queryPaying(List<QuetyPaying> list) throws HsException {

        EsAssert.notEmpty(list, "对象为空！！");

        /** 订单号 */
        String orderNos = "";

        /*** 交易类型 */
        String transTypes = "";

        /*** 原始交易号 */
        String sourceTransNos = "";

        // 查询条件遍历
        for (int i = 0; i < list.size(); i++)
        {
            QuetyPaying quetyPaying = list.get(i);
            orderNos = orderNos + "'" + quetyPaying.getOrderNo() + "'";
            transTypes = transTypes + "'" + quetyPaying.getTransType() + "'";
            sourceTransNos = sourceTransNos + "'" + quetyPaying.getSourceTransNo() + "'";
            if (i != list.size() - 1)
            {
                orderNos = orderNos + ", ";
                transTypes = transTypes + ", ";
                sourceTransNos = sourceTransNos + ", ";
            }
        }
        // 查询条件封装到MAP中
        Map<String, String> queryPayingMap = new HashMap<String, String>();
        // 订单号
        queryPayingMap.put("orderNos", orderNos);
        // 交易类型
        queryPayingMap.put("transTypes", transTypes);
        // 原始交易号
        queryPayingMap.put("sourceTransNos", sourceTransNos);
        // 查询支付订单是否存在
        List<QuetyPaying> quetyPayingList = pointMapper.queryPaying(queryPayingMap);

        return quetyPayingList;
    }

    /**
     * 新增修改统一事物处理
     * 
     * @param addOrUpdateMap
     * @throws HsException
     */
    public void addOrUpdateTransactional(final Map<String, Object> addOrUpdateMap) throws Exception {
        if (addOrUpdateMap != null && addOrUpdateMap.size() > 0)
        {
            new TransactionHandler<Boolean>(transactionMgr) {
                @Override
                protected Boolean doInTransaction() throws Exception {
                    // 积分明细实体类
                    PointDetail point = (PointDetail) addOrUpdateMap.get("point");

                    // 线上分录实体、线下分录实体
                    EntryAllot insertEntry = (EntryAllot) addOrUpdateMap.get("insertEntry");

                    // 退货明细实体类
                    BackDetail backPoint = (BackDetail) addOrUpdateMap.get("backPoint");

                    // 更新积分表交易金额(电商部分退货) 实体类
                    PointDetail updatePoint = (PointDetail) addOrUpdateMap.get("updatePoint");

                    // 消费积分表状态更新
                    Map<String, Object> updateStatusMap = (Map<String, Object>) addOrUpdateMap.get("updateStatus");

                    // 批量插入积分分配的数据
                    List<PointAllot> insertPointAllotDaily = (List<PointAllot>) addOrUpdateMap
                            .get("insertPointAllotDaily");

                    // 消费者撤单积分
                    CancellationDetail cancelPoint = (CancellationDetail) addOrUpdateMap.get("cancelPoint");

                    // 线上交易分录信息结算修改
                    EntryAllot updateEntryByEntryNo = (EntryAllot) addOrUpdateMap.get("updateEntryByEntryNo");

                    // 实时批量插入分配后的积分数据
                    List<EntryAllot> insertEntrys = (List<EntryAllot>) addOrUpdateMap.get("insertEntrys");

                    // 批量消费者积分(预留、网上积分登记、预付定金)
                    List<PointDetail> points = (List<PointDetail>) addOrUpdateMap.get("points");

                    // 批量线上交易分录信息结算修改
                    List<EntryAllot> updateEntryDetails = (List<EntryAllot>) addOrUpdateMap.get("updateEntryDetails");

                    // 批量更新原订单状态包括预留单
                    List<PointDetail> batUpdateStatus = (List<PointDetail>) addOrUpdateMap.get("batUpdateStatus");

                    // 积分明细实体类
                    if (point != null)
                    {
                        pointMapper.point(point);
                    }

                    // 线上分录实体、线下分录实体
                    if (insertEntry != null)
                    {
                        pointAllocMapper.insertEntry(insertEntry);
                    }

                    // 退货明细实体类
                    if (backPoint != null)
                    {
                        pointMapper.backPoint(backPoint);
                    }

                    // 更新积分表交易金额(电商部分退货) 实体类
                    if (updatePoint != null)
                    {
                        pointMapper.updatePoint(updatePoint);
                    }

                    // 消费积分表状态更新
                    if (updateStatusMap != null && updateStatusMap.size() > 0)
                    {
                        Integer pointBussType = (Integer) updateStatusMap.get("POINT_BUSS_TYPE");
                        String transNo = (String) updateStatusMap.get("transNo");
                        String tableName = (String) updateStatusMap.get("tableName");
                        pointMapper.updateStatus(pointBussType, transNo, tableName); // 修改原单为已退货
                    }

                    // 批量插入积分分配的数据
                    if (insertPointAllotDaily != null && insertPointAllotDaily.size() > 0)
                    {
                        pointAllocMapper.insertPointAllotDaily(insertPointAllotDaily);
                    }

                    // 消费者撤单积分
                    if (cancelPoint != null)
                    {
                        pointMapper.cancelPoint(cancelPoint);
                    }

                    // 线上交易分录信息结算修改
                    if (updateEntryByEntryNo != null)
                    {
                        pointAllocMapper.updateEntryByEntryNo(updateEntryByEntryNo);
                    }

                    // 实时批量插入分配后的积分数据
                    if (insertEntrys != null && insertEntrys.size() > 0)
                    {
                        pointAllocMapper.insertEntrys(insertEntrys);
                    }

                    // 批量消费者积分(预留、网上积分登记、预付定金)
                    if (points != null && points.size() > 0)
                    {
                        pointMapper.points(points);
                    }

                    // 批量线上交易分录信息结算修改
                    if (updateEntryDetails != null && updateEntryDetails.size() > 0)
                    {
                        pointAllocMapper.updateEntryDetails(updateEntryDetails);
                    }

                    // 批量更新原订单状态包括预留单
                    if (batUpdateStatus != null && batUpdateStatus.size() > 0)
                    {
                        pointMapper.batUpdateStatus(batUpdateStatus);
                    }
                    return true;
                }

                @Override
                protected void doWhenException(Exception e) {
                }
            }.getResult();
        }
    }

    /**
     * 批量处理新增修改统一事物处理
     * 
     * @throws HsException
     */
    public void addOrUpdateListTransactional(final List<Map<String, Object>> addOrUpdateList) throws Exception {

        if (addOrUpdateList != null && addOrUpdateList.size() > 0)
        {
            new TransactionHandler<Boolean>(transactionMgr) {
                @Override
                protected Boolean doInTransaction() throws Exception {
                    for (int i = 0; i < addOrUpdateList.size(); i++)
                    {
                        Map<String, Object> addOrUpdateMap = addOrUpdateList.get(i);
                        if (addOrUpdateMap != null && addOrUpdateMap.size() > 0)
                        {

                            // 积分明细实体类
                            PointDetail point = (PointDetail) addOrUpdateMap.get("point");

                            // 线上分录实体、线下分录实体
                            EntryAllot insertEntry = (EntryAllot) addOrUpdateMap.get("insertEntry");

                            // 退货明细实体类
                            BackDetail backPoint = (BackDetail) addOrUpdateMap.get("backPoint");

                            // 更新积分表交易金额(电商部分退货) 实体类
                            PointDetail updatePoint = (PointDetail) addOrUpdateMap.get("updatePoint");

                            // 消费积分表状态更新
                            Map<String, Object> updateStatusMap = (Map<String, Object>) addOrUpdateMap
                                    .get("updateStatus");

                            // 批量插入积分分配的数据
                            List<PointAllot> insertPointAllotDaily = (List<PointAllot>) addOrUpdateMap
                                    .get("insertPointAllotDaily");

                            // 消费者撤单积分
                            CancellationDetail cancelPoint = (CancellationDetail) addOrUpdateMap.get("cancelPoint");

                            // 线上交易分录信息结算修改
                            EntryAllot updateEntryByEntryNo = (EntryAllot) addOrUpdateMap.get("updateEntryByEntryNo");

                            // 实时批量插入分配后的积分数据
                            List<EntryAllot> insertEntrys = (List<EntryAllot>) addOrUpdateMap.get("insertEntrys");

                            // 批量消费者积分(预留、网上积分登记、预付定金)
                            List<PointDetail> points = (List<PointDetail>) addOrUpdateMap.get("points");

                            // 批量线上交易分录信息结算修改
                            List<EntryAllot> updateEntryDetails = (List<EntryAllot>) addOrUpdateMap
                                    .get("updateEntryDetails");

                            // 批量更新原订单状态包括预留单
                            List<PointDetail> batUpdateStatus = (List<PointDetail>) addOrUpdateMap
                                    .get("batUpdateStatus");

                            // 积分明细实体类
                            if (point != null)
                            {
                                pointMapper.point(point);
                            }

                            // 线上分录实体、线下分录实体
                            if (insertEntry != null)
                            {
                                pointAllocMapper.insertEntry(insertEntry);
                            }

                            // 退货明细实体类
                            if (backPoint != null)
                            {
                                pointMapper.backPoint(backPoint);
                            }

                            // 更新积分表交易金额(电商部分退货) 实体类
                            if (updatePoint != null)
                            {
                                pointMapper.updatePoint(updatePoint);
                            }

                            // 消费积分表状态更新
                            if (updateStatusMap != null && updateStatusMap.size() > 0)
                            {
                                Integer pointBussType = (Integer) updateStatusMap.get("POINT_BUSS_TYPE");
                                String transNo = (String) updateStatusMap.get("transNo");
                                String tableName = (String) updateStatusMap.get("tableName");
                                pointMapper.updateStatus(pointBussType, transNo, tableName); // 修改原单为已退货
                            }

                            // 批量插入积分分配的数据
                            if (insertPointAllotDaily != null && insertPointAllotDaily.size() > 0)
                            {
                                pointAllocMapper.insertPointAllotDaily(insertPointAllotDaily);
                            }

                            // 消费者撤单积分
                            if (cancelPoint != null)
                            {
                                pointMapper.cancelPoint(cancelPoint);
                            }

                            // 线上交易分录信息结算修改
                            if (updateEntryByEntryNo != null)
                            {
                                pointAllocMapper.updateEntryByEntryNo(updateEntryByEntryNo);
                            }

                            // 实时批量插入分配后的积分数据
                            if (insertEntrys != null && insertEntrys.size() > 0)
                            {
                                pointAllocMapper.insertEntrys(insertEntrys);
                            }

                            // 批量消费者积分(预留、网上积分登记、预付定金)
                            if (points != null && points.size() > 0)
                            {
                                pointMapper.points(points);
                            }

                            // 批量线上交易分录信息结算修改
                            if (updateEntryDetails != null && updateEntryDetails.size() > 0)
                            {
                                pointAllocMapper.updateEntryDetails(updateEntryDetails);
                            }

                            // 批量更新原订单状态包括预留单
                            if (batUpdateStatus != null && batUpdateStatus.size() > 0)
                            {
                                pointMapper.batUpdateStatus(batUpdateStatus);
                            }

                        }
                    }
                    return true;
                }

                @Override
                protected void doWhenException(Exception e) {
                }
            }.getResult();
        }
    }
}
