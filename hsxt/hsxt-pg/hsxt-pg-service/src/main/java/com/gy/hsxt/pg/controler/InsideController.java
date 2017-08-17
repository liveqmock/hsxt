package com.gy.hsxt.pg.controler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.gy.hsxt.pg.common.constant.Constant.PGUrlKeywords;
import com.gy.hsxt.pg.constant.PGConstant.PGChannelProvider;
import com.gy.hsxt.pg.controler.handlers.ChinapayInsideHandler;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-pg-service
 * 
 *  Package Name    : com.gy.hsxt.pg.controler
 * 
 *  File Name       : InsideController.java
 * 
 *  Creation Date   : 2015-10-14
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 处理来自地区平台内部的http支付相关请求
 *  
 *                    访问路径规则：
 *                      http://127.0.0.1:8080/站点名称/p/inner/c/upop/paysecond
 *                      http://127.0.0.1:8080/站点名称/p/inner/c/alipay/direct
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
@Controller
@RequestMapping("/" + PGUrlKeywords.PAY_GATEWAY + "/" + PGUrlKeywords.INNER)
public class InsideController {

	@Autowired
	private ChinapayInsideHandler chinapayInsideHandler;

	/**
	 * 接收来自内部系统的[非首次]快捷支付请求 <br>
	 * 访问地址： http://127.0.0.1:8080/站点名称/p/inner/c/upop/paysecond <br>
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/" + PGChannelProvider.CHINAPAY + "/"
			+ PGUrlKeywords.UPOP_PAY_SECOND)
	public ModelAndView handleUpopSecondPayRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return chinapayInsideHandler.handleInnerRequest(request, response);
	}
}
