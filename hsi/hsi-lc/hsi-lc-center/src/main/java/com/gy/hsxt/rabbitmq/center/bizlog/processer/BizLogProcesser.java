package com.gy.hsxt.rabbitmq.center.bizlog.processer;



import java.util.concurrent.ConcurrentLinkedQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gy.hsxt.rabbitmq.center.bizlog.util.BizQueueManager;
import com.gy.hsxt.rabbitmq.common.bean.BizLogInfo;
import com.gy.hsxt.rabbitmq.common.constant.ConfigConstant;
import com.gy.hsxt.rabbitmq.common.rabbitmq.EventProcesser;
import com.gy.hsxt.rabbitmq.common.util.LogCenterTools;

/**
 * 
 * @ClassName: BizLogProcesser
 * @Description: push业务日志到缓存队列类
 * @author tianxh
 * @date 2015-8-6 下午2:37:54
 * 
 */
public class BizLogProcesser implements EventProcesser {
	private static final Logger log = LoggerFactory
			.getLogger(BizLogProcesser.class);

	/**
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
			BizQueueManager manager = BizQueueManager.getInstance();// 获取业务缓存队列管理实例
			if (e != null) {
				if (e instanceof BizLogInfo) {// 是否是业务日志对象
					BizLogInfo bizLog = (BizLogInfo) e;
					String queueName = LogCenterTools.getBizActiveFilePath(bizLog);// 获取业务日志输出路径
					ConcurrentLinkedQueue<BizLogInfo> queue = manager
							.createLinkedQueue(queueName.toString());
					if (null != queue) {
						manager.push(queueName, bizLog);// 以业务日志输出路径为key，push业务日志对象到对应的缓存队列
					}
				}
			}
		} catch (Exception e2) {
			log.error(ConfigConstant.MOUDLENAME, "[BizLogProcesser]",
					ConfigConstant.FUNNAME, "[process],code:", e2.getCause(),",message:",
					e2.getMessage());
		}
	}
}
