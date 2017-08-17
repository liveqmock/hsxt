package com.gy.hsxt.tc.batch.mapper;

import com.gy.hsxt.tc.batch.bean.TcAoacSummary;

public interface TcAoacSummaryMapper {
    int deleteByPrimaryKey(Long sumId);

    int insert(TcAoacSummary record);

    int insertSelective(TcAoacSummary record);

    TcAoacSummary selectByPrimaryKey(Long sumId);

    int updateByPrimaryKeySelective(TcAoacSummary record);

    int updateByPrimaryKey(TcAoacSummary record);
}