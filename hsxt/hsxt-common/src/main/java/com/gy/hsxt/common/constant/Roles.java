/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.common.constant;

/**
 * 角色定义
 * 
 * @Package: com.gy.hsxt.common.constant
 * @ClassName: Roles
 * @Description: 预设角色定义
 * 
 * @author: lvyan
 * @date: 2015-12-10 下午2:21:51
 * @version V1.0
 */
public enum Roles {
    /** 仓库管理员 **/
    STOREHOUSE_ADMIN("1000", "仓库管理员"),

    /** 成员企业管理员 **/
    MEMBER_ENT_ADMIN("201", "成员企业管理员"),

    /** 托管企业管理员 **/
    DEPOSIT_ENT_ADMIN("301", "托管企业管理员"),

    /** 服务公司管理员 **/
    SERVICE_ENT_ADMIN("401", "服务公司管理员"),

    /** 管理公司管理员 **/
    MANAGE_ENT_ADMIN("501", "管理公司管理员"),

    /** 地区平台管理员 **/
    PLAT_ENT_ADMIN("601", "地区平台管理员"),

    /** 总平台管理员 **/
    CENTER_PLAT_ENT_ADMIN("701", "总平台管理员");

    /**
     * 全局角色定义
     */
    private String id;

    private String desc;

    Roles(String id, String desc) {
        this.id = id;
        this.desc = desc;
    }

    public String getId() {
        return id;
    }

    public String getDesc() {
        return desc;
    }

}
