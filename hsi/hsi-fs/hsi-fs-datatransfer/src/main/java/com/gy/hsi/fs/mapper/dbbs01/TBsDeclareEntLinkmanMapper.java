package com.gy.hsi.fs.mapper.dbbs01;

import com.gy.hsi.fs.mapper.vo.dbbs01.TBsDeclareEntLinkman;
import com.gy.hsi.fs.mapper.vo.dbbs01.TBsDeclareEntLinkmanExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TBsDeclareEntLinkmanMapper {
    int countByExample(TBsDeclareEntLinkmanExample example);

    int deleteByExample(TBsDeclareEntLinkmanExample example);

    int deleteByPrimaryKey(String applyId);

    int insert(TBsDeclareEntLinkman record);

    int insertSelective(TBsDeclareEntLinkman record);

    List<TBsDeclareEntLinkman> selectByExample(TBsDeclareEntLinkmanExample example);

    TBsDeclareEntLinkman selectByPrimaryKey(String applyId);

    int updateByExampleSelective(@Param("record") TBsDeclareEntLinkman record, @Param("example") TBsDeclareEntLinkmanExample example);

    int updateByExample(@Param("record") TBsDeclareEntLinkman record, @Param("example") TBsDeclareEntLinkmanExample example);

    int updateByPrimaryKeySelective(TBsDeclareEntLinkman record);

    int updateByPrimaryKey(TBsDeclareEntLinkman record);
}