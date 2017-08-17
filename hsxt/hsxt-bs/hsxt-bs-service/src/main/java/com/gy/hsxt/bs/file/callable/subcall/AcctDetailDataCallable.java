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
import com.gy.hsxt.bs.order.enumtype.DividendStatus;
import com.gy.hsxt.bs.order.mapper.AccountDetailMapper;
import com.gy.hsxt.bs.order.mapper.DividendRateMapper;
import com.gy.hsxt.common.constant.TransType;
import com.gy.hsxt.common.utils.DateUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 生成日终记账的数据文件
 *
 * @Package : com.gy.hsxt.bs.file.callable.subcall
 * @ClassName : AcctDetailDataCallable
 * @Description : 生成日终记账的数据文件
 * @Author : chenm
 * @Date : 2016/2/26 14:27
 * @Version V3.0.0.0
 */
public class AcctDetailDataCallable extends DataFileCallable<AccountDetail> {

    /**
     * 带参构造函数
     *
     * @param applicationContext 上下文
     * @param executeId          执行编号
     * @param dataInfo           文件信息
     * @param checkInfo          校验文件信息
     */
    private AcctDetailDataCallable(ApplicationContext applicationContext, String executeId, DataInfo<AccountDetail> dataInfo, CheckInfo checkInfo) {
        super(applicationContext, executeId, dataInfo, checkInfo);
    }

    /**
     * Computes a result, or throws an exception if unable to do so.
     *
     * @return computed result
     * @throws Exception if unable to compute a result
     */
    @Override
    @Transactional
    public DataInfo<AccountDetail> call() throws Exception {
        try {
            if (CollectionUtils.isNotEmpty(this.getDataInfo().getData())) {
                List<String> dataList = new ArrayList<>();
                // 遍历数据列表
                for (AccountDetail detail : this.getDataInfo().getData()) {
                    StringBuilder sb = new StringBuilder();// 创建对象
                    // 数据拼装
                    sb.append(StringUtils.trimToEmpty(detail.getCustId())).append(FileConfig.DATA_SEPARATOR);// 客户号
                    sb.append(StringUtils.trimToEmpty(detail.getHsResNo())).append(FileConfig.DATA_SEPARATOR);// 互生号
                    sb.append(detail.getCustType()).append(FileConfig.DATA_SEPARATOR);// 客户类型
                    sb.append(StringUtils.trimToEmpty(DateUtil.getCurrentDateNoSign())).append(FileConfig.DATA_SEPARATOR);// 批次号
                    sb.append(StringUtils.trimToEmpty(detail.getAccType())).append(FileConfig.DATA_SEPARATOR);// 帐户类型编码
                    sb.append(StringUtils.defaultIfBlank(detail.getAddAmount(), "0")).append(FileConfig.DATA_SEPARATOR);// 增向金额
                    sb.append(StringUtils.defaultIfBlank(detail.getDecAmount(), "0")).append(FileConfig.DATA_SEPARATOR);// 减向金额
                    sb.append("0").append(FileConfig.DATA_SEPARATOR);// 红冲标识
                    FileConfig fileConfig = this.getApplicationContext().getBean(FileConfig.class);
                    sb.append(fileConfig.getSystemId()).append(FileConfig.DATA_SEPARATOR);// 交易系统
                    sb.append(StringUtils.trimToEmpty(detail.getTransType())).append(FileConfig.DATA_SEPARATOR);// 交易类型
                    sb.append(StringUtils.trimToEmpty(detail.getBizNo())).append(FileConfig.DATA_SEPARATOR);// 交易流水号
                    sb.append(StringUtils.trimToEmpty(detail.getCreatedDate())).append(FileConfig.DATA_SEPARATOR);// 交易时间
                    sb.append(StringUtils.trimToEmpty(detail.getFiscalDate())).append(FileConfig.DATA_SEPARATOR);// 会计日期
                    sb.append("gjl|");// 关联交易类型
                    sb.append(StringUtils.trimToEmpty(detail.getBizNo())).append(FileConfig.DATA_SEPARATOR);// 关联交流水号
                    sb.append(detail.getRemark()).append(FileConfig.DATA_SEPARATOR);// 备注
                    sb.append(detail.getAccountNo()).append(FileConfig.DATA_SEPARATOR);// 各系统分录序号
                    sb.append(StringUtils.SPACE);// 关联各系统分录序号
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

                // -----------------------------更新记帐分解记录状态-----------------------------------
                // 遍历数据列表
                for (AccountDetail detail : this.getDataInfo().getData()) {
                    // 更新记帐分解记录状态
                    AccountDetailMapper accountDetailMapper = this.getApplicationContext().getBean(AccountDetailMapper.class);
                    accountDetailMapper.updateBizNoStatus(detail.getBizNo());
                    if (TransType.C_INVEST_BONUS.getCode().equals(detail.getTransType()) || TransType.P_INVEST_BONUS.getCode().equals(detail.getTransType())) {
                        // 获取分红年份，-1为去年
                        String dividendYear = DateUtil.getAssignYear(-1);

                        // 线程结束：记帐文件生成完成后更改投资状态为4（记帐文件生成完成）
                        DividendRateMapper dividendRateMapper = this.getApplicationContext().getBean(DividendRateMapper.class);
                        dividendRateMapper.updateDividendStatus(dividendYear, DividendStatus.ACCOUNT_FILE_COMPLETE.getCode());
                    }
                }
            }
        } catch (Exception e) {
            this.getLogger().error("========生成日终记账的数据文件[{}]失败=======", this.getDataInfo().getFileName(), e);
            IDSBatchServiceListener batchServiceListener = this.getApplicationContext().getBean(IDSBatchServiceListener.class);
            batchServiceListener.reportStatus(this.getExecuteId(), 1, "生成日终记账的数据文件[" + this.getDataInfo().getFileName() + "]失败，原因:[" + e.getMessage() + "]");
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
    public static AcctDetailDataCallable bulid(ApplicationContext applicationContext, String executeId, DataInfo<AccountDetail> dataInfo, CheckInfo checkInfo) {
        return new AcctDetailDataCallable(applicationContext, executeId, dataInfo, checkInfo);
    }
}
