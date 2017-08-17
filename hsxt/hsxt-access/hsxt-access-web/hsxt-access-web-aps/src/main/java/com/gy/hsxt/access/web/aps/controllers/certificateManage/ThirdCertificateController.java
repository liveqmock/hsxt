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
import com.gy.hsxt.access.web.aps.services.certificateManage.IThirdCertificateService;
import com.gy.hsxt.access.web.common.bean.HttpRespEnvelope;
import com.gy.hsxt.access.web.common.constant.ASRespCode;
import com.gy.hsxt.access.web.common.controller.BaseController;
import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.access.web.common.utils.RequestUtil;
import com.gy.hsxt.bs.bean.apply.Certificate;
import com.gy.hsxt.bs.bean.apply.CertificateContent;
import com.gy.hsxt.bs.bean.apply.CertificateSendHis;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;

/**
 * 第三方证书控制器
 * 
 * @Package: com.gy.hsxt.access.web.aps.controllers.certificateManage
 * @ClassName: ThirdCertificateController
 * @Description: TODO
 * @author: likui
 * @date: 2016年1月22日 上午11:04:10
 * @company: gyist
 * @version V3.0.0
 */
@Controller
@RequestMapping("thirdCertificate")
@SuppressWarnings("rawtypes")
public class ThirdCertificateController extends BaseController<T> {

	/**
	 * 第三方证书Service
	 */
	@Autowired
	private IThirdCertificateService thirdCertificateService;

	/**
	 * 分页查询第三方证书发放
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年1月28日 下午4:26:40
	 * @param request
	 * @param response
	 * @return
	 * @return : HttpRespEnvelope
	 * @version V3.0.0
	 */
	@RequestMapping(value = { "/find_third_certificate_give_out_by_page" }, method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public HttpRespEnvelope findThirdCertificateGiveOutByPage(HttpServletRequest request, HttpServletResponse response)
	{
		try
		{
			verifySecureToken(request);
			request.setAttribute("serviceName", thirdCertificateService);
			request.setAttribute("methodName", "queryThirdCertificateGiveOutByPage");
		} catch (HsException e)
		{
			return new HttpRespEnvelope(e);
		}
		return this.doList(request, response);
	}

	/**
	 * 打印第三方证书
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年1月28日 下午3:19:42
	 * @param request
	 * @param response
	 * @return
	 * @return : HttpRespEnvelope
	 * @version V3.0.0
	 */
	@ResponseBody
	@RequestMapping(value = { "/print_third_certificate" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope printThirdCertificate(HttpServletRequest request, HttpServletResponse response)
	{
		String certificateNo = request.getParameter("certificateNo");// 证书编号
		String custId = request.getParameter("custName")+"("+request.getParameter("operName")+")";// 操作员客户号  改为operName 20160405
		try
		{
			verifySecureToken(request);
			RequestUtil.verifyParamsIsNotEmpty(
					new Object[] { certificateNo, ASRespCode.ASP_CERTIFICATENO_INVALID.getCode(),
							ASRespCode.ASP_CERTIFICATENO_INVALID.getDesc() },
					new Object[] { custId, ASRespCode.ASP_DOC_OPTCUSTID_INVALID.getCode(),
							ASRespCode.ASP_DOC_OPTCUSTID_INVALID.getDesc() });
			return new HttpRespEnvelope(thirdCertificateService.printThirdCertificate(certificateNo, custId));
		} catch (HsException e)
		{
			return new HttpRespEnvelope(e);
		}
	}

	/**
	 * 发放第三方证书
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年1月28日 下午3:24:03
	 * @param request
	 * @param response
	 * @return
	 * @return : HttpRespEnvelope
	 * @version V3.0.0
	 */
	@ResponseBody
	@RequestMapping(value = { "/give_out_third_certificate" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope giveOutThirdCertificate(HttpServletRequest request, HttpServletResponse response, CertificateSendHis bean)
	{
		try
		{
			bean.setSendOperator(request.getParameter("custName")+"("+request.getParameter("operName")+")");// 操作员客户号  改为operName 20160405
			RequestUtil.verifyParamsIsNotEmpty(new Object[] { bean.getCertificateNo(),
					ASRespCode.ASP_DOC_UNIQUENO_INVALID.getCode(), ASRespCode.ASP_DOC_UNIQUENO_INVALID.getDesc() },
					new Object[] { bean.getSendOperator(), ASRespCode.ASP_DOC_OPTCUSTID_INVALID.getCode(),
							ASRespCode.ASP_DOC_OPTCUSTID_INVALID.getDesc() });
			thirdCertificateService.giveOutThirdCertificate(bean);
			return new HttpRespEnvelope();
		} catch (HsException ex)
		{
			// 12000 参数错误
			// 12690 发放证书失败
			return new HttpRespEnvelope(ex);
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "giveOutThirdCertificate," , "发放第三方证书失败", ex);
			return new HttpRespEnvelope(ex);
		}
		
	}

	/**
	 * 查询第三方证书发放历史
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年1月28日 下午3:25:09
	 * @param request
	 * @param response
	 * @return
	 * @return : HttpRespEnvelope
	 * @version V3.0.0
	 */
	@ResponseBody
	@RequestMapping(value = { "/find_third_certificate_give_out_recode" }, method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope queryThirdCertificateGiveOutRecode(HttpServletRequest request, HttpServletResponse response)
	{
		String certificateNo = request.getParameter("certificateNo");// 合同编号
		try
		{
			verifySecureToken(request);
			request.setAttribute("serviceName", thirdCertificateService);
			request.setAttribute("methodName", "queryThirdCertificateGiveOutRecode");
		} catch (HsException e)
		{
			return new HttpRespEnvelope(e);
		}
		return this.doList(request, response);
	}

	/**
	 * 分页查询第三方证书发放历史
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年1月28日 下午3:26:02
	 * @param request
	 * @param response
	 * @return
	 * @return : HttpRespEnvelope
	 * @version V3.0.0
	 */
	@RequestMapping(value = { "/find_third_certificate_recode_by_page" }, method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public HttpRespEnvelope findThirdCertificateRecodeByPage(HttpServletRequest request, HttpServletResponse response)
	{
		try
		{
			verifySecureToken(request);
			request.setAttribute("serviceName", thirdCertificateService);
			request.setAttribute("methodName", "queryThirdCertificateRecodeByPage");
		} catch (HsException e)
		{
			return new HttpRespEnvelope(e);
		}
		return this.doList(request, response);
	}
	
	@ResponseBody
	@RequestMapping(value = { "/find_certificate_content_by_id" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope queryCreContentById(HttpServletRequest request,String certificateNo){
		try
		{
			verifySecureToken(request);
			boolean realTime = Boolean.valueOf(request.getParameter("realTime"));
			CertificateContent cf = thirdCertificateService.queryCreContentById(certificateNo,realTime);
			return new HttpRespEnvelope(cf);
		}catch (HsException e)
		{
			return new HttpRespEnvelope(e);
		}catch (Exception ex){
			return new HttpRespEnvelope(ex);
		}
	}

	@Override
	protected IBaseService getEntityService()
	{
		return thirdCertificateService;
	}
}
