package com.gy.hsxt.uc.common.bean;

import static com.gy.hsxt.common.utils.StringUtils.isNotBlank;

import java.io.Serializable;
import java.sql.Timestamp;

import org.springframework.beans.BeanUtils;

import com.alibaba.fastjson.JSONObject;
import com.gy.hsxt.uc.as.bean.common.AsBankAcctInfo;

/**
 * 企业银行账户信息
 * 
 * @Package: com.gy.hsxt.uc.common.bean
 * @ClassName: EntAccount
 * @Description: TODO
 * 
 * @author: huanggaoyang
 * @date: 2015-11-26 下午8:48:02
 * @version V1.0
 */
public class EntAccount implements Serializable {
	/** 企业银行账户编号（主键序列号） */
	private Long accId;

	/** 企业客户号 */
	private String entCustId;
	
	/** 企业名称 */
    private String entName;
    
    /** 企业名称（英文） */
    private String entNameEn;

	/** 企业互生号 */
	private String entResNo;

	/** 开户行 */
	private String bankName;

	/** 开户行代码 */
	private String bankCode;

	/** 账户户名 */
	private String bankAccName;

	/** 账户账号 */
	private String bankAccNo;

	/**
	 * 账户类型1：DR_CARD-借记卡 2：CR_CARD-贷记卡 3：PASSBOOK-存折
	 */
	private Integer bankCardType;

	/** 开户行国家代码 */
	private String countryNo;

	/** 开户行省代码 */
	private String provinceNo;

	/** 开户行城市代码 */
	private String cityNo;

	/** 开户支行名称 */
	private String bankBranch;

	/** 是否默认账户1:是 0：否 */
	private Integer isDefaultAccount;

	/** 是否已验证账户1:是 0：否 */
	private Integer isValidAccount;

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

	private static final long serialVersionUID = 1L;

	/**
	 * @return the 企业银行账户编号（主键序列号）
	 */
	public Long getAccId() {
		return accId;
	}

	/**
	 * @param 企业银行账户编号
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
	 * @return the 开户行
	 */
	public String getBankName() {
		return bankName;
	}

	/**
	 * @param 开户行
	 *            the bankName to set
	 */
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	/**
	 * @return the 开户行代码
	 */
	public String getBankCode() {
		return bankCode;
	}

	/**
	 * @param 开户行代码
	 *            the bankCode to set
	 */
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	/**
	 * @return the 账户户名
	 */
	public String getBankAccName() {
		return bankAccName;
	}

	/**
	 * @param 账户户名
	 *            the bankAccName to set
	 */
	public void setBankAccName(String bankAccName) {
		this.bankAccName = bankAccName;
	}

	/**
	 * @return the 账户账号
	 */
	public String getBankAccNo() {
		return bankAccNo;
	}

	/**
	 * @param 账户账号
	 *            the bankAccNo to set
	 */
	public void setBankAccNo(String bankAccNo) {
		this.bankAccNo = bankAccNo;
	}

	/**
	 * @return the 账户类型1：DR_CARD-借记卡2：CR_CARD-贷记卡3：PASSBOOK-存折
	 */
	public Integer getBankCardType() {
		return bankCardType;
	}

	/**
	 * @param 账户类型1
	 *            ：DR_CARD-借记卡2：CR_CARD-贷记卡3：PASSBOOK-存折 the bankCardType to set
	 */
	public void setBankCardType(Integer bankCardType) {
		this.bankCardType = bankCardType;
	}

	/**
	 * @return the 开户行国家代码
	 */
	public String getCountryNo() {
		return countryNo;
	}

	/**
	 * @param 开户行国家代码
	 *            the countryNo to set
	 */
	public void setCountryNo(String countryNo) {
		this.countryNo = countryNo;
	}

	/**
	 * @return the 开户行省代码
	 */
	public String getProvinceNo() {
		return provinceNo;
	}

	/**
	 * @param 开户行省代码
	 *            the provinceNo to set
	 */
	public void setProvinceNo(String provinceNo) {
		this.provinceNo = provinceNo;
	}

	/**
	 * @return the 开户行城市代码
	 */
	public String getCityNo() {
		return cityNo;
	}

	/**
	 * @param 开户行城市代码
	 *            the cityNo to set
	 */
	public void setCityNo(String cityNo) {
		this.cityNo = cityNo;
	}

	/**
	 * @return the 开户支行名称
	 */
	public String getBankBranch() {
		return bankBranch;
	}

	/**
	 * @param 开户支行名称
	 *            the bankBranch to set
	 */
	public void setBankBranch(String bankBranch) {
		this.bankBranch = bankBranch;
	}

	/**
	 * @return the 是否默认账户1:是0：否
	 */
	public Integer getIsDefaultAccount() {
		return isDefaultAccount;
	}

	/**
	 * @param 是否默认账户1
	 *            :是0：否 the isDefaultAccount to set
	 */
	public void setIsDefaultAccount(Integer isDefaultAccount) {
		this.isDefaultAccount = isDefaultAccount;
	}

	/**
	 * @return the 是否已验证账户1:是0：否
	 */
	public Integer getIsValidAccount() {
		return isValidAccount;
	}

	/**
	 * @param 是否已验证账户1
	 *            :是0：否 the isValidAccount to set
	 */
	public void setIsValidAccount(Integer isValidAccount) {
		this.isValidAccount = isValidAccount;
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

	
	public String getEntName() {
        return entName;
    }

    public void setEntName(String entName) {
        this.entName = entName;
    }

   

    public String getEntNameEn() {
        return entNameEn;
    }

    public void setEntNameEn(String entNameEn) {
        this.entNameEn = entNameEn;
    }

    /**
	 * AsBankAcctInfo 转换成 EntAccount
	 * 
	 * @param accInfo
	 * @return
	 */
	public static EntAccount generateEntAccount(AsBankAcctInfo accInfo) {
		EntAccount entAcc = new EntAccount();
		BeanUtils.copyProperties(accInfo, entAcc);
		entAcc.setEntCustId(accInfo.getCustId());
		entAcc.setEntResNo(accInfo.getResNo());
		if (isNotBlank(accInfo.getBankCardType())) {
			entAcc.setBankCardType(Integer.parseInt(accInfo.getBankCardType()));
		}
		return entAcc;
	}

	/**
	 * 生成 AsBankAcctInfo对象
	 * 
	 * @return
	 */
	public AsBankAcctInfo generateAsBankAcctInfo() {
		AsBankAcctInfo asAcc = new AsBankAcctInfo();
		BeanUtils.copyProperties(this, asAcc);
		asAcc.setAccId(this.accId);
		asAcc.setResNo(this.entResNo);
		asAcc.setCustId(this.entCustId);
		if (this.isDefaultAccount != null) {
			asAcc.setIsDefaultAccount(this.isDefaultAccount.toString());
		}
		if (bankCardType != null) {
			asAcc.setBankCardType(bankCardType.toString());
		}
		return asAcc;
	}

	public String toString() {
		return JSONObject.toJSONString(this);
	}
}