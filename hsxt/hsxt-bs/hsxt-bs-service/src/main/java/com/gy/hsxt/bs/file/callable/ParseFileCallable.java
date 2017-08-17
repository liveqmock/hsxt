/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.bs.file.callable;

import com.gy.hsxt.bs.file.bean.ParseInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import java.util.concurrent.Callable;

/**
 * 文件解析调度
 *
 * @Package : com.gy.hsxt.bs.file.callable
 * @ClassName : ParseFileCallable
 * @Description : 文件解析调度
 * @Author : chenm
 * @Date : 2016/2/25 11:25
 * @Version V3.0.0.0
 */
public abstract class ParseFileCallable implements Callable<ParseInfo> {

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
     * 解析文件信息
     */
    private ParseInfo parseInfo;

    /**
     * 带参构造函数
     * @param applicationContext 上下文
     * @param executeId 执行编号
     * @param parseInfo 解析文件信息
     */
    protected ParseFileCallable(ApplicationContext applicationContext, String executeId, ParseInfo parseInfo) {
        this.applicationContext = applicationContext;
        this.executeId = executeId;
        this.parseInfo = parseInfo;
    }

    public Logger getLogger() {
        return logger;
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public ParseInfo getParseInfo() {
        return parseInfo;
    }

    public String getExecuteId() {
        return executeId;
    }
}
