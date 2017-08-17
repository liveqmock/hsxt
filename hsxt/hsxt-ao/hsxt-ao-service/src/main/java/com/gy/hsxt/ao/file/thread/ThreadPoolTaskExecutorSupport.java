/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.ao.file.thread;

import com.google.common.util.concurrent.ListenableFutureTask;
import com.gy.hsxt.ao.file.bean.DataInfo;
import com.gy.hsxt.ao.file.bean.ParseInfo;
import com.gy.hsxt.ao.file.callable.CheckFileCallable;
import com.gy.hsxt.ao.file.callable.DataFileCallable;
import com.gy.hsxt.ao.file.callable.ParseFileCallable;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.FutureTask;

/**
 * 线程池支持中心
 *
 * @Package : com.gy.hsxt.bs.thread
 * @ClassName : ThreadPoolTaskExecutorSupport
 * @Description : 线程池支持中心
 * @Author : chenm
 * @Date : 2016/2/24 14:16
 * @Version V3.0.0.0
 */
@Service
public class ThreadPoolTaskExecutorSupport {

    /**
     * 线程池
     */
    @Resource
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;


    /**
     * 提交线程调用
     *
     * @param checkFileCallable check调度
     * @param dataFileCallable  数据文件callable
     * @param <T>               泛型
     * @return {@link FutureTask}
     * @throws Exception
     */
    public <T> FutureTask<DataInfo<T>> submit(CheckFileCallable checkFileCallable, DataFileCallable<T> dataFileCallable) throws Exception {

        ListenableFutureTask<DataInfo<T>> futureTask = ListenableFutureTask.create(dataFileCallable);

        futureTask.addListener(checkFileCallable, threadPoolTaskExecutor);

        threadPoolTaskExecutor.execute(futureTask);

        return futureTask;

    }

    /**
     * 只运行check线程
     *
     * @param checkFileCallable check调度
     * @throws Exception
     */
    public void execute(CheckFileCallable checkFileCallable) throws Exception {
        threadPoolTaskExecutor.execute(checkFileCallable);
    }

    /**
     * 文件解析线程执行方法
     *
     * @param parseFileCallable parse文件调用
     * @return {@link FutureTask}
     * @throws Exception
     */
    public FutureTask<ParseInfo> submit(ParseFileCallable parseFileCallable) throws Exception {

        ListenableFutureTask<ParseInfo> futureTask = ListenableFutureTask.create(parseFileCallable);

        threadPoolTaskExecutor.execute(futureTask);

        return futureTask;
    }
}
