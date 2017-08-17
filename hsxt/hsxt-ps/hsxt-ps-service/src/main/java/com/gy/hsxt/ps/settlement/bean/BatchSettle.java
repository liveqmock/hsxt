package com.gy.hsxt.ps.settlement.bean;

import java.io.Serializable;
import java.math.BigDecimal;

import com.gy.hsxt.ps.bean.BatSettle;

/**
 * @description 批结算实体类
 * @author chenhz
 * @createDate 2015-7-29 下午6:30:38
 * @Company 深圳市归一科技研发有限公司
 * @version v0.0.1
 */
public class BatchSettle extends BatSettle implements Serializable
{

	private static final long serialVersionUID = -4891787427052992688L;

	/** *DETAIL表统计积分总数 */
	private BigDecimal detPoinsSum;

	/** *DETAIL表统计积分笔数 */
	private Integer detPoinsCnt;

	/** *DETAIL表统计积分撤单总数 */
	private BigDecimal detPcancelSum;

	/** *DETAIL表统计积分撤单笔数 */
	private Integer detPcancelCnt;

	/** *DETAIL表统计积分退货总数 */
	private BigDecimal detPbackSum;

	/** *DETAIL表统计积分退货笔数 */
	private Integer detPbackCnt;

	/** *对账结果 */
	private Integer settleResult;
	
	/** 批次号 **/
	private String batchNo;
	
	/** 批结算id **/
	private String id;

	/** * 互生币总金额 */
	private String hsbSum;

	/**
	 * 获取DETAIL表统计积分总数
	 * 
	 * @return detPoinsSum DETAIL表统计积分总数
	 */
	public BigDecimal getDetPoinsSum()
	{
		return detPoinsSum;
	}

	/**
	 * 设置DETAIL表统计积分总数
	 * 
	 * @param detPoinsSum
	 *            DETAIL表统计积分总数
	 */
	public void setDetPoinsSum(BigDecimal detPoinsSum)
	{
		this.detPoinsSum = detPoinsSum;
	}

	/**
	 * 获取DETAIL表统计积分笔数
	 * 
	 * @return detPoinsCnt DETAIL表统计积分笔数
	 */
	public Integer getDetPoinsCnt()
	{
		return detPoinsCnt;
	}

	/**
	 * 设置DETAIL表统计积分笔数
	 * 
	 * @param detPoinsCnt
	 *            DETAIL表统计积分笔数
	 */
	public void setDetPoinsCnt(Integer detPoinsCnt)
	{
		this.detPoinsCnt = detPoinsCnt;
	}

	/**
	 * 获取DETAIL表统计积分撤单总数
	 * 
	 * @return detPcancelSum DETAIL表统计积分撤单总数
	 */
	public BigDecimal getDetPcancelSum()
	{
		return detPcancelSum;
	}

	/**
	 * 设置DETAIL表统计积分撤单总数
	 * 
	 * @param detPcancelSum
	 *            DETAIL表统计积分撤单总数
	 */
	public void setDetPcancelSum(BigDecimal detPcancelSum)
	{
		this.detPcancelSum = detPcancelSum;
	}

	/**
	 * 获取DETAIL表统计积分撤单笔数
	 * 
	 * @return detPcancelCnt DETAIL表统计积分撤单笔数
	 */
	public Integer getDetPcancelCnt()
	{
		return detPcancelCnt;
	}

	/**
	 * 设置DETAIL表统计积分撤单笔数
	 * 
	 * @param detPcancelCnt
	 *            DETAIL表统计积分撤单笔数
	 */
	public void setDetPcancelCnt(Integer detPcancelCnt)
	{
		this.detPcancelCnt = detPcancelCnt;
	}

	/**
	 * 获取DETAIL表统计积分退货总数
	 * 
	 * @return detPbackSum DETAIL表统计积分退货总数
	 */
	public BigDecimal getDetPbackSum()
	{
		return detPbackSum;
	}

	/**
	 * 设置DETAIL表统计积分退货总数
	 * 
	 * @param detPbackSum
	 *            DETAIL表统计积分退货总数
	 */
	public void setDetPbackSum(BigDecimal detPbackSum)
	{
		this.detPbackSum = detPbackSum;
	}

	/**
	 * 获取DETAIL表统计积分退货笔数
	 * 
	 * @return detPbackCnt DETAIL表统计积分退货笔数
	 */
	public Integer getDetPbackCnt()
	{
		return detPbackCnt;
	}

	/**
	 * 设置DETAIL表统计积分退货笔数
	 * 
	 * @param detPbackCnt
	 *            DETAIL表统计积分退货笔数
	 */
	public void setDetPbackCnt(Integer detPbackCnt)
	{
		this.detPbackCnt = detPbackCnt;
	}

	/**
	 * 获取对账结果
	 * 
	 * @return settleResult 对账结果
	 */
	public Integer getSettleResult()
	{
		return settleResult;
	}

	/**
	 * 设置对账结果
	 * 
	 * @param settleResult
	 *            对账结果
	 */
	public void setSettleResult(Integer settleResult)
	{
		this.settleResult = settleResult;
	}

	/**
	 * 获取批次号
	 * @return batchNo 批次号
	 */
	public String getBatchNo()
	{
		return batchNo;
	}

	/**
	 * 设置批次号
	 * @param batchNo 批次号
	 */
	public void setBatchNo(String batchNo)
	{
		this.batchNo = batchNo;
	}

	/**
	 * 获取批结算id
	 * @return id 批结算id
	 */
	public String getId()
	{
		return id;
	}

	/**
	 * 设置批结算id
	 * @param id 批结算id
	 */
	public void setId(String id)
	{
		this.id = id;
	}

	@Override
	public String getHsbSum() {
		return hsbSum;
	}

	@Override
	public void setHsbSum(String hsbSum) {
		this.hsbSum = hsbSum;
	}
}
