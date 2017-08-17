/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.company.services.accountManagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsxt.access.web.common.service.BaseServiceImpl;
import com.gy.hsxt.bs.api.order.IBSInvestProfitService;
import com.gy.hsxt.bs.bean.order.CustomPointDividend;
import com.gy.hsxt.bs.bean.order.DividendDetail;
import com.gy.hsxt.bs.bean.order.PointDividend;
import com.gy.hsxt.bs.bean.order.PointInvest;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;

/***************************************************************************
 * <PRE>
 * Description 		: 投资账户的操作类
 * 
 * Project Name   	: hsxt-access-web-person 
 * 
 * Package Name     : com.gy.hsxt.access.web.person.services.accountManagement  
 * 
 * File Name        : InvestmentAccountService 
 * 
 * Author           : LiZhi Peter
 * 
 * Create Date      : 2015-12-29 下午4:11:33
 * 
 * Update User      : LiZhi Peter
 * 
 * Update Date      : 2015-12-29 下午4:11:33
 * 
 * UpdateRemark 	: 说明本次修改内容
 * 
 * Version          : v1.0
 * 
 * </PRE>
 ***************************************************************************/
@Service
public class InvestmentAccountService extends BaseServiceImpl implements IInvestmentAccountService {

	/**
	 * 积分投资分红信息dubbo
	 */
	@Autowired
	private IBSInvestProfitService bsInvestProfitService;

	/**
	 * 查询积分投资分红信息
	 * 
	 * @param hsResNo
	 *            互生号
	 * @param dividendYear
	 *            年份
	 * @return
	 * @throws HsException
	 */
	@Override
	public CustomPointDividend findInvestDividendInfo(String hsResNo, String dividendYear)
			throws HsException {
		return this.bsInvestProfitService.getInvestDividendInfo(hsResNo, dividendYear);
	}

	/**
	 * 查询积分投资记录
	 * 
	 * @param custId
	 *            客户号 必传
	 * @param startDate
	 *            查询条件：投资日期 可选
	 * @param endDate
	 *            查询条件：投资日期 可选
	 * @param page
	 *            分页参数 必传
	 * @return
	 * @throws HsException
	 * @see com.gy.hsxt.access.web.company.services.accountManagement.IInvestmentAccountService#getPointInvestList(java.lang.String,
	 *      java.lang.String, java.lang.String, com.gy.hsxt.common.bean.Page)
	 */
	@Override
	public PageData<PointInvest> getPointInvestList(String custId, String startDate,
			String endDate, Page page) throws HsException {
		return this.bsInvestProfitService.getPointInvestList(custId, startDate, endDate, page);
	}

	/**
	 * 查询积分投资分红列表
	 * 
	 * @param custId
	 *            客户号 必传
	 * @param startYear
	 *            开始年份 可选
	 * @param endYear
	 *            结束年份 可选
	 * @param page
	 *            分页参数 必传
	 * @return
	 * @throws HsException
	 * @see com.gy.hsxt.access.web.company.services.accountManagement.IInvestmentAccountService#getPointDividendList(java.lang.String,
	 *      java.lang.String, java.lang.String, com.gy.hsxt.common.bean.Page)
	 */
	@Override
	public PageData<PointDividend> getPointDividendList(String custId, String startYear,
			String endYear, Page page) throws HsException {
		return this.bsInvestProfitService.getPointDividendList(custId, startYear, endYear, page);
	}

	/**
	 * 投资分红详情
	 * 
	 * @param hsResNo
	 *            客户号 必传
	 * @param dividendYear
	 *            分红年份
	 * @param page
	 *            分页参数 必传
	 * @return
	 * @throws HsException
	 * @see com.gy.hsxt.access.web.company.services.accountManagement.IInvestmentAccountService#getDividendDetailList(java.lang.String,
	 *      java.lang.String, com.gy.hsxt.common.bean.Page)
	 */
	@Override
	public PageData<DividendDetail> getDividendDetailList(String hsResNo, String dividendYear,
			Page page) throws HsException {
		return this.bsInvestProfitService.getDividendDetailList(hsResNo, dividendYear, page);
	}

	/**
	 * 获取投资分红信息
	 * 
	 * @param hsResNo
	 *            互生号 必传
	 * @param dividendYear
	 *            分红年份 必传
	 * @return
	 * @throws HsException
	 * @see com.gy.hsxt.access.web.company.services.accountManagement.IInvestmentAccountService#getInvestDividendInfo(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public CustomPointDividend getInvestDividendInfo(String hsResNo, String dividendYear)
			throws HsException {
		return this.bsInvestProfitService.getInvestDividendInfo(hsResNo, dividendYear);
	}

}
