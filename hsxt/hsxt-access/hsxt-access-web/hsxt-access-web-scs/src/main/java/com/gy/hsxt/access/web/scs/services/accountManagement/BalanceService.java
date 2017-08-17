/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.scs.services.accountManagement;

import java.util.HashMap;
import java.util.Map;

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
import com.gy.hsxt.bs.api.annualfee.IBSAnnualFeeService;
import com.gy.hsxt.bs.api.apply.IBSDeclareService;
import com.gy.hsxt.bs.api.bm.IBSmlmService;
import com.gy.hsxt.bs.api.deduction.IBSHsbDeductionService;
import com.gy.hsxt.bs.api.order.IBSInvestProfitService;
import com.gy.hsxt.bs.api.order.IBSOrderService;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.constant.AccountType;
import com.gy.hsxt.common.constant.BusinessParam;
import com.gy.hsxt.common.constant.TransType;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.ps.api.IPsQueryService;
import com.gy.hsxt.ps.bean.AllocDetail;
import com.gy.hsxt.ps.bean.AllocDetailSum;
import com.gy.hsxt.ps.bean.Proceeds;
import com.gy.hsxt.ps.bean.QueryDetail;
import com.gy.hsxt.tc.api.ITcCheckBalanceService;

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
@SuppressWarnings("rawtypes")
public class BalanceService extends BaseServiceImpl implements IBalanceService {
	// 账户系统查询服务
	@Autowired
	private IAccountSearchService accountSearchService;

	// 增值服务
	@Autowired
	private IBSmlmService bSmlmService;
	// 积分投资服务
	@Autowired
	private IBSInvestProfitService bsInvestProfitService;
	// 货币转换服务
	@Autowired
	private IAOCurrencyConvertService aoCurrencyConvertService;
	// 银行转账服务
	@Autowired
	private IAOBankTransferService aoBankTransferService;
	// 账户操作服务
	@Autowired
	private IAOExchangeHsbService aoExchangeHsbService;
	// 消费积分服务
	@Autowired
	private IPsQueryService psQueryService;
	// 业务参数服务接口
	@Autowired
	private BusinessParamSearch businessParamSearch;
	@Autowired
	private IBSDeclareService bsDeclareService;
	@Autowired
	private ITcCheckBalanceService itcCheckBalanceService;// 系统调账
	@Autowired
	private IBSHsbDeductionService ibsHsbDeductionService;// 平台业务扣款
	@Autowired
	private IBSOrderService ibsOrderService;// 订单详情
	@Autowired
	private IBSAnnualFeeService ibsAnnualFeeService;// 获取年费

	@Override
	public AccountBalance findAccNormal(String custId, String accType)
			throws HsException {

		return accountSearchService.searchAccNormal(custId, accType);

	}

	/**
	 * 企业昨日积分查询
	 * 
	 * @param custId
	 * @param accType
	 * @return
	 * @throws HsException
	 * @see com.gy.hsxt.access.web.mcs.services.accountmanage.IBalanceService#findEntIntegralByYesterday(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public AccountEntrySum findEntIntegralByYesterday(String custId,
			String accType) throws HsException {
		return accountSearchService.searchEntIntegralByYesterday(custId,
				accType);
	}

	/**
	 * @param accountEntry
	 * @return
	 * @throws HsException
	 * @see com.gy.hsxt.access.web.person.services.accountManagement.integration.service.IBalanceService#findAccSumNormal(com.gy.hsxt.ac.bean.AccountEntry)
	 */
	@Override
	public AccountEntry findAccSumNormal(AccountEntry accountEntry)
			throws HsException {
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
	public Map<String, AccountBalance> searchAccBalanceByAccCategory(
			String custId, int accCategory) throws HsException {
		return this.accountSearchService.searchAccBalanceByAccCategory(custId,
				accCategory);
	}

	/**
	 * 同一客户的多个账户余额查询接口定义
	 * 
	 * @param custId
	 *            企业客户号
	 * @param accTypes
	 *            账户类型
	 * @return
	 * @throws HsException
	 */
	@Override
	public Map<String, AccountBalance> searchAccBalanceByCustIdAndAccType(
			String custId, String[] accTypes) throws HsException {
		return this.accountSearchService.searchAccBalanceByCustIdAndAccType(
				custId, accTypes);
	}

	/**
	 * 账户明细分页查询(积分、互生币、货币)
	 * 
	 * @param filterMap
	 * @param sortMap
	 * @param page
	 * @return
	 * @throws HsException
	 * @see com.gy.hsxt.access.web.scs.services.accountManagement.IBalanceService#searchAccEntryPage(java.util.Map,
	 *      java.util.Map, com.gy.hsxt.common.bean.Page)
	 */
	@Override
	public PageData<AccountEntry> searchAccEntryPage(Map filterMap,
			Map sortMap, Page page) throws HsException {
		// 获取查询参数类
		AccountEntryInfo aei = createAccountEntryInfo(filterMap);

		// 查询货币明细
		return accountSearchService.searchAccEntryList(aei, page);
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
	public Map<String, Object> queryAccOptDetailed(String transNo,
			String transType) throws HsException {
		Map<String, Object> ret = new HashMap<String, Object>();
		Object obj = null;
		Proceeds proceeds = new Proceeds();
		proceeds.setTransNo(transNo);
		switch (TransType.getTransType(transType)) {
		case C_POINT_INVEST:// 企业积分投资
			obj = bsInvestProfitService.getPointInvestInfo(transNo);
			break;
		case C_INVEST_BONUS:// 投资分红
			obj = bsInvestProfitService.getPointDividendInfo(transNo);
			break;
		case C_POINT_TO_HSB:// 积分转互生币
			obj = aoCurrencyConvertService.getPvToHsbInfo(transNo);
			break;
		case F_DIST_EXT_POINT:// 积分再增值分配
			obj = bSmlmService.queryBmlmByBizNo(transNo);
			break;
		case F_DIST_HS_POINT:// 互生积分分配
			obj = bSmlmService.queryMlmByBizNo(transNo);
			break;
		case C_HSB_TO_CASH:// 互生币转货币
			obj = aoCurrencyConvertService.getHsbToCashInfo(transNo);
			break;
		case C_PRETRANS_CASH_CANCEL:// 企业银行转账撤单
		case C_PRETRANS_CASH:// 企业转账预转出
		case C_TRANS_CASH:
			obj = aoBankTransferService.getTransOutInfo(transNo);
			break;
		case C_TRANS_REFUND:// 企业转账失败退回
		case C_TRANS_BANK_REFUND:// 企业转账银行退票退回
			obj = aoBankTransferService.getTransOutFailInfo(transNo);
			break;
		case C_INET_HSB_RECHARGE:// 网银兑换互生币
		case C_INAL_HSB_RECHARGE:// 兑换互生币
			obj = aoExchangeHsbService.getBuyHsbInfo(transNo);
			break;
		case DECLARE_FEE_ALLOC:// 劳务费收入分配
			obj = bsDeclareService.queryResFeeAllocById(transNo);
			break;
		case DECLARE_BUYHSB_FEE_ALLOC:// 申报兑换互生币
			obj = bsDeclareService.queryResFeeAllocById(transNo);
			break;
		case CHECK_BALANCE_IN:// 调账
		case CHECK_BALANCE_OUT:// 调账
			obj = itcCheckBalanceService.searchCheckBalanceById(transNo);
			break;
		case DEDUCT_HSB_FROM_CUST:// 平台业务扣款
			obj = ibsHsbDeductionService.queryDetailById(transNo);
			break;
		case C_HSB_SALES_PAY:
		case C_HSB_SALES_PAY_CANCEL:
		case C_TEMP_HSB_RECHARGE:// 线下兑换互生币
			obj = ibsOrderService.getOrder(transNo);
			break;
		case C_HSB_PAY_ANNUAL_FEE:// 互生币缴年费
			obj = ibsAnnualFeeService.queryAnnualFeeOrder(transNo);
			break;
		case HSB_BUSINESS_POINT_TAX:// 消费积分纳税
			proceeds.setTradeType(1);
			obj = psQueryService.queryTaxDetail(proceeds);
			break;
		case HSB_BUSINESS_CSC_TAX://商业服务费税收
			proceeds.setTradeType(2);
			obj = psQueryService.queryTaxDetail(proceeds);
			break;
		default:
			break;
		}
		ret.put("data", obj);
		return ret;
	}

	/**
	 * 查询消费积分分配详情
	 * 
	 * @Description:
	 * @param batchNo
	 * @param entCustId
	 * @return
	 * @throws HsException
	 */
	@Override
	public AllocDetailSum queryPointAllotDetailed(String batchNo,
			String entResNo) throws HsException {
		QueryDetail bean = new QueryDetail();
		bean.setBatchNo(batchNo);
		bean.setResNo(entResNo);
		return psQueryService.queryPointDetailSum(bean);
	}

	/**
	 * 查询消费积分分配详情流水
	 * 
	 * @Description:
	 * @param filterMap
	 * @param sortMap
	 * @param page
	 * @return
	 * @throws HsException
	 */
	@Override
	public PageData<AllocDetail> queryPointAllotDetailedList(Map filterMap,
			Map sortMap, Page page) throws HsException {
		QueryDetail bean = new QueryDetail();
		String batchNo = (String) filterMap.get("batchNo");
		String entResNo = (String) filterMap.get("hsResNo");
		bean.setBatchNo(batchNo);
		bean.setResNo(entResNo);
		bean.setNumber(page.getCurPage());
		bean.setCount(page.getPageSize());
		return psQueryService.queryPointDetail(bean);
	}

	/**
	 * 税金明细查询
	 * 
	 * @param filterMap
	 * @param sortMap
	 * @param page
	 * @return
	 * @throws HsException
	 * @see com.gy.hsxt.access.web.scs.services.accountManagement.IBalanceService#taxDetailPage(java.util.Map,
	 *      java.util.Map, com.gy.hsxt.common.bean.Page)
	 */
	@Override
	public PageData<AccountEntry> taxDetailPage(Map filterMap, Map sortMap,
			Page page) throws HsException {
		// 获取查询参数类
		AccountEntryInfo aei = createAccountEntryInfo(filterMap);
		// 查询货币明细
		return accountSearchService.searchAccEntryListByCustIdAndAccType(aei,
				page);
	}

	/**
	 * 获取保底积分
	 * 
	 * @return
	 * @throws HsException
	 * @see com.gy.hsxt.access.web.person.services.accountManagement.IBalanceService#baseIntegral()
	 */
	@Override
	public String baseIntegral() throws HsException {
		// 配置参数（组）code
		String code = BusinessParam.CONFIG_PARA.getCode();
		// 保底积分设置key
		String key = BusinessParam.HS_POIT_MIN.getCode();

		BusinessSysParamItemsRedis business = businessParamSearch
				.searchSysParamItemsByCodeKey(code, key);

		return business.getSysItemsValue();
	}

	/**
	 * 创建账户明细查询类
	 * 
	 * @param filter
	 * @return
	 */
	AccountEntryInfo createAccountEntryInfo(Map filter) {
		AccountEntryInfo aei = new AccountEntryInfo();
		// 客户号
		String entCustId = (String) filter.get("entCustId");
		if (!StringUtils.isEmpty(entCustId)) {
			aei.setCustID(entCustId);
		}
		// 账户类型编码
		String accType = (String) filter.get("accType");
		if (!StringUtils.isEmpty(accType)) {
			aei.setAccType(accType);
		}
		// 税收账户
		Object isTaxAccountObj = filter.get("isTaxAccount");
		if (null != isTaxAccountObj
				&& Boolean.parseBoolean(isTaxAccountObj.toString()) == true) {
			String[] accTypes = { AccountType.ACC_TYPE_POINT10510.getCode(),
					AccountType.ACC_TYPE_POINT20610.getCode(),
					AccountType.ACC_TYPE_POINT30310.getCode() };
			// 未指定账户类型时查询积分、积分、城市税收对接类型，否则查询指定的账户类型
			if (!StringUtils.isEmpty(aei.getAccType())) {
				accTypes = new String[] { aei.getAccType() };
			}
			aei.setAccTypes(accTypes);
		}
		// 业务类型
		String businessType = (String) filter.get("businessType");
		if (!StringUtils.isEmpty(businessType)) {
			aei.setBusinessType(Integer.parseInt(businessType));
		}
		// 开始时间
		String beginDate = (String) filter.get("beginDate");
		if (!StringUtils.isEmpty(beginDate)) {
			aei.setBeginDate(beginDate);
		}
		// 结束时间
		String endDate = (String) filter.get("endDate");
		if (!StringUtils.isEmpty(endDate)) {
			aei.setEndDate(endDate);
		}
		return aei;
	}
}
