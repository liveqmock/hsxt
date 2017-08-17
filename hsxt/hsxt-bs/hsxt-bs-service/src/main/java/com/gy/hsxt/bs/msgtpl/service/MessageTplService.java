/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.msgtpl.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsi.redis.client.api.RedisUtil;
import com.gy.hsxt.bs.api.msgtpl.IBSMessageTplService;
import com.gy.hsxt.bs.bean.msgtpl.MsgTemplate;
import com.gy.hsxt.bs.bean.msgtpl.MsgTemplateAppr;
import com.gy.hsxt.bs.common.PageContext;
import com.gy.hsxt.bs.common.enumtype.BSRespCode;
import com.gy.hsxt.bs.common.enumtype.msgtpl.MsgOptType;
import com.gy.hsxt.bs.common.enumtype.msgtpl.MsgTempStatus;
import com.gy.hsxt.bs.common.enumtype.msgtpl.MsgTplRedisKey;
import com.gy.hsxt.bs.common.enumtype.msgtpl.ReviewResult;
import com.gy.hsxt.bs.common.interfaces.ICommonService;
import com.gy.hsxt.bs.disconf.BsConfig;
import com.gy.hsxt.bs.msgtpl.mapper.MsgTemplateApprMapper;
import com.gy.hsxt.bs.msgtpl.mapper.MsgTemplateMapper;
import com.gy.hsxt.bs.task.bean.Task;
import com.gy.hsxt.bs.task.interfaces.ITaskService;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.constant.BizGroup;
import com.gy.hsxt.common.constant.TaskStatus;
import com.gy.hsxt.common.constant.TaskType;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.guid.GuidAgent;
import com.gy.hsxt.common.utils.HsAssert;
import com.gy.hsxt.common.utils.StringUtils;

/**
 * 消息模版实现类
 * 
 * @Package: com.gy.hsxt.bs.msgtpl.service
 * @ClassName: MessageTplService
 * @Description:
 * 
 * @author: kongsl
 * @date: 2016-3-8 上午10:33:04
 * @version V3.0.0
 */
@Service
public class MessageTplService implements IBSMessageTplService {
    /**
     * 业务服务私有配置参数
     */
    @Autowired
    private BsConfig bsConfig;

    /**
     * 消息模版mapeer
     */
    @Autowired
    MsgTemplateMapper msgTplMapper;

    /**
     * 模版审批mapper
     */
    @Autowired
    MsgTemplateApprMapper msgTplApprMapper;

    /** 任务Service **/
    @Autowired
    private ITaskService taskService;

    /** BS公共Service **/
    @Autowired
    private ICommonService commonService;

    /**
     * 缓存工具
     */
    @SuppressWarnings("rawtypes")
    @Resource
    RedisUtil fixRedisUtil;

    /**
     * 保存消息模版
     * 
     * @param msgTemplate
     *            消息模版信息
     * @param reqOptId
     *            申请操作员编号
     * @param reqOptName
     *            申请操作员名称
     * @throws HsException
     * @see com.gy.hsxt.bs.api.msgtpl.IBSMessageTplService#saveMessageTpl(com.gy.hsxt.bs.bean.msgtpl.MsgTemplate,
     *      java.lang.String, java.lang.String)
     */
    @Override
    @Transactional
    public void saveMessageTpl(MsgTemplate msgTemplate, String reqOptId, String reqOptName) throws HsException {
        // 记录业务日志
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                "保存消息模版,params[" + msgTemplate + ",reqOptId:" + reqOptId + ",reqOptName:" + reqOptName + "]");
        HsAssert.notNull(msgTemplate, BSRespCode.BS_PARAMS_NULL, "保存消息模版：实体参数对象为空");

        int resNum = 0;
        try
        {
            // 校验模版名称是否重复
            resNum = msgTplMapper.findSameTempName(msgTemplate.getTempName());
            HsAssert.isTrue(resNum <= 0, BSRespCode.BS_MSG_TEMP_NAME_EXSIT, "保存消息模版：模版名称重复");
            /**
             * 保存模版{
             */
            // 设置模版编号
            msgTemplate.setTempId(GuidAgent.getStringGuid(BizGroup.BS + bsConfig.getAppNode()));
            msgTemplate.setStatus(MsgTempStatus.ENABLE_REVIEW.getCode());
            msgTplMapper.insertMsgTpl(msgTemplate);
            /**
             * }
             */

            // 保存模版审批记录
            msgTemplate.setStatus(MsgTempStatus.ENABLE_REVIEW.getCode());
            saveTplAppr(msgTemplate, reqOptId, reqOptName);

            // 插入复核任务
            taskService.saveTask(new Task(msgTemplate.getTempId(), TaskType.TASK_TYPE101.getCode(), commonService
                    .getAreaPlatCustId(), commonService.getAreaPlatInfo().getPlatResNo(), commonService
                    .getAreaPlatInfo().getPlatNameCn()));
        }
        catch (HsException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                    BSRespCode.BS_SAVE_MSG_TPL_ERROR.getCode() + "保存消息模版异常,params[" + msgTemplate + ",reqOptId:"
                            + reqOptId + ",reqOptName:" + reqOptName + "]", e);
            throw new HsException(BSRespCode.BS_SAVE_MSG_TPL_ERROR.getCode(), "保存消息模版异常,params[" + msgTemplate
                    + ",reqOptId:" + reqOptId + ",reqOptName:" + reqOptName + "]" + e);
        }
    }

    /**
     * 保存模版审批记录
     * 
     * @param msgTemplate
     *            模版信息
     * @param reqOptId
     *            申请操作员编号
     * @param reqOptName
     *            申请操作员名称
     */
    private void saveTplAppr(MsgTemplate msgTemplate, String reqOptId, String reqOptName) {
        /**
         * 保存模版审批{
         */
        // 新增时，插入一条模版审批记录状态为待复核
        MsgTemplateAppr msgTemplateAppr = new MsgTemplateAppr(GuidAgent.getStringGuid(BizGroup.BS
                + bsConfig.getAppNode()),// 申请编号
                msgTemplate.getTempId(),// 模版编号
                msgTemplate.getStatus(),// 申请状态
                MsgTempStatus.WAIT_REVIEW.getCode(),// 模版状态，待复核
                reqOptId,// 申请操作员编号
                reqOptName// 申请操作员名称
        );
        // 设置申请编号
        msgTplApprMapper.insertMsgTplAppr(msgTemplateAppr);
    }

    /**
     * 修改消息模版
     * 
     * @param msgTemplate
     *            模版信息
     * @throws HsException
     * @see com.gy.hsxt.bs.api.msgtpl.IBSMessageTplService#modifyMessageTpl(com.gy.hsxt.bs.bean.msgtpl.MsgTemplate)
     */
    @Override
    public void modifyMessageTpl(MsgTemplate msgTemplate) throws HsException {
        // 记录业务日志
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                "修改消息模版,params[" + msgTemplate + "]");
        HsAssert.notNull(msgTemplate, BSRespCode.BS_PARAMS_NULL, "修改消息模版：实体参数对象为空");
        HsAssert.hasText(msgTemplate.getTempId(), BSRespCode.BS_PARAMS_NULL, "修改消息模版：模版编号参数为空");
        int resNum = 0;
        // 查询非当前模版名称
        resNum = msgTplMapper.findSameTempNameNotId(msgTemplate.getTempName(), msgTemplate.getTempId());
        HsAssert.isTrue(resNum <= 0, BSRespCode.BS_MSG_TEMP_NAME_EXSIT, "模版名称重复");
        // 校验模版类别是否已存在与非当前模版编号
        // resNum = msgTplMapper.findTplType(msgTemplate.getTempType(),
        // msgTemplate.getBizType(), msgTemplate.getTempId());
        // HsAssert.isTrue(resNum <= 0, BSRespCode.BS_MSG_TPL_EXSIT, "消息模版已存在");
        msgTplMapper.updateMsgTpl(msgTemplate);
    }

    /**
     * 查询消息模版列表
     * 
     * @param tplType
     *            模版类型
     * @param tplName
     *            模版名称
     * @param useCustType
     *            适用客户类型
     * @param tplStatus
     *            模版状态
     * @param page
     *            分页信息
     * @return 模版列表
     * @throws HsException
     * @see com.gy.hsxt.bs.api.msgtpl.IBSMessageTplService#getMessageTplList(java.lang.Integer,
     *      java.lang.String, java.lang.Integer, java.lang.Integer,
     *      com.gy.hsxt.common.bean.Page)
     */
    @Override
    public PageData<MsgTemplate> getMessageTplList(Integer tplType, String tplName, Integer useCustType,
            Integer tplStatus, Page page) throws HsException {
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                "查询消息模版列表,params[tplType:" + tplType + ",tplName" + tplName + ",useCustType:" + useCustType
                        + ",tplStatus:" + tplStatus + "]");
        HsAssert.notNull(tplType, BSRespCode.BS_PARAMS_NULL, "查询消息模版列表：模版类型参数为空");
        // 分页数据
        PageData<MsgTemplate> msgTplPageList = null;
        // 分页参数为空
        HsAssert.notNull(page, BSRespCode.BS_PAGE_PARAM_NULL, "查询消息模版列表：分页条件参数为空");
        // 设置分页信息
        PageContext.setPage(page);
        // 执行查询
        List<MsgTemplate> msgTplList = msgTplMapper.findMessageTplListPage(tplType, tplName, useCustType, tplStatus);
        if (msgTplList != null && msgTplList.size() > 0)
        {
            // 为公用分页查询设置查询结果集
            msgTplPageList = new PageData<MsgTemplate>(page.getCount(), msgTplList);
        }
        return msgTplPageList;
    }

    /**
     * 启用/停用模版
     * 
     * @param tempId
     *            模版编号
     * @param optType
     *            操作类型
     * @param reqOptName
     *            申请操作员名称
     * @throws HsException
     * @see com.gy.hsxt.bs.api.msgtpl.IBSMessageTplService#startOrStopTpl(String,
     *      Integer, String, String)
     */
    @Override
    @Transactional
    public void startOrStopTpl(String tempId, Integer optType, String reqOptId, String reqOptName) throws HsException {
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                "启用/停用模版,params[tempId:" + tempId + ",optType:" + optType + ",reqOptId:" + reqOptId + ",reqOptName:"
                        + reqOptName + "]");
        HsAssert.hasText(tempId, BSRespCode.BS_PARAMS_NULL, "启用/停用模版：模版编号参数为空");
        MsgTemplate msgTpl = null;
        // 如果点击的是启用，插入一条待启用的审批记录
        if (MsgOptType.START.getCode() == optType)
        {
            try
            {
                msgTpl = msgTplMapper.findMessageTplInfo(tempId);
                HsAssert.notNull(msgTpl, BSRespCode.BS_NOT_QUERY_DATA, "启用模版：未查询到模版编号对应的记录,tempId=" + tempId);
                HsAssert.isTrue(msgTpl.getStatus() != 2, BSRespCode.BS_MSG_TPL_EXSIT_USING,
                        "启用模版：已存在相同类型的模板，不允许重复提交模板启用");
                // 插入复核任务
                taskService.saveTask(new Task(tempId, TaskType.TASK_TYPE101.getCode(), commonService
                        .getAreaPlatCustId(), commonService.getAreaPlatInfo().getPlatResNo(), commonService
                        .getAreaPlatInfo().getPlatNameCn()));

                // 更新模版状态为启用待复核
                msgTplMapper.updateMsgTplStatus(MsgTempStatus.ENABLE_REVIEW.getCode(), tempId);

                // 保存模版审批记录
                msgTpl.setStatus(MsgTempStatus.ENABLE_REVIEW.getCode());
                saveTplAppr(msgTpl, reqOptId, reqOptName);
            }
            catch (HsException e)
            {
                throw e;
            }
            catch (Exception e)
            {
                SystemLog.error(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                        BSRespCode.BS_MSG_TPL_ENABLE_ERROR.getCode() + "启用模版异常,params[tempId:" + tempId + ",optType:"
                                + optType + ",reqOptId:" + reqOptId + ",reqOptName:" + reqOptName + "]", e);
                throw new HsException(BSRespCode.BS_MSG_TPL_ENABLE_ERROR.getCode(), "启用模版异常,params[tempId:" + tempId
                        + ",optType:" + optType + ",reqOptId:" + reqOptId + ",reqOptName:" + reqOptName + "]" + e);
            }
        }
        else
        // 如果点击的是停用，插入一条已启用的审批记录
        if (MsgOptType.STOP.getCode() == optType)
        {
            try
            {
                msgTpl = msgTplMapper.findMessageTplInfo(tempId);
                // 插入复核任务
                taskService.saveTask(new Task(tempId, TaskType.TASK_TYPE101.getCode(), commonService
                        .getAreaPlatCustId(), commonService.getAreaPlatInfo().getPlatResNo(), commonService
                        .getAreaPlatInfo().getPlatNameCn()));

                // 更新模版状态为停用待复核
                msgTplMapper.updateMsgTplStatus(MsgTempStatus.UNABLE_REVIEW.getCode(), tempId);
                // 保存模版审批记录
                msgTpl.setStatus(MsgTempStatus.UNABLE_REVIEW.getCode());
                saveTplAppr(msgTpl, reqOptId, reqOptName);
            }
            catch (HsException e)
            {
                throw e;
            }
            catch (Exception e)
            {
                SystemLog.error(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                        BSRespCode.BS_MSG_TPL_UNABLE_ERROR.getCode() + "停用模版异常,params[tempId:" + tempId + ",optType:"
                                + optType + ",reqOptId:" + reqOptId + ",reqOptName:" + reqOptName + "]", e);
                throw new HsException(BSRespCode.BS_MSG_TPL_UNABLE_ERROR.getCode(), "停用模版异常,params[tempId:" + tempId
                        + ",optType:" + optType + ",reqOptId:" + reqOptId + ",reqOptName:" + reqOptName + "]" + e);
            }
        }
    }

    /**
     * 查询模版详情
     * 
     * @param tempId
     *            模版编号
     * @return 模版详情
     * @throws HsException
     * @see com.gy.hsxt.bs.api.msgtpl.IBSMessageTplService#getMessageTplInfo(java.lang.String)
     */
    @Override
    public MsgTemplate getMessageTplInfo(String tempId) throws HsException {
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                "查询模版详情,params[tempId:" + tempId + "]");
        HsAssert.hasText(tempId, BSRespCode.BS_PARAMS_NULL, "查询模版详情：模版编号参数为空");
        MsgTemplate msgTemplate = null;
        msgTemplate = msgTplMapper.findMessageTplInfo(tempId);
        if (msgTemplate == null)
        {
            msgTemplate = new MsgTemplate();
        }

        return msgTemplate;
    }

    /**
     * 查询消息模版审批列表
     * 
     * @param exeCustId
     *            经办人编号
     * @param tplType
     *            模版类型
     * @param tplName
     *            模版名称
     * @param useCustType
     *            适用客户类型
     * @param page
     *            分页信息
     * @return 模版审批列表
     * @throws HsException
     * @see com.gy.hsxt.bs.api.msgtpl.IBSMessageTplService#getMessageTplApprList(String,
     *      Integer, String, Integer, Page)
     */
    @Override
    public PageData<MsgTemplateAppr> getMessageTplApprList(String exeCustId, Integer tplType, String tplName,
            Integer useCustType, Page page) throws HsException {
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                "查询消息模版审批列表,params[exeCustId:" + exeCustId + ",tplType:" + tplType + ",tplName:" + tplName
                        + ",useCustType:" + useCustType + "]");
        HsAssert.notNull(exeCustId, BSRespCode.BS_PARAMS_NULL, "查询消息模版审批列表：经办人编号参数为空");
        HsAssert.notNull(tplType, BSRespCode.BS_PARAMS_NULL, "查询消息模版审批列表：模版类型参数为空");
        // 分页数据
        PageData<MsgTemplateAppr> msgTplApprPageList = null;
        // 分页参数为空
        HsAssert.notNull(page, BSRespCode.BS_PAGE_PARAM_NULL, "查询消息模版审批列表：分页条件参数为空");
        // 设置分页信息
        PageContext.setPage(page);
        if (StringUtils.isNotBlank(exeCustId))
        {
            // 执行查询
            List<MsgTemplateAppr> msgTplApprList = msgTplApprMapper.findMessageTplApprListPage(exeCustId, tplType,
                    tplName, useCustType);
            if (msgTplApprList != null && msgTplApprList.size() > 0)
            {
                // 为公用分页查询设置查询结果集
                msgTplApprPageList = new PageData<MsgTemplateAppr>(page.getCount(), msgTplApprList);
            }
            else
            {
                msgTplApprPageList = new PageData<MsgTemplateAppr>();
            }
        }
        else
        {
            msgTplApprPageList = new PageData<MsgTemplateAppr>();
        }
        return msgTplApprPageList;
    }

    /**
     * 查询审批历史列表
     * 
     * @param tempId
     *            模版编号
     * @param page
     *            分页信息
     * @return 审批历史列表
     * @throws HsException
     * @see com.gy.hsxt.bs.api.msgtpl.IBSMessageTplService#getMsgTplApprHisList(java.lang.String,
     *      com.gy.hsxt.common.bean.Page)
     */
    @Override
    public PageData<MsgTemplateAppr> getMsgTplApprHisList(String tempId, Page page) throws HsException {
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                "查询消息模版审批历史列表,params[tempId:" + tempId + "]");
        HsAssert.hasText(tempId, BSRespCode.BS_PARAMS_NULL, "查询消息模版审批历史列表：模版编号参数为空");
        // 分页数据
        PageData<MsgTemplateAppr> msgTplApprPageList = null;
        // 分页参数为空
        HsAssert.notNull(page, BSRespCode.BS_PAGE_PARAM_NULL, "查询消息模版审批历史列表：分页条件参数为空");
        // 设置分页信息
        PageContext.setPage(page);
        // 执行查询
        List<MsgTemplateAppr> msgTplApprList = msgTplApprMapper.findApprHisListPage(tempId);
        if (msgTplApprList != null && msgTplApprList.size() > 0)
        {
            // 为公用分页查询设置查询结果集
            msgTplApprPageList = new PageData<MsgTemplateAppr>(page.getCount(), msgTplApprList);
        }
        else
        {
            msgTplApprPageList = new PageData<MsgTemplateAppr>();
        }
        return msgTplApprPageList;
    }

    /**
     * 复核模版
     * 
     * @param templateAppr
     *            复核信息
     * @param reviewRes
     *            复核结果
     * @throws HsException
     * @see com.gy.hsxt.bs.api.msgtpl.IBSMessageTplService#reviewTemplate(com.gy.hsxt.bs.bean.msgtpl.MsgTemplateAppr,
     *      java.lang.Integer)
     */
    @SuppressWarnings("unchecked")
    @Override
    @Transactional
    public void reviewTemplate(MsgTemplateAppr templateAppr, Integer reviewRes) throws HsException {
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                "复核消息模版,params[" + templateAppr + ",reviewRes:" + reviewRes + "]");
        HsAssert.hasText(templateAppr.getApplyId(), BSRespCode.BS_PARAMS_NULL, "复核模版：申请编号参数为空");
        HsAssert.notNull(reviewRes, BSRespCode.BS_PARAMS_NULL, "复核模版：复核结果参数为空");

        String taskId = "";
        String tplCatchKey = "";
        try
        {
            // 查询模版详情
            MsgTemplateAppr msgTplAppr = msgTplApprMapper.findMsgTplApprInfo(templateAppr.getApplyId());
            HsAssert.notNull(msgTplAppr, BSRespCode.BS_NOT_QUERY_DATA, "复核模版：未查询到消息模版审批记录");
            MsgTemplate msgTpl = msgTplMapper.findMessageTplInfo(templateAppr.getTempId());
            HsAssert.notNull(msgTpl, BSRespCode.BS_NOT_QUERY_DATA, "复核模版：未查询到消息模版记录");

            tplCatchKey = msgTpl.getTempType() + "_" + msgTpl.getCustType() + "_" + msgTpl.getBizType() + "_"
                    + (StringUtils.isNotBlank(msgTpl.getBuyResType()) ? msgTpl.getBuyResType() : "");
            /**
             * 更新任务单状态{
             */
            // 查询任务ID
            taskId = taskService.getSrcTask(msgTplAppr.getTempId(), templateAppr.getApprOptId());
            HsAssert.hasText(taskId, BSRespCode.BS_TASK_STATUS_NOT_DEALLING, "复核模版：任务状态不是办理中");
            // 修改消息模版复核任务完成
            taskService.updateTaskStatus(taskId, TaskStatus.COMPLETED.getCode());
            /**
             * }
             */

            // 复核通过
            if (ReviewResult.PASS.getCode() == reviewRes)
            {
                // 如果是启用复核通过，更新模版状态待启用为已启用
                if (MsgTempStatus.ENABLE_REVIEW.getCode() == msgTpl.getStatus())
                {
                    /**
                     * 校验是否存在已启用的模版{
                     */
                    // 查询已启用的模版
                    int enableNum = msgTplMapper.findEnabledTplNum(msgTpl.getBizType(), msgTpl.getCustType(), msgTpl
                            .getBuyResType(), msgTpl.getTempId());
                    HsAssert.isTrue(enableNum <= 0, BSRespCode.BS_MSG_TPL_EXSIT_USING, "复核消息模版：已存在相同类型的模板，不允许重复提交模板启用");
                    /**
                     * }
                     */
                    // 更新模版状态
                    msgTplMapper.updateMsgTplStatus(MsgTempStatus.ENABLED.getCode(), msgTplAppr.getTempId());
                    // 更新模版复核状态
                    msgTplApprMapper.updateApprStatus(ReviewResult.PASS.getCode(), templateAppr.getApprOptId(),
                            templateAppr.getApprOptName(), templateAppr.getApprRemark(), templateAppr.getApplyId());

                    /**
                     * 更新模版缓存{
                     */
                    fixRedisUtil.add(MsgTplRedisKey.MSG_TEMPLATE, tplCatchKey, msgTpl.toString());
                    /**
                     * }
                     */
                }

                // 如果是停用复核通过，更新模版状态启用为待启用
                if (MsgTempStatus.UNABLE_REVIEW.getCode() == msgTpl.getStatus())
                {
                    // 更新模版状态
                    msgTplMapper.updateMsgTplStatus(MsgTempStatus.WAIT_ENABLE.getCode(), msgTplAppr.getTempId());
                    // 更新模版复核状态
                    msgTplApprMapper.updateApprStatus(ReviewResult.PASS.getCode(), templateAppr.getApprOptId(),
                            templateAppr.getApprOptName(), templateAppr.getApprRemark(), templateAppr.getApplyId());

                    /**
                     * 更新模版缓存{
                     */
                    fixRedisUtil.add(MsgTplRedisKey.MSG_TEMPLATE, tplCatchKey, "");
                    /**
                     * }
                     */
                }
            }

            // 复核拒绝
            if (ReviewResult.REJECTED.getCode() == reviewRes)
            {
                // 如果是启用待复核被驳回，还原状态为待启用
                if (MsgTempStatus.ENABLE_REVIEW.getCode() == msgTpl.getStatus())
                {
                    // 更新模版状态
                    msgTplMapper.updateMsgTplStatus(MsgTempStatus.WAIT_ENABLE.getCode(), msgTplAppr.getTempId());
                    // 更新模版复核状态
                    msgTplApprMapper.updateApprStatus(ReviewResult.REJECTED.getCode(), templateAppr.getApprOptId(),
                            templateAppr.getApprOptName(), templateAppr.getApprRemark(), templateAppr.getApplyId());
                }
                // 如果是停用待复核被驳回，还原状态为启用
                if (MsgTempStatus.UNABLE_REVIEW.getCode() == msgTpl.getStatus())
                {
                    // 更新模版状态
                    msgTplMapper.updateMsgTplStatus(MsgTempStatus.ENABLED.getCode(), msgTplAppr.getTempId());
                    // 更新模版复核状态
                    msgTplApprMapper.updateApprStatus(ReviewResult.REJECTED.getCode(), templateAppr.getApprOptId(),
                            templateAppr.getApprOptName(), templateAppr.getApprRemark(), templateAppr.getApplyId());
                }
            }
        }
        catch (HsException e)
        {
            SystemLog.error(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(), e
                    .getErrorCode()
                    + ":" + e.getMessage(), e);
            throw e;
        }
        catch (Exception e)
        {
            SystemLog.error(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                    BSRespCode.BS_REVIEW_MSG_TPL_ERROR.getCode() + "复核模版异常,params[" + templateAppr + ",reviewRes:"
                            + reviewRes + "]", e);
            throw new HsException(BSRespCode.BS_REVIEW_MSG_TPL_ERROR.getCode(), "复核模版异常,params[" + templateAppr
                    + ",reviewRes:" + reviewRes + "]" + e);
        }
    }

    /**
     * 获取开启的模版到缓存
     * 
     * @param tempType
     *            模版类型
     * @param custType
     *            适用客户类型
     * @param bizType
     *            适用业务类型
     * @param buyResType
     *            启用资源类型
     * @return JSON模版字段串
     * @throws HsException
     * @see com.gy.hsxt.bs.api.msgtpl.IBSMessageTplService#getEnabledMessageTplToRedis(java.lang.Integer,
     *      java.lang.Integer, java.lang.Integer, java.lang.Integer)
     */
    @SuppressWarnings("unchecked")
    @Override
    public String getEnabledMessageTplToRedis(Integer tempType, Integer custType, Integer bizType, Integer buyResType)
            throws HsException {
        SystemLog.debug(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                "获取开启的模版到缓存,params[tempType:" + tempType + ",custType:" + custType + ",bizType:" + bizType
                        + ",buyResType:" + buyResType + "]");
        HsAssert.notNull(tempType, BSRespCode.BS_PARAMS_NULL, "获取开启的模版到缓存：模版类型参数为空");
        HsAssert.notNull(custType, BSRespCode.BS_PARAMS_NULL, "获取开启的模版到缓存：模版适用客户类型参数为空");
        HsAssert.notNull(bizType, BSRespCode.BS_PARAMS_NULL, "获取开启的模版到缓存：模版适用业务类型参数为空");
        MsgTemplate msgTpl = msgTplMapper.findEnabledMsgTplInfo(tempType, bizType, custType, buyResType);

        if (msgTpl != null)
        {
            String tplCatchKey = msgTpl.getTempType() + "_" + msgTpl.getCustType() + "_" + msgTpl.getBizType() + "_"
                    + (StringUtils.isNotBlank(msgTpl.getBuyResType()) ? msgTpl.getBuyResType() : "");

            /**
             * 更新模版缓存{
             */
            fixRedisUtil.add(MsgTplRedisKey.MSG_TEMPLATE, tplCatchKey, msgTpl.toString());
            /**
             * }
             */
            return msgTpl.toString();
        }
        return "";
    }
}
