/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO.,
 * LTD. All rights reserved.
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.bs.pwd.service;

import com.gy.hsi.lc.client.log4j.BizLog;
import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsi.nt.api.beans.NoteBean;
import com.gy.hsi.nt.api.common.Priority;
import com.gy.hsi.nt.api.exception.NoticeException;
import com.gy.hsi.nt.api.service.INtService;
import com.gy.hsxt.bs.api.pwd.IBSTransPwdService;
import com.gy.hsxt.bs.bean.pwd.ResetTransPwd;
import com.gy.hsxt.bs.bean.pwd.TransPwdQuery;
import com.gy.hsxt.bs.common.BSConstants;
import com.gy.hsxt.bs.common.PageContext;
import com.gy.hsxt.bs.common.enumtype.ApprStatus;
import com.gy.hsxt.bs.common.enumtype.BSRespCode;
import com.gy.hsxt.bs.common.enumtype.msgtpl.MsgBizType;
import com.gy.hsxt.bs.common.interfaces.ICommonService;
import com.gy.hsxt.bs.disconf.BsConfig;
import com.gy.hsxt.bs.pwd.mapper.TransPwdMapper;
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
import com.gy.hsxt.uc.bs.api.common.IUCBsMobileService;
import com.gy.hsxt.uc.bs.api.ent.IUCBsEntService;
import com.gy.hsxt.uc.bs.bean.ent.BsEntAllInfo;
import com.gy.hsxt.uc.bs.bean.ent.BsEntBaseInfo;
import com.gy.hsxt.uc.bs.bean.ent.BsEntMainInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 重置交易密码 实现类
 *
 * @version V1.0
 * @Package: com.gy.hsxt.bs.pwd.service
 * @ClassName: TranPwdService
 * @Description:
 * @author: liuhq
 * @date: 2015-10-15 上午11:46:09
 */
@Service
public class TransPwdService implements IBSTransPwdService {
    /**
     * 业务服务私有配置参数
     **/
    @Resource
    private BsConfig bsConfig;

    /**
     * 注入 重置交易密码 接口
     */
    @Resource
    private TransPwdMapper transPwdMapper;

    /**
     * 注入 获取验证码的接口
     */
    @Resource
    private IUCBsMobileService ucBsMobileService;

    /**
     * 注入 企业信息接口
     */
    @Resource
    private IUCBsEntService bsEntService;

    /**
     * 工单业务接口
     */
    @Resource
    private ITaskService taskService;

    /**
     * 公共数据接口
     */
    @Resource
    private ICommonService commonService;

    /**
     * 消息系统
     */
    @Resource
    private INtService ntService;

    /**
     * 创建 重置交易密码申请记录
     *
     * @param resetTransPwd 交易密码重置申请 实体类 非null
     *                      <p/>
     *                      无异常便成功，Exception失败
     * @throws HsException
     */
    @Override
    @Transactional
    public void createResetTransPwdApply(ResetTransPwd resetTransPwd) throws HsException {
        // 写入系统日志
        SystemLog.debug(bsConfig.getSysName(), "function:createResetTransPwdApply", "创建重置交易密码申请--实体参数[resetTransPwd]:"
                + resetTransPwd);
        // 实体类为null时抛出异常
        HsAssert.notNull(resetTransPwd, RespCode.PARAM_ERROR, "重置交易密码申请参数[resetTransPwd]为null");
        // 校验企业客户号
        HsAssert.hasText(resetTransPwd.getEntCustId(), RespCode.PARAM_ERROR, "企业客户号[entCustId]为空");
        HsAssert.hasText(resetTransPwd.getEntResNo(), RespCode.PARAM_ERROR, "企业互生号[entResNo]为空");
        resetTransPwd.setCustType(HsResNoUtils.getCustTypeByHsResNo(resetTransPwd.getEntResNo()));

        try {
            // 判断某个企业是否存在待审的申请
            String pendingId = transPwdMapper.getApplyPendingId(resetTransPwd.getEntCustId());

            // 此企业重置密码申请待审记录已存在，请不要重复提交
            HsAssert.isTrue(StringUtils.isBlank(pendingId), BSRespCode.BS_TRANS_PWD_PENDING_EXIST,
                    "此企业重置密码申请待审记录已存在，请不要重复提交");

            resetTransPwd.setApplyId(GuidAgent.getStringGuid(BizGroup.BS + bsConfig.getAppNode()));// 生成GUID

            // 执行创建
            transPwdMapper.createResetTranPwdApply(resetTransPwd);

            // 创建操作日志
            BizLog.biz(bsConfig.getSysName(), "function:createResetTransPwdApply", "params==>" + resetTransPwd,
                    "创建重置交易密码申请记录成功");

            // 保存申请后，派送工单
            String platCustId = commonService.getAreaPlatCustId();
            HsAssert.hasText(platCustId, BSRespCode.BS_QUERY_AREA_PLAT_CUST_ID_ERROR, "查询地区平台客户号失败");
            // 生成企业交易密码重置审批任务，业务办理主体为申请企业
            taskService.saveTask(new Task(resetTransPwd.getApplyId(), TaskType.TASK_TYPE161.getCode(), platCustId,
                    resetTransPwd.getEntResNo(), resetTransPwd.getEntCustName()));

        } catch (HsException hse) {
            // 写入错误日志
            SystemLog.error(bsConfig.getSysName(), "function:createResetTransPwdApply", hse.getMessage(), hse);

            throw hse;
        } catch (Exception e) {
            // 写入错误日志
            SystemLog.error(bsConfig.getSysName(), "function:createResetTransPwdApply",
                    "创建重置交易密码申请记录失败，参数[resetTransPwd]:" + resetTransPwd, e);

            throw new HsException(BSRespCode.BS_TRANS_PWD_DB_ERROR, "创建重置交易密码申请记录失败,原因:" + e.getMessage());
        }
    }

    /**
     * 分页查询重置交易密码申请记录
     *
     * @param page          分页对象 必须
     * @param transPwdQuery 条件查询实体
     * @return 返回分好页的数据列表
     * @throws HsException
     */
    @Override
    public PageData<ResetTransPwd> queryResetTransPwdApplyListPage(Page page, TransPwdQuery transPwdQuery)
            throws HsException {

        // 写入系统日志
        SystemLog.debug(bsConfig.getSysName(), "function:queryResetTransPwdApplyListPage",
                "分页查询 重置交易密码申请记录--查询实体[transPwdQuery]:" + transPwdQuery);
        // 分页对象为null抛出异常
        HsAssert.notNull(page, RespCode.PARAM_ERROR, "分页对象[page]为null");

        if (transPwdQuery != null) {
            // 校验日期
            transPwdQuery.checkDateFormat();
            transPwdQuery.checkStatus();
        }
        try {
            // 设置分页信息
            PageContext.setPage(page);

            // 分页查询数据列表
            List<ResetTransPwd> list = transPwdMapper.queryResetTranPwdApplyListPage(transPwdQuery);

            // 重载分页数据
            return PageData.bulid(page.getCount(), list);
        } catch (Exception e) {
            // 写入错误日志
            SystemLog.error(bsConfig.getSysName(), "function:queryResetTransPwdApplyListPage",
                    "分页查询重置交易密码申请记录失败，参数[transPwdQuery]:" + transPwdQuery, e);

            throw new HsException(BSRespCode.BS_TRANS_PWD_DB_ERROR, "分页查询 重置交易密码申请记录失败，原因:" + e.getMessage());
        }
    }

    /**
     * 分页查询重置交易密码待审核列表
     *
     * @param page          分页对象 必须
     * @param transPwdQuery 条件查询实体
     * @return 返回分好页的数据列表
     * @throws HsException
     */
    @Override
    public PageData<ResetTransPwd> queryTaskListPage(Page page, TransPwdQuery transPwdQuery) throws HsException {
        // 写入系统日志
        SystemLog.debug(bsConfig.getSysName(), "function:queryTaskListPage", "分页查询重置交易密码待审核列表--查询实体[transPwdQuery]:"
                + transPwdQuery);
        // 分页对象为null抛出异常
        HsAssert.notNull(page, RespCode.PARAM_ERROR, "分页对象[page]为null");
        HsAssert.notNull(transPwdQuery, RespCode.PARAM_ERROR, "查询对象[transPwdQuery]为空");
        HsAssert.hasText(transPwdQuery.getOptCustId(), RespCode.PARAM_ERROR, "操作员客户号[optCustId]为空");

        transPwdQuery.setTaskType(TaskType.TASK_TYPE161.getCode());// 企业交易密码重置平台审批
        // TASK_TYPE161
        transPwdQuery.setTaskStatus(TaskStatus.DEALLING.getCode());// 办理中
        transPwdQuery.setStatus(ApprStatus.WAIT_APPR.getCode());// 待审批
        // 校验日期
        transPwdQuery.checkDateFormat();

        try {
            // 设置分页信息
            PageContext.setPage(page);

            // 分页查询数据列表
            List<ResetTransPwd> list = transPwdMapper.selectTaskListPage(transPwdQuery);

            // 重载分页数据
            return PageData.bulid(page.getCount(), list);
        } catch (Exception e) {
            // 写入错误日志
            SystemLog.error(bsConfig.getSysName(), "function:queryTaskListPage", "分页查询重置交易密码申请记录失败，参数[transPwdQuery]:"
                    + transPwdQuery, e);

            throw new HsException(BSRespCode.BS_TRANS_PWD_DB_ERROR, "分页查询重置交易密码申请记录失败，原因:" + e.getMessage());
        }
    }

    /**
     * 查询某一条重置交易密码申请记录
     *
     * @param applyId 申请编号 必须 非null
     * @return 返回某一条重置交易密码申请记录
     * @throws HsException
     */
    @Override
    public ResetTransPwd getResetTransPwdApplyBean(String applyId) throws HsException {
        // 写入系统日志
        SystemLog.debug(bsConfig.getSysName(), "function:getResetTransPwdApplyBean", "查询某一条重置交易密码申请记录--申请编号[applyId]:"
                + applyId);
        // 申请编号为空时抛出异常
        HsAssert.hasText(applyId, RespCode.PARAM_ERROR, "查询某一条重置交易密码申请记录--申请编号[applyId]为空");

        try {
            return transPwdMapper.getResetTranPwdApplyBean(applyId);
        } catch (Exception e) {
            // 写入系统日志
            SystemLog.error(bsConfig.getSysName(), "function:getResetTransPwdApplyBean",
                    "查询某一条重置交易密码申请记录失败，参数[applyId]:" + applyId, e);

            throw new HsException(BSRespCode.BS_TRANS_PWD_DB_ERROR, "查询某一条重置交易密码申请记录失败，原因:" + e.getMessage());
        }
    }

    /**
     * 根据企业客户号查询最新的申请记录
     *
     * @param entCustId 企业客户号
     * @return bean
     * @throws HsException
     */
    @Override
    public ResetTransPwd queryLastApplyBean(String entCustId) throws HsException {
        // 写入系统日志
        SystemLog.debug(bsConfig.getSysName(), "function:queryLastApplyBean", "根据企业客户号查询最新的申请记录[entCustId]:"
                + entCustId);
        // 申请编号为空时抛出异常
        HsAssert.hasText(entCustId, RespCode.PARAM_ERROR, "企业客户号[entCustId]为空");

        try {
            return transPwdMapper.selectLastApplyBeanByCustId(entCustId);
        } catch (Exception e) {
            // 写入系统日志
            SystemLog.error(bsConfig.getSysName(), "function:queryLastApplyBean", "根据企业客户号查询最新的申请记录失败，参数[entCustId]:"
                    + entCustId, e);

            throw new HsException(BSRespCode.BS_TRANS_PWD_DB_ERROR, "根据企业客户号查询最新的申请记录失败，原因:" + e.getMessage());
        }
    }

    /**
     * 审批重置交易密码申请记录
     *
     * @param resetTransPwd 实体对象
     * @throws HsException
     */
    @Override
    @Transactional
    public void apprResetTranPwdApply(ResetTransPwd resetTransPwd) throws HsException {
        // 写入系统日志
        SystemLog.debug(bsConfig.getSysName(), "function:apprResetTransPwdApply", "审批重置交易密码申请记录[resetTransPwd]:"
                + resetTransPwd);
        // 申请编号为空或状态为null时抛出异常
        HsAssert.notNull(resetTransPwd, RespCode.PARAM_ERROR, "申请记录[resetTransPwd]为null");
        // 状态 1：通过 2：驳回
        HsAssert.isTrue(ApprStatus.checkStatus(resetTransPwd.getStatus()), RespCode.PARAM_ERROR, "审核状态参数[status]错误");
        // 校验操作者
        HsAssert.hasText(resetTransPwd.getUpdatedby(), RespCode.PARAM_ERROR, "操作者ID[updatedby]为空");
        // 申请记录ID
        HsAssert.hasText(resetTransPwd.getApplyId(), RespCode.PARAM_ERROR, "申请记录ID[applyId]为空");
        // 校验企业客户号
        HsAssert.hasText(resetTransPwd.getEntCustId(), RespCode.PARAM_ERROR, "企业客户号[entCustId]为空");

        try {

            transPwdMapper.apprResetTranPwdApply(resetTransPwd);

            // 创建操作日志
            BizLog.biz(bsConfig.getSysName(), "function:apprResetTranPwdApply", "params==>" + resetTransPwd,
                    "审批重置交易密码申请记录成功");

            // 审批操作完成时，更新工单状态
            String taskId = taskService.getSrcTask(resetTransPwd.getApplyId(), resetTransPwd.getUpdatedby());
            HsAssert.hasText(taskId, BSRespCode.BS_TASK_STATUS_NOT_DEALLING, "任务状态不是办理中");
            taskService.updateTaskStatus(taskId, TaskStatus.COMPLETED.getCode());
            // -------------当选择审批通过时 调用用户中心 发验证码到客户手机-----------

            BsEntAllInfo allInfo = bsEntService.searchEntAllInfo(resetTransPwd.getEntCustId());
            HsAssert.notNull(allInfo, BSRespCode.BS_NOT_QUERY_DATA, "根据企业客户号["+resetTransPwd.getEntCustId()+"]查询UC获取企业信息失败");
            BsEntMainInfo mainInfo = allInfo.getMainInfo();
            HsAssert.notNull(mainInfo, BSRespCode.BS_NOT_QUERY_DATA, "根据企业客户号["+resetTransPwd.getEntCustId()+"]查询UC获取企业主要信息为空");
            // 当选择审批通过时
            if (ApprStatus.PASS.getCode() == resetTransPwd.getStatus()) {
                // 调用用户中心接口
                ucBsMobileService.sendAuthCodeSuccess(mainInfo.getEntResNo(),mainInfo.getEntCustType());
            }
            if (ApprStatus.REJECT.getCode() == resetTransPwd.getStatus()) {
                //{xxx}，您的密码重置办理业务申请未通过审核，请如需再次进行办理此业务，请登录平台官网重新提交申请。
                Map<String, String> contents = new HashMap<>();
                contents.put("{0}", mainInfo.getEntName());
                NoteBean noteBean = new NoteBean();
                noteBean.setMsgId(GuidAgent.getStringGuid(BizGroup.BS + bsConfig.getAppNode()));
                noteBean.setHsResNo(mainInfo.getEntResNo());
                noteBean.setCustName(mainInfo.getEntName());
                
                BsEntBaseInfo baseInfo = allInfo.getBaseInfo();
                HsAssert.notNull(baseInfo, BSRespCode.BS_NOT_QUERY_DATA, "根据企业客户号["+resetTransPwd.getEntCustId()+"]查询UC获取企业基本信息为空");
                if(baseInfo.getStartResType()!=null){ //服务公司为空
                    noteBean.setBuyResType(baseInfo.getStartResType());
                }
                noteBean.setMsgReceiver(new String[]{mainInfo.getContactPhone()});//收信手机号
                noteBean.setContent(contents);
                noteBean.setCustType(mainInfo.getEntCustType());
                noteBean.setBizType(String.valueOf(MsgBizType.MSG_BIZ_TYPE117.getCode()));
                noteBean.setPriority(Priority.HIGH.getPriority());//消息级别
                noteBean.setSender(BSConstants.SYS_OPERATOR);
                ntService.sendNote(noteBean);// 调用通知系统发短信
            }
        } catch (HsException hse) {
            // 写入系统日志
            SystemLog.error(bsConfig.getSysName(), "function:apprResetTranPwdApply", hse.getMessage(), hse);
            throw hse;// 抛出异常
        } catch (NoticeException ne) {
            throw new HsException(BSRespCode.BS_TRANS_PWD_SEND_FAIL, "重置交易密码发送短信失败，原因:" + ne.getMessage());
        } catch (Exception e) {
            // 写入系统日志
            SystemLog.error(bsConfig.getSysName(), "function:apprResetTranPwdApply",
                    "审批重置交易密码申请记录失败，参数[resetTransPwd]:" + resetTransPwd, e);

            throw new HsException(BSRespCode.BS_TRANS_PWD_DB_ERROR, "审批重置交易密码申请记录失败，原因:" + e.getMessage());
        }
    }
}
