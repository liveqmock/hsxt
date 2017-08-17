package com.gy.hsi.nt.server.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.gy.hsi.nt.server.init.InitCache;

/**
 * 
 * @className:CommonController
 * @author:likui
 * @date:2015年8月12日
 * @desc:公共控制器
 * @company:gyist
 */
@Controller
@RequestMapping("/**")
public class CommonController {

	@RequestMapping("/common/resendLoadParam.do")
	public ModelAndView resendLoadParam(HttpServletRequest request,
			HttpServletResponse response){
		new InitCache().resendInit();
		return new ModelAndView("index");
	}
	
	@RequestMapping("/**")
	public ModelAndView handleCommonRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// 作为欢迎界面进行显示
		return new ModelAndView("index");
	}
}
