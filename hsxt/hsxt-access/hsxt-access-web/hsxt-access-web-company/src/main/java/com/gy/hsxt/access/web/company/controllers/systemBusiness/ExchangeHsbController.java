/**
 * 
 */
package com.gy.hsxt.access.web.company.controllers.systemBusiness;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.gy.hsxt.ac.bean.AccountBalance;
import com.gy.hsxt.access.web.common.bean.HttpRespEnvelope;
import com.gy.hsxt.access.web.common.constant.ASRespCode;
import com.gy.hsxt.access.web.common.controller.BaseController;
import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.access.web.common.utils.RequestUtil;
import com.gy.hsxt.access.web.company.services.accountManagement.IBalanceService;
import com.gy.hsxt.access.web.company.services.common.CompanyConfigService;
import com.gy.hsxt.access.web.company.services.systemBusiness.IExchangeHsbService;
import com.gy.hsxt.ao.bean.ProxyBuyHsb;
import com.gy.hsxt.bp.bean.BusinessCustParamItemsRedis;
import com.gy.hsxt.bp.client.api.BusinessParamSearch;
import com.gy.hsxt.common.constant.AccountType;
import com.gy.hsxt.common.constant.BusinessParam;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.BigDecimalUtils;
import com.gy.hsxt.common.utils.DateUtil;
import com.gy.hsxt.common.utils.HsAssert;
import com.gy.hsxt.common.utils.StringUtils;
import com.gy.hsxt.lcs.bean.LocalInfo;
import com.gy.hsxt.lcs.client.LcsClient;
import com.gy.hsxt.uc.as.api.consumer.IUCAsCardHolderAuthInfoService;

/**
 * @author weiyq
 * 
 */
@Controller
@RequestMapping("exchangeHsb")
public class ExchangeHsbController extends BaseController {

	@Resource
	private IBalanceService balanceService; // 账户余额查询服务类

	@Resource
	private IExchangeHsbService exangeHsbService; // 账户余额查询服务类

	@Autowired
	private LcsClient lcsClient; // 全局配置服务接口

	@Autowired
	private IUCAsCardHolderAuthInfoService ucCardHolderAuthInfoService;// 查询实名注册信息服务类

	@Autowired
	private BusinessParamSearch businessParamSearch;// 业务参数服务接口

	@Autowired
	private CompanyConfigService companyConfigService; // 全局配置文件

	@ResponseBody
	@RequestMapping(value = { "/find_exchangeHsb_info" }, method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope findAccBal(String entCustId) {
		try {
			// 非空数据验证
			RequestUtil.verifyParamsIsNotEmpty(new Object[] { entCustId,
					RespCode.AS_ENT_CUSTID_INVALID.getCode(),
					RespCode.AS_ENT_CUSTID_INVALID.getDesc() } // 企业客户号
					);
			AccountBalance bal = balanceService.findAccNormal(entCustId,
					AccountType.ACC_TYPE_POINT20110.getCode());
			LocalInfo info = lcsClient.getLocalInfo();
			Map<String, String> map = new HashMap<String, String>();
			map.put("bal", null == bal ? "0" : bal.getAccBalance());
			map.put("rate", info.getExchangeRate());

			String sysGroupCode = BusinessParam.EXCHANGE_HSB.getCode(); // 兑换互生币（组）
			String regBuyHsbMax = BusinessParam.P_REGISTERED_SINGLE_BUY_HSB_MAX
					.getCode(); // 已实名注册个人单笔兑换互生币最大限额枚举Code
			String regBuyHsbMin = BusinessParam.P_REGISTERED_SINGLE_BUY_HSB_MIN
					.getCode(); // 已实名注册个人单笔兑换生币最小限额枚举Code
			String noRegBuyHsbMax = BusinessParam.P_NOT_REGISTERED_SINGLE_BUY_HSB_MAX
					.getCode(); // 未实名注册个人单笔兑换互生币最大限额枚举Code
			String noRegBuyHsbMin = BusinessParam.P_NOT_REGISTERED_SINGLE_BUY_HSB_MIN
					.getCode(); // 未实名注册个人单笔兑换生币最小限额枚举Code

			Map<String, BusinessCustParamItemsRedis> paramGroup = businessParamSearch
					.searchCustParamItemsByGroup(sysGroupCode);
			// 已实名注册个人单笔兑换生币最大限额
			map.put("regBuyHsbMax", paramGroup.get(regBuyHsbMax)
					.getSysItemsValue());
			// 已实名注册个人单笔兑换生币最小限额
			map.put("regBuyHsbMin", paramGroup.get(regBuyHsbMin)
					.getSysItemsValue());
			// 未实名注册个人单笔兑换互生币最大限额
			map.put("noRegBuyHsbMax", paramGroup.get(noRegBuyHsbMax)
					.getSysItemsValue());
			// 未实名注册个人单笔兑换生币最小限额
			map.put("noRegBuyHsbMin", paramGroup.get(noRegBuyHsbMin)
					.getSysItemsValue());
			return new HttpRespEnvelope(map);
		} catch (Exception e) {
			return new HttpRespEnvelope(e);
		}
	}

	@ResponseBody
	@RequestMapping(value = { "/getCardHolderDayLimit" }, method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope getCardHolderDayLimit(HttpServletRequest request,
			String cardNo, String entCustId) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 实名认证状态
		String authStatus = null;
		// 兑换互生币（组）
		String sysGroupCode = BusinessParam.EXCHANGE_HSB.getCode();
		// 每日最大数量代码
		String dayCode = null;
		try {

			String perCustId = exangeHsbService.findCustIdByResNo(cardNo);
			// 执行查询 (1：未实名注册、2：已实名注册（有名字和身份证）、3:已实名认证)
			authStatus = ucCardHolderAuthInfoService
					.findAuthStatusByCustId(perCustId);
			// 判断是否实名注册
			if ("1".equals(authStatus)) {
				// 未实名注册个人每日兑换互生币最大限额枚举Code
				dayCode = BusinessParam.P_NOT_REGISTERED_SINGLE_DAY_BUY_HSB_MAX
						.getCode();
			} else {
				// 已实名注册个人每日兑换互生币最大限额枚举Code
				dayCode = BusinessParam.P_REGISTERED_SINGLE_DAY_BUY_HSB_MAX
						.getCode();
			}

			BusinessCustParamItemsRedis business; // 业务结果
			// 获取互生币单日限额
			business = businessParamSearch.searchCustParamItemsByIdKey(
					perCustId, sysGroupCode, dayCode);
			// 每日最大数量
			String amountMax = business == null ? "0" : business
					.getSysItemsValue();
			map.put("amountMax", amountMax);
			map.put("authStatus", authStatus);

			// 当日持卡人还可兑换互生币总额
			String amountAvailable = this.getCardHolderAmountAvailable(
					perCustId, amountMax);
			map.put("amountAvailable", amountAvailable);
			return new HttpRespEnvelope(map);
		} catch (HsException ex) {
			return new HttpRespEnvelope(ex);
		}

	}

	/**
	 * 查询互生号信息
	 * 
	 * @param request
	 * @param cardNo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/getCardHolderInfo" }, method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope getCardHolderDayLimit(HttpServletRequest request,
			String cardNo) {

		try {
			// 判断互生号是否存在
			String perCustId = exangeHsbService.findCustIdByResNo(cardNo);
			return new HttpRespEnvelope(perCustId);
		} catch (HsException ex) {
			return new HttpRespEnvelope(ex);
		}

	}

	/**
	 * 判断该互生号是否可以兑换当前金额
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/getExchangeHsbValidate" }, method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope getExchangeHsbValidate(HttpServletRequest request) {

		try {
			// 判断互生号是否存在

			String cardNo = request.getParameter("cardNo"); // 互生号
			String exchangeAmount = request.getParameter("exchangeAmount"); // 当前代兑互生币金额
			String entCustId = request.getParameter("entCustId"); // 当前企业ID
			RequestUtil.verifyParamsIsNotEmpty(
					new Object[] { cardNo,ASRespCode.EW_EXCHANGE_HSB_PERRESNO.getCode(),ASRespCode.EW_EXCHANGE_HSB_PERRESNO.getDesc() },//卡号不能为空
					new Object[] { exchangeAmount,ASRespCode.EW_EXCHANGE_HSB_CASHAMT.getCode(),ASRespCode.EW_EXCHANGE_HSB_CASHAMT.getDesc()}//金额不能为空
					);
			Map map = exangeHsbService.getExchangeHsbValidate(cardNo,
					exchangeAmount, entCustId);
			return new HttpRespEnvelope(map);
		} catch (HsException ex) {
			return new HttpRespEnvelope(ex);
		}

	}

	private String getCardHolderAmountAvailable(String perCustId,
			String amountMax) {
		// 每日互生币兑换已经发生数量Code
		String dayBuyCountCode = BusinessParam.SINGLE_DAY_HAD_BUY_HSB.getCode();
		// 获取单日互生币兑换已经发生额总数
		BusinessCustParamItemsRedis business = businessParamSearch
				.searchCustParamItemsByIdKey(perCustId, dayBuyCountCode);
		String sysItemsValue = business.getSysItemsValue();

		// 当前发生金额是否是当前日期的判断，如果不是，则为0
		String curDateStr = DateUtil.DateToString(new Date(), "yyyyMMdd"); // 当天时间
		String dueDate = business.getDueDate();
		if (StringUtils.isEmpty(sysItemsValue) || !dueDate.equals(curDateStr)) {
			sysItemsValue = "0";
		}

		// 当日持卡人还可兑换互生币总额
		BigDecimal amountAvailable = BigDecimalUtils.ceilingSub(amountMax,
				sysItemsValue);
		return amountAvailable.toPlainString();
	}

	private String getCompanyHsb(String entCustId) {
		// 查询流通币账户
		AccountBalance bal = balanceService.findAccNormal(entCustId,
				AccountType.ACC_TYPE_POINT20110.getCode());
		// 流通币账户余额
		String acctBalance = StringUtils.isBlank(bal) ? "0" : bal
				.getAccBalance();

		return acctBalance;
	}

	@ResponseBody
	@RequestMapping(value = { "/exchange" }, method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope exchangeHsb(HttpServletRequest request,
			ProxyBuyHsb pbh) {
		try {
			String custEntName = request.getParameter("custEntName");
			String entCustType = request.getParameter("entCustType");
			String optCustId = request.getParameter("custId");
			String entCustId = request.getParameter("entCustId");
			pbh.setEntCustName(custEntName);
			pbh.setEntCustType(Integer.parseInt(entCustType));
			pbh.setOptCustId(optCustId);

			String perCustId = exangeHsbService.findCustIdByResNo(pbh
					.getPerResNo());
			pbh.setPerCustId(perCustId);
			// 验证企业代兑互生的限额
			vaildPersonBuyHsbCount(pbh.getCashAmt(), perCustId, entCustId);

			exangeHsbService.exchangeHsb(pbh);

		} catch (HsException ex) {
			return new HttpRespEnvelope(ex);
		} catch (Exception e) {
			return new HttpRespEnvelope(e);
		}
		return new HttpRespEnvelope();
	}

	/**
	 * 验证消费者购买互生币的数量
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年3月17日 下午7:55:13
	 * @param buyAmount
	 *            数量
	 * @param preCustId
	 *            个人客户号
	 * @param entCustId
	 *            企业客户号
	 * @throws HsException
	 * @return : void
	 * @version V3.0.0
	 */
	private void vaildPersonBuyHsbCount(String buyAmount, String preCustId,
			String entCustId) throws HsException {
		// 参数
		BusinessCustParamItemsRedis param = null;
		// 实名认证状态
		String authStatus = null;
		// 兑换互生币（组）
		String sysGroupCode = BusinessParam.EXCHANGE_HSB.getCode();
		// 每日最大数量代码
		String dayCode = null;
		// 每日最大数量
		String dayCount = null;
		// 每日单笔最大数量代码
		String daySingleMaxCode = null;
		// 每日单笔最大数量
		String daySingleMaxCount = null;
		// 每日单笔最小数量代码
		String daySingleMinCode = null;
		// 每日单笔最小数量
		String daySingleMinCount = null;
		try {
			// 企业可兑换互生币
			String entExchangeHsb = this.getCompanyHsb(entCustId);
			// 判断账户余额是否够
			HsAssert.isTrue(
					BigDecimalUtils.compareTo(entExchangeHsb, buyAmount) >= 0,
					ASRespCode.EW_EXCHANG_COUNT_THAN_ENT_ACCT_BALANCE);

			// 执行查询 (1：未实名注册、2：已实名注册（有名字和身份证）、3:已实名认证)
			authStatus = ucCardHolderAuthInfoService
					.findAuthStatusByCustId(preCustId);
			// 判断是否实名注册
			if ("1".equals(authStatus)) {
				// 未实名注册个人每日兑换互生币最大限额枚举Code
				dayCode = BusinessParam.P_NOT_REGISTERED_SINGLE_DAY_BUY_HSB_MAX
						.getCode();
				// 未实名注册个人每日单笔兑换互生币最大限额枚举Code
				daySingleMaxCode = BusinessParam.P_NOT_REGISTERED_SINGLE_BUY_HSB_MAX
						.getCode();
				// 未实名注册个人每日兑换生币最小限额枚举Code
				daySingleMinCode = BusinessParam.P_NOT_REGISTERED_SINGLE_BUY_HSB_MIN
						.getCode();
			} else {
				// 已实名注册个人每日兑换互生币最大限额枚举Code
				dayCode = BusinessParam.P_REGISTERED_SINGLE_DAY_BUY_HSB_MAX
						.getCode();
				// 已实名注册个人每日单笔兑换互生币最大限额枚举Code
				daySingleMaxCode = BusinessParam.P_REGISTERED_SINGLE_BUY_HSB_MAX
						.getCode();
				// 已实名注册个人每日单笔兑换生币最小限额枚举Code
				daySingleMinCode = BusinessParam.P_REGISTERED_SINGLE_BUY_HSB_MIN
						.getCode();
			}

			// 每日单笔最大限额
			param = businessParamSearch.searchCustParamItemsByIdKey(preCustId,
					sysGroupCode, daySingleMaxCode);
			daySingleMaxCount = StringUtils.isBlank(param) ? "0" : param
					.getSysItemsValue();
			HsAssert.isTrue(
					BigDecimalUtils.compareTo(daySingleMaxCount, buyAmount) >= 0,
					ASRespCode.EW_EXCHANG_COUNT_THAN_DAY_SINGLE_MAX_COUNT);

			// 每日单笔最小限额
			param = businessParamSearch.searchCustParamItemsByIdKey(preCustId,
					sysGroupCode, daySingleMinCode);
			daySingleMinCount = StringUtils.isBlank(param) ? "0" : param
					.getSysItemsValue();
			HsAssert.isTrue(
					BigDecimalUtils.compareTo(buyAmount, daySingleMinCount) >= 0,
					ASRespCode.EW_EXCHANG_COUNT_THAN_DAY_SINGLE_MIN_COUNT);

			// 每日最大限额
			param = businessParamSearch.searchCustParamItemsByIdKey(preCustId,
					sysGroupCode, dayCode);
			dayCount = StringUtils.isBlank(param) ? "0" : param
					.getSysItemsValue();
			HsAssert.isTrue(
					BigDecimalUtils.compareTo(dayCount, buyAmount) >= 0,
					ASRespCode.EW_EXCHANG_COUNT_THAN_DAY_MAX_COUNT);

			// 当日持卡人还可兑换互生币总额
			String amountAvailable = this.getCardHolderAmountAvailable(
					preCustId, dayCount);
			HsAssert.isTrue(
					BigDecimalUtils.compareTo(amountAvailable, buyAmount) >= 0,
					ASRespCode.EW_EXCHANG_COUNT_THAN_DAY_MAY_COUNT);

		} catch (HsException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new HsException(ASRespCode.AS_DUBBO_INVOKE_ERROR,
					ex.getMessage());
		}
	}

	@ResponseBody
	@RequestMapping(value = { "/find_custid_by_resno" }, method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope findCustIdByResNo(String perResNo) {
		try {
			String perCustId = exangeHsbService.findCustIdByResNo(perResNo);
			return new HttpRespEnvelope(perCustId);
		} catch (Exception e) {
			return new HttpRespEnvelope(e);
		}
	}

	@Override
	protected IBaseService getEntityService() {
		// TODO Auto-generated method stub
		return null;
	}

}
