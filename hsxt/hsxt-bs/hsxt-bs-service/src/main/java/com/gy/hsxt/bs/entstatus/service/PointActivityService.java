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

import org.springframework.beans.BeanUtils;
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
import com.gy.hsxt.bs.api.entstatus.IBSPointActivityService;
import com.gy.hsxt.bs.apply.mapper.DeclareMapper;
//import com.gy.hsxt.bs.bean.apply.DebtOrderParam;
//import com.gy.hsxt.bs.bean.apply.ResFeeDebtOrder;
import com.gy.hsxt.bs.bean.base.ApprParam;
import com.gy.hsxt.bs.bean.base.OptHisInfo;
import com.gy.hsxt.bs.bean.entstatus.PointActivity;
import com.gy.hsxt.bs.bean.entstatus.PointActivityBaseInfo;
import com.gy.hsxt.bs.bean.entstatus.PointActivityQueryParam;
import com.gy.hsxt.bs.bean.entstatus.PointActivityStatus;
import com.gy.hsxt.bs.common.PageContext;
import com.gy.hsxt.bs.common.enumtype.BSRespCode;
import com.gy.hsxt.bs.common.enumtype.OptTable;
import com.gy.hsxt.bs.common.enumtype.entstatus.PointActivityKey;
import com.gy.hsxt.bs.common.enumtype.entstatus.PointAppBizAction;
import com.gy.hsxt.bs.common.enumtype.entstatus.PointAppStatus;
import com.gy.hsxt.bs.common.enumtype.entstatus.PointAppType;
import com.gy.hsxt.bs.common.interfaces.ICommonService;
import com.gy.hsxt.bs.common.interfaces.IOperationService;
import com.gy.hsxt.bs.disconf.BsConfig;
import com.gy.hsxt.bs.entstatus.bean.PointActivityParam;
import com.gy.hsxt.bs.entstatus.interfaces.IChangeInfoService;
import com.gy.hsxt.bs.entstatus.mapper.PointActivityMapper;
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
 * 
 * @Package: com.gy.hsxt.bs.entstatus.service
 * @ClassName: PointActivityService
 * @Description: 积分活动管理
 * 
 * @author: xiaofl
 * @date: 2015-11-16 下午4:59:37
 * @version V1.0
 */
@Service
public class PointActivityService implements IBSPointActivityService {

    /** 重要信息变更Service **/
    @Autowired
    private IChangeInfoService changeInfoService;

    @Autowired
    private PointActivityMapper pointActivityMapper;
    
    @Resource
    private DeclareMapper declareMapper;
    
    /**
     * 年费Service
     **/
    @Resource
    private IAnnualFeeInfoService annualFeeInfoService;
    

    @Autowired
    private IOperationService operationService;

    @Autowired
    private BsConfig bsConfig;

    @Autowired
    private IUCBsEntService iUCBsEntService;

    @Autowired
    private IOrderService orderService;

    @Autowired
    private IInsideInvokeCall insideInvokeCall;

    @Autowired
    private ITaskService taskService;

    @Autowired
    private ICommonService commonService;
    
    @Autowired
    EnterpriceService enterpriceService;

    /**
     * 申请停止/参与积分活动
     * 
     * @param pointActivity
     *            积分活动信息 必填
     * @throws HsException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void createPointActivity(PointActivity pointActivity) throws HsException {
        BizLog.biz(this.getClass().getName(), "createPointActivity", "input param:", null == pointActivity ? "NULL"
                : pointActivity.toString());
        //校验参数
        HsAssert.notNull(pointActivity, RespCode.PARAM_ERROR, "申请停止/参与积分活动[pointActivity]为null");
        HsAssert.notNull(pointActivity.getApplyType(), RespCode.PARAM_ERROR, "申请停止/参与积分活动[applyType]为null");
        HsAssert.hasText(pointActivity.getEntCustId(), RespCode.PARAM_ERROR, "申请停止/参与积分活动[entCustId]为空");
        HsAssert.hasText(pointActivity.getEntResNo(), RespCode.PARAM_ERROR, "申请停止/参与积分活动[entResNo]为空");

        try
        {
//            // 检查是否存在正在审批的积分活动申请
//            if (pointActivityMapper.existPointActivity(pointActivity.getEntCustId()))
//            {
//                throw new HsException(BSRespCode.BS_POINT_ACT_DUPLICATE, "存在正在审批的积分活动申请");
//            }
            
            BsEntMainInfo mainInfo = iUCBsEntService.searchEntMainInfo(pointActivity.getEntCustId());
            if(mainInfo!=null){
                pointActivity.setEntResNo(mainInfo.getEntResNo());
                pointActivity.setEntCustName(mainInfo.getEntName());
                pointActivity.setEntAddress(mainInfo.getEntRegAddr());
                pointActivity.setLinkman(mainInfo.getContactPerson());
                pointActivity.setLinkmanMobile(mainInfo.getContactPhone());
            }

            pointActivity.setApplyId(GuidAgent.getStringGuid(BizGroup.BS + bsConfig.getAppNode()));

            // 查询该托管企业所属服务公司资料
            String serEntResNo = pointActivity.getEntResNo().substring(0, 5) + "000000";
            BsEntMainInfo serEntMainInfo = iUCBsEntService.getMainInfoByResNo(serEntResNo);
            pointActivity.setSerEntCustId(serEntMainInfo.getEntCustId());
            pointActivity.setSerEntResNo(serEntMainInfo.getEntResNo());
            pointActivity.setSerEntCustName(serEntMainInfo.getEntName());

            int row = pointActivityMapper.createPointActivity(pointActivity);
            if(row == 1){
                if (PointAppType.STOP_PONIT_ACITIVITY.getCode() == pointActivity.getApplyType())
                {
                    //申请停止积分活动通知用户中心和互商
                    applyStopPoint(pointActivity);
                }
            }else{
             // 检查是否存在正在审批的积分活动申请,改由数据库进行判断
                throw new HsException(BSRespCode.BS_POINT_ACT_DUPLICATE, "存在正在审批的积分活动申请");
            }
        }
        catch (HsException hse)
        {
            throw hse;
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), "createPointActivity", BSRespCode.BS_POINT_ACT_SUBMIT_ERROR
                    .getCode()
                    + ":申请积分活动失败[param=" + pointActivity + "]", e);
            throw new HsException(BSRespCode.BS_POINT_ACT_SUBMIT_ERROR, "申请积分活动失败[param=" + pointActivity + "]" + e);
        }
    }

    /**
     * 服务公司查询积分活动申请
     * 
     * @param pointActivityQueryParam
     *            查询条件
     * @param page
     *            分页信息 必填
     * @return 返回积分活动申请列表，没有则返回null
     * @throws HsException
     */
    @Override
    public PageData<PointActivityBaseInfo> serviceQueryPointActivity(PointActivityQueryParam pointActivityQueryParam,
            Page page) throws HsException {
        if (null == pointActivityQueryParam || isBlank(pointActivityQueryParam.getSerResNo())
                || null == pointActivityQueryParam.getApplyType() || null == page)
        {
            throw new HsException(BSRespCode.BS_PARAMS_NULL, "参数不能为空");
        }
        PointActivityParam pointActivityParam = new PointActivityParam();
        BeanUtils.copyProperties(pointActivityQueryParam, pointActivityParam);
        return this.queryPointActivity(pointActivityParam, page, false);
    }

    /**
     * 地区平台查询积分活动审批
     * 
     * @param pointActivityQueryParam
     *            查询条件
     * @param page
     *            分页信息 必填
     * @return 返回积分活动申请列表，没有则返回null
     * @throws HsException
     */
    @Override
    public PageData<PointActivityBaseInfo> platQueryPointActivity4Appr(PointActivityQueryParam pointActivityQueryParam,
            Page page) throws HsException {
        if (null == pointActivityQueryParam || null == page || null == pointActivityQueryParam.getApplyType())
        {
            throw new HsException(BSRespCode.BS_PARAMS_NULL, "参数不能为空");
        }
        pointActivityQueryParam.setSerResNo(null);
        PointActivityParam pointActivityParam = new PointActivityParam();
        BeanUtils.copyProperties(pointActivityQueryParam, pointActivityParam);

        String inStatus = "(" + PointAppStatus.SERVICE_APPROVED.getCode() + ","
                + PointAppStatus.PLAT_APPROVED.getCode() + ")";
        pointActivityParam.setInStatus(inStatus);// 只能查询状态是1和2的记录
        return this.queryPointActivity(pointActivityParam, page, true);
    }

    /**
     * 地区平台查询积分活动申请
     * 
     * @param pointActivityQueryParam
     *            查询条件
     * @param page
     *            分页信息 必填
     * @return 返回积分活动申请列表，没有则返回null
     * @throws HsException
     */
    @Override
    public PageData<PointActivityBaseInfo> platQueryPointActivity(PointActivityQueryParam pointActivityQueryParam,
            Page page) throws HsException {
        if (null == pointActivityQueryParam || null == page || null == pointActivityQueryParam.getApplyType())
        {
            throw new HsException(BSRespCode.BS_PARAMS_NULL, "参数不能为空");
        }
        pointActivityQueryParam.setSerResNo(null);
        PointActivityParam pointActivityParam = new PointActivityParam();
        BeanUtils.copyProperties(pointActivityQueryParam, pointActivityParam);
        return this.queryPointActivity(pointActivityParam, page, false);
    }

    private PageData<PointActivityBaseInfo> queryPointActivity(PointActivityParam pointActivityParam, Page page,
            boolean taskFlag) {

        PageData<PointActivityBaseInfo> pageData = null;
        PageContext.setPage(page);
        List<PointActivityBaseInfo> list = null;
        if (taskFlag)
        {// 关联工单
            list = pointActivityMapper.queryPointActivity4ApprListPage(pointActivityParam);
        }
        else
        {
            list = pointActivityMapper.queryPointActivityListPage(pointActivityParam);
        }
        if (null != list && list.size() > 0)
        {
            pageData = new PageData<PointActivityBaseInfo>();
            pageData.setResult(list);
            pageData.setCount(PageContext.getPage().getCount());
        }
        return pageData;
    }

    /**
     * 查询积分活动详情
     * 
     * @param applyId
     *            申请编号
     * @return 返回积分活动详情，没有则返回null key:pointActivity value:PointActivity key:his
     *         value:List<PointActivityHis>
     * @throws HsException
     */
    @Override
    public Map<String, Object> queryPointActivityById(String applyId) throws HsException {

        Map<String, Object> map = new HashMap<String, Object>();
        OptHisInfo serOptHisInfo = null;// 服务公司及审批信息
        OptHisInfo platOptHisInfo = null;// 地区平台审批信息
        OptHisInfo platReviewHis = null;// 地区平台复核信息

        PointActivity pointActivity = pointActivityMapper.queryPointActivityById(applyId);// 停止积分活动申请信息

        List<OptHisInfo> optHisList = operationService.queryOptHisAll(applyId, OptTable.T_BS_POINTGAME_APPR.getCode());
        for (OptHisInfo optHisInfo : optHisList)
        {
            if (serOptHisInfo == null
                    && PointAppBizAction.SER_APPR_POINT_ACTIVITY.getCode() == optHisInfo.getBizAction())
            {
                serOptHisInfo = optHisInfo;
            }
            else if (platOptHisInfo == null
                    && PointAppBizAction.PLAT_APPR_POINT_ACTIVITY.getCode() == optHisInfo.getBizAction())
            {
                platOptHisInfo = optHisInfo;
            }
            else if (platReviewHis == null
                    && PointAppBizAction.PLAT_REVIEW_POINT_ACTIVITY.getCode() == optHisInfo.getBizAction())
            {
                platReviewHis = optHisInfo;
            }
        }

        map.put(PointActivityKey.POINT_ACTIVITY.getCode(), pointActivity);
        map.put(PointActivityKey.SER_APPR_HIS.getCode(), serOptHisInfo);
        map.put(PointActivityKey.PLAT_APPR_HIS.getCode(), platOptHisInfo);
        map.put(PointActivityKey.PLAT_REVIEW_HIS.getCode(), platReviewHis);

        return map;
    }

    /**
     * 服务公司审批积分活动申请
     * 
     * @param apprParam
     *            审批信息
     * @throws HsException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void serviceApprPointActivity(ApprParam apprParam) throws HsException {
        BizLog.biz(this.getClass().getName(), "serviceApprPointActivity", "input param:", null == apprParam ? "NULL"
                : apprParam.toString());
        if (null == apprParam || isBlank(apprParam.getApplyId()) || isBlank(apprParam.getOptCustId())
                || isBlank(apprParam.getOptName()) || isBlank(apprParam.getOptEntName())
                || null == apprParam.getIsPass())
        {
            throw new HsException(BSRespCode.BS_PARAMS_NULL, "参数不能为空");
        }

        try
        {
            String applyId = apprParam.getApplyId();
            PointActivity pointActivity = pointActivityMapper.queryPointActivityById(applyId);
            Integer applyType = pointActivity.getApplyType();

            Integer status = null;
            if (apprParam.getIsPass())
            {
                status = PointAppStatus.SERVICE_APPROVED.getCode(); // 服务公司审批通过
            }
            else
            {
                status = PointAppStatus.SERVICE_REJECTED.getCode(); // 服务公司审批驳回
            }

            // 更新积分活动状态
            pointActivityMapper.updatePointActivityStatus(applyId, status, apprParam.getOptCustId());

            // 记录操作历史
            operationService.createOptHis(OptTable.T_BS_POINTGAME_APPR.getCode(), applyId,
                    PointAppBizAction.SER_APPR_POINT_ACTIVITY.getCode(), status, apprParam.getOptCustId(), apprParam
                            .getOptName(), apprParam.getOptEntName(), apprParam.getApprRemark(), null, null);

            if (!apprParam.getIsPass() && PointAppType.STOP_PONIT_ACITIVITY.getCode() == applyType)
            {
                // 停止积分活动被驳回通知用户中心与互商
                rejectedStopPoint(pointActivity,apprParam.getOptCustId());
            }

            if (PointAppStatus.SERVICE_APPROVED.getCode() == status)
            {
                // 生成审批任务
                Task task = new Task(applyId, TaskType.TASK_TYPE136.getCode(), commonService.getAreaPlatCustId());
                task.setHsResNo(pointActivity.getEntResNo());
                task.setCustName(pointActivity.getEntCustName());
                taskService.saveTask(task);
            }
        }
        catch (HsException hse)
        {
            throw hse;
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), "serviceApprPointActivity",
                    BSRespCode.BS_POINT_ACT_SERVICE_APPR_ERROR.getCode() + ":服务公司审批积分活动失败[param=" + apprParam + "]", e);
            throw new HsException(BSRespCode.BS_POINT_ACT_SERVICE_APPR_ERROR, "服务公司审批积分活动失败[param=" + apprParam + "]"
                    + e);
        }
    }

    /**
     * 地区平台审批积分活动申请
     * 
     * @param apprParam
     *            审批信息
     * @throws HsException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void platApprPointActivity(ApprParam apprParam) throws HsException {
        BizLog.biz(this.getClass().getName(), "platApprPointActivity", "input param:", null == apprParam ? "NULL"
                : apprParam.toString());
        if (null == apprParam || isBlank(apprParam.getApplyId()) || isBlank(apprParam.getOptCustId())
                || isBlank(apprParam.getOptName()) || isBlank(apprParam.getOptEntName())
                || null == apprParam.getIsPass())
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
            PointActivity pointActivity = pointActivityMapper.queryPointActivityById(applyId);
            Integer applyType = pointActivity.getApplyType();

            Integer status = null;
            if (apprParam.getIsPass())
            {
                status = PointAppStatus.PLAT_APPROVED.getCode(); // 平台审批通过
            }
            else
            {
                status = PointAppStatus.PLAT_REJECTED.getCode(); // 平台审批驳回
            }

            // 更新积分活动状态
            pointActivityMapper.updatePointActivityStatus(applyId, status, apprParam.getOptCustId());

            // 记录操作历史
            operationService.createOptHis(OptTable.T_BS_POINTGAME_APPR.getCode(), applyId,
                    PointAppBizAction.PLAT_APPR_POINT_ACTIVITY.getCode(), status, apprParam.getOptCustId(), apprParam
                            .getOptName(), apprParam.getOptEntName(), apprParam.getApprRemark(), null, null);

            if (!apprParam.getIsPass() && PointAppType.STOP_PONIT_ACITIVITY.getCode() == applyType)
            {
                // 停止积分活动被驳回通知用户中心与互商
                rejectedStopPoint(pointActivity,apprParam.getOptCustId());
            }

            // 把当前任务更新为已完成
            taskService.updateTaskStatus(taskId, TaskStatus.COMPLETED.getCode());
            if (PointAppStatus.PLAT_APPROVED.getCode() == status)
            {
                // 生成审批任务
                Task task = new Task(applyId, TaskType.TASK_TYPE137.getCode(), commonService.getAreaPlatCustId());
                task.setHsResNo(pointActivity.getEntResNo());
                task.setCustName(pointActivity.getEntCustName());
                taskService.saveTask(task);
            }

        }
        catch (HsException hse)
        {
            throw hse;
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), "platApprPointActivity", BSRespCode.BS_POINT_ACT_PLAT_APPR_ERROR
                    .getCode()
                    + ":平台审批积分活动失败[param=" + apprParam + "]", e);
            throw new HsException(BSRespCode.BS_POINT_ACT_PLAT_APPR_ERROR, "平台审批积分活动失败[param=" + apprParam + "]" + e);
        }
    }
    
    /**
     * 地区平台复核积分活动申请
     * 
     * @param apprParam
     *            审批信息
     * @throws HsException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void platReviewPointActivity(ApprParam apprParam) throws HsException {
        BizLog.biz(this.getClass().getName(), "platReviewPointActivity", "input param:", null == apprParam ? "NULL"
                : apprParam.toString());
        if (null == apprParam || isBlank(apprParam.getApplyId()) || isBlank(apprParam.getOptCustId())
                || isBlank(apprParam.getOptName()) || isBlank(apprParam.getOptEntName())
                || null == apprParam.getIsPass())
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
            PointActivity pointActivity = pointActivityMapper.queryPointActivityById(applyId);
            Integer applyType = pointActivity.getApplyType();

            Integer status = null;
            if (apprParam.getIsPass())
            {
                status = PointAppStatus.PLAT_APPROVAL_REVIEWED.getCode();
            }
            else
            {
                status = PointAppStatus.PLAT_REVIEW_REJECTED.getCode();
            }

            // 更新积分活动状态
            pointActivityMapper.updatePointActivityStatus(applyId, status, apprParam.getOptCustId());

            // 记录操作历史
            operationService.createOptHis(OptTable.T_BS_POINTGAME_APPR.getCode(), applyId,
                    PointAppBizAction.PLAT_REVIEW_POINT_ACTIVITY.getCode(), status, apprParam.getOptCustId(), apprParam
                            .getOptName(), apprParam.getOptEntName(), apprParam.getApprRemark(), null, null);

            if (apprParam.getIsPass())
            {// 平台复核通过
                if (PointAppType.STOP_PONIT_ACITIVITY.getCode() == applyType)
                {
                    // 停止积分活动审批通过后通知用户中心与互商
                    passedStopPoint(pointActivity,apprParam.getOptCustId());
                }
                else if (PointAppType.JOIN_PONIT_ACTIVITY.getCode() == applyType)
                {
                    // 调用户中心接口更新企业状态,更新企业状态为已参与积分活动
                    BsEntStatusInfo bsEntStatusInfo = new BsEntStatusInfo();
                    bsEntStatusInfo.setEntCustId(pointActivity.getEntCustId());
                    bsEntStatusInfo.setBaseStatus(BsEntBaseStatusEumn.NORMAL.getstatus());// 正常状态
                    iUCBsEntService.updateEntStatusInfo(bsEntStatusInfo, apprParam.getOptCustId());
                }
            }
            else
            {
                if (PointAppType.STOP_PONIT_ACITIVITY.getCode() == applyType)
                {
                    // 停止积分活动被驳回通知用户中心与互商
                    rejectedStopPoint(pointActivity,apprParam.getOptCustId());
                }

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
            SystemLog.error(this.getClass().getName(), "platReviewPointActivity",
                    BSRespCode.BS_POINT_ACT_PLAT_REVIEW_ERROR.getCode() + ":平台复核积分活动失败[param=" + apprParam + "]", e);
            throw new HsException(BSRespCode.BS_POINT_ACT_PLAT_REVIEW_ERROR, "平台复核积分活动失败[param=" + apprParam + "]" + e);
        }
    }

    /**
     * 根据企业客户号查询积分活动状态
     * 
     * @param entCustId
     *            企业客户号
     * @return 积分活动状态信息
     */
    @Override
    public PointActivityStatus queryPointActivityStatus(String entCustId) {
        return pointActivityMapper.queryPointActivityStatus(entCustId);
    }
    
    /**
     * 停止积分活动申请被驳回时通知UC、互商进行后续处理
     * @param pointActivity 停止积分活动申请单对象
     * @param optCustId 操作员客户号
     */
    private void rejectedStopPoint(PointActivity pointActivity, String optCustId){
        // 通知用户中心更改客户状态为积分活动正常状态
        BsEntStatusInfo bsEntStatusInfo = new BsEntStatusInfo();
        bsEntStatusInfo.setEntCustId(pointActivity.getEntCustId());
        bsEntStatusInfo.setBaseStatus(pointActivity.getOldStatus());
        iUCBsEntService.updateEntStatusInfo(bsEntStatusInfo, optCustId);
        
        //通知互商停止积分活动被驳回了
        EnterpriceInputParam param = new EnterpriceInputParam();
        param.setEnterpriceResourceNo(pointActivity.getEntResNo());
        try{
            ReturnResult  result = enterpriceService.refuseEnterpriceApplyStopPointActivity(param);
            if(200 == result.getRetCode()){
                SystemLog.debug(enterpriceService.getClass().getName(), "refuseEnterpriceApplyStopPointActivity", "停止积分活动审批驳回时通知互商成功。"+pointActivity);
            }else{
                SystemLog.error(enterpriceService.getClass().getName(), "refuseEnterpriceApplyStopPointActivity", "停止积分活动审批驳回时通知互商失败，[param="+pointActivity+"],互商返回错误消息："+result.getRetMsg(),null);
            }
        }catch(Exception e){
            SystemLog.error(enterpriceService.getClass().getName(), "refuseEnterpriceApplyStopPointActivity",
                    "停止积分活动审批驳回时通知互商调用接口失败[" + pointActivity.getEntResNo() + "]", e);
        }
    }
    
    /**
     * 通过了停止积分活动后续通知用户中心、互商处理
     * @param pointActivity 停止积分活动申请单对象
     * @param optCustId 操作员客户号
     */
    private void passedStopPoint(PointActivity pointActivity, String optCustId){
      //调用户中心接口更新企业状态,更新企业状态为已停止积分活动
        BsEntStatusInfo bsEntStatusInfo = new BsEntStatusInfo();
        bsEntStatusInfo.setEntCustId(pointActivity.getEntCustId());
        bsEntStatusInfo.setBaseStatus(BsEntBaseStatusEumn.STOP_POINT.getstatus());// 已停止积分活动
        iUCBsEntService.updateEntStatusInfo(bsEntStatusInfo, optCustId);
        
      //通知互商停止积分活动被通过了
        EnterpriceInputParam param = new EnterpriceInputParam();
        param.setEnterpriceResourceNo(pointActivity.getEntResNo());
        try{
            ReturnResult  result = enterpriceService.passEnterpriceApplyStopPointActivity(param);
            if(200 == result.getRetCode()){
                SystemLog.debug(enterpriceService.getClass().getName(), "passEnterpriceApplyStopPointActivity", "停止积分活动审批通过时通知互商成功。"+pointActivity);
            }else{
                SystemLog.error(enterpriceService.getClass().getName(), "passEnterpriceApplyStopPointActivity", "停止积分活动审批通过时通知互商失败，[param="+pointActivity+"],互商返回错误消息："+result.getRetMsg(),null);
            }
        }catch(Exception e){
            SystemLog.error(enterpriceService.getClass().getName(), "passEnterpriceApplyStopPointActivity",
                    "停止积分活动审批通过时通知互商调用接口失败[" + pointActivity.getEntResNo() + "]", e);
        }
    }
    
    /**
     * 停止积分活动申请提交时通知用户中心与互商
     * @param pointActivity 停止积分活动申请单
     */
    private void applyStopPoint(PointActivity pointActivity){
        // 通知用户中心更改客户状态为停止积分活动申请中
        BsEntStatusInfo bsEntStatusInfo = new BsEntStatusInfo();
        bsEntStatusInfo.setEntCustId(pointActivity.getEntCustId());
        bsEntStatusInfo.setBaseStatus(BsEntBaseStatusEumn.APPLY_POINT.getstatus());// 申请停止积分活动中
        iUCBsEntService.updateEntStatusInfo(bsEntStatusInfo, pointActivity.getEntCustId());
        
        EnterpriceInputParam param = new EnterpriceInputParam();
        param.setEnterpriceResourceNo(pointActivity.getEntResNo());
        try{
            ReturnResult  result = enterpriceService.applyEnterpriceStopPointActivity(param);
            if(200 == result.getRetCode()){
                SystemLog.debug(enterpriceService.getClass().getName(), "applyEnterpriceStopPointActivity", "停止积分活动申请提交时通知互商成功。"+pointActivity.getEntResNo());
            }else{
                SystemLog.error(enterpriceService.getClass().getName(), "applyEnterpriceStopPointActivity", "停止积分活动申请提交时通知互商失败，[param="+pointActivity.getEntResNo()+"],互商返回错误消息："+result.getRetMsg(),null);
            }
        }catch(Exception e){
            SystemLog.error(enterpriceService.getClass().getName(), "applyEnterpriceStopPointActivity",
                    "停止积分活动申请提交时通知互商调用接口失败[" + pointActivity.getEntResNo() + "]", e);
        }
    }

}
