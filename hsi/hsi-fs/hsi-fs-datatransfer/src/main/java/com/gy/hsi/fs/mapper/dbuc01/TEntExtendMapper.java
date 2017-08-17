package com.gy.hsi.fs.mapper.dbuc01;

import com.gy.hsi.fs.mapper.vo.dbuc01.TEntExtend;
import com.gy.hsi.fs.mapper.vo.dbuc01.TEntExtendExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TEntExtendMapper {
    int countByExample(TEntExtendExample example);

    int deleteByExample(TEntExtendExample example);

    int deleteByPrimaryKey(String entCustId);

    int insert(TEntExtend record);

    int insertSelective(TEntExtend record);

    List<TEntExtend> selectByExample(TEntExtendExample example);

    TEntExtend selectByPrimaryKey(String entCustId);

    int updateByExampleSelective(@Param("record") TEntExtend record, @Param("example") TEntExtendExample example);

    int updateByExample(@Param("record") TEntExtend record, @Param("example") TEntExtendExample example);

    int updateByPrimaryKeySelective(TEntExtend record);

    int updateByPrimaryKey(TEntExtend record);
}