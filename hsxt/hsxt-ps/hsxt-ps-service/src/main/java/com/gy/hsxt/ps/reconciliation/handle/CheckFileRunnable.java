/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.ps.reconciliation.handle;

import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.ps.common.PsException;
import com.gy.hsxt.ps.distribution.bean.Alloc;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

/**
 * @version V3.0
 * @ClassName: AllocRunnable
 * @Description: 日终处理对账文件
 * @author: engfei
 * @date: 2015-12-28 上午9:20:21
 */

public class CheckFileRunnable implements Runnable {

    /**
     * list存储数据对象
     **/
    private List<Alloc> list;

    /**
     * 文件名称和路径
     **/
    private String writePathName;

    /**
     * 赋值
     *
     * @param list 积分明细信息
     */
    public CheckFileRunnable(List<Alloc> list, String writePathName) {
        this.list = list;
        this.writePathName = writePathName;
    }

    /**
     * 启动线程
     */
    @Override
    public void run() {
        try {
            // 调用日终文件生成线程方法
            batDispose();
        } catch (HsException e) {
             PsException.psThrowException(new Throwable().getStackTrace()[0], RespCode.PS_THREAD_ERROR.getCode(), "调用日终文件生成线程方法任务异常",e);
        }
    }

    /**
     * 日终生成对账文件
     *
     * @throws HsException
     */
    public void batDispose() throws HsException {
        StringBuffer sb = new StringBuffer();
        for (Alloc alloc : this.list) {
//			客户号
            sb.append(alloc.getCustId()+ "|");
//			业务记账分录ID
            sb.append(alloc.getEntryNo()+ "|");
//			增向金额
            sb.append(alloc.getAddAmount()+ "|");
//			减向金额
            sb.append(alloc.getSubAmount()+ "|");
//           交易流水号
            sb.append(alloc.getRelTransNo());
//            换行
            sb.append("\r\n");
        }
        sb.append("end");

        File file = new File(writePathName);

        try {
            //写文件
            FileUtils.writeStringToFile(file,sb.toString(),Charset.defaultCharset());
        } catch (IOException e) {
             PsException.psThrowException(new Throwable().getStackTrace()[0], RespCode.PS_WRITE_FILE_ERROR.getCode(), "写文件出现异常",e);
        }
    }
}
