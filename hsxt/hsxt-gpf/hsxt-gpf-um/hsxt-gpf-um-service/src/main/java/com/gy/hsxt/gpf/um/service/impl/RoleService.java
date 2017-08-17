/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.gpf.um.service.impl;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.DateUtil;
import com.gy.hsxt.common.utils.HsAssert;
import com.gy.hsxt.gpf.um.bean.*;
import com.gy.hsxt.gpf.um.dao.IOperatorRoleMapper;
import com.gy.hsxt.gpf.um.dao.IRoleMapper;
import com.gy.hsxt.gpf.um.dao.IRoleMenuMapper;
import com.gy.hsxt.gpf.um.enums.RoleType;
import com.gy.hsxt.gpf.um.enums.UMRespCode;
import com.gy.hsxt.gpf.um.service.IRoleService;
import com.gy.hsxt.gpf.um.utils.UmUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 角色业务层实现
 * @Package : com.gy.hsxt.gpf.um.service.impl
 * @ClassName : RoleService
 * @Description : 角色业务层实现
 * @Author : chenm
 * @Date : 2016/1/27 16:00
 * @Version V3.0.0.0
 */
@Service
public class RoleService implements IRoleService {

    /**
     * 日志
     */
    private Logger logger = LoggerFactory.getLogger(RoleService.class);

    /**
     * 角色数据库层接口
     */
    @Resource
    private IRoleMapper roleMapper;

    /**
     * 角色菜单关系数据库层接口
     */
    @Resource
    private IRoleMenuMapper roleMenuMapper;

    /**
     * 操作者角色关系数据库层接口
     */
    @Resource
    private IOperatorRoleMapper operatorRoleMapper;

    /**
     * 保存单个bean
     *
     * @param bean 实体
     * @return int 影响条数
     * @throws HsException
     */
    @Override
    @Transactional
    public int saveBean(Role bean) throws HsException {
        logger.info("======保存角色：{}=======", bean);
        HsAssert.notNull(bean, UMRespCode.UM_PARAM_NULL_ERROR, "角色[role]为null");
        HsAssert.hasText(bean.getRoleName(), UMRespCode.UM_PARAM_EMPTY_ERROR, "角色名称[roleName]为空");
        HsAssert.hasText(bean.getCreatedBy(), UMRespCode.UM_PARAM_EMPTY_ERROR, "创建者[createdBy]为空");
        bean.setRoleName(StringUtils.trimToEmpty(bean.getRoleName()));//去空格
        //重复校验
        Role role = queryBeanByQuery(RoleQuery.bulid(bean));
        HsAssert.isNull(role, UMRespCode.UM_ROLE_DB_ERROR, "已有相同角色名称["+bean.getRoleName()+"]存在");
        bean.setRoleType(RoleType.NORMAL.ordinal());//只允许添加普通角色
        bean.setCreatedDate(DateUtil.getCurrentDateTime());//创建时间
        bean.setRoleId(UmUtils.generateKey());//设置主键

        try {
            return roleMapper.insertBean(bean);
        } catch (Exception e) {
            logger.error("=======保存角色异常，参数[role]:{}=======", bean, e);
            throw new HsException(UMRespCode.UM_ROLE_DB_ERROR, "保存角色异常,原因:" + e.getMessage());
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
    @Transactional
    public int removeBeanById(String id) throws HsException {
        logger.info("=====删除角色[roleId]:{}=======", id);
        HsAssert.hasText(id, UMRespCode.UM_PARAM_EMPTY_ERROR, "用户组ID[roleId]为空");
        try {
            roleMenuMapper.batchDeleteByRoleId(id);//角色菜单关系
            operatorRoleMapper.batchDeleteByRoleId(id);//删除用户关系
            return roleMapper.deleteBeanById(id);
        } catch (Exception e) {
            logger.error("=====[异常]删除角色[roleId]:{}=======", id, e);
            throw new HsException(UMRespCode.UM_ROLE_DB_ERROR, "删除角色异常,原因:" + e.getMessage());
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
    @Transactional
    public int modifyBean(Role bean) throws HsException {
        logger.info("======修改角色：{}=======", bean);
        HsAssert.notNull(bean, UMRespCode.UM_PARAM_NULL_ERROR, "角色[role]为null");
        HsAssert.hasText(bean.getRoleName(), UMRespCode.UM_PARAM_EMPTY_ERROR, "角色名称[roleName]为空");
        HsAssert.hasText(bean.getUpdatedBy(), UMRespCode.UM_PARAM_EMPTY_ERROR, "更新者[updatedBy]为空");
        bean.setRoleName(StringUtils.trimToEmpty(bean.getRoleName()));//去空格
        //重复校验
        Role role = queryBeanByQuery(RoleQuery.bulid(bean));
        if (role != null && !role.getRoleId().equals(bean.getRoleId())) {
            HsAssert.isNull(role, UMRespCode.UM_ROLE_DB_ERROR, "已有相同角色名称["+bean.getRoleName()+"]存在");
        }
        bean.setUpdatedDate(DateUtil.getCurrentDateTime());//修改时间
        try {
            return roleMapper.updateBean(bean);
        } catch (Exception e) {
            logger.error("=======[异常]修改角色，参数[role]:{}=======", bean, e);
            throw new HsException(UMRespCode.UM_ROLE_DB_ERROR, "修改角色异常,原因:" + e.getMessage());
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
    public Role queryBeanById(String id) throws HsException {
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
    public Role queryBeanByQuery(Query query) throws HsException {
        logger.info("======根据条件查询唯一角色，参数：{} ======", query);
        HsAssert.notNull(query, UMRespCode.UM_PARAM_NULL_ERROR, "查询条件[query]为null");
        HsAssert.isInstanceOf(RoleQuery.class, query, UMRespCode.UM_PARAM_TYPE_ERROR, "查询条件[query]不是RoleQuery类型");
        try {
            return roleMapper.selectBeanByQuery(query);
        } catch (Exception e) {
            logger.error("=======根据条件查询唯一角色异常，参数[query]:{}=======", query, e);
            throw new HsException(UMRespCode.UM_ROLE_DB_ERROR, "根据条件查询唯一角色异常,原因:" + e.getMessage());
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
    public List<Role> queryBeanListByQuery(Query query) throws HsException {
        return null;
    }

    /**
     * 分页查询角色列表
     *
     * @param gridPage  分页实体
     * @param roleQuery 查询实体
     * @return {@link GridData}
     * @throws HsException
     */
    @Override
    public GridData<Role> queryBeanListForPage(GridPage gridPage, RoleQuery roleQuery) throws HsException {
        logger.info("=======分页查询角色列表,参数[roleQuery]:{}==========",roleQuery);
        try {
            //查询符合条件的总记录数
            int totalRows = roleMapper.selectCountForPage(roleQuery);
            //分页查询符合条件的记录
            List<Role> roles = roleMapper.selectBeanListForPage(gridPage, roleQuery);
            //返回结果
            return GridData.bulid(true,totalRows,gridPage.getCurPage(),roles);
        } catch (Exception e) {
            logger.error("=======分页查询角色列表异常,参数[roleQuery]:{}==========", roleQuery, e);
            return GridData.bulid(false, 0, gridPage.getCurPage(), null);
        }
    }
}
