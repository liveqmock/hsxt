/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */

package com.gy.hsxt.uc.batch.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsi.ds.api.IDSBatchService;
import com.gy.hsi.ds.api.IDSBatchServiceListener;
import com.gy.hsi.ds.common.contants.DSContants.DSTaskStatus;
import com.gy.hsi.ds.param.HsPropertiesConfigurer;
import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.uc.batch.mapper.BatchMapper;

/**
 * 定时调度-更新沉默用户的状态.计划每月执行一次。 定时任务启动后读取账务系统提供的积分变动数据文件， 多线程读取数据文件，每个文件对应一个线程；
 * 根据积分时间更新沉默用户的状态，积分时间超过1年进入休眠，3年进入沉淀
 * 
 * @Package: com.gy.hsxt.uc.batch.service
 * @ClassName: CustStatusUpdateBatchService
 * @Description: TODO
 * 
 * @author: lvyan
 * @date: 2015-10-21 上午10:27:45
 * @version V1.0
 */
@Service
public class CustStatusUpdateBatchService implements IDSBatchService {

	@Autowired
	BatchMapper batchMapper;

	/**
	 * 回调监听类
	 */
	@Autowired
	private IDSBatchServiceListener listener;

	String executeId;
	public void addListener(IDSBatchServiceListener listener) {
		this.listener = listener;
	}

	private void reportStatus(int status, String desc) {
		logInfo(executeId+" report status to scheduler center ,status: ----" + status + " desc:--" + desc);
		if(listener!=null){
			listener.reportStatus( executeId,status, desc);
		}else{
			logInfo(executeId+" listener==null " );
		}
	}

	@Override
	/**
	 * 
	 * @param args need keys:sleepMonths=休眠状态判断月数,silentMonths=沉淀状态判断月数
	 * @return 
	 * @see com.gy.hsi.ds.api.IDSBatchService#excuteBatch(java.util.Map)
	 */
	public boolean excuteBatch(String executeId,Map<String, String> args) {
		// 发送进度通知
		reportStatus(DSTaskStatus.HANDLING, "执行中");
		this.executeId=executeId;
		String def_sleepMonths = "12"; // 不活跃状态判断月数
		String def_silentMonths = "36";// 沉淀，休眠状态判断月数

		try {
			// 获取调度系统传入参数,并传递给sql 执行
			String sleepMonths = HsPropertiesConfigurer.getProperty("uc.batch.cust.sleepMonths"); // 不活跃状态判断月数
			String silentMonths = HsPropertiesConfigurer.getProperty("uc.batch.cust.silentMonths");// 沉淀状态判断月数
			if (sleepMonths == null) {
				sleepMonths = def_sleepMonths;
			}
			if (silentMonths == null) {
				silentMonths = def_silentMonths;
			}
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("sleepMonths", sleepMonths);
			map.put("silentMonths", silentMonths);
			logInfo("sleepMonths:" + sleepMonths + " silentMonths:-------" + silentMonths);

			// 根据 预设的月数，更新持卡人 的状态
			batchMapper.updateCustStatus(map);
			// 发送进度通知
			reportStatus(DSTaskStatus.SUCCESS, "执行成功");
			return true;
		} catch (Exception e) {
			logError("执行失败!", e);
			reportStatus(DSTaskStatus.FAILED, "执行失败");
			return false;
		}

	}

	public void logInfo(String msg) {
		SystemLog.info("hxst-uc-batch", "CustStatusUpdateBatchService", msg);
	}

	public void logError(String msg, Exception e) {
		SystemLog.error("hxst-uc-batch", "CustStatusUpdateBatchService", msg, e);
	}

}
