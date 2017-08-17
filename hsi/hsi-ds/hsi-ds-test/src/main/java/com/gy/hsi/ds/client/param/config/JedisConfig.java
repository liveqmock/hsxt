package com.gy.hsi.ds.client.param.config;


/**
 * Redis配置文件
 *
 * @author liaoqiqi
 * @version 2014-6-17
 */
public class JedisConfig {

	// 代表连接地址
	private String host;

	// 代表连接port
	private int port;

	/**
	 * 地址, 分布式文件配置
	 *
	 * @return
	 */
	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	/**
	 * 端口, 分布式文件配置
	 *
	 * @return
	 */
	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}
}
