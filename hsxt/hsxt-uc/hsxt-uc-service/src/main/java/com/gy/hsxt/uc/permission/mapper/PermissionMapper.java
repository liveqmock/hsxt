package com.gy.hsxt.uc.permission.mapper;

import java.util.List;
import java.util.Map;

import com.gy.hsxt.uc.permission.bean.Permission;

public interface PermissionMapper {
    /**
     * 删除单条权限记录
     * @param permId 权限id
     * @return
     */
    int deleteByPrimaryKey(String permId);
    /**
     * 
     * @param record
     * @return
     */
    int insert(Permission record);
    /**
     * 插入单条权限记录
     * @param record
     * @return
     */
    int insertSelective(Permission record);
    /**
     * 查找单条权限记录
     * @param permId
     * @return
     */
    Permission selectByPrimaryKey(String permId);
    /**
     * 权限查询列表
     * @param record 条件参数
     * @return
     */
    List<Permission> selectByPid(Permission record);
    /**
     * 权限查询列表
     * @param map 条件参数
     * @return
     */
    List<Permission> selectByRoleId(Map<String, Object> map);
    /**
     * 权限查询列表
     * @param map 条件参数
     * @return
     */
    List<Permission> selectByCustId(Map<String, Object> map);

    /**
     * 更新权限信息
     * @param record 更新内容
     * @return
     */
    int updateByPrimaryKeySelective(Permission record);
    /**
     * 更新权限信息
     * @param record 更新内容
     * @return
     */
    int updateByPrimaryKey(Permission record);
    /**
     * 插入角色权限授权信息
     * @param map 条件参数
     * @return
     */
    int insertRolePermission(Map<String, Object> map);
    /**
     * 删除角色权限授权信息
     * @param map 条件参数
     * @return
     */
    int deleteRolePermission(Map<String, Object> map);
}
