package com.gy.hsxt.ws.mapper;

import com.gy.hsxt.ws.bean.EntryInfo;

/**
 * 福利发放分录信息
 * 
 * @Package: com.gy.hsxt.ws.mapper
 * @ClassName: EntryInfoMapper
 * @Description: TODO
 * 
 * @author: huanggy
 * @date: 2015-12-24 上午11:52:06
 * @version V1.0
 */
public interface EntryInfoMapper {

	/**
	 * 新增分录信息
	 * 
	 * @param record
	 *            分录信息
	 * @return
	 */
	int insertSelective(EntryInfo record);

	/**
	 * 查询分录信息
	 * 
	 * @param entryNo
	 *            分录信息
	 * @return
	 */
	EntryInfo selectByPrimaryKey(String entryNo);

	/**
	 * 更新分录信息
	 * 
	 * @param record
	 *            分录信息
	 * @return
	 */
	int updateByPrimaryKeySelective(EntryInfo record);

}