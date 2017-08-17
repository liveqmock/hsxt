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
 *  File Name       : HttpRequestParam.java
 * 
 *  Creation Date   : 2015年6月1日
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : http请求参数[属于接口字段定义, 不得随意修改]
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class HttpRequestParam {

	/** 文件上传请求参数 **/
	public static interface UploadRequestKey {
		/** 文件名称 **/
		public static final String FILE_NAME = FsRequestKey.FILE_NAME;

		/** 文件后缀 **/
		public static final String FILE_SUFFIX = FsRequestKey.FILE_SUFFIX;

		/** 文件权限 **/
		public static final String FILE_PERMISSION = FsRequestKey.FILE_PERMISSION;

		/** 文件id, 仅用于覆盖上传 **/
		public static final String FILE_ID = FsRequestKey.FILE_ID;

		/** 操作用户id **/
		public static final String USER_ID = FsRequestKey.USER_ID;

		/** 安全令牌 **/
		public static final String TOKEN = FsRequestKey.TOKEN;
		
		/** 渠道 **/
		public static final String CHANNEL = FsRequestKey.CHANNEL;

		/** 是否通过匿名方式 **/
		public static final String BY_ANONYMOUS_MODE = FsRequestKey.BY_ANONYMOUS_MODE;

		/** 是否为有效文件名称 [文件系统内部使用字段, 不对外开放], 仅用于java客户端 **/
		public static final String $IS_VALID_FILENAME = "$@&isValidFilename@$@";
	}

	/** 文件下载请求参数 **/
	public static interface DownloadRequestKey {
		/** 文件id **/
		public static final String FILE_ID = FsRequestKey.FILE_ID;

		/** 使用保存的文件名称 **/
		public static final String USE_SAVED_FILENAME = FsRequestKey.USE_SAVED_FILENAME;

		/** 操作用户id **/
		public static final String USER_ID = FsRequestKey.USER_ID;

		/** 安全令牌 **/
		public static final String TOKEN = FsRequestKey.TOKEN;
		
		/** 渠道 **/
		public static final String CHANNEL = FsRequestKey.CHANNEL;
	}

	/** 删除文件请求参数 **/
	public static interface DeleteFileRequestKey {
		/** 文件id **/
		public static final String FILE_ID = FsRequestKey.FILE_ID;

		/** 多文件删除 **/
		public static final String IS_BATCH_DEL = FsRequestKey.IS_BATCH_DEL;

		/** 操作用户id **/
		public static final String USER_ID = FsRequestKey.USER_ID;

		/** 安全令牌 **/
		public static final String TOKEN = FsRequestKey.TOKEN;
		
		/** 渠道 **/
		public static final String CHANNEL = FsRequestKey.CHANNEL;
	}

	/** 设置文件权限请求参数 **/
	public static interface SetPermissionRequestKey {
		/** 文件id **/
		public static final String FILE_ID = FsRequestKey.FILE_ID;

		/** 文件权限 **/
		public static final String FILE_PERMISSION = FsRequestKey.FILE_PERMISSION;

		/** 操作用户id **/
		public static final String USER_ID = FsRequestKey.USER_ID;

		/** 安全令牌 **/
		public static final String TOKEN = FsRequestKey.TOKEN;
		
		/** 渠道 **/
		public static final String CHANNEL = FsRequestKey.CHANNEL;
	}

	/** 查看文件信息请求参数 **/
	public static interface ViewFileInfoRequestKey {
		/** 文件id **/
		public static final String FILE_ID = FsRequestKey.FILE_ID;

		/** 操作用户id **/
		public static final String USER_ID = FsRequestKey.USER_ID;

		/** 安全令牌 **/
		public static final String TOKEN = FsRequestKey.TOKEN;
		
		/** 渠道 **/
		public static final String CHANNEL = FsRequestKey.CHANNEL;
	}

	/** 文件上传请求参数 **/
	public static interface FsRequestKey {
		/** 文件名称 **/
		public static final String FILE_NAME = "fileName";

		/** 文件后缀 **/
		public static final String FILE_SUFFIX = "fileSuffix";

		/** 文件权限 **/
		public static final String FILE_PERMISSION = "filePermission";

		/** 文件id, 仅用于覆盖上传 **/
		public static final String FILE_ID = "fileId";

		/** 文件上传UUID, 用于大文件的断点续传的id **/
		public static final String FILE_UPLOAD_UUID = "fileUploadUUID";

		/** 操作用户id **/
		public static final String USER_ID = "userId";

		/** 安全令牌 **/
		public static final String TOKEN = "token";
		
		/** 渠道 **/
		public static final String CHANNEL = "channel";

		/** 使用保存的文件名称 **/
		public static final String USE_SAVED_FILENAME = "useSavedFileName";

		/** 是否通过匿名方式 **/
		public static final String BY_ANONYMOUS_MODE = "byAnonymousMode";

		/** 多文件删除 **/
		public static final String IS_BATCH_DEL = "isBatchDel";
	}
}
