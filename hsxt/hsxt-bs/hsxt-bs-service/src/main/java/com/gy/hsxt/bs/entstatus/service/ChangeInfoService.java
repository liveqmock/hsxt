/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.entstatus.service;

import com.gy.hsi.lc.client.log4j.BizLog;
import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.bs.api.entstatus.IBSChangeInfoService;
import com.gy.hsxt.bs.apply.interfaces.ICertificateService;
import com.gy.hsxt.bs.apply.interfaces.IContractService;
import com.gy.hsxt.bs.bean.ChangeItem;
import com.gy.hsxt.bs.bean.base.ApprParam;
import com.gy.hsxt.bs.bean.base.OptHisInfo;
import com.gy.hsxt.bs.bean.entstatus.*;
import com.gy.hsxt.bs.common.PageContext;
import com.gy.hsxt.bs.common.enumtype.BSRespCode;
import com.gy.hsxt.bs.common.enumtype.LegalCreType;
import com.gy.hsxt.bs.common.enumtype.OptTable;
import com.gy.hsxt.bs.common.enumtype.entstatus.ApprStatus;
import com.gy.hsxt.bs.common.enumtype.entstatus.ChangeInfoBizAction;
import com.gy.hsxt.bs.common.enumtype.entstatus.EntChangeKey;
import com.gy.hsxt.bs.common.enumtype.entstatus.PerChangeKey;
import com.gy.hsxt.bs.common.interfaces.ICommonService;
import com.gy.hsxt.bs.common.interfaces.IOperationService;
import com.gy.hsxt.bs.disconf.BsConfig;
import com.gy.hsxt.bs.entstatus.interfaces.IChangeInfoService;
import com.gy.hsxt.bs.entstatus.mapper.ChangeInfoMapper;
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
import com.gy.hsxt.common.utils.HsResNoUtils;
import com.gy.hsxt.uc.bs.api.consumer.IUCBsCardHolderAuthInfoService;
import com.gy.hsxt.uc.bs.api.consumer.IUCBsCardHolderStatusInfoService;
import com.gy.hsxt.uc.bs.api.ent.IUCBsEntService;
import com.gy.hsxt.uc.bs.bean.consumer.BsRealNameAuth;
import com.gy.hsxt.uc.bs.bean.ent.BsEntMainInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static com.gy.hsxt.common.utils.StringUtils.isBlank;

/**
 * 重要信息变更管理
 *
 * @version V1.0
 * @Package: com.gy.hsxt.bs.entstatus.service
 * @ClassName: ChangeInfoService
 * @Description: TODO
 * @author: xiaofl
 * @date: 2015-11-17 上午9:22:32
 */
@Service
public class ChangeInfoService implements IBSChangeInfoService, IChangeInfoService {

    @Resource
    private ChangeInfoMapper changeInfoMapper;

    @Resource
    private IOperationService operationService;

    @Resource
    private BsConfig bsConfig;

    @Resource
    private IUCBsCardHolderStatusInfoService iUCBsCardHolderStatusInfoService;

    @Resource
    private IUCBsCardHolderAuthInfoService iUCBsCardHolderAuthInfoService;

    @Resource
    private IUCBsEntService bsEntService;

    @Resource
    private ITaskService taskService;

    @Resource
    private ICommonService commonService;

    @Resource
    private IContractService contractService;

    @Resource
    private ICertificateService certificateService;

    /**
     * 创建个人消费者重要信息变更申请
     *
     * @param perChangeInfo 个人消费者重要信息变更 必填
     * @throws HsException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void createPerChange(PerChangeInfo perChangeInfo) throws HsException {
        BizLog.biz(this.getClass().getName(), "createPerChange", "input param:", null == perChangeInfo ? "NULL"
                : perChangeInfo.toString());
        //校验参数
        HsAssert.notNull(perChangeInfo, RespCode.PARAM_ERROR, "个人申请重要信息变更[perChangeInfo]为null");
        HsAssert.hasText(perChangeInfo.getPerCustId(), RespCode.PARAM_ERROR, "个人申请重要信息变更[perCustId]为空");
        HsAssert.hasText(perChangeInfo.getPerResNo(), RespCode.PARAM_ERROR, "个人申请重要信息变更[perResNo]为空");
        HsAssert.hasText(perChangeInfo.getPerCustName(), RespCode.PARAM_ERROR, "个人申请重要信息变更[perCustName]为空");

//        // 检查是否存在正在审批的个人重要信息变更申请
//        if (changeInfoMapper.existPerChange(perChangeInfo.getPerCustId()))
//        {
//            throw new HsException(BSRespCode.BS_CHANGE_PER_DUPLICATE, "存在正在审批的个人重要信息变更申请");
//        }

        // 检查个人证件名称是否重复
        checkPersonDuplicate(perChangeInfo);

        try {
            String applyId = GuidAgent.getStringGuid(BizGroup.BS + bsConfig.getAppNode());
            perChangeInfo.setApplyId(applyId);
            // 创建消费者重要信息变更申请
            int row = changeInfoMapper.createPerChangeApp(perChangeInfo);
            if (row == 1) {
                // 把消费者重要信息变更转换成属性对象列表形式
                List<ChangeItem> changeItems = this.getPerChangeItemList(perChangeInfo);
                // 创建消费者重要信息变更项数据
                changeInfoMapper.createPerChangeData(applyId, changeItems);

                // 记录个人重要信息变更操作历史
                PerChangeInfoData data = new PerChangeInfoData();
                BeanUtils.copyProperties(perChangeInfo, data);
                operationService.createOptHis(OptTable.T_BS_PER_CHANGE_INFO_APPR.getCode(), applyId,
                        ChangeInfoBizAction.SUBMIT_APPLY.getCode(), ApprStatus.WAIT_TO_APPROVE.getCode(), perChangeInfo
                                .getOptCustId(), perChangeInfo.getOptName(), perChangeInfo.getOptEntName(), perChangeInfo
                                .getApplyReason(), perChangeInfo.getDblOptCustId(), data.toString());

                // 调用用户中心，设置状态为"重要信息变更期间中"
                iUCBsCardHolderStatusInfoService.changeApplyMainInfoStatus(perChangeInfo.getPerCustId(), "1");

                // 生成审批任务
                Task task = new Task(applyId, TaskType.TASK_TYPE154.getCode(), commonService.getAreaPlatCustId(), perChangeInfo.getPerResNo(), perChangeInfo.getPerCustName());
                taskService.saveTask(task);
            } else {
                // 检查是否存在正在审批的个人重要信息变更申请，改由数据库进行判断
                throw new HsException(BSRespCode.BS_CHANGE_PER_DUPLICATE, "存在正在审批的个人重要信息变更申请");
            }
        } catch (HsException hse) {
            throw hse;
        } catch (Exception e) {
            SystemLog.error(this.getClass().getName(), "createPerChange", BSRespCode.BS_CHANGE_PER_SUBMIT_ERROR
                    .getCode()
                    + ":申请个人重要信息变更失败[param=" + perChangeInfo + "]", e);
            throw new HsException(BSRespCode.BS_CHANGE_PER_SUBMIT_ERROR, "申请个人重要信息变更失败 [param=" + perChangeInfo + "]"
                    + e);
        }
    }

    /**
     * // 检查个人证件名称是否重复
     *
     * @param perChangeInfo
     */
    private void checkPersonDuplicate(PerChangeInfo perChangeInfo) {
        // 检查证件是否已被使用
        Integer creType = perChangeInfo.getCreTypeNew();
        String creNo = perChangeInfo.getCreNoNew();
        String name = perChangeInfo.getNameNew();
        String entName = perChangeInfo.getEntNameNew();

        if (null != creType || !isBlank(creNo) || !isBlank(name) || !isBlank(entName)) {
            BsRealNameAuth bsRealNameAuth = iUCBsCardHolderAuthInfoService.searchRealNameAuthInfo(perChangeInfo
                    .getPerCustId());
            if (null == creType) {
                creType = bsRealNameAuth.getCerType();
            }
            if (isBlank(creNo)) {
                creNo = bsRealNameAuth.getCerNo();
            }
            if (LegalCreType.BIZ_LICENSE.getCode() == creType) {// 证件类型为营业执照，是企业
                if (isBlank(entName)) {
                    name = bsRealNameAuth.getEntName();
                } else {
                    name = entName;
                }
            } else {// 证件类型为身份证或护照，是个人
                if (isBlank(name)) {
                    name = bsRealNameAuth.getUserName();
                }
            }

            if (iUCBsCardHolderAuthInfoService.isIdNoExist(perChangeInfo.getPerCustId(), creType, creNo, name)) {
                throw new HsException(BSRespCode.BS_ID_NO_EXIST);
            }
        }
    }

    /**
     * 修改个人消费者重要信息变更申请
     *
     * @param perChangeInfo 个人消费者重要信息变更 必填
     * @throws HsException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void modifyPerChange(PerChangeInfo perChangeInfo) throws HsException {
        BizLog.biz(this.getClass().getName(), "modifyPerChange", "input param:", null == perChangeInfo ? "NULL"
                : perChangeInfo.toString());
        if (null == perChangeInfo || StringUtils.isBlank(perChangeInfo.getApplyId())) {
            throw new HsException(BSRespCode.BS_PARAMS_NULL, "参数不能为空:申请编号");
        }
        // 把消费者重要信息变更转换成属性对象列表形式
        List<ChangeItem> changeItems = this.getPerChangeItemList(perChangeInfo);

        String applyId = perChangeInfo.getApplyId();
        List<ChangeItem> addList = new ArrayList<ChangeItem>();// 新增变更项
        List<ChangeItem> updateList = new ArrayList<ChangeItem>();// 更新变更项
        try {
            for (ChangeItem item : changeItems) {
                if (changeInfoMapper.existPerChangeItem(applyId, item.getProperty())) {
                    updateList.add(item);
                } else {
                    addList.add(item);
                }
            }
            if (addList.size() > 0) {
                // 新增变更项
                changeInfoMapper.createPerChangeData(applyId, addList);
            }

            if (updateList.size() > 0) {
                // 修改变更项
                changeInfoMapper.updatePerChangeData(applyId, updateList);
            }

            // 更新变更项名称
            changeInfoMapper.updatePerChangeApp(perChangeInfo);
            PerChangeInfo changeInfo = changeInfoMapper.queryPerChangeById(perChangeInfo.getApplyId());
            //修改时可能没有设置客户号，只是指定了申请编号
            perChangeInfo.setPerCustId(changeInfo.getPerCustId());
            // 检查个人证件名称是否重复
            checkPersonDuplicate(perChangeInfo);

            ChangeInfoBizAction bizAction = null;
            if (changeInfo.getStatus() == ApprStatus.WAIT_TO_APPROVE.getCode()) {
                bizAction = ChangeInfoBizAction.APPR_MODIFY;
            } else if (changeInfo.getStatus() == ApprStatus.APPROVED.getCode()) {
                bizAction = ChangeInfoBizAction.REVIEW_MODIFY;
            }
            if (bizAction != null) {
                // 记录个人重要信息变更操作历史
                operationService.createOptHis(OptTable.T_BS_PER_CHANGE_INFO_APPR.getCode(), applyId, bizAction
                                .getCode(), changeInfo.getStatus(), perChangeInfo.getOptCustId(), perChangeInfo.getOptName(),
                        perChangeInfo.getOptEntName(), perChangeInfo.getOptRemark(), perChangeInfo.getDblOptCustId(),
                        perChangeInfo.getChangeContent());
            }
        } catch (HsException e) {
            throw e;
        } catch (Exception e) {
            SystemLog.error(this.getClass().getName(), "modifyPerChange", BSRespCode.BS_CHANGE_PER_MODIFY_ERROR
                    .getCode()
                    + ":修改个人重要信息变更失败[param=" + perChangeInfo + "]", e);
            throw new HsException(BSRespCode.BS_CHANGE_PER_MODIFY_ERROR, "修改个人重要信息变更失败[param=" + perChangeInfo + "]"
                    + e);
        }
    }

    /**
     * 查询个人重要信息变更
     *
     * @param changeInfoQueryParam 查询条件
     * @param page                 分页信息 必填
     * @return 返回个人重要信息变更列表，没有则返回null
     * @throws HsException
     */
    @Override
    public PageData<PerChangeBaseInfo> queryPerChange(ChangeInfoQueryParam changeInfoQueryParam, Page page)
            throws HsException {
        if (null == changeInfoQueryParam || null == page) {
            throw new HsException(BSRespCode.BS_PARAMS_NULL, "参数不能为空");
        }
        PageData<PerChangeBaseInfo> pageData = null;
        PageContext.setPage(page);
        List<PerChangeBaseInfo> list = changeInfoMapper.queryPerChangeListPage(changeInfoQueryParam);
        if (null != list && list.size() > 0) {
            pageData = new PageData<PerChangeBaseInfo>();
            pageData.setResult(list);
            pageData.setCount(PageContext.getPage().getCount());
        }
        return pageData;
    }

    /**
     * 关联工单查询个人重要信息变更审批
     *
     * @param changeInfoQueryParam 查询条件
     * @param page                 分页信息 必填
     * @return 返回个人重要信息变更列表，没有则返回null
     * @throws HsException
     */
    @Override
    public PageData<PerChangeBaseInfo> queryPerChange4Appr(ChangeInfoQueryParam changeInfoQueryParam, Page page)
            throws HsException {
        if (null == changeInfoQueryParam || isBlank(changeInfoQueryParam.getOptCustId()) || null == page) {
            throw new HsException(BSRespCode.BS_PARAMS_NULL, "参数不能为空");
        }
        PageData<PerChangeBaseInfo> pageData = null;
        PageContext.setPage(page);
        List<PerChangeBaseInfo> list = changeInfoMapper.queryPerChange4ApprListPage(changeInfoQueryParam);
        if (null != list && list.size() > 0) {
            pageData = new PageData<PerChangeBaseInfo>();
            pageData.setResult(list);
            pageData.setCount(PageContext.getPage().getCount());
        }
        return pageData;
    }

    /**
     * 通过申请编号查询个人重要信息变更详情
     *
     * @param applyId 申请编号 必填
     * @return 返回查询个人重要信息变更详情，没有则返回null
     */
    @Override
    public PerChangeInfo queryPerChangeById(String applyId) {
        if (StringUtils.isBlank(applyId)) {
            return null;
        }
        PerChangeInfo perChangeInfo = changeInfoMapper.queryPerChangeById(applyId);
        List<ChangeItem> itemList = changeInfoMapper.queryPerChangeDataByApplyId(applyId);
        if (null != perChangeInfo) {
            setPerChangeProperty(perChangeInfo, itemList);
        }
        return perChangeInfo;
    }

    /**
     * 通过客户号查询个人重要信息变更详情
     *
     * @param perCustId 个人客户号 必填
     * @return 返回查询个人重要信息变更详情，没有则返回null
     */
    @Override
    public PerChangeInfo queryPerChangeByCustId(String perCustId) {
        if (StringUtils.isBlank(perCustId)) {
            return null;
        }
        PerChangeInfo perChangeInfo = changeInfoMapper.queryPerChangeByCustId(perCustId);
        if (null != perChangeInfo) {
            List<ChangeItem> itemList = changeInfoMapper.queryPerChangeDataByApplyId(perChangeInfo.getApplyId());
            setPerChangeProperty(perChangeInfo, itemList);
        }

        return perChangeInfo;
    }

    /**
     * 审批个人重要信息变更
     *
     * @param apprParam 审批信息
     * @throws HsException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void apprPerChange(ApprParam apprParam) throws HsException {
        BizLog.biz(this.getClass().getName(), "apprPerChange", "input param:", null == apprParam ? "NULL" : apprParam
                .toString());
        if (null == apprParam || StringUtils.isBlank(apprParam.getApplyId())
                || StringUtils.isBlank(apprParam.getOptCustId()) || StringUtils.isBlank(apprParam.getOptName())
                || StringUtils.isBlank(apprParam.getOptEntName()) || null == apprParam.getIsPass()) {
            throw new HsException(BSRespCode.BS_PARAMS_NULL, "参数不能为空");
        }

        // 查询任务ID，判断用户是否能审批该任务
        String taskId = taskService.getSrcTask(apprParam.getApplyId(), apprParam.getOptCustId());
        if (isBlank(taskId)) {
            throw new HsException(BSRespCode.BS_TASK_STATUS_NOT_DEALLING.getCode(), "任务状态不是办理中");
        }

        try {
            String applyId = apprParam.getApplyId();
            Integer bizAction = null;// 业务阶段
            Integer status;
            if (apprParam.getIsPass()) {// 审批通过

                bizAction = ChangeInfoBizAction.APPR_PASS.getCode();
                status = ApprStatus.APPROVED.getCode();// 地区平台初审通过
            } else {// 审批驳回

                bizAction = ChangeInfoBizAction.APPR_REJECT.getCode();
                status = ApprStatus.REJECTED.getCode();// 审批驳回
            }

            // 更新重要信息变更状态
            changeInfoMapper
                    .updatePerChangeStatus(applyId, status, apprParam.getApprRemark(), apprParam.getOptCustId());

            // 记录个人重要信息变更操作历史
            PerChangeInfo perChangeInfoDB = this.queryPerChangeById(applyId);
            PerChangeInfoData data = new PerChangeInfoData();
            BeanUtils.copyProperties(perChangeInfoDB, data);
            operationService.createOptHis(OptTable.T_BS_PER_CHANGE_INFO_APPR.getCode(), applyId, bizAction, status,
                    apprParam.getOptCustId(), apprParam.getOptName(), apprParam.getOptEntName(), apprParam
                            .getApprRemark(), apprParam.getDblOptCustId(), data.toString());

            // 把当前任务更新为已完成
            taskService.updateTaskStatus(taskId, TaskStatus.COMPLETED.getCode());
            if (apprParam.getIsPass())// 通过
            {
                // 生成审批任务
                Task task = new Task(applyId, TaskType.TASK_TYPE155.getCode(), commonService.getAreaPlatCustId(), perChangeInfoDB.getPerResNo(), perChangeInfoDB.getPerCustName());
                taskService.saveTask(task);
            } else {
                // 调用用户中心，设置状态为"非重要信息变更期间中"
                iUCBsCardHolderStatusInfoService.changeApplyMainInfoStatus(perChangeInfoDB.getPerCustId(), "0");
            }
        } catch (HsException hse) {
            throw hse;
        } catch (Exception e) {
            SystemLog.error(this.getClass().getName(), "apprPerChange", BSRespCode.BS_CHANGE_PER_APPR_ERROR.getCode()
                    + ":审批个人重要信息变更失败[param=" + apprParam + "]", e);
            throw new HsException(BSRespCode.BS_CHANGE_PER_APPR_ERROR, "审批个人重要信息变更失败[param=" + apprParam + "]" + e);
        }
    }

    /**
     * 审批复核个人重要信息变更
     *
     * @param apprParam 审批信息
     * @throws HsException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void reviewApprPerChange(ApprParam apprParam) throws HsException {
        BizLog.biz(this.getClass().getName(), "reviewApprPerChange", "input param:", null == apprParam ? "NULL"
                : apprParam.toString());
        if (null == apprParam || StringUtils.isBlank(apprParam.getApplyId())
                || StringUtils.isBlank(apprParam.getOptCustId()) || StringUtils.isBlank(apprParam.getOptName())
                || StringUtils.isBlank(apprParam.getOptEntName()) || null == apprParam.getIsPass()) {
            throw new HsException(BSRespCode.BS_PARAMS_NULL, "参数不能为空");
        }

        // 查询任务ID，判断用户是否能审批该任务
        String taskId = taskService.getSrcTask(apprParam.getApplyId(), apprParam.getOptCustId());
        if (isBlank(taskId)) {
            throw new HsException(BSRespCode.BS_TASK_STATUS_NOT_DEALLING.getCode(), "任务状态不是办理中");
        }

        try {
            String applyId = apprParam.getApplyId();
            Integer bizAction = null;
            Integer status;
            if (apprParam.getIsPass()) {// 审批通过
                bizAction = ChangeInfoBizAction.REVIEW_PASS.getCode();
                status = ApprStatus.APPROVAL_REVIEWED.getCode();// 地区平台复审通过
            } else {// 审批驳回
                bizAction = ChangeInfoBizAction.REVIEW_REJECT.getCode();
                status = ApprStatus.REVIEW_REJECTED.getCode();// 复核驳回
            }

            // 更新重要信息变更状态
            changeInfoMapper
                    .updatePerChangeStatus(applyId, status, apprParam.getApprRemark(), apprParam.getOptCustId());

            PerChangeInfo perChangeInfo = this.queryPerChangeById(applyId);

            // 记录个人重要信息变更操作历史
            PerChangeInfoData data = new PerChangeInfoData();
            BeanUtils.copyProperties(perChangeInfo, data);
            operationService.createOptHis(OptTable.T_BS_PER_CHANGE_INFO_APPR.getCode(), applyId, bizAction, status,
                    apprParam.getOptCustId(), apprParam.getOptName(), apprParam.getOptEntName(), apprParam
                            .getApprRemark(), apprParam.getDblOptCustId(), data.toString());

            if (apprParam.getIsPass()) {// 审批通过
                // 检查个人证件名称是否重复
                checkPersonDuplicate(perChangeInfo);

                // 调用用户中心接口，更新变更项
                BsRealNameAuth bsRealNameAuthInfo = new BsRealNameAuth();
                bsRealNameAuthInfo.setCustId(perChangeInfo.getPerCustId());
                bsRealNameAuthInfo.setUserName(perChangeInfo.getNameNew());
                bsRealNameAuthInfo.setSex(perChangeInfo.getSexNew());
                bsRealNameAuthInfo.setCerType(perChangeInfo.getCreTypeNew());
                bsRealNameAuthInfo.setCerNo(perChangeInfo.getCreNoNew());
                bsRealNameAuthInfo.setValidDate(perChangeInfo.getCreExpireDateNew());
                bsRealNameAuthInfo.setIssuingOrg(perChangeInfo.getCreIssueOrgNew());
                if (perChangeInfo.getCreTypeNew() != null) {
                    //如果是身份证，设置为户籍地址，护照则为出生地
                    if (LegalCreType.ID_CARD.getCode() == perChangeInfo.getCreTypeNew()) {
                        bsRealNameAuthInfo.setBirthAddress(perChangeInfo.getRegistorAddressNew());
                    } else if (LegalCreType.PASSPORT.getCode() == perChangeInfo.getCreTypeNew()) {
                        bsRealNameAuthInfo.setBirthPlace(perChangeInfo.getRegistorAddressNew());
                    }
                }

                bsRealNameAuthInfo.setCountryCode(perChangeInfo.getNationalityNew());
                // bsRealNameAuthInfo.setCountryName(perRealnameAuth.getCountryName());
                bsRealNameAuthInfo.setCerPica(perChangeInfo.getCreFacePicNew());
                bsRealNameAuthInfo.setCerPicb(perChangeInfo.getCreBackPicNew());
                bsRealNameAuthInfo.setCerPich(perChangeInfo.getCreHoldPicNew());
                bsRealNameAuthInfo.setJob(perChangeInfo.getProfessionNew());
                bsRealNameAuthInfo.setIssuePlace(perChangeInfo.getIssuePlaceNew());
                bsRealNameAuthInfo.setEntName(perChangeInfo.getEntNameNew());
                bsRealNameAuthInfo.setEntRegAddr(perChangeInfo.getEntRegAddrNew());
                bsRealNameAuthInfo.setEntType(perChangeInfo.getEntTypeNew());
                bsRealNameAuthInfo.setEntBuildDate(perChangeInfo.getEntBuildDateNew());
                iUCBsCardHolderAuthInfoService.updateMainInfoApplyInfo(bsRealNameAuthInfo, apprParam.getOptCustId());
            } else {// 审批驳回
                // 调用用户中心，设置状态为"非重要信息变更期间中"
                iUCBsCardHolderStatusInfoService.changeApplyMainInfoStatus(perChangeInfo.getPerCustId(), "0");
            }

            // 把当前任务更新为已完成
            taskService.updateTaskStatus(taskId, TaskStatus.COMPLETED.getCode());
        } catch (HsException hse) {
            throw hse;
        } catch (Exception e) {
            SystemLog.error(this.getClass().getName(), "reviewApprPerChange", BSRespCode.BS_CHANGE_PER_REVIEW_ERROR
                    .getCode()
                    + ":复核个人重要信息变更失败[param=" + apprParam + "]", e);
            throw new HsException(BSRespCode.BS_CHANGE_PER_REVIEW_ERROR, "复核个人重要信息变更失败[param=" + apprParam + "]" + e);
        }

    }

    /**
     * 查看个人重要信息变更办理状态
     *
     * @param applyId 申请编号 必填
     * @param page    分页 必填
     * @return 个人重要信息变更办理状态
     */
    @Override
    public PageData<OptHisInfo> queryPerChangeHis(String applyId, Page page) throws HsException {
        if (isBlank(applyId) || null == page) {
            throw new HsException(BSRespCode.BS_PARAMS_NULL, "参数不能为空");
        }
        return operationService.queryOptHisListPage(applyId, OptTable.T_BS_PER_CHANGE_INFO_APPR.getCode(), page);
    }

    /**
     * 查询个人重要信息变更记录
     *
     * @param perCustId 个人客户号
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @param page      分页信息
     * @return 个人重要信息变更记录列表
     * @throws HsException
     */
    @Override
    public PageData<SysBizRecord> queryPerChagneRecord(String perCustId, String startDate, String endDate, Page page)
            throws HsException {
        if (isBlank(perCustId) || null == page) {
            throw new HsException(BSRespCode.BS_PARAMS_NULL, "参数不能为空");
        }
        PageData<SysBizRecord> pageData = null;
        PageContext.setPage(page);
        List<SysBizRecord> list = changeInfoMapper.queryPerChangeRecordListPage(perCustId, startDate, endDate);
        if (null != list && list.size() > 0) {
            pageData = new PageData<SysBizRecord>();
            pageData.setResult(list);
            pageData.setCount(PageContext.getPage().getCount());
        }
        return pageData;
    }

    /**
     * 创建企业重要信息变更
     *
     * @param entChangeInfo 企业重要信息变更 必填
     * @throws HsException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void createEntChange(EntChangeInfo entChangeInfo) throws HsException {
        BizLog.biz(this.getClass().getName(), "createEntChange", "input param:", entChangeInfo+"");
        //校验参数
        HsAssert.notNull(entChangeInfo, RespCode.PARAM_ERROR, "企业申请重要信息变更[entChangeInfo]为null");
        HsAssert.notNull(entChangeInfo.getCustType(), RespCode.PARAM_ERROR, "企业申请重要信息变更[custType]为null");
        HsAssert.hasText(entChangeInfo.getEntCustId(), RespCode.PARAM_ERROR, "企业申请重要信息变更[entCustId]为空");
        HsAssert.hasText(entChangeInfo.getEntResNo(), RespCode.PARAM_ERROR, "企业申请重要信息变更[entResNo]为空");
        HsAssert.hasText(entChangeInfo.getEntCustName(), RespCode.PARAM_ERROR, "企业申请重要信息变更[entCustName]为空");

//        // 检查是否存在正在审批的企业重要信息变更申请
//        if (changeInfoMapper.existEntChange(entChangeInfo.getEntCustId()))
//        {
//            throw new HsException(BSRespCode.BS_CHANGE_ENT_DUPLICATE, "存在正在审批的企业重要信息变更申请");
//        }
        try {
            String applyId = GuidAgent.getStringGuid(BizGroup.BS + bsConfig.getAppNode());
            entChangeInfo.setApplyId(applyId);
            // 创建企业重要信息变更申请
            int row = changeInfoMapper.createEntChangeApp(entChangeInfo);
            if (row == 1) {
                // 把企业重要信息变更转换成属性对象列表形式
                List<ChangeItem> changeItems = this.getEntChangeItemList(entChangeInfo);

                // 创建企业重要信息变更项数据
                changeInfoMapper.createEntChangeData(applyId, changeItems);

                // 记录企业重要信息变更操作历史
                EntChangeInfoData data = new EntChangeInfoData();
                BeanUtils.copyProperties(entChangeInfo, data);
                operationService.createOptHis(OptTable.T_BS_ENT_CHANGE_INFO_APPR.getCode(), applyId,
                        ChangeInfoBizAction.SUBMIT_APPLY.getCode(), ApprStatus.WAIT_TO_APPROVE.getCode(), entChangeInfo
                                .getOptCustId(), entChangeInfo.getOptName(), entChangeInfo.getOptEntName(), entChangeInfo
                                .getApplyReason(), entChangeInfo.getDblOptCustId(), data.toString());

                // 调用用户中心，设置状态为"重要信息变更期间中"
                bsEntService.changeEntMainInfoStatus(entChangeInfo.getEntCustId(), "1", entChangeInfo.getOptCustId());// 变更中

                // 生成审批任务
                Task task = new Task(applyId, TaskType.TASK_TYPE151.getCode(), commonService.getAreaPlatCustId(), entChangeInfo.getEntResNo(), entChangeInfo.getEntCustName());
                taskService.saveTask(task);
            } else {
                // 检查是否存在正在审批的企业重要信息变更申请,改由数据库判断
                throw new HsException(BSRespCode.BS_CHANGE_ENT_DUPLICATE, "存在正在审批的企业重要信息变更申请");
            }
        } catch (HsException hse) {
            throw hse;
        } catch (Exception e) {
            SystemLog.error(this.getClass().getName(), "createEntChange", BSRespCode.BS_CHANGE_ENT_SUBMIT_ERROR
                    .getCode()
                    + ":申请企业重要信息变更失败[param=" + entChangeInfo + "]", e);
            throw new HsException(BSRespCode.BS_CHANGE_ENT_SUBMIT_ERROR, "申请企业重要信息变更失败[param=" + entChangeInfo + "]"
                    + e);
        }
    }

    /**
     * 修改企业重要信息变更申请
     *
     * @param entChangeInfo 企业重要信息变更 必填
     * @throws HsException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void modifyEntChange(EntChangeInfo entChangeInfo) throws HsException {
        BizLog.biz(this.getClass().getName(), "modifyEntChange", "input param:",entChangeInfo+"");
        if (null == entChangeInfo || StringUtils.isBlank(entChangeInfo.getApplyId())) {
            throw new HsException(BSRespCode.BS_PARAMS_NULL, "参数不能为空");
        }

        // 更新重要信息变更业务办理申请书扫描件
        changeInfoMapper.updateEntChangeImptappPic(entChangeInfo.getApplyId(), entChangeInfo.getImptappPic());

        // 把企业重要信息变更转换成属性对象列表形式
        List<ChangeItem> changeItems = this.getEntChangeItemList(entChangeInfo);
        String applyId = entChangeInfo.getApplyId();
        List<ChangeItem> addList = new ArrayList<ChangeItem>();// 新增变更项
        List<ChangeItem> updateList = new ArrayList<ChangeItem>();// 更新变更项
        try {
            for (ChangeItem item : changeItems) {
                if (changeInfoMapper.existEntChangeItem(applyId, item.getProperty())) {
                    updateList.add(item);
                } else {
                    addList.add(item);
                }
            }
            if (addList.size() > 0) {
                changeInfoMapper.createEntChangeData(applyId, addList);
            }

            if (updateList.size() > 0) {
                changeInfoMapper.updateEntChangeData(applyId, updateList);
            }

            EntChangeInfo changeInfo = changeInfoMapper.queryEntChangeById(applyId);
            ChangeInfoBizAction bizAction = null;
            if (changeInfo.getStatus() == ApprStatus.WAIT_TO_APPROVE.getCode()) {
                bizAction = ChangeInfoBizAction.APPR_MODIFY;
            } else if (changeInfo.getStatus() == ApprStatus.APPROVED.getCode()) {
                bizAction = ChangeInfoBizAction.REVIEW_MODIFY;
            }
            if (bizAction != null) {
                // 记录企业重要信息变更操作历史
                operationService.createOptHis(OptTable.T_BS_ENT_CHANGE_INFO_APPR.getCode(), applyId, bizAction
                                .getCode(), changeInfo.getStatus(), entChangeInfo.getOptCustId(), entChangeInfo.getOptName(),
                        entChangeInfo.getOptEntName(), entChangeInfo.getOptRemark(), entChangeInfo.getDblOptCustId(),
                        entChangeInfo.getChangeContent());
            }
        } catch (Exception e) {
            SystemLog.error(this.getClass().getName(), "modifyEntChange", BSRespCode.BS_CHANGE_ENT_MODIFY_ERROR
                    .getCode()
                    + ":修改企业重要信息变更失败[param=" + entChangeInfo + "]", e);
            throw new HsException(BSRespCode.BS_CHANGE_ENT_MODIFY_ERROR, "修改企业重要信息变更失败[param=" + entChangeInfo + "]"
                    + e);
        }

    }

    /**
     * 企业查询重要信息变更
     *
     * @param changeInfoQueryParam 查询条件
     * @param page                 分页信息 必填
     * @return 返回企业重要信息变更列表，没有则返回null
     * @throws HsException
     */
    @Override
    public PageData<EntChangeBaseInfo> entQueryChange(ChangeInfoQueryParam changeInfoQueryParam, Page page)
            throws HsException {
        if (null == changeInfoQueryParam || StringUtils.isBlank(changeInfoQueryParam.getResNo()) || null == page) {
            throw new HsException(BSRespCode.BS_PARAMS_NULL, "参数不能为空:企业互生号");
        }
        changeInfoQueryParam.setName(null);
        changeInfoQueryParam.setEntType(null);
        PageData<EntChangeBaseInfo> pageData = null;
        PageContext.setPage(page);
        List<EntChangeBaseInfo> list = changeInfoMapper.queryEntChangeListPage(changeInfoQueryParam);
        if (null != list && list.size() > 0) {
            pageData = new PageData<>();
            pageData.setResult(list);
            pageData.setCount(PageContext.getPage().getCount());
        }
        return pageData;
    }

    /**
     * 地区平台查询企业重要信息变更
     *
     * @param changeInfoQueryParam 查询条件
     * @param page                 分页信息 必填
     * @return 返回企业重要信息变更列表，没有则返回null
     * @throws HsException
     */
    @Override
    public PageData<EntChangeBaseInfo> platQueryEntChange(ChangeInfoQueryParam changeInfoQueryParam, Page page)
            throws HsException {
        if (null == changeInfoQueryParam || null == page) {
            throw new HsException(BSRespCode.BS_PARAMS_NULL, "参数不能为空：客户类型");
        }
        PageData<EntChangeBaseInfo> pageData = null;
        PageContext.setPage(page);
        List<EntChangeBaseInfo> list = changeInfoMapper.queryEntChangeListPage(changeInfoQueryParam);
        if (null != list && list.size() > 0) {
            pageData = new PageData<EntChangeBaseInfo>();
            pageData.setResult(list);
            pageData.setCount(PageContext.getPage().getCount());
        }
        return pageData;
    }

    /**
     * 地区平台关联工单查询企业重要信息变更审批
     *
     * @param changeInfoQueryParam 查询条件
     * @param page                 分页信息 必填
     * @return 返回企业重要信息变更列表，没有则返回null
     * @throws HsException
     */
    @Override
    public PageData<EntChangeBaseInfo> platQueryEntChange4Appr(ChangeInfoQueryParam changeInfoQueryParam, Page page)
            throws HsException {
        if (null == changeInfoQueryParam || null == changeInfoQueryParam.getEntType()
                || isBlank(changeInfoQueryParam.getOptCustId()) || null == page) {
            throw new HsException(BSRespCode.BS_PARAMS_NULL, "参数不能为空：客户类型");
        }
        PageData<EntChangeBaseInfo> pageData = null;
        PageContext.setPage(page);
        List<EntChangeBaseInfo> list = changeInfoMapper.queryEntChange4ApprListPage(changeInfoQueryParam);
        if (null != list && list.size() > 0) {
            pageData = new PageData<EntChangeBaseInfo>();
            pageData.setResult(list);
            pageData.setCount(PageContext.getPage().getCount());
        }
        return pageData;
    }

    /**
     * 通过申请编号查询企业重要信息变更详情
     *
     * @param applyId 申请编号 必填
     * @return 返回重要信息变更详情，没有则返回null
     */
    @Override
    public EntChangeInfo queryEntChangeById(String applyId) {
        if (StringUtils.isBlank(applyId)) {
            return null;
        }
        EntChangeInfo entChangeInfo = changeInfoMapper.queryEntChangeById(applyId);
        List<ChangeItem> itemList = changeInfoMapper.queryEntChangeDataByApplyId(applyId);
        if (null != entChangeInfo) {
            setEntChangeProperty(entChangeInfo, itemList);
        }

        return entChangeInfo;
    }

    /**
     * 通过客户号查询企业重要信息变更详情
     *
     * @param entCustId 企业客户号 必填
     * @return 返回重要信息变更详情，没有则返回null
     */
    @Override
    public EntChangeInfo queryEntChangeByCustId(String entCustId) {
        if (StringUtils.isBlank(entCustId)) {
            return null;
        }
        EntChangeInfo entChangeInfo = changeInfoMapper.queryEntChangeByCustId(entCustId);
        if (null != entChangeInfo) {
            List<ChangeItem> itemList = changeInfoMapper.queryEntChangeDataByApplyId(entChangeInfo.getApplyId());
            setEntChangeProperty(entChangeInfo, itemList);
        }
        return entChangeInfo;
    }

    /**
     * 审批重要信息变更
     *
     * @param apprParam 审批信息
     * @throws HsException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void apprEntChange(ApprParam apprParam) throws HsException {
        BizLog.biz(this.getClass().getName(), "apprEntChange", "input param:", null == apprParam ? "NULL" : apprParam
                .toString());
        if (null == apprParam || StringUtils.isBlank(apprParam.getApplyId())
                || StringUtils.isBlank(apprParam.getOptCustId()) || StringUtils.isBlank(apprParam.getOptName())
                || StringUtils.isBlank(apprParam.getOptEntName()) || null == apprParam.getIsPass()) {
            throw new HsException(BSRespCode.BS_PARAMS_NULL, "参数不能为空");
        }

        // 查询任务ID，判断用户是否能审批该任务
        String taskId = taskService.getSrcTask(apprParam.getApplyId(), apprParam.getOptCustId());
        if (isBlank(taskId)) {
            throw new HsException(BSRespCode.BS_TASK_STATUS_NOT_DEALLING.getCode(), "任务状态不是办理中");
        }

        try {
            String applyId = apprParam.getApplyId();
            Integer bizAction = null;// 业务阶段
            Integer status;
            if (apprParam.getIsPass()) {// 审批通过

                bizAction = ChangeInfoBizAction.APPR_PASS.getCode();
                status = ApprStatus.APPROVED.getCode();// 地区平台初审通过
            } else {// 审批驳回

                bizAction = ChangeInfoBizAction.APPR_REJECT.getCode();
                status = ApprStatus.REJECTED.getCode();// 平台审批驳回
            }

            // 更新重要信息变更状态
            changeInfoMapper
                    .updateEntChangeStatus(applyId, status, apprParam.getApprRemark(), apprParam.getOptCustId());
            EntChangeInfo entChangeInfo = changeInfoMapper.queryEntChangeById(applyId);
            // 记录企业重要信息变更操作历史
            EntChangeInfo entChangeInfoDB = this.queryEntChangeById(applyId);
            EntChangeInfoData data = new EntChangeInfoData();
            BeanUtils.copyProperties(entChangeInfoDB, data);
            operationService.createOptHis(OptTable.T_BS_ENT_CHANGE_INFO_APPR.getCode(), applyId, bizAction, status,
                    apprParam.getOptCustId(), apprParam.getOptName(), apprParam.getOptEntName(), apprParam
                            .getApprRemark(), apprParam.getDblOptCustId(), data.toString());

            // 把当前任务更新为已完成
            taskService.updateTaskStatus(taskId, TaskStatus.COMPLETED.getCode());
            if (apprParam.getIsPass()) {// 审批通过

                // 生成审批任务
                Task task = new Task(applyId, TaskType.TASK_TYPE152.getCode(), commonService.getAreaPlatCustId(), entChangeInfo.getEntResNo(), entChangeInfo.getEntCustName());
                taskService.saveTask(task);
            } else {// 审批驳回
                // 调用用户中心，设置状态为"非重要信息变更期间中"
                bsEntService
                        .changeEntMainInfoStatus(entChangeInfo.getEntCustId(), "0", entChangeInfo.getOptCustId());// 正常状态
            }
        } catch (HsException hse) {
            throw hse;
        } catch (Exception e) {
            SystemLog.error(this.getClass().getName(), "apprEntChange", BSRespCode.BS_CHANGE_ENT_APPR_ERROR.getCode()
                    + ":审批企业重要信息变更失败[param=" + apprParam + "]", e);
            throw new HsException(BSRespCode.BS_CHANGE_ENT_APPR_ERROR, "审批企业重要信息变更失败[param=" + apprParam + "]" + e);
        }
    }

    /**
     * 审批复核重要信息变更
     *
     * @param apprParam 审批信息
     * @throws HsException
     */
    @Override
    @Transactional
    public void reviewApprEntChange(ApprParam apprParam) throws HsException {
        BizLog.biz(this.getClass().getName(), "reviewApprEntChange", "input param:", null == apprParam ? "NULL"
                : apprParam.toString());
        if (null == apprParam || StringUtils.isBlank(apprParam.getApplyId())
                || StringUtils.isBlank(apprParam.getOptCustId()) || StringUtils.isBlank(apprParam.getOptName())
                || StringUtils.isBlank(apprParam.getOptEntName()) || null == apprParam.getIsPass()) {
            throw new HsException(BSRespCode.BS_PARAMS_NULL, "参数不能为空");
        }

        // 查询任务ID，判断用户是否能审批该任务
        String taskId = taskService.getSrcTask(apprParam.getApplyId(), apprParam.getOptCustId());
        if (isBlank(taskId)) {
            throw new HsException(BSRespCode.BS_TASK_STATUS_NOT_DEALLING.getCode(), "任务状态不是办理中");
        }

        try {
            String applyId = apprParam.getApplyId();
            Integer bizAction = null;// 业务阶段
            Integer status;
            if (apprParam.getIsPass()) {// 审批通过

                status = ApprStatus.APPROVAL_REVIEWED.getCode();// 地区平台复审通过
                bizAction = ChangeInfoBizAction.REVIEW_PASS.getCode();
            } else {// 审批驳回

                bizAction = ChangeInfoBizAction.REVIEW_REJECT.getCode();
                status = ApprStatus.REVIEW_REJECTED.getCode();// 平台复核驳回
            }

            // 更新重要信息变更状态
            changeInfoMapper
                    .updateEntChangeStatus(applyId, status, apprParam.getApprRemark(), apprParam.getOptCustId());

            // 记录企业重要信息变更操作历史
            EntChangeInfo entChangeInfo = this.queryEntChangeById(applyId);
            EntChangeInfoData data = new EntChangeInfoData();
            BeanUtils.copyProperties(entChangeInfo, data);
            operationService.createOptHis(OptTable.T_BS_ENT_CHANGE_INFO_APPR.getCode(), applyId, bizAction, status,
                    apprParam.getOptCustId(), apprParam.getOptName(), apprParam.getOptEntName(), apprParam
                            .getApprRemark(), apprParam.getDblOptCustId(), data.toString());

            if (apprParam.getIsPass()) {// 审批通过
                //重要信息变更通过之后，修改合同证书的盖章状态
                contractService.updateContractForChangeInfo(entChangeInfo);
                //托管企业-创业资源需要重新盖销售证书  服务公司-第三方证书
                if (HsResNoUtils.isBusinessNo(entChangeInfo.getEntResNo()) || HsResNoUtils.isServiceResNo(entChangeInfo.getEntResNo())) {
                    certificateService.updateCertificateForChangeInfo(entChangeInfo);
                }


                // 调用用户中心接口，更新变更项
                BsEntMainInfo bsEntMainInfo = new BsEntMainInfo();
                bsEntMainInfo.setEntCustType(entChangeInfo.getCustType());// 客户类型
                bsEntMainInfo.setEntCustId(entChangeInfo.getEntCustId());// 企业客户号
                bsEntMainInfo.setEntResNo(entChangeInfo.getEntResNo());// 企业互生号
                bsEntMainInfo.setEntName(entChangeInfo.getCustNameNew());// 企业注册名
                bsEntMainInfo.setEntNameEn(entChangeInfo.getCustNameEnNew());// 企业英文名称
                bsEntMainInfo.setEntRegAddr(entChangeInfo.getCustAddressNew());// 企业注册地址
                bsEntMainInfo.setBusiLicenseNo(entChangeInfo.getBizLicenseNoNew());// 企业营业执照号码
                bsEntMainInfo.setBusiLicenseImg(entChangeInfo.getBizLicenseCrePicNew());// 营业执照照片
                bsEntMainInfo.setOrgCodeNo(entChangeInfo.getOrganizerNoNew());// 组织机构代码证号
                bsEntMainInfo.setOrgCodeImg(entChangeInfo.getOrganizerCrePicNew());// 组织机构代码证图片
                bsEntMainInfo.setTaxNo(entChangeInfo.getTaxpayerNoNew());// 纳税人识别号
                bsEntMainInfo.setTaxRegImg(entChangeInfo.getTaxpayerCrePicNew());// 税务登记证正面扫描图片
                bsEntMainInfo.setCreName(entChangeInfo.getLegalRepNew());// 法人代表
                bsEntMainInfo.setCreType(entChangeInfo.getLegalRepCreTypeNew());// 法人证件类型
                bsEntMainInfo.setCreNo(entChangeInfo.getLegalRepCreNoNew());// 法人证件号码
                bsEntMainInfo.setCreFaceImg(entChangeInfo.getLegalRepCreFacePicNew());// 法人身份证正面图片
                bsEntMainInfo.setCreBackImg(entChangeInfo.getLegalRepCreBackPicNew());// 法人身份证反面图片
                bsEntMainInfo.setAuthProxyFile(entChangeInfo.getLinkAuthorizePicNew());//授权委托书
                bsEntMainInfo.setContactPerson(entChangeInfo.getLinkmanNew());// 联系人
                bsEntMainInfo.setContactPhone(entChangeInfo.getLinkmanMobileNew());// 联系人电话
                bsEntService.updateMainInfoApplyInfo(bsEntMainInfo, apprParam.getOptCustId());
            } else {// 驳回

                // 调用用户中心，修改企业重要信息变更状态
                bsEntService
                        .changeEntMainInfoStatus(entChangeInfo.getEntCustId(), "0", entChangeInfo.getOptCustId());// 正常状态
            }

            // 把当前任务更新为已完成
            taskService.updateTaskStatus(taskId, TaskStatus.COMPLETED.getCode());
        } catch (HsException hse) {
            throw hse;
        } catch (Exception e) {
            SystemLog.error(this.getClass().getName(), "reviewApprEntChange", BSRespCode.BS_CHANGE_ENT_REVIEW_ERROR
                    .getCode()
                    + ":复核企业重要信息变更失败[param=" + apprParam + "]", e);
            throw new HsException(BSRespCode.BS_CHANGE_ENT_REVIEW_ERROR, "复核企业重要信息变更失败[param=" + apprParam + "]" + e);
        }

    }

    /**
     * 查看企业重要信息变更办理状态
     *
     * @param applyId 申请编号 必填
     * @param page    分页信息
     * @return 企业重要信息变更办理状态
     */
    @Override
    public PageData<OptHisInfo> queryEntChangeHis(String applyId, Page page) throws HsException {
        if (isBlank(applyId) || null == page) {
            throw new HsException(BSRespCode.BS_PARAMS_NULL, "参数不能为空");
        }
        return operationService.queryOptHisListPage(applyId, OptTable.T_BS_ENT_CHANGE_INFO_APPR.getCode(), page);
    }

    /**
     * 把消费者重要信息变更转换成属性对象列表形式
     *
     * @param perChangeInfo 消费者重要信息变
     * @return 属性对象列表
     */
    private List<ChangeItem> getPerChangeItemList(PerChangeInfo perChangeInfo) {

        List<ChangeItem> changeItems = new ArrayList<ChangeItem>();

        if (perChangeInfo.getNameOld() != null || perChangeInfo.getNameNew() != null) {
            changeItems.add(new ChangeItem(PerChangeKey.NAME.getCode(), perChangeInfo.getNameOld(), perChangeInfo
                    .getNameNew()));
        }

        if (perChangeInfo.getSexOld() != null || perChangeInfo.getSexNew() != null) {
            changeItems.add(new ChangeItem(PerChangeKey.SEX.getCode(), isBlank(perChangeInfo.getSexOld()) ? null
                    : perChangeInfo.getSexOld() + "", isBlank(perChangeInfo.getSexNew()) ? null : perChangeInfo.getSexNew()
                    + ""));
        }

        if (perChangeInfo.getNationalityOld() != null || perChangeInfo.getNationalityNew() != null) {
            changeItems.add(new ChangeItem(PerChangeKey.NATIONALITY.getCode(), perChangeInfo.getNationalityOld(),
                    perChangeInfo.getNationalityNew()));
        }

        if (perChangeInfo.getCreTypeOld() != null || perChangeInfo.getCreTypeNew() != null) {
            changeItems.add(new ChangeItem(PerChangeKey.CRE_TYPE.getCode(), isBlank(perChangeInfo.getCreTypeOld()) ? null
                    : perChangeInfo.getCreTypeOld() + "", isBlank(perChangeInfo.getCreTypeNew()) ? null : perChangeInfo
                    .getCreTypeNew()
                    + ""));
        }

        if (perChangeInfo.getCreNoOld() != null || perChangeInfo.getCreNoNew() != null) {
            changeItems.add(new ChangeItem(PerChangeKey.CRE_NO.getCode(), perChangeInfo.getCreNoOld(), perChangeInfo
                    .getCreNoNew()));
        }

        if (perChangeInfo.getCreExpireDateOld() != null || perChangeInfo.getCreExpireDateNew() != null) {
            changeItems.add(new ChangeItem(PerChangeKey.CRE_INDATE.getCode(), perChangeInfo.getCreExpireDateOld(),
                    perChangeInfo.getCreExpireDateNew()));
        }

        if (perChangeInfo.getCreIssueOrgOld() != null || perChangeInfo.getCreIssueOrgNew() != null) {
            changeItems.add(new ChangeItem(PerChangeKey.CRE_AUTHORITY_ORG.getCode(), perChangeInfo.getCreIssueOrgOld(),
                    perChangeInfo.getCreIssueOrgNew()));
        }

        if (perChangeInfo.getRegistorAddressOld() != null || perChangeInfo.getRegistorAddressNew() != null) {
            changeItems.add(new ChangeItem(PerChangeKey.CENSUS_REGISTOR_ADDRESS.getCode(), perChangeInfo
                    .getRegistorAddressOld(), perChangeInfo.getRegistorAddressNew()));
        }

        if (perChangeInfo.getProfessionOld() != null || perChangeInfo.getProfessionNew() != null) {
            changeItems.add(new ChangeItem(PerChangeKey.PROFESSION.getCode(), perChangeInfo.getProfessionOld(),
                    perChangeInfo.getProfessionNew()));
        }

        if (perChangeInfo.getCreFacePicOld() != null || perChangeInfo.getCreFacePicNew() != null) {
            changeItems.add(new ChangeItem(PerChangeKey.CRE_FACE_PIC.getCode(), perChangeInfo.getCreFacePicOld(),
                    perChangeInfo.getCreFacePicNew()));
        }

        if (perChangeInfo.getCreBackPicOld() != null || perChangeInfo.getCreBackPicNew() != null) {
            changeItems.add(new ChangeItem(PerChangeKey.CRE_BACK_PIC.getCode(), perChangeInfo.getCreBackPicOld(),
                    perChangeInfo.getCreBackPicNew()));
        }

        if (perChangeInfo.getCreHoldPicOld() != null || perChangeInfo.getCreHoldPicNew() != null) {
            changeItems.add(new ChangeItem(PerChangeKey.CRE_HOLD_PIC.getCode(), perChangeInfo.getCreHoldPicOld(),
                    perChangeInfo.getCreHoldPicNew()));
        }

        if (perChangeInfo.getIssuePlaceOld() != null || perChangeInfo.getIssuePlaceNew() != null) {
            changeItems.add(new ChangeItem(PerChangeKey.ISSUE_PLACE.getCode(), perChangeInfo.getIssuePlaceOld(),
                    perChangeInfo.getIssuePlaceNew()));
        }

        if (perChangeInfo.getEntNameOld() != null || perChangeInfo.getEntNameNew() != null) {
            changeItems.add(new ChangeItem(PerChangeKey.ENT_NAME.getCode(), perChangeInfo.getEntNameOld(), perChangeInfo
                    .getEntNameNew()));
        }

        if (perChangeInfo.getEntRegAddrOld() != null || perChangeInfo.getEntRegAddrNew() != null) {
            changeItems.add(new ChangeItem(PerChangeKey.ENT_REG_ADDR.getCode(), perChangeInfo.getEntRegAddrOld(),
                    perChangeInfo.getEntRegAddrNew()));
        }

        if (perChangeInfo.getEntTypeOld() != null || perChangeInfo.getEntTypeNew() != null) {
            changeItems.add(new ChangeItem(PerChangeKey.ENT_TYPE.getCode(), perChangeInfo.getEntTypeOld(), perChangeInfo
                    .getEntTypeNew()));
        }

        if (perChangeInfo.getEntBuildDateOld() != null || perChangeInfo.getEntBuildDateNew() != null) {
            changeItems.add(new ChangeItem(PerChangeKey.ENT_BUILD_DATE.getCode(), perChangeInfo.getEntBuildDateOld(),
                    perChangeInfo.getEntBuildDateNew()));
        }

        return changeItems;
    }

    /**
     * 把属性对象列表形式转换成消费者重要信息变更项
     *
     * @param perChangeInfo 消费者重要信息变更项
     * @param itemList      属性对象列表
     */
    private void setPerChangeProperty(PerChangeInfo perChangeInfo, List<ChangeItem> itemList) {

        for (ChangeItem item : itemList) {

            if (PerChangeKey.NAME.getCode().equals(item.getProperty())) {
                perChangeInfo.setNameOld(item.getOldValue());
                perChangeInfo.setNameNew(item.getNewValue());
                continue;
            }

            if (PerChangeKey.SEX.getCode().equals(item.getProperty())) {
                perChangeInfo.setSexOld(isBlank(item.getOldValue()) ? null : Integer.valueOf(item.getOldValue()));
                perChangeInfo.setSexNew(isBlank(item.getNewValue()) ? null : Integer.valueOf(item.getNewValue()));
                continue;
            }

            if (PerChangeKey.NATIONALITY.getCode().equals(item.getProperty())) {
                perChangeInfo.setNationalityOld(item.getOldValue());
                perChangeInfo.setNationalityNew(item.getNewValue());
                continue;
            }

            if (PerChangeKey.CRE_TYPE.getCode().equals(item.getProperty())) {
                perChangeInfo.setCreTypeOld(isBlank(item.getOldValue()) ? null : Integer.valueOf(item.getOldValue()));
                perChangeInfo.setCreTypeNew(isBlank(item.getNewValue()) ? null : Integer.valueOf(item.getNewValue()));
                continue;
            }

            if (PerChangeKey.CRE_NO.getCode().equals(item.getProperty())) {
                perChangeInfo.setCreNoOld(item.getOldValue());
                perChangeInfo.setCreNoNew(item.getNewValue());
                continue;
            }

            if (PerChangeKey.CRE_INDATE.getCode().equals(item.getProperty())) {
                perChangeInfo.setCreExpireDateOld(item.getOldValue());
                perChangeInfo.setCreExpireDateNew(item.getNewValue());
                continue;
            }

            if (PerChangeKey.CRE_AUTHORITY_ORG.getCode().equals(item.getProperty())) {
                perChangeInfo.setCreIssueOrgOld(item.getOldValue());
                perChangeInfo.setCreIssueOrgNew(item.getNewValue());
                continue;
            }

            if (PerChangeKey.CENSUS_REGISTOR_ADDRESS.getCode().equals(item.getProperty())) {
                perChangeInfo.setRegistorAddressOld(item.getOldValue());
                perChangeInfo.setRegistorAddressNew(item.getNewValue());
                continue;
            }

            if (PerChangeKey.PROFESSION.getCode().equals(item.getProperty())) {
                perChangeInfo.setProfessionOld(item.getOldValue());
                perChangeInfo.setProfessionNew(item.getNewValue());
                continue;
            }

            if (PerChangeKey.CRE_FACE_PIC.getCode().equals(item.getProperty())) {
                perChangeInfo.setCreFacePicOld(item.getOldValue());
                perChangeInfo.setCreFacePicNew(item.getNewValue());
                continue;
            }

            if (PerChangeKey.CRE_BACK_PIC.getCode().equals(item.getProperty())) {
                perChangeInfo.setCreBackPicOld(item.getOldValue());
                perChangeInfo.setCreBackPicNew(item.getNewValue());
                continue;
            }

            if (PerChangeKey.CRE_HOLD_PIC.getCode().equals(item.getProperty())) {
                perChangeInfo.setCreHoldPicOld(item.getOldValue());
                perChangeInfo.setCreHoldPicNew(item.getNewValue());
                continue;
            }

            if (PerChangeKey.ISSUE_PLACE.getCode().equals(item.getProperty())) {
                perChangeInfo.setIssuePlaceOld(item.getOldValue());
                perChangeInfo.setIssuePlaceNew(item.getNewValue());
                continue;
            }

            if (PerChangeKey.ENT_NAME.getCode().equals(item.getProperty())) {
                perChangeInfo.setEntNameOld(item.getOldValue());
                perChangeInfo.setEntNameNew(item.getNewValue());
                continue;
            }

            if (PerChangeKey.ENT_REG_ADDR.getCode().equals(item.getProperty())) {
                perChangeInfo.setEntRegAddrOld(item.getOldValue());
                perChangeInfo.setEntRegAddrNew(item.getNewValue());
                continue;
            }

            if (PerChangeKey.ENT_TYPE.getCode().equals(item.getProperty())) {
                perChangeInfo.setEntTypeOld(item.getOldValue());
                perChangeInfo.setEntTypeNew(item.getNewValue());
                continue;
            }

            if (PerChangeKey.ENT_BUILD_DATE.getCode().equals(item.getProperty())) {
                perChangeInfo.setEntBuildDateOld(item.getOldValue());
                perChangeInfo.setEntBuildDateNew(item.getNewValue());
                continue;
            }
        }
    }

    /**
     * 把企业重要信息变更转换成属性对象列表形式
     *
     * @param entChangeInfo 企业重要信息变
     * @return 属性对象列表
     */
    private List<ChangeItem> getEntChangeItemList(EntChangeInfo entChangeInfo) {

        List<ChangeItem> changeItems = new ArrayList<>();

        if (StringUtils.trimToNull(entChangeInfo.getCustNameOld()) != null || StringUtils.trimToNull(entChangeInfo.getCustNameNew()) != null) {
            changeItems.add(new ChangeItem(EntChangeKey.CUST_NAME.getCode(), entChangeInfo.getCustNameOld(), entChangeInfo
                    .getCustNameNew()));
        }

        if (StringUtils.trimToNull(entChangeInfo.getCustNameEnOld())!=null || StringUtils.trimToNull(entChangeInfo.getCustNameEnNew())!=null) {
            changeItems.add(new ChangeItem(EntChangeKey.CUST_NAME_EN.getCode(), entChangeInfo.getCustNameEnOld(),
                    entChangeInfo.getCustNameEnNew()));
        }

        if (StringUtils.trimToNull(entChangeInfo.getCustAddressOld())!=null || StringUtils.trimToNull(entChangeInfo.getCustAddressNew())!=null) {
            changeItems.add(new ChangeItem(EntChangeKey.CUST_ADDRESS.getCode(), entChangeInfo.getCustAddressOld(),
                    entChangeInfo.getCustAddressNew()));
        }

        if (StringUtils.trimToNull(entChangeInfo.getLinkmanOld())!=null || StringUtils.trimToNull(entChangeInfo.getLinkmanNew())!=null) {
            changeItems.add(new ChangeItem(EntChangeKey.LINKMAN.getCode(), entChangeInfo.getLinkmanOld(), entChangeInfo
                    .getLinkmanNew()));
        }

        if (StringUtils.trimToNull(entChangeInfo.getLinkmanMobileOld())!=null || StringUtils.trimToNull(entChangeInfo.getLinkmanMobileNew())!=null) {
            changeItems.add(new ChangeItem(EntChangeKey.LINK_MOBILE.getCode(), entChangeInfo.getLinkmanMobileOld(),
                    entChangeInfo.getLinkmanMobileNew()));
        }

        if (StringUtils.trimToNull(entChangeInfo.getLegalRepOld())!=null || StringUtils.trimToNull(entChangeInfo.getLegalRepNew())!=null) {
            changeItems.add(new ChangeItem(EntChangeKey.LEGAL_REP.getCode(), entChangeInfo.getLegalRepOld(), entChangeInfo.getLegalRepNew()));
        }

        if (entChangeInfo.getLegalRepCreTypeOld()!=null || entChangeInfo.getLegalRepCreTypeNew()!=null) {
            changeItems.add(new ChangeItem(EntChangeKey.LEGAL_REP_CRE_TYPE.getCode(),
                    entChangeInfo.getLegalRepCreTypeOld() == null ? null : entChangeInfo.getLegalRepCreTypeOld() + "",
                    entChangeInfo.getLegalRepCreTypeNew() == null ? null : entChangeInfo.getLegalRepCreTypeNew() + ""));
        }

        if (StringUtils.trimToNull(entChangeInfo.getLegalRepCreNoOld())!=null || StringUtils.trimToNull(entChangeInfo.getLegalRepCreNoNew())!=null) {
            changeItems.add(new ChangeItem(EntChangeKey.LEGAL_REP_CRE_NO.getCode(), entChangeInfo.getLegalRepCreNoOld(),
                    entChangeInfo.getLegalRepCreNoNew()));
        }

        if (StringUtils.trimToNull(entChangeInfo.getBizLicenseNoOld())!=null || StringUtils.trimToNull(entChangeInfo.getBizLicenseNoNew())!=null) {
            changeItems.add(new ChangeItem(EntChangeKey.BIZ_LICENCE_NO.getCode(), entChangeInfo.getBizLicenseNoOld(),
                    entChangeInfo.getBizLicenseNoNew()));
        }

        if (StringUtils.trimToNull(entChangeInfo.getOrganizerNoOld())!=null || StringUtils.trimToNull(entChangeInfo.getOrganizerNoNew())!=null) {
            changeItems.add(new ChangeItem(EntChangeKey.ORGANIZER_NO.getCode(), entChangeInfo.getOrganizerNoOld(),
                    entChangeInfo.getOrganizerNoNew()));
        }

        if (StringUtils.trimToNull(entChangeInfo.getTaxpayerNoOld())!=null || StringUtils.trimToNull(entChangeInfo.getTaxpayerNoNew())!=null) {
            changeItems.add(new ChangeItem(EntChangeKey.TAXPAYER_NO.getCode(), entChangeInfo.getTaxpayerNoOld(),
                    entChangeInfo.getTaxpayerNoNew()));
        }

        if (StringUtils.trimToNull(entChangeInfo.getLegalRepCreFacePicOld())!=null || StringUtils.trimToNull(entChangeInfo.getLegalRepCreFacePicNew())!=null) {
            changeItems.add(new ChangeItem(EntChangeKey.LEGAL_REP_CRE_FACE_PIC.getCode(), entChangeInfo
                    .getLegalRepCreFacePicOld(), entChangeInfo.getLegalRepCreFacePicNew()));
        }

        if (StringUtils.trimToNull(entChangeInfo.getLegalRepCreBackPicOld())!=null || StringUtils.trimToNull(entChangeInfo.getLegalRepCreBackPicNew())!=null) {
            changeItems.add(new ChangeItem(EntChangeKey.LEGAL_REP_CRE_BACK_PIC.getCode(), entChangeInfo
                    .getLegalRepCreBackPicOld(), entChangeInfo.getLegalRepCreBackPicNew()));
        }

        if (StringUtils.trimToNull(entChangeInfo.getBizLicenseCrePicOld())!=null || StringUtils.trimToNull(entChangeInfo.getBizLicenseCrePicNew())!=null) {
            changeItems.add(new ChangeItem(EntChangeKey.BIZ_LICENCE_CRE_PIC.getCode(), entChangeInfo
                    .getBizLicenseCrePicOld(), entChangeInfo.getBizLicenseCrePicNew()));
        }

        if (StringUtils.trimToNull(entChangeInfo.getOrganizerCrePicOld())!=null || StringUtils.trimToNull(entChangeInfo.getOrganizerCrePicNew())!=null) {
            changeItems.add(new ChangeItem(EntChangeKey.ORGANIZER_CRE_PIC.getCode(), entChangeInfo.getOrganizerCrePicOld(),
                    entChangeInfo.getOrganizerCrePicNew()));
        }

        if (StringUtils.trimToNull(entChangeInfo.getTaxpayerCrePicOld())!=null || StringUtils.trimToNull(entChangeInfo.getTaxpayerCrePicNew())!=null) {
            changeItems.add(new ChangeItem(EntChangeKey.TAXPAYER_CRE_PIC.getCode(), entChangeInfo.getTaxpayerCrePicOld(),
                    entChangeInfo.getTaxpayerCrePicNew()));
        }

        if (StringUtils.trimToNull(entChangeInfo.getLinkAuthorizePicOld())!=null || StringUtils.trimToNull(entChangeInfo.getLinkAuthorizePicNew())!=null) {
            changeItems.add(new ChangeItem(EntChangeKey.LINK_AUTHORIZE_PIC.getCode(), entChangeInfo.getLinkAuthorizePicOld(), entChangeInfo.getLinkAuthorizePicNew()));
        }

        return changeItems;
    }

    /**
     * 把属性对象列表形式转换成企业重要信息变更项
     *
     * @param entChangeInfo 企业重要信息变更项
     * @param itemList      属性对象列表
     */
    private void setEntChangeProperty(EntChangeInfo entChangeInfo, List<ChangeItem> itemList) {

        for (ChangeItem item : itemList) {
            if (EntChangeKey.CUST_NAME.getCode().equals(item.getProperty())) {
                entChangeInfo.setCustNameOld(item.getOldValue());
                entChangeInfo.setCustNameNew(item.getNewValue());
                continue;
            }

            if (EntChangeKey.CUST_NAME_EN.getCode().equals(item.getProperty())) {
                entChangeInfo.setCustNameEnOld(item.getOldValue());
                entChangeInfo.setCustNameEnNew(item.getNewValue());
                continue;
            }

            if (EntChangeKey.CUST_ADDRESS.getCode().equals(item.getProperty())) {
                entChangeInfo.setCustAddressOld(item.getOldValue());
                entChangeInfo.setCustAddressNew(item.getNewValue());
                continue;
            }

            if (EntChangeKey.LINKMAN.getCode().equals(item.getProperty())) {
                entChangeInfo.setLinkmanOld(item.getOldValue());
                entChangeInfo.setLinkmanNew(item.getNewValue());
                continue;
            }

            if (EntChangeKey.LINK_MOBILE.getCode().equals(item.getProperty())) {
                entChangeInfo.setLinkmanMobileOld(item.getOldValue());
                entChangeInfo.setLinkmanMobileNew(item.getNewValue());
                continue;
            }

            if (EntChangeKey.LEGAL_REP.getCode().equals(item.getProperty())) {
                entChangeInfo.setLegalRepOld(item.getOldValue());
                entChangeInfo.setLegalRepNew(item.getNewValue());
                continue;
            }

            if (EntChangeKey.LEGAL_REP_CRE_TYPE.getCode().equals(item.getProperty())) {
                entChangeInfo.setLegalRepCreTypeOld(isBlank(item.getOldValue()) ? null : Integer.valueOf(item
                        .getOldValue()));
                entChangeInfo.setLegalRepCreTypeNew(isBlank(item.getNewValue()) ? null : Integer.valueOf(item
                        .getNewValue()));
                continue;
            }

            if (EntChangeKey.LEGAL_REP_CRE_NO.getCode().equals(item.getProperty())) {
                entChangeInfo.setLegalRepCreNoOld(item.getOldValue());
                entChangeInfo.setLegalRepCreNoNew(item.getNewValue());
                continue;
            }

            if (EntChangeKey.BIZ_LICENCE_NO.getCode().equals(item.getProperty())) {
                entChangeInfo.setBizLicenseNoOld(item.getOldValue());
                entChangeInfo.setBizLicenseNoNew(item.getNewValue());
                continue;
            }

            if (EntChangeKey.ORGANIZER_NO.getCode().equals(item.getProperty())) {
                entChangeInfo.setOrganizerNoOld(item.getOldValue());
                entChangeInfo.setOrganizerNoNew(item.getNewValue());
                continue;
            }

            if (EntChangeKey.TAXPAYER_NO.getCode().equals(item.getProperty())) {
                entChangeInfo.setTaxpayerNoOld(item.getOldValue());
                entChangeInfo.setTaxpayerNoNew(item.getNewValue());
                continue;
            }

            if (EntChangeKey.LEGAL_REP_CRE_FACE_PIC.getCode().equals(item.getProperty())) {
                entChangeInfo.setLegalRepCreFacePicOld(item.getOldValue());
                entChangeInfo.setLegalRepCreFacePicNew(item.getNewValue());
                continue;
            }

            if (EntChangeKey.LEGAL_REP_CRE_BACK_PIC.getCode().equals(item.getProperty())) {
                entChangeInfo.setLegalRepCreBackPicOld(item.getOldValue());
                entChangeInfo.setLegalRepCreBackPicNew(item.getNewValue());
                continue;
            }

            if (EntChangeKey.BIZ_LICENCE_CRE_PIC.getCode().equals(item.getProperty())) {
                entChangeInfo.setBizLicenseCrePicOld(item.getOldValue());
                entChangeInfo.setBizLicenseCrePicNew(item.getNewValue());
                continue;
            }

            if (EntChangeKey.ORGANIZER_CRE_PIC.getCode().equals(item.getProperty())) {
                entChangeInfo.setOrganizerCrePicOld(item.getOldValue());
                entChangeInfo.setOrganizerCrePicNew(item.getNewValue());
                continue;
            }

            if (EntChangeKey.TAXPAYER_CRE_PIC.getCode().equals(item.getProperty())) {
                entChangeInfo.setTaxpayerCrePicOld(item.getOldValue());
                entChangeInfo.setTaxpayerCrePicNew(item.getNewValue());
                continue;
            }

            if (EntChangeKey.LINK_AUTHORIZE_PIC.getCode().equals(item.getProperty())) {
                entChangeInfo.setLinkAuthorizePicOld(item.getOldValue());
                entChangeInfo.setLinkAuthorizePicNew(item.getNewValue());
            }
        }
    }

    /**
     * 是否存在正审批的企业重要信息变更
     *
     * @param entCustId 企业客户号
     * @return true存在，false则不存在
     * @see com.gy.hsxt.bs.entstatus.interfaces.IChangeInfoService#isExistApplyingEntChange(java.lang.String)
     */
    @Override
    public Boolean isExistApplyingEntChange(String entCustId) {
        return changeInfoMapper.isExistApplyingEntChange(entCustId);
    }
}
