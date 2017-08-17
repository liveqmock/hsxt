/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.aps.controllers.userpassword;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.gy.hsxt.access.web.aps.services.userpassword.EntjylistpwdService;
import com.gy.hsxt.access.web.aps.services.userpassword.EntjypwdService;
import com.gy.hsxt.access.web.common.bean.HttpRespEnvelope;
import com.gy.hsxt.access.web.common.controller.BaseController;
import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.bs.bean.pwd.ResetTransPwd;
import com.gy.hsxt.common.exception.HsException;

/**
 * 
 * 
 * @Package: com.gy.hsxt.access.web.aps.controllers.userpassword
 * @ClassName: EntjyPwdController
 * @Description: TODO
 * 
 * @author: lyh
 * @date: 2016-1-25 下午12:24:03
 * @version V3.0
 */
@Controller
@RequestMapping("entjylistPwd")
public class EntjylistPwdController extends BaseController<EntjypwdService> {

	@Resource
	private EntjylistpwdService entjylistpwdService;

	@SuppressWarnings("rawtypes")
	protected IBaseService getEntityService() {
		return entjylistpwdService;
	}

	/**
	 * 查询分页
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/listEntJypwdinfopage" }, method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope listEntJyPwdInfoPage(HttpServletRequest request,
			HttpServletResponse response) {
		HttpRespEnvelope hre = null;
		try {
			// Token验证
			super.checkSecureToken(request);
			// 分页查询
			request.setAttribute("serviceName", entjylistpwdService);
			request.setAttribute("methodName", "findScrollResult");
			hre = super.doList(request, response);
		} catch (HsException e) {
			hre = new HttpRespEnvelope(e);
		}
		return hre;
	}

	@ResponseBody
	@RequestMapping(value = { "/apprResetTranPwdApply" }, method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope apprResetTranPwdApply(HttpServletRequest request,
			HttpServletResponse response) {
		HttpRespEnvelope hre = null;
		try {
			// Token验证
			super.checkSecureToken(request);
			String optRemark = request.getParameter("optRemark");
//			String dblOptCustId = request.getParameter("dblOptCustId");
			String applyId = request.getParameter("applyId");
			String updatedby = request.getParameter("custId");
			String entCustId = request.getParameter("entCustIdjymmcz");
			String statusStr = request.getParameter("status");
			int status = Integer.parseInt(statusStr);
			String applyReason = request.getParameter("applyReason");
			
			ResetTransPwd resetTransPwd = new ResetTransPwd();
	        resetTransPwd.setApplyId(applyId);
	        resetTransPwd.setUpdatedby(updatedby);
	        resetTransPwd.setStatus(status);
	        resetTransPwd.setApplyReason(applyReason);
	        resetTransPwd.setEntCustId(entCustId);
	        resetTransPwd.setApprRemark(optRemark);
	        
			entjylistpwdService.apprResetTranPwdApply(resetTransPwd);
			hre = new HttpRespEnvelope();
		} catch (HsException e) {
			hre = new HttpRespEnvelope(e);
		}
		return hre;
	}
}
