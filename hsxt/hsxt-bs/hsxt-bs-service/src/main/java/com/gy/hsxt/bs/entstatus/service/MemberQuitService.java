/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.entstatus.service;

import static com.gy.hsxt.common.utils.StringUtils.isBlank;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.gy.hsec.external.api.EnterpriceService;
import com.gy.hsec.external.bean.EnterpriceInputParam;
import com.gy.hsec.external.bean.ReturnResult;
import com.gy.hsi.lc.client.log4j.BizLog;
import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.bs.annualfee.interfaces.IAnnualFeeInfoService;
import com.gy.hsxt.bs.api.entstatus.IBSMemberQuitService;
import com.gy.hsxt.bs.apply.mapper.DeclareMapper;
import com.gy.hsxt.bs.bean.apply.DebtOrderParam;
import com.gy.hsxt.bs.bean.apply.ResFeeDebtOrder;
import com.gy.hsxt.bs.bean.base.ApprParam;
import com.gy.hsxt.bs.bean.base.OptHisInfo;
import com.gy.hsxt.bs.bean.entstatus.MemberQuit;
import com.gy.hsxt.bs.bean.entstatus.MemberQuitApprParam;
import com.gy.hsxt.bs.bean.entstatus.MemberQuitBaseInfo;
import com.gy.hsxt.bs.bean.entstatus.MemberQuitQueryParam;
import com.gy.hsxt.bs.bean.entstatus.MemberQuitStatus;
import com.gy.hsxt.bs.common.PageContext;
import com.gy.hsxt.bs.common.enumtype.BSRespCode;
import com.gy.hsxt.bs.common.enumtype.OptTable;
import com.gy.hsxt.bs.common.enumtype.entstatus.MemberQuitBizAction;
import com.gy.hsxt.bs.common.enumtype.entstatus.MemberQuitKey;
import com.gy.hsxt.bs.common.enumtype.entstatus.QuitStatus;
import com.gy.hsxt.bs.common.enumtype.entstatus.QuitType;
import com.gy.hsxt.bs.common.interfaces.ICommonService;
import com.gy.hsxt.bs.common.interfaces.IOperationService;
import com.gy.hsxt.bs.disconf.BsConfig;
import com.gy.hsxt.bs.entstatus.enumtype.MemberQuitProgress;
import com.gy.hsxt.bs.entstatus.interfaces.ICancelAccountService;
import com.gy.hsxt.bs.entstatus.interfaces.IChangeInfoService;
import com.gy.hsxt.bs.entstatus.mapper.MemberQuitMapper;
import com.gy.hsxt.bs.order.interfaces.IOrderService;
import com.gy.hsxt.bs.task.bean.Task;
import com.gy.hsxt.bs.task.interfaces.ITaskService;
import com.gy.hsxt.bs.tool.interfaces.IInsideInvokeCall;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.constant.BizGroup;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.constant.TaskStatus;
import com.gy.hsxt.common.constant.TaskType;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.guid.GuidAgent;
import com.gy.hsxt.common.utils.HsAssert;
import com.gy.hsxt.uc.bs.api.ent.IUCBsEntService;
import com.gy.hsxt.uc.bs.bean.ent.BsEntMainInfo;
import com.gy.hsxt.uc.bs.bean.ent.BsEntStatusInfo;
import com.gy.hsxt.uc.bs.enumerate.BsEntBaseStatusEumn;

/**
 * 成员企业销户管理
 * 
 * @Package : com.gy.hsxt.bs.entstatus.service
 * @ClassName : MemberQuitService
 * @Description : 成员企业销户管理
 * @Author : xiaofl
 * @Date : 2015-11-17 上午9:23:11
 * @Version V1.0
 */
@Service
public class MemberQuitService implements IBSMemberQuitService {

    /**
     * 成员企业销户Mapper
     **/
    @Resource
    private MemberQuitMapper memberQuitMapper;
    
    @Resource
    private DeclareMapper declareMapper;

    /**
     * 重要信息变更Service
     **/
    @Resource
    private IChangeInfoService changeInfoService;

    /**
     * 操作Service
     **/
    @Resource
    private IOperationService operationService;

    /**
     * 业务服务系统私有配置参数
     **/
    @Resource
    private BsConfig bsConfig;

    /**
     * 订单Service
     **/
    @Resource
    private IOrderService orderService;

    /**
     * 年费Service
     **/
    @Resource
    private IAnnualFeeInfoService annualFeeInfoService;

    /**
     * 用户中心Service
     **/
    @Resource
    private IUCBsEntService bsEntService;

    /**
     * 销户Service
     **/
    @Resource
    private ICancelAccountService cancelAccount;

    @Resource
    private ITaskService taskService;

    @Resource
    private ICommonService commonService;
    
    @Autowired
    EnterpriceService enterpriceService;
    
    @Autowired
    private IInsideInvokeCall insideInvokeCall;

    /**
     * 申请注销
     * 
     * @param memberQuit
     *            成员企业注销申请 必填
     * @throws HsException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void createMemberQuit(MemberQuit memberQuit) throws HsException {
        BizLog.biz(this.getClass().getName(), "createMemberQuit", "input param:", null == memberQuit ? "NULL"
                : memberQuit.toString());
        //校验参数
        HsAssert.notNull(memberQuit, RespCode.PARAM_ERROR, "申请注销/报停成员企业[memberQuit]为null");
        HsAssert.notNull(memberQuit.getApplyType(), RespCode.PARAM_ERROR, "申请注销/报停成员企业[applyType]为null");
        HsAssert.hasText(memberQuit.getEntCustId(), RespCode.PARAM_ERROR, "申请注销/报停成员企业[entCustId]为空");
        HsAssert.hasText(memberQuit.getEntResNo(), RespCode.PARAM_ERROR, "申请注销/报停成员企业[entResNo]为空");
        try
        {
            // 是否有未完成的工具订单
            if (insideInvokeCall.queryNotFinishToolOrder(memberQuit.getEntCustId()))
            {
                throw new HsException(BSRespCode.BS_POINT_ACT_UNCOMPLETE_TOOL_ORDER, "存在未完成的工具订单");
            }
            
            //是否欠系统资源费
            DebtOrderParam param = new DebtOrderParam();
            param.setEntResNo(memberQuit.getEntResNo());
            List<ResFeeDebtOrder> list = declareMapper.queryResFeeDebtOrderListPage(param);
            if(list!=null && !list.isEmpty()){
                throw new HsException(BSRespCode.BS_DEBIT_RES_FEE_NO_RETURN, "该企业属于欠款开启系统并且没有缴清系统资源费");
            }
          
//          // 是否欠年费，成员企业不需要年费
//          if (annualFeeInfoService.isArrear(memberQuit.getEntCustId()))
//          {
//              throw new HsException(BSRespCode.BS_QUIT_ARREAR_ANNUAL_FEE, "该企业欠年费");
//          }
            
            // 是否存在正在审批的重要信息变更
            if (changeInfoService.isExistApplyingEntChange(memberQuit.getEntCustId()))
            {
                throw new HsException(BSRespCode.BS_QUIT_EXIST_APPLYING_CHANGE, "存在正在审批的重要信息变更");
            }
            
            BsEntMainInfo mainInfo = bsEntService.searchEntMainInfo(memberQuit.getEntCustId());
            if(mainInfo!=null){
                memberQuit.setEntResNo(mainInfo.getEntResNo());
                memberQuit.setEntCustName(mainInfo.getEntName());
                memberQuit.setEntAddress(mainInfo.getEntRegAddr());
                memberQuit.setLinkman(mainInfo.getContactPerson());
                memberQuit.setLinkmanMobile(mainInfo.getContactPhone());
            }
            
            
            
            String applyId = GuidAgent.getStringGuid(BizGroup.BS + bsConfig.getAppNode());
            memberQuit.setApplyId(applyId);
            String serResNo = memberQuit.getEntResNo().substring(0, 5) + "000000";// 该成员企业所属服务公司互生号
            // 调用用户中心，查询该成员企业所属服务公司信息
            BsEntMainInfo bsEntMainInfo = bsEntService.getMainInfoByResNo(serResNo);
            memberQuit.setSerEntResNo(serResNo);
            memberQuit.setSerEntCustId(bsEntMainInfo.getEntCustId());
            memberQuit.setSerEntCustName(bsEntMainInfo.getEntName());

            // 创建注销申请
            memberQuitMapper.createMemberQuit(memberQuit);
            // 成员企业销户申请提交后通知用户中心与互商
            applyMemberQuit(memberQuit);
            
            MemberQuitBizAction bizAction; // 业务阶段
            if (QuitType.FORCE_QUIT.getCode() == memberQuit.getApplyType())
            {
                bizAction = MemberQuitBizAction.SERVICE_SUBMIT; // 服务公司提交资料
            }
            else
            {
                bizAction = MemberQuitBizAction.MEMBER_ENT_SUBMIT; // 成员企业提交资料
            }
            // 记录操作历史
            operationService.createOptHis(OptTable.T_BS_COMPANY_STOP_APPR.getCode(), applyId, bizAction.getCode(),
                    QuitStatus.WAIT_TO_APPROVE.getCode(), memberQuit.getCreatedBy(), memberQuit.getCreatedBy(),
                    memberQuit.getEntCustName(), memberQuit.getApplyReason(), null, null);

        }
        catch (HsException hse)
        {
            throw hse;
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), "createMemberQuit", BSRespCode.BS_QUIT_SUBMIT_ERROR.getCode()
                    + ":申请成员企业注销失败[param=" + memberQuit + "]", e);
            throw new HsException(BSRespCode.BS_QUIT_SUBMIT_ERROR, "申请成员企业注销失败[param=" + memberQuit + "]" + e);
        }

    }

    /**
     * 服务公司查询成员企业注销申请
     * 
     * @param memberQuitQueryParam
     *            查询条件
     * @param page
     *            分页信息 必填
     * @return 返回成员企业注销列表，没有则返回null
     * @throws HsException
     */
    @Override
    public PageData<MemberQuitBaseInfo> serviceQueryMemberQuit(MemberQuitQueryParam memberQuitQueryParam, Page page)
            throws HsException {

        if (null == memberQuitQueryParam || isBlank(memberQuitQueryParam.getSerResNo()) || null == page)
        {
            throw new HsException(BSRespCode.BS_PARAMS_NULL, "参数不能为空");
        }

        PageData<MemberQuitBaseInfo> pageData = null;
        PageContext.setPage(page);
        List<MemberQuitBaseInfo> list = memberQuitMapper.queryMemberQuitListPage(memberQuitQueryParam);
        if (null != list && list.size() > 0)
        {
            pageData = new PageData<>();
            pageData.setResult(list);
            pageData.setCount(PageContext.getPage().getCount());
        }
        return pageData;
    }

    /**
     * 地区平台查询成员企业注销审批
     * 
     * @param memberQuitQueryParam
     *            查询条件
     * @param page
     *            分页信息 必填
     * @return 返回成员企业注销列表，没有则返回null
     * @throws HsException
     */
    @Override
    public PageData<MemberQuitBaseInfo> platQueryMemberQuit4Appr(MemberQuitQueryParam memberQuitQueryParam, Page page)
            throws HsException {
        if (null == memberQuitQueryParam || isBlank(memberQuitQueryParam.getOptCustId()) || null == page)
        {
            throw new HsException(BSRespCode.BS_PARAMS_NULL, "参数不能为空");
        }
        memberQuitQueryParam.setSerResNo(null);
        // 地区平台审批查询需要关联任务
        return this.queryMemberQuit(memberQuitQueryParam, page, true);
    }

    /**
     * 地区平台查询成员企业注销审批列表
     * 
     * @param memberQuitQueryParam
     *            查询条件
     * @param page
     *            分页信息 必填
     * @return 返回成员企业注销列表，没有则返回null
     * @throws HsException
     */
    @Override
    public PageData<MemberQuitBaseInfo> platQueryMemberQuit(MemberQuitQueryParam memberQuitQueryParam, Page page)
            throws HsException {

        if (null == memberQuitQueryParam || null == page)
        {
            throw new HsException(BSRespCode.BS_PARAMS_NULL, "参数不能为空");
        }
        memberQuitQueryParam.setSerResNo(null);
        // 地区平台普通查询不需要关联任务
        return this.queryMemberQuit(memberQuitQueryParam, page, false);
    }

    // 分页查询成员企业注销
    private PageData<MemberQuitBaseInfo> queryMemberQuit(MemberQuitQueryParam memberQuitQueryParam, Page page,
            boolean taskFlag) {

        PageData<MemberQuitBaseInfo> pageData = null;
        PageContext.setPage(page);
        List<MemberQuitBaseInfo> list;
        if (taskFlag)
        {
            list = memberQuitMapper.queryMemberQuit4ApprListPage(memberQuitQueryParam);
        }
        else
        {
            list = memberQuitMapper.queryMemberQuitListPage(memberQuitQueryParam);
        }
        if (null != list && list.size() > 0)
        {
            pageData = new PageData<>();
            pageData.setResult(list);
            pageData.setCount(PageContext.getPage().getCount());
        }
        return pageData;
    }

    /**
     * 查询注销申请详情
     * 
     * @param applyId
     *            申请编号 必填
     * @return 返回注销申请详情，没有则返回null. Key--Value: 
     *         MEMBER_QUIT--MemberQuit,
     *         SER_APPR_HIS--OptHisInfo,
     *         PLAT_APPR_HIS--OptHisInfo,
     *         PLAT_REVIEW_HIS--OptHisInfo
     */
    @Override
    public Map<String, Object> queryMemberQuitById(String applyId) {
        Map<String, Object> map = new HashMap<>();
        OptHisInfo serApprHis = null;// 服务公司及审批信息
        OptHisInfo platApprHis = null;// 地区平台审批信息
        OptHisInfo platReviewHis = null;// 地区平台复核信息

        MemberQuit memberQuit = memberQuitMapper.queryMemberQuitById(applyId);// 成员企业注销申请信息

        List<OptHisInfo> optHisList = operationService.queryOptHisAll(applyId, OptTable.T_BS_COMPANY_STOP_APPR
                .getCode());
        for (OptHisInfo optHisInfo : optHisList)
        {
            if (serApprHis == null
                    && (MemberQuitBizAction.SERVICE_PASS.getCode() == optHisInfo.getBizAction() || MemberQuitBizAction.SERVICE_REJECTED
                            .getCode() == optHisInfo.getBizAction()))
            {// 服务公司审批信息
                serApprHis = optHisInfo;
            }
            else if (platApprHis == null
                    && (MemberQuitBizAction.PLAT_APPR_PASS.getCode() == optHisInfo.getBizAction() || MemberQuitBizAction.PLAT_APPR_REJECTED
                            .getCode() == optHisInfo.getBizAction()))
            {// 地区平台审批信息
                platApprHis = optHisInfo;
            }
            else if (platReviewHis == null
                    && (MemberQuitBizAction.PLAT_REVIEW_PASS.getCode() == optHisInfo.getBizAction() || MemberQuitBizAction.PLAT_REVIEW_REJECTED
                            .getCode() == optHisInfo.getBizAction()))

            {// 地区平台复核信息
                platReviewHis = optHisInfo;
            }
        }

        map.put(MemberQuitKey.MEMBER_QUIT.getCode(), memberQuit);// 成员企业注销申请信息
        map.put(MemberQuitKey.SER_APPR_HIS.getCode(), serApprHis);// 服务公司审批信息
        map.put(MemberQuitKey.PLAT_APPR_HIS.getCode(), platApprHis);// 地区平台审批信息
        map.put(MemberQuitKey.PLAT_REVIEW_HIS.getCode(), platReviewHis);// 地区平台复核信息

        return map;
    }

    /**
     * 服务公司审批注销申请
     * 
     * @param apprParam
     *            审批信息
     * @throws HsException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void serviceApprMemberQuit(MemberQuitApprParam apprParam) throws HsException {
        BizLog.biz(this.getClass().getName(), "serviceApprMemberQuit", "input param:", null == apprParam ? "NULL"
                : apprParam.toString());
        if (null == apprParam || isBlank(apprParam.getApplyId()) || isBlank(apprParam.getOptCustId())
                || isBlank(apprParam.getOptName()) || isBlank(apprParam.getOptEntName())
                || null == apprParam.getIsPass())
        {
            throw new HsException(BSRespCode.BS_PARAMS_NULL, "参数不能为空:" + apprParam);
        }

        try
        {
            String applyId = apprParam.getApplyId();
            MemberQuitBizAction bizAction;
            int status;
            if (apprParam.getIsPass())
            {// 审批通过
                status = QuitStatus.SERVICE_APPROVED.getCode();// 服务公司审批通过
                bizAction = MemberQuitBizAction.SERVICE_PASS;
            }
            else
            {// 审批驳回
                status = QuitStatus.SERVICE_REJECTED.getCode();// 服务公司审批驳回
                bizAction = MemberQuitBizAction.SERVICE_REJECTED;
            }

            // 更新注销申请状态
            memberQuitMapper.updateQuitStatus(applyId, status, apprParam.getOptCustId(), apprParam.getReportFile(),
                    apprParam.getStatementFile());

            // 记录操作历史
            operationService.createOptHis(OptTable.T_BS_COMPANY_STOP_APPR.getCode(), applyId, bizAction.getCode(),
                    status, apprParam.getOptCustId(), apprParam.getOptName(), apprParam.getOptEntName(), apprParam
                            .getApprRemark(), null, null);

            if (apprParam.getIsPass())// 审批通过
            {
                // 生成审批任务
                Task task = new Task(applyId, TaskType.TASK_TYPE133.getCode(), commonService.getAreaPlatCustId());
                MemberQuit memberQuit = memberQuitMapper.queryMemberQuitById(applyId);
                task.setHsResNo(memberQuit.getEntResNo());
                task.setCustName(memberQuit.getEntCustName());
                taskService.saveTask(task);
            }
            else
            {// 审批驳回
                MemberQuit memberQuit = memberQuitMapper.queryMemberQuitById(applyId);
                //审批驳回时通知用户中心与互商处理
                rejectedMemberQuit(memberQuit,apprParam.getOptCustId());
            }
        }
        catch (HsException hse)
        {
            throw hse;
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), "serviceApprMemberQuit", BSRespCode.BS_QUIT_SERVICE_APPR_ERROR
                    .getCode()
                    + ":服务公司审批成员企业注销失败[param=" + apprParam + "]", e);
            throw new HsException(BSRespCode.BS_QUIT_SERVICE_APPR_ERROR, "服务公司审批成员企业注销失败[param=" + apprParam + "]" + e);
        }
    }

    /**
     * 地区平台初审注销申请
     * 
     * @param apprParam
     *            审批信息
     * @throws HsException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void platApprMemberQuit(ApprParam apprParam) throws HsException {
        BizLog.biz(this.getClass().getName(), "platApprMemberQuit", "input param:", null == apprParam ? "NULL"
                : apprParam.toString());
        if (null == apprParam || isBlank(apprParam.getApplyId()) || isBlank(apprParam.getOptCustId())
                || isBlank(apprParam.getOptName()) || isBlank(apprParam.getOptEntName())
                || null == apprParam.getIsPass())
        {
            throw new HsException(BSRespCode.BS_PARAMS_NULL, "参数不能为空");
        }

        try
        {
            // 查询任务ID，判断用户是否能审批该任务
            String taskId = taskService.getSrcTask(apprParam.getApplyId(), apprParam.getOptCustId());
            HsAssert.hasText(taskId, BSRespCode.BS_TASK_STATUS_NOT_DEALLING, "任务状态不是办理中");
            String applyId = apprParam.getApplyId();
            MemberQuitBizAction bizAction;
            int status;
            if (apprParam.getIsPass())
            {// 审批通过
                status = QuitStatus.PLAT_APPROVED.getCode();// 地区平台审批通过
                bizAction = MemberQuitBizAction.PLAT_APPR_PASS;
            }
            else
            {// 审批驳回
                status = QuitStatus.PLAT_REJECTED.getCode();// 地区平台审批驳回
                bizAction = MemberQuitBizAction.PLAT_APPR_REJECTED;
            }
            // 更新注销申请状态
            memberQuitMapper.updateQuitStatus(applyId, status, apprParam.getOptCustId(), null, null);

            // 记录操作历史
            operationService.createOptHis(OptTable.T_BS_COMPANY_STOP_APPR.getCode(), applyId, bizAction.getCode(),
                    status, apprParam.getOptCustId(), apprParam.getOptName(), apprParam.getOptEntName(), apprParam
                            .getApprRemark(), null, null);

            // 把当前任务更新为已完成
            taskService.updateTaskStatus(taskId, TaskStatus.COMPLETED.getCode());

            MemberQuit memberQuit = memberQuitMapper.queryMemberQuitById(applyId);
            if (apprParam.getIsPass())
            {// 通过
             // 生成审批任务
                Task task = new Task(applyId, TaskType.TASK_TYPE134.getCode(), commonService.getAreaPlatCustId());
                task.setHsResNo(memberQuit.getEntResNo());
                task.setCustName(memberQuit.getEntCustName());
                taskService.saveTask(task);
            }
            else
            {// 驳回
                //审批驳回时通知用户中心与互商处理
                rejectedMemberQuit(memberQuit,apprParam.getOptCustId());
            }
        }
        catch (HsException hse)
        {
            throw hse;
        }
        catch (Exception e)
        {
            SystemLog
                    .error(this.getClass().getName(), "platApprMemberQuit", "平台审批成员企业注销失败[param=" + apprParam + "]", e);
            throw new HsException(BSRespCode.BS_QUIT_PLAT_APPR_ERROR, "平台审批成员企业注销失败[param=" + apprParam + "]" + e);
        }
    }

    /**
     * 地区平台复核注销申请
     * 
     * @param apprParam
     *            审批信息
     * @throws HsException
     */
    @Override
    @Transactional
    public void platReviewApprMemberQuit(ApprParam apprParam) throws HsException {
        BizLog.biz(this.getClass().getName(), "platReviewApprMemberQuit", "input param:", apprParam + "");
        HsAssert.notNull(apprParam, BSRespCode.BS_PARAMS_NULL, "审核参数[apprParam]为null");
        if (isBlank(apprParam.getApplyId()) || isBlank(apprParam.getOptCustId()) || isBlank(apprParam.getOptName())
                || isBlank(apprParam.getOptEntName()) || null == apprParam.getIsPass())
        {
            throw new HsException(BSRespCode.BS_PARAMS_NULL, "参数不能为空");
        }

        try
        {
            // 查询任务ID，判断用户是否能审批该任务
            String taskId = taskService.getSrcTask(apprParam.getApplyId(), apprParam.getOptCustId());
            HsAssert.hasText(taskId, BSRespCode.BS_TASK_STATUS_NOT_DEALLING, "任务状态不是办理中");

            String applyId = apprParam.getApplyId();
            MemberQuitBizAction bizAction;
            int status;
            if (apprParam.getIsPass())
            {// 审批通过
                status = QuitStatus.PLAT_APPROVAL_REVIEWED.getCode();// 地区平台复核通过
                bizAction = MemberQuitBizAction.PLAT_REVIEW_PASS;
            }
            else
            {// 审批驳回
                status = QuitStatus.PLAT_REVIEW_REJECTED.getCode();// 地区平台复核驳回
                bizAction = MemberQuitBizAction.PLAT_REVIEW_REJECTED;
            }
            // 更新注销申请状态
            memberQuitMapper.updateQuitStatus(applyId, status, apprParam.getOptCustId(), null, null);

            // 记录操作历史
            operationService.createOptHis(OptTable.T_BS_COMPANY_STOP_APPR.getCode(), applyId, bizAction.getCode(),
                    status, apprParam.getOptCustId(), apprParam.getOptName(), apprParam.getOptEntName(), apprParam
                            .getApprRemark(), null, null);

            MemberQuit memberQuit = memberQuitMapper.queryMemberQuitById(applyId);
            if (apprParam.getIsPass())// 审批通过
            {
                // 更新销户进度为0：销户审批通过
                memberQuitMapper.updateProgress(applyId, MemberQuitProgress.APPR_PASS.getCode(), apprParam
                        .getOptCustId());// 0：销户审批通过
                // 审批通过，进入销户自从处理流程，转账、增值销户、UC销户、释放资源号等,UC销户必须在转账完成后
                cancelAccount.cancelAccount(apprParam.getApplyId(), apprParam);
                
                //销户审批通过后，通知互商
                notifyHsecMemberQuit(memberQuit.getEntResNo());
            }
            else
            {// 审批驳回
                //审批驳回时通知用户中心与互商处理
                rejectedMemberQuit(memberQuit,apprParam.getOptCustId());
            }
            // 把当前任务更新为已完成
            taskService.updateTaskStatus(taskId, TaskStatus.COMPLETED.getCode());
        }
        catch (HsException hse)
        {
            throw hse;
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), "platReviewApprMemberQuit", "平台复核成员企业注销失败[param=" + apprParam
                    + "]", e);
            throw new HsException(BSRespCode.BS_QUIT_PLAT_REVIEW_ERROR, "平台复核成员企业注销失败，原因:" + e.getMessage());
        }
    }

    /**
     * 根据企业客户号查询成员企业注销状态
     * 
     * @param entCustId
     *            企业客户号
     * @return 成员企业注销状态信息
     */
    @Override
    public MemberQuitStatus queryMemberQuitStatus(String entCustId) {
        return memberQuitMapper.queryMemberQuitStatus(entCustId);
    }
    
    /**
     * 查询成员企业销户办理状态信息
     * 
     * @param applyId
     *            申请编号
     * @param page
     *            分页信息
     * @return 办理状态信息列表
     * @throws HsException
     */
    @Override
    public PageData<OptHisInfo> queryMemberQuitHis(String applyId, Page page) throws HsException {
        if (isBlank(applyId) || null == page)
        {
            throw new HsException(BSRespCode.BS_PARAMS_NULL, "参数不能为空");
        }
        return operationService.queryOptHisListPage(applyId, OptTable.T_BS_COMPANY_STOP_APPR.getCode(), page);
    }
    
    
    /**
     * 成员企业销户申请被驳回时通知UC、互商进行后续处理
     * @param memberQuit 成员企业销户申请单对象
     * @param optCustId 操作员客户号
     */
    private void rejectedMemberQuit(MemberQuit memberQuit, String optCustId){
        // 通知用户中心更改客户状态为积分活动正常状态
        BsEntStatusInfo bsEntStatusInfo = new BsEntStatusInfo();
        bsEntStatusInfo.setEntCustId(memberQuit.getEntCustId());
        bsEntStatusInfo.setBaseStatus(memberQuit.getOldStatus());
        bsEntService.updateEntStatusInfo(bsEntStatusInfo, optCustId);
        
        //通知互商成员企业销户被驳回了
        EnterpriceInputParam param = new EnterpriceInputParam();
        param.setEnterpriceResourceNo(memberQuit.getEntResNo());
        try{
            ReturnResult  result = enterpriceService.refuseEnterpriceApplyStopPointActivity(param);
            if(200 == result.getRetCode()){
                SystemLog.debug(enterpriceService.getClass().getName(), "refuseEnterpriceApplyStopPointActivity", "成员企业销户审批驳回时通知互商成功。"+memberQuit);
            }else{
                SystemLog.error(enterpriceService.getClass().getName(), "refuseEnterpriceApplyStopPointActivity", "成员企业销户审批驳回时通知互商失败，[param="+memberQuit+"],互商返回错误消息："+result.getRetMsg(),null);
            }
        }catch(Exception e){
            SystemLog.error(enterpriceService.getClass().getName(), "refuseEnterpriceApplyStopPointActivity",
                    "成员企业销户审批驳回时通知互商调用接口失败[" + memberQuit.getEntResNo() + "]", e);
        }
    }
    
    /**
     * 通知互商成员企业已销户
     * @param entResNo 成员企业销户申请单对象
     */
    private void notifyHsecMemberQuit(String entResNo){
      //通知互商成员企业销户被通过了
        EnterpriceInputParam param = new EnterpriceInputParam();
        param.setEnterpriceResourceNo(entResNo);
        try{
            ReturnResult  result = enterpriceService.passEnterpriceApplyStopPointActivity(param);
            if(200 == result.getRetCode()){
                SystemLog.debug(enterpriceService.getClass().getName(), "passEnterpriceApplyStopPointActivity", "成员企业销户审批通过时通知互商成功。[param="+entResNo+"]");
            }else{
                SystemLog.error(enterpriceService.getClass().getName(), "passEnterpriceApplyStopPointActivity", "成员企业销户审批通过时通知互商失败，[param="+entResNo+"],互商返回错误消息："+result.getRetMsg(),null);
            }
        }catch(Exception e){
            SystemLog.error(enterpriceService.getClass().getName(), "passEnterpriceApplyStopPointActivity",
                    "成员企业销户审批通过时通知互商调用接口失败[" + entResNo + "]", e);
        }
    }
    
    /**
     * 成员企业销户申请提交时通知用户中心与互商
     * @param memberQuit 成员企业销户申请单
     */
    private void applyMemberQuit(MemberQuit memberQuit){
        // 通知用户中心更改客户状态为成员企业销户申请中
        // 调用用户中心更改企业状态为"注销申请中"
        BsEntStatusInfo bsEntStatusInfo = new BsEntStatusInfo();
        bsEntStatusInfo.setEntCustId(memberQuit.getEntCustId());
        bsEntStatusInfo.setBaseStatus(BsEntBaseStatusEumn.APPLY_CANCEL.getstatus());// 注销申请中
        bsEntService.updateEntStatusInfo(bsEntStatusInfo, memberQuit.getEntCustId());
        
        EnterpriceInputParam param = new EnterpriceInputParam();
        param.setEnterpriceResourceNo(memberQuit.getEntResNo());
        try{
            ReturnResult  result = enterpriceService.applyEnterpriceStopPointActivity(param);
            if(200 == result.getRetCode()){
                SystemLog.debug(enterpriceService.getClass().getName(), "applyEnterpriceStopPointActivity", "成员企业销户申请提交时通知互商成功。"+memberQuit);
            }else{
                SystemLog.error(enterpriceService.getClass().getName(), "applyEnterpriceStopPointActivity", "成员企业销户申请提交时通知互商失败，[param="+memberQuit+"],互商返回错误消息："+result.getRetMsg(),null);
            }
        }catch(Exception e){
            SystemLog.error(enterpriceService.getClass().getName(), "applyEnterpriceStopPointActivity",
                    "成员企业销户申请提交时通知互商调用接口失败[" + memberQuit.getEntResNo() + "]", e);
        }
    }
}
