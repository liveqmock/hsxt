package com.gy.hsxt.tc.batch.mapper;

import com.gy.hsxt.tc.batch.bean.TcBsacSummary;

public interface TcBsacSummaryMapper {
    int deleteByPrimaryKey(Long sumId);

    int insert(TcBsacSummary record);

    int insertSelective(TcBsacSummary record);

    TcBsacSummary selectByPrimaryKey(Long sumId);

    int updateByPrimaryKeySelective(TcBsacSummary record);

    int updateByPrimaryKey(TcBsacSummary record);
}