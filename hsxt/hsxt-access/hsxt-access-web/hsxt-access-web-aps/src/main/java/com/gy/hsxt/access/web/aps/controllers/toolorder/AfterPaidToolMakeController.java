/**
 * 
 */
package com.gy.hsxt.access.web.aps.controllers.toolorder;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gy.hsxt.access.web.aps.services.toolorder.IAfterPaidToolMakeService;
import com.gy.hsxt.access.web.common.bean.HttpRespEnvelope;
import com.gy.hsxt.access.web.common.controller.BaseController;
import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.bs.bean.tool.AfterKeepConfig;
import com.gy.hsxt.bs.bean.tool.AfterServiceConfig;
import com.gy.hsxt.bs.bean.tool.AfterServiceDetail;

/**
 * @author weiyq
 *
 */
@SuppressWarnings("rawtypes")
@Controller
@RequestMapping("afterPaidToolMake")
public class AfterPaidToolMakeController extends BaseController{
	
	@Autowired
	private IAfterPaidToolMakeService afterPaidToolMakeService;
	
	@ResponseBody
    @RequestMapping(value = { "/queryAfterConfigDetail" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope queryAfterConfigDetail(HttpServletRequest request,String confNo){
		try{
			verifySecureToken(request);
			List<AfterServiceDetail> list = afterPaidToolMakeService.queryAfterConfigDetail(confNo);
			return new HttpRespEnvelope(list);
		}catch(Exception e){
			return new HttpRespEnvelope(e);
		}
	}
	
	@ResponseBody
    @RequestMapping(value = { "/reassociation" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope reassociation(HttpServletRequest request,AfterServiceConfig asc){
		try{
			verifySecureToken(request);
			String entCustId = request.getParameter("custIdEnt");
			asc.setEntCustId(entCustId);
			afterPaidToolMakeService.configMcrRelationAfter(asc);
			return new HttpRespEnvelope();
		}catch(Exception e){
			return new HttpRespEnvelope(e);
		}
	}
	
	@ResponseBody
    @RequestMapping(value = { "/keepAssociation" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope keepDeviceRelationAfter(HttpServletRequest request,AfterKeepConfig asc){
		try{
			verifySecureToken(request);
			String entCustId = request.getParameter("custIdEnt");
			asc.setEntCustId(entCustId);
			afterPaidToolMakeService.keepDeviceRelationAfter(asc);
			return new HttpRespEnvelope();
		}catch(Exception e){
			return new HttpRespEnvelope(e);
		}
	}
	
	@Override
	protected IBaseService getEntityService() {
		return afterPaidToolMakeService;
	}
}
