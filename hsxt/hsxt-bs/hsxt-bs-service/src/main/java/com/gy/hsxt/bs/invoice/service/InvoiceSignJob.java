/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.invoice.service;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.fastjson.JSON;
import com.gy.hsi.ds.api.IDSBatchServiceListener;
import com.gy.hsi.lc.client.log4j.BizLog;
import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.bs.annualfee.utils.AnnualAreaUtils;
import com.gy.hsxt.bs.bean.invoice.InvoicePlat;
import com.gy.hsxt.bs.bean.invoice.InvoiceQuery;
import com.gy.hsxt.bs.common.BSConstants;
import com.gy.hsxt.bs.common.enumtype.invoice.InvoiceStatus;
import com.gy.hsxt.bs.common.enumtype.invoice.ReceiveWay;
import com.gy.hsxt.bs.disconf.BsConfig;
import com.gy.hsxt.bs.invoice.interfaces.IInvoicePlatService;
import com.gy.hsxt.bs.invoice.interfaces.IInvoiceSignJob;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.HsAssert;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 发票自动签收任务
 *
 * @Package : com.gy.hsxt.bs.invoice.service
 * @ClassName : InvoiceSignJob
 * @Description : 发票自动签收任务
 * @Author : chenm
 * @Date : 2016/1/19 14:34
 * @Version V3.0.0.0
 */
@Service
public class InvoiceSignJob implements IInvoiceSignJob {

    /**
     * 业务服务私有配置参数
     **/
    @Resource
    private BsConfig bsConfig;

    /**
     * 调度监听器
     */
    @Resource
    private IDSBatchServiceListener batchServiceListener;

    /**
     * 平台发票业务实现
     */
    @Resource
    private IInvoicePlatService invoicePlatService;

    /**
     * 调度执行方法
     *
     * @param executeId 执行ID
     * @param map 参数
     * @return {@code boolean}
     * @see com.gy.hsi.ds.api.IDSBatchService#excuteBatch(java.lang.String,
     * java.util.Map)
     */
    @Override
    public boolean excuteBatch(String executeId, Map<String, String> map) {
        // 系统日志
        BizLog.biz(bsConfig.getSysName(), "function:executeBatch", "params==>" + JSON.toJSONString(map), "========平台发票自动签收开始========");
        try {
            // 提前扫描天数设置
            HsAssert.notNull(map, RespCode.PARAM_ERROR, "平台发票自动签收任务参数[map]为空");
            Object days = map.get("days");
            // 默认为查昨天以前的
            int ahead = (days == null || StringUtils.isBlank(String.valueOf(days)) || (!NumberUtils.isNumber(String
                    .valueOf(days)))) ? 0 : (1 - NumberUtils.toInt(String.valueOf(days)));

            batchServiceListener.reportStatus(executeId, 2, "执行中....");

            InvoiceQuery query = new InvoiceQuery();
            query.setStatus(InvoiceStatus.RECEIVING.ordinal());// 待签收状态
            query.setEndDate(AnnualAreaUtils.nextToday(ahead));// 几天后
            List<InvoicePlat> invoicePlats = invoicePlatService.queryListByQuery(query);

            if (CollectionUtils.isNotEmpty(invoicePlats)) {
                for (InvoicePlat invoicePlat : invoicePlats) {
                    // 设置参数
                    invoicePlat.setReceiveWay(ReceiveWay.AUTO.ordinal());// 自动签收
                    invoicePlat.setStatus(InvoiceStatus.RECEIVED.ordinal());// 已签收
                    invoicePlat.setUpdatedBy(BSConstants.SYS_OPERATOR);// 系统操作
                    // 更新操作
                    invoicePlatService.modifyBean(invoicePlat);
                }
            }

            batchServiceListener.reportStatus(executeId, 0, "执行成功");
            // 系统日志
            BizLog.biz(bsConfig.getSysName(), "function:executeBatch", "", "========平台发票自动签收结束==========");
            return true;
        } catch (HsException hse) {
            SystemLog.error(bsConfig.getSysName(), "function:executeBatch", hse.getMessage(), hse);
            batchServiceListener.reportStatus(executeId, 1, "执行失败原因:[" + hse.getMessage() + "]");
            return false;
        } catch (Exception e) {
            SystemLog.error(bsConfig.getSysName(), "function:executeBatch", "平台发票自动签收失败", e);
            batchServiceListener.reportStatus(executeId, 1, "执行失败原因:[" + e.getMessage() + "]");
            return false;
        }
    }
}
