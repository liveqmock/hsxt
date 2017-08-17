package com.gy.hsxt.access.web.controllers; /**
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 * <p>
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */

import com.gy.hsi.lc.client.log4j.SystemLog;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * @Package: com.gy.hsxt.access.web.controllers
 * @ClassName: EbankBackController
 * @Description:
 * @author: likui
 * @date: 2016/5/5 9:56
 * @company: gyist
 * @version V3.0.0
 */
@Controller
@RequestMapping("/gy")
public class EbankBackController {

    /**
     * 网银支付回调
     *
     * @Description:
     * @author: likui
     * @created: 2016年3月9日 上午10:41:15
     * @param request
     * @param response
     * @return : void
     * @version V3.0.0
     */
    @ResponseBody
    @RequestMapping(value = { "/ebank_pay_back" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public void eBankPayBack(String transStatus, String failReason, HttpServletRequest request,
                             HttpServletResponse response)
    {
        StringBuffer buf = null;
        String payResult = "";
        String title = "网银支付回调";
        try
        {
            response.setCharacterEncoding("UTF-8");
            PrintWriter writer = response.getWriter();
            buf = new StringBuffer();
            if ("100".equals(transStatus))
            {
                payResult = "支付成功";
            } else
            {
                payResult = "支付失败";
                SystemLog.info(this.getClass().getName(), "eBankPayBack",
                        "网银支付失败,订单号:" + request.getParameter("orderNo") + ",失败原因:" + failReason);
            }
            buf.append("<!DOCTYPE html PUBLIC '-//W3C//DTD HTML 4.01 Transitional//EN' 'http://www.w3.org/TR/html4/loose.dtd'>");
            buf.append("<html> <head> <meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>");
            buf.append("<title>" + title + "</title> </head>");
            buf.append("<body style='font-size: 20px; text-align:center;'>");
            buf.append("<div style='height:120px'></div> 	<div style='color:blue;'>" + payResult + "</div> ");
            buf.append("</body> </html>");
            writer.write(buf.toString());
            writer.close();
        } catch (Exception ex)
        {
            SystemLog.error(this.getClass().getName(), "eBankPayBack", "网银支付回调异常", ex);
        }
    }
}
