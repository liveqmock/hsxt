package com.gy.hsxt.rp.api;

import com.gy.hsxt.common.exception.HsException;

public interface IReportsBatchWelfareScoreFileService {
	
	/**
     * 批生成日积分活动资源列表
     * 
     * @param batchJobName
     *            记账任务名称
     * @param fileAddress
     *            日积分活动资源列表文件生成路径
     * @throws HsException
     * 
     */
    public void welfareScoreFile(String batchJobName, String fileAddress, String batchDate) throws HsException;
}
