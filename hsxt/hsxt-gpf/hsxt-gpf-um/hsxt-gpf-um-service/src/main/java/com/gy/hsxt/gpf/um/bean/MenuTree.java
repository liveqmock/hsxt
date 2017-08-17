/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.gpf.um.bean;

import com.alibaba.fastjson.JSON;
import com.gy.hsxt.gpf.um.enums.MenuType;
import org.apache.commons.lang3.StringUtils;

/**
 * 菜单树
 *
 * @Package : com.gy.hsxt.gpf.um.bean
 * @ClassName : MenuTree
 * @Description : 菜单树
 * @Author : chenm
 * @Date : 2016/2/5 17:47
 * @Version V3.0.0.0
 */
public class MenuTree {

    /**
     * 节点编号(菜单编号)
     */
    private String menuNo;

    /**
     * 父节点编号(父菜单编号)
     */
    private String parentNo;


    /**
     * 菜单名称
     */
    private String name;


    /**
     * 节点是否打开 true时打开，默认为false
     */
    private boolean open = true;

    /**
     * checkbox是否勾选  默认不勾选(false)
     */
    private boolean checked = false;

    /**
     * <p>半选</p>
     */
    private boolean halfCheck = false;

    /**
     * 是否隐藏checkbox 默认不隐藏=false
     */
    private boolean nocheck = false;

    /**
     * 是否禁用checkbox 默认不禁用=false
     */
    private boolean chkDisabled = false;

    public String getMenuNo() {
        return menuNo;
    }

    public void setMenuNo(String menuNo) {
        this.menuNo = menuNo;
    }

    public String getParentNo() {
        return parentNo;
    }

    public void setParentNo(String parentNo) {
        this.parentNo = parentNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public boolean isHalfCheck() {
        return halfCheck;
    }

    public void setHalfCheck(boolean halfCheck) {
        this.halfCheck = halfCheck;
    }

    public boolean isNocheck() {
        return nocheck;
    }

    public void setNocheck(boolean nocheck) {
        this.nocheck = nocheck;
    }

    public boolean isChkDisabled() {
        return chkDisabled;
    }

    public void setChkDisabled(boolean chkDisabled) {
        this.chkDisabled = chkDisabled;
    }

    /**
     * 构建菜单树节点
     *
     * @param menu 菜单
     * @return 节点
     */
    public static MenuTree bulid(Menu menu) {
        String menuNo = menu.getMenuNo();
        MenuTree menuTree = new MenuTree();
        menuTree.setName(menu.getMenuName());
        menuTree.setMenuNo(menuNo);
        menuTree.setParentNo(StringUtils.left(menuNo, menuNo.length() - 3));
        //菜单项打开
        menuTree.setOpen(menu.getMenuType() == MenuType.BRANCH.ordinal());
        return menuTree;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
