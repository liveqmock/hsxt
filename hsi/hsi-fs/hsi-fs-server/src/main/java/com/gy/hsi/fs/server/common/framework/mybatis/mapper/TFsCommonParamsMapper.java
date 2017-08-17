package com.gy.hsi.fs.server.common.framework.mybatis.mapper;

import com.gy.hsi.fs.server.common.framework.mybatis.mapvo.TFsCommonParams;
import com.gy.hsi.fs.server.common.framework.mybatis.mapvo.TFsCommonParamsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TFsCommonParamsMapper {
    int countByExample(TFsCommonParamsExample example);

    int deleteByExample(TFsCommonParamsExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TFsCommonParams record);

    int insertSelective(TFsCommonParams record);

    List<TFsCommonParams> selectByExample(TFsCommonParamsExample example);

    TFsCommonParams selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TFsCommonParams record, @Param("example") TFsCommonParamsExample example);

    int updateByExample(@Param("record") TFsCommonParams record, @Param("example") TFsCommonParamsExample example);

    int updateByPrimaryKeySelective(TFsCommonParams record);

    int updateByPrimaryKey(TFsCommonParams record);
}