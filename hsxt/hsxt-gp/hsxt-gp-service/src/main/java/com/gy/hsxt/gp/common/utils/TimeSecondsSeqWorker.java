package com.gy.hsxt.gp.common.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import com.gy.hsi.ds.param.HsPropertiesConfigurer;
import com.gy.hsxt.gp.common.bean.Counter;
import com.gy.hsxt.gp.common.constant.ConfigConst;
import com.gy.hsxt.gp.common.constant.ConfigConst.TcConfigConsts;

/**
 * ID 生成工具类 生成规则：天时间戳+节点内存中的自增序列
 */
public class TimeSecondsSeqWorker {

	private static String sysInstanceNo = HsPropertiesConfigurer
			.getProperty(ConfigConst.SYSTEM_INSTANCE_NO);

	private static final Map<String, String> SUBSYSID_MAPPING = new HashMap<String, String>(
			4);
	private static final Map<String, Counter> COUNTER_MAPPING = new HashMap<String, Counter>(
			4);

	/**
	 * 根据时间戳生成序列号 16位不重复号:1位系统编号+2位实例号+6位年月日时分缩略编号+7位自增号
	 * 
	 * @param srcSubsysId
	 * @return
	 */
	public static String timeNextId16(String srcSubsysId) {
		synchronized ($.LEN_16) {

			// 年月日时分缩略编号, 6位
			String timestamp = fileoutCharsByRule("---yMMdd---m");

			String prefix = getPrefixFromOrderRange(srcSubsysId);

			long newValue = increaseCounterFromCache($.SEQUENCE_7,
					$.MAX_SEQUENCE7, prefix, $.LEN_16);

			return String.format("%s%s%s%07d", prefix, sysInstanceNo,
					timestamp, newValue);
		}
	}
		
	/**
	 * (平安银行网银支付订单号规则, 专用)
	 * 
	 * 根据时间戳生成序列号 26位不重复号:10位前缀+8位日期YYYYMMDD+2位实例号+6位流水
	 * 
	 * @param srcSubsysId
	 * @return
	 */
	public static String timeNextId26ByPrefix(String prefix) {
		synchronized ($.LEN_26) {
			String instNo = sysInstanceNo;

			if (instNo.startsWith("0")) {
				instNo = instNo.replaceFirst("^0", "");
			}

			long defaultValue = (1 == instNo.length()) ? $.SEQUENCE_7
					: $.SEQUENCE_6;

			long maxValue = (1 == instNo.length()) ? $.MAX_SEQUENCE7
					: $.MAX_SEQUENCE6;

			int offset = (1 == instNo.length()) ? 7 : 6;

			// 年月日时分缩略编号, 6位
			String timestamp = fileoutCharsByRule("yyyyMMdd----");

			long newValue = increaseCounterFromCache(defaultValue, maxValue,
					prefix, $.LEN_26);

			return String.format("%s%s%s%0" + offset + "d", prefix, timestamp,
					instNo, newValue);
		}
	}

	/**
	 * 根据时间戳生成序列号 20位不重复号:1位系统编号+2位实例号+10位年月日时分缩略编号+7位自增号
	 * 
	 * @param srcSubsysId
	 * @return
	 */
	public static String timeNextId20(String srcSubsysId) {
		synchronized ($.LEN_20) {
			// 年月日时分缩略编号, 10位
			String timestamp = fileoutCharsByRule("--yyMMddHH-ms");

			String prefix = getPrefixFromOrderRange(srcSubsysId);

			long newValue = increaseCounterFromCache($.SEQUENCE_7,
					$.MAX_SEQUENCE7, prefix, $.LEN_20);

			return String.format("%s%s%s%07d", prefix, sysInstanceNo,
					timestamp, newValue);
		}
	}

	/**
	 * 32位不重复号: 两位实例号+yyyyMMddHHmmssSSS(17位)+13位自增号
	 * 
	 * @return
	 */
	public static String timeNextId32() {
		String prefix = sysInstanceNo;

		synchronized ($.LEN_32) {
			// 17位
			String timestamp = $.FMT_YYYYMMDDHHMMSSSSS.get().format(
					Calendar.getInstance().getTime());

			long newValue = increaseCounterFromCache($.SEQUENCE_13,
					$.MAX_SEQUENCE13, prefix, $.LEN_32);

			return String.format("%s%s%013d", sysInstanceNo, timestamp,
					newValue);
		}
	}

	/**
	 * 取得前缀
	 * 
	 * @param srcSubsysId
	 * @return
	 */
	private static String getPrefixFromOrderRange(String srcSubsysId) {

		String prefix = SUBSYSID_MAPPING.get(srcSubsysId);

		if (StringUtils.isEmpty(prefix)) {
			if (0 >= SUBSYSID_MAPPING.size()) {
				initChinapayOrderNoRange();
			}

			prefix = SUBSYSID_MAPPING.get(srcSubsysId);
		}

		if (StringUtils.isEmpty(prefix)) {
			prefix = "1";
		}

		return prefix;
	}

	/**
	 * initChinapayOrderNoRange
	 */
	private static void initChinapayOrderNoRange() {

		String sysIdNo = HsPropertiesConfigurer
				.getProperty(TcConfigConsts.KEY_SYS_TC_CHINAPAY_ORDERNO_RANGE);

		String[] keyValues = sysIdNo.split("\\|");

		for (String keyValue : keyValues) {
			String[] subKeyValue = StringUtils.avoidNullTrim(keyValue).split(
					"\\=");

			if ((null != subKeyValue) && (2 <= subKeyValue.length)) {
				SUBSYSID_MAPPING.put(StringUtils.avoidNullTrim(subKeyValue[0]),
						StringUtils.avoidNullTrim(subKeyValue[1]));
			}
		}
	}

	/**
	 * 根据自定义规则抽取时间缩略
	 * 
	 * @param rule
	 * @return
	 */
	private static String fileoutCharsByRule(String rule) {
		int len = rule.length();

		// 年日编号, 5位
		String timestamp = $.FMT_YYYYMMDDHHMMSSSSS.get().format(
				Calendar.getInstance().getTime());

		StringBuilder build = new StringBuilder();

		for (int i = 0; i < len; i++) {
			if ('-' != rule.charAt(i)) {
				build.append(timestamp.charAt(i));
			}
		}

		return build.toString();
	}

	/**
	 * 从缓存中获取Counter对象并自增
	 * 
	 * @param defaultValue
	 * @param maxValue
	 * @param keys
	 * @return
	 */
	private static long increaseCounterFromCache(long defaultValue,
			long maxValue, String... keys) {
		String key = StringUtils.joinString(keys);

		Counter counter = COUNTER_MAPPING.get(key);

		if (null == counter) {
			counter = new Counter(defaultValue, maxValue);

			COUNTER_MAPPING.put(key, counter);
		}

		return counter.doIncreasing();
	}

	private static class $ {
		/** 长度定义 */
		static final String LEN_16 = "16";
		static final String LEN_20 = "20";
		static final String LEN_26 = "26";
		static final String LEN_32 = "32";

		/** 每天最多生成序列号 */
		static final long MAX_SEQUENCE6 = 999_999L;
		static final long MAX_SEQUENCE7 = 999_999_9L;
		static final long MAX_SEQUENCE13 = 999_999_999_999_9L;

		/** 当前序列号, 不得随便更改, 出现问题后果自负！！！！！！！！！！！！！！ */
		static final long SEQUENCE_6 = Long.parseLong(new SimpleDateFormat(
				"HHmmss").format(Calendar.getInstance().getTime()));

		static final long SEQUENCE_7 = Long.parseLong(new SimpleDateFormat(
				"ddHHmmss").format(Calendar.getInstance().getTime()).substring(
				1));

		static final long SEQUENCE_13 = Long.parseLong(new SimpleDateFormat(
				"MMddHHmmssSSS").format(Calendar.getInstance().getTime()));

		static final ThreadLocal<SimpleDateFormat> FMT_YYYYMMDDHHMMSSSSS = new ThreadLocal<SimpleDateFormat>() {
			@Override
			protected SimpleDateFormat initialValue() {
				return new SimpleDateFormat("yyyyMMddHHmmssSSS");
			}
		};
	}

	public static void main(String[] args) {
		HsPropertiesConfigurer.setProperties(ConfigConst.SYSTEM_INSTANCE_NO,
				"01");
		HsPropertiesConfigurer.setProperties(
				TcConfigConsts.KEY_SYS_TC_CHINAPAY_ORDERNO_RANGE,
				"TMS=1|AO=6|BS=7|PS=8|");

		sysInstanceNo = "00";

//		for (int i = 0; i < 10; i++) {
//			new Thread() {
//				public void run() {
//					String id16 = timeNextId16("BS");
//					String id20 = timeNextId20("AO");
//					String id20_ = timeNextId20("PS");
//					String id20_2 = timeNextId20("TMS");
//					String id32 = timeNextId32();
//
//					System.out.println(id16 + "  len=" + id16.length());
//					System.out.println(id20 + "  len=" + id20.length());
//					System.out.println(id20_ + "  len=" + id20_.length());
//					System.out.println(id20_2 + "  len=" + id20_2.length());
//					System.out.println(id32 + "  len=" + id32.length());
//				};
//			}.start();
//		}
		
		for (int i = 0; i < 10; i++) {
			new Thread() {
				public void run() {
					String id16 = timeNextId26ByPrefix("2000311146");

					System.out.println(id16 + "  len=" + id16.length());
				};
			}.start();
		}
	}
}
