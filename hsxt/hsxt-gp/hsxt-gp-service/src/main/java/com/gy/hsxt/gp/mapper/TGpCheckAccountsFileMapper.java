package com.gy.hsxt.gp.mapper;

import com.gy.hsxt.gp.mapper.vo.TGpCheckAccountsFile;

public interface TGpCheckAccountsFileMapper {
	
	/**
	 * 插入
	 * 
	 * @param record
	 * @return
	 */
	int insert(TGpCheckAccountsFile record);

	/**
	 * 根据文件名查文件状态
	 * 
	 * @param fileName
	 * @return
	 */
	TGpCheckAccountsFile selectByFileName(String fileName);

	/**
	 * 根据文件名更新文件状态
	 * 
	 * @param record
	 * @return
	 */
	int updateByFileName(TGpCheckAccountsFile record);

	/**
	 * 根据文件名删除文件
	 * 
	 * @param fileName
	 * @return
	 */
	int deleteByFileName(String fileName);
}