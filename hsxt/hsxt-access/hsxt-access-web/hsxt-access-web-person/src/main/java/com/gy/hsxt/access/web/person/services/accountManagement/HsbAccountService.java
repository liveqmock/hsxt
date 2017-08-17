/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.person.services.accountManagement;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsxt.ac.bean.AccountBalance;
import com.gy.hsxt.access.web.common.constant.ASRespCode;
import com.gy.hsxt.access.web.common.service.BaseServiceImpl;
import com.gy.hsxt.access.web.person.bean.PersonBase;
import com.gy.hsxt.access.web.person.bean.accountManagement.NetPay;
import com.gy.hsxt.access.web.person.bean.accountManagement.PayLimit;
import com.gy.hsxt.access.web.person.services.common.IPubParamService;
import com.gy.hsxt.access.web.person.services.common.PersonConfigService;
import com.gy.hsxt.access.web.person.services.consumer.ICardholderService;
import com.gy.hsxt.access.web.person.services.consumer.INoCardholderService;
import com.gy.hsxt.access.web.person.services.hsc.ICardHolderAuthInfoService;
import com.gy.hsxt.access.web.person.services.safetyset.IPwdSetService;
import com.gy.hsxt.ao.api.IAOExchangeHsbService;
import com.gy.hsxt.ao.api.IAOPaymentService;
import com.gy.hsxt.ao.bean.BuyHsb;
import com.gy.hsxt.ao.bean.HsbToCash;
import com.gy.hsxt.ao.bean.PayInfo;
import com.gy.hsxt.bp.api.IBusinessCustParamService;
import com.gy.hsxt.bp.bean.BusinessCustParamItemsRedis;
import com.gy.hsxt.bp.client.api.BusinessParamSearch;
import com.gy.hsxt.bs.bean.order.Order;
import com.gy.hsxt.bs.common.enumtype.order.OrderChannel;
import com.gy.hsxt.common.constant.AccCategory;
import com.gy.hsxt.common.constant.AccountType;
import com.gy.hsxt.common.constant.BusinessParam;
import com.gy.hsxt.common.constant.Channel;
import com.gy.hsxt.common.constant.CustType;
import com.gy.hsxt.common.constant.PayChannel;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.BigDecimalUtils;
import com.gy.hsxt.common.utils.DateUtil;
import com.gy.hsxt.common.utils.DoubleUtil;
import com.gy.hsxt.common.utils.StringUtils;
import com.gy.hsxt.lcs.bean.LocalInfo;
import com.gy.hsxt.lcs.client.LcsClient;
import com.gy.hsxt.uc.as.api.common.IUCAsPwdService;
import com.gy.hsxt.uc.as.api.enumerate.UserTypeEnum;
import com.gy.hsxt.uc.as.bean.common.AsNetworkInfo;

/***
 * 互生币账户实现类
 * 
 * @Package: com.gy.hsxt.access.web.person.services.accountManagement
 * @ClassName: HsbAccountService
 * @Description: TODO
 * 
 * @author: wangjg
 * @date: 2016-4-14 下午12:28:20
 * @version V1.0
 */
@Service
public class HsbAccountService extends BaseServiceImpl implements IHsbAccountService {

    @Resource
    private IIntegralAccountService integralAccountService; // 用户积分服务类

    @Resource
    private IBalanceService balanceService; // 账户余额查询服务类

    @Resource
    private PersonConfigService personConfigSevice;

    @Autowired
    private ITransInnerService ransInnerService; // 内部转账

    @Resource
    private ICardholderService cardholderService;// 持卡人服务类

    @Resource
    private IOrderService orderService;// 发布订单服务

    @Autowired
    private IPubParamService pubParamService;// 平台服务公共信息服务类

    @Autowired
    private BusinessParamSearch businessParamSearch;// 业务参数服务接口

    @Autowired
    private ICardHolderAuthInfoService iCardHolderAuthInfoService;// 查询实名注册信息服务类

    @Autowired
    private IAOExchangeHsbService iAOExchangeHsbService;// 兑换互生币
    
    @Resource
    private INoCardholderService noCardholderService;//非持卡人服务类
    
    /**
     * 交易密码服务类
     */
    @Resource
    private IPwdSetService pwdSetService;

    @Autowired
    private IUCAsPwdService iUCAsPwdService; // 密码管理

    @Autowired
    private IBusinessCustParamService iBusinessCustParamService;
    
    /** 地区平台配送Service **/
    @Autowired
    private LcsClient lcsClient;

    /** 支付服务 **/
    @Resource
    private IAOPaymentService aoPaymentService;


    /**
     * 查询余额信息
     * 
     * @param personBase
     * @return
     * @throws HsException
     * @see com.gy.hsxt.access.web.person.services.accountManagement.IHsbAccountService#findHsbBlance(com.gy.hsxt.access.web.person.bean.PersonBase)
     */
    @Override
    public Map<String, Object> findHsbBlance(PersonBase personBase) throws HsException {
        AccountBalance accBalance = null;
        Map<String, Object> map = new HashMap<String, Object>();

        // 查询互生币账户余额 流通币与定向消费币
        Map<String, AccountBalance> balanceMap = this.balanceService.searchAccBalanceByAccCategory(personBase
                .getCustId(), AccCategory.ACC_TYPE_HSB.getCode());

        // 非空数据验证
        if (balanceMap != null)
        {
            // 获取流通币
            accBalance = balanceMap.get(AccountType.ACC_TYPE_POINT20110.getCode());// 获取流通币
            if (accBalance != null) {
                map.put("circulationHsb", accBalance.getAccBalance());
            }

            // 获取定向消费
            accBalance = balanceMap.get(AccountType.ACC_TYPE_POINT20120.getCode());
            if (accBalance != null)  {
                map.put("directionalHsb", accBalance.getAccBalance());
            }
        }
        
        return map;
    }

    /**
     * 初始化互生币转货币
     * 
     * @param personBase
     * @return
     * @throws HsException
     * @see com.gy.hsxt.access.web.person.services.accountManagement.IHsbAccountService#initHsbTransferCurrency(com.gy.hsxt.access.web.person.bean.PersonBase)
     */
    @Override
    public Map<String, Object> initHsbTransferCurrency(PersonBase personBase) throws HsException {
        Map<String, Object> map = new HashMap<String, Object>();    // 返回数据临时结合

        // 查询流通币
        AccountBalance accBalance = balanceService.findAccNormal(personBase.getCustId(), AccountType.ACC_TYPE_POINT20110.getCode());
        if (accBalance != null) {
            map.put("circulationHsb", accBalance.getAccBalance());
        }
        // 获取本平台的信息
        LocalInfo lcalInfo = this.pubParamService.findSystemInfo();
        map.put("currencyNameCn", lcalInfo.getCurrencyNameCn());                        // 币种名称
        map.put("exchangeRate", lcalInfo.getExchangeRate());                            // 货币转换比率
        map.put("currencyCode", lcalInfo.getCurrencyCode());                            // 币种
        map.put("currencyFee", personConfigSevice.getHsbConvertibleFee());              // 货币转换费
        map.put("hsbMin", this.personConfigSevice.getHsbCirculationConvertibleMin());   // 转出最小互生币数
        Map<String,String> infoMap = getRestrictMap(BusinessParam.HSB_TO_CASH, personBase.getCustId());
		if(null != infoMap && infoMap.size() > 0){
			map.putAll(infoMap);
		}
        return map;
    }
    
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
     * 互生币转货币
     * @param hsbToCash
     * @param tradePwd
     * @param randomToken
     * @throws HsException 
     * @see com.gy.hsxt.access.web.person.services.accountManagement.IHsbAccountService#hsbTransferCurrency(com.gy.hsxt.ao.bean.HsbToCash, java.lang.String, java.lang.String)
     */
    @Override
    public void hsbTransferCurrency(HsbToCash hsbToCash, String tradePwd, String randomToken,String hs_isCard) throws HsException {

        // 转出最小互生币数
        String itcm = this.personConfigSevice.getHsbCirculationConvertibleMin();
        if (DoubleUtil.parseDouble(hsbToCash.getFromHsbAmt()) < DoubleUtil.parseDouble(itcm)) {
            throw new HsException(RespCode.PW_MIN_NUMBER_INVALID);
        }
        String userType = UserTypeEnum.CARDER.getType();
        int custTypeCode = CustType.PERSON.getCode();
        //持卡人与非持卡人验证  非持卡人不能执行查询状态
        if(StringUtils.isEmpty(hs_isCard) == false && "undefined".equals(hs_isCard) == false&& "1".equals(hs_isCard) ==true)
        {
        	userType = UserTypeEnum.NO_CARDER.getType();
        	custTypeCode =CustType.NOT_HS_PERSON.getCode();
        }
        // 交易密码验证
        pwdSetService.checkTradePwd(hsbToCash.getCustId(), tradePwd, userType, randomToken);

        // 内部转账构造相关属性
        hsbToCash.setCustType(custTypeCode);    // 持卡人
        hsbToCash.setOptCustId(hsbToCash.getCustId());       // 操作员编号
        hsbToCash.setChannel(Channel.WEB.getCode());         // 终端渠道

        // 新增内部转帐记录
        ransInnerService.saveHsbToCash(hsbToCash);
    }

    /**
     * 初始化兑换互生币
     * @param personBase
     * @return
     * @throws HsException 
     * @see com.gy.hsxt.access.web.person.services.accountManagement.IHsbAccountService#initTransferHsb(com.gy.hsxt.access.web.person.bean.PersonBase)
     */
    @Override
    public Map<String, Object> initTransferHsb(String perCustId,String hs_isCard) throws HsException {
        
    	//变量声明
        Map<String, Object> map = new HashMap<String, Object>();      // 返回数据临时结合
        BusinessCustParamItemsRedis business;                         //业务结果
        
        String sysGroupCode = BusinessParam.EXCHANGE_HSB.getCode();                             // 兑换互生币（组）
        String hsbHadBuyAmt = BusinessParam.SINGLE_DAY_HAD_BUY_HSB.getCode();                   // 单日互生币兑换已经发生额枚举Code
        String amountMax = null;   			   // 单日限额
        String regBuyHsbMax =	null;          //个人单笔兑换互生币最大限额枚举Code
        String regBuyHsbMin = 	null;          //个人单笔兑换生币最小限额枚举Code
        String noRegBuyHsbMax = null;    	   //个人单笔兑换互生币最大限额枚举Code
        String noRegBuyHsbMin = null;    	   //个人单笔兑换生币最小限额枚举Code
        
        
        String code = null;        				//单日限额枚举Code
        String authStatus = "1";				//默认非持卡人
        
        //持卡人验证实名注册 非持卡=1  
        //if(hs_isCard == null || "1".equals(hs_isCard) == true)
        if(StringUtils.isEmpty(hs_isCard) == false && "undefined".equals(hs_isCard) == false&& "1".equals(hs_isCard) ==true)
        {
        	//查询网络信息
        	AsNetworkInfo networkInfo = this.noCardholderService.findNetworkInfoByCustId(perCustId);
        	
        	//未填写真实姓名
        	if(StringUtils.isBlank(networkInfo.getName()))
        	{
        		authStatus = "1";	//未填写真实姓名
        		code = BusinessParam.P_UNREAL_NAME_SINGLE_DAY_BUY_HSB_MAX.getCode();	//未填写真实姓名个人单日限额
        	}
        	else //已填写真实姓名
        	{
        		authStatus = "2";	//已填写真实姓名
        		code = BusinessParam.P_REAL_NAME_SINGLE_DAY_BUY_HSB_MAX.getCode(); 	//已填写真实姓名个人单日限额
        	}
        	
        	regBuyHsbMax = BusinessParam.P_REAL_NAME_SINGLE_BUY_HSB_MAX.getCode();          // 已实名注册个人单笔兑换互生币最大限额枚举Code
            regBuyHsbMin = BusinessParam.P_REAL_NAME_SINGLE_BUY_HSB_MIN.getCode();          // 已实名注册个人单笔兑换生币最小限额枚举Code
            noRegBuyHsbMax = BusinessParam.P_UNREAL_NAME_SINGLE_BUY_HSB_MAX.getCode();    // 未实名注册个人单笔兑换互生币最大限额枚举Code
            noRegBuyHsbMin = BusinessParam.P_UNREAL_NAME_SINGLE_BUY_HSB_MIN.getCode();    // 未实名注册个人单笔兑换生币最小限额枚举Code
        	
        	
        }
        else //持卡人
        {
        	// 执行查询 (1：未实名注册、2：已实名注册（有名字和身份证）、3:已实名认证)
            authStatus = iCardHolderAuthInfoService.findAuthStatusByCustId(perCustId);
            // 判断是否实名注册
            if ("1".equals(authStatus)) {
                code = BusinessParam.P_NOT_REGISTERED_SINGLE_DAY_BUY_HSB_MAX.getCode();
            } else {
                code = BusinessParam.P_REGISTERED_SINGLE_DAY_BUY_HSB_MAX.getCode();
            }
            regBuyHsbMax = BusinessParam.P_REGISTERED_SINGLE_BUY_HSB_MAX.getCode();          // 已实名注册个人单笔兑换互生币最大限额枚举Code
            regBuyHsbMin = BusinessParam.P_REGISTERED_SINGLE_BUY_HSB_MIN.getCode();          // 已实名注册个人单笔兑换生币最小限额枚举Code
            noRegBuyHsbMax = BusinessParam.P_NOT_REGISTERED_SINGLE_BUY_HSB_MAX.getCode();    // 未实名注册个人单笔兑换互生币最大限额枚举Code
            noRegBuyHsbMin = BusinessParam.P_NOT_REGISTERED_SINGLE_BUY_HSB_MIN.getCode();    // 未实名注册个人单笔兑换生币最小限额枚举Code
        }
        
       
        
        
        //获取互生币单日限额
        business = businessParamSearch.searchCustParamItemsByIdKey(perCustId, sysGroupCode, code);
        amountMax = business == null ? "0" : business.getSysItemsValue();
        map.put("amountMax", amountMax);
        map.put("authStatus", authStatus);

        // 获取单日互生币兑换已经发生额总数
        business = businessParamSearch.searchCustParamItemsByIdKey(perCustId, hsbHadBuyAmt);
        String sysItemsValue = business.getSysItemsValue();   
        
        // 当前发生金额是否是当前日期的判断，如果不是，则为0
        String curDateStr = DateUtil.DateToString(new Date(),"yyyyMMdd"); // 当天时间
        String dueDate = business.getDueDate();
        if (StringUtils.isEmpty(sysItemsValue) || !dueDate.equals(curDateStr)) {
            sysItemsValue = "0";
        }
        
        // 当日还可兑换互生币总额
        BigDecimal amountAvailable = BigDecimalUtils.ceilingSub(amountMax, sysItemsValue);
        map.put("amountAvailable", amountAvailable);
       
        //已实名注册个人单笔兑换互生币最大限额
        business = businessParamSearch.searchCustParamItemsByIdKey(perCustId, sysGroupCode, regBuyHsbMax);
        map.put("regBuyHsbMax", business == null ? 0 : business.getSysItemsValue());

        //已实名注册个人单笔兑换生币最小限额
        business = businessParamSearch.searchCustParamItemsByIdKey(perCustId, sysGroupCode, regBuyHsbMin);
        map.put("regBuyHsbMin", business == null ? 0 : business.getSysItemsValue());

        //未实名注册个人单笔兑换互生币最大限额
        business = businessParamSearch.searchCustParamItemsByIdKey(perCustId, sysGroupCode, noRegBuyHsbMax);
        map.put("noRegBuyHsbMax", business == null ? 0 : business.getSysItemsValue());

        //未实名注册个人单笔兑换生币最小限额
        business = businessParamSearch.searchCustParamItemsByIdKey(perCustId, sysGroupCode, noRegBuyHsbMin);
        map.put("noRegBuyHsbMin", business == null ? 0 : business.getSysItemsValue());

        //平台信息
        LocalInfo lcalInfo = pubParamService.findSystemInfo();  
        map.put("exchangeRate", lcalInfo.getExchangeRate());     // 货币转转换比率
        map.put("currencyCode", lcalInfo.getCurrencyCode());     // 币种
        map.put("currencyNameCn", lcalInfo.getCurrencyNameCn()); // 币种名称
        Map<String,String> infoMap = getRestrictMap(BusinessParam.BUY_HSB, perCustId);
		if(null != infoMap && infoMap.size() > 0){
			map.putAll(infoMap);
		}
        return map;
    }
    
    public static void main(String[] args) {
        // 当天时间
        Date curDate = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        String curDateStr = formatter.format(curDate);

        String a = DateUtil.DateToString(new Date(),"yyyyMMdd");

        System.out.println("curDateStr:" + curDateStr + "===========a:" + a);
    }

    /**
     * 兑换互生币提交数据
     * @param order
     * @param tradePwd
     * @param randomToken
     * @return
     * @throws HsException 
     * @see com.gy.hsxt.access.web.person.services.accountManagement.IHsbAccountService#transferHsb(com.gy.hsxt.bs.bean.order.Order, java.lang.String, java.lang.String)
     */
    @Override
    public String transferHsb(Order order) throws HsException {

        // 封装添加的兑换互生币订单信息
        BuyHsb buyHsb = new BuyHsb();
        buyHsb.setCustId(order.getCustId());
        buyHsb.setCustName(order.getCustName());
        buyHsb.setHsResNo(order.getHsResNo());
        buyHsb.setCustType(order.getCustType());
        buyHsb.setBuyHsbAmt(order.getOrderHsbAmount());
        buyHsb.setOptCustId(order.getOrderOperator());
        buyHsb.setChannel(OrderChannel.WEB.getCode());
        buyHsb.setRemark("兑换互生币");

        String transNo = iAOExchangeHsbService.saveBuyHsb(buyHsb);
        return transNo;
    }

    /**
     * 支付限额设置查询
     * @param personBase
     * @param entResType
     * @return
     * @throws HsException 
     * @see com.gy.hsxt.access.web.person.services.accountManagement.IHsbAccountService#payLimitSetting(com.gy.hsxt.access.web.person.bean.PersonBase, java.lang.String)
     */
    @Override
    public Map<String, Object> payLimitSetting(PersonBase personBase) throws HsException {
        Map<String, Object> map = new HashMap<String, Object>();                // 返回结果
        Map<String, Object> singleQuotaMap = new HashMap<String, Object>();     // 单笔支付限额
        Map<String, Object> dayQuotaMap = new HashMap<String, Object>();        // 每日支付限额

        String code = BusinessParam.HSB_PAYMENT.getCode();                      // 互生币支付code
        String codeMin = BusinessParam.CONSUMER_PAYMENT_MAX.getCode();          // 消费者互生币支付单笔限额code
        String codeDayMax = BusinessParam.CONSUMER_PAYMENT_DAY_MAX.getCode();   // 消费者互生币支付当日限额code

        // 消费者互生币支付单笔限额
        BusinessCustParamItemsRedis business = businessParamSearch.searchCustParamItemsByIdKey(personBase.getCustId(), code, codeMin);
        if (null != business) {
            singleQuotaMap.put("singleQuotaSwitch", StringUtils.trimToBlank(business.getIsActive()));   // 单笔限额开关
            singleQuotaMap.put("singleQuota", StringUtils.trimToBlank(business.getSysItemsValue()));    // 单笔限额值
        }

        // 消费者互生币支付当日限额
        business = businessParamSearch.searchCustParamItemsByIdKey(personBase.getCustId(), code, codeDayMax);
        if (null != business) {
            dayQuotaMap.put("dayQuotaSwitch", StringUtils.trimToBlank(business.getIsActive()));         // 每日限额开关
            dayQuotaMap.put("dayQuota", StringUtils.trimToBlank(business.getSysItemsValue()));          // 单笔限额值
        }

        map.put("aounmtMinByTime", singleQuotaMap);
        map.put("amountMaxByDay", dayQuotaMap);

        return map;
    }

    /**
     * 支付限额修改
     * @param payLimit
     * @throws HsException 
     * @see com.gy.hsxt.access.web.person.services.accountManagement.IHsbAccountService#payLimitUpdate(com.gy.hsxt.access.web.person.bean.accountManagement.PayLimit)
     */
    @Override
    public void payLimitUpdate(PayLimit payLimit,String hs_isCard) throws HsException {
        
        String singleQuotaReCode=BusinessParam.CONSUMER_PAYMENT_MAX.getCode();                          //单笔支付限额code
        String dayQuotaReCode=BusinessParam.CONSUMER_PAYMENT_DAY_MAX.getCode();                         //每日支付限额code
        List<BusinessCustParamItemsRedis> payLimitList = new ArrayList<BusinessCustParamItemsRedis>();  //支付限额集合
        
        String userType = UserTypeEnum.CARDER.getType();//持卡人
        
        //非持卡人验证  
        if(StringUtils.isEmpty(hs_isCard) == false && "undefined".equals(hs_isCard) == false&& "1".equals(hs_isCard) ==true)
        {
        	userType = UserTypeEnum.NO_CARDER.getType();
        }
        // 校验支付密码
        iUCAsPwdService.checkTradePwd(payLimit.getCustId(), payLimit.getTradePwd(), userType, payLimit.getRandomToken());
        
        //创建单笔支付限额对象
        payLimitList.add(this.createBCPIR(payLimit, singleQuotaReCode));
        
        //创建每日支付限额对象
        payLimitList.add(this.createBCPIR(payLimit, dayQuotaReCode));

        //设置支付限额提交
        iBusinessCustParamService.addOrUpdateCustParamItemsList(payLimit.getCustId(), payLimitList);
    }
    
    /**
     * 创建支付限额实体
     * @param payLimit
     * @param key
     * @param value
     * @param keyDesc
     * @return
     */
    private BusinessCustParamItemsRedis createBCPIR(PayLimit payLimit, String key) {
        BusinessCustParamItemsRedis bcpir = new BusinessCustParamItemsRedis();

        bcpir.setHsResNo(payLimit.getPointNo());                        //消费者互生号
        bcpir.setCustName(payLimit.getCustName());                      //消费者名称
        bcpir.setSysGroupCode(BusinessParam.HSB_PAYMENT.getCode());     //互生币支付

        //支付限额类型
        if (key == BusinessParam.CONSUMER_PAYMENT_MAX.getCode()) { //单笔支付限额
            
            bcpir.setSysItemsKey(BusinessParam.CONSUMER_PAYMENT_MAX.getCode());     //单笔限额类型
            bcpir.setIsActive(payLimit.getSingleQuotaReSwitch());                   //单笔限额开关
            bcpir.setSysItemsValue(payLimit.getSingleQuotaRe());                    //单笔限额值
            bcpir.setSysItemsName("消费者互生币支付单笔限额");                       //单笔描述
        }
        else if (key == BusinessParam.CONSUMER_PAYMENT_DAY_MAX.getCode()) { //每日支付限额
            
            bcpir.setSysItemsKey(BusinessParam.CONSUMER_PAYMENT_DAY_MAX.getCode()); //每日限额类型
            bcpir.setIsActive(payLimit.getDayQuotaReSwitch());                      //每日限额开关
            bcpir.setSysItemsValue(payLimit.getDayQuota());                         //每日限额值
            bcpir.setSysItemsName("消费者互生币支付当日限额");                       //每日描述
        }
        
        return bcpir;
    }
    
    /**
     * 兑换互生币支付
     * @param netPay
     * @return
     * @throws HsException 
     * @see com.gy.hsxt.access.web.person.services.accountManagement.IHsbAccountService#convertHSBPay(com.gy.hsxt.access.web.person.bean.accountManagement.NetPay)
     */
    @Override
    public String convertHSBPay(NetPay netPay) throws HsException {
        // 地区平台信息
        LocalInfo info = lcsClient.getLocalInfo();

        // 构造支付参数对象
        PayInfo payInfo = new PayInfo();
        payInfo.setPayChannel(netPay.getPayChannel());
        payInfo.setTransNo(netPay.getTransNo());
        payInfo.setCallbackUrl(info.getWebPayJumpUrl());
        payInfo.setGoodsName(netPay.getGoodsName());

        //银行卡支付
        if (netPay.getPayChannel() == PayChannel.CARD_PAY.getCode()) {
            return aoPaymentService.getPayUrl(payInfo);
        } else {
            throw new HsException(ASRespCode.AS_PAY_CHANNEL_ERROR);
        }
    }

}
