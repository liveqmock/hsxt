/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO.,
 * LTD. All rights reserved.
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.bs.resfee.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gy.hsi.lc.client.log4j.BizLog;
import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.bs.api.resfee.IBSResFeeService;
import com.gy.hsxt.bs.bean.resfee.ResFee;
import com.gy.hsxt.bs.bean.resfee.ResFeeQuery;
import com.gy.hsxt.bs.bean.resfee.ResFeeRule;
import com.gy.hsxt.bs.bean.resfee.ResFeeTool;
import com.gy.hsxt.bs.common.enumtype.BSRespCode;
import com.gy.hsxt.bs.common.enumtype.ProjectStatus;
import com.gy.hsxt.bs.common.enumtype.apply.ResType;
import com.gy.hsxt.bs.common.enumtype.resfee.AllocTarget;
import com.gy.hsxt.bs.common.enumtype.resfee.AllocWay;
import com.gy.hsxt.bs.common.interfaces.ICommonService;
import com.gy.hsxt.bs.disconf.BsConfig;
import com.gy.hsxt.bs.resfee.interfaces.IResFeeService;
import com.gy.hsxt.bs.resfee.mapper.ResFeeMapper;
import com.gy.hsxt.bs.resfee.mapper.ResFeeRuleMapper;
import com.gy.hsxt.bs.resfee.mapper.ResFeeToolMapper;
import com.gy.hsxt.bs.task.bean.Task;
import com.gy.hsxt.bs.task.interfaces.ITaskService;
import com.gy.hsxt.common.constant.BizGroup;
import com.gy.hsxt.common.constant.CustType;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.constant.TaskStatus;
import com.gy.hsxt.common.constant.TaskType;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.guid.GuidAgent;
import com.gy.hsxt.common.utils.HsAssert;
import com.gy.hsxt.common.utils.HsResNoUtils;

/**
 * 资源费实现类
 * 
 * @Package : com.gy.hsxt.bs.resfee
 * @ClassName : ResFeeService
 * @Description : 资源费实现类
 * @Author : liuhq
 * @Date : 2015-10-14 下午4:48:52
 * @Version V1.0
 */
@Service
public class ResFeeService implements IBSResFeeService, IResFeeService {
    /**
     * 业务服务私有配置参数
     **/
    @Resource
    private BsConfig bsConfig;

    /**
     * 注入 资源费方案 接口
     */
    @Resource
    private ResFeeMapper resFeeMapper;

    /**
     * 注入 资源费方案分配规则 接口
     */
    @Resource
    private ResFeeRuleMapper resFeeRuleMapper;

    /**
     * 注入 资源费方案包含工具 接口
     */
    @Resource
    private ResFeeToolMapper resFeeToolMapper;

    /**
     * 工单业务接口
     */
    @Resource
    private ITaskService taskService;

    /**
     * 平台数据接口
     */
    @Resource
    private ICommonService commonService;

    /**
     * 创建 资源费方案
     * 
     * @param resFee
     *            实体类 非null
     * @throws HsException
     */
    @Override
    @Transactional
    public void createResFee(ResFee resFee) throws HsException {
        // 写入系统日志
        SystemLog.debug(bsConfig.getSysName(), "function:createResFee", "创建资源费方案--实体参数[resFee]:" + resFee);
        // 实体参数为null时抛出异常
        HsAssert.notNull(resFee, RespCode.PARAM_ERROR, "资源费方案--实体参数[resFee]为null");

        try
        {
            // 获取 某一条待审的资源费方案是否已经存在
            String pendingStatus = resFeeMapper.getPendingStatus(resFee.getToCustType(), resFee.getToBuyResRange());
            // 此类型待审的资源费方案已经存在，请不要重复提交
            HsAssert.isTrue(StringUtils.isBlank(pendingStatus), BSRespCode.BS_RES_FEE_EXIST,
                    "此类型企业待审的资源费方案已经存在，请不要重复提交");

            // 生成资源费方案GUID
            resFee.setResFeeId(GuidAgent.getStringGuid(BizGroup.BS + bsConfig.getAppNode()));
            // 执行创建
            int count = resFeeMapper.createResFee(resFee);
            // 创建操作日志
            BizLog.biz(bsConfig.getSysName(), "function:createResFee", "params==>" + resFee, "创建资源费方案成功");

            // 保存申请后，派送工单
            if (count == 1)
            {
                String platCustId = commonService.getAreaPlatCustId();
                HsAssert.hasText(platCustId, BSRespCode.BS_QUERY_AREA_PLAT_CUST_ID_ERROR, "查询地区平台客户号失败");
                // 生成资源费方案审批工单，属于平台的业务不用区分业务主体
                taskService.saveTask(new Task(resFee.getResFeeId(), TaskType.TASK_TYPE202.getCode(), platCustId));
            }

        }
        catch (HsException hse)
        {
            throw hse;
        }
        catch (Exception e)
        {
            // 写入系统日志
            SystemLog.error(bsConfig.getSysName(), "function:createResFee", "资源费：创建资源费方案失败，发生未知错误,param:" + resFee, e);

            throw new HsException(BSRespCode.BS_RES_FEE_DB_ERROR, "资源费：创建资源费方案失败，发生未知错误,param:" + resFee + "\n" + e);
        }
    }

    /**
     * 查询 资源费方案列表
     * 
     * @param resFee
     *            实体类 通过指定bean的属性进行条件查询
     * @return 返回按条件查询List数据列表
     * @throws HsException
     */
    @Override
    public List<ResFee> queryResFeeList(ResFee resFee) throws HsException {
        // 写入系统日志
        SystemLog.debug(bsConfig.getSysName(), "function:queryResFeeList", "查询资源费方案列表--实体参数[resFee]:" + resFee);
        try
        {
            return resFeeMapper.queryResFeeList(resFee);
        }
        catch (Exception e)
        {
            // 写入系统日志
            SystemLog.error(bsConfig.getSysName(), "function:queryResFeeList",
                    "资源费：查询资源费方案列表失败，发生未知错误,param:" + resFee, e);

            throw new HsException(BSRespCode.BS_RES_FEE_DB_ERROR, "资源费：查询资源费方案列表失败，发生未知错误,param:" + resFee + "\n" + e);
        }
    }

    /**
     * 查询资源费方案审核列表
     * 
     * @param resFeeQuery
     *            条件查询
     * @return 返回按条件查询List数据列表
     * @throws HsException
     */
    @Override
    public List<ResFee> queryResFeeTaskList(ResFeeQuery resFeeQuery) throws HsException {
        // 写入系统日志
        SystemLog.debug(bsConfig.getSysName(), "function:queryResFeeList", "查询资源费方案审核列表参数[resFeeQuery]:" + resFeeQuery);
        HsAssert.notNull(resFeeQuery, RespCode.PARAM_ERROR, "查询条件[resFeeQuery]为空");
        HsAssert.hasText(resFeeQuery.getOptCustId(), RespCode.PARAM_ERROR, "操作员客户号[optCustId]为空");

        try
        {
            resFeeQuery.setStatus(0);// 待审批的状态
            resFeeQuery.setTaskType(TaskType.TASK_TYPE202.getCode());
            resFeeQuery.setTaskStatus(TaskStatus.DEALLING.getCode());

            return resFeeMapper.selectResFeeTaskList(resFeeQuery);
        }
        catch (Exception e)
        {
            // 写入系统日志
            SystemLog.error(bsConfig.getSysName(), "function:queryResFeeList", "查询资源费方案审核列表失败，参数[resFeeQuery]:"
                    + resFeeQuery, e);

            throw new HsException(BSRespCode.BS_RES_FEE_DB_ERROR, "查询资源费方案审核列表失败，原因:" + e.getMessage());
        }
    }

    /**
     * 获取 某一条资源费方案
     * 
     * @param resFeeId
     *            资源费方案编号 必须 非null
     * @return 返回一条资源费方案记录
     * @throws HsException
     */
    @Override
    public ResFee getResFeeBean(String resFeeId) throws HsException {

        // 写入系统日志
        SystemLog.debug(bsConfig.getSysName(), "function:getResFeeBean", "资源费：获取某一条资源费方案--资源费方案编号[resFeeId]:"
                + resFeeId);
        // 资源费方案编号为空时
        HsAssert.hasText(resFeeId, RespCode.PARAM_ERROR, "资源费方案编号[resFeeId]为空");

        ResFee resFee;// 创建空对象
        try
        {
            resFee = resFeeMapper.getResFeeBean(resFeeId);
        }
        catch (Exception e)
        {
            // 写入系统日志
            SystemLog.error(bsConfig.getSysName(), "function:getResFeeBean", "资源费：查询资源费方案列表失败，发生未知错误", e);

            throw new HsException(BSRespCode.BS_RES_FEE_DB_ERROR, "资源费：查询资源费方案列表失败，发生未知错误");
        }
        return resFee;
    }

    /**
     * 审批 资源费方案
     * 
     * @param resFee
     *            实体类 非null
     * @throws HsException
     */
    @Override
    @Transactional
    public void apprResFee(ResFee resFee) throws HsException {
        // 写入系统日志
        SystemLog.debug(bsConfig.getSysName(), "function:apprResFee", "资源费：审批 资源费方案--实体类为[resFee]:" + resFee);
        // 实体类为null时抛出异常
        HsAssert.notNull(resFee, RespCode.PARAM_ERROR, "资源费:审批 资源费方案--实体类[resFee]为null");

        HsAssert.hasText(resFee.getResFeeId(), RespCode.PARAM_ERROR, "资源费方案ID[resFeeId]为空");

        HsAssert.hasText(resFee.getApprOperator(), RespCode.PARAM_ERROR, "审批操作人[apprOperator]为空");

        // 状态参数 1：已启用 2：审批驳回 3：已停用
        // 如果状态参数类型错误抛出异常
        HsAssert.isTrue(ProjectStatus.checkStatus(resFee.getStatus()), RespCode.PARAM_ERROR, "状态参数[status]类型错误");

        try
        {
            int count = resFeeMapper.apprResFee(resFee);

            // 创建操作日志
            BizLog.biz(bsConfig.getSysName(), "function:apprResFee", "params==>" + resFee, "审批资源费方案成功");
            if (count == 1)
            {
                // 审批操作完成时，更新工单状态
                String taskId = taskService.getSrcTask(resFee.getResFeeId(), resFee.getApprOperator());
                HsAssert.hasText(taskId, BSRespCode.BS_TASK_STATUS_NOT_DEALLING, "任务状态不是办理中");
                taskService.updateTaskStatus(taskId, TaskStatus.COMPLETED.getCode());
            }
        }
        catch (Exception e)
        {
            // 写入系统日志
            SystemLog.error(bsConfig.getSysName(), "function:apprResFee", "资源费：审批资源费方案失败，发生未知错误", e);

            throw new HsException(BSRespCode.BS_RES_FEE_DB_ERROR, "资源费：审批资源费方案失败，发生未知错误");
        }
    }

    // =================== 资源费分配规则 开始 =====================

    /**
     * 创建 资源费分配规则
     * 
     * @param resFeeRule
     *            实体类 非null
     * @throws HsException
     */
    @Override
    @Transactional
    public void createResFeeRule(ResFeeRule resFeeRule) throws HsException {
        // 写入系统日志
        SystemLog.debug(bsConfig.getSysName(), "function:createResFeeRule", "资源费：创建资源费分配规则--实体参数[resFeeRule]:"
                + resFeeRule);
        // 实体参数为null时抛出异常
        HsAssert.notNull(resFeeRule, RespCode.PARAM_ERROR, "资源费分配规则--实体参数[resFeeRule]为null");

        // 分配方式 1：按比例分配 2：按金额分配
        HsAssert.isTrue(AllocWay.checkWay(resFeeRule.getAllocWay()), RespCode.PARAM_ERROR, "资源费分配方式[allocWay]参数类型错误");

        // 1：推广公司（服务公司或地区平台） 2：上级管理公司 3：地区平台
        HsAssert.isTrue(AllocTarget.checkTarget(resFeeRule.getAllocTarget()), RespCode.PARAM_ERROR,
                "资源费分配对象[allocTarget]参数类型错误");

        // 生成资源费方案GUID
        resFeeRule.setAllocItemId(GuidAgent.getStringGuid(BizGroup.BS + bsConfig.getAppNode()));

        try
        {
            resFeeRuleMapper.createResFeeRule(resFeeRule);

            // 创建操作日志
            BizLog.biz(bsConfig.getSysName(), "function:createResFeeRule", "params==>" + resFeeRule, "创建资源费分配规则成功");
        }
        catch (Exception e)
        {
            // 写入系统日志
            SystemLog.error(bsConfig.getSysName(), "function:" + "createResFeeRule", "资源费：创建资源费分配规则失败，发生未知错误", e);

            throw new HsException(BSRespCode.BS_RES_FEE_DB_ERROR, "资源费：创建资源费分配规则失败，发生未知错误");
        }
    }

    /**
     * 查询某一个资源费方案的分配规则
     * 
     * @param resFeeId
     *            资源费方案编号 必须 非null
     * @return 返回按条件查询的数据List
     * @throws HsException
     */
    @Override
    public List<ResFeeRule> queryResFeeRuleList(String resFeeId) throws HsException {
        // 写入系统日志
        SystemLog.debug(bsConfig.getSysName(), "function:queryResFeeRuleList", "查询某一个资源费方案的分配规则--资源费方案编号[resFeeId]:"
                + resFeeId);

        // 资源费方案编号为空抛出异常
        HsAssert.hasText(resFeeId, RespCode.BP_PARAMETER_NULL, "资源费方案编号[resFeeId]为空");

        List<ResFeeRule> list;// 创建空对象
        try
        {
            list = resFeeRuleMapper.queryResFeeRuleList(resFeeId);
        }
        catch (Exception e)
        {
            // 写入系统日志
            SystemLog.error(bsConfig.getSysName(), "function:" + "queryResFeeRuleList",
                    "资源费：查询 某一个资源费方案的分配规则失败，发生未知错误", e);

            throw new HsException(BSRespCode.BS_RES_FEE_DB_ERROR, "资源费：查询 某一个资源费方案的分配规则失败，发生未知错误");
        }
        return list;
    }

    /**
     * 获取某一条分配规则
     * 
     * @param allocItemId
     *            分配规则项编号 非null
     * @return 返回某一条分配规则记录
     * @throws HsException
     */
    @Override
    public ResFeeRule getResFeeRuleBean(String allocItemId) throws HsException {

        // 写入系统日志
        SystemLog.debug(bsConfig.getSysName(), "function:getResFeeRuleBean", "获取某一条分配规则--资源费分配规则编号[allocItemId]:"
                + allocItemId);
        // 分配规则项编号为空抛出异常
        HsAssert.hasText(allocItemId, RespCode.BP_PARAMETER_NULL, "资源费分配规则编号[allocItemId]为空");

        ResFeeRule resFeeRule;// 创建空对象
        try
        {
            resFeeRule = resFeeRuleMapper.getResFeeRuleBean(allocItemId);
        }
        catch (Exception e)
        {
            // 写入系统日志
            SystemLog.error(bsConfig.getSysName(), "function:getResFeeRuleBean", "资源费：获取 某一条分配规则失败，发生未知错误", e);

            throw new HsException(BSRespCode.BS_RES_FEE_DB_ERROR, "资源费：获取 某一条分配规则失败，发生未知错误");
        }
        return resFeeRule;
    }

    // =================== 资源费包含工具 开始 =====================

    /**
     * 创建 资源费包含工具
     * 
     * @param resFeeTool
     *            实体类 非null
     * @throws HsException
     */
    @Override
    @Transactional
    public void createResFeeTool(ResFeeTool resFeeTool) throws HsException {
        // 写入系统日志
        SystemLog.debug(bsConfig.getSysName(), "function:createResFeeTool", "资源费：创建资源费包含工具--实体[resFeeTool]参数:"
                + resFeeTool);
        // 实体参数为null时抛出异常
        HsAssert.notNull(resFeeTool, RespCode.PARAM_ERROR, "资源费包含工具实体[resFeeTool]参数为null");

        // 非异常便成功
        try
        {
            resFeeToolMapper.createResFeeTool(resFeeTool);

            // 创建操作日志
            BizLog.biz(bsConfig.getSysName(), "function:createResFeeTool", "params==>" + resFeeTool, "创建 资源费包含工具成功");
        }
        catch (Exception e)
        {
            // 写入系统日志
            SystemLog.error(bsConfig.getSysName(), "function:createResFeeTool", "资源费：创建 资源费包含工具失败，发生未知错误", e);

            throw new HsException(BSRespCode.BS_RES_FEE_DB_ERROR, "资源费：创建 资源费包含工具失败，发生未知错误");
        }
    }

    /**
     * 查询某一个资源费包含工具
     * 
     * @param resFeeId
     *            资源费方案编号 必须 非null
     * @return 返回按条件查询的数据List
     * @throws HsException
     */
    @Override
    public List<ResFeeTool> queryResFeeToolList(String resFeeId) throws HsException {
        // 写入系统日志
        SystemLog.debug(bsConfig.getSysName(), "function:queryResFeeToolList", "资源费:资源费包含工具--资源费方案编号[resFeeId]:"
                + resFeeId);
        // 分配规则项编号为空抛出异常
        HsAssert.hasText(resFeeId, RespCode.BP_PARAMETER_NULL, "资源费方案编号[resFeeId]为空");

        List<ResFeeTool> list;// 创建空对象
        try
        {
            list = resFeeToolMapper.queryResFeeToolList(resFeeId);
        }
        catch (Exception e)
        {
            // 写入系统日志
            SystemLog.error(bsConfig.getSysName(), "function:queryResFeeToolList", "资源费：查询某一个资源费包含工具失败，发生未知错误", e);

            throw new HsException(BSRespCode.BS_RES_FEE_DB_ERROR, "查询某一个资源费包含工具失败，发生未知错误");
        }
        return list;
    }

    /**
     * 查询 已启用的资源费方案
     * 
     * @param toCustType
     *            被申报企业客户类型 非null
     * @param toBuyResRange
     *            被申报企业购买资源段 非null
     * @return ResFee
     * @throws HsException
     */
    @Override
    public ResFee getEnableResFee(Integer toCustType, Integer toBuyResRange) throws HsException {
        // 校验客户类型
        HsAssert.isTrue(CustType.checkType(toCustType), RespCode.PARAM_ERROR, "企业客户类型[toCustType]错误");
        // 校验资源段
        if (toBuyResRange != null && (HsResNoUtils.isTrustResNo(toCustType) || HsResNoUtils.isMemberResNo(toCustType)))
        {
            HsAssert.isTrue(ResType.checkResType(toCustType, toBuyResRange), RespCode.PARAM_ERROR,
                    "企业购买的资源段参数[toBuyResRange]类型错误");
        }

        ResFee resFee;// 创建对象
        try
        {
            resFee = resFeeMapper.getEnableResFee(toCustType, toBuyResRange);
        }
        catch (Exception e)
        {
            // 写入系统日志
            SystemLog.error(bsConfig.getSysName(), "function:" + "getEnableResFee", "资源费：查询 已启用的资源费方案失败，发生未知错误", e);

            throw new HsException(BSRespCode.BS_RES_FEE_DB_ERROR, "临帐:查询 已启用的资源费方案失败--发生未知错误");
        }
        return resFee;
    }

}
