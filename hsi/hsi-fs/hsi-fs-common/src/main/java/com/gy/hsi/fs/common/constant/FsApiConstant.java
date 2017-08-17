/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsi.fs.common.constant;

import com.gy.hsi.fs.common.utils.StringUtils;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsi-fs-common
 * 
 *  Package Name    : com.gy.hsi.fs.common.constant
 * 
 *  File Name       : FsApiConstant.java
 * 
 *  Creation Date   : 2015年7月17日
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 文件系统对外暴露的api接口涉及到的常量
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class FsApiConstant {
	
	/** 值 **/
	public static interface Value {
		/** 1 **/
		public static final char VALUE_1 = '1';

		/** 0 **/
		public static final char VALUE_0 = '0';
	}
		
	/** http响应结果常量 **/
	public static interface FsRespHeaderKey {
		/** 结果代码 **/
		public static final String RESULT_CODE = "resultCode";

		/** 结果描述 **/
		public static final String RESULT_DESC = "resultDesc";
	}

	/** 文件状态 : 删除标记(0-正常；1-覆盖；2-删除) **/
	public static enum FileStatus {
		/** 正常 **/
		NORMAL((byte) 0),

		/** 覆盖 **/
		COVERED((byte) 1),

		/** 删除 **/
		DELETED((byte) 2);

		Byte value;

		FileStatus(Byte value) {
			this.value = value;
		}

		public Byte getValue() {
			return value;
		}

		public static FileStatus getNameByValue(Byte value) {
			for (FileStatus status : FileStatus.values()) {
				if (status.value.equals(value)) {
					return status;
				}
			}

			return null;
		}

		public static Byte getValueByName(String name) {
			for (FileStatus status : FileStatus.values()) {
				if (status.name().equals(name)) {
					return status.value;
				}
			}

			return null;
		}

		public boolean valueEquals(Byte value) {
			return this.value.equals(value);
		}

		public boolean nameEquals(String name) {
			return this.name().equals(name);
		}
	}

	/** 文件操作结果码常量 **/
	public static enum FileOptResultCode {
		/** 操作成功 **/
		OPT_SUCCESS(19200),

		/** 操作失败 **/
		OPT_FAILED(19201),

		/** 参数错误 **/
		PARAM_INVALID(19202),

		/** 没有权限 **/
		PERMISSION_ERR(19203),

		/** 令牌无效 **/
		SECURE_TOKEN_INVALID(19204),

		/** 文件不存在 **/
		FILE_NO_EXIST(19205),

		/** 文件大小超过限制 **/
		FILE_SIZE_OVER_LIMIT(19206),

		/** 文件类型非法 **/
		FILE_TYPE_ILLEGAL(19207),

		/** 文件过期(仅用于子系统间内部共享文件) **/
		FILE_EXPIRED(19208),

		/** 文件服务器不可用 **/
		SERVER_NOT_AVAILABLE(19400),

		/** 文件服务器繁忙 **/
		SERVER_TOO_BUSY(19401);

		private int value;

		FileOptResultCode(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}

		public static FileOptResultCode getNameByValue(Object value) {
			for (FileOptResultCode status : FileOptResultCode.values()) {
				if (status.value == StringUtils.str2Int(value)) {
					return status;
				}
			}

			return null;
		}

		public static Integer getValueByName(String name) {
			for (FileOptResultCode status : FileOptResultCode.values()) {
				if (status.name().equals(name)) {
					return status.value;
				}
			}

			return null;
		}

		public boolean valueEquals(int value) {
			return (this.value == value);
		}

		public boolean nameEquals(String name) {
			return this.name().equals(name);
		}
	}

	/**
	 * 文件访问级别常量: <br/>
	 * <br/>
	 * (1) 根据文件类型, 将文件权限划分为：公共可读(110)、有限可读(010)、有限可读写(011)、私有可读写(000); <br/>
	 * (2) “读”：文件下载、文件信息查看; “写”：文件(覆盖)上传、文件删除; <br/>
	 * (3) 基于安全考虑, “文件权限设置”功能不归属于“写”操作范畴, 只有文件属主和系统高级管理员才可以设置文件权限操作;
	 */
	public static enum FilePermission {
		/** 公共文件：110-公共可读,即：无需申请安全令牌就可以读取该文件 **/
		PUBLIC_READ("110"),

		/** 公共文件：111-公共可读可写 **/
		// @Deprecated
		// PUBLIC_READ_WRITE("111"),

		/** 受限文件：010-有限可读, 即：需提供用户id和安全令牌才可以读文件 **/
		PROTECTED_READ("010"),

		/** 受限文件：011-有限可读写, 即：需提供用户id和安全令牌才可以读、写该文件 **/
		PROTECTED_READ_WRITE("011"),

		/** 私有文件：000-仅文件属主才可读、写该文件 **/
		PRIVATE_READ_WRITE("000");

		private String value;

		FilePermission(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}

		public static FilePermission getNameByValue(String value) {
			for (FilePermission p : FilePermission.values()) {
				if (p.value.equals(value)) {
					return p;
				}
			}

			return null;
		}

		public static String getValueByName(String name) {
			for (FilePermission p : FilePermission.values()) {
				if (p.name().equals(name)) {
					return p.getValue();
				}
			}

			return null;
		}

		public static String getValue(String nameOrValue) {
			for (FilePermission p : FilePermission.values()) {
				if (p.name().equals(nameOrValue) || p.value.equals(nameOrValue)) {
					return p.getValue();
				}
			}

			return null;
		}

		public boolean valueEquals(String value) {
			return this.value.equalsIgnoreCase(value);
		}

		public boolean nameEquals(String name) {
			return this.name().equals(name);
		}

		public static boolean check(String nameOrValue) {
			for (FilePermission t : FilePermission.values()) {
				if (t.valueEquals(nameOrValue) || t.nameEquals(nameOrValue)) {
					return true;
				}
			}

			return false;
		}
	}
}
