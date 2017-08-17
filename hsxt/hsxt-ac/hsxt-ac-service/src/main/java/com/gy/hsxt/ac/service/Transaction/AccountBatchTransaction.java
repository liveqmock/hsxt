package com.gy.hsxt.ac.service.Transaction;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.ac.bean.AccountBalance;
import com.gy.hsxt.ac.bean.AccountBalanceSnap;
import com.gy.hsxt.ac.bean.AccountBatchJob;
import com.gy.hsxt.ac.bean.AccountCustType;
import com.gy.hsxt.ac.bean.AccountEntry;
import com.gy.hsxt.ac.common.bean.AcConstant;
import com.gy.hsxt.ac.common.bean.SetAndGetDataMethod;
import com.gy.hsxt.ac.mapper.AccountBalanceMapper;
import com.gy.hsxt.ac.mapper.AccountBatchProcesMapper;
import com.gy.hsxt.ac.mapper.AccountCustTypeMapper;
import com.gy.hsxt.ac.mapper.AccountEntryMapper;
import com.gy.hsxt.common.constant.CustType;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.guid.GuidAgent;

/**
 * 批处理统一事务Service
 * 
 * @Package: com.gy.hsxt.ac.service
 * @ClassName: AccountBatchRunnableService
 * @Description: TODO
 * 
 * @author: maocan
 * @date: 2015-8-31 上午10:19:22
 * @version V1.0
 */
@Service
@Transactional
public class AccountBatchTransaction {

    /**
     * 账务系统批处理Mapper
     */
    @Autowired
    public AccountBatchProcesMapper accountBatchProcesMapper;
    
    /**
     * 封装或者获取数据方法
     */
    @Autowired
    private SetAndGetDataMethod setAndGetDataMethod;

    /**
     * 账务系统记账查账Mapper
     */
    @Autowired
    public AccountEntryMapper accountEntryMapper;

    @Autowired
    public AccountBalanceMapper accountBalanceMapper;

    @Autowired
    public AccountCustTypeMapper accountCustTypeMapper;

    /**
     * 解析记账行数据到记账实体类AccountEntry
     * 
     * @param lineTxt
     *            记账行数据
     * @return 记账实体类AccountEntry
     */
    public AccountEntry getAccountEntry(String lineTxt, AccountBatchJob accountBatchJob) {
        AccountEntry accountEntry = new AccountEntry();
        String[] lines = lineTxt.split("\\|");
        for (int i = 0; i < lines.length; i++)
        {
            // 批记账任务名称
            accountEntry.setBatchJobName(accountBatchJob.getBatchJobName());

            // 批记账任务日期
            accountEntry.setBatchDate(accountBatchJob.getBatchDate());

            // 客户全局编号
            accountEntry.setCustID("null".equals(lines[0].trim())?null:lines[0].trim());

            // 互生号
            accountEntry.setHsResNo("null".equals(lines[1].trim())?null:lines[1].trim());

            // 客户类型
            accountEntry.setCustType(Integer.valueOf("null".equals(lines[2].trim())?null:lines[2].trim()));

            // 批次号
            accountEntry.setBatchNo("null".equals(lines[3].trim())?null:lines[3].trim());

            // 账户类型编码
            accountEntry.setAccType("null".equals(lines[4].trim())?null:lines[4].trim());

            String addAmount = "null".equals(lines[5].trim())?"0":lines[5].trim();
            if(addAmount == null || addAmount.length() == 0)
            {
            	addAmount = "0";
            }
            // 增向金额
            accountEntry.setAddAmount(addAmount);

            
            String subAmount = "null".equals(lines[6].trim())?"0":lines[6].trim();
            if(subAmount == null || subAmount.length() == 0)
            {
            	subAmount = "0";
            }
            // 减向金额
            accountEntry.setSubAmount(subAmount);

            // 红冲标识
            accountEntry.setWriteBack(Integer.valueOf("null".equals(lines[7].trim())?null:lines[7].trim()));

            // 交易系统
            accountEntry.setTransSys("null".equals(lines[8].trim())?null:lines[8].trim());

            // 交易类型
            accountEntry.setTransType("null".equals(lines[9].trim())?null:lines[9].trim());

            // 交易流水号
            accountEntry.setTransNo("null".equals(lines[10].trim())?null:lines[10].trim());

            // 交易时间
            accountEntry.setTransDateTim(Timestamp.valueOf("null".equals(lines[11].trim())?null:lines[11].trim()));

            // 会计时间
            accountEntry.setFiscalDateTim(Timestamp.valueOf("null".equals(lines[12].trim())?null:lines[12].trim()));

            // 关联交易类型
            accountEntry.setRelTransType("null".equals(lines[13].trim())?null:lines[13].trim());

            // 关联交易流水号
            accountEntry.setRelTransNo("null".equals(lines[14].trim())?null:lines[14].trim());

            // 备注
            accountEntry.setRemark("null".equals(lines[15].trim())?null:lines[15].trim());
            
            //各系统分录序号
            accountEntry.setSysEntryNo("null".equals(lines[16].trim())?null:lines[16].trim());
            
            //关联各系统分录序号
            accountEntry.setRelSysEntryNo("null".equals(lines[17].trim())?null:lines[17].trim());
            
            //获取原始交易流水号
        	String sourceTransNo = null;
        	if(lines.length>=19)
        	{
        		sourceTransNo = "null".equals(lines[18].trim())?null:lines[18].trim();
        	}
        	
        	//原始交易流水号没有就储存交易流水号
        	if(sourceTransNo==null||"".equals(sourceTransNo))
        	{
        		accountEntry.setSourceTransNo(accountEntry.getTransNo());
        	}
            
        }
        return accountEntry;
    }

    /**
     * 记账校验
     * 
     * @param accountEntry
     *            记账分录信息
     * @param accountBalanceMap
     *            同一账户余额更新汇总
     * @param accBalanceAll
     *            同一批记账任务账户余额共享，使所有的子线程共享同一余额变动，减少对数据库的查询，减少消耗
     * @param custAccTypeMap
     *            同一批记账任务账户与客户类型关系共享，减少对数据库的查询，减少消耗
     * @return 校验异常与快照信息
     */
    public Map<String, Object> checkAccountEntry(AccountEntry accountEntry,
            Map<String, AccountBalance> accountBalanceMap, Map<String, AccountBalance> accBalanceAll,
            Map<String, AccountCustType> custAccTypeMap,boolean flag) {
        
        Map<String, Object> outMap = new HashMap<String, Object>();

        try
        {
            // 校验记账对象字段是否存在非空项
            checkAccountEntryInfo(accountEntry);

            // 检验账户余额记录信息是否存在、当前余额账户状态和该分录的变化金额是否在账户和客户关系中最大值和最小值之间
            AccountBalanceSnap accountBalanceSnap = checkAccountEntryAmountInfo(accountEntry, accountBalanceMap,
                    accBalanceAll, custAccTypeMap,flag);
            outMap.put("accountBalanceSnap", accountBalanceSnap);
        }
        catch (HsException e)
        {
            // 校验错误抛出
            outMap.put("ERROR", e);
            return outMap;
        }
        return outMap;
    }

    /**
     * 批处理任务报错记录
     * 
     * @param accountBatchJob
     *            任务内容
     * @param hsException
     *            校验错误信息
     */
    public AccountEntry getBatchError(AccountBatchJob accountBatchJob, AccountEntry accountEntry,
            HsException hsException) {
        
        //错误截取长度
        int errorSize = 180;
        
        // 批记账任务名称
        accountEntry.setBatchJobName(accountBatchJob.getBatchJobName());
        
        // 批记账任务日期
        accountEntry.setBatchDate(accountBatchJob.getBatchDate());
        
        // 记账文件地址
        accountEntry.setErrorFileAddress(accountBatchJob.getBatchFileAddress());
        
        //判断错误信息是否超过截取长度，超过就截取
        if (hsException.getMessage().length() > errorSize)
        {
            accountEntry.setErrorDescription(hsException.getMessage().substring(0, errorSize));
        }
        else
        {
            accountEntry.setErrorDescription(hsException.getMessage());
        }
        return accountEntry;
    }

    /**
     * 新增批处理任务报错记录
     * 
     * @param errorEntry
     * @throws SQLException
     */
    public void addBatchError(AccountEntry errorEntry) throws SQLException {
        accountBatchProcesMapper.addBatchError(errorEntry);
    }

    /**
     * 开户或者更新账户余额信息
     * 
     * @param accountEntry
     * @param accountBalanceMap
     * @throws HsException
     */
    public int addOrUpdateAccountBalanceInfo(AccountBalance accountBalance) throws SQLException {
        //查询账户信息
        AccountBalance accountBalancecheck = accountBalanceMapper.searchAccountBalanceInfo(accountBalance.getCustID(),
                accountBalance.getAccType());
        Map<Integer, Integer> perMap = setAndGetDataMethod.getPerTypes();// 获取消费者客户类型
        Map<Integer, Integer> entMap = setAndGetDataMethod.getEntTypes();// 获取企业者客户类型
        Map<Integer, Integer> pfMap = setAndGetDataMethod.getPfTypes();// 获取平台客户类型
        Integer custTypeCategory = null;// 客户类型分类
        Integer custType = accountBalance.getCustType();// 客户类型
        int rowCount = 1;
        // 消费者客户类型
        if (perMap.get(custType) != null)
        {
            custTypeCategory = AcConstant.CUST_TYPE_CATEGORY[0];
        }

        // 企业客户类型
        if (entMap.get(custType) != null)
        {
            custTypeCategory = AcConstant.CUST_TYPE_CATEGORY[1];
        }

        // 平台客户类型
        if (pfMap.get(custType) != null)
        {
        	custTypeCategory = AcConstant.CUST_TYPE_CATEGORY[2];
        }
        accountBalance.setCustTypeCategory(custTypeCategory);
        
        // 如果不存在记录，则可以进行开户
        if (accountBalancecheck == null)
        {
            // 新增账户余额信息（开户）
            accountBalanceMapper.addAccountBalance(accountBalance);
        }
        else
        {
            // 更新账户余额表金额
        	rowCount = accountBalanceMapper.updateAccountBalance(accountBalance);
        }
        return rowCount;
    }

    /**
     * 封装accountBalanceSnap对象的信息
     * 
     * @param accountBalanceSnap
     *            （ 余额快照表 对象）
     * @param accountEntry
     * @param accountBalanceMap
     * @param entryNo
     */
    public void setAccountBalanceSnapInfo(AccountBalanceSnap accountBalanceSnap, AccountEntry accountEntry,
            Map<String, AccountBalance> accountBalanceMap, String entryNo) {
        
        AccountBalance accountBalance = accountBalanceMap.get(accountEntry.getCustID() + accountEntry.getAccType());
        
        // 增向金额
        String addAmt = accountEntry.getAddAmount();
        if (addAmt == null || "".equals(addAmt))
        {
            addAmt = "0";
        }
        BigDecimal addAmount = new BigDecimal(addAmt);
        
        // 减向金额
        String subAmt = accountEntry.getSubAmount();
        if (subAmt == null || "".equals(subAmt))
        {
            subAmt = "0";
        }

        BigDecimal subAmount = new BigDecimal(subAmt);
        BigDecimal amount = addAmount.subtract(subAmount);
        accountBalanceSnap.setCustID(accountEntry.getCustID());
        accountBalanceSnap.setAccType(accountEntry.getAccType());
        accountBalanceSnap.setCustType(accountEntry.getCustType());
        accountBalanceSnap.setHsResNo(accountEntry.getHsResNo());
        BigDecimal accBalance = new BigDecimal(accountBalance.getAccBalance());
        accountBalanceSnap.setAccBalanceOld(String.valueOf(accBalance.subtract(amount)));
        accountBalanceSnap.setAccBalanceNew(accountBalance.getAccBalance());
        accountBalanceSnap.setCreatedDate(accountBalance.getCreatedDate());
        accountBalanceSnap.setChangeType(2);
        accountBalanceSnap.setChangeRecordID(entryNo);
        accountBalanceSnap.setSnapDate(accountBalance.getUpdatedDate());
    }

    /**
     * 校验AccountEntry对象及对象中的条件
     * 
     * @param accountEntry
     *            (分录对象) correctFlag 是否冲正红冲标识(false 非冲正红冲，true 冲正红冲)
     * @throws HsException
     *             异常处理类
     */
    public void checkAccountEntryInfo(AccountEntry accountEntry) throws HsException {
        // 校验对象是否为空
        if (accountEntry == null)
        {
            throw new HsException(RespCode.AC_PARAMETER_NULL.getCode(), "对象为空");
        }
        
        // 账户类型编码
        String accType = accountEntry.getAccType();
        
        // 客户全局编码
        String custID = accountEntry.getCustID();
        
        // 客户类型
        Integer custType = accountEntry.getCustType();
        
        // 增向金额
        String addAmt = accountEntry.getAddAmount();
        if (addAmt == null || "".equals(addAmt))
        {
            addAmt = "0";
        }
        BigDecimal addAmount = new BigDecimal(addAmt);
        
        // 减向金额
        String subAmt = accountEntry.getSubAmount();
        if (subAmt == null || "".equals(subAmt))
        {
            subAmt = "0";
        }
        BigDecimal subAmount = new BigDecimal(subAmt);
        
        // 红冲标识
        Integer writeBack = accountEntry.getWriteBack();
        
        // 交易流水号
        String transNo = accountEntry.getTransNo();
        
        // 交易类型
        String transType = accountEntry.getTransType();
        
        // 批次号
        String batchNo = accountEntry.getBatchNo();
        if (custID == null || "".equals(custID))
        {
            throw new HsException(RespCode.AC_PARAMETER_NULL.getCode(), "客户全局编码为空");
        }
        
        if (accType == null || "".equals(accType))
        {
            throw new HsException(RespCode.AC_PARAMETER_NULL.getCode(), "账户类型编码为空");
        }
        
        if (batchNo == null || "".equals(batchNo))
        {
            throw new HsException(RespCode.AC_PARAMETER_NULL.getCode(), "批次号为空");
        }
        
        if (custType == null)
        {
            throw new HsException(RespCode.AC_PARAMETER_NULL.getCode(), "客户类型编码为空");
        }
        
        if (transNo == null || "".equals(transNo))
        {
            throw new HsException(RespCode.AC_PARAMETER_NULL.getCode(), "交易流水号为空");
        }
        
        if (transType == null || "".equals(transType))
        {
            throw new HsException(RespCode.AC_PARAMETER_NULL.getCode(), "交易类型为空");
        }
        
        if (addAmount.compareTo(BigDecimal.valueOf(0)) == 0 && subAmount.compareTo(BigDecimal.valueOf(0)) == 0)
        {
            throw new HsException(RespCode.AC_PARAMETER_NULL.getCode(), "分录记账金额为空");
        }
        
//        if (addAmount.compareTo(BigDecimal.valueOf(0)) == 1 && subAmount.compareTo(BigDecimal.valueOf(0)) == 1)
//        {
//            throw new HsException(RespCode.AC_AMOUNT_NEGATIVE.getCode(), "记账金额不能同时有增向金额（非0）和减向金额（非0）");
//        }
        
        if (addAmount != null)
        {
            if (writeBack == 0)
            {
                if (addAmount.compareTo(BigDecimal.valueOf(0)) == -1)
                {
                    throw new HsException(RespCode.AC_AMOUNT_NEGATIVE.getCode(), "记账增向金额为负");
                }
            }
            else
            {
                if (addAmount.compareTo(BigDecimal.valueOf(0)) == 1)
                {
                    throw new HsException(RespCode.AC_AMOUNT_POSITIVE.getCode(), "红冲记账增向金额为正");
                }

            }
        }
    }

    /**
     * 检验账户余额记录信息是否存在、当前余额账户状态和该分录的变化金额是否在账户和客户关系中最大值和最小值之间
     * 
     * @param accountEntry
     *            记账分录信息
     * @param accountBalanceMap
     *            同一账户余额更新汇总
     * @param accBalanceAll
     *            同一批记账任务账户余额共享，使所有的子线程共享同一余额变动，减少对数据库的查询，减少消耗
     * @param custAccTypeMap
     *            同一批记账任务账户与客户类型关系共享，减少对数据库的查询，减少消耗
     * @return 校验异常与快照信息
     * @throws HsException
     *             数据库异常
     */
    public AccountBalanceSnap checkAccountEntryAmountInfo(AccountEntry accountEntry,
            Map<String, AccountBalance> accountBalanceMap, Map<String, AccountBalance> accBalanceAll,
            Map<String, AccountCustType> custAccTypeMap,boolean flag) throws HsException {
        try
        {
            // 增向金额
            String addAmt = accountEntry.getAddAmount();
            if (addAmt == null || "".equals(addAmt))
            {
                addAmt = "0";
            }
            BigDecimal addAmount = new BigDecimal(addAmt);
            
            // 减向金额
            String subAmt = accountEntry.getSubAmount();
            if (subAmt == null || "".equals(subAmt))
            {
                subAmt = "0";
            }
            BigDecimal subAmount = new BigDecimal(subAmt);
            
            // 账户类型编码
            String accType = accountEntry.getAccType();
            
            // 客户全局编码
            String custID = accountEntry.getCustID();
            
            // 客户类型
            Integer custType = accountEntry.getCustType();
            String hsResNo = accountEntry.getHsResNo();
            // 当前时间
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());

            //获取分录的流水号前缀
            String preEntryNo = setAndGetDataMethod.getPreEntryNo();
            // 当前插入分录的序列值
            String entryNo = GuidAgent.getStringGuid(preEntryNo);
            
            // 查找账户和客户类型关系信息
            AccountCustType custAccType = custAccTypeMap.get(custType + accType);
            if (custAccType == null)
            {
                custAccType = (AccountCustType) accountCustTypeMapper.searchCustAccType(custType, accType);
                custAccTypeMap.put(custType + accType, custAccType);
            }
            
            // 批处理统一账户余额变动记录
            AccountBalance accountBalance = accBalanceAll.get(custID + accType);
            if (accountBalance == null)
            {
                accountBalance = (AccountBalance) accountBalanceMapper.searchAccountBalance(custID, accType, custType);
                if (accountBalance == null)
                {
                    // 批处理统一账户余额变动记录
                    accountBalance = new AccountBalance();
                    accountBalance.setCustID(custID);
                    accountBalance.setCustType(custType);
                    accountBalance.setAccType(accType);
                    accountBalance.setAccBalance("0");
                    accountBalance.setAccStatus(0);
                    accountBalance.setHsResNo(hsResNo);
                    accountBalance.setCreatedDateTim(timestamp);
                    accountBalance.setUpdatedDateTim(timestamp);
                    if(flag){
                        accountBalance.setAccFlag(AcConstant.ACCOUNT_MINUS);
                    }
                    accBalanceAll.put(custID + accType, accountBalance);
                }
                else
                {
                    accountBalance.setUpdatedDateTim(timestamp);
                }
            }

            if (accountBalance != null)
            {
                int accStatus = accountBalance.getAccStatus();
                if (accStatus == 3 || accStatus == 4)
                {
                    throw new HsException(RespCode.AC_ENTRY_STAUS_ERROR.getCode(), "账户状态不允许变更金额");
                }
                if (addAmount.compareTo(BigDecimal.valueOf(0)) == 1 && accStatus == 2)
                {
                    throw new HsException(RespCode.AC_ENTRY_STAUS_ERROR.getCode(), "账户状态为许减不许增，进账金额不能为增向金额");
                }
                if (subAmount.compareTo(BigDecimal.valueOf(0)) == 1 && accStatus == 1)
                {
                    throw new HsException(RespCode.AC_ENTRY_STAUS_ERROR.getCode(), "账户状态为许增不许减，进账金额不能为减向金额");
                }
            }
            
            // 变动金额额
            BigDecimal amount = addAmount.subtract(subAmount);
            
            // 账户新余额值
            BigDecimal newAccBalance = amount.add(new BigDecimal(accountBalance.getAccBalance()));
            
            // 新余额在客户和账户关系表中最大值和最小值区间比较
            if (custAccType != null)
            {
                // 最小值
                String balMin = custAccType.getBalanceMin();
                BigDecimal balanceMin = null;
                if (balMin != null && !"".equals(balMin))
                {
                    balanceMin = new BigDecimal(balMin);
                }
                
                // 最大值
                String balMax = custAccType.getBalanceMax();
                BigDecimal balanceMax = null;
                if (balMax != null && !"".equals(balMax))
                {
                    balanceMax = new BigDecimal(balMax);
                }
                
                // 校验账户余额最大值
                if (balanceMax != null)
                {
                    if (balanceMax.compareTo(newAccBalance) == -1 && addAmount.compareTo(BigDecimal.valueOf(0)) == 1)
                    {
                        if(custType==CustType.PERSON.getCode() || custType==CustType.NOT_HS_PERSON.getCode())
                        {
                            SystemLog.debug("HSXT_AC", "方法：checkAccountEntryAmountInfo", RespCode.AC_BALANCE_DEFICIENCY
                                    .getCode()
                                    +custID+"对应的账号类型:"+ accType + "和客户类型:" + custType+",余额不足");
                            throw new HsException(RespCode.AC_BALANCE_DEFICIENCY.getCode(), custID+"对应的账号类型:"+ accType + "和客户类型:" + custType+",余额不足");
                        }
                        else
                        {
                            SystemLog.debug("HSXT_AC", "方法：checkAccountEntryAmountInfo", RespCode.AC_ENT_BALANCE_DEFICIENCY
                                    .getCode()
                                    +custID+"对应的账号类型:"+ accType + "和客户类型:" + custType+",余额不足");
                            throw new HsException(RespCode.AC_ENT_BALANCE_DEFICIENCY.getCode(), custID+"对应的账号类型:"+ accType + "和客户类型:" + custType+",余额不足");
                        }
                    }// end of if (balanceMax.compareTo(newAccBalance) ...)
                } // end of  if (balanceMax != null)
                
                //校验账户余额最小值
                if (balanceMin != null && !flag)
                {
                    if (balanceMin.compareTo(newAccBalance) == 1 && subAmount.compareTo(BigDecimal.valueOf(0)) == 1)
                    {
                        if(custType==CustType.PERSON.getCode() || custType==CustType.NOT_HS_PERSON.getCode())
                        {
                            SystemLog.debug("HSXT_AC", "方法：checkAccountEntryAmountInfo", RespCode.AC_BALANCE_DEFICIENCY
                                    .getCode()
                                    +custID+"对应的账号类型:"+ accType + "和客户类型:" + custType+",余额不足");
                            throw new HsException(RespCode.AC_BALANCE_DEFICIENCY.getCode(), custID+"对应的账号类型:"+ accType + "和客户类型:" + custType+",余额不足");
                        }
                        else
                        {
                            SystemLog.debug("HSXT_AC", "方法：checkAccountEntryAmountInfo", RespCode.AC_ENT_BALANCE_DEFICIENCY
                                    .getCode()
                                    +custID+"对应的账号类型:"+ accType + "和客户类型:" + custType+",余额不足");
                            throw new HsException(RespCode.AC_ENT_BALANCE_DEFICIENCY.getCode(), custID+"对应的账号类型:"+ accType + "和客户类型:" + custType+",余额不足");
                        }
                    }// end of if (balanceMin.compareTo(newAccBalance) ...)
                }//end of if (balanceMin != null)
            }//end of if (custAccType != null)

            accountBalance.setAccBalance(String.valueOf(newAccBalance));

            // 子线程处理需要更新的账户余额
            AccountBalance updateAccountBalance = accountBalanceMap.get(custID + accType);
            if (updateAccountBalance == null)
            {
                updateAccountBalance = new AccountBalance();
                updateAccountBalance.setCustID(custID);
                updateAccountBalance.setCustType(custType);
                updateAccountBalance.setAccType(accType);
                updateAccountBalance.setAccBalance(String.valueOf(amount));
                updateAccountBalance.setAccStatus(0);
                updateAccountBalance.setHsResNo(hsResNo);
                updateAccountBalance.setCreatedDateTim(timestamp);
                updateAccountBalance.setUpdatedDateTim(timestamp);
                // 账户是否可以为负数标识
                if(flag){
                    updateAccountBalance.setAccFlag(AcConstant.ACCOUNT_MINUS);
                }
                accountBalanceMap.put(custID + accType, updateAccountBalance);
            }
            else
            {
                // 同用户同账户的变动余额做累加
                BigDecimal accBalance = new BigDecimal(updateAccountBalance.getAccBalance());
                updateAccountBalance.setAccBalance(String.valueOf(amount.add(accBalance)));
            }
            // 余额快照信息
            AccountBalanceSnap accountBalanceSnap = new AccountBalanceSnap();

            Map<Integer, Integer> perMap = setAndGetDataMethod.getPerTypes();// 获取消费者客户类型
            Map<Integer, Integer> entMap = setAndGetDataMethod.getEntTypes();// 获取企业者客户类型
            Map<Integer, Integer> pfMap = setAndGetDataMethod.getPfTypes();// 获取平台客户类型
            Integer custTypeCategory = null;// 客户类型分类
            // 消费者客户类型
            if (perMap.get(custType) != null)
            {
                custTypeCategory = AcConstant.CUST_TYPE_CATEGORY[0];
            }

            // 企业客户类型
            if (entMap.get(custType) != null)
            {
                custTypeCategory = AcConstant.CUST_TYPE_CATEGORY[1];
            }

            // 平台客户类型
            if (pfMap.get(custType) != null)
            {
            	custTypeCategory = AcConstant.CUST_TYPE_CATEGORY[2];
            }
            
            accountBalanceSnap.setCustID(accountEntry.getCustID());
            accountBalanceSnap.setAccType(accountEntry.getAccType());
            accountBalanceSnap.setCustType(accountEntry.getCustType());
            accountBalanceSnap.setHsResNo(accountEntry.getHsResNo());
            BigDecimal accBalance = new BigDecimal(accountBalance.getAccBalance());
            accountBalanceSnap.setAccBalanceOld(String.valueOf(accBalance.subtract(amount)));
            accountBalanceSnap.setAccBalanceNew(accountBalance.getAccBalance());
            accountBalanceSnap.setCreatedDateTim(accountBalance.getCreatedDateTim());
            accountBalanceSnap.setUpdatedDateTim(accountBalance.getUpdatedDateTim());
            accountBalanceSnap.setChangeType(2);
            accountBalanceSnap.setSnapDateTim(accountBalance.getUpdatedDateTim());
            accountBalanceSnap.setChangeRecordID(entryNo);
            accountBalanceSnap.setAmount(String.valueOf(amount));
            accountBalanceSnap.setCustTypeCategory(custTypeCategory);

            accountEntry.setEntryNo(entryNo);

            return accountBalanceSnap;
        }
        catch (HsException e)
        {
        	throw e;
        }
        catch (Exception e)
        {
            throw new HsException(RespCode.AC_SQL_ERROR.getCode(), e.getMessage());
        }
    }

    /**
     * 记账数据插入更新处理
     * 
     * @param accountBatchJob
     *            批任务记录
     * @param accountEntryList
     *            记账数据List
     * @param accountBalanceSnapList
     *            快照数据List
     * @param accountBalanceMap
     *            余额更新集合
     * @param count
     *            发生数据库异常时的重试次数记录
     * @throws HsException
     */
    public StringBuilder addAccountEntry(AccountBatchJob accountBatchJob, List<AccountEntry> accountEntryList,
            List<AccountEntry> accountBatchError, List<AccountBalanceSnap> accountBalanceSnapList,
            Map<String, AccountBalance> accountBalanceMap, Map<String, String> custIdBytransNo,
            List<String> lineTxtList, List<String> lineExceTxtList) throws SQLException {

        // 任务记录完成数实时记录数据库好任务中断后继续执行
        accountBatchProcesMapper.updateBatchJob(accountBatchJob);
        
        // 返回的文件内容
        StringBuilder writeFileData = new StringBuilder();
        
        // 更新或新增账户余额
        for (Map.Entry<String, AccountBalance> entry : accountBalanceMap.entrySet())
        {
            int rowCount = addOrUpdateAccountBalanceInfo(entry.getValue());
            if(rowCount == 0)
            {
            	AccountBalance accountBalance = entry.getValue();
            	String transNo = custIdBytransNo.get(accountBalance.getCustID());
            	
            	//需要移除的记账信息
            	List<Integer> removeList = new ArrayList<Integer>();
            	
            	//循环获取更新失败的记账信息
            	for(int i=0; i<accountEntryList.size(); i++)
            	{
            		AccountEntry accountEntry = accountEntryList.get(i);
            		if(transNo.equals(accountEntry.getTransNo()))
            		{
            			removeList.add(i);
                		String lineTxt = lineTxtList.get(i);
                		accountBatchError.add(getBatchError(accountBatchJob,
                                accountEntry, new HsException(RespCode.AC_BALANCE_DEFICIENCY.getCode(), "余额不足！！！")));
                		lineExceTxtList.add(lineTxt+"|余额不足！！！|0");
            		}
            		
            	}
            	for(int y=removeList.size()-1; y<=0; y--)
            	{
            		int removeId = removeList.get(y).intValue();
            		accountEntryList.remove(removeId);
            		lineTxtList.remove(removeId);
            		accountBalanceSnapList.remove(removeId);
            	}
            }
        }
        
        //判断是否是否返回所有信息文件
        if (accountBatchJob.isReturnInforFile())
        {
	        for(int i=0; i<lineTxtList.size(); i++)
	        {
	        	String lineTxt = lineTxtList.get(i);
	        	writeFileData.append(lineTxt + "|1");
                writeFileData.append(IOUtils.LINE_SEPARATOR);
	        }
        }
        
        for(int i=0; i<lineExceTxtList.size(); i++)
        {
        	String lineExceTxt = lineExceTxtList.get(i);
        	writeFileData.append(lineExceTxt);
        	writeFileData.append(IOUtils.LINE_SEPARATOR);
        }
        
        // 余额快照记录
        if (accountBalanceSnapList.size() > 0)
        {
            accountBalanceMapper.addBatchAccountBalanceSnaps(accountBalanceSnapList);
        }
        // 多个插入记账分录
        if (accountEntryList.size() > 0)
        {
            accountEntryMapper.addAccountEntrys(accountEntryList);
        }
        // 多个插入批处理任务报错记录
        if (accountBatchError.size() > 0)
        {
            accountBatchProcesMapper.addBatchErrores(accountBatchError);
        }
        
        return writeFileData;
    }

    /**
     * 修改批处理任务记录
     * 
     * @param accountBatchJob
     *            任务记录数据参数
     */
    public void updateBatchJob(AccountBatchJob accountBatchJob) throws HsException {
        try
        {
            accountBatchProcesMapper.updateBatchJob(accountBatchJob);
        }
        catch (Exception e)
        {
            throw new HsException(RespCode.AC_SQL_ERROR.getCode(), e.getMessage());
        }
    }
    
    
}
