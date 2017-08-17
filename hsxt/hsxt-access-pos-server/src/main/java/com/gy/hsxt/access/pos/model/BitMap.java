/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.pos.model;

import org.apache.commons.codec.binary.Hex;

import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.access.pos.constant.PosConstant;
import com.gy.hsxt.access.pos.constant.ReqMsg;



/**
 * @Package: com.gy.hsxt.access.pos.model  
 * @ClassName: BitMap 
 * @Description: 
 *
 * @author: wucl 
 * @date: 2015-11-10 下午3:02:12 
 * @version V1.0
 */
public class BitMap {

	static final Afd[] arr = new Afd[PosConstant.BIT_MAP_LENGTH - 1];
	
	public static final Afd EMPTY_AFD = new Afd();

	/**
	 * 存放每个域的配置信息 
	 * [][0] bit 位:在Map中的位 
	 * [][1] variable 0非变长,2位变长LL,3位变长LLL 
	 * [][2] len长度:(对定长有效) 
	 * [][3] type类型:0 n数字,1 an,2 ans,3 a,4 binary 
	 * [][4] BCD :0 表示不使用bcd压缩,1 表示使用bcd压缩， 
	 * [][5] align :0 左补0, 1 右补0
	 */
	public static final int[][] CONFIG = { 
			{ 0, 0, 0, 0, 0, 0 }, 
			{ 1, 0, 16, 4, 0, 0 },
			{ 2, 2, 19, 0, 1, 1 },
			{ 3, 0, 6, 0, 1, 0 }, 
			{ 4, 0, 12, 0, 1, 0 }, 
			{ 5, 0, 12, 0, 0, 0 }, 
			{ 6, 0, 12, 0, 0, 0 },
			{ 7, 0, 10, 0, 0, 0 }, 
			{ 8, 0, 8, 0, 0, 0 }, 
			{ 9, 0, 8, 0, 0, 0 }, 
			{ 10, 0, 8, 0, 0, 0 },
			{ 11, 0, 6, 0, 1, 0 }, 
			{ 12, 0, 6, 0, 1, 0 }, 
			{ 13, 0, 4, 0, 1, 0 }, 
			{ 14, 0, 4, 0, 1, 0 },
			{ 15, 0, 4, 0, 1, 0 }, 
			{ 16, 0, 4, 0, 0, 0 }, 
			{ 17, 0, 4, 0, 0, 0 }, 
			{ 18, 3, 4, 0, 0, 0 },
			{ 19, 0, 3, 0, 0, 0 }, 
			{ 20, 0, 3, 0, 0, 0 }, 
			{ 21, 0, 3, 0, 0, 0 }, 
			{ 22, 0, 3, 0, 1, 1 },
			{ 23, 0, 3, 0, 0, 0 }, 
			{ 24, 0, 3, 0, 0, 0 }, 
			{ 25, 0, 2, 0, 1, 0 }, 
			{ 26, 0, 2, 0, 1, 0 },
			{ 27, 0, 1, 0, 0, 0 }, 
			{ 28, 0, 8, 0, 0, 0 }, 
			{ 29, 0, 8, 0, 0, 0 }, 
			{ 30, 0, 8, 0, 0, 0 },
			{ 31, 0, 8, 0, 0, 0 }, 
			{ 32, 2, 11, 0, 1, 1 },
			{ 33, 2, 11, 0, 0, 0 }, 
			{ 34, 2, 28, 0, 0, 0 }, 
			{ 35, 2, 37, 0, 1, 1 },
			{ 36, 3, 104, 0, 0, 1 },
			{ 37, 0, 12, 1, 0, 0 }, 
			{ 38, 0, 6, 1, 0, 0 }, 
			{ 39, 0, 2, 1, 0, 0 }, 
			{ 40, 0, 3, 1, 0, 0 },
			{ 41, 0, 8, 2, 0, 0 }, 
			{ 42, 0, 15, 2, 0, 0 }, 
			{ 43, 0, 40, 2, 0, 0 }, 
			{ 44, 2, 25, 1, 0, 0 },
			{ 45, 2, 76, 1, 0, 0 }, 
			{ 46, 3, 999, 1, 0, 0 }, 
			{ 47, 3, 999, 1, 0, 0 },
			{ 48, 3, 999, 1, 1, 1 },
			{ 49, 0, 3, 3, 0, 0 }, 
			{ 50, 0, 3, 1, 0, 0 }, 
			{ 51, 0, 3, 3, 0, 0 }, 
			{ 52, 0, 8, 4, 0, 0 },
			{ 53, 0, 16, 0, 1, 0 }, 
			{ 54, 3, 120, 1, 0, 0 }, 
			{ 55, 3, 999, 2, 0, 0 }, 
			{ 56, 3, 999, 2, 0, 0 },
			{ 57, 3, 999, 2, 0, 0 }, 
			{ 58, 3, 999, 2, 0, 0 }, 
			{ 59, 3, 999, 2, 0, 0 },
			{ 60, 3, 999, 2, 1, 1 },
			{ 61, 3, 999, 2, 1, 0 }, 
			{ 62, 3, 999, 2, 0, 0 }, 
			{ 63, 3, 999, 2, 0, 0 }, 
			{ 64, 0, 8, 4, 0, 0 }, };
	/**
	 * 位
	 */
	private int bit;
	/**
	 * 格式：0非变长,2位变长LL,3位变长LLL
	 */
	private int var;
	/**
	 * 数据长度
	 */
	private int len;
	/**
	 * 属性:0 n数字,1 an,2 ans,3 a,4 binary
	 */
	private int bitType;
	/**
	 * 0 表示不使用bcd压缩,1 表示使用bcd压缩，
	 */
	private int bcd;
	/**
	 * 0 左补0, 1 右补0
	 */
	private int align;
	
	/**
	 * response的数据
	 */
	private byte[] bytes;
	
	/**
	 * request得到的数据
	 */
	private String str;

	
	public static void init() {
		arr[4] = new Fd04();
		arr[11] = new Fd11();
		arr[12] = new Fd12();
		arr[13] = new Fd13();
		arr[22] = new Fd22();
		arr[32] = new Fd32();
		arr[35] = new Fd35();
		arr[37] = new Fd37();
		arr[39] = new Fd39();
		arr[41] = new Fd41();
		arr[42] = new Fd42();
		arr[48] = new Fd48();
		arr[60] = new Fd60();
		arr[62] = new Fd62();
		arr[63] = new Fd63();
		arr[2] = arr[3] = arr[15] = arr[25] = arr[26] = arr[49] = arr[52] = arr[53] = EMPTY_AFD;
	}

	public BitMap(byte[] da, int bit, int len) {
		this(bit, da);
		this.align = CONFIG[bit][5];
		this.len = len;
	}

	private void init(int bit) {
		this.bit = bit;
		this.var = CONFIG[bit][1]; //variable 0非变长,2位变长LL,3位变长LLL
		this.len = str == null ? CONFIG[bit][2] : str.length();
		this.bitType = CONFIG[bit][3];
		this.bcd = CONFIG[bit][4];
	}

	/**
	 * 签到业务62域获取密钥对，64域使用
	 */
	public BitMap(int bit, byte[] bs) {
		// 初始化
		this.init(bit);
		this.bytes = bs;
	}

	/**
	 * 直接获取应答数据，不需要参数 32应答域处理
	 * 初始化  应答域处理
	 */
	public BitMap(int bit) throws Exception {
		this.init(bit);
		this.rsp2Bytes(arr[bit].doResponseProcess());
	}
	

	/**
	 * 暂时都没处理数据，待优化 3,15,37,39,49,62，63应答域处理
	 * 消费者互生号写入2域 kend
	 */
	public BitMap(int bit, Object data) throws Exception {
		SystemLog.debug("BitMap", "BitMap()", "bit:" + bit + ";data:" + data);
		this.init(bit);
		this.rsp2Bytes(arr[bit].doResponseProcess(data));
	}

	/**
	 * 冲正    3应答域处理
	 */
	public BitMap(int bit, String messageId, Object data) throws Exception {
		this.init(bit);
		this.rsp2Bytes(arr[bit].doResponseProcess(messageId, data));
	}

	/**
	 * 48,63应答域处理
	 */
	public BitMap(int bit, String messageId, Object data, byte[] pversion) throws Exception {
		this.init(bit);
		this.rsp2Bytes(arr[bit].doResponseProcess(messageId, data, pversion));
	}

	/**
	 * 应答4,62域处理
	 */
	public BitMap(int bit, String messageId, Object data, Object extend, byte[] pversion) throws Exception {
		this.init(bit);
		this.rsp2Bytes(arr[bit].doResponseProcess(messageId, data, extend, pversion));
	}

	/**
	 * 4,22使用
	 */
	public Object getObject() throws Exception {
		this.bytes2Str(); // 是bcd，不需要补零
		return arr[bit].doRequestProcess(null, str);
	}

	/**
	 * 60域使用
	 */
	public Object getObject(String messageId) throws Exception {
		this.bytes2Str();
		return arr[bit].doRequestProcess(messageId, str);
	}

	/**
	 * 4,48域使用
	 */
	public Object getObject(String messageId, byte[] partVersion) throws Exception {
		SystemLog.debug("BitMap", "getObject()", "开始提取48域数据。" );
		
		if (ReqMsg.POINT.getReqId().equals(messageId) || 
			ReqMsg.HSBPAY.getReqId().equals(messageId) ||
			ReqMsg.EARNESTPREPAY.getReqId().equals(messageId) ||
			ReqMsg.EARNESTSETTLEACC.getReqId().equals(messageId)) {
			return arr[bit].doRequestProcess(messageId, bytes, partVersion);
		} else {
			this.bytes2Str();
			return arr[bit].doRequestProcess(messageId, str, partVersion);
		}
	}
	
	/**
	 * 62域使用
	 */
	public Object getObject62(String messageId, byte[] partVersion) throws Exception {
		SystemLog.debug("BitMap", "getObject62()", "开始提取62域数据。");
		
		this.bytes2Str();
		return arr[bit].doRequestProcess(messageId, str, partVersion);
		
	}

	/**
	 * 获取字符串数据
	 */
	public String getStr() throws Exception {
		if (str == null) {
			this.bytes2Str();
			if (bitType != 4) { // bcd/ascii
				str = arr[bit].doRequestProcess(str);
			} else { // binary
				str = Hex.encodeHexString(bytes);
			}
		}
		return str;
	}

	/**
	 * 63域 获取字符串数据
	 */
	public String getStr(byte[] partVersion) throws Exception {
		if (str == null) {
			this.bytes2Str();
			str = arr[bit].doRequestProcess(str, partVersion);
		}
		return str;
	}

	/**
	 * 格式化请求数据为字符串
	 */
	private void bytes2Str() throws Exception {
		if (bcd == 1 && bitType != 4) { // bcd 变长域 2,32,35,48,60
			str = Hex.encodeHexString(bytes);
			if (len < str.length()) {
				if (align == 1) { // 如果bcd右补字符串零，截取
					str = str.substring(0, len);
				} else { // 左补零截取
					str = str.substring(1, len);
				}
			}
		} else if (bcd == 0 && bitType != 4) { // ascii 变长域62,63
			str = new String(bytes, PosConstant.GBK_ENCODE);
		} // else { // binary
	}

	/**
	 * 格式化应答数据为字符串
	 * 2015/02/09 GengLian delete "if (dealData != null)"
	 */
	private void rsp2Bytes(String dealData) throws Exception {
		len = dealData.length(); // bcd需更新变长域实际长度值
		if (bcd == 0 && bit != 63) {// 是ascii    63域是ascii搞特殊，跑下面去了。暂时不改
			this.bytes = dealData.getBytes(PosConstant.GBK_ENCODE); // 默认gbk
		} else { // bcd binary
			if (bit == 60 || bit == 48 || bit == 2) {//这部分逻辑有些奇怪，可能要优化 kend
				// 右补字符串零 60域网络管理信息码补零 奇数补零，积分当日查询0条/偶数条
				if (0 != (len & 1)) { // if (len % 2 != 0) { GengLian
					dealData += PosConstant.ZERO_CHAR;
				}
			}
			SystemLog.debug("BitMap", "rsp2Bytes()", "dealData=" + dealData);
			this.bytes = Hex.decodeHex(dealData.toCharArray());
		}
	}

	///////////////////////////////////////////////////////////////////////////

	public int getBit() {
		return bit;
	}

	public void setBit(int bit) {
		this.bit = bit;
	}

	public int getVar() {
		return var;
	}

	public void setVar(int var) {
		this.var = var;
	}

	public int getLen() {
		return len;
	}

	public void setLen(int len) {
		this.len = len;
	}

	public int getBitType() {
		return bitType;
	}

	public void setBitType(int bitType) {
		this.bitType = bitType;
	}

	public int isBcd() {
		return bcd;
	}

	public void setBcd(int bcd) {
		this.bcd = bcd;
	}

	public int getAlign() {
		return align;
	}

	public void setAlign(int align) {
		this.align = align;
	}

	public byte[] getBytes() {
		return bytes;
	}
	
}
