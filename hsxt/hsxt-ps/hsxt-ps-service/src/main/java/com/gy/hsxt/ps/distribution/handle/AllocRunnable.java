/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.ps.distribution.handle;

import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.ps.common.PsException;
import com.gy.hsxt.ps.distribution.service.RunnableService;
import com.gy.hsxt.ps.points.bean.PointDetail;

import java.util.List;
import java.util.concurrent.Callable;

/**
 * @version V3.0
 * @Package: com.gy.hsxt.ps.distribution.handle
 * @ClassName: AllocRunnable
 * @Description: 积分分配线程池处理
 * @author: chenhongzhi
 * @date: 2015-12-8 上午9:20:21
 */

public class AllocRunnable implements Callable<Boolean> {

    /**
     * 批处理服务
     **/
    private RunnableService batchService;

    /**
     * list存储数据对象
     **/
    private List<PointDetail> list;

    private String runDate;

    /**
     * 赋值
     *
     * @param batchService 批处理服务
     * @param list         积分明细信息
     */
    public AllocRunnable(RunnableService batchService, List<PointDetail> list, String runDate) {
        this.batchService = batchService;
        this.list = list;
        this.runDate = runDate;
    }

    /**
     * 启动线程
     */
    @Override
    public Boolean call() throws HsException {
        try {
            // 调用积分分配线程方法
            batchService.batDispose(list, runDate);
        } catch (HsException e) {
            PsException.psThrowException(new Throwable().getStackTrace()[0], RespCode.PS_THREAD_ERROR.getCode(), "批处理任务异常",e);
            return false;
        }
        return true;
    }

}
