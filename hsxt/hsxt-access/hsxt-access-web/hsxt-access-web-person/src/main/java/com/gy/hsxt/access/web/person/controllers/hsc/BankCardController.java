package com.gy.hsxt.access.web.person.controllers.hsc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.gy.hsxt.access.web.common.bean.HttpRespEnvelope;
import com.gy.hsxt.access.web.common.controller.BaseController;
import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.access.web.common.utils.RequestUtil;
import com.gy.hsxt.access.web.person.bean.BankInfo;
import com.gy.hsxt.access.web.person.services.common.PersonConfigService;
import com.gy.hsxt.access.web.person.services.hsc.IBankCardService;
import com.gy.hsxt.access.web.person.utils.ResourceUtils;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.NumbericUtils;
import com.gy.hsxt.common.utils.StringUtils;
import com.gy.hsxt.uc.as.api.enumerate.UserTypeEnum;
import com.gy.hsxt.uc.as.bean.common.AsBankAcctInfo;

/***************************************************************************
 * <PRE>
 * Description 		: 银行卡处理类
 * 
 * Project Name   	: hsxt-access-web-person
 * 
 * Package Name     : com.gy.hsxt.access.web.person.hsc.controller
 * 
 * File Name        : BankCardController
 * 
 * Author           : LiZhi Peter
 * 
 * Create Date      : 2015-8-28 下午3:24:19  
 * 
 * Update User      : LiZhi Peter
 * 
 * Update Date      : 2015-8-28 下午3:24:19
 * 
 * UpdateRemark 	: 说明本次修改内容
 * 
 * Version          : v0.0.1
 * 
 * </PRE>
 ***************************************************************************/
@Controller
@RequestMapping("bankCard")
@SuppressWarnings("unchecked")
public class BankCardController extends BaseController {
    @Resource
    private IBankCardService bankCardService; // 银行卡信息

    @Resource
    private PersonConfigService personConfigSevice;

    /**
     * 查询绑定银行卡列表
     * 
     * @param request
     * @param filterMap
     * @param sortMap
     * @return
     */
    @ResponseBody
    @RequestMapping(value = { "/findBankBindList" }, method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope findBankBindList(String custId, HttpServletRequest request) {

        // 返回的map信息
        HttpRespEnvelope hre = null;
        List<AsBankAcctInfo> bankList = null;
        Map<String, Object> map = null;

        try
        {
            // Token验证
            super.checkSecureToken(request);

            // 获取用户银行卡账户
            bankList = this.bankCardService.findBankAccountList(custId, UserTypeEnum.CARDER.getType());

            // 保存界面相应数据
            map = new HashMap<String, Object>();
            map.put("bankList", bankList);
            map.put("maxNum", this.personConfigSevice.getBankListSize());

            // 封装数据返回界面
            hre = new HttpRespEnvelope(map);
        }
        catch (HsException e)
        {
            hre = new HttpRespEnvelope(e);
        }

        // 返回封装结果数据
        return hre;
    }
    
    /**
     * 绑定消费者银行账户方法
     * 
     * @param bankInfo      银行账户信息
     * @param bankAccNoSure 确认银行卡号信息
     * @param request  当前请求
     * @return
     */
    @ResponseBody
    @RequestMapping(value = { "/add_bank_bind" }, method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope addBankBind(@ModelAttribute AsBankAcctInfo bankAcctInfo,String custName ,String bankAccNoSure , HttpServletRequest request) {

        // 返回的参数
        HttpRespEnvelope hre = null;

        try
        {
            // Token验证
            super.checkSecureToken(request);
            
            
             // 非空验证
            RequestUtil.verifyParamsIsNotEmpty(
                            new Object[] { bankAcctInfo.getBankName(), RespCode.PW_BANK_NAME_INVALID.getCode(),RespCode.PW_BANK_NAME_INVALID.getDesc() },            // 银行名称
                            new Object[] { bankAcctInfo.getBankAccNo(), RespCode.PW_BANK_ACC_NAME_INVALID.getCode(),RespCode.PW_BANK_ACC_NAME_INVALID.getDesc() },     // 银行编码
                            new Object[] { bankAcctInfo.getCountryNo(), RespCode.PW_BANKINFO_COUNTRYNO_INVALID.getCode(),RespCode.PW_BANKINFO_COUNTRYNO_INVALID.getDesc() },         // 所国家编号
                            new Object[] { bankAcctInfo.getProvinceNo(), RespCode.PW_BANKINFO_PROVINCENO_INVALID.getCode(),RespCode.PW_BANKINFO_PROVINCENO_INVALID.getDesc() },   // 所在省编号
                            new Object[] { bankAcctInfo.getCityNo(), RespCode.PW_BANKINFO_CITYNO_INVALID.getCode(),RespCode.PW_BANKINFO_CITYNO_INVALID.getDesc() },           // 所在城市编号
                            new Object[] { bankAcctInfo.getBankAccNo(), RespCode.PW_BANK_NO_INVALID.getCode(),RespCode.PW_BANK_NO_INVALID.getDesc() },                  // 银行卡账号
                            new Object[] { custName, RespCode.AS_CUSTNAME_INVALID.getCode(),RespCode.AS_CUSTNAME_INVALID.getDesc() },                  					// 客户名称不能为空
                            new Object[] { bankAccNoSure, RespCode.PW_CONFIRM_BANK_INVALID.getCode(),RespCode.PW_CONFIRM_BANK_INVALID.getDesc() }                      // 确认银行卡号
                    );
            
            //银行卡号规则验证
            //RequestUtil.verifyBankNo(bankAcctInfo.getBankAccNo(), RespCode.PW_BANK_NO_FORMAT_INVALID);
            
            //验证输入的两次银行卡号是否相同
            RequestUtil.verifyEquals(bankAcctInfo.getBankAccNo(), bankAccNoSure, RespCode.PW_BIND_NO_INCONSISTENT);
            
            bankAcctInfo.setBankAccName(custName);
            //调用绑定银行卡操作，
            this.bankCardService.addBankBind(bankAcctInfo, UserTypeEnum.CARDER.getType());
            
            hre = new HttpRespEnvelope();
        }
        catch (HsException e)
        {
            hre = new HttpRespEnvelope(e);
        }

        return hre;
    }
    
    /**
     * 删除绑定卡信息
     * 
     * @param bankInfo
     * @return
     */
    @ResponseBody
    @RequestMapping(value = { "/del_bank_bind" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope delBankBind(@ModelAttribute BankInfo bankInfo, HttpServletRequest request) {


        // 返回的参数
        HttpRespEnvelope hre = null;

        try
        {
            // Token验证
            super.checkSecureToken(request);
            
            //银行卡id验证
            RequestUtil.verifyPositiveInteger(bankInfo.getBankId(), RespCode.PW_BANK_ID_INVALID);
            
            // 删除绑定的银行卡
            this.bankCardService.delBankBind(Long.parseLong(bankInfo.getBankId()), UserTypeEnum.CARDER.getType());


            // 封装数据返回界面
            hre = new HttpRespEnvelope();
        }
        catch (HsException e)
        {
            hre = new HttpRespEnvelope(e);
        }

        // 返回封装结果数据
        return hre;
    }
    
   

   

    @Override
    protected IBaseService getEntityService() {
        return bankCardService;
    }

}
