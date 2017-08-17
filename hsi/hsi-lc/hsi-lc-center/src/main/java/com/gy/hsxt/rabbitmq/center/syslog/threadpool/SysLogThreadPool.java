package com.gy.hsxt.rabbitmq.center.syslog.threadpool;



import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gy.hsxt.rabbitmq.center.syslog.task.WriteSysLogTask;
import com.gy.hsxt.rabbitmq.center.syslog.util.SysQueueManager;
import com.gy.hsxt.rabbitmq.common.bean.SysLogInfo;
import com.gy.hsxt.rabbitmq.common.constant.ConfigConstant;

/**
 * 
 * @ClassName: SysLogThreadPool
 * @Description: 写业务日志文件的线程池类
 * @author tianxh
 * @date 2015-8-6 下午2:45:47
 * 
 */
public class SysLogThreadPool implements Runnable {
	private static final Logger log = LoggerFactory
			.getLogger(SysLogThreadPool.class);
	/**
	 * 
	 * <p>
	 * Title: run
	 * </p>
	 * <p>
	 * Description: 批量执行写系统日志文件
	 * </p>
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		ExecutorService exec = Executors.newFixedThreadPool(Runtime
				.getRuntime().availableProcessors() * 2);// 起线程池准备写文件
		Map<String, ConcurrentLinkedQueue<SysLogInfo>> queueMap = SysQueueManager.mapMessageQueue;
		Iterator<String> iterator = queueMap.keySet().iterator();
		String queueName = "";
		while (iterator.hasNext()) {
			queueName = iterator.next();// 队列的KEY即写文件要的路径
			exec.execute(new WriteSysLogTask(queueName));// 开始执行写文件操作
		}
		exec.shutdown();
		try {
			while (!exec.awaitTermination(1, TimeUnit.SECONDS)) { // 线程池是否完成关闭
				;
			}
		} catch (InterruptedException e) {
			log.error(ConfigConstant.MOUDLENAME, "[SysLogThreadPool]",
					ConfigConstant.FUNNAME, "[run],code:", e.getCause(),",message:" ,e.getMessage());
		}
	}

}
