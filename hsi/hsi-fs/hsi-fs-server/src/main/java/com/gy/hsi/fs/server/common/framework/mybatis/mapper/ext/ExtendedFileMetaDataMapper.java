/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsi.fs.server.common.framework.mybatis.mapper.ext;

import org.apache.ibatis.annotations.Param;

import com.gy.hsi.fs.server.common.framework.mybatis.mapvo.ext.FileName;
import com.gy.hsi.fs.server.common.framework.mybatis.mapvo.ext.SimpleFileMetaInfo;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsi-fs-server
 * 
 *  Package Name    : com.gy.hsi.fs.server.common.framework.mybatis.mapper.ext
 * 
 *  File Name       : ExtendedFileMetaDataMapper.java
 * 
 *  Creation Date   : 2015年5月20日
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 扩展文件元数据操作接口
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/

public interface ExtendedFileMetaDataMapper {
	/**
	 * 查询文件索引数据
	 * 
	 * @param fileId
	 * @return
	 */
	public SimpleFileMetaInfo querySimpleFileMetaInfo(
			@Param("fileId") String fileId);

	/**
	 * 查询文件id是否存在
	 * 
	 * @param fileId
	 * @return
	 */
	public int queryFileIdCounts(@Param("fileId") String fileId);

	/**
	 * 根据文件存储id查询文件名称
	 * 
	 * @param fileStorageId
	 * @return
	 */
	public FileName queryFileNameByFileStorageId(
			@Param("fileStorageId") String fileStorageId);

	/**
	 * 根据文件id查询文件名称
	 * 
	 * @param fileId
	 * @return
	 */
	public FileName queryFileNameByFileId(@Param("fileId") String fileId);

	/**
	 * 查询文件权限
	 * 
	 * @param fileId
	 * @return
	 */
	public String queryFilePermissionByFileId(@Param("fileId") String fileId);
}
