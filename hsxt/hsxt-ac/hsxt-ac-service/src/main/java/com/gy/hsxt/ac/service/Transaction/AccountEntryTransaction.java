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

package com.gy.hsxt.ac.service.Transaction;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.gy.hsi.lc.client.log4j.BizLog;
import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.ac.bean.AccountBalance;
import com.gy.hsxt.ac.bean.AccountBalanceSnap;
import com.gy.hsxt.ac.bean.AccountEntry;
import com.gy.hsxt.ac.mapper.AccountBalanceMapper;
import com.gy.hsxt.ac.mapper.AccountCustTypeMapper;
import com.gy.hsxt.ac.mapper.AccountEntryMapper;
import com.gy.hsxt.common.constant.CustType;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;

/**
 * 记账分录统一事务Service
 * 
 * @Package: com.gy.hsxt.ac.service
 * @ClassName: AccountEntryRunnableService
 * @Description: TODO
 * 
 * @author: weixz
 * @date: 2015-10-10 下午5:36:54
 * @version V1.0
 */

@Service
@Transactional
public class AccountEntryTransaction {

    /** 账务分录的接口服务 */
    @Autowired
    AccountEntryMapper accountEntryMapper;

    /** 账务余额的接口服务 */
    @Autowired
    AccountBalanceMapper accountBalanceMapper;

    /** 账户和客户类型关系的接口服务 */
    @Autowired
    AccountCustTypeMapper accountCustTypeMapper;

    /**
     * 实时记账中实现新增、更新账户余额,新增余额快照记录,新增分录信息
     * 
     * @param openAccBalanceList
     *            新增的账户余额对象集合
     * @param updateAccBalanceList
     *            更新的账户余额对象集合
     * @param accountBalanceSnapList
     *            新增余额快照记录集合
     * @param accountEntryList
     *            分录对象集合
     * @throws HsException
     *             异常处理类
     */
    public void actualAccount(List<AccountBalance> openAccBalanceList, List<AccountBalance> updateAccBalanceList,
            List<AccountBalanceSnap> accountBalanceSnapList, List<AccountEntry> accountEntryList) throws HsException {
        try
        {
            if (!accountBalanceSnapList.isEmpty())
            {
                BizLog.biz("HSXT_AC", "方法：updateAccountBalances", "accountBalanceSnapList", "新增余额快照记录集合插入到数据库");
                // 新增余额快照记录集合插入到数据库
                accountBalanceMapper.addAccountBalanceSnaps(accountBalanceSnapList);
            }
            if (!openAccBalanceList.isEmpty())
            {
                BizLog.biz("HSXT_AC", "方法：addAccountBalances", "openAccBalanceList", "插入新增的开户账户");
                // 插入新增的开户账户
                accountBalanceMapper.addAccountBalances(openAccBalanceList);
            }
            if (!updateAccBalanceList.isEmpty())
            {
                BizLog.biz("HSXT_AC", "方法：addAccountBalances", "updateAccBalanceList", "更新账户余额信息集合");
                for(int i=0; i<updateAccBalanceList.size(); i++)
                {
                    AccountBalance accountBalance = updateAccBalanceList.get(i);
                    // 更新账户余额
                    int rowCount = accountBalanceMapper.updateAccountBalance(accountBalance);
                    if(rowCount == 0)
                    {
//                        SystemLog.debug("HSXT_AC", "方法：deductAccount", RespCode.AC_PARAMETER_NULL.getCode()
//                                + accountBalance.getCustID()+"对应的账号类型:"+ accountBalance.getAccType() + "和客户类型:" + accountBalance.getCustType()+"余额不足");
//                        throw new HsException(RespCode.AC_PARAMETER_NULL.getCode(), accountBalance.getCustID()+"对应的账号类型:"+ accountBalance.getAccType() + "和客户类型:" + accountBalance.getCustType()+"余额不足");
                        if(accountBalance.getCustType()==CustType.PERSON.getCode() || accountBalance.getCustType()==CustType.NOT_HS_PERSON.getCode())
                        {
                            SystemLog.debug("HSXT_AC", "方法：checkAccountEntryAmountInfo", RespCode.AC_BALANCE_DEFICIENCY
                                    .getCode()
                                    +"客户号："+accountBalance.getCustID()+"对应的账号类型:"+ accountBalance.getAccType() + "和客户类型:" + accountBalance.getCustType()+",余额不足");
                            throw new HsException(RespCode.AC_BALANCE_DEFICIENCY.getCode(), accountBalance.getCustID()+"对应的账号类型:"+ accountBalance.getAccType() + "和客户类型:" + accountBalance.getCustType()+",余额不足");
                        }
                        else
                        {
                            SystemLog.debug("HSXT_AC", "方法：checkAccountEntryAmountInfo", RespCode.AC_ENT_BALANCE_DEFICIENCY
                                    .getCode()
                                    +"客户号："+accountBalance.getCustID()+"对应的账号类型:"+ accountBalance.getAccType() + "和客户类型:" + accountBalance.getCustType()+",余额不足");
                            throw new HsException(RespCode.AC_ENT_BALANCE_DEFICIENCY.getCode(), accountBalance.getCustID()+"对应的账号类型:"+ accountBalance.getAccType() + "和客户类型:" + accountBalance.getCustType()+",余额不足");
                        }
                    }
                }
                // 更新账户余额
//                accountBalanceMapper.updateAccountBalances(updateAccBalanceList);
            }
            if (!accountEntryList.isEmpty())
            {
                BizLog.biz("HSXT_AC", "方法：addAccountEntrys", "accountEntryList", "新增记账记录集合插入到数据库");
                // 新增记账记录集合插入到数据库
                accountEntryMapper.addAccountEntrys(accountEntryList);
            }
        }
        catch (SQLException e)
        {
            throw new HsException(RespCode.AC_SQL_ERROR.getCode(), e.getMessage());
        }
    }

    /**
     * 扣减账户余额记账中实现更新账户余额,新增余额快照记录,新增扣款分录信息
     * 
     * @param openAccBalanceList
     *            新增的账户余额对象集合
     * @param updateAccBalanceList
     *            更新的账户余额对象集合
     * @param accountBalanceSnapList
     *            新增余额快照记录集合
     * @param accountEntryList
     *            扣款分录对象集合
     * @throws HsException
     *             异常处理类
     */
    public void deductAccount(List<AccountBalance> openAccBalanceList, List<AccountBalance> updateAccBalanceList,
            List<AccountBalanceSnap> accountBalanceSnapList, List<AccountEntry> accountEntryList) throws HsException {
        try
        {
        	if (!accountBalanceSnapList.isEmpty())
            {
                BizLog.biz("HSXT_AC", "方法：updateAccountBalances", "accountBalanceSnapList", "新增余额快照记录集合插入到数据库");
                // 新增余额快照记录集合插入到数据库
                accountBalanceMapper.addAccountBalanceSnaps(accountBalanceSnapList);
            }
            if (!openAccBalanceList.isEmpty())
            {
                BizLog.biz("HSXT_AC", "方法：addAccountBalances", "openAccBalanceList", "插入新增的开户账户");
                // 插入新增的开户账户
                accountBalanceMapper.addAccountBalances(openAccBalanceList);
            }
            if (!updateAccBalanceList.isEmpty())
            {
                BizLog.biz("HSXT_AC", "方法：addAccountBalances", "updateAccBalanceList", "更新账户余额信息集合");
                for(int i=0; i<updateAccBalanceList.size(); i++)
                {
                	AccountBalance accountBalance = updateAccBalanceList.get(i);
                	// 更新账户余额
                	int rowCount = accountBalanceMapper.updateAccountBalance(accountBalance);
                	if(rowCount == 0)
                	{
                	    if(accountBalance.getCustType()==CustType.PERSON.getCode() || accountBalance.getCustType()==CustType.NOT_HS_PERSON.getCode())
                        {
                            SystemLog.debug("HSXT_AC", "方法：checkAccountEntryAmountInfo", RespCode.AC_BALANCE_DEFICIENCY
                                    .getCode()
                                    +"客户号："+accountBalance.getCustID()+"对应的账号类型:"+ accountBalance.getAccType() + "和客户类型:" + accountBalance.getCustType()+",余额不足");
                            throw new HsException(RespCode.AC_BALANCE_DEFICIENCY.getCode(), accountBalance.getCustID()+"对应的账号类型:"+ accountBalance.getAccType() + "和客户类型:" + accountBalance.getCustType()+",余额不足");
                        }
                        else
                        {
                            SystemLog.debug("HSXT_AC", "方法：checkAccountEntryAmountInfo", RespCode.AC_ENT_BALANCE_DEFICIENCY
                                    .getCode()
                                    +"客户号："+accountBalance.getCustID()+"对应的账号类型:"+ accountBalance.getAccType() + "和客户类型:" + accountBalance.getCustType()+",余额不足");
                            throw new HsException(RespCode.AC_ENT_BALANCE_DEFICIENCY.getCode(), accountBalance.getCustID()+"对应的账号类型:"+ accountBalance.getAccType() + "和客户类型:" + accountBalance.getCustType()+",余额不足");
                        }
                	}
                }
//                accountBalanceMapper.updateAccountBalances(updateAccBalanceList);
            }
            if (!accountEntryList.isEmpty())
            {
                BizLog.biz("HSXT_AC", "方法：addAccountEntrys", "accountEntryList", "新增记账记录集合插入到数据库");
                // 新增记账记录集合插入到数据库
                accountEntryMapper.addAccountEntrys(accountEntryList);
            }
        }
        catch (SQLException e)
        {
            throw new HsException(RespCode.AC_SQL_ERROR.getCode(), e.getMessage());
        }
    }

    /**
     * 冲正红冲记账进行更新账户余额信息,新增余额快照信息,新增红冲分录信息,更新原交易的红冲金额
     * 
     * @param accountBalanceList
     *            更新的账户余额信息集合
     * @param accountBalanceSnapList
     *            新增的余额快照表信息集合
     * @param accountEntryList
     *            新增的红冲分录记录集合
     * @param preAccountEntryList
     *            更新的原交易的红冲金额集合
     * @throws HsException
     *             异常处理类
     */
    public void correctAccount(List<AccountBalance> accountBalanceList,
            List<AccountBalanceSnap> accountBalanceSnapList, List<AccountEntry> accountEntryList,
            List<AccountEntry> preAccountEntryList) throws HsException {
        try
        {
            if (!accountBalanceSnapList.isEmpty())
            {
                // 新增余额快照表信息集合
                accountBalanceMapper.addAccountBalanceSnaps(accountBalanceSnapList);
            }
            
            if (!accountBalanceList.isEmpty())
            {
                // 更新账户余额信息集合
                accountBalanceMapper.updateAccountBalances(accountBalanceList);
            }

            if (!accountEntryList.isEmpty())
            {
                // 新增红冲分录记录
                accountEntryMapper.addAccountEntrys(accountEntryList);
            }

            if (!preAccountEntryList.isEmpty())
            {
                // 更新原交易的红冲金额
                accountEntryMapper.updateAccountEntrysByTrsNo(preAccountEntryList);
            }
        }
        catch (SQLException e)
        {
            throw new HsException(RespCode.AC_SQL_ERROR.getCode(), e.getMessage());
        }
    }

    /**
     * 单笔冲正红冲记账（更新账户余额信息，新增余额快照信息，新增原交易的红冲冲正记录，更新原交易的红冲金额）
     * 
     * @param accountBalanceList
     *            账户余额信息集合
     * @param accountBalanceSnapList
     *            余额快照表信息集合
     * @param accountEntryList
     *            把原交易金额变为负数的分录集合
     * @param updateAccountEntryList
     *            更新原交易的红冲金额记录集合
     * @throws HsException
     *             异常处理类
     */
    public void correctSingleAccount(List<AccountBalance> accountBalanceList,
            List<AccountBalanceSnap> accountBalanceSnapList, List<AccountEntry> accountEntryList,
            List<AccountEntry> updateAccEntryList) throws HsException {
        try
        {
            if (!accountBalanceSnapList.isEmpty())
            {
                // 新增余额快照表信息集合
                accountBalanceMapper.addAccountBalanceSnaps(accountBalanceSnapList);
            }
            
            if (!accountBalanceList.isEmpty())
            {
                // 更新账户余额信息集合
                accountBalanceMapper.updateAccountBalances(accountBalanceList);
            }

            if (!accountEntryList.isEmpty())
            {
                // 复制原交易所有分录，金额设为负，增加相关的交易类型和流水号
                accountEntryMapper.addAccountEntrys(accountEntryList);
            }

            if (!updateAccEntryList.isEmpty())
            {
                // 更新原交易的红冲金额
                accountEntryMapper.updateAccountEntrysByTrsNo(updateAccEntryList);
            }
        }
        catch (SQLException e)
        {
            throw new HsException(RespCode.AC_SQL_ERROR.getCode(), e.getMessage());
        }
    }

    /**
     * 对账务分录进行余额预留，释放，冻结，解冻的操作
     * 
     * @param accountBalanceList
     *            账户余额信息集合
     * @param accountBalanceSnapList
     *            余额快照表信息集合
     * @param accountEntryList
     *            分录集合
     * @throws HsException
     *             异常处理类
     * @throws SQLException
     */
    public void operateAccount(List<AccountBalance> openAccBalanceList, List<AccountBalance> updateAccBalanceList,
            List<AccountBalanceSnap> accountBalanceSnapList, List<AccountEntry> accountEntryList) throws SQLException {
        if (!openAccBalanceList.isEmpty())
        {
            // 新增（预留，释放，冻结，解冻）账户余额信息
            accountBalanceMapper.addAccountBalances(openAccBalanceList);
        }

        if (!updateAccBalanceList.isEmpty())
        {
            // 更新（预留，释放，冻结，解冻）账户余额信息
            accountBalanceMapper.updateAccountBalances(updateAccBalanceList);
        }
        if (!accountBalanceSnapList.isEmpty())
        {
            // 新增余额快照表信息
            accountBalanceMapper.addAccountBalanceSnaps(accountBalanceSnapList);
        }

        if (!accountEntryList.isEmpty())
        {
            // 新增相关的（预留，释放，冻结，解冻）分录信息
            accountEntryMapper.addAccountEntrys(accountEntryList);
        }
    }

    /**
     * 退单记账中实现更新账户余额,新增余额快照记录,新增扣款分录信息
     * 
     * @param updateAccBalanceList
     *            更新的账户余额对象集合
     * @param accountBalanceSnapList
     *            新增余额快照记录集合
     * @param accountEntryList
     *            扣款分录对象集合
     * @throws HsException
     *             异常处理类
     */
    public void chargebackAccount(List<AccountBalance> openAccBalanceList,List<AccountBalance> updateAccBalanceList,
            List<AccountBalanceSnap> accountBalanceSnapList, List<AccountEntry> accountEntryList) throws HsException {
        try
        {
            
            if (!accountBalanceSnapList.isEmpty())
            {
                BizLog.biz("HSXT_AC", "方法：updateAccountBalances", "accountBalanceSnapList", "新增余额快照记录集合插入到数据库");

                // 新增余额快照记录集合插入到数据库
                accountBalanceMapper.addAccountBalanceSnaps(accountBalanceSnapList);
            }
            if (!openAccBalanceList.isEmpty())
            {
                BizLog.biz("HSXT_AC", "方法：addAccountBalances", "openAccBalanceList", "新增余额记录集合插入到数据库");

                // 新增余额记录集合插入到数据库
                accountBalanceMapper.addAccountBalances(openAccBalanceList);
            }
            if (!updateAccBalanceList.isEmpty())
            {
                BizLog.biz("HSXT_AC", "方法：addAccountBalances", "updateAccBalanceList", "更新账户余额信息集合");

                // 更新账户余额
                accountBalanceMapper.updateAccountBalances(updateAccBalanceList);
            }
            if (!accountEntryList.isEmpty())
            {
                BizLog.biz("HSXT_AC", "方法：addAccountEntrys", "accountEntryList", "新增记账记录集合插入到数据库");
                // 新增记账记录集合插入到数据库
                accountEntryMapper.addAccountEntrys(accountEntryList);
            }
        }
        catch (SQLException e)
        {
            throw new HsException(RespCode.AC_SQL_ERROR.getCode(), e.getMessage());
        }
    }

    /**
     * 服务记账中实现更新账户余额,新增余额快照记录,新增扣款分录、增额分录信息
     * 
     * @param updateAccBalanceList
     *            更新的账户余额对象集合
     * @param accountBalanceSnapList
     *            新增余额快照记录集合
     * @param accountEntryList
     *            扣款分录、增额分录对象集合
     * @throws HsException
     *             异常处理类
     */
    public void chargeServiceAccount(List<AccountBalance> openAccBalanceList,List<AccountBalance> updateAccBalanceList,
            List<AccountBalanceSnap> accountBalanceSnapList, List<AccountEntry> accountEntryList) throws HsException {
        try
        {
            if (!accountBalanceSnapList.isEmpty())
            {
                BizLog.biz("HSXT_AC", "方法：updateAccountBalances", "accountBalanceSnapList", "新增余额快照记录集合插入到数据库");
                BizLog.biz("HSXT_AC", "参数：accountBalanceSnapList", "accountBalanceSnapList", JSON.toJSONString(accountBalanceSnapList));
                // 新增余额快照记录集合插入到数据库
                accountBalanceMapper.addAccountBalanceSnaps(accountBalanceSnapList);
            }
            if (!openAccBalanceList.isEmpty())
            {
                BizLog.biz("HSXT_AC", "方法：addAccountBalances", "openAccBalanceList", "插入新增的开户账户");
                BizLog.biz("HSXT_AC", "参数：openAccBalanceList", "openAccBalanceList", JSON.toJSONString(openAccBalanceList));
                // 插入新增的开户账户
                accountBalanceMapper.addAccountBalances(openAccBalanceList);
            }
            if (!updateAccBalanceList.isEmpty())
            {
                BizLog.biz("HSXT_AC", "方法：addAccountBalances", "updateAccBalanceList", "更新账户余额信息集合");
                BizLog.biz("HSXT_AC", "参数：updateAccBalanceList", "updateAccBalanceList", JSON.toJSONString(updateAccBalanceList));
                // 更新账户余额
                accountBalanceMapper.updateAccountBalances(updateAccBalanceList);
            }
            if (!accountEntryList.isEmpty())
            {
                BizLog.biz("HSXT_AC", "方法：addAccountEntrys", "accountEntryList", "新增记账记录集合插入到数据库");
                BizLog.biz("HSXT_AC", "参数：accountEntryList", "accountEntryList", JSON.toJSONString(accountEntryList));
                // 新增记账记录集合插入到数据库
                accountEntryMapper.addAccountEntrys(accountEntryList);
            }
        }
        catch (SQLException e)
        {
            throw new HsException(RespCode.AC_SQL_ERROR.getCode(), e.getMessage());
        }
    }
    
}
