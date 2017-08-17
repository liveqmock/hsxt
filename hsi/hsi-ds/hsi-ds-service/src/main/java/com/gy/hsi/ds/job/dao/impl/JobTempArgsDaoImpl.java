/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsi.ds.job.dao.impl;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.gy.hsi.ds.common.constant.Columns;
import com.gy.hsi.ds.common.constant.JobConstant.ExecuteMethod;
import com.gy.hsi.ds.common.thirds.dsp.common.dao.AbstractDao;
import com.gy.hsi.ds.common.thirds.unbiz.common.genericdao.operator.Match;
import com.gy.hsi.ds.common.thirds.unbiz.common.genericdao.param.AppenderParam;
import com.gy.hsi.ds.common.thirds.unbiz.common.genericdao.param.LteParam;
import com.gy.hsi.ds.common.utils.DateUtils;
import com.gy.hsi.ds.job.beans.bo.JobTempArgs;
import com.gy.hsi.ds.job.dao.IJobTempArgsDao;

@Service
public class JobTempArgsDaoImpl extends AbstractDao<Long, JobTempArgs>
		implements Serializable, IJobTempArgsDao {

	private static final long serialVersionUID = 8722940972362203947L;

	@Override
	public JobTempArgs getManualTempArgsByServiceName(String serviceName) {
		AppenderParam p = new AppenderParam(
				" ORDER BY CREATE_DATE DESC LIMIT 0, 1");

		List<JobTempArgs> list = find(new Match(Columns.SERVICE_NAME,
				serviceName), new Match(Columns.EXECUTE_METHOD,
				ExecuteMethod.MANUAL), new Match(Columns.CREATE_DATE, p));

		if ((null != list) && (0 < list.size())) {
			return list.get(0);
		}

		return null;
	}

	@Override
	public void create(String executeId, String serviceName, String tempArgs,
			String tempArgsKeys, int executeMethod) {
		JobTempArgs entity = new JobTempArgs();
		entity.setServiceName(serviceName);
		entity.setTempArgs(tempArgs);
		entity.setTempArgsKeys(tempArgsKeys);
		entity.setExecuteId(executeId);
		entity.setExecuteMethod(executeMethod);
		entity.setCreateDate(new Date());

		create(entity);
	}

	@Override
	public JobTempArgs getByExecuteId(String executeId) {
		return findOne(new Match(Columns.EXECUTE_ID, executeId));
	}

	@Override
	public void deleteDataByDate(int daysBeforeToday) {
		Date date = DateUtils.getDateBeforeDays(daysBeforeToday);
		String strDate = DateUtils.getDateByformat(date, "yyyy-MM-dd HH:mm:ss");

		delete(new Match(Columns.EXECUTE_METHOD, ExecuteMethod.AUTO),
				new Match(Columns.CREATE_DATE, new LteParam(strDate)));
	}

	@Override
	public void deleteByExecuteId(String executeId) {
		delete(new Match(Columns.EXECUTE_ID, executeId));
	}
}