package com.gy.hsxt.kafka.common.task;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.gy.hsxt.rabbitmq.common.bean.BizLogInfo;
import com.gy.hsxt.rabbitmq.common.constant.ConfigConstant;
import com.gy.hsxt.rabbitmq.common.persistentlog.impl.HSPolicy;
import com.gy.hsxt.rabbitmq.common.persistentlog.impl.HSWriter;
import com.gy.hsxt.rabbitmq.common.util.LogCenterTools;
import com.gy.hsxt.rabbitmq.common.util.StringUtils;

public class KafkaBizConsumerTask implements Runnable {
	private static final Logger log = LoggerFactory
			.getLogger(KafkaBizConsumerTask.class);

	private String threadName;
	private Map<String, String> configMap;

	public KafkaBizConsumerTask(String threadName, Map<String, String> configMap) {
		this.threadName = threadName;
		this.configMap = configMap;
	}

	@Override
	public void run() {
		KafkaConsumer<String, String> consumer = BuildConsumer(configMap);
		while (true) {
			ConsumerRecords<String, String> records = consumer.poll(100);// 每100毫秒从kafka服务器上获取消息
			if (null != records && records.count() > 0) {
				persisteMessage(records);// 持久化这些消息
			}
		}
	}

	public void persisteMessage(ConsumerRecords<String, String> records) {
		Map<Long, BizLogInfo> msgMap = new TreeMap<Long, BizLogInfo>();
		for (ConsumerRecord<String, String> record : records) {
			BizLogInfo bizLogInfo =  JSON.parseObject(record.value(), BizLogInfo.class);
			msgMap.put(bizLogInfo.getOrder(), bizLogInfo);
		}
		writeMessageToFile(msgMap);
	}
	private KafkaConsumer<String, String> BuildConsumer(
			Map<String, String> valueMap) {
		Properties props = new Properties();
		props.put(ConfigConstant.BOOTSTRAPSERVERS,
				valueMap.get(ConfigConstant.BOOTSTRAPSERVERS));
		props.put(ConfigConstant.GROUPID, valueMap.get(ConfigConstant.GROUPID));
		props.put(ConfigConstant.ENABLEAUTOCOMMIT,
				valueMap.get(ConfigConstant.ENABLEAUTOCOMMIT));
		props.put(ConfigConstant.AUTOCOMMITINTERVALMS,
				valueMap.get(ConfigConstant.AUTOCOMMITINTERVALMS));
		props.put(ConfigConstant.SESSIONTIMEOUTMS,
				valueMap.get(ConfigConstant.SESSIONTIMEOUTMS));
		props.put(ConfigConstant.KEYDESERIALIZER,
				valueMap.get(ConfigConstant.KEYDESERIALIZER));
		props.put(ConfigConstant.VALUEDESERIALIZER,
				valueMap.get(ConfigConstant.VALUEDESERIALIZER));
		KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
		List<String> toplicList = getTopsParams(valueMap
				.get(ConfigConstant.TOPICS));
		consumer.subscribe(toplicList);
		return consumer;
	}

	private List<String> getTopsParams(String str) {
		List<String> resultList = new ArrayList<String>();
		if (StringUtils.isBlank(str)) {
			return resultList;
		}
		if (str.length() > 0) {
			if (!str.contains(",")) {
				resultList.add(str);
			} else {
				String[] arrays = str.split(",");
				resultList = Arrays.asList(arrays);
			}
		}
		return resultList;
	}

	private void writeMessageToFile(Map<Long, BizLogInfo> treeMap) {
		if (null == treeMap) {
			return;
		}
		int count = 0;// 缓存系统日志对象个数
		StringBuffer logContent = new StringBuffer();// 系统日志内容
		HSPolicy policy = null;// 写文件策略执行类
		HSWriter writer = null;// 写文件到硬盘的执行类
		boolean firstGet = true;// 首个poll到的业务日志对象
		StringBuffer columns = new StringBuffer();// 业务日志列名
		for (long key : treeMap.keySet()) {
			BizLogInfo bizLogInfo = treeMap.get(key);
			// 从消息队列中获取业务日志消息，每
			// LogConstant.FIXEDLENGTH
			// 笔写一次。
			if (null == bizLogInfo) {
				continue;
			}
			String activeFilePath = LogCenterTools
					.getBizActiveFilePath(bizLogInfo);// 获取当前正在写的业务日志文件的路径
			String prefixFileName = LogCenterTools
					.getBizPrefixFileName(bizLogInfo);// 获取当前正在写的业务日志文件名的前缀部分（例如：平台名-系统名-模块名_功能名_）
			Map<String, String> map = new HashMap<String, String>();
		//	if (firstGet) {
				columns = columns.append(ConfigConstant.PLATFORM_NAME)
						.append(ConfigConstant.VERTICAL)
						.append(ConfigConstant.NODE_NAME)
						.append(ConfigConstant.VERTICAL)
						.append(ConfigConstant.SYSTEM_INSTANCE_NAME)
						.append(ConfigConstant.VERTICAL)
						.append(ConfigConstant.MOUDLENAME)
						.append(ConfigConstant.VERTICAL)
						.append(ConfigConstant.FUNNAME)
						.append(ConfigConstant.VERTICAL)
						.append(ConfigConstant.DATE_FIELD)
						.append(ConfigConstant.VERTICAL)
						.append(bizLogInfo.getColumns());
			//	firstGet = false;
			//}
			map.put("activeFilePath", activeFilePath);
			map.put("targetFilePath", activeFilePath);
			map.put("prefixFileName", prefixFileName);
		//	map.put("columns", columns.toString());
			policy = new HSPolicy(map);
			writer = new HSWriter(policy, map);
			count++;
			logContent
					.append(bizLogInfo.getPlatformName())
					.append(ConfigConstant.VERTICAL)
					.append(bizLogInfo.getSystemName())
					.append(ConfigConstant.VERTICAL)
					.append(bizLogInfo.getSystemInstanceName())
					.append(ConfigConstant.VERTICAL)
					.append(StringUtils.nullToEmpty(bizLogInfo.getMoudleName()))
					.append(ConfigConstant.VERTICAL)
					.append(StringUtils.nullToEmpty(bizLogInfo.getFunName()))
					.append(ConfigConstant.VERTICAL)
					.append(StringUtils.nullToEmpty(bizLogInfo.getTimeStamp()))
					.append(ConfigConstant.VERTICAL)
					.append(StringUtils.nullToEmpty(bizLogInfo.getContents()))
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
