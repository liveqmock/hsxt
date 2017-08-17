package com.gy.hsi.fs.mapper.dbbs01;

import com.gy.hsi.fs.mapper.vo.dbbs01.TBsEntChangeInfoData;
import com.gy.hsi.fs.mapper.vo.dbbs01.TBsEntChangeInfoDataExample;
import com.gy.hsi.fs.mapper.vo.dbbs01.TBsEntChangeInfoDataKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TBsEntChangeInfoDataMapper {
    int countByExample(TBsEntChangeInfoDataExample example);

    int deleteByExample(TBsEntChangeInfoDataExample example);

    int deleteByPrimaryKey(TBsEntChangeInfoDataKey key);

    int insert(TBsEntChangeInfoData record);

    int insertSelective(TBsEntChangeInfoData record);

    List<TBsEntChangeInfoData> selectByExample(TBsEntChangeInfoDataExample example);

    TBsEntChangeInfoData selectByPrimaryKey(TBsEntChangeInfoDataKey key);

    int updateByExampleSelective(@Param("record") TBsEntChangeInfoData record, @Param("example") TBsEntChangeInfoDataExample example);

    int updateByExample(@Param("record") TBsEntChangeInfoData record, @Param("example") TBsEntChangeInfoDataExample example);

    int updateByPrimaryKeySelective(TBsEntChangeInfoData record);

    int updateByPrimaryKey(TBsEntChangeInfoData record);
}