/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.tempacct.service;

import com.gy.hsi.lc.client.log4j.BizLog;
import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.bs.bean.base.Query;
import com.gy.hsxt.bs.bean.tempacct.*;
import com.gy.hsxt.bs.common.PageContext;
import com.gy.hsxt.bs.common.enumtype.BSRespCode;
import com.gy.hsxt.bs.common.enumtype.tempacct.DebitStatus;
import com.gy.hsxt.bs.common.enumtype.tempacct.PayType;
import com.gy.hsxt.bs.common.interfaces.ICommonService;
import com.gy.hsxt.bs.disconf.BsConfig;
import com.gy.hsxt.bs.task.bean.Task;
import com.gy.hsxt.bs.task.interfaces.ITaskService;
import com.gy.hsxt.bs.tempacct.interfaces.IAccountInfoService;
import com.gy.hsxt.bs.tempacct.interfaces.IDebitService;
import com.gy.hsxt.bs.tempacct.interfaces.ITempAcctRefundService;
import com.gy.hsxt.bs.tempacct.mapper.DebitMapper;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.constant.BizGroup;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.constant.TaskStatus;
import com.gy.hsxt.common.constant.TaskType;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.guid.GuidAgent;
import com.gy.hsxt.common.utils.HsAssert;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 临账业务实现层
 *
 * @Package :com.gy.hsxt.bs.tempacct.service
 * @ClassName : DebitService
 * @Description : 包括创建/修改/查询临账记录，审核临账，临账统计，不动款与临账互转业务
 * @Author : chenm
 * @Date : 2015/12/21 10:28
 * @Version V3.0.0.0
 */
@Service
public class DebitService implements IDebitService {

    /**
     * 业务服务私有配置参数
     **/
    @Resource
    private BsConfig bsConfig;

    /**
     * 临账Mapper接口
     */
    @Resource
    private DebitMapper debitMapper;

    /**
     * 收款账户信息业务接口
     */
    @Resource
    private IAccountInfoService accountInfoService;

    /**
     * 临账退款业务接口
     */
    @Resource
    private ITempAcctRefundService tempAcctRefundService;

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
     * @param debit 实体
     * @return string
     * @throws HsException
     */
    @Override
    @Transactional
    public String saveBean(Debit debit) throws HsException {
        // 写入系统日志
        SystemLog.debug(bsConfig.getSysName(), "function:saveBean", "创建临账记录参数[debit]:" + debit);
        // 实体参数为null时抛出异常
        HsAssert.notNull(debit, RespCode.PARAM_ERROR, "临帐记录实体[debit]为null");

        HsAssert.hasText(debit.getAccountInfoId(), RespCode.PARAM_ERROR, "收款账户信息ID[accountInfoId]为空");
        HsAssert.hasText(debit.getAccountReceiveTime(), RespCode.PARAM_ERROR, "到账时间[accountReceiveTime]为空");
        HsAssert.hasText(debit.getPayTime(), RespCode.PARAM_ERROR, "付款时间[payTime]为空");
        HsAssert.hasText(debit.getPayAmount(), RespCode.PARAM_ERROR, "付款金额[payAmount]为空");
        HsAssert.hasText(debit.getPayEntCustName(), RespCode.PARAM_ERROR, "付款企业名称[payEntCustName]为空");
        // 付款方式 1：POS机刷卡 2：银行汇款 3：现金付款
        HsAssert.isTrue(PayType.checkType(debit.getPayType()), RespCode.PARAM_ERROR, "付款方式[payType]参数类型错误");
        HsAssert.hasText(debit.getCreatedBy(), RespCode.PARAM_ERROR, "创建者客户号[createdby]为空");
        HsAssert.hasText(debit.getCreatedName(), RespCode.PARAM_ERROR, "创建者名称[createdName]为空");
        //查询收款账户信息
        AccountInfo accountInfo = accountInfoService.queryBeanById(debit.getAccountInfoId());
        //设置收款账户信息
        debit.setBankBranchName(accountInfo.getBankName());
        debit.setReceiveAccountName(accountInfo.getReceiveAccountName());
        debit.setReceiveAccountNo(accountInfo.getReceiveAccountNo());
        try {

            // 生成guid
            debit.setDebitId(GuidAgent.getStringGuid(BizGroup.BS + bsConfig.getAppNode()));
            // 插入临账记录
            int count = debitMapper.insertBean(debit);
            // 创建操作日志
            BizLog.biz(bsConfig.getSysName(), "function:saveBean", "params==>" + debit, "创建临帐记录成功");

            //保存成功后添加工单
            if (count == 1) {
                String platCustId = commonService.getAreaPlatCustId();
                HsAssert.hasText(platCustId, BSRespCode.BS_QUERY_AREA_PLAT_CUST_ID_ERROR, "查询地区平台客户号失败");
                Task task = new Task(debit.getDebitId(), TaskType.TASK_TYPE334.getCode(), platCustId,"",debit.getPayEntCustName());
                taskService.saveTask(task);
            }

        } catch (Exception e) {
            // 写入系统日志
            SystemLog.error(bsConfig.getSysName(), "function:saveBean", "创建临帐记录失败，参数[debit]:" + debit, e);
            throw new HsException(BSRespCode.BS_DEBIT_DB_ERROR, "创建临帐记录失败，原因:" + e.getMessage());
        }
        return debit.getDebitId();
    }

    /**
     * 更新实体
     *
     * @param debit 实体
     * @return int
     * @throws HsException
     */
    @Override
    @Transactional
    public int modifyBean(Debit debit) throws HsException {
        // 写入系统日志
        SystemLog.debug(bsConfig.getSysName(), "function:modifyBean", "修改更新临账记录--实体[debit]:" + debit);
        // 实体参数为null时抛出异常
        HsAssert.notNull(debit, RespCode.PARAM_ERROR, "临账记录实体[debit]为null");

        HsAssert.hasText(debit.getPayTime(), RespCode.PARAM_ERROR, "付款时间[payTime]为空");
        HsAssert.hasText(debit.getPayAmount(), RespCode.PARAM_ERROR, "付款金额[payAmount]为空");
        HsAssert.hasText(debit.getPayEntCustName(), RespCode.PARAM_ERROR, "付款企业名称[payEntCustName]为空");
        // 付款方式 1：POS机刷卡 2：银行汇款 3：现金付款
        HsAssert.isTrue(PayType.checkType(debit.getPayType()), RespCode.PARAM_ERROR, "付款方式[payType]参数类型错误");
        HsAssert.hasText(debit.getAccountInfoId(), RespCode.PARAM_ERROR, "收款账户信息ID[accountInfoId]为空");
        HsAssert.hasText(debit.getAccountReceiveTime(), RespCode.PARAM_ERROR, "到账时间[accountReceiveTime]为空");
        HsAssert.hasText(debit.getUpdatedBy(), RespCode.PARAM_ERROR, "更新操作人[updatedby]为空");
        HsAssert.hasText(debit.getUpdatedName(), RespCode.PARAM_ERROR, "操作人名称[updatedName]为空");

        HsAssert.hasText(debit.getDebitId(), RespCode.PARAM_ERROR, "临账编号[debitId]为空");

        try {
            Debit debitIn = debitMapper.selectBeanById(debit.getDebitId());

            HsAssert.notNull(debitIn, BSRespCode.BS_DEBIT_NOT_EXIST, "临账编号[" + debit.getDebitId() + "]临账记录不存在");

            //审核驳回状态的临账，修改完成之后，状态改为录入待复核
            if (DebitStatus.REJECTED.ordinal() == debitIn.getDebitStatus() || DebitStatus.PENDING.ordinal() == debitIn.getDebitStatus()) {
                //查询收款账户信息
                AccountInfo accountInfo = accountInfoService.queryBeanById(debit.getAccountInfoId());
                //设置收款账户信息
                debit.setBankBranchName(accountInfo.getBankName());
                debit.setReceiveAccountName(accountInfo.getReceiveAccountName());
                debit.setReceiveAccountNo(accountInfo.getReceiveAccountNo());

                debit.setDebitStatus(DebitStatus.PENDING.ordinal());
                //待审核或驳回的临账如果修改了付款金额，相应也要修改未关联金额
                if (StringUtils.isNotBlank(debit.getPayAmount())) {
                    debit.setUnlinkAmount(debit.getPayAmount());
                }
            }else{
                //其他状态不许修改必填项 “付款银行名称、付款银行账号、拟用企业互生号、付款用途、备注”数据为空
                debit.setAccountInfoId(null);
                debit.setPayTime(null);
                debit.setPayAmount(null);
                debit.setPayType(null);
                debit.setPayEntCustName(null);
                debit.setAccountReceiveTime(null);
                debit.setPayBankName(null);
                debit.setPayBankNo(null);
                if (DebitStatus.LINKABLE.ordinal() != debitIn.getDebitStatus()) {
                    debit.setUseEntCustName(null);
                    debit.setPayUse(null);
                }
                debit.setDescription(null);
            }

            // 更新临账
            int count = debitMapper.updateBean(debit);
            // 创建操作日志
            BizLog.biz(bsConfig.getSysName(), "function:modifyBean", "params==>" + debit, "更新临账记录成功");
            //保存成功后添加工单
            if (DebitStatus.REJECTED.ordinal() == debitIn.getDebitStatus() && count == 1) {
                String platCustId = commonService.getAreaPlatCustId();
                HsAssert.hasText(platCustId, BSRespCode.BS_QUERY_AREA_PLAT_CUST_ID_ERROR, "查询地区平台客户号失败");
                Task task = new Task(debit.getDebitId(), TaskType.TASK_TYPE334.getCode(), platCustId,"",debit.getPayEntCustName());
                taskService.saveTask(task);
            }
            return count;
        } catch (HsException hse) {
            // 写入系统日志
            SystemLog.error(bsConfig.getSysName(), "function:modifyBean", hse.getMessage(), hse);
            throw hse;
        } catch (Exception e) {
            // 写入系统日志
            SystemLog.error(bsConfig.getSysName(), "function:modifyBean", "更新临账记录失败,参数[debit]:" + debit, e);
            throw new HsException(BSRespCode.BS_DEBIT_DB_ERROR, "更新临账记录失败,原因:" + e.getMessage());
        }
    }

    /**
     * 修改临账状态
     *
     * @param debit 实体
     * @param appr  是否审核
     * @return int
     * @throws HsException
     */
    @Override
    @Transactional
    public int modifyDebitStatus(Debit debit, boolean appr) throws HsException {
        // 写入系统日志
        SystemLog.debug(bsConfig.getSysName(), "function:modifyDebitStatus", "修改临账状态参数[debit]:" + debit);
        // 临账记录为空时抛出异常
        HsAssert.notNull(debit, RespCode.PARAM_ERROR, "临帐记录实体[debit]为空");
        // 临账记录编号为空时抛出异常
        HsAssert.hasText(debit.getDebitId(), RespCode.PARAM_ERROR, "临帐记录编号[debitId]为空");
        // 临帐状态校验
        HsAssert.isTrue(DebitStatus.checkStatus(debit.getDebitStatus()), RespCode.PARAM_ERROR, "临帐状态[debitStatus]类型错误");
        // 操作人为空
        HsAssert.hasText(debit.getUpdatedBy(), RespCode.PARAM_ERROR, "操作人客户号[updatedby]为空");
        HsAssert.hasText(debit.getUpdatedName(), RespCode.PARAM_ERROR, "操作人名称[updatedName]为空");

        try {
            if (appr) {
                debit.setAuditCustId(debit.getUpdatedBy());
                debit.setAuditCustName(debit.getUpdatedName());
            }
            // 修改临账状态
            int count = debitMapper.updateDebitStatus(debit);
            // 创建操作日志
            BizLog.biz(bsConfig.getSysName(), "function:modifyDebitStatus", "params==>" + debit, "修改临账状态成功");
            if (appr) {
                //审批操作完成时，更新工单状态
                String taskId = taskService.getSrcTask(debit.getDebitId(), debit.getUpdatedBy());
                HsAssert.hasText(taskId, BSRespCode.BS_TASK_STATUS_NOT_DEALLING, "任务状态不是办理中");
                taskService.updateTaskStatus(taskId, TaskStatus.COMPLETED.getCode());
            }
            return count;
        } catch (Exception e) {
            // 写入系统日志
            SystemLog.error(bsConfig.getSysName(), "function:modifyDebitStatus", "修改临账状态失败，参数[debit]:" + debit, e);
            throw new HsException(BSRespCode.BS_DEBIT_DB_ERROR, "修改临账状态失败,原因:" + e.getMessage());
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
    public Debit queryBeanById(String id) throws HsException {
        // 写入系统日志
        SystemLog.debug(bsConfig.getSysName(), "function:queryBeanById", "查询某一条临帐记录的详情参数[debitId]:" + id);

        // 临账记录编号为空时抛出异常
        HsAssert.hasText(id, RespCode.PARAM_ERROR, "临账记录编号[debitId]为空");

        try {
            Debit debit = debitMapper.selectBeanById(id);
            if (debit != null && debit.getDebitStatus() == DebitStatus.REFUNDED.ordinal()) {
                //查询退款申请信息
                TempAcctRefund tempAcctRefund = tempAcctRefundService.queryBeanByDebitId(id);
                debit.setTempAcctRefund(tempAcctRefund);
            }
            return debit;
        } catch (HsException hse) {
            SystemLog.error(bsConfig.getSysName(), "function:queryBeanById", hse.getMessage(), hse);
            throw hse;
        } catch (Exception e) {
            // 写入系统日志
            SystemLog.error(bsConfig.getSysName(), "function:queryBeanById", "查询某一条临帐记录的详情失败，参数[debitId]:" + id, e);
            throw new HsException(BSRespCode.BS_DEBIT_DB_ERROR, "查询某一条临帐记录的详情失败,原因:" + e.getMessage());
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
    public List<Debit> queryListByQuery(Query query) throws HsException {
        // 写入系统日志
        SystemLog.debug(bsConfig.getSysName(), "function:queryListByQuery", "根据条件查询临账记录列表参数[query]:" + query);

        // 查询实体不为空 则校验参数
        DebitQuery debitQuery = null;
        if (query != null) {
            HsAssert.isInstanceOf(DebitQuery.class, query, RespCode.PARAM_ERROR, "查询实体[query]不是DebitQuery类型");
            debitQuery = (DebitQuery) query;
            debitQuery.checkDebitStatus();
            debitQuery.checkDateFormat();
        }
        try {
            // 获取当前页数据列表
            return debitMapper.selectBeanListByQuery(debitQuery);
        } catch (Exception e) {
            // 写入系统日志
            SystemLog.error(bsConfig.getSysName(), "function:queryListByQuery", "根据条件查询临账记录列表失败,参数[query]:" + query, e);
            throw new HsException(BSRespCode.BS_DEBIT_DB_ERROR, "根据条件查询临账记录列表失败，原因:" + e.getMessage());
        }
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
    public List<Debit> queryListForPage(Page page, Query query) throws HsException {
        // 写入系统日志
        SystemLog.debug(bsConfig.getSysName(), "function:queryListForPage", "分页查询临帐记录列表参数[query]:" + query);

        // 分页对象为null抛出异常
        HsAssert.notNull(page, RespCode.PARAM_ERROR, "分页对象[page]为null");

        // 查询实体不为空 则校验参数
        DebitQuery debitQuery = null;
        if (query != null) {
            HsAssert.isInstanceOf(DebitQuery.class, query, RespCode.PARAM_ERROR, "查询实体[query]不是DebitQuery类型");
            debitQuery = (DebitQuery) query;
            debitQuery.checkDateFormat();
            debitQuery.checkDebitStatus();
        }

        try {
            // 设置分页信息
            PageContext.setPage(page);

            // 获取当前页数据列表
            return debitMapper.selectBeanListPage(debitQuery);
        } catch (Exception e) {
            // 写入系统日志
            SystemLog.error(bsConfig.getSysName(), "function:queryListForPage", "分页查询临帐记录列表失败,参数[query]:" + query, e);
            throw new HsException(BSRespCode.BS_DEBIT_DB_ERROR, "分页查询临帐记录列表失败，原因:" + e.getMessage());
        }
    }

    /**
     * 分页查询临账录入复核/退款复核列表
     *
     * @param page       分页对象
     * @param debitQuery 查询实体
     * @return list
     * @throws HsException
     */
    @Override
    public List<Debit> queryTaskListForPage(Page page, DebitQuery debitQuery) throws HsException {
        // 写入系统日志
        SystemLog.debug(bsConfig.getSysName(), "function:queryTaskListForPage", " 分页查询临账录入复核/退款复核列表参数[debitQuery]:" + debitQuery);

        // 分页对象为null抛出异常
        HsAssert.notNull(page, RespCode.PARAM_ERROR, "分页对象[page]为null");
        HsAssert.notNull(debitQuery, RespCode.PARAM_ERROR, "查询对象[debitQuery]为null");
        HsAssert.hasText(debitQuery.getOptCustId(), RespCode.PARAM_ERROR, "操作员客户号[optCustId]为空");
        //校验临账状态
        Integer status = debitQuery.getDebitStatus();
        boolean check = status != null && (status == DebitStatus.PENDING.ordinal() || status == DebitStatus.REFUNDING.ordinal());
        HsAssert.isTrue(check, RespCode.PARAM_ERROR, "临账状态[status]错误，只能填待审核[PENDING]或待退款[REFUNDING]");
        // 查询实体不为空 则校验参数
        debitQuery.checkDateFormat();
        /**
         * 临账记录平台复核 TASK_TYPE334("334"),
         * 临账记录退款复核 TASK_TYPE336("336")
         * */
        TaskType taskType = (status != null && DebitStatus.PENDING.ordinal() == status) ? TaskType.TASK_TYPE334 : TaskType.TASK_TYPE336;
        debitQuery.setTaskType(taskType.getCode());//工单类型
        debitQuery.setTaskStatus(TaskStatus.DEALLING.getCode());//办理中

        try {
            // 设置分页信息
            PageContext.setPage(page);

            // 获取当前页数据列表
            return debitMapper.selectTaskListPage(debitQuery);
        } catch (Exception e) {
            // 写入系统日志
            SystemLog.error(bsConfig.getSysName(), "function:queryTaskListForPage", " 分页查询临账录入复核/退款复核列表失败,参数[debitQuery]:" + debitQuery, e);
            throw new HsException(BSRespCode.BS_DEBIT_DB_ERROR, " 分页查询临账录入复核/退款复核列表失败，原因:" + e.getMessage());
        }
    }

    /**
     * 分页查询临账统计结果
     * <p/>
     * 根据收款账户名称分类统计
     *
     * @param page       分页对象
     * @param debitQuery 查询实体
     * @return list
     * @throws HsException
     */
    @Override
    public List<DebitSum> queryDebitSumListForPage(Page page, DebitQuery debitQuery) throws HsException {
        // 写入系统日志
        SystemLog.debug(bsConfig.getSysName(), "function:queryDebitSumListForPage", "分页查询临账统计结果参数[debitQuery]:" + debitQuery);
        // 分页对象为null抛出异常
        HsAssert.notNull(page, RespCode.PARAM_ERROR, "分页对象[page]为null");

        // 查询实体不为空 则校验参数
        if (debitQuery != null) {
            debitQuery.checkDateFormat();
            debitQuery.checkDebitStatus();
        }

        try {
            // 设置分页信息
            PageContext.setPage(page);

            // 获取当前页数据列表
            return debitMapper.selectDebitSumListPage(debitQuery);
        } catch (Exception e) {
            // 写入系统日志
            SystemLog.error(bsConfig.getSysName(), "function:queryDebitSumListPage", "分页查询临账统计结果失败,参数[debitQuery]:" + debitQuery, e);
            throw new HsException(BSRespCode.BS_DEBIT_DB_ERROR, "分页查询临账统计结果失败，原因:" + e.getMessage());
        }
    }

    /**
     * 查询临账统计详情
     *
     * @param receiveAccountName 收款账户名称
     * @param debitQuery 查询实体
     * @return list
     * @throws HsException
     */
    @Override
    public List<Debit> queryDebitSumDetail(String receiveAccountName,DebitQuery debitQuery) throws HsException {
        // 写入系统日志
        SystemLog.debug(bsConfig.getSysName(), "function:queryDebitSumDetail", "查询临账统计详情参数[receiveAccountName]:" + receiveAccountName);
        // 写入系统日志
        SystemLog.debug(bsConfig.getSysName(), "function:queryDebitSumDetail", "查询参数[debitQuery]:" + debitQuery);
        // 收款账户名称ID不能为空
        HsAssert.hasText(receiveAccountName, RespCode.PARAM_ERROR, "收款账户名称[receiveAccountName]为空");
        // 查询实体不为空 则校验参数
        if (debitQuery != null) {
            debitQuery.checkDateFormat();
            debitQuery.checkDebitStatus();
        }
        try {
            // 获取当前页数据列表
            return debitMapper.selectDebitSumDetail(receiveAccountName,debitQuery);
        } catch (Exception e) {
            // 写入系统日志
            SystemLog.error(bsConfig.getSysName(), "function:queryDebitSumDetail", "查询临账统计详情失败，参数[receiveAccountName]:" + receiveAccountName, e);
            throw new HsException(BSRespCode.BS_DEBIT_DB_ERROR, "查询临账统计详情失败,原因:" + e.getMessage());
        }
    }

    /**
     * 不动款与临账互转
     *
     * @param debit        临账实体
     * @param dblOptCustId 双签操作员
     * @throws HsException
     */
    @Override
    public void turnDebit(Debit debit, String dblOptCustId) throws HsException {
        // 写入系统日志
        SystemLog.debug(bsConfig.getSysName(), "function:turnDebit", "不动款与临账互转参数[debit]:" + debit + "双签操作员客户号[dblOptCustId]" + dblOptCustId);
        // 校验参数
        HsAssert.notNull(debit, RespCode.PARAM_ERROR, "临账记录[debit]为null");
        // 校验不动款与临账互转的临账状态
        boolean check = DebitStatus.FROZEN.ordinal() == debit.getDebitStatus() || DebitStatus.LINKABLE.ordinal() == debit.getDebitStatus();
        HsAssert.isTrue(check, RespCode.PARAM_ERROR, "不动款与临账互转的临账状态[debitStatus]类型错误");
        HsAssert.hasText(debit.getDebitId(), RespCode.PARAM_ERROR, "临账记录ID[debitId]为空");
        HsAssert.hasText(debit.getUpdatedBy(), RespCode.PARAM_ERROR, "操作者[updatedby]为空");
        HsAssert.hasText(debit.getUpdatedName(), RespCode.PARAM_ERROR, "操作人名称[updatedName]为空");
        HsAssert.hasText(dblOptCustId, RespCode.PARAM_ERROR, "双签操作员客户号[dblOptCustId]为空");

        try {
            // 更新临账状态为不动款
            debitMapper.updateDebitStatus(debit);

            // 创建操作日志
            BizLog.biz(bsConfig.getSysName(), "function:turnDebit", "params==>" + debit, "不动款与临账互转成功");

        } catch (Exception e) {
            // 写入系统日志
            SystemLog.error(bsConfig.getSysName(), "function:turnNotMovePayment", "不动款与临账互转失败,参数[debit]:" + debit, e);
            throw new HsException(BSRespCode.BS_DEBIT_DB_ERROR, "不动款与临账互转失败,原因:" + e.getMessage());
        }
    }

    /**
     * 不动款统计
     *
     * @return sum
     * @throws HsException
     */
    @Override
    public String frozenDebitSum() throws HsException {
        try {
            // 不动款统计
            return debitMapper.frozenDebitSum(DebitStatus.FROZEN.ordinal());
        } catch (Exception e) {
            // 写入系统日志
            SystemLog.error(bsConfig.getSysName(), "function:frozenDebitSum", "统计不动款的总金额失败", e);

            throw new HsException(BSRespCode.BS_DEBIT_DB_ERROR, "统计不动款的总金额失败,原因:" + e.getMessage());
        }
    }
}
