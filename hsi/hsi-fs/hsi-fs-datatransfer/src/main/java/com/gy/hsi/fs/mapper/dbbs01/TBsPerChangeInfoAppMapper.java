package com.gy.hsi.fs.mapper.dbbs01;

import com.gy.hsi.fs.mapper.vo.dbbs01.TBsPerChangeInfoApp;
import com.gy.hsi.fs.mapper.vo.dbbs01.TBsPerChangeInfoAppExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TBsPerChangeInfoAppMapper {
    int countByExample(TBsPerChangeInfoAppExample example);

    int deleteByExample(TBsPerChangeInfoAppExample example);

    int deleteByPrimaryKey(String applyId);

    int insert(TBsPerChangeInfoApp record);

    int insertSelective(TBsPerChangeInfoApp record);

    List<TBsPerChangeInfoApp> selectByExample(TBsPerChangeInfoAppExample example);

    TBsPerChangeInfoApp selectByPrimaryKey(String applyId);

    int updateByExampleSelective(@Param("record") TBsPerChangeInfoApp record, @Param("example") TBsPerChangeInfoAppExample example);

    int updateByExample(@Param("record") TBsPerChangeInfoApp record, @Param("example") TBsPerChangeInfoAppExample example);

    int updateByPrimaryKeySelective(TBsPerChangeInfoApp record);

    int updateByPrimaryKey(TBsPerChangeInfoApp record);
}