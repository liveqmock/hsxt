/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsi.fs.server.common.constant;

import com.gy.hsi.fs.common.utils.FileUtils;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsi-fs-server
 * 
 *  Package Name    : com.gy.hsi.fs.server.common.constant
 * 
 *  File Name       : FsServerConstant.java
 * 
 *  Creation Date   : 2015年5月19日
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 文件系统服务端常量
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class FsServerConstant {

	/** 上传文件缓冲区大小 **/
	public static final int UPLOAD_CACHE_SIZE_THRESHOLD = 1024 * 1024;

	/** 上传的大文件界定边界 : 2M **/
	public static final int SMALL_FILE_SIZE_UPPER_LIMIT = 2 * 1024 * 1024;

	/** 文件上传临时缓存的文件目录[结尾的斜杠不能省略] **/
	public static final String UPLOAD_FILE_TEMPCACHE_ROOT_DIR = FileUtils
			.assembleFilePathByUserDir("./fs_temp_cache/upload/");

	/** 未知文件名称 **/
	public static final String UNKNOWN_FILE_NAME = "unknown";

	/** 上传方式 **/
	public static enum UploadMode {
		/** 实名上传 **/
		REAL_NAME("01"),

		/** 匿名上传 **/
		ANONY("02");

		private String value;

		UploadMode(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	}

	/** 操作用户类型 **/
	public static enum OptUserType {
		/** 文件属主 **/
		OWNER("01"),

		/** 其他登录用户 **/
		OTHER_LOGINER("02"),

		/** 游客 **/
		VISITOR("03");

		private String value;

		OptUserType(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}

		public boolean valueEquals(String value) {
			return this.value.equals(value);
		}
	}

	/** 功能索引: 严重声明, 每个索引值需要与数据库脚本中的权限矩阵次序保持一致, 不得随意变动 ！！！！！！ **/
	public static interface FunctionIndex {
		/** 文件上传 **/
		public static final int FILE_UPLOAD = 0;

		/** 文件覆盖上传 **/
		public static final int FILE_OVERRIDE_UPLOAD = 1;

		/** 文件下载 **/
		public static final int FILE_DOWNLOAD = 2;

		/** 文件删除 **/
		public static final int FILE_DELETE = 3;

		/** 文件信息查看 **/
		public static final int FILE_INFO_VIEW = 4;

		/** 文件权限设置 **/
		public static final int FILE_PERMISSION = 5;
	}

}
