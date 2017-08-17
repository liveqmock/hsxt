package com.gy.hsxt.point.client.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Simple to Introduction
 * 
 * @category Simple to Introduction
 * @projectName WritePOS
 * @package .Wait.java
 * @className Wait
 * @description 等待线程
 * @author fandi
 * @createDate 2014-8-8 上午11:55:08
 * @updateUser lyh
 * @updateDate 2015-12-18 上午11:55:08
 * @updateRemark 说明本次修改内容
 * @versionV3.0
 */
public class Wait extends Thread {
	private static final Logger LOGGER = LoggerFactory.getLogger(Wait.class);
	WaitForTime onwer;
	int WaitTime;
	int a;

	public Wait(WaitForTime onwer, int WaitTime) {
		super("Wait");
		this.onwer = onwer;
		this.WaitTime = WaitTime;
	}

	public void run() {
		try {
			sleep(WaitTime);
			LOGGER.debug("超时！");
			onwer.TimeOut();
		} catch (InterruptedException e) {
			LOGGER.debug("安全关闭超时时钟" + WaitTime);
		}
	}
}
