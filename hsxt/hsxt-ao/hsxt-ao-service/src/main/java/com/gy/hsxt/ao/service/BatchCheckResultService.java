/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.ao.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.ao.AOErrorCode;
import com.gy.hsxt.ao.api.IAOBatchCheckResultService;
import com.gy.hsxt.ao.bean.BatchSettle;
import com.gy.hsxt.ao.bean.BatchUpload;
import com.gy.hsxt.ao.disconf.AoConfig;
import com.gy.hsxt.ao.interfaces.IBatchCheckResultService;
import com.gy.hsxt.ao.mapper.BatchSettleHisMapper;
import com.gy.hsxt.ao.mapper.BatchSettleMapper;
import com.gy.hsxt.ao.mapper.BatchUploadHisMapper;
import com.gy.hsxt.ao.mapper.BatchUploadMapper;
import com.gy.hsxt.ao.mapper.BuyHsbMapper;
import com.gy.hsxt.ao.mapper.ProxyBuyHsbMapper;
import com.gy.hsxt.common.constant.BizGroup;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.guid.GuidAgent;
import com.gy.hsxt.common.utils.BigDecimalUtils;
import com.gy.hsxt.common.utils.HsAssert;

/**
 * 终端设备批总账对账
 * 
 * @Package: com.gy.hsxt.ao.service
 * @ClassName: BatchCheckResultService
 * @Description:
 * 
 * @author: liyh
 * @date: 2015-12-3 下午3:28:30
 * @version V1.0
 */
@Service
public class BatchCheckResultService implements IAOBatchCheckResultService, IBatchCheckResultService {

    /** 业务服务私有配置参数 **/
    @Autowired
    private AoConfig aoConfig;

    /** 兑换互生币Dao接口 **/
    @Resource
    BuyHsbMapper buyHsbMapper;

    /** 代兑互生币Dao接口 **/
    @Resource
    ProxyBuyHsbMapper proxyBuyHsbMapper;

    /** 终端数据批结算Dao接口 **/
    @Resource
    BatchSettleMapper batchSettleMapper;

    /** 终端数据批上传Dao接口 **/
    @Resource
    BatchUploadMapper batchUploadMapper;

    /** 终端数据批结算历史Dao接口 **/
    @Resource
    BatchSettleHisMapper batchSettleHisMapper;

    /** 终端数据批上传历史 Dao接口 **/
    @Resource
    BatchUploadHisMapper batchUploadHisMapper;

    /**
     * 终端设备批总账对账
     * 
     * @param batchSettle
     *            结算批次信息
     * @param buyHsbAmt
     *            兑换互生币总金额
     * @param hsbCount
     *            兑换互生币总笔数
     * @param proxyBuyHsbAmt
     *            代兑互生币总金额
     * @param proxyHsbCount
     *            代兑互生币总笔数
     * @return
     * @see com.gy.hsxt.ao.api.IAOBatchCheckResultService#batchCheckResult(com.gy.hsxt.ao.bean.BatchSettle,
     *      java.lang.String, java.lang.String, java.lang.String,
     *      java.lang.String)
     */
    public Boolean batchCheckResult(BatchSettle batchSettle, String buyHsbAmt, String hsbCount, String proxyBuyHsbAmt,
            String proxyHsbCount) {
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                "终端设备批总账对账,params[" + batchSettle + ",buyHsbAmt:" + buyHsbAmt + ",hsbCount:" + hsbCount
                        + ",proxyBuyHsbAmt:" + proxyBuyHsbAmt + ",proxyHsbCount:" + proxyHsbCount + "]");
        // 已经结算的结果 1：通过 0：不通过
        Integer isSame = null;
        // 兑换互生币Map
        Map<String, Object> buyHsbMap = null;
        // 代兑互生币Map
        Map<String, Object> proxyBuyHsbMap = null;
        // 统计的兑换互生币总笔数
        String hsbCountValueStr = "0";
        // 统计的 兑换互生币总金额
        String hsbAmountValueStr = "0";
        // 统计的 代兑互生币总笔数
        String proxyHsbCountValueStr = "0";
        // 统计的代兑互生币总金额
        String proxyHsbAmountValueStr = "0";
        // 终端设备业务数据批结算结果
        boolean flag = false;
        boolean isBuyHsb = false;
        boolean isProxyHsb = false;
        // 结算批次信息不能为空
        HsAssert.notNull(batchSettle, AOErrorCode.AO_PARAMS_NULL, "终端设备批总账结算：结算批次信息为空");
        HsAssert.notNull(batchSettle.getEntCustId(), AOErrorCode.AO_PARAMS_NULL, "终端设备业务数据批上传：企业客户号为空");
        HsAssert.notNull(batchSettle.getEntResNo(), AOErrorCode.AO_PARAMS_NULL, "终端设备业务数据批上传：企业互生号为空");
        HsAssert.notNull(batchSettle.getBatchNo(), AOErrorCode.AO_PARAMS_NULL, "终端设备业务数据批上传： 批次编号为空");
        HsAssert.notNull(batchSettle.getChannel(), AOErrorCode.AO_PARAMS_NULL, "终端设备业务数据批上传：终端渠道为空");
        HsAssert.notNull(batchSettle.getTermNo(), AOErrorCode.AO_PARAMS_NULL, "终端设备业务数据批上传：终端编号为空");
        HsAssert.hasText(buyHsbAmt, AOErrorCode.AO_PARAMS_NULL, "终端设备批总账结算：兑换互生币总金额为空params[" + buyHsbAmt + "]");
        HsAssert.hasText(hsbCount, AOErrorCode.AO_PARAMS_NULL, "终端设备批总账结算：兑换互生币总笔数为空params[" + hsbCount + "]");
        HsAssert.hasText(proxyBuyHsbAmt, AOErrorCode.AO_PARAMS_NULL, "终端设备批总账结算：代兑互生币总金额为空params[" + proxyBuyHsbAmt
                + "]");
        HsAssert.hasText(proxyHsbCount, AOErrorCode.AO_PARAMS_NULL, "终端设备批总账结算：代兑互生币总笔数为空params[" + proxyHsbCount + "]");
        // 1根据结算批次信息查询是否已经存在批结算记录，如果有直接返回记录中的结果。

        batchSettle.setBuyHsbSum(buyHsbAmt);
        batchSettle.setBuyHsbCnt(Integer.parseInt(hsbCount));
        batchSettle.setProxyHsbSum(proxyBuyHsbAmt);
        batchSettle.setProxyHsbCnt(Integer.parseInt(proxyHsbCount));
        isSame = batchSettleMapper.findBatchSettleByInfo(batchSettle);

        if (isSame == null)
        {
            try
            {
                // 2根据（企业客户号、终端渠道、终端编号、批次编号）统计兑换互生币总金额与总笔数
                buyHsbMap = buyHsbMapper.findBuyHsbInfoByBatchNo(batchSettle.getEntCustId(), batchSettle.getChannel(),
                        batchSettle.getTermNo(), batchSettle.getBatchNo());
                // 获取总笔数
                if (buyHsbMap.get("HSBCOUNT") != null && Double.parseDouble(buyHsbMap.get("HSBCOUNT").toString()) > 0)
                {
                    hsbCountValueStr = ((BigDecimal) buyHsbMap.get("HSBCOUNT")).toString();
                }
                // 获取总金额
                if (buyHsbMap.get("HSBAMOUNT") != null)
                {
                    hsbAmountValueStr = ((BigDecimal) buyHsbMap.get("HSBAMOUNT")).toString();
                }
            }
            catch (Exception e)
            {
                SystemLog.error(this.getClass().getName(), "function:" + "batchCheckResult",
                        AOErrorCode.AO_QUERY_BUY_HSB_TOTAL_AMOUNT_AND_ITEMS_ERROR + "查询兑换互生币总金额与总笔数异常,params["
                                + batchSettle + "]", e);
                throw new HsException(AOErrorCode.AO_QUERY_BUY_HSB_TOTAL_AMOUNT_AND_ITEMS_ERROR,
                        "查询兑换互生币总金额与总笔数异常,params[" + batchSettle + "]");
            }

            try
            {
                // 3根据（企业客户号、终端渠道、终端编号、批次编号）统计代兑互生币总金额与总笔数
                proxyBuyHsbMap = proxyBuyHsbMapper.findProxyBuyHsbInfoByBatchNo(batchSettle.getEntCustId(), batchSettle
                        .getChannel(), batchSettle.getTermNo(), batchSettle.getBatchNo());
                if (proxyBuyHsbMap.get("HSBCOUNT") != null
                        && Double.parseDouble(proxyBuyHsbMap.get("HSBCOUNT").toString()) > 0)
                {
                    proxyHsbCountValueStr = ((BigDecimal) proxyBuyHsbMap.get("HSBCOUNT")).toString();
                }
                if (proxyBuyHsbMap.get("HSBAMOUNT") != null)
                {
                    proxyHsbAmountValueStr = ((BigDecimal) proxyBuyHsbMap.get("HSBAMOUNT")).toString();
                }
            }
            catch (Exception e)
            {
                SystemLog.error(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                        AOErrorCode.AO_QUERY_PROXY_BUY_HSB_TOTAL_AMOUNT_AND_ITEMS_ERROR.getCode()
                                + "查询代兑互生币总金额与总笔数异常,params[" + batchSettle + "]", e);
                throw new HsException(AOErrorCode.AO_QUERY_PROXY_BUY_HSB_TOTAL_AMOUNT_AND_ITEMS_ERROR,
                        "查询代兑互生币总金额与总笔数异常,params[" + batchSettle + "]" + e);
            }
            // 4比较总金额与总笔数，只要有一个总金额或者总笔数不匹配就返回false。
            // 比较兑换总金额与总笔数
            if (BigDecimalUtils.compareTo(hsbAmountValueStr, buyHsbAmt) == 0
                    && BigDecimalUtils.compareTo(hsbCountValueStr, hsbCount) == 0)
            {
                isBuyHsb = true;
            }

            // 比较代兑总金额与总笔数
            if (BigDecimalUtils.compareTo(proxyHsbCountValueStr, proxyHsbCount) == 0
                    && BigDecimalUtils.compareTo(proxyHsbAmountValueStr, proxyBuyHsbAmt) == 0)
            {
                isProxyHsb = true;
            }

            if (isBuyHsb && isProxyHsb)
            {
                flag = true;
            }

            // 5保存批结算结果。结果入库
            try
            {
                batchSettle.setBatchCheckId(GuidAgent.getStringGuid(BizGroup.AO + aoConfig.getAppNode()));
                batchSettle.setBuyHsbSum(buyHsbAmt);
                batchSettle.setBuyHsbCnt(Integer.valueOf(hsbCount));
                batchSettle.setProxyHsbSum(proxyBuyHsbAmt);
                batchSettle.setProxyHsbCnt(Integer.valueOf(proxyHsbCount));
                // 1：通过 0：不通过
                if (flag)
                {
                    batchSettle.setIsSame(1);
                }
                else
                {
                    batchSettle.setIsSame(0);
                }
                // 1：已上传 0：未上传
                batchSettle.setUploadFlag(0);
                // 2：待处理 3：已处理
                batchSettle.setProcessStatus(2);
                batchSettleMapper.insertBatchSettle(batchSettle);
            }
            catch (Exception e)
            {
                SystemLog.error(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                        AOErrorCode.AO_SAVE_TERMINAL_BATCH_SETTLE_ERROR.getCode() + "终端设备业务数据批结算保存异常,params["
                                + batchSettle + ",buyHsbAmt:" + buyHsbAmt + ",hsbCount:" + hsbCount
                                + ",proxyBuyHsbAmt:" + proxyBuyHsbAmt + ",proxyHsbCount:" + proxyHsbCount + "]", e);
                throw new HsException(AOErrorCode.AO_SAVE_TERMINAL_BATCH_SETTLE_ERROR, "终端设备业务数据批结算保存异常params["
                        + batchSettle + ",buyHsbAmt:" + buyHsbAmt + ",hsbCount:" + hsbCount + ",proxyBuyHsbAmt:"
                        + proxyBuyHsbAmt + ",proxyHsbCount:" + proxyHsbCount + "]" + e);
            }
        }
        else
        {
            // 已经结算的结果 1：通过 0：不通过
            if (isSame == 1)
            {
                flag = true;
            }

            isSame = batchSettleMapper.findExistsBatchSettleInfo(batchSettle);
            if (isSame == 0)
                flag = false;
        }
        return flag;
    }

    /**
     * 终端设备业务数据批上传
     * 
     * @param batchSettle
     *            结算批次信息
     * @param listBatchUpload
     *            List<业务明细>
     * @see com.gy.hsxt.ao.api.IAOBatchCheckResultService#batchTerminalDataUpload(com.gy.hsxt.ao.bean.BatchSettle,
     *      java.util.List)
     */
    @Transactional
    public void batchTerminalDataUpload(BatchSettle batchSettle, List<BatchUpload> listBatchUpload) {
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                "终端设备业务数据批上传,params[" + batchSettle + ",listBatchUpload:" + JSON.toJSONString(listBatchUpload) + "]");
        // 终端批结算上传标识
        Integer uploadFlag = null;
        HsAssert.notNull(batchSettle, AOErrorCode.AO_PARAMS_NULL, "终端设备业务数据批上传：结算批次信息为空");
        HsAssert.notNull(batchSettle.getEntCustId(), AOErrorCode.AO_PARAMS_NULL, "终端设备业务数据批上传：企业客户号为空");
        HsAssert.notNull(batchSettle.getEntResNo(), AOErrorCode.AO_PARAMS_NULL, "终端设备业务数据批上传：企业互生号为空");
        HsAssert.notNull(batchSettle.getBatchNo(), AOErrorCode.AO_PARAMS_NULL, "终端设备业务数据批上传： 批次编号为空");
        HsAssert.notNull(batchSettle.getChannel(), AOErrorCode.AO_PARAMS_NULL, "终端设备业务数据批上传：终端渠道为空");
        HsAssert.notNull(batchSettle.getTermNo(), AOErrorCode.AO_PARAMS_NULL, "终端设备业务数据批上传：终端编号为空");
        HsAssert.notNull(listBatchUpload, AOErrorCode.AO_PARAMS_NULL, "终端设备业务数据批上传：业务明细信息为空");
        try
        {
            // 1 根据批次信息查询是否已经上传过数据了
            uploadFlag = batchSettleMapper.findUploadFlagByInfo(batchSettle);
            if (uploadFlag != null)
            {
                HsAssert.isTrue(uploadFlag.intValue() <= 0, AOErrorCode.AO_TERMINAL_DIVICE_BIZ_DATA_UPLOADED,
                        "终端设备业务数据已经上传,params[" + batchSettle + "]");
            }
        }
        catch (HsException e)
        {
            SystemLog.error(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), e
                    .getErrorCode()
                    + "：" + e.getMessage(), e);
            throw e;
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                    AOErrorCode.AO_QUERY_TERMINAL_BATCH_UPLOAD_FLAG_ERROR.getCode() + "查询终端批结算上传标识异常,params["
                            + batchSettle + "]", e);
            throw new HsException(AOErrorCode.AO_QUERY_TERMINAL_BATCH_UPLOAD_FLAG_ERROR, "查询终端批结算上传标识异常,params["
                    + batchSettle + "]");
        }

        try
        {
            // 2根据批次信息查询批总账编号
            String batchCheckId = batchSettleMapper.findBatchCheckIdByInfo(batchSettle);
            // 未批结算
            HsAssert.hasText(batchCheckId, AOErrorCode.AO_UN_BATCH_SETTLE, "未批结算不能做批上传,params[batchCheckId:"
                    + batchCheckId + "]");
            // 3 将查询出来的批总账编号更新到明细对象中
            for (int i = 0; i < listBatchUpload.size(); i++)
            {
                listBatchUpload.get(i).setDetailCheckId(GuidAgent.getStringGuid(BizGroup.AO + aoConfig.getAppNode()));
                listBatchUpload.get(i).setBatchCheckId(batchCheckId);
            }
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                    AOErrorCode.AO_QUERY_TERMIANL_BATCH_NUMBER_ERROR.getCode() + "查询终端批结算批总账编号异常,params[" + batchSettle
                            + "]", e);
            throw new HsException(AOErrorCode.AO_QUERY_TERMIANL_BATCH_NUMBER_ERROR, "查询终端批结算批总账编号异常params["
                    + batchSettle + "]" + e);
        }

        // 4 将业务明细数据存入数据库
        try
        {
            batchUploadMapper.insertBatchUpload(listBatchUpload);
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                    AOErrorCode.AO_SAVE_TERMINAL_BATCH_UPLOAD_ERROR.getCode() + "保存终端批上传异常,params[" + listBatchUpload
                            + "]", e);
            throw new HsException(AOErrorCode.AO_SAVE_TERMINAL_BATCH_UPLOAD_ERROR, "保存终端批上传异常,params["
                    + listBatchUpload + "]" + e);
        }

        try
        {
            // 5 修改终端批结算表上传时间和上传标识
            batchSettle.setUploadFlag(1);
            batchSettleMapper.updateBatchSettleByInfo(batchSettle);
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                    AOErrorCode.AO_UPDATE_TERMINAL_BATCH_TIME_AND_FLAG_ERROR.getCode() + "终端批结算更新上传时间和上传标识异常,params["
                            + batchSettle + "]", e);
            throw new HsException(AOErrorCode.AO_UPDATE_TERMINAL_BATCH_TIME_AND_FLAG_ERROR,
                    "终端批结算更新上传时间和上传标识异常,params[" + batchSettle + "]" + e);
        }
    }

    /**
     * 终端设备批结算数据老化
     * 
     * @see com.gy.hsxt.ao.interfaces.IBatchCheckResultService#batchDataTransfer()
     */
    @Override
    public void batchDataTransfer() {
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                "终端设备批结算数据老化,params[]");
        try
        {
            batchSettleHisMapper.insertBatchSettleHisLastMath(aoConfig.getDataTransfer());
        }
        catch (Exception e)
        {
            SystemLog.error(aoConfig.getSysName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                    AOErrorCode.AO_DATA_TRANSFER.getCode() + ":终端设备批结算数据老化，迁移前 " + aoConfig.getDataTransfer()
                            + " 几个月批结算数据到历史表异常", e);
            throw new HsException(AOErrorCode.AO_DATA_TRANSFER, "终端设备批结算数据老化，迁移前 " + aoConfig.getDataTransfer()
                    + " 几个月批结算数据到历史表异常" + e);
        }

        try
        {
            batchUploadHisMapper.insertBatchUploadHisLastMath(aoConfig.getDataTransfer());
        }
        catch (Exception e)
        {
            SystemLog.error(aoConfig.getSysName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                    AOErrorCode.AO_DATA_TRANSFER.getCode() + ":终端设备批结算数据老化，插入前 " + aoConfig.getDataTransfer()
                            + " 个月的终端批上传明细数据异常", e);
            throw new HsException(AOErrorCode.AO_DATA_TRANSFER, "终端设备批结算数据老化，插入前 " + aoConfig.getDataTransfer()
                    + " 个月的终端批上传明细数据异常" + e);
        }

        try
        {
            batchUploadMapper.deleteBatchUploadLastMath(aoConfig.getDataTransfer());
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                    AOErrorCode.AO_REMOVE_ERROR.getCode() + ":终端设备批结算数据老化，删除前 " + aoConfig.getDataTransfer()
                            + " 个月终端批上传数据异常", e);
            throw new HsException(AOErrorCode.AO_REMOVE_ERROR, "终端设备批结算数据老化，删除前 " + aoConfig.getDataTransfer()
                    + " 个月终端批上传数据异常" + e);
        }

        try
        {
            batchSettleMapper.deleteBatchSettleLastMath(aoConfig.getDataTransfer());
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                    AOErrorCode.AO_REMOVE_ERROR.getCode() + ":终端设备批结算数据老化，删除前 " + aoConfig.getDataTransfer()
                            + " 个月终端批结算数据异常", e);
            throw new HsException(AOErrorCode.AO_REMOVE_ERROR, "终端设备批结算数据老化，删除前 " + aoConfig.getDataTransfer()
                    + " 个月终端批结算数据异常" + e);
        }
    }
}
