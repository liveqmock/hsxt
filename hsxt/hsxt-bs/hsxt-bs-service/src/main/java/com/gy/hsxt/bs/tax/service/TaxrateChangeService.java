/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.tax.service;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.gy.hsi.lc.client.log4j.BizLog;
import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.bs.bean.base.OptHisInfo;
import com.gy.hsxt.bs.bean.base.Query;
import com.gy.hsxt.bs.bean.invoice.InvoicePool;
import com.gy.hsxt.bs.bean.tax.TaxrateChange;
import com.gy.hsxt.bs.bean.tax.TaxrateChangeQuery;
import com.gy.hsxt.bs.common.PageContext;
import com.gy.hsxt.bs.common.enumtype.BSRespCode;
import com.gy.hsxt.bs.common.enumtype.OptTable;
import com.gy.hsxt.bs.common.enumtype.invoice.BizType;
import com.gy.hsxt.bs.common.enumtype.tax.TaxpayerType;
import com.gy.hsxt.bs.common.enumtype.tax.TaxrateStatus;
import com.gy.hsxt.bs.common.interfaces.ICommonService;
import com.gy.hsxt.bs.common.interfaces.IOperationService;
import com.gy.hsxt.bs.disconf.BsConfig;
import com.gy.hsxt.bs.invoice.enumtype.PoolFlag;
import com.gy.hsxt.bs.invoice.interfaces.IInvoicePoolService;
import com.gy.hsxt.bs.task.bean.Task;
import com.gy.hsxt.bs.task.interfaces.ITaskService;
import com.gy.hsxt.bs.tax.interfaces.ITaxrateChangeService;
import com.gy.hsxt.bs.tax.mapper.TaxrateChangeMapper;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.constant.BizGroup;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.constant.TaskStatus;
import com.gy.hsxt.common.constant.TaskType;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.guid.GuidAgent;
import com.gy.hsxt.common.utils.BigDecimalUtils;
import com.gy.hsxt.common.utils.HsAssert;
import com.gy.hsxt.common.utils.HsResNoUtils;
import com.gy.hsxt.gpf.fss.utils.FssDateUtil;
import com.gy.hsxt.uc.bs.api.ent.IUCBsEntService;
import com.gy.hsxt.uc.bs.bean.ent.BsEntTaxRate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 税率调整申请实现
 *
 * @Package : com.gy.hsxt.bs.tax.service
 * @ClassName : TaxrateChangeService
 * @Description : 税率调整申请实现
 * @Author : chenm
 * @Date : 2015/12/29 18:51
 * @Version V3.0.0.0
 */
@Service
public class TaxrateChangeService implements ITaxrateChangeService {

    /**
     * 业务服务私有配置参数
     **/
    @Resource
    private BsConfig bsConfig;

    /**
     * 税率调整申请Mapper接口
     */
    @Resource
    private TaxrateChangeMapper taxrateChangeMapper;

    @Resource
    private IOperationService operationService;

    /**
     * 发票池数据业务接口
     */
    @Resource
    private IInvoicePoolService invoicePoolService;

    /**
     * 用户中心接口
     */
    @Resource
    private IUCBsEntService bsEntService;

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
     * 保存实体
     *
     * @param taxrateChange 实体
     * @return string
     * @throws HsException
     */
    @Override
    @Transactional
    public String saveBean(TaxrateChange taxrateChange) throws HsException {
        // 写入系统日志
        SystemLog.debug(bsConfig.getSysName(), "function:saveBean", "创建企业税率调整申请参数[taxrateChange]:" + taxrateChange);
        // 校验参数
        HsAssert.notNull(taxrateChange, RespCode.PARAM_ERROR, "税率调整申请[taxrateChange]为null");
        HsAssert.hasText(taxrateChange.getCustId(), RespCode.PARAM_ERROR, "企业客户号[custId]为空");
        HsAssert.hasText(taxrateChange.getResNo(), RespCode.PARAM_ERROR, "企业互生号[resNo]为空");
        HsAssert.hasText(taxrateChange.getCustName(), RespCode.PARAM_ERROR, "企业名称[custName]为空");
        HsAssert.hasText(taxrateChange.getLinkman(), RespCode.PARAM_ERROR, "企业联系人[linkman]为空");
        HsAssert.hasText(taxrateChange.getTelephone(), RespCode.PARAM_ERROR, "联系人电话[telephone]为空");
        HsAssert.hasText(taxrateChange.getCustName(), RespCode.PARAM_ERROR, "申请税率[applyTaxrate]为空");
        HsAssert.hasText(taxrateChange.getProveMaterialFile(), RespCode.PARAM_ERROR, "证明材料[proveMaterialFile]为空");
        HsAssert.hasText(taxrateChange.getApplyReason(), RespCode.PARAM_ERROR, "申请说明[applyReason]为空");
        HsAssert.hasText(taxrateChange.getCreatedBy(), RespCode.PARAM_ERROR, "申请操作者客户号[createdby]为空");
//        HsAssert.hasText(taxrateChange.getCreatedName(), RespCode.PARAM_ERROR, "申请操作者名称[createdName]为空");
        HsAssert.isTrue(TaxpayerType.check(taxrateChange.getTaxpayerType()), RespCode.PARAM_ERROR,
                "纳税人类型[taxpayerType]错误");

        try {
            // 判断某企业是否存在待审核的申请记录
            String pendingApplyId = taxrateChangeMapper.selectPendingBeanByCustId(taxrateChange.getCustId());

            HsAssert.isTrue(StringUtils.isEmpty(pendingApplyId), BSRespCode.BS_TAXRATE_CHANGE_PENDING_EXIST,
                    "企业税率调整申请已存在，请不要重复申请");

            //查询最后通过审核的记录
            TaxrateChange lastChange = taxrateChangeMapper.selectLastBeanByCustId(taxrateChange.getCustId(), TaxrateStatus.REVIEW.ordinal());
            // 从用户中心查询当前税率
//            String taxrate = bsEntService.findTaxRate(taxrateChange.getCustId());
//            taxrate = StringUtils.isBlank(taxrate) ? "0.00" : taxrate;// 判断是否为空，默认为0
            String taxrate = lastChange == null ? "0.00" : lastChange.getApplyTaxrate();
            taxrateChange.setCurrTaxrate(taxrate);// 设置当前税率

            taxrateChange.setApplyId(GuidAgent.getStringGuid(BizGroup.BS + bsConfig.getAppNode()));// 生成guid
            // 设置客户类型
            taxrateChange.setCustType(HsResNoUtils.getCustTypeByHsResNo(taxrateChange.getResNo()));

            int count = taxrateChangeMapper.insertBean(taxrateChange);

            // 创建操作日志
            BizLog.biz(bsConfig.getSysName(), "function:saveBean", "params==>" + taxrateChange, "企业税率调整:创建 企业税率调整申请成功");

            // 保存申请后，派送工单
            if (count == 1) {
                String platCustId = commonService.getAreaPlatCustId();
                HsAssert.hasText(platCustId, BSRespCode.BS_QUERY_AREA_PLAT_CUST_ID_ERROR, "查询地区平台客户号失败");
                Task task = new Task(taxrateChange.getApplyId(), TaskType.TASK_TYPE171.getCode(), platCustId,
                        taxrateChange.getResNo(), taxrateChange.getCustName());
                taskService.saveTask(task);
            }
            return taxrateChange.getApplyId();
        } catch (HsException hse) {
            throw hse;
        } catch (Exception e) {
            // 写入系统日志
            SystemLog.error(bsConfig.getSysName(), "function:saveBean", "企业税率调整:创建企业税率调整申请失败,参数[taxrateChange]:"
                    + taxrateChange, e);
            throw new HsException(BSRespCode.BS_TAXRATE_CHANGE_DB_ERROR, "企业税率调整:创建企业税率调整申请失败,原因:" + e.getMessage());
        }
    }

    /**
     * 判断企业是否存在审批中的税率申请
     * true - 存在 false - 不存在
     *
     * @param entCustId 企业客户号
     * @return {@code boolean}
     * @throws HsException
     */
    @Override
    public boolean checkTaxrateChangePending(String entCustId) throws HsException {
        SystemLog.debug(bsConfig.getSysName(), "function:checkTaxrateChangePending", "判断企业是否存在审批中的税率申请参数[entCustId]:" + entCustId);
        HsAssert.hasText(entCustId, BSRespCode.BS_PARAMS_EMPTY, "企业客户号[entCustId]为空");

        try {
            // 判断某企业是否存在待审核的申请记录
            String pendingApplyId = taxrateChangeMapper.selectPendingBeanByCustId(entCustId);

            return StringUtils.isNotEmpty(pendingApplyId);
        } catch (Exception e) {
            // 写入系统日志
            SystemLog.error(bsConfig.getSysName(), "function:checkTaxrateChangePending", "判断企业是否存在审批中的税率申请失败,参数[entCustId]:" + entCustId, e);
            throw new HsException(BSRespCode.BS_TAXRATE_CHANGE_DB_ERROR, "判断企业是否存在审批中的税率申请失败,原因:" + e.getMessage());
        }
    }

    /**
     * 更新实体
     *
     * @param taxrateChange 实体
     * @return int
     * @throws HsException
     */
    @Override
    @Transactional
    public int modifyBean(TaxrateChange taxrateChange) throws HsException {
        // --------------------企业税率调整申请记录-------------------------
        // 写入系统日志
        SystemLog.debug(bsConfig.getSysName(), "function:modifyBean", "审核/复核企业税率调整申请参数[taxrateChange]:" + taxrateChange);
        // 实体参数为null时抛出异常
        HsAssert.notNull(taxrateChange, RespCode.PARAM_ERROR, "企业税率调整申请记录[taxrateChange]为null");
        HsAssert.hasText(taxrateChange.getApplyId(), RespCode.PARAM_ERROR, "企业税率调整申请ID[applyId]为空");
        HsAssert.hasText(taxrateChange.getCustId(), RespCode.PARAM_ERROR, "企业客户号[custId]为空");
        HsAssert.hasText(taxrateChange.getUpdatedBy(), RespCode.PARAM_ERROR, "操作者客户号[updatedBy]为空");
        HsAssert.hasText(taxrateChange.getUpdatedName(), RespCode.PARAM_ERROR, "操作者名称[updatedName]为空");

        // 审批状态 1：地区平台初审通过 2：地区平台复核通过 3：初审驳回 4-复核驳回
        HsAssert.isTrue(TaxrateStatus.check(taxrateChange.getStatus()), RespCode.PARAM_ERROR, "税率调整申请状态[status]类型错误");

        try {
            // 地区平台复核通过
            if (TaxrateStatus.REVIEW.ordinal() == taxrateChange.getStatus()) {
                TaxrateChange disable = new TaxrateChange();
                BeanUtils.copyProperties(taxrateChange, disable);
                // 停用日期 这个月的最后一天
                disable.setDisableDate(FssDateUtil.obtainMonthLastDay(FssDateUtil.THIS_MONTH));
                // 停用之前的 税率调整申请
                taxrateChangeMapper.disableTaxrateChange(disable);

                // 启用日期 下个月的第一天
                taxrateChange.setEnableDate(FssDateUtil.obtainMonthFirstDay(FssDateUtil.NEXT_MONTH));
            }
            // 更新申请记录
            int count = taxrateChangeMapper.updateBean(taxrateChange);

            // 创建操作日志
            BizLog.biz(bsConfig.getSysName(), "function:modifyBean", "params==>" + taxrateChange, "审核/复核企业税率调整申请更新成功");

            // --------------------企业税率调整申请记录 创建操作历史-------------------------
            if (count == 1) {
                OptHisInfo taxrateChangeHis = new OptHisInfo();
                taxrateChangeHis.setOptHisId(GuidAgent.getStringGuid(BizGroup.BS + bsConfig.getAppNode()));// 生成guid
                taxrateChangeHis.setApplyId(taxrateChange.getApplyId());// 申请ID
                taxrateChangeHis.setOptId(taxrateChange.getUpdatedBy());// 操作者
                taxrateChangeHis.setOptName(taxrateChange.getUpdatedName());// 操作者名称
                taxrateChangeHis.setBizAction(taxrateChange.getStatus());// 业务阶段，业务无要求，与审批状态设置成一样
                taxrateChangeHis.setBizStatus(taxrateChange.getStatus());// 审批状态
                taxrateChangeHis.setOptRemark(taxrateChange.getApplyReason());// 操作备注
                // 记录操作历史
                operationService.createOptHis(OptTable.T_BS_ENT_TAXRATE_CHANGE_APPR.getCode(), taxrateChangeHis);

                // 创建操作日志
                BizLog.biz(bsConfig.getSysName(), "function:modifyBean", "params==>" + taxrateChangeHis,
                        "保存税率调整审核/复核操作记录成功");
                // 审批操作完成时，更新工单状态
                String taskId = taskService.getSrcTask(taxrateChange.getApplyId(), taxrateChange.getUpdatedBy());
                HsAssert.hasText(taskId, BSRespCode.BS_TASK_STATUS_NOT_DEALLING, "任务状态不是办理中");
                taskService.updateTaskStatus(taskId, TaskStatus.COMPLETED.getCode());
                // 初审通过之后，添加待复核的工单
                if (TaxrateStatus.INITIAL.ordinal() == taxrateChange.getStatus()) {
                    String platCustId = commonService.getAreaPlatCustId();
                    HsAssert.hasText(platCustId, BSRespCode.BS_QUERY_AREA_PLAT_CUST_ID_ERROR, "查询地区平台客户号失败");
                    TaxrateChange db = taxrateChangeMapper.selectBeanById(taxrateChange.getApplyId());
                    Task task = new Task(taxrateChange.getApplyId(), TaskType.TASK_TYPE171.getCode(), platCustId, db
                            .getResNo(), db.getCustName());
                    taskService.saveTask(task);
                }
                // 平台复核通过后，停止企业向平台开发票的数据统计
                if (TaxrateStatus.REVIEW.ordinal() == taxrateChange.getStatus()) {
                    InvoicePool invoicePoolInDB = invoicePoolService.queryBeanByIdAndType(taxrateChange.getCustId(),
                            BizType.CP_ALL_INVOICE.getBizCode());
                    // 判断是否需要更新
                    if (invoicePoolInDB != null && PoolFlag.CONTINUE.ordinal() == invoicePoolInDB.getGoOn()) {
                        InvoicePool invoicePool = InvoicePool.bulid(taxrateChange.getCustId(), BizType.CP_ALL_INVOICE
                                .getBizCode());
                        invoicePool.setGoOn(PoolFlag.STOP.ordinal());// 设置停止统计标识
                        invoicePool.setStopDate(FssDateUtil.obtainMonthFirstDay(FssDateUtil.NEXT_MONTH));// 停止日期设置为税率生效日期
                        // 更新发票统计
                        invoicePoolService.modifyBean(invoicePool);
                    }
                    // 调用用户中心接口同步税率状态
                    TaxrateChange change = taxrateChangeMapper.selectBeanById(taxrateChange.getApplyId());
                    BsEntTaxRate taxRate = new BsEntTaxRate();
                    taxRate.setEntCustId(change.getCustId());
                    taxRate.setEntResNo(change.getResNo());
                    taxRate.setEntTaxRate(BigDecimalUtils.ceiling(change.getApplyTaxrate()));
                    taxRate.setActiveDate(Timestamp.valueOf(change.getEnableDate() + " 00:00:00"));
                    bsEntService.updateNoEffectEntTaxRateInfo(taxRate, taxrateChange.getUpdatedBy());

                }
            }

            return count;
        } catch (HsException hse) {
            // 写入系统日志
            SystemLog.error(bsConfig.getSysName(), "function:modifyBean", hse.getMessage(), hse);
            throw hse;
        } catch (Exception e) {
            // 写入系统日志
            SystemLog.error(bsConfig.getSysName(), "function:modifyBean", "审核/复核企业税率调整申请操作失败，参数[taxrateChange]:"
                    + taxrateChange, e);
            throw new HsException(BSRespCode.BS_TAXRATE_CHANGE_DB_ERROR, "审核/复核企业税率调整申请操作失败,原因:" + e.getMessage());
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
    public TaxrateChange queryBeanById(String id) throws HsException {
        // 写入系统日志
        SystemLog.debug(bsConfig.getSysName(), "function:queryBeanById", "查询某一条企业税率调整申请记录--申请ID[applyId]:" + id);
        // 申请ID为空时抛出异常
        HsAssert.hasText(id, RespCode.PARAM_ERROR, "申请ID[applyId]为空");
        try {
            TaxrateChange taxrateChange = taxrateChangeMapper.selectBeanById(id);
            if (taxrateChange != null) {
                List<OptHisInfo> optHis = operationService.queryOptHisAll(id, OptTable.T_BS_ENT_TAXRATE_CHANGE_APPR.getCode());
                if (CollectionUtils.isNotEmpty(optHis)) {
                    Collections.sort(optHis, new Comparator<OptHisInfo>() {
                        @Override
                        public int compare(OptHisInfo o1, OptHisInfo o2) {
                            return o1.getOptHisId().compareTo(o2.getOptHisId());
                        }
                    });
                }
                taxrateChange.setTaxrateChangeHises(optHis);
            }
            return taxrateChange;
        } catch (Exception e) {
            // 写入系统日志
            SystemLog.error(bsConfig.getSysName(), "function:queryBeanById", "查询某一条企业税率调整申请记录失败,参数[applyId]:" + id, e);

            throw new HsException(BSRespCode.BS_TAXRATE_CHANGE_DB_ERROR, "查询某一条企业税率调整申请记录失败,原因:" + e.getMessage());
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
    public List<TaxrateChange> queryListByQuery(Query query) throws HsException {
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
    public List<TaxrateChange> queryListForPage(Page page, Query query) throws HsException {
        // 写入系统日志
        SystemLog.debug(bsConfig.getSysName(), "function:queryListForPage", "分页查询企业税率调整申请列表参数[query]:" + query);
        // 分页对象为null抛出异常
        HsAssert.notNull(page, RespCode.PARAM_ERROR, "分页对象[page]为null");
        // 校验查询参数
        TaxrateChangeQuery taxrateChangeQuery = null;

        if (query != null) {
            HsAssert.isInstanceOf(TaxrateChangeQuery.class, query, RespCode.PARAM_ERROR,
                    "查询实体[query]不是TaxrateChangeQuery类型");
            taxrateChangeQuery = ((TaxrateChangeQuery) query);
            taxrateChangeQuery.checkDateFormat();// 校验日期
            taxrateChangeQuery.checkStatus();// 校验审批/复核状态
        }

        try {
            // 设置分页信息
            PageContext.setPage(page);

            // 获取当前页数据列表
            return taxrateChangeMapper.selectBeanListPage(taxrateChangeQuery);
        } catch (Exception e) {
            // 写入系统日志
            SystemLog.error(bsConfig.getSysName(), "function:queryListForPage", "分页查询企业税率调整申请列表失败，参数[query]:" + query,
                    e);
            throw new HsException(BSRespCode.BS_TAXRATE_CHANGE_DB_ERROR, "分页查询企业税率调整申请列表失败,原因:" + e.getMessage());
        }
    }

    /**
     * 分页查询企业税率调整审批/复核列表
     *
     * @param page               分页对象 必须 非null
     * @param taxrateChangeQuery 条件查询实体
     * @return 返回按条件分好页的数据列表
     * @throws HsException
     */
    @Override
    public List<TaxrateChange> queryTaskListPage(Page page, TaxrateChangeQuery taxrateChangeQuery) {
        // 写入系统日志
        SystemLog.debug(bsConfig.getSysName(), "function:queryListForPage", "分页查询企业税率调整审批/复核列表参数[taxrateChangeQuery]:"
                + taxrateChangeQuery);
        // 分页对象为null抛出异常
        HsAssert.notNull(page, RespCode.PARAM_ERROR, "分页对象[page]为null");
        HsAssert.notNull(taxrateChangeQuery, RespCode.PARAM_ERROR, "分页查询对象[taxrateChangeQuery]为null");
        HsAssert.hasText(taxrateChangeQuery.getOptCustId(), RespCode.PARAM_ERROR, "操作员客户号[optCustId]为空");
        // 校验查询参数
        taxrateChangeQuery.checkDateFormat();// 校验日期
        Integer status = taxrateChangeQuery.getStatus();// 税率状态
        boolean check = status != null
                && (TaxrateStatus.PENDING.ordinal() == status || TaxrateStatus.INITIAL.ordinal() == status);
        HsAssert.isTrue(check, RespCode.PARAM_ERROR, "税率申请状态[status]错误，只能填待审批[PENDING]或待复核[INITIAL]");// 校验审批/复核状态
        // 企业税率调整平台审批 TASK_TYPE171
        taxrateChangeQuery.setTaskType(TaskType.TASK_TYPE171.getCode());
        // 待理状态的工单
        taxrateChangeQuery.setTaskStatus(TaskStatus.DEALLING.getCode());
        try {
            // 设置分页信息
            PageContext.setPage(page);

            // 获取当前页数据列表
            return taxrateChangeMapper.selectTaskListPage(taxrateChangeQuery);
        } catch (Exception e) {
            // 写入系统日志
            SystemLog.error(bsConfig.getSysName(), "function:queryTaskListPage",
                    "分页查询企业税率调整审批/复核列表失败，参数[taxrateChangeQuery]:" + taxrateChangeQuery, e);
            throw new HsException(BSRespCode.BS_TAXRATE_CHANGE_DB_ERROR, "分页查询企业税率调整审批/复核列表失败,原因:" + e.getMessage());
        }
    }

    /**
     * 查询当前有效的税率
     *
     * @param custId 企业客户号
     * @return 税率
     * @throws HsException
     */
    @Override
    public String queryValidTaxrateByCustId(String custId) throws HsException {
        // 写入系统日志
        SystemLog.debug(bsConfig.getSysName(), "function:queryValidTaxrateByCustId", "查询当前有效的税率参数[custId]:" + custId);
        // 校验参数
        HsAssert.hasText(custId, RespCode.PARAM_ERROR, "企业客户号[custId]为空");
        try {
            String taxRate = bsEntService.findTaxRate(custId);
            return StringUtils.isBlank(taxRate) ? "0.00" : taxRate;
        } catch (Exception e) {
            // 写入系统日志
            SystemLog.error(bsConfig.getSysName(), "function:queryValidTaxrateByCustId", "查询当前有效的税率失败，参数[custId]:"
                    + custId, e);
            throw new HsException(BSRespCode.BS_TAXRATE_CHANGE_DB_ERROR, "查询当前有效的税率失败,原因:" + e.getMessage());
        }
    }

    /**
     * 条件查询最新通过的税率申请
     *
     * @param taxrateChangeQuery 查询实体
     * @return list
     * @throws HsException
     */
    @Override
    public List<TaxrateChange> queryEnableTaxrateList(TaxrateChangeQuery taxrateChangeQuery) throws HsException {
        // 写入系统日志
        SystemLog.debug(bsConfig.getSysName(), "function:queryListForPage", "条件查询最新通过的税率申请参数[taxrateChangeQuery]:"
                + taxrateChangeQuery);

        if (taxrateChangeQuery != null) {
            taxrateChangeQuery.checkDateFormat();
        }
        try {
            // 查询最新通过的税率申请
            return taxrateChangeMapper.selectEnableBeanListByQuery(taxrateChangeQuery);
        } catch (Exception e) {
            // 写入系统日志
            SystemLog.error(bsConfig.getSysName(), "function:queryEnableTaxrateList",
                    "条件查询最新通过的税率申请失败，参数[taxrateChangeQuery]:" + taxrateChangeQuery, e);
            throw new HsException(BSRespCode.BS_TAXRATE_CHANGE_DB_ERROR, "条件查询最新通过的税率申请失败,原因:" + e.getMessage());
        }
    }

    /**
     * 查询企业税率调整的最新申请
     *
     * @param custId 客户号
     * @return his
     * @throws HsException
     */
    @Override
    public TaxrateChange queryLastHisByCustId(String custId) throws HsException {
        // 写入系统日志
        SystemLog.debug(bsConfig.getSysName(), "function:queryLastHisByCustId", "查询企业税率调整的最新申请参数[custId]:" + custId);
        try {
            TaxrateChange taxrateChange = taxrateChangeMapper.selectLastBeanByCustId(custId, null);
            if (taxrateChange != null) {
                List<OptHisInfo> optHis = operationService.queryOptHisAll(taxrateChange.getApplyId(),
                        OptTable.T_BS_ENT_TAXRATE_CHANGE_APPR.getCode());
                taxrateChange.setTaxrateChangeHises(optHis);
            }
            return taxrateChange;
        } catch (Exception e) {
            // 写入系统日志
            SystemLog.error(bsConfig.getSysName(), "function:queryLastHisByCustId", "查询企业税率调整的最新申请失败，参数[custId]:"
                    + custId, e);
            throw new HsException(BSRespCode.BS_TAXRATE_CHANGE_DB_ERROR, "查询企业税率调整的最新申请失败,原因:" + e.getMessage());
        }
    }
}
