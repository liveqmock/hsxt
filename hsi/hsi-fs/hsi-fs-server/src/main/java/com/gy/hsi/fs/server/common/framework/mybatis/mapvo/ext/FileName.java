/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsi.fs.server.common.framework.mybatis.mapvo.ext;

import com.gy.hsi.fs.common.utils.StringUtils;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsi-fs-server
 * 
 *  Package Name    : com.gy.hsi.fs.server.common.framework.mybatis.mapvo.ext
 * 
 *  File Name       : FileName.java
 * 
 *  Creation Date   : 2015年5月23日
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 文件名称对象
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class FileName {
	private String fileName;
	private String fileSuffix;

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileSuffix() {
		return fileSuffix;
	}

	public void setFileSuffix(String fileSuffix) {
		this.fileSuffix = fileSuffix;
	}

	public String getFullFileName(String defaultFileName) {
		if (StringUtils.isEmpty(this.fileName)) {
			this.fileName = defaultFileName; // FsServerConstant.UNKNOWN_FILE_NAME;

			if (!StringUtils.isEmpty(this.fileSuffix)) {
				return this.fileName + "." + this.fileSuffix;
			}
		}

		return this.fileName;
	}
}
