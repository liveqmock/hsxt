package com.gy.hsxt.uc.ent.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.uc.ent.bean.EntGroup;

/**
 * 企业用户组 Mapper类
 * 
 * @Package: com.gy.hsxt.uc.ent.mapper
 * @ClassName: EntGroupMapper
 * @Description: TODO
 * 
 * @author: huanggaoyang
 * @date: 2015-11-25 上午11:19:06
 * @version V1.0
 */
public interface EntGroupMapper {

	/**
	 * 删除用户组
	 * 
	 * @param groupId
	 *            用户组编号
	 * @return
	 */
	int deleteByGroupId(Long groupId);

	/**
	 * 查询用户组的用户数量
	 * @param groupId	用户组编号
	 * @return
	 */
	int countCurrentGroup(Long groupId);
	/**
	 * 插入用户组
	 * 
	 * @param record
	 *            用户组记录
	 * @return
	 */
	int insert(EntGroup record);

	/**
	 * 查询用户组信息
	 * 
	 * @param groupId
	 *            用户组编号
	 * @return
	 */
	EntGroup selectByGroupId(Long groupId);

	/**
	 * 查询用户组信息通过组名
	 * 
	 * @param entCustId
	 *            企业客户号
	 * @param groupName
	 *            组名
	 * @return
	 */
	EntGroup selectByGroupName(@Param("entCustId") String entCustId,
			@Param("groupName") String groupName);

	/**
	 * 修改用户组
	 * 
	 * @param record
	 *            用户组记录
	 * @return
	 */
	int updateByGroupId(EntGroup record);

	/**
	 * 分页查询企业用户组
	 * 
	 * @param entCustId
	 *            企业客户号
	 * @param page
	 *            分页参数
	 * @return
	 */
	List<EntGroup> pageGroup(@Param("entCustId") String entCustId, @Param("groupName") String groupName,
			@Param("page") Page page);

	/**
	 * 统计用户组数量
	 * 
	 * @param entCustId
	 *            企业客户号
	 * @param page
	 *            分页参数
	 * @return
	 */
	int countGroup(@Param("entCustId") String entCustId, @Param("groupName") String groupName,
			@Param("page") Page page);

}