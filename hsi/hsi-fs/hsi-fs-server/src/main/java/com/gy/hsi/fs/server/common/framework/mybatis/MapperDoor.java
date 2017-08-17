/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsi.fs.server.common.framework.mybatis;

import org.mybatis.spring.support.SqlSessionDaoSupport;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsi-fs-server
 * 
 *  Package Name    : com.gy.hsi.fs.server.factory
 * 
 *  File Name       : MapperDoor.java
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
public class MapperDoor extends SqlSessionDaoSupport {

	/**
	 * 取得对应的mapper
	 * 
	 * @param clasz
	 * @return
	 */
	public <T> T getMapper(Class<T> clasz) {
		return this.getSqlSession().getMapper(clasz);
	}
}
