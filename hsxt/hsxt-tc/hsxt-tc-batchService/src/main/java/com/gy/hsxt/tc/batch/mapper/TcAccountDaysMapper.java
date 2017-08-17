package com.gy.hsxt.tc.batch.mapper;

import com.gy.hsxt.tc.batch.bean.TcAccountDays;

public interface TcAccountDaysMapper {
    int deleteByPrimaryKey(Long accDaysId);

    int insert(TcAccountDays record);

    int insertSelective(TcAccountDays record);

    TcAccountDays selectByPrimaryKey(Long accDaysId);

    int updateByPrimaryKeySelective(TcAccountDays record);

    int updateByPrimaryKey(TcAccountDays record);
}