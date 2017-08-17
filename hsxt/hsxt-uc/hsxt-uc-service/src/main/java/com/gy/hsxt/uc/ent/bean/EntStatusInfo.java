package com.gy.hsxt.uc.ent.bean;

import static com.gy.hsxt.common.utils.StringUtils.isNotBlank;

import java.sql.Timestamp;

import com.alibaba.fastjson.JSONObject;
import com.gy.hsxt.common.utils.DateUtil;
import com.gy.hsxt.common.utils.StringUtils;
import com.gy.hsxt.uc.as.bean.ent.AsEntStatusInfo;

public class EntStatusInfo {

	private static final long serialVersionUID = 1L;

	/** 企业客户号 */
	private String entCustId;
	/** 客户类型 */
	private Integer custType;
	/** 企业互生号 */
	private String entResNo;
	/** 交易密码 */
	private String pwdTrans;
	/** 交易密码版本 */
	private String pwdTransVer;
	/** 交易密码盐值交易密码加密的盐值 */
	private String pwdTransSaltValue;
	/**
	 * "基本状态1：正常(NORMAL) 成员企业、托管企业用 2：预警(WARNING) 成员企业、托管企业用 3：休眠(DORMANT) 成员企业用
	 * 4：长眠（申请注销）成员企业 5：已注销 成员企业 6：申请停止积分活动中 托管企业用 7：停止积分活动 托管企业用"
	 */
	private Integer baseStatus;
	/** 实名认证状态1.已认证、0.未认证 */
	private Integer isRealnameAuth;
	/** 是否重要信息变更期间1:是 0：否 */
	private Integer isKeyinfoChanged;
	/** 是否已注册1:是 0：否 */
	private Integer isReg;
	/** 是否欠年费1:是 0：否 */
	private Integer isOweFee;
	/** 是否老企业1:是 0：否 */
	private Integer isOldEnt;
	/** 是否验证手机1:是 0：否 */
	private Integer isAuthMobile;
	/** 是否验证邮箱1:是 0：否 */
	private Integer isAuthEmail;
	/** 企业是否关闭（冻结）1:是 0：否 */
	private Integer isClosedEnt;
	/** 最后一次积分时间 */
	private Timestamp lastPointDate;
	/** 系统开启时间 */
	private Timestamp openDate;
	/** 企业缴年费截止日期 */
	private Timestamp expireDate;
	/** 企业购买积分卡数量 */
	private Long countBuyCards;
	/** 标记此条记录的状态Y:可用 N:不可用 */
	private String isactive;
	/** 创建时间创建时间，取记录创建时的系统时间 */
	private Timestamp createDate;
	/** 创建人由谁创建，值为用户的伪键ID */
	private String createdby;
	/** 更新时间更新时间，取记录更新时的系统时间 */
	private Timestamp updateDate;
	/** 更新人由谁更新，值为用户的伪键ID */
	private String updatedby;
	/** 企业的中文名称 */
	private String entCustName;
	
	/** 是否绑定银行卡1:是 0：否 */
	private Integer isBindBank;

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
	 * @return the 企业的中文名称
	 */
	public String getEntCustName() {
		return entCustName;
	}

	/**
	 * @param 企业的中文名称
	 *            the entCustId to set
	 */
	public void setEntCustName(String entCustName) {
		this.entCustName = entCustName;
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
		this.pwdTrans = pwdTrans;
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
		this.pwdTransVer = pwdTransVer;
	}

	/**
	 * @return the 交易密码盐值交易密码加密的盐值
	 */
	public String getPwdTransSaltValue() {
		return pwdTransSaltValue;
	}

	/**
	 * @param 交易密码盐值交易密码加密的盐值
	 *            the pwdTransSaltValue to set
	 */
	public void setPwdTransSaltValue(String pwdTransSaltValue) {
		this.pwdTransSaltValue = pwdTransSaltValue;
	}

	/**
	 * @return the
	 *         "基本状态1：正常(NORMAL)成员企业、托管企业用2：预警(WARNING)成员企业、托管企业用3：休眠(DORMANT)成员企业用4：长眠（申请注销）成员企业5：已注销成员企业6：申请停止积分活动中托管企业用7：停止积分活动托管企业用"
	 */
	public Integer getBaseStatus() {
		return baseStatus;
	}

	/**
	 * @param 
	 *        "基本状态1：正常(NORMAL)成员企业、托管企业用2：预警(WARNING)成员企业、托管企业用3：休眠(DORMANT)成员企业用4：长眠（申请注销）成员企业5：已注销成员企业6：申请停止积分活动中托管企业用7：停止积分活动托管企业用"
	 *        the baseStatus to set
	 */
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

	
	public Integer getIsBindBank() {
		return isBindBank;
	}

	public void setIsBindBank(Integer isBindBank) {
		this.isBindBank = isBindBank;
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
	 * @return the 系统开启时间
	 */
	public Timestamp getOpenDate() {
		return openDate;
	}

	/**
	 * @param 系统开启时间
	 *            the openDate to set
	 */
	public void setOpenDate(Timestamp openDate) {
		this.openDate = openDate;
	}

	/**
	 * @return the 企业缴年费截止日期
	 */
	public Timestamp getExpireDate() {
		return expireDate;
	}

	/**
	 * @param 企业缴年费截止日期
	 *            the expireDate to set
	 */
	public void setExpireDate(Timestamp expireDate) {
		this.expireDate = expireDate;
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
	 * @return the 标记此条记录的状态Y:可用N:不可用
	 */
	public String getIsactive() {
		return isactive;
	}

	/**
	 * @param 标记此条记录的状态Y
	 *            :可用N:不可用 the isactive to set
	 */
	public void setIsactive(String isactive) {
		this.isactive = isactive;
	}

	/**
	 * @return the 创建时间创建时间，取记录创建时的系统时间
	 */
	public Timestamp getCreateDate() {
		return createDate;
	}

	/**
	 * @param 创建时间创建时间
	 *            ，取记录创建时的系统时间 the createDate to set
	 */
	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

	/**
	 * @return the 创建人由谁创建，值为用户的伪键ID
	 */
	public String getCreatedby() {
		return createdby;
	}

	/**
	 * @param 创建人由谁创建
	 *            ，值为用户的伪键ID the createdby to set
	 */
	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}

	/**
	 * @return the 更新时间更新时间，取记录更新时的系统时间
	 */
	public Timestamp getUpdateDate() {
		return updateDate;
	}

	/**
	 * @param 更新时间更新时间
	 *            ，取记录更新时的系统时间 the updateDate to set
	 */
	public void setUpdateDate(Timestamp updateDate) {
		this.updateDate = updateDate;
	}

	/**
	 * @return the 更新人由谁更新，值为用户的伪键ID
	 */
	public String getUpdatedby() {
		return updatedby;
	}

	/**
	 * @param 更新人由谁更新
	 *            ，值为用户的伪键ID the updatedby to set
	 */
	public void setUpdatedby(String updatedby) {
		this.updatedby = updatedby;
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

	/**
	 * 生成 AsEntStatusInfo 对象
	 * 
	 * @return
	 */
	public AsEntStatusInfo buildAsEntStatusInfo() {
		AsEntStatusInfo statusInfo = new AsEntStatusInfo();
		statusInfo.setCustType(this.custType);
		statusInfo.setBaseStatus(this.baseStatus);
		statusInfo.setCountBuyCards(this.countBuyCards);
		statusInfo.setEntCustId(this.entCustId);
		statusInfo.setEntResNo(this.entResNo);
		statusInfo.setIsAuthEmail(this.isAuthEmail);
		statusInfo.setIsAuthMobile(this.isAuthMobile);
		statusInfo.setIsClosedEnt(this.isClosedEnt);
		statusInfo.setIsMaininfoChanged(this.isKeyinfoChanged);
		statusInfo.setIsOldEnt(this.isOldEnt);
		statusInfo.setIsOweFee(this.isOweFee);
		statusInfo.setIsRealnameAuth(this.isRealnameAuth);
		statusInfo.setIsReg(this.isReg);
		statusInfo.setEntCustName(this.entCustName);
		if (null != lastPointDate) {
			statusInfo.setLastPointDate(DateUtil.timestampToString(lastPointDate,
					"yyyy-MM-dd HH:mm:ss"));
		}
		if (null != openDate) {
			statusInfo.setOpenDate(DateUtil.timestampToString(openDate,
					"yyyy-MM-dd HH:mm:ss"));
		}
		if (null != expireDate) {
			statusInfo.setExpireDate(DateUtil.timestampToString(expireDate,
					"yyyy-MM-dd HH:mm:ss"));
		}
		if(null != isBindBank){
			statusInfo.setIsBindBank(isBindBank);
		}
		statusInfo.setHaveTradePwd(isNotBlank((this.pwdTrans)));
		return statusInfo;
	}

	/**
	 * 把AsEntStatusInfo 对象转换 成 EntStatusInfo对象
	 * 
	 * @param asEntStatusInfo
	 * @return
	 */
	public static EntStatusInfo generateEntStatusInfo(
			AsEntStatusInfo asEntStatusInfo) {
		EntStatusInfo dbEntStatusInfo = new EntStatusInfo();
		dbEntStatusInfo.setCustType(asEntStatusInfo.getCustType());
		dbEntStatusInfo.setBaseStatus(asEntStatusInfo.getBaseStatus());
		dbEntStatusInfo.setCountBuyCards(asEntStatusInfo.getCountBuyCards());
		dbEntStatusInfo.setEntCustId(asEntStatusInfo.getEntCustId());
		String expireDate = asEntStatusInfo.getExpireDate();
		if (StringUtils.isNotBlank(expireDate)) {
			if(expireDate.length() < 11){
				expireDate = expireDate + " 00:00:00";
			}
			dbEntStatusInfo.setExpireDate(Timestamp.valueOf(expireDate));
		}
		dbEntStatusInfo.setIsAuthEmail(asEntStatusInfo.getIsAuthEmail());
		dbEntStatusInfo.setIsAuthMobile(asEntStatusInfo.getIsAuthMobile());
		dbEntStatusInfo.setIsClosedEnt(asEntStatusInfo.getIsClosedEnt());
		dbEntStatusInfo.setIsKeyinfoChanged(asEntStatusInfo
				.getIsMaininfoChanged());
		dbEntStatusInfo.setIsOldEnt(asEntStatusInfo.getIsOldEnt());
		dbEntStatusInfo.setIsOweFee(asEntStatusInfo.getIsOweFee());
		dbEntStatusInfo.setIsRealnameAuth(asEntStatusInfo.getIsRealnameAuth());
		dbEntStatusInfo.setIsReg(asEntStatusInfo.getIsReg());
		if (null != asEntStatusInfo.getLastPointDate()) {
			dbEntStatusInfo.setLastPointDate(Timestamp.valueOf(asEntStatusInfo
					.getLastPointDate()));
		}
		dbEntStatusInfo.setEntCustName(asEntStatusInfo.getEntCustName());
		return dbEntStatusInfo;
	}

	public String toString() {
		return JSONObject.toJSONString(this);
	}
}