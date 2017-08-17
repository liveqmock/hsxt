package com.gy.hsxt.access.web.aps.services.callCenter.impl;

import com.gy.hsxt.ac.bean.AccountEntrySum;
import com.gy.hsxt.bs.api.order.IBSInvestProfitService;
import com.gy.hsxt.bs.bean.order.CustomPointDividend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsxt.ac.bean.AccountBalance;
import com.gy.hsxt.access.web.aps.services.callCenter.IAccountSearchService;
import com.gy.hsxt.common.exception.HsException;

import java.util.Map;

/**   
 * 账户查询接口实现类
 * @category      账户查询接口实现类
 * @projectName   hsxt-access-web-aps
 * @package       com.gy.hsxt.access.web.aps.services.callCenter.impl.AccountSearchServiceImpl.java
 * @className     AccountSearchServiceImpl
 * @description   账户查询接口实现类
 * @author        leiyt
 * @createDate    2016-1-27 下午6:10:18  
 * @updateUser    leiyt
 * @updateDate    2016-1-27 下午6:10:18
 * @updateRemark 	说明本次修改内容
 * @version       v0.0.1
 */
@Service
public class AccountSearchServiceImpl implements IAccountSearchService {
	
	@Autowired
	com.gy.hsxt.ac.api.IAccountSearchService accountSearchService;
	@Autowired
	private IBSInvestProfitService bsInvestProfitService;
	
	@Override
	public AccountBalance searchAccNormal(String custId, String accType) throws HsException {
		return accountSearchService.searchAccNormal(custId, accType);
	}

	@Override
	public java.util.Map<String, AccountBalance> searchAccBalanceByCustIdAndAccType(String custId, String[] accType) throws HsException {
		return accountSearchService.searchAccBalanceByCustIdAndAccType(custId,accType);
	}

	@Override
	public AccountEntrySum searchEntIntegralByYesterday(String custId, String accType) throws HsException {
		return accountSearchService.searchEntIntegralByYesterday( custId,  accType);
	}

	@Override
	public AccountEntrySum searchPerIntegralByToday(String custId, String accType) throws HsException {
		return accountSearchService.searchPerIntegralByToday(custId,accType);
	}

	@Override
	public CustomPointDividend findInvestDividendInfo(String hsResNo, String dividendYear) {
		return bsInvestProfitService.getInvestDividendInfo(hsResNo,dividendYear);
	}
}

	