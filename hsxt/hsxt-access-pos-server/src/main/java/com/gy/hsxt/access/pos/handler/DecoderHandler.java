/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.pos.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.math.BigInteger;
import java.util.List;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringUtils;

import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.access.pos.model.BitMap;
import com.gy.hsxt.access.pos.model.Cmd;
import com.gy.hsxt.access.pos.util.PosUtil;

/**
 * 
 * @Package: com.gy.hsxt.access.pos.handler  
 * @ClassName: DecoderHandler 
 * @Description: 处理pos 机请求解析类
 *
 * @author: guiyi149 
 * @date: 2015-11-10 上午11:50:52 
 * @version V1.0
 */
public class DecoderHandler extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> outList) throws Exception {
    	SystemLog.debug("DecoderHandler", "decode()", "entering method");
    	
        if (!in.isReadable()) {
            return;
        }

        SystemLog.info("DecoderHandler", "decode()","解析报文:" + ByteBufUtil.hexDump(in));
        
        //仅用于报文结构跟踪测试  kend test
//        StringBuffer sb = new StringBuffer();
//        StringBuffer s2 = new StringBuffer();
//        StringBuffer s16 = new StringBuffer();
//        byte[] b = new byte[in.readableBytes()];
//        int i = -1;
//        byte[] bb = new byte[1];
//        while(in.isReadable()) {
//        	b[++i] = in.readByte(); 
//        	sb.append(b[i]+",");
//        	bb[0] = b[i];
//        	s2.append(new BigInteger(1, bb).toString(2) + ",");
//        	s16.append(new BigInteger(1, bb).toString(16) + ",");
//        }
//        SystemLog.debug("DecoderHandler", "decode()","解析报文 十六进制:" + s16.toString());
//        SystemLog.debug("DecoderHandler", "decode()","解析报文byte(十进制):" + sb.toString());
//        SystemLog.debug("DecoderHandler", "decode()","解析报文 二进制:" + s2.toString());
        
        
        //报文长度
        final byte[] len = new byte[2];
        in.readBytes(len, 0, 2);

        
        final byte[] tpdu = new byte[5];
        in.readBytes(tpdu, 0, 5);
        
        //报文头
        //应用类别定义
        final byte appType = in.readByte();
       
        //软件总版本号
        final byte totalVersion = in.readByte();
       
        //终端 状态+处理请求
        final byte statusAndProcess = in.readByte();
       
        //软件分版本号
        final byte[] partVersion = new byte[3];
        final byte[] typeId = new byte[2];
        in.readBytes(partVersion, 0, 3).readBytes(typeId, 0, 2);
       
        //	复位消息类型ID
        //body不含有消息类型ID
        final int bodySize = in.readableBytes();
        final byte[] body = new byte[bodySize];
        
        in.readBytes(body, 0, bodySize);
        SystemLog.debug("DecoderHandler", "decode()","解析报文,req typeId+body:" + Hex.encodeHexString(typeId) + Hex.encodeHexString(body));
        
        Cmd cmd = new Cmd(tpdu, appType, totalVersion, statusAndProcess, partVersion, typeId, body);
        
        //构造唯一请求id
        final StringBuilder reqId = new StringBuilder(12).append(Hex.encodeHexString(typeId));
        
        BitMap[] bitMaps = PosUtil.unpackReq(body);
        
        //请求指令最多由这三部分组成，第一部分必须，后两部分（3域，60.1域）可选
        if (null != bitMaps[3] && StringUtils.isNotEmpty(bitMaps[3].getStr()))
            reqId.append(bitMaps[3].getStr());
        if (null != bitMaps[60] && bitMaps[60].getStr().length() > 2)
            reqId.append(bitMaps[60].getStr().substring(0, 2));
         
        
        cmd.setBitMaps(bitMaps);
        cmd.setReqId(reqId.toString());
        //构造公共返回pos 数据
        PosUtil.packPubResp(cmd);
        //解析请求包，得到请求业务数据
        PosUtil.unpackReq(cmd);
        
        outList.add(cmd);
    }

}
