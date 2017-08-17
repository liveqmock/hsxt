/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.gpf.um.bean;

import java.util.List;

/**
 * 操作员
 *
 * @Package : com.gy.hsxt.gpf.um.bean
 * @ClassName : Operator
 * @Description : 操作员
 * @Author : chenm
 * @Date : 2016/1/26 19:27
 * @Version V3.0.0.0
 */
public class Operator extends Base {

    private static final long serialVersionUID = 8994451598766202960L;
    /**
     * 操作员ID
     */
    private String operatorId;

    /**
     * 登录用户名
     */
    private String loginUser;

    /**
     * 登录密码
     */
    private String loginPwd;

    /**
     * 姓名
     */
    private String name;

    /**
     * 职务
     */
    private String duty;

    /**
     * 所属角色名
     * 查询时，显示名称
     * 保存/修改时，显示ID
     */
    private List<String> roles;

    /**
     * 所属用户组
     * 查询时，显示名称
     * 保存/修改时，显示ID
     */
    private List<String> groups;

    /**
     * 验证码
     */
    private String validCode;

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    public String getLoginUser() {
        return loginUser;
    }

    public void setLoginUser(String loginUser) {
        this.loginUser = loginUser;
    }

    public String getLoginPwd() {
        return loginPwd;
    }

    public void setLoginPwd(String loginPwd) {
        this.loginPwd = loginPwd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDuty() {
        return duty;
    }

    public void setDuty(String duty) {
        this.duty = duty;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public List<String> getGroups() {
        return groups;
    }

    public void setGroups(List<String> groups) {
        this.groups = groups;
    }

    public String getValidCode() {
        return validCode;
    }

    public void setValidCode(String validCode) {
        this.validCode = validCode;
    }
}
