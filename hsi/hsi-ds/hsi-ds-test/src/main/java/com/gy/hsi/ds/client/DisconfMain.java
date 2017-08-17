package com.gy.hsi.ds.client;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.gy.hsi.ds.client.param.task.DisconfDemoTask;

/**
 * @author liaoqiqi
 * @version 2014-6-17
 */
public class DisconfMain {

	protected static final Logger LOGGER = Logger.getLogger(DisconfMain.class);

	/**
	 * @param args
	 *
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		
		@SuppressWarnings("resource")
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext(
				new String[] { "applicationContext-disconf.xml" });
		
		DisconfDemoTask task = ctx.getBean("disconfDemoTask",
				DisconfDemoTask.class);

		task.runInThread();

		try {
			System.in.read();
		} catch (IOException e) {
		}
	}
}
