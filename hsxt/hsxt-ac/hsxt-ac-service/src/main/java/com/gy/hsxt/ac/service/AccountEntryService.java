package com.gy.hsxt.ac.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsi.lc.client.log4j.BizLog;
import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.ac.api.IAccountEntryService;
import com.gy.hsxt.ac.bean.AccountBalance;
import com.gy.hsxt.ac.bean.AccountBalanceSnap;
import com.gy.hsxt.ac.bean.AccountCustType;
import com.gy.hsxt.ac.bean.AccountEntry;
import com.gy.hsxt.ac.bean.AccountService;
import com.gy.hsxt.ac.bean.AccountServiceInfo;
import com.gy.hsxt.ac.bean.AccountWriteBack;
import com.gy.hsxt.ac.common.bean.AcConstant;
import com.gy.hsxt.ac.common.bean.SetAndGetDataMethod;
import com.gy.hsxt.ac.common.bean.ValidateInfo;
import com.gy.hsxt.ac.mapper.AccountCustTypeMapper;
import com.gy.hsxt.ac.mapper.AccountEntryMapper;
import com.gy.hsxt.ac.service.Transaction.AccountEntryTransaction;
import com.gy.hsxt.common.constant.CustType;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.guid.GuidAgent;
import com.gy.hsxt.uc.as.api.ent.IUCAsEntService;
import com.gy.hsxt.uc.as.bean.ent.AsEntInfo;

/**
 * 账户分录业务实现类
 * 
 * @Project Name : hsxt-ac-service
 * @Package Name : com.gy.hsxt.ac.service
 * @File Name : AccountEntryService.java
 * @Author : weixz
 * @Description : TODO
 * @Creation Date : 2015-8-26
 * @version V1.0
 * 
 */
@Service
public class AccountEntryService implements IAccountEntryService {

    /** 账务分录的接口服务 */
    @Autowired
    AccountEntryMapper accountEntryMapper;

    /** 账户和客户类型关系的接口服务 */
    @Autowired
    AccountCustTypeMapper accountCustTypeMapper;

    /**
     * 记账分录统一事务Service
     */
    @Autowired
    private AccountEntryTransaction accountEntryTransaction;
    
    @Autowired
	private IUCAsEntService entService;

    /**
     * 校验方法
     */
    @Autowired
    private ValidateInfo validateInfo;

    /**
     * 封装或者获取数据方法
     */
    @Autowired
    private SetAndGetDataMethod setAndGetDataMethod;

    /**
     * 实时记账
     * 
     * @param accountEntryList
     *            分录对象集合
     * @throws HsException
     *             异常处理类
     */
    @Override
    public void actualAccount(List<AccountEntry> accountEntryList) throws HsException {
        BizLog.biz("HSXT_AC", "方法：actualAccount", "accountEntryList", "实时记账");
        if (accountEntryList.isEmpty())
        {
            SystemLog.debug("HSXT_AC", "方法：actualAccount", RespCode.AC_PARAMETER_NULL.getCode()
                    + "参数accountEntryList：集合为空");
            throw new HsException(RespCode.AC_PARAMETER_NULL.getCode(), "参数accountEntryList：集合为空");
        }
        List<AccountBalance> openAccBalanceList = new ArrayList<AccountBalance>();// 封装账户余额对象集合(开户)
        List<AccountBalance> updateAccBalanceList = new ArrayList<AccountBalance>();// 封装账户余额对象集合(更新)
        List<AccountBalanceSnap> accountBalanceSnapList = new ArrayList<AccountBalanceSnap>();// 封装账户快照对象集合
        Map<String, BigDecimal> accBalanceMap = new HashMap<String, BigDecimal>();// 相同客户ID和账户类型余额最新值
        Map<Integer, Integer> perMap = setAndGetDataMethod.getPerTypes();// 获取消费者客户类型
        Map<Integer, Integer> entMap = setAndGetDataMethod.getEntTypes();// 获取企业者客户类型
        Map<Integer, Integer> pfMap = setAndGetDataMethod.getPfTypes();// 获取平台客户类型
        Map<String, String> accTypeMap = setAndGetDataMethod.getSpecialAccTypes();// 只记分录，不做余额变化的账户
        String preEntryNo = setAndGetDataMethod.getPreEntryNo();//获取分录的流水号前缀
        
        try
        {
            for (AccountEntry accountEntry : accountEntryList)
            {
            	//获取原始交易流水号
            	String sourceTransNo = accountEntry.getSourceTransNo();
            	
            	//原始交易流水号没有就储存交易流水号
            	if(sourceTransNo==null||"".equals(sourceTransNo))
            	{
            		accountEntry.setSourceTransNo(accountEntry.getTransNo());
            	}
            	
                // 校验AccountEntry对象及对象中的条件
                validateInfo.checkAccountEntryInfo(accountEntry, false);

                // 检验账户余额记录信息是否存在和该分录的变化金额是否在账户和客户关系中最大值和最小值之间
                Map<String, Object> accountBalanceMap = validateInfo.checkAccountEntryAmountInfo(accBalanceMap,
                        accountEntry, false);

                // 判断当前客户类型所属哪个客户分类（0：消费者，1：企业，2：平台）
                Integer custTypeCategory = validateInfo.checkCustTypesCategory(accountEntry, perMap, entMap, pfMap);

                // 当前插入分录的序列值
                String entryNo = GuidAgent.getStringGuid(preEntryNo);
                accountEntry.setEntryNo(entryNo);
                
                // 判断是否只是做记账分录的账户
                String accType = accountEntry.getAccType();
                if(accTypeMap.get(accType) == null){
                    // 开户或者更新账户余额信息
                    setAndGetDataMethod.addOrUpdateAccountBalanceInfo(openAccBalanceList, updateAccBalanceList,
                            accountEntry, accountBalanceMap, custTypeCategory);
                    accountBalanceMap.put("custTypeCategory", custTypeCategory);
                    // 余额快照信息对象
                    AccountBalanceSnap accountBalanceSnap = new AccountBalanceSnap();
    
                    // 封装accountBalanceSnap（余额快照表）对象的信息
                    setAndGetDataMethod.setAccountBalanceSnapInfo(accountBalanceSnap, accountEntry, accountBalanceMap,
                            entryNo);
                    accountBalanceSnapList.add(accountBalanceSnap);
                }
            }
            // 记账分录统一事务进行实时记账
            accountEntryTransaction.actualAccount(openAccBalanceList, updateAccBalanceList, accountBalanceSnapList,
                    accountEntryList);
        }
        catch (HsException e)
        {
            SystemLog.error("HSXT_AC", "方法：actualAccount", e.getErrorCode() + e.getMessage(),e);
            throw e;
        }
        catch (Exception e)
        {
            SystemLog.error("HSXT_AC", "方法：actualAccount", RespCode.AC_SQL_ERROR.getCode() + e.getMessage(),e);
            throw new HsException(RespCode.AC_SQL_ERROR.getCode(), e.getMessage());
        }
    }

    /**
     * 扣减多账户余额
     * 
     * @param accountEntryList
     *            扣款分录信息
     * @throws HsException
     *             异常处理
     * @see com.gy.hsxt.ac.api.IAccountEntryService#deductAccount(java.util.List)
     */
    @Override
    public void deductAccount(List<AccountEntry> accountEntryList) throws HsException {
        BizLog.biz("HSXT_AC", "方法：deductAccount", "accountEntryList", "扣减账户余额");
        if (accountEntryList.isEmpty())
        {
            SystemLog.debug("HSXT_AC", "方法：deductAccount", RespCode.AC_PARAMETER_NULL.getCode()
                    + "参数accountEntryList：集合为空");
            throw new HsException(RespCode.AC_PARAMETER_NULL.getCode(), "参数accountEntryList：集合为空");
        }
        List<AccountBalance> openAccBalanceList = new ArrayList<AccountBalance>();// 封装账户余额对象集合(开户)
        List<AccountBalance> updateAccBalanceList = new ArrayList<AccountBalance>();// 封装账户余额对象集合(更新)
        List<AccountBalanceSnap> accountBalanceSnapList = new ArrayList<AccountBalanceSnap>();// 封装账户快照对象集合
        List<AccountEntry> acctEntryList = new ArrayList<AccountEntry>();// 封装扣款账务分录集合
        Map<String, BigDecimal> accBalanceMap = new HashMap<String, BigDecimal>();// 相同客户ID和账户类型余额最新值
        Map<Integer, Integer> perMap = setAndGetDataMethod.getPerTypes();// 获取消费者客户类型
        Map<Integer, Integer> entMap = setAndGetDataMethod.getEntTypes();// 获取企业者客户类型
        Map<Integer, Integer> pfMap = setAndGetDataMethod.getPfTypes();// 获取平台客户类型
        Map<String, String> accTypeMap = setAndGetDataMethod.getSpecialAccTypes();// 只记分录，不做余额变化的账户

        try
        {
            for (AccountEntry accountEntry : accountEntryList)
            {
            	//获取账户余额是否可以为正负（1：正；2：负）
            	String positiveNegative = accountEntry.getPositiveNegative();
            	
            	//获取原始交易流水号
            	String sourceTransNo = accountEntry.getSourceTransNo();
            	
            	//原始交易流水号没有就储存交易流水号
            	if(sourceTransNo==null||"".equals(sourceTransNo))
            	{
            		accountEntry.setSourceTransNo(accountEntry.getTransNo());
            	}
            	
                String custId = accountEntry.getCustID();// 客户全局编码
                // 判断当前客户类型所属哪个客户分类（0：消费者，1：企业，2：平台）
                Integer custType = accountEntry.getCustType();// 客户类型
                Integer custTypeCategory = validateInfo.checkCustTypesCategory(accountEntry, perMap, entMap, pfMap);

                // 扣款账务分录的扣款账户集，账户以"|"分割，扣款账户顺序由左往右
                String accTypes = accountEntry.getAccTypes();
                if (accTypes == null || "".equals(accTypes))
                {
                    SystemLog.debug("HSXT_AC", "方法：deductAccount", RespCode.AC_PARAMETER_NULL.getCode() + "accTypes:"
                            + accTypes + ",扣款账务分录的扣款账户集不能为空");
                    throw new HsException(RespCode.AC_PARAMETER_NULL.getCode(), "accTypes:" + accTypes
                            + ",扣款账务分录的扣款账户集不能为空");
                }
                String accType[] = accTypes.split("\\|");// 以分隔符"|"拆分扣款账户字符串集

                // 扣款类型
                Integer deductType = accountEntry.getDeductType();
                if (deductType == null)
                {
                    deductType = AcConstant.ONLY_OR_COMBILE_ACCT_DEDUCT_SEQ;// 默认为第二种扣款方式
                }

                // 有多个账户,只能是减向扣款记账，扣款顺序以扣款账户从左往右
                if (accType.length > 1)
                {
                    String subAmt = accountEntry.getSubAmount();// 扣款账务分录的减向金额
                    if (subAmt == null || "".equals(subAmt) || "null".equals(subAmt))
                    {
                        SystemLog.debug("HSXT_AC", "方法：deductAccount", RespCode.AC_PARAMETER_NULL.getCode()
                                + "多扣款账户的扣款账务分录的减向金额不能为空");
                        throw new HsException(RespCode.AC_PARAMETER_NULL.getCode(), "多扣款账户的扣款账务分录的减向金额不能为空");
                    }
                    // 减向金额String 转 BigDecimal
                    BigDecimal subAmount = new BigDecimal(subAmt);

                    for (int i = 0; i < accType.length; i++)
                    {
                        accountEntry.setAccType(accType[i]);// 扣款分录的账户分录

                        // 校验AccountEntry对象及对象中的条件，false:正常分录的条件验证
                        validateInfo.checkAccountEntryInfo(accountEntry, false);

                        // 扣款账户余额的信息验证（是否存在账户，当前账户状态的验证）
                        AccountBalance accountBalance = validateInfo.checkDeductBalanceInfo(custId, accType[i], custType);

                        // 当前账户余额
                        BigDecimal accBalance = BigDecimal.valueOf(0);
                        
                        // 查找账户和客户类型关系信息
                        AccountCustType accountCustType = (AccountCustType) accountCustTypeMapper.searchCustAccType(custType, accType[i]);
                        
                        BigDecimal balanceMin = null;
                        
                        if(accountCustType != null)
                        {
                        	balanceMin = new BigDecimal(accountCustType.getBalanceMin()!=null?accountCustType.getBalanceMin():"0");
                        }

                        if (accountBalance != null)
                        {
                            accBalance = new BigDecimal(accountBalance.getAccBalance());
                        }
                        else
                        {
                        	accountBalance = new AccountBalance();
                        	accountBalance.setCustID(custId);
                        	accountBalance.setAccType(accType[i]);
                        	accountBalance.setCustType(custType);
                        	accountBalance.setAccStatus(0);
                        }

                        if (accBalance.compareTo(balanceMin) == 0 && i == accType.length - 1)
                        {
//                            SystemLog.debug("HSXT_AC", "方法：deductAccount", RespCode.AC_PARAMETER_NULL.getCode()
//                                    + custId+"对应的账号类型:"+ accType[i] + "和客户类型:" + custType+"余额不足");
//                            throw new HsException(RespCode.AC_PARAMETER_NULL.getCode(), custId+"对应的账号类型:"+ accType[i] + "和客户类型:" + custType+"余额不足");
                            int code = setAndGetDataMethod.getResCodeByAccType(accType[i],custTypeCategory);
                            if(custType==CustType.PERSON.getCode() || custType==CustType.NOT_HS_PERSON.getCode())
                            {
                               
                                SystemLog.debug("HSXT_AC", "方法：checkAccountEntryAmountInfo",code 
                                        +"客户号："+custId+"对应的账号类型:"+ accType + "和客户类型:" + custType+",余额不足");
                                throw new HsException(code, custId+"对应的账号类型:"+ accType + "和客户类型:" + custType+",余额不足");
                            }
                            else
                            {
                                SystemLog.debug("HSXT_AC", "方法：checkAccountEntryAmountInfo", code
                                        +"客户号："+custId+"对应的账号类型:"+ accType + "和客户类型:" + custType+",余额不足");
                                throw new HsException(code, custId+"对应的账号类型:"+ accType + "和客户类型:" + custType+",余额不足");
                            }
                        }
                        else if (accBalance.compareTo(balanceMin) == 0 && i != accType.length - 1)
                        {
                            continue;
                        }
                        // 相同客户ID和账户类型余额最新值（null则为当前余额值，不空则为当前账户余额最新值）
                        String key = custId + accType[i];
                        if (accBalanceMap.get(key) == null)
                        {
                            accBalanceMap.put(key, accBalance);
                        }
                        else
                        {
                            accBalance = accBalanceMap.get(key);// 当前账户余额最新值
                        }
                        accBalance = accBalance.subtract(balanceMin);

                        // 扣款账务分录减向金额小于0或等于0
                        if (subAmount.compareTo(BigDecimal.valueOf(0)) == -1
                                || subAmount.compareTo(BigDecimal.valueOf(0)) == 0)
                        {
                            SystemLog.debug("HSXT_AC", "方法：deductAccount", RespCode.AC_PARAMETER_NULL.getCode()
                                    + "多扣款账户的扣款账务分录的减向金额不能小于0或等于0");
                            throw new HsException(RespCode.AC_PARAMETER_NULL.getCode(), "多扣款账户的扣款账务分录的减向金额不能小于0或等于0");
                        }

                        // 剩余的扣款金额还大于最后一个账户的账户余额
                        if (subAmount.compareTo(accBalance) == 1 && i == accType.length - 1)
                        {
//                            SystemLog.debug("HSXT_AC", "方法：deductAccount", RespCode.AC_PARAMETER_NULL.getCode()
//                                    + custId+"对应的账号类型:"+ accType[i] + "和客户类型:" + custType+"余额不足");
//                            throw new HsException(RespCode.AC_PARAMETER_NULL.getCode(), custId+"对应的账号类型:"+ accType[i] + "和客户类型:" + custType+"余额不足");
                            int code = setAndGetDataMethod.getResCodeByAccType(accType[i],custTypeCategory);
                            if(custType==CustType.PERSON.getCode() || custType==CustType.NOT_HS_PERSON.getCode())
                            {
                                SystemLog.debug("HSXT_AC", "方法：checkAccountEntryAmountInfo", code
                                        +"客户号："+custId+"对应的账号类型:"+ accType + "和客户类型:" + custType+",余额不足");
                                throw new HsException(code, custId+"对应的账号类型:"+ accType + "和客户类型:" + custType+",余额不足");
                            }
                            else
                            {
                                SystemLog.debug("HSXT_AC", "方法：checkAccountEntryAmountInfo", code
                                        +"客户号："+custId+"对应的账号类型:"+ accType + "和客户类型:" + custType+",余额不足");
                                throw new HsException(code, custId+"对应的账号类型:"+ accType + "和客户类型:" + custType+",余额不足");
                            }
                        }

                        // 扣款金额大于当前账户余额，并且不是最后一个扣款账户
                        else if (subAmount.compareTo(accBalance) == 1 && i != accType.length - 1)
                        {
                            // 如果扣款类型为 1：按顺序检查账户余额，每次只能扣减一个账户，直到有能够扣款的账户满足
                            if (deductType.intValue() == AcConstant.ONLY_ONE_ACCT_DEDUCT_SEQ.intValue())
                            {
                                continue;
                            }

                            // 扣款后的账户余额和账户关系表中最小值比较
                            validateInfo.checkCustAccTypeInfo(custId, custType, accType[i], balanceMin, positiveNegative);
                            
                            // 剩余的扣款金额（扣款金额减去当前账户余额）
                            subAmount = subAmount.subtract(accBalance);
                            accBalanceMap.put(key, balanceMin);// 当前账户（custId和accType组成的KEY）余额已经为0

                            // 封装数据
                            setAndGetDataMethod.setDataInfo(acctEntryList, accountEntry, updateAccBalanceList,
                                    accountBalance, accBalance, accBalance, balanceMin, custTypeCategory,
                                    accountBalanceSnapList, accTypeMap, true);
                            continue;
                        }

                        // 扣款金额小于或等于当前账户余额
                        else if (subAmount.compareTo(accBalance) == -1 || subAmount.compareTo(accBalance) == 0)
                        {
                            // 扣款后的账户余额
                            BigDecimal newAccBalance = accBalance.subtract(subAmount);

                            // 扣款后的账户余额和账户关系表中最小值比较
                            validateInfo.checkCustAccTypeInfo(custId, custType, accType[i], newAccBalance, positiveNegative);

                            // 当前账户（custId + accType）剩余余额（账户余额-扣款金额）
                            accBalanceMap.put(key, newAccBalance);

                            setAndGetDataMethod.setDataInfo(acctEntryList, accountEntry, updateAccBalanceList,
                                    accountBalance, subAmount, accBalance, newAccBalance, custTypeCategory,
                                    accountBalanceSnapList, accTypeMap, true);
                            break;
                        }

                    }
                }

                // 只有一个账户,可能是增向记账也有可能是减向记账
                else
                {
                    accountEntry.setAccType(accType[0]);// 扣款分录的账户分录

                    // 封装数据
                    setAndGetDataMethod.setEntryDataInfo(acctEntryList, accountEntry, accBalanceMap,
                            openAccBalanceList, updateAccBalanceList, custTypeCategory, accountBalanceSnapList,
                            accTypeMap);
                }
            }

            // 扣减账户余额统一事务进行新增扣款分录，更新账户余额，新增余额快照记录
            accountEntryTransaction.deductAccount(openAccBalanceList, updateAccBalanceList, accountBalanceSnapList,
                    acctEntryList);
        }
        catch (HsException e)
        {
            SystemLog.error("HSXT_AC", "方法：deductAccount", e.getErrorCode() + e.getMessage(),e);
            throw e;
        }
        catch (Exception e)
        {
            SystemLog.error("HSXT_AC", "方法：deductAccount", RespCode.AC_SQL_ERROR.getCode() + e.getMessage(),e);
            throw new HsException(RespCode.AC_SQL_ERROR.getCode(), e.getMessage());
        }
    }

    /**
     * 冲正红冲记账
     * 
     * @param
     * @throws HsException
     *             异常处理类
     */
    @Override
    public void correctAccount(List<AccountEntry> accountEntryList) throws HsException {
        BizLog.biz("HSXT_AC", "方法：correctAccount", "accountEntryList", "冲正红冲记账");
        if (accountEntryList.isEmpty())
        {
            SystemLog.debug("HSXT_AC", "方法：correctAccount", RespCode.AC_PARAMETER_NULL.getCode()
                    + "参数：accountEntryList 集合为空");
            throw new HsException(RespCode.AC_PARAMETER_NULL.getCode(), "参数：accountEntryList 集合为空");
        }
        List<AccountEntry> preAccountEntryList = new ArrayList<AccountEntry>();// 封装原交易分录的对象集合
        List<AccountBalance> accountBalanceList = new ArrayList<AccountBalance>();// 封装账户余额对象集合
        List<AccountBalanceSnap> accountBalanceSnapList = new ArrayList<AccountBalanceSnap>();// 封装账户快照对象集合
        Map<String, BigDecimal> accBalanceMap = new HashMap<String, BigDecimal>();// 相同客户ID和账户类型余额最新值
        Map<String, BigDecimal> correctedMap = new HashMap<String, BigDecimal>();// 相同流水号，客户编码，账户类型，对应的唯一分录
        Map<Integer, Integer> perMap = setAndGetDataMethod.getPerTypes();// 获取消费者客户类型
        Map<Integer, Integer> entMap = setAndGetDataMethod.getEntTypes();// 获取企业者客户类型
        Map<Integer, Integer> pfMap = setAndGetDataMethod.getPfTypes();// 获取平台客户类型
        Map<String, String> accTypeMap = setAndGetDataMethod.getSpecialAccTypes();// 只记分录，不做余额变化的账户
        String preEntryNo = setAndGetDataMethod.getPreEntryNo();//获取分录的流水号前缀
        
        try
        {
            for (AccountEntry accountEntry : accountEntryList)
            {

            	//获取原始交易流水号
            	String sourceTransNo = accountEntry.getSourceTransNo();
            	
            	//原始交易流水号没有就储存交易流水号
            	if(sourceTransNo==null||"".equals(sourceTransNo))
            	{
            		accountEntry.setSourceTransNo(accountEntry.getTransNo());
            	}
            	
                // 校验AccountEntry对象及对象中的条件
                validateInfo.checkAccountEntryInfo(accountEntry, true);

                String relTransNo = accountEntry.getRelTransNo();// 关联的流水号
                // Integer writeBack = 0;// 红冲标识正常状态为0
                String custID = accountEntry.getCustID();// 客户全局编码
                String accType = accountEntry.getAccType();// 账户类型编码
                String relSysEntryNo = accountEntry.getRelSysEntryNo();// 关联系统分录序列号

                // 根据分录中关联的系统分录序列号，关联的流水号，客户全局编码，账户类型，查找红冲标识为0的正常分录信息
                AccountEntry account_Entry = accountEntryMapper.seachAccEntryByTrsNoAndSysEntryNo(relSysEntryNo,
                        relTransNo, custID, accType);
                if (account_Entry == null)
                {
                    SystemLog.debug("HSXT_AC", "方法：correctAccount", RespCode.AC_PARAMETER_NULL.getCode()
                            + "红冲冲正关联流水号找不到匹配的流水号分录记录");
                    throw new HsException(RespCode.AC_PARAMETER_NULL.getCode(), "红冲冲正关联流水号找不到匹配的流水号分录记录");
                }

                // 校验原交易流水是否冲正红冲过,及红冲的最大值控制
                Map<String, Object> accountEntryMap = validateInfo.checkWriteBackedAmount(correctedMap, account_Entry,
                        accountEntry);

                account_Entry.setWriteBackedAmount(String.valueOf(accountEntryMap.get("curCorrectEntryAmount")));

                // 当前插入分录的序列值
                String entryNo = GuidAgent.getStringGuid(preEntryNo);
                accountEntry.setEntryNo(entryNo);

                account_Entry.setWriteBack(3);// 标识原记账分录已经冲正红冲
                preAccountEntryList.add(account_Entry);

                if(accTypeMap.get(accType) == null){
                    
                    // 检验账户余额记录信息是否存在、当前余额账户状态和该分录的变化金额是否在账户和客户关系中最大值和最小值之间
                    Map<String, Object> accountBalanceMap = validateInfo.checkAccountEntryAmountInfo(accBalanceMap,
                            accountEntry, true);
                    if (!(Boolean) accountBalanceMap.get("accountOpenFlag"))
                    {
                            SystemLog.debug("HSXT_AC", "方法：correctAccount", RespCode.AC_PARAMETER_NULL.getCode()
                                    + "冲正红冲中对应的账户为空");
                            throw new HsException(RespCode.AC_PARAMETER_NULL.getCode(), "冲正红冲中对应的账户为空");
                    }
                    
                    // 账户余额对象
                    AccountBalance accountBalance = new AccountBalance();
                    accountBalance.setCustID(custID);// 客户全局编码
                    accountBalance.setAccType(accType);// 账户类型
                    Integer custType = account_Entry.getCustType();// 客户类型
                    accountBalance.setCustType(custType);

                    // 判断当前客户类型所属哪个客户分类（0：消费者，1：企业，2：平台）
                    Integer custTypeCategory = validateInfo.checkCustTypesCategory(accountEntry, perMap, entMap, pfMap);
                    accountBalance.setCustTypeCategory(custTypeCategory);

                    BigDecimal newAccBalance = (BigDecimal) accountBalanceMap.get("newAccBalance");// 当前记账分录的金额之和
                    BigDecimal accBalance = (BigDecimal) accountBalanceMap.get("accBalance");// 当前账户余额
                    BigDecimal curAmount = newAccBalance.subtract(accBalance);// 当前记账新增的金额
                    accountBalance.setAccBalance(String.valueOf(curAmount));
                    accountBalanceMap.put("custTypeCategory", custTypeCategory);
                    accountBalanceMap.put("entryAmount", curAmount);

                    // 把相同客户全局编码和账户类型相同的账户余额信息合并一起，当一次更新。
                    setAndGetDataMethod.filterBalanceInfo(accountBalanceList, accountBalance, curAmount);
                    // 封装余额快照信息
                    AccountBalanceSnap accountBalanceSnap = new AccountBalanceSnap();
                    setAndGetDataMethod.setAccountBalanceSnapInfo(accountBalanceSnap, accountEntry, accountBalanceMap,
                            entryNo);
                    accountBalanceSnapList.add(accountBalanceSnap);
                }
            }

            // 冲正红冲记账进行更新账户余额信息,新增余额快照信息,新增红冲分录信息,更新原交易的红冲金额
            accountEntryTransaction.correctAccount(accountBalanceList, accountBalanceSnapList, accountEntryList,
                    preAccountEntryList);
        }
        catch (HsException e)
        {
            SystemLog.error("HSXT_AC", "方法：correctAccount", e.getErrorCode() + e.getMessage(),e);
            throw e;
        }
        catch (Exception e)
        {
            SystemLog.error("HSXT_AC", "方法：correctAccount", RespCode.AC_SQL_ERROR.getCode() + e.getMessage(),e);
            throw new HsException(RespCode.AC_SQL_ERROR.getCode(), e.getMessage());
        }
    }

    /**
     * 单笔冲正红冲记账
     * 
     * @param writeBack
     *            冲正红冲标识（1、手工冲正，2、自动冲正）
     * @param transType
     *            交易类型
     * @param relTransNo
     *            关联交易流水号
     * @throws HsException
     *             异常处理类
     */
    @Override
    public void correctSingleAccount(AccountWriteBack accountWriteBackint) throws HsException {
        BizLog.biz("HSXT_AC", "方法：correctSingleAccount", "accountEntry", "单笔冲正红冲记账");

        try
        {
            int writeBack = accountWriteBackint.getWriteBack();// 冲正红冲标识
            
            if(writeBack == 0)
            {
            	SystemLog.debug("HSXT_AC", "方法：correctSingleAccount", RespCode.AC_PARAMETER_NULL.getCode()
                        + "writeBack:冲正红冲标识为正常状态0");
                throw new HsException(RespCode.AC_PARAMETER_NULL.getCode(), "writeBack:冲正红冲标识为正常状态0");
            }
    
            String transType = accountWriteBackint.getTransType();// 交易类型
            if (transType == null || "".equals(transType))
            {
                SystemLog.debug("HSXT_AC", "方法：correctSingleAccount", RespCode.AC_PARAMETER_NULL.getCode()
                        + "transType:交易类型为空");
                throw new HsException(RespCode.AC_PARAMETER_NULL.getCode(), "transType:交易类型为空");
            }
    
            String relTransNo = accountWriteBackint.getRelTransNo();// 关联交易类型流水号
            if (relTransNo == null || "".equals(relTransNo))
            {
                SystemLog.debug("HSXT_AC", "方法：correctSingleAccount", RespCode.AC_PARAMETER_NULL.getCode()
                        + "relTransNo:关联交易类型流水号为空");
                throw new HsException(RespCode.AC_PARAMETER_NULL.getCode(), "relTransNo:关联交易类型流水号为空");
            }
    
            List<AccountEntry> accountEntryList;// 通过关联的交易流水号查找分录的匹配的原记账分录记录集合
            List<AccountEntry> updateAccEntryList = new ArrayList<AccountEntry>();// 冲正红冲后需要更新红冲金额的账务分录对象集合
            List<AccountBalance> accountBalanceList = new ArrayList<AccountBalance>();// 封装账户余额对象集合
            List<AccountBalanceSnap> accountBalanceSnapList = new ArrayList<AccountBalanceSnap>();// 封装账户快照对象集合
            Map<Integer, Integer> perMap = setAndGetDataMethod.getPerTypes();// 获取消费者客户类型
            Map<Integer, Integer> entMap = setAndGetDataMethod.getEntTypes();// 获取企业者客户类型
            Map<Integer, Integer> pfMap = setAndGetDataMethod.getPfTypes();// 获取平台客户类型
            Map<String, String> accTypeMap = setAndGetDataMethod.getSpecialAccTypes();// 只记分录，不做余额变化的账户
            String preEntryNo = setAndGetDataMethod.getPreEntryNo();//获取分录的流水号前缀
            
//            Integer write_Back = 0;// 红冲标识正常状态为0

            // 单笔冲正红冲关联流水号对应的正常账务分录对象集合
            accountEntryList = accountEntryMapper.seachAccountEntrysByTrsNo(relTransNo, null);
            if (accountEntryList.isEmpty())
            {
                SystemLog.debug("HSXT_AC", "方法：correctSingleAccount", RespCode.AC_CORRECTED.getCode()
                        + "relTransNo:" + relTransNo + "无需要冲正的记录");
                return;
            }
            Integer write_Back = accountEntryList.get(0).getWriteBack();
            if (write_Back.intValue() == 3)
            {
                SystemLog.debug("HSXT_AC", "方法：correctSingleAccount", RespCode.AC_CORRECTED.getCode()
                        + "relTransNo:" + relTransNo + "交易流水号已冲正");
                return;
            }

            // 冲正交易流水号
            String transNo = GuidAgent.getStringGuid(preEntryNo);
            for (AccountEntry account_Entry : accountEntryList)
            {
                String correctTransType1 = account_Entry.getTransType().substring(0, 3);
                String correctTransType2 = transType.substring(0, 3);
                if(!correctTransType2.equals(correctTransType1))
                {
                    continue;
                }
                // 账户余额信息
                AccountBalance accountBalance = new AccountBalance();
                // 单笔冲正红冲对应账务分录的增向金额
                String addAmt = account_Entry.getAddAmount();
                BigDecimal addAmount = setAndGetDataMethod.getBigdAmount(addAmt);

                // 单笔冲正红冲对应账务分录的减向金额
                String subAmt = account_Entry.getSubAmount();
                BigDecimal subAmount = setAndGetDataMethod.getBigdAmount(subAmt);

                // 把当前分录增向金额变为负数
                account_Entry.setAddAmount(String.valueOf(BigDecimal.valueOf(0).subtract(addAmount)));

                // 把当前分录减向金额变为负数
                account_Entry.setSubAmount(String.valueOf(BigDecimal.valueOf(0).subtract(subAmount)));

                Map<String, Object> accountBalanceMap = null;
                String accType = account_Entry.getAccType();// 原账务分录的账户类型编码
                if(accTypeMap.get(accType) == null){
                    // 检验账户余额记录信息是否存在、当前余额账户状态和该分录的变化金额是否在账户和客户关系中最大值和最小值之间
                    accountBalanceMap = validateInfo.checkAccEntryAmountForSingal(account_Entry);
                }
               
                // 校验原交易流水是否冲正红冲过,并返回当前完全冲正红冲的金额
                BigDecimal singalWriteBackedAmt = validateInfo.checkSingalWriteBackedAmount(account_Entry);

                // 把当前完全冲正红冲的金额变为负数
                BigDecimal newsingalWriteBackedAmt = BigDecimal.valueOf(0).subtract(singalWriteBackedAmt);

                // 把原账务分录的信息改为单笔冲正红冲中冲正红冲标识、交易流水号和交易类型、更新关联流水号和类型、添加备注、并记账金额变为负数。封装成单笔冲正红冲分录信息记录。
                account_Entry.setWriteBack(writeBack);

                String custId = account_Entry.getCustID();// 原账务分录的客户全局编码
                Integer custType = account_Entry.getCustType();// 原账务分录的客户类型
                String sysEntryNo = account_Entry.getSysEntryNo();// 原账务分录的分录系统号
                String preTransType = account_Entry.getTransType();// 原账务分录的交易类型

                String sysEntyNo = GuidAgent.getStringGuid(preEntryNo);
                account_Entry.setTransNo(transNo);
                account_Entry.setRelTransType(preTransType);
                account_Entry.setRelSysEntryNo(sysEntryNo);
                account_Entry.setRemark(accountWriteBackint.getRemark());
                account_Entry.setSysEntryNo(sysEntyNo);
                account_Entry.setTransType(transType);
                account_Entry.setRelTransNo(relTransNo);

                // 增向金额大于0,原账务分录记账金额为增向金额
                if (addAmount.compareTo(BigDecimal.valueOf(0)) == 1)
                {
                    account_Entry.setAddAmount(String.valueOf(newsingalWriteBackedAmt));

                    // 原账务分录记账金额为增向金额,所以要把相应的账户余额减去冲正红冲金额
                    accountBalance.setAccBalance(String.valueOf(newsingalWriteBackedAmt));
                }

                // 减向金额大于0,原账务分录记账金额为减向金额
                if (subAmount.compareTo(BigDecimal.valueOf(0)) == 1)
                {
                    account_Entry.setSubAmount(String.valueOf(newsingalWriteBackedAmt));

                    // 原账务分录记账金额为减向金额,所以要把相应的账户余额加上冲正红冲金额
                    accountBalance.setAccBalance(String.valueOf(singalWriteBackedAmt));
                }

                // 当前插入分录的序列值
                String entryNo = GuidAgent.getStringGuid(preEntryNo);
                account_Entry.setEntryNo(entryNo);

                // 封装对原账务分录的红冲金额进行更新的条件对象
                AccountEntry updateAccEntry = new AccountEntry();
                updateAccEntry.setWriteBack(3);// 标识原记账分录已经冲正红冲
                updateAccEntry.setCustID(custId);
                updateAccEntry.setAccType(accType);
                updateAccEntry.setTransNo(relTransNo);
                updateAccEntry.setSysEntryNo(sysEntryNo);
                updateAccEntry.setWriteBackedAmount(String.valueOf(singalWriteBackedAmt));
                updateAccEntryList.add(updateAccEntry);

                if(accTypeMap.get(accType) == null){
                    // 更新对应的账户余额信息
                    accountBalance.setCustID(custId);
                    accountBalance.setAccType(accType);
                    accountBalance.setCustType(custType);

                    // 判断当前客户类型所属哪个客户分类（0：消费者，1：企业，2：平台）
                    Integer custTypeCategory = validateInfo.checkCustTypesCategory(account_Entry, perMap, entMap, pfMap);
                    accountBalance.setCustTypeCategory(custTypeCategory);
                    accountBalanceList.add(accountBalance);
                    accountBalanceMap.put("custTypeCategory", custTypeCategory);
                    accountBalanceMap.put("entryAmount", singalWriteBackedAmt);

                    // 余额快照信息
                    AccountBalanceSnap accountBalanceSnap = new AccountBalanceSnap();
                    setAndGetDataMethod.setAccountBalanceSnapInfo(accountBalanceSnap, account_Entry, accountBalanceMap,
                            entryNo);
                    accountBalanceSnapList.add(accountBalanceSnap);
                }
            }

            // 单笔冲正红冲记账（更新账户余额信息，新增余额快照信息，新增原交易的红冲冲正记录，更新原交易的红冲金额
            accountEntryTransaction.correctSingleAccount(accountBalanceList, accountBalanceSnapList, accountEntryList,
                    updateAccEntryList);
        }
        catch (HsException e)
        {
            SystemLog.error("HSXT_AC", "方法：correctSingleAccount", e.getErrorCode() + e.getMessage(),e);
            throw e;
        }
        catch (Exception e)
        {
            SystemLog.error("HSXT_AC", "方法：correctSingleAccount", RespCode.AC_SQL_ERROR.getCode() + e.getMessage(),e);
            throw new HsException(RespCode.AC_SQL_ERROR.getCode(), e.getMessage());
        }
    }

    /**
     * 多笔冲正红冲记账
     * 
     * @param List<AccountWriteBack>
     *            冲正红冲对象集合
     * @throws HsException
     *             异常处理类
     */
    @Override
    public void correctAccountList(List<AccountWriteBack> list) throws HsException {
        BizLog.biz("HSXT_AC", "方法：correctAccountList", "accountEntry", "多笔冲正红冲记账");
        
        //判断冲正集合是否为空
        if(list == null || list.size() == 0){
            SystemLog.debug("HSXT_AC", "方法：correctAccountList", RespCode.AC_PARAMETER_NULL.getCode()
                    + "参数：list 集合为空");
            throw new HsException(RespCode.AC_PARAMETER_NULL.getCode(), "参数：list 集合为空");
        }
        try
        {
            List<AccountEntry> accEntryList = new ArrayList<AccountEntry>() ;// 通过关联的交易流水号查找分录的匹配的原记账分录记录集合
            List<AccountEntry> updateAccEntryList = new ArrayList<AccountEntry>();// 冲正红冲后需要更新红冲金额的账务分录对象集合
            List<AccountBalance> accountBalanceList = new ArrayList<AccountBalance>();// 封装账户余额对象集合
            List<AccountBalanceSnap> accountBalanceSnapList = new ArrayList<AccountBalanceSnap>();// 封装账户快照对象集合
            Map<Integer, Integer> perMap = setAndGetDataMethod.getPerTypes();// 获取消费者客户类型
            Map<Integer, Integer> entMap = setAndGetDataMethod.getEntTypes();// 获取企业者客户类型
            Map<Integer, Integer> pfMap = setAndGetDataMethod.getPfTypes();// 获取平台客户类型
            Map<String, String> accTypeMap = setAndGetDataMethod.getSpecialAccTypes();// 只记分录，不做余额变化的账户
            String preEntryNo = setAndGetDataMethod.getPreEntryNo();//获取分录的流水号前缀
            
            //遍历冲正集合
            for(AccountWriteBack acBack : list){
                setAndGetDataMethod.validateBack(acBack);
                
                int writeBack = acBack.getWriteBack();//红冲冲正标识
                String transType = acBack.getTransType();// 交易类型
                String relTransNo = acBack.getRelTransNo();
                // 单笔冲正红冲关联流水号对应的正常账务分录对象集合
                List<AccountEntry> accountEntryList = accountEntryMapper.seachAccountEntrysByTrsNo(relTransNo, null);
                
                if (accountEntryList.isEmpty())
                {
                    SystemLog.debug("HSXT_AC", "方法：correctAccountList", RespCode.AC_CORRECTED.getCode()
                            + "relTransNo:" + relTransNo + "无需要冲正的记录");
                    return;
                }
                Integer write_Back = accountEntryList.get(0).getWriteBack();
                if (write_Back.intValue() == 3)
                {
                    SystemLog.debug("HSXT_AC", "方法：correctAccountList", RespCode.AC_CORRECTED.getCode()
                            + "relTransNo:" + relTransNo + "交易流水号已冲正");
                    return;
                }
                // 冲正交易流水号
                String transNo = GuidAgent.getStringGuid(preEntryNo);
                for (AccountEntry account_Entry : accountEntryList)
                {
                    // 账户余额信息
                    AccountBalance accountBalance = new AccountBalance();
                    // 单笔冲正红冲对应账务分录的增向金额
                    String addAmt = account_Entry.getAddAmount();
                    BigDecimal addAmount = setAndGetDataMethod.getBigdAmount(addAmt);

                    // 单笔冲正红冲对应账务分录的减向金额
                    String subAmt = account_Entry.getSubAmount();
                    BigDecimal subAmount = setAndGetDataMethod.getBigdAmount(subAmt);

                    // 把当前分录增向金额变为负数
                    account_Entry.setAddAmount(String.valueOf(BigDecimal.valueOf(0).subtract(addAmount)));

                    // 把当前分录减向金额变为负数
                    account_Entry.setSubAmount(String.valueOf(BigDecimal.valueOf(0).subtract(subAmount)));

                    Map<String, Object> accountBalanceMap = null;
                    String accType = account_Entry.getAccType();// 原账务分录的账户类型编码
                    if(accTypeMap.get(accType) == null){
                        // 检验账户余额记录信息是否存在、当前余额账户状态和该分录的变化金额是否在账户和客户关系中最大值和最小值之间
                        accountBalanceMap = validateInfo.checkAccEntryAmountForSingal(account_Entry);
                    }
                   
                    // 校验原交易流水是否冲正红冲过,并返回当前完全冲正红冲的金额
                    BigDecimal singalWriteBackedAmt = validateInfo.checkSingalWriteBackedAmount(account_Entry);

                    // 把当前完全冲正红冲的金额变为负数
                    BigDecimal newsingalWriteBackedAmt = BigDecimal.valueOf(0).subtract(singalWriteBackedAmt);

                    // 把原账务分录的信息改为单笔冲正红冲中冲正红冲标识、交易流水号和交易类型、更新关联流水号和类型、添加备注、并记账金额变为负数。封装成单笔冲正红冲分录信息记录。
                    account_Entry.setWriteBack(writeBack);

                    String custId = account_Entry.getCustID();// 原账务分录的客户全局编码
                    Integer custType = account_Entry.getCustType();// 原账务分录的客户类型
                    String sysEntryNo = account_Entry.getSysEntryNo();// 原账务分录的分录系统号
                    String preTransType = account_Entry.getTransType();// 原账务分录的交易类型

                    String sysEntyNo = GuidAgent.getStringGuid(preEntryNo);
                    account_Entry.setTransNo(transNo);
                    account_Entry.setRelTransType(preTransType);
                    account_Entry.setRelSysEntryNo(sysEntryNo);
                    account_Entry.setRemark(acBack.getRemark());
                    account_Entry.setSysEntryNo(sysEntyNo);
                    account_Entry.setTransType(transType);
                    account_Entry.setRelTransNo(relTransNo);

                    // 增向金额大于0,原账务分录记账金额为增向金额
                    if (addAmount.compareTo(BigDecimal.valueOf(0)) == 1)
                    {
                        account_Entry.setAddAmount(String.valueOf(newsingalWriteBackedAmt));

                        // 原账务分录记账金额为增向金额,所以要把相应的账户余额减去冲正红冲金额
                        accountBalance.setAccBalance(String.valueOf(newsingalWriteBackedAmt));
                    }

                    // 减向金额大于0,原账务分录记账金额为减向金额
                    if (subAmount.compareTo(BigDecimal.valueOf(0)) == 1)
                    {
                        account_Entry.setSubAmount(String.valueOf(newsingalWriteBackedAmt));

                        // 原账务分录记账金额为减向金额,所以要把相应的账户余额加上冲正红冲金额
                        accountBalance.setAccBalance(String.valueOf(singalWriteBackedAmt));
                    }

                    // 当前插入分录的序列值
                    String entryNo = GuidAgent.getStringGuid(preEntryNo);
                    account_Entry.setEntryNo(entryNo);

                    // 封装对原账务分录的红冲金额进行更新的条件对象
                    AccountEntry updateAccEntry = new AccountEntry();
                    updateAccEntry.setWriteBack(3);// 标识原记账分录已经冲正红冲
                    updateAccEntry.setCustID(custId);
                    updateAccEntry.setAccType(accType);
                    updateAccEntry.setTransNo(relTransNo);
                    updateAccEntry.setSysEntryNo(sysEntryNo);
                    updateAccEntry.setWriteBackedAmount(String.valueOf(singalWriteBackedAmt));
                    updateAccEntryList.add(updateAccEntry);

                    if(accTypeMap.get(accType) == null){
                        // 更新对应的账户余额信息
                        accountBalance.setCustID(custId);
                        accountBalance.setAccType(accType);
                        accountBalance.setCustType(custType);

                        // 判断当前客户类型所属哪个客户分类（0：消费者，1：企业，2：平台）
                        Integer custTypeCategory = validateInfo.checkCustTypesCategory(account_Entry, perMap, entMap, pfMap);
                        accountBalance.setCustTypeCategory(custTypeCategory);
                        accountBalanceList.add(accountBalance);
                        accountBalanceMap.put("custTypeCategory", custTypeCategory);
                        accountBalanceMap.put("entryAmount", singalWriteBackedAmt);

                        // 余额快照信息
                        AccountBalanceSnap accountBalanceSnap = new AccountBalanceSnap();
                        setAndGetDataMethod.setAccountBalanceSnapInfo(accountBalanceSnap, account_Entry, accountBalanceMap,
                                entryNo);
                        accountBalanceSnapList.add(accountBalanceSnap);
                    }
                }
                accEntryList.addAll(accountEntryList);
            }

            // 单笔冲正红冲记账（更新账户余额信息，新增余额快照信息，新增原交易的红冲冲正记录，更新原交易的红冲金额
            accountEntryTransaction.correctSingleAccount(accountBalanceList, accountBalanceSnapList, accEntryList,
                    updateAccEntryList);
        }
        catch (HsException e)
        {
            SystemLog.error("HSXT_AC", "方法：correctAccountList", e.getErrorCode() + e.getMessage(),e);
            throw e;
        }
        catch (Exception e)
        {
            SystemLog.error("HSXT_AC", "方法：correctAccountList", RespCode.AC_SQL_ERROR.getCode() + e.getMessage(),e);
            throw new HsException(RespCode.AC_SQL_ERROR.getCode(), e.getMessage());
        }
    }
    
    
    /**
     * 余额预留
     * 
     * @param accountEntryList
     *            预留分录对象集合
     * @throws HsException
     *             异常处理类
     */
    @Override
    public void obligateAccount(List<AccountEntry> accountEntryList) throws HsException {
        BizLog.biz("HSXT_AC", "方法：obligateAccount", "accountEntryList", "对正常账户进行余额预留处理");
        if (accountEntryList.isEmpty())
        {
            SystemLog.debug("HSXT_AC", "方法：obligateAccount", RespCode.AC_PARAMETER_NULL.getCode()
                    + "参数：accountEntryList 集合为空");
            throw new HsException(RespCode.AC_PARAMETER_NULL.getCode(), "参数：accountEntryList 集合为空");
        }
        Map<String, BigDecimal> accBalanceMap = new HashMap<String, BigDecimal>();// 相同客户ID和账户类型余额最新值
        List<AccountEntry> accEntryList = new ArrayList<AccountEntry>();// 预留分录对象集合
        List<AccountBalance> openAccBalanceList = new ArrayList<AccountBalance>();// 封装更新账户余额对象集合
        List<AccountBalance> updateAccBalanceList = new ArrayList<AccountBalance>();// 封装更新账户余额对象集合
        List<AccountBalanceSnap> accountBalanceSnapList = new ArrayList<AccountBalanceSnap>();// 封装余额快照对象集合
        String preEntryNo = setAndGetDataMethod.getPreEntryNo();//获取分录的流水号前缀
        try
        {
            for (AccountEntry accountEntry : accountEntryList)
            {
                String amount = accountEntry.getAmount();// 预留金额
                if (amount == null || "".equals(amount) || "0".equals(amount) || "null".equals(amount))
                {
                    SystemLog.debug("HSXT_AC", "方法：obligateAccount", RespCode.AC_PARAMETER_NULL.getCode() + "amount = "
                            + amount + "预留金额为空或者为 0");
                    throw new HsException(RespCode.AC_PARAMETER_NULL.getCode(), "amount = " + amount + "预留金额为空或者为 0");
                }
                BigDecimal bigAmount = new BigDecimal(amount);
                if (bigAmount.compareTo(BigDecimal.valueOf(0)) == -1)
                {
                    SystemLog.debug("HSXT_AC", "方法：obligateAccount", RespCode.AC_PARAMETER_FORMAT_ERROR.getCode()
                            + "预留金额:" + amount + "为负数");
                    throw new HsException(RespCode.AC_PARAMETER_FORMAT_ERROR.getCode(), "预留金额:" + amount + "为负数");
                }
                accountEntry.setAddAmount("0");
                accountEntry.setSubAmount(amount);
                validateInfo.checkAccountEntryInfo(accountEntry, false);
                Map<String, Object> accountBalanceMap = validateInfo.checkAccountEntryAmountInfo(accBalanceMap,
                        accountEntry, false);
                if (!(Boolean) accountBalanceMap.get("accountOpenFlag"))
                {
                    SystemLog.debug("HSXT_AC", "方法：obligateAccount", RespCode.AC_PARAMETER_NULL.getCode()
                            + "预留账户对应的账户不存在");
                    throw new HsException(RespCode.AC_PARAMETER_NULL.getCode(), "预留账户对应的账户不存在");
                }

                // 封装新增或者更新账户余额(预留)信息集合
                setAndGetDataMethod.setAccountBalanceInfo(openAccBalanceList, updateAccBalanceList, accountEntry,
                        AcConstant.ACC_TYPE_FLAG[2], AcConstant.OPERATE_TYPE[0]);

                // 当前插入分录的序列值
                String entryNo = GuidAgent.getStringGuid(preEntryNo);
                accountEntry.setEntryNo(entryNo);
                accountEntry.setSubAmount(amount);

                // 新增一条正常账户分录的减向金额分录
                accEntryList.add(accountEntry);

                // 新增余额快照信息
                AccountBalanceSnap accountBalanceSnap = new AccountBalanceSnap();

                // 封装accountBalanceSnap对象的信息
                setAndGetDataMethod.setAccountBalanceSnapInfo(accountBalanceSnap, accountEntry, accountBalanceMap,
                        entryNo);
                accountBalanceSnapList.add(accountBalanceSnap);

                // 封装预留账户类型的分录
                AccountEntry account_Entry = new AccountEntry();
                setAndGetDataMethod.setAccountEntryInfo(account_Entry, accountEntry, AcConstant.ACC_TYPE_FLAG[2]);

                // 预留账务分录，记账金额为增向金额
                account_Entry.setSubAmount("0");
                account_Entry.setAddAmount(amount);

                // 新增一条正常账户对应的预留账户的增向金额分录
                accEntryList.add(account_Entry);

            }

            // 统一事务将进行账务分录的预留
            accountEntryTransaction.operateAccount(openAccBalanceList, updateAccBalanceList, accountBalanceSnapList,
                    accEntryList);
        }
        catch (HsException e)
        {
            SystemLog.error("HSXT_AC", "方法：obligateAccount", e.getErrorCode() + e.getMessage(),e);
            throw e;
        }
        catch (Exception e)
        {
            SystemLog.error("HSXT_AC", "方法：obligateAccount", RespCode.AC_SQL_ERROR.getCode() + e.getMessage(),e);
            throw new HsException(RespCode.AC_SQL_ERROR.getCode(), e.getMessage());
        }
    }

    /**
     * 余额释放
     * 
     * @param 对预留余额账户进行余额释放
     *            。
     * @throws HsException
     *             异常处理类
     */
    @Override
    public void releaseAccount(List<AccountEntry> accountEntryList) throws HsException {
        BizLog.biz("HSXT_AC", "方法：releaseAccount", "accountEntryList", "对预留余额账户进行余额释放");
        if (accountEntryList.isEmpty())
        {
            SystemLog.debug("HSXT_AC", "方法：releaseAccount", RespCode.AC_PARAMETER_NULL.getCode()
                    + "参数：accountEntryList 集合为空");
            throw new HsException(RespCode.AC_PARAMETER_NULL.getCode(), "参数：accountEntryList 集合为空");
        }
        Map<String, BigDecimal> accBalanceMap = new HashMap<String, BigDecimal>();// 相同客户ID和账户类型余额最新值
        List<AccountEntry> accEntryList = new ArrayList<AccountEntry>();// 释放分录对象集合
        List<AccountBalance> openAccBalanceList = new ArrayList<AccountBalance>();// 封装新增账户余额对象集合
        List<AccountBalance> updateAccBalanceList = new ArrayList<AccountBalance>();// 封装更新账户余额对象集合
        List<AccountBalanceSnap> accountBalanceSnapList = new ArrayList<AccountBalanceSnap>();// 封装余额快照对象集合
        String preEntryNo = setAndGetDataMethod.getPreEntryNo();//获取分录的流水号前缀
        try
        {
            for (AccountEntry accountEntry : accountEntryList)
            {
                String amount = accountEntry.getAmount();// 预留金额
                if (amount == null || "".equals(amount) || "0".equals(amount) || "null".equals(amount))
                {
                    SystemLog.debug("HSXT_AC", "方法：releaseAccount", RespCode.AC_PARAMETER_NULL.getCode() + "amount = "
                            + amount + "释放金额为空或者为 0");
                    throw new HsException(RespCode.AC_PARAMETER_NULL.getCode(), "amount = " + amount + "释放金额为空或者为 0");
                }
                BigDecimal bigAmount = new BigDecimal(amount);
                if (bigAmount.compareTo(BigDecimal.valueOf(0)) == -1)
                {
                    SystemLog.debug("HSXT_AC", "方法：releaseAccount", RespCode.AC_PARAMETER_NULL.getCode() + "释放金额:"
                            + amount + "为负数");
                    throw new HsException(RespCode.AC_PARAMETER_FORMAT_ERROR.getCode(), "释放金额:" + amount + "为负数");
                }
                accountEntry.setAddAmount(amount);
                accountEntry.setSubAmount("0");
                validateInfo.checkAccountEntryInfo(accountEntry, false);
                Map<String, Object> accountBalanceMap = validateInfo.checkAccountEntryAmountInfo(accBalanceMap,
                        accountEntry, false);
                if (!(Boolean) accountBalanceMap.get("accountOpenFlag"))
                {
                    SystemLog.debug("HSXT_AC", "方法：releaseAccount", RespCode.AC_PARAMETER_NULL.getCode()
                            + "释放账户对应的账户不存在");
                    throw new HsException(RespCode.AC_PARAMETER_NULL.getCode(), "释放账户对应的账户不存在");
                }

                // 封装新增或者更新账户余额(释放)信息集合
                setAndGetDataMethod.setAccountBalanceInfo(openAccBalanceList, updateAccBalanceList, accountEntry,
                        AcConstant.ACC_TYPE_FLAG[2], AcConstant.OPERATE_TYPE[1]);

                // 当前插入分录的序列值
                String entryNo = GuidAgent.getStringGuid(preEntryNo);
                accountEntry.setEntryNo(entryNo);
                accountEntry.setAddAmount(amount);

                // 新增一条正常账户分录的增向金额分录
                accEntryList.add(accountEntry);

                // 新增余额快照信息
                AccountBalanceSnap accountBalanceSnap = new AccountBalanceSnap();

                // 封装accountBalanceSnap对象的信息
                setAndGetDataMethod.setAccountBalanceSnapInfo(accountBalanceSnap, accountEntry, accountBalanceMap,
                        entryNo);
                accountBalanceSnapList.add(accountBalanceSnap);

                // 封装释放账户类型的分录
                AccountEntry account_Entry = new AccountEntry();
                setAndGetDataMethod.setAccountEntryInfo(account_Entry, accountEntry, AcConstant.ACC_TYPE_FLAG[2]);

                // 释放账务分录，记账金额为减向金额
                account_Entry.setSubAmount(amount);
                account_Entry.setAddAmount("0");

                // 新增一条正常账户对应的预留账户的减向金额分录
                accEntryList.add(account_Entry);
            }
            // 统一事务将进行账务分录的释放
            accountEntryTransaction.operateAccount(openAccBalanceList, updateAccBalanceList, accountBalanceSnapList,
                    accEntryList);
        }
        catch (HsException e)
        {
            SystemLog.error("HSXT_AC", "方法：releaseAccount", e.getErrorCode() + e.getMessage(),e);
            throw e;
        }
        catch (Exception e)
        {
            SystemLog.error("HSXT_AC", "方法：releaseAccount", RespCode.AC_SQL_ERROR.getCode() + e.getMessage(),e);
            throw new HsException(RespCode.AC_SQL_ERROR.getCode(), e.getMessage());
        }
    }

    /**
     * 余额冻结
     * 
     * @param 对正常账户进行余额冻结
     *            。
     * @throws HsException
     *             异常处理类
     */
    @Override
    public void frozenAccount(List<AccountEntry> accountEntryList) throws HsException {
        BizLog.biz("HSXT_AC", "方法：frozenAccount", "accountEntryList", "对正常账户进行余额冻结");
        if (accountEntryList.isEmpty())
        {
            SystemLog.debug("HSXT_AC", "方法：frozenAccount", RespCode.AC_PARAMETER_NULL.getCode()
                    + "参数：accountEntryList 集合为空");
            throw new HsException(RespCode.AC_PARAMETER_NULL.getCode(), "参数：accountEntryList 集合为空");
        }
        Map<String, BigDecimal> accBalanceMap = new HashMap<String, BigDecimal>();// 相同客户ID和账户类型余额最新值
        List<AccountEntry> accEntryList = new ArrayList<AccountEntry>();// 释放分录对象集合
        List<AccountBalance> openAccBalanceList = new ArrayList<AccountBalance>();// 封装新增账户余额对象集合
        List<AccountBalance> updateAccBalanceList = new ArrayList<AccountBalance>();// 封装更新账户余额对象集合
        List<AccountBalanceSnap> accountBalanceSnapList = new ArrayList<AccountBalanceSnap>();// 封装余额快照对象集合
        String preEntryNo = setAndGetDataMethod.getPreEntryNo();//获取分录的流水号前缀
        try
        {
            for (AccountEntry accountEntry : accountEntryList)
            {
                String amount = accountEntry.getAmount();// 冻结金额
                if (amount == null || "".equals(amount) || "0".equals(amount) || "null".equals(amount))
                {
                    SystemLog.debug("HSXT_AC", "方法：frozenAccount", RespCode.AC_PARAMETER_NULL.getCode() + "amount = "
                            + amount + "冻结金额为空或者为 0");
                    throw new HsException(RespCode.AC_PARAMETER_NULL.getCode(), "amount = " + amount + "冻结金额为空或者为 0");
                }
                BigDecimal bigAmount = new BigDecimal(amount);
                if (bigAmount.compareTo(BigDecimal.valueOf(0)) == -1)
                {
                    SystemLog.debug("HSXT_AC", "方法：frozenAccount", RespCode.AC_PARAMETER_FORMAT_ERROR.getCode()
                            + "冻结金额 amount = " + amount + "为负数");
                    throw new HsException(RespCode.AC_PARAMETER_FORMAT_ERROR.getCode(), "冻结金额 amount = " + amount
                            + "为负数");
                }
                accountEntry.setAddAmount("0");
                accountEntry.setSubAmount(amount);
                validateInfo.checkAccountEntryInfo(accountEntry, false);
                Map<String, Object> accountBalanceMap = validateInfo.checkAccountEntryAmountInfo(accBalanceMap,
                        accountEntry, false);
                if (!(Boolean) accountBalanceMap.get("accountOpenFlag"))
                {
                    SystemLog.debug("HSXT_AC", "方法：frozenAccount", RespCode.AC_PARAMETER_NULL.getCode()
                            + "释放金额中对应的账户不存在");
                    throw new HsException(RespCode.AC_PARAMETER_NULL.getCode(), "释放金额中对应的账户不存在");
                }

                // 封装新增或者更新账户余额(冻结)信息集合
                setAndGetDataMethod.setAccountBalanceInfo(openAccBalanceList, updateAccBalanceList, accountEntry,
                        AcConstant.ACC_TYPE_FLAG[3], AcConstant.OPERATE_TYPE[2]);

                // 当前插入分录的序列值
                String entryNo = GuidAgent.getStringGuid(preEntryNo);
                accountEntry.setEntryNo(entryNo);
                accountEntry.setSubAmount(amount);

                // 正常账户分录的减向金额分录
                accEntryList.add(accountEntry);

                // 余额快照信息
                AccountBalanceSnap accountBalanceSnap = new AccountBalanceSnap();

                // 封装accountBalanceSnap对象的信息
                setAndGetDataMethod.setAccountBalanceSnapInfo(accountBalanceSnap, accountEntry, accountBalanceMap,
                        entryNo);
                accountBalanceSnapList.add(accountBalanceSnap);

                // 封装冻结账户类型的分录
                AccountEntry account_Entry = new AccountEntry();
                setAndGetDataMethod.setAccountEntryInfo(account_Entry, accountEntry, AcConstant.ACC_TYPE_FLAG[3]);

                // 冻结账务分录，记账金额为增向金额
                account_Entry.setSubAmount("0");
                account_Entry.setAddAmount(amount);

                // 正常账户对应的冻结账户的增向金额分录
                accEntryList.add(account_Entry);
            }
            // 统一事务将进行账务分录的冻结
            accountEntryTransaction.operateAccount(openAccBalanceList, updateAccBalanceList, accountBalanceSnapList,
                    accEntryList);
        }
        catch (HsException e)
        {
            SystemLog.error("HSXT_AC", "方法：frozenAccount", e.getErrorCode() + e.getMessage(),e);
            throw e;
        }
        catch (Exception e)
        {
            SystemLog.error("HSXT_AC", "方法：frozenAccount", RespCode.AC_SQL_ERROR.getCode() + e.getMessage(),e);
            throw new HsException(RespCode.AC_SQL_ERROR.getCode(), e.getMessage());
        }
    }

    /**
     * 余额解冻
     * 
     * @param 对冻结账户余额进行解冻
     *            。
     * @throws HsException
     *             异常处理类
     */
    @Override
    public void thawAccount(List<AccountEntry> accountEntryList) throws HsException {
        BizLog.biz("HSXT_AC", "方法：thawAccount", "accountEntryList", "对冻结账户余额进行解冻");
        if (accountEntryList.isEmpty())
        {
            SystemLog.debug("HSXT_AC", "方法：thawAccount", RespCode.AC_PARAMETER_NULL.getCode()
                    + "参数：accountEntryList 集合为空");
            throw new HsException(RespCode.AC_PARAMETER_NULL.getCode(), "参数：accountEntryList 集合为空");
        }
        Map<String, BigDecimal> accBalanceMap = new HashMap<String, BigDecimal>();// 相同客户ID和账户类型余额最新值
        List<AccountEntry> accEntryList = new ArrayList<AccountEntry>();// 释放分录对象集合
        List<AccountBalance> openAccBalanceList = new ArrayList<AccountBalance>();// 封装新增账户余额对象集合
        List<AccountBalance> updateAccBalanceList = new ArrayList<AccountBalance>();// 封装更新账户余额对象集合
        List<AccountBalanceSnap> accountBalanceSnapList = new ArrayList<AccountBalanceSnap>();// 封装余额快照对象集合
        String preEntryNo = setAndGetDataMethod.getPreEntryNo();//获取分录的流水号前缀
        try
        {
            for (AccountEntry accountEntry : accountEntryList)
            {
                String amount = accountEntry.getAmount();// 解冻金额
                if (amount == null || "".equals(amount) || "0".equals(amount) || "null".equals(amount))
                {
                    SystemLog.debug("HSXT_AC", "方法：thawAccount", RespCode.AC_PARAMETER_NULL.getCode() + "解冻金额为空");
                    throw new HsException(RespCode.AC_PARAMETER_NULL.getCode(), "解冻金额为空");
                }
                BigDecimal bigAmount = new BigDecimal(amount);
                if (bigAmount.compareTo(BigDecimal.valueOf(0)) == -1)
                {
                    SystemLog.debug("HSXT_AC", "方法：thawAccount", RespCode.AC_PARAMETER_FORMAT_ERROR.getCode()
                            + "解冻金额:amount = " + amount + "为负数");
                    throw new HsException(RespCode.AC_PARAMETER_FORMAT_ERROR.getCode(), "解冻金额:amount = " + amount
                            + "为负数");
                }
                accountEntry.setAddAmount(amount);
                accountEntry.setSubAmount("0");
                validateInfo.checkAccountEntryInfo(accountEntry, false);
                Map<String, Object> accountBalanceMap = validateInfo.checkAccountEntryAmountInfo(accBalanceMap,
                        accountEntry, false);
                if (!(Boolean) accountBalanceMap.get("accountOpenFlag"))
                {
                    SystemLog
                            .debug("HSXT_AC", "方法：thawAccount", RespCode.AC_PARAMETER_NULL.getCode() + "释放账户中对应的账户不存在");
                    throw new HsException(RespCode.AC_PARAMETER_NULL.getCode(), "释放账户中对应的账户不存在");
                }

                // 封装新增或者更新账户余额(解冻)信息集合
                setAndGetDataMethod.setAccountBalanceInfo(openAccBalanceList, updateAccBalanceList, accountEntry,
                        AcConstant.ACC_TYPE_FLAG[3], AcConstant.OPERATE_TYPE[3]);

                // 当前插入分录的序列值
                String entryNo = GuidAgent.getStringGuid(preEntryNo);
                accountEntry.setEntryNo(entryNo);
                accountEntry.setAddAmount(amount);

                // 新增一条正常账户分录的增向金额分录
                accEntryList.add(accountEntry);

                // 新增余额快照信息
                AccountBalanceSnap accountBalanceSnap = new AccountBalanceSnap();

                // 封装accountBalanceSnap对象的信息
                setAndGetDataMethod.setAccountBalanceSnapInfo(accountBalanceSnap, accountEntry, accountBalanceMap,
                        entryNo);
                accountBalanceSnapList.add(accountBalanceSnap);

                // 封装解冻账户类型的分录
                AccountEntry account_Entry = new AccountEntry();
                setAndGetDataMethod.setAccountEntryInfo(account_Entry, accountEntry, AcConstant.ACC_TYPE_FLAG[3]);

                // 解冻账务分录，记账金额为减向金额
                account_Entry.setSubAmount(amount);
                account_Entry.setAddAmount("0");

                // 新增一条正常账户对应的解冻账户的减向金额分录
                accEntryList.add(account_Entry);
            }

            // 统一事务将进行账务分录的冻结
            accountEntryTransaction.operateAccount(openAccBalanceList, updateAccBalanceList, accountBalanceSnapList,
                    accEntryList);
        }
        catch (HsException e)
        {
            SystemLog.error("HSXT_AC", "方法：thawAccount", e.getErrorCode() + e.getMessage(),e);
            throw e;
        }
        catch (Exception e)
        {
            SystemLog.error("HSXT_AC", "方法：thawAccount", RespCode.AC_SQL_ERROR.getCode() + e.getMessage(),e);
            throw new HsException(RespCode.AC_SQL_ERROR.getCode(), e.getMessage());
        }
    }

    /**
     * 退单
     * 
     * @param List
     *            <AccountEntry> 分录信息对象集合
     * @throws HsException
     *             异常处理类
     */
    @Override
    public void chargebackAccount(List<AccountEntry> accountEntryList) throws HsException {
        BizLog.biz("HSXT_AC", "方法：chargebackAccount", "accountEntryList", "退单");
        if (accountEntryList.isEmpty())
        {
            SystemLog.debug("HSXT_AC", "方法：chargebackAccount", RespCode.AC_PARAMETER_NULL.getCode()
                    + "参数accountEntryList：集合为空");
            throw new HsException(RespCode.AC_PARAMETER_NULL.getCode(), "参数accountEntryList：集合为空");
        }
        List<AccountBalance> openAccBalanceList = new ArrayList<AccountBalance>();// 封装新增账户余额对象集合
        List<AccountBalance> updateAccBalanceList = new ArrayList<AccountBalance>();// 封装账户余额对象集合(更新)
        List<AccountBalanceSnap> accountBalanceSnapList = new ArrayList<AccountBalanceSnap>();// 封装账户快照对象集合
        List<AccountEntry> acctEntryList = new ArrayList<AccountEntry>();// 封装账务分录集合
        Map<Integer, Integer> perMap = setAndGetDataMethod.getPerTypes();// 获取消费者客户类型
        Map<Integer, Integer> entMap = setAndGetDataMethod.getEntTypes();// 获取企业者客户类型
        Map<Integer, Integer> pfMap = setAndGetDataMethod.getPfTypes();// 获取平台客户类型
        Map<String, String> accTypeMap = setAndGetDataMethod.getSpecialAccTypes();// 只记分录，不做余额变化的账户
        String preEntryNo = setAndGetDataMethod.getPreEntryNo();//获取分录的流水号前缀
        
        try
        {
            for (AccountEntry accountEntry : accountEntryList)
            {
            	String positiveNegative = accountEntry.getPositiveNegative();
            	
                String custId = accountEntry.getCustID();// 客户全局编码
                Integer custType = accountEntry.getCustType();// 客户类型
                Integer custTypeCategory = validateInfo.checkCustTypesCategory(accountEntry, perMap, entMap, pfMap);
                String addAmt = accountEntry.getAddAmount();// 增向金额
                BigDecimal addAmount = setAndGetDataMethod.getBigdAmount(addAmt);
                String subAmt = accountEntry.getSubAmount();// 减向金额
                BigDecimal subAmount = setAndGetDataMethod.getBigdAmount(subAmt);
                String relTransNo = accountEntry.getRelTransNo();// 关联交易流水号
                String relSysEntryNo = accountEntry.getRelSysEntryNo();// 关联系统分录号
                // 退单账务分录的退款账户集，账户以"|"分割，退款账户顺序由左往右
                String accTypes = accountEntry.getAccTypes();
                validateInfo.checkChargeBackInfo(relTransNo, relSysEntryNo, accTypes);
                String accType[] = accTypes.split("\\|");// 以分隔符"|"拆分退款账户字符串集

                // 有多个退款账户，退款顺序以退款账户从左往右
                if (accType.length > 1)
                {
                    for (int i = 0; i < accType.length; i++)
                    {
                        accountEntry.setAccType(accType[i]);// 退单分录的账户分录

                        // 校验AccountEntry对象及对象中的条件，false:正常分录的条件验证
                        validateInfo.checkAccountEntryInfo(accountEntry, false);

                        // 退款账户余额的信息验证（是否存在账户，当前账户状态的验证）
                        AccountBalance accountBalance = validateInfo.checkBalanceInfo(custId, accType[i], addAmount,
                                subAmount, false, custType);
                        if (accountBalance == null &&  i == accType.length - 1 && addAmt!=null && addAmt != "0")
                        {
//                            SystemLog.debug("HSXT_AC", "方法：chargebackAccount", RespCode.AC_PARAMETER_NULL.getCode()
//                                    + "该客户：" + custId + ",退款对应的账户:" + accType[i] + ",为空");
//                            throw new HsException(RespCode.AC_PARAMETER_NULL.getCode(), "该客户：" + custId + ",退款对应的账户:"
//                                    + accType[i] + ",为空");
                            // modify : weixz 2016-04-20 针对网银退款的情况
                            accountBalance = new AccountBalance();
                            accountBalance.setHsResNo(accountEntry.getHsResNo());
                            accountBalance.setAccType(accType[i]);
                            accountBalance.setCustType(custType);
                            accountBalance.setCustID(custId);
                            accountBalance.setAccStatus(0);
                            accountBalance.setAccBalance(addAmt);
                            accountBalance.setCustTypeCategory(custTypeCategory);
                            openAccBalanceList.add(accountBalance);
                            AccountEntry account_Entry = new AccountEntry();// 封装退款账务
                            setAndGetDataMethod.setAccountEntryInfo(account_Entry, accountEntry);
                            // 当前插入分录的序列值
                            String entryNo = GuidAgent.getStringGuid(preEntryNo);
                            account_Entry.setEntryNo(entryNo);
                            acctEntryList.add(account_Entry);
                            // 余额快照信息对象
                            AccountBalanceSnap accountBalanceSnap = new AccountBalanceSnap();
                            Map<String, Object> accountBalanceMap = new HashMap<String, Object>();
                            accountBalanceMap.put("custTypeCategory", custTypeCategory);
                            accountBalanceMap.put("entryAmount", addAmt);
                            // 封装accountBalanceSnap（余额快照表）对象的信息
                            setAndGetDataMethod.setAccountBalanceSnapInfo(accountBalanceSnap, accountEntry, accountBalanceMap,
                                    entryNo);
                            accountBalanceSnapList.add(accountBalanceSnap);
                            break;
                        }
                        else if(accountBalance == null && addAmt!=null && addAmt != "0")
                        {
                            // modify : weixz 2016-04-20 针对网银退款的情况
//                            accountBalance = new AccountBalance();
//                            accountBalance.setHsResNo(accountEntry.getHsResNo());
//                            accountBalance.setAccType(accType[i]);
//                            accountBalance.setCustType(custType);
//                            accountBalance.setCustID(custId);
//                            accountBalance.setAccStatus(0);
//                            accountBalance.setAccBalance(addAmt);
//                            accountBalance.setCustTypeCategory(custTypeCategory);
//                            openAccBalanceList.add(accountBalance);
//                            AccountEntry account_Entry = new AccountEntry();// 封装退款账务
//                            setAndGetDataMethod.setAccountEntryInfo(account_Entry, accountEntry);
//                            // 当前插入分录的序列值
//                            String entryNo = GuidAgent.getStringGuid(preEntryNo);
//                            account_Entry.setEntryNo(entryNo);
//                            acctEntryList.add(account_Entry);
//                            // 余额快照信息对象
//                            AccountBalanceSnap accountBalanceSnap = new AccountBalanceSnap();
//                            Map<String, Object> accountBalanceMap = new HashMap<String, Object>();
//                            accountBalanceMap.put("custTypeCategory", custTypeCategory);
//                            accountBalanceMap.put("entryAmount", addAmt);
//                            // 封装accountBalanceSnap（余额快照表）对象的信息
//                            setAndGetDataMethod.setAccountBalanceSnapInfo(accountBalanceSnap, accountEntry, accountBalanceMap,
//                                    entryNo);
//                            accountBalanceSnapList.add(accountBalanceSnap);
                        	continue;
                        }
                        // 当前账户余额
                        BigDecimal accBalance = accountBalance.getAccBalance() == null ? BigDecimal.valueOf(0)
                                : new BigDecimal(accountBalance.getAccBalance());

                        // 退款对应的原记账分录
                        AccountEntry preAccountEntry = accountEntryMapper.seachAccEntryByTrsNoAndSysEntryNo(
                                relSysEntryNo, relTransNo, custId, accType[i]);
                        if (preAccountEntry == null && i != accType.length - 1)
                        {
                            continue;
                        }
                        else if (preAccountEntry == null && i == accType.length - 1)
                        {
                            SystemLog.debug("HSXT_AC", "方法：chargebackAccount", RespCode.AC_PARAMETER_NULL.getCode()
                                    + "relTransNo:" + relTransNo + ",custId:" + custId + ",accType:" + accType[i]
                                    + "relSysEntryNo:" + relSysEntryNo + ",找不到退款对应的原扣款记账记录");
                            throw new HsException(RespCode.AC_PARAMETER_NULL.getCode(), "relTransNo:" + relTransNo
                                    + ",custId:" + custId + ",accType:" + accType[i] + "relSysEntryNo:" + relSysEntryNo
                                    + ",找不到退款对应的原扣款记账记录");
                        }

                        String preSubAmt = preAccountEntry.getSubAmount();// 退款对应的原记账分录的减向金额

                        BigDecimal preSubAmount = new BigDecimal(preSubAmt);

                        // 查看对应的原记账分录是否做过退单
                        List<AccountEntry> acctEntry_List = accountEntryMapper.seachChargeBackAccEntryList(relTransNo,
                                AcConstant.WRICKBACK_FLAG, custId, accType[i], relSysEntryNo);

                        BigDecimal preAddAmounts = BigDecimal.valueOf(0);// 对应的原记账分录做过退单的总金额
                        // 对应的原记账分录做过退单
                        if (acctEntry_List != null && !acctEntry_List.isEmpty())
                        {
                            for (AccountEntry accEntry : acctEntry_List)
                            {
                                String preAddAmt = accEntry.getAddAmount();// 做过退单的金额
                                BigDecimal preAddAmount = new BigDecimal(preAddAmt);
                                preAddAmounts = preAddAmounts.add(preAddAmount);
                            }
                        }

                        // 当前原记账分录可以退单的剩余金额
                        preSubAmount = preSubAmount.subtract(preAddAmounts);

                        // 对应的原扣款记账记录退款的金额小于0或者等于0
                        if ((preSubAmount.compareTo(BigDecimal.valueOf(0)) == 0
                                || preSubAmount.compareTo(BigDecimal.valueOf(0)) == -1) &&  i == accType.length - 1)
                        {
                            SystemLog.debug("HSXT_AC", "方法：chargebackAccount", RespCode.AC_REFUND_OVER.getCode()
                                    + "relTransNo:" + relTransNo + ",custId:" + custId + ",accType:" + accType[i]
                                    + ",对应的原扣款记账记录退款的金额已经退完");
                            throw new HsException(RespCode.AC_REFUND_OVER.getCode(), "relTransNo:" + relTransNo
                                    + ",custId:" + custId + ",accType:" + accType[i] + ",对应的原扣款记账记录退款的金额已经退完");
                        }
                        else if(preSubAmount.compareTo(BigDecimal.valueOf(0)) == 0 && i != accType.length - 1)
                        {
                            continue;
                        }

                        // 退款金额大于原扣款账金额，并且不是最后一个退款账户
                        if (addAmount.compareTo(preSubAmount) == 1 && i != accType.length - 1)
                        {
                            // 剩余的可退款金额
                            addAmount = addAmount.subtract(preSubAmount);

                            // 退款之后的新的账户余额
                            BigDecimal newBalance = accBalance.add(preSubAmount);

                            // 退款后的账户余额和账户关系表中最大值比较
                            validateInfo.checkCustAccTypeInfo(custId, custType, accType[i], newBalance, positiveNegative);

                            // 封装数据
                            setAndGetDataMethod.setDataInfo(acctEntryList, accountEntry, updateAccBalanceList,
                                    accountBalance, preSubAmount, accBalance, newBalance, custTypeCategory,
                                    accountBalanceSnapList,accTypeMap, false);
                            continue;
                        }
                        // 退款金额小于或等于原扣款账金额
                        else if (addAmount.compareTo(preSubAmount) == -1 || addAmount.compareTo(preSubAmount) == 0)
                        {
                            // 退款后的账户余额
                            BigDecimal newAccBalance = accBalance.add(addAmount);

                            // 扣款后的账户余额和账户关系表中最大值比较
                            validateInfo.checkCustAccTypeInfo(custId, custType, accType[i], newAccBalance, positiveNegative);

                            // 封装数据
                            setAndGetDataMethod.setDataInfo(acctEntryList, accountEntry, updateAccBalanceList,
                                    accountBalance, addAmount, accBalance, newAccBalance, custTypeCategory,
                                    accountBalanceSnapList,accTypeMap, false);
                            break;
                        }

                        // 剩余的退款金额还大于最后一个对应的原记账金额
                        else if (addAmount.compareTo(preSubAmount) == 1 && i == accType.length - 1)
                        {
                            SystemLog.debug("HSXT_AC", "方法：chargebackAccount", RespCode.AC_REFUND_OVER_AVALIABLE.getCode()
                                    + "relTransNo:" + relTransNo + ",custId:" + custId + ",accType:" + accType[i]
                                    + ",退款金额:"+addAmount.intValue()+"大于退单剩余金额:"+preSubAmount.intValue());
                            throw new HsException(RespCode.AC_REFUND_OVER_AVALIABLE.getCode(), "relTransNo:" + relTransNo
                                    + ",custId:" + custId + ",accType:" + accType[i] + ",退款金额大于退单剩余金额");
                        }
                    }
                }
                // 只有一个账户,可能是增向记账也有可能是减向记账
                else
                {
                    accountEntry.setAccType(accType[0]);// 退单分录的账户类型

                    // 校验AccountEntry对象及对象中的条件
                    validateInfo.checkAccountEntryInfo(accountEntry, false);

                    AccountEntry account_Entry = new AccountEntry();// 封装退款账务
                    setAndGetDataMethod.setAccountEntryInfo(account_Entry, accountEntry);
                    // 当前插入分录的序列值
                    String entryNo = GuidAgent.getStringGuid(preEntryNo);
                    account_Entry.setEntryNo(entryNo);
                    acctEntryList.add(account_Entry);
                    
                    if(accTypeMap.get(accType[0]) == null){
                        
                        // 退款账户余额的信息验证（是否存在账户，当前账户状态的验证）
                        AccountBalance accountBalance = validateInfo.checkBalanceInfo(custId, accType[0], addAmount,
                                subAmount, false, custType);
                        boolean flag = true;
                        if (accountBalance == null)
                        {
//                            SystemLog.debug("HSXT_AC", "方法：chargebackAccount", RespCode.AC_PARAMETER_NULL.getCode()
//                                    + "该客户：" + custId + ",退款对应的账户:" + accType[0] + ",为空");
//                            throw new HsException(RespCode.AC_PARAMETER_NULL.getCode(), "该客户：" + custId + ",退款对应的账户:"
//                                    + accType[0] + ",为空");
                            // modify : weixz 2016-04-20 针对网银退款的情况
                            accountBalance = new AccountBalance();
                            accountBalance.setAccType(accType[0]);
                            accountBalance.setHsResNo(accountEntry.getHsResNo());
                            accountBalance.setCustType(custType);
                            accountBalance.setCustID(custId);
                            accountBalance.setAccStatus(0);
                            flag = false;
                        }
                        // 当前账户余额
                        BigDecimal accBalance = accountBalance.getAccBalance() == null ? BigDecimal.valueOf(0)
                                : new BigDecimal(accountBalance.getAccBalance());

                        BigDecimal newAccBalance = BigDecimal.valueOf(0);// 退款之后的账户新余额

                        // 分录当前记账的退款金额
                        BigDecimal accEntryAmount = addAmount.subtract(subAmount);

                        newAccBalance = accBalance.add(accEntryAmount);// 把退款金额加到对应的账户余额
                        accountBalance.setAccBalance(String.valueOf(accEntryAmount));// 当前账户余额更新值为分录当前记账金额

                        // 变化后的账户余额和账户关系表中最小值最大值比较
                        validateInfo.checkCustAccTypeInfo(custId, custType, accType[0], newAccBalance, positiveNegative);

                        // 当前客户类型所属哪个客户分类（0：消费者，1：企业，2：平台）
                        accountBalance.setCustTypeCategory(custTypeCategory);

                        if(flag){
                            // 封装账户余额更新信息集合
                            updateAccBalanceList.add(accountBalance);
                        }else{
                            // 封装账户余额新增信息集合
                            openAccBalanceList.add(accountBalance);
                        }
                        

                        // 余额快照信息对象
                        AccountBalanceSnap accountBalanceSnap = new AccountBalanceSnap();
                        Map<String, Object> accountBalanceMap = new HashMap<String, Object>();
                        //accountBalanceMap.put("oldAccBalance", accBalance);// 当前账户余额的旧余额
                        //accountBalanceMap.put("newAccBalance", newAccBalance);// 当前账户余额的新余额
                        //accountBalanceMap.put("createdDate", accountBalance.getCreatedDate());// 当前账户余额表信息的创建时间
                        //accountBalanceMap.put("updatedDate", accountBalance.getUpdatedDate());// 当前账户余额表信息的更新时间
                        accountBalanceMap.put("custTypeCategory", custTypeCategory);
                        accountBalanceMap.put("entryAmount", accEntryAmount);

                        // 封装accountBalanceSnap（余额快照表）对象的信息
                        setAndGetDataMethod.setAccountBalanceSnapInfo(accountBalanceSnap, accountEntry, accountBalanceMap,
                                entryNo);
                        accountBalanceSnapList.add(accountBalanceSnap);
                    }
                }
            }

            // 退单账户余额统一事务进行新增退单分录，更新账户余额，新增余额快照记录
            accountEntryTransaction.chargebackAccount(openAccBalanceList,updateAccBalanceList, accountBalanceSnapList, acctEntryList);
        }
        catch (HsException e)
        {
            SystemLog.error("HSXT_AC", "方法：chargebackAccount", e.getErrorCode() + e.getMessage(),e);
            throw e;
        }
        catch (Exception e)
        {
            SystemLog.error("HSXT_AC", "方法：chargebackAccount", RespCode.AC_SQL_ERROR.getCode() + e.getMessage(),e);
            throw new HsException(RespCode.AC_SQL_ERROR.getCode(), e.getMessage());
        }
        
    }

    /**
     * 服务记账
     * 
     * @param accountServiceList
     *            服务记账对象集合
     * @throws HsException
     *             异常处理
     * @see com.gy.hsxt.ac.api.IAccountEntryService#chargeServeciAccount(java.util.List)
     */
    @Override
    public List<AccountServiceInfo> chargeServiceAccount(List<AccountService> accountServiceList) throws HsException {

        if (accountServiceList == null)
        {
            SystemLog.debug("HSXT_AC", "方法：chargeServeciAccount", RespCode.AC_PARAMETER_NULL.getCode()
                    + "参数：accountServiceList 对象为空");
            throw new HsException(RespCode.AC_PARAMETER_NULL.getCode(), "参数：accountServiceList 对象为空");
        }
        List<AccountBalance> updateAccBalanceList = new ArrayList<AccountBalance>();// 封装账户余额对象集合(更新)
        List<AccountBalance> openAccBalanceList = new ArrayList<AccountBalance>();// 封装账户余额对象集合(新增)
        List<AccountBalanceSnap> accountBalanceSnapList = new ArrayList<AccountBalanceSnap>();// 封装账户快照对象集合
        List<AccountEntry> acctEntryList = new ArrayList<AccountEntry>();// 封装账务分录集合
        List<AccountServiceInfo> accServiceInfoList = new ArrayList<AccountServiceInfo>();// 返回服务记账插入数据情况集合
        AsEntInfo asEntInfo = entService.searchRegionalPlatform();
        
        // 遍历服务记账对象集合
        for (AccountService accountService : accountServiceList)
        {
            AccountServiceInfo accountServiceInfo = new AccountServiceInfo();// 返回的服务记账对象
            
            try
            {
            	if(asEntInfo == null)
                {
                	throw new HsException(RespCode.AC_PARAMETER_NULL.getCode(), "地方平台客户全局编码未设置");
                }
                // 检查服务记账对象中的条件信息
                validateInfo.checkAccountServiceInfo(accountService);
            }
            catch (HsException e)
            {
                // 如果有异常发生，把当前的返回服务记账对象设置为插入不成功，并保存失败信息
                accountServiceInfo.setCustID(accountService.getCustID());
                accountServiceInfo.setHsResNo(accountService.getHsResNo());
                accountServiceInfo.setFailInfo(e.getErrorCode().toString());
                accountServiceInfo.setFailInfo(e.getMessage());
                accServiceInfoList.add(accountServiceInfo);
                continue;
            }
            
            
            String positiveNegative = accountService.getPositiveNegative();
            
            String custId = accountService.getCustID();// 客户全局编码
            String accTypes = accountService.getAccTypes();// 账户类型编码集
            String accType[] = accTypes.split("\\|");// 以分隔符"|"拆分扣款账户字符串集
            Integer custType = accountService.getCustType();// 客户类型
            String hsResNo = accountService.getHsResNo(); // 互生号
            String amount = accountService.getAmount();// 服务记账金额
            BigDecimal newAmount = new BigDecimal(amount);// 服务记账金额 String
                                                          // -->BigDecimal

            AccountBalance accountBalance = null;// 扣减账户的对应账户对象
            AccountBalance account_Balance = null;// 增额账户对应的账户对象
            BigDecimal accBalance = BigDecimal.valueOf(0);// 扣减账户当前的账户余额
            BigDecimal newSubAmount = BigDecimal.valueOf(0);// 扣减账户扣减金额后的账户余额

            try
            {
            	
                // 扣减账户的对应账户对象
                accountBalance = validateInfo.checkBalanceInfo(custId, accType[0], BigDecimal.valueOf(0), newAmount,
                        false, custType);
                if (accountBalance == null)
                {
                    SystemLog.debug("HSXT_AC", "方法：chargeServeciAccount", RespCode.AC_ENT_BALANCE_DEFICIENCY.getCode()
                            +  "该客户：" + custId + ",扣款对应的账户:" + accType[0] + ",余额不足");
                    throw new HsException(RespCode.AC_ENT_BALANCE_DEFICIENCY.getCode(), "该客户：" + custId + ",扣款对应的账户:" + accType[0] + ",余额不足");
                }

                // 扣减账户当前的账户余额
                accBalance = accountBalance.getAccBalance() == null ? BigDecimal.valueOf(0) : new BigDecimal(
                        accountBalance.getAccBalance());
                // 扣减账户扣减金额后的账户余额
                newSubAmount = accBalance.subtract(newAmount);

                // 变化后的账户余额和账户关系表中最小值最大值比较
                validateInfo.checkCustAccTypeInfo(custId, custType, accType[0], newSubAmount, positiveNegative);

                // 增额账户对应的账户对象
                account_Balance = validateInfo.checkBalanceInfo(asEntInfo.getEntCustId(), accType[1], newAmount, BigDecimal.valueOf(0),
                        false, 6);

                // 变化后的账户余额和账户关系表中最小值最大值比较
                // validateInfo.checkCustAccTypeInfo(6, custId, newAddAmount);
            }
            catch (HsException e)
            {
                // 如果有异常发生，把当前的服务记账对象设置为插入不成功，并保存失败信息
                accountServiceInfo.setCustID(custId);
                accountServiceInfo.setHsResNo(hsResNo);
                accountServiceInfo.setDedutFlag(e.getErrorCode().toString());
                accountServiceInfo.setFailInfo(e.getMessage());
                accServiceInfoList.add(accountServiceInfo);
                continue;
            }
            catch (Exception e)
            {
                SystemLog.error("HSXT_AC", "方法：chargeServiceAccount", RespCode.AC_SQL_ERROR.getCode() + e.getMessage(),e);
                throw new HsException(RespCode.AC_SQL_ERROR.getCode(), e.getMessage());
            }

            // 封装服务记账中扣款分录信息
            setAndGetDataMethod.setServerSubEntry(accountService, acctEntryList, accountBalance, updateAccBalanceList,
                    accountBalanceSnapList);
            // 封装服务记账中增额分录信息
            setAndGetDataMethod.setServerAddEntry(accountService, acctEntryList, account_Balance, updateAccBalanceList,
                    openAccBalanceList, accountBalanceSnapList, asEntInfo);

            // 记录该服务记账条件验证通过，并成功插入到数据库
            accountServiceInfo.setCustID(custId);
            accountServiceInfo.setHsResNo(hsResNo);
            accountServiceInfo.setDedutFlag("Y");
            accServiceInfoList.add(accountServiceInfo);

        }
        try
        {
            // 更新或新增账户余额，新增余额快照记录
            accountEntryTransaction.chargeServiceAccount(openAccBalanceList, updateAccBalanceList,
                    accountBalanceSnapList, acctEntryList);
            return accServiceInfoList;
        }
        catch (HsException e)
        {
            SystemLog.error("HSXT_AC", "方法：chargeServiceAccount", e.getErrorCode() + e.getMessage(),e);
            throw e;
        }
        catch (Exception e)
        {
            SystemLog.error("HSXT_AC", "方法：chargeServiceAccount", RespCode.AC_SQL_ERROR.getCode() + e.getMessage(),e);
            throw new HsException(RespCode.AC_SQL_ERROR.getCode(), e.getMessage());
        }
    }

}
