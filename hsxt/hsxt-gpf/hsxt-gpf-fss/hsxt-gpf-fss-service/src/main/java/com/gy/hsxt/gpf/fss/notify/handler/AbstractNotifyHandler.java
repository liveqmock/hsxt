/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.gpf.fss.notify.handler;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.gpf.fss.bean.FileDetail;
import com.gy.hsxt.gpf.fss.notify.future.FutureBean;
import com.gy.hsxt.gpf.fss.service.FileDetailService;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

/**
 * @Package :com.gy.hsxt.gpf.fss.notify.handler
 * @ClassName : AbstractNotifyHandler
 * @Description : TODO
 * @Author : chenm
 * @Date : 2015/10/23 16:24
 * @Version V3.0.0.0
 */
public abstract class AbstractNotifyHandler<T> implements NotifyHandler {

    /**
     * 下一个处理器
     */
    private NotifyHandler next;

    /**
     * 线程池
     */
    protected ThreadPoolTaskExecutor jobExecutor;

    public AbstractNotifyHandler(ThreadPoolTaskExecutor jobExecutor) {
        this.jobExecutor = jobExecutor;
    }

    /**
     * 开启线程数
     */
    protected int startCount;

    /**
     * 结束线程数
     */
    protected int endCount;

    /**
     * 线程返回结果特征列表
     */
    protected List<Future<FutureBean<T>>> futures = new ArrayList<Future<FutureBean<T>>>();


    /**
     * 处理方法
     *
     * @param details
     * @throws HsException
     */
    @Override
    public boolean handler(List<FileDetail> details,FileDetailService fileDetailService) throws HsException {
        return doDeal(details,fileDetailService) && nextHandler(details,fileDetailService);
    }

    /**
     * 实际的业务处理逻辑
     *
     * @param details
     * @param fileDetailService
     * @return
     * @throws HsException
     */
    protected abstract boolean doDeal(List<FileDetail> details,FileDetailService fileDetailService) throws HsException;


    /**
     * 执行下一个handler
     *
     * @param details
     * @return
     */
    private boolean nextHandler(List<FileDetail> details,FileDetailService fileDetailService) {
        return next == null || next.handler(details,fileDetailService);
    }

    /**
     * 设置下一个处理器
     *
     * @param next
     */
    public NotifyHandler setNext(NotifyHandler next) {
        this.next = next;
        return this;
    }

    /**
     * 初始化数据
     */
    protected void initData() {
        futures.clear();
        setStartCount(0);
        setEndCount(0);
    }

    /**
     * 是否全部完成
     *
     * @return
     */
    public boolean isFinished() {
        if (startCount > 0) {
            do {
                setEndCount(0);
                for (Future<FutureBean<T>> future : futures) {
                    if (future.isDone()) {
                        endCount++;
                    }
                }
            } while (startCount != endCount);
        }
        return startCount > 0 && startCount == endCount;
    }

    public void setStartCount(int startCount) {
        this.startCount = startCount;
    }

    public void setEndCount(int endCount) {
        this.endCount = endCount;
    }

    public void setJobExecutor(ThreadPoolTaskExecutor jobExecutor) {
        this.jobExecutor = jobExecutor;
    }
}
