package com.gy.hsxt.kafka.thread;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;

import com.alibaba.fastjson.JSON;
import com.gy.hsxt.kafka.common.bean.BizLogInfo;
import com.gy.hsxt.kafka.common.constant.ConfigConstant;
import com.gy.hsxt.kafka.common.persistent.impl.HSPolicy;
import com.gy.hsxt.kafka.common.persistent.impl.HSWriter;
import com.gy.hsxt.kafka.common.util.StringUtils;
import com.gy.hsxt.kafka.common.util.ToplicManager;
import com.gy.hsxt.kafka.util.LogCenterTools;


public class BizConsumerRunner implements Runnable{
	private String toplic;
	int count;
	public BizConsumerRunner(String toplic){
		this.toplic = toplic;
		
	}
	@Override
	public void run() {
		ToplicManager manager = ToplicManager.getInstance();
		List<ConsumerRecord<String,String>> list = null;
		try {
			while (null != (list = manager.poll(toplic))) {
				persisteMessage(list);
			}
		//	persisteMessage(records);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}

	private void writerMsgToDisk(String msg) {
		FileWriter out = null;
		try {
			out = new FileWriter(new File("d:/hanhan3/BizConsumer()"+count+".txt"));
			out.write(msg);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (null != out) {
				try {
					out.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				out = null;
			}
		}
	}
	
	public void persisteMessage(List<ConsumerRecord<String,String>> list) {
	Map<Long, BizLogInfo> msgMap = new TreeMap<Long, BizLogInfo>();
	for (ConsumerRecord<String, String> record : list) {
		BizLogInfo bizLogInfo =  JSON.parseObject(record.value(), BizLogInfo.class);
		msgMap.put(bizLogInfo.getOrder(), bizLogInfo);
	}
	writeMessageToFile(msgMap);
}

private void writeMessageToFile(Map<Long, BizLogInfo> treeMap) {
	if (null == treeMap) {
		return;
	}
	int count = 0;// 缓存系统日志对象个数
	StringBuffer logContent = new StringBuffer();// 系统日志内容
	HSPolicy policy = null;// 写文件策略执行类
	HSWriter writer = null;// 写文件到硬盘的执行类
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
