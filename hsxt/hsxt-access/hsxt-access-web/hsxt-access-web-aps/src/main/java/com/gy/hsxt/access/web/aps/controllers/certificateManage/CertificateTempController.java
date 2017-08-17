/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.aps.controllers.certificateManage;

import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.access.web.aps.services.certificateManage.ICertificateTempService;
import com.gy.hsxt.access.web.common.bean.HttpRespEnvelope;
import com.gy.hsxt.access.web.common.constant.ASRespCode;
import com.gy.hsxt.access.web.common.controller.BaseController;
import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.access.web.common.utils.RequestUtil;
import com.gy.hsxt.bs.bean.apply.CertificateContent;
import com.gy.hsxt.bs.bean.apply.TemplateAppr;
import com.gy.hsxt.bs.bean.apply.Templet;
import com.gy.hsxt.bs.bean.base.ApprParam;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;

/**
 * 证书模板控制器
 * 
 * @Package: com.gy.hsxt.access.web.aps.controllers.certificateManage
 * @ClassName: CertificateTempController
 * @Description: TODO
 * @author: likui
 * @date: 2016年1月22日 上午11:03:38
 * @company: gyist
 * @version V3.0.0
 */
@Controller
@RequestMapping("certificateTemp")
@SuppressWarnings("rawtypes")
public class CertificateTempController extends BaseController<T> {

	/**
	 * 证书模板Service
	 */
	@Autowired
	private ICertificateTempService certificateTempService;

	/**
	 * 分页查询证书模板
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年1月28日 下午3:27:33
	 * @param request
	 * @param response
	 * @return
	 * @return : HttpRespEnvelope
	 * @version V3.0.0
	 */
	@RequestMapping(value = { "/find_certificate_temp_by_page" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public HttpRespEnvelope findCertificateTempByPage(HttpServletRequest request, HttpServletResponse response)
	{
		try
		{
			verifySecureToken(request);
			request.setAttribute("serviceName", certificateTempService);
			request.setAttribute("methodName", "queryCertificateTempByPage");
		} catch (HsException e)
		{
			return new HttpRespEnvelope(e);
		}
		return this.doList(request, response);
	}

	/**
	 * 新增证书模板
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年1月28日 下午3:29:10
	 * @param request
	 * @param response
	 * @return
	 * @return : HttpRespEnvelope
	 * @version V3.0.0
	 */
	@ResponseBody
	@RequestMapping(value = { "/create_certificate_temp" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope createCertificateTemp(HttpServletRequest request, HttpServletResponse response,Templet templet)
	{
		try
		{
			templet.setCreatedBy(request.getParameter("custName")+"("+request.getParameter("operName")+")");
			verifySecureToken(request);
			certificateTempService.addCertificateTemp(templet);
			return new HttpRespEnvelope();
		} catch (HsException e)
		{
			return new HttpRespEnvelope(e);
		}
	}

	/**
	 * 修改证书模板
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年1月28日 下午3:34:19
	 * @param request
	 * @param response
	 * @return
	 * @return : HttpRespEnvelope
	 * @version V3.0.0
	 */
	@ResponseBody
	@RequestMapping(value = { "/modify_certificate_temp" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope modifyCertificateTemp(HttpServletRequest request, HttpServletResponse response, Templet templet)
	{
		try
		{
			verifySecureToken(request);
			templet.setUpdatedBy(request.getParameter("custName")+"("+request.getParameter("operName")+")");
			certificateTempService.modifyCertificateTemp(templet);
			return new HttpRespEnvelope();
		} catch (HsException e)
		{
			return new HttpRespEnvelope(e);
		}
	}

	/**
	 * 根据ID查询证书模板
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年1月28日 下午3:35:35
	 * @param request
	 * @param response
	 * @return
	 * @return : HttpRespEnvelope
	 * @version V3.0.0
	 */
	@ResponseBody
	@RequestMapping(value = { "/find_certificate_temp_by_id" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope findCertificateTempById(HttpServletRequest request, HttpServletResponse response)
	{
		String templetId = request.getParameter("templetId");// 模板编号
		try
		{
			verifySecureToken(request);
			RequestUtil.verifyParamsIsNotEmpty(new Object[] { templetId, ASRespCode.ASP_DOC_TEMP_ID_INVALID.getCode(),
					ASRespCode.ASP_DOC_TEMP_ID_INVALID.getDesc() });
			return new HttpRespEnvelope(certificateTempService.queryCertificateTempById(templetId));
		} catch (HsException e)
		{
			return new HttpRespEnvelope(e);
		}
	}

	/**
	 * 启用证书模板
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年1月28日 下午3:37:52
	 * @param request
	 * @param response
	 * @return
	 * @return : HttpRespEnvelope
	 * @version V3.0.0
	 */
	@ResponseBody
	@RequestMapping(value = { "/enable_certificate_temp" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope enableCertificateTemp(HttpServletRequest request, HttpServletResponse response)
	{
		String templetId = request.getParameter("templetId");// 模板编号
		String custId = request.getParameter("custName")+"("+request.getParameter("operName")+")";// 操作员客户号
		try
		{
			verifySecureToken(request);
			RequestUtil.verifyParamsIsNotEmpty(new Object[] { templetId, ASRespCode.ASP_DOC_TEMP_ID_INVALID.getCode(),
					ASRespCode.ASP_DOC_TEMP_ID_INVALID.getDesc() }, new Object[] { custId,
					ASRespCode.ASP_DOC_OPTCUSTID_INVALID.getCode(), ASRespCode.ASP_DOC_OPTCUSTID_INVALID.getDesc() });
			certificateTempService.enableCertificateTemp(templetId, custId);
			return new HttpRespEnvelope();
		} catch (HsException e)
		{
			return new HttpRespEnvelope(e);
		}
	}

	/**
	 * 停用证书模板
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年1月28日 下午3:39:22
	 * @param request
	 * @param response
	 * @return
	 * @return : HttpRespEnvelope
	 * @version V3.0.0
	 */
	@ResponseBody
	@RequestMapping(value = { "/stop_certificate_temp" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope stopCertificateTemp(HttpServletRequest request, HttpServletResponse response)
	{
		String templetId = request.getParameter("templetId");// 模板编号
		String custId = request.getParameter("custName")+"("+request.getParameter("operName")+")";// 操作员客户号
		try
		{
			verifySecureToken(request);
			RequestUtil.verifyParamsIsNotEmpty(new Object[] { templetId, ASRespCode.ASP_DOC_TEMP_ID_INVALID.getCode(),
					ASRespCode.ASP_DOC_TEMP_ID_INVALID.getDesc() }, new Object[] { custId,
					ASRespCode.ASP_DOC_OPTCUSTID_INVALID.getCode(), ASRespCode.ASP_DOC_OPTCUSTID_INVALID.getDesc() });
			certificateTempService.stopCertificateTemp(templetId, custId);
			return new HttpRespEnvelope();
		} catch (HsException e)
		{
			return new HttpRespEnvelope(e);
		}
	}

	/**
	 * 分页查询证书模板审批
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年1月28日 下午3:45:12
	 * @param request
	 * @param response
	 * @return
	 * @return : HttpRespEnvelope
	 * @version V3.0.0
	 */
	@RequestMapping(value = { "/find_certificate_temp_appr_by_page" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public HttpRespEnvelope findCertificateTempApprByPage(HttpServletRequest request, HttpServletResponse response)
	{
		try
		{
			verifySecureToken(request);
			request.setAttribute("serviceName", certificateTempService);
			request.setAttribute("methodName", "queryCertificateTempApprByPage");
		} catch (HsException e)
		{
			return new HttpRespEnvelope(e);
		}
		return this.doList(request, response);
	}

	/**
	 * 证书模板审批
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年1月28日 下午3:46:03
	 * @param request
	 * @param response
	 * @return
	 * @return : HttpRespEnvelope
	 * @version V3.0.0
	 */
	@ResponseBody
	@RequestMapping(value = { "/certificate_temp_appr" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope certificateTempAppr(HttpServletRequest request, HttpServletResponse response,TemplateAppr bean)
	{
		try
		{
			verifySecureToken(request);
			RequestUtil.verifyParamsIsNotEmpty(
					new Object[] { bean.getTempletId(), ASRespCode.ASP_DOC_TEMP_ID_INVALID.getCode(),
							ASRespCode.ASP_DOC_TEMP_ID_INVALID.getDesc()},
					new Object[] { bean.getApprStatus(), ASRespCode.ASP_DOC_TEMP_ID_INVALID.getCode(),
							ASRespCode.ASP_DOC_TEMP_ID_INVALID.getDesc()},
					new Object[] { bean.getApprResult(), ASRespCode.ASP_DOC_TEMP_APPRSTATUS_INVALID.getCode(),
							ASRespCode.ASP_DOC_TEMP_APPRSTATUS_INVALID.getDesc()}, 
					new Object[] { bean.getApprOperator(), ASRespCode.ASP_DOC_OPTCUSTID_INVALID.getCode(),
							ASRespCode.ASP_DOC_OPTCUSTID_INVALID.getDesc()});
			certificateTempService.certificateTempAppr(bean);
			return new HttpRespEnvelope();
		} catch (HsException e)
		{
			// 12000 参数错误
			// 12686 证书模板审批失败
			return new HttpRespEnvelope(e);
		}catch (Exception ex){
			return new HttpRespEnvelope(ex);
		}
		
	}
	
	@ResponseBody
	@RequestMapping(value = { "/queryLastTemplateAppr" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope queryLastTemplateAppr(HttpServletRequest request, HttpServletResponse response,String templetId)
	{
		try
		{
			verifySecureToken(request);
			TemplateAppr ta = certificateTempService.queryLastTemplateAppr(templetId);
			return new HttpRespEnvelope(ta);
		}
		catch(Exception e){
			return new HttpRespEnvelope();
		}
		
	}
	

	@Override
	protected IBaseService getEntityService()
	{
		return certificateTempService;
	}
}
