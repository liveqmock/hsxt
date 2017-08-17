/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.gpf.um.bean;

import com.alibaba.fastjson.JSON;
import com.gy.hsxt.common.utils.DateUtil;

/**
 * 登录信息
 *
 * @Package : com.gy.hsxt.gpf.um.bean
 * @ClassName : LoginInfo
 * @Description : 登录信息
 * @Author : chenm
 * @Date : 2016/2/1 18:35
 * @Version V3.0.0.0
 */
public class LoginInfo {

    /**
     * 操作员ID
     */
    private String operatorId;

    /**
     * 登录用户名
     */
    private String loginUser;

    /**
     * 姓名
     */
    private String name;

    /**
     * 登录时间
     */
    private String loginTime;

    /**
     * 登录令牌
     */
    private String token;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(String loginTime) {
        this.loginTime = loginTime;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    /**
     * 构建登录信息
     *
     * @param operator 操作者
     * @param token    令牌
     * @return {@code LoginInfo}
     */
    public static LoginInfo bulid(Operator operator, String token) {
        LoginInfo info = new LoginInfo();
        info.setLoginUser(operator.getLoginUser());//设置登录用户名
        info.setName(operator.getName());//用户姓名
        info.setOperatorId(operator.getOperatorId());//ID
        info.setLoginTime(DateUtil.getCurrentDateTime());//登录时间
        info.setToken(token);
        return info;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
