package com.gy.hsxt.pg.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gy.hsxt.pg.mapper.vo.TPgBankcardBindingTmp;

public interface TPgBankcardBindingTmpMapper {

	int deleteByPrimaryKey(String bankCardNo);

	int insert(TPgBankcardBindingTmp record);

	TPgBankcardBindingTmp selectByPrimaryKey(String bankCardNo);

	List<TPgBankcardBindingTmp> selectByBindingStatus(
			@Param("dateBefore") String dateBefore,
			@Param("bindingStatus") int bindingStatus,
			@Param("fromIndex") int fromIndex,
			@Param("limitCount") int limitCount);

	int updateByPrimaryKeySelective(TPgBankcardBindingTmp record);

	int updateByPrimaryKey(TPgBankcardBindingTmp record);
}