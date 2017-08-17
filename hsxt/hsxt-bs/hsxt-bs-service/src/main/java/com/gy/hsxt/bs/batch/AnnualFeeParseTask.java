/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.batch;

import com.gy.hsxt.bs.disconf.FileConfig;
import com.gy.hsxt.bs.file.bean.ParseInfo;
import com.gy.hsxt.bs.file.callable.subcall.AnnualFeeParseCallable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.io.File;

/**
 * 年费文件解析任务
 *
 * @Package : com.gy.hsxt.bs.batch
 * @ClassName : AnnualFeeParseTask
 * @Description : 年费文件解析任务
 * @Author : chenm
 * @Date : 2015/12/26 11:13
 * @Version V3.0.0.0
 */
@Service
public class AnnualFeeParseTask extends AbstractBatchService {

    /**
     * 业务执行方法
     *
     * @param executeId 任务执行编号
     * @param scanDate  扫描日期
     * @throws Exception
     */
    @Override
    public void execute(String executeId, String scanDate) throws Exception {
        // 文件夹路径
        String dirPath = this.getFileConfig().joinPreDirPath(FileConfig.ANNUAL_FEE_ACCT,scanDate);
        File dir = new File(dirPath);
        int count = 0;//解析文件数
        // 文件夹存在
        if (dir.exists() && dir.isDirectory()) {
            String[] fileNames = dir.list();//文件名称
            if (!ObjectUtils.isEmpty(fileNames)) {
                for (String fileName : fileNames) {
                    if (fileName.endsWith(FileConfig.FILE_RETURN_TAIL)) {
                        //构建解析文件信息
                        ParseInfo parseInfo = ParseInfo.create(dirPath, fileName);
                        //构建年费解析调度
                        AnnualFeeParseCallable annualFeeParseCallable = AnnualFeeParseCallable.bulid(this.getApplicationContext(), executeId, parseInfo);
                        //执行年费调度
                        this.getThreadPoolTaskExecutorSupport().submit(annualFeeParseCallable);
                        //计数
                        count++;
                    }
                }
            }
        }
        //计数值为0
        if (count==0){
            this.getBatchServiceListener().reportStatus(executeId, 0, "执行成功,["+scanDate+"]的年费解析文件数为0");
        }
    }

}
