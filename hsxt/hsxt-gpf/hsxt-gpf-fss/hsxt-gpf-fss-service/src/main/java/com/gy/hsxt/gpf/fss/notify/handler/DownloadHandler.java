/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.gpf.fss.notify.handler;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.gpf.fss.bean.FileDetail;
import com.gy.hsxt.gpf.fss.notify.future.FutureBean;
import com.gy.hsxt.gpf.fss.notify.runner.DownloadRunner;
import com.gy.hsxt.gpf.fss.service.FileDetailService;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @Package :com.gy.hsxt.gpf.fss.file.handler
 * @ClassName : DownloadHandler
 * @Description : TODO
 * @Author : chenm
 * @Date : 2015/10/23 16:08
 * @Version V3.0.0.0
 */
public class DownloadHandler extends AbstractNotifyHandler<FileDetail> {

    private Logger logger = LoggerFactory.getLogger(DownloadHandler.class);

    public DownloadHandler(ThreadPoolTaskExecutor jobExecutor) {
        super(jobExecutor);
    }

    /**
     * 实际的业务处理逻辑
     *
     * @param details
     * @param fileDetailService
     * @return
     * @throws HsException
     */
    @Override
    protected boolean doDeal(List<FileDetail> details, FileDetailService fileDetailService) throws HsException {
        if (CollectionUtils.isNotEmpty(details)) {
            initData();
            for (FileDetail detail : details) {
                startCount++;
                Future<FutureBean<FileDetail>> future = jobExecutor.submit(new DownloadRunner(detail));
                futures.add(future);
            }
            if (isFinished()) {
                for (Future<FutureBean<FileDetail>> future : futures) {
                    try {
                        fileDetailService.modify(future.get().getResult());
                    } catch (InterruptedException | ExecutionException e) {
                        logger.error("=====下载更新文件详情失败=====", e);
                    }
                }
            }
        } else {
            logger.info("=====文件详情列表为空=====");
        }
        return true;
    }
}
