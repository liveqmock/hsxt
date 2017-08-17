package com.gy.hsxt.uc.search.server;

import org.apache.solr.client.solrj.SolrClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 连接solr服务器工厂类
 * 
 * @Package: com.gy.hsxt.uc.search.server  
 * @ClassName: SolrConnectionFactory 
 * @Description: TODO
 *
 * @author: lixuan 
 * @date: 2015-11-19 上午10:44:30 
 * @version V1.0
 */
@Service("solrConnectionFactory")
public class SolrConnectionFactory {

	@Autowired
	SolrSearchConnection solrSearchConnection;
	@Autowired
	SolrCloudConnection solrCloudConnection;
	@Autowired
	SolrUpdateConnection solrUpdateConnection;

	/**
	 * 
	 * @param type
	 * @param solrServerUrl
	 * @param coreName
	 * @return
	 */
	public SolrClient connect(ServerTypeEnum type, String solrServerUrl,
			String coreName) {
		String url = solrServerUrl + (coreName == null ? "" : coreName);
		switch (type) {
		case SEARCH_TYPE:
			return solrSearchConnection.connect(url);
		case UPDATE_TYPE:
			return solrUpdateConnection.connect(url);
		case CLOUD_TYPE:
			return solrCloudConnection.connect(url);
		default:
			return null;
		}
	}

	/**
	 * 服务器类型：普通和分布式
	 * 
	 * @category 服务器类型
	 * @projectName hsec-searchp-service
	 * @package com.gy.hsec.searchp.server.GenericSolrServer.java
	 * @className ServerTypeEnum
	 * @description 服务器类型
	 * @author lixuan
	 * @createDate 2014-12-8 下午2:42:56
	 * @updateUser lixuan
	 * @updateDate 2014-12-8 下午2:42:56
	 * @updateRemark 新建
	 * @version v0.0.1
	 */
	enum ServerTypeEnum {
		/**
		 * 普通查询连接
		 */
		SEARCH_TYPE(1),
		/**
		 * 普通更新连接
		 */
		UPDATE_TYPE(2),
		/**
		 * 分布式连接
		 */
		CLOUD_TYPE(3);
		int type;

		ServerTypeEnum(int type) {
			this.type = type;
		}

		public int getType() {
			return type;
		}
	}

	
}
