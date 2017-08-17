/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.gpf.um.dao;

import com.gy.hsxt.gpf.um.bean.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 操作者用户组关系数据库层接口
 *
 * @Package : com.gy.hsxt.gpf.um.dao
 * @ClassName : IOperatorGroupMapper
 * @Description : 操作者用户组关系数据库层接口
 * @Author : chenm
 * @Date : 2016/1/27 16:09
 * @Version V3.0.0.0
 */
@Repository("operatorGroupMapper")
public interface IOperatorGroupMapper {

    /**
     * 为单个操作者批量添加关系
     *
     * @param operator 操作者
     * @return int
     */
    int batchInsertForOperator(Operator operator);

    /**
     * 为单个操作者批量添加关系
     *
     * @param operatorGroup 关系实体
     * @return int
     */
    int batchInsertForOperGroup(OperatorGroup operatorGroup);


    /**
     * 为单个用户组批量添加关系
     *
     * @param group 用户组
     * @return int
     */
    int batchInsertForGroup(Group group);

    /**
     * 移除关系
     *
     * @param operatorGroup 关系实体
     * @return int
     */
    int deleteBean(OperatorGroup operatorGroup);

    /**
     * 查询符合条件的总记录数
     *
     * @param operatorGroupQuery 查询对象
     * @return int
     */
    int selectOperatorCountForPage(OperatorGroupQuery operatorGroupQuery);

    /**
     * 分页查询符合条件的记录
     *
     * @param gridPage           分页对象
     * @param operatorGroupQuery 查询对象
     * @return list
     */
    List<Operator> selectOperatorListForPage(@Param("gridPage") GridPage gridPage, @Param("operatorGroupQuery") OperatorGroupQuery operatorGroupQuery);

    /**
     * 查询符合条件的总记录数
     *
     * @param operatorGroupQuery 查询对象
     * @return int
     */
    int selectGroupCountForPage(OperatorGroupQuery operatorGroupQuery);

    /**
     * 分页查询符合条件的记录
     *
     * @param gridPage           分页对象
     * @param operatorGroupQuery 查询对象
     * @return list
     */
    List<Group> selectGroupListForPage(@Param("gridPage") GridPage gridPage, @Param("operatorGroupQuery") OperatorGroupQuery operatorGroupQuery);

    /**
     * 批量删除操作员用户组关系列表
     *
     * @param operatorGroup 关系实体
     * @return int
     */
    int batchDeleteForOperator(OperatorGroup operatorGroup);

    /**
     * 查询操作者所有的用户组名称
     *
     * @param operatorGroupQuery 查询实体
     * @return list
     */
    List<String> selectGroupNamesByQuery(OperatorGroupQuery operatorGroupQuery);
}
