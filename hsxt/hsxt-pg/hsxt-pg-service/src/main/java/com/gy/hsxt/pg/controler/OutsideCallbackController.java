package com.gy.hsxt.pg.controler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.gy.hsxt.pg.bankadapter.common.exception.BankAdapterException;
import com.gy.hsxt.pg.common.bean.CallbackRouterInfo;
import com.gy.hsxt.pg.common.constant.Constant.PGUrlKeywords;
import com.gy.hsxt.pg.common.constant.Constant.SessionKey;
import com.gy.hsxt.pg.common.utils.CallbackUrlHelper;
import com.gy.hsxt.pg.constant.PGConstant.PGChannelProvider;
import com.gy.hsxt.pg.controler.handlers.ChinapayCallbackHandler;
import com.gy.hsxt.pg.controler.handlers.PinganCallbackHandler;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-pg-service
 * 
 *  Package Name    : com.gy.hsxt.pg.controler
 * 
 *  File Name       : OutsideCallbackController.java
 * 
 *  Creation Date   : 2015-09-22
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 处理来自银行或第三方支付的http回调请求
 *  
 *                    访问路径规则：  http://unionpay.hsxt.net:18084/hsxt-pg/p/c/j/银行代号/100/100/1/
 *                              http://unionpay.hsxt.net:18084/hsxt-pg/p/c/n/银行代号/100/100/1/
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
@Controller
@RequestMapping("/" + PGUrlKeywords.PAY_GATEWAY + "/" + PGUrlKeywords.CALLBACK)
public class OutsideCallbackController {

	private Logger logger = Logger.getLogger(getClass());

	@Autowired
	private ChinapayCallbackHandler chinapayCallbackHandler;
	
	@Autowired
	private PinganCallbackHandler pinganCallbackWorker;

	/**
	 * 处理回调请求(页面)
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/" + PGUrlKeywords.JUMP + "/**")
	public ModelAndView handleJumpRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		logger.info("银行jump url:" + request.getRequestURL().toString());

		try {
			return doHandleJumpRequest(request, response);
		} catch (Exception e) {
			logger.error("", e);

			if (e instanceof BankAdapterException) {
				request.getSession().setAttribute(SessionKey.ILLEGAL_REQ_ERROR,
						"解析银行响应报文发生错误!");
			} else {
				request.getSession().setAttribute(SessionKey.ILLEGAL_REQ_ERROR,
						"非法访问!");
			}

			return new ModelAndView("/error");
		}
	}

	/**
	 * 处理回调请求(后台)
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/" + PGUrlKeywords.NOTIFY + "/**")
	public void handleNotifyRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		logger.info("银行notify url:" + request.getRequestURL().toString());

		try {
			this.doHandleNotifyRequest(request, response);
		} catch (Exception e) {
			logger.error("", e);
		}
	}

	/**
	 * 处理回调请求(页面)
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	private ModelAndView doHandleJumpRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// 解析回调地址路由信息
		CallbackRouterInfo routerInfo = CallbackUrlHelper
				.getCallbackRouterInfo(request);

		if (null == routerInfo) {
			throw new IllegalArgumentException("错误：银行回调地址非法！");
		}

		ModelAndView modelAndView = null;

		String channelProvider = routerInfo.getChannelProvider();

		// 中国银联
		if (PGChannelProvider.CHINAPAY.equals(channelProvider)) {
			modelAndView = chinapayCallbackHandler.handleJumpRequest(request,
					response, routerInfo);
		}
		// 平安银行
		else if (PGChannelProvider.PINGAN_PAY.equals(channelProvider)) {
			modelAndView = pinganCallbackWorker.handleJumpRequest(request,
					response, routerInfo);
		}

		return modelAndView;
	}

	/**
	 * 处理回调请求(后台)
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	private void doHandleNotifyRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		// 解析回调地址路由信息
		CallbackRouterInfo routerInfo = CallbackUrlHelper
				.getCallbackRouterInfo(request);

		if (null == routerInfo) {
			throw new IllegalArgumentException("错误：银行回调地址非法！");
		}

		String channelProvider = routerInfo.getChannelProvider();

		// 中国银联
		if (PGChannelProvider.CHINAPAY.equals(channelProvider)) {
			chinapayCallbackHandler.handleNotifyRequest(request, response,
					routerInfo);
		}
		// 平安银行
		else if (PGChannelProvider.PINGAN_PAY.equals(channelProvider)) {
			pinganCallbackWorker.handleNotifyRequest(request, response,
					routerInfo);
		}
	}

}