/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.access.pos.model;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;




/***************************************************************************
 * <PRE>
 *  Project Name    : PosServer
 * 
 *  Package Name    : com.gy.point.model
 * 
 *  File Name       : BatchUpload.java
 * 
 *  Creation Date   : 2014-6-11
 * 
 *  Author          : huangfuhua
 * 
 *  Purpose         : TODO 批上送
 *  pos终端输入
 * 
 *  History         : TODO
 * 
 * </PRE>
 ***************************************************************************/
public class BatchUploadPosIn{

	
	private int batchUploadCount;//本批上送笔数


	private List<PointDetail> batchUploadDetails;//批上送明细
	
	public BatchUploadPosIn(){}
	
	public BatchUploadPosIn(int batchUploadCount,List<PointDetail> batchUploadDetails){
		this.batchUploadCount = batchUploadCount;
		this.batchUploadDetails = batchUploadDetails;
	}
	
	public int getBatchUploadCount() {
		return batchUploadCount;
	}

	public void setBatchUploadCount(int batchUploadCount) {
		this.batchUploadCount = batchUploadCount;
	}
	
	public List<PointDetail> getBatchUploadDetails() {
		return batchUploadDetails;
	}

	public void setBatchUploadDetails(List<PointDetail> batchUploadDetails) {
		this.batchUploadDetails = batchUploadDetails;
	}

	@Override
	public String toString() {
//		String split = "|";
//		StringBuffer stringBuffer = new StringBuffer("");
//		stringBuffer.append("消息ID:"+this.getMessageId());
//		stringBuffer.append(split+"本批上送笔数:"+this.getBatchUploadCount());
//		stringBuffer.append(split+"网络管理信息码:"+this.getNetCode());
//		if(!"202".equals(this.getNetCode()) &&  !"207".equals(this.getNetCode())){
//			stringBuffer.append(split+"本批上送明细笔数:"+this.getBatchUploadDetails().size());
//		}
//		stringBuffer.append(split+"交易金额:"+this.getBatchUploadDetails().ge);
//		stringBuffer.append(split+"积分比例:"+this.getPointRate());
//		stringBuffer.append(split+"积分金额:"+this.getAssureOutValue());
//		stringBuffer.append(split+"本次积分:"+this.getPointValue());
//		stringBuffer.append(split+"pos终端交易码:"+this.getTermTradeCode());
//		stringBuffer.append(split+"pos终端交易流水号 :"+this.getPosRunCode());
//		stringBuffer.append(split+"pos终端编号:"+this.getPosNo());
//		stringBuffer.append(split+"企业管理号:"+this.getEntNo());
//		stringBuffer.append(split+"终端类型码:"+this.getTermTypeCode());
//		stringBuffer.append(split+"批次号:"+this.getBatchNo());
//		stringBuffer.append(split+"操作员编号:"+this.getOperNo());
		return ToStringBuilder.reflectionToString(this);
	}
}
