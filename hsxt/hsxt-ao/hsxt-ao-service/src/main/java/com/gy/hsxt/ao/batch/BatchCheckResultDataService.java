package com.gy.hsxt.ao.batch;

import com.gy.hsxt.ao.interfaces.IBatchCheckResultService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 终端设备批结算数据老化调度
 *
 * @Package : com.gy.hsxt.ao.file.callable.subcall
 * @ClassName : AccountingDataCallable
 * @Description : 终端设备批结算数据老化调度
 * @Author : ksl
 * @Date : 2016/2/29 10:53
 * @Version V3.0.0.0
 */
@Service
public class BatchCheckResultDataService extends AbstractBatchService {

    /**
     * 业务接口
     */
    @Resource
    private IBatchCheckResultService batchCheckResultService;


    /**
     * 业务执行方法
     *
     * @param executeId 任务执行编号
     * @param scanDate  扫描日期
     * @throws Exception
     */
    @Override
    public void execute(String executeId, String scanDate) throws Exception {
        this.getLogger().debug("=====终端设备批结算数据老化=====");

        batchCheckResultService.batchDataTransfer();

        this.getBatchServiceListener().reportStatus(executeId, 0, "执行成功");
    }
}
