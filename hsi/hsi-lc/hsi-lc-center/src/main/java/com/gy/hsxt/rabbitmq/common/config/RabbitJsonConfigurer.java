package com.gy.hsxt.rabbitmq.common.config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class RabbitJsonConfigurer {
	public static String sysConfig;

	public static String bizConfig;

	private static final String rabbitPath = System.getProperty("user.dir")
			+ File.separator + "conf/rabbitmq" + File.separator;

	public void initJsonConfig() {
		initRabbitSyslogConfig();
		initRabbitBizlogConfig();
	}

	public void initRabbitSyslogConfig() {
		String fullFileName = rabbitPath + "syslog.json";
		File file = new File(fullFileName);
		BufferedReader reader = null;
		// 返回值,使用StringBuffer
		StringBuffer data = new StringBuffer();
		try {
			reader = new BufferedReader(new FileReader(file));
			// 每次读取文件的缓存
			String temp = null;
			while ((temp = reader.readLine()) != null) {
				data.append(temp);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 关闭文件流
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		sysConfig = data.toString();
	}

	public void initRabbitBizlogConfig() {
		String fullFileName = rabbitPath + "bizlog.json";
		File file = new File(fullFileName);
		BufferedReader reader = null;
		// 返回值,使用StringBuffer
		StringBuffer data = new StringBuffer();
		try {
			reader = new BufferedReader(new FileReader(file));
			// 每次读取文件的缓存
			String temp = null;
			while ((temp = reader.readLine()) != null) {
				data.append(temp);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 关闭文件流
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		bizConfig = data.toString();
	}
 }
