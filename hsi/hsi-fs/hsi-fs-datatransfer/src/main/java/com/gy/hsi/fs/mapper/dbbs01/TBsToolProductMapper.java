package com.gy.hsi.fs.mapper.dbbs01;

import com.gy.hsi.fs.mapper.vo.dbbs01.TBsToolProduct;
import com.gy.hsi.fs.mapper.vo.dbbs01.TBsToolProductExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TBsToolProductMapper {
    int countByExample(TBsToolProductExample example);

    int deleteByExample(TBsToolProductExample example);

    int deleteByPrimaryKey(String productId);

    int insert(TBsToolProduct record);

    int insertSelective(TBsToolProduct record);

    List<TBsToolProduct> selectByExample(TBsToolProductExample example);

    TBsToolProduct selectByPrimaryKey(String productId);

    int updateByExampleSelective(@Param("record") TBsToolProduct record, @Param("example") TBsToolProductExample example);

    int updateByExample(@Param("record") TBsToolProduct record, @Param("example") TBsToolProductExample example);

    int updateByPrimaryKeySelective(TBsToolProduct record);

    int updateByPrimaryKey(TBsToolProduct record);
}