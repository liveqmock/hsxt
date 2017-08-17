/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */

package com.gy.hsxt.uc.password;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.gy.hsi.lc.client.log4j.SystemLog;

/**
 * 互商密码加密策略
 * 
 * @Package: com.gy.hsxt.uc.password
 * @ClassName: PasswordEcStrategy
 * @Description: TODO
 * 
 * @author: lixuan
 * @date: 2016-1-19 上午11:01:19
 * @version V1.0
 */
public class PasswordEcStrategy extends PasswordStrategy {
	private final static String MOUDLENAME = "com.gy.hsxt.uc.password.PasswordEcStrategy";

	/**
	 * 使用CBC模式，需要一个向量iv，可增加加密算法的强度
	 */
	public static String js_aes_sign = "0102030405060708";

	// 全局数组
	private final static String[] strDigits = { "0", "1", "2", "3", "4", "5",
			"6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };
	// 加密密钥
	public static final String password = "hsxt123";

	/**
	 * 根据明文密码生成消费者密码
	 * @param username 用户名
	 * @param pwd 密码明文
	 * @param saltValue 盐值
 	 * @return
	 */
	@Override
	public String genConsumerForBlankPwd(String username, String pwd, String saltValue) {
		return MD5(username + pwd);
	}

	/**
	 * 根据MD5密码生成消费者密码
	 * @param username 用户名
	 * @param md5Pwd MD5密码
	 * @param saltValue 盐值
	 * @return
	 */
	public String genConsumerForMd5Pwd(String username, String md5Pwd,
			String saltValue){
		return md5Pwd;
	}
	
	/**
	 * 根据密码明文生成企业密码
	 * @param pwd 密码明文
	 * @param saltValue 盐值
	 * @return
	 */
	@Override
	public String genEntForBlankPwd(String pwd, String saltValue) {
		// TODO Auto-generated method stub
		return getMD5Value(pwd);
	}
	
	/**
	 * 根据MD5密码生成企业密码
	 * @param md5Pwd MD5密码
	 * @param saltValue 盐值
	 * @return
	 */
	public String genEntForMd5Pwd(String md5Pwd, String saltValue){
		return md5Pwd;
	}
	/**
	 * MD5加密
	 * 
	 * @param strObj
	 * @return
	 */
	private String MD5(String strObj) {
		String resultString = null;
		try {
			// resultString = new String(strObj);
			MessageDigest md = MessageDigest.getInstance("MD5");
			// md.digest() 该函数返回值为存放哈希值结果的byte数组
			resultString = byteToString(md.digest(strObj.getBytes("UTF-8")));
		} catch (NoSuchAlgorithmException ex) {
			SystemLog.error(MOUDLENAME, "MD5", "互商加密方法加密失败", ex);
		} catch (UnsupportedEncodingException e) {
			SystemLog.error(MOUDLENAME, "MD5", "互商加密方法加密失败", e);
		}
		return resultString; 
	}

	private String byteToString(byte[] bByte) {
		StringBuffer sBuffer = new StringBuffer();
		for (int i = 0; i < bByte.length; i++) {
			sBuffer.append(byteToArrayString(bByte[i]));
		}
		return sBuffer.toString();
	}

	private String byteToArrayString(byte bByte) {
		int iRet = bByte;
		// System.out.println("iRet="+iRet);
		if (iRet < 0) {
			iRet += 256;
		}
		int iD1 = iRet / 16;
		int iD2 = iRet % 16;
		return strDigits[iD1] + strDigits[iD2];
	}

	// 互生企业员工加密方法
	public String getMD5Value(String password) {
		if (password == null || "".equals(password.trim()))
			return null;
		byte[] bytes = getBytes(password.trim());
		String result = null;
		MessageDigest messageDigest = null;
		try {
			messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.reset();
			messageDigest.update(bytes);
			bytes = messageDigest.digest();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} finally {
			messageDigest = null;
		}
		result = getMD5Value(bytes);
		return result;
	}

	private byte[] getBytes(String password) {
		byte[] bytes = null;
		try {
			bytes = password.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return bytes;
	}

	private String getMD5Value(byte[] bytes) {
		StringBuffer md5 = new StringBuffer(50);
		for (int i = 0; i < bytes.length; i++) {
			if (Integer.toHexString(0xFF & bytes[i]).length() == 1)
				md5.append("0").append(Integer.toHexString(0xFF & bytes[i]));
			else
				md5.append(Integer.toHexString(0xFF & bytes[i]));
		}
		return md5.toString();
	}

}
