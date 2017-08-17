/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.uf.common.utils.security;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.KeyException;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import com.gy.hsxt.uf.common.constant.UFConstant;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-uf-service
 * 
 *  Package Name    : com.gy.hsxt.uf.common.utils.security
 * 
 *  File Name       : AesUtils.java
 * 
 *  Creation Date   : 2014-9-27
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 进行AES加解密工具类
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public final class AesUtils {
	private static final String CLIPHER_INST_KEY = "AES/ECB/PKCS5Padding";

	/**
	 * 私有构造函数
	 */
	private AesUtils() {
	}

	/**
	 * 获得密钥对象
	 * 
	 * @param key
	 * @return
	 * @throws KeyException
	 */
	public static SecretKeySpec getSecretKeySpec(byte[] key)
			throws KeyException {
		if (null == key) {
			throw new NullPointerException("key is null");
		}

		if (200 <= key.length) {
			throw new KeyException("key must be 16 byte");
		}

		return new SecretKeySpec(key, "AES");
	}

	/**
	 * 执行加密
	 * 
	 * @param plainTextBytes
	 * @param key
	 * @return
	 * @throws GeneralSecurityException
	 * @throws IOException
	 */
	public static byte[] encrypt(byte[] plainTextBytes, String key)
			throws GeneralSecurityException, IOException {
		return encrypt(plainTextBytes, key.getBytes(UFConstant.ENCODING_UTF8));
	}

	/**
	 * 将字符串进行加密,返回加密后的字串
	 * 
	 * @param plainText
	 *            打算加密的原文字串
	 * @param key
	 *            密钥
	 * @return 加密后的字串
	 * @throws GeneralSecurityException
	 * @throws IOException
	 */
	public static String encrypt(String plainText, String key)
			throws GeneralSecurityException, IOException {
		byte[] cipherTextBytes = AesUtils.encrypt(
				plainText.getBytes(UFConstant.ENCODING_UTF8), key);

		// 加密后的字串
		String encryptedText = parseByte2HexStr(cipherTextBytes);

		return encryptedText;
	}

	/**
	 * 执行加密
	 * 
	 * @param plainTextBytes
	 * @param key
	 * @return
	 * @throws GeneralSecurityException
	 * @throws IOException
	 */
	public static byte[] encrypt(byte[] plainTextBytes, byte[] key)
			throws GeneralSecurityException, IOException {
		SecretKeySpec skeySpec = getSecretKeySpec(key);
		Cipher cipher = Cipher.getInstance(CLIPHER_INST_KEY);
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec);

		return cipher.doFinal(plainTextBytes);
	}

	/**
	 * 执行解密
	 * 
	 * @param cipherTextBytes
	 * @param key
	 * @return
	 * @throws GeneralSecurityException
	 * @throws IOException
	 */
	public static byte[] decrypt(byte[] cipherTextBytes, String key)
			throws GeneralSecurityException, IOException {
		return decrypt(cipherTextBytes, key.getBytes(UFConstant.ENCODING_UTF8));
	}

	/**
	 * 执行解密(无异常抛出)
	 * 
	 * @param cipherTextBytes
	 * @param key
	 * @return
	 */
	public static byte[] decryptInQuiet(byte[] cipherTextBytes, String key) {
		try {
			return decrypt(cipherTextBytes,
					key.getBytes(UFConstant.ENCODING_UTF8));
		} catch (Exception e) {
		}

		return new byte[0];
	}

	/**
	 * 执行解密
	 * 
	 * @param cipherTextBytes
	 * @param key
	 * @return
	 * @throws GeneralSecurityException
	 * @throws IOException
	 */
	public static byte[] decrypt(byte[] cipherTextBytes, byte[] key)
			throws GeneralSecurityException, IOException {
		SecretKeySpec skeySpec = getSecretKeySpec(key);

		Cipher cipher = Cipher.getInstance(CLIPHER_INST_KEY);
		cipher.init(Cipher.DECRYPT_MODE, skeySpec);

		return cipher.doFinal(cipherTextBytes);
	}

	/**
	 * 将加密的字串进行解密
	 * 
	 * @param encryptedText
	 *            被加密过的字串
	 * @param key
	 *            密钥
	 * @return 解密后的字串
	 * @throws GeneralSecurityException
	 * @throws IOException
	 */
	public static String decrypt(String encryptedText, String key)
			throws GeneralSecurityException, IOException {
		byte[] encryptedBytes = AesUtils.parseHexStr2Byte(encryptedText);
		byte[] decryptBytes = AesUtils.decrypt(encryptedBytes, key);

		return new String(decryptBytes);
	}

	/**
	 * 将加密的字串进行解密(静默状态,不抛出异常)
	 * 
	 * @param encryptedText
	 * @param key
	 * @return
	 */
	public static String decryptInQuiet(String encryptedText, String key) {

		try {
			return decrypt(encryptedText, key);
		} catch (GeneralSecurityException | IOException e) {
		}

		return "";
	}

	/**
	 * 将二进制转换成16进制
	 * 
	 * @param buf
	 * @return
	 */
	public static String parseByte2HexStr(byte[] buf) {
		String hex;
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < buf.length; i++) {
			hex = Integer.toHexString(buf[i] & 0xFF);

			if (1 == hex.length()) {
				hex = '0' + hex;
			}

			sb.append(hex.toUpperCase());
		}

		return sb.toString();
	}

	/**
	 * 将16进制转换为二进制
	 * 
	 * @param hexStr
	 * @return
	 */
	public static byte[] parseHexStr2Byte(String hexStr) {
		if (1 > hexStr.length()) {
			return new byte[0];
		}

		int high;
		int low;

		byte[] result = new byte[hexStr.length() / 2];

		for (int i = 0; i < hexStr.length() / 2; i++) {
			high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
			low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);

			result[i] = (byte) (high * 16 + low);
		}

		return result;
	}

	public static void main(String[] args) {
		/*
		 * String secretKey = "EA86AGS7BAF#S4AR"; String plainText = "互生经济学";
		 * 
		 * try { System.out.println("加密前：" + plainText);
		 * 
		 * String encryptedText = AesUtils.encrypt(plainText, secretKey);
		 * System.out.println("加密后：" + encryptedText);
		 * 
		 * String decryptedText = AesUtils.decrypt(encryptedText, secretKey);
		 * System.out.println("解密后：" + decryptedText); } catch (Exception e) {
		 * e.printStackTrace(); }
		 */
	}
}