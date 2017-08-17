package com.gy.hsxt.ac.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.gy.hsxt.ac.bean.AccountEntry;
import com.gy.hsxt.ac.bean.AccountEntryInfo;
import com.gy.hsxt.ac.bean.AccountEntrySum;
import com.gy.hsxt.common.exception.HsException;

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
public interface AccountEntryMapper {
	
  
	
	/**
	 * 插入单个记账分录
	 * @param accountEntry
	 * @throws HsException	异常处理类
	 */
	public void addAccountEntry(AccountEntry accountEntry) throws SQLException;

	/**
	 * 插入多个记账分录
	 * @param accountEntryList
	 */
	public void addAccountEntrys(List<AccountEntry> accountEntryList) throws SQLException;

	/**
	 * 冲正红冲集合记账服务
	 * @param accountEntryList
	 * @throws HsException
	 */
	public void addCorrectAccount(List<AccountEntry> accountEntryList) throws SQLException;
	
	/**
	 * 查询
	 * @param accountEntry
	 * @return
	 */
	public List<AccountEntry> queryAccountEntry(AccountEntry accountEntry) throws SQLException;
	
	/**
	 * 通过交易流水号找相关的分录记账信息记录
	 * @param TransNo 交易流水号  writeBack 红冲标识
	 * @throws HsException	异常处理类
	 */
	public List<AccountEntry> seachAccountEntrysByTrsNo(@Param("transNo")String transNo,@Param("writeBack")Integer writeBack) throws SQLException;
	
	/**
	 * 通过交易流水号,客户ID,账户类型，找相关的分录记账信息记录
	 * @param TransNo 关联交易流水号 custID 客户ID，accType账户类型
	 * @throws HsException	异常处理类
	 */
	public AccountEntry seachAccountEntryByTrsNo(@Param("transNo")String transNo,@Param("writeBack")Integer writeBack,@Param("custID")String custID,@Param("accType")String accType) throws SQLException;
	
	/**
     * 通过交易流水号,客户ID,账户类型，找相关的分录记账信息记录
     * @param relTransNo 关联交易流水号 custID 客户ID，accType账户类型
     * @throws HsException  异常处理类
     */
    public List<AccountEntry> seachChargeBackAccEntryList(@Param("relTransNo")String relTransNo,@Param("writeBack")Integer writeBack,@Param("custID")String custID,@Param("accType")String accType,@Param("relSysEntryNo")String relSysEntryNo) throws SQLException;
    
	
	
	/**
	 * 正常账户发生汇总额查询
	 * @param accountEntry 
	 * @return AccountEntry
	 * @throws HsException
	 */
	public AccountEntrySum searchSumAccNormal(AccountEntry accountEntry) throws SQLException;
	
	/**
	 * 特殊账户发生汇总额查询
	 * @param accountEntry 
	 * @return AccountEntry
	 * @throws HsException
	 */	
	public AccountEntrySum searchSumAccSpecial(AccountEntry accountEntry) throws SQLException;
	
	/**
	 * 账户分录查询服务
	 * @param accountEntry
	 * @return AccountEntry
	 * @throws HsException
	 */	
	public List<AccountEntry> seachAccEntryListPage(AccountEntry accountEntry) throws SQLException;
	
	/**
	 * 账户分录查询服务
	 * @param accountEntryInfo 账务分录信息对象
	 * @return List<AccountEntryInfo> 分录信息集合
	 * @throws HsException
	 */	
	public List<AccountEntry> seachAccEntryInfoListPage(AccountEntryInfo accountEntryInfo) throws SQLException;
	
	/**
	 * 查询序列当前值
	 * @author weixz
	 * @param String ：序列ID
	 * @return String 序列当前值
	 * @throws HsException
	 */
	public String querySequencesNextValue(String sequencesID) throws SQLException;
	
	/**
	 * 更新多个分录红冲金额
	 * @param accountBalanceList
	 */
	public void updateAccountEntrysByTrsNo(List<AccountEntry> accountEntryList) throws SQLException;
	
	/**
	 * 更新单个分录红冲金额
	 * @param accountBalanceList
	 */
	public void updateAccountEntryByTrsNo(AccountEntry accountEntry) throws SQLException;
	
	/**
	 * 通过会计时间区间找相关的分录记账信息记录
	 * @param accountEntryMap 
	 * @return List<AccountEntry> 分录记账信息集合
	 * @throws SQLException
	 */
	public List<AccountEntry> seachAccountEntryByFiscalDate(Map<String,Object> accountEntryMap) throws SQLException;
	
	/**
	 * 通过会计时间区间找相关的分录记账信息数量
	 * @param accountEntryMap 开始时间与结束时间
	 * @return int 分录记账信息数量
	 * @throws SQLException
	 */
	public int seachAccountEntryCount(Map<String,Object> accountEntryMap) throws SQLException;
	
	/**
	 * 通过会计时间区间找相关的(托管企业,成员企业名单,持卡人)分录记账信息数量
	 * @param accountEntryMap 开始时间与结束时间
	 * @return int 分录记账信息数量
	 * @throws SQLException
	 */
	public int seachAccEntryCountByFisDate(Map<String,Object> accountEntryMap) throws SQLException;
	
	/**
     * 通过积分预付款账户类型找相关的分录记账信息数量，区间为一个月
     * @param accountEntryMap 月份开始时间与月末结束时间
     * @return int 分录记账信息数量
     * @throws SQLException
     */
    public int seachAccEntryCountByAccType(Map<String,Object> accountEntryMap) throws SQLException;
    
	/**
	 * 通过会计时间区间找相关的(托管企业,成员企业名单,持卡人)分录记账信息记录
	 * @param accountEntryMap 
	 * @return List<AccountEntry> 分录记账信息集合
	 * @throws SQLException
	 */
	public List<AccountEntry> seachAccEntryListPageByFisDate(Map<String,Object> accountEntryMap) throws SQLException;
	
	/**
     * 通过积分预付款账户类型和月时间段查找相关的分录记账信息
     * @param accountEntryMap （积分预付款账户类型，月份开始时间与月末结束时间）
     * @return List<AccountEntry> 分录记账信息集合
     * @throws SQLException
     */
    public List<AccountEntry> seachAccEntryListPageByAccType(Map<String,Object> accountEntryMap) throws SQLException;
	
    /**
     * 个人今日积分查询
     * @param custID:客户全局编号、custType：客户类型编码
     * @return AccountBalance 账户余额对象
     * @throws HsException
     */
    public AccountEntrySum searchPerIntegralByToday(Map<String,Object> map) throws SQLException;

    /**
     * 企业昨日积分查询
     * @param custID:客户全局编号、custType：客户类型编码
     * @return AccountBalance 账户余额对象
     * @throws HsException
     */
    public AccountEntrySum searchEntIntegralByYesterday(Map<String,Object> map) throws SQLException;
    
    /**
     * 通过关联各系统分录序列号,交易流水号,客户ID,账户类型，找相关的分录记账信息记录
     * @param relSysEntryNo 关联各系统分录序列号  TransNo 关联交易流水号 custID 客户ID，accType账户类型
     * @throws HsException  异常处理类
     */
    public AccountEntry seachAccEntryByTrsNoAndSysEntryNo(@Param("relSysEntryNo")String relSysEntryNo,@Param("transNo")String transNo,@Param("custID")String custID,@Param("accType")String accType) throws SQLException;
    
    
    /**
     * 同一客户的多个账户分录查询
     * @param custId 客户全局编号
     * @param accTypes 账户类型数组
     * @param beginDateTim 开始时间
     * @param endDateTim 结束时间
     * @return PageData<AccountEntry> 分录对象集合
     * @throws HsException
     */
    public List<AccountEntry> searchAccEntryListByCustIdAndAccTypeListPage(AccountEntryInfo accountEntryInfo) throws SQLException;
   
}
