/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsi.common.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.apache.log4j.Logger;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsi-ds-api
 * 
 *  Package Name    : com.gy.hsi.ds.common.lock
 * 
 *  File Name       : DistributedSyncLock.java
 * 
 *  Creation Date   : 2015年12月11日
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 分布式锁
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public final class DistributedSyncLock {
	private final Logger logger = Logger.getLogger(this.getClass());
	
	private boolean getLock = false;

	private String rootNode;
	private String subNode;

	private String thisSubPath;
	private String waitPath;
	private String[] zookeeperHosts;

	private ZooKeeper zookeeper;
	
	private CountDownLatch latch = new CountDownLatch(1);

	/**
	 * 创建一个实例
	 * 
	 * @param rootNode
	 * @param subNode
	 * @param dubboRegAddress
	 * @return
	 */
	public static DistributedSyncLock build(String rootNode, String subNode,
			String dubboRegAddress) {
		return new DistributedSyncLock(rootNode, subNode,
				getZookeeperAddress(dubboRegAddress));
	}

	/**
	 * 创建一个实例
	 * 
	 * @param rootNode
	 * @param subNode
	 * @param zookeeperHosts
	 * @return
	 */
	public static DistributedSyncLock build(String rootNode, String subNode,
			String[] zookeeperHosts) {
		return new DistributedSyncLock(rootNode, subNode, zookeeperHosts);
	}

	/**
	 * 构造函数
	 * 
	 * @param rootNode
	 * @param subNode
	 * @param zookeeperHosts
	 */
	private DistributedSyncLock(String rootNode, String subNode,
			String[] zookeeperHosts) {
		try {
			this.setRootNode(rootNode);
			this.setSubNode(subNode);
			this.setZookeeperHost(zookeeperHosts);

			this.initializeWatcher();
		} catch (IOException e) {
			logger.error("", e);
		}
	}

	/**
	 * 获取同步锁
	 * 
	 * @return
	 */
	public boolean getLock() {
		return this.getLock(false);
	}

	/**
	 * 停止
	 */
	public void stop() {
		try {
			if (null != zookeeper) {
				zookeeper.close();
			}
		} catch (InterruptedException e) {
		}
	}

	@Override
	protected void finalize() throws Throwable {
		this.stop();
		super.finalize();
	}

	/**
	 * 初始化监视事件
	 * 
	 * @throws IOException
	 */
	private void initializeWatcher() throws IOException {
		String zookeeperAddress = this.assembleHosts(this.zookeeperHosts);

		zookeeper = new ZooKeeper(zookeeperAddress, 10 * 1000, new Watcher() {
			public void process(WatchedEvent event) {
				// 连接建立时, 打开latch, 唤醒wait在该latch上的线程
				if (KeeperState.SyncConnected == event.getState()) {
					latch.countDown();
				}

				// 发生了waitPath的删除事件
				if ((EventType.NodeDeleted == event.getType())
						&& (event.getPath().equals(waitPath))) {
					getLock(true);
				}
			}
		});

		// 等待连接建立
		try {
			latch.await();
		} catch (InterruptedException e) {
			logger.error("", e);
		}

		// 创建子节点
		try {
			// 判断是否存在根节点
			if (null == zookeeper.exists(rootNode, false)) {
				zookeeper.create(rootNode, null, Ids.OPEN_ACL_UNSAFE,
						CreateMode.PERSISTENT);
			}

			// 创建临时子节点
			thisSubPath = zookeeper.create(rootNode + subNode, null,
					Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL)
					.replaceFirst("^" + rootNode + "/", "");
		} catch (KeeperException | InterruptedException e) {
			logger.error("", e);
		}
	}

	/**
	 * 获取锁
	 * 
	 * @param isForce
	 * @return
	 */
	private boolean getLock(boolean isForce) {
		if (!this.getLock) {
			List<String> childrenNodes = new ArrayList<String>(5);

			try {
				childrenNodes.addAll(zookeeper.getChildren(rootNode, false));
			} catch (KeeperException | InterruptedException e) {
				logger.error("", e);
			}

			// 排序
			Collections.sort(childrenNodes);

			// 编号排在第一的优先获取锁
			if (0 == childrenNodes.indexOf(thisSubPath)) {
				this.getLock = true;
			}
			// 排队等待老大死去, 争当老大
			else if (isForce && (1 < childrenNodes.size())) {
				// 获得排名比thisPath前1位的节点
				this.waitPath = childrenNodes.get(0);

				// 在waitPath上注册监听器, 当waitPath被删除时, zookeeper会回调监听器的process方法
				try {
					zookeeper.getData(waitPath, true, new Stat());
				} catch (KeeperException | InterruptedException e) {
					logger.error("", e);
				}
			}
		}

		return this.getLock;
	}

	/**
	 * 设置根节点路径
	 * 
	 * @param rootNode
	 */
	private void setRootNode(String rootNode) {
		if ((1 < rootNode.length()) && (rootNode.endsWith("/"))) {
			rootNode = rootNode.replaceFirst("/$", "");
		}

		if (!rootNode.startsWith("/")) {
			rootNode = "/" + rootNode;
		}

		this.rootNode = rootNode;
	}

	/**
	 * 设置子节点路径
	 * 
	 * @param subNode
	 */
	private void setSubNode(String subNode) {
		if ((1 < subNode.length()) && (subNode.endsWith("/"))) {
			subNode = subNode.replaceFirst("/$", "");
		}

		if (!subNode.startsWith("/")) {
			subNode = "/" + subNode;
		}

		this.subNode = subNode;
	}

	/**
	 * 设置hosts
	 * 
	 * @param zookeeperHost
	 */
	private void setZookeeperHost(String[] zookeeperHost) {
		this.zookeeperHosts = zookeeperHost;
	}

	/**
	 * 组装地址
	 * 
	 * @param zookeeperHost
	 * @return
	 */
	private String assembleHosts(String[] zookeeperHost) {
		StringBuilder bld = new StringBuilder();

		for (String host : zookeeperHost) {
			bld.append(host).append(",");
		}

		bld.delete(bld.length() - 1, bld.length());

		return bld.toString();
	}

	/**
	 * 获取zookeeper地址
	 * 
	 * @param dubboRegAddress
	 * @return
	 */
	private static String[] getZookeeperAddress(String dubboRegAddress) {

		int start = dubboRegAddress.indexOf("//");

		if (0 > start) {
			start = 0;
		} else {
			start += 2;
		}

		dubboRegAddress = dubboRegAddress.substring(start).replaceAll(
				"\\?backup\\=", ",");

		if (0 < (start = dubboRegAddress.indexOf("|"))) {
			dubboRegAddress = dubboRegAddress.substring(0, start);
		}

		return dubboRegAddress.split(",");
	}
}
