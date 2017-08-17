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
import com.gy.hsxt.gpf.um.dao.IOperatorGroupMapper;
import com.gy.hsxt.gpf.um.dao.IOperatorMapper;
import com.gy.hsxt.gpf.um.dao.IOperatorRoleMapper;
import com.gy.hsxt.gpf.um.enums.UMRespCode;
import com.gy.hsxt.gpf.um.service.IOperatorService;
import com.gy.hsxt.gpf.um.utils.UmUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 操作员业务实现
 *
 * @Package : com.gy.hsxt.gpf.um.service.impl
 * @ClassName : OperatorService
 * @Description : 操作员业务实现
 * @Author : chenm
 * @Date : 2016/1/27 9:59
 * @Version V3.0.0.0
 */
@Service
public class OperatorService implements IOperatorService {

    /**
     * 日志
     */
    private Logger logger = LoggerFactory.getLogger(OperatorService.class);

    /**
     * 操作员数据库层接口
     */
    @Resource
    private IOperatorMapper operatorMapper;

    /**
     * 操作者角色关系数据库层接口
     */
    @Resource
    private IOperatorRoleMapper operatorRoleMapper;

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
    @Transactional
    public int saveBean(Operator bean) throws HsException {
        logger.info("======保存操作者[operator]:{}========", bean);
        HsAssert.notNull(bean, UMRespCode.UM_PARAM_NULL_ERROR, "操作者[operator]为null");
        HsAssert.hasText(bean.getLoginUser(), UMRespCode.UM_PARAM_EMPTY_ERROR, "登录用户名[loginUser]为空");
        HsAssert.hasText(bean.getLoginPwd(), UMRespCode.UM_PARAM_EMPTY_ERROR, "登录密码[loginPwd]为空");
        HsAssert.hasText(bean.getCreatedBy(), UMRespCode.UM_PARAM_EMPTY_ERROR, "创建者[createdBy]为空");
        HsAssert.hasText(bean.getName(), UMRespCode.UM_PARAM_EMPTY_ERROR, "姓名[name]为空");
        bean.setLoginUser(StringUtils.trimToEmpty(bean.getLoginUser()));//去首尾空格

        //校验登录用户名是否重复
        Operator operator = queryBeanByQuery(OperatorQuery.bulid(bean));
        HsAssert.isNull(operator, UMRespCode.UM_OPERATOR_LOGIN_USER_EXIST, "登录用户名["+bean.getLoginUser()+"]的操作者已存在");
        try {
            //设置操作员ID
            bean.setOperatorId(UmUtils.generateKey());//形成主键
            bean.setLoginPwd(UmUtils.generatePwd(bean));//加密登录密码
            bean.setCreatedDate(DateUtil.getCurrentDateTime());//创建时间
            int count =  operatorMapper.insertBean(bean);
            // 保存用户组关系
            if (count == 1&&CollectionUtils.isNotEmpty(bean.getGroups())) {
                operatorGroupMapper.batchInsertForOperator(bean);
            }
            return count;
        } catch (Exception e) {
            logger.error("======[异常]保存操作者[operator]:{}========", bean, e);
            throw new HsException(UMRespCode.UM_OPERATOR_DB_ERROR, "保存操作者异常,原因[" + e.getMessage() + "]");
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
        logger.info("=====删除操作者的参数[operatorId]:{}======", id);
        HsAssert.hasText(id, UMRespCode.UM_PARAM_EMPTY_ERROR, "操作者ID[operatorId]为空");
        try {
            operatorGroupMapper.batchDeleteForOperator(OperatorGroup.bulid(id, ""));//删除用户组关系
            operatorRoleMapper.batchDeleteByOperatorId(id);//删除角色关系
            return operatorMapper.deleteBeanById(id);
        } catch (Exception e) {
            logger.error("=====[异常]删除操作者的参数[operatorId]:{}======", id);
            throw new HsException(UMRespCode.UM_OPERATOR_DB_ERROR, "删除操作者异常,原因[" + e.getMessage() + "]");
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
    public int modifyBean(Operator bean) throws HsException {
        return 0;
    }

    /**
     * 修改操作员
     *
     * @param operator      操作员
     * @param operatorGroup 关系实体
     * @return boolean
     * @throws HsException
     */
    @Override
    @Transactional
    public boolean editOperator(Operator operator, OperatorGroup operatorGroup) throws HsException {
        logger.info("=====修改操作者的参数[operator]:{}======", operator);
        HsAssert.notNull(operator, UMRespCode.UM_PARAM_NULL_ERROR, "操作者[operator]为null");
        HsAssert.hasText(operator.getOperatorId(), UMRespCode.UM_PARAM_EMPTY_ERROR, "操作者ID[operatorId]为空");
        HsAssert.hasText(operator.getLoginUser(), UMRespCode.UM_PARAM_EMPTY_ERROR, "登录用户名[loginUser]为空");
        HsAssert.hasText(operator.getName(), UMRespCode.UM_PARAM_EMPTY_ERROR, "姓名[name]为空");
        HsAssert.hasText(operator.getUpdatedBy(), UMRespCode.UM_PARAM_EMPTY_ERROR, "更新者[updatedBy]为空");
        logger.info("=======处理操作员用户组关系列表,参数[operatorGroup]:{}==========", operatorGroup);
        HsAssert.notNull(operatorGroup, UMRespCode.UM_PARAM_NULL_ERROR, "操作员用户组关系实体[operatorGroup]为null");
        //校验登录用户名是否重复
        Operator oper = queryBeanByQuery(OperatorQuery.bulid(operator));
        if (oper!=null &&!oper.getOperatorId().equals(operator.getOperatorId())) {
            HsAssert.isNull(operator, UMRespCode.UM_OPERATOR_LOGIN_USER_EXIST, "登录用户名["+operator.getLoginUser()+"]的操作者已存在");
        }
        try {
            operator.setUpdatedDate(DateUtil.getCurrentDateTime());//设置更新时间
            if (StringUtils.isNotEmpty(operator.getLoginPwd())) {
                operator.setLoginPwd(UmUtils.generatePwd(operator));
            }
            int count =  operatorMapper.updateBean(operator);
            if (count == 1) {
                operatorGroup.setOperatorId(operator.getOperatorId());
                if (CollectionUtils.isNotEmpty(operatorGroup.getAddGroupIds())) {
                    operatorGroupMapper.batchInsertForOperGroup(operatorGroup);
                }
                if (CollectionUtils.isNotEmpty(operatorGroup.getDelGroupIds())) {
                    operatorGroupMapper.batchDeleteForOperator(operatorGroup);
                }
            }
            return true;
        } catch (Exception e) {
            logger.error("======修改操作者异常[operator]:{}========", operator, e);
            throw new HsException(UMRespCode.UM_OPERATOR_DB_ERROR, "修改操作者异常,原因[" + e.getMessage() + "]");
        }
    }

    /**
     * 修改登录密码
     *
     * @param operator    操作者
     * @param oldPassword 旧密码
     * @return boolean
     * @throws HsException
     */
    @Override
    public boolean editLoginPassword(Operator operator, String oldPassword) throws HsException {
        logger.info("=====修改登录密码的参数[operator]:{}======", operator);
        HsAssert.notNull(operator, UMRespCode.UM_PARAM_NULL_ERROR, "操作者[operator]为null");
        HsAssert.hasText(operator.getOperatorId(), UMRespCode.UM_PARAM_EMPTY_ERROR, "旧密码[oldPassword]为空");
        HsAssert.hasText(operator.getOperatorId(), UMRespCode.UM_PARAM_EMPTY_ERROR, "操作者ID[operatorId]为空");
        HsAssert.hasText(operator.getLoginPwd(), UMRespCode.UM_PARAM_EMPTY_ERROR, "登录密码[loginPwd]为空");
        HsAssert.hasText(operator.getUpdatedBy(), UMRespCode.UM_PARAM_EMPTY_ERROR, "更新者[updatedBy]为空");

        Operator oper = queryBeanById(operator.getOperatorId());
        //数据库中的密码
        String passwordInDB = oper.getLoginPwd();
        //旧密码
        oper.setLoginPwd(oldPassword);
        String oldPwd = UmUtils.generatePwd(oper);

        HsAssert.isTrue(passwordInDB.equals(oldPwd), UMRespCode.UM_LOGIN_PWD_ERROR, "旧密码[oldPassword]错误");

        try {
            operator.setUpdatedDate(DateUtil.getCurrentDateTime());//设置更新时间
            operator.setLoginUser(oper.getLoginUser());//设置登录用户名
            operator.setLoginPwd(UmUtils.generatePwd(operator));//加密密码
            int count =  operatorMapper.updateLoginPassword(operator);
            return count ==1;
        } catch (Exception e) {
            logger.error("======修改登录密码异常[operator]:{}========", operator, e);
            throw new HsException(UMRespCode.UM_OPERATOR_DB_ERROR, "修改登录密码异常,原因[" + e.getMessage() + "]");
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
    public Operator queryBeanById(String id) throws HsException {
        logger.info("=====根据ID查询操作员，参数：{}=====", id);
        HsAssert.hasText(id, UMRespCode.UM_PARAM_EMPTY_ERROR, "操作员ID[operatorId]为空");
        try {
            return operatorMapper.selectBeanById(id);
        } catch (Exception e) {
            logger.error("=====[异常]根据ID查询操作员，参数：{}=====", id, e);
            throw new HsException(UMRespCode.UM_OPERATOR_DB_ERROR, "根据ID查询操作员异常,原因[" + e.getMessage() + "]");
        }
    }

    /**
     * 根据唯一查询条件查询单个Bean
     *
     * @param query 查询实体
     * @return bean
     * @throws HsException
     */
    @Override
    public Operator queryBeanByQuery(Query query) throws HsException {
        logger.info("=======根据唯一条件查询操作员，参数[query]:{}=======", query);
        HsAssert.notNull(query, UMRespCode.UM_PARAM_NULL_ERROR, "查询条件[query]为空");
        HsAssert.isInstanceOf(OperatorQuery.class, query, UMRespCode.UM_PARAM_TYPE_ERROR, "查询条件[query]不是OperatorQuery类型");
        try {
            return operatorMapper.selectBeanByQuery(query);
        } catch (Exception e) {
            logger.error("=======根据唯一条件查询操作员异常，参数[query]:{}=======", query, e);
            throw new HsException(UMRespCode.UM_OPERATOR_DB_ERROR, "根据唯一条件查询操作员异常,原因:" + e.getMessage());
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
    public List<Operator> queryBeanListByQuery(Query query) throws HsException {
        return null;
    }

    /**
     * 分页查询操作员
     *
     * @param gridPage      分页对象
     * @param operatorQuery 查询对象
     * @return {@link GridData}
     * @throws HsException
     */
    @Override
    public GridData<Operator> queryBeanListForPage(GridPage gridPage, OperatorQuery operatorQuery) throws HsException {
        logger.info("=======分页查询操作员,参数[operatorQuery]:{}==========",operatorQuery);
        try {
            //查询符合条件的总记录数
            int totalRows = operatorMapper.selectCountForPage(operatorQuery);
            //分页查询符合条件的记录
            List<Operator> operators = operatorMapper.selectBeanListForPage(gridPage, operatorQuery);
            if (CollectionUtils.isNotEmpty(operators)) {
                for (Operator operator : operators) {
                    List<String> roleNames = operatorRoleMapper.selectRoleNamesByQuery(OperatorRoleQuery.bulid(operator));
                    operator.setRoles(roleNames);
                    List<String> groupNames = operatorGroupMapper.selectGroupNamesByQuery(OperatorGroupQuery.bulid(operator));
                    operator.setGroups(groupNames);
                }
            }
            //返回结果
            return GridData.bulid(true,totalRows,gridPage.getCurPage(),operators);
        } catch (Exception e) {
            logger.error("=======分页查询操作员,参数[operatorQuery]:{}==========", operatorQuery, e);
            return GridData.bulid(false, 0, gridPage.getCurPage(), null);
        }
    }
}
