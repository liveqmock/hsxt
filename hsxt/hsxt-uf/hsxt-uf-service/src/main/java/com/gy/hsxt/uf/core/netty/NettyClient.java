/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.uf.core.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.bytes.ByteArrayDecoder;
import io.netty.handler.codec.bytes.ByteArrayEncoder;

import org.apache.log4j.Logger;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.uf.bean.packet.PacketHandleResult;
import com.gy.hsxt.uf.common.bean.DestPlatformAddress;
import com.gy.hsxt.uf.common.bean.packet.SecureRegionPacket;
import com.gy.hsxt.uf.common.bean.packet.SecureRegionPacketHeader;
import com.gy.hsxt.uf.common.constant.ConfigConst;
import com.gy.hsxt.uf.common.constant.Constant;
import com.gy.hsxt.uf.common.constant.UFResultCode;
import com.gy.hsxt.uf.common.spring.UfPropertyConfigurer;
import com.gy.hsxt.uf.common.utils.StringUtils;
import com.gy.hsxt.uf.common.utils.UfUtils;
import com.gy.hsxt.uf.core.dubbo.transfer.PacketSecurityDog;
import com.gy.hsxt.uf.core.netty.handler.NettyClientHandler;
import com.gy.hsxt.uf.core.netty.parent.NettyParent;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-uf-service
 * 
 *  Package Name    : com.gy.hsxt.uf.core.netty
 * 
 *  File Name       : NettyClient.java
 * 
 *  Creation Date   : 2015-10-24
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 综合前置Client类, 用于发送socket跨地区平台报文
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public final class NettyClient extends NettyParent {
	private Logger logger = Logger.getLogger(this.getClass());

	private EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
	private NettyClientHandler clientHandler = new NettyClientHandler();

	private DestPlatformAddress targetAddress;

	/**
	 * 构造函数
	 * 
	 * @param targetAddress
	 */
	public NettyClient(DestPlatformAddress targetAddress) {
		this.targetAddress = targetAddress;
	}

	/**
	 * 构造函数2
	 * 
	 * @param ip
	 * @param port
	 */
	public NettyClient(String ip, Integer port) {
		this.targetAddress = new DestPlatformAddress(ip, String.valueOf(port));
	}

	/**
	 * 获取Bootstrap对象
	 * 
	 * @param socketConnTimeout
	 * @return
	 */
	private Bootstrap getBootstrap(Integer socketConnTimeout) {
		Bootstrap bootstrap = new Bootstrap();
		bootstrap.group(eventLoopGroup);
		bootstrap.channel(NioSocketChannel.class);
		bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
		bootstrap.option(ChannelOption.TCP_NODELAY, true);
		bootstrap.option(ChannelOption.CONNECT_TIMEOUT_MILLIS,
				socketConnTimeout);
		bootstrap.handler(new ChannelInitializer<SocketChannel>() {
			@Override
			protected void initChannel(SocketChannel ch) throws Exception {
				ChannelPipeline pipeline = ch.pipeline();
				pipeline.addLast(new LengthFieldBasedFrameDecoder(
						Integer.MAX_VALUE, 0, 4, 0, 4));
				pipeline.addLast(new LengthFieldPrepender(4));
				pipeline.addLast(new ByteArrayDecoder());
				pipeline.addLast(new ByteArrayEncoder());
				pipeline.addLast(clientHandler);
			}
		});

		return bootstrap;
	}

	/**
	 * 发送跨地区平台报文
	 * 
	 * @param secureRegionPacket
	 *            报文对象
	 * @return
	 */
	public PacketHandleResult sendRegionPacket(
			SecureRegionPacket secureRegionPacket) {
		if (null == secureRegionPacket) {
			logger.error("传入的SecureRegionPacket对象不能为 null !");

			throw new HsException(UFResultCode.SYS_INNERNAL_ERR, "UF内部处理发生错误!");
		}

		// ip地址
		String ip = targetAddress.getIp();

		// 端口号
		int port = targetAddress.getPortIntValue();

		try {
			// 取得socket连接超时时间
			Integer socketTimeout = getSocketConnTimeout(secureRegionPacket);

			// AES加密、RSA加签
			byte[] encryptPacketBytes = PacketSecurityDog.getInstance()
					.addSignAndEncrypt(secureRegionPacket);

			ChannelFuture f = getBootstrap(socketTimeout).connect(ip, port)
					.sync();
			f.channel().writeAndFlush(encryptPacketBytes);
			f.channel().closeFuture().sync();
		} catch (Exception ex) {
			logger.error("发送socket消息时发生异常：", ex);

			throw new HsException(UFResultCode.EXTERNAL_COMM_ERR,
					"UF综合前置与目标平台综合前置socket通信失败! 可能原因:(1)GCS全局系统中配置的目标平台IP或端口不正确;"
							+ "(2)与目标平台综合前置之间的网络异常;(3)其他原因导致的错误; [目标平台地址:" 
							+ ip.concat(":") + port + "]" + ", detail:"
							+ UfUtils.getStackTraceInfo(ex));
		} finally {
			eventLoopGroup.shutdownGracefully();
		}

		// 返回处理结果
		PacketHandleResult handleResult = this.clientHandler.getResult();

		if (null == handleResult) {
			throw new HsException(UFResultCode.EXTERNAL_COMM_ERR,
					"UF综合前置与目标平台综合前置socket通信异常, 没有接收到目标平台任何响应报文! 可能原因:(1)GCS全局系统中配置的目标平台IP或端口不正确;"
							+ "(2)与目标平台综合前置之间的网络异常;(3)其他原因导致的错误; [目标平台地址:" + ip
							+ ":" + port + "]");
		}

		return handleResult;
	}

	/**
	 * 从报文头中获取超时时间
	 * 
	 * @param secureRegionPacket
	 * @return 单位毫秒
	 */
	private Integer getSocketConnTimeout(SecureRegionPacket secureRegionPacket) {
		SecureRegionPacketHeader header = secureRegionPacket.getHeader();

		// socket连接超时时间
		Integer socketTimeout = (Integer) header
				.getObligateArgsValue(Constant.KEY_SOCKET_CONNTIMEOUT);

		header.removeObligateArgs(Constant.KEY_SOCKET_CONNTIMEOUT);

		// socket连接超时时间如果没值或者超过最大值2分钟, 则取默认值
		if ((null == socketTimeout) || (0 >= socketTimeout)
				|| (Constant.MAX_SOCKET_CONNTIMEOUT <= socketTimeout)) {
			socketTimeout = this.getDefaultSocketConnTimeout();
		}

		return socketTimeout;
	}

	/**
	 * netty socket连接超时时长(如果没有配置或者配置的值小于等于0, 那么其值将默认为60000)
	 * 
	 * @return
	 */
	private Integer getDefaultSocketConnTimeout() {
		String value = UfPropertyConfigurer
				.getProperty(ConfigConst.SERVER_SOCKET_CONNTIMEOUT_MILLIS);

		int timeout = StringUtils.str2Int(value);

		if (0 >= timeout) {
			return Constant.DEFAULT_SOCKET_CONNTIMEOUT;
		}

		return timeout;
	}
}
