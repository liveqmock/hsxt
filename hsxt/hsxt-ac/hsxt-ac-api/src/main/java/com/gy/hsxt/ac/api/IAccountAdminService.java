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

package com.gy.hsxt.ac.api;

import java.util.List;

import com.gy.hsxt.ac.bean.AccountBalance;
import com.gy.hsxt.ac.bean.AccountCustType;
import com.gy.hsxt.ac.bean.AccountStatusChange;
import com.gy.hsxt.ac.bean.AccountType;
import com.gy.hsxt.common.exception.HsException;

/** 
 * 账户管理接口
 * @Package: com.gy.hsxt.ac.api  
 * @ClassName: IAccountAdminService 
 * @Description: TODO
 * @author: weixz 
 * @date: 2015-10-12 上午11:10:47 
 * @version V1.0 
 */
public interface IAccountAdminService {

    /**
     * 新增单个账户类型
     * @param accountType 账户类型对象
     * @throws HsException 异常处理类
     */
    public void addAccType(AccountType accountType) throws HsException;
    
    /**
     * 新增批量账户类型
     * @param accountTypeList 账户类型对象集合 
     * @throws HsException 异常处理类
     */
    public void addAccTypes(List<AccountType> accountTypeList) throws HsException;
    
    /**
     * 修改单个账户类型
     * @param accountType 账户类型对象
     * @throws HsException 异常处理类
     */
    public void updateAccType(AccountType accountType) throws HsException;
    
    /**
     * 修改批量账户类型
     * @param accountTypeList 账户类型对象集合 
     * @throws HsException 异常处理类
     */
    public void updateAccTypes(List<AccountType> accountTypeList) throws HsException;
    
    /**
     * 删除单个账户类型
     * @param accType 账户类型编码
     * @throws HsException 异常处理类
     */
    public void deleteAccountType(String accType) throws HsException;
    
    /**
     * 删除批量账户类型
     * @param accountTypeList 账户类型对象集合
     * @throws HsException 异常处理类
     */
    public void deleteAccountTypes(List<AccountType> accountTypeList) throws HsException;
    
    /**
     * 客户类型与账户关系单个新增接口定义
     * @param accountCustType  客户类型与账户关系对象
     * @throws HsException 异常处理类
     */
    public void addCustAccType(AccountCustType accountCustType) throws HsException;
    
    /**
     * 客户类型与账户关系批量新增接口定义
     * @param accountCustTypeList  客户类型与账户关系对象集合
     * @throws HsException 异常处理类
     */
    public void addCustAccTypes(List<AccountCustType> accountCustTypeList) throws HsException;
    
    /**
     * 客户类型与账户关系单个修改接口定义
     * @param accountCustType 客户类型与账户关系对象
     * @throws HsException 异常处理类
     */
    public void updateCustAccType(AccountCustType accountCustType) throws HsException;
    
    /**
     * 客户类型与账户关系批量修改接口定义
     * @param accountCustTypeList 客户类型与账户关系对象集合
     * @throws HsException 异常处理类
     */
    public void updateCustAccTypes(List<AccountCustType> accountCustTypeList) throws HsException;
    
    /**
     * 客户类型与账户关系单个删除接口定义
     * @param custType 客户全局编码
     * @param accType 账户类型编码
     * @throws HsException 异常处理类
     */
    public void deleteCustAccType(Integer custType, String accType) throws HsException;
    
    /**
     * 客户类型与账户关系批量删除接口定义
     * @param accountCustTypeList 客户类型与账户关系对象集合
     * @throws HsException 异常处理类
     */
    public void deleteCustAccTypes(List<AccountCustType> accountCustTypeList) throws HsException;
    
    /**
     * 新增单个余额记录对象接口
     * @param accBalance 余额记录对象
     * @throws HsException
     */
    public void openAccount(AccountBalance accountBalance) throws HsException;
    
    /**
     * 新增批量余额记录对象接口
     * @param accBalanceList 余额记录对象集合
     * @throws HsException
     */
    public void openAccounts(List<AccountBalance> accountBalanceList) throws HsException;
    
    
    /**
     * 新增单个账户状态变更接口
     * @param accStatusChange 账户状态变更记录条件对象
     * @throws HsException
     */
    public void addAccStatusChange(AccountStatusChange accStatusChange) throws HsException;
    
    /**
     * 新增批量账户状态变更接口
     * @param List<AccountStatusChange> 账户状态变更记录条件对象集合
     * @throws HsException
     */
    public void addAccStatusChanges(List<AccountStatusChange> accStatusChangeList) throws HsException;
}
