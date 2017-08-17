/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.aps.controllers.receivingManage;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.WebUtils;

import com.alibaba.fastjson.JSON;
import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.access.web.aps.services.receivingManage.IOrderService;
import com.gy.hsxt.access.web.bean.reveivingManage.ApsOrderColoseBean;
import com.gy.hsxt.access.web.common.bean.HttpRespEnvelope;
import com.gy.hsxt.access.web.common.controller.BaseController;
import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.access.web.common.utils.RequestUtil;
import com.gy.hsxt.bs.bean.order.Order;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.rp.bean.PaymentManagementOrder;
import com.gy.hsxt.uc.as.bean.operator.AsOperator;

/***
 * 订单控制器
 * 
 * @Package: com.gy.hsxt.access.web.aps.controllers.receivingManage
 * @ClassName: OrderController
 * @Description: TODO
 * 
 * @author: wangjg
 * @date: 2015-12-11 上午11:58:13
 * @version V1.0
 */
@Controller("receivManage")
@RequestMapping("receivManage")
public class OrderController extends BaseController {

    @Resource
    private IOrderService iOrderService;

    /**
     * 业务订单查询
     * 
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "business_order_page", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope businessOrderPage(HttpServletRequest request, HttpServletResponse response) {
        HttpRespEnvelope hre = null;

        try
        {
            // 分页查询
            request.setAttribute("serviceName", iOrderService);
            request.setAttribute("methodName", "businessAllOrderPage");
            hre = super.doList(request, response);
        }
        catch (HsException e)
        {
            hre = new HttpRespEnvelope(e);
        }

        return hre;

    }
    
    /**
     * 业务订单导出
     * 
     * @param request
     * @param response
     * @return
     * @throws IOException 
     */
    @RequestMapping(value = "export_business_order")
    public void exportBusinessOrder(HttpServletRequest request, HttpServletResponse response) throws IOException {/*
        OutputStream fOut = null;
        try
        {
            String fileName = java.net.URLEncoder.encode("订单收款记录", "UTF-8");
            
            //重置输出格式
            response.reset();
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("content-disposition", "attachment;filename=" + fileName + ".xls");

            //查询导出数据
            Map<String, Object> filterMap = WebUtils.getParametersStartingWith(request, "search_");
            List<PaymentManagementOrder> ordList= iOrderService.exportBusinessOrder(filterMap);
            
            //转换xls文件格式数据
            OrderExportPersonalized oep=new OrderExportPersonalized(ordList);
            HSSFWorkbook hssfwb = ExcelExport.createExcel("订单收款记录", oep.cellNum, oep.cellTitle,oep.content);
            
            //输出文件流
            fOut = response.getOutputStream();
            hssfwb.write(fOut);
        }
        catch (Exception e)
        {
            SystemLog.error("receivingManage", "export", "订单收款记录异常", e);
            response.setContentType("text/html;charset=UTF-8");
            response.getOutputStream().write(ASRespCode.APS_ORDER_HISTORY_DOWNLOAD_FAIL.getDesc().getBytes());
        }
        finally
        {
            try
            {
                if (fOut != null)
                {
                    fOut.flush();
                    fOut.close();
                }
            }
            catch (IOException e)
            {
                SystemLog.error("receivingManage", "export", "订单收款记录异常关闭流异常", e);
                response.setContentType("text/html;charset=UTF-8");
                response.getOutputStream().write(ASRespCode.APS_ORDER_HISTORY_DOWNLOAD_FAIL.getDesc().getBytes());
            }
        }
    */
    
        

        
        SystemLog.info(this.getClass().getName(), "exportBusinessOrder", "订单收款记录导出入参：" + JSON.toJSONString(request.getParameterMap()));
       
        //清除缓冲数据
        response.reset();
        
        try
        {
            // 获取查询条件
            Map<String, Object> filterMap = WebUtils.getParametersStartingWith(request, "search_");

            // 获取下载文件路径
            String dowloadPath = iOrderService.exportBusinessOrder(filterMap);
            SystemLog.info(this.getClass().getName(), "exportBusinessOrder", "获取文件地址：" + dowloadPath);

            // 初始化文件
            File f = new File(dowloadPath);
            if (!f.exists())
            {
                SystemLog.info(this.getClass().getName(), "exportBusinessOrder", "文件不存在");

                response.setContentType("text/html");
                response.getOutputStream().write("Download file does not exist".getBytes());
                return;
            }

            try (BufferedInputStream br = new BufferedInputStream(new FileInputStream(f)); OutputStream out = response.getOutputStream();)//自动资源管理
            {
                // 设置输出文件类型
                response.setContentType("application/x-msdownload");
                response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(f.getName(), "UTF-8"));
                
                byte[] buf = new byte[1024];
                int len = 0;
                while ((len = br.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
            }
            catch (Exception e)
            {
                SystemLog.error(this.getClass().getName(), "exportBusinessOrder", "导出文件错误", e);

                response.setCharacterEncoding("utf-8");
                response.setContentType("text/html");
                if (e instanceof HsException) {
                    response.getOutputStream().write(e.getMessage().getBytes());
                } else  {
                    response.getOutputStream().write("Download file failed".getBytes());
                }
            }
        } catch (IOException e) {
            SystemLog.error(this.getClass().getName(), "exportBusinessOrder", "导出文件错误", e);

            response.setCharacterEncoding("utf-8");
            response.setContentType("text/html");
            response.getOutputStream().write("Download file failed".getBytes());
        }

    }

    /**
     * 查找订单明细
     * 
     * @param request
     * @param orderNo
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "get_order_detail", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope getOrderDetail(HttpServletRequest request, String orderNo) {
        HttpRespEnvelope hre = null;

        try
        {
            // 1、验证token
            super.verifySecureToken(request);

            // 2、验证非空订单号
            RequestUtil.verifyParamsIsNotEmpty(new Object[] { orderNo, RespCode.GL_PARAM_ERROR });

            // 3、获取订单详情
            PaymentManagementOrder pmo = iOrderService.getOrderDetail(orderNo);
            hre = new HttpRespEnvelope(pmo);

        }
        catch (HsException e)
        {
            hre = new HttpRespEnvelope(e);
        }

        return hre;
    }

    /**
     * 订单关闭
     * 
     * @param request
     * @param orderNo
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "close_order", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope closeOrder(HttpServletRequest request, ApsOrderColoseBean aocb) {
        HttpRespEnvelope hre = null;

        try
        {
            // 1、验证token
            super.verifySecureToken(request);

            // 2、空数据验证
            aocb.checkData();

            // 3、调用关闭接口
            iOrderService.closeOrder(aocb);
            hre = new HttpRespEnvelope();
        }
        catch (HsException e)
        {
            hre = new HttpRespEnvelope(e);
        }

        return hre;
    }

    /**
     * 分页查询年费业务单
     * 
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "annual_fee_order_page", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope annualFeeOrderPage(HttpServletRequest request, HttpServletResponse response) {

        HttpRespEnvelope hre = null;

        try
        {
            // 分页查询
            request.setAttribute("serviceName", iOrderService);
            request.setAttribute("methodName", "queryAnnualFeeOrderForPage");
            hre = super.doList(request, response);
        }
        catch (HsException e)
        {
            hre = new HttpRespEnvelope(e);
        }

        return hre;

    }

    /**
     * 系统销售费分页查询
     * 
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "debt_order_page", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope debtOrderPage(HttpServletRequest request, HttpServletResponse response) {

        HttpRespEnvelope hre = null;

        try
        {
            // 分页查询
            request.setAttribute("serviceName", iOrderService);
            request.setAttribute("methodName", "queryDebtOrder");
            hre = super.doList(request, response);
        }
        catch (HsException e)
        {
            hre = new HttpRespEnvelope(e);
        }

        return hre;
    }
    
    /**
     * 获取订单临帐支付详情
     * 
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "get_tempAcct_payInfo", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope getTempAcctPayInfo(HttpServletRequest request, String orderNo) {
        HttpRespEnvelope hre = null;
        try
        {
            // 1、验证token
            super.verifySecureToken(request);
            // 2、验证非空订单号
            RequestUtil.verifyParamsIsNotEmpty(new Object[] { orderNo, RespCode.GL_PARAM_ERROR });
            // 3、获取订单临帐支付详情
            Order order = iOrderService.getTempAcctPayInfo(orderNo);
            hre = new HttpRespEnvelope(order);
        }
        catch (HsException e)
        {
            hre = new HttpRespEnvelope(e);
        }

        return hre;
    }
    
    /**
     * 
     * 方法名称：查询操作员信息
     * 方法描述：查询操作员信息
     * @param request HttpServletRequest对象
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/findOrderOperator")
    public HttpRespEnvelope findOrderOperator(HttpServletRequest request) {
        try {
            super.verifySecureToken(request);
            String orderCustType = request.getParameter("orderCustType");//类型
            String orderOperator = request.getParameter("orderOperator");//操作员编号
            AsOperator operator = this.iOrderService.findOrderOperator(orderCustType, orderOperator);
            return new HttpRespEnvelope(operator);
        } catch (HsException e) {
            return new HttpRespEnvelope(e);
        }
    }
    

    @Override
    protected IBaseService getEntityService() {
        // TODO Auto-generated method stub
        return null;
    }

}
