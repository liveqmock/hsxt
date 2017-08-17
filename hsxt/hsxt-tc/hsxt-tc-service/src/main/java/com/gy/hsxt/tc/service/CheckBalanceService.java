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

import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.constant.BizGroup;
import com.gy.hsxt.common.constant.CustType;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.guid.GuidAgent;
import com.gy.hsxt.common.utils.HsResNoUtils;
import com.gy.hsxt.tc.api.ITcCheckBalanceService;
import com.gy.hsxt.tc.bean.CheckBalance;
import com.gy.hsxt.tc.bean.TcCheckBalance;
import com.gy.hsxt.tc.bean.TcCheckBalance.TcCheckBalanceStatus;
import com.gy.hsxt.tc.bean.TcCheckBalanceParam;
import com.gy.hsxt.tc.enums.TcErrorCode;
import com.gy.hsxt.tc.mapper.CheckBalanceMapper;
import com.gy.hsxt.tc.service.util.SystemParameterUtil;
import com.gy.hsxt.uc.as.api.consumer.IUCAsCardHolderService;
import com.gy.hsxt.uc.as.api.ent.IUCAsEntService;

/**
 * 调账申请实现
 * 
 * @Package: com.gy.hsxt.tc.api
 * @ClassName: CheckBalanceService
 * @Description: TODO
 * 
 * @author: lixuan
 * @date: 2016-03-16 上午11:17:35
 * @version V1.0
 */
@Service
public class CheckBalanceService implements ITcCheckBalanceService{

	@Autowired
	CheckBalanceMapper checkBalanceMapper;
	@Autowired
	IUCAsCardHolderService carderService;
	@Autowired
	IUCAsEntService entService;
	
	@Override
	public TcCheckBalance searchCheckBalanceById(String id){
		return checkBalanceMapper.selectByPrimaryKey(id);
	}
	
	@Override
	public PageData<TcCheckBalance> searchCheckBalances(TcCheckBalanceParam param) throws HsException {
		// TODO Auto-generated method stub
		CheckBalance cb = new CheckBalance();
//		cb.setAcctType(param.get)\
		cb.setResNo(param.getResNo());
		if(param.getStatus() != null){
			cb.setStatusStr(param.getStatus() + "");
		}
		else{
			cb.setStatusStr(param.getStatusStr());
		}
		cb.setPageSize(param.getPage().getPageSize());
		cb.setCurPage(param.getPage().getCurPage());
		if(StringUtils.isNotBlank(param.getStartDate())){
			cb.setStartDate(param.getStartDate() + " 00:00:00");
		}
		if(StringUtils.isNotBlank(param.getEndDate())){
			cb.setEndDate(param.getEndDate() + " 23:59:59");
		}
		int count = checkBalanceMapper.countCheckBalances(cb);
		PageData<TcCheckBalance> pd = new PageData<>();
		pd.setCount(count);
		// 如果查询记录数为0，直接返回结果
		if(count == 0){
			return pd;
		}
		
		List<CheckBalance> list = checkBalanceMapper.selectByCondition( cb);
		List<TcCheckBalance> result = new ArrayList<>();
		for(CheckBalance cb1 : list){
			TcCheckBalance tc = new TcCheckBalance();
			BeanUtils.copyProperties(cb1,tc);
			result.add(tc);
		}
		pd.setResult(result);
		return pd;
	}

	@Override
	public void addCheckBalance(TcCheckBalance balance) throws HsException{
		// 验证数据
		if(StringUtils.isBlank(balance.getAcctType())){
			throw new HsException(TcErrorCode.PARAM_IS_REQUIRED.getCode(),"账户类型必填");
		}
		if(balance.getAmount() == null){
			throw new HsException(TcErrorCode.PARAM_IS_REQUIRED.getCode(),"调账金额必填");
		}
		if(balance.getName() == null){
			throw new HsException(TcErrorCode.PARAM_IS_REQUIRED.getCode(),"企业名称必填");
		}
		
		if(balance.getCheckType() == null){
			throw new HsException(TcErrorCode.PARAM_IS_REQUIRED.getCode(),"调账类型必填");
		}
		// 判断客户类型
		Integer custType = HsResNoUtils.getCustTypeByHsResNo(balance.getResNo());
		if(custType == null){
			throw new HsException(TcErrorCode.RES_NO_IS_WRONG.getCode(),TcErrorCode.RES_NO_IS_WRONG.getDesc());
		}
		// 根据资源号查找客户号
		String custId = null;
		if(CustType.PERSON.getCode() == custType.intValue()){
			custId = carderService.findCustIdByResNo(balance.getResNo());
		}
		else{
			custId = entService.findEntCustIdByEntResNo(balance.getResNo());
		}
		// 如果客户号为空，该用户未找到
		if(custId == null){
			throw new HsException(TcErrorCode.USER_NOT_FOUND.getCode(),TcErrorCode.USER_NOT_FOUND.getDesc());
		}
		
		CheckBalance cb = new CheckBalance();
		BeanUtils.copyProperties(balance, cb);
		
		cb.setCustType(custType);
		cb.setCustId(custId);
		cb.setStatus(TcCheckBalanceStatus.WAIT_FIRST_CHECK.getValue());
		cb.setCreateDate(new Date());
		String no = SystemParameterUtil.getSystemNo();
		no = GuidAgent.getShortStringGuid(BizGroup.TC + no);
		System.out.println("生成的调账编号：" + no);
		cb.setId(no);
		
		checkBalanceMapper.insertSelective(cb);
	}

	@Override
	public void updateCheckBalanceStatus(String balanceId, int status)throws HsException{
		CheckBalance cb = new CheckBalance();
		cb.setStatus(status);
		cb.setId(balanceId);
		cb.setUpdateDate(new Date());
		if(status == TcCheckBalanceStatus.SECOND_CHECK_SUCCESS.getValue()){
			cb.setBalanceDate(cb.getUpdateDate());
		}
		checkBalanceMapper.updateByPrimaryKeySelective(cb);
	}
}
