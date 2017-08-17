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
import com.gy.hsxt.gpf.um.dao.IMenuMapper;
import com.gy.hsxt.gpf.um.dao.IOperatorRoleMapper;
import com.gy.hsxt.gpf.um.dao.IRoleMenuMapper;
import com.gy.hsxt.gpf.um.enums.MenuType;
import com.gy.hsxt.gpf.um.enums.UMRespCode;
import com.gy.hsxt.gpf.um.manager.SessionTokenManager;
import com.gy.hsxt.gpf.um.service.IMenuService;
import com.gy.hsxt.gpf.um.utils.MenuUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 菜单业务层实现
 *
 * @Package : com.gy.hsxt.gpf.um.service.impl
 * @ClassName : MenuService
 * @Description : 菜单业务层实现
 * @Author : chenm
 * @Date : 2016/1/27 15:58
 * @Version V3.0.0.0
 */
@Service
public class MenuService implements IMenuService {

    /**
     * 日志
     */
    private Logger logger = LoggerFactory.getLogger(MenuService.class);

    /**
     * 菜单数据库层接口
     */
    @Resource
    private IMenuMapper menuMapper;

    /**
     * 操作者角色关系数据库层接口
     */
    @Resource
    private IOperatorRoleMapper operatorRoleMapper;

    /**
     * 角色菜单关系数据库层接口
     */
    @Resource
    private IRoleMenuMapper roleMenuMapper;

    /**
     * 会话令牌与登录信息管理中心
     */
    @Resource
    private SessionTokenManager sessionTokenManager;

    /**
     * 保存单个bean
     *
     * @param bean 实体
     * @return int 影响条数
     * @throws HsException
     */
    @Override
    @Transactional
    public int saveBean(Menu bean) throws HsException {
        logger.info("=====添加新菜单，参数[menu]：{} ======", bean);
        HsAssert.notNull(bean, UMRespCode.UM_PARAM_NULL_ERROR, "添加新菜单的参数[menu]为null");
        HsAssert.hasText(bean.getMenuName(), UMRespCode.UM_PARAM_EMPTY_ERROR, "菜单名称[menuName]为空");
        HsAssert.isTrue(MenuType.checkType(bean.getMenuType()), UMRespCode.UM_PARAM_TYPE_ERROR, "菜单类型[menuType]错误");
        bean.setParentNo(StringUtils.trimToEmpty(bean.getParentNo()));//去空格
        try {
            //获取相邻菜单编号
            String adjacentNo = menuMapper.selectAdjacentNo(bean.getParentNo());
            boolean isParent = false;//是否为父节点
            //如果相邻编号为空，则传父节点
            if (StringUtils.isBlank(adjacentNo)) {
                adjacentNo = bean.getParentNo();
                isParent = true;
            }
            String menuNo = MenuUtils.getMenuNo(adjacentNo, isParent);
            bean.setMenuNo(menuNo);
            bean.setMenuLevel(menuNo.length() / 3);
            return menuMapper.insertBean(bean);
        } catch (Exception e) {
            logger.error("=====添加新菜单失败，参数[menu]：{} ======", bean, e);
            throw new HsException(UMRespCode.UM_MENU_DB_ERROR, "保存新菜单失败，原因：" + e.getMessage());
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
        return 0;
    }

    /**
     * 修改bean
     *
     * @param bean 实体
     * @return int 影响条数
     * @throws HsException
     */
    @Override
    public int modifyBean(Menu bean) throws HsException {
        return 0;
    }

    /**
     * 根据主键查询bean
     *
     * @param id 主键
     * @return bean
     * @throws HsException
     */
    @Override
    public Menu queryBeanById(String id) throws HsException {
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
    public Menu queryBeanByQuery(Query query) throws HsException {
        logger.info("========根据唯一查询条件查询单个菜单，参数[query]:{}========", query);
        return null;
    }

    /**
     * 根据查询实体查询bean列表
     *
     * @param query 查询实体
     * @return list<T>
     * @throws HsException
     */
    @Override
    public List<Menu> queryBeanListByQuery(Query query) throws HsException {
        logger.info("========根据查询实体查询菜单列表，参数[query]:{}========", query);
        try {
            return menuMapper.selectBeanListByQuery(query);
        } catch (Exception e) {
            logger.error("========根据查询实体查询菜单列表失败，参数[query]:{}========", query, e);
            throw new HsException(UMRespCode.UM_MENU_DB_ERROR, "根据查询实体查询菜单列表失败，原因：" + e.getMessage());
        }
    }

    /**
     * 获取菜单列表
     *
     * @param token 登录令牌
     * @return {@code List}
     * @throws HsException
     */
    @Override
    public List<Menu> queryMenuListByToken(String token) throws HsException {
        try {
            logger.info("======获取菜单列表,参数[token]:{}======", token);
            //获取登录信息
            LoginInfo info = sessionTokenManager.getLoginInfo(token);
            List<OperatorRole> operatorRoles = operatorRoleMapper.selectBeanListByQuery(OperatorRoleQuery.bulid(info));

            HsAssert.isTrue(CollectionUtils.isNotEmpty(operatorRoles), UMRespCode.UM_OPERATOR_ROLE_NO_RELATION, "登录用户名[" + info.getLoginUser() + "]没有分配任何角色");

            Map<String, Menu> menuMap = new HashMap<>();
            //循环遍历获取菜单
            for (OperatorRole operatorRole : operatorRoles) {
                List<RoleMenu> roleMenus = roleMenuMapper.selectBeanListByQuery(RoleMenuQuery.bulid(operatorRole));
                if (CollectionUtils.isNotEmpty(roleMenus)) {
                    for (RoleMenu roleMenu : roleMenus) {
                        if (!menuMap.containsKey(roleMenu.getMenuNo())) {//过滤掉重复的菜单
                            Menu menu = menuMapper.selectBeanById(roleMenu.getMenuNo());
                            menuMap.put(menu.getMenuNo(), menu);
                        }
                    }
                } else {
                    logger.info("=====登录用户[{}]的角色ID[{}]没有分配任何菜单权限=====", info.getLoginUser(), operatorRole.getRoleId());
                }
            }
            return MenuUtils.sortMenus(menuMap);
        } catch (Exception e) {
            logger.error("========获取菜单列表失败，参数[token]:{}========", token, e);
            throw new HsException(UMRespCode.UM_MENU_DB_ERROR, "获取菜单列表失败，原因：" + e.getMessage());
        }
    }

    /**
     * 通过角色ID查询菜单树
     *
     * @param roleId 角色ID
     * @return list
     * @throws HsException
     */
    @Override
    public List<MenuTree> queryMenuTreeByRoleId(String roleId) throws HsException {
        try {
            logger.info("======通过角色ID查询菜单树,参数[roleId]:{}======", roleId);

            Map<String, RoleMenu> roleMenuMap = new HashMap<>();
            //循环遍历获取菜单
            List<RoleMenu> roleMenus = roleMenuMapper.selectBeanListByQuery(RoleMenuQuery.bulid(roleId));
            if (CollectionUtils.isNotEmpty(roleMenus)) {
                for (RoleMenu roleMenu : roleMenus) {
                    roleMenuMap.put(roleMenu.getMenuNo(), roleMenu);
                }
            }
            //查询所有菜单
            List<Menu> allMenu = menuMapper.selectBeanListByQuery(null);
            //构建菜单树
            return MenuUtils.buildTree(allMenu, roleMenuMap);
        } catch (Exception e) {
            logger.error("========通过角色ID查询菜单树失败，参数[roleId]:{}========", roleId, e);
            throw new HsException(UMRespCode.UM_MENU_DB_ERROR, "通过角色ID查询菜单树失败，原因：" + e.getMessage());
        }
    }
}
