package com.gy.hsxt.tc.batch.mapper;

import com.gy.hsxt.tc.batch.bean.TcAogpSummary;

public interface TcAogpSummaryMapper {
    int deleteByPrimaryKey(Long sumId);

    int insert(TcAogpSummary record);

    int insertSelective(TcAogpSummary record);

    TcAogpSummary selectByPrimaryKey(Long sumId);

    int updateByPrimaryKeySelective(TcAogpSummary record);

    int updateByPrimaryKey(TcAogpSummary record);
}