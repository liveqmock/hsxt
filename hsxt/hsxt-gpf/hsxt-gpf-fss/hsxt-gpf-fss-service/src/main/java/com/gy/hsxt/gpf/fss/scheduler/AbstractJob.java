/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.gpf.fss.scheduler;

import com.gy.hsxt.gpf.fss.service.CallbackService;
import com.gy.hsxt.gpf.fss.service.FileDetailService;
import com.gy.hsxt.gpf.fss.service.LocalNotifyService;
import com.gy.hsxt.gpf.fss.service.RemoteNotifyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.Resource;

/**
 * 定时任务的抽象类
 *
 * @Package :com.gy.hsxt.gpf.fss.scheduler.job
 * @ClassName : AbstractJob
 * @Description : TODO
 * @Author : chenm
 * @Date : 2015/11/2 18:51
 * @Version V3.0.0.0
 */
public class AbstractJob {

    /**
     * 日志
     */
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 回调业务层接口
     */
    @Resource
    protected CallbackService callbackService;

    /**
     * 本地通知业务层接口
     */
    @Resource
    protected LocalNotifyService localNotifyService;

    /**
     * 远程通知业务层接口
     */
    @Resource
    protected RemoteNotifyService remoteNotifyService;

    /**
     * 文件详情业务层接口
     */
    @Resource
    protected FileDetailService fileDetailService;

    /**
     * 本平台代码
     */
    @Value("${plat.code}")
    protected String platCode;

}
