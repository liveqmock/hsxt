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
 * 角色菜单关系
 *
 * @Package : com.gy.hsxt.gpf.um.bean
 * @ClassName : RoleMenu
 * @Description : 角色菜单关系
 * @Author : chenm
 * @Date : 2016/1/26 20:00
 * @Version V3.0.0.0
 */
public class RoleMenu implements Serializable {

    private static final long serialVersionUID = -4565951944082186063L;
    /**
     * 角色ID
     */
    private String roleId;

    /**
     * 菜单编号
     */
    private String menuNo;

    /**
     * 待添加菜单
     */
    private List<String> addMenuNos;

    /**
     * 待删除菜单
     */
    private List<String> delMenuNos;


    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getMenuNo() {
        return menuNo;
    }

    public void setMenuNo(String menuNo) {
        this.menuNo = menuNo;
    }

    public List<String> getAddMenuNos() {
        return addMenuNos;
    }

    public void setAddMenuNos(List<String> addMenuNos) {
        this.addMenuNos = addMenuNos;
    }

    public List<String> getDelMenuNos() {
        return delMenuNos;
    }

    public void setDelMenuNos(List<String> delMenuNos) {
        this.delMenuNos = delMenuNos;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
