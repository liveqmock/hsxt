/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.uc.common.service;

import static com.gy.hsxt.common.utils.StringUtils.isBlank;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsi.lc.client.log4j.BizLog;
import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.DateUtil;
import com.gy.hsxt.common.utils.StringUtils;
import com.gy.hsxt.lcs.bean.LocalInfo;
import com.gy.hsxt.lcs.client.LcsClient;
import com.gy.hsxt.uc.Constants;
import com.gy.hsxt.uc.as.api.auth.IUCAsRoleService;
import com.gy.hsxt.uc.as.api.common.IUCAsBankAcctInfoService;
import com.gy.hsxt.uc.as.api.common.IUCAsNetworkInfoService;
import com.gy.hsxt.uc.as.api.common.IUCLoginService;
import com.gy.hsxt.uc.as.api.consumer.IUCAsCardHolderService;
import com.gy.hsxt.uc.as.api.ent.IUCAsEntService;
import com.gy.hsxt.uc.as.api.enumerate.ErrorCodeEnum;
import com.gy.hsxt.uc.as.api.enumerate.UserTypeEnum;
import com.gy.hsxt.uc.as.bean.auth.AsRole;
import com.gy.hsxt.uc.as.bean.common.AsConsumerLoginInfo;
import com.gy.hsxt.uc.as.bean.common.AsNetworkInfo;
import com.gy.hsxt.uc.as.bean.common.AsOperatorLoginInfo;
import com.gy.hsxt.uc.as.bean.common.AsSysOperLoginInfo;
import com.gy.hsxt.uc.as.bean.consumer.AsCardHolder;
import com.gy.hsxt.uc.as.bean.consumer.AsRealNameReg;
import com.gy.hsxt.uc.as.bean.ent.AsEntExtendInfo;
import com.gy.hsxt.uc.as.bean.ent.AsEntStatusInfo;
import com.gy.hsxt.uc.as.bean.enumerate.AsEntBaseStatusEumn;
import com.gy.hsxt.uc.as.bean.enumerate.ChannelTypeEnum;
import com.gy.hsxt.uc.as.bean.operator.AsOperator;
import com.gy.hsxt.uc.as.bean.result.AsCardHolderLoginResult;
import com.gy.hsxt.uc.as.bean.result.AsNoCardHolderLoginResult;
import com.gy.hsxt.uc.as.bean.result.AsOperatorLoginResult;
import com.gy.hsxt.uc.as.bean.result.AsSysOperatorLoginResult;
import com.gy.hsxt.uc.bs.api.consumer.IUCBsCardHolderService;
import com.gy.hsxt.uc.bs.api.consumer.IUCBsNoCardHolderService;
import com.gy.hsxt.uc.cache.service.CommonCacheService;
import com.gy.hsxt.uc.common.SysConfig;
import com.gy.hsxt.uc.common.bean.NetworkInfo;
import com.gy.hsxt.uc.consumer.bean.CardHolder;
import com.gy.hsxt.uc.consumer.bean.HsCard;
import com.gy.hsxt.uc.consumer.bean.NoCardHolder;
import com.gy.hsxt.uc.consumer.service.UCAsCardHolderAuthInfoService;
import com.gy.hsxt.uc.ent.bean.EntStatusInfo;
import com.gy.hsxt.uc.operator.bean.Operator;
import com.gy.hsxt.uc.operator.service.OperatorService;
import com.gy.hsxt.uc.password.bean.PasswordBean;
import com.gy.hsxt.uc.search.api.IUCUserInfoService;
import com.gy.hsxt.uc.search.bean.SearchUserInfo;
import com.gy.hsxt.uc.sysoper.bean.SysOperator;
import com.gy.hsxt.uc.sysoper.serivce.UCAsSysOperInfoService;
import com.gy.hsxt.uc.util.CSPRNG;
import com.gy.hsxt.uc.util.CustIdGenerator;
import com.gy.hsxt.uc.util.StringEncrypt;
import com.gy.hsxt.uf.api.IUFRegionPacketDataService;
import com.gy.hsxt.uf.bean.packet.data.RegionPacketData;

//import com.gy.hsxt.lcs.api.ILCSBaseDataService;

/**
 * 操作处理类，包括持卡人、非持卡人和操作员的登录
 * 
 * @Package: com.gy.hsxt.uc.common.service
 * @ClassName: LoginService
 * @Description: TODO
 * 
 * @author: lixuan
 * @date: 2015-11-30 下午4:42:39
 * @version V1.0
 */
@Service
public class UCAsLoginService implements IUCLoginService,
		IUFRegionPacketDataService {
	private final static String MOUDLENAME = "com.gy.hsxt.uc.common.service.UCAsLoginService";
	// 持卡人异地登录类型
	final String CARDER_LOGIN_TYPE = "1";
	// 操作员异地登录类型
	final String OPERATOR_LOGIN_TYPE = "2";

	/**
	 * AS持卡人接口
	 */
	@Autowired
	IUCAsCardHolderService asCardHolderService;

	/**
	 * BS持卡人接口
	 */
	@Autowired
	IUCBsCardHolderService bsCardHolderService;

	/**
	 * BS非持卡人接口
	 */
	@Autowired
	IUCBsNoCardHolderService bsNoCardHolderService;

	/**
	 * AS操作员接口 IUCAsOperatorService //OperatorService
	 */
	@Autowired
	OperatorService asOperatorService;

	/**
	 * 网络信息接口
	 */
	@Autowired
	IUCAsNetworkInfoService networkInfoService;

	/**
	 * 银行帐户接口
	 */
	@Autowired
	IUCAsBankAcctInfoService bankAcctInfoService;

	/**
	 * 系统管理员接口
	 */
	@Autowired
	UCAsSysOperInfoService sysOperInfoService;

	/**
	 * token接口
	 */
	@Autowired
	UCAsTokenService tokenService;

	/**
	 * 持卡人证件信息管理接口
	 */
	@Autowired
	UCAsCardHolderAuthInfoService cardHolderAuthInfoService;

	/**
	 * 企业接口
	 */
	@Autowired
	IUCAsEntService asEntService;

	/**
	 * 公共服务器接口
	 */
	@Autowired
	CommonService commonService;

	@Autowired
	CommonCacheService commonCacheService;

	/**
	 * 地区平台接口
	 */
	// @Autowired
	// ILCSBaseDataService baseDataService;
	@Autowired
	LcsClient lcsClient;

	// @Autowired
	// IUCAsSysBoSettingService sysBoSettingService;

	@Autowired
	UCAsPwdService pwdService;

	@Autowired
	IUCAsRoleService roleService;
	
	@Autowired
	private IUCUserInfoService searchUserService;
	/**
	 * 继承综合前置接口，用于实现持卡人和操作员异地登录功能
	 * 
	 * @param param
	 * @return
	 * @see com.gy.hsxt.uf.api.IUFRegionPacketDataService#handleReceived(com.gy.hsxt.uf.bean.packet.data.RegionPacketData)
	 */
	@Override
	public Object handleReceived(RegionPacketData param) {
		// 调用持卡人异地登录接口
		if (param.getHeader().getObligateArgsValue("type")
				.equals(CARDER_LOGIN_TYPE)) {
			return cardHolderUnLocalLogin((AsConsumerLoginInfo) param.getBody()
					.getBodyContent());
		}
		// 调用操作员异地登录接口
		else {
			return operatorNoLocalLogin((AsOperatorLoginInfo) param.getBody()
					.getBodyContent());
		}
	}

	/**
	 * 持卡人本地登录
	 * 
	 * @param loginInfo
	 *            登录信息
	 * @return
	 * @throws HsException
	 */
	@Override
	public AsCardHolderLoginResult cardHolderLogin(AsConsumerLoginInfo loginInfo)
			throws HsException {
		// 验证数据是否完整
		if (StringUtils.isBlank(loginInfo.getResNo())) {
			throw new HsException(ErrorCodeEnum.USER_NAME_IS_EMPTY.getValue(),
					ErrorCodeEnum.USER_NAME_IS_EMPTY.getDesc());
		}
		if (StringUtils.isBlank(loginInfo.getLoginPwd())) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"登录密码不能为空");
		}
		if (null == loginInfo.getChannelType()) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"渠道类型不能为空");
		}
		// 比对密码
		CardHolder carder = (CardHolder) pwdService.matchCarderAesPwdForLogin(
				loginInfo.getResNo(), loginInfo.getLoginPwd(),
				loginInfo.getRandomToken());
		String funName = "cardHolderLogin(持卡人本地登录)";
		StringBuffer columns = new StringBuffer();
		columns.append("resNo").append(Constants.VERTICAL).append("mobile")
				.append(Constants.VERTICAL).append("loginPwd")
				.append(Constants.VERTICAL).append("loginIp")
				.append(Constants.VERTICAL).append("randomToken")
				.append(Constants.VERTICAL).append("channelType");
		StringBuffer contents = new StringBuffer();
		String resNo = StringUtils.nullToEmpty(loginInfo.getResNo());
		String mobile = StringUtils.nullToEmpty(loginInfo.getMobile());
		String loginPwd = StringUtils.nullToEmpty(loginInfo.getLoginPwd());
		String loginIp = StringUtils.nullToEmpty(loginInfo.getLoginIp());
		String randomToken = StringUtils
				.nullToEmpty(loginInfo.getRandomToken());
		String channelType = StringUtils.nullToEmpty(String.valueOf(loginInfo
				.getChannelType().getType()));
		contents.append(resNo).append(Constants.VERTICAL).append(mobile)
				.append(Constants.VERTICAL).append(loginPwd)
				.append(Constants.VERTICAL).append(loginIp)
				.append(Constants.VERTICAL).append(randomToken)
				.append(Constants.VERTICAL).append(channelType);
		BizLog.biz(MOUDLENAME, funName, columns.toString(), contents.toString());
		String custId = carder.getPerCustId();
		if (carder.getIsactive().equals("N")
				|| carder.getBaseStatus().intValue() == 7) {
			throw new HsException(ErrorCodeEnum.USER_NOT_FOUND.getValue(),
					"持卡人[" + custId + "]已禁用或注销");
		}
		HsCard card = commonCacheService.searchHsCardInfo(custId);
		if(card == null || card.getCardStatus().intValue() == 3){
			throw new HsException(ErrorCodeEnum.HSCARD_IS_STOP.getValue(),
					custId + "的互生卡已停用");
		}
		// 保存登录缓存
		String loginToken = setUserLoginCache(loginInfo.getChannelType(),
				carder.getPerCustId());
		AsCardHolderLoginResult cardHolderLoginResult = new AsCardHolderLoginResult();
		cardHolderLoginResult.setToken(loginToken);
		AsRealNameReg asRealNameReg = cardHolderAuthInfoService
				.searchRealNameRegInfo(carder);
		String realName = asRealNameReg.getRealName();
		if("3".equals(asRealNameReg.getCertype())){
			realName = asRealNameReg.getEntName();
		}
		cardHolderLoginResult.setCustName(realName);
		cardHolderLoginResult.setCreNo(asRealNameReg.getCerNo());
		cardHolderLoginResult.setCreType(asRealNameReg.getCertype());
		NetworkInfo net = commonCacheService.searchNetworkInfo(carder
				.getPerCustId());

		BeanUtils.copyProperties(net, cardHolderLoginResult);
		BeanUtils.copyProperties(carder, cardHolderLoginResult);
		cardHolderLoginResult.setNickName(net.getNickname());
		String isBindBank = "0";
		if (null != carder.getIsBindBank()) {
			isBindBank = String.valueOf(carder.getIsBindBank());
		}

		cardHolderLoginResult.setIsBindBank(isBindBank);// 是否绑定银行卡 1：已绑定 0：未绑定
		cardHolderLoginResult.setCustId(custId);
		cardHolderLoginResult.setUserName(loginInfo.getResNo());
		cardHolderLoginResult.setResNo(loginInfo.getResNo());
		cardHolderLoginResult.setEntResNo(carder.getEntResNo());
		cardHolderLoginResult.setHeadPic(net.getHeadShot());
		cardHolderLoginResult.setIsLocal(Constants.YES);
		cardHolderLoginResult.setMobile(carder.getMobile());
		if (carder.getIsAuthMobile() != null) {
			cardHolderLoginResult.setIsAuthMobile(String.valueOf(carder
					.getIsAuthMobile().intValue()));
		} else {
			cardHolderLoginResult.setIsAuthMobile(Constants.NO);
		}
		if (carder.getIsAuthEmail() != null) {
			cardHolderLoginResult.setIsAuthEmail(String.valueOf(carder
					.getIsAuthEmail().intValue()));
			cardHolderLoginResult.setEmail(StringUtils.nullToEmpty(carder
					.getEmail()));
		} else {
			cardHolderLoginResult.setIsAuthEmail(Constants.NO);

		}
		if (carder.getIsRealnameAuth() != null) {
			cardHolderLoginResult.setIsRealnameAuth(String.valueOf(carder
					.getIsRealnameAuth()));
		} else {
			cardHolderLoginResult.setIsRealnameAuth(Constants.NO);
		}
		// 设置持卡人门户网址
		cardHolderLoginResult.setWebUrl(getWebUrl(1));
		cardHolderLoginResult.setLastLoginDate(DateUtil.DateTimeToString(carder
				.getLastLoginDate()));
		cardHolderLoginResult.setLastLoginIp(carder.getLastLoginIp());
		// 更新最后登录时间
		commonCacheService.updateCarderLoginIn(carder, loginInfo.getLoginIp());
		// 设置返回信息
		
		// 通知搜索引擎
		if(carder.getLastLoginDate() == null){
			try{
				List<SearchUserInfo> users = new ArrayList<SearchUserInfo>();
				SearchUserInfo user = new SearchUserInfo();
				user.setUserType(1);
				user.setCustId(custId);
				user.setNickName(resNo);
				user.setUsername(resNo);
				user.setResNo(resNo);
				user.setEntResNo(carder.getEntResNo());
				user.setIsLogin(1);
				user.setAge(net.getAge());
				user.setArea(net.getArea());
				user.setCity(net.getCityNo());
				user.setHeadImage(net.getHeadShot());
				user.setHobby(net.getHobby());
				user.setMobile(net.getMobile());
				user.setProvince(net.getProvinceNo());
				if(net.getSex() != null){
					user.setSex(net.getSex().intValue() + "");
				}
				user.setSign(net.getIndividualSign());
				users.add(user);
				
				searchUserService.batchAdd(users);
			}
			catch(Exception e){
				BizLog.biz(MOUDLENAME, "cardHolderLogin", columns.toString(), "通知搜索引擎，更新持卡人是否登录失败");
			}
		}
		return cardHolderLoginResult;
	}

	/**
	 * 消费者（持卡人或非持卡人）是否登录，从登录缓存中获取并验证是否登录
	 * 
	 * @param userType
	 *            用户类型
	 * @param userName
	 *            用户名称
	 * @return 0 已登录，-1 未登录，其他错误码
	 */
	@Override
	public boolean isUserLogin(UserTypeEnum userType, AsConsumerLoginInfo user)
			throws HsException {
		if (StringUtils.isBlank(user.getResNo())) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"传入参数互生号为空");
		}
		// 根据用户名获取客户号
		String custId = null;
		if (UserTypeEnum.CARDER == userType) {
			custId = commonCacheService.findCustIdByResNo(user.getResNo());
			CustIdGenerator.isCarderExist(custId, user.getResNo());
		} else if (UserTypeEnum.NO_CARDER == userType) {
			custId = bsNoCardHolderService.findCustIdByMobile(user.getMobile());
		}
		Object token = commonCacheService.getLoginTokenCache(
				user.getChannelType(), custId);// 在缓存中查询是否登录
		SystemLog.debug(MOUDLENAME, "isUserLogin", "custId[" + custId
				+ ",token[" + token + "]");
		if (token != null) {
			return true;
		}
		return false;
	}

	/**
	 * 操作员是否登录
	 * 
	 * @param userType
	 *            用户类型
	 * @param user
	 *            用户登录信息
	 * @param channelType
	 *            渠道类型
	 * @return
	 * @throws HsException
	 */
	public boolean isOperatorLogin(AsOperatorLoginInfo user) throws HsException {
		// 根据用户名获取客户号
		String operCustId = commonCacheService.findOperCustId(user.getResNo(), user.getUserName());
		CustIdGenerator.isOperExist(operCustId, user.getResNo(), user.getUserName());
		Object token = commonCacheService.getLoginTokenCache(
				user.getChannelType(), operCustId);// 在缓存中查询是否登录
		SystemLog.debug(MOUDLENAME, "isOperatorLogin",
				"operCustId[" + operCustId + ",token[" + token + "]");
		if (token != null) {
			return true;
		}
		return false;
	}

	/**
	 * 设置消费者的登录缓存
	 * 
	 * @param channelType
	 *            渠道类型
	 * @param custId
	 *            客户号
	 * @return 返回已登录token
	 */
	private String setUserLoginCache(ChannelTypeEnum channelType, String custId) {
		String token = StringEncrypt.sha256(CSPRNG.generateRandom(6)
				+ System.currentTimeMillis());
		commonCacheService.addLoginTokenCache(channelType, custId, token);

		return token;
	}

	/**
	 * 设置操作员的登录缓存
	 * 
	 * @param channelType
	 *            渠道类型
	 * @param entResNo
	 *            企业资源号
	 * @param custId
	 *            客户号
	 * @return
	 */
	private String setOperatorLoginCache(ChannelTypeEnum channelType,
			String custId) {
		String s = CSPRNG.generateRandom(SysConfig.getCsprLen());
		String token = StringEncrypt.sha256(s);
		commonCacheService.addLoginTokenCache(channelType, custId, token);
		return token;
	}

	/**
	 * 设置操作员的登录缓存
	 * 
	 * @param channelType
	 *            渠道类型
	 * @param entResNo
	 *            企业资源号
	 * @param custId
	 *            客户号
	 * @return
	 */
	private String setSysOperatorLoginCache(ChannelTypeEnum channelType,
			String custId) {
		String s = CSPRNG.generateRandom(SysConfig.getCsprLen());
		String token = StringEncrypt.sha256(s);
		commonCacheService.addLoginTokenCache(channelType, custId, token);
		return token;
	}

	/**
	 * 
	 * 持卡人异地登录后设置登录token和用户信息缓存
	 * 
	 * @param cardHolder
	 *            持卡人信息
	 * @param channelType
	 *            渠道类型
	 * @return 登录token
	 */
	public String cardHolderNoLocalLoginSet(AsCardHolder ascardHolder,
			int channelType) {
		// 设置登录token
		ChannelTypeEnum ctype = ChannelTypeEnum.get(channelType);
		String loginToken = setUserLoginCache(ctype,
				ascardHolder.getPerCustId());
		// 设置异地登录的用户信息
		CardHolder cardHolder = new CardHolder();
		BeanUtils.copyProperties(ascardHolder, cardHolder);
		commonCacheService
				.addCarderCache(cardHolder.getPerCustId(), cardHolder);
		return loginToken;
	}

	/**
	 * 异地登录
	 * 
	 * @param loginInfo
	 *            登录信息
	 * @return
	 * @throws HsException
	 */
	@Override
	public AsCardHolderLoginResult cardHolderUnLocalLogin(
			AsConsumerLoginInfo loginInfo) throws HsException {
		// 验证数据是否完整
		if (StringUtils.isBlank(loginInfo.getResNo())) {
			throw new HsException(ErrorCodeEnum.USER_NAME_IS_EMPTY.getValue(),
					ErrorCodeEnum.USER_NAME_IS_EMPTY.getDesc());
		}
		if (StringUtils.isBlank(loginInfo.getLoginPwd())) {
			throw new HsException(ErrorCodeEnum.USER_PWD_IS_WRONG.getValue(),
					ErrorCodeEnum.USER_PWD_IS_WRONG.getDesc());
		}
		if (null == loginInfo.getChannelType()) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"渠道类型不能为空");
		}
		// 验证密码
		CardHolder carder = (CardHolder) pwdService
				.matchAesLoginPwdAndProcByName(UserTypeEnum.CARDER, null,
						loginInfo.getResNo(), loginInfo.getLoginPwd(),
						loginInfo.getRandomToken());
		String funName = "cardHolderUnLocalLogin(持卡人异地登录)";
		StringBuffer columns = new StringBuffer();
		columns.append("resNo").append(Constants.VERTICAL).append("mobile")
				.append(Constants.VERTICAL).append("loginPwd")
				.append(Constants.VERTICAL).append("loginIp")
				.append(Constants.VERTICAL).append("randomToken")
				.append(Constants.VERTICAL).append("channelType");
		StringBuffer contents = new StringBuffer();
		String resNo = StringUtils.nullToEmpty(loginInfo.getResNo());
		String mobile = StringUtils.nullToEmpty(loginInfo.getMobile());
		String loginPwd = StringUtils.nullToEmpty(loginInfo.getLoginPwd());
		String loginIp = StringUtils.nullToEmpty(loginInfo.getLoginIp());
		String randomToken = StringUtils
				.nullToEmpty(loginInfo.getRandomToken());
		String channelType = StringUtils.nullToEmpty(String.valueOf(loginInfo
				.getChannelType().getType()));
		contents.append(resNo).append(Constants.VERTICAL).append(mobile)
				.append(Constants.VERTICAL).append(loginPwd)
				.append(Constants.VERTICAL).append(loginIp)
				.append(Constants.VERTICAL).append(randomToken)
				.append(Constants.VERTICAL).append(channelType);
		BizLog.biz(MOUDLENAME, funName, columns.toString(), contents.toString());
		if (carder.getIsactive().equals("N")
				|| carder.getBaseStatus().intValue() == 7) {
			throw new HsException(ErrorCodeEnum.USER_NOT_FOUND.getValue(),
					"持卡人" + carder.getPerCustId() + "已禁用或注销");
		}
		String custId = carder.getPerCustId();
		AsCardHolderLoginResult achlr = new AsCardHolderLoginResult();
		// 保存登录缓存
		String loginToken = setUserLoginCache(loginInfo.getChannelType(),
				custId);
		achlr.setToken(loginToken);
		// 设置登录返回数据
		AsRealNameReg asRealNameReg = cardHolderAuthInfoService
				.searchRealNameRegInfo(custId);
		String realName = asRealNameReg.getRealName();
		if("3".equals(asRealNameReg.getCertype())){
			realName = asRealNameReg.getEntName();
		}
		achlr.setCustName(realName);
		AsNetworkInfo info = networkInfoService.searchByCustId(custId);
		BeanUtils.copyProperties(carder, achlr);
		BeanUtils.copyProperties(info, achlr);
		achlr.setNickName(info.getNickname());
		boolean isBindBank = bankAcctInfoService.isBindAcct(custId,
				UserTypeEnum.CARDER.getType());
		achlr.setIsBindBank(isBindBank ? Constants.YES : Constants.NO);
		achlr.setCustId(custId);
		achlr.setUserName(loginInfo.getResNo());
		achlr.setResNo(loginInfo.getResNo());
		achlr.setEntResNo(carder.getEntResNo());
		achlr.setHeadPic(info.getHeadShot());
		achlr.setIsLocal(Constants.NO);
		if (carder.getIsAuthMobile() != null) {
			achlr.setIsAuthMobile(String.valueOf(carder.getIsAuthMobile()
					.intValue()));
		} else {
			achlr.setIsAuthMobile(Constants.NO);
		}
		if (carder.getIsAuthEmail() != null) {
			achlr.setIsAuthEmail(String.valueOf(carder.getIsAuthEmail()
					.intValue()));
		} else {
			achlr.setIsAuthEmail(Constants.NO);

		}
		if (carder.getIsRealnameAuth() != null) {
			achlr.setIsRealnameAuth(String.valueOf(carder.getIsRealnameAuth()));
		} else {
			achlr.setIsRealnameAuth(Constants.NO);
		}
		achlr.setLastLoginDate(DateUtil.DateTimeToString(carder
				.getLastLoginDate()));
		achlr.setLastLoginIp(carder.getLastLoginIp());
		// 更新最后登录时间
		commonCacheService.updateCarderLoginIn(carder, loginInfo.getLoginIp());
		// sysBoSettingService.loadCustSettings(custId);

		// 设置返回信息
		return achlr;
	}

	/**
	 * @param loginInfo
	 * @return
	 * @throws HsException
	 */
	@Override
	public AsNoCardHolderLoginResult noCardHolderLogin(
			AsConsumerLoginInfo loginInfo) throws HsException {
		// 验证数据是否完整
		if (StringUtils.isBlank(loginInfo.getMobile())) {
			throw new HsException(ErrorCodeEnum.USER_NAME_IS_EMPTY.getValue(),
					ErrorCodeEnum.USER_NAME_IS_EMPTY.getDesc());
		}
		if (StringUtils.isBlank(loginInfo.getLoginPwd())) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"登录密码不能为空");
		}
		if (null == loginInfo.getChannelType()) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"渠道类型不能为空");
		}
		NoCardHolder noCarder = (NoCardHolder) pwdService
				.matchAesLoginPwdAndProcByName(UserTypeEnum.NO_CARDER, null,
						loginInfo.getMobile(), loginInfo.getLoginPwd(),
						loginInfo.getRandomToken());
		String funName = "noCardHolderLogin(非持卡人登录)";
		StringBuffer columns = new StringBuffer();
		columns.append("resNo").append(Constants.VERTICAL).append("mobile")
				.append(Constants.VERTICAL).append("loginPwd")
				.append(Constants.VERTICAL).append("loginIp")
				.append(Constants.VERTICAL).append("randomToken")
				.append(Constants.VERTICAL).append("channelType");
		StringBuffer contents = new StringBuffer();
		String resNo = StringUtils.nullToEmpty(loginInfo.getResNo());
		String mobile = StringUtils.nullToEmpty(loginInfo.getMobile());
		String loginPwd = StringUtils.nullToEmpty(loginInfo.getLoginPwd());
		String loginIp = StringUtils.nullToEmpty(loginInfo.getLoginIp());
		String randomToken = StringUtils
				.nullToEmpty(loginInfo.getRandomToken());
		String channelType = StringUtils.nullToEmpty(String.valueOf(loginInfo
				.getChannelType().getType()));
		contents.append(resNo).append(Constants.VERTICAL).append(mobile)
				.append(Constants.VERTICAL).append(loginPwd)
				.append(Constants.VERTICAL).append(loginIp)
				.append(Constants.VERTICAL).append(randomToken)
				.append(Constants.VERTICAL).append(channelType);
		BizLog.biz(MOUDLENAME, funName, columns.toString(), contents.toString());
		String custId = noCarder.getPerCustId();
		// 获取用户信息
		AsNoCardHolderLoginResult achlr = new AsNoCardHolderLoginResult();
		// 保存登录缓存
		String loginToken = setUserLoginCache(loginInfo.getChannelType(),
				custId);
		achlr.setToken(loginToken);
		BeanUtils.copyProperties(noCarder, achlr);
		achlr.setMobile(loginInfo.getMobile());
		achlr.setUserName(loginInfo.getMobile());

		AsNetworkInfo info = networkInfoService.searchByCustId(custId);
		if (info != null) {
			BeanUtils.copyProperties(info, achlr);
			achlr.setNickName(info.getNickname());
			achlr.setHeadPic(info.getHeadShot());
		}

		achlr.setIsLocal(Constants.YES);
		achlr.setCustId(custId);
		if (noCarder.getIsAuthEmail() != null) {
			achlr.setIsAuthEmail(String.valueOf(noCarder.getIsAuthEmail()
					.intValue()));
			achlr.setEmail(StringUtils.nullToEmpty(noCarder.getEmail()));
		} else {
			achlr.setIsAuthEmail(Constants.NO);
		}
		if (noCarder.getLastLoginDate() != null) {
			achlr.setLastLoginDate(DateUtil.DateTimeToString(noCarder
					.getLastLoginDate()));
		}
		achlr.setLastLoginIp(noCarder.getLastLoginIp());
		String isBindBank = "0";
		if (null != noCarder.getIsBindBank()) {
			isBindBank = String.valueOf(noCarder.getIsBindBank());
		}
		achlr.setIsBindBank(isBindBank);// 是否绑定银行卡 1：已绑定 0：未绑定
		// 更新数据库中的最后登录时间
		bsNoCardHolderService.updateLoginInfo(noCarder.getPerCustId(),
				loginInfo.getLoginIp(), DateUtil.getCurrentDateTime());
		// 设置返回信息
		return achlr;
	}

	/**
	 * 操作员异地登录
	 * 
	 * @param loginInfo
	 *            登录信息
	 * @return 登录后的操作员信息
	 * @throws HsException
	 */
	@Override
	public AsOperatorLoginResult operatorNoLocalLogin(
			AsOperatorLoginInfo loginInfo) throws HsException {
		return operatorLogin(loginInfo);
	}

	/**
	 * 
	 * 使用 企业互生号+操作号+操作员密码 登陆（单密码）
	 * 
	 * @param loginInfo
	 *            登录信息
	 * @return
	 * @throws HsException
	 */
	@Override
	public AsOperatorLoginResult operatorLogin(AsOperatorLoginInfo loginInfo)
			throws HsException {
		if (StringUtils.isBlank(loginInfo.getCustType())) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"企业客户类型必传");
		}
		if (null == loginInfo.getChannelType()) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"渠道类型不能为空");
		}
		Operator operator = (Operator) pwdService
				.matchAesLoginPwdAndProcByName(UserTypeEnum.OPERATOR,
						loginInfo.getResNo(), loginInfo.getUserName(),
						loginInfo.getLoginPwd(), loginInfo.getRandomToken());
		String funName = "operatorLogin(使用 企业互生号+操作号+操作员密码 登陆（单密码）)";
		StringBuffer columns = new StringBuffer();
		columns.append("resNo").append(Constants.VERTICAL).append("userName")
				.append(Constants.VERTICAL).append("loginPwd")
				.append(Constants.VERTICAL).append("loginIp")
				.append(Constants.VERTICAL).append("randomToken")
				.append(Constants.VERTICAL).append("channelType")
				.append("custType");
		StringBuffer contents = new StringBuffer();
		String resNo = StringUtils.nullToEmpty(loginInfo.getResNo());
		String userName = StringUtils.nullToEmpty(loginInfo.getUserName());
		String loginPwd = StringUtils.nullToEmpty(loginInfo.getLoginPwd());
		String loginIp = StringUtils.nullToEmpty(loginInfo.getLoginIp());
		String custType = StringUtils.nullToEmpty(loginInfo.getCustType());
		String randomToken = StringUtils
				.nullToEmpty(loginInfo.getRandomToken());
		String channelType = StringUtils.nullToEmpty(String.valueOf(loginInfo
				.getChannelType().getType()));
		contents.append(resNo).append(Constants.VERTICAL).append(userName)
				.append(Constants.VERTICAL).append(loginPwd)
				.append(Constants.VERTICAL).append(loginIp)
				.append(Constants.VERTICAL).append(randomToken)
				.append(Constants.VERTICAL).append(channelType)
				.append(Constants.VERTICAL).append(custType);
		BizLog.biz(MOUDLENAME, funName, columns.toString(), contents.toString());
		return operatorLogin(loginInfo, operator);
	}

	/**
	 * 系统管理员登录
	 */
	@Override
	public AsSysOperatorLoginResult sysOperatorLogin(
			AsSysOperLoginInfo loginInfo) throws HsException {
		if (StringUtils.isBlank(loginInfo.getUserName())) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"用户名不能为空");
		}

		if (StringUtils.isBlank(loginInfo.getLoginPwd())) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"密码不能为空");
		}
		if (null == loginInfo.getChannelType()) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"渠道类型不能为空");
		}
		SysOperator operator = (SysOperator) pwdService
				.matchAesLoginPwdAndProcByName(UserTypeEnum.SYSTEM, null,
						loginInfo.getUserName(), loginInfo.getLoginPwd(),
						loginInfo.getRandomToken());
		String funName = "sysOperatorLogin(系统管理员登录)";
		StringBuffer columns = new StringBuffer();
		columns.append("userName").append(Constants.VERTICAL)
				.append("loginPwd").append(Constants.VERTICAL)
				.append("secondLoginPwd").append(Constants.VERTICAL)
				.append("loginIp").append(Constants.VERTICAL)
				.append("randomToken").append(Constants.VERTICAL)
				.append("channelType");
		StringBuffer contents = new StringBuffer();
		String userName = StringUtils.nullToEmpty(loginInfo.getUserName());
		String secondLoginPwd = StringUtils.nullToEmpty(loginInfo
				.getSecondLoginPwd());
		String loginPwd = StringUtils.nullToEmpty(loginInfo.getLoginPwd());
		String loginIp = StringUtils.nullToEmpty(loginInfo.getLoginIp());
		String randomToken = StringUtils
				.nullToEmpty(loginInfo.getRandomToken());
		String channelType = StringUtils.nullToEmpty(String.valueOf(loginInfo
				.getChannelType().getType()));
		contents.append(userName).append(Constants.VERTICAL).append(loginPwd)
				.append(Constants.VERTICAL).append(secondLoginPwd)
				.append(Constants.VERTICAL).append(loginIp)
				.append(Constants.VERTICAL).append(randomToken)
				.append(Constants.VERTICAL).append(channelType);
		BizLog.biz(MOUDLENAME, funName, columns.toString(), contents.toString());
		// 保存登录 token
		String loginToken = setSysOperatorLoginCache(
				loginInfo.getChannelType(), operator.getOperCustId());
		AsSysOperatorLoginResult aolr = new AsSysOperatorLoginResult();
		aolr.setIsLocal(Constants.YES);
		aolr.setToken(loginToken);
		aolr.setUserName(operator.getUserName());
		aolr.setOperCustId(operator.getOperCustId());
		return aolr;
	}

	/**
	 * 系统管理员使用双密码登录
	 * 
	 * @param loginInfo
	 * @return
	 */
	@Override
	public AsSysOperatorLoginResult sysOperatorLoginUse2Pwd(
			AsSysOperLoginInfo loginInfo) {
		if (StringUtils.isBlank(loginInfo.getUserName())) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"用户名不能为空");
		}

		if (StringUtils.isBlank(loginInfo.getLoginPwd())) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"密码不能为空");
		}
		if (StringUtils.isBlank(loginInfo.getSecondLoginPwd())) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"第2个密码不能为空");
		}
		if (null == loginInfo.getChannelType()) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"渠道类型不能为空");
		}
		// 密码1登录
		SysOperator operator = (SysOperator) pwdService
				.matchAesLoginPwdAndProcByName(UserTypeEnum.SYSTEM, null,
						loginInfo.getUserName(), loginInfo.getLoginPwd(),
						loginInfo.getRandomToken());

		String funName = "sysOperatorLoginUse2Pwd(系统管理员使用双密码登录)";
		StringBuffer columns = new StringBuffer();
		columns.append("userName").append(Constants.VERTICAL)
				.append("loginPwd").append(Constants.VERTICAL)
				.append("secondLoginPwd").append(Constants.VERTICAL)
				.append("loginIp").append(Constants.VERTICAL)
				.append("randomToken").append(Constants.VERTICAL)
				.append("channelType");
		StringBuffer contents = new StringBuffer();
		String userName = StringUtils.nullToEmpty(loginInfo.getUserName());
		String secondLoginPwd = StringUtils.nullToEmpty(loginInfo
				.getSecondLoginPwd());
		String loginPwd = StringUtils.nullToEmpty(loginInfo.getLoginPwd());
		String loginIp = StringUtils.nullToEmpty(loginInfo.getLoginIp());
		String randomToken = StringUtils
				.nullToEmpty(loginInfo.getRandomToken());
		String channelType = StringUtils.nullToEmpty(String.valueOf(loginInfo
				.getChannelType().getType()));
		contents.append(userName).append(Constants.VERTICAL).append(loginPwd)
				.append(Constants.VERTICAL).append(secondLoginPwd)
				.append(Constants.VERTICAL).append(loginIp)
				.append(Constants.VERTICAL).append(randomToken)
				.append(Constants.VERTICAL).append(channelType);
		BizLog.biz(MOUDLENAME, funName, columns.toString(), contents.toString());
		// 验证密码2是否正确
		boolean isRight = pwdService.checkSysOperatorSecondPwd(
				operator.getOperCustId(), loginInfo.getSecondLoginPwd(),
				loginInfo.getRandomToken());
		if (!isRight) {
			throw new HsException(
					ErrorCodeEnum.USER_SECOND_PWD_WRONG.getValue(), "第2个密码错误");
		}

		// 保存登录 token
		String loginToken = setSysOperatorLoginCache(
				loginInfo.getChannelType(), operator.getOperCustId());
		AsSysOperatorLoginResult aolr = new AsSysOperatorLoginResult();
		aolr.setIsLocal(Constants.YES);
		aolr.setToken(loginToken);
		aolr.setUserName(operator.getUserName());
		aolr.setOperCustId(operator.getOperCustId());
		return aolr;
	}

	public AsSysOperatorLoginResult sysDoubleOperatorLogin(
			AsSysOperLoginInfo operLoginInfo) throws HsException {
		if (null == operLoginInfo.getChannelType()) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"渠道类型不能为空");
		}
		// 操作员登录
		SysOperator oper = (SysOperator) pwdService
				.matchAesLoginPwdAndProcByName(UserTypeEnum.SYSTEM, null,
						operLoginInfo.getUserName(),
						operLoginInfo.getLoginPwd(),
						operLoginInfo.getRandomToken());

		// 验证关联的双签员登录密码是否正确
		boolean isRight = pwdService.checkSysOperatorSecondPwd(
				oper.getOperCustId2(), operLoginInfo.getSecondLoginPwd(),
				operLoginInfo.getRandomToken());
		if (!isRight) {
			throw new HsException(
					ErrorCodeEnum.USER_SECOND_PWD_WRONG.getValue(), "双签员密码错误");
		}
		String funName = "sysDoubleOperatorLogin(双签员登录)";
		StringBuffer columns = new StringBuffer();
		columns.append("userName").append(Constants.VERTICAL)
				.append("loginPwd").append(Constants.VERTICAL)
				.append("secondLoginPwd").append(Constants.VERTICAL)
				.append("loginIp").append(Constants.VERTICAL)
				.append("randomToken").append(Constants.VERTICAL)
				.append("channelType");
		StringBuffer contents = new StringBuffer();
		String userName = StringUtils.nullToEmpty(operLoginInfo.getUserName());
		String secondLoginPwd = StringUtils.nullToEmpty(operLoginInfo
				.getSecondLoginPwd());
		String loginPwd = StringUtils.nullToEmpty(operLoginInfo.getLoginPwd());
		String loginIp = StringUtils.nullToEmpty(operLoginInfo.getLoginIp());
		String randomToken = StringUtils.nullToEmpty(operLoginInfo
				.getRandomToken());
		String channelType = StringUtils.nullToEmpty(String
				.valueOf(operLoginInfo.getChannelType().getType()));
		contents.append(userName).append(Constants.VERTICAL).append(loginPwd)
				.append(Constants.VERTICAL).append(secondLoginPwd)
				.append(Constants.VERTICAL).append(loginIp)
				.append(Constants.VERTICAL).append(randomToken)
				.append(Constants.VERTICAL).append(channelType);
		BizLog.biz(MOUDLENAME, funName, columns.toString(), contents.toString());
		// 保存登录 token
		String operLoginToken = setSysOperatorLoginCache(
				operLoginInfo.getChannelType(), oper.getOperCustId());
		AsSysOperatorLoginResult aolr1 = new AsSysOperatorLoginResult();
		aolr1.setIsLocal(Constants.YES);
		aolr1.setToken(operLoginToken);
		aolr1.setUserName(oper.getUserName());
		aolr1.setOperCustId(oper.getOperCustId());
		return aolr1;
	}

	/**
	 * 系统用户登录（包含系统管理员和系统操作员）
	 * 
	 * @param operLoginInfo
	 */
	public void sysOperCommonLogin(AsSysOperLoginInfo operLoginInfo) {
		validLoginParams(operLoginInfo);
		String userName = operLoginInfo.getUserName();
		String custId = commonCacheService.findSysCustIdByUserName(userName);
		CustIdGenerator.isSysOperExist(custId, userName);
		SysOperator sysOperator = commonCacheService.searchSysOperInfo(custId);
		Short isAdmin = sysOperator.getIsAdmin();
		if (1 == isAdmin) {// 管理员
			sysOperatorLoginUse2Pwd(operLoginInfo);
		} else {// 操作员
			sysDoubleOperatorLogin(operLoginInfo);
		}
	}

	private void validLoginParams(AsSysOperLoginInfo loginInfo) {
		if (null == loginInfo) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"loginInfo1 or loginInfo2 is null");
		}
		if (StringUtils.isBlank(loginInfo.getUserName())) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"用户名不能为空");
		}

		if (StringUtils.isBlank(loginInfo.getLoginPwd())) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"密码不能为空");
		}
		if (StringUtils.isBlank(loginInfo.getSecondLoginPwd())) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"第2个密码不能为空");
		}
	}

	/**
	 * 操作员使用第2个密码并且登录
	 * 
	 * @param loginInfo
	 * @return
	 */
	public AsSysOperatorLoginResult sysOperatorUseTwoPwdAndLogin(
			AsSysOperLoginInfo loginInfo) {
		// 查询操作员客户号
		if (StringUtils.isBlank(loginInfo.getUserName())) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"用户名不能为空");
		}

		if (StringUtils.isBlank(loginInfo.getSecondLoginPwd())) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"第2个密码不能为空");
		}
		if (null == loginInfo.getChannelType()) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"渠道类型不能为空");
		}
		String custId = sysOperInfoService.findCustIdByUserName(loginInfo
				.getUserName());

		// 验证密码2是否正确
		boolean isRight = pwdService.checkSysOperatorSecondPwd(custId,
				loginInfo.getSecondLoginPwd(), loginInfo.getRandomToken());
		if (!isRight) {
			throw new HsException(
					ErrorCodeEnum.USER_SECOND_PWD_WRONG.getValue(), "第2个密码错误");
		}
		String funName = "sysOperatorUseTwoPwdAndLogin(操作员使用第2个密码并且登录)";
		StringBuffer columns = new StringBuffer();
		columns.append("userName").append(Constants.VERTICAL)
				.append("loginPwd").append(Constants.VERTICAL)
				.append("secondLoginPwd").append(Constants.VERTICAL)
				.append("loginIp").append(Constants.VERTICAL)
				.append("randomToken").append(Constants.VERTICAL)
				.append("channelType");
		StringBuffer contents = new StringBuffer();
		String userName = StringUtils.nullToEmpty(loginInfo.getUserName());
		String secondLoginPwd = StringUtils.nullToEmpty(loginInfo
				.getSecondLoginPwd());
		String loginPwd = StringUtils.nullToEmpty(loginInfo.getLoginPwd());
		String loginIp = StringUtils.nullToEmpty(loginInfo.getLoginIp());
		String randomToken = StringUtils
				.nullToEmpty(loginInfo.getRandomToken());
		String channelType = StringUtils.nullToEmpty(String.valueOf(loginInfo
				.getChannelType().getType()));
		contents.append(userName).append(Constants.VERTICAL).append(loginPwd)
				.append(Constants.VERTICAL).append(secondLoginPwd)
				.append(Constants.VERTICAL).append(loginIp)
				.append(Constants.VERTICAL).append(randomToken)
				.append(Constants.VERTICAL).append(channelType);
		BizLog.biz(MOUDLENAME, funName, columns.toString(), contents.toString());
		// 保存登录 token
		String loginToken = setSysOperatorLoginCache(
				loginInfo.getChannelType(), custId);
		AsSysOperatorLoginResult aolr = new AsSysOperatorLoginResult();
		aolr.setIsLocal(Constants.YES);
		aolr.setToken(loginToken);
		SysOperator operator = sysOperInfoService
				.searchSysOperInfoByCustIdNoWhere(custId);
		aolr.setUserName(operator.getUserName());
		aolr.setOperCustId(custId);
		return aolr;
	}

	/**
	 * 操作员使用绑定互生号+消费者卡密码登陆
	 * 
	 * @param loginInfo
	 *            登录信息
	 * @return
	 * @throws HsException
	 */
	@Override
	public AsOperatorLoginResult operatorLoginByCard(
			AsOperatorLoginInfo loginInfo) throws HsException {
		if (null == loginInfo.getChannelType()) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"渠道类型不能为空");
		}
		// 查询操作员客户号
		if (isBlank(loginInfo.getResNo())) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					ErrorCodeEnum.PARAM_IS_REQUIRED.getDesc());
		}
//		String operCustId = commonCacheService.findOperCustId(loginInfo
//				.getResNo());
//		CustIdGenerator.isOperExist(operCustId, loginInfo.getResNo());

		// 使用绑定互生号+消费者卡密码登陆
		Operator operator = (Operator) pwdService
				.matchAesLoginPwdAndProcByName(UserTypeEnum.OPERATOR_USE_CARDER,
						null,loginInfo.getResNo(), loginInfo.getLoginPwd(),
						loginInfo.getRandomToken());

		// 绑定状态不是已经绑定
		if (operator.getIsBindResNo() != 1) {
			throw new HsException(ErrorCodeEnum.CARD_NOT_BIND.getValue(),
					"操作员未绑定互生卡，操作员客户号=" + operator.getOperCustId());
		}
		String funName = "operatorLoginByCard(操作员使用绑定互生号+消费者卡密码登陆)";
		StringBuffer columns = new StringBuffer();
		columns.append("resNo").append(Constants.VERTICAL).append("userName")
				.append(Constants.VERTICAL).append("loginPwd")
				.append(Constants.VERTICAL).append("loginIp")
				.append(Constants.VERTICAL).append("randomToken")
				.append(Constants.VERTICAL).append("channelType")
				.append(Constants.VERTICAL).append("custType");
		StringBuffer contents = new StringBuffer();
		String resNo = StringUtils.nullToEmpty(loginInfo.getResNo());
		String userName = StringUtils.nullToEmpty(loginInfo.getUserName());
		String loginPwd = StringUtils.nullToEmpty(loginInfo.getLoginPwd());
		String loginIp = StringUtils.nullToEmpty(loginInfo.getLoginIp());
		String custType = StringUtils.nullToEmpty(loginInfo.getCustType());
		String randomToken = StringUtils
				.nullToEmpty(loginInfo.getRandomToken());
		String channelType = StringUtils.nullToEmpty(String.valueOf(loginInfo
				.getChannelType().getType()));
		contents.append(resNo).append(Constants.VERTICAL).append(userName)
				.append(Constants.VERTICAL).append(loginPwd)
				.append(Constants.VERTICAL).append(loginIp)
				.append(Constants.VERTICAL).append(randomToken)
				.append(Constants.VERTICAL).append(channelType)
				.append(Constants.VERTICAL).append(custType);
		BizLog.biz(MOUDLENAME, funName, columns.toString(), contents.toString());
		return operatorLogin(loginInfo, operator);
	}

	/**
	 * 操作员登录
	 * 
	 * @param loginInfo
	 * @return
	 * @throws HsException
	 * @see com.gy.hsxt.uc.as.api.common.IUCLoginService#operatorLogin(com.gy.hsxt.uc.as.bean.common.AsOperatorLoginInfo)
	 */
	private AsOperatorLoginResult operatorLogin(AsOperatorLoginInfo loginInfo,
			Operator operator) throws HsException {

		// 验证操作员帐号是否为使用状态
		if (!operator.getIsactive().equals("Y")
				|| operator.getAccountStatus().intValue() != 0) {
			throw new HsException(ErrorCodeEnum.USER_NOT_FOUND.getValue(),
					"操作员" + operator.getOperCustId() + "帐户已注销/已删除/冻结");
		}
		AsEntExtendInfo entExtendInfo = asEntService.searchEntExtInfo(operator
				.getEntCustId());
		
		// 如果客户类型不为空，则验证
		if (StringUtils.isNotBlank(loginInfo.getCustType())) {
			int custType;
			try {
				custType = Integer.valueOf(loginInfo.getCustType());
			} catch (Exception e) {
				throw new HsException(
						ErrorCodeEnum.ENT_CUSTTYPE_NOT_MATCH.getValue(),
						"企业客户号不为数字类型，传入值为" + loginInfo.getCustType());
			}

			// 验证客户类型
			checkCustIdType(entExtendInfo, custType);
		}

		// 返回企业的状态信息
		AsEntStatusInfo statusInfo = asEntService.searchEntStatusInfo(operator
				.getEntCustId());
		if (statusInfo.getBaseStatus().intValue() == AsEntBaseStatusEumn.CANCELED
				.getstatus()) {// 判断企业状态
			throw new HsException(ErrorCodeEnum.ENT_WAS_INACTIVITY.getValue(),
					statusInfo.getEntResNo() + "企业已注销，不能登录");
		}

		if (statusInfo.getIsClosedEnt().intValue() == 1) {
			throw new HsException(ErrorCodeEnum.ENT_IS_CLOSED.getValue(),
					statusInfo.getEntResNo()
							+ ErrorCodeEnum.ENT_IS_CLOSED.getDesc());
		}
		
		if(statusInfo.getBaseStatus().intValue() == AsEntBaseStatusEumn.SLEEP.getstatus()){
			throw new HsException(ErrorCodeEnum.ENT_IS_SLEEP.getValue(),
					statusInfo.getEntResNo()
							+ "企业长眠不能登录");
		}
		// 保存登录 token
		String loginToken = setOperatorLoginCache(loginInfo.getChannelType(),
				operator.getOperCustId());
		AsOperatorLoginResult aolr = new AsOperatorLoginResult();
		aolr.setIsLocal(Constants.YES);
		aolr.setToken(loginToken);
		fillDataWithEntStatusInfo(aolr, statusInfo);

		// 增加操作员的信息
		fillDataWithOperator(aolr, operator);
		// 增加企业状态
		fillDataWithEntExtInfo(aolr, entExtendInfo);

		// 返回信息增加是否绑定银行卡标识信息
		String isBindBank = "0";
		EntStatusInfo entStatusInfo = commonCacheService
				.searchEntStatusInfo(operator.getEntCustId());
		if (null != entStatusInfo.getIsBindBank()) {
			isBindBank = String.valueOf(entStatusInfo.getIsBindBank());
		}
		aolr.setIsBindBank(isBindBank);// 是否绑定银行卡 1：已绑定 0：未绑定

		// 返回信息增加网络信息
		AsNetworkInfo info = networkInfoService.searchByCustId(operator
				.getOperCustId());
		fillDataWithNetworkInfo(aolr, info);
		aolr.setLastLoginDate(DateUtil.DateTimeToString(operator
				.getLastLoginDate()));
		aolr.setLastLoginIp(operator.getLastLoginIp());

		// 更新数据库最后登录数据
		updateLoginInfo(loginInfo, operator);

		// sysBoSettingService.loadCustSettings(operator.getOperCustId());

		// 设置角色列表
		List<AsRole> roles = roleService.listRoleByCustId(null, null,
				operator.getOperCustId());
		aolr.setRoles(roles);
		System.out
				.println(operator.getOperCustId() + ", 最后登录时间："
						+ aolr.getLastLoginDate() + ", 最后登录IP："
						+ aolr.getLastLoginIp());
		return aolr;

	}

	/**
	 * 检查企业客户类型
	 * 
	 * @param loginInfo
	 */
	private void checkCustIdType(AsEntExtendInfo entExtendInfo, int custType)
			throws HsException {
		Integer entCustType = entExtendInfo.getCustType();
		if (null == entCustType) {
			throw new HsException(ErrorCodeEnum.ENT_HAS_NO_CUSTTYPE.getValue(),
					entExtendInfo.getEntCustId()
							+ ErrorCodeEnum.ENT_HAS_NO_CUSTTYPE.getDesc());
		}
		// 只需核对管理公司的企业类型
		if (custType != entCustType.intValue()) {// 核对企业的客户类型是否正确
			throw new HsException(
					ErrorCodeEnum.ENT_CUSTTYPE_NOT_MATCH.getValue(),
					ErrorCodeEnum.ENT_CUSTTYPE_NOT_MATCH.getDesc()
							+ "，企业原客户类型：" + entCustType + ", 实际传入客户类型:"
							+ custType);
		}
	}

	/**
	 * 更新数据库最后登录数据
	 * 
	 * @param loginInfo
	 * @param operator
	 */
	private void updateLoginInfo(AsOperatorLoginInfo loginInfo,
			Operator operator) {
		commonCacheService.updateOperLoginIn(operator.getOperCustId(),
				loginInfo.getLoginIp(), StringUtils.getNowTimestamp());
	}

	/**
	 * 返回实体中增加操作员信息
	 * 
	 * @param aolr
	 * @param operator
	 */
	private void fillDataWithOperator(AsOperatorLoginResult aolr,
			Operator operator) {
		BeanUtils.copyProperties(operator, aolr);
		aolr.setResNo(operator.getOperResNo());
		aolr.setIsAdmin(String.valueOf(operator.getIsAdmin()));
		aolr.setIsBindResNo(String.valueOf(operator.getIsBindResNo()));
		aolr.setCustId(operator.getOperCustId());
		aolr.setUserName(operator.getOperNo());// 操作号作为用户名返回
	}

	/**
	 * 返回企业的扩展信息
	 * 
	 * @param aolr
	 * @param info
	 */
	private void fillDataWithEntExtInfo(AsOperatorLoginResult aolr,
			AsEntExtendInfo entExtendInfo) {
		BeanUtils.copyProperties(entExtendInfo, aolr);
		if (null != entExtendInfo.getTaxRate()) {
			aolr.setTaxRate(String.valueOf(entExtendInfo.getTaxRate()));
		}
		aolr.setCountryCode(entExtendInfo.getCountryNo());
		aolr.setProvinceCode(entExtendInfo.getProvinceNo());
		aolr.setCityCode(entExtendInfo.getCityNo());
		if (null != entExtendInfo.getCustType()) {
			aolr.setEntWebUrl(getWebUrl(entExtendInfo.getCustType()));// 设置企业门户网址
		}
		if (null != entExtendInfo.getCustType()) {
			aolr.setEntResType(String.valueOf(entExtendInfo.getCustType()));
		}
		aolr.setLogo(entExtendInfo.getLogoImg());
		aolr.setReserveInfo(entExtendInfo.getEnsureInfo());
	}

	/**
	 * 返回企业的状态信息
	 * 
	 * @param aolr
	 * @param statusInfo
	 */
	private void fillDataWithEntStatusInfo(AsOperatorLoginResult aolr,
			AsEntStatusInfo statusInfo) {
		BeanUtils.copyProperties(statusInfo, aolr);

		aolr.setMainInfoStatus(String.valueOf(statusInfo.getIsMaininfoChanged()));// 设置是否在重要信息变更期
		aolr.setHaveTradePwd(statusInfo.isHaveTradePwd());

		if (null != statusInfo.getIsAuthEmail()) {
			aolr.setIsAuthEmail(String.valueOf(statusInfo.getIsAuthEmail()));
		}
		if (null != statusInfo.getIsAuthMobile()) {
			aolr.setIsAuthMobile(String.valueOf(statusInfo.getIsAuthMobile()));
		}
	}

	/**
	 * 返回网络信息
	 * 
	 * @param aolr
	 * @param info
	 */
	private void fillDataWithNetworkInfo(AsOperatorLoginResult aolr,
			AsNetworkInfo info) {
		// 如果没有设置网络信息
		if (info == null) {
			aolr.setHeadPic("");
		} else {
			// 如果设置网络信息
			aolr.setHeadPic(info.getHeadShot());
			aolr.setNickName(info.getNickname());
		}
	}

	/**
	 * 管理员双密码登陆，不区分第一个和第二个顺序
	 * 
	 * @param loginInfo
	 *            操作员信息及密码1
	 * @param pwd2
	 *            登陆密码2
	 * @return
	 */
	@Override
	public AsOperatorLoginResult operatorLogin2(AsOperatorLoginInfo loginInfo,
			String pwd2) throws HsException {
		String entResNo = loginInfo.getResNo();
		String userName = loginInfo.getUserName();

		if (StringUtils.isBlank(entResNo)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"resNo" + ErrorCodeEnum.PARAM_IS_REQUIRED.getDesc());
		}
		if (StringUtils.isBlank(userName)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"userName" + ErrorCodeEnum.PARAM_IS_REQUIRED.getDesc());
		}
		String custId = commonCacheService.getOperCustId(entResNo, userName);
		if (custId == null) {
			throw new HsException(ErrorCodeEnum.USER_NOT_FOUND.getValue(),
					entResNo + "企业互生号未找到操作员" + userName);
		}

		// 获取操作员 数据库密码2
		AsOperator op2 = asOperatorService.searchOperSecondPwd(custId);
		if (op2 == null) {
			throw new HsException(ErrorCodeEnum.PWD_LOGIN_ERROR.getValue(),
					custId + "操作员未配置第二密码");
		}
		// 数据库密码2 比对 传入 密码2
		PasswordBean passwordBean = new PasswordBean();
		passwordBean.setEnt(true);
		passwordBean.setOriginalPwd(op2.getLoginPwd());
		passwordBean.setPwd(pwd2);
		passwordBean.setRandomToken(loginInfo.getRandomToken());
		passwordBean.setSaltValue(op2.getLoginPWdSaltValue());
		passwordBean.setVersion("3");

		boolean isMatch = pwdService.matchAesLoginPwd(passwordBean);
		// 登录不成功
		if (!isMatch) {
			// 数据库密码2 比对 传入 密码1
			passwordBean.setPwd(loginInfo.getLoginPwd());
			isMatch = pwdService.matchAesLoginPwd(passwordBean);
			if (!isMatch) {
				// 增加记录操作员登录失败次数
				int loginFailTimes = commonCacheService.getOperLoginFailTimesCache(entResNo, userName);
				pwdService.processLockOperAccount(entResNo, userName, loginFailTimes);
			}
			// 设置操作员登陆密码为 传入密码2
			loginInfo.setLoginPwd(pwd2);
			//操作员密码1验证  传入密码2，跟操作员db密码1继续比对
			AsOperatorLoginResult ret = operatorLogin(loginInfo);
			return ret;
		}

		//操作员密码1验证
		// 操作员db密码1继续比对
		AsOperatorLoginResult ret = operatorLogin(loginInfo);
		return ret;
	}

	/**
	 * 管理员双密码登陆
	 * 
	 * @param loginInfo
	 *            操作员信息及密码1
	 * @param pwd2
	 *            登陆密码2
	 * @return
	 */
	public AsOperatorLoginResult operatorLogin2Order(
			AsOperatorLoginInfo loginInfo, String pwd2) throws HsException {
		// 密码1登陆
		AsOperatorLoginResult ret = operatorLogin(loginInfo);
		String custId = ret.getCustId();
		// 获取操作员密码2
		AsOperator op2 = asOperatorService.searchOperSecondPwd(custId);

		// 验证密码2是否正确
		PasswordBean passwordBean = new PasswordBean();
		passwordBean.setEnt(true);
		passwordBean.setOriginalPwd(op2.getLoginPwd());
		passwordBean.setPwd(pwd2);
		passwordBean.setRandomToken(loginInfo.getRandomToken());
		passwordBean.setSaltValue(op2.getLoginPWdSaltValue());
		passwordBean.setVersion("3");

		boolean isMatch = pwdService.matchAesLoginPwd(passwordBean);
		// 登录不成功
		if (!isMatch) {
			// processLockAccount(loginInfo.getResNo(), key, userLoginAuth);
			throw new HsException(
					ErrorCodeEnum.USER_SECOND_PWD_WRONG.getValue(), "第二个密码错误");
		}
		return ret;
	}

	/**
	 * 操作员使用第2个密码并且登录
	 * 
	 * @param loginInfo
	 * @return
	 */
	public AsOperatorLoginResult operatorUseTwoPwdAndLogin(
			AsOperatorLoginInfo loginInfo) {
		if (null == loginInfo.getChannelType()) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"渠道类型不能为空");
		}
		if (StringUtils.isBlank(loginInfo.getCustType())) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"企业客户类型必传");
		}
		// 查询操作员客户号
		if (isBlank(loginInfo.getResNo())) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					ErrorCodeEnum.PARAM_IS_REQUIRED.getDesc());
		}
		String custId = commonCacheService.findOperCustId(loginInfo.getResNo());
		CustIdGenerator.isOperExist(custId, loginInfo.getResNo());

		// 获取操作员密码2
		AsOperator secondPwdOperator = asOperatorService
				.searchOperSecondPwd(custId);

		// 验证密码2是否正确
		PasswordBean passwordBean = new PasswordBean();
		passwordBean.setEnt(true);
		passwordBean.setOriginalPwd(secondPwdOperator.getLoginPwd());
		passwordBean.setPwd(loginInfo.getLoginPwd());
		passwordBean.setRandomToken(loginInfo.getRandomToken());
		passwordBean.setSaltValue(secondPwdOperator.getLoginPWdSaltValue());
		passwordBean.setVersion("3");

		boolean isMatch = pwdService.matchAesLoginPwd(passwordBean);
		// 登录不成功
		if (!isMatch) {
			// processLockAccount(loginInfo.getResNo(), key, userLoginAuth);
			throw new HsException(
					ErrorCodeEnum.USER_SECOND_PWD_WRONG.getValue(), "第二个密码错误");
		}
		String funName = "operatorUseTwoPwdAndLogin(操作员使用第2个密码并且登录)";
		StringBuffer columns = new StringBuffer();
		columns.append("resNo").append(Constants.VERTICAL).append("userName")
				.append(Constants.VERTICAL).append("loginPwd")
				.append(Constants.VERTICAL).append("loginIp")
				.append(Constants.VERTICAL).append("randomToken")
				.append(Constants.VERTICAL).append("channelType")
				.append(Constants.VERTICAL).append("custType");
		StringBuffer contents = new StringBuffer();
		String resNo = StringUtils.nullToEmpty(loginInfo.getResNo());
		String userName = StringUtils.nullToEmpty(loginInfo.getUserName());
		String loginPwd = StringUtils.nullToEmpty(loginInfo.getLoginPwd());
		String loginIp = StringUtils.nullToEmpty(loginInfo.getLoginIp());
		String custType = StringUtils.nullToEmpty(loginInfo.getCustType());
		String randomToken = StringUtils
				.nullToEmpty(loginInfo.getRandomToken());
		String channelType = StringUtils.nullToEmpty(String.valueOf(loginInfo
				.getChannelType().getType()));
		contents.append(resNo).append(Constants.VERTICAL).append(userName)
				.append(Constants.VERTICAL).append(loginPwd)
				.append(Constants.VERTICAL).append(loginIp)
				.append(Constants.VERTICAL).append(randomToken)
				.append(Constants.VERTICAL).append(channelType)
				.append(Constants.VERTICAL).append(custType);
		BizLog.biz(MOUDLENAME, funName, columns.toString(), contents.toString());
		Operator operator = commonCacheService.searchOperByCustId(custId);
		// 登录操作
		return operatorLogin(loginInfo, operator);
	}

	/**
	 * 审批复核员验证
	 * 
	 * @param loginInfo
	 *            复核员登陆信息
	 * @param authUrl
	 *            审核通过url（权限url）
	 * @return true or false
	 */
	public boolean operatorAuth(AsOperatorLoginInfo loginInfo, String authUrl)
			throws HsException {
		// 登陆校验
		Operator operator = (Operator) pwdService
				.matchAesLoginPwdAndProcByName(UserTypeEnum.OPERATOR,
						loginInfo.getResNo(), loginInfo.getUserName(),
						loginInfo.getLoginPwd(), loginInfo.getRandomToken());
		// 审核员权限校验
		return commonService.hashUrlPermission(operator.getOperCustId(),
				authUrl);
	}

	/**
	 * 返回登陆用户对应的门户网址
	 * 
	 * @param custType
	 *            客户类型: 1 持卡人;2 成员企业;3 托管企业;4 服务公司;5 管理公司;6 地区平台;7 总平台;
	 * @return
	 */
	private String getWebUrl(int custType) {
		// LocalInfo localInfo = baseDataService.getLocalInfo();
		LocalInfo localInfo = lcsClient.getLocalInfo();
		switch (custType) {
		case 1: // 1 持卡人
			return localInfo.getPersonWebSite();
		case 2: // 2 成员企业
			return localInfo.getCompanyWebSite();
		case 3: // 3 托管企业
			return localInfo.getCompanyWebSite();
		case 4: // 4 服务公司
			return localInfo.getServiceWebSite();
		case 5: // 5 管理公司
			return localInfo.getManageWebSite();
		case 6: // 6 地区平台
			return localInfo.getPlatWebSite();
		default:
			return null;
		}
	}

}
