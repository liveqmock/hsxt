package com.gy.hsxt.ao.batch;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.gy.hsxt.ao.bean.AccountingEntry;
import com.gy.hsxt.ao.disconf.AoConfig;
import com.gy.hsxt.ao.file.bean.CheckInfo;
import com.gy.hsxt.ao.file.bean.DataInfo;
import com.gy.hsxt.ao.file.callable.CheckFileCallable;
import com.gy.hsxt.ao.file.callable.subcall.AccountingDataCallable;
import com.gy.hsxt.ao.mapper.AccountingMapper;
import com.gy.hsxt.common.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 日终生成AO-AC记账对账文件调度
 *
 * @Package : com.gy.hsxt.ao.file.callable.subcall
 * @ClassName : AccountingDataCallable
 * @Description :  日终生成AO-AC记账对账文件调度
 * @Author : ksl
 * @Date : 2016/2/29 10:53
 * @Version V3.0.0.0
 */
@Service
public class BatchAccountingFileService extends AbstractBatchService {

    /**
     * 记账分解mapper
     */
    @Autowired
    AccountingMapper accountingMapper;

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
        // 校验文件名
        String checkFileName = AoConfig.AO_AC_PREFIX + scanDate + AoConfig.FILE_CHECK_TAIL;
        // 文件夹路径
        String dirPath = this.getAoConfig().joinPreDirPath(scanDate);
        // 每个文件最大交易数
        int maxLineNum = this.getAoConfig().getSumRow();

        List<String> transNoList = accountingMapper.findTransNoByFiscalDate(scan, DateUtil.addDays(scan, 1));

        int count = transNoList.size();// 交易数量

        int fileNum = (count + maxLineNum - 1) / maxLineNum;// 计算要生成多少个文档

        CheckInfo checkInfo = CheckInfo.bulid(fileNum, dirPath, checkFileName).setNeed(true);//校验文件信息

        CheckFileCallable checkFileCallable = CheckFileCallable.bulid(this.getApplicationContext(), executeId, checkInfo);//构建校验文件回调

        if (CollectionUtils.isNotEmpty(transNoList)) {
            for (int i = fileNum; i > 0; i--) {
                int startNo = (i - 1) * maxLineNum;// 业务编号段 开始
                int endNo = (i * maxLineNum) - 1;// 业务编号段 结束
                // 判断结束段大于等于总记录数 则开始段和结束段重新赋值
                if ((i * maxLineNum) >= count) {
                    endNo = count - 1;
                }
                List<AccountingEntry> data = accountingMapper.findAccountingEntryRange(transNoList.get(startNo), transNoList.get(endNo), scan, DateUtil.addDays(scan, 1));
                // 文件名称
                String txtName = AoConfig.AO_AC_PREFIX + scanDate + "_DET_" + i;

                DataInfo<AccountingEntry> dataInfo = DataInfo.bulid(dirPath, txtName, data);//数据文件信息
                //网银购买互生币回调
                AccountingDataCallable accountingDataCallable = AccountingDataCallable.bulid(this.getApplicationContext(), executeId, dataInfo, checkInfo);
                //执行回调
                this.getThreadPoolTaskExecutorSupport().submit(checkFileCallable, accountingDataCallable);
            }
        } else {
            this.getThreadPoolTaskExecutorSupport().execute(checkFileCallable);
        }
    }
}
