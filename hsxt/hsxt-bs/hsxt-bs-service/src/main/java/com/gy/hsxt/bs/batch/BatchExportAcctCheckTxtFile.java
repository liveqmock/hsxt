/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO.,
 * LTD. All rights reserved.
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.bs.batch;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.gy.hsxt.bs.bean.order.AccountDetail;
import com.gy.hsxt.bs.file.bean.CheckInfo;
import com.gy.hsxt.bs.file.bean.DataInfo;
import com.gy.hsxt.bs.file.callable.CheckFileCallable;
import com.gy.hsxt.bs.file.callable.subcall.AcctCheckDataCallable;
import com.gy.hsxt.bs.order.bean.AccountDetailQuery;
import com.gy.hsxt.bs.order.mapper.AccountDetailMapper;
import com.gy.hsxt.common.utils.DateUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.util.Date;
import java.util.List;

/**
 * 日终账务对账文件调度
 *
 * @Package : com.gy.hsxt.bs.batch
 * @ClassName : BatchExportAcctCheckTxtFile
 * @Description :日终账务对账文件调度
 * @Author : chenm
 * @Date : 2015-10-23 下午5:27:06
 * @Version V3.0
 */
@Service
public class BatchExportAcctCheckTxtFile extends AbstractBatchService {

    /**
     * 注入 记帐分解 Mapper接口
     */
    @Resource
    AccountDetailMapper accountDetailMapper;

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
        //最大行数
        int maxLineNum = this.getFileConfig().getMaxLineNum();

        // 读取 所有未记帐的记录 按业务编号分组去重
        AccountDetailQuery query = new AccountDetailQuery();
        //扫描日期
        Date scan = DateUtil.StringToDate(scanDate, DateUtil.DATE_FORMAT);
        query.setStartDate(DateUtil.DateToString(scan));
        query.setEndDate(DateUtil.addDaysStr(scan, 1));

        List<String> bizNos = accountDetailMapper.selectBizNosByQuery(query);

        int count = (bizNos == null) ? 0 : bizNos.size();// // 获取未记帐的总记录数

        int fileNum = (count + maxLineNum - 1) / maxLineNum;// 计算要生成多少个文档

        CheckInfo checkInfo = CheckInfo.bulid(fileNum, dirPath, "BS_AC_" + scanDate + "_CHK").setNeed(true);

        CheckFileCallable checkFileCallable = CheckFileCallable.bulid(this.getApplicationContext(), executeId, checkInfo);

        // 有数据才进行数据文件生成操作
        if (CollectionUtils.isNotEmpty(bizNos)) {
            for (int i = fileNum; i > 0; i--) {
                int startNo = (i - 1) * maxLineNum;// 业务编号段 开始
                int endNo = (i * maxLineNum) - 1;// 业务编号段 结束
                // 判断结束段大于等于总记录数 则开始段和结束段重新赋值
                if ((i * maxLineNum) >= count) {
                    endNo = count - 1;
                }

                // 读取未记帐的的分段记录
                query.setStartBizNo(bizNos.get(startNo));
                query.setEndBizNo(bizNos.get(endNo));
                //分段查询
                List<AccountDetail> list = this.accountDetailMapper.selectBeanListByQuery(query);

                String txtName = "BS_AC_" + scanDate + "_DET_" + i;//数据文件名称

                DataInfo<AccountDetail> dataInfo = DataInfo.bulid(dirPath, txtName, list);

                AcctCheckDataCallable acctCheckDataCallable = AcctCheckDataCallable.bulid(this.getApplicationContext(), executeId, dataInfo, checkInfo);

                this.getThreadPoolTaskExecutorSupport().submit(checkFileCallable, acctCheckDataCallable);
            }
        } else {
            //没有数据文件，只生成check文件
            this.getThreadPoolTaskExecutorSupport().execute(checkFileCallable);
        }
    }
}
