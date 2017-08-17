package com.gy.hsxt.rabbitmq.center.bizlog.threadpool;



import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gy.hsxt.rabbitmq.center.bizlog.task.WriteBizLogTask;
import com.gy.hsxt.rabbitmq.center.bizlog.util.BizQueueManager;
import com.gy.hsxt.rabbitmq.common.bean.BizLogInfo;
import com.gy.hsxt.rabbitmq.common.constant.ConfigConstant;

/**
 * 
 * @ClassName: BizLogThreadPool
 * @Description: 写业务日志文件的线程池类
 * @author tianxh
 * @date 2015-8-6 下午2:12:00
 * 
 */
public class BizLogThreadPool implements Runnable {
	private static final Logger log = LoggerFactory
			.getLogger(BizLogThreadPool.class);

	/**
	 * <p>
	 * Title: run
	 * </p>
	 * <p>
	 * Description:批量执行写业务日志文件
	 * </p>
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		ExecutorService exec = Executors.newFixedThreadPool(Runtime
				.getRuntime().availableProcessors() * 2);// 起线程池准备写文件
		Map<String, ConcurrentLinkedQueue<BizLogInfo>> queueMap = BizQueueManager.mapMessageQueue;// 获取业务日志缓冲队列
		Iterator<String> iterator = queueMap.keySet().iterator();
		String queueName = "";
		while (iterator.hasNext()) {
			queueName = iterator.next();// 队列的KEY即写文件要的路径
			exec.execute(new WriteBizLogTask(queueName));// 开始执行写文件操作
		}
		exec.shutdown();// 关闭线程池
		try {
			while (!exec.awaitTermination(1, TimeUnit.SECONDS)) {// 线程池是否完成关闭
				;
			}
		} catch (InterruptedException e) {
			log.error(ConfigConstant.MOUDLENAME, "[BizLogThreadPool]",
					ConfigConstant.FUNNAME, "[run],code:", e.getCause(),
					",message:", e.getMessage());
		}
	}
}
