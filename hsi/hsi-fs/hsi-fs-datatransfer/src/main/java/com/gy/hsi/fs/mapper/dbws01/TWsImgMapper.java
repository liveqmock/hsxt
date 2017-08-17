package com.gy.hsi.fs.mapper.dbws01;

import com.gy.hsi.fs.mapper.vo.dbws01.TWsImg;
import com.gy.hsi.fs.mapper.vo.dbws01.TWsImgExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TWsImgMapper {
    int countByExample(TWsImgExample example);

    int deleteByExample(TWsImgExample example);

    int insert(TWsImg record);

    int insertSelective(TWsImg record);

    List<TWsImg> selectByExample(TWsImgExample example);

    int updateByExampleSelective(@Param("record") TWsImg record, @Param("example") TWsImgExample example);

    int updateByExample(@Param("record") TWsImg record, @Param("example") TWsImgExample example);
}