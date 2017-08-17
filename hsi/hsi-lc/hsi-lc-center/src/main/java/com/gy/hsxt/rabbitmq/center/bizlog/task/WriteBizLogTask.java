package com.gy.hsxt.rabbitmq.center.bizlog.task;



import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gy.hsxt.rabbitmq.center.bizlog.util.BizQueueManager;
import com.gy.hsxt.rabbitmq.common.bean.BizLogInfo;
import com.gy.hsxt.rabbitmq.common.constant.ConfigConstant;
import com.gy.hsxt.rabbitmq.common.persistentlog.impl.HSPolicy;
import com.gy.hsxt.rabbitmq.common.persistentlog.impl.HSWriter;
import com.gy.hsxt.rabbitmq.common.util.LogCenterTools;
import com.gy.hsxt.rabbitmq.common.util.StringUtils;

/**
 * 
 * @ClassName: WriteBizLogTask
 * @Description: 业务日志内容缓存类（持久化之前）
 * @author tianxh
 * @date 2015-8-6 上午11:22:55
 * 
 */
public class WriteBizLogTask implements Runnable {
	private static final Logger log = LoggerFactory
			.getLogger(WriteBizLogTask.class);
	private String queueName;// 业务日志输出路径（缓存队列里对应的key）

	public WriteBizLogTask() {
	};

	public WriteBizLogTask(String queueName) {
		this.queueName = queueName;
	}

	/**
	 * 
	 * <p>
	 * Title: run
	 * </p>
	 * <p>
	 * Description: 业务日志内容缓存
	 * </p>
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		BizQueueManager manager = BizQueueManager.getInstance();
		BizLogInfo bizLogInfo = null; // 业务日志对象
		Map<Long, BizLogInfo> bizLogMap = new TreeMap<Long, BizLogInfo>();
		try {
			while (null != (bizLogInfo = manager.poll(queueName))) {
				bizLogMap.put(bizLogInfo.getOrder(), bizLogInfo);
			}
			toWriting(bizLogMap);
		} catch (InterruptedException e) {
			log.error(ConfigConstant.MOUDLENAME, "[WriteBizLogTask]",
					ConfigConstant.FUNNAME, "[run],code:", e.getCause(),
					",message:", e.getMessage());
		}
	}

	private void toWriting(Map<Long, BizLogInfo> treeMap) {
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
			if (firstGet) {
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
				firstGet = false;
			}
			map.put("activeFilePath", activeFilePath);
			map.put("targetFilePath", activeFilePath);
			map.put("prefixFileName", prefixFileName);
			map.put("columns", columns.toString());
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
