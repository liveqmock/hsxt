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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.uc.search.api.IUCUserInfoSearchService;
import com.gy.hsxt.uc.search.bean.Paginate;
import com.gy.hsxt.uc.search.bean.Search;
import com.gy.hsxt.uc.search.bean.SearchResult;
import com.gy.hsxt.uc.search.bean.SearchUserInfo;
import com.gy.hsxt.uc.search.bean.SearchUserRole;
import com.gy.hsxt.uc.search.bean.SearchUserTypeEnum;

/**
 * 搜索用户信息实现类
 * 
 * @Package: com.gy.hsxt.uc.search.service
 * @ClassName: UserInfoService
 * @Description: TODO
 * 
 * @author: lixuan
 * @date: 2015-11-20 下午2:27:08
 * @version V1.0
 */
@Service
public class UserInfoSearchService extends
		GenericIndexSearchService<SearchUserInfo, SearchUserInfo> implements
		IUCUserInfoSearchService {
	private Log log = LogFactory.getLog(this.getClass());
	@Autowired
	UserRoleSearchService userRoleSearchService;

	public UserInfoSearchService() {
		setCoreName(SearchUserInfo.CORE_NAME);
	}

	/**
	 * 根据客户号查询消费者或企业超级管理员的信息
	 * 
	 * @param id
	 *            客户号
	 * @return
	 * @see com.gy.hsxt.uc.search.api.IUCUserInfoSearchService#getById(java.lang.String)
	 */
	@Override
	public SearchUserInfo getById(String id) {
		SearchUserInfo user = getById(SearchUserInfo.CORE_NAME, "custId", id,
				SearchUserInfo.class);
		if (user != null) {
			List<SearchUserRole> roles = userRoleSearchService.getByCustIds(id);
			user.setUserRole(roles);
		}
		return user;
	}

	@Override
	public SearchResult<SearchUserInfo> getByUserNames(List<String> usernames) {
		SearchUserInfo search = new SearchUserInfo();
		search.setUsernames(usernames);
		Paginate paginate = new Paginate(3000);
		paginate.setCurrentPage(1);
		search.setPaginate(paginate);
		SearchResult<SearchUserInfo> result = search(search,
				SearchUserInfo.class);

		return result;
	}

	@Override
	public SearchResult<SearchUserInfo> getByCustIds(List<String> custIds) {
		SearchUserInfo search = new SearchUserInfo();
		search.setCustIds(custIds);
		Paginate paginate = new Paginate(3000);
		paginate.setCurrentPage(1);
		search.setPaginate(paginate);
		SearchResult<SearchUserInfo> result = search(search,
				SearchUserInfo.class);

		return result;
	}

	@Override
	public SearchResult<SearchUserInfo> getOpersByEntCustId(String entResNo) {
		SearchUserInfo search = new SearchUserInfo();
		search.setEntResNo(entResNo);
		search.setFilterQuery(new String[] { "userType:[2,5]" });
		search.setSearchType(1);
		Paginate paginate = new Paginate(100);
		paginate.setCurrentPage(1);
		search.setPaginate(paginate);
		SearchResult<SearchUserInfo> result = search(search,
				SearchUserInfo.class);
		if (result != null) {
			List<SearchUserInfo> list = result.getList();
			for (SearchUserInfo u : list) {
				List<SearchUserRole> roles = userRoleSearchService
						.getByCustIds(u.getCustId());
				u.setUserRole(roles);
			}
		}

		return result;
	}

	/**
	 * 查询持卡人和非持卡人信息
	 * 
	 * @param search搜索条件
	 *            ，只支持根据keyword、city,province, sex, age以及paginate查询
	 * @return
	 * @see com.gy.hsxt.uc.search.api.IUCUserInfoSearchService#getUsersByKeyword(java.lang.String)
	 */
	@Override
	public SearchResult<SearchUserInfo> getCustomersByKeyword(
			SearchUserInfo search) {
		search.setUserType(SearchUserTypeEnum.CONSUMER.getType());
		// 组装年龄段查询
		if (StringUtils.isNotBlank(search.getAgeScope())) {
			search.setFilterQuery(new String[] { "age:[" + search.getAgeScope()
					+ "]" });
		}
		SearchResult<SearchUserInfo> result = search(search,
				SearchUserInfo.class);

		return result;
	}

	/**
	 * 查询平台的用户和企业的客户号
	 * 
	 * @param searchType
	 * @param paginate
	 * @return
	 * @see com.gy.hsxt.uc.search.api.IUCUserInfoSearchService#getUsersByPlatformResNo(java.lang.String,
	 *      com.gy.hsxt.uc.search.bean.Paginate, int)
	 */
	@Override
	public SearchResult<String> getCustIdsByPlatform(int searchType,
			Paginate paginate) {
		SearchUserInfo search = new SearchUserInfo();
		search.setUserType(searchType);
		search.setPaginate(paginate);
		search.setFilterField("custId");
		// 如果查询包括消费者，只查询已登录过的消费者
		if (searchType == 1 || searchType == -2) {
			search.setIsLogin(1);
		}
		// 如果查询包括企业操作员，只查询0000
		if ((searchType >= 2 && searchType <= 5) || (searchType == -3)) {
			search.setUsername("0000");
		}
		SearchResult<String> result = search(search);
		return result;

	}

	/**
	 * 根据服务公司资源号查询成员企业或托管
	 * 
	 * @param entResNo
	 * @param searchType
	 * @param paginate
	 * @return
	 * @see com.gy.hsxt.uc.search.api.IUCUserInfoSearchService#getUsersByServiceEntResNo(java.lang.String,
	 *      com.gy.hsxt.uc.search.bean.Paginate, int)
	 */
	@Override
	public SearchResult<String> getCustIdsByServiceEntResNo(String entResNo,
			int searchType, Paginate paginate) {
		SearchUserInfo search = new SearchUserInfo();
		search.setPaginate(paginate);
		search.setUserType(searchType);
		search.setFilterField("custId");
		search.setUsername("0000");
		search.setEntResNo(entResNo.substring(0, 5));
		SearchResult<String> result = search(search);
		return result;
	}

	/**
	 * 获取管理公司下的所有服务公司的管理员客户号
	 * 
	 * @param entResNo
	 * @param paginate
	 * @return
	 * @see com.gy.hsxt.uc.search.api.IUCUserInfoSearchService#getUsersByMgtEntResNo(java.lang.String,
	 *      com.gy.hsxt.uc.search.bean.Paginate)
	 */
	@Override
	public SearchResult<String> getCustIdsByMgtEntResNo(String entResNo,
			Paginate paginate) {
		SearchUserInfo search = new SearchUserInfo();
		search.setUserType(SearchUserTypeEnum.ENT_SVR.getType());
		search.setPaginate(paginate);
		search.setFilterField("custId");
		search.setUsername("0000");
		search.setEntResNo(entResNo.substring(0, 2));
		SearchResult<String> result = search(search);
		return result;
	}

	@Override
	protected String composeQuery(Search search) {
		// 查询条件
		SearchUserInfo query = (SearchUserInfo) search;
		SystemLog.debug("UserInfoSearchService", "composeQuery","查询条件：" + query);
		StringBuilder sb = new StringBuilder();
		// 根据查询条件组装查询语句
		if (query.getSearchType().intValue() == 1) {
			// 根据企业资源号获取下面所有的操作员
			sb.append(" entResNo:").append(query.getEntResNo());
			if (StringUtils.isNoneBlank(query.getUsername())) {
				sb.append(" AND username:").append(query.getUsername());
			}
			SystemLog.debug("UserInfoSearchService", "composeQuery","查询语句：" + sb.toString());
			return sb.toString();
		}
		if (query.getUsernames() != null && query.getUsernames().size() > 0) {
			StringBuilder tmp = new StringBuilder();
			tmp.append("(");
			for (String id : query.getUsernames()) {
				tmp.append("resNo:").append(id).append(" OR ");
				tmp.append("mobile:").append(id).append(" OR ");
			}
			sb.append(tmp.toString().substring(0, tmp.toString().length() - 3))
					.append(") ");
			sb.append(" AND (userType:1 OR userType:0)");
		}

		if (query.getCustIds() != null && query.getCustIds().size() > 0) {
			StringBuilder tmp = new StringBuilder();
			tmp.append("(");
			for (String id : query.getCustIds()) {
				tmp.append("custId:").append(id).append(" OR ");
			}
			sb.append(tmp.toString().substring(0, tmp.toString().length() - 3))
					.append(") ");
		}

		// 组装查询条件
		Integer userType = query.getUserType();
		if (userType != null) {
			if (userType.intValue() >= 0) {
				sb.append("userType:").append(userType);
				// 查询多个用户类型, 企业操作员
				if (StringUtils.isNotBlank(query.getEntResNo())) {
					sb.append(" AND entResNo:").append(query.getEntResNo())
							.append("* ");
				}
				if (StringUtils.isNoneBlank(query.getUsername())) {
					sb.append(" AND username:").append(query.getUsername());
				}
			} else if (userType == SearchUserTypeEnum.ENT.getType()) {
				// 查询企业
				sb.append(" (userType:2 OR userType:3) ");
				if (StringUtils.isNotBlank(query.getEntResNo())) {
					sb.append(" AND entResNo:").append(query.getEntResNo())
							.append("* ");
				}
				if (StringUtils.isNoneBlank(query.getUsername())) {
					sb.append(" AND username:").append(query.getUsername());
				}
			} else if (userType == SearchUserTypeEnum.CONSUMER.getType()) {
				// 查询消费者
				sb.append(" (userType:0 OR userType:1) "); 
				if (StringUtils.isNotBlank(query.getKeyword())) {
					sb.append(" AND ((searchMode:1 AND (mobile:*").append(query.getKeyword())
							.append("* OR resNo:*").append(query.getKeyword())
							.append("* OR nickName:*")
							.append(query.getKeyword()).append("*)) ");
					
					 sb.append(" OR  (searchMode:2 AND resNo:").append(query.getKeyword()).append(")");
					 sb.append(" OR (searchMode:3 AND mobile:").append(query.getKeyword()).append(")");
					 sb.append(")");
				}
				if (StringUtils.isNotBlank(query.getCity())) {
					sb.append(" AND city:").append(query.getCity());
				}
				if (StringUtils.isNotBlank(query.getProvince())) {
					sb.append(" AND province:").append(query.getProvince());
				}
				if (StringUtils.isNotBlank(query.getSex())) {
					sb.append(" AND sex:").append(query.getSex());
				}
				if (query.getAge() != null && query.getAge().intValue() > 0) {
					sb.append(" AND age:").append(query.getAge().intValue());
				}
			}
			// 查询所有用户，操作员只查询0000
			else if (userType.intValue() == SearchUserTypeEnum.ALL.getType()) {
				sb.append("(userType:0 AND isLogin:1) OR (userType:1 AND isLogin:1) OR (userType:2 AND username:0000) OR (userType:3 AND username:0000) OR (userType:4 AND username:0000)OR (userType:5 AND username:0000)");
			}
		}

		if (query.getIsLogin() != null) {
			sb.append(" AND isLogin:").append(query.getIsLogin().intValue());
		}
		SystemLog.debug("UserInfoSearchService", "composeQuery", "查询语句组装完成。" + sb.toString());
		return sb.toString();

	}
}