package com.gy.hsxt.uc.common.bean;

import static com.gy.hsxt.common.utils.StringUtils.isNotBlank;

import java.io.Serializable;
import java.sql.Timestamp;

import org.springframework.beans.BeanUtils;

import com.alibaba.fastjson.JSONObject;
import com.gy.hsxt.uc.as.bean.common.AsBankAcctInfo;

/**
 * 消费者银行账户 信息
 * 
 * @Package: com.gy.hsxt.uc.common.bean
 * @ClassName: CustAccount
 * @Description: TODO
 * 
 * @author: huanggaoyang
 * @date: 2015-11-26 下午8:33:15
 * @version V1.0
 */
public class CustAccount implements Serializable {
	/** 消费者银行账户编号（主键序列号） */
	private Long accId;

	/** 消费者客户号 */
	private String perCustId;
	
	/** 消费者姓名 */
    private String realName;

	/** 消费者互生号 */
	private String perResNo;

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
	 * @return the 消费者银行账户编号（主键序列号）
	 */
	public Long getAccId() {
		return accId;
	}

	/**
	 * @param 消费者银行账户编号
	 *            （主键序列号） the accId to set
	 */
	public void setAccId(Long accId) {
		this.accId = accId;
	}

	/**
	 * @return the 消费者客户号
	 */
	public String getPerCustId() {
		return perCustId;
	}

	/**
	 * @param 消费者客户号
	 *            the perCustId to set
	 */
	public void setPerCustId(String perCustId) {
		this.perCustId = perCustId;
	}

	/**
	 * @return the 消费者互生号
	 */
	public String getPerResNo() {
		return perResNo;
	}

	/**
	 * @param 消费者互生号
	 *            the perResNo to set
	 */
	public void setPerResNo(String perResNo) {
		this.perResNo = perResNo;
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
	
	

	public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
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
	 * 转换 AsBankAcctInfo对象 为 CustAccount对象
	 * 
	 * @param accInfo
	 * @return
	 */
	public static CustAccount generateCustAccount(AsBankAcctInfo accInfo) {
		CustAccount custAcc = new CustAccount();
		BeanUtils.copyProperties(accInfo, custAcc);
		custAcc.setPerCustId(accInfo.getCustId());
		custAcc.setPerResNo(accInfo.getResNo());
		if (isNotBlank(accInfo.getBankCardType())) {
			custAcc.setBankCardType(Integer.parseInt(accInfo.getBankCardType()));
		}
		return custAcc;
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
		asAcc.setResNo(this.perResNo);
		asAcc.setCustId(this.perCustId);
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