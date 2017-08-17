/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.gpf.fss.notify.handler;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.gpf.fss.bean.FileDetail;
import com.gy.hsxt.gpf.fss.service.FileDetailService;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.List;

/**
 * @Package :com.gy.hsxt.gpf.fss.file.handler
 * @ClassName : UploadHandler
 * @Description : TODO
 * @Author : chenm
 * @Date : 2015/10/23 16:08
 * @Version V3.0.0.0
 */
public class UploadHandler extends AbstractNotifyHandler<FileDetail> {


    public UploadHandler(ThreadPoolTaskExecutor jobExecutor) {
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
    protected boolean doDeal(List<FileDetail> details,FileDetailService fileDetailService) throws HsException {

        return true;
    }
}
