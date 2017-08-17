package com.gy.hsxt.rabbitmq.center.syslog.task;



import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gy.hsxt.rabbitmq.center.syslog.util.SysQueueManager;
import com.gy.hsxt.rabbitmq.common.bean.SysLogInfo;
import com.gy.hsxt.rabbitmq.common.config.PersistentPolicyConfigurer;
import com.gy.hsxt.rabbitmq.common.constant.ConfigConstant;
import com.gy.hsxt.rabbitmq.common.enums.LevelSwitchEnum;
import com.gy.hsxt.rabbitmq.common.persistentlog.impl.HSPolicy;
import com.gy.hsxt.rabbitmq.common.persistentlog.impl.HSWriter;
import com.gy.hsxt.rabbitmq.common.util.LogCenterTools;
import com.gy.hsxt.rabbitmq.common.util.StringUtils;

/**
 * 
 * @ClassName: WriteSysLogTask
 * @Description: 系统日志内容缓存类（持久化之前）
 * @author tianxh
 * @date 2015-8-6 下午2:43:46
 * 
 */
public class WriteSysLogTask implements Runnable {
	private static final Logger log = LoggerFactory
			.getLogger(WriteSysLogTask.class);

	private String queueName;// 系统日志输出路径（缓存队列里对应的key）
	private String filterLevel;// 全局日志过滤级别

	public WriteSysLogTask() {
	};

	public WriteSysLogTask(String queueName) {
		this.queueName = queueName;
		filterLevel = getFilterSwitch();
	}

	/**
	 * <p>
	 * Title: run
	 * </p>
	 * <p>
	 * Description: 写文件之前缓冲系统消息内容
	 * </p>
	 * 
	 */
	@Override
	public void run() {
		SysQueueManager manager = SysQueueManager.getInstance();
		SysLogInfo sysLogInfo = null;// 系统日志对象
		Map<Long, SysLogInfo> sysLogMap = new TreeMap<Long, SysLogInfo>();
		try {
			while (null != (sysLogInfo = manager.poll(queueName))) {
				sysLogMap.put(sysLogInfo.getOrder(), sysLogInfo);
			}
			toWriting(sysLogMap);
		} catch (InterruptedException e) {
			log.error(ConfigConstant.MOUDLENAME, "[WriteSysLogTask]",
					ConfigConstant.FUNNAME, "[run],code:", e.getCause(),
					"message:", e.getMessage());
		}
	}

	

	private String getFilterSwitch() {// 获取过滤日志级别
		String fileterLevel = "debug";
		try {
			PersistentPolicyConfigurer persistentPolicyConfigurer = PersistentPolicyConfigurer
					.getSingleton();// 获取全局参数实例
			Map<String, String> configMap = persistentPolicyConfigurer
					.getConfigMap();// 获取全局配置参数
			if (null == configMap || configMap.size() == 0) {
				log.error(ConfigConstant.MOUDLENAME, "[LogCenterTools]",
						ConfigConstant.FUNNAME,
						"[getFileParamsMap],请检查路径：config/logcenter/logCenter.properties是否存在，参数是否配置 ");
			}

			fileterLevel = StringUtils
					.nullToEmpty(configMap.get("filterLevel"));
		} catch (Exception e) {
			log.error(ConfigConstant.MOUDLENAME, "[HSPolicy]",
					ConfigConstant.FUNNAME, "[noActiveFileHandlePolicy],code:",
					e.getCause(), ",message:", e.getMessage());
		}
		return fileterLevel;
	}
	
	private void toWriting(Map<Long, SysLogInfo> treeMap) {
		if (null == treeMap) {
			return;
		}
		int count = 0;// 缓存系统日志对象个数
		StringBuffer logContent = new StringBuffer();// 系统日志内容
		HSPolicy policy = null;// 写文件策略执行类
		HSWriter writer = null;// 写文件到硬盘的执行类
		for (long key : treeMap.keySet()) {
			SysLogInfo sysLogInfo = treeMap.get(key);
			if (null == sysLogInfo) {
				continue;
			}
			if (!StringUtils.isBlank(sysLogInfo.getMonitorLevel())) {// 将告警日志重发到告警队列里
				// SystemLog.monitor(sysLogInfo);
				continue;
			}
			if (LevelSwitchEnum.getType(sysLogInfo.getLevel()) < LevelSwitchEnum
					.getType(filterLevel)) {
				continue;
			}
			String activeFilePath = LogCenterTools
					.getSystemActiveFilePath(sysLogInfo);// 获取当前正在写的文件的路径
			String prefixFileName = LogCenterTools
					.getSystemPrefixFileName(sysLogInfo);// 获取当前正在写的文件名的前缀部分（例如：平台名-系统名-业务名_模块名_功能名_）
			Map<String, String> map = new HashMap<String, String>();
			map.put("activeFilePath", activeFilePath);
			map.put("targetFilePath", activeFilePath);
			map.put("prefixFileName", prefixFileName);
			policy = new HSPolicy(map);
			writer = new HSWriter(policy, map);
			count++;
			logContent
					.append(sysLogInfo.getPlatformName())
					.append(ConfigConstant.VERTICAL)
					.append(sysLogInfo.getSystemName())
					.append(ConfigConstant.VERTICAL)
					.append(StringUtils.nullToEmpty(sysLogInfo
							.getSystemInstanceName()))
					.append(ConfigConstant.VERTICAL)
					.append(StringUtils.nullToEmpty(sysLogInfo.getTimeStamp()))
					.append(ConfigConstant.VERTICAL)
					.append(StringUtils.nullToEmpty(sysLogInfo.getLevel()))
					.append(ConfigConstant.VERTICAL)
					.append(StringUtils.nullToEmpty(sysLogInfo.getHostName()))
					.append(ConfigConstant.VERTICAL)
					.append(StringUtils.nullToEmpty(sysLogInfo.getIpAddress()))
					.append(ConfigConstant.VERTICAL)
					.append(StringUtils.nullToEmpty(sysLogInfo.getMoudleName()))
					.append(ConfigConstant.VERTICAL)
					.append(StringUtils.nullToEmpty(sysLogInfo.getFunName()))
					.append(ConfigConstant.VERTICAL)
					.append(StringUtils.nullToEmpty(sysLogInfo.getLogContent()))
					.append(ConfigConstant.VERTICAL)
					.append(StringUtils.nullToEmpty(sysLogInfo
							.getMonitorLevel()))
					.append(ConfigConstant.VERTICAL)
					.append(StringUtils.nullToEmpty(sysLogInfo
							.getExceptionMessage()))

					.append(ConfigConstant.CR);
			if (count % ConfigConstant.FIXEDLENGTH == 0) {// 每
															// LogConstant.FIXEDLENGTH
															// 笔写一次。
				writer.write(logContent.toString());
				count = 0;
				logContent.setLength(0);
			}
		}
		if (0 != count && logContent.length() > 0) {// 最后余下的日志内容写完
			writer.write(logContent.toString());
		}

	}
}
