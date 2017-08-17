/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.gpf.fss.notify.runner;

import com.gy.hsxt.gpf.fss.bean.FileDetail;
import com.gy.hsxt.gpf.fss.notify.future.FutureBean;

import java.util.concurrent.Callable;

/**
 * @Package :com.gy.hsxt.gpf.fss.notify.runner
 * @ClassName : UploadRunner
 * @Description : TODO
 * @Author : chenm
 * @Date : 2015/10/26 10:26
 * @Version V3.0.0.0
 */
public class UploadRunner implements Callable<FutureBean> {

    private FileDetail detail;

    public UploadRunner(FileDetail detail) {
        this.detail = detail;
    }

    /**
     * Computes a result, or throws an exception if unable to do so.
     *
     * @return computed result
     * @throws Exception if unable to compute a result
     */
    @Override
    public FutureBean call() throws Exception {
        System.out.println(detail);
        return null;
    }
}
