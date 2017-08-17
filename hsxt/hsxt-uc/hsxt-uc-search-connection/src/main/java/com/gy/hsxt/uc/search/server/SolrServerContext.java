package com.gy.hsxt.uc.search.server;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrInputDocument;

import com.gy.hsxt.uc.search.server.SolrConnectionFactory.ServerTypeEnum;

/**
 * solr服务器连接
 * 
 * @category solr服务器连接
 * @projectName hsec-searchp-service
 * @package com.gy.hsec.searchp.server.SolrClientContext.java
 * @className SolrClientContext
 * @description solr服务器连接
 * @author lixuan
 * @createDate 2014-12-8 上午10:45:48
 * @updateUser lixuan
 * @updateDate 2014-12-8 上午10:45:48
 * @updateRemark 新建
 * @version v0.0.1
 */
public class SolrServerContext {

	// solr服务器运行模式，1为普通模式，2为分布式模式，默认为1
	private static int SERVER_MODE = 1;
	private static String coreNames;
	private static String solrServerUrl;
	private static Map<String, SolrClient> SEARCH_SERVERS = new ConcurrentHashMap<String, SolrClient>();
	private static Map<String, SolrClient> UPDATE_SERVERS = new ConcurrentHashMap<String, SolrClient>();;
	SolrConnectionFactory solrConnectionFactory;
	private static String defaultCollection;
	
	/**
	 * 创建solr连接
	 */
	public void init() {
		// 如果solr服务器为分布式
		if (SERVER_MODE == 2) {
			createCloudServer();
		} else {
			// 普通模式
			createServer();
		}
	}

	/**
	 * 创建分布式的连接
	 */
	private void createCloudServer() {
		String[] cns = coreNames.split(",");
		for (String cn : cns) {
			 CloudSolrClient ss = (CloudSolrClient)solrConnectionFactory.connect(
					ServerTypeEnum.CLOUD_TYPE, solrServerUrl, cn);
			ss.setDefaultCollection(cn);
			SEARCH_SERVERS.put(cn, ss);
			UPDATE_SERVERS.put(cn, ss);
		}
	}

	/**
	 * 创建普通模式的连接
	 */
	private void createServer() {
		String[] cns = coreNames.split(",");
		for (String cn : cns) {
			SolrClient searchServer = solrConnectionFactory.connect(
					ServerTypeEnum.SEARCH_TYPE, solrServerUrl, cn);
			SEARCH_SERVERS.put(cn, searchServer);
			SolrClient updateServer = solrConnectionFactory.connect(
					ServerTypeEnum.UPDATE_TYPE, solrServerUrl, cn);
			UPDATE_SERVERS.put(cn, updateServer);
		}
	}

	/**
	 * 添加索引
	 * 
	 * @param core
	 * @param obj
	 * @throws Exception
	 */
	public void add(String core, Object obj) throws Exception {
		SolrClient ss = SEARCH_SERVERS.get(core);
		if (ss == null) {
			throw new Exception("The SolrClient of coreName [" + core
					+ "] is not found");
		}
		ss.addBean(obj);
		ss.commit();
	}

	/**
	 * 根据id删除索引
	 * 
	 * @param core
	 * @param id
	 * @throws Exception
	 */
	public void delete(String core, String id) throws Exception {
		SolrClient ss = SEARCH_SERVERS.get(core);
		if (ss == null) {
			throw new Exception("The SolrClient of coreName [" + core
					+ "] is not found");
		}
		ss.deleteById(id);
		ss.commit();
	}

	/**
	 * 根据ID更新字段
	 * 
	 * @param core
	 * @param list
	 */
	public void update(String core, List<UpdateData> list) throws Exception {
		SolrClient ss = SEARCH_SERVERS.get(core);
		if (ss == null) {
			throw new Exception("The SolrClient of coreName [" + core
					+ "] is not found");
		}

		for (UpdateData ud : list) {
			SolrInputDocument sd = new SolrInputDocument();

			for (Data data : ud.datas) {
				if (data.isIncrease) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("inc", data.getValue());
					String incKey = data.getFieldName();
					sd.addField(incKey, map);
				} else {
					sd.addField(data.getFieldName(), data.getValue());
				}
			}
			sd.addField("id", ud.getIdValue());
			ss.add(sd);
			ss.commit();
		}

	}

	/**
	 * 批量添加
	 * 
	 * @param core
	 * @param list
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public void batchAdd(String core, List list) throws Exception {
		SolrClient ss = SEARCH_SERVERS.get(core);
		if (ss == null) {
			throw new Exception("The SolrClient of coreName [" + core
					+ "] is not found");
		}
		ss.addBeans(list);
		ss.commit();
	}

	/**
	 * 批量删除
	 * 
	 * @param core
	 * @param idList
	 * @throws Exception
	 */
	public void batchDelete(String core, List<String> idList) throws Exception {
		SolrClient ss = SEARCH_SERVERS.get(core);
		if (ss == null) {
			throw new Exception("The SolrClient of coreName [" + core
					+ "] is not found");
		}

		ss.deleteById(idList);
		ss.commit();
	}

	/**
	 * 搜索
	 * @param core
	 * @param query
	 * @return
	 * @throws Exception
	 */
	public QueryResponse search(String core, SolrQuery query) throws Exception {
		SolrClient ss = SEARCH_SERVERS.get(core);
		if (ss == null) {
			throw new Exception("The SolrClient of coreName [" + core
					+ "] is not found");
		}
		return ss.query(query);
	}

	/**
	 * core名称
	 * 
	 * @return
	 */
	public String getCoreNames() {
		return coreNames;
	}

	/**
	 * core 名称
	 * 
	 * @param coreNames
	 */
	public void setCoreNames(String coreNames) {
		this.coreNames = coreNames;
	}

	/**
	 * 设置服务器访问地址
	 * 
	 * @param solrServerUrl
	 */
	public void setSolrClientUrl(String solrServerUrl) {
		this.solrServerUrl = solrServerUrl;
	}

	/**
	 * 设置服务器模式
	 * 
	 * @param mode
	 */
	public static void setServerMode(int mode) {
		SERVER_MODE = mode;
	}

	/**
	 * 设置连接工厂类
	 * 
	 * @param solrConnectionFactory
	 */
	public void setSolrConnectionFactory(
			SolrConnectionFactory solrConnectionFactory) {
		this.solrConnectionFactory = solrConnectionFactory;
	}

	/**
	 * @return the defaultCollection
	 */
	public static String getDefaultCollection() {
		return defaultCollection;
	}

	/**
	 * @param defaultCollection the defaultCollection to set
	 */
	public static void setDefaultCollection(String defaultCollection) {
		SolrServerContext.defaultCollection = defaultCollection;
	}
	
	

}