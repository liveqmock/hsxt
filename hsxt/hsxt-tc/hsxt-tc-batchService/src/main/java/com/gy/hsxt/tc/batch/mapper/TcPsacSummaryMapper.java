package com.gy.hsxt.tc.batch.mapper;

import com.gy.hsxt.tc.batch.bean.TcPsacSummary;

public interface TcPsacSummaryMapper {
    int deleteByPrimaryKey(Long sumId);

    int insert(TcPsacSummary record);

    int insertSelective(TcPsacSummary record);

    TcPsacSummary selectByPrimaryKey(Long sumId);

    int updateByPrimaryKeySelective(TcPsacSummary record);

    int updateByPrimaryKey(TcPsacSummary record);
}