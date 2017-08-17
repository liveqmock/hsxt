package com.gy.hsxt.keyserver.start;

import java.io.IOException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.gy.hsxt.keyserver.tools.FindFactoryBean;
//import com.gy.common.log.GyLogger;
 
public class keyServer {
//	private static final GyLogger LOGGER = GyLogger.getLogger(keyServer.class);

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		/*
		 * 初始化Bean工厂
		 */
		final ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		FindFactoryBean findFactoryBean = new FindFactoryBean();
		findFactoryBean.setApplicationContext(ctx);
		
		@SuppressWarnings("resource")
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				new String[] { "spring-keyserver.xml" });
		context.start();
		
		System.out.println("dubbo服务启动成功.");
		try {
			System.in.read();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
