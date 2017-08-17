package com.gy.hsxt.pg.mapper;

import java.util.List;
import com.gy.hsxt.pg.mapper.vo.TPgRetryPeriod;

public interface TPgRetryPeriodMapper {
    int deleteByPrimaryKey(Integer periodId);

    int insert(TPgRetryPeriod record);

    int insertSelective(TPgRetryPeriod record);

    TPgRetryPeriod selectByPrimaryKey(Integer periodId);

    int updateByPrimaryKeySelective(TPgRetryPeriod record);

    int updateByPrimaryKey(TPgRetryPeriod record);
    
    List<TPgRetryPeriod> list();
}