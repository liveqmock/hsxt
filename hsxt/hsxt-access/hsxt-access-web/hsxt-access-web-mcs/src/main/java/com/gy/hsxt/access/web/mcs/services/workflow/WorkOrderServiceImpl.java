/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.mcs.services.workflow;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.gy.hsxt.access.web.bean.MCSBase;
import com.gy.hsxt.access.web.bean.workflow.McsStatusUpdateBean;
import com.gy.hsxt.access.web.bean.workflow.McsToDoBean;
import com.gy.hsxt.access.web.common.service.BaseServiceImpl;
import com.gy.hsxt.access.web.common.utils.CommonUtils;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.constant.CustType;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.tm.api.ITMBizAuthService;
import com.gy.hsxt.tm.api.ITMOnDutyService;
import com.gy.hsxt.tm.api.ITMTaskService;
import com.gy.hsxt.tm.bean.BaseBean;
import com.gy.hsxt.tm.bean.BizType;
import com.gy.hsxt.tm.bean.Operator;
import com.gy.hsxt.tm.bean.TmBaseParam;
import com.gy.hsxt.uc.as.api.common.IUCLoginService;

/***
 * 工单服务类
 * 
 * @Package: com.gy.hsxt.access.web.aps.services.workflow
 * @ClassName: WorkOrderServiceImpl
 * @Description: TODO
 * 
 * @author: wangjg
 * @date: 2015-12-18 下午4:07:48
 * @version V1.0
 */
@Service
public class WorkOrderServiceImpl extends BaseServiceImpl implements IWorkOrderService {

    /** 注入工单dubbo服务 */
    @Resource
    private ITMTaskService itmTaskService;

    /** 注入 值班管理dubbo服务 */
    @Resource
    private ITMOnDutyService itmOnDutyService;

    /** 注入 业务权限管理dubbo服务 */
    @Resource
    private ITMBizAuthService itmBizAuthService;

    @Resource
    private IUCLoginService ucLoginService;

    /**
     * 工作单分页查询
     * @param filterMap
     * @param sortMap
     * @param page
     * @return
     * @throws HsException
     * @see com.gy.hsxt.access.web.aps.services.workflow.IWorkOrderService#workOrderPage(java.util.Map,
     *      java.util.Map, com.gy.hsxt.common.bean.Page)
     */
    @Override
    public PageData<?> workOrderPage(Map filterMap, Map sortMap, Page page) throws HsException {
        // 1、构建查询实体
        TmBaseParam tbp = createTmBaseParam(filterMap);

        // 2、分页结果
        return itmTaskService.getTaskList(tbp, page);
    }

    /**
     * 转入待办
     * @param apsToDo
     * @throws HsException
     * @see com.gy.hsxt.access.web.aps.services.workflow.IWorkOrderService#toDo(com.gy.hsxt.access.web.bean.workflow.McsToDoBean)
     */
    @Override
    public void toDo(McsToDoBean apsToDo) throws HsException {
        // 创建BaseBean
        BaseBean baseBean = createBaseBean(apsToDo);
        // 转派
        itmTaskService.sendOrder(apsToDo.getAssignedMode(), apsToDo.getTaskIds(), apsToDo.getOptCustIds(), baseBean);

    }

    /**
     * 更新工单状态（挂起、终止）
     * @param asub
     * @throws HsException
     * @see com.gy.hsxt.access.web.aps.services.workflow.IWorkOrderService#updateStatus(com.gy.hsxt.access.web.bean.workflow.McsStatusUpdateBean)
     */
    @Override
    public void updateStatus(McsStatusUpdateBean asub) throws HsException {
        // 更新状态
        itmTaskService.updateTaskStatus(asub.getTaskId(), asub.getStatus(), asub.getReason());
    }

    /**
     * 催办
     * @param asub
     * @throws HsException
     * @see com.gy.hsxt.access.web.aps.services.workflow.IWorkOrderService#reminders(com.gy.hsxt.access.web.bean.workflow.McsStatusUpdateBean)
     */
    @Override
    public void reminders(McsStatusUpdateBean asub) throws HsException {
        // 调用催办接口
        itmTaskService.updateWarnFlag(asub.getTaskId(), true);
    }

    /**
     * 获取值班员列表
     * @param mcsBase
     * @param bizType
     * @return
     * @throws HsException 
     * @see com.gy.hsxt.access.web.mcs.services.workflow.IWorkOrderService#getAttendantList(com.gy.hsxt.access.web.bean.MCSBase, java.util.List)
     */
    @Override
    public List<Operator> getAttendantList(MCSBase mcsBase, List<String> bizType) throws HsException {
        // 获取值班人员
        return itmOnDutyService.getOperatorListByCustId(mcsBase.getEntCustId(), bizType);
    }

    /**
     * 查询企业业务类型列表
     * @return
     * @see com.gy.hsxt.access.web.aps.services.workflow.IWorkOrderService#getBizAuthList()
     */
    @Override
    public List<BizType> getBizAuthList() {
        return itmBizAuthService.getBizAuthList(CustType.MANAGE_CORP.getCode());
    }

    /**
     * 构建查询实体
     * @param filter
     * @return
     */
    TmBaseParam createTmBaseParam(Map filter) {

        TmBaseParam tbp = new TmBaseParam();
      
        tbp.setEntCustId((String) filter.get("entCustId"));              // 企业客户号
        tbp.setStartDate((String) filter.get("startDate"));              // 开始时间
        tbp.setEndDate((String) filter.get("endDate"));                  // 结束时间
        tbp.setExecutor((String) filter.get("executor"));                // 经办人
        tbp.setHsResNo((String) filter.get("queryResNo"));               //客户互生号
        tbp.setBizType(CommonUtils.toInteger(filter.get("bizType")));    // 业务类型
        tbp.setStatus(CommonUtils.toInteger(filter.get("status"))) ;     // 状态

        return tbp;
    }

    /**
     * 创建BaseBean
     * @param apsToDo
     * @return
     */
    BaseBean createBaseBean(McsToDoBean apsToDo) {
        BaseBean bean = new BaseBean();
        bean.setEntCustId(apsToDo.getEntCustId());
        bean.setOperatorId(apsToDo.getCustId());
        bean.setOperatorName(apsToDo.getCustName());
        return bean;
    }
}
