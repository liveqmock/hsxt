/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.company.controllers.accountManagement;

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
import com.gy.hsxt.access.web.bean.CompanyBase;
import com.gy.hsxt.access.web.bean.accountManage.CompanyTaxrateChange;
import com.gy.hsxt.access.web.common.bean.HttpRespEnvelope;
import com.gy.hsxt.access.web.common.constant.ASRespCode;
import com.gy.hsxt.access.web.common.controller.BaseController;
import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.access.web.common.utils.RequestUtil;
import com.gy.hsxt.access.web.company.services.accountManagement.IBalanceService;
import com.gy.hsxt.access.web.company.services.accountManagement.ICityTaxAccountService;
import com.gy.hsxt.access.web.company.services.common.CompanyConfigService;
import com.gy.hsxt.bs.bean.tax.TaxrateChange;
import com.gy.hsxt.common.constant.AccountType;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.StringUtils;

/***************************************************************************
 * <PRE>
 * Description 		: 城市税收
 * 
 * Project Name   	: hsxt-access-web-scs 
 * 
 * Package Name     : com.gy.hsxt.access.web.scs.controllers.accountManagement  
 * 
 * File Name        : CityTaxAccountController 
 * 
 * Author           : LiZhi Peter
 * 
 * Create Date      : 2016-1-9 下午4:08:05
 * 
 * Update User      : LiZhi Peter
 * 
 * Update Date      : 2016-1-9 下午4:08:05
 * 
 * UpdateRemark 	: 说明本次修改内容
 * 
 * Version          : v1.0
 * 
 * </PRE>
 ***************************************************************************/
@Controller
@RequestMapping("cityTaxAccount")
public class CityTaxAccountController extends BaseController {
    @Resource
    private IBalanceService balanceService; // 账户余额查询服务类
    
    /** 城市税收对接账户*/
    @Resource
    private ICityTaxAccountService iCityTaxAccountService;
    
 

    /**
     * 用户查询积分账户余额，返回积分账户总余额，可用积分数和今日积分数
     * 
     * @param custId
     *            客户号
     * @param pointNo
     *            互生卡号
     * @param request
     *            当前请求数据
     * @return
     */
    @ResponseBody
    @RequestMapping(value = { "/find_cityTax_blance" }, method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8") 
    public HttpRespEnvelope findCityTaxBlance(String custId, String entCustId ,String token, HttpServletRequest request) {

        // 变量声明

        Map<String, Object> map = null;
        AccountBalance accBalance = null;
        HttpRespEnvelope hre = null;

        // 执行查询方法
        try
        {
            // Token验证
            super.checkSecureToken(request);
            // 非空验证
            RequestUtil.verifyParamsIsNotEmpty(
                            new Object[] { entCustId, RespCode.AS_ENT_CUSTID_INVALID.getCode(),RespCode.AS_ENT_CUSTID_INVALID.getDesc() }          // 企业客户号
                    );
            map = new HashMap<String, Object>();

            // 积分税收、互生币税收账户 、城市税收对接 类型数组
            String[] accTypes = { AccountType.ACC_TYPE_POINT10510.getCode(), AccountType.ACC_TYPE_POINT20610.getCode(),
                    AccountType.ACC_TYPE_POINT30310.getCode() };

            // 执行查询
            Map<String, AccountBalance> hashMap = balanceService.searchAccBalanceByCustIdAndAccType(entCustId, accTypes);

            String hsbTaxBlance = "0"; // 互生币税收
            String cityTaxBlance = "0";// 城市税收
            String integralTaxBlance = "0"; // 积分税收

            if (hashMap != null)
            {
                //积分税收
                accBalance = hashMap.get(AccountType.ACC_TYPE_POINT10510.getCode());
                if(accBalance != null&& StringUtils.isBlank(accBalance.getAccBalance()) == false)
                {
                    integralTaxBlance = accBalance.getAccBalance();
                }
                
                //互生币税收
                accBalance = hashMap.get(AccountType.ACC_TYPE_POINT20610.getCode());
                if(accBalance != null&& StringUtils.isBlank(accBalance.getAccBalance())== false)
                {
                    hsbTaxBlance = accBalance.getAccBalance();
                }
                
                //城市税收
                accBalance = hashMap.get(AccountType.ACC_TYPE_POINT30310.getCode());
                if(accBalance != null&& StringUtils.isBlank(accBalance.getAccBalance())== false)
                {
                    cityTaxBlance = accBalance.getAccBalance();
                }
                
            }
            map.put("cityTaxBlance", cityTaxBlance);
            map.put("hsbTaxBlance", hsbTaxBlance);
            map.put("integralTaxBlance", integralTaxBlance);
           

            hre = new HttpRespEnvelope(map);

        }
        catch (HsException e)
        {
            hre = new HttpRespEnvelope(e);
        }

        return hre;
    }
    
    /**
     * 税率查询 
     * @param request
     * @param scsBase
     * @return
     */
    @ResponseBody
    @RequestMapping(value = { "/query_tax" }, method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8") 
    public HttpRespEnvelope queryTax(HttpServletRequest request,CompanyBase companyBase) {
        HttpRespEnvelope hre = null;
        try
        {
            // Token验证
            super.checkSecureToken(request);
            //查询当前服务公司税率
            String taxRate=iCityTaxAccountService.queryTax(companyBase);
            hre=new HttpRespEnvelope(taxRate);
        }
        catch (HsException e)
        {
            hre = new HttpRespEnvelope(e);
        }

        return hre;
    }
    
    /**
     * 税率调整申请
     * @param request
     * @param scsBase
     * @return
     */
    @ResponseBody
    @RequestMapping(value = { "/tax_adjustment_apply" }, method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8") 
    public HttpRespEnvelope taxAdjustmentApply(HttpServletRequest request,CompanyTaxrateChange atc,CompanyBase companyBase) {
        HttpRespEnvelope hre = null;
        try
        {
            // Token验证
            super.checkSecureToken(request);
            //验证有效数据
            atc.checkAdjustmentData();
            //调整申请
            iCityTaxAccountService.taxAdjustmentApply(atc,companyBase);
            hre = new HttpRespEnvelope();
        }
        catch (HsException e)
        {
            hre = new HttpRespEnvelope(e);
        }

        return hre;
    }
    /**
     * 申请税率调整明细
     * @param request
     * @param scsBase
     * @return
     */
    @ResponseBody
    @RequestMapping(value = { "/tax_apply_detail" }, method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8") 
    public HttpRespEnvelope taxApplyDetail(HttpServletRequest request,String applyId) {
        HttpRespEnvelope hre = null;
        try
        {
            // Token验证
            super.checkSecureToken(request);
            //验证有效数据
            RequestUtil.verifyParamsIsNotEmpty(new Object[]{applyId,ASRespCode.EW_APPLY_ID_NOT_NULL});
            //调整申请
            TaxrateChange tc=  iCityTaxAccountService.queryTaxApplyDetail(applyId);
            hre = new HttpRespEnvelope(tc);
        }
        catch (HsException e)
        {
            hre = new HttpRespEnvelope(e);
        }

        return hre;
    }

    /**
     * 税率调整申请查询
     * @param request
     * @param scsBase
     * @return
     */
    @ResponseBody
    @RequestMapping(value = { "/tax_adjustment_apply_page" }, method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8") 
    public HttpRespEnvelope taxAdjustmentApplyPage(HttpServletRequest request,HttpServletResponse response) {
        HttpRespEnvelope hre = null;
        try
        {
            // Token验证
            super.checkSecureToken(request);
            // 分页查询
            request.setAttribute("serviceName", iCityTaxAccountService);
            request.setAttribute("methodName", "taxAdjustmentApplyPage");
            hre = super.doList(request, response);
        }
        catch (HsException e)
        {
            hre = new HttpRespEnvelope(e);
        }

        return hre;
    }
    
    /**
     * 税收对接账户明细查询
     * 
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
            request.setAttribute("methodName", "taxDetailPage");
            hre = super.doList(request, response);

        }
        catch (HsException e)
        {
            hre = new HttpRespEnvelope(e);
        }

        return hre;
    }
    
    
    
    /**
     * 查询企业税率调整的最新审批结果
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "get_last_tax_apply", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope getLastTaxApply(HttpServletRequest request,CompanyBase companyBase) {
        HttpRespEnvelope hre = null;// 返回数据对象
        try
        {
            //检验token
            super.verifySecureToken(request);
            //获取最新审批结果
            TaxrateChange tch=iCityTaxAccountService.getLastTaxApply(companyBase);
            hre = new HttpRespEnvelope(tch);
        }
        catch (Exception e)
        {
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
        // TODO Auto-generated method stub
        return null;
    }
}
