/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.uf.common.dubbo;

import com.gy.hsxt.uf.common.spring.UfPropertyConfigurer;
import com.gy.hsxt.uf.common.utils.StringUtils;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-uf-api
 * 
 *  Package Name    : com.gy.hsxt.uf.common.remote
 * 
 *  File Name       : DubboConfiger.java
 * 
 *  Creation Date   : 2015年10月27日
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : dubbo配置常量
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class DubboConfiger {
	public static final String DUBBO_APPLICATION_NAME = "dubbo.application.name";
	public static final String DUBBO_REGISTRY_ADDRESS = "dubbo.registry.address";
	public static final String DUBBO_PROTOCOL = "dubbo.protocol";
	public static final String DUBBO_PROTOCOL_PORT = "dubbo.protocol.port";
	public static final String DUBBO_PROTOCOL_THREADPOOL_TYPE = "dubbo.protocol.threadpool.type";
	public static final String DUBBO_PROTOCOL_THREADPOOL_THREADS = "dubbo.protocol.threadpool.threads";
	public static final String DUBBO_PROTOCOL_ACCEPTS = "dubbo.protocol.accepts";

	public static final String DUBBO_REFER_CONNECTIONS = "dubbo.reference.connections";
	public static final String DUBBO_REFER_RETIRES = "dubbo.reference.retires";
	public static final String DUBBO_SERV_ACTIVES = "dubbo.service.actives";

	public static final String DUBBO_SERV_TIMEOUT5000 = "dubbo.service.timeout5000";
	public static final String DUBBO_SERV_TIMEOUT10000 = "dubbo.service.timeout10000";
	public static final String DUBBO_SERV_TIMEOUT15000 = "dubbo.service.timeout15000";
	public static final String DUBBO_SERV_TIMEOUT30000 = "dubbo.service.timeout30000";
	public static final String DUBBO_SERV_TIMEOUT60000 = "dubbo.service.timeout60000";

	public static String getAppName() {
		String appName = UfPropertyConfigurer
				.getProperty(DUBBO_APPLICATION_NAME);

		if (StringUtils.isEmpty(appName)) {
			appName = "hsi-uf-" + System.currentTimeMillis();
		}

		return appName;
	}

	public static String getRegAddress() {
		String regAddress = UfPropertyConfigurer
				.getProperty(DUBBO_REGISTRY_ADDRESS);

		if (StringUtils.isEmpty(regAddress)) {
			throw new IllegalArgumentException("The property '"
					+ DUBBO_REGISTRY_ADDRESS
					+ "' can't be empty! Please check the dubbo configuration.");
		}

		return regAddress;
	}

	public static String getProtocol() {
		String protocol = UfPropertyConfigurer.getProperty(DUBBO_PROTOCOL);

		if (StringUtils.isEmpty(protocol)) {
			protocol = "dubbo";
		}

		return protocol;
	}

	public static int getProPort() {
		String strProPort = UfPropertyConfigurer
				.getProperty(DUBBO_PROTOCOL_PORT);

		if (StringUtils.isEmpty(strProPort)) {
			throw new IllegalArgumentException("The property '"
					+ DUBBO_PROTOCOL_PORT
					+ "' can't be empty! Please check the dubbo configuration.");
		}

		return StringUtils.str2Int(strProPort);
	}

	public static String getProThreadpoolType() {
		String threadpoolType = UfPropertyConfigurer
				.getProperty(DUBBO_PROTOCOL_THREADPOOL_TYPE);

		if (StringUtils.isEmpty(threadpoolType)) {
			threadpoolType = "fixed";
		}

		return threadpoolType;
	}

	public static int getProThreadpoolThreads() {
		int proThreadpoolThreads = UfPropertyConfigurer
				.getPropertyIntValue(DUBBO_PROTOCOL_THREADPOOL_THREADS);

		if (0 >= proThreadpoolThreads) {
			proThreadpoolThreads = 200;
		}

		return proThreadpoolThreads;
	}

	public static int getProAccepts() {
		int aeccepts = UfPropertyConfigurer
				.getPropertyIntValue(DUBBO_PROTOCOL_ACCEPTS);

		if (0 >= aeccepts) {
			aeccepts = 1000;
		}

		return aeccepts;
	}

	public static int getReferConnections() {
		int connections = UfPropertyConfigurer
				.getPropertyIntValue(DUBBO_REFER_CONNECTIONS);

		if (0 >= connections) {
			connections = 200;
		}

		return connections;
	}

	public static int getReferRetires() {
		return UfPropertyConfigurer.getPropertyIntValue(DUBBO_REFER_RETIRES);
	}

	public static int getServActives() {
		return UfPropertyConfigurer.getPropertyIntValue(DUBBO_SERV_ACTIVES);
	}

	public static int getServTimeout5000() {
		return UfPropertyConfigurer.getPropertyIntValue(DUBBO_SERV_TIMEOUT5000);
	}

	public static int getServTimeout10000() {
		return UfPropertyConfigurer
				.getPropertyIntValue(DUBBO_SERV_TIMEOUT10000);
	}

	public static int getServTimeout15000() {
		return UfPropertyConfigurer
				.getPropertyIntValue(DUBBO_SERV_TIMEOUT15000);
	}

	public static int getServTimeout30000() {
		return UfPropertyConfigurer
				.getPropertyIntValue(DUBBO_SERV_TIMEOUT30000);
	}

	public static int getServTimeout60000() {
		return UfPropertyConfigurer
				.getPropertyIntValue(DUBBO_SERV_TIMEOUT60000);
	}
}
