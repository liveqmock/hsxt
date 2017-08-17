/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.tm.test;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.gy.hsxt.tm.batch.AutoUrgencyTaskService;

public class AutoDispatchTest extends BaseTest {

    @Autowired
    AutoUrgencyTaskService autoUrgencyTaskService;

    @Test
    public void test() {
        autoUrgencyTaskService.excuteBatch(null, null);
    }

}
