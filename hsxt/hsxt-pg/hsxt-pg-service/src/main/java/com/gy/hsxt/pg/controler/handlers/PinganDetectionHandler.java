package com.gy.hsxt.pg.controler.handlers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gy.hsxt.pg.bankadapter.pingan.b2e.PinganB2eFacade;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-pg-service
 * 
 *  Package Name    : com.gy.hsxt.pg.controler.pingan
 * 
 *  File Name       : PinganDetectionHandler.java
 * 
 *  Creation Date   : 2015-09-22
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 处理来自外部第三方接入渠道的支付类请求
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
@Component("pinganDetectionHandler")
public class PinganDetectionHandler {

	private Logger logger = Logger.getLogger(getClass());

	private static String pinganStatus = "";

	private static long lastDetectionTime = System.currentTimeMillis();

	@Autowired
	private PinganB2eFacade pinganB2eFacade;

	/**
	 * 探测平安银行前置状态(访问地址: http://域名/hsxt-pg/test_pingan?pwd=123456)
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void handleDetectionPingAn(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String pwd = request.getParameter("pwd");

		if (!"123456".equals(pwd)) {
			return;
		}

		long timeDiff = System.currentTimeMillis() - lastDetectionTime;

		// 流控, 30秒才能访问一次
		if (30000 < timeDiff) {
			try {
				if (pinganB2eFacade.doDetectionSystem()) {
					pinganStatus = "OK";
				} else {
					throw new IllegalStateException("平安银行前置探测失败, 请检查服务是否正常!!");
				}
			} catch (Exception e) {
				logger.info("探测失败：", e);

				pinganStatus = "NOT OK!!";
			} finally {
				lastDetectionTime = System.currentTimeMillis();
			}
		}

		response.getWriter().print(pinganStatus);
	}
}
