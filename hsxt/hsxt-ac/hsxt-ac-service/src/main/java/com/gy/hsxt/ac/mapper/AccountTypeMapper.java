package com.gy.hsxt.ac.mapper;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.alibaba.dubbo.config.support.Parameter;
import com.gy.hsxt.ac.bean.AccountStatusChange;
import com.gy.hsxt.ac.bean.AccountType;
import com.gy.hsxt.common.exception.HsException;

/**
 * 账户类型DAO映射类
 * @author 作者 : weixz
 * @ClassName: 接口名:AccountTypeMapper
 * @Description: TODO
 * @createDate 创建时间: 2015-8-25 上午9:59:29
 * @version 3.0 
 */
public interface AccountTypeMapper {
	
	
	/**
	 * 多个新增账户类型
	 * @param accountTypeList 账户类型实体集合对象 ，必填(账户类型编码、账户类型名称、币种)
	 */
	public void addAccTypes(List<AccountType> accountTypeList) throws SQLException;

	/**
	 * 单个新增账户类型
	 * @param accountType 账户类型实体集合对象 ，必填(账户类型编码、账户类型名称、币种)
	 */
	public void addAccType(AccountType accountTye) throws SQLException;
	
	/**
	 * 单个账户类型修改接口定义
	 * @param accountTypeList 账户类型实体集合对象  
	 * @throws HsException 异常处理类
	 */
	public void updateAccType(AccountType accountType) throws SQLException;
	
	/**
     * 批量账户类型修改接口定义
     * @param accountType 账户类型实体  
     * @throws HsException 异常处理类
     */
    public void updateAccTypes(List<AccountType> accountTypeList) throws SQLException;
	
    /**
     * 单个账户类型删除接口定义
     * @param accountTypeList 账户类型实体集合对象
     * @throws HsException 异常处理类
     */
    public void deleteAccountType(@Param("accType")String accType) throws SQLException;
    
	/**
	 * 批量账户类型删除接口定义
	 * @param accountTypeList 账户类型实体集合对象
	 * @throws HsException 异常处理类
	 */
	public void deleteAccountTypes(List<AccountType> accountTypeList) throws SQLException;
	
	/**
     * 单个账户类型查询
     * @param AccType 账户类型编码
     * @return AccountType 账户类型对象
     */
    public AccountType searchAccountType(@Param("accType")String accType) throws SQLException;
	
	/**
	 * 集合账户类型查询
	 * @param accountTypeList 账户类型实体集合对象  
	 * @throws HsException 异常处理类
	 */
	public List<AccountType> searchAccountTypeListPage(AccountType accountType) throws SQLException;
	
	/**
	 * 账户状态变更记录获取
	 * @param accStatusChange 账户状态变更类。
	 * @return List<AccountStatusChange>
	 * @throws HsException
	 */
	public List<AccountStatusChange> searchAccStatusChangeListPage(AccountStatusChange accStatusChange) throws SQLException;
}
