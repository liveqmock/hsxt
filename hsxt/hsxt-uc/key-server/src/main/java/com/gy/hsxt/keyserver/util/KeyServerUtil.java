/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.keyserver.util;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Random;

import org.apache.commons.codec.binary.StringUtils;
import org.apache.log4j.Logger;

import com.alibaba.druid.util.HexBin;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.keyserver.DeviceToken;
import com.gy.hsxt.keyserver.tools.Tools;
import com.gy.kms.keyserver.CoDecode;

/**
 * 生成密钥、MAC等
 * 
 * @Package: com.gy.hsxt.keyserver.util
 * @ClassName: KeyServerUtil
 * @Description: TODO
 * 
 * @author: lixuan
 * @date: 2015-10-29 下午4:48:41
 * @version V1.0
 */
public class KeyServerUtil {
	Logger log = Logger.getLogger(this.getClass());
	// POS密钥长度
	int POS_ENCRYPT_CNT_LEN = 8;
	
	/**
	 * 生成新的PMK 生成主密钥，返回byte
	 * 
	 * @Ent 企业资源号
	 * @POSID POS编号
	 * @Operator 操作员
	 * @return 16位PMK
	 */
	public byte[] genPMK() {
		byte PMK[] = createRandomByte(16);
		return PMK;
	}

	/**
	 * 生成PMK，返回字符类型
	 * @return
	 */
	public String genPmkToStr() {
		return HexBin.encode(genPMK());
	}

	/**
	 * 产生规定长度的随机数
	 * 
	 * @param len
	 *            长度
	 * @return 随机数组
	 */
	private byte[] createRandomByte(int len) {
		int i;
		byte rc[] = new byte[len];

		long currentTime = Calendar.getInstance().getTimeInMillis();
		rc[0] = new Long(currentTime & 0xff).byteValue();
		currentTime >>= 8;
		rc[1] = new Long(currentTime & 0xff).byteValue();

		Random random = new Random();
		for (i = 0; i < 2; i++)
			rc[i] = (byte) ((1.0 + 127.0 * random.nextDouble()) * (int) rc[i]);
		for (i = 2; i < len; i++)
			rc[i] = (byte) (1.0 + (int) (65535.0 * random.nextDouble()));

		return rc;
	}

	/**
	 * 加密PMK
	 * @param pmk
	 * @return
	 */
	public String encodePmk(byte[] pmk){
		byte[] b =  CoDecode.encryptPMK(pmk);
		return HexBin.encode(b);
	}
	
	/**
	 * 生成pikmak
	 * 
	 * @param pmkDb
	 * @return
	 */
	public DeviceToken genPikMak(byte[] pmkDb) {
		byte[] pmk = CoDecode.decryptPMK(pmkDb);
		byte PIK[] = createRandomByte(16);
		byte MAK[] = createRandomByte(8);
		log.debug("原始PIK：" + HexBin.encode(PIK));
		log.debug("原始MAK：" + HexBin.encode(MAK));
		byte PIK_enc[] = new byte[16];
		byte MAK_enc[] = new byte[8];
		CoDecode.DES3encrypt(pmk, PIK, PIK.length, PIK_enc);
		CoDecode.DES3encrypt(pmk, MAK, MAK.length, MAK_enc);
		log.debug("加密PIK：" + HexBin.encode(PIK_enc));
		log.debug("加密MAK：" + HexBin.encode(MAK_enc));
		byte rc[] = new byte[40];
		System.arraycopy(PIK_enc, 0, rc, 0, 16);
		byte tmp[] = new byte[8];
		byte cv[] = new byte[8];
		CoDecode.DES3encrypt(PIK, tmp, tmp.length, cv);

		log.debug("加密后  [" + HexBin.encode(cv) + "]");
		System.arraycopy(cv, 0, rc, 16, 4);

		System.arraycopy(MAK_enc, 0, rc, 20, 8);
		CoDecode.DESencrypt(MAK, tmp, 8, cv, 0);
		System.arraycopy(cv, 0, rc, 36, 4);
		log.debug(" [" + HexBin.encode(cv) + "]");
		log.debug("完整PIKMAC:" + HexBin.encode(rc));
		DeviceToken token = new DeviceToken();
		token.setPik(PIK);
		token.setPikmak(rc);
		token.setMak(MAK);
		// save.SetPIKMAK(Ent + PosID, PIK, MAK);
		return token;
	}

	/**
	 * 获得新的密钥：PIK、MAK 生成新的PIK，MAK 该结构为与前一版本兼容保留
	 * 
	 * @entResNo 企业资源号
	 * @posId POS编号
	 * @operatorName 操作员
	 * @return 
	 *         结构如下：PIK[16]、PIK_CHECK[4](PIK效验)、MAC[8]、ZREO[8](0)、MAC_CHECK[4](MAC效验
	 *         )
	 */
	public DeviceToken genPikMak(String pmk) {
		byte pmk_src[] = HexBin.decode(pmk);
		if (pmk_src == null) {
			log.error("pmk生成失败");
			return null;
		}
		return genPikMak(pmk_src);

	}

	/**
	 * 效验PMK 主密钥
	 * 
	 * @Ent 企业资源号
	 * @POSID POS编号
	 * @Operator 操作员
	 * @check 效验内容 ：用PMK加密16位操作员所得结果
	 * @return true 效验成功
	 */
	public boolean checkPMK(byte[] pmkSrc, byte[] pmkDest, String operator) {
		if (pmkDest.length != 16)
			return false;
		byte operatorbyte[] = Tools.getByteFromString(operator, 16);
		byte enc[] = new byte[16];
		CoDecode.DES3encrypt(pmkSrc, operatorbyte, 16, enc);
		return Arrays.equals(pmkDest, enc);
	}

	/**
	 * 加密报文 获取mac
	 * 
	 * @param mak
	 * @param data
	 * @return
	 */
	public byte[] genMac(byte[] mak, byte[] data) {

		byte mac[] = new byte[POS_ENCRYPT_CNT_LEN];
		CoDecode.DES3Mac(mak, data, data.length, mac);

		return HexBin.encode(mac).substring(0, 8).getBytes();
	}

	/**
	 * 数据加密 ,对消费者登录密码或交易密码做数据解密
	 * 
	 * @data 信息
	 * @return 加密数据（长度与原始数据相同）
	 */
	public byte[] encodeCnt(byte[] pik, byte[] data) throws HsException {
		byte rc[] = new byte[POS_ENCRYPT_CNT_LEN];
		CoDecode.DES3encrypt(pik, data, POS_ENCRYPT_CNT_LEN, rc);
		return rc;
	}

	/**
	 * 数据解密 ,对消费者登录密码或交易密码做数据解密
	 * 
	 * @data 信息
	 * @return 加密数据（长度与原始数据相同）
	 */
	public byte[] decodeCnt(byte[] pik, byte[] data) throws HsException {
		byte rc[] = new byte[POS_ENCRYPT_CNT_LEN];
		CoDecode.DES3decrypt(pik, data, POS_ENCRYPT_CNT_LEN, rc);
		return rc;
	}
	
	/**pos机二维码解密
	 * 
	 * @param ks 秘钥
	 * @param data 待解密密文
	 * @return 解密后明文
	 */
	public static String dec3Des(byte[] ks,String data){
//		byte[] ks=key.getBytes();
		byte[] sourcebyte=null;
		try {
			byte[] out2=HexBin.decode(data);
			sourcebyte = new byte[out2.length];
			CoDecode.DES3decrypt(ks, out2, out2.length, sourcebyte);
			String dec1=new String(sourcebyte);
			return dec1;
		} catch (Exception e) {
			System.err.println(Arrays.toString(ks));
			System.err.println(data);
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 测试使用
	 * @param args
	 */
	public static void main(String[] args) {
		byte[] b1 = {-76, -27, -127, 86, 76, 11, 24, 69, 11, 40, 10, 4, 112, 52, 105, 57};
		byte[] b2 = {};
		try {
			KeyServerUtil util = new KeyServerUtil();
			System.out.println("----" + CoDecode.encryptPMK(b1));
			System.out.println(CoDecode.decryptPMK(b1));
			// 生成pmk
			//byte[] pmkSrc = 
			// 生成pik
			String s = util.genPmkToStr();
			
//			// 加密测试
//			String cnt = "test";
//
//			byte[] data = Tools.getByteFromString(cnt, cnt.length());
//			byte[] waitDecode = util.encodeCnt(token.getPik(), data);
//
//			// 解密测试
//			byte[] decodeResult = util.decodeCnt(token.getPik(), waitDecode);
//			System.out.println(new String(decodeResult));
//			byte operatorbyte[] = Tools.getByteFromString(cnt, 16);
//			//
//			byte[] pmk = new byte[16];
//			CoDecode.DES3encrypt(pmkSrc, operatorbyte, 16, pmk);
//			boolean chk = util.checkPMK(pmkSrc, pmk, cnt);
//			System.out.println(chk);
//
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
