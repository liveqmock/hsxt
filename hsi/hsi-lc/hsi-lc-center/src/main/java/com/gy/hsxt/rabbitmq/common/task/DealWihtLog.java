package com.gy.hsxt.rabbitmq.common.task;



import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;

import com.gy.hsxt.rabbitmq.center.bizlog.threadpool.BizLogThreadPool;
import com.gy.hsxt.rabbitmq.center.syslog.threadpool.SysLogThreadPool;


/**
 * 
 * @ClassName: SysInitor
 * @Description: 初始化类
 * @author Lee
 * @date 2015-6-29 下午2:33:06
 */
@Component
public class DealWihtLog implements InitializingBean, ServletContextAware {

	private static final Logger log = LoggerFactory.getLogger(DealWihtLog.class);

	@Override
	public void afterPropertiesSet() throws Exception {
		log.info("afterPropertiesSet running...");
		ScheduledExecutorService schedule = Executors.newScheduledThreadPool(2);
		schedule.scheduleWithFixedDelay(new SysLogThreadPool(), 5, 50,
				TimeUnit.MILLISECONDS);
		schedule.scheduleWithFixedDelay(new BizLogThreadPool(), 5, 50,
				TimeUnit.MILLISECONDS);
	}

	@Override
	public void setServletContext(ServletContext arg0) {
	}
}
