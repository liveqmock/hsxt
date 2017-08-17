
package com.gy.hsxt.access.pos.model;

import java.math.BigDecimal;
import java.util.List;

/** 
 * @Description: 抵扣券参数信息

 * @author: liuzh 
 * @createDate: 2016年6月24日 上午10:09:07
 * @version V1.0 
 */

public class DeductionVoucherParam {
	/**
	 * 抵扣劵个数
	 */
	private int deductionVoucherCount;
	/**
	 * 抵扣券张数列表信息
	 */
	private List<Integer> deductionVoucherCountList;
	/**
	 * 抵扣劵面值 
	 */
	private BigDecimal deductionVoucherParValue;
	/**
	 * 抵扣劵金额占比 
	 */
	private BigDecimal deductionVoucherRate;
	
	
	
	public int getDeductionVoucherCount() {
		return deductionVoucherCount;
	}
	public void setDeductionVoucherCount(int deductionVoucherCount) {
		this.deductionVoucherCount = deductionVoucherCount;
	}
	public List<Integer> getDeductionVoucherCountList() {
		return deductionVoucherCountList;
	}
	public void setDeductionVoucherCountList(List<Integer> deductionVoucherCountList) {
		this.deductionVoucherCountList = deductionVoucherCountList;
	}
	public BigDecimal getDeductionVoucherParValue() {
		return deductionVoucherParValue;
	}
	public void setDeductionVoucherParValue(BigDecimal deductionVoucherParValue) {
		this.deductionVoucherParValue = deductionVoucherParValue;
	}
	public BigDecimal getDeductionVoucherRate() {
		return deductionVoucherRate;
	}
	public void setDeductionVoucherRate(BigDecimal deductionVoucherRate) {
		this.deductionVoucherRate = deductionVoucherRate;
	}
		
}


