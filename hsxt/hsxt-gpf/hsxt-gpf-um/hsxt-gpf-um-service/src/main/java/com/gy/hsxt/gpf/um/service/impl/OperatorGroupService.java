/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.gpf.um.service.impl;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.HsAssert;
import com.gy.hsxt.gpf.um.bean.*;
import com.gy.hsxt.gpf.um.dao.IOperatorGroupMapper;
import com.gy.hsxt.gpf.um.enums.UMRespCode;
import com.gy.hsxt.gpf.um.service.IOperatorGroupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 操作员用户组关系业务实现
 *
 * @Package : com.gy.hsxt.gpf.um.service.impl
 * @ClassName : OperatorGroupService
 * @Description : 操作员用户组关系业务实现
 * @Author : chenm
 * @Date : 2016/2/4 16:24
 * @Version V3.0.0.0
 */
@Service
public class OperatorGroupService implements IOperatorGroupService {

    /**
     * 日志
     */
    private Logger logger = LoggerFactory.getLogger(OperatorGroupService.class);

    /**
     * 操作者用户组关系数据库层接口
     */
    @Resource
    private IOperatorGroupMapper operatorGroupMapper;

    /**
     * 为单个操作者批量添加关系
     *
     * @param operator 操作者
     * @return int
     * @throws HsException
     */
    @Override
    public int batchSaveBeanForOperator(Operator operator) throws HsException {
        logger.info("======为单个操作者批量添加关系,参数[operator]:{}=========", operator);
        HsAssert.notNull(operator, UMRespCode.UM_PARAM_NULL_ERROR, "用户组[operator]为null");
        HsAssert.hasText(operator.getOperatorId(), UMRespCode.UM_PARAM_EMPTY_ERROR, "操作员ID[groupId]为空");
        HsAssert.notEmpty(operator.getGroups(), UMRespCode.UM_PARAM_EMPTY_ERROR, "用户组ID列表[groups]为空");
        try {
            return operatorGroupMapper.batchInsertForOperator(operator);
        } catch (Exception e) {
            logger.error("======[异常]为单个操作者批量添加关系,参数[operator]:{}=========", operator);
            throw new HsException(UMRespCode.UM_OPERATOR_GROUP_DB_ERROR, "为单个操作者批量添加关系异常，原因:" + e.getMessage());
        }
    }

    /**
     * 为单个用户组批量添加关系
     *
     * @param group     用户组
     * @return int
     * @throws HsException
     */
    @Override
    public int batchSaveBeanForGroup(Group group) throws HsException {
        logger.info("======为单个用户组批量添加关系,参数[group]:{}=========", group);
        HsAssert.notNull(group, UMRespCode.UM_PARAM_NULL_ERROR, "用户组[group]为null");
        HsAssert.hasText(group.getGroupId(), UMRespCode.UM_PARAM_EMPTY_ERROR, "用户组ID[groupId]为空");
        HsAssert.notEmpty(group.getOperators(), UMRespCode.UM_PARAM_EMPTY_ERROR, "操作员ID列表[operators]为空");
        try {
            return operatorGroupMapper.batchInsertForGroup(group);
        } catch (Exception e) {
            logger.error("======[异常]为单个用户组批量添加关系,参数[group]:{}=========", group);
            throw new HsException(UMRespCode.UM_OPERATOR_GROUP_DB_ERROR, "为单个用户组批量添加关系异常，原因:" + e.getMessage());
        }
    }

    /**
     * 移除关系
     *
     * @param operatorGroup 关系实体
     * @return int
     * @throws HsException
     */
    @Override
    public int removeBean(OperatorGroup operatorGroup) throws HsException {
        logger.info("======移除操作员用户组关系[operatorGroup]:{}=======", operatorGroup);
        HsAssert.notNull(operatorGroup, UMRespCode.UM_PARAM_NULL_ERROR, "操作者用户组关系查[operatorGroup]为null");
        HsAssert.hasText(operatorGroup.getGroupId(), UMRespCode.UM_PARAM_EMPTY_ERROR, "用户组ID[groupId]为空");
        HsAssert.hasText(operatorGroup.getOperatorId(), UMRespCode.UM_PARAM_EMPTY_ERROR, "操作员ID[operatorId]为空");

        try {
            return operatorGroupMapper.deleteBean(operatorGroup);
        } catch (Exception e) {
            logger.info("======[异常]移除操作员用户组关系[operatorGroup]:{}=======", operatorGroup, e);
            throw new HsException(UMRespCode.UM_OPERATOR_GROUP_DB_ERROR, "移除操作员用户组关系异常，原因:" + e.getMessage());
        }
    }

    /**
     * 从关联关系分页查询操作者列表
     *
     * @param gridPage           分页对象
     * @param operatorGroupQuery 查询实体
     * @return {@code GridData}
     * @throws HsException
     */
    @Override
    public GridData<Operator> queryOperatorListForPage(GridPage gridPage, OperatorGroupQuery operatorGroupQuery) throws HsException {
        logger.info("=======从关联关系分页查询操作者列表,参数[operatorGroupQuery]:{}==========", operatorGroupQuery);
        HsAssert.notNull(operatorGroupQuery, UMRespCode.UM_PARAM_NULL_ERROR, "操作者用户组关系查询实体[operatorGroupQuery]为null");
        HsAssert.hasText(operatorGroupQuery.getGroupId(), UMRespCode.UM_PARAM_EMPTY_ERROR, "用户组ID[groupId]为空");
        operatorGroupQuery.setOperatorId(null);//设置为null

        try {
            //查询符合条件的总记录数
            int totalRows = operatorGroupMapper.selectOperatorCountForPage(operatorGroupQuery);
            //分页查询符合条件的记录
            List<Operator> operators = operatorGroupMapper.selectOperatorListForPage(gridPage, operatorGroupQuery);
            //返回结果
            return GridData.bulid(true, totalRows, gridPage.getCurPage(), operators);
        } catch (Exception e) {
            logger.error("=======从关联关系分页查询操作者列表异常,参数[operatorGroupQuery]:{}==========", operatorGroupQuery, e);
            return GridData.bulid(false, 0, gridPage.getCurPage(), null);
        }
    }

    /**
     * 从关联关系分页查询用户组列表
     *
     * @param gridPage           分页对象
     * @param operatorGroupQuery 查询实体
     * @return {@code GridData}
     * @throws HsException
     */
    @Override
    public GridData<Group> queryGroupListForPage(GridPage gridPage, OperatorGroupQuery operatorGroupQuery) throws HsException {
        logger.info("=======从关联关系分页查询用户组列表,参数[operatorGroupQuery]:{}==========", operatorGroupQuery);
        HsAssert.notNull(operatorGroupQuery, UMRespCode.UM_PARAM_NULL_ERROR, "操作者用户组关系查询实体[operatorGroupQuery]为null");
        HsAssert.hasText(operatorGroupQuery.getOperatorId(), UMRespCode.UM_PARAM_EMPTY_ERROR, "操作员ID[operatorId]为空");
        operatorGroupQuery.setGroupId(null);//设置为null

        try {
            //查询符合条件的总记录数
            int totalRows = operatorGroupMapper.selectGroupCountForPage(operatorGroupQuery);
            //分页查询符合条件的记录
            List<Group> groups = operatorGroupMapper.selectGroupListForPage(gridPage, operatorGroupQuery);
            //返回结果
            return GridData.bulid(true, totalRows, gridPage.getCurPage(), groups);
        } catch (Exception e) {
            logger.error("=======从关联关系分页查询用户组列表异常,参数[operatorGroupQuery]:{}==========", operatorGroupQuery, e);
            return GridData.bulid(false, 0, gridPage.getCurPage(), null);
        }
    }

    /**
     * 处理操作员用户组关系列表
     *
     * @param operatorGroup 关系实体
     * @return boolean
     * @throws HsException
     */
    @Override
    public boolean dealOperatorGroupList(OperatorGroup operatorGroup) throws HsException {
        logger.info("=======处理操作员用户组关系列表,参数[operatorGroup]:{}==========", operatorGroup);
        HsAssert.notNull(operatorGroup, UMRespCode.UM_PARAM_NULL_ERROR, "操作员用户组关系实体[operatorGroup]为null");
        HsAssert.hasText(operatorGroup.getOperatorId(), UMRespCode.UM_PARAM_EMPTY_ERROR, "操作员ID[operatorId]为空");
        try {
            if (CollectionUtils.isNotEmpty(operatorGroup.getAddGroupIds())) {
                operatorGroupMapper.batchInsertForOperGroup(operatorGroup);
            }
            if (CollectionUtils.isNotEmpty(operatorGroup.getDelGroupIds())) {
                operatorGroupMapper.batchDeleteForOperator(operatorGroup);
            }
            return true;
        } catch (Exception e) {
            logger.error("=======[异常]处理操作员用户组关系列表,参数[operatorGroup]:{}==========", operatorGroup,e);
            throw new HsException(UMRespCode.UM_OPERATOR_GROUP_DB_ERROR, "处理操作员用户组关系列表异常，原因:" + e.getMessage());
        }
    }
}
