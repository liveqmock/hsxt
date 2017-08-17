/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.gpf.um.service;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.gpf.um.bean.RoleMenu;

/**
 * 角色菜单业务接口
 *
 * @Package : com.gy.hsxt.gpf.um.service
 * @ClassName : IRoleMenuService
 * @Description : 角色菜单业务接口
 * @Author : chenm
 * @Date : 2016/2/16 10:55
 * @Version V3.0.0.0
 */
public interface IRoleMenuService {


    /**
     * 处理角色菜单关系
     *
     * @param roleMenu 角色菜单关系
     * @return boolean
     * @throws HsException
     */
    boolean dealRoleMenuList(RoleMenu roleMenu) throws HsException;
}
