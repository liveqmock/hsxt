/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsi.ds.job;

import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.scheduling.quartz.AdaptableJobFactory;

/**
 * jobFactory，支持autowire注入
 * 
 * @Package: com.gy.hsi.ds.job.factory
 * @ClassName: DSJobFactory
 * @Description: none
 *
 * @author: yangyp
 * @date: 2015年10月16日 上午10:49:36
 * @version V3.0
 */
public class DSJobFactory extends AdaptableJobFactory {
	// 这个对象Spring会帮我们自动注入进来,也属于Spring技术范畴.
	@Autowired
	private AutowireCapableBeanFactory capableBeanFactory;

	/**
	 * 
	 * @param bundle
	 * @return
	 * @throws Exception
	 * @see org.springframework.scheduling.quartz.AdaptableJobFactory#createJobInstance(org.quartz.spi.TriggerFiredBundle)
	 */
	@Override
	protected Object createJobInstance(TriggerFiredBundle bundle)
			throws Exception {
		// 调用父类的方法
		Object jobInstance = super.createJobInstance(bundle);

		// 进行注入.
		capableBeanFactory.autowireBean(jobInstance);

		return jobInstance;
	}

}
