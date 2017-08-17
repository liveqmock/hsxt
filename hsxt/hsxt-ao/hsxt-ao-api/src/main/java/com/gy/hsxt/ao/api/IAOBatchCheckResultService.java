/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.ao.api;

import java.util.List;

import com.gy.hsxt.ao.bean.BatchSettle;
import com.gy.hsxt.ao.bean.BatchUpload;
import com.gy.hsxt.common.exception.HsException;

/**
 * 自动对账
 * 
 * @Package: com.gy.hsxt.ao.service
 * @ClassName: BatchCheckResultService
 * @Description: TODO
 * 
 * @author: liyh
 * @date: 2015-12-3 下午3:06:24
 * @version V1.0
 */
public interface IAOBatchCheckResultService {

    /**
     * 终端设备业务数据批结算
     * 
     * @param batchSettle
     *            结算批次信息包含：（企业客户号、企业互生号、批次编号、终端渠道、终端编号）
     * @param buyHsbAmt
     *            兑换互生币总金额
     * @param hsbCount
     *            兑换互生币总笔数
     * @param proxyBuyHsbAmt
     *            代兑互生币总金额
     * @param proxyHsbCount
     *            代兑互生币总笔数
     * @return
     */
    public Boolean batchCheckResult(BatchSettle batchSettle, String buyHsbAmt, String hsbCount, String proxyBuyHsbAmt,
            String proxyHsbCount) throws HsException;

    /**
     * 终端设备业务数据批上传
     * 
     * @param batchSettle
     *            结算批次信息包含：（企业客户号、企业互生号、批次编号、终端渠道、终端编号）
     * @param listBatchUpload
     *            List<业务明细>
     *            　　业务明细包含：（交易类型码(兑换、代兑等)、原POS中心流水号、原始终端流水号、交易金额、消费者互生号）
     */
    public void batchTerminalDataUpload(BatchSettle batchSettle, List<BatchUpload> listBatchUpload) throws HsException;

}
