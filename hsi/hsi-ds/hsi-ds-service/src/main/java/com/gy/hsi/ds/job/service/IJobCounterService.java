package com.gy.hsi.ds.job.service;

public interface IJobCounterService {

	/**
	 * 获取并更新计数器
	 * 
	 * @param executeId
	 * @param instNo
	 * @return
	 */
	public int getAndUpdateCounter(String executeId);

	/**
	 * 移除过期数据
	 */
	public void removeInvalidData();
}
