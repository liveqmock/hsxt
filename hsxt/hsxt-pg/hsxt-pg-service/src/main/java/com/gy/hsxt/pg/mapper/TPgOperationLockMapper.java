package com.gy.hsxt.pg.mapper;

import org.apache.ibatis.annotations.Param;

import com.gy.hsxt.pg.mapper.vo.TPgOperationLock;

public interface TPgOperationLockMapper {
	
	/**
	 * 根据businessId和businessType记录
	 * 
	 * @param businessId
	 * @param businessType
	 * @return
	 */
	void deleteByBusinessIdAndType(@Param("businessId") String businessId,
			@Param("businessType") Integer businessType);

	/**
	 * 插入记录
	 * 
	 * @param record
	 * @return
	 */
	int insert(TPgOperationLock record);

	/**
	 * 查询记录
	 * 
	 * @param businessId
	 * @param businessType
	 * @return
	 */
	TPgOperationLock selectByBusinessId(@Param("businessId") String businessId,
			@Param("businessType") Integer businessType);
}