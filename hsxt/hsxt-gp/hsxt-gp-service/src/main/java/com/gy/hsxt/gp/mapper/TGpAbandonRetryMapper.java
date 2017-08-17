package com.gy.hsxt.gp.mapper;

import com.gy.hsxt.gp.mapper.vo.TGpAbandonRetry;

public interface TGpAbandonRetryMapper {
	
	int deleteByPrimaryKey(Integer retryId);

	int abandonRetry(TGpAbandonRetry record);

	int insert(TGpAbandonRetry record);

	int insertSelective(TGpAbandonRetry record);

	TGpAbandonRetry selectByPrimaryKey(Integer retryId);

	int updateByPrimaryKeySelective(TGpAbandonRetry record);

	int updateByPrimaryKey(TGpAbandonRetry record);
}