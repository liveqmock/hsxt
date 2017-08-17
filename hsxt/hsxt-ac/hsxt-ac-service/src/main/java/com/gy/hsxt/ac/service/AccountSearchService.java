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

package com.gy.hsxt.ac.service;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsi.lc.client.log4j.BizLog;
import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.ac.api.IAccountSearchService;
import com.gy.hsxt.ac.bean.AccountBalance;
import com.gy.hsxt.ac.bean.AccountCustType;
import com.gy.hsxt.ac.bean.AccountEntry;
import com.gy.hsxt.ac.bean.AccountEntryInfo;
import com.gy.hsxt.ac.bean.AccountEntrySum;
import com.gy.hsxt.ac.bean.AccountStatusChange;
import com.gy.hsxt.ac.bean.AccountType;
import com.gy.hsxt.ac.common.bean.PageContext;
import com.gy.hsxt.ac.mapper.AccountBalanceMapper;
import com.gy.hsxt.ac.mapper.AccountCustTypeMapper;
import com.gy.hsxt.ac.mapper.AccountEntryMapper;
import com.gy.hsxt.ac.mapper.AccountTypeMapper;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;

/**
 * 查找服务业务实现类
 * 
 * @Package: com.gy.hsxt.ac.service
 * @ClassName: SearchAccountService
 * @Description: TODO
 * 
 * @author: weixz
 * @date: 2015-10-12 下午12:16:55
 * @version V1.0
 */

@Service
public class AccountSearchService implements IAccountSearchService {


    /** 账户类型的接口服务 */
    @Autowired
    private AccountTypeMapper accountTypeMapper;

    /** 账户和客户类型关系的接口服务 */
    @Autowired
    private AccountCustTypeMapper accountCustTypeMapper;

    /** 账务分录的接口服务 */
    @Autowired
    AccountEntryMapper accountEntryMapper;

    /** 账务余额的接口服务 */
    @Autowired
    AccountBalanceMapper accountBalanceMapper;

    /**
     * 查询单个账户类型
     * 
     * @param AccountType
     *            账户类型对象，必填（账户类型编码）
     * @return AccountType 账户类型对象
     * @throws HsException
     *             异常处理类
     */
    @Override
    public AccountType searchAccountType(String accType) throws HsException {
        BizLog.biz("HSXT_AC", "方法：searchAccountType", "账户类型编码：accType", "查询单个账户类型");

        // 初始化返回的账户类型对象
        AccountType accountType;
        try
        {
            // 检查参数accType账户类型编码是否为空，为空抛出异常（accType是账户类型的唯一键）
            if (accType == null || "".equals(accType))
            {
                SystemLog.debug("HSXT_AC", "方法：searchAccountType", RespCode.AC_PARAMETER_NULL.getCode() + "accType:账户类型为空");
                throw new HsException(RespCode.AC_PARAMETER_NULL.getCode(), "accType:账户类型为空");
            }
    
            // 根据当前的账户类型编码查询数据中对应的账户类型
            accountType = (AccountType) accountTypeMapper.searchAccountType(accType);
        }
        catch (HsException e)
        {
            SystemLog.error("HSXT_AC", "方法：searchAccountType", e.getErrorCode() + e.getMessage(),e);
            throw new HsException(RespCode.AC_SQL_ERROR.getCode(), e.getMessage());
        }
        catch (Exception e)
        {
            SystemLog.error("HSXT_AC", "方法：searchAccountType", RespCode.AC_SQL_ERROR.getCode() + e.getMessage(),e);
            throw new HsException(RespCode.AC_SQL_ERROR.getCode(), e.getMessage());
        }

        return accountType;
    }

    /**
     * 查询批量账户类型
     * 
     * @param accountType
     *            账户类型对象
     * @return List<AccountType> 账户类型对象集合
     * @throws HsException
     *             异常处理类
     */
    @Override
    public List<AccountType> searchAccountTypeList(AccountType accountType, Page page) throws HsException {
        BizLog.biz("HSXT_AC", "方法：searchAccountTypeList", "账户类型对象：accountType", "查询批量账户类型");
       
        List<AccountType> accountTypeList;// 初始化返回的账户类型对象集合
        try
        {
            if (accountType == null)
            {
                SystemLog.debug("HSXT_AC", "方法：searchAccountTypeList", RespCode.AC_PARAMETER_NULL.getCode()
                        + "accountType:账户类型对象为空");
                throw new HsException(RespCode.AC_PARAMETER_NULL.getCode(), "accountType:账户类型对象为空");
            }
            String beginDate = accountType.getBeginDate();// 开始时间
            String endDate = accountType.getEndDate();// 结束时间
    
            // 验证日期的格式
            Map<String, Timestamp> dateMap = validateDateFormat(beginDate, endDate);
            if (dateMap.get("beginDate") != null)
            {
                accountType.setBeginDateTim(dateMap.get("beginDate"));
            }
            if (dateMap.get("endDate") != null)
            {
                accountType.setEndDateTim(dateMap.get("endDate"));
            }
            PageContext.setPage(page);
       
            // 根据账户类型对象中的条件查询数据中对应的账户类型
            accountTypeList = accountTypeMapper.searchAccountTypeListPage(accountType);
        }
        catch (HsException e)
        {
            SystemLog.error("HSXT_AC", "方法：searchAccountTypeList", e.getErrorCode() + e.getMessage(),e);
            throw new HsException(RespCode.AC_SQL_ERROR.getCode(), e.getMessage());
        }
        catch (Exception e)
        {
            SystemLog.error("HSXT_AC", "方法：searchAccountTypeList", RespCode.AC_SQL_ERROR.getCode() + e.getMessage(),e);
            throw new HsException(RespCode.AC_SQL_ERROR.getCode(), e.getMessage());
        }

        return accountTypeList;
    }

    /**
     * 查询当前账户状态变更记录
     * 
     * @param accStatusChange
     *            账户状态变更对象
     * @param page
     *            分页信息（curPage 当前页,pageSize 每页记录数）
     * @return List<AccountStatusChange> 账户状态变更对象集合
     * @throws HsException
     *             异常处理类
     * @see com.gy.hsxt.ac.api.IAccountTypeService#searchAccStatusChange(com.gy.hsxt.ac.bean.AccountStatusChange,
     *      com.gy.hsxt.common.bean.Page)
     */
    @Override
    public List<AccountStatusChange> searchAccStatusChangeList(AccountStatusChange accStatusChange, Page page)
            throws HsException {
        BizLog.biz("HSXT_AC", "方法：searchAccStatusChange", "账户状态变更对象：accStatusChange,分页信息：page ", "查询当前账户状态变更记录");
        
        List<AccountStatusChange> accStatusChangList;// 初始化返回的账户状态变更对象集合
        try
        {
            if (accStatusChange == null)
            {
                SystemLog.debug("HSXT_AC", "方法：searchAccStatusChange", RespCode.AC_PARAMETER_NULL.getCode()
                        + "accStatusChange:账户状态表变更对象为空");
                throw new HsException(RespCode.AC_PARAMETER_NULL.getCode(), "accStatusChange:账户状态表变更对象为空");
            }
    
            String beginDate = accStatusChange.getBeginDate();// 开始时间
            String endDate = accStatusChange.getEndDate();// 结束时间
    
            // 验证日期的格式
            Map<String, Timestamp> dateMap = validateDateFormat(beginDate, endDate);
            if (dateMap.get("beginDate") != null)
            {
                accStatusChange.setBeginDateTim(dateMap.get("beginDate"));
            }
            if (dateMap.get("endDate") != null)
            {
                accStatusChange.setEndDateTim(dateMap.get("endDate"));
            }
            PageContext.setPage(page);
        
            // 根据账户状态变更对象中的条件查询数据库中对应的账户状态变更记录
            accStatusChangList = accountTypeMapper.searchAccStatusChangeListPage(accStatusChange);
        }
        catch (HsException e)
        {
            SystemLog.error("HSXT_AC", "方法：searchAccStatusChange", e.getErrorCode() + e.getMessage(),e);
            throw new HsException(RespCode.AC_SQL_ERROR.getCode(), e.getMessage());
        }
        catch (Exception e)
        {
            SystemLog.error("HSXT_AC", "方法：searchAccStatusChange", RespCode.AC_SQL_ERROR.getCode() + e.getMessage(),e);
            throw new HsException(RespCode.AC_SQL_ERROR.getCode(), e.getMessage());
        }

        return accStatusChangList;
    }

    /**
     * 查询单个客户类型与账户关系
     * 
     * @param accType
     *            账户类型编码
     * @param custType
     *            客户类型
     * @return CustAccType 客户类型与账户关系对象
     * @throws HsException
     *             异常处理类
     */
    @Override
    public AccountCustType searchCustAccType(Integer custType, String accType) throws HsException {
        BizLog.biz("HSXT_AC", "方法：searchCustAccType", "客户类型 : custType,账户类型编码：accType", "查询单个客户类型与账户关系");

        // 检查(账户类型编码、客户类型)2个字段是否为空，为空抛出异常,不为空则检查账户类型编码的规范
        validateCustAccInfo(custType, accType);
        AccountCustType accountCustType;
        try
        {
            // 根据当前的(账户类型编码、客户类型)唯一键，查询对应的数据库记录
            accountCustType = accountCustTypeMapper.searchCustAccType(custType, accType);
        }
        catch (HsException e)
        {
            SystemLog.error("HSXT_AC", "方法：searchCustAccType", e.getErrorCode() + e.getMessage(),e);
            throw new HsException(RespCode.AC_SQL_ERROR.getCode(), e.getMessage());
        }
        catch (Exception e)
        {
            SystemLog.error("HSXT_AC", "方法：searchCustAccType", RespCode.AC_SQL_ERROR.getCode() + e.getMessage(),e);
            throw new HsException(RespCode.AC_SQL_ERROR.getCode(), e.getMessage());
        }

        return accountCustType;
    }

    /**
     * 查询批量客户类型与账户关系
     * 
     * @param custAccType
     *            客户类型与账户关系对象
     * @return List<CustAccType> 客户类型与账户关系对象集合
     * @throws HsException
     *             异常处理
     */
    @Override
    public List<AccountCustType> searchCustAccTypeList(AccountCustType accountCustType, Page page) throws HsException {
        BizLog.biz("HSXT_AC", "方法：searchCustAccTypeList", "客户类型与账户关系实体 : custAccType", "查询批量客户类型与账户关系");
        if (accountCustType == null)
        {
            SystemLog.debug("HSXT_AC", "方法：searchCustAccTypeList", RespCode.AC_PARAMETER_NULL.getCode()
                    + "custAccType:客户类型与账户关系对象为空");
            throw new HsException(RespCode.AC_PARAMETER_NULL.getCode(), "custAccType:客户类型与账户关系对象为空");
        }
        String beginDate = accountCustType.getBeginDate();// 开始时间
        String endDate = accountCustType.getEndDate();// 结束时间

        // 验证日期的格式
        Map<String, Timestamp> dateMap = validateDateFormat(beginDate, endDate);
        if (dateMap.get("beginDate") != null)
        {
            accountCustType.setBeginDateTim(dateMap.get("beginDate"));
        }
        if (dateMap.get("endDate") != null)
        {
            accountCustType.setEndDateTim(dateMap.get("endDate"));
        }
        PageContext.setPage(page);

        // 检查客户类型与账户关系对象集合中（账户类型编码、客户类型）2个字段是否为空，为空抛出异常,不为空则检查账户类型编码的规范
        // Integer custType = custAccType.getCustType();//客户全局编码
        // String accType = custAccType.getAccType();//账户类型编码
        // validateCustAccInfo(custType,accType);
        List<AccountCustType> accountCustTypeList;
        try
        {
            accountCustTypeList = accountCustTypeMapper.searchCustAccTypeListPage(accountCustType);
        }
        catch (HsException e)
        {
            SystemLog.error("HSXT_AC", "方法：searchCustAccTypeList", e.getErrorCode() + e.getMessage(),e);
            throw new HsException(RespCode.AC_SQL_ERROR.getCode(), e.getMessage());
        }
        catch (Exception e)
        {
            SystemLog.error("HSXT_AC", "方法：searchCustAccTypeList", RespCode.AC_SQL_ERROR.getCode() + e.getMessage(),e);
            throw new HsException(RespCode.AC_SQL_ERROR.getCode(), e.getMessage());
        }

        return accountCustTypeList;
    }

    /**
     * 检查客户类型与账户关系对象集合中（账户类型编码、客户全局编码）2个字段是否为空，为空抛出异常,不为空则检查账户类型编码的规范
     * 
     * @param custType
     *            客户全局编码
     * @param accType
     *            账户类型编码
     * @throws HsException
     *             异常处理
     */
    public void validateCustAccInfo(Integer custType, String accType) throws HsException {
        BizLog.biz("HSXT_AC", "方法：validateCustAccInfo", "客户全局编码：custType,账户类型编码 : accType", "检查客户类型与账户关系对象集合中的字段");
        if (accType == null || "".equals(accType))
        {
            SystemLog.debug("HSXT_AC", "方法：validateCustAccInfo", RespCode.AC_PARAMETER_NULL.getCode()
                    + "accType:账户类型为空");
            throw new HsException(RespCode.AC_PARAMETER_NULL.getCode(), "accType:账户类型为空");
        }
        if (custType == null)
        {
            SystemLog.debug("HSXT_AC", "方法：validateCustAccInfo", RespCode.AC_PARAMETER_NULL.getCode()
                    + "custType:客户类型为空");
            throw new HsException(RespCode.AC_PARAMETER_NULL.getCode(), "custType:客户类型为空");
        }
    }

    /**
     * 正常账户发生汇总额查询
     * 
     * @param accountEntry
     *            账务分录对象
     * @return AccountEntrySum 账务分录会总额对象
     * @throws HsException
     *             异常处理
     */
    @Override
    public AccountEntrySum searchSumAccNormal(AccountEntry accountEntry) throws HsException {
        BizLog.biz("HSXT_AC", "方法：searchSumAccNormal", "账务分录对象：accountEntry", "正常账户发生汇总额查询");
        if (accountEntry == null)
        {
            SystemLog.debug("HSXT_AC", "方法：searchSumAccNormal", RespCode.AC_PARAMETER_NULL.getCode()
                    + "accountEntry:账户分录类为空");
            throw new HsException(RespCode.AC_PARAMETER_NULL.getCode(), "accountEntry:账户分录类为空");
        }
        String beginDate = accountEntry.getBeginDate();// 分录创建时间（开始时间）
        String endDate = accountEntry.getEndDate();// 分录创建时间（结束时间）

        // 验证日期的格式
        Map<String, Timestamp> dateMap = validateDateFormat(beginDate, endDate);
        if (dateMap.get("beginDate") != null)
        {
            accountEntry.setBeginDateTim(dateMap.get("beginDate"));
        }
        if (dateMap.get("endDate") != null)
        {
            accountEntry.setEndDateTim(dateMap.get("endDate"));
        }
        AccountEntrySum accountEntrySum;// 返回的汇总额对象
        try
        {
            accountEntrySum = (AccountEntrySum) accountEntryMapper.searchSumAccNormal(accountEntry);
        }
        catch (HsException e)
        {
            SystemLog.error("HSXT_AC", "方法：searchSumAccNormal", e.getErrorCode() + e.getMessage(),e);
            throw new HsException(RespCode.AC_SQL_ERROR.getCode(), e.getMessage());
        }
        catch (Exception e)
        {
            SystemLog.error("HSXT_AC", "方法：searchSumAccNormal", RespCode.AC_SQL_ERROR.getCode() + e.getMessage(),e);
            throw new HsException(RespCode.AC_SQL_ERROR.getCode(), e.getMessage());
        }
        return setAccountEntrySumInfo(accountEntrySum, accountEntry);
    }

    /**
     * 特殊账户发生汇总额查询
     * 
     * @param accountEntry
     *            账务分录对象
     * @return AccountEntrySum 账务分录汇总对象
     * @throws HsException
     */
    @Override
    public AccountEntrySum searchSumAccSpecial(AccountEntry accountEntry) throws HsException {
        BizLog.biz("HSXT_AC", "方法：searchSumAccSpecial", "账务分录对象：accountEntry", "特殊账户发生汇总额查询");
        if (accountEntry == null)
        {
            SystemLog.debug("HSXT_AC", "方法：searchSumAccSpecial", RespCode.AC_PARAMETER_NULL.getCode()
                    + "accountEntry:账户分录类为空");
            throw new HsException(RespCode.AC_PARAMETER_NULL.getCode(), "accountEntry:账户分录类为空");
        }
        String beginDate = accountEntry.getBeginDate();// 分录创建时间（开始时间）
        String endDate = accountEntry.getEndDate();// 分录创建时间（结束时间）

        // 验证日期的格式
        Map<String, Timestamp> dateMap = validateDateFormat(beginDate, endDate);
        if (dateMap.get("beginDate") != null)
        {
            accountEntry.setBeginDateTim(dateMap.get("beginDate"));
        }
        if (dateMap.get("endDate") != null)
        {
            accountEntry.setEndDateTim(dateMap.get("endDate"));
        }
        AccountEntrySum accountEntrySum;// 返回的汇总额对象
        try
        {
            accountEntrySum = (AccountEntrySum) accountEntryMapper.searchSumAccSpecial(accountEntry);
        }
        catch (HsException e)
        {
            SystemLog.error("HSXT_AC", "方法：searchSumAccSpecial", e.getErrorCode() + e.getMessage(),e);
            throw new HsException(RespCode.AC_SQL_ERROR.getCode(), e.getMessage());
        }
        catch (Exception e)
        {
            SystemLog.error("HSXT_AC", "方法：searchSumAccSpecial", RespCode.AC_SQL_ERROR.getCode() + e.getMessage(),e);
            throw new HsException(RespCode.AC_SQL_ERROR.getCode(), e.getMessage());
        }
        return setAccountEntrySumInfo(accountEntrySum, accountEntry);

    }

    /**
     * 根据AccountEntry账户分录对象类的查询条件，写入给AccountEntrySum汇总对象中。与汇总金额一起返回给接收者。
     * 
     * @param accountEntrySum
     * @param accountEntry
     * @return
     */
    public AccountEntrySum setAccountEntrySumInfo(AccountEntrySum accountEntrySum, AccountEntry accountEntry) {
        BizLog.biz("HSXT_AC", "方法：setAccountEntrySumInfo", "账务分录汇总额对象：accountEntrySum", "封装返回的账务分录汇总额信息");
        if (accountEntrySum == null)
        {
            accountEntrySum = new AccountEntrySum();
        }

        // 客户全局编码
        String custID = accountEntry.getCustID();
        if (custID != null && !"".equals(custID))
        {
            accountEntrySum.setCustID(custID);
        }

        // 账户类型编码
        String accType = accountEntry.getAccType();
        if (accType != null && !"".equals(accType))
        {
            accountEntrySum.setAccType(accType);
        }

        // 互生号
        String hsResNo = accountEntry.getHsResNo();
        if (hsResNo != null && !"".equals(hsResNo))
        {
            accountEntrySum.setHsResNo(hsResNo);
        }

        // 红冲标识
        Integer writeBack = accountEntry.getWriteBack();
        if (writeBack != null)
        {
            accountEntrySum.setWriteBack(writeBack);
        }

        // 交易类型
        String transType = accountEntry.getTransType();
        if (transType != null && !"".equals(transType))
        {
            accountEntrySum.setTransType(transType);
        }

        // 交易流水号
        String transNo = accountEntry.getTransNo();
        if (transNo != null && !"".equals(transNo))
        {
            accountEntrySum.setTransNo(transNo);
        }

        // 关联交易类型
        String relTransType = accountEntry.getRelTransType();
        if (relTransType != null && !"".equals(relTransType))
        {
            accountEntrySum.setRelTransType(relTransType);
        }
        String relTransNo = accountEntry.getRelTransNo();// 关联交易流水号
        if (relTransNo != null && !"".equals(relTransNo))
        {
            accountEntrySum.setRelTransNo(relTransNo);
        }
        String beginDate = accountEntry.getBeginDate();// 开始时间
        if (beginDate != null && !"".equals(beginDate))
        {
            accountEntrySum.setBeginDate(beginDate);
        }
        String endDate = accountEntry.getEndDate();// 结束时间
        if (endDate != null && !"".equals(endDate))
        {
            accountEntrySum.setEndDate(accountEntry.getEndDate());
        }
        return accountEntrySum;
    }

    /**
     * 账户分录查询服务
     * 
     * @param accountEntry
     *            账务分录对象
     * @param page
     *            分页信息 （curPage 当前页,pageSize 每页数）
     * @return AccountEntry
     * @throws HsException
     */
    @Override
    public List<AccountEntry> searchAccEntryList(AccountEntry accountEntry, Page page) throws HsException {
        BizLog.biz("HSXT_AC", "方法：setAccountEntrySumInfo", "账务分录汇总额对象：accountEntrySum", "账户分录查询服务");
        if (accountEntry == null)
        {
            SystemLog.debug("HSXT_AC", "方法：setAccountEntrySumInfo", RespCode.AC_PARAMETER_NULL.getCode()
                    + "accountEntry:账户分录类为空");
            throw new HsException(RespCode.AC_PARAMETER_NULL.getCode(), "accountEntry:账户分录类为空");
        }
        String beginDate = accountEntry.getBeginDate();// 分录创建时间（开始时间）
        String endDate = accountEntry.getEndDate();// 分录创建时间（结束时间）

        // 验证日期的格式
        Map<String, Timestamp> dateMap = validateDateFormat(beginDate, endDate);
        if (dateMap.get("beginDate") != null)
        {
            accountEntry.setBeginDateTim(dateMap.get("beginDate"));
        }
        if (dateMap.get("endDate") != null)
        {
            accountEntry.setEndDateTim(dateMap.get("endDate"));
        }
        PageContext.setPage(page);
        List<AccountEntry> accountEntryeList;
        try
        {
            accountEntryeList = accountEntryMapper.seachAccEntryListPage(accountEntry);
        }
        catch (HsException e)
        {
            SystemLog.error("HSXT_AC", "方法：setAccountEntrySumInfo", e.getErrorCode() + e.getMessage(),e);
            throw new HsException(RespCode.AC_SQL_ERROR.getCode(), e.getMessage());
        }
        catch (Exception e)
        {
            SystemLog.error("HSXT_AC", "方法：setAccountEntrySumInfo", RespCode.AC_SQL_ERROR.getCode() + e.getMessage(),e);
            throw new HsException(RespCode.AC_SQL_ERROR.getCode(), e.getMessage());
        }

        return accountEntryeList;
    }

    /**
     * 账户分录查询服务
     * 
     * @param AccountEntryInfo
     *            账务分录查询条件
     * @param page
     *            分页信息 （curPage 当前页,pageSize 每页数）
     * @return List<AccountEntry> 账务分录对象集合
     * @throws HsException
     */
    @Override
    public PageData<AccountEntry> searchAccEntryList(AccountEntryInfo accountEntryInfo, Page page) throws HsException {
        BizLog.biz("HSXT_AC", "方法：seachAccEntryList", "账务分录汇总额对象：accountEntrySum", "账户分录查询服务");
        
        PageData<AccountEntry> pageData = null;
        try
        {
            if (accountEntryInfo == null)
            {
                SystemLog.debug("HSXT_AC", "方法：seachAccEntryList", RespCode.AC_PARAMETER_NULL.getCode()
                        + "accountEntryInfo:账户分录信息为空");
                throw new HsException(RespCode.AC_PARAMETER_NULL.getCode(), "accountEntryInfo:账户分录信息为空");
            }
            
            if(page == null)
        	{
        		SystemLog.debug("HSXT_AC", "方法：seachAccEntryList", RespCode.AC_PARAMETER_NULL.getCode()
                        + "page:分页信息参数为空");
                throw new HsException(RespCode.AC_PARAMETER_NULL.getCode(), "page:分页信息参数为空");
        	}
            
            PageContext.setPage(page);
            String custId = accountEntryInfo.getCustID();// 客户全局编码
            if (custId == null || "".equals(custId))
            {
                SystemLog
                        .debug("HSXT_AC", "方法：seachAccEntryList", RespCode.AC_PARAMETER_NULL.getCode() + "custID:客户全局编码为空");
                throw new HsException(RespCode.AC_PARAMETER_NULL.getCode(), "custID:客户全局编码为空");
            }
            String accType = accountEntryInfo.getAccType();// 账户类型
            if (accType == null || "".equals(accType))
            {
                SystemLog.debug("HSXT_AC", "方法：seachAccEntryList", RespCode.AC_PARAMETER_NULL.getCode() + "accType:客户类型为空");
                throw new HsException(RespCode.AC_PARAMETER_NULL.getCode(), "accType:账户类型为空");
            }
            String beginDate = accountEntryInfo.getBeginDate();// 开始时间
//            if (beginDate == null || "".equals(beginDate))
//            {
//                SystemLog.debug("HSXT_AC", "方法：seachAccEntryList", RespCode.AC_PARAMETER_NULL.getCode()
//                        + "beginDate:开始时间为空");
//                throw new HsException(RespCode.AC_PARAMETER_NULL.getCode(), "beginDate:开始时间为空");
//            }
            String endDate = accountEntryInfo.getEndDate();// 结束时间
//            if (endDate == null || "".equals(endDate))
//            {
//                SystemLog.debug("HSXT_AC", "方法：seachAccEntryList", RespCode.AC_PARAMETER_NULL.getCode() + "endDate:结束时间为空");
//                throw new HsException(RespCode.AC_PARAMETER_NULL.getCode(), "endDate:结束时间为空");
//            }
    
            Map<String, Timestamp> dateMap = validateDateFormat(beginDate, endDate);
            accountEntryInfo.setBeginDateTim(dateMap.get("beginDate"));
            accountEntryInfo.setEndDateTim(dateMap.get("endDate"));
    
            if (accountEntryInfo.getBusinessType() == null)
            {
                SystemLog.debug("HSXT_AC", "方法：seachAccEntryList", RespCode.AC_PARAMETER_NULL.getCode()
                        + "businessType:业务类型为空");
                throw new HsException(RespCode.AC_PARAMETER_NULL.getCode(), "businessType:业务类型为空");
            }
           
            // 根据分录对象中的条件查找数据库中对应的分录数据
            List<AccountEntry> accountEntryeList = accountEntryMapper.seachAccEntryInfoListPage(accountEntryInfo);

            if (accountEntryeList == null)
            {
                accountEntryeList = new ArrayList<AccountEntry>();
            }
            pageData = new PageData<AccountEntry>(page.getCount(), accountEntryeList);
        }
        catch (HsException e)
        {
            SystemLog.error("HSXT_AC", "方法：seachAccEntryList", e.getErrorCode() + e.getMessage(),e);
            throw e;
        }
        catch (Exception e)
        {
            SystemLog.error("HSXT_AC", "方法：seachAccEntryList", RespCode.AC_SQL_ERROR.getCode() + e.getMessage(),e);
            throw new HsException(RespCode.AC_SQL_ERROR.getCode(), e.getMessage());
        }
        return pageData;
    }

    /**
     * 通过accType账户类型匹配所要查询（个人账户，平台账户，企业账户）的正常账户余额
     * 
     * @param custID:客户全局编号
     *        custType：客户类型编码
     * @return AccountBalance 账户余额实体对象
     * @throws HsException
     */
    @Override
    public AccountBalance searchAccNormal(String custID, String accType) throws HsException {
        BizLog.biz("HSXT_AC", "方法：searchAccNormal", "客户全局编号:custID、客户类型编码：custType",
                "通过accType账户类型匹配所要所要查询（个人账户，平台账户，企业账户）的正常账户余额");
       
        AccountBalance accountBalance;// 初始化账户余额信息
        try
        {
            if ("".equals(custID) || custID == null)
            {
                SystemLog.debug("HSXT_AC", "方法：searchAccNormal", RespCode.AC_PARAMETER_NULL.getCode() + "custID : 客户ID为空");
                throw new HsException(RespCode.AC_PARAMETER_NULL.getCode(), "custID : 客户全局编码为空");
            }
            if ("".equals(accType) || accType == null)
            {
                SystemLog.debug("HSXT_AC", "方法：searchAccNormal", RespCode.AC_PARAMETER_NULL.getCode() + "accType : 账户类型为空");
                throw new HsException(RespCode.AC_PARAMETER_NULL.getCode(), "accType : 账户类型为空");
            }
                
            // 根据当前（custID:客户全局编号、custType：客户类型编码）唯一键查询对应的账户余额信息
            accountBalance = (AccountBalance) accountBalanceMapper.searchAccountBalanceInfo(custID, accType);
        }
        catch (HsException e)
        {
            SystemLog.error("HSXT_AC", "方法：searchAccNormal", e.getErrorCode() + e.getMessage(),e);
            throw new HsException(RespCode.AC_SQL_ERROR.getCode(), e.getMessage());
        }
        catch (Exception e)
        {
            SystemLog.error("HSXT_AC", "方法：searchAccNormal", RespCode.AC_SQL_ERROR.getCode() + e.getMessage(),e);
            throw new HsException(RespCode.AC_SQL_ERROR.getCode(), e.getMessage());
        }

        return accountBalance;
    }

    /**
     * 正常账户余额集合查询
     * 
     * @param accountBalance
     *            ：账户余额对象
     * @param page
     *            分页信息 （curPage 当前页,pageSize 每页数）
     * @return List<AccountBalance> 账户余额对象集合
     * @throws HsException
     */
    @Override
    public List<AccountBalance> searchAccNormalList(AccountBalance accountBalance, Page page) throws HsException {
        BizLog.biz("HSXT_AC", "方法：searchAccNormalList", "账务余额对象:accountBalance、分页信息：page", "正常账户余额集合查询");
       
        List<AccountBalance> accountBalanceList;// 初始化账户余额信息集合
        try
        {
            if (accountBalance == null)
            {
                SystemLog.debug("HSXT_AC", "方法：searchAccNormalList", RespCode.AC_PARAMETER_NULL.getCode()
                        + "accountBalance:账户余额实体类为空");
                throw new HsException(RespCode.AC_PARAMETER_NULL.getCode(), "accountBalance:账户余额实体类为空");
            }
            PageContext.setPage(page);
        
            // 根据账户余额的条件查询对应的正常账户余额信息
            accountBalanceList = accountBalanceMapper.seachAccNormalListPage(accountBalance);
        }
        catch (HsException e)
        {
            SystemLog.error("HSXT_AC", "方法：searchAccNormalList", e.getErrorCode() + e.getMessage(),e);
            throw new HsException(RespCode.AC_SQL_ERROR.getCode(), e.getMessage());
        }
        catch (Exception e)
        {
            SystemLog.error("HSXT_AC", "方法：searchAccNormalList", RespCode.AC_SQL_ERROR.getCode() + e.getMessage(),e);
            throw new HsException(RespCode.AC_SQL_ERROR.getCode(), e.getMessage());
        }

        return accountBalanceList;
    }

    /**
     * 特殊账户余额单查询
     * 
     * @param custID
     *            :客户全局编号
     * @param accType
     *            ：账户类型编码
     * @return AccountBalance 账户余额实体对象
     * @throws HsException
     */
    @Override
    public AccountBalance searchAccSpecial(String custID, String accType) throws HsException {
        BizLog.biz("HSXT_AC", "方法：searchAccSpecial", "客户全局编号:custID、账户类型编码：accType", "特殊账户余额单查询");
        
        AccountBalance accountBalance;// 初始化账户余额信息
        try
        {
            if ("".equals(custID) || custID == null)
            {
                SystemLog.debug("HSXT_AC", "方法：searchAccSpecial", RespCode.AC_PARAMETER_NULL.getCode() + "custID:客户ID为空");
                throw new HsException(RespCode.AC_PARAMETER_NULL.getCode(), "custID:客户全局编码为空");
            }
            if ("".equals(accType) || accType == null)
            {
                SystemLog.debug("HSXT_AC", "方法：searchAccSpecial", RespCode.AC_PARAMETER_NULL.getCode() + "accType:账户类型为空");
                throw new HsException(RespCode.AC_PARAMETER_NULL.getCode(), "accType:账户类型为空");
            }
            
            // 根据账户余额的条件查询对应的特殊账户余额信息
            accountBalance = (AccountBalance) accountBalanceMapper.searchAccountBalanceInfo(custID, accType);
        }
        catch (HsException e)
        {
            SystemLog.error("HSXT_AC", "方法：searchAccSpecial", e.getErrorCode() + e.getMessage(),e);
            throw new HsException(RespCode.AC_SQL_ERROR.getCode(), e.getMessage());
        }
        catch (Exception e)
        {
            SystemLog.error("HSXT_AC", "方法：searchAccSpecial", RespCode.AC_SQL_ERROR.getCode() + e.getMessage(),e);
            throw new HsException(RespCode.AC_SQL_ERROR.getCode(), e.getMessage());
        }

        return accountBalance;
    }

    /**
     * 特殊账户余额集合查询
     * 
     * @param accountBalance
     *            :账户余额对象
     * @pargm page 分页信息 （curPage 当前页,pageSize 每页数）
     * @return List<AccountBalance> 账户余额对象集合
     * @throws HsException
     */
    @Override
    public List<AccountBalance> searchAccSpecialList(AccountBalance accountBalance, Page page) throws HsException {
        BizLog.biz("HSXT_AC", "方法：seachAccSpecialList", "账户余额对象:accountBalance、分页信息：page", "特殊账户余额单查询");
        if (accountBalance == null)
        {
            SystemLog.debug("HSXT_AC", "方法：seachAccSpecialList", RespCode.AC_PARAMETER_NULL.getCode()
                    + "accountBalance:账户余额实体类为空");
            throw new HsException(RespCode.AC_PARAMETER_NULL.getCode(), "accountBalance:账户余额实体类为空");
        }
        PageContext.setPage(page);
        /*String accType = accountBalance.getAccType();
        if (accType != null && !"".equals(accType))
        {
            char accTypes[] = accType.trim().toCharArray();
            if (accTypes.length != ACC_TYPE_LENGTH)
            {
                SystemLog.debug("HSXT_AC", "方法：seachAccSpecialList", RespCode.AC_PARAMETER_FORMAT_ERROR.getCode()
                        + "账户类型:accType = " + accType + "字符串长度应该为5个字符");
                throw new HsException(RespCode.AC_PARAMETER_FORMAT_ERROR.getCode(), "账户类型:accType = " + accType
                        + "字符串长度应该为5个字符");
            }
            if (accTypes[4] == ACC_LAST_CODE_VALUE)
            {
                SystemLog.debug("HSXT_AC", "方法：seachAccSpecialList", RespCode.AC_PARAMETER_FORMAT_ERROR.getCode()
                        + "账户类型:accType = " + accType + "应该为特殊账户类型");
                throw new HsException(RespCode.AC_PARAMETER_FORMAT_ERROR.getCode(), "账户类型:accType = " + accType
                        + "不是特殊账户类型");
            }
        }*/
        List<AccountBalance> accountBalanceList;// 初始化账户余额信息集合
        try
        {
            // 根据账户余额的条件查询对应的特殊账户余额信息
            accountBalanceList = accountBalanceMapper.seachAccSpecialListPage(accountBalance);
        }
        catch (Exception e)
        {
            SystemLog.error("HSXT_AC", "方法：seachAccSpecialList", RespCode.AC_SQL_ERROR.getCode() + e.getMessage(),e);
            throw new HsException(RespCode.AC_SQL_ERROR.getCode(), e.getMessage());
        }

        return accountBalanceList;
    }

    /**
     * 查询单个余额记录对象接口
     * 
     * @param custID
     *            客户全局编码
     * @param accType
     *            账户类型编码
     * @return AccountBalance 余额记录对象
     * @throws HsException
     *             异常处理类
     */
    @Override
    public AccountBalance searchAccBalance(String custID, String accType) throws HsException {
        BizLog.biz("HSXT_AC", "方法：searchAccBalance", "客户全局编码:custID、账户类型编码：accType", "查询单个余额记录对象接口");
        AccountBalance accountbalance;
        try
        {
            if (custID == null || "".equals(custID))
            {
                SystemLog.debug("HSXT_AC", "方法：searchAccBalance", RespCode.AC_PARAMETER_NULL.getCode() + "custID:客户全局编码为空");
                throw new HsException(RespCode.AC_PARAMETER_NULL.getCode(), "custID:客户全局编码为空");
            }
            if (accType == null || "".equals(accType))
            {
                SystemLog
                        .debug("HSXT_AC", "方法：searchAccBalance", RespCode.AC_PARAMETER_NULL.getCode() + "accType:账户类型编码为空");
                throw new HsException(RespCode.AC_PARAMETER_NULL.getCode(), "accType:账户类型编码为空");
            }
            
            // 根据（客户全局编码，账户类型编码）唯一键查找对应的账户余额信息
            accountbalance = accountBalanceMapper.searchAccountBalanceInfo(custID, accType);
        }
        catch (HsException e)
        {
            SystemLog.error("HSXT_AC", "方法：searchAccBalance", e.getErrorCode() + e.getMessage(),e);
            throw new HsException(RespCode.AC_SQL_ERROR.getCode(), e.getMessage());
        }
        catch (Exception e)
        {
            SystemLog.error("HSXT_AC", "方法：searchAccBalance", RespCode.AC_SQL_ERROR.getCode() + e.getMessage(),e);
            throw new HsException(RespCode.AC_SQL_ERROR.getCode(), e.getMessage());
        }
        
        return accountbalance;
    }

    /**
     * 个人今日积分查询
     * 
     * @param custID:客户全局编号
     *        custType：客户类型编码
     * @return AccountBalance 账户余额对象
     * @throws HsException
     */
    @Override
    public AccountEntrySum searchPerIntegralByToday(String custId, String accType) throws HsException {
       
        AccountEntrySum accountEntrySum;// 返回的积分汇总对象
        try
        { 
            if (custId == null || "".equals(custId))
            {
                SystemLog.debug("HSXT_AC", "方法：searchPerIntegralByToday", RespCode.AC_PARAMETER_NULL.getCode()
                        + "custID:客户全局编码为空");
                throw new HsException(RespCode.AC_PARAMETER_NULL.getCode(), "custID:客户全局编码为空");
            }
            if (accType == null || "".equals(accType))
            {
                SystemLog.debug("HSXT_AC", "方法：searchPerIntegralByToday", RespCode.AC_PARAMETER_NULL.getCode()
                        + "accType:账户类型编码为空");
                throw new HsException(RespCode.AC_PARAMETER_NULL.getCode(), "accType:账户类型编码为空");
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();
            String nowDate = sdf.format(date);
    
            // 当天开始时间
            String beginDate = nowDate + " 00:00:00";
            Timestamp beginDateTim = Timestamp.valueOf(beginDate);
    
            // 当天结束时间
            String endDate = nowDate + " 23:59:59";
            Timestamp endDateTim = Timestamp.valueOf(endDate);
    
            // 封装查询条件
            Map<String, Object> conditionMap = new HashMap<String, Object>();
            conditionMap.put("custId", custId);
            conditionMap.put("accType", accType);
            conditionMap.put("beginDate", beginDateTim);
            conditionMap.put("endDate", endDateTim);
    
            
            accountEntrySum = accountEntryMapper.searchPerIntegralByToday(conditionMap);
        }
        catch (HsException e)
        {
            SystemLog.error("HSXT_AC", "方法：searchPerIntegralByToday", e.getErrorCode() + e.getMessage(),e);
            throw new HsException(RespCode.AC_SQL_ERROR.getCode(), e.getMessage());
        }
        catch (Exception e)
        {
            SystemLog.error("HSXT_AC", "方法：searchPerIntegralByToday", RespCode.AC_SQL_ERROR.getCode() + e.getMessage(),e);
            throw new HsException(RespCode.AC_SQL_ERROR.getCode(), e.getMessage());
        }

        return accountEntrySum;
    }

    /**
     * 企业昨日积分查询
     * 
     * @param custID:客户全局编号
     *        custType：客户类型编码
     * @return AccountBalance 账户余额对象
     * @throws HsException
     */
    @Override
    public AccountEntrySum searchEntIntegralByYesterday(String custId, String accType) throws HsException {
        
        AccountEntrySum accountEntrySum;// 返回的积分汇总对象
        try
        {
            if (custId == null || "".equals(custId))
            {
                SystemLog.debug("HSXT_AC", "方法：searchPerIntegralByToday", RespCode.AC_PARAMETER_NULL.getCode()
                        + "custID:客户全局编码为空");
                throw new HsException(RespCode.AC_PARAMETER_NULL.getCode(), "custID:客户全局编码为空");
            }
            if (accType == null || "".equals(accType))
            {
                SystemLog.debug("HSXT_AC", "方法：searchPerIntegralByToday", RespCode.AC_PARAMETER_NULL.getCode()
                        + "accType:账户类型编码为空");
                throw new HsException(RespCode.AC_PARAMETER_NULL.getCode(), "accType:账户类型编码为空");
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DATE, -1);
            String yesterdayDate = sdf.format(calendar.getTime());
    
            // 昨天开始时间
            String beginDate = yesterdayDate + " 00:00:00";
            Timestamp beginDateTim = Timestamp.valueOf(beginDate);
    
            // 昨天结束时间
            String endDate = yesterdayDate + " 23:59:59";
            Timestamp endDateTim = Timestamp.valueOf(endDate);
    
            // 封装查询条件
            Map<String, Object> conditionMap = new HashMap<String, Object>();
            conditionMap.put("custId", custId);
            conditionMap.put("accType", accType);
            conditionMap.put("beginDate", beginDateTim);
            conditionMap.put("endDate", endDateTim);

        
            accountEntrySum = accountEntryMapper.searchEntIntegralByYesterday(conditionMap);
        }
        catch (HsException e)
        {
            SystemLog.error("HSXT_AC", "方法：searchPerIntegralByToday", e.getErrorCode() + e.getMessage(),e);
            throw new HsException(RespCode.AC_SQL_ERROR.getCode(), e.getMessage());
        }
        catch (Exception e)
        {
            SystemLog.error("HSXT_AC", "方法：searchPerIntegralByToday", RespCode.AC_SQL_ERROR.getCode() + e.getMessage(),e);
            throw new HsException(RespCode.AC_SQL_ERROR.getCode(), e.getMessage());
        }

        return accountEntrySum;
    }

    /**
     * 验证开始、结束时间日期格式，开始时间时分秒为00:00:00,结束时间时分秒为23:59:59
     * 
     * @param beginDate
     *            开始时间
     * @param endDate
     *            结束时间
     * @return Map<String,String> 封装时间格式后的开始和结束时间
     * @throws HsException
     *             异常处理
     */
    public Map<String, Timestamp> validateDateFormat(String beginDate, String endDate) throws HsException {
        Map<String, Timestamp> dateMap = new HashMap<String, Timestamp>();

        // 格式化日期并转换日期格式String-->TimeStamp
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        // 开始时间
        if (beginDate != null && !"".equals(beginDate))
        {
            try
            {
                Date date = sdf.parse(beginDate);
                beginDate = sdf.format(date) + " 00:00:00";
                dateMap.put("beginDate", Timestamp.valueOf(beginDate));
            }
            catch (ParseException e)
            {
                SystemLog.debug("HSXT_AC", "方法：validateDateFormat", RespCode.AC_PARAMETER_FORMAT_ERROR.getCode() + ","
                        + beginDate + " = " + beginDate + " ,日期格式错误，正确格式 yyyy-MM-dd");
                throw new HsException(RespCode.AC_PARAMETER_FORMAT_ERROR.getCode(), beginDate + " = " + beginDate
                        + " ,日期格式错误，正确格式 yyyy-MM-dd");
            }
        }

        // 结束时间
        if (endDate != null && !"".equals(endDate))
        {
            try
            {
                Date date = sdf.parse(endDate);
                endDate = sdf.format(date) + " 23:59:59";
                dateMap.put("endDate", Timestamp.valueOf(endDate));
            }
            catch (ParseException e)
            {
                SystemLog.debug("HSXT_AC", "方法：validateDateFormat", RespCode.AC_PARAMETER_FORMAT_ERROR.getCode() + ","
                        + endDate + " = " + endDate + " ,日期格式错误，正确格式 yyyy-MM-dd");
                throw new HsException(RespCode.AC_PARAMETER_FORMAT_ERROR.getCode(), endDate + " = " + endDate
                        + " ,日期格式错误，正确格式 yyyy-MM-dd");
            }
        }
        return dateMap;
    }

    /**
     * 客户全局编码和客户类型对应关系下的所有账户余额查询
     * 
     * @param custID:客户全局编号
     * @return List<AccountBalance> 账户余额对象集合
     * @throws HsException
     */
    @Override
    public List<AccountBalance> searchAccBalanceByCustId(String custId) throws HsException {

        if (custId == null || "".equals(custId))
        {
            SystemLog.debug("HSXT_AC", "方法：searchAccBalanceByCustInfo", RespCode.AC_PARAMETER_NULL.getCode()
                    + "custID:客户全局编码为空");
            throw new HsException(RespCode.AC_PARAMETER_NULL.getCode(), "custID:客户全局编码为空");
        }
        List<AccountBalance> accountBalanceList;// 返回账户余额对象集合

        try
        {
            // 根据客户全局编码和客户类型对应关系下的所有账户余额查询
            accountBalanceList = accountBalanceMapper.searchAccBalanceByCustId(custId);
        }
        catch (Exception e)
        {
            SystemLog.debug("HSXT_AC", "方法：searchAccBalanceByCustInfo", RespCode.AC_SQL_ERROR.getCode()
                    + e.getMessage());
            throw new HsException(RespCode.AC_SQL_ERROR.getCode(), e.getMessage());
        }

        return accountBalanceList;
    }

    /**
     * 客户对应的账户类型分类下的所有该类型账户余额查询
     * 
     * @param custID
     *            客户全局编号
     *        accCategorys
     *            账户分类（1、积分类型，2、互生币类型，3、货币类型、5地区平台银行货币存款/地区平台银行货币现金）
     * @return List<AccountBalance> 账户余额对象集合
     * @throws HsException
     */
    @Override
    public Map<String,AccountBalance> searchAccBalanceByAccCategory(String custId, int accCategory) throws HsException {
        
        Map<String,AccountBalance> accountBalanceMap = null;
       
        List<AccountBalance> accountBalanceList;// 返回账户余额对象集合
        try
        {
            if (custId == null || "".equals(custId))
            {
                SystemLog.debug("HSXT_AC", "方法：searchAccBalanceByAccCategory", RespCode.AC_PARAMETER_NULL.getCode()
                        + "custID:客户全局编码参数为空");
                throw new HsException(RespCode.AC_PARAMETER_NULL.getCode(), "custID:客户全局编码为空");
            }

        
            // 客户对应的账户类型分类下的所有该类型账户余额查询
            accountBalanceList = accountBalanceMapper.searchAccBalanceByAccCategory(custId, accCategory);
            
            if(!accountBalanceList.isEmpty())
            {
                accountBalanceMap = new HashMap<String,AccountBalance>();
                for(AccountBalance accountBalance :accountBalanceList)
                {
                    accountBalanceMap.put(accountBalance.getAccType(), accountBalance);
                }
            }
        }
        catch (HsException e)
        {
            SystemLog.error("HSXT_AC", "方法：searchAccBalanceByAccCategory", e.getErrorCode()+ e.getMessage(),e);
            throw new HsException(RespCode.AC_SQL_ERROR.getCode(), e.getMessage());
        }
        catch (Exception e)
        {
            SystemLog.error("HSXT_AC", "方法：searchAccBalanceByAccCategory", RespCode.AC_SQL_ERROR.getCode()
                    + e.getMessage(),e);
            throw new HsException(RespCode.AC_SQL_ERROR.getCode(), e.getMessage());
        }

        return accountBalanceMap;
    }

    /**
     * 同一客户的多个账户余额查询
     * @param custId 客户全局编号
     * @param accTypes 账户类型数组
     * @return Map<String,AccountBalance> 账户余额对象Map集合
     * @throws HsException
     */
    public Map<String,AccountBalance> searchAccBalanceByCustIdAndAccType(String custId, String[] accTypes) throws HsException{
    	Map<String,AccountBalance> accountBalanceMap = null;
    	
    	List<AccountBalance> accountBalanceList;// 返回账户余额对象集合
        try
        {
        	if (custId == null || "".equals(custId))
            {
                SystemLog.debug("HSXT_AC", "方法：searchAccBalanceByCustIdAndAccType", RespCode.AC_PARAMETER_NULL.getCode()
                        + "custID:客户全局编码参数为空");
                throw new HsException(RespCode.AC_PARAMETER_NULL.getCode(), "custID:客户全局编码为空");
            }
        	
        	if (accTypes == null || accTypes.length == 0)
            {
                SystemLog.debug("HSXT_AC", "方法：searchAccBalanceByCustIdAndAccType", RespCode.AC_PARAMETER_NULL.getCode()
                        + "accTypes:账户类型参数为空");
                throw new HsException(RespCode.AC_PARAMETER_NULL.getCode(), "accTypes:账户类型参数为空");
            }
        	
            // 同一客户的多个账户余额查询
            accountBalanceList = accountBalanceMapper.searchAccBalanceByCustIdAndAccType(custId, accTypes);
            
            if(!accountBalanceList.isEmpty())
            {
                accountBalanceMap = new HashMap<String,AccountBalance>();
                for(AccountBalance accountBalance :accountBalanceList)
                {
                    accountBalanceMap.put(accountBalance.getAccType(), accountBalance);
                }
            }
        }
        catch (HsException e)
        {
            SystemLog.error("HSXT_AC", "方法：searchAccBalanceByCustIdAndAccType", e.getErrorCode()
                    + e.getMessage(),e);
            throw new HsException(RespCode.AC_SQL_ERROR.getCode(), e.getMessage());
        }
        catch (Exception e)
        {
            SystemLog.error("HSXT_AC", "方法：searchAccBalanceByCustIdAndAccType", RespCode.AC_SQL_ERROR.getCode()
                    + e.getMessage(),e);
            throw new HsException(RespCode.AC_SQL_ERROR.getCode(), e.getMessage());
        }
    	
    	return accountBalanceMap;
    }
    
    /**
     * 同一客户的多个账户分录查询服务
     * @param AccountEntryInfo 账务分录查询条件
     * @param page 分页信息
     * @return PageData<AccountEntry> 分录对象集合
     * @throws HsException
     */
    public PageData<AccountEntry> searchAccEntryListByCustIdAndAccType(AccountEntryInfo accountEntryInfo, Page page) throws HsException{
    	
        PageData<AccountEntry> pageData = null;
        
        try
        {
        	String custId = accountEntryInfo.getCustID();
        	if (custId == null || "".equals(custId))
            {
                SystemLog.debug("HSXT_AC", "方法：searchAccEntryListByCustIdAndAccType", RespCode.AC_PARAMETER_NULL.getCode()
                        + "custID:客户全局编码参数为空");
                throw new HsException(RespCode.AC_PARAMETER_NULL.getCode(), "custID:客户全局编码为空");
            }
        	
        	String[] accTypes = accountEntryInfo.getAccTypes();
        	if (accTypes == null || accTypes.length == 0)
            {
                SystemLog.debug("HSXT_AC", "方法：searchAccEntryListByCustIdAndAccType", RespCode.AC_PARAMETER_NULL.getCode()
                        + "accTypes:账户类型参数为空");
                throw new HsException(RespCode.AC_PARAMETER_NULL.getCode(), "accTypes:账户类型参数为空");
            }
        	
        	if(page == null)
        	{
        		SystemLog.debug("HSXT_AC", "方法：searchAccEntryListByCustIdAndAccType", RespCode.AC_PARAMETER_NULL.getCode()
                        + "page:分页信息参数为空");
                throw new HsException(RespCode.AC_PARAMETER_NULL.getCode(), "page:分页信息参数为空");
        	}
        	
        	String beginDate = accountEntryInfo.getBeginDate();
        	if (beginDate == null || "".equals(beginDate))
            {
                SystemLog.debug("HSXT_AC", "方法：searchAccEntryListByCustIdAndAccType", RespCode.AC_PARAMETER_NULL.getCode()
                        + "beginDate:开始时间为空");
                throw new HsException(RespCode.AC_PARAMETER_NULL.getCode(), "beginDate:开始时间为空");
            }
        	
        	String endDate = accountEntryInfo.getEndDate();
            if (endDate == null || "".equals(endDate))
            {
                SystemLog.debug("HSXT_AC", "方法：searchAccEntryListByCustIdAndAccType", RespCode.AC_PARAMETER_NULL.getCode() + "endDate:结束时间为空");
                throw new HsException(RespCode.AC_PARAMETER_NULL.getCode(), "endDate:结束时间为空");
            }
            
            //验证开始、结束时间日期格式，开始时间时分秒为00:00:00,结束时间时分秒为23:59:59
            //封装时间格式后的开始和结束时间为Timestamp类型
            Map<String, Timestamp> dateMap = validateDateFormat(beginDate, endDate);
            accountEntryInfo.setBeginDateTim(dateMap.get("beginDate"));
            accountEntryInfo.setEndDateTim(dateMap.get("endDate"));
            
            
        	//分页信息存储
        	PageContext.setPage(page);
        	
        	String accType = "";
        	for(int i=0;i<accTypes.length;i++){
        		 accType = accType+"'"+accTypes[i]+"'";
        		 if(i != accTypes.length - 1)
        		 {
        			 accType = accType + ", ";
        		 }
        	}
        	accountEntryInfo.setAccType(accType);
        	
            // 根据分录对象中的条件查找数据库中对应的分录数据
            List<AccountEntry> accountEntryeList = accountEntryMapper.searchAccEntryListByCustIdAndAccTypeListPage(accountEntryInfo);

            if (accountEntryeList == null)
            {
                accountEntryeList = new ArrayList<AccountEntry>();
            }
            pageData = new PageData<AccountEntry>(page.getCount(), accountEntryeList);
        }
        catch (HsException e)
        {
            SystemLog.error("HSXT_AC", "方法：seachAccEntryList", e.getErrorCode() + e.getMessage(),e);
            throw new HsException(RespCode.AC_SQL_ERROR.getCode(), e.getMessage());
        }
        catch (Exception e)
        {
            SystemLog.error("HSXT_AC", "方法：seachAccEntryList", RespCode.AC_SQL_ERROR.getCode() + e.getMessage(),e);
            throw new HsException(RespCode.AC_SQL_ERROR.getCode(), e.getMessage());
        }
    	
    	return pageData;
    }
}
