package com.gy.hsxt.access.web.aps.controllers.invoice;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gy.hsxt.access.web.aps.services.common.ICommonService;
import com.gy.hsxt.access.web.aps.services.common.IPubParamService;
import com.gy.hsxt.access.web.aps.services.invoice.ICompanyInforService;
import com.gy.hsxt.access.web.common.bean.HttpRespEnvelope;
import com.gy.hsxt.access.web.common.controller.BaseController;
import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.access.web.common.utils.RequestUtil;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.uc.as.bean.common.AsBankAcctInfo;
import com.gy.hsxt.uc.as.bean.ent.AsEntAllInfo;
import com.gy.hsxt.uc.as.bean.ent.AsEntBaseInfo;
import com.gy.hsxt.uc.as.bean.ent.AsEntExtendInfo;
import com.gy.hsxt.uc.as.bean.ent.AsEntMainInfo;
import com.gy.hsxt.uc.as.bean.ent.AsEntStatusInfo;

/**
 * 
 * @projectName : hsxt-access-web-aps
 * @package : com.gy.hsxt.access.web.aps.controllers.invoice
 * @className : CompanyInforControler.java
 * @description : 企业信息查询，银行账号查询
 * @author : chenli
 * @createDate : 2015-12-18
 * @updateUser :
 * @updateDate :
 * @updateRemark :
 * @version : v0.0.1
 */
@Controller
@RequestMapping("invoiceCompanyInfor")
public class CompanyInforControler extends BaseController<ICompanyInforService> {
	@Resource
	private ICompanyInforService service;

	@Resource
	private IPubParamService pubParamService;

	@Resource
	private ICommonService commonService;
	/**
	 * 根据 custId 查询企业的所有信息
	 * 
	 * @param customId
	 *            :企业客户ID
	 * @param request
	 * @return
	 */
	@ResponseBody
    @RequestMapping(value = { "/findAllByCustId" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope findAllByCustId(String customId, HttpServletRequest request) {
        HttpRespEnvelope hre = null;

        try
        {
            // 验证安全令牌
            verifySecureToken(request);
            
            // 非空验证
            RequestUtil.verifyParamsIsNotEmpty(new Object[] { customId, RespCode.APS_TAXRATECHANGE_CUST_ID_EMPTY });

            // 查询企业的所有信息
            AsEntAllInfo asEntAllInfo = service.findAllByCustId(customId);

            hre = new HttpRespEnvelope(asEntAllInfo);
        }
        catch (HsException e)
        {
            hre = new HttpRespEnvelope(e);
        }

        return hre;
    }

	/**
	 * 根据 custId 查询企业的基本信息
	 * 
	 * @param customId
	 *            :企业客户ID
	 * @param request
	 * @return
	 */
	@RequestMapping(value = { "/findBaseByCustId" }, method = {
			RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public HttpRespEnvelope findBaseByCustId(String customId,
			HttpServletRequest request) {
		HttpRespEnvelope hre = null;

		try {
			// 验证安全令牌
			verifySecureToken(request);
			// 非空验证
			RequestUtil.verifyParamsIsNotEmpty(new Object[] { customId,
					RespCode.APS_TAXRATECHANGE_CUST_ID_EMPTY.getCode(),
					RespCode.APS_TAXRATECHANGE_CUST_ID_EMPTY.getDesc() });

			// 查询企业的基本信息
			AsEntBaseInfo asEntBaseInfo = service.findBaseByCustId(customId);

			hre = new HttpRespEnvelope(asEntBaseInfo);
		} catch (HsException e) {
			hre = new HttpRespEnvelope(e);
		}

		return hre;
	}

	/**
	 * 根据 custId 查询企业的重要信息
	 * 
	 * @param customId
	 *            :企业客户ID
	 * @param request
	 * @return
	 */
	@RequestMapping(value = { "/findMainByCustId" }, method = {
			RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public HttpRespEnvelope findMainByCustId(String customId,
			HttpServletRequest request) {
		HttpRespEnvelope hre = null;

		try {
			// 验证安全令牌
			verifySecureToken(request);
			// 非空验证
			RequestUtil.verifyParamsIsNotEmpty(new Object[] { customId,
					RespCode.APS_TAXRATECHANGE_CUST_ID_EMPTY.getCode(),
					RespCode.APS_TAXRATECHANGE_CUST_ID_EMPTY.getDesc() });

			// 查询企业的重要信息
			AsEntMainInfo asEntMainInfo = service.findMainByCustId(customId);

			hre = new HttpRespEnvelope(asEntMainInfo);
		} catch (HsException e) {
			hre = new HttpRespEnvelope(e);
		}

		return hre;
	}

	/**
	 * 根据 hsResNo 查询企业的重要信息
	 * 
	 * @param resNo
	 *            :企业互生号
	 * @param request
	 * @return
	 */
	@RequestMapping(value = { "/findMainByResNo" }, method = {
			RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public HttpRespEnvelope findMainByResNo(String resNo,
			HttpServletRequest request) {
		HttpRespEnvelope hre = null;

		try {
			// 验证安全令牌
			verifySecureToken(request);
			// 查询企业的重要信息
			AsEntMainInfo asEntMainInfo = service.findMainByResNo(resNo);

			hre = new HttpRespEnvelope(asEntMainInfo);
		} catch (HsException e) {
			hre = new HttpRespEnvelope(e);
		}
		return hre;
	}

	/**
	 * 根据 custId 查询默认银行账户
	 * 
	 * @param customId
	 *            :企业客户ID
	 * @param request
	 * @return
	 */
	@RequestMapping(value = { "/findDefaultBankByCustId" }, method = {
			RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public HttpRespEnvelope findDefaultBankByCustId(String customId,
			HttpServletRequest request) {
		HttpRespEnvelope hre = null;

		try {
			// 验证安全令牌
			verifySecureToken(request);
			// 非空验证
			RequestUtil.verifyParamsIsNotEmpty(new Object[] { customId,
					RespCode.APS_TAXRATECHANGE_CUST_ID_EMPTY.getCode(),
					RespCode.APS_TAXRATECHANGE_CUST_ID_EMPTY.getDesc() });

			// 查询默认银行账户
			AsBankAcctInfo asBankAcctInfo = service
					.searchDefaultBankAcc(customId);

			hre = new HttpRespEnvelope(asBankAcctInfo);
		} catch (HsException e) {
			hre = new HttpRespEnvelope(e);
		}

		return hre;
	}

    /**
     * 根据 resNo 查询客户信息和默认银行账户
     * 
     * @param resNo
     *            :企业resNo
     * @param request
     * @return
     */
	@ResponseBody
    @RequestMapping(value = { "/findMainInfoDefaultBankByResNo" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope findMainInfoDefaultBankByResNo(String companyResNo, String entResNo,
            HttpServletRequest request) {
        HttpRespEnvelope hre = null;

        try
        {
            // 验证安全令牌
            verifySecureToken(request);
            if (companyResNo == null)
            {
                companyResNo = entResNo;
            }

            AsEntExtendInfo aeei =commonService.findCompanyExtInfo(commonService.findCompanyCustIdByEntResNo(companyResNo)) ;//service.findMainByResNo(companyResNo);
            Map<String, Object> resultMap = new HashMap<String, Object>();
            resultMap.put("mainInfo", aeei);
            if (aeei != null)
            {
                AsBankAcctInfo asBankAcctInfo = service.searchDefaultBankAcc(aeei.getEntCustId());

                resultMap.put("bankInfo", asBankAcctInfo);
            }
            hre = new HttpRespEnvelope(resultMap);
        }
        catch (HsException e)
        {
            hre = new HttpRespEnvelope(e);
        }

        return hre;
    }

	/**
	 * 通过企业互生号查询企业客户号
	 * 
	 * @param resNo
	 *            :企业互生号
	 * @param request
	 * @return
	 */
	@RequestMapping(value = { "/findEntCustIdByEntResNo" }, method = {
			RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public HttpRespEnvelope findEntCustIdByEntResNo(String resNo,
			HttpServletRequest request) {
		HttpRespEnvelope hre = null;

		try {
			// 验证安全令牌
			verifySecureToken(request);
			// 非空验证
			RequestUtil.verifyParamsIsNotEmpty(new Object[] { resNo,
					RespCode.APS_INVOICE_RES_NO_EMPTY.getCode(),
					RespCode.APS_INVOICE_RES_NO_EMPTY.getDesc() });

			// 查询企业客户号
			String custId = service.findEntCustIdByEntResNo(resNo);

			hre = new HttpRespEnvelope(custId);
		} catch (HsException e) {
			hre = new HttpRespEnvelope(e);
		}

		return hre;
	}

	/**
	 * 查询平台互生号
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = { "/getPlatResNo" }, method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public HttpRespEnvelope getPlatResNo(HttpServletRequest request) {
		HttpRespEnvelope hre = null;

		try {
			// 验证安全令牌
			verifySecureToken(request);

			// 查询平台互生号
			String platResNo = pubParamService.findSystemInfo().getPlatResNo();

			hre = new HttpRespEnvelope(platResNo);
		} catch (HsException e) {
			hre = new HttpRespEnvelope(e);
		}

		return hre;
	}

	/**
	 * 查询企业状态
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/searchEntStatusInfo" }, method = {
			RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope queryEnterStatus(String companyCustId,
			HttpServletRequest request) {
		// Token验证
		HttpRespEnvelope httpRespEnvelope = super.checkSecureToken(request);
		if (httpRespEnvelope == null) {
			try {
				AsEntStatusInfo info = service
						.searchEntStatusInfo(companyCustId);
				httpRespEnvelope = new HttpRespEnvelope(info);
			} catch (HsException e) {
				httpRespEnvelope = new HttpRespEnvelope(e);
			}
		}
		return httpRespEnvelope;
	}

	@Override
	protected IBaseService getEntityService() {
		return service;
	}

}
