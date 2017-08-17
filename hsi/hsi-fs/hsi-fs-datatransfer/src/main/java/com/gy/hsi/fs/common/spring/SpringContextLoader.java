/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsi.fs.common.spring;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.gy.hsi.fs.factory.BeansFactory;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-uf-service
 * 
 *  Package Name    : com.gy.hsxt.uf.common.spring
 * 
 *  File Name       : SpringContextLoader.java
 * 
 *  Creation Date   : 2015-10-25
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
	/** Spring上下文对象 **/
	private static ClassPathXmlApplicationContext context = null;

	/** Bean工厂对象 **/
	private static BeansFactory beansFactory = null;

	/**
	 * 获取AppContext对象
	 * 
	 * @return
	 */
	public static ClassPathXmlApplicationContext getAppContext() {
		if(null == context) {
			context = new ClassPathXmlApplicationContext(
					"classpath*:spring/spring-global.xml");
		}
		
		return context;
	}

	/**
	 * 获取beansFactory对象
	 * 
	 * @return
	 */
	public static BeansFactory getBeansFactory() {
		if (null == beansFactory) {
			beansFactory = (BeansFactory) context.getBean("beansFactory");
		}

		return beansFactory;
	}
}