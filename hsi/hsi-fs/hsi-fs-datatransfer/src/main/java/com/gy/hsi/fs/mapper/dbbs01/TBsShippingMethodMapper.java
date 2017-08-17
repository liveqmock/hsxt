package com.gy.hsi.fs.mapper.dbbs01;

import com.gy.hsi.fs.mapper.vo.dbbs01.TBsShippingMethod;
import com.gy.hsi.fs.mapper.vo.dbbs01.TBsShippingMethodExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TBsShippingMethodMapper {
    int countByExample(TBsShippingMethodExample example);

    int deleteByExample(TBsShippingMethodExample example);

    int deleteByPrimaryKey(String smId);

    int insert(TBsShippingMethod record);

    int insertSelective(TBsShippingMethod record);

    List<TBsShippingMethod> selectByExample(TBsShippingMethodExample example);

    TBsShippingMethod selectByPrimaryKey(String smId);

    int updateByExampleSelective(@Param("record") TBsShippingMethod record, @Param("example") TBsShippingMethodExample example);

    int updateByExample(@Param("record") TBsShippingMethod record, @Param("example") TBsShippingMethodExample example);

    int updateByPrimaryKeySelective(TBsShippingMethod record);

    int updateByPrimaryKey(TBsShippingMethod record);
}