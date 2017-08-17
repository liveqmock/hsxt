/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.uc.search.api;

import java.util.List;

import com.gy.hsxt.uc.search.bean.Paginate;
import com.gy.hsxt.uc.search.bean.SearchResult;
import com.gy.hsxt.uc.search.bean.SearchUserInfo;

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
public interface IUCUserInfoSearchService {

	/**
	 * 根据客户号查询消费者或企业超级管理员的信息
	 * 
	 * @param id
	 *            客户号
	 * @return
	 */
	public SearchUserInfo getById(String id);

	/**
	 * 查询持卡人和非持卡人信息
	 * 
	 * @param search
	 *            搜索条件
	 * 
	 * @return
	 */
	public SearchResult<SearchUserInfo> getCustomersByKeyword(
			SearchUserInfo search);

	/**
	 * 查询平台的消费者和企业的客户号
	 * 
	 * @param searchType
	 *            可使用SearchUserTypeEnum，搜索类型 0 全部 1 管理公司 2 服务公司 3 托管企业 4 成员企业
	 * @param paginate
	 *            分页信息
	 * @return
	 */
	public SearchResult<String> getCustIdsByPlatform(int searchType,
			Paginate paginate);

	/**
	 * 根据服务公司资源号查询成员企业或托管企业
	 * 
	 * @param entResNo
	 *            服务公司资源号
	 * @param searchType
	 *            可使用SearchUserTypeEnum，查询类型： 0 全部 1 管理公司 2 服务公司 3 托管企业 4 成员企业
	 * @param paginate
	 *            分页信息
	 * 
	 * @return
	 */
	public SearchResult<String> getCustIdsByServiceEntResNo(String entResNo,
			int searchType, Paginate paginate);

	/**
	 * 根据管理公司资源号查询下属企业的管理员的客户号
	 * 
	 * @param entResNo
	 *            管理公司资源号
	 * @param paginate
	 *            分页信息
	 * @return
	 */     
	public SearchResult<String> getCustIdsByMgtEntResNo(String entResNo, Paginate paginate);
	
	/**
	 * 根据多个ID搜索用户，不区分用户类型
	 * @param ids
	 * @return
	 */
	public SearchResult<SearchUserInfo> getByUserNames(List<String> usernames);
	
	/**
	 * 根据企业资源号获取所有操作员对象
	 * @param entResNo 企业资源号
	 * @return
	 */
	public SearchResult<SearchUserInfo> getOpersByEntCustId(String entResNo);
	
	/**
	 * 根据多个客户号查询用户信息
	 * @param custIds 客户号
	 * @return
	 */
	public SearchResult<SearchUserInfo> getByCustIds(List<String> custIds);
}
