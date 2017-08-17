/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */

package com.gy.hsxt.ws.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 福利资格实体
 * 
 * @Package: com.gy.hsxt.ws.bean
 * @ClassName: WelfareQualify
 * @Description: TODO
 * 
 * @author: huanggaoyang
 * @date: 2015-12-4 下午3:00:14
 * @version V1.0
 */
public class WelfareQualify implements Serializable {
	/** 福利保障单号 */
	private String welfareId;

	/** 客户号 */
	private String custId;

	/** 互生号 */
	private String hsResNo;

	/** 消费者姓名 */
	private String custName;

	/** 补贴总金额 */
	private String subsidyTotalAmount;

	/** 已发放的金额 */
	private String grantAmount;

	/** 补贴余额 */
	private String subsidyBalance;

	/** 福利分类 0 一年意外保障 1免费医疗补贴 */
	private Integer welfareType;

	/** 福利资格是否有效 0 无效 1 有效 */
	private Integer isvalid;

	/** 福利生效日期 */
	private String effectDate;

	/** 福利失效日期 */
	private String failureDate;

	/** 失效原因 1正常过期失效 2 保障升级而失效 3 持续7天积分不足而失效 4 其他原因失效 */
	private Integer failureReason;

	/** 福利持续失效累积天数 */
	private Integer durInvalidDays;

	/** 所有失效日期 */
	private List<String> allFailureDateList;

	/** 最后一次上报为失效的日期 */
	private String lastInvalidDate;

	/** 累计消费积分 */
	private String consumePoint;

	/** 累计投资积分 */
	private String investPoint;

	/** 福利资格生效时累计积分：一年意外保障为消费累计积分数，免费医疗补贴为投资累计积分数 */
	private String effectPoint;

	private static final long serialVersionUID = 1L;

	public String getWelfareId() {
		return welfareId;
	}

	public void setWelfareId(String welfareId) {
		this.welfareId = welfareId;
	}

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getHsResNo() {
		return hsResNo;
	}

	public void setHsResNo(String hsResNo) {
		this.hsResNo = hsResNo;
	}

	public String getSubsidyTotalAmount() {
		return subsidyTotalAmount;
	}

	public void setSubsidyTotalAmount(String subsidyTotalAmount) {
		this.subsidyTotalAmount = subsidyTotalAmount;
	}

	public String getGrantAmount() {
		return grantAmount;
	}

	public void setGrantAmount(String grantAmount) {
		this.grantAmount = grantAmount;
	}

	public String getSubsidyBalance() {
		return subsidyBalance;
	}

	public void setSubsidyBalance(String subsidyBalance) {
		this.subsidyBalance = subsidyBalance;
	}

	public Integer getWelfareType() {
		return welfareType;
	}

	public void setWelfareType(Integer welfareType) {
		this.welfareType = welfareType;
	}

	public Integer getIsvalid() {
		return isvalid;
	}

	public void setIsvalid(Integer isvalid) {
		this.isvalid = isvalid;
	}

	public String getEffectDate() {
		return effectDate;
	}

	public void setEffectDate(String effectDate) {
		this.effectDate = effectDate;
	}

	public String getFailureDate() {
		return failureDate;
	}

	public void setFailureDate(String failureDate) {
		this.failureDate = failureDate;
	}

	public Integer getFailureReason() {
		return failureReason;
	}

	public void setFailureReason(Integer failureReason) {
		this.failureReason = failureReason;
	}

	public Integer getDurInvalidDays() {
		return durInvalidDays;
	}

	public void setDurInvalidDays(Integer durInvalidDays) {
		this.durInvalidDays = durInvalidDays;
	}

	public List<String> getAllFailureDateList() {
		return allFailureDateList;
	}

	public void setAllFailureDateList(List<String> allFailureDateList) {
		this.allFailureDateList = allFailureDateList;
	}

	public String getLastInvalidDate() {
		return lastInvalidDate;
	}

	public void setLastInvalidDate(String lastInvalidDate) {
		this.lastInvalidDate = lastInvalidDate;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getConsumePoint() {
		return consumePoint;
	}

	public void setConsumePoint(String consumePoint) {
		this.consumePoint = consumePoint;
	}

	public String getInvestPoint() {
		return investPoint;
	}

	public void setInvestPoint(String investPoint) {
		this.investPoint = investPoint;
	}

	public String getEffectPoint() {
		return effectPoint;
	}

	public void setEffectPoint(String effectPoint) {
		this.effectPoint = effectPoint;
	}

}
