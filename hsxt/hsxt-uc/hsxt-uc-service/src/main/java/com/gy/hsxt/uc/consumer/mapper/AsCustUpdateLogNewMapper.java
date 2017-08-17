package com.gy.hsxt.uc.consumer.mapper;

import com.gy.hsxt.uc.as.bean.consumer.AsCustUpdateLogNew;

public interface AsCustUpdateLogNewMapper {
    int deleteByPrimaryKey(String logId);

    int insert(AsCustUpdateLogNew record);

    int insertSelective(AsCustUpdateLogNew record);

    AsCustUpdateLogNew selectByPrimaryKey(String logId);

    int updateByPrimaryKeySelective(AsCustUpdateLogNew record);

    int updateByPrimaryKey(AsCustUpdateLogNew record);
}