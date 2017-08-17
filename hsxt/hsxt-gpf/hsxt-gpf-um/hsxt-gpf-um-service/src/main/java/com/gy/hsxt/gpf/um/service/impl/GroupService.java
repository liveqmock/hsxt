/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.gpf.um.service.impl;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.DateUtil;
import com.gy.hsxt.common.utils.HsAssert;
import com.gy.hsxt.gpf.um.bean.*;
import com.gy.hsxt.gpf.um.dao.IGroupMapper;
import com.gy.hsxt.gpf.um.dao.IOperatorGroupMapper;
import com.gy.hsxt.gpf.um.enums.UMRespCode;
import com.gy.hsxt.gpf.um.service.IGroupService;
import com.gy.hsxt.gpf.um.utils.UmUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 用户组业务层实现
 *
 * @Package : com.gy.hsxt.gpf.um.service.impl
 * @ClassName : GroupService
 * @Description : 用户组业务层实现
 * @Author : chenm
 * @Date : 2016/1/27 15:56
 * @Version V3.0.0.0
 */
@Service
public class GroupService implements IGroupService {

    /**
     * 日志
     */
    private Logger logger = LoggerFactory.getLogger(GroupService.class);

    /**
     * 用户组数据库接口
     */
    @Resource
    private IGroupMapper groupMapper;

    /**
     * 操作者用户组关系数据库层接口
     */
    @Resource
    private IOperatorGroupMapper operatorGroupMapper;

    /**
     * 保存单个bean
     *
     * @param bean 实体
     * @return int 影响条数
     * @throws HsException
     */
    @Override
    public int saveBean(Group bean) throws HsException {
        logger.info("=====保存新用户组[group]:{}=======", bean);
        HsAssert.notNull(bean, UMRespCode.UM_PARAM_NULL_ERROR, "用户组[group]为null");
        HsAssert.hasText(bean.getGroupName(), UMRespCode.UM_PARAM_EMPTY_ERROR, "用户组名称[groupName]为空");
        HsAssert.hasText(bean.getCreatedBy(), UMRespCode.UM_PARAM_EMPTY_ERROR, "创建者[createdBy]为空");
        bean.setGroupName(StringUtils.trimToEmpty(bean.getGroupName()));//去空格
        //校验重复性
        Group group = queryBeanByQuery(GroupQuery.bulid(bean));
        HsAssert.isNull(group,UMRespCode.UM_GROUP_NAME_EXIST,"已经存在相同名称["+bean.getGroupName()+"]的用户组");

        bean.setGroupId(UmUtils.generateKey());//主键
        bean.setCreatedDate(DateUtil.getCurrentDateTime());//创建时间

        try {
            return groupMapper.insertBean(bean);
        } catch (Exception e) {
            logger.error("=====[异常]保存新用户组[group]:{}=======", bean, e);
            throw new HsException(UMRespCode.UM_GROUP_DB_ERROR, "保存新用户组异常,原因:" + e.getMessage());
        }
    }

    /**
     * 根据主键移除bean
     *
     * @param id 主键
     * @return int 影响条数
     * @throws HsException
     */
    @Override
    public int removeBeanById(String id) throws HsException {
        logger.info("=====删除用户组[groupId]:{}=======", id);
        HsAssert.hasText(id, UMRespCode.UM_PARAM_EMPTY_ERROR, "用户组ID[groupId]为空");
        try {
            operatorGroupMapper.deleteBean(OperatorGroup.bulid(null, id));
            return groupMapper.deleteBeanById(id);
        } catch (Exception e) {
            logger.error("=====[异常]删除用户组[groupId]:{}=======", id, e);
            throw new HsException(UMRespCode.UM_GROUP_DB_ERROR, "删除用户组异常,原因:" + e.getMessage());
        }
    }

    /**
     * 修改bean
     *
     * @param bean 实体
     * @return int 影响条数
     * @throws HsException
     */
    @Override
    public int modifyBean(Group bean) throws HsException {
        logger.info("=====修改用户组[group]:{}=======", bean);
        HsAssert.notNull(bean, UMRespCode.UM_PARAM_NULL_ERROR, "用户组[group]为null");
        HsAssert.hasText(bean.getGroupName(), UMRespCode.UM_PARAM_EMPTY_ERROR, "用户组名称[groupName]为空");
        HsAssert.hasText(bean.getUpdatedBy(), UMRespCode.UM_PARAM_EMPTY_ERROR, "更新者[updatedBy]为空");
        bean.setGroupName(StringUtils.trimToEmpty(bean.getGroupName()));//去空格
        //校验重复性
        Group group = queryBeanByQuery(GroupQuery.bulid(bean));
        if (group != null && !group.getGroupId().equals(bean.getGroupId())) {
            HsAssert.isNull(group,UMRespCode.UM_GROUP_NAME_EXIST,"已经存在相同名称["+bean.getGroupName()+"]的用户组");
        }
        try {
            bean.setUpdatedDate(DateUtil.getCurrentDateTime());
            return groupMapper.updateBean(bean);
        } catch (Exception e) {
            logger.error("=====[异常]修改用户组[group]:{}=======", bean, e);
            throw new HsException(UMRespCode.UM_GROUP_DB_ERROR, "修改用户组异常,原因:" + e.getMessage());
        }
    }

    /**
     * 根据主键查询bean
     *
     * @param id 主键
     * @return bean
     * @throws HsException
     */
    @Override
    public Group queryBeanById(String id) throws HsException {
        return null;
    }

    /**
     * 根据唯一查询条件查询单个Bean
     *
     * @param query 查询实体
     * @return bean
     * @throws HsException
     */
    @Override
    public Group queryBeanByQuery(Query query) throws HsException {
        logger.info("====根据唯一条件查询用户组，参数[query]:{}=======", query);
        HsAssert.notNull(query, UMRespCode.UM_PARAM_NULL_ERROR, "查询条件[query]为null");
        HsAssert.isInstanceOf(GroupQuery.class, query, UMRespCode.UM_PARAM_TYPE_ERROR, "查询条件[query]不是GroupQuery类型");
        try {
            return groupMapper.selectBeanByQuery(query);
        } catch (Exception e) {
            logger.error("=======根据唯一条件查询用户组异常，参数[query]:{}=======", query, e);
            throw new HsException(UMRespCode.UM_GROUP_DB_ERROR, "根据唯一条件查询用户组异常,原因:" + e.getMessage());
        }
    }

    /**
     * 根据查询实体查询bean列表
     *
     * @param query 查询实体
     * @return list<T>
     * @throws HsException
     */
    @Override
    public List<Group> queryBeanListByQuery(Query query) throws HsException {
        logger.info("=====根据查询实体查询bean列表,参数[query]:{}=====", query);
        if (query != null) {
            HsAssert.isInstanceOf(GroupQuery.class,query,UMRespCode.UM_PARAM_TYPE_ERROR,"查询实体[query]不是GroupQuery类型");
        }
        try {
            return groupMapper.selectBeanListByQuery(query);
        } catch (Exception e) {
            logger.info("=====[异常]根据查询实体查询bean列表,参数[query]:{}=====", query, e);
            throw new HsException(UMRespCode.UM_GROUP_DB_ERROR, "根据查询实体查询bean列表异常,原因:" + e.getMessage());
        }
    }

    /**
     * 分页查询用户组信息列表
     *
     * @param gridPage   分页对象
     * @param groupQuery 查询对象
     * @return {@code GridData}
     */
    @Override
    public GridData<Group> queryBeanListForPage(GridPage gridPage, GroupQuery groupQuery) {
        logger.info("=======分页查询用户组信息列表,参数[groupQuery]:{}==========",groupQuery);
        try {
            //查询符合条件的总记录数
            int totalRows = groupMapper.selectCountForPage(groupQuery);
            //分页查询符合条件的记录
            List<Group> groups = groupMapper.selectBeanListForPage(gridPage, groupQuery);
            //用户名称
            if (CollectionUtils.isNotEmpty(groups)) {
                for (Group group : groups) {
                    List<String> operators = groupMapper.selectOperatorsByGroupId(group.getGroupId());
                    group.setOperators(operators);
                }
            }
            //返回结果
            return GridData.bulid(true,totalRows,gridPage.getCurPage(),groups);
        } catch (Exception e) {
            logger.error("=======分页查询用户组信息列表异常,参数[groupQuery]:{}==========", groupQuery, e);
            return GridData.bulid(false, 0, gridPage.getCurPage(), null);
        }
    }
}
