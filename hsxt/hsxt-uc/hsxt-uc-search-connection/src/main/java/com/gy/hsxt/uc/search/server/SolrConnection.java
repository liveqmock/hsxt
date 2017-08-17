package com.gy.hsxt.uc.search.server;
import org.apache.solr.client.solrj.SolrClient;

/**
 * 连接solr服务器接口
 * 
 * @category Simple to Introduction
 * @projectName hsec-searchp-service
 * @package com.gy.hsec.searchp.server.SolrConnection.java
 * @className SolrConnection
 * @description 连接solr服务器接口
 * @author lixuan
 * @createDate 2014-12-9 上午10:32:21
 * @updateUser lixuan
 * @updateDate 2014-12-9 上午10:32:21
 * @updateRemark 新建
 * @version v0.0.1
 */
public interface SolrConnection {

	/**
	 * 创建更新操作的连接
	 * 
	 * @param url
	 *            solr服务器的访问url，如http://localhost:8080/solr/
	 * @return 返回连接
	 */
	public SolrClient connect(String url);

}
