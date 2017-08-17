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

package com.gy.hsxt.access.web.company.services.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.baidu.disconf.client.common.annotations.DisconfFile;
import com.baidu.disconf.client.common.annotations.DisconfFileItem;
import com.baidu.disconf.client.common.annotations.DisconfUpdateService;
import com.baidu.disconf.client.common.update.IDisconfUpdate;
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
@DisconfFile(filename = "hsxt-access-web-company.properties")
@DisconfUpdateService(classes = { CompanyConfigService.class })
public class CompanyConfigService implements IDisconfUpdate {

	public static final String DEFAULT_VALUE = "0.00";

	/**
	 * 业务参数注入服务
	 */
	@Autowired
	public BusinessParamSearch businessParamSearch;

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
			return CompanyConfigService.DEFAULT_VALUE;
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
			return CompanyConfigService.DEFAULT_VALUE;
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
			return CompanyConfigService.DEFAULT_VALUE;
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
			return CompanyConfigService.DEFAULT_VALUE;
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
			return CompanyConfigService.DEFAULT_VALUE;
		} else {
			return returnValue;
		}
	}

	/**
	 * 互生币账户--保底互生币
	 * 
	 * @return
	 */
	public String getHsbLeastIntegration() {
		String returnValue = businessParamSearch.searchSysParamItemsByCodeKey(
				BusinessParam.PUB_ACCOUNT_THRESHOLD.getCode(),
				BusinessParam.C_SAVING_HSB.getCode()).getSysItemsValue();
		if (StringUtils.isBlank(returnValue)) {
			return CompanyConfigService.DEFAULT_VALUE;
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
			return CompanyConfigService.DEFAULT_VALUE;
		} else {
			return returnValue;
		}
	}

	/** 互生币最大兑换数量 */
	private int hsbSingleConvertibleMax;

	/** 互生币最小兑换数量 */
	private int hsbSingleConvertibleMin;

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

	/** 系统每年的年费价格 **/
	private String annualFee;

	/** 登录密码长度 */
	private int loginPasswordLength;

	/** 交易密码长度 */
	private int tradingPasswordLength;

	/**
	 * 交易密码申请重置业务文件下载id
	 */
	private String tradPwdRequestFile;

	/**
	 * 转出积分数为不小于{0}的整数
	 */
	private int pointConvertibleMin;

	/**
	 * 保底积分数
	 */
	private int pointBaodi;

	/** 银行资金证明文件 **/
	private String bankPicFileId;

	/** 创业帮扶协议 **/
	private String venBefProtocolId;

	/** 合作股东证明文件文件 **/
	private String sharePicFileId;

	/** 互生币转货币的手续费比例 */
	private double currencyFee;

	/** 货币转银行手续费比例 */
	private double currencyTransferBankFee;

	/** 转出货币最小整数！ */
	private int currencyMin;

	/** 实名认证兑换互生最小值 */
	private int verifiedMin;

	/** 实名认证兑换互生最大值 */
	private int verifiedMax;

	/** 未实名认证兑换互生最小值 */
	private int notVerifiedMin;

	/** 未实名认证兑换互生最大值 */
	private int notVerifiedMax;

	/** 每天限制额度 */
	private int restrictionsHsb;

	/** 今天还可兑换的互生币数量 */
	private int toDayRestrictionsHsb;

	/** 个人最多可以设置银行卡信息 */
	private int bankListSize;

	/** 税率调整证明材料模版 */
	private String taxRateProofMaterialModule;

	/** 获取缓存图片保存时间 */
	private int imgCodeOverdueTime;
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
	 * @return the 获取缓存图片保存时间
	 */
	@DisconfFileItem(name = "img.code.overdueTime", associateField = "imgCodeOverdueTime")
	public int getImgCodeOverdueTime() {
		return imgCodeOverdueTime;
	}

	/**
	 * @param 获取缓存图片保存时间
	 *            the imgCodeOverdueTime to set
	 */
	public void setImgCodeOverdueTime(int imgCodeOverdueTime) {
		this.imgCodeOverdueTime = imgCodeOverdueTime;
	}

	/**
	 * @return the 税率调整证明材料模版
	 */
	@DisconfFileItem(name = "cityTax.RateProofMaterialModule", associateField = "account")
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
	 * 货币转费用
	 * 
	 * @return
	 */
	@DisconfFileItem(name = "currency.fee", associateField = "currencyFee")
	public double getCurrencyFee() {
		return currencyFee;
	}

	public void setCurrencyFee(double currencyFee) {
		this.currencyFee = currencyFee;
	}

	/**
	 * 货币转费用
	 * 
	 * @return
	 */
	@DisconfFileItem(name = "verified.min", associateField = "verifiedMin")
	public int getVerifiedMin() {
		return verifiedMin;
	}

	public void setVerifiedMin(int verifiedMin) {
		this.verifiedMin = verifiedMin;
	}

	/**
	 * 货币转费用
	 * 
	 * @return
	 */
	@DisconfFileItem(name = "verified.max", associateField = "verifiedMax")
	public int getVerifiedMax() {
		return verifiedMax;
	}

	public void setVerifiedMax(int verifiedMax) {
		this.verifiedMax = verifiedMax;
	}

	/**
	 * 货币转费用
	 * 
	 * @return
	 */
	@DisconfFileItem(name = "not.verified.min", associateField = "notVerifiedMin")
	public int getNotVerifiedMin() {
		return notVerifiedMin;
	}

	public void setNotVerifiedMin(int notVerifiedMin) {
		this.notVerifiedMin = notVerifiedMin;
	}

	/**
	 * 货币转费用
	 * 
	 * @return
	 */
	@DisconfFileItem(name = "not.verified.max", associateField = "notVerifiedMax")
	public int getNotVerifiedMax() {
		return notVerifiedMax;
	}

	public void setNotVerifiedMax(int notVerifiedMax) {
		this.notVerifiedMax = notVerifiedMax;
	}

	/**
	 * 每天限制额度
	 * 
	 * @return
	 */
	@DisconfFileItem(name = "restrictions.hsb", associateField = "restrictionsHsb")
	public int getRestrictionsHsb() {
		return restrictionsHsb;
	}

	public void setRestrictionsHsb(int restrictionsHsb) {
		this.restrictionsHsb = restrictionsHsb;
	}

	/**
	 * 今天还可兑换的互生币数量
	 * 
	 * @return
	 */
	@DisconfFileItem(name = "to.day.restrictions.hsb", associateField = "toDayRestrictionsHsb")
	public int getToDayRestrictionsHsb() {
		return toDayRestrictionsHsb;
	}

	public void setToDayRestrictionsHsb(int toDayRestrictionsHsb) {
		this.toDayRestrictionsHsb = toDayRestrictionsHsb;
	}

	/**
	 * 转出货币最小整数
	 * 
	 * @return
	 */
	@DisconfFileItem(name = "currency.min", associateField = "currencyMin")
	public int getCurrencyMin() {
		return currencyMin;
	}

	public void setCurrencyMin(int currencyMin) {
		this.currencyMin = currencyMin;
	}

	/**
	 * 货币转银行手续费比例
	 * 
	 * @return
	 */
	@DisconfFileItem(name = "currency.transfer.bank.fee", associateField = "currencyTransferBankFee")
	public double getCurrencyTransferBankFee() {
		return currencyTransferBankFee;
	}

	public void setCurrencyTransferBankFee(double currencyTransferBankFee) {
		this.currencyTransferBankFee = currencyTransferBankFee;
	}

	/**
	 * 获取消费者最多可舌质
	 * 
	 * @return
	 */
	@DisconfFileItem(name = "bank.list.size", associateField = "bankListSize")
	public int getBankListSize() {
		return bankListSize;
	}

	public void setBankListSize(int bankListSize) {
		this.bankListSize = bankListSize;
	}

	@DisconfFileItem(name = "entfiling.bankpicfileid", associateField = "bankPicFileId")
	public String getBankPicFileId() {
		return bankPicFileId;
	}

	public void setBankPicFileId(String bankPicFileId) {
		this.bankPicFileId = bankPicFileId;
	}

	@DisconfFileItem(name = "entfiling.sharepicfileid", associateField = "sharePicFileId")
	public String getSharePicFileId() {
		return sharePicFileId;
	}

	public void setSharePicFileId(String sharePicFileId) {
		this.sharePicFileId = sharePicFileId;
	}

	@DisconfFileItem(name = "entfiling.venbefprotocolid", associateField = "venBefProtocolId")
	public String getVenBefProtocolId() {
		return venBefProtocolId;
	}

	public void setVenBefProtocolId(String venBefProtocolId) {
		this.venBefProtocolId = venBefProtocolId;
	}

	/**
	 * 重置交易密码申请,申请文件下载地址
	 * 
	 * @return
	 */
	@DisconfFileItem(name = "tradPwd.request.file", associateField = "tradPwdRequestFile")
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
	@DisconfFileItem(name = "trading.password.length", associateField = "tradingPasswordLength")
	public int getTradingPasswordLength() {
		return tradingPasswordLength;
	}

	public void setTradingPasswordLength(int tradingPasswordLength) {
		this.tradingPasswordLength = tradingPasswordLength;
	}

	/**
	 * 登录密码长度
	 * 
	 * @return
	 */
	@DisconfFileItem(name = "login.passWord.length", associateField = "loginPasswordLength")
	public int getLoginPasswordLength() {
		return loginPasswordLength;
	}

	public void setLoginPasswordLength(int loginPasswordLength) {
		this.loginPasswordLength = loginPasswordLength;
	}

	/**
	 * 获取互生币最大兑换数量
	 * 
	 * @return
	 */
	@DisconfFileItem(name = "hsb.single.convertible.max", associateField = "hsbSingleConvertibleMax")
	public int getHsbSingleConvertibleMax() {
		return hsbSingleConvertibleMax;
	}

	public void setHsbSingleConvertibleMax(int hsbSingleConvertibleMax) {
		this.hsbSingleConvertibleMax = hsbSingleConvertibleMax;
	}

	/**
	 * 获取互生币最小兑换数量
	 * 
	 * @return
	 */
	@DisconfFileItem(name = "hsb.single.convertible.min", associateField = "hsbSingleConvertibleMin")
	public int getHsbSingleConvertibleMin() {
		return this.hsbSingleConvertibleMin;
	}

	public void setHsbSingleConvertibleMin(int hsbSingleConvertibleMin) {
		this.hsbSingleConvertibleMin = hsbSingleConvertibleMin;
	}

	/**
	 * @throws Exception
	 * @see com.baidu.disconf.client.common.update.IDisconfUpdate#reload()
	 */
	@Override
	public void reload() throws Exception {
		// TODO Auto-generated method stub

	}

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


	/**
	 * 年费价格
	 * 
	 * @return
	 */
	@DisconfFileItem(name = "jnxtsynf.annualFee", associateField = "annualFee")
	public String getAnnualFee() {
		return annualFee;
	}

	public void setAnnualFee(String annualFee) {
		this.annualFee = annualFee;
	}

	/**
	 * 积分账户的可用积分数可转出，转出积分数为不小于的整数值
	 * 
	 * @return
	 */
	@DisconfFileItem(name = "jfzh.pointConvertibleMin", associateField = "pointConvertibleMin")
	public int getPointConvertibleMin() {
		return pointConvertibleMin;
	}

	public void setPointConvertibleMin(int pointConvertibleMin) {
		this.pointConvertibleMin = pointConvertibleMin;
	}

	/**
	 * 积分保底数
	 * 
	 * @return
	 */
	@DisconfFileItem(name = "jfzh.pointBaodi", associateField = "pointBaodi")
	public int getPointBaodi() {
		return pointBaodi;
	}

	public void setPointBaodi(int pointBaodi) {
		this.pointBaodi = pointBaodi;
	}

}
