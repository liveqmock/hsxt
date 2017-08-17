package com.gy.hsxt.uc.permission.mapper;

import java.util.List;
import java.util.Map;

import com.gy.hsxt.uc.permission.bean.Role;

public interface RoleMapper {
    /**
     * 删除单条记录
     * @param roleId 记录id
     * @return
     */
    int deleteByPrimaryKey(String roleId);
    /**
     * 插入单条记录
     * @param record 角色信息
     * @return
     */
    int insert(Role record);
    /**
     * 插入单条记录
     * @param record 角色信息
     * @return
     */
    int insertSelective(Role record);
    /**
     * 查找单条记录
     * @param roleId 角色id
     * @return
     */
    Role selectByPrimaryKey(String roleId);
    
    /**
     * 查询已存在角色信息
     * @param record
     * @return
     */
    List<Role> selectBySelective(Role record);
    /**
     * 查询角色列表
     * @param record 查询条件
     * @return
     */
    List<Role> selectByType(Role record);
    /**
     * 根据企业类型查询角色列表
     * @param map 查询条件
     * @return
     */
    List<Role> selectByCustType(Map<String, Object> map);
    /**
     * 查询角色列表
     * @param map 查询条件
     * @return
     */
    List<Role> selectByCustId(Map<String, Object> map);
    
    /**
     * 查询企业可选角色列表
     * @param map 查询条件
     * @return
     */
    List<Role> selectByEntCustId(Map<String, Object> map);
    
    /**
     * 更新单条记录
     * @param record 角色信息
     * @return
     */
    int updateByPrimaryKeySelective(Role record);
    /**
     * 更新单条记录
     * @param record 角色信息
     * @return
     */
    int updateByPrimaryKey(Role record);
    /**
     * 插入用户角色授权信息
     * @param map 操作条件参数
     * @return
     */
    int insertCustRole(Map<String, Object> map);
    /**
     * 删除用户角色授权信息
     * @param map 操作条件参数
     * @return
     */
    int deleteCustRole(Map<String, Object> map);
    
    /**
     * 查询角色被哪些用户使用中
     * @param roleId
     * @return
     */
    List<Map<String,String>> selectRoleUsers(String roleId);
}
