/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.uc.search.api;

import java.util.List;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.uc.search.bean.SearchNetworkInfo;
import com.gy.hsxt.uc.search.bean.SearchUserInfo;

/**
 * 
 * 用户信息操作类，提供增加、修改、删除操作
 * 
 * @Package: com.gy.hsxt.uc.search.api  
 * @ClassName: IUCUserInfoService 
 * @Description: TODO
 *
 * @author: lixuan 
 * @date: 2015-11-19 下午12:21:34 
 * @version V1.0
 */
public interface IUCUserInfoService {

	/**
	 * 添加用户信息索引
	 * @param user 用户对象
	 * @throws HsException
	 */
	public void add(SearchUserInfo user)throws HsException;

	/**
	 * 批量添加用户信息
	 * @param users 用户列表
	 * @throws HsException
	 */
	public void batchAdd(List<SearchUserInfo> users) throws HsException;
	/**
	 * 更新用户信息
	 * @param user 用户对象
	 * @throws HsException
	 */
	public void update(SearchUserInfo user) throws HsException;
	
	/**
	 * 删除用户信息
	 * @param id ID
	 * @throws HsException
	 */
	public void delete(String id) throws HsException;
	
	/**
	 * 批量删除索引
	 * @param ids ID集合
	 * @throws HsException
	 */
	public void batchDelete(List<String> ids)throws HsException;
	
	/**
	 * 更新用户的搜索模式
	 * @param custId 客户号
	 * @param searchMode 0  不允许搜索；1  模糊搜索；2持卡人允许使用精确互生号搜索；3非持卡人使用精确手机号搜索
	 */
	public void updateSearchMode(String custId, String searchMode);
}
