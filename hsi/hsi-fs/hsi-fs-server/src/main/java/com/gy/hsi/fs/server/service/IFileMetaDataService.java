/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsi.fs.server.service;

import com.gy.hsi.fs.common.exception.FsFileNotExistException;
import com.gy.hsi.fs.server.common.framework.mybatis.mapvo.TFsFileMetaData;
import com.gy.hsi.fs.server.common.framework.mybatis.mapvo.ext.FileName;
import com.gy.hsi.fs.server.common.framework.mybatis.mapvo.ext.SimpleFileMetaInfo;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsi-fs-server
 * 
 *  Package Name    : com.gy.hsi.fs.server.service
 * 
 *  File Name       : IFileMetaDataService.java
 * 
 *  Creation Date   : 2015-05-15
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 文件元数据访问service接口
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public interface IFileMetaDataService {

	/**
	 * 插入文件相关数据[操作用户一定是文件属主本人或者匿名]
	 * 
	 * @param fileMetaData
	 * @return
	 */
	public boolean insertFileMetaData(TFsFileMetaData fileMetaData);

	/**
	 * 修改文件相关数据
	 * 
	 * @param fileMetaData
	 * @return
	 */
	public boolean updateFileMetaData(TFsFileMetaData fileMetaData);

	/**
	 * 查询文件基本信息
	 * 
	 * @param fileId
	 * @return
	 */
	public TFsFileMetaData queryFileMetaData(String fileId);

	/**
	 * 查询SimpleFileMetaInfo
	 * 
	 * @param fileId
	 * @return
	 */
	public SimpleFileMetaInfo querySimpleFileMetaInfo(String fileId);

	/**
	 * 是否为该文件的属主
	 * 
	 * @param fileId
	 * @param userId
	 * @return
	 * @throws FsFileNotExistException
	 */
	public boolean isOwnerUser(String fileId, String userId)
			throws FsFileNotExistException;

	/**
	 * 是否为匿名上传的文件
	 * 
	 * @param fileId
	 * @return
	 * @throws FsFileNotExistException
	 */
	public boolean isAnonyUploadedFile(String fileId)
			throws FsFileNotExistException;

	/**
	 * 是否存在重复的文件id
	 * 
	 * @param fileId
	 * @return
	 */
	public boolean hasDumplicatedFileId(String fileId);

	/**
	 * 是否存在指定的文件
	 * 
	 * @param fileId
	 * @return
	 */
	public boolean isExist(String fileId);

	/**
	 * 是否为public-read权限文件
	 * 
	 * @param fileId
	 * @return
	 * @throws FsFileNotExistException
	 */
	public boolean isPublicReadFile(String fileId)
			throws FsFileNotExistException;

	/**
	 * 根据文件存储id查询文件名称
	 * 
	 * @param fileStorageId
	 * @return
	 */
	public FileName queryFileNameByFileStorageId(String fileStorageId);

	/**
	 * 根据文件id删除文件
	 * 
	 * @param fileId
	 * @return
	 */
	public int deleteFileByFileId(String fileId);
}
