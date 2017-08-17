/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.uf.core.netty.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import org.apache.log4j.Logger;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.uf.bean.packet.PacketHandleResult;
import com.gy.hsxt.uf.common.bean.packet.SecureRegionPacket;
import com.gy.hsxt.uf.common.bean.packet.SecureRegionPacketHeader;
import com.gy.hsxt.uf.common.constant.Constant;
import com.gy.hsxt.uf.common.constant.UFResultCode;
import com.gy.hsxt.uf.core.dubbo.DubboBridge;
import com.gy.hsxt.uf.core.dubbo.transfer.PacketSecurityDog;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-uf-service
 * 
 *  Package Name    : com.gy.hsxt.uf.core.netty.handler
 * 
 *  File Name       : NettyServerHandler.java
 * 
 *  Creation Date   : 2015-9-24
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 综合前置Server端异步消息处理类, 用于异步接收并处理Netty Client端反馈的报文
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class NettyServerHandler extends ChannelInboundHandlerAdapter {
	/** 记录日志对象 **/
	private final Logger logger = Logger.getLogger(this.getClass());

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		super.channelActive(ctx);
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		super.channelInactive(ctx);
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object reqMsg)
			throws Exception {
		if (null == reqMsg) {
			return;
		}

		// 转换为字节类型
		byte[] reqBytes = (byte[]) reqMsg;

		// 跨地区报文对象
		SecureRegionPacket requestPacket = null;

		// 处理结果对象
		PacketHandleResult handleResult = new PacketHandleResult();

		/*------------------------------对报文进行解密、解签处理-----------------------------*/
		try {
			// 对报文进行解密、解签处理
			requestPacket = PacketSecurityDog.getInstance().decryptAndUnsign(
					reqBytes);
		} catch (Exception e) {
			handleResult.putErrorInfo(UFResultCode.PACKET_NOT_RECOGNIZED,
					"目标平台UF无法识别该报文的签名,该请求被拒绝! 请检查交互双方的UF安全密钥文件是否一致.");

			logger.error(handleResult.getResultDesc(), e);
		}

		/*------------------------------辨别、接收、内传报文-------------------------------*/
		if (null != requestPacket) {
			try {
				// 辨别该报文是否属于本地区平台, 如果不属于就不予处理
				if (!PacketSecurityDog.getInstance()
						.isBelongToMe(requestPacket)) {
					throw new HsException(UFResultCode.PACKET_ROUTER_DEST_ERR,
							"严重错误, 此来源报文不属于本目标平台, 请求被拒绝!");
				}

				// 将请求的跨地区报文交由dubbo桥处理
				handleResult = DubboBridge.getInstance()
						.handlePacketsFromOutside(requestPacket);
			} catch (Exception e) {
				handleResult.putErrorInfo(e);

				logger.error(handleResult.getResultDesc(), e);
			} finally {
				Throwable cause = handleResult.getCause();

				if (cause instanceof HsException) {
					handleResult.putErrorInfo(
							UFResultCode.DEST_PLATFORM_HANDLE_ERR,
							"目标平台处理该报文时发生错误! 参考错误原因：" + cause.getMessage());
				}

				// 为了节省流量, 网络上不传送这个比较重的异常对象
				handleResult.cleanUpCause();
			}
		}

		/*------------------------------加密&加签、回传响应结果----------------------------*/
		// 将处理结果对象装入信封
		SecureRegionPacket respRegionPacket = assembleResponsePacket(
				requestPacket, handleResult);

		// 将响应报文加签、加密后回传给请求方handler, 即：client端
		byte[] respBytes = PacketSecurityDog.getInstance().addSignAndEncrypt(
				respRegionPacket);

		ctx.channel().writeAndFlush(respBytes);
		ctx.close();
	}

	/**
	 * 组装响应报文
	 * 
	 * @param requestPacket
	 * @param handlerResult
	 * @return
	 */
	private SecureRegionPacket assembleResponsePacket(
			SecureRegionPacket requestPacket, PacketHandleResult handlerResult) {
		SecureRegionPacket respRegionPacket = SecureRegionPacket.build();

		// 请求报文对象为null
		if (null != requestPacket) {
			SecureRegionPacketHeader reqHeader = requestPacket.getHeader();

			// 响应报文id结尾加上"-RESP"
			String respPacketId = reqHeader.getPacketId().concat(
					Constant.SYNC_RESP_SUFFIX);

			SecureRegionPacketHeader respHeader = respRegionPacket.getHeader();

			respHeader.setPacketId(respPacketId)
					.setSrcPlatformId(reqHeader.getDestPlatformId())
					.setSrcSubsysId(reqHeader.getDestSubsysId())
					.setDestPlatformId(reqHeader.getSrcPlatformId())
					.setDestSubsysId(reqHeader.getSrcSubsysId())
					.setDestBizCode(reqHeader.getDestBizCode());

			respRegionPacket.getHeader().setPacketId(respPacketId);
		}

		respRegionPacket.getBody().setBodyContent(handlerResult);

		return respRegionPacket;
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		super.channelReadComplete(ctx);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		cause.printStackTrace();
	}
}
