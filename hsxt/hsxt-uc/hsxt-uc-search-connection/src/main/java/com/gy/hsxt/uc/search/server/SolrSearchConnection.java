package com.gy.hsxt.uc.search.server;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;

/**
 * 
 * solr服务器查询操作的连接
 * @category solr服务器查询操作的连接
 * @projectName hsec-searchp-service
 * @package com.gy.hsec.searchp.server.SolrServerConnection.java
 * @className SolrServerConnection
 * @description solr服务器查询操作的连接
 * @author lixuan
 * @createDate 2014-12-9 上午10:31:27
 * @updateUser lixuan
 * @updateDate 2014-12-9 上午10:31:27
 * @updateRemark 新建
 * @version v0.0.1
 */
public class SolrSearchConnection implements SolrConnection {
	// 最大连接数
	private int maxTotalConnections = 100;
	// 默认最大连接host
	private int defaultMaxConnectionsPerHost = 100;
	// 超时时间
	private int connectionTimeout = 100;

	/**
	 * 连接服务器
	 * @param url
	 * @return 
	 * @see com.gy.hsxt.uc.search.server.SolrConnection#connect(java.lang.String)
	 */
	public SolrClient connect(String url) {
		HttpSolrClient server = new HttpSolrClient(url);
		server.setConnectionTimeout(maxTotalConnections);
		server.setDefaultMaxConnectionsPerHost(defaultMaxConnectionsPerHost);
		server.setMaxTotalConnections(connectionTimeout);
		return server;
	}

	/**
	 * 最大连接数
	 * @return
	 */
	public int getMaxTotalConnections() {
		return maxTotalConnections;
	}

	/**
	 * 最大连接数
	 * @param maxTotalConnections
	 */
	public void setMaxTotalConnections(int maxTotalConnections) {
		this.maxTotalConnections = maxTotalConnections;
	}

	/**
	 * 默认最大连接host
	 * @return
	 */
	public int getDefaultMaxConnectionsPerHost() {
		return defaultMaxConnectionsPerHost;
	}

	/**
	 * 默认最大连接host
	 * @param defaultMaxConnectionsPerHost
	 */
	public void setDefaultMaxConnectionsPerHost(int defaultMaxConnectionsPerHost) {
		this.defaultMaxConnectionsPerHost = defaultMaxConnectionsPerHost;
	}

	/**
	 * 连接超时时间
	 * @return
	 */
	public int getConnectionTimeout() {
		return connectionTimeout;
	}

	/**
	 *  连接超时时间
	 * @param connectionTimeout
	 */
	public void setConnectionTimeout(int connectionTimeout) {
		this.connectionTimeout = connectionTimeout;
	}
}
