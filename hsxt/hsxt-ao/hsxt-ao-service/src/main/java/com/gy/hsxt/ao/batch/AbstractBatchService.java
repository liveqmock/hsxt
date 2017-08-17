/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.ao.batch;

import com.gy.hsi.ds.api.IDSBatchService;
import com.gy.hsi.ds.api.IDSBatchServiceListener;
import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.ao.disconf.AoConfig;
import com.gy.hsxt.ao.file.thread.ThreadPoolTaskExecutorSupport;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.*;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 调度接口抽象实现
 *
 * @Package : com.gy.hsxt.ao.batch
 * @ClassName : AbstractBatchService
 * @Description : 调度接口抽象实现
 * @Author : chenm
 * @Date : 2016/2/25 10:59
 * @Version V3.0.0.0
 */
public abstract class AbstractBatchService implements IDSBatchService, ApplicationContextAware {

    /**
     * 日志
     */
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * spring上下文
     */
    private ApplicationContext applicationContext;

    /**
     * 业务服务私有配置参数
     **/
    @Resource
    private AoConfig aoConfig;


    /**
     * 回调监听类
     */
    @Resource
    private IDSBatchServiceListener batchServiceListener;

    /**
     * 线程池支持中心
     */
    @Resource
    private ThreadPoolTaskExecutorSupport threadPoolTaskExecutorSupport;

    /**
     * 扫描日期
     */
    public static final String SCAN_DATE = "scan_date";

    /**
     * 调度执行方法
     *
     * @param executeId 任务执行编号
     * @param param     参数
     * @return {@code boolean}
     * @see com.gy.hsi.ds.api.IDSBatchService#excuteBatch(String, Map)
     */
    @Override
    public boolean excuteBatch(String executeId, Map<String, String> param) {
        try {
            // 发送进度通知
            batchServiceListener.reportStatus(executeId, 2, "执行中");

            //扫描日期 默认取昨天
            String scanDate = param == null || StringUtils.isBlank(param.get(SCAN_DATE)) ? AoConfig.getYesterday() : param.get(SCAN_DATE);

            SystemLog.debug(aoConfig.getSysName(), "function:executeBatch", "========= [" + this.getClass().getSimpleName() + "][" + scanDate + "]调度开始 =========");
            // 执行任务
            execute(executeId, scanDate);

            SystemLog.debug(aoConfig.getSysName(), "function:executeBatch", "========= [" + this.getClass().getSimpleName() + "][" + scanDate + "]调度结束 =========");

            return true;
        } catch (Exception e) {
            // 写入系统日志
            SystemLog.error(aoConfig.getSysName(), "function:executeBatch", "[" + this.getClass().getSimpleName() + "]调度发生错误", e);
            batchServiceListener.reportStatus(executeId, 1, "执行失败原因:[" + e.getMessage() + "]");
            return false;
        }
    }

    /**
     * 业务执行方法
     *
     * @param executeId 任务执行编号
     * @param scanDate  扫描日期
     * @throws Exception
     */
    public abstract void execute(String executeId, String scanDate) throws Exception;

    /**
     * Set the ApplicationContext that this object runs in.
     * Normally this call will be used to initialize the object.
     * <p>Invoked after population of normal bean properties but before an init callback such
     * as {@link InitializingBean#afterPropertiesSet()}
     * or a custom init-method. Invoked after {@link ResourceLoaderAware#setResourceLoader},
     * {@link ApplicationEventPublisherAware#setApplicationEventPublisher} and
     * {@link MessageSourceAware}, if applicable.
     *
     * @param applicationContext the ApplicationContext object to be used by this object
     * @throws ApplicationContextException in case of context initialization errors
     * @throws BeansException              if thrown by application context methods
     * @see BeanInitializationException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public Logger getLogger() {
        return logger;
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public IDSBatchServiceListener getBatchServiceListener() {
        return batchServiceListener;
    }

    public AoConfig getAoConfig() {
        return aoConfig;
    }

    public ThreadPoolTaskExecutorSupport getThreadPoolTaskExecutorSupport() {
        return threadPoolTaskExecutorSupport;
    }
}
