/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.tempacct.service;

import com.gy.hsi.lc.client.log4j.BizLog;
import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.bs.bean.base.Query;
import com.gy.hsxt.bs.bean.tempacct.Debit;
import com.gy.hsxt.bs.bean.tempacct.TempAcctRefund;
import com.gy.hsxt.bs.common.enumtype.BSRespCode;
import com.gy.hsxt.bs.common.enumtype.tempacct.DebitStatus;
import com.gy.hsxt.bs.common.enumtype.tempacct.LinkStatus;
import com.gy.hsxt.bs.common.interfaces.ICommonService;
import com.gy.hsxt.bs.disconf.BsConfig;
import com.gy.hsxt.bs.task.bean.Task;
import com.gy.hsxt.bs.task.interfaces.ITaskService;
import com.gy.hsxt.bs.tempacct.interfaces.IDebitService;
import com.gy.hsxt.bs.tempacct.interfaces.ITempAcctRefundService;
import com.gy.hsxt.bs.tempacct.mapper.TempAcctRefundMapper;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.constant.BizGroup;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.constant.TaskStatus;
import com.gy.hsxt.common.constant.TaskType;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.guid.GuidAgent;
import com.gy.hsxt.common.utils.BigDecimalUtils;
import com.gy.hsxt.common.utils.HsAssert;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 临账退款业务实现
 *
 * @Package :com.gy.hsxt.bs.tempacct.service
 * @ClassName : TempAcctRefundService
 * @Description : 临账退款业务实现
 * @Author : chenm
 * @Date : 2015/12/21 16:07
 * @Version V3.0.0.0
 */
@Service
public class TempAcctRefundService implements ITempAcctRefundService {

    /**
     * 业务服务私有配置参数
     **/
    @Resource
    private BsConfig bsConfig;

    /**
     * 临账退款申请Mapper接口
     */
    @Resource
    private TempAcctRefundMapper tempAcctRefundMapper;

    /**
     * 临账业务接口
     */
    @Resource
    private IDebitService debitService;

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
     * 保存实体
     *
     * @param tempAcctRefund 实体
     * @return string
     * @throws HsException
     */
    @Override
    @Transactional
    public String saveBean(TempAcctRefund tempAcctRefund) throws HsException {
        // 写入系统日志
        SystemLog.debug(bsConfig.getSysName(), "function:saveBean", "临账退款申请单[tempAcctRefund]:" + tempAcctRefund);
        // 实体对象为null抛出异常
        HsAssert.notNull(tempAcctRefund, RespCode.PARAM_ERROR, "临账退款申请单[tempAcctRefund]为null");

        HsAssert.hasText(tempAcctRefund.getDebitId(), RespCode.PARAM_ERROR, "临账记录编号[debitId]为空");
        HsAssert.hasText(tempAcctRefund.getRefundAmount(), RespCode.PARAM_ERROR, "退款金额[refundAmount]为空");
        HsAssert.hasText(tempAcctRefund.getReqOperator(), RespCode.PARAM_ERROR, "申请人客户号[reqOperator]为空");
        HsAssert.hasText(tempAcctRefund.getReqOperatorName(), RespCode.PARAM_ERROR, "申请人名称[reqOperatorName]为空");
        // 查询临账记录
        Debit debit = debitService.queryBeanById(tempAcctRefund.getDebitId());
        HsAssert.notNull(debit, BSRespCode.BS_DEBIT_NOT_EXIST, "申请的临账记录[" + tempAcctRefund.getDebitId() + "]不存在");
        HsAssert.isTrue(BigDecimalUtils.compareTo(debit.getUnlinkAmount(), tempAcctRefund.getRefundAmount()) == 0,
                BSRespCode.BS_TEMP_ACCT_REFUND_NOT_ALL, "退款金额[refundAmount]跟临账未关联金额不相等");

        try {
            tempAcctRefund.setApplyId(GuidAgent.getStringGuid(BizGroup.BS + bsConfig.getAppNode()));// 生成GUID

            tempAcctRefundMapper.createTempAcctRefund(tempAcctRefund);

            // 创建操作日志
            BizLog.biz(bsConfig.getSysName(), "function:saveBean", "params==>" + tempAcctRefund, "创建临账退款申请单成功");

            // ---------------------退款申请成功后--修改临帐记录的状态------------------------

            // 修改临帐记录的状态为 待退款
            Debit param = new Debit();
            param.setDebitId(tempAcctRefund.getDebitId());// 临账编号
            param.setDebitStatus(DebitStatus.REFUNDING.ordinal());// 退款中状态
            param.setRefundAmount(tempAcctRefund.getRefundAmount());// 退款金额
            param.setUpdatedBy(tempAcctRefund.getReqOperator());// 操作人客户号
            param.setUpdatedName(tempAcctRefund.getReqOperatorName());// 操作人名称
            param.setRefundRecord(tempAcctRefund.getReqRemark());// 退款备注
            int count = debitService.modifyDebitStatus(param, false);

            // 创建操作日志
            BizLog.biz(bsConfig.getSysName(), "function:" + "createTempAcctRefund", "params==>" + param,
                    "修改临帐记录的状态为退款中成功");

            // 保存申请后，派送工单
            if (count == 1) {
                String platCustId = commonService.getAreaPlatCustId();
                HsAssert.hasText(platCustId, BSRespCode.BS_QUERY_AREA_PLAT_CUST_ID_ERROR, "查询地区平台客户号失败");
                // 由于页面查询的是临账记录列表 , 则工单关联的应该是临账编号
                Task task = new Task(debit.getDebitId(), TaskType.TASK_TYPE336.getCode(), platCustId,"",debit.getPayEntCustName());
                taskService.saveTask(task);
            }

            return tempAcctRefund.getApplyId();
        } catch (Exception e) {
            // 写入系统日志
            SystemLog.error(bsConfig.getSysName(), "function:createTempAcctRefund", "创建临账退款申请单失败，参数[tempAcctRefund]:"
                    + tempAcctRefund, e);
            throw new HsException(BSRespCode.BS_TEMP_ACCT_REFUND_DB_ERROR, "创建临账退款申请单失败,原因:" + e.getMessage());
        }
    }

    /**
     * 更新实体
     *
     * @param tempAcctRefund 实体
     * @return int
     * @throws HsException
     */
    @Override
    @Transactional
    public int modifyBean(TempAcctRefund tempAcctRefund) throws HsException {
        // 写入系统日志
        SystemLog.debug(bsConfig.getSysName(), "function:modifyBean", "复核临账退款申请单[tempAcctRefund]:" + tempAcctRefund);
        // 实体对象为null抛出异常
        HsAssert.notNull(tempAcctRefund, RespCode.PARAM_ERROR, "临账退款申请单[tempAcctRefund]为空");
        // 临账编号不能为空
        HsAssert.hasText(tempAcctRefund.getDebitId(), RespCode.PARAM_ERROR, "临账记录编号[debitId]为空");
        HsAssert.hasText(tempAcctRefund.getApprOperator(), RespCode.PARAM_ERROR, "复核操作员客户号[apprOperator]为空");
        HsAssert.hasText(tempAcctRefund.getApprOperatorName(), RespCode.PARAM_ERROR, "复核操作员名称[apprOperatorName]为空");
        // 临账退款审批状态校验
        HsAssert.isTrue(LinkStatus.checkStatus(tempAcctRefund.getStatus()), RespCode.PARAM_ERROR, "临账退款复核状态[status]错误");

        try {
            int count = tempAcctRefundMapper.updateBean(tempAcctRefund);

            // 创建操作日志
            BizLog.biz(bsConfig.getSysName(), "function:modifyBean", "params==>" + tempAcctRefund, "临账退款申请单复核修改状态成功");

            // ------------------------退款申请审批后--修改临帐记录的状态-------------------------
            // 1是通过 2是驳回
            // 修改临帐记录的状态
            Debit debit = new Debit();
            debit.setDebitId(tempAcctRefund.getDebitId());// 临账编号
            debit.setUpdatedBy(tempAcctRefund.getApprOperator());// 复核操作员客户号
            debit.setUpdatedName(tempAcctRefund.getApprOperatorName());// 复核操作员名称
            debit.setRefundAuditRecord(tempAcctRefund.getApprRemark());// 复核备注

            if (LinkStatus.PASS.ordinal() == tempAcctRefund.getStatus()) {
                // 通过则改为已退款状态
                debit.setDebitStatus(DebitStatus.REFUNDED.ordinal());
                // 未关联金额置零
                debit.setUnlinkAmount("0");
            } else {
                // 驳回则还原为未关联
                debit.setDebitStatus(DebitStatus.LINKABLE.ordinal());
                // 驳回则还原原来的状态 退款金额设置为0
                debit.setRefundAmount("0");
            }
            debitService.modifyDebitStatus(debit, false);

            // 创建操作日志
            BizLog.biz(bsConfig.getSysName(), "function:apprTempAcctRefund", "params==>" + debit,
                    "临账退款申请单复核修改临帐记录的状态成功");
            // 审批操作完成时，更新工单状态
            String taskId = taskService.getSrcTask(tempAcctRefund.getDebitId(), tempAcctRefund.getApprOperator());
            HsAssert.hasText(taskId, BSRespCode.BS_TASK_STATUS_NOT_DEALLING, "任务状态不是办理中");
            taskService.updateTaskStatus(taskId, TaskStatus.COMPLETED.getCode());
            return count;
        } catch (Exception e) {
            // 写入系统日志
            SystemLog.error(bsConfig.getSysName(), "function:apprTempAcctRefund", "临账退款申请单复核失败，参数[tempAcctRefund]:"
                    + tempAcctRefund, e);
            throw new HsException(BSRespCode.BS_TEMP_ACCT_REFUND_DB_ERROR, "临账退款申请单复核失败,原因:" + e.getMessage());
        }
    }

    /**
     * 根据ID查询实体
     *
     * @param id key
     * @return t
     * @throws HsException
     */
    @Override
    public TempAcctRefund queryBeanById(String id) throws HsException {
        return null;
    }

    /**
     * 查询临账退款申请信息
     *
     * @param debitId 临账ID
     * @return bean
     * @throws HsException
     */
    @Override
    public TempAcctRefund queryBeanByDebitId(String debitId) throws HsException {
        // 写入系统日志
        SystemLog.debug(bsConfig.getSysName(), "function:queryBeanByDebitId", "查询临账退款申请信息，参数[debitId]:" + debitId);
        HsAssert.hasText(debitId, RespCode.PARAM_ERROR, "临账记录编号[debitId]为空");
        try {
            return tempAcctRefundMapper.selectBeanByDebitId(debitId);
        } catch (Exception e) {
            // 写入系统日志
            SystemLog.error(bsConfig.getSysName(), "function:queryBeanByDebitId", "查询临账退款申请信息，参数[debitId]:" + debitId, e);
            throw new HsException(BSRespCode.BS_TEMP_ACCT_REFUND_DB_ERROR, "查询临账退款申请信息失败,原因:" + e.getMessage());
        }
    }

    /**
     * 查询实体列表
     *
     * @param query 查询实体
     * @return list
     * @throws HsException
     */
    @Override
    public List<TempAcctRefund> queryListByQuery(Query query) throws HsException {
        return null;
    }

    /**
     * 分页查询列表
     *
     * @param page  分页对象
     * @param query 查询实体
     * @return list
     * @throws HsException
     */
    @Override
    public List<TempAcctRefund> queryListForPage(Page page, Query query) throws HsException {
        return null;
    }
}
