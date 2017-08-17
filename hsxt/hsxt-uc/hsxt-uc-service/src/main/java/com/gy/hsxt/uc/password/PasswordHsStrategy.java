/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */

package com.gy.hsxt.uc.password;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.gy.hsxt.common.utils.StringUtils;

public class PasswordHsStrategy extends PasswordStrategy {

	/**
	 * 根据明文密码生成消费者密码
	 * 
	 * @param username
	 *            用户名
	 * @param pwd
	 *            密码明文
	 * @param saltValue
	 *            盐值
	 * @return
	 */
	@Override
	public String genConsumerForBlankPwd(String resNo, String pwd,
			String saltValue) {
		return getLowMd5HexString(resNo, pwd);
	}

	/**
	 * 根据MD5密码生成消费者密码
	 * 
	 * @param username
	 *            用户名
	 * @param md5Pwd
	 *            MD5密码
	 * @param saltValue
	 *            盐值
	 * @return
	 */
	public String genConsumerForMd5Pwd(String username, String md5Pwd,
			String saltValue) {
		return md5Pwd;
	}

	/**
	 * 根据密码明文生成企业密码
	 * 
	 * @param pwd
	 *            密码明文
	 * @param saltValue
	 *            盐值
	 * @return
	 */
	public String genEntForBlankPwd(String pwd, String saltValue) {
		return getMD5Value(pwd);
	}

	/**
	 * 根据MD5密码生成企业密码
	 * 
	 * @param md5Pwd
	 *            MD5密码
	 * @param saltValue
	 *            盐值
	 * @return
	 */
	public String genEntForMd5Pwd(String md5Pwd, String saltValue) {
		return md5Pwd;
	}

	/**
	 * 为了兼容旧系统个人密码，copy旧系统代码，修改密码固定6位限制(不限制，应该在前台限制）
	 * 
	 * @param cardno
	 * @param password
	 * @return
	 */
	private String getCode(String cardno, String password) {
		if ((StringUtils.isEmpty(password)) || (cardno.length() != 11)) {
			return null;
		} else {
			String retString = "";
			String cardNo = "0000000000000000"
					+ cardno.substring(0, cardno.length() - 1);
			cardNo = cardNo.substring(cardNo.length() - 16);
			int len = password.length();
			if (len > 0 && len < 256) {
				byte[] pinlock = new byte[16];
				byte[] pan = new byte[16];
				byte[] retbytes = new byte[16];
				for (int i = 0; i < 16; i++) {
					char pi = cardNo.charAt(i);
					pinlock[i] = (byte) (pi - 48);
				}
				pan[0] = 0;
				pan[1] = 6;
				for (int i = 0; i < 6; i++) {
					char pi = password.charAt(i);
					pan[i + 2] = (byte) (pi - 48);
				}
				for (int i = 0; i < 8; i++) {
					pan[i + 8] = 15;
				}

				for (int i = 0; i < 16; i++) {
					retbytes[i] = (byte) (pinlock[i] ^ pan[i]);
				}

				try {
					retString = new String(retbytes, "ISO-8859-1");
				} catch (Exception e) {
					e.printStackTrace();
					return null;
				}
				return retString;
			} else {
				return null;
			}

		}

	}

	/**
	 * 为了兼容旧系统个人密码，copy旧系统代码，修改密码固定6位限制(不限制，应该在前台限制）
	 * 
	 * @param cardno
	 * @param password
	 * @return
	 */
	private byte[] getCodeBytes(String cardno, String password) {
		if (StringUtils.isEmpty(password) || cardno.length() != 11) {
			return null;
		} else {
			String cardNo = "0000000000000000"
					+ cardno.substring(0, cardno.length() - 1);
			cardNo = cardNo.substring(cardNo.length() - 16);
			int len = password.length();
			if (len > 0 && len < 256) {
				byte[] pinlock = new byte[16];
				byte[] pan = new byte[16];
				byte[] retbytes = new byte[16];
				for (int i = 0; i < 16; i++) {
					char pi = cardNo.charAt(i);
					pinlock[i] = (byte) (pi - 48);
				}
				pan[0] = 0;
				pan[1] = 6;
				for (int i = 0; i < 6; i++) {
					char pi = password.charAt(i);
					pan[i + 2] = (byte) (pi - 48);
				}
				for (int i = 0; i < 8; i++) {
					pan[i + 8] = 15;
				}

				for (int i = 0; i < 16; i++) {
					retbytes[i] = (byte) (pinlock[i] ^ pan[i]);
				}
				return retbytes;
			} else {
				return null;
			}

		}

	}

	public String toChar8String(String cardno, String password) {
		byte[] in = getCodeBytes(cardno, password);
		String ret = "";
		for (int i = 0; i < 8; i++) {
			char c = (char) (in[i * 2] * 16 + in[i * 2 + 1]);
			String t = String.valueOf(c);
			ret = ret + t;
		}
		return ret;
	}

	public String getLowMd5HexString(String cardno, String password) {
		try {
			String t = toChar8String(cardno, password);
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.update(t.getBytes("ISO8859-1"));
			byte[] byteDigest = md5.digest();
			return String.valueOf(toLowHexChar(byteDigest));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private char[] toLowHexChar(byte[] arg0) {
		char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a',
				'b', 'c', 'd', 'e', 'f' };
		char[] charDigest = new char[arg0.length * 2];

		for (int i = 0; i < arg0.length; i++) {
			charDigest[i * 2] = Digit[(arg0[i] >>> 4) & 0X0F];
			charDigest[i * 2 + 1] = Digit[arg0[i] & 0X0F];
		}

		return charDigest;
	}

	private String getMD5Value(String password) {
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
