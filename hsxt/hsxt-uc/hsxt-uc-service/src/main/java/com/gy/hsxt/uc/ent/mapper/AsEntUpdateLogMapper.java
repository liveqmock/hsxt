package com.gy.hsxt.uc.ent.mapper;

import java.util.List;

import com.gy.hsxt.uc.as.bean.ent.AsEntUpdateLog;

public interface AsEntUpdateLogMapper {


    int insertSelective(AsEntUpdateLog record);

    
    List<AsEntUpdateLog> selectList(AsEntUpdateLog record);


}