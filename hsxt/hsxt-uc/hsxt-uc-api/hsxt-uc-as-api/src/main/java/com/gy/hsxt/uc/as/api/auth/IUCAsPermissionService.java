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
import com.gy.hsxt.uc.as.bean.auth.AsPermission;

/**
 * 权限管理接口
 * 
 * @Package: com.gy.hsxt.uc.as.api
 * @ClassName: IUCPermissionService
 * @Description: TODO
 * 
 * @author: lvyan
 * @date: 2015-10-14 下午3:33:37
 * @version V1.0
 */
public interface IUCAsPermissionService {

    /**
     * 新增权限
     * 
     * @param vo
     *            权限信息
     * @param operator
     * @throws HsException
     */
    public void addPerm(AsPermission vo, String operator) throws HsException;

    /**
     * 删除权限 修改记录状态为N
     * 
     * @param permId
     * @param operator
     * @throws HsException
     */
    public void deletePerm(String permId, String operator) throws HsException;

    /**
     * 修改权限信息,只修改非null字段
     * 
     * @param vo
     *            权限信息
     * @param operator
     * @throws HsException
     */
    public void updatePerm(AsPermission vo, String operator) throws HsException;

    /**
     * 查询权限列表，可附加过滤条件：平台，子系统 ,权限类型,父权限id
     * 
     * @param platformCode
     *            null则忽略该条件
     * @param subSystemCode
     *            null则忽略该条件
     * @param permType
     *            null则忽略该条件
     * @param parentId
     *            父权限ID null则忽略该条件
     * @return
     * @throws HsException
     */
    public List<AsPermission> listPerm(String platformCode, String subSystemCode, Short permType, String parentId)
            throws HsException;
    
    /**
	 * 查询权限列表，可附加过滤条件：平台，子系统 ,权限类型,父权限id,权限编码
	 * 
	 * @param platformCode
	 *            null则忽略该条件
	 * @param subSystemCode
	 *            null则忽略该条件
	 * @param permType
	 *            null则忽略该条件
	 * @param parentId
	 *            父权限ID null则忽略该条件
	 * @param permCode
	 *            权限编码 null则忽略该条件
	 * @return
	 * @throws HsException
	 */
    public List<AsPermission> listPerm(String platformCode,
			String subSystemCode, Short permType, String parentId,String permCode)
			throws HsException;
    /**
	 * 分页查询权限列表，可附加过滤条件：平台，子系统 ,权限类型,父权限id
	 * 
	 * @param platformCode
	 *            null则忽略该条件
	 * @param subSystemCode
	 *            null则忽略该条件
	 * @param permType
	 *            null则忽略该条件
	 * @param parentId
	 *            父权限ID null则忽略该条件
	 * @param page
	 *            分页参数
	 * @return
	 * @throws HsException
	 */
	public PageData<AsPermission> listPermByPage(String platformCode,
			String subSystemCode, Short permType, String parentId, Page page)
			throws HsException;

	/**
	 * 分页查询权限列表，可附加过滤条件：平台，子系统 ,权限类型,父权限id,权限代码，权限名称
	 * @param platformCode null则忽略该条件
	 * @param subSystemCode null则忽略该条件
	 * @param permType null则忽略该条件
	 * @param parentId null则忽略该条件
	 * @param permCode null则忽略该条件
	 * @param permName null则忽略该条件
	 * @param page null则忽略该条件
	 * @return
	 * @throws HsException
	 */
	public PageData<AsPermission> listPermByPage(String platformCode,
			String subSystemCode, Short permType, String parentId,String permCode,String permName, Page page)
			throws HsException;
	
    /**
     * 根据权限类型查询权限列表，可附加过滤条件：平台，子系统
     * 
     * @param platformCode
     *            null则忽略该条件
     * @param subSystemCode
     *            null则忽略该条件
     * @param permType
     *            null则忽略该条件
     * @return
     * @throws HsException
     */
    public List<AsPermission> listPermByType(String platformCode, String subSystemCode, Short permType)
            throws HsException;

    /**
     * 查询用户拥有的权限 ,排除权限记录状态=N的记录，可附加过滤条件：平台，子系统,权限类型
     * 
     * @param platformCode 平台
     *            null则忽略该条件
     * @param subSystemCode 子系统
     *            null则忽略该条件
     * @param permType 权限类型
     *            null则忽略该条件
     * @param custId 用户id
     * @return
     * @throws HsException
     */
    public List<AsPermission> listPermByCustId(String platformCode, String subSystemCode, Short permType, String custId);
    /**
	 * 查询用户拥有的权限 ,排除权限记录状态=N的记录，可附加过滤条件：平台，子系统,父权限id,权限类型
	 * 
	 * @param platformCode 平台
	 *            null则忽略该条件
	 * @param subSystemCode 子系统
	 *            null则忽略该条件
	 * @param parentId 父id
	 *            null则忽略该条件
	 * @param permType 权限类型
	 *            null则忽略该条件
	 * @param custId 用户id
	 * @return
	 * @throws HsException
	 */
	public List<AsPermission> listPermByCustId(String platformCode,
			String subSystemCode, String parentId, Short permType, String custId)
			throws HsException;
	/**
	 * 查询用户拥有的权限 ,排除权限记录状态=N的记录，可附加过滤条件：平台，子系统,父权限id,权限类型,权限代码
	 * 
	 * @param platformCode 平台
	 *            null则忽略该条件
	 * @param subSystemCode 子系统
	 *            null则忽略该条件
	 * @param parentId 父id
	 *            null则忽略该条件
	 * @param permType 权限类型
	 *            null则忽略该条件
	 * @param custId 用户id
	 * @param permCode 权限代码，null则忽略该条件
	 * @return
	 * @throws HsException
	 */
	public List<AsPermission> listPermByCustId(String platformCode,
			String subSystemCode, String parentId, Short permType, String custId,String permCode)
			throws HsException;

    /**
     * 查询角色拥有的权限，可附加过滤条件：平台，子系统
     * 
     * @param platformCode
     *            null则忽略该条件
     * @param subSystemCode
     *            null则忽略该条件
     * @param roleId
     * @return
     * @throws HsException
     */
    public List<AsPermission> listPermByRoleId(String platformCode, String subSystemCode, String roleId)
            throws HsException;

    /**
     * 批量角色权限授权，跳过已有权限，插入未有权限。
     * 
     * @param roleId
     * @param list
     *            需授权权限id列表
     * @param operator
     * @throws HsException
     */
    public void grantPerm(String roleId, List<String> list, String operator) throws HsException;

    /**
     * 批量回收角色权限,删除角色授权记录
     * 
     * @param roleId
     * @param list
     *            需删除权限id列表,null则删除全部
     * @param operator
     * @throws HsException
     */
    public void revokePerm(String roleId, List<String> list, String operator) throws HsException;

    /**
     * 批量角色权限授权，删除已有权限，插入新权限。
     * 
     * @param roleId
     * @param list
     *            需授权权限id列表
     * @param operator
     * @throws HsException
     */
    public void resetPerm(String roleId, List<String> list, String operator) throws HsException;
    
    /**
	 * 获取角色已经拥有的权限id
	 * @param roleId 角色id
	 * @return 权限id集
	 */
	public Set<String> getRolePermSet(String roleId);
	
	/**
	 * 返回角色授权树
	 * @param platformCode 可授权条件 平台
	 * @param subSystemCode 可授权条件 子系统
	 * @param permType 可授权条件 权限类型
	 * @param parentId 可授权条件 父权限
	 * @param roleId 授权角色
	 * @return 返回角色授权树
	 */
	public List<Map<String, Object>> getRolePermTree(String platformCode,
			String subSystemCode, Short permType, String parentId, String roleId);
			
}
