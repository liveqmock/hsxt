/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.uf;

import java.io.File;

import com.gy.hsxt.uf.common.constant.ConfigConst.StartClassPath;
import com.gy.hsxt.uf.common.spring.UfPropertyConfigurer;
import com.gy.hsxt.uf.common.utils.StringUtils;
import com.gy.hsxt.uf.core.netty.NettyServer;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-uf-service
 * 
 *  Package Name    : com.gy.hsxt.uf
 * 
 *  File Name       : ServerStarter.java
 * 
 *  Creation Date   : 2015-9-26
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : Netty Server启动入口(服务器启动唯一入口)
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public final class ServerStarter {

	public static void main(String[] args) {
		// 初始化classpath
		initClasspath(args);

		// 实现优雅停机
		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				NettyServer.getInstance().shutdown();
			}
		});

		// 执行netty server的启动
		NettyServer.getInstance().startup();
	}

	/**
	 * 初始classpath
	 * 
	 * @param args
	 */
	private static void initClasspath(String[] args) {
		if (StringUtils.isAllEmpty(args)) {
			return;
		}

		// 进行参数解析和初始化
		for (String arg : args) {
			try {
				int index = arg.lastIndexOf("=");

				String key = arg.substring(0, index);
				String value = arg.substring(index + 1);

				if (StringUtils.isExistEmpty(key, value)) {
					continue;
				}

				// 如果是user.dir或log.home, 需做特殊处理
				if (StartClassPath.USER_DIR.equals(key)
						|| StartClassPath.LOG_HOME.equals(key)) {
					if (!value.endsWith(File.separator)) {
						value += File.separator;
					}

					value = new File(value).getCanonicalPath()
							.replaceFirst(File.separator + "*$", "").trim();

					if (StartClassPath.USER_DIR.equals(key)) {
						key = "-D" + key.trim();
					}
				}

				// 缓存key-value到系统属性
				System.setProperty(key, value);

				// 缓存key-value
				UfPropertyConfigurer.setProperties(key, value);
			} catch (Exception e) {
			}
		}
	}
}