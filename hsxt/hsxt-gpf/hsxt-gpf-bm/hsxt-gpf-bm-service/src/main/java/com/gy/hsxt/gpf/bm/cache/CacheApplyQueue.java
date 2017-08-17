package com.gy.hsxt.gpf.bm.cache;

import com.gy.hsxt.gpf.bm.bean.ApplyRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentLinkedQueue;


/**
 * 新增缓存申报队列
 *
 * @Package :com.gy.hsxt.gpf.bm.cache
 * @ClassName : CacheApplyQueue
 * @Description : 新增缓存申报队列
 * @Author : chenm
 * @Date : 2015/9/30 10:27
 * @Version V3.0.0.0
 */
public abstract class CacheApplyQueue {

    /**
     * 日志
     */
    private static final Logger logger = LoggerFactory.getLogger(CacheApplyQueue.class);

    /**
     * 申报列队
     */
    private static ConcurrentLinkedQueue<ApplyRecord> cacheQueue = new ConcurrentLinkedQueue<ApplyRecord>();

    /**
     * 添加申报记录
     *
     * @param applyRecord 申报记录
     * @return boolean
     */
    public static boolean add(ApplyRecord applyRecord) {
        logger.info("========列队中添加申报记录[{}]======", applyRecord);
        return cacheQueue.add(applyRecord);
    }

    /**
     * 按照先进先出的方式获取申报记录
     *
     * @return bean 申报记录
     */
    public static ApplyRecord poll() {
        return cacheQueue.poll();
    }


    /**
     * 判断列队中是否存在某申报记录
     * @param applyRecord 申报记录
     * @return boolean
     */
    public static boolean nonExist(ApplyRecord applyRecord) {
        for (ApplyRecord record : cacheQueue) {
            if (record.getPopCustId().equals(applyRecord.getPopCustId())) {
                logger.info("========列队中存在[{}]申报记录======", applyRecord.getPopCustId());
                return false;
            }
        }
        return true;
    }
}

	