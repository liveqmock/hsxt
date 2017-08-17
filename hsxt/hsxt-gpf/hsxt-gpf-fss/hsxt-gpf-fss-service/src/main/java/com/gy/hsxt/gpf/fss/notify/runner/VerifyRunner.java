/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.gpf.fss.notify.runner;

import com.gy.hsxt.gpf.fss.bean.FileDetail;
import com.gy.hsxt.gpf.fss.notify.future.FutureBean;
import com.gy.hsxt.gpf.fss.utils.MD5;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.Callable;

/**
 * @Package :com.gy.hsxt.gpf.fss.notify.runner
 * @ClassName : VerifyRunner
 * @Description : TODO
 * @Author : chenm
 * @Date : 2015/10/26 10:27
 * @Version V3.0.0.0
 */
public class VerifyRunner implements Callable<FutureBean<FileDetail>> {

    private Logger logger = LoggerFactory.getLogger(VerifyRunner.class);

    private FileDetail detail;

    public VerifyRunner(FileDetail detail) {
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
        logger.info("=====VerifyRunner---detail:{} =====", detail);
        FutureBean<FileDetail> futureBean = new FutureBean<FileDetail>();
        futureBean.setResult(detail);
        if (detail != null) {
            try {
                File file = new File(detail.getTarget());
                String md5 = MD5.toMD5code(file);
                String name = file.getName();
                if (detail.getCode().equals(md5) && detail.getFileName().equals(name)) {
                    detail.setIsPass(1);
                    futureBean.setSuccess(true);
                } else {
                    String reason = "";
                    if (!detail.getCode().equals(md5)) {
                        reason = "md5 is not equal";
                    }
                    if (!detail.getFileName().equals(name)) {
                        reason = "the file name is not equal";
                    }
                    detail.setIsPass(0);
                    detail.setReason(reason);
                    futureBean.setSuccess(false);
                    futureBean.setReason(reason);
                    logger.info("=====the verification of  file is failed,the reason is [{}] =====", reason);
                }
            } catch (IOException e) {
                logger.error("=====it occurs an exception when verifying =====", e);
                detail.setIsPass(0);
                detail.setReason("an exception");
                futureBean.setSuccess(false);
                futureBean.setReason(e.getMessage());
            }
        }
        return futureBean;
    }

}
