/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.gpf.bm.job.runner;

import java.util.concurrent.Callable;

/**
 * 文件数据处理接口类
 *
 * @Package :com.gy.hsxt.gpf.bm.job.runner
 * @ClassName : FileRunner
 * @Description : TODO
 * @Author : chenm
 * @Date : 2015/10/14 15:51
 * @Version V3.0.0.0
 */
public interface FileRunner<T> extends Callable<T> {

    /**
     * 默认字符集
     */
    String DEFAULT_CHARSET = "UTF-8";

    /**
     *
     */
    int DEFAULT_MAX_COUNT = 5;
}
