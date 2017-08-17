package com.gy.hsi.fs.mapper.dbbs01;

import com.gy.hsi.fs.mapper.vo.dbbs01.TBsCompanyStop;
import com.gy.hsi.fs.mapper.vo.dbbs01.TBsCompanyStopExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TBsCompanyStopMapper {
    int countByExample(TBsCompanyStopExample example);

    int deleteByExample(TBsCompanyStopExample example);

    int deleteByPrimaryKey(String applyId);

    int insert(TBsCompanyStop record);

    int insertSelective(TBsCompanyStop record);

    List<TBsCompanyStop> selectByExample(TBsCompanyStopExample example);

    TBsCompanyStop selectByPrimaryKey(String applyId);

    int updateByExampleSelective(@Param("record") TBsCompanyStop record, @Param("example") TBsCompanyStopExample example);

    int updateByExample(@Param("record") TBsCompanyStop record, @Param("example") TBsCompanyStopExample example);

    int updateByPrimaryKeySelective(TBsCompanyStop record);

    int updateByPrimaryKey(TBsCompanyStop record);
}