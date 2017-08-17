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

import com.gy.hsi.fs.mapper.dbws01.TWsImgMapper;

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
public abstract class WSMapperSupporter {

	@Resource(name="ws_mapperDoor")
	private MapperDoor mapperDoor;

	public TWsImgMapper getTWsImgMapper() {
		return mapperDoor.getMapper(TWsImgMapper.class);
	}
	
}
