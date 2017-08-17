package com.gy.hsi.lc.client.log4j;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import com.alibaba.fastjson.JSON;
import com.gy.hsi.ds.param.HsPropertiesConfigurer;
import com.gy.hsxt.kafka.common.bean.BizLogInfo;
import com.gy.hsxt.kafka.common.constant.ConfigConstant;
import com.gy.hsxt.kafka.util.DateUtil;
import com.gy.hsxt.kafka.util.StringUtils;

/**
 * 
 * @ClassName: BizLog
 * @Description: 业务日志
 * @author Lee
 * @date 2015-7-8 下午5:52:09
 */
public class BizLog {
	private static long order;

	private BizLog() {
	}

	private static Producer<String, String> bizProducer;

	public static Producer<String, String> buildProducer() {
		KafkaProducer<String, String> producer = null;
		try {
			Properties props = new Properties();
			String hostaddr = HsPropertiesConfigurer
					.getProperty(ConfigConstant.KAFKA_HOSTADDR);
			if(!hostaddr.contains(":")){
				String port = HsPropertiesConfigurer
						.getProperty(ConfigConstant.KAFKA_PORT);
				hostaddr = hostaddr + ":" + port;
			}
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
			producer = new KafkaProducer<String, String>(
					props);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return producer;
	}

	/**
	 * 
	 * @Title: biz
	 * @Description: 输出业务日志到日志中心
	 * @param @param msg 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	public static final void biz(String moudleName, String funName,
			String columns, String contents) {
		BizLogInfo info = initBizLogInfo(moudleName, funName, columns, contents);
		String bizSwitch = HsPropertiesConfigurer
				.getProperty(ConfigConstant.BIZSWITCH);//本地日志开关
		String isPush = HsPropertiesConfigurer
				.getProperty(ConfigConstant.PUSH_BIZLOG2CENTER);//日志是否推送到服务器的开关
		String isKafka = HsPropertiesConfigurer
				.getProperty(ConfigConstant.ISKAFKA);//日志推送到kafka服务器还是rabbitMQ的开关
		// 本地打印
		if ("1".equals(bizSwitch)) {
			String logContent = getLogContent(info);
			System.out.println(logContent);
		}
		try {
			if ("1".equals(isPush)) {
				if ("1".equals(isKafka)) {//推送到kafka服务器
					if(null == bizProducer){
						bizProducer = buildProducer();
					}
					bizProducer.send(buildBizMessage(info));
				} else {// 推送到rabbitMQ服务器
					SendLog2Q.sendBizLog2Q(info);
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
	}

	private static ProducerRecord<String, String> buildBizMessage(
			BizLogInfo info) {
		ProducerRecord<String, String> msg = null;
		try {
			msg = new ProducerRecord<String, String>(info.getSystemName()
					+ "BIZ", String.valueOf(info.getOrder()),
					JSON.toJSONString(info));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return msg;

	}

	/**
	 * 
	 * @Title: initBizLogInfo
	 * @Description: 初始化业务日志信息
	 * @param @param moudleName
	 * @param @param funName
	 * @param @param columns
	 * @param @param contents
	 * @param @return 设定文件
	 * @return BizLogInfo 返回类型
	 * @throws
	 */
	private static BizLogInfo initBizLogInfo(String moudleName, String funName,
			String columns, String contents) {
		order++;
		BizLogInfo info = new BizLogInfo();

		info.setPlatformName(StringUtils.nullToEmpty(HsPropertiesConfigurer
				.getProperty(ConfigConstant.BIZ_PLATFORM_NAME)));

		info.setSystemName(StringUtils.nullToEmpty(HsPropertiesConfigurer
				.getProperty(ConfigConstant.BIZ_SYSTEM_NAME)));

		info.setSystemInstanceName(StringUtils
				.nullToEmpty(HsPropertiesConfigurer
						.getProperty(ConfigConstant.BIZ_SYSTEM_INSTANCE_NAME)));

		info.setMoudleName(StringUtils.nullToEmpty(moudleName));

		info.setFunName(StringUtils.nullToEmpty(funName));

		info.setColumns(StringUtils.nullToEmpty(columns));

		info.setContents(StringUtils.nullToEmpty(contents));

		info.setTimeStamp(DateUtil.getCurrentDate2String());

		info.setOrder(order);
		return info;
	}

	private static boolean isLocalWrite() {
		String bizSwitch = StringUtils.nullToEmpty(HsPropertiesConfigurer
				.getProperty(ConfigConstant.BIZSWITCH));
		return ConfigConstant.YES.equals(bizSwitch);
	}

	private static boolean isPush() {
		String isPush = StringUtils.nullToEmpty(HsPropertiesConfigurer
				.getProperty(ConfigConstant.PUSH_BIZLOG2CENTER));
		return isPush.equals(ConfigConstant.YES) ? true : false;
	}

	/**
	 * 
	 * @param sysLogInfo
	 * @return
	 */
	private static String getLogContent(BizLogInfo bizLogInfo) {
		StringBuilder logContent = new StringBuilder();
		logContent.append(StringUtils.nullToEmpty(bizLogInfo.getMoudleName()))
				.append(ConfigConstant.VERTICAL)
				.append(StringUtils.nullToEmpty(bizLogInfo.getFunName()))
				.append(ConfigConstant.VERTICAL)
				.append(StringUtils.nullToEmpty(bizLogInfo.getTimeStamp()))
				.append(ConfigConstant.VERTICAL)
				.append(StringUtils.nullToEmpty(bizLogInfo.getContents()))
				.append(ConfigConstant.CR);
		return logContent.toString();
	}
}
