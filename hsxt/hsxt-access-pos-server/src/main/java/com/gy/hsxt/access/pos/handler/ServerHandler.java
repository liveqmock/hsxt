/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.pos.handler;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.access.pos.action.IBasePos;
import com.gy.hsxt.access.pos.action.PosEngine;
import com.gy.hsxt.access.pos.constant.PosConstant;
import com.gy.hsxt.access.pos.constant.PosRespCode;
import com.gy.hsxt.access.pos.constant.PosRespCodeMap;
import com.gy.hsxt.access.pos.constant.ReqMsg;
import com.gy.hsxt.access.pos.exception.PosException;
import com.gy.hsxt.access.pos.model.BitMap;
import com.gy.hsxt.access.pos.model.Cmd;
import com.gy.hsxt.access.pos.model.PosReqParam;
import com.gy.hsxt.access.pos.model.PosResp;
import com.gy.hsxt.access.pos.service.UcApiService;
import com.gy.hsxt.access.pos.util.CommonUtil;
import com.gy.hsxt.access.pos.util.PosSeqGenerator;
import com.gy.hsxt.access.pos.util.PosUtil;
import com.gy.hsxt.common.exception.HsException;

/**
 * 
 * 
 * @Package: com.gy.hsxt.access.pos.handler  
 * @ClassName: ServerHandler 
 * @Description: 所有业务的入口类
 *
 * @author: guiyi149 
 * @date: 2015-11-10 上午11:58:10 
 * @version V1.0
 */
@Sharable
@Service("serverHandler")
public class ServerHandler extends SimpleChannelInboundHandler<Cmd> {
	@Autowired
	@Qualifier("posEngine")
	private PosEngine posEngine;
	
    @Autowired
    @Qualifier("ucApiService")
    private UcApiService ucApiService;
    
    //private PosSeqGenerator seq = new PosSeqGenerator();
    @Autowired
	@Qualifier("posSeqGenerator")
	private PosSeqGenerator posSeqGenerator;
    
	public void channelRead0(ChannelHandlerContext ctx, Cmd cmd) throws Exception {
		SystemLog.debug("ServerHandler", "channelRead0()", "entering method");
		
		final String reqId = cmd.getReqId();
		
//		String pointId = DateUtil.DateToString(DateUtil.getCurrentDate(), PosConstant.DATE_FORMAT);
//		String pointId = PosSeqGenerator.getSeq2(reqId); 
		String pointId = posSeqGenerator.getSeq3(reqId, cmd.getPosReqParam().getEntNo());
		cmd.setPointId(pointId);
		
//		SystemLog.debug("ServerHandler","channelRead0()","--------------insert 37 pointId: " + pointId);//kend test
		
		try{
			IBasePos posBus = posEngine.getPosBus(reqId);
			CommonUtil.checkState(null == posBus, "无效的请求", PosRespCode.INVALID_CMD);
			posBus.action(cmd);
		}catch (Exception e){
		    SystemLog.error("ServerHandler","channelRead0()",e.getMessage(), e);
		    if (HsException.class == e.getClass()) {
				final HsException hsEx = (HsException) e;
				int errorCode = hsEx.getErrorCode();
				SystemLog.info("ServerHandler","channelRead0()","错误：pos请求未取得预期结果。互生异常代码：" + errorCode);
				if(null == cmd.getRespCode())
					cmd.setRespCode(PosRespCodeMap.codeConverts(errorCode));
			}else if (PosException.class == e.getClass()) {
				final PosException posEx = (PosException) e;
				SystemLog.debug("ServerHandler","channelRead0()","pos中心应答码：" + posEx.getRspCode());
				cmd.setRespCode(posEx.getRspCode());
			} else{
			    if(null == cmd.getRespCode()){
			        cmd.setRespCode(PosRespCode.POS_CENTER_FAIL);
			    }
			}
		} finally {
		    if(null == cmd.getRespCode()){
                cmd.setRespCode(PosRespCode.SUCCESS);
            }
		    byte[] respId = ReqMsg.getRespIdByReqId(reqId);
			finalExecute(ctx, new PosResp(cmd, respId));
		}
	}


	/**
	 * 后置处理 12,13,37,39,62  排序,64域处理
	 * @author   wucl	
	 * 2015-10-16 上午9:35:48
	 * @param ctx
	 * @param rsp
	 * @throws Exception
	 */
	public void finalExecute(ChannelHandlerContext ctx, PosResp rsp) throws Exception {
		SystemLog.debug("ServerHandler", "finalExecute()", "entering method");
		
		final Cmd cmd = rsp.getCmd();
		
		final List<BitMap> list = cmd.getIn();
		final String reqId = cmd.getReqId();
		final byte[] respId = rsp.getRespId();
		PosReqParam reqParam = cmd.getPosReqParam();
		
		if (PosRespCode.SUCCESS != cmd.getRespCode()) { // 失败
			// 签退|批上送业务	错误没有62域(错误消息),失败情况下必须有应答消息返回
			if (!ReqMsg.BATCHUPLOAD.getReqId().equals(reqId) && !ReqMsg.SIGNOFF.getReqId().equals(reqId)) {
				
				//回写应答描述
//				int respCode = cmd.getRespCode().getCode();
//				final String respMsg = respCode == 0?PosRespCode.getRespCodeMessageById( ((PosRespCode)cmd.getRespCode()).getCodeStr()) : 
//						PosRespCode.getRespCodeMessageById(cmd.getRespCode().getCode());
				
				final String respMsg = ((PosRespCode)cmd.getRespCode()).getMessage();		
				SystemLog.debug("ServerHandler", "finalExecute()", "respMsg:" + respMsg);
				list.add(new BitMap(62, null == respMsg ? StringUtils.EMPTY : respMsg));
			}
		}
		
		if (!ReqMsg.POINTDAYSEARCH.getReqId().equals(reqId) && 
				!ReqMsg.POINTCARDCHECK.getReqId().equals(reqId) && 
				!ReqMsg.SYNCPARAM.getReqId().equals(reqId)) {
			// POS中心流水号
			list.add(new BitMap(37, cmd.getPointId()));
			//SystemLog.debug("ServerHandler","finalExecute()","--------------return 37 pointId" + cmd.getPointId());
		}
		
		
		
		// 应答码，必须有应答码返回，不能抛异常
		String respCode = ((PosRespCode)cmd.getRespCode()).getCodeStr();
		SystemLog.debug("ServerHandler", "finalExecute()", "PosRespCode:" + respCode);
		list.add(new BitMap(39, respCode)); 
		
		// GengLian：由于与之相对应的都是排序好了的，所以，这里不需要排序了。另外，排序的代码也是可以缩减为一行的。return bitMap1.getBit()-bitMap2.getBit();
		Collections.sort(list, new Comparator<BitMap>() { // 从小到大排序
					public int compare(final BitMap bitMap1, final BitMap bitMap2) {
						return bitMap1.getBit() - bitMap2.getBit();
					}
				});
		
		
		byte[] rspBody;

		/**
		 * 添加 64 域，
		 * 当39域响应为 00 时，
		 */
		if (reqParam != null && reqParam.getMacDat() != null 
				&& PosRespCode.SUCCESS == cmd.getRespCode()) { 
			
			// +消息 64 域
			list.add(new BitMap(64, ArrayUtils.EMPTY_BYTE_ARRAY));// 组包更新位图64域，不加其数据
			final byte[] responseBody1 = packResponse(list);
			final int responseBodyLen1 = responseBody1.length;
			
			byte[] macDataByte = new byte[respId.length + responseBody1.length];
            
            int countk = 0;
            System.arraycopy(respId, 0, macDataByte, countk, respId.length);
            
            countk += respId.length;
            System.arraycopy(responseBody1, 0, macDataByte, countk, responseBody1.length);
			
			// 注意，每天签到后生成的报文mac才能校验成功
			byte[] newMAC = ucApiService.genMac(reqParam.getEntNo(), reqParam.getPosNo(), macDataByte);
			
			CommonUtil.checkState(newMAC.length == 0, "getMac没返回，注意业务输入参数", PosRespCode.POS_CENTER_FAIL);
			
			final int newMACLen = newMAC.length;
			rspBody = new byte[responseBodyLen1 + newMACLen]; // 合并64域
			
			System.arraycopy(responseBody1, 0, rspBody, 0, responseBodyLen1);
			System.arraycopy(newMAC, 0, rspBody, responseBodyLen1, newMACLen);
		} else {
			rspBody = packResponse(list);
		}
		rsp.setBody(rspBody);

		ctx.writeAndFlush(rsp);
	}
	
	
	/**
	 * 打包响应包,不包括消息类型
	 * @author   wucl	
	 * 2015-10-16 上午9:35:27
	 * @param list
	 * @return
	 */
	public byte[] packResponse(List<BitMap> list) {
		SystemLog.debug("ServerHandler", "packResponse()", "entering method");
		
		final ByteBuffer bb = (ByteBuffer) ByteBuffer.allocate(2000).position(Byte.SIZE);
		final boolean[] bbitMap = new boolean[PosConstant.BIT_MAP_LENGTH]; // 没有扩展位图
		
		// bbitMap[1] = false; // 第一位为false，没有扩展位图 // 2015/03/28 this line is redundant.
		for (int j = 0, size = list.size(); j < size; j++) {
			final BitMap bitMap = (BitMap) list.get(j);
			
			// 组装位图
			bbitMap[bitMap.getBit()] = true;
			
			// 64域特殊处理，只更新位图
			if (bitMap.getBit() == 64 && bitMap.getBytes() == null) continue;
			
			// 补每个域长度数据，组装body
			if (bitMap.getVar() > 0) {//变长
				
				// 数据是可变长的:拼变长的值
				final int dataLen = (bitMap.isBcd() == 1) ? bitMap.getLen() : bitMap.getBytes().length;
				final byte[] varValue = PosUtil.strToBCDBytes(StringUtils.leftPad(String.valueOf(dataLen),
						bitMap.getVar(), PosConstant.ZERO_CHAR));
			
				bb.put(varValue);
			
			}
			bb.put(bitMap.getBytes());
		}
		final int len = bb.position();
		final byte[] bs = new byte[len];
		bb.position(0);
		
		((ByteBuffer)bb.put(PosUtil.getByteFromBinary(bbitMap)).rewind()).get(bs);
		
		return bs;
	}
	

}
