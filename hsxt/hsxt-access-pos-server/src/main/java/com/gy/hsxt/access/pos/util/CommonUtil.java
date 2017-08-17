/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.pos.util;

import java.math.BigDecimal;
import java.security.MessageDigest;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Formatter;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.access.pos.constant.PosRespCode;
import com.gy.hsxt.access.pos.exception.PosException;

		
/**
 * 
 * 
 * @Package: com.gy.hsxt.access.pos.util  
 * @ClassName: CommonUtil 
 * @Description: 工具类
 *
 * @author: guiyi149 
 * @date: 2015-11-10 下午2:29:22 
 * @version V1.0
 */
public class CommonUtil {

	/**
     * 积分比率，长度。0001--0300
     */
    public static final int RATE_LEN = 4;
    
    /**
     * 48（0x30）是数字0，49是数字1……
     */
    public static final int NUMBER_ASCII_DISTANCE = 48;
    
    /**
     * 除数(比率的倍率)
     */
    public static BigDecimal rateDivisor = new BigDecimal(10000);
    /**
     * 除数(金钱的倍率)
     */
    public static BigDecimal moneyDivisor = new BigDecimal(100);
    
	/**
	 * 
	 * @author   wucl	
	 * 2015-9-23 下午2:13:20
	 * @param wrong
	 * @param msg
	 * @param respCode
	 * @throws PosException 
	 */
	public static void checkState(boolean wrong, String msg, PosRespCode respCode) throws PosException {
		if (wrong) {
			SystemLog.info("CommonUtil", "checkState()", "msg:" + msg + ";respCode:" +respCode);
			throw new PosException(respCode, msg);
		}
	}
	
	/**
	 * 记录异常信息 kend
	 * @param wrong
	 * @param msg
	 * @param respCode
	 * @param e
	 * @throws PosException
	 */
	public static void checkState(boolean wrong, String msg, PosRespCode respCode, Exception e) throws PosException {
		if (wrong) {
			SystemLog.error("CommonUtil", "checkState()", msg + " ;\r\n" +
					"Exception info: " + e.getClass().getName() + ":" + e.getMessage() + 
					LogUtil.getTrace(e), e);
			throw new PosException(respCode, msg);
		}
	}
	
	/**
     * 人民币报文字符串转金额(人民币预留两位小数)
     *
     * @param data
     * @return
     
    public static double str2Money(String s) {
        return Long.parseLong(s) / 100.0;
    }
    */
    public static BigDecimal changePackDataToMoney(String data){
        BigDecimal money = new BigDecimal(data);
        return money.divide(moneyDivisor).setScale(2);//kend
    }
    /**
     * 报文字符串转积分比例
     *
     * @param data
     * @return
     
    public static double changePackToRate(String data) {
        return Long.parseLong(data) / 10000.0;
    }
    */
    public static BigDecimal changePackDataToRate(String data){
        BigDecimal rate = new BigDecimal(data);
        return rate.divide(rateDivisor);
    }
    
    /**
     * 人民币只保留小数后两位，金额补零为12位数
     * 
     * @author   wucl	
     * 2015-9-23 下午2:38:06
     * @param money
     * @return
     
    @SuppressWarnings("resource")
	public static String fill0InMoney(double money) {
        return new Formatter().format("%012d", Math.round(money * 100)).toString();
    }
    */
    @SuppressWarnings("resource")
    public static String fill0InMoney(BigDecimal money) {
        
        return new Formatter().format("%012d", money.multiply(moneyDivisor).longValue()).toString();
    }
    /**
     * GengLian modified at 2014/11/04. 有bug。
     * GengLian fixed at 2014/12/17.
     * <p/>
     * Assert.isTrue(pointRate <= 0.3d && pointRate >= 0.0001);，不能在这里的静态函数里搞检查了。
     * 本来应该有这句的，但目前的业务是动态查询缓存服务器的pointRate上限下限。
     * 只能简单的检查长度了。
     * <p/>
     * 积分比例右补零为4位数
     *
     * @return
     
    public static String fill0InRate(double pointRate) {
        BigDecimal bd = new BigDecimal(pointRate);
        bd=bd.multiply(new BigDecimal(10000));
        double val = bd.setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
        String ss = String.valueOf(val);
       
        if (ss.contains(".")) {
            ss = StringUtils.substringBefore(ss, ".");
        }
        Assert.state(ss.length() <= RATE_LEN, "pointRate is larger than 4. " + ss);
        return StringUtils.leftPad(ss, RATE_LEN, '0');
    }
    */
    public static String fill0InRate(BigDecimal pointRate) {
        BigDecimal bd=pointRate.multiply(rateDivisor);
        double val = bd.setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
        String ss = String.valueOf(val);
       
        if (ss.contains(".")) {
            ss = StringUtils.substringBefore(ss, ".");
        }
        Assert.state(ss.length() <= RATE_LEN, "pointRate is larger than 4. " + ss);
        return StringUtils.leftPad(ss, RATE_LEN, '0');
    }
    /**
     * 这是搭配MIN_CURRENCY_AMT_DELTA而使用的。
     */
    public static final double MIN_CURRENCY_AMT_DELTA2 = 100;
    
	public static Double roundPoiAmt(Double in) {
	    	
		BigDecimal   db   =   new   BigDecimal(String.valueOf(in));
		in = db.setScale(4,   BigDecimal.ROUND_DOWN).doubleValue();
		
	    String str = StringUtils.substringAfter(in.toString(), ".");
	    
	    if (str.length() > 2) {//超过两位 截取补1
	        final double b = in * MIN_CURRENCY_AMT_DELTA2;
	        // int c = (int) Math.rint(b);
	        // int c = (int) Math.floor(b);
	        long c = (long) b;
	        ++c;
	        return c / MIN_CURRENCY_AMT_DELTA2;
	    }
        return in;
    }
    
    /**
     * 网络字节序
     * bytes转换成十六进制字符串
     * GengLian modified at 2014/10/31
     *
     * @param b
     * @return
     */
    @Deprecated
    public static String byte2HexStr(byte[] b) {
        return new String(Hex.encodeHex(b));

    }
    
    /**
     * 字节异或处理。用于代替旧的uniteBytes()
     * http://baike.baidu.com/link?url=WqOrDvlL7zp_lixGJw9ZPjIM0K0uYZxbsHbZBQ3UeY6nKaTa7wkWcxE9F7XkAHZSB0w9Ks7agBATHZLK00kjW_
     * // 把这两句放入原来的uniteBytes()里，没有报错，证明了2个输入值都是数字（ASCII 57以内）。
     * if(src0 > 57 || src1 > 57) {
     * throw new RuntimeException("cccccccccccccccccccc");
     * }
     */
    public static byte uniteBytes(byte src0, byte src1) {
        return (byte) (((src0 - NUMBER_ASCII_DISTANCE) << 4) ^ (src1 - NUMBER_ASCII_DISTANCE));
    }
    
    /**
     * 获取字符串含有中文个数  04/16 试试改回去吧，有问题反正我不管了。 
     * @param str 
     * @return
     */
    public static int getCnCount(String str) {
        int len = 0;
        int j = 0;
        char[] c = str.toCharArray();
        for (int i = 0; i < c.length; i++) {
            len = String.valueOf(c[i]).getBytes().length;
            
            if (len > 1) {
                j++;
            }
        }
        return j;
    }
    
    /**
     * 从pos机发来的请求中获取时间戳，如果为空则使用系统时间
     * @param tradeDate
     * @param tradeTime
     * @return
     */
	public static Timestamp getPosReqTime(String tradeDate, String tradeTime) {

		if (tradeTime != null && tradeDate != null) {
			Calendar now = Calendar.getInstance();
			int year = now.get(Calendar.YEAR);// 得到年
			StringBuilder builder = new StringBuilder();
			builder.append(year).append("-").append(tradeDate.charAt(0))
					.append(tradeDate.charAt(1)).append("-")
					.append(tradeDate.charAt(2)).append(tradeDate.charAt(3))
					.append(" ").append(tradeTime.charAt(0))
					.append(tradeTime.charAt(1)).append(":")
					.append(tradeTime.charAt(2)).append(tradeTime.charAt(3))
					.append(":").append(tradeTime.charAt(4))
					.append(tradeTime.charAt(5));
			return Timestamp.valueOf(builder.toString());
		} else {
			return new Timestamp(System.currentTimeMillis());// 默认系统当前时间;
		}

	}
	
	/***
     * MD5加码 生成16位md5码
     */
    public static String string2MD5(String inStr) {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
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
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16)
                hexValue.append("0");
            hexValue.append(Integer.toHexString(val));
        }
        String md5Code = hexValue.toString();
        //return md5Code.substring(8, 24);
        return md5Code;//不截取

    }
  //格式化金额
    public static String formatMoney(double money) {
        DecimalFormat df = new DecimalFormat("#.00");
        return df.format(money);
    }
    
    /**
     * double转成String 避免科学计数法
     */
    public static String doubleToString(Double val){
        DecimalFormat decimalFormat = new DecimalFormat("###0.0000");//格式化设置
        return decimalFormat.format(val);
    }
    
    /**
     * added by liuzh on 2016-06-28
     * 获取支付方式  1.互生币支付 2.网银  3.现金
     * @param transType 格式:A12400  取第三个字符,就是2
     * @return
     */
    public static String getTransWay(String transType) {    	
    	char transWay = '0';
    	if(transType.length()>2) {
    		transWay = transType.charAt(2);
    	}
    	return String.valueOf(transWay);
    }
}

	
