package com.gy.hsxt.tc.batch.mapper;

import com.gy.hsxt.tc.batch.bean.TcGpchSummary;

public interface TcGpchSummaryMapper {
    int deleteByPrimaryKey(Long sumId);

    int insert(TcGpchSummary record);

    int insertSelective(TcGpchSummary record);

    TcGpchSummary selectByPrimaryKey(Long sumId);

    int updateByPrimaryKeySelective(TcGpchSummary record);

    int updateByPrimaryKey(TcGpchSummary record);
}