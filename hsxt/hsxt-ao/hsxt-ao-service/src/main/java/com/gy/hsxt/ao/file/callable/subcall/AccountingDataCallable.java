/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.ao.file.callable.subcall;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.gy.hsi.ds.api.IDSBatchServiceListener;
import com.gy.hsxt.ao.bean.AccountingEntry;
import com.gy.hsxt.ao.disconf.AoConfig;
import com.gy.hsxt.ao.file.bean.CheckInfo;
import com.gy.hsxt.ao.file.bean.DataInfo;
import com.gy.hsxt.ao.file.callable.DataFileCallable;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 日终生成AO-AC记账对账文件
 *
 * @Package : com.gy.hsxt.ao.file.callable.subcall
 * @ClassName : AccountingDataCallable
 * @Description :  日终生成AO-AC记账对账文件
 * @Author : chenm
 * @Date : 2016/2/29 10:53
 * @Version V3.0.0.0
 */
public class AccountingDataCallable extends DataFileCallable<AccountingEntry> {

    /**
     * 带参构造函数
     *
     * @param applicationContext 上下文
     * @param executeId          执行编号
     * @param dataInfo           文件信息
     * @param checkInfo          校验文件信息
     */
    protected AccountingDataCallable(ApplicationContext applicationContext, String executeId, DataInfo<AccountingEntry> dataInfo, CheckInfo checkInfo) {
        super(applicationContext, executeId, dataInfo, checkInfo);
    }

    /**
     * Computes a result, or throws an exception if unable to do so.
     *
     * @return computed result
     * @throws Exception if unable to compute a result
     */
    @Override
    public DataInfo<AccountingEntry> call() throws Exception {
        try {
            if (CollectionUtils.isNotEmpty(this.getDataInfo().getData())) {
                List<String> dataList = new ArrayList<>();
                // 遍历数据列表
                for (AccountingEntry accountingEntry : this.getDataInfo().getData()) {
                    /**
                     * 交易流水号|业务记账分录ID｜客户号｜账户类型｜增向金额｜减向金额｜记账状态 |
                     */
                    StringBuilder sb = new StringBuilder();// 创建对象
                    // 数据拼装
                    sb.append(StringUtils.trimToEmpty(accountingEntry.getTransNo())).append(AoConfig.DATA_SEPARATOR);// 交易流水号
                    sb.append(StringUtils.trimToEmpty(accountingEntry.getAccountingEntryNo())).append(AoConfig.DATA_SEPARATOR);// 业务记账分录ID
                    sb.append(StringUtils.trimToEmpty(accountingEntry.getCustId())).append(AoConfig.DATA_SEPARATOR);// 客户号
                    sb.append(StringUtils.trimToEmpty(accountingEntry.getAccType())).append(AoConfig.DATA_SEPARATOR);// 帐户类型编码
                    sb.append(StringUtils.defaultIfBlank(accountingEntry.getAddAmount(), "0")).append(AoConfig.DATA_SEPARATOR);// 增向金额
                    sb.append(StringUtils.defaultIfBlank(accountingEntry.getDecAmount(), "0")).append(AoConfig.DATA_SEPARATOR);// 减向金额
                    sb.append(accountingEntry.getStatus() ? 1 : 0).append(AoConfig.DATA_SEPARATOR);// 记账状态
                    sb.append(accountingEntry.getTransType());// 交易类型
                    sb.append(IOUtils.LINE_SEPARATOR);
                    // 添加到List
                    dataList.add(sb.toString());
                }
                this.getDataInfo().setDataSum(dataList.size());//在添加结束标识之前计算数据总数
                // 添加结束标识
                dataList.add(AoConfig.DATA_END);

                // 把拼装好的数据生成文档
                File file = new File(this.getDataInfo().getDirPath() + this.getDataInfo().getFileName());

                // 生成文档
                FileUtils.writeLines(file, AoConfig.DEFAULT_CHARSET, dataList, "");

                this.getDataInfo().setCompleted(true);//文件生成完成

                this.getCheckInfo().addFileInfo(this.getDataInfo());
            }
        } catch (Exception e) {
            this.getLogger().error("======== 日终生成AO-AC记账对账文件[{}]失败=======", this.getDataInfo().getFileName(), e);
            IDSBatchServiceListener batchServiceListener = this.getApplicationContext().getBean(IDSBatchServiceListener.class);
            batchServiceListener.reportStatus(this.getExecuteId(), 1, " 日终生成AO-AC记账对账文件[" + this.getDataInfo().getFileName() + "]失败，原因:[" + e.getMessage() + "]");
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
    public static AccountingDataCallable bulid(ApplicationContext applicationContext, String executeId, DataInfo<AccountingEntry> dataInfo, CheckInfo checkInfo) {
        return new AccountingDataCallable(applicationContext, executeId, dataInfo, checkInfo);
    }
}
