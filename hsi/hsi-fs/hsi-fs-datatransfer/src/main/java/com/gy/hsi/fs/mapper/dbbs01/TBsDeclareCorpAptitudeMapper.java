package com.gy.hsi.fs.mapper.dbbs01;

import com.gy.hsi.fs.mapper.vo.dbbs01.TBsDeclareCorpAptitude;
import com.gy.hsi.fs.mapper.vo.dbbs01.TBsDeclareCorpAptitudeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TBsDeclareCorpAptitudeMapper {
    int countByExample(TBsDeclareCorpAptitudeExample example);

    int deleteByExample(TBsDeclareCorpAptitudeExample example);

    int deleteByPrimaryKey(String aptitudeId);

    int insert(TBsDeclareCorpAptitude record);

    int insertSelective(TBsDeclareCorpAptitude record);

    List<TBsDeclareCorpAptitude> selectByExample(TBsDeclareCorpAptitudeExample example);

    TBsDeclareCorpAptitude selectByPrimaryKey(String aptitudeId);

    int updateByExampleSelective(@Param("record") TBsDeclareCorpAptitude record, @Param("example") TBsDeclareCorpAptitudeExample example);

    int updateByExample(@Param("record") TBsDeclareCorpAptitude record, @Param("example") TBsDeclareCorpAptitudeExample example);

    int updateByPrimaryKeySelective(TBsDeclareCorpAptitude record);

    int updateByPrimaryKey(TBsDeclareCorpAptitude record);
}