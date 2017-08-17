/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.ao.testcase;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.gy.hsxt.ao.api.IAOBatchCheckResultService;
import com.gy.hsxt.ao.bean.BatchSettle;
import com.gy.hsxt.ao.bean.BatchUpload;
import com.gy.hsxt.ao.disconf.AoConfig;
import com.gy.hsxt.ao.service.BatchCheckResultService;

/**
 * 
 * 终端设备业务数据批结算
 * 
 * @Package: com.gy.hsxt.ao.testcase
 * @ClassName: BatchCheckResultServiceTest
 * @Description: TODO
 * 
 * @author: liyh
 * @date: 2015-12-17 下午2:07:35
 * @version V1.0
 */
public class BatchCheckResultServiceTest extends BaseTest {
    /** 业务服务私有配置参数 **/
    @Autowired
    private AoConfig aoConfig;

    /**
     * 自动对账接口
     */
    @Resource
    IAOBatchCheckResultService batchCheckResultService;

    @Resource
    BatchCheckResultService batchCheckResultServices;

    /**
     * 终端设备业务数据批结算测试用例
     */
    @Test
    public void testBatchCheckResult() {
        BatchSettle batchSettle = new BatchSettle();
        batchSettle.setEntCustId("06002110000164063559693312");
        batchSettle.setEntResNo("06002110000");
        batchSettle.setChannel(1);
        batchSettle.setTermNo("15615042110000000460");
        batchSettle.setBatchNo("02180955");
        boolean isBatch = batchCheckResultService.batchCheckResult(batchSettle, "0", "0", "258", "1");
        System.out.println(isBatch ? "结算通过" : "结算未通过");
    }

    /**
     * 终端设备业务数据批上传
     */
    @Test
    public void testBatchTerminalDataUpload() {
        BatchSettle batchSettle = new BatchSettle();
        batchSettle.setEntCustId("06002110000164063559693312");
        batchSettle.setEntResNo("06002110000");
        batchSettle.setChannel(1);
        batchSettle.setTermNo("15615042110000000460");
        batchSettle.setBatchNo("000072");

        List<BatchUpload> list = new ArrayList<BatchUpload>();
        BatchUpload batchUpload1 = new BatchUpload();
        // batchUpload1.setBizType("0070");
        batchUpload1.setTransAmt("500.0");
        // batchUpload1.setOriginNo("085350151820");
        batchUpload1.setTermRuncode("15615042110000000460160217141931");
        batchUpload1.setPerResNo("06002111711");
        list.add(batchUpload1);
        batchCheckResultService.batchTerminalDataUpload(batchSettle, list);
    }

    /**
     * 终端设备批结算数据老化
     */
    @Test
    public void batchDataTransfer() {
        batchCheckResultServices.batchDataTransfer();
    }

}
