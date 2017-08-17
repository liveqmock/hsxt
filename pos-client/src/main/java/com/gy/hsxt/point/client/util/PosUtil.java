/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.point.client.util;

import java.io.UnsupportedEncodingException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.codec.binary.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.alibaba.druid.util.HexBin;
import com.gy.hsxt.point.client.bean.Country;
import com.gy.hsxt.point.client.bean.CountryCurrency;
import com.gy.hsxt.point.client.bean.GenPmkResult;
import com.gy.hsxt.point.client.bean.PosInfoResult;

/**
 * 
 * POS工具类
 * 
 * @Package: com.gy.point.client.entry
 * @ClassName: MainView
 * 
 * @author: liyh
 * @date: 2015-12-18 上午11:23:40
 * @version V3.0
 */
public class PosUtil {
	private static final Logger LOGGER = LoggerFactory.getLogger(PosUtil.class);

	/**
	 * 转化方法
	 * 
	 * @param genPmkResult
	 * @return
	 */
	public byte[] pmkDone(GenPmkResult genPmkResult) {
		byte[] dataByte = null;
		try {
			// 1 pmk16位，没错
			byte GETPMKANDSYNCPARAMREPTYPE = (byte) 0x83;// 应答类型
			String pmk = genPmkResult.getPmk();
			// 获取秘钥对象
			PosInfoResult syncParam = genPmkResult.getPosInfo();
			if (pmk != null && syncParam != null) {
				LOGGER.debug(pmk.length() + "getPmk:" + pmk);
				byte[] pmkByte = HexBin.decode(pmk);
				// 日志输出
				StringBuffer tempStr1 = new StringBuffer();
				for (int j = 0; j < pmkByte.length; j++) {
					tempStr1.append(pmkByte[j] + ",");
				}
				LOGGER.debug(pmkByte.length + "getPmkByte:" + tempStr1);
				// 2 四位基本信息版本 "baseInfoVersion":"0001" ，没错
				String baseInfoVersion = syncParam.getBaseInfoVersion();
				byte[] baseInfoVersionByte = StringUtils
						.getBytesUtf8(baseInfoVersion);
				// 3 四位货币版本信息 "currencyVersion":"0001"，没错
				String currencyVersion = syncParam.getCurrencyVersion();
				byte[] currencyVersionByte = StringUtils
						.getBytesUtf8(currencyVersion);
				// 4 四位国家信息版本 "countryVersion":"0001"，没错
				String countryVersion = syncParam.getCountryVersion();
				byte[] countryVersionByte = StringUtils
						.getBytesUtf8(countryVersion);
				// 5 四位积分信息版本 "pointInfoVersion":"0001"
				String pointInfoVersion = syncParam.getPointInfoVersion();
				byte[] pointInfoVersionByte = StringUtils
						.getBytesUtf8(pointInfoVersion);
				// 6 128位企业名称 "entName":"托管企业-创业"
				String entName = syncParam.getEntName();
				byte[] entNameByte = strToGbkBytes(entName);
				// 7 1位企业类型 "entType":"T"
				String entType = String.valueOf(syncParam.getEntType());
				byte[] entTypeByte = StringUtils.getBytesUtf8(entType);
				// 8 六种常用货币信息
				CountryCurrency currencyInfoOut = null;
				// 9 货币序号
				int currencyIndex = 0;
				// 10 货币ID
				String currencyId = null;
				// 货币编码
				String currencyCode = null;
				// 货币序号字节
				byte currencyIndexByte = 0;
				// 货币ID字节
				byte[] currencyIdByte = null;
				// // 货币编码字节
				byte[] currencyCodeByte = null;
				int currencyOneLen = 1 + Constants.CURRENCYCODELEN
						+ Constants.CURRENCYIDLEN;// 序号+code+id 1+10+3=14
				List<CountryCurrency> currencys = syncParam.getCurrency();
				byte[] currencyBodyByte = new byte[currencyOneLen
						* currencys.size()];// 6条 14*6=84
				int count1 = 0;
				for (int i = 0; i < currencys.size(); i++) {
					currencyInfoOut = currencys.get(i);
					currencyIndex = currencyInfoOut.getCurrencySeqNo();// 修改
					currencyId = currencyInfoOut.getCurrencyNo();// 修改
					currencyCode = currencyInfoOut.getCurrencyCode();
					// 转字节
					currencyIndexByte = (byte) currencyIndex;
					// 特别处理了 1，2
					currencyIdByte = StringUtils.getBytesUtf8(currencyId);
					currencyCodeByte = StringUtils.getBytesUtf8(currencyCode);
					// 给数组赋值
					currencyBodyByte[count1] = currencyIndexByte;
					count1 += 1;
					// 给数组赋值
					System.arraycopy(currencyIdByte, 0, currencyBodyByte,
							count1, currencyIdByte.length);
					// 加3位
					count1 += Constants.CURRENCYIDLEN;
					// // 给数组赋值
					System.arraycopy(currencyCodeByte, 0, currencyBodyByte,
							count1, currencyCodeByte.length);
					// 加10位
					count1 += Constants.CURRENCYCODELEN;
				}
				// 国家对象
				Country countryInfoOut = null;
				// 国家序号
				int countryIndex = 0;
				// 国家编码
				String countryCode = null;
				// 国家序号字节
				byte[] countryIndexByte = null;
				// 国家编码字节
				byte[] countryCodeByte = null;
				int countryOneLen = Constants.COUNTRYINDEXLEN
						+ Constants.COUNTRYCODELEN; // 3+3
				// 获取国家信息
				List<Country> countrys = syncParam.getCountryList();
				// 国家数量
				int countryCount = countrys.size(); // 需要修改
				// 国家数量字节
				byte countByte = (byte) Integer.valueOf(countryCount)
						.intValue();
				// 国家对象字节
				byte[] countryBodyByte = new byte[countryOneLen
						* countrys.size()];
				int count2 = 0;
				// 遍历国家集合
				for (Iterator<Country> iterator = countrys.iterator(); iterator
						.hasNext();) {
					// 国家对象
					countryInfoOut = (Country) iterator.next();
					// 3 国家序号
					countryIndex = Integer.parseInt(countryInfoOut
							.getCountryNo());// 修改
					// 3 国家编码，烧机数据中，由于这个值是英文，所以这个值没有用的，而是用了countryNo，
					// 这里要特别注意，千万不可以出错
					countryCode = countryInfoOut.getCountryNo();
					// 国家序号字节
					countryIndexByte = StringUtils
							.getBytesUtf8(org.apache.commons.lang3.StringUtils
									.leftPad(countryIndex + "", 3, '0'));
					// 国家编码字节
					countryCodeByte = StringUtils.getBytesUtf8(countryCode);
					// 数组赋值
					System.arraycopy(countryIndexByte, 0, countryBodyByte,
							count2, countryIndexByte.length);
					// 加3位
					count2 += Constants.COUNTRYINDEXLEN;
					// 数组赋值
					System.arraycopy(countryCodeByte, 0, countryBodyByte,
							count2, countryCodeByte.length);
					// 加3位
					count2 += Constants.COUNTRYCODELEN;
				}
				// 12位积分转现比率
				int exchangeRate = (int) syncParam.getExchangeRate();
				// 积分转现比率字节
				byte[] exchangeRateByte = StringUtils.getBytesUtf8(exchangeRate
						+ "");
				// 12位互生币转现比率
				int hsbExchangeRate = (int) syncParam.getHsbExchangeRate();
				// 互生币转现比率字节
				byte[] hsbExchangeRateByte = StringUtils
						.getBytesUtf8(hsbExchangeRate + "");
				// 积分比例数量，默认是5个，有问题
				int pointRateCount = syncParam.getPointRateCount();
				// 积分比率数量字节
				byte pointRateCountByte = (byte) Integer
						.valueOf(pointRateCount).intValue();
				// 积分比例，需要修改，去除空字符和“|"字符
				String[] pointRates = syncParam.getPointRates();
				byte[] pointRateBodyByte = new byte[Constants.POINTRATELEN
						* pointRates.length];
				for (int i = 0; i < pointRates.length; i++) {
					double pointRatedouble = Double.valueOf(pointRates[i]) * 10000;
					int pointRateInt = (int) pointRatedouble;
					byte[] test = StringUtils.getBytesUtf8(pointRateInt + "");
					System.arraycopy(test, 0, pointRateBodyByte, i
							* Constants.POINTRATELEN, test.length);
				}
				// 定义字节数组长度
				dataByte = new byte[1 + 16 + 4 + 4 + 4 + 4 + Constants.ENTNOLEN
						+ Constants.POSNOLEN + Constants.ENTNAMELEN
						+ Constants.ENTTYPELEN + currencyBodyByte.length + 1
						+ countryBodyByte.length + Constants.EXCHANGERATELEN
						+ Constants.HSBEXCHANGERATELEN + 1
						+ pointRateBodyByte.length];
				System.out.println("字节数组长度:" + dataByte.length);
				// 1 赋值16位pmk
				int count3 = 0;
				dataByte[count3] = GETPMKANDSYNCPARAMREPTYPE;
				count3 += 1;
				System.arraycopy(pmkByte, 0, dataByte, count3, pmkByte.length);
				count3 += pmkByte.length;
				// 2 赋值11 位企业编号
				String posNoStr = syncParam.getPosNo();
				String entNoStr = syncParam.getEntResNo();
				if (posNoStr.length() == 15) {
					entNoStr = posNoStr.substring(0, 11);
				}
				byte[] entNoByte = StringUtils.getBytesUtf8(entNoStr);
				System.arraycopy(entNoByte, 0, dataByte, count3,
						entNoByte.length);
				count3 += Constants.ENTNOLEN;
				// 3 获取终端编号
				if (posNoStr.length() == 15) {
					// 获取4位终端编号，前面11位是企业互生号
					posNoStr = posNoStr.substring(11);
				}
				// 3 获取4位终端编号字节
				byte[] posNoByte = StringUtils.getBytesUtf8(posNoStr);
				// 3 赋值4位终端编号
				System.arraycopy(posNoByte, 0, dataByte, count3,
						posNoByte.length);
				count3 += Constants.POSNOLEN;
				// 4 赋值4位基本版本信息
				System.arraycopy(baseInfoVersionByte, 0, dataByte, count3,
						baseInfoVersionByte.length);
				count3 += baseInfoVersionByte.length;
				// 5 赋值4位货币版本信息
				System.arraycopy(currencyVersionByte, 0, dataByte, count3,
						currencyVersionByte.length);
				count3 += currencyVersionByte.length;
				// 6 赋值4位国家信息版本
				System.arraycopy(countryVersionByte, 0, dataByte, count3,
						countryVersionByte.length);
				count3 += countryVersionByte.length;
				// 7 赋值4位积分比例信息版本
				System.arraycopy(pointInfoVersionByte, 0, dataByte, count3,
						pointInfoVersionByte.length);
				count3 += pointInfoVersionByte.length;
				// 8 赋值128位企业名称
				System.arraycopy(entNameByte, 0, dataByte, count3,
						entNameByte.length);
				count3 += Constants.ENTNAMELEN;
				// 9 赋值1位企业编码
				System.arraycopy(entTypeByte, 0, dataByte, count3,
						entTypeByte.length);
				count3 += Constants.ENTTYPELEN;
				// 10 84位赋值货币对象6*14=84
				System.arraycopy(currencyBodyByte, 0, dataByte, count3,
						currencyBodyByte.length);
				count3 += currencyBodyByte.length;
				dataByte[count3] = countByte; // 国家个数
				count3 += 1;
				// 11 赋值国家信息对象 6*251
				System.arraycopy(countryBodyByte, 0, dataByte, count3,
						countryBodyByte.length);
				count3 += countryBodyByte.length;
				// 12 赋值12位积分转现比率
				System.arraycopy(exchangeRateByte, 0, dataByte, count3,
						exchangeRateByte.length);
				count3 += Constants.EXCHANGERATELEN;
				// 13 赋值12位互生币交换比率
				System.arraycopy(hsbExchangeRateByte, 0, dataByte, count3,
						hsbExchangeRateByte.length);
				count3 += Constants.HSBEXCHANGERATELEN;
				dataByte[count3] = pointRateCountByte; // 积分比例个数
				count3 += 1;
				// 14 赋值20位积分比例对象 4*5=20
				System.arraycopy(pointRateBodyByte, 0, dataByte, count3,
						pointRateBodyByte.length);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dataByte;
	}

	public static byte[] strToGbkBytes(String s) {
		try {
			return s.getBytes("gbk");
		} catch (UnsupportedEncodingException e) {
		}
		return null;
	}

	/**
	 * 获取本地IP，主要用于登陆接口
	 * 
	 * @return
	 */
	public static String getLocalIP() {
		@SuppressWarnings("rawtypes")
		Enumeration allNetInterfaces = null;
		try {
			allNetInterfaces = NetworkInterface.getNetworkInterfaces();
		} catch (SocketException e) {
			e.printStackTrace();
		}
		InetAddress ip = null;
		String localIP = null;
		while (allNetInterfaces.hasMoreElements()) {
			NetworkInterface netInterface = (NetworkInterface) allNetInterfaces
					.nextElement();
			@SuppressWarnings("rawtypes")
			Enumeration addresses = netInterface.getInetAddresses();
			while (addresses.hasMoreElements()) {
				ip = (InetAddress) addresses.nextElement();
				if (ip != null && ip instanceof Inet4Address) {
					if (!ip.getHostAddress().equals("127.0.0.1")) {
						localIP = ip.getHostAddress();
					}
				}
			}
		}
		return localIP;
	}

	/**
	 * 打印数据
	 * 
	 * @param Send
	 * @param i
	 */
	public static void printDataLog(byte[] Send, int i) {
		StringBuffer conarrayX1 = new StringBuffer();
		for (int x1 = 0; x1 < Send.length; x1++) {
			conarrayX1.append(Send[x1] + ",");
		}
		LOGGER.debug("Writer Before DataX" + i + ":" + conarrayX1);
	}

	/**
	 * 选择企业类型
	 * 
	 * @param tp
	 * @return
	 */
	public static String GetType(byte tp) {
		switch (tp) {
		case 'T':
			return "互生托管企业";
		case 'B':
			return "互生成员企业";
		case 'S':
			return "互生服务公司";
		case 'M':
			return "互生管理公司";
		case 'F':
			return "互生结算公司";
		default:
			return "互生未知";
		}
	}

	/**
	 * 字符串左补0 输入字节流，字节起始位置 输入需要的长度
	 */
	public static byte[] GetType(byte tp[], int start, int len) {
		byte[] tmp = new byte[len];
		int i;
		for (i = 0; i < len; i++) {
			if (tp[start + len - i - 1] == 0) {
				tmp[i] = '0';
			} else {
				break;
			}
		}
		int num = i;
		for (; i < len; i++) {
			tmp[i] = tp[start + i - num];
		}
		return tmp;
	}
}
