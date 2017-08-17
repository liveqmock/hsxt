/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.bs.file.callable;

import com.gy.hsxt.bs.file.bean.CheckInfo;
import com.gy.hsxt.bs.file.bean.DataInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import java.util.concurrent.Callable;

/**
 * @Package : com.gy.hsxt.bs.file.callable
 * @ClassName : DataFileCallable
 * @Description : TODO
 * @Author : chenm
 * @Date : 2016/2/24 14:26
 * @Version V3.0.0.0
 */
public abstract class DataFileCallable<T> implements Callable<DataInfo<T>> {

    /**
     * 日志
     */
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * spring上下文
     */
    private ApplicationContext applicationContext;

    /**
     * 执行编号
     */
    private String executeId;

    /**
     * 文件信息
     */
    private DataInfo<T> dataInfo;

    /**
     * 校验文件信息
     */
    private CheckInfo checkInfo;

    /**
     * 带参构造函数
     *
     * @param applicationContext 上下文
     * @param executeId          执行编号
     * @param dataInfo           文件信息
     * @param checkInfo          校验文件信息
     */
    protected DataFileCallable(ApplicationContext applicationContext, String executeId, DataInfo<T> dataInfo, CheckInfo checkInfo) {
        this.applicationContext = applicationContext;
        this.executeId = executeId;
        this.dataInfo = dataInfo;
        this.checkInfo = checkInfo;
    }

    public Logger getLogger() {
        return logger;
    }

    public DataInfo<T> getDataInfo() {
        return dataInfo;
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public String getExecuteId() {
        return executeId;
    }

    public CheckInfo getCheckInfo() {
        return checkInfo;
    }
}
