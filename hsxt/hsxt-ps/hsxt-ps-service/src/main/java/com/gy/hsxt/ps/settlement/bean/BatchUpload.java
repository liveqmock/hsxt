package com.gy.hsxt.ps.settlement.bean;

import java.io.Serializable;

import com.gy.hsxt.ps.bean.BatUpload;

/**   
 * @description  批上送实体类
 * @author       chenhz
 * @createDate   2015-7-29 下午6:30:38  
 * @Company      深圳市归一科技研发有限公司
 * @version      v0.0.1
 */
public class BatchUpload extends BatUpload implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8457099530017082071L;
	
	/**	 *  明细金额对比是否一致	 */
	private  Integer settleResult;

	/**
	 * 获取明细金额对比是否一致
	 * @return settleResult 明细金额对比是否一致
	 */
	public Integer getSettleResult() {
		return settleResult;
	}

	/**
	 * 设置明细金额对比是否一致
	 * @param settleResult 明细金额对比是否一致
	 */
	public void setSettleResult(Integer settleResult) {
		this.settleResult = settleResult;
	}
	

}

