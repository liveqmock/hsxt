/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.gpf.um.service;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.gpf.um.bean.*;

/**
 * 操作员用户组关系业务接口
 *
 * @Package : com.gy.hsxt.gpf.um.service
 * @ClassName : IOperatorGroupService
 * @Description : 操作员用户组关系业务接口
 * @Author : chenm
 * @Date : 2016/2/4 15:57
 * @Version V3.0.0.0
 */
public interface IOperatorGroupService {

    /**
     * 为单个操作者批量添加关系
     *
     * @param operator 操作者
     * @return int
     * @throws HsException
     */
    int batchSaveBeanForOperator(Operator operator) throws HsException;


    /**
     * 为单个用户组批量添加关系
     *
     * @param group 用户组
     * @return int
     * @throws HsException
     */
    int batchSaveBeanForGroup(Group group) throws HsException;

    /**
     * 移除关系
     *
     * @param operatorGroup 关系实体
     * @throws HsException
     */
    int removeBean(OperatorGroup operatorGroup) throws HsException;


    /**
     * 从关联关系分页查询操作者列表
     *
     * @param gridPage           分页对象
     * @param operatorGroupQuery 查询实体
     * @return {@code GridData}
     * @throws HsException
     */
    GridData<Operator> queryOperatorListForPage(GridPage gridPage, OperatorGroupQuery operatorGroupQuery) throws HsException;

    /**
     * 从关联关系分页查询用户组列表
     *
     * @param gridPage           分页对象
     * @param operatorGroupQuery 查询实体
     * @return {@code GridData}
     * @throws HsException
     */
    GridData<Group> queryGroupListForPage(GridPage gridPage, OperatorGroupQuery operatorGroupQuery) throws HsException;

    /**
     * 处理操作员用户组关系列表
     *
     * @param operatorGroup 关系实体
     * @return boolean
     * @throws HsException
     */
    boolean dealOperatorGroupList(OperatorGroup operatorGroup) throws HsException;
}
