/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.pos;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.ServerChannel;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.epoll.EpollSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.access.pos.handler.PosInitializer;

/**
 * 
 * 
 * @Package: com.gy.hsxt.access.pos  
 * @ClassName: NettyServer 
 * @Description: netty 初始化类
 *
 * @author: wucl 
 * @date: 2015-11-10 下午2:56:43 
 * @version V1.0
 */
@Service("nettyServer")
public class NettyServer  extends Thread {
	
	private ServerBootstrap bootstrap;
	private Channel channel;
	
	private volatile State state = State.STOPPED;
	
	@Value("${netty.port}")
	public int port;

	/**
	 * 是否用Linux epoll模型
	 */
	@Value("${netty.linuxEpoll}")
	private boolean linuxEpoll;
	
	@Value("${netty.bossGroupNum}")
	public int bossGroupNum;
	
	@Value("${netty.workerGroupNum}")
	public int workerGroupNum;
	
	@Autowired
	@Qualifier("posInitializer")
	private PosInitializer posInitializer;
	
	
	private enum State {
		STARTED, STOPPED
	}
	
	
//	public void init(int port, int bossGroupNum, int workerGroupNum, PosInitializer posInitializer){
//		this.port = port;
//		this.bossGroupNum = bossGroupNum;
//		this.workerGroupNum = workerGroupNum;
//		this.posInitializer = posInitializer;
//	}

	/**
	 * 服务器启动
	 *
	 */
	public synchronized void run() {
		
		
		SystemLog.info(this.getClass().getName(), "run()", "启动信息：\r\n ============pos server starting=================" +
				"\r\n Netty Port: " + port +
				"\r\n Netty bossGroupNum: " + bossGroupNum +
				"\r\n Netty workerGroupNum: " + workerGroupNum);
		
		if (isStarted()) {
			throw new IllegalStateException("Failed to start pos server: server already started");
		}
		//服务器通道类型
		Class<? extends ServerChannel> channelClass;
		//主通道线程池大小
		EventLoopGroup bossGroup;
		//工作线程池大小
		EventLoopGroup workerGroup;
		if (linuxEpoll) {//是否用Linux epoll模型
			channelClass = EpollServerSocketChannel.class;
			bossGroup = new EpollEventLoopGroup(bossGroupNum);
			workerGroup = new EpollEventLoopGroup(workerGroupNum);
		}else {
			channelClass = NioServerSocketChannel.class;
			bossGroup = new NioEventLoopGroup(bossGroupNum);
			workerGroup = new NioEventLoopGroup(workerGroupNum);
		}
        try {
            bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup).channel(channelClass)
                    // .childOption(ChannelOption.SO_BACKLOG, 2000) // 2000 is better than 1000 or 6000.
                    .childOption(ChannelOption.TCP_NODELAY, true) //
                    .childOption(ChannelOption.SO_KEEPALIVE, false) // for short conn, false is better absolutely.
                    .childOption(ChannelOption.SO_REUSEADDR, false) // false is more stable than true.
                    .childOption(ChannelOption.SO_LINGER, 0) // 0 is more stable than 300 or 3000.
                    .childOption(ChannelOption.SO_SNDBUF, 256 * 1024) //256K
                    .childOption(ChannelOption.SO_RCVBUF, 128 * 1024) // FileUtils...128K
                    .childHandler(posInitializer);
            
            channel = bootstrap.bind(port).sync().channel();
            channel.closeFuture().sync();
            
        } catch (Exception e) {
            SystemLog.error(this.getClass().getName(), "run()","启动服务失败", e);
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
		
        SystemLog.info(this.getClass().getName(), "run()","============started======== port:" + port);

		state = State.STARTED;

	}
	
	/**
	 * 服务器重启
	 *
	 * @throws Exception
	 */

	public synchronized void restart() throws Exception {
		if (isStarted()) {
			posStop();
		}
		start();
	}
	
	/**
	 * 服务器停止
	 */
	public synchronized void posStop() {
		if (isStopped()) {
			throw new IllegalStateException("Failed to stop pos server: server already stopped");
		}
		bootstrap.group().shutdownGracefully();
		SystemLog.info(this.getClass().getName(), "posStop()","============stoped========");
		state = State.STOPPED;
	}

	/**
	 * 判断服务器是否已经启动，返回true表示已经启动
	 */
	public boolean isStarted() {
		return state == State.STARTED;
	}

	/**
	 * 判断服务器是否已经停止，返回true表示已经停止
	 */
	public boolean isStopped() {
		return state == State.STOPPED;
	}

}
