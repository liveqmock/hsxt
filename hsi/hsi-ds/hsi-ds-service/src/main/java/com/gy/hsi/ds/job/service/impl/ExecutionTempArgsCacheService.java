/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsi.ds.job.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsi.common.utils.StringUtils;
import com.gy.hsi.ds.common.constant.JobConstant.ExecuteMethod;
import com.gy.hsi.ds.common.constant.JobConstant.StrValue;
import com.gy.hsi.ds.common.constant.JobConstant.TempArgsIndex;
import com.gy.hsi.ds.common.constant.JobConstant.TempArgsKey;
import com.gy.hsi.ds.common.utils.ValuesUtils;
import com.gy.hsi.ds.job.beans.bo.JobTempArgs;
import com.gy.hsi.ds.job.dao.IJobTempArgsDao;
import com.gy.hsi.ds.job.service.IExecutionTempArgsCacheService;

@Service("executionTempArgsCacheService")
public class ExecutionTempArgsCacheService implements
		IExecutionTempArgsCacheService {

	private static final Map<Integer, String> KEY_MAP = new HashMap<Integer, String>(
			3);
	private static final Map<Integer, String> DEFAULT_VALUE_MAP = new HashMap<Integer, String>(
			3);

	static {
		KEY_MAP.put(TempArgsIndex.INDEX_IS_MANUAL, TempArgsKey.KEY_IS_MANUAL); // 0
		KEY_MAP.put(TempArgsIndex.INDEX_IGNORE_FRONT, TempArgsKey.KEY_IGNORE_FRONT); // 1
		KEY_MAP.put(TempArgsIndex.INDEX_RECUR_POST, TempArgsKey.KEY_RECUR_POST); // 2

		DEFAULT_VALUE_MAP.put(TempArgsIndex.INDEX_IS_MANUAL, StrValue.FALSE); // 0
		DEFAULT_VALUE_MAP.put(TempArgsIndex.INDEX_IGNORE_FRONT, StrValue.FALSE); // 1
		DEFAULT_VALUE_MAP.put(TempArgsIndex.INDEX_RECUR_POST, StrValue.TRUE); // 2
	}

	@Autowired
	private IJobTempArgsDao jobTempArgsDao;

	/**
	 * 清理过期的bean
	 * 
	 * @param executeId
	 * @param serviceName
	 * @param tempArgs
	 */
	public void saveTempArgsCache(String executeId, String serviceName,
			Map<String, String> tempArgs) {

		// 如果是手工执行, 只有第一次递归才记录缓存
		if (!executeId.contains("#")) {
			// 缓存参数
			insertTempArgsInDB(executeId, serviceName, tempArgs);
		}
	}

	/**
	 * 清理过期的临时参数
	 */
	public void cleanOldTempArgsCache() {
		// 删除两天前的数据
		jobTempArgsDao.deleteDataByDate(2);
	}

	/**
	 * 按执行id清理临时参数
	 * 
	 * @param executeId
	 */
	@Override
	public void cleanTempArgsCacheByExecuteId(String executeId) {
		
		// 不清理手工执行的记录
		if(isManual(executeId)) {
			return;
		}
		
		String sameBatchExecuteId = executeId;

		// 去掉井号
		if (executeId.contains("#")) {
			sameBatchExecuteId = executeId.substring(0,
					executeId.lastIndexOf('#'));
		}

		jobTempArgsDao.deleteByExecuteId(sameBatchExecuteId);
	}

	/**
	 * 获取同一批次运行的业务缓存
	 * 
	 * @param executeId
	 * @return
	 */
	public JobTempArgs getTempArgs(String executeId) {

		String sameBatchExecuteId = executeId;

		// 去掉井号
		if (executeId.contains("#")) {
			sameBatchExecuteId = executeId.substring(0,
					executeId.lastIndexOf('#'));
		}

		return jobTempArgsDao.getByExecuteId(sameBatchExecuteId);
	}

	/**
	 * 临时传递的参数
	 * 
	 * @param tempArgs
	 * @return
	 */
	public String parseExecuteIdPrefix(Map<String, String> tempArgs) {
		String key = null;
		String value = null;

		StringBuilder buf = new StringBuilder();

		for (int i = 0; i < KEY_MAP.size(); i++) {
			key = KEY_MAP.get(i);

			if (null != tempArgs) {
				value = tempArgs.get(key);

				if (!StringUtils.isEmpty(value)) {
					buf.append(value);
				} else {
					buf.append(DEFAULT_VALUE_MAP.get(i));
				}
			} else {
				buf.append(DEFAULT_VALUE_MAP.get(i));
			}
		}

		return buf.toString();
	}

	/**
	 * 判断是否为手工
	 * 
	 * @param executeId
	 * @return
	 */
	public boolean isManual(String executeId) {
		if (StringUtils.isEmpty(executeId) || (5 > executeId.length())) {
			return false;
		}

		String c = String.valueOf(executeId
				.charAt(TempArgsIndex.INDEX_IS_MANUAL));

		if (StrValue.TRUE.equals(c)) {
			return true;
		}

		return false;
	}

	/**
	 * 判断是否忽略前置业务
	 * 
	 * @param executeId
	 * @return
	 */
	public boolean isIgnoreFront(String executeId) {
		if (StringUtils.isEmpty(executeId) || (5 > executeId.length())) {
			return false;
		}

		String c = String.valueOf(executeId
				.charAt(TempArgsIndex.INDEX_IGNORE_FRONT));

		if (StrValue.TRUE.equals(c)) {
			return true;
		}

		return false;
	}

	/**
	 * 判断是否触发后置业务
	 * 
	 * @param executeId
	 * @return
	 */
	public boolean isRecurPost(String executeId) {

		if (StringUtils.isEmpty(executeId) || (5 > executeId.length())) {
			return false;
		}

		String c = String.valueOf(executeId
				.charAt(TempArgsIndex.INDEX_RECUR_POST));

		if (StrValue.TRUE.equals(c)) {
			return true;
		}

		return false;
	}

	/**
	 * 向T_DS_JOB_TEMP_ARGS表中插入临时参数记录
	 * 
	 * @param executeId
	 * @param serviceName
	 * @param tempArgsMap
	 */
	private void insertTempArgsInDB(String executeId, String serviceName,
			Map<String, String> tempArgsMap) {

		if ((StringUtils.isExistEmpty(executeId, serviceName))
				|| (null == tempArgsMap) || (0 >= tempArgsMap.size())) {
			return;
		}

		String[] ignoreKeys = new String[] { TempArgsKey.KEY_DS_BATCH_DATE };

		String tempArgsKeyValues = ValuesUtils.formatMap2KeyValueStr(
				tempArgsMap, ignoreKeys, false);

		String tempArgsKeys = ValuesUtils.formatMap2KeyValueStr(tempArgsMap,
				ignoreKeys, true);

		String batchDateValue = StringUtils.avoidNull(tempArgsMap
				.get(TempArgsKey.KEY_DS_BATCH_DATE));

		StringBuilder tempArgsBuf = new StringBuilder(tempArgsKeyValues);
		StringBuilder tempArgsKeysBuf = new StringBuilder(tempArgsKeys);

		// 将BATCH_DATE放到字串的开头
		tempArgsBuf.insert(0, StringUtils.joinString(
				TempArgsKey.KEY_DS_BATCH_DATE, "=", batchDateValue, ";"));

		tempArgsKeysBuf.insert(0,
				StringUtils.joinString(TempArgsKey.KEY_DS_BATCH_DATE, "=;"));

		int executeMethod = isManual(executeId) ? ExecuteMethod.MANUAL
				: ExecuteMethod.AUTO;

		jobTempArgsDao.create(executeId, serviceName, tempArgsBuf.toString(),
				tempArgsKeysBuf.toString(), executeMethod);
	}
}
