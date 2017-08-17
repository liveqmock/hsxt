package com.gy.hsi.fs.mapper.dbbs01;

import com.gy.hsi.fs.mapper.vo.dbbs01.TBsEntRealnameAuth;
import com.gy.hsi.fs.mapper.vo.dbbs01.TBsEntRealnameAuthExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TBsEntRealnameAuthMapper {
    int countByExample(TBsEntRealnameAuthExample example);

    int deleteByExample(TBsEntRealnameAuthExample example);

    int deleteByPrimaryKey(String applyId);

    int insert(TBsEntRealnameAuth record);

    int insertSelective(TBsEntRealnameAuth record);

    List<TBsEntRealnameAuth> selectByExample(TBsEntRealnameAuthExample example);

    TBsEntRealnameAuth selectByPrimaryKey(String applyId);

    int updateByExampleSelective(@Param("record") TBsEntRealnameAuth record, @Param("example") TBsEntRealnameAuthExample example);

    int updateByExample(@Param("record") TBsEntRealnameAuth record, @Param("example") TBsEntRealnameAuthExample example);

    int updateByPrimaryKeySelective(TBsEntRealnameAuth record);

    int updateByPrimaryKey(TBsEntRealnameAuth record);
}