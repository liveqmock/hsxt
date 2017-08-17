package com.gy.hsi.fs.mapper.dbbs01;

import com.gy.hsi.fs.mapper.vo.dbbs01.TBsPointgame;
import com.gy.hsi.fs.mapper.vo.dbbs01.TBsPointgameExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TBsPointgameMapper {
    int countByExample(TBsPointgameExample example);

    int deleteByExample(TBsPointgameExample example);

    int insert(TBsPointgame record);

    int insertSelective(TBsPointgame record);

    List<TBsPointgame> selectByExample(TBsPointgameExample example);

    int updateByExampleSelective(@Param("record") TBsPointgame record, @Param("example") TBsPointgameExample example);

    int updateByExample(@Param("record") TBsPointgame record, @Param("example") TBsPointgameExample example);
}