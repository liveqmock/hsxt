package com.gy.hsxt.access.web.aps.controllers.welfare;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gy.hsxt.access.web.aps.services.welfare.IWelfareGrantService;
import com.gy.hsxt.access.web.common.bean.HttpRespEnvelope;
import com.gy.hsxt.access.web.common.controller.BaseController;
import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.ws.bean.GrantQueryCondition;

/**
 * 积分福利发放控制类
 * 
 * @category 积分福利发放控制类
 * @projectName hsxt-access-web-aps
 * @package 
 *          com.gy.hsxt.access.web.aps.controllers.welfare.WelfareGrantController
 *          .java
 * @className WelfareGrantController
 * @description 积分福利发放控制类
 * @author leiyt
 * @createDate 2015-12-31 下午5:05:44
 * @updateUser leiyt
 * @updateDate 2015-12-31 下午5:05:44
 * @updateRemark 说明本次修改内容
 * @version v0.0.1
 */
@Controller
@RequestMapping("welfareGrant")
public class WelfareGrantController extends BaseController<Object> {

	@Autowired
	IWelfareGrantService welfareGrantService;

	/**
	 * 查询待发放记录
	 * 
	 * @param queryCondition
	 * @param page
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/listPendingGrant", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public HttpRespEnvelope listPendingGrant(GrantQueryCondition queryCondition, Page page,
			HttpServletRequest request, HttpServletResponse response) {
		HttpRespEnvelope hre = null;
		try {
			// 分页查询
			request.setAttribute("serviceName", welfareGrantService);
			request.setAttribute("methodName", "findScrollResult");
			hre = super.doList(request, response);
		} catch (HsException e) {
			hre = new HttpRespEnvelope(e);
		}
		return hre;
	}

	/**
	 * 查询已发放记录
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/listHistoryGrant", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public HttpRespEnvelope listHistoryGrant(GrantQueryCondition queryCondition, Page page,
			HttpServletRequest request, HttpServletResponse response) {
		HttpRespEnvelope hre = null;
		try {
			// 分页查询
			request.setAttribute("serviceName", welfareGrantService);
			request.setAttribute("methodName", "findScrollResult2");
			hre = super.doList(request, response);
		} catch (HsException e) {
			hre = new HttpRespEnvelope(e);
		}
		return hre;
	}

	/**
	 * 查询发放详情
	 * 
	 * @param grantId
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/queryGrantDetail", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public HttpRespEnvelope queryGrantDetail(String grantId, HttpServletRequest request,
			HttpServletResponse response) {
		HttpRespEnvelope hre = null;
		try {
			hre = new HttpRespEnvelope(welfareGrantService.queryGrantDetail(grantId));
		} catch (HsException e) {
			hre = new HttpRespEnvelope(e);
		}
		return hre;
	}

	/**
	 * 发放操作
	 * 
	 * @param grantId
	 * @param remark
	 * @param request
	 * @param response
	 * @return30820160119161908000000308201601191619080000003082016011916190800000030820160119161908000000
	 */
	@RequestMapping(value = "/grantWelfare", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public HttpRespEnvelope grantWelfare(String grantId, String remark, HttpServletRequest request,
			HttpServletResponse response) {
		HttpRespEnvelope hre = null;
		try {
			welfareGrantService.grantWelfare(grantId, remark);
			hre = new HttpRespEnvelope();
		} catch (HsException e) {
			hre = new HttpRespEnvelope(e);
		}
		return hre;
	}

	@Override
	protected IBaseService<?> getEntityService() {
		return null;
	}

}
