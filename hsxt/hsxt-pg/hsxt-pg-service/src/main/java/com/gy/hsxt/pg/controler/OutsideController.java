package com.gy.hsxt.pg.controler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gy.hsxt.pg.controler.handlers.CpnotifyOutsideHandler;
import com.gy.hsxt.pg.controler.handlers.PinganDetectionHandler;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-pg-service
 * 
 *  Package Name    : com.gy.hsxt.pg.controler
 * 
 *  File Name       : OutsideController.java
 * 
 *  Creation Date   : 2015-09-22
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 处理来自外部的请求
 *                    
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
@Controller
@RequestMapping("/**")
public class OutsideController {

	@Autowired
	private CpnotifyOutsideHandler cpnotifyOutsideHandler;

	@Autowired
	private PinganDetectionHandler pinganDetectionHandler;

	/**
	 * 欢迎页面
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/**")
	public void handleCommonRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// 跳转到默认的index页面
		response.sendRedirect(request.getContextPath() + "/index.jsp");
	}

	/**
	 * 银联对账文件通知地址
	 * 
	 * 交易对账地址：
	 * http://unionpay.hsxt.net:18084/hsxt-pg/cpnotify/trading?download=下载地址
	 * 清算对账地址：
	 * http://unionpay.hsxt.net:18084/hsxt-pg/cpnotify/clearing?download=下载地址
	 * 
	 * 这个访问地址规则已经向中国银联发起过申请审核, 不得随意改动!!! 否则后果自负!!!!
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/cpnotify/trading")
	public void handleCpNotifyRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		cpnotifyOutsideHandler.handleCpDayBanlanceRequest(request, response);
	}

	/**
	 * 探测平安银行状态(访问地址: http://域名/hsxt-pg/test_pingan?pwd=123456)
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = { "/test-pingan", "/test_pingan" })
	public void handleDetectionPingAn(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		pinganDetectionHandler.handleDetectionPingAn(request, response);
	}
}
