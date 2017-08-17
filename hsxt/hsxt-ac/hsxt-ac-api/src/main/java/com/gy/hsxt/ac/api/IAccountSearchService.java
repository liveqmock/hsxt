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
import java.util.Map;

import com.gy.hsxt.ac.bean.AccountBalance;
import com.gy.hsxt.ac.bean.AccountCustType;
import com.gy.hsxt.ac.bean.AccountEntry;
import com.gy.hsxt.ac.bean.AccountEntryInfo;
import com.gy.hsxt.ac.bean.AccountEntrySum;
import com.gy.hsxt.ac.bean.AccountStatusChange;
import com.gy.hsxt.ac.bean.AccountType;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;

/** 
 * 查询账务
 * @Package: com.gy.hsxt.ac.api  
 * @ClassName: ISearchAccountService 
 * @Description: TODO
 *
 * @author: weixz 
 * @date: 2015-10-12 上午11:14:18 
 * @version V1.0 
 
 */
public interface IAccountSearchService {

    /**
     * 查询单个账户类型
     * @param accType 账户类型编码，
     * @return AccountType 账户类型对象
     * @throws HsException 异常处理类
     */
    public AccountType searchAccountType(String accType)throws HsException;
    
    /**
     * 查询多个账户类型
     * @param accountType 账户类型对象
     * @throws HsException 异常处理类
     */
    public List<AccountType> searchAccountTypeList(AccountType accountType, Page page) throws HsException;
    
    /**
     * 账户状态变更记录获取
     * @param accStatusChange 账户状态变更对象，
     * @param page： 分页信息 。
     * @return List<AccountStatusChange> 账户状态变更对象集合 
     * @throws HsException
     */
    public List<AccountStatusChange> searchAccStatusChangeList(AccountStatusChange accStatusChange, Page page) throws HsException;
    
    /**
     * 客户类型与账户关系单个查询接口定义
     * @param accType 账户类型编码
     * @param custType 客户类型
     * @return AccountCustType 客户类型与账户关系对象
     * @throws HsException 异常处理类
     */
    public AccountCustType searchCustAccType(Integer custType, String accType) throws HsException;
    
    /**
     * 客户类型与账户关系批量查询接口定义
     * @param accountCustType 客户类型与账户关系对象
     * @param Page 分页信息（当前页：curPage,每页记录数：pageSize）
     * @return List<AccountCustType> 客户类型与账户关系对象集合
     * @throws HsException 异常处理类
     */
    public List<AccountCustType> searchCustAccTypeList(AccountCustType accountCustType,Page page) throws HsException;
    
    /**
     * 正常账户发生汇总额查询
     * @param accountEntry 汇总条件封装成的正常账户分录对象
     * @return AccountEntrySum 分录汇总对象
     * @throws HsException
     */
    public AccountEntrySum searchSumAccNormal(AccountEntry accountEntry) throws HsException;
    
    /**
     * 特殊账户发生汇总额查询
     * @param accountEntry 汇总条件封装成的特殊账户分录对象
     * @return AccountEntrySum 分录汇总对象
     * @throws HsException
     */ 
    public AccountEntrySum searchSumAccSpecial(AccountEntry accountEntry) throws HsException;
    

    /**
     * 账户分录查询服务
     * @param accountEntry 查询条件封装成的分录信息对象
     * @param page 分页信息
     * @return List<AccountEntry> 分录对象集合
     * @throws HsException
     */ 
    public List<AccountEntry> searchAccEntryList(AccountEntry accountEntry, Page page) throws HsException;
    
    /**
     * 账户分录查询服务
     * @param AccountEntryInfo 查询条件封装成的分录信息对象
     * @param page 分页信息
     * @return List<AccountEntry> 分录对象集合
     * @throws HsException
     */ 
    public PageData<AccountEntry> searchAccEntryList(AccountEntryInfo accountEntryInfo, Page page) throws HsException;
    
    /**
     * 查询单个余额记录对象接口
     * @param custID 客户全局编码
     * @param accType 账户类型编码
     * @return AccountBalance 余额记录对象
     * @throws HsException
     */
    public AccountBalance searchAccBalance(String custID, String accType) throws HsException;
    
    /**
     * 正常账户余额单查询
     * @param custID:客户全局编号、custType：客户类型编码
     * @return AccountBalance 账户余额实体对象
     * @throws HsException
     */
    public AccountBalance searchAccNormal(String custID,String accType) throws HsException;
    
    /**
     * 正常账户余额集合查询
     * @param accountBalance 正常账户余额对象
     * @param page 分页信息
     * @return List<AccountBalance> 账户余额对象集合
     * @throws HsException
     */
    public List<AccountBalance> searchAccNormalList(AccountBalance accountBalance,Page page) throws HsException;
    
    /**
     * 特殊账户余额单查询
     * @param custID:客户全局编号、custType：客户类型编码
     * @return AccountBalance 特殊账户余额对象
     * @throws HsException
     */
    public AccountBalance searchAccSpecial(String custID, String accType) throws HsException;
    
    /**
     * 特殊账户余额集合查询
     * @param accountBalance 特殊账户余额对象
     * @param page 分页信息
     * @return List<AccountBalance> 特殊账户余额对象集合
     * @throws HsException
     */
    public List<AccountBalance> searchAccSpecialList(AccountBalance accountBalance, Page page) throws HsException;
    
    /**
     * 个人今日积分查询
     * @param custID:客户全局编号、accType：账户类型编码
     * @return AccountBalance 账户余额对象
     * @throws HsException
     */
    public AccountEntrySum searchPerIntegralByToday(String custId, String accType) throws HsException;

    /**
     * 企业昨日积分查询
     * @param custID:客户全局编号、accType：账户类型编码
     * @return AccountBalance 账户余额对象
     * @throws HsException
     */
    public AccountEntrySum searchEntIntegralByYesterday(String custId, String accType) throws HsException;
    
    /**
     * 客户全局编码对应的所有账户余额查询
     * @param custID:客户全局编号
     * @return List<AccountBalance> 账户余额对象集合
     * @throws HsException
     */
    public List<AccountBalance> searchAccBalanceByCustId(String custId) throws HsException;
    
    /**
     * 客户对应的账户类型分类下的所有账户余额查询
     * @param custID:客户全局编号 accCategory 账户分类（1、积分类型，2、互生币类型，3、货币类型、5地区平台银行货币存款/地区平台银行货币现金）
     * @return Map<String,AccountBalance> 账户余额对象Map集合
     * @throws HsException
     */
    public Map<String,AccountBalance> searchAccBalanceByAccCategory(String custId, int accCategory) throws HsException;
    
    /**
     * 同一客户的多个账户余额查询
     * @param custId 客户全局编号
     * @param accTypes 账户类型数组
     * @return Map<String,AccountBalance> 账户余额对象Map集合
     * @throws HsException
     */
    public Map<String,AccountBalance> searchAccBalanceByCustIdAndAccType(String custId, String[] accTypes) throws HsException;
    
    /**
     * 同一客户的多个账户分录查询服务
     * @param AccountEntryInfo 账务分录查询条件
     * @param page 分页信息
     * @return PageData<AccountEntry> 分录对象集合
     * @throws HsException
     */
    public PageData<AccountEntry> searchAccEntryListByCustIdAndAccType(AccountEntryInfo accountEntryInfo, Page page) throws HsException;
}
