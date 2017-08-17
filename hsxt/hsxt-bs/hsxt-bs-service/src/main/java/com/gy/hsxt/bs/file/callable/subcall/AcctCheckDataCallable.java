/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.bs.file.callable.subcall;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.gy.hsi.ds.api.IDSBatchServiceListener;
import com.gy.hsxt.bs.bean.order.AccountDetail;
import com.gy.hsxt.bs.disconf.FileConfig;
import com.gy.hsxt.bs.file.bean.CheckInfo;
import com.gy.hsxt.bs.file.bean.DataInfo;
import com.gy.hsxt.bs.file.callable.DataFileCallable;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 日终AC对账调用
 *
 * @Package : com.gy.hsxt.bs.file.callable.subcall
 * @ClassName : AcctCheckDataCallable
 * @Description : 日终AC对账调用
 * @Author : chenm
 * @Date : 2016/2/24 17:01
 * @Version V3.0.0.0
 */
public class AcctCheckDataCallable extends DataFileCallable<AccountDetail> {
    /**
     * 带参构造函数
     *
     * @param applicationContext 上下文
     * @param executeId          执行编号
     * @param dataInfo           文件信息
     * @param checkInfo          校验文件信息
     */
    private AcctCheckDataCallable(ApplicationContext applicationContext, String executeId, DataInfo<AccountDetail> dataInfo, CheckInfo checkInfo) {
        super(applicationContext, executeId, dataInfo, checkInfo);
    }

    /**
     * Computes a result, or throws an exception if unable to do so.
     *
     * @return computed result
     * @throws Exception if unable to compute a result
     */
    @Override
    public DataInfo<AccountDetail> call() throws Exception {
        try {
            if (CollectionUtils.isNotEmpty(this.getDataInfo().getData())) {
                List<String> dataList = new ArrayList<>();
                // 遍历数据列表
                for (AccountDetail detail : this.getDataInfo().getData()) {
                    /**
                     * 交易流水号|业务记账分录ID｜客户号｜账户类型｜增向金额｜减向金额｜记账状态|交易类型|原交易单号
                     */
                    StringBuilder sb = new StringBuilder();// 创建对象
                    // 数据拼装
                    sb.append(StringUtils.trimToEmpty(detail.getBizNo())).append(FileConfig.DATA_SEPARATOR);// 交易流水号
                    sb.append(StringUtils.trimToEmpty(detail.getAccountNo())).append(FileConfig.DATA_SEPARATOR);// 业务记账分录ID
                    sb.append(StringUtils.trimToEmpty(detail.getCustId())).append(FileConfig.DATA_SEPARATOR);// 客户号
                    sb.append(StringUtils.trimToEmpty(detail.getAccType())).append(FileConfig.DATA_SEPARATOR);// 帐户类型编码
                    sb.append(StringUtils.defaultIfBlank(detail.getAddAmount(), "0")).append(FileConfig.DATA_SEPARATOR);// 增向金额
                    sb.append(StringUtils.defaultIfBlank(detail.getDecAmount(), "0")).append(FileConfig.DATA_SEPARATOR);// 减向金额
                    sb.append(detail.getStatus() ? 1 : 0).append(FileConfig.DATA_SEPARATOR);// 记账状态
                    sb.append(StringUtils.trimToEmpty(detail.getTransType())).append(FileConfig.DATA_SEPARATOR);// 交易类型
                    sb.append(StringUtils.trimToEmpty(detail.getBizNo()));// 原交易流水号
                    sb.append(IOUtils.LINE_SEPARATOR);

                    // 添加到List
                    dataList.add(sb.toString());
                }
                this.getDataInfo().setDataSum(dataList.size());//在添加结束标识之前计算数据总数
                // 添加结束标识
                dataList.add(FileConfig.DATA_END);

                // 把拼装好的数据生成文档
                File file = new File(this.getDataInfo().getDirPath() + this.getDataInfo().getFileName());

                // 生成文档
                FileUtils.writeLines(file, FileConfig.DEFAULT_CHARSET, dataList, "");

                this.getDataInfo().setCompleted(true);//文件生成完成

                this.getCheckInfo().addFileInfo(this.getDataInfo());
            }
        } catch (Exception e) {
            this.getLogger().error("========生成日终AC对账的数据文件[{}]失败=======", this.getDataInfo().getFileName(), e);
            IDSBatchServiceListener batchServiceListener = this.getApplicationContext().getBean(IDSBatchServiceListener.class);
            batchServiceListener.reportStatus(this.getExecuteId(), 1, "生成日终AC对账的数据文件[" + this.getDataInfo().getFileName() + "]失败，原因:[" + e.getMessage() + "]");
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
    public static AcctCheckDataCallable bulid(ApplicationContext applicationContext, String executeId, DataInfo<AccountDetail> dataInfo, CheckInfo checkInfo) {
        return new AcctCheckDataCallable(applicationContext, executeId, dataInfo, checkInfo);
    }
}
