/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsi.fs.server.service;

import java.util.List;

import com.gy.hsi.fs.server.common.framework.mybatis.mapvo.TFsFileMetaDataDel;
import com.gy.hsi.fs.server.common.framework.mybatis.mapvo.ext.SimpleFileMetaDataDel;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsi-fs-server
 * 
 *  Package Name    : com.gy.hsi.fs.server.service
 * 
 *  File Name       : IFileMetaDataDelService.java
 * 
 *  Creation Date   : 2015-05-15
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 删除的文件元数据访问service接口
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public interface IFileMetaDataDelService {

	/**
	 * 查询出所有待删除的文件元数据
	 * 
	 * @param offset
	 * @param limit
	 * @return
	 */
	public List<SimpleFileMetaDataDel> selectWaitingDeletedByExample(
			int offset, int limit);

	/**
	 * 查询出待删除的文件元数据
	 * 
	 * @param fileId
	 * @return
	 */
	public SimpleFileMetaDataDel selectWaitingDeletedByFileId(String fileId);

	/**
	 * 插入文件相关数据
	 * 
	 * @param fileMetaDataDel
	 * @return
	 */
	public boolean insertFileMetaDataDel(TFsFileMetaDataDel fileMetaDataDel);

	/**
	 * 累加失败次数
	 * 
	 * @param id
	 * @param newFailedCounts
	 * @return
	 */
	public boolean updateFailedCounts(Long id, Integer newFailedCounts);

	/**
	 * 更改文件状态
	 * 
	 * @param id
	 * @param fileStatus
	 * @return
	 */
	public boolean updateFileStatus(Long id, Byte fileStatus);

	/**
	 * 使用定时任务, 从TFS中删除文件
	 * 
	 * @return
	 */
	public boolean deleteFileFromTFSByTask();

	/**
	 * 使用定时任务, 从TFS中删除文件
	 * 
	 * @return
	 */
	public boolean deleteFileFromTFSByFileId(String fileId);
}
