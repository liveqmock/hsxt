/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */

package com.gy.hsxt.access.web.person.services.common;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.baidu.disconf.client.common.annotations.DisconfFile;
import com.baidu.disconf.client.common.annotations.DisconfFileItem;
import com.baidu.disconf.client.common.annotations.DisconfUpdateService;
import com.baidu.disconf.client.common.update.IDisconfUpdate;
import com.gy.hsi.ds.param.HsPropertiesConfigurer;
import com.gy.hsxt.bp.bean.BusinessCustParamItemsRedis;
import com.gy.hsxt.bp.client.api.BusinessParamSearch;
import com.gy.hsxt.common.constant.BusinessParam;
import com.gy.hsxt.common.utils.StringUtils;

/***************************************************************************
 * <PRE>
 * Description 		: 企业web规则约束条件配置文件
 * 
 * Project Name   	: hsxt-access-web-company 
 * 
 * Package Name     : com.gy.hsxt.access.web.company.services.common  
 * 
 * File Name        : CompanyConfigService 
 * 
 * Author           : LiZhi Peter
 * 
 * Create Date      : 2015-10-9 下午4:42:32
 * 
 * Update User      : LiZhi Peter
 * 
 * Update Date      : 2015-10-9 下午4:42:32
 * 
 * UpdateRemark 	: 说明本次修改内容
 * 
 * Version          : v1.0
 * 
 * </PRE>
 ***************************************************************************/
@Service
@Scope("singleton")
@DisconfFile(filename = "hsxt-access-web-person.properties")
@DisconfUpdateService(classes = { PersonConfigService.class })
public class PersonConfigService implements IDisconfUpdate {

	public static final String DEFAULT_VALUE = "0.00";
	/**
	 * 业务参数注入服务
	 */
	@Autowired
	public BusinessParamSearch businessParamSearch;
	
	@Autowired
    private HsPropertiesConfigurer propertyConfigurer;

	/**
	 * 交易密码长度
	 * 
	 * @return
	 */
	public int getTradingPasswordLength() {
		return 8;
	}

	/**
	 * 登录密码长度
	 * 
	 * @return
	 */
	public int getLoginPasswordLength() {
		return 6;
	}

	 /**
     * 获取缓存图片保存时间 
     * @return
     */
    @DisconfFileItem(name = "img.code.overdueTime", associateField = "imgCodeOverdueTime")
    public int imgCodeOverdueTime(){
        //缓存时间
        String value =  this.propertyConfigurer.getProperty("img.code.overdueTime");
        
        if(StringUtils.isBlank(value)){
            return 1200;
        }
        
        return Integer.parseInt(value);
    }
	
	/**
	 * 平台设置公用上限阀值-消费者互生币支付单笔限额
	 * @return
	 */
	public String getConsumerPaymentMax(){
		String returnValue = businessParamSearch.searchSysParamItemsByCodeKey(
				BusinessParam.HSB_PAYMENT.getCode(),
				BusinessParam.CONSUMER_PAYMENT_MAX.getCode())
				.getSysItemsValue();
		if (StringUtils.isBlank(returnValue)) {
			return PersonConfigService.DEFAULT_VALUE;
		} else {
			return returnValue;
		}
	}
	
	/**
	 * 平台设置公用上限阀值-消费者互生币支付当日限额
	 * @return
	 */
	public String getConsumerPaymentDayMax(){
		String returnValue = businessParamSearch.searchSysParamItemsByCodeKey(
				BusinessParam.HSB_PAYMENT.getCode(),
				BusinessParam.CONSUMER_PAYMENT_DAY_MAX.getCode())
				.getSysItemsValue();
		if (StringUtils.isBlank(returnValue)) {
			return PersonConfigService.DEFAULT_VALUE;
		} else {
			return returnValue;
		}
	}

	/**
	 * 互生币转货币--最小限额整数
	 * 
	 * @return
	 */
	public String getHsbCirculationConvertibleMin() {
		String returnValue = businessParamSearch.searchSysParamItemsByCodeKey(
				BusinessParam.HSB_CHANGE_HB.getCode(),
				BusinessParam.HSB_CHANGE_HB_SINGAL_MIN.getCode())
				.getSysItemsValue();
		if (StringUtils.isBlank(returnValue)) {
			return PersonConfigService.DEFAULT_VALUE;
		} else {
			return returnValue;
		}
	}

	/**
	 * 互生币转货币---账户须扣除%的互生币作为货币转换费
	 * 
	 * @return
	 */
	public String getHsbConvertibleFee() {
		String returnValue = businessParamSearch.searchSysParamItemsByCodeKey(
				BusinessParam.HSB_CHANGE_HB.getCode(),
				BusinessParam.HSB_CHANGE_HB_RATIO.getCode()).getSysItemsValue();
		if (StringUtils.isBlank(returnValue)) {
			return PersonConfigService.DEFAULT_VALUE;
		} else {
			return returnValue;
		}
	}

	/**
	 * 货币转银行，转出金额为不小的整数
	 * 
	 * @return
	 */
	public String getMonetaryConvertibleMin() {
		String returnValue = businessParamSearch.searchSysParamItemsByCodeKey(
				BusinessParam.HB_CHANGE_BANK.getCode(),
				BusinessParam.PERSON_SINGLE_TRANSFER_MIN.getCode())
				.getSysItemsValue();
		if (StringUtils.isBlank(returnValue)) {
			return PersonConfigService.DEFAULT_VALUE;
		} else {
			return returnValue;
		}
	}

	/**
	 * 积分账户--保底积分数
	 * 
	 * @return
	 */
	public String getPersonLeastIntegration() {
		String returnValue = businessParamSearch.searchSysParamItemsByCodeKey(
				BusinessParam.JF_CHANGE_HSB.getCode(),
				BusinessParam.P_SAVING_POINT.getCode()).getSysItemsValue();
		if (StringUtils.isBlank(returnValue)) {
			return PersonConfigService.DEFAULT_VALUE;
		} else {
			return returnValue;
		}
	}

	/**
	 * 积分转互生币 最小转出数量
	 * 
	 * @return
	 */
	public String getIntegrationConvertibleMin() {
		String returnValue = businessParamSearch.searchSysParamItemsByCodeKey(
				BusinessParam.JF_CHANGE_HSB.getCode(),
				BusinessParam.P_SINGLE_EXCHANGE_POINT_MIN.getCode())
				.getSysItemsValue();
		if (StringUtils.isBlank(returnValue)) {
			return PersonConfigService.DEFAULT_VALUE;
		} else {
			return returnValue;
		}
	}

	/**
	 * 积分投资 -- 最小转出整倍数
	 * 
	 * @return
	 */
	public String getIntegrationInvIntMult() {
		String returnValue = businessParamSearch.searchSysParamItemsByCodeKey(
				BusinessParam.JF_INVEST.getCode(),
				BusinessParam.P_SINGLE_INVEST_POINT_MIN.getCode())
				.getSysItemsValue();
		if (StringUtils.isBlank(returnValue)) {
			return PersonConfigService.DEFAULT_VALUE;
		} else {
			return returnValue;
		}
	}

	/**
	 * 获取消费者最多可舌质
	 * 
	 * @return
	 */
	public String getBankListSize() {
		return businessParamSearch.searchSysParamItemsByCodeKey(
				BusinessParam.CONFIG_PARA.getCode(),
				BusinessParam.BANK_CARD_BIND_NUMBER.getCode())
				.getSysItemsValue();
	}

	/** 验证码是否为固定值(1111) **/
	private boolean imgCodeFixed;

	/**
	 * @return the 验证码是否为固定值(1111)
	 */
	@DisconfFileItem(name = "img.code.isFixed", associateField = "imgCodeFixed")
	public boolean isImgCodeFixed() {
		return imgCodeFixed;
	}

	/**
	 * @return the 验证码是否为固定值(1111)
	 */
	public void setImgCodeFixed(boolean imgCodeFixed) {
		this.imgCodeFixed = imgCodeFixed;
	}

	/**
	 * 增加业务限制提示信息
	 * 
	 * @param businessParam
	 * @param custId
	 * @return
	 */
	public Map<String, String> getRestrictMap(BusinessParam businessParam,String custId) {
		
		//变量初始化
		Map<String, String> infoMap = new HashMap<>();
		
		//获取当前用户是否存在限时信息
		Map<String, BusinessCustParamItemsRedis> custParamMap = businessParamSearch.searchCustParamItemsByGroup(custId);
		
		if (null == custParamMap) {
			return null;
		}
		
		//获取账户限时信息
		BusinessCustParamItemsRedis items = custParamMap.get(businessParam.getCode());
		
		if (null == items) {
			return null;
		}
		
		//获取值并等于null则返回""
		String restrictValue = StringUtils.nullToEmpty(items.getSysItemsValue());
		String restrictRemark = StringUtils.nullToEmpty(items.getSettingRemark());
		String restrictName = StringUtils.nullToEmpty(items.getSysItemsKey());
		
		//保存到map
		infoMap.put("restrictValue", restrictValue);
		infoMap.put("restrictRemark", restrictRemark);
		infoMap.put("restrictName", restrictName);
		
		//返回
		return infoMap;
	}

	@Override
	public void reload() throws Exception {
		// TODO Auto-generated method stub

	}
}
