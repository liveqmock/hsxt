/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsi.fs.common.constant;

import com.gy.hsi.fs.common.utils.FileUtils;


/***************************************************************************
 * <PRE>
 *  Project Name    : hsi-fs-common
 * 
 *  Package Name    : com.gy.hsi.fs.common.constant
 * 
 *  File Name       : FsConstant.java
 * 
 *  Creation Date   : 2015年5月27日
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 文件系统通用常量
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class FsConstant {
	/** utf-8编码 **/
	public static final String ENCODING_UTF8 = "utf-8";

	/** 逗号分隔符 **/
	public static final String COMMA_CHAR = ",";

	/** 中文逗号分隔符 **/
	public static final String CN_COMMA_CHAR = "，";

	/** 临时保存路径 **/
	public static final String TEMP_FILE_ROOT_PATH = FileUtils
			.assembleFilePathByUserDir("./fs_temp_files");
	
	/** 为了兼容2.0-->3.0数据迁移,默认文件属主 **/
	public static final String DEFAULT_TRANSFER_USER = "$transfer_user";

}
