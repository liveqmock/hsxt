/**
 * adx-common#com.gy.hsi.ds.param.ub.log.AopLogFactory.java
 * 下午7:49:23 created by Darwin(Tianxin)
 */
package com.gy.hsi.ds.common.thirds.ub.common.log;

import org.apache.log4j.Logger;

/**
 * 切面的LogFactory
 *
 * @author Darwin(Tianxin)
 */
public class AopLogFactory {

    /**
     * 获取一个日志记录实例
     *
     * @param clazz
     *
     * @return 下午7:49:35 created by Darwin(Tianxin)
     */
    public static Logger getLogger(Class<?> clazz) {
        return Logger.getLogger(clazz);
    }
}
