/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.uc.common.service;

import static com.gy.hsxt.common.utils.StringUtils.isBlank;

import java.sql.Timestamp;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.DateUtil;
import com.gy.hsxt.common.utils.StringUtils;
import com.gy.hsxt.gp.api.IGPNotifyBingNoService;
import com.gy.hsxt.gp.bean.QuickPayBindingNo;
import com.gy.hsxt.lcs.bean.PayBank;
import com.gy.hsxt.lcs.client.LcsClient;
import com.gy.hsxt.uc.as.api.enumerate.ErrorCodeEnum;
import com.gy.hsxt.uc.as.bean.common.AsQkBank;
import com.gy.hsxt.uc.common.bean.CustAccount;
import com.gy.hsxt.uc.common.bean.CustQkAccount;
import com.gy.hsxt.uc.common.bean.EntAccount;
import com.gy.hsxt.uc.common.bean.EntQkAccount;
import com.gy.hsxt.uc.common.mapper.CustAccountMapper;
import com.gy.hsxt.uc.common.mapper.CustQkAccountMapper;
import com.gy.hsxt.uc.common.mapper.EntAccountMapper;
import com.gy.hsxt.uc.common.mapper.EntQkAccountMapper;
import com.gy.hsxt.uc.util.CustIdGenerator;

/**
 * 
 * 
 * @Package: com.gy.hsxt.uc.common.service
 * @ClassName: GPNotifyBingNoService
 * @Description: 支付网关接口
 * 
 * @author: tianxh
 * @date: 2015-12-11 下午2:52:37
 * @version V1.0
 */
@Service
public class GPNotifyBingNoService implements IGPNotifyBingNoService {
	@Autowired
	private CustQkAccountMapper custQkAccountMapper;
	@Autowired
	private CustAccountMapper custAccountMapper;
	@Autowired
	private EntQkAccountMapper entQkAccountMapper;
	@Autowired
	private EntAccountMapper entAccountMapper;
	@Autowired
	private UCAsBankAcctInfoService bankAcctInfoService;
//	@Autowired
//	private CustIdGenerator generator;
	
	@Autowired
	LcsClient lcsClient;

	/**
	 * GP-UC快捷支付签约号同步到用户中心
	 * 
	 * @param quickPayBindingNo
	 * @return
	 * @throws HsException
	 * @see com.gy.hsxt.gp.api.IGPNotifyBingNoService#notifyQuickPayBindingNo(com.gy.hsxt.gp.bean.QuickPayBindingNo)
	 */
	@Override
//	@Transactional(rollbackFor = Exception.class)
	public boolean notifyQuickPayBindingNo(QuickPayBindingNo quickPayBindingNo)
			throws HsException {
		boolean result = true;
		checkParams(quickPayBindingNo);		
		String custId = quickPayBindingNo.getCustId().trim();
		String userType = CustIdGenerator.getUserTypeByCustId(custId);
		if (isBlank(userType)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"custId["+custId+"]不能解析出用户类型");
		}
		AsQkBank qkBank = vo2bean(quickPayBindingNo);
		bankAcctInfoService.setQkBank(qkBank, userType);
		return result;
	}



	/**
	 * 检查输入参数是否为空
	 * 
	 * @param quickPayBindingNo
	 */
	private void checkParams(QuickPayBindingNo quickPayBindingNo)
			throws HsException {
		if (null == quickPayBindingNo) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"quickPayBindingNo is null");
		}
		if (StringUtils.isBlank(quickPayBindingNo.getCustId())) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"输入参数客户号为空");
		}
		if (StringUtils.isBlank(quickPayBindingNo.getBankId())) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"输入参数银行代码为空");
		}
		if (StringUtils.isBlank(quickPayBindingNo.getBankCardType())) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"输入参数借贷记标识为空");
		}
		if (StringUtils.isBlank(quickPayBindingNo.getBankCardNo())) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"输入参数银行卡号为空");
		}

	}

	AsQkBank vo2bean(QuickPayBindingNo vo) {
		String bankId=vo.getBankId();
		AsQkBank bean = new AsQkBank();
		bean.setBankCode(bankId);
		bean.setAmountSingleLimit(vo.getTransLimit());
		bean.setAmountTotalLimit(vo.getSumLimit());
		bean.setBankCardNo(vo.getBankCardNo());
		bean.setBankCardType(vo.getBankCardType());
		bean.setCustId(vo.getCustId());
		if(!StringUtils.isBlank(vo.getBindingNo())){
			bean.setSignNo(vo.getBindingNo());
		}
		Date eDate = vo.getExpireDate();
		if (eDate != null) {
			String dateStr = DateUtil.DateToString(eDate);
			bean.setSmallPayExpireDate(dateStr);
		}
		//处理银行名称为空时，通过lcs获取
		String bankName=vo.getBankName();
		if(StringUtils.isBlank(bankName)){
			try {
				PayBank payBank = lcsClient.queryPayBankByCode(bankId);
				bankName=payBank.getBankName();
			} catch (Exception e) {
				bankName="中国银联"+bankId;
				SystemLog.error("GPNotifyBingNoService", "vo2bean", bankId+"快捷支付卡获取银行名称失败", e);
//				throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
//						bankId+"输入参数银行名称为空,lcs获取失败。"+e.getMessage());
			}
		}
		bean.setBankName(bankName);
		

		return bean;
	}

	/**
	 * 更新消费者银行账户信息入库
	 * 
	 * @param quickPayBindingNo
	 */
	private boolean updateCustAccount(QuickPayBindingNo quickPayBindingNo,
			boolean isInsert) throws HsException {
		boolean result = true;
		String custId = quickPayBindingNo.getCustId().trim();
		// 消费者银行账户信息
		CustQkAccount custQkAccount = new CustQkAccount();
		custQkAccount.setBankCode(quickPayBindingNo.getBankId().trim());
		custQkAccount.setPerCustId(custId);
		custQkAccount.setBankSignNo(quickPayBindingNo.getBindingNo().trim());
		custQkAccount.setBankCardNo(quickPayBindingNo.getBankCardNo().trim());
		// bankCardType
		// 消费者快捷银行账户信息
		CustAccount custAccount = new CustAccount();
		custAccount.setBankCardType(quickPayBindingNo.getBankCardType());
		custAccount.setPerCustId(custId);
		custAccount.setBankAccNo(quickPayBindingNo.getBankCardNo().trim());
		custAccount.setBankCode(quickPayBindingNo.getBankId().trim());
		if (isInsert) {// 插入
			custQkAccount.setCreatedby(custId);
			custAccount.setCreatedby(custId);
			// 插入消费者银行账户快捷支付信息
			custQkAccountMapper.insertSelective(custQkAccount);
			// 插入消费者银行账户信息
			custAccountMapper.insertSelective(custAccount);
		} else {// 更新
			Timestamp now = StringUtils.getNowTimestamp();
			custQkAccount.setUpdatedby(custId);
			custAccount.setUpdatedby(custId);
			custQkAccount.setUpdateDate(now);
			custAccount.setUpdateDate(now);
			try {
				// 更新消费者银行账户信息
				custAccountMapper.updateByUniqueKeySelective(custAccount);
				// 更新消费者银行账户快捷支付信息
				custQkAccountMapper.updateByUniqueKeySelective(custQkAccount);
			} catch (HsException e) {
				e.printStackTrace();
				// throw new
				// HsException(ErrorCodeEnum.SYSOPER_UPDATE_ERROR.getValue(),
				// "更新消费者银行账户是失败 ["+e.getMessage()+",]"+e.getCause());
				result = false;
			}
		}
		return result;
	}

	private boolean updateCustQKAccount(QuickPayBindingNo quickPayBindingNo,
			boolean isInsert) throws HsException {
		boolean result = true;
		String custId = quickPayBindingNo.getCustId().trim();

		CustQkAccount custQkAccount = new CustQkAccount();
		custQkAccount.setBankCode(quickPayBindingNo.getBankId().trim());
		custQkAccount.setPerCustId(custId);
		custQkAccount.setBankSignNo(quickPayBindingNo.getBindingNo().trim());
		custQkAccount.setBankCardNo(quickPayBindingNo.getBankCardNo().trim());
		// bankCardType
		// custAccount.setBankCardType(quickPayBindingNo.getBankCardType());
		// 消费者快捷银行账户信息

		if (isInsert) {// 插入
			custQkAccount.setCreatedby(custId);

			// 插入消费者银行账户快捷支付信息
			custQkAccountMapper.insertSelective(custQkAccount);

		} else {// 更新
			Timestamp now = StringUtils.getNowTimestamp();
			custQkAccount.setUpdatedby(custId);
			custQkAccount.setUpdateDate(now);
			try {
				// 更新消费者银行账户快捷支付信息
				custQkAccountMapper.updateByUniqueKeySelective(custQkAccount);
			} catch (HsException e) {
				e.printStackTrace();
				// throw new
				// HsException(ErrorCodeEnum.SYSOPER_UPDATE_ERROR.getValue(),
				// "更新消费者银行账户是失败 ["+e.getMessage()+",]"+e.getCause());
				result = false;
			}
		}
		return result;
	}

	/**
	 * 更新企业银行账户信息入库
	 * 
	 * @param quickPayBindingNo
	 */
	private boolean updateEntAccount(QuickPayBindingNo quickPayBindingNo,
			boolean isInsert) throws HsException {
		boolean result = true;
		String custId = quickPayBindingNo.getCustId().trim();
		EntQkAccount entQkAccount = new EntQkAccount();
		entQkAccount.setBankCode(quickPayBindingNo.getBankId().trim());
		entQkAccount.setBankCardNo(quickPayBindingNo.getBankCardNo().trim());
		entQkAccount.setBankSignNo(quickPayBindingNo.getBindingNo().trim());
		entQkAccount.setEntCustId(quickPayBindingNo.getCustId().trim());
		EntAccount entAccount = new EntAccount();
		entAccount.setBankAccNo(quickPayBindingNo.getBankCardNo().trim());
		entAccount.setBankCardType(quickPayBindingNo.getBankCardType());
		entAccount.setEntCustId(custId);
		entAccount.setBankCode(quickPayBindingNo.getBankId().trim());
		try {
			if (isInsert) {// 插入
				entQkAccount.setCreatedby(custId);
				entAccount.setCreatedby(custId);
				// 插入企业银行账户快捷支付信息
				entQkAccountMapper.insertSelective(entQkAccount);
				// 插入企业银行账户信息
				entAccountMapper.insertSelective(entAccount);
			} else {// 更新
				Timestamp now = StringUtils.getNowTimestamp();
				entQkAccount.setUpdatedby(custId);
				entQkAccount.setUpdateDate(now);
				entAccount.setUpdatedby(custId);
				entAccount.setUpdateDate(now);
				// 更新企业银行账户快捷支付信息
				entQkAccountMapper.updateByPrimaryKeySelective(entQkAccount);
				// 更新企业银行账户信息
				entAccountMapper.updateByPrimaryKeySelective(entAccount);
			}

		} catch (HsException e) {
			e.printStackTrace();
			result = false;
			// throw new
			// HsException(ErrorCodeEnum.SYSOPER_UPDATE_ERROR.getValue(),
			// "更新企业银行账户是失败 ["+e.getMessage()+"],"+e.getCause());
		}
		return result;
	}
	
	public static void main(String[] args){
		String custId="905178280087169024";
		
		String userType = CustIdGenerator.getUserTypeByCustId(custId);
		
		System.out.println("userType=["+userType+"]");
		
	}
	
}
