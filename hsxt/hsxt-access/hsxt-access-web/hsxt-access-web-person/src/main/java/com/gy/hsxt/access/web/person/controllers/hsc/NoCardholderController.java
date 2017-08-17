package com.gy.hsxt.access.web.person.controllers.hsc;

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
import com.gy.hsxt.access.web.common.constant.ASRespCode;
import com.gy.hsxt.access.web.common.controller.BaseController;
import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.access.web.common.utils.RequestUtil;
import com.gy.hsxt.access.web.person.bean.BankInfo;
import com.gy.hsxt.access.web.person.bean.PersonBase;
import com.gy.hsxt.access.web.person.services.common.IBaseDataService;
import com.gy.hsxt.access.web.person.services.common.PersonConfigService;
import com.gy.hsxt.access.web.person.services.consumer.ICardholderService;
import com.gy.hsxt.access.web.person.services.consumer.INoCardholderService;
import com.gy.hsxt.access.web.person.services.hsc.IBankCardService;
import com.gy.hsxt.access.web.person.services.safetyset.IPwdQuestionService;
import com.gy.hsxt.access.web.person.services.safetyset.IPwdSetService;
import com.gy.hsxt.access.web.person.services.safetyset.IReserveInfoService;
import com.gy.hsxt.common.constant.CustType;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.StringUtils;
import com.gy.hsxt.lcs.bean.City;
import com.gy.hsxt.lcs.bean.Province;
import com.gy.hsxt.uc.as.api.enumerate.UserTypeEnum;
import com.gy.hsxt.uc.as.bean.common.AsBankAcctInfo;
import com.gy.hsxt.uc.as.bean.common.AsConsumerLoginInfo;
import com.gy.hsxt.uc.as.bean.common.AsNetworkInfo;
import com.gy.hsxt.uc.as.bean.common.AsNoCarderMainInfo;
import com.gy.hsxt.uc.as.bean.common.AsPwdQuestion;
import com.gy.hsxt.uc.as.bean.common.AsReceiveAddr;
import com.gy.hsxt.uc.as.bean.enumerate.ChannelTypeEnum;

/***************************************************************************
 * <PRE>
 * Description 		: 非持卡人操作类
 * 
 * Project Name   	: hsxt-access-web-person
 * 
 * Package Name     : com.gy.hsxt.access.web.person.controllers.hsc.NoCardholderController.java
 * 
 * File Name        : NoCardholderController
 * 
 * Author           : LiZhi Peter
 * 
 * Create Date      : 2016-04-15 下午3:18:28
 * 
 * Update User      : LiZhi Peter
 * 
 * Update Date      : 2016-04-15 下午3:18:28
 * 
 * UpdateRemark 	: 说明本次修改内容
 * 
 * Version          : v0.0.1
 * 
 * </PRE>
 ***************************************************************************/
@Controller
@RequestMapping("noCardHolder")
public class NoCardholderController extends BaseController {

	@Resource
    private IPwdSetService pwdSetService = null; // 用户密码操作服务类

    @Resource
    private IPwdQuestionService pwdQuestionService = null; // 密保问题服务类

    @Resource
    private IReserveInfoService reserveInfoService; // 预留信息服务类
    
    @Resource
	private ICardholderService cardholderService;// 持卡人服务类
    
    @Resource
    private INoCardholderService noCardholderService;//非持卡人服务类

    @Resource
    private IBankCardService bankCardService; // 银行卡信息
    
    @Resource
    private PersonConfigService personConfigSevice;
    
    @Resource
    private IBaseDataService baseDataService;
	
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
            // 查询用户交易密码是否设置
            boolean isSettradePwd = this.pwdSetService.isSetTradePwd(custId, UserTypeEnum.NO_CARDER.getType());
            // 查询用户预留信息
            String reserveInfo = this.reserveInfoService.findReserveInfoByCustId(custId, UserTypeEnum.NO_CARDER.getType());
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
    public HttpRespEnvelope findReservationInformation(String custId, String pointNo, HttpServletRequest request) {
        // 变量声明

        HttpRespEnvelope hre = null;
        Map<String, Object> map = null;
        try
        {
            // Token验证
            super.checkSecureToken(request);

            // 变量初始化
            map = new HashMap<String, Object>();

            // 查询用户预留信息
            String reserveInfo = this.reserveInfoService.findReserveInfoByCustId(custId, UserTypeEnum.NO_CARDER.getType());

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
            this.reserveInfoService.saveReserveInfoByCustId(custId, UserTypeEnum.NO_CARDER.getType(), reservInfo);

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
            List<AsPwdQuestion> pwdQuestionList = this.pwdQuestionService.findPwdQuestionByCustId(hsResNo,UserTypeEnum.NO_CARDER);

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
            
            // 密码是否相等校验
            RequestUtil.verifyEquals(newLoginPwd, newLoginPwdRe, RespCode.PW_LOGIN_PWD_NOT_EQUALS);
                    
            // 执行修改
            this.pwdSetService.updateLoginPwd(custId, UserTypeEnum.NO_CARDER.getType(), oldLoginPwd, newLoginPwd, randomToken);

            hre = new HttpRespEnvelope();

        }
        catch (HsException e)
        {
            hre = new HttpRespEnvelope(e);
        }
        return hre;
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
            pwdSetService.setTradePwd(custId, randomToken, tradePwd, UserTypeEnum.NO_CARDER.getType());
            hre = new HttpRespEnvelope();
        }
        catch (HsException e)
        {
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
            pwdSetService.updateTradePwd(custId, UserTypeEnum.NO_CARDER.getType(), oldTradePwd, newTradePwd, randomToken);
            hre = new HttpRespEnvelope();
        }
        catch (HsException e)
        {
            hre = new HttpRespEnvelope(e);
        }

        return hre;
    }

    /**
	 * 发送短信验证码
	 * 
	 * @param custId
	 *            客户号
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/mobileSendCode" }, method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope mobileSendCode(String custId,String mobile, HttpServletRequest request) {

		// 变量声明
		HttpRespEnvelope hre = null;

		try {
			// Token验证
			super.checkSecureToken(request);

			// 获取用户手机号
			cardholderService.mobileSendCode(custId,mobile,CustType.NOT_HS_PERSON.getCode());

			hre = new HttpRespEnvelope();
		} catch (HsException e) {
			hre = new HttpRespEnvelope(e);
		}

		return hre;
	}
	
	/**
	 * 发送短信验证码
	 * 
	 * @param custId
	 *            客户号
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/delPwdSendMobileCode" }, method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope delPwdSendMobileCode(String custId,String mobile, HttpServletRequest request) {

		// 变量声明
		HttpRespEnvelope hre = null;

		try {
			// Token验证
			super.checkSecureToken(request);

			// 获取用户手机号
			this.noCardholderService.sendSmsCodeForResetPwd(custId,mobile);

			hre = new HttpRespEnvelope();
		} catch (HsException e) {
			hre = new HttpRespEnvelope(e);
		}

		return hre;
	}
	
	/**
	 * 获取持卡人已绑定手机号码
	 * 
	 * @param custId
	 *            客户号
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/findMobileByCustId" }, method = {RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope findMobileByCustId(String custId, String pointNo,
			HttpServletRequest request) {
		// 变量声明
		HttpRespEnvelope hre = null;
		try {
			// Token验证
			super.checkSecureToken(request);
			// 获取用户手机号
			Map<String, String> moblieMap = noCardholderService.findMobileByCustId(custId);

			hre = new HttpRespEnvelope(moblieMap);
		} catch (HsException e) {
			hre = new HttpRespEnvelope(e);
		}

		return hre;
	}
	
	
	/**
	 * 验证手机验证码是否正确
	 * @param custId 客户号
	 * @param mobile 手机
	 * @param smsCode 短信
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/checkSmsCode" }, method = {RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope checkSmsCode(String custId, String mobile,String smsCode , HttpServletRequest request) {
		// 变量声明
		HttpRespEnvelope hre = null;
		try {
			  // 参数非空验证
            RequestUtil.verifyParamsIsNotEmpty(
                    new Object[] { mobile, RespCode.PW_MOBILE_NULL.getCode(),RespCode.PW_MOBILE_NULL.getDesc() }, // 手机号码
                    new Object[] { smsCode, RespCode.PW_SMS_CODE_INVALID.getCode(),RespCode.PW_SMS_CODE_INVALID.getDesc() } // 短信交易
                    
                    );
			
			// 获取用户手机号
			String value = noCardholderService.checkSmsCode(mobile,smsCode);

			hre = new HttpRespEnvelope(value);
		} catch (HsException e) {
			hre = new HttpRespEnvelope(e);
		}

		return hre;
	}
	
	/**
	 * 修改绑定手机绑定
	 * 
	 * @param custId 	客户号
	 * @param pointNo 	互生卡号
	 * @param mobile 	手机号码
	 * @param smsCode 	短信验证码
	 * @param loginPwd	登录密码
	 * @param randomToken	随机报文
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/edit_bind_mobile" }, method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope editBindMobile(String custId,String userNameL, String mobile,
			String smsCode, String loginPwd, String randomToken, HttpServletRequest request) {

		// 变量声明
		HttpRespEnvelope hre = null;
		AsConsumerLoginInfo loginInfo = null;
		try {
			// Token验证
			super.checkSecureToken(request);

			// 非空验证
			RequestUtil.verifyParamsIsNotEmpty(
					new Object[] { mobile, RespCode.PW_MOBILE_NULL.getCode(),RespCode.PW_MOBILE_NULL.getDesc() }, // 手机号码
					new Object[] { loginPwd, RespCode.PW_LOGINPWD_INVALID.getCode(),RespCode.PW_LOGINPWD_INVALID.getDesc() },// 登录密码
					new Object[] { randomToken, RespCode.AS_RANDOM_TOKEN_INVALID.getCode(),RespCode.AS_RANDOM_TOKEN_INVALID.getDesc() }, // 随机报文
					new Object[] { smsCode, RespCode.PW_SMS_CODE_INVALID.getCode(),RespCode.PW_SMS_CODE_INVALID.getDesc() } // 验证码
					);

			// 获取用户手机号
			loginInfo = new AsConsumerLoginInfo();
			loginInfo.setMobile(userNameL);
			loginInfo.setLoginPwd(loginPwd);
			loginInfo.setRandomToken(randomToken);
			loginInfo.setChannelType(ChannelTypeEnum.WEB);
			loginInfo.setLoginIp(RequestUtil.getRemoteHost(request)); // 获取登录ip

			// 验证登录密码是否正确
			this.cardholderService.noCardHolderLogin(loginInfo);

			// 执行手机绑定手机号码 TODO 当前方法用户中心未提供
			this.cardholderService.addBindMobile(custId, mobile, smsCode,UserTypeEnum.NO_CARDER.getPrefix());
			
			// 执行成功返回信息自动包装
			hre = new HttpRespEnvelope();
			
		} catch (HsException e) {
			hre = new HttpRespEnvelope(e);
		}

		return hre;
	}
	
	/**
	 * 获取非持卡人已绑定邮箱
	 * 
	 * @param custId
	 *            客户号
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/findEamilByCustId" }, method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope findEamilByCustId(String custId, String pointNo,
			HttpServletRequest request) {

		// 变量声明
		HttpRespEnvelope hre = null;

		try {
			// Token验证
			super.checkSecureToken(request);
			
			// 获取邮箱信息
			Map<String, String> retMap = this.noCardholderService.findEamilByCustId(custId);

			hre = new HttpRespEnvelope(retMap);
		} catch (HsException e) {
			e.printStackTrace();
			hre = new HttpRespEnvelope(e);
		}

		return hre;
	}
	/**
	 * 获取非持卡人已绑定邮箱
	 * 
	 * @param custId
	 *            客户号
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/checkNoCarderMainInfo" }, method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope checkNoCarderMainInfo(AsNoCarderMainInfo mainInfo,HttpServletRequest request) {

		// 变量声明
		HttpRespEnvelope hre = null;

		try {
			// Token验证
			super.checkSecureToken(request);
			
			//校验验证码
			super.verifyCodes(request);
			
			//查询非持卡人手机号码
			Map<String,String> map = noCardholderService.findMobileByCustId(mainInfo.getPerCustId());
			
			String mobile = map.get("mobile");
			
			mainInfo.setMobile(mobile);
			
			// 获取邮箱信息
			String value = this.noCardholderService.checkNoCarderMainInfo(mainInfo);
			
			hre = new HttpRespEnvelope(value);
		} catch (HsException e) {
			e.printStackTrace();
			hre = new HttpRespEnvelope(e);
		}

		return hre;
	}
	/**
	 * 获取非持卡人已绑定邮箱
	 * 
	 * @param custId
	 *            客户号
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/reset_tradePwd" }, method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope resetTradePwd(String custId , String random,String newTradePwd ,String secretKey , HttpServletRequest request) {

		// 变量声明
		HttpRespEnvelope hre = null;

		try {
			// Token验证
			super.checkSecureToken(request);
			
			//查询非持卡人手机号码
			Map<String,String> map = noCardholderService.findMobileByCustId(custId);
			
			String mobile = map.get("mobile");
			
			
			// 重置交易密码
			this.noCardholderService.resetNoCarderTradepwd(mobile,random,newTradePwd,secretKey);
			
			hre = new HttpRespEnvelope();
			
			//对象释放
			map = null;
			mobile = null;
			
		} catch (HsException e) {
			e.printStackTrace();
			hre = new HttpRespEnvelope(e);
		}

		return hre;
	}
	/**
	 * 添加非持卡绑定邮箱
	 * 
	 * @param custId
	 *            客户号
	 * @param pointNo
	 *            互生号
	 * @param mobile
	 *            手机号
	 * @param smsCode
	 *            验证码
	 * @param request
	 *            当前请求
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/add_bind_email" }, method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope addBindEmail(PersonBase personBase, String email, String confirmEmail,
			HttpServletRequest request) {

		// 变量声明
		HttpRespEnvelope hre = null;

		try {
			// Token验证
			super.checkSecureToken(request);
			
			// 非空验证
			RequestUtil.verifyParamsIsNotEmpty(new Object[] { personBase.getCustId(),
					RespCode.AS_CUSTID_INVALID.getCode(), RespCode.AS_CUSTID_INVALID.getDesc() }, // 客户号
					new Object[] { email, RespCode.PW_EMAIL_INVALID.getCode(),
							RespCode.PW_EMAIL_INVALID.getDesc() }, // 邮箱信息
					new Object[] { confirmEmail, RespCode.PW_CONFIRM_EMAIL_INVALID.getCode(),
							RespCode.PW_CONFIRM_EMAIL_INVALID.getDesc() } // 确认邮箱信息
					);
			String mobile = request.getParameter("mobile");//手机号
			
			personBase.setPointNo(mobile);
			
			// 验证两次邮箱是否一致
			RequestUtil.verifyEquals(email, confirmEmail, RespCode.PW_INCONSISTENT_EMAIL_INVALID);

			// 邮箱规则验证
			RequestUtil.verifyEamil(email, RespCode.PW_EMAIL_FORM_INVALID);

			// 执行手机绑定手机号码 TODO 当前方法用户中心未提供
			this.cardholderService.addBindEmail(personBase, email, UserTypeEnum.NO_CARDER.getType(),CustType.NOT_HS_PERSON.getCode());
			// 执行成功返回信息自动包装
			hre = new HttpRespEnvelope();
		} catch (HsException e) {
			if(160460 == e.getErrorCode()){
        		hre = new HttpRespEnvelope(e.getMessage());
			}else{
				hre = new HttpRespEnvelope(e);
			}
		}
		return hre;
	}

	/**
	 * 修改绑定邮箱
	 * 
	 * @param personBase
	 * @param email
	 * @param confirmEmail
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/mail_modify" }, method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope mailModify(String custId, String loginPwd, String email,String confirmEmail,String randomToken, HttpServletRequest request) {
		HttpRespEnvelope hre = null;
		try {
			// Token验证
			super.checkSecureToken(request);
			// 非空验证
			RequestUtil.verifyParamsIsNotEmpty(new Object[] { loginPwd,
					ASRespCode.PW_LOGINPWD_INVALID }, // 客户号
					new Object[] { email, RespCode.PW_EMAIL_INVALID }, // 邮箱信息
					new Object[] { confirmEmail, RespCode.PW_CONFIRM_EMAIL_INVALID } // 确认邮箱信息
					);
			// 验证两次邮箱是否一致
			RequestUtil.verifyEquals(email, confirmEmail, RespCode.PW_INCONSISTENT_EMAIL_INVALID);
			// 邮箱规则验证
			RequestUtil.verifyEamil(email, RespCode.PW_EMAIL_FORM_INVALID);
			// 执行修改邮箱
			this.noCardholderService.changeBindEmail(custId,email, loginPwd, randomToken);
			hre = new HttpRespEnvelope();
		} catch (HsException e) {
			hre = new HttpRespEnvelope(e);
		}

		return hre;
	}

	/**
	 * 校验邮件
	 * 
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/checkEmailCode" }, method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope checkEmailCode(HttpServletRequest request, String param) {
		HttpRespEnvelope hre = null;
		try {
			// 非空验证
			RequestUtil.verifyParamsIsNotEmpty(new Object[] { param, ASRespCode.AS_PARAM_INVALID } // 验证参数
					);

			// 执行验证邮箱
			this.cardholderService.checkEmailCode(param);
			hre = new HttpRespEnvelope("恭喜您，验证通过，邮箱绑定成功！");
		} catch (HsException e) {
			if(160460 == e.getErrorCode()){
        		hre = new HttpRespEnvelope(e.getErrorCode(),e.getMessage());
			}else if(160137 == e.getErrorCode()){
        		hre = new HttpRespEnvelope("邮件已过期");
        	}else{
				hre = new HttpRespEnvelope(e);
			}
		}

		return hre;
	}
	
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
            bankList = this.bankCardService.findBankAccountList(custId, UserTypeEnum.NO_CARDER.getType());

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
            custName = StringUtils.trimToBlank(request.getParameter("zName"));		//客户名称
            
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
            this.bankCardService.addBankBind(bankAcctInfo, UserTypeEnum.NO_CARDER.getType());
            
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
            this.bankCardService.delBankBind(Long.parseLong(bankInfo.getBankId()), UserTypeEnum.NO_CARDER.getType());


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
    
    /**
     * 根据客户号查询网络信息
     * @param custId 客户号
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = { "/findNetworkInfoByCustId" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope findNetworkInfoByCustId(String custId, HttpServletRequest request) {


        // 返回的参数
        HttpRespEnvelope hre = null;
        AsNetworkInfo networkInfo = null;
        
        try
        {
            // Token验证
            super.checkSecureToken(request);
            
            //查询网络信息
            networkInfo = this.noCardholderService.findNetworkInfoByCustId(custId);

            // 封装数据返回界面
            hre = new HttpRespEnvelope(networkInfo);
        }
        catch (HsException e)
        {
            hre = new HttpRespEnvelope(e);
        }

        // 返回封装结果数据
        return hre;
    }

    /**
     * 修改网络信息
     * @param networkInfo 网络信息实体
     * @param custId 客户号
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = { "/updateNetworkInfo" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope updateNetworkInfo(@ModelAttribute AsNetworkInfo  networkInfo ,String custId, HttpServletRequest request) {


        // 返回的参数
        HttpRespEnvelope hre = null;
        
        try
        {
            // Token验证
            super.checkSecureToken(request);
            networkInfo.setName(StringUtils.trimToBlank(request.getParameter("zName")));		//姓名
            networkInfo.setNickname(StringUtils.trimToBlank(request.getParameter("nickName")));	//昵称
            networkInfo.setPerCustId(custId);
            
            //查询网络信息
            this.noCardholderService.updateNetworkInfo(networkInfo);

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
    
    /**
     * 修改收货地址
     * @param networkInfo 网络信息实体
     * @param custId 客户号
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = { "/updateReceiveAddr" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope updateReceiveAddr (@ModelAttribute AsReceiveAddr addr ,String custId, HttpServletRequest request) {


        // 返回的参数
        HttpRespEnvelope hre = null;
        
        try
        {
            // Token验证
            super.checkSecureToken(request);
            
            //查询网络信息
            this.noCardholderService.updateReceiveAddr(custId,addr);

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
    
    /**
     * 删除收货地址信息
     * @param networkInfo 网络信息实体
     * @param custId 客户号
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = { "/removeAddr" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope removeAddr(String custId, Long  addrId, HttpServletRequest request) {


        // 返回的参数
        HttpRespEnvelope hre = null;
        
        try
        {
            // Token验证
            super.checkSecureToken(request);
            
            //查询网络信息
            this.noCardholderService.deleteReceiveAddr(custId,addrId);

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
    
    /**
     * 设置默认收货地址
     * @param networkInfo 网络信息实体
     * @param custId 客户号
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = { "/setDefaultAddr" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope setDefaultAddr(String custId, Long  addrId, HttpServletRequest request) {


        // 返回的参数
        HttpRespEnvelope hre = null;
        
        try
        {
            // Token验证
            super.checkSecureToken(request);
            
            //设置默认收货地址
            this.noCardholderService.setDefaultAddr(custId,addrId);

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
    
    /**
     * 查询收货地址详情
     * @param networkInfo 网络信息实体
     * @param custId 客户号
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = { "/findReceiveAddrInfo" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope findReceiveAddrInfo(String custId, Long  addrId, HttpServletRequest request) {


        // 返回的参数
        HttpRespEnvelope hre = null;
        
        try
        {
            // Token验证
            super.checkSecureToken(request);
            
            
            //查询收货地址详情
            AsReceiveAddr asReceiveAddr = this.noCardholderService.findReceiveAddrInfo(custId,addrId);

            // 封装数据返回界面
            hre = new HttpRespEnvelope(asReceiveAddr);
        }
        catch (HsException e)
        {
            hre = new HttpRespEnvelope(e);
        }

        // 返回封装结果数据
        return hre;
    }
    
    /**
     * 查询收货地址
     * @param networkInfo 网络信息实体
     * @param custId 客户号
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = { "/findReceiveAddrByCustId" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope findReceiveAddrByCustId(String custId, HttpServletRequest request) {


        // 返回的参数
        HttpRespEnvelope hre = null;
        Province povince = null;
        City city = null;
        StringBuffer temp = new StringBuffer();
        try
        {
            // Token验证
            super.checkSecureToken(request);
            
            
            //查询收货地址
            List<AsReceiveAddr> list = this.noCardholderService.findReceiveAddrByCustId(custId);
            
            
            //遍历所有信息查询省份与城市
            for (AsReceiveAddr asReceiveAddr : list) {
            	temp.setLength(0);
            	povince = baseDataService.getProvinceById(asReceiveAddr.getCountryNo(),asReceiveAddr.getProvinceNo());
            	if(povince != null){
            		temp.append(povince.getProvinceName());
            	}
            	city = baseDataService.getCityById(asReceiveAddr.getCountryNo(),asReceiveAddr.getProvinceNo(),asReceiveAddr.getCityNo());
            	if(city != null){
            		temp.append(" - ").append(city.getCityName());
            	}
            	temp.append(" - ").append(asReceiveAddr.getAddress());
            	asReceiveAddr.setAddress(temp.toString());
			}
            // 封装数据返回界面
            hre = new HttpRespEnvelope(list);
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
		return null;
	}
}
