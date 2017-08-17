/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.person.controllers.accountManagement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gy.hsxt.ac.bean.AccountBalance;
import com.gy.hsxt.access.web.common.bean.HttpRespEnvelope;
import com.gy.hsxt.access.web.common.constant.ASRespCode;
import com.gy.hsxt.access.web.common.controller.BaseController;
import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.access.web.common.utils.RequestUtil;
import com.gy.hsxt.access.web.person.services.accountManagement.IBalanceService;
import com.gy.hsxt.access.web.person.services.accountManagement.ITransInnerService;
import com.gy.hsxt.access.web.person.services.common.IPubParamService;
import com.gy.hsxt.access.web.person.services.common.PersonConfigService;
import com.gy.hsxt.access.web.person.services.consumer.ICardholderService;
import com.gy.hsxt.access.web.person.services.consumer.INoCardholderService;
import com.gy.hsxt.access.web.person.services.hsc.IBankCardService;
import com.gy.hsxt.access.web.person.services.hsc.ICardHolderAuthInfoService;
import com.gy.hsxt.access.web.person.services.hsc.IImportantInfoChangeService;
import com.gy.hsxt.access.web.person.services.safetyset.IPwdSetService;
import com.gy.hsxt.ao.bean.TransOut;
import com.gy.hsxt.ao.enumtype.TransReason;
import com.gy.hsxt.bp.bean.BusinessCustParamItemsRedis;
import com.gy.hsxt.bp.bean.BusinessSysParamItemsRedis;
import com.gy.hsxt.bp.client.api.BusinessParamSearch;
import com.gy.hsxt.bs.bean.entstatus.PerChangeInfo;
import com.gy.hsxt.bs.common.enumtype.entstatus.ApprStatus;
import com.gy.hsxt.common.constant.AccountType;
import com.gy.hsxt.common.constant.BusinessParam;
import com.gy.hsxt.common.constant.Channel;
import com.gy.hsxt.common.constant.CustType;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.DateUtil;
import com.gy.hsxt.common.utils.DoubleUtil;
import com.gy.hsxt.common.utils.HsAssert;
import com.gy.hsxt.common.utils.StringUtils;
import com.gy.hsxt.gp.constant.GPConstant.GPTransSysFlag;
import com.gy.hsxt.lcs.bean.LocalInfo;
import com.gy.hsxt.uc.as.api.enumerate.UserTypeEnum;
import com.gy.hsxt.uc.as.bean.common.AsBankAcctInfo;
import com.gy.hsxt.uc.as.bean.common.AsNetworkInfo;
import com.gy.hsxt.uc.as.bean.consumer.AsRealNameReg;
import com.gy.hsxt.uc.as.bean.enumerate.CerTypeEnum;

/***************************************************************************
 * <PRE>
 * Description 		: 货币账户控制类
 * 
 * Project Name   	: hsxt-access-web-person 
 * 
 * Package Name     : com.gy.hsxt.access.web.person.controllers.accountManagement  
 * 
 * File Name        : IntegralAccountController 
 * 
 * Author           : LiZhi Peter
 * 
 * Create Date      : 2015-10-26 下午5:22:16
 * 
 * Update User      : LiZhi Peter
 * 
 * Update Date      : 2015-10-26 下午5:22:16
 * 
 * UpdateRemark 	: 说明本次修改内容
 * 
 * Version          : v1.0
 * 
 * </PRE>
 ***************************************************************************/
@Controller
@RequestMapping("currencyAccount")
public class CurrencyAccountController extends BaseController {

	@Resource
	private IBalanceService balanceService; // 账户余额查询服务类

	@Autowired
	private IPubParamService pubParamService;// 平台服务公共信息服务类

    @Resource
    private PersonConfigService personConfigSevice;

	@Resource
	private IBankCardService bankCardService; // 银行卡操作服务类
	@Resource
	private ITransInnerService transInnerService;// 转账操作服务类

	@Resource
	private IImportantInfoChangeService changeService;// 重要信息变更服务类

	@Resource
	private ICardHolderAuthInfoService cardHolderAuthInfoService;// 查询实名注册信息服务类

	@Resource
	private ICardholderService cardholderService;// 持卡人服务类

	@Autowired
	public BusinessParamSearch businessParamSearch;
	/**
	 * 交易密码服务类
	 */
	@Resource
	private IPwdSetService pwdSetService;
	
    @Resource
    private INoCardholderService noCardholderService;//非持卡人服务类

	/**
	 * 用户查询积分账户余额，返回积分账户总余额，可用积分数和今日积分数
	 * 
	 * @param custId
	 *            客户号
	 * @param pointNo
	 *            互生卡号
	 * @param request
	 *            当前请求数据
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/find_currency_blance" }, method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope findCurrencyBlance(String custId, String token, HttpServletRequest request)
	{

		// 变量声明

		Map<String, Object> map = null;
		AccountBalance accBalance = null;
		HttpRespEnvelope hre = null;

		// 执行查询方法
		try
		{
			// Token验证
			super.checkSecureToken(request);

			map = new HashMap<String, Object>();

			// 查询用户查询积分账户余额
			accBalance = balanceService.findAccNormal(custId, AccountType.ACC_TYPE_POINT30110.getCode());
			if (accBalance != null)
			{
				map.put("currencyBlance", accBalance.getAccBalance());
			}

			hre = new HttpRespEnvelope(map);

		} catch (HsException e)
		{
			hre = new HttpRespEnvelope(e);
		}

		return hre;
	}

	/**
	 * 货币转银行界面数据初始化
	 * 
	 * @param custId
	 *            客户号
	 * @param pointNo
	 *            互生卡号
	 * @param request
	 *            当前请求数据
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/init_currency_transfer_bank" }, method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope initCurrencyTransferBank(String custId, String pointNo,String hs_isCard ,  HttpServletRequest request)
	{

		// 变量声明
		LocalInfo lcalInfo = null; // 本地平台信息
		Map<String, Object> map = null; // 返回数据临时结合
		AccountBalance accBalance = null; // 账户余额对象
		HttpRespEnvelope hre = null;
		List<AsBankAcctInfo> bankAcctList = null;// 银行卡集合
		
		// 执行查询方法
		try
		{
			// Token验证
			super.checkSecureToken(request);

			map = new HashMap<String, Object>();

			// 查询积分账户余额
			accBalance = balanceService.findAccNormal(custId, AccountType.ACC_TYPE_POINT30110.getCode());

			// 非空验证
			if (accBalance != null)
			{
				// 保存账户余额
				map.put("pointBlance", accBalance.getAccBalance());
			}

			// 转出货币最小整数！
			map.put("currencyMin", this.personConfigSevice.getMonetaryConvertibleMin());

			// 转银行手续费
			// map.put("bankFee", this.companyConfigService.getCurrencyFee());

			// 获取本平台的信息
			lcalInfo = this.pubParamService.findSystemInfo();

			// 币种
			map.put("currencyCode", lcalInfo.getCurrencyNameCn());

			// 查询用户下绑定的银行卡列表
			bankAcctList = this.bankCardService.findBankAccountList(custId, UserTypeEnum.CARDER.getType());
			map.put("bankList", bankAcctList);
			
			// 获取货币转银行规则参数
	        Map<String, BusinessSysParamItemsRedis> itemMap = businessParamSearch.searchSysParamItemsByGroup(BusinessParam.HB_CHANGE_BANK.getCode());

	        // 获取个人单笔最大转账金额
	        String itemMaxValue = getSysItemsValue(itemMap, BusinessParam.PERSON_SINGLE_TRANSFER_MAX);
	        map.put("transferMaxAmount", itemMaxValue);

	        // 获取单日最大限额
	        String dailyLimitAmt = getSysItemsValue(itemMap, BusinessParam.PERSON_SINGLE_DAILY_TRANSFER_MAX);
	        map.put("dayTransferMaxAmount", dailyLimitAmt);
	        
	        // 获取单日已发生金额
	        BusinessCustParamItemsRedis dailyHadAmtItem = businessParamSearch.searchCustParamItemsByIdKey(custId,BusinessParam.SINGLE_DAILY_HAD_TRANSFER.getCode());
	        // 如果累计金额未过期，比较时以累计金额
	        if (dailyHadAmtItem != null && DateUtil.getCurrentDateNoSign().equals(dailyHadAmtItem.getDueDate())) {
	            map.put("dayTransferAmount", dailyHadAmtItem.getSysItemsValue());
	        } else {
	            map.put("dayTransferAmount", "0.00");
	        }
	        
	        //20160602 peter.li 持卡人选择企业注册时 用户名不加掩码 返回注册证件类型
	        
	        //非卡人
	        if(StringUtils.isNotBlank(hs_isCard) && "1".equals(hs_isCard)){
	        	//始终如是姓名需要添加
	        	map.put("isMask", "true");
	        }
	        else	//持卡人
	        {
	        	// 执行查询
				AsRealNameReg asRealNameReg = cardHolderAuthInfoService.searchRealNameRegInfo(custId);
				
				//当前是企业类型
				if(asRealNameReg!= null && CerTypeEnum.BUSILICENCE.getType().toString().equals(asRealNameReg.getCertype())){
		        	map.put("isMask", "false");
				}
				else
				{
					//姓名需要添加
		        	map.put("isMask", "true");
				}
	        }
	        
			
			//业务限制提示信息
			Map<String,String> infoMap = getRestrictMap(BusinessParam.CASH_TO_BANK, custId);
			
			//非空数据验证
			if(null != infoMap && infoMap.size() > 0){
				map.putAll(infoMap);
			}
			
			hre = new HttpRespEnvelope(map);

		} catch (HsException e)
		{
			hre = new HttpRespEnvelope(e);
		}
		return hre;
	}
	
	/**
     * 获取业务参数中的公共系统参数值,从参数组集合中获取指定参数项
     * 
     * @param itemMap
     *            参数组Map, 其中key为参数项key， value为参数项对象
     * @param itemKey
     *            参数项代码
     * @return
     */
    private String getSysItemsValue(Map<String, BusinessSysParamItemsRedis> itemMap, BusinessParam itemKey) {
        BusinessSysParamItemsRedis item = itemMap.get(itemKey.getCode());
        return item.getSysItemsValue();
    }
	 /**
	  * 增加业务限制提示信息
	  * @param businessParam
	  * @param custId
	  * @return
	  */
	 private Map<String,String> getRestrictMap(BusinessParam businessParam,String custId){
		 Map<String,String> infoMap = new HashMap<>();
			Map<String, BusinessCustParamItemsRedis>  custParamMap = businessParamSearch.searchCustParamItemsByGroup(custId);
			if(null == custParamMap){
				return null;
			}
			BusinessCustParamItemsRedis items = custParamMap.get(businessParam.getCode());
			if(null == items){
				return null;
			}
			String restrictValue = StringUtils.nullToEmpty(items.getSysItemsValue());
			String restrictRemark = StringUtils.nullToEmpty(items.getSettingRemark());
			String restrictName = StringUtils.nullToEmpty(items.getSysItemsKey());
			infoMap.put("restrictValue", restrictValue);
			infoMap.put("restrictRemark", restrictRemark);
			infoMap.put("restrictName", restrictName);
			return infoMap;
		}

	/**
	 * 货币转银行界面数据初始化
	 * 
	 * @param custId
	 *            客户号
	 * @param pointNo
	 *            互生卡号
	 * @param request
	 *            当前请求数据
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/getBankTransFee" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope getBankTransFee(@ModelAttribute TransOut transOut, HttpServletRequest request)
	{

		// 变量声明
		HttpRespEnvelope hre = null;
		// 执行查询方法
		try
		{
			// Token验证
			super.checkSecureToken(request);

			// 非空验证
			RequestUtil.verifyParamsIsNotEmpty(
					new Object[] { transOut.getAmount(), RespCode.PW_AMOUNT_TRANSFERRED_INVALID.getCode(),
							RespCode.PW_AMOUNT_TRANSFERRED_INVALID.getDesc() }, // 交易金额
					new Object[] { transOut.getBankCityNo(), RespCode.PW_REGINFO_CITY_INVALID.getCode(),
							RespCode.PW_REGINFO_CITY_INVALID.getDesc() }, // 收款账户开户城市代码
					new Object[] { transOut.getBankNo(), RespCode.PW_BANK_NO_INVALID.getCode(),
							RespCode.PW_BANK_NO_INVALID.getDesc() } // 收款人开户银行代码
					);

			// 获取手续费
			String ransFee = this.transInnerService.getBankTransFee(transOut, GPTransSysFlag.URGENT);

			hre = new HttpRespEnvelope(ransFee);

		} catch (HsException e)
		{
			hre = new HttpRespEnvelope(e);
		}
		return hre;
	}

	/**
	 * 提交货币转银行信息
	 * 
	 * @param transOut
	 *            货币转银行账户
	 * @param randomToken
	 *            随机token
	 * @param tradePwd
	 *            交易密码
	 * @param accId
	 *            银行id
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/currency_transfer_bank" }, method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope currencyTransferBank(TransOut transOut, String randomToken, String zCustName,String tradePwd, String accId,String hs_isCard,
			HttpServletRequest request)
	{

		// 变量声明
		HttpRespEnvelope hre = null;

		// 执行查询方法
		try
		{
		    //操作真实姓名
		    String reqOptName;
		    
			// Token验证
			super.checkSecureToken(request);
			
			String userType = UserTypeEnum.CARDER.getType();
			int custTypeCode = CustType.PERSON.getCode();
			 //非持卡人
			if(StringUtils.isEmpty(hs_isCard) == false && "undefined".equals(hs_isCard) == false&& "1".equals(hs_isCard) ==true)
	        {
			    //用户真实姓名
			    AsNetworkInfo ani = this.noCardholderService.findNetworkInfoByCustId(transOut.getCustId());
	            
			    reqOptName=ani.getName();       //操作员真实姓名
				userType = UserTypeEnum.NO_CARDER.getType();         
	        	custTypeCode = CustType.NOT_HS_PERSON.getCode();     
	        	transOut.setCustName(reqOptName);
	        	
	        	
	        }else{ //持卡人验证  实名认证与重要信息变更
	            
	            //操作员真实姓名
	            reqOptName=StringUtils.trimToBlank(request.getParameter("userName"));
	            
	        	// 验证是否已经实名认证和在重要信息变更期间
				String authStatus = this.cardHolderAuthInfoService.findAuthStatusByCustId(transOut.getCustId());
				HsAssert.isTrue(StringUtils.isNotBlank(authStatus) && !"1".equals(authStatus),
						ASRespCode.PW_REALNAME_REGISTRATION);
				
				
				boolean imptStatus = this.cardholderService.findImportantInfoChangeStatus(transOut.getCustId());
				if (imptStatus)
				{
					PerChangeInfo info = this.changeService.queryPerChangeByCustId(transOut.getCustId());
					if (null != info)
					{
						HsAssert.isTrue(info.getStatus() == ApprStatus.APPROVAL_REVIEWED.getCode()
								|| info.getStatus() == ApprStatus.REJECTED.getCode(), ASRespCode.PW_INFORMATION_CHANGES_NOT);
					}
				}
	        }
			// 非空数据验证
			RequestUtil.verifyParamsIsNotEmpty(
			        new Object[] { tradePwd, RespCode.PW_TRADEPWD_INVALID},                // 交易密码
					new Object[] { randomToken, RespCode.AS_SECURE_TOKEN_INVALID },        // 随机token
					new Object[] { transOut.getCustId(), RespCode.AS_CUSTID_INVALID },     // 客户号
					new Object[] { accId, RespCode.PW_BANK_ACC_NAME_INVALID },             // 银行账号
					new Object[] { transOut.getAmount(), RespCode.PW_TARGETAMOUNT_NULL },  // 货币金额
					new Object[] { transOut.getCustName(), RespCode.PW_CUSTNAME_INVALID }  // 客户名称
					);

			// 正整数验证
			RequestUtil.verifyPositiveInteger(transOut.getAmount(), RespCode.PW_TARGETAMOUNT_INVALID);

			// 呼叫中心的特殊处理方法
			tradePwd = RequestUtil.encodeBase64String(request, tradePwd);
			
			// 交易密码验证
			pwdSetService.checkTradePwd(transOut.getCustId(), tradePwd, userType, randomToken);

			// 转出最小互生币数
			String itcm = this.personConfigSevice.getMonetaryConvertibleMin();
			if (DoubleUtil.parseDouble(transOut.getAmount()) < DoubleUtil.parseDouble(itcm))
			{
				hre = new HttpRespEnvelope(RespCode.PW_CURRENCY_MIN_INVALID.getCode(),
						RespCode.PW_CURRENCY_MIN_INVALID.getDesc());
				return hre;
			}
			
			// 货币转银行构造相关属性
			transOut.setCustType(custTypeCode); // 持卡人
			//transOut.setReqOptId(transOut.getCustId()); // 操作员编号
			transOut.setReqOptId(transOut.getHsResNo()); // 操作员编号
			transOut.setReqOptName(reqOptName);//操作员名称
			transOut.setChannel(Channel.WEB.getCode()); // 终端渠道
			transOut.setTransReason(TransReason.ACCORD_CASH.getCode());// 主动体现
			// 执行货币转英航
			transInnerService.saveTransOut(transOut, Long.parseLong(accId));

			hre = new HttpRespEnvelope();

		} catch (HsException e)
		{
			hre = new HttpRespEnvelope(e);
		}
		return hre;
	}

	/**
	 * 分页查询的条件约束
	 * 
	 * @param request
	 *            当前请求对象
	 * @param filterMap
	 *            查询条件Map
	 * @param sortMap
	 * @return
	 * @see com.gy.hsxt.access.web.common.controller.BaseController#beforeList(javax.servlet.http.HttpServletRequest,
	 *      java.util.Map, java.util.Map)
	 */
	@Override
	protected HttpRespEnvelope beforeList(HttpServletRequest request, Map filterMap, Map sortMap)
	{
		// 变量声明
		HttpRespEnvelope hre = null;

		// 开始时间于结束时间验证
		RequestUtil.verifyQueryDate(filterMap.get("beginDate").toString(), filterMap.get("endDate").toString());

		// 返回验证信息
		return hre;
	}

	/**
	 * 货币账户明细查询
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/detailed_page" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope searchAccEntryPage(HttpServletRequest request, HttpServletResponse response)
	{
		HttpRespEnvelope hre = null;

		try
		{
			// Token验证
			super.verifySecureToken(request);

			// 分页查询
			request.setAttribute("serviceName", balanceService);
			request.setAttribute("methodName", "searchAccEntryPage");
			hre = super.doList(request, response);

		} catch (HsException e)
		{
			hre = new HttpRespEnvelope(e);
		}

		return hre;
	}

	/**
	 * @return
	 * @see com.gy.hsxt.access.web.common.controller.BaseController#getEntityService()
	 */
	@Override
	protected IBaseService getEntityService()
	{
		return null;
	}
}
