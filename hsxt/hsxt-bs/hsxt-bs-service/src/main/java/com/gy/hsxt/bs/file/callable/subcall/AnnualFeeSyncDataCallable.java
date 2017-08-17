/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.bs.file.callable.subcall;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.gy.hsi.ds.api.IDSBatchServiceListener;
import com.gy.hsxt.bs.bean.annualfee.AnnualFeeInfo;
import com.gy.hsxt.bs.disconf.FileConfig;
import com.gy.hsxt.bs.file.bean.CheckInfo;
import com.gy.hsxt.bs.file.bean.DataInfo;
import com.gy.hsxt.bs.file.callable.DataFileCallable;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.context.ApplicationContext;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 生成同步年费欠费状态数据文件
 *
 * @Package : com.gy.hsxt.bs.file.callable.subcall
 * @ClassName : AnnualFeeSyncDataCallable
 * @Description : 生成同步年费欠费状态数据文件
 * @Author : chenm
 * @Date : 2016/2/26 12:09
 * @Version V3.0.0.0
 */
public class AnnualFeeSyncDataCallable extends DataFileCallable<AnnualFeeInfo> {

    /**
     * 带参构造函数
     *
     * @param applicationContext 上下文
     * @param executeId          执行编号
     * @param dataInfo           文件信息
     * @param checkInfo          校验文件信息
     */
    private AnnualFeeSyncDataCallable(ApplicationContext applicationContext, String executeId, DataInfo<AnnualFeeInfo> dataInfo, CheckInfo checkInfo) {
        super(applicationContext, executeId, dataInfo, checkInfo);
    }

    /**
     * Computes a result, or throws an exception if unable to do so.
     *
     * @return computed result
     * @throws Exception if unable to compute a result
     */
    @Override
    public DataInfo<AnnualFeeInfo> call() throws Exception {
        try {
            this.getLogger().debug("========生成同步年费欠费状态数据文件[{}]开始=======", this.getDataInfo().getFileName());
            if (CollectionUtils.isNotEmpty(this.getDataInfo().getData())) {
                List<String> dataList = new ArrayList<>();
                // 遍历数据列表
                for (AnnualFeeInfo annualFeeInfo : this.getDataInfo().getData()) {
                    StringBuilder metadata = new StringBuilder();// 创建对象
                    // 拼装数据
                    metadata.append(annualFeeInfo.getEntCustId().trim()).append(FileConfig.DATA_SEPARATOR);// 客户号
                    metadata.append(annualFeeInfo.getEntResNo().trim()).append(FileConfig.DATA_SEPARATOR);// 互生号
                    metadata.append(annualFeeInfo.getEntCustName().trim()).append(FileConfig.DATA_SEPARATOR);// 企业名称
                    metadata.append(annualFeeInfo.getIsArrear()).append(FileConfig.DATA_SEPARATOR);// 是否欠年费0：否 // 1：是
                    metadata.append(annualFeeInfo.getEndDate());// 年费截止日期
                    metadata.append(IOUtils.LINE_SEPARATOR);
                    // 添加到List
                    dataList.add(metadata.toString());
                }
                this.getDataInfo().setDataSum(dataList.size());//在添加结束标识之前计算数据总数
                dataList.add(FileConfig.DATA_END);

                // 数据文件路径
                File file = new File(this.getDataInfo().getDirPath() + this.getDataInfo().getFileName());
                // 将数据写入文件
                FileUtils.writeLines(file, FileConfig.DEFAULT_CHARSET, dataList, "");

                this.getLogger().debug("========生成同步年费欠费状态数据文件[{}]成功=======", this.getDataInfo().getFileName());

                this.getDataInfo().setCompleted(true);

                this.getCheckInfo().addFileInfo(this.getDataInfo());
            }
        } catch (Exception e) {
            this.getLogger().error("========生成同步年费欠费状态数据文件[{}]失败=======", this.getDataInfo().getFileName(), e);
            IDSBatchServiceListener batchServiceListener = this.getApplicationContext().getBean(IDSBatchServiceListener.class);
            batchServiceListener.reportStatus(this.getExecuteId(), 1, "生成同步年费欠费状态数据文件[" + this.getDataInfo().getFileName() + "]失败，原因:[" + e.getMessage() + "]");
        }
        return this.getDataInfo();
    }

    /**
     * 构建函数
     *
     * @param applicationContext 上下文
     * @param executeId          执行编号
     * @param dataInfo           文件信息
     * @param checkInfo          校验文件信息
     * @return {@code AcctCheckDataFileCallable}
     */
    public static AnnualFeeSyncDataCallable bulid(ApplicationContext applicationContext, String executeId, DataInfo<AnnualFeeInfo> dataInfo, CheckInfo checkInfo) {
        return new AnnualFeeSyncDataCallable(applicationContext, executeId, dataInfo, checkInfo);
    }
}
