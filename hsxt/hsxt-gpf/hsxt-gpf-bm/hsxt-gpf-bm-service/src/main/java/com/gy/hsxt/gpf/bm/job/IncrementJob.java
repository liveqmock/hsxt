/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.gpf.bm.job;

import com.gy.hsxt.gpf.bm.bean.ApplyRecord;
import com.gy.hsxt.gpf.bm.cache.CacheApplyQueue;
import com.gy.hsxt.gpf.bm.service.IncrementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

/**
 * 添加申报增值节点任务
 *
 * @Package : com.gy.hsxt.gpf.bm.job
 * @ClassName : IncrementJob
 * @Description : 添加申报增值节点任务
 * @Author : chenm
 * @Date : 2016/1/9 16:54
 * @Version V3.0.0.0
 */
public class IncrementJob {

    /**
     * 日志
     */
    private Logger logger = LoggerFactory.getLogger(IncrementJob.class);

    /**
     *增值业务接口
     */
    @Resource
    private IncrementService incrementService;


    /**
     * 任务执行方法
     */
    public void execute() {

        ApplyRecord applyRecord = CacheApplyQueue.poll();

        if (null != applyRecord) {
            //开始时间
            long start = System.currentTimeMillis();
            logger.debug("=========添加[{}]的申报增值节点开始===========",applyRecord.getPopCustId());

            //添加增值节点
            boolean success = incrementService.addNode(applyRecord);

            //结束时间
            long end = System.currentTimeMillis();

            logger.debug("=========添加[{}]的申报增值节点结束；耗时[{}]毫秒；执行结果[{}]===========",applyRecord.getPopCustId(),(end-start),success);
        }


    }
}
