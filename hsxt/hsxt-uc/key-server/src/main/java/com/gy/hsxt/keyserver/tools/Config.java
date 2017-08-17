package com.gy.hsxt.keyserver.tools;

/***************************************************************************
 * <PRE>
 *  Project Name    : KeyServer
 * 
 *  Package Name    : com.gy.kms.keyserver
 * 
 *  File Name       : Config.java
 * 
 *  Creation Date   : 2014-7-4
 * 
 *  Author          : fandi
 * 
 *  Purpose         : 存放配置文件中的参数
 * 
 * </PRE>
 ***************************************************************************/
public  class Config {
	/**
	 * 中心代码	 
	 */
		static String SubCenter;
	/**
	 * POS签到有效期
	 */
		static private long DueTime;
	/**
	 * 存储方式	
	 */
		static int saveDataType;
	/**
	 * 日志级别
	 */
		static int logLevel;
		
		public static int getLogLevel() {
			return logLevel;
		}

		public static void setLogLevel(int logLevel) {
			Config.logLevel = logLevel;
		}

		public static int getSaveDataType() {
			return saveDataType;
		}

		public static void setSaveDataType(int saveDataType) {
			Config.saveDataType = saveDataType;
		}

		public static long getDueTime() {
			return DueTime;
		}

		public static void setDueTime(long dueTime) {
			DueTime = dueTime;
		}

		static public String getSubCenter() {
			return SubCenter;
		}

		static public void setSubCenter(String subCenter) {
			SubCenter = subCenter;
		}
}
