/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.es.distribution.mapper;

import java.util.List;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.es.distribution.bean.Alloc;
import com.gy.hsxt.es.distribution.bean.EntryAllot;

/** 
 * @Package: com.gy.hsxt.ps.distribution.service  
 * @ClassName: RetreatAllocMapper 
 * @Description: TODO
 *
 * @author: chenhongzhi 
 * @date: 2015-12-3 下午3:12:58 
 * @version V3.0  
 */
public interface RetreatAllocMapper
{
	/**
	 * 查询已分配的分录信息
	 *
	 * @param relTransNo
	 * @return
	 */
	public List<Alloc> queryAllocEntry(String relTransNo) throws HsException;
	
	/**
	 * 查询已分配的积分信息
	 * @param relTransNo
	 * @return
	 * @throws HsException
	 */
	public List<Alloc> queryAllocPoint(String relTransNo) throws HsException;


	/**
	 * 查询已分配的分录信息
	 *
	 * @param relTransNo
	 * @return
	 */
	public EntryAllot queryOnlineOldTransNo(String relTransNo) throws HsException;


	/**
	 * 查询已分配的分录信息
	 *
	 * @param relTransNo
	 * @return
	 */
	public EntryAllot queryOnlineEntryNo(String relTransNo) throws HsException;




	/**
	 * 查询已分配的分录信息
	 *
	 * @param relTransNo
	 * @return
	 */
	public EntryAllot queryOnlineEntry(String relTransNo) throws HsException;


}
