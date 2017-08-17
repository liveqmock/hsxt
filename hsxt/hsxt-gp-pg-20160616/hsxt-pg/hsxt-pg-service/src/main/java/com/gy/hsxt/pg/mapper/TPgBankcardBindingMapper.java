package com.gy.hsxt.pg.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gy.hsxt.pg.mapper.vo.TPgBankcardBinding;

public interface TPgBankcardBindingMapper {

	int deleteByPrimaryKey(String bankCardNo);

	int insert(TPgBankcardBinding record);

	TPgBankcardBinding selectByPrimaryKey(String bankCardNo);

	List<TPgBankcardBinding> selectByBindingStatus(
			@Param("dateBefore") String dateBefore,
			@Param("bindingStatus") int bindingStatus,
			@Param("fromIndex") int fromIndex,
			@Param("limitCount") int limitCount);

	int updateByPrimaryKeySelective(TPgBankcardBinding record);

	int updateByPrimaryKey(TPgBankcardBinding record);
}