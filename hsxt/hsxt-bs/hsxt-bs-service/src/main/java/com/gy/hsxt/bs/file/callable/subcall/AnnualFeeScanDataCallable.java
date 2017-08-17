/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.bs.file.callable.subcall;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.gy.hsi.ds.api.IDSBatchServiceListener;
import com.gy.hsxt.bs.bean.annualfee.AnnualFeeOrder;
import com.gy.hsxt.bs.bean.order.Order;
import com.gy.hsxt.bs.common.interfaces.ICommonService;
import com.gy.hsxt.bs.disconf.BsConfig;
import com.gy.hsxt.bs.disconf.FileConfig;
import com.gy.hsxt.bs.file.bean.CheckInfo;
import com.gy.hsxt.bs.file.bean.DataInfo;
import com.gy.hsxt.bs.file.callable.DataFileCallable;
import com.gy.hsxt.common.constant.AccountType;
import com.gy.hsxt.common.constant.BizGroup;
import com.gy.hsxt.common.constant.CustType;
import com.gy.hsxt.common.constant.TransType;
import com.gy.hsxt.common.guid.GuidAgent;
import com.gy.hsxt.common.utils.DateUtil;
import com.gy.hsxt.lcs.bean.LocalInfo;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 生成年费欠费数据文件
 *
 * @Package : com.gy.hsxt.bs.file.callable.subcall
 * @ClassName : AnnualFeeScanDataCallable
 * @Description : 生成年费欠费数据文件
 * @Author : chenm
 * @Date : 2016/2/25 17:56
 * @Version V3.0.0.0
 */
public class AnnualFeeScanDataCallable extends DataFileCallable<AnnualFeeOrder> {

    /**
     * 带参构造函数
     *
     * @param applicationContext 上下文
     * @param executeId          执行编号
     * @param dataInfo           文件信息
     * @param checkInfo          校验文件信息
     */
    private AnnualFeeScanDataCallable(ApplicationContext applicationContext, String executeId, DataInfo<AnnualFeeOrder> dataInfo, CheckInfo checkInfo) {
        super(applicationContext, executeId, dataInfo, checkInfo);
    }

    /**
     * Computes a result, or throws an exception if unable to do so.
     *
     * @return computed result
     * @throws Exception if unable to compute a result
     */
    @Override
    public DataInfo<AnnualFeeOrder> call() throws Exception {
        try {
            if (CollectionUtils.isNotEmpty(this.getDataInfo().getData())) {
                //
                ICommonService commonService = this.getApplicationContext().getBean(ICommonService.class);
                // 平台客户号
                String platCustId = commonService.getAreaPlatCustId();
                // 本地平台信息实体
                LocalInfo localInfo = commonService.getAreaPlatInfo();
                // 装1000000条记录
                List<String> dataList = new ArrayList<>();
                //业务基础数据
                BsConfig bsConfig = this.getApplicationContext().getBean(BsConfig.class);
                //文件基础配置
                FileConfig fileConfig = this.getApplicationContext().getBean(FileConfig.class);

                // 遍历数据列表
                for (AnnualFeeOrder annualFeeOrder : this.getDataInfo().getData()) {
                    Order order = annualFeeOrder.getOrder();

                    // -------------------------企业数据拼装--------------------------
                    /**
                     * 流通币 ACC_TYPE_POINT20110("20110"),
                     */
                    StringBuilder companyData = new StringBuilder();
                    companyData.append(order.getCustId()).append(FileConfig.DATA_SEPARATOR);// 客户号 0
                    companyData.append(order.getHsResNo()).append(FileConfig.DATA_SEPARATOR);// 互生号 1
                    companyData.append(order.getCustType()).append(FileConfig.DATA_SEPARATOR);// 客户类型 2
                    companyData.append(DateUtil.getCurrentDateNoSign()).append(FileConfig.DATA_SEPARATOR);// 批次号 3
                    companyData.append(AccountType.ACC_TYPE_POINT20110.getCode()).append(FileConfig.DATA_SEPARATOR);// 账户类型
                    companyData.append("0").append(FileConfig.DATA_SEPARATOR);// 增向金额 5
                    companyData.append(order.getOrderHsbAmount()).append(FileConfig.DATA_SEPARATOR);// 减向金额 6
                    companyData.append("0").append(FileConfig.DATA_SEPARATOR);// 红冲标识 7
                    companyData.append(fileConfig.getSystemId()).append(FileConfig.DATA_SEPARATOR);// 交易系统 8
                    companyData.append(TransType.C_HSB_PAY_ANNUAL_FEE.getCode()).append(FileConfig.DATA_SEPARATOR);// 交易类型
                    companyData.append(order.getOrderNo()).append(FileConfig.DATA_SEPARATOR);// 交易流水号 10
                    companyData.append(DateUtil.getCurrentDateTime()).append(FileConfig.DATA_SEPARATOR);// 交易时间 11
                    companyData.append(FileConfig.getYesterdayTime()).append(FileConfig.DATA_SEPARATOR);// 会计日期 12
                    companyData.append("gjl").append(FileConfig.DATA_SEPARATOR);// 关联交易类型 13
                    companyData.append(order.getOrderNo()).append(FileConfig.DATA_SEPARATOR);// 关联交易流水号 14
                    companyData.append("自动扣年费").append(FileConfig.DATA_SEPARATOR);// 备注 15
                    companyData.append(GuidAgent.getStringGuid(BizGroup.BS + bsConfig.getAppNode())).append(FileConfig.DATA_SEPARATOR);// 各系统分录序号
                    companyData.append(StringUtils.SPACE);// 关联各系统分录序号
                    companyData.append(IOUtils.LINE_SEPARATOR);
                    // 添加到List
                    dataList.add(companyData.toString());

                    // -------------------------平台数据拼装--------------------------
                    /**
                     * 年费收入 ACC_TYPE_POINT20440("20440"),
                     */
                    StringBuilder platData = new StringBuilder();// 创建字符对象
                    platData.append(platCustId).append(FileConfig.DATA_SEPARATOR);// 客户号
                    platData.append(localInfo.getPlatResNo()).append(FileConfig.DATA_SEPARATOR);// 互生号
                    platData.append(CustType.AREA_PLAT.getCode()).append(FileConfig.DATA_SEPARATOR);// 客户类型
                    platData.append(DateUtil.getCurrentDateNoSign()).append(FileConfig.DATA_SEPARATOR);// 批次号
                    platData.append(AccountType.ACC_TYPE_POINT20440.getCode()).append(FileConfig.DATA_SEPARATOR);// 账户类型
                    platData.append(order.getOrderHsbAmount()).append(FileConfig.DATA_SEPARATOR);// 增向金额
                    platData.append("0").append(FileConfig.DATA_SEPARATOR);// 减向金额
                    platData.append("0").append(FileConfig.DATA_SEPARATOR);// 红冲标识
                    platData.append(fileConfig.getSystemId()).append(FileConfig.DATA_SEPARATOR);// 交易系统
                    platData.append(TransType.C_HSB_PAY_ANNUAL_FEE.getCode()).append(FileConfig.DATA_SEPARATOR);// 交易类型
                    platData.append(order.getOrderNo()).append(FileConfig.DATA_SEPARATOR);// 交易流水号
                    platData.append(DateUtil.getCurrentDateTime()).append(FileConfig.DATA_SEPARATOR);// 交易时间
                    platData.append(FileConfig.getYesterdayTime()).append(FileConfig.DATA_SEPARATOR);// 会计日期
                    platData.append("gjl").append(FileConfig.DATA_SEPARATOR);// 关联交易类型
                    platData.append(order.getOrderNo()).append(FileConfig.DATA_SEPARATOR);// 关联交易流水号
                    platData.append("自动扣年费").append(FileConfig.DATA_SEPARATOR);// 备注
                    platData.append(GuidAgent.getStringGuid(BizGroup.BS + bsConfig.getAppNode())).append(FileConfig.DATA_SEPARATOR);// 各系统分录序号
                    platData.append(StringUtils.SPACE);// 关联各系统分录序号
                    platData.append(IOUtils.LINE_SEPARATOR);

                    // 添加到List
                    dataList.add(platData.toString());
                }
                this.getDataInfo().setDataSum(dataList.size());//在添加结束标识之前计算数据总数
                dataList.add(FileConfig.DATA_END);

                // 数据文件路径
                File file = new File(this.getDataInfo().getDirPath() + this.getDataInfo().getFileName());
                // 将数据写入文件
                FileUtils.writeLines(file, FileConfig.DEFAULT_CHARSET, dataList, "");

                this.getLogger().debug("========生成年费欠费数据文件[{}]成功=======", this.getDataInfo().getFileName());

                this.getDataInfo().setCompleted(true);

                this.getCheckInfo().addFileInfo(this.getDataInfo());
            }
        } catch (Exception e) {
            this.getLogger().error("========生成年费欠费数据文件[{}]失败=======", this.getDataInfo().getFileName(), e);
            IDSBatchServiceListener batchServiceListener = this.getApplicationContext().getBean(IDSBatchServiceListener.class);
            batchServiceListener.reportStatus(this.getExecuteId(), 1, "生成年费欠费数据文件[" + this.getDataInfo().getFileName() + "]失败，原因:[" + e.getMessage() + "]");
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
    public static AnnualFeeScanDataCallable bulid(ApplicationContext applicationContext, String executeId, DataInfo<AnnualFeeOrder> dataInfo, CheckInfo checkInfo) {
        return new AnnualFeeScanDataCallable(applicationContext, executeId, dataInfo, checkInfo);
    }
}
