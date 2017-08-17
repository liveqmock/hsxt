/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.uc.search.api;
import java.util.List;

import org.apache.solr.common.SolrDocumentList;

/**
 * 用户对象搜索接口
 * 
 * @Package: com.gy.hsxt.uc.search.api
 * @ClassName: IUCUserInfoSearchService
 * @Description: TODO
 * 
 * @author: lixuan          
 * @date: 2015-11-19 下午12:24:09
 * @version V1.0
 */
public interface GenericIndexSearchService<T> {
	/**
	 * 根据ID获取对象
	 * @param coreName
	 * @param id
	 * @param clazz
	 * @return
	 */
	public T getById(String coreName, String id, Class<T> clazz);
	
	/**
	 * 根据ID获取对象
	 * @param coreName
	 * @param idName
	 * @param idValue
	 * @param clazz
	 * @return
	 */
	public T getById(String coreName, String idName, String idValue,
			Class<T> clazz) ;
	/**
	 * 根据key获取对象
	 * @param coreName
	 * @param key
	 * @param value
	 * @param clazz
	 * @return
	 */
	public List<T> getByKey(String coreName, String key, String value, Class<T> clazz);
	/**
	 * 根据key获取solr对象
	 * @param coreName
	 * @param key
	 * @param value
	 * @return
	 */
	public SolrDocumentList getByKey(String coreName, String key, String value);
}
