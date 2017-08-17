/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.gpf.um.service.impl;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.HsAssert;
import com.gy.hsxt.gpf.um.bean.OperatorRole;
import com.gy.hsxt.gpf.um.dao.IOperatorRoleMapper;
import com.gy.hsxt.gpf.um.enums.UMRespCode;
import com.gy.hsxt.gpf.um.service.IOperatorRoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 操作员角色关系业务实现
 *
 * @Package : com.gy.hsxt.gpf.um.service.impl
 * @ClassName : OperatorRoleService
 * @Description : 操作员角色关系业务实现
 * @Author : chenm
 * @Date : 2016/2/16 17:51
 * @Version V3.0.0.0
 */
@Service
public class OperatorRoleService implements IOperatorRoleService {

    /**
     * 日志
     */
    private Logger logger = LoggerFactory.getLogger(OperatorRoleService.class);

    /**
     * 操作者角色关系数据库层接口
     */
    @Resource
    private IOperatorRoleMapper operatorRoleMapper;

    /**
     * 处理操作员角色关系列表
     *
     * @param operatorRole 关系实体
     * @return boolean
     * @throws HsException
     */
    @Override
    public Boolean dealOperRoleList(OperatorRole operatorRole) throws HsException {
        logger.info("=====处理操作员角色关系列表，参数[operatorRole]:{}=====", operatorRole);
        HsAssert.notNull(operatorRole, UMRespCode.UM_PARAM_NULL_ERROR, "操作员角色关系[operatorRole]为null");
        HsAssert.hasText(operatorRole.getOperatorId(), UMRespCode.UM_PARAM_EMPTY_ERROR, "操作员ID[operatorId]为空");

        try {
            if (CollectionUtils.isNotEmpty(operatorRole.getAddRoleIds())) {
                operatorRoleMapper.batchInsertForOperator(operatorRole);
            }

            if (CollectionUtils.isNotEmpty(operatorRole.getDelRoleIds())) {
                operatorRoleMapper.batchDeleteForOperator(operatorRole);
            }
            return true;
        } catch (Exception e) {
            logger.error("=====[异常]处理操作员角色关系列表，参数[operatorRole]:{}=====", operatorRole, e);
            throw new HsException(UMRespCode.UM_OPERATOR_ROLE_DB_ERROR, "处理操作员角色关系列表异常，原因:["+e.getMessage()+"]");
        }
    }

}
