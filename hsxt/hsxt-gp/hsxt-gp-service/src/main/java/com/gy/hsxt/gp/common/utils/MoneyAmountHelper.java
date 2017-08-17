/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.gp.common.utils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/***************************************************************************
 * <PRE>
 *  Project Name    : ufe-paygateway
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
	
	/**
	 * 分转换为元.
	 * 
	 * @param fen
	 *            分
	 * @return 元
	 */
	public static String fromFenToYuan(final BigInteger fen) {
		return fromFenToYuan(fen.toString());
	}

	/**
	 * 分转换为元.
	 * 
	 * @param fen
	 *            分
	 * @return 元
	 */
	public static String fromFenToYuan(final String fen) {
		String yuan = "";

		Pattern pattern = Pattern.compile("^[1-9][0-9]*{1}");
		Matcher matcher = pattern.matcher(fen);

		if (matcher.matches()) {
			yuan = new BigDecimal(fen).divide(new BigDecimal(100)).setScale(2)
					.toString();
		}

		return yuan;
	}

	/**
	 * 元转换为分
	 * 
	 * @param yuan
	 *            元
	 * @return 分
	 */
	public static BigInteger fromYuanToFen(final String yuan) {
		String fen = "";
		Pattern pattern = Pattern.compile("^[0-9]+(.[0-9]{2})?{1}");
		Matcher matcher = pattern.matcher(yuan);

		if (matcher.matches()) {
			try {
				NumberFormat format = NumberFormat.getInstance();
				Number number = format.parse(yuan);
				double temp = number.doubleValue() * 100.0;

				// 默认情况下GroupingUsed属性为true 不设置为false时,输出结果为2,012
				format.setGroupingUsed(false);

				// 设置返回数的小数部分所允许的最大位数
				format.setMaximumFractionDigits(0);

				fen = format.format(temp);
			} catch (ParseException e) {
			}
		}

		return new BigInteger(fen);
	}

	/**
	 * 将RMB转换为CNY
	 * 
	 * @param RMB
	 * @return
	 */
	public static String formatRMB2CNY(String RMB) {
		if (StringUtils.isEmpty(RMB) || "RMB".equalsIgnoreCase(RMB.trim())) {
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
		if (StringUtils.isEmpty(CNY) || "CNY".equalsIgnoreCase(CNY.trim())) {
			return "RMB";
		}

		return CNY;
	}

	/**
	 * 提供精确的乘法运算，但对运算结果进行四舍五入。
	 * 
	 * @param v1
	 *            被乘数
	 * @param v2
	 *            乘数
	 * @param scale
	 *            小数点后保留几位
	 * @return 两个参数的积
	 */
	public static double multiply(double v1, double v2, int scale) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.multiply(b2).setScale(scale, BigDecimal.ROUND_HALF_UP)
				.doubleValue();
	}

	/**
	 * 格式化金额精度
	 * 
	 * @param amount
	 * @return
	 */
	public static String formatAmount(double amount) {
		BigDecimal money = new BigDecimal(String.valueOf(amount));

		return new DecimalFormat("#0.000000").format(money);
	}

	/**
	 * 格式化金额精度
	 * 
	 * @param amount
	 * @return
	 */
	public static String formatAmount(String amount) {

		if (StringUtils.isEmpty(amount)) {
			return formatAmount(0);
		}

		BigDecimal money = new BigDecimal(amount);

		return new DecimalFormat("#0.000000").format(money);
	}

	/**
	 * 格式化金额精度
	 * 
	 * @param amount
	 * @return
	 */
	public static String formatAmount(BigDecimal amount) {
		if (null == amount) {
			return formatAmount(0);
		}

		return new DecimalFormat("#0.000000").format(amount);
	}

	/**
	 * 格式化金额精度
	 * 
	 * @param amount
	 * @return
	 */
	public static BigDecimal formatAmount2BigDecimal(String amount) {

		if (!StringUtils.isEmptyTrim(amount)) {
			try {
				return new BigDecimal(amount);
			} catch (NumberFormatException e) {
			}
		}

		return null;
	}

	public static void main(String[] args) {
		System.out.println(formatAmount("10.22"));
		System.out.println(formatAmount(10.2333));
	}
}
