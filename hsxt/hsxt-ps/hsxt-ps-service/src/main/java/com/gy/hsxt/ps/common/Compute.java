package com.gy.hsxt.ps.common;

import java.math.BigDecimal;

/**
 * @description 加减乘除精确计算类
 * @author chenhz
 * @createDate 2015-7-27 下午2:29:49
 * @Company 深圳市归一科技研发有限公司
 * @version v0.0.1
 */
public class Compute
{

	/**
	 * 加法运算
	 * 
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static double add(double d1, double d2)
	{
		BigDecimal b1 = new BigDecimal(d1);
		BigDecimal b2 = new BigDecimal(d2);
		return b1.add(b2).doubleValue();
	}

	/**
	 * 加法
	 * 
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static BigDecimal add(BigDecimal d1, BigDecimal d2)
	{
		return d1.add(d2);
	}

	/**
	 * 减法运算
	 * 
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static double sub(double d1, double d2)
	{
		BigDecimal b1 = new BigDecimal(d1);
		BigDecimal b2 = new BigDecimal(d2);
		return b1.subtract(b2).doubleValue();
	}

	/**
	 * 减法
	 * 
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static BigDecimal sub(BigDecimal d1, BigDecimal d2)
	{
		return d1.subtract(d2);
	}

	/**
	 * 减法
	 * 
	 * @param d1
	 * @param d2
	 * @param d3
	 * @return
	 */
	public static BigDecimal sub(BigDecimal d1, BigDecimal d2, BigDecimal d3)
	{
		return d1.subtract(d2).subtract(d3);
	}

	/**
	 * 乘法,预留len位小数，小数点len位以后进位
	 * 
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static double mulCeiling(double d1, double d2, int len)
	{
		BigDecimal b1 = new BigDecimal(d1);
		BigDecimal b2 = new BigDecimal(d2);
		return roundCeiling(b1.multiply(b2).doubleValue(), len);
	}

	/**
	 * 乘法，预留len位小数，小数点len位以后进位
	 * 
	 * @param d1
	 * @param d2
	 * @param len
	 * @return
	 */
	public static BigDecimal mulCeiling(BigDecimal d1, BigDecimal d2, int len)
	{
		return roundCeiling(d1.multiply(d2), len);
	}


	/**
	 * 乘法，预留len位小数，小数点len位以后进位
	 * 
	 * @param d1
	 * @param d2
	 * @param d3
	 * @param len
	 * @return
	 */
	public static BigDecimal mulCeiling(BigDecimal d1, BigDecimal d2, BigDecimal d3, int len)
	{
		return roundCeiling(d1.multiply(d2).multiply(d3), len);
	}

	/**
	 * 乘法，预留len位小数，小数点len位以后进位
	 * 
	 * @param d1
	 * @param d2
	 * @param len
	 * @return
	 */
	public static BigDecimal mulCeiling(BigDecimal d1, double d2, int len)
	{
		BigDecimal b2 = new BigDecimal(d2);
		return roundCeiling(d1.multiply(b2), len);
	}

	/**
	 * 乘法,预留len位小数，小数点len位以后截去
	 * 
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static double mulFloor(double d1, double d2, int len)
	{
		BigDecimal b1 = new BigDecimal(d1);
		BigDecimal b2 = new BigDecimal(d2);
		return roundFloor(b1.multiply(b2).doubleValue(), len);
	}




	/**
	 * 乘法，预留len位小数，小数点len位以后进位
	 *
	 * @param d1
	 * @param d2
	 * @param len
	 * @return
	 */
	public static BigDecimal mulRoundUp(BigDecimal d1, BigDecimal d2, int len)
	{
		return d1.multiply(d2).setScale(len,BigDecimal.ROUND_UP);
	}

	/**
	 * 乘法，预留len位小数，小数点len位以后截去
	 * 
	 * @param d1
	 * @param d2
	 * @param len
	 * @return
	 */
	public static BigDecimal mulFloor(BigDecimal d1, BigDecimal d2, int len)
	{
		return roundFloor(d1.multiply(d2), len);
	}

	/**
	 * 乘法，预留len位小数，小数点len位以后截去
	 * 
	 * @param d1
	 * @param d2
	 * @param d3
	 * @param len
	 * @return
	 */
	public static BigDecimal mulFloor(BigDecimal d1, BigDecimal d2, BigDecimal d3, int len)
	{
		return roundFloor(d1.multiply(d2).multiply(d3), len);
	}

	/**
	 * 乘法，预留len位小数，小数点len位以后截去
	 * 
	 * @param d1
	 * @param d2
	 * @param len
	 * @return
	 */
	public static BigDecimal mulFloor(BigDecimal d1, double d2, int len)
	{
		BigDecimal b2 = new BigDecimal(d2);
		return roundFloor(d1.multiply(b2), len);
	}

	/**
	 * 除法，预留len位小数，小数点len位以后进位
	 * 
	 * @param d1
	 * @param d2
	 * @param len
	 * @return
	 */
	public static double divCeiling(double d1, double d2, int len)
	{
		BigDecimal b1 = new BigDecimal(d1);
		BigDecimal b2 = new BigDecimal(d2);
		return b1.divide(b2, len, BigDecimal.ROUND_CEILING).doubleValue();
	}

	/**
	 * 除法，预留len位小数，小数点len位以后进位
	 * 
	 * @param d1
	 * @param d2
	 * @param len
	 * @return
	 */
	public static BigDecimal divCeiling(BigDecimal d1, BigDecimal d2, int len)
	{
		return d1.divide(d2, len, BigDecimal.ROUND_CEILING);
	}

	/**
	 * 除法，预留len位小数，小数点len位以后进位
	 * 
	 * @param d1
	 * @param d2
	 * @param len
	 * @return
	 */
	public static BigDecimal divCeiling(BigDecimal d1, double d2, int len)
	{
		BigDecimal b2 = new BigDecimal(d2);
		return d1.divide(b2, len, BigDecimal.ROUND_CEILING);
	}

	/**
	 * 除法，预留len位小数，小数点len位以后截去
	 * 
	 * @param d1
	 * @param d2
	 * @param len
	 * @return
	 */
	public static double divFloor(double d1, double d2, int len)
	{
		BigDecimal b1 = new BigDecimal(d1);
		BigDecimal b2 = new BigDecimal(d2);
		return b1.divide(b2, len, BigDecimal.ROUND_FLOOR).doubleValue();
	}

	/**
	 * 除法，预留len位小数，小数点len位以后截去
	 * 
	 * @param d1
	 * @param d2
	 * @param len
	 * @return
	 */
	public static BigDecimal divFloor(BigDecimal d1, BigDecimal d2, int len)
	{
		return d1.divide(d2, len, BigDecimal.ROUND_FLOOR);
	}

	/**
	 * 除法，预留len位小数，小数点len位以后截去
	 * 
	 * @param d1
	 * @param d2
	 * @param len
	 * @return
	 */
	public static BigDecimal divFloor(BigDecimal d1, double d2, int len)
	{
		BigDecimal b2 = new BigDecimal(d2);
		return d1.divide(b2, len, BigDecimal.ROUND_FLOOR);
	}

	/**
	 * 小数点len位保留，有余数进1
	 * 
	 * @param d
	 * @param len
	 * @return
	 */
	public static double roundCeiling(double d, int len)
	{
		BigDecimal b1 = new BigDecimal(d);
		BigDecimal b2 = new BigDecimal(1);
		return b1.divide(b2, len, BigDecimal.ROUND_CEILING).doubleValue();
	}

	/**
	 * 小数点len位保留，有余数进1
	 * 
	 * @param d
	 * @param len
	 * @return
	 */
	public static BigDecimal roundCeiling(BigDecimal d, int len)
	{
		BigDecimal b2 = new BigDecimal(1);
		return d.divide(b2, len, BigDecimal.ROUND_CEILING);
	}

	/**
	 * 小数点len位保留，有余数截断
	 * 
	 * @param d
	 * @param len
	 * @return
	 */
	public static double roundFloor(double d, int len)
	{
		BigDecimal b1 = new BigDecimal(d);
		BigDecimal b2 = new BigDecimal(1);
		return b1.divide(b2, len, BigDecimal.ROUND_FLOOR).doubleValue();
	}

	/**
	 * 小数点len位保留，有余数截断
	 * 
	 * @param d
	 * @param len
	 * @return
	 */
	public static BigDecimal roundFloor(BigDecimal d, int len)
	{
		BigDecimal b2 = new BigDecimal(1);
		return d.divide(b2, len, BigDecimal.ROUND_FLOOR);
	}

}
