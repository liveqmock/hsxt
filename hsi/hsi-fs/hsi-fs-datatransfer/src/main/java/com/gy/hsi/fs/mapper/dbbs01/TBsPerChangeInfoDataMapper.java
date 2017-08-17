package com.gy.hsi.fs.mapper.dbbs01;

import com.gy.hsi.fs.mapper.vo.dbbs01.TBsPerChangeInfoData;
import com.gy.hsi.fs.mapper.vo.dbbs01.TBsPerChangeInfoDataExample;
import com.gy.hsi.fs.mapper.vo.dbbs01.TBsPerChangeInfoDataKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TBsPerChangeInfoDataMapper {
    int countByExample(TBsPerChangeInfoDataExample example);

    int deleteByExample(TBsPerChangeInfoDataExample example);

    int deleteByPrimaryKey(TBsPerChangeInfoDataKey key);

    int insert(TBsPerChangeInfoData record);

    int insertSelective(TBsPerChangeInfoData record);

    List<TBsPerChangeInfoData> selectByExample(TBsPerChangeInfoDataExample example);

    TBsPerChangeInfoData selectByPrimaryKey(TBsPerChangeInfoDataKey key);

    int updateByExampleSelective(@Param("record") TBsPerChangeInfoData record, @Param("example") TBsPerChangeInfoDataExample example);

    int updateByExample(@Param("record") TBsPerChangeInfoData record, @Param("example") TBsPerChangeInfoDataExample example);

    int updateByPrimaryKeySelective(TBsPerChangeInfoData record);

    int updateByPrimaryKey(TBsPerChangeInfoData record);
}