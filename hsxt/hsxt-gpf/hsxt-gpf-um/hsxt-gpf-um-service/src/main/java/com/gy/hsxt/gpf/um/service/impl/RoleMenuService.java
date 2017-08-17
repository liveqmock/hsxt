/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.gpf.um.service.impl;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.HsAssert;
import com.gy.hsxt.gpf.um.bean.RoleMenu;
import com.gy.hsxt.gpf.um.dao.IRoleMenuMapper;
import com.gy.hsxt.gpf.um.enums.UMRespCode;
import com.gy.hsxt.gpf.um.service.IRoleMenuService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 角色菜单业务实现
 *
 * @Package : com.gy.hsxt.gpf.um.service.impl
 * @ClassName : RoleMenuService
 * @Description : 角色菜单业务实现
 * @Author : chenm
 * @Date : 2016/2/16 10:59
 * @Version V3.0.0.0
 */
@Service
public class RoleMenuService implements IRoleMenuService {

    /**
     * 日志
     */
    private Logger logger = LoggerFactory.getLogger(RoleMenuService.class);

    /**
     * 角色菜单关系数据库层接口
     */
    @Resource
    private IRoleMenuMapper roleMenuMapper;

    /**
     * 处理角色菜单关系
     *
     * @param roleMenu 角色菜单关系
     * @return boolean
     * @throws HsException
     */
    @Override
    public boolean dealRoleMenuList(RoleMenu roleMenu) throws HsException {
        logger.info("=====处理角色菜单关系,参数[roleMenu]:{}=====", roleMenu);
        HsAssert.notNull(roleMenu, UMRespCode.UM_PARAM_NULL_ERROR, "角色菜单关系[roleMenu]为null");
        HsAssert.hasText(roleMenu.getRoleId(), UMRespCode.UM_PARAM_EMPTY_ERROR, "角色ID[roleId]为空");
        try {
            //添加角色菜单关系列表
            if (CollectionUtils.isNotEmpty(roleMenu.getAddMenuNos())) {
                roleMenuMapper.batchInsertForRole(roleMenu);
            }
            //删除角色菜单关系列表
            if (CollectionUtils.isNotEmpty(roleMenu.getDelMenuNos())) {
                roleMenuMapper.batchDeleteForRole(roleMenu);
            }
            return true;
        } catch (Exception e) {
            logger.error("=====[异常]处理角色菜单关系,参数[roleMenu]:{}=====", roleMenu, e);

            throw new HsException(UMRespCode.UM_ROLE_MENU_DB_ERROR, "处理角色菜单关系异常,原因[" + e.getMessage() + "]");
        }
    }
}
