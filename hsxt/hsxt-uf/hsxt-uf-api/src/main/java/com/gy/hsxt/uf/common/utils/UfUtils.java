/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.uf.common.utils;

import java.util.Random;
import java.util.UUID;

import com.alibaba.dubbo.common.utils.NetUtils;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-uf-api
 * 
 *  Package Name    : com.gy.hsxt.uf.common.utils
 * 
 *  File Name       : UfUtils.java
 * 
 *  Creation Date   : 2015-10-9
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 工具类
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class UfUtils {
	/** 生成UUID时使用的计数器 **/
	private static long uuidCounts = 0;

	/** 随机数对象 **/
	private static Random random = new Random();

	/**
	 * 私有构造函数
	 */
	private UfUtils() {
	}

	/**
	 * 生成100年都难以重复的UUID :)
	 * 
	 * @param key
	 * @return
	 */
	public static String generateUUID() {
		return generateUUID(UfUtils.class.hashCode());
	}

	/**
	 * 生成100年都难以重复的UUID :)
	 * 
	 * @param key
	 * @return
	 */
	public static String generateUUID(Object key) {
		if (9223372036854775L <= uuidCounts) {
			uuidCounts = 0;
		}

		StringBuilder strBuffer = new StringBuilder();
		strBuffer.append(++uuidCounts).append("#");
		strBuffer.append(System.currentTimeMillis()).append("$");
		strBuffer.append(random.nextDouble()).append("%");
		strBuffer.append(UUID.randomUUID()).append("*");
		strBuffer.append(key).append(random.nextInt());

		UUID uuid = UUID.nameUUIDFromBytes(strBuffer.toString().getBytes());

		String strRandomInt = String.valueOf(random.nextInt(10));

		return uuid.toString().replaceAll("-", strRandomInt).toUpperCase();
	}

	/**
	 * 从堆栈中把日志信息递归取出
	 * 
	 * @param e
	 * @return
	 */
	public static String getStackTraceInfo(Throwable e) {
		if (null == e) {
			return "";
		}

		StringBuilder exceptionBuffer = new StringBuilder();

		getStackTraceInfo(exceptionBuffer, e, "");

		return exceptionBuffer.toString();
	}

	/**
	 * 从堆栈中把日志信息递归取出
	 * 
	 * @param exceptionBuffer
	 * @param e
	 * @param prefix
	 */
	private static void getStackTraceInfo(StringBuilder exceptionBuffer,
			Throwable e, String prefix) {
		if (null == e) {
			return;
		}

		String exMsg = e.getMessage();
		exceptionBuffer.append("\n").append(prefix);
		exceptionBuffer.append(e.getClass().getName());

		if (null != exMsg) {
			exceptionBuffer.append(":").append(e.getMessage());
		}

		StackTraceElement[] arrayOfStackTraceElement = e.getStackTrace();

		if (null != arrayOfStackTraceElement) {
			for (Object localObject : arrayOfStackTraceElement) {
				exceptionBuffer.append("\n\t at " + localObject);
			}
		}

		Throwable[] suppresseds = e.getSuppressed();

		if (null != suppresseds) {
			for (Throwable throwable : suppresseds) {
				getStackTraceInfo(exceptionBuffer, throwable, "Suppressed: ");
			}
		}

		Throwable cause = e.getCause();

		if (null != cause) {
			getStackTraceInfo(exceptionBuffer, cause, "Caused by: ");
		}
	}

	/**
	 * 获得本机IP
	 * 
	 * @return
	 */
	public static String getLocalHostIp() {
		return NetUtils.getLocalHost();
	}

	/**
	 * 获得主机名称
	 * 
	 * @return
	 */
	public static String getLocalHostName() {
		return NetUtils.getLocalAddress().getHostName();
	}

}
