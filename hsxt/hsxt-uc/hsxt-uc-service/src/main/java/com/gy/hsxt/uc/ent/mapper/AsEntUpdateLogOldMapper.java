package com.gy.hsxt.uc.ent.mapper;

import com.gy.hsxt.uc.as.bean.ent.AsEntUpdateLogOld;

public interface AsEntUpdateLogOldMapper {
    int deleteByPrimaryKey(String logId);

    int insert(AsEntUpdateLogOld record);

    int insertSelective(AsEntUpdateLogOld record);

    AsEntUpdateLogOld selectByPrimaryKey(String logId);

    int updateByPrimaryKeySelective(AsEntUpdateLogOld record);

    int updateByPrimaryKey(AsEntUpdateLogOld record);
}