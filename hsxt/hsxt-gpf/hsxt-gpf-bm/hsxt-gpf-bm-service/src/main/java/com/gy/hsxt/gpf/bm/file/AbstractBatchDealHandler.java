/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.gpf.bm.file;

import com.gy.hsxt.gpf.bm.bean.FutureBean;
import com.gy.hsxt.gpf.bm.bean.NfsBean;
import com.gy.hsxt.gpf.bm.bean.PlatData;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

/**
 * 抽象的批处理器
 *
 * @Package :com.gy.hsxt.gpf.bm.file
 * @ClassName : AbstractBatchDealHandler
 * @Description : 抽象的批处理器
 * @Author : chenm
 * @Date : 2015/10/14 16:35
 * @Version V3.0.0.0
 */
public abstract class AbstractBatchDealHandler<T> {

    /**
     * 平台代码类
     */
    protected PlatData platData;

    /**
     * nfs信息类
     */
    protected NfsBean nfsBean;

    /**
     * 工作线程池
     */
    protected ThreadPoolTaskExecutor jobExecutor;

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
    protected List<Future<FutureBean<T>>> futures = new ArrayList<>();

    /**
     * 初始化数据
     */
    protected void initData() {
        futures.clear();
        setStartCount(0);
        setEndCount(0);
    }

    /**
     * 文件是否全部下载完成
     *
     * @return {@code boolean}
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

    public void setPlatData(PlatData platData) {
        this.platData = platData;
    }

    public void setNfsBean(NfsBean nfsBean) {
        this.nfsBean = nfsBean;
    }

    public void setJobExecutor(ThreadPoolTaskExecutor jobExecutor) {
        this.jobExecutor = jobExecutor;
    }

}
