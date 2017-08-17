package com.gy.hsi.fs.mapper.dbbs01;

import com.gy.hsi.fs.mapper.vo.dbbs01.TBsFilingAptitude;
import com.gy.hsi.fs.mapper.vo.dbbs01.TBsFilingAptitudeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TBsFilingAptitudeMapper {
    int countByExample(TBsFilingAptitudeExample example);

    int deleteByExample(TBsFilingAptitudeExample example);

    int deleteByPrimaryKey(String filingAptId);

    int insert(TBsFilingAptitude record);

    int insertSelective(TBsFilingAptitude record);

    List<TBsFilingAptitude> selectByExample(TBsFilingAptitudeExample example);

    TBsFilingAptitude selectByPrimaryKey(String filingAptId);

    int updateByExampleSelective(@Param("record") TBsFilingAptitude record, @Param("example") TBsFilingAptitudeExample example);

    int updateByExample(@Param("record") TBsFilingAptitude record, @Param("example") TBsFilingAptitudeExample example);

    int updateByPrimaryKeySelective(TBsFilingAptitude record);

    int updateByPrimaryKey(TBsFilingAptitude record);
}