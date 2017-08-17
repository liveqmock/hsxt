/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.batch;

import com.gy.hsxt.bs.disconf.FileConfig;
import com.gy.hsxt.bs.file.bean.ParseInfo;
import com.gy.hsxt.bs.file.callable.subcall.InvoiceTotalParseCallable;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

/**
 * 发票统计文件解析任务
 *
 * @Package : com.gy.hsxt.bs.batch
 * @ClassName : InvoiceTotalParseTask
 * @Description : 发票统计文件解析任务
 * @Author : chenm
 * @Date : 2015/12/26 11:13
 * @Version V3.0.0.0
 */
@Service
public class InvoiceTotalParseTask extends AbstractBatchService {

    /**
     * 发票路径
     */
    public static final String INVOICE_FILE_PATH = File.separator + "RP" + File.separator + "IVS";

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
        String dirPath = this.getFileConfig().getDirRoot() + INVOICE_FILE_PATH + File.separator + scanDate + File.separator;
        //读取check文件
        File check = FileUtils.getFile(dirPath + scanDate + FileConfig.FILE_CHECK_TAIL);
        int count = 0;//解析文件数
        if (check.exists() && check.isFile()) {
            List<String> lines = FileUtils.readLines(check, FileConfig.DEFAULT_CHARSET);
            for (String line : lines) {
                if (!line.contains(FileConfig.DATA_END)) {
                    String[] metaDatas = StringUtils.splitPreserveAllTokens(line, "|");
                    //构建解析文件信息
                    ParseInfo parseInfo = ParseInfo.create(dirPath, metaDatas[0]);
                    //解析发票统计文件调度
                    InvoiceTotalParseCallable invoiceTotalParseCallable = InvoiceTotalParseCallable.bulid(this.getApplicationContext(), executeId, parseInfo);
                    //执行解析调度
                    this.getThreadPoolTaskExecutorSupport().submit(invoiceTotalParseCallable);
                    //计数
                    count++;
                }
            }
        }
        //计数值为0
        if (count==0){
            this.getBatchServiceListener().reportStatus(executeId, 0, "执行成功,["+scanDate+"]的解析发票统计文件数为0");
        }
    }
}
