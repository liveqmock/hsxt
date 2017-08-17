/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.uc.index.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.uc.search.api.GenericIndexSearchService;
import com.gy.hsxt.uc.search.api.IUCUserInfoService;
import com.gy.hsxt.uc.search.bean.ErrorCodeEnum;
import com.gy.hsxt.uc.search.bean.SearchUserInfo;
import com.gy.hsxt.uc.search.server.SolrServerContext;

/**
 * 用户信息操作类
 * 
 * @Package: com.gy.hsxt.uc.index.service
 * @ClassName: UserInfoService
 * @Description: TODO
 * 
 * @author: lixuan
 * @date: 2015-12-15 下午3:51:23
 * @version V1.0
 */
@Service
public class UserInfoService implements IUCUserInfoService {

	@Autowired
	SolrServerContext solrServer;
	@Autowired
	GenericIndexSearchService<SearchUserInfo> indexSearchService;
	
	/**
	 * 添加用户信息
	 * 
	 * @param user
	 * @throws HsException
	 * @see com.gy.hsxt.uc.search.api.IUCUserInfoService#add(com.gy.hsxt.uc.search.bean.SearchUserInfo)
	 */
	@Override
	public void add(SearchUserInfo user) throws HsException {
		try {
			// System.out.println("添加用户信息：" + JSONObject.toJSONString(user));
			SystemLog.debug("UserInfoService", "add",
					"添加用户信息：" + JSONObject.toJSONString(user));
			if (user.getSearchMode() == null 
					&& (user.getUserType().intValue() == 0 || user
							.getUserType().intValue() == 1)) {
				user.setSearchMode("1");
			}
			solrServer.add(SearchUserInfo.CORE_NAME, user);
		} catch (Exception e) {
			e.printStackTrace();
			throw new HsException(
					ErrorCodeEnum.SOLR_INDEX_ADD_FAILED.getValue(),
					ErrorCodeEnum.SOLR_INDEX_ADD_FAILED.getDesc());
		}
	}

	/**
	 * * 批量添加用户信息
	 * 
	 * @param users
	 *            用户列表
	 * @throws HsException
	 * @see com.gy.hsxt.uc.search.api.IUCUserInfoService#batchAdd(java.util.List)
	 */
	@Override
	public void batchAdd(List<SearchUserInfo> users) throws HsException {
		try {
			// System.out.println("添加批量用户信息：" + JSONObject.toJSONString(users));
			SystemLog.debug("UserInfoService", "batchAdd", "添加批量用户信息："
					+ JSONObject.toJSONString(users));
			solrServer.batchAdd(SearchUserInfo.CORE_NAME, users);
		} catch (Exception e) {
			e.printStackTrace();
			throw new HsException(
					ErrorCodeEnum.SOLR_INDEX_ADD_FAILED.getValue(),
					ErrorCodeEnum.SOLR_INDEX_ADD_FAILED.getDesc());
		}
	}

	@Override
	public void updateSearchMode(String custId, String searchMode) {
		try {
			// System.out.println("修改用户信息：" + JSONObject.toJSONString(user));
			SystemLog.debug("UserInfoService", "updateSearchMode",
					"修改用户searchMode：" + searchMode + "客户号：" + custId);
			// 判断入参是否为空
			if (StringUtils.isBlank(custId)
					|| StringUtils.isBlank(searchMode)) {
				throw new HsException(
						ErrorCodeEnum.SOLR_INDEX_UPDATE_FAILED.getValue(),
						"更新用户searchMode时，入参为空, 客户号：" + custId + "，searchMode："
								+ searchMode);
			}
			// 搜索旧数据
			SearchUserInfo user = indexSearchService.getById(SearchUserInfo.CORE_NAME,"custId", custId, SearchUserInfo.class);
			if(user == null){
				throw new HsException(
						ErrorCodeEnum.SOLR_INDEX_UPDATE_FAILED.getValue(),
						"更新用户searchMode时，未搜索到原有数据, 客户号：" + custId);
			}
			user.setSearchMode(searchMode);
			// 保存新数据
			solrServer.add(SearchUserInfo.CORE_NAME, user);
			
		} catch (Exception e) {
			// e.printStackTrace();
			SystemLog.error("UserInfoService", "updateSearchMode", "修改用户信息异常", e);
			throw new HsException(
					ErrorCodeEnum.SOLR_INDEX_UPDATE_FAILED.getValue(),
					ErrorCodeEnum.SOLR_INDEX_UPDATE_FAILED.getDesc());
		}
	}

	/**
	 * 更新用户信息
	 * 
	 * @param user
	 * @throws HsException
	 * @see com.gy.hsxt.uc.search.api.IUCUserInfoService#update(com.gy.hsxt.uc.search.bean.SearchUserInfo)
	 */
	@Override
	public void update(SearchUserInfo user) throws HsException {
		try {
			SystemLog.debug("UserInfoService", "update",
					"修改用户信息：" + JSONObject.toJSONString(user));
			solrServer.add(SearchUserInfo.CORE_NAME, user);
		} catch (Exception e) {
			// e.printStackTrace();
			SystemLog.error("UserInfoService", "update", "修改用户信息异常", e);
			throw new HsException(
					ErrorCodeEnum.SOLR_INDEX_UPDATE_FAILED.getValue(),
					ErrorCodeEnum.SOLR_INDEX_UPDATE_FAILED.getDesc());
		}
	}

	/**
	 * 删除用户信息
	 * 
	 * @param id
	 * @throws HsException
	 * @see com.gy.hsxt.uc.search.api.IUCUserInfoService#delete(java.lang.String)
	 */
	@Override
	public void delete(String id) throws HsException {
		try {
			// System.out.println("删除用户信息：" + id);
			SystemLog.debug("UserInfoService", "delete", "删除用户信息：" + id);
			solrServer.delete(SearchUserInfo.CORE_NAME, id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new HsException(
					ErrorCodeEnum.SOLR_INDEX_DELETE_FAILED.getValue(),
					ErrorCodeEnum.SOLR_INDEX_DELETE_FAILED.getDesc());
		}
	}

	/**
	 * 批量删除
	 * 
	 * @param ids
	 *            ID集合
	 * @throws HsException
	 */
	@Override
	public void batchDelete(List<String> ids) throws HsException {
		try {
			// System.out.println("批量删除用户信息：" + JSONObject.toJSONString(ids));
			SystemLog.debug("UserInfoService", "batchDelete", "批量删除用户信息："
					+ JSONObject.toJSONString(ids));
			solrServer.batchDelete(SearchUserInfo.CORE_NAME, ids);
		} catch (Exception e) {
			e.printStackTrace();
			throw new HsException(
					ErrorCodeEnum.SOLR_INDEX_DELETE_FAILED.getValue(),
					ErrorCodeEnum.SOLR_INDEX_DELETE_FAILED.getDesc());
		}
	}

}
