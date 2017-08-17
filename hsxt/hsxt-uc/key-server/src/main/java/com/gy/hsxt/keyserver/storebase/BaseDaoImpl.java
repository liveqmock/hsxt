
package com.gy.hsxt.keyserver.storebase;

 
import java.util.Arrays;
import java.util.Calendar;
import java.util.Random;

import org.apache.log4j.Logger;


import com.alibaba.druid.util.HexBin;
import com.gy.hsxt.keyserver.tools.*;
import com.gy.kms.keyserver.CoDecode;

/***************************************************************************
 * <PRE>
 *  Project Name    : KeyServer
 * 
 *  Package Name    : com.gy.kms.keyserver.DAO
 *  
 *  File Name       : BaseDAOImpl.java
 * 
 *  Creation Date   : 2014-7-4
 * 
 *  Author          : fandi
 * 
 *  Purpose         : 业务功能实现
 *  
 * 
 * </PRE>
 ***************************************************************************/
public class BaseDaoImpl implements BaseDao {
	final Logger logger = Logger.getLogger(getClass());

	store save;
	
	public store getSave() {
		return save;
	}

	public void setSave(store save) {
		this.save = save;
	}
/**
 * 获取新的PMK     生成主密钥
 * @Ent 企业资源号
 * @POSID POS编号
 * @Operator 操作员
 * @return 16位PMK
 */
	public byte[] GetNewPMK(String Ent, String PosID, String Operator) {
		byte PMK[] = CreateRandByte(16);
		save.SetPMK(Ent + PosID, PMK);
		return PMK;
	}
/**
 * 计算MAC 相当于数字签名      把pos发上来的数据包做签名  给uc用的
 * @Ent 企业资源号
 * @POSID POS编号
 * @data 信息
 * @return 8位MAC
 */
	public String GetMAC(String Ent, String PosID, byte[] data) {
		String rc = null;
		CashData cash = save.getPIKPMK(Ent + PosID);
		if (cash != null) {
			byte mak[] = cash.getMak();
			byte mac[] = new byte[8];
			CoDecode.DES3Mac(mak, data, data.length, mac);
			logger.debug("取mac mak=" + HexBin.encode(mak) + "  data="
					+ HexBin.encode(data) + "  mac=" + HexBin.encode(mac));

			return HexBin.encode(mac).substring(0, 8);
		} else
			logger.debug("取PMK失败，Ent+pos：" + Ent + PosID);
		return rc;
	}
/**
 * 数据加密解密  ,对消费者登录密码或交易密码做数据解密
 * @Ent 企业资源号
 * @POSID POS编号
 * @data 信息
 * @iFencrypt 加密为true，解密为false
 * @return 加密数据（长度与原始数据相同）
 */
	public byte[] CoDecode(String Ent, String PosID, byte[] data,
			boolean iFencrypt) {
		byte rc[] = null;
		CashData cash = save.getPIKPMK(Ent + PosID);
		if (cash != null) {
			byte pik[] = cash.getPik();
			rc = new byte[data.length];
			if (iFencrypt)
				CoDecode.DES3encrypt(pik, data, data.length, rc);
			else
				CoDecode.DES3decrypt(pik, data, data.length, rc);
		} else
			logger.debug("取PMK失败，Ent+pos：" + Ent + PosID);
		return rc;
	}
/**
 * 获得新的密钥：PIK、MAK   生成新的PIK，MAK
 * 该结构为与前一版本兼容保留
 * @Ent 企业资源号
 * @POSID POS编号
 * @Operator 操作员
 * @return 
 * 结构如下：PIK[16]、PIK_CHECK[4](PIK效验)、MAC[8]、ZREO[8](0)、MAC_CHECK[4](MAC效验)
 */
	public byte[] GetNewKey(String Ent, String PosID, String Operator) {
		byte pmk_src[] = save.GetPMK(Ent+PosID);
		if (pmk_src == null) {
			logger.error("取PMK失败，Ent+pos：" + Ent + PosID);
			return null;
		}

		byte PIK[] = CreateRandByte(16);
		byte MAK[] = CreateRandByte(8);
		logger.debug("原始PIK：" + HexBin.encode(PIK));
		logger.debug("原始MAK：" + HexBin.encode(MAK));
		byte PIK_enc[] = new byte[16];
		byte MAK_enc[] = new byte[8];
		CoDecode.DES3encrypt(pmk_src, PIK, PIK.length, PIK_enc);
		CoDecode.DES3encrypt(pmk_src, MAK, MAK.length, MAK_enc);
		logger.debug("加密PIK：" + HexBin.encode(PIK_enc));
		logger.debug("加密MAK：" + HexBin.encode(MAK_enc));
		byte rc[] = new byte[40];
		System.arraycopy(PIK_enc, 0, rc, 0, 16);
		byte tmp[] = new byte[8];
		byte cv[] = new byte[8];
		CoDecode.DES3encrypt(PIK, tmp, tmp.length, cv);
		System.arraycopy(cv, 0, rc, 16, 4);

		System.arraycopy(MAK_enc, 0, rc, 20, 8);
		CoDecode.DESencrypt(MAK, tmp, 8, cv, 0);
		System.arraycopy(cv, 0, rc, 36, 4);
		logger.debug("完整PIKMAC" + HexBin.encode(rc));
		save.SetPIKMAK(Ent + PosID, PIK, MAK);
		return rc;
	}
/**
 * 产生规定长度的随机数
 * @param len 长度
 * @return 随机数组
 */
	private byte[] CreateRandByte(int len) {
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
 * 效验PMK   主密钥
 * @Ent 企业资源号
 * @POSID POS编号
 * @Operator 操作员
 * @check 效验内容 ：用PMK加密16位操作员所得结果
 * @return true 效验成功
 */
	public boolean CheckPMK(String Ent, String PosID, String Operator,
			byte[] check) {
		if (check.length != 16)
			return false;

		byte pmk_src[] = save.GetPMK(Ent + PosID);
		if (pmk_src == null) {
			logger.error("取PMK失败，Ent+pos：" + Ent + PosID);
			return false;
		}

		logger.debug("原始PMK：" + HexBin.encode(pmk_src));
		byte Operatorbyte[] = Tools.getByteFromString(Operator, 16);
		logger.debug("Operatorbyte：" + HexBin.encode(Operatorbyte));
		byte enc[] = new byte[16];
		CoDecode.DES3encrypt(pmk_src, Operatorbyte, 16, enc);
		logger.debug("enc：" + HexBin.encode(enc));
		logger.debug("check：" + HexBin.encode(check));
		return Arrays.equals(check, enc);
	}
}
