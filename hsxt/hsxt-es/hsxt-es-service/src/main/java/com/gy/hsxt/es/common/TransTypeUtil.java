package com.gy.hsxt.es.common;

/**
 * @Package: com.gy.hsxt.ps.common
 * @ClassName: TransType
 * @Description: 交易类型规则解析
 * 
 * @author: chenhongzhi
 * @date: 2015-12-3 上午10:51:29
 * @version V3.0
 */

public class TransTypeUtil
{

	/**
	 * 订单来源
	 * 
	 * @param transType
	 *            交易类型
	 * @return
	 */
	public static String orderSource(String transType)
	{
		String[] str = transType.split("");
		return str[2];
	}

	/**
	 * 交易方式
	 * 
	 * @param transType
	 *            交易类型
	 * @return
	 */
	public static String transWay(String transType)
	{
		String[] str = transType.split("");
		return str[3];
	}

	/**
	 * 订单状态
	 * 
	 * @param transType
	 *            交易类型
	 * @return
	 */
	public static String transStatus(String transType)
	{
		String[] str = transType.split("");
		return str[4];
	}

	/**
	 * 交易特征、支付方式
	 * 
	 * @param transType
	 *            交易类型
	 * @return
	 */
	public static String bitWay(String transType)
	{
		String[] str = transType.split("");
		return str[1] + str[3];
	}

	/**
	 * 交易特征+交易状态
	 * 
	 * @param transType
	 *            交易类型
	 * @return
	 */
	public static String traitWay(String transType)
	{
		String[] str = transType.split("");
		return str[1] + str[4];
	}

	/**
	 * 冲正标志
	 * 
	 * @param transType
	 *            交易类型
	 * @return
	 */
	public static String writeBack(String transType)
	{
		String[] str = transType.split("");
		return str[5];
	}

}
