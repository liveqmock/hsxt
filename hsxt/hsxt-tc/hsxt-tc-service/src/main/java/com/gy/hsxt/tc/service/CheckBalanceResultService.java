/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */

package com.gy.hsxt.tc.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.gy.hsxt.ac.api.IAccountEntryService;
import com.gy.hsxt.ac.bean.AccountEntry;
import com.gy.hsxt.common.constant.BizGroup;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.guid.GuidAgent;
import com.gy.hsxt.common.utils.DateUtil;
import com.gy.hsxt.tc.api.ITcCheckBalanceResultService;
import com.gy.hsxt.tc.bean.CheckBalance;
import com.gy.hsxt.tc.bean.CheckBalanceResult;
import com.gy.hsxt.tc.bean.TcCheckBalance;
import com.gy.hsxt.tc.bean.TcCheckBalance.TcCheckBalanceStatus;
import com.gy.hsxt.tc.bean.TcCheckBalanceResult;
import com.gy.hsxt.tc.enums.TcErrorCode;
import com.gy.hsxt.tc.mapper.CheckBalanceResultMapper;
import com.gy.hsxt.tc.service.util.SystemParameterUtil;

/**
 * 调账申请实现
 * 
 * @Package: com.gy.hsxt.tc.api
 * @ClassName: CheckBalanceResultService
 * @Description: TODO
 * 
 * @author: lixuan
 * @date: 2016-03-16 上午11:17:35
 * @version V1.0
 */
@Service
public class CheckBalanceResultService implements ITcCheckBalanceResultService {
	@Autowired
	CheckBalanceResultMapper checkBalanceResultMapper;
	@Autowired
	CheckBalanceService checkBalanceService;
	@Autowired
	IAccountEntryService accountEntryService;
	static final String TRANS_TYPE_IN = "X10530";
	static final String TRANS_TYPE_OUT = "X10630";

	@Override
	public List<TcCheckBalanceResult> getCheckBalanceResult(String balanceId)  throws HsException{
		List<CheckBalanceResult> result = checkBalanceResultMapper
				.selectByBalanceId(balanceId);
		List<TcCheckBalanceResult> list = new ArrayList<>();
		for (CheckBalanceResult cb : result) {
			TcCheckBalanceResult tc = new TcCheckBalanceResult();
			BeanUtils.copyProperties(cb,tc);
			list.add(tc);
		}
		return list;
	}

	@Override
	// @Transactional
	public void addCheckBalanceResult(TcCheckBalanceResult cb) throws HsException{
		// 验证数据
		if (StringUtils.isBlank(cb.getCheckBalanceId())) {
			throw new HsException(TcErrorCode.PARAM_IS_REQUIRED.getCode(),
					"调账申请ID为空");
		}
		if (StringUtils.isBlank(cb.getChecker())) {
			throw new HsException(TcErrorCode.PARAM_IS_REQUIRED.getCode(),
					"操作员名称为空");
		}
//		if (StringUtils.isBlank(cb.getRemark())) {
//			throw new HsException(TcErrorCode.PARAM_IS_REQUIRED.getCode(),
//					"备注为空");
//		}
		if (cb.getCheckResult() == null) {
			throw new HsException(TcErrorCode.PARAM_IS_REQUIRED.getCode(),
					"审核结果为空");
		}
		if (cb.getCheckType() == null) {
			throw new HsException(TcErrorCode.PARAM_IS_REQUIRED.getCode(),
					"审核类型为空");
		}
		cb.setStatus(1);
		// 根据调账ID查找调账信息
		TcCheckBalance checkBalance = checkBalanceService
				.searchCheckBalanceById(cb.getCheckBalanceId());
		if (checkBalance == null) {
			throw new HsException(TcErrorCode.DATA_NO_FOUND.getCode(), "调账申请为空");
		}

		if (cb.getCheckType().intValue() == 2) {
			// 如果请求为复核，调账申请状态为初审成功
			if (checkBalance.getStatus().intValue() != TcCheckBalanceStatus.FIRST_CHECK_SUCCESS
					.getValue()) {
				throw new HsException(
						TcErrorCode.CHECK_BALANCE_STATUS_NOT_SUCCESS.getCode(),
						TcErrorCode.CHECK_BALANCE_STATUS_NOT_SUCCESS.getDesc()
								+ "， 调账状态：" + checkBalance.getStatus());
			}
		} else{
			// 如果调账申请状态不为未处理，则抛出异常
			if (checkBalance.getStatus().intValue() != TcCheckBalanceStatus.WAIT_FIRST_CHECK
					.getValue()) {
				throw new HsException(
						TcErrorCode.CHECK_BALANCE_STATUS_NOT_INIT.getCode(),
						TcErrorCode.CHECK_BALANCE_STATUS_NOT_INIT.getDesc()
								+ "， 调账状态：" + checkBalance.getStatus());
			}
		}

		// 保存审核结果
		CheckBalanceResult result = new CheckBalanceResult();
		BeanUtils.copyProperties(cb, result);
		result.setCheckDate(new Date());
		String no = SystemParameterUtil.getSystemNo();
		no = GuidAgent.getShortStringGuid(BizGroup.TC + no);
		System.out.println("生成的调账编号：" + no);
		result.setId(no);
		checkBalanceResultMapper.insertSelective(result);

		// 如果是复核
		if (cb.getCheckType().intValue() == TcCheckBalanceStatus.SECOND_CHECK_SUCCESS
				.getValue()) {
			// 如果复核通过，调用账务接口
			List<AccountEntry> list = new ArrayList<>();
			AccountEntry ae = new AccountEntry();
			ae.setAccType(checkBalance.getAcctType());
			ae.setCustID(checkBalance.getCustId());
			// 判断调账类型
			if (checkBalance.getCheckType().intValue() == 1) {
				ae.setAddAmount(String.valueOf(checkBalance.getAmount()));
				ae.setTransType(TRANS_TYPE_IN);
			} else {
				ae.setSubAmount(String.valueOf(checkBalance.getAmount()));
				ae.setTransType(TRANS_TYPE_OUT);
			}
			ae.setWriteBack(0);
			ae.setSysEntryNo(result.getCheckBalanceId());
			ae.setTransSys("TC");

			ae.setTransNo(result.getCheckBalanceId());
			ae.setCustType(checkBalance.getCustType());
			ae.setBatchNo(result.getCheckBalanceId());
			ae.setTransDate(DateUtil.getCurrentDateTime());
			ae.setFiscalDate(DateUtil.getCurrentDateTime());
			ae.setHsResNo(checkBalance.getResNo());
			list.add(ae);
			System.out.println(JSONObject.toJSONString(list));
			accountEntryService.actualAccount(list);
			int status = cb.getCheckResult() == 1 ? TcCheckBalanceStatus.SECOND_CHECK_SUCCESS
					.getValue() : TcCheckBalanceStatus.SECOND_CHECK_FAIL
					.getValue();
			// 更新申请状态
			checkBalanceService.updateCheckBalanceStatus(
					cb.getCheckBalanceId(), status);
		}
		else{
			// 如果是初审
			int status = cb.getCheckResult() == 1 ? TcCheckBalanceStatus.FIRST_CHECK_SUCCESS
					.getValue() : TcCheckBalanceStatus.FIRST_CHECK_FAIL
					.getValue();
			// 更新申请状态
			checkBalanceService.updateCheckBalanceStatus(
					cb.getCheckBalanceId(), status);
		}
	}

}
