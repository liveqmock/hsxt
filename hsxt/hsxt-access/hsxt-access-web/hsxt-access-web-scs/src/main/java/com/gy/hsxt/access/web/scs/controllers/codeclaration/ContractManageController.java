package com.gy.hsxt.access.web.scs.controllers.codeclaration;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gy.hsxt.access.web.common.bean.HttpRespEnvelope;
import com.gy.hsxt.access.web.common.constant.ASRespCode;
import com.gy.hsxt.access.web.common.controller.BaseController;
import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.access.web.common.utils.RequestUtil;
import com.gy.hsxt.access.web.scs.services.codeclaration.IContractManageService;
import com.gy.hsxt.common.exception.HsException;

/**
 * 
 * @projectName   : hsxt-access-web-scs
 * @package       : com.gy.hsxt.access.web.scs.controllers.codeclaration
 * @className     : ContractManageController.java
 * @description   : 合同管理
 * @author        : maocy
 * @createDate    : 2015-10-28
 * @updateUser    : 
 * @updateDate    : 
 * @updateRemark  : 
 * @version       : v0.0.1
 */
@Controller
@RequestMapping("contractManageController")
public class ContractManageController extends BaseController{
	
	@Resource
    private IContractManageService contractManageService; //合同管理服务类
	
	@Override
	protected HttpRespEnvelope beforeList(HttpServletRequest request, Map filterMap, Map sortMap) {
		try {
			super.verifyPointNo(request);//校验互生卡号
		} catch (HsException e) {
			return new HttpRespEnvelope(e);
		}
		return null;
	}

	@Override
	protected IBaseService getEntityService() {
		return contractManageService;
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
}
