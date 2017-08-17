package com.gy.hsxt.tc.batch.mapper;

import com.gy.hsxt.tc.batch.bean.TcBsgpPaySummary;

public interface TcBsgpPaySummaryMapper {
    int deleteByPrimaryKey(Long sumId);

    int insert(TcBsgpPaySummary record);

    int insertSelective(TcBsgpPaySummary record);

    TcBsgpPaySummary selectByPrimaryKey(Long sumId);

    int updateByPrimaryKeySelective(TcBsgpPaySummary record);

    int updateByPrimaryKey(TcBsgpPaySummary record);
}