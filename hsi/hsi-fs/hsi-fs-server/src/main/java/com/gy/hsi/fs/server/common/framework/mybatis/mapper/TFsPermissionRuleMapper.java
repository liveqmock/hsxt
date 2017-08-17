package com.gy.hsi.fs.server.common.framework.mybatis.mapper;

import com.gy.hsi.fs.server.common.framework.mybatis.mapvo.TFsPermissionRule;
import com.gy.hsi.fs.server.common.framework.mybatis.mapvo.TFsPermissionRuleExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TFsPermissionRuleMapper {
    int countByExample(TFsPermissionRuleExample example);

    int deleteByExample(TFsPermissionRuleExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TFsPermissionRule record);

    int insertSelective(TFsPermissionRule record);

    List<TFsPermissionRule> selectByExample(TFsPermissionRuleExample example);

    TFsPermissionRule selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TFsPermissionRule record, @Param("example") TFsPermissionRuleExample example);

    int updateByExample(@Param("record") TFsPermissionRule record, @Param("example") TFsPermissionRuleExample example);

    int updateByPrimaryKeySelective(TFsPermissionRule record);

    int updateByPrimaryKey(TFsPermissionRule record);
}