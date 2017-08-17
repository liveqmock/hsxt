package com.gy.hsi.ds.job.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.gy.hsi.ds.common.constant.Columns;
import com.gy.hsi.ds.common.thirds.dsp.common.dao.AbstractDao;
import com.gy.hsi.ds.common.thirds.unbiz.common.genericdao.operator.Match;
import com.gy.hsi.ds.common.thirds.unbiz.common.genericdao.operator.Modify;
import com.gy.hsi.ds.common.thirds.unbiz.common.genericdao.param.LteParam;
import com.gy.hsi.ds.common.utils.DateUtils;
import com.gy.hsi.ds.job.beans.bo.JobCounter;
import com.gy.hsi.ds.job.dao.IJobCounterDao;

@Service
public class JobCounterDaoImpl extends AbstractDao<Long, JobCounter> implements
		Serializable, IJobCounterDao {

	private static final long serialVersionUID = 4420826570360806323L;

	@Override
	public JobCounter getCounter(String executeId, String instNo) {
		try {
			return findOne(new Match(Columns.EXECUTE_ID, executeId), new Match(
					Columns.INST_NO, instNo));
		} catch (Exception e) {
		}

		return null;
	}

	@Override
	public int updateCounter(String executeId, String instNo, int newValue) {
		List<Modify> modifyList = new ArrayList<Modify>();
		modifyList.add(modify(Columns.COUNT_VALUE, newValue));
		modifyList.add(modify(Columns.UPDATE_DATE, new Date()));

		return update(modifyList, match(Columns.EXECUTE_ID, executeId),
				match(Columns.INST_NO, instNo));
	}

	@Override
	public JobCounter insertCounter(String executeId, String instNo,
			int newValue) {
		Date date = new Date();

		JobCounter entity = new JobCounter();
		entity.setInstNo(instNo);
		entity.setExecuteId(executeId);
		entity.setCountValue(newValue);
		entity.setUpdateDate(date);
		entity.setCreateDate(date);

		return create(entity);
	}

	@Override
	public void deleteDataByDate(int daysBeforeToday) {
		Date date = DateUtils.getDateBeforeDays(daysBeforeToday);
		String strDate = DateUtils.getDateByformat(date, "yyyy-MM-dd HH:mm:ss");

		delete(new Match(Columns.UPDATE_DATE, new LteParam(strDate)));
	}

}
