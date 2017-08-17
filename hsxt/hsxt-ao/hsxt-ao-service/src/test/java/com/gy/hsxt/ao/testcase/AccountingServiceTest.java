/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.ao.testcase;

import javax.annotation.Resource;

import org.junit.Test;

import com.gy.hsxt.ao.interfaces.IAccountingService;

/**
 * 记账分解接口测试用例
 * 
 * @Package: com.gy.hsxt.ao.testcase
 * @ClassName: AccountingServiceTest
 * @Description: TODO
 * 
 * @author: liyh
 * @date: 2015-12-15 下午12:16:24
 * @version V1.0
 */
public class AccountingServiceTest extends BaseTest {

    /**
     * 记帐分解接口
     */
    @Resource
    IAccountingService accountingService;

    /**
     * 记账分解数据老化
     */
    @Test
    public void accountingDataTransfer() {
        // accountingService.accountingDataTransfer();
    }

    /**
     * 日终生成记账对账文件测试用例
     */
    @Test
    public void accountingCheckFileGenerate() {
        // accountingService.accountingCheckFileGenerate();
    }

}
