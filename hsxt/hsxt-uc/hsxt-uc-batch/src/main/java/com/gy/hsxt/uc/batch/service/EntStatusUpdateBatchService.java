/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */

package com.gy.hsxt.uc.batch.service;

import static com.gy.hsxt.common.utils.StringUtils.isBlank;

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
 * 定时调度-更新沉默企业的状态.计划每周执行一次。 定时任务启动后读取账务系统提供的积分变动数据文件， 多线程读取数据文件，每个文件对应一个线程；
 * 根据积分时间更新沉默企业的状态，1个月未送积分进入预警；成员企业1年进入休眠，2年进入长眠
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
public class EntStatusUpdateBatchService implements IDSBatchService {

	@Autowired
	BatchMapper batchMapper;
	@Autowired
	private IDSBatchServiceListener listener;
	String executeId;
	public void addListener(IDSBatchServiceListener listener) {
		this.listener = listener;
	}

	private void reportStatus(int status, String desc) {
		logInfo(executeId+" report status to scheduler center ,status: ----" + status + " desc:--" + desc);
		if(listener!=null){
			listener.reportStatus(executeId,status, desc);
		}else{
			logInfo(executeId+" listener==null " );
		}
	}

	/**
	 * 
	 * @param args
	 *            need
	 *            keys:warnMonths=预警状态判断月数,sleepMonths=休眠状态判断月数,silentMonths
	 *            =长眠状态判断月数
	 * @return
	 * @see com.gy.hsi.ds.api.IDSBatchService#excuteBatch(java.util.Map)
	 */
	public boolean excuteBatch(String executeId,Map<String, String> args) {
		// 发送进度通知
		reportStatus(DSTaskStatus.HANDLING, "执行中");
		this.executeId=executeId;
		String def_warnMonths = "1"; // 预警状态判断月数
		String def_sleepMonths = "12";// 休眠状态判断月数
		String def_silentMonths = "24";// 长眠状态判断月数

		try {
			// 获取调度系统传入参数
			String warnMonths = HsPropertiesConfigurer.getProperty("uc.batch.ent.warnMonths");// 预警状态判断月数
			String sleepMonths = HsPropertiesConfigurer.getProperty("uc.batch.ent.sleepMonths");// 休眠状态判断月数
			String silentMonths = HsPropertiesConfigurer.getProperty("uc.batch.ent.silentMonths");// 长眠状态判断月数
			if (isBlank(warnMonths)) {
				warnMonths = def_warnMonths;

			}
			if (isBlank(sleepMonths)) {
				sleepMonths = def_sleepMonths;
			}
			if (isBlank(silentMonths)) {
				silentMonths = def_silentMonths;
			}
			logInfo("warnMonths:" + warnMonths + " sleepMonths:" + sleepMonths + " silentMonths:--"
					+ silentMonths);

			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("warnMonths", warnMonths);
			map.put("sleepMonths", sleepMonths);
			map.put("silentMonths", silentMonths);

			// 根据 预设的月数，更新企业 的状态
			batchMapper.updateEntStatus(map);

			// 发送进度通知
			reportStatus(DSTaskStatus.SUCCESS, "执行成功");
			return true;
		} catch (Exception e) {
			logError("执行失败", e);
			reportStatus(DSTaskStatus.FAILED, "执行失败");
			return false;
		}

	}

	public void logInfo(String msg) {
		SystemLog.info("hxst-uc-batch", "EntStatusUpdateBatchService", msg);
	}

	public void logError(String msg, Exception e) {
		SystemLog.error("hxst-uc-batch", "EntStatusUpdateBatchService", msg, e);
	}

}
