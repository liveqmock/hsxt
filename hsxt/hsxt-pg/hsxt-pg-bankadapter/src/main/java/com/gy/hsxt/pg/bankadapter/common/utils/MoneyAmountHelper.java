/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.pg.bankadapter.common.utils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/***************************************************************************
 * <PRE>
 *  Project Name    : bankadapter
 * 
 *  Package Name    : com.gy.ufe.bankadapter.common.utils
 * 
 *  File Name       : MomeyAmountHelper.java
 * 
 *  Creation Date   : 2014-10-28
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 金额帮助类
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class MoneyAmountHelper {

	private static final Map<Integer, String> SCALE_MAP = new HashMap<Integer, String>(
			2);

	static {
		SCALE_MAP.put(2, "#0.00");
		SCALE_MAP.put(6, "#0.000000");
	}

	/**
	 * 分转换为元, 精度为2
	 * 
	 * @param fen
	 *            分
	 * @return 元
	 */
	public static String fromFenToYuan(final BigInteger fen) {

		if (null == fen) {
			return fromFenToYuan("0");
		}

		return fromFenToYuan(fen.toString());
	}
	
	/**
	 * 分转换为元, 指定精度
	 * 
	 * @param fen
	 * @param scale
	 * @return
	 */
	public static String fromFenToYuan(final BigInteger fen, int scale) {
		return formatAmountScale(fromFenToYuan(fen), scale);
	}

	/**
	 * 分转换为元, 精度为2
	 * 
	 * @param fen
	 *            分
	 * @return 元
	 */
	public static String fromFenToYuan(String fen) {

		if (StringHelper.isEmpty(fen)) {
			fen = "0";
		}

		Pattern pattern = Pattern.compile("^([0])|([1-9][0-9]*{1})");
		Matcher matcher = pattern.matcher(fen);

		if (matcher.matches()) {
			return new BigDecimal(fen).divide(new BigDecimal(100)).setScale(2)
					.toString();
		}

		return new BigDecimal("0.00").setScale(2).toString();
	}

	/**
	 * 元转换为分
	 * 
	 * @param yuan
	 *            元
	 * @return 分
	 */
	public static BigInteger fromYuanToFen(String yuan) {
		if (StringHelper.isEmptyTrim(yuan)) {
			yuan = "0.00";
		}

		Pattern pattern = Pattern.compile("^[0-9]+(.[0-9]{0,6})?{1}");
		Matcher matcher = pattern.matcher(yuan);

		if (matcher.matches()) {
			return new BigDecimal(yuan).multiply(new BigDecimal(100))
					.toBigInteger();
		}

		return new BigInteger("0");
	}

	/**
	 * 将RMB转换为CNY
	 * 
	 * @param RMB
	 * @return
	 */
	public static String formatRMB2CNY(String RMB) {

		if (StringHelper.isEmpty(RMB) || "RMB".equalsIgnoreCase(RMB.trim())
				|| "156".equalsIgnoreCase(RMB.trim())) {
			return "CNY";
		}

		return RMB;
	}

	/**
	 * 将CNY转换为RMB
	 * 
	 * @param CNY
	 * @return
	 */
	public static String formatCNY2RMB(String CNY) {

		if (StringHelper.isEmpty(CNY) || "CNY".equalsIgnoreCase(CNY.trim())) {
			return "RMB";
		}

		return CNY;
	}

	/**
	 * 格式化金额精度, 精度为6
	 * 
	 * @param amount
	 * @param scale
	 * @return
	 */
	public static String formatAmountScale(double amount, int scale) {

		BigDecimal money = new BigDecimal(String.valueOf(amount));

		return new DecimalFormat(SCALE_MAP.get(scale)).format(money);
	}

	/**
	 * 格式化金额精度, 精度为6
	 * 
	 * @param amount
	 * @param scale
	 * @return
	 */
	public static String formatAmountScale(String amount, int scale) {

		if (StringHelper.isEmpty(amount)) {
			return formatAmountScale(0, scale);
		}

		BigDecimal money = new BigDecimal(amount);

		return new DecimalFormat(SCALE_MAP.get(scale)).format(money);
	}

	/**
	 * 格式化金额精度, 精度为6
	 * 
	 * @param amount
	 * @param scale
	 * @return
	 */
	public static String formatAmountScale(BigDecimal amount, int scale) {
		if (null == amount) {
			return formatAmountScale(0, scale);
		}

		return new DecimalFormat(SCALE_MAP.get(scale)).format(amount);
	}
	
	/**
	 * 格式化金额精度
	 * 
	 * @param amount
	 * @return
	 */
	public static BigDecimal formatAmount2BigDecimal(String amount) {

		if (!StringHelper.isEmptyTrim(amount)) {
			try {
				return new BigDecimal(amount);
			} catch (NumberFormatException e) {
			}
		}

		return new BigDecimal("0.000000");
	}

	public static void main(String[] args) {

//		System.out.println(formatAmountScale("10.22", 6));
//		System.out.println(formatAmountScale(0, 6));
//
//		System.out.println(fromFenToYuan("0.0000"));
//		System.out.println(fromYuanToFen("0.0000"));
		
		System.out.println(fromYuanToFen("9001.03"));
	}
}
