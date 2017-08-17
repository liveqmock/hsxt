package com.gy.hsi.fs.mapper.dbuc01;

import com.gy.hsi.fs.mapper.vo.dbuc01.TNetworkInfo;
import com.gy.hsi.fs.mapper.vo.dbuc01.TNetworkInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TNetworkInfoMapper {
    int countByExample(TNetworkInfoExample example);

    int deleteByExample(TNetworkInfoExample example);

    int deleteByPrimaryKey(String perCustId);

    int insert(TNetworkInfo record);

    int insertSelective(TNetworkInfo record);

    List<TNetworkInfo> selectByExample(TNetworkInfoExample example);

    TNetworkInfo selectByPrimaryKey(String perCustId);

    int updateByExampleSelective(@Param("record") TNetworkInfo record, @Param("example") TNetworkInfoExample example);

    int updateByExample(@Param("record") TNetworkInfo record, @Param("example") TNetworkInfoExample example);

    int updateByPrimaryKeySelective(TNetworkInfo record);

    int updateByPrimaryKey(TNetworkInfo record);
}