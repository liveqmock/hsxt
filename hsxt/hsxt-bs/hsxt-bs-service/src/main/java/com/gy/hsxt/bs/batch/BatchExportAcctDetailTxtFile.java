/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO.,
 * LTD. All rights reserved.
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.bs.batch;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.gy.hsxt.bs.bean.order.AccountDetail;
import com.gy.hsxt.bs.disconf.FileConfig;
import com.gy.hsxt.bs.file.bean.CheckInfo;
import com.gy.hsxt.bs.file.bean.DataInfo;
import com.gy.hsxt.bs.file.callable.CheckFileCallable;
import com.gy.hsxt.bs.file.callable.subcall.AcctDetailDataCallable;
import com.gy.hsxt.bs.order.bean.AccountDetailQuery;
import com.gy.hsxt.bs.order.mapper.AccountDetailMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 日终记账文件调度
 *
 * @Package : com.gy.hsxt.bs.batch
 * @ClassName : BatchExportAcctDetailTxtFile
 * @Description :日终记账文件调度
 * @Author : liuhq/chenm
 * @Date : 2015-10-23 下午5:27:06
 * @Version V3.0
 */
@Service
public class BatchExportAcctDetailTxtFile extends AbstractBatchService {

    /**
     * 注入 记帐分解 Mapper接口
     */
    @Resource
    private AccountDetailMapper accountDetailMapper;

    /**
     * 业务执行方法
     *
     * @param executeId 任务执行编号
     * @param scanDate  扫描日期
     * @throws Exception
     */
    @Override
    public void execute(String executeId, String scanDate) throws Exception {

        String dirPath = this.getFileConfig().joinPreDirPath(FileConfig.BS_DAY_ACCT,scanDate); // 文件路径
        int maxLineNum = this.getFileConfig().getMaxLineNum();

        // 读取 所有未记帐的记录 按业务编号分组去重
        AccountDetailQuery query = new AccountDetailQuery();
        query.setStatus(false);
        List<String> bizNos = accountDetailMapper.selectBizNosByQuery(query);

        int count = bizNos.size();// // 获取未记帐的总记录数

        int fileNum = (count + maxLineNum - 1) / maxLineNum;// 计算要生成多少个文档

        CheckInfo checkInfo = CheckInfo.bulid(fileNum, dirPath, scanDate + FileConfig.FILE_CHECK_TAIL);

        CheckFileCallable checkFileCallable = CheckFileCallable.bulid(this.getApplicationContext(), executeId, checkInfo);

        // 有数据才进行
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
                List<AccountDetail> data = this.accountDetailMapper.selectBeanListByQuery(query);

                String txtName = scanDate + "_" + i + FileConfig.FILE_SUFFIX;//数据文件名称
                //构建记账数据文件信息
                DataInfo<AccountDetail> dataInfo = DataInfo.bulid(dirPath, txtName, data);
                //构建记账数据文件调度
                AcctDetailDataCallable acctDetailDataCallable = AcctDetailDataCallable.bulid(this.getApplicationContext(), executeId, dataInfo, checkInfo);
                //执行调度
                this.getThreadPoolTaskExecutorSupport().submit(checkFileCallable, acctDetailDataCallable);
            }
        } else {
            //没有数据，只调用check文件信息调度
            this.getThreadPoolTaskExecutorSupport().execute(checkFileCallable);
        }
    }
}
