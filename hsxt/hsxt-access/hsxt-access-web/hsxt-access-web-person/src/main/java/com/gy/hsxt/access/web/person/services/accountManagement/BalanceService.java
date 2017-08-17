/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.person.services.accountManagement;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gy.hsi.common.utils.StringUtils;
import com.gy.hsxt.ac.api.IAccountSearchService;
import com.gy.hsxt.ac.bean.AccountBalance;
import com.gy.hsxt.ac.bean.AccountEntry;
import com.gy.hsxt.ac.bean.AccountEntryInfo;
import com.gy.hsxt.ac.bean.AccountEntrySum;
import com.gy.hsxt.access.web.common.service.BaseServiceImpl;
import com.gy.hsxt.ao.api.IAOBankTransferService;
import com.gy.hsxt.ao.api.IAOCurrencyConvertService;
import com.gy.hsxt.ao.api.IAOExchangeHsbService;
import com.gy.hsxt.bp.bean.BusinessSysParamItemsRedis;
import com.gy.hsxt.bp.client.api.BusinessParamSearch;
import com.gy.hsxt.bs.api.deduction.IBSHsbDeductionService;
import com.gy.hsxt.bs.api.order.IBSInvestProfitService;
import com.gy.hsxt.bs.api.order.IBSOrderService;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.constant.BusinessParam;
import com.gy.hsxt.common.constant.TransType;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.ps.api.IPsQueryService;
import com.gy.hsxt.tc.api.ITcCheckBalanceService;
import com.gy.hsxt.ws.api.IWsGrantService;

/**
 * *************************************************************************
 * 
 * <PRE>
 * Description      : 账户余额查询服务
 * 
 * Project Name     : hsxt-access-web-company 
 * 
 * Package Name     : com.gy.hsxt.access.web.company.services.accountManagement  
 * 
 * File Name        : BalanceService 
 * 
 * Author           : LiZhi Peter
 * 
 * Create Date      : 2015-10-8 下午4:11:39
 * 
 * Update User      : LiZhi Peter
 * 
 * Update Date      : 2015-10-8 下午4:11:39
 * 
 * UpdateRemark     : 说明本次修改内容
 * 
 * Version          : v1.0
 * 
 * </PRE>
 ************************************************************************** 
 */
@Service
public class BalanceService extends BaseServiceImpl implements IBalanceService {
	// 账户系统查询服务
	@Autowired
	private IAccountSearchService accountSearchService;

	// 业务参数服务接口
	@Resource
	private IAOExchangeHsbService aoExchangeHsbService;// 兑换互生币
	@Resource
	private IAOCurrencyConvertService aoCurrencyConvertService;
	// 积分投资服务
	@Autowired
	private IBSInvestProfitService bsInvestProfitService;
	// 银行转账服务
	@Autowired
	private IAOBankTransferService aoBankTransferService;
	@Autowired
	private BusinessParamSearch businessParamSearch;
	// ps服务
	@Autowired
	private IPsQueryService ipsQueryService;

	@Autowired
	private IBSOrderService ibsOrderService;// 订单详情

	@Autowired
	private IWsGrantService iwsGrantService;// 积分福利
    @Autowired
    private ITcCheckBalanceService itcCheckBalanceService;//系统调账
    @Autowired
    private IBSHsbDeductionService ibsHsbDeductionService;//平台业务扣款

	@Override
	public AccountBalance findAccNormal(String custId, String accType) throws HsException
	{
		return accountSearchService.searchAccNormal(custId, accType);
	}

	@Override
	public AccountEntrySum findPerIntegralByToday(String custId, String accType) throws HsException
	{

		return this.accountSearchService.searchPerIntegralByToday(custId, accType);
	}

	/**
	 * @param accountEntry
	 * @return
	 * @throws HsException
	 * @see com.gy.hsxt.access.web.person.services.accountManagement.integration.service.IBalanceService#findAccSumNormal(com.gy.hsxt.ac.bean.AccountEntry)
	 */
	@Override
	public AccountEntry findAccSumNormal(AccountEntry accountEntry) throws HsException
	{
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 客户对应的账户分类查询接口定义
	 * 
	 * @param custId
	 *            客户号
	 * @param accCategory
	 *            分类标识和对应分类名称如下（1 ：积分类型，2 ：互生币类型，3 ：货币类型，5
	 *            ：地区平台银行货币存款/地区平台银行货币现金类型）
	 * @return
	 * @throws HsException
	 */
	@Override
	public Map<String, AccountBalance> searchAccBalanceByAccCategory(String custId, int accCategory) throws HsException
	{
		return this.accountSearchService.searchAccBalanceByAccCategory(custId, accCategory);
	}

	/**
	 * 账户明细分页查询(积分、互生币、货币)
	 * 
	 * @param filterMap
	 * @param sortMap
	 * @param page
	 * @return
	 * @throws HsException
	 * @see com.gy.hsxt.access.web.person.services.accountManagement.IBalanceService#searchAccEntryPage(java.util.Map,
	 *      java.util.Map, com.gy.hsxt.common.bean.Page)
	 */
	@Override
	public PageData<AccountEntry> searchAccEntryPage(Map filterMap, Map sortMap, Page page) throws HsException
	{
		// 获取查询参数类
		AccountEntryInfo aei = createAccountEntryInfo(filterMap);

		// 查询货币明细
		return accountSearchService.searchAccEntryList(aei, page);
	}

	/**
	 * 创建账户明细查询类
	 * 
	 * @param filter
	 * @return
	 */
	AccountEntryInfo createAccountEntryInfo(Map filter)
	{
		AccountEntryInfo aei = new AccountEntryInfo();
		// 客户号
		String custId = (String) filter.get("custId");
		if (!StringUtils.isEmpty(custId))
		{
			aei.setCustID(custId);
		}
		// 账户类型编码
		String accType = (String) filter.get("accType");
		if (!StringUtils.isEmpty(accType))
		{
			aei.setAccType(accType);
		}
		// 业务类型
		String businessType = (String) filter.get("businessType");
		if (!StringUtils.isEmpty(businessType))
		{
			aei.setBusinessType(Integer.parseInt(businessType));
		}
		// 开始时间
		String beginDate = (String) filter.get("beginDate");
		if (!StringUtils.isEmpty(beginDate))
		{
			aei.setBeginDate(beginDate);
		}
		// 结束时间
		String endDate = (String) filter.get("endDate");
		if (!StringUtils.isEmpty(endDate))
		{
			aei.setEndDate(endDate);
		}
		return aei;
	}

	/**
	 * 账户货币交易明细详情
	 * 
	 * @param object
	 * @throws HsException
	 * @see com.gy.hsxt.access.web.person.services.accountManagement.IBalanceService#accEntryDetail(java.lang.Object)
	 */
	@Override
	public Object accEntryDetail(Object object) throws HsException
	{
		return null;
	}

	/**
	 * 获取保底积分
	 * 
	 * @return
	 * @throws HsException
	 * @see com.gy.hsxt.access.web.person.services.accountManagement.IBalanceService#baseIntegral()
	 */
	@Override
	public String baseIntegral() throws HsException
	{
		// 配置参数（组）code
		String code = BusinessParam.CONFIG_PARA.getCode();
		// 保底积分设置key
		String key = BusinessParam.HS_POIT_MIN.getCode();

		BusinessSysParamItemsRedis business = businessParamSearch.searchSysParamItemsByCodeKey(code, key);

		return business.getSysItemsValue();
	}

	/**
	 * 账户操作明细详情(积分、货币)
	 * 
	 * @Description:
	 * @param transNo
	 * @param transType
	 * @return
	 * @throws HsException
	 */
	@Override
	public Map<String, Object> queryAccOptDetailed(String transNo, String transType) throws HsException
	{
		Map<String, Object> ret = new HashMap<String, Object>();

		Object obj = null;
		// // 电商相关查询互生币支付撤单、互生币支付退货、网上订单支付退货、消费积分扣除、消费积分撤单、消费积分退货
		String psType = "ABCDEF";
		String a = transType.substring(0, 1);
		if (psType.indexOf(a) != -1)
		{
			obj = ipsQueryService.queryDetailsByTransNo(transNo);
		} else
		{
			TransType t = TransType.getTransType(transType);
			switch (TransType.getTransType(transType))
			{
			case P_POINT_INVEST:// 积分投资
				obj = bsInvestProfitService.getPointInvestInfo(transNo);
				break;
			case P_POINT_TO_HSB:// 积分转互生币
				obj = aoCurrencyConvertService.getPvToHsbInfo(transNo);
				break;
			case P_HSB_TO_CASH:// 互生币转货币
				obj = aoCurrencyConvertService.getHsbToCashInfo(transNo);
				break;
			case P_INAL_HSB_RECHARGE:// 货币兑换互生币
			case P_INET_HSB_RECHARGE:// 网银兑换互生币
				obj = aoExchangeHsbService.getBuyHsbInfo(transNo);
				break;
			case C_HSB_P_HSB_RECHARGE:// 企业代兑互生币
			case C_HSB_P_ACROSS_RECHARGE_FOR_C:// 企业代兑互生币
			case C_HSB_P_ACROSS_RECHARGE_FOR_P:// 企业代兑互生币
				obj = aoExchangeHsbService.getEntProxyBuyHsbInfo(transNo);
				break;
			case P_TRANS_BANK_REFUND:// 转账银行退票退回
			case P_TRANS_REFUND:// 转账失败退回
				obj = aoBankTransferService.getTransOutFailInfo(transNo);
				break;
			case P_PRETRANS_CASH_CANCEL:// 银行转账撤单
			case P_PRETRANS_CASH:// 银行转账预转出
			case P_TRANS_CASH:// 银行转账转出
				obj = aoBankTransferService.getTransOutInfo(transNo);
				break;
			case P_INVEST_BONUS:// 投资分红
				obj = bsInvestProfitService.getPointDividendInfo(transNo);
				break;
			case P_TEMP_HSB_RECHARGE:// 线下兑换互生币
			case P_HSB_SALES_PAY: // 互生币补互生卡
			case P_HSB_SALES_PAY_CANCEL:
			case P_INET_SALES_PAY_CANCEL:
				obj = ibsOrderService.getOrder(transNo);
				break;
			case W_MEDICAL_SUBSIDIES_APPLY:// 积分福利 医疗补贴申请
			case W_ACCIDENT_SECURITY_APPLY:// 积分福利 申请意外伤害补贴
			case W_DIE_SECURITY_APPLY:// 积分福利 身故保障申请
				obj = iwsGrantService.queryGrantDetail(transNo);
				break;
			case CHECK_BALANCE_IN:// 调账
			case CHECK_BALANCE_OUT:// 调账	
				obj = itcCheckBalanceService.searchCheckBalanceById(transNo);
				break;
			case DEDUCT_HSB_FROM_CUST ://平台业务扣款
				obj = ibsHsbDeductionService.queryDetailById(transNo);
				break;
			default:
				break;
			}
		}
		ret.put("data", obj);
		return ret;
	}
}
