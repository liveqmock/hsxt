/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.person.controllers.accountManagement;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gy.hsxt.access.web.common.bean.HttpRespEnvelope;
import com.gy.hsxt.access.web.common.constant.ASRespCode;
import com.gy.hsxt.access.web.common.controller.BaseController;
import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.access.web.common.utils.CommonUtils;
import com.gy.hsxt.access.web.common.utils.RequestUtil;
import com.gy.hsxt.access.web.person.bean.PersonBase;
import com.gy.hsxt.access.web.person.bean.accountManagement.NetPay;
import com.gy.hsxt.access.web.person.bean.accountManagement.PayLimit;
import com.gy.hsxt.access.web.person.services.accountManagement.IBalanceService;
import com.gy.hsxt.access.web.person.services.accountManagement.IHsbAccountService;
import com.gy.hsxt.access.web.person.services.common.PersonConfigService;
import com.gy.hsxt.access.web.person.services.consumer.INoCardholderService;
import com.gy.hsxt.ao.bean.HsbToCash;
import com.gy.hsxt.bs.bean.order.Order;
import com.gy.hsxt.common.constant.CustType;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.StringUtils;
import com.gy.hsxt.uc.as.bean.common.AsNetworkInfo;

/***
 * 互生币账户处理类
 * 
 * @Package: com.gy.hsxt.access.web.person.controllers.accountManagement  
 * @ClassName: HsbAccountController 
 * @Description: TODO
 *
 * @author: wangjg 
 * @date: 2016-4-16 上午11:59:40 
 * @version V1.0
 */
@Controller
@RequestMapping("hsbAccount")
public class HsbAccountController extends BaseController {

    @Resource
    private IBalanceService balanceService; // 账户余额查询服务类

    @Resource
    private IHsbAccountService hsbAccountService;
    
    @Resource
    private PersonConfigService configService;
    
    @Resource
    private INoCardholderService noCardholderService;//非持卡人服务类

    /**
     * 查询余额信息
     * @param personBase
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = { "/find_hsb_blance" }, method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope findHsbBlance(PersonBase personBase, HttpServletRequest request) {
        HttpRespEnvelope hre = null;

        try
        {
            // Token验证
            super.checkSecureToken(request);

            //获取余额信息
            Map<String, Object> map = hsbAccountService.findHsbBlance(personBase);
            hre = new HttpRespEnvelope(map);

        }
        catch (HsException e)
        {
            hre = new HttpRespEnvelope(e);
        }

        return hre;
    }

    /**
     * 初始化互生币转货币
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
    @RequestMapping(value = { "/init_hsb_transfer_currency" }, method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope initHsbTransferCurrency(PersonBase personBase, HttpServletRequest request) {
        HttpRespEnvelope hre = null;

        try
        {
            // Token验证
            super.checkSecureToken(request);

            // 获取互生币转货币
            Map<String, Object> map = hsbAccountService.initHsbTransferCurrency(personBase);
            hre = new HttpRespEnvelope(map);

        }
        catch (HsException e)
        {
            hre = new HttpRespEnvelope(e);
        }
        return hre;
    }

    /**
     * 互生币转货币
     * 
     * @param hsbToCash
     *            互生币转货币实体
     * @param tradePwd
     *            交易密码
     * @param randomToken
     *            随机token
     * @param request
     *            当前请求
     * @return
     */
    @ResponseBody
    @RequestMapping(value = { "/hsb_transfer_currency" }, method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope hsbTransferCurrency(@ModelAttribute HsbToCash hsbToCash,String hs_isCard, String tradePwd,
            String randomToken, HttpServletRequest request) {
        HttpRespEnvelope hre = null;
        try
        {
            // Token验证
            super.checkSecureToken(request);

            // 当没用用户名字 将互生号设置到名字中
            if (hsbToCash.getCustName().equals(null) || hsbToCash.getCustName().equals("undefined")) {
                hsbToCash.setCustName(hsbToCash.getHsResNo());
            }

            // 非空数据验证
            RequestUtil.verifyParamsIsNotEmpty(
                    new Object[] { tradePwd, RespCode.PW_TRADEPWD_INVALID },            // 交易密码
                    new Object[] { randomToken, RespCode.AS_SECURE_TOKEN_INVALID },     // 随机token
                    new Object[] { hsbToCash.getHsResNo(), RespCode.AS_CUSTID_INVALID } // 客户互生号
                    );

            // 正整数验证
            RequestUtil.verifyPositiveInteger(hsbToCash.getFromHsbAmt(), RespCode.PW_HSB_NUMBER_INVALID);
            
            // 呼叫中心的特殊处理方法
            tradePwd = RequestUtil.encodeBase64String(request, tradePwd);
            
            //互生币转货币
            hsbAccountService.hsbTransferCurrency(hsbToCash, tradePwd, randomToken,hs_isCard);
            
            hre = new HttpRespEnvelope();
        }
        catch (HsException e)
        {
            hre = new HttpRespEnvelope(e);
        }
        
        return hre;
    }

    /**
     * 初始化兑换互生币
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
    @RequestMapping(value = { "/init_transfer_hsb" }, method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope initTransferHsb(String custId,String hs_isCard, HttpServletRequest request) {
        HttpRespEnvelope hre = null;

        try
        {
            // Token验证
            super.checkSecureToken(request);
            
            // 获取兑换互生币参数
            Map<String, Object> map = hsbAccountService.initTransferHsb(custId,hs_isCard);

            hre = new HttpRespEnvelope(map);
        }
        catch (HsException e)
        {
            hre = new HttpRespEnvelope(e);
        }

        return hre;
    }

    /**
     * 兑换互生币提交数据
     * 
     * @param order
     *            订单对象
     * @param orderType
     *            订单类型 1订单支付2网银支付
     * @param tradePwd
     *            交易密码
     * @param randomToken
     *            随机token（防止CSRF攻击）
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = { "/transfer_hsb" }, method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope transferHsb(@ModelAttribute Order order,String hs_isCard , HttpServletRequest request) {
        HttpRespEnvelope hre = null;

        try
        {
            // Token验证
            super.checkSecureToken(request);
            
            // 非空验证
            RequestUtil.verifyParamsIsNotEmpty(
                    new Object[] { order.getHsResNo(), RespCode.PW_HSRESNO_INVALID },                   // 互生卡号不准为空
                    new Object[] { order.getCustId(), RespCode.PW_CUSTID_INVALID },                     // 客户号不能为空
                    new Object[] { order.getOrderOperator(), RespCode.PW_OPTCUSTID_INVALID },           // 操作员号不能为空
                    new Object[] { order.getCustType(), RespCode.PW_CUSTTYPE_INVALID},                  // 应支付金额
                    new Object[] { order.getOrderHsbAmount(), RespCode.PW_TRANSFER_NUMBER_INVALID}      // 兑换的互生币数量
                    );
            
            //持卡人与非持卡人验证  非持卡人不能执行查询状态
            if(StringUtils.isEmpty(hs_isCard) == false && "undefined".equals(hs_isCard) == false&& "1".equals(hs_isCard) ==true)
            {
            	 // 执行查询 (1：未实名注册、2：已实名注册（有名字和身份证）、3:已实名认证)
            	order.setCustType(CustType.NOT_HS_PERSON.getCode());
            	//查询网络信息
            	AsNetworkInfo networkInfo = this.noCardholderService.findNetworkInfoByCustId(order.getCustId());
            	//保存姓名
            	order.setCustName(networkInfo.getName());
            }
            
            // 当没用用户名字 将互生号设置到名字中
            if (StringUtils.isBlank(order.getCustName()) || order.getCustName().equals("undefined")) {
                order.setCustName(order.getHsResNo());
            }
            
           
            
            //获取交易号
            String transNo = hsbAccountService.transferHsb(order);
            hre = new HttpRespEnvelope(transNo);
        } catch (HsException e) {
            hre = new HttpRespEnvelope(e);
        }
        
        return hre;
    }

    /**
     * 分页查询的条件约束
     * 
     * @param request
     *            当前请求对象
     * @param filterMap
     *            查询条件Map
     * @param sortMap
     * @return
     * @see com.gy.hsxt.access.web.common.controller.BaseController#beforeList(javax.servlet.http.HttpServletRequest,
     *      java.util.Map, java.util.Map)
     */
    @Override
    protected HttpRespEnvelope beforeList(HttpServletRequest request, Map filterMap, Map sortMap) {
        // 变量声明
        HttpRespEnvelope hre = null;

        hre = RequestUtil.checkParamIsNotEmpty(new Object[] { filterMap.get("beginDate"),
                ASRespCode.AS_QUERY_STA_TIME_FORM_INVALID.getDesc() }, // 开始时间非空验证
                new Object[] { filterMap.get("endDate"), ASRespCode.AS_QUERY_END_TIME_FORM_INVALID.getDesc() } // 结束时间非空验证
                // ,new Object[] { filterMap.get("subject"), "3000" }
                ); // 业务类别

        // 返回验证信息
        return hre;
    }

    /**
     * 互生币账户明细查询
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
     * 支付限额设置查询
     * 
     * @param custId
     *            客户号
     * @param pointNo
     *            互生卡号
     * @param entType
     *            企业类型
     * @param request
     *            当前请求数据
     * @return
     */
    @ResponseBody
    @RequestMapping(value = { "/pay_Limit_Setting" }, method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope payLimitSetting(PersonBase personBase, HttpServletRequest request) {
        HttpRespEnvelope hre = null;

        try
        {
            // Token验证
            super.verifySecureToken(request);

            // 获取持卡人支付限额配置
            Map<String, Object> map = hsbAccountService.payLimitSetting(personBase);
            
            if(map == null){
            	map = new HashMap<>();
            }
            Double dbPaymentMax = CommonUtils.toDouble(this.configService.getConsumerPaymentMax());
            if(dbPaymentMax != null){
            	map.put("dbPaymentMax", new BigDecimal(dbPaymentMax).toString());
            }
            Double mrPaymentMax = CommonUtils.toDouble(this.configService.getConsumerPaymentDayMax());
            if(mrPaymentMax != null){
            	map.put("mrPaymentMax", new BigDecimal(mrPaymentMax).toString());
            }
            
            hre = new HttpRespEnvelope(map);
        }
        catch (HsException e)
        {
            hre = new HttpRespEnvelope(e);
        }
        return hre;
    }

    /**
     * 支付限额修改
     * 
     * @param custId
     *            客户号
     * @param pointNo
     *            互生卡号
     * @param entType
     *            企业类型
     * @param request
     *            当前请求数据
     * @return
     */
    @ResponseBody
    @RequestMapping(value = { "/pay_Limit_Upate" }, method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope payLimitUpdate(PayLimit payLimit,String hs_isCard , HttpServletRequest request) {
        HttpRespEnvelope hre = null;
        try
        {
            // 验证入参
            payLimit.checkEmpty();

            // 校验验证码
            super.verifyCodes(request);
            
            //开启单笔支付限额，验证支付额度格式
            if ("Y".equals(payLimit.getSingleQuotaReSwitch())) {
                Double val = CommonUtils.toDouble(payLimit.getSingleQuotaRe());
                Double max = CommonUtils.toDouble(this.configService.getConsumerPaymentMax());
                if((val != null && max != null) && val > max){
                	throw new HsException(ASRespCode.PW_BUY_HSB_DB_MORE_ERROR);
                }
            }
            
            //开启每日支付限额，验证支付额度格式
            if ("Y".equals(payLimit.getDayQuotaReSwitch())) {
            	Double val = CommonUtils.toDouble(payLimit.getDayQuota());
                Double max = CommonUtils.toDouble(this.configService.getConsumerPaymentDayMax());
                if((val != null && max != null) && val > max){
                	throw new HsException(ASRespCode.PW_BUY_HSB_JR_MORE_ERROR);
                }
            }

            // 修改支付限额
            hsbAccountService.payLimitUpdate(payLimit,hs_isCard);
            hre = new HttpRespEnvelope();
        }
        catch (HsException e)
        {
            hre = new HttpRespEnvelope(e);
        }
        return hre;
    }

    /**
     * 获取兑换互生币支付url
     * @param netPay
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = { "/convert_pay" }, method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope convertPay(NetPay netPay, HttpServletRequest request) {
        HttpRespEnvelope hre = null;

        try
        {
            //验证非空
            netPay.vaildNotNull();
            
            // 获取互生币兑换支付URL
            String url = hsbAccountService.convertHSBPay(netPay);
            hre = new HttpRespEnvelope(url);

        }
        catch (HsException e)
        {
            hre = new HttpRespEnvelope(e);
            hre.setRetCode(e.getErrorCode());
        }

        return hre;
    }
    
    /**
     * @return
     * @see com.gy.hsxt.access.web.common.controller.BaseController#getEntityService()
     */
    @Override
    protected IBaseService getEntityService() {
        return balanceService;
    }
    
 
}
