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
 *  File Name       : ConfigConst.java
 * 
 *  Creation Date   : 2015-9-25
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 属性文件中配置的key值常量
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class ConfigConst {

	/** 系统id **/
	public static final String SYSTEM_ID = "system.id";

	/** 系统实例编号 **/
	public static final String SYSTEM_INSTANCE_NO = "system.instance.no";

	/** 当前地区平台id **/
	public static final String SYSTEM_PLATFORM_ID = "system.platform.id";

	/** 是否写入来往报文日志[0:不写, 1:写] **/
	public static final String SYSTEM_PLATFORM_LOGFLAG = "system.platform.logflag";

	/** 密钥文件根目录 **/
	public static final String SYSTEM_SECURITY_KEY_DIR = "system.security.key.directory";
	
	/** 报文日志记录线程池大小 **/
	public static final String SYSTEM_LOGTASK_MAXPOOLSIZE = "system.logtask.maxpoolsize";

	/** netty管理者线程池大小 **/
	public static final String SERVER_BOSS_THREADPOOL_SIZE = "server.boss.threadpool.size";

	/** netty工作者线程池大小 **/
	public static final String SERVER_WORKER_THREADPOOL_SIZE = "server.woker.threadpool.size";

	/** netty socket连接超时时长 **/
	public static final String SERVER_SOCKET_CONNTIMEOUT_MILLIS = "server.socket.conntimeout.millis";

	/** 安全密钥 **/
	public static final class SecurityKey {
		/** AES密钥存放文件 */
		public static final String AES_KEY_FILE = "uf_aes_encrypt.key";

		/** RSA指定公钥存放文件 */
		public static final String RSA_PUBLIC_KEY_FILE = "uf_rsa_public.key";

		/** RSA指定私钥存放文件 */
		public static final String RSA_PRIVATE_KEY_FILE = "uf_rsa_private.key";
	}

	/** 启动classpath设置 **/
	public static final class StartClassPath {
		/** user.dir */
		public static final String USER_DIR = "user.dir";

		/** log.home */
		public static final String LOG_HOME = "log.home";
		
		/** 本服务器ip配置 **/
		public static final String SERVER_IP = "server.ip";

		/** 本服务器端口配置 **/
		public static final String SERVER_PORT = "server.port";
	}

}
