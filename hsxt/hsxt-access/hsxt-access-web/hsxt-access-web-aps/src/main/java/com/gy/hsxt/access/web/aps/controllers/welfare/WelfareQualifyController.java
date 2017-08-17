package com.gy.hsxt.access.web.aps.controllers.welfare;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.gy.hsxt.access.web.aps.services.welfare.IWelfareQualifyService;
import com.gy.hsxt.access.web.common.bean.HttpRespEnvelope;
import com.gy.hsxt.access.web.common.controller.BaseController;
import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.common.exception.HsException;

/**
 * 积分福利资格查询控制类
 * @category      积分福利资格查询控制类
 * @projectName   hsxt-access-web-aps
 * @package       com.gy.hsxt.access.web.aps.controllers.welfare.WelfareQualifyController.java
 * @className     WelfareQualifyController
 * @description   积分福利资格查询控制类
 * @author        leiyt
 * @createDate    2015-12-31 下午5:06:20  
 * @updateUser    leiyt
 * @updateDate    2015-12-31 下午5:06:20
 * @updateRemark 	说明本次修改内容
 * @version       v0.0.1
 */
@Controller
@RequestMapping("welfareQualify")
public class WelfareQualifyController extends BaseController<Object> {
	@Autowired
	IWelfareQualifyService welfareQualifyService;

	/**
	 * 分页查询福利资格
	 * 
	 * @param hsResNo
	 * @param welfareType
	 * @param page
	 * @return
	 */
	@RequestMapping(value="/listWelfareQualify",method={RequestMethod.GET,RequestMethod.POST},produces="application/json;charset=UTF-8")
	@ResponseBody
	public HttpRespEnvelope listWelfareQualify(HttpServletRequest request, HttpServletResponse response) {

		HttpRespEnvelope hre = null;

        try
        {
            // 分页查询
            request.setAttribute("serviceName", welfareQualifyService);
            request.setAttribute("methodName", "findScrollResult");
            hre = super.doList(request, response);
        }
        catch (HsException e)
        {
            hre = new HttpRespEnvelope(e);
        }

        return hre;
	}

	/**
	 * 查询个人福利资格信息
	 * 
	 * @param custId
	 * @return
	 */
	@RequestMapping(value="/findWelfareQualify",method={RequestMethod.GET,RequestMethod.POST},produces="application/json;charset=UTF-8")
	@ResponseBody
	public HttpRespEnvelope findWelfareQualify(String custId, HttpServletRequest request, HttpServletResponse response) {
		HttpRespEnvelope hre = null;
		try {
			hre = new HttpRespEnvelope(welfareQualifyService.findWelfareQualify(custId));
		} catch (Exception e) {
			hre = new HttpRespEnvelope(e);
		}
		return hre;
	}

	/**
	 * 查询用户是否有福利资格
	 * 
	 * @param custId
	 * @param welfareType
	 * @return
	 */
	@RequestMapping(value="/isHaveWelfareQualify",method={RequestMethod.GET,RequestMethod.POST},produces="application/json;charset=UTF-8")
	@ResponseBody
	public HttpRespEnvelope isHaveWelfareQualify(String custId, Integer welfareType, HttpServletRequest request, HttpServletResponse response) {
		HttpRespEnvelope hre = null;
		try {
			hre = new HttpRespEnvelope(welfareQualifyService.isHaveWelfareQualify(custId, welfareType));
		} catch (Exception e) {
			hre = new HttpRespEnvelope(e);
		}
		return hre;
	}

	@Override
	protected IBaseService<?> getEntityService() {
		return null;
	}
}
