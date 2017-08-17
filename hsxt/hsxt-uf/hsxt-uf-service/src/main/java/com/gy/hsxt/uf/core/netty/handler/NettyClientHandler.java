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

import com.gy.hsxt.uf.bean.packet.PacketHandleResult;
import com.gy.hsxt.uf.common.bean.packet.SecureRegionPacket;
import com.gy.hsxt.uf.common.exception.DecryptException;
import com.gy.hsxt.uf.common.exception.UnsignException;
import com.gy.hsxt.uf.core.dubbo.transfer.PacketSecurityDog;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-uf-service
 * 
 *  Package Name    : com.gy.hsxt.uf.core.netty.handler
 * 
 *  File Name       : NettyClientHandler.java
 * 
 *  Creation Date   : 2015-9-24
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 综合前置Client端异步消息处理类, 用于异步接收并处理Netty Server端反馈的报文
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class NettyClientHandler extends ChannelInboundHandlerAdapter {
	private final Logger logger = Logger.getLogger(this.getClass());

	private PacketHandleResult result = null;

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		super.channelActive(ctx);
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		super.channelInactive(ctx);
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object respMsg)
			throws Exception {
		if (null != respMsg) {
			// 接收响应结果对象
			byte[] respBytes = (byte[]) respMsg;

			try {
				// 对响应报文进行解密、解签处理
				SecureRegionPacket respPacket = PacketSecurityDog.getInstance()
						.decryptAndUnsign(respBytes);

				// 取出响应报文中的结果对象
				Object respContent = respPacket.getBody().getBodyContent();

				if (null != respContent) {
					this.result = (PacketHandleResult) respContent;
				}
			} catch (DecryptException | UnsignException e) {
				logger.error("Netty client对响应报文进行解密、解签失败！", e);

				this.exceptionCaught(ctx, e);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);

				this.exceptionCaught(ctx, e);
			}
		}
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		super.channelReadComplete(ctx);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		this.result = new PacketHandleResult(cause);

		cause.printStackTrace();
	}

	/**
	 * 返回处理结果
	 * 
	 * @return
	 */
	public PacketHandleResult getResult() {
		return this.result;
	}
}