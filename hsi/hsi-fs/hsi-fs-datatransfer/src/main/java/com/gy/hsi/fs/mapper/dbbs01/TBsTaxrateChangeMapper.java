package com.gy.hsi.fs.mapper.dbbs01;

import com.gy.hsi.fs.mapper.vo.dbbs01.TBsTaxrateChange;
import com.gy.hsi.fs.mapper.vo.dbbs01.TBsTaxrateChangeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TBsTaxrateChangeMapper {
    int countByExample(TBsTaxrateChangeExample example);

    int deleteByExample(TBsTaxrateChangeExample example);

    int deleteByPrimaryKey(String applyId);

    int insert(TBsTaxrateChange record);

    int insertSelective(TBsTaxrateChange record);

    List<TBsTaxrateChange> selectByExample(TBsTaxrateChangeExample example);

    TBsTaxrateChange selectByPrimaryKey(String applyId);

    int updateByExampleSelective(@Param("record") TBsTaxrateChange record, @Param("example") TBsTaxrateChangeExample example);

    int updateByExample(@Param("record") TBsTaxrateChange record, @Param("example") TBsTaxrateChangeExample example);

    int updateByPrimaryKeySelective(TBsTaxrateChange record);

    int updateByPrimaryKey(TBsTaxrateChange record);
}