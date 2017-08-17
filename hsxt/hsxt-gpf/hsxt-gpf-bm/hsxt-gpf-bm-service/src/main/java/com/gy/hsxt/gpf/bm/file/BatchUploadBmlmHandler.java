/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.gpf.bm.file;

import com.gy.hsxt.gpf.bm.bean.BmlmVo;
import com.gy.hsxt.gpf.bm.bean.FutureBean;
import com.gy.hsxt.gpf.bm.job.runner.handler.BmlmDataToFileHandler;
import com.gy.hsxt.gpf.fss.bean.FileDetail;
import com.gy.hsxt.gpf.fss.bean.LocalNotify;
import com.gy.hsxt.gpf.fss.constant.FssNotifyType;
import com.gy.hsxt.gpf.fss.constant.FssPurpose;
import com.gy.hsxt.gpf.fss.utils.FssDateUtil;
import com.gy.hsxt.gpf.fss.utils.NotifyNoUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 * 再增值积分上传文件批处理器
 *
 * @Package :com.gy.hsxt.gpf.bm.file
 * @ClassName : BatchUploadBmlmFileHandler
 * @Description : 再增值积分上传文件批处理器
 * @Author : chenm
 * @Date : 2015/10/13 19:51
 * @Version V3.0.0.0
 */
public class BatchUploadBmlmHandler extends AbstractBatchDealHandler<LocalNotify> {

    private Logger logger = LoggerFactory.getLogger(BatchUploadBmlmHandler.class);

    /**
     * 上传再增值积分统计数据到业务系统
     *
     * @param bmlmVos 再增值数据
     * @return {@code List<LocalNotify>}
     */
    public List<LocalNotify> uploadBmlmFile(List<BmlmVo> bmlmVos) {
        initData();
        List<LocalNotify> notifies = new ArrayList<>();
        Map<String, List<BmlmVo>> platMap = new HashMap<>();
        for (BmlmVo bmlmVo : bmlmVos) {
            String code = platData.obtainCode(bmlmVo.getResNo());
            List<BmlmVo> bds = platMap.get(code);
            if (bds == null) {
                bds = new ArrayList<>();
                platMap.put(code, bds);
            }
            bds.add(bmlmVo);
        }
        if (MapUtils.isNotEmpty(platMap)) {
            for (Map.Entry<String, List<BmlmVo>> entry : platMap.entrySet()) {
                startCount++;
                FutureTask<FutureBean<LocalNotify>> future = new FutureTask<>(new BmlmDataToFileHandler(entry.getKey(), nfsBean.buildBmlmUploadAddress(entry.getKey()), entry.getValue()));
                jobExecutor.execute(future);
                futures.add(future);
            }
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
                        localNotify.setPurpose(FssPurpose.BM_BMLM.getCode());
                        localNotify.setNotifyType(FssNotifyType.MD5.getTypeNo());
                        localNotify.setNotifyNo(NotifyNoUtil.outNotifyNo(localNotify));
                        //上个月最后一天
                        String lastDate = FssDateUtil.obtainMonthLastDay(FssDateUtil.PREVIOUS_MONTH,FssDateUtil.DEFAULT_DATE_FORMAT);
                        localNotify.setNotifyTime(lastDate);
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
