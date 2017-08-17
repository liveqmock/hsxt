/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.uf.common.utils.security;

import java.io.UnsupportedEncodingException;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;

import com.gy.hsxt.uf.common.bean.DataEnvelope;
import com.gy.hsxt.uf.common.constant.UFConstant;
import com.gy.hsxt.uf.common.utils.BytesUtils;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-uf-service
 * 
 *  Package Name    : com.gy.hsxt.uf.common.utils.security
 * 
 *  File Name       : SummaryUtils.java
 * 
 *  Creation Date   : 2014-9-28
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 针对java对象进行摘要信息抽取的工具类
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class SummaryUtils {
	/** 记录日志对象 **/
	private static final Logger logger = Logger.getLogger(SummaryUtils.class);

	/**
	 * 针对java对象进行摘要信息抽取
	 * 
	 * @param obj
	 * @return
	 */
	public static String parseSummaryData(Object obj) {
		if (null == obj) {
			return "";
		}

		DataEnvelope dataEnvelope = new DataEnvelope();
		dataEnvelope.setData(obj);

		byte[] bytesValue = BytesUtils.object2ByteByzip(dataEnvelope);

		StringBuilder strBuilder = new StringBuilder();

		int len = bytesValue.length;
		long result = 0;

		for (int i = len - 1; i >= 0; i--) {
			if (0 != i % 100) {
				result += Byte.valueOf(bytesValue[i]).intValue();

				if (0 == i % 5) {
					result += i;
				}

				if (0 == i % 20) {
					result += (i + 1);
				}
			} else {
				strBuilder.append(result);
				result = 0;
			}

			if ((100 >= len) && (0 == i)) {
				strBuilder.append(result);
			}
		}

		String resultStr = strBuilder.toString().replaceAll("-", "&")
				.replaceAll("0", "o").replaceAll("1", "l").replaceAll("7", "t")
				.replaceAll("6", "b").replaceAll("5", "s").replaceAll("3", "w")
				.replaceAll("8", "g").replaceAll("9", "p");

		try {
			byte[] bytes = resultStr.toUpperCase().getBytes(
					UFConstant.ENCODING_UTF8);

			return Base64.encodeBase64String(bytes).replaceAll("=", "X")
					.toUpperCase();
		} catch (UnsupportedEncodingException e) {
			logger.error(e);
		}

		return null;
	}
}
