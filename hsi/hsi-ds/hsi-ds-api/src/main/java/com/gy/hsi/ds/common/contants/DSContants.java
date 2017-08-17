package com.gy.hsi.ds.common.contants;

public class DSContants {

	/** 调度任务执行结果状态 **/
	public static interface DSTaskStatus {
		
		/** -2- 未上报 **/
		public static final int NON_REPORT = -2;
		
		/** -1- 未执行 **/
		public static final int NON_DONE = -1;

		/** 0- 成功 **/
		public static final int SUCCESS = 0;

		/** 1- 失败 **/
		public static final int FAILED = 1;

		/** 2- 处理中 **/
		public static final int HANDLING = 2;
	}
	
	/** DS访问上下文根目录 **/
	public static final String ROOT_CONTEXT = "/hsi-ds-service";
	
	/** 调度执行日期 */
	public static final String BATCH_DATE = "BATCH_DATE";
	
	/** 系统实例标号: system.instance.no */
	public static final String SYS_INST_NO = "system.instance.no";
	
}
