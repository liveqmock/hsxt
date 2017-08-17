/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.gpf.um.utils;

import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.HsAssert;
import com.gy.hsxt.gpf.um.bean.Menu;
import com.gy.hsxt.gpf.um.bean.MenuTree;
import com.gy.hsxt.gpf.um.bean.RoleMenu;
import com.gy.hsxt.gpf.um.enums.MenuType;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.*;

/**
 * 菜单工具类
 *
 * @Package : com.gy.hsxt.gpf.um.utils
 * @ClassName : MenuUtils
 * @Description : 菜单工具类
 * @Author : chenm
 * @Date : 2016/1/26 20:13
 * @Version V3.0.0.0
 */
public abstract class MenuUtils {


    /**
     * 菜单每层起始编号
     */
    public static final String MENU_NO_START = "000";

    /**
     * 菜单编号的形成
     * 规则：
     *
     * @param menuNo   相邻参考编号
     * @param isParent 是否父节点
     * @return no
     * @throws HsException
     */
    public static String getMenuNo(String menuNo, boolean isParent) throws HsException {
        menuNo = StringUtils.trimToEmpty(menuNo);//去空格
        HsAssert.isTrue(menuNo.length() % 3 == 0, RespCode.PARAM_ERROR, "参考的菜单编号[" + menuNo + "]不符合规则");
        String no;
        if (isParent) {
            no = menuNo + MENU_NO_START;
        } else {
            int num = NumberUtils.toInt(StringUtils.right(menuNo, 3)) + 1;
            HsAssert.isTrue(num < 1000, RespCode.PARAM_ERROR, "参考的菜单编号[" + menuNo + "]的最后一层已达到最大值999");
            if (num >= 100) {
                no = StringUtils.left(menuNo, menuNo.length() - 3) + num;
            } else {
                String numStr = num >= 10 ? "0" + num : "00" + num;
                no = StringUtils.left(menuNo, menuNo.length() - 3) + numStr;
            }
        }
        return no;
    }

    /**
     * 菜单排序
     *
     * @param menuMap 菜单
     * @return {@code List<Menu>}
     */
    public static List<Menu> sortMenus(Map<String, Menu> menuMap) {
        List<Menu> topList = new ArrayList<>();
        for (Map.Entry<String, Menu> entry : menuMap.entrySet()) {
            int keyLen = StringUtils.length(entry.getKey());
            if (keyLen == 3) { //长度为三的全部是一级菜单
                topList.add(entry.getValue());
            } else {
                Menu menu = menuMap.get(StringUtils.left(entry.getKey(), keyLen - 3));
                List<Menu> childList = menu.getChildList();
                if (childList == null) {
                    childList = new ArrayList<>();
                    menu.setChildList(childList);
                }
                childList.add(entry.getValue());
            }
        }
       Collections.sort(topList);
        return topList;
    }

    /**
     * 菜单排序
     *
     * @param allMenu 菜单
     * @return {@code Map<String, Menu>}
     */
    public static Map<String, Menu> topMenuMap(List<Menu> allMenu) {
        Map<String, Menu> menuMap = new HashMap<>();
        for (Menu menu : allMenu) {
            int keyLen = StringUtils.length(menu.getMenuNo());
            if (keyLen == 3) { //长度为三的全部是一级菜单
                Menu m = menuMap.get(menu.getMenuNo());
                if (m == null) {
                    menuMap.put(menu.getMenuNo(), menu);
                }else {
                    menu.setChildList(m.getChildList());
                    menuMap.put(menu.getMenuNo(), menu);
                }
            } else {
                String parentNo = StringUtils.left(menu.getMenuNo(), keyLen - 3);
                Menu parent = menuMap.get(parentNo);
                if (parent == null) {
                    parent = new Menu();
                    menuMap.put(parentNo, parent);
                }
                List<Menu> childList = parent.getChildList();
                if (childList == null) {
                    childList = new ArrayList<>();
                    parent.setChildList(childList);
                }
                childList.add(menu);
            }
        }
        return menuMap;
    }

    /**
     * 构建菜单树
     *
     * @param allMenu     所有菜单
     * @param roleMenuMap 角色菜单关系
     * @return list
     */
    public static List<MenuTree> buildTree(List<Menu> allMenu, Map<String, RoleMenu> roleMenuMap) {
        //菜单树列表
        List<MenuTree> menuTrees = new ArrayList<>();

        for (Menu menu : allMenu) {
            //构建菜单树
            MenuTree tree = MenuTree.bulid(menu);
            //如果是子叶
            if (menu.getMenuType() == MenuType.LEAF.ordinal()) {
                RoleMenu roleMenu = roleMenuMap.get(menu.getMenuNo());
                tree.setChecked(roleMenu != null);
            }else {//菜单项
                int all = 0;
                int set = 0;
                for (Menu m : allMenu) {
                    //判断是否为下一级
                    if (m.getMenuNo().startsWith(menu.getMenuNo())&&m.getMenuLevel()==(menu.getMenuLevel()+1)) {
                        all++;
                        RoleMenu rm = roleMenuMap.get(m.getMenuNo());
                        if (rm != null) {
                            set++;
                        }
                    }
                }
                tree.setHalfCheck(all>set&&set>0);//设置半选
                tree.setChecked(all==0||set>0);//设置全选
            }
            menuTrees.add(tree);
        }
        return menuTrees;
    }

}
