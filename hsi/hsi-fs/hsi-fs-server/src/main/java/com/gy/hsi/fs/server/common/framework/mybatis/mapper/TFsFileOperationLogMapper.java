package com.gy.hsi.fs.server.common.framework.mybatis.mapper;

import com.gy.hsi.fs.server.common.framework.mybatis.mapvo.TFsFileOperationLog;
import com.gy.hsi.fs.server.common.framework.mybatis.mapvo.TFsFileOperationLogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TFsFileOperationLogMapper {
    int countByExample(TFsFileOperationLogExample example);

    int deleteByExample(TFsFileOperationLogExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TFsFileOperationLog record);

    int insertSelective(TFsFileOperationLog record);

    List<TFsFileOperationLog> selectByExampleWithBLOBs(TFsFileOperationLogExample example);

    List<TFsFileOperationLog> selectByExample(TFsFileOperationLogExample example);

    TFsFileOperationLog selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") TFsFileOperationLog record, @Param("example") TFsFileOperationLogExample example);

    int updateByExampleWithBLOBs(@Param("record") TFsFileOperationLog record, @Param("example") TFsFileOperationLogExample example);

    int updateByExample(@Param("record") TFsFileOperationLog record, @Param("example") TFsFileOperationLogExample example);

    int updateByPrimaryKeySelective(TFsFileOperationLog record);

    int updateByPrimaryKeyWithBLOBs(TFsFileOperationLog record);

    int updateByPrimaryKey(TFsFileOperationLog record);
}