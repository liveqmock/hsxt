/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.gpf.um.bean;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;
import java.util.List;

/**
 * 菜单
 *
 * @Package : com.gy.hsxt.gpf.um.bean
 * @ClassName : Menu
 * @Description : 菜单
 * @Author : chenm
 * @Date : 2016/1/26 18:22
 * @Version V3.0.0.0
 */
public class Menu implements Serializable, Comparable<Menu> {

    private static final long serialVersionUID = 3848930258154332994L;
    /**
     * 菜单编号
     */
    private String menuNo;

    /**
     * 菜单模块代码
     */
    private String menuCode;

    /**
     * 菜单名称
     */
    private String menuName;

    /**
     * 菜单类型 0-菜单组 1-菜单项
     */
    private Integer menuType;

    /**
     * 菜单级别
     */
    private Integer menuLevel;

    /**
     * 菜单描述
     */
    private String description;

    /**
     * 上级节点编码
     */
    private String parentNo;

    /**
     * 下级菜单
     */
    private List<Menu> childList;

    public String getMenuNo() {
        return menuNo;
    }

    public void setMenuNo(String menuNo) {
        this.menuNo = menuNo;
    }

    public String getMenuCode() {
        return menuCode;
    }

    public void setMenuCode(String menuCode) {
        this.menuCode = menuCode;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public Integer getMenuType() {
        return menuType;
    }

    public void setMenuType(Integer menuType) {
        this.menuType = menuType;
    }

    public Integer getMenuLevel() {
        return menuLevel;
    }

    public void setMenuLevel(Integer menuLevel) {
        this.menuLevel = menuLevel;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getParentNo() {
        return parentNo;
    }

    public void setParentNo(String parentNo) {
        this.parentNo = parentNo;
    }

    public List<Menu> getChildList() {
        return childList;
    }

    public void setChildList(List<Menu> childList) {
        this.childList = childList;
    }


    @Override
    public int compareTo(Menu menu) {
        return this.getMenuNo().compareTo(menu.getMenuNo());
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
