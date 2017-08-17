/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsi.ds.job.service.impl;

import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsi.ds.common.contants.DSContants;
import com.gy.hsi.ds.common.spring.MyPropertyConfigurer;
import com.gy.hsi.ds.job.beans.bo.JobCounter;
import com.gy.hsi.ds.job.dao.IJobCounterDao;
import com.gy.hsi.ds.job.service.IJobCounterService;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsi-ds-service
 * 
 *  Package Name    : com.gy.hsi.ds.job.service.impl
 * 
 *  File Name       : JobCounterServiceImpl.java
 * 
 *  Creation Date   : 2016年2月22日
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : none
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
@Service("jobCounterService")
public class JobCounterServiceImpl implements IJobCounterService {
	
	private static Logger logger = LoggerFactory
			.getLogger(JobCounterServiceImpl.class);

	@Autowired
	private IJobCounterDao jobCounterDao;

	private static final ReentrantLock lock = new ReentrantLock();

	private String instanceNo = MyPropertyConfigurer
			.getProperty(DSContants.SYS_INST_NO);

	// Transactional(propagation = Propagation.REQUIRED, isolation =
	// Isolation.SERIALIZABLE)
	public int getAndUpdateCounter(String executeId) {

		try {
			lock.lock();

			JobCounter record = jobCounterDao.getCounter(executeId, instanceNo);

			if (null == record) {
				record = jobCounterDao.insertCounter(executeId, instanceNo, 1);
			} else {
				record.setCountValue(record.getCountValue() + 1);

				jobCounterDao.update(record);
			}

			return record.getCountValue();
		} catch (Exception e) {
			logger.error("", e);
		} finally {
			try {
				lock.unlock();
			} catch (IllegalMonitorStateException ex) {
			}
		}

		return new Random().nextInt(10000) + 10000;
	}

	@Override
	public void removeInvalidData() {
		jobCounterDao.deleteDataByDate(3);
	}

}
