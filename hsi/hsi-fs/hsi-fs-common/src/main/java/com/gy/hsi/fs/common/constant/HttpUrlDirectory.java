/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsi.fs.common.constant;


/***************************************************************************
 * <PRE>
 *  Project Name    : hsi-fs-common
 * 
 *  Package Name    : com.gy.hsi.fs.common.constant
 * 
 *  File Name       : HttpUrlDirectory.java
 * 
 *  Creation Date   : 2015年6月6日
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : http请求Url常量定义
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class HttpUrlDirectory {
	/** 文件系统url关键字 **/
	public static interface FsUrlKeywords {
		public static final String FS = "/fs";
		public static final String UPLOAD = "/upload";
		public static final String UPLOAD_BATCH = "/uploadBatch";
		public static final String DOWNLOAD = "/download";
		public static final String DELETE_FILE = "/deleteFile";
		public static final String VIEW_FILE_INFO = "/viewFileInfo";
		public static final String SET_PERMISSION = "/setPermission";
	}

	/** 文件系统url关键字 **/
	public static interface FsUrlSubDirectory {
		/** 上传 **/
		public static final String URL_UPLOAD = FsUrlKeywords.FS
				+ FsUrlKeywords.UPLOAD;

		/** 批量上传 **/
		public static final String URL_UPLOAD_BATCH = FsUrlKeywords.FS
				+ FsUrlKeywords.UPLOAD;

		/** 下载 **/
		public static final String URL_DOWNLOAD = FsUrlKeywords.FS
				+ FsUrlKeywords.DOWNLOAD;

		/** 删除文件 **/
		public static final String URL_DELETE_FILE = FsUrlKeywords.FS
				+ FsUrlKeywords.DELETE_FILE;

		/** 查看文件基本信息 **/
		public static final String URL_VIEW_FILE_INFO = FsUrlKeywords.FS
				+ FsUrlKeywords.VIEW_FILE_INFO;

		/** 设置文件权限 **/
		public static final String URL_SET_PERMISSION = FsUrlKeywords.FS
				+ FsUrlKeywords.SET_PERMISSION;
	}
}
