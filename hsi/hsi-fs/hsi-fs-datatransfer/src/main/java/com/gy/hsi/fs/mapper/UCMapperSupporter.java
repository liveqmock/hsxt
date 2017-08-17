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

import com.gy.hsi.fs.mapper.dbuc01.TCustIdInfoMapper;
import com.gy.hsi.fs.mapper.dbuc01.TEntExtendMapper;
import com.gy.hsi.fs.mapper.dbuc01.TNetworkInfoMapper;

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
public abstract class UCMapperSupporter {

	@Resource(name="uc_mapperDoor")
	private MapperDoor mapperDoor;

	public TCustIdInfoMapper getTCustIdInfoMapper() {
		return mapperDoor.getMapper(TCustIdInfoMapper.class);
	}

	public TEntExtendMapper getTEntExtendMapper() {
		return mapperDoor.getMapper(TEntExtendMapper.class);
	}
	
	public TNetworkInfoMapper getTNetworkInfoMapper() {
		return mapperDoor.getMapper(TNetworkInfoMapper.class);
	}
	
}
