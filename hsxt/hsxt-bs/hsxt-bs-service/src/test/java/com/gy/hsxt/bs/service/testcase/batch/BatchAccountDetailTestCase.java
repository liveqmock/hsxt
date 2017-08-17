/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO.,
 * LTD. All rights reserved.
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.bs.service.testcase.batch;

import javax.annotation.Resource;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.gy.hsxt.bs.batch.BatchExportAcctDetailTxtFile;

/**
 * 记帐批处理 生成文档
 * 
 * @Package: com.gy.hsxt.bs.service.testcase.batch
 * @ClassName: BatchAccountDetail
 * @Description:
 * 
 * @author: liuhq
 * @date: 2015-10-23 下午6:15:42
 * @version V1.0
 */
@FixMethodOrder(MethodSorters.DEFAULT)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/spring-global.xml")
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
@Transactional
public class BatchAccountDetailTestCase {

    @Resource
    BatchExportAcctDetailTxtFile batchExportAcctDetailTxtFile;

    /**
     * 读取数据生成文档
     */
    @Test
    public void exportAcctDetaiTxtFile() {
        try
        {
            System.out.println("操作成功！");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

}
