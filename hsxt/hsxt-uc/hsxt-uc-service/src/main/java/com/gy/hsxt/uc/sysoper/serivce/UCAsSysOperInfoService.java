/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */

package com.gy.hsxt.uc.sysoper.serivce;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.guid.RandomGuidAgent;
import com.gy.hsxt.common.utils.BeanCopierUtils;
import com.gy.hsxt.common.utils.PageUtil;
import com.gy.hsxt.common.utils.StringUtils;
import com.gy.hsxt.uc.as.api.enumerate.ErrorCodeEnum;
import com.gy.hsxt.uc.as.api.enumerate.UserTypeEnum;
import com.gy.hsxt.uc.as.api.sysoper.IUCAsSysOperInfoService;
import com.gy.hsxt.uc.as.bean.sysoper.AsSysOper;
import com.gy.hsxt.uc.cache.service.CommonCacheService;
import com.gy.hsxt.uc.checker.bean.DoubleChecker;
import com.gy.hsxt.uc.checker.mapper.DoubleCheckerMapper;
import com.gy.hsxt.uc.common.SysConfig;
import com.gy.hsxt.uc.common.service.UCAsPwdService;
import com.gy.hsxt.uc.consumer.service.UCAsCardHolderService;
import com.gy.hsxt.uc.password.PasswordService;
import com.gy.hsxt.uc.password.bean.PasswordBean;
import com.gy.hsxt.uc.sysoper.bean.AsQuerySysCondition;
import com.gy.hsxt.uc.sysoper.bean.SysOperator;
import com.gy.hsxt.uc.sysoper.mapper.SysOperatorMapper;
import com.gy.hsxt.uc.util.CSPRNG;
import com.gy.hsxt.uc.util.CustIdGenerator;

/**
 * 
 * @Package: com.gy.hsxt.uc.sysoper.serivce
 * @ClassName: UCAsSysOperInfoService
 * @Description: 系统用户信息管理
 * 
 * @author: tianxh
 * @date: 2015-10-30 下午4:18:06
 * @version V1.0
 */
@Service
public class UCAsSysOperInfoService implements IUCAsSysOperInfoService {
	private static final String MOUDLENAME = "com.gy.hsxt.uc.sysoper.serivce.UCAsSysOperInfoService";
	@Autowired
	private UCAsCardHolderService uCAsCardHolderService;
	@Autowired
	private SysOperatorMapper sysOperatorMapper;
	@Autowired
	private CommonCacheService commonCacheService;
	@Autowired
	private PasswordService passwordService;
	@Autowired
	private UCAsPwdService pwdService;
	@Autowired
	DoubleCheckerMapper doubleCheckerMapper;
	
	/**
	 * 注册系统用户
	 * 
	 * @param asSysOper
	 *            系统用户信息
	 * @param secretKey
	 *            AES秘钥（随机报文校验token）
	 * @see com.gy.hsxt.uc.as.api.sysoper.IUCAsSysOperInfoService#regSysOper(com.gy.hsxt.uc.as.bean.sysoper.AsSysOper)
	 */
	@Override
	public String regSysOper(AsSysOper asSysOper, String adminCustId) {
		validRegParams(asSysOper, adminCustId);
		String userName = asSysOper.getUserName();
		String loginPwd = asSysOper.getPwdLogin();
		String subSystemCode = asSysOper.getSubSystemCode();
		String platformCode = asSysOper.getPlatformCode();
		// 验证用户名是否已存在
		try {
			findCustIdByUserName(asSysOper.getUserName());
			throw new HsException(ErrorCodeEnum.USER_EXIST.getValue(),
					ErrorCodeEnum.USER_EXIST.getDesc() + ",用户名："
							+ asSysOper.getUserName());
		} catch (HsException e) {

		}
		// 生成登录密码
		String salt = CSPRNG.generateRandom(SysConfig.getCsprLen());// 盐值
		PasswordBean passwordBean = new PasswordBean();
		passwordBean.setEnt(false);
		passwordBean.setPwd(loginPwd);
		passwordBean.setRandomToken(asSysOper.getRandomToken());
		passwordBean.setSaltValue(salt);

		String encryptLoginPwd = passwordService.getDesByAesPwd(passwordBean);
		// 组装保存数据
		String machineNo = "8" + "99";
		String custId = RandomGuidAgent.getStringGuid(machineNo);
		asSysOper.setUserName(userName.trim());
		asSysOper.setOperCustId(custId);
		asSysOper.setPwdLogin(encryptLoginPwd);
		asSysOper.setPwdLoginSaltValue(salt);
		asSysOper.setPwdLoginVer("3");
		asSysOper.setSubSystemCode(subSystemCode.trim());
		asSysOper.setPlatformCode(platformCode.trim());
		asSysOper.setCreatedby(adminCustId);
		SysOperator sysOperator = new SysOperator();
		BeanCopierUtils.copy(asSysOper, sysOperator);
		saveInfo(sysOperator);
		DoubleChecker record = new DoubleChecker();
		BeanCopierUtils.copy(asSysOper, record);
		record.setOperCustId(custId);
		record.setPwdLogin(salt);
		record.setPwdLoginVer("3");
		saveAdminInfo2(record);
		return custId;
	}

	public void regUserOper(AsSysOper asSysOper, String adminCustId) {

	}

	/**
	 * 更新系统用户信息
	 * 
	 * @param asSysOper
	 *            系统用户信息
	 * @see com.gy.hsxt.uc.as.api.sysoper.IUCAsSysOperInfoService#updateSysOper(com.gy.hsxt.uc.as.bean.sysoper.AsSysOper)
	 */
	@Override
	public void updateSysOper(AsSysOper asSysOper) {
		if (null == asSysOper) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"传入参数系统用户信息为空");
		}
		if (StringUtils.isBlank(asSysOper.getOperCustId())) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"传入参数客户号为空");
		}
		SysOperator sysOperator = new SysOperator();
		sysOperator.setUpdateDate(StringUtils.getNowTimestamp());
		sysOperator.setUpdatedby(asSysOper.getOperCustId().trim());
		BeanCopierUtils.copy(asSysOper, sysOperator);
		// 判断系统用户密码是否修改，如修改，生成新密码
		if (StringUtils.isNotBlank(asSysOper.getPwdLogin())) {
			PasswordBean passwordBean = new PasswordBean();
			passwordBean.setEnt(true);
			passwordBean.setPwd(asSysOper.getPwdLogin());
			passwordBean.setRandomToken(asSysOper.getRandomToken());
			String salt = CSPRNG.generateRandom(SysConfig.getCsprLen());
			passwordBean.setSaltValue(salt);
			String pwd = passwordService.getDesByAesPwd(passwordBean);
			sysOperator.setPwdLoginSaltValue(salt);
			sysOperator.setPwdLogin(pwd);
		}

		updateCardHolderInfo(sysOperator);
	}

	/**
	 * 删除系统用户信息 (逻辑删除)
	 * 
	 * @param custId
	 *            客户号
	 */
	@Override
	public void delSysOper(String custId) {
		if (StringUtils.isBlank(custId)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"传入参数客户号为空");
		}
		String operCustId = custId.trim();
		SysOperator sysOperator = new SysOperator();
		sysOperator.setUpdateDate(StringUtils.getNowTimestamp());
		sysOperator.setUpdatedby(operCustId);
		sysOperator.setOperCustId(operCustId);
		sysOperator.setIsactive("N");
		updateCardHolderInfo(sysOperator);
	}

	/**
	 * 查询系统用户信息
	 * 
	 * @param custId
	 *            客户号
	 * @return 系统用户信息
	 */
	@Override
	public AsSysOper searchSysOperInfoByCustId(String custId) {
		if (StringUtils.isBlank(custId)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"传入参数客户号为空");
		}
		SysOperator sysOperator = searchSysOperInfoByCustIdNoWhere(custId
				.trim());
		AsSysOper asSysOper = bean2vo(sysOperator);
		return asSysOper;
	}

	// commonCacheService
	/**
	 * 通过客户号查询系统用户信息
	 * 
	 * @param sysCustId
	 *            客户号
	 * @return 系统用户信息
	 */
	public SysOperator searchSysOperInfoByCustIdNoWhere(String sysCustId) {
		if (StringUtils.isBlank(sysCustId)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"传入参数客户号为空");
		}
		SysOperator sysOperator = commonCacheService
				.searchSysOperInfo(sysCustId);
		if (null == sysOperator) {
			throw new HsException(ErrorCodeEnum.SYSOPER_NOT_FOUND.getValue(),
					ErrorCodeEnum.SYSOPER_NOT_FOUND.getDesc());
		}
		return sysOperator;
	}

	/**
	 * 通过客户号查询系统用户信息
	 * 
	 * @param sysCustId
	 *            客户号
	 * @return 系统用户信息
	 */
	public SysOperator searchSysOperSecondByCustId(String sysCustId) {
		if (StringUtils.isBlank(sysCustId)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"传入参数客户号为空");
		}
		SysOperator sysOperator = commonCacheService
				.searchSysOperSecondInfo(sysCustId);
		if (null == sysOperator) {
			throw new HsException(ErrorCodeEnum.SYSOPER_NOT_FOUND.getValue(),
					ErrorCodeEnum.SYSOPER_NOT_FOUND.getDesc());
		}
		return sysOperator;
	}

	/**
	 * 更新系统用户信息
	 * 
	 * @param srcCardHolder
	 *            系统用户信息
	 */
	public void updateCardHolderInfo(SysOperator srcSysOperator) {
		commonCacheService.updateSysOperInfo(srcSysOperator);
	}

	/**
	 * 系统用户信息入库
	 * 
	 * @param cardHolder
	 *            系统用户信息
	 */
	public void saveInfo(SysOperator sysOperator) {
		try {
			sysOperatorMapper.insertSelective(sysOperator);
		} catch (Exception e) {
			throw new HsException(ErrorCodeEnum.SYSOPER_SAVE_ERROR.getValue(),
					e.getMessage());
		}
	}
	
	public void saveAdminInfo2(DoubleChecker record){
		try {
			doubleCheckerMapper.insertSelective(record);
		} catch (Exception e) {
			throw new HsException(ErrorCodeEnum.SYSOPER_SAVE_ERROR.getValue(),
					e.getMessage());
		}
		
	}

	/**
	 * 分页查询用户列表，可附加过滤条件：平台，子系统 ,类型，客户号,账号，姓名
	 * 
	 * @param platformCode
	 *            平台
	 * @param subSystemCode
	 *            子系统
	 * @param isAdmin
	 *            类型
	 * @param operCustId
	 *            客户号
	 * @param userName
	 *            账号
	 * @param realName
	 *            姓名
	 * @param page
	 *            分页参数
	 * @return 查询结果分页数据
	 * @throws HsException
	 */
	public PageData<AsSysOper> listPermByPage(String platformCode,
			String subSystemCode, Short isAdmin, String operCustId,
			String userName, String realName, Page page) throws HsException {
		// 组装查询参数
		SysOperator oper = new SysOperator();
		oper.setOperCustId(operCustId);
		oper.setPlatformCode(platformCode);
		oper.setSubSystemCode(subSystemCode);
		oper.setIsAdmin(isAdmin);
		oper.setUserName(userName);
		oper.setRealName(realName);
		// 从数据库查询数据
		List<SysOperator> dataList = sysOperatorMapper.selectOperList(oper);
		if (dataList == null || dataList.isEmpty()) {
			return null;
		}
		// 封装分页结果及返回
		PageData<SysOperator> pd = PageUtil.subPage(page, dataList);
		List<AsSysOper> voList = beanList2voList(pd.getResult());
		return new PageData<AsSysOper>(pd.getCount(), voList);
	}

	private List<AsSysOper> beanList2voList(List<SysOperator> beanList) {
		List<AsSysOper> ret = new ArrayList<AsSysOper>();
		for (SysOperator bean : beanList) {
			AsSysOper vo = bean2vo(bean);
			ret.add(vo);
		}
		return ret;
	}

	private AsSysOper bean2vo(SysOperator sysOperator) {
		AsSysOper asSysOper = new AsSysOper();
		BeanCopierUtils.copy(sysOperator, asSysOper);
		return asSysOper;
	}

	/**
	 * 通过系统操作员的账号查询客户号
	 * 
	 * @param userName
	 * @return
	 * @see com.gy.hsxt.uc.as.api.sysoper.IUCAsSysOperInfoService#findCustIdByUserName(java.lang.String)
	 */
	@Override
	public String findCustIdByUserName(String userName) {
		if (StringUtils.isBlank(userName)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"传入参数用户名为空");
		}
		String custId = commonCacheService.findSysCustIdByUserName(userName);
		if (StringUtils.isBlank(custId)) {
			throw new HsException(ErrorCodeEnum.USER_NOT_FOUND.getValue(),
					ErrorCodeEnum.USER_NOT_FOUND.getDesc());
		}
		return custId;
	}

	/**
	 * 通过系统操作员2的账号查询客户号
	 * 
	 * @param userName
	 * @return
	 * @see com.gy.hsxt.uc.as.api.sysoper.IUCAsSysOperInfoService#findCustIdByUserName(java.lang.String)
	 */
	public String findCustIdSecondByUserName(String userName) {
		if (StringUtils.isBlank(userName)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"传入参数用户名为空");
		}
		String custId = commonCacheService
				.findSysCustIdSecondByUserName(userName);
		if (StringUtils.isBlank(custId)) {
			throw new HsException(ErrorCodeEnum.USER_NOT_FOUND.getValue(),
					ErrorCodeEnum.USER_NOT_FOUND.getDesc());
		}
		return custId;
	}

	public SysOperator findSecondPwdByCustId(String custId) {
		SysOperator sysoper = sysOperatorMapper
				.selectOperatorSecondPwdByCustId(custId);
		return sysoper;
	}

	/**
	 * 只更新密码，不验证数据
	 * 
	 * @param oper
	 */
	public void updateSecondPwd(SysOperator oper) {
		sysOperatorMapper.updateSecondPwdByPrimaryKeySelective(oper);
	}

	/**
	 * 只添加第2个密码，不验证数据
	 * 
	 * @param oper
	 */
	public void addSecondPwd(SysOperator oper) {
		sysOperatorMapper.insertSecondPwdSelective(oper);
	}

	@Override
	public void modifyAdminLogPwd(AsSysOper sysOper) throws HsException {
		validPwdParams(sysOper);
		SysOperator sysoper = commonCacheService.searchSysOperInfo(sysOper
				.getOperCustId());
		CustIdGenerator.isSysOperExist(sysoper, sysOper.getOperCustId());
		if (0 == sysoper.getIsAdmin()) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_ILLEGAL.getValue(),
					"不能修改系统操作员登录密码");
		}
		pwdService.updateLoginPwd(sysOper.getOperCustId(),
				UserTypeEnum.SYSTEM.getType(), sysOper.getPwdLogin(),
				sysOper.getNewPwdLogin(), sysOper.getRandomToken());

	}

	@Override
	public void modifySysOperPwd(AsSysOper sysOper) throws HsException {
		validPwdParams(sysOper);
		SysOperator sysoper = commonCacheService.searchSysOperInfo(sysOper
				.getOperCustId());
		CustIdGenerator.isSysOperExist(sysoper, sysOper.getOperCustId());
		if (1 == sysoper.getIsAdmin()) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_ILLEGAL.getValue(),
					"不能修改管理员登录密码");
		}
		pwdService.updateLoginPwd(sysOper.getOperCustId(),
				UserTypeEnum.SYSTEM.getType(), sysOper.getPwdLogin(),
				sysOper.getNewPwdLogin(), sysOper.getRandomToken());
	}


	/**
	 * 检查入参
	 * 
	 * @param sysOper
	 * @throws HsException
	 */
	private void validPwdParams(AsSysOper sysOper) throws HsException {
		if (null == sysOper) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"sysOper is null");
		}
		if (StringUtils.isBlank(sysOper.getPwdLogin())) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"AES登录密码不能为空");
		}
		if (StringUtils.isBlank(sysOper.getNewPwdLogin())) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"AES新登录密码不能为空");
		}
		if (StringUtils.isBlank(sysOper.getRandomToken())) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"AES秘钥不能为空");
		}
	}

	/**
	 * 检查入参
	 * 
	 * @param sysOper
	 * @param adminCustId
	 */
	private void validRegParams(AsSysOper sysOper, String adminCustId) {
		if (null == sysOper) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"sysOper is null");
		}
		if (StringUtils.isBlank(sysOper.getPwdLogin())) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"AES登录密码不能为空");
		}
		if (StringUtils.isBlank(adminCustId)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"管理员客户号不能为空");
		}
		if (StringUtils.isBlank(sysOper.getRandomToken())) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"AES秘钥不能为空");
		}
	}

	@Override
	public List<AsSysOper> listSysOperAndChecker(String userName,
			String realName,Page page) throws HsException {
		AsQuerySysCondition condition = new AsQuerySysCondition();
		List<AsSysOper> resultList = null;
		if(StringUtils.isBlank(userName)){
			condition.setUserName(userName);
		}
		if(StringUtils.isBlank(realName)){
			condition.setRealName(realName);
		}
		try {
			resultList = sysOperatorMapper.listSysOperAndChecker(condition, page);
		} catch (Exception e) {
			SystemLog.error(MOUDLENAME, "listSysOperAndChecker", "查询系统用户和双签员信息列表异常：\r\n", e);
		}
		return resultList;
	}
	
	
}
