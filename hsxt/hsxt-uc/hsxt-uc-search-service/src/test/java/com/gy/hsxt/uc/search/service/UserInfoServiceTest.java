/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.uc.search.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSONObject;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.uc.search.api.IUCUserInfoSearchService;
import com.gy.hsxt.uc.search.bean.Paginate;
import com.gy.hsxt.uc.search.bean.SearchResult;
import com.gy.hsxt.uc.search.bean.SearchUserInfo;
import com.gy.hsxt.uc.search.bean.SearchUserTypeEnum;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/spring-solr-server.xml" })
public class UserInfoServiceTest {

	@Autowired
	IUCUserInfoSearchService userInfoSearchService;

	// @Test
	public void getById() {

		try {
			SearchUserInfo u = userInfoSearchService.getById("0603201000043000000");
			System.out.println(u.toString());
		} catch (HsException e) {
			e.printStackTrace();
		}
	}

	//@Test
	public void getByIds(){
		List<String> custIds = new ArrayList<String>();
		custIds.add("0601612000120160509");
		custIds.add("0601612000220160509");
		SearchResult<SearchUserInfo> result = userInfoSearchService.getByCustIds(custIds);
		System.out.println(result);
	}
	
	//@Test
	public void getCustomersByKeyword() {
		try {
			SearchUserInfo search = new SearchUserInfo();
			search.setKeyword("06");
			search.setAgeScope("1,100");
			Paginate paginate = new Paginate(10);
			search.setPaginate(paginate);

			SearchResult<SearchUserInfo> result = userInfoSearchService
					.getCustomersByKeyword(search);
			System.out.println(result);

		} catch (HsException e) {
			e.printStackTrace();
		}
	} 

	//@Test
	public void getOpersByEntCustId() {
		try {//06002110000
			SearchResult<SearchUserInfo> result = userInfoSearchService
					.getOpersByEntCustId("06112110000");
			System.out.println(result);
		} catch (HsException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取平台下的客户号
	 */
	 @Test
	public void getCustIdsByPlatform() {
		try {
			Paginate paginate = new Paginate(10);
			// 其下所有企业管理员客户号以及消费者的客户号
			SearchResult<String> result = userInfoSearchService
					.getCustIdsByPlatform(SearchUserTypeEnum.ALL.getType(),
							paginate);
			System.out.println("平台下所有用户：" + result);
			// 所有管理公司的管理员客户号
			result = userInfoSearchService.getCustIdsByPlatform(
					SearchUserTypeEnum.ENT_MGT.getType(), paginate);
			System.out.println("平台下所有管理公司 ：" + result);
			//
			result = userInfoSearchService.getCustIdsByPlatform(
					SearchUserTypeEnum.ENT_SVR.getType(), paginate);
			System.out.println("平台下所有服务公司：" + result);

			// 平台下所有托管企业
			result = userInfoSearchService.getCustIdsByPlatform(
					SearchUserTypeEnum.ENT_TRUSTTEE.getType(), paginate);
			System.out.println("平台下所有托管企业：" + result);

			// 平台下所有成员企业
			result = userInfoSearchService.getCustIdsByPlatform(
					SearchUserTypeEnum.ENT_MEMEBER.getType(), paginate);
			System.out.println("平台下所有成员企业：" + result);
			// 平台下所有消费者
			result = userInfoSearchService.getCustIdsByPlatform(
					SearchUserTypeEnum.CONSUMER.getType(), paginate);
			System.out.println("平台下所有消费者：" + result);

		} catch (HsException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 管理公司资源号查询其下所有的服务公司管理员的操作号
	 */
	// @Test
	public void getCustIdsByMgtEntResNo() {
		try {
			Paginate paginate = new Paginate(10);
			// 管理公司资源号查询其下所有的服务公司管理员的操作号
			SearchResult<String> result = userInfoSearchService
					.getCustIdsByMgtEntResNo("01000000000", paginate);
			System.out.println("管理公司资源号查询所有的服务公司：" + result);

		} catch (HsException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 根据服务公司资源号查询托管企业+成员企业的客户号
	 */
	// @Test
	public void getCustIdsByServiceEntResNo() {
		try {
			Paginate paginate = new Paginate(10);
			// 服务公司资源号查询下属托管企业+成员企业
			SearchResult<String> result = userInfoSearchService
					.getCustIdsByServiceEntResNo("06001000000",
							SearchUserTypeEnum.ENT.getType(), paginate);
			System.out.println("服务公司资源号查询托管企业+成员企业：" + result);
			// 服务公司资源号查询下属托管企业
			result = userInfoSearchService.getCustIdsByServiceEntResNo(
					"06001000000", SearchUserTypeEnum.ENT_TRUSTTEE.getType(),
					paginate);
			System.out.println("服务公司资源号查询托管企业：" + result);
			// 服务公司资源号查询下属成员企业
			result = userInfoSearchService.getCustIdsByServiceEntResNo(
					"06001000000", SearchUserTypeEnum.ENT_MEMEBER.getType(),
					paginate);
			System.out.println("服务公司资源号查询成员企业：" + result);
		} catch (HsException e) {
			e.printStackTrace();
		}
	}

	// @Test
	public void getByUserNames() {
		List<String> idsList = new ArrayList<String>();
		/*
		 * idsList.add("06011000000000020160109");
		 * idsList.add("08186630000162206994400256");
		 */
		/*
		 * idsList.add("06011000000"); idsList.add("08186630000");
		 */

		idsList.add("06002113711");
		idsList.add("06001110041");
		idsList.add("06002113712");

		SearchResult<SearchUserInfo> result = userInfoSearchService
				.getByUserNames(idsList);

		List<SearchUserInfo> searchUserInfoList = result.getList();

		System.out.println(searchUserInfoList.size());

	}
}
