package com.gy.hsi.fs.server.common.framework.mybatis.mapper;

import com.gy.hsi.fs.server.common.framework.mybatis.mapvo.TFsFileMetaData;
import com.gy.hsi.fs.server.common.framework.mybatis.mapvo.TFsFileMetaDataExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TFsFileMetaDataMapper {
    int countByExample(TFsFileMetaDataExample example);

    int deleteByExample(TFsFileMetaDataExample example);

    int deleteByPrimaryKey(String fileId);

    int insert(TFsFileMetaData record);

    int insertSelective(TFsFileMetaData record);

    List<TFsFileMetaData> selectByExample(TFsFileMetaDataExample example);

    TFsFileMetaData selectByPrimaryKey(String fileId);

    int updateByExampleSelective(@Param("record") TFsFileMetaData record, @Param("example") TFsFileMetaDataExample example);

    int updateByExample(@Param("record") TFsFileMetaData record, @Param("example") TFsFileMetaDataExample example);

    int updateByPrimaryKeySelective(TFsFileMetaData record);

    int updateByPrimaryKey(TFsFileMetaData record);
}