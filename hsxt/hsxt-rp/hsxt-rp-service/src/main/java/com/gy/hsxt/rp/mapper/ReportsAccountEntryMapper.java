/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/

package com.gy.hsxt.rp.mapper;

import java.sql.SQLException;
import java.util.List;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.rp.bean.ReportsAccountEntry;
import com.gy.hsxt.rp.bean.ReportsAccountEntryInfo;

/** 
 * 账户流水查询
 * @Package: com.gy.hsxt.rp.mapper  
 * @ClassName: ReportsAccountEntryMapper 
 * @Description: TODO
 *
 * @author: weixz 
 * @date: 2016-1-6 上午10:50:25 
 * @version V1.0 
 

 */
public interface ReportsAccountEntryMapper {

	/**
	 * 企业账户流水查询
	 * @param rpEnterprisResource 企业查询条件
	 * @return List<ReportsAccountEntry> 企业账户流水数据集合
	 * @throws HsException
	 */
    public List<ReportsAccountEntry> searchEntAccountEntryListPage(ReportsAccountEntryInfo reportsAccountEntryInfo) throws SQLException;
    
    /**
	 * 消费者账户流水查询
	 * @param rpCardholderResource 消费者查询条件
	 * @return List<ReportsAccountEntry> 消费者账户流水数据集合
	 * @throws HsException
	 */
    public List<ReportsAccountEntry> searchCarAccountEntryListPage(ReportsAccountEntryInfo reportsAccountEntryInfo) throws SQLException;
    
	/**
	 * 企业分红账户流水查询
	 * @param rpEnterprisResource 企业查询条件
	 * @return List<ReportsAccountEntry> 企业账户流水数据集合
	 * @throws HsException
	 */
    public List<ReportsAccountEntry> searchEntAccountEntryDividendListPage(ReportsAccountEntryInfo reportsAccountEntryInfo) throws SQLException;
    
    /**
	 * 消费者分红账户流水查询
	 * @param rpCardholderResource 消费者查询条件
	 * @return List<ReportsAccountEntry> 消费者账户流水数据集合
	 * @throws HsException
	 */
    public List<ReportsAccountEntry> searchCarAccountEntryDividendListPage(ReportsAccountEntryInfo reportsAccountEntryInfo) throws SQLException;
    
    /**
     * 非持卡人账户流水查询
     * @param rpCardholderResource 非持卡人查询条件
     * @return List<ReportsAccountEntry> 非持卡人账户流水数据集合
     * @throws HsException
     */
    public List<ReportsAccountEntry> searchNoCarAccountEntryListPage(ReportsAccountEntryInfo reportsAccountEntryInfo) throws SQLException;
    
    
}
