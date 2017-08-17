/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.gpf.um.service;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.gpf.um.bean.Menu;
import com.gy.hsxt.gpf.um.bean.MenuTree;

import java.util.List;

/**
 * 菜单业务接口
 *
 * @Package : com.gy.hsxt.gpf.um.service
 * @ClassName : IMenuService
 * @Description : 菜单业务接口
 * @Author : chenm
 * @Date : 2016/1/27 15:54
 * @Version V3.0.0.0
 */
public interface IMenuService extends IBaseService<Menu> {

    /**
     * 获取菜单列表
     *
     * @param token 登录令牌
     * @return {@code List}
     * @throws HsException
     */
    List<Menu> queryMenuListByToken(String token) throws HsException;

    /**
     * 通过角色ID查询菜单树
     *
     * @param roleId 角色ID
     * @return list
     * @throws HsException
     */
    List<MenuTree> queryMenuTreeByRoleId(String roleId) throws HsException;
}
