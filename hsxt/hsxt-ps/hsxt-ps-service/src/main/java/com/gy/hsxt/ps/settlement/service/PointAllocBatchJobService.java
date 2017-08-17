/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.ps.settlement.service;

import com.gy.hsi.ds.api.IDSBatchService;
import com.gy.hsi.ds.api.IDSBatchServiceListener;
import com.gy.hsi.ds.common.contants.DSContants.DSTaskStatus;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.utils.DateUtil;
import com.gy.hsxt.ps.common.PsException;
import com.gy.hsxt.ps.distribution.service.PointAllocService;
import com.gy.hsxt.ps.settlement.handle.PosSettleHandle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @version V3.0
 * @Package: com.gy.hsxt.es.distribution.service
 * @ClassName: DayEndStatement
 * @Description: 定时任务实现类
 * @author: chenhongzhi
 * @date: 2015-11-18 下午6:01:34
 */
@Service
public class PointAllocBatchJobService implements IDSBatchService {

    private Logger logger = LoggerFactory.getLogger(PointAllocBatchJobService.class);

    // 回调监听类
    @Autowired
    private IDSBatchServiceListener listener;

    // 积分分配服务(积分、税收)
    @Autowired
    private PointAllocService pointAllocService;

    /**
     * @param args
     * @return
     */
    @Override
    public boolean excuteBatch(String executeId, Map<String, String> args) {

        logger.debug("消费积分分配开始执行！" + DateUtil.now());
            // 发送进度通知
            listener.reportStatus(executeId, DSTaskStatus.HANDLING, "执行中");
            StringBuffer stringBuffer=null;
            try {
                // 积分分配
                 stringBuffer=pointAllocService.batAllocPoint(PosSettleHandle.getJobDate(args));
            } catch (Exception e) {
                // 发送进度通知
                listener.reportStatus(executeId, DSTaskStatus.FAILED, e.toString());
                // 抛出 异常
                PsException.psThrowException(new Throwable().getStackTrace()[0],
                        RespCode.PS_POINT_ALLOC_BATCH_JOB_ERROR.getCode(), e.getMessage(),e);
            }
            // 发送进度通知
            listener.reportStatus(executeId, DSTaskStatus.SUCCESS, "积分分配执行成功"+stringBuffer);
            logger.debug("消费积分分配执行成功！" + DateUtil.now());
        return true;
    }

}
