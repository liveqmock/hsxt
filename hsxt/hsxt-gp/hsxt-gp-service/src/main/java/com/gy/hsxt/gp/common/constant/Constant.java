/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.gp.common.constant;

import com.gy.hsi.ds.common.contants.DSContants;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-gp-service
 * 
 *  Package Name    : com.gy.hsxt.gp.common.constant
 * 
 *  File Name       : Constant.java
 * 
 *  Creation Date   : 2015-11-12
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 常量类
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class Constant {

	/** zk同步监听路径 **/
	public static final String ZK_ROOT_NODE = "/HSXT-GP";

	/** zk同步监听路径 **/
	public static final String ZK_SUB_NODE = "/SUB";
	
	/** 文件换行符 */
	public static final String LINE_SEPARATOR = System.getProperty("line.separator");
	
	/** 文件路径分隔符 */
	public static final String FILE_SEPARATOR = System.getProperty("file.separator");
	
	/** 调度执行日期 */
	public static final String BATCH_DATE = DSContants.BATCH_DATE;
	
	/** 对账文件日期 */
	public static final String ACC_FILE_DATE = "ACC_FILE_DATE";
	
	/** UTF-8 */
	public static final String ENCODE_UTF8 = "UTF-8";

	/** 重试结果状态 0 - 成功 1 - 失败 2 - 放弃 */
	public static interface RetryStatus {
		/** 0 - 成功 **/
		public static final int SUCCESS = 0;

		/** 1 - 失败 **/
		public static final int FAIL = 1;

		/** 2 - 放弃 **/
		public static final int ABANDON = 2;
	}

	/** 文件状态 */
	public static interface FileStatus {

		/** 10 - 文件待创建 */
		public static final int READY = 10;

		/** 11 - 文件正在创建 */
		public static final int CREATING = 11;

		/** 20 - 文件创建成功 */
		public static final int SUCCESS = 20;

		/** 21 - 文件创建失败 */
		public static final int FAILED = 21;
	}

	/** 行内跨行标志 1：行内转账，0：跨行转账 */
	public enum UnionFlag {
		// 行内转账
		SAME_BANK(1),
		// 跨行转账
		OTHER_BANK(0);

		private int value;

		public int getValue() {
			return this.value;
		}

		private UnionFlag(int value) {
			this.value = value;
		}
	}

	/** 同城/异地标志“1”—同城 “2”—异地 */
	public enum AddrFlag {
		// 同城
		SAME_CITY(1),
		// 异地
		OTHER_CITY(2);

		private int value;

		public int getValue() {
			return this.value;
		}

		private AddrFlag(int value) {
			this.value = value;
		}
	}

	/** 转账加急标志 Y：加急 N：不加急 S：特急（汇总扣款模式不支持） */
	public enum BSysFlag {
		NO("N"), YES("Y"), SPECITAL("S");

		private String value;

		public String getValue() {
			return this.value;
		}

		private BSysFlag(String value) {
			this.value = value;
		}

		public boolean valueEquals(String value) {
			return this.getValue().equals(value);
		}
	}
	
	/** 对账类型枚举值定义 **/
	public static interface GPCheckType {
		/** 100:支付对账 **/
		public static final int PAYMENT = 100;
		
		/** 200:提现对账 **/
		public static final int TRANS_CASH = 200;
	}
	
	/** 序列号类型: 30-快捷签约  */
	public static interface SeqType {
		/** 30 - 快捷签约 **/
		public static final int UPOP_BINDING = 30;
	}

}
