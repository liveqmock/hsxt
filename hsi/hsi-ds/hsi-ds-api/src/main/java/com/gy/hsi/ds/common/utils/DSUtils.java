/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsi.ds.common.utils;

import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsi-ds-api
 * 
 *  Package Name    : com.gy.hsi.ds.common.utils
 * 
 *  File Name       : DSUtils.java
 * 
 *  Creation Date   : 2016-4-5
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : DS通用工具类
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public final class DSUtils {
	private static String LOCAL_IP_ADDR = getLanHostIp();

	/**
	 * 私有构造函数
	 */
	private DSUtils() {
	}

	/**
	 * 获得本机IP地址
	 * 
	 * @return
	 */
	public static String getLanHostIp() {
		if (!StringUtils.isEmpty(LOCAL_IP_ADDR)) {
			return LOCAL_IP_ADDR;
		}

		// 根据网卡取本机配置的IP
		Enumeration<NetworkInterface> netInters = null;
		NetworkInterface ni = null;
		Enumeration<InetAddress> ips = null;
		InetAddress inetAddr = null;

		String currIp;

		StringBuilder buffer = new StringBuilder();

		try {
			netInters = NetworkInterface.getNetworkInterfaces();

			while (netInters.hasMoreElements()) {
				ni = netInters.nextElement();
				ips = ni.getInetAddresses();

				while (ips.hasMoreElements()) {
					inetAddr = (InetAddress) ips.nextElement();
					currIp = inetAddr.getHostAddress();

					if ((inetAddr instanceof Inet6Address)
							|| (currIp.startsWith("127.0."))) {
						continue;
					}

					if (0 < buffer.length()) {
						buffer.append(", ");
					}

					buffer.append(currIp);
				}
			}
		} catch (Exception e) {
		} finally {
			try {
				buffer.insert(0, InetAddress.getLocalHost().getHostName() + "/");
			} catch (UnknownHostException e) {
			}
		}

		return buffer.toString();
	}

	/**
	 * 根据域名获取ip
	 * 
	 * @param domain
	 * @return
	 */
	public static String getIpByDomain(String domain) {
		String ipAddr;
		InetAddress[] addresses;

		List<String> list = new ArrayList<String>(2);

		try {
			addresses = InetAddress.getAllByName(domain);

			for (InetAddress addr : addresses) {
				ipAddr = addr.getHostAddress();
				list.add(ipAddr);
			}
		} catch (Exception e) {
		}

		if (0 >= list.size()) {
			return "";
		}

		Collections.sort(list);

		return list.get(0);
	}
}
