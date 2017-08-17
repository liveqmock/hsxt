/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.bs.file.callable.subcall;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.gy.hsi.ds.api.IDSBatchServiceListener;
import com.gy.hsxt.bs.bean.order.Order;
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
 * 生成日终GP对账的数据文件
 *
 * @Package : com.gy.hsxt.bs.file.callable.subcall
 * @ClassName : GPCheckDataCallable
 * @Description : 生成日终GP对账的数据文件
 * @Author : chenm
 * @Date : 2016/2/27 10:52
 * @Version V3.0.0.0
 */
public class GPCheckDataCallable extends DataFileCallable<Order> {

    /**
     * 带参构造函数
     *
     * @param applicationContext 上下文
     * @param executeId          执行编号
     * @param dataInfo           文件信息
     * @param checkInfo          校验文件信息
     */
    private GPCheckDataCallable(ApplicationContext applicationContext, String executeId, DataInfo<Order> dataInfo, CheckInfo checkInfo) {
        super(applicationContext, executeId, dataInfo, checkInfo);
    }

    /**
     * Computes a result, or throws an exception if unable to do so.
     *
     * @return computed result
     * @throws Exception if unable to compute a result
     */
    @Override
    public DataInfo<Order> call() throws Exception {
        try {
            if (CollectionUtils.isNotEmpty(this.getDataInfo().getData())) {
                List<String> dataList = new ArrayList<>();
                // 遍历数据列表
                for (Order order : this.getDataInfo().getData()) {
                    /**
                     * 业务订单号｜订单货币金额｜订单时间｜支付状态
                     */
                    StringBuilder line = new StringBuilder();
                    line.append(order.getOrderNo()).append(FileConfig.DATA_SEPARATOR);// 业务订单号
                    line.append(order.getOrderCashAmount()).append(FileConfig.DATA_SEPARATOR);// 订单货币金额
                    line.append(order.getOrderTime()).append(FileConfig.DATA_SEPARATOR);// 订单时间
                    line.append(order.getPayStatus());// 支付状态
                    line.append(IOUtils.LINE_SEPARATOR);
                    dataList.add(line.toString());
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
            this.getLogger().error("========生成日终GP对账的数据文件[{}]失败=======", this.getDataInfo().getFileName(), e);
            IDSBatchServiceListener batchServiceListener = this.getApplicationContext().getBean(IDSBatchServiceListener.class);
            batchServiceListener.reportStatus(this.getExecuteId(), 1, "生成日终GP对账的数据文件[" + this.getDataInfo().getFileName() + "]失败，原因:[" + e.getMessage() + "]");
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
    public static GPCheckDataCallable bulid(ApplicationContext applicationContext, String executeId, DataInfo<Order> dataInfo, CheckInfo checkInfo) {
        return new GPCheckDataCallable(applicationContext, executeId, dataInfo, checkInfo);
    }
}
