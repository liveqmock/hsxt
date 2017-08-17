/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.ao.testcase;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.gy.hsxt.ao.interfaces.IBatchAutoCommitService;

public class BatchAutoCommitServiceTest extends BaseTest {

    @Autowired
    IBatchAutoCommitService autoCommitService;

    @Test
    public void testExcuteBatch() {
//        autoCommitService.batchAutoTransCommit();
    }

}
