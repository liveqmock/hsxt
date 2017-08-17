/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.uf.common.utils.security;

import java.io.ByteArrayOutputStream;
import java.security.Key;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

import org.apache.commons.codec.binary.Base64;

import com.gy.hsxt.uf.common.utils.FileUtils;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-uf-service
 * 
 *  Package Name    : com.gy.hsxt.uf.common.utils.security
 * 
 *  File Name       : RsaUtils.java
 * 
 *  Creation Date   : 2014-9-29
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : RSA加解密
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class RsaUtils {
	/** 指定加密算法 **/
	private static final String KEY_ALGORITHM = "RSA";

	/** 签名算法 **/
	public static final String SIGNATURE_ALGORITHM = "MD5withRSA";

	/** RSA最大加密明文大小 **/
	private static final int MAX_ENCRYPT_BLOCK = 117;

	/** RSA最大解密密文大小 **/
	private static final int MAX_DECRYPT_BLOCK = 128;

	private RsaUtils() {
	}

	/**
	 * 使用公钥加密
	 * 
	 * @param sourceData
	 *            源数据
	 * @param publicKey
	 *            公钥(BASE64编码)
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptByPublicKey(byte[] sourceData, String publicKey)
			throws Exception {
		byte[] keyBytes = Base64.decodeBase64(publicKey);

		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		Key publicK = keyFactory.generatePublic(x509KeySpec);

		// 对数据加密
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.ENCRYPT_MODE, publicK);

		ByteArrayOutputStream out = new ByteArrayOutputStream();

		byte[] cache;
		int offSet = 0;
		int index = 0;
		int inputLen = sourceData.length;

		// 对数据分段加密
		while (inputLen - offSet > 0) {
			if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
				cache = cipher.doFinal(sourceData, offSet, MAX_ENCRYPT_BLOCK);
			} else {
				cache = cipher.doFinal(sourceData, offSet, inputLen - offSet);
			}

			out.write(cache, 0, cache.length);

			index++;

			offSet = index * MAX_ENCRYPT_BLOCK;
		}

		byte[] encryptedData = out.toByteArray();

		out.close();

		return encryptedData;
	}

	/**
	 * 使用私钥加密
	 * 
	 * @param sourceData
	 *            源数据
	 * @param privateKey
	 *            私钥(BASE64编码)
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptByPrivateKey(byte[] sourceData,
			String privateKey) throws Exception {
		byte[] keyBytes = Base64.decodeBase64(privateKey);
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);

		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.ENCRYPT_MODE, privateK);

		ByteArrayOutputStream out = new ByteArrayOutputStream();

		byte[] cache;

		int inputLen = sourceData.length;
		int offSet = 0;
		int index = 0;

		// 对数据分段加密
		while (inputLen - offSet > 0) {
			if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
				cache = cipher.doFinal(sourceData, offSet, MAX_ENCRYPT_BLOCK);
			} else {
				cache = cipher.doFinal(sourceData, offSet, inputLen - offSet);
			}

			out.write(cache, 0, cache.length);

			index++;

			offSet = index * MAX_ENCRYPT_BLOCK;
		}

		byte[] encryptedData = out.toByteArray();

		out.close();

		return encryptedData;
	}

	/**
	 * 使用私钥解密
	 * 
	 * @param encryptedData
	 *            加密的数据
	 * @param privateKey
	 *            私钥(BASE64编码)
	 * @return
	 * @throws Exception
	 */
	public static byte[] decryptByPrivateKey(byte[] encryptedData,
			String privateKey) throws Exception {
		byte[] keyBytes = Base64.decodeBase64(privateKey);
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);

		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);

		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.DECRYPT_MODE, privateK);

		ByteArrayOutputStream out = new ByteArrayOutputStream();

		byte[] cache;

		int inputLen = encryptedData.length;
		int offSet = 0;
		int index = 0;

		// 对数据分段解密
		while (inputLen - offSet > 0) {
			if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
				cache = cipher
						.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
			} else {
				cache = cipher
						.doFinal(encryptedData, offSet, inputLen - offSet);
			}

			out.write(cache, 0, cache.length);

			index++;

			offSet = index * MAX_DECRYPT_BLOCK;
		}

		byte[] decryptedData = out.toByteArray();

		out.close();

		return decryptedData;
	}

	/**
	 * 使用公钥解密
	 * 
	 * @param encryptedData
	 *            已加密数据
	 * @param publicKey
	 *            公钥(BASE64编码)
	 * @return
	 * @throws Exception
	 */
	public static byte[] decryptByPublicKey(byte[] encryptedData,
			String publicKey) throws Exception {
		byte[] keyBytes = Base64.decodeBase64(publicKey);
		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);

		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		Key publicK = keyFactory.generatePublic(x509KeySpec);

		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.DECRYPT_MODE, publicK);

		ByteArrayOutputStream out = new ByteArrayOutputStream();

		byte[] cache;

		int inputLen = encryptedData.length;
		int offset = 0;
		int index = 0;

		// 对数据分段解密
		while (inputLen - offset > 0) {
			if (inputLen - offset > MAX_DECRYPT_BLOCK) {
				cache = cipher
						.doFinal(encryptedData, offset, MAX_DECRYPT_BLOCK);
			} else {
				cache = cipher
						.doFinal(encryptedData, offset, inputLen - offset);
			}
			out.write(cache, 0, cache.length);
			index++;
			offset = index * MAX_DECRYPT_BLOCK;
		}

		byte[] decryptedData = out.toByteArray();

		out.close();

		return decryptedData;
	}

	/**
	 * 使用私钥解密
	 * 
	 * @param cryptograph
	 * @param privateKey
	 * @return
	 * @throws Exception
	 */
	public static String decryptByPrivateKey(String cryptograph,
			String privateKey) throws Exception {
		byte[] bytes = decryptByPrivateKey(cryptograph.getBytes(), privateKey);

		return new String(bytes);
	}

	/**
	 * 从文件中读取密钥
	 * 
	 * @param path
	 * @param fileName
	 * @return
	 */
	public static String readKeyFromFile(String path, String fileName) {
		return (String) FileUtils.readKeyFromFile(path, fileName);
	}

	/**
	 * 用私钥对信息生成数字签名
	 * 
	 * @param data
	 *            已加密数据
	 * @param privateKey
	 *            私钥(BASE64编码)
	 * @return
	 * @throws Exception
	 */
	public static String sign(byte[] data, String privateKey) throws Exception {
		byte[] keyBytes = Base64.decodeBase64(privateKey);

		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);

		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		PrivateKey privateK = keyFactory.generatePrivate(pkcs8KeySpec);

		Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
		signature.initSign(privateK);
		signature.update(data);

		return Base64.encodeBase64String(signature.sign());
	}

	/**
	 * 校验数字签名
	 * 
	 * @param data
	 *            已加密数据
	 * @param publicKey
	 *            公钥(BASE64编码)
	 * @param sign
	 *            数字签名 (BASE64编码)
	 * @return
	 * @throws Exception
	 */
	public static boolean verify(byte[] data, String publicKey, String sign)
			throws Exception {
		byte[] keyBytes = Base64.decodeBase64(publicKey);

		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		PublicKey publicK = keyFactory.generatePublic(keySpec);

		Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
		signature.initVerify(publicK);
		signature.update(data);

		return signature.verify(Base64.decodeBase64(sign));
	}

	public static void main(String[] args) throws Exception {
//		String source = "H";// 要加密的字符串
//		byte[] cryptograph = encryptByPublicKey(
//				source.getBytes(),
//				readKeyFromFile(
//						"D:/zys_workspace_root/uf/hsxt-uf/hsxt-uf-service/src/main/resources/security",
//						"uf_rsa_public.key"));
//
//		// 生成的密文
//		System.out.println(new String(cryptograph, "utf-8"));
//
//		byte[] target = decryptByPrivateKey(
//				cryptograph,
//				readKeyFromFile(
//						"D:/zys_workspace_root/uf/hsxt-uf/hsxt-uf-service/src/main/resources/security",
//						"uf_rsa_private.key"));//
//		// 解密密文
//		System.out.println(new String(target));
	}
}