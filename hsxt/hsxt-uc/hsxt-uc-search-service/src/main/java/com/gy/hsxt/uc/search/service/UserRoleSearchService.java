/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.uc.search.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.gy.hsxt.uc.search.api.IUCUserRoleSearchService;
import com.gy.hsxt.uc.search.bean.Paginate;
import com.gy.hsxt.uc.search.bean.Search;
import com.gy.hsxt.uc.search.bean.SearchResult;
import com.gy.hsxt.uc.search.bean.SearchUserRole;

/**
 * 用户角色搜索接口
 * 
 * @Package: com.gy.hsxt.uc.index.service
 * @ClassName: UserRoleSearchService
 * @Description: TODO
 * 
 * @author: lixuan
 * @date: 2016-4-9 下午3:51:23
 * @version V1.0
 */
@Service
public class UserRoleSearchService extends
		GenericIndexSearchService<SearchUserRole, SearchUserRole> implements
		IUCUserRoleSearchService {
	private Log log = LogFactory.getLog(this.getClass());

	public UserRoleSearchService() {
		setCoreName(SearchUserRole.CORE_NAME);
	}
	
	@Override
	public List<SearchUserRole> getByCustIds(String custId) {
		log.debug("开始搜索角色，客户号：" + custId);
		SearchUserRole search = new SearchUserRole();
		Paginate paginate = new Paginate(MAX_ROWS);
		search.setCustId(custId);
		search.setPaginate(paginate);
		SearchResult<SearchUserRole> result = search(search, SearchUserRole.class);
		if(result == null){
			return null;
		}
		return result.getList();
	}
	
	@Override
	protected String composeQuery(Search search) {
		// 查询条件
		SearchUserRole query = (SearchUserRole) search;
		
		StringBuilder sb = new StringBuilder();
		String custIdStr = query.getCustId();
		if(StringUtils.isNoneBlank(custIdStr)){
			String[] custIds = custIdStr.split(",");
			for(String id : custIds){
				sb.append("custId:").append(id).append(" OR ");
			}
		}
		
		return sb.toString().substring(0, sb.toString().length() -3);
	}
}
