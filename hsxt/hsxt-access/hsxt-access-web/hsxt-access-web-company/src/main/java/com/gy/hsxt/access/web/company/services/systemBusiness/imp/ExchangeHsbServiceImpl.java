/**
 * 
 */
package com.gy.hsxt.access.web.company.services.systemBusiness.imp;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsxt.ac.bean.AccountBalance;
import com.gy.hsxt.access.web.common.constant.ASRespCode;
import com.gy.hsxt.access.web.common.utils.RequestUtil;
import com.gy.hsxt.access.web.company.services.accountManagement.IBalanceService;
import com.gy.hsxt.access.web.company.services.systemBusiness.IExchangeHsbService;
import com.gy.hsxt.ao.api.IAOExchangeHsbService;
import com.gy.hsxt.ao.bean.ProxyBuyHsb;
import com.gy.hsxt.bp.bean.BusinessCustParamItemsRedis;
import com.gy.hsxt.bp.client.api.BusinessParamSearch;
import com.gy.hsxt.common.constant.AccountType;
import com.gy.hsxt.common.constant.BusinessParam;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.BigDecimalUtils;
import com.gy.hsxt.common.utils.DateUtil;
import com.gy.hsxt.common.utils.StringUtils;
import com.gy.hsxt.uc.as.api.common.IUCAsPwdService;
import com.gy.hsxt.uc.as.api.consumer.IUCAsCardHolderAuthInfoService;
import com.gy.hsxt.uc.as.api.consumer.IUCAsCardHolderService;
import com.gy.hsxt.uc.as.api.enumerate.UserTypeEnum;

/**
 * @author weiyq
 *
 */
@Service
public class ExchangeHsbServiceImpl implements IExchangeHsbService {

	@Resource
	private IBalanceService balanceService; // 账户余额查询服务类
	
	@Resource
	private IAOExchangeHsbService aoExchangeHsbService;

	@Resource
	private IUCAsCardHolderService cardHolderService;

	@Autowired
	private IUCAsPwdService iucasPwdService;

	@Autowired
	private IUCAsCardHolderAuthInfoService ucCardHolderAuthInfoService;// 查询实名注册信息服务类
	
	@Autowired
	private BusinessParamSearch businessParamSearch;// 业务参数服务接口
	
	/**
	 * 代兑互生币
	 * 
	 * @param pbh
	 * @param custId
	 * @param loginPwd
	 * @param secretKey
	 */
	public void exchangeHsb(ProxyBuyHsb pbh)
	{
		RequestUtil.verifyParamsIsNotEmpty(new Object[]
		{ pbh.getEntCustId(), ASRespCode.EW_EXCHANGE_HSB_ENTCUSTID.getCode(),
				ASRespCode.EW_EXCHANGE_HSB_ENTCUSTID.getDesc() }, new Object[]
		{ pbh.getEntResNo(), ASRespCode.EW_EXCHANGE_HSB_ENTRESNO.getCode(),
				ASRespCode.EW_EXCHANGE_HSB_ENTRESNO.getDesc() }, new Object[]
		{ pbh.getEntCustName(), ASRespCode.EW_EXCHANGE_HSB_ENTCUSTNAME.getCode(),
				ASRespCode.EW_EXCHANGE_HSB_ENTCUSTNAME.getDesc() }, new Object[]
		{ pbh.getEntCustType(), ASRespCode.EW_EXCHANGE_HSB_ENTCUSTTYPE.getCode(),
				ASRespCode.EW_EXCHANGE_HSB_ENTCUSTTYPE.getDesc() }, new Object[]
		{ pbh.getPerCustId(), ASRespCode.EW_EXCHANGE_HSB_PERCUSTID.getCode(),
				ASRespCode.EW_EXCHANGE_HSB_PERCUSTID.getDesc() }, new Object[]
		{ pbh.getPerResNo(), ASRespCode.EW_EXCHANGE_HSB_PERRESNO.getCode(),
				ASRespCode.EW_EXCHANGE_HSB_PERRESNO.getDesc() }, new Object[]
		{ pbh.getPerCustType(), ASRespCode.EW_EXCHANGE_HSB_PERCUSTTYPE.getCode(),
				ASRespCode.EW_EXCHANGE_HSB_PERCUSTTYPE.getDesc() }, new Object[]
		{ pbh.getCashAmt(), ASRespCode.EW_EXCHANGE_HSB_CASHAMT.getCode(),
				ASRespCode.EW_EXCHANGE_HSB_CASHAMT.getDesc() }, new Object[]
		{ pbh.getOptCustId(), ASRespCode.EW_EXCHANGE_HSB_OPTCUSTID.getCode(),
				ASRespCode.EW_EXCHANGE_HSB_OPTCUSTID.getDesc() }, new Object[]
		{ pbh.getChannel(), ASRespCode.EW_EXCHANGE_HSB_CHANNEL.getCode(),
				ASRespCode.EW_EXCHANGE_HSB_CHANNEL.getDesc() });

		aoExchangeHsbService.saveEntProxyBuyHsb(pbh);
	}

	/**
	 * 根据互生号查询客户号
	 * 
	 * @param perResNo
	 * @return
	 * @throws HsException
	 */
	public String findCustIdByResNo(String perResNo) throws HsException
	{
		try
		{
			return cardHolderService.findCustIdByResNo(perResNo);
		} catch (HsException hse)
		{
			throw hse;
		} catch (Exception e)
		{
			throw new HsException(ASRespCode.EW_EXCHANGE_HSB_CUST_NOT_FOUND);
		}
	}
	
	/**
	 * 判断该互生号是否可以兑换当前金额
	 * @param request
	 * @return Map
	 * @throws HsException
	 */
	public Map<String,String> getExchangeHsbValidate(String cardNo,String exchangeAmount,String entCustId)throws HsException{
		
		Map<String,String> map = new HashMap<String,String>();
		
		// 通过互生号查询持卡人的客户ID
		String perCustId = this.findCustIdByResNo(cardNo);
		// 执行查询 (1：未实名注册、2：已实名注册（有名字和身份证）、3:已实名认证)
		String authStatus = ucCardHolderAuthInfoService.findAuthStatusByCustId(perCustId);
		// 判断当前企业是否有足够的互生币代兑持卡人互生币金额
		String entHsbAmt = this.getCompanyHsb(entCustId);  // 当前企业可代兑的互生币金额
		
		if(BigDecimalUtils.compareTo(exchangeAmount, entHsbAmt) > 0){
			 map.put("exption", "1");
			 return map;
		}
		BusinessCustParamItemsRedis bpItems = null; // BP系统参数对象
		// 兑换互生币（组）
	    String sysGroupCode = BusinessParam.EXCHANGE_HSB.getCode();
		String dayCode = null;    // 每日最大数量代码
		String dayCount = null;   // 每日最大数量
		String daySingleMaxCode = null;  // 每日单笔最大数量代码
		String daySingleMaxCount = null; // 每日单笔最大数量
		String daySingleMinCode = null;  // 每日单笔最小数量代码
		String daySingleMinCount = null; // 每日单笔最小数量
		String alertInfo = "";
		// 判断是否实名注册
		if ("1".equals(authStatus)) {
			// 未实名注册个人每日兑换互生币最大限额枚举Code
			dayCode = BusinessParam.P_NOT_REGISTERED_SINGLE_DAY_BUY_HSB_MAX.getCode();
			// 未实名注册个人每日单笔兑换互生币最大限额枚举Code
			daySingleMaxCode = BusinessParam.P_NOT_REGISTERED_SINGLE_BUY_HSB_MAX.getCode();
			// 未实名注册个人每日兑换生币最小限额枚举Code
			daySingleMinCode = BusinessParam.P_NOT_REGISTERED_SINGLE_BUY_HSB_MIN.getCode();
			alertInfo = "N";
		} else {
			// 已实名注册个人每日兑换互生币最大限额枚举Code
			dayCode = BusinessParam.P_REGISTERED_SINGLE_DAY_BUY_HSB_MAX.getCode();
			// 已实名注册个人每日单笔兑换互生币最大限额枚举Code
			daySingleMaxCode = BusinessParam.P_REGISTERED_SINGLE_BUY_HSB_MAX.getCode();
			// 已实名注册个人每日单笔兑换生币最小限额枚举Code
			daySingleMinCode = BusinessParam.P_REGISTERED_SINGLE_BUY_HSB_MIN.getCode();
			alertInfo = "Y";
		}
		// 每日单笔最大限额校验
		bpItems = businessParamSearch.searchCustParamItemsByIdKey(perCustId,sysGroupCode, daySingleMaxCode);
		daySingleMaxCount = StringUtils.isBlank(bpItems) ? "0" : bpItems.getSysItemsValue();
		if(BigDecimalUtils.compareTo(exchangeAmount,daySingleMaxCount) > 0 ){
			map.put("exption", alertInfo+"2");
			return map;
		}
		// 每日单笔最小限额校验
		bpItems = businessParamSearch.searchCustParamItemsByIdKey(perCustId,sysGroupCode, daySingleMinCode);
		daySingleMinCount = StringUtils.isBlank(bpItems) ? "0" : bpItems.getSysItemsValue();
		if(BigDecimalUtils.compareTo(daySingleMinCount,exchangeAmount) > 0 ){
			map.put("exption", alertInfo+"3");
			return map;
		}
		// 每日最大限额校验
		bpItems = businessParamSearch.searchCustParamItemsByIdKey(perCustId,sysGroupCode, dayCode);
		dayCount = StringUtils.isBlank(bpItems) ? "0" : bpItems.getSysItemsValue();
		
		// 当日持卡人还可兑换互生币总额
		String amountAvailable = this.getCardHolderAmountAvailable(perCustId, dayCount);
		
		if(BigDecimalUtils.compareTo(amountAvailable,"0") == 0){
			map.put("exption", "4");
			return map;
		}
		if(BigDecimalUtils.compareTo(exchangeAmount,dayCount) > 0 ||BigDecimalUtils.compareTo(exchangeAmount,amountAvailable) > 0 ){
			map.put("exption", "5");
			map.put("amt", amountAvailable);
			return map;
		}
		return map;
	};
	
	/**
	 * 当日持卡人还可兑换互生币金额
	 * @param perCustId
	 * @param amountMax
	 * @return
	 */
	private String getCardHolderAmountAvailable(String perCustId,String exchangeAmount) {
		// 每日互生币兑换已经发生数量Code
		String dayBuyCountCode = BusinessParam.SINGLE_DAY_HAD_BUY_HSB.getCode();
		// 获取单日互生币兑换已经发生额总数
		BusinessCustParamItemsRedis business = businessParamSearch.searchCustParamItemsByIdKey(perCustId, dayBuyCountCode);
		String sysItemsValue = business.getSysItemsValue();
		// 当前发生金额是否是当前日期的判断，如果不是，则为0
		String curDateStr = DateUtil.DateToString(new Date(), "yyyyMMdd"); // 当天时间
		String dueDate = business.getDueDate();
		if (StringUtils.isEmpty(sysItemsValue) || !dueDate.equals(curDateStr)) {
			sysItemsValue = "0";
		}
		// 当日持卡人还可兑换互生币总额
		BigDecimal amountAvailable = BigDecimalUtils.ceilingSub(exchangeAmount,sysItemsValue);
		return amountAvailable.toPlainString();
	}
	
	// 通过企业id查询企业互生币余额
	private String getCompanyHsb(String entCustId) {
		// 查询流通币账户
		AccountBalance bal = balanceService.findAccNormal(entCustId,AccountType.ACC_TYPE_POINT20110.getCode());
		// 流通币账户余额
		return StringUtils.isBlank(bal) ? "0" : bal.getAccBalance();
	}
	
}
