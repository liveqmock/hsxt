package com.gy.hsi.lc.client.log4j;

import com.gy.hsi.ds.param.HsPropertiesConfigurer;
import com.gy.hsxt.kafka.common.constant.ConfigConstant;
import com.gy.hsxt.kafka.common.exceptions.SendRefuseException;
import com.gy.hsxt.kafka.common.rabbitmq.RabbitMQSender;
import com.gy.hsxt.kafka.util.StringUtils;
public class SendLog2Q {
	static String bizName = HsPropertiesConfigurer
			.getProperty(ConfigConstant.BIZ_PLATFORM_NAME);
	static String bizNodeName = HsPropertiesConfigurer
			.getProperty(ConfigConstant.BIZ_SYSTEM_NAME);
	static String bizInstanceName = HsPropertiesConfigurer
			.getProperty(ConfigConstant.BIZ_SYSTEM_INSTANCE_NAME);
	static String host = HsPropertiesConfigurer
			.getProperty(ConfigConstant.RABBITMQ_HOSTADDR);
	static String sysName = HsPropertiesConfigurer
			.getProperty(ConfigConstant.SYS_PLATFORM_NAME);
	static String sysNodeName = HsPropertiesConfigurer
			.getProperty(ConfigConstant.SYS_SYSTEM_NAME);
	static String sysInstanceName = HsPropertiesConfigurer
			.getProperty(ConfigConstant.SYS_SYSTEM_INSTANCE_NAME);
	static String sysQueue = getName(sysName, sysNodeName, sysInstanceName,
			"_", "syslog_queue", false);
	static String sysExchange = getName(sysName, sysNodeName, sysInstanceName,
			"_", "syslog_exchange", false);
	static String bizQueue = getName(bizName, bizNodeName, bizInstanceName,
			"_", "bizlog_queue", true);
	static String bizExchange = getName(bizName, bizNodeName, bizInstanceName,
			"_", "bizlog_exchange", true);

	private static RabbitMQSender syslogSender = new RabbitMQSender(host,
			sysQueue, sysExchange);
	private static RabbitMQSender bizlogSender = new RabbitMQSender(host,
			bizQueue, bizExchange);
	private static RabbitMQSender monitorLogSender = new RabbitMQSender(host,
			ConfigConstant.MONITORLOG_QUEUE, ConfigConstant.MONITORLOG_EXCHANGE);

	/**
	 * 
	 * @Title: sendSysLog2Q
	 * @Description: 发送系统日志到日志中心队列
	 * @param @param msg 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	public static void sendSysLog2Q(String msg) {
		try {
			if (syslogSender == null) {
				host = HsPropertiesConfigurer
						.getProperty(ConfigConstant.RABBITMQ_HOSTADDR);
				sysName = HsPropertiesConfigurer
						.getProperty(ConfigConstant.SYS_PLATFORM_NAME);
				sysNodeName = HsPropertiesConfigurer
						.getProperty(ConfigConstant.SYS_SYSTEM_NAME);
				sysInstanceName = HsPropertiesConfigurer
						.getProperty(ConfigConstant.SYS_SYSTEM_INSTANCE_NAME);
				sysQueue = getName(sysName, sysNodeName, sysInstanceName, "_",
						"syslog_queue", false);
				sysExchange = getName(sysName, sysNodeName, sysInstanceName,
						"_", "syslog_exchange", false);
				syslogSender = new RabbitMQSender(host, sysQueue, sysExchange);
			}
			syslogSender.sendString(msg);
		} catch (SendRefuseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @Title: sendSysLog2Q
	 * @Description: 发送系统日志到日志中心队列
	 * @param @param msg 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	public static void sendSysLog2Q(Object obj) {
		try {
			if (syslogSender == null) {
				host = HsPropertiesConfigurer
						.getProperty(ConfigConstant.RABBITMQ_HOSTADDR);
				sysName = HsPropertiesConfigurer
						.getProperty(ConfigConstant.SYS_PLATFORM_NAME);
				sysNodeName = HsPropertiesConfigurer
						.getProperty(ConfigConstant.SYS_SYSTEM_NAME);
				sysInstanceName = HsPropertiesConfigurer
						.getProperty(ConfigConstant.SYS_SYSTEM_INSTANCE_NAME);
				sysQueue = getName(sysName, sysNodeName, sysInstanceName, "_",
						"syslog_queue", false);
				sysExchange = getName(sysName, sysNodeName, sysInstanceName,
						"_", "syslog_exchange", false);
				syslogSender = new RabbitMQSender(host, sysQueue, sysExchange);
			}
			syslogSender.sendObject(obj);
		} catch (SendRefuseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @Title: sendBizLog2Q
	 * @Description: 发送业务日志到日志中心mq队列
	 * @param @param msg 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	public static void sendBizLog2Q(String msg) {
		try {
			if (bizlogSender == null) {
				String queueName = getName(
						HsPropertiesConfigurer
								.getProperty(ConfigConstant.BIZ_PLATFORM_NAME),
						HsPropertiesConfigurer
								.getProperty(ConfigConstant.BIZ_SYSTEM_NAME),
						HsPropertiesConfigurer
								.getProperty(ConfigConstant.BIZ_SYSTEM_INSTANCE_NAME),
						"_", "bizlog_queue", true);
				String exchangeName = getName(
						HsPropertiesConfigurer
								.getProperty(ConfigConstant.BIZ_PLATFORM_NAME),
						HsPropertiesConfigurer
								.getProperty(ConfigConstant.BIZ_SYSTEM_NAME),
						HsPropertiesConfigurer
								.getProperty(ConfigConstant.BIZ_SYSTEM_INSTANCE_NAME),
						"_", "bizlog_exchange", true);
				String host = HsPropertiesConfigurer
						.getProperty(ConfigConstant.RABBITMQ_HOSTADDR);
				bizlogSender = new RabbitMQSender(host, queueName, exchangeName);
			}

			bizlogSender.sendString(msg);

		} catch (SendRefuseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @Title: sendBizLog2Q
	 * @Description: 发送业务日志到日志中心mq队列
	 * @param @param msg 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	public static void sendBizLog2Q(Object obj) {
		try {
			if (bizlogSender == null) {
				bizName = HsPropertiesConfigurer
						.getProperty(ConfigConstant.BIZ_PLATFORM_NAME);
				bizNodeName = HsPropertiesConfigurer
						.getProperty(ConfigConstant.BIZ_SYSTEM_NAME);
				bizInstanceName = HsPropertiesConfigurer
						.getProperty(ConfigConstant.BIZ_SYSTEM_INSTANCE_NAME);
				host = HsPropertiesConfigurer
						.getProperty(ConfigConstant.RABBITMQ_HOSTADDR);
				bizQueue = getName(bizName, bizNodeName, bizInstanceName, "_",
						"bizlog_queue", true);
				bizExchange = getName(bizName, bizNodeName, bizInstanceName,
						"_", "bizlog_exchange", true);
				bizlogSender = new RabbitMQSender(host, bizQueue, bizExchange);
			}
			bizlogSender.sendObject(obj);

		} catch (SendRefuseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 
	 * @Title: sendBizLog2Q
	 * @Description: 发送业务日志到日志中心mq队列
	 * @param @param msg 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	public static void sendMonitor2Q(Object obj) {
		try {
			if (monitorLogSender == null) {
				host = HsPropertiesConfigurer
						.getProperty(ConfigConstant.RABBITMQ_HOSTADDR);
				monitorLogSender = new RabbitMQSender(host, ConfigConstant.MONITORLOG_QUEUE, ConfigConstant.MONITORLOG_EXCHANGE);
			}
			monitorLogSender.sendObject(obj);

		} catch (SendRefuseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// platformName 平台名
	// systemName 系统名
	// 实例名
	// appendName 系统或者业务的queueName或者exchaneName命名
	// differentiate 是否去除字符串中的数字，true 去除 flase 不去
	private static String getName(String platformName, String systemName,
			String systemInstanceName, String gap, String appendName,
			boolean differentiate) {
		StringBuffer name = new StringBuffer();
		if (StringUtils.isBlank(platformName)
				|| StringUtils.isBlank(systemName)
				|| StringUtils.isBlank(systemInstanceName)
				|| StringUtils.isBlank(gap) || StringUtils.isBlank(appendName)) {
			return "";
		}
		name.append(platformName).append(gap);
		String tempSystemName = systemName;
//		String tempSystemIntanceName = systemInstanceName;
		if (differentiate) {
			tempSystemName = remveNumberFromStr(systemName);
//			tempSystemIntanceName = remveNumberFromStr(systemInstanceName);
		}
		name.append(tempSystemName).append(gap).append(appendName);
		return name.toString();
	}

	// 去除字符串
	private static String remveNumberFromStr(String dealStr) {
		StringBuffer removeNumberStr = new StringBuffer();
		if (StringUtils.isBlank(dealStr)) {
			return "";
		}
		char[] cc = dealStr.toCharArray();
		for (char c : cc) {
			if (!StringUtils.isNumer(String.valueOf(c))) {
				removeNumberStr.append(c);
			}
		}
		return removeNumberStr.toString();
	}

	public static void showSysName() {
		System.out.println("queue:[" + sysQueue + "],exchange[" + sysExchange
				+ "]");
	}

	public static void showBizName() {
		System.out.println("queue:[" + bizQueue + "],exchange[" + bizExchange
				+ "]");
	}
}
