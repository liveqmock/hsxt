package com.gy.hsxt.uc.consumer.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.uc.as.bean.consumer.AsUserActionLog;
import com.gy.hsxt.uc.consumer.bean.UserActionLog;

public interface UserActionLogMapper {

	int deleteByPrimaryKey(String logId);

	int insertSelective(UserActionLog record);

	UserActionLog selectByPrimaryKey(String logId);

	int updateByPrimaryKeySelective(UserActionLog record);

	int updateByPrimaryKey(UserActionLog record);
	
	int countAsUserActionLog(@Param("custId") String custId,
			@Param("action") String action,@Param("beginDate")String beginDate,@Param("endDate")String endDate);
	/**
	 * 
	 * @param custId
	 * @param action
	 * @param beginDate
	 * @param endDate
	 * @param page
	 * @return
	 */
	List<AsUserActionLog> pageUserActionInfo(@Param("custId") String custId,
			@Param("action") String action,@Param("beginDate")String beginDate,@Param("endDate")String endDate, @Param("page") Page page);

}