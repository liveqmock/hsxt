/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsi.fs.server.common.framework.mybatis.mapvo.ext;

import com.gy.hsi.fs.server.common.framework.mybatis.mapvo.TFsFileMetaDataDelExample;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsi-fs-server
 * 
 *  Package Name    : com.gy.hsi.fs.server.common.framework.mybatis.mapvo.ext
 * 
 *  File Name       : ExtendedFileMetaDataDelExample.java
 * 
 *  Creation Date   : 2015年7月14日
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
public class ExtendedFileMetaDataDelExample extends TFsFileMetaDataDelExample {
	protected int offset = 0;
	protected int limit = 10;

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

}
