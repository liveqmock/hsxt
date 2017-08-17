/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.mcs.services.workflow;

import java.util.List;
import java.util.Map;

import com.gy.hsxt.access.web.bean.MCSBase;
import com.gy.hsxt.access.web.bean.workflow.McsStatusUpdateBean;
import com.gy.hsxt.access.web.bean.workflow.McsToDoBean;
import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.tm.bean.BizType;
import com.gy.hsxt.tm.bean.Operator;

/***
 * 工单接口类
 * 
 * @Package: com.gy.hsxt.access.web.aps.services.workflow
 * @ClassName: IWorkOrderService
 * @Description: TODO
 * 
 * @author: wangjg
 * @date: 2015-12-18 下午4:06:37
 * @version V1.0
 */
public interface IWorkOrderService extends IBaseService {

    /**
     * 工作单分页查询
     * 
     * @param filterMap
     * @param sortMap
     * @param page
     * @return
     * @throws HsException
     */
    public PageData<?> workOrderPage(Map filterMap, Map sortMap, Page page) throws HsException;

    /**
     * 更新工单状态（挂起、终止）
     * 
     * @param asub
     * @throws HsException
     */
    public void updateStatus(McsStatusUpdateBean asub) throws HsException;

    /**
     * 转入待办
     * 
     * @param apsToDo
     * @throws HsException
     */
    public void toDo(McsToDoBean apsToDo) throws HsException;

    /**
     * 催办
     * 
     * @param asub
     * @throws HsException
     */
    public void reminders(McsStatusUpdateBean asub) throws HsException;

    /**
     * 获取值班员列表
     * @param mcsBase
     * @param bizType
     * @return
     * @throws HsException
     */
    public List<Operator> getAttendantList(MCSBase mcsBase,List<String> bizType) throws HsException;

    /**
     * 查询企业业务类型列表
     * 
     * @return
     */
    public List<BizType> getBizAuthList() throws HsException;
}
