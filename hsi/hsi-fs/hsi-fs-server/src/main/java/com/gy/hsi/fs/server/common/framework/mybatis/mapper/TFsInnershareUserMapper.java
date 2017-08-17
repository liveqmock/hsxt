package com.gy.hsi.fs.server.common.framework.mybatis.mapper;

import com.gy.hsi.fs.server.common.framework.mybatis.mapvo.TFsInnershareUser;
import com.gy.hsi.fs.server.common.framework.mybatis.mapvo.TFsInnershareUserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TFsInnershareUserMapper {
    int countByExample(TFsInnershareUserExample example);

    int deleteByExample(TFsInnershareUserExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TFsInnershareUser record);

    int insertSelective(TFsInnershareUser record);

    List<TFsInnershareUser> selectByExample(TFsInnershareUserExample example);

    TFsInnershareUser selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TFsInnershareUser record, @Param("example") TFsInnershareUserExample example);

    int updateByExample(@Param("record") TFsInnershareUser record, @Param("example") TFsInnershareUserExample example);

    int updateByPrimaryKeySelective(TFsInnershareUser record);

    int updateByPrimaryKey(TFsInnershareUser record);
}