package com.gy.hsxt.rabbitmq.center.syslog.processer;



import java.util.concurrent.ConcurrentLinkedQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gy.hsxt.rabbitmq.center.syslog.util.SysQueueManager;
import com.gy.hsxt.rabbitmq.common.bean.SysLogInfo;
import com.gy.hsxt.rabbitmq.common.constant.ConfigConstant;
import com.gy.hsxt.rabbitmq.common.rabbitmq.EventProcesser;
import com.gy.hsxt.rabbitmq.common.util.LogCenterTools;

/**
 * 
 * @ClassName: SysLogProcesser
 * @Description: push系统日志到缓存队列类
 * @author tianxh
 * @date 2015-8-6 下午2:37:17
 * 
 */
public class SysLogProcesser implements EventProcesser {
	private static final Logger log = LoggerFactory
			.getLogger(SysLogProcesser.class);

	/**
	 * 
	 * <p>
	 * Title: process
	 * </p>
	 * <p>
	 * Description: push业务日志到缓存队列
	 * </p>
	 * 
	 * @param e
	 * @see com.gy.hsi.lc.common.rabbitmq.EventProcesser#process(java.lang.Object)
	 */
	@Override
	public void process(Object e) {
		try {
			if (e != null) {
				SysQueueManager manager = SysQueueManager.getInstance();// 获取系统缓存队列管理实例
				if (e instanceof SysLogInfo) {// 是否是系统日志对象
					SysLogInfo syslog = (SysLogInfo) e;
					String queueName = LogCenterTools
							.getSystemActiveFilePath(syslog);// 获取系统日志输出路径
					ConcurrentLinkedQueue<SysLogInfo> queue = manager
							.createLinkedQueue(queueName);
					if (null != queue) {
						manager.push(queueName, syslog);// 以系统日志输出路径为key，push业务日志对象到对应的缓存队列
					}
				}else{
					log.warn("! e instanceof SysLogInfo:"+e.getClass().getName());
					System.out.println("! e instanceof SysLogInfo:"+e.getClass().getName());
				}
			}else{
				log.warn("process(Object e) e==null");
				System.out.println("process(Object e) e==null");
			}
		} catch (Exception e2) {
			log.error(ConfigConstant.MOUDLENAME, "[SysLogProcesser]",
					ConfigConstant.FUNNAME, "[process],code:", e2.getCause(),
					",message:", e2.getMessage());
		}
	}
}
