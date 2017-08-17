package com.gy.hsxt.common.utils;

import java.util.Random;

/**
 * Simple to Introduction
 * 
 * @category Simple to Introduction
 * @className RandomCode
 * @description 一句话描述该类的功能
 * @author chenda
 * @createDate 2014-12-9 下午3:42:25
 * @updateUser chenda
 * @updateDate 2014-12-9 下午3:42:25
 * @updateRemark 说明本次修改内容
 * @version v0.0.1
 */
public class RandomCodeUtils {
	static char[] mixCode = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
			'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
			'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
	static char[] englishCode = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I',
			'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V',
			'W', 'X', 'Y', 'Z' };
	static char[] numberCode = { '0', '1', '2', '3', '4', '5', '6', '7', '8',
			'9' };
	private static Integer codeCount = 4;

	/**
	 * 英文与数字混合随机码
	 * 
	 * @return
	 */
	public static String getMixCode() {
		Random random = new Random();
		String code = "";
		for (int i = 0; i < codeCount; i++) {
			code += String.valueOf(mixCode[random.nextInt(36)]);
		}
		return code;
	}

	/**
	 * 英文随机码
	 * 
	 * @return
	 */
	public static String getEnglishCode() {
		Random random = new Random();
		String code = "";
		for (int i = 0; i < codeCount; i++) {
			code += String.valueOf(englishCode[random.nextInt(26)]);
		}
		return code;
	}

	/**
	 * 数字随机码
	 * 
	 * @return
	 */
	public static String getNumberCode() {
		Random random = new Random();
		String code = "";
		for (int i = 0; i < codeCount; i++) {
			code += String.valueOf(numberCode[random.nextInt(10)]);
		}
		return code;
	}

	/**
	 * 随机生成6位不同的数字
	 * 
	 * @return
	 */
	public static String getNumberResult() {
		Random rand = new Random();
		for (int i = 10; i > 1; i--) {
			int index = rand.nextInt(i);
			char tmp = numberCode[index];
			numberCode[index] = numberCode[i - 1];
			numberCode[i - 1] = tmp;
		}
		String result = "";
		for (int i = 0; i < 6; i++) {
			result = result + numberCode[i];
		}
		return result;
	}

	/**
	 * 根据传入参数获取数字随机码
	 * 
	 * @param capacity
	 *            位数
	 * @return
	 */
	public static String getNumberCode(int capacity) {
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < capacity; i++) {
			sb.append(numberCode[random.nextInt(10)]);
		}
		return sb.toString();
	}

	/**
	 * 英文与数字混合随机码
	 * 
	 * @param codeCount
	 *            随机码数量
	 * @return
	 */
	public static String getMixCode(Integer codeCount) {
		Random random = new Random();
		String code = "";
		for (int i = 0; i < codeCount; i++) {
			code += String.valueOf(mixCode[random.nextInt(36)]);
		}
		return code;
	}

	/**
	 * 英文随机码
	 * 
	 * @param codeCount
	 *            随机码数量
	 * @return
	 */
	public static String getEnglishCode(Integer codeCount) {
		Random random = new Random();
		String code = "";
		for (int i = 0; i < codeCount; i++) {
			code += String.valueOf(englishCode[random.nextInt(26)]);
		}
		return code;
	}

	/**
	 * 数字随机码
	 * 
	 * @param codeCount
	 *            随机码数量
	 * @return
	 */
	public static String getNumberCode(Integer codeCount) {
		Random random = new Random();
		String code = "";
		for (int i = 0; i < codeCount; i++) {
			code += String.valueOf(numberCode[random.nextInt(10)]);
		}
		return code;
	}

	/**
	 * 自定义随机码
	 * 
	 * @param obj
	 *            随机码内容
	 * @param codeCount
	 *            随机码数量
	 * @return
	 */
	public static String getCustomCode(char[] obj, Integer codeCount) {
		Random random = new Random();
		String code = "";
		for (int i = 0; i < codeCount; i++) {
			code += String.valueOf(obj[random.nextInt(obj.length)]);
		}
		return code;
	}
}
