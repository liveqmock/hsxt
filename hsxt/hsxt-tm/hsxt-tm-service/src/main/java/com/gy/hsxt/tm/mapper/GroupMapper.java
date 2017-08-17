/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.tm.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gy.hsxt.tm.bean.Group;

/**
 * 值班组mapper dao映射类
 * 
 * @Package: com.gy.hsxt.tm.mapper
 * @ClassgroupName: GroupMapper
 * @Description: TODO
 * 
 * @author: kongsl
 * @date: 2015-11-11 下午6:06:12
 * @version V3.0.0
 */
public interface GroupMapper {

    /**
     * 保存值班组
     * 
     * @param group
     *            值班组信息
     * @return 成功记录数
     */
    public int insertGroup(Group group);

    /**
     * 查询是否存在同名的值班组
     * 
     * @param groupName
     *            值班组名称
     * @param groupId
     *            值班组编号
     * @return 记录数
     */
    public int findGroupIsExists(@Param("groupName") String groupName, @Param("groupId") String groupId);

    /**
     * 修改时查询是否有重复的组名
     * 
     * @param entCustId
     *            企业客户号
     * @param groupId
     *            值班组编号
     * @param groupName
     *            值班组名称
     * @return 记录数
     */
    public int findGroupNameIsExistsByModify(@Param("entCustId") String entCustId, @Param("groupId") String groupId,
            @Param("groupName") String groupName);

    /**
     * 保存时检验值班组名是否存在
     * 
     * @param entCustId
     *            企业客户号
     * @param groupName
     *            值班组名称
     * @return 记录数
     */
    public int findGroupNameIsExists(@Param("entCustId") String entCustId, @Param("groupName") String groupName);

    /**
     * 更新值班组
     * 
     * @param group
     *            值班组信息
     * @return 成功记录数
     */
    public int updateGroup(Group group);

    /**
     * 更新值班组类型
     * 
     * @param groupType
     *            值班组类型
     * @return 成功记录数
     */
    public int updateGroupType(@Param("groupId") String groupId, @Param("groupType") Integer groupType);

    /**
     * 更新值班组开关状态
     * 
     * @param groupId
     *            值班组类型
     * @param opened
     *            开关状态
     * @return 成功记录数
     */
    public int updateGroupOpened(@Param("groupId") String groupId, @Param("opened") Integer opened);

    /**
     * 查询企业开启的值班组列表
     * 
     * @param entCustId
     *            企业客户号
     * @return 值班组列表
     */
    public List<Group> findOpenedGroupList(@Param("entCustId") String entCustId);

    /**
     * 查询值班组列表
     * 
     * @param entCustId
     *            企业客户号
     * @return 值班组列表
     */
    public List<Group> findGroupList(@Param("entCustId") String entCustId);

    /**
     * 修改值班组关联查询出值班员
     * 
     * @param groupId
     *            值班组编号
     * @return 值班组信息
     */
    public List<Group> findGroupAndOperatorResMap(@Param("groupId") String groupId);

    /**
     * 查询所有值班且关联出值班员
     * 
     * @param entCustId
     *            企业客户号
     * @param isOpen
     *            是否查询所有
     * @param isOpen
     *            是否开启
     * @return 值班组信息
     */
    public List<Group> findGroupAndOperator(@Param("entCustId") String entCustId, @Param("isAll") boolean isAll,
            @Param("isOpen") boolean isOpen);

}
