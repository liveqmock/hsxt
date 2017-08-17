package com.gy.hsxt.uc.consumer.bean;

import java.sql.Timestamp;

import com.alibaba.fastjson.JSONObject;
import com.gy.hsxt.common.utils.StringUtils;
import com.gy.hsxt.uc.common.bean.UserInfo;

/**
 * 
 * 
 * @Package: com.gy.hsxt.uc.consumer.bean
 * @ClassName: CardHolder
 * @Description: 持卡人信息
 * 
 * @author: tianxh
 * @date: 2015-12-14 下午7:47:11
 * @version V1.0
 */
public class CardHolder extends UserInfo  {
	private static final long serialVersionUID = 3320553804042822432L;
	/** 客户号 */
	private String perCustId;

	/** 互生号 */
	private String perResNo;

	/** 企业互生号 */
	private String entResNo;

	/** 手机号码 */
	private String mobile;

	/** 邮箱 */
	private String email;

	/** 指纹 */
	private String fingerprint;

	/** 交易密码 */
	private String pwdTrans;

	/** 交易密码版本 */
	private String pwdTransVer;

	/** 交易密码盐值 */
	private String pwdTransSaltValue;

	/** 基本状态1：启用、2：曾经登录、3：激活、4：活跃、5：休眠、6：沉淀、7：注销 */
	private Integer baseStatus;

	/** 实名状态1：未实名注册、2：已实名注册（有名字和身份证）、3:已实名认证 */
	private Integer isRealnameAuth;

	/** 是否已验证邮箱1:已验证 0: 未验证 */
	private Integer isAuthEmail;

	/** 是否已验证手机1:已验证 0: 未验证 */
	private Integer isAuthMobile;

	/** 是否重要信息变更期间1:是0：否 */
	private Integer isKeyinfoChanged;

	/** 预留信息 */
	private String ensureInfo;

	/** 最后一次积分时间 */
	private Timestamp lastPointDate;

	/** 最近登录时间 */
	private Timestamp lastLoginDate;

	/** 最近登录IP */
	private String lastLoginIp;

	/** 标记此条记录的状态Y:可用 N:不可用 */
	private String isactive;

	

	/** 实名认证时间 */
	private Timestamp realnameAuthDate;

	/** 实名注册时间 */
	private Timestamp realnameRegDate;

	/** 0:启用：已经开卡状态（系统资源使用数即为此状态的数据） 1:激活：有积分 或 消费者已实名注册 */
	private Integer hsCardActiveStatus;

	/** 是否绑定银行卡1:已绑定 0: 未绑定 */
	private Integer isBindBank;
	
	
	
	public Integer getIsBindBank() {
		return isBindBank;
	}

	public void setIsBindBank(Integer isBindBank) {
		this.isBindBank = isBindBank;
	}

	/**
	 * @return the 客户号
	 */
	public String getPerCustId() {
		return perCustId;
	}

	/**
	 * @param 客户号
	 *            the perCustId to set
	 */
	public void setPerCustId(String perCustId) {
		this.perCustId = perCustId == null ? null : perCustId.trim();
	}

	/**
	 * @return the 互生号
	 */
	public String getPerResNo() {
		return perResNo;
	}

	/**
	 * @param 互生号
	 *            the perResNo to set
	 */
	public void setPerResNo(String perResNo) {
		this.perResNo = perResNo == null ? null : perResNo.trim();
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
		this.entResNo = entResNo == null ? null : entResNo.trim();
	}

	/**
	 * @return the 手机号码
	 */
	public String getMobile() {
		return mobile;
	}

	/**
	 * @param 手机号码
	 *            the mobile to set
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile == null ? null : mobile.trim();
	}

	/**
	 * @return the 邮箱
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param 邮箱
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email == null ? null : email.trim();
	}

	/**
	 * @return the 指纹
	 */
	public String getFingerprint() {
		return fingerprint;
	}

	/**
	 * @param 指纹
	 *            the fingerprint to set
	 */
	public void setFingerprint(String fingerprint) {
		this.fingerprint = fingerprint == null ? null : fingerprint.trim();
	}

	
	/**
	 * @return the 交易密码
	 */
	public String getPwdTrans() {
		return pwdTrans;
	}

	/**
	 * @param 交易密码
	 *            the pwdTrans to set
	 */
	public void setPwdTrans(String pwdTrans) {
		this.pwdTrans = pwdTrans == null ? null : pwdTrans.trim();
	}

	/**
	 * @return the 交易密码版本
	 */
	public String getPwdTransVer() {
		return pwdTransVer;
	}

	/**
	 * @param 交易密码版本
	 *            the pwdTransVer to set
	 */
	public void setPwdTransVer(String pwdTransVer) {
		this.pwdTransVer = pwdTransVer == null ? null : pwdTransVer.trim();
	}

	

	/**
	 * @return the 交易密码盐值
	 */
	public String getPwdTransSaltValue() {
		return pwdTransSaltValue;
	}

	/**
	 * @param 交易密码盐值
	 *            the pwdTransSaltValue to set
	 */
	public void setPwdTransSaltValue(String pwdTransSaltValue) {
		this.pwdTransSaltValue = pwdTransSaltValue == null ? null : pwdTransSaltValue.trim();
	}

	/**
	 * @return the 基本状态1：启用、2：曾经登录、3：激活、4：活跃、5：休眠、6：沉淀、7：注销
	 */
	public Integer getBaseStatus() {
		return baseStatus;
	}

	/**
	 * @param 基本状态1
	 *            ：启用、2：曾经登录、3：激活、4：活跃、5：休眠、6：沉淀、7：注销 the baseStatus to set
	 */
	public void setBaseStatus(Integer baseStatus) {
		this.baseStatus = baseStatus;
	}

	/**
	 * @return the 实名状态1：未实名注册、2：已实名注册（有名字和身份证）、3:已实名认证
	 */
	public Integer getIsRealnameAuth() {
		return isRealnameAuth;
	}

	/**
	 * @param 实名状态1
	 *            ：未实名注册、2：已实名注册（有名字和身份证）、3:已实名认证 the isRealnameAuth to set
	 */
	public void setIsRealnameAuth(Integer isRealnameAuth) {
		this.isRealnameAuth = isRealnameAuth;
	}

	/**
	 * @return the 是否已验证邮箱1:已验证 0: 未验证
	 */
	public Integer getIsAuthEmail() {
		return isAuthEmail;
	}

	/**
	 * @param 是否已验证邮箱1
	 *            :已验证 0: 未验证 the isAuthEmail to set
	 */
	public void setIsAuthEmail(Integer isAuthEmail) {
		this.isAuthEmail = isAuthEmail;
	}

	/**
	 * @return the 是否已验证手机1:已验证 0: 未验证
	 */
	public Integer getIsAuthMobile() {
		return isAuthMobile;
	}

	/**
	 * @param 是否已验证手机1
	 *            :已验证 0: 未验证 the isAuthMobile to set
	 */
	public void setIsAuthMobile(Integer isAuthMobile) {
		this.isAuthMobile = isAuthMobile;
	}

	/**
	 * @return the 是否重要信息变更期间1:是0：否
	 */
	public Integer getIsKeyinfoChanged() {
		return isKeyinfoChanged;
	}

	/**
	 * @param 是否重要信息变更期间1
	 *            :是0：否 the isKeyinfoChanged to set
	 */
	public void setIsKeyinfoChanged(Integer isKeyinfoChanged) {
		this.isKeyinfoChanged = isKeyinfoChanged;
	}

	/**
	 * @return the 预留信息
	 */
	public String getEnsureInfo() {
		return ensureInfo;
	}

	/**
	 * @param 预留信息
	 *            the ensureInfo to set
	 */
	public void setEnsureInfo(String ensureInfo) {
		this.ensureInfo = ensureInfo == null ? null : ensureInfo.trim();
	}

	/**
	 * @return the 最近登录IP
	 */
	public String getLastLoginIp() {
		return lastLoginIp;
	}

	/**
	 * @param 最近登录IP
	 *            the lastLoginIp to set
	 */
	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp == null ? null : lastLoginIp.trim();
	}

	/**
	 * @return the 标记此条记录的状态Y:可用 N:不可用
	 */
	public String getIsactive() {
		return isactive;
	}

	/**
	 * @param 标记此条记录的状态Y
	 *            :可用 N:不可用 the isactive to set
	 */
	public void setIsactive(String isactive) {
		this.isactive = isactive == null ? null : isactive.trim();
	}

	
	/**
	 * @return the 最后一次积分时间
	 */
	public Timestamp getLastPointDate() {
		return lastPointDate;
	}

	/**
	 * @param 最后一次积分时间
	 *            the lastPointDate to set
	 */
	public void setLastPointDate(Timestamp lastPointDate) {
		this.lastPointDate = lastPointDate;
	}

	/**
	 * @return the 最近登录时间
	 */
	public Timestamp getLastLoginDate() {
		return lastLoginDate;
	}

	/**
	 * @param 最近登录时间
	 *            the lastLoginDate to set
	 */
	public void setLastLoginDate(Timestamp lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	/**
	 * @param 实名状态更新时间
	 *            the updateDate to set
	 */
	public Timestamp getRealnameAuthDate() {
		return realnameAuthDate;
	}

	/**
	 * @return the 实名状态更新时间
	 */
	public void setRealnameAuthDate(Timestamp realnameAuthDate) {
		this.realnameAuthDate = realnameAuthDate;
	}

	/**
	 * @return the 0:启用：已经开卡状态（系统资源使用数即为此状态的数据） 1:激活：有积分 或 消费者已实名注册
	 */
	public Integer getHsCardActiveStatus() {
		return hsCardActiveStatus;
	}

	/**
	 * @param 0:启用：已经开卡状态（系统资源使用数即为此状态的数据） 1:激活：有积分 或 消费者已实名注册 the updateDate to
	 *        set
	 */
	public void setHsCardActiveStatus(Integer hsCardActiveStatus) {
		this.hsCardActiveStatus = hsCardActiveStatus;
	}

	/**
	 * @return the 实名注册时间
	 */
	public Timestamp getRealnameRegDate() {
		return realnameRegDate;
	}

	/**
	 * @param 实名注册时间
	 *            the realnameRegDate to set
	 */
	public void setRealnameRegDate(Timestamp realnameRegDate) {
		this.realnameRegDate = realnameRegDate;
	}

	/**
	 * 使用CardHolder对象信息覆盖更新
	 * 
	 * @param cardHolder
	 *            持卡人信息
	 */
	public void setParams(CardHolder cardHolder) {
		if (!StringUtils.isBlank(cardHolder.getPerCustId())) {
			this.perCustId = cardHolder.getPerCustId().trim();
		}
		if (!StringUtils.isBlank(cardHolder.getPerResNo())) {
			this.perResNo = cardHolder.getPerResNo().trim();
		}
		if (!StringUtils.isBlank(cardHolder.getEntResNo())) {
			this.entResNo = cardHolder.getEntResNo().trim();
		}
		if (!StringUtils.isBlank(cardHolder.getMobile())) {
			this.mobile = cardHolder.getMobile().trim();
		}
		if (!StringUtils.isBlank(cardHolder.getEmail())) {
			this.email = cardHolder.getEmail().trim();
		}
		if (!StringUtils.isBlank(cardHolder.getFingerprint())) {
			this.fingerprint = cardHolder.getFingerprint().trim();
		}
		if (!StringUtils.isBlank(cardHolder.getPwdLogin())) {
			this.setPwdLogin(cardHolder.getPwdLogin().trim());
		}
		if (!StringUtils.isBlank(cardHolder.getPwdLoginVer())) {
			this.setPwdLoginVer(cardHolder.getPwdLoginVer().trim());
		}
		if (!StringUtils.isBlank(cardHolder.getPwdTrans())) {
			this.pwdTrans = cardHolder.getPwdTrans().trim();
		}
		if (!StringUtils.isBlank(cardHolder.getPwdTransVer())) {
			this.pwdTransVer = cardHolder.getPwdTransVer().trim();
		}
		if (!StringUtils.isBlank(cardHolder.getPwdLoginSaltValue())) {
			this.setPwdLoginSaltValue(cardHolder.getPwdLoginSaltValue().trim());
		}
		if (!StringUtils.isBlank(cardHolder.getPwdTransSaltValue())) {
			this.pwdTransSaltValue = cardHolder.getPwdTransSaltValue().trim();
		}
		if (null != cardHolder.getBaseStatus()) {
			this.baseStatus = cardHolder.getBaseStatus();
		}
		if (null != cardHolder.getIsRealnameAuth()) {
			this.isRealnameAuth = cardHolder.getIsRealnameAuth();
		}
		if (null != cardHolder.getIsAuthEmail()) {
			this.isAuthEmail = cardHolder.getIsAuthEmail();
		}
		if (null != cardHolder.getIsAuthMobile()) {
			this.isAuthMobile = cardHolder.getIsAuthMobile();
		}
		if (null != cardHolder.getIsKeyinfoChanged()) {
			this.isKeyinfoChanged = cardHolder.getIsKeyinfoChanged();
		}
		if (!StringUtils.isBlank(cardHolder.getEnsureInfo())) {
			this.ensureInfo = cardHolder.getEnsureInfo().trim();
		}
		if (null != lastPointDate) {
			this.lastPointDate = cardHolder.getLastPointDate();
		}
		if (null != cardHolder.getLastLoginDate()) {
			this.lastLoginDate = cardHolder.getLastLoginDate();
		}
		if (!StringUtils.isBlank(cardHolder.getLastLoginIp())) {
			this.lastLoginIp = cardHolder.getLastLoginIp().trim();
		}
	}

	public String toString() {
		return JSONObject.toJSONString(this);
	}
}