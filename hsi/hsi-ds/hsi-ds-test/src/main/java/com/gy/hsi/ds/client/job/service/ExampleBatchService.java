/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */

package com.gy.hsi.ds.client.job.service;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsi.ds.api.IDSBatchService;
import com.gy.hsi.ds.api.IDSBatchServiceListener;
import com.gy.hsi.ds.common.contants.DSContants.DSTaskStatus;

/**
 * 调度业务示例类
 * 
 * @Package: com.gy.hsi.ds.example
 * @ClassName: ExampleBatchService
 * @Description: none
 *
 * @author: yangyp
 * @date: 2015年9月11日 下午3:40:55
 * @version V3.0
 */
@Service("exampleBatchService")
public class ExampleBatchService implements IDSBatchService {

	private static final Logger LOG = Logger
			.getLogger(ExampleBatchService.class);
	
	/** 回调监听类 */
	@Autowired
	private IDSBatchServiceListener listener;

	@Override
	public boolean excuteBatch(String executeId, Map<String, String> args) {

		LOG.info("执行中!!!!");

		// 发送进度通知
		listener.reportStatus(executeId, DSTaskStatus.HANDLING, "执行中");

		try {
			Thread.sleep(5000);
			
			LOG.info("执行成功!!!!");

			// 发送进度通知
			listener.reportStatus(executeId, DSTaskStatus.SUCCESS, "执行成功");
		} catch (Exception e) {
			LOG.info("执行失败!!!!", e);

			// 发送进度通知
			listener.reportStatus(executeId, DSTaskStatus.FAILED, "执行失败");

			return false;
		}

		return true;
	}
}