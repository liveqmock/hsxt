package com.gy.hsxt.ws.common;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import com.gy.hsi.ds.param.HsPropertiesConfigurer;
import com.gy.hsxt.common.constant.BizGroup;
import com.gy.hsxt.common.guid.GuidAgent;
import com.gy.hsxt.common.utils.DateUtil;

public class WsTools {

	public static String getGUID() {
		String sysInstanceNo = HsPropertiesConfigurer.getProperty("system.instance.no");// 当前机器编号
		return GuidAgent.getStringGuid(BizGroup.WS + sysInstanceNo);
	}

	/**
	 * 生成积分的流水号
	 * 
	 * @return
	 */
	/*
	 * public static String getGUID(String number) { return
	 * GuidAgent.getStringGuid(number); }
	 *//**
	 * 生成UUID
	 * 
	 * @return
	 */
	/*
	 * public static String getUUID(String number) { return number +
	 * UUID.randomUUID().toString().replaceAll("-", ""); }
	 */
	/**
	 * 获取前一天的日期
	 * 
	 * @return
	 */
	public static String getBeforeDay() {
		return DateUtil.DateToString(DateUtil.getAfterDay(DateUtil.today(), -1));
	}

	/**
	 * 获取后一天的日期
	 * 
	 * @return
	 */
	public static String getAfterDay() {
		return DateUtil.DateToString(DateUtil.getAfterDay(DateUtil.today(), 1));
	}

	/**
	 * 获取下一年的日期
	 * 
	 * @return
	 */
	public static String getAfterYear() {
		return DateUtil.DateToString(DateUtil.getAfterYear(DateUtil.today(), 1));
	}

	/**
	 * 金额格式化返回字符串(保留6位小数)
	 * 
	 * @param bd
	 * @return
	 */
	public static String amountDecimalFormat(BigDecimal bd) {
		DecimalFormat myformat = new DecimalFormat("0.000000");
		return myformat.format(bd);
	}

	/**
	 * 金额格式化返回BigDecimal(保留6位小数)
	 * 
	 * @param bd
	 * @return
	 */
	public static BigDecimal formatBigDec(BigDecimal bd) {
		String adf = amountDecimalFormat(bd);
		BigDecimal bigDec = new BigDecimal(adf);
		return bigDec;
	}

}
