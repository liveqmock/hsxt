/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.person.controllers.safetyset;

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

import com.alibaba.fastjson.JSON;
import com.gy.hsi.common.utils.StringUtils;
import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.access.web.common.bean.HttpRespEnvelope;
import com.gy.hsxt.access.web.common.controller.BaseController;
import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.access.web.common.utils.RequestUtil;
import com.gy.hsxt.access.web.person.bean.safetySet.DealPwdReset;
import com.gy.hsxt.access.web.person.bean.safetySet.DealPwdResetCheck;
import com.gy.hsxt.access.web.person.services.common.PersonConfigService;
import com.gy.hsxt.access.web.person.services.safetyset.IPwdQuestionService;
import com.gy.hsxt.access.web.person.services.safetyset.IPwdSetService;
import com.gy.hsxt.access.web.person.services.safetyset.IReserveInfoService;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.uc.as.api.consumer.IUCAsCardHolderService;
import com.gy.hsxt.uc.as.api.enumerate.UserTypeEnum;
import com.gy.hsxt.uc.as.bean.common.AsPwdQuestion;

/***************************************************************************
 * <PRE>
 * Description 		: 安全设置Controller
 * 
 * Project Name   	: hsxt-access-web-person 
 * 
 * Package Name     : com.gy.hsxt.access.web.person.controllers.safetyset  
 * 
 * File Name        : SafetySetController 
 * 
 * Author           : LiZhi Peter
 * 
 * Create Date      : 2015-11-17 下午5:43:31
 * 
 * Update User      : LiZhi Peter
 * 
 * Update Date      : 2015-11-17 下午5:43:31
 * 
 * UpdateRemark 	: 说明本次修改内容
 * 
 * Version          : v1.0
 * 
 * </PRE>
 ***************************************************************************/
@Controller
@RequestMapping("safetySet")
public class SafetySetController extends BaseController {
	private static final String MOUDLENAME = "com.gy.hsxt.access.web.person.controllers.safetyset.SafetySetController";
	private static final String NEWLINE = "\r\n";
    @Resource
    private IPwdSetService pwdSetService = null; // 用户密码操作服务类

    @Resource
    private IPwdQuestionService pwdQuestionService = null; // 密保问题服务类

    @Resource
    private IReserveInfoService reserveInfoService; // 预留信息服务类
    
    @Resource
    private PersonConfigService personConfigSevice;
    
    @Resource
    private IUCAsCardHolderService cardHolderService;

    /**
     * 查询是否设置过预留信息及交易密码
     * 
     * @param custId
     * @param pointNo
     * @param tradePwd
     * @param reservInfo
     * @return
     */
    @RequestMapping(value = { "/findSecuritySetType" }, method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public HttpRespEnvelope checkSetPwdOrReservInfoIsOk(String custId, String pointNo, HttpServletRequest request) {
        HttpRespEnvelope hre = null;
        Map<String, Object> map = new HashMap<String, Object>();;
        try
        {
            // Token验证
            super.checkSecureToken(request);
            // 查询用户交易密码是否设置
            boolean isSettradePwd = this.pwdSetService.isSetTradePwd(custId, UserTypeEnum.CARDER.getType());
            // 查询用户预留信息
            String reserveInfo = this.reserveInfoService.findReserveInfoByCustId(custId, UserTypeEnum.CARDER.getType());
            // 保存数据
            map.put("dealPwdFlag", isSettradePwd);
            map.put("reserveInfo", !StringUtils.isEmpty(reserveInfo));

            hre = new HttpRespEnvelope(map);
        }
        catch (HsException e)
        {
            e.printStackTrace();
            hre = new HttpRespEnvelope(e);
        }
        return hre;
    }

    /**
     * 查询的预留信息
     * 
     * @param custId
     * @param pointNo
     * @param tradePwd
     * @param reservInfo
     * @return
     */
    @RequestMapping(value = { "/find_reservation_information" }, method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public HttpRespEnvelope findReservationInformation(String custId, String pointNo, String hs_isCard,HttpServletRequest request) {
        // 变量声明

        HttpRespEnvelope hre = null;
        Map<String, Object> map = null;
        try
        {
            // Token验证
            super.checkSecureToken(request);

            // 变量初始化
            map = new HashMap<String, Object>();
            
            String userType = UserTypeEnum.CARDER.getType();
            if(StringUtils.isEmpty(hs_isCard) == false && "undefined".equals(hs_isCard) == false&& "1".equals(hs_isCard) ==true){
            	userType = UserTypeEnum.NO_CARDER.getType();
            }
            // 查询用户预留信息
            String reserveInfo = this.reserveInfoService.findReserveInfoByCustId(custId, userType);

            map.put("reservInfo", reserveInfo);

            hre = new HttpRespEnvelope(map);

        }
        catch (HsException e)
        {
            hre = new HttpRespEnvelope(e);
        }
        return hre;
    }

    /**
     * 修改预留信息方法
     * 
     * @param custId
     *            客户号
     * @param reservInfo
     *            预留信息
     * @param request
     * @return
     */
    @RequestMapping(value = { "/save_reservation_information" }, method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public HttpRespEnvelope saveReservationInformation(String custId, String reservInfo, HttpServletRequest request) {
        // 变量声明

        HttpRespEnvelope hre = null;

        try
        {
            // Token验证
            super.checkSecureToken(request);
            
            // 非空验证
            RequestUtil.verifyParamsIsNotEmpty(
                    new Object[] { reservInfo,RespCode.PW_NO_SET_RESERVE_INFO.getCode(), RespCode.PW_NO_SET_RESERVE_INFO.getDesc() } // 预留信息
                    );
            
            //长度验证
            RequestUtil.verifyParamsLength(
                    new Object[] { reservInfo,2,20, RespCode.PW_RESERVE_MAX_INVALID.getCode(),RespCode.PW_RESERVE_MAX_INVALID.getDesc() }  // 预留信息
                    );
            
            // 查询用户预留信息
            this.reserveInfoService.saveReserveInfoByCustId(custId, UserTypeEnum.CARDER.getType(), reservInfo);

            // 构造返回结果
            hre = new HttpRespEnvelope();

        }
        catch (HsException e)
        {
            hre = new HttpRespEnvelope(e);
        }

        return hre;
    }

    /**
     * 查询密码提示问题
     * 
     * @param custId
     *            客户号
     * @param pointNo
     *            互生号
     * @param request
     *            当前求求
     * @return
     */
    @RequestMapping(value = { "/find_password_question" }, method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public HttpRespEnvelope findPasswordQuestion(String hsResNo, HttpServletRequest request) {
        // 变量声明
        HttpRespEnvelope hre = null;

        try
        {
         // 非空验证
            RequestUtil.verifyParamsIsNotEmpty(
                    new Object[] { hsResNo,RespCode.AS_POINTNO_INVALID.getCode(), RespCode.AS_POINTNO_INVALID.getDesc() } // 互生号
                    );
            
            // 查询相关的
            List<AsPwdQuestion> pwdQuestionList = this.pwdQuestionService.findPwdQuestionByCustId(hsResNo,UserTypeEnum.CARDER);

            hre = new HttpRespEnvelope(pwdQuestionList);

        }
        catch (HsException e)
        {
            hre = new HttpRespEnvelope(e);
        }
        return hre;
    }

    /**
     * 保存密保问题
     * 
     * @param pointNo
     * @param questionId
     * @param answer
     * @param request
     * @return
     */
    @RequestMapping(value = { "/save_password_question" }, method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public HttpRespEnvelope savePasswordQuestion(@ModelAttribute AsPwdQuestion pwdQuestion, String custId,
            HttpServletRequest request) {
        // 变量声明
        HttpRespEnvelope hre = null;

        try
        {

            // 非空验证
            RequestUtil.verifyParamsIsNotEmpty(
                    new Object[] { pwdQuestion.getQuestion(),RespCode.PW_RESTRICTIONS_INVALID.getCode(), RespCode.PW_RESTRICTIONS_INVALID.getDesc() }, // 密保问题
                    new Object[] { pwdQuestion.getAnswer(), RespCode.PW_RESTRICTIONS_ANSWER_INVALID.getCode(),RespCode.PW_RESTRICTIONS_ANSWER_INVALID.getDesc() }// 密保答案
                    );
          //长度规则验证
            RequestUtil.verifyParamsLength(
                    new Object[] { pwdQuestion.getAnswer(),2,50, RespCode.PW_RESTRICTIONS_MAX_INVALID.getCode(),RespCode.PW_RESTRICTIONS_MAX_INVALID.getDesc() }             // 密保答案
                    );
            // 执行添加
            this.pwdQuestionService.savePwdQuestion(pwdQuestion, custId);

            hre = new HttpRespEnvelope();

        }
        catch (HsException e)
        {
            hre = new HttpRespEnvelope(e);
        }
        return hre;
    }

    /**
     * 密保验证
     * 
     * @param pwdQuestion
     *            密保问题
     * @param pointNo
     * @param request
     * @return
     */
    @RequestMapping(value = { "/check_pwd_question" }, method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public HttpRespEnvelope checkPwdQuestion(@ModelAttribute AsPwdQuestion pwdQuestion, String pointNo,
            HttpServletRequest request) {
        // 变量声明
        HttpRespEnvelope hre = null;

        try
        {
            // 非空验证
            RequestUtil.verifyParamsIsNotEmpty(new Object[] { pwdQuestion.getQuestionId(),
                    RespCode.PW_RESTRICTIONS_INVALID.getCode(), RespCode.PW_RESTRICTIONS_INVALID.getDesc() }, // 密保问题
                    new Object[] { pwdQuestion.getAnswer(), RespCode.PW_RESTRICTIONS_ANSWER_INVALID.getCode(),
                            RespCode.PW_RESTRICTIONS_ANSWER_INVALID.getDesc() }, // 密保答案
                    new Object[] { pointNo, RespCode.AS_POINTNO_INVALID.getCode(),
                            RespCode.AS_POINTNO_INVALID.getDesc() } // 互生号

                    );
            String perCustId = cardHolderService.findCustIdByResNo(pointNo);
            // 查询相关的
            this.pwdQuestionService.checkPwdQuestion(pwdQuestion, perCustId, UserTypeEnum.CARDER.getType());

            hre = new HttpRespEnvelope();

        }
        catch (HsException e)
        {
            hre = new HttpRespEnvelope(e);
        }
        return hre;
    }

    /**
     * 修改登录密码
     * 
     * @param randomToken
     *            AES秘钥（随机报文校验token）
     * @param userName
     *            用户名持卡人=互生号 非持卡人手机号
     * @param oldLoginPwd
     *            老密码
     * @param newLoginPwd
     *            新密码
     * @param newLoginPwdRe
     *            新密码确认
     * @param request
     * @return
     */
    @RequestMapping(value = { "/modify_password" }, method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public HttpRespEnvelope modifyPassword(String randomToken, String custId, String oldLoginPwd, String newLoginPwd,String newLoginPwdRe, HttpServletRequest request) {
        // 变量声明
        HttpRespEnvelope hre = null;

        try
        {
            // token校验
            super.checkSecureToken(request);

            // 非空验证
            RequestUtil.verifyParamsIsNotEmpty(
                    new Object[] { randomToken, RespCode.AS_RANDOM_TOKEN_INVALID.getCode(),RespCode.AS_RANDOM_TOKEN_INVALID.getDesc() }, // AES秘钥（随机报文校验token）
                    new Object[] { oldLoginPwd, RespCode.PW_OLD_PASSWORD_INVALID.getCode(),RespCode.PW_OLD_PASSWORD_INVALID.getDesc() }, // 老密码
                    new Object[] { newLoginPwd, RespCode.PW_NEW_PASSWORD_INVALID.getCode(),RespCode.PW_NEW_PASSWORD_INVALID.getDesc() }, // 新密码
                    new Object[] { newLoginPwdRe, RespCode.PW_NEW_PASSWORD_CONFIRM_INVALID.getCode(),RespCode.PW_NEW_PASSWORD_CONFIRM_INVALID.getDesc() } // 新密码确认
                    );
            
            //字符串特殊字符转换 呼叫中心跳转过来+会丢失
         /*   oldLoginPwd = oldLoginPwd.trim().replaceAll("!T", "+");
            newLoginPwd = newLoginPwd.trim().replaceAll("!T", "+");
            newLoginPwdRe = newLoginPwdRe.trim().replaceAll("!T", "+");*/
            
            
            
            // 密码是否相等校验
            RequestUtil.verifyEquals(newLoginPwd, newLoginPwdRe, RespCode.PW_LOGIN_PWD_NOT_EQUALS);

            oldLoginPwd =RequestUtil.encodeBase64String(request, oldLoginPwd) ;
            newLoginPwd =RequestUtil.encodeBase64String(request, newLoginPwd);
                    
            // 执行修改
            this.pwdSetService.updateLoginPwd(custId, UserTypeEnum.CARDER.getType(), oldLoginPwd, newLoginPwd, randomToken);

            hre = new HttpRespEnvelope();

        }
        catch (HsException e)
        {	
        	hre = handleModifyPasswordException(e);
        }
        return hre;
    }
    
    private HttpRespEnvelope handleModifyPasswordException(HsException e){
    	HttpRespEnvelope hre = new HttpRespEnvelope();
    	int errorCode = e.getErrorCode();
//    	String errorMsg = e.getMessage();
//    	String funName = "handleModifyPasswordException";
//    	StringBuffer msg = new StringBuffer();
//    	msg.append("处理修改登录密码Exception时报错,传入参数信息  e["+JSON.toJSONString(e)+"] "+NEWLINE);
    	if(160359 == errorCode || 160108 == errorCode){
    		/**保留
     		try {
        			if(!StringUtils.isEmptyTrim(errorMsg) && errorMsg.contains(",")){
            			String[] msgInfo = errorMsg.split(",");
            			if(msgInfo.length > 1){
            				int loginFailTimes = Integer.parseInt(msgInfo[0]);
            				int maxFailTimes = Integer.parseInt(msgInfo[1]);
            			}
            		}
            		
    			} catch (Exception e2) {
    				SystemLog.error(MOUDLENAME, funName, msg.toString(), e2);
    			}
        		*/
    		hre = new HttpRespEnvelope(e.getErrorCode(),"登录密码不正确");
    	}else if(160467 == errorCode){
    		hre = new HttpRespEnvelope(e.getErrorCode(),"账户被锁定");
    	}else{
    		hre = new HttpRespEnvelope(e);
    	}
    	return hre;
    }
    
    /**
     * 修改交易密码
     * 
     * @param custId
     *            客户编号
     * @param randomToken
     *            随机登录token
     * @param oldTradePwd
     *            老的交易密码
     * @param newTradePwd
     *            新交易密码
     * @param newTradePwdRe
     *            确定交易密码
     * @param request
     * @return
     */
    @RequestMapping(value = { "/modify_del_password" }, method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public HttpRespEnvelope modifyDelPassword(String custId, String randomToken, String oldTradePwd,String newTradePwd, String newTradePwdRe, HttpServletRequest request) {

        // 变量声明
        HttpRespEnvelope hre = null;

        try
        {
            // Token验证
            super.checkSecureToken(request);
            
            // 参数非空验证
            RequestUtil.verifyParamsIsNotEmpty(
                    new Object[] { randomToken, RespCode.AS_RANDOM_TOKEN_INVALID.getCode(),RespCode.AS_RANDOM_TOKEN_INVALID.getDesc() }, // AES秘钥（随机报文校验token）
                    new Object[] { oldTradePwd, RespCode.PW_OLD_PASSWORD_INVALID.getCode(),RespCode.PW_OLD_PASSWORD_INVALID.getDesc() }, // 老密码
                    new Object[] { newTradePwd, RespCode.PW_NEW_PASSWORD_INVALID.getCode(),RespCode.PW_NEW_PASSWORD_INVALID.getDesc() }, // 新密码
                    new Object[] { newTradePwdRe, RespCode.PW_NEW_PASSWORD_CONFIRM_INVALID.getCode(),RespCode.PW_NEW_PASSWORD_CONFIRM_INVALID.getDesc() } // 新密码确认
                    );

            // 密码是否相等校验
            RequestUtil.verifyEquals(newTradePwd, newTradePwdRe, RespCode.PW_TRADE_PWD_NOT_EQUALS);

            // 修改密码
            pwdSetService.updateTradePwd(custId, UserTypeEnum.CARDER.getType(), oldTradePwd, newTradePwd, randomToken);
            hre = new HttpRespEnvelope();
        }
        catch (HsException e)
        {
            hre = new HttpRespEnvelope(e);
        }

        return hre;
    }
    /**
     * 获取交易密码与登录密码的长度
     * 
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "initTradePwd", method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope initTradePwd(HttpServletRequest request) {
        
        //变量声明
        Map<String, Object> tempMap = null;
        HttpRespEnvelope returnHre = null;// 返回数据对象

        try
        {
            // Token验证
            super.checkSecureToken(request);
            
            tempMap = new HashMap<>();
            // 登录密码长度限制
            tempMap.put("loginPassLength", personConfigSevice.getLoginPasswordLength());
            // 交易密码长度限制
            tempMap.put("tradingPasswordLength", personConfigSevice.getTradingPasswordLength());

            returnHre = new HttpRespEnvelope(tempMap);
        }
        catch (Exception e)
        {
            returnHre = new HttpRespEnvelope(e);
        }

        return returnHre;
    }
    /**
     * 初始化交易密码
     * 
     * @param custId
     * @param randomToken
     * @param tradePwd
     * @param tradePwdRe
     * @param request
     * @return
     */
    @RequestMapping(value = { "/set_tradePwd" }, method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public HttpRespEnvelope setTradePwd(String custId, String randomToken, String tradePwd, String tradePwdRe,HttpServletRequest request) {

        // 变量声明
        HttpRespEnvelope hre = null;

        try
        {
            // Token验证
            super.checkSecureToken(request);
            // 参数非空验证
            RequestUtil.verifyParamsIsNotEmpty(
                    new Object[] { randomToken, RespCode.AS_RANDOM_TOKEN_INVALID.getCode(),RespCode.AS_RANDOM_TOKEN_INVALID.getDesc() }, // AES秘钥（随机报文校验token）
                    new Object[] { tradePwd, RespCode.PW_TRADEPWD_INVALID.getCode(),RespCode.PW_TRADEPWD_INVALID.getDesc() }, // 交易密码
                    new Object[] { tradePwdRe, RespCode.PW_TRADEPWD_CONFIRM_INVALID.getCode(),RespCode.PW_TRADEPWD_CONFIRM_INVALID.getDesc() } // 确认交易密码
                    );

            // 密码是否相等校验
            RequestUtil.verifyEquals(tradePwd, tradePwdRe, RespCode.PW_TRADE_PWD_NOT_EQUALS);

            // 修改密码
            pwdSetService.setTradePwd(custId, randomToken, tradePwd, UserTypeEnum.CARDER.getType());
            hre = new HttpRespEnvelope();
        }
        catch (HsException e)
        {
            hre = new HttpRespEnvelope(e);
        }

        return hre;
    }

    /**
     * 重置交易密码前验证证件号码、姓名、登录密码是否输入正确.
     * @param dprc
     * @param request
     * @return
     */
    @RequestMapping(value = { "/verification_del_password" }, method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public HttpRespEnvelope verificationDelPassword(DealPwdResetCheck dprc, HttpServletRequest request) {
        HttpRespEnvelope hre = null;
        try
        {
            // Token验证
            super.verifySecureToken(request);
            // 参数非空验证
            dprc.checkEmptyData();
            // 重置密码验证
            pwdSetService.checkUserinfo(dprc);
            hre = new HttpRespEnvelope();
        }
        catch (HsException e)
        {
            hre = new HttpRespEnvelope(e);
        }

        return hre;
    }

    /**
     * 重置交易密码
     * @param dprc
     * @param request
     * @return
     */
    @RequestMapping(value = { "/reset_del_password" }, method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public HttpRespEnvelope resetDelPassword(DealPwdReset dpr, HttpServletRequest request) {
        HttpRespEnvelope hre = null;
        try
        {
            // Token验证
            super.checkSecureToken(request);
            // 参数验证
            dpr.checkData();
            // 重置交易密码
            pwdSetService.resetTradePwd(dpr);
            hre = new HttpRespEnvelope();
        }
        catch (HsException e)
        {
            hre = new HttpRespEnvelope(e);
        }

        return hre;
    }

    @Override
    protected IBaseService getEntityService() {
        // TODO Auto-generated method stub
        return null;
    }

}
