/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.gpf.um.enums;

import com.gy.hsxt.common.constant.IRespCode;

/**
 * UM错误码
 *
 * @Package : com.gy.hsxt.gpf.um.enums
 * @ClassName : UMRespCode
 * @Description : UM错误码
 * 范围: 47000~47999
 * @Author : chenm
 * @Date : 2016/1/27 10:51
 * @Version V3.0.0.0
 */
public enum UMRespCode implements IRespCode {

    // UM公共错误代码 范围 47000~47100
    /**
     * 参数为null
     */
    UM_PARAM_NULL_ERROR(47000, "参数为null"),
    /**
     * 参数为空字符串
     */
    UM_PARAM_EMPTY_ERROR(47001, "参数为空字符串"),
    /**
     * 参数类型错误
     */
    UM_PARAM_TYPE_ERROR(47002, "参数类型错误"),
    /**
     * 生成验证码错误
     */
    UM_GENERATE_VALID_CODE_ERROR(47003, "生成验证码错误"),

    // 操作员错误代码 范围 47100~47150
    /**
     * 操作员数据库层错误
     */
    UM_OPERATOR_DB_ERROR(47100, "操作员数据库层错误"),
    /**
     * 登录用户名已存在
     */
    UM_OPERATOR_LOGIN_USER_EXIST(47101, "登录用户名已存在"),
    // 登录错误代码 范围 47150~47200
    /**
     * 登录用户名不存在
     */
    UM_LOGIN_USER_NOT_EXIST(47150, "登录用户名不存在"),
    /**
     * 登录密码错误
     */
    UM_LOGIN_PWD_ERROR(47151, "登录密码错误"),
    /**
     * 登录验证码错误
     */
    UM_LOGIN_VALID_CODE_ERROR(47152, "登录验证码错误"),
    /**
     * 登录验证码过期
     */
    UM_LOGIN_VALID_CODE_EXPIRE(47153, "登录验证码过期"),
    /**
     * 登录令牌已失效
     */
    UM_LOGIN_TOKEN_EXPIRE(47154, "登录令牌已失效"),
    /**
     * 从请求中获取登录令牌失败
     */
    UM_LOGIN_TOKEN_EMPTY(47155, "从请求中获取登录令牌失败"),
    // 菜单错误代码 范围 47200~47250
    /**
     * 菜单数据库层错误
     */
    UM_MENU_DB_ERROR(47200, "菜单数据库层错误"),

    // 菜单错误代码 范围 47250~47300
    /**
     * 操作者没有分配角色
     */
    UM_OPERATOR_ROLE_NO_RELATION(47250, "操作者没有分配角色"),

    // 菜单错误代码 范围 47300~47350
    /**
     * 角色数据库层错误
     */
    UM_ROLE_DB_ERROR(47300, "角色数据库层错误"),
    // 用户组错误代码 范围 47350~47400
    /**
     * 用户组数据库层错误
     */
    UM_GROUP_DB_ERROR(47350, "用户组数据库层错误"),
    /**
     * 用户组名称已经存在
     */
    UM_GROUP_NAME_EXIST(47351, "用户组名称已经存在"),
    // 用户组错误代码 范围 47400~47450
    /**
     * 操作者用户组关系数据库层错误
     */
    UM_OPERATOR_GROUP_DB_ERROR(47400, "操作者用户组关系数据库层错误"),
    /**
     * 用户组名称已经存在
     */
//    UM_GROUP_NAME_EXIST(47351, "用户组名称已经存在"),

    // 角色菜单关系错误代码 范围 47450~47500
    /**
     * 角色菜单关系数据库层错误
     */
    UM_ROLE_MENU_DB_ERROR(47450, "角色菜单关系数据库层错误"),
    // 用户组错误代码 范围 47500~47550
    /**
     * 用户角色关系数据库层错误
     */
    UM_OPERATOR_ROLE_DB_ERROR(47500, "用户角色关系数据库层错误"),

    ;

    /**
     * 错误代码
     */
    private int code;

    /**
     * 错误描述
     */
    private String desc;

    UMRespCode(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }


    /**
     * 获取错误代码
     *
     * @return int
     */
    @Override
    public int getCode() {
        return this.code;
    }

    /**
     * 获取错误码描述
     *
     * @return {@code String}
     */
    @Override
    public String getDesc() {
        return this.desc;
    }
}
