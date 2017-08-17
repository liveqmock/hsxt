/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.bs.apply.service;

import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.bs.apply.interfaces.ITemplateService;
import com.gy.hsxt.bs.apply.mapper.TemplateApprMapper;
import com.gy.hsxt.bs.apply.mapper.TempletMapper;
import com.gy.hsxt.bs.bean.apply.TemplateAppr;
import com.gy.hsxt.bs.bean.apply.Templet;
import com.gy.hsxt.bs.bean.apply.TempletQuery;
import com.gy.hsxt.bs.common.PageContext;
import com.gy.hsxt.bs.common.enumtype.BSRespCode;
import com.gy.hsxt.bs.common.enumtype.apply.ResType;
import com.gy.hsxt.bs.common.enumtype.apply.TempletStatus;
import com.gy.hsxt.bs.common.interfaces.ICommonService;
import com.gy.hsxt.bs.disconf.BsConfig;
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
import com.gy.hsxt.common.utils.HsResNoUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 模版业务实现
 *
 * @Package : com.gy.hsxt.bs.apply.service
 * @ClassName : TemplateService
 * @Description : 模版业务实现
 * @Author : chenm
 * @Date : 2016/3/14 16:27
 * @Version V3.0.0.0
 */
@Service
public class TemplateService implements ITemplateService {
    /**
     * bs基础数据
     */
    @Resource
    private BsConfig bsConfig;

    /**
     * 模版Mapper
     */
    @Resource
    private TempletMapper templetMapper;

    /**
     * 模版审批Mapper
     */
    @Resource
    private TemplateApprMapper templateApprMapper;

    /**
     * 公共数据
     */
    @Resource
    private ICommonService commonService;

    /**
     * 工单业务接口
     */
    @Resource
    private ITaskService taskService;

    /**
     * 创建模板
     *
     * @param templet 模板
     * @throws HsException
     */
    @Override
    @Transactional
    public void createTemplet(Templet templet) throws HsException {
        HsAssert.notNull(templet, BSRespCode.BS_PARAMS_NULL, "模版[templet]不能为null");
        HsAssert.hasText(templet.getTempletName(), BSRespCode.BS_PARAMS_EMPTY, "模版名称[templetName]为空");
        HsAssert.notNull(templet.getCustType(), BSRespCode.BS_PARAMS_NULL, "适合客户类型[custType]为null");
        if (HsResNoUtils.isMemberResNo(templet.getCustType()) || HsResNoUtils.isTrustResNo(templet.getCustType())) {//成员企业和托管企业
            HsAssert.notNull(templet.getResType(), BSRespCode.BS_PARAMS_NULL, "启用资源类型[resType]为null");
            HsAssert.isTrue(ResType.checkResType(templet.getCustType(), templet.getResType()), BSRespCode.BS_PARAMS_TYPE_ERROR, "启用资源类型[resType]错误");
        }
        HsAssert.hasText(templet.getCreatedBy(), BSRespCode.BS_PARAMS_EMPTY, "创建者客户号[createdBy]为空");

        try {
            templet.setTempletId(GuidAgent.getStringGuid(BizGroup.BS + bsConfig.getAppNode()));
            templet.setStatus(1);
            //审批状态 0-已启用 1-待启用 2-待启用复核 3-待停用复核
            templet.setApprStatus(1);
            templetMapper.createTemplet(templet);
        } catch (Exception e) {
            SystemLog.error(this.getClass().getName(), "createTemplet", BSRespCode.BS_CRE_TPL_SAVE_ERROR.getCode() + ":保存模板失败[param=" + templet + "]", e);
            throw new HsException(BSRespCode.BS_TEMPLATE_DB_ERROR, "保存模板失败[param=" + templet + "]" + e);
        }
    }

    /**
     * 修改模板
     *
     * @param templet 模板
     * @throws HsException
     */
    @Override
    @Transactional
    public void modifyTemplet(Templet templet) throws HsException {
        HsAssert.notNull(templet, BSRespCode.BS_PARAMS_NULL, "模版[templet]不能为null");
        HsAssert.hasText(templet.getTempletId(), BSRespCode.BS_PARAMS_EMPTY, "模版ID[templetId]为空");
        HsAssert.hasText(templet.getTempletName(), BSRespCode.BS_PARAMS_EMPTY, "模版名称[templetName]为空");
        HsAssert.notNull(templet.getCustType(), BSRespCode.BS_PARAMS_NULL, "适合客户类型[custType]为null");
        if (HsResNoUtils.isMemberResNo(templet.getCustType()) || HsResNoUtils.isTrustResNo(templet.getCustType())) {//成员企业和托管企业
            HsAssert.notNull(templet.getResType(), BSRespCode.BS_PARAMS_NULL, "启用资源类型[resType]为null");
            HsAssert.isTrue(ResType.checkResType(templet.getCustType(), templet.getResType()), BSRespCode.BS_PARAMS_TYPE_ERROR, "启用资源类型[resType]错误");
        }
        HsAssert.hasText(templet.getUpdatedBy(), BSRespCode.BS_PARAMS_EMPTY, "更新者客户号[updatedBy]为空");
        try {
            templetMapper.updateTemplet(templet);
        } catch (Exception e) {
            SystemLog.error(this.getClass().getName(), "modifyTemplet", BSRespCode.BS_CRE_TPL_SAVE_ERROR.getCode() + ":保存模板失败[param=" + templet + "]", e);
            throw new HsException(BSRespCode.BS_TEMPLATE_DB_ERROR, "保存模板失败[param=" + templet + "]" + e.getMessage());
        }
    }

    /**
     * 查看模板详情
     *
     * @param templetId 合同模板ID
     * @return 返回合同模板详情，没有则返回null
     * @throws HsException
     */
    @Override
    public Templet queryTempletById(String templetId) throws HsException {
        HsAssert.hasText(templetId, BSRespCode.BS_PARAMS_EMPTY, "模版ID[templetId]不能为空");
        try {
            Templet templet = templetMapper.queryTempletById(templetId);
            if (templet != null) {
                List<TemplateAppr> templateApprs = templateApprMapper.selectBeanList(templetId);
                templet.setTemplateApprs(templateApprs);
            }
            return templet;
        } catch (Exception e) {
            SystemLog.error(this.getClass().getName(), "queryTempletById", "查看模板详情失败[param=" + templetId + "]", e);
            throw new HsException(BSRespCode.BS_TEMPLATE_DB_ERROR, "查看模板详情失败,原因：" + e.getMessage());
        }
    }

    /**
     * 分页查询模板
     *
     * @param query 模板名称
     * @param page  分页信息 必填
     * @return 返回模板列表，没有则返回null
     * @throws HsException
     */
    @Override
    public PageData<Templet> queryTempletList(TempletQuery query, Page page) throws HsException {
        // 写入系统日志
        SystemLog.debug(bsConfig.getSysName(), "function:queryTempletList", "模版查询实体[query]：" + query);
        HsAssert.notNull(page, BSRespCode.BS_PARAMS_NULL, "分页对象[page]为空");

        try {
            PageContext.setPage(page);
            List<Templet> tplList = templetMapper.queryTempletListPage(query);
            return PageData.bulid(page.getCount(), tplList);
        } catch (Exception e) {
            SystemLog.error(this.getClass().getName(), "queryTempletList", "分页查询模板失败[param=" + query + "]", e);
            throw new HsException(BSRespCode.BS_TEMPLATE_DB_ERROR, "分页查询模板失败,原因：" + e.getMessage());
        }
    }

    /**
     * 分页查询待复核模板
     *
     * @param query 模板查询实体
     * @param page  分页信息 必填
     * @return 返回模板列表，没有则返回null
     * @throws HsException
     */
    @Override
    public PageData<Templet> queryTemplet4Appr(TempletQuery query, Page page) throws HsException {
        // 写入系统日志
        SystemLog.debug(bsConfig.getSysName(), "function:queryTemplet4Appr", "模版查询实体[query]：" + query);
        HsAssert.notNull(page, BSRespCode.BS_PARAMS_NULL, "分页对象[page]为空");
        HsAssert.hasText(query.getOptCustId(), BSRespCode.BS_PARAMS_EMPTY, "操作者客户号[optCustId]为空");
        try {
            query.setTaskStatus(TaskStatus.DEALLING.getCode());
            query.setTaskType(TaskType.TASK_TYPE212.getCode());
            PageContext.setPage(page);
            List<Templet> tplList = templetMapper.queryTempletTaskListPage(query);
            return PageData.bulid(page.getCount(), tplList);
        } catch (Exception e) {
            SystemLog.error(this.getClass().getName(), "queryTemplet4Appr", "分页查询待复核模板失败[param=" + query + "]", e);
            throw new HsException(BSRespCode.BS_TEMPLATE_DB_ERROR, "分页查询待复核模板失败,原因：" + e.getMessage());
        }
    }

    /**
     * 复核模板
     *
     * @param templateAppr 审批内容
     * @throws HsException
     */
    @Override
    @Transactional
    public void apprTemplet(TemplateAppr templateAppr) throws HsException {
        HsAssert.notNull(templateAppr, BSRespCode.BS_PARAMS_NULL, "审批内容[templateAppr]为null");
        HsAssert.hasText(templateAppr.getTempletId(), BSRespCode.BS_PARAMS_EMPTY, "申请ID[templetId]不能为空");
        HsAssert.hasText(templateAppr.getApprOperator(), BSRespCode.BS_PARAMS_EMPTY, "操作[apprOperator]不能为空");
        HsAssert.isTrue(templateAppr.getApprStatus() != null && templateAppr.getApprStatus() > 1, BSRespCode.BS_PARAMS_TYPE_ERROR, "操作[apprStatus]类型错误，必须是待审批类型");
        boolean result = templateAppr.getApprResult() == 1 || templateAppr.getApprResult() == 2;
        HsAssert.isTrue(result, BSRespCode.BS_PARAMS_TYPE_ERROR, "审批结果[appResult]类型错误");
        try {
            Templet templet = templetMapper.queryTempletById(templateAppr.getTempletId());
            Integer status;
            if (TempletStatus.ENABLE_APPR.ordinal() == templet.getApprStatus()) {//开启审核
                status = templateAppr.getApprResult() == 1 ? TempletStatus.ENABLED.ordinal() : TempletStatus.STOP.ordinal();
            } else {//停止审核
                status = templateAppr.getApprResult() == 1 ? TempletStatus.STOP.ordinal() : TempletStatus.ENABLED.ordinal();
            }
            //待开启状态审核通过的情况下，检查是否存在同类型的处于启用状态的模版
            if (templateAppr.getApprResult() == 1 && TempletStatus.ENABLE_APPR.ordinal() == templet.getApprStatus()) {
                // 查询同一个类型的当前已启用模板ID
                TempletQuery query = new TempletQuery();
                query.setTempletType(templet.getTempletType());
                query.setResType(templet.getResType());
                query.setCustType(templet.getCustType());
                Templet currentTpl = templetMapper.queryCurrentTplByQuery(query);
                HsAssert.isNull(currentTpl, BSRespCode.BS_TEMPLATE_EXIST_WAIT_DEAL, "存在同类型处于已启用状态或停用待复核状态的模版");
            }
            //修改模版状态
            templet.setStatus(status);
            templet.setApprStatus(status);
            templet.setUpdatedBy(templateAppr.getApprOperator());
            templet.setUpdatedName(templateAppr.getApprName());

            templetMapper.updateTplStatus(templet);
            //修改审核记录
            templateApprMapper.updateBean(templateAppr);
            // 审批操作完成时，更新工单状态
            String taskId = taskService.getSrcTask(templet.getTempletId(), templateAppr.getApprOperator());
            HsAssert.hasText(taskId, BSRespCode.BS_TASK_STATUS_NOT_DEALLING, "任务[" + taskId + "]状态不是办理中");
            taskService.updateTaskStatus(taskId, TaskStatus.COMPLETED.getCode());

        } catch (HsException hse) {
            throw hse;
        } catch (Exception e) {
            SystemLog.error(this.getClass().getName(), "apprTemplet", "审批模板失败[param=" + templateAppr + "]", e);
            throw new HsException(BSRespCode.BS_TEMPLATE_DB_ERROR, "审批模板失败,原因：" + e.getMessage());
        }
    }

    /**
     * 启用模板
     *
     * @param templetId 模板ID 必填
     * @param operator  操作员客户号
     * @throws HsException
     */
    @Override
    @Transactional
    public void enableTemplet(String templetId, String operator) throws HsException {
        HsAssert.hasText(templetId, BSRespCode.BS_PARAMS_EMPTY, "模版ID[templetId]不能为空");
        HsAssert.hasText(operator, BSRespCode.BS_PARAMS_EMPTY, "模版ID[operator]不能为空");

        try {
            //修改模版状态
            Templet templet = new Templet();
            templet.setApprStatus(TempletStatus.ENABLE_APPR.ordinal());
            templet.setTempletId(templetId);
            templet.setUpdatedBy(operator);

            templetMapper.updateTplStatus(templet);
            //保存审核记录
            TemplateAppr appr = new TemplateAppr();
            String applyId = GuidAgent.getStringGuid(BizGroup.BS + bsConfig.getAppNode());
            appr.setApplyId(applyId);
            appr.setTempletId(templetId);
            appr.setApprStatus(TempletStatus.ENABLE_APPR.ordinal());
            appr.setApprResult(0);
            appr.setReqOperator(operator);
            appr.setReqName(operator);

            templateApprMapper.insertBean(appr);

            String platCustId = commonService.getAreaPlatCustId();
            HsAssert.hasText(platCustId, BSRespCode.BS_QUERY_AREA_PLAT_CUST_ID_ERROR, "查询地区平台客户号失败");
            Task task = new Task(templetId, TaskType.TASK_TYPE212.getCode(), platCustId);
            taskService.saveTask(task);
        } catch (Exception e) {
            SystemLog.error(this.getClass().getName(), "enableTemplet", "启用模板失败[templetId=" + templetId + "]", e);
            throw new HsException(BSRespCode.BS_TEMPLATE_DB_ERROR, "启用模板失败[templetId=" + templetId + "]" + e.getMessage());
        }
    }

    /**
     * 停用模板
     *
     * @param templetId 模板ID 必填
     * @param operator  操作员客户号
     * @throws HsException
     */
    @Override
    @Transactional
    public void disableTemplet(String templetId, String operator) throws HsException {
        HsAssert.hasText(templetId, BSRespCode.BS_PARAMS_EMPTY, "模版ID[templetId]不能为空");
        HsAssert.hasText(operator, BSRespCode.BS_PARAMS_EMPTY, "模版ID[operator]不能为空");
        try {
            //修改模版状态
            Templet templet = new Templet();
            templet.setApprStatus(TempletStatus.STOP_APPR.ordinal());
            templet.setTempletId(templetId);
            templet.setUpdatedBy(operator);

            templetMapper.updateTplStatus(templet);
            //保存审核记录
            TemplateAppr appr = new TemplateAppr();
            String applyId = GuidAgent.getStringGuid(BizGroup.BS + bsConfig.getAppNode());
            appr.setApplyId(applyId);
            appr.setTempletId(templetId);
            appr.setApprStatus(TempletStatus.STOP_APPR.ordinal());
            appr.setApprResult(0);
            appr.setReqOperator(operator);
            appr.setReqName(operator);

            templateApprMapper.insertBean(appr);

            String platCustId = commonService.getAreaPlatCustId();
            HsAssert.hasText(platCustId, BSRespCode.BS_QUERY_AREA_PLAT_CUST_ID_ERROR, "查询地区平台客户号失败");
            Task task = new Task(templetId, TaskType.TASK_TYPE212.getCode(), platCustId);
            taskService.saveTask(task);
        } catch (Exception e) {
            SystemLog.error(this.getClass().getName(), "disableTemplet", "停用模板失败[templetId=" + templetId + "]", e);
            throw new HsException(BSRespCode.BS_CRE_TPL_DISABLE_ERROR, "停用模板失败,原因:" + e.getMessage());
        }
    }

    /**
     * 查询当前生效模板
     *
     * @param templetQuery 模板查询实体
     * @return 当前模板
     */
    @Override
    public Templet queryCurrentTplByQuery(TempletQuery templetQuery) throws HsException {
        // 写入系统日志
        SystemLog.debug(bsConfig.getSysName(), "function:queryCurrentTplByQuery", "模版查询实体[templetQuery]：" + templetQuery);
        HsAssert.notNull(templetQuery.getCustType(), BSRespCode.BS_PARAMS_NULL, "模版适用类型[custType]为null");
        HsAssert.notNull(templetQuery.getTempletType(), BSRespCode.BS_PARAMS_NULL, "模版类型[templetType]为null");
        try {
            return templetMapper.queryCurrentTplByQuery(templetQuery);
        } catch (Exception e) {
            SystemLog.error(this.getClass().getName(), "queryCurrentTplByQuery", "查询当前生效模板失败[templetQuery=" + templetQuery + "]", e);
            throw new HsException(BSRespCode.BS_CRE_TPL_DISABLE_ERROR, "查询当前生效模板失败,原因:" + e.getMessage());
        }
    }

    /**
     * 查询模版最新审核记录
     *
     * @param templetId 模版ID
     * @return TemplateAppr
     * @throws HsException
     */
    @Override
    public TemplateAppr queryLastTemplateAppr(String templetId) throws HsException {
        // 写入系统日志
        SystemLog.debug(bsConfig.getSysName(), "function:queryLastTemplateAppr", "模版ID[templetId]：" + templetId);
        HsAssert.notNull(templetId, BSRespCode.BS_PARAMS_EMPTY, "模版ID[templetId]为空");
        try {
            TemplateAppr templateAppr =  templateApprMapper.selectLastBeanByTemplateId(templetId);
            if (templateAppr != null) {
                Templet templet = templetMapper.queryTempletById(templetId);
                templateAppr.setTemplet(templet);
            }
            return templateAppr;
        } catch (Exception e) {
            SystemLog.error(this.getClass().getName(), "queryLastTemplateAppr", "查询模版最新审核记录失败[templetId=" + templetId + "]", e);
            throw new HsException(BSRespCode.BS_CRE_TPL_DISABLE_ERROR, "查询模版最新审核记录失败,原因:" + e.getMessage());
        }
    }
}
