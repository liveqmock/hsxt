/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.rp.api;

import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.rp.bean.ReportsAccountEntry;
import com.gy.hsxt.rp.bean.ReportsAccountEntryInfo;

/**
 * 
 * 账户流水查询接口
 * @Package: com.gy.hsxt.rp.api  
 * @ClassName: IReportsBankrollReserveService 
 * @Description: TODO
 *
 * @author: maocan 
 * @date: 2015-12-28 下午6:14:32 
 * @version V1.0
 */
public interface IReportsAccountEntryService {
	
	/**
	 * 企业账户流水查询
	 * @param rpEnterprisResource 企业查询条件
	 * @param page 
	 * 				分页信息（curPage 当前页,pageSize 每页记录数）
	 * @return List<ReportsAccountEntry> 企业账户流水数据集合
	 * @throws HsException
	 */
	public PageData<ReportsAccountEntry> searchEntAccountEntry(ReportsAccountEntryInfo reportsAccountEntryInfo, Page page) throws HsException;
	
	/**
	 * 企业分红账户流水查询
	 * @param rpEnterprisResource 企业查询条件
	 * @param page 
	 * 				分页信息（curPage 当前页,pageSize 每页记录数）
	 * @return PageData<ReportsAccountEntry> 企业账户流水数据集合
	 * @throws HsException
	 */
	public PageData<ReportsAccountEntry> searchEntAccountEntryDividend(	ReportsAccountEntryInfo reportsAccountEntryInfo, Page page) throws HsException;
	
	/**
	 * 消费者账户流水查询
	 * @param rpCardholderResource 消费者查询条件
	 * @param page 
	 * 				分页信息（curPage 当前页,pageSize 每页记录数）
	 * @return List<ReportsAccountEntry> 消费者账户流水数据集合
	 * @throws HsException
	 */
	public PageData<ReportsAccountEntry> searchCarAccountEntry(ReportsAccountEntryInfo reportsAccountEntryInfo, Page page) throws HsException;
	
	/**
	 * 消费者分红账户流水查询
	 * @param rpEnterprisResource 消费者查询条件
	 * @param page 
	 * 				分页信息（curPage 当前页,pageSize 每页记录数）
	 * @return PageData<ReportsAccountEntry> 消费者分红流水数据集合
	 * @throws HsException
	 */
	public PageData<ReportsAccountEntry> searchCarAccountEntryDividend(	ReportsAccountEntryInfo reportsAccountEntryInfo, Page page) throws HsException;
	
	/**
     * 非持卡人账户流水查询
     * @param rpCardholderResource 非持卡人查询条件
     * @param page 分页信息（curPage 当前页,pageSize 每页记录数）
     * @return List<ReportsAccountEntry> 非持卡人账户流水数据集合
     * @throws HsException
     */
	public PageData<ReportsAccountEntry> searchNoCarAccountEntry(ReportsAccountEntryInfo reportsAccountEntryInfo, Page page) throws HsException;
	
}
