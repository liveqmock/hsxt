/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.gpf.fss.notify.runner;

import com.gy.hsxt.common.utils.DateUtil;
import com.gy.hsxt.gpf.fss.bean.FileDetail;
import com.gy.hsxt.gpf.fss.notify.future.FutureBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.concurrent.Callable;

/**
 * @Package :com.gy.hsxt.gpf.fss.notify.runner
 * @ClassName : DownloadRunner
 * @Description : TODO
 * @Author : chenm
 * @Date : 2015/10/23 18:28
 * @Version V3.0.0.0
 */
public class DownloadRunner implements Callable<FutureBean<FileDetail>> {

    private Logger logger = LoggerFactory.getLogger(DownloadRunner.class);

    private FileDetail detail;

    public DownloadRunner(FileDetail detail) {
        this.detail = detail;
    }

    /**
     * Computes a result, or throws an exception if unable to do so.
     *
     * @return computed result
     * @throws Exception if unable to compute a result
     */
    @Override
    public FutureBean<FileDetail> call() throws Exception {
        logger.info("=====DownloadRunner---detail:{} =====", detail);
        FutureBean<FileDetail> futureBean = new FutureBean<>();
        futureBean.setResult(detail);
        try {
            if (detail != null && detail.getIsPass() == 1) {
                detail.setStartTime(DateUtil.DateTimeToString(new Date()));
                detail.setRecount(detail.getRecount() + 1);
                URL url = new URL(detail.getSource());
                // 下载网络文件
                int read;
                URLConnection conn = url.openConnection();
                InputStream inStream = conn.getInputStream();
                FileOutputStream fs = new FileOutputStream(detail.getTarget());
                byte[] buffer = new byte[1204];
                while ((read = inStream.read(buffer)) != -1) {
                    fs.write(buffer, 0, read);
                }
                fs.flush();
                fs.close();
                inStream.close();

                detail.setEndTime(DateUtil.DateTimeToString(new Date()));
                detail.setPercent(1);//下载完成
                futureBean.setSuccess(true);
            }
        } catch (Exception e) {
            logger.error("=====DownloadRunner---exception=====", e);
            detail.setPercent(0);
            futureBean.setSuccess(false);
            futureBean.setReason(e.getMessage());
        }
        return futureBean;
    }
}
