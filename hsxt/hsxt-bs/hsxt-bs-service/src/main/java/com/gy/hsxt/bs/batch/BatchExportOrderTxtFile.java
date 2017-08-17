/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.batch;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.gy.hsxt.bs.bean.order.Order;
import com.gy.hsxt.bs.bean.order.OrderQueryParam;
import com.gy.hsxt.bs.file.bean.CheckInfo;
import com.gy.hsxt.bs.file.bean.DataInfo;
import com.gy.hsxt.bs.file.callable.CheckFileCallable;
import com.gy.hsxt.bs.file.callable.subcall.GPCheckDataCallable;
import com.gy.hsxt.bs.order.interfaces.IOrderService;
import com.gy.hsxt.common.utils.DateUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.util.Date;
import java.util.List;

/**
 * 扫描业务订单生成对账文件(BS_GP)
 *
 * @Package : com.gy.hsxt.bs.batch
 * @ClassName : BatchExportOrderTxtFile
 * @Description : 扫描业务订单生成对账文件 业务系统和支付系统对账文件扫描
 * @Author : chenm
 * @Date : 2016/1/7 14:50
 * @Version V3.0.0.0
 */
@Service
public class BatchExportOrderTxtFile extends AbstractBatchService {

    /**
     * 业务订单接口
     */
    @Resource
    private IOrderService orderService;

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
        String dirPath = this.getFileConfig().getDirRoot() + File.separator + "TCAS" + File.separator + this.getFileConfig().getSystemId() + File.separator + scanDate + File.separator; // 文件路径
        //扫描日期
        Date scan = DateUtil.StringToDate(scanDate, DateUtil.DATE_FORMAT);

        int maxLineNum = this.getFileConfig().getMaxLineNum();

        // 读取 所有未记帐的记录 按业务编号分组去重
        OrderQueryParam queryParam = new OrderQueryParam();
        queryParam.setStartDate(DateUtil.DateToString(scan));// 昨天
        queryParam.setEndDate(DateUtil.addDaysStr(scan, 1));// 今天

        List<Order> orders = orderService.queryListForGPByQuery(queryParam);

        int fileNum = (orders.size() + maxLineNum - 1) / maxLineNum;

        CheckInfo checkInfo = CheckInfo.bulid(fileNum, dirPath, "BS_GP_PAY_" + scanDate + "_CHK").setNeed(true);

        CheckFileCallable checkFileCallable = CheckFileCallable.bulid(this.getApplicationContext(), executeId, checkInfo);

        if (CollectionUtils.isNotEmpty(orders)) {
            for (int i = 1; i <= fileNum; i++) {
                int start = (i - 1) * maxLineNum;
                int end = i * maxLineNum;
                if (end > orders.size()) {
                    end = orders.size();
                }
                List<Order> subList = orders.subList(start,end);
                // 文件名称
                String txtName = "BS_GP_PAY_" + scanDate + "_DET_" + i;

                DataInfo<Order> dataInfo = DataInfo.bulid(dirPath, txtName, subList);

                GPCheckDataCallable gpCheckDataCallable = GPCheckDataCallable.bulid(this.getApplicationContext(), executeId, dataInfo, checkInfo);

                this.getThreadPoolTaskExecutorSupport().submit(checkFileCallable, gpCheckDataCallable);
            }
        } else {
            //没有数据文件，只生成check文件
            this.getThreadPoolTaskExecutorSupport().execute(checkFileCallable);
        }
    }

}
