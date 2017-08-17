/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.ps.points.handle;

import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringUtils;

import java.security.MessageDigest;

/** 
 * @Package: com.gy.hsxt.ps.points.handle  
 * @ClassName: CheckCardHandle 
 * @Description: 验证互生卡帮助类
 *
 * @author: chenhz 
 * @date: 2016-1-26 上午9:40:06 
 * @version V3.0  
 */
public class CheckCardHandle
{
	/**
	 * MD5加码 生成16位md5码
	 */
	public static String string2MD5(String inStr)
	{
		MessageDigest md5 = null;
		try
		{
			md5 = MessageDigest.getInstance("MD5");
		} catch (Exception e)
		{
			System.out.println(e.toString());
			e.printStackTrace();
			return "";
		}
		char[] charArray = inStr.toCharArray();
		byte[] byteArray = new byte[charArray.length];

		for (int i = 0; i < charArray.length; i++)
			byteArray[i] = (byte) charArray[i];
		byte[] md5Bytes = md5.digest(byteArray);
		StringBuffer hexValue = new StringBuffer();
		for (int i = 0; i < md5Bytes.length; i++)
		{
			int val = ((int) md5Bytes[i]) & 0xff;
			if (val < 16)
				hexValue.append("0");
			hexValue.append(Integer.toHexString(val));
		}
		String md5Code = hexValue.toString();
		return md5Code.substring(8, 24);

	}

	/**
	 * 例子[6, 17, 17, 23, -25, -97, -17, -99] 06186010626 密码111111 输入归一自定义ANSI
	 * X9.8 Format PIN和卡号，逆向生成明文密码 校验报文pin密码长度 it comes from ANSIFormat
	 * 归一个人标识码（PIN）的加密和解密（huangfh）
	 * 
	 * @param arrGyPin
	 * @param accNo
	 *            不能有暗码
	 * @param servicePinLen
	 * @return
	 * @throws HsException
	 */
	public static String decryptWithANSIFormat(byte[] arrGyPin, String accNo, String servicePinLen)
			throws HsException
	{

		accNo = StringUtils.left(accNo, 11);
		final byte arrAccNo[] = getHAccno(accNo);
		final byte arrPin[] = new byte[8];
		for (int i = 0; i < 8; i++)
		{
			arrPin[i] = (byte) (arrGyPin[i] ^ arrAccNo[i]);
		}
		final int pinLen = arrPin[0];
		final String strPin = new String(Hex.encodeHex(arrPin));

		if (StringUtils.isNotEmpty(servicePinLen))
		{
			final int pinLen1 = Integer.parseInt(servicePinLen);

			checkState(pinLen > pinLen1, "密钥解密输出pin长度:" + pinLen + ",报文pin最大长度：" + pinLen1,
					RespCode.REQUEST_PACK_FORMAT);
		}
		return strPin.substring(2, 2 + pinLen);
	}

	/**
	 * 获取pan,归一定义，右边数第二位开始，向左取10位 it comes from ANSIFormat
	 * 归一个人标识码（PIN）的加密和解密（huangfh）
	 */
	private static byte[] getHAccno(String accno)
	{
		final byte encode[] = new byte[8];
		final int len = accno.length();

		final byte arrAccNo[] = org.apache.commons.codec.binary.StringUtils.getBytesUtf8(accno
				.substring(len < 11 ? 0 : len - 11, len - 1));

		encode[3] = uniteBytes(arrAccNo[0], arrAccNo[1]);
		encode[4] = uniteBytes(arrAccNo[2], arrAccNo[3]);
		encode[5] = uniteBytes(arrAccNo[4], arrAccNo[5]);
		encode[6] = uniteBytes(arrAccNo[6], arrAccNo[7]);
		encode[7] = uniteBytes(arrAccNo[8], arrAccNo[9]);
		return encode;
	}

	/**
	 * 字节异或处理。用于代替旧的uniteBytes() http://baike.baidu.com/link?url=
	 * WqOrDvlL7zp_lixGJw9ZPjIM0K0uYZxbsHbZBQ3UeY6nKaTa7wkWcxE9F7XkAHZSB0w9Ks7agBATHZLK00kjW_
	 * // 把这两句放入原来的uniteBytes()里，没有报错，证明了2个输入值都是数字（ASCII 57以内）。 if(src0 > 57 ||
	 * src1 > 57) { throw new RuntimeException("cccccccccccccccccccc"); }
	 */
	public static byte uniteBytes(byte src0, byte src1)
	{
		return (byte) (((src0 - 48) << 4) ^ (src1 - 48));
	}

	/**
	 * 
	 * @author wucl 2015-9-23 下午2:13:20
	 * @param wrong
	 * @param msg
	 * @param respCode
	 * @throws HsException
	 */
	public static void checkState(boolean wrong, String msg, RespCode respCode)
			throws HsException
	{
		if (wrong)
		{
			SystemLog.warn("CommonUtil", "checkState()", msg);
			throw new HsException(respCode, msg);
		}
	}
}
