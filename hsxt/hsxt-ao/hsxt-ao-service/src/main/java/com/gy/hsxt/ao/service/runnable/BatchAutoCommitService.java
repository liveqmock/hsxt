/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.ao.service.runnable;

import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsi.ds.api.IDSBatchServiceListener;
import com.gy.hsi.lc.client.log4j.BizLog;
import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.ao.AOErrorCode;
import com.gy.hsxt.ao.disconf.AoConfig;
import com.gy.hsxt.ao.enumtype.CommitType;
import com.gy.hsxt.ao.interfaces.IBatchAutoCommitService;
import com.gy.hsxt.ao.interfaces.ITransOutService;
import com.gy.hsxt.ao.mapper.TransOutMapper;
import com.gy.hsxt.common.exception.HsException;

/**
 * 批量自动提交转账定时任务实现类
 * 
 * @Package: com.gy.hsxt.ao.service.runnable
 * @ClassName: BatchAutoCommitService
 * @Description: TODO
 * 
 * @author: kongsl
 * @date: 2016-1-18 下午6:15:55
 * @version V3.0.0
 */
@Service
public class BatchAutoCommitService implements IBatchAutoCommitService {
    /**
     * 调度监听器
     */
    @Autowired
    private IDSBatchServiceListener batchServiceListener;

    /**
     * 银行转账mapper
     */
    @Resource
    private TransOutMapper transOutMapper;

    /**
     * 银行转账内部接口
     */
    @Resource
    private ITransOutService transOutService;

    /**
     * 帐户操作私有配置参数
     **/
    @Resource
    AoConfig aoConfig;

    @Override
    public boolean excuteBatch(String executeId, Map<String, String> arg0) {
        BizLog.biz(this.getClass().getName(), "method:" + Thread.currentThread().getStackTrace()[1].getMethodName(),
                "params[" + arg0 + "]", "帐户操作系统：定时扫描批量自动转账");
        // 设置执行状态
        batchServiceListener.reportStatus(executeId, 2, "帐户操作系统：开始扫描批量自动转账");
        try
        {
            // 批量自动提交转账时间
            int batchCommitTime = aoConfig.getBatchAutoTransCommitTime();
            // 获取自动批量转账的列表
            Set<String> transOutList = transOutMapper.findBatchAutoCommit(batchCommitTime * 60);
            if (transOutList != null && transOutList.size() > 0)
            {
                transOutService.transBatch(transOutList, CommitType.BATCH_AUTO.getCode(), "", "", "批量自动提交");
            }
            // 返回调度状态
            batchServiceListener.reportStatus(executeId, 0, "帐户操作系统：定时扫描批量自动转账成功");
        }
        catch (HsException e)
        {
            SystemLog.error(this.getClass().getName(), "method:"
                    + Thread.currentThread().getStackTrace()[1].getMethodName(), e.getMessage(), e);
            batchServiceListener.reportStatus(executeId, 1, "帐户操作系统：执行失败" + e.getErrorCode() + ":" + e.getMessage());
            throw e;
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), "method:"
                    + Thread.currentThread().getStackTrace()[1].getMethodName(),
                    AOErrorCode.AO_BATCH_AUTO_TRANS_COMMIT_ERROR.getCode() + ":帐户操作系统：定时扫描批量自动转账异常", e);
            batchServiceListener.reportStatus(executeId, 1, "帐户操作系统：执行失败" + e.getMessage());
            throw new HsException(AOErrorCode.AO_BATCH_AUTO_TRANS_COMMIT_ERROR.getCode(), "帐户操作系统：定时扫描批量自动转账异常" + e);
        }
        return true;
    }
}
