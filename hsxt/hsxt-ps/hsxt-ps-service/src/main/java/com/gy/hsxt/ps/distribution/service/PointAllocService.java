/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.ps.distribution.service;

import com.gy.hsxt.common.constant.AccountType;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.guid.GuidAgent;
import com.gy.hsxt.common.utils.DateUtil;
import com.gy.hsxt.common.utils.HsResNoUtils;
import com.gy.hsxt.ps.common.*;
import com.gy.hsxt.ps.distribution.bean.Alloc;
import com.gy.hsxt.ps.distribution.bean.EntryAllot;
import com.gy.hsxt.ps.distribution.bean.PointAllot;
import com.gy.hsxt.ps.distribution.bean.PointPage;
import com.gy.hsxt.ps.distribution.handle.*;
import com.gy.hsxt.ps.distribution.mapper.HsbAllocMapper;
import com.gy.hsxt.ps.distribution.mapper.PointAllocMapper;
import com.gy.hsxt.ps.distribution.mapper.RetreatAllocMapper;
import com.gy.hsxt.ps.points.bean.BackDetail;
import com.gy.hsxt.ps.points.bean.CorrectDetail;
import com.gy.hsxt.ps.points.bean.PointDetail;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.Future;

//import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * @version V3.0
 * @Package: com.gy.hsxt.ps.distribution.service
 * @ClassName: PointAllocService
 * @Description: 积分分配服务接口实现类
 * @author: chenhongzhi
 * @date: 2015-12-8 上午9:20:21
 */

@Service
public class PointAllocService {

    // 调用互生币分配的映射器
    @Autowired
    private HsbAllocMapper hsbAllocMapper;
    // 积分分配映射器
    @Autowired
    private PointAllocMapper pointAllocMapper;

    // 退回映射器(全部退货、撤单、冲正)
    @Autowired
    private RetreatAllocMapper retreatAllocMapper;

    // 批处理服务
    @Autowired
    private RunnableService batchService;

    @Autowired
    private ThreadPoolTaskExecutor jobExecutor;

    /***
     * 积分分配
     *
     * @param pointDetail 积分明细入参对象
     * @return List 返回分录信息
     */
    public List<Alloc> allocPoint(PointDetail pointDetail) throws HsException {
        // 获取交易类型
        String transType = pointDetail.getTransType();

        // 积分分配处理,传入积分明细信息
        PointAllocHandle pointAllocHandle = new PointAllocHandle(pointDetail);

        // 解析交易类型 交易特征+交易状态
        String type = TransTypeUtil.traitWay(transType);

        // 根据交易类型调用对应的积分处理方法
        return pointAllocHandle.getList(type);
    }

    /**
     * 退货处理
     *
     * @param back        退货明细信息
     * @param pointDetail 积分明细信息
     * @return 返回分配list
     * @throws HsException
     */
    public List<Alloc> allocBackPoint(BackDetail back, PointDetail pointDetail) throws HsException {
        // 获取入参交易类型
        String transType = back.getTransType();

        // 创建退货处理对象并传入入参对象和积分明细对象
        BackAllocHandle pointAllocHandle = new BackAllocHandle(back, pointDetail);

        // 获取批次号
        String batchNo = pointDetail.getBatchNo();

        // 解析交易类型 交易特征+交易状态
        String type = TransTypeUtil.traitWay(transType);

        if (pointDetail.getIsSettle() == Constants.IS_SETTLE1 && batchNo.equals(DateUtil.DateToString(new Date(), DateUtil.DEFAULT_DATE_FORMAT))) {
            // 判断如果是当天, 则调用积分分配当日退货处理方法
            return pointAllocHandle.getCurBackList(type);
        } else {
            // 否则调用积分分配隔日退货处理方法
            return pointAllocHandle.getNotCurBackList(type);
        }
    }


    /**
     * 查出原单反向交易(撤单、退货、冲正)
     *
     * @param pointDetail 积分明细信息
     * @return 返回分配list
     * @throws HsException
     */
    public Pair<List<Alloc>, EntryAllot> canceQueryOffLineEntry(PointDetail pointDetail) throws HsException {

        // 创建退回处理对象
        RetreatAllocHandle retreatAllocHandle = new RetreatAllocHandle();

        EntryAllot entryAllot = retreatAllocMapper.queryOfflineEntry(pointDetail.getTransNo());

        //积分分路拆分
        List<Alloc> listDataList = RetreatAllocHandle.pointAllotSplit(entryAllot);

        // 分录list数据处理,金额反向操作
        retreatAllocHandle.retreatAlloc(listDataList);

        // 返回
        return Pair.of(listDataList, entryAllot);
    }


    /**
     * 查出原单反向交易(撤单、退货、冲正)
     *
     * @param pointDetail 积分明细信息
     * @return 返回分配list
     * @throws HsException
     */
    public EntryAllot backQueryOffLineEntry(PointDetail pointDetail) throws HsException {

        return retreatAllocMapper.queryOfflineEntryeNo(pointDetail.getTransNo());

    }


    /**
     * 冲正(金额设置为负数)
     *
     * @param correct     积分明细信息
     * @param pointDetail 积分明细信息
     * @return 返回分配list
     * @throws HsException
     */
    public Pair<List<Alloc>, EntryAllot> allocReturnPoint(CorrectDetail correct, PointDetail pointDetail)
            throws HsException {
        // 创建分录存储list
        List<Alloc> list = null;
        Pair<List<Alloc>, EntryAllot> pair = null;
        // 获取交易类型
        String transType = correct.getTransType();

        // 解析订单状态
        String transWay = TransTypeUtil.transStatus(transType);

        // 解析冲正标志
        String writeBack = TransTypeUtil.writeBack(transType);

        if (transWay.equals(Constants.POINT_BUSS_TYPE0)
                || transWay.equals(Constants.POINT_BUSS_TYPE1)
                || transWay.equals(Constants.POINT_BUSS_TYPE2)
                || transWay.equals(Constants.POINT_BUSS_TYPE7)
                || transWay.equals(Constants.POINT_BUSS_TYPE8)
                || transWay.equals(Constants.POINT_BUSS_TYPE9)) {
            // 判断如果订单状态为正常或者已撤单或者已退货, 则调用原订单分配反向操作
            pair = this.canceQueryOffLineEntry(pointDetail);
            list = pair.getLeft();
        } else {
            // 否则抛出互生异常
            PsException.psHsThrowException(new Throwable().getStackTrace()[0],
                    RespCode.PS_TRANSTYPE_ERROR.getCode(), "交易类型错误");
        }

        // 循环遍历分录
        for (Alloc alloc : list) {
            // 设置冲正标志
            alloc.setWriteBack(writeBack);

            // 设置冲正单流水号
            alloc.setTransNo(correct.getReturnNo());

            // 设置冲正交易类型
            alloc.setTransType(correct.getTransType());

            // 设置关联的原单流水号
            alloc.setRelTransNo(pointDetail.getTransNo());

            // 设置关联的交易类型
            alloc.setRelTransType(pointDetail.getTransType());

            if (alloc.getAddAmount() != null) {
                // 判断如果增向金额不为空, 获取增向金额
                BigDecimal addAmount = alloc.getAddAmount();

                // 设置增向金额为负数
                alloc.setAddAmount(addAmount.negate());
            }
            if (alloc.getSubAmount() != null) {
                // 判断如果减向金额不为空, 获取减向金额
                BigDecimal subAmout = alloc.getSubAmount();

                // 设置减向金额为负数
                alloc.setSubAmount(subAmout.negate());
            }
        }
        // 返回
        return pair;
    }

    /**
     * 日终多线程分批分配积分(企业/公司)
     *
     * @throws HsException
     */
    public StringBuffer batAllocPoint(String runDate) throws HsException {
        StringBuffer stringBuffer = new StringBuffer();
        try {
            // 查询可分配积分单数量
            int pageSum = batchService.queryPointSum(runDate);

            // 查询正常积分单数据分页
            PointPage pp = this.querySettleOrder(pageSum);

            pp.setRunDate(runDate);

            // 获取总共页数
            int count = pp.getPageCount();

            // 循环分批分批积分
            int i = 0;
            while (i < count) {
                // 循环设置页数
                pp.setRow(i);
                // 分页查询可分配的积分记录
                List<PointDetail> list = batchService.queryPoint(pp);
                // 将线程放入池中执行
                Future<Boolean> future = jobExecutor.submit(new AllocRunnable(batchService, list, runDate));
                if (future.get()) {
                    stringBuffer.append("总共" + count + "线程执行，第几" + jobExecutor.getThreadPoolExecutor().getActiveCount() + "个线程执行成功！");
                }
                i++;
            }
            pointAllocMapper.updateBPointSettle(runDate);
        } catch (SQLException e) {
            // 积分汇总信息不能为空,抛出互生异常
            throw new HsException(RespCode.PS_DB_SQL_ERROR.getCode(), e.getMessage());
        } catch (HsException e) {
            // 积分汇总信息不能为空,抛出互生异常
            throw e;
        } catch (Exception e) {
            // 积分汇总信息不能为空,抛出互生异常
            throw new HsException(RespCode.FAIL.getCode(), e.getMessage());
        }
        return stringBuffer;
    }

    /**
     * 积分汇总(税后、生成文件)
     */
    public void pointSummary(String runDate) throws HsException {

        try {
            // 查询可分配积分单数量
            int pageSum = pointAllocMapper.queryPointAllotCount(runDate);

            // 查询正常积分单数据分页
            PointPage pointPage = this.querySettleOrder(pageSum);

            pointPage.setRunDate(runDate);
            // 获取总共页数
            int count = pointPage.getPageCount();

            // 循环分批分批积分
            int i = 0;
            while (i < count) {
                // 循环设置页数
                pointPage.setRow(i);

                // 调用积分汇总方法
                List<PointAllot> listPointAllot = pointAllocMapper.queryPointAllot(pointPage);

                //积分分配汇总后拆分成不同的公司类型
                List<Alloc> list = AllocHandle.pointAllotSplit(listPointAllot);

                List<String> hsNoList = AllocHandle.getHsResNo(list);

                // 从缓存中批量查出客户ID
                Map<String, String> custIdMap = PsRedisUtil.getCustIdMap(hsNoList);

                //检查是否查到值
                AllocHandle.isFindRedis(list, custIdMap,"从缓存中查询客户号");

                // 判断积分汇总信息记录数大于零
                if (custIdMap.size() > 0) {
                    //把查出来的custId拼装起来
                    AllocHandle.pointEndDaySum(list, custIdMap);

                    // 从缓存中批量查出企业的税率
                    Map<String, String> taxMapMap = PsRedisUtil.getEntTaxMap(hsNoList);
                    //检查是否查到值
                    AllocHandle.isFindRedis(list, taxMapMap,"从缓存中查询税率");

                    if (taxMapMap.size() > 0) {
                        // 调用积分城市税后处理方法,传入积分汇总list信息
                        List<Alloc> cityTax = AllocHandle.pointCityTax(list, taxMapMap);

                        if (!CollectionUtils.isEmpty(cityTax)) {
                            // 调用保存积分城市税收记录处理方法
                            this.cityTaxList(cityTax);
                        }

                        // 调用扣税后汇总的处理方法,传入汇总、税收信息, 返回扣税后的list
                        AllocHandle.taxAfterSum(list, cityTax);
                    }

                    // 积分汇总list数据保存到积分汇总表
                    this.pointAllocMapper.insertCollect(list);

                    // 生成积分文件
                    AllocHandle.writeFile(list, runDate, Constants.SETTLEMENT_POINT);

                    this.pointAllocMapper.updatePointAllocSettle(runDate);

                } else {
                    // 积分汇总信息不能为空,抛出互生异常
                    throw new HsException(RespCode.PS_ORDER_NOT_FOUND.getCode(), "找不到原订单");
                }
                i++;
            }
        } catch (SQLException e) {
            // 抛出 异常
            PsException.psThrowException(new Throwable().getStackTrace()[0], RespCode.PS_DB_SQL_ERROR
                    .getCode(), e.getMessage(), e);
        } catch (HsException e) {
            // 积分汇总信息不能为空,抛出互生异常
            throw e;
        } catch (Exception e) {
            // 积分汇总信息不能为空,抛出互生异常
            throw new HsException(RespCode.FAIL.getCode(), e.getMessage());
        }
    }

    /**
     * 查询积分订单总数
     *
     * @param pageSum 查询可分配积分单数量
     * @return PointPage 返回分页对象信息
     */
    private PointPage querySettleOrder(int pageSum) {
        // 创建分页对象
        PointPage pp = new PointPage();

        if (pageSum > 0) {
            // 判断如果有积分单,则设置积分单总数
            pp.setPageSum(pageSum);
        }
        return pp;
    }


    /**
     * 保存分录数据
     *
     * @param entryAllot 分录明细信息
     * @throws HsException
     */
    public void addEntryData(EntryAllot entryAllot) throws HsException {
        try {
            // 插入数据到分录表
            this.pointAllocMapper.insertEntry(entryAllot);
        } catch (SQLException e) {
            // 积分汇总信息不能为空,抛出互生异常
            throw new HsException(RespCode.PS_DB_SQL_ERROR.getCode(), e.getMessage());
        } catch (Exception e) {
            if (e instanceof HsException) {
                // 积分汇总信息不能为空,抛出互生异常
                throw e;
            } else {
                // 积分汇总信息不能为空,抛出互生异常
                throw new HsException(RespCode.FAIL.getCode(), e.getMessage());
            }
        }
    }

    /**
     * 修改原分路冲正状态
     *
     * @param writeBack 红冲标识
     * @param entryNo   流水号
     * @throws HsException
     */
    public void updatePosHsbWriteBack(String writeBack, String entryNo) throws HsException {
        try {
            // 插入数据到分录表
            this.hsbAllocMapper.updatePosHsbWriteBack(writeBack, entryNo);
        } catch (SQLException e) {
            // 抛出 异常
            PsException.psThrowException(new Throwable().getStackTrace()[0], RespCode.PS_DB_SQL_ERROR
                    .getCode(), e.getMessage(), e);
        } catch (Exception e) {
            // 积分汇总信息不能为空,抛出互生异常
            throw new HsException(RespCode.FAIL.getCode(), e.getMessage());
        }
    }

    /**
     * 实时保存积分分配数据
     *
     * @param pointAllot 分路
     */
    public void addPointAllotData(List<PointAllot> pointAllot) {
        try {
            this.pointAllocMapper.insertPointAllotDaily(pointAllot);
        } catch (SQLException e) {
            // 积分汇总信息不能为空,抛出互生异常
            throw new HsException(RespCode.PS_DB_SQL_ERROR.getCode(), e.getMessage());
        }
    }


    /**
     * 保存积分城市税收记录
     *
     * @param cityList 积分城市税收明细信息
     */
    void cityTaxList(List<Alloc> cityList) throws HsException {
        try {
            // 插入数据到积分税收表
            pointAllocMapper.insertTax(cityList);
        } catch (SQLException e) {
            // 积分汇总信息不能为空,抛出互生异常
            throw new HsException(RespCode.PS_DB_SQL_ERROR.getCode(), e.getMessage());
        }
    }

    /**
     * 积分税收
     *
     * @throws HsException 异常
     */
    public void pointTax(String runDate) throws HsException {
        try {
            List<Alloc> taxList;
            try {
                taxList = pointAllocMapper.queryTax(runDate);
            } catch (SQLException e) {
                // 积分汇总信息不能为空,抛出互生异常
                throw new HsException(RespCode.PS_DB_SQL_ERROR.getCode(), e.getMessage());
            }
            if (!CollectionUtils.isEmpty(taxList)) {
                // 生成积分文件
                if (taxList.size() > 0) {
                    AllocHandle.writeFile(taxList, runDate, Constants.SETTLEMENT_TAX);
                }

            }
        } catch (SQLException e) {
            // 抛出 异常
            PsException.psThrowException(new Throwable().getStackTrace()[0], RespCode.PS_DB_SQL_ERROR
                    .getCode(), e.getMessage(), e);
        } catch (HsException e) {
            // 积分汇总信息不能为空,抛出互生异常
            throw e;
        } catch (Exception e) {
            // 积分汇总信息不能为空,抛出互生异常
            throw new HsException(RespCode.FAIL.getCode(), e.getMessage());
        }
    }

    /**
     * 日终批量退税
     */
    public void dailyBackTax(String runDate) throws HsException {
        try {
            List<Alloc> list = new ArrayList<>();
            // 查询可分配积分单数量
            int pageSum = pointAllocMapper.queryTaxEntryCount(runDate);
            // 查询正常积分单数据分页
            PointPage pointPage = this.querySettleOrder(pageSum);

            pointPage.setRunDate(runDate);

            // 获取总共页数
            int count = pointPage.getPageCount();

            // 循环分批分批积分
            for (int i = 0; i < count; i++) {
                // 循环设置页数
                pointPage.setRow(i);
                List<PointAllot> dailyBackTaxList = pointAllocMapper.queryTaxEntry(pointPage);
                // 企业互生号
                List<String> listResNo = new ArrayList<>();

                for (PointAllot pointAllot : dailyBackTaxList) { // 列出企业互生号
                    listResNo.add(pointAllot.getTrusteeEntHsNo());
                    listResNo.add(pointAllot.getServiceEntHsNo());
                }
                // 从缓存中批量查出企业的税率
                Map<String, String> taxMapMap = PsRedisUtil.getEntTaxMap(listResNo);

                // 从缓存中批量查出企业的CustId
                Map<String, String> custIdMap = PsRedisUtil.getCustIdMap(listResNo);

                if (!CollectionUtils.isEmpty(dailyBackTaxList)) {
                    for (PointAllot pointAllot : dailyBackTaxList) {
                        //判断税率和CustId为空时，不做操作
                        if (StringUtils.isEmpty(taxMapMap.get(pointAllot.getTrusteeEntHsNo())) && StringUtils.isEmpty(custIdMap.get(pointAllot.getTrusteeEntHsNo()))
                                && StringUtils.isEmpty(taxMapMap.get(pointAllot.getServiceEntHsNo())) && StringUtils.isEmpty(custIdMap.get(pointAllot.getServiceEntHsNo()))) {
                            continue;
                        }
                        //托管企业税收开始
                        Alloc trusteeEn = new Alloc();
                        trusteeEn.setCustId(custIdMap.get(pointAllot.getTrusteeEntHsNo()));
                        trusteeEn.setHsResNo(pointAllot.getTrusteeEntHsNo());
                        trusteeEn.setBatchNo(DateUtil.DateToString(new Date(), DateUtil.DEFAULT_DATE_FORMAT));
                        trusteeEn.setTaxNo(GuidAgent.getStringGuid(Constants.NO_POINT_TAX22 + PsTools.getInstanceNo()));
                        trusteeEn.setCustType(HsResNoUtils.getCustTypeByHsResNo(pointAllot.getTrusteeEntHsNo()));
                        trusteeEn.setSubAmount(AllocItem.getTaxPoint(
                                PsTools.formatBigDec(pointAllot.getTrusteeSubPoint()),
                                taxMapMap.get(pointAllot.getTrusteeEntHsNo())));
                        trusteeEn.setTaxRate(new BigDecimal(taxMapMap.get(pointAllot.getTrusteeEntHsNo())));
                        trusteeEn.setTaxAccType(AccountType.ACC_TYPE_POINT10510.getCode());
                        list.add(trusteeEn);
                        //托管企业税收结束

                        //服务公司税收开始
                        Alloc serviceEnt = new Alloc();
                        serviceEnt.setCustId(custIdMap.get(pointAllot.getServiceEntHsNo()));
                        serviceEnt.setHsResNo(pointAllot.getServiceEntHsNo());
                        serviceEnt.setBatchNo(DateUtil.DateToString(new Date(), DateUtil.DEFAULT_DATE_FORMAT));
                        serviceEnt.setTaxNo(GuidAgent.getStringGuid(Constants.NO_POINT_TAX22 + PsTools.getInstanceNo()));
                        serviceEnt.setCustType(HsResNoUtils.getCustTypeByHsResNo(pointAllot.getServiceEntHsNo()));
                        serviceEnt.setSubAmount(AllocItem.getTaxPoint(
                                PsTools.formatBigDec(pointAllot.getServiceSubPoint()),
                                taxMapMap.get(pointAllot.getServiceEntHsNo())));
                        serviceEnt.setTaxAccType(AccountType.ACC_TYPE_POINT10510.getCode());
                        serviceEnt.setTaxRate(new BigDecimal(taxMapMap.get(pointAllot.getServiceEntHsNo())));
                        //目前没有，为空
                        //serviceEnt.setRelPvNo();
                        list.add(serviceEnt);
                        //服务公司税收结束
                /*	newalloc.setTaxAccType(AccountType.ACC_TYPE_POINT10110.getCode());
                    newalloc.setAddAmount(alloc.getSubAmount());
					newalloc.setTaxNo(PsTools.GUID(Constants.NO_POINT_TAX22));
					list.add(newalloc);*/
                    }
                    pointAllocMapper.insertTax(list);
                }
            }
        } catch (SQLException e) {
            // 积分汇总信息不能为空,抛出互生异常
            throw new HsException(RespCode.PS_DB_SQL_ERROR.getCode(), e.getMessage());
        } catch (Exception e) {
            if (e instanceof HsException) {
                // 积分汇总信息不能为空,抛出互生异常
                throw e;
            } else {
                // 积分汇总信息不能为空,抛出互生异常
                throw new HsException(RespCode.FAIL.getCode(), e.getMessage());
            }
        }
    }

    /**
     * 获取原交易已分配的积分
     *
     * @param relTransNo
     * @return
     */
    public PointAllot getPointAllot(String relTransNo) throws HsException {
        PointAllot pointAllot = null;
        try {
            pointAllot = this.pointAllocMapper.getPointAllot(relTransNo);
        } catch (Exception e) {
            // 抛出 异常
            PsException.psThrowException(new Throwable().getStackTrace()[0], RespCode.PS_DB_SQL_ERROR.getCode(), e
                    .getMessage(), e);
        }
        return pointAllot;
    }

}
