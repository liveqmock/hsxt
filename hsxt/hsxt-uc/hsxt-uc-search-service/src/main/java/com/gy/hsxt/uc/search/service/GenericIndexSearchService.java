/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.uc.search.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.StringUtils;
import com.gy.hsxt.uc.search.bean.ErrorCodeEnum;
import com.gy.hsxt.uc.search.bean.Search;
import com.gy.hsxt.uc.search.bean.SearchResult;
import com.gy.hsxt.uc.search.server.SolrServerContext;
import com.gy.hsxt.uc.search.server.util.TransferSolrObject;
import com.gy.hsi.lc.client.log4j.SystemLog;

/**
 * 
 * 索引工程中使用的通用搜索类，提供按ID查询索引
 * 
 * @Package: com.gy.hsxt.uc.search.service
 * @ClassName: GenericIndexSearchService
 * @Description: TODO
 * 
 * @author: lixuan
 * @date: 2015-11-19 下午6:37:36
 * @version V1.0
 * 
 * @param <T>
 */
@Service
public abstract class GenericIndexSearchService<T, R> {
	private Log log = LogFactory.getLog(this.getClass());
	@Autowired
	SolrServerContext solrServerContext;
	int MAX_ROWS = 20000;
	String coreName;

	public void setCoreName(String coreName) {
		this.coreName = coreName;
	}

	/**
	 * 根据ID获取对象
	 * 
	 * @param coreName
	 * @param id
	 * @param clazz
	 * @return
	 */
	public T getById(String coreName, String idName, String idValue,
			Class<T> clazz) {
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
			SystemLog.error("GenericIndexSearchService", "getById","根据ID获取对象时异常",e);
			return null;
		}
	}

	/**
	 * 根据key获取对象
	 * 
	 * @param coreName
	 * @param key
	 * @param value
	 * @param clazz
	 * @return
	 */
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
			SystemLog.error("GenericIndexSearchService", "getByKey","根据key获取对象时异常",e);
			return null;
		}
	}

	/**
	 * 根据key获取索引数据
	 * 
	 * @param coreName
	 * @param key
	 * @param value
	 * @return
	 */
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

	public void beforeSearch() {

	}

	/**
	 * 组装fl语句
	 * 
	 * @param search
	 * @return
	 */
	protected String composeField(Search search) {
		if (StringUtils.isBlank(search.getFilterField())) {
			return null;
		}
		return search.getFilterField();
	}

	/**
	 * 组装filter query 语句
	 * 
	 * @return
	 */
	protected String[] composeFilterQuery(Search search) {
		if (search.getFilterQuery() == null) {
			return null;
		}
		String[] result = new String[search.getFilterQuery().length];
		int i = 0;
		for (String s : search.getFilterQuery()) {
			String[] tmp = s.split(",");
			// 验证格式
			if (tmp.length != 2) {
				throw new HsException(
						ErrorCodeEnum.SOLR_SEARCH_FAILED.getValue(),
						"输入参数filterQuery格式不正确，"
								+ JSONObject.toJSONString(search
										.getFilterQuery()));
			}
			// try {
			result[i] = tmp[0] + " TO " + tmp[1];
			i++;
			// } catch (NumberFormatException e) {
			// throw new HsException(
			// ErrorCodeEnum.SOLR_SEARCH_FAILED.getValue(),
			// "输入参数filterQuery格式不正确，传入的非数字"
			// + JSONObject.toJSONString(search
			// .getFilterQuery()));
			// }
		}
		return result;

	}

	/**
	 * 组装查询语句
	 * 
	 * @param search
	 * @param searchResult
	 * @return
	 */
	protected abstract String composeQuery(Search search);

	/**
	 * 搜索
	 * 
	 * @param search
	 * @return
	 */
	public SolrDocumentList searchDoc(Search search) {
		beforeSearch();
		String queryStr = composeQuery(search);

		SolrQuery query = new SolrQuery();
		if (search.getSortName() != null && !search.getSortName().equals("")) {
			if (search.isAsc()) {
				query.addSort(search.getSortName(), ORDER.asc);
			} else {
				query.addSort(search.getSortName(), ORDER.desc);
			}
		}

		query.setStart(search.getPaginate().getStartNum());
		query.setRows(search.getPaginate().getPageSize());

		String[] fq = composeFilterQuery(search);
		String fl = composeField(search);
		if (fl != null) {
			query.addField(fl);
		}
		log.debug("--- Core name: " + coreName + ", Query String is:"
				+ queryStr + ", order:" + search.getSortName() + ",pageSize:"
				+ search.getPaginate().getPageSize());
		if (fq != null) {
			query.addFilterQuery(fq);
			for (String s : fq) {
				log.debug("---- fq: " + s);
			}
		}

		// 设置查询语句
		if (StringUtils.isNotBlank(queryStr)) {
			query.setQuery(queryStr);
		} else {
			log.error("Query string is empty");
			return null;
		}
		try {
			QueryResponse resp = solrServerContext.search(coreName, query);

			SolrDocumentList list = resp.getResults();
			search.getPaginate().setTotalNum(list.getNumFound());
			return resp.getResults();

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 搜索
	 * 
	 * @param search
	 * @param clazz
	 * @return
	 */
	public SearchResult<T> search(Search search, Class<T> clazz) {
		SolrDocumentList list = searchDoc(search);
		if (list == null || list.size() <= 0) {
			log.debug("--- Core name: " + coreName + ", No data found.");
			return null;
		}
		log.debug("--- Core name: " + coreName + ", Search record:"
				+ list.size());
		// 转换搜索结果对象
		TransferSolrObject<T> tso = new TransferSolrObject<>();
		List<T> rsList = tso.toBeanList(list, clazz);
		SearchResult<T> result = new SearchResult<T>();
		result.setList(rsList);
		result.setPaginate(search.getPaginate());
		return result;
	}

	/**
	 * 搜索数据，并返回一个字段
	 * 
	 * @param search
	 * @return
	 */
	public SearchResult<String> search(Search search) {
		SolrDocumentList list = searchDoc(search);
		if (list == null || list.size() <= 0) {
			
			return null;
		}
		SystemLog.debug("GenericIndexSearchService", "search","--- Core name: " + coreName + ", 搜索结果数:"
				+ list.size());
		
		// 转换搜索结果对象
		TransferSolrObject<String> tso = new TransferSolrObject<String>();
		List<String> rsList = tso.toStringList(list, search.getFilterField());
		SearchResult<String> result = new SearchResult<String>();
		result.setList(rsList);
		result.setPaginate(search.getPaginate());
		return result;
	}
}
