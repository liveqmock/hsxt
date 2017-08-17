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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsi.common.utils.StringUtils;
import com.gy.hsi.ds.api.IDSExternalTriggerServcie;
import com.gy.hsi.ds.common.constant.JobConstant.StrValue;
import com.gy.hsi.ds.common.constant.JobConstant.TempArgsKey;
import com.gy.hsi.ds.common.utils.DateUtils;
import com.gy.hsi.ds.job.dao.IJobStatusDao;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsi-ds-service
 * 
 *  Package Name    : com.gy.hsi.ds.job.service.impl
 * 
 *  File Name       : DSExternalTriggerServcie.java
 * 
 *  Creation Date   : 2016年2月19日
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 提供给其他子系统触发调度的接口[目前只提供给支付网关使用]
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
@Service("dsExternalTriggerServcie")
public class DSExternalTriggerServcie implements IDSExternalTriggerServcie {

	private static Logger logger = LoggerFactory
			.getLogger(DSExternalTriggerServcie.class);

	@Autowired
	private BusExecuteLogicServcie busExecuteLogicServcie;

	@Autowired
	private IJobStatusDao jobStatusDao;

	@Override
	public void triggerByExternal(String name, String group,
			Map<String, String> externalArgs) {

		if (null == jobStatusDao.getByNameAndGroup(name, group)) {
			logger.info(StringUtils.joinString(
					"Failed to trigger by external: 不存在业务: name=", name,
					", group=" + group));

			return;
		}

		Map<String, String> $externalArgs = externalArgs;

		if (null == $externalArgs) {
			$externalArgs = new HashMap<String, String>(1);
		}

		if (!$externalArgs.containsKey(TempArgsKey.KEY_DS_BATCH_DATE)) {
			$externalArgs.put(TempArgsKey.KEY_DS_BATCH_DATE,
					DateUtils.getyyyyMMddNowDate());
		}
		
		$externalArgs.put(TempArgsKey.KEY_IS_MANUAL, StrValue.TRUE);

		try {
			busExecuteLogicServcie.recursiveServiceByExternal(name, group,
					$externalArgs);
		} catch (Exception e) {
			logger.error("", e);
		}
	}

}
