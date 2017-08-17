/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsi.ds.param;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.baidu.disconf.client.config.DisClientConfig;
import com.baidu.disconf.core.common.restful.core.RemoteUrl;
import com.gy.hsi.common.utils.StringUtils;
import com.gy.hsi.ds.common.utils.DSUtils;
import com.gy.hsi.ds.common.utils.HttpClientHelper;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsi-ds-api
 * 
 *  Package Name    : com.gy.hsi.ds.param
 * 
 *  File Name       : SystemInstanceNoWorker.java
 * 
 *  Creation Date   : 2016年4月5日
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 获取系统实例编号
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class HsSystemInstanceNoWorker {

	private static long lastFetchTimeMills = System.currentTimeMillis();

	private static final long FETCH_TIME_INTERVAL = 60 * 60 * 1000;

	/**
	 * 获取系统实例编号
	 * 
	 * @return
	 */
	public static String fetchSysInstNoFromDSServer() {

		try {
			String sysInstNo = "";

			String url = selectOneUrlByRandom();

			if (!StringUtils.isEmpty(url)) {
				Map<String, String> params = new HashMap<String, String>(1);
				params.put("ipAddress", DSUtils.getLanHostIp());

				sysInstNo = HttpClientHelper.doGet(url, params);
			}

			if (StringUtils.avoidNull(sysInstNo).trim().matches("^\\d{2}$")) {
				return sysInstNo;
			}
		} catch (Exception e) {
		}

		return "";
	}

	/**
	 * 与DS服务端进行握手
	 */
	public static void doHandshakeWithDSServer() {

		long currTimeMills = System.currentTimeMillis();
		long timeInterval = currTimeMills - lastFetchTimeMills;

		if (FETCH_TIME_INTERVAL <= timeInterval) {
			lastFetchTimeMills = currTimeMills;

			fetchSysInstNoFromDSServer();
		}
	}

	/**
	 * 随机选取一个URL
	 * 
	 * @return
	 */
	private static String selectOneUrlByRandom() {
		String confServerHost = DisClientConfig.getInstance().CONF_SERVER_HOST;

		List<String> serverList = new ArrayList<String>();

		if (!StringUtils.isEmpty(confServerHost)) {
			String[] hosts = confServerHost.split(",");

			if (0 < hosts.length) {
				serverList = Arrays.asList(hosts);
			}
		}

		List<URL> urls = new RemoteUrl("/fetchSysInstNo", serverList).getUrls();

		if (0 < urls.size()) {
			int randomIndex = new Random().nextInt(urls.size() - 1);

			return urls.get(randomIndex).getPath();
		}

		return "";
	}
}
