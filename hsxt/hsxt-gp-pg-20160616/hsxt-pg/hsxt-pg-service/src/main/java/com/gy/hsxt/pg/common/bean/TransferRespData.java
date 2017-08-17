/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.pg.common.bean;

import com.gy.hsxt.pg.bankadapter.common.utils.MoneyAmountHelper;
import com.gy.hsxt.pg.bankadapter.pingan.b2e.protocol.dto.body.bean.QryMaxBatchTransferResDetailDTO;
import com.gy.hsxt.pg.bankadapter.pingan.b2e.protocol.dto.body.bean.QrySingleTransferResDTO;
import com.gy.hsxt.pg.common.constant.Constant.PGAmountScale;
import com.gy.hsxt.pg.common.utils.StringUtils;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-pg-service
 * 
 *  Package Name    : com.gy.hsxt.pg.common.bean
 * 
 *  File Name       : TransferRespData.java
 * 
 *  Creation Date   : 2016年3月15日
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : none
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class TransferRespData {

	private String stt;
	private String isBack;
	private String submitTime;
	private String fee;
	private String frontLogNo;
	private String yhcljg;

	public TransferRespData(QrySingleTransferResDTO resBody) {
		this.stt = resBody.getStt();

		this.isBack = resBody.getIsBack();

		this.submitTime = resBody.getSubmitTime();

		// 银行手续费
		this.fee = MoneyAmountHelper.formatAmountScale(resBody.getFee(),
				PGAmountScale.SIX);

		// 银行流水号
		this.frontLogNo = resBody.getFrontLogNo();

		// 根据姜方春意见修改
		this.yhcljg = StringUtils.avoidNull(resBody.getYhcljg());
	}

	public TransferRespData(QryMaxBatchTransferResDetailDTO resBody) {

		this.stt = resBody.getStt();

		this.isBack = resBody.getIsBack();

		this.submitTime = resBody.getSubmitTime();

		// 银行手续费
		this.fee = MoneyAmountHelper.formatAmountScale(resBody.getFee(),
				PGAmountScale.SIX);

		// 银行流水号
		this.frontLogNo = resBody.getFrontLogNo();

		// 根据姜方春意见修改
		this.yhcljg = StringUtils.avoidNull(resBody.getYhcljg());
	}

	public String getStt() {
		return stt;
	}

	public void setStt(String stt) {
		this.stt = stt;
	}

	public String getIsBack() {
		return isBack;
	}

	public void setIsBack(String isBack) {
		this.isBack = isBack;
	}

	public String getSubmitTime() {
		return submitTime;
	}

	public void setSubmitTime(String submitTime) {
		this.submitTime = submitTime;
	}

	public String getFee() {
		return fee;
	}

	public void setFee(String fee) {
		this.fee = fee;
	}

	public String getFrontLogNo() {
		return frontLogNo;
	}

	public void setFrontLogNo(String frontLogNo) {
		this.frontLogNo = frontLogNo;
	}

	public String getYhcljg() {
		return yhcljg;
	}

	public void setYhcljg(String yhcljg) {
		this.yhcljg = yhcljg;
	}

	public static TransferRespData build(QrySingleTransferResDTO resBody) {
		return new TransferRespData(resBody);
	}

	public static TransferRespData build(QryMaxBatchTransferResDetailDTO resBody) {
		return new TransferRespData(resBody);
	}

}
