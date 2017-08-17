package com.gy.hsxt.ao.batch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gy.hsxt.ao.mapper.AccountingHisMapper;
import com.gy.hsxt.ao.mapper.AccountingMapper;

/**
 * 记账分解数据迁移调度
 * 
 * @Package : com.gy.hsxt.ao.file.callable.subcall
 * @ClassName : AccountingDataCallable
 * @Description : 记账分解数据迁移调度(老化记账分解数据)
 * @Author : ksl
 * @Date : 2016/2/29 10:53
 * @Version V3.0.0.0
 */
@Service
public class BatchAccountingDataService extends AbstractBatchService {

    @Autowired
    AccountingHisMapper accountingHisMapper;

    @Autowired
    AccountingMapper accountingMapper;

    /**
     * 业务执行方法
     * 
     * @param executeId
     *            任务执行编号
     * @param scanDate
     *            扫描日期
     * @throws Exception
     */
    @Override
    @Transactional
    public void execute(String executeId, String scanDate) throws Exception {
        this.getLogger().debug("老化记账分解数据，前几个{}月的数据迁移失败", this.getAoConfig().getDataTransfer());

        accountingHisMapper.insertAccountingHisLastMath(this.getAoConfig().getDataTransfer());

        accountingMapper.deleteAccountingLastMath(this.getAoConfig().getDataTransfer());

        this.getBatchServiceListener().reportStatus(executeId, 0, "执行成功");
    }
}
