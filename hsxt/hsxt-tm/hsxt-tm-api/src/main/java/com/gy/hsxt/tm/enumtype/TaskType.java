/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.tm.enumtype;

/**
 * 任务类型枚举定义
 * 
 * @Package: com.gy.hsxt.bs.common.enumtype
 * @ClassName: TaskType
 * @Description: TODO
 * 
 * @author: kongsl
 * @date: 2015-10-29 下午9:16:18
 * @version V3.0.0
 */
public enum TaskType {
    /*
     * 开/关企业系统复核
     */
    TASK_TYPE100("100"),

    /*
     * 消息模版复核
     */
    TASK_TYPE101("101"),

    /*
     * 扣款撤销复核
     */
    TASK_TYPE102("102"),

    /*
     * 二级区域资源配额分配审批
     */
    TASK_TYPE104("104"),

    /*
     * 三级区域资源配额分配审批
     */
    TASK_TYPE106("106"),

    /*
     * 企业报备平台审批
     */
    TASK_TYPE114("114"),

    /*
     * 企业申报资料管理公司初审
     */
    TASK_TYPE119("119"),
    /*
     * 企业申报资料管理公司复核
     */
    TASK_TYPE120("120"),
    /*
     * 企业申报资料平台开启系统
     */
    TASK_TYPE121("121"),
    /*
     * 成员企业注销/报停平台审批
     */
    TASK_TYPE133("133"),
    /*
     * 成员企业注销/报停平台复核
     */
    TASK_TYPE134("134"),
    /*
     * 托管企业积分活动变更平台审批
     */
    TASK_TYPE136("136"),
    /*
     * 托管企业积分活动变更平台复核
     */
    TASK_TYPE137("137"),
    /*
     * 个人实名认证平台审批
     */
    TASK_TYPE142("142"),
    /*
     * 个人实名认证平台复核
     */
    TASK_TYPE143("143"),
    /*
     * 企业实名认证平台审批
     */
    TASK_TYPE145("145"),
    /*
     * 企业实名认证平台复核
     */
    TASK_TYPE146("146"),
    /*
     * 企业重要信息变更平台审批
     */
    TASK_TYPE151("151"),
    /*
     * 企业重要信息变更平台复核
     */
    TASK_TYPE152("152"),
    /*
     * 个人重要信息变更平台审批
     */
    TASK_TYPE154("154"),
    /*
     * 个人重要信息变更平台复核
     */
    TASK_TYPE155("155"),
    /*
     * 企业交易密码重置平台审批
     */
    TASK_TYPE161("161"),
    /*
     * 企业税率调整平台审批
     */
    TASK_TYPE171("171"),
    /*
     * 资源费方案调整审核
     */
    TASK_TYPE202("202"),
    /*
     * 年费价格方案调整复核
     */
    TASK_TYPE205("205"),
    /*
     * 合同模板启用复核
     */
    TASK_TYPE212("212"),
    /*
     * 售后服务审批任务
     */
    TASK_TYPE213("213"),
    /*
     * 工具产品上下架复核
     */
    TASK_TYPE222("222"),
    /*
     * 工具产品价格调整复核
     */
    TASK_TYPE224("224"),
    /*
     * 平台代购工具复核
     */
    TASK_TYPE332("332"),
    /*
     * 临账记录平台复核
     */
    TASK_TYPE334("334"),
    /*
     * 临账记录退款复核
     */
    TASK_TYPE336("336"),
    /*
     * 临账支付关联复核
     */
    TASK_TYPE338("338"),

    /*
     * 积分福利初审
     */
    TASK_TYPE116("116"),

    /*
     * 积分福利复核
     */
    TASK_TYPE117("117"),

    /*
     * 积分福利发放
     */
    TASK_TYPE118("118"),

    //
    ;
    private String code;

    TaskType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
