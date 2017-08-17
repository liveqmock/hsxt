/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.deduction.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gy.hsi.lc.client.log4j.BizLog;
import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.bs.bean.deduction.HsbDeduction;
import com.gy.hsxt.bs.bean.deduction.HsbDeductionQuery;
import com.gy.hsxt.bs.bean.order.AccountDetail;
import com.gy.hsxt.bs.common.PageContext;
import com.gy.hsxt.bs.common.enumtype.BSRespCode;
import com.gy.hsxt.bs.common.enumtype.deduction.DeductionStatus;
import com.gy.hsxt.bs.common.interfaces.ICommonService;
import com.gy.hsxt.bs.deduction.interfaces.IHsbDeductionService;
import com.gy.hsxt.bs.deduction.mapper.HsbDeductionMapper;
import com.gy.hsxt.bs.disconf.BsConfig;
import com.gy.hsxt.bs.order.interfaces.IAccountDetailService;
import com.gy.hsxt.bs.task.bean.Task;
import com.gy.hsxt.bs.task.interfaces.ITaskService;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.constant.AccountType;
import com.gy.hsxt.common.constant.BizGroup;
import com.gy.hsxt.common.constant.CurrencyCode;
import com.gy.hsxt.common.constant.CustType;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.constant.TaskStatus;
import com.gy.hsxt.common.constant.TaskType;
import com.gy.hsxt.common.constant.TransType;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.guid.GuidAgent;
import com.gy.hsxt.common.utils.DateUtil;
import com.gy.hsxt.common.utils.HsAssert;
import com.gy.hsxt.common.utils.HsResNoUtils;
import com.gy.hsxt.lcs.bean.LocalInfo;

/**
 * 互生币扣除业务实现
 * 
 * @Package : com.gy.hsxt.bs.deduction.service
 * @ClassName : HsbDeductionService
 * @Description : 互生币扣除业务实现
 * @Author : chenm
 * @Date : 2016/3/25 17:11
 * @Version V3.0.0.0
 */
@Service
public class HsbDeductionService implements IHsbDeductionService {

    /**
     * 业务服务私有配置参数
     **/
    @Resource
    private BsConfig bsConfig;

    /**
     * 互生币扣除Mapper
     */
    @Resource
    private HsbDeductionMapper hsbDeductionMapper;

    /**
     * 工单业务接口
     */
    @Resource
    private ITaskService taskService;

    /**
     * 公共数据配置
     */
    @Resource
    private ICommonService commonService;

    /**
     * 注入记帐分解接口
     */
    @Resource
    private IAccountDetailService accountDetailService;

    /**
     * 互生币扣除申请
     * 
     * @param hsbDeduction
     *            扣除实体
     * @return 申请ID
     * @throws HsException
     */
    @Override
    @Transactional
    public String applyHsbDeduction(HsbDeduction hsbDeduction) throws HsException {
        // 写入系统日志
        SystemLog.debug(bsConfig.getSysName(), "function:applyHsbDeduction", "互生币扣除申请参数[hsbDeduction]:" + hsbDeduction);
        // 校验参数
        HsAssert.notNull(hsbDeduction, BSRespCode.BS_PARAMS_NULL, "互生币扣除申请[hsbDeduction]为null");
        HsAssert.hasText(hsbDeduction.getEntCustId(), BSRespCode.BS_PARAMS_EMPTY, "企业客户号[custId]为空");
        HsAssert.hasText(hsbDeduction.getEntResNo(), BSRespCode.BS_PARAMS_EMPTY, "企业互生号[resNo]为空");
        hsbDeduction.setCustType(HsResNoUtils.getCustTypeByHsResNo(hsbDeduction.getEntResNo()));// 获取企业类型
        HsAssert.hasText(hsbDeduction.getAmount(), BSRespCode.BS_PARAMS_EMPTY, "扣除金额[amount]为空");
        HsAssert.isTrue(NumberUtils.isNumber(hsbDeduction.getAmount()), BSRespCode.BS_PARAMS_TYPE_ERROR,
                "扣除金额[amount]不是数字类型");
        HsAssert.hasText(hsbDeduction.getApplyOper(), BSRespCode.BS_PARAMS_EMPTY, "申请者客户号[applyOper]为空");

        try
        {
            hsbDeduction.setApplyId(GuidAgent.getStringGuid(BizGroup.BS + bsConfig.getAppNode()));
            int count = hsbDeductionMapper.insertBean(hsbDeduction);
            if (count == 1)
            {
                String platCustId = commonService.getAreaPlatCustId();
                HsAssert.hasText(platCustId, BSRespCode.BS_QUERY_AREA_PLAT_CUST_ID_ERROR, "查询地区平台客户号失败");
                Task task = new Task(hsbDeduction.getApplyId(), TaskType.TASK_TYPE102.getCode(), platCustId,
                        hsbDeduction.getEntResNo(), hsbDeduction.getEntCustName());
                taskService.saveTask(task);
            }
            return hsbDeduction.getApplyId();
        }
        catch (HsException hse)
        {
            throw hse;
        }
        catch (Exception e)
        {
            // 写入系统日志
            SystemLog.error(bsConfig.getSysName(), "function:saveBean", "创建互生币扣除申请失败,参数[hsbDeduction]:" + hsbDeduction,
                    e);
            throw new HsException(BSRespCode.BS_HSB_DEDUCTION_DB_ERROR, "创建互生币扣除申请失败,原因:" + e.getMessage());
        }
    }

    /**
     * 条件分页查询互生币扣除申请列表
     * 
     * @param page
     *            分页对象
     * @param hsbDeductionQuery
     *            查询对象
     * @return 申请列表
     * @throws HsException
     */
    @Override
    public PageData<HsbDeduction> queryHsbDeductionListPage(Page page, HsbDeductionQuery hsbDeductionQuery)
            throws HsException {
        // 写入系统日志
        SystemLog.debug(bsConfig.getSysName(), "function:queryHsbDeductionListPage",
                "条件分页查询互生币扣除申请列表参数[hsbDeductionQuery]:" + hsbDeductionQuery);
        // 分页对象为null抛出异常
        HsAssert.notNull(page, BSRespCode.BS_PARAMS_NULL, "分页对象[page]为null");
        // 校验查询参数
        if (hsbDeductionQuery != null)
        {
            hsbDeductionQuery.checkDateFormat();// 校验日期
        }

        try
        {
            // 设置分页信息
            PageContext.setPage(page);

            // 获取当前页数据列表
            List<HsbDeduction> deductionList = hsbDeductionMapper.selectBeanListPage(hsbDeductionQuery);

            return PageData.bulid(page.getCount(), deductionList);
        }
        catch (Exception e)
        {
            // 写入系统日志
            SystemLog.error(bsConfig.getSysName(), "function:queryHsbDeductionListPage",
                    "条件分页查询互生币扣除申请列表失败，参数[hsbDeductionQuery]:" + hsbDeductionQuery, e);
            throw new HsException(BSRespCode.BS_HSB_DEDUCTION_DB_ERROR, "条件分页查询互生币扣除申请列表失败,原因:" + e.getMessage());
        }
    }

    /**
     * 条件分页查询互生币扣除申请的工单列表
     * 
     * @param page
     *            分页对象
     * @param hsbDeductionQuery
     *            查询对象
     * @return 申请列表
     * @throws HsException
     */
    @Override
    public PageData<HsbDeduction> queryTaskListPage(Page page, HsbDeductionQuery hsbDeductionQuery) throws HsException {
        // 写入系统日志
        SystemLog.debug(bsConfig.getSysName(), "function:queryTaskListPage", "条件分页查询互生币扣除申请的工单列表参数[hsbDeductionQuery]:"
                + hsbDeductionQuery);
        // 分页对象为null抛出异常
        HsAssert.notNull(page, BSRespCode.BS_PARAMS_NULL, "分页对象[page]为null");
        // 校验查询参数
        HsAssert.notNull(hsbDeductionQuery, BSRespCode.BS_PARAMS_NULL, "分页查询对象[hsbDeductionQuery]为null");
        HsAssert.hasText(hsbDeductionQuery.getOptCustId(), RespCode.PARAM_ERROR, "操作员客户号[optCustId]为空");
        hsbDeductionQuery.checkDateFormat();// 校验日期
        // 待复核状态 0
        hsbDeductionQuery.setStatus(DeductionStatus.WAIT_APPR.ordinal());

        // 互生币扣除平台审批 TASK_TYPE102
        hsbDeductionQuery.setTaskType(TaskType.TASK_TYPE102.getCode());
        // 待理状态的工单
        hsbDeductionQuery.setTaskStatus(TaskStatus.DEALLING.getCode());
        try
        {
            // 设置分页信息
            PageContext.setPage(page);

            // 获取当前页数据列表
            List<HsbDeduction> deductionList = hsbDeductionMapper.selectTaskListPage(hsbDeductionQuery);

            return PageData.bulid(page.getCount(), deductionList);
        }
        catch (Exception e)
        {
            // 写入系统日志
            SystemLog.error(bsConfig.getSysName(), "function:queryTaskListPage",
                    "条件分页查询互生币扣除申请的工单列表失败，参数[hsbDeductionQuery]:" + hsbDeductionQuery, e);
            throw new HsException(BSRespCode.BS_HSB_DEDUCTION_DB_ERROR, "条件分页查询互生币扣除申请的工单列表失败,原因:" + e.getMessage());
        }
    }

    /**
     * 查询互生币扣除申请详情
     * 
     * @param applyId
     *            申请ID
     * @return 详情
     * @throws HsException
     */
    @Override
    public HsbDeduction queryDetailById(String applyId) throws HsException {
        // 写入系统日志
        SystemLog.debug(bsConfig.getSysName(), "function:queryDetailById", "查询互生币扣除申请详情,参数[applyId]:" + applyId);
        HsAssert.hasText(applyId, BSRespCode.BS_PARAMS_EMPTY, "申请ID[applyId]为空");
        try
        {
            return hsbDeductionMapper.selectBeanById(applyId);
        }
        catch (Exception e)
        {
            // 写入系统日志
            SystemLog.error(bsConfig.getSysName(), "function:queryDetailById", "查询互生币扣除申请详情失败，参数[applyId]:" + applyId,
                    e);
            throw new HsException(BSRespCode.BS_HSB_DEDUCTION_DB_ERROR, "查询互生币扣除申请详情失败,原因:" + e.getMessage());
        }
    }

    /**
     * 复审互生币扣除申请
     * 
     * @param hsbDeduction
     *            扣除申请
     * @return 操作结果
     * @throws HsException
     */
    @Override
    @Transactional
    public boolean apprHsbDeduction(HsbDeduction hsbDeduction) throws HsException {
        // 写入系统日志
        SystemLog.debug(bsConfig.getSysName(), "function:apprHsbDeduction", "互生币扣除申请参数[hsbDeduction]:" + hsbDeduction);
        // 校验参数
        HsAssert.notNull(hsbDeduction, BSRespCode.BS_PARAMS_NULL, "互生币扣除申请[hsbDeduction]为null");
        HsAssert.hasText(hsbDeduction.getApplyId(), BSRespCode.BS_PARAMS_EMPTY, "申请ID[applyId]为空");
        HsAssert.hasText(hsbDeduction.getApprOper(), BSRespCode.BS_PARAMS_EMPTY, "复核者客户号[apprOper]为空");
        HsAssert.isTrue(DeductionStatus.checkStatus(hsbDeduction.getStatus()), BSRespCode.BS_PARAMS_TYPE_ERROR,
                "审核[status]类型错误");

        try
        {
            int count = hsbDeductionMapper.updateBean(hsbDeduction);
            // 审批操作完成时，更新工单状态
            String taskId = taskService.getSrcTask(hsbDeduction.getApplyId(), hsbDeduction.getApprOper());
            HsAssert.hasText(taskId, BSRespCode.BS_TASK_STATUS_NOT_DEALLING, "任务状态不是办理中");
            taskService.updateTaskStatus(taskId, TaskStatus.COMPLETED.getCode());
            // 互生币扣除
            if (hsbDeduction.getStatus() == DeductionStatus.APPR_PASS.ordinal())
            {
                HsbDeduction deduction = hsbDeductionMapper.selectBeanById(hsbDeduction.getApplyId());
                parseAndNoteAccountForHsbDeduction(deduction);
            }
            return count == 1;
        }
        catch (HsException hse)
        {
            throw hse;
        }
        catch (Exception e)
        {
            // 写入系统日志
            SystemLog.error(bsConfig.getSysName(), "function:apprHsbDeduction", "复审互生币扣除申请失败，参数[hsbDeduction]:"
                    + hsbDeduction, e);
            throw new HsException(BSRespCode.BS_HSB_DEDUCTION_DB_ERROR, "复审互生币扣除申请失败,原因:" + e.getMessage());
        }
    }

    /**
     * 平台互生币扣除记账
     * 
     * @param hsbDeduction
     *            实体
     */
    private void parseAndNoteAccountForHsbDeduction(HsbDeduction hsbDeduction) throws HsException {
        // 校验基础信息
        LocalInfo localInfo = commonService.getAreaPlatInfo();
        HsAssert.notNull(localInfo, RespCode.PARAM_ERROR, "本地平台信息[localInfo]为空");

        String platCustId = commonService.getAreaPlatCustId();
        HsAssert.hasText(platCustId, RespCode.PARAM_ERROR, "本平台客户号[areaPlatCustId]为空");

        List<AccountDetail> accountDetails = new ArrayList<>();
        // -------------------------平台年费收入数据拼装--------------------------
        /** 平台扣款 ACC_TYPE_POINT20490("20490") */

        AccountDetail platDetail = new AccountDetail();// 创建对象
        platDetail.setAccountNo(GuidAgent.getStringGuid(BizGroup.BS + bsConfig.getAppNode()));// 生成guid
        platDetail.setBizNo(hsbDeduction.getApplyId());// 交易流水号
        /**
         * 平台扣款 DEDUCT_HSB_FROM_CUST("S72000")
         */
        platDetail.setTransType(TransType.DEDUCT_HSB_FROM_CUST.getCode());// 交易类型
        platDetail.setCustId(platCustId);// 平台 客户号
        platDetail.setHsResNo(localInfo.getPlatResNo());// 平台互生号
        platDetail.setCustName(localInfo.getPlatNameCn());// 客户名称
        platDetail.setCustType(CustType.AREA_PLAT.getCode());// 客户类型
        platDetail.setAccType(AccountType.ACC_TYPE_POINT20490.getCode());// 账户类型编码
        platDetail.setAddAmount(hsbDeduction.getAmount());// 增向金额
        platDetail.setDecAmount("0");// 减向金额
        platDetail.setCurrencyCode(CurrencyCode.HSB.getCode());// 币种
        platDetail.setFiscalDate(DateUtil.getCurrentDateTime());// 会计日期
        platDetail.setRemark("平台互生币扣除");// 备注内容
        platDetail.setStatus(true);// 记账状态

        accountDetails.add(platDetail);

        // 互生币支付记企业的账
        // -------------------------企业数据拼装--------------------------
        /**
         * 流通币 ACC_TYPE_POINT20110("20110"),
         */
        AccountDetail companyDetail = new AccountDetail();// 创建对象
        companyDetail.setAccountNo(GuidAgent.getStringGuid(BizGroup.BS + bsConfig.getAppNode()));// 生成guid
        companyDetail.setBizNo(hsbDeduction.getApplyId());// 交易流水号
        companyDetail.setTransType(TransType.DEDUCT_HSB_FROM_CUST.getCode());// 交易类型
        companyDetail.setCustId(hsbDeduction.getEntCustId());// 客户号
        companyDetail.setHsResNo(hsbDeduction.getEntResNo());// 互生号
        companyDetail.setCustName(hsbDeduction.getEntCustName());// 客户名称
        companyDetail.setCustType(hsbDeduction.getCustType());// 客户类型
        companyDetail.setAccType(AccountType.ACC_TYPE_POINT20110.getCode());// 账户类型编码
                                                                            // 流通币
        companyDetail.setAddAmount("0");// 增向金额
        companyDetail.setDecAmount(hsbDeduction.getAmount());// 减向金额
        companyDetail.setCurrencyCode(CurrencyCode.HSB.getCode());// 币种
        companyDetail.setFiscalDate(DateUtil.getCurrentDateTime());// 会计日期
        companyDetail.setRemark("平台互生币扣除");// 备注内容
        companyDetail.setStatus(true);// 记账状态

        accountDetails.add(companyDetail);
        // 执行插入
        accountDetailService.saveGenActDetail(accountDetails, "平台互生币扣除", true);

        // 创建操作日志
        BizLog.biz(bsConfig.getSysName(), "function:parseAndNoteAccountForHsbDeduction", "params==>" + accountDetails,
                "平台互生币扣除分解记录成功");
    }
}
