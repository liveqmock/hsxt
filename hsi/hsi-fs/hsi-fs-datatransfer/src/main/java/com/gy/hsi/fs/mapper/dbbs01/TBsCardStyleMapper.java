package com.gy.hsi.fs.mapper.dbbs01;

import com.gy.hsi.fs.mapper.vo.dbbs01.TBsCardStyle;
import com.gy.hsi.fs.mapper.vo.dbbs01.TBsCardStyleExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TBsCardStyleMapper {
    int countByExample(TBsCardStyleExample example);

    int deleteByExample(TBsCardStyleExample example);

    int deleteByPrimaryKey(String cardStyleId);

    int insert(TBsCardStyle record);

    int insertSelective(TBsCardStyle record);

    List<TBsCardStyle> selectByExample(TBsCardStyleExample example);

    TBsCardStyle selectByPrimaryKey(String cardStyleId);

    int updateByExampleSelective(@Param("record") TBsCardStyle record, @Param("example") TBsCardStyleExample example);

    int updateByExample(@Param("record") TBsCardStyle record, @Param("example") TBsCardStyleExample example);

    int updateByPrimaryKeySelective(TBsCardStyle record);

    int updateByPrimaryKey(TBsCardStyle record);
}