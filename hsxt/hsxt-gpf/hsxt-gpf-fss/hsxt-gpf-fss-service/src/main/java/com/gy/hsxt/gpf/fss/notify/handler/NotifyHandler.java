/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.gpf.fss.notify.handler;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.gpf.fss.bean.FileDetail;
import com.gy.hsxt.gpf.fss.service.FileDetailService;

import java.util.List;

/**
 * @Package :com.gy.hsxt.gpf.fss.file.handler
 * @ClassName : NotifyHandler
 * @Description : TODO
 * @Author : chenm
 * @Date : 2015/10/23 15:52
 * @Version V3.0.0.0
 */
public interface NotifyHandler {

    /**
     * 处理方法
     * @param details
     * @param fileDetailService
     * @throws HsException
     */
    boolean handler(List<FileDetail> details,FileDetailService fileDetailService) throws HsException;
}
