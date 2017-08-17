/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.ps.distribution.handle;

import java.util.ArrayList;
import java.util.List;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.BeanCopierUtils;
import com.gy.hsxt.ps.distribution.bean.Alloc;

/** 
 * @Package: com.gy.hsxt.ps.distribution.handle  
 * @ClassName: CommercialServiceFee 
 * @Description: TODO
 *
 * @author: chenhz 
 * @date: 2016-1-29 下午8:13:13 
 * @version V3.0  
 */
public class CommercialServiceFee extends AllocItem
{
	private List<Alloc> list = new ArrayList<>();

	/**
	 * 初始化积分对象
	 * 
	 * @param alloc
	 *            分配对象信息
	 * @throws HsException
	 */
	public void initAlloc(Alloc alloc) throws HsException
	{
		// 把积分明细信息拷贝到PointItem实体中
		BeanCopierUtils.copy(alloc, this);

		this.initCsc(addAmount, alloc.getTaxRate());
		this.initHsResNo(alloc.getHsResNo());
	}
	
	/**
	 * 装入商业服务费
	 * @param alloc
	 * 	  		积分明细信息
	 */
	public void addCommercialServiceFee(Alloc alloc)
	{
		// 积分分配基本信息处理、积分计算处理
		this.initAlloc(alloc);
		
		// 获取装载积分
		this.addServiceFee();
	}
	
	/**
	 * 装入商业服务费税收
	 * @param alloc
	 * 	  		积分明细信息
	 */
	public void addCommercialServiceFeeTaxRate(Alloc alloc)
	{
		// 积分分配基本信息处理、积分计算处理
		this.initAlloc(alloc);
		
		// 获取装载积分
		this.addServiceFeeTaxRate();
	}
	
	/**
	 * 获取分配的明细信息
	 * @return
	 */
	public List<Alloc> getAllocList()
	{
		return this.list;
	}
	
	/**
	 * 增向装载积分(托管公司、服务公司、管理公司、平台、结余)
	 */
	public void addServiceFee()
	{
		// 服务公司商业服务费
		list.add(this.getServiceHsbAddCsc());
		
		// 平台商业服务费
		list.add(this.getPaasHsbAddCsc());
	}
	
	/**
	 * 增向装载积分(托管公司、服务公司、管理公司、平台、结余)
	 */
	public void addServiceFeeTaxRate()
	{
		// 服务公司商业服务费税收
		list.add(this.getServiceTaxAddCsc());
	}
	
}
