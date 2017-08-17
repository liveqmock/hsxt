/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.company.services.systemBusiness.imp;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.access.web.common.service.BaseServiceImpl;
import com.gy.hsxt.access.web.company.services.systemBusiness.IQuickPayManageService;
import com.gy.hsxt.ao.api.IAOExchangeHsbService;
import com.gy.hsxt.bs.api.order.IBSOrderService;
import com.gy.hsxt.common.bean.OpenQuickPayBean;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.HsAssert;
import com.gy.hsxt.common.utils.StringUtils;
import com.gy.hsxt.lcs.bean.LocalInfo;
import com.gy.hsxt.lcs.bean.PayBank;
import com.gy.hsxt.lcs.client.LcsClient;
import com.gy.hsxt.uc.as.api.common.IUCAsBankAcctInfoService;
import com.gy.hsxt.uc.as.api.enumerate.UserTypeEnum;
import com.gy.hsxt.uc.as.bean.common.AsQkBank;

/**
 * 快捷支付Service实现类
 * 
 * @Package: com.gy.hsxt.access.web.company.services.systemBusiness.imp
 * @ClassName: QuickPayManageService
 * @Description: TODO
 * @author: likui
 * @date: 2016年1月12日 下午2:08:38
 * @company: gyist
 * @version V3.0.0
 */
@Service
public class QuickPayManageService extends BaseServiceImpl<QuickPayManageService> implements IQuickPayManageService {

	/**
	 * 用户中心银行Service
	 */
	@Autowired
	private IUCAsBankAcctInfoService iuCAsBankAcctInfoService;

	/**
	 * bs订单Service
	 */
	@Autowired
	private IBSOrderService ibSOrderService;

	/** 地区平台配送Service **/
	@Autowired
	private LcsClient lcsClient;

	/** ao账户操作Service **/
	@Autowired
	private IAOExchangeHsbService aoExchangeHsbService;

	/**
	 * 查询绑定的快捷支付列表
	 * 
	 * @Description:
	 * @param entCustId
	 * @return
	 */
	@Override
	public List<AsQkBank> queryBandQuickBank(String entCustId) throws HsException
	{
		try
		{
			return iuCAsBankAcctInfoService.listQkBanksByCustId(entCustId, UserTypeEnum.ENT.getType());
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "findMayBuyTools", "调用UC查询绑定的快捷支付列表失败", ex);
			return null;
		}
	}

	/**
	 * 获取支持快捷支付的银行
	 * 
	 * @Description:
	 * @param cardType
	 * @return
	 */
	@Override
	public List<PayBank> queryQuickPayBank(String cardType) throws HsException
	{
		List<PayBank> result = null;
		try
		{
			List<PayBank> banks = lcsClient.queryPayBankAll();
			HsAssert.isTrue(StringUtils.isNotBlank(banks), RespCode.FAIL);
			result = new ArrayList<PayBank>();
			for (PayBank bank : banks)
			{
				// 储畜卡
				if ("deposit".equals(cardType) && bank.isSupportDebit())
				{
					result.add(bank);
				}
				// 信用卡
				if ("credit".equals(cardType) && bank.isSupportCredit())
				{
					result.add(bank);
				}
			}
			return result;
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "queryQuickPayBank", "调用LCS获取支持快捷支付的银行失败", ex);
			return null;
		}
	}

	/**
	 * 开通快捷支付
	 * 
	 * @Description:
	 * @param bean
	 * @param callType
	 * @return
	 * @throws HsException
	 */
	@Override
	public String openQuickPay(OpenQuickPayBean bean, String callType) throws HsException
	{
		try
		{
			LocalInfo info = lcsClient.getLocalInfo();
			bean.setJumpUrl(info.getWebPayJumpUrl());
			if (callType.equals("bs"))
			{
				return ibSOrderService.getOpenQuickPayUrl(bean);
			}
			if (callType.equals("ao"))
			{
				return aoExchangeHsbService.getOpenQuickPayUrl(bean);
			}
			return null;
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "openQuickPay", "调用BS开通快捷支付失败", ex);
			return null;
		}
	}

	/**
	 * 发送快捷支付短信验证码
	 * 
	 * @Description:
	 * @param orderNo
	 * @param bindingNo
	 * @param privObligate
	 * @param callType
	 */
	@Override
	public void sendQuickPaySmsCode(String orderNo, String bindingNo, String privObligate, String callType)
			throws HsException
	{
		try
		{
			if (callType.equals("bs"))
			{
				ibSOrderService.getQuickPaySmsCode(orderNo, bindingNo, privObligate);
			}
			if (callType.equals("ao"))
			{
				aoExchangeHsbService.getQuickPaySmsCode(orderNo, bindingNo, privObligate);
			}
		} catch (HsException ex)
		{
			throw ex;
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "openQuickPay", "调用BS发送快捷支付短信验证码失败", ex);
			throw new HsException(RespCode.FAIL);
		}
	}
}
