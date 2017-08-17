package com.gy.hsxt.uc.search.server;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.ConcurrentUpdateSolrClient;

/**
 * solr服务器更新操作的连接
 * 
 * @category solr服务器更新操作的连接
 * @projectName hsec-searchp-service
 * @package com.gy.hsec.searchp.server.SolrUpdateConnection.java
 * @className SolrUpdateConnection
 * @description solr服务器更新操作的连接
 * @author lixuan
 * @createDate 2014-12-9 上午11:27:35
 * @updateUser lixuan
 * @updateDate 2014-12-9 上午11:27:35
 * @updateRemark 新建
 * @version v0.0.1
 */
public class SolrUpdateConnection implements SolrConnection{
	private int queueSize = 10;
	private int threadCount = 10;

	/**
	 * 连接solr服务器
	 * @param url
	 * @return 
	 * @see com.gy.hsxt.uc.search.server.SolrConnection#connect(java.lang.String)
	 */
	public SolrClient connect(String url) {
		ConcurrentUpdateSolrClient cuServer = new ConcurrentUpdateSolrClient(
				url, queueSize, threadCount);
		return cuServer;
	}

	/**
	 * 获取缓冲池大小
	 * @return
	 */
	public int getQueueSize() {
		return queueSize;
	}

	/**
	 * 设置缓冲池大小
	 * @param queueSize
	 */
	public void setQueueSize(int queueSize) {
		this.queueSize = queueSize;
	}

	/**
	 * 获取线程数
	 * @return
	 */
	public int getThreadCount() {
		return threadCount;
	}

	/**
	 * 设置线程数
	 * @param threadCount
	 */
	public void setThreadCount(int threadCount) {
		this.threadCount = threadCount;
	}
}
