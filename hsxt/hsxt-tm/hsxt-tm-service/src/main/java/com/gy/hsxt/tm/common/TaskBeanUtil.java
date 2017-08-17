/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.tm.common;

import com.gy.hsxt.tm.api.ITMSyncTaskService;

/**
 * 获取任务来源bean工具类
 * 
 * @Package: com.gy.hsxt.tm.common
 * @ClassName: TaskBeanUtil
 * @Description: TODO
 * 
 * @author: kongsl
 * @date: 2015-11-18 下午7:22:43
 * @version V3.0.0
 */
public class TaskBeanUtil {

    /**
     * 获取目标bean
     * 
     * @param taskSrc
     *            任务来源
     * @return 同步服务的目标实现类
     */
    public static ITMSyncTaskService getTargetBean(int taskSrc) {
        // HsAssert.isNull(taskSrc <= 0, RespCode.BS_PARAMS_NULL,
        // "获取Bean时参数为空taskSrc = " + taskSrc);
        StringBuilder beanName = new StringBuilder();
        beanName.append("taskSync_");
        // 业务服务Bean
        if (taskSrc == 1)
        {
            beanName.append("1");
        }
        else
        // 积分福利Bean
        if (taskSrc == 2)
        {
            beanName.append("2");
        }
        else if (taskSrc == 3)
        {
            beanName.append("3");
        }
        else if (taskSrc == 4)
        {
            beanName.append("4");
        }
        else if (taskSrc == 5)
        {
            beanName.append("5");
        }
        else if (taskSrc == 6)
        {
            beanName.append("6");
        }
        else if (taskSrc == 7)
        {
            beanName.append("7");
        }
        else if (taskSrc == 8)
        {
            beanName.append("8");
        }
        return (ITMSyncTaskService) SpringContextUtils.getBean(beanName.toString());
    }
}
