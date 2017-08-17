package com.gy.hsxt.pg.mapper;

import com.gy.hsxt.pg.mapper.vo.TPgCheckAccountsFile;

public interface TPgCheckAccountsFileMapper {
	
	/**
	 * 插入记录
	 * 
	 * @param record
	 * @return
	 */
	int insert(TPgCheckAccountsFile record);

	/**
	 * 根据文件名查文件状态
	 * 
	 * @param fileName
	 * @return
	 */
	TPgCheckAccountsFile selectByFileName(String fileName);

	/**
	 * 根据文件名更新文件状态
	 * 
	 * @param record
	 * @return
	 */
	int updateByFileName(TPgCheckAccountsFile record);

	/**
	 * 根据文件名删除文件
	 * 
	 * @param fileName
	 * @return
	 */
	int deleteByFileName(String fileName);
}