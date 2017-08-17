/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.uc.index.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsxt.uc.search.server.SolrServerContext;
import com.gy.hsxt.uc.search.server.util.TransferSolrObject;
import com.gy.hsxt.uc.search.api.GenericIndexSearchService;

/**
 * 索引工程中使用的通用搜索类，提供按ID查询索引
 * 
 * @category 索引工程中使用的通用搜索类，提供按ID查询索引
 * @projectName hsec-searchp-indexing
 * @package 
 *          com.gy.hsec.searchp.indexing.service.GenericIndexSearchServiceImpl.java
 * @className GenericIndexSearchServiceImpl
 * @description 索引工程中使用的通用搜索类，提供按ID查询索引
 * @author lixuan
 * @createDate 2015-1-14 下午8:33:35
 * @updateUser lixuan
 * @updateDate 2015-1-14 下午8:33:35
 * @updateRemark 新建
 * @version v0.0.1
 */
@Service("genericIndexSearchService")
public class GenericIndexSearchServiceImpl<T> implements
		GenericIndexSearchService<T> {

	@Autowired
	SolrServerContext solrServerContext;
	int MAX_ROWS = 20000;
	
	@Override
	public T getById(String coreName, String idName, String idValue,
			Class<T> clazz){
		SolrQuery query = new SolrQuery();
		query.setQuery(idName + ":" + idValue);
		try {
			QueryResponse resp = solrServerContext.search(coreName, query);
			SolrDocumentList list = resp.getResults();
			if (list != null && list.size() > 0) {
				// 转换搜索结果对象
				TransferSolrObject<T> tso = new TransferSolrObject<T>();
				return tso.toBean(list.get(0), clazz);
			}
			return null;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public T getById(String coreName, String id, Class<T> clazz) {
		SolrQuery query = new SolrQuery();
		query.setQuery("id:" + id);
		try {
			QueryResponse resp = solrServerContext.search(coreName, query);
			SolrDocumentList list = resp.getResults();
			if (list != null && list.size() > 0) {
				// 转换搜索结果对象
				TransferSolrObject<T> tso = new TransferSolrObject<T>();
				return tso.toBean(list.get(0), clazz);
			}
			return null;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<T> getByKey(String coreName, String key, String value,
			Class<T> clazz) {
		SolrQuery query = new SolrQuery();
		query.setQuery(key + ":" + value);
		query.setStart(0);
		query.setRows(MAX_ROWS);
		try {
			QueryResponse resp = solrServerContext.search(coreName, query);
			SolrDocumentList list = resp.getResults();
			if (list != null && list.size() > 0) {
				// 转换搜索结果对象
				TransferSolrObject<T> tso = new TransferSolrObject<T>();
				List<T> result = new ArrayList<T>();
				for (SolrDocument sd : list) {
					result.add(tso.toBean(sd, clazz));
				}
				return result;
			}
			return null;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public SolrDocumentList getByKey(String coreName, String key, String value) {
		SolrQuery query = new SolrQuery();
		query.setQuery(key + ":" + value);
		query.setStart(0);
		query.setRows(MAX_ROWS);
		try {
			QueryResponse resp = solrServerContext.search(coreName, query);
			return resp.getResults();

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
