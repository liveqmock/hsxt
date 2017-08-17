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

import com.alibaba.fastjson.JSONObject;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.uc.search.api.IUCUserRoleService;
import com.gy.hsxt.uc.search.bean.ErrorCodeEnum;
import com.gy.hsxt.uc.search.bean.SearchUserRole;
import com.gy.hsxt.uc.search.server.SolrServerContext;

/**
 * 用户角色操作类
 * 
 * @Package: com.gy.hsxt.uc.index.service
 * @ClassName: UserRoleService
 * @Description: TODO
 * 
 * @author: lixuan
 * @date: 2016-4-9 下午3:51:23
 * @version V1.0
 */
@Service
public class UserRoleService implements IUCUserRoleService {
	@Autowired
	SolrServerContext solrServer;

	@Override
	public void add(List<SearchUserRole> roles) {
		try {
			System.out.println("添加角色：" + JSONObject.toJSONString(roles));
			solrServer.batchAdd(SearchUserRole.CORE_NAME, roles);
		} catch (Exception e) {
			e.printStackTrace();
			throw new HsException(
					ErrorCodeEnum.SOLR_INDEX_ADD_FAILED.getValue(),
					ErrorCodeEnum.SOLR_INDEX_ADD_FAILED.getDesc());
		}

	}

	@Override
	public void update(String custId, List<SearchUserRole> roles){
		// 删除操作员所有角色
		deleteByCustId(custId);
		// 添加角色
		add(roles);
	}
	
	@Override
	public void deleteByCustId(String custId) {
		try {
			// 获取用户的所有ID，然后再删除
			SolrQuery query = new SolrQuery();
			query.setQuery("custId:" + custId);
			QueryResponse resp = solrServer.search(SearchUserRole.CORE_NAME,
					query);
			List<String> ids = new ArrayList<>();
			
			SolrDocumentList list = resp.getResults();
			if (list != null && list.size() > 0) {
				for (SolrDocument sd : list) {
					ids.add((String) sd.get("id"));
				}
				solrServer.batchDelete(SearchUserRole.CORE_NAME, ids);
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new HsException(
					ErrorCodeEnum.SOLR_INDEX_DELETE_FAILED.getValue(),
					ErrorCodeEnum.SOLR_INDEX_DELETE_FAILED.getDesc());
		}

	}

}
