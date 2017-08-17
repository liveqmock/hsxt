package com.gy.hsxt.access.pos.model;

import java.math.BigDecimal;

import org.apache.commons.lang3.builder.ToStringBuilder;



/***************************************************************************
 * <PRE>
 *  Project Name    : PosServer
 * 
 *  Package Name    : com.gy.point.model
 * 
 *  File Name       : BatchSettle.java
 * 
 *  Creation Date   : 2014-6-11
 * 
 *  Author          : huangfuhua
 * 
 *  Purpose         : 批结算
 * pos终端输入
 * 
 *  History         : 
 * 
 * </PRE>
 ***************************************************************************/
public class BatchSettle {

	private String entNo;
	private String posNo;
	private String batchNo;
	private String channelType;

	/**
	 * 该批次内卡输入借记笔数
	 */
	private int inDebitCount = 0;
	/**
	 * 该批次内卡输入借记总金额
	 */
	private BigDecimal inDebitSum = new BigDecimal(0);
	/**
	 * 该批次内卡输入贷计笔数
	 */
	private int inCreditCount = 0;
	/**
	 * 该批次内卡输入贷计总金额
	 */
	private BigDecimal inCreditSum = new BigDecimal(0);
	/**
	 * 该批次内卡输入积分笔数
	 */
	private int inPointCount = 0;
	/**
	 * 该批次内卡输入积分总金额
	 */
	private BigDecimal inPointSum = new BigDecimal(0);
	/**
	 * 该批次内卡输入积分撤单笔数
	 */
	private int inPointCancelCount = 0;
	/**
	 * 该批次内卡输入积分撤单总金额
	 */
	private BigDecimal inPointCancelSum = new BigDecimal(0);

	/**
	 * 该批次内卡输入互生币支付笔数
	 */
	private int inHsbPayCount = 0;
	/**
	 * 该批次内卡输入互生币支付总金额
	 */
	private BigDecimal inHsbPaySum = new BigDecimal(0);
	/**
	 * 该批次内卡输入互生币撤单笔数
	 */
	private int inHsbCancelCount = 0;
	/**
	 * 该批次内卡输入互生币撤单总金额
	 */
	private BigDecimal inHsbCancelSum = new BigDecimal(0);
	/**
	 * 该批次内卡输入互生币退货笔数
	 */
	private int inHsbReturnCount = 0;
	/**
	 * 该批次内卡输入互生币退货总金额
	 */
	private BigDecimal inHsbReturnSum = new BigDecimal(0);
	//	/**
	//	 * 该批次内卡输入互生钱包支付笔数
	//	 */
	//	private int inHswPayCount = 0;
	//	/**
	//	 * 该批次内卡输入互生钱包支付总金额
	//	 */
	//	private BigDecimal inHswPaySum = 0;	
	//	/**
	//	 * 该批次内卡输入互生钱包撤单笔数
	//	 */
	//	private int inHswCancelCount = 0;
	//	/**
	//	 * 该批次内卡输入互生钱包撤单总金额
	//	 */
	//	private BigDecimal inHswCancelSum = 0;	
	//	/**
	//	 * 该批次内卡输入互生钱包退货笔数
	//	 */
	//	private int inHswReturnCount = 0;
	//	/**
	//	 * 该批次内卡输入互生钱包退货总金额
	//	 */
	//	private BigDecimal inHswReturnSum = 0;	
	/**
	 * 该批次内卡输入互生订单笔数
	 */
	private int inHsOrderCount = 0;
	/**
	 * 该批次内卡输入互生订单总金额
	 */
	private BigDecimal inHsOrderSum = new BigDecimal(0);
	/**
	 * 该批次内卡输入代兑互生币笔数
	 */
	private int inHsbCReChargeCount = 0;
	/**
	 * 该批次内卡输入代兑互生币总金额
	 */
	private BigDecimal inHsbCReChargeSum = new BigDecimal(0);
	/**
	 * 该批次内卡输入兑换互生币笔数
	 */
	private int inHsbBReChargeCount = 0;
	/**
	 * 该批次内卡输入兑换互生币总金额
	 */
	private BigDecimal inHsbBReChargeSum = new BigDecimal(0);
	
	/**
	 * 该批次外卡输入借记笔数
	 */
	private int inDebitCount_wild = 0;
	/**
	 * 该批次外卡输入借记总金额
	 */
	private BigDecimal inDebitSum_wild = new BigDecimal(0);
	/**
	 * 该批次外卡输入贷计笔数
	 */
	private int inCreditCount_wild = 0;
	/**
	 * 该批次外卡输入贷计总金额
	 */
	private BigDecimal inCreditSum_wild = new BigDecimal(0);
	/**
	 * 该批次外卡输入积分笔数
	 */
	private int inPointCount_wild = 0;
	/**
	 * 该批次外卡输入积分总金额
	 */
	private BigDecimal inPointSum_wild = new BigDecimal(0);
	/**
	 * 该批次外卡输入撤单笔数
	 */
	private int inCancelCount_wild = 0;
	/**
	 * 该批次外卡输入撤单总金额
	 */
	private BigDecimal inCancelSum_wild = new BigDecimal(0);

	/**
	 * 该批次外卡输入互生币支付笔数
	 */
	private int inHsbPayCount_wild = 0;
	/**
	 * 该批次外卡输入互生币支付总金额
	 */
	private BigDecimal inHsbPaySum_wild = new BigDecimal(0);
	/**
	 * 该批次外卡输入互生币撤单笔数
	 */
	private int inHsbCancelCount_wild = 0;
	/**
	 * 该批次外卡输入互生币撤单总金额
	 */
	private BigDecimal inHsbCancelSum_wild = new BigDecimal(0);
	/**
	 * 该批次外卡输入互生币退货笔数
	 */
	private int inHsbReturnCount_wild = 0;
	/**
	 * 该批次外卡输入互生币退货总金额
	 */
	private BigDecimal inHsbReturnSum_wild = new BigDecimal(0);
	//	/**
	//	 * 该批次外卡输入互生钱包支付笔数
	//	 */
	//	private int inHswPayCount_wild = 0;
	//	/**
	//	 * 该批次外卡输入互生钱包支付总金额
	//	 */
	//	private BigDecimal inHswPaySum_wild = 0;	
	//	/**
	//	 * 该批次外卡输入互生钱包撤单笔数
	//	 */
	//	private int inHswCancelCount_wild = 0;
	//	/**
	//	 * 该批次外卡输入互生钱包撤单总金额
	//	 */
	//	private BigDecimal inHswCancelSum_wild = 0;	
	//	/**
	//	 * 该批次外卡输入互生钱包退货笔数
	//	 */
	//	private int inHswReturnCount_wild = 0;
	//	/**
	//	 * 该批次外卡输入互生钱包退货总金额
	//	 */
	//	private BigDecimal inHswReturnSum_wild = 0;	
	/**
	 * 该批次外卡输入互生订单笔数
	 */
	private int inHsOrderCount_wild = 0;
	/**
	 * 该批次外卡输入互生订单总金额
	 */
	private BigDecimal inHsOrderSum_wild = new BigDecimal(0);
	
	private String settleResult = "0";//该批次内卡对帐结果   0:ISO保留,1:对账平,2:对账不平,3:出错
	private String settleResult_wild = "0";//该批次外卡对帐结果 0:ISO保留,1:对账平,2:对账不平,3:出错

	public BatchSettle() {
	}

	public BatchSettle(String batchNo, String posNo) {
		this.batchNo = batchNo;
		this.posNo = posNo;
	}

	public BatchSettle(String entNo, String posNo, String batchNo, int inPointCount, BigDecimal inPointSum,
			int inPointCancelCount, BigDecimal inPointCancelSum, int inPointCount_wild, BigDecimal inPointSum_wild, int inCancelCount_wild,
			BigDecimal inCancelSum_wild, String settleResult, String settleResult_wild) {
		this.entNo = entNo;
		this.posNo = posNo;
		this.batchNo = batchNo;
		this.inPointCount = inPointCount;
		this.inPointSum = inPointSum;
		this.inPointCancelCount = inPointCancelCount;
		this.inPointCancelSum = inPointCancelSum;
		this.inPointCount_wild = inPointCount_wild;
		this.inPointSum_wild = inPointSum_wild;
		this.inCancelCount_wild = inCancelCount_wild;
		this.inCancelSum_wild = inCancelSum_wild;
		this.settleResult = settleResult;
		this.settleResult_wild = settleResult_wild;
	}

	public BatchSettle(
				int inDebitCount, BigDecimal inDebitSum, 
				int inCreditCount, BigDecimal inCreditSum, 
				int inPointCount, BigDecimal inPointSum, 
				int inPointCancelCount, BigDecimal inPointCancelSum, 
				int inHsbPayCount, BigDecimal inHsbPaySum,
				int inHsbCancelCount, BigDecimal inHsbCancelSum, 
				int inHsbReturnCount, BigDecimal inHsbReturnSum,
				int inHsOrderCount, BigDecimal inHsOrderSum, 
				int inHsbCReChargeCount, BigDecimal inHsbCReChargeSum,
				int inHsbBReChargeCount, BigDecimal inHsbBReChargeSum,
				int inDebitCount_wild, BigDecimal inDebitSum_wild, 
				int inCreditCount_wild, BigDecimal inCreditSum_wild, 
				int inPointCount_wild, BigDecimal inPointSum_wild, 
				int inCancelCount_wild, BigDecimal inCancelSum_wild, 
				int inHsbPayCount_wild, BigDecimal inHsbPaySum_wild,
				int inHsbCancelCount_wild, BigDecimal inHsbCancelSum_wild, 
				int inHsbReturnCount_wild, BigDecimal inHsbReturnSum_wild,
				int inHsOrderCount_wild, BigDecimal inHsOrderSum_wild) {
		this.inDebitCount = inDebitCount;
		this.inDebitSum = inDebitSum;
		this.inCreditCount = inCreditCount;
		this.inCreditSum = inCreditSum;
		this.inPointCount = inPointCount;
		this.inPointSum = inPointSum;
		this.inPointCancelCount = inPointCancelCount;
		this.inPointCancelSum = inPointCancelSum;

		this.inHsbPayCount = inHsbPayCount;
		this.inHsbPaySum = inHsbPaySum;
		this.inHsbCancelCount = inHsbCancelCount;
		this.inHsbCancelSum = inHsbCancelSum;
		this.inHsbReturnCount = inHsbReturnCount;
		this.inHsbReturnSum = inHsbReturnSum;
		this.inHsOrderCount = inHsOrderCount;
		this.inHsOrderSum = inHsOrderSum;
		this.inHsbCReChargeCount = inHsbCReChargeCount;
		this.inHsbCReChargeSum = inHsbCReChargeSum;
		this.inHsbBReChargeCount = inHsbBReChargeCount;
		this.inHsbBReChargeSum = inHsbBReChargeSum;
		
		this.inDebitCount_wild = inDebitCount_wild;
		this.inDebitSum_wild = inDebitSum_wild;
		this.inCreditCount_wild = inCreditCount_wild;
		this.inCreditSum_wild = inCreditSum_wild;
		this.inPointCount_wild = inPointCount_wild;
		this.inPointSum_wild = inPointSum_wild;
		this.inCancelCount_wild = inCancelCount_wild;
		this.inCancelSum_wild = inCancelSum_wild;

		this.inHsbPayCount_wild = inHsbPayCount_wild;
		this.inHsbPaySum_wild = inHsbPaySum_wild;
		this.inHsbCancelCount_wild = inHsbCancelCount_wild;
		this.inHsbCancelSum_wild = inHsbCancelSum_wild;
		this.inHsbReturnCount_wild = inHsbReturnCount_wild;
		this.inHsbReturnSum_wild = inHsbReturnSum_wild;
		this.inHsOrderCount_wild = inHsOrderCount_wild;
		this.inHsOrderSum_wild = inHsOrderSum_wild;
	}

	public String getEntNo() {
		return entNo;
	}

	public void setEntNo(String entNo) {
		this.entNo = entNo;
	}

	public String getPosNo() {
		return posNo;
	}

	public void setPosNo(String posNo) {
		this.posNo = posNo;
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public int getInDebitCount() {
		return inDebitCount;
	}

	public void setInDebitCount(int inDebitCount) {
		this.inDebitCount = inDebitCount;
	}

	public BigDecimal getInDebitSum() {
		return inDebitSum;
	}

	public void setInDebitSum(BigDecimal inDebitSum) {
		this.inDebitSum = inDebitSum;
	}

	public int getInCreditCount() {
		return inCreditCount;
	}

	public void setInCreditCount(int inCreditCount) {
		this.inCreditCount = inCreditCount;
	}

	public BigDecimal getInCreditSum() {
		return inCreditSum;
	}

	public void setInCreditSum(BigDecimal inCreditSum) {
		this.inCreditSum = inCreditSum;
	}

	public int getInPointCount() {
		return inPointCount;
	}

	public void setInPointCount(int inPointCount) {
		this.inPointCount = inPointCount;
	}

	public BigDecimal getInPointSum() {
		return inPointSum;
	}

	public void setInPointSum(BigDecimal inPointSum) {
		this.inPointSum = inPointSum;
	}

	public int getInPointCancelCount() {
		return inPointCancelCount;
	}

	public void setInPointCancelCount(int inPointCancelCount) {
		this.inPointCancelCount = inPointCancelCount;
	}

	public BigDecimal getInPointCancelSum() {
		return inPointCancelSum;
	}

	public void setInPointCancelSum(BigDecimal inPointCancelSum) {
		this.inPointCancelSum = inPointCancelSum;
	}

	public int getInDebitCount_wild() {
		return inDebitCount_wild;
	}

	public void setinDebitCount_wild(int inDebitCount_wild) {
		this.inDebitCount_wild = inDebitCount_wild;
	}

	public BigDecimal getInDebitSum_wild() {
		return inDebitSum_wild;
	}

	public void setinDebitSum_wild(BigDecimal inDebitSum_wild) {
		this.inDebitSum_wild = inDebitSum_wild;
	}

	public int getInCreditCount_wild() {
		return inCreditCount_wild;
	}

	public void setinCreditCount_wild(int inCreditCount_wild) {
		this.inCreditCount_wild = inCreditCount_wild;
	}

	public BigDecimal getInCreditSum_wild() {
		return inCreditSum_wild;
	}

	public void setinCreditSum_wild(BigDecimal inCreditSum_wild) {
		this.inCreditSum_wild = inCreditSum_wild;
	}

	public int getInPointCount_wild() {
		return inPointCount_wild;
	}

	public void setinPointCount_wild(int inPointCount_wild) {
		this.inPointCount_wild = inPointCount_wild;
	}

	public BigDecimal getInPointSum_wild() {
		return inPointSum_wild;
	}

	public void setinPointSum_wild(BigDecimal inPointSum_wild) {
		this.inPointSum_wild = inPointSum_wild;
	}

	public int getInCancelCount_wild() {
		return inCancelCount_wild;
	}

	public void setinCancelCount_wild(int inCancelCount_wild) {
		this.inCancelCount_wild = inCancelCount_wild;
	}

	public BigDecimal getInCancelSum_wild() {
		return inCancelSum_wild;
	}

	public void setinCancelSum_wild(BigDecimal inCancelSum_wild) {
		this.inCancelSum_wild = inCancelSum_wild;
	}

	public String getSettleResult() {
		return settleResult;
	}

	public void setSettleResult(String settleResult) {
		this.settleResult = settleResult;
	}

	public String getSettleResult_wild() {
		return settleResult_wild;
	}

	public void setSettleResult_wild(String settleResult_wild) {
		this.settleResult_wild = settleResult_wild;
	}

	public int getInHsbPayCount() {
		return inHsbPayCount;
	}

	public void setInHsbPayCount(int inHsbPayCount) {
		this.inHsbPayCount = inHsbPayCount;
	}

	public BigDecimal getInHsbPaySum() {
		return inHsbPaySum;
	}

	public void setInHsbPaySum(BigDecimal inHsbPaySum) {
		this.inHsbPaySum = inHsbPaySum;
	}

	public int getInHsbCancelCount() {
		return inHsbCancelCount;
	}

	public void setInHsbCancelCount(int inHsbCancelCount) {
		this.inHsbCancelCount = inHsbCancelCount;
	}

	public BigDecimal getInHsbCancelSum() {
		return inHsbCancelSum;
	}

	public void setInHsbCancelSum(BigDecimal inHsbCancelSum) {
		this.inHsbCancelSum = inHsbCancelSum;
	}

	public int getInHsbReturnCount() {
		return inHsbReturnCount;
	}

	public void setInHsbReturnCount(int inHsbReturnCount) {
		this.inHsbReturnCount = inHsbReturnCount;
	}

	public BigDecimal getInHsbReturnSum() {
		return inHsbReturnSum;
	}

	public void setInHsbReturnSum(BigDecimal inHsbReturnSum) {
		this.inHsbReturnSum = inHsbReturnSum;
	}

	public int getInHsOrderCount() {
		return inHsOrderCount;
	}

	public void setInHsOrderCount(int inHsOrderCount) {
		this.inHsOrderCount = inHsOrderCount;
	}

	public BigDecimal getInHsOrderSum() {
		return inHsOrderSum;
	}

	public void setInHsOrderSum(BigDecimal inHsOrderSum) {
		this.inHsOrderSum = inHsOrderSum;
	}

	public int getInHsbCReChargeCount() {
		return inHsbCReChargeCount;
	}

	public void setInHsbCReChargeCount(int inHsbCReChargeCount) {
		this.inHsbCReChargeCount = inHsbCReChargeCount;
	}

	public BigDecimal getInHsbCReChargeSum() {
		return inHsbCReChargeSum;
	}

	public void setInHsbCReChargeSum(BigDecimal inHsbCReChargeSum) {
		this.inHsbCReChargeSum = inHsbCReChargeSum;
	}

	public int getInHsbPayCount_wild() {
		return inHsbPayCount_wild;
	}

	public void setinHsbPayCount_wild(int inHsbPayCount_wild) {
		this.inHsbPayCount_wild = inHsbPayCount_wild;
	}

	public BigDecimal getInHsbPaySum_wild() {
		return inHsbPaySum_wild;
	}

	public void setinHsbPaySum_wild(BigDecimal inHsbPaySum_wild) {
		this.inHsbPaySum_wild = inHsbPaySum_wild;
	}

	public int getInHsbCancelCount_wild() {
		return inHsbCancelCount_wild;
	}

	public void setinHsbCancelCount_wild(int inHsbCancelCount_wild) {
		this.inHsbCancelCount_wild = inHsbCancelCount_wild;
	}

	public BigDecimal getInHsbCancelSum_wild() {
		return inHsbCancelSum_wild;
	}

	public void setinHsbCancelSum_wild(BigDecimal inHsbCancelSum_wild) {
		this.inHsbCancelSum_wild = inHsbCancelSum_wild;
	}

	public int getInHsbReturnCount_wild() {
		return inHsbReturnCount_wild;
	}

	public void setinHsbReturnCount_wild(int inHsbReturnCount_wild) {
		this.inHsbReturnCount_wild = inHsbReturnCount_wild;
	}

	public BigDecimal getInHsbReturnSum_wild() {
		return inHsbReturnSum_wild;
	}

	public void setinHsbReturnSum_wild(BigDecimal inHsbReturnSum_wild) {
		this.inHsbReturnSum_wild = inHsbReturnSum_wild;
	}

	public int getInHsOrderCount_wild() {
		return inHsOrderCount_wild;
	}

	public void setinHsOrderCount_wild(int inHsOrderCount_wild) {
		this.inHsOrderCount_wild = inHsOrderCount_wild;
	}

	public BigDecimal getInHsOrderSum_wild() {
		return inHsOrderSum_wild;
	}

	public void setinHsOrderSum_wild(BigDecimal inHsOrderSum_wild) {
		this.inHsOrderSum_wild = inHsOrderSum_wild;
	}

	public int getInHsbBReChargeCount() {
		return inHsbBReChargeCount;
	}

	public void setInHsbBReChargeCount(int inHsbBReChargeCount) {
		this.inHsbBReChargeCount = inHsbBReChargeCount;
	}

	public BigDecimal getInHsbBReChargeSum() {
		return inHsbBReChargeSum;
	}

	public void setInHsbBReChargeSum(BigDecimal inHsbBReChargeSum) {
		this.inHsbBReChargeSum = inHsbBReChargeSum;
	}

	public String getChannelType() {
		return channelType;
	}

	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}

	@Override
	public String toString() {
		//		String split = "|";
		//		StringBuffer stringBuffer = new StringBuffer("");
		////		stringBuffer.append(split + "批次号:" + this.getBatchNo());
		//		stringBuffer.append(split + "该批次内卡输入借记笔数:" + this.getInDebitCount());
		//		stringBuffer.append(split + "该批次内卡输入借记总金额:" + this.getInDebitSum());
		//		stringBuffer.append(split + "该批次内卡输入贷计笔数:" + this.getInCreditCount());
		//		stringBuffer.append(split + "该批次内卡输入贷计总金额:" + this.getInCreditSum());
		//		stringBuffer.append(split + "该批次内卡输入积分笔数:" + this.getInPointCount());
		//		stringBuffer.append(split + "该批次内卡输入积分总金额:" + this.getInPointSum());
		//		stringBuffer.append(split + "该批次内卡输入撤单笔数:" + this.getInCancelCount());
		//		stringBuffer.append(split + "该批次内卡输入撤单总金额:" + this.getInCancelSum());
		//		
		//		stringBuffer.append(split + "该批次内卡输入互生币支付笔数:" + this.getInHsbPayCount());
		//		stringBuffer.append(split + "该批次内卡输入互生币支付总金额:" + this.getInHsbPaySum());
		//		stringBuffer.append(split + "该批次内卡输入互生币撤单笔数:" + this.getInHsbCancelCount());
		//		stringBuffer.append(split + "该批次内卡输入互生币撤单总金额:" + this.getInHsbCancelSum());
		//		stringBuffer.append(split + "该批次内卡输入互生币退货笔数:" + this.getInHsbReturnCount());
		//		stringBuffer.append(split + "该批次内卡输入互生币退货总金额:" + this.getInHsbReturnSum());		
		//		stringBuffer.append(split + "该批次内卡输入互生钱包支付笔数:" + this.getInHswPayCount());
		//		stringBuffer.append(split + "该批次内卡输入互生钱包支付总金额:" + this.getInHswPaySum());
		//		stringBuffer.append(split + "该批次内卡输入互生钱包撤单笔数:" + this.getInHswCancelCount());
		//		stringBuffer.append(split + "该批次内卡输入互生钱包撤单总金额:" + this.getInHswCancelSum());
		//		stringBuffer.append(split + "该批次内卡输入互生钱包退货笔数:" + this.getInHswReturnCount());
		//		stringBuffer.append(split + "该批次内卡输入互生钱包退货总金额:" + this.getInHswReturnSum());	
		//		stringBuffer.append(split + "该批次内卡输入互生订单笔数:" + this.getInHsOrderCount());
		//		stringBuffer.append(split + "该批次内卡输入互生订单总金额:" + this.getInHsOrderSum());
		//		stringBuffer.append(split + "该批次内卡输入代兑互生币笔数:" + this.getInHsbReChargeCount());
		//		stringBuffer.append(split + "该批次内卡输入代兑互生币总金额:" + this.getInHsbReChargeSum());
		//		
		//		stringBuffer.append(split + "该批次外卡输入借记笔数:" + this.getInDebitCount_wild());
		//		stringBuffer.append(split + "该批次外卡输入借记总金额:" + this.getInDebitSum_wild());
		//		stringBuffer.append(split + "该批次外卡输入贷计笔数:" + this.getInCreditCount_wild());
		//		stringBuffer.append(split + "该批次外卡输入贷计总金额:" + this.getInCreditSum_wild());
		//		stringBuffer.append(split + "该批次外卡输入积分笔数:" + this.getInPointCount_wild());
		//		stringBuffer.append(split + "该批次外卡输入积分总金额:" + this.getInPointSum_wild());
		//		stringBuffer.append(split + "该批次外卡输入撤单笔数:" + this.getInCancelCount_wild());
		//		stringBuffer.append(split + "该批次外卡输入撤单总金额:" + this.getInCancelSum_wild());
		//		
		//		stringBuffer.append(split + "该批次外卡输入互生币支付笔数:" + this.getInHsbPayCount_wild());
		//		stringBuffer.append(split + "该批次外卡输入互生币支付总金额:" + this.getInHsbPaySum_wild());
		//		stringBuffer.append(split + "该批次外卡输入互生币撤单笔数:" + this.getInHsbCancelCount_wild());
		//		stringBuffer.append(split + "该批次外卡输入互生币撤单总金额:" + this.getInHsbCancelSum_wild());
		//		stringBuffer.append(split + "该批次外卡输入互生币退货笔数:" + this.getInHsbReturnCount_wild());
		//		stringBuffer.append(split + "该批次外卡输入互生币退货总金额:" + this.getInHsbReturnSum_wild());		
		//		stringBuffer.append(split + "该批次外卡输入互生钱包支付笔数:" + this.getInHswPayCount_wild());
		//		stringBuffer.append(split + "该批次外卡输入互生钱包支付总金额:" + this.getInHswPaySum_wild());
		//		stringBuffer.append(split + "该批次外卡输入互生钱包撤单笔数:" + this.getInHswCancelCount_wild());
		//		stringBuffer.append(split + "该批次外卡输入互生钱包撤单总金额:" + this.getInHswCancelSum_wild());
		//		stringBuffer.append(split + "该批次外卡输入互生钱包退货笔数:" + this.getInHswReturnCount_wild());
		//		stringBuffer.append(split + "该批次外卡输入互生钱包退货总金额:" + this.getInHswReturnSum_wild());	
		//		stringBuffer.append(split + "该批次外卡输入互生订单笔数:" + this.getInHsOrderCount_wild());
		//		stringBuffer.append(split + "该批次外卡输入互生订单总金额:" + this.getInHsOrderSum_wild());
		//		stringBuffer.append(split + "该批次外卡输入代兑互生币笔数:" + this.getInHsbReChargeCount_wild());
		//		stringBuffer.append(split + "该批次外卡输入代兑互生币总金额:" + this.getInHsbReChargeSum_wild());		
		//		return stringBuffer.toString();
		// return PrintUtils.getString(this);
		return ToStringBuilder.reflectionToString(this);
	}

	/**
	 * 有些双精度型的，不是用==比较的。
	 * GengLian modified at 2015/02/17
	 */
	@Override
	public boolean equals(Object obj) {
//		return EqualsBuilder.reflectionEquals(this, obj);
		if(!(obj instanceof BatchSettle)){
			return false;
		}
		BatchSettle model = (BatchSettle)obj;
		return model.getInDebitCount() == inDebitCount && 
			model.getInDebitSum() == inDebitSum && 
			model.getInCreditCount() == inCreditCount && 
			model.getInCreditSum() == inCreditSum && 
			model.getInPointCount() == inPointCount && 
			model.getInPointSum() == inPointSum && 
			model.getInPointCancelCount() == inPointCancelCount && 
			model.getInPointCancelSum() == inPointCancelSum && 
			
			model.getInHsbPayCount() == inHsbPayCount && 
			model.getInHsbPaySum() == inHsbPaySum && 
			model.getInHsbCancelCount() == inHsbCancelCount && 
			model.getInHsbCancelSum() == inHsbCancelSum && 
			model.getInHsbReturnCount() == inHsbReturnCount && 
			model.getInHsbReturnSum() == inHsbReturnSum && 
		//			model.getInHswPayCount() == inHswPayCount && 
		//			model.getInHswPaySum() == inHswPaySum && 
		//			model.getInHswCancelCount() == inHswCancelCount && 
		//			model.getInHswCancelSum() == inHswCancelSum && 
		//			model.getInHswReturnCount() == inHswReturnCount && 
		//			model.getInHswReturnSum() == inHswReturnSum && 
		model.getInHsOrderCount() == inHsOrderCount && 
		model.getInHsOrderSum() == inHsOrderSum && 
		model.getInHsbCReChargeCount() == inHsbCReChargeCount && 
		model.getInHsbCReChargeSum() == inHsbCReChargeSum && 
		model.getInHsbBReChargeCount() == inHsbBReChargeCount && 
		model.getInHsbBReChargeSum() == inHsbBReChargeSum;		
	}
/*
	@Override
	public int hashCode() {
//		return HashCodeBuilder.reflectionHashCode(this);
		int result = 17;
		result = 37 * result + inDebitCount;
		long tolong = Double.doubleToLongBits(inDebitSum);
		result = 37 * result + (int) (tolong ^ (tolong >>> 32));
		result = 37 * result + inCreditCount;
		tolong = Double.doubleToLongBits(inCreditSum);
		result = 37 * result + (int) (tolong ^ (tolong >>> 32));
		result = 37 * result + inPointCount;
		tolong = Double.doubleToLongBits(inPointSum);
		result = 37 * result + (int) (tolong ^ (tolong >>> 32));
		result = 37 * result + inCancelCount;
		tolong = Double.doubleToLongBits(inCancelSum);
		result = 37 * result + (int) (tolong ^ (tolong >>> 32));
		
		result = 37 * result + inHsbPayCount;
		tolong = Double.doubleToLongBits(inHsbPaySum);
		result = 37 * result + (int) (tolong ^ (tolong >>> 32));
		result = 37 * result + inHsbCancelCount;
		tolong = Double.doubleToLongBits(inHsbCancelSum);
		result = 37 * result + (int) (tolong ^ (tolong >>> 32));
		result = 37 * result + inHsbReturnCount;
		tolong = Double.doubleToLongBits(inHsbReturnSum);
		result = 37 * result + (int) (tolong ^ (tolong >>> 32));
		//		result = 37 * result + inHswPayCount;
		//		tolong = Double.doubleToLongBits(inHswPaySum);
		//		result = 37 * result + (int) (tolong ^ (tolong >>> 32));
		//		result = 37 * result + inHswCancelCount;
		//		tolong = Double.doubleToLongBits(inHswCancelSum);
		//		result = 37 * result + (int) (tolong ^ (tolong >>> 32));
		//		result = 37 * result + inHswReturnCount;
		//		tolong = Double.doubleToLongBits(inHswReturnSum);
		//		result = 37 * result + (int) (tolong ^ (tolong >>> 32));
		result = 37 * result + inHsOrderCount;
		tolong = Double.doubleToLongBits(inHsOrderSum);
		result = 37 * result + (int) (tolong ^ (tolong >>> 32));
		result = 37 * result + inHsbCReChargeCount;
		tolong = Double.doubleToLongBits(inHsbCReChargeSum);
		result = 37 * result + (int) (tolong ^ (tolong >>> 32));	
		result = 37 * result + inHsbBReChargeCount;
		tolong = Double.doubleToLongBits(inHsbBReChargeSum);
		result = 37 * result + (int) (tolong ^ (tolong >>> 32));		
		return result;
	}*/
}
