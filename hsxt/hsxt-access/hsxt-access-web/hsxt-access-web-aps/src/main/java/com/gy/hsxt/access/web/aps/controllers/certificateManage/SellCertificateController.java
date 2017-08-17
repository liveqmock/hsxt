/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.aps.controllers.certificateManage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gy.hsxt.access.web.aps.services.certificateManage.ISellCertificateServuce;
import com.gy.hsxt.access.web.common.bean.HttpRespEnvelope;
import com.gy.hsxt.access.web.common.constant.ASRespCode;
import com.gy.hsxt.access.web.common.controller.BaseController;
import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.access.web.common.utils.RequestUtil;
import com.gy.hsxt.common.exception.HsException;

/**
 * 消费资格证书控制器
 * 
 * @Package: com.gy.hsxt.access.web.aps.controllers.certificateManage
 * @ClassName: SellCertificateController
 * @Description: TODO
 * @author: likui
 * @date: 2016年1月22日 上午11:03:55
 * @company: gyist
 * @version V3.0.0
 */
@Controller
@RequestMapping("sellCertificate")
@SuppressWarnings("rawtypes")
public class SellCertificateController extends BaseController<T> {

	/**
	 * 销售资格证书Service
	 */
	@Autowired
	private ISellCertificateServuce sellCertificateServuce;

	/**
	 * 分页查询消费资格证书盖章
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年1月28日 下午2:53:17
	 * @param request
	 * @param response
	 * @return
	 * @return : HttpRespEnvelope
	 * @version V3.0.0
	 */
	@RequestMapping(value = { "/find_sell_certificate_by_seal" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public HttpRespEnvelope findSellCertificateBySeal(HttpServletRequest request, HttpServletResponse response)
	{
		try
		{
			verifySecureToken(request);
			request.setAttribute("serviceName", sellCertificateServuce);
			request.setAttribute("methodName", "querySellCertificateBySeal");
		} catch (HsException e)
		{
			return new HttpRespEnvelope(e);
		}
		return this.doList(request, response);
	}

	/**
	 * 销售资格证书盖章
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年1月28日 下午2:56:08
	 * @param request
	 * @param response
	 * @return
	 * @return : HttpRespEnvelope
	 * @version V3.0.0
	 */
	@ResponseBody
	@RequestMapping(value = { "/sell_certificate_seal" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope sellCertificateSeal(HttpServletRequest request, HttpServletResponse response)
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
			sellCertificateServuce.sellCertificateSeal(certificateNo, custId);
			return new HttpRespEnvelope();
		} catch (HsException e)
		{
			return new HttpRespEnvelope(e);
		}
	}

	/**
	 * 根据ID查询销售资格证书
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年1月28日 下午2:59:17
	 * @param request
	 * @param response
	 * @return
	 * @return : HttpRespEnvelope
	 * @version V3.0.0
	 */
	@ResponseBody
	@RequestMapping(value = { "/find_sell_certificate_by_id" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope findSellCertificateById(HttpServletRequest request, HttpServletResponse response)
	{
		String certificateNo = request.getParameter("certificateNo");// 证书编号
		try
		{
			verifySecureToken(request);
			RequestUtil.verifyParamsIsNotEmpty(new Object[] { certificateNo,
					ASRespCode.ASP_CERTIFICATENO_INVALID.getCode(), ASRespCode.ASP_CERTIFICATENO_INVALID.getDesc() });
			return new HttpRespEnvelope(sellCertificateServuce.querySellCertificateById(certificateNo));
		} catch (HsException e)
		{
			return new HttpRespEnvelope(e);
		}
	}

	/**
	 * 查询销售资格证书内容
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年1月28日 下午3:00:22
	 * @param request
	 * @param response
	 * @return
	 * @return : HttpRespEnvelope
	 * @version V3.0.0
	 */
	@ResponseBody
	@RequestMapping(value = { "/find_sell_certificate_content" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope findSellCertificateContent(HttpServletRequest request, HttpServletResponse response)
	{
		String certificateNo = request.getParameter("certificateNo");// 证书编号
		try
		{
			verifySecureToken(request);
			RequestUtil.verifyParamsIsNotEmpty(new Object[] { certificateNo,
					ASRespCode.ASP_CERTIFICATENO_INVALID.getCode(), ASRespCode.ASP_CERTIFICATENO_INVALID.getDesc() });
			return new HttpRespEnvelope(sellCertificateServuce.querySellCertificateContent(certificateNo));
		} catch (HsException e)
		{
			return new HttpRespEnvelope(e);
		}
	}

	/**
	 * 分页查询证书发放
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年1月28日 下午3:01:55
	 * @param request
	 * @param response
	 * @return
	 * @return : HttpRespEnvelope
	 * @version V3.0.0
	 */
	@RequestMapping(value = { "/find_sell_certificate_give_out_by_page" }, method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public HttpRespEnvelope findSellCertificateGiveOutByPage(HttpServletRequest request, HttpServletResponse response)
	{
		try
		{
			verifySecureToken(request);
			request.setAttribute("serviceName", sellCertificateServuce);
			request.setAttribute("methodName", "querySellCertificateGiveOutByPage");
		} catch (HsException e)
		{
			return new HttpRespEnvelope(e);
		}
		return this.doList(request, response);
	}

	/**
	 * 打印销售资格证书
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年1月28日 下午3:03:45
	 * @param request
	 * @param response
	 * @return
	 * @return : HttpRespEnvelope
	 * @version V3.0.0
	 */
	@ResponseBody
	@RequestMapping(value = { "/print_sell_certificate" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope printSellCertificate(HttpServletRequest request, HttpServletResponse response)
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
			return new HttpRespEnvelope(sellCertificateServuce.printSellCertificate(certificateNo, custId));
		} catch (HsException e)
		{
			return new HttpRespEnvelope(e);
		}
	}

	/**
	 * 销售资格证书发放
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年1月28日 下午3:05:12
	 * @param request
	 * @param response
	 * @return
	 * @return : HttpRespEnvelope
	 * @version V3.0.0
	 */
	@ResponseBody
	@RequestMapping(value = { "/give_out_sell_certificate" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope giveOutSellCertificate(HttpServletRequest request, HttpServletResponse response)
	{
		String giveOut = request.getParameter("giveOut");
		try
		{
			verifySecureToken(request);
			sellCertificateServuce.giveOutSellCertificate(giveOut);
			return new HttpRespEnvelope();
		} catch (HsException e)
		{
			return new HttpRespEnvelope(e);
		}
	}

	/**
	 * 查询销售资格证书发放历史
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年1月28日 下午3:13:45
	 * @param request
	 * @param response
	 * @return
	 * @return : HttpRespEnvelope
	 * @version V3.0.0
	 */
	@ResponseBody
	@RequestMapping(value = { "/find_sell_certificate_give_out_recode" }, method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope findSellCertificateGiveOutRecode(HttpServletRequest request, HttpServletResponse response)
	{
		String certificateNo = request.getParameter("certificateNo");// 合同编号
		try
		{
			verifySecureToken(request);
			request.setAttribute("serviceName", sellCertificateServuce);
			request.setAttribute("methodName", "querySellCertificateGiveOutRecode");
		} catch (HsException e)
		{
			return new HttpRespEnvelope(e);
		}
		return this.doList(request, response);
	}

	/**
	 * 分页查询销售资格证书发放历史
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年1月28日 下午3:15:45
	 * @param request
	 * @param response
	 * @return
	 * @return : HttpRespEnvelope
	 * @version V3.0.0
	 */
	@RequestMapping(value = { "/find_sell_certificate_recode_by_page" }, method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public HttpRespEnvelope findSellCertificateRecodeByPage(HttpServletRequest request, HttpServletResponse response)
	{
		try
		{
			verifySecureToken(request);
			request.setAttribute("serviceName", sellCertificateServuce);
			request.setAttribute("methodName", "querySellCertificateRecodeByPage");
		} catch (HsException e)
		{
			return new HttpRespEnvelope(e);
		}
		return this.doList(request, response);
	}

	@Override
	protected IBaseService getEntityService()
	{
		return sellCertificateServuce;
	}
}
