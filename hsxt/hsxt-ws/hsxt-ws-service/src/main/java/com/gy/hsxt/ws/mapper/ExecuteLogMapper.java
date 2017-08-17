package com.gy.hsxt.ws.mapper;

import org.apache.ibatis.annotations.Param;

import com.gy.hsxt.ws.bean.ExecuteLog;

public interface ExecuteLogMapper {

	int insertSelective(ExecuteLog record);

	ExecuteLog querySucessLog(@Param("executeDate") String executeDate,
			@Param("executeService") String executeService);
}