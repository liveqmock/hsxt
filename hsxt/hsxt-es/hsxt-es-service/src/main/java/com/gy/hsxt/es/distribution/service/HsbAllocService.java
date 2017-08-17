package com.gy.hsxt.es.distribution.service;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.es.common.Constants;
import com.gy.hsxt.es.distribution.bean.Alloc;
import com.gy.hsxt.es.distribution.handle.AllocHandle;
import com.gy.hsxt.es.distribution.mapper.HsbAllocMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author MartinCubbon
 * @version v0.0.1
 * @Project Name hsxt
 * @Created with IntelliJ IDEA.
 * @package com.gy.hsxt.ps.distribution.service
 * @description 互生币业务接口实现类
 * @createDate 2015/9/28 11:52
 * @updateUser chenhongzhi
 * @updateDate 2015/9/28 11:52
 * @updateRemark 说明本次修改内容
 */
@Service
public class HsbAllocService implements IHsbAllocService
{

	/**
	 * 通过注解调用互生币的映射器
	 */
	@Autowired
	private HsbAllocMapper hsbAllocMapper;
	@Autowired
	private PointAllocService pointAllocService;
	@Autowired
	private RunnableService batchService;
	
	/**
	 * 日终查询电商的交易订单
	 */
	@Override
	public List<Alloc> queryElectricityTrans() throws HsException
	{
		return this.hsbAllocMapper.queryElectricityTrans();
	}

	/**
	 * 日结暂存商业服务费
	 */
	@Override
	public List<Alloc> businessServiceFee() throws HsException
	{
		List<Alloc> list = this.queryElectricityTrans();
		List<Alloc> serviceList = AllocHandle.serviceDayCsc(list);
		this.hsbAllocMapper.insertDailyCsc(serviceList);
		AllocHandle.writeFile(serviceList, Constants.SETTLEMENT_CSC);
		return serviceList;
	}

	/**
	 * 查询汇总当月的商业服务费
	 * 
	 * @return
	 * @throws HsException
	 */
	public List<Alloc> queryMonthCSC() throws HsException
	{
		return this.hsbAllocMapper.queryMonthCsc();
	}

	/**
	 * 月结商业服务费(服务公司、平台)
	 */
	@Override
	public void monthlyBusinessServiceFee() throws HsException
	{
		List<Alloc> monthList = this.queryMonthCSC();
		
		AllocHandle.monthBuckleCityTax(monthList);
		
		AllocHandle.serviceMonthServiceFee(monthList);
		this.hsbAllocMapper.insertDailyCsc(monthList);
		
		AllocHandle.paasMonthServiceFee(monthList);
		this.hsbAllocMapper.insertDailyCsc(monthList);
	}

	/**
	 * 互生币汇总
	 */
	@Override
	public void hsbSummary() throws HsException
	{
		List<Map<String, String>> dataList = new ArrayList<Map<String, String>>();
		
		List<Alloc> hsbList = this.queryElectricityTrans();
		List<Alloc> bsfList = this.businessServiceFee();
		
		AllocHandle.hsbSumSubList(hsbList, bsfList, dataList);
		
		this.hsbAllocMapper.insertHsbSum(hsbList);
		
		AllocHandle.writeHsbFile(dataList, Constants.SETTLEMENT_HSB);

	}

}
