package com.gy.hsi.ds.client;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author liaoqiqi
 * @version 2014-6-17
 */
public class JobMain {

	protected static final Logger LOGGER = Logger.getLogger(JobMain.class);

	/**
	 * @param args
	 *
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {

		@SuppressWarnings({ "unused", "resource" })
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext(
				new String[] { "applicationContext-job.xml" });

		try {
			System.in.read();
		} catch (IOException e) {
		}
	}
}
