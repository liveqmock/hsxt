/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.aps.controllers.contractManage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gy.hsxt.access.web.aps.services.contractManage.IContractTempManageService;
import com.gy.hsxt.access.web.common.bean.HttpRespEnvelope;
import com.gy.hsxt.access.web.common.constant.ASRespCode;
import com.gy.hsxt.access.web.common.controller.BaseController;
import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.access.web.common.utils.RequestUtil;
import com.gy.hsxt.bs.bean.apply.TemplateAppr;
import com.gy.hsxt.bs.bean.apply.Templet;
import com.gy.hsxt.common.exception.HsException;

/**
 * 合同模板管理
 * 
 * @Package: com.gy.hsxt.access.web.aps.controllers.contractManage
 * @ClassName: ContractTempManageController
 * @Description: TODO
 * @author: likui
 * @date: 2016年1月22日 上午10:20:11
 * @company: gyist
 * @version V3.0.0
 */
@Controller
@RequestMapping("contractTempManage")
@SuppressWarnings("rawtypes")
public class ContractTempManageController extends BaseController<T> {

	/**
	 * 合同模板Service
	 */
	@Autowired
	private IContractTempManageService contractTempManageService;

	/**
	 * 分页查询合同模板
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年1月28日 下午2:34:34
	 * @param request
	 * @param response
	 * @return
	 * @return : HttpRespEnvelope
	 * @version V3.0.0
	 */
	@RequestMapping(value = { "/find_contract_temp_by_page" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public HttpRespEnvelope findContractTempByPage(HttpServletRequest request, HttpServletResponse response)
	{
		try
		{
			verifySecureToken(request);
			request.setAttribute("serviceName", contractTempManageService);
			request.setAttribute("methodName", "queryContractTempByPage");
		} catch (HsException e)
		{
			return new HttpRespEnvelope(e);
		}
		return this.doList(request, response);
	}

	/**
	 * 根据编号查询合同模板
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年1月28日 下午2:37:34
	 * @param request
	 * @param response
	 * @return
	 * @return : HttpRespEnvelope
	 * @version V3.0.0
	 */
	@ResponseBody
	@RequestMapping(value = { "/find_contract_temp_by_id" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope findContractTempById(HttpServletRequest request, HttpServletResponse response)
	{
		String templetId = request.getParameter("templetId");// 模板编号
		try
		{
			verifySecureToken(request);
			RequestUtil.verifyParamsIsNotEmpty(new Object[] { templetId, ASRespCode.ASP_DOC_TEMP_ID_INVALID.getCode(),
					ASRespCode.ASP_DOC_TEMP_ID_INVALID.getDesc() });
			return new HttpRespEnvelope(contractTempManageService.queryContractTempById(templetId));
		} catch (HsException e)
		{
			return new HttpRespEnvelope(e);
		}
	}

	/**
	 * 创建合同模板
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年1月28日 下午2:39:12
	 * @param request
	 * @param response
	 * @return
	 * @return : HttpRespEnvelope
	 * @version V3.0.0
	 */
	@ResponseBody
	@RequestMapping(value = { "/create_contract_temp" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope createContractTemp(HttpServletRequest request, HttpServletResponse response, Templet bean)
	{
		try
		{
			verifySecureToken(request);
			contractTempManageService.addContractTemp(bean);
			return new HttpRespEnvelope();
		} catch (HsException e)
		{
			return new HttpRespEnvelope(e);
		}
		
	}

	/**
	 * 修改合同模板
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年1月28日 下午2:43:45
	 * @param request
	 * @param response
	 * @return
	 * @return : HttpRespEnvelope
	 * @version V3.0.0
	 */
	@ResponseBody
	@RequestMapping(value = { "/modify_contract_temp" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope modifyContractTemp(HttpServletRequest request, HttpServletResponse response, Templet bean)
	{
		try
		{
			verifySecureToken(request);
			contractTempManageService.modifyContractTemp(bean);
			return new HttpRespEnvelope();
		} catch (HsException e)
		{
			return new HttpRespEnvelope(e);
		}
	}

	/**
	 * 合同模板启用
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年1月28日 下午2:46:19
	 * @param request
	 * @param response
	 * @return
	 * @return : HttpRespEnvelope
	 * @version V3.0.0
	 */
	@ResponseBody
	@RequestMapping(value = { "/enable_contract_temp" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope enableContractTemp(HttpServletRequest request, HttpServletResponse response)
	{
		String templetId = request.getParameter("templetId");// 模板编号
		String custId = request.getParameter("custId");// 操作员客户号
		try
		{
			verifySecureToken(request);
			RequestUtil.verifyParamsIsNotEmpty(new Object[] { templetId, ASRespCode.ASP_DOC_TEMP_ID_INVALID.getCode(),
					ASRespCode.ASP_DOC_TEMP_ID_INVALID.getDesc() }, new Object[] { custId,
					ASRespCode.ASP_DOC_OPTCUSTID_INVALID.getCode(), ASRespCode.ASP_DOC_OPTCUSTID_INVALID.getDesc() });
			contractTempManageService.enableContractTemp(templetId, custId);
			return new HttpRespEnvelope();
		} catch (HsException e)
		{
			return new HttpRespEnvelope(e);
		}
	}

	/**
	 * 停用合同模板
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年1月28日 下午2:47:01
	 * @param request
	 * @param response
	 * @return
	 * @return : HttpRespEnvelope
	 * @version V3.0.0
	 */
	@ResponseBody
	@RequestMapping(value = { "/stop_contract_temp" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope stopContractTemp(HttpServletRequest request, HttpServletResponse response)
	{
		String templetId = request.getParameter("templetId");// 模板编号
		String custId = request.getParameter("custId");// 操作员客户号
		try
		{
			verifySecureToken(request);
			RequestUtil.verifyParamsIsNotEmpty(new Object[] { templetId, ASRespCode.ASP_DOC_TEMP_ID_INVALID.getCode(),
					ASRespCode.ASP_DOC_TEMP_ID_INVALID.getDesc() }, new Object[] { custId,
					ASRespCode.ASP_DOC_OPTCUSTID_INVALID.getCode(), ASRespCode.ASP_DOC_OPTCUSTID_INVALID.getDesc() });
			contractTempManageService.stopContractTemp(templetId, custId);
			return new HttpRespEnvelope();
		} catch (HsException e)
		{
			return new HttpRespEnvelope(e);
		}
	}

	/**
	 * 分页查询合同模板审批
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年1月28日 下午2:47:58
	 * @param request
	 * @param response
	 * @return
	 * @return : HttpRespEnvelope
	 * @version V3.0.0
	 */
	@RequestMapping(value = { "/find_contract_temp_appr_by_page" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public HttpRespEnvelope findContractTempApprByPage(HttpServletRequest request, HttpServletResponse response)
	{
		try
		{
			verifySecureToken(request);
			request.setAttribute("serviceName", contractTempManageService);
			request.setAttribute("methodName", "queryContractTempApprByPage");
		} catch (HsException e)
		{
			return new HttpRespEnvelope(e);
		}
		return this.doList(request, response);
	}

	/**
	 * 合同模板审批
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年1月28日 下午2:51:25
	 * @param request
	 * @param response
	 * @return
	 * @return : HttpRespEnvelope
	 * @version V3.0.0
	 */
	@ResponseBody
	@RequestMapping(value = { "/contract_temp_appr" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope contractTempAppr(HttpServletRequest request, HttpServletResponse response, TemplateAppr bean)
	{
		try
		{
			verifySecureToken(request);
			contractTempManageService.contractTempAppr(bean);
			return new HttpRespEnvelope();
		} catch (HsException e)
		{
			return new HttpRespEnvelope(e);
		}
	}

	@Override
	protected IBaseService getEntityService()
	{
		return contractTempManageService;
	}
}
