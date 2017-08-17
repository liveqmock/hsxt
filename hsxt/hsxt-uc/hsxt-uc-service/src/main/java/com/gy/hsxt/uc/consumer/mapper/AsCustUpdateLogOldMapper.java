package com.gy.hsxt.uc.consumer.mapper;

import com.gy.hsxt.uc.as.bean.consumer.AsCustUpdateLogOld;

public interface AsCustUpdateLogOldMapper {
    int deleteByPrimaryKey(String logId);

    int insert(AsCustUpdateLogOld record);

    int insertSelective(AsCustUpdateLogOld record);

    AsCustUpdateLogOld selectByPrimaryKey(String logId);

    int updateByPrimaryKeySelective(AsCustUpdateLogOld record);

    int updateByPrimaryKey(AsCustUpdateLogOld record);
}