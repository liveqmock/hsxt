/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsi.fs.client.common.framework.spring;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsi-fs-client
 * 
 *  Package Name    : com.gy.hsi.fs.client.common.framework.spring
 * 
 *  File Name       : SpringContextLoader.java
 * 
 *  Creation Date   : 2015-5-25
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : SpringContextLoader
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public final class SpringContextLoader {
	
	private static final Logger logger = Logger.getLogger(SpringContextLoader.class);
	
	private static ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(
			"classpath*:spring-context-fs.xml");
	
	/**
	 * init
	 */
	public static void init() {
		try {
			applicationContext.start();
		} catch (Exception e) {
			logger.error("", e);
		}
	}

	/**
	 * 获取ApplicationContext对象
	 * 
	 * @return
	 */
	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

}