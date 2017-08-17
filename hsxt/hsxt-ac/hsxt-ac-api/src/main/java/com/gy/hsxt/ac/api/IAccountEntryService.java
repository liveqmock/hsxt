

package com.gy.hsxt.ac.api;

import java.util.List;

import com.gy.hsxt.ac.bean.AccountEntry;
import com.gy.hsxt.ac.bean.AccountService;
import com.gy.hsxt.ac.bean.AccountServiceInfo;
import com.gy.hsxt.ac.bean.AccountWriteBack;
import com.gy.hsxt.common.exception.HsException;

/**
 * 账户分录接口
 * 
 * @Package: com.gy.hsxt.ac.api  
 * @ClassName: IAccountEntryService 
 * @Description: TODO
 *
 * @author: maocan 
 * @date: 2015-10-20 下午12:14:41 
 * @version V1.0
 */
public interface IAccountEntryService {

	/**
	 * 实时记账
	 * @param  List<AccountEntry> 分录信息对象集合
	 * @throws HsException   异常处理类
	 */
	public void actualAccount(List<AccountEntry> accountEntryList) throws HsException;
	
	/**
	 * 冲正红冲记账
	 * @param  List<AccountEntry> 分录信息对象集合（红冲冲正分录对象）
	 * @throws HsException   异常处理类
	 */
	public void correctAccount(List<AccountEntry> accountEntryList) throws HsException;
	
	/**
	 * 单笔冲正红冲记账
	 * @param accountWriteBack 冲正红冲对象
	 * @throws HsException   异常处理类
	 */
	public void correctSingleAccount(AccountWriteBack accountWriteBack) throws HsException;
	
	/**
     * 多笔冲正红冲记账
     * @param accountWriteBack 冲正红冲对象
     * @throws HsException   异常处理类
     */
    public void correctAccountList(List<AccountWriteBack> list) throws HsException;
	
	/**
	 * 对正常账户进行余额预留处理
	 * @param  List<AccountEntry> 需要余额预留处理的分录对象
	 * @throws HsException   异常处理类
	 */
	public void obligateAccount(List<AccountEntry> accountEntryList) throws HsException;
	
	/**
	 * 对预留余额账户进行余额释放。
	 * @param  List<AccountEntry> 需要余额释放处理的分录对象
	 * @throws HsException   异常处理类
	 */
	public void releaseAccount(List<AccountEntry> accountEntryList) throws HsException;
	
	
	
	/**
	 * 对正常账户进行余额冻结。
	 * @param  List<AccountEntry> 需要余额冻结处理的分录对象
	 * @throws HsException   异常处理类
	 */
	public void frozenAccount(List<AccountEntry> accountEntryList) throws HsException;
	
	
	/**
	 * 对冻结账户余额进行解冻。
	 * @param  List<AccountEntry> 需要余额解冻处理的分录对象
	 * @throws HsException   异常处理类
	 */	
	public void thawAccount(List<AccountEntry> accountEntryList) throws HsException;
	
	/**
     * 扣减账户余额
     * @param  List<AccountEntry> 分录信息对象集合
     * @throws HsException   异常处理类
     */
    public void deductAccount(List<AccountEntry> accountEntryList) throws HsException;
	
    /**
     * 退单接口
     * @param  List<AccountEntry> 分录信息对象集合
     * @throws HsException   异常处理类
     */
    public void chargebackAccount(List<AccountEntry> accountEntryList) throws HsException;
    
    /**
     * 服务记账接口
     * @param  List<AccountService> 服务记账信息对象集合
     * @return List<AccountServiceInfo> 返回服务记账信息对象插入数据库情况集合
     * @throws HsException   异常处理类
     */
    public List<AccountServiceInfo> chargeServiceAccount(List<AccountService> accountServiceList) throws HsException;
    
    
}
