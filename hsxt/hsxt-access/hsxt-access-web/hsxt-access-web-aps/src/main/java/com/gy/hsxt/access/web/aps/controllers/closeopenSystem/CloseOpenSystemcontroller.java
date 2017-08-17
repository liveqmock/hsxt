package com.gy.hsxt.access.web.aps.controllers.closeopenSystem;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.gy.hsxt.access.web.aps.services.closeopen.ICloseOpenEntService;
import com.gy.hsxt.access.web.common.bean.HttpRespEnvelope;
import com.gy.hsxt.access.web.common.controller.BaseController;
import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.bs.bean.base.ApprParam;
import com.gy.hsxt.bs.bean.entstatus.CloseOpenEnt;
import com.gy.hsxt.bs.bean.entstatus.CloseOpenEntDetail;
import com.gy.hsxt.bs.bean.entstatus.CloseOpenEntInfo;
import com.gy.hsxt.common.exception.HsException;

/**
 * 
 * @projectName : hsxt-access-web-aps
 * @package : com.gy.hsxt.access.web.aps.controllers.closeopenSystem
 * @className : CloseOpenSystemcontroller.java
 * @description : 开启关闭系统管理
 * @author : chenli
 * @createDate : 2015-1-19
 * @updateUser :
 * @updateDate :
 * @updateRemark :
 * @version : v0.0.1
 */
@Controller
@RequestMapping("closeopenSystem")
public class CloseOpenSystemcontroller extends
		BaseController<ICloseOpenEntService> {
	@Resource
	private ICloseOpenEntService service;

	/**
	 * 开启系统
	 * 
	 * @param closeOpenEnt
	 *            :开启关闭系统实体
	 * @param request
	 * @return
	 */
	@RequestMapping(value = { "/open" }, method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public HttpRespEnvelope open(CloseOpenEnt closeOpenEnt, String companyName,
			String companyResNo, String companyCustId, String companyCustType,
			String custId, String custName, HttpServletRequest request) {
		HttpRespEnvelope hre = null;

		try {
			// 验证安全令牌
			verifySecureToken(request);

			closeOpenEnt.setEntCustName(companyName);
			closeOpenEnt.setEntCustId(companyCustId);
			closeOpenEnt.setCustType(Integer.parseInt(companyCustType));
			closeOpenEnt.setEntResNo(companyResNo);
			closeOpenEnt.setReqOptCustId(custId);
			closeOpenEnt.setReqOptCustName(custName);

			// 调用服务创建收款账户
			service.openEnt(closeOpenEnt);

			hre = new HttpRespEnvelope();
		} catch (HsException e) {
			hre = new HttpRespEnvelope(e);
		}

		return hre;
	}

	/**
	 * 关闭系统
	 * 
	 * @param closeOpenEnt
	 *            :开启关闭系统实体
	 * @param request
	 * @return
	 */
	@RequestMapping(value = { "/close" }, method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public HttpRespEnvelope close(CloseOpenEnt closeOpenEnt,
			String companyName, String companyResNo, String companyCustId,
			String companyCustType, String custId, String custName,
			HttpServletRequest request) {
		HttpRespEnvelope hre = null;

		try {
			// 验证安全令牌
			verifySecureToken(request);

			closeOpenEnt.setEntCustName(companyName);
			closeOpenEnt.setEntCustId(companyCustId);
			closeOpenEnt.setCustType(Integer.parseInt(companyCustType));
			closeOpenEnt.setEntResNo(companyResNo);
			closeOpenEnt.setReqOptCustId(custId);
			closeOpenEnt.setReqOptCustName(custName);
			// 调用服务创建收款账户
			service.closeEnt(closeOpenEnt);

			hre = new HttpRespEnvelope();
		} catch (HsException e) {
			hre = new HttpRespEnvelope(e);
		}

		return hre;
	}

	/**
	 * 审批开启关闭系统
	 * 
	 * @param ApprParam
	 *            :审批参数
	 * @param request
	 * @return
	 */
	@RequestMapping(value = { "/apprCloseOpenEnt" }, method = {
			RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public HttpRespEnvelope apprCloseOpenEnt(ApprParam apprParam,
			String custId, String custName, HttpServletRequest request) {
		HttpRespEnvelope hre = null;

		try {
			// 验证安全令牌
			verifySecureToken(request);

			apprParam.setOptCustId(custId);
			apprParam.setOptName(custName);
			// 调用服务创建收款账户
			service.apprCloseOpenEnt(apprParam);

			hre = new HttpRespEnvelope();
		} catch (HsException e) {
			hre = new HttpRespEnvelope(e);
		}

		return hre;
	}

	/**
	 * 根据ID查询详情
	 * 
	 * @param applyId
	 *            :实体ID
	 * @param request
	 * @return
	 */
	@RequestMapping(value = { "/findById" }, method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public HttpRespEnvelope findById(String applyId, HttpServletRequest request) {
		HttpRespEnvelope hre = null;

		try {
			// 验证安全令牌
			verifySecureToken(request);
			// 调用服务创建收款账户
			CloseOpenEntDetail closeOpenEntDetail = service
					.queryCloseOpenEntDetail(applyId);

			hre = new HttpRespEnvelope(closeOpenEntDetail);
		} catch (HsException e) {
			hre = new HttpRespEnvelope(e);
		}

		return hre;
	}

	/**
	 * 根据ID查询企业上一次关开系统信息
	 * 
	 * @param applyId
	 *            :实体ID
	 * @param request
	 * @return
	 */
	@RequestMapping(value = { "/queryLastCloseOpenEntInfo" }, method = {
			RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public HttpRespEnvelope queryLastCloseOpenEntInfo(String companyCustId,
			Integer applyType, HttpServletRequest request) {
		HttpRespEnvelope hre = null;

		try {
			// 验证安全令牌
			verifySecureToken(request);
			// 调用服务创建收款账户
			CloseOpenEntInfo closeOpenEntInfo = service
					.queryLastCloseOpenEntInfo(companyCustId, applyType);

			hre = new HttpRespEnvelope(closeOpenEntInfo);
		} catch (HsException e) {
			hre = new HttpRespEnvelope(e);
		}

		return hre;
	}

	/**
	 * 分页查询开关系统申请
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "/queryCloseOpenEnt4Appr" }, method = {
			RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public HttpRespEnvelope queryCloseOpenEnt4Appr(HttpServletRequest request,
			HttpServletResponse response) {
		HttpRespEnvelope hre = null;
		try {
			// 验证安全令牌
			verifySecureToken(request);

			// 设置服务名和方法名，调用父类查询分页方法
			request.setAttribute("serviceName", service);
			request.setAttribute("methodName", "queryCloseOpenEnt4Appr");
			return super.doList(request, response);
		} catch (HsException e) {
			hre = new HttpRespEnvelope(e);
		}

		return hre;
	}

	/**
	 * 分页查询所有企业
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "/getAllEnt" }, method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public HttpRespEnvelope getAllEnt(HttpServletRequest request,
			HttpServletResponse response) {
		HttpRespEnvelope hre = null;
		try {
			// 验证安全令牌
			verifySecureToken(request);

			// 设置服务名和方法名，调用父类查询分页方法
			request.setAttribute("serviceName", service);
			request.setAttribute("methodName", "getAllEnt");
			return super.doList(request, response);
		} catch (HsException e) {
			hre = new HttpRespEnvelope(e);
		}

		return hre;
	}
	
	
	@RequestMapping(value = { "/updateTask" }, method = {
			RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public HttpRespEnvelope updateTask(String bizNo, Integer taskStatus, String exeCustId,
			HttpServletRequest request) {
		HttpRespEnvelope hre = null;

		try {
			// 验证安全令牌
			verifySecureToken(request);
			// 调用服务创建收款账户
			service.updateTask(bizNo, taskStatus, exeCustId);
			hre = new HttpRespEnvelope();
		} catch (HsException e) {
			hre = new HttpRespEnvelope(e);
		}

		return hre;
	}
	

	@Override
	protected IBaseService<ICloseOpenEntService> getEntityService() {
		return service;
	}
}
