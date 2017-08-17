/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.pos.handler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.timeout.ReadTimeoutHandler;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.gy.hsi.lc.client.log4j.SystemLog;

/**
 * 
 * 
 * @Package: com.gy.hsxt.access.pos.handler  
 * @ClassName: PosInitializer 
 * @Description: netty 初始化渠道
 *
 * @author: guiyi149 
 * @date: 2015-11-10 上午11:57:26 
 * @version V1.0
 */
@Service("posInitializer")
public class PosInitializer extends ChannelInitializer<SocketChannel> {

	@Autowired
	@Qualifier("serverHandler")
	private ServerHandler serverHandler;
	
	
//	@Autowired
//	@Qualifier("decoderHandler")
//	private DecoderHandler decoderHandler;
//	
//	@Autowired
//	@Qualifier("encoderHandler")
//	private EncoderHandler encoderHandler;
	
	//modified by kend 10秒改为30秒
	private static final int READ_IDEL_TIME_OUT = 30;

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		SystemLog.debug("PosInitializer", "initChannel()", "entering method");
		final ChannelPipeline pipeline = ch.pipeline();
		
		pipeline.addLast("readTimeoutHandler", new ReadTimeoutHandler(READ_IDEL_TIME_OUT, TimeUnit.SECONDS));

		pipeline.addLast("decoder1", new LengthFieldBasedFrameDecoder(Character.MAX_VALUE, 0, 2, 0, 0));
		pipeline.addLast("decoder2", new DecoderHandler());
		
		pipeline.addLast("encoder1", new LengthFieldPrepender(2));
		pipeline.addLast("encoder2", new EncoderHandler());
		// singleton.
		pipeline.addLast(serverHandler);
	}
}
