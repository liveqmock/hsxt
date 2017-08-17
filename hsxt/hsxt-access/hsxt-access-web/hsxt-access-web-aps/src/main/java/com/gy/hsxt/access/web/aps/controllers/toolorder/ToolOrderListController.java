/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.access.web.aps.controllers.toolorder;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gy.hsxt.access.web.aps.services.toolorder.ToolOrderListService;
import com.gy.hsxt.access.web.common.bean.HttpRespEnvelope;
import com.gy.hsxt.access.web.common.controller.BaseController;
import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.bs.bean.tool.OrderBean;
import com.gy.hsxt.common.exception.HsException;

/**
 * 工具制作管理--工具订单列表
 *
 * @version V3.0.0
 * @Package: com.gy.hsxt.access.web.aps.controllers.toolorder
 * @ClassName: ToolOrderListController
 * @Description: TODO
 * @author: zhangcy
 * @date: 2015-11-17 下午5:45:21
 */
@SuppressWarnings("rawtypes")
@Controller
@RequestMapping("toolorder")
public class ToolOrderListController extends BaseController {

    @Resource
    private ToolOrderListService toolOrderListService;


    /**
     * 查询订单详情
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = {"/query_detail"}, method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope queryDetail(HttpServletRequest request) {
        // Token验证
        HttpRespEnvelope httpRespEnvelope = super.checkSecureToken(request);
        if (httpRespEnvelope == null) {
            String orderId = request.getParameter("orderNo");
            try {

                OrderBean result = (OrderBean) toolOrderListService.findById(orderId);
                httpRespEnvelope = new HttpRespEnvelope(result);
            } catch (HsException e) {
                httpRespEnvelope = new HttpRespEnvelope(e);
            }
        }
        return httpRespEnvelope;
    }

    /**
     * 确认订单
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = {"/confirm_order"}, method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope confirmOrder(HttpServletRequest request) {
        // Token验证
        HttpRespEnvelope httpRespEnvelope = super.checkSecureToken(request);
        if (httpRespEnvelope == null) {
            String orderId = request.getParameter("orderNo");
            try {

                toolOrderListService.toolOrderConfirmMark(orderId);
                httpRespEnvelope = new HttpRespEnvelope();
            } catch (HsException e) {
                httpRespEnvelope = new HttpRespEnvelope(e);
            }
        }
        return httpRespEnvelope;
    }

    /**
     * 关闭订单
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = {"/close_order"}, method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope closeOrder(HttpServletRequest request) {
        // Token验证
        HttpRespEnvelope httpRespEnvelope = super.checkSecureToken(request);
        if (httpRespEnvelope == null) {
            String orderId = request.getParameter("orderNo");
            try {

                toolOrderListService.closeToolOrder(orderId);

                httpRespEnvelope = new HttpRespEnvelope();
            } catch (HsException e) {
                httpRespEnvelope = new HttpRespEnvelope(e);
            }
        }
        return httpRespEnvelope;
    }

    /**
     * 分页查询配置单
     *
     * @Desc:
     * @author: likui
     * @created: 2016/6/8 9:53
     * @param:
     * @return:
     * @throws:
     * @company: gyist
     * @version V3.0.0
     */
    @ResponseBody
    @RequestMapping(value = "/get_tool_config_page")
    public HttpRespEnvelope getToolConfigPage(HttpServletRequest request, HttpServletResponse response) throws HsException {

        try {
            verifySecureToken(request);
            request.setAttribute("serviceName", toolOrderListService);
            request.setAttribute("methodName", "getToolConfigPage");
        } catch (HsException e) {
            return new HttpRespEnvelope(e);
        }
        return this.doList(request, response);
    }

    @Override
    protected IBaseService getEntityService() {
        return toolOrderListService;
    }

}
