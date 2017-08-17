package com.gy.hsxt.uf.mapper;

import com.gy.hsxt.uf.mapper.vo.TUfPacketLog;
import com.gy.hsxt.uf.mapper.vo.TUfPacketLogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TUfPacketLogMapper {
    int countByExample(TUfPacketLogExample example);

    int deleteByExample(TUfPacketLogExample example);

    int deleteByPrimaryKey(Long logId);

    int insert(TUfPacketLog record);

    int insertSelective(TUfPacketLog record);

    List<TUfPacketLog> selectByExample(TUfPacketLogExample example);

    TUfPacketLog selectByPrimaryKey(Long logId);

    int updateByExampleSelective(@Param("record") TUfPacketLog record, @Param("example") TUfPacketLogExample example);

    int updateByExample(@Param("record") TUfPacketLog record, @Param("example") TUfPacketLogExample example);

    int updateByPrimaryKeySelective(TUfPacketLog record);

    int updateByPrimaryKey(TUfPacketLog record);
}