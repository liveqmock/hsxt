package com.gy.hsxt.uf.mapper;

import com.gy.hsxt.uf.mapper.vo.TUfPacketLogDetail;
import com.gy.hsxt.uf.mapper.vo.TUfPacketLogDetailExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TUfPacketLogDetailMapper {
    int countByExample(TUfPacketLogDetailExample example);

    int deleteByExample(TUfPacketLogDetailExample example);

    int deleteByPrimaryKey(String logStackTraceId);

    int insert(TUfPacketLogDetail record);

    int insertSelective(TUfPacketLogDetail record);

    List<TUfPacketLogDetail> selectByExampleWithBLOBs(TUfPacketLogDetailExample example);

    List<TUfPacketLogDetail> selectByExample(TUfPacketLogDetailExample example);

    TUfPacketLogDetail selectByPrimaryKey(String logStackTraceId);

    int updateByExampleSelective(@Param("record") TUfPacketLogDetail record, @Param("example") TUfPacketLogDetailExample example);

    int updateByExampleWithBLOBs(@Param("record") TUfPacketLogDetail record, @Param("example") TUfPacketLogDetailExample example);

    int updateByExample(@Param("record") TUfPacketLogDetail record, @Param("example") TUfPacketLogDetailExample example);

    int updateByPrimaryKeySelective(TUfPacketLogDetail record);

    int updateByPrimaryKeyWithBLOBs(TUfPacketLogDetail record);

    int updateByPrimaryKey(TUfPacketLogDetail record);
}