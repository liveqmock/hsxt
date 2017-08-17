package com.gy.hsxt.uc.consumer.mapper;

import java.util.List;

import com.gy.hsxt.uc.as.bean.consumer.AsCustUpdateLog;
import com.gy.hsxt.uc.as.bean.consumer.AsCustUpdateLogNew;

public interface AsCustUpdateLogMapper {

    int insertSelective(AsCustUpdateLog record);

    List<AsCustUpdateLog> selectList(AsCustUpdateLog record);
}