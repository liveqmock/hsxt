package com.gy.hsxt.uc.search.server;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.CloudSolrClient;

/**
 * 创建分布式solr连接
 * 
 * @category 创建分布式solr连接
 * @projectName hsec-searchp-service
 * @package com.gy.hsec.searchp.server.SolrCloudConnection.java
 * @className SolrCloudConnection
 * @description 创建分布式solr连接
 * @author lixuan
 * @createDate 2014-12-9 上午10:31:43
 * @updateUser lixuan
 * @updateDate 2014-12-9 上午10:31:43
 * @updateRemark 新建
 * @version v0.0.1
 */
public class SolrCloudConnection implements SolrConnection {
	// ZK
	private String zkHost;

	/**
	 * 连接服务器
	 * 
	 * @param url
	 * @return
	 * @see com.gy.hsxt.uc.search.server.SolrConnection#connect(java.lang.String)
	 */
	public SolrClient connect(String url) {
		CloudSolrClient server = new CloudSolrClient(zkHost);
		return server;
	}

	/**
	 * 设置zk
	 * 
	 * @param zkHost
	 */
	public void setZkHost(String zkHost) {
		this.zkHost = zkHost;
	}
}
