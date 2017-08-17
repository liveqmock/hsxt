package com.gy.hsi.fs.mapper.dbuc01;

import com.gy.hsi.fs.mapper.vo.dbuc01.TCustIdInfo;
import com.gy.hsi.fs.mapper.vo.dbuc01.TCustIdInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TCustIdInfoMapper {
    int countByExample(TCustIdInfoExample example);

    int deleteByExample(TCustIdInfoExample example);

    int deleteByPrimaryKey(String perCustId);

    int insert(TCustIdInfo record);

    int insertSelective(TCustIdInfo record);

    List<TCustIdInfo> selectByExample(TCustIdInfoExample example);

    TCustIdInfo selectByPrimaryKey(String perCustId);

    int updateByExampleSelective(@Param("record") TCustIdInfo record, @Param("example") TCustIdInfoExample example);

    int updateByExample(@Param("record") TCustIdInfo record, @Param("example") TCustIdInfoExample example);

    int updateByPrimaryKeySelective(TCustIdInfo record);

    int updateByPrimaryKey(TCustIdInfo record);
}