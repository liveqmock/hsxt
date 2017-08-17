package com.gy.hsi.fs.mapper.dbbs01;

import com.gy.hsi.fs.mapper.vo.dbbs01.TBsEntChangeInfoApp;
import com.gy.hsi.fs.mapper.vo.dbbs01.TBsEntChangeInfoAppExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TBsEntChangeInfoAppMapper {
    int countByExample(TBsEntChangeInfoAppExample example);

    int deleteByExample(TBsEntChangeInfoAppExample example);

    int deleteByPrimaryKey(String applyId);

    int insert(TBsEntChangeInfoApp record);

    int insertSelective(TBsEntChangeInfoApp record);

    List<TBsEntChangeInfoApp> selectByExample(TBsEntChangeInfoAppExample example);

    TBsEntChangeInfoApp selectByPrimaryKey(String applyId);

    int updateByExampleSelective(@Param("record") TBsEntChangeInfoApp record, @Param("example") TBsEntChangeInfoAppExample example);

    int updateByExample(@Param("record") TBsEntChangeInfoApp record, @Param("example") TBsEntChangeInfoAppExample example);

    int updateByPrimaryKeySelective(TBsEntChangeInfoApp record);

    int updateByPrimaryKey(TBsEntChangeInfoApp record);
}