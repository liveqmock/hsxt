package com.gy.hsi.fs.mapper.dbbs01;

import com.gy.hsi.fs.mapper.vo.dbbs01.TBsPerRealnameAuth;
import com.gy.hsi.fs.mapper.vo.dbbs01.TBsPerRealnameAuthExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TBsPerRealnameAuthMapper {
    int countByExample(TBsPerRealnameAuthExample example);

    int deleteByExample(TBsPerRealnameAuthExample example);

    int deleteByPrimaryKey(String applyId);

    int insert(TBsPerRealnameAuth record);

    int insertSelective(TBsPerRealnameAuth record);

    List<TBsPerRealnameAuth> selectByExample(TBsPerRealnameAuthExample example);

    TBsPerRealnameAuth selectByPrimaryKey(String applyId);

    int updateByExampleSelective(@Param("record") TBsPerRealnameAuth record, @Param("example") TBsPerRealnameAuthExample example);

    int updateByExample(@Param("record") TBsPerRealnameAuth record, @Param("example") TBsPerRealnameAuthExample example);

    int updateByPrimaryKeySelective(TBsPerRealnameAuth record);

    int updateByPrimaryKey(TBsPerRealnameAuth record);
}