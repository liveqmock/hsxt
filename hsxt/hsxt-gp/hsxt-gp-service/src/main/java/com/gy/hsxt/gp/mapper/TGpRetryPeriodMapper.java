package com.gy.hsxt.gp.mapper;

import java.util.List;

import com.gy.hsxt.gp.mapper.vo.TGpRetryPeriod;

public interface TGpRetryPeriodMapper {
	
    int deleteByPrimaryKey(Integer periodId);

    int insert(TGpRetryPeriod record);

    int insertSelective(TGpRetryPeriod record);

    TGpRetryPeriod selectByPrimaryKey(Integer periodId);

    int updateByPrimaryKeySelective(TGpRetryPeriod record);

    int updateByPrimaryKey(TGpRetryPeriod record);
    
    List<TGpRetryPeriod> list();
}