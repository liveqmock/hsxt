package com.gy.hsxt.gp.mapper;

import org.apache.ibatis.annotations.Param;

import com.gy.hsxt.gp.mapper.vo.TGpSequence;

public interface TGpSequenceMapper {

	/**
	 * 查询当前序列号
	 * 
	 * @param seqType
	 * @param sysInstanceNo
	 * @return
	 */
	String selectCurrSeqNumber(@Param("seqType") Integer seqType,
			@Param("sysInstanceNo") String sysInstanceNo);

	/**
	 * 插入序列号
	 * 
	 * @param record
	 * @return
	 */
	int insertSequence(TGpSequence record);

	/**
	 * 更新序列号
	 * 
	 * @param record
	 * @return
	 */
	int updateSequence(TGpSequence record);
}
