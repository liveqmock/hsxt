package com.gy.hsxt.pg.bankadapter.pingan.b2e.protocol;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Hashtable;

import org.apache.log4j.Logger;
/**
 * 测试发现仍然需要优化，待成熟之后注入系统
 * @author jbli
 */
public class ConnectionPool {
	
	private static final Logger logger = Logger.getLogger(ConnectionPool.class);
	
	//10个连接
	private static final int CONNECTION_POOL_SIZE = 20;

	private static final String API_SERVER_HOST = "192.168.1.250";

	private static final int API_SERVER_PORT = 7070;

	private static ConnectionPool self = null; // ConnectionPool的唯一实例
	
	private Hashtable<Object, Object> socketPool = null;// 连接池
	
	private boolean[] socketStatusArray = null;// 连接的状态（true-被占用；false-空闲）

	public static synchronized ConnectionPool init() {
		self = new ConnectionPool();
		self.socketPool = new Hashtable<Object, Object>();
		self.socketStatusArray = new boolean[CONNECTION_POOL_SIZE];
		ConnectionPool.buildConnectionPool();
		return self;
	}

	public synchronized void reset() {
		self = null;
		init();
	}

	/**
	 * 取出连接
	 * @return
	 */
	public synchronized Socket getConnection() {
		if (self == null)
			init();
		int i = 0;
		
		//从连接池中取出一个连接未被占用的连接
		for (i = 0; i < CONNECTION_POOL_SIZE; i++) {
			if (!self.socketStatusArray[i]) {
				self.socketStatusArray[i] = true;
				Socket socket= (Socket) self.socketPool.get(new Integer(i));
				
				if(socket ==null){
					socket = new Socket();
					SocketAddress endpoint = new InetSocketAddress(API_SERVER_HOST,API_SERVER_PORT);
					try {
						socket.connect(endpoint, 100*1000);
						socket.setSoTimeout(6000*1000);          // 读超时为60秒
					} catch (IOException e) {
						logger.error("IOException", e);
					}
					self.socketPool.put(new Integer(i), socket);
					self.socketStatusArray[i] = false;
					return socket;
				}else
					return socket;
				
			}
			if(i == CONNECTION_POOL_SIZE-1){
				logger.info("No enough pooled connections.");
				try {
					this.wait();
					logger.info("has excute this.wait()！");
				} catch (InterruptedException e) {
					logger.error("InterruptedException", e);
				}
			}
		}
		return getConnection();
	}

	/**
	 * 释放一个连接
	 * @param socket
	 */
	public synchronized void releaseConnection(Socket socket) {
		if (self == null)
			init();
		for (int i = 0; i < CONNECTION_POOL_SIZE; i++) {
			if (((Socket) self.socketPool.get(new Integer(i))) == socket) {
				self.socketStatusArray[i] = false;
				logger.info("will excute this.notify()！");
				this.notify();
				logger.info("has excute this.notify()！");
				break;
			}
		}
	}

	public synchronized static Socket rebuildConnection(Socket socket) {
		if (self == null)
			init();

		Socket newSocket = null;
		for (int i = 0; i < CONNECTION_POOL_SIZE; i++) {
			try {
				if (((Socket) self.socketPool.get(new Integer(i))) == socket) {
					logger.info("rebuild conections of pool, the no." + i + " conection.");
					newSocket = new Socket(API_SERVER_HOST, API_SERVER_PORT);
					self.socketPool.put(new Integer(i), newSocket);
					self.socketStatusArray[i] = true;
					break;
				}
			} catch (Exception e) {
				logger.error("failed to reconect！");
				throw new RuntimeException(e);
			}
		}
		return newSocket;
	}

	public synchronized static void buildConnectionPool() {
		if (self == null)
			init();
		logger.debug("准备建立连接池.");
		Socket socket = null;
		int i = 0;
		try {
			for (i = 0; i < CONNECTION_POOL_SIZE; i++) {
				socket = new Socket();
				SocketAddress endpoint = new InetSocketAddress(API_SERVER_HOST,API_SERVER_PORT);
				socket.connect(endpoint, 100*1000);
				socket.setSoTimeout(6000*1000);          // 读超时为60秒
				self.socketPool.put(new Integer(i), socket);
				self.socketStatusArray[i] = false;
			}
		} catch (Exception e) {
			logger.error("the no."+i+" conect to bank failed！"+e);
			throw new RuntimeException(e);
		}
	}

	public synchronized static void releaseAllConnection() {
		if (self == null)
			init();

		// 关闭所有连接
		Socket socket = null;
		for (int i = 0; i < CONNECTION_POOL_SIZE; i++) {
			socket = (Socket) self.socketPool.get(new Integer(i));
			try {
				socket.close();
			} catch (Exception e) {
			}
		}
	}
	
	public static void main(String args[]){
		ConnectionPool.init();
		try {
			Thread.sleep(60*1000);
		} catch (InterruptedException e) {
			logger.error("in test main method error:",e);
		}
	}
}
