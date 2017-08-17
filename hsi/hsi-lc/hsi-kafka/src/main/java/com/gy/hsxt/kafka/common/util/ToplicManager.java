package com.gy.hsxt.kafka.common.util;



import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 
 * @ClassName: SysQueueManager
 * @Description: 队列管理器,将队列统一管理
 * @author tianxh
 * @date 2015-8-6 下午2:57:49
 * 
 */
public class ToplicManager {
	private static final Logger log = LoggerFactory
			.getLogger(ToplicManager.class);
	public static final int defQueueCapacity = 100000;
	public static final String defalutName = "mainQueue";

	public static final String MSG_SEND_QUEUE_NAME = "msgSendQueue";

	public static final String MSG_SEND_SUCCESS_QUEUE_NAME = "msgSendSuccessQueue";

	// 消息除列
	public static Map<String, ConcurrentLinkedQueue<List<ConsumerRecord<String,String>>>> mapMessageQueue = new HashMap<String, ConcurrentLinkedQueue<List<ConsumerRecord<String,String>>>>();

	private static ToplicManager instance = new ToplicManager();

	public static ToplicManager getInstance() {
		if (instance == null) {
			instance = new ToplicManager();
		}

		return instance;
	}

	public ToplicManager() {
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
	public synchronized ConcurrentLinkedQueue<List<ConsumerRecord<String,String>>> createLinkedQueue(
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
	public synchronized ConcurrentLinkedQueue<List<ConsumerRecord<String,String>>> createLinkedQueue(
			String queueName) {
		return newLinkedQueue(queueName, ToplicManager.defQueueCapacity);
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
	private synchronized ConcurrentLinkedQueue<List<ConsumerRecord<String,String>>> newLinkedQueue(
			String queueName, int capacity) {
		ConcurrentLinkedQueue<List<ConsumerRecord<String,String>>> queue = mapMessageQueue
				.get(queueName);
		if (queue == null) {
			queue = new ConcurrentLinkedQueue<List<ConsumerRecord<String,String>>>();
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
	public boolean push(String queueName, List<ConsumerRecord<String,String>> list) {
		ConcurrentLinkedQueue<List<ConsumerRecord<String,String>>> queue = mapMessageQueue
				.get(queueName);
		if (queue == null) {
			log.error("mapMessageQueue.get(queueName)==null," + queueName);
			queue = createLinkedQueue(queueName, defQueueCapacity);
		} 
		return queue.add(list);
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
	public List<ConsumerRecord<String,String>> poll(String queueName) throws InterruptedException {
		List<ConsumerRecord<String,String>> cm = null;
		ConcurrentLinkedQueue<List<ConsumerRecord<String,String>>> queue = mapMessageQueue
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

		Iterator<Entry<String, ConcurrentLinkedQueue<List<ConsumerRecord<String,String>>>>> it = mapMessageQueue
				.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, ConcurrentLinkedQueue<List<ConsumerRecord<String,String>>>> entry = (Entry<String, ConcurrentLinkedQueue<List<ConsumerRecord<String,String>>>>) it
					.next();
			String key = entry.getKey();
			ConcurrentLinkedQueue<List<ConsumerRecord<String,String>>> value = entry.getValue();
			sb.append(key).append("  size()=").append(value.size())
					.append("\n");
		}
		return sb.toString();
	}
}
