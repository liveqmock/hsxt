/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.ps.distribution.mapper;

import java.util.List;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.ps.distribution.bean.Alloc;
import com.gy.hsxt.ps.distribution.bean.EntryAllot;

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
	List<Alloc> queryAllocEntry(String relTransNo) throws HsException;

	/**
	 * 查询已分配的分录信息
	 *
	 * @param relTransNo
	 * @return
	 */
	List<EntryAllot> queryOfflineEntryList(String relTransNo) throws HsException;

	/**
	 * 查询已分配的分录信息
	 *
	 * @param relTransNo
	 * @return
	 */
	EntryAllot queryOfflineEntry(String relTransNo) throws HsException;


	/**
	 * 查询已分配的分录信息
	 *
	 * @param relTransNo
	 * @return
	 */
	EntryAllot queryOfflineEntryeNo(String relTransNo) throws HsException;
	
	/**
	 * 查询已分配的积分信息
	 * @param relTransNo
	 * @return
	 * @throws HsException
	 */
	List<Alloc> queryAllocPoint(String relTransNo) throws HsException;

}
