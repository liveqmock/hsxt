/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsi.ds.job.dao;

import com.gy.hsi.ds.common.thirds.unbiz.common.genericdao.dao.BaseDao;
import com.gy.hsi.ds.job.beans.bo.JobTempArgs;

public interface IJobTempArgsDao extends BaseDao<Long, JobTempArgs> {

	public JobTempArgs getManualTempArgsByServiceName(String serviceName);

	public JobTempArgs getByExecuteId(String executeId);
	
	public void create(String executeId, String serviceName, String tempArgs,
			String tempArgsKeys, int executeMethod);

	public void deleteDataByDate(int daysBeforeToday);
	
	public void deleteByExecuteId(String executeId);
}
