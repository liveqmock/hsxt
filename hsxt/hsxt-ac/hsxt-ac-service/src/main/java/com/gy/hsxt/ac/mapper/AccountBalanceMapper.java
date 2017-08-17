

package com.gy.hsxt.ac.mapper;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gy.hsxt.ac.bean.AccountBalance;
import com.gy.hsxt.ac.bean.AccountBalanceSnap;
import com.gy.hsxt.ac.bean.AccountStatusChange;
import com.gy.hsxt.common.exception.HsException;

/**
 *  账户余额DAO映射类
 *  @Project Name    : hsxt-ac-service
 *  @Package Name    : com.gy.hsxt.ac.mapper
 *  @File Name       : AccountBalanceMapper.java
 *  @Author          : weixz
 *  @Description     : TODO
 *  @Creation Date   : 2015-8-27
 *  @version V1.0
 * 
 */
public interface AccountBalanceMapper {

	
	
	
	/**
	 * 查询余额记录
	 * @param accountBalanceList 账户余额对象 
	 * @return
	 */
	 public AccountBalance queryAccountBalance(AccountBalance accountBalance) throws SQLException;
	 
	/**
	 * 新增多个余额记录
	 * @param accBalanceList 账户余额对象集合
	 * @throws HsException
	 */
	public void addAccountBalances(List<AccountBalance> accountBalanceList) throws SQLException;
	
	/**
	 * 新增单个余额记录
	 * @param accBalance 账户余额对象
	 * @throws HsException
	 */
	public void addAccountBalance(AccountBalance accountBalance) throws SQLException;
	
	/**
	 * 新增单个个人余额记录
	 * @param accBalance 个人账户余额对象
	 * @throws HsException
	 */
	public void addPerAccountBalance(AccountBalance accountBalance) throws SQLException;
	
	/**
	 * 新增单个平台余额记录
	 * @param accBalance 平台账户余额对象
	 * @throws HsException
	 */
	public void addPfAccountBalance(AccountBalance accountBalance) throws SQLException;
	
	/**
	 * 新增单个企业余额记录
	 * @param accBalance 企业账户余额对象
	 * @throws HsException
	 */
	public void addEntAccountBalance(AccountBalance accountBalance) throws SQLException;
	
	
	/**
	 * 更新多个余额信息(用于余额增减额计算)
	 * @param accountBalanceList 账户余额对象集合
	 */
	public void updateAccountBalances(List<AccountBalance> accountBalanceList) throws SQLException;
	
	/**
	 * 更新单个余额信息(用于余额增减额计算)
	 * @param accountBalance 账户余额信息对象
	 */
	public int updateAccountBalance(AccountBalance accountBalance) throws SQLException;
	
	/**
     * 更新多个余额信息(用于外部直接修改余额)
     * @param accountBalanceList 账户余额对象集合
     */
    public void updateAccBalances(List<AccountBalance> accountBalanceList) throws SQLException;
    
    /**
     * 更新单个余额信息(用于外部直接修改余额)
     * @param accountBalance 账户余额信息对象
     */
    public void updateAccBalance(AccountBalance accountBalance) throws SQLException;
	
	/**
	 * 更新单个个人余额信息
	 * @param accountBalance 个人账户余额信息对象
	 */
	public void updatePerAccountBalance(AccountBalance accountBalance) throws SQLException;
	/**
	 * 更新单个平台余额信息
	 * @param accountBalance 平台账户余额信息对象
	 */
	public void updatePfAccountBalance(AccountBalance accountBalance) throws SQLException;
	
	/**
	 * 更新单个企业余额信息
	 * @param accountBalance 企业账户余额信息对象
	 */
	public void updateEntAccountBalance(AccountBalance accountBalance) throws SQLException;
	
	/**
	 * 新增单个账户状态变更接口
	 * @param accStatusChange 账户状态变更对象
	 * @throws HsException 
	 */
	public void addAccStatusChangeInfo(AccountStatusChange accStatusChange) throws SQLException;
	
	/**
     * 新增单个账户状态变更接口
     * @param accStatusChangeList 账户状态变更对象集合
     * @throws HsException 
     */
    public void addAccStatusChangeInfos(List<AccountStatusChange> accStatusChangeList) throws SQLException;
	
	/**
	 * 账户余额信息查询
	 * @param custID:客户全局编号、accType：账户类型编码
	 * @return AccountBalance 账户余额实体对象 
	 * @throws HsException
	 */
	public AccountBalance searchAccountBalanceInfo(@Param("custID")String custID,@Param("accType")String accType) throws SQLException;
	
	/**
	 * 账户余额信息查询
	 * @param custID:客户全局编号、accType：账户类型编码
	 * @return AccountBalance 账户余额实体对象 
	 * @throws HsException
	 */
	public AccountBalance searchAccountBalance(@Param("custID")String custID,@Param("accType")String accType,@Param("custType")Integer custType) throws SQLException;
	
	/**
     * 多个账户余额的汇总额查询
     * @param accTypes：账户类型编码集
     * @return String 多个账户余额的汇总额
     * @throws HsException
     */
    public String searchAccountBalanceSumAmount(@Param("accType")String accType, @Param("custTypeCategory")Integer custTypeCategory) throws SQLException;
	
	/**
	 * 正常账户余额集合查询
	 * @param AccountBalance 账户余额实体对象 
	 * @return List<AccountBalance>
	 * @throws HsException
	 */
	public List<AccountBalance> seachAccNormalListPage(AccountBalance accountBalance) throws SQLException;
	
	/**
	 * 特殊账户余额集合查询
	 * @param AccountBalance 账户余额实体对象 
	 * @return List<AccountBalance> 特殊账户余额集合
	 * @throws HsException
	 */
	public List<AccountBalance> seachAccSpecialListPage(AccountBalance accountBalance) throws SQLException;
	
	/**
	 * 新增单个余额快照表记录
	 * @param accountBalanceSnap 余额快照对象
	 * @throws HsException
	 */
	public void addAccountBalanceSnap(AccountBalanceSnap accountBalanceSnap) throws SQLException;
	
	/**
	 * 新增多个余额快照表记录
	 * @param accountBalanceSnapList 余额快照对象集合
	 * @throws HsException
	 */
	public void addAccountBalanceSnaps(List<AccountBalanceSnap> accountBalanceSnapList) throws SQLException;
	
	/**
     * 批处理新增多个余额快照表记录
     * @param addBatchAccountBalanceSnaps 余额快照对象集合
     * @throws HsException
     */
    public void addBatchAccountBalanceSnaps(List<AccountBalanceSnap> accountBalanceSnapList) throws SQLException;
	
	/**
     * 客户全局编码和客户类型对应关系下的所有账户余额查询
     * @param custID:客户全局编号
     * @return List<AccountBalance> 账户余额对象集合
     * @throws HsException
     */
	public List<AccountBalance> searchAccBalanceByCustId(String custId) throws SQLException ;

	/**
     * 客户对应的账户类型分类下的所有该类型账户余额查询
     * @param custID:客户全局编号 accCategory 账户分类（1、积分类型，2、互生币类型，3、货币类型、4地区平台银行货币存款/地区平台银行货币现金）
     * @return List<AccountBalance> 账户余额对象集合
     * @throws HsException
     */
	public List<AccountBalance> searchAccBalanceByAccCategory(@Param("custID")String custId, @Param("accCategory")int accCategory) throws SQLException;

	/**
     * 同一客户的多个账户余额查询
     * @param custId 客户全局编号
     * @param accTypes 账户类型数组
     * @return List<AccountBalance> 账户余额对象List集合
     * @throws HsException
     */
    public List<AccountBalance> searchAccBalanceByCustIdAndAccType(@Param("custID")String custId, @Param("accTypes")String[] accTypes) throws SQLException;

}
