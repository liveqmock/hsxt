/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsi.fs.mapper;

import javax.annotation.Resource;

import org.apache.log4j.Logger;

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
public abstract class FSMapperSupporter {
	/** 记录日志对象 **/
	protected final Logger logger = Logger.getLogger(this.getClass());
	
	@Resource(name="fs_mapperDoor")
	private MapperDoor mapperDoor;

	public TFsFileMetaDataMapper getTFsFileMetaDataMapper() {
		return mapperDoor.getMapper(TFsFileMetaDataMapper.class);
	}

}
