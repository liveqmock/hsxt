package com.gy.hsxt.uc.ent.mapper;

import com.gy.hsxt.uc.as.bean.ent.AsEntUpdateLogNew;

public interface AsEntUpdateLogNewMapper {
    int deleteByPrimaryKey(String logId);

    int insert(AsEntUpdateLogNew record);

    int insertSelective(AsEntUpdateLogNew record);

    AsEntUpdateLogNew selectByPrimaryKey(String logId);

    int updateByPrimaryKeySelective(AsEntUpdateLogNew record);

    int updateByPrimaryKey(AsEntUpdateLogNew record);
}