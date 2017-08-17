/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.pos.util;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.text.StrBuilder;

import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.access.pos.constant.PosConstant;
import com.gy.hsxt.access.pos.constant.PosRespCode;
import com.gy.hsxt.access.pos.constant.ReqMsg;
import com.gy.hsxt.access.pos.exception.PosException;
import com.gy.hsxt.access.pos.model.BitMap;
import com.gy.hsxt.access.pos.model.Cmd;
import com.gy.hsxt.access.pos.model.PosReqParam;
import com.gy.hsxt.access.pos.model.TradeType;
import com.gy.hsxt.common.constant.Channel;
import com.gy.hsxt.common.constant.CommonConstant;
import com.gy.hsxt.common.utils.DateUtil;



/**
 *  
 * @Package: com.gy.hsxt.access.pos.util  
 * @ClassName: PosUtil 
 * @Description: 针对 pos 请求 的公具类
 *
 * @author: wucl 
 * @date: 2015-11-10 上午11:53:14 
 * @version V1.0
 */
public class PosUtil {
	
	
	/**
	 * 解析请求包 获取位图信息
	 *
	 * @param body
	 */
	public static BitMap[] unpackReq(byte[] body) throws Exception {
		SystemLog.debug("PosUtil", "unpackReq(byte[])", "entering method");
		
		final BitMap[] outMap = new BitMap[PosConstant.BIT_MAP_LENGTH];
		
		// 取得位图   目前定义只有第一位图
		final byte[] map = new byte[Byte.SIZE];
		
		//取得位图的初始位置。
		System.arraycopy(body, 0, map, 0, Byte.SIZE);

		final char[] bmap = getBinaryFromByte(map);
		SystemLog.debug("PosUtil", "unpackReq(byte[] body)", "bmap:"+ new String(bmap));//kend test
		
		int le0 = Byte.SIZE, len, datLen, bcdLen;
		byte[] next, var;
		
		for (int i = 2; i < PosConstant.BIT_MAP_LENGTH; i++) {
			if ('1' == bmap[i]) {
	
				// 取出变长部分的值。
				int variable = BitMap.CONFIG[i][1];
				if (variable > 0) {//是变长
					
					var = new byte[--variable];
					System.arraycopy(body, le0, var, 0, var.length);
					
					len = datLen = bcdToInt(var);
					
					//bcd压缩 每个字节存储2位数字
					if (BitMap.CONFIG[i][4] == 1) {
						datLen = (len + 1) >> 1;
					}
					
					le0 += variable;//跳过变长中长度
					
					// 取出变长部分后带的值。
					next = new byte[datLen];
					System.arraycopy(body, le0, next, 0, datLen);
					
					SystemLog.debug("PosUtil", "unpackReq(byte[] body)", "域位i: " + i + 
								" ; 数据长datLen: " + datLen + " ; 内容next: " + Arrays.toString(next));//kend temptest
					
					le0 += datLen;
				} else {
					len = BitMap.CONFIG[i][2];
					if (BitMap.CONFIG[i][4] == 1) {//bcd
						bcdLen = (len + 1) >> 1;
					} else {//ascii
						bcdLen = len;
					}
					System.arraycopy(body, le0, next = new byte[bcdLen], 0, bcdLen);
					
					SystemLog.debug("PosUtil", "unpackReq(byte[] body)", "域位i: " + i + 
							"; 数据长bcdLen: " + bcdLen + " ; 内容next: " + Arrays.toString(next));//kend temptest
				
					le0 += bcdLen;
				}
				
				outMap[i] = new BitMap(next, i, len);
				
			}
		}
		return outMap;
	}
	
	/**
	 * 获取请求数据
	 */
	public static void unpackReq(Cmd cmd) throws Exception {
		SystemLog.debug("PosUtil", "unpackReq(Cmd)", "entering method");
		
		final BitMap[] model = cmd.getBitMaps();
		
		final byte[] partVersion = cmd.getPartVersion();
		final String reqId = cmd.getReqId();

		String posRunCode = model[11].getStr();
		String posNo = model[41].getStr();
		String entNo = model[42].getStr();
		String operNo = null == model[63] ? null : model[63].getStr(partVersion);
		
		String inputWayAndPin = null == model[22] ? null : model[22].getStr();
		final String inputWay = null == inputWayAndPin ? null : inputWayAndPin.substring(0, 2);
		final String isPin = null == inputWayAndPin ? null : inputWayAndPin.substring(2);
		
		String cardNo = null == model[2] ? null : model[2].getStr();
		String termTradeCode = null == model[3] ? null : model[3].getStr();
		
		BigDecimal transAmount = null == model[4] ? null : (BigDecimal)(model[4].getObject());
		
		String termServiceCode = null == model[25] ? null : model[25].getStr();
		String servicePinLen = null == model[26] ? null : model[26].getStr();
		
		String stripe2 = null == model[35] ? null : model[35].getStr();
		//不为空时为原交易订单流水号
		String originalNo = null == model[37] ? null : model[37].getStr();
		String reverseCode = null == model[39] ? null : model[39].getStr();
		Object gyBean = null == model[48] ? null : model[48].getObject(reqId, partVersion);
		String currency = null == model[49] ? null : model[49].getStr();
		byte[] pin = null == model[52] ? null : model[52].getBytes();
		
		// 服务点输入方式码 
		TradeType tradeTypeInfoBean = null == model[60] ? null : (TradeType) model[60].getObject(reqId); 
		Object custom2 = null == model[62] ? null : model[62].getObject62(reqId, partVersion);
		byte[] macDat = null == model[64] ? null : model[64].getBytes();
		
		String tradeTime = null == model[12]?null :model[12].getStr();
		String tradeDate = null == model[13]?null :model[13].getStr();
		Timestamp tradeTimestamp = CommonUtil.getPosReqTime(tradeDate, tradeTime);
		
		//设备校验数据
		PosReqParam reqParam = new PosReqParam();
		
		reqParam.setCardNo(cardNo);
		reqParam.setCurrencyCode(currency);
		reqParam.setCustom2(custom2);
		reqParam.setEntNo(entNo);
		reqParam.setGyBean(gyBean);
		reqParam.setInputWay(inputWay);
		reqParam.setIsPin(isPin);
		reqParam.setMacDat(macDat);
		reqParam.setPinDat(pin);
		reqParam.setPosNo(posNo);
		reqParam.setPosRunCode(posRunCode);
		reqParam.setReverseCode(reverseCode);
		reqParam.setServicePinLen(servicePinLen);
		reqParam.setStripe2(stripe2);
		reqParam.setTermServiceCode(termServiceCode);
		reqParam.setTermTradeCode(termTradeCode);
		reqParam.setTradeTypeInfoBean(tradeTypeInfoBean);
		reqParam.setTransAmount(null == transAmount ? new BigDecimal(0) : transAmount);
		reqParam.setOperNo(operNo);
		reqParam.setChannelType(Channel.POS.getCode());
		reqParam.setOriginalNo(originalNo);
		reqParam.setTradeTime(tradeTime);
		reqParam.setTradeDate(tradeDate);
		reqParam.setTradeTimestamp(tradeTimestamp);

		cmd.setPosReqParam(reqParam);
	}
	
	
	
	/**
	 * 公共域应答域封装（必须提前返回指定域）响应报文中要求有值且能提前确定的，先在这里组装好。
	 * 2,3,4,11,12,13,25,32,41,42,49,60
	 */
	public static void packPubResp(Cmd cmd) throws Exception {
		SystemLog.debug("PosUtil", "packPubResp(Cmd)", "entering method");
		final List<BitMap> list = cmd.getIn();
		
		final String reqId = cmd.getReqId();
		final BitMap[] model = cmd.getBitMaps();
		
		// 主账号
		if (model[2] != null) list.add(model[2]); 
		
		// 交易处理码
		if (model[3] != null) list.add(model[3]); 
		
		// 积分直接返回 交易金额
		if (model[4] != null) list.add(model[4]); 
		
		// POS终端交易流水号
		list.add(model[11]); 
		
		// 不是同步参数
		if (!ReqMsg.SYNCPARAM.getReqId().equals(reqId)) { 
			// 单笔查询业务没有12,13域,必须有时间返回,不能抛异常
			if (!ReqMsg.POINTORDERSEARCH.getReqId().equals(reqId) && !ReqMsg.ASSURESEARCH.getReqId().equals(reqId)
					&& !ReqMsg.CARDNAMESEARCH.getReqId().equals(reqId) && !ReqMsg.HSORDERSEARCH.getReqId().equals(reqId)) { 
				
				final Timestamp ts = new Timestamp(System.currentTimeMillis());
				//受卡方所在地时间 HHmmss
				list.add(new BitMap(12, ts)); 
				//受卡方所在地日期 MMDD
				list.add(new BitMap(13, ts)); 
			}
			
			//不是上传参数
			if (!ReqMsg.UPLOADPARAM.getReqId().equals(reqId)) {
				//将42域的受卡方标识码（商户代码）写入32域的受理方标识码
				list.add(new BitMap(32,cmd.getBitMaps()[42].getStr())); 
			}
		}
		
		//服务点条件码 普通余额查询填写00，联盟积分查询填写65。暂定直接返回
		if (model[25] != null) list.add(model[25]); 
		
		// POS终端号
		list.add(model[41]); 
		
		//受卡方标识码、 商户号(企业管理号)
		list.add(model[42]); 
		
		// 货币代号 M 给的什么返回什么
		if (model[49] != null) list.add(model[49]);
		
		
		if (!ReqMsg.BATCHUPLOAD.getReqId().equals(reqId)) { 
			//自定义域，消息类型码(2)+批次号(6)+网络管理信息码(3)
			if (model[60] != null) list.add(model[60]);
		}
	}
	
	
	 /**
     * 将一个16字节数组转成128二进制数组
     * GengLian modified at 2014/10/31
     * @param bs 肯定是8bytes。
     */
    public static char[] getBinaryFromByte(byte[] bs) {
        final StrBuilder buf = new StrBuilder(PosConstant.BIT_MAP_LENGTH).append('0');
        for (int i = 0; i < Byte.SIZE; i++) {
            buf.append(getEigthBitsStringFromByte(bs[i]));
        }
        return buf.toCharArray();
    }
    
    
    /**
     * 这个可以代替旧的getEigthBitsStringFromByte()了。
     * GengLian added at 2015/02/27
     */
    public static String getEigthBitsStringFromByte(int b) {
        final String str = Integer.toBinaryString(b);
        final int len = str.length();
        
        if (len >= Byte.SIZE) {
            return str.substring(len - Byte.SIZE, len);
        } else {
            return StringUtils.leftPad(str, Byte.SIZE, '0');
        }
    }
    
    /**
     * 将BCD码转成int
     *
     * @param b
     * @return
     */
    public static int bcdToInt(byte[] b) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < b.length; i++) {
            int h = ((b[i] & 0xff) >> 4) + PosConstant.NUMBER_ASCII_DISTANCE;
            sb.append((char) h);
            int l = (b[i] & 0x0f) + PosConstant.NUMBER_ASCII_DISTANCE;
            sb.append((char) l);
        }
        return Integer.parseInt(sb.toString());
    }
    
    /**
     * 将String转成BCD码
     *
     * @param s
     * @return
     */
    public static byte[] strToBCDBytes(String s) {
        if ((s.length() & 1) != 0) {
            s = '0' + s;
        }
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        final char[] cs = s.toCharArray();
        for (int i = 0; i < cs.length; i += 2) {
            int high = cs[i] - PosConstant.NUMBER_ASCII_DISTANCE;
            int low = cs[i + 1] - PosConstant.NUMBER_ASCII_DISTANCE;
            baos.write(high << 4 | low);
        }
        return baos.toByteArray();
    }
    
    
    /**
     * 将一个128二进制数组转成16字节数组
     *
		final BitSet bitSet = new BitSet(BIT_MAP_LENGTH - 1);
		for (int i = 1; i < BIT_MAP_LENGTH; i++) {
		    if(binary[i]) {
		        bitSet.set(i);}}
		return bitSet.toByteArray(); // it is wrong. 2015/02/27
     * @param arr
     * @return
     */
    public static byte[] getByteFromBinary(boolean[] arr) {
		final byte[] bs = new byte[Byte.SIZE];
		final int len = PosConstant.BIT_MAP_LENGTH - 1;
		final StringBuilder buf = new StringBuilder(len);
		for (int i = 1; i < PosConstant.BIT_MAP_LENGTH; i++) {
		    buf.append(arr[i] ? '1' : '0');
		}
		for (int i = 0, arrayIdx = 0; i < len; i += 8) {
		    bs[arrayIdx++] = getByteFromEigthBitsString(buf.substring(i, i + 8));
		}
		return bs;
    }
    
    
    /**
     * @param str
     */
    private static byte getByteFromEigthBitsString(String str) {
        byte b;
        if ('1' == str.charAt(0)) {
            b = Byte.parseByte(str.substring(1), 2);
            b |= 128;
        } else {
            b = Byte.parseByte(str, 2);
        }
        return b;
    }
    
    /**
     * 字节异或处理。用于代替旧的uniteBytes()
     * http://baike.baidu.com/link?url=WqOrDvlL7zp_lixGJw9ZPjIM0K0uYZxbsHbZBQ3UeY6nKaTa7wkWcxE9F7XkAHZSB0w9Ks7agBATHZLK00kjW_
     * // 把这两句放入原来的uniteBytes()里，没有报错，证明了2个输入值都是数字（ASCII 57以内）。
     * if(src0 > 57 || src1 > 57) {
     * throw new RuntimeException("cccccccccccccccccccc");
     * }
     */
    public static byte uniteBytes(byte src0, byte src1) {
		return (byte) (((src0 - PosConstant.NUMBER_ASCII_DISTANCE) << 4) ^ (src1 - PosConstant.NUMBER_ASCII_DISTANCE));
    }
    
	/**
	 * 获取pan,归一定义，右边数第二位开始，向左取10位
	 * it comes from ANSIFormat 归一个人标识码（PIN）的加密和解密（huangfh）
	 */
	private static byte[] getHAccno(String accno) {
		final byte encode[] = new byte[8];
		final int len = accno.length();
		
		final byte arrAccNo[] = org.apache.commons.codec.binary.StringUtils
				.getBytesUtf8(accno.substring(len < 11 ? 0 : len - 11, len - 1));

		encode[3] = uniteBytes(arrAccNo[0], arrAccNo[1]);
		encode[4] = uniteBytes(arrAccNo[2], arrAccNo[3]);
		encode[5] = uniteBytes(arrAccNo[4], arrAccNo[5]);
		encode[6] = uniteBytes(arrAccNo[6], arrAccNo[7]);
		encode[7] = uniteBytes(arrAccNo[8], arrAccNo[9]);
		return encode;
	}

    
    /**
	 * 例子[6, 17, 17, 23, -25, -97, -17, -99] 06186010626      密码111111
	 * 输入归一自定义ANSI X9.8 Format PIN和卡号，逆向生成明文密码
	 * 校验报文pin密码长度
	 * it comes from ANSIFormat 归一个人标识码（PIN）的加密和解密（huangfh）
	 *
	 * @param arrGyPin
	 * @param accNo         不能有暗码
	 * @param servicePinLen
	 * @return
     * @throws PosException 
	 */
	public static String decryptWithANSIFormat(byte[] arrGyPin, String accNo, String servicePinLen) throws PosException {
		
		accNo = StringUtils.left(accNo, 11);
		final byte arrAccNo[] = getHAccno(accNo);
		final byte arrPin[] = new byte[8];
		for (int i = 0; i < 8; i++) {
			arrPin[i] = (byte) (arrGyPin[i] ^ arrAccNo[i]);
		}
		final int pinLen = arrPin[0];
		final String strPin = new String(Hex.encodeHex(arrPin));
		
		if (StringUtils.isNotEmpty(servicePinLen)) {
			final int pinLen1 = Integer.parseInt(servicePinLen);
			
			CommonUtil.checkState(pinLen > pinLen1, "密钥解密输出pin长度:" + pinLen
					+ ",报文pin最大长度：" + pinLen1, PosRespCode.REQUEST_PACK_FORMAT);
		}
		return strPin.substring(2, 2 + pinLen);
	}
	
	/**
	 * 输入密码明文和卡号生成 XOR Result（归一自定义ANSI X9.8 Format PIN）
	 * it comes from ANSIFormat 
	 * @param pin （明文）归一个人标识码（PIN）的加密和解密（huangfh）
	 * @param accno  卡号
	 * 2015/02/27 只有TC用了这个，正规的src不用这个。
	 */
	public static byte[] encryptWithANSIFormat(String pin, String accno) {
		byte arrPin[] = getHPin(pin);
		byte arrAccno[] = getHAccno(accno);
		byte arrGyPin[] = new byte[8];
		// PIN BLOCK 格式等于 PIN 按位异或 主帐号;
		for (int i = 0; i < 8; i++) {
			arrGyPin[i] = (byte) (arrPin[i] ^ arrAccno[i]);
		}
		return arrGyPin;
	}
	/**
	 * pin加密长度固定6位   新版本互生支付有8位交易密码
	 * it comes from ANSIFormat 归一个人标识码（PIN）的加密和解密（huangfh）
	 *
	 * @param pin
	 * @return
	 */
	private static byte[] getHPin(String pin) {
		final byte encode[] = new byte[8];
		final byte arrPin[] = pin.getBytes();
		final int len = arrPin.length;
		encode[0] = (byte) (len & 0xff);
		encode[1] = CommonUtil.uniteBytes(arrPin[0], arrPin[1]);
		encode[2] = CommonUtil.uniteBytes(arrPin[2], arrPin[3]);
		encode[3] = CommonUtil.uniteBytes(arrPin[4], arrPin[5]);
		encode[4] = (len == 7 || len == 8) ? CommonUtil.uniteBytes(arrPin[6], arrPin[7]) : NumberUtils.BYTE_MINUS_ONE;
		encode[5] = (len == 9 || len == 10) ? CommonUtil.uniteBytes(arrPin[8], arrPin[9]) : NumberUtils.BYTE_MINUS_ONE;
		encode[6] = encode[7] = NumberUtils.BYTE_MINUS_ONE;
		return encode;
	}
	
	/**
	 * 获取当前时间
	 *
	 * @param datetime
	 * @return HHmmss
	 */
	public static String getFormatHms(Timestamp datetime) {
		return new SimpleDateFormat(DateUtil.DATE_FORMAT_HHmmss).format(datetime);
	}
	
}
