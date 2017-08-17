/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */

package com.gy.hsxt.tm;

import com.gy.hsxt.common.constant.IRespCode;

/**
 * @Description: TM错误码定义，异常代码范围：42000~42999
 * 
 * @Package: com.gy.hsxt.tm
 * @ClassName: TMErrorCode
 * @author: yangjianguo
 * @date: 2016-1-13 下午3:34:41
 * @version V1.0
 */
public enum TMErrorCode implements IRespCode {

    /** 参数为空 **/
    TM_PARAMS_NULL(42100, "参数为空"),
    /** 查询工单列表异常 **/
    TM_QUERY_TASK_LIST_ERROR(42101, "查询工单列表异常"),
    /** 保存任务异常 **/
    TM_SAVE_TASK_ERROR(42105, "保存任务异常"),
    /** 更新任务状态异常 **/
    TM_UPDATE_TASK_STATUS_ERROR(42107, "更新任务状态异常"),
    /** 分页参数为空 **/
    TM_PAGE_PARAM_NULL(42108, "分页参数为空"),
    /** 更新任务紧急状态异常 **/
    TM_UPDATE_TASK_PRIORITY_STAT_ERROR(42110, "更新任务紧急状态异常"),
    /** 保存值班组异常 **/
    TM_SAVE_GROUP_ERROR(42111, "保存值班组异常"),
    /** 更新值班组异常 **/
    TM_UPDATE_GROUP_ERROR(42112, "更新值班组异常"),
    /** 查询值班组异常 **/
    TM_QUERY_GROUP_ERROR(42113, "查询值班组异常"),
    /** 查询值班员列表异常 **/
    TM_QUERY_OPERATOR_LIST_ERROR(42114, "查询值班员列表异常"),
    /** 查询值班员信息异常 **/
    TM_QUERY_OPERATOR_INFO_ERROR(42115, "查询值班员信息异常"),
    /** 删除值班员异常 **/
    TM_DELETE_OPERATOR_ERROR(42116, "删除值班员异常"),
    /** 保存值班员异常 **/
    TM_SAVE_OPERATOR_ERROR(42117, "保存值班员异常"),
    /** 更改值班主任异常 **/
    TM_CHANGE_CHIEF_ERROR(42118, "更改值班主任异常"),
    /** 值班组名称已存在 **/
    TM_GROUP_NAME_ALREADY_EXISTS(42123, "值班组名称已存在"),
    /** 未设置值班主任 **/
    TM_UNSET_CHIEF(42124, "未设置值班主任"),
    /** 值班组名称不存在 **/
    TM_GROUP_NAME_NOT_EXISTS(42125, "值班组名称不存在"),
    /** 值班计划已存在 **/
    TM_WORK_PLAN_EXISTS(42126, "值班计划已存在"),
    /** 月份校验失败 **/
    TM_CHANGE_SHIFT_MONTH_NOT_SAME(42127, "月份校验失败"),
    /** 无值班计划或无排除 **/
    TM_CHANGE_SHIFT_NO_SCHEDULE_OR_SCHOPT(42128, "无值班计划或无排除"),
    /** 排班数有误 **/
    TM_SCHEDULEOPT_ERROR(42129, "排班数有误"),
    /** 派单异常 **/
    TM_SEND_ORDER_ERROR(42131, "派单异常"),
    /** 未查询到数据 **/
    TM_NOT_QUERY_DATA(42132, "未查询到数据"),

    /** 保存值班计划异常 **/
    TM_SAVE_SCHEDULE_ERROR(42133, "保存值班计划异常"),
    /** 保存值班员排班异常 **/
    TM_SAVE_SCHEDULE_OPT_ERROR(42134, "保存值班员排班异常"),
    /** 修改值班员值班状态异常 **/
    TM_UPDATE_WORK_TYPE_ERROR(42135, "修改值班员值班状态异常"),
    /** 暂停值班计划异常 **/
    TM_PAUSE_SCHEDULE_ERROR(42136, "暂停值班计划异常"),
    /** 执行值班计划异常 **/
    TM_EXECUTE_SCHEDULE_ERROR(42137, "执行值班计划异常"),
    /** 调班异常 **/
    TM_CHANGE_SHIFT_ERROR(42138, "调班异常"),
    /** 必须有两个排班才可调班 **/
    TM_SCHEDULEOPT_NUM_ERROR(42139, "必须有两个排班才可调班"),
    /** 换班异常 **/
    TM_CHANGE_OF_SHIFT_ERROR(42140, "换班异常"),
    /** 更新催办状态异常 **/
    TM_UPDATE_WARN_FLAG_ERROR(42141, "更新催办状态异常"),
    /** 查询转派工单列表异常 **/
    TM_QUERY_TURN_TASK_LIST_ERROR(42142, "查询转派工单列表异常"),
    /** 查询催办紧急工单列表异常 **/
    TM_QUERY_URGENCY_TASK_ERROR(42143, "查询催办紧急工单列表异常"),
    /** 保存批量工单任务异常 **/
    TM_BATCH_SAVE_TASK_ERROR(42144, "保存批量工单任务异常"),
    /** 添加值班员授权异常 **/
    TM_ADD_OPERATOR_BIZ_TYPE(42145, "添加值班员授权异常"),
    /** 删除值班员授权时异常 **/
    TM_DELETE_OPERATOR_AUTH_ERROR(42146, "删除值班员授权时异常"),
    /** 更新值班组开关状态异常 **/
    TM_UPDATE_GROUP_OPEN_CLOSE_ERROR(42147, "更新值班组开关状态异常"),
    /** 获取值班计划异常 **/
    TM_GET_SCHEDULE_OPT_ERROR(42148, "获取值班计划异常"),
    /** 业务类型已存在 **/
    TM_BIZ_TYPE_EXISTS(42149, "业务类型已存在"),
    /** 保存业务类型时异常 **/
    TM_SAVE_BIZ_TYPE_ERROR(42150, "保存业务类型时异常"),
    /** 保存授权记录异常 **/
    TM_SAVE_BIZ_TYPE_AUTH_ERROR(42151, "保存授权记录异常"),
    /** 修改业务类型名称异常 **/
    TM_UPDATE_BIZ_TYPE_NAME_ERROR(42152, "修改业务类型名称异常"),
    /** 删除业务类型异常 **/
    TM_DELETE_BIZ_TYPE_ERROR(42153, "删除业务类型异常"),
    /** 删除值班员授权异常 **/
    TM_DELETE_OPERATOR_AUTH(42154, "删除值班员授权异常"),
    /** 查询业务类型列表异常 **/
    TM_QUERY_BIZ_TYPE_LIST_ERROR(42155, "查询业务类型列表异常"),
    /** 获取值班员授权列表异常 **/
    TM_GET_OPT_AUTH_LIST_ERROR(42156, "获取值班员授权列表异常"),
    /** 查询企业业务类型列表异常 **/
    TM_GET_ENT_BIZ_TYPE_LIST_ERROR(42157, "查询企业业务类型列表异常"),
    /** 查询授权值班员时异常 **/
    TM_GET_AUTH_OPERATOR_ERROR(42158, "查询授权值班员时异常"),
    /** 查询当班的值班员时异常 **/
    TM_GET_ON_WORK_OPERATOR_ERROR(42159, "查询当班的值班员时异常"),
    /** 获取随机值班员异常 **/
    TM_GET_RANDOM_OPERATOR_ERROR(42160, "获取随机值班员异常"),
    /** 自动催办紧急工单异常 **/
    TM_AUTO_URGENCY_TASK_ERROR(42161, "自动催办紧急工单异常"),
    /** 没有操作员可办理此业务 **/
    TM_NOT_AUTH_OPT(42162, "没有操作员可办理此业务"),
    /** 调用用户中心接口异常 **/
    TM_INVOKE_UC_NOT_QUERY_DATA(42163, "调用用户中心接口异常"),
    /** 非值班主任无权限执行此功能 **/
    TM_NOT_CHIEF_UNAUTH_OPT(42164, "非值班主任无权限操作"),
    //
    ;
    /**
     * 错误代码
     */
    private int errorCode;

    /**
     * 错误描述，默认描述
     */
    private String erroDesc;

    TMErrorCode(int code, String desc) {
        this.errorCode = code;
        this.erroDesc = desc;
    }

    /**
     * @return
     * @see com.gy.hsxt.common.constant.IRespCode#getCode()
     */
    @Override
    public int getCode() {
        return errorCode;
    }

    /**
     * @return
     * @see com.gy.hsxt.common.constant.IRespCode#getDesc()
     */
    @Override
    public String getDesc() {
        return erroDesc;
    }

}
