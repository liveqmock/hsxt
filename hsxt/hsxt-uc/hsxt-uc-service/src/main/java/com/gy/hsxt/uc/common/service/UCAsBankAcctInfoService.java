/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.uc.common.service;

import static com.gy.hsxt.common.utils.StringUtils.isBlank;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.guid.RandomGuidAgent;
import com.gy.hsxt.common.utils.StringUtils;
import com.gy.hsxt.uc.Constants;
import com.gy.hsxt.uc.as.api.common.IUCAsBankAcctInfoService;
import com.gy.hsxt.uc.as.api.consumer.IUCAsCardHolderAuthInfoService;
import com.gy.hsxt.uc.as.api.consumer.IUCAsCardHolderService;
import com.gy.hsxt.uc.as.api.consumer.IUCAsNoCardHolderService;
import com.gy.hsxt.uc.as.api.ent.IUCAsEntService;
import com.gy.hsxt.uc.as.api.enumerate.ErrorCodeEnum;
import com.gy.hsxt.uc.as.api.enumerate.UserTypeEnum;
import com.gy.hsxt.uc.as.bean.common.AsBankAcctInfo;
import com.gy.hsxt.uc.as.bean.common.AsQkBank;
import com.gy.hsxt.uc.as.bean.consumer.AsCardHolder;
import com.gy.hsxt.uc.as.bean.ent.AsEntBaseInfo;
import com.gy.hsxt.uc.as.bean.ent.AsEntStatusInfo;
import com.gy.hsxt.uc.cache.service.CommonCacheService;
import com.gy.hsxt.uc.common.SysConfig;
import com.gy.hsxt.uc.common.bean.CustAccount;
import com.gy.hsxt.uc.common.bean.CustQkAccount;
import com.gy.hsxt.uc.common.bean.EntAccount;
import com.gy.hsxt.uc.common.bean.EntQkAccount;
import com.gy.hsxt.uc.common.mapper.CustAccountMapper;
import com.gy.hsxt.uc.common.mapper.CustQkAccountMapper;
import com.gy.hsxt.uc.common.mapper.EntAccountMapper;
import com.gy.hsxt.uc.common.mapper.EntQkAccountMapper;
import com.gy.hsxt.uc.consumer.bean.CardHolder;
import com.gy.hsxt.uc.consumer.bean.NoCardHolder;
import com.gy.hsxt.uc.ent.bean.EntStatusInfo;
import com.gy.hsxt.uc.util.CustIdGenerator;

/**
 * 用户中心提供的银行账户服务接口
 * 
 * @Package: com.gy.hsxt.uc.common.service
 * @ClassName: UCAsBankAcctInfoService
 * @Description: TODO
 * 
 * @author: lixuan
 * @date: 2015-11-12 下午1:59:19
 * @version V1.0
 */
@Service
public class UCAsBankAcctInfoService implements IUCAsBankAcctInfoService {
	private final static String MOUDLENAME = "com.gy.hsxt.uc.common.service.UCAsBankAcctInfoService";
	/**
	 * 银行帐户
	 */
	@Autowired
	CustAccountMapper custAccountMapper;
	/**
	 * 快捷银行帐户
	 */
	@Autowired
	CustQkAccountMapper custQkAccountMapper;
	/**
	 * 企业帐户
	 */
	@Autowired
	EntAccountMapper entAccountMapper;
	/**
	 * 企业快捷帐户
	 */
	@Autowired
	EntQkAccountMapper entQkAccountMapper;
	/**
	 * 企业操作接口
	 */
	@Autowired
	IUCAsEntService asEntService;
	/**
	 * 持卡人
	 */
	@Autowired
	IUCAsCardHolderService cardHolderService;
	/**
	 * 非持卡人
	 */
	@Autowired
	IUCAsNoCardHolderService noCardHolderService;
	/**
	 * 持卡人证件信息管理
	 */
	@Autowired
	IUCAsCardHolderAuthInfoService cardHolderAuthInfoService;

	@Autowired
	private CustIdGenerator generator;

	@Autowired
	CommonCacheService commonCacheService;

	/**
	 * 是否绑定银行
	 * 
	 * @param custId
	 *            客户号
	 * @param userType
	 *            用户类型（持卡人和企业），企业类型使用Operator
	 * @return
	 */
	@Override
	public boolean isBindAcct(String custId, String userType) {
		List<AsBankAcctInfo> list = listBanksByCustId(custId, userType);

		if (list == null || list.size() <= 0) {
			return false;
		}
		return true;
	}

	/**
	 * 绑定银行账户
	 * 
	 * @param acctInfo
	 *            银行账户
	 * @param userType
	 *            用户类型
	 * @throws HsException
	 * @see com.gy.hsxt.uc.as.api.common.IUCAsBankAcctInfoService#bindBank(com.gy.hsxt.uc.as.bean.common.AsBankAcctInfo,
	 *      com.gy.hsxt.uc.as.api.enumerate.UserTypeEnum)
	 */
	@Override
	public void bindBank(AsBankAcctInfo acctInfo, String userType)
			throws HsException {
		// 数据验证
		if (isBlank(acctInfo)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"帐户信息不能为空");
		}
		if (isBlank(userType)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"用户类型不能为空");
		}
		if (isBlank(acctInfo.getCustId())) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"客户号不能为空");
		}
		if (isBlank(acctInfo.getBankCode())) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"银行代码不能为空");
		}
		if (isBlank(acctInfo.getBankAccNo())) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"银行帐号不能为空");
		}
		if (isBlank(acctInfo.getBankAccName())) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"银行账户名称不能为空");
		}
		if (userType.equals(UserTypeEnum.ENT.getType())) {
			if (isBlank(acctInfo.getResNo())) {
				throw new HsException(
						ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(), "企业互生号不能为空");
			}
			bindEntBank(acctInfo);
			return;
		}
		// 绑定银行帐户
		if (userType.equals(UserTypeEnum.CARDER.getType())
				|| userType.equals(UserTypeEnum.NO_CARDER.getType())) {
			bindCustBank(acctInfo, UserTypeEnum.getUserTypeEnum(userType));
			return;
		} else {
			// 其他 类型抛出类型不正确的异常
			SystemLog.debug(MOUDLENAME, "bindBank", "绑定银行帐户时入参用户类型不正确，用户类型："
					+ userType);
			throw new HsException(ErrorCodeEnum.PARAM_IS_ILLEGAL.getValue(),
					userType + "用户 类型不正确");
		}
	}

	/**
	 * 绑定企业银行帐号
	 * 
	 * @param bankAcc
	 *            企业银行帐号信息
	 */
	public void bindEntBank(AsBankAcctInfo bankAcc) throws HsException {
		// 验证企业是否存在
		EntStatusInfo entStatusInfo = commonCacheService
				.searchEntStatusInfo(bankAcc.getCustId());
		Integer isBindBank = entStatusInfo.getIsBindBank();
		// 检查企业银行账户是否存在
		checkEntBankCardIsExist(bankAcc);
		bankAcc.setResNo(entStatusInfo.getEntResNo());
		// 插入数据
		EntAccount entAcc = EntAccount.generateEntAccount(bankAcc);
		try {
			if ("1".equals(bankAcc.getIsDefaultAccount())) {
				// 设置企业所有银行账号不是默认账号
				entAccountMapper.setDefaultAccToGeneral(entAcc.getEntCustId());
				// 设置该账号为默认
				entAcc.setIsDefaultAccount(1);
			}
		} catch (HsException e) {
			SystemLog
					.error(MOUDLENAME, "bindEntBank", "设置企业所有银行账号不是默认账号失败：", e);
			e.printStackTrace();
			throw new HsException(
					ErrorCodeEnum.SET_ACCOUT_NOT_DEFAULT_ERROR.getValue(),
					ErrorCodeEnum.SET_ACCOUT_NOT_DEFAULT_ERROR.getDesc()
							+ "：\r\n" + e);
		}
		try {
			//主键id
//			String id =CustIdGenerator.genSeqId("");
//			entAcc.setAccId(Long.valueOf(id)); //页面 json不兼容15位以上长数字，暂时还是使用自增长
			entAccountMapper.insertSelective(entAcc);
		} catch (Exception e) {
			e.printStackTrace();
			SystemLog.error(MOUDLENAME, "bindEntBank", "绑定企业银行帐号失败：", e);
			throw new HsException(ErrorCodeEnum.SAVE_ACCOUT_ERROR.getValue(),
					ErrorCodeEnum.SAVE_ACCOUT_ERROR.getDesc() + "：\r\n" + e);
		}
		if (null == isBindBank || 0 == isBindBank) {
			entStatusInfo = new EntStatusInfo();
			entStatusInfo.setEntCustId(bankAcc.getCustId());
			entStatusInfo.setIsBindBank(1);
			entStatusInfo.setUpdateDate(StringUtils.getNowTimestamp());
			entStatusInfo.setUpdatedby(bankAcc.getCustId());
			commonCacheService.updateEntStatusInfo(entStatusInfo,
					bankAcc.getCustId());
		}
	}

	/**
	 * 检查企业银行账户是否已存在
	 * 
	 * @param bankAcc
	 * @throws HsException
	 */
	private void checkEntBankCardIsExist(AsBankAcctInfo bankAcc)
			throws HsException {
		int count = 0;
		try {
			count = entAccountMapper.selectCounts(bankAcc.getCustId(),
					bankAcc.getBankAccNo());
		} catch (HsException e) {
			throw new HsException(
					ErrorCodeEnum.QUERY_ENT_BANK_ACCOUT_COUNT_ERROR.getValue(),
					ErrorCodeEnum.QUERY_ENT_BANK_ACCOUT_COUNT_ERROR.getDesc()
							+ ",entCustId[" + bankAcc.getCustId()
							+ ",bankAccNo[" + bankAcc.getBankAccNo()
							+ "],异常：\r\n" + e);
		}
		if (count > 0) {
			throw new HsException(
					ErrorCodeEnum.BANK_ACCOUT_NO_IS_EXIST.getValue(),
					ErrorCodeEnum.BANK_ACCOUT_NO_IS_EXIST.getDesc()
							+ ",entCustId[" + bankAcc.getCustId()
							+ ",bankAccNo[" + bankAcc.getBankAccNo() + "]");
		}
	}

	/**
	 * 检查消费者银行账户是否已存在
	 * 
	 * @param bankAcc
	 * @throws HsException
	 */
	private void checkCustBankCardIsExist(AsBankAcctInfo bankAcc)
			throws HsException {
		int count = 0;
		try {
			count = custAccountMapper.selectCounts(bankAcc.getCustId(),
					bankAcc.getBankAccNo());
		} catch (HsException e) {
			SystemLog.error(MOUDLENAME, "checkCustBankCardIsExist",
					ErrorCodeEnum.QUERY_CUST_BANK_ACCOUT_COUNT_ERROR.getDesc()
							+ "：r\n", e);
			throw new HsException(
					ErrorCodeEnum.QUERY_CUST_BANK_ACCOUT_COUNT_ERROR.getValue(),
					ErrorCodeEnum.QUERY_CUST_BANK_ACCOUT_COUNT_ERROR.getDesc()
							+ ",custIdCustId[" + bankAcc.getCustId()
							+ ",bankAccNo[" + bankAcc.getBankAccNo()
							+ "],异常：\r\n" + e);
		}
		if (count > 0) {
			SystemLog.error(MOUDLENAME, "checkCustBankCardIsExist", ""
					+ ErrorCodeEnum.BANK_ACCOUT_NO_IS_EXIST.getValue()
					+ ",custIdCustId[" + bankAcc.getCustId() + ",bankAccNo["
					+ bankAcc.getBankAccNo() + "]", new Exception("银行账户已存在"));
			throw new HsException(
					ErrorCodeEnum.BANK_ACCOUT_NO_IS_EXIST.getValue(),
					ErrorCodeEnum.BANK_ACCOUT_NO_IS_EXIST.getDesc());
		}
	}

	/**
	 * 绑定消费者银行帐号信息
	 * 
	 * @param bankAcc
	 *            银行帐号信息
	 * @param userType
	 *            用户类型
	 */
	private void bindCustBank(AsBankAcctInfo bankAcc, UserTypeEnum userType)
			throws HsException {
		Integer isBindBank = null;
		// 验证消费者是否存在
		if (userType == UserTypeEnum.CARDER) {
			CardHolder cardHolder = commonCacheService
					.searchCardHolderInfo(bankAcc.getCustId());
			CustIdGenerator.isCarderExist(cardHolder, bankAcc.getCustId());
			bankAcc.setResNo(cardHolder.getPerResNo());
			isBindBank = cardHolder.getIsBindBank();
		}
		if (userType == UserTypeEnum.NO_CARDER) {
			NoCardHolder noCardHolder = commonCacheService
					.searchNoCardHolderInfo(bankAcc.getCustId());
			CustIdGenerator.isNoCarderExist(noCardHolder, bankAcc.getCustId());
			bankAcc.setResNo(null);
			isBindBank = noCardHolder.getIsBindBank();
		}
		// 检查银行账户是否已存在
		checkCustBankCardIsExist(bankAcc);
		// 插入数据
		CustAccount custAcc = CustAccount.generateCustAccount(bankAcc);
		try {
			if ("1".equals(bankAcc.getIsDefaultAccount())) {
				// 设置消费者所有银行账号不是默认账号
				custAccountMapper
						.setDefaultAccToGeneral(custAcc.getPerCustId());
				// 设置该账号为默认
				custAcc.setIsDefaultAccount(1);
			}

		} catch (Exception e) {
			e.printStackTrace();
			SystemLog.error(MOUDLENAME, "bindEntBank", "设置消费者所有银行账号不是默认账号失败：",
					e);
			throw new HsException(
					ErrorCodeEnum.SET_ACCOUT_NOT_DEFAULT_ERROR.getValue(),
					ErrorCodeEnum.SET_ACCOUT_NOT_DEFAULT_ERROR.getDesc()
							+ "：\r\n" + e);
		}
		try {
			//主键id
//			String id = CustIdGenerator.genSeqId("");
//			custAcc.setAccId(Long.valueOf(id));//页面 json不兼容15位以上长数字，暂时还是使用自增长
			custAccountMapper.insertSelective(custAcc);
		} catch (Exception e) {
			e.printStackTrace();
			SystemLog.error(MOUDLENAME, "bindEntBank", "绑定消费者银行帐号失败：", e);
			throw new HsException(ErrorCodeEnum.SAVE_ACCOUT_ERROR.getValue(),
					ErrorCodeEnum.SAVE_ACCOUT_ERROR.getDesc() + "：\r\n" + e);
		}
		if (null == isBindBank || 0 == isBindBank) {
			if (userType == UserTypeEnum.CARDER) {
				CardHolder cardHolder = new CardHolder();
				cardHolder.setPerCustId(bankAcc.getCustId());
				cardHolder.setIsBindBank(1);
				cardHolder.setUpdateDate(StringUtils.getNowTimestamp());
				cardHolder.setUpdatedby(bankAcc.getCustId());
				commonCacheService.updateCardHolderInfo(cardHolder);
			}
			if (userType == UserTypeEnum.NO_CARDER) {
				NoCardHolder noCardHolder = new NoCardHolder();
				noCardHolder.setPerCustId(bankAcc.getCustId());
				noCardHolder.setIsBindBank(1);
				noCardHolder.setUpdateDate(StringUtils.getNowTimestamp());
				noCardHolder.setUpdatedby(bankAcc.getCustId());
				commonCacheService.updateNoCardHolderInfo(noCardHolder);
			}
		}
	}

	/**
	 * 解绑银行账户
	 * 
	 * @param accId
	 *            银行账户编号（主键 序列号）
	 * @param userType
	 *            用户类型
	 * @throws HsException
	 * @see com.gy.hsxt.uc.as.api.common.IUCAsBankAcctInfoService#unBindBank(java.lang.Long,
	 *      com.gy.hsxt.uc.as.api.enumerate.UserTypeEnum)
	 */
	@Override
	public void unBindBank(Long accId, String userType) throws HsException {
		if (isBlank(accId)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"帐户ID为空,accId=[" + accId + "],userType=[" + userType + "]");
		}
		if (isBlank(userType)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"用户类型为空,accId=[" + accId + "],userType=[" + userType + "]");
		}
		if (userType.equals(UserTypeEnum.ENT.getType())) {
			try {
				EntAccount entAccount = entAccountMapper
						.selectByPrimaryKey(accId);
				CustIdGenerator.isEntBankCardExist(entAccount, accId);
				entAccountMapper.deleteByPrimaryKey(accId);
				int count = entAccountMapper.selectCounts(
						entAccount.getEntCustId(), null);// ,
															// entAccount.getBankAccNo()
				if (0 == count) {
					EntStatusInfo entStatusInfo = new EntStatusInfo();
					entStatusInfo.setEntCustId(entAccount.getEntCustId());
					entStatusInfo.setIsBindBank(0);
					entStatusInfo.setUpdateDate(StringUtils.getNowTimestamp());
					entStatusInfo.setUpdatedby(entStatusInfo.getEntCustId());
					commonCacheService.updateEntStatusInfo(entStatusInfo,
							entStatusInfo.getEntCustId());
				}
			} catch (HsException e) {
				SystemLog.error(MOUDLENAME, "unBindBank", "accId[" + accId
						+ "],解绑企业银行卡异常：\r\n", e);
			}
		} else if (userType.equals(UserTypeEnum.CARDER.getType())
				|| userType.equals(UserTypeEnum.NO_CARDER.getType())) {
			try {
				CustAccount custAccount = custAccountMapper
						.selectByPrimaryKey(accId);
				CustIdGenerator.isCustBankCardExist(custAccount, accId);
				custAccountMapper.deleteByPrimaryKey(accId);
				int count = custAccountMapper.selectCounts(
						custAccount.getPerCustId(), null); // custAccount.getBankAccNo()
				if (0 == count) {// 设置绑定银行标识为0 未绑定
					if (userType.equals(UserTypeEnum.CARDER.getType())) {
						CardHolder cardHolder = new CardHolder();
						cardHolder.setPerCustId(custAccount.getPerCustId());
						cardHolder.setIsBindBank(0);
						cardHolder.setUpdateDate(StringUtils.getNowTimestamp());
						cardHolder.setUpdatedby(custAccount.getPerCustId());
						commonCacheService.updateCardHolderInfo(cardHolder);
					}
					if (userType.equals(UserTypeEnum.NO_CARDER.getType())) {
						NoCardHolder noCardHolder = new NoCardHolder();
						noCardHolder.setPerCustId(custAccount.getPerCustId());
						noCardHolder.setIsBindBank(0);
						noCardHolder.setUpdateDate(StringUtils
								.getNowTimestamp());
						noCardHolder.setUpdatedby(custAccount.getPerCustId());
						commonCacheService.updateNoCardHolderInfo(noCardHolder);
					}
				}
			} catch (HsException e) {
				SystemLog.error(MOUDLENAME, "unBindBank", "accId[" + accId
						+ "],userType[" + userType + "],解除消费者银行卡异常", e);
			}
		} else {
			throw new HsException(ErrorCodeEnum.PARAM_IS_ILLEGAL.getValue(),
					"用户类型不支持,accId=[" + accId + "],userType=[" + userType + "]");
		}
	}

	/**
	 * 设置默认银行账户
	 * 
	 * @param custId
	 *            客户号（持卡人、非持卡人、企业）
	 * @param userType
	 *            用户类型
	 * @param accId
	 *            银行账户编号（主键 序列号）
	 * @throws HsException
	 * @see com.gy.hsxt.uc.as.api.common.IUCAsBankAcctInfoService#setDefaultBank(java.lang.String,
	 *      com.gy.hsxt.uc.as.api.enumerate.UserTypeEnum, java.lang.String)
	 */
	@Transactional
	@Override
	public void setDefaultBank(String custId, String userType, Long accId)
			throws HsException {
		// 验证数据
		if (isBlank(custId)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"客户号不能为空");
		}
		if (isBlank(accId)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"帐号ID不能为空");
		}
		if (isBlank(userType)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"用户类型不能为空");
		}
		// 设置默认帐户
		if (userType.equals(UserTypeEnum.ENT.getType())) {
			checkEntBankCarderCustId(custId, accId);
			setEntDefaultAcct(custId, accId);
			return;
		}
		if (userType.equals(UserTypeEnum.CARDER.getType())
				|| userType.equals(UserTypeEnum.NO_CARDER.getType())) {
			checkBankCarderCustId(custId, accId);
			setCustDefaultAcct(custId, accId);
			return;
		}
		SystemLog
				.debug(MOUDLENAME, "setDefaultBank", "非法用户类型,用户类型：" + userType);
		throw new HsException(ErrorCodeEnum.PARAM_IS_ILLEGAL.getValue(),
				"非法用户类型:" + userType);
	}

	/**
	 * 检查消费者的客户号是否吻合
	 * 
	 * @param custId
	 * @param accId
	 * @throws HsException
	 */
	private void checkBankCarderCustId(String custId, Long accId)
			throws HsException {
		CustAccount custAccount = null;
		try {
			custAccount = custAccountMapper.selectByPrimaryKey(accId);
			String perCustId = StringUtils.nullToEmpty(custAccount
					.getPerCustId());
			if (!custId.equals(perCustId)) {
				throw new HsException(
						ErrorCodeEnum.CUSTID_NOT_MATCH.getValue(), perCustId
								+ ErrorCodeEnum.CUSTID_NOT_MATCH.getDesc()
								+ ",custId[" + custId + "]");
			}
		} catch (HsException e) {
			SystemLog.error(MOUDLENAME, "checkBankCarderCustId",
					"检查消费者银行账户信息出错：custId[" + custId + "],accId[" + accId
							+ "]\r\n", e);
			throw e;
		} catch (Exception e) {
			SystemLog.error(MOUDLENAME, "checkBankCarderCustId",
					"检查消费者银行账户信息出错：custId[" + custId + "],accId[" + accId
							+ "]\r\n", e);
			throw new HsException(ErrorCodeEnum.BANK_ACCT_NOT_FOUND.getValue(),
					ErrorCodeEnum.BANK_ACCT_NOT_FOUND.getDesc());
		}

	}

	/**
	 * 检查企业的客户号是否吻合
	 * 
	 * @param custId
	 * @param accId
	 * @throws HsException
	 */
	private void checkEntBankCarderCustId(String custId, Long accId)
			throws HsException {
		EntAccount entAccount = null;
		try {
			entAccount = entAccountMapper.selectByPrimaryKey(accId);
			String entCustId = StringUtils.nullToEmpty(entAccount
					.getEntCustId());
			if (!custId.equals(entCustId)) {
				throw new HsException(
						ErrorCodeEnum.CUSTID_NOT_MATCH.getValue(), entCustId
								+ ErrorCodeEnum.CUSTID_NOT_MATCH.getDesc()
								+ ",custId[" + custId + "]");
			}
		} catch (HsException e) {
			SystemLog.error(MOUDLENAME, "checkEntBankCarderCustId",
					"检查企业银行账户信息出错：custId[" + custId + "],accId[" + accId
							+ "]\r\n", e);
			throw e;
		} catch (Exception e) {
			SystemLog.error(MOUDLENAME, "checkEntBankCarderCustId",
					"检查企业银行账户信息出错：custId[" + custId + "],accId[" + accId
							+ "]\r\n", e);
			throw new HsException(ErrorCodeEnum.BANK_ACCT_NOT_FOUND.getValue(),
					ErrorCodeEnum.BANK_ACCT_NOT_FOUND.getDesc());
		}

	}

	/**
	 * 设置企业默认银行账户
	 * 
	 * @param custId
	 * @param accId
	 */
	private void setEntDefaultAcct(String custId, Long accId) {
		// 设置企业所有银行账号不是默认账号
		entAccountMapper.setDefaultAccToGeneral(custId);
		// 设置此银行账号为企业默认账户
		EntAccount entAccount = new EntAccount();
		entAccount.setIsDefaultAccount(1);
		entAccount.setAccId(accId);
		entAccountMapper.updateByPrimaryKeySelective(entAccount);
	}

	/**
	 * 设置消费者默认银行账户
	 * 
	 * @param custId
	 * @param accId
	 */
	private void setCustDefaultAcct(String custId, Long accId) {
		// 设置消费者所有银行账号不是默认账号
		custAccountMapper.setDefaultAccToGeneral(custId);
		// 设置此银行账号为消费者默认账户
		CustAccount custAccount = new CustAccount();
		custAccount.setIsDefaultAccount(1);
		custAccount.setAccId(accId);
		custAccountMapper.updateByPrimaryKeySelective(custAccount);
	}

	/**
	 * 修改银行账户转账状态
	 * 
	 * @param accId
	 *            银行账户编号（主键 序列号）
	 * @param isValidAccount
	 *            是否已验证账户1:是 0：否
	 * @param userType
	 *            用户类型
	 * @throws HsException
	 * @see com.gy.hsxt.uc.as.api.common.IUCAsBankAcctInfoService#updateTransStatus(java.lang.Long,
	 *      java.lang.String, com.gy.hsxt.uc.as.api.enumerate.UserTypeEnum)
	 */
	@Override
	public void updateTransStatus(Long accId, String isValidAccount,
			String userType) throws HsException {
		// 数据验证
		if (isBlank(accId)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"帐户ID不能为空");
		}

		if (isBlank(isValidAccount)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"是否使用不能为空");
		}
		if (isBlank(userType)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"用户类型不能为空");
		}
		// 持卡人
		if (userType.equals(UserTypeEnum.CARDER.getType())
				|| userType.equals(UserTypeEnum.NO_CARDER.getType())) {
			updateCustAcctTransStatus(accId, isValidAccount);
			return;
		}
		// 操作员
		if (userType.equals(UserTypeEnum.ENT.getType())) {
			updateEntAcctTransStatus(accId, isValidAccount);
			return;
		}
		SystemLog.debug(MOUDLENAME, "updateTransStatus", "非法用户类型,用户类型："
				+ userType);
		throw new HsException(ErrorCodeEnum.PARAM_IS_ILLEGAL.getValue(),
				"非法用户类型");

	}

	/**
	 * 修改企业银行账户转账状态
	 * 
	 * @param accId
	 *            帐户ID
	 * @param useFlag
	 *            是否使用
	 */
	private void updateEntAcctTransStatus(Long accId, String useFlag) {
		EntAccount entAccount = new EntAccount();
		entAccount.setIsValidAccount(Integer.parseInt(useFlag));
		entAccount.setAccId(accId);
		entAccountMapper.updateByPrimaryKeySelective(entAccount);
	}

	/**
	 * 修改消费者银行账户转账状态
	 * 
	 * @param accId
	 *            帐户ID
	 * @param useFlag
	 *            是否使用
	 */
	private void updateCustAcctTransStatus(Long accId, String useFlag) {
		CustAccount custAccount = new CustAccount();
		custAccount.setIsValidAccount(Integer.parseInt(useFlag));
		custAccount.setAccId(accId);
		custAccountMapper.updateByPrimaryKeySelective(custAccount);
	}

	/**
	 * 查询绑定的银行账户列表
	 * 
	 * @param custId
	 *            客户号（持卡人、非持卡人、企业）
	 * @param userType
	 *            用户类型
	 * @return
	 * @throws HsException
	 * @see com.gy.hsxt.uc.as.api.common.IUCAsBankAcctInfoService#listBanksByCustId(java.lang.String,
	 *      com.gy.hsxt.uc.as.api.enumerate.UserTypeEnum)
	 */
	@Override
	public List<AsBankAcctInfo> listBanksByCustId(String custId, String userType)
			throws HsException {
		// 数据验证
		if (isBlank(custId)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"客户号不能为空");
		}
		if (isBlank(userType)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"用户类型不能为空");
		}
		// 持卡人
		if (userType.equals(UserTypeEnum.CARDER.getType())
				|| userType.equals(UserTypeEnum.NO_CARDER.getType())) {
			return listCustBanks(custId);
		}
		// 企业
		if (userType.equals(UserTypeEnum.ENT.getType())) {
			return listEntBanks(custId);
		}
		SystemLog.debug(MOUDLENAME, "listBanksByCustId", "非法用户类型,用户类型："
				+ userType);
		throw new HsException(ErrorCodeEnum.PARAM_IS_ILLEGAL.getValue(),
				"非法用户类型");
	}

	/**
	 * 查询消费者的银行帐户列表
	 * 
	 * @param custId
	 *            客户号
	 * @return
	 */
	private List<AsBankAcctInfo> listCustBanks(String custId) {
		List<CustAccount> list = custAccountMapper.listAccountByCustId(custId);
		if (list == null || list.isEmpty()) {
			return null;
		}
		List<AsBankAcctInfo> bankAcctList = new ArrayList<AsBankAcctInfo>();
		for (CustAccount acc : list) {
			bankAcctList.add(acc.generateAsBankAcctInfo());
		}
		return bankAcctList;
	}

	/**
	 * 查询企业的银行帐户列表
	 * 
	 * @param custId
	 *            客户号
	 * @return
	 */
	private List<AsBankAcctInfo> listEntBanks(String custId) {
		List<EntAccount> list = entAccountMapper.listAccountByCustId(custId);
		if (list == null || list.isEmpty()) {
			return null;
		}
		List<AsBankAcctInfo> bankAcctList = new ArrayList<AsBankAcctInfo>();
		for (EntAccount acc : list) {
			bankAcctList.add(acc.generateAsBankAcctInfo());
		}
		return bankAcctList;
	}

	/**
	 * 快捷支付签约号同步
	 * 
	 * @param qkBank
	 *            快捷账户信息
	 * @param userType
	 *            用户类型
	 * @throws HsException
	 * @see com.gy.hsxt.uc.as.api.common.IUCAsBankAcctInfoService#setQkBank(com.gy.hsxt.uc.as.bean.common.AsQkBank,
	 *      com.gy.hsxt.uc.as.api.enumerate.UserTypeEnum)
	 */
	@Override
	@Transactional
	public void setQkBank(AsQkBank qkBank, String userType) throws HsException {

		// 数据验证
		if (isBlank(qkBank)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"qkBank快捷支付银行信息不能为空");
		}
		if (isBlank(userType)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"用户类型不能为空");
		}
		// if (isBlank(qkBank.getResNo())) {
		// log.debug("资源号不能为空");
		// throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
		// "资源号不能为空");
		// }
		if (isBlank(qkBank.getBankCode())) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"银行代码不能为空");
		}
		if (isBlank(qkBank.getBankCardNo())) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"银行卡号不能为空");
		}

		if (userType.equals(UserTypeEnum.CARDER.getType())) {
			setCustQkBank(qkBank, UserTypeEnum.CARDER);
			return;
		} else if (userType.equals(UserTypeEnum.NO_CARDER.getType())) {
			setCustQkBank(qkBank, UserTypeEnum.NO_CARDER);
			return;
		} else if (userType.equals(UserTypeEnum.ENT.getType())) {
			setEntQkBank(qkBank);
			return;
		} else {
			SystemLog.debug(MOUDLENAME, "setQkBank", "非法用户类型,用户类型：" + userType);
			throw new HsException(ErrorCodeEnum.PARAM_IS_ILLEGAL.getValue(),
					"非法用户类型");
		}

	}

	/**
	 * 设置企业快捷支付银行帐户信息
	 * 
	 * @param qkBank
	 *            银行帐户信息
	 */
	private void setEntQkBank(AsQkBank qkBank) {
		String entCustId = qkBank.getCustId();
		// 验证企业是否存在
		AsEntStatusInfo entStatusInfo = asEntService
				.searchEntStatusInfo(entCustId);
		qkBank.setResNo(entStatusInfo.getEntResNo());

		List<AsQkBank> list = listEntQkBanks(entCustId);
		;

		// 检查快捷支付卡账号是否已存在，//账号已存在则修改已有，否则新增记录
		boolean isInsert = true; // 账号已存在则修改，否则新增记录
		if (null == list || list.isEmpty()) {
			isInsert = true;
		} else {
			for (AsQkBank bean : list) {
				if (bean.getCustId().equals(entCustId)
						&& bean.getBankCardNo().equals(qkBank.getBankCardNo())) {
					isInsert = false;
					qkBank.setAccId(bean.getAccId());
					break;
				}
			}
		}

		EntQkAccount entQkAcc = EntQkAccount.generateCustQkAccount(qkBank);
		if (isInsert) {
			// 插入数据
			//主键id
//			String id = CustIdGenerator.genSeqId("");
//			entQkAcc.setAccId(Long.valueOf(id));//页面 json不兼容15位以上长数字，暂时还是使用自增长
			entQkAccountMapper.insertSelective(entQkAcc);
		} else {
			entQkAccountMapper.updateByPrimaryKeySelective(entQkAcc);
		}
	}

	/**
	 * 设置消费者快捷支付银行帐户信息
	 * 
	 * @param qkBank
	 *            银行帐户信息
	 * @param userType
	 *            用户类型
	 */
	private void setCustQkBank(AsQkBank qkBank, UserTypeEnum userType) {
		// 验证消费者是否存在
		if (userType == UserTypeEnum.CARDER) {
			AsCardHolder cardHolder = cardHolderService
					.searchCardHolderInfoByCustId(qkBank.getCustId());
			qkBank.setResNo(cardHolder.getPerResNo());
		}
		if (userType == UserTypeEnum.NO_CARDER) {
			noCardHolderService.searchNoCardHolderInfoByCustId(qkBank
					.getCustId());
			qkBank.setResNo(null);
		}
		String custId = qkBank.getCustId();
		List<AsQkBank> list = listCustQkBanks(custId);

		// 检查快捷支付卡账号是否已存在，//账号已存在则修改已有，否则新增记录
		boolean isInsert = true; // 账号已存在则修改，否则新增记录
		if (null == list || list.isEmpty()) {
			isInsert = true;
		} else {
			for (AsQkBank bean : list) {
				if (bean.getCustId().equals(custId)
						&& bean.getBankCardNo().equals(qkBank.getBankCardNo())) {
					isInsert = false;
					qkBank.setAccId(bean.getAccId());
					break;
				}
			}
		}

		// 插入数据
		CustQkAccount custQkAcc = CustQkAccount.generateCustQkAccount(qkBank);
		if (isInsert) {
			//主键id
//			String id = CustIdGenerator.genSeqId("");
//			custQkAcc.setAccId(Long.valueOf(id));//页面 json不兼容15位以上长数字，暂时还是使用自增长
			custQkAccountMapper.insertSelective(custQkAcc);
		} else {
			custQkAccountMapper.updateByPrimaryKeySelective(custQkAcc);
		}
	}

	/**
	 * 查询绑定的快捷银行账户列表
	 * 
	 * @param custId
	 *            客户号（持卡人、非持卡人、企业）
	 * @param userType
	 *            用户类型
	 * @return
	 * @throws HsException
	 * @see com.gy.hsxt.uc.as.api.common.IUCAsBankAcctInfoService#listQkBanksByCustId(java.lang.String,
	 *      com.gy.hsxt.uc.as.api.enumerate.UserTypeEnum)
	 */
	public List<AsQkBank> listQkBanksByCustId(String custId, String userType)
			throws HsException {
		// 数据验证
		if (isBlank(userType)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"客户号为空");
		}
		if (isBlank(userType)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"用户类型为空");
		}

		if (userType.equals(UserTypeEnum.CARDER.getType())
				|| userType.equals(UserTypeEnum.NO_CARDER.getType())) {
			return listCustQkBanks(custId);
		}
		if (userType.equals(UserTypeEnum.ENT.getType())) {
			return listEntQkBanks(custId);
		}
		SystemLog.debug(MOUDLENAME, "listQkBanksByCustId", "非法用户类型,用户类型："
				+ userType);
		throw new HsException(ErrorCodeEnum.PARAM_IS_ILLEGAL.getValue(),
				"非法用户类型,用户类型：" + userType);
	}

	/**
	 * 查询消费者快捷支付列表
	 * 
	 * @param custId
	 *            客户号
	 * @return
	 */
	private List<AsQkBank> listCustQkBanks(String custId) {
		List<CustQkAccount> list = custQkAccountMapper
				.listAccountByCustId(custId);
		List<AsQkBank> bankAcctList = new ArrayList<AsQkBank>();
		for (CustQkAccount acc : list) {
			bankAcctList.add(acc.generateAsQkBank());
		}
		return bankAcctList;
	}

	/**
	 * 查询企业快捷支付列表
	 * 
	 * @param custId
	 *            客户号
	 * @return
	 */
	private List<AsQkBank> listEntQkBanks(String custId) {
		List<EntQkAccount> list = entQkAccountMapper
				.listAccountByCustId(custId);
		List<AsQkBank> bankAcctList = new ArrayList<AsQkBank>();
		for (EntQkAccount acc : list) {
			bankAcctList.add(acc.generateAsQkBank());
		}
		return bankAcctList;
	}

	/**
	 * 查询银行账户信息 通过银行账户ID
	 * 
	 * @param accId
	 *            银行账户编号（主键 序列号）
	 * @param userType
	 *            用户类型
	 * @return
	 * @throws HsException
	 * @see com.gy.hsxt.uc.as.api.common.IUCAsBankAcctInfoService#findBankAccByAccId(java.lang.Long,
	 *      com.gy.hsxt.uc.as.api.enumerate.UserTypeEnum)
	 */
	@Override
	public AsBankAcctInfo findBankAccByAccId(Long accId, String userType)
			throws HsException {
		// 数据验证
		if (isBlank(userType)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"客户号为空");
		}
		if (isBlank(userType)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"用户类型为空");
		}
		// 如果查询消费者银行账户信息
		if (userType.equals(UserTypeEnum.CARDER.getType())
				|| userType.equals(UserTypeEnum.NO_CARDER.getType())) {
			CustAccount acctInfo = custAccountMapper.selectByPrimaryKey(accId);
			if (acctInfo == null) {
				return null;
			}
			return acctInfo.generateAsBankAcctInfo();
		}

		// 如果查询企业银行账户信息
		if (userType.equals(UserTypeEnum.ENT.getType())) {
			EntAccount acctInfo = entAccountMapper.selectByPrimaryKey(accId);
			if (acctInfo == null) {
				return null;
			}
			String custId = StringUtils.nullToEmpty(acctInfo.getEntCustId());
			AsEntBaseInfo entBaseInfo = asEntService.searchEntBaseInfo(custId);
			acctInfo.setEntName(StringUtils.nullToEmpty(entBaseInfo
					.getEntName()));
			acctInfo.setEntNameEn(StringUtils.nullToEmpty(entBaseInfo
					.getEntNameEn()));
			return acctInfo.generateAsBankAcctInfo();
		}
		SystemLog.debug(MOUDLENAME, "findBankAccByAccId", "非法用户类型,用户类型："
				+ userType);
		throw new HsException(ErrorCodeEnum.PARAM_IS_ILLEGAL.getValue(),
				"非法用户类型,用户类型：" + userType);

	}

	/**
	 * 查询默认银行账户
	 * 
	 * @param custId
	 *            客户号（持卡人、非持卡人、企业）
	 * @param userType
	 *            用户类型
	 * @return
	 * @throws HsException
	 * @see com.gy.hsxt.uc.as.api.common.IUCAsBankAcctInfoService#searchDefaultBankAcc(java.lang.String,
	 *      com.gy.hsxt.uc.as.api.enumerate.UserTypeEnum)
	 */
	@Override
	public AsBankAcctInfo searchDefaultBankAcc(String custId, String userType)
			throws HsException {
		List<AsBankAcctInfo> accInfos = listBanksByCustId(custId, userType);
		if (null != accInfos) {
			for (AsBankAcctInfo acctInfo : accInfos) {
				if ("1".equals(acctInfo.getIsDefaultAccount())) {
					return acctInfo;
				}
			}
		}
		return null;
	}

	@Override
	public void updateBankAcctDefaultInfo(String custId, Long accId,
			Integer isDefaultAccount, String userType) throws HsException {
		// 检查入参
		validBankAcctDefaultParams(custId, accId, isDefaultAccount, userType);
		// 更新默认账户标识
		updateBankAcctInfoByUserType(custId, accId, isDefaultAccount, userType);
	}

	/**
	 * 根据用户类型更新银行账户信息（企业和消费者）
	 * 
	 * @param acctInfo
	 * @param userType
	 *            非持卡人：1 持卡人：2 企业：4
	 * @throws HsException
	 */
	private void updateBankAcctInfoByUserType(String custId, Long accId,
			Integer isDefaultAccount, String userType) throws HsException {
		if (1 == isDefaultAccount) {
			setDefaultBank(custId, userType, accId);
		} else if (0 == isDefaultAccount) {
			if (UserTypeEnum.NO_CARDER.getType().equals(userType)
					|| UserTypeEnum.CARDER.getType().equals(userType)) {// 更新持卡人或非持卡人银行账户信息
				custAccountMapper.setDefaultAccToGeneral(custId);
			} else if (UserTypeEnum.ENT.getType().equals(userType)) {// 更新企业银行账户信息
				entAccountMapper.setDefaultAccToGeneral(custId);
			} else {
				throw new HsException(
						ErrorCodeEnum.USERTYPE_IS_ILLEGAL.getValue(),
						ErrorCodeEnum.USERTYPE_IS_ILLEGAL.getDesc());
			}
		} else {
			throw new HsException(
					ErrorCodeEnum.IS_DEFAULT_ACCOUNT_ILLEGAL.getValue(),
					ErrorCodeEnum.IS_DEFAULT_ACCOUNT_ILLEGAL.getDesc());
		}
	}

	@Override
	public void setDefaultQkBank(String custId, String userType, Long accId)
			throws HsException {
		// TODO Auto-generated method stub

	}

	/**
	 * 删除快捷银行账户
	 * 
	 * @param accId
	 *            快捷银行账户ID
	 * @param userType
	 *            用户类型 非持卡人：1 持卡人：2 企业：4
	 * @throws HsException
	 * @see com.gy.hsxt.uc.as.api.common.IUCAsBankAcctInfoService#unBindQkBank(java.lang.Long,
	 *      java.lang.String)
	 */
	@Override
	public void unBindQkBank(Long accId, String userType) throws HsException {
		if (isBlank(accId)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"快捷银行帐户ID为空");
		}
		if (isBlank(userType)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"用户类型为空");
		}
		deleteQkBankInfo(accId, userType);
	}

	/**
	 * 根据快捷银行账户ID和用户类型删除快捷银行账户信息
	 * 
	 * @param accId
	 *            快捷银行账户ID
	 * @param userType
	 *            用户类型 非持卡人：1 持卡人：2 企业：4
	 * @throws HsException
	 */
	private void deleteQkBankInfo(Long accId, String userType)
			throws HsException {
		if (userType.equals(UserTypeEnum.ENT.getType())) {
			try {
				entQkAccountMapper.deleteByPrimaryKey(accId);
			} catch (Exception e) {
				throw new HsException(
						ErrorCodeEnum.DELETE_ENTBANK_ERROR.getValue(),
						e.getMessage());
			}
		} else if (userType.equals(UserTypeEnum.CARDER.getType())
				|| userType.equals(UserTypeEnum.NO_CARDER.getType())) {
			try {
				custQkAccountMapper.deleteByPrimaryKey(accId);
			} catch (Exception e) {
				throw new HsException(
						ErrorCodeEnum.DELETE_COSUMERBANK_ERROR.getValue(),
						e.getMessage());
			}
		} else {
			SystemLog.debug(MOUDLENAME, "deleteQkBankInfo", "非法用户类型,用户类型："
					+ userType);
			throw new HsException(ErrorCodeEnum.PARAM_IS_ILLEGAL.getValue(),
					userType + "用户类型不正确");
		}
	}

	/**
	 * 检查入参
	 * 
	 * @param custId
	 * @param accId
	 * @param isDefaultAccount
	 * @param userType
	 * @throws HsException
	 */
	private void validBankAcctDefaultParams(String custId, Long accId,
			Integer isDefaultAccount, String userType) throws HsException {
		if (StringUtils.isBlank(userType)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"用户类型为空");
		}
		if (null == accId) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"银行账户编号为空");
		}
		if (StringUtils.isBlank(custId)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"客户号为空");
		}
		if (null == isDefaultAccount) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"是否默认账户标识为空");
		}
	}

	/**
	 * 通过企业的互生号查询企业的银行账户信息
	 * 
	 * @param resNo
	 *            企业互生号
	 * @return 企业的银行账户信息
	 * @throws HsException
	 * @see com.gy.hsxt.uc.bs.api.common.IUCBsBankAcctInfoService#searchDefaultBankAcc(java.lang.String)
	 */
	@Override
	public AsBankAcctInfo searchDefaultBankAcc(String resNo) throws HsException {
		if (StringUtils.isBlank(resNo)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"互生号为空");
		}
		String rno = resNo.trim();
		String userType = generator.getUserTypeByResNo(rno);
		String custId = "";
		if (UserTypeEnum.CARDER.getType().equals(userType)) {
			custId = commonCacheService.findCustIdByResNo(rno);
			CustIdGenerator.isCarderExist(custId, rno);
		} else if (UserTypeEnum.ENT.getType().equals(userType)) {
			custId = commonCacheService.findEntCustIdByEntResNo(rno);
			if (StringUtils.isBlank(custId)) {
				throw new HsException(
						ErrorCodeEnum.ENT_IS_NOT_FOUND.getValue(),
						ErrorCodeEnum.ENT_IS_NOT_FOUND.getDesc() + ",entResNo["
								+ rno + "]");
			}
		} else {
			throw new HsException(ErrorCodeEnum.USERTYPE_IS_ILLEGAL.getValue(),
					ErrorCodeEnum.USERTYPE_IS_ILLEGAL.getDesc());
		}
		AsBankAcctInfo acctInfo = searchDefaultBankAcc(custId, userType);
		return acctInfo;
	}

}
