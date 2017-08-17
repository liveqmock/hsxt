package com.gy.hsi.ds.param.beans;

/**
 * @author liaoqiqi
 * @version 2014-6-24
 */
public class ZooConfig {

	private String zooHosts = "";

	public String zookeeperUrlPrefix = "";

	public String getZooHosts() {
		return zooHosts;
	}

	public void setZooHosts(String zooHosts) {
		this.zooHosts = format(zooHosts);
	}

	public String getZookeeperUrlPrefix() {
		return zookeeperUrlPrefix;
	}

	public void setZookeeperUrlPrefix(String zookeeperUrlPrefix) {
		this.zookeeperUrlPrefix = zookeeperUrlPrefix;
	}

	/**
	 * 只抽取出ip端口
	 * 
	 * @param hosts
	 * @return
	 */
	private String format(String hosts) {
		int start = hosts.indexOf("//");
		int end = hosts.lastIndexOf("?");

		if (0 > start) {
			start = 0;
		} else {
			start += 2;
		}

		if (0 > end) {
			end = hosts.length();
		}

		return hosts.substring(start, end);
	}
}
