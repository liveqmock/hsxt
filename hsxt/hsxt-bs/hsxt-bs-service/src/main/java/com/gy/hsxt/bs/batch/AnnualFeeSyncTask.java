/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.batch;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.gy.hsxt.bs.annualfee.interfaces.IAnnualFeeInfoService;
import com.gy.hsxt.bs.bean.annualfee.AnnualFeeInfo;
import com.gy.hsxt.bs.bean.annualfee.AnnualFeeInfoQuery;
import com.gy.hsxt.bs.disconf.FileConfig;
import com.gy.hsxt.bs.file.bean.CheckInfo;
import com.gy.hsxt.bs.file.bean.DataInfo;
import com.gy.hsxt.bs.file.callable.CheckFileCallable;
import com.gy.hsxt.bs.file.callable.subcall.AnnualFeeSyncDataCallable;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.utils.DateUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 年费信息状态同步实现
 *
 * @Package : com.gy.hsxt.bs.annualfee.service
 * @ClassName : AnnualFeeSyncTask
 * @Description : 将年费信息状态同步到用户中心
 * @Author : chenm
 * @Date : 2015/12/26 18:32
 * @Version V3.0.0.0
 */
@Service
public class AnnualFeeSyncTask extends AbstractBatchService {

    /**
     * 年费信息业务接口
     */
    @Resource
    private IAnnualFeeInfoService annualFeeInfoService;

    /**
     * 业务执行方法
     *
     * @param executeId 任务执行编号
     * @param scanDate  扫描日期
     * @throws Exception
     */
    @Override
    public void execute(String executeId, String scanDate) throws Exception {
        //扫描日期
        Date scan = DateUtil.StringToDate(scanDate, DateUtil.DATE_FORMAT);
        String date = DateUtil.addDaysStr(scan, 1);
        //文件目录
        String dirPath = this.getFileConfig().joinPreDirPath(FileConfig.ANNUAL_FEE_SYNC,scanDate);

        //最大行数
        int maxLineNum = this.getFileConfig().getMaxLineNum();
        // 设置查询条件
        AnnualFeeInfoQuery query = new AnnualFeeInfoQuery();
        query.setStatusChangeDate(date);// 变更欠费状态的时间为今天

        Page page = new Page(1, maxLineNum);
        // 查询所有变更了欠费状态的年费信息
        List<AnnualFeeInfo> annualFeeInfos = annualFeeInfoService.queryListForPage(page, query);

        int fileNum = (page.getCount() + maxLineNum - 1) / maxLineNum;// 计算要生成多少个文档
        /**
         * 校验文件名称格式：YYYYMMDD_CHECK.TXT
         */
        CheckInfo checkInfo = CheckInfo.bulid(fileNum, dirPath, scanDate + FileConfig.FILE_CHECK_TAIL);

        CheckFileCallable checkFileCallable = CheckFileCallable.bulid(this.getApplicationContext(), executeId, checkInfo);

        if (CollectionUtils.isNotEmpty(annualFeeInfos)) {
            for (int i = fileNum; i > 0; i--) {
                //文件数据
                List<AnnualFeeInfo> data;
                if (i == 1) {
                    data = annualFeeInfos;
                } else {
                    page.setCurPage(i);
                    data = annualFeeInfoService.queryListForPage(page, query);
                }
                /**
                 * 数据文件名称格式：YYYYMMDD_N.TXT（YYYYMMDD年月日，N从1开始递增）
                 */
                String txtName = scanDate + "_" + i + FileConfig.FILE_SUFFIX;

                //数据文件信息
                DataInfo<AnnualFeeInfo> dataInfo = DataInfo.bulid(dirPath, txtName, data);

                //构建年费状态同步调度
                AnnualFeeSyncDataCallable annualFeeSyncDataCallable = AnnualFeeSyncDataCallable.bulid(this.getApplicationContext(), executeId, dataInfo, checkInfo);

                //执行调度
                this.getThreadPoolTaskExecutorSupport().submit(checkFileCallable, annualFeeSyncDataCallable);

            }
        } else {
            //生成空check文件
            this.getThreadPoolTaskExecutorSupport().execute(checkFileCallable);
        }
    }
}
