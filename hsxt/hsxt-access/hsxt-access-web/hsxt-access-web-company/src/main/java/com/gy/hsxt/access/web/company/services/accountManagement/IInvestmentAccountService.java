/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.company.services.accountManagement;

import org.springframework.stereotype.Service;

import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.bs.bean.order.CustomPointDividend;
import com.gy.hsxt.bs.bean.order.DividendDetail;
import com.gy.hsxt.bs.bean.order.PointDividend;
import com.gy.hsxt.bs.bean.order.PointInvest;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;

/***************************************************************************
 * <PRE>
 * Description 		: 积分账户服务操作类
 * 
 * Project Name   	: hsxt-access-web-person 
 * 
 * Package Name     : com.gy.hsxt.access.web.person.services.accountManagement  
 * 
 * File Name        : IInvestmentAccountService 
 * 
 * Author           : LiZhi Peter
 * 
 * Create Date      : 2015-12-29 下午4:10:16
 * 
 * Update User      : LiZhi Peter
 * 
 * Update Date      : 2015-12-29 下午4:10:16
 * 
 * UpdateRemark 	: 说明本次修改内容
 * 
 * Version          : v1.0
 * 
 * </PRE>
 ***************************************************************************/
@Service
public interface IInvestmentAccountService extends IBaseService {

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
	public CustomPointDividend findInvestDividendInfo(String hsResNo, String dividendYear)
			throws HsException;

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
	 */
	PageData<PointInvest> getPointInvestList(String custId, String startDate, String endDate,
			Page page) throws HsException;

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
	 */
	PageData<PointDividend> getPointDividendList(String custId, String startYear, String endYear,
			Page page) throws HsException;

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
	 */
	PageData<DividendDetail> getDividendDetailList(String hsResNo, String dividendYear, Page page)
			throws HsException;

	/**
	 * 获取投资分红信息
	 * 
	 * @param hsResNo
	 *            互生号 必传
	 * @param dividendYear
	 *            分红年份 必传
	 * @return
	 * @throws HsException
	 */
	CustomPointDividend getInvestDividendInfo(String hsResNo, String dividendYear)
			throws HsException;
}
