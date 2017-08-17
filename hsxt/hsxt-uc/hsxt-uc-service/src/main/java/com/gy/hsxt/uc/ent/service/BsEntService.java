/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */

package com.gy.hsxt.uc.ent.service;

import static com.gy.hsxt.common.utils.StringUtils.getNowTimestamp;
import static com.gy.hsxt.common.utils.StringUtils.isBlank;
import static com.gy.hsxt.common.utils.StringUtils.isNotBlank;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsi.nt.api.service.INtService;
import com.gy.hsxt.common.constant.CustType;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.DateUtil;
import com.gy.hsxt.common.utils.StringUtils;
import com.gy.hsxt.lcs.client.LcsClient;
import com.gy.hsxt.uc.as.api.auth.IUCAsRoleService;
import com.gy.hsxt.uc.as.api.common.IUCAsBankAcctInfoService;
import com.gy.hsxt.uc.as.api.enumerate.ErrorCodeEnum;
import com.gy.hsxt.uc.as.api.enumerate.UserTypeEnum;
import com.gy.hsxt.uc.as.bean.common.AsBankAcctInfo;
import com.gy.hsxt.uc.as.bean.common.AsPosEnt;
import com.gy.hsxt.uc.as.bean.ent.AsEntAllInfo;
import com.gy.hsxt.uc.as.bean.ent.AsEntBaseInfo;
import com.gy.hsxt.uc.as.bean.ent.AsEntMainInfo;
import com.gy.hsxt.uc.as.bean.ent.AsEntStatusInfo;
import com.gy.hsxt.uc.as.bean.ent.AsEntTaxRate;
import com.gy.hsxt.uc.bs.api.ent.IUCBsEntService;
import com.gy.hsxt.uc.bs.bean.common.BsBankAcctInfo;
import com.gy.hsxt.uc.bs.bean.ent.BsEntAllInfo;
import com.gy.hsxt.uc.bs.bean.ent.BsEntBaseInfo;
import com.gy.hsxt.uc.bs.bean.ent.BsEntInfo;
import com.gy.hsxt.uc.bs.bean.ent.BsEntMainInfo;
import com.gy.hsxt.uc.bs.bean.ent.BsEntRegInfo;
import com.gy.hsxt.uc.bs.bean.ent.BsEntStatusInfo;
import com.gy.hsxt.uc.bs.bean.ent.BsEntTaxRate;
import com.gy.hsxt.uc.bs.bean.ent.BsHkEntRegInfo;
import com.gy.hsxt.uc.cache.service.CommonCacheService;
import com.gy.hsxt.uc.common.SysConfig;
import com.gy.hsxt.uc.common.bean.PwdQuestion;
import com.gy.hsxt.uc.common.mapper.EntAccountMapper;
import com.gy.hsxt.uc.common.mapper.NetworkInfoMapper;
import com.gy.hsxt.uc.common.mapper.PwdQuestionMapper;
import com.gy.hsxt.uc.common.service.CommonService;
import com.gy.hsxt.uc.common.service.UCAsEmailService;
import com.gy.hsxt.uc.common.service.UCAsMobileService;
import com.gy.hsxt.uc.ent.bean.EntExtendInfo;
import com.gy.hsxt.uc.ent.bean.EntStatusInfo;
import com.gy.hsxt.uc.ent.mapper.EntExtendInfoMapper;
import com.gy.hsxt.uc.ent.mapper.EntStatusInfoMapper;
import com.gy.hsxt.uc.ent.runnable.UpdateEntMapLocation;
import com.gy.hsxt.uc.operator.bean.Operator;
import com.gy.hsxt.uc.operator.mapper.OperatorMapper;
import com.gy.hsxt.uc.operator.service.OperatorService;
import com.gy.hsxt.uc.search.api.IUCUserInfoService;
import com.gy.hsxt.uc.search.bean.SearchUserInfo;
import com.gy.hsxt.uc.util.CSPRNG;

/**
 * 用户中心提供业务企业相关服务的实现类
 * 
 * @Package: com.gy.hsxt.uc.ent.service
 * @ClassName: EntService
 * @Description: TODO
 * 
 * @author: huanggaoyang
 * @date: 2015-10-20 上午10:25:35
 * @version V1.0
 */
@Service
public class BsEntService implements IUCBsEntService {
	private final static String MOUDLENAME = "com.gy.hsxt.uc.ent.service.com.gy.hsxt.uc.ent.service";
	@Autowired
	private EntExtendInfoMapper entExtendInfoMapper;
	@Autowired
	private CommonService commonService;
	@Autowired
	private EntStatusInfoMapper entStatusInfoMapper;

	@Autowired
	private PwdQuestionMapper pwdQuestionMapper;

	@Autowired
	private NetworkInfoMapper networkInfoMapper;

	@Autowired
	private OperatorMapper operatorMapper;

	@Autowired
	private INtService netService;

	@Autowired
	private AsEntService asEntService;

	@Autowired
	private IUCAsRoleService roleService;

	@Autowired
	private IUCUserInfoService userInfoService;

	@Autowired
	private IUCAsBankAcctInfoService bankAcctInfoService;

	@Autowired
	private CommonCacheService commonCacheService;

	@Autowired
	EntAccountMapper entAccountMapper;

	@Autowired
	private UCAsMobileService mobileService;

	@Autowired
	private UCAsEmailService emailService;

	@Autowired
	OperatorService operatorService;

	@Autowired
	LcsClient lcsClient;

	/**
	 * 接收综合前置通知TO_REGION_INIT_M_ENT后，创建管理公司，并生成管理员，发送对应密码。 成功后返回”ok”
	 * 
	 * @param JSONObject
	 *            {entResNo,entCustName,email,optCustId}
	 * @return
	 */
	@Transactional
	public String addManEntByUf(JSONObject json) {
		String entResNo = json.getString("entResNo");
		String entCustName = json.getString("entCustName");
		String email = json.getString("email");
		String optCustId = json.getString("optCustId");

		BsEntRegInfo regInfo = new BsEntRegInfo();
		regInfo.setCustType(CustType.MANAGE_CORP.getCode());
		regInfo.setEntResNo(entResNo);
		regInfo.setEntName(entCustName);
		regInfo.setContactEmail(email);
		regInfo.setOperator(optCustId);
		addEnt(regInfo);

		return "ok";
	}

	/**
	 * 用户中心接收消息TO_REGION_INIT_PLAT_ENT后创建平台企业信息，并生成两个管理密码，发送对应密码等。 成功后返回”ok”.
	 * 
	 * @param JSONObject
	 *            {entResNo,entCustName,emailA,emailB,optCustId}
	 * @return
	 */
	@Transactional
	public String addPlatEntByUf(JSONObject json) {
		String entResNo = json.getString("entResNo");
		String entCustName = json.getString("entCustName");
		String emailA = json.getString("emailA");
		String emailB = json.getString("emailB");
		String optCustId = json.getString("optCustId");

		BsEntRegInfo regInfo = new BsEntRegInfo();
		regInfo.setCustType(CustType.AREA_PLAT.getCode());
		regInfo.setEntResNo(entResNo);
		regInfo.setEntName(entCustName);
		regInfo.setContactEmail(emailA);
		regInfo.setContactAddr(emailA + "," + emailB);
		regInfo.setOperator(optCustId);
		addEnt(regInfo);

		// 生成第二个密码并发送邮件给emailB
		regInfo.setContactEmail(emailB);
		this.savePwd2(regInfo);
		return "ok";
	}

	/**企业开启系统
	 * 添加大陆企业
	 * 
	 * @param regInfo
	 *            企业注册信息
	 * @throws HsException
	 * @see com.gy.hsxt.uc.bs.api.ent.IUCBsEntService#addEnt(com.gy.hsxt.uc.bs.bean.ent.BsEntRegInfo)
	 */
	@Transactional
	public String addEnt(BsEntRegInfo regInfo) throws HsException {
		validEntInfo(regInfo);
		return regEnt(regInfo);
	}

	/**企业开启系统
	 * 添加香港澳门企业
	 * 
	 * @param regInfo
	 *            企业注册信息
	 * @throws HsException
	 * @see com.gy.hsxt.uc.bs.api.ent.IUCBsEntService#addEnt(com.gy.hsxt.uc.bs.bean.ent.BsHkEntRegInfo)
	 */
	@Transactional
	public String addEnt(BsHkEntRegInfo regInfo) throws HsException {
		validEntInfo(regInfo);
		return regEnt(regInfo);
	}

	private void validEntInfo(BsEntRegInfo regInfo) throws HsException {
		// 验证企业名称不能为空
		if (isBlank(regInfo.getEntName())) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"entName is not be null");
		}
		// 验证企业客户类型不能为空
		if (isBlank(regInfo.getCustType())) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"ent custType is not be null");
		}
		// 验证企业是否已存在
		// int count =
		// entExtendInfoMapper.countEntByEntName(regInfo.getEntName());
		// if (count > 0) {
		// throw new HsException(ErrorCodeEnum.ENT_IS_EXIST.getValue(),
		// regInfo.getEntName()+ErrorCodeEnum.ENT_IS_EXIST.getDesc());
		// }
		// 需要传入企业互生号 ，并且验证是否存在
		if (isBlank(regInfo.getEntResNo())) {
			throw new HsException(ErrorCodeEnum.RES_NO_IS_NULL.getValue(),
					ErrorCodeEnum.RES_NO_IS_NULL.getDesc());
		}
		String entCustId = commonCacheService.findEntCustIdByEntResNo(regInfo
				.getEntResNo());
		if (isNotBlank(entCustId)) {// 企业互生号已存在
			throw new HsException(ErrorCodeEnum.RES_NO_EXIST.getValue(),
					regInfo.getEntResNo()
							+ ErrorCodeEnum.RES_NO_EXIST.getDesc());
		}

	}

	@Transactional
	private String regEnt(BsEntRegInfo regInfo) {
		String bsCustId = regInfo.getEntCustId();
		String entCustId = null;
		if (StringUtils.isBlank(bsCustId)) {
			// 生成企业客户号 =企业互生号（11位）+ 企业注册日期（8位）
			entCustId = regInfo.getEntResNo() + getYYYYMMDD();
		} else {// 使用bs生成的客户号
			entCustId = bsCustId;
		}
		// 验证企业是否已存在
		EntExtendInfo entExtendInfo = commonCacheService
				.searchEntExtendInfo(entCustId);
		if (entExtendInfo != null) {
			throw new HsException(ErrorCodeEnum.ENT_IS_EXIST.getValue(),
					entCustId + "企业ID已存在,请等明天生成新客户ID再试");
		}
		// entCustId = "0146208700520151202";
		// 插入企业其他信息
		EntExtendInfo extendInfo = new EntExtendInfo();
		extendInfo.fillRegParams(regInfo);
		extendInfo.setEntCustId(entCustId);
		entExtendInfoMapper.insertSelective(extendInfo);
		Integer entCustType = extendInfo.getCustType();
		// 插入企业状态信息
		EntStatusInfo statusInfo = new EntStatusInfo();
		statusInfo.setEntResNo(regInfo.getEntResNo());
		statusInfo.setEntCustId(entCustId);
		statusInfo.setCustType(entCustType);
		statusInfo.setEntCustName(regInfo.getEntName());
		statusInfo.setIsRealnameAuth(1);// 设置状态为已实名认证 
		if (!isBlank(regInfo.getOpenDate())) {
			statusInfo.setOpenDate(DateUtil.StringToDateHMS(regInfo
					.getOpenDate()));
		}
		if (!isBlank(regInfo.getExpireDate())) {
			statusInfo.setExpireDate(DateUtil.StringToDateHMS(regInfo
					.getExpireDate()));
		}
		entStatusInfoMapper.insertSelective(statusInfo);

		// 生成企业管理员
		String pwd = "666666";
		pwd = CSPRNG.generateRandom(6);  //生成6位随机数登陆密码
		Operator adminOper = genOperator(regInfo, pwd, entCustId);
		operatorMapper.insertSelective(adminOper);

		// 保存密保信息
		PwdQuestion pwdQuestion = new PwdQuestion();
		pwdQuestion.setCreateBy(regInfo.getOperator());
		pwdQuestion.setCustId(entCustId);
		List<PwdQuestion> pwdQuestionList = new ArrayList<PwdQuestion>();
		pwdQuestionList.add(pwdQuestion);
		pwdQuestionMapper.batchInsertSelective(pwdQuestionList);
		// 授权管理员角色
		String roleId = (entCustType + "01");
		List<String> list = new ArrayList<String>();
		list.add(roleId);
		if(entCustType==6){
			list.add("603");//地区平台超级管理员需要初始化 仓库操作员角色 
		}
		roleService.grantRole(adminOper.getOperCustId(), list, "sys");

		sendMsgToOperator(adminOper, pwd, extendInfo);
		try {
			// 通知搜索引擎
			SearchUserInfo user = new SearchUserInfo();
			user.setCustId(adminOper.getOperCustId());
			user.setEntResNo(regInfo.getEntResNo());
			user.setParentEntResNo(extendInfo.getSuperEntResNo());
			user.setUserType(entCustType);
			user.setEntCustId(adminOper.getEntCustId());
			user.setIsLogin(1);
			user.setUsername(adminOper.getOperNo());
			
			if (entCustType == null) {
				throw new HsException(
						ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
						"客户类型(custType)为空");
			}
			userInfoService.add(user);
		} catch (Exception e) {
			SystemLog.error(MOUDLENAME, "regEnt", "通知搜索引擎失败：", e);
		}
		SystemLog.info(MOUDLENAME, "regEnt", JSON.toJSONString(regInfo));

		try {// 使用百度地图api更新企业经纬度
			Integer scsType = 4;
			if (scsType.equals(entCustType)) {//服务公司才更新 地图位置
				(new UpdateEntMapLocation(lcsClient, commonCacheService,
						entExtendInfoMapper, extendInfo)).start();
			}
		} catch (Exception e) {

		}
		return entCustId;
	}

	void sendMsgToOperator(Operator adminOper, String pwd,
			EntExtendInfo extendInfo) {
		if (isNotBlank(adminOper.getPhone())) {// 发送企业申报成功短信给管理员
			sendSms(adminOper, pwd, extendInfo);
		} else if (isNotBlank(adminOper.getEmail())) {// 发送邮件
			sendEmail(adminOper, pwd, extendInfo);
		} else {// PARAM_IS_REQUIRED
			// throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
			// "手机号和邮箱同时为空");
			SystemLog
					.error(MOUDLENAME, "sendMsgToOperator", "手机号和邮箱同时为空", null);
		}
	}

	public String savePwd2(BsEntRegInfo regInfo) {
		// 生成企业客户号 =企业互生号（11位）+ 企业注册日期（8位）
		String entCustId = regInfo.getEntResNo() + getYYYYMMDD();
		// 生成企业管理员
		String pwd = "222222";// CSPRNG.generateRandom(6);
		String operCustId = commonCacheService.findOperCustId(
				regInfo.getEntResNo(), "0000");
		Operator adminOper = genOperator(regInfo, pwd, entCustId);
		adminOper.setOperCustId(operCustId);
		operatorMapper.insertPwd2(adminOper);
		EntExtendInfo extendInfo = new EntExtendInfo();
		BeanUtils.copyProperties(regInfo, extendInfo);
		extendInfo.setEntCustName(regInfo.getEntName());
		sendMsgToOperator(adminOper, pwd, extendInfo);
		return entCustId;
	}

	/**
	 * 查询企业重要信息
	 * 
	 * @param entCustId
	 *            企业客户号
	 * @return
	 * @throws HsException
	 * @see com.gy.hsxt.uc.bs.api.ent.IUCBsEntService#searchEntMainInfo(java.lang.String)
	 */
	@Override
	public BsEntMainInfo searchEntMainInfo(String entCustId) throws HsException {
		// 验证数据
		if (isBlank(entCustId)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					ErrorCodeEnum.PARAM_IS_REQUIRED.getDesc());
		}

		AsEntMainInfo asEntMainInfo = asEntService.searchEntMainInfo(entCustId);
		BsEntMainInfo bsEntMainInfo = new BsEntMainInfo();
		BeanUtils.copyProperties(asEntMainInfo, bsEntMainInfo);
		return bsEntMainInfo;
	}

	/**
	 * 查询企业重要信息 通过企业互生号
	 * 
	 * @param entResNo
	 *            企业互生号
	 * @return
	 * @throws HsException
	 * @see com.gy.hsxt.uc.bs.api.ent.IUCBsEntService#getMainInfoByResNo(java.lang.String)
	 */
	@Override
	public BsEntMainInfo getMainInfoByResNo(String entResNo) throws HsException {
		String entCustId = findEntCustIdByEntResNo(entResNo);
		return searchEntMainInfo(entCustId);
	}

	/**
	 * 修改企业重要信息（实名认证通过后）
	 * 
	 * @param entMainInfo
	 *            企业重要信息
	 * @param operator
	 *            操作者客户号
	 * @throws HsException
	 * @see com.gy.hsxt.uc.bs.api.ent.IUCBsEntService#updateEntMainInfo(com.gy.hsxt.uc.bs.bean.ent.BsEntMainInfo,
	 *      java.lang.String)
	 */
	@Transactional
	public void updateEntMainInfo(BsEntMainInfo entMainInfo, String operator)
			throws HsException {
		// 验证数据
		if (isBlank(operator)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"operator is not be null");
		}
		if (isBlank(entMainInfo) || isBlank(entMainInfo.getEntCustId())) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"entCustId is not be null");
		}
		if (isBlank(entMainInfo) || isBlank(entMainInfo.getEntCustType())) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"custType is not be null");
		}
		if (isBlank(entMainInfo) || isBlank(entMainInfo.getEntName())) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"entName is not be null");
		}
		EntExtendInfo source = commonCacheService
				.searchEntExtendInfo(entMainInfo.getEntCustId());
		// 更新企业重要信息
		Timestamp now = StringUtils.getNowTimestamp();
		EntExtendInfo extendInfo = EntExtendInfo
				.generateExtendInfo(entMainInfo);
		extendInfo.setUpdateDate(now);
		extendInfo.setUpdatedby(operator);
		extendInfo.setModifyAcount(1);// 设置modifyAcount不为空，Mapper.xml文件里面自动增加1
		entExtendInfoMapper.updateByPrimaryKeySelective(extendInfo);
		EntStatusInfo entStatusInfo = new EntStatusInfo();
		entStatusInfo.setEntCustId(extendInfo.getEntCustId());
//		entStatusInfo.setIsKeyinfoChanged(0);// 更改是否重要信息变更期间标识为0（1:是 0：否）
		entStatusInfo.setIsRealnameAuth(1);// 更改为已实名认证
		entStatusInfo.setUpdateDate(now);
		entStatusInfo.setEntCustName(extendInfo.getEntCustName());
		entStatusInfo.setUpdatedby(operator);
		entStatusInfoMapper.updateByPrimaryKeySelective(entStatusInfo);
		// 更新缓存
		commonCacheService.removeEntExtendInfoCache(entMainInfo.getEntCustId());
		commonCacheService.removeEntStatusInfoCache(extendInfo.getEntCustId());
		// 更新POS企业的版本信息，如果名称和企业类型有修改
		updatePosEnt(entMainInfo.getEntName(), entMainInfo.getEntCustType(),
				entMainInfo.getEntResNo());
		// 重要信息变更成功后，不更新用户的当前银行账户信息。
		// if (StringUtils.isNotBlank(entMainInfo.getEntName())) {
		// List<EntAccount> list = entAccountMapper
		// .listAccountByCustId(entMainInfo.getEntCustId());
		// for (EntAccount acct : list) {
		// EntAccount a = new EntAccount();
		// a.setAccId(acct.getAccId());
		// a.setBankAccName(entMainInfo.getEntName());
		// entAccountMapper.updateByPrimaryKeySelective(a);
		// }
		// }
		commonService.isEntNameOrPhoneChag(source, extendInfo);
	}

	/**
	 * 修改企业的重要信息（重要信息变更申请通过后）
	 * 
	 * @param entMainInfo
	 * @param operator
	 * @throws HsException
	 * @see com.gy.hsxt.uc.bs.api.ent.IUCBsEntService#updateMainInfoApplyInfo(com.gy.hsxt.uc.bs.bean.ent.BsEntMainInfo,
	 *      java.lang.String)
	 */
	@Override
	public void updateMainInfoApplyInfo(BsEntMainInfo entMainInfo,
			String operator) throws HsException {
		// 验证数据
		if (isBlank(operator)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"operator is not be null");
		}
		if (isBlank(entMainInfo) || isBlank(entMainInfo.getEntCustId())) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"entCustId is not be null");
		}
		EntExtendInfo source = commonCacheService
				.searchEntExtendInfo(entMainInfo.getEntCustId());
		// 更新企业重要信息
		Timestamp now = StringUtils.getNowTimestamp();
		EntExtendInfo extendInfo = EntExtendInfo
				.generateExtendInfo(entMainInfo);
		extendInfo.setUpdateDate(now);
		extendInfo.setUpdatedby(operator);
		extendInfo.setEntCustNameEn(entMainInfo.getEntNameEn());
		extendInfo.setModifyAcount(1);// 设置modifyAcount不为空，Mapper.xml文件里面自动增加1
		entExtendInfoMapper.updateByPrimaryKeySelective(extendInfo);
		EntStatusInfo entStatusInfo = new EntStatusInfo();
		entStatusInfo.setEntCustId(extendInfo.getEntCustId());
		entStatusInfo.setIsKeyinfoChanged(0);// 更改是否重要信息变更期间标识为0（1:是 0：否）
		entStatusInfo.setUpdateDate(now);
		entStatusInfo.setUpdatedby(operator);
		entStatusInfo.setEntCustName(extendInfo.getEntCustName());
		entStatusInfoMapper.updateByPrimaryKeySelective(entStatusInfo);
		// 更新缓存
		commonCacheService.removeEntExtendInfoCache(entMainInfo.getEntCustId());
		commonCacheService.removeEntStatusInfoCache(extendInfo.getEntCustId());
		// 更新POS企业的版本信息，如果名称和企业类型有修改
		updatePosEnt(entMainInfo.getEntName(), entMainInfo.getEntCustType(),
				entMainInfo.getEntResNo());
		// 重要信息变更成功后，不更新用户的当前银行账户信息。
		// if (StringUtils.isNotBlank(entMainInfo.getEntName())) {
		// List<EntAccount> list = entAccountMapper
		// .listAccountByCustId(entMainInfo.getEntCustId());
		// for (EntAccount acct : list) {
		// EntAccount a = new EntAccount();
		// a.setAccId(acct.getAccId());
		// a.setBankAccName(entMainInfo.getEntName());
		// entAccountMapper.updateByPrimaryKeySelective(a);
		// }
		// }
		commonService.isEntNameOrPhoneChag(source, extendInfo);
	}

	/**
	 * 更新POS的企业信息
	 * 
	 * @param ent
	 *            企业信息，如果参数为null，不会更新，企业互生号不能为空
	 */
	private void updatePosEnt(String entName, Integer entType, String entResNo) {
		if (StringUtils.isBlank(entResNo)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"企业互生号不能为空");
		}
		// 获取POS企业信息
		AsPosEnt oldEnt = commonCacheService.getPosEntCache(entResNo);
		boolean isModify = false;
		if (oldEnt == null) {
			oldEnt = new AsPosEnt();
			// 如果企业名称或类型为空，获取企业信息
			if (StringUtils.isBlank(entName) || entType == null) {
				String custId = commonCacheService
						.findEntCustIdByEntResNo(entResNo);
				EntExtendInfo entInfo = commonCacheService
						.searchEntExtendInfo(custId);
				oldEnt.setEntName(entInfo.getEntCustName());
				oldEnt.setEntType(String.valueOf(entInfo.getCustType()));
			} else {
				// 如果不为空直接赋值
				oldEnt.setEntName(entName);
				oldEnt.setEntType(String.valueOf(entType));
			}
			oldEnt.setEntResNo(entResNo);
			oldEnt.setVersion("1");
			commonCacheService.addPosEntCache(entResNo, oldEnt);
			SystemLog.info(MOUDLENAME, "updatePosEnt", "POS 企业信息缓存添加成功");
			return;
		}

		// 比对是否修改了企业名称和企业类型
		if (StringUtils.isNotBlank(entName)
				&& !entName.equals(oldEnt.getEntName())) {
			oldEnt.setEntName(entName);
			isModify = true;
		}
		if (entType != null
				&& entType.intValue() != Integer.valueOf(oldEnt.getEntType())) {
			isModify = true;
			oldEnt.setEntType(String.valueOf(entType));
		}

		if (isModify) {
			String version = genVersion(oldEnt.getVersion());
			oldEnt.setVersion("" + version);
			// 重设缓存数据
			commonCacheService.addPosEntCache(entResNo, oldEnt);
			SystemLog.info(MOUDLENAME, "updatePosEnt", "POS 企业信息缓存更新成功");
			return;
		}

		SystemLog.info(MOUDLENAME, "updatePosEnt", "POS企业信息缓存不需更新");
	}

	/**
	 * 生成基础信息版本号
	 * 
	 * @param vs
	 * @return
	 */
	public String genVersion(String vs) {
		int version = Integer.valueOf(vs);
		version = version >= 99 ? 1 : version + 1;

		return String.format("%02d", version);
	}

	/**
	 * 查询企业所有信息
	 * 
	 * @param entCustId
	 *            企业客户号
	 * @return
	 * @throws HsException
	 * @see com.gy.hsxt.uc.bs.api.ent.IUCBsEntService#searchEntAllInfo(java.lang.String)
	 */
	@Override
	public BsEntAllInfo searchEntAllInfo(String entCustId) throws HsException {
		AsEntAllInfo asAllInfo = asEntService.searchEntAllInfo(entCustId);

		// copy 属性值 从 AsEntAllInfo to BsEntAllInfo
		BsEntAllInfo bsAllInfo = new BsEntAllInfo();

		BsEntMainInfo mainInfo = new BsEntMainInfo();
		BeanUtils.copyProperties(asAllInfo.getMainInfo(), mainInfo);
		BsEntBaseInfo baseInfo = new BsEntBaseInfo();
		BeanUtils.copyProperties(asAllInfo.getBaseInfo(), baseInfo);
		BsEntStatusInfo statusInfo = new BsEntStatusInfo();
		BeanUtils.copyProperties(asAllInfo.getStatusInfo(), statusInfo);

		bsAllInfo.setBaseInfo(baseInfo);
		bsAllInfo.setMainInfo(mainInfo);
		bsAllInfo.setStatusInfo(statusInfo);
		return bsAllInfo;
	}

	/**
	 * 查询企业所有信息通过企业互生号
	 * 
	 * @param entResNo
	 *            企业互生号
	 * @return
	 * @throws HsException
	 * @see com.gy.hsxt.uc.bs.api.ent.IUCBsEntService#searchEntAllInfoByResNo(java.lang.String)
	 */
	@Override
	public BsEntAllInfo searchEntAllInfoByResNo(String entResNo)
			throws HsException {
		String entCustId = findEntCustIdByEntResNo(entResNo);
		return searchEntAllInfo(entCustId);
	}

	/**
	 * 更改 企业重要信息变更状态
	 * 
	 * @param entCustId
	 *            企业客户号
	 * @param status
	 *            企业重要信息变更状态 是否重要信息变更期间1:是 0：否
	 * @param operator
	 *            操作者客户号
	 * @throws HsException
	 * @see com.gy.hsxt.uc.bs.api.ent.IUCBsEntService#changeEntMainInfoStatus(java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */
	@Transactional
	public void changeEntMainInfoStatus(String entCustId, String status,
			String operator) throws HsException {
		asEntService.changeEntMainInfoStatus(entCustId, status, operator);
	}

	/**
	 * 查询企业税率
	 * 
	 * @param entCustId
	 *            企业客户号
	 * @return
	 * @throws HsException
	 * @see com.gy.hsxt.uc.bs.api.ent.IUCBsEntService#findTaxRate(java.lang.String)
	 */
	@Override
	public String findTaxRate(String entCustId) throws HsException {
		// 验证数据
		if (isBlank(entCustId)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					ErrorCodeEnum.PARAM_IS_REQUIRED.getDesc());
		}
		EntExtendInfo entExtendInfo = commonCacheService
				.getEntExtendInfoCache(entCustId);// 从缓存中查找
		if (entExtendInfo != null) {
			return entExtendInfo.getTaxRate() == null ? null : String
					.valueOf(entExtendInfo.getTaxRate());
		}
		// 缓存没有 从数据库查找
		entExtendInfo = entExtendInfoMapper.selectByPrimaryKey(entCustId);
		if (entExtendInfo == null) {
			throw new HsException(
					ErrorCodeEnum.CUST_ID_IS_NOT_FOUND.getValue(),
					ErrorCodeEnum.CUST_ID_IS_NOT_FOUND.getDesc());
		}
		return entExtendInfo.getTaxRate() == null ? null : String
				.valueOf(entExtendInfo.getTaxRate());
	}

	/**
	 * 修改企业税率
	 * 
	 * @param entCustId
	 *            企业客户号
	 * @param taxRate
	 *            企业税率
	 * @param operator
	 *            操作者客户号
	 * @throws HsException
	 * @see com.gy.hsxt.uc.bs.api.ent.IUCBsEntService#updateTaxRate(java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */
	@Transactional
	public void updateTaxRate(String entCustId, String taxRate, String operator)
			throws HsException {
		// 验证数据
		if (isBlank(entCustId) || isBlank(taxRate) || isBlank(operator)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					ErrorCodeEnum.PARAM_IS_REQUIRED.getDesc());
		}

		// 更新企业税率
		EntExtendInfo entExtendInfo = new EntExtendInfo();
		entExtendInfo.setEntCustId(entCustId);
		entExtendInfo.setTaxRate(new BigDecimal(taxRate));
		entExtendInfo.setUpdateDate(getNowTimestamp());
		entExtendInfo.setUpdatedby(operator);
		entExtendInfoMapper.updateByPrimaryKeySelective(entExtendInfo);
		commonCacheService.removeEntExtendInfoCache(entCustId);// 更新缓存
	}

	/**
	 * 查询企业客户号通过企业互生号
	 * 
	 * @param entResNo
	 *            企业互生号 企业互生号
	 * @return
	 * @throws HsException
	 * @see com.gy.hsxt.uc.bs.api.ent.IUCBsEntService#findEntCustIdByEntResNo(java.lang.String)
	 */
	@Override
	public String findEntCustIdByEntResNo(String entResNo) throws HsException {
		String entCustId = commonCacheService.findEntCustIdByEntResNo(entResNo);
		if (StringUtils.isBlank(entCustId)) {
			throw new HsException(ErrorCodeEnum.ENT_IS_NOT_FOUND.getValue(),
					ErrorCodeEnum.ENT_IS_NOT_FOUND.getDesc() + ",entResNo["
							+ entResNo + "]");
		}
		return entCustId;
	}

	/**
	 * 修改企业状态信息
	 * 
	 * @param entStatusInfo
	 *            企业状信息
	 * @param operator
	 *            操作者客户号
	 * @throws HsException
	 * @see com.gy.hsxt.uc.bs.api.ent.IUCBsEntService#updateEntStatusInfo(com.gy.hsxt.uc.bs.bean.ent.BsEntStatusInfo,
	 *      java.lang.String)
	 */
	@Override
	public void updateEntStatusInfo(BsEntStatusInfo entStatusInfo,
			String operator) throws HsException {
		AsEntStatusInfo asEntStatusInfo = new AsEntStatusInfo();
		BeanUtils.copyProperties(entStatusInfo, asEntStatusInfo);
		asEntService.updateEntStatusInfo(asEntStatusInfo, operator);
	}

	/**
	 * 查询企业一般信息
	 * 
	 * @param entCustId
	 *            企业客户号 ，必传
	 * @return
	 * @throws HsException
	 * @see com.gy.hsxt.uc.bs.api.ent.IUCBsEntService#searchEntBaseInfo(java.lang.String)
	 */
	@Override
	public BsEntBaseInfo searchEntBaseInfo(String entCustId) throws HsException {
		AsEntBaseInfo asEntBaseInfo = asEntService.searchEntBaseInfo(entCustId);
		BsEntBaseInfo bsEntBaseInfo = new BsEntBaseInfo();
		BeanUtils.copyProperties(asEntBaseInfo, bsEntBaseInfo);
		return bsEntBaseInfo;
	}

	/**
	 * 查询企业状态信息
	 * 
	 * @param entCustId
	 *            企业客户号 ，必传
	 * @return
	 * @throws HsException
	 * @see com.gy.hsxt.uc.bs.api.ent.IUCBsEntService#searchEntStatusInfo(java.lang.String)
	 */
	@Override
	public BsEntStatusInfo searchEntStatusInfo(String entCustId)
			throws HsException {
		AsEntStatusInfo asEntStatusInfo = asEntService
				.searchEntStatusInfo(entCustId);
		BsEntStatusInfo bsEntStatusInfo = new BsEntStatusInfo();
		BeanUtils.copyProperties(asEntStatusInfo, bsEntStatusInfo);
		return bsEntStatusInfo;
	}

	/**
	 * 修改企业一般信息
	 * 
	 * @param entBaseInfo
	 *            企业基本信息
	 * @param operator
	 *            操作和客户号
	 * @throws HsException
	 * @see com.gy.hsxt.uc.bs.api.ent.IUCBsEntService#updateEntBaseInfo(com.gy.hsxt.uc.bs.bean.ent.BsEntBaseInfo,
	 *      java.lang.String)
	 */
	@Override
	public void updateEntBaseInfo(BsEntBaseInfo entBaseInfo, String operator)
			throws HsException {
		AsEntStatusInfo asEntStatusInfo = new AsEntStatusInfo();
		BeanUtils.copyProperties(entBaseInfo, asEntStatusInfo);
		asEntService.updateEntStatusInfo(asEntStatusInfo, operator);
	}

	/**
	 * 返回当前日期（YYYYMMDD格式）
	 * 
	 * @return
	 */
	static String getYYYYMMDD() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");// yyyyMMddhhmmss
		return sdf.format(Calendar.getInstance().getTime());
	}

	Operator genOperator(BsEntRegInfo regInfo, String pwd, String entCustId) {
		// 生成企业客户号 =企业互生号（11位）+ 企业注册日期（8位）
		// String entCustId = regInfo.getEntResNo() + getYYYYMMDD();
		// 企业管理员客户号=企业互生号（11位）+ 操作员编号（4位）+ 操作员登记日期（8位）
		String operNo = SysConfig.getAdminAcctName();
		if (StringUtils.isBlank(operNo)) {
			operNo = "0000";
		}
		String operCustId = commonService.checkAndGetOperCustId(
				regInfo.getEntResNo(), operNo);
		Operator adminOper = Operator.buildAdminOperator(pwd);
		adminOper.setOperCustId(operCustId);
		adminOper.setEntCustId(entCustId);
		adminOper.setEntResNo(regInfo.getEntResNo());
		adminOper.setEnterpriseResourceType(regInfo.getCustType().toString());
		adminOper.setOperName("系统操作员"); // 联系人regInfo.getContactPerson()
		adminOper.setOperDuty(regInfo.getContactDuty());// 联系人职务
		adminOper.setPhone(regInfo.getContactPhone());
		adminOper.setEmail(regInfo.getContactEmail());
		adminOper.setOperNo(operNo);
		return adminOper;
	}

	void sendSms(Operator adminOper, String cleartextPwd,
			EntExtendInfo extendInfo) {
		// 发送企业申报成功短信给管理员
		try {
			mobileService.sendInitOperPwd(extendInfo, adminOper, cleartextPwd);
		} catch (Exception e) {
			SystemLog.error(MOUDLENAME, "sendSms", "发送企业申报成功短信给管理员异常", e);
		}
	}

	void sendEmail(Operator adminOper, String cleartextPwd,
			EntExtendInfo extendInfo) {
		try {
			emailService.sendOperInitPwdByEmail(extendInfo, cleartextPwd,
					adminOper);
		} catch (HsException e) {
			SystemLog.error(MOUDLENAME, "sendEmail", "发送企业申报成功邮箱给管理员异常", e);
		}
	}

	/**
	 * 根据企业的客户类型查询企业列表信息
	 * 
	 * @param custType
	 *            企业的客户类型
	 * @return 企业列表
	 * @see com.gy.hsxt.uc.bs.api.ent.IUCBsEntService#listEntInfo(java.lang.String)
	 */
	@Override
	public List<BsEntInfo> listEntInfo(int custType) {
		List<BsEntInfo> resultList = new ArrayList<BsEntInfo>();
		List<EntExtendInfo> list = entExtendInfoMapper
				.listEntendsInfo(custType);
		BsEntInfo entServiceInfo = null;
		for (EntExtendInfo extendInfo : list) {
			entServiceInfo = new BsEntInfo();
			entServiceInfo.setEntCustId(extendInfo.getEntCustId());
			entServiceInfo.setEntResNo(extendInfo.getEntResNo());
			entServiceInfo.setCountryNo(extendInfo.getCountryNo());
			entServiceInfo.setProvinceNo(extendInfo.getProvinceNo());
			entServiceInfo.setCityNo(extendInfo.getCityNo());
			entServiceInfo.setEntName(extendInfo.getEntCustName());
			entServiceInfo.setEntNameEn(extendInfo.getEntCustNameEn());
			entServiceInfo.setContactPerson(extendInfo.getContactPerson());
			entServiceInfo.setContactPhone(extendInfo.getContactPhone());
			entServiceInfo.setEntRegAddr(extendInfo.getEntRegAddr());
			entServiceInfo.setLongitude(extendInfo.getLongitude());
			entServiceInfo.setLatitude(extendInfo.getLatitude());
			resultList.add(entServiceInfo);
		}
		return resultList;
	}

	/**
	 * 企业开启系统
	 */
	@Override
	@Transactional
	public String regEntAddBankCard(BsEntRegInfo regInfo,
			BsBankAcctInfo bsBankAcctInfo) throws HsException {
		// 检查入参
		// 注册企业
		String entCustId = addEnt(regInfo);
		if (null == bsBankAcctInfo) {
			return entCustId;
		}
		bsBankAcctInfo.setCustId(entCustId);
		AsBankAcctInfo asBankAcctInfo = new AsBankAcctInfo();
		BeanUtils.copyProperties(bsBankAcctInfo, asBankAcctInfo);
		// 添加企业银行账户信息
		bankAcctInfoService
				.bindBank(asBankAcctInfo, UserTypeEnum.ENT.getType());
		return entCustId;
	}

	/**
	 * 企业修改税率审核通过
	 * 
	 * @param entCustId
	 *            企业客户号
	 * @throws HsException
	 */
	@Override
	public void updateNoEffectEntTaxRateInfo(BsEntTaxRate entTaxRate,
			String operCustId) throws HsException {
		AsEntTaxRate target = new AsEntTaxRate();
		if (null != entTaxRate) {
			BeanUtils.copyProperties(entTaxRate, target);
			asEntService.updateNoEffectEntTaxRateInfo(target, operCustId);
		}
	}

	@Override
	public void batchUpdateEntStatusInfo(List<BsEntStatusInfo> list)
			throws HsException {
		if (null == list) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"参数 list is null");
		}
		Integer isOweFee = null;
		String expireDate = "";
		EntStatusInfo statusInfo = null;
		String entCustId = "";
		for (BsEntStatusInfo entStatusInfo : list) {
			isOweFee = entStatusInfo.getIsOweFee();
			expireDate = entStatusInfo.getExpireDate();
			entCustId = entStatusInfo.getEntCustId();
			if (null == isOweFee || StringUtils.isBlank(expireDate)
					|| StringUtils.isBlank(entCustId)) {
				SystemLog.warn(MOUDLENAME, "batchUpdateEntStatusInfo",
						"isOweFee[" + isOweFee + "],expireDate[" + expireDate
								+ "],entCustId[" + entCustId + "]");
				continue;
			}
			if (expireDate.trim().length() < 11) {
				expireDate = expireDate + " 00:00:00";
			}
			statusInfo = new EntStatusInfo();
			statusInfo.setIsOweFee(isOweFee);
			statusInfo.setExpireDate(Timestamp.valueOf(expireDate));//
			statusInfo.setEntCustId(entCustId);
			try {
				entStatusInfoMapper.updateByPrimaryKeySelective(statusInfo);
				commonCacheService.removeEntStatusInfoCache(entCustId);
			} catch (Exception e) {
				SystemLog.error(MOUDLENAME, "batchUpdateEntStatusInfo",
						"批量更新企业状态异常：\r\n", e);
				return;
			}
		}
	}

	/**
	 * 检查企业名称是否存在，可附加条件：客户类型 最新业务要求允许企业名称及营业执照重复
	 * 
	 * @param entName
	 *            企业名称
	 * @param custType
	 *            企业类型 2 成员企业;3 托管企业;4 服务公司;5 管理公司;6 地区平台; null 则忽略该条件
	 * @return
	 */
	@Deprecated
	public boolean isEntExist(String entName, Integer custType) {
		return asEntService.isEntExist(entName, custType);
	}

	/**
	 * 检查企业营业执照号是否存在，可附加条件：客户类型 最新业务要求允许企业名称及营业执照重复
	 * 
	 * @param busiLicenseNo
	 *            营业执照号
	 * @param custType
	 *            企业类型 2 成员企业;3 托管企业;4 服务公司;5 管理公司;6 地区平台; null 则忽略该条件
	 * @return
	 */
	@Deprecated
	public boolean isEntBusiLicenseNoExist(String busiLicenseNo,
			Integer custType) {
		return asEntService.isEntBusiLicenseNoExist(busiLicenseNo, custType);
	}

	/**
	 * 企业注销
	 * 
	 * @param entCustId
	 * @param operCustId
	 * @throws HsException
	 * @see com.gy.hsxt.uc.bs.api.ent.IUCBsEntService#entCancel(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	@Transactional
	public void entCancel(String entCustId, String operCustId)
			throws HsException {
		commonService.kickedOut(entCustId);
		// 注销企业及其状态信息
		commonService.entCancel(entCustId, operCustId);
		// 注销企业操作员
		operatorService.deleteOpersByEntCustId(entCustId, operCustId);

	}
}
