/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.aps.controllers.accountPerson;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gy.hsxt.access.web.aps.services.accountCompany.IBalanceService;
import com.gy.hsxt.access.web.aps.services.accountCompany.IHsbAccountDetailService;
import com.gy.hsxt.access.web.aps.services.accountCompany.IIntegralAccountService;
import com.gy.hsxt.access.web.common.bean.HttpRespEnvelope;
import com.gy.hsxt.access.web.common.controller.BaseController;
import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.access.web.common.utils.RequestUtil;
import com.gy.hsxt.ao.bean.BuyHsb;
import com.gy.hsxt.ao.bean.HsbToCash;
import com.gy.hsxt.ao.bean.ProxyBuyHsb;
import com.gy.hsxt.ao.bean.PvToHsb;
import com.gy.hsxt.bs.bean.annualfee.AnnualFeeOrder;
import com.gy.hsxt.bs.bean.order.CustomPointDividend;
import com.gy.hsxt.bs.bean.order.DividendDetail;
import com.gy.hsxt.bs.bean.order.OrderCustom;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.ps.bean.QueryResult;
import com.gy.hsxt.ps.bean.QuerySingle;

/***************************************************************************
 * <PRE>
 * Description 		: 企业账户查询Controller
 * 
 * Project Name   	: hsxt-access-web-aps 
 * 
 * Package Name     : com.gy.hsxt.access.web.aps.controllers.accountCompany
 * 
 * File Name        : IntegralAccountController 
 * 
 * Author           : weixz
 * 
 * Create Date      : 2016-2-18 上午11:43:16
 * 
 * Update User      : weixz
 * 
 * Update Date      : 2016-2-18 上午11:43:16
 * 
 * UpdateRemark 	: 说明本次修改内容
 * 
 * Version          : v1.0
 * 
 * </PRE>
 ***************************************************************************/
@Controller
@RequestMapping("hsbPersonAccount")
public class HsbAccountPersonController extends BaseController {
	@Resource
	private IIntegralAccountService integralAccountService; // 用户积分服务类

	@Resource
	private IBalanceService balanceService; // 账户余额查询服务类
	
	@Resource
	private IHsbAccountDetailService service;

	
	/**
	 * 分页查询积分明细
	 * 
	 * @Description:
	 * @author: weixz
	 * @created: 2016年2月18日 上午11:45:03
	 * @param request
	 * @param response
	 * @return
	 * @return : HttpRespEnvelope
	 * @version V3.0.0
	 */
	@ResponseBody
	@RequestMapping(value = { "/hsb_detailed_page" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope searchAccEntryPage(HttpServletRequest request, HttpServletResponse response)
	{
		HttpRespEnvelope hre = null;

		try
		{
			// Token验证
			super.verifySecureToken(request);

			// 分页查询
			request.setAttribute("serviceName", balanceService);
			request.setAttribute("methodName", "searchPerAccountEntry");
			hre = super.doList(request, response);

		} catch (HsException e)
		{
			hre = new HttpRespEnvelope(e);
		}

		return hre;
	}
	
	 /**
     * 查询互生币
     * 
     * @param transNo
     *            交易流水号
     * @param request
     *            当前请求数据
     * @return
     */
    @ResponseBody
    @RequestMapping(value = { "/get_PersonComm_Detail" }, method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope getPersonCommDetail(String transNo,
            HttpServletRequest request) {
        HttpRespEnvelope hre = null;
        try {
            // Token验证
            super.checkSecureToken(request);

            // 非空数据验证
            RequestUtil.verifyParamsIsNotEmpty(new Object[] { transNo,
                    RespCode.AS_ENT_CUSTID_INVALID.getCode(),
                    RespCode.AS_ENT_CUSTID_INVALID.getDesc() } // 交易流水号
                    );

            // 非空数据验证
            RequestUtil.verifyParamsIsNotEmpty(new Object[] { transNo,
                    RespCode.AS_ENT_CUSTID_INVALID.getCode(),
                    RespCode.AS_ENT_CUSTID_INVALID.getDesc() } // 交易流水号
                    );
                String transType = request.getParameter("transType");
                String batchNo = request.getParameter("batchNo");
                String custId = request.getParameter("custId");
                Map<String,String> map =new HashMap<String,String>();
                map.put("transNo", transNo);
                map.put("transType", transType);
                map.put("batchNo", batchNo);
                map.put("entCustId", custId);
                hre = new HttpRespEnvelope(balanceService.queryAccOptDetailed(map));

        } catch (HsException e) {
            e.printStackTrace();
            hre = new HttpRespEnvelope(e);
        }

        return hre;
    }
	
	/**
     * 汇总下面的流水信息分页查询
     * 
     * @Description:
     * @author: weixz
     * @created: 2016年2月18日 上午11:45:03
     * @param request
     * @param response
     * @return
     * @return : HttpRespEnvelope
     * @version V3.0.0
     */
    @ResponseBody
    @RequestMapping(value = { "/sum_hsb_detailed_page" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope searchAccountEntryPage(HttpServletRequest request, HttpServletResponse response)
    {
        HttpRespEnvelope hre = null;

        try
        {
            // Token验证
            super.verifySecureToken(request);

            // 分页查询
            request.setAttribute("serviceName", balanceService);
            request.setAttribute("methodName", "searchEntAccountEntryList");
            hre = super.doList(request, response);

        } catch (HsException e)
        {
            hre = new HttpRespEnvelope(e);
        }

        return hre;
    }

    /**
     * 查询积分投资分红详情
     * 
     * @param transNo
     *            交易流水号
     * @param request
     *            当前请求数据
     * @return
     */
    @ResponseBody
    @RequestMapping(value = { "/getPointDividendInfo" }, method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope getPointDividendInfo(String hsResNo,
            HttpServletRequest request) {
        HttpRespEnvelope hre = null;
        try {
            // Token验证
            super.checkSecureToken(request);

            // 非空数据验证
            RequestUtil.verifyParamsIsNotEmpty(new Object[] { hsResNo,
                    RespCode.AS_ENT_CUSTID_INVALID.getCode(),
                    RespCode.AS_ENT_CUSTID_INVALID.getDesc() } // 互生号
                    );
            String dividendYear = request.getParameter("dividendYear");
            // 查询积分投资分红详情
            CustomPointDividend pointDividend = service.getInvestDividendInfo(hsResNo,dividendYear);

            hre = new HttpRespEnvelope(pointDividend);

        } catch (HsException e) {
            e.printStackTrace();
            hre = new HttpRespEnvelope(e);
        }

        return hre;
    }
    
    /**
     * 投资分红汇总下的流水详情
     * 
     * @param transNo
     *            交易流水号
     * @param request
     *            当前请求数据
     * @return
     */
    @ResponseBody
    @RequestMapping(value = { "/get_per_tzfh_mxcx" }, method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope getDividendDetailList(HttpServletRequest request,HttpServletResponse response) {
        HttpRespEnvelope hre = null;
        try {
            
            // Token验证
            super.verifySecureToken(request);

            // 分页查询
            request.setAttribute("serviceName", balanceService);
            request.setAttribute("methodName", "getDividendDetailList");
            hre = super.doList(request, response);

        } catch (HsException e) {
            e.printStackTrace();
            hre = new HttpRespEnvelope(e);
        }

        return hre;
    }
    
    
    
	/**
	 * @return
	 * @see com.gy.hsxt.access.web.common.controller.BaseController#getEntityService()
	 */
	@Override
	protected IBaseService getEntityService()
	{
		return integralAccountService;
	}
}
