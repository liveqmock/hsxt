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

package com.gy.hsxt.access.web.scs.services.common;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.baidu.disconf.client.common.annotations.DisconfFileItem;
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
 * Package Name     : com.gy.hsxt.access.web.scs.services.common  
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
public class SCSConfigService implements IDisconfUpdate {

	/**
	 * 业务参数注入服务
	 */
	@Autowired
	public BusinessParamSearch businessParamSearch;
	
	@Autowired
    private HsPropertiesConfigurer propertyConfigurer;

	public static final String DEFAULT_VALUE = "0.00";

	/**
	 * 积分账户--保底积分数
	 * 
	 * @return
	 */
	public String getPersonLeastIntegration() {
		String returnValue = businessParamSearch.searchSysParamItemsByCodeKey(
				BusinessParam.JF_CHANGE_HSB.getCode(),
				BusinessParam.C_SAVING_POINT.getCode()).getSysItemsValue();
		if (StringUtils.isBlank(returnValue)) {
			return SCSConfigService.DEFAULT_VALUE;
		} else {
			return returnValue;
		}
	}

	/**
	 * 积分转互生币 -- 最小转出数量
	 * 
	 * @return
	 */
	public String getIntegrationConvertibleMin() {
		String returnValue = businessParamSearch.searchSysParamItemsByCodeKey(
				BusinessParam.JF_CHANGE_HSB.getCode(),
				BusinessParam.C_SINGLE_EXCHANGE_POINT_MIN.getCode())
				.getSysItemsValue();
		if (StringUtils.isBlank(returnValue)) {
			return SCSConfigService.DEFAULT_VALUE;
		} else {
			return returnValue;
		}
	}

	/**
	 * 积分投资 -- 最小转出整数
	 * 
	 * @return
	 */
	public String getIntegrationInvIntMult() {
		String returnValue = businessParamSearch.searchSysParamItemsByCodeKey(
				BusinessParam.JF_INVEST.getCode(),
				BusinessParam.C_SINGLE_INVEST_POINT_MIN.getCode())
				.getSysItemsValue();
		if (StringUtils.isBlank(returnValue)) {
			return SCSConfigService.DEFAULT_VALUE;
		} else {
			return returnValue;
		}
	}

	/**
	 * 互生币转货币 -- 最小转出整数
	 * 
	 * @return
	 */
	public String getHsbCirculationConvertibleMin() {
		String returnValue = businessParamSearch.searchSysParamItemsByCodeKey(
				BusinessParam.HSB_CHANGE_HB.getCode(),
				BusinessParam.HSB_CHANGE_HB_SINGAL_MIN.getCode())
				.getSysItemsValue();
		if (StringUtils.isBlank(returnValue)) {
			return SCSConfigService.DEFAULT_VALUE;
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
			return SCSConfigService.DEFAULT_VALUE;
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
				BusinessParam.COMAPNY_SINGLE_TRANSFER_MIN.getCode())
				.getSysItemsValue();
		if (StringUtils.isBlank(returnValue)) {
			return SCSConfigService.DEFAULT_VALUE;
		} else {
			return returnValue;
		}
	}

	/** 法人代表证件正面照FileId */
	private String lrCredentialFrontFileId;

	/** 法人代表证件背面照FileId */
	private String lrCredentialBackFileId;

	/** 营业执照图片ID *******/
	private String busLicenceFileId;

	/*** 组织机构证件照ID ******/
	private String organizationFileId;

	/** 税务登记证件照ID */
	private String taxplayerFileId;

	/** 变更申请书扫描文件 **/
	private String applyFileId;

	/** 授权委拖书扫描文件 **/
	private String powerOfAttorneyFileId;

	/** 银行资金证明文件 **/
	private String bankPicFileId;

	/** 合作股东证明文件文件 **/
	private String sharePicFileId;

	/**
	 * 交易密码申请重置业务文件下载id
	 */
	private String tradPwdRequestFile;

	/** 验证码是否为固定值(1111) **/
	private boolean imgCodeFixed;

	/**
	 * @return the 验证码是否为固定值(1111)
	 */ 
	public boolean isImgCodeFixed() {
		return imgCodeFixed;
	}

	public void setImgCodeFixed(boolean imgCodeFixed) {
		this.imgCodeFixed = imgCodeFixed;
	}

	/** 登录密码长度 */
	private int loginPasswordLength;

	/** 交易密码长度 */
	private int tradingPasswordLength;

	/** 税率调整证明材料模版 */
	private String taxRateProofMaterialModule;

	public String getLrCredentialFrontFileId() {
		return lrCredentialFrontFileId;
	}

	public void setLrCredentialFrontFileId(String lrCredentialFrontFileId) {
		this.lrCredentialFrontFileId = lrCredentialFrontFileId;
	}

	public String getLrCredentialBackFileId() {
		return lrCredentialBackFileId;
	}

	public void setLrCredentialBackFileId(String lrCredentialBackFileId) {
		this.lrCredentialBackFileId = lrCredentialBackFileId;
	}

	public String getBusLicenceFileId() {
		return busLicenceFileId;
	}

	public void setBusLicenceFileId(String busLicenceFileId) {
		this.busLicenceFileId = busLicenceFileId;
	}

	public String getOrganizationFileId() {
		return organizationFileId;
	}

	public void setOrganizationFileId(String organizationFileId) {
		this.organizationFileId = organizationFileId;
	}

	public String getTaxplayerFileId() {
		return taxplayerFileId;
	}

	public void setTaxplayerFileId(String taxplayerFileId) {
		this.taxplayerFileId = taxplayerFileId;
	}

	public String getApplyFileId() {
		return applyFileId;
	}

	public void setApplyFileId(String applyFileId) {
		this.applyFileId = applyFileId;
	}

	public String getPowerOfAttorneyFileId() {
		return powerOfAttorneyFileId;
	}

	public void setPowerOfAttorneyFileId(String powerOfAttorneyFileId) {
		this.powerOfAttorneyFileId = powerOfAttorneyFileId;
	}

	public String getBankPicFileId() {
		return bankPicFileId;
	}

	public void setBankPicFileId(String bankPicFileId) {
		this.bankPicFileId = bankPicFileId;
	}

	public String getSharePicFileId() {
		return sharePicFileId;
	}

	public void setSharePicFileId(String sharePicFileId) {
		this.sharePicFileId = sharePicFileId;
	}

	/**
	 * 重置交易密码申请,申请文件下载地址
	 * 
	 * @return
	 */
	public String getTradPwdRequestFile() {
		return tradPwdRequestFile;
	}

	public void setTradPwdRequestFile(String tradPwdRequestFile) {
		this.tradPwdRequestFile = tradPwdRequestFile;
	}

	/**
	 * 交易密码长度
	 * 
	 * @return
	 */
	public int getTradingPasswordLength() {
		return 8;
	}

	public void setTradingPasswordLength(int tradingPasswordLength) {
		this.tradingPasswordLength = tradingPasswordLength;
	}

	/**
	 * 登录密码长度
	 * 
	 * @return
	 */
	public int getLoginPasswordLength() {
		return 6;
	}

	public void setLoginPasswordLength(int loginPasswordLength) {
		this.loginPasswordLength = 8;
	}

	/**
	 * @return the 税率调整证明材料模版
	 */
	public String getTaxRateProofMaterialModule() {
		return taxRateProofMaterialModule;
	}

	/**
	 * @param 税率调整证明材料模版
	 *            the taxRateProofMaterialModule to set
	 */
	public void setTaxRateProofMaterialModule(String taxRateProofMaterialModule) {
		this.taxRateProofMaterialModule = taxRateProofMaterialModule;
	}

	/**
	 * 增加业务限制提示信息
	 * 
	 * @param businessParam
	 * @param custId
	 * @return
	 */
	public Map<String, String> getRestrictMap(BusinessParam businessParam,
			String custId) {

		// 变量初始化
		Map<String, String> infoMap = new HashMap<>();

		// 获取当前用户是否存在限时信息
		Map<String, BusinessCustParamItemsRedis> custParamMap = businessParamSearch
				.searchCustParamItemsByGroup(custId);

		if (null == custParamMap) {
			return null;
		}

		// 获取账户限时信息
		BusinessCustParamItemsRedis items = custParamMap.get(businessParam
				.getCode());

		if (null == items) {
			return null;
		}

		// 获取值并等于null则返回""
		String restrictValue = StringUtils
				.nullToEmpty(items.getSysItemsValue());
		String restrictRemark = StringUtils.nullToEmpty(items
				.getSettingRemark());
		String restrictName = StringUtils.nullToEmpty(items.getSysItemsKey());

		// 保存到map
		infoMap.put("restrictValue", restrictValue);
		infoMap.put("restrictRemark", restrictRemark);
		infoMap.put("restrictName", restrictName);

		// 返回
		return infoMap;
	}

	/**
     * 获取缓存图片保存时间 
     * @return
     */
    @DisconfFileItem(name = "img.code.overdueTime", associateField = "imgCodeOverdueTime")
    public int imgCodeOverdueTime(){
        //缓存时间
        String value = this.propertyConfigurer.getProperty("img.code.overdueTime");
        
        if(StringUtils.isBlank(value)){
            return 1200;
        }
        
        return Integer.parseInt(value);
    }
    
	/**
	 * @throws Exception
	 * @see com.baidu.disconf.client.common.update.IDisconfUpdate#reload()
	 */
	@Override
	public void reload() throws Exception {
		// TODO Auto-generated method stub

	}

	public static void main(String[] args) {

	}
}
