package com.gy.hsxt.rp.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.gy.hsxt.rp.bean.ReportsAccountEntry;

/**
 *  账户分录DAO映射类
 *  @Project Name    : hsxt-ac-service
 *  @Package Name    : com.gy.hsxt.ac.mapper
 *  @File Name       : AccountEntrymapper.java
 *  @Author          : weixz
 *  @Description     : TODO
 *  @Creation Date   : 2015-8-26
 *  @version V1.0
 * 
 */
public interface ReportAccountEntryBatchMapper {
	
	/**
	 * 通过会计时间区间找相关的分录记账信息记录
	 * @param accountEntryMap 
	 * @return List<AccountEntry> 分录记账信息集合
	 * @throws SQLException
	 */
	public List<ReportsAccountEntry> seachAccountEntryByFiscalDate(Map<String,Object> accountEntryMap) throws SQLException;
	
	/**
	 * 通过会计时间区间找相关的分录记账信息数量
	 * @param accountEntryMap 开始时间与结束时间
	 * @return int 分录记账信息数量
	 * @throws SQLException
	 */
	public int seachAccountEntryCount(Map<String,Object> accountEntryMap) throws SQLException;
	
	/**
	 * 批生成日积分活动资源数量
	 * @param accountEntryMap 开始时间与结束时间
	 * @return int 分录记账信息数量
	 * @throws SQLException
	 */
	public int seachAccEntryCountByFisDate(Map<String,Object> accountEntryMap) throws SQLException;
	
	/**
     * 批生成企业月送积分数量
     * @param accountEntryMap 月份开始时间与月末结束时间
     * @return int 分录记账信息数量
     * @throws SQLException
     */
    public int seachAccEntryCountByTransType(Map<String,Object> accountEntryMap) throws SQLException;
    
	/**
	 * 批生成日积分活动资源信息记录
	 * @param accountEntryMap 
	 * @return List<AccountEntry> 分录记账信息集合
	 * @throws SQLException
	 */
	public List<ReportsAccountEntry> seachAccEntryListPageByFisDate(Map<String,Object> accountEntryMap) throws SQLException;
	
	/**
     * 批生成企业月送积分列表
     * @param accountEntryMap （交易类型，月份开始时间与月末结束时间）
     * @return List<AccountEntry> 分录记账信息集合
     * @throws SQLException
     */
    public List<ReportsAccountEntry> seachAccEntryListPageByTransType(Map<String,Object> accountEntryMap) throws SQLException;
	
}
