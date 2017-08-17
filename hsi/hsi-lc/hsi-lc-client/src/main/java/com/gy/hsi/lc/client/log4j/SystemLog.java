package com.gy.hsi.lc.client.log4j;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import com.alibaba.fastjson.JSON;
import com.gy.hsi.ds.param.HsPropertiesConfigurer;
import com.gy.hsxt.kafka.common.bean.SysLogInfo;
import com.gy.hsxt.kafka.common.constant.ConfigConstant;
import com.gy.hsxt.kafka.common.constant.LogConstant;
import com.gy.hsxt.kafka.common.enums.LevelSwitchEnum;
import com.gy.hsxt.kafka.util.DateUtil;
import com.gy.hsxt.kafka.util.StringUtils;

public class SystemLog {
	private SystemLog() {
	}

	private static long order = 0;
	private static Producer<String, String> sysProducer ;

	public static Producer<String, String> buildProducer() {
		KafkaProducer<String, String> producer = null;
		try {
			String hostaddr = HsPropertiesConfigurer
					.getProperty(ConfigConstant.KAFKA_HOSTADDR);
			if(!hostaddr.contains(":")){
				String port = HsPropertiesConfigurer
						.getProperty(ConfigConstant.KAFKA_PORT);
				hostaddr = hostaddr + ":" + port;
			}
			Properties props = new Properties();
			props.put("bootstrap.servers", hostaddr);
			props.put("acks", "all");
			props.put("retries", 0);
			props.put("batch.size", 16384);
			props.put("linger.ms", 1);
			props.put("buffer.memory", 33554432);
			props.put("key.serializer",
					"org.apache.kafka.common.serialization.StringSerializer");
			props.put("value.serializer",
					"org.apache.kafka.common.serialization.StringSerializer");
			producer = new KafkaProducer<String, String>(props);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return producer;
	}

	/**
	 * 重写debug日志实现
	 * 
	 * @param moudleName
	 *            模块名称
	 * @param funName
	 *            方法名称
	 * @param msg
	 *            描述信息
	 */
	public static final void debug(String moudleName, String funName, String msg) {
		noMonLevelPrint(moudleName, funName, msg, LogConstant.LOG_LEVEL_DEBUG,
				null);
	}

	/**
	 * 重写info日志实现
	 * 
	 * @param moudleName
	 *            模块名称
	 * @param funName
	 *            方法名称
	 * @param msg
	 *            描述信息
	 */
	public static final void info(String moudleName, String funName, String msg) {
		noMonLevelPrint(moudleName, funName, msg, LogConstant.LOG_LEVEL_INFO,
				null);
	}

	/**
	 * 重写info日志实现
	 * 
	 * @param moudleName
	 *            模块名称
	 * @param funName
	 *            方法名称
	 * @param msg
	 *            描述信息
	 */
	public static final void info(String moudleName, String funName,
			String msg, Exception e) {
		noMonLevelPrint(moudleName, funName, msg, LogConstant.LOG_LEVEL_INFO, e);
	}

	/**
	 * 重写warn日志实现
	 * 
	 * @param moudleName
	 *            模块名称
	 * @param funName
	 *            方法名称
	 * @param msg
	 *            描述信息
	 */
	public static final void warn(String moudleName, String funName,
			String msg, Exception e) {
		noMonLevelPrint(moudleName, funName, msg, LogConstant.LOG_LEVEL_WARN, e);
	}

	/**
	 * 重写warn日志实现
	 * 
	 * @param moudleName
	 *            模块名称
	 * @param funName
	 *            方法名称
	 * @param msg
	 *            描述信息
	 */
	public static final void warn(String moudleName, String funName, String msg) {
		noMonLevelPrint(moudleName, funName, msg, LogConstant.LOG_LEVEL_WARN,
				null);
	}

	/**
	 * 重写fatal日志实现
	 * 
	 * @param moudleName
	 *            模块名称
	 * @param funName
	 *            方法名称
	 * @param msg
	 *            描述信息
	 */
	public static final void fatal(String moudleName, String funName, String msg) {
		noMonLevelPrint(moudleName, funName, msg, LogConstant.LOG_LEVEL_FATAL,
				null);
	}

	/**
	 * 重写fatal日志实现
	 * 
	 * @param moudleName
	 *            模块名称
	 * @param funName
	 *            方法名称
	 * @param msg
	 *            描述信息
	 */
	public static final void fatal(String moudleName, String funName,
			String msg, Exception e) {
		noMonLevelPrint(moudleName, funName, msg, LogConstant.LOG_LEVEL_FATAL,
				e);
	}

	/**
	 * 重写info告警日志实现
	 * 
	 * @param monitorLevel
	 *            告警级别（1：低级 2：中级 3:高级 ）
	 * @param moudleName
	 *            模块名称
	 * @param funName
	 *            方法名称
	 * @param msg
	 *            描述信息
	 */
	public static final void info(String monitorLevel, String moudleName,
			String funName, String msg) {
		monLevelPrint(moudleName, funName, msg, LogConstant.LOG_LEVEL_INFO,
				monitorLevel, null);
	}

	/**
	 * 重写info告警日志实现
	 * 
	 * @param monitorLevel
	 *            告警级别（1：低级 2：中级 3:高级 ）
	 * @param moudleName
	 *            模块名称
	 * @param funName
	 *            方法名称
	 * @param msg
	 *            描述信息
	 */
	public static final void info(String monitorLevel, String moudleName,
			String funName, String msg, Exception e) {
		monLevelPrint(moudleName, funName, msg, LogConstant.LOG_LEVEL_INFO,
				monitorLevel, e);
	}

	/**
	 * 重写warn告警日志实现
	 * 
	 * @param monitorLevel
	 *            monitorLevel 告警级别（1：低级 2：中级 3:高级 ）
	 * @param moudleName
	 *            模块名称
	 * @param funName
	 *            方法名称
	 * @param msg
	 *            描述信息
	 */
	public static final void warn(String monitorLevel, String moudleName,
			String funName, String msg) {
		monLevelPrint(moudleName, funName, msg, LogConstant.LOG_LEVEL_WARN,
				monitorLevel, null);
	}

	/**
	 * 重写warn告警日志实现
	 * 
	 * @param monitorLevel
	 *            monitorLevel 告警级别（1：低级 2：中级 3:高级 ）
	 * @param moudleName
	 *            模块名称
	 * @param funName
	 *            方法名称
	 * @param msg
	 *            描述信息
	 */
	public static final void warn(String monitorLevel, String moudleName,
			String funName, String msg, Exception e) {
		monLevelPrint(moudleName, funName, msg, LogConstant.LOG_LEVEL_WARN,
				monitorLevel, e);
	}

	/**
	 * 重写fatal告警日志实现
	 * 
	 * @param monitorLevel
	 *            monitorLevel 告警级别（1：低级 2：中级 3:高级 ）
	 * @param moudleName
	 *            模块名称
	 * @param funName
	 *            方法名称
	 * @param msg
	 *            描述信息
	 */
	public static final void fatal(String monitorLevel, String moudleName,
			String funName, String msg) {
		monLevelPrint(moudleName, funName, msg, LogConstant.LOG_LEVEL_FATAL,
				monitorLevel, null);
	}

	/**
	 * 重写fatal告警日志实现
	 * 
	 * @param monitorLevel
	 *            monitorLevel 告警级别（1：低级 2：中级 3:高级 ）
	 * @param moudleName
	 *            模块名称
	 * @param funName
	 *            方法名称
	 * @param msg
	 *            描述信息
	 */
	public static final void fatal(String monitorLevel, String moudleName,
			String funName, String msg, Exception e) {
		monLevelPrint(moudleName, funName, msg, LogConstant.LOG_LEVEL_FATAL,
				monitorLevel, e);
	}

	/**
	 * 重写error告警日志实现
	 * 
	 * @param moudleName
	 *            模块名称
	 * @param funName
	 *            方法名称
	 * @param msg
	 *            描述信息
	 * @param e
	 *            异常类
	 */
	public static final void error(String moudleName, String funName,
			String msg, Exception e) {
		noMonLevelErrorPrint(moudleName, funName, msg, e,
				LogConstant.LOG_LEVEL_ERROR);
	}

	/**
	 * 重写error告警日志实现
	 * 
	 * @param monitorLevel
	 *            monitorLevel 告警级别（1：低级 2：中级 3:高级 ）
	 * @param moudleName
	 *            模块名称
	 * @param funName
	 *            方法名称
	 * @param msg
	 *            描述信息
	 * @param e
	 *            异常类
	 */
	public static final void error(String monitorLevel, String moudleName,
			String funName, String msg, Exception e) {
		monLevelErrorPrint(moudleName, funName, msg, e,
				LogConstant.LOG_LEVEL_ERROR, monitorLevel);
	}

	/**
	 * 没有警告级别的日志打印
	 * 
	 * @param moudleName
	 * @param funName
	 * @param msg
	 * @param level
	 */
	private static void noMonLevelPrint(String moudleName, String funName,
			String msg, String level, Exception e) {
		SysLogInfo info = initSysLogInfo(level, msg, moudleName, funName, "",
				getStringError(e));
		send(info);
	}

	public static void send(SysLogInfo info) {
		String sysSwitch = HsPropertiesConfigurer
				.getProperty(ConfigConstant.SYSSWITCH);//本地日志开关
		String isPush = HsPropertiesConfigurer
				.getProperty(ConfigConstant.PUSH_SYSLOG2CENTER);//日志是否推送到服务器的开关
		String isKafka = HsPropertiesConfigurer
				.getProperty(ConfigConstant.ISKAFKA);//日志推送到kafka服务器还是rabbitMQ的开关
		// 本地打印
		if ("1".equals(sysSwitch) && isLocalWrite(info.getLevel())) {
			String logContent = getLogContent(info);
			System.out.println(logContent);
		}
		try {
			if ("1".equals(isPush)) {
				if ("1".equals(isKafka)) {//推送到kafka服务器
					if(null == sysProducer){
						sysProducer = buildProducer();
					}
					sysProducer.send(buildSysMessage(info));
				} else {// 推送到rabbitMQ服务器
					SendLog2Q.sendBizLog2Q(info);
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
	}

	private static boolean isLocalWrite(String logLevel) {
		String level = StringUtils.nullToEmpty(HsPropertiesConfigurer
				.getProperty(ConfigConstant.FILTER_LEVEL));
		return LevelSwitchEnum.getType(level) <= LevelSwitchEnum
				.getType(logLevel);
	}

	private static ProducerRecord<String, String> buildSysMessage(
			SysLogInfo sysLogInfo) {
		ProducerRecord<String, String> msg = null;
		try {
			msg = new ProducerRecord<String, String>(sysLogInfo.getSystemName()
					+ "SYS", String.valueOf(sysLogInfo.getOrder()),
					JSON.toJSONString(sysLogInfo));
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return msg;
	}

	/**
	 * 有警告级别的日志打印
	 * 
	 * @param moudleName
	 * @param funName
	 * @param msg
	 * @param level
	 * @param monitorLevel
	 */
	private static void monLevelPrint(String moudleName, String funName,
			String msg, String level, String monitorLevel, Exception e) {
		SysLogInfo info = initSysLogInfo(level, msg, moudleName, funName,
				monitorLevel, getStringError(e));
		send(info);
	}

	private static void noMonLevelErrorPrint(String moudleName, String funName,
			String msg, Exception e, String level) {
		SysLogInfo info = initSysLogInfo(level, msg, moudleName, funName, "",
				getStringError(e));
		send(info);
	}

	private static String getStringError(Exception e) {
		String expMessage = "";
		if (null != e) {
			ByteArrayOutputStream buf = new java.io.ByteArrayOutputStream();
			e.printStackTrace(new java.io.PrintWriter(buf, true));
			expMessage = buf.toString();
			try {
				buf.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		return expMessage;
	}

	private static void monLevelErrorPrint(String moudleName, String funName,
			String msg, Exception e, String level, String monitorLevel) {
		SysLogInfo info = initSysLogInfo(level, msg, moudleName, funName,
				monitorLevel, getStringError(e));
		send(info);
	}

	/**
	 * 
	 * @Title: initSysLogInfo
	 * @Description: 初始化系统日志信息
	 * @param @param Level 日志级别
	 * @param @param msg 日志内容
	 * @param @param moudleName 模块名
	 * @param @param funName 功能名
	 * @param @return 设定文件
	 * @return SysLogInfo 返回类型
	 * @throws
	 */
	private static SysLogInfo initSysLogInfo(String level, String msg,
			String moudleName, String funName, String monitorLevel,
			String exceptionMessage) {
		order++;
		SysLogInfo info = new SysLogInfo();
		InetAddress netAddress = null;
		try {
			netAddress = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		String hostName = "";
		String hostAddress = "";
		if (null != netAddress) {
			hostName = StringUtils.nullToEmpty(netAddress.getHostName());
			hostAddress = StringUtils.nullToEmpty(netAddress.getHostAddress());
		}
		info.setHostName(hostName);
		info.setIpAddress(hostAddress);
		info.setPlatformName(StringUtils.nullToEmpty(HsPropertiesConfigurer
				.getProperty(ConfigConstant.SYS_PLATFORM_NAME)));
		info.setSystemName(StringUtils.nullToEmpty(HsPropertiesConfigurer
				.getProperty(ConfigConstant.SYS_SYSTEM_NAME)));
		info.setSystemInstanceName(StringUtils
				.nullToEmpty(HsPropertiesConfigurer
						.getProperty(ConfigConstant.SYS_SYSTEM_INSTANCE_NAME)));
		info.setMoudleName(StringUtils.nullToEmpty(moudleName));
		info.setFunName(StringUtils.nullToEmpty(funName));
		info.setLevel(StringUtils.nullToEmpty(level));
		info.setLogContent(StringUtils.nullToEmpty(msg));
		info.setTimeStamp(DateUtil.getCurrentDate2String());
		info.setMonitorLevel(StringUtils.nullToEmpty(monitorLevel));
		info.setExceptionMessage(StringUtils.nullToEmpty(exceptionMessage));
		info.setOrder(order);
		info.setEmail(HsPropertiesConfigurer.getProperty(ConfigConstant.EMAIL));
		info.setMobile(HsPropertiesConfigurer
				.getProperty(ConfigConstant.MOBILE));
		return info;
	}

	/**
	 * 
	 * @param sysLogInfo
	 * @return
	 */
	private static String getLogContent(SysLogInfo info) {
		StringBuilder logContent = new StringBuilder();
		logContent
				.append(StringUtils.nullToEmpty(DateUtil
						.getCurrentDate2String()))
				.append(ConfigConstant.VERTICAL)
				.append(StringUtils.nullToEmpty(info.getLevel()))
				.append(ConfigConstant.VERTICAL)
				.append(StringUtils.nullToEmpty(info.getHostName()))
				.append(ConfigConstant.VERTICAL)
				.append(StringUtils.nullToEmpty(info.getIpAddress()))
				.append(ConfigConstant.VERTICAL)
				.append(StringUtils.nullToEmpty(info.getMoudleName()))
				.append(ConfigConstant.VERTICAL)
				.append(StringUtils.nullToEmpty(info.getFunName()))
				.append(ConfigConstant.VERTICAL)
				.append(StringUtils.nullToEmpty(info.getLogContent()))
				.append(ConfigConstant.VERTICAL)
				.append(StringUtils.nullToEmpty(info.getMonitorLevel()))
				.append(ConfigConstant.VERTICAL)
				.append(StringUtils.nullToEmpty(info.getExceptionMessage()))
				.append(ConfigConstant.CR);
		return logContent.toString();
	}
}
