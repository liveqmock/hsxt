/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsi.fs.server.common.framework.mybatis.mapper.ext;

import java.util.List;

import com.gy.hsi.fs.server.common.framework.mybatis.mapvo.ext.ExtendedFileMetaDataDelExample;
import com.gy.hsi.fs.server.common.framework.mybatis.mapvo.ext.SimpleFileMetaDataDel;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsi-fs-server
 * 
 *  Package Name    : com.gy.hsi.fs.server.common.framework.mybatis.mapper.ext
 * 
 *  File Name       : ExtendedFileMetaDataDelMapper.java
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

public interface ExtendedFileMetaDataDelMapper {
	/**
	 * 查询带删除的文件元数据信息[每次查出200条]
	 * 
	 * @param example
	 * @return
	 */
	List<SimpleFileMetaDataDel> selectWaitingDeletedByExample(
			ExtendedFileMetaDataDelExample example);

	/**
	 * 查询出待删除的文件元数据
	 * 
	 * @param fileId
	 * @return
	 */
	public SimpleFileMetaDataDel selectWaitingDeletedByFileId(String fileId);
}
