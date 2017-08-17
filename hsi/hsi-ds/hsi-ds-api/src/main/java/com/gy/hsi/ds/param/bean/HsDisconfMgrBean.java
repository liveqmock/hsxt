package com.gy.hsi.ds.param.bean;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;

import com.baidu.disconf.client.DisconfMgrBean;

public class HsDisconfMgrBean extends DisconfMgrBean {	
    protected static final Logger logger = Logger.getLogger(HsDisconfMgrBean.class);

	@Override
	public void postProcessBeanDefinitionRegistry(
			BeanDefinitionRegistry registry) throws BeansException {
		try {
			super.postProcessBeanDefinitionRegistry(registry);
		} catch (Exception e) {
			logger.error("", e);
		}
	}
}
