/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.uc.common.service;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caucho.hessian.client.HessianConnectionException;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.StringUtils;
import com.gy.hsxt.uc.as.api.common.IUCAsPwdQuestionService;
import com.gy.hsxt.uc.as.api.consumer.IUCAsCardHolderService;
import com.gy.hsxt.uc.as.api.ent.IUCAsEntService;
import com.gy.hsxt.uc.as.api.enumerate.ErrorCodeEnum;
import com.gy.hsxt.uc.as.api.enumerate.UserTypeEnum;
import com.gy.hsxt.uc.as.bean.common.AsConsumerLoginInfo;
import com.gy.hsxt.uc.as.bean.common.AsPwdQuestion;
import com.gy.hsxt.uc.as.bean.operator.AsOperator;
import com.gy.hsxt.uc.cache.service.CommonCacheService;
import com.gy.hsxt.uc.common.SysConfig;
import com.gy.hsxt.uc.common.bean.PwdQuestion;
import com.gy.hsxt.uc.common.mapper.PwdQuestionMapper;
import com.gy.hsxt.uc.consumer.bean.CardHolder;
import com.gy.hsxt.uc.consumer.bean.NoCardHolder;
import com.gy.hsxt.uc.operator.bean.Operator;
import com.gy.hsxt.uc.operator.service.OperatorService;
import com.gy.hsxt.uc.util.CSPRNG;
import com.gy.hsxt.uc.util.CustIdGenerator;

/**
 * 密保问题操作实现类
 * 
 * @Package: com.gy.hsxt.uc.common.service
 * @ClassName: UCAsPwdQuestionService
 * @Description: 密保问题信息管理
 * 
 * @author: lixuan
 * @date: 2015-11-23 下午5:47:52
 * @version V1.0
 */
@Service
public class UCAsPwdQuestionService implements IUCAsPwdQuestionService {
	private static final String MOUDLENAME = "com.gy.hsxt.uc.common.service.UCAsPwdQuestionService";
	@Autowired
	PwdQuestionMapper pwdQuestionMapper;
	@Autowired
	IUCAsEntService entService;
	@Autowired
	IUCAsCardHolderService cardHolderService;
	@Autowired
	SysConfig config;
	@Autowired
	UCAsLoginService loginService;
	@Autowired
	OperatorService operatorService;
	@Autowired
	CommonCacheService commonCacheService;
	@Autowired
	CommonService commonService;
	/**
	 * 获取默认密保问题
	 * 
	 * @return
	 * @throws HsException
	 * @see com.gy.hsxt.uc.as.api.common.IUCAsPwdQuestionService#listDefaultPwdQuestions()
	 */
	@Override
	public List<AsPwdQuestion> listDefaultPwdQuestions() throws HsException {
		List<AsPwdQuestion> list = new ArrayList<AsPwdQuestion>();
		AsPwdQuestion pwdQuestion1 = new AsPwdQuestion();
		AsPwdQuestion pwdQuestion3 = new AsPwdQuestion();
		AsPwdQuestion pwdQuestion2 = new AsPwdQuestion();
		String pwdQuestionFirst = SysConfig.getPwdQuestionFirst();
		String pwdQuestionSecond = SysConfig.getPwdQuestionSecond();
		String pwdQuestionThird = SysConfig.getPwdQuestionThird();
		try {
			pwdQuestionFirst = new String(
					pwdQuestionFirst.getBytes("ISO-8859-1"), "utf-8");
		} catch (UnsupportedEncodingException e) {
			throw new HsException(
					ErrorCodeEnum.PWDQUESTION_CONVERT_ERROR.getValue(),
					e.getMessage());
		}
		try {
			pwdQuestionSecond = new String(
					pwdQuestionSecond.getBytes("ISO-8859-1"), "utf-8");
		} catch (UnsupportedEncodingException e) {
			throw new HsException(
					ErrorCodeEnum.PWDQUESTION_CONVERT_ERROR.getValue(),
					e.getMessage());
		}
		try {
			pwdQuestionThird = new String(
					pwdQuestionThird.getBytes("ISO-8859-1"), "utf-8");
		} catch (UnsupportedEncodingException e) {
			throw new HsException(
					ErrorCodeEnum.PWDQUESTION_CONVERT_ERROR.getValue(),
					e.getMessage());
		}
		pwdQuestion1.setQuestion(pwdQuestionFirst);
		pwdQuestion2.setQuestion(pwdQuestionSecond);
		pwdQuestion3.setQuestion(pwdQuestionThird);
		list.add(pwdQuestion1);
		list.add(pwdQuestion2);
		list.add(pwdQuestion3);
		return list;
	}

	/**
	 * 更新密保问题
	 * 
	 * @param custId
	 *            客户号
	 * @param question
	 *            问题ID和答案必填
	 * @throws HsException
	 */
	@Override
	public void updatePwdQuestion(String custId, AsPwdQuestion question)
			throws HsException {
		if (null == question) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"AsPwdQuestion is null");
		}
		if (StringUtils.isBlank(question.getAnswer())) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"密保答案为空");
		}
		if (StringUtils.isBlank(question.getQuestion())) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"密保问题为空");
		}
		List<PwdQuestion> list = null;
		try {
			list = pwdQuestionMapper.selectByCustId(custId.trim());
		} catch (HsException e) {
			throw new HsException(
					ErrorCodeEnum.PWD_QUESTION_QUERY_ERROR.getValue(),
					e.getMessage());
		}
		if (null == list || 0 == list.size()) {
			PwdQuestion pwdQuestion = new PwdQuestion();
			pwdQuestion.setPwdRestQuestion(question.getQuestion().trim());
			pwdQuestion.setPwdRestPwdAnswer(question.getAnswer().trim());
			pwdQuestion.setCustId(custId.trim());
			try {
				pwdQuestionMapper.insertSelective(pwdQuestion);
			} catch (Exception e) {
				throw new HsException(
						ErrorCodeEnum.PWD_QUESTION_SAVE_ERROR.getValue(),
						ErrorCodeEnum.PWD_QUESTION_SAVE_ERROR.getDesc() + ","
								+ e.getMessage());
			}
			return;
		}
		PwdQuestion pq = list.get(0);
		PwdQuestion pwd = new PwdQuestion();
		pwd.setPwdRestPwdAnswer(question.getAnswer().trim());
		pwd.setPwdRestQuestion(question.getQuestion().trim());
		pwd.setQuestionId(pq.getQuestionId());
		try {
			pwdQuestionMapper.updateByPrimaryKeySelective(pwd);
		} catch (Exception e) {
			throw new HsException(
					ErrorCodeEnum.PWD_QUESTION_UPDATE_ERROR.getValue(),
					ErrorCodeEnum.PWD_QUESTION_UPDATE_ERROR.getDesc() + ","
							+ e.getMessage());
		}
	}

	/**
	 * 
	 * 密保验证
	 * 
	 * @param custId
	 *            客户号 包含持卡人和操作员客户号
	 * @param userType
	 *            用户类型 2 持卡人 4 企业
	 * @param question
	 *            问题ID和答案为必填
	 * @return
	 * @throws HsException
	 */
	@Override
	public String checkPwdQuestion(String custId, String userType,AsPwdQuestion question) throws HsException {
		if (StringUtils.isBlank(userType)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"用户类型为空");
		}
		if (StringUtils.isBlank(custId)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"客户号不能为空");
		}
		if (null == question) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"密保问题对象为空");
		}
		if (StringUtils.isBlank(question.getAnswer())) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"密保答案为空");
		}
		if (StringUtils.isBlank(question.getQuestion())) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"密保问题为空");
		}
	//	commonService.isCyptLockAccount(custId, userType);
		PwdQuestion pq = getQuestionAndAnswer(custId, userType);// 获取正确的密保问题和答案
		String pwdRestQuestion = StringUtils.nullToEmpty(pq
				.getPwdRestQuestion());
		String pwdRestPwdAnswer = StringUtils.nullToEmpty(pq
				.getPwdRestPwdAnswer());
		if (!(pwdRestPwdAnswer.equals(question.getAnswer()) && (pwdRestQuestion
				.equals(question.getQuestion())))) {// 密保信息不正确，记录失败次数。
			throw new HsException(ErrorCodeEnum.PWD_INFO_IS_WRONG.getValue(),ErrorCodeEnum.PWD_INFO_IS_WRONG.getDesc());
	//		processLockAccount(custId, userType);
		}
		String random = CSPRNG.generateRandom(SysConfig.getCsprLen());
		commonCacheService.addCryptCache(custId, random);
		return random;

	}

	
	
	

	/**
	 * 根据互生号查询客户号
	 * 
	 * @param resNo
	 *            互生号
	 * @param userType
	 *            用户类型 2持卡人 3企业
	 * @return
	 */
	private PwdQuestion getQuestionAndAnswer(String custId, String userType)
			throws HsException {
		String cid = "";
		if (userType.equals(UserTypeEnum.ENT.getType())) {
			AsOperator operator = operatorService.searchOperByCustId(custId);
			cid = operator.getEntCustId();
		} else if (userType.equals(UserTypeEnum.CARDER.getType()) || userType.equals(UserTypeEnum.NO_CARDER.getType())) {
			cid = custId;
		} else {
			throw new HsException(ErrorCodeEnum.USERTYPE_IS_ILLEGAL.getValue(),
					ErrorCodeEnum.USERTYPE_IS_ILLEGAL.getDesc());
		}
		List<PwdQuestion> list = null;
		try {
			list = pwdQuestionMapper.selectByCustId(cid);
		} catch (HessianConnectionException e) {
			throw new HsException(
					ErrorCodeEnum.PWD_QUESTION_QUERY_ERROR.getValue(),
					ErrorCodeEnum.PWD_QUESTION_QUERY_ERROR.getDesc() + ","
							+ e.getMessage());
		}
		if (null == list || 0 == list.size()) {
			throw new HsException(
					ErrorCodeEnum.PWD_QUESTION_IS_EMPTY.getValue(),
					ErrorCodeEnum.PWD_QUESTION_IS_EMPTY.getDesc());
		}
		return list.get(0);
	}

	
	public boolean isSetCarderPwdQuestion(String perResNo) throws HsException{
		String perCustId = commonCacheService.findCustIdByResNo(perResNo);
		List<PwdQuestion> list = null;
		String methodName = "isSetCarderPwdQuestion";
		StringBuffer msg = new StringBuffer();
		msg.append("判断非持卡人是否已设置密保问题异常，输入参数 perResNo["+perResNo+"]");
		try {
			list = pwdQuestionMapper.selectByCustId(perCustId);
		} catch (HsException e) {
			commonService.handleHsException(MOUDLENAME, methodName, msg.toString(), ErrorCodeEnum.PWD_QUESTION_QUERY_ERROR);
		}
		if (null == list || 0 == list.size()) {
			return false;
		}
		return true;
	}
	
	public boolean isSetNoCarderPwdQuestion(String mobile) throws HsException{
		String perCustId = commonCacheService.findCustIdByMobile(mobile);
		List<PwdQuestion> list = null;
		String methodName = "isSetNoCarderPwdQuestion";
		StringBuffer msg = new StringBuffer();
		msg.append("判断非持卡人是否已设置密保问题异常，输入参数 mobile["+mobile+"]");
		try {
			list = pwdQuestionMapper.selectByCustId(perCustId);
		} catch (HsException e) {
			commonService.handleHsException(MOUDLENAME, methodName, msg.toString(), ErrorCodeEnum.PWD_QUESTION_QUERY_ERROR);
		}
		if (null == list || 0 == list.size()) {
			return false;
		}
		return true;
	}
	
	public boolean isSetEntPwdQuestion(String entResNo){
		String entCustId = commonCacheService.findEntCustIdByEntResNo(entResNo);
		List<PwdQuestion> list = null;
		String methodName = "isSetEntPwdQuestion";
		StringBuffer msg = new StringBuffer();
		msg.append("判断企业是否已设置密保问题异常，输入参数 entResNo["+entResNo+"]");
		try {
			list = pwdQuestionMapper.selectByCustId(entCustId);
		} catch (HsException e) {
			commonService.handleHsException(MOUDLENAME, methodName, msg.toString(), ErrorCodeEnum.PWD_QUESTION_QUERY_ERROR);
		}
		if (null == list || 0 == list.size()) {
			return false;
		}
		return true;
	}
	
	/**
	 * 处理锁定帐户方法
	 * 
	 * @param resNo
	 *            互生号（持卡人 ：互生号 企业：企业互生号）
	 * @param userType
	 *            用户类型: 2 持卡人 4 企业
	 * @param userName
	 *            操作员账号（非持卡人不填）
	 * @return
	 */
	private void processLockAccount(String custId, String userType)
			throws HsException {
		String resNo = "";
		String entResNo = "";
		String userName = "";
		int pwdQuestionFailTimes = 0;
		int maxFailTimes = SysConfig.getPwdQuestionFailTimes();
		StringBuffer msg = new StringBuffer();
		String str = "";
		// 持卡人锁定帐户
		if (UserTypeEnum.CARDER.getType().equals(userType)) {
			AsConsumerLoginInfo loginInfo = new AsConsumerLoginInfo();
			CardHolder cardHolder = commonCacheService
					.searchCardHolderInfo(custId);
			CustIdGenerator.isCarderExist(cardHolder, custId);
			resNo = StringUtils.nullToEmpty(cardHolder.getPerResNo());
			loginInfo.setResNo(resNo);
			pwdQuestionFailTimes = commonCacheService.getCarderPwdQuestionFailTimesCache(resNo);
			pwdQuestionFailTimes++;
			commonCacheService.addCarderPwdQuestionFailTimesCache(resNo, pwdQuestionFailTimes);
			// 如果持卡人验证密保失败次数超过限制
			msg.append(pwdQuestionFailTimes).append(",").append(maxFailTimes).append(",");
			if (pwdQuestionFailTimes >= maxFailTimes) {
				long second = commonService.computeUnlockSecond(SysConfig
						.getPwdQuestionFailUnlockTime());
				commonCacheService.setCarderPwdQuestionFailTimesCacheKeyExpireInSecond(resNo, second);
				str = "密保信息出错5次，账户已锁定。userType["+userType+"],perResNo["+resNo+"],custId["+custId+"]";
				msg.append(str);
				throw new HsException(ErrorCodeEnum.USER_LOCKED.getValue(),	msg.toString());
			}else{
				str = "密保信息已出错"+pwdQuestionFailTimes+"次，达到5次将锁定账户。userType["+userType+"],perResNo["+resNo+"],custId["+custId+"]";
				msg.append(str);
				throw new HsException(ErrorCodeEnum.PWD_INFO_IS_WRONG.getValue(),msg.toString());
			}
		}
		// 操作员锁定帐户
		else if (UserTypeEnum.ENT.getType().equals(userType)) {
			Operator operator = commonCacheService.searchOperByCustId(custId);
			CustIdGenerator.isOperExist(operator,custId);
			entResNo = StringUtils.nullToEmpty(operator.getEntResNo());
			userName = StringUtils.nullToEmpty(operator.getOperNo());
			pwdQuestionFailTimes = commonCacheService.getEntPwdQuestionFailTimesCache(entResNo);
			pwdQuestionFailTimes++;
			commonCacheService.addEntPwdQuestionFailTimesCache(entResNo, pwdQuestionFailTimes);
			msg.append(pwdQuestionFailTimes).append(",").append(maxFailTimes).append(",");
			// 如果操作员验证密保失败次数次数超过限制
			if (pwdQuestionFailTimes >= maxFailTimes) {
				long second = commonService.computeUnlockSecond(SysConfig
						.getPwdQuestionFailUnlockTime());
				commonCacheService.setEntCyptFailTimesKeyExpireInSecond(entResNo, second);
				str = "密保信息出错5次，验证密保业务已锁定，userType["+userType+"],entResNo["+entResNo+"],userName["+userName+"],custId["+custId+"]";
				msg.append(str);
				throw new HsException(ErrorCodeEnum.CPYT_IS_LOCK.getValue(),msg.toString());
			}else{
				str = "密保信息已出错"+pwdQuestionFailTimes+"次，达到5次将锁定账户。userType["+userType+"],perResNo["+resNo+"],custId["+custId+"]";
				msg.append(str);
				throw new HsException(ErrorCodeEnum.PWD_INFO_IS_WRONG.getValue(),msg.toString());
			}
		}else if (UserTypeEnum.NO_CARDER.getType().equals(userType)) {
			NoCardHolder noCardHolder = commonCacheService.searchNoCardHolderInfo(custId);
			String mobile = noCardHolder.getMobile();
			CustIdGenerator.isNoCarderExist(noCardHolder, custId);
			pwdQuestionFailTimes = commonCacheService.getNoCardPwdQuestionFailTimesCache(mobile);
			pwdQuestionFailTimes++;
			commonCacheService.addNoCardPwdQuestionFailTimesCache(mobile, pwdQuestionFailTimes);// 放入登录token
			msg.append(pwdQuestionFailTimes).append(",").append(maxFailTimes).append(",");
			// 如果操作员验证密保失败次数次数超过限制
			if (pwdQuestionFailTimes >= maxFailTimes) {
				long second = commonService.computeUnlockSecond(SysConfig
						.getPwdQuestionFailUnlockTime());
				commonCacheService.setNoCarderPwdQuestionFailTimesCacheKeyExpireInSecond(mobile, second);
				str = "密保信息出错5次，账户已锁定，userType["+userType+"],mobile["+mobile+"],custId["+custId+"]";
				msg.append(str);
				throw new HsException(ErrorCodeEnum.USER_LOCKED.getValue(),msg.toString());
			}else{
				str = "密保信息已出错"+pwdQuestionFailTimes+"次，达到5次将锁定账户。userType["+userType+"],custId["+custId+"],mobile["+mobile+"]";
				msg.append(str);
				throw new HsException(ErrorCodeEnum.PWD_INFO_IS_WRONG.getValue(),msg.toString());
			}
		}else{
			throw new HsException(ErrorCodeEnum.USERTYPE_IS_ILLEGAL.getValue(),
					ErrorCodeEnum.USERTYPE_IS_ILLEGAL.getDesc()+",userType["+userType+",entResNo["+entResNo+"],userName["+userName+"],perResNo["+resNo+"],custId["+custId+"]");
		}
	}
}
