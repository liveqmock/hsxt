/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.aps.controllers.contractManage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gy.hsxt.bs.bean.apply.ContractContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gy.hsxt.access.web.aps.services.contractManage.IContractManageService;
import com.gy.hsxt.access.web.aps.services.contractManage.imp.ContractManageService;
import com.gy.hsxt.access.web.common.bean.HttpRespEnvelope;
import com.gy.hsxt.access.web.common.constant.ASRespCode;
import com.gy.hsxt.access.web.common.controller.BaseController;
import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.access.web.common.utils.RequestUtil;
import com.gy.hsxt.bs.bean.apply.ContractSendHis;
import com.gy.hsxt.common.exception.HsException;

/**
 * 合同管理控制器
 * 
 * @Package: com.gy.hsxt.access.web.aps.controllers.contractManage
 * @ClassName: ContractQueryController
 * @Description: TODO
 * @author: likui
 * @date: 2016年1月21日 下午8:01:45
 * @company: gyist
 * @version V3.0.0
 */
@Controller
@RequestMapping("contractManage")
@SuppressWarnings("rawtypes")
public class ContractManageController extends BaseController<ContractManageService> {

	/**
	 * 合同管理Server
	 */
	@Autowired
	private IContractManageService contractManageService;

	/**
	 * 分页查询合同
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年1月28日 下午12:07:08
	 * @param request
	 * @param response
	 * @return
	 * @return : HttpRespEnvelope
	 * @version V3.0.0
	 */
	@RequestMapping(value = { "/find_contarct_by_page" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public HttpRespEnvelope findContarctByPage(HttpServletRequest request, HttpServletResponse response)
	{
		try
		{
			verifySecureToken(request);
			request.setAttribute("serviceName", contractManageService);
			request.setAttribute("methodName", "queryContarctByPage");
		} catch (HsException e)
		{
			return new HttpRespEnvelope(e);
		}
		return this.doList(request, response);
	}

	/**
	 * 查询打印合同内容
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年1月28日 下午12:17:45
	 * @param request
	 * @param response
	 * @return
	 * @return : HttpRespEnvelope
	 * @version V3.0.0
	 */
	@ResponseBody
	@RequestMapping(value = { "/find_contract_content_by_print" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope findContractContentByPrint(HttpServletRequest request, HttpServletResponse response)
	{
		String contractNo = request.getParameter("contractNo");// 合同编号
		try
		{
			verifySecureToken(request);
			RequestUtil.verifyParamsIsNotEmpty(new Object[] { contractNo, ASRespCode.ASP_CONTRACTNO_INVALID.getCode(),
					ASRespCode.ASP_CONTRACTNO_INVALID.getDesc() });
			return new HttpRespEnvelope(contractManageService.getContractContentByPrint(contractNo));
		} catch (HsException e)
		{
			return new HttpRespEnvelope(e);
		}
	}

	/**
	 * 分页查询盖章合同
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年1月28日 下午2:09:02
	 * @param request
	 * @param response
	 * @return
	 * @return : HttpRespEnvelope
	 * @version V3.0.0
	 */
	@RequestMapping(value = { "/find_seal_contarct_by_page" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public HttpRespEnvelope findSealContarctByPage(HttpServletRequest request, HttpServletResponse response)
	{
		try
		{
			verifySecureToken(request);
			request.setAttribute("serviceName", contractManageService);
			request.setAttribute("methodName", "querySealContarctByPage");
		} catch (HsException e)
		{
			return new HttpRespEnvelope(e);
		}
		return this.doList(request, response);
	}

	/**
	 * 查询盖章合同内容
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年1月28日 下午2:20:23
	 * @param request
	 * @param response
	 * @return
	 * @return : HttpRespEnvelope
	 * @version V3.0.0
	 */
	@ResponseBody
	@RequestMapping(value = { "/find_contract_content_by_seal" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope findContractContentBySeal(HttpServletRequest request, HttpServletResponse response)
	{
		String contractNo = request.getParameter("contractNo");// 合同编号
		String realTime = request.getParameter("realTime");// 合同编号
		try
		{
			verifySecureToken(request);
			RequestUtil.verifyParamsIsNotEmpty(new Object[] { contractNo, ASRespCode.ASP_CONTRACTNO_INVALID.getCode(),
					ASRespCode.ASP_CONTRACTNO_INVALID.getDesc() });
			return new HttpRespEnvelope(contractManageService.queryContractContentBySeal(contractNo,Boolean.valueOf(realTime)));
		} catch (HsException e)
		{
			return new HttpRespEnvelope(e);
		}
	}

	/**
	 * 查询合同预览内容
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年1月28日 下午2:21:43
	 * @param request
	 * @param response
	 * @return
	 * @return : HttpRespEnvelope
	 * @version V3.0.0
	 */
	@ResponseBody
	@RequestMapping(value = { "/find_contract_content_by_view" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope findContractContentByView(HttpServletRequest request, HttpServletResponse response)
	{
		String contractNo = request.getParameter("contractNo");// 合同编号
		try
		{
			verifySecureToken(request);
			RequestUtil.verifyParamsIsNotEmpty(new Object[] { contractNo, ASRespCode.ASP_CONTRACTNO_INVALID.getCode(),
					ASRespCode.ASP_CONTRACTNO_INVALID.getDesc() });
			return new HttpRespEnvelope(contractManageService.queryContractContentByView(contractNo));
		} catch (HsException e)
		{
			return new HttpRespEnvelope(e);
		}
	}

	/**
	 * 分页查询合同发放
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年1月28日 下午2:23:07
	 * @param request
	 * @param response
	 * @return
	 * @return : HttpRespEnvelope
	 * @version V3.0.0
	 */
	@RequestMapping(value = { "/find_contract_give_out_by_page" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public HttpRespEnvelope findContractGiveOutByPage(HttpServletRequest request, HttpServletResponse response)
	{
		try
		{
			verifySecureToken(request);
			request.setAttribute("serviceName", contractManageService);
			request.setAttribute("methodName", "queryContractGiveOutByPage");
		} catch (HsException e)
		{
			return new HttpRespEnvelope(e);
		}
		return this.doList(request, response);
	}

	/**
	 * 合同发放
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年1月28日 下午2:28:21
	 * @param request
	 * @param response
	 * @return
	 * @return : HttpRespEnvelope
	 * @version V3.0.0
	 */
	@ResponseBody
	@RequestMapping(value = { "/opt_contract_give_out" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope optContractGiveOut(HttpServletRequest request, HttpServletResponse response,ContractSendHis bean)
	{
		try
		{
			verifySecureToken(request);
			bean.setOperator(request.getParameter("operName"));
			contractManageService.contractGiveOut(bean);
			return new HttpRespEnvelope();
		} catch (HsException e)
		{
			return new HttpRespEnvelope(e);
		}
	}

	/**
	 * 查询合同发放历史
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年1月28日 下午2:29:33
	 * @param request
	 * @param response
	 * @return
	 * @return : HttpRespEnvelope
	 * @version V3.0.0
	 */
	@ResponseBody
	@RequestMapping(value = { "/find_contract_give_out_recode" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope findContractGiveOutRecode(HttpServletRequest request, HttpServletResponse response)
	{
		String contractNo = request.getParameter("contractNo");// 合同编号
		try
		{
			verifySecureToken(request);
			
			request.setAttribute("serviceName", contractManageService);
			request.setAttribute("methodName", "queryContractGiveOutRecode");
		
		} catch (HsException e)
		{
			return new HttpRespEnvelope(e);
		}
		return this.doList(request, response);
	}
	/**
	 * 合同盖章
	 * @param request
	 * @param contractNo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/sealContract" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope sealContract(HttpServletRequest request,String contractNo){
		try
		{
			verifySecureToken(request);
			ContractContent contract =contractManageService.sealContract(contractNo);
			return new HttpRespEnvelope(contract);
		} catch(Exception e)
		{
			return new HttpRespEnvelope(e);
		}
	}
	
	/**
	 * 合同打印
	 * @param request
	 * @param contractNo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/printContract" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope printContract(HttpServletRequest request,String contractNo){
		try
		{
			verifySecureToken(request);
			contractManageService.printContract(contractNo);
			return new HttpRespEnvelope();
		} catch(Exception e)
		{
			return new HttpRespEnvelope(e);
		}
	}
	
	@Override
	protected IBaseService getEntityService()
	{
		return contractManageService;
	}
}
