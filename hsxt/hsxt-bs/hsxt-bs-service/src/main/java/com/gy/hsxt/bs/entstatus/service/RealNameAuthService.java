/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.entstatus.service;

import static com.gy.hsxt.common.utils.StringUtils.isBlank;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.gy.hsi.lc.client.log4j.BizLog;
import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.bs.api.entstatus.IBSRealNameAuthService;
import com.gy.hsxt.bs.bean.base.ApprParam;
import com.gy.hsxt.bs.bean.base.OptHisInfo;
import com.gy.hsxt.bs.bean.entstatus.EntRealnameAuth;
import com.gy.hsxt.bs.bean.entstatus.EntRealnameBaseInfo;
import com.gy.hsxt.bs.bean.entstatus.PerRealnameAuth;
import com.gy.hsxt.bs.bean.entstatus.PerRealnameBaseInfo;
import com.gy.hsxt.bs.bean.entstatus.RealNameQueryParam;
import com.gy.hsxt.bs.bean.entstatus.SysBizRecord;
import com.gy.hsxt.bs.common.PageContext;
import com.gy.hsxt.bs.common.enumtype.BSRespCode;
import com.gy.hsxt.bs.common.enumtype.LegalCreType;
import com.gy.hsxt.bs.common.enumtype.OptTable;
import com.gy.hsxt.bs.common.enumtype.entstatus.ApprStatus;
import com.gy.hsxt.bs.common.enumtype.entstatus.RealNameAuthBizAction;
import com.gy.hsxt.bs.common.interfaces.ICommonService;
import com.gy.hsxt.bs.common.interfaces.IOperationService;
import com.gy.hsxt.bs.disconf.BsConfig;
import com.gy.hsxt.bs.entstatus.mapper.RealNameAuthMapper;
import com.gy.hsxt.bs.task.bean.Task;
import com.gy.hsxt.bs.task.interfaces.ITaskService;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.constant.BizGroup;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.constant.TaskStatus;
import com.gy.hsxt.common.constant.TaskType;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.guid.GuidAgent;
import com.gy.hsxt.common.utils.HsAssert;
import com.gy.hsxt.common.utils.StringUtils;
import com.gy.hsxt.uc.bs.api.consumer.IUCBsCardHolderAuthInfoService;
import com.gy.hsxt.uc.bs.api.ent.IUCBsEntService;
import com.gy.hsxt.uc.bs.bean.consumer.BsRealNameAuth;
import com.gy.hsxt.uc.bs.bean.ent.BsEntMainInfo;

/**
 * 
 * @Package: com.gy.hsxt.bs.entstatus.service
 * @ClassName: RealNameAuthService
 * @Description: 实名认证管理
 * 
 * @author: xiaofl
 * @date: 2015-11-17 上午9:23:53
 * @version V1.0
 */
@Service
public class RealNameAuthService implements IBSRealNameAuthService {

    @Autowired
    private RealNameAuthMapper authMapper;

    @Autowired
    private IOperationService operationService;

    @Autowired
    private BsConfig bsConfig;

    @Autowired
    private IUCBsCardHolderAuthInfoService iUCBsCardHolderAuthInfoService;

    @Autowired
    private IUCBsEntService iUCBsEntService;

    @Autowired
    private ITaskService taskService;

    @Autowired
    private ICommonService commonService;

    /**
     * 创建个人实名认证申请
     * 
     * @param perRealnameAuth
     *            实名认证申请 必填
     * @throws HsException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void createPerRealnameAuth(PerRealnameAuth perRealnameAuth) throws HsException {
        BizLog.biz(this.getClass().getName(), "createPerRealnameAuth", "input param:", null == perRealnameAuth ? "NULL"
                : perRealnameAuth.toString());
        //校验参数
        HsAssert.notNull(perRealnameAuth, RespCode.PARAM_ERROR, "个人申请实名认证[perRealnameAuth]为null");
        HsAssert.hasText(perRealnameAuth.getPerCustId(), RespCode.PARAM_ERROR, "个人申请实名认证[perCustId]为空");
        HsAssert.hasText(perRealnameAuth.getPerResNo(), RespCode.PARAM_ERROR, "个人申请实名认证[perResNo]为空");
//      在数据库插入时进行重复判断
//        // 判断是否存在正在审批的个人实名认证申请
//        if (authMapper.existPerAuth(perRealnameAuth.getPerCustId()))
//        {
//            throw new HsException(BSRespCode.BS_AUTH_PER_DUPLICATE, "存在正在审批的个人实名认证申请");
//        }

        //检查证件名称是否重复
        checkPersonDuplicate(perRealnameAuth);

        try
        {
            String applyId = GuidAgent.getStringGuid(BizGroup.BS + bsConfig.getAppNode());
            perRealnameAuth.setApplyId(applyId);
            // 创建个人实名认证申请
            int row = authMapper.createPerRealnameAuth(perRealnameAuth);
            if(row == 1){
                // 记录个人实名认证操作历史
                operationService.createOptHis(OptTable.T_BS_PER_REALNAME_APPR.getCode(), applyId,
                        RealNameAuthBizAction.SUBMIT_APPLY.getCode(), ApprStatus.WAIT_TO_APPROVE.getCode(), perRealnameAuth
                                .getOptCustId(), perRealnameAuth.getOptName(), perRealnameAuth.getOptEntName(),
                        perRealnameAuth.getPostScript(), null, null);
    
                // 生成个人实名认证审批任务,业务办理主体为消费者个人
                taskService.saveTask(new Task(applyId, TaskType.TASK_TYPE142.getCode(), commonService.getAreaPlatCustId(),
                        perRealnameAuth.getPerResNo(), perRealnameAuth.getName()==null?perRealnameAuth.getEntName() : perRealnameAuth.getName()));
            }else{
                throw new HsException(BSRespCode.BS_AUTH_PER_DUPLICATE, "存在正在审批或已通过的个人实名认证申请");
            }
        }
        catch (HsException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), "createPerRealnameAuth", BSRespCode.BS_AUTH_PER_SUBMIT_ERROR
                    .getCode()
                    + ":申请个人实名认证失败[param=" + perRealnameAuth + "]", e);
            throw new HsException(BSRespCode.BS_AUTH_PER_SUBMIT_ERROR, "申请个人实名认证失败[param=" + perRealnameAuth + "]" + e);
        }

    }

    /**
     * 修改个人实名认证申请
     * 
     * @param perRealnameAuth
     *            实名认证申请 必填
     * @throws HsException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void modifyPerRealnameAuth(PerRealnameAuth perRealnameAuth) throws HsException {
        BizLog.biz(this.getClass().getName(), "modifyPerRealnameAuth", "input param:", null == perRealnameAuth ? "NULL"
                : perRealnameAuth.toString());
        if (null == perRealnameAuth || StringUtils.isBlank(perRealnameAuth.getApplyId()))
        {
            throw new HsException(BSRespCode.BS_PARAMS_NULL, "参数不能为空");
        }
        try
        {
            // 修改个人实名认证申请
            authMapper.updatePerRealnameAuth(perRealnameAuth);
            PerRealnameAuth realname = authMapper.queryPerRealnameAuthById(perRealnameAuth.getApplyId());
            //检查证件名称是否重复
            checkPersonDuplicate(realname);
            
            RealNameAuthBizAction bizAction = null;
            if (realname.getStatus() == ApprStatus.WAIT_TO_APPROVE.getCode())
            {
                bizAction = RealNameAuthBizAction.APPR_MODIFY;
            }
            else if (realname.getStatus() == ApprStatus.APPROVED.getCode())
            {
                bizAction = RealNameAuthBizAction.REVIEW_MODIFY;
            }
            if (bizAction != null)
            {
                // 记录个人实名认证操作历史
                operationService.createOptHis(OptTable.T_BS_PER_REALNAME_APPR.getCode(), perRealnameAuth.getApplyId(),
                        bizAction.getCode(), realname.getStatus(), perRealnameAuth.getOptCustId(), perRealnameAuth
                                .getOptName(), perRealnameAuth.getOptEntName(), perRealnameAuth.getOptRemark(),
                        perRealnameAuth.getDblOptCustId(), perRealnameAuth.getChangeContent());
            }
        }
        catch (HsException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), "modifyPerRealnameAuth", BSRespCode.BS_AUTH_PER_MODIFY_ERROR
                    .getCode()
                    + ":修改个人实名认证失败[param=" + perRealnameAuth + "]", e);
            throw new HsException(BSRespCode.BS_AUTH_PER_MODIFY_ERROR, "修改个人实名认证失败[param=" + perRealnameAuth + "]" + e);
        }
    }
    
    /**
     * 检查个人证件是否重复
     * @param realname
     */
    private void checkPersonDuplicate(PerRealnameAuth realname){
        String name = "";
        if (LegalCreType.BIZ_LICENSE.getCode() == realname.getCertype())
        {
            name = realname.getEntName();
        }
        else
        {
            name = realname.getName();
        }
        // 检查证件是否已被使用
        if (iUCBsCardHolderAuthInfoService.isIdNoExist(realname.getPerCustId(), realname.getCertype(),
                realname.getCredentialsNo(), name))
        {
            throw new HsException(BSRespCode.BS_ID_NO_EXIST);
        }
    }

    /**
     * 查询个人实名认证
     * 
     * @param realNameQueryParam
     *            查询条件
     * @param page
     *            分页信息 必填
     * @return 返回个人实名认证列表，没有则返回null
     * @throws HsException
     */
    @Override
    public PageData<PerRealnameBaseInfo> queryPerRealnameAuth(RealNameQueryParam realNameQueryParam, Page page)
            throws HsException {
        if (null == realNameQueryParam || null == page)
        {
            throw new HsException(BSRespCode.BS_PARAMS_NULL, "参数不能为空");
        }
        PageData<PerRealnameBaseInfo> pageData = null;
        PageContext.setPage(page);
        List<PerRealnameBaseInfo> list = authMapper.queryPerAuthListPage(realNameQueryParam);
        if (null != list && list.size() > 0)
        {
            pageData = new PageData<PerRealnameBaseInfo>();
            pageData.setResult(list);
            pageData.setCount(PageContext.getPage().getCount());
        }
        return pageData;
    }

    /**
     * 关联工单查询个人实名认证审批
     * 
     * @param realNameQueryParam
     *            查询条件
     * @param page
     *            分页信息 必填
     * @return 返回个人实名认证列表，没有则返回null
     * @throws HsException
     */
    @Override
    public PageData<PerRealnameBaseInfo> queryPerRealnameAuth4Appr(RealNameQueryParam realNameQueryParam, Page page)
            throws HsException {
        if (null == realNameQueryParam || isBlank(realNameQueryParam.getOptCustId()) || null == page)
        {
            throw new HsException(BSRespCode.BS_PARAMS_NULL, "参数不能为空");
        }
        PageData<PerRealnameBaseInfo> pageData = null;
        PageContext.setPage(page);
        List<PerRealnameBaseInfo> list = authMapper.queryPerAuth4ApprListPage(realNameQueryParam);
        if (null != list && list.size() > 0)
        {
            pageData = new PageData<PerRealnameBaseInfo>();
            pageData.setResult(list);
            pageData.setCount(PageContext.getPage().getCount());
        }
        return pageData;
    }

    /**
     * 通过申请编号查询个人实名认证详情
     * 
     * @param applyId
     *            申请编号 必填
     * @return 返回个人实名认证详情，没有则返回null
     */
    @Override
    public PerRealnameAuth queryPerRealnameAuthById(String applyId) {
        if (StringUtils.isBlank(applyId))
        {
            return null;
        }

        return authMapper.queryPerRealnameAuthById(applyId);
    }

    /**
     * 通过客户号查询个人实名认证详情
     * 
     * @param perCustId
     *            个人客户号 必填
     * @return 返回个人实名认证详情，没有则返回null
     */
    @Override
    public PerRealnameAuth queryPerRealnameAuthByCustId(String perCustId) {
        if (StringUtils.isBlank(perCustId))
        {
            return null;
        }

        return authMapper.queryPerRealnameAuthByCustId(perCustId);
    }

    /**
     * 审批个人实名认证
     * 
     * @param apprParam
     *            申报信息 必填
     * @throws HsException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void apprPerRealnameAuth(ApprParam apprParam) throws HsException {
        BizLog.biz(this.getClass().getName(), "apprPerRealnameAuth", "input param:", null == apprParam ? "NULL"
                : apprParam.toString());
        if (null == apprParam || StringUtils.isBlank(apprParam.getApplyId())
                || StringUtils.isBlank(apprParam.getOptCustId()) || StringUtils.isBlank(apprParam.getOptName())
                || StringUtils.isBlank(apprParam.getOptEntName()) || null == apprParam.getIsPass())
        {
            throw new HsException(BSRespCode.BS_PARAMS_NULL, "参数不能为空");
        }

        // 查询任务ID，判断用户是否能审批该任务
        String taskId = taskService.getSrcTask(apprParam.getApplyId(), apprParam.getOptCustId());
        if (isBlank(taskId))
        {
            throw new HsException(BSRespCode.BS_TASK_STATUS_NOT_DEALLING.getCode(), "任务状态不是办理中");
        }

        try
        {
            String applyId = apprParam.getApplyId();
            Integer bizAction = null;// 业务阶段
            Integer status;
            if (apprParam.getIsPass())
            {
                bizAction = RealNameAuthBizAction.APPR_PASS.getCode();
                status = ApprStatus.APPROVED.getCode();
            }
            else
            {
                bizAction = RealNameAuthBizAction.APPR_REJECT.getCode();
                status = ApprStatus.REJECTED.getCode();
            }
            // 更新个人实名认证状态
            authMapper
                    .updatePerRealnameAuthStatus(applyId, status, apprParam.getOptCustId(), apprParam.getApprRemark());

            // 记录个人实名认证操作历史
            operationService.createOptHis(OptTable.T_BS_PER_REALNAME_APPR.getCode(), apprParam.getApplyId(), bizAction,
                    status, apprParam.getOptCustId(), apprParam.getOptName(), apprParam.getOptEntName(), apprParam
                            .getApprRemark(), apprParam.getDblOptCustId(), null);

            // 把当前任务更新为已完成
            taskService.updateTaskStatus(taskId, TaskStatus.COMPLETED.getCode());
            if (ApprStatus.APPROVED.getCode() == status)
            {
                // 生成审批任务
                Task task = new Task(applyId, TaskType.TASK_TYPE143.getCode(), commonService.getAreaPlatCustId());
                PerRealnameAuth perRealnameAuth = authMapper.queryPerRealnameAuthById(applyId);
                task.setHsResNo(perRealnameAuth.getPerResNo());
                task.setCustName(perRealnameAuth.getName()==null?perRealnameAuth.getEntName() : perRealnameAuth.getName());
                taskService.saveTask(task);
            }

        }
        catch (HsException hse)
        {
            throw hse;
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), "apprPerRealnameAuth", BSRespCode.BS_AUTH_PER_APPR_ERROR
                    .getCode()
                    + ":审批个人实名认证失败[param=" + apprParam + "]", e);
            throw new HsException(BSRespCode.BS_AUTH_PER_APPR_ERROR, "审批个人实名认证失败[param=" + apprParam + "]" + e);
        }
    }

    /**
     * 审批复核个人实名认证
     * 
     * @param apprParam
     *            审批信息
     * @throws HsException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void reviewApprPerRealnameAuth(ApprParam apprParam) throws HsException {
        BizLog.biz(this.getClass().getName(), "apprPerRealnameAuth", "input param:", null == apprParam ? "NULL"
                : apprParam.toString());
        if (null == apprParam || StringUtils.isBlank(apprParam.getApplyId())
                || StringUtils.isBlank(apprParam.getOptCustId()) || StringUtils.isBlank(apprParam.getOptName())
                || StringUtils.isBlank(apprParam.getOptEntName()) || null == apprParam.getIsPass())
        {
            throw new HsException(BSRespCode.BS_PARAMS_NULL, "参数不能为空");
        }

        // 查询任务ID，判断用户是否能审批该任务
        String taskId = taskService.getSrcTask(apprParam.getApplyId(), apprParam.getOptCustId());
        if (isBlank(taskId))
        {
            throw new HsException(BSRespCode.BS_TASK_STATUS_NOT_DEALLING.getCode(), "任务状态不是办理中");
        }

        try
        {

            String applyId = apprParam.getApplyId();
            Integer bizAction = null;// 业务阶段
            Integer status;
            if (apprParam.getIsPass())
            {
                bizAction = RealNameAuthBizAction.REVIEW_PASS.getCode();
                status = ApprStatus.APPROVAL_REVIEWED.getCode();
            }
            else
            {
                bizAction = RealNameAuthBizAction.REVIEW_REJECT.getCode();
                status = ApprStatus.REVIEW_REJECTED.getCode();
            }

            // 更新个人实名认证状态
            authMapper
                    .updatePerRealnameAuthStatus(applyId, status, apprParam.getOptCustId(), apprParam.getApprRemark());

            // 记录个人实名认证操作历史
            operationService.createOptHis(OptTable.T_BS_PER_REALNAME_APPR.getCode(), apprParam.getApplyId(), bizAction,
                    status, apprParam.getOptCustId(), apprParam.getOptName(), apprParam.getOptEntName(), apprParam
                            .getApprRemark(), apprParam.getDblOptCustId(), null);

            if (apprParam.getIsPass())
            {
                PerRealnameAuth perRealnameAuth = authMapper.queryPerRealnameAuthById(applyId);
                //检查证件名称是否重复
                checkPersonDuplicate(perRealnameAuth);

                // 调用用户中心接口，更新个人信息及状态
                BsRealNameAuth bsRealNameAuthInfo = new BsRealNameAuth();
                bsRealNameAuthInfo.setCustId(perRealnameAuth.getPerCustId());// 持卡人客户号
                bsRealNameAuthInfo.setUserName(perRealnameAuth.getName());// 姓名
                bsRealNameAuthInfo.setSex(perRealnameAuth.getSex());// 性别1：男 0：女
                bsRealNameAuthInfo.setCerType(perRealnameAuth.getCertype());// 证件类型
                bsRealNameAuthInfo.setCerNo(perRealnameAuth.getCredentialsNo());// 证件号码
                bsRealNameAuthInfo.setValidDate(perRealnameAuth.getValidDate());// 证件有效期
                bsRealNameAuthInfo.setIssuingOrg(perRealnameAuth.getLicenceIssuing());// 发证机关
                bsRealNameAuthInfo.setBirthAddress(perRealnameAuth.getBirthAddr());// 户籍地址
                bsRealNameAuthInfo.setCountryCode(perRealnameAuth.getCountryNo());// 国家代码
                bsRealNameAuthInfo.setCountryName(perRealnameAuth.getCountryName());// 国家名称
                bsRealNameAuthInfo.setCerPica(perRealnameAuth.getCerpica());// 证件正面照
                bsRealNameAuthInfo.setCerPicb(perRealnameAuth.getCerpicb());// 证件背面照
                bsRealNameAuthInfo.setCerPich(perRealnameAuth.getCerpich());// 手持证件照
                bsRealNameAuthInfo.setJob(perRealnameAuth.getProfession());// 职业
                bsRealNameAuthInfo.setBirthPlace(perRealnameAuth.getBirthAddr());// 出生地
                bsRealNameAuthInfo.setIssuePlace(perRealnameAuth.getIssuePlace());// 签发地点
                bsRealNameAuthInfo.setEntName(perRealnameAuth.getEntName());// 企业名称
                bsRealNameAuthInfo.setEntRegAddr(perRealnameAuth.getEntRegAddr());// 企业注册地址
                bsRealNameAuthInfo.setEntType(perRealnameAuth.getEntType());// 企业类型
                bsRealNameAuthInfo.setEntBuildDate(perRealnameAuth.getEntBuildDate());// 企业成立日期
                bsRealNameAuthInfo.setAuthRemark(perRealnameAuth.getPostScript());// 认证附言
                iUCBsCardHolderAuthInfoService.authByRealName(bsRealNameAuthInfo, apprParam.getOptCustId());// 同时更改了个人实名认证状态
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
            SystemLog.error(this.getClass().getName(), "reviewApprPerRealnameAuth", BSRespCode.BS_AUTH_PER_REVIEW_ERROR
                    .getCode()
                    + ":复核个人实名认证失败", e);
            throw new HsException(BSRespCode.BS_AUTH_PER_REVIEW_ERROR, "复核个人实名认证失败[param=" + apprParam + "]" + e);
        }
    }

    /**
     * 查询个人实名认证办理状态信息
     * 
     * @param applyId
     *            申请编号
     * @param page
     *            分页信息
     * @return 办理状态信息列表
     * @throws HsException
     */
    @Override
    public PageData<OptHisInfo> queryPerRealnameAuthHis(String applyId, Page page) throws HsException {
        if (isBlank(applyId) || null == page)
        {
            throw new HsException(BSRespCode.BS_PARAMS_NULL, "参数不能为空");
        }
        return operationService.queryOptHisListPage(applyId, OptTable.T_BS_PER_REALNAME_APPR.getCode(), page);
    }

    /**
     * 查询个人实名认证记录
     * 
     * @param perCustId
     *            个人客户号
     * @param startDate
     *            开始时间
     * @param endDate
     *            结束时间
     * @param page
     *            分页信息
     * @return 个人实名认证记录列表
     * @throws HsException
     */
    @Override
    public PageData<SysBizRecord> queryPerAuthRecord(String perCustId, String startDate, String endDate, Page page)
            throws HsException {
        if (isBlank(perCustId) || null == page)
        {
            throw new HsException(BSRespCode.BS_PARAMS_NULL, "参数不能为空");
        }
        PageData<SysBizRecord> pageData = null;
        PageContext.setPage(page);
        List<SysBizRecord> list = authMapper.queryPerAuthRecordListPage(perCustId, startDate, endDate);
        if (null != list && list.size() > 0)
        {
            pageData = new PageData<SysBizRecord>();
            pageData.setResult(list);
            pageData.setCount(PageContext.getPage().getCount());
        }
        return pageData;
    }

    /**
     * 创建企业实名认证信息
     * 
     * @param entRealnameAuth
     *            企业实名认证信息 必填
     * @throws HsException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void createEntRealnameAuth(EntRealnameAuth entRealnameAuth) throws HsException {
        BizLog.biz(this.getClass().getName(), "createEntRealnameAuth", "input param:", entRealnameAuth+"");
        //校验参数
        HsAssert.notNull(entRealnameAuth, RespCode.PARAM_ERROR, "企业申请实名认证[entRealnameAuth]为null");
        HsAssert.hasText(entRealnameAuth.getEntCustId(), RespCode.PARAM_ERROR, "企业申请实名认证[entCustId]为空");
        HsAssert.hasText(entRealnameAuth.getEntResNo(), RespCode.PARAM_ERROR, "企业申请实名认证[entResNo]为空");

//      数据插入时判重
//        // 判断是否存在正在审批的企业实名认证申请
//        if (authMapper.existEntAuth(entRealnameAuth.getEntCustId()))
//        {
//            throw new HsException(BSRespCode.BS_AUTH_ENT_DUPLICATE, "存在正在审批的企业实名认证申请");
//        }
        try
        {
            String applyId = GuidAgent.getStringGuid(BizGroup.BS + bsConfig.getAppNode());
            entRealnameAuth.setApplyId(applyId);

            // 创建企业实名认证信息
            int row = authMapper.createEntRealnameAuth(entRealnameAuth);
            if(row == 1){
                // 记录企业实名认证操作历史
                operationService.createOptHis(OptTable.T_BS_ENT_REALNAME_APPR.getCode(), applyId,
                        RealNameAuthBizAction.SUBMIT_APPLY.getCode(), ApprStatus.WAIT_TO_APPROVE.getCode(), entRealnameAuth
                                .getOptCustId(), entRealnameAuth.getOptName(), entRealnameAuth.getOptEntName(),
                        entRealnameAuth.getPostScript(), entRealnameAuth.getDblOptCustId(), null);
    
                // 生成审批任务
                Task task = new Task(applyId, TaskType.TASK_TYPE145.getCode(), commonService.getAreaPlatCustId());
                task.setHsResNo(entRealnameAuth.getEntResNo());
                task.setCustName(entRealnameAuth.getEntCustName());
                taskService.saveTask(task);
            }else{
                throw new HsException(BSRespCode.BS_AUTH_ENT_DUPLICATE, "存在正在审批的企业实名认证申请");
            }
        }
        catch (HsException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), "createEntRealnameAuth", BSRespCode.BS_AUTH_ENT_SBUMIT_ERROR
                    .getCode()
                    + ":申请企业实名认证失败[param=" + entRealnameAuth + "]", e);
            throw new HsException(BSRespCode.BS_AUTH_ENT_SBUMIT_ERROR, "申请企业实名认证失败[param=" + entRealnameAuth + "]" + e);
        }

    }

    /**
     * 修改企业实名认证申请
     * 
     * @param entRealnameAuth
     *            企业实名认证信息 必填
     * @throws HsException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void modifyEntRealnameAuth(EntRealnameAuth entRealnameAuth) throws HsException {
        BizLog.biz(this.getClass().getName(), "modifyEntRealnameAuth", "input param:", null == entRealnameAuth ? "NULL"
                : entRealnameAuth.toString());
        if (null == entRealnameAuth || StringUtils.isBlank(entRealnameAuth.getApplyId()))
        {
            throw new HsException(BSRespCode.BS_PARAMS_NULL, "参数不能为空");
        }
        try
        {
            // 修改企业实名认证申请
            authMapper.updateEntRealnameAuth(entRealnameAuth);
            EntRealnameAuth realname = authMapper.queryEntRealnameAuthById(entRealnameAuth.getApplyId());
            RealNameAuthBizAction bizAction = null;
            if (realname.getStatus() == ApprStatus.WAIT_TO_APPROVE.getCode())
            {
                bizAction = RealNameAuthBizAction.APPR_MODIFY;
            }
            else if (realname.getStatus() == ApprStatus.APPROVED.getCode())
            {
                bizAction = RealNameAuthBizAction.REVIEW_MODIFY;
            }
            if (bizAction != null)
            {
                // 记录企业实名认证操作历史
                operationService.createOptHis(OptTable.T_BS_ENT_REALNAME_APPR.getCode(), entRealnameAuth.getApplyId(),
                        bizAction.getCode(), realname.getStatus(), entRealnameAuth.getOptCustId(), entRealnameAuth
                                .getOptName(), entRealnameAuth.getOptEntName(), entRealnameAuth.getOptRemark(),
                        entRealnameAuth.getDblOptCustId(), entRealnameAuth.getChangeContent());
            }
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), "modifyEntRealnameAuth", BSRespCode.BS_AUTH_ENT_MODIFY_ERROR
                    .getCode()
                    + ":修改企业实名认证失败[param=" + entRealnameAuth + "]", e);
            throw new HsException(BSRespCode.BS_AUTH_ENT_MODIFY_ERROR, "修改企业实名认证失败[param=" + entRealnameAuth + "]" + e);
        }
    }

    /**
     * 查询企业实名认证
     * 
     * @param realNameQueryParam
     *            查询条件
     * @param page
     *            分页信息 必填
     * @return 返回企业实名认证信息列表，没有则返回null
     * @throws HsException
     */
    @Override
    public PageData<EntRealnameBaseInfo> queryEntRealnameAuth(RealNameQueryParam realNameQueryParam, Page page)
            throws HsException {
        if (null == realNameQueryParam || null == page)
        {
            throw new HsException(BSRespCode.BS_PARAMS_NULL, "参数不能为空");
        }
        PageData<EntRealnameBaseInfo> pageData = null;
        PageContext.setPage(page);
        List<EntRealnameBaseInfo> list = authMapper.queryEntAuthListPage(realNameQueryParam);
        if (null != list && list.size() > 0)
        {
            pageData = new PageData<EntRealnameBaseInfo>();
            pageData.setResult(list);
            pageData.setCount(PageContext.getPage().getCount());
        }
        return pageData;
    }

    /**
     * 关联工单查询企业实名认证审批
     * 
     * @param realNameQueryParam
     *            查询条件
     * @param page
     *            分页信息 必填
     * @return 返回企业实名认证信息列表，没有则返回null
     * @throws HsException
     */
    @Override
    public PageData<EntRealnameBaseInfo> queryEntRealnameAuth4Appr(RealNameQueryParam realNameQueryParam, Page page)
            throws HsException {
        if (null == realNameQueryParam || null == realNameQueryParam.getEntType()
                || isBlank(realNameQueryParam.getOptCustId()) || null == page)
        {
            throw new HsException(BSRespCode.BS_PARAMS_NULL, "参数不能为空");
        }
        PageData<EntRealnameBaseInfo> pageData = null;
        PageContext.setPage(page);
        List<EntRealnameBaseInfo> list = authMapper.queryEntAuth4ApprListPage(realNameQueryParam);
        if (null != list && list.size() > 0)
        {
            pageData = new PageData<EntRealnameBaseInfo>();
            pageData.setResult(list);
            pageData.setCount(PageContext.getPage().getCount());
        }
        return pageData;
    }

    /**
     * 通过申请编号查询企业实名认证详情
     * 
     * @param applyId
     *            申请编号 必填
     * @return 返回实名认证详情,没有则返回null
     */
    @Override
    public EntRealnameAuth queryEntRealnameAuthById(String applyId) {
        if (StringUtils.isBlank(applyId))
        {
            return null;
        }

        return authMapper.queryEntRealnameAuthById(applyId);
    }

    /**
     * 通过客户号查询企业实名认证详情
     * 
     * @param entCustId
     *            企业客户号 必填
     * @return 返回实名认证详情,没有则返回null
     */
    @Override
    public EntRealnameAuth queryEntRealnameAuthByCustId(String entCustId) {
        if (StringUtils.isBlank(entCustId))
        {
            return null;
        }

        return authMapper.queryEntRealnameAuthByCustId(entCustId);
    }

    /**
     * 审批企业实名认证
     * 
     * @param apprParam
     *            审批信息
     * @throws HsException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void apprEntRealnameAuth(ApprParam apprParam) throws HsException {
        BizLog.biz(this.getClass().getName(), "apprEntRealnameAuth", "input param:", null == apprParam ? "NULL"
                : apprParam.toString());
        if (null == apprParam || StringUtils.isBlank(apprParam.getApplyId())
                || StringUtils.isBlank(apprParam.getOptCustId()) || StringUtils.isBlank(apprParam.getOptName())
                || StringUtils.isBlank(apprParam.getOptEntName()) || null == apprParam.getIsPass())
        {
            throw new HsException(BSRespCode.BS_PARAMS_NULL, "参数不能为空");
        }

        // 查询任务ID，判断用户是否能审批该任务
        String taskId = taskService.getSrcTask(apprParam.getApplyId(), apprParam.getOptCustId());
        if (isBlank(taskId))
        {
            throw new HsException(BSRespCode.BS_TASK_STATUS_NOT_DEALLING.getCode(), "任务状态不是办理中");
        }

        try
        {
            String applyId = apprParam.getApplyId();
            Integer bizAction = null;// 业务阶段
            Integer status;
            if (apprParam.getIsPass())
            {
                bizAction = RealNameAuthBizAction.APPR_PASS.getCode();
                status = ApprStatus.APPROVED.getCode();
            }
            else
            {
                bizAction = RealNameAuthBizAction.APPR_REJECT.getCode();
                status = ApprStatus.REJECTED.getCode();
            }

            // 更新企业实名状态
            authMapper
                    .updateEntRealnameAuthStatus(applyId, status, apprParam.getOptCustId(), apprParam.getApprRemark());

            // 记录企业实名认证操作历史
            operationService.createOptHis(OptTable.T_BS_ENT_REALNAME_APPR.getCode(), apprParam.getApplyId(), bizAction,
                    status, apprParam.getOptCustId(), apprParam.getOptName(), apprParam.getOptEntName(), apprParam
                            .getApprRemark(), apprParam.getDblOptCustId(), apprParam.getChangeContent());

            // 把当前任务更新为已完成
            taskService.updateTaskStatus(taskId, TaskStatus.COMPLETED.getCode());
            if (ApprStatus.APPROVED.getCode() == status)
            {
                // 生成审批任务
                Task task = new Task(applyId, TaskType.TASK_TYPE146.getCode(), commonService.getAreaPlatCustId());
                EntRealnameAuth entRealnameAuth = authMapper.queryEntRealnameAuthById(applyId);
                task.setHsResNo(entRealnameAuth.getEntResNo());
                task.setCustName(entRealnameAuth.getEntCustName());
                taskService.saveTask(task);
            }

        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), "apprEntRealnameAuth", BSRespCode.BS_AUTH_ENT_APPR_ERROR
                    .getCode()
                    + ":审批企业实名认证失败[param=" + apprParam + "]", e);
            throw new HsException(BSRespCode.BS_AUTH_ENT_APPR_ERROR, "审批企业实名认证失败[param=" + apprParam + "]" + e);
        }

    }

    /**
     * 审批复核企业实名认证
     * 
     * @param apprParam
     *            审批信息
     * @throws HsException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void reviewApprEntRealnameAuth(ApprParam apprParam) throws HsException {
        BizLog.biz(this.getClass().getName(), "reviewApprEntRealnameAuth", "input param:", null == apprParam ? "NULL"
                : apprParam.toString());
        if (null == apprParam || StringUtils.isBlank(apprParam.getApplyId())
                || StringUtils.isBlank(apprParam.getOptCustId()) || StringUtils.isBlank(apprParam.getOptName())
                || StringUtils.isBlank(apprParam.getOptEntName()) || null == apprParam.getIsPass())
        {
            throw new HsException(BSRespCode.BS_PARAMS_NULL, "参数不能为空");
        }

        // 查询任务ID，判断用户是否能审批该任务
        String taskId = taskService.getSrcTask(apprParam.getApplyId(), apprParam.getOptCustId());
        if (isBlank(taskId))
        {
            throw new HsException(BSRespCode.BS_TASK_STATUS_NOT_DEALLING.getCode(), "任务状态不是办理中");
        }

        try
        {

            String applyId = apprParam.getApplyId();
            Integer bizAction = null;// 业务阶段
            Integer status;
            if (apprParam.getIsPass())
            {
                bizAction = RealNameAuthBizAction.REVIEW_PASS.getCode();
                status = ApprStatus.APPROVAL_REVIEWED.getCode();
            }
            else
            {
                bizAction = RealNameAuthBizAction.REVIEW_REJECT.getCode();
                status = ApprStatus.REVIEW_REJECTED.getCode();
            }
            
            // 更新企业实名状态
            authMapper
                    .updateEntRealnameAuthStatus(applyId, status, apprParam.getOptCustId(), apprParam.getApprRemark());

            // 记录企业实名认证操作历史
            operationService.createOptHis(OptTable.T_BS_ENT_REALNAME_APPR.getCode(), apprParam.getApplyId(), bizAction,
                    status, apprParam.getOptCustId(), apprParam.getOptName(), apprParam.getOptEntName(), apprParam
                            .getApprRemark(), apprParam.getDblOptCustId(), apprParam.getChangeContent());

            if (apprParam.getIsPass())
            {
                EntRealnameAuth entRealnameAuth = authMapper.queryEntRealnameAuthById(applyId);

                // 调用用户中心接口，更新企业信息及状态
                BsEntMainInfo bsEntMainInfo = new BsEntMainInfo();
                bsEntMainInfo.setEntCustType(entRealnameAuth.getCustType());// 客户类型
                bsEntMainInfo.setEntCustId(entRealnameAuth.getEntCustId());// 企业客户号
                bsEntMainInfo.setEntResNo(entRealnameAuth.getEntResNo());// 企业互生号
                bsEntMainInfo.setEntName(entRealnameAuth.getEntCustName());// 企业注册名
                bsEntMainInfo.setEntNameEn(entRealnameAuth.getEntCustNameEn());// 企业英文名称
                bsEntMainInfo.setEntRegAddr(entRealnameAuth.getEntAddr());// 企业注册地址
                bsEntMainInfo.setBusiLicenseNo(entRealnameAuth.getLicenseNo());// 企业营业执照号码
                bsEntMainInfo.setBusiLicenseImg(entRealnameAuth.getLicensePic());// 营业执照照片
                bsEntMainInfo.setOrgCodeNo(entRealnameAuth.getOrgNo());// 组织机构代码证号
                bsEntMainInfo.setOrgCodeImg(entRealnameAuth.getOrgPic());// 组织机构代码证图片
                bsEntMainInfo.setTaxNo(entRealnameAuth.getTaxNo());// 纳税人识别号
                bsEntMainInfo.setTaxRegImg(entRealnameAuth.getTaxPic());// 税务登记证正面扫描图片
                bsEntMainInfo.setCreName(entRealnameAuth.getLegalName());// 法人代表
                bsEntMainInfo.setCreType(entRealnameAuth.getLegalCreType());// 法人证件类型
                bsEntMainInfo.setCreNo(entRealnameAuth.getLegalCreNo());// 法人证件号码
                bsEntMainInfo.setCreFaceImg(entRealnameAuth.getLrcFacePic());// 法人身份证正面图片
                bsEntMainInfo.setCreBackImg(entRealnameAuth.getLrcBackPic());// 法人身份证反面图片
                bsEntMainInfo.setContactPerson(entRealnameAuth.getLinkman());// 联系人
                bsEntMainInfo.setContactPhone(entRealnameAuth.getMobile());// 联系人电话
                iUCBsEntService.updateEntMainInfo(bsEntMainInfo, apprParam.getOptCustId());
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
            SystemLog.error(this.getClass().getName(), "reviewApprEntRealnameAuth", BSRespCode.BS_AUTH_ENT_REVIEW_ERROR
                    .getCode()
                    + ":复核企业实名认证失败[param=" + apprParam + "]", e);
            throw new HsException(BSRespCode.BS_AUTH_ENT_REVIEW_ERROR, "复核企业实名认证失败[param=" + apprParam + "]" + e);
        }

    }

    /**
     * 查询企业实名认证办理状态信息
     * 
     * @param applyId
     *            申请编号
     * @param page
     *            分页信息
     * @return 办理状态信息列表
     * @throws HsException
     */
    @Override
    public PageData<OptHisInfo> queryEntRealnameAuthHis(String applyId, Page page) throws HsException {
        if (isBlank(applyId) || null == page)
        {
            throw new HsException(BSRespCode.BS_PARAMS_NULL, "参数不能为空");
        }
        return operationService.queryOptHisListPage(applyId, OptTable.T_BS_ENT_REALNAME_APPR.getCode(), page);
    }

}
