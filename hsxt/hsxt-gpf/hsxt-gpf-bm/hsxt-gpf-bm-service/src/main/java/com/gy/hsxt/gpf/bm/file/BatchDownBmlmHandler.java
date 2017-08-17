/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.gpf.bm.file;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.gpf.bm.bean.Bmlm;
import com.gy.hsxt.gpf.bm.bean.FutureBean;
import com.gy.hsxt.gpf.bm.job.runner.handler.BmlmFileToDataHandler;
import com.gy.hsxt.gpf.fss.bean.FileDetail;
import com.gy.hsxt.gpf.fss.bean.LocalNotify;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * 再增值积分文件下载批处理器
 *
 * @Package :com.gy.hsxt.gpf.bm.file
 * @ClassName : BatchDownBmlmFileHandler
 * @Description : TODO
 * @Author : chenm
 * @Date : 2015/10/13 19:51
 * @Version V3.0.0.0
 */
public class BatchDownBmlmHandler extends AbstractBatchDealHandler<List<Bmlm>> {

    private Logger logger = LoggerFactory.getLogger(BatchDownBmlmHandler.class);

    /**
     * 再增值数据
     */
    private List<Bmlm> bmlms = new ArrayList<Bmlm>();

    /**
     * 文件系统通知
     */
    private LocalNotify localNotify;

    public List<Bmlm> dealFiles() throws HsException {
        initData();
        logger.info("=====通知实体：{} =====",localNotify);
        if (localNotify != null&&localNotify.getFileCount()==localNotify.getDetails().size()) {
            for (FileDetail fileDetail : localNotify.getDetails()) {
                File file = new File(fileDetail.getTarget());
                Future<FutureBean<List<Bmlm>>> future = jobExecutor.submit(new BmlmFileToDataHandler(file));
                futures.add(future);
                startCount++;
            }
            if (isFinished()) {
                for (Future<FutureBean<List<Bmlm>>> future : futures) {
                    if (future.isDone()) {
                        try {
                            bmlms.addAll(future.get().getT());
                        } catch (InterruptedException | ExecutionException e) {
                            logger.error("======获取文件解析结果异常========", e);
                        }
                    }
                }
            }
        }
        return bmlms;
    }

    /**
     * 设置通知实体
     * @param localNotify
     * @return
     */
    public BatchDownBmlmHandler setLocalNotify(LocalNotify localNotify) {
        this.localNotify = localNotify;
        return this;
    }
}
