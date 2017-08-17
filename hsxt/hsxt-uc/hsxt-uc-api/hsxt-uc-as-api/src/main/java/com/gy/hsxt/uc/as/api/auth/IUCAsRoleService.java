/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */

package com.gy.hsxt.uc.as.api.auth;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.uc.as.bean.auth.AsRole;

/**
 * 
 * @Package: com.gy.hsxt.uc.as.api.auth
 * @ClassName: IUCRoleService
 * @Description: TODO
 * 
 * @author: lvyan
 * @date: 2015-10-14 下午4:03:48
 * @version V1.0
 */
public interface IUCAsRoleService {

	/**
	 * 增加角色
	 * 
	 * @param vo
	 *            角色信息
	 * @param operator
	 * @throws HsException
	 */
	public String addRole(AsRole vo, String operator) throws HsException;

	/**
	 * 删除角色 修改记录状态为N
	 * 
	 * @param roleId 角色id
	 * @param operator
	 * @throws HsException
	 */
	public void deleteRole(String roleId, String operator) throws HsException;

	/**
	 * 修改角色信息,只修改非null字段
	 * 
	 * @param vo
	 *            角色信息
	 * @param operator
	 * @throws HsException
	 */
	public void updateRole(AsRole vo, String operator) throws HsException;

	/**
	 * 查询角色列表，可附加过滤条件：平台，子系统,角色类型，企业客户id
	 * @param platformCode 平台，
	 *            null则忽略该条件
	 * @param subSystemCode 子系统，
	 *            null则忽略该条件
	 * @param roleType 角色类型，
	 *            null则忽略该条件
	 * @param entCustId 企业客户号，
	 *            null则忽略该条件
	 * @return
	 * @throws HsException
	 */
	public List<AsRole> listRole(String platformCode, String subSystemCode, Short roleType,
			String entCustId) throws HsException;
	/**
	 * 查询角色列表，可附加过滤条件：平台，子系统,角色类型，企业客户id,企业类型
	 * @param platformCode 平台，
	 *            null则忽略该条件
	 * @param subSystemCode 子系统，
	 *            null则忽略该条件
	 * @param roleType 角色类型，
	 *            null则忽略该条件
	 * @param entCustId 企业客户号，
	 *            null则忽略该条件
	 * @param custType 企业类型， null则忽略该条件
	 * @return
	 * @throws HsException
	 */
	public List<AsRole> listRole(String platformCode, String subSystemCode,
			Short roleType, String entCustId,String entCustType) throws HsException ;
	/**
	 * 查询角色列表，可附加过滤条件：平台，子系统,角色类型，企业客户id,企业类型
	 * @param platformCode 平台，
	 *            null则忽略该条件
	 * @param subSystemCode 子系统，
	 *            null则忽略该条件
	 * @param roleType 角色类型，
	 *            null则忽略该条件
	 * @param entCustId 企业客户号，
	 *            null则忽略该条件
	 * @param custType 企业类型， null则忽略该条件
	 * @param roleName 角色名称， null则忽略该条件
	 * @return
	 * @throws HsException
	 */
	public List<AsRole> listRole(String platformCode, String subSystemCode,
			Short roleType, String entCustId, String entCustType,String roleName)
			throws HsException ;
	/**
	 * 分页查询角色列表，可附加过滤条件：平台，子系统,角色类型，企业客户id
	 * 
	 * @param platformCode 平台，
	 *            null则忽略该条件
	 * @param subSystemCode 子系统，
	 *            null则忽略该条件
	 * @param roleType 角色类型，
	 *            null则忽略该条件
	 * @param entCustId 企业客户号，
	 *            null则忽略该条件
	 * @return 根据page分页后单页数据
	 * @throws HsException
	 */
	public PageData<AsRole> listRoleByPage(String platformCode, String subSystemCode,
			Short roleType, String entCustId, Page page) throws HsException;
	/**
	 * 分页查询角色列表，可附加过滤条件：平台，子系统,角色类型，企业客户id,企业类型
	 * @param platformCode 平台，
	 *            null则忽略该条件
	 * @param subSystemCode 子系统，
	 *            null则忽略该条件
	 * @param roleType 角色类型，
	 *            null则忽略该条件
	 * @param entCustId 企业客户号，
	 *            null则忽略该条件
	 * @param custType 企业类型， null则忽略该条件
	 * @param page
	 * @return
	 * @throws HsException
	 */
	public PageData<AsRole> listRoleByPage(String platformCode, String subSystemCode,
			Short roleType, String entCustId,String custType, Page page) throws HsException;

	/**
	 * 查询角色列表，可附加过滤条件：平台，子系统,角色类型，企业客户id,企业类型
	 * @param platformCode 平台，
	 *            null则忽略该条件
	 * @param subSystemCode 子系统，
	 *            null则忽略该条件
	 * @param roleType 角色类型，
	 *            null则忽略该条件
	 * @param entCustId 企业客户号，
	 *            null则忽略该条件
	 * @param custType 企业类型， null则忽略该条件
	 * @param roleName 角色名称， null则忽略该条件
	 * @return
	 * @throws HsException
	 */
	public PageData<AsRole> listRoleByPage(String platformCode,
			String subSystemCode, Short roleType, String entCustId,
			String custType,String roleName, Page page) throws HsException;
	/**
	 * 根据角色类型查询角色列表，可附加过滤条件：平台，子系统 推荐使用 listRole(String platformCode, String
	 * subSystemCode, Short roleType,String entCustId)
	 * 
	 * @param platformCode 平台，
	 *            null则忽略该条件
	 * @param subSystemCode 子系统，
	 *            null则忽略该条件
	 * @param roleType 角色类型
	 *            null则忽略该条件
	 * @return
	 * @throws HsException
	 */
	@Deprecated
	public List<AsRole> listRoleByType(String platformCode, String subSystemCode, Short roleType)
			throws HsException;

	/**
	 * 查询用户拥有的角色 ,排除角色记录状态=N的记录
	 * 
	 * @param platformCode 平台，
	 *            null则忽略该条件
	 * @param subSystemCode 子系统，
	 *            null则忽略该条件
	 * @param custId 客户id
	 * @return
	 * @throws HsException
	 */
	public List<AsRole> listRoleByCustId(String platformCode, String subSystemCode, String custId)
			throws HsException;

	/**
	 * 查询企业可分配的角色 ,排除角色记录状态=N的记录，可附加过滤条件：平台，子系统 返回全局角色，平台角色，及企业私有角色
	 * 
	 * @param platformCode 平台，
	 *            null则忽略该条件
	 * @param subSystemCode 子系统，
	 *            null则忽略该条件
	 * @param entCustId
	 *            企业客户号
	 * @return 返回全局角色，平台角色，及企业私有角色
	 * @throws HsException
	 */
	public List<AsRole> selectByEntCustId(String platformCode, String subSystemCode,
			String entCustId) throws HsException;

	/**
	 * 批量用户角色授权，跳过已有角色，插入未有角色。
	 * 
	 * @param custId 客户id，
	 * @param list
	 *            需授权角色id列表
	 * @param operator
	 * @throws HsException
	 */
	public void grantRole(String custId, List<String> list, String operator) throws HsException;

	/**
	 * 批量回收用户角色,删除用户授权记录
	 * 
	 * @param custId 客户id，
	 * @param list
	 *            需删除角色id列表,null则删除全部
	 * @param operator
	 * @throws HsException
	 */
	public void revokeRole(String custId, List<String> list, String operator) throws HsException;

	/**
	 * 批量授权用户角色,删除已有记录，插入新记录
	 * 
	 * @param custId 客户id，
	 * @param list
	 *            需授权角色id列表
	 * @param operator
	 * @throws HsException
	 */
	public void resetRole(String custId, List<String> list, String operator) throws HsException;

	/**
	 * 判断用户是否拥有对应url权限
	 * 
	 * @param custId 客户id
	 * @param url 权限url
	 * @return
	 */
	public boolean hashUrlPermission(String custId, String url);

	/**
	 * 从数据库中获取用户角色并设置至缓存
	 * 
	 * @param custId
	 *            客户号
	 * @return Set<String 角色ID>
	 */
	public Set<String> resetCustRoleCache(String custId);

	/**
	 * 根据用户id获取用户角色ID集合 先从缓存获取，若无，从数据库获取
	 * 
	 * @param custId 客户id
	 * @return Set<String roleId>
	 */
	public Set<String> getCustRoleIdSet(String custId);

	/**
	 * 判断用户是否拥有对应roleId 先从缓存获取，若无，从数据库获取
	 * 
	 * @param custId 客户id
	 * @param roleId 角色id
	 * @return
	 */
	public boolean hasRoleId(String custId, String roleId);

	/**
	 * 获取用户角色授权树
	 * 
	 * @param platformCode
	 *            平台
	 * @param subSystemCode
	 *            子系统
	 * @param entCustId
	 *            企业客户号
	 * @param userCustId
	 *            用户ID
	 * @return 用户角色授权树
	 */
	public List<Map<String, Object>> getRoleTree(String platformCode, String subSystemCode,
			String entCustId, String userCustId);
}
