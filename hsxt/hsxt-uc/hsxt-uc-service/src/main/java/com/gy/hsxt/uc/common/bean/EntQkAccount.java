package com.gy.hsxt.uc.common.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import org.springframework.beans.BeanUtils;
import static com.gy.hsxt.common.utils.StringUtils.isNotBlank;
import com.alibaba.fastjson.JSONObject;
import com.gy.hsxt.uc.as.bean.common.AsQkBank;

public class EntQkAccount implements Serializable{
  
	private static final long serialVersionUID = 1307636666819384152L;

	/** 企业快捷支付签约账户编号（主键序列号） */
	private Long accId;

	/** 企业客户号 */
	private String entCustId;

	/** 企业互生号 */
	private String entResNo;

	/** 开户行名称 */
	private String bankName;

	/** 开户行代码（银行代码） */
	private String bankCode;

	/** 银行卡号 */
	private String bankCardNo;
	
	/**
	 * 账户类型1：DR_CARD-借记卡 2：CR_CARD-贷记卡 3：PASSBOOK-存折
	 */
	private Integer bankCardType;

	/** 快捷支付签约号 */
	private String bankSignNo;

	/** 小额支付有效期 */
	private Timestamp smallPayExpireDate;

	/** 小额支付总限额 */
	private BigDecimal amountTotalLimit;

	/** 小额支付单笔限额 */
	private BigDecimal amountSingleLimit;

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
	
	/** 是否默认账户1:是 0：否*/
    private Integer isDefaultAccount;

	/**
	 * @return the 企业快捷支付签约账户编号（主键序列号）
	 */
	public Long getAccId() {
		return accId;
	}

	/**
	 * @param 企业快捷支付签约账户编号
	 *            （主键序列号） the accId to set
	 */
	public void setAccId(Long accId) {
		this.accId = accId;
	}

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

	/**
	 * @return the 开户行名称
	 */
	public String getBankName() {
		return bankName;
	}

	/**
	 * @param 开户行名称
	 *            the bankName to set
	 */
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	/**
	 * @return the 开户行代码（银行代码）
	 */
	public String getBankCode() {
		return bankCode;
	}

	/**
	 * @param 开户行代码
	 *            （银行代码） the bankCode to set
	 */
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	/**
	 * @return the 银行卡号
	 */
	public String getBankCardNo() {
		return bankCardNo;
	}

	/**
	 * @param 银行卡号
	 *            the bankCardNo to set
	 */
	public void setBankCardNo(String bankCardNo) {
		this.bankCardNo = bankCardNo;
	}

	/**
	 * @return the 快捷支付签约号
	 */
	public String getBankSignNo() {
		return bankSignNo;
	}

	/**
	 * @param 快捷支付签约号
	 *            the bankSignNo to set
	 */
	public void setBankSignNo(String bankSignNo) {
		this.bankSignNo = bankSignNo;
	}

	/**
	 * @return the 小额支付有效期
	 */
	public Timestamp getSmallPayExpireDate() {
		return smallPayExpireDate;
	}

	/**
	 * @param 小额支付有效期
	 *            the smallPayExpireDate to set
	 */
	public void setSmallPayExpireDate(Timestamp smallPayExpireDate) {
		this.smallPayExpireDate = smallPayExpireDate;
	}

	/**
	 * @return the 小额支付总限额
	 */
	public BigDecimal getAmountTotalLimit() {
		return amountTotalLimit;
	}

	/**
	 * @param 小额支付总限额
	 *            the amountTotalLimit to set
	 */
	public void setAmountTotalLimit(BigDecimal amountTotalLimit) {
		this.amountTotalLimit = amountTotalLimit;
	}

	/**
	 * @return the 小额支付单笔限额
	 */
	public BigDecimal getAmountSingleLimit() {
		return amountSingleLimit;
	}

	/**
	 * @param 小额支付单笔限额
	 *            the amountSingleLimit to set
	 */
	public void setAmountSingleLimit(BigDecimal amountSingleLimit) {
		this.amountSingleLimit = amountSingleLimit;
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

	public Integer getIsDefaultAccount() {
		return isDefaultAccount;
	}

	public void setIsDefaultAccount(Integer isDefaultAccount) {
		this.isDefaultAccount = isDefaultAccount;
	}

	/**
	 * @return the 账户类型1：DR_CARD-借记卡2：CR_CARD-贷记卡3：PASSBOOK-存折
	 */
	public Integer getBankCardType() {
		return bankCardType;
	}

	/**
	 * @param 账户类型1：DR_CARD-借记卡2：CR_CARD-贷记卡3：PASSBOOK-存折 the bankCardType to set
	 */
	public void setBankCardType(Integer bankCardType) {
		this.bankCardType = bankCardType;
	}

	/**
	 * AsQkBank 对象转成 EntQkAccount 对象
	 * 
	 * @param asQkBank
	 * @return
	 */
	public static EntQkAccount generateCustQkAccount(AsQkBank asQkBank) {
		EntQkAccount qkAcc = new EntQkAccount();
		BeanUtils.copyProperties(asQkBank, qkAcc);
		qkAcc.setEntCustId(asQkBank.getCustId());
		qkAcc.setEntResNo(asQkBank.getResNo());
		qkAcc.setBankSignNo(asQkBank.getSignNo());
		if(asQkBank.getAccId()!=null){
			qkAcc.setAccId(Long.valueOf(asQkBank.getAccId()));
		}
		if (isNotBlank(asQkBank.getAmountSingleLimit())) {
			qkAcc.setAmountSingleLimit(new BigDecimal(asQkBank
					.getAmountSingleLimit()));
		}
		if (isNotBlank(asQkBank.getAmountTotalLimit())) {
			qkAcc.setAmountTotalLimit(new BigDecimal(asQkBank
					.getAmountTotalLimit()));
		}
		if (isNotBlank(asQkBank.getSmallPayExpireDate())) {
			qkAcc.setSmallPayExpireDate(Timestamp.valueOf(asQkBank
					.getSmallPayExpireDate()));
		}
		return qkAcc;
	}

	/**
	 * 生成AsQkBank对象
	 * 
	 * @return
	 */
	public AsQkBank generateAsQkBank() {
		AsQkBank asQkBank = new AsQkBank();
		BeanUtils.copyProperties(this, asQkBank);
		asQkBank.setAccId(this.accId.toString());
		asQkBank.setCustId(this.entCustId);
		asQkBank.setResNo(this.entResNo);
		asQkBank.setSignNo(this.bankSignNo);
		if (this.amountSingleLimit != null) {
			asQkBank.setAmountSingleLimit(this.amountSingleLimit.toString());
		}
		if (this.amountTotalLimit != null) {
			asQkBank.setAmountTotalLimit(this.amountTotalLimit.toString());
		}
		if (this.smallPayExpireDate != null) {
			asQkBank.setSmallPayExpireDate(this.smallPayExpireDate.toString());
		}
		return asQkBank;
	}

	public String toString() {
		return JSONObject.toJSONString(this);
	}
	
	
}