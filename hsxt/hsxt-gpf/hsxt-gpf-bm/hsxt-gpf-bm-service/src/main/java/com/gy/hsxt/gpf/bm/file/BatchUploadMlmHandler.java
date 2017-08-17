/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.gpf.bm.file;

import com.gy.hsxt.gpf.bm.bean.DetailResult;
import com.gy.hsxt.gpf.bm.bean.FutureBean;
import com.gy.hsxt.gpf.bm.bean.PointValue;
import com.gy.hsxt.gpf.bm.job.runner.handler.MlmDataToFileHandler;
import com.gy.hsxt.gpf.fss.bean.FileDetail;
import com.gy.hsxt.gpf.fss.bean.LocalNotify;
import com.gy.hsxt.gpf.fss.constant.FssNotifyType;
import com.gy.hsxt.gpf.fss.constant.FssPurpose;
import com.gy.hsxt.gpf.fss.utils.FssDateUtil;
import com.gy.hsxt.gpf.fss.utils.NotifyNoUtil;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * 增值积分上传批处理器
 *
 * @Package :com.gy.hsxt.gpf.bm.file
 * @ClassName : BatchUploadMlmFileHandler
 * @Description : TODO
 * @Author : chenm
 * @Date : 2015/10/15 11:11
 * @Version V3.0.0.0
 */
public class BatchUploadMlmHandler extends AbstractBatchDealHandler<LocalNotify> {

    private Logger logger = LoggerFactory.getLogger(BatchUploadMlmHandler.class);

    /**
     * 上传增值积分统计数据到业务系统
     *
     * @param platMap 各企业增值积分汇总
     * @return List<LocalNotify>
     */
    public List<LocalNotify> uploadMlmFile(Map<String, Map<String, DetailResult<PointValue>>> platMap) {
        initData();
        List<LocalNotify> notifies = new ArrayList<>();
        for (Map.Entry<String, Map<String, DetailResult<PointValue>>> entry : platMap.entrySet()) {
            startCount++;
            Future<FutureBean<LocalNotify>> future = jobExecutor.submit(new MlmDataToFileHandler(entry.getKey(), nfsBean.buildMlmUploadAddress(entry.getKey()), entry.getValue()));
            futures.add(future);
        }
        if (isFinished()) {
            for (Future<FutureBean<LocalNotify>> future : futures) {
                try {
                    FutureBean<LocalNotify> bean = future.get();
                    if (bean != null && !bean.isHasException()) {
                        LocalNotify localNotify = bean.getT();
                        localNotify.setFromPlat(nfsBean.getPlatCode());
                        localNotify.setFromSys(nfsBean.getSysCode());
                        localNotify.setToPlat(bean.getToPlatCode());
                        localNotify.setToSys(nfsBean.getToSysCode());
                        localNotify.setPurpose(FssPurpose.BM_MLM.getCode());
                        localNotify.setNotifyType(FssNotifyType.MD5.getTypeNo());
                        localNotify.setNotifyNo(NotifyNoUtil.outNotifyNo(localNotify));
                        localNotify.setNotifyTime(FssDateUtil.obtainWeekDay(Calendar.SUNDAY, FssDateUtil.PREVIOUS_WEEK));
                        if (CollectionUtils.isNotEmpty(localNotify.getDetails())) {
                            for (FileDetail detail : localNotify.getDetails()) {
                                detail.setNotifyNo(localNotify.getNotifyNo());
                            }
                        }
                        notifies.add(localNotify);
                    }
                } catch (InterruptedException | ExecutionException e) {
                    logger.error("====上传增值积分统计文件，获取结果异常=====", e);
                }
            }
        }
        return notifies;
    }

}
