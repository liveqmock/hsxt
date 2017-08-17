package com.gy.hsxt.rabbitmq.center.framework.bean;


/**
 * 
 * @ClassName: ConfigQueue 
 * @Description: 系统&业务日志队列信息实体bean
 * @author Lee 
 * @date 2015-7-10 下午6:09:16
 */
public class ConfigQueue {
	
	
	
	/**
	 * rabbitmq主机地址
	 */
	private String host;
	
	/**
	 * rabbitmq主机端口
	 */
	private String port;
	
	/**
	 * rabbitmq用户名
	 */
	private String username;
	
	/**
	 * rabbitmq密码
	 */
	private String password;
	
	/**
	 * 队列名
	 */
	private String queue;
	
	/**
	 * exchange
	 */
	private String exchange;
	
	/**
	 * routerKey
	 */
	private String routerKey;
	
	/**
	 * 消费者线程数
	 */
	private int maxconsumers;
	
	/**
	 * 队列信息相关描述
	 */
	private String desc;

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getQueue() {
		return queue;
	}

	public void setQueue(String queue) {
		this.queue = queue;
	}

	public String getExchange() {
		return exchange;
	}

	public void setExchange(String exchange) {
		this.exchange = exchange;
	}

	public String getRouterKey() {
		return routerKey;
	}

	public void setRouterKey(String routerKey) {
		this.routerKey = routerKey;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public int getMaxconsumers() {
		return maxconsumers;
	}

	public void setMaxconsumers(int maxconsumers) {
		this.maxconsumers = maxconsumers;
	}
	
	
}
