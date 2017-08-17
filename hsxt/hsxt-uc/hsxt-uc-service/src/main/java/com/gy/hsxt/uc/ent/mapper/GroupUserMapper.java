package com.gy.hsxt.uc.ent.mapper;

import org.apache.ibatis.annotations.Param;

import com.gy.hsxt.uc.ent.bean.GroupUser;

/**
 * 
 * 用户跟用户组关联关系 Mapper
 * 
 * @Package: com.gy.hsxt.uc.ent.mapper
 * @ClassName: GroupUserMapper
 * @Description: TODO
 * 
 * @author: huanggaoyang
 * @date: 2015-11-25 下午12:00:12
 * @version V1.0
 */
public interface GroupUserMapper {

	/**
	 * 删除用户跟用户组关联关系
	 * 
	 * @param groupId
	 *            用户组编号
	 * @param operCustId
	 *            用户编号（操作员客户号）
	 * @return
	 */
	int deleteGroupUser(@Param("groupId") Long groupId, @Param("operCustId") String operCustId);

	/**
	 * 插入用户跟用户组关联关系
	 * 
	 * @param record
	 *            用户跟用户组关系
	 * @return
	 */
	int insert(GroupUser record);

	/**
	 * 移除操作员用户组关联
	 * 
	 * @param operCustId
	 *            用户编号（操作员客户号）
	 * @return
	 */
	int removeUserGroup(@Param("operCustId") String operCustId);
	/**
	 * 清除用户组下的所有用户
	 * @param groupId
	 */
	void clearAllGroupUser(@Param("groupId") Long groupId);
}