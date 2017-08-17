/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.scs.controllers.companyInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gy.hsxt.access.web.common.bean.HttpRespEnvelope;
import com.gy.hsxt.access.web.common.controller.BaseController;
import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.access.web.common.utils.ValidateUtil;
import com.gy.hsxt.access.web.scs.services.common.PubParamService;
import com.gy.hsxt.access.web.scs.services.companyInfo.IBankAccountInfoService;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.StringUtils;
import com.gy.hsxt.lcs.bean.LocalInfo;
import com.gy.hsxt.uc.as.bean.common.AsBankAcctInfo;

/***
 * 银行帐户
 * 
 * @Package: com.gy.hsxt.access.web.business.companySystemInfo.controller
 * @ClassName: RealNameAuthController
 * @Description: TODO
 * 
 * @author: liuxy
 * @date: 2015-9-29 上午11:44:01
 * @version V3.0.0
 */
@Controller
@RequestMapping("bankAccountInfo")
public class BankAccountInfoController extends BaseController {
    @Resource
    private IBankAccountInfoService service;

    @Resource
    private PubParamService bsService;

    /***
     * 查找绑定的银行帐户列表
     * 
     * @param request
     * @return
     */
    @RequestMapping(value = { "/findCardList" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public HttpRespEnvelope findCardList(String entCustId,String custType, HttpServletRequest request) {
        HttpRespEnvelope hre = null;

        try
        {
            verifySecureToken(request);
            List<AsBankAcctInfo> info = service.findBankAccountList(entCustId,custType);
            hre = new HttpRespEnvelope(info);
        }
        catch (HsException e)
        {
            hre = new HttpRespEnvelope(e);
        }

        return hre;
    }

    /***
     * 绑定新的银行卡
     * 
     * @param request
     * @return
     */
    @RequestMapping(value = { "/addBankCard" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public HttpRespEnvelope addBankCard(@ModelAttribute AsBankAcctInfo bcInfo,String entCustId,String custType, HttpServletRequest request) {
        HttpRespEnvelope hre = null;

        try
        {

            verifySecureToken(request);

            hre = validData(bcInfo);

            if (hre != null)
            {
                return hre;
            }
            // 修改
            bcInfo.setCustId(entCustId);
            service.addBankCard(bcInfo,custType);

            hre = new HttpRespEnvelope();
        }
        catch (HsException e)
        {
            hre = new HttpRespEnvelope(e);
        }

        return hre;
    }

    /***
     * 解除绑定
     * 
     * @param request
     * @return
     */
    @RequestMapping(value = { "/delBankCard" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public HttpRespEnvelope delBankCard(String bankAccId, String custType, HttpServletRequest request) {
        HttpRespEnvelope hre = null;

        try
        {
            verifySecureToken(request);
            // 修改
            service.delBankCard(bankAccId, custType);

            hre = new HttpRespEnvelope();
        }
        catch (HsException e)
        {
            hre = new HttpRespEnvelope(e);
        }

        return hre;
    }

    /***
     * 验证提交 的数据
     * 
     * @param info
     * @return
     */
    private HttpRespEnvelope validData(AsBankAcctInfo info) {
        HttpRespEnvelope hre = null;

        if (StringUtils.isBlank(info.getCityNo()))
        {
            hre = new HttpRespEnvelope(RespCode.SW_NOT_EMPTY_CITY_CODE.getCode(), RespCode.SW_NOT_EMPTY_CITY_CODE
                    .getDesc());
        }
        else if (StringUtils.isBlank(info.getProvinceNo()))
        {
            hre = new HttpRespEnvelope(RespCode.SW_NOT_EMPTY_PROVINCE_CODE.getCode(),
                    RespCode.SW_NOT_EMPTY_PROVINCE_CODE.getDesc());        }
        else if (StringUtils.isBlank(info.getBankCode()))
        {
            hre = new HttpRespEnvelope(RespCode.SW_NOT_EMPTY_BANK_CODE.getCode(), RespCode.SW_NOT_EMPTY_BANK_CODE
                    .getDesc());
        }
        else if (!ValidateUtil.validateBankCardId(info.getBankAccNo()))
        {
            hre = new HttpRespEnvelope(RespCode.SW_ILLEGAL_BANK_CARD_ID.getCode(), RespCode.SW_ILLEGAL_BANK_CARD_ID
                    .getDesc());
        }

        return hre;
    }

    @SuppressWarnings("rawtypes")
    @Override
    protected IBaseService getEntityService() {
        return service;
    }

}
