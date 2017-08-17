/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.pos.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import org.apache.commons.lang3.math.NumberUtils;

import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.access.pos.constant.PosRespCode;
import com.gy.hsxt.access.pos.model.BitMap;
import com.gy.hsxt.access.pos.model.Cmd;
import com.gy.hsxt.access.pos.model.PosResp;
import com.gy.hsxt.access.pos.util.PosUtil;

/**
 * 
 * 
 * @Package: com.gy.hsxt.access.pos.handler  
 * @ClassName: EncoderHandler 
 * @Description: pos 请求 响应类 处理返回pos的信息
 *
 * @author: guiyi149 
 * @date: 2015-11-10 上午11:55:11 
 * @version V1.0
 */
public class EncoderHandler extends MessageToByteEncoder<PosResp> {

    public EncoderHandler() {
        super();
    }

	@Override
	protected void encode(ChannelHandlerContext ctx, PosResp resp, ByteBuf out) throws Exception {
		SystemLog.debug("EncoderHandler", "encode()", "entering method");
		Cmd cmd = resp.getCmd();
		byte[] typeId = resp.getRespId();
		byte[] body = resp.getBody();
		
		byte[] tpdu = cmd.getTpdu();
		byte appType = cmd.getAppType();
		byte totalVersion = cmd.getVer();
		byte statusAndProcess = cmd.getStatusAndProcess();
		byte[] partVersion = cmd.getPartVersion();
		

		if (body != null) { 
			try {
				out.writeBytes(tpdu);
				out.writeBytes(new byte[] { appType, totalVersion, statusAndProcess });
				out.writeBytes(partVersion);
				out.writeBytes(typeId);
				out.writeBytes(body);
				
				//求 应答报文的长度
				final String dump = ByteBufUtil.hexDump(out);
				String len = String.format("%x", dump.length() / 2);
				
				SystemLog.debug("EncoderHandler", "encode()","out:" + String.format("%s  %s", len,ByteBufUtil.hexDump(out)));
				
				 BitMap[] bitMaps = PosUtil.unpackReq(body);
				 
				 BitMap bitMap = bitMaps[39];
				 String field39Str = bitMap.getStr();
				 String id = "90" + field39Str;
				 String respMsg = null;
				 if (NumberUtils.isNumber(id))
					 respMsg = PosRespCode.getRespCodeMessageById(Integer.parseInt(id));
				 else
					 respMsg = PosRespCode.getRespCodeMessageById(id);//kend 支持字母
				 respMsg = respMsg == null? "应答码：code=" + id + "不存在。":respMsg;
				 SystemLog.debug("EncoderHandler", "encode()","resp pack field39Str:" +field39Str +",respMsg:"+respMsg);
			} catch (Exception e) {
			    SystemLog.error("EncoderHandler", "encode()",e.getMessage(), e);
			}
		}
	}
}
