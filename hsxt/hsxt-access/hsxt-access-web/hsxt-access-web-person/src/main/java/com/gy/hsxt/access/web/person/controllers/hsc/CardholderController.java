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

import com.gy.hsi.redis.client.api.RedisUtil;
import com.gy.hsxt.access.web.common.bean.HttpRespEnvelope;
import com.gy.hsxt.access.web.common.constant.ASRespCode;
import com.gy.hsxt.access.web.common.controller.BaseController;
import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.access.web.common.utils.CommonUtils;
import com.gy.hsxt.access.web.common.utils.RequestUtil;
import com.gy.hsxt.access.web.common.utils.ValidateUtil;
import com.gy.hsxt.access.web.person.bean.PersonBase;
import com.gy.hsxt.access.web.person.services.common.IBaseDataService;
import com.gy.hsxt.access.web.person.services.consumer.ICardholderService;
import com.gy.hsxt.access.web.person.services.consumer.IReceiveAddrInfoService;
import com.gy.hsxt.access.web.person.services.hsc.ICardHolderAuthInfoService;
import com.gy.hsxt.bs.bean.entstatus.PerRealnameAuth;
import com.gy.hsxt.common.constant.CustType;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.uc.as.api.common.IUCAsPwdService;
import com.gy.hsxt.uc.as.api.enumerate.UserTypeEnum;
import com.gy.hsxt.uc.as.bean.common.AsConsumerLoginInfo;
import com.gy.hsxt.uc.as.bean.common.AsNetworkInfo;
import com.gy.hsxt.uc.as.bean.common.AsReceiveAddr;
import com.gy.hsxt.uc.as.bean.consumer.AsRealNameAuth;
import com.gy.hsxt.uc.as.bean.consumer.AsRealNameReg;
import com.gy.hsxt.uc.as.bean.enumerate.CerTypeEnum;
import com.gy.hsxt.uc.as.bean.enumerate.ChannelTypeEnum;
import com.gy.hsxt.uc.as.bean.result.AsCardHolderLoginResult;
import com.gy.hsxt.uc.as.bean.result.AsNoCardHolderLoginResult;

/***************************************************************************
 * <PRE>
 * Description 		: 持卡人操作类
 * 
 * Project Name   	: hsxt-access-web-person
 * 
 * Package Name     : com.gy.hsxt.access.web.person.hsc.controller.AccountController.java
 * 
 * File Name        :AccountController
 * 
 * Author           : LiZhi Peter
 * 
 * Create Date      : 2015-8-7 下午6:00:28
 * 
 * Update User      : LiZhi Peter
 * 
 * Update Date      : 2015-8-7 下午6:00:28
 * 
 * UpdateRemark 	: 说明本次修改内容
 * 
 * Version          : v0.0.1
 * 
 * </PRE>
 ***************************************************************************/
@SuppressWarnings("rawtypes")
@Controller
@RequestMapping("cardHolder")
public class CardholderController extends BaseController {

	@Resource
	private ICardholderService cardholderService;// 持卡人服务类

	@Resource
	private IBaseDataService baseDataService; // 崎岖平台配置服务类

	@Resource
	private IReceiveAddrInfoService receiveAddrInfoService;// 收货地址服务类

	@Resource
	private ICardHolderAuthInfoService cardHolderAuthInfoService;// 查询实名注册信息服务类

	@Resource
	private RedisUtil<String> changeRedisUtil;
	
	@Resource
    private IUCAsPwdService ucAsPwdService;
	/**
	 * 获取重要信息状态
	 * 
	 * @param custId
	 *            客户号
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/findImportantInfoChangeStatus" }, method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope findImportantInfoChangeStatus(String custId, HttpServletRequest request) {
		HttpRespEnvelope hre = null;
		try {
			// Token验证
			super.checkSecureToken(request);

			// 获取用户重要信息变更状态
			boolean status = this.cardholderService.findImportantInfoChangeStatus(custId);

			hre = new HttpRespEnvelope(status);
		} catch (HsException e) {
			e.printStackTrace();
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
	@RequestMapping(value = { "/findMobileByCustId" }, method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope findMobileByCustId(String custId, String pointNo,
			HttpServletRequest request) {
		// 变量声明
		HttpRespEnvelope hre = null;
		try {
			// Token验证
			super.checkSecureToken(request);
			// 获取用户手机号
			Map<String, String> moblieMap = cardholderService.findMobileByCustId(custId);

			hre = new HttpRespEnvelope(moblieMap);
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
	@RequestMapping(value = { "/mobileSendCode" }, method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope mobileSendCode(String custId,String mobile, HttpServletRequest request) {

		// 变量声明
		HttpRespEnvelope hre = null;

		try {
			// Token验证
			super.checkSecureToken(request);

			// 获取用户手机号
			cardholderService.mobileSendCode(custId,mobile,CustType.PERSON.getCode());

			hre = new HttpRespEnvelope();
		} catch (HsException e) {
			if(160461 == e.getErrorCode()){
        		hre = new HttpRespEnvelope(e.getErrorCode(), e.getMessage());
			}else{
				hre = new HttpRespEnvelope(e);
			}
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
	@RequestMapping(value = { "/cardHolderLogin" }, method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope cardHolderLogin(AsConsumerLoginInfo loginInfo,
			HttpServletRequest request) {

		// 变量声明
		HttpRespEnvelope hre = null;

		try {
			loginInfo.setChannelType(ChannelTypeEnum.WEB);
			loginInfo.setLoginIp("127.0.1.1");
			// 获取用户手机号
			AsCardHolderLoginResult cardHolderLoginResult = cardholderService
					.cardHolderLogin(loginInfo);
			
			hre = new HttpRespEnvelope(cardHolderLoginResult);
		} catch (HsException e) {
			hre = handleModifyPasswordException(e,true);
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
	@RequestMapping(value = { "/noCardHolderLogin" }, method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope noCardHolderLogin(AsConsumerLoginInfo loginInfo,
			HttpServletRequest request) {

		// 变量声明
		HttpRespEnvelope hre = null;

		try {
			loginInfo.setChannelType(ChannelTypeEnum.WEB);
			loginInfo.setLoginIp("127.0.1.1");
			// 获取用户手机号
			AsNoCardHolderLoginResult noCardHolderLoginResult = cardholderService.noCardHolderLogin(loginInfo);

			hre = new HttpRespEnvelope(noCardHolderLoginResult);
		} catch (HsException e) {
			hre = new HttpRespEnvelope(e);
		}

		return hre;
	}

	/**
	 * 添加用户绑定手机号码
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
	@RequestMapping(value = { "/add_bind_mobile" }, method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope addBindMobile(String custId, String pointNo, String mobile,
			String smsCode, HttpServletRequest request) {

		// 变量声明
		HttpRespEnvelope hre = null;

		try {
			// Token验证
			super.checkSecureToken(request);

			// 非空验证
			RequestUtil.verifyParamsIsNotEmpty(
					new Object[] { custId, RespCode.AS_CUSTID_INVALID.getCode(),
							RespCode.AS_CUSTID_INVALID.getDesc() }, // 客户号
					new Object[] { mobile, RespCode.PW_MOBILE_NULL.getCode(),
							RespCode.PW_MOBILE_NULL.getDesc() }, // 手机号码
					new Object[] { smsCode, RespCode.PW_SMS_CODE_INVALID.getCode(),
							RespCode.PW_SMS_CODE_INVALID.getDesc() } // 验证码
					);

			// 执行手机绑定手机号码 TODO 当前方法用户中心未提供
			this.cardholderService.addBindMobile(custId, mobile, smsCode,
					UserTypeEnum.CARDER.getType());
			// 执行成功返回信息自动包装
			hre = new HttpRespEnvelope();
		} catch (HsException e) {
			if(160461 == e.getErrorCode()){
        		hre = new HttpRespEnvelope(e.getErrorCode(),e.getMessage());
			}else{
				hre = new HttpRespEnvelope(e);
			}
		}
		return hre;
	}

	/**
	 * 修改绑定手机绑定
	 * 
	 * @param custId
	 *            客户号
	 * @param pointNo
	 *            互生卡号
	 * @param mobile
	 *            手机号码
	 * @param smsCode
	 *            短信验证码
	 * @param loginPwd
	 *            登录密码
	 * @param randomToken
	 *            随机报文
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/edit_bind_mobile" }, method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope editBindMobile(String custId, String pointNo, String mobile,
			String smsCode, String loginPwd, String randomToken, HttpServletRequest request) {

		// 变量声明
		HttpRespEnvelope hre = null;
		try {
			// Token验证
			super.checkSecureToken(request);

			// 非空验证
			RequestUtil.verifyParamsIsNotEmpty(
					new Object[] { pointNo, RespCode.AS_POINTNO_INVALID.getCode(),
							RespCode.AS_POINTNO_INVALID.getDesc() }, // 互生号
					new Object[] { mobile, RespCode.PW_MOBILE_NULL.getCode(),
							RespCode.PW_MOBILE_NULL.getDesc() }, // 手机号码
					new Object[] { loginPwd, RespCode.PW_LOGINPWD_INVALID.getCode(),
							RespCode.PW_LOGINPWD_INVALID.getDesc() },// 登录密码
					new Object[] { randomToken, RespCode.AS_RANDOM_TOKEN_INVALID.getCode(),
							RespCode.AS_RANDOM_TOKEN_INVALID.getDesc() }, // 随机报文
					new Object[] { smsCode, RespCode.PW_SMS_CODE_INVALID.getCode(),
							RespCode.PW_SMS_CODE_INVALID.getDesc() } // 验证码
					);


			// 验证登录密码是否正确  1：非持卡人 2：持卡人 3：操作员
			ucAsPwdService.checkLoginPwd(custId, loginPwd, "2", randomToken);
			
			// 执行手机绑定手机号码 TODO 当前方法用户中心未提供
			this.cardholderService.addBindMobile(custId, mobile, smsCode,
					UserTypeEnum.CARDER.getType());
			// 执行成功返回信息自动包装
			hre = new HttpRespEnvelope();
		} catch (HsException e) {
			if(160461 == e.getErrorCode()){
        		hre = new HttpRespEnvelope(e.getErrorCode(),e.getMessage());
			}else{
				hre = new HttpRespEnvelope(e);
			}
		}
		return hre;
	}

	/**
	 * 获取持卡人已绑定邮箱
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
			Map<String, String> retMap = cardholderService.findEamilByCustId(custId);

			hre = new HttpRespEnvelope(retMap);
		} catch (HsException e) {
			e.printStackTrace();
			hre = new HttpRespEnvelope(e);
		}

		return hre;
	}

	/**
	 * 添加用户绑定手机号码
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

			// 验证两次邮箱是否一致
			RequestUtil.verifyEquals(email, confirmEmail, RespCode.PW_INCONSISTENT_EMAIL_INVALID);

			// 邮箱规则验证
			RequestUtil.verifyEamil(email, RespCode.PW_EMAIL_FORM_INVALID);

			// 执行手机绑定手机号码 TODO 当前方法用户中心未提供
			this.cardholderService.addBindEmail(personBase, email, UserTypeEnum.CARDER.getType(),CustType.PERSON.getCode());
			// 执行成功返回信息自动包装
			hre = new HttpRespEnvelope();
		} catch (HsException e) {
			if(160460 == e.getErrorCode()){
        		hre = new HttpRespEnvelope(e.getErrorCode(),e.getMessage());
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
	public HttpRespEnvelope mailModify(PersonBase personBase, String loginPwd, String email,
			String confirmEmail, HttpServletRequest request) {
		HttpRespEnvelope hre = null;
		boolean isLogin = false;
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
			this.cardholderService.mailModify(personBase, loginPwd, email,
					UserTypeEnum.CARDER.getType());
			hre = new HttpRespEnvelope();
		} catch (HsException e) {
			if(160460 == e.getErrorCode()){
        		hre = new HttpRespEnvelope(e.getErrorCode(),e.getMessage());
			}else{
				hre = handleModifyPasswordException(e,isLogin);
			}
		}
		return hre;
	}

	 private HttpRespEnvelope handleModifyPasswordException(HsException e,boolean isLogin){
	    	HttpRespEnvelope hre = new HttpRespEnvelope();
	    	int errorCode = e.getErrorCode();
//	    	String errorMsg = e.getMessage();
//	    	String funName = "handleModifyPasswordException";
//	    	StringBuffer msg = new StringBuffer();
//	    	msg.append("处理修改登录密码Exception时报错,传入参数信息  e["+JSON.toJSONString(e)+"] "+NEWLINE);
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
	    		String alertMsg = isLogin == true ? e.getMessage() : "账户被锁定";
	    		hre = new HttpRespEnvelope(e.getErrorCode(),alertMsg);
	    	}else{
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
	 * 初始化实名注册信息
	 * 
	 * @param custId
	 *            客户号
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/init_registration" }, method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope queryConsumerInfo(String custId, HttpServletRequest request) {

		// 变量声明
		HttpRespEnvelope hre = null;
		AsRealNameReg asRealNameReg = null;
		try {
			// Token验证
			super.checkSecureToken(request);

			// 执行查询
			asRealNameReg = cardHolderAuthInfoService.searchRealNameRegInfo(custId);
			
			String cardNo  = getHiddenCerNo(asRealNameReg.getCertype(), asRealNameReg.getCerNo());
			
			asRealNameReg.setCerNo(cardNo);
			//asRealNameReg.setEntName(CommonUtils.strMask(asRealNameReg.getEntName(), 2, 2, "*"));
			if(ValidateUtil.validateFX(asRealNameReg.getRealName())){
			    asRealNameReg.setRealName(CommonUtils.strMask(asRealNameReg.getRealName(), 2, 0, "*"));
			}else{
			    asRealNameReg.setRealName(CommonUtils.strMask(asRealNameReg.getRealName(), 1, 0, "*"));
			}

			// 保存数据返回界面
			hre = new HttpRespEnvelope(asRealNameReg);
		} catch (HsException e) {
			hre = new HttpRespEnvelope(e);
		}

		return hre;
	}
	
	private String  getHiddenCerNo(String CerType,String cerNO){
		if(cerNO==null){
			return null;
		}
		if("1".equals(CerType)){	    
			
			int iLen=cerNO.length();
			if(iLen>=18){
				//大陆身份证证件号码的年月日用*替换
				cerNO = CommonUtils.strMask(cerNO, 6, 4, "*");
			}else{
				//港澳台：首位与最后2位之间的字符用*号替换
				cerNO = CommonUtils.strMask(cerNO, 1, 2, "*");
			}
		}else if("2".equals(CerType)){
		    //护照：432**22
			//前三位与最后2位之间用*号替换
			cerNO = CommonUtils.strMask(cerNO, 3, 2, "*");
		}else if("3".equals(CerType)){
		    //营业执照 : 123456******123
			//首位与最后2位之间的字符用*号替换
			cerNO = CommonUtils.strMask(cerNO, 1, 2, "*");
		}
		return cerNO;
	}

	/**
	 * 实名注册
	 * 
	 * @param custId
	 *            客户号
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/registration" }, method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope registration(String custId,
			@ModelAttribute AsRealNameReg asRealNameReg, HttpServletRequest request) {

		// 变量声明
		HttpRespEnvelope hre = null;

		try {
			// Token验证
			super.checkSecureToken(request);

			// 非空验证
			RequestUtil.verifyParamsIsNotEmpty(new Object[] { asRealNameReg.getCertype(),
					RespCode.PW_CERTYPE_NULL.getCode(), RespCode.PW_CERTYPE_NULL.getDesc() }, // 证件类型(1：身份证
																								// 2：护照:3：营业执照)
					new Object[] { asRealNameReg.getCerNo(), RespCode.PW_CERNO_NULL.getCode(),
							RespCode.PW_CERNO_NULL.getDesc() } // 证件号码
					);
			// 营业执照
			if (CerTypeEnum.BUSILICENCE.getType() == Integer.parseInt(asRealNameReg.getCertype())) {
				RequestUtil.verifyParamsIsNotEmpty(
						new Object[] { asRealNameReg.getEntName(),
								RespCode.PW_ENTERPRISE_NULL.getCode(),
								RespCode.PW_ENTERPRISE_NULL.getDesc() }, // 企业名称
						new Object[] { asRealNameReg.getEntRegAddr(),
								RespCode.PW_ENTERPRISE_ADDRESS_NULL.getCode(),
								RespCode.PW_ENTERPRISE_ADDRESS_NULL.getDesc() } // 注册地址
						);
				// 长度规则验证
				RequestUtil.verifyParamsLength(
						new Object[] { asRealNameReg.getEntName(), 0, 64,
								RespCode.PW_ENTERPRISE_INVALID.getCode(),
								RespCode.PW_ENTERPRISE_INVALID.getDesc() }, // 企业名称
						new Object[] { asRealNameReg.getEntRegAddr(), 0, 128,
								RespCode.PW_ENTERPRISE_INVALID.getCode(),
								RespCode.PW_ENTERPRISE_INVALID.getDesc() } // 企业地址
						);

			} else // 身份证、护照
			{
				RequestUtil.verifyParamsIsNotEmpty(new Object[] { asRealNameReg.getRealName(),
						RespCode.PW_REALNAME_NULL.getCode(), RespCode.PW_REALNAME_NULL.getDesc() }, // 姓名
						new Object[] { asRealNameReg.getCountryCode(),
								RespCode.PW_COUNTRY_CODE_NULL.getCode(),
								RespCode.PW_COUNTRY_CODE_NULL.getDesc() } // 国籍不能为空
						);

				// 身份真号码则进行规则验证
				if (asRealNameReg.getCertype().equals("1")) {
					RequestUtil.verifyCard(asRealNameReg.getCerNo(), RespCode.PW_IDCARD_NO_INVALID);
				}
			}

			// 执行使命注册
			cardHolderAuthInfoService.saveRealNameReg(asRealNameReg);

			hre = new HttpRespEnvelope();
		} catch (HsException e) {
			hre = new HttpRespEnvelope(e);
		}

		return hre;
	}

	/**
	 * 初始化实名认证信息
	 * 
	 * @param custId
	 *            客户号
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/init_authentication" }, method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope initAuthentication(String custId, HttpServletRequest request) {

		// 变量声明
		HttpRespEnvelope hre = null;
		String authStatus = null;
		Map<String, Object> map = null;
		try {
			// Token验证
			super.checkSecureToken(request);

			// 返回参数初始化
			map = new HashMap<String, Object>();

			// 执行查询 (1：未实名注册、2：已实名注册（有名字和身份证）、3:已实名认证)
			authStatus = cardHolderAuthInfoService.findAuthStatusByCustId(custId);

			// 保存实名状态
			map.put("authStatus", authStatus);

			// 已实名认证查询用户中心数据
			if (authStatus.equals("3")) {
				AsRealNameAuth realNameAuth = this.cardHolderAuthInfoService
						.searchRealNameAuthInfo(custId);
				//realNameAuth.setEntName(CommonUtils.strMask(realNameAuth.getEntName(), 2, 2, "*"));
				if(ValidateUtil.validateFX(realNameAuth.getUserName())){
				    realNameAuth.setUserName(CommonUtils.strMask(realNameAuth.getUserName(), 2, 0, "*"));
	            }else{
	                realNameAuth.setUserName(CommonUtils.strMask(realNameAuth.getUserName(), 1, 0, "*"));
	            }
				String creNO  = getHiddenCerNo(realNameAuth.getCerType(), realNameAuth.getCerNo());
				realNameAuth.setCerNo(creNO);
				map.put("realNameAuth", realNameAuth);
			} else if (authStatus.equals("2")) // 已经实名注册 查询bs审批注册信息
			{
				// 执行查询
				AsRealNameReg realNameReg = cardHolderAuthInfoService.searchRealNameRegInfo(custId);// 查询实名注册信息
				if (realNameReg != null) {
					
					String creNO  = getHiddenCerNo(realNameReg.getCertype(), realNameReg.getCerNo());
					realNameReg.setCerNo(creNO);
					//检查名字复姓
					if(ValidateUtil.validateFX(realNameReg.getRealName())){
					    realNameReg.setRealName(CommonUtils.strMask(realNameReg.getRealName(), 2, 0, "*"));
		            }else{
		                realNameReg.setRealName(CommonUtils.strMask(realNameReg.getRealName(), 1, 0, "*"));
		            }
				}
				PerRealnameAuth realnameAuth = this.cardHolderAuthInfoService.queryPerRealnameAuthByCustId(custId);
				if (realnameAuth != null) {
					realnameAuth.setEntName(CommonUtils.strMask(realnameAuth.getEntName(), 2, 2,"*"));
					
					String creNO  = getHiddenCerNo(realnameAuth.getCertype()+"", realnameAuth.getCredentialsNo());
					realnameAuth.setCredentialsNo(creNO);
					
					//检查名字复姓
                    if(ValidateUtil.validateFX(realnameAuth.getName())){
                        realnameAuth.setName(CommonUtils.strMask(realnameAuth.getName(), 2, 0, "*"));
                    }else{
                        realnameAuth.setName(CommonUtils.strMask(realnameAuth.getName(), 1, 0, "*"));
                    }
				}
				map.put("realNameReg", realNameReg); // 实名注册信息
				map.put("realNameAuth", realnameAuth); // 实名认证信息
			}

			// 保存数据返回界面
			hre = new HttpRespEnvelope(map);
		} catch (HsException e) {
			hre = new HttpRespEnvelope(e);
		}

		return hre;
	}

	/**
	 * 实名认证信息
	 * 
	 * @param custId
	 *            客户号
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/authentication" }, method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope authentication(String custId, String checkcode,
			PerRealnameAuth perRealnameAuth, HttpServletRequest request) {

		// 变量声明
		HttpRespEnvelope hre = null;
		String authStatus = null;
		try {
			String perResNo = super.verifyPointNo(request);// 获取消费者互生号
			String perCustId = request.getParameter("custId");// 获取消费者客户号
			String optName = request.getParameter("custName");// 获取操作员名字
			String mobile = request.getParameter("mobile");// 获取消费者的手机号码
			// 执行查询 (1：未实名注册、2：已实名注册（有名字和身份证）、3:已实名认证)
			authStatus = cardHolderAuthInfoService.findAuthStatusByCustId(custId);
			// 未实名注册
			if (authStatus.equals("1")) {
				// 直接返回未实名注册提示信息
				hre = new HttpRespEnvelope(RespCode.PW_NOT_RELNAME_AUTH_INVALID.getCode(),
						RespCode.PW_NOT_RELNAME_AUTH_INVALID.getDesc());
				return hre;
			}

			// 认证附言长度验证
			RequestUtil.verifyParamsLength(new Object[] { perRealnameAuth.getPostScript(), 0, 300,
					RespCode.PW_POST_SCRIPT_NOT_NULL.getCode(),
					RespCode.PW_POST_SCRIPT_NOT_NULL.getDesc() } // 认证附言
					);
			
			// lizhi 前台传递过来的值部分存在掩码，需要重新查询一次 20160225
			AsRealNameReg realNameReg = cardHolderAuthInfoService.searchRealNameRegInfo(perCustId);// 查询实名注册信息
			if (realNameReg != null) {
				perRealnameAuth.setCredentialsNo(realNameReg.getCerNo());
				perRealnameAuth.setEntName(realNameReg.getEntName());
				perRealnameAuth.setName(realNameReg.getRealName());
			}
			// 营业执照
			if (CerTypeEnum.BUSILICENCE.getType() == perRealnameAuth.getCertype()) {
				// 企业注册信息的非空验证
				RequestUtil.verifyParamsIsNotEmpty(
						new Object[] { perRealnameAuth.getEntBuildDate(),
								RespCode.PW_BIZREG_ESTADATE_INVALID.getCode(),
								RespCode.PW_BIZREG_ESTADATE_INVALID.getDesc() }, // 成立日期
						new Object[] { perRealnameAuth.getCerpica(),
								RespCode.PW_SMRZSP_CERPICA_NOT_NULL.getCode(),
								RespCode.PW_SMRZSP_CERPICA_NOT_NULL.getDesc() }, // 证件正面照
						new Object[] { perRealnameAuth.getCerpich(),
								RespCode.PW_SMRZSP_CERPICH_NOT_NULL.getCode(),
								RespCode.PW_SMRZSP_CERPICH_NOT_NULL.getDesc() }, // 手持证件照
						new Object[] { perRealnameAuth.getEntType(),
								RespCode.PW_COMPANY_TYPE_INVALID.getCode(),
								RespCode.PW_COMPANY_TYPE_INVALID.getDesc() } // 公司类型
						);
				// 长度规则验证
				RequestUtil.verifyParamsLength(new Object[] { perRealnameAuth.getEntType(), 0, 20,
						RespCode.PW_ENTTYPE_LENGTH_INVALID },// 公司类型
						new Object[] { perRealnameAuth.getEntName(), 0, 64,
								RespCode.PW_ENTERPRISE_INVALID.getCode(),
								RespCode.PW_ENTERPRISE_INVALID.getDesc() }, // 企业名称
						new Object[] { perRealnameAuth.getEntRegAddr(), 0, 128,
								RespCode.PW_ENTERPRISE_INVALID.getCode(),
								RespCode.PW_ENTERPRISE_INVALID.getDesc() } // 企业地址
						);

			} else if (CerTypeEnum.IDCARD.getType() == perRealnameAuth.getCertype()) // 身份证
			{
				RequestUtil.verifyParamsIsNotEmpty(
								
								new Object[] { perRealnameAuth.getSex(),
										RespCode.PW_SMRZSP_SEX_NOT_NULL.getCode(),
										RespCode.PW_SMRZSP_SEX_NOT_NULL.getDesc() }, // 性别
								new Object[] { perRealnameAuth.getBirthAddr(),
										RespCode.PW_SMRZSP_BIRTHADDR_NOT_NULL.getCode(),
										RespCode.PW_SMRZSP_BIRTHADDR_NOT_NULL.getDesc() }, // 户籍地址
								new Object[] { perRealnameAuth.getLicenceIssuing(),
										RespCode.PW_SMRZSP_LICENCEISSUING_NOT_NULL.getCode(),
										RespCode.PW_SMRZSP_LICENCEISSUING_NOT_NULL.getDesc() }, // 发证机关
								new Object[] { perRealnameAuth.getCerpica(),
										RespCode.PW_SMRZSP_CERPICA_NOT_NULL.getCode(),
										RespCode.PW_SMRZSP_CERPICA_NOT_NULL.getDesc() }, // 证件正面照
								new Object[] { perRealnameAuth.getCerpicb(),
										RespCode.PW_SMRZSP_CERPICB_NOT_NULL.getCode(),
										RespCode.PW_SMRZSP_CERPICB_NOT_NULL.getDesc() }, // 证件背面照
								new Object[] { perRealnameAuth.getCerpich(),
										RespCode.PW_SMRZSP_CERPICH_NOT_NULL.getCode(),
										RespCode.PW_SMRZSP_CERPICH_NOT_NULL.getDesc() } // 手持证件照
								

						);
				// 长度规则验证
				RequestUtil.verifyParamsLength(new Object[] { perRealnameAuth.getBirthAddr(), 2,
						128, RespCode.PW_BIRTHADDR_LENGTH_INVALID },// 户籍地址
						new Object[] { perRealnameAuth.getProfession(), 0, 20,
								RespCode.PW_PROFESSION_LENGTH_INVALID },// 职业
						new Object[] { perRealnameAuth.getLicenceIssuing(), 2, 20,
								RespCode.PW_LICENCE_ISSUING_LENGTH_INVALID }// 发证机关
						);
				
			} else if (CerTypeEnum.PASSPORT.getType() == perRealnameAuth.getCertype()) // 护照
			{
				RequestUtil.verifyParamsIsNotEmpty(
								new Object[] { perRealnameAuth.getSex(),
										RespCode.PW_SMRZSP_SEX_NOT_NULL }, // 性别
								new Object[] { perRealnameAuth.getIssuePlace(),
										RespCode.PW_SMRZSP_LOCATION_NOT_NULL }, // 签发地点
								new Object[] { perRealnameAuth.getLicenceIssuing(),
										RespCode.PW_SMRZSP_LICENCEISSUING_NOT_NULL }, // 发证机关
								new Object[] { perRealnameAuth.getBirthAddr(),
										RespCode.PW_BIRTH_PLACE_NOT_NULL }, // 出生地点
								new Object[] { perRealnameAuth.getCerpica(),
										RespCode.PW_SMRZSP_CERPICA_NOT_NULL }, // 证件正面照
								new Object[] { perRealnameAuth.getCerpich(),
										RespCode.PW_SMRZSP_CERPICH_NOT_NULL } // 手持证件照
								
						);
				// 长度规则验证
				RequestUtil.verifyParamsLength(new Object[] { perRealnameAuth.getBirthAddr(), 2,
						128, RespCode.PW_PASSPORT_BIRTHADDR_LENGTH_INVALID },// 户籍地址
						new Object[] { perRealnameAuth.getIssuePlace(), 2, 40,
								RespCode.PW_PASSPORT_PROFESSION_LENGTH_INVALID },// 签发地点
						new Object[] { perRealnameAuth.getLicenceIssuing(), 2, 20,
								RespCode.PW_PASSPORT_LICENCE_ISSUING_LENGTH_INVALID }// 签发机关
						);
			}

			perRealnameAuth.setOptName(perResNo + "(" + optName + ")");
			perRealnameAuth.setOptEntName(optName);
			perRealnameAuth.setOptCustId(perCustId);
			perRealnameAuth.setPerCustId(perCustId);
			perRealnameAuth.setPerResNo(perResNo);
			perRealnameAuth.setMobile(mobile);
			
			this.cardHolderAuthInfoService.createPerRealnameAuth(perRealnameAuth);
			
			// 保存数据返回界面
			hre = new HttpRespEnvelope();
		} catch (HsException e) {
			hre = new HttpRespEnvelope(e);
		}

		return hre;
	}


	/**
	 * 获取持卡人实名注册信息
	 * 
	 * @param custId
	 *            客户号
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/findCardHolderByCustId" }, method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope findCardHolderByCustId(String custId, String pointNo,
			HttpServletRequest request) {

		// 变量声明
		AsRealNameReg asRealNameReg = null;// 持卡人信息
		HttpRespEnvelope hre = null;

		try {
			// Token验证
			super.checkSecureToken(request);

			// 获取实名认证消息
			asRealNameReg = cardholderService.findRealNameRegInfo(custId);

			hre = new HttpRespEnvelope(asRealNameReg);
		} catch (HsException e) {
			e.printStackTrace();
			hre = new HttpRespEnvelope(e);
		}

		return hre;
	}

	/**
	 * 获取查询收货地址接口
	 * 
	 * @param custId
	 *            客户号
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/findReceiveAddrsByCustId" }, method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope findReceiveAddrsByCustId(String custId, String pointNo,
			HttpServletRequest request) {

		// 变量声明
		List<AsReceiveAddr> receiveAddrList = null;// 持卡人信息
		HttpRespEnvelope hre = null;

		try {
			// Token验证
			super.checkSecureToken(request);

			// 获取用户手机号
			receiveAddrList = this.receiveAddrInfoService.findReceiveAddrsByCustId(custId,
					UserTypeEnum.CARDER.getType());

			hre = new HttpRespEnvelope(receiveAddrList);
		} catch (HsException e) {
			e.printStackTrace();
			hre = new HttpRespEnvelope(e);
		}

		return hre;
	}

	/**
	 * 获取查询收货地址接口
	 * 
	 * @param custId
	 *            客户号
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/findNetworkInfo" }, method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope findNetworkInfo(String custId, String pointNo,
			HttpServletRequest request) {

		// 变量声明
		AsNetworkInfo networkInfo = null;// 持卡人信息
		HttpRespEnvelope hre = null;

		try {
			// Token验证
			super.checkSecureToken(request);

			// 获取用户手机号
			networkInfo = this.cardholderService.findNetworkInfo(custId);

			hre = new HttpRespEnvelope(networkInfo);
		} catch (HsException e) {
			e.printStackTrace();
			hre = new HttpRespEnvelope(e);
		}

		return hre;
	}

	/**
	 * 获取查询实名认证信息
	 * 
	 * @param custId
	 *            客户号
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/findRealNameAuthInfo" }, method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope findRealNameAuthInfo(String custId, String pointNo,
			HttpServletRequest request) {

		// 变量声明
		AsRealNameAuth asRealNameAuth = null;// 持卡人信息
		HttpRespEnvelope hre = null;

		try {
			// Token验证
			super.checkSecureToken(request);

			// 获取用户手机号
			asRealNameAuth = cardholderService.findRealNameAuthInfo(custId);

			hre = new HttpRespEnvelope(asRealNameAuth);
		} catch (HsException e) {
			e.printStackTrace();
			hre = new HttpRespEnvelope(e);
		}

		return hre;
	}

	@Override
	protected IBaseService getEntityService() {
		return cardholderService;
	}
}
