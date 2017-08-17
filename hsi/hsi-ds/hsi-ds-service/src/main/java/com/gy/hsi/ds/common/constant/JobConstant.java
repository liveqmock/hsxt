/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsi.ds.common.constant;

import java.util.HashMap;
import java.util.Map;

import com.gy.hsi.ds.common.contants.DSContants.DSTaskStatus;

/**
 * 常量类
 * 
 * @Package: com.gy.hsi.ds.common.constant
 * @ClassName: JobConstant
 * @Description: none
 *
 * @author: yangyp
 * @date: 2015年10月12日 下午12:19:50
 * @version V3.0
 */
public class JobConstant {

	/** 业务参数分隔符; */
	public static final String PARA_SEPARATOR_COMMA = ";";

	/** 业务参数分隔符= */
	public static final String PARA_SEPARATOR_EQUAL = "=";

	/** SELECT分割符 */
	public static final String SELECT_SPLIT_FLAG = ",";

	/** 前置任务标识 */
	public static final int FRONT_FLAG = 0;

	/** 后置任务标识 */
	public static final int POST_FLAG = 1;

	public static final Map<String, String> TRIGGER_STATE = new HashMap<String, String>();

	static {
		TRIGGER_STATE.put("ACQUIRED", "执行中");
		TRIGGER_STATE.put("PAUSED", "暂停中");
		TRIGGER_STATE.put("WAITING", "等待中");
		TRIGGER_STATE.put("BLOCKED", "阻塞中");
		TRIGGER_STATE.put("ERROR", "错误");
		TRIGGER_STATE.put("DELETED", "被删除");
		TRIGGER_STATE.put("EXECUTING", "执行中");
		TRIGGER_STATE.put("MISFIRED", "无法启动");
		TRIGGER_STATE.put("PAUSED_BLOCKED", "暂停阻塞中");
	}

	public static final Map<Integer, String> SERVICE_STATE = new HashMap<Integer, String>();

	static {
		SERVICE_STATE.put(DSTaskStatus.SUCCESS, "成功");
		SERVICE_STATE.put(DSTaskStatus.FAILED, "失败");
		SERVICE_STATE.put(DSTaskStatus.HANDLING, "执行中");
		SERVICE_STATE.put(DSTaskStatus.NON_DONE, "未执行");
		SERVICE_STATE.put(DSTaskStatus.NON_REPORT, "未上报");
	}

	public static interface ServiceState {
		/** -2-业务未上报标识 */
		public static final int NON_REPORT = DSTaskStatus.NON_REPORT;

		/** -1-业务未执行标识 */
		public static final int NON_DONE = DSTaskStatus.NON_DONE;

		/** 0-业务执行成功标识 */
		public static final int SUCCESS = DSTaskStatus.SUCCESS;

		/** 1-业务执行失败标识 */
		public static final int FAILED = DSTaskStatus.FAILED;

		/** 2-业务执行中标识 */
		public static final int HANDING = DSTaskStatus.HANDLING;
	}
	
	public static interface ExecuteMethod {
		/** 自动执行 */
		public static final int AUTO = 0;

		/** 手工执行 */
		public static final int MANUAL = 1;
	}

	public static interface TaskFlag {

		/** 业务服务任务存在标识 */
		public static final int NOT_TASK_FLAG = 1;

		/** 业务服务任务存在标识 */
		public static final int HAS_TASK_FLAG = 0;

		/** 无后置业务标识 */
		public static final int NOT_POST_TASK_FLAG = -1;
	}

	public static interface TempArgsKey {

		/** 是否手工触发 */
		public static final String KEY_IS_MANUAL = "IS_MANUAL";

		/** 是否忽略前置业务 */
		public static final String KEY_IGNORE_FRONT = "IGNORE_FRONT";

		/** 是否触发后置业务 */
		public static final String KEY_RECUR_POST = "RECUR_POST";
		
		/** 执行日期 */
		public static final String KEY_DS_BATCH_DATE = "BATCH_DATE";
	}

	public static interface TempArgsIndex {

		/** 是否手工触发 */
		public static final int INDEX_IS_MANUAL = 0;

		/** 是否忽略前置业务 */
		public static final int INDEX_IGNORE_FRONT = 1;

		/** 是否触发后置业务 */
		public static final int INDEX_RECUR_POST = 2;
	}

	public static interface StrValue {
		
		/** 值: 1 */
		public static final String TRUE = "1";

		/** 值： 0 */
		public static final String FALSE = "0";
	}

}
