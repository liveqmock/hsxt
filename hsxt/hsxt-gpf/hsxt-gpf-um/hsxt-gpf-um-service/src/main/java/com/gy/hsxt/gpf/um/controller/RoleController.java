/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.gpf.um.controller;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.gpf.um.bean.*;
import com.gy.hsxt.gpf.um.service.IRoleMenuService;
import com.gy.hsxt.gpf.um.service.IRoleService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * 角色控制中心
 *
 * @Package : com.gy.hsxt.gpf.um.controller
 * @ClassName : RoleController
 * @Description : 角色控制中心
 * @Author : chenm
 * @Date : 2016/2/2 10:56
 * @Version V3.0.0.0
 */
@Controller
@RequestMapping("/role")
public class RoleController extends BaseController {

    /**
     * 角色业务接口
     */
    @Resource
    private IRoleService roleService;

    /**
     * 角色菜单业务接口
     */
    @Resource
    private IRoleMenuService roleMenuService;

    /**
     * 添加角色
     *
     * @param role 角色
     * @return 操作结果
     */
    @ResponseBody
    @RequestMapping("/save")
    public RespInfo<Role> saveRole(Role role) throws HsException {
        //添加角色
        roleService.saveBean(role);
        //返回操作结果
        return RespInfo.bulid(role);
    }

    /**
     * 分页查询角色列表
     *
     * @param gridPage  分页实体
     * @param roleQuery 查询实体
     * @return {@link GridData}
     * @throws HsException
     */
    @ResponseBody
    @RequestMapping("/queryListForPage")
    public GridData<Role> queryRoleList(GridPage gridPage, RoleQuery roleQuery) throws HsException {
        //返回操作结果
        return roleService.queryBeanListForPage(gridPage, roleQuery);
    }

    /**
     * 修改角色
     *
     * @param role 角色
     * @return boolean
     * @throws HsException
     */
    @ResponseBody
    @RequestMapping("/edit")
    public RespInfo<Boolean> editRole(Role role) throws HsException {
        //修改角色
        int count = roleService.modifyBean(role);
        //返回操作结果
        return RespInfo.bulid(count == 1);
    }

    /**
     * 删除角色
     *
     * @param roleId 角色ID
     * @return boolean
     * @throws HsException
     */
    @ResponseBody
    @RequestMapping("/delete")
    public RespInfo<Boolean> delRole(String roleId) throws HsException {
        //删除角色
        int count = roleService.removeBeanById(roleId);
        //返回操作结果
        return RespInfo.bulid(count == 1);
    }

    /**
     * 处理角色菜单关系
     *
     * @param roleMenu 角色菜单关系
     * @return boolean
     * @throws HsException
     */
    @ResponseBody
    @RequestMapping("/dealRoleMenuList")
    public RespInfo<Boolean> dealRoleMenuList(RoleMenu roleMenu) throws HsException {
        //处理角色菜单关系
        boolean success = roleMenuService.dealRoleMenuList(roleMenu);
        //返回操作结果
        return RespInfo.bulid(success);
    }
}
