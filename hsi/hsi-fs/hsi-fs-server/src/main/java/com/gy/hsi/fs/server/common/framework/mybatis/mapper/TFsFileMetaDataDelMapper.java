package com.gy.hsi.fs.server.common.framework.mybatis.mapper;

import com.gy.hsi.fs.server.common.framework.mybatis.mapvo.TFsFileMetaDataDel;
import com.gy.hsi.fs.server.common.framework.mybatis.mapvo.TFsFileMetaDataDelExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TFsFileMetaDataDelMapper {
    int countByExample(TFsFileMetaDataDelExample example);

    int deleteByExample(TFsFileMetaDataDelExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TFsFileMetaDataDel record);

    int insertSelective(TFsFileMetaDataDel record);

    List<TFsFileMetaDataDel> selectByExample(TFsFileMetaDataDelExample example);

    TFsFileMetaDataDel selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") TFsFileMetaDataDel record, @Param("example") TFsFileMetaDataDelExample example);

    int updateByExample(@Param("record") TFsFileMetaDataDel record, @Param("example") TFsFileMetaDataDelExample example);

    int updateByPrimaryKeySelective(TFsFileMetaDataDel record);

    int updateByPrimaryKey(TFsFileMetaDataDel record);
}