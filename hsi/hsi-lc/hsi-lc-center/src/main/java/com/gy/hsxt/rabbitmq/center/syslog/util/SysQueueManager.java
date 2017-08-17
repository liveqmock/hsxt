package com.gy.hsxt.rabbitmq.center.syslog.util;



import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gy.hsxt.rabbitmq.common.bean.SysLogInfo;

/**
 * 
 * @ClassName: SysQueueManager
 * @Description: 队列管理器,将队列统一管理
 * @author tianxh
 * @date 2015-8-6 下午2:57:49
 * 
 */
public class SysQueueManager {
	private static final Logger log = LoggerFactory
			.getLogger(SysQueueManager.class);
	public static final int defQueueCapacity = 100000;
	public static final String defalutName = "mainQueue";

	public static final String MSG_SEND_QUEUE_NAME = "msgSendQueue";

	public static final String MSG_SEND_SUCCESS_QUEUE_NAME = "msgSendSuccessQueue";

	// 消息除列
	public static Map<String, ConcurrentLinkedQueue<SysLogInfo>> mapMessageQueue = new HashMap<String, ConcurrentLinkedQueue<SysLogInfo>>();

	private static SysQueueManager instance = new SysQueueManager();

	public static SysQueueManager getInstance() {
		if (instance == null) {
			instance = new SysQueueManager();
		}

		return instance;
	}

	public SysQueueManager() {
	}

	/**
	 * 
	 * @Title: createLinkedQueue
	 * @Description: 创建消息队列
	 * @param @param queueName
	 * @param @param capacity
	 * @param @return 设定文件
	 * @return ConcurrentLinkedQueue<SysLogInfo> 返回类型
	 * @throws
	 */
	public synchronized ConcurrentLinkedQueue<SysLogInfo> createLinkedQueue(
			String queueName, int capacity) {
		return newLinkedQueue(queueName, capacity);
	}

	/**
	 * 
	 * @Title: createLinkedQueue
	 * @Description: 创建消息队列
	 * @param @param queueName
	 * @param @return 设定文件
	 * @return ConcurrentLinkedQueue<SysLogInfo> 返回类型
	 * @throws
	 */
	public synchronized ConcurrentLinkedQueue<SysLogInfo> createLinkedQueue(
			String queueName) {
		return newLinkedQueue(queueName, SysQueueManager.defQueueCapacity);
	}

	/**
	 * 
	 * @Title: newLinkedQueue
	 * @Description: 创建消息队列，并返回
	 * @param @param queueName
	 * @param @param capacity
	 * @param @return 设定文件
	 * @return ConcurrentLinkedQueue<SysLogInfo> 返回类型
	 * @throws
	 */
	private synchronized ConcurrentLinkedQueue<SysLogInfo> newLinkedQueue(
			String queueName, int capacity) {
		ConcurrentLinkedQueue<SysLogInfo> queue = mapMessageQueue
				.get(queueName);
		if (queue == null) {
			queue = new ConcurrentLinkedQueue<SysLogInfo>();
			mapMessageQueue.put(queueName, queue);
		}
		return queue;
	}

	/**
	 * 
	 * @Title: push
	 * @Description: 添加消息到消息队列中(如果队列不存在，则先创建，再添加)
	 * @param @param queueName
	 * @param @param entity
	 * @param @return 设定文件
	 * @return boolean 返回类型
	 * @throws
	 */
	public boolean push(String queueName, SysLogInfo entity) {
		ConcurrentLinkedQueue<SysLogInfo> queue = mapMessageQueue
				.get(queueName);
		if (queue == null) {
			log.error("mapMessageQueue.get(queueName)==null," + queueName);
			queue = createLinkedQueue(queueName, defQueueCapacity);
		} 
		return queue.add(entity);
	}

	/**
	 * 
	 * @Title: poll
	 * @Description: 从消息队列中获取消息(如果队列不存在，则先创建，再获取消息)
	 * @param @param queueName
	 * @param @return
	 * @param @throws InterruptedException 设定文件
	 * @return SysLogInfo 返回类型
	 * @throws
	 */
	public SysLogInfo poll(String queueName) throws InterruptedException {
		SysLogInfo cm = null;
		ConcurrentLinkedQueue<SysLogInfo> queue = mapMessageQueue
				.get(queueName);
		if (queue == null) {
			queue = createLinkedQueue(queueName, defQueueCapacity);
		} else {
			// synchronized(queue) {
			if (!queue.isEmpty()) {
				cm = queue.poll();
			}

			// }
		}
		return cm;
	}

	/**
	 * 
	 * <p>
	 * Title: toString
	 * </p>
	 * <p>
	 * Description: 重写 toString()
	 * </p>
	 * 
	 * @return
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		Iterator<Entry<String, ConcurrentLinkedQueue<SysLogInfo>>> it = mapMessageQueue
				.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, ConcurrentLinkedQueue<SysLogInfo>> entry = (Entry<String, ConcurrentLinkedQueue<SysLogInfo>>) it
					.next();
			String key = entry.getKey();
			ConcurrentLinkedQueue<SysLogInfo> value = entry.getValue();
			sb.append(key).append("  size()=").append(value.size())
					.append("\n");
		}
		return sb.toString();
	}
}
