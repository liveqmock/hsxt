package com.baidu.disconf.core.common.restful.core;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.gy.hsi.common.utils.StringUtils;
import com.gy.hsi.ds.common.contants.DSContants;
import com.gy.hsi.ds.common.utils.HttpClientHelper;

/**
 * @author liaoqiqi
 * @version 2014-6-10
 */
public class RemoteUrl {

	private String url;

	private List<URL> urls = new ArrayList<URL>(5);

	private static List<String> newServerList = null;

	public RemoteUrl(String url, List<String> serverList) {

		this.url = url;

		if (!isValidServerList(newServerList)) {
			newServerList = parseServerContext(serverList);
		}

		for (String server : newServerList) {
			try {
				urls.add(new URL(server + url));
			} catch (MalformedURLException e) {
			}
		}
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<URL> getUrls() {
		return urls;
	}

	@Override
	public String toString() {
		return "RemoteUrl [url=" + url + ", urls=" + urls + "]";
	}

	/**
	 * 解析上下文
	 * 
	 * @param serverList
	 * @return
	 */
	private List<String> parseServerContext(List<String> serverList) {

		String context = DSContants.ROOT_CONTEXT;

		boolean isRootContextOk = false;

		for (String server : serverList) {
			if (server.endsWith(context)) {
				continue;
			}

			server = addHttpProtocol(server) + context;

			if (HttpClientHelper.isSiteOk(server, 2500)) {
				isRootContextOk = true;

				break;
			}
		}

		List<String> newServerList = new ArrayList<String>(5);

		for (String server : serverList) {

			server = addHttpProtocol(server);

			if (server.endsWith(context)) {
				newServerList.add(server);

				continue;
			}

			if (isRootContextOk) {
				server = server.replaceAll("\\/+$", "").concat(context);
			}

			newServerList.add(server);
		}

		return newServerList;
	}

	/**
	 * 添加http协议
	 * 
	 * @param server
	 * @return
	 */
	private String addHttpProtocol(String server) {
		if (!server.startsWith("http://")) {
			server = "http://" + server;
		}

		return server;
	}

	/**
	 * check valid
	 * 
	 * @param serverList
	 * @return
	 */
	private boolean isValidServerList(List<String> serverList) {
		
		if ((null == serverList) || (0 >= serverList.size())) {
			return false;
		}

		String server = StringUtils.avoidNull(serverList.get(0));

		return server.endsWith(DSContants.ROOT_CONTEXT);
	}

}
