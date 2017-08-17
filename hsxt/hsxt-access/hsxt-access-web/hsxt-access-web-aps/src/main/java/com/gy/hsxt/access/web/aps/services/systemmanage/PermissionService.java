/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.access.web.aps.services.systemmanage;

import java.util.List;

import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.uc.as.bean.auth.AsPermission;

/**
 * 权限管理服务
 * 
 * @Package: com.gy.hsxt.access.web.mcs.services.systemmanage  
 * @ClassName: PermissionService 
 * @Description: TODO
 *
 * @author: zhangcy 
 * @date: 2016-1-12 下午12:28:49 
 * @version V1.0
 */
@SuppressWarnings("rawtypes")
public interface PermissionService extends IBaseService {

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
}
