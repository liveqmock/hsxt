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

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsi.lc.client.log4j.BizLog;
import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.ac.api.IAccountAdminService;
import com.gy.hsxt.ac.bean.AccountBalance;
import com.gy.hsxt.ac.bean.AccountCustType;
import com.gy.hsxt.ac.bean.AccountStatusChange;
import com.gy.hsxt.ac.bean.AccountType;
import com.gy.hsxt.ac.common.bean.AcConstant;
import com.gy.hsxt.ac.common.bean.PropertyConfigurer;
import com.gy.hsxt.ac.mapper.AccountBalanceMapper;
import com.gy.hsxt.ac.mapper.AccountCustTypeMapper;
import com.gy.hsxt.ac.mapper.AccountTypeMapper;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;

/**
 * 账户管理业务实现类
 * 
 * @Package: com.gy.hsxt.ac.service
 * @ClassName: AccountAdminService
 * @Description: TODO
 * 
 * @author: weixz
 * @date: 2015-10-12 上午11:46:34
 * @version V1.0
 */
@Service
public class AccountAdminService implements IAccountAdminService {


    /** 账户类型的接口服务 */
    @Autowired
    private AccountTypeMapper accountTypeMapper;

    /** 账户和客户类型关系的接口服务 */
    @Autowired
    private AccountCustTypeMapper accountCustTypeMapper;

    /** 账户余额的接口服务 */
    @Autowired
    private AccountBalanceMapper accountBalanceMapper;

    /**
     * 新增单个账户类型
     * 
     * @param accountType
     *            账户类型对象 ，必填(账户类型编码、账户类型名称、币种)
     */
    @Override
    public void addAccType(AccountType accountType) throws HsException {
        BizLog.biz("HSXT_AC", "方法：addAccType", "账户类型对象:accountType", "新增单个账户类型");
        try
        {

            // 检查账户类型对象集合中（账户类型编码、账户类型名称、币种）3个字段是否为空，为空抛出异常,不为空则检查账户类型编码的规范
            String accType = accountType.getAccType();// 账户类型编码
            if (accType == null || "".equals(accType))
            {
                SystemLog.debug("HSXT_AC", "方法：addAccType", RespCode.AC_PARAMETER_NULL.getCode() + "accType:账户类型编码为空");
                throw new HsException(RespCode.AC_PARAMETER_NULL.getCode(), "accType:账户类型编码为空");
            }

            // 检查是否已经存在当前账户类型编码的账户
            AccountType account_Type = (AccountType) accountTypeMapper.searchAccountType(accType);
            if (account_Type != null)
            {
                SystemLog.debug("HSXT_AC", "方法：addAccType", RespCode.AC_REPEATED_DATA.getCode() + "accType = "
                        + accType + ":该账户类型已经存在");
                throw new HsException(RespCode.AC_REPEATED_DATA.getCode(), "accType = " + accType + ":该账户类型已经存在");
            }
            String accName = accountType.getAccName();// 账户类型名称
            if (accName == null || "".equals(accName))
            {
                SystemLog.debug("HSXT_AC", "方法：addAccType", RespCode.AC_PARAMETER_NULL.getCode() + "accName:账户名称为空");
                throw new HsException(RespCode.AC_PARAMETER_NULL.getCode(), "accName:账户名称为空");
            }
            String currencyCode = accountType.getCurrencyCode();// 账户币种
            if (currencyCode == null || "".equals(currencyCode))
            {
                SystemLog.debug("HSXT_AC", "方法：addAccType", RespCode.AC_PARAMETER_NULL.getCode()
                        + "currencyCode:账户币种为空");
                throw new HsException(RespCode.AC_PARAMETER_NULL.getCode(), "currencyCode:账户币种为空");
            }

            // 将参数中账户类型实体集合插入到数据库中
            accountTypeMapper.addAccType(accountType);
        }
        catch (HsException e)
        {
            SystemLog.error("HSXT_AC", "方法：addAccType", e.getErrorCode()+e.getMessage() ,e);
            throw new HsException(RespCode.AC_SQL_ERROR.getCode(), e.getMessage());
        }
        catch (Exception e)
        {
            SystemLog.error("HSXT_AC", "方法：addAccType", RespCode.AC_SQL_ERROR.getCode() + e.getMessage(),e);
            throw new HsException(RespCode.AC_SQL_ERROR.getCode(), e.getMessage());
        }
    }

    /**
     * 新增批量账户类型
     * 
     * @param accountTypeList
     *            账户类型实体对象集合 ，必填(每个对象中的账户类型编码、账户类型名称、币种)
     */
    @Override
    public void addAccTypes(List<AccountType> accountTypeList) throws HsException {
        BizLog.biz("HSXT_AC", "方法：addAccTypes", "账户类型对象集合:accountTypeList", "新增批量账户类型");
        if (accountTypeList.isEmpty())
        {
            SystemLog.debug("HSXT_AC", "方法：addAccTypes", RespCode.AC_PARAMETER_NULL.getCode()
                    + "accountTypeList 账户类型集合为空");
            throw new HsException(RespCode.AC_PARAMETER_NULL.getCode(), "方法：addAccTypes,参数：accountTypeList 账户类型集合为空");
        }
        try
        {

            // 遍历检查账户类型对象集合中（账户类型编码、账户类型名称、币种）3个字段是否为空，为空抛出异常，不为空则检查账户类型编码的规范
            for (AccountType accountType : accountTypeList)
            {
                String accType = accountType.getAccType();// 账户类型编码
                if (accType == null || "".equals(accType))
                {
                    SystemLog.debug("HSXT_AC", "方法：addAccTypes", RespCode.AC_PARAMETER_NULL.getCode()
                            + "accType:账户类型编码为空");
                    throw new HsException(RespCode.AC_PARAMETER_NULL.getCode(), "accType:账户类型编码为空");
                }

                // 检查是否已经存在当前账户类型编码的账户
                AccountType account_Type = (AccountType) accountTypeMapper.searchAccountType(accType);
                if (account_Type != null)
                {
                    SystemLog.debug("HSXT_AC", "方法：addAccTypes", RespCode.AC_REPEATED_DATA.getCode() + "accType = "
                            + accType + ":该账户类型已经存在");
                    throw new HsException(RespCode.AC_REPEATED_DATA.getCode(), "accType = " + accType + ":该账户类型已经存在");
                }
                String accName = accountType.getAccName();// 账户类型名称
                if (accName == null || "".equals(accName))
                {
                    SystemLog.debug("HSXT_AC", "方法：addAccTypes", RespCode.AC_PARAMETER_NULL.getCode()
                            + "accName:账户名称为空");
                    throw new HsException(RespCode.AC_PARAMETER_NULL.getCode(), "accName:账户名称为空");
                }
                String currencyCode = accountType.getCurrencyCode();// 账户币种
                if (currencyCode == null || "".equals(currencyCode))
                {
                    SystemLog.debug("HSXT_AC", "方法：addAccTypes", RespCode.AC_PARAMETER_NULL.getCode()
                            + "currencyCode:账户币种为空");
                    throw new HsException(RespCode.AC_PARAMETER_NULL.getCode(), "currencyCode:账户币种为空");
                }
            }
            
            // 将参数中账户类型实体集合插入到数据库中
            accountTypeMapper.addAccTypes(accountTypeList);
        }
        catch (HsException e)
        {
            SystemLog.error("HSXT_AC", "方法：addAccTypes", e.getErrorCode()+e.getMessage() ,e);
            throw new HsException(RespCode.AC_SQL_ERROR.getCode(), e.getMessage());
        }
        catch (Exception e)
        {
            SystemLog.error("HSXT_AC", "方法：addAccTypes", RespCode.AC_SQL_ERROR.getCode() + e.getMessage(),e);
            throw new HsException(RespCode.AC_SQL_ERROR.getCode(), e.getMessage());
        }
    }

    /**
     * 修改单个账户类型
     * 
     * @param accountTypeList
     *            账户类型实体集合
     * @throws HsException
     *             异常处理类
     */
    @Override
    public void updateAccType(AccountType accountType) throws HsException {
        BizLog.biz("HSXT_AC", "方法：updateAccType", "账户类型对象:accountType", "修改单个账户类型");
        try
        {
            if (accountType == null)
            {
                SystemLog.debug("HSXT_AC", "方法：updateAccType", RespCode.AC_PARAMETER_NULL.getCode()
                        + "方法：updateAccType,参数：accountType 账户类型对象为空");
                throw new HsException(RespCode.AC_PARAMETER_NULL.getCode(), "方法：updateAccType,参数：accountType 账户类型对象为空");
            }
    
            // 检查账户类型对象集合中（账户类型编码）字段是否为空，为空抛出异常
            String accType = accountType.getAccType();
            if (accType == null || "".equals(accType))
            {
                SystemLog.debug("HSXT_AC", "方法：updateAccType", RespCode.AC_PARAMETER_NULL.getCode() + "accType:账户类型编码为空");
                throw new HsException(RespCode.AC_PARAMETER_NULL.getCode(), "accType:账户类型编码为空");
            }

            // 将账户类型对象更新到数据库
            accountTypeMapper.updateAccType(accountType);
        }
        catch (HsException e)
        {
            SystemLog.error("HSXT_AC", "方法：updateAccType", e.getErrorCode()+e.getMessage() ,e);
            throw new HsException(RespCode.AC_SQL_ERROR.getCode(), e.getMessage());
        }
        catch (Exception e)
        {
            SystemLog.error("HSXT_AC", "方法：updateAccType", RespCode.AC_SQL_ERROR.getCode() + e.getMessage(),e);
            throw new HsException(RespCode.AC_SQL_ERROR.getCode(), e.getMessage());
        }
    }

    /**
     * 修改批量账户类型
     * 
     * @param accountTypeList
     *            账户类型实体集合
     * @throws HsException
     *             异常处理类
     */
    @Override
    public void updateAccTypes(List<AccountType> accountTypeList) throws HsException {
        BizLog.biz("HSXT_AC", "方法：updateAccTypes", "账户类型对象集合:accountTypeList", "修改批量账户类型");
        try
        {
            if (accountTypeList.isEmpty())
            {
                SystemLog.debug("HSXT_AC", "方法：updateAccType", RespCode.AC_PARAMETER_NULL.getCode()
                        + "方法：updateAccTypes,参数：accountTypeList 账户类型对象集合为空");
                throw new HsException(RespCode.AC_PARAMETER_NULL.getCode(), "方法：updateAccTypes,参数：accountTypeList 账户类型集合为空");
            }
    
            // 遍历检查账户类型对象集合中（账户类型编码）字段是否为空，为空抛出异常
            for (AccountType accountType : accountTypeList)
            {
                String accType = accountType.getAccType();
                if (accType == null || "".equals(accType))
                {
                    SystemLog.debug("HSXT_AC", "方法：updateAccTypes", RespCode.AC_PARAMETER_NULL.getCode()
                            + "accType:账户类型编码为空");
                    throw new HsException(RespCode.AC_PARAMETER_NULL.getCode(), "accType:账户类型编码为空");
                }
            }
            // 将账户类型对象集合更新到数据库
            accountTypeMapper.updateAccTypes(accountTypeList);
        }
        catch (HsException e)
        {
            SystemLog.error("HSXT_AC", "方法：updateAccTypes", e.getErrorCode() + e.getMessage(),e);
            throw new HsException(RespCode.AC_SQL_ERROR.getCode(), e.getMessage());
        }
        catch (Exception e)
        {
            SystemLog.error("HSXT_AC", "方法：updateAccTypes", RespCode.AC_SQL_ERROR.getCode() + e.getMessage(),e);
            throw new HsException(RespCode.AC_SQL_ERROR.getCode(), e.getMessage());
        }
    }

    /**
     * 删除单个账户类型
     * 
     * @param accountType
     *            账户类型实体
     * @throws HsException
     *             异常处理类
     */
    @Override
    public void deleteAccountType(String accType) throws HsException {
        BizLog.biz("HSXT_AC", "方法：deleteAccountType", "账户类型编码:accType", "删除单个账户类型");

        try
        {
            // 检查账户类型编码字段是否为空，为空抛出异常
            if (accType == null || "".equals(accType))
            {
                SystemLog.debug("HSXT_AC", "方法：deleteAccountType", RespCode.AC_PARAMETER_NULL.getCode()
                        + "accType:账户类型编码为空");
                throw new HsException(RespCode.AC_PARAMETER_NULL.getCode(), "accType:账户类型编码为空");
            }

            // 将当前账户类型编码从数据库中删除
            accountTypeMapper.deleteAccountType(accType);
        }
        catch (HsException e)
        {
            SystemLog.error("HSXT_AC", "方法：deleteAccountType", e.getErrorCode() + e.getMessage(),e);
            throw new HsException(RespCode.AC_SQL_ERROR.getCode(), e.getMessage());
        }
        catch (Exception e)
        {
            SystemLog.error("HSXT_AC", "方法：deleteAccountType", RespCode.AC_SQL_ERROR.getCode() + e.getMessage(),e);
            throw new HsException(RespCode.AC_SQL_ERROR.getCode(), e.getMessage());
        }
    }

    /**
     * 删除批量账户类型
     * 
     * @param accountTypeList
     *            账户类型实体集合
     * @throws HsException
     *             异常处理类
     */
    @Override
    public void deleteAccountTypes(List<AccountType> accountTypeList) throws HsException {
        BizLog.biz("HSXT_AC", "方法：deleteAccountTypes", "账户类型对象集合:accountTypeList", "删除批量账户类型");
        try
        { 
            if (accountTypeList.isEmpty())
                {
                    SystemLog.debug("HSXT_AC", "方法：deleteAccountTypes", RespCode.AC_PARAMETER_NULL.getCode()
                            + "方法：deleteAccountTypes,参数：accountTypeList 账户类型集合为空");
                    throw new HsException(RespCode.AC_PARAMETER_NULL.getCode(),
                            "方法：deleteAccountTypes,参数：accountTypeList 账户类型集合为空");
                }
        
                // 遍历检查账户类型对象集合中（账户类型编码）字段是否为空，为空抛出异常
                for (AccountType accountType : accountTypeList)
                {
                    String accType = accountType.getAccType();
                    if (accType == null || "".equals(accType))
                    {
                        SystemLog.debug("HSXT_AC", "方法：deleteAccountTypes", RespCode.AC_PARAMETER_NULL.getCode()
                                + "accType:账户类型编码为空");
                        throw new HsException(RespCode.AC_PARAMETER_NULL.getCode(), "accType:账户类型编码为空");
                    }
                }

            // 将账户类型对象集合从数据库中删除
            accountTypeMapper.deleteAccountTypes(accountTypeList);
        }
        catch (HsException e)
        {
            SystemLog.error("HSXT_AC", "方法：deleteAccountTypes", e.getErrorCode() + e.getMessage(),e);
            throw new HsException(RespCode.AC_SQL_ERROR.getCode(), e.getMessage());
        }
        catch (Exception e)
        {
            SystemLog.error("HSXT_AC", "方法：deleteAccountTypes", RespCode.AC_SQL_ERROR.getCode() + e.getMessage(),e);
            throw new HsException(RespCode.AC_SQL_ERROR.getCode(), e.getMessage());
        }
    }

    /**
     * 新增单个客户类型与账户关系对象
     * 
     * @param custAccType
     *            客户类型与账户关系对象
     * @throws HsException
     *             异常处理类
     */
    @Override
    public void addCustAccType(AccountCustType accountCustType) throws HsException {
        BizLog.biz("HSXT_AC", "方法：addCustAccType", "客户类型与账户关系对象:custAccType", "新增单个客户类型与账户关系对象");
        try
        {  
            if (accountCustType == null)
                {
                    SystemLog.debug("HSXT_AC", "方法：addCustAccType", RespCode.AC_PARAMETER_NULL.getCode()
                            + "custAccType:客户类型与账户关系对象为空");
                    throw new HsException(RespCode.AC_PARAMETER_NULL.getCode(), "custAccType:客户类型与账户关系对象为空");
                }
        
                // 检查客户类型与账户关系对象集合中（账户类型编码、客户全局编码）2个字段是否为空，为空抛出异常,不为空则检查账户类型编码的规范
                Integer custType = accountCustType.getCustType();// 客户全局编码
                String accType = accountCustType.getAccType();// 账户类型编码
                validateCustAccInfo(custType, accType);
        
                // 查看是否存在该客户类型
                checkCustTypeCategory(custType);
                
    
                // 检查是否已经存在当前客户类型与账户关系对象
                AccountCustType custAcc_Type = (AccountCustType) accountCustTypeMapper.searchCustAccType(custType, accType);
                if (custAcc_Type != null)
                {
                    SystemLog.debug("HSXT_AC", "方法：addCustAccType", RespCode.AC_REPEATED_DATA.getCode() + "accType = "
                            + accType + ",custType = " + custType + ":该客户类型与账户关系对象已经存在");
                    throw new HsException(RespCode.AC_REPEATED_DATA.getCode(), "accType = " + accType + ",custType = "
                            + custType + ":该客户类型与账户关系对象已经存在");
                }

            // 将客户类型与账户关系对象插入到数据库中
            accountCustTypeMapper.addCustAccType(accountCustType);
        }
        catch (HsException e)
        {
            SystemLog.error("HSXT_AC", "方法：addCustAccType", e.getErrorCode() + e.getMessage(),e);
            throw new HsException(RespCode.AC_SQL_ERROR.getCode(), e.getMessage());
        }
        catch (Exception e)
        {
            SystemLog.error("HSXT_AC", "方法：addCustAccType", RespCode.AC_SQL_ERROR.getCode() + e.getMessage(),e);
            throw new HsException(RespCode.AC_SQL_ERROR.getCode(), e.getMessage());
        }
    }

    /**
     * 新增批量客户类型与账户关系对象
     * 
     * @param custAccTypeList
     *            客户类型与账户关系对象集合
     * @throws HsException
     *             异常处理类
     */
    @Override
    public void addCustAccTypes(List<AccountCustType> accountCustTypeList) throws HsException {
        BizLog.biz("HSXT_AC", "方法：addCustAccTypes", "客户类型与账户关系对象集合:custAccTypeList", "新增批量客户类型与账户关系对象");
        try
        {    
            if (accountCustTypeList.isEmpty())
            {
                SystemLog.debug("HSXT_AC", "方法：addCustAccTypes", RespCode.AC_PARAMETER_NULL.getCode()
                        + "custAccTypeList:客户类型与账户关系对象集合为空");
                throw new HsException(RespCode.AC_PARAMETER_NULL.getCode(), "custAccTypeList:客户类型与账户关系对象集合为空");
            }

            // 检查客户类型与账户关系对象集合中（账户类型编码、客户类型）2个字段是否为空，为空抛出异常,不为空则检查账户类型编码的规范
            for (AccountCustType accountCustType : accountCustTypeList)
            {
                Integer custType = accountCustType.getCustType();// 客户全局编码
                String accType = accountCustType.getAccType();// 账户类型编码
                validateCustAccInfo(custType, accType);

                // 查看是否存在该客户类型
                checkCustTypeCategory(custType);

                // 检查是否已经存在当前客户类型与账户关系对象
                AccountCustType accCustType_Type = (AccountCustType) accountCustTypeMapper.searchCustAccType(custType,
                        accType);
                if (accCustType_Type != null)
                {
                    SystemLog.debug("HSXT_AC", "方法：addCustAccTypes", RespCode.AC_PARAMETER_NULL.getCode()
                            + "accType = " + accType + ",custType = " + custType + ":该客户类型与账户关系对象已经存在");
                    throw new HsException(RespCode.AC_REPEATED_DATA.getCode(), "accType = " + accType + ",custType = "
                            + custType + ":该客户类型与账户关系对象已经存在");
                }
            }

            // 将客户类型与账户关系对象集合插入到数据库中
            accountCustTypeMapper.addCustAccTypes(accountCustTypeList);
        }
        catch (HsException e)
        {
            SystemLog.error("HSXT_AC", "方法：addCustAccTypes", e.getErrorCode() + e.getMessage(),e);
            throw new HsException(RespCode.AC_SQL_ERROR.getCode(), e.getMessage());
        }
        catch (Exception e)
        {
            SystemLog.error("HSXT_AC", "方法：addCustAccTypes", RespCode.AC_SQL_ERROR.getCode() + e.getMessage(),e);
            throw new HsException(RespCode.AC_SQL_ERROR.getCode(), e.getMessage());
        }
    }

    /**
     * 修改单个客户类型与账户关系
     * 
     * @param custAccType
     *            客户类型与账户关系对象
     * @throws HsException
     *             异常处理类
     */
    @Override
    public void updateCustAccType(AccountCustType accountCustType) throws HsException {
        BizLog.biz("HSXT_AC", "方法：updateCustAccType", "客户类型与账户关系对象:custAccType", "修改单个客户类型与账户关系");
        try
        {   
            if (accountCustType == null)
            {
                SystemLog.debug("HSXT_AC", "方法：updateCustAccType", RespCode.AC_PARAMETER_NULL.getCode()
                        + "custAccType:客户类型与账户关系对象为空");
                throw new HsException(RespCode.AC_PARAMETER_NULL.getCode(), "custAccType:客户类型与账户关系对象为空");
            }
    
            // 检查客户类型与账户关系对象集合中（账户类型编码、客户类型）2个字段是否为空，为空抛出异常,不为空则检查账户类型编码的规范
            Integer custType = accountCustType.getCustType();// 客户全局编码
            String accType = accountCustType.getAccType();// 账户类型编码
            validateCustAccInfo(custType, accType);
            

            // 根据当前的客户类型与账户关系对象中的（账户类型编码、客户类型）唯一键，去更新对应的数据库记录
            accountCustTypeMapper.updateCustAccType(accountCustType);
        }
        catch (HsException e)
        {
            SystemLog.error("HSXT_AC", "方法：updateCustAccType", e.getErrorCode() + e.getMessage(),e);
            throw new HsException(RespCode.AC_SQL_ERROR.getCode(), e.getMessage());
        }
        catch (Exception e)
        {
            SystemLog.error("HSXT_AC", "方法：updateCustAccType", RespCode.AC_SQL_ERROR.getCode() + e.getMessage(),e);
            throw new HsException(RespCode.AC_SQL_ERROR.getCode(), e.getMessage());
        }
    }

    /**
     * 修改批量客户类型与账户关系
     * 
     * @param custAccTypeList
     *            客户类型与账户关系对象集合
     * @throws HsException
     *             异常处理类
     */
    @Override
    public void updateCustAccTypes(List<AccountCustType> accountCustTypeList) throws HsException {
        BizLog.biz("HSXT_AC", "方法：updateCustAccTypes", "客户类型与账户关系对象集合:custAccTypeList", "修改批量客户类型与账户关系");
        try
        { 
            if (accountCustTypeList.isEmpty())
            {
                SystemLog.debug("HSXT_AC", "方法：updateCustAccTypes", RespCode.AC_PARAMETER_NULL.getCode()
                        + "custAccTypeList:客户类型与账户关系对象集合为空");
                throw new HsException(RespCode.AC_PARAMETER_NULL.getCode(), "custAccTypeList:客户类型与账户关系对象集合为空");
            }
    
            // 检查客户类型与账户关系对象集合中（账户类型编码、客户类型）2个字段是否为空，为空抛出异常,不为空则检查账户类型编码的规范
            for (AccountCustType accountCustType : accountCustTypeList)
            {
                Integer custType = accountCustType.getCustType();// 客户全局编码
                String accType = accountCustType.getAccType();// 账户类型编码
                validateCustAccInfo(custType, accType);
            }

            // 根据当前的客户类型与账户关系对象集合中的每个（账户类型编码、客户类型）唯一键，去更新对应的数据库记录
            accountCustTypeMapper.updateCustAccTypes(accountCustTypeList);
        }
        catch (HsException e)
        {
            SystemLog.error("HSXT_AC", "方法：updateCustAccTypes", e.getErrorCode() + e.getMessage(),e);
            throw new HsException(RespCode.AC_SQL_ERROR.getCode(), e.getMessage());
        }
        catch (Exception e)
        {
            SystemLog.error("HSXT_AC", "方法：updateCustAccTypes", RespCode.AC_SQL_ERROR.getCode() + e.getMessage(),e);
            throw new HsException(RespCode.AC_SQL_ERROR.getCode(), e.getMessage());
        }
    }

    /**
     * 删除单个客户类型与账户关系
     * 
     * @param custType
     *            客户全局编码
     * @param accType
     *            账户类型编码
     * @throws HsException
     */
    @Override
    public void deleteCustAccType(Integer custType, String accType) throws HsException {

        BizLog.biz("HSXT_AC", "方法：deleteCustAccType", "客户类型编码：custType,账户类型编码:accType", "删除单个客户类型与账户关系");

        // 检查(账户类型编码、客户类型)2个字段是否为空，为空抛出异常,不为空则检查账户类型编码的规范
        validateCustAccInfo(custType, accType);
        try
        {

            // 根据当前的(账户类型编码、客户类型)唯一键，删除对应的数据库记录
            accountCustTypeMapper.deleteCustAccType(custType, accType);
        }
        catch (Exception e)
        {
            SystemLog.error("HSXT_AC", "方法：deleteCustAccType", RespCode.AC_SQL_ERROR.getCode() + e.getMessage(),e);
            throw new HsException(RespCode.AC_SQL_ERROR.getCode(), e.getMessage());
        }
    }

    /**
     * 删除批量客户类型与账户关系
     * 
     * @param accountCustTypeList
     *            客户类型与账户关系对象集合
     * @throws HsException
     */
    @Override
    public void deleteCustAccTypes(List<AccountCustType> accountCustTypeList) throws HsException {
        BizLog.biz("HSXT_AC", "方法：deleteCustAccTypes", "客户类型与账户关系对象集合：custAccTypeList", "删除批量客户类型与账户关系");
        try
        {
            if (accountCustTypeList.isEmpty())
            {
                SystemLog.debug("HSXT_AC", "方法：deleteCustAccTypes", RespCode.AC_PARAMETER_NULL.getCode()
                        + "custAccTypeList:客户类型与账户关系对象集合为空");
                throw new HsException(RespCode.AC_PARAMETER_NULL.getCode(), "custAccTypeList:客户类型与账户关系对象集合为空");
            }
    
            // 检查客户类型与账户关系对象集合中（账户类型编码、客户类型）2个字段是否为空，为空抛出异常,不为空则检查账户类型编码的规范
            for (AccountCustType accountCustType : accountCustTypeList)
            {
                Integer custType = accountCustType.getCustType();// 客户全局编码
                String accType = accountCustType.getAccType();// 账户类型编码
                validateCustAccInfo(custType, accType);
            }
            

            // 根据当前集合中的每个对象中(账户类型编码、客户类型)唯一键，删除对应的数据库记录
            accountCustTypeMapper.deleteCustAccTypes(accountCustTypeList);
        }
        catch (HsException e)
        {
            SystemLog.error("HSXT_AC", "方法：deleteCustAccTypes", e.getErrorCode() + e.getMessage(),e);
            throw new HsException(RespCode.AC_SQL_ERROR.getCode(), e.getMessage());
        }
        catch (Exception e)
        {
            SystemLog.error("HSXT_AC", "方法：deleteCustAccTypes", RespCode.AC_SQL_ERROR.getCode() + e.getMessage(),e);
            throw new HsException(RespCode.AC_SQL_ERROR.getCode(), e.getMessage());
        }
    }

    /**
     * 新增单个账户余额（开户）
     * 
     * @param accountBalance
     *            账户余额对象
     * @throws HsException
     * @see com.gy.hsxt.ac.api.IAccountBalanceService#accOpen(java.util.List)
     */
    @Override
    public void openAccount(AccountBalance accountBalance) throws HsException {
        BizLog.biz("HSXT_AC", "方法：openAccount", "账户余额对象：accountBalance", "新增单个账户余额（开户）");
        try
        {
            if (accountBalance == null)
            {
                SystemLog.debug("HSXT_AC", "方法：openAccount", RespCode.AC_PARAMETER_NULL.getCode()
                        + "accountBalance:账户余额对象为空");
                throw new HsException(RespCode.AC_PARAMETER_NULL.getCode(), "accountBalance:账户余额对象为空");
            }

            // 检查账户余额信息必填项（客户全局编码，账户类型编码，客户类型编码，账户余额，账户状态）
            String custID = accountBalance.getCustID();// 客户全局编码
            if (custID == null || "".equals(custID))
            {
                SystemLog.debug("HSXT_AC", "方法：openAccount", RespCode.AC_PARAMETER_NULL.getCode() + "CustID:客户全局编码为空");
                throw new HsException(RespCode.AC_PARAMETER_NULL.getCode(), "CustID:客户全局编码为空");
            }
            String accType = accountBalance.getAccType();// 账户类型编码
            if (accType == null || "".equals(accType))
            {
                SystemLog.debug("HSXT_AC", "方法：openAccount", RespCode.AC_PARAMETER_NULL.getCode() + "accType:账户类型编码为空");
                throw new HsException(RespCode.AC_PARAMETER_NULL.getCode(), "accType:账户类型编码为空");
            }
            Integer custType = accountBalance.getCustType();// 客户类型编码
            if (custType == null)
            {
                SystemLog.debug("HSXT_AC", "方法：openAccount", RespCode.AC_PARAMETER_NULL.getCode() 
                        + "custType:客户类型编码为空");
                throw new HsException(RespCode.AC_PARAMETER_NULL.getCode(), "custType:客户类型编码为空");
            }
            
            // 判断当前客户类型所属哪个客户分类（0：消费者，1：企业，2：平台）,如果为空，则该客户类型不存在
            Integer custTypeCategory = checkCustTypeCategory(custType);
            accountBalance.setCustTypeCategory(custTypeCategory);
            String accBalance = accountBalance.getAccBalance();// 账户余额
            if (accBalance == null || "".equals(accBalance))
            {
                SystemLog.debug("HSXT_AC", "方法：openAccount", RespCode.AC_PARAMETER_NULL.getCode() 
                        + "accBalance:账户余额为空");
                throw new HsException(RespCode.AC_PARAMETER_NULL.getCode(), "accBalance:账户余额为空");
            }
            Integer accStatus = accountBalance.getAccStatus();// 账户状态
            if (accStatus == null)
            {
                SystemLog.debug("HSXT_AC", "方法：openAccount", RespCode.AC_PARAMETER_NULL.getCode() + "accStatus:账户状态为空");
                throw new HsException(RespCode.AC_PARAMETER_NULL.getCode(), "accStatus:账户状态为空");
            }

            // 检查是否已经有（CustID,AccType）唯一键的账户余额信息存在
            AccountBalance account_Balance = accountBalanceMapper.searchAccountBalanceInfo(custID, accType);
            if (account_Balance != null)
            {
                SystemLog.debug("HSXT_AC", "方法：openAccount", RespCode.AC_REPEATED_DATA.getCode() + "custID = " + custID
                        + ",accType = " + accType + ":该账户余额信息已经存在");
                throw new HsException(RespCode.AC_REPEATED_DATA.getCode(), "custID = " + custID + ",accType = "
                        + accType + ":该账户余额信息已经存在");
            }

            // 将当前账户余额插入到数据库中
            accountBalanceMapper.addAccountBalance(accountBalance);
        }
        catch (HsException e)
        {
            SystemLog.error("HSXT_AC", "方法：openAccount", e.getErrorCode() + e.getMessage(),e);
            throw new HsException(RespCode.AC_SQL_ERROR.getCode(), e.getMessage());
        }
        catch (Exception e)
        {
            SystemLog.error("HSXT_AC", "方法：openAccount", RespCode.AC_SQL_ERROR.getCode() + e.getMessage(),e);
            throw new HsException(RespCode.AC_SQL_ERROR.getCode(), e.getMessage());
        }
    }

    /**
     * 新增批量账户余额（开户）
     * 
     * @param accountBalanceList
     * @throws HsException
     * @see com.gy.hsxt.ac.api.IAccountBalanceService#accOpen(java.util.List)
     */
    @Override
    public void openAccounts(List<AccountBalance> accountBalanceList) throws HsException {
        BizLog.biz("HSXT_AC", "方法：openAccounts", "账户余额对象集合：accountBalanceList", "新增批量账户余额（开户）");
        if (accountBalanceList.isEmpty())
        {
            SystemLog.debug("HSXT_AC", "方法：openAccounts", RespCode.AC_PARAMETER_NULL.getCode()
                    + "accountBalanceList : 账户余额开户信息集合为空");
            throw new HsException(RespCode.AC_PARAMETER_NULL.getCode(), "accountBalanceList : 账户余额开户信息集合为空");
        }
        try
        {
            for (AccountBalance accountBalance : accountBalanceList)
            {
                String custID = accountBalance.getCustID();// 客户全局编码
                if (custID == null || "".equals(custID))
                {
                    SystemLog.debug("HSXT_AC", "方法：openAccounts", RespCode.AC_PARAMETER_NULL.getCode()
                            + "custID : 客户全局编码为空");
                    throw new HsException(RespCode.AC_PARAMETER_NULL.getCode(), "custID : 客户全局编码为空");
                }
                String accType = accountBalance.getAccType();// 账户类型编码
                if (accType == null || "".equals(accType))
                {
                    SystemLog.debug("HSXT_AC", "方法：openAccounts", RespCode.AC_PARAMETER_NULL.getCode()
                            + "accType : 账户类型编码为空");
                    throw new HsException(RespCode.AC_PARAMETER_NULL.getCode(), "accType : 账户类型编码为空");
                }
                Integer custType = accountBalance.getCustType();// 客户类型编码
                if (custType == null)
                {
                    SystemLog.debug("HSXT_AC", "方法：openAccounts", RespCode.AC_PARAMETER_NULL.getCode()
                            + "custType:客户类型编码为空");
                    throw new HsException(RespCode.AC_PARAMETER_NULL.getCode(), "custType:客户类型编码为空");
                }
                
                // 判断当前客户类型所属哪个客户分类（0：消费者，1：企业，2：平台）,如果为空，则该客户类型不存在
                Integer custTypeCategory = checkCustTypeCategory(custType);
                accountBalance.setCustTypeCategory(custTypeCategory);
                String accBalance = accountBalance.getAccBalance();// 账户余额
                if (accBalance == null || "".equals(accBalance))
                {
                    SystemLog.debug("HSXT_AC", "方法：openAccounts", RespCode.AC_PARAMETER_NULL.getCode()
                            + "accBalance:账户余额为空");
                    throw new HsException(RespCode.AC_PARAMETER_NULL.getCode(), "accBalance:账户余额为空");
                }
                Integer accStatus = accountBalance.getAccStatus();// 账户状态
                if (accStatus == null)
                {
                    SystemLog.debug("HSXT_AC", "方法：openAccounts", RespCode.AC_PARAMETER_NULL.getCode()
                            + "accStatus:账户状态为空");
                    throw new HsException(RespCode.AC_PARAMETER_NULL.getCode(), "accStatus:账户状态为空");
                }

                // 检查是否已经有（CustID,AccType）唯一键的账户余额信息存在
                AccountBalance account_Balance = accountBalanceMapper.searchAccountBalanceInfo(custID, accType);
                if (account_Balance != null)
                {
                    SystemLog.debug("HSXT_AC", "方法：openAccounts", RespCode.AC_REPEATED_DATA.getCode() + "custID = "
                            + custID + ",accType = " + accType + ":该账户余额信息已经存在");
                    throw new HsException(RespCode.AC_REPEATED_DATA.getCode(), "custID = " + custID + ",accType = "
                            + accType + ":该账户余额信息已经存在");
                }
            }

            // 将当前账户余额集合插入到数据库中
            accountBalanceMapper.addAccountBalances(accountBalanceList);
        }
        catch (HsException e)
        {
            SystemLog.error("HSXT_AC", "方法：openAccounts", e.getErrorCode() + e.getMessage(),e);
            throw new HsException(RespCode.AC_SQL_ERROR.getCode(), e.getMessage());
        }
        catch (Exception e)
        {
            SystemLog.error("HSXT_AC", "方法：openAccounts", RespCode.AC_SQL_ERROR.getCode() + e.getMessage(),e);
            throw new HsException(RespCode.AC_SQL_ERROR.getCode(), e.getMessage());
        }
    }

    /**
     * 更新单个账户余额
     * 
     * @param accountBalance
     *            需更新的账户余额对象信息
     * @throws HsException
     * @see com.gy.hsxt.ac.api.IAccountBalanceService#updateaccountBalance(java.util.List)
     */
//    public void updateAccountBalance(AccountBalance accountBalance) throws HsException {
//        BizLog.biz("HSXT_AC", "方法：updateAccountBalance", "账户余额对象：accountBalance", "更新单个账户余额");
//        try
//        {
//            if (accountBalance == null)
//            {
//                SystemLog.debug("HSXT_AC", "方法：updateAccountBalance", RespCode.AC_PARAMETER_NULL.getCode()
//                        + "accountBalance:账户余额更新对象为空");
//                throw new HsException(RespCode.AC_PARAMETER_NULL.getCode(), "accountBalance:账户余额更新对象为空");
//            }
//            String custId = accountBalance.getCustID();// 客户全局编码
//            if (custId == null || "".equals(custId))
//            {
//                SystemLog.debug("HSXT_AC", "方法：updateAccountBalance", RespCode.AC_PARAMETER_NULL.getCode()
//                        + "custID:客户全局编码为空");
//                throw new HsException(RespCode.AC_PARAMETER_NULL.getCode(), "custID:客户全局编码为空");
//            }
//            String accType = accountBalance.getAccType();// 账户类型编码
//            if (accType == null || "".equals(accType))
//            {
//                SystemLog.debug("HSXT_AC", "方法：updateAccountBalance", RespCode.AC_PARAMETER_NULL.getCode()
//                        + "accType:账户类型编码为空");
//                throw new HsException(RespCode.AC_PARAMETER_NULL.getCode(), "accType:账户类型编码为空");
//            }
//            String accBalance = accountBalance.getAccBalance();// 更新的账户余额
//            if (accBalance == null || "".equals(accBalance) || "null".equals(accBalance))
//            {
//                SystemLog.debug("HSXT_AC", "方法：updateAccountBalance", RespCode.AC_PARAMETER_NULL.getCode()
//                        + "accBalance:账户余额为空");
//                throw new HsException(RespCode.AC_PARAMETER_NULL.getCode(), "accBalance:账户余额为空");
//            }
//            BigDecimal udpateBalance = new BigDecimal(accBalance);// 更新的账户余额（String->BigDecimal）
//    
//            Integer custType = accountBalance.getCustType();// 客户类型编码
//            if (custType != null)
//            {
//                // 判断当前客户类型所属哪个客户分类（0：消费者，1：企业，2：平台）,如果为空，则该客户类型不存在
//                Integer custTypeCategory = checkCustTypeCategory(custType);
//                accountBalance.setCustTypeCategory(custTypeCategory);
//            }
//            
//            // 更新账户余额的信息验证（是否存在账户，当前账户状态的验证）
//            AccountBalance acc_Balance = checkBalanceInfo(custId, accType, udpateBalance);
//            
//            // 当前账户余额值
//            BigDecimal curAccBalance = acc_Balance.getAccBalance() == null ? BigDecimal.valueOf(0) : new BigDecimal(
//                    acc_Balance.getAccBalance());
//            BigDecimal newBalance = curAccBalance.add(udpateBalance);// 更新后的账户余额值
//           
//            // 账户余额和账户关系表中最小值最大值比较
//            checkCustAccTypeInfo(custType, accType, newBalance);
//
//            // 根据当前条件更新对应的账户余额信息的余额
//            accountBalanceMapper.updateAccountBalance(accountBalance);
//        }
//        catch (HsException e)
//        {
//            SystemLog.error("HSXT_AC", "方法：updateAccountBalance", e.getErrorCode() + e.getMessage(),e);
//            throw new HsException(RespCode.AC_SQL_ERROR.getCode(), e.getMessage());
//        }
//        catch (Exception e)
//        {
//            SystemLog.error("HSXT_AC", "方法：updateAccountBalance", RespCode.AC_SQL_ERROR.getCode() + e.getMessage(),e);
//            throw new HsException(RespCode.AC_SQL_ERROR.getCode(), e.getMessage());
//        }
//    }

    /**
     * 更新批量账户余额
     * 
     * @param accountBalanceList
     *            需更新的账户余额对象信息
     * @throws HsException
     * @see com.gy.hsxt.ac.api.IAccountBalanceService#updateaccountBalance(java.util.List)
     */
    public void updateAccountBalances(List<AccountBalance> accountBalanceList) throws HsException {
        BizLog.biz("HSXT_AC", "方法：updateAccountBalances", "账户余额对象集合：accountBalanceList", "更新批量账户余额");
        if (accountBalanceList.isEmpty())
        {
            SystemLog.debug("HSXT_AC", "方法：updateAccountBalances", RespCode.AC_PARAMETER_NULL.getCode()
                    + "accountBalanceList:账户余额更新信息集合为空");
            throw new HsException(RespCode.AC_PARAMETER_NULL.getCode(), "accountBalanceList:账户余额更新信息集合为空");
        }
        try
        {
            for (AccountBalance accountBalance : accountBalanceList)
            {
                String custId = accountBalance.getCustID();// 客户全局编码
                if (custId == null || "".equals(custId))
                {
                    SystemLog.debug("HSXT_AC", "方法：updateAccountBalances", RespCode.AC_PARAMETER_NULL.getCode()
                            + "custID:客户全局编码为空");
                    throw new HsException(RespCode.AC_PARAMETER_NULL.getCode(), "custID:客户全局编码为空");
                }
                String accType = accountBalance.getAccType();// 账户类型编码
                if (accType == null || "".equals(accType))
                {
                    SystemLog.debug("HSXT_AC", "方法：updateAccountBalances", RespCode.AC_PARAMETER_NULL.getCode()
                            + "accType:账户类型编码为空");
                    throw new HsException(RespCode.AC_PARAMETER_NULL.getCode(), "accType:账户类型编码为空");
                }
                String accBalance = accountBalance.getAccBalance();// 账户余额
                if (accBalance == null || "".equals(accBalance) || "null".equals(accBalance))
                {
                    SystemLog.debug("HSXT_AC", "方法：updateAccountBalances", RespCode.AC_PARAMETER_NULL.getCode()
                            + "accBalance:账户余额为空");
                    throw new HsException(RespCode.AC_PARAMETER_NULL.getCode(), "accBalance:账户余额为空");
                }
                BigDecimal udpateBalance = new BigDecimal(accBalance);// 更新的账户余额（String->BigDecimal）

                Integer custType = accountBalance.getCustType();// 客户类型编码
                if (custType != null)
                {
                    // 判断当前客户类型所属哪个客户分类（0：消费者，1：企业，2：平台）,为空，则该客户类型不存在
                    Integer custTypeCategory = checkCustTypeCategory(custType);
                    accountBalance.setCustTypeCategory(custTypeCategory);
                }
                
                // 更新账户余额的信息验证（是否存在账户，当前账户状态的验证）
                AccountBalance acc_Balance = checkBalanceInfo(custId, accType, udpateBalance);
               
                // 当前账户余额值
                BigDecimal curAccBalance = acc_Balance.getAccBalance() == null ? BigDecimal.valueOf(0)
                        : new BigDecimal(acc_Balance.getAccBalance());
                BigDecimal newBalance = curAccBalance.add(udpateBalance);// 更新后的账户余额值
                
                // 账户余额和账户关系表中最小值最大值比较
//                checkCustAccTypeInfo(custType, accType, newBalance);
            }
            
            // 根据当前账户余额集合更新对应的账户余额信息的余额
            accountBalanceMapper.updateAccountBalances(accountBalanceList);
        }
        catch (HsException e)
        {
            SystemLog.error("HSXT_AC", "方法：updateAccountBalances", e.getErrorCode() + e.getMessage(),e);
            throw new HsException(RespCode.AC_SQL_ERROR.getCode(), e.getMessage());
        }
        catch (Exception e)
        {
            SystemLog.error("HSXT_AC", "方法：updateAccountBalances", RespCode.AC_SQL_ERROR.getCode() + e.getMessage(),e);
            throw new HsException(RespCode.AC_SQL_ERROR.getCode(), e.getMessage());
        }
    }

    /**
     * 新增单个账户变更状态对象
     * 
     * @param AccountStatusChange
     *            账户变更状态对象
     * @throws HsException
     */
    @Override
    public void addAccStatusChange(AccountStatusChange accStatusChange) throws HsException {
        BizLog.biz("HSXT_AC", "方法：addAccStatusChange", "账户变更状态对象 ：accStatusChange", "新增单个账户变更状态对象");
        try
        {
            if (accStatusChange == null)
            {
                SystemLog.debug("HSXT_AC", "方法：addAccStatusChange", RespCode.AC_PARAMETER_NULL.getCode()
                        + "accStatusChange:账户状态表变更对象为空");
                throw new HsException(RespCode.AC_PARAMETER_NULL.getCode(), "accStatusChange:账户状态表变更对象为空");
            }
            String custID = accStatusChange.getCustID();// 客户全局编码
            if ("".equals(custID) || custID == null)
            {
                SystemLog.debug("HSXT_AC", "方法：addAccStatusChange", RespCode.AC_PARAMETER_NULL.getCode()
                        + "custID 客户全局编码为空");
                throw new HsException(RespCode.AC_PARAMETER_NULL.getCode(), "custID 客户全局编码为空");
            }
            String accType = accStatusChange.getAccType();// 账户类型编码
            if ("".equals(accType) || accType == null)
            {
                SystemLog.debug("HSXT_AC", "方法：addAccStatusChange", RespCode.AC_PARAMETER_NULL.getCode()
                        + "accType 账户类型编码为空");
                throw new HsException(RespCode.AC_PARAMETER_NULL.getCode(), "accType 账户类型编码为空");
            }
            Integer accStatusNew = accStatusChange.getAccStatusNew();// 账户变更后状态
            if (accStatusNew == null)
            {
                SystemLog.debug("HSXT_AC", "方法：addAccStatusChange", RespCode.AC_PARAMETER_NULL.getCode()
                        + "accStatusNew 账户变更后状态为空");
                throw new HsException(RespCode.AC_PARAMETER_NULL.getCode(), "accStatusNew 账户变更后状态为空");
            }
            Integer accStatusOld = accStatusChange.getAccStatusOld();// 账户变更前状态
            if (accStatusOld == null)
            {
                SystemLog.debug("HSXT_AC", "方法：addAccStatusChange", RespCode.AC_PARAMETER_NULL.getCode()
                        + "accStatusOld 账户变更前状态为空");
                throw new HsException(RespCode.AC_PARAMETER_NULL.getCode(), "accStatusOld 账户变更前状态为空");
            }

            // 将当前账户变更状态对象插入到数据库中
            accountBalanceMapper.addAccStatusChangeInfo(accStatusChange);
        }
        catch (HsException e)
        {
            SystemLog.error("HSXT_AC", "方法：addAccStatusChange", e.getErrorCode() + e.getMessage(),e);
            throw new HsException(RespCode.AC_SQL_ERROR.getCode(), e.getMessage());
        }
        catch (Exception e)
        {
            SystemLog.error("HSXT_AC", "方法：addAccStatusChange", RespCode.AC_SQL_ERROR.getCode() + e.getMessage(),e);
            throw new HsException(RespCode.AC_SQL_ERROR.getCode(), e.getMessage());
        }
    }

    /**
     * 新增批量账户变更状态对象
     * 
     * @param accountStatusChangeList
     *            账户变更状态对象集合
     * @throws HsException
     */
    @Override
    public void addAccStatusChanges(List<AccountStatusChange> accStatusChangeList) throws HsException {
        BizLog.biz("HSXT_AC", "方法：addAccStatusChanges", "账户变更状态对象 集合：accStatusChangeList", "新增批量账户变更状态对象");
        try
        {
            if (accStatusChangeList.isEmpty())
            {
                SystemLog.debug("HSXT_AC", "方法：addAccStatusChanges", RespCode.AC_PARAMETER_NULL.getCode()
                        + "accStatusChangeList:账户状态表变更对象集合为空");
                throw new HsException(RespCode.AC_PARAMETER_NULL.getCode(), "accStatusChangeList:账户状态表变更对象集合为空");
            }
            for (AccountStatusChange accStatusChange : accStatusChangeList)
            {
                String custID = accStatusChange.getCustID();// 客户全局编码
                if ("".equals(custID) || custID == null)
                {
                    SystemLog.debug("HSXT_AC", "方法：addAccStatusChanges", RespCode.AC_PARAMETER_NULL.getCode()
                            + "custID 客户全局编码为空");
                    throw new HsException(RespCode.AC_PARAMETER_NULL.getCode(), "custID 客户全局编码为空");
                }
                String accType = accStatusChange.getAccType();// 账户类型编码
                if ("".equals(accType) || accType == null)
                {
                    SystemLog.debug("HSXT_AC", "方法：addAccStatusChanges", RespCode.AC_PARAMETER_NULL.getCode()
                            + "accType 账户类型编码为空");
                    throw new HsException(RespCode.AC_PARAMETER_NULL.getCode(), "accType 账户类型编码为空");
                }
                Integer accStatusNew = accStatusChange.getAccStatusNew();// 账户变更后状态
                if (accStatusNew == null)
                {
                    SystemLog.debug("HSXT_AC", "方法：addAccStatusChanges", RespCode.AC_PARAMETER_NULL.getCode()
                            + "accStatusNew 账户变更后状态为空");
                    throw new HsException(RespCode.AC_PARAMETER_NULL.getCode(), "accStatusNew 账户变更后状态为空");
                }
                Integer accStatusOld = accStatusChange.getAccStatusOld();// 账户变更前状态
                if (accStatusOld == null)
                {
                    SystemLog.debug("HSXT_AC", "方法：addAccStatusChanges", RespCode.AC_PARAMETER_NULL.getCode()
                            + "accStatusOld 账户变更前状态为空");
                    throw new HsException(RespCode.AC_PARAMETER_NULL.getCode(), "accStatusOld 账户变更前状态为空");
                }
            }

            // 将当前账户变更状态对象集合插入到数据库中
            accountBalanceMapper.addAccStatusChangeInfos(accStatusChangeList);
        }
        catch (HsException e)
        {
            SystemLog.error("HSXT_AC", "方法：addAccStatusChanges", e.getErrorCode() + e.getMessage(),e);
            throw new HsException(RespCode.AC_SQL_ERROR.getCode(), e.getMessage());
        }
        catch (Exception e)
        {
            SystemLog.error("HSXT_AC", "方法：addAccStatusChanges", RespCode.AC_SQL_ERROR.getCode() + e.getMessage(),e);
            throw new HsException(RespCode.AC_SQL_ERROR.getCode(), e.getMessage());
        }
    }

    /**
     * 检查客户类型与账户关系对象集合中（账户类型编码、客户全局编码）2个字段是否为空，为空抛出异常,不为空则检查账户类型编码的规范
     * 
     * @param custType
     *            客户全局编码
     * @param accType
     *            账户类型编码
     */
    public void validateCustAccInfo(Integer custType, String accType) throws HsException {
        BizLog.biz("HSXT_AC", "方法：validateCustAccInfo", "账户类型编码：accType,客户全局编码 :custType", "新增批量账户变更状态对象");
        if (accType == null || "".equals(accType))
        {
            SystemLog.debug("HSXT_AC", "方法：validateCustAccInfo", RespCode.AC_PARAMETER_NULL.getCode()
                    + "custID 客户全局编码为空");
            throw new HsException(RespCode.AC_PARAMETER_NULL.getCode(), "accType:账户类型编码为空");
        }
        if (custType == null)
        {
            SystemLog.debug("HSXT_AC", "方法：validateCustAccInfo", RespCode.AC_PARAMETER_NULL.getCode()
                    + "custType:客户类型为空");
            throw new HsException(RespCode.AC_PARAMETER_NULL.getCode(), "custType:客户类型为空");
        }
    }

    /**
     * 判断当前客户类型所属哪个客户分类（0：消费者，1：企业，2：平台）
     * 
     * @param custType
     *            客户类型
     * @return 客户类型分类 。如果为空，则说明该客户类型不存在
     * @throws HsException
     *             异常处理类
     */
    public Integer checkCustTypeCategory(Integer custType) throws HsException {

        String[] perTypes = PropertyConfigurer.getProperty("ac.custType.perType").split("\\|");// 消费者客户类型
        String[] entTypes = PropertyConfigurer.getProperty("ac.custType.entType").split("\\|");// 企业客户类型
        String[] pfTypes = PropertyConfigurer.getProperty("ac.custType.pfType").split("\\|");// 平台客户类型

        Integer custTypeCategory = null;// 客户类型分类
        
        // 消费者客户类型
        for (String perType : perTypes)
        {
            if (perType.equals(String.valueOf(custType)))
            {
                custTypeCategory = AcConstant.CUST_TYPE_CATEGORY[0];
            }
        }
       
        // 企业客户类型
        for (String entType : entTypes)
        {
            if (entType.equals(String.valueOf(custType)))
            {
                custTypeCategory = AcConstant.CUST_TYPE_CATEGORY[1];
            }
        }
        // 平台客户类型
        for (String pfType : pfTypes)
        {
            if (pfType.equals(String.valueOf(custType)))
            {
                custTypeCategory = AcConstant.CUST_TYPE_CATEGORY[2];
            }
        }
        
        // 为空，则说明该客户类型不存在
        if (custTypeCategory == null)
        {
            SystemLog.debug("HSXT_AC", "方法：checkCustTypeCategory", RespCode.AC_PARAMETER_NULL.getCode() + "custType:"
                    + custType + ",不存在该客户类型");
            throw new HsException(RespCode.AC_PARAMETER_NULL.getCode(), "custType:" + custType + ",不存在该客户类型");
        }
        return custTypeCategory;
    }

    /**
     * 账户余额和账户关系表中最小值最大值比较
     * 
     * @param custType
     *            客户类型编码
     * @param accType
     *            账户类型编码
     * @param amount
     *            扣款后的账户余额
     * @throws SQLException
     * @throws HsException
     */
//    public void checkCustAccTypeInfo(Integer custType, String accType, BigDecimal amount) throws SQLException,
//            HsException {
//        
//        // 查找账户和客户类型关系信息
//        AccountCustType accountCustType = (AccountCustType) accountCustTypeMapper.searchCustAccType(custType, accType);
//        if (accountCustType == null)
//        {
//            SystemLog.debug("HSXT_AC", "方法：checkCustAccTypeInfo", RespCode.AC_NO_RELATION.getCode() + "对应的账号类型:"
//                    + accType + "和客户类型:" + custType + "关系信息不存在");
//            throw new HsException(RespCode.AC_NO_RELATION.getCode(), "对应的账号类型:" + accType + "和客户类型:" + custType
//                    + "关系信息不存在");
//        }
//        String balanceMin = accountCustType.getBalanceMin();// 最小值
//        String balanceMax = accountCustType.getBalanceMax();// 最大值
//
//        // 新余额在客户和账户关系表中最大值区间比较
//        if (balanceMax != null && !"".equals(balanceMax))
//        {
//            BigDecimal balMax = new BigDecimal(balanceMax);
//          
//            // 账户新余额大于客户和账户关心设置中的最大值
//            if (amount.compareTo(balMax) == 1)
//            {
//                SystemLog.debug("HSXT_AC", "方法：checkCustAccTypeInfo", RespCode.AC_BALANCE_DEFICIENCY.getCode()
//                        + "（账户余额+增向金额）超过账户和关系设置中的最大值");
//                throw new HsException(RespCode.AC_BALANCE_DEFICIENCY.getCode(), "（账户余额+增向金额）超过客户和账户关系设置中的最大值");
//            }
//        }
//       
//        // 新余额在客户和账户关系表中最小值区间比较
//        if (balanceMin != null && !"".equals(balanceMin))
//        {
//            BigDecimal balMin = new BigDecimal(balanceMin);
//            
//            // 账户新余额小于客户和账户关心设置中的最小值
//            if (amount.compareTo(balMin) == -1)
//            {
//                SystemLog.debug("HSXT_AC", "方法：checkCustAccTypeInfo", RespCode.AC_BALANCE_DEFICIENCY.getCode()
//                        + "（账户余额-减向金额）超过账户和关系设置中的最小值");
//                throw new HsException(RespCode.AC_BALANCE_DEFICIENCY.getCode(), "（账户余额-减向金额）超过账户和关系设置中的最小值");
//            }
//        }
//    }

    /**
     * 更新账户余额的信息验证（是否存在账户，当前账户状态的验证）
     * 
     * @param custId
     *            客户全局编码
     * @param accType
     *            账户类型编码
     * @param amount
     *            当前更新的金额
     * @return AccountBalance 当前账户余额对象
     * @throws SQLException
     *             异常处理
     * @throws HsException
     *             异常处理
     */
    public AccountBalance checkBalanceInfo(String custId, String accType, BigDecimal amount) throws SQLException,
            HsException {
        
        // 通过（账户类型编码：accType,客户全局编码:custID）查找账户余额记录是否存在
        AccountBalance accountBalance = (AccountBalance) accountBalanceMapper.searchAccountBalanceInfo(custId, accType);
        if (accountBalance != null)
        {
            int accStatus = accountBalance.getAccStatus();// 当前账户余额中的账户状态
            
            // 根据当前账户状态判断当前账户是否允许变更金额
            if (accStatus == AcConstant.ACC_STATUS[3] || accStatus == AcConstant.ACC_STATUS[4])
            {
                SystemLog.debug("HSXT_AC", "方法：checkBalanceInfo", RespCode.AC_ENTRY_STAUS_ERROR.getCode()
                        + "账户状态不允许变更金额");
                throw new HsException(RespCode.AC_ENTRY_STAUS_ERROR.getCode(), "账户状态不允许变更金额");
            }
            
            // 更新金额大于0,当前账户状态为(2:许减不许增)
            if (amount.compareTo(BigDecimal.valueOf(0)) == 1 && accStatus == AcConstant.ACC_STATUS[2])
            {
                SystemLog.debug("HSXT_AC", "方法：checkAccountEntryAmountInfo", RespCode.AC_ENTRY_STAUS_ERROR.getCode()
                        + "账户状态为许减不许增，不能金额增额更新");
                throw new HsException(RespCode.AC_ENTRY_STAUS_ERROR.getCode(), "账户状态为许减不许增，不能金额增额更新");
            }
            
            // 更新金额小于0,当前账户状态为(1:许增不许减)
            if (amount.compareTo(BigDecimal.valueOf(0)) == -1 && accStatus == AcConstant.ACC_STATUS[1])
            {
                SystemLog.debug("HSXT_AC", "方法：checkAccountEntryAmountInfo", RespCode.AC_ENTRY_STAUS_ERROR.getCode()
                        + "账户状态为许增不许减，不能金额减额更新");
                throw new HsException(RespCode.AC_ENTRY_STAUS_ERROR.getCode(), "账户状态为许增不许减，不能金额减额更新");
            }
        }
        return accountBalance;// 返回当前账户余额
    }
}
