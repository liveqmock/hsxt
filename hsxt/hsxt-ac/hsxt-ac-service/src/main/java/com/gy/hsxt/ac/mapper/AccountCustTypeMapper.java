package com.gy.hsxt.ac.mapper;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gy.hsxt.ac.bean.AccountCustType;
import com.gy.hsxt.common.exception.HsException;

/**
 * 账户和客户类型关系DAO映射类
 * @author 作者 : weixz
 * @ClassName: 接口名:CustAccTypeMapper
 * @Description: TODO
 * @createDate 创建时间: 2015-8-26 下午6:28:29
 * @version 3.0 
 */
public interface AccountCustTypeMapper {

	/**
	 * 新增单个客户类型与账户关系对象
	 * @param accountCustType  客户类型与账户关系对象
	 * @throws HsException 异常处理类
	 */
	public void addCustAccType(AccountCustType accountCustType) throws SQLException;
	
	/**
     * 新增批量客户类型与账户关系对象
     * @param accountCustType  客户类型与账户关系对象集合
     * @throws HsException 异常处理类
     */
    public void addCustAccTypes(List<AccountCustType> accountCustType) throws SQLException;
	
    /**
     * 修改单个客户类型与账户关系对象
     * @param accountCustType 客户类型与账户关系对象
     * @throws HsException 异常处理类
     */
    public void updateCustAccType(AccountCustType accountCustType) throws SQLException;
    
	/**
	 * 修改批量客户类型与账户关系对象
	 * @param accountCustTypeList 客户类型与账户关系对象集合
	 * @throws HsException 异常处理类
	 */
	public void updateCustAccTypes(List<AccountCustType> accountCustTypeList) throws SQLException;
	
	/**
     * 删除单个客户类型与账户关系对象
     * @param custType 客户全局编码
     * @param accType 账户类型编码
     * @throws HsException 异常处理类
     */
    public void deleteCustAccType(@Param("custType")Integer custType, @Param("accType")String accType) throws SQLException;
	
	/**
	 * 删除批量客户类型与账户关系对象
	 * @param accountCustTypeList 客户类型与账户关系对象集合
	 * @throws HsException 异常处理类
	 */
	public void deleteCustAccTypes(List<AccountCustType> accountCustTypeList) throws SQLException;
	
	/**
     * 查询单个客户类型与账户关系对象
     * @param custID:客户全局编号
     * @param accType：账户类型编码
     * @return CustAccType 客户类型与账户关系对象
     * @throws HsException 异常处理类
     */
    public AccountCustType searchCustAccType(@Param("custType")Integer custType,@Param("accType")String accType) throws SQLException;
    
	/**
	 * 查询批量客户类型与账户关系对象
	 * @param AccountCustType 客户类型与账户关系对象
	 * @return List<AccountCustType> 客户类型与账户关系对象集合
	 * @throws HsException 异常处理类
	 */
	public List<AccountCustType> searchCustAccTypeListPage(AccountCustType accountCustType) throws SQLException;
	
}
