/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.uf.common.constant;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-uf-service
 * 
 *  Package Name    : com.gy.hsxt.uf.common.constant
 * 
 *  File Name       : Constant.java
 * 
 *  Creation Date   : 2015年9月24日
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

	/** socket 连接默认超时时间： 60秒 **/
	public static final int DEFAULT_SOCKET_CONNTIMEOUT = 60 * 1000;

	/** socket 连接最大超时时间： 120秒 **/
	public static final int MAX_SOCKET_CONNTIMEOUT = 120 * 1000;

	/** netty 默认端口 **/
	public static final int DEFAULT_SERVER_PORT = 8088;

	/** 同步消息响应报文的报文ID的特征结尾字符 **/
	public static final String SYNC_RESP_SUFFIX = "-RESP";

	/** 总平台id：000 **/
	public static final String GLOBAL_PLATFORM_ID = "000";

	/** UF回声 **/
	public static final String UF_ECHO_TEST = "UF$ECHO_PING_PONG_TEST!#%@!#UF";

	/** 超时时间key **/
	public static final String KEY_SOCKET_CONNTIMEOUT = "SOCKET_CONN_TIMEOUT";

	/** 报文操作类型 **/
	public static final class OptType {
		/** 10-外发 **/
		public static final int SENDING = 10;

		/** 20-接收 **/
		public static final int RECEIVING = 20;
	}

	/** 运行状态枚举定义 **/
	public static final class OptStatus {
		/** 100-异常 **/
		public static final int ABNORMAL = 100;

		/** 200-正常 **/
		public static final int NORMAL = 200;
	}

	/** 是否写入来往报文的流水日志表示枚举值 **/
	public static final class UfLogFlag {
		/** 1-写 **/
		public static final String TRUE = "1";

		/** 0-不写 **/
		public static final String FALSE = "0";
	}
}
