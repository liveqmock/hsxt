package com.gy.hsxt.lcs.mapper;

import java.util.List;

import com.gy.hsxt.lcs.bean.SysUser;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-gpf-gcs-service
 * 
 *  Package Name    : com.gy.hsxt.gpf.gcs.mapper
 * 
 *  File Name       : UserMapper.java
 * 
 *  Creation Date   : 2015-7-05
 * 
 *  Author          : liuhq
 * 
 *  Purpose         : 系统管理员UserMapper接口
 * 
 * 
 *  History         : TODO
 * 
 * </PRE>
 ***************************************************************************/
public interface UserMapper {
	/**
	 * 插入记录
	 * 
	 * @param user
	 *            实体类 必须，非null
	 * @return 返int类型 1成功，其他失败
	 */
	public int insert(SysUser user);

	/**
	 * 读取某个用户的信息
	 * 
	 * @param user
	 *            实体类 必须，非null
	 * @return 返回实体类SysUser
	 */
	public SysUser getUserName(SysUser user);

	/**
	 * 获取用户列表
	 * 
	 * @return 返回List<SysUser>，数据为空，返回空List<SysUser>
	 */
	public List<SysUser> getUserNameList();

	/**
	 * 更新某条记录
	 * 
	 * @param user
	 *            实体类 必须，非null
	 * @return 返int类型 1成功，其他失败
	 */
	public int update(SysUser user);

	/**
	 * 删除某条记录
	 * 
	 * @param userName
	 *            用户id 必须，非null
	 * @return 返int类型 1成功，其他失败
	 */
	public int delete(String userName);

}
