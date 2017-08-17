/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.ps.distribution.service;

import com.github.knightliao.apollo.utils.time.DateUtils;
import com.gy.hsxt.bp.client.api.BusinessParamSearch;
import com.gy.hsxt.common.constant.CustType;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.DateUtil;
import com.gy.hsxt.ps.common.Compute;
import com.gy.hsxt.ps.common.Constants;
import com.gy.hsxt.ps.common.PsException;
import com.gy.hsxt.ps.common.PsRedisUtil;
import com.gy.hsxt.ps.distribution.bean.Alloc;
import com.gy.hsxt.ps.distribution.handle.AllocHandle;
import com.gy.hsxt.ps.distribution.mapper.HsbAllocMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @version V3.0
 * @Package: com.gy.hsxt.ps.distribution.service
 * @ClassName: HsbAllocService
 * @Description: 互生币分配服务接口实现类
 * @author: chenhongzhi
 * @date: 2015-12-8 上午9:20:21
 */

@Service
public class HsbAllocService {
    // 调用互生币分配的映射器
    @Autowired
    private HsbAllocMapper hsbAllocMapper;

    // 调用积分分配服务
    @Autowired
    private PointAllocService pointAllocService;

    // 业务参数系统配置服务
    @Autowired
    private BusinessParamSearch businessParamSearch;

    /**
     * 查询互生币POS分录交易订单
     */
    private List<Alloc> queryPosHsbTrans(String runDate) throws HsException {
        List<Alloc> list = new ArrayList<>();
        try {
            list.addAll(hsbAllocMapper.queryOfflineEntry(runDate));
            list.addAll(hsbAllocMapper.queryOfflineEntryOnlyBc(runDate));
        } catch (SQLException e) {
            // 积分汇总信息不能为空,抛出互生异常
            throw new HsException(RespCode.PS_DB_SQL_ERROR.getCode(), e.getMessage());
        } catch (Exception e) {
            // 积分汇总信息不能为空,抛出互生异常
            throw new HsException(RespCode.FAIL.getCode(), e.getMessage());
        }
        return list;
    }

    /**
     * 日终查询电商分录的交易订单
     */
    private List<Alloc> queryElectricityTrans(String runDate) throws HsException {
        List<Alloc> list = new ArrayList<>();
        try {
            list.addAll(hsbAllocMapper.queryOnlineEntry(runDate));
            list.addAll(hsbAllocMapper.queryOnlineEntryOnlyBc(runDate));
        } catch (SQLException e) {
            // 积分汇总信息不能为空,抛出互生异常
            throw new HsException(RespCode.PS_DB_SQL_ERROR.getCode(), e.getMessage());
        } catch (Exception e) {
            // 积分汇总信息不能为空,抛出互生异常
            throw new HsException(RespCode.FAIL.getCode(), e.getMessage());
        }
        return list;
    }

    /**
     * 日结暂存商业服务费、计算商业服务费
     */
    private List<Alloc> businessServiceFee(List<Alloc> ecListHsbCollect) throws HsException {
        List<Alloc> serviceList = new ArrayList<>();
        try {
            List<Alloc> ecList = new ArrayList<>();
            ecList.addAll(ecListHsbCollect);
            if (!CollectionUtils.isEmpty(ecList)) {
                // 日终商业服务费暂存计算处理
                serviceList = AllocHandle.serviceDayCsc(ecList, businessParamSearch);
                if (!CollectionUtils.isEmpty(serviceList)) {
                    this.hsbAllocMapper.insertDailyCsc(serviceList);
                }
                // 商业服务费数据写入文件
                // AllocHandle.writeFile(serviceList, Constants.SETTLEMENT_CSC);
            }
        } catch (SQLException e) {
            // 积分汇总信息不能为空,抛出互生异常
            throw new HsException(RespCode.PS_DB_SQL_ERROR.getCode(), e.getMessage());
        } catch (Exception e) {
            // 积分汇总信息不能为空,抛出互生异常
            throw new HsException(RespCode.FAIL.getCode(), e.getMessage());
        }
        return serviceList;
    }

    /**
     * 查询汇总当月的商业服务费
     *
     * @throws HsException
     */
    private List<Alloc> queryMonthCsc(String runDate) throws HsException {
        try {
            return this.hsbAllocMapper.queryMonthCsc(runDate);
        } catch (SQLException e) {
            // 积分汇总信息不能为空,抛出互生异常
            throw new HsException(RespCode.PS_DB_SQL_ERROR.getCode(), e.getMessage());
        } catch (Exception e) {
            // 积分汇总信息不能为空,抛出互生异常
            throw new HsException(RespCode.FAIL.getCode(), e.getMessage());
        }
    }

    /**
     * 月结商业服务费(服务公司、平台)
     */
    @Transactional
    public void monthlyBusinessServiceFee(String runDate) throws HsException {
        try {
            //截取年月
            String newRunDate = runDate.substring(0, 7);
            // 查询汇总当月的商业服务费
            List<Alloc> serviceList = this.queryMonthCsc(newRunDate);

            //服务公司互生号
            List<String> serviceResNo = new ArrayList<>();
            if (!CollectionUtils.isEmpty(serviceList)) {
                //取值互生号
                List<String> hsNoList = AllocHandle.getHsResNo(serviceList);

                hsNoList.addAll(serviceResNo);
                //取客户ID号
                Map<String, String> custIdMap = PsRedisUtil.getCustIdMap(hsNoList);
                Map<String, String> taxMap;
                if (!CollectionUtils.isEmpty(custIdMap)) {
                    //检查是否查到值
                    AllocHandle.isFindRedis(serviceList, custIdMap, "从缓存中查询客户号");
                    //取税收
                    taxMap = PsRedisUtil.getEntTaxMap(hsNoList);

                    AllocHandle.isFindRedis(serviceList, taxMap, "从缓存中查询税率");
                    //计算出税收
                    List<Alloc> listTax = AllocHandle.serviceMonthServiceFeeTaxTate(serviceList, custIdMap, taxMap);
                    if (!CollectionUtils.isEmpty(listTax)) {
                        // 保存城市税收记录
                        pointAllocService.cityTaxList(listTax);
                    }
                    // 商业服务费结算
                    List<Alloc> list = AllocHandle.serviceMonthServiceFee(serviceList, custIdMap, taxMap);

                    BigDecimal bigDecimalAreaPlat = BigDecimal.ZERO;
                    if (!CollectionUtils.isEmpty(list)) {
                        List<Alloc> listAreaPlats = new ArrayList<>();
                        Alloc areaPlatAlloc;
                        for (Alloc newsList : list) {
                            if (newsList.getCustType().equals(CustType.AREA_PLAT.getCode())) {
                                bigDecimalAreaPlat = Compute.add(bigDecimalAreaPlat, newsList.getTollAddAmount());
                                listAreaPlats.add(newsList);
                            }
                        }

                        list.removeAll(listAreaPlats);

                        if (!CollectionUtils.isEmpty(listAreaPlats)) {
                            areaPlatAlloc = listAreaPlats.get(0);
                            areaPlatAlloc.setTollAddAmount(bigDecimalAreaPlat);
                            list.add(areaPlatAlloc);
                        }

                        //给已结算值
                        for (Alloc alloc : list) {
                            alloc.setIsSettle(Constants.IS_SETTLE0);
                        }
                        // 保存商业服务费数据到服务费表
                        this.hsbAllocMapper.insertDailyCsc(list);
                        // 生成商业服务费文件
                        AllocHandle.writeFile(list, DateUtil.DateToString(DateUtils.getLastDayOfMonth(DateUtil.StringToDate(runDate))), Constants.SETTLEMENT_CSC);
                    }
                }
            }
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
    }

    /**
     * 互生币汇总
     */
    @Transactional
    public void hsbSummary(String runDate) throws HsException {
        try {
            // 创建存储对象(企业收入数据)
            List<Alloc> entList = new ArrayList<>();

            // 创建存储对象(文件数据)
            List<Map<String, String>> dataList = new ArrayList<>();

            // 创建存储对象(文件数据)
            List<Map<String, String>> dataListEs = new ArrayList<>();

            // 创建存储对象(文件数据)
            List<Map<String, String>> dataListPs = new ArrayList<>();

            // 汇总企业线下收入
            List<Alloc> posHsbList = this.queryPosHsbTrans(runDate);

            if (posHsbList.size() > 0) {
                AllocHandle.posHsbSumList(posHsbList, dataList, runDate);
                entList.addAll(posHsbList);
            }
            // 汇总企业商城收入
            List<Alloc> ecHsbList = this.queryElectricityTrans(runDate);

            if (ecHsbList.size() > 0) {
                // 企业商城收入减去商业服务费
                AllocHandle.ecHsbSumList(ecHsbList, dataList, runDate);
                entList.addAll(ecHsbList);
                // 日终暂存商业服务费并保存到数据库
                this.businessServiceFee(ecHsbList);
            }

            // 企业商城汇总后的收入和线下汇总后的收入保存到数据库
            if (entList.size() > 0) {
                //给已结算值
                for (Alloc alloc : entList) {
                    alloc.setIsSettle(Constants.IS_SETTLE0);
                }
                this.hsbAllocMapper.insertHsbSum(entList);
            }

            // 生成文件供账务批记账
            if (dataList.size() > 0) {
                for (Map dl : dataList) {
                    String transType = Objects.toString(dl.get("transType"), "");
                    if (transType.equals(Constants.HSB_BUSINESS_OFFLINE_CSC)) {
                        dataListPs.add(dl);
                    }
                    if (transType.equals(Constants.HSB_BUSINESS_ONLINE_CSC)) {
                        dataListEs.add(dl);
                    }
                }
                if (!CollectionUtils.isEmpty(dataListPs)) {
                    writeFile(dataListPs, runDate, Constants.SETTLEMENT_HSB);
                }
                if (!CollectionUtils.isEmpty(dataListEs)) {
                    writeFile(dataListEs, runDate, Constants.SETTLEMENT_HSB);
                }
                // this.hsbAllocMapper.updateHsbSumSettle(runDate);
                this.hsbAllocMapper.updateEsHsbSettle(runDate);
                this.hsbAllocMapper.updatePosHsbSettle(runDate);
                this.hsbAllocMapper.updatePointSettle(runDate);
                this.hsbAllocMapper.updateBPointSettle(runDate);
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
     * 增加同步锁
     * @param list
     * @param runDate
     * @param typeName
     * @throws Exception
     */
    private synchronized void writeFile(List<Map<String, String>> list, String runDate, String typeName) throws Exception {
        AllocHandle.writeHsbFile(list, runDate, typeName);
    }

}
