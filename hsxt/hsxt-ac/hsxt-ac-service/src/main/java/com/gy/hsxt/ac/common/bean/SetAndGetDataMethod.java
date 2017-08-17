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

package com.gy.hsxt.ac.common.bean;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsi.lc.client.log4j.BizLog;
import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.ac.bean.AccountBalance;
import com.gy.hsxt.ac.bean.AccountBalanceSnap;
import com.gy.hsxt.ac.bean.AccountEntry;
import com.gy.hsxt.ac.bean.AccountService;
import com.gy.hsxt.ac.bean.AccountWriteBack;
import com.gy.hsxt.ac.mapper.AccountBalanceMapper;
import com.gy.hsxt.common.constant.BizGroup;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.guid.GuidAgent;
import com.gy.hsxt.uc.as.bean.ent.AsEntInfo;

/**
 * 封装或者获取数据方法
 * 
 * @Package: com.gy.hsxt.ac.common.bean
 * @ClassName: SetAndGetDataMethod
 * @Description: TODO
 * 
 * @author: weixz
 * @date: 2015-12-14 下午5:15:49
 * @version V1.0
 */
@Service
public class SetAndGetDataMethod {

    /** 账务余额的接口服务 */
    @Autowired
    AccountBalanceMapper accountBalanceMapper;

    /**
     * 校验方法
     */
    @Autowired
    private ValidateInfo validateInfo;

    /**
     * 封装accountBalanceSnap对象的信息
     * 
     * @param accountBalanceSnap
     *            （ 余额快照表 对象）
     * @param accountEntry
     *            账务分录对象
     * @param accountBalanceMap
     *            账户余额集合
     * @param entryNo
     *            账务序列ID
     */
    public void setAccountBalanceSnapInfo(AccountBalanceSnap accountBalanceSnap, AccountEntry accountEntry,
            Map<String, Object> accountBalanceMap, String entryNo) {
        BizLog.biz("HSXT_AC", "方法：setAccountBalanceSnapInfo", "accountEntry", "封装余额快照对象信息集合");
        accountBalanceSnap.setCustID(accountEntry.getCustID());// 客户全局编码
        accountBalanceSnap.setAccType(accountEntry.getAccType());// 账户类型编码
        accountBalanceSnap.setCustType(accountEntry.getCustType());// 客户类型
        accountBalanceSnap.setHsResNo(accountEntry.getHsResNo());// 互生号
//        accountBalanceSnap.setAccBalanceOld(String.valueOf(accountBalanceMap.get("oldAccBalance")));// 账户变更前余额值
//        accountBalanceSnap.setAccBalanceNew(String.valueOf(accountBalanceMap.get("newAccBalance")));// 账户变更后余额值
        accountBalanceSnap.setAmount(String.valueOf(accountBalanceMap.get("entryAmount")));//变更金额
        accountBalanceSnap.setCustTypeCategory((Integer)accountBalanceMap.get("custTypeCategory"));//客户类型分类

        // 账户余额的创建时间（String转Timestamp）
//        String createdDate = String.valueOf(accountBalanceMap.get("createdDate"));
//        Timestamp createdDateTim = null;
//        if (!"null".equals(createdDate) && !"".equals(createdDate))
//        {
//            createdDateTim = Timestamp.valueOf(createdDate);
//        }
//        accountBalanceSnap.setCreatedDateTim(createdDateTim);
//
//        // 账户余额的更新时间（String转Timestamp）
//        String updatedDate = String.valueOf(accountBalanceMap.get("updatedDate"));
//        Timestamp updatedDateTim = null;
//        if (!"null".equals(updatedDate) && !"".equals(updatedDate))
//        {
//            updatedDateTim = Timestamp.valueOf(updatedDate);
//        }
//        accountBalanceSnap.setUpdatedDateTim(updatedDateTim);

        // 变更类型（1 ：单笔）
        accountBalanceSnap.setChangeType(1);

        // 账务分录的序列ID
        accountBalanceSnap.setChangeRecordID(entryNo);
    }

    /**
     * 开户或者更新账户余额信息
     * 
     * @param accountEntry
     * @param accountBalanceMap
     * @throws HsException
     */
    public void addOrUpdateAccountBalanceInfo(List<AccountBalance> openAccBalList,
            List<AccountBalance> updateAccBalList, AccountEntry accountEntry, Map<String, Object> accountBalanceMap,
            Integer custTypeCategory) throws HsException {
        BizLog.biz("HSXT_AC", "方法：addOrUpdateAccountBalanceInfo", "openAccBalList,updateAccBalList", "封装开户或者更新账户余额信息集合");
        Integer custType = accountEntry.getCustType();// 客户类型
        String accType = accountEntry.getAccType();// 账户类型编码
        String custId = accountEntry.getCustID();// 客户全局编码
        String hsResNo = accountEntry.getHsResNo();// 互生号
        BigDecimal newAccBalance = (BigDecimal) accountBalanceMap.get("newAccBalance");// 当前记账分录的金额之和
        BigDecimal accBalance = (BigDecimal) accountBalanceMap.get("accBalance");// 当前账户余额
        BigDecimal curAmount = newAccBalance.subtract(accBalance);// 当前记账相同客户全局编码和账户类型累加后的新增或更新金额值
        accountBalanceMap.put("entryAmount", curAmount);
        String guaranteeIntegral = accountEntry.getGuaranteeIntegral();//当前业务保底值
        String accFlag = accountEntry.getPositiveNegative();// 账户是否为正负的标识

        // 创建一个AccountBalance账户余额实例，为新增或者更新设置相应条件
        AccountBalance accountBalance = new AccountBalance();
        accountBalance.setCustID(custId);
        accountBalance.setAccType(accType);
        accountBalance.setCustType(custType);
        accountBalance.setAccBalance(String.valueOf(curAmount));
        accountBalance.setCustTypeCategory(custTypeCategory);
        accountBalance.setGuaranteeIntegral(guaranteeIntegral);
        if(StringUtils.isNotBlank(accFlag)){
            accountBalance.setAccFlag(Integer.valueOf(accFlag));
        }

        // 如果不存在记录，则可以进行开户
        if (!(Boolean) accountBalanceMap.get("accountOpenFlag"))
        {
            // 新增账户余额信息（开户）
            accountBalance.setAccStatus(0);
            accountBalance.setHsResNo(hsResNo);

            // 过滤相同客户全局编码和账户类型的对象信息，并合并为一次新增，取累加后的相同客户全局编码和账户类型的对象中余额新增值
            filterBalanceInfo(openAccBalList, accountBalance, curAmount);

        }
        else
        {
            // 过滤相同客户全局编码和账户类型的对象信息，并合并为一次更新,取累加后的相同客户全局编码和账户类型的对象中余额更新值
            filterBalanceInfo(updateAccBalList, accountBalance, curAmount);
        }
    }

    /**
     * 封装正常账务分录对应的(预留、释放，冻结，解冻)账户类型的分录
     * 
     * @param account_Entry
     *            特殊(预留、释放，冻结，解冻)的账务分录对象
     * @param accountEntry
     *            正常账务分录对象
     * @param flag
     *            账户类型标识字符
     * @throws HsException
     *             异常处理类
     */
    public void setAccountEntryInfo(AccountEntry account_Entry, AccountEntry accountEntry, char flag)
            throws SQLException {
        account_Entry.setSysEntryNo(accountEntry.getSysEntryNo());
        account_Entry.setCustID(accountEntry.getCustID());
        account_Entry.setCustType(accountEntry.getCustType());
        account_Entry.setHsResNo(accountEntry.getHsResNo());
        account_Entry.setAccName(accountEntry.getAccName());
        account_Entry.setBatchNo(accountEntry.getBatchNo());
        account_Entry.setTransNo(accountEntry.getTransNo());
        account_Entry.setWriteBack(accountEntry.getWriteBack());
        account_Entry.setTransType(accountEntry.getTransType());
        account_Entry.setRelTransNo(accountEntry.getRelTransNo());
        account_Entry.setRelTransType(accountEntry.getRelTransType());
        account_Entry.setTransDateTim(Timestamp.valueOf(accountEntry.getTransDate()));
        account_Entry.setFiscalDateTim(Timestamp.valueOf(accountEntry.getFiscalDate()));

        String preEntryNo = getPreEntryNo();//获取分录的流水号前缀
        
        // 当前插入分录的序列值
        String entry_No = GuidAgent.getStringGuid(preEntryNo);
        account_Entry.setEntryNo(entry_No);

        // 把当前账户类型变为(预留、释放，冻结，解冻)账户类型
        String accType = accountEntry.getAccType();
        char accTypes[] = accType.toCharArray();
        String newccType = String.valueOf(accTypes, 0, 4);
        StringBuffer strBuffer = new StringBuffer(newccType);
        strBuffer.append(flag);
        newccType = String.valueOf(strBuffer);
        account_Entry.setAccType(newccType);
    }

    /**
     * 封装账务分录
     * 
     * @param account_Entry
     *            封装新的账务分录对象
     * @param accountEntry
     *            当前账务分录对象
     * @throws HsException
     *             异常处理类
     */
    public void setAccountEntryInfo(AccountEntry account_Entry, AccountEntry accountEntry) throws SQLException {
        account_Entry.setSysEntryNo(accountEntry.getSysEntryNo());
        account_Entry.setCustID(accountEntry.getCustID());
        account_Entry.setAddAmount(accountEntry.getAddAmount());
        account_Entry.setSubAmount(accountEntry.getSubAmount());
        account_Entry.setAccType(accountEntry.getAccType());
        account_Entry.setCustType(accountEntry.getCustType());
        account_Entry.setHsResNo(accountEntry.getHsResNo());
        account_Entry.setAccName(accountEntry.getAccName());
        account_Entry.setBatchNo(accountEntry.getBatchNo());
        account_Entry.setTransSys(accountEntry.getTransSys());
        account_Entry.setRelSysEntryNo(accountEntry.getRelSysEntryNo());
        //获取原始交易流水号
    	String sourceTransNo = accountEntry.getSourceTransNo();
    	
    	//原始交易流水号没有就储存交易流水号
    	if(sourceTransNo == null || "".equals(sourceTransNo))
    	{
    		account_Entry.setSourceTransNo(accountEntry.getTransNo());
    	}
    	else
    	{
    		account_Entry.setSourceTransNo(accountEntry.getSourceTransNo());
    	}
        account_Entry.setTransNo(accountEntry.getTransNo());
        account_Entry.setWriteBack(accountEntry.getWriteBack());
        account_Entry.setTransType(accountEntry.getTransType());
        account_Entry.setRelTransNo(accountEntry.getRelTransNo());
        account_Entry.setRelTransType(accountEntry.getRelTransType());
        account_Entry.setTransDateTim(Timestamp.valueOf(accountEntry.getTransDate()));
        account_Entry.setFiscalDateTim(Timestamp.valueOf(accountEntry.getFiscalDate()));
    }

    /**
     * 封装新增或者更新账户余额(预留、释放，冻结，解冻)信息集合
     * 
     * @param openAccBalanceList
     *            (预留、释放，冻结，解冻)账户余额开户信息集合
     * @param updateAccBalanceList
     *            账户余额更新信息集合
     * @param accountEntry
     *            账务分录对象
     * @param flag
     *            账户类型标识字符
     * @param OperateTpye
     *            业务操作类型
     * @throws SQLException
     *             异常处理类
     * @throws HsException
     *             异常处理类
     */
    public void setAccountBalanceInfo(List<AccountBalance> openAccBalanceList,
            List<AccountBalance> updateAccBalanceList, AccountEntry accountEntry, char flag, Integer OperateTpye)
            throws SQLException, HsException {

        Integer custType = accountEntry.getCustType();// 客户类型

        // 判断当前客户类型所属哪个客户分类（0：消费者，1：企业，2：平台）,为空，则该客户类型不存在
        Integer custTypeCategory = validateInfo.checkCustTypeCategory(custType);
        String accType = accountEntry.getAccType();// 账户类型编码
        String custId = accountEntry.getCustID();// 客户全局编码
        String hsResNo = accountEntry.getHsResNo();// 互生号
        String amount = accountEntry.getAmount();// 当前(预留、释放，冻结，解冻)金额
        BigDecimal bigAmount = new BigDecimal(amount);// 当前(预留、释放，冻结，解冻)金额

        // 创建一个AccountBalance账户余额实例
        AccountBalance accountBalance = new AccountBalance();
        accountBalance.setCustID(custId);
        accountBalance.setAccType(accType);
        accountBalance.setCustType(custType);

        // 判断当前客户类型所属哪个客户分类（0：消费者，1：企业，2：平台）
        accountBalance.setCustTypeCategory(custTypeCategory);

        // 操作为余额预留和余额冻结，当前金额要变为负数
        if (OperateTpye.intValue() == AcConstant.OPERATE_TYPE[0].intValue() || OperateTpye.intValue() == AcConstant.OPERATE_TYPE[2].intValue())
        {
            accountBalance.setAccBalance(String.valueOf(BigDecimal.valueOf(0).subtract(bigAmount)));
        }

        // 操作为余额释放和余额解冻
        if (OperateTpye.intValue() == AcConstant.OPERATE_TYPE[1].intValue() || OperateTpye == AcConstant.OPERATE_TYPE[3].intValue())
        {
            accountBalance.setAccBalance(amount);
        }
        updateAccBalanceList.add(accountBalance);

        // 把当前账户类型变为(预留、释放，冻结，解冻)账户类型
        char accTypes[] = accType.toCharArray();
        String newccType = String.valueOf(accTypes, 0, 4);
        StringBuffer strBuffer = new StringBuffer(newccType);
        strBuffer.append(flag);
        newccType = String.valueOf(strBuffer);

        // 是否存在(预留、释放，冻结，解冻)账户余额
        AccountBalance accBalance = accountBalanceMapper.searchAccountBalanceInfo(custId, newccType);
        AccountBalance newAccBalance = new AccountBalance();
        newAccBalance.setCustID(custId);
        newAccBalance.setAccType(newccType);
        newAccBalance.setCustType(custType);

        // 判断当前客户类型所属哪个客户分类（0：消费者，1：企业，2：平台）
        newAccBalance.setCustTypeCategory(custTypeCategory);
        newAccBalance.setHsResNo(hsResNo);
        newAccBalance.setAccBalance(amount);

        // 不存在，新增一个特殊账户余额记录，存在更新原来账户余额的金额
        if (accBalance == null)
        {
            // 操作为余额释放，验证是否有对应的预留账户余额
            if (OperateTpye == AcConstant.OPERATE_TYPE[1])
            {
                throw new HsException(RespCode.AC_PARAMETER_NULL.getCode(), "custId = " + custId + ",newccType = "
                        + newccType + ",没有对应的预留账户可以释放");
            }

            // 操作为余额解冻，验证是否有对应的冻结账户余额
            if (OperateTpye == AcConstant.OPERATE_TYPE[3])
            {
                throw new HsException(RespCode.AC_PARAMETER_NULL.getCode(), "custId = " + custId + ",newccType = "
                        + newccType + ",没有对应的冻结账户可以解冻");
            }
            newAccBalance.setAccStatus(0);
            openAccBalanceList.add(newAccBalance);
        }
        else
        {
            // 操作为余额释放和余额解冻。验证当前释放、解冻金额是否大于之前预留、冻结的金额
            if (OperateTpye == AcConstant.OPERATE_TYPE[1] || OperateTpye == AcConstant.OPERATE_TYPE[3])
            {
                // （预留或者冻结）账户余额值
                String accBalanceAmt = accBalance.getAccBalance();
                BigDecimal accBalAmt = new BigDecimal(accBalanceAmt);

                // 当前（释放或者解冻）的金额大于之前（预留或者冻结）账户余额值
                if (accBalAmt.compareTo(bigAmount) == -1)
                {
                    if (OperateTpye == AcConstant.OPERATE_TYPE[1])
                    {
                        throw new HsException(RespCode.AC_AMOUNT_BEYOND.getCode(), "释放金额： " + amount + ",预留金额："
                                + accBalanceAmt + ",释放金额大于预留金额");
                    }
                    if (OperateTpye == AcConstant.OPERATE_TYPE[3])
                    {
                        throw new HsException(RespCode.AC_AMOUNT_BEYOND.getCode(), "解冻金额： " + amount + ",冻结金额："
                                + accBalanceAmt + ",解冻金额大于冻结金额");
                    }
                }

                // 余额释放和解冻，把当前金额变为负数。用于更新账户余额中的值。
                newAccBalance.setAccBalance(String.valueOf(BigDecimal.valueOf(0).subtract(bigAmount)));
            }
            updateAccBalanceList.add(newAccBalance);
        }

    }

    /**
     * 获取消费者客户类型
     * 
     * @return 消费者客户类型Map
     */
    public Map<Integer, Integer> getPerTypes() {

        String[] perTypes = PropertyConfigurer.getProperty("ac.custType.perType").split("\\|");// 消费者客户类型

        Map<Integer, Integer> perTypeMap = new HashMap<Integer, Integer>();

        // 消费者客户类型
        for (String perType : perTypes)
        {
            Integer personType = Integer.valueOf(perType);
            perTypeMap.put(personType, personType);
        }

        return perTypeMap;
    }

    /**
     * 获取企业客户类型
     * 
     * @return 企业客户类型Map
     */
    public Map<Integer, Integer> getEntTypes() {

        String[] entTypes = PropertyConfigurer.getProperty("ac.custType.entType").split("\\|");// 企业客户类型

        Map<Integer, Integer> entTypeMap = new HashMap<Integer, Integer>();

        // 消费者客户类型
        for (String entType : entTypes)
        {
            Integer enterpriseType = Integer.valueOf(entType);
            entTypeMap.put(enterpriseType, enterpriseType);
        }

        return entTypeMap;
    }

    /**
     * 获取平台客户类型
     * 
     * @return 平台客户类型Map
     */
    public Map<Integer, Integer> getPfTypes() {

        String[] pfTypes = PropertyConfigurer.getProperty("ac.custType.pfType").split("\\|");// 平台客户类型

        Map<Integer, Integer> pfTypeMap = new HashMap<Integer, Integer>();

        // 消费者客户类型
        for (String pfType : pfTypes)
        {
            Integer plaformType = Integer.valueOf(pfType);
            pfTypeMap.put(plaformType, plaformType);
        }

        return pfTypeMap;
    }
    
    /**
     * 获取只记分录，不做余额变化的账户
     * 
     * @return 只记分录，不做余额变化的账户类型Map
     */
    public Map<String, String> getSpecialAccTypes() {

        String[] accTypes = PropertyConfigurer.getProperty("ac.noBalance.accTypes").split("\\|");// 只记分录，不做余额变化的账户

        Map<String, String> accTypeMap = new HashMap<String, String>();

        // 消费者客户类型
        for (String accType : accTypes)
        {
            accTypeMap.put(accType, accType);
        }

        return accTypeMap;
    }

    /**
     * 过滤相同客户全局编码和账户类型的对象信息，取累加后的相同客户全局编码和账户类型的对象中余额新增值
     * 
     * @param accBalanceList
     *            账户余额对象集合
     * @param accountBalance
     *            账户余额对象
     * @param amount
     *            金额
     */
    public void filterBalanceInfo(List<AccountBalance> accBalanceList, AccountBalance accountBalance, BigDecimal amount) {
        String custId = accountBalance.getCustID();// 当前客户全局编码
        String accType = accountBalance.getAccType();// 当前账户类型

        // 过滤相同客户全局编码和账户类型的对象信息，取累加后的相同客户全局编码和账户类型的对象中余额新增值
        if (accBalanceList.size() > 0)
        {
            Boolean flag = true;// 相同客户全局编码和账户类型的对象标记
            for (AccountBalance accBal : accBalanceList)
            {
                String cust_id = accBal.getCustID();// 之前对象保存的客户全局编码
                String acc_Type = accBal.getAccType();// 之前对象保存的账户类型
                if (cust_id.equals(custId) && acc_Type.equals(accType))
                {
                    accBal.setAccBalance(String.valueOf(amount));
                    flag = false;
                    break;
                }
            }
            if (flag)
            {
                accBalanceList.add(accountBalance);
            }
        }
        else
        {
            accBalanceList.add(accountBalance);
        }
    }

    public void setDataInfo(List<AccountEntry> acctEntryList, AccountEntry accountEntry,
            List<AccountBalance> updateAccBalanceList, AccountBalance accountBalance, BigDecimal entryAmount,
            BigDecimal accBalance, BigDecimal newAccBalance, Integer custTypeCategory,
            List<AccountBalanceSnap> accountBalanceSnapList,Map<String, String> accTypeMap, boolean flag) throws SQLException {

        // 封装扣款/退款账务分录信息
        AccountEntry account_Entry = new AccountEntry();
        setAccountEntryInfo(account_Entry, accountEntry);
        String preEntryNo = getPreEntryNo();//获取分录的流水号前缀
        BigDecimal amount = new BigDecimal(0);
        
        // ture为扣款，false 为退款
        if (flag)
        {
            account_Entry.setSubAmount(String.valueOf(entryAmount));// 扣款账户分录减向金额为当前账户余额
            accountBalance.setAccBalance(String.valueOf(BigDecimal.valueOf(0).subtract(entryAmount)));
            amount = amount.subtract(entryAmount);
        }
        else
        {
            account_Entry.setAddAmount(String.valueOf(entryAmount));// 退款账户分录增向金额为原记账分录的金额
            accountBalance.setAccBalance(String.valueOf(entryAmount));
            amount = entryAmount;
        }

        // 当前插入分录的序列值
        String entryNo = GuidAgent.getStringGuid(preEntryNo);
        account_Entry.setEntryNo(entryNo);
        acctEntryList.add(account_Entry);

        // 当前客户类型所属哪个客户分类（0：消费者，1：企业，2：平台）
        accountBalance.setCustTypeCategory(custTypeCategory);
        //保底值
        String guaranteeIntegral = accountEntry.getGuaranteeIntegral();
        accountBalance.setGuaranteeIntegral(guaranteeIntegral);
        String accFlag = accountEntry.getPositiveNegative();// 账户可为正负数标识
        if(StringUtils.isNotBlank(accFlag)){
            accountBalance.setAccFlag(Integer.valueOf(accFlag));
        }
        
        // 判断是否只是做记账分录的账户
        String accType = accountEntry.getAccType();
        if(accTypeMap.get(accType) == null){
            updateAccBalanceList.add(accountBalance);
            // 余额快照信息对象
            AccountBalanceSnap accountBalanceSnap = new AccountBalanceSnap();
            // 封装accountBalanceSnap（余额快照表）对象的信息
            Map<String, Object> accountBalanceMap = new HashMap<String, Object>();
            accountBalanceMap.put("entryAmount", amount);
            accountBalanceMap.put("custTypeCategory", custTypeCategory);
            accountBalanceMap.put("oldAccBalance", accBalance);// 当前账户余额的旧余额
            accountBalanceMap.put("newAccBalance", newAccBalance);// 当前账户余额的新余额
            accountBalanceMap.put("createdDate", accountBalance.getCreatedDate());// 当前账户余额表信息的创建时间
            accountBalanceMap.put("updatedDate", accountBalance.getUpdatedDate());// 当前账户余额表信息的更新时间
            setAccountBalanceSnapInfo(accountBalanceSnap, accountEntry, accountBalanceMap, entryNo);
            accountBalanceSnapList.add(accountBalanceSnap);
        }
    }

    public void setEntryDataInfo(List<AccountEntry> acctEntryList, AccountEntry accountEntry,
            Map<String, BigDecimal> accBalanceMap, List<AccountBalance> openAccBalanceList,
            List<AccountBalance> updateAccBalanceList, Integer custTypeCategory,
            List<AccountBalanceSnap> accountBalanceSnapList,Map<String, String> accTypeMap) throws SQLException {

        // 校验AccountEntry对象及对象中的条件
        validateInfo.checkAccountEntryInfo(accountEntry, false);
        
        String preEntryNo = getPreEntryNo();//获取分录的流水号前缀

        // 检验账户余额记录信息是否存在和该分录的变化金额是否在账户和客户关系中最大值和最小值之间
        Map<String, Object> accountBalanceMap = validateInfo.checkAccountEntryAmountInfo(accBalanceMap, accountEntry,
                false);

        AccountEntry account_Entry = new AccountEntry();// 封装扣款账务
        setAccountEntryInfo(account_Entry, accountEntry);

        // 当前插入分录的序列值
        String entryNo = GuidAgent.getStringGuid(preEntryNo);
        account_Entry.setEntryNo(entryNo);
        acctEntryList.add(account_Entry);
        
        // 判断是否只是做记账分录的账户
        String accType = accountEntry.getAccType();
        if(accTypeMap.get(accType) == null){
            // 开户或者更新账户余额信息
            addOrUpdateAccountBalanceInfo(openAccBalanceList, updateAccBalanceList, accountEntry, accountBalanceMap,
                    custTypeCategory);
            // 余额快照信息对象
            AccountBalanceSnap accountBalanceSnap = new AccountBalanceSnap();
            accountBalanceMap.put("custTypeCategory", custTypeCategory);
            // 封装accountBalanceSnap（余额快照表）对象的信息
            setAccountBalanceSnapInfo(accountBalanceSnap, accountEntry, accountBalanceMap, entryNo);
            accountBalanceSnapList.add(accountBalanceSnap);
        }
    }

    public BigDecimal getBigdAmount(String amountStr) {

        BigDecimal amount = BigDecimal.valueOf(0);
        if (amountStr != null && !"".equals(amountStr) && !"null".equals(amountStr))
        {
            amount = new BigDecimal(amountStr);
        }
        return amount;
    }

    // 封装服务记账中扣款分录信息
    public void setServerSubEntry(AccountService accountService, List<AccountEntry> acctEntryList,
            AccountBalance accountBalance, List<AccountBalance> updateAccBalanceList,
            List<AccountBalanceSnap> accountBalanceSnapList) {

    	String preEntryNo = getPreEntryNo();//获取分录的流水号前缀
        String custId = accountService.getCustID();// 客户全局编码
        String transSys = accountService.getTransSys();//交易系统
        String accTypes = accountService.getAccTypes();// 账户类型编码集
        String accType[] = accTypes.split("\\|");// 以分隔符"|"拆分扣款账户字符串集
        Integer custType = accountService.getCustType();// 客户类型
        String hsResNo = accountService.getHsResNo(); // 互生号
        String transType = accountService.getTransType();// 交易类型
        String transNo = accountService.getTransNo();// 交易流水号
        String sourceTransNo = accountService.getSourceTransNo(); //原始交易流水号
        String amount = accountService.getAmount();// 服务记账金额
        BigDecimal newAmount = new BigDecimal(amount);// 服务记账金额 String
                                                      // -->BigDecimal
        // 封装扣减账务分录对象
        AccountEntry accountEntry = new AccountEntry();// 账务分录对象初始化
        accountEntry.setAccType(accType[0]);
        accountEntry.setCustID(custId);
        accountEntry.setCustType(custType);
        accountEntry.setHsResNo(hsResNo);
        accountEntry.setSubAmount(amount);
        accountEntry.setAddAmount("0");
        accountEntry.setWriteBack(0);
        accountEntry.setTransType(transType);
        accountEntry.setTransSys(transSys);
        accountEntry.setTransNo(transNo);
        if(sourceTransNo == null || "".equals(sourceTransNo))
        {
            accountEntry.setSourceTransNo(transNo);
        }
        accountEntry.setBatchNo("1");
        String sysEntryNo = GuidAgent.getStringGuid(preEntryNo);
        accountEntry.setSysEntryNo(sysEntryNo);
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 日期格式
        String curTime = dateFormat.format(now);
        accountEntry.setTransDateTim(Timestamp.valueOf(curTime));
        accountEntry.setFiscalDateTim(Timestamp.valueOf(curTime));
        // 当前插入分录的序列值
        String entryNo = GuidAgent.getStringGuid(preEntryNo);
        accountEntry.setEntryNo(entryNo);
        acctEntryList.add(accountEntry);

        // 判断当前客户类型所属哪个客户分类（0：消费者，1：企业，2：平台）
        Integer custTypeCategory = validateInfo.checkCustTypeCategory(custType);
        accountBalance.setCustTypeCategory(custTypeCategory);
        accountBalance.setAccBalance(String.valueOf(BigDecimal.valueOf(0).subtract(newAmount)));
        // 封装账户余额更新信息集合
        updateAccBalanceList.add(accountBalance);

        // 扣减账户当前的账户余额
        BigDecimal accBalance = accountBalance.getAccBalance() == null ? BigDecimal.valueOf(0) : new BigDecimal(
                accountBalance.getAccBalance());
        // 扣减账户扣减金额后的账户余额
        BigDecimal newSubAmount = accBalance.subtract(newAmount);

        // 余额快照信息对象
        AccountBalanceSnap accountBalanceSnap = new AccountBalanceSnap();
        Map<String, Object> accountBalanceMap = new HashMap<String, Object>();
        accountBalanceMap.put("oldAccBalance", accBalance);// 当前账户余额的旧余额
        accountBalanceMap.put("newAccBalance", newSubAmount);// 当前账户余额的新余额
        accountBalanceMap.put("createdDate", accountBalance.getCreatedDate());// 当前账户余额表信息的创建时间
        accountBalanceMap.put("updatedDate", accountBalance.getUpdatedDate());// 当前账户余额表信息的更新时间
        accountBalanceMap.put("custTypeCategory", custTypeCategory);
        accountBalanceMap.put("entryAmount", BigDecimal.valueOf(0).subtract(newAmount));

        // 封装accountBalanceSnap（余额快照表）对象的信息
        setAccountBalanceSnapInfo(accountBalanceSnap, accountEntry, accountBalanceMap, entryNo);
        accountBalanceSnapList.add(accountBalanceSnap);
    }

    // 封装服务记账中增额分录信息
    public void setServerAddEntry(AccountService accountService, List<AccountEntry> acctEntryList,
            AccountBalance accountBalance, List<AccountBalance> updateAccBalanceList,
            List<AccountBalance> openAccBalanceList, List<AccountBalanceSnap> accountBalanceSnapList, AsEntInfo asEntInfo) {

    	String preEntryNo = getPreEntryNo();//获取分录的流水号前缀
    	
        String custId = asEntInfo.getEntCustId();// 平台客户全局编码
        String transSys = accountService.getTransSys();//交易系统
        String accTypes = accountService.getAccTypes();// 账户类型编码集
        String accType[] = accTypes.split("\\|");// 以分隔符"|"拆分扣款账户字符串集
        Integer custType = 6;// 地区平台客户类型
        String hsResNo = asEntInfo.getEntResNo(); // 互生号
        String transType = accountService.getTransType();// 交易类型
        String transNo = accountService.getTransNo();// 交易流水号
        String sourceTransNo = accountService.getSourceTransNo(); //原始交易流水号
        String amount = accountService.getAmount();// 服务记账金额
        BigDecimal newAmount = new BigDecimal(amount);// 服务记账金额 String
                                                      // -->BigDecimal
        // 封装增额账务分录对象
        AccountEntry account_Entry = new AccountEntry();// 账务分录对象初始化
        account_Entry.setAccType(accType[1]);
        account_Entry.setCustID(custId);
        account_Entry.setCustType(custType);
        account_Entry.setHsResNo(hsResNo);
        account_Entry.setSubAmount("0");
        account_Entry.setAddAmount(amount);
        account_Entry.setWriteBack(0);
        account_Entry.setTransType(transType);
        account_Entry.setTransSys(transSys);
        account_Entry.setTransNo(transNo);
        if(sourceTransNo == null || "".equals(sourceTransNo))
        {
            account_Entry.setSourceTransNo(transNo);
        }
        account_Entry.setBatchNo("1");
        String sysEntry_No = GuidAgent.getStringGuid(preEntryNo);
        account_Entry.setSysEntryNo(sysEntry_No);
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 日期格式
        String curTime = dateFormat.format(now);
        account_Entry.setTransDateTim(Timestamp.valueOf(curTime));
        account_Entry.setFiscalDateTim(Timestamp.valueOf(curTime));
        // 当前插入分录的序列值
        String entry_No = GuidAgent.getStringGuid(preEntryNo);
        account_Entry.setEntryNo(entry_No);
        acctEntryList.add(account_Entry);
        
        // 判断当前客户类型所属哪个客户分类（0：消费者，1：企业，2：平台）
        Integer custTypeCategory = validateInfo.checkCustTypeCategory(custType);
        BigDecimal accBalance = BigDecimal.valueOf(0); //增额前的账户余额
        BigDecimal newAddAmount = BigDecimal.valueOf(0);//增额后的账户余额
        String createdDate = null;
        String updatedDate = null;  
        
        if(accountBalance == null){
            accountBalance = new AccountBalance();
            accountBalance.setCustID(custId);
            accountBalance.setAccType(accType[1]);
            accountBalance.setHsResNo(hsResNo);
            accountBalance.setCustTypeCategory(custTypeCategory);
            accountBalance.setAccStatus(0);
            accountBalance.setAccBalance(amount);
            accountBalance.setCustType(custType);
            openAccBalanceList.add(accountBalance);
        }else{
            
            // 增额账户当前的账户余额
            accBalance = accountBalance.getAccBalance() == null ? BigDecimal.valueOf(0) : new BigDecimal(
                    accountBalance.getAccBalance());
            // 增额账户增加金额后的账户余额
            newAddAmount = accBalance.subtract(newAmount);
            accountBalance.setCustTypeCategory(custTypeCategory);
            accountBalance.setAccBalance(amount);
            // 封装账户余额更新信息集合
            updateAccBalanceList.add(accountBalance);
            createdDate = accountBalance.getCreatedDate();
            updatedDate = accountBalance.getUpdatedDate();
        }
        
        // 余额快照信息对象
        AccountBalanceSnap account_BalanceSnap = new AccountBalanceSnap();
        Map<String, Object> account_BalanceMap = new HashMap<String, Object>();
        account_BalanceMap.put("oldAccBalance", accBalance);// 当前账户余额的旧余额
        account_BalanceMap.put("newAccBalance", newAddAmount);// 当前账户余额的新余额
        account_BalanceMap.put("createdDate", createdDate);// 当前账户余额表信息的创建时间
        account_BalanceMap.put("updatedDate", updatedDate);// 当前账户余额表信息的更新时间
        account_BalanceMap.put("custTypeCategory", custTypeCategory);
        account_BalanceMap.put("entryAmount", amount);

        // 封装accountBalanceSnap（余额快照表）对象的信息
        setAccountBalanceSnapInfo(account_BalanceSnap, account_Entry, account_BalanceMap, entry_No);
        accountBalanceSnapList.add(account_BalanceSnap);
    }
    
    /**
     * 获取分录的流水号前缀
     * 
     * @return 分录的流水号前缀
     */
    public String getPreEntryNo() {
        
        String sysInstanceNo = PropertyConfigurer.getProperty("system.instance.no");// 当前机器编号
        return BizGroup.AC + sysInstanceNo;
    }

    /**
     * 验证冲正对象参数
     */
    public void validateBack(AccountWriteBack writeBack) throws HsException{
        
        int writeb = writeBack.getWriteBack();// 冲正红冲标识
        
        if(writeb == 0)
        {
            SystemLog.debug("HSXT_AC", "方法：correctSingleAccount", RespCode.AC_PARAMETER_NULL.getCode()
                    + "writeBack:冲正红冲标识不能为正常状态0");
            throw new HsException(RespCode.AC_PARAMETER_NULL.getCode(), "writeBack:冲正红冲标识不能为正常状态0");
        }

        String transType = writeBack.getTransType();// 交易类型
        if (StringUtils.isBlank(transType))
        {
            SystemLog.debug("HSXT_AC", "方法：correctSingleAccount", RespCode.AC_PARAMETER_NULL.getCode()
                    + "transType:交易类型为空");
            throw new HsException(RespCode.AC_PARAMETER_NULL.getCode(), "transType:交易类型为空");
        }

        String relTransNo = writeBack.getRelTransNo();// 关联交易类型流水号
        if (StringUtils.isBlank(relTransNo))
        {
            SystemLog.debug("HSXT_AC", "方法：correctSingleAccount", RespCode.AC_PARAMETER_NULL.getCode()
                    + "relTransNo:关联交易类型流水号为空");
            throw new HsException(RespCode.AC_PARAMETER_NULL.getCode(), "relTransNo:关联交易类型流水号为空");
        }
        
    }
    /**
     * 根据账户类型判断异常码
     * @param accType
     * @return
     */
    public int getResCodeByAccType(String accType,Integer custTypeCategory){
        
        if(accType.equals(AcConstant.ACCTYPES[0]) && custTypeCategory == AcConstant.CUST_TYPE_CATEGORY[0]){
            return RespCode.AC_PER_JF_DEFICIENCY.getCode();
        }
        if(accType.equals(AcConstant.ACCTYPES[1]) && custTypeCategory == AcConstant.CUST_TYPE_CATEGORY[0]){
            return RespCode.AC_PER_HSB_DEFICIENCY.getCode();
        }
        if(accType.equals(AcConstant.ACCTYPES[2]) && custTypeCategory == AcConstant.CUST_TYPE_CATEGORY[0]){
            return RespCode.AC_PER_HB_DEFICIENCY.getCode();
        }
        if(accType.equals(AcConstant.ACCTYPES[0]) && custTypeCategory == AcConstant.CUST_TYPE_CATEGORY[1]){
            return RespCode.AC_ENT_JF_DEFICIENCY.getCode();
        }
        if(accType.equals(AcConstant.ACCTYPES[1]) && custTypeCategory == AcConstant.CUST_TYPE_CATEGORY[1]){
            return RespCode.AC_ENT_HSB_DEFICIENCY.getCode();
        }
        if(accType.equals(AcConstant.ACCTYPES[2]) && custTypeCategory == AcConstant.CUST_TYPE_CATEGORY[1]){
            return RespCode.AC_ENT_HB_DEFICIENCY.getCode();
        }
        return RespCode.AC_BALANCE_DEFICIENCY.getCode();
    }
    
}
