package com.gy.hsxt.ps.settlement.service;

import com.gy.hsi.ds.api.IDSBatchService;
import com.gy.hsi.ds.api.IDSBatchServiceListener;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.ps.common.PsException;
import com.gy.hsxt.ps.common.PsTools;
import com.gy.hsxt.ps.reconciliation.service.ReconciliationService;
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
public class ReconciliationJobService implements IDSBatchService
{
	// 回调监听类
	@Autowired
	private IDSBatchServiceListener listener;

	// 日终与账务对账
	@Autowired
	private ReconciliationService reconciliationService;

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
				reconciliationService.balanceAccount(PosSettleHandle.getJobDate(args));

			} catch (Exception e)
			{
				// 发送进度通知
				listener.reportStatus(executeId, 1, "执行失败");
				result = false;
				// 抛出 异常
				PsException.psHsThrowException(new Throwable().getStackTrace()[0],
						RespCode.PS_POINT_ALLOC_BATCH_JOB_ERROR.getCode(), e.getMessage());
			}
			// 发送进度通知
			listener.reportStatus(executeId, 0, "积分分配执行成功");
		}
		return result;
	}

}
