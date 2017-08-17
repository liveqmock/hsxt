/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gy.hsi.lc.client.log4j.BizLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.access.web.bean.HttpResp;
import com.gy.hsxt.bs.api.apply.IBSOfficialWebService;
import com.gy.hsxt.bs.bean.apply.CertificateContent;
import com.gy.hsxt.bs.bean.apply.ContractContent;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.utils.StringUtils;

/**
 * 文书验证控制器
 * 
 * @Package: com.gy.hsxt.access.web.controllers
 * @ClassName: DocumentVaildController
 * @Description: TODO
 * @author: likui
 * @date: 2015年12月9日 下午5:09:20
 * @company: gyist
 * @version V3.0.0
 */
@Controller
@RequestMapping("/gy/official")
public class DocumentVaildController {

	/** 官网Service **/
	@Autowired
	private IBSOfficialWebService officialWebService;

	/**
	 * 验证合同
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2015年12月9日 下午6:24:11
	 * @param contractNo
	 * @param uniqueCode
	 * @param request
	 * @param response
	 * @return
	 * @return : HttpRespEnvelope
	 * @version V3.0.0
	 */
	@ResponseBody
	@RequestMapping(value =
	{ "/docVaild/vaild_contract_lawful" }, method =
	{ RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpResp vaildContractLawful(@RequestParam("contractNo") String contractNo,
			@RequestParam("uniqueCode") String uniqueCode, @RequestParam("vaildCode") String vaildCode,
			HttpServletRequest request, HttpServletResponse response)
	{
		BizLog.biz(this.getClass().getName(), "function:vaildContractLawful", "params==>contractNo:" + contractNo
				+ ",vaildCode:" + vaildCode, "验证合同");
		// 去掉后台验证码
		// String code = (String)
		// request.getSession().getAttribute(VaildCodeType.DOC_VAILD.getCode());
		// if (vaildCode.equalsIgnoreCase(code))
		// {
		// }
		// return new HttpResp(RespCode.VAILD_CODE_ERROR.getCode());
		try
		{
			ContractContent bean = officialWebService.queryContractContent(uniqueCode, contractNo);
			if (StringUtils.isNotBlank(bean))
			{
				JSONObject json = new JSONObject();
				json.put("tempContent", bean.getContent());
				json.put("custType", bean.getCustType());
				json.put("sealX", -25);
				json.put("sealY", 0);
				json.put("status", bean.getSealStatus());
				return new HttpResp(RespCode.SUCCESS.getCode(), json);
			}
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "function:vaildContractLawful", "调用BS获取合同信息失败", ex);
		}
		return new HttpResp(RespCode.FAIL.getCode());
	}

	/**
	 * 验证销售资格证书
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2015年12月9日 下午6:25:35
	 * @param certificateNo
	 * @param request
	 * @param request
	 * @param response
	 * @return
	 * @return : HttpRespEnvelope
	 * @version V3.0.0
	 */
	@ResponseBody
	@RequestMapping(value =
	{ "/docVaild/valid_sell_certificate_lawful" }, method =
	{ RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpResp validSellCertificateLawful(@RequestParam("certificateNo") String certificateNo,
			@RequestParam("uniqueCode") String uniqueCode, @RequestParam("vaildCode") String vaildCode,
			HttpServletRequest request, HttpServletResponse response)
	{
		BizLog.biz(this.getClass().getName(), "function:vaildContractLawful", "params==>contractNo:" + certificateNo
				+ ",vaildCode:" + vaildCode, "验证销售资格证书");
		// 去掉后台验证码
		// String code = (String)
		// request.getSession().getAttribute(VaildCodeType.DOC_VAILD.getCode());
		// if (vaildCode.equalsIgnoreCase(code))
		// {
		// }
		// return new HttpResp(RespCode.VAILD_CODE_ERROR.getCode());
		try
		{
			CertificateContent bean = officialWebService.querySaleCre(uniqueCode, certificateNo);
			if (StringUtils.isNotBlank(bean))
			{
				return new HttpResp(RespCode.SUCCESS.getCode(), bean);
			}
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "function:validSellCertificateLawful", "调用BS获取销售资格证书信息失败", ex);
		}
		return new HttpResp(RespCode.FAIL.getCode());
	}

	/**
	 * 验证第三方业务证书
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2015年12月9日 下午6:28:04
	 * @param certificateNo
	 * @param uniqueCode
	 * @param request
	 * @param response
	 * @return
	 * @return : HttpRespEnvelope
	 * @version V3.0.0
	 */
	@ResponseBody
	@RequestMapping(value =
	{ "/docVaild/vaild_third_certificate_lawful" }, method =
	{ RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpResp vaildThirdCertificateLawful(@RequestParam("certificateNo") String certificateNo,
			@RequestParam("uniqueCode") String uniqueCode, @RequestParam("vaildCode") String vaildCode,
			HttpServletRequest request, HttpServletResponse response)
	{
		BizLog.biz(this.getClass().getName(), "function:vaildContractLawful", "params==>contractNo:" + certificateNo
				+ ",vaildCode:" + vaildCode, "验证第三方业务证书");
		// 去掉后台验证码
		// String code = (String)
		// request.getSession().getAttribute(VaildCodeType.DOC_VAILD.getCode());
		// if (vaildCode.equalsIgnoreCase(code))
		// {
		// }
		// return new HttpResp(RespCode.VAILD_CODE_ERROR.getCode());
		try
		{
			CertificateContent bean = officialWebService.queryBizCre(uniqueCode, certificateNo);
			if (StringUtils.isNotBlank(bean))
			{
				return new HttpResp(RespCode.SUCCESS.getCode(), bean);
			}
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "function:vaildThirdCertificateLawful", "调用BS获取第三方证书信息失败", ex);
		}
		return new HttpResp(RespCode.FAIL.getCode());
	}
}
