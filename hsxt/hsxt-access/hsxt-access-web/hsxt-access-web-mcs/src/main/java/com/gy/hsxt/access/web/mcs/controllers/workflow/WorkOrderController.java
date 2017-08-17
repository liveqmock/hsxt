/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.mcs.controllers.workflow;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gy.hsxt.access.web.bean.MCSBase;
import com.gy.hsxt.access.web.bean.workflow.McsStatusUpdateBean;
import com.gy.hsxt.access.web.bean.workflow.McsToDoBean;
import com.gy.hsxt.access.web.common.bean.HttpRespEnvelope;
import com.gy.hsxt.access.web.common.controller.BaseController;
import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.access.web.mcs.services.workflow.IWorkOrderService;
import com.gy.hsxt.common.constant.TaskStatus;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.StringUtils;
import com.gy.hsxt.tm.bean.BizType;
import com.gy.hsxt.tm.bean.Operator;

/***
 * 工单类
 * 
 * @Package: com.gy.hsxt.access.web.aps.controllers.workflow
 * @ClassName: WorkOrderController
 * @Description: TODO
 * 
 * @author: wangjg
 * @date: 2015-12-18 下午3:53:51
 * @version V1.0
 */
@Controller
@RequestMapping("workOrder")
public class WorkOrderController extends BaseController {

    @Resource
    private IWorkOrderService iWorkOrderService;

    /**
     * 工单分页查询
     * 
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "work_order_page", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope workOrderPage(HttpServletRequest request, HttpServletResponse response) {
        HttpRespEnvelope hre = null;
        try
        {
            // 分页查询
            request.setAttribute("serviceName", iWorkOrderService);
            request.setAttribute("methodName", "workOrderPage");
            hre = super.doList(request, response);
        }
        catch (HsException e)
        {
            hre = new HttpRespEnvelope(e);
        }

        return hre;
    }

    /**
     * 终止
     * 
     * @param request
     * @param workOrderNo
     * @return
     * @throws HsException
     */
   /* @ResponseBody
    @RequestMapping(value = "termination", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope termination(HttpServletRequest request, McsStatusUpdateBean asub) {
        HttpRespEnvelope hre = null;

        try
        {
            // 1、验证token
            super.verifySecureToken(request);
            // 2、验证有效数据
            asub.checkData();
            // 3、设置状态
            asub.setStatus(TaskStatus.STOPPED.getCode());
            // 4、终止
            iWorkOrderService.updateStatus(asub);
            hre = new HttpRespEnvelope();

        }
        catch (HsException e)
        {
            hre = new HttpRespEnvelope(e);
        }

        return hre;
    }*/

    /**
     * 拒绝受理
     * @param request
     * @param asub
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "door", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope door(HttpServletRequest request, McsStatusUpdateBean msub) {
        HttpRespEnvelope hre = null;

        try
        {
            // 1、验证token
            super.verifySecureToken(request);
            // 2、验证有效数据
            msub.checkData();
            // 3、设置状态
            msub.setStatus(TaskStatus.UNACCEPT.getCode());
            // 4、更新为未受理状态
            iWorkOrderService.updateStatus(msub);
            hre = new HttpRespEnvelope();

        }
        catch (HsException e)
        {
            hre = new HttpRespEnvelope(e);
        }

        return hre;
    }
    /**
     * 转入待办
     * 
     * @param request
     * @param apsToDo
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "to_do", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope toDo(HttpServletRequest request, McsToDoBean apsToDo) {
        HttpRespEnvelope hre = null;
        try
        {
            // 1、验证token
            super.verifySecureToken(request);
            // 2、数据验证
            apsToDo.checkData();
            // 3、转入待办
            iWorkOrderService.toDo(apsToDo);
            hre = new HttpRespEnvelope();
        }
        catch (HsException e)
        {
            hre = new HttpRespEnvelope(e);
        }

        return hre;
    }

    /**
     * 挂起
     * 
     * @param request
     * @param workOrderNo
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "suspend", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope suspend(HttpServletRequest request, McsStatusUpdateBean asub) {
        HttpRespEnvelope hre = null;
        try
        {
            // 1、验证token
            super.verifySecureToken(request);
            // 2、验证有效数据
            asub.checkData();
            // 3、设置状态
            asub.setStatus(TaskStatus.HANG_UP.getCode());
            // 4、挂起
            iWorkOrderService.updateStatus(asub);
            hre = new HttpRespEnvelope();

        }
        catch (HsException e)
        {
            hre = new HttpRespEnvelope(e);
        }

        return hre;
    }

    /**
     * 催办
     * 
     * @param request
     * @param workOrderNo
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "reminders", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope reminders(HttpServletRequest request, McsStatusUpdateBean asub) {
        HttpRespEnvelope hre = null;
        try
        {
            // 1、验证token
            super.verifySecureToken(request);
            // 2、验证有效数据
            asub.checkBizNo();
            // 3、催办
            iWorkOrderService.reminders(asub);
            hre = new HttpRespEnvelope();
        }
        catch (HsException e)
        {
            hre = new HttpRespEnvelope(e);
        }

        return hre;
    }

    /**
     * 获取值班员
     * 
     * @param request
     * @param apsBase
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "get_attendant_list", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope getAttendantList(HttpServletRequest request, MCSBase mcsBase) {
        HttpRespEnvelope hre = null;
        try
        {
            
            // 1、验证token
            super.verifySecureToken(request);
            // 2、业务类型
            List<String> bieTypes=this.cleanArrayEmpty(request.getParameterValues("bizType[]"));
            // 3、获取值班员
            List<Operator> aList = iWorkOrderService.getAttendantList(mcsBase,bieTypes);
            hre = new HttpRespEnvelope(aList);
        }
        catch (HsException e)
        {
            hre = new HttpRespEnvelope(e);
        }

        return hre;
    }
    
    
    /**
     * 清除为空的数组集合
     * @param status
     * @return
     */
    List<String> cleanArrayEmpty(String[] status) {
        if (status == null || status.length == 0)
        {
            return null;
        }

        // 临时存储满足条件数据
        List<String> temList = new ArrayList<String>();

        // 遍历判断
        for (String s : status)
        {
            if (!StringUtils.isBlank(s) && !temList.contains(s))
            {
                temList.add(s);
            }
        }

        // 无满足条件 返回null
        if (temList.size() == 0)
        {
            return null;
        }

        return temList;
    }

    /**
     * 查询地区平台业务类型列表
     * 
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "get_bizAuth_list", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope getBizAuthList(HttpServletRequest request) {
        HttpRespEnvelope hre = null;
        try
        {
            // 1、验证token
            super.verifySecureToken(request);
            // 2、查询企业业务类型列表
            List<BizType> btList = iWorkOrderService.getBizAuthList();
            hre = new HttpRespEnvelope(btList);
        }
        catch (HsException e)
        {
            hre = new HttpRespEnvelope(e);
        }

        return hre;
    }

    @Override
    protected IBaseService getEntityService() {
        // TODO Auto-generated method stub
        return null;
    }

}
