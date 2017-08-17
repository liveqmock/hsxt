/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.gpf.bm.file;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.gpf.bm.bean.NfsBean;
import com.gy.hsxt.gpf.bm.bean.PlatData;
import com.gy.hsxt.gpf.bm.common.BMRespCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 批处理文件工厂类
 *
 * @Package :com.gy.hsxt.gpf.bm.file
 * @ClassName : BatchDealFileFactory
 * @Description : TODO
 * @Author : chenm
 * @Date : 2015/10/22 20:24
 * @Version V3.0.0.0
 */
@Service
public class BatchDealFileFactory {

    private Logger logger = LoggerFactory.getLogger(BatchDealFileFactory.class);

    /**
     * 平台代码工具类
     */
    @Resource
    private PlatData platData;

    /**
     * nfs信息类
     */
    @Resource
    private NfsBean nfsBean;

    /**
     * 工作线程池
     */
    @Resource(name = "jobExecutor")
    private ThreadPoolTaskExecutor jobExecutor;

    /**
     * 构建处理器实例
     *
     * @param clazz
     * @param <T>
     * @return
     * @throws Exception
     */
    public <T extends AbstractBatchDealHandler> T buildDealHandler(Class<T> clazz) throws HsException {
        T instance = null;
        try {
            instance = clazz.newInstance();
            instance.setPlatData(platData);
            instance.setNfsBean(nfsBean);
            instance.setJobExecutor(jobExecutor);
        } catch (InstantiationException | IllegalAccessException e) {
            logger.error("===========构建批文件处理器异常===========", e);
            throw new HsException(BMRespCode.BM_SYSTEM_ERROR.getCode(), "构建批文件处理器异常");
        }
        return instance;
    }

    /**
     * 启动一个线程
     * @param runnable
     */
    public void execute(Runnable runnable) {
        jobExecutor.execute(runnable);
    }
}
