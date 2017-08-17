/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.ps.settlement.service;

import com.gy.hsi.ds.api.IDSBatchService;
import com.gy.hsi.ds.api.IDSBatchServiceListener;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.ps.common.PsException;
import com.gy.hsxt.ps.common.PsTools;
import com.gy.hsxt.ps.distribution.service.PointAllocService;
import com.gy.hsxt.ps.settlement.handle.PosSettleHandle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @Package: com.gy.hsxt.es.distribution.service
 * @ClassName: DayEndStatement
 * @Description: 定时任务实现类
 * 
 * @author: chenhongzhi
 * @date: 2015-11-18 下午6:01:34
 * @version V3.0
 */
@Service
public class PointTaxBatchJobService implements IDSBatchService
{

	// 回调监听类
	@Autowired
	private IDSBatchServiceListener listener;

	// 积分分配服务(积分、税收)
	@Autowired
	private PointAllocService pointAllocService;

	/**
	 * @param args
	 * @return @
	 */
	@Override
	public boolean excuteBatch(String executeId, Map<String, String> args)
	{
		boolean result = true;
		if (!PsTools.isEmpty(listener))
		{
			// 发送进度通知
			listener.reportStatus(executeId, 2, "执行中");

			try
			{
				// 积分税收
				pointAllocService.pointTax(PosSettleHandle.getJobDate(args));
			} catch (Exception e)
			{
				// 发送进度通知
				listener.reportStatus(executeId, 1, e.toString());
				result = false;
				// 抛出 异常
				PsException.psThrowException(new Throwable().getStackTrace()[0],
						RespCode.PS_POINT_ALLOC_BATCH_JOB_ERROR.getCode(), e.getMessage(),e);
			}
			// 发送进度通知
			listener.reportStatus(executeId, 0, "税收执行成功");
		}
		return result;
	}

}
