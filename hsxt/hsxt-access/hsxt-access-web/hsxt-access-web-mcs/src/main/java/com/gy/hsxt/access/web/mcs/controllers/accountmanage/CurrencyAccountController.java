/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.mcs.controllers.accountmanage;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gy.hsxt.ac.bean.AccountBalance;
import com.gy.hsxt.access.web.bean.MCSBase;
import com.gy.hsxt.access.web.common.bean.HttpRespEnvelope;
import com.gy.hsxt.access.web.common.controller.BaseController;
import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.access.web.common.utils.RequestUtil;
import com.gy.hsxt.access.web.mcs.services.accountmanage.IBalanceService;
import com.gy.hsxt.access.web.mcs.services.common.IPubParamService;
import com.gy.hsxt.ao.bean.TransOut;
import com.gy.hsxt.bs.bean.order.OrderCustom;
import com.gy.hsxt.common.constant.AccountType;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.lcs.bean.LocalInfo;

/***************************************************************************
 * <PRE>
 * Description 		: 货币账户控制类
 * 
 * Project Name   	: hsxt-access-web-person 
 * 
 * Package Name     : com.gy.hsxt.access.web.person.controllers.accountManagement  
 * 
 * File Name        : IntegralAccountController 
 * 
 * Author           : LiZhi Peter
 * 
 * Create Date      : 2015-10-26 下午5:22:16
 * 
 * Update User      : LiZhi Peter
 * 
 * Update Date      : 2015-10-26 下午5:22:16
 * 
 * UpdateRemark 	: 说明本次修改内容
 * 
 * Version          : v1.0
 * 
 * </PRE>
 ***************************************************************************/
@Controller
@RequestMapping("currencyAccount")
public class CurrencyAccountController extends BaseController {

    @Resource
    private IBalanceService balanceService; // 账户余额查询服务类

    @Autowired
    private IPubParamService pubParamService;// 平台服务公共信息服务类

    /**
     * 用户查询积分账户余额，返回积分账户总余额，可用积分数和昨日积分数
     * @param custId
     *            客户号
     * @param pointNo
     *            互生卡号
     * @param request
     *            当前请求数据
     * @return
     */
    @ResponseBody
    @RequestMapping(value = { "/find_currency_blance" }, method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope findCurrencyBlance(MCSBase mcsBase, HttpServletRequest request) {
        // 变量声明
        Map<String, Object> map = null;
        AccountBalance accBalance = null;
        HttpRespEnvelope hre = null;
        try
        {
            map = new HashMap<String, Object>();
            // Token验证
            super.checkSecureToken(request);
            // 查询货币账户余额
            accBalance = balanceService.findAccNormal(mcsBase.getEntCustId(), AccountType.ACC_TYPE_POINT30110.getCode());
            if (accBalance != null)
            {
                map.put("currencyBlance", accBalance.getAccBalance());
            }
            // 获取平台信息
            LocalInfo localInfo = pubParamService.findSystemInfo();
            //国家代码
            map.put("countryNo", localInfo.getCountryNo());

            hre = new HttpRespEnvelope(map);
        }
        catch (HsException e)
        {
            hre = new HttpRespEnvelope(e);
        }

        return hre;
    }

    /**
     * 货币账户明细查询
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = { "/detailed_page" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope searchAccEntryPage(HttpServletRequest request, HttpServletResponse response) {
        HttpRespEnvelope hre = null;
        try
        {
            // Token验证
            super.verifySecureToken(request);
            // 分页查询
            request.setAttribute("serviceName", balanceService);
            request.setAttribute("methodName", "searchAccEntryPage");
            hre = super.doList(request, response);
        }
        catch (HsException e)
        {
            hre = new HttpRespEnvelope(e);
        }

        return hre;
    }
    /**
	 * 查询账户详情详情
	 * 
	 * @param transNo
	 *            交易流水号
	 * @param request
	 *            当前请求数据
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/getdetailInfo" }, method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope getTransOutInfo(String transNo,String transType,
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
			hre = new HttpRespEnvelope(balanceService.queryAccOptDetailed(transNo, transType));

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
    protected IBaseService getEntityService() {
        return null;
    }
}
