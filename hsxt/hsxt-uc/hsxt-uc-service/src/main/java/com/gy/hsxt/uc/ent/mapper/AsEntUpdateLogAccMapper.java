package com.gy.hsxt.uc.ent.mapper;

import com.gy.hsxt.uc.as.bean.ent.AsEntUpdateLogAcc;

public interface AsEntUpdateLogAccMapper {

    int insertDel(AsEntUpdateLogAcc record);

    int insertAdd(AsEntUpdateLogAcc record);

    AsEntUpdateLogAcc selectByPrimaryKey(String logId);


}