/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.tm.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.DateUtil;
import com.gy.hsxt.common.utils.HsAssert;
import com.gy.hsxt.tm.TMErrorCode;
import com.gy.hsxt.tm.api.ITMBizAuthService;
import com.gy.hsxt.tm.api.ITMOnDutyService;
import com.gy.hsxt.tm.bean.BizTypeAuth;
import com.gy.hsxt.tm.bean.CustomOperator;
import com.gy.hsxt.tm.bean.Group;
import com.gy.hsxt.tm.bean.Operator;
import com.gy.hsxt.tm.bean.Schedule;
import com.gy.hsxt.tm.bean.ScheduleOpt;
import com.gy.hsxt.tm.common.StringUtil;
import com.gy.hsxt.tm.disconf.TmConfig;
import com.gy.hsxt.tm.enumtype.GroupStatus;
import com.gy.hsxt.tm.enumtype.WorkTypeStatus;
import com.gy.hsxt.tm.interfaces.IOperatorService;
import com.gy.hsxt.tm.mapper.BizTypeAuthMapper;
import com.gy.hsxt.tm.mapper.BizTypeMapper;
import com.gy.hsxt.tm.mapper.GroupMapper;
import com.gy.hsxt.tm.mapper.OperatorMapper;
import com.gy.hsxt.tm.mapper.ScheduleMapper;
import com.gy.hsxt.tm.mapper.ScheduleOptMapper;

/**
 * 值班管理dubbo service实现类
 * 
 * @Package: com.gy.hsxt.tm.service
 * @ClassName: OnDutyService
 * @Description: TODO
 * 
 * @author: kongsl
 * @date: 2015-11-11 下午8:57:03
 * @version V3.0.0
 */
@Service
public class OnDutyService implements ITMOnDutyService {
    /** 业务服务私有配置参数 **/
    @Autowired
    private TmConfig tmConfig;

    /**
     * 注入业务授权mapper
     */
    @Autowired
    BizTypeAuthMapper authMapper;

    /**
     * 注入值班组mapper
     */
    @Autowired
    GroupMapper groupMapper;

    /**
     * 注入值班员mapper
     */
    @Autowired
    OperatorMapper operatorMapper;

    /**
     * 注入业务类型mapper
     */
    @Autowired
    BizTypeMapper bizTypeMapper;

    /**
     * 授权mapper
     */
    @Autowired
    BizTypeAuthMapper bizTypeAuthMapper;

    /**
     * 注入值班计划mapper
     */
    @Autowired
    ScheduleMapper scheduleMapper;

    /**
     * 注入排班计划mapper
     */
    @Autowired
    ScheduleOptMapper scheduleOptMapper;

    /**
     * 操作员内部service
     */
    @Autowired
    IOperatorService operatorService;

    /**
     * 业务权限管理接口
     */
    @Autowired
    ITMBizAuthService bizAuthService;

    /**
     * 保存值班组
     * 
     * @param group
     *            值班组信息
     * @param operators
     *            值班员列表
     * @throws HsException
     * @see com.gy.hsxt.tm.api.ITMOnDutyService#saveGroup(com.gy.hsxt.tm.bean.Group,
     *      java.util.List)
     */
    @Override
    @Transactional
    public void saveGroup(Group group, List<Operator> operators) throws HsException {
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                "保存值班组,params[" + group + ",operators:" + JSON.toJSONString(operators) + "]");

        // 值班组对象为空
        HsAssert.notNull(group, TMErrorCode.TM_PARAMS_NULL, "保存值班组：输入参数对象为空");

        // 校验值班组名已存在
        HsAssert.isTrue(!checkGroupName(group.getEntCustId(), group.getGroupName()),
                TMErrorCode.TM_GROUP_NAME_ALREADY_EXISTS, "保存值班组：值班组名称重复");
        // 值班员为空
        HsAssert.notNull(operators, TMErrorCode.TM_PARAMS_NULL, "保存值班组：值班员为空");
        // 校验是否设置值班主任
        HsAssert.isTrue(checkIsSetChief(operators), TMErrorCode.TM_UNSET_CHIEF, "保存值班组：未设置值班主任");

        try
        {
            // 设置值班组编号
            group.setGroupId(StringUtil.getTmGuid(tmConfig.getAppNode()));
            // 保存值班组
            groupMapper.insertGroup(group);
            // 保存值班员
            saveOperator(operators, group.getGroupId(), group.getGroupName(), "");
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), "method:"
                    + Thread.currentThread().getStackTrace()[1].getMethodName(), TMErrorCode.TM_SAVE_GROUP_ERROR
                    .getCode()
                    + ":保存值班组异常,params[" + group + ",operators:" + JSON.toJSONString(operators) + "]", e);
            throw new HsException(TMErrorCode.TM_SAVE_GROUP_ERROR.getCode(), "保存值班组异常,params[" + group + ",operators:"
                    + JSON.toJSONString(operators) + "]" + e);
        }
    }

    /**
     * 修改值班组
     * 
     * @param group
     *            值班组信息
     * @param operators
     *            值班员列表
     * @param scheduleId
     *            值班计划编号
     * @throws HsException
     * @see com.gy.hsxt.tm.api.ITMOnDutyService#modifyGroup(com.gy.hsxt.tm.bean.Group,
     *      java.util.List)
     */
    @Override
    @Transactional
    public void modifyGroup(Group group, List<Operator> operators, String scheduleId) throws HsException {
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                "修改值班组,params[" + group + ",operators:" + JSON.toJSONString(operators) + "]");
        // 值班组对象为空
        HsAssert.notNull(group, TMErrorCode.TM_PARAMS_NULL, "修改值班组：输入参数对象为空");

        // 值班组编号为空
        HsAssert.hasText(group.getGroupId(), TMErrorCode.TM_PARAMS_NULL, "修改值班组：值班组编号为空");
        // 值班组类型为空
        HsAssert.notNull(group.getGroupType(), TMErrorCode.TM_PARAMS_NULL, "修改值班组：值班组类型为空");
        // 值班员为空
        HsAssert.notNull(operators, TMErrorCode.TM_PARAMS_NULL, "修改值班组：值班员为空");

        int resultNum = 0;

        if (StringUtils.isNotBlank(group.getGroupName()))
        {
            HsAssert.isTrue(!checkGroupNameModify(group.getEntCustId(), group.getGroupId(), group.getGroupName()),
                    TMErrorCode.TM_GROUP_NAME_ALREADY_EXISTS, "修改值班组：值班组名称重复");
        }
        try
        {
            // 修改值班组类型
            groupMapper.updateGroup(group);
            // 每次编辑都将组值班员的值班组重置为空
            // operatorMapper.resetOperatorGroup(group.getGroupId());
            // 校验是否设置值班主任
            HsAssert.isTrue(checkIsSetChief(operators), TMErrorCode.TM_UNSET_CHIEF, "修改值班组：未设置值班主任");
            // 保存值班员
            saveOperator(operators, group.getGroupId(), group.getGroupName(), scheduleId);

            for (Operator operator : operators)
            {
                resultNum = operatorMapper.findOperatorById(operator.getOptCustId());
                // 删除授权
                if (resultNum <= 1)
                {
                    bizTypeAuthMapper.deleteOptAuth(operator.getGroupId(), operator.getOptCustId());
                }
            }
        }
        catch (HsException e)
        {
            SystemLog.error(this.getClass().getName(), "method:"
                    + Thread.currentThread().getStackTrace()[1].getMethodName(), e.getMessage(), e);
            throw e;
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), "method:"
                    + Thread.currentThread().getStackTrace()[1].getMethodName(), TMErrorCode.TM_UPDATE_GROUP_ERROR
                    .getCode()
                    + ":修改值班组异常,params[" + group + ",operators:" + JSON.toJSONString(operators) + "]", e);
            throw new HsException(TMErrorCode.TM_UPDATE_GROUP_ERROR.getCode(), "修改值班组异常,params[" + group
                    + ",operators:" + JSON.toJSONString(operators) + "]" + e);
        }
    }

    /**
     * 添加值班员授权
     * 
     * @param operatorAuths
     *            授权信息列表
     * @throws HsException
     * @see com.gy.hsxt.tm.api.ITMOnDutyService#addOperatorBizType(java.util.List)
     */
    @Override
    public void addOperatorBizType(List<BizTypeAuth> operatorAuths) throws HsException {
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                "添加值班员授权,params[" + JSON.toJSONString(operatorAuths) + "]");
        // 校验参数对象不为空
        HsAssert.notNull(operatorAuths, TMErrorCode.TM_PARAMS_NULL, "添加值班员授权：授权信息列表参数为空");
        try
        {
            BizTypeAuth auth = null;
            // 遍历授权列表
            for (BizTypeAuth bizTypeAuth : operatorAuths)
            {
                auth = authMapper.findOptCustAuth(bizTypeAuth.getBizType(), bizTypeAuth.getOptCustId());
                if (auth == null)
                {
                    // 新增授权
                    authMapper.insertBizTypeAuth(bizTypeAuth);
                }
            }
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), "method:"
                    + Thread.currentThread().getStackTrace()[1].getMethodName(), TMErrorCode.TM_ADD_OPERATOR_BIZ_TYPE
                    .getCode()
                    + ":添加值班员授权异常,params[" + JSON.toJSONString(operatorAuths) + "]", e);
            throw new HsException(TMErrorCode.TM_ADD_OPERATOR_BIZ_TYPE.getCode(), "添加值班员授权异常,params["
                    + JSON.toJSONString(operatorAuths) + "]" + e);
        }
    }

    /**
     * 获取值班组信息，关联查询出值班员
     * 
     * @param groupId
     *            值班组编号
     * @return 值班组信息
     * @throws HsException
     */
    @Override
    public List<Group> getGroupInfo(String groupId) throws HsException {
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                "获取值班组信息,params[groupId:" + groupId + "]");
        List<Group> groupList = null;
        // 校验值班组编号不为空
        HsAssert.hasText(groupId, TMErrorCode.TM_PARAMS_NULL, "获取值班组信息：值班组编号参数为空");
        try
        {
            // 查询值班组
            groupList = groupMapper.findGroupAndOperatorResMap(groupId);
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), "method:"
                    + Thread.currentThread().getStackTrace()[1].getMethodName(), TMErrorCode.TM_UPDATE_GROUP_ERROR
                    .getCode()
                    + ":获取值班组信息异常,params[groupId:" + groupId + "]", e);
            throw new HsException(TMErrorCode.TM_QUERY_GROUP_ERROR.getCode(), "获取值班组信息异常,params[groupId:" + groupId
                    + "]" + e);
        }
        return groupList;
    }

    /**
     * 获取企业值班组信息
     * 
     * @param entCustId
     *            企业客户号
     * @param isAll
     *            是否查询所有
     * @param isOpen
     *            是否开启
     * @return 值班组信息
     * @throws HsException
     * @see com.gy.hsxt.tm.api.ITMOnDutyService#getTaskGroupInfo(String
     *      entCustId,boolean isall,boolean isOpen)
     */
    public List<Group> getTaskGroupInfo(String entCustId, boolean isAll, boolean isOpen) throws HsException {
        List<Group> listGroup = null;
        // 查询值班组
        listGroup = groupMapper.findGroupAndOperator(entCustId, isAll, isOpen);
        if (listGroup == null)
        {
            listGroup = new ArrayList<>();
        }
        return listGroup;
    }

    /**
     * 查询值班组列表
     * 
     * @param entCustId
     *            企业客户号
     * @return 值班组列表
     * @throws HsException
     * @see com.gy.hsxt.tm.api.ITMOnDutyService#getOpenedGroupList(java.lang.String)
     */
    @Override
    public List<Group> getOpenedGroupList(String entCustId) throws HsException {
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                "查询值班组列表,params[entCustId:" + entCustId + "]");
        List<Group> groupList = null;
        // 校验客户号不为空
        HsAssert.hasText(entCustId, TMErrorCode.TM_PARAMS_NULL, "查询值班组列表：企业客户号参数为空");
        try
        {
            // 查询值班组
            groupList = groupMapper.findOpenedGroupList(entCustId);
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), "method:"
                    + Thread.currentThread().getStackTrace()[1].getMethodName(), TMErrorCode.TM_QUERY_GROUP_ERROR
                    .getCode()
                    + ":查询值班组异常,params[entCustId:" + entCustId + "]", e);
            throw new HsException(TMErrorCode.TM_QUERY_GROUP_ERROR.getCode(), "查询值班组异常,params[entCustId:" + entCustId
                    + "]");
        }
        return groupList;
    }

    /**
     * 查询值班员列表
     * 
     * @param groupId
     *            值班组编号
     * @return 值班员列表
     * @throws HsException
     * @see com.gy.hsxt.tm.api.ITMOnDutyService#queryOperatorList(java.lang.String)
     */
    @Override
    public List<Operator> getOperatorList(String groupId) throws HsException {
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                "获取值班组信息,params[groupId:" + groupId + "]");
        List<Operator> operatorList = null;
        // 校验值班组编号不为空
        HsAssert.hasText(groupId, TMErrorCode.TM_PARAMS_NULL, "获取值班组信息：值班组编号参数为空");
        try
        {
            // 查询值班员
            operatorList = operatorMapper.findOperatorList(groupId);
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), "method:"
                    + Thread.currentThread().getStackTrace()[1].getMethodName(),
                    TMErrorCode.TM_QUERY_OPERATOR_LIST_ERROR.getCode() + ":获取值班组信息异常,params[groupId:" + groupId + "]",
                    e);
            throw new HsException(TMErrorCode.TM_QUERY_OPERATOR_LIST_ERROR.getCode(), "获取值班组信息异常,params[groupId:"
                    + groupId + "]" + e);
        }
        return operatorList;
    }

    /**
     * 获取值班员信息
     * 
     * @param optCustId
     *            值班员编号
     * @param groupId
     *            值班组编号
     * @param scheduleId
     *            值班计划编号
     * @return 值班员信息
     * @throws HsException
     * @see com.gy.hsxt.tm.api.ITMOnDutyService#getOperatorInfo(java.lang.String,
     *      java.lang.String, java.lang.String)
     */
    @Override
    public Map<String, Object> getOperatorInfo(String optCustId, String groupId, String scheduleId) throws HsException {
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                "获取值班员信息,params[optCustId:" + optCustId + ",groupId:" + groupId + ",scheduleId:" + scheduleId + "]");
        // 校验值班员编号不为空
        HsAssert.hasText(optCustId, TMErrorCode.TM_PARAMS_NULL, "获取值班员信息：值班员编号参数为空");
        Map<String, Object> map = new HashMap<String, Object>();
        try
        {
            String planDate = com.gy.hsxt.common.utils.DateUtil.getCurrentDateNoSign();
            // 查询值班员信息
            List<CustomOperator> operatorInfo = operatorMapper.findOperator(optCustId, groupId);
            // 查询休假天数
            int restNum = scheduleOptMapper.findRestNum(optCustId, DateUtil.DateToString(new Date(), "yyyyMM"));
            // 查询授权业务
            List<BizTypeAuth> authList = authMapper.findOptCustAuthList(optCustId);
            // 查询当天值班状态
            ScheduleOpt workType = scheduleOptMapper.findWorkType(optCustId, planDate, scheduleId);
            // 设置值班员信息
            map.put("operatorInfo", operatorInfo);
            // 设置休假天数
            map.put("restNum", restNum);
            // 设置授权业务
            map.put("authList", authList);
            // 设置当天值班状态
            int nowType = 0;
            if (workType != null && workType.getWorkType() != null)
            {
                nowType = workType.getWorkType();
            }
            map.put("workType", nowType == 0 ? null : WorkTypeStatus.getDescribe(nowType));
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), "method:"
                    + Thread.currentThread().getStackTrace()[1].getMethodName(),
                    TMErrorCode.TM_QUERY_OPERATOR_INFO_ERROR.getCode() + ":查询值班员信息异常,params[optCustId:" + optCustId
                            + ",groupId:" + groupId + ",scheduleId:" + scheduleId + "]", e);
            throw new HsException(TMErrorCode.TM_QUERY_OPERATOR_INFO_ERROR.getCode(), "查询值班员信息异常,params[optCustId:"
                    + optCustId + ",groupId:" + groupId + ",scheduleId:" + scheduleId + "]" + e);
        }
        return map;
    }

    /**
     * 获取值班组列表
     * 
     * @param entCustId
     *            企业客户号
     * @return 值班组列表
     * @throws HsException
     * @see com.gy.hsxt.tm.api.ITMOnDutyService#getGroupList(java.lang.String)
     */
    @Override
    public List<Group> getGroupList(String entCustId) throws HsException {
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                "获取值班组列表,params[entCustId:" + entCustId + "]");
        // 校验企业客户号不为空
        HsAssert.hasText(entCustId, TMErrorCode.TM_PARAMS_NULL, "获取值班组列表：企业客户号参数为空");
        try
        {
            // 查询值班组列表
            return groupMapper.findGroupList(entCustId);
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), "method:"
                    + Thread.currentThread().getStackTrace()[1].getMethodName(), TMErrorCode.TM_QUERY_GROUP_ERROR
                    .getCode()
                    + ":获取值班组列表异常,params[entCustId:" + entCustId + "]", e);
            throw new HsException(TMErrorCode.TM_QUERY_GROUP_ERROR.getCode(), "获取值班组列表异常,params[entCustId:" + entCustId
                    + "]" + e);
        }
    }

    /**
     * 获取值班员列表
     * 
     * @param entCustId
     *            企业客户号
     * @param bizTypeList
     *            业务类型列表
     * @return 值班员列表
     * @throws HsException
     * @see com.gy.hsxt.tm.api.ITMOnDutyService#getOperatorListByCustId(java.lang.String)
     */
    @Override
    public List<Operator> getOperatorListByCustId(String entCustId, List<String> bizTypeList) throws HsException {
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                "获取值班员列表,params[entCustId:" + entCustId + "]");
        // 校验企业客户号不为空
        HsAssert.hasText(entCustId, TMErrorCode.TM_PARAMS_NULL, "获取值班员列表：企业客户号参数为空");
        List<Operator> operatorList = null;
        try
        {
            // List去重
            List<String> bizTypeSizeList = new ArrayList<String>(new HashSet<String>(bizTypeList));
            if (bizTypeSizeList != null)
            {
                // 查询企业值班员列表
                operatorList = operatorMapper.findOperatorListByEntCustId(entCustId, bizTypeSizeList, bizTypeSizeList
                        .size());
            }
            if (operatorList == null)
            {
                operatorList = new ArrayList<Operator>();
            }
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                    TMErrorCode.TM_QUERY_OPERATOR_LIST_ERROR.getCode() + ":获取值班员列表异常,params[entCustId:" + entCustId
                            + "]", e);
            throw new HsException(TMErrorCode.TM_QUERY_OPERATOR_LIST_ERROR.getCode(), "获取值班员列表异常,params[entCustId:"
                    + entCustId + "]" + e);
        }
        return operatorList;
    }

    /**
     * 获取值班计划
     * 
     * @param groupId
     *            值班组编号
     * @param planYear
     *            计划年份
     * @param planMonth
     *            计划月份
     * @return 值班员关联计划
     * @throws HsException
     * @see com.gy.hsxt.tm.api.ITMOnDutyService#getOperatorSchedule(java.lang.String,
     *      java.lang.String, java.lang.String)
     */
    @Override
    public Map<String, Object> getOperatorSchedule(String groupId, String planYear, String planMonth)
            throws HsException {
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                "获取值班计划,params[groupId:" + groupId + ",planYear:" + planYear + ",planMonth:" + planMonth + "]");
        // 值班组编号为空
        HsAssert.hasText(groupId, TMErrorCode.TM_PARAMS_NULL, "获取值班计划：值班组编号参数为空");
        // 计划年份为空
        HsAssert.hasText(planYear, TMErrorCode.TM_PARAMS_NULL, "获取值班计划：计划年份参数为空");
        // 计划月份为空
        HsAssert.hasText(planMonth, TMErrorCode.TM_PARAMS_NULL, "获取值班计划：计划月份参数为空");

        try
        {
            // 查询值班员列表
            List<Operator> operators = operatorMapper.findOperatorList(groupId);
            // 查询排班信息
            List<ScheduleOpt> scheduleOpts = scheduleOptMapper.findOperatorScheduleOpt(groupId, planYear, planMonth);
            // 查询值班计划
            Schedule schedule = scheduleMapper.findExistsSchedule(planYear, planMonth, groupId);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("operators", operators);
            map.put("scheduleOpts", scheduleOpts);
            map.put("scheduleId", schedule == null ? null : schedule.getScheduleId());
            map.put("scheduleStatus", schedule == null ? null : schedule.getStatus());
            return map;
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), "method:"
                    + Thread.currentThread().getStackTrace()[1].getMethodName(),
                    TMErrorCode.TM_GET_SCHEDULE_OPT_ERROR.getCode() + ":获取值班计划异常,params[groupId:" + groupId
                            + ",planYear:" + planYear + ",planMonth:" + planMonth + "]", e);
            throw new HsException(TMErrorCode.TM_GET_SCHEDULE_OPT_ERROR.getCode(), "获取值班计划异常,params[groupId:" + groupId
                    + ",planYear:" + planYear + ",planMonth:" + planMonth + "]" + e);
        }
    }

    /**
     * 移除值班员
     * 
     * @param groupId
     *            值班组编号
     * @param optCustId
     *            值班员编号
     * @throws HsException
     * @see com.gy.hsxt.tm.api.ITMOnDutyService#removeOperator(java.lang.String,
     *      java.lang.String)
     */
    @Override
    @Transactional
    public void removeOperator(String groupId, String optCustId) throws HsException {
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                "移除值班员,params[groupId:" + groupId + ",optCustId:" + optCustId + "]");
        // 值班组编号不为空
        HsAssert.hasText(groupId, TMErrorCode.TM_PARAMS_NULL, "移除值班员：值班组编号参数为空");
        // 值班员编号不为空
        HsAssert.hasText(optCustId, TMErrorCode.TM_PARAMS_NULL, "移除值班员：值班员编号参数为空");
        int resultNum = 0;
        try
        {
            resultNum = operatorMapper.findOperatorById(optCustId);
            // 删除授权
            if (resultNum <= 1)
            {
                bizTypeAuthMapper.deleteOptAuth(groupId, optCustId);
            }
            // 删除值班员
            operatorMapper.deleteOperator(groupId, optCustId);

            // 获取当前时间以后的值班计划
            List<String> scheduleIdList = scheduleMapper.findNowAfterScheduleId(groupId);
            // 删除当前时间以后的排班
            if (scheduleIdList.size() > 0)
            {
                scheduleOptMapper.deleteScheduleOptNowAfter(scheduleIdList, optCustId);
            }
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), "method:"
                    + Thread.currentThread().getStackTrace()[1].getMethodName(), TMErrorCode.TM_DELETE_OPERATOR_ERROR
                    .getCode()
                    + ":移除值班员异常,params[groupId:" + groupId + ",optCustId:" + optCustId + "]", e);
            throw new HsException(TMErrorCode.TM_DELETE_OPERATOR_ERROR, "移除值班员异常,params[groupId:" + groupId
                    + ",optCustId:" + optCustId + "]" + e);
        }
    }

    /**
     * 删除值班员
     * 
     * @param optCustId
     *            值班员编号
     * @throws HsException
     * @see com.gy.hsxt.tm.api.ITMOnDutyService#removeOperator(java.lang.String)
     */
    @Override
    @Transactional
    public void removeOperator(String optCustId) throws HsException {
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                "删除值班员,params[optCustId:" + optCustId + "]");
        // 值班员编号不为空
        HsAssert.hasText(optCustId, TMErrorCode.TM_PARAMS_NULL, "删除值班员：值班员编号参数为空");
        try
        {
            Operator operator = operatorMapper.findOperatorInfo(optCustId);
            if (operator != null)
            {
                // 删除值班员
                operatorMapper.deleteOperatorById(optCustId);
                // 删除授权
                bizTypeAuthMapper.deleteAuth(optCustId);
                // 删除当前时间以后的排班
                scheduleOptMapper.deleteScheduleOptNowAfter(null, optCustId);
            }
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                    TMErrorCode.TM_DELETE_OPERATOR_ERROR.getCode() + ":删除值班员异常,params[optCustId:" + optCustId + "]", e);
            throw new HsException(TMErrorCode.TM_DELETE_OPERATOR_ERROR, "删除值班员异常,params[optCustId:" + optCustId + "]"
                    + e);
        }
    }

    /**
     * 删除值班员授权
     * 
     * @param bizTypeCode
     *            业务类型代码
     * @param optCustId
     *            值班员编号
     * @throws HsException
     * @see com.gy.hsxt.tm.api.ITMOnDutyService#deleteOptCustAuth(java.lang.String,
     *      java.lang.String)
     */
    @Override
    public void deleteOptCustAuth(Set<String> bizTypeCode, String optCustId) throws HsException {
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                "删除值班员授权,params[bizTypeCode:" + bizTypeCode + ",optCustId:" + optCustId + "]");
        // 业务类型代码为空
        HsAssert.notNull(bizTypeCode, TMErrorCode.TM_PARAMS_NULL, "删除值班员授权：业务类型代码参数为空");
        // 值班员编号不为空
        HsAssert.hasText(optCustId, TMErrorCode.TM_PARAMS_NULL, "删除值班员授权：值班员编号参数为空");
        try
        {
            // 删除授权
            authMapper.deleteOptCustAuth(bizTypeCode, optCustId);
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), "method:"
                    + Thread.currentThread().getStackTrace()[1].getMethodName(),
                    TMErrorCode.TM_DELETE_OPERATOR_AUTH_ERROR.getCode() + ":删除值班员授权异常,params[bizTypeCode:"
                            + bizTypeCode + ",optCustId:" + optCustId + "]", e);
            throw new HsException(TMErrorCode.TM_DELETE_OPERATOR_AUTH_ERROR, "删除值班员授权时异常,params[bizTypeCode:"
                    + bizTypeCode + ",optCustId:" + optCustId + "]" + e);
        }
    }

    /**
     * 修改值班主任
     * 
     * @param isChief
     *            是否主任
     * @param optCustId
     *            值班员编号
     * @throws HsException
     * @see com.gy.hsxt.tm.api.ITMOnDutyService#updateChief(java.lang.Integer,
     *      java.lang.String)
     */
    @Override
    public void updateChief(boolean isChief, String optCustId) throws HsException {
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                "修改值班主任,params[isChief:" + isChief + ",optCustId:" + optCustId + "]");
        HsAssert.hasText(optCustId, TMErrorCode.TM_PARAMS_NULL, "修改值班主任：值班员编号参数为空");
        try
        {
            // 更新值班主任
            operatorMapper.updateChief(isChief, optCustId);
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), "method:"
                    + Thread.currentThread().getStackTrace()[1].getMethodName(), TMErrorCode.TM_CHANGE_CHIEF_ERROR
                    .getCode()
                    + ":修改值班主任异常,params[isChief:" + isChief + ",optCustId:" + optCustId + "]", e);
            throw new HsException(TMErrorCode.TM_CHANGE_CHIEF_ERROR, "修改值班主任异常,params[isChief:" + isChief
                    + ",optCustId:" + optCustId + "]" + e);
        }
    }

    /**
     * 更新值班组开关状态
     * 
     * @param groupId
     *            值班组编号
     * @param opened
     *            开关状态
     * @throws HsException
     * @see com.gy.hsxt.tm.api.ITMOnDutyService#udpateGroupOpenedStatus(java.lang.String,
     *      java.lang.Integer)
     */
    @Override
    public void udpateGroupOpenedStatus(String groupId, Integer opened) throws HsException {
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                "更新值班组开关状态,params[groupId:" + groupId + ",opened:" + opened + "]");
        // 值班组编号为空
        HsAssert.hasText(groupId, TMErrorCode.TM_PARAMS_NULL, "更新值班组开关状态：值班组编号参数为空");
        // 开关状态为空
        HsAssert.notNull(opened, TMErrorCode.TM_PARAMS_NULL, "更新值班组开关状态：开关状态参数为空");
        try
        {
            if (GroupStatus.CLOSED.getCode().intValue() == opened)
                // 修改值班组状态
                groupMapper.updateGroupOpened(groupId, opened);
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), "method:"
                    + Thread.currentThread().getStackTrace()[1].getMethodName(),
                    TMErrorCode.TM_UPDATE_GROUP_OPEN_CLOSE_ERROR.getCode() + ":更新值班组开关状态异常,params[groupId:" + groupId
                            + ",opened:" + opened + "]", e);
            throw new HsException(TMErrorCode.TM_UPDATE_GROUP_OPEN_CLOSE_ERROR.getCode(), "更新值班组开关状态异常,params[groupId:"
                    + groupId + ",opened:" + opened + "]" + e);
        }
    }

    /**
     * 同步操作员名称
     * 
     * @param optCustId
     *            操作员编号
     * @param optName
     *            操作员名称
     * @throws HsException
     * @see com.gy.hsxt.tm.api.ITMOnDutyService#synOperatorName(java.lang.String,java.lang.String)
     */
    @Override
    public void synOperatorName(String optCustId, String optName) throws HsException {
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                "同步操作员名称,params[optCustId:" + optCustId + "]");
        HsAssert.hasText(optCustId, TMErrorCode.TM_PARAMS_NULL, "同步操作员名称：操作员编号参数为空");
        operatorMapper.updateOperatorName(optCustId, optName);
    }

    /**
     * 查询是否值班主任
     * 
     * @param optCustId
     *            操作员编号
     * @return true:是 false:否
     * @throws HsException
     * @see com.gy.hsxt.tm.api.ITMOnDutyService#isChief(java.lang.String)
     */
    @Override
    public boolean isChief(String optCustId) throws HsException {
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                "查询是否值班主任,params[optCustId:" + optCustId + "]");
        HsAssert.hasText(optCustId, TMErrorCode.TM_PARAMS_NULL, "查询是否值班主任：操作员编号参数为空");
        int resNum = operatorMapper.findOperatorIsChief(optCustId);
        return resNum >= 1 ? true : false;
    }

    /**
     * 查询值班员是否在值班级中为值班主任
     * 
     * @param groupId
     *            值班组编号
     * @param optCustId
     *            值班员编号
     * @return true:是 false:否
     * @throws HsException
     * @see com.gy.hsxt.tm.api.ITMOnDutyService#isChief(java.lang.String,
     *      java.lang.String)
     */
    @Override
    public boolean isChief(String groupId, String optCustId) throws HsException {
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                "查询是否值班主任,params[optCustId:" + optCustId + "]");
        HsAssert.hasText(optCustId, TMErrorCode.TM_PARAMS_NULL, "查询是否值班主任：操作员编号参数为空");
        int resNum = operatorMapper.findOperatorInGroupIsChief(groupId, optCustId);
        return resNum >= 1 ? true : false;
    }

    /**
     * 保存值班员
     * 
     * @param operators
     *            值班员列表
     * @param groupId
     *            值班组编号
     */
    private void saveOperator(List<Operator> operators, String groupId, String groupName, String scheduleId) {
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                "保存值班员,params[operators:" + operators + ",groupId:" + groupId + ",groupName:" + groupName
                        + ",scheduleId:" + scheduleId + "]");
        try
        {
            Set<String> optCustIdList = new HashSet<String>();
            for (Operator operator : operators)
            {
                optCustIdList.add(operator.getOptCustId());
            }
            // 删除不在当前列表中的值班员
            operatorMapper.deleteOperatorByOptCustId(groupId, optCustIdList);
            if (StringUtils.isNotBlank(scheduleId))
            {
                // 删除排班
                scheduleOptMapper.deleteScheduleOpt(scheduleId, optCustIdList);
            }
            for (Operator operator : operators)
            {
                // 校验值班员是否已存在
                Operator operatorInfo = operatorMapper.findGroupOperatorInfo(groupId, operator.getOptCustId());
                // 不存在值班员
                if (operatorInfo == null)
                {
                    // 设置值班组编号
                    operator.setGroupId(groupId);
                    // 保存值班员
                    operatorMapper.insertOperator(operator);
                }
                else
                // 已存在更新值班员值班组
                {
                    // 设置值班员到值班组
                    setUnGroupOperator(operatorInfo.getOptCustId(), groupId, operator.getChief());
                }
            }
        }
        catch (Exception e)
        {
            throw new HsException(TMErrorCode.TM_SAVE_OPERATOR_ERROR.getCode(), "保存值班员异常" + e);
        }
    }

    /**
     * 检查值班组名称是否重复
     * 
     * @param entCustId
     *            企业客户号
     * @param groupName
     *            值班组名称
     * @return true存在false不存在
     */
    private boolean checkGroupName(String entCustId, String groupName) {
        // 返回记录数
        int resultNum = 0;
        // 查询是否存在相同名称值班组
        resultNum = groupMapper.findGroupNameIsExists(entCustId, groupName);
        if (resultNum > 0)
        {
            return true;
        }
        return false;
    }

    /**
     * 检查值班组名称是否重复
     * 
     * @param entCustId
     *            企业客户号
     * @param groupId
     *            值班组编号
     * @param groupName
     *            值班组名称
     * @return true存在false不存在
     */
    private boolean checkGroupNameModify(String entCustId, String groupId, String groupName) {
        // 返回记录数
        int resultNum = 0;
        // 查询是否存在相同名称值班组
        resultNum = groupMapper.findGroupNameIsExistsByModify(entCustId, groupId, groupName);
        if (resultNum > 0)
        {
            return true;
        }
        return false;
    }

    /**
     * 设置无值班组的值班员到值班组
     * 
     * @param optCustId
     *            值班员编号
     * @param groupId
     *            值班组编号
     * @param isChief
     *            值班主任：true是，false否
     */
    private void setUnGroupOperator(String optCustId, String groupId, boolean isChief) {
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                "设置无值班组的值班员到值班组,params[optCustId:" + optCustId + ",groupId:" + groupId + ",isChief:" + isChief + "]");
        // 更新值班员的值班组
        operatorMapper.updateOperatorGroup(optCustId, groupId, isChief);
    }

    /**
     * 查验是否设置值班主任
     * 
     * @param operators
     *            值班员列表
     * @return true有false无
     */
    private boolean checkIsSetChief(List<Operator> operators) {
        // 值班主任数量
        int chiefNum = 0;

        // 迭代值班员列表
        for (Operator operator : operators)
        {
            // 校验值班主任主字段
            if (operator.getChief())
            {
                chiefNum++;
            }
        }
        // 大于0表示设置有值班主任
        if (chiefNum > 0)
        {
            return true;
        }
        return false;
    }
}
