/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.tm.test;

import java.util.Random;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.test.context.ContextConfiguration;

import com.gy.hsxt.common.utils.DateUtil;
import com.gy.hsxt.tm.api.ITMSyncTaskService;
import com.gy.hsxt.tm.common.SpringContextUtils;

@FixMethodOrder(MethodSorters.DEFAULT)
@RunWith(JUnit4CRunner.class)
// @RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/spring-global.xml")
// @TransactionConfiguration(transactionManager = "transactionManager",
// defaultRollback = false)
// @Transactional
public class BaseTest {

    String hsResNo = "";

    String custId = "";

    @Before
    public void setUp() throws Exception {
        hsResNo = "0" + new Random().nextInt(9) + "" + new Random().nextInt(9) + "" + new Random().nextInt(9) + ""
                + new Random().nextInt(9) + "0" + new Random().nextInt(9) + "" + new Random().nextInt(9) + ""
                + new Random().nextInt(9) + "" + new Random().nextInt(9) + "" + new Random().nextInt(9);

    }

    public String getHsResNo() {
        return hsResNo;
    }

    public void setHsResNo(String hsResNo) {
        this.hsResNo = hsResNo;
    }

    public String getCustId() {
        return getHsResNo() + DateUtil.getCurrentDateNoSign();
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public static void main(String[] args) throws Exception {
        // ClassPathXmlApplicationContext ac = new
        // ClassPathXmlApplicationContext("classpath:spring/spring-global.xml");
        String beanName = "";
        int taskSrc = 1;
        // 业务服务Bean
        if (taskSrc == 1)
        {
            beanName = "taskSync_1";
        }
        else
        // 积分福利Bean
        if (taskSrc == 2)
        {
            beanName = "taskSync_2";
        }
        if (SpringContextUtils.containsBean(beanName))
        {
            System.out.println("----------------true");
        }
        else
        {
            System.out.println("--------------------false");
        }

        // ITMSyncTaskService taskService = (ITMSyncTaskService)
        // ac.getBean(beanName);
        ITMSyncTaskService taskService = (ITMSyncTaskService) SpringContextUtils.getBean(beanName);
        taskService.updateSrcTaskExecutor("888", "张三疯", "张三疯", "a");

    }
}
