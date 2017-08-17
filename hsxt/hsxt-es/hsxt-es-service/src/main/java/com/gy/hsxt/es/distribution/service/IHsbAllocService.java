package com.gy.hsxt.es.distribution.service;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.es.distribution.bean.Alloc;

import java.util.List;

/**
 * @author MartinCubbon
 * @version v0.0.1
 * @Project Name hsxt
 * @Created with IntelliJ IDEA.
 * @package com.gy.hsxt.ps.distribution.service
 * @description 互生币业务
 * @createDate 2015/9/28 11:46
 * @updateUser chenhongzhi
 * @updateDate 2015/9/28 11:46
 * @updateRemark 说明本次修改内容
 */
public interface IHsbAllocService
{

	/**
	 * 查询互生币电商交易订单
	 * 
	 * @return
	 * @throws HsException
	 */
	public List<Alloc> queryElectricityTrans() throws HsException;

	/**
	 * 日终结算商业服务费
	 * 
	 * @return
	 * @throws HsException
	 */
	public List<Alloc> businessServiceFee() throws HsException;

	/**
	 * 月终结算商业服务费
	 * 
	 * @throws HsException
	 */
	public void monthlyBusinessServiceFee() throws HsException;

	/**
	 * 互生币汇总
	 * 
	 * @throws HsException
	 */
	public void hsbSummary() throws HsException;
}
