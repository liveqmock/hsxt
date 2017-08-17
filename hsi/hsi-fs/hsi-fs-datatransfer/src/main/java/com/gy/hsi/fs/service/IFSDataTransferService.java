/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsi.fs.service;

import java.util.List;

import com.gy.hsi.fs.mapper.vo.TFsFileMetaData;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsi-fs-datatransfer
 * 
 *  Package Name    : com.gy.hsi.fs.service
 * 
 *  File Name       : IFSDataTransferService.java
 * 
 *  Creation Date   : 2015年11月20日
 * 
 *  Author          : Administrator
 * 
 *  Purpose         : TODO
 * 
 * 
 *  History         : TODO
 * 
 * </PRE>
 ***************************************************************************/
public interface IFSDataTransferService {

	/**
	 * 将迁移文件的数据插入到文件的元数据表中
	 * 
	 * @param tfsFileId
	 * @return
	 */
	public String insert2FsDb(String tfsFileId, boolean isUCHeadshot);

	/**
	 * 移除所有UC头像临时数据
	 * 
	 * @return
	 */
	public boolean removeAllTempDataForUCHeadshot();

	/**
	 * 查询文件数据
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	public List<TFsFileMetaData> queryTFsFileMetaDatasForDownloadTFS(int start,
			int end);

	/**
	 * 查询文件数据(上传)
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	public List<TFsFileMetaData> queryTFsFileMetaDatasForUpload(int start,
			int end);

	/**
	 * 查询文件数据
	 * 
	 * @param isOnlyTfs
	 * @param start
	 * @param end
	 * @return
	 */
	public List<TFsFileMetaData> queryTFsFileMetaDatasForDeleteAllUploaded(
			int start, int end);
	
	/**
	 * 查询文件数据
	 * 
	 * @param isOnlyTfs
	 * @param start
	 * @param end
	 * @return
	 */
	public List<TFsFileMetaData> queryTFsFileMetaDatasForDeleteAllInDB(
			int start, int end);

	/**
	 * 查询文件数据
	 * 
	 * @param isOnlyTfs
	 * @param start
	 * @param end
	 * @return
	 */
	public TFsFileMetaData queryByFileId(String fileId);

	public TFsFileMetaData queryByFileStorageId(String fileStorageId);

	public boolean deleteByFileIdOrStoreId(String fileId, String fileStoreId);

	public boolean updateByFileStoreId(String oldFileStoreId,
			String newFileStoreId, long size);

	public int countTFSFiles();

	public int countFTPFiles();
}
