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

package com.gy.hsxt.ac.bean;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 *  账户分录信息对象
 *  @Project Name    : hsxt-ac-api
 *  @Package Name    : com.gy.hsxt.ac.bean
 *  @Author          : weixz
 *  @Description     : TODO
 *  @Creation Date   : 2015-8-25
 *  @version V1.0
 * 
 */
public class AccountEntry implements Serializable{

	private static final long serialVersionUID = -5440372174989646075L;

	/**	分录全局顺序号 */
	private   String       entryNo;
	
	/** 各系统分录序号 */
    private   String       sysEntryNo;
	
	/**	批任务名称 */
	private   String       batchJobName;
	                       
	/**	任务日期（YYYYMMDD）*/
	private   String       batchDate;
	
	/** 错误文件地址 */
	private   String       errorFileAddress;
	
	/**	客户全局编号 */
	private   String       custID;
	
	/**	互生号 */
	private   String       hsResNo;
	
	/** 客户类型 */
	private   Integer      custType;
	
	/**	批次号 */
	private   String       batchNo;
	
	/**	账户类型编码 */
	private   String       accType;
	
	/**	账户类型名称 */
	private   String       accName;

	/**	增向金额 */
	private   String       addAmount;
	
	/**	减向金额 */
	private   String       subAmount;
	
	/**	红冲标识 */
	private   Integer      writeBack; 	  
	
	/** 交易系统 **/
	private   String       transSys;
	
	/**	交易类型 */
	private   String       transType;
	
	/**	交易名称 */
	private   String       transName;
	
	/**	交易流水号	*/
	private   String       transNo;
	
	/**	交易时间 */
	private   String       transDate;
	
	/**会计日期 */
	private   String       fiscalDate ;
	
	/**	交易时间 */
	private   Timestamp    transDateTim;
	
	/**会计日期 */
	private   Timestamp    fiscalDateTim ;

	/**	关联交易类型 */
	private   String       relTransType;
	
	/**	关联交易流水号 */
	private   String       relTransNo;
	
	/** 关联各系统分录序号 */
    private   String       relSysEntryNo;
	
	/**	备注 */
	private   String       remark;

	/** 错误描述 */
	private   String       errorDescription;
	
	/**	创建时间 */
	private   String       createdDate;
	
	/**	更新时间 */
	private   String       updatedDate;
	
	/**	开始时间 */
	private   String       beginDate;
	
	/**	结束时间 */
	private   String       endDate;
	
	/** 开始时间 */
    private   Timestamp    beginDateTim;
    
    /** 结束时间 */
    private   Timestamp    endDateTim;

	/**	金额 */
	private   String       amount;
	
	/**	红冲金额 */
	private   String       writeBackedAmount;
	
	/** 账户余额 */
    private   String       accBalanceNew;
	
	/** 业务类型 
	 * (1：收入，2：支出，3：全部)
	 * */
	private   Integer 	   businessType;
	
	/** 扣款类型
     *  1：ONLY_ONE_ACCT_DEDUCT_SEQ 
     *  按顺序检查账户余额，每次只能扣减一个账户，如果所有账户都不能满足扣减金额，则返回提示，
     *  2：ONLY_OR_COMBILE_ACCT_DEDUCT_SEQ 
     *  按顺序检查账户余额，首先从第一个账户中扣减，如果第一个账户不足扣减，则从第二个账户继续扣减剩余部份，依此类推支出
     * */
    private   Integer      deductType;
	
	/**
	 * 扣款或者退款账户顺序排列字符串集
	 * （账户以分隔符"|"隔开，扣款或者退款顺序由左往右排列）
	 */
	private   String       accTypes;
	
	/**
     * 保底值
     */
    private   String       guaranteeIntegral;
    
    /**
     * 账户余额正负（1：正；2：负）
     */
    private	   String		positiveNegative;
    
    /**
     * 原始交易流水号
     */
    private   String       sourceTransNo;
	
	
	/**
	 * @return the 分录全局顺序号
	 */ 
	public String getEntryNo() {
		return entryNo;
	}

	/**
	 * @param 分录全局顺序号 the entryNo to set
	 */
	public void setEntryNo(String entryNo) {
		this.entryNo = entryNo;
	}

	/**
	 * @return the 账户类型编码
	 */
	public String getAccType() {
		return accType;
	}

	/**
	 * @param 账户类型编码 the accType to set
	 */
	public void setAccType(String accType) {
		this.accType = accType;
	}

	/**
	 * @return the 批任务名称
	 */
	public String getBatchJobName() {
		return batchJobName;
	}

	/**
	 * @param 批任务名称 the batchJobName to set
	 */
	public void setBatchJobName(String batchJobName) {
		this.batchJobName = batchJobName;
	}

	/**
	 * @return the 任务日期（YYYYMMDD）
	 */
	public String getErrorFileAddress() {
		return errorFileAddress;
	}


	public void setErrorFileAddress(String errorFileAddress) {
		this.errorFileAddress = errorFileAddress;
	}


	public String getBatchDate() {
		return batchDate;
	}

	/**
	 * @param 任务日期（YYYYMMDD） the batchDate to set
	 */
	public void setBatchDate(String batchDate) {
		this.batchDate = batchDate;
	}

	/**
	 * @return the 客户全局编号
	 */
	public String getCustID() {
		return custID;
	}

	/**
	 * @param 客户全局编号 the custID to set
	 */
	public void setCustID(String custID) {
		this.custID = custID;
	}

	/**
	 * @return the 互生号
	 */
	public String getHsResNo() {
		return hsResNo;
	}

	/**
	 * @param 互生号 the hsResNo to set
	 */
	public void setHsResNo(String hsResNo) {
		this.hsResNo = hsResNo;
	}

	public Integer getCustType() {
		return custType;
	}

	/**
	 * @return the 批次号
	 */

	public void setCustType(Integer custType) {
		this.custType = custType;
	}


	public String getBatchNo() {
		return batchNo;
	}

	/**
	 * @param 批次号 the batchNo to set
	 */
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	/**
	 * @return the 增向金额
	 */
	public String getAddAmount() {
		if(addAmount != null)
		{
			String a = "";
			if(addAmount.length()>1)
			{
				a = addAmount.substring(0, 1);
			}
			String b = "";
			if(addAmount.length()>2)
			{
				b = addAmount.substring(0, 2);
			}
			if(".".equals(a)){
				addAmount = 0 + addAmount;
			}
			if("-.".equals(b))
			{
				addAmount = a + 0 + addAmount.substring(1);
			}
		}
		return addAmount;
	}

	/**
	 * @param 增向金额 the addAmount to set
	 */
	public void setAddAmount(String addAmount) {
		this.addAmount = addAmount;
	}

	/**
	 * @return the 减向金额
	 */
	public String getSubAmount() {
		if(subAmount != null)
		{
			String a = "";
			if(subAmount.length()>1)
			{
				a = subAmount.substring(0, 1);
			}
			String b = "";
			if(subAmount.length()>2)
			{
				b = subAmount.substring(0, 2);
			}
			if(".".equals(a)){
				subAmount = 0 + subAmount;
			}
			if("-.".equals(b))
			{
				subAmount = a + 0 + subAmount.substring(1);
			}
		}
		return subAmount;
	}

	/**
	 * @param 减向金额 the subAmount to set
	 */
	public void setSubAmount(String subAmount) {
		this.subAmount = subAmount;
	}

	/**
	 * @return the 红冲标识
	 */
	public Integer getWriteBack() {
		return writeBack;
	}

	/**
	 * @param 红冲标识 the writeBack to set
	 */
	public void setWriteBack(Integer writeBack) {
		this.writeBack = writeBack;
	}

	/**
	 * 获取交易系统
	 * @return transSys 交易系统
	 */
	public String getTransSys() {
		return transSys;
	}

	/**
	 * 设置交易系统
	 * @param transSys 交易系统
	 */
	public void setTransSys(String transSys) {
		this.transSys = transSys;
	}

	/**
	 * @return the 交易类型
	 */
	public String getTransType() {
		return transType;
	}

	/**
	 * @param 交易类型 the transType to set
	 */
	public void setTransType(String transType) {
		this.transType = transType;
	}

	/**
	 * @return the 交易流水号
	 */
	public String getTransNo() {
		return transNo;
	}

	/**
	 * @param 交易流水号 the transNo to set
	 */
	public void setTransNo(String transNo) {
		this.transNo = transNo;
	}

	/**
	 * @return the 交易时间
	 */
	public String getTransDate() {
		return transDate;
	}

	/**
	 * @param 交易时间 the transDate to set
	 */
	public void setTransDate(String transDate) {
		this.transDate = transDate;
	}

	/**
	 * @return the 会计日期
	 */
	public String getFiscalDate() {
		return fiscalDate;
	}

	/**
	 * @param 会计日期 the piscalDate to set
	 */
	public void setFiscalDate(String fiscalDate) {
		this.fiscalDate = fiscalDate;
	}

	/**
	 * @return the 关联交易类型
	 */
	public String getRelTransType() {
		return relTransType;
	}

	/**
	 * @param 关联交易类型 the relTransType to set
	 */
	public void setRelTransType(String relTransType) {
		this.relTransType = relTransType;
	}

	/**
	 * @return the 关联交易流水号
	 */
	public String getRelTransNo() {
		return relTransNo;
	}

	/**
	 * @param 关联交易流水号 the relTransNo to set
	 */
	public void setRelTransNo(String relTransNo) {
		this.relTransNo = relTransNo;
	}

	/**
	 * @return the 备注
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * @param 备注 the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * @return the 创建时间
	 */
	public String getCreatedDate() {
		return createdDate;
	}

	/**
	 * @param 创建时间 the createdDate to set
	 */
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * @return the 更新时间
	 */
	public String getUpdatedDate() {
		return updatedDate;
	}

	/**
	 * @param 更新时间 the updatedDate to set
	 */
	public void setUpdatedDate(String updatedDate) {
		this.updatedDate = updatedDate;
	}

	/**
	 * @return the 创建时间
	 */
	public String getBeginDate() {
		return beginDate;
	}

	/**
	 * @param 创建时间 the beginDate to set
	 */
	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}
	
	/**
     * @return the 错误描述
     */
    public String getErrorDescription() {
        return errorDescription;
    }

    /**
     * @param 错误描述 the errorDescription to set
     */
    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }

    /**
	 * @return the 结束时间
	 */
	public String getEndDate() {
		return endDate;
	}

	/**
	 * @param 结束时间 the endDate to set
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return the 金额
	 */
	public String getAmount() {
		if(amount != null)
		{
			String a = "";
			if(amount.length()>1)
			{
				a = amount.substring(0, 1);
			}
			String b = "";
			if(amount.length()>2)
			{
				b = amount.substring(0, 2);
			}
			if(".".equals(a)){
				amount = 0 + amount;
			}
			if("-.".equals(b))
			{
				amount = a + 0 + amount.substring(1);
			}
		}
		return amount;
	}

	/**
	 * @param 金额 the amount to set
	 */
	public void setAmount(String amount) {
		this.amount = amount;
	}

	/**
	 * @return the writeBackedAmount
	 */
	public String getWriteBackedAmount() {
		if(writeBackedAmount != null)
		{
			String a = "";
			if(writeBackedAmount.length()>1)
			{
				a = writeBackedAmount.substring(0, 1);
			}
			String b = "";
			if(writeBackedAmount.length()>2)
			{
				b = writeBackedAmount.substring(0, 2);
			}
			if(".".equals(a)){
				writeBackedAmount = 0 + writeBackedAmount;
			}
			if("-.".equals(b))
			{
				writeBackedAmount = a + 0 + writeBackedAmount.substring(1);
			}
		}
		return writeBackedAmount;
	}

	/**
	 * @param writeBackedAmount the writeBackedAmount to set
	 */
	public void setWriteBackedAmount(String writeBackedAmount) {
		this.writeBackedAmount = writeBackedAmount;
	}

	/**
	 * @return the 账户类型名称
	 */
	public String getAccName() {
		return accName;
	}

	/**
	 * @param 账户类型名称 the accName to set
	 */
	public void setAccName(String accName) {
		this.accName = accName;
	}

	/**
	 * @return the 业务类型(1：收入，2：支出)
	 */
	public Integer getBusinessType() {
		return businessType;
	}

	/**
	 * @param 业务类型(1：收入，2：支出) the businessType to set
	 */
	public void setBusinessType(Integer businessType) {
		this.businessType = businessType;
	}

	/**
	 * @return the 交易名称
	 */
	public String getTransName() {
		return transName;
	}

	/**
	 * @param 交易名称 the transName to set
	 */
	public void setTransName(String transName) {
		this.transName = transName;
	}

    /**
     * @return the 交易时间
     */
    public Timestamp getTransDateTim() {
        return transDateTim;
    }

    /**
     * @param 交易时间 the transDateTim to set
     */
    public void setTransDateTim(Timestamp transDateTim) {
        this.transDateTim = transDateTim;
    }

    /**
     * @return the 会计日期
     */
    public Timestamp getFiscalDateTim() {
        return fiscalDateTim;
    }

    /**
     * @param 会计日期 the fiscalDateTim to set
     */
    public void setFiscalDateTim(Timestamp fiscalDateTim) {
        this.fiscalDateTim = fiscalDateTim;
    }

    /**
     * @return the 开始时间
     */
    public Timestamp getBeginDateTim() {
        return beginDateTim;
    }

    /**
     * @param 开始时间 the beginDateTim to set
     */
    public void setBeginDateTim(Timestamp beginDateTim) {
        this.beginDateTim = beginDateTim;
    }

    /**
     * @return the 结束时间
     */
    public Timestamp getEndDateTim() {
        return endDateTim;
    }

    /**
     * @param 结束时间 the endDateTim to set
     */
    public void setEndDateTim(Timestamp endDateTim) {
        this.endDateTim = endDateTim;
    }

    /**
     * @return the 扣款账户顺序排列字符串集（账户以分隔符"|"隔开，扣款顺序由左往右排列）
     */
    public String getAccTypes() {
        return accTypes;
    }

    /**
     * @param 扣款账户顺序排列字符串集（账户以分隔符"|"隔开，扣款顺序由左往右排列） the accTypes to set
     */
    public void setAccTypes(String accTypes) {
        this.accTypes = accTypes;
    }
    

    /**
     * @return the 账户余额
     */
    public String getAccBalanceNew() {
    	if(accBalanceNew != null)
		{
			String a = "";
			if(accBalanceNew.length()>1)
			{
				a = accBalanceNew.substring(0, 1);
			}
			String b = "";
			if(accBalanceNew.length()>2)
			{
				b = accBalanceNew.substring(0, 2);
			}
			if(".".equals(a)){
				accBalanceNew = 0 + accBalanceNew;
			}
			if("-.".equals(b))
			{
				accBalanceNew = a + 0 + accBalanceNew.substring(1);
			}
		}
        return accBalanceNew;
    }

    /**
     * @param 账户余额 the accBalanceNew to set
     */
    public void setAccBalanceNew(String accBalanceNew) {
        this.accBalanceNew = accBalanceNew;
    }

    /**
     * @return the 扣款类型
     * 1：ONLY_ONE_ACCT_DEDUCT_SEQ
     * 按顺序检查账户余额，每次只能扣减一个账户，如果所有账户都不能满足扣减金额，则返回提示，
     * 2：ONLY_OR_COMBILE_ACCT_DEDUCT_SEQ
     * 按顺序检查账户余额，首先从第一个账户中扣减，如果第一个账户不足扣减，则从第二个账户继续扣减剩余部份，依此类推支出
     */
    public Integer getDeductType() {
        return deductType;
    }

    /**
     * @param 扣款类型
     * 1：ONLY_ONE_ACCT_DEDUCT_SEQ
     * 按顺序检查账户余额，每次只能扣减一个账户，如果所有账户都不能满足扣减金额，则返回提示，
     * 2：ONLY_OR_COMBILE_ACCT_DEDUCT_SEQ
     * 按顺序检查账户余额，首先从第一个账户中扣减，如果第一个账户不足扣减，则从第二个账户继续扣减剩余部份，依此类推支出 
     * the deductType to set
     */
    public void setDeductType(Integer deductType) {
        this.deductType = deductType;
    }

    /**
     * @return the 各系统分录序号
     */
    public String getSysEntryNo() {
        return sysEntryNo;
    }

    /**
     * @param 各系统分录序号 the sysEntryNo to set
     */
    public void setSysEntryNo(String sysEntryNo) {
        this.sysEntryNo = sysEntryNo;
    }

    /**
     * @return the 关联各系统分录序号
     */
    public String getRelSysEntryNo() {
        return relSysEntryNo;
    }

    /**
     * @param 关联各系统分录序号 the relSysEntryNo to set
     */
    public void setRelSysEntryNo(String relSysEntryNo) {
        this.relSysEntryNo = relSysEntryNo;
    }

    /**
     * @return the 保底值
     */
    public String getGuaranteeIntegral() {
        return guaranteeIntegral;
    }

    /**
     * @param 保底值 the guaranteeIntegral to set
     */
    public void setGuaranteeIntegral(String guaranteeIntegral) {
        this.guaranteeIntegral = guaranteeIntegral;
    }

	/**
	 * @return the 账户余额正负（1：正；2：负）
	 */
	public String getPositiveNegative() {
		return positiveNegative;
	}

	/**
	 * @param 账户余额正负（1：正；2：负） the positiveNegative to set
	 */
	public void setPositiveNegative(String positiveNegative) {
		this.positiveNegative = positiveNegative;
	}

	/**
	 * @return the 原始交易流水号
	 */
	public String getSourceTransNo() {
		return sourceTransNo;
	}

	/**
	 * @param 原始交易流水号 the sourceTransNo to set
	 */
	public void setSourceTransNo(String sourceTransNo) {
		this.sourceTransNo = sourceTransNo;
	}

}
