/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.aps.services.accountCompany;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsi.common.utils.StringUtils;
import com.gy.hsxt.ac.api.IAccountSearchService;
import com.gy.hsxt.ac.bean.AccountBalance;
import com.gy.hsxt.ac.bean.AccountEntry;
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
import com.gy.hsxt.bs.bean.order.DividendDetail;
import com.gy.hsxt.bs.bean.order.PointDividend;
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
import com.gy.hsxt.rp.api.IReportsAccountEntryService;
import com.gy.hsxt.rp.bean.ReportsAccountEntry;
import com.gy.hsxt.rp.bean.ReportsAccountEntryInfo;
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
@SuppressWarnings("rawtypes")
@Service
public class BalanceService extends BaseServiceImpl implements IBalanceService {
	// 账户系统查询服务
	@Resource
	private IAccountSearchService accountSearchService;
	// 账户流水查询服务
	@Resource
	private IReportsAccountEntryService iReportsAccountEntryService;

	@Resource
	private IAOExchangeHsbService aoExchangeHsbService;
	@Resource
	private IAOCurrencyConvertService aoCurrencyConvertService;
	// 增值服务
	@Autowired
	private IBSmlmService bSmlmService;
	// 积分投资服务
	@Autowired
	private IBSInvestProfitService bsInvestProfitService;
	// 银行转账服务
	@Autowired
	private IAOBankTransferService aoBankTransferService;
	// 消费积分服务
	@Autowired
	private IPsQueryService ipsQueryService;
	// 业务参数服务接口
	@Autowired
	private BusinessParamSearch businessParamSearch;
	@Autowired
	private IBSOrderService ibsOrderService;// 订单详情
	@Autowired
	private IBSDeclareService ibsDeclareService;// Id查询资源费分配记录
	@Autowired
	private IBSAnnualFeeService ibsAnnualFeeService;// 获取年费
	@Autowired
	private ITcCheckBalanceService itcCheckBalanceService;// 系统调账
	@Autowired
	private IBSHsbDeductionService ibsHsbDeductionService;// 平台业务扣款
	@Autowired
	private IWsGrantService iwsGrantService;// 积分福利

	@Override
	public AccountBalance findAccNormal(String custId, String accType)
			throws HsException {

		return accountSearchService.searchAccNormal(custId, accType);

	}

	@Override
	public AccountEntrySum findPerIntegralByToday(String custId, String accType)
			throws HsException {

		return this.accountSearchService.searchPerIntegralByToday(custId,
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
	/*
	 * @Override public Map<String, AccountBalance>
	 * searchAccBalanceByCustIdAndAccType(String custId, String[] accTypes)
	 * throws HsException { return
	 * this.accountSearchService.searchAccBalanceByCustIdAndAccType(custId,
	 * accTypes); }
	 *//**
	 * 税金明细查询
	 * 
	 * @param filterMap
	 * @param sortMap
	 * @param page
	 * @return
	 * @throws HsException
	 * @see com.gy.hsxt.access.web.company.services.accountManagement.IBalanceService#taxDetailPage(java.util.Map,
	 *      java.util.Map, com.gy.hsxt.common.bean.Page)
	 */
	/*
	 * @Override public PageData<AccountEntry> taxDetailPage(Map filterMap, Map
	 * sortMap, Page page) throws HsException { // 获取查询参数类 AccountEntryInfo aei
	 * = this.createAccountEntryInfo(filterMap); // 查询货币明细 return
	 * accountSearchService.searchAccEntryListByCustIdAndAccType(aei, page); }
	 */

	/**
	 * 创建账户明细查询类
	 * 
	 * @param filter
	 * @return
	 */
	ReportsAccountEntryInfo createAccountEntryInfo(Map filter) {
		ReportsAccountEntryInfo aei = new ReportsAccountEntryInfo();
		// 账户类型编码
		String accType = (String) filter.get("accType");
		if (!StringUtils.isEmpty(accType)) {
			String[] accTypes = { accType };
			aei.setAccTypes(accTypes);
		}
		// 交易类型
		aei.setTransType((String) filter.get("transType"));
		// 税收账户
		Object isTaxAccountObj = filter.get("isTaxAccount");
		if (null != isTaxAccountObj
				&& Boolean.parseBoolean(isTaxAccountObj.toString()) == true) {
			String[] accTypes = { AccountType.ACC_TYPE_POINT10510.getCode(),
					AccountType.ACC_TYPE_POINT20610.getCode(),
					AccountType.ACC_TYPE_POINT30310.getCode() };
			// 未指定账户类型时查询积分、积分、城市税收对接类型，否则查询指定的账户类型
			if (!StringUtils.isEmpty(aei.getAccTypes())) {
				accTypes = aei.getAccTypes();
			}
			aei.setAccTypes(accTypes);
		}
		// 业务类型
		String businessType = (String) filter.get("businessType");
		if (!StringUtils.isEmpty(businessType)) {
			aei.setBusinessType(Integer.parseInt(businessType));
		}
		// 互生号
		aei.setHsResNo((String) filter.get("hsResNo"));
		// 名称
		aei.setCustName((String) filter.get("enterpriseName"));
		// 开始时间
		aei.setBeginDate((String) filter.get("beginDate"));
		// 结束时间
		aei.setEndDate((String) filter.get("endDate"));
		return aei;
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
	public Map<String, Object> queryAccOptDetailed(Map map) throws HsException {
		Map<String, Object> ret = new HashMap<String, Object>();
		String transType = (String) map.get("transType");
		String transNo = (String) map.get("transNo");
		Object obj = null;
		// // 电商相关查询互生币支付撤单、互生币支付退货、网上订单支付退货、消费积分扣除、消费积分撤单、消费积分退货
		String psType = "ABCDEF";
		String a = transType.substring(0, 1);
		if (psType.indexOf(a) != -1) {
			obj = ipsQueryService.queryDetailsByTransNo(transNo);
		} else {
			Proceeds proceeds = new Proceeds();
			switch (TransType.getTransType(transType)) {
			case C_POINT_INVEST:// 企业积分投资
			case P_POINT_INVEST:// 积分投资
				obj = bsInvestProfitService.getPointInvestInfo(transNo);
				break;
			case C_POINT_TO_HSB:// 积分转互生币
			case P_POINT_TO_HSB:// 积分转互生币
				obj = aoCurrencyConvertService.getPvToHsbInfo(transNo);
				break;
			case F_DIST_EXT_POINT:// 积分再增值分配
				obj = bSmlmService.queryBmlmByBizNo(transNo);
				break;
			case F_DIST_HS_POINT:// 互生积分分配
				obj = bSmlmService.queryMlmByBizNo(transNo);
				break;
			case C_HSB_TO_CASH:// 互生币转货币
			case P_HSB_TO_CASH:// 互生币转货币
				obj = aoCurrencyConvertService.getHsbToCashInfo(transNo);
				break;
			case C_PRETRANS_CASH:// 企业转账预转出
			case C_PRETRANS_CASH_CANCEL:// 企业银行转账撤单
			case P_PRETRANS_CASH_CANCEL:// 银行转账撤单
			case P_PRETRANS_CASH:// 银行转账预转出
			case P_TRANS_CASH:// 银行转账转出
				obj = aoBankTransferService.getTransOutInfo(transNo);
				break;
			case C_INAL_HSB_RECHARGE:// 货币兑换互生币
			case C_INET_HSB_RECHARGE:// 网银兑换互生币
			case P_INAL_HSB_RECHARGE:// 货币兑换互生币
			case P_INET_HSB_RECHARGE:// 网银兑换互生币
				obj = aoExchangeHsbService.getBuyHsbInfo(transNo);
				break;
			case C_HSB_P_HSB_RECHARGE:// 企业代兑互生币
			case C_HSB_P_ACROSS_RECHARGE_FOR_C:// 企业代兑互生币
			case C_HSB_P_ACROSS_RECHARGE_FOR_P:// 企业代兑互生币
				obj = aoExchangeHsbService.getEntProxyBuyHsbInfo(transNo);
				break;
			case C_TRANS_REFUND:// 企业转账失败退回
			case P_TRANS_BANK_REFUND:// 转账银行退票退回
			case P_TRANS_REFUND:// 转账失败退回
			case C_TRANS_BANK_REFUND:// 企业转账银行退票退回
				obj = aoBankTransferService.getTransOutFailInfo(transNo);
				break;
			case C_INVEST_BONUS:// 投资分红
			case P_INVEST_BONUS:// 投资分红
				obj = bsInvestProfitService.getPointDividendInfo(transNo);
				break;
			case C_TEMP_HSB_RECHARGE:// 线下兑换互生币
			case P_TEMP_HSB_RECHARGE:// 线下兑换互生币
			case P_HSB_SALES_PAY: // 互生币补互生卡
			case P_HSB_SALES_PAY_CANCEL://
			case C_HSB_SALES_PAY:
			case C_HSB_SALES_PAY_CANCEL:
			case C_INET_SALES_PAY_CANCEL:
			case C_TEMP_SALES_PAY_CANCEL:
			case C_HSB_REMAKE_CARD_CANCEL:
			case C_INET_REMAKE_CARD_CANCEL:
			case C_TEMP_REMAKE_CARD_CANCEL:
			case C_HSB_BUY_RES_RANGE_CANCEL:
			case C_INET_BUY_RES_RANGE_CANCEL:
			case C_TEMP_BUY_RES_RANGE_CANCEL:
				obj = ibsOrderService.getOrder(transNo);
				break;
			case C_HSB_PAY_ANNUAL_FEE:// 互生币缴年费
				obj = ibsAnnualFeeService.queryAnnualFeeOrder(transNo);
				break;
			case DECLARE_BUYHSB_FEE_ALLOC:// 申报兑换互生币
				obj = ibsDeclareService.queryResFeeAllocById(transNo);
				break;
			case HSB_BUSINESS_POINT_TAX:// 消费积分纳税
				proceeds.setTransNo(transNo);
				obj = ipsQueryService.queryTaxDetail(proceeds);
				break;
			case HSB_BUSINESS_OFFLINE_CSC:// 线下销售收入
			case HSB_BUSINESS_ONLINE_CSC:// 商城销售收入
				proceeds.setTransNo(transNo);
				obj = ipsQueryService.proceedsDetail(proceeds);
				break;
			case CHECK_BALANCE_IN:// 调账
			case CHECK_BALANCE_OUT:// 调账
				obj = itcCheckBalanceService.searchCheckBalanceById(transNo);
				break;
			case DEDUCT_HSB_FROM_CUST:// 平台业务扣款
				obj = ibsHsbDeductionService.queryDetailById(transNo);
				break;
			case W_MEDICAL_SUBSIDIES_APPLY:// 积分福利 医疗补贴申请
			case W_ACCIDENT_SECURITY_APPLY:// 积分福利 申请意外伤害补贴
			case W_DIE_SECURITY_APPLY:// 积分福利 身故保障申请
				obj = iwsGrantService.queryGrantDetail(transNo);
				break;
			default:

				break;
			}
		}
		ret.put("data", obj);
		return ret;
	}

	/*@Override
	public PvToHsb getPvToHsbInfo(String transNo) throws HsException {
		return aoCurrencyConvertService.getPvToHsbInfo(transNo);
	}

	@Override
	public BuyHsb getBuyHsbInfo(String transNo) throws HsException {
		return aoExchangeHsbService.getBuyHsbInfo(transNo);
	}*/

	/**
	 * 获取保底积分
	 * 
	 * @return
	 * @throws HsException
	 * @see com.gy.hsxt.access.web.company.services.accountManagement.IBalanceService#baseIntegral()
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
	 * @param filterMap
	 * @param sortMap
	 * @param page
	 * @return
	 * @throws HsException
	 * @see com.gy.hsxt.access.web.aps.services.accountCompany.IBalanceService#searchEntAccountEntry(java.util.Map,
	 *      java.util.Map, com.gy.hsxt.common.bean.Page)
	 */
	@Override
	public PageData<ReportsAccountEntry> searchEntAccountEntry(Map filterMap,
			Map sortMap, Page page) throws HsException {

		// 获取查询参数类
		ReportsAccountEntryInfo aei = createAccountEntryInfo(filterMap);

		// 查询货币明细
		return iReportsAccountEntryService.searchEntAccountEntry(aei, page);
	}

	/**
	 * @param filterMap
	 * @param sortMap
	 * @param page
	 * @return
	 * @throws HsException
	 * @see com.gy.hsxt.access.web.aps.services.accountCompany.IBalanceService#searchEntAccountEntry(java.util.Map,
	 *      java.util.Map, com.gy.hsxt.common.bean.Page)
	 */
	@Override
	public PageData<ReportsAccountEntry> searchPerAccountEntry(Map filterMap,
			Map sortMap, Page page) throws HsException {

		// 获取查询参数类
		ReportsAccountEntryInfo aei = createAccountEntryInfo(filterMap);

		// 查询货币明细
		return iReportsAccountEntryService.searchCarAccountEntry(aei, page);
	}

	/**
	 * 分红账户流水查询
	 * 
	 * @param filterMap
	 * @param page
	 * @return
	 * @throws HsException
	 * @see com.gy.hsxt.access.web.aps.services.accountCompany.IBalanceService#searchCarAccountEntryDividend(java.util.Map,
	 *      com.gy.hsxt.common.bean.Page)
	 */
	/*@Override
	public PageData<ReportsAccountEntry> searchCarAccountEntryDividend(
			Map filterMap, Map sortMap, Page page) throws HsException {
		// 获取查询参数类
		ReportsAccountEntryInfo aei = createAccountEntryInfo(filterMap);
		String transType = (String) filterMap.get("transType");
		PageData<ReportsAccountEntry> pd = null;
		switch (TransType.getTransType(transType)) {
		case P_INVEST_BONUS:
			pd = iReportsAccountEntryService.searchCarAccountEntryDividend(aei,
					page);
			break;
		case C_INVEST_BONUS:
			pd = iReportsAccountEntryService.searchEntAccountEntryDividend(aei,
					page);
			break;
		default:
			break;
		}

		// 查询货币明细
		return pd;
	}*/

    /**
     * 分红账户流水查询
     * 
     * @param filterMap 查询条件
     * @param page 分页信息
     * @return PageData<PointDividend> 分红列表
     * @throws HsException
     * @see com.gy.hsxt.access.web.aps.services.accountCompany.IBalanceService#searchCarAccountEntryDividend(java.util.Map,
     *      com.gy.hsxt.common.bean.Page)
     */
    @Override
    public PageData<PointDividend> searchCarAccountEntryDividend(
            Map filterMap, Map sortMap, Page page) throws HsException {
        int custType = Integer.parseInt((String)filterMap.get("custType")); // 客户类型
        String hsResNo = (String)filterMap.get("hsResNo");                  // 互生号
        String custName = (String)filterMap.get("enterpriseName");          // 客户名称
        String beginYear = (String)filterMap.get("beginYear");              // 开始年份
        String endYear = (String)filterMap.get("endYear");                  // 结束年份
        PageData<PointDividend> pd = bsInvestProfitService.getPlatformDividendList(custType, hsResNo, custName, beginYear, endYear, page);
        // 查询货币明细
        return pd; 
    }

	
    
    
	/**
	 * 投资分红汇总下的流水详情
	 * 
	 * @param transNo
	 * @return
	 */
	@Override
	public PageData<DividendDetail> getDividendDetailList(Map filterMap,
			Map sortMap, Page page) {

		String hsResNo = (String) filterMap.get("hsResNo");
		String year = (String) filterMap.get("year");

		return bsInvestProfitService.getDividendDetailList(hsResNo, year, page);
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
		return ipsQueryService.queryPointDetailSum(bean);
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
		String entResNo = (String) filterMap.get("companyResNo");
		bean.setBatchNo((String) filterMap.get("batchNo"));
		bean.setResNo(entResNo);
		bean.setNumber(page.getCurPage());
		bean.setCount(page.getPageSize());
		return ipsQueryService.queryPointDetail(bean);
	}
}
