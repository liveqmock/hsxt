/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.bs.file.callable;

import com.gy.hsi.ds.api.IDSBatchServiceListener;
import com.gy.hsxt.bs.disconf.FileConfig;
import com.gy.hsxt.bs.file.bean.CheckInfo;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.util.Assert;

import java.io.File;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * check文件生成线程
 *
 * @Package : com.gy.hsxt.bs.file.callable
 * @ClassName : CheckFileCallable
 * @Description : check文件生成线程
 * @Author : chenm
 * @Date : 2016/2/24 14:27
 * @Version V3.0.0.0
 */
public class CheckFileCallable implements Runnable {

    /**
     * 日志
     */
    private Logger logger = LoggerFactory.getLogger(CheckFileCallable.class);

    /**
     * spring上下文
     */
    private ApplicationContext applicationContext;

    /**
     * 执行编号
     */
    private String executeId;

    /**
     * check文件信息
     */
    private CheckInfo checkInfo;

    /**
     * 私有构造函数
     *
     * @param applicationContext 上下文
     * @param executeId          执行编号
     * @param checkInfo          check文件信息
     */
    private CheckFileCallable(ApplicationContext applicationContext, String executeId, CheckInfo checkInfo) {
        this.applicationContext = applicationContext;
        this.executeId = executeId;
        this.checkInfo = checkInfo;
    }

    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p/>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        try {
            AtomicInteger atomic = this.checkInfo.getAtomicInteger();
            int value = 0;
            if (checkInfo.getCheckNum() > 0) {
                final int andIncrement = atomic.getAndIncrement();// 乐观自增
                value = andIncrement +1;
            }
            logger.debug("========atomic:{} || checkNum:{}=======", value, checkInfo.getCheckNum());
            if (value == this.checkInfo.getCheckNum()) {

                File file = new File(checkInfo.getDirPath() + checkInfo.getFileName());
                if (checkInfo.getCheckNum() != null&&this.checkInfo.isNeed()) {
                    checkInfo.getFileInfos().add(0, checkInfo.getCheckNum() + IOUtils.LINE_SEPARATOR);
                }
                checkInfo.getFileInfos().add(FileConfig.DATA_END);// 文件结束标识

                // 调用生成check文档方法
                FileUtils.writeLines(file, FileConfig.DEFAULT_CHARSET, checkInfo.getFileInfos(), "");
                logger.debug("========生成名称为[{}]的check文件成功=======", checkInfo.getFileName());
                //check文件生成之后，报告成功
                IDSBatchServiceListener batchServiceListener = applicationContext.getBean(IDSBatchServiceListener.class);
                batchServiceListener.reportStatus(executeId, 0, "执行成功");
            }
        } catch (Exception e) {
            logger.error("========生成名称为[{}]的check文件失败=======", checkInfo.getFileName(), e);
            IDSBatchServiceListener batchServiceListener = applicationContext.getBean(IDSBatchServiceListener.class);
            batchServiceListener.reportStatus(executeId, 1, "生成名称为[" + checkInfo.getFileName() + "]的check文件失败，原因:[" + e.getMessage() + "]");
        }
    }

    /**
     * 构建函数
     *
     * @param executeId 执行编号
     * @param checkInfo check文件信息
     * @return {@code CheckFileCallable}
     */
    public static CheckFileCallable bulid(ApplicationContext applicationContext, String executeId, CheckInfo checkInfo) {
        Assert.notNull(applicationContext, "the applicationContext bean must not be null !");
        Assert.hasText(executeId, "the value of executeId must not be null or empty !");
        Assert.notNull(checkInfo, "the checkInfo bean must not be null !");
        return new CheckFileCallable(applicationContext, executeId, checkInfo);
    }
}
