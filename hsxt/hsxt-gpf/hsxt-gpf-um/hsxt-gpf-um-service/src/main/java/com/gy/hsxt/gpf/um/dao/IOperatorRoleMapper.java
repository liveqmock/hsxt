/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.gpf.um.dao;

import com.gy.hsxt.gpf.um.bean.OperatorRole;
import com.gy.hsxt.gpf.um.bean.OperatorRoleQuery;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 操作者角色关系数据库层接口
 *
 * @Package : com.gy.hsxt.gpf.um.dao
 * @ClassName : IOperatorRoleMapper
 * @Description : 操作者角色关系数据库层接口
 * @Author : chenm
 * @Date : 2016/1/27 16:07
 * @Version V3.0.0.0
 */
@Repository("operatorRoleMapper")
public interface IOperatorRoleMapper extends IBaseMapper<OperatorRole> {

    /**
     * 根据查询实体查询操作员角色名称列表
     *
     * @param operatorRoleQuery 查询实体
     * @return {@code list}
     */
    List<String> selectRoleNamesByQuery(OperatorRoleQuery operatorRoleQuery);

    /**
     * 批量插入操作员角色关系列表
     *
     * @param operatorRole 关系实体
     */
    void batchInsertForOperator(OperatorRole operatorRole);

    /**
     * 批量删除操作员角色关系列表
     *
     * @param operatorRole 关系实体
     */
    void batchDeleteForOperator(OperatorRole operatorRole);

    /**
     * 批量删除操作员角色关系
     *
     * @param operatorId 操作员Id
     * @return int
     */
    int batchDeleteByOperatorId(@Param("operatorId") String operatorId);

    /**
     * 批量删除操作员角色关系
     *
     * @param roleId 角色ID
     * @return int
     */
    int batchDeleteByRoleId(@Param("roleId") String roleId);
}
