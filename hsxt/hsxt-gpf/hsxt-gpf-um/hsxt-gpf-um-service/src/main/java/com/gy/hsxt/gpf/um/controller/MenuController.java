/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.gpf.um.controller;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.gpf.um.bean.Menu;
import com.gy.hsxt.gpf.um.bean.MenuTree;
import com.gy.hsxt.gpf.um.bean.RespInfo;
import com.gy.hsxt.gpf.um.service.IMenuService;
import com.gy.hsxt.gpf.um.utils.TokenUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 菜单控制中心
 *
 * @Package : com.gy.hsxt.gpf.um.controller
 * @ClassName : MenuController
 * @Description : 菜单控制中心
 * @Author : chenm
 * @Date : 2016/2/1 17:17
 * @Version V3.0.0.0
 */
@Controller
@RequestMapping("/menu")
public class MenuController extends BaseController {

    /**
     * 菜单业务接口
     */
    @Resource
    private IMenuService menuService;

    /**
     * 获取菜单列表
     *
     * @return {@code List}
     * @throws HsException
     */
    @ResponseBody
    @RequestMapping("/queryMenuList")
    public RespInfo<List<Menu>> queryMenuList(HttpServletRequest request) throws HsException {
        //获取token
        String token = TokenUtils.takeToken(request);
        //查询菜单列表
        List<Menu> menuList = menuService.queryMenuListByToken(token);
        return RespInfo.bulid(menuList);
    }

    /**
     * 通过角色ID查询菜单树
     *
     * @param roleId 角色ID
     * @return list
     * @throws HsException
     */
    @ResponseBody
    @RequestMapping("/queryMenuTree")
    public RespInfo<List<MenuTree>> queryMenuTree(@RequestParam("roleId") String roleId) throws HsException {
        //查询菜单列表
        List<MenuTree> trees = menuService.queryMenuTreeByRoleId(roleId);

        return RespInfo.bulid(trees);
    }
}
