package com.gy.hsi.ds.job.dao;

import com.gy.hsi.ds.common.thirds.unbiz.common.genericdao.dao.BaseDao;
import com.gy.hsi.ds.job.beans.bo.JobCounter;

public interface IJobCounterDao extends BaseDao<Long, JobCounter> {

	/**
	 * 根据基执行id获取计数器,用于编号
	 * 
	 * @param executeId
	 * @param instNo
	 * @return
	 */
	public JobCounter getCounter(String executeId, String instNo);
	
	/**
	 * 更新计数器
	 * 
	 * @param executeId
	 * @param newValue
	 * @return
	 */
	public int updateCounter(String executeId, String instNo, int newValue);
	
	/**
	 * 插入计数器
	 * 
	 * @param executeId
	 * @return
	 */
	public JobCounter insertCounter(String executeId, String instNo, int newValue);
	
	/**
	 * 删除数据
	 * 
	 * @param daysBeforeToday
	 */
	public void deleteDataByDate(int daysBeforeToday);
}
