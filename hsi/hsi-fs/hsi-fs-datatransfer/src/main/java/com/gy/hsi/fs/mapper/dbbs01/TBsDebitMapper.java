package com.gy.hsi.fs.mapper.dbbs01;

import com.gy.hsi.fs.mapper.vo.dbbs01.TBsDebit;
import com.gy.hsi.fs.mapper.vo.dbbs01.TBsDebitExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TBsDebitMapper {
    int countByExample(TBsDebitExample example);

    int deleteByExample(TBsDebitExample example);

    int deleteByPrimaryKey(String debitId);

    int insert(TBsDebit record);

    int insertSelective(TBsDebit record);

    List<TBsDebit> selectByExample(TBsDebitExample example);

    TBsDebit selectByPrimaryKey(String debitId);

    int updateByExampleSelective(@Param("record") TBsDebit record, @Param("example") TBsDebitExample example);

    int updateByExample(@Param("record") TBsDebit record, @Param("example") TBsDebitExample example);

    int updateByPrimaryKeySelective(TBsDebit record);

    int updateByPrimaryKey(TBsDebit record);
}