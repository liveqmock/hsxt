package com.gy.hsxt.gp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gy.hsxt.gp.mapper.vo.TGpRetry;

public interface TGpRetryMapper {

	/**
	 * 插入记录
	 * 
	 * @param record
	 * @return
	 */
	int insert(TGpRetry record);

	/** 
	 * 根据businessId更新状态
	 * 
	 * @param record
	 * @return
	 */
	int updateByBusinessId(TGpRetry record);

	/**
	 * 查出所有重试状态为“失败”且下一次重试时间小于当前时间的记录
	 * 
	 * @param start
	 * @param pageSize
	 * @return
	 */
	List<TGpRetry> selectRetryByPage(@Param("start") Integer start,
			@Param("pageSize") Integer pageSize);
	
	/**
	 * 根据businessId、retryBusinessType查询
	 * 
	 * @param businessId
	 * @param retryBusinessType
	 * @return
	 */
	TGpRetry selectByBusinessId(@Param("businessId") String businessId,
			@Param("retryBusinessType") Integer retryBusinessType);

	/**
	 * 根据删除该条数据
	 * 
	 * @param businessId
	 * @param retryBusinessType
	 */
	void deleteByBusinessIdAndType(@Param("businessId") String businessId,
			@Param("retryBusinessType") Integer retryBusinessType);
	
	/**
	 * 删除指定日期之前的数据
	 * 
	 * @param retryDate
	 */
	void deleteBeforeDate(@Param("retryDate") String retryDate);
}
