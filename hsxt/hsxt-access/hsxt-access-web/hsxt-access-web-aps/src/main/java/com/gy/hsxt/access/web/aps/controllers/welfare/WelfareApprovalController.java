package com.gy.hsxt.access.web.aps.controllers.welfare;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gy.hsxt.access.web.aps.services.welfare.IWelfareApprovalService;
import com.gy.hsxt.access.web.common.bean.HttpRespEnvelope;
import com.gy.hsxt.access.web.common.controller.BaseController;
import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.ws.bean.ApprovalQueryCondition;

/**
 * 积分福利审批控制类
 * 
 * @category 积分福利审批控制类
 * @projectName hsxt-access-web-aps
 * @package 
 *          com.gy.hsxt.access.web.aps.controllers.welfare.WelfareApprovalController
 *          .java
 * @className WelfareApprovalController // * @description 积分福利审批控制类
 * @author leiyt
 * @createDate 2015-12-31 下午5:04:59
 * @updateUser leiyt
 * @updateDate 2015-12-31 下午5:04:59
 * @updateRemark 说明本次修改内容
 * @version v0.0.1
 */
@Controller
@RequestMapping("welfareApproval")
public class WelfareApprovalController extends BaseController<Object> {
	@Autowired
	IWelfareApprovalService welfareApprovalService;

	/**
	 * 积分福利审批
	 * 
	 * @param approvalId
	 * @param approvalResult
	 * @param replyAmount
	 * @param remark
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "/approvalWelfare" }, method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public HttpRespEnvelope approvalWelfare(String approvalId, Integer approvalResult,
			String replyAmount, String remark, HttpServletRequest request,
			HttpServletResponse response) {
		HttpRespEnvelope hre = null;
		try {
			welfareApprovalService.approvalWelfare(approvalId, approvalResult, replyAmount, remark);
			hre = new HttpRespEnvelope();
		} catch (HsException e) {
			hre = new HttpRespEnvelope(e);
		}
		return hre;
	}

	/**
	 * 查询待审批记录
	 * 
	 * @param approvalId
	 * @param approvalResult
	 * @param replyAmount
	 * @param approvalStep
	 * @param remark
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "/listPendingApproval" }, method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public HttpRespEnvelope listPendingApproval(HttpServletRequest request,
			HttpServletResponse response) {
		HttpRespEnvelope hre = null;

		try {
			// 分页查询
			request.setAttribute("serviceName", welfareApprovalService);
			request.setAttribute("methodName", "findScrollResult");
			hre = super.doList(request, response);
		} catch (HsException e) {
			hre = new HttpRespEnvelope(e);
		}

		return hre;
	}

	/**
	 * 查询已审批记录
	 * 
	 * @param queryCondition
	 * @param page
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "/listApprovalRecord" }, method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public HttpRespEnvelope listApprovalRecord(ApprovalQueryCondition queryCondition, Page page,
			HttpServletRequest request, HttpServletResponse response) {
		HttpRespEnvelope hre = null;
		try {
			// 分页查询
			request.setAttribute("serviceName", welfareApprovalService);
			request.setAttribute("methodName", "findScrollResult2");
			hre = super.doList(request, response);
		} catch (HsException e) {
			hre = new HttpRespEnvelope(e);
		}
		return hre;
	}

	/**
	 * 查询审批详情
	 * 
	 * @param approvalId
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "/queryApprovalDetail" }, method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public HttpRespEnvelope queryApprovalDetail(String approvalId, HttpServletRequest request,
			HttpServletResponse response) {
		HttpRespEnvelope hre = null;
		try {
			hre = new HttpRespEnvelope(welfareApprovalService.queryApprovalDetail(approvalId));
		} catch (HsException e) {
			hre = new HttpRespEnvelope(e);
		}
		return hre;
	}

	/**
	 * 更新任务状态
	 * @param bizNo
	 * @param taskStatus
	 * @param remark
	 * @param request
	 * @return
	 */
	@RequestMapping(value = { "/updateWsTask" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public HttpRespEnvelope updateWsTask(String bizNo, Integer taskStatus, String remark,
			HttpServletRequest request) {
		HttpRespEnvelope hre = null;

		try {
			welfareApprovalService.updateTask(bizNo, taskStatus, remark);
			hre = new HttpRespEnvelope();
		} catch (HsException e) {
			hre = new HttpRespEnvelope(e);
		}

		return hre;
	}

	@Override
	protected IBaseService<Object> getEntityService() {
		return null;
	}

}
