/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.controllers;

import java.awt.image.BufferedImage;
import java.io.PrintWriter;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.access.web.bean.HttpResp;
import com.gy.hsxt.access.web.bean.VaildCodeParam;
import com.gy.hsxt.access.web.utils.VaildCodeType;
import com.gy.hsxt.access.web.utils.VaildCodeUtils;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.utils.StringUtils;
import com.gy.hsxt.common.utils.ValidateCodeUtils;
import com.gy.hsxt.lcs.client.LcsClient;
import com.gy.hsxt.lcs.client.OpenCountry;

/**
 * 公共控制器
 * 
 * @Package: com.gy.hsxt.access.web.controllers
 * @ClassName: CommonController
 * @Description: TODO
 * @author: likui
 * @date: 2015年12月30日 下午8:24:12
 * @company: gyist
 * @version V3.0.0
 */
@Controller
@RequestMapping("/gy/official")
public class CommonController {

	/** 地区平台配送Service **/
	@Autowired
	private LcsClient lcsClient;

	/**
	 * 获取应用和文书验证码
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2015年12月30日 下午8:24:19
	 * @param vaildCodeType
	 * @param request
	 * @param response
	 * @return : void
	 * @version V3.0.0
	 */
	@ResponseBody
	@RequestMapping(value = { "/common/get_vaild_code" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public void getVaildCode(@RequestParam("vaildCodeType") String vaildCodeType, HttpServletRequest request,
			HttpServletResponse response)
	{
		// 属性key
		String arrtKey = "";
		// 获取验证码
		Object[] obj = VaildCodeUtils.createValidCodeImage(new VaildCodeParam(4, 1));
		// 设置验证码类型
		switch (VaildCodeType.getVaildCodeType(vaildCodeType))
		{
		case DOC_VAILD:
			arrtKey = VaildCodeType.DOC_VAILD.getCode();
			break;
		case AAP_HANDLE:
			arrtKey = VaildCodeType.AAP_HANDLE.getCode();
			break;
		case APP_QUERY:
			arrtKey = VaildCodeType.APP_QUERY.getCode();
			break;
		case LOGIN:
			arrtKey = VaildCodeType.LOGIN.getCode();
			break;
		default:
			arrtKey = "vaildCode";
			break;
		}
		request.getSession().setAttribute(arrtKey, obj[0]);
		// 禁止图像缓存。
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("image/jpeg");
		System.out.println(request.getSession().getId());
		try
		{
			// 将图像输出到Servlet输出流中。
			ServletOutputStream sos = response.getOutputStream();
			ImageIO.write((BufferedImage) obj[1], "jpeg", sos);
			sos.close();
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "getVaildCode", "生成验证码异常", ex);
		}
	}

	/**
	 * 获取登录验证码
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2015年12月30日 下午8:25:54
	 * @param request
	 * @param response
	 * @return : void
	 * @version V3.0.0
	 */
	@ResponseBody
	@RequestMapping(value = { "/common/get_login_vaild_code" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public void getLoginVaildCode(HttpServletRequest request, HttpServletResponse response)
	{
		try
		{
			// 禁止图像缓存。
			response.setHeader("Pragma", "no-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
			response.setContentType("image/jpeg");
			// 获取验证码
			String vaildCode = ValidateCodeUtils.getDefaultRandcode(4, response.getOutputStream());
			request.getSession().setAttribute("vaildCode", vaildCode);
			System.out.println(request.getSession().getId());
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "getLoginVaildCode", "生成验证码异常", ex);
		}
	}

	/**
	 * 获取所有开启的地区平台
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2015年12月30日 下午2:50:42
	 * @param request
	 * @param response
	 * @return
	 * @return : HttpResp
	 * @version V3.0.0
	 */
	@ResponseBody
	@RequestMapping(value = { "/common/get_start_area_plat" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpResp getStartAreaPlat(HttpServletRequest request, HttpServletResponse response)
	{
		List<OpenCountry> countrys = null;
		try
		{
			countrys = lcsClient.queryOpenCountryAll();
			if (StringUtils.isNotBlank(countrys))
			{
				return new HttpResp(RespCode.SUCCESS.getCode(), countrys);
			}
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "getStartAreaPlat", "获取所有开启的地区平台异常", ex);
		}
		return new HttpResp(RespCode.FAIL.getCode());
	}

	/**
	 * 验证登录验证码
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年1月20日 下午7:21:37
	 * @param vaildCode
	 * @param request
	 * @param response
	 * @return
	 * @return : HttpResp
	 * @version V3.0.0
	 */
	@ResponseBody
	@RequestMapping(value = { "/common/vaild_login_vaild_code" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpResp vaildContractLawful(@RequestParam("vaildCode") String vaildCode, HttpServletRequest request,
			HttpServletResponse response)
	{
		String code = (String) request.getSession().getAttribute(VaildCodeType.LOGIN.getCode());
		System.out.println(request.getSession().getId());
		if (vaildCode.equalsIgnoreCase(code))
		{
			return new HttpResp(RespCode.SUCCESS.getCode());
		}
		return new HttpResp(RespCode.VAILD_CODE_ERROR.getCode());
	}

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
	@RequestMapping(value = { "/common/ebank_pay_back" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
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
