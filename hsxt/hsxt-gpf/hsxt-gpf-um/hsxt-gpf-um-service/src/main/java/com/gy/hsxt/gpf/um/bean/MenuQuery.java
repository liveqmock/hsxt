/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.gpf.um.bean;

/**
 * 菜单查询实体
 *
 * @Package : com.gy.hsxt.gpf.um.bean
 * @ClassName : MenuQuery
 * @Description : 菜单查询实体
 * @Author : chenm
 * @Date : 2016/2/1 16:29
 * @Version V3.0.0.0
 */
public class MenuQuery extends Query {

    private static final long serialVersionUID = -3311071614295823906L;
    /**
     * 菜单编号
     */
    private String menuNo;

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
     * 上级节点编码
     */
    private String parentNo;

    public String getMenuNo() {
        return menuNo;
    }

    public void setMenuNo(String menuNo) {
        this.menuNo = menuNo;
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

    public String getParentNo() {
        return parentNo;
    }

    public void setParentNo(String parentNo) {
        this.parentNo = parentNo;
    }
}
