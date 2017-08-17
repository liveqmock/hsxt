/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsi.fs.server.common.framework.mybatis;

import org.springframework.beans.factory.annotation.Autowired;

import com.gy.hsi.fs.server.common.framework.mybatis.mapper.TFsCommonParamsMapper;
import com.gy.hsi.fs.server.common.framework.mybatis.mapper.TFsFileMetaDataDelMapper;
import com.gy.hsi.fs.server.common.framework.mybatis.mapper.TFsFileMetaDataMapper;
import com.gy.hsi.fs.server.common.framework.mybatis.mapper.TFsFileOperationLogMapper;
import com.gy.hsi.fs.server.common.framework.mybatis.mapper.TFsPermissionRuleMapper;
import com.gy.hsi.fs.server.common.framework.mybatis.mapper.ext.ExtendedFileMetaDataDelMapper;
import com.gy.hsi.fs.server.common.framework.mybatis.mapper.ext.ExtendedFileMetaDataMapper;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsi-fs-server
 * 
 *  Package Name    : com.gy.hsi.fs.server.common.framework.mybatis
 * 
 *  File Name       : MapperSupporter.java
 * 
 *  Creation Date   : 2015年5月20日
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : none
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public abstract class MapperSupporter {

	@Autowired
	private MapperDoor mapperDoor;

	public TFsCommonParamsMapper getTFsCommonParamsMapper() {
		return mapperDoor.getMapper(TFsCommonParamsMapper.class);
	}

	public TFsFileMetaDataMapper getTFsFileMetaDataMapper() {
		return mapperDoor.getMapper(TFsFileMetaDataMapper.class);
	}

	public TFsFileMetaDataDelMapper getTFsFileMetaDataDelMapper() {
		return mapperDoor.getMapper(TFsFileMetaDataDelMapper.class);
	}

	public TFsFileOperationLogMapper getTFsFileOperationLogMapper() {
		return mapperDoor.getMapper(TFsFileOperationLogMapper.class);
	}

	public TFsPermissionRuleMapper getTFsPermissionRuleMapper() {
		return mapperDoor.getMapper(TFsPermissionRuleMapper.class);
	}

	public ExtendedFileMetaDataMapper getExtendedFileMetaDataMapper() {
		return mapperDoor.getMapper(ExtendedFileMetaDataMapper.class);
	}

	public ExtendedFileMetaDataDelMapper getExtendedFileMetaDataDelMapper() {
		return mapperDoor.getMapper(ExtendedFileMetaDataDelMapper.class);
	}
}
