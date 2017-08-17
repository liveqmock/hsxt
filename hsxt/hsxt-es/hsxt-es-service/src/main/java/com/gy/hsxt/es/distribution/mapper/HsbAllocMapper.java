package com.gy.hsxt.es.distribution.mapper;

import java.util.List;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.es.distribution.bean.Alloc;

public interface HsbAllocMapper
{
	/**
	 * 查询互生币交易订单
	 * 
	 * @return
	 * @throws HsException
	 */
	public List<Alloc> queryHsbTrans() throws HsException;

	/**
	 * 日终查询电商的交易订单
	 * 
	 * @return
	 * @throws HsException
	 */
	public List<Alloc> queryElectricityTrans() throws HsException;

	/**
	 * 查询当月每天的商业服务费
	 * 
	 * @return
	 * @throws HsException
	 */
	public List<Alloc> queryMonthCsc() throws HsException;

	/**
	 * 插入商业服务费(日终暂存、月结)
	 * 
	 * @return
	 * @throws HsException
	 */
	public void insertDailyCsc(List<Alloc> list) throws HsException;
	
	/**
	 * 插入互生币汇总(减商业服务费后)
	 * @param list
	 * @throws HsException
	 */
	public void insertHsbSum(List<Alloc> list)throws HsException;

}
