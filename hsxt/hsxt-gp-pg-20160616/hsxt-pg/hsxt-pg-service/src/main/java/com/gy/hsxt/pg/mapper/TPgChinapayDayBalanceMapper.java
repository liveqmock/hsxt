package com.gy.hsxt.pg.mapper;

import java.util.Date;
import java.util.List;

import com.gy.hsxt.pg.mapper.vo.TPgChinapayDayBalance;

public interface TPgChinapayDayBalanceMapper {

	int insert(TPgChinapayDayBalance record);

	TPgChinapayDayBalance selectByPrimaryKey(String id);

	TPgChinapayDayBalance selectByFileName(String fileName);

	List<TPgChinapayDayBalance> selectByBalanceDate(Date balanceDate);

	int updateStatusByFileName(TPgChinapayDayBalance record);
}