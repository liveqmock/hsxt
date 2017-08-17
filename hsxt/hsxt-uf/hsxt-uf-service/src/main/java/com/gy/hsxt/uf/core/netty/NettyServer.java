/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.uf.core.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.bytes.ByteArrayDecoder;
import io.netty.handler.codec.bytes.ByteArrayEncoder;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.BindException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.springframework.util.Log4jConfigurer;
import org.springframework.util.ResourceUtils;
import org.springframework.util.SystemPropertyUtils;

import com.gy.hsxt.uf.common.constant.ConfigConst;
import com.gy.hsxt.uf.common.constant.ConfigConst.StartClassPath;
import com.gy.hsxt.uf.common.constant.Constant;
import com.gy.hsxt.uf.common.exception.UfBindException;
import com.gy.hsxt.uf.common.spring.UfPropertyConfigurer;
import com.gy.hsxt.uf.common.utils.StringUtils;
import com.gy.hsxt.uf.common.utils.UfUtils;
import com.gy.hsxt.uf.core.dubbo.DubboBridge;
import com.gy.hsxt.uf.core.netty.handler.NettyServerHandler;
import com.gy.hsxt.uf.core.netty.parent.NettyParent;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-uf-service
 * 
 *  Package Name    : com.gy.hsxt.uf.core.netty
 * 
 *  File Name       : NettyServer.java
 * 
 *  Creation Date   : 2015-9-24
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 综合前置Server类
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public final class NettyServer extends NettyParent {
	/** 记录日志对象 **/
	private Logger logger = Logger.getLogger(this.getClass());

	/** 单实例对象 **/
	private static final NettyServer instance = new NettyServer();

	/** 管理者工作组对象 **/
	private final EventLoopGroup bossGroup;

	/** 工作者工作组对象 **/
	private final EventLoopGroup workerGroup;

	/**
	 * 私有构造
	 */
	private NettyServer() {
		
		super();

		// 初始化log4j配置文件
		this.initLog4jCfg();

		int bossTheadPoolSize = this.getBossThreadPoolSize();
		int workerTheadPoolSize = this.getWokerThreadPoolSize();

		bossGroup = new NioEventLoopGroup(bossTheadPoolSize);
		workerGroup = new NioEventLoopGroup(workerTheadPoolSize);
	}

	/**
	 * 获得单实例
	 * 
	 * @return
	 */
	public static NettyServer getInstance() {
		return instance;
	}

	/**
	 * 启动netty server(直接使用配置文件中的ip和端口启动Server)
	 */
	public void startup() {
		// 启动成功标志
		boolean isStartSuccess = false;

		// 绑定的ip地址
		List<String> ipList = this.getServerIpList();

		// 绑定的端口
		Integer port = this.getServerPort();

		// 绑定异常对象
		Map<String, BindException> exceptions = new HashMap<String, BindException>(
				3);

		try {
			// 如果ip列表为空, 则抛出异常
			if (ipList.isEmpty()) {
				throw new IllegalArgumentException(
						"无法智能获取本主机的ip地址, 请手工方式在startup.sh文件中配置启动绑定的IP地址！");
			}

			// 执行netty server启动
			for (String ip : ipList) {
				String address = ip + ":" + port;

				try {
					logger.info("--> Try to bind " + address);

					// 尝试启动
					this.actionStartup(ip, port);

					// 置为启动成功标志
					isStartSuccess = true;

					// 重要!!!!!!!!!!!!!!
					break;
				} catch (BindException e) {
					exceptions.put(address, e);
				}
			}

			if (isStartSuccess) {
				// 向dubbo进行注册
				DubboBridge.getInstance().registerDubboService();
			} else {
				throw new UfBindException();
			}
		} catch (Exception e) {
			logger.error("--> Failed to start the server of UF!!!!!!");

			if (e instanceof UfBindException) {
				logger.error("[严重错误]： 无法启动netty server, 请检查配置的ip地址或端口是否有效!");

				Iterator<Entry<String, BindException>> itor = exceptions
						.entrySet().iterator();

				while (itor.hasNext()) {
					Entry<String, BindException> entry = (Entry<String, BindException>) itor
							.next();

					logger.error("无法绑定" + entry.getKey(), entry.getValue());
				}
			} else {
				logger.error("[严重错误]： 无法启动netty server, 请检查错误日志：", e);
			}

			// 退出进程
			System.exit(0);
		}
	}

	/**
	 * 关闭netty server
	 */
	public void shutdown() {
		workerGroup.shutdownGracefully();
		bossGroup.shutdownGracefully();

		logger.info("--> The server of UF has shutdown.");
	}

	/**
	 * 执行netty server启动
	 * 
	 * @param ip
	 * @param port
	 * @throws Exception
	 */
	private void actionStartup(String ip, Integer port) throws Exception {
		String serverIp = ip;
		Integer serverPort = port;

		{
			// 初始化netty并启动
			ServerBootstrap bootStrap = new ServerBootstrap();
			bootStrap.channel(NioServerSocketChannel.class);
			bootStrap.option(ChannelOption.SO_KEEPALIVE, true);
			bootStrap.option(ChannelOption.TCP_NODELAY, true);
			bootStrap.group(bossGroup, workerGroup);
			bootStrap.childHandler(new ChannelInitializer<SocketChannel>() {
				@Override
				public void initChannel(SocketChannel ch) throws Exception {
					ChannelPipeline pipeline = ch.pipeline();
					pipeline.addLast(new LengthFieldBasedFrameDecoder(
							Integer.MAX_VALUE, 0, 4, 0, 4));
					pipeline.addLast(new LengthFieldPrepender(4));
					pipeline.addLast(new ByteArrayDecoder());
					pipeline.addLast(new ByteArrayEncoder());
					pipeline.addLast(new NettyServerHandler());
				}
			});

			// 绑定ip和端口,并启动
			ChannelFuture f = bootStrap.bind(serverIp, serverPort).sync();

			// 启动成功日志
			logger.info("--> The server of UF has started success.");
			logger.info("--> The Server IP: " + serverIp + ", Port: "
					+ serverPort + ".");

			f.channel().closeFuture().sync();
		}
	}

	/**
	 * netty管理者线程池大小(如果没有配置或者配置的值小于等于0, 那么其值将默认为"当前主机的可用cpu内核数*2")
	 * 
	 * @return
	 */
	private int getBossThreadPoolSize() {
		String value = UfPropertyConfigurer
				.getProperty(ConfigConst.SERVER_BOSS_THREADPOOL_SIZE);

		int bossThreadPoolSize = StringUtils.str2Int(value);

		if (0 >= bossThreadPoolSize) {
			return Runtime.getRuntime().availableProcessors() * 2;
		}

		return bossThreadPoolSize;
	}

	/**
	 * netty工作者线程池大小(如果没有配置或者配置的值小于等于0, 那么其值将默认为"当前主机的可用cpu内核数*10")
	 * 
	 * @return
	 */
	private int getWokerThreadPoolSize() {
		String value = UfPropertyConfigurer
				.getProperty(ConfigConst.SERVER_WORKER_THREADPOOL_SIZE);
		int workerThreadPoolSize = StringUtils.str2Int(value);

		if (0 >= workerThreadPoolSize) {
			return Runtime.getRuntime().availableProcessors() * 10;
		}

		return workerThreadPoolSize;
	}

	/**
	 * 获得服务端ip
	 * 
	 * @return
	 */
	private List<String> getServerIpList() {
		List<String> ipList = new ArrayList<String>(3);

		// ip地址
		String ip = UfPropertyConfigurer.getProperty(StartClassPath.SERVER_IP);

		// 获取真实ip
		if (StringUtils.isEmpty(ip) || "127.0.0.1".equals(ip)
				|| "0.0.0.0".equals(ip)) {
			// 获取本机主机名
			ip = UfUtils.getLocalHostName();

			if (!StringUtils.isEmpty(ip)) {
				ipList.add(ip);
			}

			// 获取本机ip地址
			ip = UfUtils.getLocalHostIp();

			if (!StringUtils.isEmpty(ip) && !"127.0.0.1".equals(ip)
					&& !"0.0.0.0".equals(ip)) {
				ipList.add(UfUtils.getLocalHostIp());
			}
		} else {
			ipList.add(ip);
		}

		return ipList;
	}

	/**
	 * 获得服务端port
	 * 
	 * @return
	 */
	private int getServerPort() {
		String strPort = UfPropertyConfigurer
				.getProperty(StartClassPath.SERVER_PORT);

		int port = StringUtils.str2Int(strPort);

		// 1024以下端口属于linux系统自己使用的端口范围
		if (1024 > port) {
			port = Constant.DEFAULT_SERVER_PORT;
		}

		return port;
	}

	/**
	 * 初始化log4j配置文件
	 */
	private void initLog4jCfg() {

		String log4jCfgFilePath = "file:${user.dir}/conf/hsxt-uf";

		try {
			// 属性文件路径
			String propFilePath = SystemPropertyUtils
					.resolvePlaceholders(log4jCfgFilePath + "/log4j.properties");

			// xml文件路径
			String xmlFilePath = SystemPropertyUtils
					.resolvePlaceholders(log4jCfgFilePath + "/log4j.xml");

			if (isLog4jCfgFileExist(propFilePath)) {
				// Log4jConfigurer.initLogging("classpath:config/hsxt-uf/log4j.properties");
				Log4jConfigurer.initLogging(propFilePath);
			} else if (isLog4jCfgFileExist(xmlFilePath)) {
				// Log4jConfigurer.initLogging("classpath:config/hsxt-uf/log4j.xml");
				Log4jConfigurer.initLogging(xmlFilePath);
			}
		} catch (FileNotFoundException e) {
		}
	}

	/**
	 * 校验文件是否存在
	 * 
	 * @param filePath
	 * @return
	 */
	private boolean isLog4jCfgFileExist(String filePath) {
		try {
			String path = SystemPropertyUtils.resolvePlaceholders(filePath);
			URL url = ResourceUtils.getURL(path);

			URLConnection uConn = url.openConnection();
			uConn.setUseCaches(false);
			InputStream stream = uConn.getInputStream();

			stream.close();
		} catch (Exception e) {
			return false;
		}

		return true;
	}
}