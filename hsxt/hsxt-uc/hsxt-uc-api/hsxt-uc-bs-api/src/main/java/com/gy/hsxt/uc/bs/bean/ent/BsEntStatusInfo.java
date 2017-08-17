/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */

package com.gy.hsxt.uc.bs.bean.ent;

import java.io.Serializable;

/**
 * 企业状态信息
 * 
 * @Package: com.gy.hsxt.uc.bs.bean.ent
 * @ClassName: BsEntStatusInfo
 * @Description: TODO
 * 
 * @author: huanggaoyang
 * @date: 2015-10-19 下午12:22:43
 * @version V1.0
 */
public class BsEntStatusInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	/** 企业客户号 */
	private String entCustId;
	/** 企业互生号 */
	private String entResNo;
	/** 客户类型 */
	private Integer custType;
	/**
	 * 基本状态
	 * 
	 * 1：正常 成员企业、托管企业
	 * 
	 * 2：预警 成员企业、托管企业
	 * 
	 * 3：休眠 成员企业
	 * 
	 * 4：长眠 成员企业
	 * 
	 * 5：已注销 成员企业
	 * 
	 * 6：申请停止积分活动中 托管企业
	 * 
	 * 7：停止积分活动 托管企业
	 * 
	 * 8：注销申请中 成员企业
	 */
	private Integer baseStatus;
	/** 实名认证状态 1.已认证、0.未认证 */
	private Integer isRealnameAuth;
	/** 是否重要信息变更期间 1:是 0：否 */
	private Integer isMaininfoChanged;
	/** 是否已注册 1:是 0：否 */
	private Integer isReg;
	/** 是否欠年费 1:是 0：否 */
	private Integer isOweFee;
	/** 是否老企业 1:是 0：否 */
	private Integer isOldEnt;
	/** 是否验证手机 1:是 0：否 */
	private Integer isAuthMobile;
	/** 是否验证邮箱 1:是 0：否 */
	private Integer isAuthEmail;
	/** 企业是否关闭（冻结） 1:是 0：否 */
	private Integer isClosedEnt;
	/** 最后一次积分时间 */
	private String lastPointDate;
	/** 系统开启时间 */
	private String openDate;
	/** 企业缴年费截止日期 */
	private String expireDate;
	/** 企业购买积分卡数量 */
	private Long countBuyCards;
	/** 是否设置交易密码 */
	private boolean isHaveTradePwd;

	/**
	 * @return the 企业客户号
	 */
	public String getEntCustId() {
		return entCustId;
	}

	/**
	 * @param 企业客户号
	 *            the entCustId to set
	 */
	public void setEntCustId(String entCustId) {
		this.entCustId = entCustId;
	}

	/**
	 * @return the 企业互生号
	 */
	public String getEntResNo() {
		return entResNo;
	}

	/**
	 * @param 企业互生号
	 *            the entResNo to set
	 */
	public void setEntResNo(String entResNo) {
		this.entResNo = entResNo;
	}

	public Integer getBaseStatus() {
		return baseStatus;
	}

	public void setBaseStatus(Integer baseStatus) {
		this.baseStatus = baseStatus;
	}

	/**
	 * @return the 实名认证状态1.已认证、0.未认证
	 */
	public Integer getIsRealnameAuth() {
		return isRealnameAuth;
	}

	/**
	 * @param 实名认证状态1
	 *            .已认证、0.未认证 the isRealnameAuth to set
	 */
	public void setIsRealnameAuth(Integer isRealnameAuth) {
		this.isRealnameAuth = isRealnameAuth;
	}

	/**
	 * @return the 是否重要信息变更期间1:是0：否
	 */
	public Integer getIsMaininfoChanged() {
		return isMaininfoChanged;
	}

	/**
	 * @param 是否重要信息变更期间1
	 *            :是0：否 the isMaininfoChanged to set
	 */
	public void setIsMaininfoChanged(Integer isMaininfoChanged) {
		this.isMaininfoChanged = isMaininfoChanged;
	}

	/**
	 * @return the 是否已注册1:是0：否
	 */
	public Integer getIsReg() {
		return isReg;
	}

	/**
	 * @param 是否已注册1
	 *            :是0：否 the isReg to set
	 */
	public void setIsReg(Integer isReg) {
		this.isReg = isReg;
	}

	/**
	 * @return the 是否欠年费1:是0：否
	 */
	public Integer getIsOweFee() {
		return isOweFee;
	}

	/**
	 * @param 是否欠年费1
	 *            :是0：否 the isOweFee to set
	 */
	public void setIsOweFee(Integer isOweFee) {
		this.isOweFee = isOweFee;
	}

	/**
	 * @return the 是否老企业1:是0：否
	 */
	public Integer getIsOldEnt() {
		return isOldEnt;
	}

	/**
	 * @param 是否老企业1
	 *            :是0：否 the isOldEnt to set
	 */
	public void setIsOldEnt(Integer isOldEnt) {
		this.isOldEnt = isOldEnt;
	}

	/**
	 * @return the 是否验证手机1:是0：否
	 */
	public Integer getIsAuthMobile() {
		return isAuthMobile;
	}

	/**
	 * @param 是否验证手机1
	 *            :是0：否 the isAuthMobile to set
	 */
	public void setIsAuthMobile(Integer isAuthMobile) {
		this.isAuthMobile = isAuthMobile;
	}

	/**
	 * @return the 是否验证邮箱1:是0：否
	 */
	public Integer getIsAuthEmail() {
		return isAuthEmail;
	}

	/**
	 * @param 是否验证邮箱1
	 *            :是0：否 the isAuthEmail to set
	 */
	public void setIsAuthEmail(Integer isAuthEmail) {
		this.isAuthEmail = isAuthEmail;
	}

	/**
	 * @return the 企业是否关闭（冻结）1:是0：否
	 */
	public Integer getIsClosedEnt() {
		return isClosedEnt;
	}

	/**
	 * @param 企业是否关闭
	 *            （冻结）1:是0：否 the isClosedEnt to set
	 */
	public void setIsClosedEnt(Integer isClosedEnt) {
		this.isClosedEnt = isClosedEnt;
	}

	/**
	 * @return the 最后一次积分时间
	 */
	public String getLastPointDate() {
		return lastPointDate;
	}

	/**
	 * @param 最后一次积分时间
	 *            the lastPointDate to set
	 */
	public void setLastPointDate(String lastPointDate) {
		this.lastPointDate = lastPointDate;
	}

	/**
	 * @return the 系统开启时间
	 */
	public String getOpenDate() {
		return openDate;
	}

	/**
	 * @param 系统开启时间
	 *            the openDate to set
	 */
	public void setOpenDate(String openDate) {
		this.openDate = openDate;
	}

	/**
	 * @return the 企业缴年费截止日期
	 */
	public String getExpireDate() {
		return expireDate;
	}

	/**
	 * @param 企业缴年费截止日期
	 *            the expireDate to set
	 */
	public void setExpireDate(String expireDate) {
		this.expireDate = expireDate;
	}

	/**
	 * @return the 是否设置交易密码
	 */
	public boolean isHaveTradePwd() {
		return isHaveTradePwd;
	}

	/**
	 * @param 是否设置交易密码
	 *            the isHaveTradePwd to set
	 */
	public void setHaveTradePwd(boolean isHaveTradePwd) {
		this.isHaveTradePwd = isHaveTradePwd;
	}

	/**
	 * @return the 企业购买积分卡数量
	 */
	public Long getCountBuyCards() {
		return countBuyCards;
	}

	/**
	 * @param 企业购买积分卡数量
	 *            the countBuyCards to set
	 */
	public void setCountBuyCards(Long countBuyCards) {
		this.countBuyCards = countBuyCards;
	}

	/**
	 * @return the 客户类型
	 */
	public Integer getCustType() {
		return custType;
	}

	/**
	 * @param 客户类型
	 *            the custType to set
	 */
	public void setCustType(Integer custType) {
		this.custType = custType;
	}

}
