/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */

package com.gy.hsxt.uc.batch.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsi.ds.api.IDSBatchService;
import com.gy.hsi.ds.api.IDSBatchServiceListener;
import com.gy.hsi.ds.common.contants.DSContants.DSTaskStatus;
import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsi.redis.client.api.RedisUtil;
import com.gy.hsxt.common.utils.StringUtils;
import com.gy.hsxt.uc.as.api.util.UcCacheKey;
import com.gy.hsxt.uc.batch.mapper.BatchMapper;

/**
 * 定时调度-更新企业的是否欠年费状态.计划每天执行一次。
 * 
 * 
 * @Package: com.gy.hsxt.uc.batch.service
 * @ClassName: EntIsOweFeeUpdateBatchService
 * @Description: TODO
 * 
 * @author: lvyan
 * @date: 2015-10-21 上午10:27:45
 * @version V1.0
 */
@Service
public class EntUpdateTaxRateBatchService implements IDSBatchService {

	@Autowired
	BatchMapper batchMapper;

	@Resource
	RedisUtil fixRedisUtil;

	@Resource
	RedisUtil changeRedisUtil;

	@Autowired
	private IDSBatchServiceListener listener;

	String executeId;

	public void addListener(IDSBatchServiceListener listener) {
		this.listener = listener;
	}

	private void reportStatus(int status, String desc) {
		logInfo(executeId+" report status to scheduler center ,status: ----" + status
				+ " desc:--" + desc);
		if(listener!=null){
			listener.reportStatus(executeId,status, desc);
		}else{
			logInfo(executeId+" listener==null " );
		}
	}

	@Override
	public boolean excuteBatch(String executeId, Map<String, String> args) {
		// 发送进度通知
		reportStatus(DSTaskStatus.HANDLING, "执行中");
		this.executeId = executeId;
		try {
			// 更新企业税率
			batchMapper.updateEntTaxRate();
			// 更新企业税率缓存
			List<Map<String, Object>> list = batchMapper
					.selectUpdateEntTaxRate();
			if (list != null && !list.isEmpty()) {
				HashMap<String, Object> valus = new HashMap<>();
				Object entResNo = null;
				Object entTaxTate = null;
				Object entCust = null;
				String entCustId = null;
				String objKey;
				for (Map<String, Object> m : list) {
					// ENT_CUST_ID
					entResNo = m.get("ENT_RES_NO");
					entTaxTate = m.get("ENT_TAX_RATE");
					entCust = m.get("ENT_CUST_ID");
					if (!StringUtils.isBlank(entResNo)
							&& !StringUtils.isBlank(entTaxTate)) {
						valus.put(entResNo.toString(), entTaxTate.toString());

						// 清除企业缓存信息
						entCustId = String.valueOf(entCust);
						objKey = UcCacheKey.genStatusInfoKey(entCustId);
						changeRedisUtil.remove(objKey); // 清除企业状态缓存
						objKey = UcCacheKey.genExtendEntInfoKey(entCustId);
						fixRedisUtil.remove(objKey); // 清除企业扩展信息缓存

					}

				}
				if (!valus.isEmpty()) {
					String key = UcCacheKey.genEntTaxRate();// "UC.E_RES_TAXRATE";
					fixRedisUtil.redisTemplate.opsForHash().putAll(key, valus);
				}
			}
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
		SystemLog.info("hxst-uc-batch", "EntUpdateTaxRateBatchService", msg);
	}

	public void logError(String msg, Exception e) {
		SystemLog
				.error("hxst-uc-batch", "EntUpdateTaxRateBatchService", msg, e);
	}

}
