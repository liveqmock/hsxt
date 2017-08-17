/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.es.points.service;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.bp.bean.BusinessCustParamItemsRedis;
import com.gy.hsxt.bp.client.api.BusinessParamSearch;
import com.gy.hsxt.common.constant.BusinessParam;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.DateUtil;
import com.gy.hsxt.es.common.Compute;
import com.gy.hsxt.es.common.EsTools;

/**
 * @Package: com.gy.hsxt.ps.points.service
 * @ClassName: AccountQuotaService
 * @Description: 互生币支付限额
 * 
 * @author: chenhz
 * @date: 2015-12-30 下午2:35:53
 * @version V3.0
 */

@Service
public class AccountQuotaService
{
	/**
	 * 业务参数系统配置服务
	 */
	@Autowired
	private BusinessParamSearch businessParamSearch;

	// 当天日期
	public static final String today = DateUtil
			.DateToString(DateUtil.today(), DateUtil.DATE_FORMAT);

	/**
	 * 消费者支付限额检查
	 * 
	 * @param custId
	 *            消费者客户号
	 * @param amount
	 *            消费者支付金额
	 * @return
	 * @throws HsException
	 * @see com.gy.hsxt.ps.points.service.IAccountQuotaService#checkConsumerQuota()
	 */
	public boolean checkCustQuota(String custId, String amount) throws HsException
	{
	    BigDecimal payAmount = new BigDecimal(amount);
	    Map<String,BusinessCustParamItemsRedis> custParamMap = businessParamSearch.searchCustParamItemsByGroup(custId);
		// 获取单笔限额参数
		BusinessCustParamItemsRedis singleQuotaParam = custParamMap.get(BusinessParam.CONSUMER_PAYMENT_MAX.getCode());
		
		BigDecimal singleQuota = null;
		
		if(singleQuotaParam != null && "Y".equals(singleQuotaParam.getIsActive()))
		{
		    singleQuota = new BigDecimal(singleQuotaParam.getSysItemsValue());
		}
		
		// 获取每日限额参数
		BusinessCustParamItemsRedis dailyQuotaParam = custParamMap.get(BusinessParam.CONSUMER_PAYMENT_DAY_MAX.getCode());
		
		BigDecimal dailyQuota = null;
		
		if(dailyQuotaParam != null && "Y".equals(dailyQuotaParam.getIsActive()))
        {
		    dailyQuota = new BigDecimal(dailyQuotaParam.getSysItemsValue());
        }
		        

//		// 获取互生币支付单日已经交易次数
//		BusinessCustParamItemsRedis dailyFrequencyParam = businessParamSearch
//				.searchCustParamItemsByIdKey(custId, BusinessParam.HSB_PAYMENT.getCode(),
//						BusinessParam.CONSUMER_PAYMENT_DAY_NUMBER.getCode());
//		int dailyFrequency = Integer.parseInt(dailyFrequencyParam.getSysItemsValue());

		// 互生币支付单笔限额校验
		if (singleQuota != null && payAmount.compareTo(singleQuota) == 1)
		{
			throw new HsException(RespCode.PS_HSB_CONSUMER_PAYMENT_MAX.getCode(), "互生币支付单笔金额已超限");
		}

		// 获得当天日期
		// String today = DateUtil.DateToString(DateUtil.today(),
		// DateUtil.DATE_FORMAT);

		/**
		 * 业务描述判断更新日期是否是当天,是当天的日期就验证金额是否超限,支付次数是否超限.
		 * 否则更新日期不是当天的,就是第二天初次验证未恢复额度情况下放行不拦截, 同时在下面的处理上更新额度、次数
		 */
		
		if (!EsTools.isEmpty(dailyQuota) && dailyQuota.compareTo(BigDecimal.valueOf(0)) == 1)
        {
            // 获取当日已支付的信息
            BusinessCustParamItemsRedis hadQuotaParam = businessParamSearch.searchCustParamItemsByIdKey(custId,BusinessParam.HAD_PAYMENT_DAY.getCode());
            
            // 消费者互生币已支付额度
            String dailyAlreadyAmount = hadQuotaParam == null?"0":hadQuotaParam.getSysItemsValue();

            if (payAmount.compareTo(dailyQuota) == 1)
            {
                throw new HsException(RespCode.PS_HSB_CONSUMER_PAYMENT_DAY_MAX.getCode(), "互生币单日支付金额已超限");
            }

            if (!EsTools.isEmpty(hadQuotaParam.getDueDate()))
            {
                if (hadQuotaParam.getDueDate().equals(today))
                {
                    // 判断当天支付金额已超限
                    if (!EsTools.isEmpty(dailyAlreadyAmount))
                    {
                        BigDecimal alreadyAmount = Compute.add(new BigDecimal(dailyAlreadyAmount),
                                payAmount);
                        if (alreadyAmount.compareTo(dailyQuota) == 1)
                        {
                            throw new HsException(RespCode.PS_HSB_CONSUMER_PAYMENT_DAY_MAX.getCode(), "互生币单日支付金额已超限");
                        }
                    }
                }
            }
        }

		/*if (dailyQuotaParam != null && !EsTools.isEmpty(dailyQuotaParam.getDueDate()))
		{
			if (dailyQuotaParam.getDueDate().equals(today))
			{
				// 消费者互生币已支付额度
				String dailyAlreadyAmount = businessParamSearch.searchCustParamItemsByIdKey(custId,
						BusinessParam.HAD_PAYMENT_DAY.getCode()).getSysItemsValue();
				// 判断当天支付金额已超限
				if (!EsTools.isEmpty(dailyAlreadyAmount))
				{
					BigDecimal alreadyAmount = Compute.add(new BigDecimal(dailyAlreadyAmount),
							new BigDecimal(amount));
					if (alreadyAmount.compareTo(dailyQuota) == 1)
					{
						throw new HsException(RespCode.PS_HSB_CONSUMER_PAYMENT_DAY_MAX.getCode(), "互生币单日支付金额已超限");
					}
				}
			}
		}*/

//		if (!EsTools.isEmpty(dailyFrequencyParam.getDueDate()))
//		{
//			if (dailyFrequencyParam.getDueDate().equals(today))
//			{
//				// 互生币支付单日已经交易次数
//				String dailyAlreadyFrequency = businessParamSearch.searchCustParamItemsByIdKey(
//						custId, BusinessParam.HAD_PAYMENT_DAY_NUMBER.getCode()).getSysItemsValue();
//				// 判断当天已经交易次数已超限
//				if (!EsTools.isEmpty(dailyAlreadyFrequency))
//				{
//					if (dailyFrequency <= Integer.parseInt(dailyAlreadyFrequency))
//					{
//						throw new HsException(RespCode.PS_PARAM_ERROR.getCode(), "互生币单日支付次数已超限");
//					}
//				}
//			}
//		}
		return true;
	}

	/**
	 * 消费者互生币支付限额处理
	 * 
	 * @param custId
	 *            企业客户号
	 * @param amount
	 *            企业支付金额
	 * @throws HsException
	 *      java.lang.String)
	 */
	public void quotaHandle(String custId, String amount) throws HsException
	{
		int count = 0;
		BigDecimal alreadyAmount = new BigDecimal("0.00"); 
		BusinessCustParamItemsRedis CustParam = new BusinessCustParamItemsRedis();

		//start--modified by liuzh on 2016-05-18
		// 消费者互生币已支付额度
		/*
		String dailyAlreadyAmount = businessParamSearch.searchCustParamItemsByIdKey(custId,
				BusinessParam.HAD_PAYMENT_DAY.getCode()).getSysItemsValue();
		*/
		BusinessCustParamItemsRedis hadQuotaParam = businessParamSearch.searchCustParamItemsByIdKey(custId,BusinessParam.HAD_PAYMENT_DAY.getCode());
		String dailyAlreadyAmount = hadQuotaParam.getSysItemsValue();
		//要比较是否当日,当日就累计,非当日就从0计
		if (!today.equals(hadQuotaParam.getDueDate())) {
			dailyAlreadyAmount = "0";
		}
        SystemLog.debug(this.getClass().getName(), "quotaHandle","hadQuotaParam.getDueDate():" + hadQuotaParam.getDueDate()
        		+ ",dailyAlreadyAmount:" + dailyAlreadyAmount);	        
		//end--modified by liuzh on 2016-05-18
        
		if(!EsTools.isEmpty(dailyAlreadyAmount)){
			alreadyAmount = new BigDecimal(dailyAlreadyAmount);
		}

		// 互生币支付单日已经交易次数
		String dailyAlreadyFrequency = businessParamSearch.searchCustParamItemsByIdKey(custId,
				BusinessParam.HAD_PAYMENT_DAY_NUMBER.getCode()).getSysItemsValue();
		
		if(!EsTools.isEmpty(dailyAlreadyFrequency)){
			count = Integer.parseInt(dailyAlreadyFrequency);
		}

		CustParam.setCustId(custId);
		CustParam.setSysItemsKey(BusinessParam.HAD_PAYMENT_DAY.getCode());
		// 更新单日互生币已支付金额
		BigDecimal alreadyPayAmount = Compute.add(alreadyAmount, new BigDecimal(amount));
		CustParam.setSysItemsValue(String.valueOf(alreadyPayAmount));
		CustParam.setDueDate(today);
		businessParamSearch.setBusinessCustParamItemsRed(CustParam);

		CustParam.setCustId(custId);
		CustParam.setSysItemsKey(BusinessParam.HAD_PAYMENT_DAY_NUMBER.getCode());
		// 更新单日互生币已支付次数
		CustParam.setSysItemsValue(String.valueOf(count + 1));
		CustParam.setDueDate(today);
		businessParamSearch.setBusinessCustParamItemsRed(CustParam);

	}

}
