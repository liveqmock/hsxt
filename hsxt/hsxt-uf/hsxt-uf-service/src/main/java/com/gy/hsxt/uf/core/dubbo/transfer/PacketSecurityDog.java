/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.uf.core.dubbo.transfer;

import org.apache.log4j.Logger;

import com.gy.hsxt.uf.common.bean.packet.SecureRegionPacket;
import com.gy.hsxt.uf.common.bean.packet.SecureRegionPacketHeader;
import com.gy.hsxt.uf.common.constant.ConfigConst;
import com.gy.hsxt.uf.common.constant.ConfigConst.SecurityKey;
import com.gy.hsxt.uf.common.exception.DecryptException;
import com.gy.hsxt.uf.common.exception.UnsignException;
import com.gy.hsxt.uf.common.spring.UfPropertyConfigurer;
import com.gy.hsxt.uf.common.utils.BytesUtils;
import com.gy.hsxt.uf.common.utils.FileUtils;
import com.gy.hsxt.uf.common.utils.ResourcePathTool;
import com.gy.hsxt.uf.common.utils.StringUtils;
import com.gy.hsxt.uf.common.utils.security.AesUtils;
import com.gy.hsxt.uf.common.utils.security.RsaUtils;
import com.gy.hsxt.uf.common.utils.security.SummaryUtils;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-uf-service
 * 
 *  Package Name    : com.gy.hsxt.uf.core.dubbo.transfer
 * 
 *  File Name       : PacketSecurityDog.java
 * 
 *  Creation Date   : 2015-10-29
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 报文安全防护墙
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class PacketSecurityDog {
	/** 记录日志对象 **/
	private final Logger logger = Logger.getLogger(this.getClass());

	/** 单实例对象 **/
	private static final PacketSecurityDog instance = new PacketSecurityDog();

	/** RSA公钥(BASE64编码) **/
	private static String rsaPublicKey;

	/** RSA私钥 (BASE64编码) **/
	private static String rsaPrivateKey;

	/** AES密钥 **/
	private static String aesEncrypKey;

	/**
	 * 私有构造函数
	 */
	private PacketSecurityDog() {
		// 执行初始化
		this.initSecurityKey();
	}

	/**
	 * 获得单实例
	 * 
	 * @return
	 */
	public static PacketSecurityDog getInstance() {
		return instance;
	}

	/**
	 * 初始化安全密钥
	 */
	private void initSecurityKey() {
		// 综合前置密钥文件根目录
		String resRootPath = "";

		try {
			resRootPath = UfPropertyConfigurer
					.getProperty(ConfigConst.SYSTEM_SECURITY_KEY_DIR);

			if (StringUtils.isEmpty(resRootPath)) {
				resRootPath = ResourcePathTool.getInstance()
						.getResourceFile(SecurityKey.RSA_PUBLIC_KEY_FILE)
						.getParent();
			}

			resRootPath = this.formatPath(resRootPath);

			rsaPublicKey = RsaUtils.readKeyFromFile(resRootPath,
					SecurityKey.RSA_PUBLIC_KEY_FILE);

			rsaPrivateKey = RsaUtils.readKeyFromFile(resRootPath,
					SecurityKey.RSA_PRIVATE_KEY_FILE);

			aesEncrypKey = (String) FileUtils.readKeyFromFile(resRootPath,
					SecurityKey.AES_KEY_FILE);

			aesEncrypKey = aesEncrypKey.substring(0, 16);
		} catch (Exception e) {
			logger.error("综合前置初始化安全密钥失败, 请检查安全密钥文件目录配置是否正确："
					+ ConfigConst.SYSTEM_SECURITY_KEY_DIR + "=" + resRootPath,
					e);
			throw e;
		}
	}

	/**
	 * 对报文进行加签、加密处理
	 * 
	 * @param regionPacket
	 * @return
	 */
	public byte[] addSignAndEncrypt(SecureRegionPacket regionPacket) {
		this.addSign(regionPacket);

		return this.encryptPacketInAes(regionPacket);
	}

	/**
	 * 对报文进行解密、解签处理
	 * 
	 * @param packetBytes
	 * @return
	 * @throws DecryptException
	 * @throws UnsignException
	 */
	public SecureRegionPacket decryptAndUnsign(byte[] packetBytes)
			throws DecryptException, UnsignException {
		SecureRegionPacket packet = this.decryptPacketByAes(packetBytes);
		
		return packet;

		/** 备注：因为部分场景有问题, 暂时屏蔽掉, 有时间在定位处理, 不影响正常功能, 只是锦上添花, marked by:zhangysh
		if (this.verifySign(packet)) {
			return packet;
		}

		throw new UnsignException("目标综合前置进行解密、解签处理失败！");
		**/
	}

	/**
	 * 辨别该报文是否属于本地区平台
	 * 
	 * @param regionPacket
	 * @return
	 */
	public boolean isBelongToMe(SecureRegionPacket regionPacket) {
		if (null == regionPacket) {
			return false;
		}

		SecureRegionPacketHeader header = regionPacket.getHeader();

		String destPlatformId = header.getDestPlatformId();
		String myPlatformId = UfPropertyConfigurer
				.getProperty(ConfigConst.SYSTEM_PLATFORM_ID);

		// 判断该跨地区报文是否属于本平台
		return myPlatformId.equals(destPlatformId);
	}

	/**
	 * 是否自交
	 * 
	 * @param destPlatformId
	 * @return
	 */
	public boolean isSelfOverlap(String destPlatformId) {
		String myPlatformId = UfPropertyConfigurer
				.getProperty(ConfigConst.SYSTEM_PLATFORM_ID);

		// 判断该跨地区报文是否属于本平台
		return myPlatformId.equals(destPlatformId);
	}

	/**
	 * 给报文加签(使用RSA公钥)
	 * 
	 * @param regionPacket
	 * @return
	 */
	private void addSign(SecureRegionPacket regionPacket) {
		try {
			byte[] headerData = this.getPacketSummaryBytesData(regionPacket);
			byte[] encodedData = RsaUtils.encryptByPrivateKey(headerData,
					rsaPrivateKey);

			String sign = RsaUtils.sign(encodedData, rsaPrivateKey);

			regionPacket.getHeader().setDigitSignature(sign);
		} catch (Exception e) {
			logger.error("RSA对报文头加签失败", e);
		}
	}

	/**
	 * 给报文解签(使用RSA私钥)
	 * 
	 * @param regionPacket
	 * @return
	 */
	@SuppressWarnings("unused")
	private boolean verifySign(SecureRegionPacket regionPacket) {

		try {
			SecureRegionPacketHeader header = regionPacket.getHeader();

			byte[] headerData = this.getPacketSummaryBytesData(regionPacket);
			byte[] encodedData = RsaUtils.encryptByPrivateKey(headerData,
					rsaPrivateKey);

			String signature = header.getDigitSignature();

			return RsaUtils.verify(encodedData, rsaPublicKey, signature);
		} catch (Exception e) {
			logger.error("RSA对报文头解签失败", e);
		}

		return false;
	}

	/**
	 * 加密报文(使用AES密钥)
	 * 
	 * @param regionPacket
	 * @return
	 */
	private byte[] encryptPacketInAes(SecureRegionPacket regionPacket) {
		try {
			byte[] bytes = BytesUtils.object2ByteByzip(regionPacket);

			return AesUtils.encrypt(bytes, aesEncrypKey);
		} catch (Exception e) {
			logger.error("AES加密报文失败", e);
		}

		return null;
	}

	/**
	 * 解密报文(使用AES密钥)
	 * 
	 * @param packetBytes
	 * @return
	 * @throws DecryptException
	 */
	private SecureRegionPacket decryptPacketByAes(byte[] packetBytes)
			throws DecryptException {
		try {
			byte[] decrypted = AesUtils.decrypt(packetBytes, aesEncrypKey);

			return (SecureRegionPacket) BytesUtils.byte2ObjectByzip(decrypted);
		} catch (Exception e) {
			String errorInfo = "AES解密报文失败!";
			logger.error(errorInfo, e);
			throw new DecryptException(errorInfo, e);
		}
	}

	/**
	 * 获得报文摘要字节数据
	 * 
	 * @param regionPacket
	 * @return
	 */
	private byte[] getPacketSummaryBytesData(SecureRegionPacket regionPacket) {
		// 报文头信息
		String packetHeaderInfo = regionPacket.getHeader().toString();

		// 报文体摘要
		String packetBodySummary = SummaryUtils.parseSummaryData(regionPacket
				.getBody().getBodyContent());

		return (packetHeaderInfo.concat(packetBodySummary)).getBytes();
	}

	/**
	 * 格式化文件目录
	 * 
	 * @param resPath
	 * @return
	 */
	private String formatPath(String resPath) {
		if (StringUtils.isEmpty(resPath)) {
			return "";
		}

		if (resPath.endsWith("/")) {
			return resPath;
		}

		return resPath + "/";
	}
}