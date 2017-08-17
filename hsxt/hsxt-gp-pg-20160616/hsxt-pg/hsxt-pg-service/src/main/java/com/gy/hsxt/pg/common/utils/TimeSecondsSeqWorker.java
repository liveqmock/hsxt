package com.gy.hsxt.pg.common.utils;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import com.gy.hsi.ds.param.HsPropertiesConfigurer;
import com.gy.hsxt.pg.common.constant.ConfigConst;
import com.gy.hsxt.pg.common.constant.ConfigConst.TcConfigConsts;
import com.gy.hsxt.pg.common.constant.Constant;

/**
 * ID 生成工具类 生成规则：天时间戳+节点内存中的自增序列
 */
public class TimeSecondsSeqWorker {

	private static String sysInstanceNo = HsPropertiesConfigurer
			.getProperty(ConfigConst.SYSTEM_INSTANCE_NO);

	private static final Map<Integer, String> CHINAPAY_ORDERNO_RANGE = new HashMap<Integer, String>(
			4);

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
		synchronized ($.LOCK_16) {

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
	 * 根据时间戳生成序列号 20位不重复号:1位系统编号+2位实例号+10位年月日时分缩略编号+7位自增号
	 * 
	 * @param srcSubsysId
	 * @return
	 */
	public static String timeNextId20(String srcSubsysId) {
		synchronized ($.LOCK_20) {
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

		synchronized ($.LOCK_32) {
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

		if (StringUtils.isEmpty(prefix) && !StringUtils.isEmpty(srcSubsysId)) {
			int id = 0;

			try {
				byte[] bytes = srcSubsysId.getBytes(Constant.ENCODING_UTF8);

				for (byte b : bytes) {
					id += b;
				}
			} catch (UnsupportedEncodingException e) {
			}

			if (0 >= CHINAPAY_ORDERNO_RANGE.size()) {
				initChinapayOrderNoRange();
			}

			int index = id % CHINAPAY_ORDERNO_RANGE.size();

			prefix = CHINAPAY_ORDERNO_RANGE.get((0 > index) ? 0 : index);

			SUBSYSID_MAPPING.put(srcSubsysId, prefix);
		}

		if (StringUtils.isEmpty(prefix)) {
			prefix = CHINAPAY_ORDERNO_RANGE.get(0);
			prefix = StringUtils.isEmpty(prefix) ? "6" : prefix;
		}

		return prefix;
	}

	/**
	 * initChinapayOrderNoRange
	 */
	private static void initChinapayOrderNoRange() {

		String sysIdNo = HsPropertiesConfigurer
				.getProperty(TcConfigConsts.KEY_SYS_TC_CHINAPAY_ORDERNO_RANGE);

		String[] temps = sysIdNo.split("\\|");

		int index = 0;

		for (String temp : temps) {
			CHINAPAY_ORDERNO_RANGE.put(index++, temp);
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

		/** 当前序列号, 不得随便更改, 出现问题后果自负！！！！！！！！！！！！！！ */
		static long SEQUENCE_7 = Long.parseLong(new SimpleDateFormat("ddHHmmss").format(
						Calendar.getInstance().getTime()).substring(1));

		static long SEQUENCE_13 = Long.parseLong(new SimpleDateFormat(
				"MMddHHmmssSSS").format(Calendar.getInstance().getTime()));

		/** 锁对象 */
		static final Object LOCK_16 = new Object();
		static final Object LOCK_20 = new Object();
		static final Object LOCK_32 = new Object();
		
		/** 长度定义 */
		static final String LEN_16 = "16";
		static final String LEN_20 = "20";
		static final String LEN_32 = "32";

		/** 每天最多生成序列号 */
		static final long MAX_SEQUENCE7 = 999_999_9L;
		static final long MAX_SEQUENCE13 = 999_999_999_999_9L;

		static final ThreadLocal<SimpleDateFormat> FMT_YYYYMMDDHHMMSSSSS = new ThreadLocal<SimpleDateFormat>() {
			@Override
			protected SimpleDateFormat initialValue() {
				return new SimpleDateFormat("yyyyMMddHHmmssSSS");
			}
		};
	}

	private static class Counter {
		private long value;
		private long maxValue;

		public Counter(long defaultValue, long maxValue) {
			this.value = defaultValue;
			this.maxValue = maxValue;
		}

		public long doIncreasing() {
			if (this.maxValue <= this.value) {
				this.value = 0;
			}

			this.value++;

			return this.value;
		}
	}

	public static void main(String[] args) {
		HsPropertiesConfigurer.setProperties(ConfigConst.SYSTEM_INSTANCE_NO,
				"01");
		HsPropertiesConfigurer.setProperties(
				TcConfigConsts.KEY_SYS_TC_CHINAPAY_ORDERNO_RANGE, "6|7|8|9");

		sysInstanceNo = "01";

		for (int i = 0; i < 10; i++) {
			new Thread() {
				public void run() {
					String id16 = timeNextId16("BS");
					String id20 = timeNextId20("AO");
					String id20_ = timeNextId20("PS");
					String id32 = timeNextId32();

					System.out.println(id16 + "  len=" + id16.length());
					System.out.println(id20 + "  len=" + id20.length());
					System.out.println(id20_ + "  len=" + id20_.length());
					System.out.println(id32 + "  len=" + id32.length());
				};
			}.start();
		}
	}
}
